package com.android.server.am;

import android.app.ApplicationErrorReport;
import android.os.IInstalld;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;
import android.system.UnixSocketAddress;
import android.util.Slog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;

/* loaded from: classes.dex */
public final class NativeCrashListener extends Thread {
    public final ActivityManagerService mAm;

    /* loaded from: classes.dex */
    public class NativeCrashReporter extends Thread {
        public ProcessRecord mApp;
        public String mCrashReport;
        public boolean mGwpAsanRecoverableCrash;
        public int mSignal;

        public NativeCrashReporter(ProcessRecord processRecord, int i, boolean z, String str) {
            super("NativeCrashReport");
            this.mApp = processRecord;
            this.mSignal = i;
            this.mGwpAsanRecoverableCrash = z;
            this.mCrashReport = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                ApplicationErrorReport.CrashInfo crashInfo = new ApplicationErrorReport.CrashInfo();
                crashInfo.exceptionClassName = "Native crash";
                crashInfo.exceptionMessage = Os.strsignal(this.mSignal);
                crashInfo.throwFileName = "unknown";
                crashInfo.throwClassName = "unknown";
                crashInfo.throwMethodName = "unknown";
                crashInfo.stackTrace = this.mCrashReport;
                ActivityManagerService activityManagerService = NativeCrashListener.this.mAm;
                String str = this.mGwpAsanRecoverableCrash ? "native_recoverable_crash" : "native_crash";
                ProcessRecord processRecord = this.mApp;
                activityManagerService.handleApplicationCrashInner(str, processRecord, processRecord.processName, crashInfo);
            } catch (Exception e) {
                Slog.e("NativeCrashListener", "Unable to report native crash", e);
            }
        }
    }

    public NativeCrashListener(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        byte[] bArr = new byte[1];
        File file = new File("/data/system/ndebugsocket");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileDescriptor socket = Os.socket(OsConstants.AF_UNIX, OsConstants.SOCK_STREAM, 0);
            Os.bind(socket, UnixSocketAddress.createFileSystem("/data/system/ndebugsocket"));
            Os.listen(socket, 1);
            Os.chmod("/data/system/ndebugsocket", 511);
            while (true) {
                FileDescriptor fileDescriptor = null;
                try {
                    try {
                        fileDescriptor = Os.accept(socket, null);
                        if (fileDescriptor != null) {
                            consumeNativeCrashData(fileDescriptor);
                        }
                    } catch (Exception e) {
                        Slog.w("NativeCrashListener", "Error handling connection", e);
                        if (fileDescriptor != null) {
                            Os.write(fileDescriptor, bArr, 0, 1);
                        }
                    }
                    if (fileDescriptor != null) {
                        Os.write(fileDescriptor, bArr, 0, 1);
                        try {
                            Os.close(fileDescriptor);
                        } catch (ErrnoException unused) {
                        }
                    }
                } catch (Throwable th) {
                    if (fileDescriptor != null) {
                        try {
                            Os.write(fileDescriptor, bArr, 0, 1);
                        } catch (Exception unused2) {
                        }
                        try {
                            Os.close(fileDescriptor);
                        } catch (ErrnoException unused3) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e2) {
            Slog.e("NativeCrashListener", "Unable to init native debug socket!", e2);
        }
    }

    public static int unpackInt(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static int readExactly(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) {
        int i3 = 0;
        while (i2 > 0) {
            int read = Os.read(fileDescriptor, bArr, i + i3, i2);
            if (read <= 0) {
                return -1;
            }
            i2 -= read;
            i3 += read;
        }
        return i3;
    }

    public void consumeNativeCrashData(FileDescriptor fileDescriptor) {
        ProcessRecord processRecord;
        byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(IInstalld.FLAG_USE_QUOTA);
        try {
            StructTimeval fromMillis = StructTimeval.fromMillis(10000L);
            Os.setsockoptTimeval(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_RCVTIMEO, fromMillis);
            Os.setsockoptTimeval(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO, fromMillis);
            if (readExactly(fileDescriptor, bArr, 0, 9) != 9) {
                Slog.e("NativeCrashListener", "Unable to read from debuggerd");
                return;
            }
            int unpackInt = unpackInt(bArr, 0);
            int unpackInt2 = unpackInt(bArr, 4);
            boolean z = bArr[8] != 0;
            if (unpackInt < 0) {
                Slog.e("NativeCrashListener", "Bogus pid!");
                return;
            }
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(unpackInt);
            }
            if (processRecord == null) {
                Slog.w("NativeCrashListener", "Couldn't find ProcessRecord for pid " + unpackInt);
                return;
            }
            if (processRecord.isPersistent()) {
                return;
            }
            while (true) {
                int read = Os.read(fileDescriptor, bArr, 0, IInstalld.FLAG_USE_QUOTA);
                if (read > 0) {
                    int i = read - 1;
                    if (bArr[i] == 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                if (read <= 0) {
                    break;
                }
            }
            if (!z) {
                ActivityManagerService activityManagerService = this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        ActivityManagerGlobalLock activityManagerGlobalLock = this.mAm.mProcLock;
                        ActivityManagerService.boostPriorityForProcLockedSection();
                        synchronized (activityManagerGlobalLock) {
                            try {
                                processRecord.mErrorState.setCrashing(true);
                                processRecord.mErrorState.setForceCrashReport(true);
                            } catch (Throwable th) {
                                ActivityManagerService.resetPriorityAfterProcLockedSection();
                                throw th;
                            }
                        }
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                    } catch (Throwable th2) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            }
            new NativeCrashReporter(processRecord, unpackInt2, z, new String(byteArrayOutputStream.toByteArray(), "UTF-8")).start();
        } catch (Exception e) {
            Slog.e("NativeCrashListener", "Exception dealing with report", e);
        }
    }
}

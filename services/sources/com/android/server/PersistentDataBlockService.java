package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.persistentdata.IPersistentDataBlockService;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.pm.UserManagerInternal;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class PersistentDataBlockService extends SystemService {
    public static final String TAG = "PersistentDataBlockService";
    public int mAllowedUid;
    public long mBlockDeviceSize;
    public final Context mContext;
    public final String mDataBlockFile;
    public final CountDownLatch mInitDoneSignal;
    public PersistentDataBlockManagerInternal mInternalService;
    public final boolean mIsRunningDSU;
    public boolean mIsWritable;
    public final Object mLock;
    public final IBinder mService;

    private native long nativeGetBlockDeviceSize(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeWipe(String str);

    public PersistentDataBlockService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mInitDoneSignal = new CountDownLatch(1);
        this.mAllowedUid = -1;
        this.mIsWritable = true;
        this.mService = new IPersistentDataBlockService.Stub() { // from class: com.android.server.PersistentDataBlockService.1
            public int write(byte[] bArr) {
                PersistentDataBlockService.this.enforceUid(Binder.getCallingUid());
                long doGetMaximumDataBlockSize = PersistentDataBlockService.this.doGetMaximumDataBlockSize();
                if (bArr.length > doGetMaximumDataBlockSize) {
                    return (int) (-doGetMaximumDataBlockSize);
                }
                try {
                    FileChannel blockOutputChannel = PersistentDataBlockService.this.getBlockOutputChannel();
                    ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 8 + 32);
                    allocate.put(new byte[32]);
                    allocate.putInt(428873843);
                    allocate.putInt(bArr.length);
                    allocate.put(bArr);
                    allocate.flip();
                    synchronized (PersistentDataBlockService.this.mLock) {
                        if (!PersistentDataBlockService.this.mIsWritable) {
                            return -1;
                        }
                        try {
                            blockOutputChannel.write(allocate);
                            blockOutputChannel.force(true);
                            if (!PersistentDataBlockService.this.computeAndWriteDigestLocked()) {
                                return -1;
                            }
                            return bArr.length;
                        } catch (IOException e) {
                            Slog.e(PersistentDataBlockService.TAG, "failed writing to the persistent data block", e);
                            return -1;
                        }
                    }
                } catch (IOException e2) {
                    Slog.e(PersistentDataBlockService.TAG, "partition not available?", e2);
                    return -1;
                }
            }

            public byte[] read() {
                PersistentDataBlockService.this.enforceUid(Binder.getCallingUid());
                if (!PersistentDataBlockService.this.enforceChecksumValidity()) {
                    return new byte[0];
                }
                try {
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        try {
                            synchronized (PersistentDataBlockService.this.mLock) {
                                int totalDataSizeLocked = PersistentDataBlockService.this.getTotalDataSizeLocked(dataInputStream);
                                if (totalDataSizeLocked == 0) {
                                    byte[] bArr = new byte[0];
                                    try {
                                        dataInputStream.close();
                                    } catch (IOException unused) {
                                        Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                                    }
                                    return bArr;
                                }
                                byte[] bArr2 = new byte[totalDataSizeLocked];
                                int read = dataInputStream.read(bArr2, 0, totalDataSizeLocked);
                                if (read >= totalDataSizeLocked) {
                                    try {
                                        dataInputStream.close();
                                    } catch (IOException unused2) {
                                        Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                                    }
                                    return bArr2;
                                }
                                Slog.e(PersistentDataBlockService.TAG, "failed to read entire data block. bytes read: " + read + "/" + totalDataSizeLocked);
                                try {
                                    dataInputStream.close();
                                } catch (IOException unused3) {
                                    Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                                }
                                return null;
                            }
                        } catch (IOException e) {
                            Slog.e(PersistentDataBlockService.TAG, "failed to read data", e);
                            try {
                                dataInputStream.close();
                            } catch (IOException unused4) {
                                Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                            }
                            return null;
                        }
                    } catch (Throwable th) {
                        try {
                            dataInputStream.close();
                        } catch (IOException unused5) {
                            Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e2) {
                    Slog.e(PersistentDataBlockService.TAG, "partition not available?", e2);
                    return null;
                }
            }

            public void wipe() {
                PersistentDataBlockService.this.enforceOemUnlockWritePermission();
                synchronized (PersistentDataBlockService.this.mLock) {
                    PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
                    if (persistentDataBlockService.nativeWipe(persistentDataBlockService.mDataBlockFile) < 0) {
                        Slog.e(PersistentDataBlockService.TAG, "failed to wipe persistent partition");
                    } else {
                        PersistentDataBlockService.this.mIsWritable = false;
                        Slog.i(PersistentDataBlockService.TAG, "persistent partition now wiped and unwritable");
                    }
                }
            }

            public void setOemUnlockEnabled(boolean z) {
                if (ActivityManager.isUserAMonkey()) {
                    return;
                }
                PersistentDataBlockService.this.enforceOemUnlockWritePermission();
                PersistentDataBlockService.this.enforceIsAdmin();
                if (z) {
                    PersistentDataBlockService.this.enforceUserRestriction("no_oem_unlock");
                    PersistentDataBlockService.this.enforceUserRestriction("no_factory_reset");
                }
                synchronized (PersistentDataBlockService.this.mLock) {
                    PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
                    PersistentDataBlockService.this.computeAndWriteDigestLocked();
                }
            }

            public boolean getOemUnlockEnabled() {
                PersistentDataBlockService.this.enforceOemUnlockReadPermission();
                return PersistentDataBlockService.this.doGetOemUnlockEnabled();
            }

            public int getFlashLockState() {
                PersistentDataBlockService.this.enforceOemUnlockReadPermission();
                String str = SystemProperties.get("ro.boot.flash.locked");
                str.hashCode();
                if (str.equals("0")) {
                    return 0;
                }
                return !str.equals("1") ? -1 : 1;
            }

            public int getDataBlockSize() {
                int totalDataSizeLocked;
                enforcePersistentDataBlockAccess();
                try {
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        synchronized (PersistentDataBlockService.this.mLock) {
                            totalDataSizeLocked = PersistentDataBlockService.this.getTotalDataSizeLocked(dataInputStream);
                        }
                        return totalDataSizeLocked;
                    } catch (IOException unused) {
                        Slog.e(PersistentDataBlockService.TAG, "error reading data block size");
                        return 0;
                    } finally {
                        IoUtils.closeQuietly(dataInputStream);
                    }
                } catch (FileNotFoundException unused2) {
                    Slog.e(PersistentDataBlockService.TAG, "partition not available");
                    return 0;
                }
            }

            public final void enforcePersistentDataBlockAccess() {
                if (PersistentDataBlockService.this.mContext.checkCallingPermission("android.permission.ACCESS_PDB_STATE") != 0) {
                    PersistentDataBlockService.this.enforceUid(Binder.getCallingUid());
                }
            }

            public long getMaximumDataBlockSize() {
                PersistentDataBlockService.this.enforceUid(Binder.getCallingUid());
                return PersistentDataBlockService.this.doGetMaximumDataBlockSize();
            }

            public boolean isEnabled() {
                int callingUid = Binder.getCallingUid();
                if (callingUid != 1000 && callingUid != 1001) {
                    Slog.e(PersistentDataBlockService.TAG, "uid " + Binder.getCallingUid() + " not allowed to access PST");
                    return false;
                }
                if (!PersistentDataBlockService.this.enforceChecksumValidity()) {
                    return false;
                }
                try {
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        try {
                            synchronized (PersistentDataBlockService.this.mLock) {
                                boolean oemUnlockEnabled = getOemUnlockEnabled();
                                if (PersistentDataBlockService.this.getTotalDataSizeLocked(dataInputStream) <= 0 || oemUnlockEnabled) {
                                    try {
                                        dataInputStream.close();
                                    } catch (IOException unused) {
                                        Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                                    }
                                    return false;
                                }
                                try {
                                    dataInputStream.close();
                                    return true;
                                } catch (IOException unused2) {
                                    Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                                    return true;
                                }
                            }
                        } catch (Throwable th) {
                            try {
                                dataInputStream.close();
                            } catch (IOException unused3) {
                                Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        Slog.e(PersistentDataBlockService.TAG, "failed to read statues", e);
                        try {
                            dataInputStream.close();
                        } catch (IOException unused4) {
                            Slog.e(PersistentDataBlockService.TAG, "failed to close OutputStream");
                        }
                        return false;
                    }
                } catch (FileNotFoundException e2) {
                    Slog.e(PersistentDataBlockService.TAG, "partition not available?", e2);
                    return false;
                }
            }

            public boolean hasFrpCredentialHandle() {
                enforcePersistentDataBlockAccess();
                try {
                    return PersistentDataBlockService.this.mInternalService.getFrpCredentialHandle() != null;
                } catch (IllegalStateException e) {
                    Slog.e(PersistentDataBlockService.TAG, "error reading frp handle", e);
                    throw new UnsupportedOperationException("cannot read frp credential");
                }
            }

            public String getPersistentDataPackageName() {
                enforcePersistentDataBlockAccess();
                return PersistentDataBlockService.this.mContext.getString(R.string.global_action_assist);
            }

            public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                if (DumpUtils.checkDumpPermission(PersistentDataBlockService.this.mContext, PersistentDataBlockService.TAG, printWriter)) {
                    printWriter.println("mDataBlockFile: " + PersistentDataBlockService.this.mDataBlockFile);
                    printWriter.println("mIsRunningDSU: " + PersistentDataBlockService.this.mIsRunningDSU);
                    printWriter.println("mInitDoneSignal: " + PersistentDataBlockService.this.mInitDoneSignal);
                    printWriter.println("mAllowedUid: " + PersistentDataBlockService.this.mAllowedUid);
                    printWriter.println("mBlockDeviceSize: " + PersistentDataBlockService.this.mBlockDeviceSize);
                    synchronized (PersistentDataBlockService.this.mLock) {
                        printWriter.println("mIsWritable: " + PersistentDataBlockService.this.mIsWritable);
                    }
                }
            }
        };
        this.mInternalService = new PersistentDataBlockManagerInternal() { // from class: com.android.server.PersistentDataBlockService.2
            @Override // com.android.server.PersistentDataBlockManagerInternal
            public void setFrpCredentialHandle(byte[] bArr) {
                writeInternal(bArr, PersistentDataBlockService.this.getFrpCredentialDataOffset(), 996);
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public byte[] getFrpCredentialHandle() {
                return readInternal(PersistentDataBlockService.this.getFrpCredentialDataOffset(), 996);
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public void setTestHarnessModeData(byte[] bArr) {
                writeInternal(bArr, PersistentDataBlockService.this.getTestHarnessModeDataOffset(), 9996);
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public byte[] getTestHarnessModeData() {
                byte[] readInternal = readInternal(PersistentDataBlockService.this.getTestHarnessModeDataOffset(), 9996);
                return readInternal == null ? new byte[0] : readInternal;
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public void clearTestHarnessModeData() {
                writeDataBuffer(PersistentDataBlockService.this.getTestHarnessModeDataOffset(), ByteBuffer.allocate(Math.min(9996, getTestHarnessModeData().length) + 4));
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public int getAllowedUid() {
                return PersistentDataBlockService.this.mAllowedUid;
            }

            public final void writeInternal(byte[] bArr, long j, int i) {
                boolean z = true;
                Preconditions.checkArgument(bArr == null || bArr.length > 0, "data must be null or non-empty");
                if (bArr != null && bArr.length > i) {
                    z = false;
                }
                Preconditions.checkArgument(z, "data must not be longer than " + i);
                ByteBuffer allocate = ByteBuffer.allocate(i + 4);
                allocate.putInt(bArr != null ? bArr.length : 0);
                if (bArr != null) {
                    allocate.put(bArr);
                }
                allocate.flip();
                writeDataBuffer(j, allocate);
            }

            public final void writeDataBuffer(long j, ByteBuffer byteBuffer) {
                synchronized (PersistentDataBlockService.this.mLock) {
                    if (PersistentDataBlockService.this.mIsWritable) {
                        try {
                            FileChannel blockOutputChannel = PersistentDataBlockService.this.getBlockOutputChannel();
                            blockOutputChannel.position(j);
                            blockOutputChannel.write(byteBuffer);
                            blockOutputChannel.force(true);
                            PersistentDataBlockService.this.computeAndWriteDigestLocked();
                        } catch (IOException e) {
                            Slog.e(PersistentDataBlockService.TAG, "unable to access persistent partition", e);
                        }
                    }
                }
            }

            public final byte[] readInternal(long j, int i) {
                if (!PersistentDataBlockService.this.enforceChecksumValidity()) {
                    throw new IllegalStateException("invalid checksum");
                }
                try {
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        try {
                            synchronized (PersistentDataBlockService.this.mLock) {
                                dataInputStream.skip(j);
                                int readInt = dataInputStream.readInt();
                                if (readInt > 0 && readInt <= i) {
                                    byte[] bArr = new byte[readInt];
                                    dataInputStream.readFully(bArr);
                                    return bArr;
                                }
                                IoUtils.closeQuietly(dataInputStream);
                                return null;
                            }
                        } catch (IOException e) {
                            throw new IllegalStateException("persistent partition not readable", e);
                        }
                    } finally {
                        IoUtils.closeQuietly(dataInputStream);
                    }
                } catch (FileNotFoundException unused) {
                    throw new IllegalStateException("persistent partition not available");
                }
            }

            @Override // com.android.server.PersistentDataBlockManagerInternal
            public void forceOemUnlockEnabled(boolean z) {
                synchronized (PersistentDataBlockService.this.mLock) {
                    PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
                    PersistentDataBlockService.this.computeAndWriteDigestLocked();
                }
            }
        };
        this.mContext = context;
        boolean z = SystemProperties.getBoolean("ro.gsid.image_running", false);
        this.mIsRunningDSU = z;
        if (z) {
            this.mDataBlockFile = "/data/gsi_persistent_data";
        } else {
            this.mDataBlockFile = SystemProperties.get("ro.frp.pst");
        }
        this.mBlockDeviceSize = -1L;
    }

    public final int getAllowedUid() {
        int mainUserId = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getMainUserId();
        if (mainUserId < 0) {
            return -1;
        }
        String string = this.mContext.getResources().getString(R.string.global_action_assist);
        if (TextUtils.isEmpty(string)) {
            return -1;
        }
        try {
            return this.mContext.getPackageManager().getPackageUidAsUser(string, 1048576, mainUserId);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "not able to find package " + string, e);
            return -1;
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.PersistentDataBlockService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PersistentDataBlockService.this.lambda$onStart$0();
            }
        }, TAG + ".onStart");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0() {
        enforceChecksumValidity();
        formatIfOemUnlockEnabled();
        publishBinderService("persistent_data_block", this.mService);
        this.mInitDoneSignal.countDown();
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            try {
                if (!this.mInitDoneSignal.await(10L, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Service " + TAG + " init timeout");
                }
                this.mAllowedUid = getAllowedUid();
                LocalServices.addService(PersistentDataBlockManagerInternal.class, this.mInternalService);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Service " + TAG + " init interrupted", e);
            }
        }
        super.onBootPhase(i);
    }

    public final void formatIfOemUnlockEnabled() {
        boolean doGetOemUnlockEnabled = doGetOemUnlockEnabled();
        if (doGetOemUnlockEnabled) {
            synchronized (this.mLock) {
                formatPartitionLocked(true);
            }
        }
        SystemProperties.set("sys.oem_unlock_allowed", doGetOemUnlockEnabled ? "1" : "0");
    }

    public final void enforceOemUnlockReadPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_OEM_UNLOCK_STATE") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.OEM_UNLOCK_STATE") == -1) {
            throw new SecurityException("Can't access OEM unlock state. Requires READ_OEM_UNLOCK_STATE or OEM_UNLOCK_STATE permission.");
        }
    }

    public final void enforceOemUnlockWritePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.OEM_UNLOCK_STATE", "Can't modify OEM unlock state");
    }

    public final void enforceUid(int i) {
        if (i == this.mAllowedUid) {
            return;
        }
        throw new SecurityException("uid " + i + " not allowed to access PST");
    }

    public final void enforceIsAdmin() {
        if (!UserManager.get(this.mContext).isUserAdmin(UserHandle.getCallingUserId())) {
            throw new SecurityException("Only the Admin user is allowed to change OEM unlock state");
        }
    }

    public final void enforceUserRestriction(String str) {
        if (UserManager.get(this.mContext).hasUserRestriction(str)) {
            throw new SecurityException("OEM unlock is disallowed by user restriction: " + str);
        }
    }

    public final int getTotalDataSizeLocked(DataInputStream dataInputStream) {
        dataInputStream.skipBytes(32);
        if (dataInputStream.readInt() == 428873843) {
            return dataInputStream.readInt();
        }
        return 0;
    }

    public final long getBlockDeviceSize() {
        synchronized (this.mLock) {
            if (this.mBlockDeviceSize == -1) {
                if (this.mIsRunningDSU) {
                    this.mBlockDeviceSize = 102400L;
                } else {
                    this.mBlockDeviceSize = nativeGetBlockDeviceSize(this.mDataBlockFile);
                }
            }
        }
        return this.mBlockDeviceSize;
    }

    public final long getFrpCredentialDataOffset() {
        return (getBlockDeviceSize() - 1) - 1000;
    }

    public final long getTestHarnessModeDataOffset() {
        return getFrpCredentialDataOffset() - 10000;
    }

    public final boolean enforceChecksumValidity() {
        byte[] bArr = new byte[32];
        synchronized (this.mLock) {
            byte[] computeDigestLocked = computeDigestLocked(bArr);
            if (computeDigestLocked != null && Arrays.equals(bArr, computeDigestLocked)) {
                return true;
            }
            Slog.i(TAG, "Formatting FRP partition...");
            formatPartitionLocked(false);
            return false;
        }
    }

    public final FileChannel getBlockOutputChannel() {
        return new RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
    }

    public final boolean computeAndWriteDigestLocked() {
        byte[] computeDigestLocked = computeDigestLocked(null);
        if (computeDigestLocked != null) {
            try {
                FileChannel blockOutputChannel = getBlockOutputChannel();
                try {
                    ByteBuffer allocate = ByteBuffer.allocate(32);
                    allocate.put(computeDigestLocked);
                    allocate.flip();
                    blockOutputChannel.write(allocate);
                    blockOutputChannel.force(true);
                    return true;
                } catch (IOException e) {
                    Slog.e(TAG, "failed to write block checksum", e);
                    return false;
                }
            } catch (IOException e2) {
                Slog.e(TAG, "partition not available?", e2);
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035 A[Catch: all -> 0x0041, IOException -> 0x0043, LOOP:0: B:11:0x002e->B:13:0x0035, LOOP_END, Merged into TryCatch #0 {all -> 0x0041, IOException -> 0x0043, blocks: (B:24:0x001c, B:26:0x001f, B:10:0x0026, B:11:0x002e, B:13:0x0035, B:9:0x0023, B:20:0x0044), top: B:7:0x001a }, TRY_LEAVE] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0039 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] computeDigestLocked(byte[] r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch: java.io.FileNotFoundException -> L5f
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> L5f
            java.io.File r3 = new java.io.File     // Catch: java.io.FileNotFoundException -> L5f
            java.lang.String r5 = r5.mDataBlockFile     // Catch: java.io.FileNotFoundException -> L5f
            r3.<init>(r5)     // Catch: java.io.FileNotFoundException -> L5f
            r2.<init>(r3)     // Catch: java.io.FileNotFoundException -> L5f
            r1.<init>(r2)     // Catch: java.io.FileNotFoundException -> L5f
            java.lang.String r5 = "SHA-256"
            java.security.MessageDigest r5 = java.security.MessageDigest.getInstance(r5)     // Catch: java.security.NoSuchAlgorithmException -> L53
            r2 = 32
            if (r6 == 0) goto L23
            int r3 = r6.length     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
            if (r3 != r2) goto L23
            r1.read(r6)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
            goto L26
        L23:
            r1.skipBytes(r2)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
        L26:
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
            r3 = 0
            r5.update(r6, r3, r2)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
        L2e:
            int r2 = r1.read(r6)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
            r4 = -1
            if (r2 == r4) goto L39
            r5.update(r6, r3, r2)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L43
            goto L2e
        L39:
            libcore.io.IoUtils.closeQuietly(r1)
            byte[] r5 = r5.digest()
            return r5
        L41:
            r5 = move-exception
            goto L4f
        L43:
            r5 = move-exception
            java.lang.String r6 = com.android.server.PersistentDataBlockService.TAG     // Catch: java.lang.Throwable -> L41
            java.lang.String r2 = "failed to read partition"
            android.util.Slog.e(r6, r2, r5)     // Catch: java.lang.Throwable -> L41
            libcore.io.IoUtils.closeQuietly(r1)
            return r0
        L4f:
            libcore.io.IoUtils.closeQuietly(r1)
            throw r5
        L53:
            r5 = move-exception
            java.lang.String r6 = com.android.server.PersistentDataBlockService.TAG
            java.lang.String r2 = "SHA-256 not supported?"
            android.util.Slog.e(r6, r2, r5)
            libcore.io.IoUtils.closeQuietly(r1)
            return r0
        L5f:
            r5 = move-exception
            java.lang.String r6 = com.android.server.PersistentDataBlockService.TAG
            java.lang.String r1 = "partition not available?"
            android.util.Slog.e(r6, r1, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.PersistentDataBlockService.computeDigestLocked(byte[]):byte[]");
    }

    public final void formatPartitionLocked(boolean z) {
        try {
            FileChannel blockOutputChannel = getBlockOutputChannel();
            ByteBuffer allocate = ByteBuffer.allocate(40);
            allocate.put(new byte[32]);
            allocate.putInt(428873843);
            allocate.putInt(0);
            allocate.flip();
            blockOutputChannel.write(allocate);
            blockOutputChannel.force(true);
            blockOutputChannel.write(ByteBuffer.allocate((((((int) getBlockDeviceSize()) - 40) - 10000) - 1000) - 1));
            blockOutputChannel.force(true);
            blockOutputChannel.position(blockOutputChannel.position() + 10000);
            blockOutputChannel.write(ByteBuffer.allocate(1000));
            blockOutputChannel.force(true);
            ByteBuffer allocate2 = ByteBuffer.allocate(1000);
            allocate2.put((byte) 0);
            allocate2.flip();
            blockOutputChannel.write(allocate2);
            blockOutputChannel.force(true);
            doSetOemUnlockEnabledLocked(z);
            computeAndWriteDigestLocked();
        } catch (IOException e) {
            Slog.e(TAG, "failed to format block", e);
        }
    }

    public final void doSetOemUnlockEnabledLocked(boolean z) {
        try {
            try {
                FileChannel blockOutputChannel = getBlockOutputChannel();
                blockOutputChannel.position(getBlockDeviceSize() - 1);
                ByteBuffer allocate = ByteBuffer.allocate(1);
                allocate.put(z ? (byte) 1 : (byte) 0);
                allocate.flip();
                blockOutputChannel.write(allocate);
                blockOutputChannel.force(true);
                SystemProperties.set("sys.oem_unlock_allowed", z ? "1" : "0");
            } catch (IOException e) {
                Slog.e(TAG, "unable to access persistent partition", e);
                SystemProperties.set("sys.oem_unlock_allowed", z ? "1" : "0");
            }
        } catch (Throwable th) {
            SystemProperties.set("sys.oem_unlock_allowed", z ? "1" : "0");
            throw th;
        }
    }

    public final boolean doGetOemUnlockEnabled() {
        boolean z;
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(this.mDataBlockFile)));
            try {
                synchronized (this.mLock) {
                    dataInputStream.skip(getBlockDeviceSize() - 1);
                    z = dataInputStream.readByte() != 0;
                }
                return z;
            } catch (IOException e) {
                Slog.e(TAG, "unable to access persistent partition", e);
                return false;
            } finally {
                IoUtils.closeQuietly(dataInputStream);
            }
        } catch (FileNotFoundException unused) {
            Slog.e(TAG, "partition not available");
            return false;
        }
    }

    public final long doGetMaximumDataBlockSize() {
        long blockDeviceSize = ((((getBlockDeviceSize() - 8) - 32) - 10000) - 1000) - 1;
        if (blockDeviceSize <= 102400) {
            return blockDeviceSize;
        }
        return 102400L;
    }
}

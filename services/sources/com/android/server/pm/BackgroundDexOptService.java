package com.android.server.pm;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.BatteryManagerInternal;
import android.os.Binder;
import android.os.Environment;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.PinnerService;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.pm.Installer;
import com.android.server.pm.dex.ArtStatsLogUtils;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.utils.TimingsTraceAndSlog;
import com.samsung.android.server.pm.dexopt.BgDexOptUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class BackgroundDexOptService {
    static final int JOB_IDLE_OPTIMIZE = 800;
    static final int JOB_POST_BOOT_UPDATE = 801;
    public Thread mDexOptCancellingThread;
    public final DexOptHelper mDexOptHelper;
    public Thread mDexOptThread;
    public boolean mDisableJobSchedulerJobs;
    public final long mDowngradeUnusedAppsThresholdInMillis;
    public final ArraySet mFailedPackageNamesPrimary;
    public final ArraySet mFailedPackageNamesSecondary;
    public boolean mFinishedPostBootUpdate;
    public final Injector mInjector;
    public final ArraySet mLastCancelledPackages;
    public long mLastExecutionDurationMs;
    public long mLastExecutionStartUptimeMs;
    public int mLastExecutionStatus;
    public final Object mLock;
    public List mPackagesUpdatedListeners;
    public final ArtStatsLogUtils.BackgroundDexoptJobStatsLogger mStatsLogger;
    public int mThermalStatusCutoff;
    public static final boolean DEBUG = Log.isLoggable("BackgroundDexOptService", 3);
    public static final long IDLE_OPTIMIZATION_PERIOD = TimeUnit.DAYS.toMillis(1);
    public static ComponentName sDexoptServiceName = new ComponentName("android", BackgroundDexOptJobService.class.getName());

    public BackgroundDexOptService(Context context, DexManager dexManager, PackageManagerService packageManagerService) {
        this(new Injector(context, dexManager, packageManagerService));
    }

    public BackgroundDexOptService(Injector injector) {
        this.mStatsLogger = new ArtStatsLogUtils.BackgroundDexoptJobStatsLogger();
        this.mLock = new Object();
        this.mLastExecutionStatus = -1;
        this.mLastCancelledPackages = new ArraySet();
        this.mFailedPackageNamesPrimary = new ArraySet();
        this.mFailedPackageNamesSecondary = new ArraySet();
        this.mPackagesUpdatedListeners = new ArrayList();
        this.mThermalStatusCutoff = 2;
        Installer.checkLegacyDexoptDisabled();
        this.mInjector = injector;
        this.mDexOptHelper = injector.getDexOptHelper();
        LocalServices.addService(BackgroundDexOptService.class, this);
        this.mDowngradeUnusedAppsThresholdInMillis = injector.getDowngradeUnusedAppsThresholdInMillis();
    }

    public void systemReady() {
        Installer.checkLegacyDexoptDisabled();
        if (this.mInjector.isBackgroundDexOptDisabled()) {
            return;
        }
        this.mInjector.getContext().registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.BackgroundDexOptService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                BackgroundDexOptService.this.mInjector.getContext().unregisterReceiver(this);
                BackgroundDexOptService.this.scheduleAJob(BackgroundDexOptService.JOB_POST_BOOT_UPDATE);
                BackgroundDexOptService.this.scheduleAJob(BackgroundDexOptService.JOB_IDLE_OPTIMIZE);
                if (BackgroundDexOptService.DEBUG) {
                    Slog.d("BackgroundDexOptService", "BootBgDexopt scheduled");
                }
            }
        }, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        boolean isBackgroundDexOptDisabled = this.mInjector.isBackgroundDexOptDisabled();
        indentingPrintWriter.print("enabled:");
        indentingPrintWriter.println(!isBackgroundDexOptDisabled);
        if (isBackgroundDexOptDisabled) {
            return;
        }
        synchronized (this.mLock) {
            indentingPrintWriter.print("mDexOptThread:");
            indentingPrintWriter.println(this.mDexOptThread);
            indentingPrintWriter.print("mDexOptCancellingThread:");
            indentingPrintWriter.println(this.mDexOptCancellingThread);
            indentingPrintWriter.print("mFinishedPostBootUpdate:");
            indentingPrintWriter.println(this.mFinishedPostBootUpdate);
            indentingPrintWriter.print("mDisableJobSchedulerJobs:");
            indentingPrintWriter.println(this.mDisableJobSchedulerJobs);
            indentingPrintWriter.print("mLastExecutionStatus:");
            indentingPrintWriter.println(this.mLastExecutionStatus);
            indentingPrintWriter.print("mLastExecutionStartUptimeMs:");
            indentingPrintWriter.println(this.mLastExecutionStartUptimeMs);
            indentingPrintWriter.print("mLastExecutionDurationMs:");
            indentingPrintWriter.println(this.mLastExecutionDurationMs);
            indentingPrintWriter.print("now:");
            indentingPrintWriter.println(SystemClock.elapsedRealtime());
            indentingPrintWriter.print("mLastCancelledPackages:");
            indentingPrintWriter.println(String.join(",", this.mLastCancelledPackages));
            indentingPrintWriter.print("mFailedPackageNamesPrimary:");
            indentingPrintWriter.println(String.join(",", this.mFailedPackageNamesPrimary));
            indentingPrintWriter.print("mFailedPackageNamesSecondary:");
            indentingPrintWriter.println(String.join(",", this.mFailedPackageNamesSecondary));
        }
    }

    public static BackgroundDexOptService getService() {
        return (BackgroundDexOptService) LocalServices.getService(BackgroundDexOptService.class);
    }

    public boolean runBackgroundDexoptJob(List list) {
        enforceRootOrShell();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                waitForDexOptThreadToFinishLocked();
                resetStatesForNewDexOptRunLocked(Thread.currentThread());
            }
            PackageManagerService packageManagerService = this.mInjector.getPackageManagerService();
            if (list == null) {
                list = this.mDexOptHelper.getOptimizablePackages(packageManagerService.snapshotComputer());
            }
            return runIdleOptimization(packageManagerService, list, false);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            markDexOptCompleted();
        }
    }

    public void cancelBackgroundDexoptJob() {
        Installer.checkLegacyDexoptDisabled();
        enforceRootOrShell();
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                BackgroundDexOptService.this.lambda$cancelBackgroundDexoptJob$0();
            }
        });
    }

    public void setDisableJobSchedulerJobs(boolean z) {
        Installer.checkLegacyDexoptDisabled();
        enforceRootOrShell();
        synchronized (this.mLock) {
            this.mDisableJobSchedulerJobs = z;
        }
    }

    public void notifyPackageChanged(String str) {
        Installer.checkLegacyDexoptDisabled();
        synchronized (this.mLock) {
            this.mFailedPackageNamesPrimary.remove(str);
            this.mFailedPackageNamesSecondary.remove(str);
        }
    }

    public boolean onStartJob(final BackgroundDexOptJobService backgroundDexOptJobService, final JobParameters jobParameters) {
        Slog.i("BackgroundDexOptService", "onStartJob:" + jobParameters.getJobId());
        boolean z = jobParameters.getJobId() == JOB_POST_BOOT_UPDATE;
        final PackageManagerService packageManagerService = this.mInjector.getPackageManagerService();
        if (packageManagerService.isStorageLow()) {
            Slog.w("BackgroundDexOptService", "Low storage, skipping this run");
            markPostBootUpdateCompleted(jobParameters);
            return false;
        }
        final List optimizablePackages = this.mDexOptHelper.getOptimizablePackages(packageManagerService.snapshotComputer());
        if (optimizablePackages.isEmpty()) {
            Slog.i("BackgroundDexOptService", "No packages to optimize");
            markPostBootUpdateCompleted(jobParameters);
            return false;
        }
        this.mThermalStatusCutoff = this.mInjector.getDexOptThermalCutoff();
        synchronized (this.mLock) {
            if (this.mDisableJobSchedulerJobs) {
                Slog.i("BackgroundDexOptService", "JobScheduler invocations disabled");
                return false;
            }
            Thread thread = this.mDexOptThread;
            if (thread != null && thread.isAlive()) {
                return false;
            }
            if (!z && !this.mFinishedPostBootUpdate) {
                return false;
            }
            try {
                Injector injector = this.mInjector;
                StringBuilder sb = new StringBuilder();
                sb.append("BackgroundDexOptService_");
                sb.append(z ? "PostBoot" : "Idle");
                resetStatesForNewDexOptRunLocked(injector.createAndStartThread(sb.toString(), new Runnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BackgroundDexOptService.this.lambda$onStartJob$1(packageManagerService, optimizablePackages, jobParameters, backgroundDexOptJobService);
                    }
                }));
            } catch (Installer.LegacyDexoptDisabledException e) {
                Slog.wtf("BackgroundDexOptService", e);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b7  */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$onStartJob$1(com.android.server.pm.PackageManagerService r9, java.util.List r10, android.app.job.JobParameters r11, com.android.server.pm.BackgroundDexOptJobService r12) {
        /*
            r8 = this;
            java.lang.String r0 = " completed:"
            java.lang.String r1 = "dexopt finishing. jobid:"
            com.android.server.utils.TimingsTraceAndSlog r2 = new com.android.server.utils.TimingsTraceAndSlog
            r3 = 16384(0x4000, double:8.0948E-320)
            java.lang.String r5 = "BackgroundDexOptService"
            r2.<init>(r5, r3)
            java.lang.String r3 = "jobExecution"
            r2.traceBegin(r3)
            r3 = 801(0x321, float:1.122E-42)
            r4 = 1
            r6 = 0
            int r7 = r11.getJobId()     // Catch: java.lang.Throwable -> L55 java.lang.RuntimeException -> L58 com.android.server.pm.Installer.LegacyDexoptDisabledException -> L5d
            if (r7 != r3) goto L1e
            r7 = r4
            goto L1f
        L1e:
            r7 = r6
        L1f:
            boolean r9 = r8.runIdleOptimization(r9, r10, r7)     // Catch: java.lang.Throwable -> L55 java.lang.RuntimeException -> L58 com.android.server.pm.Installer.LegacyDexoptDisabledException -> L5d
            r2.traceEnd()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r1)
            int r1 = r11.getJobId()
            r10.append(r1)
            r10.append(r0)
            r10.append(r9)
            java.lang.String r10 = r10.toString()
            android.util.Slog.i(r5, r10)
            r8.writeStatsLog(r11)
            int r10 = r11.getJobId()
            if (r10 != r3) goto L50
            if (r9 == 0) goto L50
            r8.markPostBootUpdateCompleted(r11)
        L50:
            if (r9 != 0) goto L53
            goto L87
        L53:
            r4 = r6
            goto L87
        L55:
            r9 = move-exception
            r10 = r6
            goto L8e
        L58:
            r9 = move-exception
            throw r9     // Catch: java.lang.Throwable -> L5a
        L5a:
            r9 = move-exception
            r10 = r4
            goto L8e
        L5d:
            r9 = move-exception
            android.util.Slog.wtf(r5, r9)     // Catch: java.lang.Throwable -> L55
            r2.traceEnd()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r1)
            int r10 = r11.getJobId()
            r9.append(r10)
            r9.append(r0)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            android.util.Slog.i(r5, r9)
            r8.writeStatsLog(r11)
            int r9 = r11.getJobId()
        L87:
            r12.jobFinished(r11, r4)
            r8.markDexOptCompleted()
            return
        L8e:
            r2.traceEnd()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            int r1 = r11.getJobId()
            r2.append(r1)
            r2.append(r0)
            r2.append(r6)
            java.lang.String r0 = r2.toString()
            android.util.Slog.i(r5, r0)
            r8.writeStatsLog(r11)
            int r0 = r11.getJobId()
            if (r10 != 0) goto Lb7
            goto Lb8
        Lb7:
            r4 = r6
        Lb8:
            r12.jobFinished(r11, r4)
            r8.markDexOptCompleted()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.BackgroundDexOptService.lambda$onStartJob$1(com.android.server.pm.PackageManagerService, java.util.List, android.app.job.JobParameters, com.android.server.pm.BackgroundDexOptJobService):void");
    }

    public boolean onStopJob(BackgroundDexOptJobService backgroundDexOptJobService, JobParameters jobParameters) {
        Slog.i("BackgroundDexOptService", "onStopJob:" + jobParameters.getJobId());
        this.mInjector.createAndStartThread("DexOptCancel", new Runnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BackgroundDexOptService.this.lambda$onStopJob$2();
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStopJob$2() {
        try {
            lambda$cancelBackgroundDexoptJob$0();
        } catch (Installer.LegacyDexoptDisabledException e) {
            Slog.wtf("BackgroundDexOptService", e);
        }
    }

    /* renamed from: cancelDexOptAndWaitForCompletion, reason: merged with bridge method [inline-methods] */
    public final void lambda$cancelBackgroundDexoptJob$0() {
        synchronized (this.mLock) {
            if (this.mDexOptThread == null) {
                return;
            }
            Thread thread = this.mDexOptCancellingThread;
            if (thread != null && thread.isAlive()) {
                waitForDexOptThreadToFinishLocked();
                return;
            }
            this.mDexOptCancellingThread = Thread.currentThread();
            try {
                controlDexOptBlockingLocked(true);
                waitForDexOptThreadToFinishLocked();
            } finally {
                this.mDexOptCancellingThread = null;
                this.mDexOptThread = null;
                controlDexOptBlockingLocked(false);
                this.mLock.notifyAll();
            }
        }
    }

    public final void waitForDexOptThreadToFinishLocked() {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("BackgroundDexOptService", 262144L);
        timingsTraceAndSlog.traceBegin("waitForDexOptThreadToFinishLocked");
        while (true) {
            try {
                Thread thread = this.mDexOptThread;
                if (thread == null || !thread.isAlive()) {
                    break;
                } else {
                    this.mLock.wait(200L);
                }
            } catch (InterruptedException unused) {
                Slog.w("BackgroundDexOptService", "Interrupted while waiting for dexopt thread");
                Thread.currentThread().interrupt();
            }
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void markDexOptCompleted() {
        synchronized (this.mLock) {
            if (this.mDexOptThread != Thread.currentThread()) {
                throw new IllegalStateException("Only mDexOptThread can mark completion, mDexOptThread:" + this.mDexOptThread + " current:" + Thread.currentThread());
            }
            this.mDexOptThread = null;
            this.mLock.notifyAll();
        }
    }

    public final void resetStatesForNewDexOptRunLocked(Thread thread) {
        this.mDexOptThread = thread;
        this.mLastCancelledPackages.clear();
        controlDexOptBlockingLocked(false);
    }

    public final void enforceRootOrShell() {
        int callingUid = this.mInjector.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            throw new SecurityException("Should be shell or root user");
        }
    }

    public final void controlDexOptBlockingLocked(boolean z) {
        this.mInjector.getPackageManagerService();
        this.mDexOptHelper.controlDexOptBlocking(z);
    }

    public final void scheduleAJob(int i) {
        JobScheduler jobScheduler = this.mInjector.getJobScheduler();
        JobInfo.Builder requiresDeviceIdle = new JobInfo.Builder(i, sDexoptServiceName).setRequiresDeviceIdle(true);
        if (i == JOB_IDLE_OPTIMIZE) {
            requiresDeviceIdle.setRequiresCharging(true).setPeriodic(IDLE_OPTIMIZATION_PERIOD);
        }
        jobScheduler.schedule(requiresDeviceIdle.build());
    }

    public final long getLowStorageThreshold() {
        long dataDirStorageLowBytes = this.mInjector.getDataDirStorageLowBytes();
        if (dataDirStorageLowBytes == 0) {
            Slog.e("BackgroundDexOptService", "Invalid low storage threshold");
        }
        return dataDirStorageLowBytes;
    }

    public final void logStatus(int i) {
        if (i == 0) {
            Slog.i("BackgroundDexOptService", "Idle optimizations completed.");
            return;
        }
        if (i == 1) {
            Slog.w("BackgroundDexOptService", "Idle optimizations aborted by cancellation.");
            return;
        }
        if (i == 2) {
            Slog.w("BackgroundDexOptService", "Idle optimizations aborted because of space constraints.");
            return;
        }
        if (i == 3) {
            Slog.w("BackgroundDexOptService", "Idle optimizations aborted by thermal throttling.");
            return;
        }
        if (i == 4) {
            Slog.w("BackgroundDexOptService", "Idle optimizations aborted by low battery.");
            return;
        }
        if (i == 5) {
            Slog.w("BackgroundDexOptService", "Idle optimizations failed from dexopt.");
            return;
        }
        Slog.w("BackgroundDexOptService", "Idle optimizations ended with unexpected code: " + i);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean runIdleOptimization(com.android.server.pm.PackageManagerService r11, java.util.List r12, boolean r13) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            r1 = -1
            r10.mLastExecutionStatus = r1     // Catch: java.lang.Throwable -> L55
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L55
            r10.mLastExecutionStartUptimeMs = r2     // Catch: java.lang.Throwable -> L55
            r2 = -1
            r10.mLastExecutionDurationMs = r2     // Catch: java.lang.Throwable -> L55
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L55
            long r7 = r10.getLowStorageThreshold()     // Catch: java.lang.Throwable -> L3c java.lang.RuntimeException -> L3e
            r4 = r10
            r5 = r11
            r6 = r12
            r9 = r13
            int r1 = r4.idleOptimizePackages(r5, r6, r7, r9)     // Catch: java.lang.Throwable -> L3c java.lang.RuntimeException -> L3e
            r10.logStatus(r1)     // Catch: java.lang.Throwable -> L3c java.lang.RuntimeException -> L3e
            if (r1 == 0) goto L28
            r11 = 5
            if (r1 != r11) goto L26
            goto L28
        L26:
            r11 = 0
            goto L29
        L28:
            r11 = 1
        L29:
            java.lang.Object r12 = r10.mLock
            monitor-enter(r12)
            r10.mLastExecutionStatus = r1     // Catch: java.lang.Throwable -> L39
            long r0 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L39
            long r2 = r10.mLastExecutionStartUptimeMs     // Catch: java.lang.Throwable -> L39
            long r0 = r0 - r2
            r10.mLastExecutionDurationMs = r0     // Catch: java.lang.Throwable -> L39
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L39
            return r11
        L39:
            r10 = move-exception
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L39
            throw r10
        L3c:
            r11 = move-exception
            goto L42
        L3e:
            r11 = move-exception
            throw r11     // Catch: java.lang.Throwable -> L40
        L40:
            r11 = move-exception
            r1 = 6
        L42:
            java.lang.Object r12 = r10.mLock
            monitor-enter(r12)
            r10.mLastExecutionStatus = r1     // Catch: java.lang.Throwable -> L52
            long r0 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L52
            long r2 = r10.mLastExecutionStartUptimeMs     // Catch: java.lang.Throwable -> L52
            long r0 = r0 - r2
            r10.mLastExecutionDurationMs = r0     // Catch: java.lang.Throwable -> L52
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L52
            throw r11
        L52:
            r10 = move-exception
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L52
            throw r10
        L55:
            r10 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L55
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.BackgroundDexOptService.runIdleOptimization(com.android.server.pm.PackageManagerService, java.util.List, boolean):boolean");
    }

    public final long getDirectorySize(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            long j = 0;
            if (listFiles == null || listFiles.length == 0) {
                return 0L;
            }
            for (File file2 : listFiles) {
                j += getDirectorySize(file2);
            }
            return j;
        }
        return file.length();
    }

    public final long getPackageSize(Computer computer, String str) {
        ApplicationInfo applicationInfo;
        long j = 0;
        PackageInfo packageInfo = computer.getPackageInfo(str, 0L, 0);
        if (packageInfo != null && (applicationInfo = packageInfo.applicationInfo) != null) {
            File file = Paths.get(applicationInfo.sourceDir, new String[0]).toFile();
            if (file.isFile()) {
                file = file.getParentFile();
            }
            j = 0 + getDirectorySize(file);
            if (!ArrayUtils.isEmpty(packageInfo.applicationInfo.splitSourceDirs)) {
                for (String str2 : packageInfo.applicationInfo.splitSourceDirs) {
                    File file2 = Paths.get(str2, new String[0]).toFile();
                    if (file2.isFile()) {
                        file2 = file2.getParentFile();
                    }
                    if (!file.getAbsolutePath().equals(file2.getAbsolutePath())) {
                        j += getDirectorySize(file2);
                    }
                }
            }
        }
        return j;
    }

    public final int idleOptimizePackages(PackageManagerService packageManagerService, List list, long j, boolean z) {
        List list2;
        int abortIdleOptimizations;
        ArraySet arraySet = new ArraySet();
        if (!BgDexOptUtils.isBatteryFullyCharged(this.mInjector.getContext())) {
            PackageManagerServiceUtils.logCriticalInfo(4, "isCharging: " + this.mInjector.isCharging() + ", isPostBootUpdate: " + z);
            if ((z && this.mInjector.isCharging()) || !z) {
                PackageManagerServiceUtils.logCriticalInfo(4, "Aborted by not full charge. batteryLevel: " + BgDexOptUtils.getBatteryLevel());
                return 4;
            }
        }
        try {
            boolean supportSecondaryDex = this.mInjector.supportSecondaryDex();
            if (!supportSecondaryDex || (abortIdleOptimizations = reconcileSecondaryDexFiles()) == 0) {
                boolean shouldDowngrade = shouldDowngrade(2 * j);
                boolean z2 = DEBUG;
                if (z2) {
                    Slog.d("BackgroundDexOptService", "Should Downgrade " + shouldDowngrade);
                }
                if (shouldDowngrade) {
                    Computer snapshotComputer = packageManagerService.snapshotComputer();
                    Set<String> unusedPackages = snapshotComputer.getUnusedPackages(this.mDowngradeUnusedAppsThresholdInMillis);
                    if (z2) {
                        Slog.d("BackgroundDexOptService", "Unsused Packages " + String.join(",", unusedPackages));
                    }
                    if (!unusedPackages.isEmpty()) {
                        for (String str : unusedPackages) {
                            abortIdleOptimizations = abortIdleOptimizations(-1L);
                            if (abortIdleOptimizations == 0) {
                                int downgradePackage = downgradePackage(snapshotComputer, packageManagerService, str, true, z);
                                if (downgradePackage == 1) {
                                    arraySet.add(str);
                                }
                                abortIdleOptimizations = convertPackageDexOptimizerStatusToInternal(downgradePackage);
                                if (abortIdleOptimizations == 0 && (!supportSecondaryDex || (abortIdleOptimizations = convertPackageDexOptimizerStatusToInternal(downgradePackage(snapshotComputer, packageManagerService, str, false, z))) == 0)) {
                                }
                            }
                        }
                        ArrayList arrayList = new ArrayList(list);
                        arrayList.removeAll(unusedPackages);
                        list2 = arrayList;
                        return optimizePackages(list2, j, arraySet, z);
                    }
                }
                list2 = list;
                return optimizePackages(list2, j, arraySet, z);
            }
            return abortIdleOptimizations;
        } finally {
            notifyPinService(arraySet);
            notifyPackagesUpdated(arraySet);
        }
    }

    public final int optimizePackages(List list, long j, ArraySet arraySet, boolean z) {
        boolean supportSecondaryDex = this.mInjector.supportSecondaryDex();
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            int abortIdleOptimizations = abortIdleOptimizations(j);
            if (abortIdleOptimizations != 0) {
                return abortIdleOptimizations;
            }
            int optimizePackage = optimizePackage(str, true, z);
            if (optimizePackage == 2) {
                return 1;
            }
            if (optimizePackage == 1) {
                arraySet.add(str);
            } else if (optimizePackage == -1) {
                i = convertPackageDexOptimizerStatusToInternal(optimizePackage);
            }
            if (supportSecondaryDex) {
                int optimizePackage2 = optimizePackage(str, false, z);
                if (optimizePackage2 == 2) {
                    return 1;
                }
                if (optimizePackage2 == -1) {
                    i = convertPackageDexOptimizerStatusToInternal(optimizePackage2);
                }
            }
        }
        return i;
    }

    public final int downgradePackage(Computer computer, PackageManagerService packageManagerService, String str, boolean z, boolean z2) {
        int performDexOptPrimary;
        if (DEBUG) {
            Slog.d("BackgroundDexOptService", "Downgrading " + str);
        }
        if (isCancelling()) {
            return 2;
        }
        String compilerFilterForReason = PackageManagerServiceCompilerMapping.getCompilerFilterForReason(11);
        int i = DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason) ? 37 : 36;
        if (!z2) {
            i |= 512;
        }
        long packageSize = getPackageSize(computer, str);
        if (z || "android".equals(str)) {
            if (!packageManagerService.canHaveOatDir(computer, str)) {
                packageManagerService.deleteOatArtifactsOfPackage(computer, str);
                performDexOptPrimary = 0;
            } else {
                performDexOptPrimary = performDexOptPrimary(str, 11, compilerFilterForReason, i);
            }
        } else {
            performDexOptPrimary = performDexOptSecondary(str, 11, compilerFilterForReason, i);
        }
        if (performDexOptPrimary == 1) {
            FrameworkStatsLog.write(128, str, packageSize, getPackageSize(packageManagerService.snapshotComputer(), str), false);
        }
        return performDexOptPrimary;
    }

    public final int reconcileSecondaryDexFiles() {
        for (String str : this.mInjector.getDexManager().getAllPackagesWithSecondaryDexFiles()) {
            if (isCancelling()) {
                return 1;
            }
            this.mInjector.getDexManager().reconcileSecondaryDexFiles(str);
        }
        return 0;
    }

    public final int optimizePackage(String str, boolean z, boolean z2) {
        int i = z2 ? 2 : 9;
        String compilerFilterForReason = PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i);
        int i2 = !z2 ? FrameworkStatsLog.RESOURCE_API_INFO : 4;
        if (DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason)) {
            i2 |= 1;
        }
        if (z || "android".equals(str)) {
            return performDexOptPrimary(str, i, compilerFilterForReason, i2);
        }
        return performDexOptSecondary(str, i, compilerFilterForReason, i2);
    }

    public final int performDexOptPrimary(String str, int i, String str2, int i2) {
        final DexoptOptions dexoptOptions = new DexoptOptions(str, i, str2, null, i2);
        return trackPerformDexOpt(str, true, new FunctionalUtils.ThrowingCheckedSupplier() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda3
            public final Object get() {
                Integer lambda$performDexOptPrimary$3;
                lambda$performDexOptPrimary$3 = BackgroundDexOptService.this.lambda$performDexOptPrimary$3(dexoptOptions);
                return lambda$performDexOptPrimary$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$performDexOptPrimary$3(DexoptOptions dexoptOptions) {
        return Integer.valueOf(this.mDexOptHelper.performDexOptWithStatus(dexoptOptions));
    }

    public final int performDexOptSecondary(String str, int i, String str2, int i2) {
        final DexoptOptions dexoptOptions = new DexoptOptions(str, i, str2, null, i2 | 8);
        return trackPerformDexOpt(str, false, new FunctionalUtils.ThrowingCheckedSupplier() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda4
            public final Object get() {
                Integer lambda$performDexOptSecondary$4;
                lambda$performDexOptSecondary$4 = BackgroundDexOptService.this.lambda$performDexOptSecondary$4(dexoptOptions);
                return lambda$performDexOptSecondary$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$performDexOptSecondary$4(DexoptOptions dexoptOptions) {
        return Integer.valueOf(this.mDexOptHelper.performDexOpt(dexoptOptions) ? 1 : -1);
    }

    public final int trackPerformDexOpt(String str, boolean z, FunctionalUtils.ThrowingCheckedSupplier throwingCheckedSupplier) {
        synchronized (this.mLock) {
            ArraySet arraySet = z ? this.mFailedPackageNamesPrimary : this.mFailedPackageNamesSecondary;
            if (arraySet.contains(str)) {
                return 0;
            }
            int intValue = ((Integer) throwingCheckedSupplier.get()).intValue();
            if (intValue == -1) {
                synchronized (this.mLock) {
                    arraySet.add(str);
                }
            } else if (intValue == 2) {
                synchronized (this.mLock) {
                    this.mLastCancelledPackages.add(str);
                }
            }
            return intValue;
        }
    }

    public final int convertPackageDexOptimizerStatusToInternal(int i) {
        if (i == -1) {
            return 5;
        }
        if (i == 0 || i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        Slog.e("BackgroundDexOptService", "Unkknown error code from PackageDexOptimizer:" + i, new RuntimeException());
        return 5;
    }

    public final int abortIdleOptimizations(long j) {
        if (isCancelling()) {
            return 1;
        }
        int currentThermalStatus = this.mInjector.getCurrentThermalStatus();
        if (DEBUG) {
            Log.d("BackgroundDexOptService", "Thermal throttling status during bgdexopt: " + currentThermalStatus);
        }
        if (currentThermalStatus >= this.mThermalStatusCutoff) {
            PackageManagerServiceUtils.logCriticalInfo(4, "Aborted by thermal: " + currentThermalStatus);
            if (!BgDexOptUtils.isRunningBgDexOptCTS(this.mInjector.getContext())) {
                return 3;
            }
            PackageManagerServiceUtils.logCriticalInfo(4, "Keep running optimizations");
        }
        if (this.mInjector.isBatteryLevelLow()) {
            return 4;
        }
        long dataDirUsableSpace = this.mInjector.getDataDirUsableSpace();
        if (dataDirUsableSpace >= j) {
            return 0;
        }
        Slog.w("BackgroundDexOptService", "Aborting background dex opt job due to low storage: " + dataDirUsableSpace);
        return 2;
    }

    public final boolean shouldDowngrade(long j) {
        return this.mInjector.getDataDirUsableSpace() < j;
    }

    public final boolean isCancelling() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDexOptCancellingThread != null;
        }
        return z;
    }

    public final void markPostBootUpdateCompleted(JobParameters jobParameters) {
        if (jobParameters.getJobId() != JOB_POST_BOOT_UPDATE) {
            return;
        }
        synchronized (this.mLock) {
            if (!this.mFinishedPostBootUpdate) {
                this.mFinishedPostBootUpdate = true;
            }
        }
        this.mInjector.getJobScheduler().cancel(JOB_POST_BOOT_UPDATE);
    }

    public final void notifyPinService(ArraySet arraySet) {
        PinnerService pinnerService = this.mInjector.getPinnerService();
        if (pinnerService != null) {
            Slog.i("BackgroundDexOptService", "Pinning optimized code " + arraySet);
            pinnerService.update(arraySet, false);
        }
    }

    public final void notifyPackagesUpdated(ArraySet arraySet) {
        synchronized (this.mLock) {
            Iterator it = this.mPackagesUpdatedListeners.iterator();
            if (it.hasNext()) {
                CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }
    }

    public final void writeStatsLog(JobParameters jobParameters) {
        int i;
        long j;
        synchronized (this.mLock) {
            i = this.mLastExecutionStatus;
            j = this.mLastExecutionDurationMs;
        }
        this.mStatsLogger.write(i, jobParameters.getStopReason(), j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class Injector {
        public final Context mContext;
        public final File mDataDir = Environment.getDataDirectory();
        public final DexManager mDexManager;
        public final PackageManagerService mPackageManagerService;

        public Injector(Context context, DexManager dexManager, PackageManagerService packageManagerService) {
            this.mContext = context;
            this.mDexManager = dexManager;
            this.mPackageManagerService = packageManagerService;
        }

        public int getCallingUid() {
            return Binder.getCallingUid();
        }

        public Context getContext() {
            return this.mContext;
        }

        public PackageManagerService getPackageManagerService() {
            return this.mPackageManagerService;
        }

        public DexOptHelper getDexOptHelper() {
            return new DexOptHelper(getPackageManagerService());
        }

        public JobScheduler getJobScheduler() {
            return (JobScheduler) this.mContext.getSystemService(JobScheduler.class);
        }

        public DexManager getDexManager() {
            return this.mDexManager;
        }

        public PinnerService getPinnerService() {
            return (PinnerService) LocalServices.getService(PinnerService.class);
        }

        public boolean isBackgroundDexOptDisabled() {
            return SystemProperties.getBoolean("pm.dexopt.disable_bg_dexopt", false);
        }

        public boolean isBatteryLevelLow() {
            return ((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class)).getBatteryLevelLow();
        }

        public boolean isCharging() {
            return ((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class)).getPlugType() != 0;
        }

        public long getDowngradeUnusedAppsThresholdInMillis() {
            String str = SystemProperties.get("pm.dexopt.downgrade_after_inactive_days");
            if (str == null || str.isEmpty()) {
                Slog.w("BackgroundDexOptService", "SysProp pm.dexopt.downgrade_after_inactive_days not set");
                return Long.MAX_VALUE;
            }
            return TimeUnit.DAYS.toMillis(Long.parseLong(str));
        }

        public boolean supportSecondaryDex() {
            return SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false);
        }

        public long getDataDirUsableSpace() {
            return this.mDataDir.getUsableSpace();
        }

        public long getDataDirStorageLowBytes() {
            return ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getStorageLowBytes(this.mDataDir);
        }

        public int getCurrentThermalStatus() {
            try {
                return IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice")).getCurrentThermalStatus();
            } catch (RemoteException unused) {
                return 3;
            }
        }

        public int getDexOptThermalCutoff() {
            return SystemProperties.getInt("dalvik.vm.dexopt.thermal-cutoff", 2);
        }

        public Thread createAndStartThread(String str, Runnable runnable) {
            Thread thread = new Thread(runnable, str);
            Slog.i("BackgroundDexOptService", "Starting thread:" + str);
            thread.start();
            return thread;
        }
    }
}

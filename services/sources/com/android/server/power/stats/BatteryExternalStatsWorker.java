package com.android.server.power.stats;

import android.content.Context;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.SynchronousResultReceiver;
import android.os.SystemClock;
import android.os.ThreadLocalWorkSource;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.power.PowerStatsInternal;
import android.telephony.TelephonyManager;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.power.stats.BatteryStatsImpl;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class BatteryExternalStatsWorker implements BatteryStatsImpl.ExternalStatsSync {
    public Future mBatteryLevelSync;
    public Future mCurrentFuture;
    public String mCurrentReason;
    public EnergyConsumerSnapshot mEnergyConsumerSnapshot;
    public SparseArray mEnergyConsumerTypeToIdMap;
    public final ScheduledExecutorService mExecutorService;
    public final Injector mInjector;
    public long mLastCollectionTimeStamp;
    public WifiActivityEnergyInfo mLastWifiInfo;
    public boolean mOnBattery;
    public boolean mOnBatteryScreenOff;
    public int[] mPerDisplayScreenStates;
    public PowerStatsInternal mPowerStatsInternal;
    public Future mProcessStateSync;
    public int mScreenState;
    public final BatteryStatsImpl mStats;
    public final Runnable mSyncTask;
    public TelephonyManager mTelephony;
    public final IntArray mUidsToRemove;
    public int mUpdateFlags;
    public boolean mUseLatestStates;
    public Future mWakelockChangesUpdate;
    public WifiManager mWifiManager;
    public final Object mWorkerLock;
    public final Runnable mWriteTask;

    public static /* synthetic */ Thread lambda$new$1(final Runnable runnable) {
        Thread thread = new Thread(new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BatteryExternalStatsWorker.lambda$new$0(runnable);
            }
        }, "batterystats-worker");
        thread.setPriority(5);
        return thread;
    }

    public static /* synthetic */ void lambda$new$0(Runnable runnable) {
        ThreadLocalWorkSource.setUid(Process.myUid());
        runnable.run();
    }

    /* loaded from: classes3.dex */
    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public Object getSystemService(Class cls) {
            return this.mContext.getSystemService(cls);
        }

        public Object getLocalService(Class cls) {
            return LocalServices.getService(cls);
        }
    }

    public BatteryExternalStatsWorker(Context context, BatteryStatsImpl batteryStatsImpl) {
        this(new Injector(context), batteryStatsImpl);
    }

    public BatteryExternalStatsWorker(Injector injector, BatteryStatsImpl batteryStatsImpl) {
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread lambda$new$1;
                lambda$new$1 = BatteryExternalStatsWorker.lambda$new$1(runnable);
                return lambda$new$1;
            }
        });
        this.mUpdateFlags = 0;
        this.mCurrentFuture = null;
        this.mCurrentReason = null;
        this.mPerDisplayScreenStates = null;
        this.mUseLatestStates = true;
        this.mUidsToRemove = new IntArray();
        this.mWorkerLock = new Object();
        this.mWifiManager = null;
        this.mTelephony = null;
        this.mPowerStatsInternal = null;
        this.mLastWifiInfo = new WifiActivityEnergyInfo(0L, 0, 0L, 0L, 0L, 0L);
        this.mEnergyConsumerTypeToIdMap = null;
        this.mEnergyConsumerSnapshot = null;
        this.mSyncTask = new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.1
            @Override // java.lang.Runnable
            public void run() {
                int i;
                String str;
                int[] array;
                boolean z;
                boolean z2;
                int i2;
                int[] iArr;
                boolean z3;
                int i3;
                int i4;
                synchronized (BatteryExternalStatsWorker.this) {
                    i = BatteryExternalStatsWorker.this.mUpdateFlags;
                    str = BatteryExternalStatsWorker.this.mCurrentReason;
                    array = BatteryExternalStatsWorker.this.mUidsToRemove.size() > 0 ? BatteryExternalStatsWorker.this.mUidsToRemove.toArray() : EmptyArray.INT;
                    z = BatteryExternalStatsWorker.this.mOnBattery;
                    z2 = BatteryExternalStatsWorker.this.mOnBatteryScreenOff;
                    i2 = BatteryExternalStatsWorker.this.mScreenState;
                    iArr = BatteryExternalStatsWorker.this.mPerDisplayScreenStates;
                    z3 = BatteryExternalStatsWorker.this.mUseLatestStates;
                    BatteryExternalStatsWorker.this.mUpdateFlags = 0;
                    BatteryExternalStatsWorker.this.mCurrentReason = null;
                    BatteryExternalStatsWorker.this.mUidsToRemove.clear();
                    BatteryExternalStatsWorker.this.mCurrentFuture = null;
                    BatteryExternalStatsWorker.this.mUseLatestStates = true;
                    i3 = i & 127;
                    if (i3 == 127) {
                        BatteryExternalStatsWorker.this.cancelSyncDueToBatteryLevelChangeLocked();
                    }
                    i4 = i & 1;
                    if (i4 != 0) {
                        BatteryExternalStatsWorker.this.cancelCpuSyncDueToWakelockChange();
                    }
                    if ((i & 14) == 14) {
                        BatteryExternalStatsWorker.this.cancelSyncDueToProcessStateChange();
                    }
                }
                try {
                    synchronized (BatteryExternalStatsWorker.this.mWorkerLock) {
                        BatteryExternalStatsWorker.this.updateExternalStatsLocked(str, i, z, z2, i2, iArr, z3);
                    }
                    if (i4 != 0) {
                        BatteryExternalStatsWorker.this.mStats.updateCpuTimesForAllUids();
                    }
                    synchronized (BatteryExternalStatsWorker.this.mStats) {
                        for (int i5 : array) {
                            FrameworkStatsLog.write(43, -1, i5, 0);
                            BatteryExternalStatsWorker.this.mStats.maybeRemoveIsolatedUidLocked(i5, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis());
                        }
                        BatteryExternalStatsWorker.this.mStats.clearPendingRemovedUidsLocked();
                    }
                } catch (Exception e) {
                    Slog.wtf("BatteryExternalStatsWorker", "Error updating external stats: ", e);
                }
                if ((i & 128) != 0) {
                    synchronized (BatteryExternalStatsWorker.this) {
                        BatteryExternalStatsWorker.this.mLastCollectionTimeStamp = 0L;
                    }
                } else if (i3 == 127) {
                    synchronized (BatteryExternalStatsWorker.this) {
                        BatteryExternalStatsWorker.this.mLastCollectionTimeStamp = SystemClock.elapsedRealtime();
                    }
                }
            }
        };
        this.mWriteTask = new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (BatteryExternalStatsWorker.this.mStats) {
                    BatteryExternalStatsWorker.this.mStats.writeAsyncLocked();
                }
            }
        };
        this.mInjector = injector;
        this.mStats = batteryStatsImpl;
    }

    public void systemServicesReady() {
        int batteryVoltageMvLocked;
        String[] strArr;
        boolean[] zArr;
        SparseArray populateEnergyConsumerSubsystemMapsLocked;
        WifiManager wifiManager = (WifiManager) this.mInjector.getSystemService(WifiManager.class);
        TelephonyManager telephonyManager = (TelephonyManager) this.mInjector.getSystemService(TelephonyManager.class);
        PowerStatsInternal powerStatsInternal = (PowerStatsInternal) this.mInjector.getLocalService(PowerStatsInternal.class);
        synchronized (this.mStats) {
            batteryVoltageMvLocked = this.mStats.getBatteryVoltageMvLocked();
        }
        synchronized (this.mWorkerLock) {
            this.mWifiManager = wifiManager;
            this.mTelephony = telephonyManager;
            this.mPowerStatsInternal = powerStatsInternal;
            if (powerStatsInternal == null || (populateEnergyConsumerSubsystemMapsLocked = populateEnergyConsumerSubsystemMapsLocked()) == null) {
                strArr = null;
                zArr = null;
            } else {
                this.mEnergyConsumerSnapshot = new EnergyConsumerSnapshot(populateEnergyConsumerSubsystemMapsLocked);
                try {
                    this.mEnergyConsumerSnapshot.updateAndGetDelta((EnergyConsumerResult[]) getEnergyConsumptionData().get(2000L, TimeUnit.MILLISECONDS), batteryVoltageMvLocked);
                } catch (InterruptedException | TimeoutException e) {
                    Slog.w("BatteryExternalStatsWorker", "timeout or interrupt reading initial getEnergyConsumedAsync: " + e);
                } catch (ExecutionException e2) {
                    Slog.wtf("BatteryExternalStatsWorker", "exception reading initial getEnergyConsumedAsync: " + e2.getCause());
                }
                strArr = this.mEnergyConsumerSnapshot.getOtherOrdinalNames();
                zArr = getSupportedEnergyBuckets(populateEnergyConsumerSubsystemMapsLocked);
            }
            synchronized (this.mStats) {
                this.mStats.initEnergyConsumerStatsLocked(zArr, strArr);
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public synchronized Future scheduleSync(String str, int i) {
        return scheduleSyncLocked(str, i);
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public synchronized Future scheduleCpuSyncDueToRemovedUid(int i) {
        this.mUidsToRemove.add(i);
        return scheduleSyncLocked("remove-uid", 1);
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public Future scheduleSyncDueToScreenStateChange(int i, boolean z, boolean z2, int i2, int[] iArr) {
        Future scheduleSyncLocked;
        synchronized (this) {
            if (this.mCurrentFuture == null || (this.mUpdateFlags & 1) == 0) {
                this.mOnBattery = z;
                this.mOnBatteryScreenOff = z2;
                this.mUseLatestStates = false;
            }
            this.mScreenState = i2;
            this.mPerDisplayScreenStates = iArr;
            scheduleSyncLocked = scheduleSyncLocked("screen-state", i);
        }
        return scheduleSyncLocked;
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public Future scheduleCpuSyncDueToWakelockChange(long j) {
        Future scheduleDelayedSyncLocked;
        synchronized (this) {
            scheduleDelayedSyncLocked = scheduleDelayedSyncLocked(this.mWakelockChangesUpdate, new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryExternalStatsWorker.this.lambda$scheduleCpuSyncDueToWakelockChange$3();
                }
            }, j);
            this.mWakelockChangesUpdate = scheduleDelayedSyncLocked;
        }
        return scheduleDelayedSyncLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCpuSyncDueToWakelockChange$3() {
        scheduleSync("wakelock-change", 1);
        scheduleRunnable(new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BatteryExternalStatsWorker.this.lambda$scheduleCpuSyncDueToWakelockChange$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCpuSyncDueToWakelockChange$2() {
        this.mStats.postBatteryNeedsCpuUpdateMsg();
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public void cancelCpuSyncDueToWakelockChange() {
        synchronized (this) {
            Future future = this.mWakelockChangesUpdate;
            if (future != null) {
                future.cancel(false);
                this.mWakelockChangesUpdate = null;
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public Future scheduleSyncDueToBatteryLevelChange(long j) {
        Future scheduleDelayedSyncLocked;
        synchronized (this) {
            scheduleDelayedSyncLocked = scheduleDelayedSyncLocked(this.mBatteryLevelSync, new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryExternalStatsWorker.this.lambda$scheduleSyncDueToBatteryLevelChange$4();
                }
            }, j);
            this.mBatteryLevelSync = scheduleDelayedSyncLocked;
        }
        return scheduleDelayedSyncLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSyncDueToBatteryLevelChange$4() {
        scheduleSync("battery-level", 127);
    }

    public final void cancelSyncDueToBatteryLevelChangeLocked() {
        Future future = this.mBatteryLevelSync;
        if (future != null) {
            future.cancel(false);
            this.mBatteryLevelSync = null;
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public void scheduleSyncDueToProcessStateChange(final int i, long j) {
        synchronized (this) {
            this.mProcessStateSync = scheduleDelayedSyncLocked(this.mProcessStateSync, new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryExternalStatsWorker.this.lambda$scheduleSyncDueToProcessStateChange$5(i);
                }
            }, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSyncDueToProcessStateChange$5(int i) {
        scheduleSync("procstate-change", i);
    }

    public void cancelSyncDueToProcessStateChange() {
        synchronized (this) {
            Future future = this.mProcessStateSync;
            if (future != null) {
                future.cancel(false);
                this.mProcessStateSync = null;
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public Future scheduleCleanupDueToRemovedUser(final int i) {
        ScheduledFuture<?> schedule;
        synchronized (this) {
            ScheduledExecutorService scheduledExecutorService = this.mExecutorService;
            Runnable runnable = new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryExternalStatsWorker.this.lambda$scheduleCleanupDueToRemovedUser$6(i);
                }
            };
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            scheduledExecutorService.schedule(runnable, 2000L, timeUnit);
            schedule = this.mExecutorService.schedule(new Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryExternalStatsWorker.this.lambda$scheduleCleanupDueToRemovedUser$7(i);
                }
            }, 10000L, timeUnit);
        }
        return schedule;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCleanupDueToRemovedUser$6(int i) {
        synchronized (this.mStats) {
            this.mStats.clearRemovedUserUidsLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCleanupDueToRemovedUser$7(int i) {
        synchronized (this.mStats) {
            this.mStats.clearRemovedUserUidsLocked(i);
        }
    }

    public final Future scheduleDelayedSyncLocked(Future future, Runnable runnable, long j) {
        if (this.mExecutorService.isShutdown()) {
            return CompletableFuture.failedFuture(new IllegalStateException("worker shutdown"));
        }
        if (future != null) {
            if (j != 0) {
                return future;
            }
            future.cancel(false);
        }
        return this.mExecutorService.schedule(runnable, j, TimeUnit.MILLISECONDS);
    }

    public synchronized Future scheduleWrite() {
        if (this.mExecutorService.isShutdown()) {
            return CompletableFuture.failedFuture(new IllegalStateException("worker shutdown"));
        }
        scheduleSyncLocked("write", 127);
        return this.mExecutorService.submit(this.mWriteTask);
    }

    public synchronized void scheduleRunnable(Runnable runnable) {
        if (!this.mExecutorService.isShutdown()) {
            this.mExecutorService.submit(runnable);
        }
    }

    public synchronized void shutdown() {
        this.mExecutorService.shutdownNow();
    }

    public final Future scheduleSyncLocked(String str, int i) {
        if (this.mExecutorService.isShutdown()) {
            return CompletableFuture.failedFuture(new IllegalStateException("worker shutdown"));
        }
        if (this.mCurrentFuture == null) {
            this.mUpdateFlags = i;
            this.mCurrentReason = str;
            this.mCurrentFuture = this.mExecutorService.submit(this.mSyncTask);
        }
        this.mUpdateFlags |= i;
        return this.mCurrentFuture;
    }

    public long getLastCollectionTimeStamp() {
        long j;
        synchronized (this) {
            j = this.mLastCollectionTimeStamp;
        }
        return j;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0136 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x019b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateExternalStatsLocked(java.lang.String r40, int r41, boolean r42, boolean r43, int r44, int[] r45, boolean r46) {
        /*
            Method dump skipped, instructions count: 854
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryExternalStatsWorker.updateExternalStatsLocked(java.lang.String, int, boolean, boolean, int, int[], boolean):void");
    }

    public static /* synthetic */ void lambda$updateExternalStatsLocked$8(SynchronousResultReceiver synchronousResultReceiver, WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("controller_activity", wifiActivityEnergyInfo);
        synchronousResultReceiver.send(0, bundle);
    }

    public static Parcelable awaitControllerInfo(SynchronousResultReceiver synchronousResultReceiver) {
        if (synchronousResultReceiver == null) {
            return null;
        }
        try {
            SynchronousResultReceiver.Result awaitResult = synchronousResultReceiver.awaitResult(2000L);
            Bundle bundle = awaitResult.bundle;
            if (bundle != null) {
                bundle.setDefusable(true);
                Parcelable parcelable = awaitResult.bundle.getParcelable("controller_activity");
                if (parcelable != null) {
                    return parcelable;
                }
            }
        } catch (TimeoutException unused) {
            Slog.w("BatteryExternalStatsWorker", "timeout reading " + synchronousResultReceiver.getName() + " stats");
        }
        return null;
    }

    public final WifiActivityEnergyInfo extractDeltaLocked(WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        boolean z;
        long timeSinceBootMillis = wifiActivityEnergyInfo.getTimeSinceBootMillis() - this.mLastWifiInfo.getTimeSinceBootMillis();
        long controllerScanDurationMillis = this.mLastWifiInfo.getControllerScanDurationMillis();
        long controllerIdleDurationMillis = this.mLastWifiInfo.getControllerIdleDurationMillis();
        long controllerTxDurationMillis = this.mLastWifiInfo.getControllerTxDurationMillis();
        long controllerRxDurationMillis = this.mLastWifiInfo.getControllerRxDurationMillis();
        long controllerEnergyUsedMicroJoules = this.mLastWifiInfo.getControllerEnergyUsedMicroJoules();
        long timeSinceBootMillis2 = wifiActivityEnergyInfo.getTimeSinceBootMillis();
        int stackState = wifiActivityEnergyInfo.getStackState();
        long controllerTxDurationMillis2 = wifiActivityEnergyInfo.getControllerTxDurationMillis() - controllerTxDurationMillis;
        long controllerRxDurationMillis2 = wifiActivityEnergyInfo.getControllerRxDurationMillis() - controllerRxDurationMillis;
        long controllerIdleDurationMillis2 = wifiActivityEnergyInfo.getControllerIdleDurationMillis() - controllerIdleDurationMillis;
        long controllerScanDurationMillis2 = wifiActivityEnergyInfo.getControllerScanDurationMillis() - controllerScanDurationMillis;
        long j9 = 0;
        if (controllerTxDurationMillis2 < 0 || controllerRxDurationMillis2 < 0 || controllerScanDurationMillis2 < 0 || controllerIdleDurationMillis2 < 0) {
            if (wifiActivityEnergyInfo.getControllerTxDurationMillis() + wifiActivityEnergyInfo.getControllerRxDurationMillis() + wifiActivityEnergyInfo.getControllerIdleDurationMillis() <= timeSinceBootMillis + 750) {
                long controllerEnergyUsedMicroJoules2 = wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules();
                j = wifiActivityEnergyInfo.getControllerRxDurationMillis();
                long controllerTxDurationMillis3 = wifiActivityEnergyInfo.getControllerTxDurationMillis();
                j3 = wifiActivityEnergyInfo.getControllerIdleDurationMillis();
                j4 = wifiActivityEnergyInfo.getControllerScanDurationMillis();
                j9 = controllerTxDurationMillis3;
                j2 = controllerEnergyUsedMicroJoules2;
            } else {
                j = 0;
                j2 = 0;
                j3 = 0;
                j4 = 0;
            }
            j5 = j;
            controllerTxDurationMillis2 = j9;
            j6 = j2;
            j7 = j3;
            j8 = j4;
            z = true;
        } else {
            z = false;
            j6 = Math.max(0L, wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules() - controllerEnergyUsedMicroJoules);
            j8 = controllerScanDurationMillis2;
            j5 = controllerRxDurationMillis2;
            j7 = controllerIdleDurationMillis2;
        }
        this.mLastWifiInfo = wifiActivityEnergyInfo;
        WifiActivityEnergyInfo wifiActivityEnergyInfo2 = new WifiActivityEnergyInfo(timeSinceBootMillis2, stackState, controllerTxDurationMillis2, j5, j8, j7, j6);
        if (z) {
            Slog.v("BatteryExternalStatsWorker", "WiFi energy data was reset, new WiFi energy data is " + wifiActivityEnergyInfo2);
        }
        return wifiActivityEnergyInfo2;
    }

    public static boolean[] getSupportedEnergyBuckets(SparseArray sparseArray) {
        if (sparseArray == null) {
            return null;
        }
        boolean[] zArr = new boolean[10];
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            switch (((EnergyConsumer) sparseArray.valueAt(i)).type) {
                case 1:
                    zArr[5] = true;
                    break;
                case 2:
                    zArr[3] = true;
                    break;
                case 3:
                    zArr[0] = true;
                    zArr[1] = true;
                    zArr[2] = true;
                    break;
                case 4:
                    zArr[6] = true;
                    break;
                case 5:
                    zArr[7] = true;
                    zArr[9] = true;
                    break;
                case 6:
                    zArr[4] = true;
                    break;
                case 7:
                    zArr[8] = true;
                    break;
            }
        }
        return zArr;
    }

    public final CompletableFuture getEnergyConsumptionData() {
        return getEnergyConsumptionData(new int[0]);
    }

    public final CompletableFuture getEnergyConsumptionData(int[] iArr) {
        return this.mPowerStatsInternal.getEnergyConsumedAsync(iArr);
    }

    public CompletableFuture getEnergyConsumersLocked(int i) {
        if (this.mEnergyConsumerSnapshot == null || this.mPowerStatsInternal == null) {
            return null;
        }
        if (i == 127) {
            return getEnergyConsumptionData();
        }
        IntArray intArray = new IntArray();
        if ((i & 8) != 0) {
            addEnergyConsumerIdLocked(intArray, 1);
        }
        if ((i & 1) != 0) {
            addEnergyConsumerIdLocked(intArray, 2);
        }
        if ((i & 32) != 0) {
            addEnergyConsumerIdLocked(intArray, 3);
        }
        if ((i & 4) != 0) {
            addEnergyConsumerIdLocked(intArray, 5);
        }
        if ((i & 2) != 0) {
            addEnergyConsumerIdLocked(intArray, 6);
        }
        if ((i & 64) != 0) {
            addEnergyConsumerIdLocked(intArray, 7);
        }
        if (intArray.size() == 0) {
            return null;
        }
        return getEnergyConsumptionData(intArray.toArray());
    }

    public final void addEnergyConsumerIdLocked(IntArray intArray, int i) {
        int[] iArr = (int[]) this.mEnergyConsumerTypeToIdMap.get(i);
        if (iArr == null) {
            return;
        }
        intArray.addAll(iArr);
    }

    public final SparseArray populateEnergyConsumerSubsystemMapsLocked() {
        byte b;
        PowerStatsInternal powerStatsInternal = this.mPowerStatsInternal;
        SparseArray sparseArray = null;
        if (powerStatsInternal == null) {
            return null;
        }
        EnergyConsumer[] energyConsumerInfo = powerStatsInternal.getEnergyConsumerInfo();
        if (energyConsumerInfo != null && energyConsumerInfo.length != 0) {
            sparseArray = new SparseArray(energyConsumerInfo.length);
            SparseArray sparseArray2 = new SparseArray();
            for (EnergyConsumer energyConsumer : energyConsumerInfo) {
                if (energyConsumer.ordinal == 0 || (b = energyConsumer.type) == 0 || b == 2 || b == 3) {
                    sparseArray.put(energyConsumer.id, energyConsumer);
                    IntArray intArray = (IntArray) sparseArray2.get(energyConsumer.type);
                    if (intArray == null) {
                        intArray = new IntArray();
                        sparseArray2.put(energyConsumer.type, intArray);
                    }
                    intArray.add(energyConsumer.id);
                } else {
                    Slog.w("BatteryExternalStatsWorker", "EnergyConsumer '" + energyConsumer.name + "' has unexpected ordinal " + energyConsumer.ordinal + " for type " + ((int) energyConsumer.type));
                }
            }
            this.mEnergyConsumerTypeToIdMap = new SparseArray(sparseArray2.size());
            int size = sparseArray2.size();
            for (int i = 0; i < size; i++) {
                this.mEnergyConsumerTypeToIdMap.put(sparseArray2.keyAt(i), ((IntArray) sparseArray2.valueAt(i)).toArray());
            }
        }
        return sparseArray;
    }
}

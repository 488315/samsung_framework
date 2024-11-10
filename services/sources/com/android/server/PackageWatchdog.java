package com.android.server;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.net.ConnectivityModuleConnector;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.service.watchdog.ExplicitHealthCheckService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.LongArrayQueue;
import android.util.MathUtils;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class PackageWatchdog {
    static final int DEFAULT_BOOT_LOOP_TRIGGER_COUNT = 5;
    public static final long DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS;
    static final long DEFAULT_DEESCALATION_WINDOW_MS;
    static final long DEFAULT_OBSERVING_DURATION_MS;
    static final int DEFAULT_TRIGGER_FAILURE_COUNT = 5;
    static final int DEFAULT_TRIGGER_FAILURE_DURATION_MS;
    public static final long NATIVE_CRASH_POLLING_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(30);
    public static PackageWatchdog sPackageWatchdog;
    public final ArrayMap mAllObservers;
    public final BootThreshold mBootThreshold;
    public final ConnectivityModuleConnector mConnectivityModuleConnector;
    public final Context mContext;
    public final ExplicitHealthCheckController mHealthCheckController;
    public boolean mIsHealthCheckEnabled;
    public boolean mIsPackagesReady;
    public final Object mLock;
    public final Handler mLongTaskHandler;
    public long mNumberOfNativeCrashPollsRemaining;
    public final DeviceConfig.OnPropertiesChangedListener mOnPropertyChangedListener;
    public final AtomicFile mPolicyFile;
    public Set mRequestedHealthCheckPackages;
    public final Runnable mSaveToFile;
    public final Handler mShortTaskHandler;
    public final Runnable mSyncRequests;
    public boolean mSyncRequired;
    public final Runnable mSyncStateWithScheduledReason;
    public final SystemClock mSystemClock;
    public int mTriggerFailureCount;
    public int mTriggerFailureDurationMs;
    public long mUptimeAtLastStateSync;

    /* loaded from: classes.dex */
    public interface PackageHealthObserver {
        boolean execute(VersionedPackage versionedPackage, int i, int i2);

        default boolean executeBootLoopMitigation(int i) {
            return false;
        }

        String getName();

        boolean isPersistent();

        boolean mayObservePackage(String str);

        default int onBootLoop(int i) {
            return 0;
        }

        int onHealthCheckFailed(VersionedPackage versionedPackage, int i, int i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @FunctionalInterface
    /* loaded from: classes.dex */
    public interface SystemClock {
        long uptimeMillis();
    }

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        DEFAULT_TRIGGER_FAILURE_DURATION_MS = (int) timeUnit.toMillis(1L);
        DEFAULT_OBSERVING_DURATION_MS = TimeUnit.DAYS.toMillis(2L);
        DEFAULT_DEESCALATION_WINDOW_MS = TimeUnit.HOURS.toMillis(1L);
        DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS = timeUnit.toMillis(10L);
    }

    public PackageWatchdog(Context context) {
        this(context, new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), "package-watchdog.xml")), new Handler(Looper.myLooper()), BackgroundThread.getHandler(), new ExplicitHealthCheckController(context), ConnectivityModuleConnector.getInstance(), new SystemClock() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda1
            @Override // com.android.server.PackageWatchdog.SystemClock
            public final long uptimeMillis() {
                return SystemClock.uptimeMillis();
            }
        });
    }

    public PackageWatchdog(Context context, AtomicFile atomicFile, Handler handler, Handler handler2, ExplicitHealthCheckController explicitHealthCheckController, ConnectivityModuleConnector connectivityModuleConnector, SystemClock systemClock) {
        this.mLock = new Object();
        this.mAllObservers = new ArrayMap();
        this.mSyncRequests = new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.syncRequests();
            }
        };
        this.mSyncStateWithScheduledReason = new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.syncStateWithScheduledReason();
            }
        };
        this.mSaveToFile = new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.saveToFile();
            }
        };
        this.mOnPropertyChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda5
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                PackageWatchdog.this.onPropertyChanged(properties);
            }
        };
        this.mRequestedHealthCheckPackages = new ArraySet();
        this.mIsHealthCheckEnabled = true;
        this.mTriggerFailureDurationMs = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
        this.mTriggerFailureCount = 5;
        this.mSyncRequired = false;
        this.mContext = context;
        this.mPolicyFile = atomicFile;
        this.mShortTaskHandler = handler;
        this.mLongTaskHandler = handler2;
        this.mHealthCheckController = explicitHealthCheckController;
        this.mConnectivityModuleConnector = connectivityModuleConnector;
        this.mSystemClock = systemClock;
        this.mNumberOfNativeCrashPollsRemaining = 10L;
        this.mBootThreshold = new BootThreshold(5, DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS);
        loadFromFile();
        sPackageWatchdog = this;
    }

    public static PackageWatchdog getInstance(Context context) {
        PackageWatchdog packageWatchdog;
        synchronized (PackageWatchdog.class) {
            if (sPackageWatchdog == null) {
                new PackageWatchdog(context);
            }
            packageWatchdog = sPackageWatchdog;
        }
        return packageWatchdog;
    }

    public void onPackagesReady() {
        synchronized (this.mLock) {
            this.mIsPackagesReady = true;
            this.mHealthCheckController.setCallbacks(new Consumer() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageWatchdog.this.lambda$onPackagesReady$0((String) obj);
                }
            }, new Consumer() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageWatchdog.this.lambda$onPackagesReady$1((List) obj);
                }
            }, new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    PackageWatchdog.this.onSyncRequestNotified();
                }
            });
            setPropertyChangedListenerLocked();
            updateConfigs();
            registerConnectivityModuleHealthListener();
        }
    }

    public void registerHealthObserver(PackageHealthObserver packageHealthObserver) {
        synchronized (this.mLock) {
            ObserverInternal observerInternal = (ObserverInternal) this.mAllObservers.get(packageHealthObserver.getName());
            if (observerInternal != null) {
                observerInternal.registeredObserver = packageHealthObserver;
            } else {
                ObserverInternal observerInternal2 = new ObserverInternal(packageHealthObserver.getName(), new ArrayList());
                observerInternal2.registeredObserver = packageHealthObserver;
                this.mAllObservers.put(packageHealthObserver.getName(), observerInternal2);
                syncState("added new observer");
            }
        }
    }

    public void startObservingHealth(final PackageHealthObserver packageHealthObserver, final List list, long j) {
        if (list.isEmpty()) {
            Slog.wtf("PackageWatchdog", "No packages to observe, " + packageHealthObserver.getName());
            return;
        }
        if (j < 1) {
            Slog.wtf("PackageWatchdog", "Invalid duration " + j + "ms for observer " + packageHealthObserver.getName() + ". Not observing packages " + list);
            j = DEFAULT_OBSERVING_DURATION_MS;
        }
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            MonitoredPackage newMonitoredPackage = newMonitoredPackage((String) list.get(i), j, false);
            if (newMonitoredPackage != null) {
                arrayList.add(newMonitoredPackage);
            } else {
                Slog.w("PackageWatchdog", "Failed to create MonitoredPackage for pkg=" + ((String) list.get(i)));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mLongTaskHandler.post(new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.lambda$startObservingHealth$2(packageHealthObserver, list, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObservingHealth$2(PackageHealthObserver packageHealthObserver, List list, List list2) {
        syncState("observing new packages");
        synchronized (this.mLock) {
            ObserverInternal observerInternal = (ObserverInternal) this.mAllObservers.get(packageHealthObserver.getName());
            if (observerInternal == null) {
                Slog.d("PackageWatchdog", packageHealthObserver.getName() + " started monitoring health of packages " + list);
                this.mAllObservers.put(packageHealthObserver.getName(), new ObserverInternal(packageHealthObserver.getName(), list2));
            } else {
                Slog.d("PackageWatchdog", packageHealthObserver.getName() + " added the following packages to monitor " + list);
                observerInternal.updatePackagesLocked(list2);
            }
        }
        registerHealthObserver(packageHealthObserver);
        syncState("updated observers");
    }

    public void onPackageFailure(final List list, final int i) {
        if (list == null) {
            Slog.w("PackageWatchdog", "Could not resolve a list of failing packages");
        } else {
            this.mLongTaskHandler.post(new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PackageWatchdog.this.lambda$onPackageFailure$4(i, list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x001a A[Catch: all -> 0x0085, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000b, B:16:0x001a, B:17:0x0083, B:20:0x0020, B:22:0x0026, B:23:0x0033, B:25:0x003b, B:27:0x0047, B:29:0x0051, B:31:0x005b, B:32:0x0062, B:37:0x006d, B:46:0x0074, B:47:0x007d, B:49:0x0080), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$onPackageFailure$4(int r13, java.util.List r14) {
        /*
            r12 = this;
            java.lang.Object r0 = r12.mLock
            monitor-enter(r0)
            android.util.ArrayMap r1 = r12.mAllObservers     // Catch: java.lang.Throwable -> L85
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L85
            if (r1 == 0) goto Ld
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L85
            return
        Ld:
            r1 = 0
            r2 = 1
            if (r13 == r2) goto L17
            r3 = 2
            if (r13 != r3) goto L15
            goto L17
        L15:
            r3 = r1
            goto L18
        L17:
            r3 = r2
        L18:
            if (r3 == 0) goto L1f
            r12.handleFailureImmediately(r14, r13)     // Catch: java.lang.Throwable -> L85
            goto L83
        L1f:
            r3 = r1
        L20:
            int r4 = r14.size()     // Catch: java.lang.Throwable -> L85
            if (r3 >= r4) goto L83
            java.lang.Object r4 = r14.get(r3)     // Catch: java.lang.Throwable -> L85
            android.content.pm.VersionedPackage r4 = (android.content.pm.VersionedPackage) r4     // Catch: java.lang.Throwable -> L85
            r5 = 0
            r6 = 2147483647(0x7fffffff, float:NaN)
            r8 = r1
            r7 = r6
            r6 = r5
        L33:
            android.util.ArrayMap r9 = r12.mAllObservers     // Catch: java.lang.Throwable -> L85
            int r9 = r9.size()     // Catch: java.lang.Throwable -> L85
            if (r8 >= r9) goto L70
            android.util.ArrayMap r9 = r12.mAllObservers     // Catch: java.lang.Throwable -> L85
            java.lang.Object r9 = r9.valueAt(r8)     // Catch: java.lang.Throwable -> L85
            com.android.server.PackageWatchdog$ObserverInternal r9 = (com.android.server.PackageWatchdog.ObserverInternal) r9     // Catch: java.lang.Throwable -> L85
            com.android.server.PackageWatchdog$PackageHealthObserver r10 = r9.registeredObserver     // Catch: java.lang.Throwable -> L85
            if (r10 == 0) goto L6d
            java.lang.String r11 = r4.getPackageName()     // Catch: java.lang.Throwable -> L85
            boolean r11 = r9.onPackageFailureLocked(r11)     // Catch: java.lang.Throwable -> L85
            if (r11 == 0) goto L6d
            java.lang.String r11 = r4.getPackageName()     // Catch: java.lang.Throwable -> L85
            com.android.server.PackageWatchdog$MonitoredPackage r9 = r9.getMonitoredPackage(r11)     // Catch: java.lang.Throwable -> L85
            if (r9 == 0) goto L61
            int r11 = r9.getMitigationCountLocked()     // Catch: java.lang.Throwable -> L85
            int r11 = r11 + r2
            goto L62
        L61:
            r11 = r2
        L62:
            int r11 = r10.onHealthCheckFailed(r4, r13, r11)     // Catch: java.lang.Throwable -> L85
            if (r11 == 0) goto L6d
            if (r11 >= r7) goto L6d
            r6 = r9
            r5 = r10
            r7 = r11
        L6d:
            int r8 = r8 + 1
            goto L33
        L70:
            if (r5 == 0) goto L80
            if (r6 == 0) goto L7c
            r6.noteMitigationCallLocked()     // Catch: java.lang.Throwable -> L85
            int r6 = r6.getMitigationCountLocked()     // Catch: java.lang.Throwable -> L85
            goto L7d
        L7c:
            r6 = r2
        L7d:
            r5.execute(r4, r13, r6)     // Catch: java.lang.Throwable -> L85
        L80:
            int r3 = r3 + 1
            goto L20
        L83:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L85
            return
        L85:
            r12 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L85
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.PackageWatchdog.lambda$onPackageFailure$4(int, java.util.List):void");
    }

    public final void handleFailureImmediately(List list, int i) {
        int onHealthCheckFailed;
        PackageHealthObserver packageHealthObserver = null;
        VersionedPackage versionedPackage = list.size() > 0 ? (VersionedPackage) list.get(0) : null;
        Iterator it = this.mAllObservers.values().iterator();
        int i2 = Integer.MAX_VALUE;
        while (it.hasNext()) {
            PackageHealthObserver packageHealthObserver2 = ((ObserverInternal) it.next()).registeredObserver;
            if (packageHealthObserver2 != null && (onHealthCheckFailed = packageHealthObserver2.onHealthCheckFailed(versionedPackage, i, 1)) != 0 && onHealthCheckFailed < i2) {
                packageHealthObserver = packageHealthObserver2;
                i2 = onHealthCheckFailed;
            }
        }
        if (packageHealthObserver != null) {
            packageHealthObserver.execute(versionedPackage, i, 1);
        }
    }

    public void noteBoot() {
        int onBootLoop;
        synchronized (this.mLock) {
            if (this.mBootThreshold.incrementAndTest()) {
                this.mBootThreshold.reset();
                int mitigationCount = this.mBootThreshold.getMitigationCount() + 1;
                PackageHealthObserver packageHealthObserver = null;
                int i = Integer.MAX_VALUE;
                for (int i2 = 0; i2 < this.mAllObservers.size(); i2++) {
                    PackageHealthObserver packageHealthObserver2 = ((ObserverInternal) this.mAllObservers.valueAt(i2)).registeredObserver;
                    if (packageHealthObserver2 != null && (onBootLoop = packageHealthObserver2.onBootLoop(mitigationCount)) != 0 && onBootLoop < i) {
                        packageHealthObserver = packageHealthObserver2;
                        i = onBootLoop;
                    }
                }
                if (packageHealthObserver != null) {
                    this.mBootThreshold.setMitigationCount(mitigationCount);
                    this.mBootThreshold.saveMitigationCountToMetadata();
                    packageHealthObserver.executeBootLoopMitigation(mitigationCount);
                }
            }
        }
    }

    public void writeNow() {
        synchronized (this.mLock) {
            if (!this.mAllObservers.isEmpty()) {
                this.mLongTaskHandler.removeCallbacks(this.mSaveToFile);
                pruneObserversLocked();
                saveToFile();
                Slog.i("PackageWatchdog", "Last write to update package durations");
            }
        }
    }

    public final void setExplicitHealthCheckEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mIsHealthCheckEnabled = z;
            this.mHealthCheckController.setEnabled(z);
            this.mSyncRequired = true;
            StringBuilder sb = new StringBuilder();
            sb.append("health check state ");
            sb.append(z ? "enabled" : "disabled");
            syncState(sb.toString());
        }
    }

    /* renamed from: checkAndMitigateNativeCrashes, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$scheduleCheckAndMitigateNativeCrashes$6() {
        this.mNumberOfNativeCrashPollsRemaining--;
        if ("1".equals(SystemProperties.get("sys.init.updatable_crashing"))) {
            onPackageFailure(Collections.EMPTY_LIST, 1);
        } else if (this.mNumberOfNativeCrashPollsRemaining > 0) {
            this.mShortTaskHandler.postDelayed(new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    PackageWatchdog.this.lambda$checkAndMitigateNativeCrashes$5();
                }
            }, NATIVE_CRASH_POLLING_INTERVAL_MILLIS);
        }
    }

    public void scheduleCheckAndMitigateNativeCrashes() {
        Slog.i("PackageWatchdog", "Scheduling " + this.mNumberOfNativeCrashPollsRemaining + " polls to check and mitigate native crashes");
        this.mShortTaskHandler.post(new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.lambda$scheduleCheckAndMitigateNativeCrashes$6();
            }
        });
    }

    public long getTriggerFailureCount() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureCount;
        }
        return j;
    }

    public long getTriggerFailureDurationMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureDurationMs;
        }
        return j;
    }

    public final void syncRequestsAsync() {
        this.mShortTaskHandler.removeCallbacks(this.mSyncRequests);
        this.mShortTaskHandler.post(this.mSyncRequests);
    }

    public final void syncRequests() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mIsPackagesReady) {
                Set packagesPendingHealthChecksLocked = getPackagesPendingHealthChecksLocked();
                if (this.mSyncRequired || !packagesPendingHealthChecksLocked.equals(this.mRequestedHealthCheckPackages) || packagesPendingHealthChecksLocked.isEmpty()) {
                    this.mRequestedHealthCheckPackages = packagesPendingHealthChecksLocked;
                    z = true;
                }
            }
            z = false;
        }
        if (z) {
            Slog.i("PackageWatchdog", "Syncing health check requests for packages: " + this.mRequestedHealthCheckPackages);
            this.mHealthCheckController.syncRequests(this.mRequestedHealthCheckPackages);
            this.mSyncRequired = false;
        }
    }

    /* renamed from: onHealthCheckPassed, reason: merged with bridge method [inline-methods] */
    public final void lambda$onPackagesReady$0(String str) {
        boolean z;
        Slog.i("PackageWatchdog", "Health check passed for package: " + str);
        synchronized (this.mLock) {
            z = false;
            for (int i = 0; i < this.mAllObservers.size(); i++) {
                MonitoredPackage monitoredPackage = ((ObserverInternal) this.mAllObservers.valueAt(i)).getMonitoredPackage(str);
                if (monitoredPackage != null) {
                    z |= monitoredPackage.getHealthCheckStateLocked() != monitoredPackage.tryPassHealthCheckLocked();
                }
            }
        }
        if (z) {
            syncState("health check passed for " + str);
        }
    }

    /* renamed from: onSupportedPackages, reason: merged with bridge method [inline-methods] */
    public final void lambda$onPackagesReady$1(List list) {
        boolean z;
        int tryPassHealthCheckLocked;
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ExplicitHealthCheckService.PackageConfig packageConfig = (ExplicitHealthCheckService.PackageConfig) it.next();
            arrayMap.put(packageConfig.getPackageName(), Long.valueOf(packageConfig.getHealthCheckTimeoutMillis()));
        }
        synchronized (this.mLock) {
            Slog.d("PackageWatchdog", "Received supported packages " + list);
            Iterator it2 = this.mAllObservers.values().iterator();
            z = false;
            while (it2.hasNext()) {
                for (MonitoredPackage monitoredPackage : ((ObserverInternal) it2.next()).getMonitoredPackages().values()) {
                    String name = monitoredPackage.getName();
                    int healthCheckStateLocked = monitoredPackage.getHealthCheckStateLocked();
                    if (arrayMap.containsKey(name)) {
                        tryPassHealthCheckLocked = monitoredPackage.setHealthCheckActiveLocked(((Long) arrayMap.get(name)).longValue());
                    } else {
                        tryPassHealthCheckLocked = monitoredPackage.tryPassHealthCheckLocked();
                    }
                    z |= healthCheckStateLocked != tryPassHealthCheckLocked;
                }
            }
        }
        if (z) {
            syncState("updated health check supported packages " + list);
        }
    }

    public final void onSyncRequestNotified() {
        synchronized (this.mLock) {
            this.mSyncRequired = true;
            syncRequestsAsync();
        }
    }

    public final Set getPackagesPendingHealthChecksLocked() {
        ArraySet arraySet = new ArraySet();
        Iterator it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            for (MonitoredPackage monitoredPackage : ((ObserverInternal) it.next()).getMonitoredPackages().values()) {
                String name = monitoredPackage.getName();
                if (monitoredPackage.isPendingHealthChecksLocked()) {
                    arraySet.add(name);
                }
            }
        }
        return arraySet;
    }

    public final void syncState(String str) {
        synchronized (this.mLock) {
            Slog.i("PackageWatchdog", "Syncing state, reason: " + str);
            pruneObserversLocked();
            saveToFileAsync();
            syncRequestsAsync();
            scheduleNextSyncStateLocked();
        }
    }

    public final void syncStateWithScheduledReason() {
        syncState("scheduled");
    }

    public final void scheduleNextSyncStateLocked() {
        long nextStateSyncMillisLocked = getNextStateSyncMillisLocked();
        this.mShortTaskHandler.removeCallbacks(this.mSyncStateWithScheduledReason);
        if (nextStateSyncMillisLocked == Long.MAX_VALUE) {
            Slog.i("PackageWatchdog", "Cancelling state sync, nothing to sync");
            this.mUptimeAtLastStateSync = 0L;
        } else {
            this.mUptimeAtLastStateSync = this.mSystemClock.uptimeMillis();
            this.mShortTaskHandler.postDelayed(this.mSyncStateWithScheduledReason, nextStateSyncMillisLocked);
        }
    }

    public final long getNextStateSyncMillisLocked() {
        long j = Long.MAX_VALUE;
        for (int i = 0; i < this.mAllObservers.size(); i++) {
            ArrayMap monitoredPackages = ((ObserverInternal) this.mAllObservers.valueAt(i)).getMonitoredPackages();
            for (int i2 = 0; i2 < monitoredPackages.size(); i2++) {
                long shortestScheduleDurationMsLocked = ((MonitoredPackage) monitoredPackages.valueAt(i2)).getShortestScheduleDurationMsLocked();
                if (shortestScheduleDurationMsLocked < j) {
                    j = shortestScheduleDurationMsLocked;
                }
            }
        }
        return j;
    }

    public final void pruneObserversLocked() {
        PackageHealthObserver packageHealthObserver;
        long uptimeMillis = this.mUptimeAtLastStateSync == 0 ? 0L : this.mSystemClock.uptimeMillis() - this.mUptimeAtLastStateSync;
        if (uptimeMillis <= 0) {
            Slog.i("PackageWatchdog", "Not pruning observers, elapsed time: " + uptimeMillis + "ms");
            return;
        }
        Iterator it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            ObserverInternal observerInternal = (ObserverInternal) it.next();
            Set prunePackagesLocked = observerInternal.prunePackagesLocked(uptimeMillis);
            if (!prunePackagesLocked.isEmpty()) {
                onHealthCheckFailed(observerInternal, prunePackagesLocked);
            }
            if (observerInternal.getMonitoredPackages().isEmpty() && ((packageHealthObserver = observerInternal.registeredObserver) == null || !packageHealthObserver.isPersistent())) {
                Slog.i("PackageWatchdog", "Discarding observer " + observerInternal.name + ". All packages expired");
                it.remove();
            }
        }
    }

    public final void onHealthCheckFailed(final ObserverInternal observerInternal, final Set set) {
        this.mLongTaskHandler.post(new Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                PackageWatchdog.this.lambda$onHealthCheckFailed$7(observerInternal, set);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHealthCheckFailed$7(ObserverInternal observerInternal, Set set) {
        synchronized (this.mLock) {
            PackageHealthObserver packageHealthObserver = observerInternal.registeredObserver;
            if (packageHealthObserver != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    VersionedPackage versionedPackage = getVersionedPackage(((MonitoredPackage) it.next()).getName());
                    if (versionedPackage != null) {
                        Slog.i("PackageWatchdog", "Explicit health check failed for package " + versionedPackage);
                        packageHealthObserver.execute(versionedPackage, 2, 1);
                    }
                }
            }
        }
    }

    public final PackageInfo getPackageInfo(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(str, 4194304);
        } catch (PackageManager.NameNotFoundException unused) {
            return packageManager.getPackageInfo(str, 1073741824);
        }
    }

    public final VersionedPackage getVersionedPackage(String str) {
        if (this.mContext.getPackageManager() != null && !TextUtils.isEmpty(str)) {
            try {
                return new VersionedPackage(str, getPackageInfo(str).getLongVersionCode());
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return null;
    }

    public final void loadFromFile() {
        this.mAllObservers.clear();
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mPolicyFile.openRead();
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                XmlUtils.beginDocument(resolvePullParser, "package-watchdog");
                int depth = resolvePullParser.getDepth();
                while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                    ObserverInternal read = ObserverInternal.read(resolvePullParser, this);
                    if (read != null) {
                        this.mAllObservers.put(read.name, read);
                    }
                }
            } catch (FileNotFoundException unused) {
            } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | XmlPullParserException e) {
                Slog.wtf("PackageWatchdog", "Unable to read monitored packages, deleting file", e);
                this.mPolicyFile.delete();
            }
        } finally {
            IoUtils.closeQuietly(fileInputStream);
        }
    }

    public final void onPropertyChanged(DeviceConfig.Properties properties) {
        try {
            updateConfigs();
        } catch (Exception unused) {
            Slog.w("PackageWatchdog", "Failed to reload device config changes");
        }
    }

    public final void setPropertyChangedListenerLocked() {
        DeviceConfig.addOnPropertiesChangedListener("rollback", this.mContext.getMainExecutor(), this.mOnPropertyChangedListener);
    }

    public void removePropertyChangedListener() {
        DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertyChangedListener);
    }

    public void updateConfigs() {
        synchronized (this.mLock) {
            int i = DeviceConfig.getInt("rollback", "watchdog_trigger_failure_count", 5);
            this.mTriggerFailureCount = i;
            if (i <= 0) {
                this.mTriggerFailureCount = 5;
            }
            int i2 = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
            int i3 = DeviceConfig.getInt("rollback", "watchdog_trigger_failure_duration_millis", i2);
            this.mTriggerFailureDurationMs = i3;
            if (i3 <= 0) {
                this.mTriggerFailureDurationMs = i2;
            }
            setExplicitHealthCheckEnabled(DeviceConfig.getBoolean("rollback", "watchdog_explicit_health_check_enabled", true));
        }
    }

    public final void registerConnectivityModuleHealthListener() {
        this.mConnectivityModuleConnector.registerHealthListener(new ConnectivityModuleConnector.ConnectivityModuleHealthListener() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda10
            @Override // android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener
            public final void onNetworkStackFailure(String str) {
                PackageWatchdog.this.lambda$registerConnectivityModuleHealthListener$8(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerConnectivityModuleHealthListener$8(String str) {
        VersionedPackage versionedPackage = getVersionedPackage(str);
        if (versionedPackage == null) {
            Slog.wtf("PackageWatchdog", "NetworkStack failed but could not find its package");
        } else {
            onPackageFailure(Collections.singletonList(versionedPackage), 2);
        }
    }

    public final boolean saveToFile() {
        Slog.i("PackageWatchdog", "Saving observer state to file");
        synchronized (this.mLock) {
            try {
                try {
                    FileOutputStream startWrite = this.mPolicyFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "package-watchdog");
                        resolveSerializer.attributeInt((String) null, "version", 1);
                        for (int i = 0; i < this.mAllObservers.size(); i++) {
                            ((ObserverInternal) this.mAllObservers.valueAt(i)).writeLocked(resolveSerializer);
                        }
                        resolveSerializer.endTag((String) null, "package-watchdog");
                        resolveSerializer.endDocument();
                        this.mPolicyFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w("PackageWatchdog", "Failed to save monitored packages, restoring backup", e);
                        this.mPolicyFile.failWrite(startWrite);
                        return false;
                    } finally {
                        IoUtils.closeQuietly(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("PackageWatchdog", "Cannot update monitored packages", e2);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final void saveToFileAsync() {
        if (this.mLongTaskHandler.hasCallbacks(this.mSaveToFile)) {
            return;
        }
        this.mLongTaskHandler.post(this.mSaveToFile);
    }

    public static String longArrayQueueToString(LongArrayQueue longArrayQueue) {
        if (longArrayQueue.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(longArrayQueue.get(0));
        for (int i = 1; i < longArrayQueue.size(); i++) {
            sb.append(",");
            sb.append(longArrayQueue.get(i));
        }
        return sb.toString();
    }

    public static LongArrayQueue parseLongArrayQueue(String str) {
        LongArrayQueue longArrayQueue = new LongArrayQueue();
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split(",")) {
                longArrayQueue.addLast(Long.parseLong(str2));
            }
        }
        return longArrayQueue;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Package Watchdog status");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            for (String str : this.mAllObservers.keySet()) {
                indentingPrintWriter.println("Observer name: " + str);
                indentingPrintWriter.increaseIndent();
                ((ObserverInternal) this.mAllObservers.get(str)).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    /* loaded from: classes.dex */
    public class ObserverInternal {
        public final ArrayMap mPackages = new ArrayMap();
        public final String name;
        public PackageHealthObserver registeredObserver;

        public ObserverInternal(String str, List list) {
            this.name = str;
            updatePackagesLocked(list);
        }

        public boolean writeLocked(TypedXmlSerializer typedXmlSerializer) {
            try {
                typedXmlSerializer.startTag((String) null, "observer");
                typedXmlSerializer.attribute((String) null, "name", this.name);
                for (int i = 0; i < this.mPackages.size(); i++) {
                    ((MonitoredPackage) this.mPackages.valueAt(i)).writeLocked(typedXmlSerializer);
                }
                typedXmlSerializer.endTag((String) null, "observer");
                return true;
            } catch (IOException e) {
                Slog.w("PackageWatchdog", "Cannot save observer", e);
                return false;
            }
        }

        public void updatePackagesLocked(List list) {
            for (int i = 0; i < list.size(); i++) {
                MonitoredPackage monitoredPackage = (MonitoredPackage) list.get(i);
                MonitoredPackage monitoredPackage2 = getMonitoredPackage(monitoredPackage.getName());
                if (monitoredPackage2 != null) {
                    monitoredPackage2.updateHealthCheckDuration(monitoredPackage.mDurationMs);
                } else {
                    putMonitoredPackage(monitoredPackage);
                }
            }
        }

        public final Set prunePackagesLocked(long j) {
            ArraySet arraySet = new ArraySet();
            Iterator it = this.mPackages.values().iterator();
            while (it.hasNext()) {
                MonitoredPackage monitoredPackage = (MonitoredPackage) it.next();
                int healthCheckStateLocked = monitoredPackage.getHealthCheckStateLocked();
                int handleElapsedTimeLocked = monitoredPackage.handleElapsedTimeLocked(j);
                if (healthCheckStateLocked != 3 && handleElapsedTimeLocked == 3) {
                    Slog.i("PackageWatchdog", "Package " + monitoredPackage.getName() + " failed health check");
                    arraySet.add(monitoredPackage);
                }
                if (monitoredPackage.isExpiredLocked()) {
                    it.remove();
                }
            }
            return arraySet;
        }

        public boolean onPackageFailureLocked(String str) {
            if (getMonitoredPackage(str) == null && this.registeredObserver.isPersistent() && this.registeredObserver.mayObservePackage(str)) {
                putMonitoredPackage(PackageWatchdog.sPackageWatchdog.newMonitoredPackage(str, PackageWatchdog.DEFAULT_OBSERVING_DURATION_MS, false));
            }
            MonitoredPackage monitoredPackage = getMonitoredPackage(str);
            if (monitoredPackage != null) {
                return monitoredPackage.onFailureLocked();
            }
            return false;
        }

        public ArrayMap getMonitoredPackages() {
            return this.mPackages;
        }

        public MonitoredPackage getMonitoredPackage(String str) {
            return (MonitoredPackage) this.mPackages.get(str);
        }

        public void putMonitoredPackage(MonitoredPackage monitoredPackage) {
            this.mPackages.put(monitoredPackage.getName(), monitoredPackage);
        }

        public static ObserverInternal read(TypedXmlPullParser typedXmlPullParser, PackageWatchdog packageWatchdog) {
            String str;
            if ("observer".equals(typedXmlPullParser.getName())) {
                str = typedXmlPullParser.getAttributeValue((String) null, "name");
                if (TextUtils.isEmpty(str)) {
                    Slog.wtf("PackageWatchdog", "Unable to read observer name");
                    return null;
                }
            } else {
                str = null;
            }
            ArrayList arrayList = new ArrayList();
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                try {
                    if ("package".equals(typedXmlPullParser.getName())) {
                        try {
                            MonitoredPackage parseMonitoredPackage = packageWatchdog.parseMonitoredPackage(typedXmlPullParser);
                            if (parseMonitoredPackage != null) {
                                arrayList.add(parseMonitoredPackage);
                            }
                        } catch (NumberFormatException e) {
                            Slog.wtf("PackageWatchdog", "Skipping package for observer " + str, e);
                        }
                    }
                } catch (IOException | XmlPullParserException e2) {
                    Slog.wtf("PackageWatchdog", "Unable to read observer " + str, e2);
                    return null;
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return new ObserverInternal(str, arrayList);
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            PackageHealthObserver packageHealthObserver = this.registeredObserver;
            indentingPrintWriter.println("Persistent: " + (packageHealthObserver != null && packageHealthObserver.isPersistent()));
            for (String str : this.mPackages.keySet()) {
                MonitoredPackage monitoredPackage = getMonitoredPackage(str);
                indentingPrintWriter.println(str + ": ");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("# Failures: " + monitoredPackage.mFailureHistory.size());
                indentingPrintWriter.println("Monitoring duration remaining: " + monitoredPackage.mDurationMs + "ms");
                indentingPrintWriter.println("Explicit health check duration: " + monitoredPackage.mHealthCheckDurationMs + "ms");
                StringBuilder sb = new StringBuilder();
                sb.append("Health check state: ");
                sb.append(monitoredPackage.toString(monitoredPackage.mHealthCheckState));
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public MonitoredPackage newMonitoredPackage(String str, long j, boolean z) {
        return newMonitoredPackage(str, j, Long.MAX_VALUE, z, new LongArrayQueue());
    }

    public MonitoredPackage newMonitoredPackage(String str, long j, long j2, boolean z, LongArrayQueue longArrayQueue) {
        return new MonitoredPackage(str, j, j2, z, longArrayQueue);
    }

    public MonitoredPackage parseMonitoredPackage(TypedXmlPullParser typedXmlPullParser) {
        return newMonitoredPackage(typedXmlPullParser.getAttributeValue((String) null, "name"), typedXmlPullParser.getAttributeLong((String) null, "duration"), typedXmlPullParser.getAttributeLong((String) null, "health-check-duration"), typedXmlPullParser.getAttributeBoolean((String) null, "passed-health-check"), parseLongArrayQueue(typedXmlPullParser.getAttributeValue((String) null, "mitigation-calls")));
    }

    /* loaded from: classes.dex */
    public class MonitoredPackage {
        public long mDurationMs;
        public boolean mHasPassedHealthCheck;
        public long mHealthCheckDurationMs;
        public final LongArrayQueue mMitigationCalls;
        public final String mPackageName;
        public final LongArrayQueue mFailureHistory = new LongArrayQueue();
        public int mHealthCheckState = 1;

        public final long toPositive(long j) {
            if (j > 0) {
                return j;
            }
            return Long.MAX_VALUE;
        }

        public final String toString(int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "FAILED" : "PASSED" : "INACTIVE" : "ACTIVE";
        }

        public MonitoredPackage(String str, long j, long j2, boolean z, LongArrayQueue longArrayQueue) {
            this.mPackageName = str;
            this.mDurationMs = j;
            this.mHealthCheckDurationMs = j2;
            this.mHasPassedHealthCheck = z;
            this.mMitigationCalls = longArrayQueue;
            updateHealthCheckStateLocked();
        }

        public void writeLocked(TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.startTag((String) null, "package");
            typedXmlSerializer.attribute((String) null, "name", getName());
            typedXmlSerializer.attributeLong((String) null, "duration", this.mDurationMs);
            typedXmlSerializer.attributeLong((String) null, "health-check-duration", this.mHealthCheckDurationMs);
            typedXmlSerializer.attributeBoolean((String) null, "passed-health-check", this.mHasPassedHealthCheck);
            typedXmlSerializer.attribute((String) null, "mitigation-calls", PackageWatchdog.longArrayQueueToString(normalizeMitigationCalls()));
            typedXmlSerializer.endTag((String) null, "package");
        }

        public boolean onFailureLocked() {
            long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
            this.mFailureHistory.addLast(uptimeMillis);
            while (uptimeMillis - this.mFailureHistory.peekFirst() > PackageWatchdog.this.mTriggerFailureDurationMs) {
                this.mFailureHistory.removeFirst();
            }
            boolean z = this.mFailureHistory.size() >= PackageWatchdog.this.mTriggerFailureCount;
            if (z) {
                this.mFailureHistory.clear();
            }
            return z;
        }

        public void noteMitigationCallLocked() {
            this.mMitigationCalls.addLast(PackageWatchdog.this.mSystemClock.uptimeMillis());
        }

        public int getMitigationCountLocked() {
            try {
                long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
                while (uptimeMillis - this.mMitigationCalls.peekFirst() > PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                    this.mMitigationCalls.removeFirst();
                }
            } catch (NoSuchElementException unused) {
            }
            return this.mMitigationCalls.size();
        }

        public LongArrayQueue normalizeMitigationCalls() {
            LongArrayQueue longArrayQueue = new LongArrayQueue();
            long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
            for (int i = 0; i < this.mMitigationCalls.size(); i++) {
                longArrayQueue.addLast(this.mMitigationCalls.get(i) - uptimeMillis);
            }
            return longArrayQueue;
        }

        public int setHealthCheckActiveLocked(long j) {
            if (j <= 0) {
                Slog.wtf("PackageWatchdog", "Cannot set non-positive health check duration " + j + "ms for package " + getName() + ". Using total duration " + this.mDurationMs + "ms instead");
                j = this.mDurationMs;
            }
            if (this.mHealthCheckState == 1) {
                this.mHealthCheckDurationMs = j;
            }
            return updateHealthCheckStateLocked();
        }

        public int handleElapsedTimeLocked(long j) {
            if (j <= 0) {
                Slog.w("PackageWatchdog", "Cannot handle non-positive elapsed time for package " + getName());
                return this.mHealthCheckState;
            }
            this.mDurationMs -= j;
            if (this.mHealthCheckState == 0) {
                this.mHealthCheckDurationMs -= j;
            }
            return updateHealthCheckStateLocked();
        }

        public void updateHealthCheckDuration(long j) {
            this.mDurationMs = j;
        }

        public int tryPassHealthCheckLocked() {
            if (this.mHealthCheckState != 3) {
                this.mHasPassedHealthCheck = true;
            }
            return updateHealthCheckStateLocked();
        }

        public final String getName() {
            return this.mPackageName;
        }

        public int getHealthCheckStateLocked() {
            return this.mHealthCheckState;
        }

        public long getShortestScheduleDurationMsLocked() {
            return Math.min(toPositive(this.mDurationMs), isPendingHealthChecksLocked() ? toPositive(this.mHealthCheckDurationMs) : Long.MAX_VALUE);
        }

        public boolean isExpiredLocked() {
            return this.mDurationMs <= 0;
        }

        public boolean isPendingHealthChecksLocked() {
            int i = this.mHealthCheckState;
            return i == 0 || i == 1;
        }

        public final int updateHealthCheckStateLocked() {
            int i = this.mHealthCheckState;
            if (this.mHasPassedHealthCheck) {
                this.mHealthCheckState = 2;
            } else {
                long j = this.mHealthCheckDurationMs;
                if (j <= 0 || this.mDurationMs <= 0) {
                    this.mHealthCheckState = 3;
                } else if (j == Long.MAX_VALUE) {
                    this.mHealthCheckState = 1;
                } else {
                    this.mHealthCheckState = 0;
                }
            }
            if (i != this.mHealthCheckState) {
                Slog.i("PackageWatchdog", "Updated health check state for package " + getName() + ": " + toString(i) + " -> " + toString(this.mHealthCheckState));
            }
            return this.mHealthCheckState;
        }

        public boolean isEqualTo(MonitoredPackage monitoredPackage) {
            return getName().equals(monitoredPackage.getName()) && this.mDurationMs == monitoredPackage.mDurationMs && this.mHasPassedHealthCheck == monitoredPackage.mHasPassedHealthCheck && this.mHealthCheckDurationMs == monitoredPackage.mHealthCheckDurationMs && this.mMitigationCalls.toString().equals(monitoredPackage.mMitigationCalls.toString());
        }
    }

    /* loaded from: classes.dex */
    public class BootThreshold {
        public final int mBootTriggerCount;
        public final long mTriggerWindow;

        public BootThreshold(int i, long j) {
            this.mBootTriggerCount = i;
            this.mTriggerWindow = j;
        }

        public void reset() {
            setStart(PackageWatchdog.this.mSystemClock.uptimeMillis());
            setCount(0);
        }

        public final int getCount() {
            return SystemProperties.getInt("sys.rescue_boot_count", 0);
        }

        public final void setCount(int i) {
            SystemProperties.set("sys.rescue_boot_count", Integer.toString(i));
        }

        public long getStart() {
            return SystemProperties.getLong("sys.rescue_boot_start", 0L);
        }

        public int getMitigationCount() {
            return SystemProperties.getInt("sys.boot_mitigation_count", 0);
        }

        public void setStart(long j) {
            setPropertyStart("sys.rescue_boot_start", j);
        }

        public void setMitigationStart(long j) {
            setPropertyStart("sys.boot_mitigation_start", j);
        }

        public long getMitigationStart() {
            return SystemProperties.getLong("sys.boot_mitigation_start", 0L);
        }

        public void setMitigationCount(int i) {
            SystemProperties.set("sys.boot_mitigation_count", Integer.toString(i));
        }

        public void setPropertyStart(String str, long j) {
            SystemProperties.set(str, Long.toString(MathUtils.constrain(j, 0L, PackageWatchdog.this.mSystemClock.uptimeMillis())));
        }

        public void saveMitigationCountToMetadata() {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/metadata/watchdog/mitigation_count.txt"));
                try {
                    bufferedWriter.write(String.valueOf(getMitigationCount()));
                    bufferedWriter.close();
                } finally {
                }
            } catch (Exception e) {
                Slog.e("PackageWatchdog", "Could not save metadata to file: " + e);
            }
        }

        public void readMitigationCountFromMetadataIfNecessary() {
            File file = new File("/metadata/watchdog/mitigation_count.txt");
            if (file.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("/metadata/watchdog/mitigation_count.txt"));
                    try {
                        setMitigationCount(Integer.parseInt(bufferedReader.readLine()));
                        file.delete();
                        bufferedReader.close();
                    } finally {
                    }
                } catch (Exception e) {
                    Slog.i("PackageWatchdog", "Could not read metadata file: " + e);
                }
            }
        }

        public boolean incrementAndTest() {
            readMitigationCountFromMetadataIfNecessary();
            long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
            if (uptimeMillis - getStart() < 0) {
                Slog.e("PackageWatchdog", "Window was less than zero. Resetting start to current time.");
                setStart(uptimeMillis);
                setMitigationStart(uptimeMillis);
            }
            if (uptimeMillis - getMitigationStart() > PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                setMitigationCount(0);
                setMitigationStart(uptimeMillis);
            }
            long start = uptimeMillis - getStart();
            if (start >= this.mTriggerWindow) {
                setCount(1);
                setStart(uptimeMillis);
                return false;
            }
            int count = getCount() + 1;
            setCount(count);
            EventLogTags.writeRescueNote(0, count, start);
            return count >= this.mBootTriggerCount;
        }
    }
}

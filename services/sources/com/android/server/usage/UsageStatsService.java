package com.android.server.usage;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IUidObserver;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.usage.AppLaunchEstimateInfo;
import android.app.usage.AppStandbyInfo;
import android.app.usage.BroadcastResponseStatsList;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutServiceInternal;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.usage.AppStandbyInternal;
import com.android.server.usage.AppTimeLimitController;
import com.android.server.usage.UsageStatsService;
import com.android.server.usage.UserUsageStatsService;
import com.android.server.utils.AlarmQueue;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class UsageStatsService extends SystemService implements UserUsageStatsService.StatsUpdatedListener {
    public static final File COMMON_USAGE_STATS_DIR;
    public static final File LEGACY_COMMON_USAGE_STATS_DIR;
    public static final File LEGACY_USER_USAGE_STATS_DIR;
    public AppOpsManager mAppOps;
    public AppStandbyInternal mAppStandby;
    public AppTimeLimitController mAppTimeLimit;
    public DevicePolicyManagerInternal mDpmInternal;
    public final CopyOnWriteArraySet mEstimatedLaunchTimeChangedListeners;
    public Handler mHandler;
    public final Injector mInjector;
    public final Map mLastTimeComponentUsedGlobal;
    public final SparseArray mLaunchTimeAlarmQueues;
    public final Object mLock;
    public PackageManager mPackageManager;
    public PackageManagerInternal mPackageManagerInternal;
    public final PackageMonitor mPackageMonitor;
    public final SparseSetArray mPendingLaunchTimeChangePackages;
    public long mRealTimeSnapshot;
    public final SparseArray mReportedEvents;
    public BroadcastResponseStatsTracker mResponseStatsTracker;
    public ShortcutServiceInternal mShortcutServiceInternal;
    public AppStandbyInternal.AppIdleStateChangeListener mStandbyChangeListener;
    public long mSystemTimeSnapshot;
    public final IUidObserver mUidObserver;
    public final SparseIntArray mUidToKernelCounter;
    public final ArraySet mUsageEventListeners;
    public final SparseArray mUsageReporters;
    public int mUsageSource;
    public final HashMap mUsageStatsWatchers;
    public final HashMap mUsageStatsWatchersComponent;
    public UserManager mUserManager;
    public final SparseArray mUserState;
    public final CopyOnWriteArraySet mUserUnlockedStates;
    public final SparseArray mVisibleActivities;
    public static final boolean ENABLE_TIME_CHANGE_CORRECTION = SystemProperties.getBoolean("persist.debug.time_correction", true);
    public static final boolean DEBUG_RESPONSE_STATS = Log.isLoggable("UsageStatsService", 3);
    public static final File KERNEL_COUNTER_FILE = new File("/proc/uid_procstat/set");

    public static long calculateNextLaunchTime(boolean z, long j) {
        return j + (z ? 604800000L : BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
    }

    static {
        File file = new File(Environment.getDataSystemDirectory(), "usagestats");
        COMMON_USAGE_STATS_DIR = file;
        LEGACY_USER_USAGE_STATS_DIR = file;
        LEGACY_COMMON_USAGE_STATS_DIR = new File(Environment.getDataSystemDeDirectory(), "usagestats");
    }

    /* loaded from: classes3.dex */
    public class ActivityData {
        public int lastEvent;
        public final String mTaskRootClass;
        public final String mTaskRootPackage;
        public final String mUsageSourcePackage;

        public ActivityData(String str, String str2, String str3) {
            this.lastEvent = 0;
            this.mTaskRootPackage = str;
            this.mTaskRootClass = str2;
            this.mUsageSourcePackage = str3;
        }
    }

    /* loaded from: classes3.dex */
    class Injector {
        public AppStandbyInternal getAppStandbyController(Context context) {
            return AppStandbyInternal.newAppStandbyController(UsageStatsService.class.getClassLoader(), context);
        }
    }

    public UsageStatsService(Context context) {
        this(context, new Injector());
    }

    public UsageStatsService(Context context, Injector injector) {
        super(context);
        this.mLock = new Object();
        this.mUserState = new SparseArray();
        this.mUserUnlockedStates = new CopyOnWriteArraySet();
        this.mUidToKernelCounter = new SparseIntArray();
        this.mLastTimeComponentUsedGlobal = new ArrayMap();
        this.mUsageStatsWatchers = new HashMap();
        this.mUsageStatsWatchersComponent = new HashMap();
        this.mPackageMonitor = new MyPackageMonitor();
        this.mReportedEvents = new SparseArray();
        this.mUsageReporters = new SparseArray();
        this.mVisibleActivities = new SparseArray();
        this.mLaunchTimeAlarmQueues = new SparseArray();
        this.mUsageEventListeners = new ArraySet();
        this.mEstimatedLaunchTimeChangedListeners = new CopyOnWriteArraySet();
        this.mPendingLaunchTimeChangePackages = new SparseSetArray();
        this.mStandbyChangeListener = new AppStandbyInternal.AppIdleStateChangeListener() { // from class: com.android.server.usage.UsageStatsService.1
            public void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
                UsageEvents.Event event = new UsageEvents.Event(11, SystemClock.elapsedRealtime());
                event.mBucketAndReason = (i2 << 16) | (i3 & GnssNative.GNSS_AIDING_TYPE_ALL);
                event.mPackage = str;
                UsageStatsService.this.reportEventOrAddToQueue(i, event);
            }
        };
        this.mUidObserver = new UidObserver() { // from class: com.android.server.usage.UsageStatsService.3
            public void onUidStateChanged(int i, int i2, long j, int i3) {
                UsageStatsService.this.mHandler.obtainMessage(3, i, i2).sendToTarget();
            }

            public void onUidGone(int i, boolean z) {
                onUidStateChanged(i, 20, 0L, 0);
            }
        };
        this.mInjector = injector;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mAppOps = (AppOpsManager) getContext().getSystemService("appops");
        this.mUserManager = (UserManager) getContext().getSystemService("user");
        this.mPackageManager = getContext().getPackageManager();
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mHandler = new H(BackgroundThread.get().getLooper());
        AppStandbyInternal appStandbyController = this.mInjector.getAppStandbyController(getContext());
        this.mAppStandby = appStandbyController;
        this.mResponseStatsTracker = new BroadcastResponseStatsTracker(appStandbyController);
        this.mAppTimeLimit = new AppTimeLimitController(getContext(), new AppTimeLimitController.TimeLimitCallbackListener() { // from class: com.android.server.usage.UsageStatsService.2
            @Override // com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener
            public void onLimitReached(int i, int i2, long j, long j2, PendingIntent pendingIntent) {
                if (pendingIntent == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("android.app.usage.extra.OBSERVER_ID", i);
                intent.putExtra("android.app.usage.extra.TIME_LIMIT", j);
                intent.putExtra("android.app.usage.extra.TIME_USED", j2);
                try {
                    pendingIntent.send(UsageStatsService.this.getContext(), 0, intent);
                } catch (PendingIntent.CanceledException unused) {
                    Slog.w("UsageStatsService", "Couldn't deliver callback: " + pendingIntent);
                }
            }

            @Override // com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener
            public void onSessionEnd(int i, int i2, long j, PendingIntent pendingIntent) {
                if (pendingIntent == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("android.app.usage.extra.OBSERVER_ID", i);
                intent.putExtra("android.app.usage.extra.TIME_USED", j);
                try {
                    pendingIntent.send(UsageStatsService.this.getContext(), 0, intent);
                } catch (PendingIntent.CanceledException unused) {
                    Slog.w("UsageStatsService", "Couldn't deliver callback: " + pendingIntent);
                }
            }
        }, this.mHandler.getLooper());
        this.mAppStandby.addListener(this.mStandbyChangeListener);
        this.mPackageMonitor.register(getContext(), (Looper) null, UserHandle.ALL, true);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        getContext().registerReceiverAsUser(new UserActionsReceiver(), UserHandle.ALL, intentFilter, null, this.mHandler);
        getContext().registerReceiverAsUser(new UidRemovedReceiver(), UserHandle.ALL, new IntentFilter("android.intent.action.UID_REMOVED"), null, this.mHandler);
        this.mRealTimeSnapshot = SystemClock.elapsedRealtime();
        this.mSystemTimeSnapshot = System.currentTimeMillis();
        publishLocalService(UsageStatsManagerInternal.class, new LocalService());
        publishLocalService(AppStandbyInternal.class, this.mAppStandby);
        publishBinderServices();
        this.mHandler.obtainMessage(7).sendToTarget();
    }

    public void publishBinderServices() {
        publishBinderService("usagestats", new BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        this.mAppStandby.onBootPhase(i);
        if (i == 500) {
            getDpmInternal();
            getShortcutServiceInternal();
            this.mResponseStatsTracker.onSystemServicesReady(getContext());
            File file = KERNEL_COUNTER_FILE;
            if (file.exists()) {
                try {
                    ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (String) null);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Slog.w("UsageStatsService", "Missing procfs interface: " + file);
            }
            readUsageSourceSetting();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        this.mUserState.put(targetUser.getUserIdentifier(), null);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(5, targetUser.getUserIdentifier(), 0).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(userIdentifier))) {
                persistPendingEventsLocked(userIdentifier);
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(29, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            reportEvent(event, userIdentifier);
            UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(userIdentifier);
            if (userUsageStatsService != null) {
                userUsageStatsService.userStopped();
            }
            this.mUserUnlockedStates.remove(Integer.valueOf(userIdentifier));
            this.mUserState.put(userIdentifier, null);
            LaunchTimeAlarmQueue launchTimeAlarmQueue = (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(userIdentifier);
            if (launchTimeAlarmQueue != null) {
                launchTimeAlarmQueue.removeAllAlarms();
                this.mLaunchTimeAlarmQueues.remove(userIdentifier);
            }
        }
    }

    public final void onUserUnlocked(int i) {
        HashMap installedPackages = getInstalledPackages(i);
        UsageStatsIdleService.scheduleUpdateMappingsJob(getContext(), i);
        boolean shouldDeleteObsoleteData = shouldDeleteObsoleteData(UserHandle.of(i));
        synchronized (this.mLock) {
            this.mUserUnlockedStates.add(Integer.valueOf(i));
            UsageEvents.Event event = new UsageEvents.Event(28, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            migrateStatsToSystemCeIfNeededLocked(i);
            LinkedList linkedList = new LinkedList();
            loadPendingEventsLocked(i, linkedList);
            synchronized (this.mReportedEvents) {
                LinkedList linkedList2 = (LinkedList) this.mReportedEvents.get(i);
                if (linkedList2 != null) {
                    linkedList.addAll(linkedList2);
                    this.mReportedEvents.remove(i);
                }
            }
            boolean z = !linkedList.isEmpty();
            initializeUserUsageStatsServiceLocked(i, System.currentTimeMillis(), installedPackages, shouldDeleteObsoleteData);
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                Slog.i("UsageStatsService", "Attempted to unlock stopped or removed user " + i);
                return;
            }
            while (linkedList.peek() != null) {
                reportEvent((UsageEvents.Event) linkedList.poll(), i);
            }
            reportEvent(event, i);
            this.mHandler.obtainMessage(8, i, 0).sendToTarget();
            deleteRecursively(new File(Environment.getDataSystemDeDirectory(i), "usagestats"));
            if (z) {
                userUsageStatsServiceLocked.persistActiveStats();
            }
        }
    }

    public final HashMap getInstalledPackages(int i) {
        PackageManager packageManager = this.mPackageManager;
        if (packageManager == null) {
            return null;
        }
        List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(IInstalld.FLAG_FORCE, i);
        HashMap hashMap = new HashMap();
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(size);
            hashMap.put(packageInfo.packageName, Long.valueOf(packageInfo.firstInstallTime));
        }
        return hashMap;
    }

    public final DevicePolicyManagerInternal getDpmInternal() {
        if (this.mDpmInternal == null) {
            this.mDpmInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        }
        return this.mDpmInternal;
    }

    public final ShortcutServiceInternal getShortcutServiceInternal() {
        if (this.mShortcutServiceInternal == null) {
            this.mShortcutServiceInternal = (ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class);
        }
        return this.mShortcutServiceInternal;
    }

    public final void readUsageSourceSetting() {
        synchronized (this.mLock) {
            this.mUsageSource = Settings.Global.getInt(getContext().getContentResolver(), "app_time_limit_usage_source", 2);
        }
    }

    /* loaded from: classes3.dex */
    public class LaunchTimeAlarmQueue extends AlarmQueue {
        public final int mUserId;

        public LaunchTimeAlarmQueue(int i, Context context, Looper looper) {
            super(context, looper, "*usage.launchTime*", "Estimated launch times", true, 30000L);
            this.mUserId = i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(String str, int i) {
            return this.mUserId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public void processExpiredAlarms(ArraySet arraySet) {
            if (arraySet.size() > 0) {
                synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                    UsageStatsService.this.mPendingLaunchTimeChangePackages.addAll(this.mUserId, arraySet);
                }
                UsageStatsService.this.mHandler.sendEmptyMessage(9);
            }
        }
    }

    /* loaded from: classes3.dex */
    public class UserActionsReceiver extends BroadcastReceiver {
        public UserActionsReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            String action = intent.getAction();
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                if (intExtra >= 0) {
                    UsageStatsService.this.mHandler.obtainMessage(2, intExtra, 0).sendToTarget();
                    UsageStatsService.this.mResponseStatsTracker.onUserRemoved(intExtra);
                    return;
                }
                return;
            }
            if (!"android.intent.action.USER_STARTED".equals(action) || intExtra < 0) {
                return;
            }
            UsageStatsService.this.mAppStandby.postCheckIdleStates(intExtra);
        }
    }

    /* loaded from: classes3.dex */
    public class UidRemovedReceiver extends BroadcastReceiver {
        public UidRemovedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra == -1) {
                return;
            }
            synchronized (UsageStatsService.this.mLock) {
                UsageStatsService.this.mResponseStatsTracker.onUidRemoved(intExtra);
            }
        }
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onStatsUpdated() {
        this.mHandler.sendEmptyMessageDelayed(1, 1200000L);
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onStatsReloaded() {
        this.mAppStandby.postOneTimeCheckIdleStates();
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onNewUpdate(int i) {
        this.mAppStandby.initializeDefaultsForSystemApps(i);
    }

    public final boolean sameApp(int i, int i2, String str) {
        return this.mPackageManagerInternal.getPackageUid(str, 0L, i2) == i;
    }

    public final boolean isInstantApp(String str, int i) {
        return this.mPackageManagerInternal.isPackageEphemeral(i, str);
    }

    public final boolean shouldObfuscateInstantAppsForCaller(int i, int i2) {
        return !this.mPackageManagerInternal.canAccessInstantApps(i, i2);
    }

    public final boolean shouldHideShortcutInvocationEvents(int i, String str, int i2, int i3) {
        if (getShortcutServiceInternal() != null) {
            return !r1.hasShortcutHostPermission(i, str, i2, i3);
        }
        return true;
    }

    public final boolean shouldHideLocusIdEvents(int i, int i2) {
        return (i2 == 1000 || getContext().checkPermission("android.permission.ACCESS_LOCUS_ID_USAGE_STATS", i, i2) == 0) ? false : true;
    }

    public final boolean shouldObfuscateNotificationEvents(int i, int i2) {
        return (i2 == 1000 || getContext().checkPermission("android.permission.MANAGE_NOTIFICATIONS", i, i2) == 0) ? false : true;
    }

    public static void deleteRecursively(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                deleteRecursively(file2);
            }
        }
        if (!file.exists() || file.delete()) {
            return;
        }
        Slog.e("UsageStatsService", "Failed to delete " + file);
    }

    public final UserUsageStatsService getUserUsageStatsServiceLocked(int i) {
        UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(i);
        if (userUsageStatsService == null) {
            Slog.wtf("UsageStatsService", "Failed to fetch usage stats service for user " + i + ". The user might not have been initialized yet.");
        }
        return userUsageStatsService;
    }

    public final void initializeUserUsageStatsServiceLocked(int i, long j, HashMap hashMap, boolean z) {
        UserUsageStatsService userUsageStatsService = new UserUsageStatsService(getContext(), i, new File(Environment.getDataSystemCeDirectory(i), "usagestats"), this);
        try {
            userUsageStatsService.init(j, hashMap, z);
            this.mUserState.put(i, userUsageStatsService);
        } catch (Exception e) {
            if (this.mUserManager.isUserUnlocked(i)) {
                Slog.w("UsageStatsService", "Failed to initialized unlocked user " + i);
                throw e;
            }
            Slog.w("UsageStatsService", "Attempted to initialize service for stopped or removed user " + i);
        }
    }

    public final void migrateStatsToSystemCeIfNeededLocked(int i) {
        File file = new File(Environment.getDataSystemCeDirectory(i), "usagestats");
        if (!file.mkdirs() && !file.exists()) {
            throw new IllegalStateException("Usage stats directory does not exist: " + file.getAbsolutePath());
        }
        File file2 = new File(file, "migrated");
        if (file2.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
                try {
                    if (Integer.parseInt(bufferedReader.readLine()) >= 4) {
                        deleteLegacyUserDir(i);
                        bufferedReader.close();
                        return;
                    }
                    bufferedReader.close();
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | NumberFormatException e) {
                Slog.e("UsageStatsService", "Failed to read migration status file, possibly corrupted.");
                deleteRecursively(file);
                if (file.exists()) {
                    Slog.e("UsageStatsService", "Unable to delete usage stats CE directory.");
                    throw new RuntimeException(e);
                }
                if (!file.mkdirs() && !file.exists()) {
                    throw new IllegalStateException("Usage stats directory does not exist: " + file.getAbsolutePath());
                }
            }
        }
        Slog.i("UsageStatsService", "Starting migration to system CE for user " + i);
        File file3 = new File(LEGACY_USER_USAGE_STATS_DIR, Integer.toString(i));
        if (file3.exists()) {
            copyRecursively(file, file3);
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
            try {
                bufferedWriter.write(Integer.toString(4));
                bufferedWriter.write(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                bufferedWriter.flush();
                bufferedWriter.close();
                Slog.i("UsageStatsService", "Finished migration to system CE for user " + i);
                deleteLegacyUserDir(i);
            } finally {
            }
        } catch (IOException e2) {
            Slog.e("UsageStatsService", "Failed to write migrated status file");
            throw new RuntimeException(e2);
        }
    }

    public static void copyRecursively(File file, File file2) {
        File file3;
        File[] listFiles = file2.listFiles();
        if (listFiles == null) {
            try {
                Files.copy(file2.toPath(), new File(file, file2.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                return;
            } catch (IOException e) {
                Slog.e("UsageStatsService", "Failed to move usage stats file : " + file2.toString());
                throw new RuntimeException(e);
            }
        }
        for (int length = listFiles.length - 1; length >= 0; length--) {
            if (listFiles[length].isDirectory()) {
                file3 = new File(file, listFiles[length].getName());
                if (!file3.mkdirs() && !file3.exists()) {
                    throw new IllegalStateException("Failed to create usage stats directory during migration: " + file3.getAbsolutePath());
                }
            } else {
                file3 = file;
            }
            copyRecursively(file3, listFiles[length]);
        }
    }

    public final void deleteLegacyUserDir(int i) {
        File file = new File(LEGACY_USER_USAGE_STATS_DIR, Integer.toString(i));
        if (file.exists()) {
            deleteRecursively(file);
            if (file.exists()) {
                Slog.w("UsageStatsService", "Error occurred while attempting to delete legacy usage stats dir for user " + i);
            }
        }
    }

    public void shutdown() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            UsageEvents.Event event = new UsageEvents.Event(26, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            event.mFlags = 1;
            reportShutdownEvent(event);
            reportEventToAllUserId(event);
            flushToDiskLocked();
            persistGlobalComponentUsageLocked();
        }
        this.mAppStandby.flushToDisk();
    }

    public void reportShutdownEvent(UsageEvents.Event event) {
        int size = this.mUserState.size();
        for (int i = 0; i < size; i++) {
            UsageEvents.Event event2 = new UsageEvents.Event(event);
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(this.mUserState.keyAt(i));
            if (userUsageStatsServiceLocked == null) {
                return;
            }
            userUsageStatsServiceLocked.reportShutdownEvent(event2);
        }
    }

    public void prepareForPossibleShutdown() {
        UsageEvents.Event event = new UsageEvents.Event(26, SystemClock.elapsedRealtime());
        event.mPackage = "android";
        this.mHandler.obtainMessage(4, event).sendToTarget();
        this.mHandler.sendEmptyMessage(1);
    }

    public final void loadPendingEventsLocked(int i, LinkedList linkedList) {
        File[] listFiles = new File(Environment.getDataSystemDeDirectory(i), "usagestats").listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        Arrays.sort(listFiles);
        int length = listFiles.length;
        for (int i2 = 0; i2 < length; i2++) {
            AtomicFile atomicFile = new AtomicFile(listFiles[i2]);
            LinkedList linkedList2 = new LinkedList();
            try {
                FileInputStream openRead = atomicFile.openRead();
                try {
                    UsageStatsProtoV2.readPendingEvents(openRead, linkedList2);
                    if (openRead != null) {
                        openRead.close();
                    }
                    linkedList.addAll(linkedList2);
                } catch (Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                    break;
                }
            } catch (Exception unused) {
                Slog.e("UsageStatsService", "Could not read " + listFiles[i2] + " for user " + i);
            }
        }
    }

    public final void persistPendingEventsLocked(int i) {
        LinkedList linkedList = (LinkedList) this.mReportedEvents.get(i);
        if (linkedList == null || linkedList.isEmpty()) {
            return;
        }
        File dataSystemDeDirectory = Environment.getDataSystemDeDirectory(i);
        File file = new File(dataSystemDeDirectory, "usagestats");
        if (!file.mkdir() && !file.exists()) {
            if (dataSystemDeDirectory.exists()) {
                Slog.e("UsageStatsService", "Failed to create " + file);
                return;
            }
            Slog.w("UsageStatsService", "User " + i + " was already removed! Discarding pending events");
            linkedList.clear();
            return;
        }
        File file2 = new File(file, "pendingevents_" + System.currentTimeMillis());
        AtomicFile atomicFile = new AtomicFile(file2);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    UsageStatsProtoV2.writePendingEvents(startWrite, linkedList);
                    atomicFile.finishWrite(startWrite);
                    linkedList.clear();
                } catch (Exception unused) {
                    fileOutputStream = startWrite;
                    Slog.e("UsageStatsService", "Failed to write " + file2.getAbsolutePath() + " for user " + i);
                    atomicFile.failWrite(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
        }
        atomicFile.failWrite(fileOutputStream);
    }

    public final void loadGlobalComponentUsageLocked() {
        AtomicFile atomicFile = new AtomicFile(new File(COMMON_USAGE_STATS_DIR, "globalcomponentusage"));
        if (!atomicFile.exists()) {
            atomicFile = new AtomicFile(new File(LEGACY_COMMON_USAGE_STATS_DIR, "globalcomponentusage"));
            if (!atomicFile.exists()) {
                return;
            } else {
                Slog.i("UsageStatsService", "Reading globalcomponentusage file from old location");
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        try {
            FileInputStream openRead = atomicFile.openRead();
            try {
                UsageStatsProtoV2.readGlobalComponentUsage(openRead, arrayMap);
                if (openRead != null) {
                    openRead.close();
                }
                Map.Entry[] entryArr = (Map.Entry[]) arrayMap.entrySet().toArray();
                int length = entryArr.length;
                for (int i = 0; i < length; i++) {
                    this.mLastTimeComponentUsedGlobal.putIfAbsent((String) entryArr[i].getKey(), (Long) entryArr[i].getValue());
                }
            } finally {
            }
        } catch (Exception unused) {
            Slog.e("UsageStatsService", "Could not read " + atomicFile.getBaseFile());
        }
    }

    public final void persistGlobalComponentUsageLocked() {
        if (this.mLastTimeComponentUsedGlobal.isEmpty()) {
            return;
        }
        File file = COMMON_USAGE_STATS_DIR;
        if (!file.mkdirs() && !file.exists()) {
            throw new IllegalStateException("Common usage stats directory does not exist: " + file.getAbsolutePath());
        }
        File file2 = new File(file, "globalcomponentusage");
        AtomicFile atomicFile = new AtomicFile(file2);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    UsageStatsProtoV2.writeGlobalComponentUsage(startWrite, this.mLastTimeComponentUsedGlobal);
                    atomicFile.finishWrite(startWrite);
                } catch (Exception unused) {
                    fileOutputStream = startWrite;
                    Slog.e("UsageStatsService", "Failed to write " + file2.getAbsolutePath());
                    atomicFile.failWrite(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (Exception unused2) {
            }
            atomicFile.failWrite(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void reportEventOrAddToQueue(int i, UsageEvents.Event event) {
        if (this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
            this.mHandler.obtainMessage(0, i, 0, event).sendToTarget();
            return;
        }
        synchronized (this.mReportedEvents) {
            LinkedList linkedList = (LinkedList) this.mReportedEvents.get(i);
            if (linkedList == null) {
                linkedList = new LinkedList();
                this.mReportedEvents.put(i, linkedList);
            }
            linkedList.add(event);
            if (linkedList.size() == 1) {
                this.mHandler.sendEmptyMessageDelayed(1, 1200000L);
            }
        }
    }

    public final void convertToSystemTimeLocked(UsageEvents.Event event) {
        long currentTimeMillis = System.currentTimeMillis();
        if (ENABLE_TIME_CHANGE_CORRECTION) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = currentTimeMillis - ((elapsedRealtime - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot);
            if (Math.abs(j) > 2000) {
                Slog.i("UsageStatsService", "Time changed in by " + (j / 1000) + " seconds");
                this.mRealTimeSnapshot = elapsedRealtime;
                this.mSystemTimeSnapshot = currentTimeMillis;
            }
        }
        event.mTimeStamp = Math.max(0L, event.mTimeStamp - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0233 A[Catch: all -> 0x0257, DONT_GENERATE, TryCatch #4 {, blocks: (B:24:0x003e, B:26:0x004a, B:27:0x008d, B:29:0x008f, B:40:0x022d, B:42:0x0233, B:44:0x0235, B:45:0x0238, B:59:0x00a7, B:60:0x00a9, B:62:0x00b5, B:63:0x00f0, B:65:0x00f2, B:67:0x00f6, B:68:0x00fd, B:69:0x00ff, B:74:0x010d, B:93:0x0145, B:94:0x0146, B:96:0x014a, B:98:0x0156, B:101:0x0162, B:105:0x016d, B:106:0x016e, B:107:0x0180, B:109:0x018c, B:111:0x0190, B:112:0x019e, B:113:0x01b6, B:115:0x01bc, B:118:0x0197, B:119:0x01af, B:120:0x01c9, B:123:0x01db, B:125:0x01df, B:126:0x01ed, B:128:0x020f, B:130:0x0217, B:132:0x0226, B:135:0x01e6, B:76:0x010e, B:78:0x0115, B:80:0x011b, B:83:0x013e, B:85:0x0128, B:88:0x0141, B:71:0x0100, B:72:0x010a), top: B:23:0x003e, inners: #0, #1, #3, #5, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0235 A[Catch: all -> 0x0257, TryCatch #4 {, blocks: (B:24:0x003e, B:26:0x004a, B:27:0x008d, B:29:0x008f, B:40:0x022d, B:42:0x0233, B:44:0x0235, B:45:0x0238, B:59:0x00a7, B:60:0x00a9, B:62:0x00b5, B:63:0x00f0, B:65:0x00f2, B:67:0x00f6, B:68:0x00fd, B:69:0x00ff, B:74:0x010d, B:93:0x0145, B:94:0x0146, B:96:0x014a, B:98:0x0156, B:101:0x0162, B:105:0x016d, B:106:0x016e, B:107:0x0180, B:109:0x018c, B:111:0x0190, B:112:0x019e, B:113:0x01b6, B:115:0x01bc, B:118:0x0197, B:119:0x01af, B:120:0x01c9, B:123:0x01db, B:125:0x01df, B:126:0x01ed, B:128:0x020f, B:130:0x0217, B:132:0x0226, B:135:0x01e6, B:76:0x010e, B:78:0x0115, B:80:0x011b, B:83:0x013e, B:85:0x0128, B:88:0x0141, B:71:0x0100, B:72:0x010a), top: B:23:0x003e, inners: #0, #1, #3, #5, #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reportEvent(android.app.usage.UsageEvents.Event r13, int r14) {
        /*
            Method dump skipped, instructions count: 602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.UsageStatsService.reportEvent(android.app.usage.UsageEvents$Event, int):void");
    }

    public final String getUsageSourcePackage(UsageEvents.Event event) {
        if (this.mUsageSource == 2) {
            return event.mPackage;
        }
        return event.mTaskRootPackage;
    }

    public void reportEventToAllUserId(UsageEvents.Event event) {
        synchronized (this.mLock) {
            int size = this.mUserState.size();
            for (int i = 0; i < size; i++) {
                reportEventOrAddToQueue(this.mUserState.keyAt(i), new UsageEvents.Event(event));
            }
        }
    }

    public void flushToDisk() {
        synchronized (this.mLock) {
            UsageEvents.Event event = new UsageEvents.Event(25, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            reportEventToAllUserId(event);
            flushToDiskLocked();
        }
        this.mAppStandby.flushToDisk();
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            Slog.i("UsageStatsService", "Removing user " + i + " and all data.");
            this.mUserState.remove(i);
            this.mAppTimeLimit.onUserRemoved(i);
            LaunchTimeAlarmQueue launchTimeAlarmQueue = (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(i);
            if (launchTimeAlarmQueue != null) {
                launchTimeAlarmQueue.removeAllAlarms();
                this.mLaunchTimeAlarmQueues.remove(i);
            }
        }
        synchronized (this.mPendingLaunchTimeChangePackages) {
            this.mPendingLaunchTimeChangePackages.remove(i);
        }
        this.mAppStandby.onUserRemoved(i);
        UsageStatsIdleService.cancelPruneJob(getContext(), i);
        UsageStatsIdleService.cancelUpdateMappingsJob(getContext(), i);
    }

    public final void onPackageRemoved(int i, String str) {
        synchronized (this.mPendingLaunchTimeChangePackages) {
            ArraySet arraySet = this.mPendingLaunchTimeChangePackages.get(i);
            if (arraySet != null) {
                arraySet.remove(str);
            }
        }
        synchronized (this.mLock) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                LaunchTimeAlarmQueue launchTimeAlarmQueue = (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue != null) {
                    launchTimeAlarmQueue.removeAlarmForKey(str);
                }
                UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(i);
                if (userUsageStatsService == null) {
                    return;
                }
                int onPackageRemoved = userUsageStatsService.onPackageRemoved(str, currentTimeMillis);
                if (onPackageRemoved != -1) {
                    UsageStatsIdleService.schedulePruneJob(getContext(), i);
                }
            }
        }
    }

    public final boolean pruneUninstalledPackagesData(int i) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                return false;
            }
            UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(i);
            if (userUsageStatsService == null) {
                return false;
            }
            return userUsageStatsService.pruneUninstalledPackagesData();
        }
    }

    public final boolean updatePackageMappingsData(int i) {
        if (!shouldDeleteObsoleteData(UserHandle.of(i))) {
            return true;
        }
        HashMap installedPackages = getInstalledPackages(i);
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                return false;
            }
            UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(i);
            if (userUsageStatsService == null) {
                return false;
            }
            return userUsageStatsService.updatePackageMappingsLocked(installedPackages);
        }
    }

    public List queryUsageStats(int i, int i2, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query usage stats for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            List queryUsageStats = userUsageStatsServiceLocked.queryUsageStats(i2, j, j2);
            if (queryUsageStats == null) {
                return null;
            }
            if (z) {
                for (int size = queryUsageStats.size() - 1; size >= 0; size--) {
                    UsageStats usageStats = (UsageStats) queryUsageStats.get(size);
                    if (isInstantApp(usageStats.mPackageName, i)) {
                        queryUsageStats.set(size, usageStats.getObfuscatedForInstantApp());
                    }
                }
            }
            return queryUsageStats;
        }
    }

    public List queryConfigurationStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query configuration stats for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryConfigurationStats(i2, j, j2);
        }
    }

    public List queryEventStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query event stats for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryEventStats(i2, j, j2);
        }
    }

    public UsageEvents queryEvents(int i, long j, long j2, int i2) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query events for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryEvents(j, j2, i2);
        }
    }

    public UsageEvents queryEventsForPackage(int i, long j, long j2, String str, boolean z) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query package events for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryEventsForPackage(j, j2, str, z);
        }
    }

    public final UsageEvents queryEarliestAppEvents(int i, long j, long j2, int i2) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query earliest events for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryEarliestAppEvents(j, j2, i2);
        }
    }

    public final UsageEvents queryEarliestEventsForPackage(int i, long j, long j2, String str, int i2) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to query earliest package events for locked user " + i);
                return null;
            }
            UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
            if (userUsageStatsServiceLocked == null) {
                return null;
            }
            return userUsageStatsServiceLocked.queryEarliestEventsForPackage(j, j2, str, i2);
        }
    }

    public long getEstimatedPackageLaunchTime(int i, String str) {
        long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(str, i);
        long currentTimeMillis = System.currentTimeMillis();
        if (estimatedLaunchTime < currentTimeMillis || estimatedLaunchTime == Long.MAX_VALUE) {
            estimatedLaunchTime = calculateEstimatedPackageLaunchTime(i, str);
            this.mAppStandby.setEstimatedLaunchTime(str, i, estimatedLaunchTime);
            synchronized (this.mLock) {
                LaunchTimeAlarmQueue launchTimeAlarmQueue = (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue == null) {
                    launchTimeAlarmQueue = new LaunchTimeAlarmQueue(i, getContext(), BackgroundThread.get().getLooper());
                    this.mLaunchTimeAlarmQueues.put(i, launchTimeAlarmQueue);
                }
                launchTimeAlarmQueue.addAlarm(str, SystemClock.elapsedRealtime() + (estimatedLaunchTime - currentTimeMillis));
            }
        }
        return estimatedLaunchTime;
    }

    public final long calculateEstimatedPackageLaunchTime(int i, String str) {
        synchronized (this.mLock) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis + 31536000000L;
            UsageEvents queryEarliestEventsForPackage = queryEarliestEventsForPackage(i, currentTimeMillis - 604800000, currentTimeMillis, str, 1);
            if (queryEarliestEventsForPackage == null) {
                return j;
            }
            UsageEvents.Event event = new UsageEvents.Event();
            if (!queryEarliestEventsForPackage.getNextEvent(event)) {
                return j;
            }
            boolean z = currentTimeMillis - event.getTimeStamp() > BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            do {
                if (event.getEventType() == 1) {
                    long calculateNextLaunchTime = calculateNextLaunchTime(z, event.getTimeStamp());
                    if (calculateNextLaunchTime > currentTimeMillis) {
                        return calculateNextLaunchTime;
                    }
                }
            } while (queryEarliestEventsForPackage.getNextEvent(event));
            return j;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00b3 A[Catch: all -> 0x00d4, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x0020, B:9:0x0022, B:11:0x0036, B:12:0x004c, B:14:0x0053, B:16:0x005e, B:19:0x0070, B:21:0x0077, B:23:0x007d, B:29:0x00ad, B:31:0x00b3, B:32:0x00b9, B:34:0x00c2, B:35:0x0095, B:40:0x00cb, B:41:0x00d2), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleEstimatedLaunchTimesOnUserUnlock(int r22) {
        /*
            r21 = this;
            r0 = r21
            r8 = r22
            java.lang.Object r9 = r0.mLock
            monitor-enter(r9)
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch: java.lang.Throwable -> Ld4
            long r12 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld4
            r14 = 604800000(0x240c8400, double:2.988109026E-315)
            long r3 = r12 - r14
            r7 = 1
            r1 = r21
            r2 = r22
            r5 = r12
            android.app.usage.UsageEvents r1 = r1.queryEarliestAppEvents(r2, r3, r5, r7)     // Catch: java.lang.Throwable -> Ld4
            if (r1 != 0) goto L22
            monitor-exit(r9)     // Catch: java.lang.Throwable -> Ld4
            return
        L22:
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch: java.lang.Throwable -> Ld4
            r2.<init>()     // Catch: java.lang.Throwable -> Ld4
            android.app.usage.UsageEvents$Event r3 = new android.app.usage.UsageEvents$Event     // Catch: java.lang.Throwable -> Ld4
            r3.<init>()     // Catch: java.lang.Throwable -> Ld4
            android.util.SparseArray r4 = r0.mLaunchTimeAlarmQueues     // Catch: java.lang.Throwable -> Ld4
            java.lang.Object r4 = r4.get(r8)     // Catch: java.lang.Throwable -> Ld4
            com.android.server.usage.UsageStatsService$LaunchTimeAlarmQueue r4 = (com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue) r4     // Catch: java.lang.Throwable -> Ld4
            if (r4 != 0) goto L4c
            com.android.server.usage.UsageStatsService$LaunchTimeAlarmQueue r4 = new com.android.server.usage.UsageStatsService$LaunchTimeAlarmQueue     // Catch: java.lang.Throwable -> Ld4
            android.content.Context r5 = r21.getContext()     // Catch: java.lang.Throwable -> Ld4
            com.android.internal.os.BackgroundThread r6 = com.android.internal.os.BackgroundThread.get()     // Catch: java.lang.Throwable -> Ld4
            android.os.Looper r6 = r6.getLooper()     // Catch: java.lang.Throwable -> Ld4
            r4.<init>(r8, r5, r6)     // Catch: java.lang.Throwable -> Ld4
            android.util.SparseArray r5 = r0.mLaunchTimeAlarmQueues     // Catch: java.lang.Throwable -> Ld4
            r5.put(r8, r4)     // Catch: java.lang.Throwable -> Ld4
        L4c:
            boolean r5 = r1.getNextEvent(r3)     // Catch: java.lang.Throwable -> Ld4
            r7 = 0
        L51:
            if (r5 == 0) goto Lc9
            java.lang.String r5 = r3.getPackageName()     // Catch: java.lang.Throwable -> Ld4
            boolean r16 = r2.containsKey(r5)     // Catch: java.lang.Throwable -> Ld4
            r6 = 1
            if (r16 != 0) goto L77
            long r17 = r3.getTimeStamp()     // Catch: java.lang.Throwable -> Ld4
            long r17 = r12 - r17
            r19 = 86400000(0x5265c00, double:4.2687272E-316)
            int r16 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r16 <= 0) goto L6e
            r16 = r6
            goto L70
        L6e:
            r16 = 0
        L70:
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r16)     // Catch: java.lang.Throwable -> Ld4
            r2.put(r5, r14)     // Catch: java.lang.Throwable -> Ld4
        L77:
            int r14 = r3.getEventType()     // Catch: java.lang.Throwable -> Ld4
            if (r14 != r6) goto Lbf
            com.android.server.usage.AppStandbyInternal r6 = r0.mAppStandby     // Catch: java.lang.Throwable -> Ld4
            long r14 = r6.getEstimatedLaunchTime(r5, r8)     // Catch: java.lang.Throwable -> Ld4
            int r6 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r6 < 0) goto L95
            r19 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r6 = (r14 > r19 ? 1 : (r14 == r19 ? 0 : -1))
            if (r6 != 0) goto L91
            goto L95
        L91:
            r17 = 604800000(0x240c8400, double:2.988109026E-315)
            goto Lad
        L95:
            java.lang.Object r6 = r2.get(r5)     // Catch: java.lang.Throwable -> Ld4
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.Throwable -> Ld4
            boolean r6 = r6.booleanValue()     // Catch: java.lang.Throwable -> Ld4
            long r14 = r3.getTimeStamp()     // Catch: java.lang.Throwable -> Ld4
            long r14 = calculateNextLaunchTime(r6, r14)     // Catch: java.lang.Throwable -> Ld4
            com.android.server.usage.AppStandbyInternal r6 = r0.mAppStandby     // Catch: java.lang.Throwable -> Ld4
            r6.setEstimatedLaunchTime(r5, r8, r14)     // Catch: java.lang.Throwable -> Ld4
            goto L91
        Lad:
            long r19 = r12 + r17
            int r6 = (r14 > r19 ? 1 : (r14 == r19 ? 0 : -1))
            if (r6 >= 0) goto Lb9
            boolean r6 = r0.stageChangedEstimatedLaunchTime(r8, r5)     // Catch: java.lang.Throwable -> Ld4
            r6 = r6 | r7
            r7 = r6
        Lb9:
            long r14 = r14 - r12
            long r14 = r14 + r10
            r4.addAlarm(r5, r14)     // Catch: java.lang.Throwable -> Ld4
            goto Lc2
        Lbf:
            r17 = 604800000(0x240c8400, double:2.988109026E-315)
        Lc2:
            boolean r5 = r1.getNextEvent(r3)     // Catch: java.lang.Throwable -> Ld4
            r14 = r17
            goto L51
        Lc9:
            if (r7 == 0) goto Ld2
            android.os.Handler r0 = r0.mHandler     // Catch: java.lang.Throwable -> Ld4
            r1 = 9
            r0.sendEmptyMessage(r1)     // Catch: java.lang.Throwable -> Ld4
        Ld2:
            monitor-exit(r9)     // Catch: java.lang.Throwable -> Ld4
            return
        Ld4:
            r0 = move-exception
            monitor-exit(r9)     // Catch: java.lang.Throwable -> Ld4
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.UsageStatsService.handleEstimatedLaunchTimesOnUserUnlock(int):void");
    }

    public final void setEstimatedLaunchTime(int i, String str, long j) {
        if (j > System.currentTimeMillis() && j != this.mAppStandby.getEstimatedLaunchTime(str, i)) {
            this.mAppStandby.setEstimatedLaunchTime(str, i, j);
            if (stageChangedEstimatedLaunchTime(i, str)) {
                this.mHandler.sendEmptyMessage(9);
            }
        }
    }

    public final void setEstimatedLaunchTimes(int i, List list) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            AppLaunchEstimateInfo appLaunchEstimateInfo = (AppLaunchEstimateInfo) list.get(size);
            if (appLaunchEstimateInfo.estimatedLaunchTime > currentTimeMillis) {
                long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(appLaunchEstimateInfo.packageName, i);
                long j = appLaunchEstimateInfo.estimatedLaunchTime;
                if (j != estimatedLaunchTime) {
                    this.mAppStandby.setEstimatedLaunchTime(appLaunchEstimateInfo.packageName, i, j);
                    z |= stageChangedEstimatedLaunchTime(i, appLaunchEstimateInfo.packageName);
                }
            }
        }
        if (z) {
            this.mHandler.sendEmptyMessage(9);
        }
    }

    public final boolean stageChangedEstimatedLaunchTime(int i, String str) {
        boolean add;
        synchronized (this.mPendingLaunchTimeChangePackages) {
            add = this.mPendingLaunchTimeChangePackages.add(i, str);
        }
        return add;
    }

    public final void registerListener(UsageStatsManagerInternal.UsageEventListener usageEventListener) {
        synchronized (this.mUsageEventListeners) {
            this.mUsageEventListeners.add(usageEventListener);
        }
    }

    public final void unregisterListener(UsageStatsManagerInternal.UsageEventListener usageEventListener) {
        synchronized (this.mUsageEventListeners) {
            this.mUsageEventListeners.remove(usageEventListener);
        }
    }

    public final void registerLaunchTimeChangedListener(UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
        this.mEstimatedLaunchTimeChangedListeners.add(estimatedLaunchTimeChangedListener);
    }

    public final boolean shouldDeleteObsoleteData(UserHandle userHandle) {
        DevicePolicyManagerInternal dpmInternal = getDpmInternal();
        return dpmInternal == null || dpmInternal.getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle) == null;
    }

    public final String buildFullToken(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 1);
        sb.append(str);
        sb.append('/');
        sb.append(str2);
        return sb.toString();
    }

    public final void flushToDiskLocked() {
        int size = this.mUserState.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUserState.keyAt(i);
            if (!this.mUserUnlockedStates.contains(Integer.valueOf(keyAt))) {
                persistPendingEventsLocked(keyAt);
            } else {
                UserUsageStatsService userUsageStatsService = (UserUsageStatsService) this.mUserState.get(keyAt);
                if (userUsageStatsService != null) {
                    userUsageStatsService.persistActiveStats();
                }
            }
        }
        this.mHandler.removeMessages(1);
    }

    public final void deleteUsageStats(int i) {
        synchronized (this.mLock) {
            getUserUsageStatsServiceLocked(i).deleteUsageData(getInstalledPackages(i));
        }
    }

    public void dump(String[] strArr, PrintWriter printWriter) {
        boolean z;
        boolean z2;
        int[] iArr;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (strArr != null) {
            z = false;
            z2 = false;
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                if ("--checkin".equals(str)) {
                    z = true;
                } else if ("-c".equals(str)) {
                    z2 = true;
                } else {
                    if ("flush".equals(str)) {
                        synchronized (this.mLock) {
                            flushToDiskLocked();
                        }
                        this.mAppStandby.flushToDisk();
                        printWriter.println("Flushed stats to disk");
                        return;
                    }
                    if ("is-app-standby-enabled".equals(str)) {
                        printWriter.println(this.mAppStandby.isAppIdleEnabled());
                        return;
                    }
                    if ("apptimelimit".equals(str)) {
                        synchronized (this.mLock) {
                            int i3 = i2 + 1;
                            if (i3 >= strArr.length) {
                                this.mAppTimeLimit.dump(null, printWriter);
                            } else {
                                this.mAppTimeLimit.dump((String[]) Arrays.copyOfRange(strArr, i3, strArr.length), printWriter);
                            }
                        }
                        return;
                    }
                    if ("file".equals(str)) {
                        IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            if (i2 + 1 >= strArr.length) {
                                int size = this.mUserState.size();
                                while (i < size) {
                                    int keyAt = this.mUserState.keyAt(i);
                                    if (this.mUserUnlockedStates.contains(Integer.valueOf(keyAt))) {
                                        indentingPrintWriter2.println("user=" + keyAt);
                                        indentingPrintWriter2.increaseIndent();
                                        ((UserUsageStatsService) this.mUserState.valueAt(i)).dumpFile(indentingPrintWriter2, null);
                                        indentingPrintWriter2.decreaseIndent();
                                    }
                                    i++;
                                }
                            } else {
                                int parseUserIdFromArgs = parseUserIdFromArgs(strArr, i2, indentingPrintWriter2);
                                if (parseUserIdFromArgs != -10000) {
                                    ((UserUsageStatsService) this.mUserState.get(parseUserIdFromArgs)).dumpFile(indentingPrintWriter2, (String[]) Arrays.copyOfRange(strArr, i2 + 2, strArr.length));
                                }
                            }
                        }
                        return;
                    }
                    if ("database-info".equals(str)) {
                        IndentingPrintWriter indentingPrintWriter3 = new IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            if (i2 + 1 >= strArr.length) {
                                int size2 = this.mUserState.size();
                                while (i < size2) {
                                    int keyAt2 = this.mUserState.keyAt(i);
                                    if (this.mUserUnlockedStates.contains(Integer.valueOf(keyAt2))) {
                                        indentingPrintWriter3.println("user=" + keyAt2);
                                        indentingPrintWriter3.increaseIndent();
                                        ((UserUsageStatsService) this.mUserState.valueAt(i)).dumpDatabaseInfo(indentingPrintWriter3);
                                        indentingPrintWriter3.decreaseIndent();
                                    }
                                    i++;
                                }
                            } else {
                                int parseUserIdFromArgs2 = parseUserIdFromArgs(strArr, i2, indentingPrintWriter3);
                                if (parseUserIdFromArgs2 != -10000) {
                                    ((UserUsageStatsService) this.mUserState.get(parseUserIdFromArgs2)).dumpDatabaseInfo(indentingPrintWriter3);
                                }
                            }
                        }
                        return;
                    }
                    if ("appstandby".equals(str)) {
                        this.mAppStandby.dumpState(strArr, printWriter);
                        return;
                    }
                    if ("stats-directory".equals(str)) {
                        IndentingPrintWriter indentingPrintWriter4 = new IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            int parseUserIdFromArgs3 = parseUserIdFromArgs(strArr, i2, indentingPrintWriter4);
                            if (parseUserIdFromArgs3 != -10000) {
                                indentingPrintWriter4.println(new File(Environment.getDataSystemCeDirectory(parseUserIdFromArgs3), "usagestats").getAbsolutePath());
                            }
                        }
                        return;
                    }
                    if ("mappings".equals(str)) {
                        IndentingPrintWriter indentingPrintWriter5 = new IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            int parseUserIdFromArgs4 = parseUserIdFromArgs(strArr, i2, indentingPrintWriter5);
                            if (parseUserIdFromArgs4 != -10000) {
                                ((UserUsageStatsService) this.mUserState.get(parseUserIdFromArgs4)).dumpMappings(indentingPrintWriter5);
                            }
                        }
                        return;
                    }
                    if ("broadcast-response-stats".equals(str)) {
                        synchronized (this.mLock) {
                            this.mResponseStatsTracker.dump(indentingPrintWriter);
                        }
                        return;
                    }
                    if ("app-component-usage".equals(str)) {
                        IndentingPrintWriter indentingPrintWriter6 = new IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            if (!this.mLastTimeComponentUsedGlobal.isEmpty()) {
                                indentingPrintWriter6.println("App Component Usages:");
                                indentingPrintWriter6.increaseIndent();
                                for (String str2 : this.mLastTimeComponentUsedGlobal.keySet()) {
                                    indentingPrintWriter6.println("package=" + str2 + " lastUsed=" + UserUsageStatsService.formatDateTime(((Long) this.mLastTimeComponentUsedGlobal.get(str2)).longValue(), true));
                                }
                                indentingPrintWriter6.decreaseIndent();
                            }
                        }
                        return;
                    }
                    if (str != null && !str.startsWith(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                        arrayList.add(str);
                    }
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        synchronized (this.mLock) {
            int size3 = this.mUserState.size();
            iArr = new int[size3];
            while (i < size3) {
                int keyAt3 = this.mUserState.keyAt(i);
                iArr[i] = keyAt3;
                indentingPrintWriter.printPair("user", Integer.valueOf(keyAt3));
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                if (this.mUserUnlockedStates.contains(Integer.valueOf(keyAt3))) {
                    if (z) {
                        ((UserUsageStatsService) this.mUserState.valueAt(i)).checkin(indentingPrintWriter);
                    } else {
                        ((UserUsageStatsService) this.mUserState.valueAt(i)).dump(indentingPrintWriter, arrayList, z2);
                        indentingPrintWriter.println();
                    }
                }
                indentingPrintWriter.decreaseIndent();
                i++;
            }
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("Usage Source", UsageStatsManager.usageSourceToString(this.mUsageSource));
            indentingPrintWriter.println();
            this.mAppTimeLimit.dump(null, printWriter);
            indentingPrintWriter.println();
            this.mResponseStatsTracker.dump(indentingPrintWriter);
        }
        this.mAppStandby.dumpUsers(indentingPrintWriter, iArr, arrayList);
        if (CollectionUtils.isEmpty(arrayList)) {
            printWriter.println();
            this.mAppStandby.dumpState(strArr, printWriter);
        }
    }

    public final int parseUserIdFromArgs(String[] strArr, int i, IndentingPrintWriter indentingPrintWriter) {
        try {
            int parseInt = Integer.parseInt(strArr[i + 1]);
            if (this.mUserState.indexOfKey(parseInt) < 0) {
                indentingPrintWriter.println("the specified user does not exist.");
                return -10000;
            }
            if (this.mUserUnlockedStates.contains(Integer.valueOf(parseInt))) {
                return parseInt;
            }
            indentingPrintWriter.println("the specified user is currently in a locked state.");
            return -10000;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            indentingPrintWriter.println("invalid user specified.");
            return -10000;
        }
    }

    /* loaded from: classes3.dex */
    public class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int size;
            int keyAt;
            switch (message.what) {
                case 0:
                    UsageStatsService.this.reportEvent((UsageEvents.Event) message.obj, message.arg1);
                    return;
                case 1:
                    UsageStatsService.this.flushToDisk();
                    return;
                case 2:
                    UsageStatsService.this.onUserRemoved(message.arg1);
                    return;
                case 3:
                    int i = message.arg1;
                    int i2 = message.arg2 <= 2 ? 0 : 1;
                    synchronized (UsageStatsService.this.mUidToKernelCounter) {
                        if (i2 != UsageStatsService.this.mUidToKernelCounter.get(i, 0)) {
                            UsageStatsService.this.mUidToKernelCounter.put(i, i2);
                            try {
                                FileUtils.stringToFile(UsageStatsService.KERNEL_COUNTER_FILE, i + " " + i2);
                            } catch (IOException e) {
                                Slog.w("UsageStatsService", "Failed to update counter set: " + e);
                            }
                        }
                    }
                    return;
                case 4:
                    UsageStatsService.this.reportEventToAllUserId((UsageEvents.Event) message.obj);
                    return;
                case 5:
                    try {
                        UsageStatsService.this.onUserUnlocked(message.arg1);
                        return;
                    } catch (Exception e2) {
                        if (UsageStatsService.this.mUserManager.isUserUnlocked(message.arg1)) {
                            throw e2;
                        }
                        Slog.w("UsageStatsService", "Attempted to unlock stopped or removed user " + message.arg1);
                        return;
                    }
                case 6:
                    UsageStatsService.this.onPackageRemoved(message.arg1, (String) message.obj);
                    return;
                case 7:
                    synchronized (UsageStatsService.this.mLock) {
                        UsageStatsService.this.loadGlobalComponentUsageLocked();
                    }
                    return;
                case 8:
                    UsageStatsService.this.handleEstimatedLaunchTimesOnUserUnlock(message.arg1);
                    return;
                case 9:
                    removeMessages(9);
                    ArraySet arraySet = new ArraySet();
                    synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                        size = UsageStatsService.this.mPendingLaunchTimeChangePackages.size();
                    }
                    for (int i3 = size - 1; i3 >= 0; i3--) {
                        arraySet.clear();
                        synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                            keyAt = UsageStatsService.this.mPendingLaunchTimeChangePackages.keyAt(i3);
                            arraySet.addAll(UsageStatsService.this.mPendingLaunchTimeChangePackages.get(keyAt));
                            UsageStatsService.this.mPendingLaunchTimeChangePackages.remove(keyAt);
                        }
                        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                            String str = (String) arraySet.valueAt(size2);
                            long estimatedPackageLaunchTime = UsageStatsService.this.getEstimatedPackageLaunchTime(keyAt, str);
                            Iterator it = UsageStatsService.this.mEstimatedLaunchTimeChangedListeners.iterator();
                            while (it.hasNext()) {
                                ((UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener) it.next()).onEstimatedLaunchTimeChanged(keyAt, str, estimatedPackageLaunchTime);
                            }
                        }
                    }
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    public void clearLastUsedTimestamps(String str, int i) {
        this.mAppStandby.clearLastUsedTimestampsForTest(str, i);
    }

    /* loaded from: classes3.dex */
    public final class BinderService extends IUsageStatsManager.Stub {
        public BinderService() {
        }

        public final boolean hasPermission(String str) {
            int callingUid = Binder.getCallingUid();
            if (callingUid == 1000) {
                return true;
            }
            int noteOp = UsageStatsService.this.mAppOps.noteOp(43, callingUid, str);
            return noteOp == 3 ? UsageStatsService.this.getContext().checkCallingPermission("android.permission.PACKAGE_USAGE_STATS") == 0 : noteOp == 0;
        }

        public final boolean hasObserverPermission() {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (callingUid != 1000) {
                return (dpmInternal != null && (dpmInternal.isActiveProfileOwner(callingUid) || dpmInternal.isActiveDeviceOwner(callingUid))) || UsageStatsService.this.getContext().checkCallingPermission("android.permission.OBSERVE_APP_USAGE") == 0;
            }
            return true;
        }

        public final boolean hasPermissions(String... strArr) {
            if (Binder.getCallingUid() == 1000) {
                return true;
            }
            Context context = UsageStatsService.this.getContext();
            boolean z = true;
            for (String str : strArr) {
                z = z && context.checkCallingPermission(str) == 0;
            }
            return z;
        }

        public final void checkCallerIsSystemOrSameApp(String str) {
            if (isCallingUidSystem()) {
                return;
            }
            checkCallerIsSameApp(str);
        }

        public final void checkCallerIsSameApp(String str) {
            int callingUid = Binder.getCallingUid();
            if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(callingUid)) == callingUid) {
                return;
            }
            throw new SecurityException("Calling uid " + callingUid + " cannot query eventsfor package " + str);
        }

        public final boolean isCallingUidSystem() {
            return UserHandle.getAppId(Binder.getCallingUid()) == 1000;
        }

        public ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) {
            if (!hasPermission(str)) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), callingUid, i2, false, true, "queryUsageStats", str);
            boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, UserHandle.getCallingUserId());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryUsageStats = UsageStatsService.this.queryUsageStats(handleIncomingUser, i, j, j2, shouldObfuscateInstantAppsForCaller);
                if (queryUsageStats != null) {
                    return new ParceledListSlice(queryUsageStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) {
            if (!hasPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryConfigurationStats = UsageStatsService.this.queryConfigurationStats(callingUserId, i, j, j2);
                if (queryConfigurationStats != null) {
                    return new ParceledListSlice(queryConfigurationStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ParceledListSlice queryEventStats(int i, long j, long j2, String str) {
            if (!hasPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryEventStats = UsageStatsService.this.queryEventStats(callingUserId, i, j, j2);
                if (queryEventStats != null) {
                    return new ParceledListSlice(queryEventStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v8 */
        /* JADX WARN: Type inference failed for: r3v9 */
        public UsageEvents queryEvents(long j, long j2, String str) {
            if (!hasPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, callingUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean shouldHideShortcutInvocationEvents = UsageStatsService.this.shouldHideShortcutInvocationEvents(callingUserId, str, callingPid, callingUid);
                boolean shouldHideLocusIdEvents = UsageStatsService.this.shouldHideLocusIdEvents(callingPid, callingUid);
                boolean shouldObfuscateNotificationEvents = UsageStatsService.this.shouldObfuscateNotificationEvents(callingPid, callingUid);
                ?? r3 = shouldObfuscateInstantAppsForCaller;
                if (shouldHideShortcutInvocationEvents) {
                    r3 = (shouldObfuscateInstantAppsForCaller ? 1 : 0) | 2;
                }
                if (shouldHideLocusIdEvents) {
                    r3 = (r3 == true ? 1 : 0) | '\b';
                }
                return UsageStatsService.this.queryEvents(callingUserId, j, j2, shouldObfuscateNotificationEvents ? r3 | 4 : r3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public UsageEvents queryEventsForPackage(long j, long j2, String str) {
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            checkCallerIsSameApp(str);
            boolean hasPermission = hasPermission(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return UsageStatsService.this.queryEventsForPackage(userId, j, j2, str, hasPermission);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v9 */
        public UsageEvents queryEventsForUser(long j, long j2, int i, String str) {
            if (!hasPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to query usage stats for this user");
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, callingUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean shouldHideShortcutInvocationEvents = UsageStatsService.this.shouldHideShortcutInvocationEvents(i, str, callingPid, callingUid);
                boolean shouldObfuscateNotificationEvents = UsageStatsService.this.shouldObfuscateNotificationEvents(callingPid, callingUid);
                boolean shouldHideLocusIdEvents = UsageStatsService.this.shouldHideLocusIdEvents(callingPid, callingUid);
                ?? r3 = shouldObfuscateInstantAppsForCaller;
                if (shouldHideShortcutInvocationEvents) {
                    r3 = (shouldObfuscateInstantAppsForCaller ? 1 : 0) | 2;
                }
                if (shouldHideLocusIdEvents) {
                    r3 = (r3 == true ? 1 : 0) | '\b';
                }
                return UsageStatsService.this.queryEvents(i, j, j2, shouldObfuscateNotificationEvents ? r3 | 4 : r3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) {
            if (!hasPermission(str2)) {
                return null;
            }
            if (i != UserHandle.getCallingUserId()) {
                UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to query usage stats for this user");
            }
            checkCallerIsSystemOrSameApp(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return UsageStatsService.this.queryEventsForPackage(i, j, j2, str, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAppStandbyEnabled() {
            return UsageStatsService.this.mAppStandby.isAppIdleEnabled();
        }

        public boolean isAppInactive(String str, int i, String str2) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "isAppInactive", (String) null);
                if (str.equals(str2)) {
                    if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(str2, 0L, handleIncomingUser) != callingUid) {
                        return false;
                    }
                } else if (!hasPermission(str2)) {
                    return false;
                }
                boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.isAppIdleFiltered(str, handleIncomingUser, SystemClock.elapsedRealtime(), shouldObfuscateInstantAppsForCaller);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setAppInactive(String str, boolean z, int i) {
            try {
                int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, "setAppInactive", (String) null);
                UsageStatsService.this.getContext().enforceCallingPermission("android.permission.CHANGE_APP_IDLE_STATE", "No permission to change app idle state");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (UsageStatsService.this.mAppStandby.getAppId(str) < 0) {
                        return;
                    }
                    UsageStatsService.this.mAppStandby.setAppIdleAsync(str, z, handleIncomingUser);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getAppStandbyBucket(String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (String) null);
                int packageUid = UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, handleIncomingUser);
                boolean z = packageUid == callingUid;
                if (!z && !hasPermission(str2)) {
                    throw new SecurityException("Don't have permission to query app standby bucket");
                }
                boolean isInstantApp = UsageStatsService.this.isInstantApp(str, handleIncomingUser);
                boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                if (packageUid < 0 || (!z && isInstantApp && shouldObfuscateInstantAppsForCaller)) {
                    throw new IllegalArgumentException("Cannot get standby bucket for non existent package (" + str + ")");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.getAppStandbyBucket(str, handleIncomingUser, SystemClock.elapsedRealtime(), false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setAppStandbyBucket(String str, int i, int i2) {
            super.setAppStandbyBucket_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.mAppStandby.setAppStandbyBucket(str, i, i2, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ParceledListSlice getAppStandbyBuckets(String str, int i) {
            final int callingUid = Binder.getCallingUid();
            try {
                final int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (String) null);
                if (!hasPermission(str)) {
                    throw new SecurityException("Don't have permission to query app standby bucket");
                }
                final boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List appStandbyBuckets = UsageStatsService.this.mAppStandby.getAppStandbyBuckets(handleIncomingUser);
                    if (appStandbyBuckets == null) {
                        return ParceledListSlice.emptyList();
                    }
                    appStandbyBuckets.removeIf(new Predicate() { // from class: com.android.server.usage.UsageStatsService$BinderService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$getAppStandbyBuckets$0;
                            lambda$getAppStandbyBuckets$0 = UsageStatsService.BinderService.this.lambda$getAppStandbyBuckets$0(callingUid, handleIncomingUser, shouldObfuscateInstantAppsForCaller, (AppStandbyInfo) obj);
                            return lambda$getAppStandbyBuckets$0;
                        }
                    });
                    return new ParceledListSlice(appStandbyBuckets);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$getAppStandbyBuckets$0(int i, int i2, boolean z, AppStandbyInfo appStandbyInfo) {
            return !UsageStatsService.this.sameApp(i, i2, appStandbyInfo.mPackageName) && UsageStatsService.this.isInstantApp(appStandbyInfo.mPackageName, i2) && z;
        }

        public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) {
            super.setAppStandbyBuckets_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.mAppStandby.setAppStandbyBuckets(parceledListSlice.getList(), i, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getAppMinStandbyBucket(String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (String) null);
                int packageUid = UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, handleIncomingUser);
                if (packageUid != callingUid && !hasPermission(str2)) {
                    throw new SecurityException("Don't have permission to query min app standby bucket");
                }
                boolean isInstantApp = UsageStatsService.this.isInstantApp(str, handleIncomingUser);
                boolean shouldObfuscateInstantAppsForCaller = UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                if (packageUid < 0 || (isInstantApp && shouldObfuscateInstantAppsForCaller)) {
                    throw new IllegalArgumentException("Cannot get min standby bucket for non existent package (" + str + ")");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.getAppMinStandbyBucket(str, UserHandle.getAppId(packageUid), handleIncomingUser, false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setEstimatedLaunchTime(String str, long j, int i) {
            super.setEstimatedLaunchTime_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.setEstimatedLaunchTime(i, str, j);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) {
            super.setEstimatedLaunchTimes_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.setEstimatedLaunchTimes(i, parceledListSlice.getList());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void onCarrierPrivilegedAppsChanged() {
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.BIND_CARRIER_SERVICES", "onCarrierPrivilegedAppsChanged can only be called by privileged apps.");
            UsageStatsService.this.mAppStandby.clearCarrierPrivilegedApps();
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(UsageStatsService.this.getContext(), "UsageStatsService", printWriter)) {
                UsageStatsService.this.dump(strArr, printWriter);
            }
        }

        public void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) {
            if (str == null) {
                throw new IllegalArgumentException("Package selection must not be null.");
            }
            if (str2 == null || str2.isBlank() || str3 == null || str3.isBlank()) {
                return;
            }
            if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, i) < 0) {
                Slog.w("UsageStatsService", "Event report user selecting an invalid package");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(9, SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mAction = str3;
            event.mContentType = str2;
            event.mContentAnnotations = strArr;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public void reportUserInteraction(String str, int i) {
            Objects.requireNonNull(str);
            if (!isCallingUidSystem()) {
                throw new SecurityException("Only system is allowed to call reportUserInteraction");
            }
            UsageEvents.Event event = new UsageEvents.Event(7, SystemClock.elapsedRealtime());
            event.mPackage = str;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null) {
                throw new NullPointerException("callbackIntent can't be null");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerAppUsageObserver(callingUid, i, strArr, j, pendingIntent, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterAppUsageObserver(int i, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.unregisterAppUsageObserver(callingUid, i, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one observed entity");
            }
            if (pendingIntent == null) {
                throw new NullPointerException("limitReachedCallbackIntent can't be null");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerUsageSessionObserver(callingUid, i, strArr, j, j2, pendingIntent, pendingIntent2, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterUsageSessionObserver(int i, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.unregisterUsageSessionObserver(callingUid, i, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (!hasPermissions("android.permission.SUSPEND_APPS", "android.permission.OBSERVE_APP_USAGE") && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new SecurityException("Caller must be the active supervision app or it must have both SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null && j2 < j) {
                throw new NullPointerException("callbackIntent can't be null");
            }
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerAppUsageLimitObserver(callingUid, i, strArr, j, j2, pendingIntent, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterAppUsageLimitObserver(int i, String str) {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (!hasPermissions("android.permission.SUSPEND_APPS", "android.permission.OBSERVE_APP_USAGE") && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new SecurityException("Caller must be the active supervision app or it must have both SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.unregisterAppUsageLimitObserver(callingUid, i, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void reportUsageStart(IBinder iBinder, String str, String str2) {
            reportPastUsageStart(iBinder, str, 0L, str2);
        }

        public void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) {
            ArraySet arraySet;
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UsageStatsService.this.mUsageReporters) {
                    arraySet = (ArraySet) UsageStatsService.this.mUsageReporters.get(iBinder.hashCode());
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                        UsageStatsService.this.mUsageReporters.put(iBinder.hashCode(), arraySet);
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.add(str)) {
                        throw new IllegalArgumentException(str + " for " + str2 + " is already reported as started for this activity");
                    }
                }
                UsageStatsService usageStatsService = UsageStatsService.this;
                usageStatsService.mAppTimeLimit.noteUsageStart(usageStatsService.buildFullToken(str2, str), userId, j);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void reportUsageStop(IBinder iBinder, String str, String str2) {
            ArraySet arraySet;
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UsageStatsService.this.mUsageReporters) {
                    arraySet = (ArraySet) UsageStatsService.this.mUsageReporters.get(iBinder.hashCode());
                    if (arraySet == null) {
                        throw new IllegalArgumentException("Unknown reporter trying to stop token " + str + " for " + str2);
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.remove(str)) {
                        throw new IllegalArgumentException(str + " for " + str2 + " is already reported as stopped for this activity");
                    }
                }
                UsageStatsService usageStatsService = UsageStatsService.this;
                usageStatsService.mAppTimeLimit.noteUsageStop(usageStatsService.buildFullToken(str2, str), userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getUsageSource() {
            int i;
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            synchronized (UsageStatsService.this.mLock) {
                i = UsageStatsService.this.mUsageSource;
            }
            return i;
        }

        public void forceUsageSourceSettingRead() {
            UsageStatsService.this.readUsageSourceSetting();
        }

        public long getLastTimeAnyComponentUsed(String str, String str2) {
            long millis;
            if (!hasPermissions("android.permission.INTERACT_ACROSS_USERS")) {
                throw new SecurityException("Caller doesn't have INTERACT_ACROSS_USERS permission");
            }
            if (!hasPermission(str2)) {
                throw new SecurityException("Don't have permission to query usage stats");
            }
            synchronized (UsageStatsService.this.mLock) {
                long longValue = ((Long) UsageStatsService.this.mLastTimeComponentUsedGlobal.getOrDefault(str, 0L)).longValue();
                TimeUnit timeUnit = TimeUnit.DAYS;
                millis = (longValue / timeUnit.toMillis(1L)) * timeUnit.toMillis(1L);
            }
            return millis;
        }

        public BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) {
            Objects.requireNonNull(str2);
            if (j < 0) {
                throw new IllegalArgumentException("id needs to be >=0");
            }
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "queryBroadcastResponseStats");
            int callingUid = Binder.getCallingUid();
            return new BroadcastResponseStatsList(UsageStatsService.this.mResponseStatsTracker.queryBroadcastResponseStats(callingUid, str, j, ActivityManager.handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "queryBroadcastResponseStats", str2)));
        }

        public void clearBroadcastResponseStats(String str, long j, String str2, int i) {
            Objects.requireNonNull(str2);
            if (j < 0) {
                throw new IllegalArgumentException("id needs to be >=0");
            }
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "clearBroadcastResponseStats");
            int callingUid = Binder.getCallingUid();
            UsageStatsService.this.mResponseStatsTracker.clearBroadcastResponseStats(callingUid, str, j, ActivityManager.handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "clearBroadcastResponseStats", str2));
        }

        public void clearBroadcastEvents(String str, int i) {
            Objects.requireNonNull(str);
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "clearBroadcastEvents");
            int callingUid = Binder.getCallingUid();
            UsageStatsService.this.mResponseStatsTracker.clearBroadcastEvents(callingUid, ActivityManager.handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "clearBroadcastResponseStats", str));
        }

        public String getAppStandbyConstant(String str) {
            Objects.requireNonNull(str);
            if (!hasPermissions("android.permission.READ_DEVICE_CONFIG")) {
                throw new SecurityException("Caller doesn't have READ_DEVICE_CONFIG permission");
            }
            return UsageStatsService.this.mAppStandby.getAppStandbyConstant(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            return new UsageStatsShellCommand(UsageStatsService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public void deleteUsageStats() {
            if (UserHandle.getAppId(Binder.getCallingUid()) == 1000) {
                UsageStatsService.this.deleteUsageStats(0);
            }
        }

        public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                if (!UsageStatsService.this.mUsageStatsWatchers.containsKey(iUsageStatsWatcher.asBinder())) {
                    UsageStatsService.this.mUsageStatsWatchers.put(iUsageStatsWatcher.asBinder(), iUsageStatsWatcher);
                }
            }
        }

        public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List list) {
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                if (!UsageStatsService.this.mUsageStatsWatchers.containsKey(iUsageStatsWatcher.asBinder())) {
                    UsageStatsService.this.mUsageStatsWatchers.put(iUsageStatsWatcher.asBinder(), iUsageStatsWatcher);
                    if (list != null) {
                        UsageStatsService.this.mUsageStatsWatchersComponent.put(iUsageStatsWatcher.asBinder(), list);
                    }
                }
            }
        }

        public void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
            UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                if (UsageStatsService.this.mUsageStatsWatchers.containsKey(iUsageStatsWatcher.asBinder())) {
                    UsageStatsService.this.mUsageStatsWatchers.remove(iUsageStatsWatcher.asBinder());
                    UsageStatsService.this.mUsageStatsWatchersComponent.remove(iUsageStatsWatcher.asBinder());
                }
            }
        }
    }

    public void registerAppUsageObserver(int i, int i2, String[] strArr, long j, PendingIntent pendingIntent, int i3) {
        this.mAppTimeLimit.addAppUsageObserver(i, i2, strArr, j, pendingIntent, i3);
    }

    public void unregisterAppUsageObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeAppUsageObserver(i, i2, i3);
    }

    public void registerUsageSessionObserver(int i, int i2, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i3) {
        this.mAppTimeLimit.addUsageSessionObserver(i, i2, strArr, j, j2, pendingIntent, pendingIntent2, i3);
    }

    public void unregisterUsageSessionObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeUsageSessionObserver(i, i2, i3);
    }

    public void registerAppUsageLimitObserver(int i, int i2, String[] strArr, long j, long j2, PendingIntent pendingIntent, int i3) {
        this.mAppTimeLimit.addAppUsageLimitObserver(i, i2, strArr, j, j2, pendingIntent, i3);
    }

    public void unregisterAppUsageLimitObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeAppUsageLimitObserver(i, i2, i3);
    }

    /* loaded from: classes3.dex */
    public final class LocalService extends UsageStatsManagerInternal {
        public LocalService() {
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportEvent(ComponentName componentName, int i, int i2, int i3, ComponentName componentName2) {
            if (componentName == null) {
                Slog.w("UsageStatsService", "Event reported without a component name");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(i2, SystemClock.elapsedRealtime());
            event.mPackage = componentName.getPackageName();
            event.mClass = componentName.getClassName();
            event.mInstanceId = i3;
            if (componentName2 == null) {
                event.mTaskRootPackage = null;
                event.mTaskRootClass = null;
            } else {
                event.mTaskRootPackage = componentName2.getPackageName();
                event.mTaskRootClass = componentName2.getClassName();
            }
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportEvent(String str, int i, int i2) {
            if (str == null) {
                Slog.w("UsageStatsService", "Event reported without a package name, eventType:" + i2);
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(i2, SystemClock.elapsedRealtime());
            event.mPackage = str;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportEvent(ComponentName componentName, int i, int i2, int i3, ComponentName componentName2, Intent intent) {
            List list;
            if (componentName == null) {
                Slog.w("UsageStatsService", "Event reported without a component name");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(i2, SystemClock.elapsedRealtime());
            event.mPackage = componentName.getPackageName();
            event.mClass = componentName.getClassName();
            event.mInstanceId = i3;
            if (componentName2 == null) {
                event.mTaskRootPackage = null;
                event.mTaskRootClass = null;
            } else {
                event.mTaskRootPackage = componentName2.getPackageName();
                event.mTaskRootClass = componentName2.getClassName();
            }
            UsageStatsService.this.mHandler.obtainMessage(0, i, 0, event).sendToTarget();
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                ArrayList arrayList = new ArrayList();
                if (i2 == 1 || i2 == 2 || i2 == 23 || i2 == 24) {
                    for (Map.Entry entry : UsageStatsService.this.mUsageStatsWatchers.entrySet()) {
                        IBinder iBinder = (IBinder) entry.getKey();
                        try {
                            IUsageStatsWatcher iUsageStatsWatcher = (IUsageStatsWatcher) entry.getValue();
                            if (UsageStatsService.this.mUsageStatsWatchersComponent.containsKey(iBinder) && (list = (List) UsageStatsService.this.mUsageStatsWatchersComponent.get(iBinder)) != null && !list.contains(componentName)) {
                                iUsageStatsWatcher = null;
                            }
                            if (iUsageStatsWatcher != null) {
                                if (i2 == 1) {
                                    iUsageStatsWatcher.noteResumeComponent(componentName, intent, i3, i);
                                } else if (i2 == 2) {
                                    iUsageStatsWatcher.notePauseComponent(componentName, intent, i3, i);
                                } else if (i2 == 23 || i2 == 24) {
                                    iUsageStatsWatcher.noteStopComponent(componentName, intent, i3, i);
                                }
                            }
                        } catch (RemoteException unused) {
                            arrayList.add(iBinder);
                        }
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    IBinder iBinder2 = (IBinder) it.next();
                    UsageStatsService.this.mUsageStatsWatchers.remove(iBinder2);
                    UsageStatsService.this.mUsageStatsWatchersComponent.remove(iBinder2);
                }
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportConfigurationChange(Configuration configuration, int i) {
            if (configuration == null) {
                Slog.w("UsageStatsService", "Configuration event reported with a null config");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(5, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            event.mConfiguration = new Configuration(configuration);
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportInterruptiveNotification(String str, String str2, int i) {
            if (str == null || str2 == null) {
                Slog.w("UsageStatsService", "Event reported without a package name or a channel ID");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(12, SystemClock.elapsedRealtime());
            event.mPackage = str.intern();
            event.mNotificationChannelId = str2.intern();
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportShortcutUsage(String str, String str2, int i) {
            if (str == null || str2 == null) {
                Slog.w("UsageStatsService", "Event reported without a package name or a shortcut ID");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(8, SystemClock.elapsedRealtime());
            event.mPackage = str.intern();
            event.mShortcutId = str2.intern();
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportLocusUpdate(ComponentName componentName, int i, LocusId locusId, IBinder iBinder) {
            if (locusId == null) {
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(30, SystemClock.elapsedRealtime());
            event.mLocusId = locusId.getId();
            event.mPackage = componentName.getPackageName();
            event.mClass = componentName.getClassName();
            event.mInstanceId = iBinder.hashCode();
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportContentProviderUsage(String str, String str2, int i) {
            UsageStatsService.this.mAppStandby.postReportContentProviderUsage(str, str2, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean isAppIdle(String str, int i, int i2) {
            return UsageStatsService.this.mAppStandby.isAppIdleFiltered(str, i, i2, SystemClock.elapsedRealtime());
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public int getAppStandbyBucket(String str, int i, long j) {
            return UsageStatsService.this.mAppStandby.getAppStandbyBucket(str, i, j, false);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public int[] getIdleUidsForUser(int i) {
            return UsageStatsService.this.mAppStandby.getIdleUidsForUser(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void prepareShutdown() {
            UsageStatsService.this.shutdown();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void prepareForPossibleShutdown() {
            UsageStatsService.this.prepareForPossibleShutdown();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public byte[] getBackupPayload(int i, String str) {
            if (!UsageStatsService.this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                Slog.w("UsageStatsService", "Failed to get backup payload for locked user " + i);
                return null;
            }
            synchronized (UsageStatsService.this.mLock) {
                UserUsageStatsService userUsageStatsServiceLocked = UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                Slog.i("UsageStatsService", "Returning backup payload for u=" + i);
                return userUsageStatsServiceLocked.getBackupPayload(str);
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void applyRestoredPayload(int i, String str, byte[] bArr) {
            synchronized (UsageStatsService.this.mLock) {
                if (!UsageStatsService.this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w("UsageStatsService", "Failed to apply restored payload for locked user " + i);
                    return;
                }
                UserUsageStatsService userUsageStatsServiceLocked = UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return;
                }
                UsageStatsService.this.mAppStandby.restoreAppsToRare(userUsageStatsServiceLocked.applyRestoredPayload(str, bArr), i);
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public List queryUsageStatsForUser(int i, int i2, long j, long j2, boolean z) {
            return UsageStatsService.this.queryUsageStats(i, i2, j, j2, z);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public UsageEvents queryEventsForUser(int i, long j, long j2, int i2) {
            return UsageStatsService.this.queryEvents(i, j, j2, i2);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setLastJobRunTime(String str, int i, long j) {
            UsageStatsService.this.mAppStandby.setLastJobRunTime(str, i, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public long getEstimatedPackageLaunchTime(String str, int i) {
            return UsageStatsService.this.getEstimatedPackageLaunchTime(i, str);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public long getTimeSinceLastJobRun(String str, int i) {
            return UsageStatsService.this.mAppStandby.getTimeSinceLastJobRun(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void onActiveAdminAdded(String str, int i) {
            UsageStatsService.this.mAppStandby.addActiveDeviceAdmin(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setActiveAdminApps(Set set, int i) {
            UsageStatsService.this.mAppStandby.setActiveAdminApps(set, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setAdminProtectedPackages(Set set, int i) {
            UsageStatsService.this.mAppStandby.setAdminProtectedPackages(set, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void onAdminDataAvailable() {
            UsageStatsService.this.mAppStandby.onAdminDataAvailable();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportSyncScheduled(String str, int i, boolean z) {
            UsageStatsService.this.mAppStandby.postReportSyncScheduled(str, i, z);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportExemptedSyncStart(String str, int i) {
            UsageStatsService.this.mAppStandby.postReportExemptedSyncStart(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public UsageStatsManagerInternal.AppUsageLimitData getAppUsageLimit(String str, UserHandle userHandle) {
            return UsageStatsService.this.mAppTimeLimit.getAppUsageLimit(str, userHandle);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean pruneUninstalledPackagesData(int i) {
            return UsageStatsService.this.pruneUninstalledPackagesData(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean updatePackageMappingsData(int i) {
            return UsageStatsService.this.updatePackageMappingsData(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void registerListener(UsageStatsManagerInternal.UsageEventListener usageEventListener) {
            UsageStatsService.this.registerListener(usageEventListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void unregisterListener(UsageStatsManagerInternal.UsageEventListener usageEventListener) {
            UsageStatsService.this.unregisterListener(usageEventListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void registerLaunchTimeChangedListener(UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
            UsageStatsService.this.registerLaunchTimeChangedListener(estimatedLaunchTimeChangedListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportBroadcastDispatched(int i, String str, UserHandle userHandle, long j, long j2, int i2) {
            UsageStatsService.this.mResponseStatsTracker.reportBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationPosted(String str, UserHandle userHandle, long j) {
            UsageStatsService.this.mResponseStatsTracker.reportNotificationPosted(str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationUpdated(String str, UserHandle userHandle, long j) {
            UsageStatsService.this.mResponseStatsTracker.reportNotificationUpdated(str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationRemoved(String str, UserHandle userHandle, long j) {
            UsageStatsService.this.mResponseStatsTracker.reportNotificationCancelled(str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void deleteDailyStatsFiles() {
            int size = UsageStatsService.this.mUserState.size();
            for (int i = 0; i < size; i++) {
                UserUsageStatsService userUsageStatsService = (UserUsageStatsService) UsageStatsService.this.mUserState.get(UsageStatsService.this.mUserState.keyAt(i));
                if (userUsageStatsService != null) {
                    userUsageStatsService.deleteDailyStatsFiles();
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {
        }

        public void onPackageRemoved(String str, int i) {
            int changingUserId = getChangingUserId();
            if (UsageStatsService.this.shouldDeleteObsoleteData(UserHandle.of(changingUserId))) {
                UsageStatsService.this.mHandler.obtainMessage(6, changingUserId, 0, str).sendToTarget();
            }
            UsageStatsService.this.mResponseStatsTracker.onPackageRemoved(str, UserHandle.getUserId(i));
            super.onPackageRemoved(str, i);
        }
    }
}

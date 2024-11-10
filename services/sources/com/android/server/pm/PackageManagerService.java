package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.ApplicationPackageManager;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.admin.IDevicePolicyManager;
import android.app.admin.SecurityLog;
import android.app.backup.IBackupManager;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ASKSManager;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.ComponentInfo;
import android.content.pm.FallbackCategoryProvider;
import android.content.pm.FeatureInfo;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IncrementalStatesInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstantAppRequest;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackagePartitions;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.TestUtilityService;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.overlay.OverlayPaths;
import android.content.pm.parsing.PackageLite;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ReconcileSdkDataArgs;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.os.incremental.PerUidReadTimeouts;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.permission.PermissionManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import com.android.internal.app.ResolverActivity;
import com.android.internal.content.F2fsUtils;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.telephony.CarrierAppUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.permission.persistence.RuntimePermissionsPersistence;
import com.android.server.FgThread;
import com.android.server.HeimdAllFsService;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.PackageWatchdog;
import com.android.server.ServiceThread;
import com.android.server.SpegService;
import com.android.server.SystemConfig;
import com.android.server.Watchdog;
import com.android.server.apphibernation.AppHibernationManagerInternal;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.baiducarlife.BaiduCarlifeADBConnectUtils;
import com.android.server.clipboard.ClipboardService;
import com.android.server.compat.CompatChange;
import com.android.server.compat.PlatformCompat;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda1;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.ApkChecksums;
import com.android.server.pm.CompilerStats;
import com.android.server.pm.Installer;
import com.android.server.pm.MovePackageHelper;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.android.server.pm.PerPackageReadTimeouts;
import com.android.server.pm.Settings;
import com.android.server.pm.dex.ArtManagerService;
import com.android.server.pm.dex.ArtUtils;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.dex.DynamicCodeLogger;
import com.android.server.pm.dex.ViewCompiler;
import com.android.server.pm.local.PackageManagerLocalImpl;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.permission.LegacyPermissionManagerInternal;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.component.ParsedInstrumentation;
import com.android.server.pm.pkg.component.ParsedMainComponent;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.pm.pkg.mutate.PackageStateWrite;
import com.android.server.pm.pkg.mutate.PackageUserStateWrite;
import com.android.server.pm.pkg.parsing.ParsingPackageUtils;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxy;
import com.android.server.storage.DeviceStorageMonitorInternal;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedSparseBooleanArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.utils.Watcher;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.MetaDataHelper;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.PmServerUtils;
import com.samsung.android.server.pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.lifecycle.PmCustomInjector;
import com.samsung.android.server.pm.lifecycle.PmLifecycleImpl;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import com.samsung.android.server.pm.monetization.MonetizationUtils;
import dalvik.system.VMRuntime;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;

/* loaded from: classes3.dex */
public class PackageManagerService implements PackageSender, TestUtilityService {
    public static final Handler.Callback BACKGROUND_HANDLER_CALLBACK;
    public static final boolean DEBUG_COMPRESSION;
    public static final boolean DEBUG_INSTANT;
    public static final long DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD;
    public static final int[] EMPTY_INT_ARRAY;
    public static final PerUidReadTimeouts[] EMPTY_PER_UID_READ_TIMEOUTS_ARRAY;
    public static final boolean FORCE_SPEG;
    public static final long FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD;
    public static final long PRUNE_UNUSED_SHARED_LIBRARIES_DELAY;
    public static final List SYSTEM_PARTITIONS;
    public static PersonaManagerService sPersonaManager;
    public static final AtomicReference sSnapshot;
    public static final AtomicInteger sSnapshotPendingVersion;
    public final String mAmbientContextDetectionPackage;
    public ApplicationInfo mAndroidApplication;
    public final ApexManager mApexManager;
    public final AppDataHelper mAppDataHelper;
    public final File mAppInstallDir;
    public final String mAppPredictionServicePackage;
    public final AppsFilterImpl mAppsFilter;
    public final ArtManagerService mArtManagerService;
    public final AsecInstallHelper mAsecInstallHelper;
    public AutoDisableHandler mAutoDisableHandler;
    public final ArrayMap mAvailableFeatures;
    public final BackgroundDexOptService mBackgroundDexOptService;
    public final Handler mBackgroundHandler;
    public final BroadcastHelper mBroadcastHelper;
    public File mCacheDir;
    public final ChangedPackagesTracker mChangedPackagesTracker;
    public final CompilerStats mCompilerStats;
    public final ComponentResolver mComponentResolver;
    public final String mConfiguratorPackage;
    public final Context mContext;
    public final PmCustomInjector mCustomInjector;
    public ComponentName mCustomResolverComponentName;
    public final int mDefParseFlags;
    public final DefaultAppProvider mDefaultAppProvider;
    public final String mDefaultTextClassifierPackage;
    public final DeletePackageHelper mDeletePackageHelper;
    public IDevicePolicyManager mDevicePolicyManager;
    public final DexManager mDexManager;
    public final DexOptHelper mDexOptHelper;
    public final ArraySet mDirtyUsers;
    public final DistractingPackageHelper mDistractingPackageHelper;
    public final DomainVerificationConnection mDomainVerificationConnection;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final DynamicCodeLogger mDynamicCodeLogger;
    public EmergencyModePackageHandler mEmergencyModePackageHandler;
    public final boolean mEnableFreeCacheV2;
    public ArraySet mExistingPackages;
    public PackageManagerInternal.ExternalSourcesPolicy mExternalSourcesPolicy;
    public final boolean mFactoryTest;
    public boolean mFirstBoot;
    public final FrozenPackageInterceptor mFrozenPackageInterceptor;
    public final WatchedArrayMap mFrozenPackages;
    public final SnapshotCache mFrozenPackagesSnapshot;
    public final Handler mHandler;
    public final String mIncidentReportApproverPackage;
    public final IncrementalManager mIncrementalManager;
    public final String mIncrementalVersion;
    public final InitAppsHelper mInitAppsHelper;
    public final Set mInitialNonStoppedSystemPackages;
    public final PackageManagerServiceInjector mInjector;
    public final Object mInstallLock;
    public final InstallPackageHelper mInstallPackageHelper;
    public final Installer mInstaller;
    public final PackageInstallerService mInstallerService;
    public ActivityInfo mInstantAppInstallerActivity;
    public final ResolveInfo mInstantAppInstallerInfo;
    public final InstantAppRegistry mInstantAppRegistry;
    public final InstantAppResolverConnection mInstantAppResolverConnection;
    public final ComponentName mInstantAppResolverSettingsComponent;
    public final WatchedArrayMap mInstrumentation;
    public final SnapshotCache mInstrumentationSnapshot;
    public final boolean mIsEngBuild;
    public final boolean mIsPreNMR1Upgrade;
    public final boolean mIsPreQUpgrade;
    public final boolean mIsUpgrade;
    public final boolean mIsUserDebugBuild;
    public final WatchedSparseIntArray mIsolatedOwners;
    public final SnapshotCache mIsolatedOwnersSnapshot;
    public final ArraySet mKeepUninstalledPackages;
    public final LegacyPermissionManagerInternal mLegacyPermissionManager;
    public ComputerLocked mLiveComputer;
    public final PackageManagerTracedLock mLock;
    public final DisplayMetrics mMetrics;
    public final ModuleInfoProvider mModuleInfoProvider;
    public MonetizationUtils mMonetizationUtils;
    public final MovePackageHelper.MoveCallbacks mMoveCallbacks;
    public int mNextInstallToken;
    public final AtomicInteger mNextMoveId;
    public final Map mNoKillInstallObservers;
    public final OverlayConfig mOverlayConfig;
    public final String mOverlayConfigSignaturePackage;
    public final PackageDexOptimizer mPackageDexOptimizer;
    public final PackageObserverHelper mPackageObserverHelper;
    public final PackageParser2.Callback mPackageParserCallback;
    public final PackageProperty mPackageProperty;
    public final PackageManagerTracedLock mPackageStateWriteLock;
    public final PackageUsage mPackageUsage;
    public final WatchedArrayMap mPackages;
    public final SnapshotCache mPackagesSnapshot;
    public final PendingPackageBroadcasts mPendingBroadcasts;
    public final SparseArray mPendingEnableRollback;
    public int mPendingEnableRollbackToken;
    public final Map mPendingKillInstallObservers;
    public final SparseArray mPendingVerification;
    public int mPendingVerificationToken;
    public PerUidReadTimeouts[] mPerUidReadTimeoutsCache;
    public final PermissionManagerServiceInternal mPermissionManager;
    public AndroidPackage mPlatformPackage;
    public String[] mPlatformPackageOverlayPaths;
    public String[] mPlatformPackageOverlayResourceDirs;
    public final PmLifecycleImpl mPmLifecycle;
    public final PreferredActivityHelper mPreferredActivityHelper;
    public Future mPrepareAppDataFuture;
    public final ProcessLoggingHandler mProcessLoggingHandler;
    public boolean mPromoteSystemApps;
    public final ArraySet mProtectedBroadcasts;
    public final ProtectedPackages mProtectedPackages;
    public final String mRecentsPackage;
    public List mReleaseOnSystemReady;
    public final RemovePackageHelper mRemovePackageHelper;
    public String[] mReplacedResolverPackageOverlayPaths;
    public String[] mReplacedResolverPackageOverlayResourceDirs;
    public final String mRequiredInstallerPackage;
    public final String mRequiredPermissionControllerPackage;
    public final String mRequiredSdkSandboxPackage;
    public final Set mRequiredSystemPackages;
    public final String mRequiredUninstallerPackage;
    public final String[] mRequiredVerifierPackages;
    public final ActivityInfo mResolveActivity;
    public ComponentName mResolveComponentName;
    public final ResolveInfo mResolveInfo;
    public final ResolveIntentHelper mResolveIntentHelper;
    public boolean mResolverReplaced;
    public final String mRetailDemoPackage;
    public final SparseArray mRunningInstalls;
    public volatile boolean mSafeMode;
    public final int mSdkVersion;
    public final String[] mSeparateProcesses;
    public long mServiceStartWithDelay;
    public final String mServicesExtensionPackageName;
    public final Settings mSettings;
    public final String mSetupWizardPackage;
    public final SharedLibrariesImpl mSharedLibraries;
    public final String mSharedSystemSharedLibraryPackageName;
    public final boolean mShouldStopSystemPackagesByDefault;
    public final Object mSnapshotLock;
    public final SnapshotStatistics mSnapshotStatistics;
    public SpegService mSpeg;
    public final StorageEventHelper mStorageEventHelper;
    public final String mStorageManagerPackage;
    public final SuspendPackageHelper mSuspendPackageHelper;
    public volatile boolean mSystemReady;
    public final String mSystemTextClassifierPackageName;
    public final TestUtilityService mTestUtilityService;
    public final ArraySet mTransferredPackages;
    public final UserManagerService mUserManager;
    public final UserNeedsBadgingCache mUserNeedsBadging;
    public final ViewCompiler mViewCompiler;
    public final Watcher mWatcher;
    public final String mWearableSensingPackage;
    public final WatchedSparseBooleanArray mWebInstantAppsDisabled;
    public final PackageManagerTracedLock mOverlayPathsLock = new PackageManagerTracedLock();
    public final PackageStateMutator mPackageStateMutator = new PackageStateMutator(new Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return PackageManagerService.this.getPackageSettingForMutation((String) obj);
        }
    }, new Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return PackageManagerService.this.getDisabledPackageSettingForMutation((String) obj);
        }
    });

    /* loaded from: classes3.dex */
    public class FindPreferredActivityBodyResult {
        public boolean mChanged;
        public ResolveInfo mPreferredResolveInfo;
    }

    /* loaded from: classes3.dex */
    public enum InstallLocaleOverlaysType {
        PACKAGE_INSTALL,
        PACKAGE_UNINSTALL_UPDATES,
        PACKAGE_ENABLE
    }

    /* renamed from: $r8$lambda$Sl6nXlL7kE1wmpCP574m3H-7954 */
    public static /* synthetic */ SystemConfig m9463$r8$lambda$Sl6nXlL7kE1wmpCP574m3H7954(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        SystemConfig systemConfig;
        systemConfig = SystemConfig.getInstance();
        return systemConfig;
    }

    public static /* synthetic */ ApexManager $r8$lambda$yTyDl_Ki2ilKmJCBq0xuESmQ62g(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        ApexManager apexManager;
        apexManager = ApexManager.getInstance();
        return apexManager;
    }

    public static /* synthetic */ DomainVerificationManagerInternal lambda$main$34(DomainVerificationService domainVerificationService, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return domainVerificationService;
    }

    public static /* synthetic */ Handler lambda$requestChecksumsInternal$5(Handler handler) {
        return handler;
    }

    public static /* synthetic */ Handler lambda$requestFileChecksums$1(Handler handler) {
        return handler;
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
        DEBUG_COMPRESSION = z;
        DEBUG_INSTANT = z;
        EMPTY_INT_ARRAY = new int[0];
        FORCE_SPEG = !Build.IS_USER && "true".equals(SystemProperties.get("com.samsung.speg.force"));
        SYSTEM_PARTITIONS = Collections.unmodifiableList(PackagePartitions.getOrderedPartitions(new Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new ScanPartition((PackagePartitions.SystemPartition) obj);
            }
        }));
        EMPTY_PER_UID_READ_TIMEOUTS_ARRAY = new PerUidReadTimeouts[0];
        PRUNE_UNUSED_SHARED_LIBRARIES_DELAY = TimeUnit.MINUTES.toMillis(3L);
        FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = TimeUnit.HOURS.toMillis(2L);
        DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = TimeUnit.DAYS.toMillis(7L);
        sSnapshot = new AtomicReference();
        sSnapshotPendingVersion = new AtomicInteger(1);
        BACKGROUND_HANDLER_CALLBACK = new Handler.Callback() { // from class: com.android.server.pm.PackageManagerService.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                int i = message.what;
                if (i == 14) {
                    ((PackageManagerService) message.obj).writePendingRestrictions();
                    return true;
                }
                if (i != 30) {
                    return false;
                }
                ((Runnable) message.obj).run();
                return true;
            }
        };
    }

    public boolean isKidsHome() {
        String defaultHome = this.mDefaultAppProvider.getDefaultHome(0);
        return defaultHome != null && "com.sec.android.app.kidshome".equals(defaultHome);
    }

    /* loaded from: classes3.dex */
    public class DefaultSystemWrapper implements PackageManagerServiceInjector.SystemWrapper {
        public DefaultSystemWrapper() {
        }

        public /* synthetic */ DefaultSystemWrapper(DefaultSystemWrapperIA defaultSystemWrapperIA) {
            this();
        }

        @Override // com.android.server.pm.PackageManagerServiceInjector.SystemWrapper
        public void disablePackageCaches() {
            PackageManager.disableApplicationInfoCache();
            PackageManager.disablePackageInfoCache();
            ApplicationPackageManager.invalidateGetPackagesForUidCache();
            ApplicationPackageManager.disableGetPackagesForUidCache();
            ApplicationPackageManager.invalidateHasSystemFeatureCache();
            PackageManager.corkPackageInfoCache();
        }

        @Override // com.android.server.pm.PackageManagerServiceInjector.SystemWrapper
        public void enablePackageCaches() {
            PackageManager.uncorkPackageInfoCache();
        }
    }

    public static void invalidatePackageInfoCache() {
        PackageManager.invalidatePackageInfoCache();
        onChanged();
    }

    /* renamed from: com.android.server.pm.PackageManagerService$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends Watcher {
        public AnonymousClass1() {
        }

        @Override // com.android.server.utils.Watcher
        public void onChange(Watchable watchable) {
            PackageManagerService.onChange(watchable);
        }
    }

    /* loaded from: classes3.dex */
    public class Snapshot {
        public final ApplicationInfo androidApplication;
        public final String appPredictionServicePackage;
        public final AppsFilterSnapshot appsFilter;
        public final ComponentResolverApi componentResolver;
        public final WatchedArrayMap frozenPackages;
        public final ActivityInfo instantAppInstallerActivity;
        public final ResolveInfo instantAppInstallerInfo;
        public final InstantAppRegistry instantAppRegistry;
        public final WatchedArrayMap instrumentation;
        public final WatchedSparseIntArray isolatedOwners;
        public final WatchedArrayMap packages;
        public final ActivityInfo resolveActivity;
        public final ComponentName resolveComponentName;
        public final PackageManagerService service;
        public final Settings settings;
        public final SharedLibrariesRead sharedLibraries;
        public final WatchedSparseBooleanArray webInstantAppsDisabled;

        public Snapshot(int i) {
            if (i == 2) {
                this.settings = PackageManagerService.this.mSettings.snapshot();
                this.isolatedOwners = (WatchedSparseIntArray) PackageManagerService.this.mIsolatedOwnersSnapshot.snapshot();
                this.packages = (WatchedArrayMap) PackageManagerService.this.mPackagesSnapshot.snapshot();
                this.instrumentation = (WatchedArrayMap) PackageManagerService.this.mInstrumentationSnapshot.snapshot();
                ComponentName componentName = PackageManagerService.this.mResolveComponentName;
                this.resolveComponentName = componentName == null ? null : componentName.clone();
                this.resolveActivity = new ActivityInfo(PackageManagerService.this.mResolveActivity);
                this.instantAppInstallerActivity = PackageManagerService.this.mInstantAppInstallerActivity == null ? null : new ActivityInfo(PackageManagerService.this.mInstantAppInstallerActivity);
                this.instantAppInstallerInfo = new ResolveInfo(PackageManagerService.this.mInstantAppInstallerInfo);
                this.webInstantAppsDisabled = PackageManagerService.this.mWebInstantAppsDisabled.snapshot();
                this.instantAppRegistry = PackageManagerService.this.mInstantAppRegistry.snapshot();
                this.androidApplication = PackageManagerService.this.mAndroidApplication != null ? new ApplicationInfo(PackageManagerService.this.mAndroidApplication) : null;
                this.appPredictionServicePackage = PackageManagerService.this.mAppPredictionServicePackage;
                this.appsFilter = PackageManagerService.this.mAppsFilter.snapshot();
                this.componentResolver = PackageManagerService.this.mComponentResolver.snapshot();
                this.frozenPackages = (WatchedArrayMap) PackageManagerService.this.mFrozenPackagesSnapshot.snapshot();
                this.sharedLibraries = PackageManagerService.this.mSharedLibraries.snapshot();
            } else if (i == 1) {
                this.settings = PackageManagerService.this.mSettings;
                this.isolatedOwners = PackageManagerService.this.mIsolatedOwners;
                this.packages = PackageManagerService.this.mPackages;
                this.instrumentation = PackageManagerService.this.mInstrumentation;
                this.resolveComponentName = PackageManagerService.this.mResolveComponentName;
                this.resolveActivity = PackageManagerService.this.mResolveActivity;
                this.instantAppInstallerActivity = PackageManagerService.this.mInstantAppInstallerActivity;
                this.instantAppInstallerInfo = PackageManagerService.this.mInstantAppInstallerInfo;
                this.webInstantAppsDisabled = PackageManagerService.this.mWebInstantAppsDisabled;
                this.instantAppRegistry = PackageManagerService.this.mInstantAppRegistry;
                this.androidApplication = PackageManagerService.this.mAndroidApplication;
                this.appPredictionServicePackage = PackageManagerService.this.mAppPredictionServicePackage;
                this.appsFilter = PackageManagerService.this.mAppsFilter;
                this.componentResolver = PackageManagerService.this.mComponentResolver;
                this.frozenPackages = PackageManagerService.this.mFrozenPackages;
                this.sharedLibraries = PackageManagerService.this.mSharedLibraries;
            } else {
                throw new IllegalArgumentException();
            }
            this.service = PackageManagerService.this;
        }
    }

    public Computer snapshotComputer() {
        return snapshotComputer(true);
    }

    public Computer snapshotComputer(boolean z) {
        boolean holdsLock = Thread.holdsLock(this.mLock);
        if (z && holdsLock) {
            return this.mLiveComputer;
        }
        AtomicReference atomicReference = sSnapshot;
        Computer computer = (Computer) atomicReference.get();
        AtomicInteger atomicInteger = sSnapshotPendingVersion;
        int i = atomicInteger.get();
        if (computer != null && computer.getVersion() == i) {
            return computer.use();
        }
        if (holdsLock) {
            Computer rebuildSnapshot = rebuildSnapshot(computer, i);
            atomicReference.set(rebuildSnapshot);
            return rebuildSnapshot.use();
        }
        synchronized (this.mSnapshotLock) {
            Computer computer2 = (Computer) atomicReference.get();
            int i2 = atomicInteger.get();
            if (computer2 != null && computer2.getVersion() == i2) {
                return computer2.use();
            }
            synchronized (this.mLock) {
                Computer computer3 = (Computer) atomicReference.get();
                int i3 = atomicInteger.get();
                if (computer3 != null && computer3.getVersion() == i3) {
                    return computer3.use();
                }
                Computer rebuildSnapshot2 = rebuildSnapshot(computer3, i3);
                atomicReference.set(rebuildSnapshot2);
                return rebuildSnapshot2.use();
            }
        }
    }

    public final Computer rebuildSnapshot(Computer computer, int i) {
        long currentTimeMicro = SystemClock.currentTimeMicro();
        int used = computer == null ? -1 : computer.getUsed();
        ComputerEngine computerEngine = new ComputerEngine(new Snapshot(2), i);
        long currentTimeMicro2 = SystemClock.currentTimeMicro();
        SnapshotStatistics snapshotStatistics = this.mSnapshotStatistics;
        if (snapshotStatistics != null) {
            snapshotStatistics.rebuild(currentTimeMicro, currentTimeMicro2, used, computerEngine.getPackageStates().size());
        }
        return computerEngine;
    }

    public final ComputerLocked createLiveComputer() {
        return new ComputerLocked(new Snapshot(1));
    }

    public static void onChange(Watchable watchable) {
        sSnapshotPendingVersion.incrementAndGet();
    }

    public static void onChanged() {
        onChange(null);
    }

    public void notifyInstallObserver(String str, boolean z) {
        InstallRequest installRequest;
        if (z) {
            installRequest = (InstallRequest) this.mPendingKillInstallObservers.remove(str);
        } else {
            installRequest = (InstallRequest) this.mNoKillInstallObservers.remove(str);
        }
        if (installRequest != null) {
            notifyInstallObserver(installRequest);
        }
    }

    public void notifyInstallObserver(InstallRequest installRequest) {
        if (installRequest.getObserver() != null) {
            try {
                PmLog.logDebugInfoAndLogcat("result of install: " + installRequest.getReturnCode() + "{" + installRequest.getObserver().hashCode() + "}");
                installRequest.getObserver().onPackageInstalled(installRequest.getName(), installRequest.getReturnCode(), installRequest.getReturnMsg(), extrasForInstallResult(installRequest));
            } catch (RemoteException unused) {
                Slog.i("PackageManager", "Observer no longer exists.");
            }
        }
    }

    public void scheduleDeferredNoKillInstallObserver(InstallRequest installRequest) {
        String packageName = installRequest.getPkg().getPackageName();
        this.mNoKillInstallObservers.put(packageName, installRequest);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(24, packageName), 500L);
    }

    public void scheduleDeferredNoKillPostDelete(InstallArgs installArgs) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(23, installArgs), 3000L);
    }

    public void schedulePruneUnusedStaticSharedLibraries(boolean z) {
        this.mHandler.removeMessages(28);
        this.mHandler.sendEmptyMessageDelayed(28, z ? getPruneUnusedSharedLibrariesDelay() : 0L);
    }

    public void scheduleDeferredPendingKillInstallObserver(InstallRequest installRequest) {
        String packageName = installRequest.getPkg().getPackageName();
        this.mPendingKillInstallObservers.put(packageName, installRequest);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(29, packageName), 1000L);
    }

    public static long getPruneUnusedSharedLibrariesDelay() {
        return SystemProperties.getLong("debug.pm.prune_unused_shared_libraries_delay", PRUNE_UNUSED_SHARED_LIBRARIES_DELAY);
    }

    public void requestFileChecksums(File file, final String str, final int i, final int i2, List list, final IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        Executor backgroundExecutor = this.mInjector.getBackgroundExecutor();
        final Handler backgroundHandler = this.mInjector.getBackgroundHandler();
        final Certificate[] decodeCertificates = list != null ? decodeCertificates(list) : null;
        final ArrayList arrayList = new ArrayList(1);
        arrayList.add(Pair.create(null, file));
        backgroundExecutor.execute(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$requestFileChecksums$3(backgroundHandler, arrayList, i, i2, str, decodeCertificates, iOnChecksumsReadyListener);
            }
        });
    }

    public /* synthetic */ void lambda$requestFileChecksums$3(final Handler handler, List list, int i, int i2, String str, Certificate[] certificateArr, IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        ApkChecksums.Injector.Producer producer = new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda21
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                Context lambda$requestFileChecksums$0;
                lambda$requestFileChecksums$0 = PackageManagerService.this.lambda$requestFileChecksums$0();
                return lambda$requestFileChecksums$0;
            }
        };
        ApkChecksums.Injector.Producer producer2 = new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda22
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                Handler lambda$requestFileChecksums$1;
                lambda$requestFileChecksums$1 = PackageManagerService.lambda$requestFileChecksums$1(handler);
                return lambda$requestFileChecksums$1;
            }
        };
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        Objects.requireNonNull(packageManagerServiceInjector);
        ApkChecksums.getChecksums(list, i, i2, str, certificateArr, iOnChecksumsReadyListener, new ApkChecksums.Injector(producer, producer2, new PackageManagerService$$ExternalSyntheticLambda15(packageManagerServiceInjector), new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda23
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                PackageManagerInternal lambda$requestFileChecksums$2;
                lambda$requestFileChecksums$2 = PackageManagerService.this.lambda$requestFileChecksums$2();
                return lambda$requestFileChecksums$2;
            }
        }));
    }

    public /* synthetic */ Context lambda$requestFileChecksums$0() {
        return this.mContext;
    }

    public /* synthetic */ PackageManagerInternal lambda$requestFileChecksums$2() {
        return (PackageManagerInternal) this.mInjector.getLocalService(PackageManagerInternal.class);
    }

    public void requestChecksumsInternal(Computer computer, String str, boolean z, final int i, final int i2, List list, final IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3, Executor executor, final Handler handler) {
        String[] strArr;
        Objects.requireNonNull(str);
        Objects.requireNonNull(iOnChecksumsReadyListener);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(handler);
        ApplicationInfo applicationInfoInternal = computer.getApplicationInfoInternal(str, 0L, Binder.getCallingUid(), i3);
        if (applicationInfoInternal == null) {
            throw new ParcelableException(new PackageManager.NameNotFoundException(str));
        }
        InstallSourceInfo installSourceInfo = computer.getInstallSourceInfo(str, i3);
        String initiatingPackageName = installSourceInfo.getInitiatingPackageName();
        if (PackageManagerServiceUtils.isInstalledByAdb(initiatingPackageName)) {
            initiatingPackageName = installSourceInfo.getInstallingPackageName();
        }
        final String str2 = initiatingPackageName;
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Pair.create(null, new File(applicationInfoInternal.sourceDir)));
        if (z && (strArr = applicationInfoInternal.splitNames) != null) {
            int length = strArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                arrayList.add(Pair.create(applicationInfoInternal.splitNames[i4], new File(applicationInfoInternal.splitSourceDirs[i4])));
            }
        }
        final Certificate[] decodeCertificates = list != null ? decodeCertificates(list) : null;
        executor.execute(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$requestChecksumsInternal$7(handler, arrayList, i, i2, str2, decodeCertificates, iOnChecksumsReadyListener);
            }
        });
    }

    public /* synthetic */ void lambda$requestChecksumsInternal$7(final Handler handler, List list, int i, int i2, String str, Certificate[] certificateArr, IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        ApkChecksums.Injector.Producer producer = new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda13
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                Context lambda$requestChecksumsInternal$4;
                lambda$requestChecksumsInternal$4 = PackageManagerService.this.lambda$requestChecksumsInternal$4();
                return lambda$requestChecksumsInternal$4;
            }
        };
        ApkChecksums.Injector.Producer producer2 = new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda14
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                Handler lambda$requestChecksumsInternal$5;
                lambda$requestChecksumsInternal$5 = PackageManagerService.lambda$requestChecksumsInternal$5(handler);
                return lambda$requestChecksumsInternal$5;
            }
        };
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        Objects.requireNonNull(packageManagerServiceInjector);
        ApkChecksums.getChecksums(list, i, i2, str, certificateArr, iOnChecksumsReadyListener, new ApkChecksums.Injector(producer, producer2, new PackageManagerService$$ExternalSyntheticLambda15(packageManagerServiceInjector), new ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda16
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final Object produce() {
                PackageManagerInternal lambda$requestChecksumsInternal$6;
                lambda$requestChecksumsInternal$6 = PackageManagerService.this.lambda$requestChecksumsInternal$6();
                return lambda$requestChecksumsInternal$6;
            }
        }));
    }

    public /* synthetic */ Context lambda$requestChecksumsInternal$4() {
        return this.mContext;
    }

    public /* synthetic */ PackageManagerInternal lambda$requestChecksumsInternal$6() {
        return (PackageManagerInternal) this.mInjector.getLocalService(PackageManagerInternal.class);
    }

    public static Certificate[] decodeCertificates(List list) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate[] certificateArr = new Certificate[list.size()];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                certificateArr[i] = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream((byte[]) list.get(i)));
            }
            return certificateArr;
        } catch (CertificateException e) {
            throw ExceptionUtils.propagate(e);
        }
    }

    public static Bundle extrasForInstallResult(InstallRequest installRequest) {
        Bundle bundle;
        int returnCode = installRequest.getReturnCode();
        if (returnCode != -112) {
            if (returnCode != 1) {
                return null;
            }
            bundle = new Bundle();
            bundle.putBoolean("android.intent.extra.REPLACING", (installRequest.getRemovedInfo() == null || installRequest.getRemovedInfo().mRemovedPackage == null) ? false : true);
        } else {
            bundle = new Bundle();
            bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PERMISSION", installRequest.getOrigPermission());
            bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE", installRequest.getOrigPackage());
        }
        return bundle;
    }

    public void scheduleWriteSettings() {
        invalidatePackageInfoCache();
        if (this.mHandler.hasMessages(13)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(13, 10000L);
    }

    public final void scheduleWritePackageListLocked(int i) {
        invalidatePackageInfoCache();
        if (this.mHandler.hasMessages(19)) {
            return;
        }
        Message obtainMessage = this.mHandler.obtainMessage(19);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessageDelayed(obtainMessage, 10000L);
    }

    public void scheduleWritePackageRestrictions(UserHandle userHandle) {
        scheduleWritePackageRestrictions(userHandle == null ? -1 : userHandle.getIdentifier());
    }

    public void scheduleWritePackageRestrictions(int i) {
        invalidatePackageInfoCache();
        if (i == -1) {
            synchronized (this.mDirtyUsers) {
                for (int i2 : this.mUserManager.getUserIds()) {
                    this.mDirtyUsers.add(Integer.valueOf(i2));
                }
            }
        } else {
            if (!this.mUserManager.exists(i)) {
                return;
            }
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.add(Integer.valueOf(i));
            }
        }
        if (this.mBackgroundHandler.hasMessages(14)) {
            return;
        }
        Handler handler = this.mBackgroundHandler;
        handler.sendMessageDelayed(handler.obtainMessage(14, this), 10000L);
    }

    public void writePendingRestrictions() {
        synchronized (this.mLock) {
            this.mBackgroundHandler.removeMessages(14);
            synchronized (this.mDirtyUsers) {
                if (this.mDirtyUsers.isEmpty()) {
                    return;
                }
                Integer[] numArr = (Integer[]) this.mDirtyUsers.toArray(new IntFunction() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda12
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        Integer[] lambda$writePendingRestrictions$8;
                        lambda$writePendingRestrictions$8 = PackageManagerService.lambda$writePendingRestrictions$8(i);
                        return lambda$writePendingRestrictions$8;
                    }
                });
                this.mDirtyUsers.clear();
                this.mSettings.writePackageRestrictions(numArr);
            }
        }
    }

    public static /* synthetic */ Integer[] lambda$writePendingRestrictions$8(int i) {
        return new Integer[i];
    }

    public void writeSettings(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(13);
            this.mBackgroundHandler.removeMessages(14);
            writeSettingsLPrTEMP(z);
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.clear();
            }
        }
    }

    public void writePackageList(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(19);
            this.mSettings.writePackageListLPr(i);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 implements Handler.Callback {
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 14) {
                ((PackageManagerService) message.obj).writePendingRestrictions();
                return true;
            }
            if (i != 30) {
                return false;
            }
            ((Runnable) message.obj).run();
            return true;
        }
    }

    public static PackageManagerService main(final Context context, final Installer installer, final DomainVerificationService domainVerificationService, boolean z) {
        PackageManagerServiceCompilerMapping.checkProperties();
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("PackageManagerTiming", 262144L);
        timingsTraceAndSlog.traceBegin("create package manager");
        final PackageManagerTracedLock packageManagerTracedLock = new PackageManagerTracedLock();
        final Object obj = new Object();
        ServiceThread serviceThread = new ServiceThread("PackageManagerBg", 10, true);
        serviceThread.start();
        final Handler handler = new Handler(serviceThread.getLooper(), BACKGROUND_HANDLER_CALLBACK);
        PackageAbiHelperImpl packageAbiHelperImpl = new PackageAbiHelperImpl();
        List list = SYSTEM_PARTITIONS;
        PackageManagerServiceInjector.Producer producer = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda26
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                ComponentResolver lambda$main$9;
                lambda$main$9 = PackageManagerService.lambda$main$9(packageManagerServiceInjector, packageManagerService);
                return lambda$main$9;
            }
        };
        PackageManagerServiceInjector.Producer producer2 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda37
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PermissionManagerServiceInternal lambda$main$10;
                lambda$main$10 = PackageManagerService.lambda$main$10(context, packageManagerServiceInjector, packageManagerService);
                return lambda$main$10;
            }
        };
        PackageManagerServiceInjector.Producer producer3 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda48
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                UserManagerService lambda$main$11;
                lambda$main$11 = PackageManagerService.lambda$main$11(context, installer, obj, packageManagerTracedLock, packageManagerServiceInjector, packageManagerService);
                return lambda$main$11;
            }
        };
        PackageManagerServiceInjector.Producer producer4 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda52
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                Settings lambda$main$12;
                lambda$main$12 = PackageManagerService.lambda$main$12(DomainVerificationService.this, handler, packageManagerTracedLock, packageManagerServiceInjector, packageManagerService);
                return lambda$main$12;
            }
        };
        PackageManagerServiceInjector.Producer producer5 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda53
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                AppsFilterImpl lambda$main$13;
                lambda$main$13 = PackageManagerService.lambda$main$13(packageManagerServiceInjector, packageManagerService);
                return lambda$main$13;
            }
        };
        PackageManagerServiceInjector.Producer producer6 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda54
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PlatformCompat lambda$main$14;
                lambda$main$14 = PackageManagerService.lambda$main$14(packageManagerServiceInjector, packageManagerService);
                return lambda$main$14;
            }
        };
        PackageManagerServiceInjector.Producer producer7 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda55
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                return PackageManagerService.m9463$r8$lambda$Sl6nXlL7kE1wmpCP574m3H7954(packageManagerServiceInjector, packageManagerService);
            }
        };
        PackageManagerServiceInjector.Producer producer8 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda56
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PackageDexOptimizer lambda$main$16;
                lambda$main$16 = PackageManagerService.lambda$main$16(packageManagerServiceInjector, packageManagerService);
                return lambda$main$16;
            }
        };
        PackageManagerServiceInjector.Producer producer9 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda57
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                DexManager lambda$main$17;
                lambda$main$17 = PackageManagerService.lambda$main$17(packageManagerServiceInjector, packageManagerService);
                return lambda$main$17;
            }
        };
        PackageManagerServiceInjector.Producer producer10 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda58
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                DynamicCodeLogger lambda$main$18;
                lambda$main$18 = PackageManagerService.lambda$main$18(packageManagerServiceInjector, packageManagerService);
                return lambda$main$18;
            }
        };
        PackageManagerServiceInjector.Producer producer11 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda27
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                ArtManagerService lambda$main$19;
                lambda$main$19 = PackageManagerService.lambda$main$19(packageManagerServiceInjector, packageManagerService);
                return lambda$main$19;
            }
        };
        PackageManagerServiceInjector.Producer producer12 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda28
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                return PackageManagerService.$r8$lambda$yTyDl_Ki2ilKmJCBq0xuESmQ62g(packageManagerServiceInjector, packageManagerService);
            }
        };
        PackageManagerServiceInjector.Producer producer13 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda29
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                ViewCompiler lambda$main$21;
                lambda$main$21 = PackageManagerService.lambda$main$21(packageManagerServiceInjector, packageManagerService);
                return lambda$main$21;
            }
        };
        PackageManagerServiceInjector.Producer producer14 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda30
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                IncrementalManager lambda$main$22;
                lambda$main$22 = PackageManagerService.lambda$main$22(packageManagerServiceInjector, packageManagerService);
                return lambda$main$22;
            }
        };
        PackageManagerServiceInjector.Producer producer15 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda31
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                DefaultAppProvider lambda$main$25;
                lambda$main$25 = PackageManagerService.lambda$main$25(context, packageManagerServiceInjector, packageManagerService);
                return lambda$main$25;
            }
        };
        PackageManagerServiceInjector.Producer producer16 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda32
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                DisplayMetrics lambda$main$26;
                lambda$main$26 = PackageManagerService.lambda$main$26(packageManagerServiceInjector, packageManagerService);
                return lambda$main$26;
            }
        };
        PackageManagerServiceInjector.Producer producer17 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda33
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PackageParser2 lambda$main$27;
                lambda$main$27 = PackageManagerService.lambda$main$27(packageManagerServiceInjector, packageManagerService);
                return lambda$main$27;
            }
        };
        PackageManagerServiceInjector.Producer producer18 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda34
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PackageParser2 lambda$main$28;
                lambda$main$28 = PackageManagerService.lambda$main$28(packageManagerServiceInjector, packageManagerService);
                return lambda$main$28;
            }
        };
        PackageManagerServiceInjector.Producer producer19 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda35
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PackageParser2 lambda$main$29;
                lambda$main$29 = PackageManagerService.lambda$main$29(packageManagerServiceInjector, packageManagerService);
                return lambda$main$29;
            }
        };
        PackageManagerServiceInjector.Producer producer20 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda36
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                PackageInstallerService lambda$main$30;
                lambda$main$30 = PackageManagerService.lambda$main$30(packageManagerServiceInjector, packageManagerService);
                return lambda$main$30;
            }
        };
        PackageManagerServiceInjector.ProducerWithArgument producerWithArgument = new PackageManagerServiceInjector.ProducerWithArgument() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda38
            @Override // com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService, Object obj2) {
                InstantAppResolverConnection lambda$main$31;
                lambda$main$31 = PackageManagerService.lambda$main$31(packageManagerServiceInjector, packageManagerService, (ComponentName) obj2);
                return lambda$main$31;
            }
        };
        PackageManagerServiceInjector.Producer producer21 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda39
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                ModuleInfoProvider lambda$main$32;
                lambda$main$32 = PackageManagerService.lambda$main$32(packageManagerServiceInjector, packageManagerService);
                return lambda$main$32;
            }
        };
        PackageManagerServiceInjector.Producer producer22 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda40
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                LegacyPermissionManagerInternal lambda$main$33;
                lambda$main$33 = PackageManagerService.lambda$main$33(packageManagerServiceInjector, packageManagerService);
                return lambda$main$33;
            }
        };
        PackageManagerServiceInjector.Producer producer23 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda41
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                DomainVerificationManagerInternal lambda$main$34;
                lambda$main$34 = PackageManagerService.lambda$main$34(DomainVerificationService.this, packageManagerServiceInjector, packageManagerService);
                return lambda$main$34;
            }
        };
        PackageManagerServiceInjector.Producer producer24 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda42
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
                Handler lambda$main$35;
                lambda$main$35 = PackageManagerService.lambda$main$35(packageManagerServiceInjector, packageManagerService);
                return lambda$main$35;
            }
        };
        DefaultSystemWrapper defaultSystemWrapper = new DefaultSystemWrapper();
        PackageManagerServiceInjector.ServiceProducer serviceProducer = new PackageManagerServiceInjector.ServiceProducer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda43
            @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
            public final Object produce(Class cls) {
                return LocalServices.getService(cls);
            }
        };
        Objects.requireNonNull(context);
        PackageManagerServiceInjector packageManagerServiceInjector = new PackageManagerServiceInjector(context, packageManagerTracedLock, installer, obj, packageAbiHelperImpl, handler, list, producer, producer2, producer3, producer4, producer5, producer6, producer7, producer8, producer9, producer10, producer11, producer12, producer13, producer14, producer15, producer16, producer17, producer18, producer19, producer20, producerWithArgument, producer21, producer22, producer23, producer24, defaultSystemWrapper, serviceProducer, new PackageManagerServiceInjector.ServiceProducer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda44
            @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
            public final Object produce(Class cls) {
                return context.getSystemService(cls);
            }
        }, new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda45
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                BackgroundDexOptService lambda$main$36;
                lambda$main$36 = PackageManagerService.lambda$main$36(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$36;
            }
        }, new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda46
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                IBackupManager lambda$main$37;
                lambda$main$37 = PackageManagerService.lambda$main$37(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$37;
            }
        }, new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda47
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                SharedLibrariesImpl lambda$main$38;
                lambda$main$38 = PackageManagerService.lambda$main$38(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$38;
            }
        }, new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda49
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                CrossProfileIntentFilterHelper lambda$main$39;
                lambda$main$39 = PackageManagerService.lambda$main$39(context, packageManagerServiceInjector2, packageManagerService);
                return lambda$main$39;
            }
        }, new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda50
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                UpdateOwnershipHelper lambda$main$40;
                lambda$main$40 = PackageManagerService.lambda$main$40(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$40;
            }
        });
        final PackageManagerService packageManagerService = new PackageManagerService(packageManagerServiceInjector, z, PackagePartitions.FINGERPRINT, Build.IS_ENG, Build.IS_USERDEBUG, Build.VERSION.SDK_INT, Build.VERSION.INCREMENTAL);
        timingsTraceAndSlog.traceEnd();
        CompatChange.ChangeListener changeListener = new CompatChange.ChangeListener() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda51
            @Override // com.android.server.compat.CompatChange.ChangeListener
            public final void onCompatChange(String str) {
                PackageManagerService.lambda$main$42(PackageManagerService.this, str);
            }
        };
        packageManagerServiceInjector.getCompatibility().registerListener(143539591L, changeListener);
        packageManagerServiceInjector.getCompatibility().registerListener(168782947L, changeListener);
        packageManagerService.installAllowlistedSystemPackages();
        ServiceManager.addService("package", new IPackageManagerImpl());
        ServiceManager.addService("package_native", new PackageManagerNative(packageManagerService));
        return packageManagerService;
    }

    public static /* synthetic */ ComponentResolver lambda$main$9(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ComponentResolver(packageManagerServiceInjector.getUserManagerService(), packageManagerService.mUserNeedsBadging);
    }

    public static /* synthetic */ PermissionManagerServiceInternal lambda$main$10(Context context, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return PermissionManagerService.create(context, packageManagerServiceInjector.getSystemConfig().getAvailableFeatures());
    }

    public static /* synthetic */ UserManagerService lambda$main$11(Context context, Installer installer, Object obj, PackageManagerTracedLock packageManagerTracedLock, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new UserManagerService(context, packageManagerService, new UserDataPreparer(installer, obj, context), packageManagerTracedLock, sPersonaManager);
    }

    public static /* synthetic */ Settings lambda$main$12(DomainVerificationService domainVerificationService, Handler handler, PackageManagerTracedLock packageManagerTracedLock, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new Settings(Environment.getDataDirectory(), RuntimePermissionsPersistence.createInstance(), packageManagerServiceInjector.getPermissionManagerServiceInternal(), domainVerificationService, handler, packageManagerTracedLock);
    }

    public static /* synthetic */ AppsFilterImpl lambda$main$13(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return AppsFilterImpl.create(packageManagerServiceInjector, (PackageManagerInternal) packageManagerServiceInjector.getLocalService(PackageManagerInternal.class));
    }

    public static /* synthetic */ PlatformCompat lambda$main$14(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return (PlatformCompat) ServiceManager.getService("platform_compat");
    }

    public static /* synthetic */ PackageDexOptimizer lambda$main$16(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageDexOptimizer(packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock(), packageManagerServiceInjector.getContext(), "*dexopt*");
    }

    public static /* synthetic */ DexManager lambda$main$17(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new DexManager(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getPackageDexOptimizer(), packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock(), packageManagerServiceInjector.getDynamicCodeLogger());
    }

    public static /* synthetic */ DynamicCodeLogger lambda$main$18(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new DynamicCodeLogger(packageManagerServiceInjector.getInstaller());
    }

    public static /* synthetic */ ArtManagerService lambda$main$19(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ArtManagerService(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock());
    }

    public static /* synthetic */ ViewCompiler lambda$main$21(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ViewCompiler(packageManagerServiceInjector.getInstallLock(), packageManagerServiceInjector.getInstaller());
    }

    public static /* synthetic */ IncrementalManager lambda$main$22(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return (IncrementalManager) packageManagerServiceInjector.getContext().getSystemService("incremental");
    }

    public static /* synthetic */ RoleManager lambda$main$23(Context context) {
        return (RoleManager) context.getSystemService(RoleManager.class);
    }

    public static /* synthetic */ DefaultAppProvider lambda$main$25(final Context context, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new DefaultAppProvider(new Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda67
            @Override // java.util.function.Supplier
            public final Object get() {
                RoleManager lambda$main$23;
                lambda$main$23 = PackageManagerService.lambda$main$23(context);
                return lambda$main$23;
            }
        }, new Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68
            @Override // java.util.function.Supplier
            public final Object get() {
                UserManagerInternal lambda$main$24;
                lambda$main$24 = PackageManagerService.lambda$main$24();
                return lambda$main$24;
            }
        });
    }

    public static /* synthetic */ UserManagerInternal lambda$main$24() {
        return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public static /* synthetic */ DisplayMetrics lambda$main$26(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new DisplayMetrics();
    }

    public static /* synthetic */ PackageParser2 lambda$main$27(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), packageManagerService.mCacheDir, packageManagerService.mPackageParserCallback);
    }

    public static /* synthetic */ PackageParser2 lambda$main$28(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), null, packageManagerService.mPackageParserCallback);
    }

    public static /* synthetic */ PackageParser2 lambda$main$29(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), null, packageManagerService.mPackageParserCallback);
    }

    public static /* synthetic */ PackageInstallerService lambda$main$30(final PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageInstallerService(packageManagerServiceInjector.getContext(), packageManagerService, new Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda69
            @Override // java.util.function.Supplier
            public final Object get() {
                return PackageManagerServiceInjector.this.getScanningPackageParser();
            }
        });
    }

    public static /* synthetic */ InstantAppResolverConnection lambda$main$31(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService, ComponentName componentName) {
        return new InstantAppResolverConnection(packageManagerServiceInjector.getContext(), componentName, "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE");
    }

    public static /* synthetic */ ModuleInfoProvider lambda$main$32(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ModuleInfoProvider(packageManagerServiceInjector.getContext());
    }

    public static /* synthetic */ LegacyPermissionManagerInternal lambda$main$33(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return LegacyPermissionManagerService.create(packageManagerServiceInjector.getContext());
    }

    public static /* synthetic */ Handler lambda$main$35(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        ServiceThread serviceThread = new ServiceThread("PackageManager", 0, true);
        serviceThread.start();
        return new PackageHandler(serviceThread.getLooper(), packageManagerService);
    }

    public static /* synthetic */ BackgroundDexOptService lambda$main$36(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        if (DexOptHelper.useArtService()) {
            return null;
        }
        try {
            return new BackgroundDexOptService(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getDexManager(), packageManagerService);
        } catch (Installer.LegacyDexoptDisabledException e) {
            throw new RuntimeException(e);
        }
    }

    public static /* synthetic */ IBackupManager lambda$main$37(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
    }

    public static /* synthetic */ SharedLibrariesImpl lambda$main$38(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new SharedLibrariesImpl(packageManagerService, packageManagerServiceInjector);
    }

    public static /* synthetic */ CrossProfileIntentFilterHelper lambda$main$39(Context context, PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new CrossProfileIntentFilterHelper(packageManagerServiceInjector.getSettings(), packageManagerServiceInjector.getUserManagerService(), packageManagerServiceInjector.getLock(), packageManagerServiceInjector.getUserManagerInternal(), context);
    }

    public static /* synthetic */ UpdateOwnershipHelper lambda$main$40(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new UpdateOwnershipHelper();
    }

    public static /* synthetic */ void lambda$main$42(PackageManagerService packageManagerService, String str) {
        synchronized (packageManagerService.mInstallLock) {
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                Slog.e("PackageManager", "Failed to find package setting " + str);
                return;
            }
            AndroidPackageInternal pkg = packageStateInternal.getPkg();
            SharedUserApi sharedUser = snapshotComputer.getSharedUser(packageStateInternal.getSharedUserAppId());
            String seInfo = packageStateInternal.getSeInfo();
            if (pkg == null) {
                Slog.e("PackageManager", "Failed to find package " + str);
                return;
            }
            final String seInfo2 = SELinuxMMAC.getSeInfo(packageStateInternal, pkg, sharedUser, packageManagerService.mInjector.getCompatibility());
            if (!seInfo2.equals(seInfo)) {
                Slog.i("PackageManager", "Updating seInfo for package " + str + " from: " + seInfo + " to: " + seInfo2);
                packageManagerService.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda61
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((PackageStateWrite) obj).setOverrideSeInfo(seInfo2);
                    }
                });
                packageManagerService.mAppDataHelper.prepareAppDataAfterInstallLIF(pkg);
            }
        }
    }

    public final void installAllowlistedSystemPackages() {
        if (this.mUserManager.installWhitelistedSystemPackages(isFirstBoot(), isDeviceUpgrading(), this.mExistingPackages)) {
            scheduleWritePackageRestrictions(-1);
            scheduleWriteSettings();
        }
    }

    public final void registerObservers(boolean z) {
        WatchedArrayMap watchedArrayMap = this.mPackages;
        if (watchedArrayMap != null) {
            watchedArrayMap.registerObserver(this.mWatcher);
        }
        SharedLibrariesImpl sharedLibrariesImpl = this.mSharedLibraries;
        if (sharedLibrariesImpl != null) {
            sharedLibrariesImpl.registerObserver(this.mWatcher);
        }
        WatchedArrayMap watchedArrayMap2 = this.mInstrumentation;
        if (watchedArrayMap2 != null) {
            watchedArrayMap2.registerObserver(this.mWatcher);
        }
        WatchedSparseBooleanArray watchedSparseBooleanArray = this.mWebInstantAppsDisabled;
        if (watchedSparseBooleanArray != null) {
            watchedSparseBooleanArray.registerObserver(this.mWatcher);
        }
        AppsFilterImpl appsFilterImpl = this.mAppsFilter;
        if (appsFilterImpl != null) {
            appsFilterImpl.registerObserver(this.mWatcher);
        }
        InstantAppRegistry instantAppRegistry = this.mInstantAppRegistry;
        if (instantAppRegistry != null) {
            instantAppRegistry.registerObserver(this.mWatcher);
        }
        Settings settings = this.mSettings;
        if (settings != null) {
            settings.registerObserver(this.mWatcher);
        }
        WatchedSparseIntArray watchedSparseIntArray = this.mIsolatedOwners;
        if (watchedSparseIntArray != null) {
            watchedSparseIntArray.registerObserver(this.mWatcher);
        }
        ComponentResolver componentResolver = this.mComponentResolver;
        if (componentResolver != null) {
            componentResolver.registerObserver(this.mWatcher);
        }
        WatchedArrayMap watchedArrayMap3 = this.mFrozenPackages;
        if (watchedArrayMap3 != null) {
            watchedArrayMap3.registerObserver(this.mWatcher);
        }
        if (z) {
            Watchable.verifyWatchedAttributes(this, this.mWatcher, (this.mIsEngBuild || this.mIsUserDebugBuild) ? false : true);
        }
    }

    public PackageManagerService(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerServiceTestParams packageManagerServiceTestParams) {
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "PackageManagerService.mPackages");
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mIsolatedOwners = watchedSparseIntArray;
        this.mIsolatedOwnersSnapshot = new SnapshotCache.Auto(watchedSparseIntArray, watchedSparseIntArray, "PackageManagerService.mIsolatedOwners");
        this.mExistingPackages = null;
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mFrozenPackages = watchedArrayMap2;
        this.mFrozenPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "PackageManagerService.mFrozenPackages");
        this.mPackageObserverHelper = new PackageObserverHelper();
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mInstrumentation = watchedArrayMap3;
        this.mInstrumentationSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "PackageManagerService.mInstrumentation");
        this.mTransferredPackages = new ArraySet();
        this.mProtectedBroadcasts = new ArraySet();
        this.mPendingVerification = new SparseArray();
        this.mPendingEnableRollback = new SparseArray();
        this.mNextMoveId = new AtomicInteger();
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new WatchedSparseBooleanArray();
        this.mResolveActivity = new ActivityInfo();
        this.mResolveInfo = new ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new ResolveInfo();
        this.mNoKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mPendingKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mKeepUninstalledPackages = new ArraySet();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new PackageProperty();
        this.mDirtyUsers = new ArraySet();
        this.mRunningInstalls = new SparseArray();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new PackageUsage();
        this.mCompilerStats = new CompilerStats();
        this.mWatcher = new Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            public AnonymousClass1() {
            }

            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                PackageManagerService.onChange(watchable);
            }
        };
        this.mSnapshotLock = new Object();
        this.mInjector = packageManagerServiceInjector;
        packageManagerServiceInjector.bootstrap(this);
        this.mAppsFilter = packageManagerServiceInjector.getAppsFilter();
        this.mComponentResolver = packageManagerServiceInjector.getComponentResolver();
        Context context = packageManagerServiceInjector.getContext();
        this.mContext = context;
        this.mInstaller = packageManagerServiceInjector.getInstaller();
        this.mInstallLock = packageManagerServiceInjector.getInstallLock();
        PackageManagerTracedLock lock = packageManagerServiceInjector.getLock();
        this.mLock = lock;
        this.mPackageStateWriteLock = lock;
        this.mPermissionManager = packageManagerServiceInjector.getPermissionManagerServiceInternal();
        this.mSettings = packageManagerServiceInjector.getSettings();
        UserManagerService userManagerService = packageManagerServiceInjector.getUserManagerService();
        this.mUserManager = userManagerService;
        this.mUserNeedsBadging = new UserNeedsBadgingCache(userManagerService);
        this.mDomainVerificationManager = packageManagerServiceInjector.getDomainVerificationManagerInternal();
        this.mHandler = packageManagerServiceInjector.getHandler();
        this.mBackgroundHandler = packageManagerServiceInjector.getBackgroundHandler();
        SharedLibrariesImpl sharedLibrariesImpl = packageManagerServiceInjector.getSharedLibrariesImpl();
        this.mSharedLibraries = sharedLibrariesImpl;
        this.mApexManager = packageManagerServiceTestParams.apexManager;
        this.mArtManagerService = packageManagerServiceTestParams.artManagerService;
        this.mAvailableFeatures = packageManagerServiceTestParams.availableFeatures;
        this.mBackgroundDexOptService = packageManagerServiceTestParams.backgroundDexOptService;
        this.mDefParseFlags = packageManagerServiceTestParams.defParseFlags;
        this.mDefaultAppProvider = packageManagerServiceTestParams.defaultAppProvider;
        this.mLegacyPermissionManager = packageManagerServiceTestParams.legacyPermissionManagerInternal;
        this.mDexManager = packageManagerServiceTestParams.dexManager;
        this.mDynamicCodeLogger = packageManagerServiceTestParams.dynamicCodeLogger;
        this.mFactoryTest = packageManagerServiceTestParams.factoryTest;
        this.mIncrementalManager = packageManagerServiceTestParams.incrementalManager;
        this.mInstallerService = packageManagerServiceTestParams.installerService;
        this.mInstantAppRegistry = packageManagerServiceTestParams.instantAppRegistry;
        this.mChangedPackagesTracker = packageManagerServiceTestParams.changedPackagesTracker;
        this.mInstantAppResolverConnection = packageManagerServiceTestParams.instantAppResolverConnection;
        this.mInstantAppResolverSettingsComponent = packageManagerServiceTestParams.instantAppResolverSettingsComponent;
        this.mIsPreNMR1Upgrade = packageManagerServiceTestParams.isPreNmr1Upgrade;
        this.mIsPreQUpgrade = packageManagerServiceTestParams.isPreQupgrade;
        this.mIsUpgrade = packageManagerServiceTestParams.isUpgrade;
        this.mMetrics = packageManagerServiceTestParams.Metrics;
        this.mModuleInfoProvider = packageManagerServiceTestParams.moduleInfoProvider;
        this.mMoveCallbacks = packageManagerServiceTestParams.moveCallbacks;
        this.mOverlayConfig = packageManagerServiceTestParams.overlayConfig;
        this.mPackageDexOptimizer = packageManagerServiceTestParams.packageDexOptimizer;
        this.mPackageParserCallback = packageManagerServiceTestParams.packageParserCallback;
        this.mPendingBroadcasts = packageManagerServiceTestParams.pendingPackageBroadcasts;
        this.mTestUtilityService = packageManagerServiceTestParams.testUtilityService;
        this.mProcessLoggingHandler = packageManagerServiceTestParams.processLoggingHandler;
        ProtectedPackages protectedPackages = packageManagerServiceTestParams.protectedPackages;
        this.mProtectedPackages = protectedPackages;
        this.mSeparateProcesses = packageManagerServiceTestParams.separateProcesses;
        this.mViewCompiler = packageManagerServiceTestParams.viewCompiler;
        this.mRequiredVerifierPackages = packageManagerServiceTestParams.requiredVerifierPackages;
        this.mRequiredInstallerPackage = packageManagerServiceTestParams.requiredInstallerPackage;
        this.mRequiredUninstallerPackage = packageManagerServiceTestParams.requiredUninstallerPackage;
        this.mRequiredPermissionControllerPackage = packageManagerServiceTestParams.requiredPermissionControllerPackage;
        this.mSetupWizardPackage = packageManagerServiceTestParams.setupWizardPackage;
        this.mStorageManagerPackage = packageManagerServiceTestParams.storageManagerPackage;
        this.mDefaultTextClassifierPackage = packageManagerServiceTestParams.defaultTextClassifierPackage;
        this.mSystemTextClassifierPackageName = packageManagerServiceTestParams.systemTextClassifierPackage;
        this.mRetailDemoPackage = packageManagerServiceTestParams.retailDemoPackage;
        this.mRecentsPackage = packageManagerServiceTestParams.recentsPackage;
        this.mAmbientContextDetectionPackage = packageManagerServiceTestParams.ambientContextDetectionPackage;
        this.mWearableSensingPackage = packageManagerServiceTestParams.wearableSensingPackage;
        this.mConfiguratorPackage = packageManagerServiceTestParams.configuratorPackage;
        this.mAppPredictionServicePackage = packageManagerServiceTestParams.appPredictionServicePackage;
        this.mIncidentReportApproverPackage = packageManagerServiceTestParams.incidentReportApproverPackage;
        this.mServicesExtensionPackageName = packageManagerServiceTestParams.servicesExtensionPackageName;
        this.mSharedSystemSharedLibraryPackageName = packageManagerServiceTestParams.sharedSystemSharedLibraryPackageName;
        this.mOverlayConfigSignaturePackage = packageManagerServiceTestParams.overlayConfigSignaturePackage;
        this.mResolveComponentName = packageManagerServiceTestParams.resolveComponentName;
        this.mRequiredSdkSandboxPackage = packageManagerServiceTestParams.requiredSdkSandboxPackage;
        this.mInitialNonStoppedSystemPackages = packageManagerServiceTestParams.initialNonStoppedSystemPackages;
        this.mShouldStopSystemPackagesByDefault = packageManagerServiceTestParams.shouldStopSystemPackagesByDefault;
        this.mLiveComputer = createLiveComputer();
        this.mSnapshotStatistics = null;
        watchedArrayMap.putAll(packageManagerServiceTestParams.packages);
        this.mEnableFreeCacheV2 = packageManagerServiceTestParams.enableFreeCacheV2;
        this.mSdkVersion = packageManagerServiceTestParams.sdkVersion;
        this.mAppInstallDir = packageManagerServiceTestParams.appInstallDir;
        this.mIsEngBuild = packageManagerServiceTestParams.isEngBuild;
        this.mIsUserDebugBuild = packageManagerServiceTestParams.isUserDebugBuild;
        this.mIncrementalVersion = packageManagerServiceTestParams.incrementalVersion;
        this.mDomainVerificationConnection = new DomainVerificationConnection(this);
        this.mBroadcastHelper = packageManagerServiceTestParams.broadcastHelper;
        this.mAppDataHelper = packageManagerServiceTestParams.appDataHelper;
        this.mInstallPackageHelper = packageManagerServiceTestParams.installPackageHelper;
        this.mRemovePackageHelper = packageManagerServiceTestParams.removePackageHelper;
        this.mInitAppsHelper = packageManagerServiceTestParams.initAndSystemPackageHelper;
        DeletePackageHelper deletePackageHelper = packageManagerServiceTestParams.deletePackageHelper;
        this.mDeletePackageHelper = deletePackageHelper;
        this.mPreferredActivityHelper = packageManagerServiceTestParams.preferredActivityHelper;
        this.mResolveIntentHelper = packageManagerServiceTestParams.resolveIntentHelper;
        this.mDexOptHelper = packageManagerServiceTestParams.dexOptHelper;
        this.mSuspendPackageHelper = packageManagerServiceTestParams.suspendPackageHelper;
        this.mDistractingPackageHelper = packageManagerServiceTestParams.distractingPackageHelper;
        this.mFrozenPackageInterceptor = null;
        sharedLibrariesImpl.setDeletePackageHelper(deletePackageHelper);
        this.mStorageEventHelper = packageManagerServiceTestParams.storageEventHelper;
        registerObservers(false);
        invalidatePackageInfoCache();
        if (CoreRune.SYSFW_APP_SPEG) {
            this.mSpeg = (SpegService) LocalServices.getService(SpegService.class);
        }
        this.mCustomInjector = new PmCustomInjector();
        this.mPmLifecycle = new PmLifecycleImpl();
        this.mRequiredSystemPackages = new ArraySet();
        this.mEmergencyModePackageHandler = new EmergencyModePackageHandler(context, this, packageManagerServiceInjector.getUserManagerInternal(), protectedPackages);
        this.mAsecInstallHelper = new AsecInstallHelper(this);
    }

    public PackageManagerService(final PackageManagerServiceInjector packageManagerServiceInjector, boolean z, String str, boolean z2, boolean z3, int i, String str2) {
        String str3;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "PackageManagerService.mPackages");
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mIsolatedOwners = watchedSparseIntArray;
        this.mIsolatedOwnersSnapshot = new SnapshotCache.Auto(watchedSparseIntArray, watchedSparseIntArray, "PackageManagerService.mIsolatedOwners");
        this.mExistingPackages = null;
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mFrozenPackages = watchedArrayMap2;
        this.mFrozenPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "PackageManagerService.mFrozenPackages");
        this.mPackageObserverHelper = new PackageObserverHelper();
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mInstrumentation = watchedArrayMap3;
        this.mInstrumentationSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "PackageManagerService.mInstrumentation");
        this.mTransferredPackages = new ArraySet();
        this.mProtectedBroadcasts = new ArraySet();
        this.mPendingVerification = new SparseArray();
        this.mPendingEnableRollback = new SparseArray();
        this.mNextMoveId = new AtomicInteger();
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new WatchedSparseBooleanArray();
        this.mResolveActivity = new ActivityInfo();
        this.mResolveInfo = new ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new ResolveInfo();
        this.mNoKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mPendingKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mKeepUninstalledPackages = new ArraySet();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new PackageProperty();
        this.mDirtyUsers = new ArraySet();
        this.mRunningInstalls = new SparseArray();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new PackageUsage();
        this.mCompilerStats = new CompilerStats();
        this.mWatcher = new Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            public AnonymousClass1() {
            }

            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                PackageManagerService.onChange(watchable);
            }
        };
        this.mSnapshotLock = new Object();
        this.mIsEngBuild = z2;
        this.mIsUserDebugBuild = z3;
        this.mSdkVersion = i;
        this.mIncrementalVersion = str2;
        this.mInjector = packageManagerServiceInjector;
        packageManagerServiceInjector.getSystemWrapper().disablePackageCaches();
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("PackageManagerTiming", 262144L);
        this.mPendingBroadcasts = new PendingPackageBroadcasts();
        packageManagerServiceInjector.bootstrap(this);
        PackageManagerTracedLock lock = packageManagerServiceInjector.getLock();
        this.mLock = lock;
        this.mPackageStateWriteLock = lock;
        Object installLock = packageManagerServiceInjector.getInstallLock();
        this.mInstallLock = installLock;
        LockGuard.installLock(lock, 3);
        Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_start");
        EventLog.writeEvent(3060, SystemClock.uptimeMillis());
        Context context = packageManagerServiceInjector.getContext();
        this.mContext = context;
        this.mFactoryTest = z;
        DisplayMetrics displayMetrics = packageManagerServiceInjector.getDisplayMetrics();
        this.mMetrics = displayMetrics;
        Installer installer = packageManagerServiceInjector.getInstaller();
        this.mInstaller = installer;
        this.mEnableFreeCacheV2 = SystemProperties.getBoolean("fw.free_cache_v2", true);
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
        timingsTraceAndSlog.traceBegin("createSubComponents");
        sPersonaManager = null;
        if (PersonaServiceHelper.isSpfKnoxSupported()) {
            SemPersonaManager.getKnoxInfo();
            sPersonaManager = new PersonaManagerService(context, this, lock);
        }
        LocalServices.addService(PackageManagerInternal.class, new PackageManagerInternalImpl());
        LocalManagerRegistry.addManager(PackageManagerLocal.class, new PackageManagerLocalImpl(this));
        LocalServices.addService(TestUtilityService.class, this);
        PmCustomInjector pmCustomInjector = new PmCustomInjector(packageManagerServiceInjector, this, new PmCustomInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda62
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                MultiUserInstallPolicy lambda$new$46;
                lambda$new$46 = PackageManagerService.lambda$new$46(packageManagerServiceInjector2, packageManagerService);
                return lambda$new$46;
            }
        }, new PmCustomInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda63
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
                UnknownSourceAppManager lambda$new$47;
                lambda$new$47 = PackageManagerService.lambda$new$47(PackageManagerServiceInjector.this, packageManagerServiceInjector2, packageManagerService);
                return lambda$new$47;
            }
        });
        this.mCustomInjector = pmCustomInjector;
        PmLifecycleImpl pmLifecycleImpl = new PmLifecycleImpl(this, packageManagerServiceInjector, pmCustomInjector);
        this.mPmLifecycle = pmLifecycleImpl;
        pmLifecycleImpl.onInstalldStarting();
        this.mTestUtilityService = (TestUtilityService) LocalServices.getService(TestUtilityService.class);
        UserManagerService userManagerService = packageManagerServiceInjector.getUserManagerService();
        this.mUserManager = userManagerService;
        UserNeedsBadgingCache userNeedsBadgingCache = new UserNeedsBadgingCache(userManagerService);
        this.mUserNeedsBadging = userNeedsBadgingCache;
        this.mComponentResolver = packageManagerServiceInjector.getComponentResolver();
        PermissionManagerServiceInternal permissionManagerServiceInternal = packageManagerServiceInjector.getPermissionManagerServiceInternal();
        this.mPermissionManager = permissionManagerServiceInternal;
        Settings settings = packageManagerServiceInjector.getSettings();
        this.mSettings = settings;
        this.mIncrementalManager = packageManagerServiceInjector.getIncrementalManager();
        this.mDefaultAppProvider = packageManagerServiceInjector.getDefaultAppProvider();
        this.mLegacyPermissionManager = packageManagerServiceInjector.getLegacyPermissionManagerInternal();
        this.mPackageParserCallback = new PackageParser2.Callback() { // from class: com.android.server.pm.PackageManagerService.3
            public final /* synthetic */ PlatformCompat val$platformCompat;

            public AnonymousClass3(PlatformCompat platformCompat) {
                r2 = platformCompat;
            }

            @Override // com.android.server.pm.parsing.PackageParser2.Callback
            public boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) {
                return r2.isChangeEnabled(j, applicationInfo);
            }

            @Override // com.android.server.pm.pkg.parsing.ParsingPackageUtils.Callback
            public boolean hasFeature(String str4) {
                return PackageManagerService.this.hasSystemFeature(str4, 0);
            }
        };
        pmLifecycleImpl.onInitPreparing();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("addSharedUsers");
        settings.addSharedUserLPw("android.uid.system", 1000, 1, 8);
        settings.addSharedUserLPw("android.uid.phone", 1001, 1, 8);
        settings.addSharedUserLPw("android.uid.log", 1007, 1, 8);
        settings.addSharedUserLPw("android.uid.nfc", 1027, 1, 8);
        settings.addSharedUserLPw("android.uid.bluetooth", 1002, 1, 8);
        settings.addSharedUserLPw("android.uid.shell", 2000, 1, 8);
        settings.addSharedUserLPw("android.uid.se", 1068, 1, 8);
        settings.addSharedUserLPw("android.uid.networkstack", 1073, 1, 8);
        settings.addSharedUserLPw("android.uid.uwb", 1083, 1, 8);
        settings.addSharedUserLPw("android.uid.spass", 5278, 1, 8);
        settings.addSharedUserLPw("android.uid.mdxkit", 5025, 1, 8);
        settings.addSharedUserLPw("android.uid.sharelive", 5026, 1, 8);
        settings.addSharedUserLPw("android.uid.samsungcloud", 5009, 1, 8);
        settings.addSharedUserLPw("android.uid.bcmgr", 5006, 1, 8);
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 31) {
            settings.addSharedUserLPw("android.uid.sendhelpmessage", 5003, 1, 8);
        }
        settings.addSharedUserLPw("android.uid.cmhservice", 5004, 1, 8);
        settings.addSharedUserLPw("android.uid.spay", 5279, 1, 8);
        settings.addSharedUserLPw("android.uid.adaptive_brightness", 5021, 1, 8);
        settings.addSharedUserLPw("android.uid.advmodem", 5017, 1, 8);
        settings.addSharedUserLPw("android.uid.ipsgeofence", 5022, 1, 8);
        settings.addSharedUserLPw("android.uid.networkdiagnostic", 5023, 1, 8);
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 31) {
            settings.addSharedUserLPw("android.uid.intelligenceservice", 5010, 1, 8);
        }
        timingsTraceAndSlog.traceEnd();
        settings.addSharedUserLPw("android.uid.nsflp", 5013, 1, 8);
        if (PersonaServiceHelper.isSpfKnoxSupported()) {
            settings.addSharedUserLPw("android.uid.knoxcore", 5250, 1, 8);
        }
        String str4 = SystemProperties.get("debug.separate_processes");
        if (str4 != null && str4.length() > 0) {
            if ("*".equals(str4)) {
                this.mDefParseFlags = 2;
                this.mSeparateProcesses = null;
                Slog.w("PackageManager", "Running with debug.separate_processes: * (ALL)");
            } else {
                this.mDefParseFlags = 0;
                this.mSeparateProcesses = str4.split(",");
                Slog.w("PackageManager", "Running with debug.separate_processes: " + str4);
            }
        } else {
            this.mDefParseFlags = 0;
            this.mSeparateProcesses = null;
        }
        EnterprisePartitionManager.setInstaller(installer, installLock);
        this.mPackageDexOptimizer = packageManagerServiceInjector.getPackageDexOptimizer();
        this.mDexManager = packageManagerServiceInjector.getDexManager();
        this.mDynamicCodeLogger = packageManagerServiceInjector.getDynamicCodeLogger();
        this.mBackgroundDexOptService = packageManagerServiceInjector.getBackgroundDexOptService();
        this.mArtManagerService = packageManagerServiceInjector.getArtManagerService();
        this.mMoveCallbacks = new MovePackageHelper.MoveCallbacks(FgThread.get().getLooper());
        this.mViewCompiler = packageManagerServiceInjector.getViewCompiler();
        SharedLibrariesImpl sharedLibrariesImpl = packageManagerServiceInjector.getSharedLibrariesImpl();
        this.mSharedLibraries = sharedLibrariesImpl;
        this.mBackgroundHandler = packageManagerServiceInjector.getBackgroundHandler();
        ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getMetrics(displayMetrics);
        timingsTraceAndSlog.traceBegin("get system config");
        SystemConfig systemConfig = packageManagerServiceInjector.getSystemConfig();
        this.mAvailableFeatures = systemConfig.getAvailableFeatures();
        timingsTraceAndSlog.traceEnd();
        ProtectedPackages protectedPackages = new ProtectedPackages(context);
        this.mProtectedPackages = protectedPackages;
        this.mApexManager = packageManagerServiceInjector.getApexManager();
        this.mAppsFilter = packageManagerServiceInjector.getAppsFilter();
        this.mInstantAppRegistry = new InstantAppRegistry(context, permissionManagerServiceInternal, packageManagerServiceInjector.getUserManagerInternal(), new DeletePackageHelper(this));
        this.mChangedPackagesTracker = new ChangedPackagesTracker();
        this.mAppInstallDir = new File(Environment.getDataDirectory(), "app");
        DomainVerificationConnection domainVerificationConnection = new DomainVerificationConnection(this);
        this.mDomainVerificationConnection = domainVerificationConnection;
        DomainVerificationManagerInternal domainVerificationManagerInternal = packageManagerServiceInjector.getDomainVerificationManagerInternal();
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        domainVerificationManagerInternal.setConnection(domainVerificationConnection);
        BroadcastHelper broadcastHelper = new BroadcastHelper(packageManagerServiceInjector);
        this.mBroadcastHelper = broadcastHelper;
        AppDataHelper appDataHelper = new AppDataHelper(this);
        this.mAppDataHelper = appDataHelper;
        this.mInstallPackageHelper = new InstallPackageHelper(this, appDataHelper);
        RemovePackageHelper removePackageHelper = new RemovePackageHelper(this, appDataHelper);
        this.mRemovePackageHelper = removePackageHelper;
        DeletePackageHelper deletePackageHelper = new DeletePackageHelper(this, removePackageHelper, appDataHelper);
        this.mDeletePackageHelper = deletePackageHelper;
        sharedLibrariesImpl.setDeletePackageHelper(deletePackageHelper);
        PreferredActivityHelper preferredActivityHelper = new PreferredActivityHelper(this);
        this.mPreferredActivityHelper = preferredActivityHelper;
        this.mResolveIntentHelper = new ResolveIntentHelper(context, preferredActivityHelper, packageManagerServiceInjector.getCompatibility(), userManagerService, domainVerificationManagerInternal, userNeedsBadgingCache, new Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda64
            @Override // java.util.function.Supplier
            public final Object get() {
                ResolveInfo lambda$new$48;
                lambda$new$48 = PackageManagerService.this.lambda$new$48();
                return lambda$new$48;
            }
        }, new Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda65
            @Override // java.util.function.Supplier
            public final Object get() {
                ActivityInfo lambda$new$49;
                lambda$new$49 = PackageManagerService.this.lambda$new$49();
                return lambda$new$49;
            }
        }, packageManagerServiceInjector.getBackgroundHandler());
        this.mDexOptHelper = new DexOptHelper(this);
        SuspendPackageHelper suspendPackageHelper = new SuspendPackageHelper(this, packageManagerServiceInjector, userManagerService, broadcastHelper, protectedPackages);
        this.mSuspendPackageHelper = suspendPackageHelper;
        this.mDistractingPackageHelper = new DistractingPackageHelper(this, packageManagerServiceInjector, broadcastHelper, suspendPackageHelper);
        AsecInstallHelper asecInstallHelper = new AsecInstallHelper(this);
        this.mAsecInstallHelper = asecInstallHelper;
        this.mStorageEventHelper = new StorageEventHelper(this, deletePackageHelper, removePackageHelper, asecInstallHelper);
        synchronized (lock) {
            this.mSnapshotStatistics = new SnapshotStatistics();
            sSnapshotPendingVersion.incrementAndGet();
            this.mLiveComputer = createLiveComputer();
            registerObservers(true);
        }
        pmLifecycleImpl.onInitStarting();
        ComputerLocked computerLocked = this.mLiveComputer;
        synchronized (this.mInstallLock) {
            synchronized (this.mLock) {
                Handler handler = packageManagerServiceInjector.getHandler();
                this.mHandler = handler;
                this.mProcessLoggingHandler = new ProcessLoggingHandler();
                Watchdog.getInstance().addThread(handler, 600000L);
                this.mEmergencyModePackageHandler = new EmergencyModePackageHandler(this.mContext, this, this.mInjector.getUserManagerInternal(), protectedPackages);
                this.mAutoDisableHandler = new AutoDisableHandler(this.mContext, this);
                ArrayMap sharedLibraries = systemConfig.getSharedLibraries();
                int size = sharedLibraries.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mSharedLibraries.addBuiltInSharedLibraryLPw((SystemConfig.SharedLibraryEntry) sharedLibraries.valueAt(i2));
                }
                for (int i3 = 0; i3 < size; i3++) {
                    String str5 = (String) sharedLibraries.keyAt(i3);
                    SystemConfig.SharedLibraryEntry sharedLibraryEntry = (SystemConfig.SharedLibraryEntry) sharedLibraries.valueAt(i3);
                    int length = sharedLibraryEntry.dependencies.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        SharedLibraryInfo sharedLibraryInfo = computerLocked.getSharedLibraryInfo(sharedLibraryEntry.dependencies[i4], -1L);
                        if (sharedLibraryInfo != null) {
                            computerLocked.getSharedLibraryInfo(str5, -1L).addDependency(sharedLibraryInfo);
                        }
                    }
                }
                SELinuxMMAC.readInstallPolicy();
                timingsTraceAndSlog.traceBegin("loadFallbacks");
                FallbackCategoryProvider.loadFallbacks();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("read user settings");
                this.mFirstBoot = !this.mSettings.readLPw(computerLocked, this.mInjector.getUserManagerInternal().getUsers(true, false, false), this.mCustomInjector.getPackageManagerBackupController());
                timingsTraceAndSlog.traceEnd();
                if (this.mFirstBoot) {
                    timingsTraceAndSlog.traceBegin("setFirstBoot: ");
                    try {
                        this.mInstaller.setFirstBoot();
                    } catch (Installer.InstallerException e) {
                        Slog.w("PackageManager", "Could not set First Boot: ", e);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                this.mPermissionManager.readLegacyPermissionsTEMP(this.mSettings.mPermissions);
                this.mPermissionManager.readLegacyPermissionStateTEMP();
                if (this.mFirstBoot) {
                    DexOptHelper.requestCopyPreoptedFiles();
                }
                String string = Resources.getSystem().getString(R.string.ext_media_move_success_message);
                if (!TextUtils.isEmpty(string)) {
                    this.mCustomResolverComponentName = ComponentName.unflattenFromString(string);
                }
                synchronized (this.mAvailableFeatures) {
                    NfcFeatureManager.updateFeatureAndPackage(this.mAvailableFeatures, this.mCustomInjector.getSkippingApks(), this.mFactoryTest);
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_system_scan_start");
                EventLog.writeEvent(3070, uptimeMillis);
                String str6 = System.getenv("BOOTCLASSPATH");
                String str7 = System.getenv("SYSTEMSERVERCLASSPATH");
                if (str6 == null) {
                    Slog.w("PackageManager", "No BOOTCLASSPATH found!");
                }
                if (str7 == null) {
                    Slog.w("PackageManager", "No SYSTEMSERVERCLASSPATH found!");
                }
                Settings.VersionInfo internalVersion = this.mSettings.getInternalVersion();
                boolean z4 = !str.equals(internalVersion.fingerprint);
                this.mIsUpgrade = z4;
                if (z4) {
                    PackageManagerServiceUtils.logCriticalInfo(4, "Upgrading from " + internalVersion.fingerprint + " (" + internalVersion.buildFingerprint + ") to " + PackagePartitions.FINGERPRINT + " (" + Build.FINGERPRINT + ")");
                }
                if (z4 || this.mFirstBoot) {
                    PackageManagerServiceUtils.logCriticalInfo(5, "[PM]Build Info: " + SystemProperties.get("ro.omc.build.id") + PackageManagerShellCommandDataLoader.STDIN_PATH + Build.FINGERPRINT);
                    SystemProperties.set("sys.boot.is_upgrade", "1");
                }
                this.mInitAppsHelper = new InitAppsHelper(this, this.mApexManager, this.mInstallPackageHelper, this.mInjector.getSystemPartitions());
                this.mPromoteSystemApps = z4 && internalVersion.sdkVersion <= 22;
                this.mIsPreNMR1Upgrade = z4 && internalVersion.sdkVersion < 25;
                this.mIsPreQUpgrade = z4 && internalVersion.sdkVersion < 29;
                WatchedArrayMap packagesLocked = this.mSettings.getPackagesLocked();
                if (isDeviceUpgrading()) {
                    this.mExistingPackages = new ArraySet(packagesLocked.size());
                    Iterator it = packagesLocked.values().iterator();
                    while (it.hasNext()) {
                        this.mExistingPackages.add(((PackageSetting) it.next()).getPackageName());
                    }
                    timingsTraceAndSlog.traceBegin("cross profile intent filter update");
                    this.mInjector.getCrossProfileIntentFilterHelper().updateDefaultCrossProfileIntentFilter();
                    timingsTraceAndSlog.traceEnd();
                }
                this.mCacheDir = PackageManagerServiceUtils.preparePackageParserCache(this.mIsEngBuild, this.mIsUserDebugBuild, this.mIncrementalVersion);
                this.mInitialNonStoppedSystemPackages = this.mInjector.getSystemConfig().getInitialNonStoppedSystemPackages();
                this.mShouldStopSystemPackagesByDefault = this.mContext.getResources().getBoolean(17891848);
                this.mPmLifecycle.onSystemScanning(this.mCacheDir);
                final int[] userIds = this.mUserManager.getUserIds();
                PackageParser2 scanningCachingPackageParser = this.mInjector.getScanningCachingPackageParser();
                this.mOverlayConfig = this.mInitAppsHelper.initSystemApps(scanningCachingPackageParser, packagesLocked, userIds, uptimeMillis);
                this.mPmLifecycle.onDataScanning();
                this.mInitAppsHelper.initNonSystemApps(scanningCachingPackageParser, userIds, uptimeMillis);
                scanningCachingPackageParser.close();
                this.mRequiredVerifierPackages = getRequiredButNotReallyRequiredVerifiersLPr(computerLocked);
                this.mRequiredInstallerPackage = getRequiredInstallerLPr(computerLocked);
                this.mRequiredUninstallerPackage = getRequiredUninstallerLPr(computerLocked);
                this.mRequiredPermissionControllerPackage = getRequiredPermissionControllerLPr(computerLocked);
                this.mStorageManagerPackage = getStorageManagerPackageName(computerLocked);
                String setupWizardPackageNameImpl = getSetupWizardPackageNameImpl(computerLocked);
                this.mSetupWizardPackage = setupWizardPackageNameImpl;
                this.mComponentResolver.fixProtectedFilterPriorities(setupWizardPackageNameImpl);
                this.mDefaultTextClassifierPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.hardware));
                this.mSystemTextClassifierPackageName = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.face_acquired_too_much_motion));
                this.mConfiguratorPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.face_error_lockout));
                this.mAppPredictionServicePackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.ext_media_status_bad_removal));
                this.mIncidentReportApproverPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.fingerprint_error_no_fingerprints));
                this.mRetailDemoPackage = getRetailDemoPackageName();
                this.mOverlayConfigSignaturePackage = ensureSystemPackageName(computerLocked, this.mInjector.getSystemConfig().getOverlayConfigSignaturePackage());
                this.mRecentsPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.global_action_toggle_silent_mode));
                this.mAmbientContextDetectionPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.ext_media_seamless_action));
                this.mWearableSensingPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.face_error_canceled));
                this.mSharedLibraries.updateAllSharedLibrariesLPw(null, null, Collections.unmodifiableMap(this.mPackages));
                for (SharedUserSetting sharedUserSetting : this.mSettings.getAllSharedUsersLPw()) {
                    List applyAdjustedAbiToSharedUser = ScanPackageUtils.applyAdjustedAbiToSharedUser(sharedUserSetting, null, this.mInjector.getAbiHelper().getAdjustedAbiForSharedUser(sharedUserSetting.getPackageStates(), null));
                    if (!DexOptHelper.useArtService() && applyAdjustedAbiToSharedUser != null && applyAdjustedAbiToSharedUser.size() > 0) {
                        for (int size2 = applyAdjustedAbiToSharedUser.size() - 1; size2 >= 0; size2--) {
                            try {
                                this.mInstaller.rmdex((String) applyAdjustedAbiToSharedUser.get(size2), InstructionSets.getDexCodeInstructionSet(InstructionSets.getPreferredInstructionSet()));
                            } catch (Installer.InstallerException unused) {
                            } catch (Installer.LegacyDexoptDisabledException e2) {
                                throw new RuntimeException(e2);
                            }
                        }
                    }
                    sharedUserSetting.fixSeInfoLocked();
                    sharedUserSetting.updateProcesses();
                }
                this.mPackageUsage.read(packagesLocked);
                this.mCompilerStats.read();
                Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_end");
                EventLog.writeEvent(3090, SystemClock.uptimeMillis());
                Slog.i("PackageManager", "Time to scan packages: " + (((float) (SystemClock.uptimeMillis() - uptimeMillis)) / 1000.0f) + " seconds");
                if (this.mIsUpgrade) {
                    Slog.i("PackageManager", "Partitions fingerprint changed from " + internalVersion.fingerprint + " to " + PackagePartitions.FINGERPRINT + "; regranting permissions for internal storage");
                }
                this.mPermissionManager.onStorageVolumeMounted(StorageManager.UUID_PRIVATE_INTERNAL, this.mIsUpgrade);
                internalVersion.sdkVersion = this.mSdkVersion;
                if (this.mPromoteSystemApps || this.mFirstBoot) {
                    Iterator it2 = this.mInjector.getUserManagerInternal().getUsers(true).iterator();
                    while (it2.hasNext()) {
                        this.mSettings.applyDefaultPreferredAppsLPw(((UserInfo) it2.next()).id);
                    }
                }
                if (this.mIsUpgrade) {
                    ArrayList arrayList = new ArrayList();
                    Slog.i("PackageManager", "Build fingerprint changed; clearing code caches");
                    for (int i5 = 0; i5 < packagesLocked.size(); i5++) {
                        PackageSetting packageSetting = (PackageSetting) packagesLocked.valueAt(i5);
                        if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, packageSetting.getVolumeUuid())) {
                            this.mAppDataHelper.clearAppDataLIF(packageSetting.getPkg(), -1, 131111);
                        }
                        if (this.mInstallPackageHelper.needToRemoveNonInstalledOverlayLPr(packageSetting)) {
                            arrayList.add(packageSetting);
                        }
                    }
                    internalVersion.buildFingerprint = Build.FINGERPRINT;
                    internalVersion.fingerprint = PackagePartitions.FINGERPRINT;
                    if (!arrayList.isEmpty()) {
                        this.mInstallPackageHelper.clearNonInstalledOverlaysLIF(arrayList);
                    }
                }
                this.mPrepareAppDataFuture = this.mAppDataHelper.fixAppsDataOnBoot();
                if (this.mIsPreQUpgrade) {
                    Slog.i("PackageManager", "Allowlisting all existing apps to hide their icons");
                    int size3 = packagesLocked.size();
                    for (int i6 = 0; i6 < size3; i6++) {
                        PackageSetting packageSetting2 = (PackageSetting) packagesLocked.valueAt(i6);
                        if ((packageSetting2.getFlags() & 1) == 0) {
                            packageSetting2.disableComponentLPw(PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, 0);
                        }
                    }
                }
                this.mPromoteSystemApps = false;
                internalVersion.databaseVersion = 3;
                timingsTraceAndSlog.traceBegin("write settings");
                writeSettingsLPrTEMP();
                timingsTraceAndSlog.traceEnd();
                Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_ready");
                EventLog.writeEvent(3100, SystemClock.uptimeMillis());
                ComponentName intentFilterVerifierComponentNameLPr = getIntentFilterVerifierComponentNameLPr(computerLocked);
                ComponentName domainVerificationAgentComponentNameLPr = getDomainVerificationAgentComponentNameLPr(computerLocked);
                Context context2 = this.mContext;
                DomainVerificationManagerInternal domainVerificationManagerInternal2 = this.mDomainVerificationManager;
                this.mDomainVerificationManager.setProxy(DomainVerificationProxy.makeProxy(intentFilterVerifierComponentNameLPr, domainVerificationAgentComponentNameLPr, context2, domainVerificationManagerInternal2, domainVerificationManagerInternal2.getCollector(), this.mDomainVerificationConnection));
                this.mServicesExtensionPackageName = getRequiredServicesExtensionPackageLPr(computerLocked);
                this.mSharedSystemSharedLibraryPackageName = getRequiredSharedLibrary(computerLocked, "android.ext.shared", -1);
                this.mSettings.setPermissionControllerVersion(computerLocked.getPackageInfo(this.mRequiredPermissionControllerPackage, 0L, 0).getLongVersionCode());
                this.mRequiredSdkSandboxPackage = getRequiredSdkSandboxPackageName(computerLocked);
                this.mRequiredSystemPackages = buildRequiredSystemPackages();
                forEachPackageState(computerLocked, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda66
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PackageManagerService.this.lambda$new$50(userIds, (PackageStateInternal) obj);
                    }
                });
                this.mInstallerService = this.mInjector.getPackageInstallerService();
                ComponentName instantAppResolver = getInstantAppResolver(computerLocked);
                if (instantAppResolver != null) {
                    if (DEBUG_INSTANT) {
                        Slog.d("PackageManager", "Set ephemeral resolver: " + instantAppResolver);
                    }
                    this.mInstantAppResolverConnection = this.mInjector.getInstantAppResolverConnection(instantAppResolver);
                    this.mInstantAppResolverSettingsComponent = getInstantAppResolverSettingsLPr(computerLocked, instantAppResolver);
                    str3 = null;
                } else {
                    str3 = null;
                    this.mInstantAppResolverConnection = null;
                    this.mInstantAppResolverSettingsComponent = null;
                }
                updateInstantAppInstallerLocked(str3);
                HashMap hashMap = new HashMap();
                for (int i7 : userIds) {
                    hashMap.put(Integer.valueOf(i7), computerLocked.getInstalledPackages(0L, i7).getList());
                }
                this.mDexManager.load(hashMap);
                this.mDynamicCodeLogger.load(hashMap);
                if (this.mIsUpgrade) {
                    FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 13, SystemClock.uptimeMillis() - uptimeMillis);
                }
                if (this.mFirstBoot || isDeviceUpgrading()) {
                    for (Map.Entry entry : systemConfig.getAppMetadataFilePaths().entrySet()) {
                        String str8 = (String) entry.getKey();
                        String str9 = (String) entry.getValue();
                        str9 = new File(str9).exists() ? str9 : str3;
                        PackageSetting disabledSystemPkgLPr = this.mSettings.getDisabledSystemPkgLPr(str8);
                        if (disabledSystemPkgLPr == null) {
                            PackageSetting packageLPr = this.mSettings.getPackageLPr(str8);
                            if (packageLPr != null) {
                                packageLPr.setAppMetadataFilePath(str9);
                            } else {
                                Slog.w("PackageManager", "Cannot set app metadata file for nonexistent package " + str8);
                            }
                        } else {
                            disabledSystemPkgLPr.setAppMetadataFilePath(str9);
                        }
                    }
                }
                this.mLiveComputer = createLiveComputer();
            }
        }
        this.mModuleInfoProvider = this.mInjector.getModuleInfoProvider();
        this.mInjector.getSystemWrapper().enablePackageCaches();
        timingsTraceAndSlog.traceBegin("GC");
        VMRuntime.getRuntime().requestConcurrentGC();
        timingsTraceAndSlog.traceEnd();
        this.mInstaller.setWarnIfHeld(this.mLock);
        ParsingPackageUtils.readConfigUseRoundIcon(this.mContext.getResources());
        this.mServiceStartWithDelay = SystemClock.uptimeMillis() + 60000;
        this.mFrozenPackageInterceptor = new FrozenPackageInterceptor(this.mContext);
        Slog.i("PackageManager", "Fix for b/169414761 is applied");
        this.mPmLifecycle.onInitCompleted();
    }

    public static /* synthetic */ MultiUserInstallPolicy lambda$new$46(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        Settings settings = packageManagerServiceInjector.getSettings();
        Objects.requireNonNull(settings);
        return new MultiUserInstallPolicy(new MultiUserInstallPolicy.PackageSettingsDelegator(new PackageManagerService$$ExternalSyntheticLambda71(settings), new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda73
            public final void accept(Object obj, Object obj2, Object obj3) {
                PackageManagerService.lambda$new$43((PackageSetting) obj, (Boolean) obj2, (Integer) obj3);
            }
        }, new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda74
            public final void accept(Object obj, Object obj2, Object obj3) {
                PackageManagerService.lambda$new$44((PackageSetting) obj, (Integer) obj2, (Integer) obj3);
            }
        }, new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda75
            public final void accept(Object obj, Object obj2, Object obj3) {
                PackageManagerService.lambda$new$45((PackageSetting) obj, (String) obj2, (Integer) obj3);
            }
        }), new MetaDataHelper(), UserManagerService.getInstance());
    }

    public static /* synthetic */ void lambda$new$43(PackageSetting packageSetting, Boolean bool, Integer num) {
        packageSetting.setInstalled(bool.booleanValue(), num.intValue());
    }

    public static /* synthetic */ void lambda$new$44(PackageSetting packageSetting, Integer num, Integer num2) {
        packageSetting.setEnabled(num.intValue(), num2.intValue(), null);
    }

    public static /* synthetic */ void lambda$new$45(PackageSetting packageSetting, String str, Integer num) {
        packageSetting.addDisabledComponent(str, num.intValue());
    }

    public static /* synthetic */ UnknownSourceAppManager lambda$new$47(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService) {
        Handler handler = packageManagerServiceInjector.getHandler();
        Settings settings = packageManagerServiceInjector2.getSettings();
        Objects.requireNonNull(settings);
        return new UnknownSourceAppManager(handler, new UnknownSourceAppManager.PackageSettingsDelegator(new PackageManagerService$$ExternalSyntheticLambda71(settings)));
    }

    /* renamed from: com.android.server.pm.PackageManagerService$3 */
    /* loaded from: classes3.dex */
    public class AnonymousClass3 extends PackageParser2.Callback {
        public final /* synthetic */ PlatformCompat val$platformCompat;

        public AnonymousClass3(PlatformCompat platformCompat) {
            r2 = platformCompat;
        }

        @Override // com.android.server.pm.parsing.PackageParser2.Callback
        public boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) {
            return r2.isChangeEnabled(j, applicationInfo);
        }

        @Override // com.android.server.pm.pkg.parsing.ParsingPackageUtils.Callback
        public boolean hasFeature(String str4) {
            return PackageManagerService.this.hasSystemFeature(str4, 0);
        }
    }

    public /* synthetic */ ResolveInfo lambda$new$48() {
        return this.mResolveInfo;
    }

    public /* synthetic */ ActivityInfo lambda$new$49() {
        return this.mInstantAppInstallerActivity;
    }

    public /* synthetic */ void lambda$new$50(int[] iArr, PackageStateInternal packageStateInternal) {
        if (packageStateInternal.getAndroidPackage() == null || packageStateInternal.isSystem()) {
            return;
        }
        for (int i : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i).isInstantApp() && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                this.mInstantAppRegistry.addInstantApp(i, packageStateInternal.getAppId());
            }
        }
    }

    public void updateInstantAppInstallerLocked(String str) {
        ActivityInfo activityInfo = this.mInstantAppInstallerActivity;
        if (activityInfo == null || activityInfo.getComponentName().getPackageName().equals(str)) {
            setUpInstantAppInstallerActivityLP(getInstantAppInstallerLPr());
        }
    }

    public boolean isFirstBoot() {
        return this.mFirstBoot;
    }

    public boolean isDeviceUpgrading() {
        return this.mIsUpgrade || SystemProperties.getBoolean("persist.pm.mock-upgrade", false);
    }

    public final String[] getRequiredButNotReallyRequiredVerifiersLPr(Computer computer) {
        List queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION"), "application/vnd.android.package-archive", 1835008L, 0, Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        if (size == 0) {
            Log.w("PackageManager", "There should probably be a verifier, but, none were found");
            return EmptyArray.STRING;
        }
        if (size <= 2) {
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                String str = ((ResolveInfo) queryIntentReceiversInternal.get(i)).getComponentInfo().packageName;
                strArr[i] = str;
                if (TextUtils.isEmpty(str)) {
                    throw new RuntimeException("Invalid verifier: " + queryIntentReceiversInternal);
                }
            }
            return strArr;
        }
        throw new RuntimeException("There must be no more than 2 verifiers; found " + queryIntentReceiversInternal);
    }

    public final String getRequiredSharedLibrary(Computer computer, String str, int i) {
        SharedLibraryInfo sharedLibraryInfo = computer.getSharedLibraryInfo(str, i);
        if (sharedLibraryInfo == null) {
            throw new IllegalStateException("Missing required shared library:" + str);
        }
        String packageName = sharedLibraryInfo.getPackageName();
        if (packageName != null) {
            return packageName;
        }
        throw new IllegalStateException("Expected a package for shared library " + str);
    }

    public final String getRequiredServicesExtensionPackageLPr(Computer computer) {
        String ensureSystemPackageName = ensureSystemPackageName(computer, this.mContext.getString(R.string.hardware));
        if (TextUtils.isEmpty(ensureSystemPackageName)) {
            throw new RuntimeException("Required services extension package is missing, check config_servicesExtensionPackage.");
        }
        return ensureSystemPackageName;
    }

    public final String getRequiredInstallerLPr(Computer computer) {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.parse("content://com.example/foo.apk"), "application/vnd.android.package-archive");
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, "application/vnd.android.package-archive", 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (!((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.applicationInfo.isPrivilegedApp()) {
                throw new RuntimeException("The installer must be a privileged app");
            }
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        if (queryIntentActivitiesInternal.size() == 0) {
            throwSystemRequiredPackageState(computer, intent, "application/vnd.android.package-archive");
        }
        throw new RuntimeException("There must be exactly one installer; found " + queryIntentActivitiesInternal);
    }

    public final String getRequiredUninstallerLPr(Computer computer) {
        Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.fromParts("package", "foo.bar", null));
        ResolveInfo resolveIntentInternal = this.mResolveIntentHelper.resolveIntentInternal(computer, intent, null, 1835008L, 0L, 0, false, Binder.getCallingUid());
        if (resolveIntentInternal == null || this.mResolveActivity.name.equals(resolveIntentInternal.getComponentInfo().name)) {
            throwSystemRequiredPackageState(computer, intent, null);
            throw new RuntimeException("There must be exactly one uninstaller; found " + resolveIntentInternal);
        }
        return resolveIntentInternal.getComponentInfo().packageName;
    }

    public final String getRequiredPermissionControllerLPr(Computer computer) {
        Intent intent = new Intent("android.intent.action.MANAGE_PERMISSIONS");
        intent.addCategory("android.intent.category.DEFAULT");
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, null, 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (!((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.applicationInfo.isPrivilegedApp()) {
                throw new RuntimeException("The permissions manager must be a privileged app");
            }
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        if (queryIntentActivitiesInternal.size() == 0) {
            throwSystemRequiredPackageState(computer, intent, null);
        }
        throw new RuntimeException("There must be exactly one permissions manager; found " + queryIntentActivitiesInternal);
    }

    public final void throwSystemRequiredPackageState(Computer computer, Intent intent, String str) {
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, str, 1966592L, 0);
        if (queryIntentActivitiesInternal.size() != 0) {
            PackageSetting packageLPr = this.mSettings.getPackageLPr(((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName);
            if (packageLPr != null) {
                PmLog.throwPackageState(packageLPr, this.mSettings.getPackagesLocked());
            }
        }
    }

    public final ComponentName getIntentFilterVerifierComponentNameLPr(Computer computer) {
        List queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new Intent("android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION"), "application/vnd.android.package-archive", 1835008L, 0, Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        ResolveInfo resolveInfo = null;
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentReceiversInternal.get(i);
            if (checkPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", resolveInfo2.getComponentInfo().packageName, 0) == 0 && (resolveInfo == null || resolveInfo2.priority > resolveInfo.priority)) {
                resolveInfo = resolveInfo2;
            }
        }
        if (resolveInfo != null) {
            return resolveInfo.getComponentInfo().getComponentName();
        }
        Slog.w("PackageManager", "Intent filter verifier not found");
        return null;
    }

    public final ComponentName getDomainVerificationAgentComponentNameLPr(Computer computer) {
        List queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new Intent("android.intent.action.DOMAINS_NEED_VERIFICATION"), null, 1835008L, 0, Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        ResolveInfo resolveInfo = null;
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentReceiversInternal.get(i);
            String str = resolveInfo2.getComponentInfo().packageName;
            if (checkPermission("android.permission.DOMAIN_VERIFICATION_AGENT", str, 0) != 0) {
                Slog.w("PackageManager", "Domain verification agent found but does not hold permission: " + str);
            } else if (resolveInfo == null || resolveInfo2.priority > resolveInfo.priority) {
                if (computer.isComponentEffectivelyEnabled(resolveInfo2.getComponentInfo(), UserHandle.SYSTEM)) {
                    resolveInfo = resolveInfo2;
                } else {
                    Slog.w("PackageManager", "Domain verification agent found but not enabled");
                }
            }
        }
        if (resolveInfo != null) {
            return resolveInfo.getComponentInfo().getComponentName();
        }
        Slog.w("PackageManager", "Domain verification agent not found");
        return null;
    }

    public ComponentName getInstantAppResolver(Computer computer) {
        String[] stringArray = this.mContext.getResources().getStringArray(17236207);
        if (stringArray.length == 0 && !Build.IS_DEBUGGABLE) {
            if (DEBUG_INSTANT) {
                Slog.d("PackageManager", "Ephemeral resolver NOT found; empty package list");
            }
            return null;
        }
        List queryIntentServicesInternal = computer.queryIntentServicesInternal(new Intent("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE"), null, (!Build.IS_DEBUGGABLE ? 1048576 : 0) | 786432, 0, Binder.getCallingUid(), false);
        int size = queryIntentServicesInternal.size();
        if (size == 0) {
            if (DEBUG_INSTANT) {
                Slog.d("PackageManager", "Ephemeral resolver NOT found; no matching intent filters");
            }
            return null;
        }
        ArraySet arraySet = new ArraySet(Arrays.asList(stringArray));
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesInternal.get(i);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null) {
                String str = serviceInfo.packageName;
                if (!arraySet.contains(str) && !Build.IS_DEBUGGABLE) {
                    if (DEBUG_INSTANT) {
                        Slog.d("PackageManager", "Ephemeral resolver not in allowed package list; pkg: " + str + ", info:" + resolveInfo);
                    }
                } else {
                    if (DEBUG_INSTANT) {
                        Slog.v("PackageManager", "Ephemeral resolver found; pkg: " + str + ", info:" + resolveInfo);
                    }
                    return new ComponentName(str, resolveInfo.serviceInfo.name);
                }
            }
        }
        if (DEBUG_INSTANT) {
            Slog.v("PackageManager", "Ephemeral resolver NOT found");
        }
        return null;
    }

    public final ActivityInfo getInstantAppInstallerLPr() {
        String[] strArr;
        boolean z = this.mIsEngBuild;
        if (z) {
            strArr = new String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE_TEST", "android.intent.action.INSTALL_INSTANT_APP_PACKAGE"};
        } else {
            strArr = new String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE"};
        }
        int i = (z ? 0 : 1048576) | (-2146697216);
        Computer snapshotComputer = snapshotComputer();
        Intent intent = new Intent();
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File("foo.apk")), "application/vnd.android.package-archive");
        List list = null;
        for (String str : strArr) {
            intent.setAction(str);
            list = snapshotComputer.queryIntentActivitiesInternal(intent, "application/vnd.android.package-archive", i, 0);
            if (!list.isEmpty()) {
                break;
            }
            if (DEBUG_INSTANT) {
                Slog.d("PackageManager", "Instant App installer not found with " + str);
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (checkPermission("android.permission.INSTALL_PACKAGES", ((ResolveInfo) it.next()).activityInfo.packageName, 0) != 0 && !this.mIsEngBuild) {
                it.remove();
            }
        }
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return (ActivityInfo) ((ResolveInfo) list.get(0)).getComponentInfo();
        }
        throw new RuntimeException("There must be at most one ephemeral installer; found " + list);
    }

    public final ComponentName getInstantAppResolverSettingsLPr(Computer computer, ComponentName componentName) {
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(new Intent("android.intent.action.INSTANT_APP_RESOLVER_SETTINGS").addCategory("android.intent.category.DEFAULT").setPackage(componentName.getPackageName()), null, 786432L, 0);
        if (queryIntentActivitiesInternal.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().getComponentName();
    }

    public PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        return ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).getPermissionGroupInfo(str, i);
    }

    public void freeAllAppCacheAboveQuota(String str) {
        synchronized (this.mInstallLock) {
            try {
                this.mInstaller.freeCache(str, Long.MAX_VALUE, 2304);
            } catch (Installer.InstallerException unused) {
            }
        }
    }

    public void freeStorage(String str, long j, int i) {
        File findPathForUuid = ((StorageManager) this.mInjector.getSystemService(StorageManager.class)).findPathForUuid(str);
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        if (this.mEnableFreeCacheV2) {
            boolean equals = Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str);
            boolean z = (i & 1) != 0;
            if (equals && (z || SystemProperties.getBoolean("persist.sys.preloads.file_cache_expired", false))) {
                deletePreloadsFileCache();
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            if (equals && z) {
                FileUtils.deleteContents(this.mCacheDir);
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            synchronized (this.mInstallLock) {
                try {
                    this.mInstaller.freeCache(str, j, 256);
                } catch (Installer.InstallerException unused) {
                }
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            Computer snapshotComputer = snapshotComputer();
            if (equals && this.mSharedLibraries.pruneUnusedStaticSharedLibraries(snapshotComputer, j, Settings.Global.getLong(this.mContext.getContentResolver(), "unused_static_shared_lib_min_cache_period", FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD))) {
                return;
            }
            if (equals && this.mInstantAppRegistry.pruneInstalledInstantApps(snapshotComputer, j, Settings.Global.getLong(this.mContext.getContentResolver(), "installed_instant_app_min_cache_period", 604800000L))) {
                return;
            }
            synchronized (this.mInstallLock) {
                try {
                    this.mInstaller.freeCache(str, j, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE);
                } catch (Installer.InstallerException unused2) {
                }
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            if (equals && this.mInstantAppRegistry.pruneUninstalledInstantApps(snapshotComputer, j, Settings.Global.getLong(this.mContext.getContentResolver(), "uninstalled_instant_app_min_cache_period", 604800000L))) {
                return;
            }
            StorageManagerInternal storageManagerInternal = (StorageManagerInternal) this.mInjector.getLocalService(StorageManagerInternal.class);
            long usableSpace = j - findPathForUuid.getUsableSpace();
            if (usableSpace > 0) {
                storageManagerInternal.freeCache(str, usableSpace);
            }
            this.mInstallerService.freeStageDirs(str);
        } else {
            synchronized (this.mInstallLock) {
                try {
                    this.mInstaller.freeCache(str, j, 0);
                } catch (Installer.InstallerException unused3) {
                }
            }
        }
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        throw new IOException("Failed to free " + j + " on storage device at " + findPathForUuid);
    }

    public int freeCacheForInstallation(int i, PackageLite packageLite, String str, String str2, int i2) {
        int i3;
        long storageLowBytes = StorageManager.from(this.mContext).getStorageLowBytes(Environment.getDataDirectory());
        long calculateInstalledSize = PackageManagerServiceUtils.calculateInstalledSize(str, str2);
        if (calculateInstalledSize >= 0) {
            synchronized (this.mInstallLock) {
                try {
                    try {
                        this.mInstaller.freeCache(null, calculateInstalledSize + storageLowBytes, 0);
                        PackageInfoLite minimalPackageInfo = PackageManagerServiceUtils.getMinimalPackageInfo(this.mContext, packageLite, str, i2, str2);
                        if (minimalPackageInfo.recommendedInstallLocation == -6) {
                            minimalPackageInfo.recommendedInstallLocation = -1;
                        }
                        i3 = minimalPackageInfo.recommendedInstallLocation;
                    } catch (Installer.InstallerException e) {
                        Slog.w("PackageManager", "Failed to free cache", e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i3;
        }
        return i;
    }

    public ModuleInfo getModuleInfo(String str, int i) {
        return this.mModuleInfoProvider.getModuleInfo(str, i);
    }

    public void updateSequenceNumberLP(PackageSetting packageSetting, int[] iArr) {
        this.mChangedPackagesTracker.updateSequenceNumber(packageSetting.getPackageName(), iArr);
    }

    public boolean hasSystemFeature(String str, int i) {
        if (MaintenanceModeUtils.isMaintenanceModeFeature(str) && MaintenanceModeManager.isFeatureDisallowedByPolicy(this.mContext)) {
            return false;
        }
        synchronized (this.mAvailableFeatures) {
            FeatureInfo featureInfo = (FeatureInfo) this.mAvailableFeatures.get(str);
            if (featureInfo == null) {
                return false;
            }
            return featureInfo.version >= i;
        }
    }

    public int checkPermission(String str, String str2, int i) {
        return this.mPermissionManager.checkPermission(str2, str, i);
    }

    public String getSdkSandboxPackageName() {
        return this.mRequiredSdkSandboxPackage;
    }

    public String getPackageInstallerPackageName() {
        return this.mRequiredInstallerPackage;
    }

    public void requestInstantAppResolutionPhaseTwo(AuxiliaryResolveInfo auxiliaryResolveInfo, Intent intent, String str, String str2, String str3, boolean z, Bundle bundle, int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(20, new InstantAppRequest(auxiliaryResolveInfo, intent, str, str2, str3, z, i, bundle, false, auxiliaryResolveInfo.hostDigestPrefixSecure, auxiliaryResolveInfo.token)));
    }

    public ParceledListSlice queryIntentReceivers(Computer computer, Intent intent, String str, long j, int i) {
        return new ParceledListSlice(this.mResolveIntentHelper.queryIntentReceiversInternal(computer, intent, str, j, i, Binder.getCallingUid()));
    }

    public static void reportSettingsProblem(int i, String str) {
        PackageManagerServiceUtils.logCriticalInfo(i, str);
    }

    public static void renameStaticSharedLibraryPackage(ParsedPackage parsedPackage) {
        parsedPackage.setPackageName(toStaticSharedLibraryPackageName(parsedPackage.getPackageName(), parsedPackage.getStaticSharedLibraryVersion()));
    }

    public static String toStaticSharedLibraryPackageName(String str, long j) {
        return str + "_" + j;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004d A[Catch: RemoteException -> 0x0057, TryCatch #0 {RemoteException -> 0x0057, blocks: (B:3:0x0005, B:6:0x000d, B:8:0x0022, B:10:0x002f, B:12:0x004d, B:17:0x0051), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void performFstrimIfNeeded() {
        /*
            r8 = this;
            java.lang.String r0 = "Only the system can request fstrim"
            com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot(r0)
            android.os.storage.IStorageManager r0 = com.android.internal.content.InstallLocationUtils.getStorageManager()     // Catch: android.os.RemoteException -> L57
            java.lang.String r1 = "PackageManager"
            if (r0 == 0) goto L51
            android.content.Context r8 = r8.mContext     // Catch: android.os.RemoteException -> L57
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch: android.os.RemoteException -> L57
            java.lang.String r2 = "fstrim_mandatory_interval"
            r3 = 259200000(0xf731400, double:1.280618154E-315)
            long r2 = android.provider.Settings.Global.getLong(r8, r2, r3)     // Catch: android.os.RemoteException -> L57
            r4 = 0
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 <= 0) goto L4a
            long r4 = java.lang.System.currentTimeMillis()     // Catch: android.os.RemoteException -> L57
            long r6 = r0.lastMaintenance()     // Catch: android.os.RemoteException -> L57
            long r4 = r4 - r6
            int r8 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r8 <= 0) goto L4a
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L57
            r8.<init>()     // Catch: android.os.RemoteException -> L57
            java.lang.String r2 = "No disk maintenance in "
            r8.append(r2)     // Catch: android.os.RemoteException -> L57
            r8.append(r4)     // Catch: android.os.RemoteException -> L57
            java.lang.String r2 = "; running immediately"
            r8.append(r2)     // Catch: android.os.RemoteException -> L57
            java.lang.String r8 = r8.toString()     // Catch: android.os.RemoteException -> L57
            android.util.Slog.w(r1, r8)     // Catch: android.os.RemoteException -> L57
            r8 = 1
            goto L4b
        L4a:
            r8 = 0
        L4b:
            if (r8 == 0) goto L57
            r0.runMaintenance()     // Catch: android.os.RemoteException -> L57
            goto L57
        L51:
            java.lang.String r8 = "storageManager service unavailable!"
            android.util.Slog.e(r1, r8)     // Catch: android.os.RemoteException -> L57
        L57:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.performFstrimIfNeeded():void");
    }

    public void updatePackagesIfNeeded() {
        this.mDexOptHelper.performPackageDexOptUpgradeIfNeeded();
    }

    public final void notifyPackageUseInternal(String str, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mLock) {
            PackageSetting packageLPr = this.mSettings.getPackageLPr(str);
            if (packageLPr == null) {
                return;
            }
            packageLPr.getPkgState().setLastPackageUsageTimeInMills(i, currentTimeMillis);
        }
    }

    public DexManager getDexManager() {
        return this.mDexManager;
    }

    public DexOptHelper getDexOptHelper() {
        return this.mDexOptHelper;
    }

    public DynamicCodeLogger getDynamicCodeLogger() {
        return this.mDynamicCodeLogger;
    }

    public AsecInstallHelper getAsecInstallHelper() {
        return this.mAsecInstallHelper;
    }

    public void shutdown() {
        this.mCompilerStats.writeNow();
        this.mDexManager.writePackageDexUsageNow();
        this.mDynamicCodeLogger.writeNow();
        PackageWatchdog.getInstance(this.mContext).writeNow();
        synchronized (this.mLock) {
            this.mPackageUsage.writeNow(this.mSettings.getPackagesLocked());
            if (this.mHandler.hasMessages(13) || this.mBackgroundHandler.hasMessages(14) || this.mHandler.hasMessages(19)) {
                writeSettings(true);
            }
        }
        ((ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class)).shutdown();
    }

    public int[] resolveUserIds(int i) {
        return i == -1 ? this.mUserManager.getUserIds() : new int[]{i};
    }

    public final void setUpInstantAppInstallerActivityLP(ActivityInfo activityInfo) {
        if (activityInfo == null) {
            if (DEBUG_INSTANT) {
                Slog.d("PackageManager", "Clear ephemeral installer activity");
            }
            this.mInstantAppInstallerActivity = null;
            onChanged();
            return;
        }
        if (DEBUG_INSTANT) {
            Slog.d("PackageManager", "Set ephemeral installer activity: " + activityInfo.getComponentName());
        }
        this.mInstantAppInstallerActivity = activityInfo;
        activityInfo.flags |= 288;
        activityInfo.exported = true;
        activityInfo.enabled = true;
        ResolveInfo resolveInfo = this.mInstantAppInstallerInfo;
        resolveInfo.activityInfo = activityInfo;
        resolveInfo.priority = 1;
        resolveInfo.preferredOrder = 1;
        resolveInfo.isDefault = true;
        resolveInfo.match = 5799936;
        onChanged();
    }

    public void killApplication(String str, int i, String str2, int i2) {
        killApplication(str, i, -1, str2, i2);
    }

    public void killApplication(String str, int i, int i2, String str2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                try {
                    service.killApplication(str, i, i2, str2, i3);
                } catch (RemoteException unused) {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public /* synthetic */ void lambda$sendPackageBroadcast$51(String str, String str2, Bundle bundle, int i, String str3, IIntentReceiver iIntentReceiver, int[] iArr, int[] iArr2, SparseArray sparseArray, Bundle bundle2) {
        this.mBroadcastHelper.sendPackageBroadcast(str, str2, bundle, i, str3, iIntentReceiver, iArr, iArr2, sparseArray, null, bundle2);
    }

    @Override // com.android.server.pm.PackageSender
    public void sendPackageBroadcast(final String str, final String str2, final Bundle bundle, final int i, final String str3, final IIntentReceiver iIntentReceiver, final int[] iArr, final int[] iArr2, final SparseArray sparseArray, final Bundle bundle2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$sendPackageBroadcast$51(str, str2, bundle, i, str3, iIntentReceiver, iArr, iArr2, sparseArray, bundle2);
            }
        });
    }

    public void notifyPackageAdded(String str, int i) {
        this.mPackageObserverHelper.notifyAdded(str, i);
    }

    public void notifyPackageChanged(String str, int i) {
        this.mPackageObserverHelper.notifyChanged(str, i);
    }

    @Override // com.android.server.pm.PackageSender
    public void notifyPackageRemoved(String str, int i) {
        this.mPackageObserverHelper.notifyRemoved(str, i);
        UserPackage.removeFromCache(UserHandle.getUserId(i), str);
    }

    public void sendPackageAddedForUser(Computer computer, String str, PackageStateInternal packageStateInternal, int i, int i2) {
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        boolean isSystem = packageStateInternal.isSystem();
        boolean isInstantApp = userStateOrDefault.isInstantApp();
        sendPackageAddedForNewUsers(computer, str, isSystem, false, packageStateInternal.getAppId(), isInstantApp ? EMPTY_INT_ARRAY : new int[]{i}, isInstantApp ? new int[]{i} : EMPTY_INT_ARRAY, i2);
        PackageInstaller.SessionInfo sessionInfo = new PackageInstaller.SessionInfo();
        sessionInfo.installReason = userStateOrDefault.getInstallReason();
        sessionInfo.appPackageName = str;
        sendSessionCommitBroadcast(sessionInfo, i);
    }

    public void sendPackageAddedForNewUsers(Computer computer, final String str, boolean z, final boolean z2, final int i, final int[] iArr, final int[] iArr2, final int i2) {
        if (ArrayUtils.isEmpty(iArr) && ArrayUtils.isEmpty(iArr2)) {
            return;
        }
        final SparseArray visibilityAllowList = this.mAppsFilter.getVisibilityAllowList(computer, computer.getPackageStateInternal(str, 1000), iArr, computer.getPackageStates());
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$sendPackageAddedForNewUsers$52(str, i, iArr, iArr2, i2, visibilityAllowList);
            }
        });
        if (!z || ArrayUtils.isEmpty(iArr)) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$sendPackageAddedForNewUsers$53(iArr, str, z2);
            }
        });
    }

    public /* synthetic */ void lambda$sendPackageAddedForNewUsers$52(String str, int i, int[] iArr, int[] iArr2, int i2, SparseArray sparseArray) {
        this.mBroadcastHelper.sendPackageAddedForNewUsers(str, i, iArr, iArr2, i2, sparseArray);
    }

    public /* synthetic */ void lambda$sendPackageAddedForNewUsers$53(int[] iArr, String str, boolean z) {
        for (int i : iArr) {
            this.mBroadcastHelper.sendBootCompletedBroadcastToSystemApp(str, z, i);
        }
    }

    public final void sendApplicationHiddenForUser(String str, PackageStateInternal packageStateInternal, int i) {
        PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo(this);
        packageRemovedInfo.mRemovedPackage = str;
        packageRemovedInfo.mInstallerPackageName = packageStateInternal.getInstallSource().mInstallerPackageName;
        packageRemovedInfo.mRemovedUsers = new int[]{i};
        packageRemovedInfo.mBroadcastUsers = new int[]{i};
        packageRemovedInfo.mUid = UserHandle.getUid(i, packageStateInternal.getAppId());
        packageRemovedInfo.mRemovedPackageVersionCode = packageStateInternal.getVersionCode();
        packageRemovedInfo.mHidden = true;
        packageRemovedInfo.sendPackageRemovedBroadcasts(true, false);
    }

    public boolean isUserRestricted(int i, String str) {
        if (!this.mUserManager.getUserRestrictions(i).getBoolean(str, false)) {
            return false;
        }
        Log.w("PackageManager", "User is restricted: " + str);
        return true;
    }

    public final void enforceCanSetPackagesSuspendedAsUser(Computer computer, String str, int i, int i2, String str2) {
        if (i == 0 || UserHandle.getAppId(i) == 1000) {
            return;
        }
        String deviceOwnerOrProfileOwnerPackage = this.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i2);
        if (deviceOwnerOrProfileOwnerPackage == null || computer.getPackageUid(deviceOwnerOrProfileOwnerPackage, 0L, i2) != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", str2);
            int packageUid = computer.getPackageUid(str, 0L, i2);
            boolean z = packageUid == i;
            if ((i == 2000 && UserHandle.isSameApp(packageUid, i)) || z) {
                return;
            }
            throw new SecurityException("Calling package " + str + " in user " + i2 + " does not belong to calling uid " + i);
        }
    }

    public void unsuspendForSuspendingPackage(Computer computer, final String str, int i) {
        String[] strArr = (String[]) computer.getPackageStates().keySet().toArray(new String[0]);
        SuspendPackageHelper suspendPackageHelper = this.mSuspendPackageHelper;
        Objects.requireNonNull(str);
        suspendPackageHelper.removeSuspensionsBySuspendingPackage(computer, strArr, new Predicate() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals((String) obj);
            }
        }, i);
    }

    public void removeAllDistractingPackageRestrictions(Computer computer, int i) {
        this.mDistractingPackageHelper.removeDistractingPackageRestrictions(computer, computer.getAllAvailablePackageNames(), i);
    }

    public final void enforceCanSetDistractingPackageRestrictionsAsUser(int i, int i2, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", str);
        if (PackageManagerServiceUtils.isSystemOrRoot(i) || UserHandle.getUserId(i) == i2) {
            return;
        }
        throw new SecurityException("Calling uid " + i + " cannot call for user " + i2);
    }

    public void setEnableRollbackCode(int i, int i2) {
        Message obtainMessage = this.mHandler.obtainMessage(21);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void notifyFirstLaunch(final String str, final String str2, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$notifyFirstLaunch$54(str, i, str2);
            }
        });
    }

    public /* synthetic */ void lambda$notifyFirstLaunch$54(String str, int i, String str2) {
        for (int i2 = 0; i2 < this.mRunningInstalls.size(); i2++) {
            InstallRequest installRequest = (InstallRequest) this.mRunningInstalls.valueAt(i2);
            if (installRequest.getReturnCode() == 1 && str.equals(installRequest.getPkg().getPackageName())) {
                for (int i3 = 0; i3 < installRequest.getNewUsers().length; i3++) {
                    if (i == installRequest.getNewUsers()[i3]) {
                        return;
                    }
                }
            }
        }
        boolean isInstantAppInternal = snapshotComputer().isInstantAppInternal(str, i, 1000);
        this.mBroadcastHelper.sendFirstLaunchBroadcast(str, str2, isInstantAppInternal ? EMPTY_INT_ARRAY : new int[]{i}, isInstantAppInternal ? new int[]{i} : EMPTY_INT_ARRAY);
    }

    public Settings.VersionInfo getSettingsVersionForPackage(AndroidPackage androidPackage) {
        if (androidPackage.isExternalStorage()) {
            if (TextUtils.isEmpty(androidPackage.getVolumeUuid())) {
                return this.mSettings.getExternalVersion();
            }
            return this.mSettings.findOrCreateVersion(androidPackage.getVolumeUuid());
        }
        return this.mSettings.getInternalVersion();
    }

    public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        this.mDeletePackageHelper.deleteExistingPackageAsUser(versionedPackage, iPackageDeleteObserver2, i);
    }

    public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) {
        this.mDeletePackageHelper.deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, i2, false);
    }

    public boolean isCallerVerifier(Computer computer, int i) {
        int userId = UserHandle.getUserId(i);
        for (String str : this.mRequiredVerifierPackages) {
            if (i == computer.getPackageUid(str, 0L, userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPackageDeviceAdminOnAnyUser(Computer computer, String str) {
        int callingUid = Binder.getCallingUid();
        if (computer.checkUidPermission("android.permission.MANAGE_USERS", callingUid) != 0) {
            EventLog.writeEvent(1397638484, "128599183", -1, "");
            throw new SecurityException("android.permission.MANAGE_USERS permission is required to call this API");
        }
        if (computer.getInstantAppPackageName(callingUid) == null || computer.isCallerSameApp(str, callingUid)) {
            return isPackageDeviceAdmin(str, -1);
        }
        return false;
    }

    public boolean isPackageDeviceAdmin(String str, int i) {
        int[] iArr;
        IDevicePolicyManager devicePolicyManager = getDevicePolicyManager();
        if (devicePolicyManager != null) {
            try {
                ComponentName deviceOwnerComponent = devicePolicyManager.getDeviceOwnerComponent(false);
                if (str.equals(deviceOwnerComponent == null ? null : deviceOwnerComponent.getPackageName())) {
                    return true;
                }
                if (i == -1) {
                    iArr = this.mUserManager.getUserIds();
                } else {
                    iArr = new int[]{i};
                }
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    if (devicePolicyManager.packageHasActiveAdmins(str, iArr[i2]) || isDeviceManagementRoleHolder(str, iArr[i2])) {
                        return true;
                    }
                }
            } catch (RemoteException unused) {
            }
        }
        return false;
    }

    public final boolean isDeviceManagementRoleHolder(String str, int i) {
        return Objects.equals(str, getDevicePolicyManagementRoleHolderPackageName(i));
    }

    public String getDevicePolicyManagementRoleHolderPackageName(final int i) {
        return (String) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda7
            public final Object getOrThrow() {
                String lambda$getDevicePolicyManagementRoleHolderPackageName$55;
                lambda$getDevicePolicyManagementRoleHolderPackageName$55 = PackageManagerService.this.lambda$getDevicePolicyManagementRoleHolderPackageName$55(i);
                return lambda$getDevicePolicyManagementRoleHolderPackageName$55;
            }
        });
    }

    public /* synthetic */ String lambda$getDevicePolicyManagementRoleHolderPackageName$55(int i) {
        List roleHoldersAsUser = ((RoleManager) this.mContext.getSystemService(RoleManager.class)).getRoleHoldersAsUser("android.app.role.DEVICE_POLICY_MANAGEMENT", UserHandle.of(i));
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (String) roleHoldersAsUser.get(0);
    }

    public final IDevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        return this.mDevicePolicyManager;
    }

    public boolean clearPackageAfterSpeg(String str, int i) {
        SpegService spegService;
        if (!CoreRune.SYSFW_APP_SPEG || (spegService = this.mSpeg) == null || !spegService.checkSpegState(str)) {
            Slog.e("SPEG", "Clear package " + str + " is not allowed");
            return false;
        }
        return clearApplicationUserDataLIF(snapshotComputer(), str, 0);
    }

    public final boolean clearApplicationUserDataLIF(Computer computer, String str, int i) {
        SpegService spegService;
        int i2 = 0;
        if (str == null) {
            Slog.w("PackageManager", "Attempt to delete null packageName.");
            return false;
        }
        AndroidPackage androidPackage = computer.getPackage(str);
        if (androidPackage == null) {
            Slog.w("PackageManager", "Package named '" + str + "' doesn't exist.");
            return false;
        }
        if (CoreRune.SYSFW_APP_SPEG && (spegService = this.mSpeg) != null && spegService.checkSpegState(str)) {
            Slog.d("SPEG", "Skip permissions reset");
        } else {
            this.mPermissionManager.resetRuntimePermissions(androidPackage, i);
        }
        this.mAppDataHelper.clearAppDataLIF(androidPackage, i, 7);
        this.mAppDataHelper.clearKeystoreData(i, UserHandle.getAppId(androidPackage.getUid()));
        UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        StorageManagerInternal storageManagerInternal = (StorageManagerInternal) this.mInjector.getLocalService(StorageManagerInternal.class);
        if (StorageManager.isUserKeyUnlocked(i) && storageManagerInternal.isCeStoragePrepared(i)) {
            i2 = 3;
        } else if (userManagerInternal.isUserRunning(i)) {
            i2 = 1;
        }
        this.mAppDataHelper.prepareAppDataContentsLIF(androidPackage, computer.getPackageStateInternal(str), i, i2);
        return true;
    }

    public final void resetComponentEnabledSettingsIfNeededLPw(String str, final int i) {
        final PackageSetting packageLPr;
        AndroidPackage androidPackage = str != null ? (AndroidPackage) this.mPackages.get(str) : null;
        if (androidPackage == null || !androidPackage.isResetEnabledSettingsOnAppDataCleared() || (packageLPr = this.mSettings.getPackageLPr(str)) == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        Consumer consumer = new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda59
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageManagerService.lambda$resetComponentEnabledSettingsIfNeededLPw$56(PackageSetting.this, i, arrayList, (ParsedMainComponent) obj);
            }
        };
        for (int i2 = 0; i2 < androidPackage.getActivities().size(); i2++) {
            consumer.accept(androidPackage.getActivities().get(i2));
        }
        for (int i3 = 0; i3 < androidPackage.getReceivers().size(); i3++) {
            consumer.accept(androidPackage.getReceivers().get(i3));
        }
        for (int i4 = 0; i4 < androidPackage.getServices().size(); i4++) {
            consumer.accept(androidPackage.getServices().get(i4));
        }
        for (int i5 = 0; i5 < androidPackage.getProviders().size(); i5++) {
            consumer.accept(androidPackage.getProviders().get(i5));
        }
        if (ArrayUtils.isEmpty(arrayList)) {
            return;
        }
        updateSequenceNumberLP(packageLPr, new int[]{i});
        updateInstantAppInstallerLocked(str);
        scheduleWritePackageRestrictions(i);
        this.mPendingBroadcasts.addComponents(i, str, arrayList);
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    public static /* synthetic */ void lambda$resetComponentEnabledSettingsIfNeededLPw$56(PackageSetting packageSetting, int i, ArrayList arrayList, ParsedMainComponent parsedMainComponent) {
        if (packageSetting.restoreComponentLPw(parsedMainComponent.getClassName(), i)) {
            arrayList.add(parsedMainComponent.getClassName());
        }
    }

    public /* synthetic */ void lambda$postPreferredActivityChangedBroadcast$57(int i) {
        this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
    }

    public void postPreferredActivityChangedBroadcast(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$postPreferredActivityChangedBroadcast$57(i);
            }
        });
    }

    public void clearPackagePreferredActivitiesLPw(String str, SparseBooleanArray sparseBooleanArray, int i) {
        this.mSettings.clearPackagePreferredActivities(str, sparseBooleanArray, i);
    }

    public void restorePermissionsAndUpdateRolesForNewUserInstall(String str, int i) {
        this.mPermissionManager.restoreDelayedRuntimePermissions(str, i);
        this.mPreferredActivityHelper.updateDefaultHomeNotLocked(snapshotComputer(), i);
    }

    public void addCrossProfileIntentFilter(Computer computer, WatchedIntentFilter watchedIntentFilter, String str, int i, int i2, int i3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        int callingUid = Binder.getCallingUid();
        enforceOwnerRights(computer, str, callingUid);
        this.mUserManager.enforceCrossProfileIntentFilterAccess(i, i2, callingUid, true);
        PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
        if (!watchedIntentFilter.checkDataPathAndSchemeSpecificParts()) {
            EventLog.writeEvent(1397638484, "246749936", Integer.valueOf(callingUid));
            throw new IllegalArgumentException("Invalid intent data paths or scheme specific parts in the filter.");
        }
        if (watchedIntentFilter.countActions() == 0) {
            Slog.w("PackageManager", "Cannot set a crossProfile intent filter with no filter actions");
            return;
        }
        synchronized (this.mLock) {
            CrossProfileIntentFilter crossProfileIntentFilter = new CrossProfileIntentFilter(watchedIntentFilter, str, i2, i3, this.mUserManager.getCrossProfileIntentFilterAccessControl(i, i2));
            CrossProfileIntentResolver editCrossProfileIntentResolverLPw = this.mSettings.editCrossProfileIntentResolverLPw(i);
            ArrayList findFilters = editCrossProfileIntentResolverLPw.findFilters(watchedIntentFilter);
            if (findFilters != null) {
                int size = findFilters.size();
                for (int i4 = 0; i4 < size; i4++) {
                    if (crossProfileIntentFilter.equalsIgnoreFilter((CrossProfileIntentFilter) findFilters.get(i4))) {
                        return;
                    }
                }
            }
            editCrossProfileIntentResolverLPw.addFilter((PackageDataSnapshot) snapshotComputer(), (WatchedIntentFilter) crossProfileIntentFilter);
            scheduleWritePackageRestrictions(i);
        }
    }

    public final void enforceOwnerRights(Computer computer, String str, int i) {
        if (UserHandle.getAppId(i) == 1000 || UserHandle.getAppId(i) == 5250) {
            return;
        }
        if (!ArrayUtils.contains(computer.getPackagesForUid(i), str)) {
            throw new SecurityException("Calling uid " + i + " does not own package " + str);
        }
        int userId = UserHandle.getUserId(i);
        if (computer.getPackageInfo(str, 0L, userId) != null) {
            return;
        }
        throw new IllegalArgumentException("Unknown package " + str + " on user " + userId);
    }

    public void sendSessionCommitBroadcast(PackageInstaller.SessionInfo sessionInfo, int i) {
        UserManagerService userManagerService = UserManagerService.getInstance();
        if (userManagerService == null || sessionInfo.isStaged()) {
            return;
        }
        UserInfo profileParent = userManagerService.getProfileParent(i);
        int i2 = profileParent != null ? profileParent.id : i;
        this.mBroadcastHelper.sendSessionCommitBroadcast(sessionInfo, i, i2, snapshotComputer().getDefaultHomeActivity(i2), this.mAppPredictionServicePackage);
    }

    public final String getSetupWizardPackageNameImpl(Computer computer) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.SETUP_WIZARD");
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, null, 1835520L, UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        Slog.e("PackageManager", "There should probably be exactly one setup wizard; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    public final String getStorageManagerPackageName(Computer computer) {
        List queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(new Intent("android.os.storage.action.MANAGE_STORAGE"), null, 1835520L, UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        Slog.w("PackageManager", "There should probably be exactly one storage manager; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    public static String getRequiredSdkSandboxPackageName(Computer computer) {
        List queryIntentServicesInternal = computer.queryIntentServicesInternal(new Intent("com.android.sdksandbox.SdkSandboxService"), null, 1835008L, 0, Process.myUid(), false);
        if (queryIntentServicesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentServicesInternal.get(0)).getComponentInfo().packageName;
        }
        throw new RuntimeException("There should exactly one sdk sandbox package; found " + queryIntentServicesInternal.size() + ": matches=" + queryIntentServicesInternal);
    }

    public final String getRetailDemoPackageName() {
        AndroidPackage androidPackage;
        SigningDetails signingDetails;
        String string = this.mContext.getString(R.string.global_actions);
        String string2 = this.mContext.getString(R.string.global_actions_airplane_mode_off_status);
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && (androidPackage = (AndroidPackage) this.mPackages.get(string)) != null && (signingDetails = androidPackage.getSigningDetails()) != null && signingDetails.getSignatures() != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                for (Signature signature : signingDetails.getSignatures()) {
                    if (TextUtils.equals(string2, HexEncoding.encodeToString(messageDigest.digest(signature.toByteArray()), false))) {
                        return string;
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                Slog.e("PackageManager", "Unable to verify signatures as getting the retail demo package name", e);
            }
        }
        return null;
    }

    public String getPackageFromComponentString(int i) {
        ComponentName unflattenFromString;
        String string = this.mContext.getString(i);
        if (TextUtils.isEmpty(string) || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        return unflattenFromString.getPackageName();
    }

    public String ensureSystemPackageName(Computer computer, String str) {
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (computer.getPackageInfo(str, 2097152L, 0) != null) {
                return str;
            }
            PackageInfo packageInfo = computer.getPackageInfo(str, 0L, 0);
            if (packageInfo != null) {
                EventLog.writeEvent(1397638484, "145981139", Integer.valueOf(packageInfo.applicationInfo.uid), "");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Missing required system package: ");
            sb.append(str);
            sb.append(packageInfo != null ? ", but found with extended search." : ".");
            Log.w("PackageManager", sb.toString());
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateComponentLabelIcon(final ComponentName componentName, final String str, final Integer num, final int i) {
        if (componentName == null) {
            throw new IllegalArgumentException("Must specify a component");
        }
        int callingUid = Binder.getCallingUid();
        String packageName = componentName.getPackageName();
        Computer snapshotComputer = snapshotComputer();
        if (!UserHandle.isSameApp(callingUid, snapshotComputer.getPackageUid(packageName, 0L, i))) {
            throw new SecurityException("The calling UID (" + callingUid + ") does not match the target UID");
        }
        String string = this.mContext.getString(R.string.foreground_service_multiple_separator);
        if (TextUtils.isEmpty(string)) {
            throw new SecurityException("There is no package defined as allowed to change a component's label or icon");
        }
        int packageUid = snapshotComputer.getPackageUid(string, 1048576L, i);
        if (packageUid == -1 || !UserHandle.isSameApp(callingUid, packageUid)) {
            throw new SecurityException("The calling UID (" + callingUid + ") is not allowed to change a component's label or icon");
        }
        PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(packageName);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || (!packageStateInternal.isSystem() && !packageStateInternal.isUpdatedSystemApp())) {
            throw new SecurityException("Changing the label is not allowed for " + componentName);
        }
        if (!snapshotComputer.getComponentResolver().componentExists(componentName)) {
            throw new IllegalArgumentException("Component " + componentName + " not found");
        }
        Pair overrideLabelIconForComponent = packageStateInternal.getUserStateOrDefault(i).getOverrideLabelIconForComponent(componentName);
        String str2 = overrideLabelIconForComponent == null ? null : (String) overrideLabelIconForComponent.first;
        Integer num2 = overrideLabelIconForComponent == null ? null : (Integer) overrideLabelIconForComponent.second;
        if (TextUtils.equals(str2, str) && Objects.equals(num2, num)) {
            return;
        }
        commitPackageStateMutation(null, packageName, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageManagerService.lambda$updateComponentLabelIcon$58(i, componentName, str, num, (PackageStateWrite) obj);
            }
        });
        this.mPendingBroadcasts.addComponent(i, packageName, componentName.getClassName());
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    public static /* synthetic */ void lambda$updateComponentLabelIcon$58(int i, ComponentName componentName, String str, Integer num, PackageStateWrite packageStateWrite) {
        packageStateWrite.userState(i).setComponentLabelIcon(componentName, str, num);
    }

    public final Set buildRequiredSystemPackages() {
        Set requiredSystemPackages = this.mInjector.getSystemConfig().getRequiredSystemPackages();
        requiredSystemPackages.add(this.mRequiredInstallerPackage);
        requiredSystemPackages.add(this.mRequiredUninstallerPackage);
        requiredSystemPackages.add(this.mRequiredPermissionControllerPackage);
        requiredSystemPackages.add(this.mRequiredSdkSandboxPackage);
        return requiredSystemPackages;
    }

    public boolean isRequiredSystemPackage(Computer computer, String str, int i) {
        return this.mRequiredSystemPackages.contains(str) && computer.getPackageStateInternal(str).isSystem() && i == 0;
    }

    public Set getRequiredSystemPackages() {
        return this.mRequiredSystemPackages;
    }

    public final void setEnabledSettings(List list, int i, String str) {
        int i2;
        Computer computer;
        int i3;
        int i4;
        int i5;
        String str2;
        int i6;
        AndroidPackage androidPackage;
        int callingUid = Binder.getCallingUid();
        Computer snapshotComputer = snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, false, true, "set enabled");
        String str3 = str == null ? Integer.toString(Binder.getCallingUid()) + "/" + PmServerUtils.getProcessNameForPid(Binder.getCallingPid()) : str;
        int i7 = 1;
        String str4 = "1000/system".equals(str3) ? " stackTrace = " + Debug.getCallers(1, 5) : "";
        int size = list.size();
        int i8 = 0;
        while (i8 < size) {
            int enabledState = ((PackageManager.ComponentEnabledSetting) list.get(i8)).getEnabledState();
            if (enabledState != 0 && enabledState != i7 && enabledState != 2 && enabledState != 3 && enabledState != 4) {
                throw new IllegalArgumentException("Invalid new component state: " + enabledState);
            }
            PackageManager.ComponentEnabledSetting componentEnabledSetting = (PackageManager.ComponentEnabledSetting) list.get(i8);
            Log.d("PackageManager", "setEnabledSetting : userId = " + i + " packageName = " + componentEnabledSetting.getPackageName() + " cmp = " + componentEnabledSetting.getClassName() + " newState = " + componentEnabledSetting.getEnabledState() + " callingPackage = " + str3 + str4);
            String packageName = ((PackageManager.ComponentEnabledSetting) list.get(i8)).getPackageName();
            ComponentName componentName = ((PackageManager.ComponentEnabledSetting) list.get(i8)).getComponentName();
            PackageSetting packageLPr = this.mSettings.getPackageLPr(packageName);
            if (componentName != null && packageLPr != null && !packageLPr.isSystem()) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setPackage(packageName);
                i5 = enabledState;
                str2 = str4;
                i6 = i8;
                Iterator it = snapshotComputer.queryIntentActivitiesInternal(intent, null, 512L, i).iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((ResolveInfo) it.next()).activityInfo.name.equals(componentName.getClassName())) {
                            FrameworkStatsLog.write(FrameworkStatsLog.DETECT_MALICIOUS_ACTION, packageName, "APP_ICON");
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } else {
                i5 = enabledState;
                str2 = str4;
                i6 = i8;
            }
            String packageName2 = ((PackageManager.ComponentEnabledSetting) list.get(i6)).getPackageName();
            if (isRequiredSystemPackage(snapshotComputer, packageName2, i) && ((i5 == 2 || i5 == 3 || i5 == 4) && !((PackageManager.ComponentEnabledSetting) list.get(i6)).isComponent())) {
                PmLog.logDebugInfoAndLogcat("Cannot disable required package " + packageName2 + " callingPackage: " + str3);
                throw new SecurityException("Cannot disable required package " + packageName2);
            }
            String packageName3 = ((PackageManager.ComponentEnabledSetting) list.get(i6)).getPackageName();
            PackageSetting packageLPr2 = this.mSettings.getPackageLPr(packageName3);
            if (packageName3 != null && (androidPackage = snapshotComputer.getPackage(packageName3)) != null) {
                boolean z = SemSamsungThemeUtils.hasSamsungOverlayPermission(androidPackage.getRequestedPermissions()) && !str3.equalsIgnoreCase("com.samsung.android.themecenter");
                boolean isZippedLocaleOverlay = SemSamsungThemeUtils.isZippedLocaleOverlay(androidPackage);
                boolean z2 = callingUid == 2000 && "com.samsung.android.themestore".equals(packageName3) && (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPlatformSigned(packageName3) || packageLPr2.isSystem());
                if ((z || isZippedLocaleOverlay || z2 || SamsungThemeConstants.protectedApps.contains(packageName3)) && i5 > 1) {
                    throw new SecurityException("Cannot disable a protected package: " + packageName3);
                }
            }
            i8 = i6 + 1;
            str4 = str2;
            i7 = 1;
        }
        if (size > i7) {
            ArraySet arraySet = new ArraySet();
            ArraySet arraySet2 = new ArraySet();
            ArrayMap arrayMap = new ArrayMap();
            for (int i9 = 0; i9 < size; i9++) {
                PackageManager.ComponentEnabledSetting componentEnabledSetting2 = (PackageManager.ComponentEnabledSetting) list.get(i9);
                String packageName4 = componentEnabledSetting2.getPackageName();
                if (componentEnabledSetting2.isComponent()) {
                    ComponentName componentName2 = componentEnabledSetting2.getComponentName();
                    if (arraySet2.contains(componentName2)) {
                        throw new IllegalArgumentException("The component " + componentName2 + " is duplicated");
                    }
                    arraySet2.add(componentName2);
                    Integer num = (Integer) arrayMap.get(packageName4);
                    if (num == null) {
                        arrayMap.put(packageName4, Integer.valueOf(componentEnabledSetting2.getEnabledFlags()));
                    } else if ((num.intValue() & 1) != (componentEnabledSetting2.getEnabledFlags() & 1)) {
                        throw new IllegalArgumentException("A conflict of the DONT_KILL_APP flag between components in the package " + packageName4);
                    }
                } else {
                    if (arraySet.contains(packageName4)) {
                        throw new IllegalArgumentException("The package " + packageName4 + " is duplicated");
                    }
                    arraySet.add(packageName4);
                }
            }
        }
        boolean z3 = this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE") == 0;
        boolean[] zArr = new boolean[size];
        Arrays.fill(zArr, true);
        ArrayMap arrayMap2 = new ArrayMap(size);
        synchronized (this.mLock) {
            Computer snapshotComputer2 = snapshotComputer();
            int i10 = 0;
            while (i10 < size) {
                PackageManager.ComponentEnabledSetting componentEnabledSetting3 = (PackageManager.ComponentEnabledSetting) list.get(i10);
                String packageName5 = componentEnabledSetting3.getPackageName();
                if (!arrayMap2.containsKey(packageName5)) {
                    boolean contains = ArrayUtils.contains(snapshotComputer2.getPackagesForUid(callingUid), packageName5);
                    PackageSetting packageLPr3 = this.mSettings.getPackageLPr(packageName5);
                    if (!contains && !z3) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Attempt to change component state; pid=");
                        sb.append(Binder.getCallingPid());
                        sb.append(", uid=");
                        sb.append(callingUid);
                        sb.append(componentEnabledSetting3.isComponent() ? ", component=" + componentEnabledSetting3.getComponentName() : ", package=" + packageName5);
                        throw new SecurityException(sb.toString());
                    }
                    if (packageLPr3 != null && !snapshotComputer2.shouldFilterApplicationIncludingUninstalled(packageLPr3, callingUid, i)) {
                        if (!contains && this.mProtectedPackages.isPackageStateProtected(i, packageName5)) {
                            throw new SecurityException("Cannot disable a protected package: " + packageName5);
                        }
                        if (callingUid == 2000 && (packageLPr3.getFlags() & 256) == 0) {
                            int enabled = packageLPr3.getEnabled(i);
                            computer = snapshotComputer2;
                            int enabledState2 = componentEnabledSetting3.getEnabledState();
                            if (!componentEnabledSetting3.isComponent()) {
                                i3 = callingUid;
                                if (enabled == 3 || enabled == 0) {
                                    i4 = 1;
                                } else {
                                    i4 = 1;
                                    if (enabled == 1) {
                                    }
                                }
                                if (enabledState2 != 3 && enabledState2 != 0 && enabledState2 != i4) {
                                }
                            }
                            throw new SecurityException("Shell cannot change component state for " + componentEnabledSetting3.getComponentName() + " to " + enabledState2);
                        }
                        computer = snapshotComputer2;
                        i3 = callingUid;
                        arrayMap2.put(packageName5, packageLPr3);
                    }
                    throw new IllegalArgumentException(componentEnabledSetting3.isComponent() ? "Unknown component: " + componentEnabledSetting3.getComponentName() : "Unknown package: " + packageName5);
                }
                computer = snapshotComputer2;
                i3 = callingUid;
                i10++;
                snapshotComputer2 = computer;
                callingUid = i3;
            }
            for (int i11 = 0; i11 < size; i11++) {
                if (!PmHook.isUpdateAllowedByMdm((PackageManager.ComponentEnabledSetting) list.get(i11), i, this.mContext)) {
                    zArr[i11] = false;
                }
            }
            for (int i12 = 0; i12 < size; i12++) {
                PackageManager.ComponentEnabledSetting componentEnabledSetting4 = (PackageManager.ComponentEnabledSetting) list.get(i12);
                if (componentEnabledSetting4.isComponent()) {
                    String packageName6 = componentEnabledSetting4.getPackageName();
                    String className = componentEnabledSetting4.getClassName();
                    if (!z3 && PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(className)) {
                        throw new SecurityException("Cannot disable a system-generated component");
                    }
                    AndroidPackageInternal pkg = ((PackageSetting) arrayMap2.get(packageName6)).getPkg();
                    if (pkg == null || !AndroidPackageUtils.hasComponentClassName(pkg, className)) {
                        if (pkg != null && pkg.getTargetSdkVersion() >= 16) {
                            throw new IllegalArgumentException("Component class " + className + " does not exist in " + packageName6);
                        }
                        Slog.w("PackageManager", "Failed setComponentEnabledSetting: component class " + className + " does not exist in " + packageName6);
                        zArr[i12] = false;
                    }
                }
            }
        }
        for (int i13 = 0; i13 < size; i13++) {
            PackageManager.ComponentEnabledSetting componentEnabledSetting5 = (PackageManager.ComponentEnabledSetting) list.get(i13);
            if (!componentEnabledSetting5.isComponent()) {
                PackageSetting packageSetting = (PackageSetting) arrayMap2.get(componentEnabledSetting5.getPackageName());
                int enabledState3 = componentEnabledSetting5.getEnabledState();
                synchronized (this.mLock) {
                    if (packageSetting.getEnabled(i) == enabledState3) {
                        zArr[i13] = false;
                    } else {
                        AndroidPackageInternal pkg2 = packageSetting.getPkg();
                        if ((pkg2 != null && pkg2.isStub() && packageSetting.isSystem()) && ((enabledState3 == 0 || enabledState3 == 1) && !this.mInstallPackageHelper.enableCompressedPackage(pkg2, packageSetting))) {
                            Slog.w("PackageManager", "Failed setApplicationEnabledSetting: failed to enable commpressed package " + componentEnabledSetting5.getPackageName());
                            zArr[i13] = false;
                        }
                    }
                }
            }
        }
        ArrayMap arrayMap3 = new ArrayMap(size);
        synchronized (this.mLock) {
            Computer snapshotComputer3 = snapshotComputer();
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            for (int i14 = 0; i14 < size; i14 = i2 + 1) {
                if (zArr[i14]) {
                    PackageManager.ComponentEnabledSetting componentEnabledSetting6 = (PackageManager.ComponentEnabledSetting) list.get(i14);
                    String packageName7 = componentEnabledSetting6.getPackageName();
                    i2 = i14;
                    if (setEnabledSettingInternalLocked(snapshotComputer3, (PackageSetting) arrayMap2.get(packageName7), componentEnabledSetting6, i, str3)) {
                        if ((componentEnabledSetting6.getEnabledFlags() & 2) != 0) {
                            z5 = true;
                        }
                        PmHook.enableDisableApplicationLog(packageName7, componentEnabledSetting6.getClassName(), componentEnabledSetting6.getEnabledState(), i);
                        String className2 = componentEnabledSetting6.isComponent() ? componentEnabledSetting6.getClassName() : packageName7;
                        if ((componentEnabledSetting6.getEnabledFlags() & 4) != 0) {
                            ArrayList arrayList = this.mEmergencyModePackageHandler.getPendingBroadcastsForBurst().get(i, packageName7);
                            boolean z7 = arrayList == null;
                            if (z7) {
                                arrayList = new ArrayList();
                            }
                            if (!arrayList.contains(className2)) {
                                arrayList.add(className2);
                            }
                            if (z7) {
                                this.mEmergencyModePackageHandler.getPendingBroadcastsForBurst().put(i, packageName7, arrayList);
                            }
                            this.mEmergencyModePackageHandler.getPendingBroadcastsForBurst().putNewState(i, packageName7, componentEnabledSetting6.getEnabledState());
                        } else if ((componentEnabledSetting6.getEnabledFlags() & 1) == 0) {
                            ArrayList arrayList2 = (ArrayList) arrayMap3.get(packageName7);
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            if (!arrayList2.contains(className2)) {
                                arrayList2.add(className2);
                            }
                            arrayMap3.put(packageName7, arrayList2);
                            this.mPendingBroadcasts.remove(i, packageName7);
                        } else {
                            this.mPendingBroadcasts.addComponent(i, packageName7, className2);
                            z4 = true;
                            z6 = true;
                        }
                        z4 = true;
                    }
                } else {
                    i2 = i14;
                }
            }
            if (z4) {
                if (z5) {
                    flushPackageRestrictionsAsUserInternalLocked(i);
                } else {
                    scheduleWritePackageRestrictions(i);
                }
                if (z6 && !this.mHandler.hasMessages(1)) {
                    this.mHandler.sendEmptyMessageDelayed(1, SystemClock.uptimeMillis() > this.mServiceStartWithDelay ? 1000L : 10000L);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Computer snapshotComputer4 = snapshotComputer();
                    for (int i15 = 0; i15 < arrayMap3.size(); i15++) {
                        String str5 = (String) arrayMap3.keyAt(i15);
                        sendPackageChangedBroadcast(snapshotComputer4, str5, false, (ArrayList) arrayMap3.valueAt(i15), UserHandle.getUid(i, ((PackageSetting) arrayMap2.get(str5)).getAppId()), null);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public final boolean setEnabledSettingInternalLocked(Computer computer, PackageSetting packageSetting, PackageManager.ComponentEnabledSetting componentEnabledSetting, int i, String str) {
        boolean restoreComponentLPw;
        int enabledState = componentEnabledSetting.getEnabledState();
        String packageName = componentEnabledSetting.getPackageName();
        if (!componentEnabledSetting.isComponent()) {
            packageSetting.setEnabled(enabledState, i, str);
            if ((enabledState == 3 || enabledState == 2) && checkPermission("android.permission.SUSPEND_APPS", packageName, i) == 0) {
                unsuspendForSuspendingPackage(computer, packageName, i);
                removeAllDistractingPackageRestrictions(computer, i);
            }
            restoreComponentLPw = true;
        } else {
            String className = componentEnabledSetting.getClassName();
            if (enabledState == 0) {
                restoreComponentLPw = packageSetting.restoreComponentLPw(className, i);
            } else if (enabledState == 1) {
                restoreComponentLPw = packageSetting.enableComponentLPw(className, i);
            } else if (enabledState == 2) {
                restoreComponentLPw = packageSetting.disableComponentLPw(className, i);
            } else {
                Slog.e("PackageManager", "Failed setComponentEnabledSetting: component " + packageName + "/" + className + " requested an invalid new component state: " + enabledState);
                restoreComponentLPw = false;
            }
        }
        if (!restoreComponentLPw) {
            return false;
        }
        updateSequenceNumberLP(packageSetting, new int[]{i});
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            updateInstantAppInstallerLocked(packageName);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void flushPackageRestrictionsAsUserInternalLocked(int i) {
        this.mSettings.writePackageRestrictionsLPr(i);
        synchronized (this.mDirtyUsers) {
            this.mDirtyUsers.remove(Integer.valueOf(i));
            if (this.mDirtyUsers.isEmpty()) {
                this.mBackgroundHandler.removeMessages(14);
            }
        }
    }

    public void sendPackageChangedBroadcast(Computer computer, final String str, final boolean z, final ArrayList arrayList, final int i, final String str2) {
        if (computer.getPackageStateInternal(str, 1000) == null) {
            return;
        }
        int userId = UserHandle.getUserId(i);
        boolean isInstantAppInternal = computer.isInstantAppInternal(str, userId, 1000);
        final int[] iArr = isInstantAppInternal ? EMPTY_INT_ARRAY : new int[]{userId};
        final int[] iArr2 = isInstantAppInternal ? new int[]{userId} : EMPTY_INT_ARRAY;
        final SparseArray visibilityAllowLists = isInstantAppInternal ? null : computer.getVisibilityAllowLists(str, iArr);
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$sendPackageChangedBroadcast$59(str, z, arrayList, i, str2, iArr, iArr2, visibilityAllowLists);
            }
        });
    }

    public /* synthetic */ void lambda$sendPackageChangedBroadcast$59(String str, boolean z, ArrayList arrayList, int i, String str2, int[] iArr, int[] iArr2, SparseArray sparseArray) {
        this.mBroadcastHelper.sendPackageChangedBroadcast(str, z, arrayList, i, str2, iArr, iArr2, sparseArray);
    }

    public void waitForAppDataPrepared() {
        this.mPmLifecycle.onWaitForAppDataPrepared();
        if (this.mIsUpgrade) {
            this.mInstallPackageHelper.clearNoninstalledDataApps();
        }
        Future future = this.mPrepareAppDataFuture;
        if (future == null) {
            return;
        }
        ConcurrentUtils.waitForFutureNoInterrupt(future, "wait for prepareAppData");
        this.mPrepareAppDataFuture = null;
    }

    public void systemReady() {
        PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can claim the system is ready");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        List list = this.mReleaseOnSystemReady;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                F2fsUtils.releaseCompressedBlocks(contentResolver, (File) this.mReleaseOnSystemReady.get(size));
            }
            this.mReleaseOnSystemReady = null;
        }
        this.mSystemReady = true;
        AnonymousClass4 anonymousClass4 = new ContentObserver(this.mHandler) { // from class: com.android.server.pm.PackageManagerService.4
            public final /* synthetic */ ContentResolver val$resolver;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(Handler handler, ContentResolver contentResolver2) {
                super(handler);
                r3 = contentResolver2;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                boolean z2 = Settings.Global.getInt(r3, "enable_ephemeral_feature", 1) == 0;
                for (int i : UserManagerService.getInstance().getUserIds()) {
                    PackageManagerService.this.mWebInstantAppsDisabled.put(i, z2 || Settings.Secure.getIntForUser(r3, "instant_apps_enabled", 1, i) == 0);
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("enable_ephemeral_feature"), false, anonymousClass4, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("instant_apps_enabled"), false, anonymousClass4, -1);
        anonymousClass4.onChange(true);
        if (CoreRune.SYSFW_APP_SPEG) {
            this.mSpeg = (SpegService) LocalServices.getService(SpegService.class);
            AnonymousClass5 anonymousClass5 = new ContentObserver(this.mHandler) { // from class: com.android.server.pm.PackageManagerService.5
                public final /* synthetic */ ContentResolver val$resolver;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(Handler handler, ContentResolver contentResolver2) {
                    super(handler);
                    r3 = contentResolver2;
                }

                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    AppPrelaunchManagerService appPrelaunchManagerService;
                    boolean z2 = Settings.Global.getInt(r3, "device_provisioned", 0) == 1 || Settings.Secure.getInt(r3, "user_setup_complete", 0) == 1;
                    if (PackageManagerService.this.mSpeg != null) {
                        PackageManagerService.this.mSpeg.setSetupWizardState(z2);
                    }
                    if (CoreRune.SYSFW_APP_PREL && (appPrelaunchManagerService = (AppPrelaunchManagerService) LocalServices.getService(AppPrelaunchManagerService.class)) != null) {
                        appPrelaunchManagerService.setSetupWizardFinished(z2);
                    }
                    Log.d("PackageManager", "SetupWizardFinished: " + z2);
                }
            };
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, anonymousClass5, -1);
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, anonymousClass5, -1);
            anonymousClass5.onChange(true);
        }
        this.mAppsFilter.onSystemReady((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class));
        CarrierAppUtils.disableCarrierAppsUntilPrivileged(this.mContext.getOpPackageName(), 0, this.mContext);
        disableSkuSpecificApps();
        ParsingPackageUtils.setCompatibilityModeEnabled(Settings.Global.getInt(this.mContext.getContentResolver(), "compatibility_mode", 1) == 1);
        synchronized (this.mLock) {
            ArrayList systemReady = this.mSettings.systemReady(this.mComponentResolver);
            for (int i = 0; i < systemReady.size(); i++) {
                this.mSettings.writePackageRestrictionsLPr(((Integer) systemReady.get(i)).intValue());
            }
        }
        this.mUserManager.systemReady();
        ((StorageManager) this.mInjector.getSystemService(StorageManager.class)).registerListener(this.mStorageEventHelper);
        this.mInstallerService.systemReady();
        this.mPackageDexOptimizer.systemReady();
        this.mUserManager.reconcileUsers(StorageManager.UUID_PRIVATE_INTERNAL);
        this.mStorageEventHelper.reconcileApps(snapshotComputer(), StorageManager.UUID_PRIVATE_INTERNAL);
        this.mPermissionManager.onSystemReady();
        if (CoreRune.SYSFW_APP_SPEG) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_ON");
            intentFilter.addAction("com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_OFF");
            this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.PackageManagerService.6
                public AnonymousClass6() {
                }

                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    boolean z;
                    AppPrelaunchManagerService appPrelaunchManagerService;
                    String action = intent.getAction();
                    if ("com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_ON".equals(action)) {
                        z = true;
                    } else {
                        "com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_OFF".equals(action);
                        z = false;
                    }
                    if (PackageManagerService.this.mSpeg != null) {
                        PackageManagerService.this.mSpeg.setSmartSwitchState(z);
                    }
                    if (!CoreRune.SYSFW_APP_PREL || (appPrelaunchManagerService = (AppPrelaunchManagerService) LocalServices.getService(AppPrelaunchManagerService.class)) == null) {
                        return;
                    }
                    appPrelaunchManagerService.setSmartSwitchState(z);
                }
            }, intentFilter, "com.samsung.android.permission.SSRM_NOTIFICATION_PERMISSION", null);
        }
        int[] iArr = EMPTY_INT_ARRAY;
        List users = this.mInjector.getUserManagerInternal().getUsers(true, true, false);
        int size2 = users.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int i3 = ((UserInfo) users.get(i2)).id;
            if (this.mSettings.isPermissionUpgradeNeeded(i3)) {
                iArr = ArrayUtils.appendInt(iArr, i3);
            }
        }
        for (int i4 : iArr) {
            this.mLegacyPermissionManager.grantDefaultPermissions(i4);
        }
        if (iArr == EMPTY_INT_ARRAY) {
            this.mLegacyPermissionManager.scheduleReadDefaultPermissionExceptions();
        }
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils.initializeSettingsForMonetization(this.mIsUpgrade, this.mFirstBoot);
        }
        if (this.mInstantAppResolverConnection != null) {
            this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.PackageManagerService.7
                public AnonymousClass7() {
                }

                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    PackageManagerService.this.mInstantAppResolverConnection.optimisticBind();
                    PackageManagerService.this.mContext.unregisterReceiver(this);
                }
            }, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        }
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.PackageManagerService.8
            public AnonymousClass8() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Uri data;
                String schemeSpecificPart;
                Computer snapshotComputer;
                AndroidPackage androidPackage;
                if (intent == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null || (androidPackage = (snapshotComputer = PackageManagerService.this.snapshotComputer()).getPackage(schemeSpecificPart)) == null) {
                    return;
                }
                PackageManagerService.this.sendPackageChangedBroadcast(snapshotComputer, androidPackage.getPackageName(), true, new ArrayList(Collections.singletonList(androidPackage.getPackageName())), androidPackage.getUid(), "android.intent.action.OVERLAY_CHANGED");
            }
        }, intentFilter2);
        this.mModuleInfoProvider.systemReady();
        this.mInstallerService.restoreAndApplyStagedSessionIfNeeded();
        this.mExistingPackages = null;
        DeviceConfig.addOnPropertiesChangedListener("package_manager_service", this.mInjector.getBackgroundExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda60
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                PackageManagerService.this.lambda$systemReady$60(properties);
            }
        });
        if (!DexOptHelper.useArtService()) {
            try {
                this.mBackgroundDexOptService.systemReady();
            } catch (Installer.LegacyDexoptDisabledException e) {
                throw new RuntimeException(e);
            }
        }
        schedulePruneUnusedStaticSharedLibraries(false);
        DexUseManagerLocal dexUseManagerLocal = DexOptHelper.getDexUseManagerLocal();
        if (dexUseManagerLocal != null) {
            dexUseManagerLocal.systemReady();
        }
        FrozenPackageInterceptor frozenPackageInterceptor = this.mFrozenPackageInterceptor;
        if (frozenPackageInterceptor != null) {
            frozenPackageInterceptor.registerListeners();
        }
        this.mPmLifecycle.onSystemReady();
        PackageManagerAppOpPreAllow.appOpPreAllow(isFirstBoot(), this.mCustomInjector.getOmcInstallHelper().needsOmcOrTssInit(), this.mContext);
        if (Settings.Global.getLong(this.mContext.getContentResolver(), "verifier_timeout_samsung", -1L) == -1) {
            Settings.Global.putLong(this.mContext.getContentResolver(), "verifier_timeout_samsung", 0L);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$4 */
    /* loaded from: classes3.dex */
    public class AnonymousClass4 extends ContentObserver {
        public final /* synthetic */ ContentResolver val$resolver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Handler handler, ContentResolver contentResolver2) {
            super(handler);
            r3 = contentResolver2;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            boolean z2 = Settings.Global.getInt(r3, "enable_ephemeral_feature", 1) == 0;
            for (int i : UserManagerService.getInstance().getUserIds()) {
                PackageManagerService.this.mWebInstantAppsDisabled.put(i, z2 || Settings.Secure.getIntForUser(r3, "instant_apps_enabled", 1, i) == 0);
            }
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$5 */
    /* loaded from: classes3.dex */
    public class AnonymousClass5 extends ContentObserver {
        public final /* synthetic */ ContentResolver val$resolver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(Handler handler, ContentResolver contentResolver2) {
            super(handler);
            r3 = contentResolver2;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            AppPrelaunchManagerService appPrelaunchManagerService;
            boolean z2 = Settings.Global.getInt(r3, "device_provisioned", 0) == 1 || Settings.Secure.getInt(r3, "user_setup_complete", 0) == 1;
            if (PackageManagerService.this.mSpeg != null) {
                PackageManagerService.this.mSpeg.setSetupWizardState(z2);
            }
            if (CoreRune.SYSFW_APP_PREL && (appPrelaunchManagerService = (AppPrelaunchManagerService) LocalServices.getService(AppPrelaunchManagerService.class)) != null) {
                appPrelaunchManagerService.setSetupWizardFinished(z2);
            }
            Log.d("PackageManager", "SetupWizardFinished: " + z2);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$6 */
    /* loaded from: classes3.dex */
    public class AnonymousClass6 extends BroadcastReceiver {
        public AnonymousClass6() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean z;
            AppPrelaunchManagerService appPrelaunchManagerService;
            String action = intent.getAction();
            if ("com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_ON".equals(action)) {
                z = true;
            } else {
                "com.samsung.android.intent.action.REQUEST_COOLDOWN_INSTALL_OFF".equals(action);
                z = false;
            }
            if (PackageManagerService.this.mSpeg != null) {
                PackageManagerService.this.mSpeg.setSmartSwitchState(z);
            }
            if (!CoreRune.SYSFW_APP_PREL || (appPrelaunchManagerService = (AppPrelaunchManagerService) LocalServices.getService(AppPrelaunchManagerService.class)) == null) {
                return;
            }
            appPrelaunchManagerService.setSmartSwitchState(z);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$7 */
    /* loaded from: classes3.dex */
    public class AnonymousClass7 extends BroadcastReceiver {
        public AnonymousClass7() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            PackageManagerService.this.mInstantAppResolverConnection.optimisticBind();
            PackageManagerService.this.mContext.unregisterReceiver(this);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$8 */
    /* loaded from: classes3.dex */
    public class AnonymousClass8 extends BroadcastReceiver {
        public AnonymousClass8() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            String schemeSpecificPart;
            Computer snapshotComputer;
            AndroidPackage androidPackage;
            if (intent == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null || (androidPackage = (snapshotComputer = PackageManagerService.this.snapshotComputer()).getPackage(schemeSpecificPart)) == null) {
                return;
            }
            PackageManagerService.this.sendPackageChangedBroadcast(snapshotComputer, androidPackage.getPackageName(), true, new ArrayList(Collections.singletonList(androidPackage.getPackageName())), androidPackage.getUid(), "android.intent.action.OVERLAY_CHANGED");
        }
    }

    public /* synthetic */ void lambda$systemReady$60(DeviceConfig.Properties properties) {
        Set keyset = properties.getKeyset();
        if (keyset.contains("incfs_default_timeouts") || keyset.contains("known_digesters_list")) {
            this.mPerUidReadTimeoutsCache = null;
        }
    }

    public final void disableSkuSpecificApps() {
        String[] stringArray = this.mContext.getResources().getStringArray(17236171);
        String[] stringArray2 = this.mContext.getResources().getStringArray(17236170);
        if (ArrayUtils.isEmpty(stringArray)) {
            return;
        }
        String str = SystemProperties.get("ro.boot.hardware.sku");
        if (TextUtils.isEmpty(str) || !ArrayUtils.contains(stringArray2, str)) {
            Computer snapshotComputer = snapshotComputer();
            for (String str2 : stringArray) {
                setSystemAppHiddenUntilInstalled(snapshotComputer, str2, true);
                Iterator it = this.mInjector.getUserManagerInternal().getUsers(false).iterator();
                while (it.hasNext()) {
                    setSystemAppInstallState(snapshotComputer, str2, false, ((UserInfo) it.next()).id);
                }
            }
        }
    }

    public PackageFreezer freezePackage(String str, int i, String str2, int i2) {
        return new PackageFreezer(str, i, str2, this, i2);
    }

    public PackageFreezer freezePackageForDelete(String str, int i, int i2, String str2, int i3) {
        if ((i2 & 8) != 0) {
            return new PackageFreezer(this);
        }
        return freezePackage(str, i, str2, i3);
    }

    public void cleanUpUser(UserManagerService userManagerService, int i) {
        synchronized (this.mLock) {
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.remove(Integer.valueOf(i));
            }
            this.mUserNeedsBadging.delete(i);
            this.mPermissionManager.onUserRemoved(i);
            this.mSettings.removeUserLPw(i);
            this.mPendingBroadcasts.remove(i);
            this.mEmergencyModePackageHandler.getPendingBroadcastsForBurst().remove(i);
            this.mDeletePackageHelper.removeUnusedPackagesLPw(userManagerService, i);
            this.mAppsFilter.onUserDeleted(snapshotComputer(), i);
        }
        this.mInstantAppRegistry.onUserRemoved(i);
    }

    public void createNewUser(int i, Set set, String[] strArr) {
        synchronized (this.mInstallLock) {
            this.mSettings.createNewUserLI(this, this.mInstaller, i, set, strArr);
        }
        synchronized (this.mLock) {
            scheduleWritePackageRestrictions(i);
            scheduleWritePackageListLocked(i);
            this.mAppsFilter.onUserCreated(snapshotComputer(), i);
        }
    }

    public void onNewUserCreated(int i, boolean z) {
        if (!z || !readPermissionStateForUser(i)) {
            this.mPermissionManager.onUserCreated(i);
            this.mLegacyPermissionManager.grantDefaultPermissions(i);
            this.mDomainVerificationManager.clearUser(i);
        }
        this.mPmLifecycle.onNewUserCreated(i);
    }

    public final boolean readPermissionStateForUser(int i) {
        boolean isPermissionUpgradeNeeded;
        synchronized (this.mLock) {
            this.mPermissionManager.writeLegacyPermissionStateTEMP();
            this.mSettings.readPermissionStateForUserSyncLPr(i);
            this.mPermissionManager.readLegacyPermissionStateTEMP();
            isPermissionUpgradeNeeded = this.mSettings.isPermissionUpgradeNeeded(i);
        }
        return isPermissionUpgradeNeeded;
    }

    public boolean isStorageLow() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DeviceStorageMonitorInternal deviceStorageMonitorInternal = (DeviceStorageMonitorInternal) this.mInjector.getLocalService(DeviceStorageMonitorInternal.class);
            if (deviceStorageMonitorInternal != null) {
                return deviceStorageMonitorInternal.isMemoryLow();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deletePackageIfUnused(Computer computer, final String str) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return;
        }
        SparseArray userStates = packageStateInternal.getUserStates();
        for (int i = 0; i < userStates.size(); i++) {
            if (((PackageUserStateInternal) userStates.valueAt(i)).isInstalled()) {
                return;
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PackageManagerService.this.lambda$deletePackageIfUnused$61(str);
            }
        });
    }

    public /* synthetic */ void lambda$deletePackageIfUnused$61(String str) {
        this.mDeletePackageHelper.deletePackageX(str, -1L, 0, 2, true);
    }

    public void deletePreloadsFileCache() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", "deletePreloadsFileCache");
        File dataPreloadsFileCacheDirectory = Environment.getDataPreloadsFileCacheDirectory();
        Slog.i("PackageManager", "Deleting preloaded file cache " + dataPreloadsFileCacheDirectory);
        FileUtils.deleteContents(dataPreloadsFileCacheDirectory);
    }

    public void setSystemAppHiddenUntilInstalled(Computer computer, final String str, final boolean z) {
        int callingUid = Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || packageStateInternal.getPkg() == null) {
            return;
        }
        if (packageStateInternal.getPkg().isCoreApp() && !z2) {
            throw new SecurityException("Only system or phone callers can modify core apps");
        }
        commitPackageStateMutation(null, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageManagerService.lambda$setSystemAppHiddenUntilInstalled$62(str, z, (PackageStateMutator) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$setSystemAppHiddenUntilInstalled$62(String str, boolean z, PackageStateMutator packageStateMutator) {
        packageStateMutator.forPackage(str).setHiddenUntilInstalled(z);
        packageStateMutator.forDisabledSystemPackage(str).setHiddenUntilInstalled(z);
    }

    public boolean setSystemAppInstallState(Computer computer, String str, boolean z, int i) {
        int callingUid = Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || packageStateInternal.getPkg() == null) {
            return false;
        }
        if (packageStateInternal.getPkg().isCoreApp() && !z2) {
            throw new SecurityException("Only system or phone callers can modify core apps");
        }
        if (packageStateInternal.getUserStateOrDefault(i).isInstalled() == z) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                this.mInstallPackageHelper.installExistingPackageAsUser(str, i, 4194304, 3, null, null);
                return true;
            }
            deletePackageVersioned(new VersionedPackage(str, -1), new PackageManager.LegacyPackageDeleteObserver((IPackageDeleteObserver) null).getBinder(), i, 4);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void finishPackageInstall(int i, boolean z) {
        PackageManagerServiceUtils.enforceSystemOrRoot("Only the system is allowed to finish installs");
        Trace.asyncTraceEnd(262144L, "restore", i);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, i, z ? 1 : 0));
    }

    public void checkPackageStartable(Computer computer, String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (computer.getInstantAppPackageName(callingUid) != null) {
            throw new SecurityException("Instant applications don't have access to this method");
        }
        if (!this.mUserManager.exists(i)) {
            throw new SecurityException("User doesn't exist");
        }
        computer.enforceCrossUserPermission(callingUid, i, false, false, "checkPackageStartable");
        int packageStartability = computer.getPackageStartability(this.mSafeMode, str, callingUid, i);
        if (packageStartability == 1) {
            throw new SecurityException("Package " + str + " was not found!");
        }
        if (packageStartability == 2) {
            throw new SecurityException("Package " + str + " not a system app!");
        }
        if (packageStartability == 3) {
            throw new SecurityException("Package " + str + " is currently frozen!");
        }
        if (packageStartability == 4) {
            throw new SecurityException("Package " + str + " is not encryption aware!");
        }
        if (packageStartability != 5) {
            return;
        }
        throw new SecurityException("Package " + str + " is not dualdar aware!");
    }

    public void setPackageStoppedState(Computer computer, final String str, final boolean z, final int i) {
        List asList = Arrays.asList("com.samsung.android.mtp", "com.android.mtp");
        if (!(z && asList.contains(str)) && this.mUserManager.exists(i)) {
            int callingUid = Binder.getCallingUid();
            if (computer.getInstantAppPackageName(callingUid) == null) {
                boolean z2 = false;
                if (!(this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE") == 0) && !ArrayUtils.contains(computer.getPackagesForUid(callingUid), str)) {
                    throw new SecurityException("Permission Denial: attempt to change stopped state from pid=" + Binder.getCallingPid() + ", uid=" + callingUid + ", package=" + str);
                }
                computer.enforceCrossUserPermission(callingUid, i, true, true, "stop package");
                PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str, callingUid, i);
                PackageUserStateInternal userStateOrDefault = packageStateForInstalledAndFiltered == null ? null : packageStateForInstalledAndFiltered.getUserStateOrDefault(i);
                if (packageStateForInstalledAndFiltered != null && userStateOrDefault.isStopped() != z) {
                    final boolean isNotLaunched = userStateOrDefault.isNotLaunched();
                    commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PackageManagerService.this.lambda$setPackageStoppedState$63(i, z, isNotLaunched, str, (PackageStateWrite) obj);
                        }
                    });
                    if (CoreRune.SYSFW_APP_SPEG) {
                        SpegService spegService = this.mSpeg;
                        z2 = spegService != null && spegService.checkSpegState(str);
                    }
                    if (isNotLaunched && !z2) {
                        String str2 = packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName;
                        if (str2 != null) {
                            notifyFirstLaunch(str, str2, i);
                        }
                        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED && i == 0 && str != null && this.mMonetizationUtils.isMonetizedPreloadApp(str)) {
                            Log.i("PackageManager", "Monetized App " + str + " opened for first time");
                            notifyFirstLaunch(str, "MONETIZED_APP_OPENED", i);
                        }
                    }
                    scheduleWritePackageRestrictions(i);
                }
            }
            if (z) {
                return;
            }
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.this.lambda$setPackageStoppedState$64(str, i);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setPackageStoppedState$63(int i, boolean z, boolean z2, String str, PackageStateWrite packageStateWrite) {
        SpegService spegService;
        PackageUserStateWrite userState = packageStateWrite.userState(i);
        userState.setStopped(z);
        if (z2) {
            if (CoreRune.SYSFW_APP_SPEG && (spegService = this.mSpeg) != null && spegService.checkSpegState(str)) {
                return;
            }
            userState.setNotLaunched(false);
        }
    }

    public /* synthetic */ void lambda$setPackageStoppedState$64(String str, int i) {
        AppHibernationManagerInternal appHibernationManagerInternal = (AppHibernationManagerInternal) this.mInjector.getLocalService(AppHibernationManagerInternal.class);
        if (appHibernationManagerInternal == null || !appHibernationManagerInternal.isHibernatingForUser(str, i)) {
            return;
        }
        appHibernationManagerInternal.setHibernatingForUser(str, i, false);
        appHibernationManagerInternal.setHibernatingGlobally(str, false);
    }

    /* loaded from: classes3.dex */
    public class IPackageManagerImpl extends IPackageManagerBase {
        public boolean createEncAppData(String str, int i) {
            return false;
        }

        public boolean removeEncPkgDir(int i, String str) {
            return false;
        }

        public boolean removeEncUserDir(int i) {
            return false;
        }

        public IPackageManagerImpl() {
            super(PackageManagerService.this, PackageManagerService.this.mContext, PackageManagerService.this.mDexOptHelper, PackageManagerService.this.mModuleInfoProvider, PackageManagerService.this.mPreferredActivityHelper, PackageManagerService.this.mResolveIntentHelper, PackageManagerService.this.mDomainVerificationManager, PackageManagerService.this.mDomainVerificationConnection, PackageManagerService.this.mInstallerService, PackageManagerService.this.mPackageProperty, PackageManagerService.this.mResolveComponentName, PackageManagerService.this.mInstantAppResolverSettingsComponent, PackageManagerService.this.mRequiredSdkSandboxPackage, PackageManagerService.this.mServicesExtensionPackageName, PackageManagerService.this.mSharedSystemSharedLibraryPackageName);
        }

        public void checkPackageStartable(String str, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.checkPackageStartable(packageManagerService.snapshotComputer(), str, i);
        }

        public void clearApplicationProfileData(String str) {
            PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can clear all profile data");
            AndroidPackage androidPackage = PackageManagerService.this.snapshotComputer().getPackage(str);
            PackageFreezer freezePackage = PackageManagerService.this.freezePackage(str, -1, "clearApplicationProfileData", 13);
            try {
                synchronized (PackageManagerService.this.mInstallLock) {
                    PackageManagerService.this.mAppDataHelper.clearAppProfilesLIF(androidPackage);
                }
                if (freezePackage != null) {
                    freezePackage.close();
                }
            } catch (Throwable th) {
                if (freezePackage != null) {
                    try {
                        freezePackage.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void clearApplicationUserData(final String str, final IPackageDataObserver iPackageDataObserver, int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_USER_DATA", null);
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "clear application data");
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i) == null) {
                if (iPackageDataObserver != null) {
                    PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            PackageManagerService.IPackageManagerImpl.lambda$clearApplicationUserData$0(iPackageDataObserver, str);
                        }
                    });
                    return;
                }
                return;
            }
            if (PackageManagerService.this.mProtectedPackages.isPackageDataProtected(i, str)) {
                throw new SecurityException("Cannot clear data for a protected package: " + str);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("START CLEAR APPLICATION USER DATA: observer{");
            sb.append(iPackageDataObserver != null ? Integer.valueOf(iPackageDataObserver.hashCode()) : "null");
            sb.append("}\npkg{");
            sb.append(str);
            sb.append("}\nuser{");
            sb.append(i);
            sb.append("}\n");
            Log.d("PackageManager", sb.toString());
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.1
                public final /* synthetic */ IPackageDataObserver val$observer;
                public final /* synthetic */ String val$packageName;
                public final /* synthetic */ int val$userId;

                public AnonymousClass1(final String str2, int i2, final IPackageDataObserver iPackageDataObserver2) {
                    r2 = str2;
                    r3 = i2;
                    r4 = iPackageDataObserver2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    boolean clearApplicationUserDataLIF;
                    PackageManagerService.this.mHandler.removeCallbacks(this);
                    PackageFreezer freezePackage = PackageManagerService.this.freezePackage(r2, -1, "clearApplicationUserData", 10);
                    try {
                        synchronized (PackageManagerService.this.mInstallLock) {
                            PackageManagerService packageManagerService = PackageManagerService.this;
                            clearApplicationUserDataLIF = packageManagerService.clearApplicationUserDataLIF(packageManagerService.snapshotComputer(), r2, r3);
                        }
                        PackageManagerService.this.mInstantAppRegistry.deleteInstantApplicationMetadata(r2, r3);
                        synchronized (PackageManagerService.this.mLock) {
                            if (clearApplicationUserDataLIF) {
                                PackageManagerService.this.resetComponentEnabledSettingsIfNeededLPw(r2, r3);
                            }
                        }
                        if (freezePackage != null) {
                            freezePackage.close();
                        }
                        if (clearApplicationUserDataLIF) {
                            DeviceStorageMonitorInternal deviceStorageMonitorInternal = (DeviceStorageMonitorInternal) LocalServices.getService(DeviceStorageMonitorInternal.class);
                            if (deviceStorageMonitorInternal != null) {
                                deviceStorageMonitorInternal.checkMemory();
                            }
                            if (IPackageManagerImpl.this.checkPermission("android.permission.SUSPEND_APPS", r2, r3) == 0) {
                                Computer snapshotComputer2 = PackageManagerService.this.snapshotComputer();
                                PackageManagerService.this.unsuspendForSuspendingPackage(snapshotComputer2, r2, r3);
                                PackageManagerService.this.removeAllDistractingPackageRestrictions(snapshotComputer2, r3);
                                synchronized (PackageManagerService.this.mLock) {
                                    PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(r3);
                                }
                            }
                        }
                        if (r4 != null) {
                            try {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("result of clearing user data: ");
                                sb2.append(clearApplicationUserDataLIF ? "succeeded" : "failed");
                                sb2.append("{");
                                sb2.append(r4.hashCode());
                                sb2.append("}");
                                Log.d("PackageManager", sb2.toString());
                                r4.onRemoveCompleted(r2, clearApplicationUserDataLIF);
                            } catch (RemoteException unused) {
                                Log.i("PackageManager", "Observer no longer exists.");
                            }
                        }
                    } catch (Throwable th) {
                        if (freezePackage != null) {
                            try {
                                freezePackage.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
            });
        }

        public static /* synthetic */ void lambda$clearApplicationUserData$0(IPackageDataObserver iPackageDataObserver, String str) {
            try {
                iPackageDataObserver.onRemoveCompleted(str, false);
            } catch (RemoteException unused) {
                Log.i("PackageManager", "Observer no longer exists.");
            }
        }

        /* renamed from: com.android.server.pm.PackageManagerService$IPackageManagerImpl$1 */
        /* loaded from: classes3.dex */
        public class AnonymousClass1 implements Runnable {
            public final /* synthetic */ IPackageDataObserver val$observer;
            public final /* synthetic */ String val$packageName;
            public final /* synthetic */ int val$userId;

            public AnonymousClass1(final String str2, int i2, final IPackageDataObserver iPackageDataObserver2) {
                r2 = str2;
                r3 = i2;
                r4 = iPackageDataObserver2;
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean clearApplicationUserDataLIF;
                PackageManagerService.this.mHandler.removeCallbacks(this);
                PackageFreezer freezePackage = PackageManagerService.this.freezePackage(r2, -1, "clearApplicationUserData", 10);
                try {
                    synchronized (PackageManagerService.this.mInstallLock) {
                        PackageManagerService packageManagerService = PackageManagerService.this;
                        clearApplicationUserDataLIF = packageManagerService.clearApplicationUserDataLIF(packageManagerService.snapshotComputer(), r2, r3);
                    }
                    PackageManagerService.this.mInstantAppRegistry.deleteInstantApplicationMetadata(r2, r3);
                    synchronized (PackageManagerService.this.mLock) {
                        if (clearApplicationUserDataLIF) {
                            PackageManagerService.this.resetComponentEnabledSettingsIfNeededLPw(r2, r3);
                        }
                    }
                    if (freezePackage != null) {
                        freezePackage.close();
                    }
                    if (clearApplicationUserDataLIF) {
                        DeviceStorageMonitorInternal deviceStorageMonitorInternal = (DeviceStorageMonitorInternal) LocalServices.getService(DeviceStorageMonitorInternal.class);
                        if (deviceStorageMonitorInternal != null) {
                            deviceStorageMonitorInternal.checkMemory();
                        }
                        if (IPackageManagerImpl.this.checkPermission("android.permission.SUSPEND_APPS", r2, r3) == 0) {
                            Computer snapshotComputer2 = PackageManagerService.this.snapshotComputer();
                            PackageManagerService.this.unsuspendForSuspendingPackage(snapshotComputer2, r2, r3);
                            PackageManagerService.this.removeAllDistractingPackageRestrictions(snapshotComputer2, r3);
                            synchronized (PackageManagerService.this.mLock) {
                                PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(r3);
                            }
                        }
                    }
                    if (r4 != null) {
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("result of clearing user data: ");
                            sb2.append(clearApplicationUserDataLIF ? "succeeded" : "failed");
                            sb2.append("{");
                            sb2.append(r4.hashCode());
                            sb2.append("}");
                            Log.d("PackageManager", sb2.toString());
                            r4.onRemoveCompleted(r2, clearApplicationUserDataLIF);
                        } catch (RemoteException unused) {
                            Log.i("PackageManager", "Observer no longer exists.");
                        }
                    }
                } catch (Throwable th) {
                    if (freezePackage != null) {
                        try {
                            freezePackage.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        public void clearCrossProfileIntentFilters(int i, String str) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
            int callingUid = Binder.getCallingUid();
            PackageManagerService.this.enforceOwnerRights(PackageManagerService.this.snapshotComputer(), str, callingUid);
            PackageManagerServiceUtils.enforceShellRestriction(PackageManagerService.this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
            PackageManagerService.this.mInjector.getCrossProfileIntentFilterHelper().clearCrossProfileIntentFilters(i, str, null);
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
            int callingUid = Binder.getCallingUid();
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.enforceOwnerRights(packageManagerService.snapshotComputer(), str, callingUid);
            boolean z = false;
            PackageManagerService.this.mUserManager.enforceCrossProfileIntentFilterAccess(i, i2, callingUid, false);
            PackageManagerServiceUtils.enforceShellRestriction(PackageManagerService.this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
            synchronized (PackageManagerService.this.mLock) {
                CrossProfileIntentResolver editCrossProfileIntentResolverLPw = PackageManagerService.this.mSettings.editCrossProfileIntentResolverLPw(i);
                Iterator it = new ArraySet(editCrossProfileIntentResolverLPw.filterSet()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) it.next();
                    if (IntentFilter.filterEquals(crossProfileIntentFilter.mFilter, intentFilter) && crossProfileIntentFilter.getOwnerPackage().equals(str) && crossProfileIntentFilter.getTargetUserId() == i2 && crossProfileIntentFilter.getFlags() == i3) {
                        editCrossProfileIntentResolverLPw.removeFilter((WatchedIntentFilter) crossProfileIntentFilter);
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                PackageManagerService.this.scheduleWritePackageRestrictions(i);
            }
            return z;
        }

        public final void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) {
            deleteApplicationCacheFilesAsUser(str, UserHandle.getCallingUserId(), iPackageDataObserver);
        }

        public void deleteApplicationCacheFilesAsUser(final String str, final int i, final IPackageDataObserver iPackageDataObserver) {
            final int callingUid = Binder.getCallingUid();
            if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES") != 0) {
                if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_CACHE_FILES") == 0) {
                    Slog.w("PackageManager", "Calling uid " + callingUid + " does not have android.permission.INTERNAL_DELETE_CACHE_FILES, silently ignoring");
                    return;
                }
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES", null);
            }
            PackageManagerService.this.snapshotComputer().enforceCrossUserPermission(callingUid, i, true, false, "delete application cache files");
            final int checkCallingOrSelfPermission = PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS");
            IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
            if (asInterface != null) {
                try {
                    if (asInterface.isApplicationClearCacheDisabled(str, i, true)) {
                        Log.w("PackageManager", "Cache cannot be cleared for this app due to EDM policy. packageName = " + str);
                        if (iPackageDataObserver != null) {
                            try {
                                Log.d("PackageManager", "result of delete cache files: completed{" + iPackageDataObserver.hashCode() + "}");
                                iPackageDataObserver.onRemoveCompleted(str, false);
                                return;
                            } catch (RemoteException unused) {
                                Log.i("PackageManager", "Observer no longer exists.");
                                return;
                            }
                        }
                        return;
                    }
                } catch (Exception unused2) {
                }
            }
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$deleteApplicationCacheFilesAsUser$1(str, callingUid, checkCallingOrSelfPermission, i, iPackageDataObserver);
                }
            });
        }

        public /* synthetic */ void lambda$deleteApplicationCacheFilesAsUser$1(String str, int i, int i2, int i3, IPackageDataObserver iPackageDataObserver) {
            PackageStateInternal packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(UserHandle.getUserId(i)).isInstantApp() || i2 == 0) {
                synchronized (PackageManagerService.this.mInstallLock) {
                    AndroidPackage androidPackage = PackageManagerService.this.snapshotComputer().getPackage(str);
                    PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i3, 23);
                    PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i3, 39);
                }
            }
            if (iPackageDataObserver != null) {
                try {
                    iPackageDataObserver.onRemoveCompleted(str, true);
                } catch (RemoteException unused) {
                    Log.i("PackageManager", "Observer no longer exists.");
                }
            }
        }

        public void enterSafeMode() {
            PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request entering safe mode");
            if (PackageManagerService.this.mSystemReady) {
                return;
            }
            PackageManagerService.this.mSafeMode = true;
        }

        public void extendVerificationTimeout(final int i, final int i2, final long j) {
            if (i >= 0) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can extend verification timeouts");
            }
            final int callingUid = Binder.getCallingUid();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$extendVerificationTimeout$2(i, callingUid, i2, j);
                }
            });
        }

        public /* synthetic */ void lambda$extendVerificationTimeout$2(int i, int i2, int i3, long j) {
            if (i < 0) {
                i = -i;
            }
            PackageVerificationState packageVerificationState = (PackageVerificationState) PackageManagerService.this.mPendingVerification.get(i);
            if (packageVerificationState == null || !packageVerificationState.extendTimeout(i2)) {
                return;
            }
            PackageVerificationResponse packageVerificationResponse = new PackageVerificationResponse(i3, i2);
            if (j > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                j = 3600000;
            }
            if (j < 0) {
                j = 0;
            }
            Message obtainMessage = PackageManagerService.this.mHandler.obtainMessage(15);
            obtainMessage.arg1 = i;
            obtainMessage.obj = packageVerificationResponse;
            PackageManagerService.this.mHandler.sendMessageDelayed(obtainMessage, j);
        }

        public void flushPackageRestrictionsAsUser(int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) == null && PackageManagerService.this.mUserManager.exists(i)) {
                snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "flushPackageRestrictions");
                synchronized (PackageManagerService.this.mLock) {
                    PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                }
            }
        }

        public void freeStorage(final String str, final long j, final int i, final IntentSender intentSender) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", "PackageManager");
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$freeStorage$3(str, j, i, intentSender);
                }
            });
        }

        public /* synthetic */ void lambda$freeStorage$3(String str, long j, int i, IntentSender intentSender) {
            boolean z;
            try {
                PackageManagerService.this.freeStorage(str, j, i);
                z = true;
            } catch (IOException e) {
                Slog.w("PackageManager", e);
                z = false;
            }
            if (intentSender != null) {
                try {
                    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                    makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                    intentSender.sendIntent(null, z ? 1 : 0, null, null, null, null, makeBasic.toBundle());
                } catch (IntentSender.SendIntentException e2) {
                    Slog.w("PackageManager", e2);
                }
            }
        }

        public void freeStorageAndNotify(final String str, final long j, final int i, final IPackageDataObserver iPackageDataObserver) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", null);
            StringBuilder sb = new StringBuilder();
            sb.append("START FREE STORAGE AND NOTIFY: observer{");
            sb.append(iPackageDataObserver != null ? Integer.valueOf(iPackageDataObserver.hashCode()) : "null");
            sb.append("}\nfreeStorageSize{");
            sb.append(j);
            sb.append("}");
            Log.d("PackageManager", sb.toString());
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$freeStorageAndNotify$4(str, j, i, iPackageDataObserver);
                }
            });
        }

        public /* synthetic */ void lambda$freeStorageAndNotify$4(String str, long j, int i, IPackageDataObserver iPackageDataObserver) {
            boolean z;
            try {
                PackageManagerService.this.freeStorage(str, j, i);
                z = true;
            } catch (IOException e) {
                Slog.w("PackageManager", e);
                z = false;
            }
            if (iPackageDataObserver != null) {
                try {
                    Log.d("PackageManager", "result of freeStorageAndNotify: " + z + "{" + iPackageDataObserver.hashCode() + "}");
                    iPackageDataObserver.onRemoveCompleted((String) null, z);
                } catch (RemoteException e2) {
                    Slog.w("PackageManager", e2);
                }
            }
        }

        public ChangedPackages getChangedPackages(int i, int i2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null || !PackageManagerService.this.mUserManager.exists(i2)) {
                return null;
            }
            snapshotComputer.enforceCrossUserPermission(callingUid, i2, false, false, "getChangedPackages");
            ChangedPackages changedPackages = PackageManagerService.this.mChangedPackagesTracker.getChangedPackages(i, i2);
            if (changedPackages != null) {
                List<String> packageNames = changedPackages.getPackageNames();
                for (int size = packageNames.size() - 1; size >= 0; size--) {
                    if (snapshotComputer.shouldFilterApplication(snapshotComputer.getPackageStateInternal(packageNames.get(size)), callingUid, i2)) {
                        packageNames.remove(size);
                    }
                }
            }
            return changedPackages;
        }

        public byte[] getDomainVerificationBackup(int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call getDomainVerificationBackup()");
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    packageManagerService.mDomainVerificationManager.writeSettings(packageManagerService.snapshotComputer(), resolveSerializer, true, i);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } catch (Exception unused) {
                return null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public IBinder getHoldLockToken() {
            if (!Build.IS_DEBUGGABLE) {
                throw new SecurityException("getHoldLockToken requires a debuggable build");
            }
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.INJECT_EVENTS", "getHoldLockToken requires INJECT_EVENTS permission");
            Binder binder = new Binder();
            binder.attachInterface(this, "holdLock:" + Binder.getCallingUid());
            return binder;
        }

        public String getInstantAppAndroidId(String str, int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getInstantAppAndroidId");
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "getInstantAppAndroidId");
            if (snapshotComputer.isInstantApp(str, i)) {
                return PackageManagerService.this.mInstantAppRegistry.getInstantAppAndroidId(str, i);
            }
            return null;
        }

        public byte[] getInstantAppCookie(String str, int i) {
            PackageStateInternal packageStateInternal;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "getInstantAppCookie");
            if (!snapshotComputer.isCallerSameApp(str, Binder.getCallingUid()) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || packageStateInternal.getPkg() == null) {
                return null;
            }
            return PackageManagerService.this.mInstantAppRegistry.getInstantAppCookie(packageStateInternal.getPkg(), i);
        }

        public Bitmap getInstantAppIcon(String str, int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(Binder.getCallingUid(), i)) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getInstantAppIcon");
            }
            snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "getInstantAppIcon");
            return PackageManagerService.this.mInstantAppRegistry.getInstantAppIcon(str, i);
        }

        public ParceledListSlice getInstantApps(int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(Binder.getCallingUid(), i)) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getEphemeralApplications");
            }
            snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "getEphemeralApplications");
            List instantApps = PackageManagerService.this.mInstantAppRegistry.getInstantApps(snapshotComputer, i);
            if (instantApps != null) {
                return new ParceledListSlice(instantApps);
            }
            return null;
        }

        public ResolveInfo getLastChosenActivity(Intent intent, String str, int i) {
            return PackageManagerService.this.mPreferredActivityHelper.getLastChosenActivity(PackageManagerService.this.snapshotComputer(), intent, str, i);
        }

        public IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) {
            return PackageManagerService.this.mResolveIntentHelper.getLaunchIntentSenderForPackage(PackageManagerService.this.snapshotComputer(), str, str2, str3, i);
        }

        public List getMimeGroup(String str, String str2) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, Binder.getCallingUid());
            return PackageManagerService.this.getMimeGroupInternal(snapshotComputer, str, str2);
        }

        public int getMoveStatus(int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS", null);
            return PackageManagerService.this.mMoveCallbacks.mLastStatus.get(i);
        }

        public ParcelFileDescriptor getAppMetadataFd(String str, int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.GET_APP_METADATA", "getAppMetadataFd");
            PackageStateInternal packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                throw new ParcelableException(new PackageManager.NameNotFoundException(str));
            }
            String appMetadataFilePath = packageStateForInstalledAndFiltered.getAppMetadataFilePath();
            if (appMetadataFilePath != null) {
                try {
                    return ParcelFileDescriptor.open(new File(appMetadataFilePath), 268435456);
                } catch (FileNotFoundException unused) {
                }
            }
            return null;
        }

        public String getPermissionControllerPackageName() {
            int callingUid = Binder.getCallingUid();
            if (PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(PackageManagerService.this.mRequiredPermissionControllerPackage, callingUid, UserHandle.getUserId(callingUid)) != null) {
                return PackageManagerService.this.mRequiredPermissionControllerPackage;
            }
            throw new IllegalStateException("PermissionController is not found");
        }

        public int getRuntimePermissionsVersion(int i) {
            Preconditions.checkArgumentNonnegative(i);
            PackageManagerService.this.enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions("getRuntimePermissionVersion");
            return PackageManagerService.this.mSettings.getDefaultRuntimePermissionsVersion(i);
        }

        public String getSplashScreenTheme(String str, int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "getSplashScreenTheme");
            PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getSplashScreenTheme();
        }

        public Bundle getSuspendedPackageAppExtras(String str, int i) {
            int callingUid = Binder.getCallingUid();
            Computer snapshot = snapshot();
            if (snapshot.getPackageUid(str, 0L, i) != callingUid) {
                throw new SecurityException("Calling package " + str + " does not belong to calling uid " + callingUid);
            }
            return PackageManagerService.this.mSuspendPackageHelper.getSuspendedPackageAppExtras(snapshot, str, i, callingUid);
        }

        public ParceledListSlice getSystemAvailableFeatures() {
            ArrayList arrayList;
            synchronized (PackageManagerService.this.mAvailableFeatures) {
                arrayList = new ArrayList(PackageManagerService.this.mAvailableFeatures.size() + 1);
                arrayList.addAll(PackageManagerService.this.mAvailableFeatures.values());
            }
            FeatureInfo featureInfo = new FeatureInfo();
            featureInfo.reqGlEsVersion = SystemProperties.getInt("ro.opengles.version", 0);
            arrayList.add(featureInfo);
            return new ParceledListSlice(arrayList);
        }

        public List getInitialNonStoppedSystemPackages() {
            return PackageManagerService.this.mInitialNonStoppedSystemPackages != null ? new ArrayList(PackageManagerService.this.mInitialNonStoppedSystemPackages) : new ArrayList();
        }

        public String[] getUnsuspendablePackagesForUser(String[] strArr, int i) {
            Objects.requireNonNull(strArr, "packageNames cannot be null");
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "getUnsuspendablePackagesForUser");
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getUserId(callingUid) != i) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Calling uid " + callingUid + " cannot query getUnsuspendablePackagesForUser for user " + i);
            }
            return PackageManagerService.this.mSuspendPackageHelper.getUnsuspendablePackagesForUser(PackageManagerService.this.snapshotComputer(), strArr, i, callingUid);
        }

        public VerifierDeviceIdentity getVerifierDeviceIdentity() {
            VerifierDeviceIdentity verifierDeviceIdentityLPw;
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can read the verifier device identity");
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService packageManagerService = PackageManagerService.this;
                verifierDeviceIdentityLPw = packageManagerService.mSettings.getVerifierDeviceIdentityLPw(packageManagerService.mLiveComputer);
            }
            return verifierDeviceIdentityLPw;
        }

        public void makeProviderVisible(int i, String str) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int userId = UserHandle.getUserId(i);
            ProviderInfo grantImplicitAccessProviderInfo = snapshotComputer.getGrantImplicitAccessProviderInfo(i, str);
            if (grantImplicitAccessProviderInfo == null) {
                return;
            }
            PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, UserHandle.getAppId(i), grantImplicitAccessProviderInfo.applicationInfo.uid, false, false);
        }

        public void makeUidVisible(int i, int i2) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MAKE_UID_VISIBLE", "makeUidVisible");
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(i);
            int userId2 = UserHandle.getUserId(i2);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, userId, false, false, "makeUidVisible");
            snapshotComputer.enforceCrossUserPermission(callingUid, userId2, false, false, "makeUidVisible");
            snapshotComputer.enforceCrossUserPermission(i, userId2, false, false, "makeUidVisible");
            PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, UserHandle.getAppId(i), i2, false, false);
        }

        public void holdLock(IBinder iBinder, int i) {
            PackageManagerService.this.mTestUtilityService.verifyHoldLockToken(iBinder);
            synchronized (PackageManagerService.this.mLock) {
                SystemClock.sleep(i);
            }
        }

        public int installExistingPackageAsUser(String str, int i, int i2, int i3, List list) {
            return ((Integer) PackageManagerService.this.mInstallPackageHelper.installExistingPackageAsUser(str, i, i2, i3, list, null).first).intValue();
        }

        public boolean isAutoRevokeWhitelisted(String str) {
            return ((AppOpsManager) PackageManagerService.this.mInjector.getSystemService(AppOpsManager.class)).checkOpNoThrow(97, Binder.getCallingUid(), str) == 1;
        }

        public boolean isPackageStateProtected(String str, int i) {
            int callingUid = Binder.getCallingUid();
            int appId = UserHandle.getAppId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, true, "isPackageStateProtected");
            if (!PackageManagerServiceUtils.isSystemOrRoot(appId) && snapshotComputer.checkUidPermission("android.permission.MANAGE_DEVICE_ADMINS", callingUid) != 0) {
                throw new SecurityException("Caller must have the android.permission.MANAGE_DEVICE_ADMINS permission.");
            }
            return PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str);
        }

        public boolean isProtectedBroadcast(String str) {
            boolean contains;
            if (str != null && (str.startsWith("android.net.netmon.lingerExpired") || str.startsWith("com.android.server.sip.SipWakeupTimer") || str.startsWith("com.android.internal.telephony.data-reconnect") || str.startsWith("android.net.netmon.launchCaptivePortalApp"))) {
                return true;
            }
            synchronized (PackageManagerService.this.mProtectedBroadcasts) {
                contains = PackageManagerService.this.mProtectedBroadcasts.contains(str);
            }
            return contains;
        }

        public void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) {
            if (PackageManagerService.this.snapshotComputer().getInstantAppPackageName(Binder.getCallingUid()) == null && SecurityLog.isLoggingEnabled()) {
                PackageManagerService packageManagerService = PackageManagerService.this;
                packageManagerService.mProcessLoggingHandler.logAppProcessStart(packageManagerService.mContext, (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class), str4, str, str2, i, str3, i2);
            }
        }

        public int movePackage(final String str, final String str2) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MOVE_PACKAGE", null);
            Log.d("PackageManager", "START MOVE PACKAGE: pkg{" + str + "}\nvolumeUuid{" + str2 + "}\n");
            final int callingUid = Binder.getCallingUid();
            final UserHandle userHandle = new UserHandle(UserHandle.getUserId(callingUid));
            final int andIncrement = PackageManagerService.this.mNextMoveId.getAndIncrement();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$movePackage$5(str, str2, andIncrement, callingUid, userHandle);
                }
            });
            return andIncrement;
        }

        public /* synthetic */ void lambda$movePackage$5(String str, String str2, int i, int i2, UserHandle userHandle) {
            try {
                new MovePackageHelper(PackageManagerService.this).movePackageInternal(str, str2, i, i2, userHandle);
            } catch (PackageManagerException e) {
                Slog.w("PackageManager", "Failed to move " + str, e);
                PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(i, e.error);
            }
        }

        public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) {
            return new MovePackageHelper(PackageManagerService.this).movePackageToSd(str, str2, iMemorySaverPackageMoveObserver);
        }

        public int movePrimaryStorage(String str) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MOVE_PACKAGE", null);
            int andIncrement = PackageManagerService.this.mNextMoveId.getAndIncrement();
            Bundle bundle = new Bundle();
            bundle.putString("android.os.storage.extra.FS_UUID", str);
            PackageManagerService.this.mMoveCallbacks.notifyCreated(andIncrement, bundle);
            ((StorageManager) PackageManagerService.this.mInjector.getSystemService(StorageManager.class)).setPrimaryStorageUuid(str, new IPackageMoveObserver.Stub() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.2
                public final /* synthetic */ int val$realMoveId;

                public void onCreated(int i, Bundle bundle2) {
                }

                public AnonymousClass2(int andIncrement2) {
                    r2 = andIncrement2;
                }

                public void onStatusChanged(int i, int i2, long j) {
                    PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(r2, i2, j);
                }
            });
            return andIncrement2;
        }

        /* renamed from: com.android.server.pm.PackageManagerService$IPackageManagerImpl$2 */
        /* loaded from: classes3.dex */
        public class AnonymousClass2 extends IPackageMoveObserver.Stub {
            public final /* synthetic */ int val$realMoveId;

            public void onCreated(int i, Bundle bundle2) {
            }

            public AnonymousClass2(int andIncrement2) {
                r2 = andIncrement2;
            }

            public void onStatusChanged(int i, int i2, long j) {
                PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(r2, i2, j);
            }
        }

        public void notifyDexLoad(String str, Map map, String str2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshot = snapshot();
            if (!PackageManagerServiceUtils.isSystemOrRoot() && !snapshot.isCallerSameApp(str, callingUid, true)) {
                Slog.w("PackageManager", TextUtils.formatSimple("Invalid dex load report. loadingPackageName=%s, uid=%d", new Object[]{str, Integer.valueOf(callingUid)}));
                return;
            }
            UserHandle callingUserHandle = Binder.getCallingUserHandle();
            int identifier = callingUserHandle.getIdentifier();
            DexUseManagerLocal dexUseManagerLocal = DexOptHelper.getDexUseManagerLocal();
            if (dexUseManagerLocal != null) {
                PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class)).withFilteredSnapshot(callingUid, callingUserHandle);
                if (str2 != null) {
                    try {
                        PackageState packageState = withFilteredSnapshot.getPackageState(str);
                        if (packageState != null) {
                            String primaryCpuAbi = packageState.getPrimaryCpuAbi();
                            if (primaryCpuAbi == null) {
                                primaryCpuAbi = Build.SUPPORTED_ABIS[0];
                            }
                            String dexCodeInstructionSet = InstructionSets.getDexCodeInstructionSet(VMRuntime.getInstructionSet(primaryCpuAbi));
                            if (!str2.equals(dexCodeInstructionSet)) {
                                Log.wtf("PackageManager", "Invalid loaderIsa in notifyDexLoad call from " + str + ", uid " + callingUid + ": expected " + dexCodeInstructionSet + ", got " + str2);
                                withFilteredSnapshot.close();
                                return;
                            }
                        }
                    } catch (Throwable th) {
                        if (withFilteredSnapshot != null) {
                            try {
                                withFilteredSnapshot.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                dexUseManagerLocal.notifyDexContainersLoaded(withFilteredSnapshot, str, map);
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                    return;
                }
                return;
            }
            ApplicationInfo applicationInfo = snapshot.getApplicationInfo(str, 0L, identifier);
            if (applicationInfo == null) {
                Slog.w("PackageManager", "Loading a package that does not exist for the calling user. package=" + str + ", user=" + identifier);
                return;
            }
            PackageManagerService.this.mDexManager.notifyDexLoad(applicationInfo, map, str2, identifier, Process.isIsolated(callingUid));
        }

        public void notifyPackageUse(String str, int i) {
            boolean z;
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null) {
                z = snapshotComputer.isCallerSameApp(str, callingUid);
            } else {
                z = !snapshotComputer.isInstantAppInternal(str, userId, 1000);
            }
            if (z) {
                PackageManagerService.this.notifyPackageUseInternal(str, i);
            }
        }

        public void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Override label should be a valid String");
            }
            PackageManagerService.this.updateComponentLabelIcon(componentName, str, Integer.valueOf(i), i2);
        }

        public int performDexOptForADCP(String str, boolean z) {
            int i = z ? 2 : 0;
            Slog.e("dex2oat", "For test new API");
            return PackageManagerService.this.mDexOptHelper.performDexOptWithStatus(new DexoptOptions(str, 23, "speed-profile", null, i | 1 | 4));
        }

        public ParceledListSlice queryProperty(String str, int i) {
            Objects.requireNonNull(str);
            final int callingUid = Binder.getCallingUid();
            final int callingUserId = UserHandle.getCallingUserId();
            final Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            List queryProperty = PackageManagerService.this.mPackageProperty.queryProperty(str, i, new Predicate() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$queryProperty$6;
                    lambda$queryProperty$6 = PackageManagerService.IPackageManagerImpl.lambda$queryProperty$6(Computer.this, callingUid, callingUserId, (String) obj);
                    return lambda$queryProperty$6;
                }
            });
            if (queryProperty == null) {
                return ParceledListSlice.emptyList();
            }
            return new ParceledListSlice(queryProperty);
        }

        public static /* synthetic */ boolean lambda$queryProperty$6(Computer computer, int i, int i2, String str) {
            return computer.getPackageStateForInstalledAndFiltered(str, i, i2) == null;
        }

        public void registerDexModule(String str, final String str2, boolean z, final IDexModuleRegisterCallback iDexModuleRegisterCallback) {
            Slog.i("PackageManager", "Ignored unsupported registerDexModule call for " + str2 + " in " + str);
            final DexManager.RegisterDexModuleResult registerDexModuleResult = new DexManager.RegisterDexModuleResult(false, "registerDexModule call not supported since Android U");
            if (iDexModuleRegisterCallback != null) {
                PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        PackageManagerService.IPackageManagerImpl.lambda$registerDexModule$7(iDexModuleRegisterCallback, str2, registerDexModuleResult);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$registerDexModule$7(IDexModuleRegisterCallback iDexModuleRegisterCallback, String str, DexManager.RegisterDexModuleResult registerDexModuleResult) {
            try {
                iDexModuleRegisterCallback.onDexModuleRegistered(str, registerDexModuleResult.success, registerDexModuleResult.message);
            } catch (RemoteException e) {
                Slog.w("PackageManager", "Failed to callback after module registration " + str, e);
            }
        }

        public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS", null);
            PackageManagerService.this.mMoveCallbacks.register(iPackageMoveObserver);
        }

        public void restoreDomainVerification(byte[] bArr, int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call restorePreferredActivities()");
            }
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(byteArrayInputStream);
                PackageManagerService packageManagerService = PackageManagerService.this;
                packageManagerService.mDomainVerificationManager.restoreSettings(packageManagerService.snapshotComputer(), resolvePullParser);
                byteArrayInputStream.close();
            } catch (Exception unused) {
            }
        }

        public void restoreLabelAndIcon(ComponentName componentName, int i) {
            PackageManagerService.this.updateComponentLabelIcon(componentName, null, null, i);
        }

        public void sendDeviceCustomizationReadyBroadcast() {
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.SEND_DEVICE_CUSTOMIZATION_READY", "sendDeviceCustomizationReadyBroadcast");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                BroadcastHelper.sendDeviceCustomizationReadyBroadcast();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setApplicationCategoryHint(final String str, final int i, final String str2) {
            FunctionalUtils.ThrowingBiFunction throwingBiFunction = new FunctionalUtils.ThrowingBiFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda18
                public final Object applyOrThrow(Object obj, Object obj2) {
                    PackageStateMutator.Result lambda$setApplicationCategoryHint$9;
                    lambda$setApplicationCategoryHint$9 = PackageManagerService.IPackageManagerImpl.this.lambda$setApplicationCategoryHint$9(str2, str, i, (PackageStateMutator.InitialState) obj, (Computer) obj2);
                    return lambda$setApplicationCategoryHint$9;
                }
            };
            PackageStateMutator.Result result = (PackageStateMutator.Result) throwingBiFunction.apply(PackageManagerService.this.recordInitialState(), PackageManagerService.this.snapshotComputer());
            if (result != null && result.isStateChanged() && !result.isSpecificPackageNull()) {
                synchronized (PackageManagerService.this.mPackageStateWriteLock) {
                    result = (PackageStateMutator.Result) throwingBiFunction.apply(PackageManagerService.this.recordInitialState(), PackageManagerService.this.snapshotComputer());
                }
            }
            if (result == null || !result.isCommitted()) {
                return;
            }
            PackageManagerService.this.scheduleWriteSettings();
        }

        public /* synthetic */ PackageStateMutator.Result lambda$setApplicationCategoryHint$9(String str, String str2, final int i, PackageStateMutator.InitialState initialState, Computer computer) {
            if (computer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                throw new SecurityException("Instant applications don't have access to this method");
            }
            ((AppOpsManager) PackageManagerService.this.mInjector.getSystemService(AppOpsManager.class)).checkPackage(Binder.getCallingUid(), str);
            PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str2, Binder.getCallingUid(), UserHandle.getCallingUserId());
            if (packageStateForInstalledAndFiltered == null) {
                throw new IllegalArgumentException("Unknown target package " + str2);
            }
            if (!isAllowedToSetCategoryHint(packageStateForInstalledAndFiltered, str) && !Objects.equals(str, packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName)) {
                throw new IllegalArgumentException("Calling package " + str + " is not installer for " + str2);
            }
            PmLog.logDebugInfoAndLogcat("setApplicationCategoryHint, pkg: " + str2 + ", oldCategory: " + packageStateForInstalledAndFiltered.getCategoryOverride() + ", newCategory: " + i + ", manifestCategory: " + (packageStateForInstalledAndFiltered.getPkg() != null ? packageStateForInstalledAndFiltered.getPkg().getCategory() : -1) + ", caller: " + str + "/" + Binder.getCallingUid());
            if (packageStateForInstalledAndFiltered.getCategoryOverride() != i) {
                return PackageManagerService.this.commitPackageStateMutation(initialState, str2, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda21
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((PackageStateWrite) obj).setCategoryOverride(i);
                    }
                });
            }
            return null;
        }

        public final boolean isAllowedToSetCategoryHint(PackageStateInternal packageStateInternal, String str) {
            return (Binder.getCallingUid() == 1000 && Binder.getCallingPid() == Process.myPid()) || ("com.sec.android.easyMover".equals(str) && "com.sec.android.easyMover".equals(packageStateInternal.getInstallSource().mInitiatingPackageName) && ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPlatformSigned(str));
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void setApplicationEnabledSetting(java.lang.String r7, int r8, int r9, int r10, java.lang.String r11) {
            /*
                r6 = this;
                com.android.server.pm.PackageManagerService r0 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.UserManagerService r0 = r0.mUserManager
                boolean r0 = r0.exists(r10)
                if (r0 != 0) goto Lb
                return
            Lb:
                if (r11 != 0) goto L15
                int r11 = android.os.Binder.getCallingUid()
                java.lang.String r11 = java.lang.Integer.toString(r11)
            L15:
                java.lang.String r0 = "auto_disabler"
                boolean r1 = r0.equals(r11)
                r2 = 0
                r3 = 1
                if (r1 != 0) goto L4a
                java.lang.String r1 = "com.sec.android.emergencymode.service"
                boolean r1 = r1.equals(r11)
                if (r1 != 0) goto L4a
                com.android.server.pm.PackageManagerService r1 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.Computer r1 = r1.snapshotComputer()
                com.android.server.pm.pkg.PackageStateInternal r1 = r1.getPackageStateInternal(r7)
                if (r1 == 0) goto L4a
                com.android.server.pm.pkg.PackageUserStateInternal r1 = r1.getUserStateOrDefault(r10)
                int r4 = r1.getEnabledState()
                r5 = 4
                if (r4 != r5) goto L4a
                java.lang.String r1 = r1.getLastDisableAppCaller()
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L4a
                r0 = r3
                goto L4b
            L4a:
                r0 = r2
            L4b:
                com.android.server.pm.PackageManagerService r1 = com.android.server.pm.PackageManagerService.this
                android.content.pm.PackageManager$ComponentEnabledSetting r4 = new android.content.pm.PackageManager$ComponentEnabledSetting
                r4.<init>(r7, r8, r9)
                java.util.List r9 = java.util.List.of(r4)
                com.android.server.pm.PackageManagerService.m9522$$Nest$msetEnabledSettings(r1, r9, r10, r11)
                if (r0 == 0) goto L85
                java.lang.String r9 = "com.google.android.partnersetup"
                boolean r9 = r9.equals(r11)
                if (r9 == 0) goto L68
                if (r8 == 0) goto L67
                if (r8 != r3) goto L68
            L67:
                r2 = r3
            L68:
                com.android.server.pm.PackageManagerService r9 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.Computer r9 = r9.snapshotComputer()
                com.android.server.pm.pkg.PackageStateInternal r9 = r9.getPackageStateInternal(r7)
                if (r9 == 0) goto L85
                com.android.server.pm.pkg.PackageUserStateInternal r9 = r9.getUserStateOrDefault(r10)
                int r9 = r9.getEnabledState()
                if (r9 != r8) goto L85
                com.android.server.pm.PackageManagerService r6 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.AutoDisableHandler r6 = r6.mAutoDisableHandler
                r6.sendAutoDisabledAppStateChanged(r7, r10, r2)
            L85:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.setApplicationEnabledSetting(java.lang.String, int, int, int, java.lang.String):void");
        }

        public boolean setApplicationHiddenSettingAsUser(String str, final boolean z, final int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USERS", null);
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "setApplicationHiddenSetting for user " + i);
            PmLog.logDebugInfoAndLogcat("setApplicationHiddenSettingAsUser, packageName: " + str + ", userId: " + i + ", hidden: " + z + ", callingUid: " + callingUid + ", callingPackage: " + PmServerUtils.getProcessNameForPid(Binder.getCallingPid()));
            if (z && PackageManagerService.this.isPackageDeviceAdmin(str, i)) {
                Slog.w("PackageManager", "Not hiding package " + str + ": has active device admin");
                return false;
            }
            if ("android".equals(str) || PackageManagerService.this.isRequiredSystemPackage(snapshotComputer, str, i)) {
                Slog.w("PackageManager", "Cannot hide package: " + str);
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    return false;
                }
                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                if (userStateOrDefault.isHidden() != z && userStateOrDefault.isInstalled() && !snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i)) {
                    AndroidPackageInternal pkg = packageStateInternal.getPkg();
                    if (pkg != null) {
                        if (pkg.getSdkLibraryName() != null) {
                            Slog.w("PackageManager", "Cannot hide package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                            return false;
                        }
                        if (pkg.getStaticSharedLibraryName() != null) {
                            Slog.w("PackageManager", "Cannot hide package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                            return false;
                        }
                    }
                    if (z && !UserHandle.isSameApp(callingUid, packageStateInternal.getAppId()) && PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str)) {
                        Slog.w("PackageManager", "Not hiding protected package: " + str);
                        return false;
                    }
                    PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PackageManagerService.IPackageManagerImpl.lambda$setApplicationHiddenSettingAsUser$10(i, z, (PackageStateWrite) obj);
                        }
                    });
                    Computer snapshotComputer2 = PackageManagerService.this.snapshotComputer();
                    PackageStateInternal packageStateInternal2 = snapshotComputer2.getPackageStateInternal(str);
                    if (z) {
                        PackageManagerService.this.killApplication(str, packageStateInternal2.getAppId(), i, "hiding pkg", 13);
                        PackageManagerService.this.sendApplicationHiddenForUser(str, packageStateInternal2, i);
                    } else if (PackageManagerService.this.isLocaleOptimizedPackage(str, i)) {
                        PackageManagerService.this.updateLocaleOverlaysForPackageEnabled(str, i);
                    } else {
                        PackageManagerService.this.sendPackageAddedForUser(snapshotComputer2, str, packageStateInternal2, i, 0);
                    }
                    PackageManagerService.this.scheduleWritePackageRestrictions(i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public static /* synthetic */ void lambda$setApplicationHiddenSettingAsUser$10(int i, boolean z, PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setHidden(z);
        }

        public boolean setBlockUninstallForUser(String str, boolean z, int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
            PackageStateInternal packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
                AndroidPackageInternal pkg = packageStateInternal.getPkg();
                if (pkg.getSdkLibraryName() != null) {
                    Slog.w("PackageManager", "Cannot block uninstall of package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                    return false;
                }
                if (pkg.getStaticSharedLibraryName() != null) {
                    Slog.w("PackageManager", "Cannot block uninstall of package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                    return false;
                }
            }
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.mSettings.setBlockUninstallLPw(i, str, z);
            }
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
            return true;
        }

        public void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) {
            if (PackageManagerService.this.mUserManager.exists(i3)) {
                if (str == null) {
                    str = Integer.toString(Binder.getCallingUid());
                }
                PackageManagerService.this.setEnabledSettings(List.of(new PackageManager.ComponentEnabledSetting(componentName, i, i2)), i3, str);
            }
        }

        public void setComponentEnabledSettings(List list, int i, String str) {
            if (PackageManagerService.this.mUserManager.exists(i)) {
                if (list == null || list.isEmpty()) {
                    throw new IllegalArgumentException("The list of enabled settings is empty");
                }
                if (str == null) {
                    str = Integer.toString(Binder.getCallingUid());
                }
                PackageManagerService.this.setEnabledSettings(list, i, str);
            }
        }

        public String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.enforceCanSetDistractingPackageRestrictionsAsUser(callingUid, i2, "setDistractingPackageRestrictionsAsUser");
            Objects.requireNonNull(strArr, "packageNames cannot be null");
            return PackageManagerService.this.mDistractingPackageHelper.setDistractingPackageRestrictionsAsUser(snapshotComputer, strArr, i, i2, callingUid);
        }

        public void setHarmfulAppWarning(String str, final CharSequence charSequence, final int i) {
            int callingUid = Binder.getCallingUid();
            int appId = UserHandle.getAppId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "setHarmfulAppInfo");
            if (!PackageManagerServiceUtils.isSystemOrRoot(appId) && snapshotComputer.checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
                throw new SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
            }
            if (PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageManagerService.IPackageManagerImpl.lambda$setHarmfulAppWarning$11(i, charSequence, (PackageStateWrite) obj);
                }
            }).isSpecificPackageNull()) {
                throw new IllegalArgumentException("Unknown package: " + str);
            }
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        public static /* synthetic */ void lambda$setHarmfulAppWarning$11(int i, CharSequence charSequence, PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setHarmfulAppWarning(charSequence == null ? null : charSequence.toString());
        }

        public boolean setInstallLocation(int i) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS", null);
            if (getInstallLocation() == i) {
                return true;
            }
            if (i != 0 && i != 1 && i != 2) {
                return false;
            }
            Settings.Global.putInt(PackageManagerService.this.mContext.getContentResolver(), "default_install_location", i);
            return true;
        }

        public void setInstallerPackageName(final String str, final String str2) {
            final int callingUid = Binder.getCallingUid();
            final int userId = UserHandle.getUserId(callingUid);
            FunctionalUtils.ThrowingCheckedFunction throwingCheckedFunction = new FunctionalUtils.ThrowingCheckedFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda7
                public final Object apply(Object obj) {
                    Boolean lambda$setInstallerPackageName$12;
                    lambda$setInstallerPackageName$12 = PackageManagerService.IPackageManagerImpl.this.lambda$setInstallerPackageName$12(callingUid, str, userId, str2, (Computer) obj);
                    return lambda$setInstallerPackageName$12;
                }
            };
            PackageStateMutator.InitialState recordInitialState = PackageManagerService.this.recordInitialState();
            if (((Boolean) throwingCheckedFunction.apply(PackageManagerService.this.snapshotComputer())).booleanValue()) {
                final int packageUid = str2 == null ? -1 : PackageManagerService.this.snapshotComputer().getPackageUid(str2, 0L, userId);
                synchronized (PackageManagerService.this.mLock) {
                    PackageStateMutator.Result commitPackageStateMutation = PackageManagerService.this.commitPackageStateMutation(recordInitialState, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((PackageStateWrite) obj).setInstaller(str2, packageUid);
                        }
                    });
                    if (commitPackageStateMutation.isPackagesChanged() || commitPackageStateMutation.isStateChanged()) {
                        synchronized (PackageManagerService.this.mPackageStateWriteLock) {
                            if (!((Boolean) throwingCheckedFunction.apply(PackageManagerService.this.snapshotComputer())).booleanValue()) {
                                return;
                            } else {
                                PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((PackageStateWrite) obj).setInstaller(str2, packageUid);
                                    }
                                });
                            }
                        }
                    }
                    PackageStateInternal packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
                    PackageManagerService.this.mSettings.addInstallerPackageNames(packageStateInternal.getInstallSource());
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    packageManagerService.mAppsFilter.addPackage(packageManagerService.snapshotComputer(), packageStateInternal);
                    PackageManagerService.this.scheduleWriteSettings();
                }
            }
        }

        public /* synthetic */ Boolean lambda$setInstallerPackageName$12(int i, String str, int i2, String str2, Computer computer) {
            PackageStateInternal packageStateInternal;
            Signature[] signatures;
            if (computer.getInstantAppPackageName(i) != null) {
                return Boolean.FALSE;
            }
            PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str, i, i2);
            if (packageStateForInstalledAndFiltered == null) {
                throw new IllegalArgumentException("Unknown target package: " + str);
            }
            if (str2 != null) {
                packageStateInternal = computer.getPackageStateForInstalledAndFiltered(str2, i, i2);
                if (packageStateInternal == null) {
                    throw new IllegalArgumentException("Unknown installer package: " + str2);
                }
            } else {
                packageStateInternal = null;
            }
            Pair packageOrSharedUser = computer.getPackageOrSharedUser(UserHandle.getAppId(i));
            if (packageOrSharedUser != null) {
                Object obj = packageOrSharedUser.first;
                if (obj != null) {
                    signatures = ((PackageStateInternal) obj).getSigningDetails().getSignatures();
                } else {
                    signatures = ((SharedUserApi) packageOrSharedUser.second).getSigningDetails().getSignatures();
                }
                if (packageStateInternal != null && PackageManagerServiceUtils.compareSignatures(signatures, packageStateInternal.getSigningDetails().getSignatures()) != 0) {
                    throw new SecurityException("Caller does not have same cert as new installer package " + str2);
                }
                String str3 = packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName;
                PackageStateInternal packageStateInternal2 = str3 != null ? computer.getPackageStateInternal(str3) : null;
                if (packageStateInternal2 != null) {
                    if (PackageManagerServiceUtils.compareSignatures(signatures, packageStateInternal2.getSigningDetails().getSignatures()) != 0) {
                        throw new SecurityException("Caller does not have same cert as old installer package " + str3);
                    }
                } else if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                    EventLog.writeEvent(1397638484, "150857253", Integer.valueOf(i), "");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (PackageManagerService.this.mInjector.getCompatibility().isChangeEnabledByUid(150857253L, i)) {
                            throw new SecurityException("Neither user " + i + " nor current process has android.permission.INSTALL_PACKAGES");
                        }
                        return Boolean.FALSE;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                return Boolean.TRUE;
            }
            throw new SecurityException("Unknown calling UID: " + i);
        }

        public void relinquishUpdateOwnership(String str) {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, userId);
            if (packageStateForInstalledAndFiltered == null) {
                throw new IllegalArgumentException("Unknown target package: " + str);
            }
            String str2 = packageStateForInstalledAndFiltered.getInstallSource().mUpdateOwnerPackageName;
            PackageStateInternal packageStateInternal = str2 == null ? null : snapshotComputer.getPackageStateInternal(str2);
            if (packageStateInternal == null) {
                return;
            }
            int appId = UserHandle.getAppId(callingUid);
            int appId2 = packageStateInternal.getAppId();
            if (appId != 1000 && appId != 2000 && appId != appId2) {
                throw new SecurityException("Caller is not the current update owner.");
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageStateWrite) obj).setUpdateOwner(null);
                }
            });
            PackageManagerService.this.scheduleWriteSettings();
        }

        public boolean setInstantAppCookie(String str, byte[] bArr, int i) {
            PackageStateInternal packageStateInternal;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, true, true, "setInstantAppCookie");
            if (!snapshotComputer.isCallerSameApp(str, Binder.getCallingUid()) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || packageStateInternal.getPkg() == null) {
                return false;
            }
            return PackageManagerService.this.mInstantAppRegistry.setInstantAppCookie(packageStateInternal.getPkg(), bArr, PackageManagerService.this.mContext.getPackageManager().getInstantAppCookieMaxBytes(), i);
        }

        public void setKeepUninstalledPackages(List list) {
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.KEEP_UNINSTALLED_PACKAGES", "setKeepUninstalledPackages requires KEEP_UNINSTALLED_PACKAGES permission");
            Objects.requireNonNull(list);
            PackageManagerService.this.setKeepUninstalledPackagesInternal(snapshot(), list);
        }

        public void setMimeGroup(final String str, final String str2, List list) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, Binder.getCallingUid());
            List emptyIfNull = CollectionUtils.emptyIfNull(list);
            Iterator it = emptyIfNull.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).length() > 255) {
                    throw new IllegalArgumentException("MIME type length exceeds 255 characters");
                }
            }
            final PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            Set set = (Set) packageStateInternal.getMimeGroups().get(str2);
            if (set == null) {
                throw new IllegalArgumentException("Unknown MIME group " + str2 + " for package " + str);
            }
            if (set.size() == emptyIfNull.size() && set.containsAll(emptyIfNull)) {
                return;
            }
            if (emptyIfNull.size() > 500) {
                throw new IllegalStateException("Max limit on MIME types for MIME group " + str2 + " exceeded for package " + str);
            }
            final ArraySet arraySet = new ArraySet(emptyIfNull);
            PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageStateWrite) obj).setMimeGroup(str2, arraySet);
                }
            });
            PackageManagerService packageManagerService = PackageManagerService.this;
            if (packageManagerService.mComponentResolver.updateMimeGroup(packageManagerService.snapshotComputer(), str, str2)) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda20
                    public final void runOrThrow() {
                        PackageManagerService.IPackageManagerImpl.this.lambda$setMimeGroup$17(str, packageStateInternal);
                    }
                });
            }
            PackageManagerService.this.scheduleWriteSettings();
        }

        public /* synthetic */ void lambda$setMimeGroup$17(String str, PackageStateInternal packageStateInternal) {
            PackageManagerService.this.mPreferredActivityHelper.clearPackagePreferredActivities(str, -1);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            ArrayList arrayList = new ArrayList(Collections.singletonList(str));
            int appId = packageStateInternal.getAppId();
            int[] resolveUserIds = PackageManagerService.this.resolveUserIds(-1);
            for (int i = 0; i < resolveUserIds.length; i++) {
                PackageUserStateInternal packageUserStateInternal = (PackageUserStateInternal) packageStateInternal.getUserStates().get(resolveUserIds[i]);
                if (packageUserStateInternal != null && packageUserStateInternal.isInstalled()) {
                    PackageManagerService.this.sendPackageChangedBroadcast(snapshotComputer, str, true, arrayList, UserHandle.getUid(resolveUserIds[i], appId), "The mimeGroup is changed");
                }
            }
        }

        public void setPackageStoppedState(String str, boolean z, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.setPackageStoppedState(packageManagerService.snapshotComputer(), str, z, i);
        }

        public String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, String str, int i) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.enforceCanSetPackagesSuspendedAsUser(snapshotComputer, str, callingUid, i, "setPackagesSuspendedAsUser");
            PmLog.logDebugInfoAndLogcat("setPackagesSuspendedAsUser, packageName: " + Arrays.asList(strArr) + ", userId: " + i + ", suspended: " + z + ", callingUid: " + callingUid + ", callingPackage: " + str);
            return PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspended(snapshotComputer, strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, str, i, callingUid, false);
        }

        public boolean setRequiredForSystemUser(String str, final boolean z) {
            PackageManagerServiceUtils.enforceSystemOrRoot("setRequiredForSystemUser can only be run by the system or root");
            if (!PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageStateWrite) obj).setRequiredForSystemUser(z);
                }
            }).isCommitted()) {
                return false;
            }
            PackageManagerService.this.scheduleWriteSettings();
            return true;
        }

        public void setRuntimePermissionsVersion(int i, int i2) {
            Preconditions.checkArgumentNonnegative(i);
            Preconditions.checkArgumentNonnegative(i2);
            PackageManagerService.this.enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions("setRuntimePermissionVersion");
            PackageManagerService.this.mSettings.setDefaultRuntimePermissionsVersion(i, i2);
        }

        public void setSplashScreenTheme(String str, final String str2, final int i) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "setSplashScreenTheme");
            PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, callingUid);
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i) == null) {
                return;
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageManagerService.IPackageManagerImpl.lambda$setSplashScreenTheme$19(i, str2, (PackageStateWrite) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$setSplashScreenTheme$19(int i, String str, PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setSplashScreenTheme(str);
        }

        public void setUpdateAvailable(String str, final boolean z) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", null);
            PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageStateWrite) obj).setUpdateAvailable(z);
                }
            });
        }

        public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) {
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS", null);
            PackageManagerService.this.mMoveCallbacks.unregister(iPackageMoveObserver);
        }

        public void verifyPendingInstall(final int i, final int i2) {
            if (i >= 0) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can verify applications");
            }
            final int callingUid = Binder.getCallingUid();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl.this.lambda$verifyPendingInstall$21(i, callingUid, i2);
                }
            });
            Slog.d("PackageManager", "verifyPendingInstall vid: " + i + ", vcode: " + i2 + ", callingUid: " + callingUid);
        }

        public /* synthetic */ void lambda$verifyPendingInstall$21(int i, int i2, int i3) {
            if (i < 0) {
                i = -i;
            }
            PackageVerificationState packageVerificationState = (PackageVerificationState) PackageManagerService.this.mPendingVerification.get(i);
            if (packageVerificationState == null) {
                return;
            }
            if (packageVerificationState.checkRequiredVerifierUid(i2) || packageVerificationState.checkSufficientVerifierUid(i2)) {
                Message obtainMessage = PackageManagerService.this.mHandler.obtainMessage(15);
                PackageVerificationResponse packageVerificationResponse = new PackageVerificationResponse(i3, i2);
                obtainMessage.arg1 = i;
                obtainMessage.obj = packageVerificationResponse;
                PackageManagerService.this.mHandler.sendMessage(obtainMessage);
            }
        }

        public void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.requestChecksumsInternal(packageManagerService.snapshotComputer(), str, z, i, i2, list, iOnChecksumsReadyListener, i3, PackageManagerService.this.mInjector.getBackgroundExecutor(), PackageManagerService.this.mInjector.getBackgroundHandler());
        }

        public void notifyPackagesReplacedReceived(String[] strArr) {
            ArraySet notifyPackagesForReplacedReceived = PackageManagerService.this.snapshotComputer().getNotifyPackagesForReplacedReceived(strArr);
            for (int i = 0; i < notifyPackagesForReplacedReceived.size(); i++) {
                PackageManagerService.this.notifyInstallObserver((String) notifyPackagesForReplacedReceived.valueAt(i), false);
            }
        }

        public boolean waitForHandler(long j, boolean z) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            if (z) {
                PackageManagerService.this.mBackgroundHandler.post(new SettingsStore$$ExternalSyntheticLambda1(countDownLatch));
            } else {
                PackageManagerService.this.mHandler.post(new SettingsStore$$ExternalSyntheticLambda1(countDownLatch));
            }
            long currentTimeMillis = System.currentTimeMillis() + j;
            while (countDownLatch.getCount() > 0) {
                try {
                    long currentTimeMillis2 = currentTimeMillis - System.currentTimeMillis();
                    if (currentTimeMillis2 <= 0) {
                        return false;
                    }
                    return countDownLatch.await(currentTimeMillis2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException unused) {
                }
            }
            return true;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (RuntimeException e) {
                if (!(e instanceof SecurityException) && !(e instanceof IllegalArgumentException) && !(e instanceof ParcelableException)) {
                    Slog.wtf("PackageManager", "Package Manager Unexpected Exception", e);
                }
                throw e;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            new PackageManagerShellCommand(this, packageManagerService.mContext, packageManagerService.mDomainVerificationManager.getShell()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            ArrayMap arrayMap;
            ArraySet arraySet;
            if (DumpUtils.checkDumpAndUsageStatsPermission(PackageManagerService.this.mContext, "PackageManager", printWriter)) {
                if (CoreRune.BAIDU_CARLIFE && BaiduCarlifeADBConnectUtils.isCarlifeForceConnect() && (strArr.length == 0 || BaiduCarlifeADBConnectUtils.isBaiduCarlifePkg(strArr[0]))) {
                    BaiduCarlifeADBConnectUtils.printCarlifePkgInfo(printWriter);
                    return;
                }
                Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
                DefaultAppProvider defaultAppProvider = PackageManagerService.this.mDefaultAppProvider;
                PackageManagerService packageManagerService = PackageManagerService.this;
                KnownPackages knownPackages = new KnownPackages(defaultAppProvider, packageManagerService.mRequiredInstallerPackage, packageManagerService.mRequiredUninstallerPackage, packageManagerService.mSetupWizardPackage, packageManagerService.mRequiredVerifierPackages, packageManagerService.mDefaultTextClassifierPackage, packageManagerService.mSystemTextClassifierPackageName, packageManagerService.mRequiredPermissionControllerPackage, packageManagerService.mConfiguratorPackage, packageManagerService.mIncidentReportApproverPackage, packageManagerService.mAmbientContextDetectionPackage, packageManagerService.mWearableSensingPackage, packageManagerService.mAppPredictionServicePackage, "com.android.companiondevicemanager", packageManagerService.mRetailDemoPackage, packageManagerService.mOverlayConfigSignaturePackage, packageManagerService.mRecentsPackage);
                synchronized (PackageManagerService.this.mAvailableFeatures) {
                    arrayMap = new ArrayMap(PackageManagerService.this.mAvailableFeatures);
                }
                synchronized (PackageManagerService.this.mProtectedBroadcasts) {
                    arraySet = new ArraySet(PackageManagerService.this.mProtectedBroadcasts);
                }
                PackageManagerService packageManagerService2 = PackageManagerService.this;
                PermissionManagerServiceInternal permissionManagerServiceInternal = packageManagerService2.mPermissionManager;
                StorageEventHelper storageEventHelper = packageManagerService2.mStorageEventHelper;
                PackageManagerService packageManagerService3 = PackageManagerService.this;
                new DumpHelper(permissionManagerServiceInternal, storageEventHelper, packageManagerService3.mDomainVerificationManager, packageManagerService3.mInstallerService, packageManagerService3.mRequiredVerifierPackages, knownPackages, packageManagerService3.mChangedPackagesTracker, arrayMap, arraySet, packageManagerService3.getPerUidReadTimeouts(snapshotComputer), PackageManagerService.this.mSnapshotStatistics).doDump(snapshotComputer, fileDescriptor, printWriter, strArr);
                PackageManagerService.this.mPmLifecycle.onDump(snapshotComputer, printWriter, strArr);
                if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
                    PackageManagerService.this.mMonetizationUtils.dumpMonetizationBadgeState(printWriter, strArr);
                }
                dumpHeimdAllFS(printWriter, strArr);
            }
        }

        public final void dumpHeimdAllFS(PrintWriter printWriter, String[] strArr) {
            String str;
            Boolean bool = Boolean.FALSE;
            Boolean bool2 = Boolean.TRUE;
            int i = 0;
            while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                i++;
                if ("--proto".equals(str)) {
                    return;
                }
            }
            String str2 = null;
            while (i < strArr.length) {
                String str3 = strArr[i];
                i++;
                if ("android".equals(str3) || str3.contains(".")) {
                    str2 = str3;
                } else if ("heimdallfs".equals(str3)) {
                    bool = Boolean.TRUE;
                } else {
                    bool2 = Boolean.FALSE;
                }
            }
            if (bool.booleanValue() || bool2.booleanValue()) {
                dumpHeimdAllFSState(printWriter, str2);
            }
        }

        public final void dumpHeimdAllFSState(PrintWriter printWriter, String str) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            List<PackageInfo> arrayList = new ArrayList<>();
            HeimdAllFsService heimdAllFsService = new HeimdAllFsService(PackageManagerService.this.mContext);
            HashMap hashMap = new HashMap();
            synchronized (PackageManagerService.this.mLock) {
                if (str != null) {
                    try {
                        arrayList.add(PackageManagerService.this.mContext.getPackageManager().getPackageInfo(str, 0));
                    } catch (PackageManager.NameNotFoundException unused) {
                        return;
                    }
                } else {
                    arrayList = PackageManagerService.this.mContext.getPackageManager().getInstalledPackages(0);
                }
                if (arrayList != null && arrayList.size() != 0) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("HeimdAllFS state:");
                    indentingPrintWriter.increaseIndent();
                    if (!HeimdAllFsService.isServiceActivate()) {
                        indentingPrintWriter.println("HeimdAllFS does not supported.");
                        return;
                    }
                    long size = arrayList.size();
                    for (PackageInfo packageInfo : arrayList) {
                        if (packageInfo.applicationInfo.sourceDir.startsWith("/data")) {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(packageInfo.applicationInfo.sourceDir);
                            String[] strArr = packageInfo.applicationInfo.splitSourceDirs;
                            if (!ArrayUtils.isEmpty(strArr)) {
                                for (String str2 : strArr) {
                                    arrayList2.add(str2);
                                }
                            }
                            hashMap.put(packageInfo.packageName, arrayList2);
                        }
                    }
                    long[] jArr = {0, 0};
                    long size2 = hashMap.size();
                    long j = 0;
                    for (String str3 : hashMap.keySet()) {
                        ArrayList arrayList3 = (ArrayList) hashMap.get(str3);
                        long j2 = size2;
                        j += arrayList3.size();
                        indentingPrintWriter.println("[" + str3 + "]");
                        indentingPrintWriter.increaseIndent();
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            long[] dumpPackageState = heimdAllFsService.dumpPackageState(indentingPrintWriter, (String) it.next());
                            long j3 = dumpPackageState[0];
                            long j4 = dumpPackageState[1];
                            if (j3 > j4) {
                                jArr[0] = jArr[0] + j3;
                                jArr[1] = jArr[1] + j4;
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                        size2 = j2;
                    }
                    indentingPrintWriter.println("[ Summary ]");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.print("# of packages: " + size);
                    indentingPrintWriter.print(" / # of packages in DATA: " + size2);
                    indentingPrintWriter.println(" (" + j + ")");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Compressed apks info - sum of orig size: ");
                    sb.append(jArr[0]);
                    indentingPrintWriter.print(sb.toString());
                    indentingPrintWriter.println(" / sum of compr size : " + jArr[1]);
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                indentingPrintWriter.println("Unable to find package: " + str);
            }
        }

        public boolean getMetadataForIconTray(String str, String str2, int i, List list) {
            Bundle metaData;
            if (PackageManagerService.this.mUserManager.exists(i) && str != null && str2 != null && list != null) {
                Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
                snapshotComputer.enforceCrossUserPermission(Binder.getCallingUid(), i, false, false, "get application info");
                int callingUid = Binder.getCallingUid();
                PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null || packageStateInternal.getPkg() == null || snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i) || (metaData = packageStateInternal.getPkg().getMetaData()) == null) {
                    return false;
                }
                boolean z = metaData.getBoolean(str2);
                CharSequence charSequence = metaData.getCharSequence("com.samsung.android.icon_container.feature_appicon");
                if (charSequence != null) {
                    for (String str3 : charSequence.toString().split("\\|")) {
                        list.add(str3.trim());
                    }
                }
                return z;
            }
            return false;
        }

        public boolean isPackageAutoDisabled(String str, int i) {
            PackageStateInternal packageStateInternal;
            int userId = UserHandle.getUserId(i);
            if (str != null && (packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str)) != null) {
                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(userId);
                if (userStateOrDefault.getEnabledState() == 4 && "auto_disabler".equals(userStateOrDefault.getLastDisableAppCaller())) {
                    return true;
                }
            }
            return false;
        }

        public boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) {
            if (UserHandle.getCallingUserId() != i) {
                PackageManagerService.this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "semIsPermissionRevokedByUserFixed for user " + i);
            }
            if (checkPermission(str2, str, i) == 0) {
                return false;
            }
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
            if (packageStateInternal == null || snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i)) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return (((PermissionManager) PackageManagerService.this.mContext.getSystemService(PermissionManager.class)).getPermissionFlags(str2, str, UserHandle.of(i)) & 2) != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setApplicationEnabledSettingWithList(List list, int i, int i2, boolean z, boolean z2, int i3, String str) {
            PackageManagerService.this.mEmergencyModePackageHandler.setApplicationEnabledSettingWithList(list, i, i2, z, z2, i3, str);
        }

        public int getProgressionOfPackageChanged() {
            return PackageManagerService.this.mEmergencyModePackageHandler.getProgressionOfPackageChanged();
        }

        public void cancelEMPHandlerSendPendingBroadcast() {
            PackageManagerService.this.mEmergencyModePackageHandler.cancelEMPHandlerSendPendingBroadcast();
        }

        public List getRequestedRuntimePermissionsForMDM(String str) {
            if (Binder.getCallingPid() != Process.myPid()) {
                throw new SecurityException();
            }
            if (str == null) {
                return null;
            }
            return PackageManagerService.this.mPermissionManager.getRequestedRuntimePermissionsForMDM(str);
        }

        public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
            return PackageManagerService.this.mPermissionManager.applyRuntimePermissionsForAllApplicationsForMDM(i, i2);
        }

        public boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
            return PackageManagerService.this.mPermissionManager.applyRuntimePermissionsForMDM(str, list, i, i2);
        }

        public int setLicensePermissionsForMDM(String str) {
            int licensePermissionsForMDM;
            Log.i("PackageManager", "setLicensePermissions ");
            if (Binder.getCallingUid() != Process.myUid()) {
                Log.i("PackageManager", "Caller is not SYSTEM_PROCESS");
                return -1;
            }
            Set keySet = EnterpriseDeviceAdminInfo.sKnownPolicies.keySet();
            synchronized (PackageManagerService.this.mLock) {
                licensePermissionsForMDM = PackageManagerService.this.mPermissionManager.setLicensePermissionsForMDM(str, keySet);
                PackageManagerService packageManagerService = PackageManagerService.this;
                packageManagerService.mSettings.writeLPr(packageManagerService.mLiveComputer, false);
            }
            return licensePermissionsForMDM;
        }

        public List getPackageGrantedPermissionsForMDM(String str) {
            int callingUid = Binder.getCallingUid();
            if (callingUid != 0 && callingUid != 1000) {
                throw new SecurityException("getPackageGrantedPermissionsForMDM can only be called by the system");
            }
            return PackageManagerService.this.mPermissionManager.getPackageGrantedPermissionsForMDM(str);
        }

        public void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) {
            int callingUid = Binder.getCallingUid();
            if (callingUid != 0 && callingUid != 1000) {
                throw new SecurityException("clearPackagePreferredActivitiesAsUserForMDM can only be called by the system");
            }
            long currentTimeMillis = System.currentTimeMillis();
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            try {
                synchronized (PackageManagerService.this.mLock) {
                    PackageManagerService.this.clearPackagePreferredActivitiesLPw(str, sparseBooleanArray, i);
                    if (sparseBooleanArray.size() > 0) {
                        PackageManagerService.this.scheduleWritePackageRestrictions(i);
                    }
                }
            } finally {
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                if (currentTimeMillis2 > 100) {
                    Log.d("PackageManager", "clearPackagePreferredActivitiesAsUserForMDM - Execution time: " + currentTimeMillis2 + " ms");
                }
            }
        }

        public List getGrantedPermissionsForMDM(String str) {
            Log.i("PackageManager", "getGrantedPermissionsForMDM");
            if (Binder.getCallingUid() != Process.myUid()) {
                Log.i("PackageManager", "getGrantedPermissionsForMDM: Caller is not SYSTEM_PROCESS");
                return null;
            }
            PackageStateInternal packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
                Log.i("PackageManager", "Not found ps or pkg for " + str);
                return null;
            }
            LegacyPermissionState legacyPermissionState = packageStateInternal.getLegacyPermissionState();
            ArrayList arrayList = new ArrayList();
            Collection<LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(-1);
            Collection<LegacyPermissionState.PermissionState> permissionStates2 = packageStateInternal.getPkg().getTargetSdkVersion() > 22 ? legacyPermissionState.getPermissionStates(0) : null;
            if (permissionStates != null && permissionStates.size() > 0) {
                for (LegacyPermissionState.PermissionState permissionState : permissionStates) {
                    if (permissionState != null && permissionState.isGranted()) {
                        arrayList.add(permissionState.getName());
                    }
                }
            } else {
                Log.i("PackageManager", "getGrantedPermissionsForMDM : installPermissions is null");
            }
            if (permissionStates2 != null && permissionStates2.size() > 0) {
                for (LegacyPermissionState.PermissionState permissionState2 : permissionStates2) {
                    if (permissionState2 != null && permissionState2.isGranted()) {
                        arrayList.add(permissionState2.getName());
                    }
                }
            } else {
                Log.i("PackageManager", "getGrantedPermissionsForMDM : runtimePermissions is null");
            }
            return new ArrayList(arrayList);
        }

        public void checkDeletableListForASKS() {
            try {
                if (ASKSManager.getASKSManager() != null) {
                    ASKSManager.getASKSManager().checkDeletableListForASKS();
                } else {
                    Slog.e("AASA_PackageManager", "ASKSManager is null");
                }
            } catch (RemoteException e) {
                Log.e("PackageManager", "RemoteException: " + e.getMessage());
            }
        }

        public void setTrustTimebyStatusChanged() {
            try {
                if (ASKSManager.getASKSManager() != null) {
                    ASKSManager.getASKSManager().setTrustTimebyStatusChanged();
                } else {
                    Slog.e("AASA_PackageManager", "ASKSManager is null");
                }
            } catch (RemoteException e) {
                Log.e("PackageManager", "RemoteException: " + e.getMessage());
            }
        }

        public String getUNvalueForASKS() {
            try {
                if (ASKSManager.getASKSManager() != null) {
                    return ASKSManager.getASKSManager().getUNvalueForASKS();
                }
                Slog.e("AASA_PackageManager", "ASKSManager is null");
                return null;
            } catch (RemoteException e) {
                Log.e("PackageManager", "RemoteException: " + e.getMessage());
                return null;
            }
        }

        public String[] checkASKSTarget(int i) {
            try {
                if (ASKSManager.getASKSManager() != null) {
                    return ASKSManager.getASKSManager().checkASKSTarget(i);
                }
                Slog.e("AASA_PackageManager", "ASKSManager is null");
                return null;
            } catch (RemoteException e) {
                Log.e("PackageManager", "RemoteException: " + e.getMessage());
                return null;
            }
        }

        public List getPackageListForDualDarPolicy(String str) {
            return PackageManagerService.this.getPackageListForDualDarPolicy(str);
        }

        public ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) {
            ArrayList arrayList = new ArrayList();
            UnknownSourceAppManager unknownSourceAppManager = PackageManagerService.this.mCustomInjector.getUnknownSourceAppManager();
            if (unknownSourceAppManager == null) {
                return new ParceledListSlice(arrayList);
            }
            for (PackageInfo packageInfo : snapshot().getInstalledPackages(j, i).getList()) {
                if (unknownSourceAppManager.isUnknownSourcePackage(packageInfo.packageName)) {
                    arrayList.add(packageInfo);
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public boolean isUnknownSourcePackage(String str) {
            UnknownSourceAppManager unknownSourceAppManager = PackageManagerService.this.mCustomInjector.getUnknownSourceAppManager();
            return (unknownSourceAppManager == null || !unknownSourceAppManager.isUnknownSourcePackage(str) || snapshot().getPackageStateInternal(str) == null) ? false : true;
        }

        public void changeMonetizationBadgeState(String str, String str2) {
            PackageManagerService.this.mMonetizationUtils.changeMonetizationBadgeState(str, str2);
        }

        public boolean shouldAppSupportBadgeIcon(String str) {
            return PackageManagerService.this.mMonetizationUtils.shouldAppSupportBadgeIcon(str);
        }
    }

    /* loaded from: classes3.dex */
    public class PackageManagerInternalImpl extends PackageManagerInternalBase {
        public PackageManagerInternalImpl() {
            super(PackageManagerService.this);
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public Context getContext() {
            return PackageManagerService.this.mContext;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public PermissionManagerServiceInternal getPermissionManager() {
            return PackageManagerService.this.mPermissionManager;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public AppDataHelper getAppDataHelper() {
            return PackageManagerService.this.mAppDataHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public PackageObserverHelper getPackageObserverHelper() {
            return PackageManagerService.this.mPackageObserverHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public ResolveIntentHelper getResolveIntentHelper() {
            return PackageManagerService.this.mResolveIntentHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public SuspendPackageHelper getSuspendPackageHelper() {
            return PackageManagerService.this.mSuspendPackageHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public DistractingPackageHelper getDistractingPackageHelper() {
            return PackageManagerService.this.mDistractingPackageHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public ProtectedPackages getProtectedPackages() {
            return PackageManagerService.this.mProtectedPackages;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public InstantAppRegistry getInstantAppRegistry() {
            return PackageManagerService.this.mInstantAppRegistry;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        public ApexManager getApexManager() {
            return PackageManagerService.this.mApexManager;
        }

        @Override // android.content.pm.PackageManagerInternal
        public DynamicCodeLogger getDynamicCodeLogger() {
            return PackageManagerService.this.mDynamicCodeLogger;
        }

        @Override // android.content.pm.PackageManagerInternal
        public void updateExternalMediaStatus(boolean z, boolean z2) {
            PackageManagerService.this.mAsecInstallHelper.updateExternalMediaStatus(z, z2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPlatformSigned(String str) {
            PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return false;
            }
            SigningDetails signingDetails = packageStateInternal.getSigningDetails();
            return signingDetails.hasAncestorOrSelf(PackageManagerService.this.mPlatformPackage.getSigningDetails()) || PackageManagerService.this.mPlatformPackage.getSigningDetails().checkCapability(signingDetails, 4);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isDataRestoreSafe(byte[] bArr, String str) {
            SigningDetails signingDetails = snapshot().getSigningDetails(str);
            if (signingDetails == null) {
                return false;
            }
            return signingDetails.hasSha256Certificate(bArr, 1);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isDataRestoreSafe(Signature signature, String str) {
            SigningDetails signingDetails = snapshot().getSigningDetails(str);
            if (signingDetails == null) {
                return false;
            }
            return signingDetails.hasCertificate(signature, 1);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean hasSignatureCapability(int i, int i2, int i3) {
            Computer snapshot = snapshot();
            SigningDetails signingDetails = snapshot.getSigningDetails(i);
            SigningDetails signingDetails2 = snapshot.getSigningDetails(i2);
            return signingDetails.checkCapability(signingDetails2, i3) || signingDetails2.hasAncestorOrSelf(signingDetails);
        }

        @Override // android.content.pm.PackageManagerInternal
        public PackageList getPackageList(PackageManagerInternal.PackageListObserver packageListObserver) {
            final ArrayList arrayList = new ArrayList();
            PackageManagerService.this.forEachPackageState(snapshot(), new Consumer() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageManagerService.PackageManagerInternalImpl.lambda$getPackageList$0(arrayList, (PackageStateInternal) obj);
                }
            });
            PackageList packageList = new PackageList(arrayList, packageListObserver);
            if (packageListObserver != null) {
                PackageManagerService.this.mPackageObserverHelper.addObserver(packageList);
            }
            return packageList;
        }

        public static /* synthetic */ void lambda$getPackageList$0(ArrayList arrayList, PackageStateInternal packageStateInternal) {
            AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (pkg != null) {
                arrayList.add(pkg.getPackageName());
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public String getDisabledSystemPackageName(String str) {
            PackageStateInternal disabledSystemPackage = snapshot().getDisabledSystemPackage(str);
            AndroidPackageInternal pkg = disabledSystemPackage == null ? null : disabledSystemPackage.getPkg();
            if (pkg == null) {
                return null;
            }
            return pkg.getPackageName();
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isResolveActivityComponent(ComponentInfo componentInfo) {
            return PackageManagerService.this.mResolveActivity.packageName.equals(componentInfo.packageName) && PackageManagerService.this.mResolveActivity.name.equals(componentInfo.name);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void removeAllNonSystemPackageSuspensions(int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(snapshotComputer, snapshotComputer.getAllAvailablePackageNames(), new Predicate() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$removeAllNonSystemPackageSuspensions$1;
                    lambda$removeAllNonSystemPackageSuspensions$1 = PackageManagerService.PackageManagerInternalImpl.lambda$removeAllNonSystemPackageSuspensions$1((String) obj);
                    return lambda$removeAllNonSystemPackageSuspensions$1;
                }
            }, i);
        }

        public static /* synthetic */ boolean lambda$removeAllNonSystemPackageSuspensions$1(String str) {
            return !"android".equals(str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void flushPackageRestrictions(int i) {
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public String[] setPackagesSuspendedByAdmin(int i, String[] strArr, boolean z) {
            return PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspendedByAdmin(PackageManagerService.this.snapshotComputer(), i, strArr, z);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setPackagesSuspendedForQuietMode(int i, boolean z) {
            PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspendedForQuietMode(PackageManagerService.this.snapshotComputer(), i, z);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setDeviceAndProfileOwnerPackages(int i, String str, SparseArray sparseArray) {
            PackageManagerService.this.mProtectedPackages.setDeviceAndProfileOwnerPackages(i, str, sparseArray);
            ArraySet arraySet = new ArraySet();
            if (str != null) {
                arraySet.add(Integer.valueOf(i));
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (sparseArray.valueAt(i2) != null) {
                    removeAllNonSystemPackageSuspensions(sparseArray.keyAt(i2));
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean wasPackageStopped(String str, int i) {
            PackageStateInternal packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return true;
            }
            return packageStateInternal.getUserStateOrDefault(i).isStopped();
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setExternalSourcesPolicy(PackageManagerInternal.ExternalSourcesPolicy externalSourcesPolicy) {
            if (externalSourcesPolicy != null) {
                PackageManagerService.this.mExternalSourcesPolicy = externalSourcesPolicy;
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPackagePersistent(String str) {
            AndroidPackageInternal pkg;
            PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            return packageStateInternal != null && (pkg = packageStateInternal.getPkg()) != null && packageStateInternal.isSystem() && pkg.isPersistent();
        }

        @Override // android.content.pm.PackageManagerInternal
        public List getTargetPackageNames(int i) {
            final ArrayList arrayList = new ArrayList();
            PackageManagerService.this.forEachPackageState(snapshot(), new Consumer() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageManagerService.PackageManagerInternalImpl.lambda$getTargetPackageNames$2(arrayList, (PackageStateInternal) obj);
                }
            });
            return arrayList;
        }

        public static /* synthetic */ void lambda$getTargetPackageNames$2(List list, PackageStateInternal packageStateInternal) {
            AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (pkg == null || pkg.isResourceOverlay()) {
                return;
            }
            list.add(pkg.getPackageName());
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setEnabledOverlayPackages(int i, ArrayMap arrayMap, Set set, Set set2) {
            PackageManagerService.this.setEnabledOverlayPackages(i, arrayMap, set, set2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void addIsolatedUid(int i, int i2) {
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.mIsolatedOwners.put(i, i2);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void removeIsolatedUid(int i) {
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.mIsolatedOwners.delete(i);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void notifyPackageUse(String str, int i) {
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.notifyPackageUseInternal(str, i);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public String removeLegacyDefaultBrowserPackageName(int i) {
            String removeDefaultBrowserPackageNameLPw;
            synchronized (PackageManagerService.this.mLock) {
                removeDefaultBrowserPackageNameLPw = PackageManagerService.this.mSettings.removeDefaultBrowserPackageNameLPw(i);
            }
            return removeDefaultBrowserPackageNameLPw;
        }

        @Override // android.content.pm.PackageManagerInternal
        public void uninstallApex(String str, long j, int i, IntentSender intentSender, int i2) {
            if (!PackageManagerServiceUtils.isRootOrShell(Binder.getCallingUid())) {
                throw new SecurityException("Not allowed to uninstall apexes");
            }
            PackageInstallerService.PackageDeleteObserverAdapter packageDeleteObserverAdapter = new PackageInstallerService.PackageDeleteObserverAdapter(PackageManagerService.this.mContext, intentSender, str, false, i);
            if ((i2 & 2) == 0) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Can't uninstall an apex for a single user");
                return;
            }
            ApexManager apexManager = PackageManagerService.this.mApexManager;
            PackageInfo packageInfo = snapshot().getPackageInfo(str, 1073741824L, 0);
            if (packageInfo == null) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, str + " is not an apex package");
                return;
            }
            if (j != -1 && packageInfo.getLongVersionCode() != j) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Active version " + packageInfo.getLongVersionCode() + " is not equal to " + j + "]");
                return;
            }
            if (!apexManager.uninstallApex(packageInfo.applicationInfo.sourceDir)) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Failed to uninstall apex " + str);
                return;
            }
            packageDeleteObserverAdapter.onPackageDeleted(str, 1, null);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void legacyDumpProfiles(String str, boolean z) {
            AndroidPackage androidPackage = PackageManagerService.this.snapshotComputer().getPackage(str);
            if (androidPackage == null) {
                throw new IllegalArgumentException("Unknown package: " + str);
            }
            synchronized (PackageManagerService.this.mInstallLock) {
                Trace.traceBegin(16384L, "dump profiles");
                PackageManagerService.this.mArtManagerService.dumpProfiles(androidPackage, z);
                Trace.traceEnd(16384L);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void legacyForceDexOpt(String str) {
            PackageManagerService.this.mDexOptHelper.forceDexOpt(PackageManagerService.this.snapshotComputer(), str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void legacyReconcileSecondaryDexFiles(String str) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) == null && !snapshotComputer.isInstantAppInternal(str, UserHandle.getCallingUserId(), 1000)) {
                PackageManagerService.this.mDexManager.reconcileSecondaryDexFiles(str);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void updateRuntimePermissionsFingerprint(int i) {
            PackageManagerService.this.mSettings.updateRuntimePermissionsFingerprint(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void migrateLegacyObbData() {
            try {
                PackageManagerService.this.mInstaller.migrateLegacyObbData();
            } catch (Exception e) {
                Slog.wtf("PackageManager", e);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void writeSettings(boolean z) {
            synchronized (PackageManagerService.this.mLock) {
                if (z) {
                    PackageManagerService.this.scheduleWriteSettings();
                } else {
                    PackageManagerService.this.writeSettingsLPrTEMP();
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void writePermissionSettings(int[] iArr, boolean z) {
            synchronized (PackageManagerService.this.mLock) {
                for (int i : iArr) {
                    PackageManagerService.this.mSettings.writePermissionStateForUserLPr(i, !z);
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPermissionUpgradeNeeded(int i) {
            return PackageManagerService.this.mSettings.isPermissionUpgradeNeeded(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setIntegrityVerificationResult(int i, int i2) {
            Message obtainMessage = PackageManagerService.this.mHandler.obtainMessage(25);
            obtainMessage.arg1 = i;
            obtainMessage.obj = Integer.valueOf(i2);
            PackageManagerService.this.mHandler.sendMessage(obtainMessage);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setVisibilityLogging(String str, boolean z) {
            PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can set visibility logging.");
            PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                throw new IllegalStateException("No package found for " + str);
            }
            PackageManagerService.this.mAppsFilter.getFeatureConfig().enableLogging(packageStateInternal.getAppId(), z);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void clearBlockUninstallForUser(int i) {
            synchronized (PackageManagerService.this.mLock) {
                PackageManagerService.this.mSettings.clearBlockUninstallLPw(i);
                PackageManagerService.this.mSettings.writePackageRestrictionsLPr(i);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean registerInstalledLoadingProgressCallback(String str, PackageManagerInternal.InstalledLoadingProgressCallback installedLoadingProgressCallback, int i) {
            PackageStateInternal packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                return false;
            }
            if (!packageStateForInstalledAndFiltered.isLoading()) {
                Slog.w("PackageManager", "Failed registering loading progress callback. Package is fully loaded.");
                return false;
            }
            IncrementalManager incrementalManager = PackageManagerService.this.mIncrementalManager;
            if (incrementalManager == null) {
                Slog.w("PackageManager", "Failed registering loading progress callback. Incremental is not enabled");
                return false;
            }
            return incrementalManager.registerLoadingProgressCallback(packageStateForInstalledAndFiltered.getPathString(), installedLoadingProgressCallback.getBinder());
        }

        @Override // android.content.pm.PackageManagerInternal
        public IncrementalStatesInfo getIncrementalStatesInfo(String str, int i, int i2) {
            PackageStateInternal packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, i, i2);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return new IncrementalStatesInfo(packageStateForInstalledAndFiltered.isLoading(), packageStateForInstalledAndFiltered.getLoadingProgress(), packageStateForInstalledAndFiltered.getLoadingCompletedTime());
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isSameApp(String str, int i, int i2) {
            return isSameApp(str, 0L, i, i2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isSameApp(String str, long j, int i, int i2) {
            if (str == null) {
                return false;
            }
            if (Process.isSdkSandboxUid(i)) {
                return str.equals(PackageManagerService.this.mRequiredSdkSandboxPackage);
            }
            return UserHandle.isSameApp(snapshot().getPackageUid(str, j, i2), i);
        }

        public /* synthetic */ void lambda$onPackageProcessKilledForUninstall$3(String str) {
            PackageManagerService.this.notifyInstallObserver(str, true);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void onPackageProcessKilledForUninstall(final String str) {
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.PackageManagerInternalImpl.this.lambda$onPackageProcessKilledForUninstall$3(str);
                }
            });
        }
    }

    public final void setEnabledOverlayPackages(final int i, final ArrayMap arrayMap, final Set set, Set set2) {
        int i2;
        String str;
        ArrayMap arrayMap2 = arrayMap;
        final ArrayMap arrayMap3 = new ArrayMap();
        final int size = arrayMap.size();
        synchronized (this.mOverlayPathsLock) {
            Computer snapshotComputer = snapshotComputer();
            int i3 = 0;
            while (true) {
                ArraySet arraySet = null;
                if (i3 >= size) {
                    break;
                }
                String str2 = (String) arrayMap2.keyAt(i3);
                OverlayPaths overlayPaths = (OverlayPaths) arrayMap2.valueAt(i3);
                PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
                AndroidPackageInternal pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                if (str2 != null && pkg != null) {
                    if (Objects.equals(packageStateInternal.getUserStateOrDefault(i).getOverlayPaths(), overlayPaths)) {
                        i2 = i3;
                    } else {
                        if (pkg.getLibraryNames() != null) {
                            for (String str3 : pkg.getLibraryNames()) {
                                int i4 = i3;
                                SharedLibraryInfo sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(str3, -1L);
                                if (sharedLibraryInfo == null) {
                                    i3 = i4;
                                } else {
                                    String str4 = str3;
                                    OverlayPaths overlayPaths2 = overlayPaths;
                                    PackageStateInternal packageStateInternal2 = packageStateInternal;
                                    String str5 = str2;
                                    ArraySet arraySet2 = arraySet;
                                    List<VersionedPackage> packagesUsingSharedLibrary = snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 0L, 1000, i);
                                    if (packagesUsingSharedLibrary == null) {
                                        packageStateInternal = packageStateInternal2;
                                        overlayPaths = overlayPaths2;
                                        arraySet = arraySet2;
                                        i3 = i4;
                                        str2 = str5;
                                    } else {
                                        ArraySet arraySet3 = arraySet2;
                                        for (VersionedPackage versionedPackage : packagesUsingSharedLibrary) {
                                            PackageStateInternal packageStateInternal3 = snapshotComputer.getPackageStateInternal(versionedPackage.getPackageName());
                                            if (packageStateInternal3 != null) {
                                                String str6 = str4;
                                                if (canSetOverlayPaths((OverlayPaths) packageStateInternal3.getUserStateOrDefault(i).getSharedLibraryOverlayPaths().get(str6), overlayPaths2)) {
                                                    String packageName = versionedPackage.getPackageName();
                                                    ArraySet add = ArrayUtils.add(arraySet3, packageName);
                                                    set.add(packageName);
                                                    arraySet3 = add;
                                                }
                                                str4 = str6;
                                            }
                                        }
                                        String str7 = str4;
                                        if (arraySet3 != null) {
                                            str = str5;
                                            ArrayMap arrayMap4 = (ArrayMap) arrayMap3.get(str);
                                            if (arrayMap4 == null) {
                                                arrayMap4 = new ArrayMap();
                                                arrayMap3.put(str, arrayMap4);
                                            }
                                            arrayMap4.put(str7, arraySet3);
                                        } else {
                                            str = str5;
                                        }
                                        str2 = str;
                                        packageStateInternal = packageStateInternal2;
                                        overlayPaths = overlayPaths2;
                                        arraySet = arraySet2;
                                        i3 = i4;
                                    }
                                }
                            }
                        }
                        String str8 = str2;
                        i2 = i3;
                        if (canSetOverlayPaths(packageStateInternal.getUserStateOrDefault(i).getOverlayPaths(), overlayPaths)) {
                            set.add(str8);
                        }
                    }
                    i3 = i2 + 1;
                    arrayMap2 = arrayMap;
                }
                i2 = i3;
                Slog.e("PackageManager", "failed to find package " + str2);
                set2.add(str2);
                i3 = i2 + 1;
                arrayMap2 = arrayMap;
            }
            commitPackageStateMutation(null, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda72
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageManagerService.lambda$setEnabledOverlayPackages$65(size, arrayMap, set, i, arrayMap3, (PackageStateMutator) obj);
                }
            });
        }
        if (i == 0) {
            for (int i5 = 0; i5 < size; i5++) {
                maybeUpdateSystemOverlays((String) arrayMap.keyAt(i5), (OverlayPaths) arrayMap.valueAt(i5));
            }
        }
        invalidatePackageInfoCache();
    }

    public static /* synthetic */ void lambda$setEnabledOverlayPackages$65(int i, ArrayMap arrayMap, Set set, int i2, ArrayMap arrayMap2, PackageStateMutator packageStateMutator) {
        for (int i3 = 0; i3 < i; i3++) {
            String str = (String) arrayMap.keyAt(i3);
            OverlayPaths overlayPaths = (OverlayPaths) arrayMap.valueAt(i3);
            if (set.contains(str)) {
                packageStateMutator.forPackage(str).userState(i2).setOverlayPaths(overlayPaths);
                ArrayMap arrayMap3 = (ArrayMap) arrayMap2.get(str);
                if (arrayMap3 != null) {
                    for (int i4 = 0; i4 < arrayMap3.size(); i4++) {
                        String str2 = (String) arrayMap3.keyAt(i4);
                        ArraySet arraySet = (ArraySet) arrayMap3.valueAt(i4);
                        for (int i5 = 0; i5 < arraySet.size(); i5++) {
                            packageStateMutator.forPackage((String) arraySet.valueAt(i5)).userState(i2).setOverlayPathsForLibrary(str2, overlayPaths);
                        }
                    }
                }
            }
        }
    }

    public final boolean canSetOverlayPaths(OverlayPaths overlayPaths, OverlayPaths overlayPaths2) {
        if (Objects.equals(overlayPaths, overlayPaths2)) {
            return false;
        }
        return ((overlayPaths == null && overlayPaths2.isEmpty()) || (overlayPaths2 == null && overlayPaths.isEmpty())) ? false : true;
    }

    public final void maybeUpdateSystemOverlays(String str, OverlayPaths overlayPaths) {
        if (!this.mResolverReplaced) {
            if (str.equals("android")) {
                if (overlayPaths == null) {
                    this.mPlatformPackageOverlayPaths = null;
                    this.mPlatformPackageOverlayResourceDirs = null;
                } else {
                    this.mPlatformPackageOverlayPaths = (String[]) overlayPaths.getOverlayPaths().toArray(new String[0]);
                    this.mPlatformPackageOverlayResourceDirs = (String[]) overlayPaths.getResourceDirs().toArray(new String[0]);
                }
                applyUpdatedSystemOverlayPaths();
                return;
            }
            return;
        }
        if (str.equals(this.mResolveActivity.applicationInfo.packageName)) {
            if (overlayPaths == null) {
                this.mReplacedResolverPackageOverlayPaths = null;
                this.mReplacedResolverPackageOverlayResourceDirs = null;
            } else {
                this.mReplacedResolverPackageOverlayPaths = (String[]) overlayPaths.getOverlayPaths().toArray(new String[0]);
                this.mReplacedResolverPackageOverlayResourceDirs = (String[]) overlayPaths.getResourceDirs().toArray(new String[0]);
            }
            applyUpdatedSystemOverlayPaths();
        }
    }

    public final void applyUpdatedSystemOverlayPaths() {
        ApplicationInfo applicationInfo = this.mAndroidApplication;
        if (applicationInfo == null) {
            Slog.i("PackageManager", "Skipped the AndroidApplication overlay paths update - no app yet");
        } else {
            applicationInfo.overlayPaths = this.mPlatformPackageOverlayPaths;
            applicationInfo.resourceDirs = this.mPlatformPackageOverlayResourceDirs;
        }
        if (this.mResolverReplaced) {
            ApplicationInfo applicationInfo2 = this.mResolveActivity.applicationInfo;
            applicationInfo2.overlayPaths = this.mReplacedResolverPackageOverlayPaths;
            applicationInfo2.resourceDirs = this.mReplacedResolverPackageOverlayResourceDirs;
        }
    }

    public final void enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.UPGRADE_RUNTIME_PERMISSIONS") == 0) {
            return;
        }
        throw new SecurityException(str + " requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY or android.permission.UPGRADE_RUNTIME_PERMISSIONS");
    }

    public PackageSetting getPackageSettingForMutation(String str) {
        return this.mSettings.getPackageLPr(str);
    }

    public PackageSetting getDisabledPackageSettingForMutation(String str) {
        return this.mSettings.getDisabledSystemPkgLPr(str);
    }

    public void forEachPackageSetting(Consumer consumer) {
        synchronized (this.mLock) {
            int size = this.mSettings.getPackagesLocked().size();
            for (int i = 0; i < size; i++) {
                consumer.accept((PackageSetting) this.mSettings.getPackagesLocked().valueAt(i));
            }
        }
    }

    public void forEachPackageState(Computer computer, Consumer consumer) {
        forEachPackageState(computer.getPackageStates(), consumer);
    }

    public void forEachPackage(Computer computer, Consumer consumer) {
        ArrayMap packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i);
            if (packageStateInternal.getPkg() != null) {
                consumer.accept(packageStateInternal.getPkg());
            }
        }
    }

    public final void forEachPackageState(ArrayMap arrayMap, Consumer consumer) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((PackageStateInternal) arrayMap.valueAt(i));
        }
    }

    public void forEachInstalledPackage(Computer computer, final Consumer consumer, final int i) {
        forEachPackageState(computer.getPackageStates(), new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda70
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageManagerService.lambda$forEachInstalledPackage$66(i, consumer, (PackageStateInternal) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$forEachInstalledPackage$66(int i, Consumer consumer, PackageStateInternal packageStateInternal) {
        if (packageStateInternal.getPkg() == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
            return;
        }
        consumer.accept(packageStateInternal.getPkg());
    }

    public boolean isHistoricalPackageUsageAvailable() {
        return this.mPackageUsage.isHistoricalPackageUsageAvailable();
    }

    public CompilerStats.PackageStats getOrCreateCompilerPackageStats(AndroidPackage androidPackage) {
        return getOrCreateCompilerPackageStats(androidPackage.getPackageName());
    }

    public CompilerStats.PackageStats getOrCreateCompilerPackageStats(String str) {
        return this.mCompilerStats.getOrCreatePackageStats(str);
    }

    public void grantImplicitAccess(Computer computer, int i, Intent intent, int i2, int i3, boolean z, boolean z2) {
        boolean grantImplicitAccess;
        AndroidPackage androidPackage = computer.getPackage(i3);
        int uid = UserHandle.getUid(i, i2);
        if (androidPackage == null || computer.getPackage(uid) == null) {
            return;
        }
        if (!computer.isInstantAppInternal(androidPackage.getPackageName(), i, i3)) {
            grantImplicitAccess = this.mAppsFilter.grantImplicitAccess(uid, i3, z2);
        } else if (!z) {
            return;
        } else {
            grantImplicitAccess = this.mInstantAppRegistry.grantInstantAccess(i, intent, i2, UserHandle.getAppId(i3));
        }
        if (grantImplicitAccess) {
            ApplicationPackageManager.invalidateGetPackagesForUidCache();
        }
    }

    public boolean canHaveOatDir(Computer computer, String str) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return false;
        }
        return AndroidPackageUtils.canHaveOatDir(packageStateInternal, packageStateInternal.getPkg());
    }

    public long deleteOatArtifactsOfPackage(Computer computer, String str) {
        PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can delete oat artifacts");
        if (DexOptHelper.useArtService()) {
            PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
            try {
                try {
                    try {
                        long freedBytes = DexOptHelper.getArtManagerLocal().deleteDexoptArtifacts(withFilteredSnapshot, str).getFreedBytes();
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        return freedBytes;
                    } catch (IllegalArgumentException e) {
                        Log.e("PackageManager", e.toString());
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        return -1L;
                    }
                } catch (IllegalStateException e2) {
                    Slog.wtfStack("PackageManager", e2.toString());
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                    return -1L;
                }
            } catch (Throwable th) {
                if (withFilteredSnapshot != null) {
                    try {
                        withFilteredSnapshot.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return -1L;
        }
        try {
            return this.mDexManager.deleteOptimizedFiles(ArtUtils.createArtPackageInfo(packageStateInternal.getPkg(), packageStateInternal));
        } catch (Installer.LegacyDexoptDisabledException e3) {
            throw new RuntimeException(e3);
        }
    }

    public List getMimeGroupInternal(Computer computer, String str, String str2) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return Collections.emptyList();
        }
        Map mimeGroups = packageStateInternal.getMimeGroups();
        Set set = mimeGroups != null ? (Set) mimeGroups.get(str2) : null;
        if (set == null) {
            throw new IllegalArgumentException("Unknown MIME group " + str2 + " for package " + str);
        }
        return new ArrayList(set);
    }

    public void writeSettingsLPrTEMP(boolean z) {
        snapshotComputer(false);
        this.mPermissionManager.writeLegacyPermissionsTEMP(this.mSettings.mPermissions);
        this.mSettings.writeLPr(this.mLiveComputer, z);
    }

    public void writeSettingsLPrTEMP() {
        writeSettingsLPrTEMP(false);
    }

    @Override // android.content.pm.TestUtilityService
    public void verifyHoldLockToken(IBinder iBinder) {
        if (!Build.IS_DEBUGGABLE) {
            throw new SecurityException("holdLock requires a debuggable build");
        }
        if (iBinder == null) {
            throw new SecurityException("null holdLockToken");
        }
        if (iBinder.queryLocalInterface("holdLock:" + Binder.getCallingUid()) != this) {
            throw new SecurityException("Invalid holdLock() token");
        }
    }

    public static String getDefaultTimeouts() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getString("package_manager_service", "incfs_default_timeouts", "");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static String getKnownDigestersList() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getString("package_manager_service", "known_digesters_list", "");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isPreapprovalRequestAvailable() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Resources.getSystem().getBoolean(17891733)) {
                return DeviceConfig.getBoolean("package_manager_service", "is_preapproval_available", true);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isUpdateOwnershipEnforcementAvailable() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getBoolean("package_manager_service", "is_update_ownership_enforcement_available", true);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public PerUidReadTimeouts[] getPerUidReadTimeouts(Computer computer) {
        PerUidReadTimeouts[] perUidReadTimeoutsArr = this.mPerUidReadTimeoutsCache;
        if (perUidReadTimeoutsArr != null) {
            return perUidReadTimeoutsArr;
        }
        PerUidReadTimeouts[] parsePerUidReadTimeouts = parsePerUidReadTimeouts(computer);
        this.mPerUidReadTimeoutsCache = parsePerUidReadTimeouts;
        return parsePerUidReadTimeouts;
    }

    public final PerUidReadTimeouts[] parsePerUidReadTimeouts(Computer computer) {
        List parseDigestersList = PerPackageReadTimeouts.parseDigestersList(getDefaultTimeouts(), getKnownDigestersList());
        if (parseDigestersList.size() == 0) {
            return EMPTY_PER_UID_READ_TIMEOUTS_ARRAY;
        }
        int[] userIds = this.mInjector.getUserManagerService().getUserIds();
        ArrayList arrayList = new ArrayList(parseDigestersList.size());
        int size = parseDigestersList.size();
        for (int i = 0; i < size; i++) {
            PerPackageReadTimeouts perPackageReadTimeouts = (PerPackageReadTimeouts) parseDigestersList.get(i);
            PackageStateInternal packageStateInternal = computer.getPackageStateInternal(perPackageReadTimeouts.packageName);
            if (packageStateInternal != null && packageStateInternal.getAppId() >= 10000) {
                AndroidPackageInternal pkg = packageStateInternal.getPkg();
                if (pkg.getLongVersionCode() >= perPackageReadTimeouts.versionCodes.minVersionCode && pkg.getLongVersionCode() <= perPackageReadTimeouts.versionCodes.maxVersionCode && (perPackageReadTimeouts.sha256certificate == null || pkg.getSigningDetails().hasSha256Certificate(perPackageReadTimeouts.sha256certificate))) {
                    for (int i2 : userIds) {
                        if (packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
                            int uid = UserHandle.getUid(i2, packageStateInternal.getAppId());
                            PerUidReadTimeouts perUidReadTimeouts = new PerUidReadTimeouts();
                            perUidReadTimeouts.uid = uid;
                            PerPackageReadTimeouts.Timeouts timeouts = perPackageReadTimeouts.timeouts;
                            perUidReadTimeouts.minTimeUs = timeouts.minTimeUs;
                            perUidReadTimeouts.minPendingTimeUs = timeouts.minPendingTimeUs;
                            perUidReadTimeouts.maxPendingTimeUs = timeouts.maxPendingTimeUs;
                            arrayList.add(perUidReadTimeouts);
                        }
                    }
                }
            }
        }
        return (PerUidReadTimeouts[]) arrayList.toArray(new PerUidReadTimeouts[arrayList.size()]);
    }

    public void setKeepUninstalledPackagesInternal(Computer computer, List list) {
        Preconditions.checkNotNull(list);
        synchronized (this.mKeepUninstalledPackages) {
            ArrayList arrayList = new ArrayList(this.mKeepUninstalledPackages);
            arrayList.removeAll(list);
            this.mKeepUninstalledPackages.clear();
            this.mKeepUninstalledPackages.addAll(list);
            for (int i = 0; i < arrayList.size(); i++) {
                deletePackageIfUnused(computer, (String) arrayList.get(i));
            }
        }
    }

    public boolean shouldKeepUninstalledPackageLPr(String str) {
        boolean contains;
        synchronized (this.mKeepUninstalledPackages) {
            contains = this.mKeepUninstalledPackages.contains(str);
        }
        return contains;
    }

    public boolean getSafeMode() {
        return this.mSafeMode;
    }

    public ComponentName getResolveComponentName() {
        return this.mResolveComponentName;
    }

    public DefaultAppProvider getDefaultAppProvider() {
        return this.mDefaultAppProvider;
    }

    public File getCacheDir() {
        return this.mCacheDir;
    }

    public PackageProperty getPackageProperty() {
        return this.mPackageProperty;
    }

    public WatchedArrayMap getInstrumentation() {
        return this.mInstrumentation;
    }

    public int getSdkVersion() {
        return this.mSdkVersion;
    }

    public void addAllPackageProperties(AndroidPackage androidPackage) {
        this.mPackageProperty.addAllProperties(androidPackage);
    }

    public void addInstrumentation(ComponentName componentName, ParsedInstrumentation parsedInstrumentation) {
        this.mInstrumentation.put(componentName, parsedInstrumentation);
    }

    public String[] getKnownPackageNamesInternal(Computer computer, int i, int i2) {
        return new KnownPackages(this.mDefaultAppProvider, this.mRequiredInstallerPackage, this.mRequiredUninstallerPackage, this.mSetupWizardPackage, this.mRequiredVerifierPackages, this.mDefaultTextClassifierPackage, this.mSystemTextClassifierPackageName, this.mRequiredPermissionControllerPackage, this.mConfiguratorPackage, this.mIncidentReportApproverPackage, this.mAmbientContextDetectionPackage, this.mWearableSensingPackage, this.mAppPredictionServicePackage, "com.android.companiondevicemanager", this.mRetailDemoPackage, this.mOverlayConfigSignaturePackage, this.mRecentsPackage).getKnownPackageNames(computer, i, i2);
    }

    public String getActiveLauncherPackageName(int i) {
        return this.mDefaultAppProvider.getDefaultHome(i);
    }

    public boolean setActiveLauncherPackage(String str, int i, Consumer consumer) {
        return this.mDefaultAppProvider.setDefaultHome(str, i, this.mContext.getMainExecutor(), consumer);
    }

    public void setDefaultBrowser(String str, boolean z, int i) {
        this.mDefaultAppProvider.setDefaultBrowser(str, z, i);
    }

    public PackageUsage getPackageUsage() {
        return this.mPackageUsage;
    }

    public String getModuleMetadataPackageName() {
        return this.mModuleInfoProvider.getPackageName();
    }

    public File getAppInstallDir() {
        return this.mAppInstallDir;
    }

    public boolean isExpectingBetter(String str) {
        return this.mInitAppsHelper.isExpectingBetter(str);
    }

    public int getDefParseFlags() {
        return this.mDefParseFlags;
    }

    public void setUpCustomResolverActivity(AndroidPackage androidPackage, PackageSetting packageSetting) {
        synchronized (this.mLock) {
            this.mResolverReplaced = true;
            ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, 0L, PackageUserStateInternal.DEFAULT, 0, packageSetting);
            ActivityInfo activityInfo = this.mResolveActivity;
            activityInfo.applicationInfo = generateApplicationInfo;
            activityInfo.name = this.mCustomResolverComponentName.getClassName();
            this.mResolveActivity.packageName = androidPackage.getPackageName();
            this.mResolveActivity.processName = androidPackage.getProcessName();
            ActivityInfo activityInfo2 = this.mResolveActivity;
            activityInfo2.launchMode = 0;
            activityInfo2.flags = 65824;
            activityInfo2.theme = 0;
            activityInfo2.exported = true;
            activityInfo2.enabled = true;
            ResolveInfo resolveInfo = this.mResolveInfo;
            resolveInfo.activityInfo = activityInfo2;
            resolveInfo.priority = 0;
            resolveInfo.preferredOrder = 0;
            resolveInfo.match = 0;
            this.mResolveComponentName = this.mCustomResolverComponentName;
            onChanged();
            Slog.i("PackageManager", "Replacing default ResolverActivity with custom activity: " + this.mResolveComponentName);
        }
    }

    public void setPlatformPackage(AndroidPackage androidPackage, PackageSetting packageSetting) {
        synchronized (this.mLock) {
            this.mPlatformPackage = androidPackage;
            ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, 0L, PackageUserStateInternal.DEFAULT, 0, packageSetting);
            this.mAndroidApplication = generateApplicationInfo;
            if (!this.mResolverReplaced) {
                ActivityInfo activityInfo = this.mResolveActivity;
                activityInfo.applicationInfo = generateApplicationInfo;
                activityInfo.name = ResolverActivity.class.getName();
                ActivityInfo activityInfo2 = this.mResolveActivity;
                activityInfo2.packageName = this.mAndroidApplication.packageName;
                activityInfo2.processName = "system:ui";
                activityInfo2.launchMode = 0;
                activityInfo2.documentLaunchMode = 3;
                activityInfo2.flags = 69664;
                activityInfo2.theme = R.style.Theme.Material.Dialog.Alert;
                activityInfo2.exported = true;
                activityInfo2.enabled = true;
                activityInfo2.resizeMode = 2;
                activityInfo2.configChanges = 3504;
                ResolveInfo resolveInfo = this.mResolveInfo;
                resolveInfo.activityInfo = activityInfo2;
                resolveInfo.priority = 0;
                resolveInfo.preferredOrder = 0;
                resolveInfo.match = 0;
                this.mResolveComponentName = new ComponentName(this.mAndroidApplication.packageName, this.mResolveActivity.name);
            }
            onChanged();
        }
        applyUpdatedSystemOverlayPaths();
    }

    public ApplicationInfo getCoreAndroidApplication() {
        return this.mAndroidApplication;
    }

    public boolean isSystemReady() {
        return this.mSystemReady;
    }

    public AndroidPackage getPlatformPackage() {
        return this.mPlatformPackage;
    }

    public boolean isPreNMR1Upgrade() {
        return this.mIsPreNMR1Upgrade;
    }

    public boolean isOverlayMutable(String str) {
        return this.mOverlayConfig.isMutable(str);
    }

    public int getSystemPackageScanFlags(File file) {
        ScanPartition scanPartition;
        List dirsToScanAsSystem = this.mInitAppsHelper.getDirsToScanAsSystem();
        int size = dirsToScanAsSystem.size();
        do {
            size--;
            if (size < 0) {
                return 65536;
            }
            scanPartition = (ScanPartition) dirsToScanAsSystem.get(size);
        } while (!scanPartition.containsFile(file));
        int i = 65536 | scanPartition.scanFlag;
        return scanPartition.containsPrivApp(file) ? i | IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES : i;
    }

    public Pair getSystemPackageRescanFlagsAndReparseFlags(File file, int i, int i2) {
        int i3;
        List dirsToScanAsSystem = this.mInitAppsHelper.getDirsToScanAsSystem();
        int size = dirsToScanAsSystem.size() - 1;
        while (true) {
            if (size < 0) {
                i2 = 0;
                i3 = 0;
                break;
            }
            ScanPartition scanPartition = (ScanPartition) dirsToScanAsSystem.get(size);
            if (scanPartition.containsPrivApp(file)) {
                i3 = 131072 | i | scanPartition.scanFlag;
                break;
            }
            if (scanPartition.containsApp(file)) {
                i3 = scanPartition.scanFlag | i;
                break;
            }
            size--;
        }
        return new Pair(Integer.valueOf(i3), Integer.valueOf(i2));
    }

    public PackageStateMutator.InitialState recordInitialState() {
        return this.mPackageStateMutator.initialState(this.mChangedPackagesTracker.getSequenceNumber());
    }

    public PackageStateMutator.Result commitPackageStateMutation(PackageStateMutator.InitialState initialState, Consumer consumer) {
        synchronized (this.mPackageStateWriteLock) {
            PackageStateMutator.Result generateResult = this.mPackageStateMutator.generateResult(initialState, this.mChangedPackagesTracker.getSequenceNumber());
            PackageStateMutator.Result result = PackageStateMutator.Result.SUCCESS;
            if (generateResult != result) {
                return generateResult;
            }
            consumer.accept(this.mPackageStateMutator);
            this.mPackageStateMutator.onFinished();
            onChanged();
            return result;
        }
    }

    public PackageStateMutator.Result commitPackageStateMutation(PackageStateMutator.InitialState initialState, String str, Consumer consumer) {
        PackageStateMutator.Result result = Thread.holdsLock(this.mPackageStateWriteLock) ? PackageStateMutator.Result.SUCCESS : null;
        synchronized (this.mPackageStateWriteLock) {
            if (result == null) {
                result = this.mPackageStateMutator.generateResult(initialState, this.mChangedPackagesTracker.getSequenceNumber());
            }
            PackageStateMutator.Result result2 = PackageStateMutator.Result.SUCCESS;
            if (result != result2) {
                return result;
            }
            PackageStateWrite forPackage = this.mPackageStateMutator.forPackage(str);
            if (forPackage == null) {
                return PackageStateMutator.Result.SPECIFIC_PACKAGE_NULL;
            }
            consumer.accept(forPackage);
            forPackage.onChanged();
            return result2;
        }
    }

    public void notifyInstantAppPackageInstalled(String str, int[] iArr) {
        this.mInstantAppRegistry.onPackageInstalled(snapshotComputer(), str, iArr);
    }

    public void addInstallerPackageName(InstallSource installSource) {
        synchronized (this.mLock) {
            this.mSettings.addInstallerPackageNames(installSource);
        }
    }

    public IPackageManagerImpl getIPackageManager() {
        return (IPackageManagerImpl) ServiceManager.getService("package");
    }

    public void reconcileSdkData(String str, String str2, List list, int i, int i2, int i3, String str3, int i4) {
        synchronized (this.mInstallLock) {
            ReconcileSdkDataArgs buildReconcileSdkDataArgs = Installer.buildReconcileSdkDataArgs(str, str2, list, i, i2, str3, i4);
            buildReconcileSdkDataArgs.previousAppId = i3;
            try {
                this.mInstaller.reconcileSdkData(buildReconcileSdkDataArgs);
            } catch (Installer.InstallerException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    public void deletePackageAsUserAndPersona(String str, IPackageDeleteObserver iPackageDeleteObserver, int i, int i2) {
        PersonaServiceHelper.deletePackageAsUserAndPersona(str, iPackageDeleteObserver, i, i2, this.mContext, this, this.mHandler, this.mSettings, false, false);
    }

    public boolean isPackageInstalled(String str) {
        synchronized (this.mLock) {
            return this.mPackages.containsKey(str);
        }
    }

    public boolean setPackageSettingInstalled(String str, boolean z, int i) {
        synchronized (this.mLock) {
            PackageSetting packageSetting = (PackageSetting) this.mSettings.mPackages.get(str);
            if (packageSetting == null) {
                return false;
            }
            packageSetting.setInstalled(z, i);
            return true;
        }
    }

    public int installExistingPackageForPersona(int i, String str) {
        boolean z;
        int i2;
        PackageSetting packageSetting;
        this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", null);
        Log.w("PackageManager", "verifying app can be installed or not for user - " + i);
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface != null) {
            synchronized (this.mLock) {
                packageSetting = (PackageSetting) this.mSettings.mPackages.get(str);
            }
            if (packageSetting != null) {
                try {
                    if (packageSetting.getPkg() != null && !asInterface.isApplicationInstallationEnabled(str, packageSetting.getPkg().getSigningDetails().getSignatures(), packageSetting.getPkg().getRequestedPermissions(), i)) {
                        Log.w("PackageManager", "This app installation is not allowed");
                        return -110;
                    }
                } catch (RemoteException unused) {
                }
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle bundle = new Bundle(1);
            synchronized (this.mLock) {
                PackageSetting packageSetting2 = (PackageSetting) this.mSettings.mPackages.get(str);
                if (packageSetting2 != null) {
                    if (packageSetting2.getInstalled(i)) {
                        z = false;
                    } else {
                        packageSetting2.setInstalled(true, i);
                        this.mSettings.writePackageRestrictionsLPr(i);
                        bundle.putInt("android.intent.extra.UID", UserHandle.getUid(i, packageSetting2.getAppId()));
                        z = true;
                    }
                    if (z) {
                        i2 = 1;
                        sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, new int[]{i}, null, null, null);
                    } else {
                        i2 = 1;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (PersonaServiceHelper.isSpfKnoxSupported()) {
                        Log.w("PackageManager", "installExistingPackageForPersona  packageName - " + str);
                        if (sPersonaManager.getSeparationConfigfromCache() != null && sPersonaManager.processAppSeparationInstallation(str) != i2) {
                            return -110;
                        }
                    }
                    return i2;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -3;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int deletePackageXForKnox(String str, int i, int i2) {
        PmLog.logDebugInfoAndLogcat("deletePackageXForKnox: pkg{" + str + "}, user{" + i + "}, flags{" + i2 + "}");
        return this.mDeletePackageHelper.deletePackageX(str, -1L, i, i2, true);
    }

    public PersonaManagerService getPersonaService() {
        return sPersonaManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public List getPackageListForDualDarPolicy(String str) {
        AndroidPackage androidPackage;
        AndroidPackage androidPackage2;
        ArrayMap packageStates = snapshotComputer().getPackageStates();
        ArrayList arrayList = new ArrayList();
        int size = packageStates.size();
        str.hashCode();
        int i = 0;
        char c = 65535;
        switch (str.hashCode()) {
            case -1695330962:
                if (str.equals("pkg_not_system")) {
                    c = 0;
                    break;
                }
                break;
            case -1228185402:
                if (str.equals("pkg_not_clearable_system")) {
                    c = 1;
                    break;
                }
                break;
            case -517416486:
                if (str.equals("pkg_sys")) {
                    c = 2;
                    break;
                }
                break;
            case 1141000794:
                if (str.equals("pkg_clearable_system")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                while (i < size) {
                    PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i);
                    if (packageStateInternal.getPkg() != null && packageStateInternal.getPackageName() != null && !packageStateInternal.isSystem()) {
                        arrayList.add(packageStateInternal.getPackageName());
                    }
                    i++;
                }
                break;
            case 1:
                while (i < size) {
                    PackageStateInternal packageStateInternal2 = (PackageStateInternal) packageStates.valueAt(i);
                    if (packageStateInternal2.getPkg() != null && packageStateInternal2.getPackageName() != null && packageStateInternal2.isSystem() && (androidPackage = (AndroidPackage) this.mPackages.get(packageStateInternal2.getPackageName())) != null && !androidPackage.isClearUserDataAllowed()) {
                        arrayList.add(packageStateInternal2.getPackageName());
                    }
                    i++;
                }
                break;
            case 2:
                while (i < size) {
                    PackageStateInternal packageStateInternal3 = (PackageStateInternal) packageStates.valueAt(i);
                    if (packageStateInternal3.getPkg() != null && packageStateInternal3.getPackageName() != null && packageStateInternal3.isSystem()) {
                        arrayList.add(packageStateInternal3.getPackageName());
                    }
                    i++;
                }
                break;
            case 3:
                while (i < size) {
                    PackageStateInternal packageStateInternal4 = (PackageStateInternal) packageStates.valueAt(i);
                    if (packageStateInternal4.getPkg() != null && packageStateInternal4.getPackageName() != null && packageStateInternal4.isSystem() && (androidPackage2 = (AndroidPackage) this.mPackages.get(packageStateInternal4.getPackageName())) != null && androidPackage2.isClearUserDataAllowed()) {
                        arrayList.add(packageStateInternal4.getPackageName());
                    }
                    i++;
                }
                break;
        }
        return arrayList;
    }

    public void overlaysInstallComplete(int i, int i2, InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str) {
        overlaysInstallComplete(i, i2, installLocaleOverlaysType, i3, str, null);
    }

    public void overlaysInstallComplete(int i, int i2, InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str, Runnable runnable) {
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
        }
        Slog.d("PackageManager", "overlaysInstallComplete() called with: token = [" + i + "], didLaunch = [" + i2 + "], type = [" + installLocaleOverlaysType + "], userId = [" + i3 + "], targetPackage = [" + str + "], timeoutRunnable = [" + runnable + "]");
        int i4 = AnonymousClass9.$SwitchMap$com$android$server$pm$PackageManagerService$InstallLocaleOverlaysType[installLocaleOverlaysType.ordinal()];
        if (i4 == 1) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(10, i, i2));
        } else if (i4 == 2) {
            this.mDeletePackageHelper.overlaysInstallComplete(i);
        } else {
            if (i4 != 3) {
                return;
            }
            Computer snapshotComputer = snapshotComputer();
            sendPackageAddedForUser(snapshotComputer, str, snapshotComputer.getPackageStateInternal(str), i3, 0);
        }
    }

    /* renamed from: com.android.server.pm.PackageManagerService$9 */
    /* loaded from: classes3.dex */
    public abstract /* synthetic */ class AnonymousClass9 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$pm$PackageManagerService$InstallLocaleOverlaysType;

        static {
            int[] iArr = new int[InstallLocaleOverlaysType.values().length];
            $SwitchMap$com$android$server$pm$PackageManagerService$InstallLocaleOverlaysType = iArr;
            try {
                iArr[InstallLocaleOverlaysType.PACKAGE_INSTALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$pm$PackageManagerService$InstallLocaleOverlaysType[InstallLocaleOverlaysType.PACKAGE_UNINSTALL_UPDATES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$server$pm$PackageManagerService$InstallLocaleOverlaysType[InstallLocaleOverlaysType.PACKAGE_ENABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public final void updateLocaleOverlaysForPackageEnabled(String str, int i) {
        updateLocaleOverlaysForPackage(-1, 0, InstallLocaleOverlaysType.PACKAGE_ENABLE, i, str);
    }

    public void updateLocaleOverlaysForUpdateRemovedPackage(int i, String str) {
        updateLocaleOverlaysForPackage(i, 0, InstallLocaleOverlaysType.PACKAGE_UNINSTALL_UPDATES, -1, str);
    }

    public void updateLocaleOverlaysForPackage(Message message) {
        Slog.d("PackageManager", "updateLocaleOverlaysForPackage() called with: msg = [" + message + "]");
        int i = message.arg1;
        int i2 = message.arg2;
        InstallRequest installRequest = (InstallRequest) this.mRunningInstalls.get(i);
        if (installRequest == null) {
            overlaysInstallComplete(i, i2, InstallLocaleOverlaysType.PACKAGE_INSTALL, -1, null);
        } else {
            updateLocaleOverlaysForPackage(i, i2, InstallLocaleOverlaysType.PACKAGE_INSTALL, -1, installRequest.getName());
        }
    }

    public final void updateLocaleOverlaysForPackage(int i, int i2, InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str) {
        if (str == null) {
            Slog.e("PackageManager", "updateLocaleOverlaysForPackage() called with null packageName");
            overlaysInstallComplete(i, i2, installLocaleOverlaysType, i3, str);
            return;
        }
        LocaleOverlayManagerWrapper localeOverlayManagerWrapper = LocaleOverlayManagerWrapper.getInstance(this.mContext);
        try {
            OverlayChangeObserverImpl overlayChangeObserverImpl = new OverlayChangeObserverImpl(i, i2, installLocaleOverlaysType, i3, str, this);
            this.mHandler.postDelayed(overlayChangeObserverImpl.mTimeoutRunnable, 30000L);
            localeOverlayManagerWrapper.applyLocalesForPackage(str, i, i3, overlayChangeObserverImpl);
        } catch (Exception e) {
            overlaysInstallComplete(i, i2, installLocaleOverlaysType, i3, str);
            e.printStackTrace();
        }
    }

    public boolean isLocaleOptimizedPackage(String str) {
        return isLocaleOptimizedPackage(str, 0);
    }

    public boolean isLocaleOptimizedPackage(String str, int i) {
        Bundle bundle;
        if (str == null) {
            return false;
        }
        ApplicationInfo applicationInfo = snapshotComputer().getApplicationInfo(str, 128L, i);
        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
            return true;
        }
        Slog.d("PackageManager", "isLocaleOptimizedPackage() -  Non optimized app: " + str + ". Proceed with normal install");
        return false;
    }
}

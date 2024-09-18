package android.app;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.app.LoadedApk;
import android.app.RemoteServiceException;
import android.app.admin.DevicePolicyManager;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.backup.BackupAgent;
import android.app.compat.CompatChanges;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ActivityRelaunchItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.CoreStatesChangeItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.ResumeActivityItem;
import android.app.servertransaction.TransactionExecutor;
import android.app.servertransaction.TransactionExecutorHelper;
import android.app.slice.Slice;
import android.bluetooth.BluetoothFrameworkInitializer;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ProviderInfoList;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDebug;
import android.ddm.DdmHandleAppName;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ExifInterface;
import android.media.MediaFrameworkInitializer;
import android.media.MediaFrameworkPlatformInitializer;
import android.media.MediaServiceManager;
import android.net.ConnectivityManager;
import android.net.Proxy;
import android.net.ProxyInfoWrapper;
import android.net.Uri;
import android.nfc.NfcFrameworkInitializer;
import android.nfc.NfcServiceManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.BluetoothServiceManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.StatsFrameworkInitializer;
import android.os.StatsServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.TelephonyServiceManager;
import android.os.Trace;
import android.os.UserHandle;
import android.permission.IPermissionManager;
import android.provider.BlockedNumberContract;
import android.provider.CallLog;
import android.provider.DeviceConfigInitializer;
import android.provider.DeviceConfigServiceManager;
import android.provider.Settings;
import android.renderscript.RenderScriptCacheDir;
import android.security.NetworkSecurityPolicy;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.telephony.TelephonyFrameworkInitializer;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.IContentCaptureOptionsCallback;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationSpec;
import android.webkit.WebView;
import android.window.ConfigurationHelper;
import android.window.SizeConfigurationBuckets;
import android.window.SplashScreen;
import android.window.SplashScreenView;
import android.window.WindowProviderService;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.SafeZipPathValidatorCallback;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.org.conscrypt.OpenSSLProvider;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.samsung.android.app.AbnormalUsage;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.ipm.SecIpmManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.share.SemShareConstants;
import com.samsung.ucm.keystore.KnoxUcmKeyStoreProvider;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import dalvik.system.ZipPathValidator;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider;
import java.security.Security;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import libcore.io.ForwardingOs;
import libcore.io.IoUtils;
import libcore.net.event.NetworkEventDispatcher;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class ActivityThread extends ClientTransactionHandler implements ActivityThreadInternal {
    private static final int ACTIVITY_THREAD_CHECKIN_VERSION = 4;
    private static final long CONTENT_PROVIDER_RETAIN_TIME = 1000;
    private static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_BROADCAST = false;
    public static final boolean DEBUG_CONFIGURATION = false;
    static final String DEBUG_LEVEL;
    static final boolean DEBUG_LEVEL_LOW;
    public static final boolean DEBUG_MEMORY_TRIM = false;
    static final boolean DEBUG_MESSAGES = false;
    public static final boolean DEBUG_ORDER = false;
    private static final boolean DEBUG_PROVIDER = false;
    private static final boolean DEBUG_RESULTS = false;
    private static final boolean DEBUG_SERVICE = false;
    private static final String DEFAULT_FULL_BACKUP_AGENT = "android.app.backup.FullBackupAgent";
    private static final String HEAP_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s";
    private static final String HEAP_FULL_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s";
    public static final long INVALID_PROC_STATE_SEQ = -1;
    private static final long MIN_TIME_BETWEEN_GCS = 5000;
    private static final String ONE_ALT_COUNT_COLUMN = "%21s %8s %21s %8d";
    private static final String ONE_COUNT_COLUMN = "%21s %8d";
    public static final String PROC_START_SEQ_IDENT = "seq=";
    private static final int REQUEST_DIRECT_ACTIONS_RETRY_MAX_COUNT = 7;
    private static final long REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS = 200;
    public static final int SERVICE_DONE_EXECUTING_ANON = 0;
    public static final int SERVICE_DONE_EXECUTING_START = 1;
    public static final int SERVICE_DONE_EXECUTING_STOP = 2;
    private static final int SQLITE_MEM_RELEASED_EVENT_LOG_TAG = 75003;
    public static final String TAG = "ActivityThread";
    private static final String THREE_COUNT_COLUMNS = "%21s %8d %21s %8s %21s %8d";
    private static final double THRESHOLD_FOR_HEAPDUMP = 0.96d;
    private static final String TWO_COUNT_COLUMNS = "%21s %8d %21s %8d";
    private static final String TWO_COUNT_COLUMN_HEADER = "%21s %8s %21s %8s";
    private static final int VM_PROCESS_STATE_JANK_IMPERCEPTIBLE = 1;
    private static final int VM_PROCESS_STATE_JANK_PERCEPTIBLE = 0;
    static final boolean localLOGV = false;
    private static volatile ActivityThread sCurrentActivityThread;
    private static final ThreadLocal<Intent> sCurrentBroadcastIntent;
    private static boolean sFixedAppContextDisplay;
    static volatile Handler sMainThreadHandler;
    static volatile IPackageManager sPackageManager;
    private static volatile IPermissionManager sPermissionManager;
    private static int sProcessDisplayId;
    private IdsController idsController;
    private AbnormalUsage mAbnormalUsage;
    final ArrayMap<IBinder, ActivityClientRecord> mActivities;
    final Map<IBinder, ClientTransactionItem> mActivitiesToBeDestroyed;
    final ArrayList<Application> mAllApplications;
    private final SparseArray<ArrayMap<String, BackupAgent>> mBackupAgentsByUser;
    AppBindData mBoundApplication;
    private CompatibilityInfo mCompatibilityInfo;
    Configuration mConfiguration;
    private ConfigurationController mConfigurationController;
    private IContentCaptureOptionsCallback.Stub mContentCaptureOptionsCallback;
    Bundle mCoreSettings;
    private final Object mCoreSettingsLock;
    int mCurDefaultDisplayDpi;
    boolean mDensityCompatMode;
    private SparseArray<ContextImpl> mDisplaySystemUiContexts;
    private float mDssScale;
    final Executor mExecutor;
    final GcIdler mGcIdler;
    boolean mGcIdlerScheduled;
    final ArrayMap<ProviderKey, ProviderKey> mGetProviderKeys;
    final H mH;
    Application mInitialApplication;
    Instrumentation mInstrumentation;
    String mInstrumentationAppDir;
    String mInstrumentationLibDir;
    String mInstrumentationPackageName;
    String[] mInstrumentationSplitAppDirs;
    String mInstrumentedAppDir;
    String mInstrumentedLibDir;
    String[] mInstrumentedSplitAppDirs;
    boolean mInstrumentingWithoutRestart;
    ArrayList<WeakReference<AssistStructure>> mLastAssistStructures;
    private int mLastProcessState;
    int mLastReportedDeviceId;
    private int mLastSessionId;
    final ArrayMap<IBinder, ProviderClientRecord> mLocalProviders;
    final ArrayMap<ComponentName, ProviderClientRecord> mLocalProvidersByName;
    private final List<MultiWindowCoreState.MultiWindowCoreStateListener> mMultiWindowCoreStateListeners;
    final ArrayList<ActivityClientRecord> mNewActivities;
    private final AtomicInteger mNumLaunchingActivities;
    int mNumVisibleActivities;
    final ArrayMap<Activity, ArrayList<OnActivityPausedListener>> mOnPauseListeners;
    final ArrayMap<String, WeakReference<LoadedApk>> mPackages;
    Configuration mPendingConfiguration;
    private final ArrayMap<IBinder, Configuration> mPendingOverrideConfigs;
    Profiler mProfiler;
    final ArrayMap<ProviderKey, ProviderClientRecord> mProviderMap;
    final ArrayMap<IBinder, ProviderRefCount> mProviderRefCountMap;
    final PurgeIdler mPurgeIdler;
    boolean mPurgeIdlerScheduled;
    final ArrayList<ActivityClientRecord> mRelaunchingActivities;
    private Map<SafeCancellationTransport, CancellationSignal> mRemoteCancellations;
    final ArrayMap<String, WeakReference<LoadedApk>> mResourcePackages;
    private final ResourcesManager mResourcesManager;
    final ArrayMap<IBinder, Service> mServices;
    final ArrayMap<IBinder, CreateServiceData> mServicesData;
    boolean mSomeActivitiesChanged;
    private SplashScreen.SplashScreenManagerGlobal mSplashScreenGlobal;
    private long mStartSeq;
    private ContextImpl mSystemContext;
    boolean mSystemThread;
    private final TransactionExecutor mTransactionExecutor;
    private boolean mUpdateHttpProxyOnBind;
    Handler trackingHandler;
    final HandlerThread trackingThread;
    public int webviewPreloadState;
    public boolean webviewPreloaded;
    private static final Bitmap.Config THUMBNAIL_FORMAT = Bitmap.Config.RGB_565;
    private static boolean mIsAnomalyDetected = false;
    private static boolean sDisableAID = false;
    private final Object mNetworkPolicyLock = new Object();
    private long mNetworkBlockSeq = -1;
    final ApplicationThread mAppThread = new ApplicationThread();
    final Looper mLooper = Looper.myLooper();

    private native void nInitZygoteChildHeapProfiling();

    private native void nPurgePendingResources();

    static {
        String str = SystemProperties.get("ro.boot.debug_level", "Unknown");
        DEBUG_LEVEL = str;
        DEBUG_LEVEL_LOW = "0x4f4c".equalsIgnoreCase(str);
        sProcessDisplayId = 0;
        sCurrentBroadcastIntent = new ThreadLocal<>();
        sFixedAppContextDisplay = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ProviderKey {
        final String authority;
        ContentProviderHolder mHolder;
        final Object mLock = new Object();
        final int userId;

        public ProviderKey(String authority, int userId) {
            this.authority = authority;
            this.userId = userId;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ProviderKey)) {
                return false;
            }
            ProviderKey other = (ProviderKey) o;
            return Objects.equals(this.authority, other.authority) && this.userId == other.userId;
        }

        public int hashCode() {
            String str = this.authority;
            return (str != null ? str.hashCode() : 0) ^ this.userId;
        }
    }

    /* loaded from: classes.dex */
    public static final class ActivityClientRecord {
        Activity activity;
        ViewRootImpl.ActivityConfigCallback activityConfigCallback;
        ActivityInfo activityInfo;
        public IBinder assistToken;
        CompatibilityInfo compatInfo;
        Configuration createdConfig;
        String embeddedID;
        boolean hideForNow;
        int ident;
        Intent intent;
        public final boolean isForward;
        boolean isTopResumedActivity;
        Activity.NonConfigurationInstances lastNonConfigurationInstances;
        boolean lastReportedTopResumedState;
        ActivityOptions mActivityOptions;
        boolean mIsUserLeaving;
        int mLastReportedWindowingMode;
        boolean mLaunchedFromBubble;
        private int mLifecycleState;
        Window mPendingRemoveWindow;
        WindowManager mPendingRemoveWindowManager;
        boolean mPreserveWindow;
        private SizeConfigurationBuckets mSizeConfigurations;
        public IBinder mTaskFragmentToken;
        Configuration overrideConfig;
        public LoadedApk packageInfo;
        Activity parent;
        boolean paused;
        int pendingConfigChanges;
        List<ReferrerIntent> pendingIntents;
        List<ResultInfo> pendingResults;
        PersistableBundle persistentState;
        ProfilerInfo profilerInfo;
        String referrer;
        public IBinder shareableActivityToken;
        boolean startsNotResumed;
        Bundle state;
        boolean stopped;
        private Configuration tmpConfig;
        public IBinder token;
        IVoiceInteractor voiceInteractor;
        Window window;

        public ActivityClientRecord() {
            this.tmpConfig = new Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLifecycleState = 0;
            this.isForward = false;
            init();
        }

        public ActivityClientRecord(IBinder token, Intent intent, int ident, ActivityInfo info, Configuration overrideConfig, String referrer, IVoiceInteractor voiceInteractor, Bundle state, PersistableBundle persistentState, List<ResultInfo> pendingResults, List<ReferrerIntent> pendingNewIntents, ActivityOptions activityOptions, boolean isForward, ProfilerInfo profilerInfo, ClientTransactionHandler client, IBinder assistToken, IBinder shareableActivityToken, boolean launchedFromBubble, IBinder taskFragmentToken) {
            this.tmpConfig = new Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLifecycleState = 0;
            this.token = token;
            this.assistToken = assistToken;
            this.shareableActivityToken = shareableActivityToken;
            this.ident = ident;
            this.intent = intent;
            this.referrer = referrer;
            this.voiceInteractor = voiceInteractor;
            this.activityInfo = info;
            this.state = state;
            this.persistentState = persistentState;
            this.pendingResults = pendingResults;
            this.pendingIntents = pendingNewIntents;
            this.isForward = isForward;
            this.profilerInfo = profilerInfo;
            this.overrideConfig = overrideConfig;
            this.packageInfo = client.getPackageInfoNoCheck(info.applicationInfo);
            this.mActivityOptions = activityOptions;
            this.mLaunchedFromBubble = launchedFromBubble;
            this.mTaskFragmentToken = taskFragmentToken;
            init();
        }

        private void init() {
            this.parent = null;
            this.embeddedID = null;
            this.paused = false;
            this.stopped = false;
            this.hideForNow = false;
            this.activityConfigCallback = new ViewRootImpl.ActivityConfigCallback() { // from class: android.app.ActivityThread.ActivityClientRecord.1
                @Override // android.view.ViewRootImpl.ActivityConfigCallback
                public void onConfigurationChanged(Configuration overrideConfig, int newDisplayId) {
                    if (ActivityClientRecord.this.activity == null) {
                        throw new IllegalStateException("Received config update for non-existing activity");
                    }
                    ActivityClientRecord.this.activity.mMainThread.handleActivityConfigurationChanged(ActivityClientRecord.this, overrideConfig, newDisplayId, false);
                }

                @Override // android.view.ViewRootImpl.ActivityConfigCallback
                public void requestCompatCameraControl(boolean showControl, boolean transformationApplied, ICompatCameraControlCallback callback) {
                    if (ActivityClientRecord.this.activity == null) {
                        throw new IllegalStateException("Received camera compat control update for non-existing activity");
                    }
                    ActivityClient.getInstance().requestCompatCameraControl(ActivityClientRecord.this.activity.getResources(), ActivityClientRecord.this.token, showControl, transformationApplied, callback);
                }
            };
        }

        public int getLifecycleState() {
            return this.mLifecycleState;
        }

        public void setState(int newLifecycleState) {
            this.mLifecycleState = newLifecycleState;
            switch (newLifecycleState) {
                case 1:
                    this.paused = true;
                    this.stopped = true;
                    return;
                case 2:
                    this.paused = true;
                    this.stopped = false;
                    return;
                case 3:
                    this.paused = false;
                    this.stopped = false;
                    return;
                case 4:
                    this.paused = true;
                    this.stopped = false;
                    return;
                case 5:
                    this.paused = true;
                    this.stopped = true;
                    return;
                default:
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreHoneycomb() {
            Activity activity = this.activity;
            return activity != null && activity.getApplicationInfo().targetSdkVersion < 11;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreP() {
            Activity activity = this.activity;
            return activity != null && activity.getApplicationInfo().targetSdkVersion < 28;
        }

        public boolean isPersistable() {
            return this.activityInfo.persistableMode == 2;
        }

        public boolean isVisibleFromServer() {
            Activity activity = this.activity;
            return activity != null && activity.mVisibleFromServer;
        }

        public String toString() {
            Intent intent = this.intent;
            ComponentName componentName = intent != null ? intent.getComponent() : null;
            return "ActivityRecord{" + Integer.toHexString(System.identityHashCode(this)) + " token=" + this.token + " " + (componentName == null ? "no component name" : componentName.toShortString()) + "}";
        }

        public String getStateString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ActivityClientRecord{");
            sb.append("paused=").append(this.paused);
            sb.append(", stopped=").append(this.stopped);
            sb.append(", hideForNow=").append(this.hideForNow);
            sb.append(", startsNotResumed=").append(this.startsNotResumed);
            sb.append(", isForward=").append(this.isForward);
            sb.append(", pendingConfigChanges=").append(this.pendingConfigChanges);
            sb.append(", preserveWindow=").append(this.mPreserveWindow);
            if (this.activity != null) {
                sb.append(", Activity{");
                sb.append("resumed=").append(this.activity.mResumed);
                sb.append(", stopped=").append(this.activity.mStopped);
                sb.append(", finished=").append(this.activity.isFinishing());
                sb.append(", destroyed=").append(this.activity.isDestroyed());
                sb.append(", startedActivity=").append(this.activity.mStartedActivity);
                sb.append(", changingConfigurations=").append(this.activity.mChangingConfigurations);
                sb.append("}");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class ProviderClientRecord {
        final ContentProviderHolder mHolder;
        final ContentProvider mLocalProvider;
        final String[] mNames;
        final IContentProvider mProvider;

        ProviderClientRecord(String[] names, IContentProvider provider, ContentProvider localProvider, ContentProviderHolder holder) {
            this.mNames = names;
            this.mProvider = provider;
            this.mLocalProvider = localProvider;
            this.mHolder = holder;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class ReceiverData extends BroadcastReceiver.PendingResult {
        CompatibilityInfo compatInfo;
        ActivityInfo info;
        Intent intent;

        public ReceiverData(Intent intent, int resultCode, String resultData, Bundle resultExtras, boolean ordered, boolean sticky, boolean assumeDelivered, IBinder token, int sendingUser, int sendingUid, String sendingPackage) {
            super(resultCode, resultData, resultExtras, 0, ordered, sticky, assumeDelivered, token, sendingUser, intent.getFlags(), sendingUid, sendingPackage);
            this.intent = intent;
        }

        public String toString() {
            return "ReceiverData{intent=" + this.intent + " packageName=" + this.info.packageName + " resultCode=" + getResultCode() + " resultData=" + getResultData() + " resultExtras=" + getResultExtras(false) + " sentFromUid=" + getSentFromUid() + " sentFromPackage=" + getSentFromPackage() + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class CreateBackupAgentData {
        ApplicationInfo appInfo;
        int backupDestination;
        int backupMode;
        int userId;

        CreateBackupAgentData() {
        }

        public String toString() {
            return "CreateBackupAgentData{appInfo=" + this.appInfo + " backupAgent=" + this.appInfo.backupAgentName + " mode=" + this.backupMode + " userId=" + this.userId + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class CreateServiceData {
        CompatibilityInfo compatInfo;
        ServiceInfo info;
        Intent intent;
        IBinder token;

        CreateServiceData() {
        }

        public String toString() {
            return "CreateServiceData{token=" + this.token + " className=" + this.info.name + " packageName=" + this.info.packageName + " intent=" + this.intent + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class BindServiceData {
        long bindSeq;
        Intent intent;
        boolean rebind;
        IBinder token;

        BindServiceData() {
        }

        public String toString() {
            return "BindServiceData{token=" + this.token + " intent=" + this.intent + " bindSeq=" + this.bindSeq + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class ServiceArgsData {
        Intent args;
        int flags;
        int startId;
        boolean taskRemoved;
        IBinder token;

        ServiceArgsData() {
        }

        public String toString() {
            return "ServiceArgsData{token=" + this.token + " startId=" + this.startId + " args=" + this.args + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class AppBindData {
        ApplicationInfo appInfo;
        AutofillOptions autofillOptions;
        String buildSerial;
        CompatibilityInfo compatInfo;
        Configuration config;
        ContentCaptureOptions contentCaptureOptions;
        int debugMode;
        long[] disabledCompatChanges;
        float dssScale;
        boolean enableBinderTracking;
        LoadedApk info;
        ProfilerInfo initProfilerInfo;
        Bundle instrumentationArgs;
        ComponentName instrumentationName;
        IUiAutomationConnection instrumentationUiAutomationConnection;
        IInstrumentationWatcher instrumentationWatcher;
        SharedMemory mSerializedSystemFontMap;
        boolean persistent;
        String processName;
        List<ProviderInfo> providers;
        boolean restrictedBackupMode;
        String sdkSandboxClientAppPackage;
        String sdkSandboxClientAppVolumeUuid;
        long startRequestedElapsedTime;
        long startRequestedUptime;
        boolean trackAllocation;

        AppBindData() {
        }

        public String toString() {
            return "AppBindData{appInfo=" + this.appInfo + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Profiler {
        boolean autoStopProfiler;
        boolean handlingProfiling;
        int mClockType;
        ParcelFileDescriptor profileFd;
        String profileFile;
        boolean profiling;
        int samplingInterval;
        boolean streamingOutput;

        Profiler() {
        }

        public void setProfiler(ProfilerInfo profilerInfo) {
            ParcelFileDescriptor fd = profilerInfo.profileFd;
            if (this.profiling) {
                if (fd != null) {
                    try {
                        fd.close();
                        return;
                    } catch (IOException e) {
                        return;
                    }
                }
                return;
            }
            ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e2) {
                }
            }
            this.profileFile = profilerInfo.profileFile;
            this.profileFd = fd;
            this.samplingInterval = profilerInfo.samplingInterval;
            this.autoStopProfiler = profilerInfo.autoStopProfiler;
            this.streamingOutput = profilerInfo.streamingOutput;
            this.mClockType = profilerInfo.clockType;
        }

        public void startProfiling() {
            if (this.profileFd == null || this.profiling) {
                return;
            }
            try {
                int bufferSize = SystemProperties.getInt("debug.traceview-buffer-size-mb", 8);
                String str = this.profileFile;
                FileDescriptor fileDescriptor = this.profileFd.getFileDescriptor();
                int i = bufferSize * 1024 * 1024;
                int i2 = this.mClockType;
                int i3 = this.samplingInterval;
                VMDebug.startMethodTracing(str, fileDescriptor, i, i2, i3 != 0, i3, this.streamingOutput);
                this.profiling = true;
            } catch (RuntimeException e) {
                Slog.w(ActivityThread.TAG, "Profiling failed on path " + this.profileFile, e);
                try {
                    this.profileFd.close();
                    this.profileFd = null;
                } catch (IOException e2) {
                    Slog.w(ActivityThread.TAG, "Failure closing profile fd", e2);
                }
            }
        }

        public void stopProfiling() {
            if (this.profiling) {
                this.profiling = false;
                Debug.stopMethodTracing();
                ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                    }
                }
                this.profileFd = null;
                this.profileFile = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class DumpComponentInfo {
        String[] args;
        ParcelFileDescriptor fd;
        String prefix;
        IBinder token;

        DumpComponentInfo() {
        }
    }

    /* loaded from: classes.dex */
    static final class ContextCleanupInfo {
        ContextImpl context;
        String what;
        String who;

        ContextCleanupInfo() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class DumpHeapData {
        ParcelFileDescriptor fd;
        RemoteCallback finishCallback;
        public boolean mallocInfo;
        public boolean managed;
        String path;
        public boolean runGc;

        DumpHeapData() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class DumpResourcesData {
        public ParcelFileDescriptor fd;
        public RemoteCallback finishCallback;

        DumpResourcesData() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class UpdateCompatibilityData {
        CompatibilityInfo info;
        String pkg;

        UpdateCompatibilityData() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class RequestAssistContextExtras {
        IBinder activityToken;
        int flags;
        IBinder requestToken;
        int requestType;
        int sessionId;

        RequestAssistContextExtras() {
        }
    }

    /* loaded from: classes.dex */
    static final class ReceiverList {
        int index;
        List<ReceiverInfo> receivers;

        ReceiverList() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ApplicationThread extends IApplicationThread.Stub {
        private static final String DB_CONNECTION_INFO_FORMAT = "  %8s %8s %14s %5d %5d %5d  %s";
        private static final String DB_CONNECTION_INFO_HEADER = "  %8s %8s %14s %5s %5s %5s  %s";
        private static final String DB_POOL_INFO_FORMAT = "  %13d %13d %13d  %s";
        private static final String DB_POOL_INFO_HEADER = "  %13s %13s %13s  %s";
        static final int START_SABINDER_TRACKING = 4;
        static final int STOP_SABINDER_TRACKING_AND_DUMP = 5;

        private ApplicationThread() {
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean ordered, boolean assumeDelivered, int sendingUser, int processState, int sendingUid, String sendingPackage) {
            ActivityThread.this.updateProcessState(processState, false);
            ReceiverData r = new ReceiverData(intent, resultCode, data, extras, ordered, false, assumeDelivered, ActivityThread.this.mAppThread.asBinder(), sendingUser, sendingUid, sendingPackage);
            r.info = info;
            ActivityThread.this.sendMessage(113, r);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiverList(List<ReceiverInfo> info) throws RemoteException {
            for (int i = 0; i < info.size(); i++) {
                ReceiverInfo r = info.get(i);
                if (r.registered) {
                    scheduleRegisteredReceiver(r.receiver, r.intent, r.resultCode, r.data, r.extras, r.ordered, r.sticky, r.assumeDelivered, r.sendingUser, r.processState, r.sendingUid, r.sendingPackage);
                } else {
                    scheduleReceiver(r.intent, r.activityInfo, r.compatInfo, r.resultCode, r.data, r.extras, r.sync, r.assumeDelivered, r.sendingUser, r.processState, r.sendingUid, r.sendingPackage);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateBackupAgent(ApplicationInfo app, int backupMode, int userId, int backupDestination) {
            CreateBackupAgentData d = new CreateBackupAgentData();
            d.appInfo = app;
            d.backupMode = backupMode;
            d.userId = userId;
            d.backupDestination = backupDestination;
            ActivityThread.this.sendMessage(128, d);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleDestroyBackupAgent(ApplicationInfo app, int userId) {
            CreateBackupAgentData d = new CreateBackupAgentData();
            d.appInfo = app;
            d.userId = userId;
            ActivityThread.this.sendMessage(129, d);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) {
            ActivityThread.this.updateProcessState(processState, false);
            CreateServiceData s = new CreateServiceData();
            s.token = token;
            s.info = info;
            ActivityThread.this.sendMessage(114, s);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState, long bindSeq) {
            ActivityThread.this.updateProcessState(processState, false);
            BindServiceData s = new BindServiceData();
            s.token = token;
            s.intent = intent;
            s.rebind = rebind;
            s.bindSeq = bindSeq;
            ActivityThread.this.sendMessage(121, s);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleUnbindService(IBinder token, Intent intent) {
            BindServiceData s = new BindServiceData();
            s.token = token;
            s.intent = intent;
            s.bindSeq = -1L;
            ActivityThread.this.sendMessage(122, s);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleServiceArgs(IBinder token, ParceledListSlice args) {
            List<ServiceStartArgs> list = args.getList();
            for (int i = 0; i < list.size(); i++) {
                ServiceStartArgs ssa = list.get(i);
                ServiceArgsData s = new ServiceArgsData();
                s.token = token;
                s.taskRemoved = ssa.taskRemoved;
                s.startId = ssa.startId;
                s.flags = ssa.flags;
                s.args = ssa.args;
                ActivityThread.this.sendMessage(115, s);
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleStopService(IBinder token) {
            ActivityThread.this.sendMessage(116, token);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleTimeoutService(IBinder token, int startId) {
            ActivityThread.this.sendMessage(167, token, startId);
        }

        @Override // android.app.IApplicationThread
        public final void schedulePing(RemoteCallback pong) {
            ActivityThread.this.sendMessage(168, pong);
        }

        @Override // android.app.IApplicationThread
        public final void bindApplication(String processName, ApplicationInfo appInfo, String sdkSandboxClientAppVolumeUuid, String sdkSandboxClientAppPackage, ProviderInfoList providerList, ComponentName instrumentationName, ProfilerInfo profilerInfo, Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean isRestrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, float dssScale, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] disabledCompatChanges, SharedMemory serializedSystemFontMap, long startRequestedElapsedTime, long startRequestedUptime, int processDisplayId, boolean isFixedAppContextDisplay) {
            if (services != null) {
                ServiceManager.initServiceCache(services);
            }
            Bundle states = coreSettings.getBundle(MultiWindowCoreState.TAG);
            ClientTransaction clientTransaction = ClientTransaction.obtain(ActivityThread.this.mAppThread, null);
            clientTransaction.addCallback(CoreStatesChangeItem.obtain(states));
            try {
                ActivityThread.this.mAppThread.scheduleTransaction(clientTransaction);
            } catch (RemoteException e) {
                Slog.w(ActivityThread.TAG, e.getMessage());
            }
            coreSettings.remove(MultiWindowCoreState.TAG);
            ActivityThread.sProcessDisplayId = processDisplayId;
            ActivityThread.sFixedAppContextDisplay = isFixedAppContextDisplay;
            setCoreSettings(coreSettings);
            AppBindData data = new AppBindData();
            data.processName = processName;
            data.appInfo = appInfo;
            data.sdkSandboxClientAppVolumeUuid = sdkSandboxClientAppVolumeUuid;
            data.sdkSandboxClientAppPackage = sdkSandboxClientAppPackage;
            data.providers = providerList.getList();
            data.instrumentationName = instrumentationName;
            data.instrumentationArgs = instrumentationArgs;
            data.instrumentationWatcher = instrumentationWatcher;
            data.instrumentationUiAutomationConnection = instrumentationUiConnection;
            data.debugMode = debugMode;
            data.enableBinderTracking = enableBinderTracking;
            data.trackAllocation = trackAllocation;
            data.restrictedBackupMode = isRestrictedBackupMode;
            data.persistent = persistent;
            data.config = config;
            data.compatInfo = compatInfo;
            data.initProfilerInfo = profilerInfo;
            data.buildSerial = buildSerial;
            data.autofillOptions = autofillOptions;
            data.contentCaptureOptions = contentCaptureOptions;
            data.disabledCompatChanges = disabledCompatChanges;
            data.mSerializedSystemFontMap = serializedSystemFontMap;
            data.startRequestedElapsedTime = startRequestedElapsedTime;
            data.startRequestedUptime = startRequestedUptime;
            updateCompatOverrideScale(compatInfo);
            CompatibilityInfo.applyOverrideScaleIfNeeded(config);
            ActivityThread.this.sendMessage(110, data);
        }

        private void updateCompatOverrideScale(CompatibilityInfo info) {
            CompatibilityInfo.setOverrideInvertedScale(info.hasOverrideScaling() ? info.applicationInvertedScale : 1.0f);
        }

        @Override // android.app.IApplicationThread
        public final void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = entryPoint;
            args.arg2 = entryPointArgs;
            ActivityThread.this.sendMessage(158, args);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleExit() {
            ActivityThread.this.sendMessage(111, null);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleSuicide() {
            ActivityThread.this.sendMessage(130, null);
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo ai) {
            ActivityThread.this.mResourcesManager.appendPendingAppInfoUpdate(new String[]{ai.sourceDir}, ai);
            ActivityThread.this.mH.removeMessages(156, ai);
            ActivityThread.this.sendMessage(156, ai);
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() {
            TimeZone.setDefault(null);
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() {
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().dispatchNetworkConfigurationChange();
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() {
            synchronized (ActivityThread.this) {
                Application app = ActivityThread.this.getApplication();
                if (app == null) {
                    ActivityThread.this.mUpdateHttpProxyOnBind = true;
                } else {
                    ActivityThread.updateHttpProxy(app);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void setHttpProxyInfo(ProxyInfoWrapper wrapper) {
            Proxy.setHttpProxySystemProperty(wrapper.getProxyInfo());
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() {
            ActivityThread.this.mH.removeMessages(120);
            ActivityThread.this.mH.sendMessage(ActivityThread.this.mH.obtainMessage(120));
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor pfd, IBinder servicetoken, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                try {
                    data.fd = pfd.dup();
                    data.token = servicetoken;
                    data.args = args;
                    ActivityThread.this.sendMessage(123, data, 0, 0, true);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpService failed", e);
                }
            } finally {
                IoUtils.closeQuietly(pfd);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String dataStr, Bundle extras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int processState, int sendingUid, String sendingPackage) throws RemoteException {
            ActivityThread.this.updateProcessState(processState, false);
            if (receiver instanceof LoadedApk.ReceiverDispatcher.InnerReceiver) {
                ((LoadedApk.ReceiverDispatcher.InnerReceiver) receiver).performReceive(intent, resultCode, dataStr, extras, ordered, sticky, assumeDelivered, sendingUser, sendingUid, sendingPackage);
                return;
            }
            if (!assumeDelivered) {
                Log.wtf(ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + receiver + " and " + intent + " without mechanism to finish delivery");
            }
            if (sendingUid != -1 || sendingPackage != null) {
                Log.wtf(ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + receiver + " and " + intent + " from " + sendingPackage + " (UID: " + sendingUid + ") without mechanism to propagate the sender's identity");
            }
            receiver.performReceive(intent, resultCode, dataStr, extras, ordered, sticky, sendingUser);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() {
            ActivityThread.this.sendMessage(124, null);
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) {
            ActivityThread.this.sendMessage(127, profilerInfo, z ? 1 : 0, i);
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) {
            DumpHeapData dhd = new DumpHeapData();
            dhd.managed = managed;
            dhd.mallocInfo = mallocInfo;
            dhd.runGc = runGc;
            dhd.path = path;
            try {
                try {
                    dhd.fd = fd.dup();
                    IoUtils.closeQuietly(fd);
                    dhd.finishCallback = finishCallback;
                    ActivityThread.this.sendMessage(135, dhd, 0, 0, true);
                } catch (IOException e) {
                    Slog.e(ActivityThread.TAG, "Failed to duplicate heap dump file descriptor", e);
                    IoUtils.closeQuietly(fd);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(fd);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String agent) {
            ActivityThread.this.sendMessage(155, agent);
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(String dataDir) {
            ActivityThread.this.sendMessage(162, dataDir);
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int group) {
            try {
                Process.setProcessGroup(Process.myPid(), group);
            } catch (Exception e) {
                Slog.w(ActivityThread.TAG, "Failed setting process group to " + group, e);
            }
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int cmd, String[] packages) {
            ActivityThread.this.sendMessage(133, packages, cmd);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String msg, int typeId, Bundle extras) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = msg;
            args.arg2 = extras;
            ActivityThread.this.sendMessage(134, args, typeId);
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(ParcelFileDescriptor fd, RemoteCallback callback) {
            DumpResourcesData data = new DumpResourcesData();
            try {
                try {
                    data.fd = fd.dup();
                    data.finishCallback = callback;
                    ActivityThread.this.sendMessage(166, data, 0, 0, false);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpResources failed", e);
                }
            } finally {
                IoUtils.closeQuietly(fd);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor pfd, IBinder activitytoken, String prefix, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                try {
                    data.fd = pfd.dup();
                    data.token = activitytoken;
                    data.prefix = prefix;
                    data.args = args;
                    ActivityThread.this.sendMessage(136, data, 0, 0, true);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpActivity failed", e);
                }
            } finally {
                IoUtils.closeQuietly(pfd);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor pfd, IBinder providertoken, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                try {
                    data.fd = pfd.dup();
                    data.token = providertoken;
                    data.args = args;
                    ActivityThread.this.sendMessage(141, data, 0, 0, true);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpProvider failed", e);
                }
            } finally {
                IoUtils.closeQuietly(pfd);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(ParcelFileDescriptor pfd, Debug.MemoryInfo mem, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) {
            FileOutputStream fout = new FileOutputStream(pfd.getFileDescriptor());
            PrintWriter pw = new FastPrintWriter(fout);
            try {
                dumpMemInfo(pw, mem, checkin, dumpFullInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable);
            } finally {
                pw.flush();
                IoUtils.closeQuietly(pfd);
            }
        }

        private void dumpMemInfo(PrintWriter pw, Debug.MemoryInfo memInfo, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable) {
            long viewRootInstanceCount;
            long nativeMax = Debug.getNativeHeapSize() / 1024;
            long nativeAllocated = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeFree = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long dalvikMax = runtime.totalMemory() / 1024;
            long dalvikFree = runtime.freeMemory() / 1024;
            long dalvikAllocated = dalvikMax - dalvikFree;
            Class[] classesToCount = {ContextImpl.class, Activity.class, WebView.class, View.class, ViewRootImpl.class};
            long[] instanceCounts = VMDebug.countInstancesOfClasses(classesToCount, true);
            long appContextInstanceCount = instanceCounts[0];
            long activityInstanceCount = instanceCounts[1];
            long webviewInstanceCount = instanceCounts[2];
            long viewInstanceCount = instanceCounts[3];
            long viewRootInstanceCount2 = instanceCounts[4];
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = Debug.getBinderDeathObjectCount();
            long parcelSize = Parcel.getGlobalAllocSize();
            long parcelCount = Parcel.getGlobalAllocCount();
            SQLiteDebug.PagerStats stats = SQLiteDebug.getDatabaseInfo();
            ActivityThread.dumpMemInfoTable(pw, memInfo, checkin, dumpFullInfo, dumpDalvik, dumpSummaryOnly, Process.myPid(), ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown", nativeMax, nativeAllocated, nativeFree, dalvikMax, dalvikAllocated, dalvikFree);
            if (checkin) {
                pw.print(viewInstanceCount);
                pw.print(',');
                pw.print(viewRootInstanceCount2);
                pw.print(',');
                pw.print(appContextInstanceCount);
                pw.print(',');
                pw.print(activityInstanceCount);
                pw.print(',');
                pw.print(globalAssetCount);
                pw.print(',');
                pw.print(globalAssetManagerCount);
                pw.print(',');
                int binderLocalObjectCount2 = binderLocalObjectCount;
                pw.print(binderLocalObjectCount2);
                pw.print(',');
                int binderProxyObjectCount2 = binderProxyObjectCount;
                pw.print(binderProxyObjectCount2);
                pw.print(',');
                int binderDeathObjectCount2 = binderDeathObjectCount;
                pw.print(binderDeathObjectCount2);
                pw.print(',');
                SQLiteDebug.PagerStats stats2 = stats;
                pw.print(stats2.memoryUsed / 1024);
                pw.print(',');
                pw.print(stats2.memoryUsed / 1024);
                pw.print(',');
                pw.print(stats2.pageCacheOverflow / 1024);
                pw.print(',');
                pw.print(stats2.largestMemAlloc / 1024);
                int i = 0;
                while (true) {
                    int binderDeathObjectCount3 = binderDeathObjectCount2;
                    if (i < stats2.dbStats.size()) {
                        SQLiteDebug.DbStats dbStats = stats2.dbStats.get(i);
                        pw.print(',');
                        pw.print(dbStats.dbName);
                        pw.print(',');
                        pw.print(dbStats.pageSize);
                        pw.print(',');
                        pw.print(dbStats.dbSize);
                        pw.print(',');
                        pw.print(dbStats.lookaside);
                        pw.print(',');
                        pw.print(dbStats.cacheHits);
                        pw.print(',');
                        pw.print(dbStats.cacheMisses);
                        pw.print(',');
                        pw.print(dbStats.cacheSize);
                        i++;
                        binderProxyObjectCount2 = binderProxyObjectCount2;
                        binderLocalObjectCount2 = binderLocalObjectCount2;
                        binderDeathObjectCount2 = binderDeathObjectCount3;
                        stats2 = stats2;
                    } else {
                        pw.println();
                        return;
                    }
                }
            } else {
                long viewRootInstanceCount3 = viewRootInstanceCount2;
                pw.println(" ");
                pw.println(" Objects");
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "Views:", Long.valueOf(viewInstanceCount), "ViewRootImpl:", Long.valueOf(viewRootInstanceCount3));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "AppContexts:", Long.valueOf(appContextInstanceCount), "Activities:", Long.valueOf(activityInstanceCount));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "Assets:", Integer.valueOf(globalAssetCount), "AssetManagers:", Integer.valueOf(globalAssetManagerCount));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "Local Binders:", Integer.valueOf(binderLocalObjectCount), "Proxy Binders:", Integer.valueOf(binderProxyObjectCount));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "Parcel memory:", Long.valueOf(parcelSize / 1024), "Parcel count:", Long.valueOf(parcelCount));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "Death Recipients:", Integer.valueOf(binderDeathObjectCount), "WebViews:", Long.valueOf(webviewInstanceCount));
                pw.println(" ");
                pw.println(" SQL");
                ActivityThread.printRow(pw, ActivityThread.ONE_COUNT_COLUMN, "MEMORY_USED:", Integer.valueOf(stats.memoryUsed / 1024));
                ActivityThread.printRow(pw, ActivityThread.TWO_COUNT_COLUMNS, "PAGECACHE_OVERFLOW:", Integer.valueOf(stats.pageCacheOverflow / 1024), "MALLOC_SIZE:", Integer.valueOf(stats.largestMemAlloc / 1024));
                pw.println(" ");
                int N = stats.dbStats.size();
                if (N > 0) {
                    pw.println(" DATABASES");
                    ActivityThread.printRow(pw, DB_CONNECTION_INFO_HEADER, "pgsz", "dbsz", "Lookaside(b)", "cache hits", "cache misses", "cache size", "Dbname");
                    pw.println("PER CONNECTION STATS");
                    int i2 = 0;
                    while (i2 < N) {
                        SQLiteDebug.DbStats dbStats2 = stats.dbStats.get(i2);
                        if (dbStats2.arePoolStats) {
                            viewRootInstanceCount = viewRootInstanceCount3;
                        } else {
                            Object[] objArr = new Object[7];
                            viewRootInstanceCount = viewRootInstanceCount3;
                            objArr[0] = dbStats2.pageSize > 0 ? String.valueOf(dbStats2.pageSize) : " ";
                            objArr[1] = dbStats2.dbSize > 0 ? String.valueOf(dbStats2.dbSize) : " ";
                            objArr[2] = dbStats2.lookaside > 0 ? String.valueOf(dbStats2.lookaside) : " ";
                            objArr[3] = Integer.valueOf(dbStats2.cacheHits);
                            objArr[4] = Integer.valueOf(dbStats2.cacheMisses);
                            objArr[5] = Integer.valueOf(dbStats2.cacheSize);
                            objArr[6] = dbStats2.dbName;
                            ActivityThread.printRow(pw, DB_CONNECTION_INFO_FORMAT, objArr);
                        }
                        i2++;
                        viewRootInstanceCount3 = viewRootInstanceCount;
                    }
                    pw.println("POOL STATS");
                    ActivityThread.printRow(pw, DB_POOL_INFO_HEADER, "cache hits", "cache misses", "cache size", "Dbname");
                    for (int i3 = 0; i3 < N; i3++) {
                        SQLiteDebug.DbStats dbStats3 = stats.dbStats.get(i3);
                        if (dbStats3.arePoolStats) {
                            ActivityThread.printRow(pw, DB_POOL_INFO_FORMAT, Integer.valueOf(dbStats3.cacheHits), Integer.valueOf(dbStats3.cacheMisses), Integer.valueOf(dbStats3.cacheSize), dbStats3.dbName);
                        }
                    }
                }
                String assetAlloc = AssetManager.getAssetAllocations();
                if (assetAlloc != null) {
                    pw.println(" ");
                    pw.println(" Asset Allocations");
                    pw.print(assetAlloc);
                }
                if (dumpUnreachable) {
                    boolean showContents = !(ActivityThread.this.mBoundApplication == null || (ActivityThread.this.mBoundApplication.appInfo.flags & 2) == 0) || Build.IS_DEBUGGABLE;
                    pw.println(" ");
                    pw.println(" Unreachable memory");
                    pw.print(Debug.getUnreachableMemory(100, showContents));
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(ParcelFileDescriptor pfd, Debug.MemoryInfo mem, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) {
            ProtoOutputStream proto = new ProtoOutputStream(pfd.getFileDescriptor());
            try {
                dumpMemInfo(proto, mem, dumpFullInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable);
            } finally {
                proto.flush();
                IoUtils.closeQuietly(pfd);
            }
        }

        private void dumpMemInfo(ProtoOutputStream proto, Debug.MemoryInfo memInfo, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable) {
            int flags;
            long nativeMax = Debug.getNativeHeapSize() / 1024;
            long nativeAllocated = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeFree = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long dalvikMax = runtime.totalMemory() / 1024;
            long dalvikFree = runtime.freeMemory() / 1024;
            long dalvikAllocated = dalvikMax - dalvikFree;
            Class[] classesToCount = {ContextImpl.class, Activity.class, WebView.class, View.class, ViewRootImpl.class};
            long[] instanceCounts = VMDebug.countInstancesOfClasses(classesToCount, true);
            long appContextInstanceCount = instanceCounts[0];
            long activityInstanceCount = instanceCounts[1];
            long webviewInstanceCount = instanceCounts[2];
            long viewInstanceCount = instanceCounts[3];
            long viewRootInstanceCount = instanceCounts[4];
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = Debug.getBinderDeathObjectCount();
            long parcelSize = Parcel.getGlobalAllocSize();
            long parcelCount = Parcel.getGlobalAllocCount();
            SQLiteDebug.PagerStats stats = SQLiteDebug.getDatabaseInfo();
            long mToken = proto.start(1146756268033L);
            proto.write(1120986464257L, Process.myPid());
            proto.write(1138166333442L, ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown");
            ActivityThread.dumpMemInfoTable(proto, memInfo, dumpDalvik, dumpSummaryOnly, nativeMax, nativeAllocated, nativeFree, dalvikMax, dalvikAllocated, dalvikFree);
            proto.end(mToken);
            long oToken = proto.start(1146756268034L);
            proto.write(1120986464257L, viewInstanceCount);
            proto.write(1120986464258L, viewRootInstanceCount);
            long appContextInstanceCount2 = appContextInstanceCount;
            proto.write(1120986464259L, appContextInstanceCount2);
            long activityInstanceCount2 = activityInstanceCount;
            proto.write(1120986464260L, activityInstanceCount2);
            proto.write(1120986464261L, globalAssetCount);
            proto.write(1120986464262L, globalAssetManagerCount);
            proto.write(1120986464263L, binderLocalObjectCount);
            proto.write(1120986464264L, binderProxyObjectCount);
            proto.write(1112396529673L, parcelSize / 1024);
            proto.write(1120986464266L, parcelCount);
            proto.write(1120986464267L, binderDeathObjectCount);
            proto.write(1120986464269L, webviewInstanceCount);
            proto.end(oToken);
            long sToken = proto.start(1146756268035L);
            SQLiteDebug.PagerStats stats2 = stats;
            proto.write(1120986464257L, stats2.memoryUsed / 1024);
            proto.write(1120986464258L, stats2.pageCacheOverflow / 1024);
            proto.write(1120986464259L, stats2.largestMemAlloc / 1024);
            int n = stats2.dbStats.size();
            int i = 0;
            while (i < n) {
                SQLiteDebug.DbStats dbStats = stats2.dbStats.get(i);
                SQLiteDebug.PagerStats stats3 = stats2;
                long dToken = proto.start(2246267895812L);
                proto.write(1138166333441L, dbStats.dbName);
                proto.write(1120986464258L, dbStats.pageSize);
                proto.write(1120986464259L, dbStats.dbSize);
                proto.write(1120986464260L, dbStats.lookaside);
                proto.write(1120986464262L, dbStats.cacheHits);
                proto.write(1120986464263L, dbStats.cacheMisses);
                proto.write(1120986464264L, dbStats.cacheSize);
                proto.end(dToken);
                i++;
                n = n;
                activityInstanceCount2 = activityInstanceCount2;
                appContextInstanceCount2 = appContextInstanceCount2;
                stats2 = stats3;
            }
            proto.end(sToken);
            String assetAlloc = AssetManager.getAssetAllocations();
            if (assetAlloc != null) {
                proto.write(1138166333444L, assetAlloc);
            }
            if (dumpUnreachable) {
                if (ActivityThread.this.mBoundApplication == null) {
                    flags = 0;
                } else {
                    flags = ActivityThread.this.mBoundApplication.appInfo.flags;
                }
                boolean showContents = (flags & 2) != 0 || Build.IS_DEBUGGABLE;
                proto.write(1138166333445L, Debug.getUnreachableMemory(100, showContents));
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor pfd, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                try {
                    data.fd = pfd.dup();
                    data.token = null;
                    data.args = args;
                    ActivityThread.this.sendMessage(165, data, 0, 0, true);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpGfxInfo failed", e);
                }
            } finally {
                IoUtils.closeQuietly(pfd);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(ParcelFileDescriptor pfd, String[] args) {
            PropertyInvalidatedCache.dumpCacheInfo(pfd, args);
            IoUtils.closeQuietly(pfd);
        }

        private File getDatabasesDir(Context context) {
            return context.getDatabasePath("a").getParentFile();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpDatabaseInfo(ParcelFileDescriptor pfd, String[] args, boolean isSystem) {
            PrintWriter pw = new FastPrintWriter(new FileOutputStream(pfd.getFileDescriptor()));
            PrintWriterPrinter printer = new PrintWriterPrinter(pw);
            SQLiteDebug.dump(printer, args, isSystem);
            pw.flush();
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor pfd, final String[] args) {
            try {
                if (!ActivityThread.this.mSystemThread) {
                    dumpDatabaseInfo(pfd, args, false);
                    return;
                }
                final ParcelFileDescriptor dup = pfd.dup();
                IoUtils.closeQuietly(pfd);
                AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: android.app.ActivityThread.ApplicationThread.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            ApplicationThread.this.dumpDatabaseInfo(dup, args, true);
                        } finally {
                            IoUtils.closeQuietly(dup);
                        }
                    }
                });
            } catch (IOException e) {
                Log.w(ActivityThread.TAG, "Could not dup FD " + pfd.getFileDescriptor().getInt$());
            } finally {
                IoUtils.closeQuietly(pfd);
            }
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder provider) {
            ActivityThread.this.sendMessage(142, provider);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) {
            RequestAssistContextExtras cmd = new RequestAssistContextExtras();
            cmd.activityToken = activityToken;
            cmd.requestToken = requestToken;
            cmd.requestType = requestType;
            cmd.sessionId = sessionId;
            cmd.flags = flags;
            ActivityThread.this.sendMessage(143, cmd);
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle coreSettings) {
            ActivityThread.this.sendMessage(138, coreSettings);
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) {
            UpdateCompatibilityData ucd = new UpdateCompatibilityData();
            ucd.pkg = pkg;
            ucd.info = info;
            updateCompatOverrideScale(info);
            ActivityThread.this.sendMessage(139, ucd);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int level) {
            Runnable r = PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ActivityThread) obj).handleTrimMemory(((Integer) obj2).intValue());
                }
            }, ActivityThread.this, Integer.valueOf(level)).recycleOnUse();
            Choreographer choreographer = Choreographer.getMainThreadInstance();
            if (choreographer != null) {
                choreographer.postCallback(4, r, null);
            } else {
                ActivityThread.this.mH.post(r);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) {
            ActivityThread.this.sendMessage(144, iBinder, z ? 1 : 0);
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewActivityOptions(IBinder token, Bundle options) {
            ActivityThread.this.sendMessage(146, new Pair(token, ActivityOptions.fromBundle(options)));
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int state) {
            ActivityThread.this.updateProcessState(state, true);
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long procStateSeq) {
            synchronized (ActivityThread.this.mNetworkPolicyLock) {
                ActivityThread.this.mNetworkBlockSeq = procStateSeq;
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo provider) {
            ActivityThread.this.sendMessage(145, provider);
        }

        @Override // android.app.IApplicationThread
        public final void updateTimePrefs(int timeFormatPreference) {
            Boolean timeFormatPreferenceBool;
            if (timeFormatPreference == 0) {
                timeFormatPreferenceBool = Boolean.FALSE;
            } else if (timeFormatPreference == 1) {
                timeFormatPreferenceBool = Boolean.TRUE;
            } else {
                timeFormatPreferenceBool = null;
            }
            DateFormat.set24HourTimePref(timeFormatPreferenceBool);
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder token) {
            ActivityThread.this.sendMessage(149, token);
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] firstPacket) {
            if (StrictMode.vmCleartextNetworkEnabled()) {
                StrictMode.onCleartextNetworkDetected(firstPacket);
            }
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() {
            if (Binder.isSystemServerBinderTrackerEnabled) {
                Message msg = Message.obtain();
                msg.what = 4;
                ActivityThread.this.trackingHandler.sendMessage(msg);
                return;
            }
            ActivityThread.this.sendMessage(150, null);
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor pfd) {
            try {
                ActivityThread.this.sendMessage(151, pfd.dup());
            } catch (IOException e) {
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor pfd, String processName, String packageName, int pid, int uid) {
            try {
                SomeArgs args = SomeArgs.obtain();
                args.arg1 = pfd.dup();
                args.arg2 = processName;
                args.arg3 = packageName;
                args.argi1 = pid;
                args.argi2 = uid;
                Message msg = Message.obtain();
                msg.what = 5;
                msg.obj = args;
                ActivityThread.this.trackingHandler.sendMessage(msg);
            } catch (IOException e) {
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = token;
            args.arg2 = voiceInteractor;
            ActivityThread.this.sendMessage(154, args);
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() {
            NetworkSecurityPolicy.getInstance().handleTrustStorageUpdate();
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
            ActivityThread.this.scheduleTransaction(transaction);
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder activityToken, IVoiceInteractor interactor, RemoteCallback cancellationCallback, RemoteCallback callback) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (cancellationCallback != null) {
                ICancellationSignal transport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle cancellationResult = new Bundle();
                cancellationResult.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, transport.asBinder());
                cancellationCallback.sendResult(cancellationResult);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((ActivityThread) obj).handleRequestDirectActions((IBinder) obj2, (IVoiceInteractor) obj3, (CancellationSignal) obj4, (RemoteCallback) obj5, ((Integer) obj6).intValue());
                }
            }, ActivityThread.this, activityToken, interactor, cancellationSignal, callback, 7));
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (cancellationCallback != null) {
                ICancellationSignal transport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle cancellationResult = new Bundle();
                cancellationResult.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, transport.asBinder());
                cancellationCallback.sendResult(cancellationResult);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((ActivityThread) obj).handlePerformDirectAction((IBinder) obj2, (String) obj3, (Bundle) obj4, (CancellationSignal) obj5, (RemoteCallback) obj6);
                }
            }, ActivityThread.this, activityToken, actionId, arguments, cancellationSignal, resultCallback));
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(ContentProviderHolder holder, String authorities, int userId, boolean published) {
            String[] auths = authorities.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            for (String auth : auths) {
                ProviderKey key = ActivityThread.this.getGetProviderKey(auth, userId);
                synchronized (key.mLock) {
                    key.mHolder = holder;
                    key.mLock.notifyAll();
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(ComponentName instrumentationName, Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection, ApplicationInfo targetInfo) {
            AppBindData data = new AppBindData();
            data.instrumentationName = instrumentationName;
            data.instrumentationArgs = instrumentationArgs;
            data.instrumentationWatcher = instrumentationWatcher;
            data.instrumentationUiAutomationConnection = instrumentationUiConnection;
            data.appInfo = targetInfo;
            ActivityThread.this.sendMessage(170, data);
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(IBinder activityToken, int state, TranslationSpec sourceSpec, TranslationSpec targetSpec, List<AutofillId> viewIds, UiTranslationSpec uiTranslationSpec) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = activityToken;
            args.arg2 = Integer.valueOf(state);
            args.arg3 = sourceSpec;
            args.arg4 = targetSpec;
            args.arg5 = viewIds;
            args.arg6 = uiTranslationSpec;
            ActivityThread.this.sendMessage(163, args);
        }

        @Override // android.app.IApplicationThread
        public void clearIdsTrainingData(boolean flag) {
            IdsController.clearTrainingData(flag);
        }

        @Override // android.app.IApplicationThread
        public void forceGc() {
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
        }

        @Override // android.app.IApplicationThread
        public void getProfileLength(String pkg) {
            ActivityThread.this.sendMessage(169, pkg);
        }

        @Override // android.app.IApplicationThread
        public void setFlingerFlag(String pkg) {
            ActivityThread.this.sendMessage(172, pkg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SafeCancellationTransport createSafeCancellationTransport(CancellationSignal cancellationSignal) {
        SafeCancellationTransport transport;
        synchronized (this) {
            if (this.mRemoteCancellations == null) {
                this.mRemoteCancellations = new ArrayMap();
            }
            transport = new SafeCancellationTransport(this, cancellationSignal);
            this.mRemoteCancellations.put(transport, cancellationSignal);
        }
        return transport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CancellationSignal removeSafeCancellationTransport(SafeCancellationTransport transport) {
        CancellationSignal cancellation;
        synchronized (this) {
            cancellation = this.mRemoteCancellations.remove(transport);
            if (this.mRemoteCancellations.isEmpty()) {
                this.mRemoteCancellations = null;
            }
        }
        return cancellation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class SafeCancellationTransport extends ICancellationSignal.Stub {
        private final WeakReference<ActivityThread> mWeakActivityThread;

        SafeCancellationTransport(ActivityThread activityThread, CancellationSignal cancellation) {
            this.mWeakActivityThread = new WeakReference<>(activityThread);
        }

        @Override // android.os.ICancellationSignal
        public void cancel() {
            CancellationSignal cancellation;
            ActivityThread activityThread = this.mWeakActivityThread.get();
            if (activityThread != null && (cancellation = activityThread.removeSafeCancellationTransport(this)) != null) {
                cancellation.cancel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void throwRemoteServiceException(String message, int typeId, Bundle extras) {
        switch (typeId) {
            case 1:
                throw generateForegroundServiceDidNotStartInTimeException(message, extras);
            case 2:
                throw new RemoteServiceException.CannotPostForegroundServiceNotificationException(message);
            case 3:
                throw new RemoteServiceException.BadForegroundServiceNotificationException(message);
            case 4:
                throw new RemoteServiceException.MissingRequestPasswordComplexityPermissionException(message);
            case 5:
                throw new RemoteServiceException.CrashedByAdbException(message);
            case 6:
                throw new RemoteServiceException.BadUserInitiatedJobNotificationException(message);
            default:
                throw new RemoteServiceException(message + " (with unwknown typeId:" + typeId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private RemoteServiceException.ForegroundServiceDidNotStartInTimeException generateForegroundServiceDidNotStartInTimeException(String message, Bundle extras) {
        String serviceClassName = RemoteServiceException.ForegroundServiceDidNotStartInTimeException.getServiceClassNameFromExtras(extras);
        Exception inner = serviceClassName == null ? null : Service.getStartForegroundServiceStackTrace(serviceClassName);
        throw new RemoteServiceException.ForegroundServiceDidNotStartInTimeException(message, inner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class WebviewRunnable implements Runnable {
        private WebviewRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Process.setThreadPriority(10);
            try {
                Class<?> webViewFactoryClass = Class.forName("android.webkit.WebViewFactory");
                Method method = webViewFactoryClass.getDeclaredMethod("getProvider", new Class[0]);
                if (method != null) {
                    method.setAccessible(true);
                    method.invoke(webViewFactoryClass, new Object[0]);
                }
            } catch (ReflectiveOperationException e) {
                throw new AndroidRuntimeException("webview invoke failed!!!", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class H extends Handler {
        public static final int ADCP_CHECK_PROFILE_SIZE = 169;
        public static final int APPLICATION_INFO_CHANGED = 156;
        public static final int ATTACH_AGENT = 155;
        public static final int ATTACH_STARTUP_AGENTS = 162;
        public static final int BBA_SET_FLINGER_FLAG = 172;
        public static final int BIND_APPLICATION = 110;
        public static final int BIND_SERVICE = 121;
        public static final int CLEAN_UP_CONTEXT = 119;
        public static final int CONFIGURATION_CHANGED = 118;
        public static final int CREATE_BACKUP_AGENT = 128;
        public static final int CREATE_SERVICE = 114;
        public static final int DESTROY_BACKUP_AGENT = 129;
        public static final int DISPATCH_PACKAGE_BROADCAST = 133;
        public static final int DUMP_ACTIVITY = 136;
        public static final int DUMP_GFXINFO = 165;
        public static final int DUMP_HEAP = 135;
        public static final int DUMP_PROVIDER = 141;
        public static final int DUMP_RESOURCES = 166;
        public static final int DUMP_SERVICE = 123;
        public static final int ENTER_ANIMATION_COMPLETE = 149;
        public static final int EXECUTE_TRANSACTION = 159;
        public static final int EXIT_APPLICATION = 111;
        public static final int FINISH_INSTRUMENTATION_WITHOUT_RESTART = 171;
        public static final int GC_WHEN_IDLE = 120;
        public static final int INSTALL_PROVIDER = 145;
        public static final int INSTRUMENT_WITHOUT_RESTART = 170;
        public static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
        public static final int LOW_MEMORY = 124;
        public static final int ON_NEW_ACTIVITY_OPTIONS = 146;
        public static final int PING = 168;
        public static final int PROFILER_CONTROL = 127;
        public static final int PURGE_RESOURCES = 161;
        public static final int RECEIVER = 113;
        public static final int RELAUNCH_ACTIVITY = 160;
        public static final int REMOVE_PROVIDER = 131;
        public static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
        public static final int RUN_ISOLATED_ENTRY_POINT = 158;
        public static final int SCHEDULE_CRASH = 134;
        public static final int SERVICE_ARGS = 115;
        public static final int SET_CONTENT_CAPTURE_OPTIONS_CALLBACK = 164;
        public static final int SET_CORE_SETTINGS = 138;
        public static final int SLEEPING = 137;
        public static final int START_BINDER_TRACKING = 150;
        public static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
        public static final int STOP_SERVICE = 116;
        public static final int SUICIDE = 130;
        public static final int TIMEOUT_SERVICE = 167;
        public static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
        public static final int UNBIND_SERVICE = 122;
        public static final int UNSTABLE_PROVIDER_DIED = 142;
        public static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;
        public static final int UPDATE_UI_TRANSLATION_STATE = 163;

        H() {
        }

        String codeToString(int code) {
            return Integer.toString(code);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 110:
                    Trace.traceBegin(64L, "bindApplication");
                    AppBindData data = (AppBindData) msg.obj;
                    ActivityThread.this.handleBindApplication(data);
                    break;
                case 111:
                    if (ActivityThread.this.mInitialApplication != null) {
                        ActivityThread.this.mInitialApplication.onTerminate();
                    }
                    Looper.myLooper().quit();
                    break;
                case 113:
                    if (Trace.isTagEnabled(64L)) {
                        ReceiverData rec = (ReceiverData) msg.obj;
                        if (rec.intent != null) {
                            Trace.traceBegin(64L, "broadcastReceiveComp: " + rec.intent.getAction());
                        } else {
                            Trace.traceBegin(64L, "broadcastReceiveComp");
                        }
                    }
                    ActivityThread.this.handleReceiver((ReceiverData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 114:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceCreate: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleCreateService((CreateServiceData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 115:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceStart: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleServiceArgs((ServiceArgsData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 116:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceStop: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleStopService((IBinder) msg.obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64L);
                    break;
                case 118:
                    ActivityThread.this.mConfigurationController.handleConfigurationChanged((Configuration) msg.obj);
                    break;
                case 119:
                    ContextCleanupInfo cci = (ContextCleanupInfo) msg.obj;
                    cci.context.performFinalCleanup(cci.who, cci.what);
                    break;
                case 120:
                    Trace.traceBegin(64L, "gcWhenIdle");
                    try {
                        ActivityThread.this.scheduleGcIdler();
                        Trace.traceEnd(64L);
                        break;
                    } finally {
                    }
                case 121:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceBind: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleBindService((BindServiceData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 122:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceUnbind: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleUnbindService((BindServiceData) msg.obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64L);
                    break;
                case 123:
                    ActivityThread.this.handleDumpService((DumpComponentInfo) msg.obj);
                    break;
                case 124:
                    Trace.traceBegin(64L, "lowMemory");
                    ActivityThread.this.handleLowMemory();
                    Trace.traceEnd(64L);
                    break;
                case 127:
                    ActivityThread.this.handleProfilerControl(msg.arg1 != 0, (ProfilerInfo) msg.obj, msg.arg2);
                    break;
                case 128:
                    Trace.traceBegin(64L, "backupCreateAgent");
                    ActivityThread.this.handleCreateBackupAgent((CreateBackupAgentData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 129:
                    Trace.traceBegin(64L, "backupDestroyAgent");
                    ActivityThread.this.handleDestroyBackupAgent((CreateBackupAgentData) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 130:
                    Process.killProcess(Process.myPid());
                    break;
                case 131:
                    Trace.traceBegin(64L, "providerRemove");
                    ActivityThread.this.completeRemoveProvider((ProviderRefCount) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 133:
                    Trace.traceBegin(64L, "broadcastPackage");
                    ActivityThread.this.handleDispatchPackageBroadcast(msg.arg1, (String[]) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 134:
                    SomeArgs args = (SomeArgs) msg.obj;
                    String message = (String) args.arg1;
                    Bundle extras = (Bundle) args.arg2;
                    args.recycle();
                    ActivityThread.this.throwRemoteServiceException(message, msg.arg1, extras);
                    break;
                case 135:
                    ActivityThread.handleDumpHeap((DumpHeapData) msg.obj);
                    break;
                case 136:
                    ActivityThread.this.handleDumpActivity((DumpComponentInfo) msg.obj);
                    break;
                case 138:
                    Trace.traceBegin(64L, "setCoreSettings");
                    ActivityThread.this.handleSetCoreSettings((Bundle) msg.obj);
                    Trace.traceEnd(64L);
                    break;
                case 139:
                    ActivityThread.this.handleUpdatePackageCompatibilityInfo((UpdateCompatibilityData) msg.obj);
                    break;
                case 141:
                    ActivityThread.this.handleDumpProvider((DumpComponentInfo) msg.obj);
                    break;
                case 142:
                    ActivityThread.this.handleUnstableProviderDied((IBinder) msg.obj, false);
                    break;
                case 143:
                    ActivityThread.this.handleRequestAssistContextExtras((RequestAssistContextExtras) msg.obj);
                    break;
                case 144:
                    ActivityThread.this.handleTranslucentConversionComplete((IBinder) msg.obj, msg.arg1 == 1);
                    break;
                case 145:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "providerInstall: " + String.valueOf(msg.obj));
                    }
                    try {
                        ActivityThread.this.handleInstallProvider((ProviderInfo) msg.obj);
                        Trace.traceEnd(64L);
                        break;
                    } finally {
                    }
                case 146:
                    Pair<IBinder, ActivityOptions> pair = (Pair) msg.obj;
                    ActivityThread.this.onNewActivityOptions(pair.first, pair.second);
                    break;
                case 149:
                    ActivityThread.this.handleEnterAnimationComplete((IBinder) msg.obj);
                    break;
                case 150:
                    ActivityThread.this.handleStartBinderTracking();
                    break;
                case 151:
                    ActivityThread.this.handleStopBinderTrackingAndDump((ParcelFileDescriptor) msg.obj);
                    break;
                case 154:
                    ActivityThread.this.handleLocalVoiceInteractionStarted((IBinder) ((SomeArgs) msg.obj).arg1, (IVoiceInteractor) ((SomeArgs) msg.obj).arg2);
                    break;
                case 155:
                    Application app = ActivityThread.this.getApplication();
                    ActivityThread.handleAttachAgent((String) msg.obj, app != null ? app.mLoadedApk : null);
                    break;
                case 156:
                    ActivityThread.this.handleApplicationInfoChanged((ApplicationInfo) msg.obj);
                    break;
                case 158:
                    ActivityThread.this.handleRunIsolatedEntryPoint((String) ((SomeArgs) msg.obj).arg1, (String[]) ((SomeArgs) msg.obj).arg2);
                    break;
                case 159:
                    ClientTransaction transaction = (ClientTransaction) msg.obj;
                    ActivityThread.this.mTransactionExecutor.execute(transaction);
                    if (ActivityThread.isSystem()) {
                        transaction.recycle();
                        break;
                    }
                    break;
                case 160:
                    ActivityThread.this.handleRelaunchActivityLocally((IBinder) msg.obj);
                    break;
                case 161:
                    ActivityThread.this.schedulePurgeIdler();
                    break;
                case 162:
                    ActivityThread.handleAttachStartupAgents((String) msg.obj);
                    break;
                case 163:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    ActivityThread.this.updateUiTranslationState((IBinder) args2.arg1, ((Integer) args2.arg2).intValue(), (TranslationSpec) args2.arg3, (TranslationSpec) args2.arg4, (List) args2.arg5, (UiTranslationSpec) args2.arg6);
                    break;
                case 164:
                    ActivityThread.this.handleSetContentCaptureOptionsCallback((String) msg.obj);
                    break;
                case 165:
                    ActivityThread.this.handleDumpGfxInfo((DumpComponentInfo) msg.obj);
                    break;
                case 166:
                    ActivityThread.this.handleDumpResources((DumpResourcesData) msg.obj);
                    break;
                case 167:
                    if (Trace.isTagEnabled(64L)) {
                        Trace.traceBegin(64L, "serviceTimeout: " + String.valueOf(msg.obj));
                    }
                    ActivityThread.this.handleTimeoutService((IBinder) msg.obj, msg.arg1);
                    break;
                case 168:
                    ((RemoteCallback) msg.obj).sendResult(null);
                    break;
                case 169:
                    ActivityThread.this.getProfileSizeOfApp((String) msg.obj);
                    break;
                case 170:
                    ActivityThread.this.handleInstrumentWithoutRestart((AppBindData) msg.obj);
                    break;
                case 171:
                    ActivityThread.this.handleFinishInstrumentationWithoutRestart();
                    break;
                case 172:
                    ActivityThread.this.setFlingerFlag((String) msg.obj, false);
                    break;
            }
            Object obj = msg.obj;
            if (obj instanceof SomeArgs) {
                ((SomeArgs) obj).recycle();
            }
        }
    }

    /* loaded from: classes.dex */
    private class Idler implements MessageQueue.IdleHandler {
        private Idler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            boolean stopProfiling = false;
            if (ActivityThread.this.mBoundApplication != null && ActivityThread.this.mProfiler.profileFd != null && ActivityThread.this.mProfiler.autoStopProfiler) {
                stopProfiling = true;
            }
            ActivityClient ac = ActivityClient.getInstance();
            while (ActivityThread.this.mNewActivities.size() > 0) {
                ActivityClientRecord a = ActivityThread.this.mNewActivities.remove(0);
                if (a.activity != null && !a.activity.mFinished) {
                    ac.activityIdle(a.token, a.createdConfig, stopProfiling);
                    a.createdConfig = null;
                    EventLogTags.writeWmOnIdleCalled(a.activityInfo.getComponentName().toShortString());
                }
            }
            if (stopProfiling) {
                ActivityThread.this.mProfiler.stopProfiling();
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class GcIdler implements MessageQueue.IdleHandler {
        GcIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            ActivityThread.this.doGcIfNeeded();
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class PurgeIdler implements MessageQueue.IdleHandler {
        PurgeIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    public static ActivityThread currentActivityThread() {
        return sCurrentActivityThread;
    }

    public static boolean isSystem() {
        if (sCurrentActivityThread != null) {
            return sCurrentActivityThread.mSystemThread;
        }
        return false;
    }

    public static String currentOpPackageName() {
        ActivityThread am = currentActivityThread();
        if (am == null || am.getApplication() == null) {
            return null;
        }
        return am.getApplication().getOpPackageName();
    }

    public static AttributionSource currentAttributionSource() {
        ActivityThread am = currentActivityThread();
        if (am == null || am.getApplication() == null) {
            return null;
        }
        return am.getApplication().getAttributionSource();
    }

    public static String currentPackageName() {
        AppBindData appBindData;
        ActivityThread am = currentActivityThread();
        if (am == null || (appBindData = am.mBoundApplication) == null) {
            return null;
        }
        return appBindData.appInfo.packageName;
    }

    public static String currentProcessName() {
        AppBindData appBindData;
        ActivityThread am = currentActivityThread();
        if (am == null || (appBindData = am.mBoundApplication) == null) {
            return null;
        }
        return appBindData.processName;
    }

    public static Application currentApplication() {
        ActivityThread am = currentActivityThread();
        if (am != null) {
            return am.mInitialApplication;
        }
        return null;
    }

    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        IBinder b = ServiceManager.getService("package");
        sPackageManager = IPackageManager.Stub.asInterface(b);
        return sPackageManager;
    }

    public static IPermissionManager getPermissionManager() {
        if (sPermissionManager != null) {
            return sPermissionManager;
        }
        IBinder b = ServiceManager.getService("permissionmgr");
        sPermissionManager = IPermissionManager.Stub.asInterface(b);
        return sPermissionManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Resources getTopLevelResources(String resDir, String[] splitResDirs, String[] legacyOverlayDirs, String[] overlayPaths, String[] libDirs, LoadedApk pkgInfo, Configuration overrideConfig) {
        return this.mResourcesManager.getResources(null, resDir, splitResDirs, legacyOverlayDirs, overlayPaths, libDirs, null, overrideConfig, pkgInfo.getCompatibilityInfo(), pkgInfo.getClassLoader(), null);
    }

    public Handler getHandler() {
        return this.mH;
    }

    public final LoadedApk getPackageInfo(String packageName, CompatibilityInfo compatInfo, int flags) {
        return getPackageInfo(packageName, compatInfo, flags, UserHandle.myUserId());
    }

    public final LoadedApk getPackageInfo(String packageName, CompatibilityInfo compatInfo, int flags, int userId) {
        WeakReference<LoadedApk> ref;
        boolean differentUser = UserHandle.myUserId() != userId;
        ApplicationInfo ai = PackageManager.getApplicationInfoAsUserCached(packageName, 268436480L, userId < 0 ? UserHandle.myUserId() : userId);
        synchronized (this.mResourcesManager) {
            if (differentUser) {
                ref = null;
            } else if ((flags & 1) != 0) {
                ref = this.mPackages.get(packageName);
            } else {
                ref = this.mResourcePackages.get(packageName);
            }
            LoadedApk packageInfo = ref != null ? ref.get() : null;
            if (ai != null && packageInfo != null) {
                if (!isLoadedApkResourceDirsUpToDate(packageInfo, ai)) {
                    List<String> oldPaths = new ArrayList<>();
                    LoadedApk.makePaths(this, ai, oldPaths);
                    packageInfo.updateApplicationInfo(ai, oldPaths);
                }
                if (packageInfo.isSecurityViolation() && (flags & 2) == 0) {
                    throw new SecurityException("Requesting code from " + packageName + " to be run in process " + this.mBoundApplication.processName + "/" + this.mBoundApplication.appInfo.uid);
                }
                return packageInfo;
            }
            if (ai == null) {
                return null;
            }
            return getPackageInfo(ai, compatInfo, flags);
        }
    }

    public final LoadedApk getPackageInfo(ApplicationInfo ai, CompatibilityInfo compatInfo, int flags) {
        boolean includeCode = (flags & 1) != 0;
        boolean securityViolation = includeCode && ai.uid != 0 && ai.uid != 1000 && (this.mBoundApplication == null || !UserHandle.isSameApp(ai.uid, this.mBoundApplication.appInfo.uid));
        boolean registerPackage = includeCode && (1073741824 & flags) != 0;
        if ((flags & 3) == 1 && securityViolation) {
            String msg = "Requesting code from " + ai.packageName + " (with uid " + ai.uid + NavigationBarInflaterView.KEY_CODE_END;
            if (this.mBoundApplication != null) {
                msg = msg + " to be run in process " + this.mBoundApplication.processName + " (with uid " + this.mBoundApplication.appInfo.uid + NavigationBarInflaterView.KEY_CODE_END;
            }
            throw new SecurityException(msg);
        }
        return getPackageInfo(ai, compatInfo, null, securityViolation, includeCode, registerPackage);
    }

    public final LoadedApk getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo) {
        return getPackageInfo(ai, compatInfo, null, false, true, false);
    }

    @Override // android.app.ClientTransactionHandler
    public LoadedApk getPackageInfoNoCheck(ApplicationInfo ai) {
        return getPackageInfo(ai, this.mCompatibilityInfo, null, false, true, false);
    }

    public final LoadedApk peekPackageInfo(String packageName, boolean includeCode) {
        WeakReference<LoadedApk> ref;
        LoadedApk loadedApk;
        synchronized (this.mResourcesManager) {
            if (includeCode) {
                ref = this.mPackages.get(packageName);
            } else {
                ref = this.mResourcePackages.get(packageName);
            }
            loadedApk = ref != null ? ref.get() : null;
        }
        return loadedApk;
    }

    private LoadedApk getPackageInfo(ApplicationInfo aInfo, CompatibilityInfo compatInfo, ClassLoader baseLoader, boolean securityViolation, boolean includeCode, boolean registerPackage) {
        return getPackageInfo(aInfo, compatInfo, baseLoader, securityViolation, includeCode, registerPackage, Process.isSdkSandbox());
    }

    private LoadedApk getPackageInfo(ApplicationInfo aInfo, CompatibilityInfo compatInfo, ClassLoader baseLoader, boolean securityViolation, boolean includeCode, boolean registerPackage, boolean isSdkSandbox) {
        return getPackageInfo(aInfo, compatInfo, baseLoader, securityViolation, includeCode, registerPackage, isSdkSandbox, false);
    }

    private LoadedApk getPackageInfo(ApplicationInfo aInfo, CompatibilityInfo compatInfo, ClassLoader baseLoader, boolean securityViolation, boolean includeCode, boolean registerPackage, boolean isSdkSandbox, boolean isCallFromReceiver) {
        WeakReference<LoadedApk> ref;
        boolean differentUser = UserHandle.myUserId() != UserHandle.getUserId(aInfo.uid);
        synchronized (this.mResourcesManager) {
            try {
                if (differentUser || isSdkSandbox) {
                    ref = null;
                } else if (includeCode) {
                    ref = this.mPackages.get(aInfo.packageName);
                } else {
                    ref = this.mResourcePackages.get(aInfo.packageName);
                }
                LoadedApk packageInfo = ref != null ? ref.get() : null;
                if (packageInfo != null) {
                    if (!isLoadedApkResourceDirsUpToDate(packageInfo, aInfo)) {
                        if (packageInfo.getApplicationInfo().createTimestamp > aInfo.createTimestamp) {
                            Slog.w(TAG, "getPackageInfo() called with an older ApplicationInfo than the cached version for package " + aInfo.packageName);
                        } else {
                            boolean needAppInfoUpdate = true;
                            if (isCallFromReceiver) {
                                ContextImpl context = (ContextImpl) packageInfo.makeApplication(false, this.mInstrumentation).getBaseContext();
                                LocaleList configLocaleList = context.getResources().getConfiguration().getLocales();
                                Collection<?> broadcastList = getlangListFromOverlayPaths(aInfo.overlayPaths);
                                Set<String> missingLocales = new HashSet<>();
                                for (int i = 0; i < configLocaleList.size(); i++) {
                                    String lang = configLocaleList.get(i).getLanguage();
                                    if (lang.length() == 3) {
                                        Slog.i(TAG, "LocaleChanged: trying to get ISO_639_1 mapping for locale: " + lang);
                                        if (SamsungThemeConstants.FILIPINO_LOCALE_CODE.equals(lang)) {
                                            lang = SamsungThemeConstants.TAGALOG_LOCALE_CODE;
                                        } else {
                                            lang = null;
                                        }
                                    }
                                    if (lang != null) {
                                        missingLocales.add(lang);
                                    }
                                }
                                missingLocales.removeAll(broadcastList);
                                Set<String> currentLocales = getlangListFromOverlayPaths(packageInfo.getOverlayPaths());
                                Iterator<String> it = currentLocales.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    String locale = it.next();
                                    if (missingLocales.contains(locale)) {
                                        needAppInfoUpdate = false;
                                        break;
                                    }
                                }
                            }
                            if (needAppInfoUpdate) {
                                Slog.v(TAG, "getPackageInfo() caused update to cached ApplicationInfo for package " + aInfo.packageName);
                                List<String> oldPaths = new ArrayList<>();
                                LoadedApk.makePaths(this, aInfo, oldPaths);
                                packageInfo.updateApplicationInfo(aInfo, oldPaths);
                            }
                        }
                    }
                    return packageInfo;
                }
                LoadedApk packageInfo2 = new LoadedApk(this, aInfo, compatInfo, baseLoader, securityViolation, includeCode && (aInfo.flags & 4) != 0, registerPackage);
                if (this.mSystemThread && "android".equals(aInfo.packageName)) {
                    packageInfo2.installSystemApplicationInfo(aInfo, getSystemContext().mPackageInfo.getClassLoader());
                }
                if (!differentUser && !isSdkSandbox) {
                    if (includeCode) {
                        this.mPackages.put(aInfo.packageName, new WeakReference<>(packageInfo2));
                    } else {
                        this.mResourcePackages.put(aInfo.packageName, new WeakReference<>(packageInfo2));
                    }
                }
                return packageInfo2;
            } finally {
            }
        }
    }

    private Set<String> getlangListFromOverlayPaths(String[] overlayPaths) {
        String packageName;
        Set<String> locales = new HashSet<>();
        if (overlayPaths != null && overlayPaths.length > 0) {
            for (String overlayPath : overlayPaths) {
                if (overlayPath != null && overlayPath.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_LOCALE_APKS) && overlayPath.lastIndexOf(46) != -1 && overlayPath.lastIndexOf(46) + 1 < overlayPath.length() && (packageName = overlayPath.substring(0, overlayPath.lastIndexOf(46))) != null) {
                    String lang = packageName.substring(packageName.lastIndexOf(46) + 1);
                    locales.add(lang);
                }
            }
        }
        return locales;
    }

    private static boolean isLoadedApkResourceDirsUpToDate(LoadedApk loadedApk, ApplicationInfo appInfo) {
        Resources packageResources = loadedApk.mResources;
        boolean resourceDirsUpToDate = Arrays.equals(ArrayUtils.defeatNullable(appInfo.resourceDirs), ArrayUtils.defeatNullable(loadedApk.getOverlayDirs()));
        boolean overlayPathsUpToDate = Arrays.equals(ArrayUtils.defeatNullable(appInfo.overlayPaths), ArrayUtils.defeatNullable(loadedApk.getOverlayPaths()));
        return (packageResources == null || packageResources.getAssets().isUpToDate()) && resourceDirsUpToDate && overlayPathsUpToDate;
    }

    ActivityThread() {
        this.trackingHandler = null;
        HandlerThread handlerThread = new HandlerThread("AppBinderTrackerThread");
        this.trackingThread = handlerThread;
        H h = new H();
        this.mH = h;
        this.mExecutor = new HandlerExecutor(h);
        this.mActivities = new ArrayMap<>();
        this.mPendingOverrideConfigs = new ArrayMap<>();
        this.mActivitiesToBeDestroyed = Collections.synchronizedMap(new ArrayMap());
        this.mNewActivities = new ArrayList<>();
        this.mNumVisibleActivities = 0;
        this.mNumLaunchingActivities = new AtomicInteger();
        this.mLastProcessState = -1;
        this.mLastAssistStructures = new ArrayList<>();
        this.mServicesData = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUpdateHttpProxyOnBind = false;
        this.mAllApplications = new ArrayList<>();
        this.mBackupAgentsByUser = new SparseArray<>();
        this.mInstrumentationPackageName = null;
        this.mInstrumentationAppDir = null;
        this.mInstrumentationSplitAppDirs = null;
        this.mInstrumentationLibDir = null;
        this.mInstrumentedAppDir = null;
        this.mInstrumentedSplitAppDirs = null;
        this.mInstrumentedLibDir = null;
        this.mSystemThread = false;
        this.mSomeActivitiesChanged = false;
        this.mPackages = new ArrayMap<>();
        this.mResourcePackages = new ArrayMap<>();
        this.mRelaunchingActivities = new ArrayList<>();
        this.mPendingConfiguration = null;
        this.mTransactionExecutor = new TransactionExecutor(this);
        this.mMultiWindowCoreStateListeners = new ArrayList();
        this.idsController = null;
        this.mAbnormalUsage = new AbnormalUsage();
        this.mProviderMap = new ArrayMap<>();
        this.mProviderRefCountMap = new ArrayMap<>();
        this.mLocalProviders = new ArrayMap<>();
        this.mLocalProvidersByName = new ArrayMap<>();
        this.mGetProviderKeys = new ArrayMap<>();
        this.mOnPauseListeners = new ArrayMap<>();
        this.mGcIdler = new GcIdler();
        this.mPurgeIdler = new PurgeIdler();
        this.mPurgeIdlerScheduled = false;
        this.mGcIdlerScheduled = false;
        this.mCoreSettings = null;
        this.mCoreSettingsLock = new Object();
        this.mDssScale = 1.0f;
        this.mContentCaptureOptionsCallback = null;
        this.webviewPreloaded = false;
        this.webviewPreloadState = -1;
        if (Binder.isSystemServerBinderTrackerEnabled) {
            handlerThread.start();
            this.trackingHandler = new Handler(handlerThread.getLooper()) { // from class: android.app.ActivityThread.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 4:
                            ActivityThread.this.handleStartBinderTracking();
                            return;
                        case 5:
                            ActivityThread.this.handleStopBinderTrackingAndDump((ParcelFileDescriptor) ((SomeArgs) msg.obj).arg1, (String) ((SomeArgs) msg.obj).arg2, (String) ((SomeArgs) msg.obj).arg3, ((SomeArgs) msg.obj).argi1, ((SomeArgs) msg.obj).argi2);
                            ((SomeArgs) msg.obj).recycle();
                            return;
                        default:
                            return;
                    }
                }
            };
        }
        this.mResourcesManager = ResourcesManager.getInstance();
    }

    public ApplicationThread getApplicationThread() {
        return this.mAppThread;
    }

    public Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    public boolean isProfiling() {
        Profiler profiler = this.mProfiler;
        return (profiler == null || profiler.profileFile == null || this.mProfiler.profileFd != null) ? false : true;
    }

    public String getProfileFilePath() {
        return this.mProfiler.profileFile;
    }

    public Looper getLooper() {
        return this.mLooper;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    @Override // android.app.ActivityThreadInternal
    public Application getApplication() {
        return this.mInitialApplication;
    }

    public String getProcessName() {
        return this.mBoundApplication.processName;
    }

    @Override // android.app.ActivityThreadInternal
    public ContextImpl getSystemContext() {
        ContextImpl contextImpl;
        synchronized (this) {
            if (this.mSystemContext == null) {
                this.mSystemContext = ContextImpl.createSystemContext(this);
            }
            contextImpl = this.mSystemContext;
        }
        return contextImpl;
    }

    public ContextImpl getSystemUiContext() {
        return getSystemUiContext(0);
    }

    public ContextImpl getSystemUiContext(int displayId) {
        ContextImpl systemUiContext;
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                this.mDisplaySystemUiContexts = new SparseArray<>();
            }
            systemUiContext = this.mDisplaySystemUiContexts.get(displayId);
            if (systemUiContext == null) {
                systemUiContext = ContextImpl.createSystemUiContext(getSystemContext(), displayId);
                this.mDisplaySystemUiContexts.put(displayId, systemUiContext);
            }
        }
        return systemUiContext;
    }

    @Override // android.app.ActivityThreadInternal
    public ContextImpl getSystemUiContextNoCreate() {
        synchronized (this) {
            SparseArray<ContextImpl> sparseArray = this.mDisplaySystemUiContexts;
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onSystemUiContextCleanup(ContextImpl context) {
        synchronized (this) {
            SparseArray<ContextImpl> sparseArray = this.mDisplaySystemUiContexts;
            if (sparseArray == null) {
                return;
            }
            int index = sparseArray.indexOfValue(context);
            if (index >= 0) {
                this.mDisplaySystemUiContexts.removeAt(index);
            }
        }
    }

    public void installSystemApplicationInfo(ApplicationInfo info, ClassLoader classLoader) {
        synchronized (this) {
            getSystemContext().installSystemApplicationInfo(info, classLoader);
            getSystemUiContext().installSystemApplicationInfo(info, classLoader);
            this.mProfiler = new Profiler();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void scheduleGcIdler() {
        if (!this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void unscheduleGcIdler() {
        if (this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void schedulePurgeIdler() {
        if (!this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void unschedulePurgeIdler() {
        if (this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void doGcIfNeeded() {
        doGcIfNeeded("bg");
    }

    void doGcIfNeeded(String reason) {
        this.mGcIdlerScheduled = false;
        long now = SystemClock.uptimeMillis();
        if (BinderInternal.getLastGcTime() + 5000 < now) {
            BinderInternal.forceGc(reason);
        }
    }

    static void printRow(PrintWriter pw, String format, Object... objs) {
        pw.println(String.format(format, objs));
    }

    public static void dumpMemInfoTable(PrintWriter pw, Debug.MemoryInfo memInfo, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, int pid, String processName, long nativeMax, long nativeAllocated, long nativeFree, long dalvikMax, long dalvikAllocated, long dalvikFree) {
        String str;
        int i;
        int otherPss;
        int otherRss;
        if (checkin) {
            pw.print(4);
            pw.print(',');
            pw.print(pid);
            pw.print(',');
            pw.print(processName);
            pw.print(',');
            pw.print(nativeMax);
            pw.print(',');
            pw.print(dalvikMax);
            pw.print(',');
            pw.print("N/A,");
            pw.print(nativeMax + dalvikMax);
            pw.print(',');
            pw.print(nativeAllocated);
            pw.print(',');
            pw.print(dalvikAllocated);
            pw.print(',');
            pw.print("N/A,");
            pw.print(nativeAllocated + dalvikAllocated);
            pw.print(',');
            pw.print(nativeFree);
            pw.print(',');
            pw.print(dalvikFree);
            pw.print(',');
            pw.print("N/A,");
            pw.print(nativeFree + dalvikFree);
            pw.print(',');
            pw.print(memInfo.nativePss);
            pw.print(',');
            pw.print(memInfo.dalvikPss);
            pw.print(',');
            pw.print(memInfo.otherPss);
            pw.print(',');
            pw.print(memInfo.getTotalPss());
            pw.print(',');
            pw.print(memInfo.nativeSwappablePss);
            pw.print(',');
            pw.print(memInfo.dalvikSwappablePss);
            pw.print(',');
            pw.print(memInfo.otherSwappablePss);
            pw.print(',');
            pw.print(memInfo.getTotalSwappablePss());
            pw.print(',');
            pw.print(memInfo.nativeSharedDirty);
            pw.print(',');
            pw.print(memInfo.dalvikSharedDirty);
            pw.print(',');
            pw.print(memInfo.otherSharedDirty);
            pw.print(',');
            pw.print(memInfo.getTotalSharedDirty());
            pw.print(',');
            pw.print(memInfo.nativeSharedClean);
            pw.print(',');
            pw.print(memInfo.dalvikSharedClean);
            pw.print(',');
            pw.print(memInfo.otherSharedClean);
            pw.print(',');
            pw.print(memInfo.getTotalSharedClean());
            pw.print(',');
            pw.print(memInfo.nativePrivateDirty);
            pw.print(',');
            pw.print(memInfo.dalvikPrivateDirty);
            pw.print(',');
            pw.print(memInfo.otherPrivateDirty);
            pw.print(',');
            pw.print(memInfo.getTotalPrivateDirty());
            pw.print(',');
            pw.print(memInfo.nativePrivateClean);
            pw.print(',');
            pw.print(memInfo.dalvikPrivateClean);
            pw.print(',');
            pw.print(memInfo.otherPrivateClean);
            pw.print(',');
            pw.print(memInfo.getTotalPrivateClean());
            pw.print(',');
            pw.print(memInfo.nativeSwappedOut);
            pw.print(',');
            pw.print(memInfo.dalvikSwappedOut);
            pw.print(',');
            pw.print(memInfo.otherSwappedOut);
            pw.print(',');
            pw.print(memInfo.getTotalSwappedOut());
            pw.print(',');
            if (memInfo.hasSwappedOutPss) {
                pw.print(memInfo.nativeSwappedOutPss);
                pw.print(',');
                pw.print(memInfo.dalvikSwappedOutPss);
                pw.print(',');
                pw.print(memInfo.otherSwappedOutPss);
                pw.print(',');
                pw.print(memInfo.getTotalSwappedOutPss());
                pw.print(',');
            } else {
                pw.print("N/A,");
                pw.print("N/A,");
                pw.print("N/A,");
                pw.print("N/A,");
            }
            for (int i2 = 0; i2 < 17; i2++) {
                pw.print(Debug.MemoryInfo.getOtherLabel(i2));
                pw.print(',');
                pw.print(memInfo.getOtherPss(i2));
                pw.print(',');
                pw.print(memInfo.getOtherSwappablePss(i2));
                pw.print(',');
                pw.print(memInfo.getOtherSharedDirty(i2));
                pw.print(',');
                pw.print(memInfo.getOtherSharedClean(i2));
                pw.print(',');
                pw.print(memInfo.getOtherPrivateDirty(i2));
                pw.print(',');
                pw.print(memInfo.getOtherPrivateClean(i2));
                pw.print(',');
                pw.print(memInfo.getOtherSwappedOut(i2));
                pw.print(',');
                if (memInfo.hasSwappedOutPss) {
                    pw.print(memInfo.getOtherSwappedOutPss(i2));
                    pw.print(',');
                } else {
                    pw.print("N/A,");
                }
            }
            return;
        }
        if (dumpSummaryOnly) {
            str = " ";
        } else {
            if (dumpFullInfo) {
                Object[] objArr = new Object[12];
                objArr[0] = "";
                objArr[1] = "Pss";
                objArr[2] = "Pss";
                objArr[3] = "Shared";
                objArr[4] = "Private";
                objArr[5] = "Shared";
                objArr[6] = "Private";
                objArr[7] = memInfo.hasSwappedOutPss ? "SwapPss" : "Swap";
                objArr[8] = "Rss";
                objArr[9] = "Heap";
                objArr[10] = "Heap";
                objArr[11] = "Heap";
                printRow(pw, HEAP_FULL_COLUMN, objArr);
                printRow(pw, HEAP_FULL_COLUMN, "", SemShareConstants.DMA_SURVEY_FEATURE_SMART_SHARE_TOTAL_SHARE, "Clean", "Dirty", "Dirty", "Clean", "Clean", "Dirty", SemShareConstants.DMA_SURVEY_FEATURE_SMART_SHARE_TOTAL_SHARE, "Size", "Alloc", "Free");
                printRow(pw, HEAP_FULL_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                Object[] objArr2 = new Object[12];
                objArr2[0] = "Native Heap";
                objArr2[1] = Integer.valueOf(memInfo.nativePss);
                objArr2[2] = Integer.valueOf(memInfo.nativeSwappablePss);
                objArr2[3] = Integer.valueOf(memInfo.nativeSharedDirty);
                objArr2[4] = Integer.valueOf(memInfo.nativePrivateDirty);
                objArr2[5] = Integer.valueOf(memInfo.nativeSharedClean);
                objArr2[6] = Integer.valueOf(memInfo.nativePrivateClean);
                objArr2[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.nativeSwappedOutPss : memInfo.nativeSwappedOut);
                objArr2[8] = Integer.valueOf(memInfo.nativeRss);
                objArr2[9] = Long.valueOf(nativeMax);
                objArr2[10] = Long.valueOf(nativeAllocated);
                objArr2[11] = Long.valueOf(nativeFree);
                printRow(pw, HEAP_FULL_COLUMN, objArr2);
                Object[] objArr3 = new Object[12];
                objArr3[0] = "Dalvik Heap";
                objArr3[1] = Integer.valueOf(memInfo.dalvikPss);
                objArr3[2] = Integer.valueOf(memInfo.dalvikSwappablePss);
                objArr3[3] = Integer.valueOf(memInfo.dalvikSharedDirty);
                objArr3[4] = Integer.valueOf(memInfo.dalvikPrivateDirty);
                objArr3[5] = Integer.valueOf(memInfo.dalvikSharedClean);
                objArr3[6] = Integer.valueOf(memInfo.dalvikPrivateClean);
                objArr3[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.dalvikSwappedOutPss : memInfo.dalvikSwappedOut);
                objArr3[8] = Integer.valueOf(memInfo.dalvikRss);
                objArr3[9] = Long.valueOf(dalvikMax);
                objArr3[10] = Long.valueOf(dalvikAllocated);
                objArr3[11] = Long.valueOf(dalvikFree);
                printRow(pw, HEAP_FULL_COLUMN, objArr3);
            } else {
                Object[] objArr4 = new Object[9];
                objArr4[0] = "";
                objArr4[1] = "Pss";
                objArr4[2] = "Private";
                objArr4[3] = "Private";
                objArr4[4] = memInfo.hasSwappedOutPss ? "SwapPss" : "Swap";
                objArr4[5] = "Rss";
                objArr4[6] = "Heap";
                objArr4[7] = "Heap";
                objArr4[8] = "Heap";
                printRow(pw, HEAP_COLUMN, objArr4);
                printRow(pw, HEAP_COLUMN, "", SemShareConstants.DMA_SURVEY_FEATURE_SMART_SHARE_TOTAL_SHARE, "Dirty", "Clean", "Dirty", SemShareConstants.DMA_SURVEY_FEATURE_SMART_SHARE_TOTAL_SHARE, "Size", "Alloc", "Free");
                printRow(pw, HEAP_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                Object[] objArr5 = new Object[9];
                objArr5[0] = "Native Heap";
                objArr5[1] = Integer.valueOf(memInfo.nativePss);
                objArr5[2] = Integer.valueOf(memInfo.nativePrivateDirty);
                objArr5[3] = Integer.valueOf(memInfo.nativePrivateClean);
                objArr5[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.nativeSwappedOutPss : memInfo.nativeSwappedOut);
                objArr5[5] = Integer.valueOf(memInfo.nativeRss);
                objArr5[6] = Long.valueOf(nativeMax);
                objArr5[7] = Long.valueOf(nativeAllocated);
                objArr5[8] = Long.valueOf(nativeFree);
                printRow(pw, HEAP_COLUMN, objArr5);
                Object[] objArr6 = new Object[9];
                objArr6[0] = "Dalvik Heap";
                objArr6[1] = Integer.valueOf(memInfo.dalvikPss);
                objArr6[2] = Integer.valueOf(memInfo.dalvikPrivateDirty);
                objArr6[3] = Integer.valueOf(memInfo.dalvikPrivateClean);
                objArr6[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.dalvikSwappedOutPss : memInfo.dalvikSwappedOut);
                objArr6[5] = Integer.valueOf(memInfo.dalvikRss);
                objArr6[6] = Long.valueOf(dalvikMax);
                objArr6[7] = Long.valueOf(dalvikAllocated);
                objArr6[8] = Long.valueOf(dalvikFree);
                printRow(pw, HEAP_COLUMN, objArr6);
            }
            int otherPss2 = memInfo.otherPss;
            int otherSwappablePss = memInfo.otherSwappablePss;
            int otherSharedDirty = memInfo.otherSharedDirty;
            int otherPrivateDirty = memInfo.otherPrivateDirty;
            int otherSharedClean = memInfo.otherSharedClean;
            int otherPss3 = memInfo.otherPrivateClean;
            int otherPrivateClean = otherPss3;
            int otherPrivateClean2 = memInfo.otherSwappedOut;
            int otherSwappedOut = otherPrivateClean2;
            int otherSwappedOut2 = memInfo.otherSwappedOutPss;
            int otherSwappedOutPss = otherSwappedOut2;
            int otherSwappedOutPss2 = memInfo.otherRss;
            int otherSwappablePss2 = otherSwappedOutPss2;
            int otherRss2 = otherPss2;
            int otherPss4 = otherSharedClean;
            int otherSharedClean2 = otherPrivateDirty;
            int otherPrivateDirty2 = otherSharedDirty;
            int otherSharedDirty2 = otherSwappablePss;
            for (0; i < 17; i + 1) {
                int myPss = memInfo.getOtherPss(i);
                int mySwappablePss = memInfo.getOtherSwappablePss(i);
                int mySharedDirty = memInfo.getOtherSharedDirty(i);
                int myPrivateDirty = memInfo.getOtherPrivateDirty(i);
                int mySharedClean = memInfo.getOtherSharedClean(i);
                int myPrivateClean = memInfo.getOtherPrivateClean(i);
                int mySwappedOut = memInfo.getOtherSwappedOut(i);
                int mySwappedOutPss = memInfo.getOtherSwappedOutPss(i);
                int myRss = memInfo.getOtherRss(i);
                if (myPss == 0 && mySharedDirty == 0 && myPrivateDirty == 0 && mySharedClean == 0 && myPrivateClean == 0 && myRss == 0) {
                    i = (memInfo.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut) == 0 ? i + 1 : 0;
                }
                if (dumpFullInfo) {
                    Object[] objArr7 = new Object[12];
                    objArr7[0] = Debug.MemoryInfo.getOtherLabel(i);
                    objArr7[1] = Integer.valueOf(myPss);
                    objArr7[2] = Integer.valueOf(mySwappablePss);
                    objArr7[3] = Integer.valueOf(mySharedDirty);
                    objArr7[4] = Integer.valueOf(myPrivateDirty);
                    objArr7[5] = Integer.valueOf(mySharedClean);
                    objArr7[6] = Integer.valueOf(myPrivateClean);
                    objArr7[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut);
                    objArr7[8] = Integer.valueOf(myRss);
                    objArr7[9] = "";
                    objArr7[10] = "";
                    objArr7[11] = "";
                    printRow(pw, HEAP_FULL_COLUMN, objArr7);
                } else {
                    Object[] objArr8 = new Object[9];
                    objArr8[0] = Debug.MemoryInfo.getOtherLabel(i);
                    objArr8[1] = Integer.valueOf(myPss);
                    objArr8[2] = Integer.valueOf(myPrivateDirty);
                    objArr8[3] = Integer.valueOf(myPrivateClean);
                    objArr8[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut);
                    objArr8[5] = Integer.valueOf(myRss);
                    objArr8[6] = "";
                    objArr8[7] = "";
                    objArr8[8] = "";
                    printRow(pw, HEAP_COLUMN, objArr8);
                }
                otherRss2 -= myPss;
                otherSharedDirty2 -= mySwappablePss;
                otherPrivateDirty2 -= mySharedDirty;
                otherSharedClean2 -= myPrivateDirty;
                otherPss4 -= mySharedClean;
                otherPrivateClean -= myPrivateClean;
                otherSwappedOut -= mySwappedOut;
                otherSwappedOutPss -= mySwappedOutPss;
                otherSwappablePss2 -= myRss;
            }
            if (dumpFullInfo) {
                Object[] objArr9 = new Object[12];
                objArr9[0] = "Unknown";
                objArr9[1] = Integer.valueOf(otherRss2);
                objArr9[2] = Integer.valueOf(otherSharedDirty2);
                objArr9[3] = Integer.valueOf(otherPrivateDirty2);
                objArr9[4] = Integer.valueOf(otherSharedClean2);
                objArr9[5] = Integer.valueOf(otherPss4);
                objArr9[6] = Integer.valueOf(otherPrivateClean);
                objArr9[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut);
                objArr9[8] = Integer.valueOf(otherSwappablePss2);
                objArr9[9] = "";
                objArr9[10] = "";
                objArr9[11] = "";
                printRow(pw, HEAP_FULL_COLUMN, objArr9);
                Object[] objArr10 = new Object[12];
                objArr10[0] = "TOTAL";
                objArr10[1] = Integer.valueOf(memInfo.getTotalPss());
                objArr10[2] = Integer.valueOf(memInfo.getTotalSwappablePss());
                objArr10[3] = Integer.valueOf(memInfo.getTotalSharedDirty());
                objArr10[4] = Integer.valueOf(memInfo.getTotalPrivateDirty());
                objArr10[5] = Integer.valueOf(memInfo.getTotalSharedClean());
                objArr10[6] = Integer.valueOf(memInfo.getTotalPrivateClean());
                objArr10[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.getTotalSwappedOutPss() : memInfo.getTotalSwappedOut());
                objArr10[8] = Integer.valueOf(memInfo.getTotalRss());
                objArr10[9] = Long.valueOf(nativeMax + dalvikMax);
                objArr10[10] = Long.valueOf(nativeAllocated + dalvikAllocated);
                objArr10[11] = Long.valueOf(nativeFree + dalvikFree);
                printRow(pw, HEAP_FULL_COLUMN, objArr10);
            } else {
                Object[] objArr11 = new Object[9];
                objArr11[0] = "Unknown";
                objArr11[1] = Integer.valueOf(otherRss2);
                objArr11[2] = Integer.valueOf(otherSharedClean2);
                objArr11[3] = Integer.valueOf(otherPrivateClean);
                objArr11[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut);
                objArr11[5] = Integer.valueOf(otherSwappablePss2);
                objArr11[6] = "";
                objArr11[7] = "";
                objArr11[8] = "";
                printRow(pw, HEAP_COLUMN, objArr11);
                Object[] objArr12 = new Object[9];
                objArr12[0] = "TOTAL";
                objArr12[1] = Integer.valueOf(memInfo.getTotalPss());
                objArr12[2] = Integer.valueOf(memInfo.getTotalPrivateDirty());
                objArr12[3] = Integer.valueOf(memInfo.getTotalPrivateClean());
                objArr12[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? memInfo.getTotalSwappedOutPss() : memInfo.getTotalSwappedOut());
                objArr12[5] = Integer.valueOf(memInfo.getTotalRss());
                objArr12[6] = Long.valueOf(nativeMax + dalvikMax);
                objArr12[7] = Long.valueOf(nativeAllocated + dalvikAllocated);
                objArr12[8] = Long.valueOf(nativeFree + dalvikFree);
                printRow(pw, HEAP_COLUMN, objArr12);
            }
            if (dumpDalvik) {
                str = " ";
                pw.println(str);
                pw.println(" Dalvik Details");
                int i3 = 17;
                while (i3 < 32) {
                    int myPss2 = memInfo.getOtherPss(i3);
                    int mySwappablePss2 = memInfo.getOtherSwappablePss(i3);
                    int mySharedDirty2 = memInfo.getOtherSharedDirty(i3);
                    int myPrivateDirty2 = memInfo.getOtherPrivateDirty(i3);
                    int mySharedClean2 = memInfo.getOtherSharedClean(i3);
                    int myPrivateClean2 = memInfo.getOtherPrivateClean(i3);
                    int mySwappedOut2 = memInfo.getOtherSwappedOut(i3);
                    int mySwappedOutPss2 = memInfo.getOtherSwappedOutPss(i3);
                    int myRss2 = memInfo.getOtherRss(i3);
                    if (myPss2 != 0 || mySharedDirty2 != 0 || myPrivateDirty2 != 0 || mySharedClean2 != 0 || myPrivateClean2 != 0) {
                        otherPss = otherRss2;
                    } else {
                        otherPss = otherRss2;
                        if ((memInfo.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2) == 0) {
                            otherRss = otherSwappablePss2;
                            i3++;
                            otherSwappablePss2 = otherRss;
                            otherRss2 = otherPss;
                        }
                    }
                    if (dumpFullInfo) {
                        otherRss = otherSwappablePss2;
                        Object[] objArr13 = new Object[12];
                        objArr13[0] = Debug.MemoryInfo.getOtherLabel(i3);
                        objArr13[1] = Integer.valueOf(myPss2);
                        objArr13[2] = Integer.valueOf(mySwappablePss2);
                        objArr13[3] = Integer.valueOf(mySharedDirty2);
                        objArr13[4] = Integer.valueOf(myPrivateDirty2);
                        objArr13[5] = Integer.valueOf(mySharedClean2);
                        objArr13[6] = Integer.valueOf(myPrivateClean2);
                        objArr13[7] = Integer.valueOf(memInfo.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2);
                        objArr13[8] = Integer.valueOf(myRss2);
                        objArr13[9] = "";
                        objArr13[10] = "";
                        objArr13[11] = "";
                        printRow(pw, HEAP_FULL_COLUMN, objArr13);
                    } else {
                        otherRss = otherSwappablePss2;
                        Object[] objArr14 = new Object[9];
                        objArr14[0] = Debug.MemoryInfo.getOtherLabel(i3);
                        objArr14[1] = Integer.valueOf(myPss2);
                        objArr14[2] = Integer.valueOf(myPrivateDirty2);
                        objArr14[3] = Integer.valueOf(myPrivateClean2);
                        objArr14[4] = Integer.valueOf(memInfo.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2);
                        objArr14[5] = Integer.valueOf(myRss2);
                        objArr14[6] = "";
                        objArr14[7] = "";
                        objArr14[8] = "";
                        printRow(pw, HEAP_COLUMN, objArr14);
                    }
                    i3++;
                    otherSwappablePss2 = otherRss;
                    otherRss2 = otherPss;
                }
            } else {
                str = " ";
            }
        }
        pw.println(str);
        pw.println(" App Summary");
        printRow(pw, TWO_COUNT_COLUMN_HEADER, "", "Pss(KB)", "", "Rss(KB)");
        printRow(pw, TWO_COUNT_COLUMN_HEADER, "", "------", "", "------");
        printRow(pw, TWO_COUNT_COLUMNS, "Java Heap:", Integer.valueOf(memInfo.getSummaryJavaHeap()), "", Integer.valueOf(memInfo.getSummaryJavaHeapRss()));
        printRow(pw, TWO_COUNT_COLUMNS, "Native Heap:", Integer.valueOf(memInfo.getSummaryNativeHeap()), "", Integer.valueOf(memInfo.getSummaryNativeHeapRss()));
        printRow(pw, TWO_COUNT_COLUMNS, "Code:", Integer.valueOf(memInfo.getSummaryCode()), "", Integer.valueOf(memInfo.getSummaryCodeRss()));
        printRow(pw, TWO_COUNT_COLUMNS, "Stack:", Integer.valueOf(memInfo.getSummaryStack()), "", Integer.valueOf(memInfo.getSummaryStackRss()));
        printRow(pw, TWO_COUNT_COLUMNS, "Graphics:", Integer.valueOf(memInfo.getSummaryGraphics()), "", Integer.valueOf(memInfo.getSummaryGraphicsRss()));
        printRow(pw, ONE_COUNT_COLUMN, "Private Other:", Integer.valueOf(memInfo.getSummaryPrivateOther()));
        printRow(pw, ONE_COUNT_COLUMN, "System:", Integer.valueOf(memInfo.getSummarySystem()));
        printRow(pw, ONE_ALT_COUNT_COLUMN, "Unknown:", "", "", Integer.valueOf(memInfo.getSummaryUnknownRss()));
        pw.println(str);
        if (memInfo.hasSwappedOutPss) {
            printRow(pw, THREE_COUNT_COLUMNS, "TOTAL PSS:", Integer.valueOf(memInfo.getSummaryTotalPss()), "TOTAL RSS:", Integer.valueOf(memInfo.getTotalRss()), "TOTAL SWAP PSS:", Integer.valueOf(memInfo.getSummaryTotalSwapPss()));
        } else {
            printRow(pw, THREE_COUNT_COLUMNS, "TOTAL PSS:", Integer.valueOf(memInfo.getSummaryTotalPss()), "TOTAL RSS:", Integer.valueOf(memInfo.getTotalRss()), "TOTAL SWAP (KB):", Integer.valueOf(memInfo.getSummaryTotalSwap()));
        }
    }

    private static void dumpMemoryInfo(ProtoOutputStream proto, long fieldId, String name, int pss, int cleanPss, int sharedDirty, int privateDirty, int sharedClean, int privateClean, boolean hasSwappedOutPss, int dirtySwap, int dirtySwapPss, int rss) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, name);
        proto.write(1120986464258L, pss);
        proto.write(1120986464259L, cleanPss);
        proto.write(1120986464260L, sharedDirty);
        proto.write(1120986464261L, privateDirty);
        proto.write(1120986464262L, sharedClean);
        proto.write(1120986464263L, privateClean);
        if (hasSwappedOutPss) {
            proto.write(1120986464265L, dirtySwapPss);
        } else {
            proto.write(1120986464264L, dirtySwap);
        }
        proto.write(1120986464266L, rss);
        proto.end(token);
    }

    public static void dumpMemInfoTable(ProtoOutputStream proto, Debug.MemoryInfo memInfo, boolean dumpDalvik, boolean dumpSummaryOnly, long nativeMax, long nativeAllocated, long nativeFree, long dalvikMax, long dalvikAllocated, long dalvikFree) {
        int i;
        long tToken;
        long dvToken;
        int i2;
        if (!dumpSummaryOnly) {
            long nhToken = proto.start(1146756268035L);
            dumpMemoryInfo(proto, 1146756268033L, "Native Heap", memInfo.nativePss, memInfo.nativeSwappablePss, memInfo.nativeSharedDirty, memInfo.nativePrivateDirty, memInfo.nativeSharedClean, memInfo.nativePrivateClean, memInfo.hasSwappedOutPss, memInfo.nativeSwappedOut, memInfo.nativeSwappedOutPss, memInfo.nativeRss);
            proto.write(1120986464258L, nativeMax);
            proto.write(1120986464259L, nativeAllocated);
            proto.write(1120986464260L, nativeFree);
            proto.end(nhToken);
            long dvToken2 = proto.start(1146756268036L);
            dumpMemoryInfo(proto, 1146756268033L, "Dalvik Heap", memInfo.dalvikPss, memInfo.dalvikSwappablePss, memInfo.dalvikSharedDirty, memInfo.dalvikPrivateDirty, memInfo.dalvikSharedClean, memInfo.dalvikPrivateClean, memInfo.hasSwappedOutPss, memInfo.dalvikSwappedOut, memInfo.dalvikSwappedOutPss, memInfo.dalvikRss);
            proto.write(1120986464258L, dalvikMax);
            proto.write(1120986464259L, dalvikAllocated);
            proto.write(1120986464260L, dalvikFree);
            long dvToken3 = dvToken2;
            proto.end(dvToken3);
            int otherPss = memInfo.otherPss;
            int otherSwappablePss = memInfo.otherSwappablePss;
            int otherSharedDirty = memInfo.otherSharedDirty;
            int otherPrivateDirty = memInfo.otherPrivateDirty;
            int otherSharedClean = memInfo.otherSharedClean;
            int otherPrivateClean = memInfo.otherPrivateClean;
            int otherPss2 = otherPss;
            int otherPss3 = memInfo.otherSwappedOut;
            int otherSwappedOut = otherPss3;
            int otherSwappedOut2 = memInfo.otherSwappedOutPss;
            int otherSwappedOutPss = otherSwappedOut2;
            int otherSwappedOutPss2 = memInfo.otherRss;
            int otherSwappablePss2 = otherSwappablePss;
            int otherSharedDirty2 = otherSharedDirty;
            int otherPrivateDirty2 = otherPrivateDirty;
            int otherSharedClean2 = otherSharedClean;
            int otherPrivateClean2 = otherPrivateClean;
            int otherPrivateClean3 = 0;
            int otherRss = otherSwappedOutPss2;
            while (otherPrivateClean3 < 17) {
                int myPss = memInfo.getOtherPss(otherPrivateClean3);
                int mySwappablePss = memInfo.getOtherSwappablePss(otherPrivateClean3);
                int mySharedDirty = memInfo.getOtherSharedDirty(otherPrivateClean3);
                int myPrivateDirty = memInfo.getOtherPrivateDirty(otherPrivateClean3);
                int mySharedClean = memInfo.getOtherSharedClean(otherPrivateClean3);
                int myPrivateClean = memInfo.getOtherPrivateClean(otherPrivateClean3);
                int mySwappedOut = memInfo.getOtherSwappedOut(otherPrivateClean3);
                int mySwappedOutPss = memInfo.getOtherSwappedOutPss(otherPrivateClean3);
                int myRss = memInfo.getOtherRss(otherPrivateClean3);
                if (myPss == 0 && mySharedDirty == 0 && myPrivateDirty == 0 && mySharedClean == 0 && myPrivateClean == 0 && myRss == 0) {
                    if ((memInfo.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut) == 0) {
                        dvToken = dvToken3;
                        i2 = otherPrivateClean3;
                        otherPrivateClean3 = i2 + 1;
                        dvToken3 = dvToken;
                    }
                }
                dvToken = dvToken3;
                i2 = otherPrivateClean3;
                dumpMemoryInfo(proto, 2246267895813L, Debug.MemoryInfo.getOtherLabel(otherPrivateClean3), myPss, mySwappablePss, mySharedDirty, myPrivateDirty, mySharedClean, myPrivateClean, memInfo.hasSwappedOutPss, mySwappedOut, mySwappedOutPss, myRss);
                otherPss2 -= myPss;
                otherSwappablePss2 -= mySwappablePss;
                otherSharedDirty2 -= mySharedDirty;
                otherPrivateDirty2 -= myPrivateDirty;
                otherSharedClean2 -= mySharedClean;
                otherPrivateClean2 -= myPrivateClean;
                otherSwappedOut -= mySwappedOut;
                otherSwappedOutPss -= mySwappedOutPss;
                otherRss -= myRss;
                otherPrivateClean3 = i2 + 1;
                dvToken3 = dvToken;
            }
            dumpMemoryInfo(proto, 1146756268038L, "Unknown", otherPss2, otherSwappablePss2, otherSharedDirty2, otherPrivateDirty2, otherSharedClean2, otherPrivateClean2, memInfo.hasSwappedOutPss, otherSwappedOut, otherSwappedOutPss, otherRss);
            long tToken2 = proto.start(1146756268039L);
            dumpMemoryInfo(proto, 1146756268033L, "TOTAL", memInfo.getTotalPss(), memInfo.getTotalSwappablePss(), memInfo.getTotalSharedDirty(), memInfo.getTotalPrivateDirty(), memInfo.getTotalSharedClean(), memInfo.getTotalPrivateClean(), memInfo.hasSwappedOutPss, memInfo.getTotalSwappedOut(), memInfo.getTotalSwappedOutPss(), memInfo.getTotalRss());
            proto.write(1120986464258L, nativeMax + dalvikMax);
            proto.write(1120986464259L, nativeAllocated + dalvikAllocated);
            proto.write(1120986464260L, nativeFree + dalvikFree);
            long tToken3 = tToken2;
            proto.end(tToken3);
            if (dumpDalvik) {
                int i3 = 17;
                while (i3 < 32) {
                    int myPss2 = memInfo.getOtherPss(i3);
                    int mySwappablePss2 = memInfo.getOtherSwappablePss(i3);
                    int mySharedDirty2 = memInfo.getOtherSharedDirty(i3);
                    int myPrivateDirty2 = memInfo.getOtherPrivateDirty(i3);
                    int mySharedClean2 = memInfo.getOtherSharedClean(i3);
                    int myPrivateClean2 = memInfo.getOtherPrivateClean(i3);
                    int mySwappedOut2 = memInfo.getOtherSwappedOut(i3);
                    int mySwappedOutPss2 = memInfo.getOtherSwappedOutPss(i3);
                    int myRss2 = memInfo.getOtherRss(i3);
                    if (myPss2 == 0 && mySharedDirty2 == 0 && myPrivateDirty2 == 0 && mySharedClean2 == 0 && myPrivateClean2 == 0) {
                        if ((memInfo.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2) == 0) {
                            i = i3;
                            tToken = tToken3;
                            i3 = i + 1;
                            tToken3 = tToken;
                        }
                    }
                    i = i3;
                    tToken = tToken3;
                    dumpMemoryInfo(proto, 2246267895816L, Debug.MemoryInfo.getOtherLabel(i3), myPss2, mySwappablePss2, mySharedDirty2, myPrivateDirty2, mySharedClean2, myPrivateClean2, memInfo.hasSwappedOutPss, mySwappedOut2, mySwappedOutPss2, myRss2);
                    i3 = i + 1;
                    tToken3 = tToken;
                }
            }
        }
        long asToken = proto.start(1146756268041L);
        proto.write(1120986464257L, memInfo.getSummaryJavaHeap());
        proto.write(1120986464258L, memInfo.getSummaryNativeHeap());
        proto.write(1120986464259L, memInfo.getSummaryCode());
        proto.write(1120986464260L, memInfo.getSummaryStack());
        proto.write(1120986464261L, memInfo.getSummaryGraphics());
        proto.write(1120986464262L, memInfo.getSummaryPrivateOther());
        proto.write(1120986464263L, memInfo.getSummarySystem());
        if (memInfo.hasSwappedOutPss) {
            proto.write(1120986464264L, memInfo.getSummaryTotalSwapPss());
        } else {
            proto.write(1120986464264L, memInfo.getSummaryTotalSwap());
        }
        proto.write(1120986464266L, memInfo.getSummaryJavaHeapRss());
        proto.write(1120986464267L, memInfo.getSummaryNativeHeapRss());
        proto.write(1120986464268L, memInfo.getSummaryCodeRss());
        proto.write(1120986464269L, memInfo.getSummaryStackRss());
        proto.write(1120986464270L, memInfo.getSummaryGraphicsRss());
        proto.write(1120986464271L, memInfo.getSummaryUnknownRss());
        proto.end(asToken);
    }

    public void registerOnActivityPausedListener(Activity activity, OnActivityPausedListener listener) {
        synchronized (this.mOnPauseListeners) {
            ArrayList<OnActivityPausedListener> list = this.mOnPauseListeners.get(activity);
            if (list == null) {
                list = new ArrayList<>();
                this.mOnPauseListeners.put(activity, list);
            }
            list.add(listener);
        }
    }

    public void unregisterOnActivityPausedListener(Activity activity, OnActivityPausedListener listener) {
        synchronized (this.mOnPauseListeners) {
            ArrayList<OnActivityPausedListener> list = this.mOnPauseListeners.get(activity);
            if (list != null) {
                list.remove(listener);
            }
        }
    }

    public final ActivityInfo resolveActivityInfo(Intent intent) {
        ActivityInfo aInfo = intent.resolveActivityInfo(this.mInitialApplication.getPackageManager(), 1024);
        if (aInfo == null) {
            Instrumentation.checkStartActivityResult(-92, intent);
        }
        return aInfo;
    }

    public final Activity startActivityNow(Activity parent, String id, Intent intent, ActivityInfo activityInfo, IBinder token, Bundle state, Activity.NonConfigurationInstances lastNonConfigurationInstances, IBinder assistToken, IBinder shareableActivityToken) {
        ActivityClientRecord r = new ActivityClientRecord();
        r.token = token;
        r.assistToken = assistToken;
        r.shareableActivityToken = shareableActivityToken;
        r.ident = 0;
        r.intent = intent;
        r.state = state;
        r.parent = parent;
        r.embeddedID = id;
        r.activityInfo = activityInfo;
        r.lastNonConfigurationInstances = lastNonConfigurationInstances;
        return performLaunchActivity(r, null);
    }

    @Override // android.app.ClientTransactionHandler
    public final Activity getActivity(IBinder token) {
        ActivityClientRecord activityRecord = this.mActivities.get(token);
        if (activityRecord != null) {
            return activityRecord.activity;
        }
        return null;
    }

    @Override // android.app.ClientTransactionHandler
    public ActivityClientRecord getActivityClient(IBinder token) {
        return this.mActivities.get(token);
    }

    public Configuration getConfiguration() {
        return this.mConfigurationController.getConfiguration();
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingConfiguration(Configuration config) {
        Configuration updatedConfig = this.mConfigurationController.updatePendingConfiguration(config);
        if (updatedConfig != null) {
            this.mPendingConfiguration = updatedConfig;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void updateProcessState(int processState, boolean fromIpc) {
        synchronized (this.mAppThread) {
            int i = this.mLastProcessState;
            if (i == processState) {
                return;
            }
            if (ActivityManager.isProcStateCached(i) != ActivityManager.isProcStateCached(processState)) {
                updateVmProcessState(processState);
            }
            this.mLastProcessState = processState;
        }
    }

    private void updateVmProcessState(int processState) {
        int state;
        if (ActivityManager.isProcStateCached(processState)) {
            state = 1;
        } else {
            state = 0;
        }
        VMRuntime.getRuntime().updateProcessState(state);
    }

    @Override // android.app.ClientTransactionHandler
    public void countLaunchingActivities(int num) {
        this.mNumLaunchingActivities.getAndAdd(num);
    }

    public final void sendActivityResult(IBinder token, String id, int requestCode, int resultCode, Intent data) {
        ArrayList<ResultInfo> list = new ArrayList<>();
        list.add(new ResultInfo(id, requestCode, resultCode, data));
        ClientTransaction clientTransaction = ClientTransaction.obtain(this.mAppThread, token);
        clientTransaction.addCallback(ActivityResultItem.obtain(list));
        try {
            this.mAppThread.scheduleTransaction(clientTransaction);
        } catch (RemoteException e) {
        }
    }

    @Override // android.app.ClientTransactionHandler
    TransactionExecutor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    @Override // android.app.ClientTransactionHandler
    void sendMessage(int what, Object obj) {
        sendMessage(what, obj, 0, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1) {
        sendMessage(what, obj, arg1, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1, int arg2) {
        sendMessage(what, obj, arg1, arg2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1, int arg2, boolean async) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        if (async) {
            msg.setAsynchronous(true);
        }
        this.mH.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void scheduleContextCleanup(ContextImpl context, String who, String what) {
        ContextCleanupInfo cci = new ContextCleanupInfo();
        cci.context = context;
        cci.who = who;
        cci.what = what;
        sendMessage(119, cci);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02e1  */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.app.Instrumentation] */
    /* JADX WARN: Type inference failed for: r2v39, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v30, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v32, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v30, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r4v31, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r4v32 */
    /* JADX WARN: Type inference failed for: r4v34, types: [android.content.AttributionSource] */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v39 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v19, types: [android.app.Instrumentation] */
    /* JADX WARN: Type inference failed for: r6v24, types: [android.app.Instrumentation] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.app.Activity performLaunchActivity(android.app.ActivityThread.ActivityClientRecord r30, android.content.Intent r31) {
        /*
            Method dump skipped, instructions count: 831
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent):android.app.Activity");
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStartActivity(ActivityClientRecord r, PendingTransactionActions pendingActions, ActivityOptions activityOptions) {
        Activity activity = r.activity;
        if (!r.stopped) {
            throw new IllegalStateException("Can't start activity that is not stopped.");
        }
        AppBindData appBindData = this.mBoundApplication;
        if (appBindData != null) {
            setFlingerFlag(appBindData.processName, true);
        }
        if (r.activity.mFinished) {
            return;
        }
        unscheduleGcIdler();
        if (activityOptions != null) {
            activity.mPendingOptions = activityOptions;
        }
        activity.performStart("handleStartActivity");
        r.setState(2);
        if (pendingActions == null) {
            return;
        }
        if (pendingActions.shouldRestoreInstanceState()) {
            if (r.isPersistable()) {
                if (r.state != null || r.persistentState != null) {
                    this.mInstrumentation.callActivityOnRestoreInstanceState(activity, r.state, r.persistentState);
                }
            } else if (r.state != null) {
                this.mInstrumentation.callActivityOnRestoreInstanceState(activity, r.state);
            }
        }
        if (pendingActions.shouldCallOnPostCreate()) {
            activity.mCalled = false;
            Trace.traceBegin(32L, "onPostCreate");
            if (r.isPersistable()) {
                this.mInstrumentation.callActivityOnPostCreate(activity, r.state, r.persistentState);
            } else {
                this.mInstrumentation.callActivityOnPostCreate(activity, r.state);
            }
            Trace.traceEnd(32L);
            if (!activity.mCalled) {
                throw new SuperNotCalledException("Activity " + r.intent.getComponent().toShortString() + " did not call through to super.onPostCreate()");
            }
        }
        updateVisibility(r, true);
        this.mSomeActivitiesChanged = true;
    }

    private void checkAndBlockForNetworkAccess() {
        synchronized (this.mNetworkPolicyLock) {
            if (this.mNetworkBlockSeq != -1) {
                try {
                    ActivityManager.getService().waitForNetworkStateUpdate(this.mNetworkBlockSeq);
                    this.mNetworkBlockSeq = -1L;
                } catch (RemoteException e) {
                }
            }
        }
    }

    private ContextImpl createBaseContextForActivity(ActivityClientRecord r) {
        int displayId;
        if (isInDexDisplay() && r.parent != null) {
            IBinder activityToken = r.parent.getActivityToken();
            displayId = ActivityClient.getInstance().getDisplayId(activityToken);
        } else {
            displayId = ActivityClient.getInstance().getDisplayId(r.token);
        }
        ContextImpl appContext = ContextImpl.createActivityContext(this, r.packageInfo, r.activityInfo, r.token, displayId, r.overrideConfig);
        DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
        String pkgName = SystemProperties.get("debug.second-display.pkg");
        if (pkgName != null && !pkgName.isEmpty() && r.packageInfo.mPackageName.contains(pkgName)) {
            for (int id : dm.getDisplayIds()) {
                if (id != 0) {
                    Display display = dm.getCompatibleDisplay(id, appContext.getResources());
                    return (ContextImpl) appContext.createDisplayContext(display);
                }
            }
            return appContext;
        }
        return appContext;
    }

    @Override // android.app.ClientTransactionHandler
    public Activity handleLaunchActivity(ActivityClientRecord r, PendingTransactionActions pendingActions, int deviceId, Intent customIntent) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (r.profilerInfo != null) {
            this.mProfiler.setProfiler(r.profilerInfo);
            this.mProfiler.startProfiling();
        }
        this.mConfigurationController.handleConfigurationChanged(null, null);
        updateDeviceIdForNonUIContexts(deviceId);
        if (ThreadedRenderer.sRendererEnabled && (r.activityInfo.flags & 512) != 0) {
            HardwareRenderer.preload();
        }
        WindowManagerGlobal.initialize();
        GraphicsEnvironment.hintActivityLaunch();
        Activity a = performLaunchActivity(r, customIntent);
        if (a != null) {
            r.createdConfig = new Configuration(this.mConfigurationController.getConfiguration());
            reportSizeConfigurations(r);
            if (!r.activity.mFinished && pendingActions != null) {
                pendingActions.setOldState(r.state);
                pendingActions.setRestoreInstanceState(true);
                pendingActions.setCallOnPostCreate(true);
            }
        } else {
            ActivityClient.getInstance().finishActivity(r.token, 0, null, 0);
        }
        return a;
    }

    private void reportSizeConfigurations(ActivityClientRecord r) {
        Configuration[] configurations;
        if (this.mActivitiesToBeDestroyed.containsKey(r.token) || (configurations = r.activity.getResources().getSizeConfigurations()) == null) {
            return;
        }
        r.mSizeConfigurations = new SizeConfigurationBuckets(configurations);
        ActivityClient.getInstance().reportSizeConfigurations(r.token, r.mSizeConfigurations);
    }

    private void deliverNewIntents(ActivityClientRecord r, List<ReferrerIntent> intents) {
        int N = intents.size();
        for (int i = 0; i < N; i++) {
            ReferrerIntent intent = intents.get(i);
            intent.setExtrasClassLoader(r.activity.getClassLoader());
            intent.prepareToEnterProcess(isProtectedComponent(r.activityInfo), r.activity.getAttributionSource());
            r.activity.mFragments.noteStateNotSaved();
            this.mInstrumentation.callActivityOnNewIntent(r.activity, intent);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleNewIntent(ActivityClientRecord r, List<ReferrerIntent> intents) {
        checkAndBlockForNetworkAccess();
        deliverNewIntents(r, intents);
    }

    public void handleRequestAssistContextExtras(RequestAssistContextExtras cmd) {
        Uri referrer;
        AssistStructure structure;
        String structuredJson;
        boolean forAutofill = cmd.requestType == 2;
        boolean requestedOnlyContent = cmd.requestType == 3;
        if (this.mLastSessionId != cmd.sessionId) {
            this.mLastSessionId = cmd.sessionId;
            for (int i = this.mLastAssistStructures.size() - 1; i >= 0; i--) {
                AssistStructure structure2 = this.mLastAssistStructures.get(i).get();
                if (structure2 != null) {
                    structure2.clearSendChannel();
                }
                this.mLastAssistStructures.remove(i);
            }
        }
        Bundle data = new Bundle();
        AssistStructure structure3 = null;
        AssistContent content = forAutofill ? null : new AssistContent();
        long startTime = SystemClock.uptimeMillis();
        ActivityClientRecord r = this.mActivities.get(cmd.activityToken);
        Uri referrer2 = null;
        if (r == null) {
            referrer = null;
        } else {
            if (!forAutofill) {
                r.activity.getApplication().dispatchOnProvideAssistData(r.activity, data);
                r.activity.onProvideAssistData(data);
                referrer2 = r.activity.onProvideReferrer();
            }
            if (cmd.requestType == 1 || forAutofill || requestedOnlyContent) {
                if (!requestedOnlyContent) {
                    structure3 = new AssistStructure(r.activity, forAutofill, cmd.flags);
                }
                Intent activityIntent = r.activity.getIntent();
                boolean notSecure = r.window == null || (r.window.getAttributes().flags & 8192) == 0;
                if (activityIntent != null && notSecure) {
                    if (!forAutofill) {
                        Intent intent = new Intent(activityIntent);
                        intent.setFlags(intent.getFlags() & (-67));
                        content.setDefaultIntent(intent);
                    }
                } else if (!forAutofill) {
                    content.setDefaultIntent(new Intent());
                }
                if (!forAutofill) {
                    r.activity.onProvideAssistContent(content);
                }
            }
            if (!forAutofill && r.activity != null && content != null && content.getStructuredData() == null && (structuredJson = getContentFromDispatcher(r)) != null) {
                content.setStructuredData(structuredJson);
            }
            referrer = referrer2;
        }
        if (requestedOnlyContent) {
            structure = structure3;
        } else {
            if (structure3 == null) {
                structure3 = new AssistStructure();
            }
            structure3.setAcquisitionStartTime(startTime);
            structure3.setAcquisitionEndTime(SystemClock.uptimeMillis());
            this.mLastAssistStructures.add(new WeakReference<>(structure3));
            structure = structure3;
        }
        IActivityTaskManager mgr = ActivityTaskManager.getService();
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
            mgr.reportAssistContextExtras(cmd.requestToken, data, structure, content, referrer);
        } catch (RemoteException e2) {
            e = e2;
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0066, code lost:            r1.close();     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getContentFileName(java.lang.String r9) {
        /*
            r8 = this;
            r0 = r9
            android.app.ActivityThread r1 = currentActivityThread()
            android.app.Application r1 = r1.getApplication()
            android.content.ContentResolver r2 = r1.getContentResolver()
            android.net.Uri r3 = android.net.Uri.parse(r9)
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)
            if (r1 == 0) goto L63
            int r2 = r1.getCount()     // Catch: java.lang.Throwable -> L57
            if (r2 == 0) goto L63
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L57
            if (r2 != 0) goto L28
            goto L63
        L28:
            java.lang.String r2 = "_data"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L57
            if (r2 >= 0) goto L37
        L31:
            if (r1 == 0) goto L36
            r1.close()
        L36:
            return r0
        L37:
            java.lang.String r3 = r1.getString(r2)     // Catch: java.lang.Throwable -> L57
            if (r3 != 0) goto L44
        L3e:
            if (r1 == 0) goto L43
            r1.close()
        L43:
            return r0
        L44:
            java.lang.String r4 = "/"
            int r4 = r3.lastIndexOf(r4)     // Catch: java.lang.Throwable -> L57
            int r4 = r4 + 1
            java.lang.String r4 = r3.substring(r4)     // Catch: java.lang.Throwable -> L57
            r0 = r4
            if (r1 == 0) goto L56
            r1.close()
        L56:
            return r0
        L57:
            r2 = move-exception
            if (r1 == 0) goto L62
            r1.close()     // Catch: java.lang.Throwable -> L5e
            goto L62
        L5e:
            r3 = move-exception
            r2.addSuppressed(r3)
        L62:
            throw r2
        L63:
            if (r1 == 0) goto L69
            r1.close()
        L69:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getContentFileName(java.lang.String):java.lang.String");
    }

    private String getContentFromDispatcher(ActivityClientRecord r) {
        String[] strArr = {"", "", ""};
        try {
            String[] contentInfo = ActivityManager.getService().getContentByTask(r.activity.getTaskId());
            if (contentInfo == null || contentInfo[2] == null || r.activity.getApplicationInfo() == null || !contentInfo[2].equals(r.activity.getApplicationInfo().packageName)) {
                return null;
            }
            contentInfo[1] = getContentFileName(contentInfo[0]);
            String structuredJson = new JSONObject().put("file_metadata", new JSONObject().put("file_uri", contentInfo[0]).put("mime_type", "application/pdf").put("file_name", contentInfo[1]).put("is_work_profile", isWorkProfile())).toString();
            return structuredJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isWorkProfile() {
        DevicePolicyManager mDpm = (DevicePolicyManager) currentActivityThread().getApplication().getSystemService(Context.DEVICE_POLICY_SERVICE);
        List<ComponentName> activeAdmins = mDpm.getActiveAdmins();
        if (activeAdmins == null) {
            return false;
        }
        for (ComponentName activeAdmin : activeAdmins) {
            if (mDpm.isProfileOwnerApp(activeAdmin.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestDirectActions(IBinder activityToken, IVoiceInteractor interactor, CancellationSignal cancellationSignal, final RemoteCallback callback, int retryCount) {
        final ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r == null) {
            Log.w(TAG, "requestDirectActions(): no activity for " + activityToken);
            callback.sendResult(null);
            return;
        }
        int lifecycleState = r.getLifecycleState();
        if (lifecycleState < 2) {
            if (retryCount > 0) {
                this.mH.sendMessageDelayed(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.function.HexConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                        ((ActivityThread) obj).handleRequestDirectActions((IBinder) obj2, (IVoiceInteractor) obj3, (CancellationSignal) obj4, (RemoteCallback) obj5, ((Integer) obj6).intValue());
                    }
                }, this, activityToken, interactor, cancellationSignal, callback, Integer.valueOf(retryCount - 1)), 200L);
                return;
            } else {
                Log.w(TAG, "requestDirectActions(" + r + "): wrong lifecycle: " + lifecycleState);
                callback.sendResult(null);
                return;
            }
        }
        if (lifecycleState >= 5) {
            Log.w(TAG, "requestDirectActions(" + r + "): wrong lifecycle: " + lifecycleState);
            callback.sendResult(null);
            return;
        }
        if (r.activity.mVoiceInteractor == null || r.activity.mVoiceInteractor.mInteractor.asBinder() != interactor.asBinder()) {
            if (r.activity.mVoiceInteractor != null) {
                r.activity.mVoiceInteractor.destroy();
            }
            r.activity.mVoiceInteractor = new VoiceInteractor(interactor, r.activity, r.activity, Looper.myLooper());
        }
        r.activity.onGetDirectActions(cancellationSignal, new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityThread.lambda$handleRequestDirectActions$0(ActivityThread.ActivityClientRecord.this, callback, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$handleRequestDirectActions$0(ActivityClientRecord r, RemoteCallback callback, List actions) {
        Objects.requireNonNull(actions);
        Preconditions.checkCollectionElementsNotNull(actions, Slice.HINT_ACTIONS);
        if (!actions.isEmpty()) {
            int actionCount = actions.size();
            for (int i = 0; i < actionCount; i++) {
                DirectAction action = (DirectAction) actions.get(i);
                action.setSource(r.activity.getTaskId(), r.activity.getAssistToken());
            }
            Bundle result = new Bundle();
            result.putParcelable(DirectAction.KEY_ACTIONS_LIST, new ParceledListSlice(actions));
            callback.sendResult(result);
            return;
        }
        callback.sendResult(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePerformDirectAction(IBinder activityToken, String actionId, Bundle arguments, CancellationSignal cancellationSignal, final RemoteCallback resultCallback) {
        ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r != null) {
            int lifecycleState = r.getLifecycleState();
            if (lifecycleState < 2 || lifecycleState >= 5) {
                resultCallback.sendResult(null);
                return;
            }
            Bundle nonNullArguments = arguments != null ? arguments : Bundle.EMPTY;
            Activity activity = r.activity;
            Objects.requireNonNull(resultCallback);
            activity.onPerformDirectAction(actionId, nonNullArguments, cancellationSignal, new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteCallback.this.sendResult((Bundle) obj);
                }
            });
            return;
        }
        resultCallback.sendResult(null);
    }

    public void handleTranslucentConversionComplete(IBinder token, boolean drawComplete) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.onTranslucentConversionComplete(drawComplete);
        }
    }

    public void onNewActivityOptions(IBinder token, ActivityOptions options) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.onNewActivityOptions(options);
        }
    }

    public void handleInstallProvider(ProviderInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            installContentProviders(this.mInitialApplication, Arrays.asList(info));
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEnterAnimationComplete(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.dispatchEnterAnimationComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartBinderTracking() {
        if (Binder.isSystemServerBinderTrackerEnabled) {
            Binder.getTransactionTracker().clearTraces();
        }
        Binder.enableStackTracking();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(ParcelFileDescriptor fd) {
        try {
            Binder.disableStackTracking();
            Binder.getTransactionTracker().writeTracesToFile(fd);
        } finally {
            IoUtils.closeQuietly(fd);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(ParcelFileDescriptor fd, String _processName, String _packageName, int _pid, int _uid) {
        try {
            Binder.getTransactionTracker().setBinderInfo(_pid, _uid, _processName, _packageName);
            Binder.disableStackTracking();
            Binder.getTransactionTracker().writeTracesToFile(fd);
        } finally {
            IoUtils.closeQuietly(fd);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureRequested(ActivityClientRecord r) {
        boolean receivedByApp = r.activity.onPictureInPictureRequested();
        if (!receivedByApp) {
            schedulePauseWithUserLeaveHintAndReturnToCurrentState(r);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureStateChanged(ActivityClientRecord r, PictureInPictureUiState pipState) {
        r.activity.onPictureInPictureUiStateChanged(pipState);
    }

    public void registerSplashScreenManager(SplashScreen.SplashScreenManagerGlobal manager) {
        synchronized (this) {
            this.mSplashScreenGlobal = manager;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public boolean isHandleSplashScreenExit(IBinder token) {
        boolean z;
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            z = splashScreenManagerGlobal != null && splashScreenManagerGlobal.containsExitListener(token);
        }
        return z;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleAttachSplashScreenView(ActivityClientRecord r, SplashScreenView.SplashScreenViewParcelable parcelable, SurfaceControl startingWindowLeash) {
        DecorView decorView = (DecorView) r.window.peekDecorView();
        if (parcelable != null && decorView != null) {
            createSplashScreen(r, decorView, parcelable, startingWindowLeash);
        } else {
            Slog.e(TAG, "handleAttachSplashScreenView failed, unable to attach");
        }
    }

    private void createSplashScreen(ActivityClientRecord r, DecorView decorView, SplashScreenView.SplashScreenViewParcelable parcelable, SurfaceControl startingWindowLeash) {
        SplashScreenView.Builder builder = new SplashScreenView.Builder(r.activity);
        SplashScreenView view = builder.createFromParcel(parcelable).build();
        view.attachHostWindow(r.window);
        decorView.addView(view);
        view.requestLayout();
        view.getViewTreeObserver().addOnPreDrawListener(new AnonymousClass2(view, r, decorView, startingWindowLeash));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.app.ActivityThread$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements ViewTreeObserver.OnPreDrawListener {
        private boolean mHandled = false;
        final /* synthetic */ DecorView val$decorView;
        final /* synthetic */ ActivityClientRecord val$r;
        final /* synthetic */ SurfaceControl val$startingWindowLeash;
        final /* synthetic */ SplashScreenView val$view;

        AnonymousClass2(SplashScreenView splashScreenView, ActivityClientRecord activityClientRecord, DecorView decorView, SurfaceControl surfaceControl) {
            this.val$view = splashScreenView;
            this.val$r = activityClientRecord;
            this.val$decorView = decorView;
            this.val$startingWindowLeash = surfaceControl;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (this.mHandled) {
                return true;
            }
            this.mHandled = true;
            ActivityThread.this.syncTransferSplashscreenViewTransaction(this.val$view, this.val$r.token, this.val$decorView, this.val$startingWindowLeash);
            final SplashScreenView splashScreenView = this.val$view;
            splashScreenView.post(new Runnable() { // from class: android.app.ActivityThread$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityThread.AnonymousClass2.this.lambda$onPreDraw$0(splashScreenView);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$0(SplashScreenView view) {
            view.getViewTreeObserver().removeOnPreDrawListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportSplashscreenViewShown, reason: merged with bridge method [inline-methods] */
    public void lambda$syncTransferSplashscreenViewTransaction$1(IBinder token, SplashScreenView view) {
        ActivityClient.getInstance().reportSplashScreenAttached(token);
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            if (splashScreenManagerGlobal != null) {
                splashScreenManagerGlobal.handOverSplashScreenView(token, view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncTransferSplashscreenViewTransaction(final SplashScreenView view, final IBinder token, View decorView, SurfaceControl startingWindowLeash) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.hide(startingWindowLeash);
        decorView.getViewRootImpl().applyTransactionOnDraw(transaction);
        view.syncTransferSurfaceOnDraw();
        decorView.postOnAnimation(new Runnable() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ActivityThread.this.lambda$syncTransferSplashscreenViewTransaction$1(token, view);
            }
        });
    }

    private void schedulePauseWithUserLeaveHintAndReturnToCurrentState(ActivityClientRecord r) {
        int prevState = r.getLifecycleState();
        if (prevState != 3 && prevState != 4) {
            return;
        }
        switch (prevState) {
            case 3:
                schedulePauseWithUserLeavingHint(r);
                scheduleResume(r);
                return;
            case 4:
                scheduleResume(r);
                schedulePauseWithUserLeavingHint(r);
                return;
            default:
                return;
        }
    }

    private void schedulePauseWithUserLeavingHint(ActivityClientRecord r) {
        ClientTransaction transaction = ClientTransaction.obtain(this.mAppThread, r.token);
        transaction.setLifecycleStateRequest(PauseActivityItem.obtain(r.activity.isFinishing(), true, r.activity.mConfigChangeFlags, false, false));
        executeTransaction(transaction);
    }

    private void scheduleResume(ActivityClientRecord r) {
        ClientTransaction transaction = ClientTransaction.obtain(this.mAppThread, r.token);
        transaction.setLifecycleStateRequest(ResumeActivityItem.obtain(false, false));
        executeTransaction(transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor interactor) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.voiceInteractor = interactor;
            r.activity.setVoiceInteractor(interactor);
            if (interactor == null) {
                r.activity.onLocalVoiceInteractionStopped();
            } else {
                r.activity.onLocalVoiceInteractionStarted();
            }
        }
    }

    private static boolean attemptAttachAgent(String agent, ClassLoader classLoader) {
        try {
            VMDebug.attachAgent(agent, classLoader);
            return true;
        } catch (IOException e) {
            Slog.e(TAG, "Attaching agent with " + classLoader + " failed: " + agent);
            return false;
        }
    }

    static void handleAttachAgent(String agent, LoadedApk loadedApk) {
        ClassLoader classLoader = loadedApk != null ? loadedApk.getClassLoader() : null;
        if (!attemptAttachAgent(agent, classLoader) && classLoader != null) {
            attemptAttachAgent(agent, null);
        }
    }

    static void handleAttachStartupAgents(String dataDir) {
        try {
            Path codeCache = ContextImpl.getCodeCacheDirBeforeBind(new File(dataDir)).toPath();
            if (!Files.exists(codeCache, new LinkOption[0])) {
                return;
            }
            Path startupPath = codeCache.resolve("startup_agents");
            if (Files.exists(startupPath, new LinkOption[0])) {
                DirectoryStream<Path> startupFiles = Files.newDirectoryStream(startupPath);
                try {
                    for (Path p : startupFiles) {
                        handleAttachAgent(p.toAbsolutePath().toString() + "=" + dataDir, null);
                    }
                    if (startupFiles != null) {
                        startupFiles.close();
                    }
                } finally {
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTranslationState(IBinder activityToken, int state, TranslationSpec sourceSpec, TranslationSpec targetSpec, List<AutofillId> viewIds, UiTranslationSpec uiTranslationSpec) {
        ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r == null) {
            Log.w(TAG, "updateUiTranslationState(): no activity for " + activityToken);
        } else {
            r.activity.updateUiTranslationState(state, sourceSpec, targetSpec, viewIds, uiTranslationSpec);
        }
    }

    public static Intent getIntentBeingBroadcast() {
        return sCurrentBroadcastIntent.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(13:(2:17|(13:19|20|21|23|24|(1:26)(1:57)|27|(4:31|32|33|(2:35|36)(1:38))|46|(5:48|49|50|51|(1:53)(1:54))(1:56)|32|33|(0)(0)))|20|21|23|24|(0)(0)|27|(4:31|32|33|(0)(0))|46|(0)(0)|32|33|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x016d, code lost:            r0 = e;     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0169, code lost:            r0 = th;     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01b2, code lost:            android.app.ActivityThread.sCurrentBroadcastIntent.set(null);     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01b8, code lost:            throw r0;     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e8 A[Catch: all -> 0x0169, Exception -> 0x016d, TRY_LEAVE, TryCatch #6 {Exception -> 0x016d, all -> 0x0169, blocks: (B:24:0x00bd, B:27:0x00d7, B:46:0x00e4, B:48:0x00e8), top: B:23:0x00bd }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleReceiver(android.app.ActivityThread.ReceiverData r19) {
        /*
            Method dump skipped, instructions count: 484
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleReceiver(android.app.ActivityThread$ReceiverData):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateBackupAgent(CreateBackupAgentData data) {
        IBinder binder;
        try {
            PackageInfo requestedPackage = getPackageManager().getPackageInfo(data.appInfo.packageName, 0L, UserHandle.myUserId());
            if (requestedPackage.applicationInfo.uid != Process.myUid()) {
                Slog.w(TAG, "Asked to instantiate non-matching package " + data.appInfo.packageName);
                return;
            }
            unscheduleGcIdler();
            LoadedApk packageInfo = getPackageInfoNoCheck(data.appInfo);
            String packageName = packageInfo.mPackageName;
            if (packageName == null) {
                Slog.d(TAG, "Asked to create backup agent for nonexistent package");
                return;
            }
            String classname = getBackupAgentName(data);
            IBinder binder2 = null;
            try {
                Map backupAgentsForUser = getBackupAgentsForUser(data.userId);
                BackupAgent agent = (BackupAgent) backupAgentsForUser.get(packageName);
                if (agent != null) {
                    binder = agent.onBind();
                } else {
                    try {
                        ClassLoader cl = packageInfo.getClassLoader();
                        BackupAgent backupAgent = (BackupAgent) cl.loadClass(classname).newInstance();
                        ContextImpl createAppContext = ContextImpl.createAppContext(this, packageInfo);
                        createAppContext.setOuterContext(backupAgent);
                        backupAgent.attach(createAppContext);
                        backupAgent.onCreate(UserHandle.of(data.userId), data.backupDestination, getOperationTypeFromBackupMode(data.backupMode));
                        binder2 = backupAgent.onBind();
                        backupAgentsForUser.put(packageName, backupAgent);
                        binder = binder2;
                    } catch (Exception e) {
                        Slog.e(TAG, "Agent threw during creation: " + e);
                        if (data.backupMode != 2 && data.backupMode != 3) {
                            throw e;
                        }
                        binder = binder2;
                    }
                }
                try {
                    ActivityManager.getService().backupAgentCreated(packageName, binder, data.userId);
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            } catch (Exception e3) {
                throw new RuntimeException("Unable to create BackupAgent " + classname + ": " + e3.toString(), e3);
            }
        } catch (RemoteException e4) {
            throw e4.rethrowFromSystemServer();
        }
    }

    private static int getOperationTypeFromBackupMode(int backupMode) {
        switch (backupMode) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 1;
            default:
                Slog.w(TAG, "Invalid backup mode when initialising BackupAgent: " + backupMode);
                return -1;
        }
    }

    private String getBackupAgentName(CreateBackupAgentData data) {
        String agentName = data.appInfo.backupAgentName;
        if (agentName != null) {
            return agentName;
        }
        if (data.backupMode == 1 || data.backupMode == 3) {
            return DEFAULT_FULL_BACKUP_AGENT;
        }
        return agentName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroyBackupAgent(CreateBackupAgentData data) {
        LoadedApk packageInfo = getPackageInfoNoCheck(data.appInfo);
        String packageName = packageInfo.mPackageName;
        ArrayMap<String, BackupAgent> backupAgents = getBackupAgentsForUser(data.userId);
        BackupAgent agent = backupAgents.get(packageName);
        if (agent == null) {
            Slog.w(TAG, "Attempt to destroy unknown backup agent " + data);
            return;
        }
        try {
            agent.onDestroy();
        } catch (Exception e) {
            Slog.w(TAG, "Exception thrown in onDestroy by backup agent of " + data.appInfo);
            e.printStackTrace();
        }
        backupAgents.remove(packageName);
    }

    private ArrayMap<String, BackupAgent> getBackupAgentsForUser(int userId) {
        ArrayMap<String, BackupAgent> backupAgents = this.mBackupAgentsByUser.get(userId);
        if (backupAgents == null) {
            ArrayMap<String, BackupAgent> backupAgents2 = new ArrayMap<>();
            this.mBackupAgentsByUser.put(userId, backupAgents2);
            return backupAgents2;
        }
        return backupAgents;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateService(CreateServiceData data) {
        ClassLoader cl;
        ContextImpl context;
        unscheduleGcIdler();
        LoadedApk packageInfo = getPackageInfoNoCheck(data.info.applicationInfo);
        Service service = null;
        try {
            Application app = packageInfo.makeApplicationInner(false, this.mInstrumentation);
            if (data.info.splitName != null) {
                cl = packageInfo.getSplitClassLoader(data.info.splitName);
            } else {
                cl = packageInfo.getClassLoader();
            }
            service = packageInfo.getAppFactory().instantiateService(cl, data.info.name, data.intent);
            ContextImpl context2 = ContextImpl.getImpl(service.createServiceBaseContext(this, packageInfo));
            if (data.info.splitName != null) {
                context2 = (ContextImpl) context2.createContextForSplit(data.info.splitName);
            }
            if (data.info.attributionTags != null && data.info.attributionTags.length > 0) {
                String attributionTag = data.info.attributionTags[0];
                context = (ContextImpl) context2.createAttributionContext(attributionTag);
            } else {
                context = context2;
            }
            context.getResources().addLoaders((ResourcesLoader[]) app.getResources().getLoaders().toArray(new ResourcesLoader[0]));
            context.setOuterContext(service);
            service.attach(context, this, data.info.name, data.token, app, ActivityManager.getService());
            if (!service.isUiContext()) {
                VirtualDeviceManager vdm = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class);
                int i = this.mLastReportedDeviceId;
                if (i == 0 || vdm.isValidVirtualDeviceId(i)) {
                    service.updateDeviceId(this.mLastReportedDeviceId);
                }
            }
            service.onCreate();
            this.mServicesData.put(data.token, data);
            this.mServices.put(data.token, service);
            try {
                ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(service, e2)) {
                throw new RuntimeException("Unable to create service " + data.info.name + ": " + e2.toString(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBindService(BindServiceData data) {
        CreateServiceData createData = this.mServicesData.get(data.token);
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                data.intent.setExtrasClassLoader(s.getClassLoader());
                data.intent.prepareToEnterProcess(isProtectedComponent(createData.info), s.getAttributionSource());
                try {
                    if (!data.rebind) {
                        IBinder binder = s.onBind(data.intent);
                        ActivityManager.getService().publishService(data.token, data.intent, binder);
                    } else {
                        s.onRebind(data.intent);
                        ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
                    }
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(s, e)) {
                    throw new RuntimeException("Unable to bind to service " + s + " with " + data.intent + ": " + e.toString(), e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnbindService(BindServiceData data) {
        CreateServiceData createData = this.mServicesData.get(data.token);
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                data.intent.setExtrasClassLoader(s.getClassLoader());
                data.intent.prepareToEnterProcess(isProtectedComponent(createData.info), s.getAttributionSource());
                boolean doRebind = s.onUnbind(data.intent);
                try {
                    if (doRebind) {
                        ActivityManager.getService().unbindFinished(data.token, data.intent, doRebind);
                    } else {
                        ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
                    }
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(s, e)) {
                    throw new RuntimeException("Unable to unbind to service " + s + " with " + data.intent + ": " + e.toString(), e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpGfxInfo(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            try {
                ThreadedRenderer.handleDumpGfxInfo(info.fd.getFileDescriptor(), info.args);
            } catch (Exception e) {
                Log.w(TAG, "Caught exception from dumpGfxInfo()", e);
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpService(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            Service s = this.mServices.get(info.token);
            if (s != null) {
                PrintWriter pw = new FastPrintWriter(new FileOutputStream(info.fd.getFileDescriptor()));
                s.dump(info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpResources(DumpResourcesData info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            PrintWriter pw = new FastPrintWriter(new FileOutputStream(info.fd.getFileDescriptor()));
            Resources.dumpHistory(pw, "");
            pw.flush();
            if (info.finishCallback != null) {
                info.finishCallback.sendResult(null);
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpActivity(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            ActivityClientRecord r = this.mActivities.get(info.token);
            if (r != null && r.activity != null) {
                PrintWriter pw = new FastPrintWriter(new FileOutputStream(info.fd.getFileDescriptor()));
                r.activity.dumpInternal(info.prefix, info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpProvider(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            ProviderClientRecord r = this.mLocalProviders.get(info.token);
            if (r != null && r.mLocalProvider != null) {
                PrintWriter pw = new FastPrintWriter(new FileOutputStream(info.fd.getFileDescriptor()));
                r.mLocalProvider.dump(info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleServiceArgs(ServiceArgsData data) {
        int res;
        CreateServiceData createData = this.mServicesData.get(data.token);
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                if (data.args != null) {
                    data.args.setExtrasClassLoader(s.getClassLoader());
                    data.args.prepareToEnterProcess(isProtectedComponent(createData.info), s.getAttributionSource());
                }
                if (!data.taskRemoved) {
                    res = s.onStartCommand(data.args, data.flags, data.startId);
                } else {
                    s.onTaskRemoved(data.args);
                    res = 1000;
                }
                QueuedWork.waitToFinish();
                try {
                    ActivityManager.getService().serviceDoneExecuting(data.token, 1, data.startId, res);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(s, e2)) {
                    throw new RuntimeException("Unable to start service " + s + " with " + data.args + ": " + e2.toString(), e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopService(IBinder token) {
        this.mServicesData.remove(token);
        Service s = this.mServices.remove(token);
        if (s == null) {
            Slog.i(TAG, "handleStopService: token=" + token + " not found.");
            return;
        }
        try {
            s.onDestroy();
            s.detachAndCleanUp();
            Context context = s.getBaseContext();
            if (context instanceof ContextImpl) {
                String who = s.getClassName();
                ((ContextImpl) context).scheduleFinalCleanup(who, "Service");
            }
            QueuedWork.waitToFinish();
            try {
                ActivityManager.getService().serviceDoneExecuting(token, 2, 0, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (Exception e2) {
            if (this.mInstrumentation.onException(s, e2)) {
                Slog.i(TAG, "handleStopService: exception for " + token, e2);
                return;
            }
            throw new RuntimeException("Unable to stop service " + s + ": " + e2.toString(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeoutService(IBinder token, int startId) {
        Service s = this.mServices.get(token);
        if (s == null) {
            Slog.wtf(TAG, "handleTimeoutService: token=" + token + " not found.");
            return;
        }
        try {
            s.callOnTimeout(startId);
        } catch (Exception e) {
            if (this.mInstrumentation.onException(s, e)) {
                Slog.i(TAG, "handleTimeoutService: exception for " + token, e);
                return;
            }
            throw new RuntimeException("Unable to call onTimeout on service " + s + ": " + e.toString(), e);
        }
    }

    public boolean performResumeActivity(ActivityClientRecord r, boolean finalStateRequest, String reason) {
        if (r.activity.mFinished) {
            return false;
        }
        if (r.getLifecycleState() == 3) {
            if (!finalStateRequest) {
                RuntimeException e = new IllegalStateException("Trying to resume activity which is already resumed");
                Slog.e(TAG, e.getMessage(), e);
                Slog.e(TAG, r.getStateString());
            }
            return false;
        }
        if (finalStateRequest) {
            r.hideForNow = false;
            r.activity.mStartedActivity = false;
        }
        try {
            r.activity.onStateNotSaved();
            r.activity.mFragments.noteStateNotSaved();
            checkAndBlockForNetworkAccess();
            if (r.pendingIntents != null) {
                deliverNewIntents(r, r.pendingIntents);
                r.pendingIntents = null;
            }
            if (r.pendingResults != null) {
                deliverResults(r, r.pendingResults, reason);
                r.pendingResults = null;
            }
            r.activity.performResume(r.startsNotResumed, reason);
            if (!DEBUG_LEVEL_LOW) {
                long allocatedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                double thresholdOfHeapSize = Runtime.getRuntime().maxMemory() * THRESHOLD_FOR_HEAPDUMP;
                if (!mIsAnomalyDetected && allocatedMemory > thresholdOfHeapSize && ActivityManager.getService().isHeapDumpAllowed()) {
                    mIsAnomalyDetected = true;
                    try {
                        Slog.i(TAG, currentPackageName() + " is using " + (allocatedMemory / 1048576) + " MB, so start dumping for java heapdump");
                        String path = "/data/log/core/" + currentPackageName() + ".hprof";
                        Debug.dumpHprofData(path);
                    } catch (Exception e2) {
                        Slog.w(TAG, "Cannot dump for java heapdump");
                        e2.printStackTrace();
                    }
                }
            }
            r.state = null;
            r.persistentState = null;
            r.setState(3);
            reportTopResumedActivityChanged(r, r.isTopResumedActivity, "topWhenResuming");
        } catch (Exception e3) {
            if (!this.mInstrumentation.onException(r.activity, e3)) {
                throw new RuntimeException("Unable to resume activity " + r.intent.getComponent().toShortString() + ": " + e3.toString(), e3);
            }
        }
        return true;
    }

    static final void cleanUpPendingRemoveWindows(ActivityClientRecord r, boolean force) {
        if (r.mPreserveWindow && !force) {
            return;
        }
        if (r.mPendingRemoveWindow != null) {
            r.mPendingRemoveWindowManager.removeViewImmediate(r.mPendingRemoveWindow.getDecorView());
            IBinder wtoken = r.mPendingRemoveWindow.getDecorView().getWindowToken();
            if (wtoken != null) {
                WindowManagerGlobal.getInstance().closeAll(wtoken, r.activity.getClass().getName(), "Activity");
            }
        }
        r.mPendingRemoveWindow = null;
        r.mPendingRemoveWindowManager = null;
    }

    private void scheduleVsyncSS(ActivityClientRecord r, boolean willBeVisible) {
        Choreographer choreographer = Choreographer.getInstance();
        if (r != null && r.window != null && choreographer != null) {
            View decor = r.window.getDecorView();
            if (!decor.isFrameMetricsObservers()) {
                getIdsController().openIdsWindow(decor.getRootView(), choreographer);
                if (getIdsController().doIds()) {
                    Objects.requireNonNull(choreographer);
                    choreographer.scheduleVsyncSS(2);
                } else {
                    Objects.requireNonNull(choreographer);
                    choreographer.scheduleVsyncSS(0);
                }
            }
        }
        if (willBeVisible) {
            resetAIDFlag();
        }
    }

    private static void resetAIDFlag() {
        sDisableAID = true;
    }

    public IdsController getIdsController() {
        if (this.idsController == null) {
            this.idsController = new IdsController(getApplication());
        }
        return this.idsController;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleResumeActivity(ActivityClientRecord r, boolean finalStateRequest, boolean isForward, boolean shouldSendCompatFakeFocus, String reason) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        AppBindData appBindData = this.mBoundApplication;
        if (appBindData != null) {
            setFlingerFlag(appBindData.processName, true);
        }
        if (!performResumeActivity(r, finalStateRequest, reason) || this.mActivitiesToBeDestroyed.containsKey(r.token)) {
            return;
        }
        Activity a = r.activity;
        int forwardBit = isForward ? 256 : 0;
        boolean willBeVisible = !a.mStartedActivity;
        if (!willBeVisible) {
            willBeVisible = ActivityClient.getInstance().willActivityBeVisible(a.getActivityToken());
        }
        if (r.window == null && !a.mFinished && willBeVisible) {
            r.window = r.activity.getWindow();
            View decor = r.window.getDecorView();
            decor.setVisibility(4);
            ViewManager wm = a.getWindowManager();
            WindowManager.LayoutParams l = r.window.getAttributes();
            a.mDecor = decor;
            l.type = 1;
            l.softInputMode |= forwardBit;
            if (r.mPreserveWindow) {
                a.mWindowAdded = true;
                r.mPreserveWindow = false;
                ViewRootImpl impl = decor.getViewRootImpl();
                if (impl != null) {
                    impl.notifyChildRebuilt();
                }
            }
            if (a.mVisibleFromClient) {
                if (!a.mWindowAdded) {
                    a.mWindowAdded = true;
                    wm.addView(decor, l);
                } else {
                    a.onWindowAttributesChanged(l);
                }
            }
        } else if (!willBeVisible) {
            r.hideForNow = true;
        }
        cleanUpPendingRemoveWindows(r, false);
        if (!r.activity.mFinished && willBeVisible && r.activity.mDecor != null && !r.hideForNow) {
            ViewRootImpl impl2 = r.window.getDecorView().getViewRootImpl();
            WindowManager.LayoutParams l2 = impl2 != null ? impl2.mWindowAttributes : r.window.getAttributes();
            if ((256 & l2.softInputMode) != forwardBit) {
                l2.softInputMode = (l2.softInputMode & (-257)) | forwardBit;
                if (r.activity.mVisibleFromClient) {
                    ViewManager wm2 = a.getWindowManager();
                    wm2.updateViewLayout(r.window.getDecorView(), l2);
                }
            }
            r.activity.mVisibleFromServer = true;
            this.mNumVisibleActivities++;
            if (r.activity.mVisibleFromClient) {
                r.activity.makeVisible();
            }
            if (shouldSendCompatFakeFocus) {
                if (impl2 != null) {
                    impl2.dispatchCompatFakeFocus();
                } else {
                    r.window.getDecorView().fakeFocusAfterAttachingToWindow();
                }
            }
        }
        this.mNewActivities.add(r);
        Looper.myQueue().addIdleHandler(new Idler());
        if (!sDisableAID) {
            scheduleVsyncSS(r, willBeVisible);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleTopResumedActivityChanged(ActivityClientRecord r, boolean onTop, String reason) {
        if (r.isTopResumedActivity == onTop) {
            if (!Build.IS_DEBUGGABLE) {
                Slog.w(TAG, "Activity top position already set to onTop=" + onTop);
                return;
            }
            Slog.e(TAG, "Activity top position already set to onTop=" + onTop);
        }
        r.isTopResumedActivity = onTop;
        if (r.getLifecycleState() == 3) {
            reportTopResumedActivityChanged(r, onTop, "topStateChangedWhenResumed");
        }
    }

    private void reportTopResumedActivityChanged(ActivityClientRecord r, boolean onTop, String reason) {
        if (r.lastReportedTopResumedState != onTop) {
            r.lastReportedTopResumedState = onTop;
            r.activity.performTopResumedActivityChanged(onTop, reason);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePauseActivity(ActivityClientRecord r, boolean finished, boolean userLeaving, int configChanges, boolean autoEnteringPip, PendingTransactionActions pendingActions, String reason) {
        if (userLeaving) {
            performUserLeavingActivity(r);
        }
        r.activity.mConfigChangeFlags |= configChanges;
        if (autoEnteringPip) {
            r.activity.mIsInPictureInPictureMode = true;
        }
        performPauseActivity(r, finished, reason, pendingActions);
        if (r.isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        this.mSomeActivitiesChanged = true;
    }

    final void performUserLeavingActivity(ActivityClientRecord r) {
        this.mInstrumentation.callActivityOnPictureInPictureRequested(r.activity);
        this.mInstrumentation.callActivityOnUserLeaving(r.activity);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Bundle performPauseActivity(IBinder token, boolean finished, String reason, PendingTransactionActions pendingActions) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            return performPauseActivity(r, finished, reason, pendingActions);
        }
        return null;
    }

    private Bundle performPauseActivity(ActivityClientRecord r, boolean finished, String reason, PendingTransactionActions pendingActions) {
        ArrayList<OnActivityPausedListener> listeners;
        if (r.paused) {
            if (r.activity.mFinished) {
                return null;
            }
            RuntimeException e = new RuntimeException("Performing pause of activity that is not resumed: " + r.intent.getComponent().toShortString());
            Slog.e(TAG, e.getMessage(), e);
        }
        if (finished) {
            r.activity.mFinished = true;
        }
        boolean shouldSaveState = !r.activity.mFinished && r.isPreHoneycomb();
        if (shouldSaveState) {
            callActivityOnSaveInstanceState(r);
        }
        performPauseActivityIfNeeded(r, reason);
        synchronized (this.mOnPauseListeners) {
            listeners = this.mOnPauseListeners.remove(r.activity);
        }
        int size = listeners != null ? listeners.size() : 0;
        for (int i = 0; i < size; i++) {
            listeners.get(i).onPaused(r.activity);
        }
        Bundle oldState = pendingActions != null ? pendingActions.getOldState() : null;
        if (oldState != null && r.isPreHoneycomb()) {
            r.state = oldState;
        }
        if (shouldSaveState) {
            return r.state;
        }
        return null;
    }

    private void performPauseActivityIfNeeded(ActivityClientRecord r, String reason) {
        if (r.paused) {
            return;
        }
        reportTopResumedActivityChanged(r, false, "pausing");
        try {
            r.activity.mCalled = false;
            this.mInstrumentation.callActivityOnPause(r.activity);
        } catch (SuperNotCalledException e) {
            throw e;
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(r.activity, e2)) {
                throw new RuntimeException("Unable to pause activity " + safeToComponentShortString(r.intent) + ": " + e2.toString(), e2);
            }
        }
        if (!r.activity.mCalled) {
            throw new SuperNotCalledException("Activity " + safeToComponentShortString(r.intent) + " did not call through to super.onPause()");
        }
        r.setState(4);
        if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED) {
            performReleaseActivityFocusIfNeeded(r.token);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void performStopActivity(IBinder token, boolean saveState, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        performStopActivityInner(r, null, saveState, false, reason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ProviderRefCount {
        public final ProviderClientRecord client;
        public final ContentProviderHolder holder;
        public boolean removePending;
        public int stableCount;
        public int unstableCount;

        ProviderRefCount(ContentProviderHolder inHolder, ProviderClientRecord inClient, int sCount, int uCount) {
            this.holder = inHolder;
            this.client = inClient;
            this.stableCount = sCount;
            this.unstableCount = uCount;
        }
    }

    private void performStopActivityInner(ActivityClientRecord r, PendingTransactionActions.StopInfo info, boolean saveState, boolean finalStateRequest, String reason) {
        if (r.stopped) {
            if (r.activity.mFinished) {
                return;
            }
            if (!finalStateRequest) {
                RuntimeException e = new RuntimeException("Performing stop of activity that is already stopped: " + r.intent.getComponent().toShortString());
                Slog.e(TAG, e.getMessage(), e);
                Slog.e(TAG, r.getStateString());
            }
        }
        performPauseActivityIfNeeded(r, reason);
        if (info != null) {
            try {
                info.setDescription(r.activity.onCreateDescription());
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(r.activity, e2)) {
                    throw new RuntimeException("Unable to save state of activity " + r.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
                }
            }
        }
        callActivityOnStop(r, saveState, reason);
        this.mAbnormalUsage.checkAbnormalUsage();
    }

    private void callActivityOnStop(ActivityClientRecord r, boolean saveState, String reason) {
        boolean shouldSaveState = saveState && !r.activity.mFinished && r.state == null && !r.isPreHoneycomb();
        boolean isPreP = r.isPreP();
        if (shouldSaveState && isPreP) {
            callActivityOnSaveInstanceState(r);
        }
        try {
            r.activity.performStop(r.mPreserveWindow, reason);
        } catch (SuperNotCalledException e) {
            throw e;
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(r.activity, e2)) {
                throw new RuntimeException("Unable to stop activity " + r.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
            }
        }
        r.setState(5);
        if (shouldSaveState && !isPreP) {
            callActivityOnSaveInstanceState(r);
        }
    }

    private void updateVisibility(ActivityClientRecord r, boolean show) {
        View v = r.activity.mDecor;
        if (v != null) {
            if (show) {
                if (!r.activity.mVisibleFromServer) {
                    r.activity.mVisibleFromServer = true;
                    this.mNumVisibleActivities++;
                    if (r.activity.mVisibleFromClient) {
                        r.activity.makeVisible();
                        return;
                    }
                    return;
                }
                return;
            }
            if (r.activity.mVisibleFromServer) {
                r.activity.mVisibleFromServer = false;
                this.mNumVisibleActivities--;
                v.setVisibility(4);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStopActivity(ActivityClientRecord r, int configChanges, PendingTransactionActions pendingActions, boolean finalStateRequest, String reason) {
        r.activity.mConfigChangeFlags |= configChanges;
        PendingTransactionActions.StopInfo stopInfo = new PendingTransactionActions.StopInfo();
        performStopActivityInner(r, stopInfo, true, finalStateRequest, reason);
        updateVisibility(r, false);
        if (!r.isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        stopInfo.setActivity(r);
        stopInfo.setState(r.state);
        stopInfo.setPersistentState(r.persistentState);
        pendingActions.setStopInfo(stopInfo);
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public void reportStop(PendingTransactionActions pendingActions) {
        this.mH.post(pendingActions.getStopInfo());
    }

    @Override // android.app.ClientTransactionHandler
    public void performRestartActivity(ActivityClientRecord r, boolean start) {
        if (r.stopped) {
            r.activity.performRestart(start);
            if (start) {
                r.setState(2);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRefresh(ActivityClientRecord r) {
        ActivityClient.getInstance().activityRefreshed(r.token);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetCoreSettings(Bundle coreSettings) {
        synchronized (this.mCoreSettingsLock) {
            this.mCoreSettings = coreSettings;
        }
        onCoreSettingsChange();
    }

    private void onCoreSettingsChange() {
        if (updateDebugViewAttributeState()) {
            relaunchAllActivities(true, "onCoreSettingsChange");
        }
        updateDefaultNavigationBarColor();
    }

    private boolean updateDebugViewAttributeState() {
        boolean previousState = View.sDebugViewAttributes;
        View.sDebugViewAttributesApplicationPackage = this.mCoreSettings.getString(Settings.Global.DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE, "");
        AppBindData appBindData = this.mBoundApplication;
        String currentPackage = (appBindData == null || appBindData.appInfo == null) ? "<unknown-app>" : this.mBoundApplication.appInfo.packageName;
        View.sDebugViewAttributes = this.mCoreSettings.getInt(Settings.Global.DEBUG_VIEW_ATTRIBUTES, 0) != 0 || View.sDebugViewAttributesApplicationPackage.equals(currentPackage);
        return previousState != View.sDebugViewAttributes;
    }

    private void relaunchAllActivities(boolean preserveWindows, String reason) {
        Log.i(TAG, "Relaunch all activities: " + reason);
        for (int i = this.mActivities.size() - 1; i >= 0; i--) {
            scheduleRelaunchActivityIfPossible(this.mActivities.valueAt(i), preserveWindows);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdatePackageCompatibilityInfo(UpdateCompatibilityData data) {
        this.mCompatibilityInfo = data.info;
        LoadedApk apk = peekPackageInfo(data.pkg, false);
        if (apk != null) {
            apk.setCompatibilityInfo(data.info);
        }
        LoadedApk apk2 = peekPackageInfo(data.pkg, true);
        if (apk2 != null) {
            apk2.setCompatibilityInfo(data.info);
        }
        this.mConfigurationController.handleConfigurationChanged(data.info);
    }

    private void deliverResults(ActivityClientRecord r, List<ResultInfo> results, String reason) {
        int N = results.size();
        for (int i = 0; i < N; i++) {
            ResultInfo ri = results.get(i);
            try {
                if (ri.mData != null) {
                    ri.mData.setExtrasClassLoader(r.activity.getClassLoader());
                    ri.mData.prepareToEnterProcess(isProtectedComponent(r.activityInfo), r.activity.getAttributionSource());
                }
                r.activity.dispatchActivityResult(ri.mResultWho, ri.mRequestCode, ri.mResultCode, ri.mData, reason);
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(r.activity, e)) {
                    throw new RuntimeException("Failure delivering result " + ri + " to activity " + r.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleSendResult(ActivityClientRecord r, List<ResultInfo> results, String reason) {
        boolean resumed = !r.paused;
        if (!r.activity.mFinished && r.activity.mDecor != null && r.hideForNow && resumed) {
            updateVisibility(r, true);
        }
        if (resumed) {
            try {
                r.activity.mCalled = false;
                this.mInstrumentation.callActivityOnPause(r.activity);
                if (!r.activity.mCalled) {
                    throw new SuperNotCalledException("Activity " + r.intent.getComponent().toShortString() + " did not call through to super.onPause()");
                }
            } catch (SuperNotCalledException e) {
                throw e;
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(r.activity, e2)) {
                    throw new RuntimeException("Unable to pause activity " + r.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
                }
            }
        }
        checkAndBlockForNetworkAccess();
        deliverResults(r, results, reason);
        if (resumed) {
            r.activity.performResume(false, reason);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void performDestroyActivity(ActivityClientRecord r, boolean finishing, int configChanges, boolean getNonConfigInstance, String reason) {
        Class<?> cls = r.activity.getClass();
        r.activity.mConfigChangeFlags |= configChanges;
        if (finishing) {
            r.activity.mFinished = true;
        }
        performPauseActivityIfNeeded(r, "destroy");
        if (!r.stopped) {
            callActivityOnStop(r, false, "destroy");
        }
        if (getNonConfigInstance) {
            try {
                r.lastNonConfigurationInstances = r.activity.retainNonConfigurationInstances();
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(r.activity, e)) {
                    throw new RuntimeException("Unable to retain activity " + r.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
        try {
            r.activity.mCalled = false;
            this.mInstrumentation.callActivityOnDestroy(r.activity);
        } catch (SuperNotCalledException e2) {
            throw e2;
        } catch (Exception e3) {
            if (!this.mInstrumentation.onException(r.activity, e3)) {
                throw new RuntimeException("Unable to destroy activity " + safeToComponentShortString(r.intent) + ": " + e3.toString(), e3);
            }
        }
        if (!r.activity.mCalled) {
            throw new SuperNotCalledException("Activity " + safeToComponentShortString(r.intent) + " did not call through to super.onDestroy()");
        }
        if (r.window != null) {
            r.window.closeAllPanels();
        }
        r.setState(6);
        schedulePurgeIdler();
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            if (splashScreenManagerGlobal != null) {
                splashScreenManagerGlobal.tokenDestroyed(r.token);
            }
        }
        synchronized (this.mResourcesManager) {
            this.mActivities.remove(r.token);
        }
        StrictMode.decrementExpectedActivityCount(cls);
    }

    private static String safeToComponentShortString(Intent intent) {
        ComponentName component = intent.getComponent();
        return component == null ? "[Unknown]" : component.toShortString();
    }

    @Override // android.app.ClientTransactionHandler
    public Map<IBinder, ClientTransactionItem> getActivitiesToBeDestroyed() {
        return this.mActivitiesToBeDestroyed;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleDestroyActivity(ActivityClientRecord r, boolean finishing, int configChanges, boolean getNonConfigInstance, String reason) {
        performDestroyActivity(r, finishing, configChanges, getNonConfigInstance, reason);
        cleanUpPendingRemoveWindows(r, finishing);
        WindowManager wm = r.activity.getWindowManager();
        View v = r.activity.mDecor;
        if (v != null) {
            if (r.activity.mVisibleFromServer) {
                this.mNumVisibleActivities--;
            }
            IBinder wtoken = v.getWindowToken();
            if (r.activity.mWindowAdded) {
                if (r.mPreserveWindow) {
                    r.mPendingRemoveWindow = r.window;
                    r.mPendingRemoveWindowManager = wm;
                    r.window.clearContentView();
                } else {
                    ViewRootImpl viewRoot = v.getViewRootImpl();
                    if (viewRoot != null) {
                        viewRoot.setActivityConfigCallback(null);
                    }
                    wm.removeViewImmediate(v);
                }
            }
            if (wtoken != null && r.mPendingRemoveWindow == null) {
                WindowManagerGlobal.getInstance().closeAll(wtoken, r.activity.getClass().getName(), "Activity");
            } else if (r.mPendingRemoveWindow != null) {
                WindowManagerGlobal.getInstance().closeAllExceptView(r.token, v, r.activity.getClass().getName(), "Activity");
            }
            r.activity.mDecor = null;
        }
        if (r.mPendingRemoveWindow == null) {
            WindowManagerGlobal.getInstance().closeAll(r.token, r.activity.getClass().getName(), "Activity");
        }
        Context c = r.activity.getBaseContext();
        if (c instanceof ContextImpl) {
            ((ContextImpl) c).scheduleFinalCleanup(r.activity.getClass().getName(), "Activity");
        }
        if (finishing) {
            ActivityClient.getInstance().activityDestroyed(r.token);
            this.mNewActivities.remove(r);
        }
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public ActivityClientRecord prepareRelaunchActivity(IBinder token, List<ResultInfo> pendingResults, List<ReferrerIntent> pendingNewIntents, int configChanges, MergedConfiguration config, boolean preserveWindow) {
        ActivityClientRecord target = null;
        boolean scheduleRelaunch = false;
        synchronized (this.mResourcesManager) {
            int i = 0;
            while (true) {
                if (i >= this.mRelaunchingActivities.size()) {
                    break;
                }
                ActivityClientRecord r = this.mRelaunchingActivities.get(i);
                if (r.token != token) {
                    i++;
                } else {
                    target = r;
                    if (pendingResults != null) {
                        if (r.pendingResults != null) {
                            r.pendingResults.addAll(pendingResults);
                        } else {
                            r.pendingResults = pendingResults;
                        }
                    }
                    if (pendingNewIntents != null) {
                        if (r.pendingIntents != null) {
                            r.pendingIntents.addAll(pendingNewIntents);
                        } else {
                            r.pendingIntents = pendingNewIntents;
                        }
                    }
                }
            }
            if (target == null) {
                target = new ActivityClientRecord();
                target.token = token;
                target.pendingResults = pendingResults;
                target.pendingIntents = pendingNewIntents;
                target.mPreserveWindow = preserveWindow;
                this.mRelaunchingActivities.add(target);
                scheduleRelaunch = true;
            }
            target.createdConfig = config.getGlobalConfiguration();
            target.overrideConfig = config.getOverrideConfiguration();
            target.pendingConfigChanges |= configChanges;
        }
        if (scheduleRelaunch) {
            return target;
        }
        return null;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleRelaunchActivity(ActivityClientRecord tmp, PendingTransactionActions pendingActions) {
        Configuration config;
        int configChanges;
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        synchronized (this.mResourcesManager) {
            try {
                int N = this.mRelaunchingActivities.size();
                try {
                    IBinder token = tmp.token;
                    int i = 0;
                    int configChanges2 = 0;
                    ActivityClientRecord tmp2 = null;
                    while (i < N) {
                        try {
                            ActivityClientRecord r = this.mRelaunchingActivities.get(i);
                            if (r.token == token) {
                                try {
                                    configChanges = r.pendingConfigChanges | configChanges2;
                                } catch (Throwable th) {
                                    th = th;
                                }
                                try {
                                    this.mRelaunchingActivities.remove(i);
                                    i--;
                                    N--;
                                    tmp2 = r;
                                    configChanges2 = configChanges;
                                } catch (Throwable th2) {
                                    th = th2;
                                    while (true) {
                                        try {
                                            break;
                                        } catch (Throwable th3) {
                                            th = th3;
                                        }
                                    }
                                    throw th;
                                }
                            }
                            i++;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    }
                    if (tmp2 == null) {
                        return;
                    }
                    Configuration changedConfig = this.mConfigurationController.getPendingConfiguration(true);
                    this.mPendingConfiguration = null;
                    Configuration changedConfig2 = (tmp2.createdConfig == null || ((config = this.mConfigurationController.getConfiguration()) != null && (!tmp2.createdConfig.isOtherSeqNewer(config) || config.diff(tmp2.createdConfig) == 0)) || !(changedConfig == null || tmp2.createdConfig.isOtherSeqNewer(changedConfig))) ? changedConfig : tmp2.createdConfig;
                    if (changedConfig2 != null) {
                        this.mConfigurationController.updateDefaultDensity(changedConfig2.densityDpi);
                        this.mConfigurationController.handleConfigurationChanged(changedConfig2, null);
                        this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
                        this.mConfiguration = this.mConfigurationController.getConfiguration();
                    }
                    ActivityClientRecord r2 = this.mActivities.get(tmp2.token);
                    if (r2 == null) {
                        return;
                    }
                    r2.activity.mConfigChangeFlags |= configChanges2;
                    r2.mPreserveWindow = tmp2.mPreserveWindow;
                    r2.activity.mChangingConfigurations = true;
                    handleRelaunchActivityInner(r2, configChanges2, tmp2.pendingResults, tmp2.pendingIntents, pendingActions, tmp2.startsNotResumed, tmp2.overrideConfig, "handleRelaunchActivity");
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void scheduleRelaunchActivity(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            Log.i(TAG, "Schedule relaunch activity: " + r.activityInfo.name);
            Log.d(TAG, "scheduleRelaunchActivity: preserveWindow=" + (!r.stopped) + ", r=" + r.activity + ", caller=" + Debug.getCallers(8));
            scheduleRelaunchActivityIfPossible(r, !r.stopped);
        }
    }

    private void scheduleRelaunchActivityIfPossible(ActivityClientRecord r, boolean preserveWindow) {
        if ((r.activity != null && r.activity.mFinished) || (r.token instanceof Binder)) {
            return;
        }
        if (preserveWindow && r.window != null) {
            r.mPreserveWindow = true;
        }
        this.mH.removeMessages(160, r.token);
        sendMessage(160, r.token);
    }

    public void handleRelaunchActivityLocally(IBinder token) {
        ActivityLifecycleItem lifecycleRequest;
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null) {
            Log.w(TAG, "Activity to relaunch no longer exists");
            return;
        }
        int prevState = r.getLifecycleState();
        if (prevState < 2 || prevState > 5) {
            Log.w(TAG, "Activity state must be in [ON_START..ON_STOP] in order to be relaunched,current state is " + prevState);
            return;
        }
        if (prevState == 2 && hasRelaunchingActivity(token)) {
            Log.w(TAG, "Activity is already relaunching at start state");
            return;
        }
        ActivityClient.getInstance().activityLocalRelaunch(r.token);
        MergedConfiguration mergedConfiguration = new MergedConfiguration(r.createdConfig != null ? r.createdConfig : this.mConfigurationController.getConfiguration(), r.overrideConfig);
        ActivityRelaunchItem activityRelaunchItem = ActivityRelaunchItem.obtain(null, null, 0, mergedConfiguration, r.mPreserveWindow);
        if (shouldResumeOnRelaunchActivityLocally(r)) {
            lifecycleRequest = ResumeActivityItem.obtain(false, false);
        } else {
            lifecycleRequest = TransactionExecutorHelper.getLifecycleRequestForCurrentState(r);
        }
        ClientTransaction transaction = ClientTransaction.obtain(this.mAppThread, r.token);
        transaction.addCallback(activityRelaunchItem);
        transaction.setLifecycleStateRequest(lifecycleRequest);
        executeTransaction(transaction);
    }

    private boolean shouldResumeOnRelaunchActivityLocally(ActivityClientRecord r) {
        int prevState = r.getLifecycleState();
        if (prevState < 2 || prevState > 3) {
            return false;
        }
        if (hasResumedPopOver()) {
            return true;
        }
        return (r.mPreserveWindow || r.activity == null || !r.activity.mWindowAdded) ? false : true;
    }

    private void handleRelaunchActivityInner(ActivityClientRecord r, int configChanges, List<ResultInfo> pendingResults, List<ReferrerIntent> pendingIntents, PendingTransactionActions pendingActions, boolean startsNotResumed, Configuration overrideConfig, String reason) {
        Intent customIntent = r.activity.mIntent;
        if (!r.paused) {
            performPauseActivity(r, false, reason, (PendingTransactionActions) null);
        }
        if (!r.stopped) {
            callActivityOnStop(r, true, reason);
        }
        handleDestroyActivity(r, false, configChanges, true, reason);
        if (CoreRune.FW_CCM_BUG_FIX) {
            clearDecorViewContentCaptureSession(r);
        }
        r.activity = null;
        r.window = null;
        r.hideForNow = false;
        if (pendingResults != null) {
            if (r.pendingResults == null) {
                r.pendingResults = pendingResults;
            } else {
                r.pendingResults.addAll(pendingResults);
            }
        }
        if (pendingIntents != null) {
            if (r.pendingIntents == null) {
                r.pendingIntents = pendingIntents;
            } else {
                r.pendingIntents.addAll(pendingIntents);
            }
        }
        r.startsNotResumed = startsNotResumed;
        r.overrideConfig = overrideConfig;
        handleLaunchActivity(r, pendingActions, this.mLastReportedDeviceId, customIntent);
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRelaunch(ActivityClientRecord r) {
        ActivityClient.getInstance().activityRelaunched(r.token);
    }

    private void callActivityOnSaveInstanceState(ActivityClientRecord r) {
        r.state = new Bundle();
        r.state.setAllowFds(false);
        if (r.isPersistable()) {
            r.persistentState = new PersistableBundle();
            this.mInstrumentation.callActivityOnSaveInstanceState(r.activity, r.state, r.persistentState);
        } else {
            this.mInstrumentation.callActivityOnSaveInstanceState(r.activity, r.state);
        }
    }

    @Override // android.app.ActivityThreadInternal
    public ArrayList<ComponentCallbacks2> collectComponentCallbacks(boolean includeUiContexts) {
        ArrayList<ComponentCallbacks2> callbacks = new ArrayList<>();
        synchronized (this.mResourcesManager) {
            int NAPP = this.mAllApplications.size();
            for (int i = 0; i < NAPP; i++) {
                callbacks.add(this.mAllApplications.get(i));
            }
            if (includeUiContexts) {
                for (int i2 = this.mActivities.size() - 1; i2 >= 0; i2--) {
                    Activity a = this.mActivities.valueAt(i2).activity;
                    if (a != null && !a.mFinished) {
                        callbacks.add(a);
                    }
                }
            }
            int NSVC = this.mServices.size();
            for (int i3 = 0; i3 < NSVC; i3++) {
                Service service = this.mServices.valueAt(i3);
                if (includeUiContexts || !(service instanceof WindowProviderService)) {
                    callbacks.add(service);
                }
            }
        }
        synchronized (this.mProviderMap) {
            int NPRV = this.mLocalProviders.size();
            for (int i4 = 0; i4 < NPRV; i4++) {
                callbacks.add(this.mLocalProviders.valueAt(i4).mLocalProvider);
            }
        }
        return callbacks;
    }

    private Configuration performConfigurationChangedForActivity(ActivityClientRecord r, Configuration newBaseConfig, int displayId, boolean alwaysReportChange) {
        r.tmpConfig.setTo(newBaseConfig);
        if (r.overrideConfig != null) {
            r.tmpConfig.updateFrom(r.overrideConfig);
        }
        Configuration reportedConfig = performActivityConfigurationChanged(r, r.tmpConfig, r.overrideConfig, displayId, alwaysReportChange);
        ConfigurationHelper.freeTextLayoutCachesIfNeeded(r.activity.mCurrentConfig.diff(r.tmpConfig));
        return reportedConfig;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.res.Configuration performActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord r21, android.content.res.Configuration r22, android.content.res.Configuration r23, int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.performActivityConfigurationChanged(android.app.ActivityThread$ActivityClientRecord, android.content.res.Configuration, android.content.res.Configuration, int, boolean):android.content.res.Configuration");
    }

    public static boolean shouldReportChange(Configuration currentConfig, Configuration newConfig, SizeConfigurationBuckets sizeBuckets, int handledConfigChanges, boolean alwaysReportChange) {
        int publicDiff = currentConfig.diffPublicOnly(newConfig);
        if (publicDiff == 0) {
            return false;
        }
        if (alwaysReportChange) {
            return true;
        }
        int diffWithBucket = SizeConfigurationBuckets.filterDiff(publicDiff, currentConfig, newConfig, sizeBuckets);
        int diff = diffWithBucket != 0 ? diffWithBucket : publicDiff;
        if (((~handledConfigChanges) & diff) != 0) {
            return false;
        }
        return true;
    }

    public final void applyConfigurationToResources(Configuration config) {
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyConfigurationToResources(config, null);
        }
    }

    private void updateDeviceIdForNonUIContexts(int deviceId) {
        if (deviceId == -1 || deviceId == this.mLastReportedDeviceId) {
            return;
        }
        this.mLastReportedDeviceId = deviceId;
        ArrayList<Context> nonUIContexts = new ArrayList<>();
        synchronized (this.mResourcesManager) {
            int numApps = this.mAllApplications.size();
            for (int i = 0; i < numApps; i++) {
                nonUIContexts.add(this.mAllApplications.get(i));
            }
            int numServices = this.mServices.size();
            for (int i2 = 0; i2 < numServices; i2++) {
                Service service = this.mServices.valueAt(i2);
                if (!service.isUiContext()) {
                    nonUIContexts.add(service);
                }
            }
        }
        Iterator<Context> it = nonUIContexts.iterator();
        while (it.hasNext()) {
            Context context = it.next();
            try {
                context.updateDeviceId(deviceId);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleConfigurationChanged(Configuration config, int deviceId) {
        this.mConfigurationController.handleConfigurationChanged(config);
        updateDeviceIdForNonUIContexts(deviceId);
        this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
        this.mConfiguration = this.mConfigurationController.getConfiguration();
        this.mPendingConfiguration = this.mConfigurationController.getPendingConfiguration(false);
    }

    private void handleWindowingModeChangeIfNeeded(ActivityClientRecord r, Configuration newConfiguration) {
        Activity activity = r.activity;
        int newWindowingMode = newConfiguration.windowConfiguration.getWindowingMode();
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            newWindowingMode = CompatSandbox.getCompatWindowingMode(newConfiguration, newWindowingMode);
        }
        int oldWindowingMode = r.mLastReportedWindowingMode;
        if (oldWindowingMode == newWindowingMode) {
            return;
        }
        if (newWindowingMode == 2) {
            activity.dispatchPictureInPictureModeChanged(true, newConfiguration);
        } else if (oldWindowingMode == 2) {
            activity.dispatchPictureInPictureModeChanged(false, newConfiguration);
        }
        boolean wasInMultiWindowMode = WindowConfiguration.inMultiWindowMode(oldWindowingMode);
        boolean nowInMultiWindowMode = WindowConfiguration.inMultiWindowMode(newWindowingMode);
        if (wasInMultiWindowMode != nowInMultiWindowMode) {
            activity.dispatchMultiWindowModeChanged(nowInMultiWindowMode, newConfiguration);
        }
        r.mLastReportedWindowingMode = newWindowingMode;
    }

    public void handleSystemApplicationInfoChanged(ApplicationInfo ai) {
        Preconditions.checkState(this.mSystemThread, "Must only be called in the system process");
        handleApplicationInfoChanged(ai);
    }

    public void handleApplicationInfoChanged(ApplicationInfo ai) {
        LoadedApk apk;
        LoadedApk resApk;
        synchronized (this.mResourcesManager) {
            WeakReference<LoadedApk> ref = this.mPackages.get(ai.packageName);
            apk = ref != null ? ref.get() : null;
            WeakReference<LoadedApk> ref2 = this.mResourcePackages.get(ai.packageName);
            resApk = ref2 != null ? ref2.get() : null;
        }
        if (apk != null) {
            ArrayList<String> oldPaths = new ArrayList<>();
            LoadedApk.makePaths(this, apk.getApplicationInfo(), oldPaths);
            apk.updateApplicationInfo(ai, oldPaths);
        }
        if (resApk != null) {
            ArrayList<String> oldPaths2 = new ArrayList<>();
            LoadedApk.makePaths(this, resApk.getApplicationInfo(), oldPaths2);
            resApk.updateApplicationInfo(ai, oldPaths2);
        }
        if (this.mActivities.size() > 0) {
            Slog.i(TAG, "handleApplicationInfoChanged: updating resDirs from appInfo to activityInfo for locale overlays");
            for (ActivityClientRecord r : this.mActivities.values()) {
                LoadedApk pInfo = getPackageInfo(r.activityInfo.packageName, (CompatibilityInfo) null, 0);
                if (pInfo != null) {
                    r.activityInfo.applicationInfo.resourceDirs = pInfo.getOverlayDirs();
                    r.activityInfo.applicationInfo.overlayPaths = pInfo.getOverlayPaths();
                }
            }
        }
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyAllPendingAppInfoUpdates();
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingActivityConfiguration(IBinder token, Configuration overrideConfig) {
        synchronized (this.mPendingOverrideConfigs) {
            Configuration pendingOverrideConfig = this.mPendingOverrideConfigs.get(token);
            if (pendingOverrideConfig == null || pendingOverrideConfig.isOtherSeqNewer(overrideConfig)) {
                this.mPendingOverrideConfigs.put(token, overrideConfig);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleActivityConfigurationChanged(ActivityClientRecord r, Configuration overrideConfig, int displayId) {
        handleActivityConfigurationChanged(r, overrideConfig, displayId, true);
    }

    void handleActivityConfigurationChanged(ActivityClientRecord r, Configuration overrideConfig, int displayId, boolean alwaysReportChange) {
        synchronized (this.mPendingOverrideConfigs) {
            Configuration pendingOverrideConfig = this.mPendingOverrideConfigs.get(r.token);
            if (overrideConfig.isOtherSeqNewer(pendingOverrideConfig)) {
                return;
            }
            this.mPendingOverrideConfigs.remove(r.token);
            if (displayId == -1) {
                displayId = r.activity.getDisplayId();
            }
            boolean movedToDifferentDisplay = ConfigurationHelper.isDifferentDisplay(r.activity.getDisplayId(), displayId);
            if (r.overrideConfig != null && !r.overrideConfig.isOtherSeqNewer(overrideConfig) && !movedToDifferentDisplay) {
                return;
            }
            updateConfigurationForDexCompatIfNeeded(r, overrideConfig);
            r.overrideConfig = overrideConfig;
            ViewRootImpl viewRoot = r.activity.mDecor != null ? r.activity.mDecor.getViewRootImpl() : null;
            Configuration reportedConfig = performConfigurationChangedForActivity(r, this.mConfigurationController.getCompatConfiguration(), movedToDifferentDisplay ? displayId : r.activity.getDisplayId(), alwaysReportChange);
            if (viewRoot != null) {
                if (movedToDifferentDisplay) {
                    viewRoot.onMovedToDisplay(displayId, reportedConfig);
                }
                ArrayList<ViewRootImpl> views = WindowManagerGlobal.getInstance().getRootViews(r.activity.getActivityToken());
                Iterator<ViewRootImpl> it = views.iterator();
                while (it.hasNext()) {
                    ViewRootImpl v = it.next();
                    if (v.mWindowAttributes.type != 3) {
                        v.updateConfiguration(displayId);
                    }
                }
                if (!views.contains(viewRoot)) {
                    viewRoot.updateConfiguration(displayId);
                }
            }
            this.mSomeActivitiesChanged = true;
        }
    }

    final void handleProfilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) {
        try {
            if (start) {
                try {
                    this.mProfiler.setProfiler(profilerInfo);
                    this.mProfiler.startProfiling();
                } catch (RuntimeException e) {
                    Slog.w(TAG, "Profiling failed on path " + profilerInfo.profileFile + " -- can the process access this path?");
                }
                return;
            }
            this.mProfiler.stopProfiling();
        } finally {
            profilerInfo.closeFd();
        }
    }

    public void stopProfiling() {
        Profiler profiler = this.mProfiler;
        if (profiler != null) {
            profiler.stopProfiling();
        }
    }

    static void handleDumpHeap(DumpHeapData dhd) {
        ParcelFileDescriptor fd;
        if (dhd.runGc) {
            System.gc();
            System.runFinalization();
            System.gc();
        }
        try {
            fd = dhd.fd;
        } catch (IOException e) {
            if (dhd.managed) {
                Slog.w(TAG, "Managed heap dump failed on path " + dhd.path + " -- can the process access this path?", e);
            } else {
                Slog.w(TAG, "Failed to dump heap", e);
            }
        } catch (RuntimeException e2) {
            Slog.wtf(TAG, "Heap dumper threw a runtime exception", e2);
        }
        try {
            if (dhd.managed) {
                Debug.dumpHprofData(dhd.path, fd.getFileDescriptor());
            } else if (dhd.mallocInfo) {
                Debug.dumpNativeMallocInfo(fd.getFileDescriptor());
            } else {
                Debug.dumpNativeHeap(fd.getFileDescriptor());
            }
            if (fd != null) {
                fd.close();
            }
            try {
                ActivityManager.getService().dumpHeapFinished(dhd.path);
                if (dhd.finishCallback != null) {
                    dhd.finishCallback.sendResult(null);
                }
            } catch (RemoteException e3) {
                throw e3.rethrowFromSystemServer();
            }
        } finally {
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:59:0x0103
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1166)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:1022)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:55)
        */
    final void handleDispatchPackageBroadcast(int r17, java.lang.String[] r18) {
        /*
            Method dump skipped, instructions count: 358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleDispatchPackageBroadcast(int, java.lang.String[]):void");
    }

    final void handleLowMemory() {
        ArrayList<ComponentCallbacks2> callbacks = collectComponentCallbacks(true);
        int N = callbacks.size();
        for (int i = 0; i < N; i++) {
            callbacks.get(i).onLowMemory();
        }
        int i2 = Process.myUid();
        if (i2 != 1000) {
            int sqliteReleased = SQLiteDatabase.releaseMemory();
            EventLog.writeEvent(SQLITE_MEM_RELEASED_EVENT_LOG_TAG, sqliteReleased);
        }
        Canvas.freeCaches();
        Canvas.freeTextLayoutCaches();
        BinderInternal.forceGc("mem");
    }

    /* loaded from: classes.dex */
    public static class ReclaimerLog {
        private static String reclaimerLogPath = "/proc/reclaimer_log";
        private static boolean RECLAIMER_LOG_SUPPORT = true;
        private static boolean RECLAIMER_LOG_SUPPORT_CHECKED = false;

        private static boolean reclaimer_log_supported() {
            if (!RECLAIMER_LOG_SUPPORT_CHECKED) {
                RECLAIMER_LOG_SUPPORT_CHECKED = true;
                StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskReads();
                File f = new File(reclaimerLogPath);
                if (!f.exists()) {
                    RECLAIMER_LOG_SUPPORT = false;
                }
                StrictMode.setThreadPolicy(oldPolicy);
            }
            return RECLAIMER_LOG_SUPPORT;
        }

        public static boolean write(String msg, boolean writeLogcat) {
            if (writeLogcat) {
                Slog.i("UMR", msg);
            }
            if (!reclaimer_log_supported()) {
                return false;
            }
            StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
            try {
                try {
                    FileWriter fw = new FileWriter(reclaimerLogPath);
                    fw.write("UMR: " + msg);
                    fw.flush();
                    fw.close();
                    StrictMode.setThreadPolicy(oldPolicy);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    StrictMode.setThreadPolicy(oldPolicy);
                    return false;
                }
            } catch (Throwable th) {
                StrictMode.setThreadPolicy(oldPolicy);
                throw th;
            }
        }

        public static boolean write(String msg) {
            return write(msg, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTrimMemory(int level) {
        if (this.mLastProcessState <= 6 && level >= 40) {
            return;
        }
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "trimMemory: " + level);
        }
        ReclaimerLog.write("B|trimMemory level=" + level, false);
        if (level >= 80) {
            try {
                PropertyInvalidatedCache.onTrimMemory();
            } catch (Throwable th) {
                Trace.traceEnd(64L);
                throw th;
            }
        }
        ArrayList<ComponentCallbacks2> callbacks = collectComponentCallbacks(true);
        int N = callbacks.size();
        for (int i = 0; i < N; i++) {
            callbacks.get(i).onTrimMemory(level);
        }
        Trace.traceEnd(64L);
        WindowManagerGlobal.getInstance().trimMemory(level);
        ReclaimerLog.write("E|trimMemory", false);
        if (SystemProperties.getInt("debug.am.run_gc_trim_level", Integer.MAX_VALUE) <= level) {
            unscheduleGcIdler();
            doGcIfNeeded("tm");
        }
        if (SystemProperties.getInt("debug.am.run_mallopt_trim_level", Integer.MAX_VALUE) <= level) {
            unschedulePurgeIdler();
            purgePendingResources();
        }
    }

    private void setupGraphicsSupport(Context context) {
        Trace.traceBegin(64L, "setupGraphicsSupport");
        if (!"android".equals(context.getPackageName())) {
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                Log.v(TAG, "Unable to initialize \"java.io.tmpdir\" property due to missing cache directory");
            } else {
                String tmpdir = cacheDir.getAbsolutePath();
                System.setProperty("java.io.tmpdir", tmpdir);
                try {
                    Os.setenv("TMPDIR", tmpdir, true);
                } catch (ErrnoException ex) {
                    Log.w(TAG, "Unable to initialize $TMPDIR", ex);
                }
            }
            Context deviceContext = context.createDeviceProtectedStorageContext();
            File codeCacheDir = deviceContext.getCodeCacheDir();
            File deviceCacheDir = deviceContext.getCacheDir();
            if (codeCacheDir == null || deviceCacheDir == null) {
                Log.w(TAG, "Unable to use shader/script cache: missing code-cache directory");
            } else {
                try {
                    int uid = Process.myUid();
                    String[] packages = getPackageManager().getPackagesForUid(uid);
                    if (packages != null) {
                        HardwareRenderer.setupDiskCache(deviceCacheDir);
                        RenderScriptCacheDir.setupDiskCache(codeCacheDir);
                    }
                } catch (RemoteException e) {
                    Trace.traceEnd(64L);
                    throw e.rethrowFromSystemServer();
                }
            }
        }
        GraphicsEnvironment.getInstance().setup(context, this.mCoreSettings);
        Trace.traceEnd(64L);
    }

    private String getInstrumentationLibrary(ApplicationInfo appInfo, InstrumentationInfo insInfo) {
        if (appInfo.primaryCpuAbi != null && appInfo.secondaryCpuAbi != null && appInfo.secondaryCpuAbi.equals(insInfo.secondaryCpuAbi)) {
            String secondaryIsa = VMRuntime.getInstructionSet(appInfo.secondaryCpuAbi);
            String secondaryDexCodeIsa = SystemProperties.get("ro.dalvik.vm.isa." + secondaryIsa);
            String secondaryIsa2 = secondaryDexCodeIsa.isEmpty() ? secondaryIsa : secondaryDexCodeIsa;
            String runtimeIsa = VMRuntime.getRuntime().vmInstructionSet();
            if (runtimeIsa.equals(secondaryIsa2)) {
                return insInfo.secondaryNativeLibraryDir;
            }
        }
        return insInfo.nativeLibraryDir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(19:1|(1:3)|4|(1:6)|7|(2:9|(70:11|12|(1:14)|15|(1:17)|18|(1:20)(1:241)|21|22|23|24|115|29|(1:31)(1:233)|32|(1:34)|(1:36)|37|(1:39)|40|(3:42|(1:44)(1:231)|45)(1:232)|46|(1:48)(1:230)|49|(1:51)(1:(1:229)(1:228))|52|(1:224)|(1:221)|(1:220)(1:62)|63|(1:65)|66|(1:68)(1:219)|69|70|71|(2:209|210)|73|(4:75|76|77|78)(1:208)|79|(1:81)|(1:83)(1:203)|84|(1:86)(1:202)|87|(2:89|(1:91)(2:92|(1:94)))|95|96|2de|(2:188|189)|102|(1:106)|117|118|119|120|(9:122|123|124|125|126|127|128|(2:130|(3:132|133|134))|177)(1:184)|135|136|138|139|(1:141)|142|143|(1:166)|147|(3:156|157|(1:161))|149|150|151))|242|12|(0)|15|(0)|18|(0)(0)|21|22|23|24|115|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0109, code lost:            android.util.Slog.e(android.app.ActivityThread.TAG, "Failed to parse serialized system font map");        android.graphics.Typeface.loadPreinstalledSystemFontMap();     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0116 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleBindApplication(android.app.ActivityThread.AppBindData r26) {
        /*
            Method dump skipped, instructions count: 1189
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleBindApplication(android.app.ActivityThread$AppBindData):void");
    }

    private void waitForDebugger(AppBindData data) {
        IActivityManager mgr = ActivityManager.getService();
        Slog.w(TAG, "Application " + data.info.getPackageName() + " is waiting for the debugger ...");
        try {
            mgr.showWaitingForDebugger(this.mAppThread, true);
            Debug.waitForDebugger();
            try {
                mgr.showWaitingForDebugger(this.mAppThread, false);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } catch (RemoteException ex2) {
            throw ex2.rethrowFromSystemServer();
        }
    }

    private void suspendAllAndSendVmStart(AppBindData data) {
        IActivityManager mgr = ActivityManager.getService();
        Slog.w(TAG, "Application " + data.info.getPackageName() + " is suspending. Debugger needs to resume to continue.");
        try {
            mgr.showWaitingForDebugger(this.mAppThread, true);
            Debug.suspendAllAndSendVmStart();
            try {
                mgr.showWaitingForDebugger(this.mAppThread, false);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } catch (RemoteException ex2) {
            throw ex2.rethrowFromSystemServer();
        }
    }

    private void initZipPathValidatorCallback() {
        if (CompatChanges.isChangeEnabled(SafeZipPathValidatorCallback.VALIDATE_ZIP_PATH_FOR_PATH_TRAVERSAL)) {
            ZipPathValidator.setCallback(new SafeZipPathValidatorCallback());
        } else {
            ZipPathValidator.clearCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetContentCaptureOptionsCallback(String packageName) {
        IBinder b;
        if (this.mContentCaptureOptionsCallback != null || (b = ServiceManager.getService(Context.CONTENT_CAPTURE_MANAGER_SERVICE)) == null) {
            return;
        }
        IContentCaptureManager service = IContentCaptureManager.Stub.asInterface(b);
        IContentCaptureOptionsCallback.Stub stub = new IContentCaptureOptionsCallback.Stub() { // from class: android.app.ActivityThread.3
            @Override // android.view.contentcapture.IContentCaptureOptionsCallback
            public void setContentCaptureOptions(ContentCaptureOptions options) throws RemoteException {
                if (ActivityThread.this.mInitialApplication != null) {
                    ActivityThread.this.mInitialApplication.setContentCaptureOptions(options);
                }
            }
        };
        this.mContentCaptureOptionsCallback = stub;
        try {
            service.registerContentCaptureOptionsCallback(packageName, stub);
        } catch (RemoteException e) {
            Slog.w(TAG, "registerContentCaptureOptionsCallback() failed: " + packageName, e);
            this.mContentCaptureOptionsCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInstrumentWithoutRestart(AppBindData data) {
        try {
            data.compatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
            data.info = getPackageInfoNoCheck(data.appInfo);
            this.mInstrumentingWithoutRestart = true;
            InstrumentationInfo ii = prepareInstrumentation(data);
            ContextImpl appContext = ContextImpl.createAppContext(this, data.info);
            initInstrumentation(ii, data, appContext);
            try {
                this.mInstrumentation.onCreate(data.instrumentationArgs);
            } catch (Exception e) {
                throw new RuntimeException("Exception thrown in onCreate() of " + data.instrumentationName + ": " + e.toString(), e);
            }
        } catch (Exception e2) {
            Slog.e(TAG, "Error in handleInstrumentWithoutRestart", e2);
        }
    }

    private InstrumentationInfo prepareInstrumentation(AppBindData data) {
        try {
            InstrumentationInfo ii = getPackageManager().getInstrumentationInfoAsUser(data.instrumentationName, 0, UserHandle.myUserId());
            if (ii == null) {
                throw new RuntimeException("Unable to find instrumentation info for: " + data.instrumentationName);
            }
            if (!Objects.equals(data.appInfo.primaryCpuAbi, ii.primaryCpuAbi) || !Objects.equals(data.appInfo.secondaryCpuAbi, ii.secondaryCpuAbi)) {
                Slog.w(TAG, "Package uses different ABI(s) than its instrumentation: package[" + data.appInfo.packageName + "]: " + data.appInfo.primaryCpuAbi + ", " + data.appInfo.secondaryCpuAbi + " instrumentation[" + ii.packageName + "]: " + ii.primaryCpuAbi + ", " + ii.secondaryCpuAbi);
            }
            this.mInstrumentationPackageName = ii.packageName;
            this.mInstrumentationAppDir = ii.sourceDir;
            this.mInstrumentationSplitAppDirs = ii.splitSourceDirs;
            this.mInstrumentationLibDir = getInstrumentationLibrary(data.appInfo, ii);
            this.mInstrumentedAppDir = data.info.getAppDir();
            this.mInstrumentedSplitAppDirs = data.info.getSplitAppDirs();
            this.mInstrumentedLibDir = data.info.getLibDir();
            return ii;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void initInstrumentation(InstrumentationInfo ii, AppBindData data, ContextImpl appContext) {
        ApplicationInfo instrApp;
        try {
            instrApp = getPackageManager().getApplicationInfo(ii.packageName, 0L, UserHandle.myUserId());
        } catch (RemoteException e) {
            instrApp = null;
        }
        if (instrApp == null) {
            instrApp = new ApplicationInfo();
        }
        ii.copyTo(instrApp);
        instrApp.initForUser(UserHandle.myUserId());
        LoadedApk pi = getPackageInfo(instrApp, data.compatInfo, appContext.getClassLoader(), false, true, false);
        ContextImpl instrContext = ContextImpl.createAppContext(this, pi, appContext.getOpPackageName());
        try {
            ClassLoader cl = instrContext.getClassLoader();
            this.mInstrumentation = (Instrumentation) cl.loadClass(data.instrumentationName.getClassName()).newInstance();
            ComponentName component = new ComponentName(ii.packageName, ii.name);
            this.mInstrumentation.init(this, instrContext, appContext, component, data.instrumentationWatcher, data.instrumentationUiAutomationConnection);
            if (this.mProfiler.profileFile != null && !ii.handleProfiling && this.mProfiler.profileFd == null) {
                this.mProfiler.handlingProfiling = true;
                File file = new File(this.mProfiler.profileFile);
                file.getParentFile().mkdirs();
                Debug.startMethodTracing(file.toString(), 8388608);
            }
        } catch (Exception e2) {
            throw new RuntimeException("Unable to instantiate instrumentation " + data.instrumentationName + ": " + e2.toString(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishInstrumentationWithoutRestart() {
        this.mInstrumentation.onDestroy();
        this.mInstrumentationPackageName = null;
        this.mInstrumentationAppDir = null;
        this.mInstrumentationSplitAppDirs = null;
        this.mInstrumentationLibDir = null;
        this.mInstrumentedAppDir = null;
        this.mInstrumentedSplitAppDirs = null;
        this.mInstrumentedLibDir = null;
        this.mInstrumentingWithoutRestart = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void finishInstrumentation(int resultCode, Bundle results) {
        IActivityManager am = ActivityManager.getService();
        if (this.mProfiler.profileFile != null && this.mProfiler.handlingProfiling && this.mProfiler.profileFd == null) {
            Debug.stopMethodTracing();
        }
        try {
            am.finishInstrumentation(this.mAppThread, resultCode, results);
            if (this.mInstrumentingWithoutRestart) {
                sendMessage(171, null);
            }
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private void installContentProviders(Context context, List<ProviderInfo> providers) {
        ArrayList<ContentProviderHolder> results = new ArrayList<>();
        for (ProviderInfo cpi : providers) {
            ContentProviderHolder cph = installProvider(context, null, cpi, false, true, true);
            if (cph != null) {
                cph.noReleaseNeeded = true;
                results.add(cph);
            }
        }
        try {
            ActivityManager.getService().publishContentProviders(getApplicationThread(), results);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:82:0x0069
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1166)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:1022)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:55)
        */
    public final android.content.IContentProvider acquireProvider(android.content.Context r19, java.lang.String r20, int r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.acquireProvider(android.content.Context, java.lang.String, int, boolean):android.content.IContentProvider");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ProviderKey getGetProviderKey(String auth, int userId) {
        ProviderKey lock;
        ProviderKey key = new ProviderKey(auth, userId);
        synchronized (this.mGetProviderKeys) {
            lock = this.mGetProviderKeys.get(key);
            if (lock == null) {
                lock = key;
                this.mGetProviderKeys.put(key, lock);
            }
        }
        return lock;
    }

    private final void incProviderRefLocked(ProviderRefCount prc, boolean stable) {
        int unstableDelta;
        if (stable) {
            prc.stableCount++;
            if (prc.stableCount == 1) {
                if (prc.removePending) {
                    unstableDelta = -1;
                    prc.removePending = false;
                    this.mH.removeMessages(131, prc);
                } else {
                    unstableDelta = 0;
                }
                try {
                    ActivityManager.getService().refContentProvider(prc.holder.connection, 1, unstableDelta);
                    return;
                } catch (RemoteException e) {
                    return;
                }
            }
            return;
        }
        prc.unstableCount++;
        if (prc.unstableCount == 1) {
            if (prc.removePending) {
                prc.removePending = false;
                this.mH.removeMessages(131, prc);
            } else {
                try {
                    ActivityManager.getService().refContentProvider(prc.holder.connection, 0, 1);
                } catch (RemoteException e2) {
                }
            }
        }
    }

    public final IContentProvider acquireExistingProvider(Context c, String auth, int userId, boolean stable) {
        synchronized (this.mProviderMap) {
            ProviderKey key = new ProviderKey(auth, userId);
            ProviderClientRecord pr = this.mProviderMap.get(key);
            if (pr == null) {
                return null;
            }
            IContentProvider provider = pr.mProvider;
            IBinder jBinder = provider.asBinder();
            if (!jBinder.isBinderAlive()) {
                Log.i(TAG, "Acquiring provider " + auth + " for user " + userId + ": existing object's process dead");
                handleUnstableProviderDiedLocked(jBinder, true);
                return null;
            }
            ProviderRefCount prc = this.mProviderRefCountMap.get(jBinder);
            if (prc != null) {
                incProviderRefLocked(prc, stable);
            }
            return provider;
        }
    }

    public final boolean releaseProvider(IContentProvider provider, boolean stable) {
        if (provider == null) {
            return false;
        }
        IBinder jBinder = provider.asBinder();
        synchronized (this.mProviderMap) {
            ProviderRefCount prc = this.mProviderRefCountMap.get(jBinder);
            if (prc == null) {
                return false;
            }
            boolean lastRef = false;
            if (stable) {
                if (prc.stableCount == 0) {
                    return false;
                }
                prc.stableCount--;
                if (prc.stableCount == 0) {
                    lastRef = prc.unstableCount == 0;
                    try {
                        ActivityManager.getService().refContentProvider(prc.holder.connection, -1, lastRef ? 1 : 0);
                    } catch (RemoteException e) {
                    }
                }
            } else {
                if (prc.unstableCount == 0) {
                    return false;
                }
                prc.unstableCount--;
                if (prc.unstableCount == 0) {
                    lastRef = prc.stableCount == 0;
                    if (!lastRef) {
                        try {
                            ActivityManager.getService().refContentProvider(prc.holder.connection, 0, -1);
                        } catch (RemoteException e2) {
                        }
                    }
                }
            }
            if (lastRef) {
                if (!prc.removePending) {
                    prc.removePending = true;
                    Message msg = this.mH.obtainMessage(131, prc);
                    this.mH.sendMessageDelayed(msg, 1000L);
                } else {
                    Slog.w(TAG, "Duplicate remove pending of provider " + prc.holder.info.name);
                }
            }
            return true;
        }
    }

    final void completeRemoveProvider(ProviderRefCount prc) {
        synchronized (this.mProviderMap) {
            if (prc.removePending) {
                prc.removePending = false;
                IBinder jBinder = prc.holder.provider.asBinder();
                ProviderRefCount existingPrc = this.mProviderRefCountMap.get(jBinder);
                if (existingPrc == prc) {
                    this.mProviderRefCountMap.remove(jBinder);
                }
                for (int i = this.mProviderMap.size() - 1; i >= 0; i--) {
                    ProviderClientRecord pr = this.mProviderMap.valueAt(i);
                    IBinder myBinder = pr.mProvider.asBinder();
                    if (myBinder == jBinder) {
                        this.mProviderMap.removeAt(i);
                    }
                }
                try {
                    ActivityManager.getService().removeContentProvider(prc.holder.connection, false);
                } catch (RemoteException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void handleUnstableProviderDied(IBinder provider, boolean fromClient) {
        synchronized (this.mProviderMap) {
            handleUnstableProviderDiedLocked(provider, fromClient);
        }
    }

    final void handleUnstableProviderDiedLocked(IBinder provider, boolean fromClient) {
        ProviderRefCount prc = this.mProviderRefCountMap.get(provider);
        if (prc != null) {
            this.mProviderRefCountMap.remove(provider);
            for (int i = this.mProviderMap.size() - 1; i >= 0; i--) {
                ProviderClientRecord pr = this.mProviderMap.valueAt(i);
                if (pr != null && pr.mProvider.asBinder() == provider) {
                    Slog.i(TAG, "Removing dead content provider:" + pr.mProvider.toString());
                    this.mProviderMap.removeAt(i);
                }
            }
            if (fromClient) {
                try {
                    ActivityManager.getService().unstableProviderDied(prc.holder.connection);
                } catch (RemoteException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void appNotRespondingViaProvider(IBinder provider) {
        synchronized (this.mProviderMap) {
            ProviderRefCount prc = this.mProviderRefCountMap.get(provider);
            if (prc != null) {
                try {
                    ActivityManager.getService().appNotRespondingViaProvider(prc.holder.connection);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private ProviderClientRecord installProviderAuthoritiesLocked(IContentProvider provider, ContentProvider localProvider, ContentProviderHolder holder) {
        char c;
        String[] auths = holder.info.authority.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        int userId = UserHandle.getUserId(holder.info.applicationInfo.uid);
        if (provider != null) {
            for (String auth : auths) {
                switch (auth.hashCode()) {
                    case -845193793:
                        if (auth.equals("com.android.contacts")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -456066902:
                        if (auth.equals("com.android.calendar")) {
                            c = 4;
                            break;
                        }
                        break;
                    case -172298781:
                        if (auth.equals(CallLog.AUTHORITY)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 63943420:
                        if (auth.equals(CallLog.SHADOW_AUTHORITY)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 783201304:
                        if (auth.equals(PropertyInvalidatedCache.MODULE_TELEPHONY)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 1312704747:
                        if (auth.equals("downloads")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 1995645513:
                        if (auth.equals(BlockedNumberContract.AUTHORITY)) {
                            c = 3;
                            break;
                        }
                        break;
                }
                c = 65535;
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        Binder.allowBlocking(provider.asBinder());
                        break;
                }
            }
        }
        ProviderClientRecord pcr = new ProviderClientRecord(auths, provider, localProvider, holder);
        for (String auth2 : auths) {
            ProviderKey key = new ProviderKey(auth2, userId);
            ProviderClientRecord existing = this.mProviderMap.get(key);
            if (existing != null) {
                Slog.w(TAG, "Content provider " + pcr.mHolder.info.name + " already published as " + auth2);
            } else {
                this.mProviderMap.put(key, pcr);
            }
        }
        return pcr;
    }

    private ContentProviderHolder installProvider(Context context, ContentProviderHolder holder, ProviderInfo info, boolean noisy, boolean noReleaseNeeded, boolean stable) {
        IContentProvider provider;
        ContentProvider localProvider;
        ProviderRefCount providerRefCount;
        ContentProviderHolder retHolder;
        if (holder == null || holder.provider == null) {
            if (noisy) {
                Slog.d(TAG, "Loading provider " + info.authority + ": " + info.name);
            }
            Context c = null;
            ApplicationInfo ai = info.applicationInfo;
            if (context.getPackageName().equals(ai.packageName)) {
                c = context;
            } else {
                Application application = this.mInitialApplication;
                if (application != null && application.getPackageName().equals(ai.packageName)) {
                    c = this.mInitialApplication;
                } else {
                    try {
                        try {
                            c = context.createPackageContext(ai.packageName, 1);
                        } catch (PackageManager.NameNotFoundException e) {
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                    }
                }
            }
            if (c == null) {
                Slog.w(TAG, "Unable to get context for package " + ai.packageName + " while loading content provider " + info.name);
                return null;
            }
            if (info.splitName != null) {
                try {
                    c = c.createContextForSplit(info.splitName);
                } catch (PackageManager.NameNotFoundException e3) {
                    throw new RuntimeException(e3);
                }
            }
            if (info.attributionTags != null && info.attributionTags.length > 0) {
                String attributionTag = info.attributionTags[0];
                c = c.createAttributionContext(attributionTag);
            }
            try {
                ClassLoader cl = c.getClassLoader();
                LoadedApk packageInfo = peekPackageInfo(ai.packageName, true);
                if (packageInfo == null) {
                    packageInfo = getSystemContext().mPackageInfo;
                }
                ContentProvider localProvider2 = packageInfo.getAppFactory().instantiateProvider(cl, info.name);
                provider = localProvider2.getIContentProvider();
                if (provider == null) {
                    Slog.e(TAG, "Failed to instantiate class " + info.name + " from sourceDir " + info.applicationInfo.sourceDir);
                    return null;
                }
                localProvider2.attachInfo(c, info);
                localProvider = localProvider2;
            } catch (Exception e4) {
                if (this.mInstrumentation.onException(null, e4)) {
                    return null;
                }
                throw new RuntimeException("Unable to get provider " + info.name + ": " + e4.toString(), e4);
            }
        } else {
            provider = holder.provider;
            localProvider = null;
        }
        synchronized (this.mProviderMap) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                IBinder jBinder = provider.asBinder();
                if (localProvider != null) {
                    ComponentName cname = new ComponentName(info.packageName, info.name);
                    ProviderClientRecord pr = this.mLocalProvidersByName.get(cname);
                    if (pr != null) {
                        IContentProvider iContentProvider = pr.mProvider;
                    } else {
                        ContentProviderHolder holder2 = new ContentProviderHolder(info);
                        holder2.provider = provider;
                        holder2.noReleaseNeeded = true;
                        pr = installProviderAuthoritiesLocked(provider, localProvider, holder2);
                        this.mLocalProviders.put(jBinder, pr);
                        this.mLocalProvidersByName.put(cname, pr);
                    }
                    retHolder = pr.mHolder;
                } else {
                    ProviderRefCount prc = this.mProviderRefCountMap.get(jBinder);
                    if (prc != null) {
                        if (!noReleaseNeeded) {
                            incProviderRefLocked(prc, stable);
                            try {
                                ActivityManager.getService().removeContentProvider(holder.connection, stable);
                            } catch (RemoteException e5) {
                            }
                        }
                    } else {
                        ProviderClientRecord client = installProviderAuthoritiesLocked(provider, localProvider, holder);
                        if (noReleaseNeeded) {
                            prc = new ProviderRefCount(holder, client, 1000, 1000);
                        } else {
                            if (stable) {
                                providerRefCount = new ProviderRefCount(holder, client, 1, 0);
                            } else {
                                providerRefCount = new ProviderRefCount(holder, client, 0, 1);
                            }
                            prc = providerRefCount;
                        }
                        this.mProviderRefCountMap.put(jBinder, prc);
                    }
                    retHolder = prc.holder;
                }
                return retHolder;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRunIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) {
        try {
            Method main = Class.forName(entryPoint).getMethod("main", String[].class);
            main.invoke(null, entryPointArgs);
            System.exit(0);
        } catch (ReflectiveOperationException e) {
            throw new AndroidRuntimeException("runIsolatedEntryPoint failed", e);
        }
    }

    private void attach(boolean system, long startSeq) {
        sCurrentActivityThread = this;
        this.mConfigurationController = new ConfigurationController(this);
        this.mSystemThread = system;
        this.mStartSeq = startSeq;
        if (!system) {
            DdmHandleAppName.setAppName("<pre-initialized>", UserHandle.myUserId());
            RuntimeInit.setApplicationObject(this.mAppThread.asBinder());
            IActivityManager mgr = ActivityManager.getService();
            try {
                mgr.attachApplication(this.mAppThread, startSeq);
                BinderInternal.addGcWatcher(new Runnable() { // from class: android.app.ActivityThread.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!ActivityThread.this.mSomeActivitiesChanged) {
                            return;
                        }
                        Runtime runtime = Runtime.getRuntime();
                        long dalvikMax = runtime.maxMemory();
                        long dalvikUsed = runtime.totalMemory() - runtime.freeMemory();
                        if (dalvikUsed > (3 * dalvikMax) / 4) {
                            ActivityThread.this.mSomeActivitiesChanged = false;
                            try {
                                ActivityTaskManager.getService().releaseSomeActivities(ActivityThread.this.mAppThread);
                            } catch (RemoteException e) {
                                throw e.rethrowFromSystemServer();
                            }
                        }
                    }
                });
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } else {
            DdmHandleAppName.setAppName("system_process", UserHandle.myUserId());
            try {
                Instrumentation instrumentation = new Instrumentation();
                this.mInstrumentation = instrumentation;
                instrumentation.basicInit(this);
                ContextImpl context = ContextImpl.createAppContext(this, getSystemContext().mPackageInfo);
                Application makeApplicationInner = context.mPackageInfo.makeApplicationInner(true, null);
                this.mInitialApplication = makeApplicationInner;
                makeApplicationInner.onCreate();
            } catch (Exception e) {
                throw new RuntimeException("Unable to instantiate Application():" + e.toString(), e);
            }
        }
        ViewRootImpl.ConfigChangedCallback configChangedCallback = new ViewRootImpl.ConfigChangedCallback() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda0
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(Configuration configuration) {
                ActivityThread.this.lambda$attach$2(configuration);
            }
        };
        ViewRootImpl.addConfigCallback(configChangedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$2(Configuration globalConfig) {
        synchronized (this.mResourcesManager) {
            if (this.mResourcesManager.applyConfigurationToResources(globalConfig, null)) {
                this.mConfigurationController.updateLocaleListFromAppContext(this.mInitialApplication.getApplicationContext());
                Configuration updatedConfig = this.mConfigurationController.updatePendingConfiguration(globalConfig);
                if (updatedConfig != null) {
                    sendMessage(118, globalConfig);
                    this.mPendingConfiguration = updatedConfig;
                }
            }
        }
    }

    public static ActivityThread systemMain() {
        ThreadedRenderer.initForSystemProcess();
        ActivityThread thread = new ActivityThread();
        thread.attach(true, 0L);
        return thread;
    }

    public static void updateHttpProxy(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        Proxy.setHttpProxyConfiguration(cm.getDefaultProxy());
    }

    public final void installSystemProviders(List<ProviderInfo> providers) {
        if (providers != null) {
            installContentProviders(this.mInitialApplication, providers);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bundle getCoreSettings() {
        Bundle bundle;
        synchronized (this.mCoreSettingsLock) {
            bundle = this.mCoreSettings;
        }
        return bundle;
    }

    public int getIntCoreSetting(String key, int defaultValue) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return defaultValue;
            }
            return bundle.getInt(key, defaultValue);
        }
    }

    public String getStringCoreSetting(String key, String defaultValue) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return defaultValue;
            }
            return bundle.getString(key, defaultValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFloatCoreSetting(String key, float defaultValue) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return defaultValue;
            }
            return bundle.getFloat(key, defaultValue);
        }
    }

    /* loaded from: classes.dex */
    private static class AndroidOs extends ForwardingOs {
        public static void install() {
            libcore.io.Os def;
            do {
                def = libcore.io.Os.getDefault();
            } while (!libcore.io.Os.compareAndSetDefault(def, new AndroidOs(def)));
        }

        private AndroidOs(libcore.io.Os os) {
            super(os);
        }

        private FileDescriptor openDeprecatedDataPath(String path, int mode) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath(path);
            Log.v(ActivityThread.TAG, "Redirecting " + path + " to " + uri);
            ContentResolver cr = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                FileDescriptor fd = new FileDescriptor();
                fd.setInt$(cr.openFileDescriptor(uri, FileUtils.translateModePosixToString(mode)).detachFd());
                return fd;
            } catch (FileNotFoundException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.ENOENT);
            } catch (SecurityException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.EACCES);
            }
        }

        private void deleteDeprecatedDataPath(String path) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath(path);
            Log.v(ActivityThread.TAG, "Redirecting " + path + " to " + uri);
            ContentResolver cr = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                if (cr.delete(uri, null, null) == 0) {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.ENOENT);
            } catch (SecurityException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.EACCES);
            }
        }

        public boolean access(String path, int mode) throws ErrnoException {
            if (path != null && path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                IoUtils.closeQuietly(openDeprecatedDataPath(path, FileUtils.translateModeAccessToPosix(mode)));
                return true;
            }
            return super.access(path, mode);
        }

        public FileDescriptor open(String path, int flags, int mode) throws ErrnoException {
            if (path != null && path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return openDeprecatedDataPath(path, mode);
            }
            return super.open(path, flags, mode);
        }

        public StructStat stat(String path) throws ErrnoException {
            if (path != null && path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                FileDescriptor fd = openDeprecatedDataPath(path, OsConstants.O_RDONLY);
                try {
                    return Os.fstat(fd);
                } finally {
                    IoUtils.closeQuietly(fd);
                }
            }
            return super.stat(path);
        }

        public void unlink(String path) throws ErrnoException {
            if (path != null && path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(path);
            } else {
                super.unlink(path);
            }
        }

        public void remove(String path) throws ErrnoException {
            if (path != null && path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(path);
            } else {
                super.remove(path);
            }
        }

        public void rename(String oldPath, String newPath) throws ErrnoException {
            try {
                super.rename(oldPath, newPath);
            } catch (ErrnoException e) {
                if (e.errno == OsConstants.EXDEV && oldPath.startsWith("/storage/emulated") && newPath.startsWith("/storage/emulated")) {
                    Log.v(ActivityThread.TAG, "Recovering failed rename " + oldPath + " to " + newPath);
                    try {
                        Files.move(new File(oldPath).toPath(), new File(newPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        return;
                    } catch (IOException e2) {
                        Log.e(ActivityThread.TAG, "Rename recovery failed ", e2);
                        throw e;
                    }
                }
                throw e;
            }
        }
    }

    private static void setConscryptValidator() {
        Log.d(TAG, "setConscryptValidator");
        for (Provider p : Security.getProviders()) {
            if (p instanceof OpenSSLProvider) {
                Log.d(TAG, "setConscryptValidator - put");
                p.put("CertPathValidator.PKIX", "android.sec.enterprise.certificate.DelegatingCertPathValidator");
                p.put("CertPathValidator.PKIX ImplementedIn", ExifInterface.TAG_SOFTWARE);
                p.put("CertPathValidator.PKIX ValidationAlgorithm", "RFC3280");
            }
        }
    }

    public static void main(String[] args) {
        Trace.traceBegin(64L, "ActivityThreadMain");
        AndroidOs.install();
        CloseGuard.setEnabled(false);
        Environment.initForCurrentUser();
        File configDir = Environment.getUserConfigDirectory(UserHandle.myUserId());
        TrustedCertificateStore.setDefaultUserDirectory(configDir);
        initializeMainlineModules();
        Process.setArgV0("<pre-initialized>");
        Looper.prepareMainLooper();
        long startSeq = 0;
        if (args != null) {
            for (int i = args.length - 1; i >= 0; i--) {
                if (args[i] != null && args[i].startsWith(PROC_START_SEQ_IDENT)) {
                    startSeq = Long.parseLong(args[i].substring(PROC_START_SEQ_IDENT.length()));
                }
            }
        }
        addUcmKeyStoreProviderForAppContext();
        setConscryptValidator();
        ActivityThread thread = new ActivityThread();
        thread.attach(false, startSeq);
        if (sMainThreadHandler == null) {
            sMainThreadHandler = thread.getHandler();
        }
        Trace.traceEnd(64L);
        Looper.loop();
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

    public static void initializeMainlineModules() {
        TelephonyFrameworkInitializer.setTelephonyServiceManager(new TelephonyServiceManager());
        StatsFrameworkInitializer.setStatsServiceManager(new StatsServiceManager());
        MediaFrameworkPlatformInitializer.setMediaServiceManager(new MediaServiceManager());
        MediaFrameworkInitializer.setMediaServiceManager(new MediaServiceManager());
        BluetoothFrameworkInitializer.setBluetoothServiceManager(new BluetoothServiceManager());
        BluetoothFrameworkInitializer.setBinderCallsStatsInitializer(new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BinderCallsStats.startForBluetooth((Context) obj);
            }
        });
        NfcFrameworkInitializer.setNfcServiceManager(new NfcServiceManager());
        DeviceConfigInitializer.setDeviceConfigServiceManager(new DeviceConfigServiceManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgePendingResources() {
        Trace.traceBegin(64L, "purgePendingResources");
        nPurgePendingResources();
        Trace.traceEnd(64L);
    }

    public static boolean isProtectedComponent(ActivityInfo ai) {
        return isProtectedComponent(ai, ai.permission);
    }

    public static boolean isProtectedComponent(ServiceInfo si) {
        return isProtectedComponent(si, si.permission);
    }

    private static boolean isProtectedComponent(ComponentInfo ci, String permission) {
        if (!StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        if (!ci.exported) {
            return true;
        }
        if (permission != null) {
            try {
                PermissionInfo pi = getPermissionManager().getPermissionInfo(permission, currentOpPackageName(), 0);
                if (pi != null) {
                    return pi.getProtection() == 2;
                }
                return false;
            } catch (RemoteException e) {
            }
        }
        return false;
    }

    public static boolean isProtectedBroadcast(Intent intent) {
        if (!StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        try {
            return getPackageManager().isProtectedBroadcast(intent.getAction());
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.app.ActivityThreadInternal
    public boolean isInDensityCompatMode() {
        return this.mDensityCompatMode;
    }

    private void updateDefaultNavigationBarColor() {
        for (Map.Entry<IBinder, ActivityClientRecord> entry : this.mActivities.entrySet()) {
            ActivityClientRecord r = entry.getValue();
            if (r.window instanceof PhoneWindow) {
                int settingsColor = this.mCoreSettings.getInt("navigationbar_current_color", ((PhoneWindow) r.window).getDeviceDefaultNavigationBarColor());
                ((PhoneWindow) r.window).setSettingsNavigationBarColor(settingsColor);
                ((PhoneWindow) r.window).updateDefaultNavigationBarColor();
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleCoreStatesChanged(Bundle states) {
        int changes = MultiWindowCoreState.getInstance().updateFrom(states);
        if (changes != 0) {
            notifyMultiWindowCoreStateChanges(changes);
        }
    }

    public void registerMultiWindowCoreStateListener(MultiWindowCoreState.MultiWindowCoreStateListener listener) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            this.mMultiWindowCoreStateListeners.add(listener);
        }
    }

    public void unregisterMultiWindowCoreStateListener(MultiWindowCoreState.MultiWindowCoreStateListener listener) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            this.mMultiWindowCoreStateListeners.remove(listener);
        }
    }

    private void notifyMultiWindowCoreStateChanges(int changes) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            for (MultiWindowCoreState.MultiWindowCoreStateListener listener : this.mMultiWindowCoreStateListeners) {
                listener.onMultiWindowCoreStateChanged(changes);
            }
        }
    }

    public static int getProcessDisplayId() {
        return sProcessDisplayId;
    }

    public static boolean isInDexDisplay() {
        return sProcessDisplayId == 2;
    }

    public float getDssScale() {
        return this.mDssScale;
    }

    public CompatibilityInfo getCompatInfo() {
        return this.mCompatibilityInfo;
    }

    public static boolean isFixedAppContextDisplay() {
        return sFixedAppContextDisplay;
    }

    private void updateConfigurationForDexCompatIfNeeded(ActivityClientRecord r, Configuration activityOverrideConfig) {
        if (isDexCompatMode() && activityOverrideConfig.dexCompatEnabled == 2) {
            DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
            dm.disableLocalDisplayInfoCaches();
            Configuration threadConfig = new Configuration(this.mConfiguration);
            int changes = threadConfig.updateFromDexCompatTaskConfig(activityOverrideConfig);
            if (changes != 0) {
                threadConfig.seq = 0;
                Slog.i(TAG, "[DexCompat] updateConfigurationForDexCompatIfNeeded: threadConfig, changes=0x" + Integer.toHexString(changes) + ", new=" + threadConfig + ", old=" + this.mConfiguration + ", override=" + activityOverrideConfig + (CoreRune.SAFE_DEBUG ? ", r=" + r : "") + ", Callers=" + Debug.getCallers(3));
                handleConfigurationChanged(threadConfig, this.mLastReportedDeviceId);
            }
        }
    }

    private boolean isDexMode() {
        Configuration configuration = this.mConfiguration;
        return configuration != null && configuration.semDesktopModeEnabled == 1;
    }

    public boolean isDexCompatMode() {
        return isDexMode() && this.mConfiguration.dexCompatEnabled == 2;
    }

    private void handleDexTaskDockingChangeIfNeeded(Activity activity, Configuration newConfig) {
        int state = newConfig.windowConfiguration.getDexTaskDockingState();
        activity.onDexTaskDockingChanged(state);
    }

    private void performReleaseActivityFocusIfNeeded(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null && r.activity != null) {
            r.activity.releaseActivityFocusIfNeeded();
        }
    }

    public boolean mayStayActivityFocus(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        return (r == null || r.paused || r.stopped) ? false : true;
    }

    private boolean hasResumedPopOver() {
        if (this.mActivities.isEmpty()) {
            return false;
        }
        for (int i = this.mActivities.size() - 1; i >= 0; i--) {
            ActivityClientRecord activity = this.mActivities.valueAt(i);
            if (activity.getLifecycleState() == 3 && activity.overrideConfig != null && activity.overrideConfig.windowConfiguration.isPopOver()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRelaunchingActivity(IBinder token) {
        synchronized (this.mResourcesManager) {
            for (int i = this.mRelaunchingActivities.size() - 1; i >= 0; i--) {
                ActivityClientRecord activity = this.mRelaunchingActivities.get(i);
                if (activity.token == token) {
                    return true;
                }
            }
            return false;
        }
    }

    private void setActivityCurrentConfigIfPossible(Activity activity, Configuration config) {
        Window win = activity.getWindow();
        if (win instanceof PhoneWindow) {
            ((PhoneWindow) win).setActivityCurrentConfig(config);
        }
    }

    public void dumpProcessAdjustmentInfo(PrintWriter pw) {
        pw.print("  ProcessConfig");
        pw.print("(#" + getProcessDisplayId() + ")=");
        pw.println(getConfiguration());
        Application application = this.mInitialApplication;
        if (application != null) {
            Configuration resConfig = application.getResources().getConfiguration();
            pw.println("  Application ResourcesConfig=" + resConfig);
        }
    }

    private void clearDecorViewContentCaptureSession(ActivityClientRecord r) {
        if (r.window != null) {
            View decor = r.window.getDecorView();
            if (decor instanceof DecorView) {
                ((DecorView) decor).resetContentCaptureSession();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getProfileSizeOfApp(String pkg) {
        long len = 0;
        if (pkg != null) {
            try {
                File profile = new File("/data/misc/profiles/cur/0/" + pkg + "/primary.prof");
                if (profile.exists()) {
                    len = profile.length();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
        SecIpmManager mSecIpmManager = (SecIpmManager) getApplication().getSystemService("PkgPredictorService");
        if (mSecIpmManager != null) {
            Slog.d("[secipm]", "mSecIpmManager setProfileLength " + pkg + " profile:" + len);
            mSecIpmManager.setProfileLength(pkg, Process.myUid(), len);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFlingerFlag(String pkgName, boolean flag) {
        Choreographer choreographer = Choreographer.getMainThreadInstance();
        if (choreographer != null) {
            choreographer.setIsFg(flag);
        }
    }

    private static void addUcmKeyStoreProviderForAppContext() {
        if (!SystemProperties.getBoolean(KnoxUcmKeyStoreProvider.PROPERTY_UCM_CRYPTO, false)) {
            return;
        }
        UcmKeyStoreHelper.addUcmProvider();
    }
}

package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.IOnProjectionStateChangedListener;
import android.app.IUiModeManager;
import android.app.IUiModeManagerCallback;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.Sandman;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.internal.app.DisableCarModeActivity;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.twilight.TwilightListener;
import com.android.server.twilight.TwilightManager;
import com.android.server.twilight.TwilightState;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public final class UiModeManagerService extends SystemService {
    public final LocalTime DEFAULT_CUSTOM_NIGHT_END_TIME;
    public final LocalTime DEFAULT_CUSTOM_NIGHT_START_TIME;
    public ActivityTaskManagerInternal mActivityTaskManager;
    public AlarmManager mAlarmManager;
    public boolean mAutoModeChangeImmediately;
    public final BroadcastReceiver mBatteryReceiver;
    public boolean mCar;
    public int mCarModeEnableFlags;
    public boolean mCarModeEnabled;
    public boolean mCarModeKeepsScreenOn;
    public Map mCarModePackagePriority;
    public boolean mCharging;
    public boolean mComputedNightMode;
    public Configuration mConfiguration;
    public final ContentObserver mContrastObserver;
    public final SparseArray mContrasts;
    public int mCurUiMode;
    public int mCurrentUser;
    public LocalTime mCustomAutoNightModeEndMilliseconds;
    public LocalTime mCustomAutoNightModeStartMilliseconds;
    public final AlarmManager.OnAlarmListener mCustomTimeListener;
    public final ContentObserver mDarkThemeObserver;
    public int mDefaultUiModeType;
    public boolean mDeskModeKeepsScreenOn;
    public boolean mDesktopModeEnabled;
    public final BroadcastReceiver mDockModeReceiver;
    public int mDockState;
    public boolean mDreamsDisabledByAmbientModeSuppression;
    public boolean mEnableCarDockLaunch;
    public final Handler mHandler;
    public boolean mHoldingConfiguration;
    public final Injector mInjector;
    public boolean mIsNightModeRegistered;
    public KeyguardManager mKeyguardManager;
    public boolean mLastBedtimeRequestedNightMode;
    public int mLastBroadcastState;
    public PowerManagerInternal mLocalPowerManager;
    public final LocalService mLocalService;
    public final Object mLock;
    public boolean mNewDexModeEnabled;
    public int mNightMode;
    public int mNightModeCustomType;
    public boolean mNightModeLocked;
    public final List mNightPriorityAllowedPackagesFromScpm;
    public final PackageFeatureUserChange mNightPriorityAppliedPackages;
    public NotificationManager mNotificationManager;
    public final BroadcastReceiver mOnPackageAdded;
    public final BroadcastReceiver mOnScreenOffHandler;
    public final BroadcastReceiver mOnShutdown;
    public final BroadcastReceiver mOnTimeChangedHandler;
    public boolean mOverrideNightModeOff;
    public boolean mOverrideNightModeOn;
    public int mOverrideNightModeUser;
    public final PackageFeatureUserChange mPackagesNeedToShowDialog;
    public PowerManager mPowerManager;
    public boolean mPowerSave;
    public SparseArray mProjectionHolders;
    public SparseArray mProjectionListeners;
    public final BroadcastReceiver mResultReceiver;
    public final IUiModeManager.Stub mService;
    public int mSetUiMode;
    public final BroadcastReceiver mSettingsRestored;
    public boolean mSetupWizardComplete;
    public final ContentObserver mSetupWizardObserver;
    public boolean mShopDemo;
    public boolean mStartDreamImmediatelyOnDock;
    public StatusBarManager mStatusBarManager;
    public boolean mSystemReady;
    public boolean mTelevision;
    public boolean mToggleNewDexMode;
    public final TwilightListener mTwilightListener;
    public TwilightManager mTwilightManager;
    public boolean mUiModeLocked;
    public final SparseArray mUiModeManagerCallbacks;
    public boolean mVrHeadset;
    public final IVrStateCallbacks mVrStateCallbacks;
    public boolean mWaitForScreenOff;
    public PowerManager.WakeLock mWakeLock;
    public boolean mWatch;
    public WindowManagerInternal mWindowManager;
    public static final String TAG = UiModeManager.class.getSimpleName();
    public static final Set SUPPORTED_NIGHT_MODE_CUSTOM_TYPES = new ArraySet(new Integer[]{0, 1});
    public static DateTimeFormatter sFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");

    public static boolean isDeskDockState(int i) {
        return i == 1 || i == 3 || i == 4;
    }

    public final long getCustomTimeToNano(long j) {
        return j * 60 * 1000000000;
    }

    public UiModeManagerService(Context context) {
        this(context, false, null, new Injector());
    }

    public UiModeManagerService(Context context, boolean z, TwilightManager twilightManager, Injector injector) {
        super(context);
        this.mLock = new Object();
        this.mDockState = 0;
        this.mLastBroadcastState = 0;
        this.mNightMode = 1;
        this.mNightModeCustomType = -1;
        LocalTime of = LocalTime.of(19, 0);
        this.DEFAULT_CUSTOM_NIGHT_START_TIME = of;
        LocalTime of2 = LocalTime.of(7, 0);
        this.DEFAULT_CUSTOM_NIGHT_END_TIME = of2;
        this.mCustomAutoNightModeStartMilliseconds = of;
        this.mCustomAutoNightModeEndMilliseconds = of2;
        this.mCarModePackagePriority = new HashMap();
        this.mCarModeEnabled = false;
        this.mCharging = false;
        this.mPowerSave = false;
        this.mWaitForScreenOff = false;
        this.mLastBedtimeRequestedNightMode = false;
        this.mStartDreamImmediatelyOnDock = true;
        this.mDreamsDisabledByAmbientModeSuppression = false;
        this.mEnableCarDockLaunch = true;
        this.mUiModeLocked = false;
        this.mNightModeLocked = false;
        this.mCurUiMode = 0;
        this.mSetUiMode = 0;
        this.mHoldingConfiguration = false;
        this.mConfiguration = new Configuration();
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mOverrideNightModeUser = 0;
        this.mLocalService = new LocalService();
        this.mUiModeManagerCallbacks = new SparseArray();
        this.mContrasts = new SparseArray();
        this.mAutoModeChangeImmediately = false;
        this.mIsNightModeRegistered = false;
        this.mDesktopModeEnabled = false;
        this.mNewDexModeEnabled = false;
        this.mToggleNewDexMode = false;
        this.mResultReceiver = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (getResultCode() != -1) {
                    return;
                }
                int intExtra = intent.getIntExtra("enableFlags", 0);
                int intExtra2 = intent.getIntExtra("disableFlags", 0);
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.updateAfterBroadcastLocked(intent.getAction(), intExtra, intExtra2);
                }
            }
        };
        this.mDockModeReceiver = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                UiModeManagerService.this.updateDockState(intent.getIntExtra("android.intent.extra.DOCK_STATE", 0));
            }
        };
        this.mBatteryReceiver = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                    UiModeManagerService.this.mCharging = intent.getIntExtra("plugged", 0) != 0;
                }
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mSystemReady && (uiModeManagerService.mCarModeEnabled || UiModeManagerService.isDeskDockState(UiModeManagerService.this.mDockState))) {
                        UiModeManagerService.this.updateLocked(0, 0);
                    }
                }
            }
        };
        this.mTwilightListener = new TwilightListener() { // from class: com.android.server.UiModeManagerService.4
            @Override // com.android.server.twilight.TwilightListener
            public void onTwilightStateChanged(TwilightState twilightState) {
                synchronized (UiModeManagerService.this.mLock) {
                    if (UiModeManagerService.this.mNightMode == 0) {
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        if (uiModeManagerService.mSystemReady) {
                            if (!uiModeManagerService.shouldApplyAutomaticChangesImmediately() && !UiModeManagerService.this.mAutoModeChangeImmediately) {
                                UiModeManagerService.this.registerScreenOffEventLocked();
                            }
                            if (UiModeManagerService.this.mAutoModeChangeImmediately) {
                                LogWrapper.i(UiModeManagerService.TAG, "Twilight state is changed immediately after MODE_NIGHT_AUTO setting");
                            }
                            UiModeManagerService.this.updateLocked(0, 0);
                        }
                    }
                }
            }
        };
        this.mOnScreenOffHandler = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.unregisterScreenOffEventLocked();
                    UiModeManagerService.this.updateLocked(0, 0);
                }
            }
        };
        this.mOnTimeChangedHandler = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.updateCustomTimeLocked();
                }
            }
        };
        this.mCustomTimeListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                UiModeManagerService.this.lambda$new$0();
            }
        };
        this.mVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.server.UiModeManagerService.7
            public void onVrStateChanged(boolean z2) {
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.mVrHeadset = z2;
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mSystemReady) {
                        uiModeManagerService.updateLocked(0, 0);
                    }
                }
            }
        };
        this.mSetupWizardObserver = new ContentObserver(handler) { // from class: com.android.server.UiModeManagerService.8
            @Override // android.database.ContentObserver
            public void onChange(boolean z2, Uri uri) {
                synchronized (UiModeManagerService.this.mLock) {
                    if (UiModeManagerService.this.setupWizardCompleteForCurrentUser() && !z2) {
                        UiModeManagerService.this.mSetupWizardComplete = true;
                        UiModeManagerService.this.getContext().getContentResolver().unregisterContentObserver(UiModeManagerService.this.mSetupWizardObserver);
                        Context context2 = UiModeManagerService.this.getContext();
                        UiModeManagerService.this.updateNightModeFromSettingsLocked(context2, context2.getResources(), UserHandle.getCallingUserId());
                        UiModeManagerService.this.updateLocked(0, 0);
                    }
                }
            }
        };
        this.mDarkThemeObserver = new ContentObserver(handler) { // from class: com.android.server.UiModeManagerService.9
            @Override // android.database.ContentObserver
            public void onChange(boolean z2, Uri uri) {
                UiModeManagerService.this.updateSystemProperties();
            }
        };
        this.mContrastObserver = new AnonymousClass10(handler);
        this.mOnPackageAdded = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int intExtra;
                Uri data = intent.getData();
                String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                if (schemeSpecificPart == null || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000) {
                    return;
                }
                String action = intent.getAction();
                if (!("android.intent.action.PACKAGE_ADDED".equals(action) && intent.getBooleanExtra("android.intent.extra.REPLACING", false)) && UiModeManagerService.this.isNightPriorityAllowed(schemeSpecificPart)) {
                    Log.i(UiModeManagerService.TAG, "onReceive : " + action + ", package : " + schemeSpecificPart + ", " + intExtra);
                    UiModeManagerService.this.setPackageNightModeInner(schemeSpecificPart, intExtra, 2);
                    synchronized (UiModeManagerService.this.mPackagesNeedToShowDialog) {
                        UiModeManagerService.this.mPackagesNeedToShowDialog.putValue(intExtra, schemeSpecificPart, Boolean.TRUE);
                    }
                }
            }
        };
        this.mOnShutdown = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (UiModeManagerService.this.mNightMode == 0) {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    uiModeManagerService.persistComputedNightMode(uiModeManagerService.mCurrentUser);
                }
            }
        };
        this.mSettingsRestored = new BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.14
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (Arrays.asList("ui_night_mode", "dark_theme_custom_start_time", "dark_theme_custom_end_time").contains(intent.getExtras().getCharSequence("setting_name"))) {
                    synchronized (UiModeManagerService.this.mLock) {
                        UiModeManagerService.this.updateNightModeFromSettingsLocked(context2, context2.getResources(), UserHandle.getCallingUserId());
                        UiModeManagerService.this.updateConfigurationLocked();
                    }
                }
            }
        };
        this.mService = new AnonymousClass15();
        this.mNightPriorityAllowedPackagesFromScpm = new ArrayList();
        String str = PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY;
        this.mNightPriorityAppliedPackages = new PackageFeatureUserChange(IInstalld.FLAG_USE_QUOTA, str, "NightModePriorityAppliedPackages");
        this.mPackagesNeedToShowDialog = new PackageFeatureUserChange(IInstalld.FLAG_FORCE, str, "NightModeShowDialogPackages");
        this.mConfiguration.setToDefaults();
        this.mSetupWizardComplete = z;
        this.mTwilightManager = twilightManager;
        this.mInjector = injector;
    }

    public static Intent buildHomeIntent(String str) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(str);
        intent.setFlags(270532608);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this.mLock) {
            updateCustomTimeLocked();
        }
    }

    /* renamed from: com.android.server.UiModeManagerService$10, reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass10 extends ContentObserver {
        public AnonymousClass10(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            synchronized (UiModeManagerService.this.mLock) {
                if (UiModeManagerService.this.updateContrastLocked()) {
                    final float contrastLocked = UiModeManagerService.this.getContrastLocked();
                    ((RemoteCallbackList) UiModeManagerService.this.mUiModeManagerCallbacks.get(UiModeManagerService.this.mCurrentUser, new RemoteCallbackList())).broadcast(FunctionalUtils.ignoreRemoteException(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.UiModeManagerService$10$$ExternalSyntheticLambda0
                        public final void acceptOrThrow(Object obj) {
                            ((IUiModeManagerCallback) obj).notifyContrastChanged(contrastLocked);
                        }
                    }));
                }
            }
        }
    }

    public final void updateSystemProperties() {
        int intForUser = Settings.Secure.getIntForUser(getContext().getContentResolver(), "ui_night_mode", this.mNightMode, 0);
        if (intForUser == 0 || intForUser == 3) {
            intForUser = 2;
        }
        SystemProperties.set("persist.sys.theme", Integer.toString(intForUser));
    }

    public void setStartDreamImmediatelyOnDock(boolean z) {
        this.mStartDreamImmediatelyOnDock = z;
    }

    public void setDreamsDisabledByAmbientModeSuppression(boolean z) {
        this.mDreamsDisabledByAmbientModeSuppression = z;
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mCurrentUser = targetUser2.getUserIdentifier();
        if (this.mNightMode == 0) {
            persistComputedNightMode(targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            synchronized (this.mLock) {
                Context context = getContext();
                boolean z = true;
                this.mSystemReady = true;
                this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                this.mPowerManager = powerManager;
                this.mWakeLock = powerManager.newWakeLock(26, TAG);
                this.mWindowManager = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                this.mActivityTaskManager = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
                this.mAlarmManager = (AlarmManager) getContext().getSystemService("alarm");
                TwilightManager twilightManager = (TwilightManager) getLocalService(TwilightManager.class);
                if (twilightManager != null) {
                    this.mTwilightManager = twilightManager;
                }
                this.mLocalPowerManager = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
                initPowerSave();
                if (this.mDockState != 2) {
                    z = false;
                }
                this.mCarModeEnabled = z;
                registerVrStateListener();
                context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("ui_night_mode"), false, this.mDarkThemeObserver, 0);
                context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("contrast_level"), false, this.mContrastObserver, -1);
                context.registerReceiver(this.mDockModeReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
                context.registerReceiver(this.mBatteryReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                context.registerReceiver(this.mSettingsRestored, new IntentFilter("android.os.action.SETTING_RESTORED"));
                context.registerReceiver(this.mOnShutdown, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
                intentFilter.addDataScheme("package");
                context.registerReceiver(this.mOnPackageAdded, intentFilter);
                context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("shopdemo"), false, new ContentObserver(this.mHandler) { // from class: com.android.server.UiModeManagerService.11
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z2, Uri uri) {
                        UiModeManagerService.this.updateShopDemo();
                    }
                }, 0);
                updateShopDemo();
                updateConfigurationLocked();
                applyConfigurationExternallyLocked();
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        final Context context = getContext();
        verifySetupWizardCompleted();
        final Resources resources = context.getResources();
        this.mStartDreamImmediatelyOnDock = resources.getBoolean(17891845);
        this.mDreamsDisabledByAmbientModeSuppression = resources.getBoolean(17891640);
        this.mNightMode = resources.getInteger(R.integer.config_longPressOnPowerBehavior);
        this.mDefaultUiModeType = resources.getInteger(R.integer.config_maxUiWidth);
        this.mCarModeKeepsScreenOn = resources.getInteger(R.integer.config_doubleTapOnHomeBehavior) == 1;
        this.mDeskModeKeepsScreenOn = resources.getInteger(R.integer.config_minNumVisibleRecentTasks) == 1;
        this.mEnableCarDockLaunch = resources.getBoolean(17891660);
        this.mUiModeLocked = resources.getBoolean(17891757);
        this.mNightModeLocked = resources.getBoolean(17891756);
        PackageManager packageManager = context.getPackageManager();
        this.mTelevision = packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback");
        this.mCar = packageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mWatch = packageManager.hasSystemFeature("android.hardware.type.watch");
        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UiModeManagerService.this.lambda$onStart$1(context, resources);
            }
        }, TAG + ".onStart");
        publishBinderService("uimode", this.mService);
        publishLocalService(UiModeManagerInternal.class, this.mLocalService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$1(Context context, Resources resources) {
        synchronized (this.mLock) {
            TwilightManager twilightManager = (TwilightManager) getLocalService(TwilightManager.class);
            if (twilightManager != null) {
                this.mTwilightManager = twilightManager;
            }
            updateNightModeFromSettingsLocked(context, resources, UserHandle.getCallingUserId());
            updateSystemProperties();
        }
    }

    public final void updateShopDemo() {
        this.mShopDemo = Settings.Secure.getInt(getContext().getContentResolver(), "shopdemo", 0) == 1;
    }

    public final void persistComputedNightMode(int i) {
        Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_last_computed", this.mComputedNightMode ? 1 : 0, i);
    }

    public final void initPowerSave() {
        this.mPowerSave = this.mLocalPowerManager.getLowPowerState(16).batterySaverEnabled;
        this.mLocalPowerManager.registerLowPowerModeObserver(16, new Consumer() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UiModeManagerService.this.lambda$initPowerSave$2((PowerSaveState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPowerSave$2(PowerSaveState powerSaveState) {
        synchronized (this.mLock) {
            boolean z = this.mPowerSave;
            boolean z2 = powerSaveState.batterySaverEnabled;
            if (z == z2) {
                return;
            }
            this.mPowerSave = z2;
            if (this.mSystemReady) {
                updateLocked(0, 0);
            }
        }
    }

    public IUiModeManager getService() {
        return this.mService;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public final void verifySetupWizardCompleted() {
        Context context = getContext();
        int callingUserId = UserHandle.getCallingUserId();
        if (!setupWizardCompleteForCurrentUser()) {
            this.mSetupWizardComplete = false;
            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mSetupWizardObserver, callingUserId);
        } else {
            this.mSetupWizardComplete = true;
        }
    }

    public final boolean setupWizardCompleteForCurrentUser() {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "user_setup_complete", 0, UserHandle.getCallingUserId()) == 1;
    }

    public final void updateCustomTimeLocked() {
        if (this.mNightMode != 3) {
            return;
        }
        if (shouldApplyAutomaticChangesImmediately()) {
            updateLocked(0, 0);
        } else {
            registerScreenOffEventLocked();
        }
        scheduleNextCustomTimeListener();
    }

    public final void updateNightModeFromSettingsLocked(Context context, Resources resources, int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        this.mNightMode = Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode", resources.getInteger(R.integer.config_longPressOnPowerBehavior), i);
        this.mNightModeCustomType = Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_custom_type", -1, i);
        this.mOverrideNightModeOn = Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_override_on", 0, i) != 0;
        this.mOverrideNightModeOff = Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_override_off", 0, i) != 0;
        long longForUser = Settings.System.getLongForUser(context.getContentResolver(), "display_night_theme_on_time", getCustomTimeToMinute(this.DEFAULT_CUSTOM_NIGHT_START_TIME), i);
        long longForUser2 = Settings.System.getLongForUser(context.getContentResolver(), "display_night_theme_off_time", getCustomTimeToMinute(this.DEFAULT_CUSTOM_NIGHT_END_TIME), i);
        this.mCustomAutoNightModeStartMilliseconds = LocalTime.ofNanoOfDay(getCustomTimeToNano(longForUser));
        this.mCustomAutoNightModeEndMilliseconds = LocalTime.ofNanoOfDay(getCustomTimeToNano(longForUser2));
        if (this.mNightMode == 0) {
            this.mComputedNightMode = Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_last_computed", 0, i) != 0;
        }
        LogWrapper.i(TAG, "updateNightModeFromSettings : " + this.mNightMode + " userID : " + i);
        persistNightModeSettingDB(i);
    }

    public static long toMilliSeconds(LocalTime localTime) {
        return localTime.toNanoOfDay() / 1000;
    }

    public static LocalTime fromMilliseconds(long j) {
        return LocalTime.ofNanoOfDay(j * 1000);
    }

    public final void registerScreenOffEventLocked() {
        if (this.mPowerSave) {
            return;
        }
        this.mWaitForScreenOff = true;
        getContext().registerReceiver(this.mOnScreenOffHandler, new IntentFilter("android.intent.action.SCREEN_OFF"));
    }

    public final void cancelCustomAlarm() {
        this.mAlarmManager.cancel(this.mCustomTimeListener);
    }

    public final void unregisterScreenOffEventLocked() {
        this.mWaitForScreenOff = false;
        try {
            getContext().unregisterReceiver(this.mOnScreenOffHandler);
        } catch (IllegalArgumentException unused) {
        }
    }

    public final void registerTimeChangeEvent() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        getContext().registerReceiver(this.mOnTimeChangedHandler, intentFilter);
    }

    public final void unregisterTimeChangeEvent() {
        try {
            getContext().unregisterReceiver(this.mOnTimeChangedHandler);
        } catch (IllegalArgumentException unused) {
        }
    }

    /* renamed from: com.android.server.UiModeManagerService$15, reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass15 extends IUiModeManager.Stub {
        public AnonymousClass15() {
        }

        public void addCallback(IUiModeManagerCallback iUiModeManagerCallback) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (UiModeManagerService.this.mLock) {
                if (!UiModeManagerService.this.mUiModeManagerCallbacks.contains(callingUserId)) {
                    UiModeManagerService.this.mUiModeManagerCallbacks.put(callingUserId, new RemoteCallbackList());
                }
                ((RemoteCallbackList) UiModeManagerService.this.mUiModeManagerCallbacks.get(callingUserId)).register(iUiModeManagerCallback);
            }
        }

        public void enableCarMode(int i, int i2, String str) {
            if (isUiModeLocked()) {
                Slog.e(UiModeManagerService.TAG, "enableCarMode while UI mode is locked");
                return;
            }
            if (i2 != 0 && UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.ENTER_CAR_MODE_PRIORITIZED") != 0) {
                throw new SecurityException("Enabling car mode with a priority requires permission ENTER_CAR_MODE_PRIORITIZED");
            }
            if (!(UiModeManagerService.this.mInjector.getCallingUid() == 2000)) {
                UiModeManagerService.this.assertLegit(str);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.setCarModeLocked(true, i, i2, str);
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mSystemReady) {
                        uiModeManagerService.updateLocked(i, 0);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void disableCarMode(int i) {
            disableCarModeByCallingPackage(i, null);
        }

        public void disableCarModeByCallingPackage(int i, final String str) {
            if (isUiModeLocked()) {
                Slog.e(UiModeManagerService.TAG, "disableCarMode while UI mode is locked");
                return;
            }
            int callingUid = UiModeManagerService.this.mInjector.getCallingUid();
            boolean z = callingUid == 1000;
            boolean z2 = callingUid == 2000;
            if (!z && !z2) {
                UiModeManagerService.this.assertLegit(str);
            }
            int i2 = z ? i : i & (-3);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    UiModeManagerService.this.setCarModeLocked(false, i2, ((Integer) UiModeManagerService.this.mCarModePackagePriority.entrySet().stream().filter(new Predicate() { // from class: com.android.server.UiModeManagerService$15$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$disableCarModeByCallingPackage$0;
                            lambda$disableCarModeByCallingPackage$0 = UiModeManagerService.AnonymousClass15.lambda$disableCarModeByCallingPackage$0(str, (Map.Entry) obj);
                            return lambda$disableCarModeByCallingPackage$0;
                        }
                    }).findFirst().map(new UiModeManagerService$15$$ExternalSyntheticLambda1()).orElse(0)).intValue(), str);
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mSystemReady) {
                        uiModeManagerService.updateLocked(0, i);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public static /* synthetic */ boolean lambda$disableCarModeByCallingPackage$0(String str, Map.Entry entry) {
            return ((String) entry.getValue()).equals(str);
        }

        public int getCurrentModeType() {
            int i;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    i = UiModeManagerService.this.mCurUiMode & 15;
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNightMode(int i) {
            setNightModeInternal(i, i == 3 ? 0 : -1);
        }

        public final void setNightModeInternal(int i, int i2) {
            if (isNightModeLocked() && UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                Slog.e(UiModeManagerService.TAG, "Night mode locked, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            if (i != 0 && i != 1 && i != 2) {
                if (i == 3) {
                    if (!UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES.contains(Integer.valueOf(i2))) {
                        throw new IllegalArgumentException("Can't set the custom type to " + i2);
                    }
                } else {
                    throw new IllegalArgumentException("Unknown mode: " + i);
                }
            }
            if (i != 1 && !UiModeManagerService.this.canSetNightMode()) {
                Log.i(UiModeManagerService.TAG, "Ignore setNightMode : " + i);
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    if (UiModeManagerService.this.mNightMode != i || UiModeManagerService.this.mNightModeCustomType != i2) {
                        if (UiModeManagerService.this.mNightMode == 0 || UiModeManagerService.this.mNightMode == 3) {
                            UiModeManagerService.this.unregisterScreenOffEventLocked();
                            UiModeManagerService.this.cancelCustomAlarm();
                        }
                        UiModeManagerService.this.mNightModeCustomType = i == 3 ? i2 : -1;
                        UiModeManagerService.this.mNightMode = i;
                        UiModeManagerService.this.resetNightModeOverrideLocked();
                        UiModeManagerService.this.persistNightMode(-2);
                        LogWrapper.i(UiModeManagerService.TAG, "setNightMode : " + i + ", customModeType : " + i2 + ", by " + Binder.getCallingPid() + ", " + UiModeManagerService.this.getContext().getPackageManager().getNameForUid(Binder.getCallingUid()) + ", " + UiModeManagerService.this.getPackageName());
                        UiModeManagerService.this.persistNightModeSettingDB(-2);
                        if ((UiModeManagerService.this.mNightMode == 0 || UiModeManagerService.this.mNightMode == 3) && !UiModeManagerService.this.shouldApplyAutomaticChangesImmediately() && !UiModeManagerService.this.isWithInCustomScheduled() && !UiModeManagerService.this.mAutoModeChangeImmediately) {
                            UiModeManagerService.this.registerScreenOffEventLocked();
                        }
                        UiModeManagerService.this.unregisterScreenOffEventLocked();
                        UiModeManagerService.this.updateLocked(0, 0);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightMode() {
            int i;
            synchronized (UiModeManagerService.this.mLock) {
                i = UiModeManagerService.this.mNightMode;
            }
            return i;
        }

        public void setNightModeCustomType(int i) {
            if (UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                throw new SecurityException("setNightModeCustomType requires MODIFY_DAY_NIGHT_MODE permission");
            }
            setNightModeInternal(3, i);
        }

        public int getNightModeCustomType() {
            int i;
            if (UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                throw new SecurityException("getNightModeCustomType requires MODIFY_DAY_NIGHT_MODE permission");
            }
            synchronized (UiModeManagerService.this.mLock) {
                i = UiModeManagerService.this.mNightModeCustomType;
            }
            return i;
        }

        public void setApplicationNightMode(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException("Unknown mode: " + i);
            }
            int i2 = i != 1 ? i != 2 ? 0 : 32 : 16;
            ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater = UiModeManagerService.this.mActivityTaskManager.createPackageConfigurationUpdater();
            createPackageConfigurationUpdater.setNightMode(i2);
            createPackageConfigurationUpdater.commit();
        }

        public void setPackageNightMode(String str, int i, int i2) {
            if (UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                Slog.e(UiModeManagerService.TAG, "setPackageNightMode requires MODIFY_DAY_NIGHT_MODE permission");
            } else {
                UiModeManagerService.this.setPackageNightModeInner(str, i, i2);
            }
        }

        public int getPackageNightMode(String str, int i) {
            int i2;
            Integer num;
            if (!UiModeManagerService.this.isNightPriorityAllowed(str)) {
                Slog.e(UiModeManagerService.TAG, "getPackageNightMode is not allowed for " + str);
                return -1;
            }
            boolean isNightPriorityApplied = UiModeManagerService.this.isNightPriorityApplied(str, i);
            if (isNightPriorityApplied) {
                ActivityTaskManagerInternal.PackageConfig applicationConfig = UiModeManagerService.this.mActivityTaskManager.getApplicationConfig(str, i);
                if (applicationConfig == null || (num = applicationConfig.mNightMode) == null) {
                    Slog.e(UiModeManagerService.TAG, "getPackageNightMode cannot get config for " + str);
                    return -1;
                }
                i2 = num.intValue();
            } else {
                i2 = 2;
            }
            if (CoreRune.SAFE_DEBUG) {
                Slog.d(UiModeManagerService.TAG, "getPackageNightMode m=" + i2 + " p=" + str + "u=" + i + " applied=" + isNightPriorityApplied);
            }
            return i2;
        }

        public void setNightPriorityAllowedPackagesFromScpm(List list) {
            if (UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
                Slog.e(UiModeManagerService.TAG, "setNightPriorityAllowedPackagesFromScpm requires INTERACT_ACROSS_USERS_FULL permission");
                return;
            }
            if (list == null) {
                Slog.e(UiModeManagerService.TAG, "setNightPriorityAllowedPackagesFromScpm received invalid list");
                return;
            }
            synchronized (UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm) {
                Slog.d(UiModeManagerService.TAG, "setNightPriorityAllowedPackagesFromScpm " + list.size());
                UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm.clear();
                UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm.addAll(list);
            }
        }

        public List getNightPriorityAllowedPackagesFromScpm() {
            List list;
            synchronized (UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm) {
                list = UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm;
            }
            return list;
        }

        public void addNightPriorityAllowedPackageFromShell(String str) {
            if (!CoreRune.SAFE_DEBUG || TextUtils.isEmpty(str)) {
                return;
            }
            synchronized (UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm) {
                UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm.add(str);
            }
        }

        public void resetNightPriorityAppliedPackages(int i) {
            if (CoreRune.SAFE_DEBUG) {
                synchronized (UiModeManagerService.this.mNightPriorityAppliedPackages) {
                    if (UiModeManagerService.this.mNightPriorityAppliedPackages.getUserIds().contains(Integer.valueOf(i))) {
                        UiModeManagerService.this.mNightPriorityAppliedPackages.reset(i);
                    }
                }
            }
        }

        public boolean isUiModeLocked() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = UiModeManagerService.this.mUiModeLocked;
            }
            return z;
        }

        public boolean isNightModeLocked() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = UiModeManagerService.this.mNightModeLocked;
            }
            return z;
        }

        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new Shell(UiModeManagerService.this.mService).exec(UiModeManagerService.this.mService, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(UiModeManagerService.this.getContext(), UiModeManagerService.TAG, printWriter)) {
                UiModeManagerService.this.dumpImpl(printWriter);
            }
        }

        public boolean setNightModeActivatedForCustomMode(int i, boolean z) {
            return setNightModeActivatedForModeInternal(i, z);
        }

        public void setDesktopMode(boolean z) {
            synchronized (UiModeManagerService.this.mLock) {
                UiModeManagerService.this.mDesktopModeEnabled = z;
                UiModeManagerService.this.updateConfigurationLocked();
            }
        }

        public boolean setNightModeActivated(boolean z) {
            return setNightModeActivatedForModeInternal(UiModeManagerService.this.mNightModeCustomType, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x0121  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean setNightModeActivatedForModeInternal(int r11, boolean r12) {
            /*
                Method dump skipped, instructions count: 408
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.UiModeManagerService.AnonymousClass15.setNightModeActivatedForModeInternal(int, boolean):boolean");
        }

        public long getCustomNightModeStart() {
            return UiModeManagerService.this.mCustomAutoNightModeStartMilliseconds.toNanoOfDay() / 1000;
        }

        public void setCustomNightModeStart(long j) {
            LocalTime ofNanoOfDay;
            if (isNightModeLocked() && UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                Slog.e(UiModeManagerService.TAG, "Set custom time start, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = LocalTime.ofNanoOfDay(j * 1000);
                } catch (DateTimeException unused) {
                    UiModeManagerService.this.unregisterScreenOffEventLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                UiModeManagerService.this.mCustomAutoNightModeStartMilliseconds = ofNanoOfDay;
                UiModeManagerService.this.persistNightMode(callingUserId);
                UiModeManagerService.this.onCustomTimeUpdated(callingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long getCustomNightModeEnd() {
            return UiModeManagerService.this.mCustomAutoNightModeEndMilliseconds.toNanoOfDay() / 1000;
        }

        public void setCustomNightModeEnd(long j) {
            LocalTime ofNanoOfDay;
            if (isNightModeLocked() && UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                Slog.e(UiModeManagerService.TAG, "Set custom time end, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = LocalTime.ofNanoOfDay(j * 1000);
                } catch (DateTimeException unused) {
                    UiModeManagerService.this.unregisterScreenOffEventLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                UiModeManagerService.this.mCustomAutoNightModeEndMilliseconds = ofNanoOfDay;
                UiModeManagerService.this.onCustomTimeUpdated(callingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean requestProjection(IBinder iBinder, int i, String str) {
            UiModeManagerService.this.assertLegit(str);
            UiModeManagerService.assertSingleProjectionType(i);
            UiModeManagerService.this.enforceProjectionTypePermissions(i);
            synchronized (UiModeManagerService.this.mLock) {
                if (UiModeManagerService.this.mProjectionHolders == null) {
                    UiModeManagerService.this.mProjectionHolders = new SparseArray(1);
                }
                if (!UiModeManagerService.this.mProjectionHolders.contains(i)) {
                    UiModeManagerService.this.mProjectionHolders.put(i, new ArrayList(1));
                }
                List list = (List) UiModeManagerService.this.mProjectionHolders.get(i);
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (str.equals(((ProjectionHolder) list.get(i2)).mPackageName)) {
                        return true;
                    }
                }
                if (i == 1 && !list.isEmpty()) {
                    return false;
                }
                final UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                ProjectionHolder projectionHolder = new ProjectionHolder(str, i, iBinder, new ProjectionHolder.ProjectionReleaser() { // from class: com.android.server.UiModeManagerService$15$$ExternalSyntheticLambda2
                    @Override // com.android.server.UiModeManagerService.ProjectionHolder.ProjectionReleaser
                    public final boolean release(int i3, String str2) {
                        boolean releaseProjectionUnchecked;
                        releaseProjectionUnchecked = UiModeManagerService.this.releaseProjectionUnchecked(i3, str2);
                        return releaseProjectionUnchecked;
                    }
                });
                if (!projectionHolder.linkToDeath()) {
                    return false;
                }
                list.add(projectionHolder);
                Slog.d(UiModeManagerService.TAG, "Package " + str + " set projection type " + i + ".");
                UiModeManagerService.this.onProjectionStateChangedLocked(i);
                return true;
            }
        }

        public boolean releaseProjection(int i, String str) {
            UiModeManagerService.this.assertLegit(str);
            UiModeManagerService.assertSingleProjectionType(i);
            UiModeManagerService.this.enforceProjectionTypePermissions(i);
            return UiModeManagerService.this.releaseProjectionUnchecked(i, str);
        }

        public int getActiveProjectionTypes() {
            int i;
            UiModeManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.READ_PROJECTION_STATE", "getActiveProjectionTypes");
            synchronized (UiModeManagerService.this.mLock) {
                i = 0;
                if (UiModeManagerService.this.mProjectionHolders != null) {
                    int i2 = 0;
                    while (i < UiModeManagerService.this.mProjectionHolders.size()) {
                        if (!((List) UiModeManagerService.this.mProjectionHolders.valueAt(i)).isEmpty()) {
                            i2 |= UiModeManagerService.this.mProjectionHolders.keyAt(i);
                        }
                        i++;
                    }
                    i = i2;
                }
            }
            return i;
        }

        public List getProjectingPackages(int i) {
            ArrayList arrayList;
            UiModeManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.READ_PROJECTION_STATE", "getProjectionState");
            synchronized (UiModeManagerService.this.mLock) {
                arrayList = new ArrayList();
                UiModeManagerService.this.populateWithRelevantActivePackageNames(i, arrayList);
            }
            return arrayList;
        }

        public void addOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) {
            UiModeManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.READ_PROJECTION_STATE", "addOnProjectionStateChangedListener");
            if (i == 0) {
                return;
            }
            synchronized (UiModeManagerService.this.mLock) {
                if (UiModeManagerService.this.mProjectionListeners == null) {
                    UiModeManagerService.this.mProjectionListeners = new SparseArray(1);
                }
                if (!UiModeManagerService.this.mProjectionListeners.contains(i)) {
                    UiModeManagerService.this.mProjectionListeners.put(i, new RemoteCallbackList());
                }
                if (((RemoteCallbackList) UiModeManagerService.this.mProjectionListeners.get(i)).register(iOnProjectionStateChangedListener)) {
                    ArrayList arrayList = new ArrayList();
                    int populateWithRelevantActivePackageNames = UiModeManagerService.this.populateWithRelevantActivePackageNames(i, arrayList);
                    if (!arrayList.isEmpty()) {
                        try {
                            iOnProjectionStateChangedListener.onProjectionStateChanged(populateWithRelevantActivePackageNames, arrayList);
                        } catch (RemoteException unused) {
                            Slog.w(UiModeManagerService.TAG, "Failed a call to onProjectionStateChanged() during listener registration.");
                        }
                    }
                }
            }
        }

        public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener) {
            UiModeManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.READ_PROJECTION_STATE", "removeOnProjectionStateChangedListener");
            synchronized (UiModeManagerService.this.mLock) {
                if (UiModeManagerService.this.mProjectionListeners != null) {
                    for (int i = 0; i < UiModeManagerService.this.mProjectionListeners.size(); i++) {
                        ((RemoteCallbackList) UiModeManagerService.this.mProjectionListeners.valueAt(i)).unregister(iOnProjectionStateChangedListener);
                    }
                }
            }
        }

        public float getContrast() {
            float contrastLocked;
            synchronized (UiModeManagerService.this.mLock) {
                contrastLocked = UiModeManagerService.this.getContrastLocked();
            }
            return contrastLocked;
        }
    }

    public final void enforceProjectionTypePermissions(int i) {
        if ((i & 1) != 0) {
            getContext().enforceCallingPermission("android.permission.TOGGLE_AUTOMOTIVE_PROJECTION", "toggleProjection");
        }
    }

    public static void assertSingleProjectionType(int i) {
        boolean z = ((i + (-1)) & i) == 0;
        if (i == 0 || !z) {
            throw new IllegalArgumentException("Must specify exactly one projection type.");
        }
    }

    public static List toPackageNameList(Collection collection) {
        ArrayList arrayList = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((ProjectionHolder) it.next()).mPackageName);
        }
        return arrayList;
    }

    public final int populateWithRelevantActivePackageNames(int i, List list) {
        list.clear();
        if (this.mProjectionHolders == null) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mProjectionHolders.size(); i3++) {
            int keyAt = this.mProjectionHolders.keyAt(i3);
            List list2 = (List) this.mProjectionHolders.valueAt(i3);
            if ((i & keyAt) != 0 && list.addAll(toPackageNameList(list2))) {
                i2 |= keyAt;
            }
        }
        return i2;
    }

    public final boolean releaseProjectionUnchecked(int i, String str) {
        boolean z;
        List list;
        synchronized (this.mLock) {
            SparseArray sparseArray = this.mProjectionHolders;
            z = false;
            if (sparseArray != null && (list = (List) sparseArray.get(i)) != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    ProjectionHolder projectionHolder = (ProjectionHolder) list.get(size);
                    if (str.equals(projectionHolder.mPackageName)) {
                        projectionHolder.unlinkToDeath();
                        Slog.d(TAG, "Projection type " + i + " released by " + str + ".");
                        list.remove(size);
                        z = true;
                    }
                }
            }
            if (z) {
                onProjectionStateChangedLocked(i);
            } else {
                Slog.w(TAG, str + " tried to release projection type " + i + " but was not set by that package.");
            }
        }
        return z;
    }

    public final float getContrastLocked() {
        if (!this.mContrasts.contains(this.mCurrentUser)) {
            updateContrastLocked();
        }
        return ((Float) this.mContrasts.get(this.mCurrentUser)).floatValue();
    }

    public final boolean updateContrastLocked() {
        float floatForUser = Settings.Secure.getFloatForUser(getContext().getContentResolver(), "contrast_level", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, this.mCurrentUser);
        if (Math.abs(((Float) this.mContrasts.get(this.mCurrentUser, Float.valueOf(Float.MAX_VALUE))).floatValue() - floatForUser) < 1.0E-10d) {
            return false;
        }
        this.mContrasts.put(this.mCurrentUser, Float.valueOf(floatForUser));
        return true;
    }

    /* loaded from: classes.dex */
    public class ProjectionHolder implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final String mPackageName;
        public final ProjectionReleaser mProjectionReleaser;
        public final int mProjectionType;

        /* loaded from: classes.dex */
        public interface ProjectionReleaser {
            boolean release(int i, String str);
        }

        public ProjectionHolder(String str, int i, IBinder iBinder, ProjectionReleaser projectionReleaser) {
            this.mPackageName = str;
            this.mProjectionType = i;
            this.mBinder = iBinder;
            this.mProjectionReleaser = projectionReleaser;
        }

        public final boolean linkToDeath() {
            try {
                this.mBinder.linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                Slog.e(UiModeManagerService.TAG, "linkToDeath failed for projection requester: " + this.mPackageName + ".", e);
                return false;
            }
        }

        public final void unlinkToDeath() {
            this.mBinder.unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Slog.w(UiModeManagerService.TAG, "Projection holder " + this.mPackageName + " died. Releasing projection type " + this.mProjectionType + ".");
            this.mProjectionReleaser.release(this.mProjectionType, this.mPackageName);
        }
    }

    public final void assertLegit(String str) {
        if (doesPackageHaveCallingUid(str)) {
            return;
        }
        throw new SecurityException("Caller claimed bogus packageName: " + str + ".");
    }

    public final boolean doesPackageHaveCallingUid(String str) {
        int callingUid = this.mInjector.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getContext().getPackageManager().getPackageUidAsUser(str, userId) == callingUid;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onProjectionStateChangedLocked(int i) {
        if (this.mProjectionListeners == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mProjectionListeners.size(); i2++) {
            int keyAt = this.mProjectionListeners.keyAt(i2);
            if ((i & keyAt) != 0) {
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mProjectionListeners.valueAt(i2);
                ArrayList arrayList = new ArrayList();
                int populateWithRelevantActivePackageNames = populateWithRelevantActivePackageNames(keyAt, arrayList);
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        remoteCallbackList.getBroadcastItem(i3).onProjectionStateChanged(populateWithRelevantActivePackageNames, arrayList);
                    } catch (RemoteException unused) {
                        Slog.w(TAG, "Failed a call to onProjectionStateChanged().");
                    }
                }
                remoteCallbackList.finishBroadcast();
            }
        }
    }

    public final void onCustomTimeUpdated(int i) {
        persistNightMode(i);
        if (this.mNightMode != 3) {
            return;
        }
        if (shouldApplyAutomaticChangesImmediately() || isWithInCustomScheduled()) {
            unregisterScreenOffEventLocked();
            updateLocked(0, 0);
        } else {
            registerScreenOffEventLocked();
        }
    }

    public void dumpImpl(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("Current UI Mode Service state:");
            printWriter.print("  mDockState=");
            printWriter.print(this.mDockState);
            printWriter.print(" mLastBroadcastState=");
            printWriter.println(this.mLastBroadcastState);
            printWriter.print(" mStartDreamImmediatelyOnDock=");
            printWriter.print(this.mStartDreamImmediatelyOnDock);
            printWriter.print("  mNightMode=");
            printWriter.print(this.mNightMode);
            printWriter.print(" (");
            printWriter.print(Shell.nightModeToStr(this.mNightMode, this.mNightModeCustomType));
            printWriter.print(") ");
            printWriter.print(" mOverrideOn/Off=");
            printWriter.print(this.mOverrideNightModeOn);
            printWriter.print("/");
            printWriter.print(this.mOverrideNightModeOff);
            printWriter.print(" mNightModeLocked=");
            printWriter.println(this.mNightModeLocked);
            printWriter.print("  mCarModeEnabled=");
            printWriter.print(this.mCarModeEnabled);
            printWriter.print(" (carModeApps=");
            for (Map.Entry entry : this.mCarModePackagePriority.entrySet()) {
                printWriter.print(entry.getKey());
                printWriter.print(XmlUtils.STRING_ARRAY_SEPARATOR);
                printWriter.print((String) entry.getValue());
                printWriter.print(" ");
            }
            printWriter.println("");
            printWriter.print(" waitScreenOff=");
            printWriter.print(this.mWaitForScreenOff);
            printWriter.print(" mComputedNightMode=");
            printWriter.print(this.mComputedNightMode);
            printWriter.print(" customStart=");
            printWriter.print(this.mCustomAutoNightModeStartMilliseconds);
            printWriter.print(" customEnd");
            printWriter.print(this.mCustomAutoNightModeEndMilliseconds);
            printWriter.print(" mCarModeEnableFlags=");
            printWriter.print(this.mCarModeEnableFlags);
            printWriter.print(" mEnableCarDockLaunch=");
            printWriter.println(this.mEnableCarDockLaunch);
            printWriter.print("  mCurUiMode=0x");
            printWriter.print(Integer.toHexString(this.mCurUiMode));
            printWriter.print(" mUiModeLocked=");
            printWriter.print(this.mUiModeLocked);
            printWriter.print(" mSetUiMode=0x");
            printWriter.println(Integer.toHexString(this.mSetUiMode));
            printWriter.print("  mHoldingConfiguration=");
            printWriter.print(this.mHoldingConfiguration);
            printWriter.print(" mSystemReady=");
            printWriter.println(this.mSystemReady);
            if (this.mTwilightManager != null) {
                printWriter.print("  mTwilightService.getLastTwilightState()=");
                printWriter.println(this.mTwilightManager.getLastTwilightState());
            }
            printWriter.println(buildLogString('V', TAG, "SavedLogsStart"));
            printWriter.println(LogWrapper.getLogText());
            printWriter.print("  mNightPriorityAllowedPackagesFromScpm=");
            printWriter.println(this.mNightPriorityAllowedPackagesFromScpm);
            this.mNightPriorityAppliedPackages.dump(printWriter, "Applied packages", "    ");
            this.mPackagesNeedToShowDialog.dump(printWriter, "Show dialog packages", "    ");
            printWriter.print("  mDesktopModeEnabled=");
            printWriter.println(this.mDesktopModeEnabled);
        }
    }

    public void setCarModeLocked(boolean z, int i, int i2, String str) {
        if (z) {
            enableCarMode(i2, str);
        } else {
            disableCarMode(i, i2, str);
        }
        boolean isCarModeEnabled = isCarModeEnabled();
        if (this.mCarModeEnabled != isCarModeEnabled) {
            this.mCarModeEnabled = isCarModeEnabled;
            if (!isCarModeEnabled) {
                Context context = getContext();
                updateNightModeFromSettingsLocked(context, context.getResources(), UserHandle.getCallingUserId());
            }
        }
        this.mCarModeEnableFlags = i;
    }

    public final void disableCarMode(int i, int i2, String str) {
        boolean z = true;
        boolean z2 = (i & 2) != 0;
        boolean contains = this.mCarModePackagePriority.keySet().contains(Integer.valueOf(i2));
        if (!(i2 == 0) && ((!contains || !((String) this.mCarModePackagePriority.get(Integer.valueOf(i2))).equals(str)) && !z2)) {
            z = false;
        }
        if (z) {
            Slog.d(TAG, "disableCarMode: disabling, priority=" + i2 + ", packageName=" + str);
            if (z2) {
                ArraySet<Map.Entry> arraySet = new ArraySet(this.mCarModePackagePriority.entrySet());
                this.mCarModePackagePriority.clear();
                for (Map.Entry entry : arraySet) {
                    notifyCarModeDisabled(((Integer) entry.getKey()).intValue(), (String) entry.getValue());
                }
                return;
            }
            this.mCarModePackagePriority.remove(Integer.valueOf(i2));
            notifyCarModeDisabled(i2, str);
        }
    }

    public final void enableCarMode(int i, String str) {
        boolean containsKey = this.mCarModePackagePriority.containsKey(Integer.valueOf(i));
        boolean containsValue = this.mCarModePackagePriority.containsValue(str);
        if (!containsKey && !containsValue) {
            Slog.d(TAG, "enableCarMode: enabled at priority=" + i + ", packageName=" + str);
            this.mCarModePackagePriority.put(Integer.valueOf(i), str);
            notifyCarModeEnabled(i, str);
            return;
        }
        Slog.d(TAG, "enableCarMode: car mode at priority " + i + " already enabled.");
    }

    public final void notifyCarModeEnabled(int i, String str) {
        Intent intent = new Intent("android.app.action.ENTER_CAR_MODE_PRIORITIZED");
        intent.putExtra("android.app.extra.CALLING_PACKAGE", str);
        intent.putExtra("android.app.extra.PRIORITY", i);
        getContext().sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.HANDLE_CAR_MODE_CHANGES");
    }

    public final void notifyCarModeDisabled(int i, String str) {
        Intent intent = new Intent("android.app.action.EXIT_CAR_MODE_PRIORITIZED");
        intent.putExtra("android.app.extra.CALLING_PACKAGE", str);
        intent.putExtra("android.app.extra.PRIORITY", i);
        getContext().sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.HANDLE_CAR_MODE_CHANGES");
    }

    public final boolean isCarModeEnabled() {
        return this.mCarModePackagePriority.size() > 0;
    }

    public final void updateDockState(int i) {
        synchronized (this.mLock) {
            if (i != this.mDockState) {
                this.mDockState = i;
                setCarModeLocked(i == 2, 0, 0, "");
                if (this.mSystemReady) {
                    updateLocked(1, 0);
                }
            }
        }
    }

    public final void persistNightMode(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode", this.mNightMode, i);
        Settings.Secure.putLongForUser(getContext().getContentResolver(), "ui_night_mode_custom_type", this.mNightModeCustomType, i);
        Settings.System.putLongForUser(getContext().getContentResolver(), "display_night_theme_on_time", getCustomTimeToMinute(this.mCustomAutoNightModeStartMilliseconds), i);
        Settings.System.putLongForUser(getContext().getContentResolver(), "display_night_theme_off_time", getCustomTimeToMinute(this.mCustomAutoNightModeEndMilliseconds), i);
    }

    public final void persistNightModeOverrides(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_override_on", this.mOverrideNightModeOn ? 1 : 0, i);
        Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_override_off", this.mOverrideNightModeOff ? 1 : 0, i);
    }

    public final void updateConfigurationLocked() {
        int i = this.mDefaultUiModeType;
        if (!this.mUiModeLocked) {
            if (!this.mDesktopModeEnabled && (!CoreRune.MT_NEW_DEX || !this.mNewDexModeEnabled)) {
                if (this.mTelevision) {
                    i = 4;
                } else if (this.mWatch) {
                    i = 6;
                } else if (this.mCarModeEnabled) {
                    i = 3;
                } else if (!isDeskDockState(this.mDockState)) {
                    if (this.mVrHeadset) {
                        i = 7;
                    }
                }
            }
            i = 2;
        }
        int i2 = this.mNightMode;
        if (i2 == 2 || i2 == 1) {
            updateComputedNightModeLocked(i2 == 2);
        }
        if (this.mNightMode == 0) {
            boolean z = this.mComputedNightMode;
            TwilightManager twilightManager = this.mTwilightManager;
            if (twilightManager != null) {
                twilightManager.registerListener(this.mTwilightListener, this.mHandler);
                TwilightState lastTwilightState = this.mTwilightManager.getLastTwilightState();
                z = lastTwilightState == null ? this.mComputedNightMode : lastTwilightState.isNight();
            }
            updateComputedNightModeLocked(z);
            if (z != this.mComputedNightMode) {
                this.mWaitForScreenOff = false;
            }
            updateThemeImmediately();
            unregisterScreenOffEventLocked();
        } else {
            TwilightManager twilightManager2 = this.mTwilightManager;
            if (twilightManager2 != null) {
                twilightManager2.unregisterListener(this.mTwilightListener);
            }
        }
        if (this.mNightMode == 3) {
            if (this.mNightModeCustomType == 1) {
                updateComputedNightModeLocked(this.mLastBedtimeRequestedNightMode);
            } else {
                registerTimeChangeEvent();
                updateComputedNightModeLocked(computeCustomNightMode());
                scheduleNextCustomTimeListener();
                updateThemeImmediately();
            }
        } else {
            unregisterTimeChangeEvent();
        }
        int computedUiModeConfiguration = (!this.mPowerSave || this.mCarModeEnabled || this.mCar) ? getComputedUiModeConfiguration(i) : (i & (-17)) | 32;
        this.mCurUiMode = computedUiModeConfiguration;
        if (this.mHoldingConfiguration) {
            return;
        }
        if (!this.mWaitForScreenOff || this.mPowerSave) {
            this.mConfiguration.uiMode = computedUiModeConfiguration;
        }
    }

    public final int getComputedUiModeConfiguration(int i) {
        boolean z = this.mComputedNightMode;
        return (z ? -17 : -33) & (i | (z ? 32 : 16));
    }

    public final boolean computeCustomNightMode() {
        return TimeUtils.isTimeBetween(LocalTime.now(), this.mCustomAutoNightModeStartMilliseconds, this.mCustomAutoNightModeEndMilliseconds);
    }

    public final void applyConfigurationExternallyLocked() {
        int i = this.mSetUiMode;
        int i2 = this.mConfiguration.uiMode;
        if (i != i2) {
            this.mSetUiMode = i2;
            this.mWindowManager.clearSnapshotCache();
            try {
                ActivityTaskManager.getService().updateConfiguration(this.mConfiguration);
            } catch (RemoteException e) {
                Slog.w(TAG, "Failure communicating with activity manager", e);
            } catch (SecurityException e2) {
                Slog.e(TAG, "Activity does not have the ", e2);
            }
        }
    }

    public final boolean shouldApplyAutomaticChangesImmediately() {
        return this.mCar || !this.mPowerManager.isInteractive() || this.mNightModeCustomType == 1;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [java.time.ZonedDateTime] */
    public final void scheduleNextCustomTimeListener() {
        LocalDateTime dateTimeAfter;
        cancelCustomAlarm();
        LocalDateTime now = LocalDateTime.now();
        if (computeCustomNightMode()) {
            dateTimeAfter = getDateTimeAfter(this.mCustomAutoNightModeEndMilliseconds, now);
        } else {
            dateTimeAfter = getDateTimeAfter(this.mCustomAutoNightModeStartMilliseconds, now);
        }
        this.mAlarmManager.setExact(1, dateTimeAfter.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), TAG, this.mCustomTimeListener, null);
    }

    public final LocalDateTime getDateTimeAfter(LocalTime localTime, LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.toLocalDate(), localTime);
        return of.isBefore(localDateTime) ? of.plusDays(1L) : of;
    }

    public void updateLocked(int i, int i2) {
        String str;
        int i3 = this.mLastBroadcastState;
        String str2 = null;
        if (i3 == 2) {
            adjustStatusBarCarModeLocked();
            str = UiModeManager.ACTION_EXIT_CAR_MODE;
        } else {
            str = isDeskDockState(i3) ? UiModeManager.ACTION_EXIT_DESK_MODE : null;
        }
        boolean z = false;
        if (this.mCarModeEnabled) {
            if (this.mLastBroadcastState != 2) {
                adjustStatusBarCarModeLocked();
                if (str != null) {
                    sendForegroundBroadcastToAllUsers(str);
                }
                this.mLastBroadcastState = 2;
                str = UiModeManager.ACTION_ENTER_CAR_MODE;
            }
            str = null;
        } else if (isDeskDockState(this.mDockState)) {
            if (!isDeskDockState(this.mLastBroadcastState)) {
                if (str != null) {
                    sendForegroundBroadcastToAllUsers(str);
                }
                this.mLastBroadcastState = this.mDockState;
                str = UiModeManager.ACTION_ENTER_DESK_MODE;
            }
            str = null;
        } else {
            this.mLastBroadcastState = 0;
        }
        if (str != null) {
            Intent intent = new Intent(str);
            intent.putExtra("enableFlags", i);
            intent.putExtra("disableFlags", i2);
            intent.addFlags(268435456);
            getContext().sendOrderedBroadcastAsUser(intent, UserHandle.CURRENT, null, this.mResultReceiver, null, -1, null, null);
            this.mHoldingConfiguration = true;
            updateConfigurationLocked();
        } else {
            if (this.mCarModeEnabled) {
                if (this.mEnableCarDockLaunch && (i & 1) != 0) {
                    str2 = "android.intent.category.CAR_DOCK";
                }
            } else if (isDeskDockState(this.mDockState)) {
                if ((i & 1) != 0) {
                    str2 = "android.intent.category.DESK_DOCK";
                }
            } else if ((i2 & 1) != 0) {
                str2 = "android.intent.category.HOME";
            }
            sendConfigurationAndStartDreamOrDockAppLocked(str2);
        }
        if (this.mCharging && ((this.mCarModeEnabled && this.mCarModeKeepsScreenOn && (this.mCarModeEnableFlags & 2) == 0) || (this.mCurUiMode == 2 && this.mDeskModeKeepsScreenOn))) {
            z = true;
        }
        if (z != this.mWakeLock.isHeld()) {
            if (z) {
                this.mWakeLock.acquire();
            } else {
                this.mWakeLock.release();
            }
        }
    }

    public final void sendForegroundBroadcastToAllUsers(String str) {
        getContext().sendBroadcastAsUser(new Intent(str).addFlags(268435456), UserHandle.ALL);
    }

    public final void updateAfterBroadcastLocked(String str, int i, int i2) {
        String str2;
        if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(str)) {
            if (this.mEnableCarDockLaunch && (i & 1) != 0) {
                str2 = "android.intent.category.CAR_DOCK";
            }
            str2 = null;
        } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(str)) {
            if ((i & 1) != 0) {
                str2 = "android.intent.category.DESK_DOCK";
            }
            str2 = null;
        } else {
            if ((i2 & 1) != 0) {
                str2 = "android.intent.category.HOME";
            }
            str2 = null;
        }
        sendConfigurationAndStartDreamOrDockAppLocked(str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0098 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendConfigurationAndStartDreamOrDockAppLocked(java.lang.String r19) {
        /*
            r18 = this;
            r1 = r18
            java.lang.String r2 = "Could not start dock app: "
            r3 = 0
            r1.mHoldingConfiguration = r3
            r18.updateConfigurationLocked()
            r4 = 1
            if (r19 == 0) goto L83
            android.content.Intent r15 = buildHomeIntent(r19)
            android.content.Context r0 = r18.getContext()
            boolean r0 = android.service.dreams.Sandman.shouldStartDockApp(r0, r15)
            if (r0 == 0) goto L83
            android.app.IActivityTaskManager r5 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> L6d
            r6 = 0
            android.content.Context r0 = r18.getContext()     // Catch: android.os.RemoteException -> L6d
            java.lang.String r7 = r0.getBasePackageName()     // Catch: android.os.RemoteException -> L6d
            android.content.Context r0 = r18.getContext()     // Catch: android.os.RemoteException -> L6d
            java.lang.String r8 = r0.getAttributionTag()     // Catch: android.os.RemoteException -> L6d
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            android.content.res.Configuration r0 = r1.mConfiguration     // Catch: android.os.RemoteException -> L6d
            r16 = 0
            r17 = -2
            r9 = r15
            r3 = r15
            r15 = r0
            int r0 = r5.startActivityWithConfig(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)     // Catch: android.os.RemoteException -> L6b
            boolean r5 = android.app.ActivityManager.isStartResultSuccessful(r0)     // Catch: android.os.RemoteException -> L6b
            if (r5 == 0) goto L4a
            r0 = r4
            goto L84
        L4a:
            r5 = -91
            if (r0 == r5) goto L83
            java.lang.String r5 = com.android.server.UiModeManagerService.TAG     // Catch: android.os.RemoteException -> L6b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L6b
            r6.<init>()     // Catch: android.os.RemoteException -> L6b
            r6.append(r2)     // Catch: android.os.RemoteException -> L6b
            r6.append(r3)     // Catch: android.os.RemoteException -> L6b
            java.lang.String r7 = ", startActivityWithConfig result "
            r6.append(r7)     // Catch: android.os.RemoteException -> L6b
            r6.append(r0)     // Catch: android.os.RemoteException -> L6b
            java.lang.String r0 = r6.toString()     // Catch: android.os.RemoteException -> L6b
            android.util.Slog.e(r5, r0)     // Catch: android.os.RemoteException -> L6b
            goto L83
        L6b:
            r0 = move-exception
            goto L6f
        L6d:
            r0 = move-exception
            r3 = r15
        L6f:
            java.lang.String r5 = com.android.server.UiModeManagerService.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            r6.append(r3)
            java.lang.String r2 = r6.toString()
            android.util.Slog.e(r5, r2, r0)
        L83:
            r0 = 0
        L84:
            r18.applyConfigurationExternallyLocked()
            boolean r2 = r1.mDreamsDisabledByAmbientModeSuppression
            if (r2 == 0) goto L95
            android.os.PowerManagerInternal r2 = r1.mLocalPowerManager
            boolean r2 = r2.isAmbientDisplaySuppressed()
            if (r2 == 0) goto L95
            r3 = r4
            goto L96
        L95:
            r3 = 0
        L96:
            if (r19 == 0) goto Lb9
            if (r0 != 0) goto Lb9
            if (r3 != 0) goto Lb9
            boolean r0 = r1.mStartDreamImmediatelyOnDock
            if (r0 != 0) goto Lb0
            com.android.server.wm.WindowManagerInternal r0 = r1.mWindowManager
            boolean r0 = r0.isKeyguardShowingAndNotOccluded()
            if (r0 != 0) goto Lb0
            android.os.PowerManager r0 = r1.mPowerManager
            boolean r0 = r0.isInteractive()
            if (r0 != 0) goto Lb9
        Lb0:
            com.android.server.UiModeManagerService$Injector r0 = r1.mInjector
            android.content.Context r1 = r18.getContext()
            r0.startDreamWhenDockedIfAppropriate(r1)
        Lb9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.UiModeManagerService.sendConfigurationAndStartDreamOrDockAppLocked(java.lang.String):void");
    }

    public final void adjustStatusBarCarModeLocked() {
        Context context = getContext();
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        StatusBarManager statusBarManager = this.mStatusBarManager;
        if (statusBarManager != null) {
            statusBarManager.disable(this.mCarModeEnabled ? 524288 : 0);
        }
        if (this.mNotificationManager == null) {
            this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
        }
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager != null) {
            if (this.mCarModeEnabled) {
                this.mNotificationManager.notifyAsUser(null, 10, new Notification.Builder(context, SystemNotificationChannels.CAR_MODE).setSmallIcon(17304204).setDefaults(4).setOngoing(true).setWhen(0L).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(context.getString(R.string.data_usage_rapid_title)).setContentText(context.getString(R.string.data_usage_rapid_body)).setContentIntent(PendingIntent.getActivityAsUser(context, 0, new Intent(context, (Class<?>) DisableCarModeActivity.class), 33554432, null, UserHandle.CURRENT)).build(), UserHandle.ALL);
            } else {
                notificationManager.cancelAsUser(null, 10, UserHandle.ALL);
            }
        }
    }

    public final void updateComputedNightModeLocked(boolean z) {
        TwilightManager twilightManager;
        this.mComputedNightMode = z;
        int i = this.mNightMode;
        if (i == 2 || i == 1) {
            return;
        }
        if (this.mOverrideNightModeOn && !z) {
            this.mComputedNightMode = true;
            return;
        }
        if (this.mOverrideNightModeOff && z) {
            this.mComputedNightMode = false;
        } else {
            if (i == 0 && ((twilightManager = this.mTwilightManager) == null || twilightManager.getLastTwilightState() == null)) {
                return;
            }
            resetNightModeOverrideLocked();
        }
    }

    public final boolean resetNightModeOverrideLocked() {
        if (!this.mOverrideNightModeOff && !this.mOverrideNightModeOn) {
            return false;
        }
        this.mOverrideNightModeOff = false;
        this.mOverrideNightModeOn = false;
        persistNightModeOverrides(this.mOverrideNightModeUser);
        this.mOverrideNightModeUser = 0;
        return true;
    }

    public final void registerVrStateListener() {
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(this.mVrStateCallbacks);
            } catch (RemoteException e) {
                Slog.e(TAG, "Failed to register VR mode state listener: " + e);
            }
        }
    }

    public final boolean canSetNightMode() {
        String string = Settings.System.getString(getContext().getContentResolver(), "current_sec_active_themepackage");
        if (string != null && !string.isEmpty()) {
            r1 = Settings.System.getInt(getContext().getContentResolver(), "current_theme_support_night_mode", 0) == 1;
            if (!r1) {
                Log.i(TAG, "Cannot set night mode because current theme does not support night mode");
            }
        }
        return r1;
    }

    public final void persistNightModeSettingDB(int i) {
        int i2 = this.mNightMode;
        if (i2 == 0) {
            if (Settings.System.getIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 0, i) != 1) {
                Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 1, i);
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.UiModeManagerService.16
                @Override // java.lang.Runnable
                public void run() {
                    UiModeManagerService.this.mAutoModeChangeImmediately = false;
                }
            }, 500L);
            this.mAutoModeChangeImmediately = true;
            return;
        }
        if (i2 == 1) {
            Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme", 0, i);
            Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 0, i);
            this.mComputedNightMode = false;
        } else if (i2 == 2) {
            Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme", 1, i);
            Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 0, i);
            this.mComputedNightMode = false;
        } else if (i2 == 3 && Settings.System.getIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 0, i) != 1) {
            Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme_scheduled", 1, i);
        }
    }

    public final void updateThemeImmediately() {
        boolean isSetDarkTheme = isSetDarkTheme();
        boolean z = this.mComputedNightMode;
        if (isSetDarkTheme == z) {
            return;
        }
        if (!z && !isMinimalBatteryUseEnabled(getContext())) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.UiModeManagerService.17
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(new ContextThemeWrapper(UiModeManagerService.this.getContext(), R.style.Theme.DeviceDefault.Light), UiModeManagerService.this.getContext().getString(17042667), 1).show();
                }
            }, 500L);
        }
        Settings.System.putIntForUser(getContext().getContentResolver(), "display_night_theme", this.mComputedNightMode ? 1 : 0, -2);
    }

    public final boolean isSetDarkTheme() {
        return Settings.System.getIntForUser(getContext().getContentResolver(), "display_night_theme", 0, -2) == 1;
    }

    public final boolean isMinimalBatteryUseEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 1;
    }

    public final boolean isWithInCustomScheduled() {
        if (this.mNightMode == 3) {
            if (this.mComputedNightMode ^ computeCustomNightMode()) {
                return true;
            }
        }
        return false;
    }

    public final long getCustomTimeToMinute(LocalTime localTime) {
        return (localTime.getHour() * 60) + localTime.getMinute();
    }

    public final String getPackageName() {
        try {
            return ActivityManager.getService().getPackageFromAppProcesses(Binder.getCallingPid());
        } catch (Exception e) {
            Log.e(TAG, "Exception in getPackageNameFromAppProcesses : " + e);
            return "none";
        }
    }

    public static String buildLogString(char c, String str, String str2) {
        return LocalDateTime.now().format(sFormatter) + " " + adjustTab(Process.myUid()) + " " + adjustTab(Process.myPid()) + " " + adjustTab(Process.myTid()) + " " + c + " " + str + ": " + str2;
    }

    public static String adjustTab(int i) {
        String valueOf = String.valueOf(i);
        if (valueOf.length() > 4) {
            return valueOf;
        }
        return " " + valueOf;
    }

    public final boolean isNightPriorityAllowed(String str) {
        boolean contains;
        synchronized (this.mNightPriorityAllowedPackagesFromScpm) {
            contains = this.mNightPriorityAllowedPackagesFromScpm.contains(str);
        }
        return contains;
    }

    public final boolean isNightPriorityApplied(String str, int i) {
        boolean z;
        synchronized (this.mNightPriorityAppliedPackages) {
            z = this.mNightPriorityAppliedPackages.getValue(i, str) != null;
        }
        return z;
    }

    public final boolean setPackageNightModeInner(String str, int i, int i2) {
        if (i2 != 0 && i2 != 2) {
            Slog.e(TAG, "setPackageNightModeInner received unsupported mode: " + i2);
            return false;
        }
        if (!isNightPriorityAllowed(str)) {
            Slog.e(TAG, "setPackageNightModeInner is not allowed for " + str);
            return false;
        }
        synchronized (this.mNightPriorityAppliedPackages) {
            Slog.d(TAG, "setPackageNightModeInner m=" + i2 + " p=" + str + " u=" + i + " by " + Binder.getCallingUid());
            PackageFeatureUserChange packageFeatureUserChange = this.mNightPriorityAppliedPackages;
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append("/");
            sb.append(Binder.getCallingUid());
            packageFeatureUserChange.putValue(i, str, sb.toString());
        }
        ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater = this.mActivityTaskManager.createPackageConfigurationUpdater(str, i);
        createPackageConfigurationUpdater.setNightMode(i2 == 2 ? 32 : 0);
        createPackageConfigurationUpdater.commit();
        return true;
    }

    /* loaded from: classes.dex */
    public class Shell extends ShellCommand {
        public final IUiModeManager mInterface;

        public static String nightModeToStr(int i, int i2) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "unknown" : i2 == 0 ? "custom_schedule" : i2 == 1 ? "custom_bedtime" : "unknown" : "yes" : "no" : "auto";
        }

        public Shell(IUiModeManager iUiModeManager) {
            this.mInterface = iUiModeManager;
        }

        public void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("UiModeManager service (uimode) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  night [yes|no|auto|custom_schedule|custom_bedtime]");
            outPrintWriter.println("    Set or read night mode.");
            outPrintWriter.println("  car [yes|no]");
            outPrintWriter.println("    Set or read car mode.");
            outPrintWriter.println("  time [start|end] <ISO time>");
            outPrintWriter.println("    Set custom start/end schedule time (night mode must be set to custom to apply).");
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0054 A[Catch: RemoteException -> 0x0059, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0059, blocks: (B:7:0x0008, B:18:0x0045, B:20:0x004a, B:22:0x004f, B:24:0x0054, B:26:0x001e, B:29:0x0029, B:32:0x0034), top: B:6:0x0008 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int onCommand(java.lang.String r6) {
            /*
                r5 = this;
                if (r6 != 0) goto L7
                int r5 = r5.handleDefaultCommands(r6)
                return r5
            L7:
                r0 = -1
                int r1 = r6.hashCode()     // Catch: android.os.RemoteException -> L59
                r2 = 98260(0x17fd4, float:1.37692E-40)
                r3 = 2
                r4 = 1
                if (r1 == r2) goto L34
                r2 = 3560141(0x3652cd, float:4.98882E-39)
                if (r1 == r2) goto L29
                r2 = 104817688(0x63f6418, float:3.5996645E-35)
                if (r1 == r2) goto L1e
                goto L3e
            L1e:
                java.lang.String r1 = "night"
                boolean r1 = r6.equals(r1)     // Catch: android.os.RemoteException -> L59
                if (r1 == 0) goto L3e
                r1 = 0
                goto L3f
            L29:
                java.lang.String r1 = "time"
                boolean r1 = r6.equals(r1)     // Catch: android.os.RemoteException -> L59
                if (r1 == 0) goto L3e
                r1 = r3
                goto L3f
            L34:
                java.lang.String r1 = "car"
                boolean r1 = r6.equals(r1)     // Catch: android.os.RemoteException -> L59
                if (r1 == 0) goto L3e
                r1 = r4
                goto L3f
            L3e:
                r1 = r0
            L3f:
                if (r1 == 0) goto L54
                if (r1 == r4) goto L4f
                if (r1 == r3) goto L4a
                int r5 = r5.handleDefaultCommands(r6)     // Catch: android.os.RemoteException -> L59
                return r5
            L4a:
                int r5 = r5.handleCustomTime()     // Catch: android.os.RemoteException -> L59
                return r5
            L4f:
                int r5 = r5.handleCarMode()     // Catch: android.os.RemoteException -> L59
                return r5
            L54:
                int r5 = r5.handleNightMode()     // Catch: android.os.RemoteException -> L59
                return r5
            L59:
                r6 = move-exception
                java.io.PrintWriter r5 = r5.getErrPrintWriter()
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Remote exception: "
                r1.append(r2)
                r1.append(r6)
                java.lang.String r6 = r1.toString()
                r5.println(r6)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.UiModeManagerService.Shell.onCommand(java.lang.String):int");
        }

        public final int handleCustomTime() {
            String nextArg = getNextArg();
            if (nextArg == null) {
                printCustomTime();
                return 0;
            }
            if (nextArg.equals("end")) {
                this.mInterface.setCustomNightModeEnd(UiModeManagerService.toMilliSeconds(LocalTime.parse(getNextArg())));
                return 0;
            }
            if (nextArg.equals("start")) {
                this.mInterface.setCustomNightModeStart(UiModeManagerService.toMilliSeconds(LocalTime.parse(getNextArg())));
                return 0;
            }
            getErrPrintWriter().println("command must be in [start|end]");
            return -1;
        }

        public final void printCustomTime() {
            getOutPrintWriter().println("start " + UiModeManagerService.fromMilliseconds(this.mInterface.getCustomNightModeStart()).toString());
            getOutPrintWriter().println("end " + UiModeManagerService.fromMilliseconds(this.mInterface.getCustomNightModeEnd()).toString());
        }

        public final int handleNightMode() {
            PrintWriter errPrintWriter = getErrPrintWriter();
            String nextArg = getNextArg();
            if (nextArg == null) {
                printCurrentNightMode();
                return 0;
            }
            int strToNightMode = strToNightMode(nextArg);
            int strToNightModeCustomType = strToNightModeCustomType(nextArg);
            if (strToNightMode >= 0) {
                String nextArg2 = getNextArg();
                if (CoreRune.SAFE_DEBUG && !TextUtils.isEmpty(nextArg2)) {
                    this.mInterface.setPackageNightMode(nextArg2, ActivityManager.getCurrentUser(), strToNightMode);
                    return 0;
                }
                this.mInterface.setNightMode(strToNightMode);
                if (strToNightMode == 3) {
                    this.mInterface.setNightModeCustomType(strToNightModeCustomType);
                }
                printCurrentNightMode();
                return 0;
            }
            if (CoreRune.SAFE_DEBUG && nextArg.equals("add")) {
                this.mInterface.addNightPriorityAllowedPackageFromShell(getNextArg());
                return 0;
            }
            if (CoreRune.SAFE_DEBUG && nextArg.equals("reset")) {
                this.mInterface.resetNightPriorityAppliedPackages(Integer.parseInt(getNextArg()));
                return 0;
            }
            errPrintWriter.println("Error: mode must be 'yes', 'no', or 'auto', or 'custom_schedule', or 'custom_bedtime'");
            return -1;
        }

        public final void printCurrentNightMode() {
            getOutPrintWriter().println("Night mode: " + nightModeToStr(this.mInterface.getNightMode(), this.mInterface.getNightModeCustomType()));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static int strToNightMode(String str) {
            char c;
            str.hashCode();
            switch (str.hashCode()) {
                case -757868544:
                    if (str.equals("custom_bedtime")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3521:
                    if (str.equals("no")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 119527:
                    if (str.equals("yes")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3005871:
                    if (str.equals("auto")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 164399013:
                    if (str.equals("custom_schedule")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 4:
                    return 3;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 0;
                default:
                    return -1;
            }
        }

        public static int strToNightModeCustomType(String str) {
            str.hashCode();
            if (str.equals("custom_bedtime")) {
                return 1;
            }
            return !str.equals("custom_schedule") ? -1 : 0;
        }

        public final int handleCarMode() {
            PrintWriter errPrintWriter = getErrPrintWriter();
            String nextArg = getNextArg();
            if (nextArg == null) {
                printCurrentCarMode();
                return 0;
            }
            if (nextArg.equals("yes")) {
                this.mInterface.enableCarMode(0, 0, "");
                printCurrentCarMode();
                return 0;
            }
            if (nextArg.equals("no")) {
                this.mInterface.disableCarMode(0);
                printCurrentCarMode();
                return 0;
            }
            errPrintWriter.println("Error: mode must be 'yes', or 'no'");
            return -1;
        }

        public final void printCurrentCarMode() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            int currentModeType = this.mInterface.getCurrentModeType();
            StringBuilder sb = new StringBuilder();
            sb.append("Car mode: ");
            sb.append(currentModeType == 3 ? "yes" : "no");
            outPrintWriter.println(sb.toString());
        }
    }

    /* loaded from: classes.dex */
    public final class LocalService extends UiModeManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean isNightMode() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = (UiModeManagerService.this.mConfiguration.uiMode & 32) != 0;
            }
            return z;
        }

        @Override // com.android.server.UiModeManagerInternal
        public void onEarlySwitchUser(int i) {
            UiModeManagerService.this.getContext().getContentResolver().unregisterContentObserver(UiModeManagerService.this.mSetupWizardObserver);
            UiModeManagerService.this.verifySetupWizardCompleted();
            synchronized (UiModeManagerService.this.mLock) {
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                uiModeManagerService.updateNightModeFromSettingsLocked(uiModeManagerService.getContext(), UiModeManagerService.this.getContext().getResources(), i);
                UiModeManagerService.this.resetNightModeOverrideLocked();
                LogWrapper.i(UiModeManagerService.TAG, "onEarlySwitchUser : " + UiModeManagerService.this.mNightMode + " userID : " + i);
                UiModeManagerService.this.updateLocked(0, 0);
                UiModeManagerService.this.cancelCustomAlarm();
                if (UiModeManagerService.this.mNightMode == 3) {
                    if (!UiModeManagerService.this.mIsNightModeRegistered) {
                        UiModeManagerService.this.registerTimeChangeEvent();
                        UiModeManagerService.this.mIsNightModeRegistered = true;
                    }
                } else if (UiModeManagerService.this.mIsNightModeRegistered) {
                    UiModeManagerService.this.unregisterTimeChangeEvent();
                    UiModeManagerService.this.mIsNightModeRegistered = false;
                }
            }
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean applyPackageNightModeIfNeeded(String str, int i) {
            if (!UiModeManagerService.this.isNightPriorityAllowed(str) || UiModeManagerService.this.isNightPriorityApplied(str, i)) {
                return false;
            }
            return UiModeManagerService.this.setPackageNightModeInner(str, i, 2);
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean needToShowNightModeDialog(String str, int i) {
            boolean z = false;
            if (UiModeManagerService.this.isNightPriorityAllowed(str)) {
                synchronized (UiModeManagerService.this.mPackagesNeedToShowDialog) {
                    try {
                        z = ((Boolean) UiModeManagerService.this.mPackagesNeedToShowDialog.getValue(i, str)).equals(Boolean.TRUE);
                    } catch (Exception e) {
                        Log.i(UiModeManagerService.TAG, " " + e.toString());
                    }
                }
            }
            return z;
        }

        @Override // com.android.server.UiModeManagerInternal
        public void setNightModeDialogShown(String str, int i) {
            synchronized (UiModeManagerService.this.mPackagesNeedToShowDialog) {
                UiModeManagerService.this.mPackagesNeedToShowDialog.putValue(i, str, Boolean.FALSE);
            }
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean shouldIgnoreDialog() {
            return UiModeManagerService.this.mShopDemo;
        }

        @Override // com.android.server.UiModeManagerInternal
        public void setNewDexMode(boolean z) {
            synchronized (UiModeManagerService.this.mLock) {
                if (UiModeManagerService.this.mNewDexModeEnabled == z) {
                    return;
                }
                UiModeManagerService.this.mToggleNewDexMode = true;
                UiModeManagerService.this.mNewDexModeEnabled = z;
                UiModeManagerService.this.updateConfigurationLocked();
                UiModeManagerService.this.applyConfigurationExternallyLocked();
                UiModeManagerService.this.mToggleNewDexMode = false;
            }
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean toggleNewDexMode() {
            if (Thread.holdsLock(UiModeManagerService.this.mLock)) {
                return UiModeManagerService.this.mToggleNewDexMode;
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public class Injector {
        public int getCallingUid() {
            return Binder.getCallingUid();
        }

        public void startDreamWhenDockedIfAppropriate(Context context) {
            Sandman.startDreamWhenDockedIfAppropriate(context);
        }
    }

    /* loaded from: classes.dex */
    public abstract class LogWrapper {
        public static FileHandler fileHandler;
        public static Logger logger;
        public static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
        public static final Date date = new Date();

        static {
            try {
                FileHandler fileHandler2 = new FileHandler("/data/log/dark_mode_log%g.txt", 5120, 2, true);
                fileHandler = fileHandler2;
                fileHandler2.setFormatter(new Formatter() { // from class: com.android.server.UiModeManagerService.LogWrapper.1
                    @Override // java.util.logging.Formatter
                    public String format(LogRecord logRecord) {
                        LogWrapper.date.setTime(System.currentTimeMillis());
                        StringBuilder sb = new StringBuilder(80);
                        sb.append(LogWrapper.formatter.format(LogWrapper.date));
                        sb.append(logRecord.getMessage());
                        return sb.toString();
                    }
                });
                Logger logger2 = Logger.getLogger(LogWrapper.class.getName());
                logger = logger2;
                logger2.addHandler(fileHandler);
                logger.setLevel(Level.ALL);
                logger.setUseParentHandlers(false);
            } catch (Exception e) {
                Log.i("LogWrapper", "Can not use LogWrapper " + e.toString());
            }
        }

        public static void i(String str, String str2) {
            Logger logger2 = logger;
            if (logger2 != null) {
                logger2.log(Level.INFO, String.format("V %s(%d): %s\n", str, Integer.valueOf(Binder.getCallingPid()), str2));
            }
            Log.i(str, str2);
        }

        public static StringBuilder getLogText() {
            File[] fileArr = {new File("/data/log/dark_mode_log0.txt"), new File("/data/log/dark_mode_log1.txt")};
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                File file = fileArr[i];
                if (file.exists()) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                                sb.append('\n');
                            } finally {
                            }
                        }
                        bufferedReader.close();
                        sb.append('\n');
                    } catch (IOException e) {
                        Log.e("LogWrapper", "Can not use getLogText : " + e);
                        return null;
                    }
                }
            }
            return sb;
        }
    }
}

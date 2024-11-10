package com.android.server.app;

import android.app.ActivityManager;
import android.app.GameManagerInternal;
import android.app.GameModeConfiguration;
import android.app.GameModeInfo;
import android.app.GameState;
import android.app.IGameManagerService;
import android.app.IGameModeListener;
import android.app.StatsManager;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.KeyValueListParser;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.app.GameManagerService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class GameManagerService extends IGameManagerService.Stub {
    public final ArrayMap mConfigs;
    public final Context mContext;
    public DeviceConfigListener mDeviceConfigListener;
    public final Object mDeviceConfigLock;
    public final Set mForegroundGameUids;
    final AtomicFile mGameModeInterventionListFile;
    public final Object mGameModeListenerLock;
    public final ArrayMap mGameModeListeners;
    public final GameServiceController mGameServiceController;
    final Handler mHandler;
    public final Object mLock;
    public final PackageManager mPackageManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public final ArrayMap mSettings;
    public final File mSystemDir;
    final MyUidObserver mUidObserver;
    public final Object mUidObserverLock;
    public final UserManager mUserManager;

    public static int gameModeToStatsdGameMode(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i != 3) {
            return i != 4 ? 0 : 5;
        }
        return 4;
    }

    public static int gameStateModeToStatsdGameState(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 0;
                    }
                }
            }
        }
        return i2;
    }

    public static int modeToBitmask(int i) {
        return 1 << i;
    }

    private static native void nativeSetOverrideFrameRate(int i, float f);

    public final void updateUseAngle(String str, int i) {
    }

    public GameManagerService(Context context) {
        this(context, createServiceThread().getLooper());
    }

    public GameManagerService(Context context, Looper looper) {
        this(context, looper, Environment.getDataDirectory());
    }

    public GameManagerService(Context context, Looper looper, File file) {
        this.mLock = new Object();
        this.mDeviceConfigLock = new Object();
        this.mGameModeListenerLock = new Object();
        this.mSettings = new ArrayMap();
        this.mConfigs = new ArrayMap();
        this.mGameModeListeners = new ArrayMap();
        this.mUidObserverLock = new Object();
        this.mForegroundGameUids = new HashSet();
        this.mContext = context;
        this.mHandler = new SettingsHandler(looper);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        File file2 = new File(file, "system");
        this.mSystemDir = file2;
        file2.mkdirs();
        FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        AtomicFile atomicFile = new AtomicFile(new File(file2, "game_mode_intervention.list"));
        this.mGameModeInterventionListFile = atomicFile;
        FileUtils.setPermissions(atomicFile.getBaseFile().getAbsolutePath(), FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, -1, -1);
        if (context.getPackageManager().hasSystemFeature("android.software.game_service")) {
            this.mGameServiceController = new GameServiceController(context, BackgroundThread.getExecutor(), new GameServiceProviderSelectorImpl(context.getResources(), context.getPackageManager()), new GameServiceProviderInstanceFactoryImpl(context));
        } else {
            this.mGameServiceController = null;
        }
        MyUidObserver myUidObserver = new MyUidObserver();
        this.mUidObserver = myUidObserver;
        try {
            ActivityManager.getService().registerUidObserver(myUidObserver, 3, -1, (String) null);
        } catch (RemoteException unused) {
            Slog.w("GameManagerService", "Could not register UidObserver");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new GameManagerShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump GameManagerService from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        if (strArr == null || strArr.length == 0) {
            printWriter.println("*Dump GameManagerService*");
            dumpAllGameConfigs(printWriter);
        }
    }

    public final void dumpAllGameConfigs(PrintWriter printWriter) {
        int currentUser = ActivityManager.getCurrentUser();
        for (String str : getInstalledGamePackageNames(currentUser)) {
            printWriter.println(getInterventionList(str, currentUser));
        }
    }

    /* loaded from: classes.dex */
    public class SettingsHandler extends Handler {
        public SettingsHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            doHandleMessage(message);
        }

        public void doHandleMessage(Message message) {
            int i;
            switch (message.what) {
                case 1:
                    int intValue = ((Integer) message.obj).intValue();
                    if (intValue < 0) {
                        Slog.wtf("GameManagerService", "Attempt to write settings for invalid user: " + intValue);
                        synchronized (GameManagerService.this.mLock) {
                            removeEqualMessages(1, message.obj);
                        }
                        return;
                    }
                    Process.setThreadPriority(0);
                    synchronized (GameManagerService.this.mLock) {
                        removeEqualMessages(1, message.obj);
                        if (GameManagerService.this.mSettings.containsKey(Integer.valueOf(intValue))) {
                            ((GameManagerSettings) GameManagerService.this.mSettings.get(Integer.valueOf(intValue))).writePersistentDataLocked();
                        }
                    }
                    Process.setThreadPriority(10);
                    return;
                case 2:
                    int intValue2 = ((Integer) message.obj).intValue();
                    if (intValue2 < 0) {
                        Slog.wtf("GameManagerService", "Attempt to write settings for invalid user: " + intValue2);
                        synchronized (GameManagerService.this.mLock) {
                            removeEqualMessages(1, message.obj);
                            removeEqualMessages(2, message.obj);
                        }
                        return;
                    }
                    synchronized (GameManagerService.this.mLock) {
                        removeEqualMessages(1, message.obj);
                        removeEqualMessages(2, message.obj);
                        if (GameManagerService.this.mSettings.containsKey(Integer.valueOf(intValue2))) {
                            GameManagerSettings gameManagerSettings = (GameManagerSettings) GameManagerService.this.mSettings.get(Integer.valueOf(intValue2));
                            GameManagerService.this.mSettings.remove(Integer.valueOf(intValue2));
                            gameManagerSettings.writePersistentDataLocked();
                        }
                    }
                    return;
                case 3:
                    removeEqualMessages(3, message.obj);
                    int intValue3 = ((Integer) message.obj).intValue();
                    GameManagerService.this.updateConfigsForUser(intValue3, false, GameManagerService.this.getInstalledGamePackageNames(intValue3));
                    return;
                case 4:
                    GameState gameState = (GameState) message.obj;
                    boolean isLoading = gameState.isLoading();
                    Bundle data = message.getData();
                    String string = data.getString("packageName");
                    int i2 = data.getInt("userId");
                    boolean z = GameManagerService.this.getGameMode(string, i2) == 2;
                    try {
                        i = GameManagerService.this.mPackageManager.getPackageUidAsUser(string, i2);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.v("GameManagerService", "Failed to get package metadata");
                        i = -1;
                    }
                    FrameworkStatsLog.write(FrameworkStatsLog.GAME_STATE_CHANGED, string, i, z, GameManagerService.gameStateModeToStatsdGameState(gameState.getMode()), isLoading, gameState.getLabel(), gameState.getQuality());
                    if (z) {
                        if (GameManagerService.this.mPowerManagerInternal == null) {
                            Slog.d("GameManagerService", "Error setting loading mode for package " + string + " and userId " + i2);
                            return;
                        }
                        if (GameManagerService.this.mHandler.hasMessages(5)) {
                            GameManagerService.this.mHandler.removeMessages(5);
                        }
                        GameManagerService.this.mPowerManagerInternal.setPowerMode(16, isLoading);
                        if (isLoading) {
                            int loadingBoostDuration = GameManagerService.this.getLoadingBoostDuration(string, i2);
                            if (loadingBoostDuration <= 0) {
                                loadingBoostDuration = 5000;
                            }
                            Handler handler = GameManagerService.this.mHandler;
                            handler.sendMessageDelayed(handler.obtainMessage(5), loadingBoostDuration);
                            return;
                        }
                        return;
                    }
                    return;
                case 5:
                    GameManagerService.this.mPowerManagerInternal.setPowerMode(16, false);
                    return;
                case 6:
                    int intValue4 = ((Integer) message.obj).intValue();
                    if (intValue4 < 0) {
                        Slog.wtf("GameManagerService", "Attempt to write setting for invalid user: " + intValue4);
                        synchronized (GameManagerService.this.mLock) {
                            removeEqualMessages(6, message.obj);
                        }
                        return;
                    }
                    Process.setThreadPriority(0);
                    removeEqualMessages(6, message.obj);
                    GameManagerService.this.writeGameModeInterventionsToFile(intValue4);
                    Process.setThreadPriority(10);
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes.dex */
    public class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public DeviceConfigListener() {
            DeviceConfig.addOnPropertiesChangedListener("game_overlay", GameManagerService.this.mContext.getMainExecutor(), this);
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            GameManagerService.this.updateConfigsForUser(ActivityManager.getCurrentUser(), true, (String[]) properties.getKeyset().toArray(new String[0]));
        }

        public void finalize() {
            DeviceConfig.removeOnPropertiesChangedListener(this);
        }
    }

    /* loaded from: classes.dex */
    public enum FrameRate {
        FPS_DEFAULT(0),
        FPS_30(30),
        FPS_36(36),
        FPS_40(40),
        FPS_45(45),
        FPS_48(48),
        FPS_60(60),
        FPS_72(72),
        FPS_90(90),
        FPS_120(120),
        FPS_144(144),
        FPS_INVALID(-1);

        public final int fps;

        FrameRate(int i) {
            this.fps = i;
        }
    }

    public static int getFpsInt(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 0:
                if (str.equals("")) {
                    c = 0;
                    break;
                }
                break;
            case 1629:
                if (str.equals("30")) {
                    c = 1;
                    break;
                }
                break;
            case 1635:
                if (str.equals("36")) {
                    c = 2;
                    break;
                }
                break;
            case 1660:
                if (str.equals("40")) {
                    c = 3;
                    break;
                }
                break;
            case 1665:
                if (str.equals("45")) {
                    c = 4;
                    break;
                }
                break;
            case 1668:
                if (str.equals("48")) {
                    c = 5;
                    break;
                }
                break;
            case 1722:
                if (str.equals("60")) {
                    c = 6;
                    break;
                }
                break;
            case 1755:
                if (str.equals("72")) {
                    c = 7;
                    break;
                }
                break;
            case 1815:
                if (str.equals("90")) {
                    c = '\b';
                    break;
                }
                break;
            case 48687:
                if (str.equals("120")) {
                    c = '\t';
                    break;
                }
                break;
            case 48753:
                if (str.equals("144")) {
                    c = '\n';
                    break;
                }
                break;
            case 1671308008:
                if (str.equals("disable")) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 11:
                return FrameRate.FPS_DEFAULT.fps;
            case 1:
                return FrameRate.FPS_30.fps;
            case 2:
                return FrameRate.FPS_36.fps;
            case 3:
                return FrameRate.FPS_40.fps;
            case 4:
                return FrameRate.FPS_45.fps;
            case 5:
                return FrameRate.FPS_48.fps;
            case 6:
                return FrameRate.FPS_60.fps;
            case 7:
                return FrameRate.FPS_72.fps;
            case '\b':
                return FrameRate.FPS_90.fps;
            case '\t':
                return FrameRate.FPS_120.fps;
            case '\n':
                return FrameRate.FPS_144.fps;
            default:
                return FrameRate.FPS_INVALID.fps;
        }
    }

    public void setGameState(String str, GameState gameState, int i) {
        if (!lambda$updateConfigsForUser$0(str, i)) {
            Slog.d("GameManagerService", "No-op for attempt to set game state for non-game app: " + str);
            return;
        }
        Message obtainMessage = this.mHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt("userId", i);
        obtainMessage.setData(bundle);
        obtainMessage.obj = gameState;
        this.mHandler.sendMessage(obtainMessage);
    }

    /* loaded from: classes.dex */
    public class GamePackageConfiguration {
        public boolean mAllowAngle;
        public boolean mAllowDownscale;
        public boolean mAllowFpsOverride;
        public boolean mBatteryModeOverridden;
        public final Object mModeConfigLock;
        public final ArrayMap mModeConfigs;
        public final String mPackageName;
        public boolean mPerfModeOverridden;

        public GamePackageConfiguration(String str) {
            this.mModeConfigLock = new Object();
            this.mModeConfigs = new ArrayMap();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
        }

        public GamePackageConfiguration(PackageManager packageManager, String str, int i) {
            Bundle bundle;
            this.mModeConfigLock = new Object();
            this.mModeConfigs = new ArrayMap();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
            try {
                ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 128, i);
                if (!parseInterventionFromXml(packageManager, applicationInfoAsUser, str) && (bundle = applicationInfoAsUser.metaData) != null) {
                    this.mPerfModeOverridden = bundle.getBoolean("com.android.app.gamemode.performance.enabled");
                    this.mBatteryModeOverridden = applicationInfoAsUser.metaData.getBoolean("com.android.app.gamemode.battery.enabled");
                    this.mAllowDownscale = applicationInfoAsUser.metaData.getBoolean("com.android.graphics.intervention.wm.allowDownscale", true);
                    this.mAllowAngle = applicationInfoAsUser.metaData.getBoolean("com.android.graphics.intervention.angle.allowAngle", true);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.v("GameManagerService_GamePackageConfiguration", "Failed to get package metadata");
            }
            String property = DeviceConfig.getProperty("game_overlay", str);
            if (property != null) {
                for (String str2 : property.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    try {
                        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
                        keyValueListParser.setString(str2);
                        addModeConfig(new GameModeConfiguration(keyValueListParser));
                    } catch (IllegalArgumentException unused2) {
                        Slog.e("GameManagerService_GamePackageConfiguration", "Invalid config string");
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean parseInterventionFromXml(android.content.pm.PackageManager r7, android.content.pm.ApplicationInfo r8, java.lang.String r9) {
            /*
                r6 = this;
                java.lang.String r0 = "GameManagerService_GamePackageConfiguration"
                r1 = 1
                r2 = 0
                java.lang.String r3 = "android.game_mode_config"
                android.content.res.XmlResourceParser r8 = r8.loadXmlMetaData(r7, r3)     // Catch: java.lang.Throwable -> L8a
                if (r8 != 0) goto L27
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L24
                r7.<init>()     // Catch: java.lang.Throwable -> L24
                java.lang.String r9 = "No android.game_mode_config meta-data found for package "
                r7.append(r9)     // Catch: java.lang.Throwable -> L24
                java.lang.String r9 = r6.mPackageName     // Catch: java.lang.Throwable -> L24
                r7.append(r9)     // Catch: java.lang.Throwable -> L24
                java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L24
                android.util.Slog.v(r0, r7)     // Catch: java.lang.Throwable -> L24
                r7 = r2
                goto L75
            L24:
                r7 = move-exception
                r9 = r2
                goto L7d
            L27:
                android.content.res.Resources r7 = r7.getResourcesForApplication(r9)     // Catch: java.lang.Throwable -> L7b
                android.util.AttributeSet r9 = android.util.Xml.asAttributeSet(r8)     // Catch: java.lang.Throwable -> L7b
            L2f:
                int r3 = r8.next()     // Catch: java.lang.Throwable -> L7b
                r4 = 2
                if (r3 == r1) goto L39
                if (r3 == r4) goto L39
                goto L2f
            L39:
                java.lang.String r3 = "game-mode-config"
                java.lang.String r5 = r8.getName()     // Catch: java.lang.Throwable -> L7b
                boolean r3 = r3.equals(r5)     // Catch: java.lang.Throwable -> L7b
                if (r3 != 0) goto L4b
                java.lang.String r7 = "Meta-data does not start with game-mode-config tag"
                android.util.Slog.w(r0, r7)     // Catch: java.lang.Throwable -> L7b
                goto L74
            L4b:
                int[] r3 = com.android.internal.R.styleable.GameModeConfig     // Catch: java.lang.Throwable -> L7b
                android.content.res.TypedArray r7 = r7.obtainAttributes(r9, r3)     // Catch: java.lang.Throwable -> L7b
                boolean r9 = r7.getBoolean(r1, r2)     // Catch: java.lang.Throwable -> L7b
                r6.mPerfModeOverridden = r9     // Catch: java.lang.Throwable -> L7b
                boolean r9 = r7.getBoolean(r2, r2)     // Catch: java.lang.Throwable -> L7b
                r6.mBatteryModeOverridden = r9     // Catch: java.lang.Throwable -> L7b
                r9 = 3
                boolean r9 = r7.getBoolean(r9, r1)     // Catch: java.lang.Throwable -> L7b
                r6.mAllowDownscale = r9     // Catch: java.lang.Throwable -> L7b
                boolean r9 = r7.getBoolean(r4, r1)     // Catch: java.lang.Throwable -> L7b
                r6.mAllowAngle = r9     // Catch: java.lang.Throwable -> L7b
                r9 = 4
                boolean r9 = r7.getBoolean(r9, r1)     // Catch: java.lang.Throwable -> L7b
                r6.mAllowFpsOverride = r9     // Catch: java.lang.Throwable -> L7b
                r7.recycle()     // Catch: java.lang.Throwable -> L7b
            L74:
                r7 = r1
            L75:
                if (r8 == 0) goto L9a
                r8.close()     // Catch: java.lang.Throwable -> L8b
                goto L9a
            L7b:
                r7 = move-exception
                r9 = r1
            L7d:
                if (r8 == 0) goto L87
                r8.close()     // Catch: java.lang.Throwable -> L83
                goto L87
            L83:
                r8 = move-exception
                r7.addSuppressed(r8)     // Catch: java.lang.Throwable -> L88
            L87:
                throw r7     // Catch: java.lang.Throwable -> L88
            L88:
                r7 = r9
                goto L8b
            L8a:
                r7 = r2
            L8b:
                r6.mPerfModeOverridden = r2
                r6.mBatteryModeOverridden = r2
                r6.mAllowDownscale = r1
                r6.mAllowAngle = r1
                r6.mAllowFpsOverride = r1
                java.lang.String r6 = "Error while parsing XML meta-data for android.game_mode_config"
                android.util.Slog.e(r0, r6)
            L9a:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.app.GameManagerService.GamePackageConfiguration.parseInterventionFromXml(android.content.pm.PackageManager, android.content.pm.ApplicationInfo, java.lang.String):boolean");
        }

        public GameModeConfiguration getOrAddDefaultGameModeConfiguration(int i) {
            GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                this.mModeConfigs.putIfAbsent(Integer.valueOf(i), new GameModeConfiguration(i));
                gameModeConfiguration = (GameModeConfiguration) this.mModeConfigs.get(Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        public boolean hasActiveGameModeConfig() {
            boolean z;
            synchronized (this.mModeConfigLock) {
                z = !this.mModeConfigs.isEmpty();
            }
            return z;
        }

        /* loaded from: classes.dex */
        public class GameModeConfiguration {
            public String mFps;
            public final int mGameMode;
            public int mLoadingBoostDuration;
            public float mScaling;
            public boolean mUseAngle;

            public GameModeConfiguration(int i) {
                this.mScaling = -1.0f;
                this.mFps = "";
                this.mGameMode = i;
                this.mUseAngle = false;
                this.mLoadingBoostDuration = -1;
            }

            public GameModeConfiguration(KeyValueListParser keyValueListParser) {
                float f = -1.0f;
                this.mScaling = -1.0f;
                String str = "";
                this.mFps = "";
                boolean z = false;
                int i = keyValueListParser.getInt("mode", 0);
                this.mGameMode = i;
                if (GamePackageConfiguration.this.mAllowDownscale && !GamePackageConfiguration.this.willGamePerformOptimizations(i)) {
                    f = keyValueListParser.getFloat("downscaleFactor", -1.0f);
                }
                this.mScaling = f;
                if (GamePackageConfiguration.this.mAllowFpsOverride && !GamePackageConfiguration.this.willGamePerformOptimizations(i)) {
                    str = keyValueListParser.getString("fps", "");
                }
                this.mFps = str;
                if (GamePackageConfiguration.this.mAllowAngle && !GamePackageConfiguration.this.willGamePerformOptimizations(i) && keyValueListParser.getBoolean("useAngle", false)) {
                    z = true;
                }
                this.mUseAngle = z;
                this.mLoadingBoostDuration = GamePackageConfiguration.this.willGamePerformOptimizations(i) ? -1 : keyValueListParser.getInt("loadingBoost", -1);
            }

            public int getGameMode() {
                return this.mGameMode;
            }

            public synchronized float getScaling() {
                return this.mScaling;
            }

            public synchronized int getFps() {
                return GameManagerService.getFpsInt(this.mFps);
            }

            public synchronized String getFpsStr() {
                return this.mFps;
            }

            public synchronized boolean getUseAngle() {
                return this.mUseAngle;
            }

            public synchronized int getLoadingBoostDuration() {
                return this.mLoadingBoostDuration;
            }

            public synchronized void setScaling(float f) {
                this.mScaling = f;
            }

            public synchronized void setFpsStr(String str) {
                this.mFps = str;
            }

            public synchronized void setUseAngle(boolean z) {
                this.mUseAngle = z;
            }

            public synchronized void setLoadingBoostDuration(int i) {
                this.mLoadingBoostDuration = i;
            }

            public boolean isActive() {
                int i = this.mGameMode;
                return (i == 1 || i == 2 || i == 3 || i == 4) && !GamePackageConfiguration.this.willGamePerformOptimizations(i);
            }

            public android.app.GameModeConfiguration toPublicGameModeConfig() {
                int fpsInt = GameManagerService.getFpsInt(this.mFps);
                if (fpsInt <= 0) {
                    fpsInt = 0;
                }
                float f = this.mScaling;
                if (f == -1.0f) {
                    f = 1.0f;
                }
                return new GameModeConfiguration.Builder().setScalingFactor(f).setFpsOverride(fpsInt).build();
            }

            public void updateFromPublicGameModeConfig(android.app.GameModeConfiguration gameModeConfiguration) {
                this.mScaling = gameModeConfiguration.getScalingFactor();
                this.mFps = String.valueOf(gameModeConfiguration.getFpsOverride());
            }

            public String toString() {
                return "[Game Mode:" + this.mGameMode + ",Scaling:" + this.mScaling + ",Use Angle:" + this.mUseAngle + ",Fps:" + this.mFps + ",Loading Boost Duration:" + this.mLoadingBoostDuration + "]";
            }
        }

        public boolean willGamePerformOptimizations(int i) {
            return (this.mBatteryModeOverridden && i == 3) || (this.mPerfModeOverridden && i == 2);
        }

        public final int getAvailableGameModesBitfield() {
            int modeToBitmask = GameManagerService.modeToBitmask(4) | GameManagerService.modeToBitmask(1);
            synchronized (this.mModeConfigLock) {
                Iterator it = this.mModeConfigs.keySet().iterator();
                while (it.hasNext()) {
                    modeToBitmask |= GameManagerService.modeToBitmask(((Integer) it.next()).intValue());
                }
            }
            if (this.mBatteryModeOverridden) {
                modeToBitmask |= GameManagerService.modeToBitmask(3);
            }
            return this.mPerfModeOverridden ? modeToBitmask | GameManagerService.modeToBitmask(2) : modeToBitmask;
        }

        public int[] getAvailableGameModes() {
            int availableGameModesBitfield = getAvailableGameModesBitfield();
            int[] iArr = new int[Integer.bitCount(availableGameModesBitfield)];
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(Integer.highestOneBit(availableGameModesBitfield));
            int i = 0;
            for (int i2 = 0; i2 <= numberOfTrailingZeros; i2++) {
                if (((availableGameModesBitfield >> i2) & 1) != 0) {
                    iArr[i] = i2;
                    i++;
                }
            }
            return iArr;
        }

        public int[] getOverriddenGameModes() {
            boolean z = this.mBatteryModeOverridden;
            if (z && this.mPerfModeOverridden) {
                return new int[]{3, 2};
            }
            if (z) {
                return new int[]{3};
            }
            return this.mPerfModeOverridden ? new int[]{2} : new int[0];
        }

        public GameModeConfiguration getGameModeConfiguration(int i) {
            GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                gameModeConfiguration = (GameModeConfiguration) this.mModeConfigs.get(Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        public void addModeConfig(GameModeConfiguration gameModeConfiguration) {
            if (gameModeConfiguration.isActive()) {
                synchronized (this.mModeConfigLock) {
                    this.mModeConfigs.put(Integer.valueOf(gameModeConfiguration.getGameMode()), gameModeConfiguration);
                }
            } else {
                Slog.w("GameManagerService_GamePackageConfiguration", "Attempt to add inactive game mode config for " + this.mPackageName + XmlUtils.STRING_ARRAY_SEPARATOR + gameModeConfiguration.toString());
            }
        }

        public void removeModeConfig(int i) {
            synchronized (this.mModeConfigLock) {
                this.mModeConfigs.remove(Integer.valueOf(i));
            }
        }

        public boolean isActive() {
            boolean z;
            synchronized (this.mModeConfigLock) {
                z = this.mModeConfigs.size() > 0 || this.mBatteryModeOverridden || this.mPerfModeOverridden;
            }
            return z;
        }

        public GamePackageConfiguration copyAndApplyOverride(GamePackageConfiguration gamePackageConfiguration) {
            GamePackageConfiguration gamePackageConfiguration2 = new GamePackageConfiguration(this.mPackageName);
            boolean z = true;
            gamePackageConfiguration2.mPerfModeOverridden = this.mPerfModeOverridden && (gamePackageConfiguration == null || gamePackageConfiguration.getGameModeConfiguration(2) == null);
            gamePackageConfiguration2.mBatteryModeOverridden = this.mBatteryModeOverridden && (gamePackageConfiguration == null || gamePackageConfiguration.getGameModeConfiguration(3) == null);
            gamePackageConfiguration2.mAllowDownscale = this.mAllowDownscale || gamePackageConfiguration != null;
            gamePackageConfiguration2.mAllowAngle = this.mAllowAngle || gamePackageConfiguration != null;
            if (!this.mAllowFpsOverride && gamePackageConfiguration == null) {
                z = false;
            }
            gamePackageConfiguration2.mAllowFpsOverride = z;
            if (gamePackageConfiguration != null) {
                synchronized (gamePackageConfiguration2.mModeConfigLock) {
                    synchronized (this.mModeConfigLock) {
                        for (Map.Entry entry : this.mModeConfigs.entrySet()) {
                            gamePackageConfiguration2.mModeConfigs.put((Integer) entry.getKey(), (GameModeConfiguration) entry.getValue());
                        }
                    }
                    synchronized (gamePackageConfiguration.mModeConfigLock) {
                        for (Map.Entry entry2 : gamePackageConfiguration.mModeConfigs.entrySet()) {
                            gamePackageConfiguration2.mModeConfigs.put((Integer) entry2.getKey(), (GameModeConfiguration) entry2.getValue());
                        }
                    }
                }
            }
            return gamePackageConfiguration2;
        }

        public String toString() {
            String str;
            synchronized (this.mModeConfigLock) {
                str = "[Name:" + this.mPackageName + " Modes: " + this.mModeConfigs.toString() + "]";
            }
            return str;
        }
    }

    /* loaded from: classes.dex */
    public final class LocalService extends GameManagerInternal {
        public LocalService() {
        }

        public float getResolutionScalingFactor(String str, int i) {
            return GameManagerService.this.getResolutionScalingFactorInternal(str, GameManagerService.this.getGameModeFromSettingsUnchecked(str, i), i);
        }

        public void updateResolutionScalingFactorInternal(String str, float f) {
            synchronized (GameManagerService.this.mLock) {
                if (GameManagerService.this.mSettings.containsKey(Integer.valueOf(GameManagerService.this.mContext.getUserId()))) {
                    GameManagerService gameManagerService = GameManagerService.this;
                    int gameModeFromSettingsUnchecked = gameManagerService.getGameModeFromSettingsUnchecked(str, gameManagerService.mContext.getUserId());
                    GameManagerService gameManagerService2 = GameManagerService.this;
                    gameManagerService2.updateResolutionScalingFactor(str, gameModeFromSettingsUnchecked, f, gameManagerService2.mContext.getUserId());
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public class Lifecycle extends SystemService {
        public GameManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new GameManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("game", this.mService);
            this.mService.publishLocalService();
            this.mService.registerDeviceConfigListener();
            this.mService.registerPackageReceiver();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 1000) {
                this.mService.onBootCompleted();
                this.mService.registerStatsCallbacks();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            Slog.d("GameManagerService", "Starting user " + targetUser.getUserIdentifier());
            this.mService.onUserStarting(targetUser, Environment.getDataSystemDeDirectory(targetUser.getUserIdentifier()));
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mService.onUserUnlocking(targetUser);
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(SystemService.TargetUser targetUser) {
            this.mService.onUserStopping(targetUser);
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            this.mService.onUserSwitching(targetUser, targetUser2);
        }
    }

    public final boolean isValidPackageName(String str, int i) {
        try {
            return this.mPackageManager.getPackageUidAsUser(str, i) == Binder.getCallingUid();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void checkPermission(String str) {
        if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
            return;
        }
        throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission " + str);
    }

    public final int[] getAvailableGameModesUnchecked(String str, int i) {
        GamePackageConfiguration config = getConfig(str, i);
        if (config == null) {
            return new int[]{1, 4};
        }
        return config.getAvailableGameModes();
    }

    /* renamed from: isPackageGame, reason: merged with bridge method [inline-methods] */
    public final boolean lambda$updateConfigsForUser$0(String str, int i) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, i).category == 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public int[] getAvailableGameModes(String str, int i) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        return !lambda$updateConfigsForUser$0(str, i) ? new int[0] : getAvailableGameModesUnchecked(str, i);
    }

    public final int getGameModeFromSettingsUnchecked(String str, int i) {
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i))) {
                Slog.d("GameManagerService", "User ID '" + i + "' does not have a Game Mode selected for package: '" + str + "'");
                return 1;
            }
            return ((GameManagerSettings) this.mSettings.get(Integer.valueOf(i))).getGameModeLocked(str);
        }
    }

    public int getGameMode(String str, int i) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, "getGameMode", "com.android.server.app.GameManagerService");
        boolean z = false;
        if (!lambda$updateConfigsForUser$0(str, handleIncomingUser)) {
            return 0;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            StackTraceElement stackTraceElement = stackTrace[3];
            if (stackTraceElement.getClassName().contains("IGameManagerService") && stackTraceElement.getMethodName().contains("onTransact")) {
                z = true;
            }
        }
        String str2 = "GAME_MODE_PERFORMANCE";
        if (isValidPackageName(str, handleIncomingUser)) {
            if (z) {
                int gameModeFromSettingsUnchecked = getGameModeFromSettingsUnchecked(str, handleIncomingUser);
                if (gameModeFromSettingsUnchecked == 3) {
                    str2 = "GAME_MODE_BATTERY";
                } else if (gameModeFromSettingsUnchecked == 1) {
                    str2 = "GAME_MODE_STANDARD";
                } else if (gameModeFromSettingsUnchecked != 2) {
                    str2 = "GAME_MODE_UNSUPPORTED";
                }
                Slog.d("GMS_getGameMode", "PackageName = : " + str + str2);
            }
            return getGameModeFromSettingsUnchecked(str, handleIncomingUser);
        }
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (z) {
            int gameModeFromSettingsUnchecked2 = getGameModeFromSettingsUnchecked(str, handleIncomingUser);
            if (gameModeFromSettingsUnchecked2 == 3) {
                str2 = "GAME_MODE_BATTERY";
            } else if (gameModeFromSettingsUnchecked2 == 1) {
                str2 = "GAME_MODE_STANDARD";
            } else if (gameModeFromSettingsUnchecked2 != 2) {
                str2 = "GAME_MODE_UNSUPPORTED";
            }
            Slog.d("GMS_getGameMode", "PackageName = : " + str + str2);
        }
        return getGameModeFromSettingsUnchecked(str, handleIncomingUser);
    }

    public GameModeInfo getGameModeInfo(String str, int i) {
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, "getGameModeInfo", "com.android.server.app.GameManagerService");
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!lambda$updateConfigsForUser$0(str, handleIncomingUser)) {
            return null;
        }
        int gameModeFromSettingsUnchecked = getGameModeFromSettingsUnchecked(str, handleIncomingUser);
        GamePackageConfiguration config = getConfig(str, handleIncomingUser);
        if (config != null) {
            int[] overriddenGameModes = config.getOverriddenGameModes();
            int[] availableGameModes = config.getAvailableGameModes();
            GameModeInfo.Builder fpsOverrideAllowed = new GameModeInfo.Builder().setActiveGameMode(gameModeFromSettingsUnchecked).setAvailableGameModes(availableGameModes).setOverriddenGameModes(overriddenGameModes).setDownscalingAllowed(config.mAllowDownscale).setFpsOverrideAllowed(config.mAllowFpsOverride);
            for (int i2 : availableGameModes) {
                if (!config.willGamePerformOptimizations(i2) && (gameModeConfiguration = config.getGameModeConfiguration(i2)) != null) {
                    fpsOverrideAllowed.setGameModeConfiguration(i2, gameModeConfiguration.toPublicGameModeConfig());
                }
            }
            return fpsOverrideAllowed.build();
        }
        return new GameModeInfo.Builder().setActiveGameMode(gameModeFromSettingsUnchecked).setAvailableGameModes(getAvailableGameModesUnchecked(str, handleIncomingUser)).build();
    }

    public void setGameMode(String str, int i, int i2) {
        int i3;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (i == 0) {
            Slog.d("GameManagerService", "No-op for attempt to set UNSUPPORTED mode for app: " + str);
            return;
        }
        if (!lambda$updateConfigsForUser$0(str, i2)) {
            Slog.d("GameManagerService", "No-op for attempt to set game mode for non-game app: " + str);
            return;
        }
        synchronized (this.mLock) {
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "setGameMode", "com.android.server.app.GameManagerService");
            if (!this.mSettings.containsKey(Integer.valueOf(handleIncomingUser))) {
                Slog.d("GameManagerService", "Failed to set game mode for package " + str + " as user " + handleIncomingUser + " is not started");
                return;
            }
            GameManagerSettings gameManagerSettings = (GameManagerSettings) this.mSettings.get(Integer.valueOf(handleIncomingUser));
            int gameModeLocked = gameManagerSettings.getGameModeLocked(str);
            gameManagerSettings.setGameModeLocked(str, i);
            updateInterventions(str, i, handleIncomingUser);
            synchronized (this.mGameModeListenerLock) {
                for (IGameModeListener iGameModeListener : this.mGameModeListeners.keySet()) {
                    Binder.allowBlocking(iGameModeListener.asBinder());
                    try {
                        iGameModeListener.onGameModeChanged(str, gameModeLocked, i, handleIncomingUser);
                    } catch (RemoteException unused) {
                        Slog.w("GameManagerService", "Cannot notify game mode change for listener added by " + this.mGameModeListeners.get(iGameModeListener));
                    }
                }
            }
            sendUserMessage(handleIncomingUser, 1, "SET_GAME_MODE", 10000);
            sendUserMessage(handleIncomingUser, 6, "SET_GAME_MODE", 0);
            try {
                i3 = this.mPackageManager.getPackageUidAsUser(str, handleIncomingUser);
            } catch (PackageManager.NameNotFoundException unused2) {
                Slog.d("GameManagerService", "Cannot find the UID for package " + str + " under user " + handleIncomingUser);
                i3 = -1;
            }
            FrameworkStatsLog.write(FrameworkStatsLog.GAME_MODE_CHANGED, i3, Binder.getCallingUid(), gameModeToStatsdGameMode(gameModeLocked), gameModeToStatsdGameMode(i));
        }
    }

    public boolean isAngleEnabled(String str, int i) {
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return false;
        }
        synchronized (this.mDeviceConfigLock) {
            GamePackageConfiguration gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str);
            if (gamePackageConfiguration == null) {
                return false;
            }
            GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(gameMode);
            if (gameModeConfiguration == null) {
                return false;
            }
            return gameModeConfiguration.getUseAngle();
        }
    }

    public int getLoadingBoostDuration(String str, int i) {
        GamePackageConfiguration gamePackageConfiguration;
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return -1;
        }
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str);
        }
        if (gamePackageConfiguration == null || (gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(gameMode)) == null) {
            return -1;
        }
        return gameModeConfiguration.getLoadingBoostDuration();
    }

    public void notifyGraphicsEnvironmentSetup(String str, int i) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, "notifyGraphicsEnvironmentSetup", "com.android.server.app.GameManagerService");
        if (!isValidPackageName(str, handleIncomingUser)) {
            Slog.d("GameManagerService", "No-op for attempt to notify graphics env setup for different packagethan caller with uid: " + Binder.getCallingUid());
            return;
        }
        if (getGameMode(str, handleIncomingUser) == 0) {
            Slog.d("GameManagerService", "No-op for attempt to notify graphics env setup for non-game app: " + str);
            return;
        }
        int loadingBoostDuration = getLoadingBoostDuration(str, handleIncomingUser);
        if (loadingBoostDuration != -1) {
            if (loadingBoostDuration == 0 || loadingBoostDuration > 5000) {
                loadingBoostDuration = 5000;
            }
            if (this.mHandler.hasMessages(5)) {
                this.mHandler.removeMessages(5);
            } else {
                this.mPowerManagerInternal.setPowerMode(16, true);
            }
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(5), loadingBoostDuration);
        }
    }

    public void setGameServiceProvider(String str) {
        checkPermission("android.permission.SET_GAME_SERVICE");
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController == null) {
            return;
        }
        gameServiceController.setGameServiceProvider(str);
    }

    public void updateResolutionScalingFactor(String str, int i, float f, int i2) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i2))) {
                throw new IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        setGameModeConfigOverride(str, i2, i, null, Float.toString(f));
    }

    public float getResolutionScalingFactor(String str, int i, int i2) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i2))) {
                throw new IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        return getResolutionScalingFactorInternal(str, i, i2);
    }

    public float getResolutionScalingFactorInternal(String str, int i, int i2) {
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        GamePackageConfiguration config = getConfig(str, i2);
        if (config == null || (gameModeConfiguration = config.getGameModeConfiguration(i)) == null) {
            return -1.0f;
        }
        return gameModeConfiguration.getScaling();
    }

    public void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration, int i) {
        int i2;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!lambda$updateConfigsForUser$0(str, i)) {
            Slog.d("GameManagerService", "No-op for attempt to update custom game mode for non-game app: " + str);
            return;
        }
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i))) {
                throw new IllegalArgumentException("User " + i + " wasn't started");
            }
        }
        synchronized (this.mLock) {
            if (this.mSettings.containsKey(Integer.valueOf(i))) {
                GameManagerSettings gameManagerSettings = (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                if (configOverride == null) {
                    configOverride = new GamePackageConfiguration(str);
                    gameManagerSettings.setConfigOverride(str, configOverride);
                }
                GamePackageConfiguration.GameModeConfiguration orAddDefaultGameModeConfiguration = configOverride.getOrAddDefaultGameModeConfiguration(4);
                float scaling = orAddDefaultGameModeConfiguration.getScaling();
                int fps = orAddDefaultGameModeConfiguration.getFps();
                orAddDefaultGameModeConfiguration.updateFromPublicGameModeConfig(gameModeConfiguration);
                sendUserMessage(i, 1, "UPDATE_CUSTOM_GAME_MODE_CONFIG", 10000);
                sendUserMessage(i, 6, "UPDATE_CUSTOM_GAME_MODE_CONFIG", 10000);
                int gameMode = getGameMode(str, i);
                if (gameMode == 4) {
                    updateInterventions(str, gameMode, i);
                }
                Slog.i("GameManagerService", "Updated custom game mode config for package: " + str + " with FPS=" + orAddDefaultGameModeConfiguration.getFps() + ";Scaling=" + orAddDefaultGameModeConfiguration.getScaling() + " under user " + i);
                try {
                    i2 = this.mPackageManager.getPackageUidAsUser(str, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.d("GameManagerService", "Cannot find the UID for package " + str + " under user " + i);
                    i2 = -1;
                }
                FrameworkStatsLog.write(FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i2, Binder.getCallingUid(), gameModeToStatsdGameMode(4), scaling, gameModeConfiguration.getScalingFactor(), fps, gameModeConfiguration.getFpsOverride());
            }
        }
    }

    public void addGameModeListener(final IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            final IBinder asBinder = iGameModeListener.asBinder();
            asBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.app.GameManagerService.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    GameManagerService.this.removeGameModeListenerUnchecked(iGameModeListener);
                    asBinder.unlinkToDeath(this, 0);
                }
            }, 0);
            synchronized (this.mGameModeListenerLock) {
                this.mGameModeListeners.put(iGameModeListener, Integer.valueOf(Binder.getCallingUid()));
            }
        } catch (RemoteException e) {
            Slog.e("GameManagerService", "Failed to link death recipient for IGameModeListener from caller " + Binder.getCallingUid() + ", abandoned its listener registration", e);
        }
    }

    public void removeGameModeListener(IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        removeGameModeListenerUnchecked(iGameModeListener);
    }

    public final void removeGameModeListenerUnchecked(IGameModeListener iGameModeListener) {
        synchronized (this.mGameModeListenerLock) {
            this.mGameModeListeners.remove(iGameModeListener);
        }
    }

    public void onBootCompleted() {
        Slog.d("GameManagerService", "onBootCompleted");
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null) {
            gameServiceController.onBootComplete();
        }
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.app.GameManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (GameManagerService.this.mLock) {
                        Iterator it = GameManagerService.this.mSettings.entrySet().iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) ((Map.Entry) it.next()).getKey()).intValue();
                            GameManagerService.this.sendUserMessage(intValue, 1, "RECEIVE_SHUTDOWN_INDENT", 0);
                            GameManagerService.this.sendUserMessage(intValue, 6, "RECEIVE_SHUTDOWN_INDENT", 0);
                        }
                    }
                }
            }
        }, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
    }

    public final void sendUserMessage(int i, int i2, String str, int i3) {
        if (this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i2, Integer.valueOf(i)), i3)) {
            return;
        }
        Slog.e("GameManagerService", "Failed to send user message " + i2 + " on " + str);
    }

    public void onUserStarting(SystemService.TargetUser targetUser, File file) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(userIdentifier))) {
                GameManagerSettings gameManagerSettings = new GameManagerSettings(file);
                this.mSettings.put(Integer.valueOf(userIdentifier), gameManagerSettings);
                gameManagerSettings.readPersistentDataLocked();
            }
        }
        sendUserMessage(userIdentifier, 3, "ON_USER_STARTING", 0);
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null) {
            gameServiceController.notifyUserStarted(targetUser);
        }
    }

    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null) {
            gameServiceController.notifyUserUnlocking(targetUser);
        }
    }

    public void onUserStopping(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            if (this.mSettings.containsKey(Integer.valueOf(userIdentifier))) {
                sendUserMessage(userIdentifier, 2, "ON_USER_STOPPING", 0);
                GameServiceController gameServiceController = this.mGameServiceController;
                if (gameServiceController != null) {
                    gameServiceController.notifyUserStopped(targetUser);
                }
            }
        }
    }

    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        sendUserMessage(targetUser2.getUserIdentifier(), 3, "ON_USER_SWITCHING", 0);
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null) {
            gameServiceController.notifyNewForegroundUser(targetUser2);
        }
    }

    public final void resetFps(String str, int i) {
        try {
            setOverrideFrameRate(this.mPackageManager.getPackageUidAsUser(str, i), DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final boolean bitFieldContainsModeBitmask(int i, int i2) {
        return (modeToBitmask(i2) & i) != 0;
    }

    public final void updateFps(GamePackageConfiguration gamePackageConfiguration, String str, int i, int i2) {
        if (gamePackageConfiguration.getGameModeConfiguration(i) == null) {
            Slog.d("GameManagerService", "Game mode " + i + " not found for " + str);
            return;
        }
        try {
            setOverrideFrameRate(this.mPackageManager.getPackageUidAsUser(str, i2), r1.getFps());
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final void updateInterventions(String str, int i, int i2) {
        GamePackageConfiguration config = getConfig(str, i2);
        if (i == 1 || i == 0 || config == null || config.willGamePerformOptimizations(i) || config.getGameModeConfiguration(i) == null) {
            resetFps(str, i2);
            if (config == null) {
                Slog.v("GameManagerService", "Package configuration not found for " + str);
                return;
            }
        } else {
            updateFps(config, str, i, i2);
        }
        updateUseAngle(str, i);
    }

    public void setGameModeConfigOverride(String str, int i, int i2, String str2, String str3) {
        int i3;
        float parseFloat;
        int parseInt;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            i3 = this.mPackageManager.getPackageUidAsUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d("GameManagerService", "Cannot find the UID for package " + str + " under user " + i);
            i3 = -1;
        }
        int i4 = i3;
        GamePackageConfiguration config = getConfig(str, i);
        if (config != null && config.getGameModeConfiguration(i2) != null) {
            GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i2);
            int callingUid = Binder.getCallingUid();
            int gameModeToStatsdGameMode = gameModeToStatsdGameMode(i2);
            float scaling = gameModeConfiguration.getScaling();
            if (str3 == null) {
                parseFloat = gameModeConfiguration.getScaling();
            } else {
                parseFloat = Float.parseFloat(str3);
            }
            int fps = gameModeConfiguration.getFps();
            if (str2 == null) {
                parseInt = gameModeConfiguration.getFps();
            } else {
                parseInt = Integer.parseInt(str2);
            }
            FrameworkStatsLog.write(FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i4, callingUid, gameModeToStatsdGameMode, scaling, parseFloat, fps, parseInt);
        } else {
            FrameworkStatsLog.write(FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i4, Binder.getCallingUid(), gameModeToStatsdGameMode(i2), -1.0f, str3 == null ? -1.0f : Float.parseFloat(str3), 0, str2 == null ? 0 : Integer.parseInt(str2));
        }
        synchronized (this.mLock) {
            if (this.mSettings.containsKey(Integer.valueOf(i))) {
                GameManagerSettings gameManagerSettings = (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                if (configOverride == null) {
                    configOverride = new GamePackageConfiguration(str);
                    gameManagerSettings.setConfigOverride(str, configOverride);
                }
                GamePackageConfiguration.GameModeConfiguration orAddDefaultGameModeConfiguration = configOverride.getOrAddDefaultGameModeConfiguration(i2);
                if (str2 != null) {
                    orAddDefaultGameModeConfiguration.setFpsStr(str2);
                } else {
                    orAddDefaultGameModeConfiguration.setFpsStr("");
                }
                if (str3 != null) {
                    for (int i5 = 1; i5 <= 3; i5++) {
                        if (i5 != i2) {
                            configOverride.getOrAddDefaultGameModeConfiguration(i5).setScaling(Float.parseFloat(str3));
                        }
                    }
                    orAddDefaultGameModeConfiguration.setScaling(Float.parseFloat(str3));
                }
                Slog.i("GameManagerService", "Package Name: " + str + " FPS: " + String.valueOf(orAddDefaultGameModeConfiguration.getFps()) + " Scaling: " + orAddDefaultGameModeConfiguration.getScaling());
                setGameMode(str, i2, i);
            }
        }
    }

    public void resetGameModeConfigOverride(String str, int i, int i2) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (this.mSettings.containsKey(Integer.valueOf(i))) {
                GameManagerSettings gameManagerSettings = (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                if (i2 != -1) {
                    GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                    if (configOverride == null) {
                        return;
                    }
                    if (!bitFieldContainsModeBitmask(configOverride.getAvailableGameModesBitfield(), i2)) {
                        return;
                    }
                    configOverride.removeModeConfig(i2);
                    if (!configOverride.hasActiveGameModeConfig()) {
                        gameManagerSettings.removeConfigOverride(str);
                    }
                } else {
                    gameManagerSettings.removeConfigOverride(str);
                }
                int gameMode = getGameMode(str, i);
                if (gameMode != getNewGameMode(gameMode, getConfig(str, i))) {
                    setGameMode(str, 1, i);
                } else {
                    setGameMode(str, gameMode, i);
                }
            }
        }
    }

    public final int getNewGameMode(int i, GamePackageConfiguration gamePackageConfiguration) {
        if (gamePackageConfiguration == null) {
            return 1;
        }
        if (!bitFieldContainsModeBitmask(gamePackageConfiguration.getAvailableGameModesBitfield() & (~modeToBitmask(0)), i)) {
            i = 1;
        }
        return i;
    }

    public String getInterventionList(String str, int i) {
        checkPermission("android.permission.QUERY_ALL_PACKAGES");
        GamePackageConfiguration config = getConfig(str, i);
        StringBuilder sb = new StringBuilder();
        if (config == null) {
            sb.append("\n No intervention found for package ");
            sb.append(str);
            return sb.toString();
        }
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append(config.toString());
        return sb.toString();
    }

    public void updateConfigsForUser(final int i, boolean z, String... strArr) {
        GamePackageConfiguration gamePackageConfiguration;
        if (z) {
            strArr = (String[]) Arrays.stream(strArr).filter(new Predicate() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$updateConfigsForUser$0;
                    lambda$updateConfigsForUser$0 = GameManagerService.this.lambda$updateConfigsForUser$0(i, (String) obj);
                    return lambda$updateConfigsForUser$0;
                }
            }).toArray(new IntFunction() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    String[] lambda$updateConfigsForUser$1;
                    lambda$updateConfigsForUser$1 = GameManagerService.lambda$updateConfigsForUser$1(i2);
                    return lambda$updateConfigsForUser$1;
                }
            });
        }
        try {
            synchronized (this.mDeviceConfigLock) {
                for (String str : strArr) {
                    GamePackageConfiguration gamePackageConfiguration2 = new GamePackageConfiguration(this.mPackageManager, str, i);
                    if (gamePackageConfiguration2.isActive()) {
                        this.mConfigs.put(str, gamePackageConfiguration2);
                    } else {
                        this.mConfigs.remove(str);
                    }
                }
            }
            synchronized (this.mLock) {
                if (this.mSettings.containsKey(Integer.valueOf(i))) {
                    for (String str2 : strArr) {
                        int gameMode = getGameMode(str2, i);
                        synchronized (this.mDeviceConfigLock) {
                            gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str2);
                        }
                        int newGameMode = getNewGameMode(gameMode, gamePackageConfiguration);
                        if (newGameMode != gameMode) {
                            setGameMode(str2, newGameMode, i);
                        } else {
                            updateInterventions(str2, gameMode, i);
                        }
                    }
                    sendUserMessage(i, 6, "UPDATE_CONFIGS_FOR_USERS", 0);
                }
            }
        } catch (Exception e) {
            Slog.e("GameManagerService", "Failed to update configs for user " + i + ": " + e);
        }
    }

    public static /* synthetic */ String[] lambda$updateConfigsForUser$1(int i) {
        return new String[i];
    }

    public final void writeGameModeInterventionsToFile(int i) {
        int i2 = i;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mGameModeInterventionListFile.startWrite();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.defaultCharset()));
            StringBuilder sb = new StringBuilder();
            for (String str : getInstalledGamePackageNamesByAllUsers(i)) {
                GamePackageConfiguration config = getConfig(str, i2);
                if (config != null) {
                    sb.append(str);
                    sb.append("\t");
                    sb.append(this.mPackageManager.getPackageUidAsUser(str, i2));
                    sb.append("\t");
                    sb.append(getGameMode(str, i2));
                    sb.append("\t");
                    for (int i3 : config.getAvailableGameModes()) {
                        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i3);
                        if (gameModeConfiguration != null) {
                            sb.append(i3);
                            sb.append("\t");
                            sb.append(TextUtils.formatSimple("angle=%d", new Object[]{Integer.valueOf(gameModeConfiguration.getUseAngle() ? 1 : 0)}));
                            sb.append(",");
                            float scaling = gameModeConfiguration.getScaling();
                            sb.append("scaling=");
                            sb.append(scaling);
                            sb.append(",");
                            sb.append(TextUtils.formatSimple("fps=%d", new Object[]{Integer.valueOf(gameModeConfiguration.getFps())}));
                            sb.append("\t");
                        }
                    }
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    i2 = i;
                }
            }
            bufferedWriter.append((CharSequence) sb);
            bufferedWriter.flush();
            FileUtils.sync(fileOutputStream);
            this.mGameModeInterventionListFile.finishWrite(fileOutputStream);
        } catch (Exception e) {
            this.mGameModeInterventionListFile.failWrite(fileOutputStream);
            Slog.wtf("GameManagerService", "Failed to write game_mode_intervention.list, exception " + e);
        }
    }

    public final int[] getAllUserIds(int i) {
        List users = this.mUserManager.getUsers();
        int size = users.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((UserInfo) users.get(i2)).id;
        }
        return i != -1 ? ArrayUtils.appendInt(iArr, i) : iArr;
    }

    public final String[] getInstalledGamePackageNames(int i) {
        return (String[]) this.mPackageManager.getInstalledPackagesAsUser(0, i).stream().filter(new Predicate() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getInstalledGamePackageNames$2;
                lambda$getInstalledGamePackageNames$2 = GameManagerService.lambda$getInstalledGamePackageNames$2((PackageInfo) obj);
                return lambda$getInstalledGamePackageNames$2;
            }
        }).map(new Function() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String str;
                str = ((PackageInfo) obj).packageName;
                return str;
            }
        }).toArray(new IntFunction() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                String[] lambda$getInstalledGamePackageNames$4;
                lambda$getInstalledGamePackageNames$4 = GameManagerService.lambda$getInstalledGamePackageNames$4(i2);
                return lambda$getInstalledGamePackageNames$4;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getInstalledGamePackageNames$2(PackageInfo packageInfo) {
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return applicationInfo != null && applicationInfo.category == 0;
    }

    public static /* synthetic */ String[] lambda$getInstalledGamePackageNames$4(int i) {
        return new String[i];
    }

    public final List getInstalledGamePackageNamesByAllUsers(int i) {
        HashSet hashSet = new HashSet();
        for (int i2 : getAllUserIds(i)) {
            hashSet.addAll(Arrays.asList(getInstalledGamePackageNames(i2)));
        }
        return new ArrayList(hashSet);
    }

    public GamePackageConfiguration getConfig(String str, int i) {
        GamePackageConfiguration gamePackageConfiguration;
        GamePackageConfiguration configOverride;
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str);
        }
        synchronized (this.mLock) {
            configOverride = this.mSettings.containsKey(Integer.valueOf(i)) ? ((GameManagerSettings) this.mSettings.get(Integer.valueOf(i))).getConfigOverride(str) : null;
        }
        if (configOverride == null || gamePackageConfiguration == null) {
            return configOverride == null ? gamePackageConfiguration : configOverride;
        }
        return gamePackageConfiguration.copyAndApplyOverride(configOverride);
    }

    public final void registerPackageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverForAllUsers(new BroadcastReceiver() { // from class: com.android.server.app.GameManagerService.3
            /* JADX WARN: Removed duplicated region for block: B:19:0x0050  */
            /* JADX WARN: Removed duplicated region for block: B:48:0x00af A[Catch: NullPointerException -> 0x00b9, TRY_LEAVE, TryCatch #3 {NullPointerException -> 0x00b9, blocks: (B:3:0x0004, B:7:0x000f, B:9:0x0013, B:12:0x0024, B:22:0x0053, B:24:0x005b, B:25:0x0061, B:29:0x006c, B:30:0x0072, B:42:0x00ab, B:46:0x00ae, B:48:0x00af, B:50:0x0039, B:53:0x0043, B:32:0x0073, B:34:0x0083, B:35:0x0096, B:36:0x00a7, B:27:0x0062, B:28:0x006b), top: B:2:0x0004, inners: #1, #2 }] */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(android.content.Context r7, android.content.Intent r8) {
                /*
                    r6 = this;
                    android.net.Uri r7 = r8.getData()
                    int r0 = r6.getSendingUserId()     // Catch: java.lang.NullPointerException -> Lb9
                    int r1 = android.app.ActivityManager.getCurrentUser()     // Catch: java.lang.NullPointerException -> Lb9
                    if (r0 == r1) goto Lf
                    return
                Lf:
                    java.lang.String r7 = r7.getSchemeSpecificPart()     // Catch: java.lang.NullPointerException -> Lb9
                    com.android.server.app.GameManagerService r1 = com.android.server.app.GameManagerService.this     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L24 java.lang.NullPointerException -> Lb9
                    android.content.pm.PackageManager r1 = com.android.server.app.GameManagerService.m2347$$Nest$fgetmPackageManager(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L24 java.lang.NullPointerException -> Lb9
                    r2 = 131072(0x20000, float:1.83671E-40)
                    android.content.pm.ApplicationInfo r1 = r1.getApplicationInfoAsUser(r7, r2, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L24 java.lang.NullPointerException -> Lb9
                    int r1 = r1.category     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L24 java.lang.NullPointerException -> Lb9
                    if (r1 == 0) goto L24
                    return
                L24:
                    java.lang.String r1 = r8.getAction()     // Catch: java.lang.NullPointerException -> Lb9
                    int r2 = r1.hashCode()     // Catch: java.lang.NullPointerException -> Lb9
                    r3 = 525384130(0x1f50b9c2, float:4.419937E-20)
                    r4 = 0
                    r5 = 1
                    if (r2 == r3) goto L43
                    r3 = 1544582882(0x5c1076e2, float:1.62652439E17)
                    if (r2 == r3) goto L39
                    goto L4d
                L39:
                    java.lang.String r2 = "android.intent.action.PACKAGE_ADDED"
                    boolean r1 = r1.equals(r2)     // Catch: java.lang.NullPointerException -> Lb9
                    if (r1 == 0) goto L4d
                    r1 = r4
                    goto L4e
                L43:
                    java.lang.String r2 = "android.intent.action.PACKAGE_REMOVED"
                    boolean r1 = r1.equals(r2)     // Catch: java.lang.NullPointerException -> Lb9
                    if (r1 == 0) goto L4d
                    r1 = r5
                    goto L4e
                L4d:
                    r1 = -1
                L4e:
                    if (r1 == 0) goto Laf
                    if (r1 == r5) goto L53
                    goto Lc0
                L53:
                    java.lang.String r1 = "android.intent.extra.REPLACING"
                    boolean r8 = r8.getBooleanExtra(r1, r4)     // Catch: java.lang.NullPointerException -> Lb9
                    if (r8 != 0) goto Lc0
                    com.android.server.app.GameManagerService r8 = com.android.server.app.GameManagerService.this     // Catch: java.lang.NullPointerException -> Lb9
                    java.lang.Object r8 = com.android.server.app.GameManagerService.m2344$$Nest$fgetmDeviceConfigLock(r8)     // Catch: java.lang.NullPointerException -> Lb9
                    monitor-enter(r8)     // Catch: java.lang.NullPointerException -> Lb9
                    com.android.server.app.GameManagerService r1 = com.android.server.app.GameManagerService.this     // Catch: java.lang.Throwable -> Lac
                    android.util.ArrayMap r1 = com.android.server.app.GameManagerService.m2342$$Nest$fgetmConfigs(r1)     // Catch: java.lang.Throwable -> Lac
                    r1.remove(r7)     // Catch: java.lang.Throwable -> Lac
                    monitor-exit(r8)     // Catch: java.lang.Throwable -> Lac
                    com.android.server.app.GameManagerService r8 = com.android.server.app.GameManagerService.this     // Catch: java.lang.NullPointerException -> Lb9
                    java.lang.Object r8 = com.android.server.app.GameManagerService.m2346$$Nest$fgetmLock(r8)     // Catch: java.lang.NullPointerException -> Lb9
                    monitor-enter(r8)     // Catch: java.lang.NullPointerException -> Lb9
                    com.android.server.app.GameManagerService r1 = com.android.server.app.GameManagerService.this     // Catch: java.lang.Throwable -> La9
                    android.util.ArrayMap r1 = com.android.server.app.GameManagerService.m2349$$Nest$fgetmSettings(r1)     // Catch: java.lang.Throwable -> La9
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> La9
                    boolean r1 = r1.containsKey(r2)     // Catch: java.lang.Throwable -> La9
                    if (r1 == 0) goto L96
                    com.android.server.app.GameManagerService r1 = com.android.server.app.GameManagerService.this     // Catch: java.lang.Throwable -> La9
                    android.util.ArrayMap r1 = com.android.server.app.GameManagerService.m2349$$Nest$fgetmSettings(r1)     // Catch: java.lang.Throwable -> La9
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> La9
                    java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> La9
                    com.android.server.app.GameManagerSettings r1 = (com.android.server.app.GameManagerSettings) r1     // Catch: java.lang.Throwable -> La9
                    r1.removeGame(r7)     // Catch: java.lang.Throwable -> La9
                L96:
                    com.android.server.app.GameManagerService r7 = com.android.server.app.GameManagerService.this     // Catch: java.lang.Throwable -> La9
                    java.lang.String r1 = "android.intent.action.PACKAGE_REMOVED"
                    r2 = 10000(0x2710, float:1.4013E-41)
                    com.android.server.app.GameManagerService.m2359$$Nest$msendUserMessage(r7, r0, r5, r1, r2)     // Catch: java.lang.Throwable -> La9
                    com.android.server.app.GameManagerService r6 = com.android.server.app.GameManagerService.this     // Catch: java.lang.Throwable -> La9
                    java.lang.String r7 = "android.intent.action.PACKAGE_REMOVED"
                    r1 = 6
                    com.android.server.app.GameManagerService.m2359$$Nest$msendUserMessage(r6, r0, r1, r7, r2)     // Catch: java.lang.Throwable -> La9
                    monitor-exit(r8)     // Catch: java.lang.Throwable -> La9
                    goto Lc0
                La9:
                    r6 = move-exception
                    monitor-exit(r8)     // Catch: java.lang.Throwable -> La9
                    throw r6     // Catch: java.lang.NullPointerException -> Lb9
                Lac:
                    r6 = move-exception
                    monitor-exit(r8)     // Catch: java.lang.Throwable -> Lac
                    throw r6     // Catch: java.lang.NullPointerException -> Lb9
                Laf:
                    com.android.server.app.GameManagerService r6 = com.android.server.app.GameManagerService.this     // Catch: java.lang.NullPointerException -> Lb9
                    java.lang.String[] r7 = new java.lang.String[]{r7}     // Catch: java.lang.NullPointerException -> Lb9
                    r6.updateConfigsForUser(r0, r5, r7)     // Catch: java.lang.NullPointerException -> Lb9
                    goto Lc0
                Lb9:
                    java.lang.String r6 = "GameManagerService"
                    java.lang.String r7 = "Failed to get package name for new package"
                    android.util.Slog.e(r6, r7)
                Lc0:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.app.GameManagerService.AnonymousClass3.onReceive(android.content.Context, android.content.Intent):void");
            }
        }, intentFilter, null, null);
    }

    public final void registerDeviceConfigListener() {
        this.mDeviceConfigListener = new DeviceConfigListener();
    }

    public final void publishLocalService() {
        LocalServices.addService(GameManagerInternal.class, new LocalService());
    }

    public final void registerStatsCallbacks() {
        StatsManager statsManager = (StatsManager) this.mContext.getSystemService(StatsManager.class);
        statsManager.setPullAtomCallback(FrameworkStatsLog.GAME_MODE_INFO, (StatsManager.PullAtomMetadata) null, BackgroundThread.getExecutor(), new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, List list) {
                int onPullAtom;
                onPullAtom = GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
        statsManager.setPullAtomCallback(FrameworkStatsLog.GAME_MODE_CONFIGURATION, (StatsManager.PullAtomMetadata) null, BackgroundThread.getExecutor(), new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, List list) {
                int onPullAtom;
                onPullAtom = GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
        statsManager.setPullAtomCallback(FrameworkStatsLog.GAME_MODE_LISTENER, (StatsManager.PullAtomMetadata) null, BackgroundThread.getExecutor(), new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, List list) {
                int onPullAtom;
                onPullAtom = GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
    }

    public final int onPullAtom(int i, List list) {
        Set<String> keySet;
        int i2;
        if (i == 10165 || i == 10166) {
            int currentUser = ActivityManager.getCurrentUser();
            synchronized (this.mDeviceConfigLock) {
                keySet = this.mConfigs.keySet();
            }
            for (String str : keySet) {
                GamePackageConfiguration config = getConfig(str, currentUser);
                if (config != null) {
                    try {
                        i2 = this.mPackageManager.getPackageUidAsUser(str, currentUser);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.d("GameManagerService", "Cannot find UID for package " + str + " under user handle id " + currentUser);
                        i2 = -1;
                    }
                    if (i == 10165) {
                        list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.GAME_MODE_INFO, i2, gameModesToStatsdGameModes(config.getOverriddenGameModes()), gameModesToStatsdGameModes(config.getAvailableGameModes())));
                    } else if (i == 10166) {
                        for (int i3 : config.getAvailableGameModes()) {
                            GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i3);
                            if (gameModeConfiguration != null) {
                                list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.GAME_MODE_CONFIGURATION, i2, gameModeToStatsdGameMode(i3), gameModeConfiguration.getFps(), gameModeConfiguration.getScaling()));
                            }
                        }
                    }
                }
            }
        } else if (i == 10167) {
            synchronized (this.mGameModeListenerLock) {
                list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.GAME_MODE_LISTENER, this.mGameModeListeners.size()));
            }
        }
        return 0;
    }

    public static int[] gameModesToStatsdGameModes(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] iArr2 = new int[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            iArr2[i2] = gameModeToStatsdGameMode(iArr[i]);
            i++;
            i2++;
        }
        return iArr2;
    }

    public static ServiceThread createServiceThread() {
        ServiceThread serviceThread = new ServiceThread("GameManagerService", 10, true);
        serviceThread.start();
        return serviceThread;
    }

    public void setOverrideFrameRate(int i, float f) {
        nativeSetOverrideFrameRate(i, f);
    }

    /* loaded from: classes.dex */
    public final class MyUidObserver extends UidObserver {
        public MyUidObserver() {
        }

        public void onUidGone(int i, boolean z) {
            synchronized (GameManagerService.this.mUidObserverLock) {
                disableGameMode(i);
            }
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (GameManagerService.this.mUidObserverLock) {
                if (ActivityManager.isProcStateBackground(i2)) {
                    disableGameMode(i);
                    return;
                }
                String[] packagesForUid = GameManagerService.this.mContext.getPackageManager().getPackagesForUid(i);
                if (packagesForUid != null && packagesForUid.length != 0) {
                    final int userId = GameManagerService.this.mContext.getUserId();
                    if (Arrays.stream(packagesForUid).anyMatch(new Predicate() { // from class: com.android.server.app.GameManagerService$MyUidObserver$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$onUidStateChanged$0;
                            lambda$onUidStateChanged$0 = GameManagerService.MyUidObserver.this.lambda$onUidStateChanged$0(userId, (String) obj);
                            return lambda$onUidStateChanged$0;
                        }
                    })) {
                        if (GameManagerService.this.mForegroundGameUids.isEmpty()) {
                            Slog.v("GameManagerService", "Game power mode ON (process state was changed to foreground)");
                            GameManagerService.this.mPowerManagerInternal.setPowerMode(15, true);
                        }
                        GameManagerService.this.mForegroundGameUids.add(Integer.valueOf(i));
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onUidStateChanged$0(int i, String str) {
            return GameManagerService.this.lambda$updateConfigsForUser$0(str, i);
        }

        public final void disableGameMode(int i) {
            synchronized (GameManagerService.this.mUidObserverLock) {
                if (GameManagerService.this.mForegroundGameUids.contains(Integer.valueOf(i))) {
                    GameManagerService.this.mForegroundGameUids.remove(Integer.valueOf(i));
                    if (GameManagerService.this.mForegroundGameUids.isEmpty()) {
                        Slog.v("GameManagerService", "Game power mode OFF (process remomved or state changed to background)");
                        GameManagerService.this.mPowerManagerInternal.setPowerMode(15, false);
                    }
                }
            }
        }
    }
}

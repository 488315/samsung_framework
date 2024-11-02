package com.android.systemui.cover;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.TaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.display.VirtualDisplay;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.cover.CoverWindowDelegate;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.cover.PluginDisplayCover;
import com.android.systemui.plugins.cover.PluginViewCover;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.cover.CoverState;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CoverScreenManager implements PluginListener, ScreenLifecycle.Observer, WakefulnessLifecycle.Observer, Dumpable {
    public CoverHomeActivity mActivity;
    public final DelayableExecutor mBackgroundExecutor;
    public final Context mContext;
    public final CoverHost mCoverHost;
    public PluginCover mCoverPlugin;
    public CoverState mCoverState;
    public CoverWindowDelegate mCoverWindowDelegate;
    public final DisplayWindowListenerImpl mDisplayContainerListener;
    public final DisplayManager mDisplayManager;
    public final DumpManager mDumpManager;
    public final Lazy mFaceWidgetManagerLazy;
    public IDisplayManager mIDisplayManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public IRefreshRateToken mMaxRefreshRateToken;
    public final Lazy mPluginAODManagerLazy;
    public Context mPluginContext;
    public final PluginManager mPluginManager;
    public final PowerManager mPowerManager;
    public ScreenLifecycle mScreenLifecycle;
    public final SettingsHelper mSettingsHelper;
    public VirtualDisplay mVirtualDisplay;
    public Rect mVisibleRect;
    public WakefulnessLifecycle mWakefulnessLifecycle;
    public boolean mIsAttached = false;
    public boolean mIsStarted = false;
    public boolean mIsPluginListenerAdded = false;
    public final ConcurrentHashMap mSubRoomMap = new ConcurrentHashMap();
    public final IBinder mRefreshRateToken = new Binder();
    public final CoverScreenManager$$ExternalSyntheticLambda0 mPluginConnectionRunnable = new CoverScreenManager$$ExternalSyntheticLambda0(this, 1);
    public final KeyguardUpdateMonitorCallback mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.cover.CoverScreenManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockDisabledChanged(boolean z) {
            CoverScreenManager coverScreenManager = CoverScreenManager.this;
            CoverState coverState = coverScreenManager.mCoverState;
            if (coverState != null && coverState.getAttachState() && CoverScreenManager.isUseDisplayCoverPlugin(coverScreenManager.mCoverState)) {
                PluginCover pluginCover = coverScreenManager.mCoverPlugin;
                if (pluginCover == null) {
                    Log.w("CoverScreenManager", "onLockDisabledChanged() no plugin");
                } else if (pluginCover instanceof PluginDisplayCover) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onLockDisabledChanged() ", z, "CoverScreenManager");
                    ((PluginDisplayCover) coverScreenManager.mCoverPlugin).onLockDisabledChanged(z);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onUserSwitchComplete() ", i, ", mCoverState = ");
            CoverScreenManager coverScreenManager = CoverScreenManager.this;
            m.append(coverScreenManager.mCoverState);
            Log.d("CoverScreenManager", m.toString());
            CoverState coverState = coverScreenManager.mCoverState;
            if (coverState != null && coverState.getAttachState()) {
                if (CoverScreenManager.isUseCoverPlugin(coverScreenManager.mCoverState) || CoverScreenManager.isUseDisplayCoverPlugin(coverScreenManager.mCoverState)) {
                    coverScreenManager.updatePluginListener();
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            CoverScreenManager coverScreenManager = CoverScreenManager.this;
            CoverState coverState = coverScreenManager.mCoverState;
            if (coverState != null && coverState.getAttachState() && CoverScreenManager.isUseDisplayCoverPlugin(coverScreenManager.mCoverState)) {
                PluginCover pluginCover = coverScreenManager.mCoverPlugin;
                if (pluginCover == null) {
                    Log.w("CoverScreenManager", "onUserUnlocked() no plugin");
                } else if (pluginCover instanceof PluginDisplayCover) {
                    Log.d("CoverScreenManager", "onUserUnlocked() ");
                    ((PluginDisplayCover) coverScreenManager.mCoverPlugin).onUserUnlocked();
                }
            }
        }
    };
    public final AnonymousClass2 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.cover.CoverScreenManager.2
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            CoverWindowDelegate coverWindowDelegate;
            CoverWindowDelegate.ViewCoverDecorView viewCoverDecorView;
            Rect rect;
            WindowManager.LayoutParams layoutParams;
            int i = message.what;
            if (i != 1000) {
                if (i != 1001) {
                    if (i == 2000 && (coverWindowDelegate = CoverScreenManager.this.mCoverWindowDelegate) != null && (viewCoverDecorView = coverWindowDelegate.mDecorView) != null && (rect = coverWindowDelegate.mWindowRect) != null && (layoutParams = (WindowManager.LayoutParams) viewCoverDecorView.getLayoutParams()) != null) {
                        if (layoutParams.width != rect.width() || layoutParams.height != rect.height()) {
                            layoutParams.width = rect.width();
                            layoutParams.height = rect.height();
                            layoutParams.flags = (layoutParams.flags & (-9)) | 131072;
                            layoutParams.samsungFlags |= 262144;
                            coverWindowDelegate.mWindowManager.updateViewLayout(coverWindowDelegate.mDecorView, layoutParams);
                            return;
                        }
                        return;
                    }
                    return;
                }
                CoverScreenManager coverScreenManager = CoverScreenManager.this;
                CoverState coverState = coverScreenManager.mCoverState;
                if (coverState != null) {
                    coverScreenManager.initialize(coverState);
                    return;
                }
                return;
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                CoverScreenManager coverScreenManager2 = CoverScreenManager.this;
                if (coverScreenManager2.mVirtualDisplay == null) {
                    coverScreenManager2.prepareVirtualDisplay();
                }
                CoverScreenManager coverScreenManager3 = CoverScreenManager.this;
                if (coverScreenManager3.mVirtualDisplay == null) {
                    coverScreenManager3.mHandler.sendEmptyMessageDelayed(1000, 300L);
                }
            }
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class DisplayWindowListenerImpl extends IDisplayWindowListener.Stub {
        public /* synthetic */ DisplayWindowListenerImpl(CoverScreenManager coverScreenManager, int i) {
            this();
        }

        public final void onDisplayAdded(int i) {
            if (i == 4) {
                post(new CoverScreenManager$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 0));
            }
        }

        public final void onDisplayRemoved(int i) {
            if (i == 4) {
                post(new CoverScreenManager$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 1));
            }
        }

        private DisplayWindowListenerImpl() {
        }

        public final void onFixedRotationFinished(int i) {
        }

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        }

        public final void onFixedRotationStarted(int i, int i2) {
        }

        public final void onKeepClearAreasChanged(int i, List list, List list2) {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.cover.CoverScreenManager$2] */
    public CoverScreenManager(Context context, CoverHost coverHost, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, Lazy lazy2, PluginManager pluginManager, DisplayManager displayManager, DelayableExecutor delayableExecutor, PowerManager powerManager, SettingsHelper settingsHelper, DumpManager dumpManager) {
        this.mContext = context;
        this.mCoverHost = coverHost;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPluginAODManagerLazy = lazy;
        this.mFaceWidgetManagerLazy = lazy2;
        this.mPluginManager = pluginManager;
        this.mDisplayManager = displayManager;
        this.mBackgroundExecutor = delayableExecutor;
        this.mPowerManager = powerManager;
        this.mSettingsHelper = settingsHelper;
        this.mDumpManager = dumpManager;
        new Configuration(context.getResources().getConfiguration());
        this.mDisplayContainerListener = new DisplayWindowListenerImpl(this, 0);
    }

    public static boolean isUseCoverPlugin(CoverState coverState) {
        if (LsRune.COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER && 15 == coverState.type) {
            return false;
        }
        int i = coverState.type;
        if (i != 1 && i != 8 && i != 11 && i != 15 && i != 16) {
            return false;
        }
        return true;
    }

    public static boolean isUseDisplayCoverPlugin(CoverState coverState) {
        if ((LsRune.COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER && 15 == coverState.type) || coverState.type == 17) {
            return true;
        }
        return false;
    }

    public final void addPluginListener(CoverState coverState) {
        if (this.mIsPluginListenerAdded) {
            return;
        }
        if (isUseCoverPlugin(coverState) || isUseDisplayCoverPlugin(coverState)) {
            if (isUseCoverPlugin(coverState)) {
                this.mPluginManager.addPluginListener(PluginViewCover.ACTION, this, PluginViewCover.class, false, true, 0);
            } else {
                VirtualDisplay virtualDisplay = this.mVirtualDisplay;
                if (virtualDisplay == null) {
                    Log.w("CoverScreenManager", "addPluginListener fail. wait virtual display");
                    return;
                } else {
                    if (this.mActivity == null) {
                        Log.w("CoverScreenManager", "addPluginListener fail. wait activity created");
                        return;
                    }
                    this.mPluginManager.addPluginListener(PluginDisplayCover.ACTION, this, PluginDisplayCover.class, false, true, virtualDisplay.getDisplay().getDisplayId());
                }
            }
            Log.d("CoverScreenManager", "addPluginListener()");
            this.mIsPluginListenerAdded = true;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sb = new StringBuilder("  mCoverState = ");
        CoverState coverState = this.mCoverState;
        Object obj = "null";
        if (coverState == null) {
            coverState = "null";
        }
        sb.append(coverState);
        printWriter.println(sb.toString());
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsPluginConnected = "), this.mIsPluginListenerAdded, printWriter, "  mCoverPlugin = ");
        Object obj2 = this.mCoverPlugin;
        if (obj2 == null) {
            obj2 = "null";
        }
        m.append(obj2);
        printWriter.println(m.toString());
        StringBuilder sb2 = new StringBuilder("  mVirtualDisplay = ");
        Object obj3 = this.mVirtualDisplay;
        if (obj3 == null) {
            obj3 = "null";
        }
        sb2.append(obj3);
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder("  mActivity = ");
        Object obj4 = this.mActivity;
        if (obj4 != null) {
            obj = obj4;
        }
        sb3.append(obj);
        printWriter.println(sb3.toString());
        printWriter.println("  mRoomMap = " + this.mSubRoomMap.toString());
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.dump(null, printWriter, strArr);
        }
    }

    public final void initialize(CoverState coverState) {
        if (isUseCoverPlugin(coverState)) {
            if (this.mCoverWindowDelegate == null) {
                this.mCoverWindowDelegate = new CoverWindowDelegate(this.mContext, null);
            }
            requestPluginConnection(coverState);
        } else if (isUseDisplayCoverPlugin(coverState)) {
            prepareVirtualDisplay();
            VirtualDisplay virtualDisplay = this.mVirtualDisplay;
            if (virtualDisplay != null) {
                startCoverHomeActivity(virtualDisplay.getDisplay());
                this.mDumpManager.registerNsDumpable("CoverScreenManager", this);
            } else {
                sendEmptyMessageDelayed(1000, 300L);
            }
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        if (plugin != null && context != null) {
            boolean z = plugin instanceof PluginViewCover;
            if (z) {
                this.mCoverPlugin = (PluginViewCover) plugin;
            } else if (plugin instanceof PluginDisplayCover) {
                this.mCoverPlugin = (PluginDisplayCover) plugin;
            } else {
                Log.d("CoverScreenManager", "onPluginConnected fail by wrong instance ");
                return;
            }
            Log.d("CoverScreenManager", "onPluginConnected() ");
            this.mPluginContext = context;
            if (z) {
                startCover();
            } else if ((plugin instanceof PluginDisplayCover) && this.mActivity != null) {
                startCover();
            }
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        CoverWindowDelegate coverWindowDelegate;
        Log.d("CoverScreenManager", "onPluginDisconnected() ");
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setCoverPlugin(this.mPluginContext, null);
        Log.d("CoverScreenManager", "stopCover:");
        this.mIsStarted = false;
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onCoverDetached();
            this.mCoverPlugin = null;
        }
        if (!this.mIsAttached) {
            CoverWindowDelegate coverWindowDelegate2 = this.mCoverWindowDelegate;
            if (coverWindowDelegate2 != null) {
                coverWindowDelegate2.detach();
                this.mCoverWindowDelegate = null;
                return;
            }
            return;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY && (coverWindowDelegate = this.mCoverWindowDelegate) != null) {
            coverWindowDelegate.minimize();
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenInternalTurningOff() {
        Log.i("CoverScreenManager", "onScreenInternalTurningOff() ");
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onScreenInternalTurningOff();
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenInternalTurningOn() {
        Log.i("CoverScreenManager", "onScreenInternalTurningOn() ");
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onScreenInternalTurningOn();
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOff() {
        Log.d("CoverScreenManager", "onScreenTurnedOff() ");
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onScreenTurnedOff();
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurningOn() {
        Log.d("CoverScreenManager", "onScreenTurningOn() ");
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onScreenTurningOn();
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        Log.d("CoverScreenManager", "onScreenTurningOn() ");
        PluginCover pluginCover = this.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onStartedWakingUp();
        }
    }

    public final boolean prepareCoverHomeActivity() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).getRunningTasks(1);
        if (!runningTasks.isEmpty()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
            ComponentName componentName = ((TaskInfo) runningTaskInfo).topActivity;
            if (componentName != null) {
                if ("com.android.systemui.cover.CoverHomeActivity".equals(componentName.getClassName())) {
                    Log.i("CoverScreenManager", "ignore startCoverHomeActivity cause already display");
                    return true;
                }
                if ("com.samsung.android.spay".equals(((TaskInfo) runningTaskInfo).topActivity.getPackageName())) {
                    Log.i("CoverScreenManager", "ignore startCoverHomeActivity cause samsung pay");
                    return false;
                }
                if ("com.skt.prod.dialer".equals(((TaskInfo) runningTaskInfo).topActivity.getPackageName())) {
                    Log.i("CoverScreenManager", "ignore startCoverHomeActivity by T-CALL");
                    return false;
                }
            }
        }
        this.mBackgroundExecutor.executeDelayed(100L, new CoverScreenManager$$ExternalSyntheticLambda0(this, 0));
        return true;
    }

    public final void prepareCoverWindow() {
        CoverState coverState;
        if (this.mCoverPlugin != null && (coverState = this.mCoverState) != null && this.mVirtualDisplay != null) {
            boolean switchState = coverState.getSwitchState();
            AnonymousClass2 anonymousClass2 = this.mHandler;
            if (!switchState) {
                this.mVirtualDisplay.setDisplayState(true);
                anonymousClass2.removeMessages(2000);
                if (prepareCoverHomeActivity()) {
                    anonymousClass2.sendEmptyMessageDelayed(2000, 100L);
                } else {
                    anonymousClass2.sendEmptyMessageDelayed(2000, 300L);
                }
                if (this.mWakefulnessLifecycle.mWakefulness == 2) {
                    this.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
                    this.mCoverPlugin.onStartedWakingUp();
                }
                if (LsRune.COVER_ADJUST_REFRESH_RATE) {
                    updateRefreshRate(true);
                    return;
                }
                return;
            }
            this.mVirtualDisplay.setDisplayState(false);
            anonymousClass2.removeMessages(2000);
            CoverWindowDelegate coverWindowDelegate = this.mCoverWindowDelegate;
            if (coverWindowDelegate != null) {
                coverWindowDelegate.minimize();
            }
            if (LsRune.COVER_ADJUST_REFRESH_RATE) {
                updateRefreshRate(false);
            }
        }
    }

    public final void prepareVirtualDisplay() {
        int i;
        SurfaceControl surfaceControl;
        if (this.mVirtualDisplay == null) {
            Point point = new Point();
            SemWindowManager.getInstance().getUserDisplaySize(point);
            int i2 = point.x;
            if (i2 < 1080) {
                i = 320;
            } else if (i2 < 1440) {
                i = VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE;
            } else {
                i = 640;
            }
            int i3 = i;
            if (!this.mCoverState.getVisibleRect().isEmpty() && !LsRune.COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER) {
                this.mVisibleRect = new Rect(this.mCoverState.getVisibleRect());
                Log.d("CoverScreenManager", "cover mVisibleRect=" + this.mVisibleRect + " density=" + i3);
            } else {
                Point point2 = new Point();
                SemWindowManager.getInstance().getUserDisplaySize(point2);
                int i4 = (int) (point2.x * 0.518d);
                int i5 = (int) (point2.y * 0.008d);
                this.mVisibleRect = new Rect(i4, i5, ((int) (point2.x * 0.46d)) + i4, ((int) (point2.y * 0.312d)) + i5);
                Log.d("CoverScreenManager", "point=" + point2 + "  cover mVisibleRect=" + this.mVisibleRect + " density=" + i3);
            }
            if (this.mCoverWindowDelegate == null) {
                this.mCoverWindowDelegate = new CoverWindowDelegate(this.mContext, this.mVisibleRect);
            }
            ViewRootImpl viewRootImpl = this.mCoverWindowDelegate.attach().getViewRootImpl();
            if (viewRootImpl != null) {
                surfaceControl = viewRootImpl.getSurfaceControl();
                if (surfaceControl != null && surfaceControl.isValid()) {
                    surfaceControl.resize(this.mVisibleRect.width(), this.mVisibleRect.height());
                }
            } else {
                surfaceControl = null;
            }
            synchronized (this) {
                if (surfaceControl != null) {
                    if (surfaceControl.isValid()) {
                        this.mVirtualDisplay = this.mDisplayManager.createVirtualDisplay("Cover-Virtual-Display", this.mVisibleRect.width(), this.mVisibleRect.height(), i3, new Surface(surfaceControl), 1073743371);
                        Log.d("CoverScreenManager", " prepareVirtualDisplay mVirtualDisplay=" + this.mVirtualDisplay.getDisplay());
                        this.mCoverWindowDelegate.mCoverDisplay = this.mVirtualDisplay.getDisplay();
                    }
                }
            }
        }
    }

    public final void removePluginListener() {
        if (!this.mIsPluginListenerAdded) {
            return;
        }
        Log.d("CoverScreenManager", "removePluginListener() ");
        this.mPluginManager.removePluginListener(this);
        this.mIsPluginListenerAdded = false;
    }

    public final void removeVirtualDisplay() {
        synchronized (this) {
            if (this.mVirtualDisplay != null) {
                Log.d("CoverScreenManager", "removeVirtualDisplay() ");
                this.mVirtualDisplay.release();
                this.mVirtualDisplay = null;
                this.mCoverWindowDelegate.mCoverDisplay = null;
            }
        }
    }

    public final void requestPluginConnection(CoverState coverState) {
        boolean z;
        Log.d("CoverScreenManager", "requestPluginListener()");
        if (((PluginFaceWidgetManager) this.mFaceWidgetManagerLazy.get()).mFaceWidgetPlugin != null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            addPluginListener(coverState);
        } else {
            Log.w("CoverScreenManager", "addPluginListener() PluginFaceWidget is not connected, wait connection");
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).addConnectionRunnable(this.mPluginConnectionRunnable);
        }
    }

    public final void startCover() {
        boolean z;
        WindowManager.LayoutParams layoutParams;
        WindowInsetsController windowInsetsController;
        if (this.mIsStarted) {
            return;
        }
        if (this.mCoverPlugin == null) {
            Log.w("CoverScreenManager", "startCover fail.  plugin is null.");
            return;
        }
        if (this.mCoverWindowDelegate == null) {
            Log.w("CoverScreenManager", "startCover fail. cover window is null.");
            return;
        }
        if (this.mCoverState != null && this.mIsAttached) {
            Log.d("CoverScreenManager", "startCover:");
            CoverWindowDelegate.ViewCoverDecorView attach = this.mCoverWindowDelegate.attach();
            if (attach != null && (windowInsetsController = attach.getWindowInsetsController()) != null) {
                int navigationBars = WindowInsets.Type.navigationBars();
                if (this.mCoverState.getType() == 16) {
                    navigationBars |= WindowInsets.Type.statusBars();
                }
                Log.d("CoverScreenManager", "startCover() hide systemBars - " + navigationBars);
                windowInsetsController.hide(navigationBars);
            }
            PluginCover pluginCover = this.mCoverPlugin;
            if (pluginCover instanceof PluginViewCover) {
                pluginCover.onCoverAttached(attach, this.mCoverState);
            } else if (pluginCover instanceof PluginDisplayCover) {
                Window window = this.mActivity.getWindow();
                this.mPluginContext.getResources().getConfiguration().updateFrom(window.getDecorView().getResources().getConfiguration());
                this.mCoverPlugin.onCoverAttached(window, this.mCoverState);
            }
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).setCoverPlugin(this.mPluginContext, this.mCoverPlugin);
            this.mCoverPlugin.onCoverStateUpdated(this.mCoverState);
            if (LsRune.COVER_VIRTUAL_DISPLAY && !this.mCoverState.getSwitchState()) {
                CoverWindowDelegate.ViewCoverDecorView viewCoverDecorView = this.mCoverWindowDelegate.mDecorView;
                if (viewCoverDecorView != null && (layoutParams = (WindowManager.LayoutParams) viewCoverDecorView.getLayoutParams()) != null && (layoutParams.width == 1 || layoutParams.height == 1)) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    prepareCoverWindow();
                }
            }
            this.mIsStarted = true;
        }
    }

    public final void startCoverHomeActivity(Display display) {
        Log.d("CoverScreenManager", "startCoverHomeActivity() " + display);
        Intent intent = new Intent();
        intent.addFlags(268500992);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.SECONDARY_HOME");
        intent.setClassName("com.android.systemui", "com.android.systemui.cover.CoverHomeActivity");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(display.getDisplayId());
        makeBasic.setForceLaunchWindowingMode(1);
        try {
            this.mContext.startActivity(intent, makeBasic.toBundle());
        } catch (ActivityNotFoundException e) {
            Log.w("CoverScreenManager", "startCoverHomeActivity() " + e);
        }
    }

    public final void updatePluginListener() {
        Log.d("CoverScreenManager", "updatePluginListener() ");
        removePluginListener();
        if (this.mIsAttached) {
            requestPluginConnection(this.mCoverState);
        }
    }

    public final void updateRefreshRate(boolean z) {
        int refreshRateMode = this.mSettingsHelper.getRefreshRateMode(false);
        if ((refreshRateMode == 1 || refreshRateMode == 2) && z) {
            if (this.mMaxRefreshRateToken == null) {
                if (this.mIDisplayManager == null) {
                    this.mIDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                }
                IDisplayManager iDisplayManager = this.mIDisplayManager;
                if (iDisplayManager != null) {
                    try {
                        this.mMaxRefreshRateToken = iDisplayManager.acquireRefreshRateMaxLimitToken(this.mRefreshRateToken, 60, "CoverScreenManager");
                        Log.d("CoverScreenManager", "updateRefreshRate enabled");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (this.mMaxRefreshRateToken == null) {
                Log.w("CoverScreenManager", "updateRefreshRate failed");
                return;
            }
            return;
        }
        IRefreshRateToken iRefreshRateToken = this.mMaxRefreshRateToken;
        if (iRefreshRateToken != null) {
            try {
                iRefreshRateToken.release();
                Log.d("CoverScreenManager", "updateRefreshRate disabled");
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            this.mMaxRefreshRateToken = null;
        }
    }
}

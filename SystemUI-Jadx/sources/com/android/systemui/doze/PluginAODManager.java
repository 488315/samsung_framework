package com.android.systemui.doze;

import android.app.Notification;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.blur.SecCoverBlurController;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginControllerImpl;
import com.android.systemui.facewidget.plugin.FaceWidgetWallpaperUtilsWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.AODDumpLog;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.SecLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.media.SubscreenMusicWidgetController;
import com.android.systemui.media.SubscreenMusicWidgetSubroom;
import com.android.systemui.pluginlock.PluginLockDelegateApp;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.aod.PluginAODSystemUIConfiguration;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.keyguardstatusview.PluginAODStateProvider;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda1;
import com.android.systemui.subscreen.SubScreenQSEventHandler;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class PluginAODManager implements Dumpable, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public AODMachine mAODMachine;
    public AODOverlayContainer mAODOverlayContainer;
    public PluginAOD mAODPlugin;
    public final AODTouchModeManager mAODTouchModeManager;
    public PluginClockPack mClockPackPlugin;
    public boolean mClockTransitionStarted;
    public final Lazy mCommonNotifCollectionLazy;
    public final Context mContext;
    public boolean mControlScreenOff;
    public PluginCover mCoverPlugin;
    public Context mCoverPluginContext;
    public final Lazy mCoverScreenManagerLazy;
    public final DisplayLifecycle mDisplayLifeCycle;
    public IDisplayManager mDisplayManager;
    public final DozeParameters mDozeParameters;
    public final DozeServiceHost mDozeServiceHost;
    public boolean mDozing;
    public EngineeringModeManagerWrapper mEmm;
    public final FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final KeyguardFoldController mFoldController;
    public final AnonymousClass9 mHandler;
    public boolean mIsDifferentOrientation;
    public final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewController mKeyguardViewController;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
    public final LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationsController mNotificationsController;
    public IRefreshRateToken mPassiveModeToken;
    public PluginAODStateProvider mPluginAODStateProvider;
    public final PluginLockMediator mPluginLockMediator;
    public final PluginLockStarManager mPluginLockStarManager;
    public boolean mStartedByFolderClosed;
    public StatusBarManager mStatusBarManager;
    public final SubScreenManager mSubScreenManager;
    public PluginSubScreen mSubScreenPlugin;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public int mWakefulness;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final FaceWidgetWallpaperUtilsWrapper mWallpaperUtilsWrapper;
    public final PluginAODSystemUIConfiguration mSysUIConfig = new PluginAODSystemUIConfiguration();
    public final FaceWidgetNotificationController mFaceWidgetNotiController = ((FaceWidgetPluginControllerImpl) Dependency.get(FaceWidgetPluginControllerImpl.class)).mNotificationManager;
    public final ArrayList mSmartAlerts = new ArrayList();
    public final ConcurrentHashMap mNotiIconMap = new ConcurrentHashMap();
    public boolean mShowAODNotifications = true;
    public Point mZigzagPosition = new Point();
    public List mConnectionRunnableList = null;
    public final IBinder mDisplayStateLock = new Binder();
    public final IBinder mToken = new Binder();
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.doze.PluginAODManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPhoneStateChanged(int i) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mSysUIConfig.get(2, 0) != i) {
                pluginAODManager.mSysUIConfig.set(2, i);
                PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                if (pluginAOD != null) {
                    pluginAOD.onSystemUIConfigurationChanged(pluginAODManager.mSysUIConfig);
                    return;
                }
                PluginClockPack pluginClockPack = pluginAODManager.mClockPackPlugin;
                if (pluginClockPack != null) {
                    pluginClockPack.onSystemUIConfigurationChanged(pluginAODManager.mSysUIConfig);
                }
            }
        }
    };
    public final AnonymousClass2 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.doze.PluginAODManager.2
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            PluginAOD pluginAOD = PluginAODManager.this.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.onFinishedGoingToSleep();
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                PluginAODManager pluginAODManager = PluginAODManager.this;
                if (pluginAODManager.mKeyguardUpdateMonitor.isCoverClosed()) {
                    ((CoverScreenManager) pluginAODManager.mCoverScreenManagerLazy.get()).prepareCoverHomeActivity();
                }
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            boolean z;
            Log.d("PluginAODManager", "onStartedWakingUp");
            int i = PluginAODManager.this.mWakefulnessLifecycle.mLastWakeReason;
            ListPopupWindow$$ExternalSyntheticOutline0.m("onStartedWakingUp why=", i, "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.getClass();
            if (i != 0 && i != 2 && i != 110) {
                Log.d("PluginAODManager", "clearNotiMapIfNeeded() clear");
                pluginAODManager.mSysUIConfig.setNotiMap(null);
            }
            if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
                WakefulnessLifecycle wakefulnessLifecycle = PluginAODManager.this.mWakefulnessLifecycle;
                synchronized (wakefulnessLifecycle.mMsgForLifecycle) {
                    Optional findFirst = wakefulnessLifecycle.mMsgForLifecycle.stream().filter(new Predicate() { // from class: com.android.systemui.keyguard.SecLifecycle$$ExternalSyntheticLambda1
                        public final /* synthetic */ int f$0 = 3;
                        public final /* synthetic */ int f$1 = 3;

                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i2 = this.f$0;
                            int i3 = this.f$1;
                            SecLifecycle.Msg msg = (SecLifecycle.Msg) obj;
                            if (msg.msg == i2 && msg.reason == i3) {
                                return true;
                            }
                            return false;
                        }
                    }).findFirst();
                    if (findFirst != null && findFirst.isPresent()) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                if (!z) {
                    PluginAODManager.this.setStartedByFolderClosed(false);
                }
            }
        }
    };
    public final AnonymousClass3 mNotificationCallback = new PluginAODNotificationManager.Callback() { // from class: com.android.systemui.doze.PluginAODManager.3
        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void animateExpandLockedShadePanel(StatusBarNotification statusBarNotification) {
            StatusBarNotification clone;
            Log.d("PluginAODManager", "animateExpandLockedShadePanel() sbn=" + statusBarNotification);
            PluginAODManager pluginAODManager = PluginAODManager.this;
            boolean hasMessages = pluginAODManager.mHandler.hasMessages(1000);
            AnonymousClass9 anonymousClass9 = pluginAODManager.mHandler;
            if (hasMessages) {
                anonymousClass9.removeMessages(1000);
            }
            Message obtainMessage = anonymousClass9.obtainMessage(1000);
            if (statusBarNotification == null) {
                clone = null;
            } else {
                clone = statusBarNotification.clone();
            }
            obtainMessage.obj = clone;
            anonymousClass9.sendMessageDelayed(obtainMessage, 300L);
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void clickNotification(String str) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("clickNotification() ", str, "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) pluginAODManager.mCommonNotifCollectionLazy.get())).getEntry(str);
            if (entry != null) {
                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                NotificationVisibility obtain = NotificationVisibility.obtain(str, entry.mRanking.getRank(), pluginAODManager.mNotificationsController.getActiveNotificationsCount(), true);
                try {
                    if (asInterface != null) {
                        asInterface.onNotificationClick(str, obtain);
                    } else {
                        Log.e("PluginAODManager", "can't get STATUS_BAR_SERVICE");
                    }
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final Icon getNotificationIcon(String str) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            ConcurrentHashMap concurrentHashMap = pluginAODManager.mNotiIconMap;
            if (concurrentHashMap != null && !concurrentHashMap.isEmpty()) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getNotificationIcon() ", str, "PluginAODManager");
                return (Icon) pluginAODManager.mNotiIconMap.get(str);
            }
            return null;
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void requestActiveNotifications() {
            Collection allNotifs = ((NotifPipeline) ((CommonNotifCollection) PluginAODManager.this.mCommonNotifCollectionLazy.get())).getAllNotifs();
            Log.d("PluginAODManager", "requestActiveNotifications() size = " + allNotifs.size());
            ArrayList arrayList = new ArrayList();
            Iterator it = allNotifs.iterator();
            while (it.hasNext()) {
                arrayList.add(((NotificationEntry) it.next()).mSbn);
            }
            PluginAODManager pluginAODManager = PluginAODManager.this;
            synchronized (pluginAODManager.mSmartAlerts) {
                try {
                    pluginAODManager.mSmartAlerts.clear();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        StatusBarNotification statusBarNotification = (StatusBarNotification) it2.next();
                        if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                            pluginAODManager.mSmartAlerts.add(statusBarNotification);
                        }
                    }
                    if (pluginAODManager.mSmartAlerts.size() > 0) {
                        pluginAODManager.logSmartAlert("updateActiveNotifications");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateActiveNotifications(arrayList);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().updateActiveNotifications(arrayList);
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void requestVisibleNotifications() {
            Log.d("PluginAODManager", "requestVisibleNotifications()");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.mLockscreenNotificationIconsOnlyController.init();
            LockscreenNotificationManager lockscreenNotificationManager = pluginAODManager.mLockscreenNotificationManager;
            lockscreenNotificationManager.getClass();
            Log.i("LockscreenNotificationManager", "refreshLockScreenNotifications: AOD_REQUEST_NOTIFICATIONS");
            LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = lockscreenNotificationManager.mLockScreenNotificationStateListener;
            if (lockScreenNotiIconCoordinator != null) {
                lockScreenNotiIconCoordinator.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void showSubScreenNotification(String str) {
        }
    };
    public final AnonymousClass4 mLockscreenNotiCallback = new LockscreenNotificationManager.Callback() { // from class: com.android.systemui.doze.PluginAODManager.4
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4, types: [int] */
        /* JADX WARN: Type inference failed for: r14v5 */
        /* JADX WARN: Type inference failed for: r14v6 */
        /* JADX WARN: Type inference failed for: r14v7 */
        /* JADX WARN: Type inference failed for: r5v18, types: [java.lang.StringBuilder] */
        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationInfoUpdated(ArrayList arrayList) {
            ?? r14;
            boolean z;
            boolean z2;
            boolean z3;
            StatusBarIconView statusBarIconView;
            Log.d("PluginAODManager", "onNotificationInfoUpdated() " + arrayList.size());
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.mNotiIconMap.clear();
            StringBuilder sb = new StringBuilder();
            boolean z4 = pluginAODManager.mShowAODNotifications;
            FaceWidgetNotificationController faceWidgetNotificationController = pluginAODManager.mFaceWidgetNotiController;
            PluginNotificationController pluginNotificationController = ((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.isMusicFaceWidgetOn();
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                LockscreenNotificationInfo lockscreenNotificationInfo = (LockscreenNotificationInfo) it.next();
                if (lockscreenNotificationInfo != null) {
                    StatusBarNotification statusBarNotification = lockscreenNotificationInfo.mSbn;
                    statusBarNotification.getKey();
                    faceWidgetNotificationController.getClass();
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) pluginAODManager.mCommonNotifCollectionLazy.get())).getEntry(lockscreenNotificationInfo.mKey);
                    if (entry == null) {
                        Log.i("PluginAODManager", "onNotificationInfoUpdated : can not find " + lockscreenNotificationInfo.mKey);
                    } else {
                        if (z4) {
                            r14 = 2;
                        } else {
                            r14 = z4;
                        }
                        if (z4 && ((NotificationLockscreenUserManagerImpl) pluginAODManager.mNotificationLockscreenUserManager).mShowLockscreenNotifications) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            r14 = 3;
                        }
                        if (z && !((KeyguardNotificationVisibilityProviderImpl) pluginAODManager.mKeyguardNotificationVisibilityProvider).shouldHideNotification(entry)) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            r14 = 4;
                        }
                        if (z4 && !z2) {
                            z3 = z4;
                            sb.append("[" + statusBarNotification.getKey() + "$" + r14 + "]");
                        } else {
                            z3 = z4;
                        }
                        if (z2) {
                            Log.d("PluginAODManager", "onNotificationInfoUpdated() " + lockscreenNotificationInfo.mKey);
                            arrayList2.add(statusBarNotification);
                            String str = lockscreenNotificationInfo.mKey;
                            ConcurrentHashMap concurrentHashMap = pluginAODManager.mNotiIconMap;
                            if (concurrentHashMap != null && (statusBarIconView = lockscreenNotificationInfo.mStatusBarIcon) != null) {
                                concurrentHashMap.putIfAbsent(str, statusBarIconView.mIcon.icon);
                            }
                            arrayList3.add(statusBarNotification);
                            if (statusBarNotification.getNotification().isGroupSummary()) {
                                try {
                                    Iterator it2 = entry.row.getAttachedChildren().iterator();
                                    while (it2.hasNext()) {
                                        arrayList3.add(((ExpandableNotificationRow) it2.next()).mEntry.mSbn);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        z4 = z3;
                    }
                }
            }
            if (sb.length() > 0) {
                String str2 = "onNotificationInfoUpdated$ don't show - " + sb.toString();
                SamsungServiceLogger samsungServiceLogger = AODDumpLog.logger;
                LogLevel logLevel = LogLevel.DEBUG;
                SamsungServiceLogger samsungServiceLogger2 = AODDumpLog.logger;
                if (samsungServiceLogger2 != null) {
                    ((SamsungServiceLoggerImpl) samsungServiceLogger2).logWithThreadId("", logLevel, str2);
                }
            }
            int size = arrayList2.size();
            try {
                PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                if (pluginAOD != null) {
                    pluginAOD.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
                } else {
                    PluginCover pluginCover = pluginAODManager.mCoverPlugin;
                    if (pluginCover != null) {
                        pluginCover.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
                    } else {
                        PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                        if (pluginSubScreen != null) {
                            pluginSubScreen.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
                        }
                    }
                }
            } catch (AbstractMethodError e2) {
                Log.d("PluginAODManager", "updateVisibleNotifications: there is no method e=" + e2);
            }
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb2.append("[updateVisibleNotifications] totalCount : [" + size + "]");
            sb2.append(" showingKeys ");
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                StatusBarNotification statusBarNotification2 = (StatusBarNotification) it3.next();
                if (statusBarNotification2 != null) {
                    sb3.append("[" + statusBarNotification2.getKey() + "]");
                }
            }
            sb2.append(sb3.toString());
            String sb4 = sb2.toString();
            SamsungServiceLogger samsungServiceLogger3 = AODDumpLog.logger;
            LogLevel logLevel2 = LogLevel.DEBUG;
            SamsungServiceLogger samsungServiceLogger4 = AODDumpLog.logger;
            if (samsungServiceLogger4 != null) {
                ((SamsungServiceLoggerImpl) samsungServiceLogger4).logWithThreadId("", logLevel2, sb4);
            }
        }

        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationTypeChanged(int i) {
        }
    };
    public final SysuiStatusBarStateController mStatusBarStateController = (SysuiStatusBarStateController) Dependency.get(StatusBarStateController.class);
    public final AnonymousClass5 mStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.doze.PluginAODManager.5
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            PluginCover pluginCover;
            PluginAODManager pluginAODManager = PluginAODManager.this;
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.onDozeAmountChanged(f, f2);
            }
            PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
            if (pluginSubScreen != null) {
                pluginSubScreen.onDozeAmountChanged(f2);
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY && (pluginCover = pluginAODManager.mCoverPlugin) != null) {
                pluginCover.onDozeAmountChanged(f2);
            }
        }
    };
    public final AnonymousClass6 mAODUICallback = new PluginAOD.UICallback() { // from class: com.android.systemui.doze.PluginAODManager.6
        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final int getKeyguardOrientation() {
            NotificationShadeWindowController notificationShadeWindowController = PluginAODManager.this.mNotificationShadeWindowController;
            if (notificationShadeWindowController == null) {
                return -1;
            }
            SecNotificationShadeWindowControllerHelperImpl.Provider provider = ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.provider;
            if (provider == null) {
                provider = null;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) provider.lpSupplier.get();
            if (layoutParams == null) {
                return -1;
            }
            return layoutParams.screenOrientation;
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final Bundle getLockStarData(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("getLockStarData() ", z, "PluginAODManager");
            PluginLockStar pluginLockStar = PluginAODManager.this.mPluginLockStarManager.mPluginLockStar;
            if (pluginLockStar == null) {
                Log.w("LStar|PluginLockStarManager", "getAODLockStarData: no plugin");
                return new Bundle();
            }
            try {
                return pluginLockStar.getAODData(z);
            } catch (Error unused) {
                Log.w("LStar|PluginLockStarManager", "getAODLockStarData: no method");
                return new Bundle();
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final boolean isWonderLandAmbientWallpaperEnabled() {
            return PluginAODManager.this.mAODAmbientWallpaperHelper.isWonderLandAmbientWallpaper();
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "registerAODDoubleTouchListener() ");
            DozeServiceHost dozeServiceHost = PluginAODManager.this.mDozeServiceHost;
            if (dozeServiceHost != null && (shadeViewController = dozeServiceHost.mNotificationPanel) != null) {
                ((NotificationPanelViewController) shadeViewController).mAODDoubleTouchListener = onTouchListener;
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void setBottomArea(View view) {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "setBottomArea() ");
            DozeServiceHost dozeServiceHost = PluginAODManager.this.mDozeServiceHost;
            if (dozeServiceHost != null && (shadeViewController = dozeServiceHost.mNotificationPanel) != null) {
                KeyguardBottomAreaView keyguardBottomAreaView = ((NotificationPanelViewController) shadeViewController).mKeyguardBottomArea;
                if (keyguardBottomAreaView instanceof KeyguardSecBottomAreaView) {
                    KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) keyguardBottomAreaView;
                    ((FrameLayout) keyguardSecBottomAreaView.bottomDozeArea$delegate.getValue()).removeAllViews();
                    ((FrameLayout) keyguardSecBottomAreaView.bottomDozeArea$delegate.getValue()).addView(view);
                }
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void unregisterAODDoubleTouchListener() {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "unregisterAODDoubleTouchListener() ");
            DozeServiceHost dozeServiceHost = PluginAODManager.this.mDozeServiceHost;
            if (dozeServiceHost != null && (shadeViewController = dozeServiceHost.mNotificationPanel) != null) {
                ((NotificationPanelViewController) shadeViewController).mAODDoubleTouchListener = null;
            }
        }
    };
    public final AnonymousClass7 mSubUICallback = new PluginSubScreen.Callback() { // from class: com.android.systemui.doze.PluginAODManager.7
        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getBouncerMessage();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getIncorrectBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getIncorrectBouncerMessage();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final SubRoom getSubRoom(int i) {
            String str;
            ListPopupWindow$$ExternalSyntheticOutline0.m("getSubRoom() ", i, "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            SubScreenManager subScreenManager = pluginAODManager.mSubScreenManager;
            if (subScreenManager != null) {
                Log.d("SubScreenManager", "getSubRoom() " + subScreenManager.getRoomName(i));
                return (SubRoom) subScreenManager.mSubRoomMap.get(Integer.valueOf(i));
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                CoverScreenManager coverScreenManager = (CoverScreenManager) pluginAODManager.mCoverScreenManagerLazy.get();
                StringBuilder sb = new StringBuilder("getSubRoom() ");
                coverScreenManager.getClass();
                if (i != 301) {
                    str = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m("INVALID TYPE [", i, "]");
                } else {
                    str = "SUB_ROOM_NOTIFICATION";
                }
                ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "CoverScreenManager");
                return (SubRoom) coverScreenManager.mSubRoomMap.get(Integer.valueOf(i));
            }
            return null;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final List getVisibleNotificationList() {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            final ArrayList arrayList = new ArrayList();
            subScreenManager.mNotifPipeline.getAllNotifs().stream().forEach(new Consumer() { // from class: com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((NotificationEntry) obj).mSbn);
                }
            });
            return arrayList;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final PluginSystemUIWallpaperUtils getWallpaperUtils() {
            return PluginAODManager.this.mWallpaperUtilsWrapper;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isCaptureEnabled() {
            return PluginAODManager.this.mEmm.isCaptureEnabled;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isDualDarInnerAuthRequired() {
            return PluginAODManager.this.mKeyguardUpdateMonitor.isDualDarInnerAuthRequired(KeyguardUpdateMonitor.getCurrentUser());
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isFullscreenBouncer() {
            return SecurityUtils.checkFullscreenBouncer(PluginAODManager.this.mKeyguardUpdateMonitor.getCurrentSecurityMode());
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isKeyguardShowing() {
            return PluginAODManager.this.mKeyguardUpdateMonitor.mKeyguardShowing;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isSecure() {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mKeyguardUpdateMonitor.isSecure() && !pluginAODManager.mKeyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isSimPinSecure() {
            return PluginAODManager.this.mKeyguardUpdateMonitor.isSimPinSecure();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isUserUnlocked() {
            Log.d("PluginAODManager", "isUserUnlocked() ");
            return PluginAODManager.this.mKeyguardUpdateMonitor.isUserUnlocked();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void launchApp(ComponentName componentName) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("launchApp() ", componentName, "PluginAODManager");
            int i = PluginAODManager.$r8$clinit;
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.getClass();
            if (componentName != null) {
                if (TextUtils.equals("Flashlight", componentName.getClassName())) {
                    SubscreenFlashLightController.getInstance(pluginAODManager.mContext).startFlashActivity();
                    return;
                }
                ShadeViewController shadeViewController = pluginAODManager.mDozeServiceHost.mNotificationPanel;
                if (shadeViewController != null) {
                    ((NotificationPanelViewController) shadeViewController).mKeyguardBottomAreaViewController.launchApp(componentName);
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void onSubScreenBouncerStateChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onSubScreenBouncerStateChanged() ", z, "PluginAODManager");
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchSubScreenBouncerStateChanged(z);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void requestDualState(boolean z) {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            if (subScreenManager.mDeviceState == 4) {
                subScreenManager.requestDualState(z);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setAODVisibleState(int i) {
            KeyguardWallpaperController keyguardWallpaperController = PluginAODManager.this.mKeyguardWallpaperController;
            keyguardWallpaperController.getClass();
            Log.d("KeyguardWallpaperController", "setAODVisibleState: state=" + i);
            try {
                keyguardWallpaperController.mService.notifyAodVisibilityState(i);
            } catch (RemoteException e) {
                Log.d("KeyguardWallpaperController", "setAODVisibleState: System dead?" + e);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setDisplayStateLimit(int i) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mDisplayManager == null) {
                pluginAODManager.mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
            try {
                Log.i("PluginAODManager", "updateDisplayStateLimit: displayState=" + i);
                IDisplayManager iDisplayManager = pluginAODManager.mDisplayManager;
                if (iDisplayManager != null) {
                    iDisplayManager.setDisplayStateLimit(pluginAODManager.mDisplayStateLock, i);
                    if (i == 2) {
                        pluginAODManager.mPassiveModeToken = pluginAODManager.mDisplayManager.acquirePassiveModeToken(pluginAODManager.mToken, "PluginAODManager");
                        return;
                    }
                    IRefreshRateToken iRefreshRateToken = pluginAODManager.mPassiveModeToken;
                    if (iRefreshRateToken != null) {
                        iRefreshRateToken.release();
                        pluginAODManager.mPassiveModeToken = null;
                        return;
                    }
                    return;
                }
                Log.e("PluginAODManager", "updateDisplayStateLimit : mDisplayManager is null!! ERROR case");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setEnableDLS(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setEnableDLS() ", z, "PluginAODManager");
            PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) PluginAODManager.this.mPluginLockMediator;
            if (pluginLockMediatorImpl.mBasicListener != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", PluginLock.ACTION_WALLPAPER_STATE_CHANGED);
                bundle.putInt(PluginLock.KEY_SCREEN, 1);
                bundle.putInt("state", z ? 1 : 0);
                PluginLockBasicManager pluginLockBasicManager = pluginLockMediatorImpl.mBasicListener.mBasicManager;
                if (pluginLockBasicManager != null) {
                    pluginLockBasicManager.onEventReceived(bundle);
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean shouldControlScreenOff() {
            boolean z = PluginAODManager.this.mDozeParameters.mControlScreenOffAnimation;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("shouldControlScreenOff() : ", z, "PluginAODManager");
            return z;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStartSubscreenBiometric(null);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startFingerprintState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStartSubscreenFingerprint();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startSubHomeActivity() {
            Log.d("PluginAODManager", "startSubHomeActivity() ");
            PluginAODManager.this.mSubScreenManager.startSubHomeActivity();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startSubHomeActivityIfNeeded() {
            Log.d("PluginAODManager", "startSubHomeActivityIfNeeded() ");
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                subScreenManager.mDismissCallbackRegistry.notifyDismissCancelled();
                subScreenManager.mRequestBouncerForLauncherTask = false;
                if (((ArrayList) subScreenManager.mOccludedApps).isEmpty()) {
                    subScreenManager.startSubHomeActivity();
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void stopBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStopSubscreenBiometric();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void updateBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_SUB_SCREEN);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void updateSubScreenFallback(boolean z) {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateFallback()  , ", z, " , ");
                m.append(subScreenManager.mFallback);
                Log.d("SubScreenManager", m.toString());
                if (z) {
                    subScreenManager.startSubScreenFallback(subScreenManager.mSubDisplay);
                    return;
                }
                if (!subScreenManager.mKeyguardUpdateMonitor.isUserUnlocked()) {
                    Log.d("SubScreenManager", "updateFallback. Do not unlocked. So not finish ");
                } else if (subScreenManager.mFallback != null) {
                    subScreenManager.startSubHomeActivity();
                    subScreenManager.mHandler.sendEmptyMessageDelayed(3000, 500L);
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void verifyCredential(String str) {
            PluginAODManager.this.mKeyguardViewController.requestUnlock(str);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void onClockPageClicked() {
        }
    };
    public final AnonymousClass8 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.doze.PluginAODManager.8
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            Log.d("PluginAODManager", "onEntryAdded: ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            pluginAODManager.getClass();
            if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                synchronized (pluginAODManager.mSmartAlerts) {
                    pluginAODManager.mSmartAlerts.add(statusBarNotification);
                }
                pluginAODManager.logSmartAlert("addNotification");
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().addNotification(statusBarNotification);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().addNotification(statusBarNotification);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x003d, code lost:
        
            r4 = r2.mAODPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:
        
            if (r4 == null) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0041, code lost:
        
            r4.getNotificationManager().removeNotification(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0049, code lost:
        
            r2 = r2.mCoverPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
        
            if (r2 == null) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
        
            r2.getNotificationManager().removeNotification(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
        
            return;
         */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onEntryRemoved(com.android.systemui.statusbar.notification.collection.NotificationEntry r3, int r4) {
            /*
                r2 = this;
                java.lang.String r4 = "PluginAODManager"
                java.lang.String r0 = "onEntryRemoved: "
                android.util.Log.d(r4, r0)
                com.android.systemui.doze.PluginAODManager r2 = com.android.systemui.doze.PluginAODManager.this
                java.lang.String r3 = r3.mKey
                java.util.ArrayList r4 = r2.mSmartAlerts
                java.util.Iterator r4 = r4.iterator()
            L12:
                boolean r0 = r4.hasNext()
                if (r0 == 0) goto L3d
                java.lang.Object r0 = r4.next()
                android.service.notification.StatusBarNotification r0 = (android.service.notification.StatusBarNotification) r0
                if (r0 == 0) goto L12
                java.lang.String r1 = r0.getKey()
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L12
                java.util.ArrayList r1 = r2.mSmartAlerts
                monitor-enter(r1)
                java.util.ArrayList r4 = r2.mSmartAlerts     // Catch: java.lang.Throwable -> L3a
                r4.remove(r0)     // Catch: java.lang.Throwable -> L3a
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L3a
                java.lang.String r4 = "removeNotification"
                r2.logSmartAlert(r4)
                goto L3d
            L3a:
                r2 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L3a
                throw r2
            L3d:
                com.android.systemui.plugins.aod.PluginAOD r4 = r2.mAODPlugin
                if (r4 == 0) goto L49
                com.android.systemui.plugins.aod.PluginAODNotificationManager r2 = r4.getNotificationManager()
                r2.removeNotification(r3)
                goto L54
            L49:
                com.android.systemui.plugins.cover.PluginCover r2 = r2.mCoverPlugin
                if (r2 == 0) goto L54
                com.android.systemui.plugins.aod.PluginAODNotificationManager r2 = r2.getNotificationManager()
                r2.removeNotification(r3)
            L54:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.PluginAODManager.AnonymousClass8.onEntryRemoved(com.android.systemui.statusbar.notification.collection.NotificationEntry, int):void");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            Log.d("PluginAODManager", "onEntryUpdated: ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            pluginAODManager.getClass();
            if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                Iterator it = pluginAODManager.mSmartAlerts.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) it.next();
                    if (statusBarNotification2 != null && statusBarNotification != null && statusBarNotification2.getKey().equals(statusBarNotification.getKey())) {
                        synchronized (pluginAODManager.mSmartAlerts) {
                            pluginAODManager.mSmartAlerts.remove(statusBarNotification2);
                            if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                                pluginAODManager.mSmartAlerts.add(statusBarNotification);
                            }
                        }
                        pluginAODManager.logSmartAlert("updateNotification");
                    }
                }
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateNotification(statusBarNotification);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().updateNotification(statusBarNotification);
            }
        }
    };
    public boolean mIsFolderOpened = true;
    public int mCurrentPhoneState = 0;

    static {
        boolean z = DozeService.DEBUG;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.doze.PluginAODManager$9] */
    /* JADX WARN: Type inference failed for: r8v10, types: [com.android.systemui.doze.PluginAODManager$6] */
    /* JADX WARN: Type inference failed for: r8v11, types: [com.android.systemui.doze.PluginAODManager$7] */
    /* JADX WARN: Type inference failed for: r8v12, types: [com.android.systemui.doze.PluginAODManager$8] */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.doze.PluginAODManager$2] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.systemui.doze.PluginAODManager$3] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.doze.PluginAODManager$4] */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.android.systemui.doze.PluginAODManager$5] */
    public PluginAODManager(Context context, LockscreenNotificationManager lockscreenNotificationManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, PluginFaceWidgetManager pluginFaceWidgetManager, SettingsHelper settingsHelper, DozeParameters dozeParameters, PluginLockMediator pluginLockMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardWallpaper keyguardWallpaper, DozeServiceHost dozeServiceHost, SubScreenManager subScreenManager, Lazy lazy, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, NotificationLockscreenUserManager notificationLockscreenUserManager, SamsungServiceLogger samsungServiceLogger, KeyguardFoldController keyguardFoldController, DumpManager dumpManager, PluginLockStarManager pluginLockStarManager, NotificationsController notificationsController, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, final SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, KeyguardViewMediatorHelper keyguardViewMediatorHelper, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, ConfigurationController configurationController, AODTouchModeManager aODTouchModeManager, KeyguardWallpaperController keyguardWallpaperController) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        int i = 0;
        this.mContext = context;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewController = keyguardViewController;
        this.mDozeParameters = dozeParameters;
        this.mPluginLockMediator = pluginLockMediator;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mDozeServiceHost = dozeServiceHost;
        this.mWallpaperUtilsWrapper = faceWidgetWallpaperUtilsWrapper;
        this.mDisplayLifeCycle = displayLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mCommonNotifCollectionLazy = lazy2;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mFaceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenManager = subScreenManager;
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                if (subScreenManager != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
                    subScreenManager.setSubRoom(301, subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null);
                }
                if (lockscreenNotificationManager != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
                    lockscreenNotificationManager.addCallback((subscreenDeviceModelParent2 == null || (subscreenSubRoomNotification = subscreenDeviceModelParent2.mSubRoomNotification) == null) ? null : subscreenSubRoomNotification.mLockscreenNotiCallback);
                } else {
                    subscreenNotificationController.getClass();
                }
            }
            SubscreenMusicWidgetController subscreenMusicWidgetController = (SubscreenMusicWidgetController) Dependency.get(SubscreenMusicWidgetController.class);
            subScreenManager.setSubRoom(304, new SubscreenMusicWidgetSubroom(subscreenMusicWidgetController.mContext, subscreenMusicWidgetController.mMediaHost));
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                if (QpRune.QUICK_PANEL_SUBSCREEN_QUICK_PANEL_WINDOW && Process.myUserHandle().equals(UserHandle.SYSTEM)) {
                    SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) subScreenQuickPanelWindowController.mPanelLogger;
                    secPanelLoggerImpl.addCoverPanelStateLog("initSubRoomCommon");
                    subScreenQuickPanelWindowController.mCommandQueue.addCallback((CommandQueue.Callbacks) subScreenQuickPanelWindowController);
                    subScreenQuickPanelWindowController.mDisplayLifecycle.addObserver(subScreenQuickPanelWindowController.mFoldStateChangedListener);
                    secPanelLoggerImpl.addCoverPanelStateLog("createAndAddWindow");
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2415, -2138832832, -3);
                    subScreenQuickPanelWindowController.mLp = layoutParams;
                    layoutParams.privateFlags |= 16;
                    layoutParams.layoutInDisplayCutoutMode = 3;
                    layoutParams.gravity = 48;
                    layoutParams.screenOrientation = 1;
                    layoutParams.setTitle("SubScreenQuickPanel");
                    WindowManager.LayoutParams layoutParams2 = subScreenQuickPanelWindowController.mLp;
                    SubScreenQSEventHandler subScreenQSEventHandler = subScreenQuickPanelWindowController.mSubScreenQSEventHandler;
                    layoutParams2.semSetScreenTimeout(subScreenQSEventHandler.getScreenTimeOut());
                    WindowManager.LayoutParams layoutParams3 = subScreenQuickPanelWindowController.mLp;
                    layoutParams3.setFitInsetsTypes(layoutParams3.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()) & (~WindowInsets.Type.statusBars()));
                    Context context2 = subScreenQuickPanelWindowController.mContext;
                    subScreenQuickPanelWindowController.mWindowManager = (WindowManager) context2.getSystemService("window");
                    if (subScreenQuickPanelWindowController.mSubScreenQsWindowView == null) {
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView = (SubScreenQuickPanelWindowView) LayoutInflater.from(context2).inflate(R.layout.subscreen_quickpanel_window_view, (ViewGroup) null);
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCorners(15, context2.getResources().getDimensionPixelSize(17105696));
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCornerColor(15, context2.getColor(android.R.color.black));
                        View view = subScreenQuickPanelWindowController.mSubscreenQsPanelController.getSubRoomQuickPanel().mMainView;
                        subScreenQuickPanelWindowController.mQSPanel = view;
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.addView(view);
                        new SecCoverBlurController(context2, subScreenQuickPanelWindowController.mQSPanel).applyBlur();
                    }
                    subScreenQuickPanelWindowController.mWindowManager.addView(subScreenQuickPanelWindowController.mSubScreenQsWindowView, subScreenQuickPanelWindowController.mLp);
                    subScreenQuickPanelWindowController.mPanelResourcePicker.getClass();
                    subScreenQuickPanelWindowController.mMaxExpandedHeight = DeviceState.getScreenHeight(context2);
                    subScreenQSEventHandler.init();
                    SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    subScreenQuickPanelWindowView.mSubScreenQSTouchHandler = subScreenQSEventHandler;
                    subScreenQuickPanelWindowView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.2
                        public AnonymousClass2() {
                        }

                        @Override // android.view.View.OnApplyWindowInsetsListener
                        public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                            int i2;
                            DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                            if (displayCutout != null) {
                                i2 = displayCutout.getSafeInsetTop();
                            } else {
                                i2 = 0;
                            }
                            View view3 = SubScreenQuickPanelWindowController.this.mQSPanel;
                            if (view3 != null) {
                                view3.setPadding(view3.getPaddingLeft(), i2, SubScreenQuickPanelWindowController.this.mQSPanel.getPaddingRight(), SubScreenQuickPanelWindowController.this.mQSPanel.getPaddingBottom());
                            }
                            return windowInsets;
                        }
                    });
                    subScreenQuickPanelWindowController.mSubScreenComponent.create(subScreenQuickPanelWindowController.mSubScreenQsWindowView).getSubScreenQuickPanelHeaderController().init();
                    subScreenManager.setSubRoom(300, new SubScreenQuickPanelWindowController.PanelExpandedFractionProvider(subScreenQuickPanelWindowController, i));
                }
            } else if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
                if (this.mSubscreenQsPanelController == null) {
                    this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class);
                }
                this.mSubscreenQsPanelController.init();
                subScreenManager.setSubRoom(300, this.mSubscreenQsPanelController.getSubRoomQuickPanel());
            }
        }
        this.mCoverScreenManagerLazy = lazy;
        AODDumpLog.logger = samsungServiceLogger;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.doze.PluginAODManager.9
            /* JADX WARN: Code restructure failed: missing block: B:20:0x0066, code lost:
            
                if (r6.isShowingPublic() == false) goto L25;
             */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void handleMessage(android.os.Message r6) {
                /*
                    r5 = this;
                    int r0 = r6.what
                    r1 = 1000(0x3e8, float:1.401E-42)
                    if (r0 == r1) goto L8
                    goto Ld3
                L8:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "MSG_EXPAND_NOTI_PANEL: mDozing="
                    r0.<init>(r1)
                    com.android.systemui.doze.PluginAODManager r1 = com.android.systemui.doze.PluginAODManager.this
                    boolean r1 = r1.mDozing
                    java.lang.String r2 = "PluginAODManager"
                    com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0.m(r0, r1, r2)
                    com.android.systemui.doze.PluginAODManager r5 = com.android.systemui.doze.PluginAODManager.this
                    boolean r0 = r5.mDozing
                    if (r0 != 0) goto Ld3
                    java.lang.Object r6 = r6.obj
                    android.service.notification.StatusBarNotification r6 = (android.service.notification.StatusBarNotification) r6
                    com.android.systemui.statusbar.phone.DozeServiceHost r5 = r5.mDozeServiceHost
                    com.android.systemui.statusbar.phone.CentralSurfaces r0 = r5.mCentralSurfaces
                    java.lang.String r1 = "DozeServiceHost"
                    if (r0 != 0) goto L31
                    java.lang.String r5 = "animateExpandLockedShadePanel() called before initialize(), return"
                    android.util.Log.i(r1, r5)
                    goto Ld3
                L31:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r2 = "animateExpandLockedShadePanel sbn="
                    r0.<init>(r2)
                    r2 = 0
                    r3 = 1
                    if (r6 != 0) goto L3e
                    r4 = r3
                    goto L3f
                L3e:
                    r4 = r2
                L3f:
                    androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(r0, r4, r1)
                    dagger.Lazy r5 = r5.mSecPanelPolicyLazy
                    java.lang.Object r5 = r5.get()
                    com.android.systemui.shade.SecPanelPolicy r5 = (com.android.systemui.shade.SecPanelPolicy) r5
                    if (r6 != 0) goto L4d
                    goto L69
                L4d:
                    r5.getClass()
                    java.lang.String r6 = r6.getKey()
                    com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r0 = r5.mCommonNotifCollection
                    com.android.systemui.statusbar.notification.collection.NotifPipeline r0 = (com.android.systemui.statusbar.notification.collection.NotifPipeline) r0
                    com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r0.getEntry(r6)
                    if (r6 == 0) goto L69
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = r6.row
                    if (r6 == 0) goto L69
                    boolean r0 = r6.isShowingPublic()
                    if (r0 != 0) goto L69
                    goto L6a
                L69:
                    r6 = 0
                L6a:
                    com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r5.mSysuiStatusBarStateController
                    com.android.systemui.statusbar.StatusBarStateControllerImpl r0 = (com.android.systemui.statusbar.StatusBarStateControllerImpl) r0
                    int r0 = r0.mState
                    if (r0 != r3) goto L74
                    r0 = r3
                    goto L75
                L74:
                    r0 = r2
                L75:
                    if (r0 == 0) goto L86
                    com.android.systemui.statusbar.CommandQueue r0 = r5.mCommandQueue
                    boolean r0 = r0.panelsEnabled()
                    if (r0 != 0) goto L80
                    goto Ld3
                L80:
                    com.android.systemui.statusbar.LockscreenShadeTransitionController r0 = r5.mLockscreenShadeTransitionController
                    r0.goToLockedShade(r6, r3)
                    goto L8f
                L86:
                    com.android.systemui.statusbar.phone.CentralSurfaces r0 = r5.mCentralSurfaces
                    com.android.systemui.statusbar.phone.CentralSurfacesImpl r0 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r0
                    com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks r0 = r0.mCommandQueueCallbacks
                    r0.animateExpandNotificationsPanel()
                L8f:
                    if (r6 == 0) goto Ld3
                    boolean r6 = r6.mIsSummaryWithChildren
                    if (r6 == 0) goto Ld3
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder
                    java.lang.String r0 = "[animateExpandShadeLockedPanel]"
                    r6.<init>(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "isKeyguardState:"
                    r0.<init>(r1)
                    com.android.systemui.statusbar.SysuiStatusBarStateController r1 = r5.mSysuiStatusBarStateController
                    com.android.systemui.statusbar.StatusBarStateControllerImpl r1 = (com.android.systemui.statusbar.StatusBarStateControllerImpl) r1
                    int r1 = r1.mState
                    if (r1 != r3) goto Lac
                    r2 = r3
                Lac:
                    r0.append(r2)
                    java.lang.String r0 = r0.toString()
                    r6.append(r0)
                    com.android.systemui.log.SecPanelLogger r5 = r5.mPanelLogger
                    com.android.systemui.log.SecPanelLoggerImpl r5 = (com.android.systemui.log.SecPanelLoggerImpl) r5
                    java.lang.String r0 = ""
                    r5.appendStatusBarState(r6, r0)
                    java.lang.String r6 = r6.toString()
                    boolean r0 = com.android.systemui.log.SecPanelLoggerImpl.DEBUG_MODE
                    if (r0 == 0) goto Lcc
                    java.lang.String r0 = "SecPanelLogger"
                    android.util.Log.d(r0, r6)
                Lcc:
                    java.lang.String r0 = "EXTERNAL_INPUT"
                    com.android.systemui.log.SecPanelLogWriter r5 = r5.writer
                    r5.logPanel(r0, r6)
                Ld3:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.PluginAODManager.AnonymousClass9.handleMessage(android.os.Message):void");
            }
        };
        this.mPluginLockStarManager = pluginLockStarManager;
        pluginLockStarManager.registerCallback("AOD", new PluginLockStarManager.LockStarCallback(this) { // from class: com.android.systemui.doze.PluginAODManager.10
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarEnabled(boolean z) {
            }

            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onUpdateModifiers() {
            }
        });
        this.mNotificationsController = notificationsController;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "PluginAODManager", this);
        this.mFoldController = keyguardFoldController;
        if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.doze.PluginAODManager$$ExternalSyntheticLambda0
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    PluginAODManager pluginAODManager = PluginAODManager.this;
                    pluginAODManager.getClass();
                    Log.i("PluginAODManager", "onFolderStateChanged isOpened : " + z);
                    pluginAODManager.mIsFolderOpened = z;
                    if (LsRune.AOD_SUB_DISPLAY_COVER) {
                        pluginAODManager.updateAnimateScreenOff();
                    }
                    if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT && !z) {
                        pluginAODManager.setStartedByFolderClosed(true);
                    }
                    PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                    if (pluginAOD != null) {
                        pluginAOD.onFolderStateChanged(z);
                    }
                }
            }, 4, true);
        }
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mAODTouchModeManager = aODTouchModeManager;
        this.mKeyguardWallpaperController = keyguardWallpaperController;
    }

    public static boolean isSmartAlertNoti(StatusBarNotification statusBarNotification) {
        Notification notification2;
        Bundle bundle;
        if (statusBarNotification == null || (notification2 = statusBarNotification.getNotification()) == null || (bundle = notification2.extras) == null || TextUtils.isEmpty(bundle.getString("smart_alert_title", ""))) {
            return false;
        }
        return true;
    }

    public final void addConnectionRunnable(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (this.mConnectionRunnableList == null) {
            this.mConnectionRunnableList = new ArrayList();
        }
        if (!this.mConnectionRunnableList.contains(runnable)) {
            this.mConnectionRunnableList.add(runnable);
        }
    }

    public final void chargingAnimStarted(boolean z) {
        PluginAOD pluginAOD = this.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.onChargingAnimStarted(z);
            return;
        }
        PluginClockPack pluginClockPack = this.mClockPackPlugin;
        if (pluginClockPack != null) {
            pluginClockPack.onChargingAnimStarted(z);
        }
    }

    public final void disableStatusBar(int i) {
        Context context;
        if (this.mStatusBarManager == null && (context = this.mCoverPluginContext) != null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            Log.w("PluginAODManager", "disableStatusBar() : Could not get status bar manager");
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m("disableStatusBar() ", i, "PluginAODManager");
            this.mStatusBarManager.disable(i);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("");
        printWriter.println("      Plugins");
        printWriter.println("        mAODPlugin : " + this.mAODPlugin);
        printWriter.println("        mClockPackPlugin : " + this.mClockPackPlugin);
        printWriter.println("        mCoverPlugin : " + this.mCoverPlugin);
        printWriter.println("        mSubScreenPlugin : " + this.mSubScreenPlugin);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("        supportAODLightReveal : "), LsRune.AOD_LIGHT_REVEAL, printWriter);
    }

    public final void enableTouch(boolean z) {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("enableTouch : ", z, " FaceWidgetContainerWrapper = ");
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mFaceWidgetContainerWrapper;
        m.append(faceWidgetContainerWrapper);
        Log.d("PluginAODManager", m.toString());
        if (faceWidgetContainerWrapper != null && (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) != null) {
            pluginKeyguardStatusView.setTouchEnabled(z);
        }
    }

    public final void initAODOverlayContainer() {
        AODOverlayContainer aODOverlayContainer;
        if (this.mAODOverlayContainer != null) {
            return;
        }
        Log.d("PluginAODManager", "initAODOverlayContainer()");
        ShadeViewController shadeViewController = this.mDozeServiceHost.mNotificationPanel;
        if (shadeViewController == null) {
            aODOverlayContainer = null;
        } else {
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
            Log.d("NotificationPanelView", "getAODOverlayContainer() ");
            if (notificationPanelViewController.mAODOverlayContainer == null) {
                notificationPanelViewController.mAODOverlayContainer = (AODOverlayContainer) ((ViewStub) ((ViewGroup) notificationPanelViewController.mView.findViewWithTag("aod_overlay_container_stub_parent")).findViewById(R.id.aod_overlay_container_stub)).inflate();
            }
            aODOverlayContainer = notificationPanelViewController.mAODOverlayContainer;
        }
        this.mAODOverlayContainer = aODOverlayContainer;
    }

    public final void logSmartAlert(String str) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb.append("[onUpdateSmartAlert:" + str + "] ");
        ArrayList arrayList = this.mSmartAlerts;
        sb.append(arrayList.size() + 40 + 41);
        sb.append(" showingKeys ");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            StatusBarNotification statusBarNotification = (StatusBarNotification) it.next();
            if (statusBarNotification != null) {
                sb2.append("[" + statusBarNotification.getKey() + ']');
            }
        }
        sb.append(sb2.toString());
        String sb3 = sb.toString();
        SamsungServiceLogger samsungServiceLogger = AODDumpLog.logger;
        LogLevel logLevel = LogLevel.DEBUG;
        SamsungServiceLogger samsungServiceLogger2 = AODDumpLog.logger;
        if (samsungServiceLogger2 != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger2).logWithThreadId("", logLevel, sb3);
        }
    }

    public final boolean needControlScreenOff() {
        PluginAODStateProvider pluginAODStateProvider;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if ((keyguardUpdateMonitor.getCoverState() != null && !keyguardUpdateMonitor.getCoverState().switchState) || keyguardUpdateMonitor.isBouncerFullyShown() || keyguardUpdateMonitor.isScreenOffMemoRunning() || this.mStartedByFolderClosed || this.mCurrentPhoneState != 0) {
            return false;
        }
        boolean z = this.mDozeParameters.mAODParameters.mDozeAlwaysOn;
        if (z && (pluginAODStateProvider = this.mPluginAODStateProvider) != null && pluginAODStateProvider.getNeedScreenOff()) {
            return false;
        }
        return z;
    }

    public final void onAodTransitionEnd() {
        if (this.mAODMachine == null) {
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onAodTransitionEnd mClockTransitionStarted:"), this.mClockTransitionStarted, "PluginAODManager");
        if (this.mClockTransitionStarted) {
            this.mAODMachine.requestState(DozeMachine.State.SCRIM_AOD_ENDED);
        }
        if (LsRune.SUBSCREEN_WATCHFACE && !this.mDisplayLifeCycle.mIsFolderOpened) {
            Log.d("PluginAODManager", "onAodTransitionEnd() in folded state");
            onTransitionEnded();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        updateAnimateScreenOff();
    }

    public final void onTransitionEnded() {
        PluginLockBasicManager pluginLockBasicManager;
        if (this.mAODMachine == null) {
            return;
        }
        boolean z = this.mDozeParameters.mControlScreenOffAnimation;
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onTransitionEnded mClockTransitionStarted:"), this.mClockTransitionStarted, " shouldControlScreenOff : ", z, "PluginAODManager");
        if (this.mClockTransitionStarted) {
            this.mAODMachine.requestState(DozeMachine.State.DOZE_TRANSITION_ENDED);
            this.mAODTouchModeManager.setTouchMode(0);
            this.mClockTransitionStarted = false;
        }
        if (z) {
            PluginLockDelegateApp pluginLockDelegateApp = ((PluginLockMediatorImpl) this.mPluginLockMediator).mBasicListener;
            if (pluginLockDelegateApp != null && (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) != null) {
                pluginLockBasicManager.onAodTransitionEnd();
            }
            SystemUIWallpaperBase systemUIWallpaperBase = ((KeyguardWallpaperController) this.mKeyguardWallpaper).mWallpaperView;
            if (systemUIWallpaperBase != null) {
                systemUIWallpaperBase.onPause();
            }
        }
    }

    public final void registerUpdateMonitor() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mWakefulness = wakefulnessLifecycle.mWakefulness;
        RecyclerView$$ExternalSyntheticOutline0.m(new StringBuilder("registerUpdateMonitor mWakefulness="), this.mWakefulness, "PluginAODManager");
    }

    public final void removeUpdateMonitor() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        this.mWakefulness = wakefulnessLifecycle.mWakefulness;
        RecyclerView$$ExternalSyntheticOutline0.m(new StringBuilder("removeUpdateMonitor mWakefulness="), this.mWakefulness, "PluginAODManager");
    }

    public final void setAODPlugin(PluginAOD pluginAOD) {
        AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
        AnonymousClass5 anonymousClass5 = this.mStateListener;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        if (pluginAOD == null && this.mCoverPlugin == null && this.mSubScreenPlugin == null) {
            removeUpdateMonitor();
            ((StatusBarStateControllerImpl) sysuiStatusBarStateController).removeCallback((StatusBarStateController.StateListener) anonymousClass5);
            if (lockscreenNotificationManager != null) {
                lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
            }
        }
        this.mAODPlugin = pluginAOD;
        AnonymousClass8 anonymousClass8 = this.mNotifCollectionListener;
        Lazy lazy = this.mCommonNotifCollectionLazy;
        if (pluginAOD != null) {
            if (this.mCoverPlugin == null && this.mSubScreenPlugin == null) {
                registerUpdateMonitor();
            }
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
            statusBarStateControllerImpl.addCallback((StatusBarStateController.StateListener) anonymousClass5);
            PluginAOD pluginAOD2 = this.mAODPlugin;
            float f = statusBarStateControllerImpl.mDozeAmount;
            pluginAOD2.onDozeAmountChanged(f, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(f));
            this.mAODPlugin.setIsDozing(this.mDozing, false);
            if (lockscreenNotificationManager != null) {
                this.mAODPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
                lockscreenNotificationManager.addCallback(anonymousClass4);
                Log.d("PluginAODManager", "addNotiCollectionListener: ");
                ((NotifPipeline) ((CommonNotifCollection) lazy.get())).addCollectionListener(anonymousClass8);
            }
            this.mAODPlugin.setAODUICallback(this.mAODUICallback);
            return;
        }
        this.mDozeParameters.mAODParameters.updateDozeAlwaysOn();
        updateAnimateScreenOff();
        Log.d("PluginAODManager", "removeNotiCollectionListener: ");
        NotifCollection notifCollection = ((NotifPipeline) ((CommonNotifCollection) lazy.get())).mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        ((ArrayList) notifCollection.mNotifCollectionListeners).remove(anonymousClass8);
    }

    public final void setCoverPlugin(Context context, PluginCover pluginCover) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        CoverScreenManager coverScreenManager;
        SubscreenSubRoomNotification subscreenSubRoomNotification2;
        this.mCoverPluginContext = context;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        if (pluginCover == null && this.mAODPlugin == null && this.mSubScreenPlugin == null) {
            if (lockscreenNotificationManager != null) {
                lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                ((StatusBarStateControllerImpl) sysuiStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this.mStateListener);
            }
            removeUpdateMonitor();
        }
        this.mCoverPlugin = pluginCover;
        if (pluginCover != null) {
            if (this.mAODPlugin == null && this.mSubScreenPlugin == null) {
                registerUpdateMonitor();
            }
            if (lockscreenNotificationManager != null) {
                this.mCoverPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
                lockscreenNotificationManager.addCallback(anonymousClass4);
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                if (NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                    SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                    SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
                    if (subscreenDeviceModelParent != null) {
                        subscreenDeviceModelParent.onDisplayReady();
                    }
                    SubscreenSubRoomNotification.AnonymousClass3 anonymousClass3 = null;
                    Lazy lazy = this.mCoverScreenManagerLazy;
                    if (lazy != null && (coverScreenManager = (CoverScreenManager) lazy.get()) != null) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
                        if (subscreenDeviceModelParent2 != null) {
                            subscreenSubRoomNotification2 = subscreenDeviceModelParent2.getSubRoomNotification();
                        } else {
                            subscreenSubRoomNotification2 = null;
                        }
                        Log.d("CoverScreenManager", "setSubRoom() SUB_ROOM_NOTIFICATION, " + subscreenSubRoomNotification2);
                        coverScreenManager.mSubRoomMap.put(301, subscreenSubRoomNotification2);
                    }
                    if (lockscreenNotificationManager != null) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationController.mDeviceModel;
                        if (subscreenDeviceModelParent3 != null && (subscreenSubRoomNotification = subscreenDeviceModelParent3.mSubRoomNotification) != null) {
                            anonymousClass3 = subscreenSubRoomNotification.mLockscreenNotiCallback;
                        }
                        lockscreenNotificationManager.addCallback(anonymousClass3);
                    }
                }
                this.mCoverPlugin.setPluginCallback(this.mSubUICallback);
                this.mCoverPlugin.onDozingChanged(this.mDozing);
                this.mCoverPlugin.onDozeAmountChanged(((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
                return;
            }
            return;
        }
        disableStatusBar(0);
    }

    public final void setIsDozing(boolean z, boolean z2) {
        AODOverlayContainer aODOverlayContainer;
        if (this.mDozing == z) {
            return;
        }
        initAODOverlayContainer();
        if (!z && (aODOverlayContainer = this.mAODOverlayContainer) != null) {
            aODOverlayContainer.setVisibility(8);
        }
        this.mDozing = z;
        PluginAOD pluginAOD = this.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.setIsDozing(z, z2);
        }
        if (LsRune.SUBSCREEN_UI) {
            PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
            if (pluginSubScreen != null) {
                pluginSubScreen.onDozingChanged(z);
            }
            if (LsRune.SUBSCREEN_WATCHFACE && !this.mDisplayLifeCycle.mIsFolderOpened) {
                boolean z3 = this.mDozing;
                PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration = this.mSysUIConfig;
                boolean z4 = false;
                int i = 1;
                SubScreenManager subScreenManager = this.mSubScreenManager;
                if (z3) {
                    if (this.mWakefulnessLifecycle.mLastSleepReason == 3) {
                        subScreenManager.getClass();
                        subScreenManager.mBackgroundExecutor.executeDelayed(100L, new SubScreenManager$$ExternalSyntheticLambda1(subScreenManager, i));
                    } else {
                        if (pluginAODSystemUIConfiguration.get(2, 0) == 0) {
                            z4 = true;
                        }
                        if (z4) {
                            subScreenManager.adjustSubHomeActivityOrder(true);
                        }
                    }
                } else if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                    if (pluginAODSystemUIConfiguration.get(2, 0) != 0) {
                        i = 0;
                    }
                    if (i != 0) {
                        subScreenManager.adjustSubHomeActivityOrder(false);
                    }
                }
            }
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            PluginCover pluginCover = this.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.onDozingChanged(z);
            }
            if (this.mKeyguardUpdateMonitor.isCoverClosed() && this.mDozing) {
                ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).prepareCoverHomeActivity();
            }
        }
    }

    public final void setStartedByFolderClosed(boolean z) {
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m("setStartedByFolderClosed: ", z, "PluginAODManager");
        this.mStartedByFolderClosed = z;
        updateAnimateScreenOff();
    }

    public final void setSubScreenPlugin(PluginSubScreen pluginSubScreen) {
        if (!LsRune.SUBSCREEN_UI) {
            return;
        }
        PluginAOD pluginAOD = this.mAODPlugin;
        AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        if (pluginAOD == null && this.mCoverPlugin == null && pluginSubScreen == null) {
            lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
            removeUpdateMonitor();
        }
        this.mSubScreenPlugin = pluginSubScreen;
        if (pluginSubScreen != null) {
            if (this.mAODPlugin == null && this.mCoverPlugin == null) {
                registerUpdateMonitor();
            }
            this.mSubScreenPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
            this.mSubScreenPlugin.setPluginCallback(this.mSubUICallback);
            this.mSubScreenPlugin.onDozingChanged(this.mDozing);
            this.mSubScreenPlugin.onFolderStateChanged(((KeyguardFoldControllerImpl) this.mFoldController).isFoldOpened());
            this.mSubScreenPlugin.onDozeAmountChanged(((StatusBarStateControllerImpl) this.mStatusBarStateController).mDozeAmount);
            lockscreenNotificationManager.addCallback(anonymousClass4);
        }
    }

    public final void updateAnimateScreenOff() {
        boolean z = LsRune.AOD_DISABLE_CLOCK_TRANSITION;
        boolean z2 = false;
        DozeParameters dozeParameters = this.mDozeParameters;
        if (z) {
            dozeParameters.setControlScreenOffAnimation(false);
            return;
        }
        if (!dozeParameters.getDisplayNeedsBlanking()) {
            boolean alwaysOn = dozeParameters.getAlwaysOn();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (alwaysOn && ((keyguardUpdateMonitor.isKeyguardVisible() || ((LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && !this.mIsFolderOpened) || (LsRune.AOD_FULLSCREEN && dozeParameters.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation()))) && needControlScreenOff())) {
                z2 = true;
            }
            if (this.mControlScreenOff != z2) {
                this.mControlScreenOff = z2;
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateAnimateScreenOff : controlScreenOff=", z2, " AlwaysOn=");
                m.append(dozeParameters.getAlwaysOn());
                m.append(" keyguardShowing=");
                m.append(keyguardUpdateMonitor.isKeyguardVisible());
                m.append(" powerSaveActive=");
                m.append(((BatteryControllerImpl) this.mDozeServiceHost.mBatteryController).mAodPowerSave);
                m.append(" mIsFolderOpened=");
                m.append(this.mIsFolderOpened);
                m.append(" needControlScreenOff=");
                m.append(needControlScreenOff());
                m.append(" called=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(4, m, "PluginAODManager");
            }
            dozeParameters.setControlScreenOffAnimation(z2);
        }
    }
}

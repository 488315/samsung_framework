package com.android.systemui.globalactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.sysprop.TelephonyProperties;
import android.telecom.TelecomManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import com.android.app.animation.Interpolators;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.MultiListLayout;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.plugins.GlobalActionsPanelPlugin;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class GlobalActionsDialogLite implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener, ConfigurationController.ConfigurationListener, GlobalActionsPanelPlugin.Callbacks, LifecycleOwner {
    static final String GLOBAL_ACTION_KEY_POWER = "power";
    public final AnonymousClass7 mAirplaneModeObserver;
    public AirplaneModeAction mAirplaneModeOn;
    public final AudioManager mAudioManager;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass5 mBroadcastReceiver;
    public final Optional mCentralSurfacesOptional;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    protected ActionsDialogLite mDialog;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public int mDialogPressDelay;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public final GlobalSettings mGlobalSettings;
    public final AnonymousClass8 mHandler;
    public final boolean mHasTelephony;
    public final boolean mHasVibrator;
    public final IActivityManager mIActivityManager;
    public final IWindowManager mIWindowManager;
    public boolean mIsWaitingForEcmExit;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public final Handler mMainHandler;
    public final MetricsLogger mMetricsLogger;
    public int mOrientation;
    public final AnonymousClass6 mPhoneStateListener;
    public final Resources mResources;
    public final RingerModeTracker mRingerModeTracker;
    public final ScreenshotHelper mScreenshotHelper;
    public final boolean mShowSilentToggle;
    public Action mSilentModeAction;
    public int mSmallestScreenWidthDp;
    public final TelecomManager mTelecomManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TrustManager mTrustManager;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final GlobalActions.GlobalActionsManager mWindowManagerFuncs;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    protected final ArrayList<Action> mItems = new ArrayList<>();
    protected final ArrayList<Action> mOverflowItems = new ArrayList<>();
    protected final ArrayList<Action> mPowerItems = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public class ActionsDialogLite extends SystemUIDialog implements DialogInterface, ColorExtractor.OnColorsChangedListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final MyAdapter mAdapter;
        public ScrimDrawable mBackgroundDrawable;
        public final Optional mCentralSurfacesOptional;
        public final SysuiColorExtractor mColorExtractor;
        public ViewGroup mContainer;
        public final Context mContext;
        public final GestureDetector mGestureDetector;
        protected GestureDetector.SimpleOnGestureListener mGestureListener;
        public MultiListLayout mGlobalActionsLayout;
        public final boolean mKeyguardShowing;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LightBarController mLightBarController;
        public final LockPatternUtils mLockPatternUtils;
        public final NotificationShadeWindowController mNotificationShadeWindowController;
        public final GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4 mOnBackInvokedCallback;
        public final Runnable mOnRefreshCallback;
        public final MyOverflowAdapter mOverflowAdapter;
        public GlobalActionsPopupMenu mOverflowPopup;
        public OnBackInvokedDispatcher mOverriddenBackDispatcher;
        public final MyPowerOptionsAdapter mPowerOptionsAdapter;
        public Dialog mPowerOptionsDialog;
        public final UiEventLogger mUiEventLogger;
        public float mWindowDimAmount;

        /* renamed from: -$$Nest$mopenShadeAndDismiss, reason: not valid java name */
        public static void m1252$$Nest$mopenShadeAndDismiss(ActionsDialogLite actionsDialogLite) {
            actionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
            int i = 0;
            if (((Boolean) actionsDialogLite.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8(i)).orElse(Boolean.FALSE)).booleanValue()) {
                actionsDialogLite.mCentralSurfacesOptional.ifPresent(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9(i));
            } else {
                actionsDialogLite.mCentralSurfacesOptional.ifPresent(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9(1));
            }
            actionsDialogLite.dismiss();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4] */
        public ActionsDialogLite(Context context, int i, MyAdapter myAdapter, MyOverflowAdapter myOverflowAdapter, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, LightBarController lightBarController, NotificationShadeWindowController notificationShadeWindowController, Runnable runnable, boolean z, MyPowerOptionsAdapter myPowerOptionsAdapter, UiEventLogger uiEventLogger, Optional<CentralSurfaces> optional, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils) {
            super(context, i, false);
            new Binder();
            this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.ActionsDialogLite.this;
                    int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                    actionsDialogLite.mUiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_CLOSE_BACK);
                    actionsDialogLite.dismiss();
                }
            };
            this.mGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onDown(MotionEvent motionEvent) {
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 <= 0.0f || Math.abs(f2) <= Math.abs(f) || motionEvent.getY() > ((Integer) ActionsDialogLite.this.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8(1)).orElse(0)).intValue()) {
                        return false;
                    }
                    ActionsDialogLite.m1252$$Nest$mopenShadeAndDismiss(ActionsDialogLite.this);
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 >= 0.0f || f2 <= f || motionEvent.getY() > ((Integer) ActionsDialogLite.this.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8(2)).orElse(0)).intValue()) {
                        return false;
                    }
                    ActionsDialogLite.m1252$$Nest$mopenShadeAndDismiss(ActionsDialogLite.this);
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onSingleTapUp(MotionEvent motionEvent) {
                    ActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
                    ActionsDialogLite.this.cancel();
                    return false;
                }
            };
            this.mContext = context;
            this.mAdapter = myAdapter;
            this.mOverflowAdapter = myOverflowAdapter;
            this.mPowerOptionsAdapter = myPowerOptionsAdapter;
            this.mColorExtractor = sysuiColorExtractor;
            this.mLightBarController = lightBarController;
            this.mNotificationShadeWindowController = notificationShadeWindowController;
            this.mOnRefreshCallback = runnable;
            this.mKeyguardShowing = z;
            this.mUiEventLogger = uiEventLogger;
            this.mCentralSurfacesOptional = optional;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public final void dismiss() {
            GlobalActionsPopupMenu globalActionsPopupMenu = this.mOverflowPopup;
            if (globalActionsPopupMenu != null) {
                globalActionsPopupMenu.dismiss();
            }
            Dialog dialog = this.mPowerOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setRequestTopUi("GlobalActionsDialogLite", false);
            super.dismiss();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final int getHeight() {
            return -1;
        }

        @Override // android.app.Dialog
        public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mOverriddenBackDispatcher;
            if (onBackInvokedDispatcher != null) {
                return onBackInvokedDispatcher;
            }
            return super.getOnBackInvokedDispatcher();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final int getWidth() {
            return -1;
        }

        @Override // android.app.Dialog
        public final void onBackPressed() {
            super.onBackPressed();
            this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_BACK);
        }

        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            if (this.mKeyguardShowing) {
                if ((i & 2) != 0) {
                    updateColors(colorExtractor.getColors(2), true);
                }
            } else if ((i & 1) != 0) {
                updateColors(colorExtractor.getColors(1), true);
            }
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            getWindow().setTitle(getContext().getString(R.string.accessibility_quick_settings_power_menu));
            setContentView(R.layout.global_actions_grid_lite);
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            viewGroup2.setClipChildren(false);
            viewGroup2.setClipToPadding(false);
            MultiListLayout multiListLayout = (MultiListLayout) findViewById(R.id.global_actions_view);
            this.mGlobalActionsLayout = multiListLayout;
            multiListLayout.getListView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.2
                @Override // android.view.View.AccessibilityDelegate
                public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    accessibilityEvent.getText().add(ActionsDialogLite.this.mContext.getString(android.R.string.permdesc_readSyncStats));
                    return true;
                }
            });
            MultiListLayout multiListLayout2 = this.mGlobalActionsLayout;
            multiListLayout2.mRotationListener = new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0(this);
            multiListLayout2.mAdapter = this.mAdapter;
            ViewGroup viewGroup3 = (ViewGroup) findViewById(R.id.global_actions_container);
            this.mContainer = viewGroup3;
            viewGroup3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    GlobalActionsDialogLite.ActionsDialogLite.this.mGestureDetector.onTouchEvent(motionEvent);
                    return view.onTouchEvent(motionEvent);
                }
            });
            View findViewById = findViewById(R.id.global_actions_overflow_button);
            if (findViewById != null) {
                if (this.mOverflowAdapter.getCount() > 0) {
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.ActionsDialogLite.this;
                            int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                            actionsDialogLite.getClass();
                            GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(new ContextThemeWrapper(actionsDialogLite.mContext, 2132017557), false);
                            globalActionsPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view2, int i2, long j) {
                                    GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = GlobalActionsDialogLite.ActionsDialogLite.this.mOverflowAdapter;
                                    GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i2);
                                    if (!(action instanceof GlobalActionsDialogLite.SilentModeTriStateAction)) {
                                        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                                        if (globalActionsDialogLite.mDialog != null) {
                                            globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                            GlobalActionsDialogLite.this.mDialog.dismiss();
                                        } else {
                                            Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
                                        }
                                        action.onPress();
                                    }
                                }
                            });
                            globalActionsPopupMenu.mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda7
                                @Override // android.widget.AdapterView.OnItemLongClickListener
                                public final boolean onItemLongClick(AdapterView adapterView, View view2, int i2, long j) {
                                    GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = GlobalActionsDialogLite.ActionsDialogLite.this.mOverflowAdapter;
                                    GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i2);
                                    if (action instanceof GlobalActionsDialogLite.LongPressAction) {
                                        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                                        if (globalActionsDialogLite.mDialog != null) {
                                            globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                            GlobalActionsDialogLite.this.mDialog.dismiss();
                                        } else {
                                            Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                                        }
                                        return ((GlobalActionsDialogLite.LongPressAction) action).onLongPress();
                                    }
                                    return false;
                                }
                            };
                            globalActionsPopupMenu.setAnchorView(actionsDialogLite.findViewById(R.id.global_actions_overflow_button));
                            globalActionsPopupMenu.setAdapter(actionsDialogLite.mOverflowAdapter);
                            actionsDialogLite.mOverflowPopup = globalActionsPopupMenu;
                            globalActionsPopupMenu.show();
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams.setMarginEnd(0);
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams);
                } else {
                    findViewById.setVisibility(8);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams2.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(R.dimen.global_actions_side_margin));
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams2);
                }
            }
            if (this.mBackgroundDrawable == null) {
                this.mBackgroundDrawable = new ScrimDrawable();
            }
            boolean userHasTrust = this.mKeyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
            if (this.mKeyguardShowing && userHasTrust) {
                this.mLockPatternUtils.requireCredentialEntry(KeyguardUpdateMonitor.getCurrentUser());
                final View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.global_actions_toast, this.mContainer, false);
                final int recommendedTimeoutMillis = ((AccessibilityManager) getContext().getSystemService("accessibility")).getRecommendedTimeoutMillis(PluginLock.VERSION, 2);
                inflate.setVisibility(0);
                inflate.setAlpha(0.0f);
                this.mContainer.addView(inflate);
                inflate.animate().alpha(1.0f).setDuration(333L).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        inflate.animate().alpha(0.0f).setDuration(333L).setStartDelay(recommendedTimeoutMillis).setListener(null);
                    }
                });
            }
            this.mWindowDimAmount = getWindow().getAttributes().dimAmount;
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.Dialog, android.view.Window.Callback
        public final void onDetachedFromWindow() {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
        }

        @Override // android.app.Dialog
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (!this.mGestureDetector.onTouchEvent(motionEvent) && !super.onTouchEvent(motionEvent)) {
                return false;
            }
            return true;
        }

        public void setBackDispatcherOverride(OnBackInvokedDispatcher onBackInvokedDispatcher) {
            this.mOverriddenBackDispatcher = onBackInvokedDispatcher;
        }

        @Override // android.app.Dialog
        public final void show() {
            boolean z;
            super.show();
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setRequestTopUi("GlobalActionsDialogLite", true);
            if (getWindow().getAttributes().windowAnimations == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                startAnimation(null, true);
                setDismissOverride(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3(this, 0));
            }
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final void start() {
            MultiListLayout multiListLayout = this.mGlobalActionsLayout;
            if (multiListLayout.mAdapter != null) {
                multiListLayout.onUpdateList();
                LightBarController lightBarController = this.mLightBarController;
                if (!lightBarController.mGlobalActionsVisible) {
                    lightBarController.mGlobalActionsVisible = true;
                    lightBarController.reevaluate();
                }
                if (this.mBackgroundDrawable instanceof ScrimDrawable) {
                    this.mColorExtractor.addOnColorsChangedListener(this);
                    updateColors(this.mColorExtractor.mNeutralColorsLock, false);
                    return;
                }
                return;
            }
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }

        public final void startAnimation(final GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3 globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3, final boolean z) {
            float dimension;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            Resources resources = getContext().getResources();
            if (z) {
                dimension = resources.getDimension(17105618);
                ofFloat.setInterpolator(Interpolators.STANDARD);
                ofFloat.setDuration(resources.getInteger(android.R.integer.config_burnInProtectionMaxRadius));
            } else {
                dimension = resources.getDimension(17105619);
                ofFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
                ofFloat.setDuration(resources.getInteger(android.R.integer.config_burnInProtectionMaxVerticalOffset));
            }
            final float f = dimension;
            final Window window = getWindow();
            final int rotation = window.getWindowManager().getDefaultDisplay().getRotation();
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float f2;
                    float f3;
                    GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.ActionsDialogLite.this;
                    boolean z2 = z;
                    Window window2 = window;
                    float f4 = f;
                    int i = rotation;
                    int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                    actionsDialogLite.getClass();
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (z2) {
                        f2 = floatValue;
                    } else {
                        f2 = 1.0f - floatValue;
                    }
                    actionsDialogLite.mGlobalActionsLayout.setAlpha(f2);
                    window2.setDimAmount(actionsDialogLite.mWindowDimAmount * f2);
                    if (z2) {
                        f3 = (1.0f - floatValue) * f4;
                    } else {
                        f3 = f4 * floatValue;
                    }
                    if (i != 0) {
                        if (i != 1) {
                            if (i != 2) {
                                if (i == 3) {
                                    actionsDialogLite.mGlobalActionsLayout.setTranslationY(f3);
                                    return;
                                }
                                return;
                            }
                            actionsDialogLite.mGlobalActionsLayout.setTranslationX(-f3);
                            return;
                        }
                        actionsDialogLite.mGlobalActionsLayout.setTranslationY(-f3);
                        return;
                    }
                    actionsDialogLite.mGlobalActionsLayout.setTranslationX(f3);
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.4
                public int mPreviousLayerType;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(this.mPreviousLayerType, null);
                    Runnable runnable = globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3;
                    if (runnable != null) {
                        runnable.run();
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator, boolean z2) {
                    this.mPreviousLayerType = ActionsDialogLite.this.mGlobalActionsLayout.getLayerType();
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(2, null);
                }
            });
            ofFloat.start();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final void stop() {
            LightBarController lightBarController = this.mLightBarController;
            if (lightBarController.mGlobalActionsVisible) {
                lightBarController.mGlobalActionsVisible = false;
                lightBarController.reevaluate();
            }
            this.mColorExtractor.removeOnColorsChangedListener(this);
        }

        public final void updateColors(ColorExtractor.GradientColors gradientColors, boolean z) {
            ScrimDrawable scrimDrawable = this.mBackgroundDrawable;
            if (!(scrimDrawable instanceof ScrimDrawable)) {
                return;
            }
            scrimDrawable.setColor(EmergencyPhoneWidget.BG_COLOR, z);
            View decorView = getWindow().getDecorView();
            if (gradientColors.supportsDarkText()) {
                decorView.setSystemUiVisibility(8208);
            } else {
                decorView.setSystemUiVisibility(0);
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AirplaneModeAction extends ToggleAction {
        public AirplaneModeAction() {
            super(GlobalActionsDialogLite.this, android.R.drawable.ic_media_route_connected_dark_23_mtrl, android.R.drawable.ic_media_route_connected_dark_25_mtrl, android.R.string.permdesc_receiveSms, android.R.string.permdesc_receiveMms, android.R.string.permdesc_receiveBootCompleted);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void changeStateFromPress(boolean z) {
            ToggleState toggleState;
            if (GlobalActionsDialogLite.this.mHasTelephony && !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                if (z) {
                    toggleState = ToggleState.TurningOn;
                } else {
                    toggleState = ToggleState.TurningOff;
                }
                this.mState = toggleState;
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void onToggle(boolean z) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mHasTelephony && ((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                globalActionsDialogLite.mIsWaitingForEcmExit = true;
                Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                globalActionsDialogLite.mContext.startActivity(intent);
                return;
            }
            GlobalActionsDialogLite.m1251$$Nest$mchangeAirplaneModeSystemSetting(globalActionsDialogLite, z);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    class BugReportAction extends SinglePressAction implements LongPressAction {
        public BugReportAction() {
            super(android.R.drawable.ic_media_route_connected_dark_27_mtrl, android.R.string.config_wimaxManagerClassname);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                globalActionsDialogLite.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_stopTcpDump);
                globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_LONG_PRESS);
                globalActionsDialogLite.mIActivityManager.requestFullBugReport();
            } catch (RemoteException unused) {
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.BugReportAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        GlobalActionsDialogLite.this.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_startTcpDump);
                        GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_PRESS);
                        if (!GlobalActionsDialogLite.this.mIActivityManager.launchBugReportHandlerApp()) {
                            Log.w("GlobalActionsDialogLite", "Bugreport handler could not be launched");
                            GlobalActionsDialogLite.this.mIActivityManager.requestInteractiveBugReport();
                        }
                    } catch (RemoteException unused) {
                    }
                }
            }, r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            if (!Build.isDebuggable()) {
                return false;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mGlobalSettings.getIntForUser(0, ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().id, "bugreport_in_power_menu") == 0 || !((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().isAdmin()) {
                return false;
            }
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class CurrentUserProvider {
        public boolean mFetched;
        public UserInfo mUserInfo;

        public /* synthetic */ CurrentUserProvider(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        public final UserInfo get() {
            if (!this.mFetched) {
                this.mFetched = true;
                this.mUserInfo = ((UserTrackerImpl) GlobalActionsDialogLite.this.mUserTracker).getUserInfo();
            }
            return this.mUserInfo;
        }

        private CurrentUserProvider() {
            this.mUserInfo = null;
            this.mFetched = false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class EmergencyAffordanceAction extends EmergencyAction {
        public EmergencyAffordanceAction() {
            super(android.R.drawable.expander_open_mtrl_alpha, android.R.string.permdesc_disableKeyguard);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite.this.mEmergencyAffordanceManager.performEmergencyCall();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    class EmergencyDialerAction extends EmergencyAction {
        public /* synthetic */ EmergencyDialerAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mMetricsLogger.action(1569);
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_EMERGENCY_DIALER_PRESS);
            if (globalActionsDialogLite.mTelecomManager != null) {
                globalActionsDialogLite.mCentralSurfacesOptional.ifPresent(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9(2));
                Intent createLaunchEmergencyDialerIntent = globalActionsDialogLite.mTelecomManager.createLaunchEmergencyDialerIntent(null);
                createLaunchEmergencyDialerIntent.addFlags(343932928);
                createLaunchEmergencyDialerIntent.putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 2);
                globalActionsDialogLite.mContext.startActivityAsUser(createLaunchEmergencyDialerIntent, ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserHandle());
            }
        }

        private EmergencyDialerAction() {
            super(R.drawable.ic_emergency_star, android.R.string.permdesc_disableKeyguard);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public enum GlobalActionsEvent implements UiEventLogger.UiEventEnum {
        GA_POWER_MENU_OPEN(337),
        GA_POWER_MENU_CLOSE(471),
        GA_BUGREPORT_PRESS(344),
        GA_BUGREPORT_LONG_PRESS(345),
        GA_EMERGENCY_DIALER_PRESS(346),
        GA_SCREENSHOT_PRESS(347),
        /* JADX INFO: Fake field, exist only in values array */
        GA_SCREENSHOT_LONG_PRESS(348),
        GA_SHUTDOWN_PRESS(802),
        GA_SHUTDOWN_LONG_PRESS(VpnErrorValues.ERROR_STORING_PROXY_PASSWORD),
        GA_REBOOT_PRESS(349),
        GA_REBOOT_LONG_PRESS(VpnErrorValues.ERROR_INVALID_PROXY_CONFIGURATION),
        GA_LOCKDOWN_PRESS(354),
        /* JADX INFO: Fake field, exist only in values array */
        GA_OPEN_QS(VpnErrorValues.ERROR_INVALID_IPV6_CONFIGURATION),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_LONG_PRESS_POWER(806),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_LONG_PRESS_POWER(807),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_LONG_PRESS_POWER(808),
        GA_CLOSE_BACK(809),
        GA_CLOSE_TAP_OUTSIDE(810),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_POWER_VOLUP(811);

        private final int mId;

        GlobalActionsEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    class LockDownAction extends SinglePressAction {
        public LockDownAction() {
            super(android.R.drawable.ic_media_route_connected_dark_30_mtrl, android.R.string.permdesc_handoverStatus);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mLockPatternUtils.requireStrongAuth(32, -1);
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_LOCKDOWN_PRESS);
            try {
                globalActionsDialogLite.mIWindowManager.lockNow((Bundle) null);
                globalActionsDialogLite.mBackgroundExecutor.execute(new GlobalActionsDialogLite$LogoutAction$$ExternalSyntheticLambda0(this, 1));
            } catch (RemoteException e) {
                Log.e("GlobalActionsDialogLite", "Error while trying to lock device.", e);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class LogoutAction extends SinglePressAction {
        public /* synthetic */ LogoutAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            postDelayed(new GlobalActionsDialogLite$LogoutAction$$ExternalSyntheticLambda0(this, 0), r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }

        private LogoutAction() {
            super(android.R.drawable.ic_media_route_connecting_dark_13_mtrl, android.R.string.permdesc_install_shortcut);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface LongPressAction extends Action {
        boolean onLongPress();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MyAdapter extends MultiListLayout.MultiListAdapter {
        public MyAdapter() {
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            return false;
        }

        public final int countItems(boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < GlobalActionsDialogLite.this.mItems.size(); i2++) {
                GlobalActionsDialogLite.this.mItems.get(i2).shouldBeSeparated();
                if (!z) {
                    i++;
                }
            }
            return i;
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final int countListItems() {
            return countItems(false);
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final int countSeparatedItems() {
            return countItems(true);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return countItems(false) + countItems(true);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            Context context = GlobalActionsDialogLite.this.mContext;
            View create = item.create(context, view, viewGroup, LayoutInflater.from(context));
            create.setOnClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(this, i, 0));
            if (item instanceof LongPressAction) {
                create.setOnLongClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(this, i, 0));
            }
            return create;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final boolean shouldBeSeparated(int i) {
            getItem(i).shouldBeSeparated();
            return false;
        }

        @Override // android.widget.Adapter
        public final Action getItem(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < GlobalActionsDialogLite.this.mItems.size(); i3++) {
                Action action = GlobalActionsDialogLite.this.mItems.get(i3);
                if (GlobalActionsDialogLite.this.shouldShowAction(action)) {
                    if (i2 == i) {
                        return action;
                    }
                    i2++;
                }
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("position ", i, " out of range of showable actions, filtered count=");
            m.append(getCount());
            m.append(", keyguardshowing=false, provisioned=false");
            GlobalActionsDialogLite.this.getClass();
            GlobalActionsDialogLite.this.getClass();
            throw new IllegalArgumentException(m.toString());
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MyOverflowAdapter extends BaseAdapter {
        public MyOverflowAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return GlobalActionsDialogLite.this.mOverflowItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mOverflowItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i);
            if (action == null) {
                IconCompat$$ExternalSyntheticOutline0.m("No overflow action found at position: ", i, "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(R.layout.controls_more_item, viewGroup, false);
            }
            TextView textView = (TextView) view;
            if (action.getMessageResId() != 0) {
                textView.setText(action.getMessageResId());
            } else {
                textView.setText(action.getMessage());
            }
            return textView;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MyPowerOptionsAdapter extends BaseAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;

        public MyPowerOptionsAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return GlobalActionsDialogLite.this.mPowerItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mPowerItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
            if (action == null) {
                IconCompat$$ExternalSyntheticOutline0.m("No power options action found at position: ", i, "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(R.layout.global_actions_power_item, viewGroup, false);
            }
            view.setOnClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(this, i, 1));
            if (action instanceof LongPressAction) {
                view.setOnLongClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(this, i, 1));
            }
            ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
            TextView textView = (TextView) view.findViewById(android.R.id.message);
            textView.setSelected(true);
            imageView.setImageDrawable(action.getIcon(GlobalActionsDialogLite.this.mContext));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (action.getMessage() != null) {
                textView.setText(action.getMessage());
            } else {
                textView.setText(action.getMessageResId());
            }
            return view;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class PowerOptionsAction extends SinglePressAction {
        public /* synthetic */ PowerOptionsAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite != null) {
                Context context = actionsDialogLite.mContext;
                MyPowerOptionsAdapter myPowerOptionsAdapter = actionsDialogLite.mPowerOptionsAdapter;
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.global_actions_power_dialog, (ViewGroup) null);
                for (int i = 0; i < myPowerOptionsAdapter.getCount(); i++) {
                    viewGroup.addView(myPowerOptionsAdapter.getView(i, null, viewGroup));
                }
                Resources resources = context.getResources();
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewGroup);
                Window window = dialog.getWindow();
                window.setType(2020);
                window.setTitle("");
                window.setBackgroundDrawable(resources.getDrawable(R.drawable.control_background, context.getTheme()));
                window.addFlags(131072);
                actionsDialogLite.mPowerOptionsDialog = dialog;
                dialog.show();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }

        private PowerOptionsAction() {
            super(R.drawable.ic_settings_power, android.R.string.permdesc_manageNetworkPolicy);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    final class RestartAction extends SinglePressAction implements LongPressAction {
        public RestartAction() {
            super(android.R.drawable.item_background_material_light, android.R.string.permdesc_modifyNetworkAccounting);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_LONG_PRESS);
            if (globalActionsDialogLite.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            globalActionsDialogLite.mWindowManagerFuncs.reboot(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_PRESS);
            globalActionsDialogLite.mWindowManagerFuncs.reboot(false);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    class ScreenshotAction extends SinglePressAction {
        public ScreenshotAction() {
            super(android.R.drawable.jog_dial_arrow_long_right_red, android.R.string.permdesc_persistentActivity);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ScreenshotAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                    globalActionsDialogLite.mScreenshotHelper.takeScreenshot(0, globalActionsDialogLite.mHandler, (Consumer) null);
                    GlobalActionsDialogLite.this.mMetricsLogger.action(1282);
                    GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SCREENSHOT_PRESS);
                }
            }, r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean shouldShow() {
            if (1 == GlobalActionsDialogLite.this.mContext.getResources().getInteger(android.R.integer.kg_security_flipper_weight)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    final class ShutDownAction extends SinglePressAction implements LongPressAction {
        public ShutDownAction() {
            super(android.R.drawable.ic_lock_power_off, android.R.string.permdesc_killBackgroundProcesses);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_LONG_PRESS);
            if (globalActionsDialogLite.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            globalActionsDialogLite.mWindowManagerFuncs.reboot(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_PRESS);
            globalActionsDialogLite.mWindowManagerFuncs.shutdown();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class SilentModeToggleAction extends ToggleAction {
        public SilentModeToggleAction() {
            super(GlobalActionsDialogLite.this, android.R.drawable.ic_contact_picture_holo_dark, android.R.drawable.ic_contact_picture_3, android.R.string.permdesc_readSms, android.R.string.permdesc_readHistoryBookmarks, android.R.string.permdesc_readContacts);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void onToggle(boolean z) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (z) {
                globalActionsDialogLite.mAudioManager.setRingerMode(0);
            } else {
                globalActionsDialogLite.mAudioManager.setRingerMode(2);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class ToggleAction implements Action {
        public final int mDisabledIconResid;
        public final int mDisabledStatusMessageResId;
        public final int mEnabledIconResId;
        public final int mEnabledStatusMessageResId;
        public ToggleState mState = ToggleState.Off;

        public ToggleAction(GlobalActionsDialogLite globalActionsDialogLite, int i, int i2, int i3, int i4, int i5) {
            this.mEnabledIconResId = i;
            this.mDisabledIconResid = i2;
            this.mEnabledStatusMessageResId = i4;
            this.mDisabledStatusMessageResId = i5;
        }

        public void changeStateFromPress(boolean z) {
            ToggleState toggleState;
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            this.mState = toggleState;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            int i;
            boolean z = false;
            View inflate = layoutInflater.inflate(R.layout.global_actions_grid_item_v2, viewGroup, false);
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            layoutParams.width = -2;
            inflate.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) inflate.findViewById(android.R.id.icon);
            TextView textView = (TextView) inflate.findViewById(android.R.id.message);
            boolean isEnabled = isEnabled();
            if (textView != null) {
                textView.setText(getMessageResId());
                textView.setEnabled(isEnabled);
                textView.setSelected(true);
            }
            if (imageView != null) {
                ToggleState toggleState = this.mState;
                if (toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) {
                    z = true;
                }
                if (z) {
                    i = this.mEnabledIconResId;
                } else {
                    i = this.mDisabledIconResid;
                }
                imageView.setImageDrawable(context.getDrawable(i));
                imageView.setEnabled(isEnabled);
            }
            inflate.setEnabled(isEnabled);
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            boolean z;
            int i;
            ToggleState toggleState = this.mState;
            if (toggleState != ToggleState.On && toggleState != ToggleState.TurningOn) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                i = this.mEnabledIconResId;
            } else {
                i = this.mDisabledIconResid;
            }
            return context.getDrawable(i);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            boolean z;
            ToggleState toggleState = this.mState;
            if (toggleState != ToggleState.On && toggleState != ToggleState.TurningOn) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return this.mEnabledStatusMessageResId;
            }
            return this.mDisabledStatusMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return !this.mState.inTransition();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            boolean z;
            if (this.mState.inTransition()) {
                Log.w("GlobalActionsDialogLite", "shouldn't be able to toggle when in transition");
                return;
            }
            if (this.mState != ToggleState.On) {
                z = true;
            } else {
                z = false;
            }
            onToggle(z);
            changeStateFromPress(z);
        }

        public abstract void onToggle(boolean z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public enum ToggleState {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);

        private final boolean mInTransition;

        ToggleState(boolean z) {
            this.mInTransition = z;
        }

        public final boolean inTransition() {
            return this.mInTransition;
        }
    }

    /* renamed from: -$$Nest$mchangeAirplaneModeSystemSetting, reason: not valid java name */
    public static void m1251$$Nest$mchangeAirplaneModeSystemSetting(GlobalActionsDialogLite globalActionsDialogLite, boolean z) {
        GlobalSettings globalSettings = globalActionsDialogLite.mGlobalSettings;
        globalSettings.putIntForUser(z ? 1 : 0, globalSettings.getUserId(), "airplane_mode_on");
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        intent.putExtra("state", z);
        globalActionsDialogLite.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (!globalActionsDialogLite.mHasTelephony) {
            ToggleState toggleState = ToggleState.Off;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$7, android.database.ContentObserver] */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$8] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$5, android.content.BroadcastReceiver] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$6, java.lang.Object] */
    public GlobalActionsDialogLite(Context context, GlobalActions.GlobalActionsManager globalActionsManager, AudioManager audioManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, TelephonyListenerManager telephonyListenerManager, GlobalSettings globalSettings, SecureSettings secureSettings, VibratorHelper vibratorHelper, Resources resources, ConfigurationController configurationController, UserTracker userTracker, KeyguardStateController keyguardStateController, UserManager userManager, TrustManager trustManager, IActivityManager iActivityManager, TelecomManager telecomManager, MetricsLogger metricsLogger, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, LightBarController lightBarController, NotificationShadeWindowController notificationShadeWindowController, IWindowManager iWindowManager, Executor executor, UiEventLogger uiEventLogger, RingerModeTracker ringerModeTracker, Handler handler, PackageManager packageManager, Optional<CentralSurfaces> optional, KeyguardUpdateMonitor keyguardUpdateMonitor, DialogLaunchAnimator dialogLaunchAnimator) {
        ToggleState toggleState = ToggleState.Off;
        this.mIsWaitingForEcmExit = false;
        this.mDialogPressDelay = 850;
        ?? r8 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) && !"android.intent.action.SCREEN_OFF".equals(action)) {
                    if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false)) {
                        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                        if (globalActionsDialogLite.mIsWaitingForEcmExit) {
                            globalActionsDialogLite.mIsWaitingForEcmExit = false;
                            GlobalActionsDialogLite.m1251$$Nest$mchangeAirplaneModeSystemSetting(globalActionsDialogLite, true);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String stringExtra = intent.getStringExtra("reason");
                if (!"globalactions".equals(stringExtra)) {
                    GlobalActionsDialogLite.this.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                    AnonymousClass8 anonymousClass8 = GlobalActionsDialogLite.this.mHandler;
                    anonymousClass8.sendMessage(anonymousClass8.obtainMessage(0, stringExtra));
                }
            }
        };
        this.mBroadcastReceiver = r8;
        ?? r9 = new TelephonyCallback.ServiceStateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.6
            @Override // android.telephony.TelephonyCallback.ServiceStateListener
            public final void onServiceStateChanged(ServiceState serviceState) {
                boolean z;
                ToggleState toggleState2;
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (!globalActionsDialogLite.mHasTelephony) {
                    return;
                }
                if (globalActionsDialogLite.mAirplaneModeOn == null) {
                    Log.d("GlobalActionsDialogLite", "Service changed before actions created");
                    return;
                }
                if (serviceState.getState() == 3) {
                    z = true;
                } else {
                    z = false;
                }
                GlobalActionsDialogLite globalActionsDialogLite2 = GlobalActionsDialogLite.this;
                if (z) {
                    toggleState2 = ToggleState.On;
                } else {
                    toggleState2 = ToggleState.Off;
                }
                globalActionsDialogLite2.getClass();
                globalActionsDialogLite2.mAirplaneModeOn.mState = toggleState2;
                throw null;
            }
        };
        this.mPhoneStateListener = r9;
        ?? r10 = new ContentObserver(this.mMainHandler) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                GlobalActionsDialogLite.this.onAirplaneModeChanged();
            }
        };
        this.mAirplaneModeObserver = r10;
        this.mHandler = new Handler() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.8
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                ToggleState toggleState2;
                int i = message.what;
                if (i != 0) {
                    boolean z = true;
                    if (i == 1) {
                        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                        if (!globalActionsDialogLite.mHasVibrator) {
                            Integer value = ((RingerModeTrackerImpl) globalActionsDialogLite.mRingerModeTracker).ringerMode.getValue();
                            if (value == null || value.intValue() == 2) {
                                z = false;
                            }
                            ToggleAction toggleAction = (ToggleAction) globalActionsDialogLite.mSilentModeAction;
                            if (z) {
                                toggleState2 = ToggleState.On;
                            } else {
                                toggleState2 = ToggleState.Off;
                            }
                            toggleAction.mState = toggleState2;
                        }
                        GlobalActionsDialogLite.this.getClass();
                        throw null;
                    }
                    return;
                }
                if (GlobalActionsDialogLite.this.mDialog != null) {
                    if (BcSmartspaceDataPlugin.UI_SURFACE_DREAM.equals(message.obj)) {
                        GlobalActionsDialogLite.this.mDialog.hide();
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    } else {
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    }
                    GlobalActionsDialogLite.this.mDialog = null;
                }
            }
        };
        this.mContext = context;
        this.mWindowManagerFuncs = globalActionsManager;
        this.mAudioManager = audioManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGlobalSettings = globalSettings;
        this.mResources = resources;
        this.mConfigurationController = configurationController;
        this.mUserTracker = userTracker;
        this.mUserManager = userManager;
        this.mTrustManager = trustManager;
        this.mIActivityManager = iActivityManager;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mIWindowManager = iWindowManager;
        this.mBackgroundExecutor = executor;
        this.mRingerModeTracker = ringerModeTracker;
        this.mMainHandler = handler;
        this.mSmallestScreenWidthDp = resources.getConfiguration().smallestScreenWidthDp;
        this.mOrientation = resources.getConfiguration().orientation;
        this.mCentralSurfacesOptional = optional;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        broadcastDispatcher.registerReceiver(intentFilter, r8);
        this.mHasTelephony = packageManager.hasSystemFeature("android.hardware.telephony");
        ((ArrayList) telephonyListenerManager.mTelephonyCallback.mServiceStateListeners).add(r9);
        telephonyListenerManager.updateListening();
        globalSettings.registerContentObserverForUser(Settings.Global.getUriFor("airplane_mode_on"), true, (ContentObserver) r10, globalSettings.getUserId());
        this.mHasVibrator = vibratorHelper.hasVibrator();
        boolean z = !resources.getBoolean(17891895);
        this.mShowSilentToggle = z;
        if (z) {
            ((RingerModeTrackerImpl) ringerModeTracker).ringerMode.observe(this, new Observer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    sendEmptyMessage(1);
                }
            });
        }
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
    }

    public final void addIfShouldShowAction(List list, Action action) {
        if (shouldShowAction(action)) {
            ((ArrayList) list).add(action);
        }
    }

    public void createActionItems() {
        String[] strArr;
        String str;
        UserInfo userInfo;
        String[] strArr2;
        String str2;
        boolean z;
        Drawable drawable;
        String str3;
        if (!this.mHasVibrator) {
            this.mSilentModeAction = new SilentModeToggleAction();
        } else {
            this.mSilentModeAction = new SilentModeTriStateAction(this.mAudioManager, this.mHandler);
        }
        this.mAirplaneModeOn = new AirplaneModeAction();
        onAirplaneModeChanged();
        this.mItems.clear();
        this.mOverflowItems.clear();
        this.mPowerItems.clear();
        String[] defaultActions = getDefaultActions();
        Action shutDownAction = new ShutDownAction();
        Action restartAction = new RestartAction();
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        CurrentUserProvider currentUserProvider = new CurrentUserProvider(this, 0 == true ? 1 : 0);
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            addIfShouldShowAction(arrayList, new EmergencyAffordanceAction());
            arraySet.add(ImsProfile.PDN_EMERGENCY);
        }
        int i = 0;
        while (i < defaultActions.length) {
            String str4 = defaultActions[i];
            if (arraySet.contains(str4)) {
                strArr = defaultActions;
            } else {
                if (GLOBAL_ACTION_KEY_POWER.equals(str4)) {
                    addIfShouldShowAction(arrayList, shutDownAction);
                } else if (SubRoom.EXTRA_KEY_AIRPLANE_MODE.equals(str4)) {
                    addIfShouldShowAction(arrayList, this.mAirplaneModeOn);
                } else if ("bugreport".equals(str4)) {
                    if (shouldDisplayBugReport(currentUserProvider.get())) {
                        addIfShouldShowAction(arrayList, new BugReportAction());
                    }
                } else if ("silent".equals(str4)) {
                    if (this.mShowSilentToggle) {
                        addIfShouldShowAction(arrayList, this.mSilentModeAction);
                    }
                } else if ("users".equals(str4)) {
                    if (SystemProperties.getBoolean("fw.power_user_switcher", z2)) {
                        UserInfo userInfo2 = currentUserProvider.get();
                        if (this.mUserManager.isUserSwitcherEnabled()) {
                            for (final UserInfo userInfo3 : this.mUserManager.getUsers()) {
                                if (userInfo3.supportsSwitchToByUser()) {
                                    if (userInfo2 != null ? userInfo2.id == userInfo3.id : userInfo3.id == 0) {
                                        z = true;
                                    } else {
                                        z = z2;
                                    }
                                    String str5 = userInfo3.iconPath;
                                    if (str5 != null) {
                                        drawable = Drawable.createFromPath(str5);
                                    } else {
                                        drawable = null;
                                    }
                                    Drawable drawable2 = drawable;
                                    int i2 = android.R.drawable.ic_perm_group_network;
                                    String str6 = userInfo3.name;
                                    if (str6 == null) {
                                        str6 = "Primary";
                                    }
                                    if (z) {
                                        str3 = " ✔";
                                    } else {
                                        str3 = "";
                                    }
                                    userInfo = userInfo2;
                                    strArr2 = defaultActions;
                                    str2 = str4;
                                    addIfShouldShowAction(arrayList, new SinglePressAction(i2, drawable2, str6.concat(str3)) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.4
                                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                                        public final void onPress() {
                                            try {
                                                GlobalActionsDialogLite.this.mIActivityManager.switchUser(userInfo3.id);
                                            } catch (RemoteException e) {
                                                Log.e("GlobalActionsDialogLite", "Couldn't switch user " + e);
                                            }
                                        }

                                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                                        public final boolean showBeforeProvisioning() {
                                            return false;
                                        }
                                    });
                                } else {
                                    userInfo = userInfo2;
                                    strArr2 = defaultActions;
                                    str2 = str4;
                                }
                                str4 = str2;
                                userInfo2 = userInfo;
                                defaultActions = strArr2;
                                z2 = false;
                            }
                        }
                    }
                } else {
                    strArr = defaultActions;
                    str = str4;
                    if ("settings".equals(str)) {
                        addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.jog_dial_dimple_dim, android.R.string.permdesc_readCellBroadcasts) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.1
                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final void onPress() {
                                Intent intent = new Intent("android.settings.SETTINGS");
                                intent.addFlags(335544320);
                                GlobalActionsDialogLite.this.mContext.startActivity(intent);
                            }

                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final boolean showBeforeProvisioning() {
                                return true;
                            }
                        });
                    } else if ("lockdown".equals(str)) {
                        if (shouldDisplayLockdown(currentUserProvider.get())) {
                            addIfShouldShowAction(arrayList, new LockDownAction());
                        }
                    } else if ("voiceassist".equals(str)) {
                        addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.list_highlight_inactive, android.R.string.permdesc_readSyncSettings) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.3
                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final void onPress() {
                                Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
                                intent.addFlags(335544320);
                                GlobalActionsDialogLite.this.mContext.startActivity(intent);
                            }

                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final boolean showBeforeProvisioning() {
                                return true;
                            }
                        });
                    } else if ("assist".equals(str)) {
                        addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.ic_clear_search_api_disabled_holo_dark, android.R.string.permdesc_callCompanionApp) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.2
                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final void onPress() {
                                Intent intent = new Intent("android.intent.action.ASSIST");
                                intent.addFlags(335544320);
                                GlobalActionsDialogLite.this.mContext.startActivity(intent);
                            }

                            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                            public final boolean showBeforeProvisioning() {
                                return true;
                            }
                        });
                    } else if ("restart".equals(str)) {
                        addIfShouldShowAction(arrayList, restartAction);
                    } else if ("screenshot".equals(str)) {
                        addIfShouldShowAction(arrayList, new ScreenshotAction());
                    } else if ("logout".equals(str)) {
                        if (this.mDevicePolicyManager.isLogoutEnabled() && currentUserProvider.get() != null && currentUserProvider.get().id != 0) {
                            addIfShouldShowAction(arrayList, new LogoutAction(this, 0));
                        }
                    } else {
                        int i3 = 0;
                        if (ImsProfile.PDN_EMERGENCY.equals(str)) {
                            if (shouldDisplayEmergency()) {
                                addIfShouldShowAction(arrayList, new EmergencyDialerAction(this, i3));
                            }
                        } else {
                            Log.e("GlobalActionsDialogLite", "Invalid global action key " + str);
                        }
                    }
                    arraySet.add(str);
                }
                strArr = defaultActions;
                str = str4;
                arraySet.add(str);
            }
            i++;
            defaultActions = strArr;
            z2 = false;
        }
        if (arrayList.contains(shutDownAction) && arrayList.contains(restartAction) && arrayList.size() > getMaxShownPowerItems()) {
            int min = Math.min(arrayList.indexOf(restartAction), arrayList.indexOf(shutDownAction));
            arrayList.remove(shutDownAction);
            arrayList.remove(restartAction);
            this.mPowerItems.add(shutDownAction);
            this.mPowerItems.add(restartAction);
            arrayList.add(min, new PowerOptionsAction(this, 0));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if (this.mItems.size() < getMaxShownPowerItems()) {
                this.mItems.add(action);
            } else {
                this.mOverflowItems.add(action);
            }
        }
    }

    @Override // com.android.systemui.plugins.GlobalActionsPanelPlugin.Callbacks
    public final void dismissGlobalActionsMenu() {
        removeMessages(0);
        sendEmptyMessage(0);
    }

    public String[] getDefaultActions() {
        return this.mResources.getStringArray(17236221);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mLifecycle;
    }

    public int getMaxShownPowerItems() {
        return this.mResources.getInteger(R.integer.power_menu_lite_max_rows) * this.mResources.getInteger(R.integer.power_menu_lite_max_columns);
    }

    public BugReportAction makeBugReportActionForTesting() {
        return new BugReportAction();
    }

    public EmergencyDialerAction makeEmergencyDialerActionForTesting() {
        return new EmergencyDialerAction(this, 0);
    }

    public ScreenshotAction makeScreenshotActionForTesting() {
        return new ScreenshotAction();
    }

    public final void onAirplaneModeChanged() {
        ToggleState toggleState;
        if (!this.mHasTelephony && this.mAirplaneModeOn != null) {
            boolean z = false;
            if (this.mGlobalSettings.getInt("airplane_mode_on", 0) == 1) {
                z = true;
            }
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            this.mAirplaneModeOn.mState = toggleState;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ActionsDialogLite actionsDialogLite = this.mDialog;
        if (actionsDialogLite != null && actionsDialogLite.isShowing()) {
            int i = configuration.smallestScreenWidthDp;
            if (i != this.mSmallestScreenWidthDp || configuration.orientation != this.mOrientation) {
                this.mSmallestScreenWidthDp = i;
                this.mOrientation = configuration.orientation;
                ActionsDialogLite actionsDialogLite2 = this.mDialog;
                actionsDialogLite2.mOnRefreshCallback.run();
                GlobalActionsPopupMenu globalActionsPopupMenu = actionsDialogLite2.mOverflowPopup;
                if (globalActionsPopupMenu != null) {
                    globalActionsPopupMenu.dismiss();
                }
                Dialog dialog = actionsDialogLite2.mPowerOptionsDialog;
                if (dialog != null) {
                    dialog.dismiss();
                }
                MultiListLayout multiListLayout = actionsDialogLite2.mGlobalActionsLayout;
                if (multiListLayout.mAdapter != null) {
                    multiListLayout.onUpdateList();
                    return;
                }
                throw new IllegalStateException("mAdapter must be set before calling updateList");
            }
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mDialog == dialogInterface) {
            this.mDialog = null;
        }
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_CLOSE);
        this.mWindowManagerFuncs.onGlobalActionsHidden();
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        this.mMetricsLogger.visible(1568);
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_OPEN);
    }

    public void setZeroDialogPressDelayForTesting() {
        this.mDialogPressDelay = 0;
    }

    public boolean shouldDisplayBugReport(UserInfo userInfo) {
        if (userInfo == null || !userInfo.isAdmin() || this.mGlobalSettings.getIntForUser(0, userInfo.id, "bugreport_in_power_menu") == 0) {
            return false;
        }
        return true;
    }

    public boolean shouldDisplayEmergency() {
        return this.mHasTelephony;
    }

    public boolean shouldDisplayLockdown(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        int i = userInfo.id;
        if (!((KeyguardStateControllerImpl) this.mKeyguardStateController).mSecure) {
            return false;
        }
        int strongAuthForUser = this.mLockPatternUtils.getStrongAuthForUser(i);
        if (strongAuthForUser != 0 && strongAuthForUser != 4) {
            return false;
        }
        return true;
    }

    public boolean shouldShowAction(Action action) {
        if (!action.showBeforeProvisioning()) {
            return false;
        }
        return action.shouldShow();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class SinglePressAction implements Action {
        public final Drawable mIcon;
        public final int mIconResId;
        public final CharSequence mMessage;
        public final int mMessageResId;

        public SinglePressAction(int i, int i2) {
            this.mIconResId = i;
            this.mMessageResId = i2;
            this.mMessage = null;
            this.mIcon = null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            GlobalActionsDialogLite.this.getClass();
            View inflate = layoutInflater.inflate(R.layout.global_actions_grid_item_lite, viewGroup, false);
            inflate.setId(View.generateViewId());
            ImageView imageView = (ImageView) inflate.findViewById(android.R.id.icon);
            TextView textView = (TextView) inflate.findViewById(android.R.id.message);
            textView.setSelected(true);
            imageView.setImageDrawable(getIcon(context));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CharSequence charSequence = this.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setText(this.mMessageResId);
            }
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                return drawable;
            }
            return context.getDrawable(this.mIconResId);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return this.mMessage;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            return this.mMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return true;
        }

        public SinglePressAction(int i, Drawable drawable, CharSequence charSequence) {
            this.mIconResId = i;
            this.mMessageResId = 0;
            this.mMessage = charSequence;
            this.mIcon = drawable;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface Action {
        View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater);

        Drawable getIcon(Context context);

        CharSequence getMessage();

        int getMessageResId();

        boolean isEnabled();

        void onPress();

        default boolean shouldShow() {
            return true;
        }

        boolean showBeforeProvisioning();

        default void shouldBeSeparated() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class EmergencyAction extends SinglePressAction {
        public EmergencyAction(int i, int i2) {
            super(i, i2);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View create = super.create(context, view, viewGroup, layoutInflater);
            GlobalActionsDialogLite.this.getClass();
            int color = context.getResources().getColor(R.color.global_actions_lite_text);
            int color2 = context.getResources().getColor(R.color.global_actions_lite_emergency_icon);
            int color3 = context.getResources().getColor(R.color.global_actions_lite_emergency_background);
            TextView textView = (TextView) create.findViewById(android.R.id.message);
            textView.setTextColor(color);
            textView.setSelected(true);
            ImageView imageView = (ImageView) create.findViewById(android.R.id.icon);
            imageView.getDrawable().setTint(color2);
            imageView.setBackgroundTintList(ColorStateList.valueOf(color3));
            create.setBackgroundTintList(ColorStateList.valueOf(color3));
            return create;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void shouldBeSeparated() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class SilentModeTriStateAction implements Action, View.OnClickListener {
        public static final int[] ITEM_IDS = {android.R.id.signature, android.R.id.signatureOrSystem, android.R.id.signed};
        public final AudioManager mAudioManager;
        public final Handler mHandler;

        public SilentModeTriStateAction(AudioManager audioManager, Handler handler) {
            this.mAudioManager = audioManager;
            this.mHandler = handler;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            boolean z;
            View inflate = layoutInflater.inflate(android.R.layout.media_controller, viewGroup, false);
            int ringerMode = this.mAudioManager.getRingerMode();
            for (int i = 0; i < 3; i++) {
                View findViewById = inflate.findViewById(ITEM_IDS[i]);
                if (ringerMode == i) {
                    z = true;
                } else {
                    z = false;
                }
                findViewById.setSelected(z);
                findViewById.setTag(Integer.valueOf(i));
                findViewById.setOnClickListener(this);
            }
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            return 0;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return true;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (!(view.getTag() instanceof Integer)) {
                return;
            }
            this.mAudioManager.setRingerMode(((Integer) view.getTag()).intValue());
            this.mHandler.sendEmptyMessageDelayed(0, 300L);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
        }
    }
}

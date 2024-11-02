package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.AuthKeyguardMessageArea;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.KeyguardSecSecurityContainer;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityCallback;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardTextBuilder;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.biometrics.KeyguardBiometricToastView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import com.android.systemui.keyguard.data.BouncerViewImpl;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntConsumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SecStatusBarKeyguardViewManager extends StatusBarKeyguardViewManager {
    public BiometricUnlockController mBiometricUnlockController;
    public final KeyguardFastBioUnlockController mFastBioUnlockController;
    public boolean mIsCoverClosed;
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy mKeyguardUnlockAnimationControllerLazy;
    public boolean mKeyguardUnlocking;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public final KeyguardViewMediatorHelper mKeyguardViewMediatorHelper;
    public boolean mLastCoverClosed;
    public boolean mLastKeyguardUnlocking;
    public boolean mLaunchEditMode;
    public ViewGroup mLockIconContainer;
    public View mNotificationContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda0 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final Lazy mShadeControllerLazy;
    public ShadeViewController mShadeViewController;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final AnonymousClass2 mSystemUIWidgetCallback;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$2] */
    public SecStatusBarKeyguardViewManager(KeyguardViewMediatorHelper keyguardViewMediatorHelper, SecRotationWatcher secRotationWatcher, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, NotificationMediaManager notificationMediaManager, KeyguardMessageAreaController.Factory factory, Optional<SysUIUnfoldComponent> optional, Lazy lazy2, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, FeatureFlags featureFlags, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter) {
        super(context, viewMediatorCallback, lockPatternUtils, sysuiStatusBarStateController, configurationController, keyguardUpdateMonitor, dreamOverlayStateController, navigationModeController, dockManager, notificationShadeWindowController, keyguardStateController, notificationMediaManager, factory, optional, lazy2, latencyTracker, keyguardSecurityModel, featureFlags, primaryBouncerCallbackInteractor, primaryBouncerInteractor, bouncerView, alternateBouncerInteractor, udfpsOverlayInteractor, activityStarter);
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                secStatusBarKeyguardViewManager.updateLockContainerMargin();
                BiometricUnlockController biometricUnlockController = secStatusBarKeyguardViewManager.mBiometricUnlockController;
                KeyguardBiometricToastView keyguardBiometricToastView = biometricUnlockController.mBiometricToastView;
                if (keyguardBiometricToastView != null) {
                    keyguardBiometricToastView.setViewAttribution(biometricUnlockController.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
                }
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                if (DeviceType.isTablet() && LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && biometricSourceType.equals(BiometricSourceType.FINGERPRINT)) {
                    SecStatusBarKeyguardViewManager.this.updateLockContainerMargin();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onEmergencyCallAction() {
                boolean z = LsRune.SECURITY_ESIM;
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                if (z) {
                    secStatusBarKeyguardViewManager.mKeyguardUpdateManager.clearESimRemoved();
                }
                if (((KeyguardStateControllerImpl) secStatusBarKeyguardViewManager.mKeyguardStateController).mOccluded) {
                    secStatusBarKeyguardViewManager.reset(true);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                if (secStatusBarKeyguardViewManager.isFullscreenBouncer()) {
                    secStatusBarKeyguardViewManager.updateStates();
                }
            }
        };
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.2
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                if ((j & 512) != 0) {
                    SecStatusBarKeyguardViewManager.this.updateBouncerNavigationBar(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
                }
            }
        };
        this.mRotationWatcher = secRotationWatcher;
        this.mKeyguardViewMediatorHelper = keyguardViewMediatorHelper;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mFastBioUnlockController = keyguardFastBioUnlockController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardUnlockAnimationControllerLazy = lazy;
        this.mShadeControllerLazy = lazy2;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, boolean z2, boolean z3) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            cancelPendingWakeupAction();
            if (this.mDozing && !isWakeAndUnlocking() && (!z3 || keyguardStateControllerImpl.mOccluded)) {
                this.mPendingWakeupAction = new StatusBarKeyguardViewManager.DismissWithActionRequest(onDismissAction, runnable, z, null);
                return;
            }
            boolean isFullscreenBouncer = isFullscreenBouncer();
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (isFullscreenBouncer && this.mKeyguardUpdateManager.isActiveDismissAction()) {
                primaryBouncerInteractor.show(true);
            }
            if (!z) {
                if (z2) {
                    primaryBouncerInteractor.setDismissAction(onDismissAction, runnable);
                    primaryBouncerInteractor.show(true);
                } else {
                    primaryBouncerInteractor.setDismissAction(onDismissAction, runnable);
                }
            } else if (z2) {
                this.mAfterKeyguardGoneAction = onDismissAction;
                this.mKeyguardGoneCancelAction = runnable;
                primaryBouncerInteractor.show(true);
            } else {
                this.mAfterKeyguardGoneAction = onDismissAction;
            }
        }
        updateStates();
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void folderOpenAndDismiss() {
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide) {
            dismissAndCollapse();
        } else {
            ((ShadeControllerImpl) ((ShadeController) this.mShadeControllerLazy.get())).makeExpandedInvisible();
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final Bundle getBouncerMessage() {
        boolean z;
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
            KeyguardSecurityModel.SecurityMode securityMode = keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = keyguardSecSecurityContainerController.mKeyguardPluginController;
            keyguardPluginControllerImpl.getClass();
            Bundle bundle = new Bundle();
            int i = KeyguardPluginControllerImpl.AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
            boolean z2 = false;
            if (i != 1 && i != 2 && i != 3) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                KeyguardTextBuilder keyguardTextBuilder = keyguardPluginControllerImpl.mKeyguardTextBuilder;
                bundle.putCharSequence(PluginSubScreen.KEY_BOUNCER_MESSAGE, keyguardTextBuilder.getDefaultSecurityMessage(securityMode));
                int bouncerPromptReason = keyguardPluginControllerImpl.mViewMediatorCallback.getBouncerPromptReason();
                keyguardPluginControllerImpl.mPromptReason = bouncerPromptReason;
                String str = "";
                if (bouncerPromptReason != 0 && keyguardPluginControllerImpl.mKeyguardUpdateMonitor.getLockoutAttemptDeadline() <= 0) {
                    String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, keyguardPluginControllerImpl.mPromptReason);
                    if (!TextUtils.isEmpty(promptSecurityMessage)) {
                        int i2 = keyguardPluginControllerImpl.mPromptReason;
                        if (i2 == 2 || i2 == 7 || i2 == 17) {
                            z2 = true;
                        }
                        if (z2) {
                            keyguardPluginControllerImpl.mStrongAuthPopupMessage = keyguardTextBuilder.getStrongAuthTimeOutMessage(securityMode);
                        } else {
                            keyguardPluginControllerImpl.mStrongAuthPopupMessage = "";
                        }
                        str = promptSecurityMessage;
                    }
                }
                bundle.putCharSequence(PluginSubScreen.KEY_STRONG_AUTH_MESSAGE, str);
                bundle.putCharSequence(PluginSubScreen.KEY_STRONG_AUTH_POPUP_MESSAGE, keyguardPluginControllerImpl.mStrongAuthPopupMessage);
                return bundle;
            }
            return bundle;
        }
        return null;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final Bundle getIncorrectBouncerMessage() {
        boolean z;
        String str;
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
            KeyguardSecurityModel.SecurityMode securityMode = keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = keyguardSecSecurityContainerController.mKeyguardPluginController;
            keyguardPluginControllerImpl.getClass();
            Bundle bundle = new Bundle();
            int[] iArr = KeyguardPluginControllerImpl.AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;
            int i = iArr[securityMode.ordinal()];
            int i2 = 0;
            if (i != 1 && i != 2 && i != 3) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
                int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(2);
                int i3 = iArr[securityMode.ordinal()];
                if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 == 3) {
                            i2 = R.string.kg_incorrect_password;
                        }
                    } else {
                        i2 = R.string.kg_incorrect_pattern;
                    }
                } else {
                    i2 = R.string.kg_incorrect_pin;
                }
                Context context = keyguardPluginControllerImpl.mContext;
                String string = context.getResources().getString(i2);
                if (remainingAttempt > 0) {
                    string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, " ("), context.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                }
                bundle.putCharSequence(PluginSubScreen.KEY_INCORRECT_BOUNCER_MESSAGE, string);
                int remainingAttempt2 = keyguardUpdateMonitor.getRemainingAttempt(1);
                int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
                if (remainingAttempt2 > 0) {
                    str = keyguardPluginControllerImpl.mKeyguardTextBuilder.getWarningAutoWipeMessage(failedUnlockAttempts, remainingAttempt2);
                } else {
                    str = "";
                }
                bundle.putCharSequence(PluginSubScreen.KEY_AUTO_WIPE_WARNING_MESSAGE, str);
                return bundle;
            }
            return bundle;
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean getLastNavBarVisible() {
        boolean z;
        if (LsRune.COVER_SUPPORTED && this.mLastCoverClosed) {
            z = true;
        } else {
            z = false;
        }
        boolean z2 = this.mLastKeyguardUnlocking;
        if ((!z && super.getLastNavBarVisible()) || z2) {
            return true;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final ViewGroup getLockIconContainer() {
        return this.mLockIconContainer;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void hide(long j, long j2) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded) {
            if (!((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide) {
                ((KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class)).start(false);
            } else {
                Log.d("SecStatusBarKeyguardViewManager", "leaveOpenOnKeyguardHide true");
            }
        }
        super.hide(j, j2);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void hideBouncer(boolean z) {
        boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (z2 && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isUnlockOnFoldOpened() && LsRune.SECURITY_SWIPE_BOUNCER && !primaryBouncerInteractor.isSwipeBouncer()) {
            android.util.Log.d("SecStatusBarKeyguardViewManager", "do not hideBouncer when folder is opening");
            return;
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            setShowSwipeBouncer(false);
        }
        primaryBouncerInteractor.hide();
        BouncerViewDelegate delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            ((KeyguardSecSecurityContainer) ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mView).setVisibility(4);
        }
        if (z) {
            ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._primaryBouncerInflate.setValue(Boolean.FALSE);
        }
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            cancelPostAuthActions();
        }
        cancelPendingWakeupAction();
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final boolean interceptRestKey(KeyEvent keyEvent) {
        boolean dispatchKeyEvent;
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
            if (keyguardSecSecurityContainerController.interceptMediaKey(keyEvent)) {
                dispatchKeyEvent = true;
            } else {
                if (!((KeyguardSecSecurityContainer) keyguardSecSecurityContainerController.mView).hasFocus()) {
                    ((KeyguardSecSecurityContainer) keyguardSecSecurityContainerController.mView).requestFocus();
                }
                dispatchKeyEvent = ((KeyguardSecSecurityContainer) keyguardSecSecurityContainerController.mView).dispatchKeyEvent(keyEvent);
            }
            if (dispatchKeyEvent) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final boolean isLaunchEditMode() {
        return this.mLaunchEditMode;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isNavBarVisible() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        if (LsRune.COVER_SUPPORTED && this.mIsCoverClosed) {
            z = true;
        } else {
            z = false;
        }
        boolean z5 = this.mKeyguardUnlocking;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z6 = keyguardStateControllerImpl.mShowing;
        if (z6 && !keyguardStateControllerImpl.mOccluded) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.mIsDlsOverlay && centralSurfacesImpl.mState == 1) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (z4) {
                z2 = true;
                if (!z6 && !keyguardStateControllerImpl.mOccluded && ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if ((z && super.isNavBarVisible()) || z5 || z2 || z3) {
                    return true;
                }
                return false;
            }
        }
        z2 = false;
        if (!z6) {
        }
        z3 = false;
        if (z) {
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final boolean isPanelFullyCollapsed() {
        return ((NotificationPanelViewController) this.mShadeViewController).isFullyCollapsed();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean needsFullscreenBouncer() {
        return SecurityUtils.checkFullscreenBouncer(this.mKeyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()));
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onBackPressed() {
        boolean z;
        resetKeyguardDismissAction();
        if (!primaryBouncerIsShowing()) {
            return;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        centralSurfacesImpl.releaseGestureWakeLock();
        ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
        if (LsRune.SECURITY_ESIM) {
            this.mKeyguardUpdateManager.clearESimRemoved();
        }
        CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) this.mCentralSurfaces;
        boolean z2 = true;
        if (centralSurfacesImpl2.mScrimController.mState == ScrimState.BOUNCER_SCRIMMED) {
            z = true;
        } else {
            z = false;
        }
        boolean z3 = centralSurfacesImpl2.mBouncerShowingOverDream;
        if (!z && !z3) {
            z2 = false;
        }
        if (primaryBouncerIsScrimmed() && !needsFullscreenBouncer()) {
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).resetFoldOpenState(false);
            }
            ((NotificationPanelViewController) this.mShadeViewController).resetViews(false);
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
            if (statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
            }
            ((CentralSurfacesImpl) this.mCentralSurfaces).userActivity();
            if (LsRune.SECURITY_SWIPE_BOUNCER && this.mPrimaryBouncerInteractor.isSwipeBouncer()) {
                showBouncerOrKeyguard(z2);
            } else {
                hideBouncer(false);
            }
            updateStates();
            return;
        }
        reset(z2);
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void onCoverSwitchStateChanged(boolean z) {
        if (LsRune.SECURITY_SWIPE_BOUNCER && this.mPrimaryBouncerInteractor.isSwipeBouncer() && z) {
            setShowSwipeBouncer(false);
        }
        this.mIsCoverClosed = z;
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        hideBouncer(true);
        updateLockContainerMargin();
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void onDismissCancelled() {
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeViewController;
        KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
        int i = notificationPanelViewController.mBarState;
        keyguardStatusViewController.setKeyguardStatusViewVisibility(i, i, false, false);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onFinishedGoingToSleep() {
        BouncerViewDelegate delegate;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (primaryBouncerInteractor.isFullyShowing() && (delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate()) != null) {
            ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.onPause();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onKeyguardFadedAway() {
        final int i = 0;
        if (LsRune.SECURITY_BOUNCER_WINDOW) {
            android.util.Log.d("SecStatusBarKeyguardViewManager", "onKeyguardFadedAway");
            ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.post(new Runnable(this) { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda1
                public final /* synthetic */ SecStatusBarKeyguardViewManager f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            ((NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            return;
                        default:
                            ((NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            return;
                    }
                }
            });
        } else {
            final int i2 = 1;
            this.mNotificationContainer.postDelayed(new Runnable(this) { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda1
                public final /* synthetic */ SecStatusBarKeyguardViewManager f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            ((NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            return;
                        default:
                            ((NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            return;
                    }
                }
            }, 100L);
        }
        ((NotificationPanelViewController) this.mShadeViewController).resetViewGroupFade();
        ((CentralSurfacesImpl) this.mCentralSurfaces).finishKeyguardFadingAway();
        this.mBiometricUnlockController.finishKeyguardFadingAway();
        this.mLaunchEditMode = false;
        WindowManagerGlobal.getInstance().trimMemory(60);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedGoingToSleep() {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class);
        if (keyguardVisibilityMonitor.cancelExecToken != null) {
            Log.d("KeyguardVisible", "cancelAll");
            keyguardVisibilityMonitor.cancelExecToken(false);
        }
        super.onStartedGoingToSleep();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedWakingUp() {
        updateLockContainerMargin();
        super.onStartedWakingUp();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        if (!LsRune.SECURITY_SUB_DISPLAY_LOCK || !((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
            updateResources();
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void onTrimMemory(int i) {
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.onTrimMemory(i);
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void onWakeAndUnlock() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void postSetOccluded(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(keyguardStateControllerImpl.mShowing, keyguardStateControllerImpl.mOccluded);
        if (this.mKeyguardUpdateManager.isEarlyWakeUp() && z) {
            cancelPendingWakeupAction();
            reset(true);
        } else if (!this.mDozing || (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && z && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isBouncerOnFoldOpened())) {
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && this.mDozing) {
                Log.d("SecStatusBarKeyguardViewManager", "hideBounce even though fold opened");
            }
            reset(z);
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && z) {
            ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).resetFoldOpenState(false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void registerCentralSurfaces(CentralSurfaces centralSurfaces, ShadeSurface shadeSurface, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, ViewGroup viewGroup, NotificationStackScrollLayout notificationStackScrollLayout, KeyguardBypassController keyguardBypassController) {
        this.mBiometricUnlockController = biometricUnlockController;
        this.mLockIconContainer = viewGroup;
        this.mShadeViewController = shadeSurface;
        this.mNotificationContainer = notificationStackScrollLayout;
        this.mCentralSurfaces = centralSurfaces;
        super.mBiometricUnlockController = biometricUnlockController;
        ArrayList arrayList = this.mPrimaryBouncerCallbackInteractor.expansionCallbacks;
        StatusBarKeyguardViewManager.AnonymousClass1 anonymousClass1 = this.mExpansionCallback;
        if (!arrayList.contains(anonymousClass1)) {
            arrayList.add(anonymousClass1);
        }
        super.mShadeViewController = shadeSurface;
        if (shadeExpansionStateManager != null) {
            onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(this));
        }
        this.mBypassController = keyguardBypassController;
        super.mNotificationContainer = notificationStackScrollLayout;
        AuthKeyguardMessageArea authKeyguardMessageArea = (AuthKeyguardMessageArea) ((CentralSurfacesImpl) centralSurfaces).mNotificationShadeWindowViewController.mView.findViewById(R.id.keyguard_message_area);
        KeyguardMessageAreaController.Factory factory = this.mKeyguardMessageAreaFactory;
        this.mKeyguardMessageAreaController = new KeyguardMessageAreaController(authKeyguardMessageArea, factory.mKeyguardUpdateMonitor, factory.mConfigurationController);
        boolean z = true;
        this.mCentralSurfacesRegistered = true;
        super.mKeyguardUpdateManager.registerCallback(super.mUpdateMonitorCallback);
        ((StatusBarStateControllerImpl) super.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        if (BasicRune.NAVBAR_GESTURE || !QuickStepContract.isGesturalMode(this.mNavigationModeController.addListener(this))) {
            z = false;
        }
        this.mGesturalNav = z;
        FoldAodAnimationController foldAodAnimationController = this.mFoldAodAnimationController;
        if (foldAodAnimationController != null) {
            foldAodAnimationController.statusListeners.add(this);
        }
        this.mKeyguardUpdateManager.registerCallback(this.mUpdateMonitorCallback);
        this.mRotationWatcher.addCallback(this.mRotationConsumer);
        if (LsRune.SECURITY_BOUNCER_WINDOW) {
            ((WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class)).registerCallback(false, this.mSystemUIWidgetCallback, 512L);
        }
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).listeners.add(new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.3
            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationStarted(boolean z2, boolean z3) {
                if (z2) {
                    SecStatusBarKeyguardViewManager.this.updateStates();
                }
            }
        });
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.4
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStatePreChange(int i, int i2) {
                if (i == 1 && i2 == 0) {
                    SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                    if (!((KeyguardStateControllerImpl) secStatusBarKeyguardViewManager.mKeyguardStateController).mShowing && ((CentralSurfacesImpl) secStatusBarKeyguardViewManager.mCentralSurfaces).mBouncerShowing) {
                        secStatusBarKeyguardViewManager.updateStates();
                    }
                }
            }
        });
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void requestUnlock(String str) {
        LockscreenCredential createPasswordOrNone;
        boolean z;
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mKeyguardPluginController;
            keyguardPluginControllerImpl.getClass();
            android.util.Log.d("KeyguardPluginController", "requestUnlock");
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            boolean isEmpty = TextUtils.isEmpty(str);
            LockPatternUtils lockPatternUtils = keyguardPluginControllerImpl.mLockPatternUtils;
            if (isEmpty) {
                createPasswordOrNone = LockscreenCredential.createNone();
            } else if (lockPatternUtils.isLockPasswordEnabled(currentUser)) {
                if (LockPatternUtils.isQualityAlphabeticPassword(lockPatternUtils.getKeyguardStoredPasswordQuality(currentUser))) {
                    createPasswordOrNone = LockscreenCredential.createPasswordOrNone(str);
                } else {
                    createPasswordOrNone = LockscreenCredential.createPinOrNone(str);
                }
            } else if (lockPatternUtils.isLockPatternEnabled(currentUser)) {
                createPasswordOrNone = LockscreenCredential.createPattern(LockPatternUtils.byteArrayToPattern(str.getBytes()));
            } else {
                createPasswordOrNone = LockscreenCredential.createPasswordOrNone(str);
            }
            if (createPasswordOrNone == null) {
                android.util.Log.e("KeyguardPluginController", "credential null : failed to get credential type");
                return;
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
            int credentialTypeForUser = keyguardUpdateMonitor.getCredentialTypeForUser(currentUser);
            if (keyguardUpdateMonitor.getUserHasTrust(currentUser) || keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock()) {
                credentialTypeForUser = -1;
            }
            if (createPasswordOrNone.isNone() && credentialTypeForUser != -1) {
                NestedScrollView$$ExternalSyntheticOutline0.m("credential none, but credentialType is ", credentialTypeForUser, "KeyguardPluginController");
                return;
            }
            if (credentialTypeForUser != 1 && credentialTypeForUser != 2 && credentialTypeForUser != 3 && credentialTypeForUser != 4) {
                if (KeyguardUpdateMonitor.getCurrentUser() == currentUser) {
                    z = true;
                } else {
                    z = false;
                }
                KeyguardSecurityCallback keyguardSecurityCallback = keyguardPluginControllerImpl.mKeyguardCallback;
                keyguardSecurityCallback.reportUnlockAttempt(currentUser, 0, true);
                if (z) {
                    keyguardSecurityCallback.dismiss(currentUser, KeyguardSecurityModel.SecurityMode.Invalid, false);
                    return;
                }
                return;
            }
            AsyncTask asyncTask = keyguardPluginControllerImpl.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            LatencyTracker latencyTracker = keyguardPluginControllerImpl.mLatencyTracker;
            latencyTracker.onActionStart(3);
            latencyTracker.onActionStart(4);
            keyguardPluginControllerImpl.mPendingLockCheck = LockPatternChecker.checkCredential(lockPatternUtils, createPasswordOrNone, currentUser, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardPluginControllerImpl.1
                public final /* synthetic */ LockscreenCredential val$credential;
                public final /* synthetic */ int val$userId;

                public AnonymousClass1(int currentUser2, LockscreenCredential createPasswordOrNone2) {
                    r2 = currentUser2;
                    r3 = createPasswordOrNone2;
                }

                public final void onCancelled() {
                    KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(4);
                    r3.zeroize();
                }

                public final void onChecked(boolean z2, int i) {
                    KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(4);
                    KeyguardPluginControllerImpl keyguardPluginControllerImpl2 = KeyguardPluginControllerImpl.this;
                    keyguardPluginControllerImpl2.mPendingLockCheck = null;
                    if (!z2) {
                        KeyguardPluginControllerImpl.m55$$Nest$monPasswordChecked(keyguardPluginControllerImpl2, r2, false, i);
                    }
                    r3.zeroize();
                }

                public final void onEarlyMatched() {
                    KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(3);
                    KeyguardPluginControllerImpl.m55$$Nest$monPasswordChecked(KeyguardPluginControllerImpl.this, r2, true, 0);
                    r3.zeroize();
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void reset(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            ((NotificationPanelViewController) this.mShadeViewController).resetViews(true);
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastBioUnlockController;
            if (keyguardFastBioUnlockController.isEnabled()) {
                VisibilityController visibilityController = keyguardFastBioUnlockController.curVisibilityController;
                if (visibilityController != null) {
                    visibilityController.resetForceInvisible(true);
                }
                keyguardFastBioUnlockController.reset();
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
            if (z2 && (!this.mDozing || keyguardUpdateMonitor.isEarlyWakeUp())) {
                ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
                if (z || needsFullscreenBouncer()) {
                    hideBouncer(false);
                }
            } else {
                showBouncerOrKeyguard(z);
            }
            hideAlternateBouncer(false);
            keyguardUpdateMonitor.mHandler.obtainMessage(312).sendToTarget();
            updateStates();
            resetKeyguardDismissAction();
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void resetKeyguardDismissAction() {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        boolean willDismissWithAction = primaryBouncerInteractor.willDismissWithAction();
        if (willDismissWithAction || this.mAfterKeyguardGoneAction != null) {
            Object[] objArr = new Object[2];
            Map map = LogUtil.beginTimes;
            int i = 0;
            objArr[0] = Integer.valueOf(willDismissWithAction ? 1 : 0);
            if (this.mAfterKeyguardGoneAction != null) {
                i = 1;
            }
            objArr[1] = Integer.valueOf(i);
            LogUtil.d("SecStatusBarKeyguardViewManager", "resetKeyguardDismissAction hasDismissAction=%d hasGoneAction=%d", objArr);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        if (keyguardUpdateMonitor.isActiveDismissAction()) {
            keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
            if (needsFullscreenBouncer()) {
                primaryBouncerInteractor.show(true);
                keyguardUpdateMonitor.sendKeyguardStateUpdated(this.mLastShowing, this.mLastOccluded, this.mLastPrimaryBouncerShowing, true);
            }
        }
        BouncerViewDelegate delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
            keyguardSecSecurityContainerController.mUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
            keyguardSecSecurityContainerController.setOnDismissAction(null, null);
        }
        this.mAfterKeyguardGoneAction = null;
        ((KeyguardViewMediatorHelperImpl) this.mKeyguardViewMediatorHelper).goingAwayWithAnimation = true;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void sendKeyguardViewState(boolean z, boolean z2, boolean z3) {
        if (z != this.mLastShowing || z2 != this.mLastOccluded || z3 != this.mLastPrimaryBouncerShowing) {
            if (z3 != this.mLastPrimaryBouncerShowing && ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).playingCannedUnlockAnimation) {
                return;
            }
            this.mKeyguardUpdateManager.sendKeyguardStateUpdated(z, z2, z3, false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void setLaunchEditMode() {
        this.mLaunchEditMode = true;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void setOccluded(boolean z, boolean z2) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing && z != keyguardStateControllerImpl.mOccluded) {
            ((KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class)).start(!z);
        }
        super.setOccluded(z, z2);
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void setShowSwipeBouncer(boolean z) {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        primaryBouncerInteractor.getClass();
        android.util.Log.d("PrimaryBouncerInteractor", "setShowSwipeBouncer " + z);
        BouncerViewDelegate delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mIsSwipeBouncer = z;
        }
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).mIsSwipeBouncer = z;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void shouldSubtleWindowAnimationsForUnlock() {
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastBioUnlockController;
        if (!keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController.isFastUnlockMode()) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).isEnabledBiometricUnlockVI();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void show(Bundle bundle) {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class);
        if (keyguardVisibilityMonitor.cancelExecToken != null) {
            Log.d("KeyguardVisible", "cancelAll");
            keyguardVisibilityMonitor.cancelExecToken(false);
        }
        super.show(bundle);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void showBouncerOrKeyguard(boolean z) {
        boolean z2 = this.mDozing;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (!z2 && needsFullscreenBouncer()) {
            boolean z3 = this.mDozing;
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
            if (z3 == this.mDozing) {
                primaryBouncerInteractor.show(true);
            } else {
                android.util.Log.d("SecStatusBarKeyguardViewManager", "showBouncerOrKeyguard dozing is changed");
                showBouncerOrKeyguard(z);
            }
        } else {
            ((CentralSurfacesImpl) this.mCentralSurfaces).showKeyguard();
            if (z) {
                hideBouncer(false);
                ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._primaryBouncerInflate.setValue(Boolean.TRUE);
            }
        }
        updateStates();
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateBouncerNavigationBar(boolean z) {
        int i;
        FrameLayout frameLayout = ((CentralSurfacesImpl) this.mCentralSurfaces).mBouncerContainer;
        int systemUiVisibility = frameLayout.getSystemUiVisibility();
        if (z) {
            i = systemUiVisibility | 16;
        } else {
            i = systemUiVisibility & (-17);
        }
        frameLayout.setSystemUiVisibility(i);
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateDlsNaviBarVisibility() {
        updateNavigationBarVisibility(isNavBarVisible());
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateKeyguardUnlocking() {
        boolean z;
        if (!((KeyguardViewMediatorHelperImpl) this.mKeyguardViewMediatorHelper).isKeyguardHiding()) {
            KeyguardStateController keyguardStateController = this.mKeyguardStateController;
            if (((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway || ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway) {
                z = true;
                this.mKeyguardUnlocking = z;
            }
        }
        z = false;
        this.mKeyguardUnlocking = z;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateLastCoverClosed() {
        this.mLastCoverClosed = this.mIsCoverClosed;
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateLastKeyguardUnlocking() {
        this.mLastKeyguardUnlocking = this.mKeyguardUnlocking;
    }

    public final void updateLockContainerMargin() {
        int dimensionPixelSize;
        int i = 0;
        int rotation = DeviceState.getRotation(((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getDisplay(0).getRotation());
        if (this.mLockIconContainer != null) {
            boolean isTablet = DeviceType.isTablet();
            boolean z = true;
            Context context = this.mContext;
            if (isTablet) {
                Resources resources = context.getResources();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mLockIconContainer.getLayoutParams();
                if (isBouncerShowing() && resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin_tablet) != 0) {
                    layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin_tablet);
                } else {
                    int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height);
                    if (rotation != 1 && rotation != 3) {
                        z = false;
                    }
                    if (!z) {
                        i = resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin);
                    }
                    layoutParams.topMargin = dimensionPixelSize2 + i;
                }
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && rotation == 2) {
                    if (this.mKeyguardUpdateManager.isInDisplayFingerprintMarginAccepted()) {
                        dimensionPixelSize = DeviceState.sInDisplayFingerprintHeight;
                    } else {
                        dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.status_bar_height);
                    }
                    layoutParams.topMargin = dimensionPixelSize;
                }
                this.mLockIconContainer.setLayoutParams(layoutParams);
                return;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLockIconContainer.getLayoutParams();
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
            if (rotation != 1 && rotation != 3) {
                z = false;
            }
            if (!z) {
                i = context.getResources().getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin);
            }
            layoutParams2.topMargin = dimensionPixelSize3 + i;
            this.mLockIconContainer.setLayoutParams(layoutParams2);
        }
    }

    @Override // com.android.keyguard.KeyguardSecViewController
    public final void updateLockoutWarningMessage() {
        String str;
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mKeyguardPluginController;
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
            int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(1);
            int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
            if (remainingAttempt <= 0) {
                str = "";
            } else {
                str = keyguardPluginControllerImpl.mKeyguardTextBuilder.getWarningAutoWipeMessage(failedUnlockAttempts, remainingAttempt);
            }
            if (str.isEmpty() && keyguardPluginControllerImpl.mKeyguardUpdateMonitor.getLockoutAttemptDeadline() > 0) {
                str = keyguardPluginControllerImpl.mContext.getString(R.string.kg_too_many_failed_attempts_warning);
            }
            if (!str.isEmpty()) {
                DesktopManager desktopManager = keyguardPluginControllerImpl.mDesktopManager;
                if (((DesktopManagerImpl) desktopManager).isDesktopMode()) {
                    DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) desktopManager;
                    Lazy lazy = desktopManagerImpl.mDesktopSystemUiBinderLazy;
                    if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected() && desktopManagerImpl.isDualView()) {
                        android.util.Log.i("DesktopManager", "notifyKeyguardLockout lockout=true  msg=" + ((Object) str) + "  sub=  popupMsg=");
                        ((DesktopSystemUiBinder) lazy.get()).onLockout(true, DesktopManagerImpl.getBouncerMessage(str, ""));
                    }
                }
            }
        }
    }
}

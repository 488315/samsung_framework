package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import androidx.core.math.MathUtils;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController$Stub;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class KeyguardUnlockAnimationController extends ISysuiUnlockAnimationController$Stub implements KeyguardStateController.Callback {
    public static final Companion Companion = new Companion(null);
    public final Lazy biometricUnlockControllerLazy;
    public final Context context;
    public boolean dismissAmountThresholdsReached;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediator;
    public ILauncherUnlockAnimationController launcherUnlockController;
    public View lockscreenSmartspace;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean playingCannedUnlockAnimation;
    public final PowerManager powerManager;
    public final float roundedCornerRadius;
    public final SysuiStatusBarStateController statusBarStateController;
    public final ValueAnimator surfaceBehindAlphaAnimator;
    public final ValueAnimator surfaceBehindEntryAnimator;
    public final Matrix surfaceBehindMatrix;
    public long surfaceBehindRemoteAnimationStartTime;
    public RemoteAnimationTarget[] surfaceBehindRemoteAnimationTargets;
    public SyncRtSurfaceTransactionApplier surfaceTransactionApplier;
    public final float[] tmpFloat;
    public final ValueAnimator wallpaperCannedUnlockAnimator;
    public final WallpaperManager wallpaperManager;
    public RemoteAnimationTarget[] wallpaperTargets;
    public final ArrayList listeners = new ArrayList();
    public float surfaceBehindAlpha = 1.0f;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardUnlockAnimationController(Context context, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, PowerManager powerManager, WallpaperManager wallpaperManager) {
        this.context = context;
        this.keyguardStateController = keyguardStateController;
        this.keyguardViewMediator = lazy;
        this.keyguardViewController = keyguardViewController;
        this.featureFlags = featureFlags;
        this.biometricUnlockControllerLazy = lazy2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.powerManager = powerManager;
        this.wallpaperManager = wallpaperManager;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindAlphaAnimator = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.wallpaperCannedUnlockAnimator = ofFloat2;
        this.surfaceBehindMatrix = new Matrix();
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindEntryAnimator = ofFloat3;
        this.handler = new Handler();
        this.tmpFloat = new float[9];
        ofFloat.setDuration(175L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.surfaceBehindAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardUnlockAnimationController.this.updateSurfaceBehindAppearAmount();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                boolean z;
                float f = KeyguardUnlockAnimationController.this.surfaceBehindAlpha;
                if (f == 0.0f) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    android.util.Log.d("KeyguardUnlock", "surfaceBehindAlphaAnimator#onAnimationEnd");
                    KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                    keyguardUnlockAnimationController.surfaceBehindRemoteAnimationTargets = null;
                    keyguardUnlockAnimationController.wallpaperTargets = null;
                    ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).finishSurfaceBehindRemoteAnimation();
                    return;
                }
                android.util.Log.d("KeyguardUnlock", "skip finishSurfaceBehindRemoteAnimation surfaceBehindAlpha=" + f);
            }
        });
        ofFloat2.setDuration(633L);
        ofFloat2.setInterpolator(Interpolators.ALPHA_OUT);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.setWallpaperAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "wallpaperCannedUnlockAnimator#onAnimationEnd");
                ((KeyguardViewMediator) KeyguardUnlockAnimationController.this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        });
        ofFloat3.setDuration(200L);
        ofFloat3.setStartDelay(75L);
        ofFloat3.setInterpolator(Interpolators.TOUCH_RESPONSE);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$3$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.surfaceBehindAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardUnlockAnimationController.this.setSurfaceBehindAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), true);
            }
        });
        ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$3$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "surfaceBehindEntryAnimator#onAnimationEnd");
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                keyguardUnlockAnimationController.playingCannedUnlockAnimation = false;
                ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this);
        this.roundedCornerRadius = context.getResources().getDimensionPixelSize(17105692);
    }

    public final void finishKeyguardExitRemoteAnimationIfReachThreshold() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && !this.dismissAmountThresholdsReached && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() && ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            float f = keyguardStateControllerImpl.mDismissAmount;
            if (f >= 1.0f || (keyguardStateControllerImpl.mDismissingFromTouch && !keyguardStateControllerImpl.mFlingingToDismissKeyguardDuringSwipeGesture && f >= 0.3f)) {
                setSurfaceBehindAppearAmount(1.0f, true);
                this.dismissAmountThresholdsReached = true;
                ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        }
    }

    public long getUnlockAnimationDuration() {
        return 0L;
    }

    public final void hideKeyguardViewAfterRemoteAnimation() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            this.keyguardViewController.hide(this.surfaceBehindRemoteAnimationStartTime, 0L);
        } else {
            android.util.Log.i("KeyguardUnlock", "#hideKeyguardViewAfterRemoteAnimation called when keyguard view is not showing. Ignoring...");
        }
    }

    public void notifyFinishedKeyguardExitAnimation(boolean z) {
        View view;
        this.handler.removeCallbacksAndMessages(null);
        this.surfaceBehindAlpha = 1.0f;
        boolean z2 = true;
        setSurfaceBehindAppearAmount(1.0f, true);
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindEntryAnimator.cancel();
        this.wallpaperCannedUnlockAnimator.cancel();
        try {
            ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController != null) {
                ((ILauncherUnlockAnimationController$Stub$Proxy) iLauncherUnlockAnimationController).setUnlockAmount(1.0f, false);
            }
        } catch (RemoteException e) {
            android.util.Log.e("KeyguardUnlock", "Remote exception in notifyFinishedKeyguardExitAnimation", e);
        }
        this.surfaceBehindRemoteAnimationTargets = null;
        this.wallpaperTargets = null;
        this.playingCannedUnlockAnimation = false;
        this.dismissAmountThresholdsReached = false;
        View view2 = this.lockscreenSmartspace;
        if (view2 == null || view2.getVisibility() != 4) {
            z2 = false;
        }
        if (z2 && (view = this.lockscreenSmartspace) != null) {
            view.setVisibility(0);
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardUnlockAnimationListener) it.next()).onUnlockAnimationFinished();
        }
    }

    public void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, long j, boolean z) {
        boolean z2;
        if (this.surfaceTransactionApplier == null) {
            this.surfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(this.keyguardViewController.getViewRootImpl().getView());
        }
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.wallpaperTargets = remoteAnimationTargetArr2;
        this.surfaceBehindRemoteAnimationStartTime = j;
        if (z) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
                playCannedUnlockAnimation();
            } else {
                boolean z3 = keyguardStateControllerImpl.mDismissingFromTouch;
                android.util.Log.d("KeyguardUnlock", "fadeInSurfaceBehind");
                this.surfaceBehindAlphaAnimator.cancel();
                this.surfaceBehindAlphaAnimator.start();
            }
        } else {
            playCannedUnlockAnimation();
        }
        if (((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock() && ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).mMode != 6) {
            z2 = true;
        } else {
            z2 = false;
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardUnlockAnimationListener) it.next()).onUnlockAnimationStarted(this.playingCannedUnlockAnimation, z2);
        }
        if (!this.playingCannedUnlockAnimation) {
            finishKeyguardExitRemoteAnimationIfReachThreshold();
        }
    }

    public void onKeyguardDismissAmountChanged() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && !this.playingCannedUnlockAnimation) {
            FeatureFlags featureFlags = this.featureFlags;
            Flags.INSTANCE.getClass();
            if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.NEW_UNLOCK_SWIPE_ANIMATION) && !this.playingCannedUnlockAnimation && !this.dismissAmountThresholdsReached) {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
                if (keyguardStateControllerImpl.mShowing) {
                    float f = keyguardStateControllerImpl.mDismissAmount;
                    if (f >= 0.15f && !((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                        ((KeyguardViewMediator) this.keyguardViewMediator.get()).showSurfaceBehindKeyguard();
                    } else if (f < 0.15f && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                        ((KeyguardViewMediator) this.keyguardViewMediator.get()).hideSurfaceBehindKeyguard();
                        android.util.Log.d("KeyguardUnlock", "fadeOutSurfaceBehind");
                        this.surfaceBehindAlphaAnimator.cancel();
                        this.surfaceBehindAlphaAnimator.reverse();
                    }
                    finishKeyguardExitRemoteAnimationIfReachThreshold();
                }
            }
            if ((((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() || ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) && !this.playingCannedUnlockAnimation) {
                updateSurfaceBehindAppearAmount();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardGoingAwayChanged() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway && !((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide) {
            Companion.getClass();
        }
        boolean z = ((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway;
    }

    public void playCannedUnlockAnimation() {
        android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation");
        this.playingCannedUnlockAnimation = true;
        if (((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock()) {
            android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation, isWakeAndUnlock");
            setSurfaceBehindAppearAmount(1.0f, true);
            ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
        } else {
            android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation, surfaceBehindEntryAnimator#start");
            this.surfaceBehindEntryAnimator.start();
        }
    }

    public void setSurfaceBehindAppearAmount(float f, boolean z) {
        float f2;
        boolean z2;
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mSnappingKeyguardBackAfterSwipe) {
            f2 = f;
        } else if (!this.powerManager.isInteractive()) {
            f2 = 0.0f;
        } else {
            f2 = this.surfaceBehindAlpha;
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                int height = remoteAnimationTarget.screenSpaceBounds.height();
                float clamp = (MathUtils.clamp(f, 0.0f, 1.0f) * 0.050000012f) + 0.95f;
                boolean z3 = ((KeyguardStateControllerImpl) this.keyguardStateController).mDismissingFromTouch;
                Matrix matrix = this.surfaceBehindMatrix;
                Rect rect = remoteAnimationTarget.screenSpaceBounds;
                float f3 = height;
                matrix.setTranslate(rect.left, ((1.0f - f) * 0.05f * f3) + rect.top);
                this.surfaceBehindMatrix.postScale(clamp, clamp, this.keyguardViewController.getViewRootImpl().getWidth() / 2.0f, f3 * 0.66f);
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                View view = this.keyguardViewController.getViewRootImpl().getView();
                boolean z4 = true;
                if (view != null && view.getVisibility() == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        z4 = false;
                    }
                    if (z4) {
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.setMatrix(surfaceControl, this.surfaceBehindMatrix, this.tmpFloat);
                        transaction.setCornerRadius(surfaceControl, this.roundedCornerRadius);
                        transaction.setAlpha(surfaceControl, f2);
                        transaction.apply();
                    }
                }
                SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withMatrix(this.surfaceBehindMatrix).withCornerRadius(this.roundedCornerRadius).withAlpha(f2).build();
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.surfaceTransactionApplier;
                Intrinsics.checkNotNull(syncRtSurfaceTransactionApplier);
                syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
            }
        }
        if (z) {
            setWallpaperAppearAmount(f);
        }
    }

    public final void setWallpaperAppearAmount(float f) {
        boolean z;
        if (!this.powerManager.isInteractive()) {
            f = 0.0f;
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.wallpaperTargets;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                View view = this.keyguardViewController.getViewRootImpl().getView();
                boolean z2 = true;
                if (view != null && view.getVisibility() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        z2 = false;
                    }
                    if (z2) {
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.setAlpha(surfaceControl, f);
                        transaction.apply();
                    }
                }
                SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(f).build();
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.surfaceTransactionApplier;
                Intrinsics.checkNotNull(syncRtSurfaceTransactionApplier);
                syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
            }
        }
    }

    public final void unlockToLauncherWithInWindowAnimations() {
        View view;
        this.surfaceBehindAlpha = 1.0f;
        boolean z = false;
        setSurfaceBehindAppearAmount(1.0f, false);
        try {
            ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController != null) {
                ((ILauncherUnlockAnimationController$Stub$Proxy) iLauncherUnlockAnimationController).playUnlockAnimation(633L, true, 100L);
            }
        } catch (DeadObjectException unused) {
            android.util.Log.e("KeyguardUnlock", "launcherUnlockAnimationController was dead, but non-null. Catching exception as this should mean Launcher is in the process of being destroyed, but the IPC to System UI telling us hasn't arrived yet.");
        }
        View view2 = this.lockscreenSmartspace;
        if (view2 != null && view2.getVisibility() == 0) {
            z = true;
        }
        if (z && (view = this.lockscreenSmartspace) != null) {
            view.setVisibility(4);
        }
        this.handler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$1
            /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
            
                if ((!r2) == true) goto L18;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r5 = this;
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r0 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    dagger.Lazy r0 = r0.keyguardViewMediator
                    java.lang.Object r0 = r0.get()
                    com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
                    boolean r0 = r0.isShowingAndNotOccluded()
                    java.lang.String r1 = "KeyguardUnlock"
                    if (r0 == 0) goto L22
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r0 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    com.android.systemui.statusbar.policy.KeyguardStateController r0 = r0.keyguardStateController
                    com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0
                    boolean r0 = r0.mKeyguardGoingAway
                    if (r0 != 0) goto L22
                    java.lang.String r5 = "Finish keyguard exit animation delayed Runnable ran, but we are showing and not going away."
                    android.util.Log.e(r1, r5)
                    return
                L22:
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r0 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    android.view.RemoteAnimationTarget[] r2 = r0.wallpaperTargets
                    r3 = 0
                    if (r2 == 0) goto L34
                    int r2 = r2.length
                    r4 = 1
                    if (r2 != 0) goto L2f
                    r2 = r4
                    goto L30
                L2f:
                    r2 = r3
                L30:
                    r2 = r2 ^ r4
                    if (r2 != r4) goto L34
                    goto L35
                L34:
                    r4 = r3
                L35:
                    if (r4 == 0) goto L59
                    android.app.WallpaperManager r0 = r0.wallpaperManager
                    boolean r0 = r0.isLockscreenLiveWallpaperEnabled()
                    if (r0 == 0) goto L59
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r0 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    r0.getClass()
                    java.lang.String r2 = "fadeInWallpaper"
                    android.util.Log.d(r1, r2)
                    android.animation.ValueAnimator r1 = r0.wallpaperCannedUnlockAnimator
                    r1.cancel()
                    android.animation.ValueAnimator r0 = r0.wallpaperCannedUnlockAnimator
                    r0.start()
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r5 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    r5.hideKeyguardViewAfterRemoteAnimation()
                    goto L66
                L59:
                    com.android.systemui.keyguard.KeyguardUnlockAnimationController r5 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.this
                    dagger.Lazy r5 = r5.keyguardViewMediator
                    java.lang.Object r5 = r5.get()
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = (com.android.systemui.keyguard.KeyguardViewMediator) r5
                    r5.exitKeyguardAndFinishSurfaceBehindRemoteAnimation(r3)
                L66:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$1.run():void");
            }
        }, 100L);
    }

    public final void updateSurfaceBehindAppearAmount() {
        if (this.surfaceBehindRemoteAnimationTargets == null || this.playingCannedUnlockAnimation) {
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
            setSurfaceBehindAppearAmount(keyguardStateControllerImpl.mDismissAmount, true);
        } else if (keyguardStateControllerImpl.mDismissingFromTouch || keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe) {
            setSurfaceBehindAppearAmount((keyguardStateControllerImpl.mDismissAmount - 0.15f) / 0.15f, true);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface KeyguardUnlockAnimationListener {
        default void onUnlockAnimationFinished() {
        }

        default void onUnlockAnimationStarted(boolean z, boolean z2) {
        }
    }

    public void setCallback(KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5) {
    }

    public static /* synthetic */ void getSurfaceBehindAlphaAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceBehindEntryAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceTransactionApplier$annotations() {
    }

    public static /* synthetic */ void getWillUnlockWithInWindowLauncherAnimations$annotations() {
    }
}

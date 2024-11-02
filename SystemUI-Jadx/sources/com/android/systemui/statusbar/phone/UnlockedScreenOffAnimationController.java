package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.IDisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.TraceUtils;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class UnlockedScreenOffAnimationController implements WakefulnessLifecycle.Observer, ScreenOffAnimation {
    public float animatorDurationScale;
    public final UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1 animatorDurationScaleObserver;
    public boolean aodUiAnimationPlaying;
    public final Context context;
    public Boolean decidedToAnimateGoingToSleep;
    public final Lazy dozeParameters;
    public final GlobalSettings globalSettings;
    public final Handler handler;
    public final SecUnlockedScreenOffAnimationHelper helper;
    public boolean initialized;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardStateController keyguardStateController;
    public final Lazy keyguardViewMediatorLazy;
    public final Lazy lazyBlurController;
    public boolean lightRevealAnimationPlaying;
    public final ValueAnimator lightRevealAnimator;
    public LightRevealScrim lightRevealScrim;
    public CentralSurfaces mCentralSurfaces;
    public final PowerManager powerManager;
    public boolean shouldAnimateInKeyguard;
    public final UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 startLightRevealCallback;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1] */
    public UnlockedScreenOffAnimationController(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateControllerImpl statusBarStateControllerImpl, Lazy lazy, KeyguardStateController keyguardStateController, Lazy lazy2, GlobalSettings globalSettings, InteractionJankMonitor interactionJankMonitor, PowerManager powerManager, Handler handler, Lazy lazy3) {
        this.helper = secUnlockedScreenOffAnimationHelper;
        this.context = context;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardStateController = keyguardStateController;
        this.dozeParameters = lazy2;
        this.globalSettings = globalSettings;
        this.interactionJankMonitor = interactionJankMonitor;
        this.powerManager = powerManager;
        this.handler = handler;
        this.lazyBlurController = lazy3;
        this.animatorDurationScale = 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(LsRune.AOD_FULLSCREEN ? 650L : 750L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightRevealScrim lightRevealScrim;
                boolean z;
                LightRevealScrim lightRevealScrim2 = UnlockedScreenOffAnimationController.this.lightRevealScrim;
                LightRevealScrim lightRevealScrim3 = null;
                if (lightRevealScrim2 == null) {
                    lightRevealScrim = null;
                } else {
                    lightRevealScrim = lightRevealScrim2;
                }
                if (!(lightRevealScrim.revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim2 == null) {
                        lightRevealScrim2 = null;
                    }
                    lightRevealScrim2.setRevealAmount(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    boolean z2 = LsRune.AOD_FULLSCREEN;
                    final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                    Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SecUnlockedScreenOffAnimationHelper.this.onAmountChanged(((Number) obj).floatValue());
                        }
                    };
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (z2) {
                        consumer.accept(Float.valueOf(floatValue));
                    }
                }
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                LightRevealScrim lightRevealScrim4 = unlockedScreenOffAnimationController.lightRevealScrim;
                if (lightRevealScrim4 != null) {
                    lightRevealScrim3 = lightRevealScrim4;
                }
                if (lightRevealScrim3.interpolatedRevealAmount < 0.1f) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && unlockedScreenOffAnimationController.interactionJankMonitor.isInstrumenting(40)) {
                    UnlockedScreenOffAnimationController.this.interactionJankMonitor.end(40);
                }
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                LightRevealScrim lightRevealScrim;
                LightRevealScrim lightRevealScrim2 = UnlockedScreenOffAnimationController.this.lightRevealScrim;
                if (lightRevealScrim2 == null) {
                    lightRevealScrim = null;
                } else {
                    lightRevealScrim = lightRevealScrim2;
                }
                if (!(lightRevealScrim.revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim2 == null) {
                        lightRevealScrim2 = null;
                    }
                    lightRevealScrim2.setRevealAmount(1.0f);
                }
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationCancel$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        float f;
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationCancel deviceInteractive=" + secUnlockedScreenOffAnimationHelper3.deviceInteractive);
                        if (secUnlockedScreenOffAnimationHelper3.deviceInteractive) {
                            f = 1.0f;
                        } else {
                            f = 0.0f;
                        }
                        secUnlockedScreenOffAnimationHelper3.onAmountChanged(f);
                    }
                }, z);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.lightRevealAnimationPlaying = false;
                unlockedScreenOffAnimationController.interactionJankMonitor.end(40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationEnd$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
                        secUnlockedScreenOffAnimationHelper3.getClass();
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationEnd aodUiAnimationPlaying=" + booleanValue);
                        if (LsRune.AOD_TSP_CONTROL && secUnlockedScreenOffAnimationHelper3.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                            secUnlockedScreenOffAnimationHelper3.aodTouchModeManager.setTouchMode(0);
                        }
                        PluginAODManager pluginAODManager = (PluginAODManager) secUnlockedScreenOffAnimationHelper3.pluginAODManagerLazy.get();
                        Log.d("PluginAODManager", "onUnlockedScreenOffAnimationEnd: mAODPlugin=" + pluginAODManager.mAODPlugin);
                        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                        if (pluginAOD != null) {
                            pluginAOD.onUnlockedScreenOffAnimationEnd();
                        }
                        SecUnlockedScreenOffAnimationHelper.logD("updatePendingLock");
                        ((KeyguardViewMediator) secUnlockedScreenOffAnimationHelper3.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                        CentralSurfaces centralSurfaces = secUnlockedScreenOffAnimationHelper3.centralSurfaces;
                        Function0 function0 = null;
                        if (centralSurfaces == null) {
                            centralSurfaces = null;
                        }
                        ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard();
                        Function0 function02 = secUnlockedScreenOffAnimationHelper3.clearDecidedToAnimateGoingToSleep;
                        if (function02 != null) {
                            function0 = function02;
                        }
                        function0.invoke();
                    }
                };
                boolean z2 = UnlockedScreenOffAnimationController.this.aodUiAnimationPlaying;
                if (z) {
                    consumer.accept(Boolean.valueOf(z2));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                InteractionJankMonitor interactionJankMonitor2 = unlockedScreenOffAnimationController.interactionJankMonitor;
                CentralSurfaces centralSurfaces = unlockedScreenOffAnimationController.mCentralSurfaces;
                if (centralSurfaces == null) {
                    centralSurfaces = null;
                }
                interactionJankMonitor2.begin(((CentralSurfacesImpl) centralSurfaces).mNotificationShadeWindowView, 40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationStart$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationStart needUpdateWallpaperVisibility=" + secUnlockedScreenOffAnimationHelper3.needUpdateWallpaperVisibility);
                        if (!secUnlockedScreenOffAnimationHelper3.needUpdateWallpaperVisibility) {
                            secUnlockedScreenOffAnimationHelper3.needUpdateWallpaperVisibility = true;
                            secUnlockedScreenOffAnimationHelper3.updateWallpaperVisibility(false);
                            SecUnlockedScreenOffAnimationHelper.logD("playUnlockedScreenOffCapturedViewAnimation");
                            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
                            ofFloat2.setDuration(250L);
                            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$playUnlockedScreenOffCapturedViewAnimation$1$1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    SecUnlockedScreenOffCapturedView secUnlockedScreenOffCapturedView = SecUnlockedScreenOffAnimationHelper.this.secUnlockedScreenOffCapturedView;
                                    if (secUnlockedScreenOffCapturedView == null) {
                                        secUnlockedScreenOffCapturedView = null;
                                    }
                                    secUnlockedScreenOffCapturedView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                }
                            });
                            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$playUnlockedScreenOffCapturedViewAnimation$1$2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator2) {
                                    super.onAnimationCancel(animator2);
                                    SecUnlockedScreenOffAnimationHelper.this.getClass();
                                    SecUnlockedScreenOffAnimationHelper.logD("onAnimationCancel unlockedScreenOffCapturedViewAlphaAnimator");
                                    SecUnlockedScreenOffAnimationHelper.this.setCapturedViewVisibility(false, null);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator2) {
                                    super.onAnimationEnd(animator2);
                                    SecUnlockedScreenOffAnimationHelper.this.getClass();
                                    SecUnlockedScreenOffAnimationHelper.logD("onAnimationEnd unlockedScreenOffCapturedViewAlphaAnimator");
                                    SecUnlockedScreenOffAnimationHelper.this.setCapturedViewVisibility(false, null);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator2) {
                                    super.onAnimationStart(animator2);
                                    SecUnlockedScreenOffAnimationHelper.this.getClass();
                                    SecUnlockedScreenOffAnimationHelper.logD("onAnimationStart unlockedScreenOffCapturedViewAlphaAnimator");
                                }
                            });
                            ofFloat2.start();
                            secUnlockedScreenOffAnimationHelper3.playWallpaperAnimation();
                        }
                    }
                }, z);
            }
        });
        this.lightRevealAnimator = ofFloat;
        int i = TraceUtils.$r8$clinit;
        this.startLightRevealCallback = new UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1("startLightReveal", this);
        this.animatorDurationScaleObserver = new ContentObserver() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                GlobalSettings globalSettings2 = unlockedScreenOffAnimationController.globalSettings;
                unlockedScreenOffAnimationController.animatorDurationScale = WindowManager.fixScale(globalSettings2.getFloatForUser("animator_duration_scale", globalSettings2.getUserId(), 1.0f));
            }
        };
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void animateInKeyguard(View view, final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        this.shouldAnimateInKeyguard = false;
        view.setAlpha(0.0f);
        view.setVisibility(0);
        float y = view.getY();
        view.setY(y - (view.getHeight() * 0.1f));
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        PropertyAnimator.cancelAnimation(view, anonymousClass7);
        AnimationProperties animationProperties = new AnimationProperties();
        long j = 500;
        animationProperties.duration = j;
        PropertyAnimator.setProperty(view, anonymousClass7, y, animationProperties, true);
        InteractionJankMonitor interactionJankMonitor = this.interactionJankMonitor;
        interactionJankMonitor.cancel(41);
        AnimatableProperty.AnonymousClass7 anonymousClass72 = AnimatableProperty.ALPHA;
        PropertyAnimator.cancelAnimation(view, anonymousClass72);
        AnimationProperties animationProperties2 = new AnimationProperties();
        animationProperties2.delay = 0L;
        animationProperties2.duration = j;
        animationProperties2.mAnimationEndAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animateInKeyguard$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.aodUiAnimationPlaying = false;
                ((KeyguardViewMediator) unlockedScreenOffAnimationController.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                CentralSurfaces centralSurfaces = UnlockedScreenOffAnimationController.this.mCentralSurfaces;
                if (centralSurfaces == null) {
                    centralSurfaces = null;
                }
                ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard();
                keyguardVisibilityHelper$$ExternalSyntheticLambda0.run();
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController2 = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController2.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController2.interactionJankMonitor.end(41);
            }
        };
        animationProperties2.mAnimationCancelAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animateInKeyguard$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.aodUiAnimationPlaying = false;
                unlockedScreenOffAnimationController.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController.interactionJankMonitor.cancel(41);
            }
        };
        animationProperties2.setCustomInterpolator(View.ALPHA, Interpolators.FAST_OUT_SLOW_IN);
        PropertyAnimator.setProperty(view, anonymousClass72, 1.0f, animationProperties2, true);
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (centralSurfaces == null) {
            centralSurfaces = null;
        }
        interactionJankMonitor.begin(((CentralSurfacesImpl) centralSurfaces).mNotificationShadeWindowView, 41);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, LightRevealScrim lightRevealScrim) {
        if (!LsRune.AOD_FULLSCREEN) {
            return;
        }
        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$initialize$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(Intrinsics.areEqual(UnlockedScreenOffAnimationController.this.decidedToAnimateGoingToSleep, Boolean.FALSE));
            }
        };
        Function0 function02 = new Function0() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$initialize$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                UnlockedScreenOffAnimationController.this.decidedToAnimateGoingToSleep = null;
                return Unit.INSTANCE;
            }
        };
        final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.helper;
        secUnlockedScreenOffAnimationHelper.centralSurfaces = centralSurfaces;
        secUnlockedScreenOffAnimationHelper.secUnlockedScreenOffCapturedView = (SecUnlockedScreenOffCapturedView) ((CentralSurfacesImpl) centralSurfaces).mNotificationShadeWindowView.findViewById(R.id.sec_unlocked_screen_off_captured_view);
        secUnlockedScreenOffAnimationHelper.isFalseDecidedToAnimateGoingToSleep = function0;
        secUnlockedScreenOffAnimationHelper.clearDecidedToAnimateGoingToSleep = function02;
        secUnlockedScreenOffAnimationHelper.settingsHelper.registerCallback(secUnlockedScreenOffAnimationHelper.aodShowStateCallback, Settings.System.getUriFor("aod_show_state"));
        secUnlockedScreenOffAnimationHelper.wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$init$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                String str;
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = SecUnlockedScreenOffAnimationHelper.this;
                secUnlockedScreenOffAnimationHelper2.deviceInteractive = false;
                boolean shouldPlayUnlockedScreenOffAnimation = secUnlockedScreenOffAnimationHelper2.shouldPlayUnlockedScreenOffAnimation();
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = secUnlockedScreenOffAnimationHelper2.aodAmbientWallpaperHelper;
                boolean isAODFullScreenMode = aODAmbientWallpaperHelper.isAODFullScreenMode();
                int i = secUnlockedScreenOffAnimationHelper2.lastReason;
                List list = secUnlockedScreenOffAnimationHelper2.reasonLog;
                if (i == 7) {
                    Object orNull = CollectionsKt___CollectionsKt.getOrNull(i, list);
                    SettingsHelper settingsHelper = secUnlockedScreenOffAnimationHelper2.settingsHelper;
                    str = orNull + " allowRotation=" + settingsHelper.isLockScreenRotationAllowed() + ", rotationLock=" + settingsHelper.isRotationLocked() + ", rotation=" + secUnlockedScreenOffAnimationHelper2.curRotation;
                } else {
                    str = (String) CollectionsKt___CollectionsKt.getOrNull(i, list);
                }
                boolean z = secUnlockedScreenOffAnimationHelper2.needUpdateWallpaperVisibility;
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onStartedGoingToSleep: isAODFullScreenMode=", isAODFullScreenMode, ", shouldPlayUnlockedScreenOffAnimation=", shouldPlayUnlockedScreenOffAnimation, " / reason=");
                m.append(str);
                m.append(", needUpdateWallpaperVisibility=");
                m.append(z);
                SecUnlockedScreenOffAnimationHelper.logD(m.toString());
                if (aODAmbientWallpaperHelper.isAODFullScreenMode()) {
                    if (shouldPlayUnlockedScreenOffAnimation) {
                        ((StatusBarKeyguardViewManager) secUnlockedScreenOffAnimationHelper2.statusBarKeyguardViewManagerLazy.get()).updateNavigationBarVisibility(false);
                        Point point = new Point();
                        secUnlockedScreenOffAnimationHelper2.windowManager.getDefaultDisplay().getRealSize(point);
                        Bitmap screenshot = SemWindowManager.getInstance().screenshot(0, 1, false, new Rect(), point.x, point.y, false, 0, true);
                        SecUnlockedScreenOffAnimationHelper.logD("screenShotUnlockedScreenOffCapturedView()");
                        secUnlockedScreenOffAnimationHelper2.setCapturedViewVisibility(true, screenshot);
                        return;
                    }
                    secUnlockedScreenOffAnimationHelper2.playWallpaperAnimation();
                    return;
                }
                secUnlockedScreenOffAnimationHelper2.playWallpaperAnimation();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = SecUnlockedScreenOffAnimationHelper.this;
                secUnlockedScreenOffAnimationHelper2.deviceInteractive = true;
                secUnlockedScreenOffAnimationHelper2.setSkipAnimationInOthers(false);
                secUnlockedScreenOffAnimationHelper2.isPanelOpenedOnGoingToSleep = false;
                Job job = secUnlockedScreenOffAnimationHelper2.job;
                if (job != null && job.isActive()) {
                    job.cancel(null);
                }
                secUnlockedScreenOffAnimationHelper2.job = null;
                IRefreshRateToken iRefreshRateToken = secUnlockedScreenOffAnimationHelper2.refreshRateToken;
                if (iRefreshRateToken != null) {
                    try {
                        iRefreshRateToken.release();
                        SecUnlockedScreenOffAnimationHelper.logD("clearMaxRefreshRate");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    secUnlockedScreenOffAnimationHelper2.refreshRateToken = null;
                }
                secUnlockedScreenOffAnimationHelper2.setCapturedViewVisibility(false, null);
                Handler handler = secUnlockedScreenOffAnimationHelper2.mainHandler;
                SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 = secUnlockedScreenOffAnimationHelper2.updateWallpaperVisibilityRunnable;
                if (handler.hasCallbacks(secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1)) {
                    handler.removeCallbacks(secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1);
                }
                SecUnlockedScreenOffAnimationHelper.logD("onStartedWakingUp: needUpdateWallpaperVisibility=" + secUnlockedScreenOffAnimationHelper2.needUpdateWallpaperVisibility);
                if (secUnlockedScreenOffAnimationHelper2.needUpdateWallpaperVisibility) {
                    secUnlockedScreenOffAnimationHelper2.needUpdateWallpaperVisibility = false;
                    secUnlockedScreenOffAnimationHelper2.updateWallpaperVisibility(true);
                }
            }
        });
        secUnlockedScreenOffAnimationHelper.screenLifecycle.addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$init$2
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = SecUnlockedScreenOffAnimationHelper.this;
                SecUnlockedScreenOffAnimationHelper.logD("onScreenTurningOff: isAODFullScreenMode=" + secUnlockedScreenOffAnimationHelper2.aodAmbientWallpaperHelper.isAODFullScreenMode() + ", needUpdateWallpaperVisibility=" + secUnlockedScreenOffAnimationHelper2.needUpdateWallpaperVisibility);
                if (secUnlockedScreenOffAnimationHelper2.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                    secUnlockedScreenOffAnimationHelper2.mainHandler.post(secUnlockedScreenOffAnimationHelper2.updateWallpaperVisibilityRunnable);
                }
            }
        });
        this.initialized = true;
        this.lightRevealScrim = lightRevealScrim;
        this.mCentralSurfaces = centralSurfaces;
        GlobalSettings globalSettings = this.globalSettings;
        this.animatorDurationScale = WindowManager.fixScale(globalSettings.getFloatForUser("animator_duration_scale", globalSettings.getUserId(), 1.0f));
        globalSettings.registerContentObserverForUser(Settings.Global.getUriFor("animator_duration_scale"), false, (ContentObserver) this.animatorDurationScaleObserver, globalSettings.getUserId());
        this.wakefulnessLifecycle.addObserver(this);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isAnimationPlaying() {
        if (!this.lightRevealAnimationPlaying && !this.aodUiAnimationPlaying) {
            return false;
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isKeyguardHideDelayed() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isKeyguardShowDelayed() {
        return isAnimationPlaying();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        this.aodUiAnimationPlaying = false;
        if (((DozeParameters) this.dozeParameters.get()).canControlUnlockedScreenOff()) {
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            if (centralSurfaces == null) {
                centralSurfaces = null;
            }
            ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard(true);
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        this.decidedToAnimateGoingToSleep = null;
        this.shouldAnimateInKeyguard = false;
        boolean z = DejankUtils.STRICT_MODE_ENABLED;
        Assert.isMainThread();
        ArrayList arrayList = DejankUtils.sPendingRunnables;
        UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 = this.startLightRevealCallback;
        arrayList.remove(unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1);
        DejankUtils.sHandler.removeCallbacks(unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1);
        this.lightRevealAnimator.cancel();
        this.lightRevealAnimationPlaying = false;
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean overrideNotificationsDozeAmount() {
        if (shouldPlayUnlockedScreenOffAnimation() && isAnimationPlaying()) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateAodIcons() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateClockChange() {
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateDozingChange() {
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateInKeyguard() {
        return this.shouldAnimateInKeyguard;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayDisplayDozeTransition() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayKeyguardShow() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldHideScrimOnWakeUp() {
        return this.lightRevealAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldPlayAnimation() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0056, code lost:
    
        if (((com.android.systemui.shade.NotificationPanelViewController) ((com.android.systemui.statusbar.phone.CentralSurfacesImpl) r2).mShadeSurface).mPanelExpanded != false) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        /*
            r4 = this;
            boolean r0 = r4.initialized
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            boolean r0 = com.android.systemui.LsRune.AOD_FULLSCREEN
            if (r0 == 0) goto L11
            com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper r4 = r4.helper
            boolean r4 = r4.shouldPlayUnlockedScreenOffAnimation()
            return r4
        L11:
            dagger.Lazy r0 = r4.dozeParameters
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.phone.DozeParameters r0 = (com.android.systemui.statusbar.phone.DozeParameters) r0
            boolean r0 = r0.canControlUnlockedScreenOff()
            if (r0 != 0) goto L20
            return r1
        L20:
            java.lang.Boolean r0 = r4.decidedToAnimateGoingToSleep
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 == 0) goto L2b
            return r1
        L2b:
            android.content.Context r0 = r4.context
            android.content.ContentResolver r2 = r0.getContentResolver()
            java.lang.String r3 = "animator_duration_scale"
            java.lang.String r2 = android.provider.Settings.Global.getString(r2, r3)
            java.lang.String r3 = "0.0"
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 == 0) goto L40
            return r1
        L40:
            com.android.systemui.statusbar.StatusBarStateControllerImpl r2 = r4.statusBarStateControllerImpl
            int r2 = r2.mState
            if (r2 == 0) goto L47
            return r1
        L47:
            com.android.systemui.statusbar.phone.CentralSurfaces r2 = r4.mCentralSurfaces
            if (r2 == 0) goto L58
            if (r2 != 0) goto L4e
            r2 = 0
        L4e:
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r2 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r2
            com.android.systemui.shade.ShadeSurface r2 = r2.mShadeSurface
            com.android.systemui.shade.NotificationPanelViewController r2 = (com.android.systemui.shade.NotificationPanelViewController) r2
            boolean r2 = r2.mPanelExpanded
            if (r2 == 0) goto L5f
        L58:
            boolean r2 = r4.isAnimationPlaying()
            if (r2 != 0) goto L5f
            return r1
        L5f:
            com.android.systemui.statusbar.policy.KeyguardStateController r4 = r4.keyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r4 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r4
            boolean r4 = r4.isKeyguardScreenRotationAllowed()
            if (r4 != 0) goto L74
            android.view.Display r4 = r0.getDisplay()
            int r4 = r4.getRotation()
            if (r4 == 0) goto L74
            return r1
        L74:
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation():boolean");
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldShowAodIconsWhenShade() {
        return isAnimationPlaying();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean startAnimation() {
        long j;
        SecPanelBackgroundController secPanelBackgroundController;
        if (shouldPlayUnlockedScreenOffAnimation()) {
            boolean z = LsRune.AOD_FULLSCREEN;
            final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.helper;
            Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$startAnimation$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = SecUnlockedScreenOffAnimationHelper.this;
                    int refreshRateMode = secUnlockedScreenOffAnimationHelper2.settingsHelper.getRefreshRateMode(false);
                    if (refreshRateMode == 1 || refreshRateMode == 2) {
                        if (secUnlockedScreenOffAnimationHelper2.refreshRateToken == null) {
                            try {
                                IDisplayManager iDisplayManager = (IDisplayManager) secUnlockedScreenOffAnimationHelper2.displayManager$delegate.getValue();
                                IBinder iBinder = (IBinder) secUnlockedScreenOffAnimationHelper2.token$delegate.getValue();
                                kotlin.Lazy lazy = secUnlockedScreenOffAnimationHelper2.maxRefreshRate$delegate;
                                secUnlockedScreenOffAnimationHelper2.refreshRateToken = iDisplayManager.acquireRefreshRateMaxLimitToken(iBinder, ((Number) lazy.getValue()).intValue(), "UnlockedScreenOffAnimation");
                                SecUnlockedScreenOffAnimationHelper.logD("setMaxRefreshRate " + ((Number) lazy.getValue()).intValue() + "Hz");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        if (secUnlockedScreenOffAnimationHelper2.refreshRateToken == null) {
                            SecUnlockedScreenOffAnimationHelper.logD("setMaxRefreshRate failed");
                        }
                    }
                }
            }, z);
            this.decidedToAnimateGoingToSleep = Boolean.TRUE;
            this.shouldAnimateInKeyguard = !z;
            this.lightRevealAnimationPlaying = true;
            DejankUtils.postAfterTraversal(this.startLightRevealCallback);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$startAnimation$2
                @Override // java.lang.Runnable
                public final void run() {
                    if (!UnlockedScreenOffAnimationController.this.powerManager.isInteractive(0)) {
                        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                        unlockedScreenOffAnimationController.aodUiAnimationPlaying = !LsRune.AOD_FULLSCREEN;
                        CentralSurfaces centralSurfaces = unlockedScreenOffAnimationController.mCentralSurfaces;
                        if (centralSurfaces == null) {
                            centralSurfaces = null;
                        }
                        ((NotificationPanelViewController) ((CentralSurfacesImpl) centralSurfaces).mShadeSurface).showAodUi();
                    }
                }
            };
            if (z) {
                j = 300;
            } else {
                j = 600;
            }
            this.handler.postDelayed(runnable, ((float) j) * this.animatorDurationScale);
            if (QpRune.QUICK_PANEL_BLUR && (secPanelBackgroundController = ((SecQpBlurController) this.lazyBlurController.get()).mBackgroundController) != null) {
                ((SecPanelBackground) secPanelBackgroundController.mView).setAlpha(0.0f * secPanelBackgroundController.mMaxAlpha);
            }
            return true;
        }
        this.decidedToAnimateGoingToSleep = Boolean.FALSE;
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void onAlwaysOnChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void onScrimOpaqueChanged(boolean z) {
    }

    public /* synthetic */ UnlockedScreenOffAnimationController(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateControllerImpl statusBarStateControllerImpl, Lazy lazy, KeyguardStateController keyguardStateController, Lazy lazy2, GlobalSettings globalSettings, InteractionJankMonitor interactionJankMonitor, PowerManager powerManager, Handler handler, Lazy lazy3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(secUnlockedScreenOffAnimationHelper, context, wakefulnessLifecycle, statusBarStateControllerImpl, lazy, keyguardStateController, lazy2, globalSettings, interactionJankMonitor, powerManager, (i & 1024) != 0 ? new Handler() : handler, lazy3);
    }
}

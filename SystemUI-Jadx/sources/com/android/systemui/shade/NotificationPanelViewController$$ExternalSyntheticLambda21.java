package com.android.systemui.shade;

import android.animation.ValueAnimator;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.systemui.plugins.ClockController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda21 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda21(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 1:
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.f$0;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mBottomAreaShadeAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                notificationPanelViewController.updateKeyguardBottomAreaAlpha();
                return;
            default:
                KeyguardStatusViewController keyguardStatusViewController = NotificationPanelViewController.this.mKeyguardStatusViewController;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                ClockController clockController = keyguardStatusViewController.mKeyguardClockSwitchController.mClockEventController.clock;
                if (clockController != null) {
                    clockController.getSmallClock().getAnimations().fold(animatedFraction);
                    clockController.getLargeClock().getAnimations().fold(animatedFraction);
                    return;
                }
                return;
        }
    }
}

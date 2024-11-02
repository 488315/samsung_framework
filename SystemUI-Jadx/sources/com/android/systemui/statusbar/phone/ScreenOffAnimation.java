package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.LightRevealScrim;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface ScreenOffAnimation {
    void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0);

    void initialize(CentralSurfaces centralSurfaces, LightRevealScrim lightRevealScrim);

    boolean isAnimationPlaying();

    boolean isKeyguardHideDelayed();

    boolean isKeyguardShowDelayed();

    void onAlwaysOnChanged(boolean z);

    void onScrimOpaqueChanged(boolean z);

    boolean overrideNotificationsDozeAmount();

    boolean shouldAnimateAodIcons();

    boolean shouldAnimateClockChange();

    boolean shouldAnimateDozingChange();

    boolean shouldAnimateInKeyguard();

    boolean shouldDelayDisplayDozeTransition();

    boolean shouldDelayKeyguardShow();

    boolean shouldHideScrimOnWakeUp();

    boolean shouldPlayAnimation();

    boolean shouldShowAodIconsWhenShade();

    boolean startAnimation();
}

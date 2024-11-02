package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.view.animation.Interpolator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecAffordanceView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecAffordanceView f$0;

    public /* synthetic */ KeyguardSecAffordanceView$$ExternalSyntheticLambda0(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecAffordanceView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                keyguardSecAffordanceView.mRectangleIconAlpha = (int) ((1.0f - (keyguardSecAffordanceView.mRectangleDistanceCovered / keyguardSecAffordanceView.mScreenWidth)) * 255.0f);
                keyguardSecAffordanceView.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                keyguardSecAffordanceView.invalidate();
                return;
            case 1:
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.f$0;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView2.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (keyguardSecAffordanceView2.mIsDown) {
                    KeyguardSecAffordanceView.cancelAnimator(keyguardSecAffordanceView2.mRectangleShrinkAnimator);
                }
                keyguardSecAffordanceView2.updatePanelViews(floatValue);
                keyguardSecAffordanceView2.invalidate();
                return;
            case 2:
                KeyguardSecAffordanceView keyguardSecAffordanceView3 = this.f$0;
                Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView3.getClass();
                keyguardSecAffordanceView3.mImageScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView3.invalidate();
                return;
            case 3:
                KeyguardSecAffordanceView keyguardSecAffordanceView4 = this.f$0;
                Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView4.getClass();
                keyguardSecAffordanceView4.mVerticalScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                return;
            case 4:
                KeyguardSecAffordanceView keyguardSecAffordanceView5 = this.f$0;
                Interpolator interpolator4 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView5.getClass();
                keyguardSecAffordanceView5.mRectangleIconScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                return;
            case 5:
                this.f$0.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                return;
            case 6:
                KeyguardSecAffordanceView keyguardSecAffordanceView6 = this.f$0;
                Interpolator interpolator5 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView6.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView6.setRectangleBounds(floatValue2);
                keyguardSecAffordanceView6.updateRectangleCornerRadius(floatValue2);
                return;
            case 7:
                KeyguardSecAffordanceView keyguardSecAffordanceView7 = this.f$0;
                Interpolator interpolator6 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView7.getClass();
                keyguardSecAffordanceView7.setRectangleBounds(((Float) valueAnimator.getAnimatedValue()).floatValue());
                keyguardSecAffordanceView7.invalidate();
                return;
            default:
                KeyguardSecAffordanceView keyguardSecAffordanceView8 = this.f$0;
                keyguardSecAffordanceView8.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                keyguardSecAffordanceView8.invalidate();
                return;
        }
    }
}

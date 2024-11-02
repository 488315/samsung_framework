package com.google.android.material.chip;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SeslExpandableContainer$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ SeslExpandableContainer f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SeslExpandableContainer$$ExternalSyntheticLambda3(SeslExpandableContainer seslExpandableContainer, int i, int i2, int i3) {
        this.f$0 = seslExpandableContainer;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SeslExpandableContainer seslExpandableContainer = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = this.f$3;
        if (i > 0) {
            ObjectAnimator ofInt = ObjectAnimator.ofInt(seslExpandableContainer.mScrollView, "scrollX", i2);
            ObjectAnimator ofInt2 = ObjectAnimator.ofInt(seslExpandableContainer.mScrollView, "scrollY", 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(AnimationUtils.loadInterpolator(seslExpandableContainer.getContext(), R.interpolator.sesl_chip_default_interpolator));
            animatorSet.setDuration(i);
            animatorSet.setStartDelay(i3);
            animatorSet.playTogether(ofInt, ofInt2);
            animatorSet.start();
            return;
        }
        int i4 = SeslExpandableContainer.$r8$clinit;
        seslExpandableContainer.getClass();
    }
}

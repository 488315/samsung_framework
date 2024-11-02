package com.android.wm.shell.bubbles.bar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BubbleBarAnimationHelper {
    public final AnimatableScaleMatrix mExpandedViewContainerMatrix;
    public boolean mIsExpanded;
    public final BubbleBarLayerView mLayerView;
    public final BubblePositioner mPositioner;

    public BubbleBarAnimationHelper(Context context, BubbleBarLayerView bubbleBarLayerView, BubblePositioner bubblePositioner) {
        new PhysicsAnimator.SpringConfig(300.0f, 0.9f);
        new PhysicsAnimator.SpringConfig(900.0f, 1.0f);
        this.mExpandedViewContainerMatrix = new AnimatableScaleMatrix();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mIsExpanded = false;
        this.mLayerView = bubbleBarLayerView;
        this.mPositioner = bubblePositioner;
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(Interpolators.PANEL_CLOSE_ACCELERATED);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleBarAnimationHelper.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                BubbleBarAnimationHelper.this.getClass();
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BubbleBarAnimationHelper.this.getClass();
            }
        });
    }
}

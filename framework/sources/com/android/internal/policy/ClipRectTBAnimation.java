package com.android.internal.policy;

import android.graphics.Rect;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

/* loaded from: classes5.dex */
public class ClipRectTBAnimation extends ClipRectAnimation {
    private final int mFromTranslateY;
    private float mNormalizedTime;
    private final int mToTranslateY;
    private final Interpolator mTranslateInterpolator;

    public ClipRectTBAnimation(int fromT, int fromB, int toT, int toB, int fromTranslateY, int toTranslateY, Interpolator translateInterpolator) {
        super(0, fromT, 0, fromB, 0, toT, 0, toB);
        this.mFromTranslateY = fromTranslateY;
        this.mToTranslateY = toTranslateY;
        this.mTranslateInterpolator = translateInterpolator;
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        float normalizedTime;
        long startOffset = getStartOffset();
        long duration = getDuration();
        if (duration != 0) {
            normalizedTime = (currentTime - (getStartTime() + startOffset)) / duration;
        } else {
            normalizedTime = currentTime < getStartTime() ? 0.0f : 1.0f;
        }
        this.mNormalizedTime = normalizedTime;
        return super.getTransformation(currentTime, outTransformation);
    }

    @Override // android.view.animation.ClipRectAnimation, android.view.animation.Animation
    protected void applyTransformation(float it, Transformation tr) {
        float translationT = this.mTranslateInterpolator.getInterpolation(this.mNormalizedTime);
        int translation = (int) (this.mFromTranslateY + ((this.mToTranslateY - this.mFromTranslateY) * translationT));
        Rect oldClipRect = tr.getClipRect();
        tr.setClipRect(oldClipRect.left, (this.mFromRect.top - translation) + ((int) ((this.mToRect.top - this.mFromRect.top) * it)), oldClipRect.right, (this.mFromRect.bottom - translation) + ((int) ((this.mToRect.bottom - this.mFromRect.bottom) * it)));
    }
}

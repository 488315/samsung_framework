package com.android.systemui.volume;

import android.animation.TimeInterpolator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SystemUIInterpolators$LogDecelerateInterpolator implements TimeInterpolator {
    public final float mBase;
    public final float mDrift;
    public final float mOutputScale;
    public final float mTimeScale;

    public SystemUIInterpolators$LogDecelerateInterpolator() {
        this(400.0f, 1.4f, 0.0f);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return ((this.mDrift * f) + (1.0f - ((float) Math.pow(this.mBase, (-f) * this.mTimeScale)))) * this.mOutputScale;
    }

    private SystemUIInterpolators$LogDecelerateInterpolator(float f, float f2, float f3) {
        this.mBase = f;
        this.mDrift = f3;
        this.mTimeScale = 1.0f / f2;
        this.mOutputScale = 1.0f / ((f3 * 1.0f) + (1.0f - ((float) Math.pow(f, (-1.0f) * r5))));
    }
}

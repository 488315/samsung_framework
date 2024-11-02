package com.android.systemui.volume;

import android.animation.TimeInterpolator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SystemUIInterpolators$LogAccelerateInterpolator implements TimeInterpolator {
    public final int mBase;
    public final int mDrift;
    public final float mLogScale;

    public SystemUIInterpolators$LogAccelerateInterpolator() {
        this(100, 0);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return 1.0f - (((this.mDrift * (1.0f - f)) + (((float) (-Math.pow(this.mBase, -r8))) + 1.0f)) * this.mLogScale);
    }

    private SystemUIInterpolators$LogAccelerateInterpolator(int i, int i2) {
        this.mBase = i;
        this.mDrift = i2;
        this.mLogScale = 1.0f / ((i2 * 1.0f) + (((float) (-Math.pow(i, -1.0f))) + 1.0f));
    }
}

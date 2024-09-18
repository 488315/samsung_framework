package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class CubicEaseOut implements Interpolator {
    public CubicEaseOut() {
    }

    public CubicEaseOut(Context context, AttributeSet attrs) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t);
    }

    private float out(float t) {
        float t2 = t - 1.0f;
        return (t2 * t2 * t2) + 1.0f;
    }
}

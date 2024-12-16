package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class SineIn33 implements Interpolator {
    private static final float[][] segments = {new float[]{0.0f, 0.001f, 0.32f}, new float[]{0.32f, 0.59f, 1.0f}};

    public SineIn33() {
    }

    public SineIn33(Context context, AttributeSet attrs) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        float _loc_5 = input / 1.0f;
        int _loc_6 = segments.length;
        int _loc_9 = (int) Math.floor(_loc_6 * _loc_5);
        if (_loc_9 >= segments.length) {
            _loc_9 = segments.length - 1;
        }
        float _loc_7 = (_loc_5 - (_loc_9 * (1.0f / _loc_6))) * _loc_6;
        float[] _loc_8 = segments[_loc_9];
        float ret = ((_loc_8[0] + ((((1.0f - _loc_7) * 2.0f * (_loc_8[1] - _loc_8[0])) + ((_loc_8[2] - _loc_8[0]) * _loc_7)) * _loc_7)) * 1.0f) + 0.0f;
        return ret;
    }
}

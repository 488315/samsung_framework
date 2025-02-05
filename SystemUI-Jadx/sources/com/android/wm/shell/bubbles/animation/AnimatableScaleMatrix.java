package com.android.wm.shell.bubbles.animation;

import android.graphics.Matrix;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class AnimatableScaleMatrix extends Matrix {
    public static final AnonymousClass1 SCALE_X = new FloatPropertyCompat("matrixScaleX") { // from class: com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix.1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((AnimatableScaleMatrix) obj).mScaleX * 499.99997f;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((AnimatableScaleMatrix) obj).setScaleX(f * 0.002f);
        }
    };
    public static final AnonymousClass2 SCALE_Y = new FloatPropertyCompat("matrixScaleY") { // from class: com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix.2
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((AnimatableScaleMatrix) obj).mScaleY * 499.99997f;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((AnimatableScaleMatrix) obj).setScaleY(f * 0.002f);
        }
    };
    public float mScaleX = 1.0f;
    public float mScaleY = 1.0f;
    public float mPivotX = 0.0f;
    public float mPivotY = 0.0f;

    @Override // android.graphics.Matrix
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return false;
    }

    @Override // android.graphics.Matrix
    public final void setScale(float f, float f2, float f3, float f4) {
        this.mScaleX = f;
        this.mScaleY = f2;
        this.mPivotX = f3;
        this.mPivotY = f4;
        super.setScale(f, f2, f3, f4);
    }

    public final void setScaleX(float f) {
        this.mScaleX = f;
        super.setScale(f, this.mScaleY, this.mPivotX, this.mPivotY);
    }

    public final void setScaleY(float f) {
        this.mScaleY = f;
        super.setScale(this.mScaleX, f, this.mPivotX, this.mPivotY);
    }
}

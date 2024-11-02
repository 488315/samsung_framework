package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/85205018 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class UdfpsDrawable extends Drawable {
    public int _alpha;
    public final ShapeDrawable fingerprintDrawable;
    public boolean isDisplayConfigured;

    public UdfpsDrawable(Context context, Function1 function1) {
        ShapeDrawable shapeDrawable = (ShapeDrawable) function1.invoke(context);
        this.fingerprintDrawable = shapeDrawable;
        this._alpha = 255;
        shapeDrawable.getPaint().getStrokeWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this._alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this._alpha = i;
        this.fingerprintDrawable.setAlpha(i);
        invalidateSelf();
    }

    public UdfpsDrawable(Context context) {
        this(context, UdfpsDrawableKt.defaultFactory);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}

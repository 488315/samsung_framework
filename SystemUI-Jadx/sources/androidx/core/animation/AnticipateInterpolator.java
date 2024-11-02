package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class AnticipateInterpolator implements Interpolator {
    public final float mTension;

    public AnticipateInterpolator() {
        this.mTension = 2.0f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        float f2 = this.mTension;
        return (((1.0f + f2) * f) - f2) * f * f;
    }

    public AnticipateInterpolator(float f) {
        this.mTension = f;
    }

    public AnticipateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AnticipateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_ANTICIPATEOVERSHOOT_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f);
        obtainAttributes.recycle();
    }
}

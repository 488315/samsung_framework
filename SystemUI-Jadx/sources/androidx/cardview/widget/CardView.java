package androidx.cardview.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.cardview.R$styleable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class CardView extends FrameLayout {
    public static final int[] COLOR_BACKGROUND_ATTR = {R.attr.colorBackground};
    public static final CardViewApi21Impl IMPL = new CardViewApi21Impl();
    public final AnonymousClass1 mCardViewDelegate;
    public final boolean mCompatPadding;
    public final Rect mContentPadding;
    public final boolean mPreventCornerOverlap;
    public final Rect mShadowBounds;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: androidx.cardview.widget.CardView$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 {
        public Drawable mCardBackground;

        public AnonymousClass1() {
        }

        public final void setShadowPadding(int i, int i2, int i3, int i4) {
            CardView cardView = CardView.this;
            cardView.mShadowBounds.set(i, i2, i3, i4);
            Rect rect = cardView.mContentPadding;
            CardView.super.setPadding(i + rect.left, i2 + rect.top, i3 + rect.right, i4 + rect.bottom);
        }
    }

    public CardView(Context context) {
        this(context, null);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        CardViewApi21Impl cardViewApi21Impl = IMPL;
        if (!(cardViewApi21Impl instanceof CardViewApi21Impl)) {
            int mode = View.MeasureSpec.getMode(i);
            if (mode == Integer.MIN_VALUE || mode == 1073741824) {
                AnonymousClass1 anonymousClass1 = this.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                i = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil(((RoundRectDrawable) anonymousClass1.mCardBackground).mRadius * 2.0f), View.MeasureSpec.getSize(i)), mode);
            }
            int mode2 = View.MeasureSpec.getMode(i2);
            if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
                AnonymousClass1 anonymousClass12 = this.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                i2 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil(((RoundRectDrawable) anonymousClass12.mCardBackground).mRadius * 2.0f), View.MeasureSpec.getSize(i2)), mode2);
            }
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void setMinimumHeight(int i) {
        super.setMinimumHeight(i);
    }

    @Override // android.view.View
    public final void setMinimumWidth(int i) {
        super.setMinimumWidth(i);
    }

    public void setRadius(float f) {
        CardViewApi21Impl cardViewApi21Impl = IMPL;
        AnonymousClass1 anonymousClass1 = this.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        RoundRectDrawable roundRectDrawable = (RoundRectDrawable) anonymousClass1.mCardBackground;
        if (f != roundRectDrawable.mRadius) {
            roundRectDrawable.mRadius = f;
            roundRectDrawable.updateBounds(null);
            roundRectDrawable.invalidateSelf();
        }
    }

    public CardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.cardViewStyle);
    }

    public CardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int color;
        ColorStateList valueOf;
        Rect rect = new Rect();
        this.mContentPadding = rect;
        this.mShadowBounds = new Rect();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mCardViewDelegate = anonymousClass1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CardView, i, com.android.systemui.R.style.CardView);
        if (obtainStyledAttributes.hasValue(2)) {
            valueOf = obtainStyledAttributes.getColorStateList(2);
        } else {
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int color2 = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color2, fArr);
            if (fArr[2] > 0.5f) {
                color = getResources().getColor(com.android.systemui.R.color.cardview_light_background);
            } else {
                color = getResources().getColor(com.android.systemui.R.color.cardview_dark_background);
            }
            valueOf = ColorStateList.valueOf(color);
        }
        float dimension = obtainStyledAttributes.getDimension(3, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(4, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(5, 0.0f);
        this.mCompatPadding = obtainStyledAttributes.getBoolean(7, false);
        this.mPreventCornerOverlap = obtainStyledAttributes.getBoolean(6, true);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        rect.left = obtainStyledAttributes.getDimensionPixelSize(10, dimensionPixelSize);
        rect.top = obtainStyledAttributes.getDimensionPixelSize(12, dimensionPixelSize);
        rect.right = obtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        rect.bottom = obtainStyledAttributes.getDimensionPixelSize(9, dimensionPixelSize);
        dimension3 = dimension2 > dimension3 ? dimension2 : dimension3;
        obtainStyledAttributes.getDimensionPixelSize(0, 0);
        obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        CardViewApi21Impl cardViewApi21Impl = IMPL;
        cardViewApi21Impl.getClass();
        RoundRectDrawable roundRectDrawable = new RoundRectDrawable(valueOf, dimension);
        anonymousClass1.mCardBackground = roundRectDrawable;
        CardView cardView = CardView.this;
        cardView.setBackgroundDrawable(roundRectDrawable);
        cardView.setClipToOutline(true);
        cardView.setElevation(dimension2);
        RoundRectDrawable roundRectDrawable2 = (RoundRectDrawable) anonymousClass1.mCardBackground;
        boolean z = cardView.mCompatPadding;
        boolean z2 = cardView.mPreventCornerOverlap;
        if (dimension3 != roundRectDrawable2.mPadding || roundRectDrawable2.mInsetForPadding != z || roundRectDrawable2.mInsetForRadius != z2) {
            roundRectDrawable2.mPadding = dimension3;
            roundRectDrawable2.mInsetForPadding = z;
            roundRectDrawable2.mInsetForRadius = z2;
            roundRectDrawable2.updateBounds(null);
            roundRectDrawable2.invalidateSelf();
        }
        cardViewApi21Impl.updatePadding(anonymousClass1);
    }

    @Override // android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
    }

    @Override // android.view.View
    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
    }
}

package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    public WeakReference anchorViewRef;
    public final Rect badgeBounds;
    public float badgeCenterX;
    public float badgeCenterY;
    public final WeakReference contextRef;
    public float cornerRadius;
    public WeakReference customBadgeParentRef;
    public float halfBadgeHeight;
    public float halfBadgeWidth;
    public int maxBadgeNumber;
    public final MaterialShapeDrawable shapeDrawable;
    public final BadgeState state;
    public final TextDrawableHelper textDrawableHelper;

    private BadgeDrawable(Context context, int i, int i2, int i3, BadgeState.State state) {
        FrameLayout frameLayout;
        TextAppearance textAppearance;
        Context context2;
        WeakReference weakReference = new WeakReference(context);
        this.contextRef = weakReference;
        ThemeEnforcement.checkTheme(context, ThemeEnforcement.MATERIAL_CHECK_ATTRS, "Theme.MaterialComponents");
        this.badgeBounds = new Rect();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.shapeDrawable = materialShapeDrawable;
        TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper;
        TextPaint textPaint = textDrawableHelper.textPaint;
        textPaint.setTextAlign(Paint.Align.CENTER);
        Context context3 = (Context) weakReference.get();
        if (context3 != null && textDrawableHelper.textAppearance != (textAppearance = new TextAppearance(context3, 2132018234)) && (context2 = (Context) weakReference.get()) != null) {
            textDrawableHelper.setTextAppearance(textAppearance, context2);
            updateCenterAndBounds();
        }
        BadgeState badgeState = new BadgeState(context, i, i2, i3, state);
        this.state = badgeState;
        BadgeState.State state2 = badgeState.currentState;
        this.maxBadgeNumber = ((int) Math.pow(10.0d, state2.maxCharacterCount - 1.0d)) - 1;
        textDrawableHelper.textWidthDirty = true;
        updateCenterAndBounds();
        invalidateSelf();
        textDrawableHelper.textWidthDirty = true;
        updateCenterAndBounds();
        invalidateSelf();
        textPaint.setAlpha(getAlpha());
        invalidateSelf();
        ColorStateList valueOf = ColorStateList.valueOf(state2.backgroundColor.intValue());
        if (materialShapeDrawable.drawableState.fillColor != valueOf) {
            materialShapeDrawable.setFillColor(valueOf);
            invalidateSelf();
        }
        textPaint.setColor(state2.badgeTextColor.intValue());
        invalidateSelf();
        WeakReference weakReference2 = this.anchorViewRef;
        if (weakReference2 != null && weakReference2.get() != null) {
            View view = (View) this.anchorViewRef.get();
            WeakReference weakReference3 = this.customBadgeParentRef;
            if (weakReference3 != null) {
                frameLayout = (FrameLayout) weakReference3.get();
            } else {
                frameLayout = null;
            }
            updateBadgeCoordinates(view, frameLayout);
        }
        updateCenterAndBounds();
        setVisible(state2.isVisible.booleanValue(), false);
    }

    public static BadgeDrawable createFromSavedState(Context context, BadgeState.State state) {
        return new BadgeDrawable(context, 0, R.attr.badgeStyle, 2132019075, state);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (!getBounds().isEmpty() && getAlpha() != 0 && isVisible()) {
            this.shapeDrawable.draw(canvas);
            if (hasNumber()) {
                Rect rect = new Rect();
                String badgeText = getBadgeText();
                this.textDrawableHelper.textPaint.getTextBounds(badgeText, 0, badgeText.length(), rect);
                canvas.drawText(badgeText, this.badgeCenterX, this.badgeCenterY + (rect.height() / 2), this.textDrawableHelper.textPaint);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.state.currentState.alpha;
    }

    public final String getBadgeText() {
        if (getNumber() <= this.maxBadgeNumber) {
            return NumberFormat.getInstance(this.state.currentState.numberLocale).format(getNumber());
        }
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return "";
        }
        return String.format(this.state.currentState.numberLocale, context.getString(R.string.mtrl_exceed_max_badge_number_suffix), Integer.valueOf(this.maxBadgeNumber), "+");
    }

    public final FrameLayout getCustomBadgeParent() {
        WeakReference weakReference = this.customBadgeParentRef;
        if (weakReference != null) {
            return (FrameLayout) weakReference.get();
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public final int getNumber() {
        if (hasNumber()) {
            return this.state.currentState.number;
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final boolean hasNumber() {
        if (this.state.currentState.number != -1) {
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return false;
    }

    @Override // android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final void onTextSizeChange() {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        BadgeState badgeState = this.state;
        badgeState.overridingState.alpha = i;
        badgeState.currentState.alpha = i;
        this.textDrawableHelper.textPaint.setAlpha(getAlpha());
        invalidateSelf();
    }

    public final void updateBadgeCoordinates(View view, FrameLayout frameLayout) {
        this.anchorViewRef = new WeakReference(view);
        this.customBadgeParentRef = new WeakReference(frameLayout);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        updateCenterAndBounds();
        invalidateSelf();
    }

    public final void updateCenterAndBounds() {
        View view;
        int intValue;
        int i;
        int intValue2;
        float f;
        float f2;
        float f3;
        Context context = (Context) this.contextRef.get();
        WeakReference weakReference = this.anchorViewRef;
        ViewGroup viewGroup = null;
        if (weakReference != null) {
            view = (View) weakReference.get();
        } else {
            view = null;
        }
        if (context != null && view != null) {
            Rect rect = new Rect();
            rect.set(this.badgeBounds);
            Rect rect2 = new Rect();
            view.getDrawingRect(rect2);
            WeakReference weakReference2 = this.customBadgeParentRef;
            if (weakReference2 != null) {
                viewGroup = (ViewGroup) weakReference2.get();
            }
            if (viewGroup != null) {
                viewGroup.offsetDescendantRectToMyCoords(view, rect2);
            }
            if (hasNumber()) {
                intValue = this.state.currentState.verticalOffsetWithText.intValue();
            } else {
                intValue = this.state.currentState.verticalOffsetWithoutText.intValue();
            }
            int intValue3 = this.state.currentState.additionalVerticalOffset.intValue() + intValue;
            int intValue4 = this.state.currentState.badgeGravity.intValue();
            if (intValue4 != 8388691 && intValue4 != 8388693) {
                this.badgeCenterY = rect2.top + intValue3;
            } else {
                this.badgeCenterY = rect2.bottom - intValue3;
            }
            if (getNumber() <= 9) {
                if (!hasNumber()) {
                    f3 = this.state.badgeRadius;
                } else {
                    f3 = this.state.badgeWithTextRadius;
                }
                this.cornerRadius = f3;
                this.halfBadgeHeight = f3;
                this.halfBadgeWidth = f3;
            } else {
                float f4 = this.state.badgeWithTextRadius;
                this.cornerRadius = f4;
                this.halfBadgeHeight = f4;
                this.halfBadgeWidth = (this.textDrawableHelper.getTextWidth(getBadgeText()) / 2.0f) + this.state.badgeWidePadding;
            }
            Resources resources = context.getResources();
            if (hasNumber()) {
                i = R.dimen.mtrl_badge_text_horizontal_edge_offset;
            } else {
                i = R.dimen.mtrl_badge_horizontal_edge_offset;
            }
            int dimensionPixelSize = resources.getDimensionPixelSize(i);
            if (hasNumber()) {
                intValue2 = this.state.currentState.horizontalOffsetWithText.intValue();
            } else {
                intValue2 = this.state.currentState.horizontalOffsetWithoutText.intValue();
            }
            int intValue5 = this.state.currentState.additionalHorizontalOffset.intValue() + intValue2;
            int intValue6 = this.state.currentState.badgeGravity.intValue();
            if (intValue6 != 8388659 && intValue6 != 8388691) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                    f2 = ((rect2.right + this.halfBadgeWidth) - dimensionPixelSize) - intValue5;
                } else {
                    f2 = (rect2.left - this.halfBadgeWidth) + dimensionPixelSize + intValue5;
                }
                this.badgeCenterX = f2;
            } else {
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                    f = (rect2.left - this.halfBadgeWidth) + dimensionPixelSize + intValue5;
                } else {
                    f = ((rect2.right + this.halfBadgeWidth) - dimensionPixelSize) - intValue5;
                }
                this.badgeCenterX = f;
            }
            Rect rect3 = this.badgeBounds;
            float f5 = this.badgeCenterX;
            float f6 = this.badgeCenterY;
            float f7 = this.halfBadgeWidth;
            float f8 = this.halfBadgeHeight;
            rect3.set((int) (f5 - f7), (int) (f6 - f8), (int) (f5 + f7), (int) (f6 + f8));
            MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
            materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.drawableState.shapeAppearanceModel.withCornerSize(this.cornerRadius));
            if (!rect.equals(this.badgeBounds)) {
                this.shapeDrawable.setBounds(this.badgeBounds);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}

package com.google.android.material.card;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.FrameLayout;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewApi21Impl;
import androidx.cardview.widget.RoundRectDrawable;
import androidx.core.view.ViewCompat;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class MaterialCardView extends CardView implements Checkable, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public final MaterialCardViewHelper cardViewHelper;
    public boolean checked;
    public final boolean isParentCardViewDoneInitializing;

    public MaterialCardView(Context context) {
        this(context, null);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.cardViewHelper.bgDrawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        boolean z;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 3);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(isChecked());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            z = true;
        } else {
            z = false;
        }
        accessibilityNodeInfo.setCheckable(z);
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(isChecked());
    }

    @Override // androidx.cardview.widget.CardView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        boolean z;
        int i5;
        boolean z2;
        int i6;
        boolean z3;
        int i7;
        int i8;
        int i9;
        int i10;
        float f;
        super.onMeasure(i, i2);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (materialCardViewHelper.clickableForegroundDrawable != null) {
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            boolean z4 = false;
            if (materialCardView.mCompatPadding) {
                CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
                CardView.AnonymousClass1 anonymousClass1 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                float f2 = ((RoundRectDrawable) anonymousClass1.mCardBackground).mPadding * 1.5f;
                float f3 = 0.0f;
                if (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground()) {
                    f = materialCardViewHelper.calculateActualCornerPadding();
                } else {
                    f = 0.0f;
                }
                i4 = (int) Math.ceil((f2 + f) * 2.0f);
                CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                float f4 = ((RoundRectDrawable) anonymousClass12.mCardBackground).mPadding;
                if (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground()) {
                    f3 = materialCardViewHelper.calculateActualCornerPadding();
                }
                i3 = (int) Math.ceil((f4 + f3) * 2.0f);
            } else {
                i3 = 0;
                i4 = 0;
            }
            int i11 = materialCardViewHelper.checkedIconGravity;
            if ((i11 & 8388613) == 8388613) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i5 = ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i3;
            } else {
                i5 = materialCardViewHelper.checkedIconMargin;
            }
            if ((i11 & 80) == 80) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                i6 = materialCardViewHelper.checkedIconMargin;
            } else {
                i6 = ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i4;
            }
            int i12 = i6;
            if ((i11 & 8388613) == 8388613) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                i7 = materialCardViewHelper.checkedIconMargin;
            } else {
                i7 = ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i3;
            }
            if ((i11 & 80) == 80) {
                z4 = true;
            }
            if (z4) {
                i8 = ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i4;
            } else {
                i8 = materialCardViewHelper.checkedIconMargin;
            }
            int i13 = i8;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.getLayoutDirection(materialCardView) == 1) {
                i10 = i7;
                i9 = i5;
            } else {
                i9 = i7;
                i10 = i5;
            }
            materialCardViewHelper.clickableForegroundDrawable.setLayerInset(2, i10, i13, i9, i12);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (this.isParentCardViewDoneInitializing) {
            if (!this.cardViewHelper.isBackgroundOverwritten) {
                Log.i("MaterialCardView", "Setting a custom background is not supported.");
                this.cardViewHelper.isBackgroundOverwritten = true;
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (this.checked != z) {
            toggle();
        }
    }

    @Override // android.view.View
    public final void setClickable(boolean z) {
        Drawable drawable;
        super.setClickable(z);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null) {
            Drawable drawable2 = materialCardViewHelper.fgDrawable;
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            if (materialCardView.isClickable()) {
                drawable = materialCardViewHelper.getClickableForeground();
            } else {
                drawable = materialCardViewHelper.foregroundContentDrawable;
            }
            materialCardViewHelper.fgDrawable = drawable;
            if (drawable2 != drawable) {
                if (materialCardView.getForeground() instanceof InsetDrawable) {
                    ((InsetDrawable) materialCardView.getForeground()).setDrawable(drawable);
                } else {
                    materialCardView.setForeground(materialCardViewHelper.insetDrawable(drawable));
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x002a, code lost:
    
        if (r3 != false) goto L11;
     */
    @Override // androidx.cardview.widget.CardView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setRadius(float r3) {
        /*
            r2 = this;
            super.setRadius(r3)
            com.google.android.material.card.MaterialCardViewHelper r2 = r2.cardViewHelper
            com.google.android.material.shape.ShapeAppearanceModel r0 = r2.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel r3 = r0.withCornerSize(r3)
            r2.setShapeAppearanceModel(r3)
            android.graphics.drawable.Drawable r3 = r2.fgDrawable
            r3.invalidateSelf()
            boolean r3 = r2.shouldAddCornerPaddingOutsideCardBackground()
            com.google.android.material.shape.MaterialShapeDrawable r0 = r2.bgDrawable
            com.google.android.material.card.MaterialCardView r1 = r2.materialCardView
            if (r3 != 0) goto L2c
            boolean r3 = r1.mPreventCornerOverlap
            if (r3 == 0) goto L29
            boolean r3 = r0.isRoundRect()
            if (r3 != 0) goto L29
            r3 = 1
            goto L2a
        L29:
            r3 = 0
        L2a:
            if (r3 == 0) goto L2f
        L2c:
            r2.updateContentPadding()
        L2f:
            boolean r3 = r2.shouldAddCornerPaddingOutsideCardBackground()
            if (r3 == 0) goto L49
            boolean r3 = r2.isBackgroundOverwritten
            if (r3 != 0) goto L40
            com.google.android.material.card.MaterialCardViewHelper$1 r3 = r2.insetDrawable(r0)
            super.setBackgroundDrawable(r3)
        L40:
            android.graphics.drawable.Drawable r3 = r2.fgDrawable
            com.google.android.material.card.MaterialCardViewHelper$1 r2 = r2.insetDrawable(r3)
            r1.setForeground(r2)
        L49:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.card.MaterialCardView.setRadius(float):void");
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        RectF rectF = new RectF();
        rectF.set(this.cardViewHelper.bgDrawable.getBounds());
        setClipToOutline(shapeAppearanceModel.isRoundRect(rectF));
        this.cardViewHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        boolean z;
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        int i = 0;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            z = true;
        } else {
            z = false;
        }
        if (z && isEnabled()) {
            this.checked = !this.checked;
            refreshDrawableState();
            MaterialCardViewHelper materialCardViewHelper2 = this.cardViewHelper;
            Drawable drawable = materialCardViewHelper2.rippleDrawable;
            if (drawable != null) {
                Rect bounds = drawable.getBounds();
                int i2 = bounds.bottom;
                materialCardViewHelper2.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, i2 - 1);
                materialCardViewHelper2.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, i2);
            }
            MaterialCardViewHelper materialCardViewHelper3 = this.cardViewHelper;
            boolean z2 = this.checked;
            Drawable drawable2 = materialCardViewHelper3.checkedIcon;
            if (drawable2 != null) {
                if (z2) {
                    i = 255;
                }
                drawable2.setAlpha(i);
            }
        }
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.materialCardViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.graphics.drawable.Drawable] */
    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019096), attributeSet, i);
        this.checked = false;
        this.isParentCardViewDoneInitializing = true;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MaterialCardView, i, 2132019096, new int[0]);
        MaterialCardViewHelper materialCardViewHelper = new MaterialCardViewHelper(this, attributeSet, i, 2132019096);
        this.cardViewHelper = materialCardViewHelper;
        CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
        CardView.AnonymousClass1 anonymousClass1 = this.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        ColorStateList colorStateList = ((RoundRectDrawable) anonymousClass1.mCardBackground).mBackground;
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        materialShapeDrawable.setFillColor(colorStateList);
        Rect rect = this.mContentPadding;
        materialCardViewHelper.userContentPadding.set(rect.left, rect.top, rect.right, rect.bottom);
        materialCardViewHelper.updateContentPadding();
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 11);
        materialCardViewHelper.strokeColor = colorStateList2;
        if (colorStateList2 == null) {
            materialCardViewHelper.strokeColor = ColorStateList.valueOf(-1);
        }
        materialCardViewHelper.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        materialCardViewHelper.checkable = z;
        materialCardView.setLongClickable(z);
        materialCardViewHelper.checkedIconTint = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 6);
        Drawable drawable = MaterialResources.getDrawable(materialCardView.getContext(), obtainStyledAttributes, 2);
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            materialCardViewHelper.checkedIcon = mutate;
            mutate.setTintList(materialCardViewHelper.checkedIconTint);
            boolean isChecked = materialCardView.isChecked();
            Drawable drawable2 = materialCardViewHelper.checkedIcon;
            if (drawable2 != null) {
                drawable2.setAlpha(isChecked ? 255 : 0);
            }
        } else {
            materialCardViewHelper.checkedIcon = null;
        }
        LayerDrawable layerDrawable = materialCardViewHelper.clickableForegroundDrawable;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(com.android.systemui.R.id.mtrl_card_checked_layer_id, materialCardViewHelper.checkedIcon);
        }
        materialCardViewHelper.checkedIconSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        materialCardViewHelper.checkedIconMargin = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        materialCardViewHelper.checkedIconGravity = obtainStyledAttributes.getInteger(3, 8388661);
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 7);
        materialCardViewHelper.rippleColor = colorStateList3;
        if (colorStateList3 == null) {
            materialCardViewHelper.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(materialCardView, com.android.systemui.R.attr.colorControlHighlight));
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 1);
        MaterialShapeDrawable materialShapeDrawable2 = materialCardViewHelper.foregroundContentDrawable;
        materialShapeDrawable2.setFillColor(colorStateList4 == null ? ColorStateList.valueOf(0) : colorStateList4);
        Drawable drawable3 = materialCardViewHelper.rippleDrawable;
        if (drawable3 != null) {
            ((RippleDrawable) drawable3).setColor(materialCardViewHelper.rippleColor);
        }
        CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        materialShapeDrawable.setElevation(CardView.this.getElevation());
        float f = materialCardViewHelper.strokeWidth;
        ColorStateList colorStateList5 = materialCardViewHelper.strokeColor;
        materialShapeDrawable2.drawableState.strokeWidth = f;
        materialShapeDrawable2.invalidateSelf();
        materialShapeDrawable2.setStrokeColor(colorStateList5);
        super.setBackgroundDrawable(materialCardViewHelper.insetDrawable(materialShapeDrawable));
        MaterialShapeDrawable clickableForeground = materialCardView.isClickable() ? materialCardViewHelper.getClickableForeground() : materialShapeDrawable2;
        materialCardViewHelper.fgDrawable = clickableForeground;
        materialCardView.setForeground(materialCardViewHelper.insetDrawable(clickableForeground));
        obtainStyledAttributes.recycle();
    }
}

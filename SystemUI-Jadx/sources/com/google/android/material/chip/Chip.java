package com.google.android.material.chip;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.graphics.drawable.WrappedDrawableApi14;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R$styleable;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.MaterialCheckable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable, MaterialCheckable {
    public CharSequence accessibilityClassName;
    public ChipDrawable chipDrawable;
    public boolean closeIconFocused;
    public boolean closeIconHovered;
    public boolean closeIconPressed;
    public boolean deferredCheckedValue;
    public boolean ensureMinTouchTargetSize;
    public final AnonymousClass1 fontCallback;
    public InsetDrawable insetBackgroundDrawable;
    public int lastLayoutDirection;
    public int minTouchTargetSize;
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    public CheckableGroup.AnonymousClass1 onCheckedChangeListenerInternal;
    public final Rect rect;
    public final RectF rectF;
    public RippleDrawable ripple;
    public static final Rect EMPTY_BOUNDS = new Rect();
    public static final int[] SELECTED_STATE = {R.attr.state_selected};
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ChipTouchHelper extends ExploreByTouchHelper {
        public ChipTouchHelper(Chip chip) {
            super(chip);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            Rect rect = Chip.EMPTY_BOUNDS;
            Chip chip = Chip.this;
            if (chip.hasCloseIcon() && chip.getCloseIconTouchBounds().contains(f, f2)) {
                return 1;
            }
            return 0;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            ((ArrayList) list).add(0);
            Rect rect = Chip.EMPTY_BOUNDS;
            Chip.this.hasCloseIcon();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 == 16) {
                Chip chip = Chip.this;
                if (i == 0) {
                    return chip.performClick();
                }
                if (i == 1) {
                    chip.playSoundEffect(0);
                }
            }
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            boolean z;
            Chip chip = Chip.this;
            ChipDrawable chipDrawable = chip.chipDrawable;
            if (chipDrawable != null && chipDrawable.checkable) {
                z = true;
            } else {
                z = false;
            }
            accessibilityNodeInfoCompat.setCheckable(z);
            accessibilityNodeInfoCompat.setClickable(chip.isClickable());
            accessibilityNodeInfoCompat.setClassName(chip.getAccessibilityClassName());
            accessibilityNodeInfoCompat.setText(chip.getText());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CharSequence charSequence = "";
            if (i == 1) {
                Chip chip = Chip.this;
                ChipDrawable chipDrawable = chip.chipDrawable;
                CharSequence text = chip.getText();
                Context context = chip.getContext();
                Object[] objArr = new Object[1];
                if (!TextUtils.isEmpty(text)) {
                    charSequence = text;
                }
                objArr[0] = charSequence;
                accessibilityNodeInfoCompat.setContentDescription(context.getString(com.android.systemui.R.string.mtrl_chip_close_icon_content_description, objArr).trim());
                RectF closeIconTouchBounds = chip.getCloseIconTouchBounds();
                chip.rect.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
                accessibilityNodeInfoCompat.setBoundsInParent(chip.rect);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                accessibilityNodeInfoCompat.mInfo.setEnabled(chip.isEnabled());
                return;
            }
            accessibilityNodeInfoCompat.setContentDescription("");
            accessibilityNodeInfoCompat.setBoundsInParent(Chip.EMPTY_BOUNDS);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
            if (i == 1) {
                Chip chip = Chip.this;
                chip.closeIconFocused = z;
                chip.refreshDrawableState();
            }
        }
    }

    public Chip(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [int, boolean] */
    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        int i;
        super.drawableStateChanged();
        ChipDrawable chipDrawable = this.chipDrawable;
        boolean z = false;
        if (chipDrawable != null && ChipDrawable.isStateful(chipDrawable.closeIcon)) {
            ChipDrawable chipDrawable2 = this.chipDrawable;
            ?? isEnabled = isEnabled();
            int i2 = isEnabled;
            if (this.closeIconFocused) {
                i2 = isEnabled + 1;
            }
            int i3 = i2;
            if (this.closeIconHovered) {
                i3 = i2 + 1;
            }
            int i4 = i3;
            if (this.closeIconPressed) {
                i4 = i3 + 1;
            }
            int i5 = i4;
            if (isChecked()) {
                i5 = i4 + 1;
            }
            int[] iArr = new int[i5];
            if (isEnabled()) {
                iArr[0] = 16842910;
                i = 1;
            } else {
                i = 0;
            }
            if (this.closeIconFocused) {
                iArr[i] = 16842908;
                i++;
            }
            if (this.closeIconHovered) {
                iArr[i] = 16843623;
                i++;
            }
            if (this.closeIconPressed) {
                iArr[i] = 16842919;
                i++;
            }
            if (isChecked()) {
                iArr[i] = 16842913;
            }
            if (!Arrays.equals(chipDrawable2.closeIconStateSet, iArr)) {
                chipDrawable2.closeIconStateSet = iArr;
                if (chipDrawable2.showsCloseIcon()) {
                    z = chipDrawable2.onStateChange(chipDrawable2.getState(), iArr);
                }
            }
        }
        if (z) {
            invalidate();
        }
    }

    public final void ensureAccessibleTouchTarget(int i) {
        int i2;
        this.minTouchTargetSize = i;
        float f = 0.0f;
        int i3 = 0;
        if (!this.ensureMinTouchTargetSize) {
            InsetDrawable insetDrawable = this.insetBackgroundDrawable;
            if (insetDrawable != null) {
                if (insetDrawable != null) {
                    this.insetBackgroundDrawable = null;
                    setMinWidth(0);
                    ChipDrawable chipDrawable = this.chipDrawable;
                    if (chipDrawable != null) {
                        f = chipDrawable.chipMinHeight;
                    }
                    setMinHeight((int) f);
                    updateBackgroundDrawable();
                    return;
                }
                return;
            }
            updateBackgroundDrawable();
            return;
        }
        int max = Math.max(0, i - ((int) this.chipDrawable.chipMinHeight));
        int max2 = Math.max(0, i - this.chipDrawable.getIntrinsicWidth());
        if (max2 <= 0 && max <= 0) {
            InsetDrawable insetDrawable2 = this.insetBackgroundDrawable;
            if (insetDrawable2 != null) {
                if (insetDrawable2 != null) {
                    this.insetBackgroundDrawable = null;
                    setMinWidth(0);
                    ChipDrawable chipDrawable2 = this.chipDrawable;
                    if (chipDrawable2 != null) {
                        f = chipDrawable2.chipMinHeight;
                    }
                    setMinHeight((int) f);
                    updateBackgroundDrawable();
                    return;
                }
                return;
            }
            updateBackgroundDrawable();
            return;
        }
        if (max2 > 0) {
            i2 = max2 / 2;
        } else {
            i2 = 0;
        }
        if (max > 0) {
            i3 = max / 2;
        }
        int i4 = i3;
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            if (rect.top == i4 && rect.bottom == i4 && rect.left == i2 && rect.right == i2) {
                updateBackgroundDrawable();
                return;
            }
        }
        if (getMinHeight() != i) {
            setMinHeight(i);
        }
        if (getMinWidth() != i) {
            setMinWidth(i);
        }
        this.insetBackgroundDrawable = new InsetDrawable((Drawable) this.chipDrawable, i2, i4, i2, i4);
        updateBackgroundDrawable();
    }

    @Override // android.widget.CheckBox, android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        boolean z;
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null && chipDrawable.checkable) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            ViewParent parent = getParent();
            if (!(parent instanceof ChipGroup) || !((ChipGroup) parent).checkableGroup.singleSelection) {
                return "android.widget.Button";
            }
            return "android.widget.RadioButton";
        }
        if (isClickable()) {
            return "android.widget.Button";
        }
        return "android.view.View";
    }

    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable = this.insetBackgroundDrawable;
        if (insetDrawable == null) {
            return this.chipDrawable;
        }
        return insetDrawable;
    }

    public final RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        hasCloseIcon();
        return this.rectF;
    }

    @Override // android.widget.TextView
    public final TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.truncateAt;
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
    }

    public final boolean hasCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            Object obj = chipDrawable.closeIcon;
            if (obj != null) {
                if (obj instanceof WrappedDrawable) {
                    obj = ((WrappedDrawableApi14) ((WrappedDrawable) obj)).mDrawable;
                }
            } else {
                obj = null;
            }
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        boolean z;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, SELECTED_STATE);
        }
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null && chipDrawable.checkable) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 7) {
            if (actionMasked == 10 && this.closeIconHovered) {
                this.closeIconHovered = false;
                refreshDrawableState();
            }
        } else {
            boolean contains = getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY());
            if (this.closeIconHovered != contains) {
                this.closeIconHovered = contains;
                refreshDrawableState();
            }
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        int i;
        boolean z2;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null && chipDrawable.checkable) {
            z = true;
        } else {
            z = false;
        }
        accessibilityNodeInfo.setCheckable(z);
        accessibilityNodeInfo.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
            int i2 = -1;
            if (chipGroup.singleLine) {
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i3 < chipGroup.getChildCount()) {
                        View childAt = chipGroup.getChildAt(i3);
                        if (childAt instanceof Chip) {
                            if (chipGroup.getChildAt(i3).getVisibility() == 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z2) {
                                continue;
                            } else if (((Chip) childAt) == this) {
                                break;
                            } else {
                                i4++;
                            }
                        }
                        i3++;
                    } else {
                        i4 = -1;
                        break;
                    }
                }
                i = i4;
            } else {
                i = -1;
            }
            Object tag = getTag(com.android.systemui.R.id.row_index_key);
            if (tag instanceof Integer) {
                i2 = ((Integer) tag).intValue();
            }
            wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i2, 1, i, 1, isChecked()));
        }
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) && isEnabled()) {
            return PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.lastLayoutDirection != i) {
            this.lastLayoutDirection = i;
            updatePaddingInternal();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        if (r0 != 3) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L43
            if (r0 == r2) goto L2f
            r4 = 2
            if (r0 == r4) goto L21
            r1 = 3
            if (r0 == r1) goto L38
            goto L50
        L21:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L50
            if (r1 != 0) goto L4e
            if (r0 == 0) goto L4e
            r5.closeIconPressed = r3
            r5.refreshDrawableState()
            goto L4e
        L2f:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L38
            r5.playSoundEffect(r3)
            r0 = r2
            goto L39
        L38:
            r0 = r3
        L39:
            boolean r1 = r5.closeIconPressed
            if (r1 == 0) goto L51
            r5.closeIconPressed = r3
            r5.refreshDrawableState()
            goto L51
        L43:
            if (r1 == 0) goto L50
            boolean r0 = r5.closeIconPressed
            if (r0 == r2) goto L4e
            r5.closeIconPressed = r2
            r5.refreshDrawableState()
        L4e:
            r0 = r2
            goto L51
        L50:
            r0 = r3
        L51:
            if (r0 != 0) goto L5b
            boolean r5 = super.onTouchEvent(r6)
            if (r5 == 0) goto L5a
            goto L5b
        L5a:
            r2 = r3
        L5b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        if (drawable != getBackgroundDrawable() && drawable != this.ripple) {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
        } else {
            super.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != getBackgroundDrawable() && drawable != this.ripple) {
            Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
        } else {
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public final void setBackgroundResource(int i) {
        Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            this.deferredCheckedValue = z;
        } else if (chipDrawable.checkable) {
            super.setChecked(z);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
            return;
        }
        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
            return;
        }
        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setElevation(f);
        }
    }

    @Override // android.widget.TextView
    public final void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.chipDrawable == null) {
            return;
        }
        if (truncateAt != TextUtils.TruncateAt.MARQUEE) {
            super.setEllipsize(truncateAt);
            ChipDrawable chipDrawable = this.chipDrawable;
            if (chipDrawable != null) {
                chipDrawable.truncateAt = truncateAt;
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
    }

    @Override // android.widget.TextView
    public final void setGravity(int i) {
        if (i != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i);
        }
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        if (this.chipDrawable == null) {
            return;
        }
        super.setLayoutDirection(i);
    }

    @Override // android.widget.TextView
    public final void setLines(int i) {
        if (i <= 1) {
            super.setLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public final void setMaxLines(int i) {
        if (i <= 1) {
            super.setMaxLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public final void setMaxWidth(int i) {
        super.setMaxWidth(i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.maxWidth = i;
        }
    }

    @Override // android.widget.TextView
    public final void setMinLines(int i) {
        if (i <= 1) {
            super.setMinLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.CompoundButton
    public final void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.widget.TextView
    public final void setSingleLine(boolean z) {
        if (z) {
            super.setSingleLine(z);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        CharSequence charSequence2;
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            return;
        }
        if (charSequence == null) {
            charSequence = "";
        }
        if (chipDrawable.shouldDrawText) {
            charSequence2 = null;
        } else {
            charSequence2 = charSequence;
        }
        super.setText(charSequence2, bufferType);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setText(charSequence);
        }
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable.context, i), chipDrawable.context);
        }
        updateTextPaintDrawState();
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            float applyDimension = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
            TextDrawableHelper textDrawableHelper = chipDrawable.textDrawableHelper;
            TextAppearance textAppearance = textDrawableHelper.textAppearance;
            if (textAppearance != null) {
                textAppearance.textSize = applyDimension;
                textDrawableHelper.textPaint.setTextSize(applyDimension);
                chipDrawable.onTextSizeChange();
            }
        }
        updateTextPaintDrawState();
    }

    public final void updateBackgroundDrawable() {
        this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.rippleColor), getBackgroundDrawable(), null);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable.useCompatRipple) {
            chipDrawable.useCompatRipple = false;
            chipDrawable.compatRippleColor = null;
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        RippleDrawable rippleDrawable = this.ripple;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, rippleDrawable);
        updatePaddingInternal();
    }

    public final void updatePaddingInternal() {
        ChipDrawable chipDrawable;
        if (!TextUtils.isEmpty(getText()) && (chipDrawable = this.chipDrawable) != null) {
            int calculateCloseIconWidth = (int) (chipDrawable.calculateCloseIconWidth() + chipDrawable.chipEndPadding + chipDrawable.textEndPadding);
            ChipDrawable chipDrawable2 = this.chipDrawable;
            int calculateChipIconWidth = (int) (chipDrawable2.calculateChipIconWidth() + chipDrawable2.chipStartPadding + chipDrawable2.textStartPadding);
            if (this.insetBackgroundDrawable != null) {
                Rect rect = new Rect();
                this.insetBackgroundDrawable.getPadding(rect);
                calculateChipIconWidth += rect.left;
                calculateCloseIconWidth += rect.right;
            }
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(this, calculateChipIconWidth, paddingTop, calculateCloseIconWidth, paddingBottom);
        }
    }

    public final void updateTextPaintDrawState() {
        TextAppearance textAppearance;
        TextPaint paint = getPaint();
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            paint.drawableState = chipDrawable.getState();
        }
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            textAppearance = chipDrawable2.textDrawableHelper.textAppearance;
        } else {
            textAppearance = null;
        }
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), paint, this.fontCallback);
        }
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.chipStyle);
    }

    /* JADX WARN: Type inference failed for: r13v4, types: [com.google.android.material.chip.Chip$1] */
    public Chip(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019098), attributeSet, i);
        ChipDrawable chipDrawable;
        this.rect = new Rect();
        this.rectF = new RectF();
        this.fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.chip.Chip.1
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public final void onFontRetrieved(Typeface typeface, boolean z) {
                CharSequence text;
                Chip chip = Chip.this;
                ChipDrawable chipDrawable2 = chip.chipDrawable;
                if (chipDrawable2.shouldDrawText) {
                    text = chipDrawable2.text;
                } else {
                    text = chip.getText();
                }
                chip.setText(text);
                chip.requestLayout();
                chip.invalidate();
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public final void onFontRetrievalFailed(int i2) {
            }
        };
        Context context2 = getContext();
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") == null) {
                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") == null) {
                    if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") == null) {
                        if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") == null) {
                            if (attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) == 1 && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) == 1 && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) == 1) {
                                if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                                    Log.w("Chip", "Chip text must be vertically center and start aligned");
                                }
                            } else {
                                throw new UnsupportedOperationException("Chip does not support multi-line text");
                            }
                        } else {
                            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                        }
                    } else {
                        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                    }
                } else {
                    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
                }
            } else {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            }
        }
        ChipDrawable createFromAttributes = ChipDrawable.createFromAttributes(context2, attributeSet, i);
        int[] iArr = R$styleable.Chip;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, iArr, i, 2132019098, new int[0]);
        this.ensureMinTouchTargetSize = obtainStyledAttributes.getBoolean(32, false);
        this.minTouchTargetSize = (int) Math.ceil(obtainStyledAttributes.getDimension(20, (float) Math.ceil(ViewUtils.dpToPx(48, getContext()))));
        obtainStyledAttributes.recycle();
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != createFromAttributes) {
            if (chipDrawable2 != null) {
                chipDrawable2.delegate = new WeakReference(null);
            }
            this.chipDrawable = createFromAttributes;
            createFromAttributes.shouldDrawText = false;
            createFromAttributes.delegate = new WeakReference(this);
            ensureAccessibleTouchTarget(this.minTouchTargetSize);
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        createFromAttributes.setElevation(ViewCompat.Api21Impl.getElevation(this));
        TypedArray obtainStyledAttributes2 = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, iArr, i, 2132019098, new int[0]);
        boolean hasValue = obtainStyledAttributes2.hasValue(37);
        obtainStyledAttributes2.recycle();
        new ChipTouchHelper(this);
        if (hasCloseIcon() && (chipDrawable = this.chipDrawable) != null) {
            boolean z = chipDrawable.closeIconVisible;
        }
        ViewCompat.setAccessibilityDelegate(this, null);
        if (!hasValue) {
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.chip.Chip.2
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    ChipDrawable chipDrawable3 = Chip.this.chipDrawable;
                    if (chipDrawable3 != null) {
                        chipDrawable3.getOutline(outline);
                    } else {
                        outline.setAlpha(0.0f);
                    }
                }
            });
        }
        setChecked(this.deferredCheckedValue);
        setText(createFromAttributes.text);
        setEllipsize(createFromAttributes.truncateAt);
        updateTextPaintDrawState();
        if (!this.chipDrawable.shouldDrawText) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        setGravity(8388627);
        updatePaddingInternal();
        if (this.ensureMinTouchTargetSize) {
            setMinHeight(this.minTouchTargetSize);
        }
        this.lastLayoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
        super.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.google.android.material.chip.Chip$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Chip chip = Chip.this;
                CheckableGroup.AnonymousClass1 anonymousClass1 = chip.onCheckedChangeListenerInternal;
                if (anonymousClass1 != null) {
                    CheckableGroup checkableGroup = CheckableGroup.this;
                    if (!z2 ? checkableGroup.uncheckInternal(chip, checkableGroup.selectionRequired) : checkableGroup.checkInternal(chip)) {
                        checkableGroup.onCheckedStateChanged();
                    }
                }
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = chip.onCheckedChangeListener;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(compoundButton, z2);
                }
            }
        });
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(int i) {
        super.setTextAppearance(i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable.context, i), chipDrawable.context);
        }
        updateTextPaintDrawState();
    }
}

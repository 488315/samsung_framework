package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayApi18;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import com.samsung.android.nexus.video.VideoPlayer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class BaseSlider<S extends BaseSlider<S, L, T>, L, T> extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityEventSender accessibilityEventSender;
    public final AccessibilityHelper accessibilityHelper;
    public final AccessibilityManager accessibilityManager;
    public int activeThumbIdx;
    public final Paint activeTicksPaint;
    public final Paint activeTrackPaint;
    public final List changeListeners;
    public final List customThumbDrawablesForValues;
    public final MaterialShapeDrawable defaultThumbDrawable;
    public int defaultThumbRadius;
    public int defaultTrackHeight;
    public boolean dirtyConfig;
    public int focusedThumbIdx;
    public boolean forceDrawCompatHalo;
    public ColorStateList haloColor;
    public final Paint haloPaint;
    public int haloRadius;
    public final Paint inactiveTicksPaint;
    public final Paint inactiveTrackPaint;
    public boolean isLongPress;
    public int labelBehavior;
    public final AnonymousClass1 labelMaker;
    public int labelPadding;
    public final List labels;
    public boolean labelsAreAnimatedIn;
    public ValueAnimator labelsInAnimator;
    public ValueAnimator labelsOutAnimator;
    public MotionEvent lastEvent;
    public int minTrackSidePadding;
    public int minWidgetHeight;
    public final int scaledTouchSlop;
    public int separationUnit;
    public float stepSize;
    public boolean thumbIsPressed;
    public final Paint thumbPaint;
    public int thumbRadius;
    public ColorStateList tickColorActive;
    public ColorStateList tickColorInactive;
    public boolean tickVisible;
    public float[] ticksCoordinates;
    public float touchDownX;
    public final List touchListeners;
    public float touchPosition;
    public ColorStateList trackColorActive;
    public ColorStateList trackColorInactive;
    public int trackHeight;
    public int trackSidePadding;
    public int trackWidth;
    public float valueFrom;
    public float valueTo;
    public ArrayList values;
    public int widgetHeight;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.google.android.material.slider.BaseSlider$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 {
        public final /* synthetic */ AttributeSet val$attrs;
        public final /* synthetic */ int val$defStyleAttr;

        public AnonymousClass1(AttributeSet attributeSet, int i) {
            this.val$attrs = attributeSet;
            this.val$defStyleAttr = i;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class AccessibilityHelper extends ExploreByTouchHelper {
        public final BaseSlider slider;
        public final Rect virtualViewBounds;

        public AccessibilityHelper(BaseSlider<?, ?, ?> baseSlider) {
            super(baseSlider);
            this.virtualViewBounds = new Rect();
            this.slider = baseSlider;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            int i = 0;
            while (true) {
                BaseSlider baseSlider = this.slider;
                if (i < baseSlider.getValues().size()) {
                    Rect rect = this.virtualViewBounds;
                    baseSlider.updateBoundsForVirtualViewId(i, rect);
                    if (rect.contains((int) f, (int) f2)) {
                        return i;
                    }
                    i++;
                } else {
                    return -1;
                }
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            BaseSlider baseSlider = this.slider;
            if (!baseSlider.isEnabled()) {
                return false;
            }
            if (i2 != 4096 && i2 != 8192) {
                if (i2 == 16908349 && bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                    float f = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                    int i3 = BaseSlider.$r8$clinit;
                    if (baseSlider.snapThumbToValue(f, i)) {
                        baseSlider.updateHaloHotspot();
                        baseSlider.postInvalidate();
                        invalidateVirtualView(i);
                        return true;
                    }
                }
                return false;
            }
            int i4 = BaseSlider.$r8$clinit;
            float f2 = baseSlider.stepSize;
            if (f2 == 0.0f) {
                f2 = 1.0f;
            }
            if ((baseSlider.valueTo - baseSlider.valueFrom) / f2 > 20) {
                f2 *= Math.round(r1 / r5);
            }
            if (i2 == 8192) {
                f2 = -f2;
            }
            if (baseSlider.isRtl()) {
                f2 = -f2;
            }
            if (!baseSlider.snapThumbToValue(MathUtils.clamp(((Float) baseSlider.getValues().get(i)).floatValue() + f2, baseSlider.valueFrom, baseSlider.valueTo), i)) {
                return false;
            }
            baseSlider.updateHaloHotspot();
            baseSlider.postInvalidate();
            invalidateVirtualView(i);
            return true;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            String str;
            String str2;
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            BaseSlider baseSlider = this.slider;
            List values = baseSlider.getValues();
            float floatValue = ((Float) values.get(i)).floatValue();
            float f = baseSlider.valueFrom;
            float f2 = baseSlider.valueTo;
            if (baseSlider.isEnabled()) {
                if (floatValue > f) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (floatValue < f2) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) new AccessibilityNodeInfoCompat.RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(1, f, f2, floatValue)).mInfo);
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder sb = new StringBuilder();
            if (baseSlider.getContentDescription() != null) {
                sb.append(baseSlider.getContentDescription());
                sb.append(",");
            }
            if (values.size() > 1) {
                if (i == baseSlider.getValues().size() - 1) {
                    str = baseSlider.getContext().getString(R.string.material_slider_range_end);
                } else if (i == 0) {
                    str = baseSlider.getContext().getString(R.string.material_slider_range_start);
                } else {
                    str = "";
                }
                sb.append(str);
                if (((int) floatValue) == floatValue) {
                    str2 = "%.0f";
                } else {
                    str2 = "%.2f";
                }
                sb.append(String.format(str2, Float.valueOf(floatValue)));
            }
            accessibilityNodeInfoCompat.setContentDescription(sb.toString());
            Rect rect = this.virtualViewBounds;
            baseSlider.updateBoundsForVirtualViewId(i, rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator() { // from class: com.google.android.material.slider.BaseSlider.SliderState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SliderState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SliderState[i];
            }
        };
        public boolean hasFocus;
        public float stepSize;
        public float valueFrom;
        public float valueTo;
        public ArrayList values;

        public /* synthetic */ SliderState(Parcel parcel, AnonymousClass1 anonymousClass1) {
            this(parcel);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.valueFrom);
            parcel.writeFloat(this.valueTo);
            parcel.writeList(this.values);
            parcel.writeFloat(this.stepSize);
            parcel.writeBooleanArray(new boolean[]{this.hasFocus});
        }

        public SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        private SliderState(Parcel parcel) {
            super(parcel);
            this.valueFrom = parcel.readFloat();
            this.valueTo = parcel.readFloat();
            ArrayList arrayList = new ArrayList();
            this.values = arrayList;
            parcel.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = parcel.readFloat();
            this.hasFocus = parcel.createBooleanArray()[0];
        }
    }

    public BaseSlider(Context context) {
        this(context, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:
    
        if (r2 != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int calculateTrackCenter() {
        /*
            r5 = this;
            int r0 = r5.widgetHeight
            int r0 = r0 / 2
            int r1 = r5.labelBehavior
            r2 = 1
            r3 = 0
            if (r1 == r2) goto L11
            r4 = 3
            if (r1 != r4) goto Le
            goto Lf
        Le:
            r2 = r3
        Lf:
            if (r2 == 0) goto L1f
        L11:
            java.util.List r5 = r5.labels
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            java.lang.Object r5 = r5.get(r3)
            com.google.android.material.tooltip.TooltipDrawable r5 = (com.google.android.material.tooltip.TooltipDrawable) r5
            int r3 = r5.getIntrinsicHeight()
        L1f:
            int r0 = r0 + r3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.calculateTrackCenter():int");
    }

    public final ValueAnimator createLabelAnimator(boolean z) {
        float f;
        ValueAnimator valueAnimator;
        long j;
        TimeInterpolator timeInterpolator;
        float f2 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (z) {
            valueAnimator = this.labelsOutAnimator;
        } else {
            valueAnimator = this.labelsInAnimator;
        }
        if (valueAnimator != null && valueAnimator.isRunning()) {
            f = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            valueAnimator.cancel();
        }
        if (z) {
            f2 = 1.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        if (z) {
            j = 83;
        } else {
            j = 117;
        }
        ofFloat.setDuration(j);
        if (z) {
            timeInterpolator = AnimationUtils.DECELERATE_INTERPOLATOR;
        } else {
            timeInterpolator = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        }
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.slider.BaseSlider.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                Iterator it = ((ArrayList) BaseSlider.this.labels).iterator();
                while (it.hasNext()) {
                    TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
                    tooltipDrawable.tooltipPivotY = 1.2f;
                    tooltipDrawable.tooltipScaleX = floatValue;
                    tooltipDrawable.tooltipScaleY = floatValue;
                    tooltipDrawable.labelOpacity = AnimationUtils.lerp(0.0f, 1.0f, 0.19f, 1.0f, floatValue);
                    tooltipDrawable.invalidateSelf();
                }
                BaseSlider baseSlider = BaseSlider.this;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(baseSlider);
            }
        });
        return ofFloat;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (!this.accessibilityHelper.dispatchHoverEvent(motionEvent) && !super.dispatchHoverEvent(motionEvent)) {
            return false;
        }
        return true;
    }

    @Override // android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void drawThumbDrawable(Canvas canvas, int i, int i2, float f, Drawable drawable) {
        canvas.save();
        canvas.translate((this.trackSidePadding + ((int) (normalizeValue(f) * i))) - (drawable.getBounds().width() / 2.0f), i2 - (drawable.getBounds().height() / 2.0f));
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        Iterator it = ((ArrayList) this.labels).iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            if (tooltipDrawable.isStateful()) {
                tooltipDrawable.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    public final void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            ValueAnimator createLabelAnimator = createLabelAnimator(false);
            this.labelsOutAnimator = createLabelAnimator;
            this.labelsInAnimator = null;
            createLabelAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.slider.BaseSlider.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ViewOverlayApi18 contentViewOverlay = ViewUtils.getContentViewOverlay(BaseSlider.this);
                    Iterator it = ((ArrayList) BaseSlider.this.labels).iterator();
                    while (it.hasNext()) {
                        contentViewOverlay.viewOverlay.remove((TooltipDrawable) it.next());
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    public void forceDrawCompatHalo(boolean z) {
        this.forceDrawCompatHalo = z;
    }

    @Override // android.view.View
    public final CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.mAccessibilityFocusedVirtualViewId;
    }

    public final float[] getActiveRange() {
        float floatValue = ((Float) Collections.max(getValues())).floatValue();
        float floatValue2 = ((Float) Collections.min(getValues())).floatValue();
        if (this.values.size() == 1) {
            floatValue2 = this.valueFrom;
        }
        float normalizeValue = normalizeValue(floatValue2);
        float normalizeValue2 = normalizeValue(floatValue);
        if (isRtl()) {
            return new float[]{normalizeValue2, normalizeValue};
        }
        return new float[]{normalizeValue, normalizeValue2};
    }

    public final int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    public float getMinSeparation() {
        return 0.0f;
    }

    public List getValues() {
        return new ArrayList(this.values);
    }

    public final boolean isInVerticalScrollingContainer() {
        ViewParent parent = getParent();
        while (true) {
            boolean z = false;
            if (!(parent instanceof ViewGroup)) {
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) {
                z = true;
            }
            if (z && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
    }

    public final boolean isMultipleOfStepSize(float f) {
        if (Math.abs(Math.round(r2) - new BigDecimal(Float.toString(f)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue()) < 1.0E-4d) {
            return true;
        }
        return false;
    }

    public final boolean isRtl() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
            return true;
        }
        return false;
    }

    public final void maybeCalculateTicksCoordinates() {
        if (this.stepSize <= 0.0f) {
            return;
        }
        validateConfigurationIfDirty();
        int min = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / (this.trackHeight * 2)) + 1);
        float[] fArr = this.ticksCoordinates;
        if (fArr == null || fArr.length != min * 2) {
            this.ticksCoordinates = new float[min * 2];
        }
        float f = this.trackWidth / (min - 1);
        for (int i = 0; i < min * 2; i += 2) {
            float[] fArr2 = this.ticksCoordinates;
            fArr2[i] = ((i / 2) * f) + this.trackSidePadding;
            fArr2[i + 1] = calculateTrackCenter();
        }
    }

    public final boolean moveFocus(int i) {
        int i2 = this.focusedThumbIdx;
        long j = i2 + i;
        long size = this.values.size() - 1;
        if (j < 0) {
            j = 0;
        } else if (j > size) {
            j = size;
        }
        int i3 = (int) j;
        this.focusedThumbIdx = i3;
        if (i3 == i2) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = i3;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    public final void moveFocusInAbsoluteDirection(int i) {
        if (isRtl()) {
            if (i == Integer.MIN_VALUE) {
                i = Integer.MAX_VALUE;
            } else {
                i = -i;
            }
        }
        moveFocus(i);
    }

    public final float normalizeValue(float f) {
        float f2 = this.valueFrom;
        float f3 = (f - f2) / (this.valueTo - f2);
        if (isRtl()) {
            return 1.0f - f3;
        }
        return f3;
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Iterator it = ((ArrayList) this.labels).iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            ViewGroup contentView = ViewUtils.getContentView(this);
            if (contentView == null) {
                tooltipDrawable.getClass();
            } else {
                tooltipDrawable.getClass();
                int[] iArr = new int[2];
                contentView.getLocationOnScreen(iArr);
                tooltipDrawable.locationOnScreenX = iArr[0];
                contentView.getWindowVisibleDisplayFrame(tooltipDrawable.displayFrame);
                contentView.addOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
            }
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
        if (accessibilityEventSender != null) {
            removeCallbacks(accessibilityEventSender);
        }
        this.labelsAreAnimatedIn = false;
        Iterator it = ((ArrayList) this.labels).iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            ViewOverlayApi18 contentViewOverlay = ViewUtils.getContentViewOverlay(this);
            if (contentViewOverlay != null) {
                contentViewOverlay.viewOverlay.remove(tooltipDrawable);
                ViewGroup contentView = ViewUtils.getContentView(this);
                if (contentView == null) {
                    tooltipDrawable.getClass();
                } else {
                    contentView.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                }
            }
        }
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00cd, code lost:
    
        if (r2 != false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01b1  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDraw(android.graphics.Canvas r15) {
        /*
            Method dump skipped, instructions count: 508
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.onDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 17) {
                    if (i == 66) {
                        moveFocusInAbsoluteDirection(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    }
                } else {
                    moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
                }
            } else {
                moveFocus(VideoPlayer.MEDIA_ERROR_SYSTEM);
            }
        } else {
            moveFocus(Integer.MAX_VALUE);
        }
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!isEnabled()) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        Float f = null;
        Boolean bool = null;
        if (this.activeThumbIdx == -1) {
            if (i != 61) {
                if (i != 66) {
                    if (i != 81) {
                        if (i != 69) {
                            if (i != 70) {
                                switch (i) {
                                    case 21:
                                        moveFocusInAbsoluteDirection(-1);
                                        bool = Boolean.TRUE;
                                        break;
                                    case 22:
                                        moveFocusInAbsoluteDirection(1);
                                        bool = Boolean.TRUE;
                                        break;
                                }
                            }
                        } else {
                            moveFocus(-1);
                            bool = Boolean.TRUE;
                        }
                    }
                    moveFocus(1);
                    bool = Boolean.TRUE;
                }
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                bool = Boolean.TRUE;
            } else if (keyEvent.hasNoModifiers()) {
                bool = Boolean.valueOf(moveFocus(1));
            } else if (keyEvent.isShiftPressed()) {
                bool = Boolean.valueOf(moveFocus(-1));
            } else {
                bool = Boolean.FALSE;
            }
            if (bool != null) {
                return bool.booleanValue();
            }
            return super.onKeyDown(i, keyEvent);
        }
        boolean isLongPress = this.isLongPress | keyEvent.isLongPress();
        this.isLongPress = isLongPress;
        float f2 = 1.0f;
        if (isLongPress) {
            float f3 = this.stepSize;
            if (f3 != 0.0f) {
                f2 = f3;
            }
            if ((this.valueTo - this.valueFrom) / f2 > 20) {
                f2 *= Math.round(r0 / r11);
            }
        } else {
            float f4 = this.stepSize;
            if (f4 != 0.0f) {
                f2 = f4;
            }
        }
        if (i != 21) {
            if (i != 22) {
                if (i != 69) {
                    if (i == 70 || i == 81) {
                        f = Float.valueOf(f2);
                    }
                } else {
                    f = Float.valueOf(-f2);
                }
            } else {
                if (isRtl()) {
                    f2 = -f2;
                }
                f = Float.valueOf(f2);
            }
        } else {
            if (!isRtl()) {
                f2 = -f2;
            }
            f = Float.valueOf(f2);
        }
        if (f != null) {
            if (snapThumbToValue(f.floatValue() + ((Float) this.values.get(this.activeThumbIdx)).floatValue(), this.activeThumbIdx)) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        if (i != 23) {
            if (i != 61) {
                if (i != 66) {
                    return super.onKeyDown(i, keyEvent);
                }
            } else {
                if (keyEvent.hasNoModifiers()) {
                    return moveFocus(1);
                }
                if (!keyEvent.isShiftPressed()) {
                    return false;
                }
                return moveFocus(-1);
            }
        }
        this.activeThumbIdx = -1;
        postInvalidate();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.isLongPress = false;
        return super.onKeyUp(i, keyEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000d, code lost:
    
        if (r1 != false) goto L9;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r5, int r6) {
        /*
            r4 = this;
            int r6 = r4.widgetHeight
            int r0 = r4.labelBehavior
            r1 = 1
            r2 = 0
            if (r0 == r1) goto Lf
            r3 = 3
            if (r0 != r3) goto Lc
            goto Ld
        Lc:
            r1 = r2
        Ld:
            if (r1 == 0) goto L1d
        Lf:
            java.util.List r0 = r4.labels
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            java.lang.Object r0 = r0.get(r2)
            com.google.android.material.tooltip.TooltipDrawable r0 = (com.google.android.material.tooltip.TooltipDrawable) r0
            int r2 = r0.getIntrinsicHeight()
        L1d:
            int r6 = r6 + r2
            r0 = 1073741824(0x40000000, float:2.0)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r0)
            super.onMeasure(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.onMeasure(int, int):void");
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SliderState sliderState = (SliderState) parcelable;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        this.trackWidth = Math.max(i - (this.trackSidePadding * 2), 0);
        maybeCalculateTicksCoordinates();
        updateHaloHotspot();
    }

    public final void onStartTrackingTouch() {
        Iterator it = ((ArrayList) this.touchListeners).iterator();
        if (!it.hasNext()) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        throw null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        float f = (x - this.trackSidePadding) / this.trackWidth;
        this.touchPosition = f;
        float max = Math.max(0.0f, f);
        this.touchPosition = max;
        this.touchPosition = Math.min(1.0f, max);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!this.thumbIsPressed) {
                        if (isInVerticalScrollingContainer() && Math.abs(x - this.touchDownX) < this.scaledTouchSlop) {
                            return false;
                        }
                        getParent().requestDisallowInterceptTouchEvent(true);
                        onStartTrackingTouch();
                    }
                    if (pickActiveThumb()) {
                        this.thumbIsPressed = true;
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                    }
                }
            } else {
                this.thumbIsPressed = false;
                MotionEvent motionEvent2 = this.lastEvent;
                if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - motionEvent.getX()) <= this.scaledTouchSlop && Math.abs(this.lastEvent.getY() - motionEvent.getY()) <= this.scaledTouchSlop && pickActiveThumb()) {
                    onStartTrackingTouch();
                }
                if (this.activeThumbIdx != -1) {
                    snapTouchPosition();
                    this.activeThumbIdx = -1;
                    Iterator it = ((ArrayList) this.touchListeners).iterator();
                    if (it.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                        throw null;
                    }
                }
                invalidate();
            }
        } else {
            this.touchDownX = x;
            if (!isInVerticalScrollingContainer()) {
                getParent().requestDisallowInterceptTouchEvent(true);
                if (pickActiveThumb()) {
                    requestFocus();
                    this.thumbIsPressed = true;
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                    onStartTrackingTouch();
                }
            }
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(motionEvent);
        return true;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        ViewOverlayApi18 contentViewOverlay;
        super.onVisibilityChanged(view, i);
        if (i == 0 || (contentViewOverlay = ViewUtils.getContentViewOverlay(this)) == null) {
            return;
        }
        Iterator it = ((ArrayList) this.labels).iterator();
        while (it.hasNext()) {
            contentViewOverlay.viewOverlay.remove((TooltipDrawable) it.next());
        }
    }

    public boolean pickActiveThumb() {
        boolean z;
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float f = this.touchPosition;
        if (isRtl()) {
            f = 1.0f - f;
        }
        float f2 = this.valueTo;
        float f3 = this.valueFrom;
        float m = DependencyGraph$$ExternalSyntheticOutline0.m(f2, f3, f, f3);
        float normalizeValue = (normalizeValue(m) * this.trackWidth) + this.trackSidePadding;
        this.activeThumbIdx = 0;
        float abs = Math.abs(((Float) this.values.get(0)).floatValue() - m);
        for (int i = 1; i < this.values.size(); i++) {
            float abs2 = Math.abs(((Float) this.values.get(i)).floatValue() - m);
            float normalizeValue2 = (normalizeValue(((Float) this.values.get(i)).floatValue()) * this.trackWidth) + this.trackSidePadding;
            if (Float.compare(abs2, abs) > 1) {
                break;
            }
            if (!isRtl() ? normalizeValue2 - normalizeValue < 0.0f : normalizeValue2 - normalizeValue > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            if (Float.compare(abs2, abs) < 0) {
                this.activeThumbIdx = i;
            } else {
                if (Float.compare(abs2, abs) != 0) {
                    continue;
                } else {
                    if (Math.abs(normalizeValue2 - normalizeValue) < this.scaledTouchSlop) {
                        this.activeThumbIdx = -1;
                        return false;
                    }
                    if (z) {
                        this.activeThumbIdx = i;
                    }
                }
            }
            abs = abs2;
        }
        if (this.activeThumbIdx != -1) {
            return true;
        }
        return false;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        int i;
        super.setEnabled(z);
        if (z) {
            i = 0;
        } else {
            i = 2;
        }
        setLayerType(i, null);
    }

    public final void setValueForLabel(TooltipDrawable tooltipDrawable, float f) {
        String str;
        if (((int) f) == f) {
            str = "%.0f";
        } else {
            str = "%.2f";
        }
        String format = String.format(str, Float.valueOf(f));
        if (!TextUtils.equals(tooltipDrawable.text, format)) {
            tooltipDrawable.text = format;
            tooltipDrawable.textDrawableHelper.textWidthDirty = true;
            tooltipDrawable.invalidateSelf();
        }
        int normalizeValue = (this.trackSidePadding + ((int) (normalizeValue(f) * this.trackWidth))) - (tooltipDrawable.getIntrinsicWidth() / 2);
        int calculateTrackCenter = calculateTrackCenter() - (this.labelPadding + this.thumbRadius);
        tooltipDrawable.setBounds(normalizeValue, calculateTrackCenter - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + normalizeValue, calculateTrackCenter);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).viewOverlay.add(tooltipDrawable);
    }

    public void setValues(Float... fArr) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, fArr);
        setValuesInternal(arrayList);
    }

    public final void setValuesInternal(ArrayList arrayList) {
        ViewGroup contentView;
        ViewOverlayApi18 contentViewOverlay;
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            if (this.values.size() == arrayList.size() && this.values.equals(arrayList)) {
                return;
            }
            this.values = arrayList;
            int i = 1;
            this.dirtyConfig = true;
            this.focusedThumbIdx = 0;
            updateHaloHotspot();
            if (((ArrayList) this.labels).size() > this.values.size()) {
                List<TooltipDrawable> subList = ((ArrayList) this.labels).subList(this.values.size(), ((ArrayList) this.labels).size());
                for (TooltipDrawable tooltipDrawable : subList) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isAttachedToWindow(this) && (contentViewOverlay = ViewUtils.getContentViewOverlay(this)) != null) {
                        contentViewOverlay.viewOverlay.remove(tooltipDrawable);
                        ViewGroup contentView2 = ViewUtils.getContentView(this);
                        if (contentView2 == null) {
                            tooltipDrawable.getClass();
                        } else {
                            contentView2.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                        }
                    }
                }
                subList.clear();
            }
            while (((ArrayList) this.labels).size() < this.values.size()) {
                AnonymousClass1 anonymousClass1 = this.labelMaker;
                BaseSlider baseSlider = BaseSlider.this;
                TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(baseSlider.getContext(), anonymousClass1.val$attrs, R$styleable.Slider, anonymousClass1.val$defStyleAttr, 2132019153, new int[0]);
                TooltipDrawable createFromAttributes = TooltipDrawable.createFromAttributes(baseSlider.getContext(), obtainStyledAttributes.getResourceId(8, 2132019187));
                obtainStyledAttributes.recycle();
                ((ArrayList) this.labels).add(createFromAttributes);
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api19Impl.isAttachedToWindow(this) && (contentView = ViewUtils.getContentView(this)) != null) {
                    int[] iArr = new int[2];
                    contentView.getLocationOnScreen(iArr);
                    createFromAttributes.locationOnScreenX = iArr[0];
                    contentView.getWindowVisibleDisplayFrame(createFromAttributes.displayFrame);
                    contentView.addOnLayoutChangeListener(createFromAttributes.attachedViewLayoutChangeListener);
                }
            }
            if (((ArrayList) this.labels).size() == 1) {
                i = 0;
            }
            Iterator it = ((ArrayList) this.labels).iterator();
            while (it.hasNext()) {
                TooltipDrawable tooltipDrawable2 = (TooltipDrawable) it.next();
                tooltipDrawable2.drawableState.strokeWidth = i;
                tooltipDrawable2.invalidateSelf();
            }
            Iterator it2 = ((ArrayList) this.changeListeners).iterator();
            while (it2.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
                Iterator it3 = this.values.iterator();
                if (it3.hasNext()) {
                    ((Float) it3.next()).floatValue();
                    throw null;
                }
            }
            postInvalidate();
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    public final boolean shouldDrawCompatHalo() {
        if (!this.forceDrawCompatHalo && (getBackground() instanceof RippleDrawable)) {
            return false;
        }
        return true;
    }

    public final boolean snapThumbToValue(float f, int i) {
        float floatValue;
        float floatValue2;
        this.focusedThumbIdx = i;
        if (Math.abs(f - ((Float) this.values.get(i)).floatValue()) < 1.0E-4d) {
            return false;
        }
        float minSeparation = getMinSeparation();
        if (this.separationUnit == 0) {
            if (minSeparation == 0.0f) {
                minSeparation = 0.0f;
            } else {
                float f2 = this.valueFrom;
                minSeparation = DependencyGraph$$ExternalSyntheticOutline0.m(f2, this.valueTo, (minSeparation - this.trackSidePadding) / this.trackWidth, f2);
            }
        }
        if (isRtl()) {
            minSeparation = -minSeparation;
        }
        int i2 = i + 1;
        if (i2 >= this.values.size()) {
            floatValue = this.valueTo;
        } else {
            floatValue = ((Float) this.values.get(i2)).floatValue() - minSeparation;
        }
        int i3 = i - 1;
        if (i3 < 0) {
            floatValue2 = this.valueFrom;
        } else {
            floatValue2 = minSeparation + ((Float) this.values.get(i3)).floatValue();
        }
        this.values.set(i, Float.valueOf(MathUtils.clamp(f, floatValue2, floatValue)));
        Iterator it = ((ArrayList) this.changeListeners).iterator();
        AnonymousClass1 anonymousClass1 = null;
        if (!it.hasNext()) {
            AccessibilityManager accessibilityManager = this.accessibilityManager;
            if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
                if (accessibilityEventSender == null) {
                    this.accessibilityEventSender = new AccessibilityEventSender(this, anonymousClass1);
                } else {
                    removeCallbacks(accessibilityEventSender);
                }
                AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
                accessibilityEventSender2.virtualViewId = i;
                postDelayed(accessibilityEventSender2, 200L);
                return true;
            }
            return true;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        ((Float) this.values.get(i)).floatValue();
        throw null;
    }

    public final void snapTouchPosition() {
        double d;
        float f = this.touchPosition;
        float f2 = this.stepSize;
        if (f2 > 0.0f) {
            d = Math.round(f * r1) / ((int) ((this.valueTo - this.valueFrom) / f2));
        } else {
            d = f;
        }
        if (isRtl()) {
            d = 1.0d - d;
        }
        float f3 = this.valueTo;
        snapThumbToValue((float) ((d * (f3 - r1)) + this.valueFrom), this.activeThumbIdx);
    }

    public final void updateBoundsForVirtualViewId(int i, Rect rect) {
        int normalizeValue = this.trackSidePadding + ((int) (normalizeValue(((Float) getValues().get(i)).floatValue()) * this.trackWidth));
        int calculateTrackCenter = calculateTrackCenter();
        int i2 = this.thumbRadius;
        rect.set(normalizeValue - i2, calculateTrackCenter - i2, normalizeValue + i2, calculateTrackCenter + i2);
    }

    public final void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int normalizeValue = (int) ((normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * this.trackWidth) + this.trackSidePadding);
                int calculateTrackCenter = calculateTrackCenter();
                int i = this.haloRadius;
                background.setHotspotBounds(normalizeValue - i, calculateTrackCenter - i, normalizeValue + i, calculateTrackCenter + i);
            }
        }
    }

    public final void updateWidgetLayout() {
        boolean z;
        int max = Math.max(this.minWidgetHeight, Math.max(this.trackHeight + getPaddingBottom() + getPaddingTop(), getPaddingBottom() + getPaddingTop() + (this.thumbRadius * 2)));
        boolean z2 = false;
        if (max == this.widgetHeight) {
            z = false;
        } else {
            this.widgetHeight = max;
            z = true;
        }
        int max2 = Math.max(Math.max(this.thumbRadius - this.defaultThumbRadius, 0), Math.max((this.trackHeight - this.defaultTrackHeight) / 2, 0)) + this.minTrackSidePadding;
        if (this.trackSidePadding != max2) {
            this.trackSidePadding = max2;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isLaidOut(this)) {
                this.trackWidth = Math.max(getWidth() - (this.trackSidePadding * 2), 0);
                maybeCalculateTicksCoordinates();
            }
            z2 = true;
        }
        if (z) {
            requestLayout();
        } else if (z2) {
            postInvalidate();
        }
    }

    public final void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            float f = this.valueFrom;
            float f2 = this.valueTo;
            if (f < f2) {
                if (f2 > f) {
                    if (this.stepSize > 0.0f && !isMultipleOfStepSize(f2 - f)) {
                        throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
                    }
                    Iterator it = this.values.iterator();
                    while (it.hasNext()) {
                        Float f3 = (Float) it.next();
                        if (f3.floatValue() >= this.valueFrom && f3.floatValue() <= this.valueTo) {
                            if (this.stepSize > 0.0f && !isMultipleOfStepSize(f3.floatValue() - this.valueFrom)) {
                                throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", f3, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)));
                            }
                        } else {
                            throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", f3, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
                        }
                    }
                    float minSeparation = getMinSeparation();
                    if (minSeparation >= 0.0f) {
                        float f4 = this.stepSize;
                        if (f4 > 0.0f && minSeparation > 0.0f) {
                            if (this.separationUnit == 1) {
                                if (minSeparation < f4 || !isMultipleOfStepSize(minSeparation)) {
                                    throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)", Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)));
                                }
                            } else {
                                throw new IllegalStateException(String.format("minSeparation(%s) cannot be set as a dimension when using stepSize(%s)", Float.valueOf(minSeparation), Float.valueOf(this.stepSize)));
                            }
                        }
                        float f5 = this.stepSize;
                        if (f5 != 0.0f) {
                            if (((int) f5) != f5) {
                                Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "stepSize", Float.valueOf(f5)));
                            }
                            float f6 = this.valueFrom;
                            if (((int) f6) != f6) {
                                Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "valueFrom", Float.valueOf(f6)));
                            }
                            float f7 = this.valueTo;
                            if (((int) f7) != f7) {
                                Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "valueTo", Float.valueOf(f7)));
                            }
                        }
                        this.dirtyConfig = false;
                        return;
                    }
                    throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal to 0", Float.valueOf(minSeparation)));
                }
                throw new IllegalStateException(String.format("valueTo(%s) must be greater than valueFrom(%s)", Float.valueOf(this.valueTo), Float.valueOf(this.valueFrom)));
            }
            throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class AccessibilityEventSender implements Runnable {
        public int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        @Override // java.lang.Runnable
        public final void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }

        public /* synthetic */ AccessibilityEventSender(BaseSlider baseSlider, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public BaseSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public BaseSlider(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019153), attributeSet, i);
        int i2;
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.thumbIsPressed = false;
        this.values = new ArrayList();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.tickVisible = true;
        this.isLongPress = false;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.defaultThumbDrawable = materialShapeDrawable;
        List<Drawable> emptyList = Collections.emptyList();
        this.customThumbDrawablesForValues = emptyList;
        this.separationUnit = 0;
        Context context2 = getContext();
        Paint paint = new Paint();
        this.inactiveTrackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.activeTrackPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        Paint paint3 = new Paint(1);
        this.thumbPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint4 = new Paint(1);
        this.haloPaint = paint4;
        paint4.setStyle(Paint.Style.FILL);
        Paint paint5 = new Paint();
        this.inactiveTicksPaint = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeCap(Paint.Cap.ROUND);
        Paint paint6 = new Paint();
        this.activeTicksPaint = paint6;
        paint6.setStyle(Paint.Style.STROKE);
        paint6.setStrokeCap(Paint.Cap.ROUND);
        Resources resources = context2.getResources();
        this.minWidgetHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.minTrackSidePadding = dimensionPixelOffset;
        this.trackSidePadding = dimensionPixelOffset;
        this.defaultThumbRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.defaultTrackHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_track_height);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
        this.labelMaker = new AnonymousClass1(attributeSet, i);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.Slider, i, 2132019153, new int[0]);
        this.valueFrom = obtainStyledAttributes.getFloat(3, 0.0f);
        this.valueTo = obtainStyledAttributes.getFloat(4, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        this.stepSize = obtainStyledAttributes.getFloat(2, 0.0f);
        boolean hasValue = obtainStyledAttributes.hasValue(18);
        int i3 = hasValue ? 18 : 20;
        int i4 = hasValue ? 18 : 19;
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i3);
        colorStateList = colorStateList == null ? ContextCompat.getColorStateList(R.color.material_slider_inactive_track_color, context2) : colorStateList;
        if (!colorStateList.equals(this.trackColorInactive)) {
            this.trackColorInactive = colorStateList;
            paint.setColor(getColorForState(colorStateList));
            invalidate();
        }
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i4);
        colorStateList2 = colorStateList2 == null ? ContextCompat.getColorStateList(R.color.material_slider_active_track_color, context2) : colorStateList2;
        if (!colorStateList2.equals(this.trackColorActive)) {
            this.trackColorActive = colorStateList2;
            paint2.setColor(getColorForState(colorStateList2));
            invalidate();
        }
        materialShapeDrawable.setFillColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 9));
        if (obtainStyledAttributes.hasValue(12)) {
            materialShapeDrawable.setStrokeColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 12));
            postInvalidate();
        }
        materialShapeDrawable.drawableState.strokeWidth = obtainStyledAttributes.getDimension(13, 0.0f);
        materialShapeDrawable.invalidateSelf();
        postInvalidate();
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 5);
        colorStateList3 = colorStateList3 == null ? ContextCompat.getColorStateList(R.color.material_slider_halo_color, context2) : colorStateList3;
        if (!colorStateList3.equals(this.haloColor)) {
            this.haloColor = colorStateList3;
            Drawable background = getBackground();
            if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
                ((RippleDrawable) background).setColor(colorStateList3);
            } else {
                paint4.setColor(getColorForState(colorStateList3));
                paint4.setAlpha(63);
                invalidate();
            }
        }
        this.tickVisible = obtainStyledAttributes.getBoolean(17, true);
        boolean hasValue2 = obtainStyledAttributes.hasValue(14);
        int i5 = hasValue2 ? 14 : 16;
        int i6 = hasValue2 ? 14 : 15;
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i5);
        colorStateList4 = colorStateList4 == null ? ContextCompat.getColorStateList(R.color.material_slider_inactive_tick_marks_color, context2) : colorStateList4;
        if (!colorStateList4.equals(this.tickColorInactive)) {
            this.tickColorInactive = colorStateList4;
            paint5.setColor(getColorForState(colorStateList4));
            invalidate();
        }
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i6);
        colorStateList5 = colorStateList5 == null ? ContextCompat.getColorStateList(R.color.material_slider_active_tick_marks_color, context2) : colorStateList5;
        if (!colorStateList5.equals(this.tickColorActive)) {
            this.tickColorActive = colorStateList5;
            paint6.setColor(getColorForState(colorStateList5));
            invalidate();
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(11, 0);
        if (dimensionPixelSize == this.thumbRadius) {
            i2 = 0;
        } else {
            this.thumbRadius = dimensionPixelSize;
            RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
            ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
            float f = this.thumbRadius;
            CornerTreatment createCornerTreatment = MaterialShapeUtils.createCornerTreatment(0);
            builder.topLeftCorner = createCornerTreatment;
            float compatCornerTreatmentSize = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
            if (compatCornerTreatmentSize != -1.0f) {
                builder.setTopLeftCornerSize(compatCornerTreatmentSize);
            }
            builder.topRightCorner = createCornerTreatment;
            float compatCornerTreatmentSize2 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
            if (compatCornerTreatmentSize2 != -1.0f) {
                builder.setTopRightCornerSize(compatCornerTreatmentSize2);
            }
            builder.bottomRightCorner = createCornerTreatment;
            float compatCornerTreatmentSize3 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
            if (compatCornerTreatmentSize3 != -1.0f) {
                builder.setBottomRightCornerSize(compatCornerTreatmentSize3);
            }
            builder.bottomLeftCorner = createCornerTreatment;
            float compatCornerTreatmentSize4 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
            if (compatCornerTreatmentSize4 != -1.0f) {
                builder.setBottomLeftCornerSize(compatCornerTreatmentSize4);
            }
            builder.setAllCornerSizes(f);
            materialShapeDrawable.setShapeAppearanceModel(builder.build());
            int i7 = this.thumbRadius * 2;
            materialShapeDrawable.setBounds(0, 0, i7, i7);
            for (Drawable drawable : emptyList) {
                int i8 = this.thumbRadius * 2;
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicWidth == -1 && intrinsicHeight == -1) {
                    drawable.setBounds(0, 0, i8, i8);
                } else {
                    float max = i8 / Math.max(intrinsicWidth, intrinsicHeight);
                    drawable.setBounds(0, 0, (int) (intrinsicWidth * max), (int) (intrinsicHeight * max));
                }
            }
            i2 = 0;
            updateWidgetLayout();
        }
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(6, i2);
        if (dimensionPixelSize2 != this.haloRadius) {
            this.haloRadius = dimensionPixelSize2;
            Drawable background2 = getBackground();
            if (!shouldDrawCompatHalo() && (background2 instanceof RippleDrawable)) {
                ((RippleDrawable) background2).setRadius(this.haloRadius);
            } else {
                postInvalidate();
            }
        }
        this.defaultThumbDrawable.setElevation(obtainStyledAttributes.getDimension(10, 0.0f));
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(21, 0);
        if (this.trackHeight != dimensionPixelSize3) {
            this.trackHeight = dimensionPixelSize3;
            this.inactiveTrackPaint.setStrokeWidth(dimensionPixelSize3);
            this.activeTrackPaint.setStrokeWidth(this.trackHeight);
            this.inactiveTicksPaint.setStrokeWidth(this.trackHeight / 2.0f);
            this.activeTicksPaint.setStrokeWidth(this.trackHeight / 2.0f);
            updateWidgetLayout();
        }
        int i9 = obtainStyledAttributes.getInt(7, 0);
        if (this.labelBehavior != i9) {
            this.labelBehavior = i9;
            requestLayout();
        }
        if (!obtainStyledAttributes.getBoolean(0, true)) {
            setEnabled(false);
        }
        obtainStyledAttributes.recycle();
        setFocusable(true);
        setClickable(true);
        this.defaultThumbDrawable.setShadowCompatibilityMode();
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper(this);
        this.accessibilityHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }
}

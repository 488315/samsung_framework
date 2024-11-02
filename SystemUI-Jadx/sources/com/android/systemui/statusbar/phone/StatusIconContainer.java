package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import androidx.collection.ArraySet;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class StatusIconContainer extends AlphaOptimizedLinearLayout {
    public static final AnonymousClass1 ADD_ICON_PROPERTIES;
    public static final AnonymousClass3 ANIMATE_ALL_PROPERTIES;
    public static final AnonymousClass2 X_ANIMATION_PROPERTIES;
    public int mCutoutRightSideAvailableWidth;
    public int mCutoutRightSideIconsWidth;
    public float mDeltaWidth;
    public int mIconSpacing;
    public final ArrayList mIgnoredSlots;
    public IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final ArrayList mLayoutStates;
    public final ArrayList mMeasureViews;
    public boolean mNeedsUnderflow;
    public boolean mShouldRestrictIcons;
    public SidelingCutoutContainerInfo mSidelingCutoutContainerInfo;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class StatusIconState extends ViewState {
        public int visibleState = 0;
        public boolean justAdded = true;
        public float distanceToViewEnd = -1.0f;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            float f;
            AnimationProperties animationProperties;
            if (view.getParent() instanceof View) {
                f = ((View) view.getParent()).getWidth();
            } else {
                f = 0.0f;
            }
            float f2 = f - this.mXTranslation;
            if (!(view instanceof StatusIconDisplayable)) {
                return;
            }
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) view;
            boolean z = true;
            if (!this.justAdded && (statusIconDisplayable.getVisibleState() != 2 || this.visibleState != 0)) {
                int visibleState = statusIconDisplayable.getVisibleState();
                int i = this.visibleState;
                animationProperties = null;
                if (visibleState != i) {
                    if (statusIconDisplayable.getVisibleState() == 0 && this.visibleState == 2) {
                        z = false;
                    } else {
                        animationProperties = StatusIconContainer.ANIMATE_ALL_PROPERTIES;
                    }
                } else if (i != 2 && this.distanceToViewEnd != f2) {
                    animationProperties = StatusIconContainer.X_ANIMATION_PROPERTIES;
                }
            } else {
                super.applyToView(view);
                view.setAlpha(0.0f);
                statusIconDisplayable.setVisibleState(2);
                animationProperties = StatusIconContainer.ADD_ICON_PROPERTIES;
            }
            statusIconDisplayable.setVisibleState(this.visibleState, z);
            if (animationProperties != null) {
                animateTo(view, animationProperties);
            } else {
                super.applyToView(view);
            }
            this.justAdded = false;
            this.distanceToViewEnd = f2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.StatusIconContainer$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.phone.StatusIconContainer$2, com.android.systemui.statusbar.notification.stack.AnimationProperties] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.phone.StatusIconContainer$3, com.android.systemui.statusbar.notification.stack.AnimationProperties] */
    static {
        ?? r0 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.1
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r0.duration = 200L;
        r0.delay = 50L;
        ADD_ICON_PROPERTIES = r0;
        ?? r02 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.2
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r02.duration = 200L;
        X_ANIMATION_PROPERTIES = r02;
        ?? r03 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.3
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                animationFilter.animateY = true;
                animationFilter.animateAlpha = true;
                Property property = View.SCALE_X;
                ArraySet arraySet = animationFilter.mAnimatedProperties;
                arraySet.add(property);
                arraySet.add(View.SCALE_Y);
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r03.duration = 200L;
        ANIMATE_ALL_PROPERTIES = r03;
    }

    public StatusIconContainer(Context context) {
        this(context, null);
    }

    public static int getViewTotalMeasuredWidth(View view) {
        return view.getPaddingEnd() + view.getPaddingStart() + view.getMeasuredWidth();
    }

    public final void addIgnoredSlot(String str) {
        boolean z;
        if (this.mIgnoredSlots.contains(str)) {
            z = false;
        } else {
            this.mIgnoredSlots.add(str);
            z = true;
        }
        if (z) {
            requestLayout();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        IndicatorCutoutUtil indicatorCutoutUtil;
        Rect displayCutoutAreaToExclude;
        float height = getHeight() / 2.0f;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i6 = (int) (height - (measuredHeight / 2.0f));
            childAt.layout(0, i6, measuredWidth, measuredHeight + i6);
        }
        for (int i7 = 0; i7 < getChildCount(); i7++) {
            View childAt2 = getChildAt(i7);
            StatusIconState statusIconState = (StatusIconState) childAt2.getTag(R.id.status_bar_view_state_tag);
            if (statusIconState != null) {
                statusIconState.initFrom(childAt2);
                statusIconState.setAlpha(1.0f);
                statusIconState.hidden = false;
            }
        }
        this.mLayoutStates.clear();
        float width = getWidth();
        float paddingEnd = width - getPaddingEnd();
        getPaddingStart();
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i8 = childCount - 1; i8 >= 0; i8--) {
            View childAt3 = getChildAt(i8);
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) childAt3;
            StatusIconState statusIconState2 = (StatusIconState) childAt3.getTag(R.id.status_bar_view_state_tag);
            if (statusIconDisplayable.isIconVisible() && !statusIconDisplayable.isIconBlocked() && !this.mIgnoredSlots.contains(statusIconDisplayable.getSlot())) {
                float paddingEnd2 = paddingEnd - (childAt3.getPaddingEnd() + (childAt3.getPaddingStart() + childAt3.getWidth()));
                statusIconState2.visibleState = 0;
                statusIconState2.setXTranslation(paddingEnd2);
                this.mLayoutStates.add(0, statusIconState2);
                if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && (indicatorCutoutUtil = this.mIndicatorCutoutUtil) != null && this.mSidelingCutoutContainerInfo != null && (displayCutoutAreaToExclude = indicatorCutoutUtil.getDisplayCutoutAreaToExclude()) != null) {
                    float width2 = ((getWidth() - getPaddingEnd()) - this.mCutoutRightSideAvailableWidth) - statusIconState2.mXTranslation;
                    if (width2 > 0.0f && !z2) {
                        paddingEnd2 -= ((childAt3.getPaddingEnd() + (childAt3.getPaddingStart() + childAt3.getWidth())) + ((getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) * 2) + displayCutoutAreaToExclude.width())) - width2;
                        statusIconState2.setXTranslation(paddingEnd2);
                        z2 = true;
                    }
                }
                paddingEnd = paddingEnd2 - this.mIconSpacing;
            } else {
                statusIconState2.visibleState = 2;
            }
        }
        if (isLayoutRtl()) {
            for (int i9 = 0; i9 < childCount; i9++) {
                StatusIconState statusIconState3 = (StatusIconState) getChildAt(i9).getTag(R.id.status_bar_view_state_tag);
                statusIconState3.setXTranslation((width - statusIconState3.mXTranslation) - r0.getWidth());
            }
        }
        for (int i10 = 0; i10 < getChildCount(); i10++) {
            View childAt4 = getChildAt(i10);
            StatusIconState statusIconState4 = (StatusIconState) childAt4.getTag(R.id.status_bar_view_state_tag);
            if (statusIconState4 != null) {
                statusIconState4.applyToView(childAt4);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0089, code lost:
    
        if (r10 <= r14) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a0, code lost:
    
        r5 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009d, code lost:
    
        r3 = r10;
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009b, code lost:
    
        if (r10 <= r14) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r14, int r15) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusIconContainer.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        StatusIconState statusIconState = new StatusIconState();
        statusIconState.justAdded = true;
        view.setTag(R.id.status_bar_view_state_tag, statusIconState);
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        view.setTag(R.id.status_bar_view_state_tag, null);
    }

    public final void removeIgnoredSlot(String str) {
        if (this.mIgnoredSlots.remove(str)) {
            requestLayout();
        }
    }

    public StatusIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShouldRestrictIcons = true;
        this.mLayoutStates = new ArrayList();
        this.mMeasureViews = new ArrayList();
        this.mIgnoredSlots = new ArrayList();
        getResources().getDimensionPixelSize(17106181);
        getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mIconSpacing = getResources().getDimensionPixelSize(R.dimen.status_bar_system_icon_spacing);
        getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius);
        setWillNotDraw(true);
    }
}

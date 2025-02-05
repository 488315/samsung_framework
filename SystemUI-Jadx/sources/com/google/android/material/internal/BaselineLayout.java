package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class BaselineLayout extends ViewGroup {
    public int baseline;

    public BaselineLayout(Context context) {
        super(context, null, 0);
        this.baseline = -1;
    }

    @Override // android.view.View
    public final int getBaseline() {
        return this.baseline;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = ((i3 - i) - getPaddingRight()) - paddingLeft;
        int paddingTop = getPaddingTop();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int m = AbsActionBarView$$ExternalSyntheticOutline0.m(paddingRight, measuredWidth, 2, paddingLeft);
                if (this.baseline != -1 && childAt.getBaseline() != -1) {
                    i5 = (this.baseline + paddingTop) - childAt.getBaseline();
                } else {
                    i5 = paddingTop;
                }
                childAt.layout(m, i5, measuredWidth + m, measuredHeight + i5);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = -1;
        int i7 = -1;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                int baseline = childAt.getBaseline();
                if (baseline != -1) {
                    i6 = Math.max(i6, baseline);
                    i7 = Math.max(i7, childAt.getMeasuredHeight() - baseline);
                }
                i4 = Math.max(i4, childAt.getMeasuredWidth());
                i3 = Math.max(i3, childAt.getMeasuredHeight());
                i5 = View.combineMeasuredStates(i5, childAt.getMeasuredState());
            }
        }
        if (i6 != -1) {
            i3 = Math.max(i3, Math.max(i7, getPaddingBottom()) + i6);
            this.baseline = i6;
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(i4, getSuggestedMinimumWidth()), i, i5), View.resolveSizeAndState(Math.max(i3, getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.baseline = -1;
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.baseline = -1;
    }
}

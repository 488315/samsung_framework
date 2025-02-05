package com.android.settingslib.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class ZenRadioLayout extends LinearLayout {
    public ZenRadioLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static View findFirstClickable(View view) {
        if (view.isClickable()) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View findFirstClickable = findFirstClickable(viewGroup.getChildAt(i));
                if (findFirstClickable != null) {
                    return findFirstClickable;
                }
            }
            return null;
        }
        return null;
    }

    public static View findLastClickable(View view) {
        View findLastClickable;
        if (view.isClickable()) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            do {
                childCount--;
                if (childCount >= 0) {
                    findLastClickable = findLastClickable(viewGroup.getChildAt(childCount));
                } else {
                    return null;
                }
            } while (findLastClickable == null);
            return findLastClickable;
        }
        return null;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        ViewGroup viewGroup2 = (ViewGroup) getChildAt(1);
        int childCount = viewGroup.getChildCount();
        if (childCount == viewGroup2.getChildCount()) {
            View view = null;
            boolean z = false;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = viewGroup.getChildAt(i3);
                View childAt2 = viewGroup2.getChildAt(i3);
                if (view != null) {
                    childAt.setAccessibilityTraversalAfter(view.getId());
                }
                View findFirstClickable = findFirstClickable(childAt2);
                if (findFirstClickable != null) {
                    findFirstClickable.setAccessibilityTraversalAfter(childAt.getId());
                }
                view = findLastClickable(childAt2);
                if (childAt.getLayoutParams().height != childAt2.getMeasuredHeight()) {
                    childAt.getLayoutParams().height = childAt2.getMeasuredHeight();
                    z = true;
                }
            }
            if (z) {
                super.onMeasure(i, i2);
                return;
            }
            return;
        }
        throw new IllegalStateException("Expected matching children");
    }
}

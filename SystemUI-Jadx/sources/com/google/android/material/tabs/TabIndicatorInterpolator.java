package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.tabs.TabLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class TabIndicatorInterpolator {
    public static RectF calculateIndicatorWidthForTab(TabLayout tabLayout, View view) {
        if (view == null) {
            return new RectF();
        }
        if (!tabLayout.tabIndicatorFullWidth && (view instanceof TabLayout.TabView)) {
            TabLayout.TabView tabView = (TabLayout.TabView) view;
            View[] viewArr = {tabView.textView, tabView.iconView, tabView.customView};
            int i = 0;
            int i2 = 0;
            boolean z = false;
            for (int i3 = 0; i3 < 3; i3++) {
                View view2 = viewArr[i3];
                if (view2 != null && view2.getVisibility() == 0) {
                    if (z) {
                        i2 = Math.min(i2, view2.getLeft());
                    } else {
                        i2 = view2.getLeft();
                    }
                    if (z) {
                        i = Math.max(i, view2.getRight());
                    } else {
                        i = view2.getRight();
                    }
                    z = true;
                }
            }
            int i4 = i - i2;
            View[] viewArr2 = {tabView.textView, tabView.iconView, tabView.customView};
            int i5 = 0;
            int i6 = 0;
            boolean z2 = false;
            for (int i7 = 0; i7 < 3; i7++) {
                View view3 = viewArr2[i7];
                if (view3 != null && view3.getVisibility() == 0) {
                    if (z2) {
                        i6 = Math.min(i6, view3.getTop());
                    } else {
                        i6 = view3.getTop();
                    }
                    if (z2) {
                        i5 = Math.max(i5, view3.getBottom());
                    } else {
                        i5 = view3.getBottom();
                    }
                    z2 = true;
                }
            }
            int i8 = i5 - i6;
            int dpToPx = (int) ViewUtils.dpToPx(24, tabView.getContext());
            if (i4 < dpToPx) {
                i4 = dpToPx;
            }
            int right = (tabView.getRight() + tabView.getLeft()) / 2;
            int bottom = (tabView.getBottom() + tabView.getTop()) / 2;
            int i9 = i4 / 2;
            return new RectF(right - i9, bottom - (i8 / 2), i9 + right, (right / 2) + bottom);
        }
        return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public void updateIndicatorForOffset(TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        RectF calculateIndicatorWidthForTab = calculateIndicatorWidthForTab(tabLayout, view);
        RectF calculateIndicatorWidthForTab2 = calculateIndicatorWidthForTab(tabLayout, view2);
        drawable.setBounds(AnimationUtils.lerp(f, (int) calculateIndicatorWidthForTab.left, (int) calculateIndicatorWidthForTab2.left), drawable.getBounds().top, AnimationUtils.lerp(f, (int) calculateIndicatorWidthForTab.right, (int) calculateIndicatorWidthForTab2.right), drawable.getBounds().bottom);
    }
}

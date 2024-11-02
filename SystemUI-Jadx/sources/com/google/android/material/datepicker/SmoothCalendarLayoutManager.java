package com.google.android.material.datepicker;

import android.content.Context;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SmoothCalendarLayoutManager extends LinearLayoutManager {
    public SmoothCalendarLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(this, recyclerView.getContext()) { // from class: com.google.android.material.datepicker.SmoothCalendarLayoutManager.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / displayMetrics.densityDpi;
            }
        };
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }
}

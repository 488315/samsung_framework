package com.android.systemui.decor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.RegionInterceptingFrameLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class PrivacyDotCornerDecorProviderImpl extends CornerDecorProvider {
    public final int alignedBound1;
    public final int alignedBound2;
    public final int layoutId;
    public final int viewId;

    public PrivacyDotCornerDecorProviderImpl(int i, int i2, int i3, int i4) {
        this.viewId = i;
        this.alignedBound1 = i2;
        this.alignedBound2 = i3;
        this.layoutId = i4;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public final int getAlignedBound1() {
        return this.alignedBound1;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public final int getAlignedBound2() {
        return this.alignedBound2;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2) {
        LayoutInflater.from(context).inflate(this.layoutId, (ViewGroup) regionInterceptingFrameLayout, true);
        return regionInterceptingFrameLayout.getChildAt(regionInterceptingFrameLayout.getChildCount() - 1);
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
    }
}

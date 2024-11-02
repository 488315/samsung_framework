package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class LayoutProviderImpl implements LayoutProvider {
    public final Context mContext;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LayoutProviderImpl(Context context) {
        this.mContext = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonDistanceSize(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.11d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.2222d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        Context context = this.mContext;
        if (z) {
            if (z2) {
                return context.getString(R.string.config_secNavBarGestureRevLayoutHandle);
            }
            return context.getString(R.string.config_secNavBarGestureLayoutHandle);
        }
        if (z2) {
            return context.getString(R.string.config_secNavBarRevLayoutHandle);
        }
        return context.getString(R.string.config_secNavBarLayoutHandle);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getGestureWidth(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.35d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z) {
        Context context = this.mContext;
        if (z) {
            return context.getString(R.string.config_secNavBarRevLayout);
        }
        return context.getString(R.string.config_secNavBarLayout);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z) {
        return getSpaceSidePadding(point, z, false);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceWidth(Point point, boolean z, boolean z2) {
        double min;
        double d;
        if (z2) {
            min = Math.min(point.x, point.y);
            d = 0.14d;
        } else {
            min = Math.min(point.x, point.y);
            d = 0.11d;
        }
        return (int) (min * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getVerticalLayoutID(boolean z) {
        if (z) {
            return R.layout.samsung_navigation_layout_vertical;
        }
        return R.layout.samsung_navigation_layout;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z, boolean z2) {
        double min;
        double d;
        if (z2) {
            min = Math.min(point.x, point.y);
            d = 0.077d;
        } else {
            min = Math.min(point.x, point.y);
            d = 0.0d;
        }
        return (int) (min * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z, int i) {
        return getLayout(z);
    }
}

package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class LayoutUtil {
    public final Context context;

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

    public LayoutUtil(Context context) {
        this.context = context;
    }

    public final int getAvailableSpanCount(int i, int i2) {
        Context context = this.context;
        int dimensionPixelSize = (i - ((context.getResources().getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin)) * 2)) / i2;
        if (1 >= dimensionPixelSize) {
            return 1;
        }
        return dimensionPixelSize;
    }

    public final void setLayoutWeightWidthPercentBasic(View view, float f) {
        LinearLayout.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 instanceof LinearLayout.LayoutParams) {
            layoutParams = (LinearLayout.LayoutParams) layoutParams2;
        } else {
            layoutParams = null;
        }
        if (layoutParams != null) {
            layoutParams.width = 0;
            if (this.context.getResources().getConfiguration().screenHeightDp <= 411) {
                f = 1.0f;
            }
            layoutParams.weight = f;
        }
    }
}

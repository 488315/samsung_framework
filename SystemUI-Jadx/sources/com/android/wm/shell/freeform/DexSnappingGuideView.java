package com.android.wm.shell.freeform;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class DexSnappingGuideView extends FrameLayout {
    public int mMargin;
    public ImageView mView;

    public DexSnappingGuideView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mView = (ImageView) findViewById(R.id.dex_snapping_guide_view);
        this.mMargin = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.dex_snapping_guide_view_margin);
    }

    public final void show(int i, Rect rect) {
        int height;
        int height2;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
        if (i == 0) {
            layoutParams.leftMargin = rect.left;
            layoutParams.topMargin = rect.top;
            layoutParams.width = rect.width();
            layoutParams.height = rect.height();
        } else {
            if ((i & 1) != 0) {
                layoutParams.leftMargin = this.mMargin;
            }
            if ((i & 2) != 0) {
                layoutParams.topMargin = this.mMargin;
            }
            if ((i & 4) != 0) {
                layoutParams.leftMargin = rect.width();
            }
            if ((i & 8) != 0) {
                layoutParams.bottomMargin = this.mMargin;
            }
            if (i == 1) {
                int i2 = rect.top;
                if (i2 == 0) {
                    i2 = this.mMargin;
                }
                layoutParams.topMargin = i2;
                layoutParams.bottomMargin = this.mMargin;
                layoutParams.width = rect.width() - this.mMargin;
                if (rect.top == 0) {
                    height2 = rect.height() - (this.mMargin * 2);
                } else {
                    height2 = rect.height();
                }
                layoutParams.height = height2;
            } else if (i == 2) {
                int i3 = this.mMargin;
                layoutParams.leftMargin = i3;
                layoutParams.bottomMargin = i3;
                layoutParams.width = rect.width() - (this.mMargin * 2);
                layoutParams.height = rect.height() - (this.mMargin * 2);
            } else if (i == 4) {
                int i4 = rect.top;
                if (i4 == 0) {
                    i4 = this.mMargin;
                }
                layoutParams.topMargin = i4;
                layoutParams.bottomMargin = this.mMargin;
                layoutParams.width = rect.width() - this.mMargin;
                if (rect.top == 0) {
                    height = rect.height() - (this.mMargin * 2);
                } else {
                    height = rect.height();
                }
                layoutParams.height = height;
            } else if (i == 3) {
                layoutParams.bottomMargin = this.mMargin;
                layoutParams.width = rect.width() - this.mMargin;
                layoutParams.height = rect.height() - this.mMargin;
            } else if (i == 9) {
                layoutParams.topMargin = rect.height();
                layoutParams.width = rect.width() - this.mMargin;
                layoutParams.height = rect.height() - this.mMargin;
            } else if (i == 6) {
                layoutParams.bottomMargin = this.mMargin;
                layoutParams.width = rect.width() - this.mMargin;
                layoutParams.height = rect.height() - this.mMargin;
            } else if (i == 12) {
                layoutParams.topMargin = rect.height();
                layoutParams.width = rect.width() - this.mMargin;
                layoutParams.height = rect.height() - this.mMargin;
            }
        }
        this.mView.setLayoutParams(layoutParams);
        setVisibility(0);
    }

    public DexSnappingGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}

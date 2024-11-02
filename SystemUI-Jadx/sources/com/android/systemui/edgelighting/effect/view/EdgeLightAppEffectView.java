package com.android.systemui.edgelighting.effect.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class EdgeLightAppEffectView extends AbsEdgeLightingMaskView {
    public final String TAG;
    public final long lineDuration;
    public ValueAnimator repeatColorAnimation;

    public EdgeLightAppEffectView(Context context) {
        super(context);
        this.TAG = "EdgeLightAppEffectView";
        this.lineDuration = 100L;
        init();
    }

    @Override // com.android.systemui.edgelighting.effect.view.DrawEdgeLayout, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override // com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView
    public final void init() {
        super.init();
        this.mStrokeWidth = 10.0f;
        invalidate();
        this.mStrokeWidth = 9.0f;
        AbsEdgeLightingMaskView.changeRingImageAlpha(this.mContainer, 0.0f, 200L);
        this.mRotateDuration = 5000L;
        this.mStrokeAlpha = 0.8f;
    }

    public final void setImageDrawable() {
        if (this.mTopLayer.getDrawable() == null) {
            this.mTopLayer.setImageResource(R.drawable.music_gradient);
        }
        if (this.mBottomLayer.getDrawable() == null) {
            this.mBottomLayer.setImageResource(R.drawable.music_gradient);
        }
    }

    public final void setMainColor(int i) {
        this.mMainColor = i;
        Color.colorToHSV(i, this.mHsvColors);
        float[] fArr = this.mHsvColors;
        fArr[2] = fArr[2] + 0.5f;
        this.mTopLayer.setColorFilter(Color.HSVToColor(fArr));
        Color.colorToHSV(this.mMainColor, this.mHsvColors);
        float[] fArr2 = this.mHsvColors;
        fArr2[2] = fArr2[2] - 0.5f;
        this.mMainLayer.setBackgroundColor(Color.HSVToColor(fArr2));
        this.mBottomLayer.setColorFilter(this.mMainColor);
    }

    public EdgeLightAppEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "EdgeLightAppEffectView";
        this.lineDuration = 100L;
        init();
    }

    public EdgeLightAppEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "EdgeLightAppEffectView";
        this.lineDuration = 100L;
        init();
    }

    public EdgeLightAppEffectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "EdgeLightAppEffectView";
        this.lineDuration = 100L;
        init();
    }
}

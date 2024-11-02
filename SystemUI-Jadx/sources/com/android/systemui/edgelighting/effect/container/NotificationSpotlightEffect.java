package com.android.systemui.edgelighting.effect.container;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.SpotLightEffectView;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class NotificationSpotlightEffect extends NotificationEffect {
    public SpotLightEffectView mSpotlightEffect;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.edgelighting.effect.container.NotificationSpotlightEffect$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public NotificationSpotlightEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        boolean z;
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            AnimatorSet animatorSet = spotLightEffectView.mAnimatorSet;
            if (animatorSet != null) {
                z = animatorSet.isRunning();
            } else {
                z = false;
            }
            if (z) {
                return;
            }
            this.mSpotlightEffect.setVisibility(0);
            SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
            spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
            spotLightEffectView2.startAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        SpotLightEffectView spotLightEffectView = new SpotLightEffectView(getContext());
        this.mSpotlightEffect = spotLightEffectView;
        spotLightEffectView.setVisibility(0);
        this.mSpotlightEffect.mAnimListener = new AnonymousClass1();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(VolteConstants.ErrorCode.KDDI_INVITE_FAIL, 2521);
        layoutParams.setMarginStart((this.mScreenWidth - VolteConstants.ErrorCode.KDDI_INVITE_FAIL) / 2);
        layoutParams.topMargin = -1912;
        addView(this.mSpotlightEffect, layoutParams);
        this.mSpotlightEffect.setZ(-1.0f);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void onFlickUpAnimation() {
        super.onFlickUpAnimation();
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mSpotlightEffect.setVisibility(4);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mSpotlightEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.mSpotlightDrawable.setColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP);
            spotLightEffectView.setBackground(null);
            spotLightEffectView.setBackground(spotLightEffectView.mSpotlightDrawable);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
            if (!this.mIsShowMorphView) {
                this.mSpotlightEffect.setVisibility(0);
                SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
                spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
                spotLightEffectView2.startAnimation();
            }
        }
        super.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.setVisibility(0);
            SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
            spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
            spotLightEffectView2.startAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateEffectLocation() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.startAnimation();
        }
    }

    public NotificationSpotlightEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}

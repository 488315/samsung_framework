package com.android.systemui.charging;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import java.text.NumberFormat;
import java.util.Arrays;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class WirelessChargingLayout extends FrameLayout {
    public RippleView mRippleView;
    public RippleShader.SizeAtProgress[] mSizeAtProgressArray;

    public WirelessChargingLayout(Context context, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        super(context);
        init(context, i, i2, z, rippleShape);
    }

    public final void init(Context context, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        boolean z2;
        int i3;
        float f;
        long j;
        long j2;
        if (i != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z) {
            i3 = 2132017552;
        } else {
            i3 = 2132017553;
        }
        FrameLayout.inflate(new ContextThemeWrapper(context, i3), R.layout.wireless_charging_layout, this);
        TextView textView = (TextView) findViewById(R.id.wireless_charging_percentage);
        if (i2 != -1) {
            textView.setText(NumberFormat.getPercentInstance().format(i2 / 100.0f));
            textView.setAlpha(0.0f);
        }
        long integer = context.getResources().getInteger(R.integer.wireless_charging_fade_offset);
        long integer2 = context.getResources().getInteger(R.integer.wireless_charging_fade_duration);
        float f2 = context.getResources().getFloat(R.dimen.wireless_charging_anim_battery_level_text_size_start);
        float f3 = context.getResources().getFloat(R.dimen.wireless_charging_anim_battery_level_text_size_end);
        if (z2) {
            f = 0.75f;
        } else {
            f = 1.0f;
        }
        float f4 = f3 * f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "textSize", f2, f4);
        ofFloat.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        String str = "alpha";
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        boolean z3 = z2;
        ofFloat2.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat2.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, 0.0f);
        ofFloat3.setDuration(integer2);
        ofFloat3.setInterpolator(interpolator);
        ofFloat3.setStartDelay(integer);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        if (!Utilities.isLargeScreen(context)) {
            ObjectAnimator ofArgb = ObjectAnimator.ofArgb(this, "backgroundColor", 0, 1275068416);
            ofArgb.setDuration(300L);
            ofArgb.setInterpolator(interpolator);
            ObjectAnimator ofArgb2 = ObjectAnimator.ofArgb(this, "backgroundColor", 1275068416, 0);
            str = "alpha";
            j = integer;
            ofArgb2.setDuration(300L);
            ofArgb2.setInterpolator(interpolator);
            if (rippleShape == RippleShader.RippleShape.CIRCLE) {
                j2 = 1500;
            } else {
                j2 = 3000;
            }
            ofArgb2.setStartDelay(j2 - 300);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofArgb, ofArgb2);
            animatorSet2.start();
        } else {
            j = integer;
        }
        RippleView rippleView = (RippleView) findViewById(R.id.wireless_charging_ripple);
        this.mRippleView = rippleView;
        rippleView.setupShader(rippleShape);
        int defaultColor = Utils.getColorAttr(android.R.attr.colorAccent, this.mRippleView.getContext()).getDefaultColor();
        RippleView rippleView2 = this.mRippleView;
        RippleShader.RippleShape rippleShape2 = rippleView2.rippleShape;
        if (rippleShape2 == null) {
            rippleShape2 = null;
        }
        if (rippleShape2 == RippleShader.RippleShape.ROUNDED_BOX) {
            rippleView2.duration = 3000L;
            rippleView2.getRippleShader().setFloatUniform("in_sparkle_strength", 0.22f);
            this.mRippleView.setColor(defaultColor, 102);
            RippleShader.FadeParams fadeParams = this.mRippleView.getRippleShader().baseRingFadeParams;
            fadeParams.fadeInStart = 0.0f;
            fadeParams.fadeInEnd = 0.0f;
            fadeParams.fadeOutStart = 0.2f;
            fadeParams.fadeOutEnd = 0.47f;
            RippleShader.FadeParams fadeParams2 = this.mRippleView.getRippleShader().sparkleRingFadeParams;
            fadeParams2.fadeInStart = 0.0f;
            fadeParams2.fadeInEnd = 0.0f;
            fadeParams2.fadeOutStart = 0.2f;
            fadeParams2.fadeOutEnd = 1.0f;
            RippleShader.FadeParams fadeParams3 = this.mRippleView.getRippleShader().centerFillFadeParams;
            fadeParams3.fadeInStart = 0.0f;
            fadeParams3.fadeInEnd = 0.0f;
            fadeParams3.fadeOutStart = 0.0f;
            fadeParams3.fadeOutEnd = 0.2f;
            RippleView rippleView3 = this.mRippleView;
            rippleView3.getRippleShader().getClass();
            rippleView3.getRippleShader().getClass();
        } else {
            rippleView2.duration = 1500L;
            rippleView2.setColor(defaultColor, 115);
        }
        this.mRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WirelessChargingLayout.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                WirelessChargingLayout.this.mRippleView.startRipple(null);
                WirelessChargingLayout.this.mRippleView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        if (!z3) {
            animatorSet.start();
            return;
        }
        TextView textView2 = (TextView) findViewById(R.id.reverse_wireless_charging_percentage);
        textView2.setVisibility(0);
        textView2.setText(NumberFormat.getPercentInstance().format(i / 100.0f));
        textView2.setAlpha(0.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(textView2, "textSize", f2, f4);
        ofFloat4.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat4.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        String str2 = str;
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(textView2, str2, 0.0f, 1.0f);
        ofFloat5.setInterpolator(interpolator);
        ofFloat5.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat5.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(textView2, str2, 1.0f, 0.0f);
        ofFloat6.setDuration(integer2);
        ofFloat6.setInterpolator(interpolator);
        long j3 = j;
        ofFloat6.setStartDelay(j3);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(ofFloat4, ofFloat5, ofFloat6);
        ImageView imageView = (ImageView) findViewById(R.id.reverse_wireless_charging_icon);
        imageView.setVisibility(0);
        int round = Math.round(TypedValue.applyDimension(1, f4, getResources().getDisplayMetrics()));
        imageView.setPadding(round, 0, round, 0);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(imageView, str2, 0.0f, 1.0f);
        ofFloat7.setInterpolator(interpolator);
        ofFloat7.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat7.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(imageView, str2, 1.0f, 0.0f);
        ofFloat8.setDuration(integer2);
        ofFloat8.setInterpolator(interpolator);
        ofFloat8.setStartDelay(j3);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(ofFloat7, ofFloat8);
        animatorSet.start();
        animatorSet3.start();
        animatorSet4.start();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mRippleView != null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = measuredWidth;
            float f2 = measuredHeight;
            this.mRippleView.setCenter(f * 0.5f, 0.5f * f2);
            RippleShader.RippleShape rippleShape = this.mRippleView.rippleShape;
            if (rippleShape == null) {
                rippleShape = null;
            }
            if (rippleShape == RippleShader.RippleShape.ROUNDED_BOX) {
                RippleShader.SizeAtProgress[] sizeAtProgressArr = this.mSizeAtProgressArray;
                if (sizeAtProgressArr == null) {
                    float max = Math.max(f, f2);
                    this.mSizeAtProgressArray = new RippleShader.SizeAtProgress[]{new RippleShader.SizeAtProgress(0.0f, 0.0f, 0.0f), new RippleShader.SizeAtProgress(0.3f, f * 0.4f, f2 * 0.4f), new RippleShader.SizeAtProgress(1.0f, max, max)};
                } else {
                    RippleShader.SizeAtProgress sizeAtProgress = sizeAtProgressArr[0];
                    sizeAtProgress.t = 0.0f;
                    sizeAtProgress.width = 0.0f;
                    sizeAtProgress.height = 0.0f;
                    RippleShader.SizeAtProgress sizeAtProgress2 = sizeAtProgressArr[1];
                    sizeAtProgress2.t = 0.3f;
                    sizeAtProgress2.width = f * 0.4f;
                    sizeAtProgress2.height = 0.4f * f2;
                    float max2 = Math.max(f, f2);
                    RippleShader.SizeAtProgress sizeAtProgress3 = this.mSizeAtProgressArray[2];
                    sizeAtProgress3.t = 1.0f;
                    sizeAtProgress3.width = max2;
                    sizeAtProgress3.height = max2;
                }
                RippleView rippleView = this.mRippleView;
                RippleShader.SizeAtProgress[] sizeAtProgressArr2 = this.mSizeAtProgressArray;
                rippleView.getRippleShader().rippleSize.setSizeAtProgresses((RippleShader.SizeAtProgress[]) Arrays.copyOf(sizeAtProgressArr2, sizeAtProgressArr2.length));
            } else {
                float max3 = Math.max(measuredWidth, measuredHeight);
                this.mRippleView.getRippleShader().rippleSize.setMaxSize(max3, max3);
            }
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    private WirelessChargingLayout(Context context) {
        super(context);
        init(context, -1, -1, false, RippleShader.RippleShape.CIRCLE);
    }

    private WirelessChargingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, -1, -1, false, RippleShader.RippleShape.CIRCLE);
    }
}

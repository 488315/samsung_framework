package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ReceiverChipRippleView extends RippleView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isStarted;

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

    public ReceiverChipRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupShader(RippleShader.RippleShape.CIRCLE);
        RippleShader rippleShader = getRippleShader();
        RippleShader.FadeParams fadeParams = rippleShader.baseRingFadeParams;
        fadeParams.fadeOutStart = 1.0f;
        fadeParams.fadeOutEnd = 1.0f;
        RippleShader.FadeParams fadeParams2 = rippleShader.centerFillFadeParams;
        fadeParams2.fadeInStart = 0.0f;
        fadeParams2.fadeInEnd = 0.0f;
        fadeParams2.fadeOutStart = 1.0f;
        fadeParams2.fadeOutEnd = 1.0f;
        getRippleShader().setFloatUniform("in_sparkle_strength", 0.0f);
        this.isStarted = false;
    }
}

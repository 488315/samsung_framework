package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.BaseLayer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class AnimatableTransform implements ContentModel {
    public final AnimatablePathValue anchorPoint;
    public final AnimatableFloatValue endOpacity;
    public final AnimatableIntegerValue opacity;
    public final AnimatableValue position;
    public final AnimatableFloatValue rotation;
    public final AnimatableScaleValue scale;
    public final AnimatableFloatValue skew;
    public final AnimatableFloatValue skewAngle;
    public final AnimatableFloatValue startOpacity;

    public AnimatableTransform() {
        this(null, null, null, null, null, null, null, null, null);
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer) {
        return null;
    }

    public AnimatableTransform(AnimatablePathValue animatablePathValue, AnimatableValue animatableValue, AnimatableScaleValue animatableScaleValue, AnimatableFloatValue animatableFloatValue, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue2, AnimatableFloatValue animatableFloatValue3, AnimatableFloatValue animatableFloatValue4, AnimatableFloatValue animatableFloatValue5) {
        this.anchorPoint = animatablePathValue;
        this.position = animatableValue;
        this.scale = animatableScaleValue;
        this.rotation = animatableFloatValue;
        this.opacity = animatableIntegerValue;
        this.startOpacity = animatableFloatValue2;
        this.endOpacity = animatableFloatValue3;
        this.skew = animatableFloatValue4;
        this.skewAngle = animatableFloatValue5;
    }
}

package com.airbnb.lottie.model.layer;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArraySet;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.PerformanceTracker;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.utils.MeanCalculator;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class BaseLayer implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    public final List animations;
    public BlurMaskFilter blurMaskFilter;
    public float blurMaskFilterRadius;
    public final Matrix boundsMatrix;
    public final RectF canvasBounds;
    public final LPaint clearPaint;
    public FloatKeyframeAnimation inOutAnimation;
    public final Layer layerModel;
    public final LottieDrawable lottieDrawable;
    public final MaskKeyframeAnimation mask;
    public final RectF maskBoundsRect;
    public final RectF matteBoundsRect;
    public BaseLayer matteLayer;
    public final LPaint mattePaint;
    public BaseLayer parentLayer;
    public List parentLayers;
    public final RectF rect;
    public final RectF tempMaskBoundsRect;
    public final TransformKeyframeAnimation transform;
    public boolean visible;
    public final Path path = new Path();
    public final Matrix matrix = new Matrix();
    public final Matrix canvasMatrix = new Matrix();
    public final LPaint contentPaint = new LPaint(1);
    public final LPaint dstInPaint = new LPaint(1, PorterDuff.Mode.DST_IN);
    public final LPaint dstOutPaint = new LPaint(1, PorterDuff.Mode.DST_OUT);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.airbnb.lottie.model.layer.BaseLayer$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode;
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType;

        static {
            int[] iArr = new int[Mask.MaskMode.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode = iArr;
            try {
                iArr[Mask.MaskMode.MASK_MODE_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_INTERSECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Layer.LayerType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType = iArr2;
            try {
                iArr2[Layer.LayerType.SHAPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.PRE_COMP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.SOLID.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.NULL.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.TEXT.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public BaseLayer(LottieDrawable lottieDrawable, Layer layer) {
        LPaint lPaint = new LPaint(1);
        this.mattePaint = lPaint;
        this.clearPaint = new LPaint(PorterDuff.Mode.CLEAR);
        this.rect = new RectF();
        this.canvasBounds = new RectF();
        this.maskBoundsRect = new RectF();
        this.matteBoundsRect = new RectF();
        this.tempMaskBoundsRect = new RectF();
        this.boundsMatrix = new Matrix();
        this.animations = new ArrayList();
        this.visible = true;
        this.blurMaskFilterRadius = 0.0f;
        this.lottieDrawable = lottieDrawable;
        this.layerModel = layer;
        String str = layer.layerName;
        if (layer.matteType == Layer.MatteType.INVERT) {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        AnimatableTransform animatableTransform = layer.transform;
        animatableTransform.getClass();
        TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
        this.transform = transformKeyframeAnimation;
        transformKeyframeAnimation.addListener(this);
        List list = layer.masks;
        if (list != null && !list.isEmpty()) {
            MaskKeyframeAnimation maskKeyframeAnimation = new MaskKeyframeAnimation(list);
            this.mask = maskKeyframeAnimation;
            Iterator it = ((ArrayList) maskKeyframeAnimation.maskAnimations).iterator();
            while (it.hasNext()) {
                ((BaseKeyframeAnimation) it.next()).addUpdateListener(this);
            }
            Iterator it2 = ((ArrayList) this.mask.opacityAnimations).iterator();
            while (it2.hasNext()) {
                BaseKeyframeAnimation baseKeyframeAnimation = (BaseKeyframeAnimation) it2.next();
                addAnimation(baseKeyframeAnimation);
                baseKeyframeAnimation.addUpdateListener(this);
            }
        }
        Layer layer2 = this.layerModel;
        if (!layer2.inOutKeyframes.isEmpty()) {
            FloatKeyframeAnimation floatKeyframeAnimation = new FloatKeyframeAnimation(layer2.inOutKeyframes);
            this.inOutAnimation = floatKeyframeAnimation;
            floatKeyframeAnimation.isDiscrete = true;
            floatKeyframeAnimation.addUpdateListener(new BaseKeyframeAnimation.AnimationListener() { // from class: com.airbnb.lottie.model.layer.BaseLayer$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
                public final void onValueChanged() {
                    boolean z;
                    BaseLayer baseLayer = BaseLayer.this;
                    if (baseLayer.inOutAnimation.getFloatValue() == 1.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z != baseLayer.visible) {
                        baseLayer.visible = z;
                        baseLayer.lottieDrawable.invalidateSelf();
                    }
                }
            });
            boolean z = ((Float) this.inOutAnimation.getValue()).floatValue() == 1.0f;
            if (z != this.visible) {
                this.visible = z;
                this.lottieDrawable.invalidateSelf();
            }
            addAnimation(this.inOutAnimation);
            return;
        }
        if (true != this.visible) {
            this.visible = true;
            this.lottieDrawable.invalidateSelf();
        }
    }

    public final void addAnimation(BaseKeyframeAnimation baseKeyframeAnimation) {
        if (baseKeyframeAnimation == null) {
            return;
        }
        this.animations.add(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        this.transform.applyValueCallback(lottieValueCallback, obj);
    }

    public final void buildParentLayerListIfNeeded() {
        if (this.parentLayers != null) {
            return;
        }
        if (this.parentLayer == null) {
            this.parentLayers = Collections.emptyList();
            return;
        }
        this.parentLayers = new ArrayList();
        for (BaseLayer baseLayer = this.parentLayer; baseLayer != null; baseLayer = baseLayer.parentLayer) {
            this.parentLayers.add(baseLayer);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0316  */
    @Override // com.airbnb.lottie.animation.content.DrawingContent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r19, android.graphics.Matrix r20, int r21) {
        /*
            Method dump skipped, instructions count: 874
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.BaseLayer.draw(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public abstract void drawLayer(Canvas canvas, Matrix matrix, int i);

    public BlurEffect getBlurEffect() {
        return this.layerModel.blurEffect;
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        buildParentLayerListIfNeeded();
        Matrix matrix2 = this.boundsMatrix;
        matrix2.set(matrix);
        if (z) {
            List list = this.parentLayers;
            if (list != null) {
                int size = list.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else {
                        matrix2.preConcat(((BaseLayer) this.parentLayers.get(size)).transform.getMatrix());
                    }
                }
            } else {
                BaseLayer baseLayer = this.parentLayer;
                if (baseLayer != null) {
                    matrix2.preConcat(baseLayer.transform.getMatrix());
                }
            }
        }
        matrix2.preConcat(this.transform.getMatrix());
    }

    public DropShadowEffect getDropShadowEffect() {
        return this.layerModel.dropShadowEffect;
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.layerModel.layerName;
    }

    public final boolean hasMasksOnThisLayer() {
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        if (maskKeyframeAnimation != null && !((ArrayList) maskKeyframeAnimation.maskAnimations).isEmpty()) {
            return true;
        }
        return false;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public final void recordRenderTime() {
        PerformanceTracker performanceTracker = this.lottieDrawable.composition.performanceTracker;
        String str = this.layerModel.layerName;
        if (performanceTracker.enabled) {
            HashMap hashMap = (HashMap) performanceTracker.layerRenderTimes;
            MeanCalculator meanCalculator = (MeanCalculator) hashMap.get(str);
            if (meanCalculator == null) {
                meanCalculator = new MeanCalculator();
                hashMap.put(str, meanCalculator);
            }
            int i = meanCalculator.n + 1;
            meanCalculator.n = i;
            if (i == Integer.MAX_VALUE) {
                meanCalculator.n = i / 2;
            }
            if (str.equals("__container")) {
                ArraySet arraySet = performanceTracker.frameListeners;
                arraySet.getClass();
                ArraySet.ElementIterator elementIterator = new ArraySet.ElementIterator();
                if (elementIterator.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(elementIterator.next());
                    throw null;
                }
            }
        }
    }

    public final void removeAnimation(BaseKeyframeAnimation baseKeyframeAnimation) {
        ((ArrayList) this.animations).remove(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        BaseLayer baseLayer = this.matteLayer;
        Layer layer = this.layerModel;
        if (baseLayer != null) {
            KeyPath addKey = keyPath2.addKey(baseLayer.layerModel.layerName);
            if (keyPath.fullyResolvesTo(i, this.matteLayer.layerModel.layerName)) {
                ((ArrayList) list).add(addKey.resolve(this.matteLayer));
            }
            if (keyPath.propagateToChildren(i, layer.layerName)) {
                this.matteLayer.resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(i, this.matteLayer.layerModel.layerName) + i, list, addKey);
            }
        }
        if (!keyPath.matches(i, layer.layerName)) {
            return;
        }
        String str = layer.layerName;
        if (!"__container".equals(str)) {
            keyPath2 = keyPath2.addKey(str);
            if (keyPath.fullyResolvesTo(i, str)) {
                ((ArrayList) list).add(keyPath2.resolve(this));
            }
        }
        if (keyPath.propagateToChildren(i, str)) {
            resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(i, str) + i, list, keyPath2);
        }
    }

    public void setProgress(float f) {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
        BaseKeyframeAnimation baseKeyframeAnimation = transformKeyframeAnimation.opacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = transformKeyframeAnimation.startOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = transformKeyframeAnimation.endOpacity;
        if (baseKeyframeAnimation3 != null) {
            baseKeyframeAnimation3.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation4 = transformKeyframeAnimation.anchorPoint;
        if (baseKeyframeAnimation4 != null) {
            baseKeyframeAnimation4.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation5 = transformKeyframeAnimation.position;
        if (baseKeyframeAnimation5 != null) {
            baseKeyframeAnimation5.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation6 = transformKeyframeAnimation.scale;
        if (baseKeyframeAnimation6 != null) {
            baseKeyframeAnimation6.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation7 = transformKeyframeAnimation.rotation;
        if (baseKeyframeAnimation7 != null) {
            baseKeyframeAnimation7.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation = transformKeyframeAnimation.skew;
        if (floatKeyframeAnimation != null) {
            floatKeyframeAnimation.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation2 = transformKeyframeAnimation.skewAngle;
        if (floatKeyframeAnimation2 != null) {
            floatKeyframeAnimation2.setProgress(f);
        }
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        if (maskKeyframeAnimation != null) {
            for (int i = 0; i < ((ArrayList) maskKeyframeAnimation.maskAnimations).size(); i++) {
                ((BaseKeyframeAnimation) ((ArrayList) maskKeyframeAnimation.maskAnimations).get(i)).setProgress(f);
            }
        }
        FloatKeyframeAnimation floatKeyframeAnimation3 = this.inOutAnimation;
        if (floatKeyframeAnimation3 != null) {
            floatKeyframeAnimation3.setProgress(f);
        }
        BaseLayer baseLayer = this.matteLayer;
        if (baseLayer != null) {
            baseLayer.setProgress(f);
        }
        List list = this.animations;
        ((ArrayList) list).size();
        for (int i2 = 0; i2 < ((ArrayList) list).size(); i2++) {
            ((BaseKeyframeAnimation) ((ArrayList) list).get(i2)).setProgress(f);
        }
        ((ArrayList) list).size();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
    }

    public void resolveChildKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
    }
}
package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ContentGroup implements DrawingContent, PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    private final List<Content> contents;
    private final boolean hidden;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private final String name;
    private LPaint offScreenPaint;
    private RectF offScreenRectF;
    private final Path path;
    private List<PathContent> pathContents;
    private final RectF rect;
    private TransformKeyframeAnimation transformAnimation;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ContentGroup(com.airbnb.lottie.LottieDrawable r8, com.airbnb.lottie.model.layer.BaseLayer r9, com.airbnb.lottie.model.content.ShapeGroup r10) {
        /*
            r7 = this;
            java.lang.String r3 = r10.getName()
            boolean r4 = r10.isHidden()
            java.util.List r0 = r10.getItems()
            java.util.ArrayList r5 = new java.util.ArrayList
            int r1 = r0.size()
            r5.<init>(r1)
            r1 = 0
            r2 = r1
        L17:
            int r6 = r0.size()
            if (r2 >= r6) goto L2f
            java.lang.Object r6 = r0.get(r2)
            com.airbnb.lottie.model.content.ContentModel r6 = (com.airbnb.lottie.model.content.ContentModel) r6
            com.airbnb.lottie.animation.content.Content r6 = r6.toContent(r8, r9)
            if (r6 == 0) goto L2c
            r5.add(r6)
        L2c:
            int r2 = r2 + 1
            goto L17
        L2f:
            java.util.List r10 = r10.getItems()
        L33:
            int r0 = r10.size()
            if (r1 >= r0) goto L4a
            java.lang.Object r0 = r10.get(r1)
            com.airbnb.lottie.model.content.ContentModel r0 = (com.airbnb.lottie.model.content.ContentModel) r0
            boolean r2 = r0 instanceof com.airbnb.lottie.model.animatable.AnimatableTransform
            if (r2 == 0) goto L47
            com.airbnb.lottie.model.animatable.AnimatableTransform r0 = (com.airbnb.lottie.model.animatable.AnimatableTransform) r0
            r6 = r0
            goto L4c
        L47:
            int r1 = r1 + 1
            goto L33
        L4a:
            r10 = 0
            r6 = r10
        L4c:
            r0 = r7
            r1 = r8
            r2 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.ContentGroup.<init>(com.airbnb.lottie.LottieDrawable, com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.content.ShapeGroup):void");
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            transformKeyframeAnimation.applyValueCallback(lottieValueCallback, obj);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        boolean z;
        if (this.hidden) {
            return;
        }
        Matrix matrix2 = this.matrix;
        matrix2.set(matrix);
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix2.preConcat(transformKeyframeAnimation.getMatrix());
            i = (int) (((((this.transformAnimation.getOpacity() == null ? 100 : this.transformAnimation.getOpacity().getValue().intValue()) / 100.0f) * i) / 255.0f) * 255.0f);
        }
        boolean isApplyingOpacityToLayersEnabled = this.lottieDrawable.isApplyingOpacityToLayersEnabled();
        boolean z2 = false;
        List<Content> list = this.contents;
        if (isApplyingOpacityToLayersEnabled) {
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    z = false;
                    break;
                } else {
                    if ((list.get(i2) instanceof DrawingContent) && (i3 = i3 + 1) >= 2) {
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
            if (z && i != 255) {
                z2 = true;
            }
        }
        if (z2) {
            this.offScreenRectF.set(0.0f, 0.0f, 0.0f, 0.0f);
            getBounds(this.offScreenRectF, matrix2, true);
            this.offScreenPaint.setAlpha(i);
            RectF rectF = this.offScreenRectF;
            LPaint lPaint = this.offScreenPaint;
            int i4 = Utils.$r8$clinit;
            canvas.saveLayer(rectF, lPaint);
            L.endSection();
        }
        if (z2) {
            i = 255;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            Content content = list.get(size);
            if (content instanceof DrawingContent) {
                ((DrawingContent) content).draw(canvas, matrix2, i);
            }
        }
        if (z2) {
            canvas.restore();
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        Matrix matrix2 = this.matrix;
        matrix2.set(matrix);
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix2.preConcat(transformKeyframeAnimation.getMatrix());
        }
        RectF rectF2 = this.rect;
        rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
        List<Content> list = this.contents;
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            Content content = list.get(size);
            if (content instanceof DrawingContent) {
                ((DrawingContent) content).getBounds(rectF2, matrix2, z);
                rectF.union(rectF2);
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        Matrix matrix = this.matrix;
        matrix.reset();
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix.set(transformKeyframeAnimation.getMatrix());
        }
        Path path = this.path;
        path.reset();
        if (this.hidden) {
            return path;
        }
        List<Content> list = this.contents;
        for (int size = list.size() - 1; size >= 0; size--) {
            Content content = list.get(size);
            if (content instanceof PathContent) {
                path.addPath(((PathContent) content).getPath(), matrix);
            }
        }
        return path;
    }

    final List<PathContent> getPathList() {
        if (this.pathContents == null) {
            this.pathContents = new ArrayList();
            int i = 0;
            while (true) {
                List<Content> list = this.contents;
                if (i >= list.size()) {
                    break;
                }
                Content content = list.get(i);
                if (content instanceof PathContent) {
                    this.pathContents.add((PathContent) content);
                }
                i++;
            }
        }
        return this.pathContents;
    }

    final Matrix getTransformationMatrix() {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            return transformKeyframeAnimation.getMatrix();
        }
        Matrix matrix = this.matrix;
        matrix.reset();
        return matrix;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        String str = this.name;
        if (!keyPath.matches(i, str)) {
            return;
        }
        if (!"__container".equals(str)) {
            keyPath2 = keyPath2.addKey(str);
            if (keyPath.fullyResolvesTo(i, str)) {
                ((ArrayList) list).add(keyPath2.resolve(this));
            }
        }
        if (!keyPath.propagateToChildren(i, str)) {
            return;
        }
        int incrementDepthBy = keyPath.incrementDepthBy(i, str) + i;
        int i2 = 0;
        while (true) {
            List<Content> list2 = this.contents;
            if (i2 >= list2.size()) {
                return;
            }
            Content content = list2.get(i2);
            if (content instanceof KeyPathElement) {
                ((KeyPathElement) content).resolveKeyPath(keyPath, incrementDepthBy, list, keyPath2);
            }
            i2++;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
        int size = list.size();
        List<Content> list3 = this.contents;
        ArrayList arrayList = new ArrayList(list3.size() + size);
        arrayList.addAll(list);
        for (int size2 = list3.size() - 1; size2 >= 0; size2--) {
            Content content = list3.get(size2);
            content.setContents(arrayList, list3.subList(0, size2));
            arrayList.add(content);
        }
    }

    ContentGroup(LottieDrawable lottieDrawable, BaseLayer baseLayer, String str, boolean z, List<Content> list, AnimatableTransform animatableTransform) {
        this.offScreenPaint = new LPaint();
        this.offScreenRectF = new RectF();
        this.matrix = new Matrix();
        this.path = new Path();
        this.rect = new RectF();
        this.name = str;
        this.lottieDrawable = lottieDrawable;
        this.hidden = z;
        this.contents = list;
        if (animatableTransform != null) {
            TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
            this.transformAnimation = transformKeyframeAnimation;
            transformKeyframeAnimation.addAnimationsToLayer(baseLayer);
            this.transformAnimation.addListener(this);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            Content content = (Content) arrayList2.get(size);
            if (content instanceof GreedyContent) {
                arrayList.add((GreedyContent) content);
            }
        }
        int size2 = arrayList.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                return;
            } else {
                ((GreedyContent) arrayList.get(size2)).absorbContent(arrayList2.listIterator(arrayList2.size()));
            }
        }
    }
}

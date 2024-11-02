.class public final Lcom/airbnb/lottie/model/layer/SolidLayer;
.super Lcom/airbnb/lottie/model/layer/BaseLayer;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public colorFilterAnimation:Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;

.field public final layerModel:Lcom/airbnb/lottie/model/layer/Layer;

.field public final paint:Lcom/airbnb/lottie/animation/LPaint;

.field public final path:Landroid/graphics/Path;

.field public final points:[F

.field public final rect:Landroid/graphics/RectF;


# direct methods
.method public constructor <init>(Lcom/airbnb/lottie/LottieDrawable;Lcom/airbnb/lottie/model/layer/Layer;)V
    .locals 1

    .line 1
    invoke-direct {p0, p1, p2}, Lcom/airbnb/lottie/model/layer/BaseLayer;-><init>(Lcom/airbnb/lottie/LottieDrawable;Lcom/airbnb/lottie/model/layer/Layer;)V

    .line 2
    .line 3
    .line 4
    new-instance p1, Landroid/graphics/RectF;

    .line 5
    .line 6
    invoke-direct {p1}, Landroid/graphics/RectF;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object p1, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->rect:Landroid/graphics/RectF;

    .line 10
    .line 11
    new-instance p1, Lcom/airbnb/lottie/animation/LPaint;

    .line 12
    .line 13
    invoke-direct {p1}, Lcom/airbnb/lottie/animation/LPaint;-><init>()V

    .line 14
    .line 15
    .line 16
    iput-object p1, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->paint:Lcom/airbnb/lottie/animation/LPaint;

    .line 17
    .line 18
    const/16 v0, 0x8

    .line 19
    .line 20
    new-array v0, v0, [F

    .line 21
    .line 22
    iput-object v0, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->points:[F

    .line 23
    .line 24
    new-instance v0, Landroid/graphics/Path;

    .line 25
    .line 26
    invoke-direct {v0}, Landroid/graphics/Path;-><init>()V

    .line 27
    .line 28
    .line 29
    iput-object v0, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->path:Landroid/graphics/Path;

    .line 30
    .line 31
    iput-object p2, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->layerModel:Lcom/airbnb/lottie/model/layer/Layer;

    .line 32
    .line 33
    const/4 p0, 0x0

    .line 34
    invoke-virtual {p1, p0}, Lcom/airbnb/lottie/animation/LPaint;->setAlpha(I)V

    .line 35
    .line 36
    .line 37
    sget-object p0, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    .line 38
    .line 39
    invoke-virtual {p1, p0}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 40
    .line 41
    .line 42
    iget p0, p2, Lcom/airbnb/lottie/model/layer/Layer;->solidColor:I

    .line 43
    .line 44
    invoke-virtual {p1, p0}, Landroid/graphics/Paint;->setColor(I)V

    .line 45
    .line 46
    .line 47
    return-void
.end method


# virtual methods
.method public final addValueCallback(Lcom/airbnb/lottie/value/LottieValueCallback;Ljava/lang/Object;)V
    .locals 1

    .line 1
    invoke-super {p0, p1, p2}, Lcom/airbnb/lottie/model/layer/BaseLayer;->addValueCallback(Lcom/airbnb/lottie/value/LottieValueCallback;Ljava/lang/Object;)V

    .line 2
    .line 3
    .line 4
    sget-object v0, Lcom/airbnb/lottie/LottieProperty;->COLOR_FILTER:Landroid/graphics/ColorFilter;

    .line 5
    .line 6
    if-ne p2, v0, :cond_1

    .line 7
    .line 8
    if-nez p1, :cond_0

    .line 9
    .line 10
    const/4 p1, 0x0

    .line 11
    iput-object p1, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->colorFilterAnimation:Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;

    .line 12
    .line 13
    goto :goto_0

    .line 14
    :cond_0
    new-instance p2, Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;

    .line 15
    .line 16
    invoke-direct {p2, p1}, Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;-><init>(Lcom/airbnb/lottie/value/LottieValueCallback;)V

    .line 17
    .line 18
    .line 19
    iput-object p2, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->colorFilterAnimation:Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;

    .line 20
    .line 21
    :cond_1
    :goto_0
    return-void
.end method

.method public final drawLayer(Landroid/graphics/Canvas;Landroid/graphics/Matrix;I)V
    .locals 10

    .line 1
    iget-object v0, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->layerModel:Lcom/airbnb/lottie/model/layer/Layer;

    .line 2
    .line 3
    iget v1, v0, Lcom/airbnb/lottie/model/layer/Layer;->solidColor:I

    .line 4
    .line 5
    invoke-static {v1}, Landroid/graphics/Color;->alpha(I)I

    .line 6
    .line 7
    .line 8
    move-result v1

    .line 9
    if-nez v1, :cond_0

    .line 10
    .line 11
    return-void

    .line 12
    :cond_0
    iget-object v2, p0, Lcom/airbnb/lottie/model/layer/BaseLayer;->transform:Lcom/airbnb/lottie/animation/keyframe/TransformKeyframeAnimation;

    .line 13
    .line 14
    iget-object v2, v2, Lcom/airbnb/lottie/animation/keyframe/TransformKeyframeAnimation;->opacity:Lcom/airbnb/lottie/animation/keyframe/BaseKeyframeAnimation;

    .line 15
    .line 16
    if-nez v2, :cond_1

    .line 17
    .line 18
    const/16 v2, 0x64

    .line 19
    .line 20
    goto :goto_0

    .line 21
    :cond_1
    invoke-virtual {v2}, Lcom/airbnb/lottie/animation/keyframe/BaseKeyframeAnimation;->getValue()Ljava/lang/Object;

    .line 22
    .line 23
    .line 24
    move-result-object v2

    .line 25
    check-cast v2, Ljava/lang/Integer;

    .line 26
    .line 27
    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    .line 28
    .line 29
    .line 30
    move-result v2

    .line 31
    :goto_0
    int-to-float p3, p3

    .line 32
    const/high16 v3, 0x437f0000    # 255.0f

    .line 33
    .line 34
    div-float/2addr p3, v3

    .line 35
    int-to-float v1, v1

    .line 36
    div-float/2addr v1, v3

    .line 37
    int-to-float v2, v2

    .line 38
    mul-float/2addr v1, v2

    .line 39
    const/high16 v2, 0x42c80000    # 100.0f

    .line 40
    .line 41
    div-float/2addr v1, v2

    .line 42
    mul-float/2addr v1, p3

    .line 43
    mul-float/2addr v1, v3

    .line 44
    float-to-int p3, v1

    .line 45
    iget-object v1, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->paint:Lcom/airbnb/lottie/animation/LPaint;

    .line 46
    .line 47
    invoke-virtual {v1, p3}, Lcom/airbnb/lottie/animation/LPaint;->setAlpha(I)V

    .line 48
    .line 49
    .line 50
    iget-object v2, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->colorFilterAnimation:Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;

    .line 51
    .line 52
    if-eqz v2, :cond_2

    .line 53
    .line 54
    invoke-virtual {v2}, Lcom/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation;->getValue()Ljava/lang/Object;

    .line 55
    .line 56
    .line 57
    move-result-object v2

    .line 58
    check-cast v2, Landroid/graphics/ColorFilter;

    .line 59
    .line 60
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setColorFilter(Landroid/graphics/ColorFilter;)Landroid/graphics/ColorFilter;

    .line 61
    .line 62
    .line 63
    :cond_2
    if-lez p3, :cond_3

    .line 64
    .line 65
    iget-object p3, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->points:[F

    .line 66
    .line 67
    const/4 v2, 0x0

    .line 68
    const/4 v3, 0x0

    .line 69
    aput v3, p3, v2

    .line 70
    .line 71
    const/4 v4, 0x1

    .line 72
    aput v3, p3, v4

    .line 73
    .line 74
    iget v5, v0, Lcom/airbnb/lottie/model/layer/Layer;->solidWidth:I

    .line 75
    .line 76
    int-to-float v5, v5

    .line 77
    const/4 v6, 0x2

    .line 78
    aput v5, p3, v6

    .line 79
    .line 80
    const/4 v7, 0x3

    .line 81
    aput v3, p3, v7

    .line 82
    .line 83
    const/4 v8, 0x4

    .line 84
    aput v5, p3, v8

    .line 85
    .line 86
    iget v0, v0, Lcom/airbnb/lottie/model/layer/Layer;->solidHeight:I

    .line 87
    .line 88
    int-to-float v0, v0

    .line 89
    const/4 v5, 0x5

    .line 90
    aput v0, p3, v5

    .line 91
    .line 92
    const/4 v9, 0x6

    .line 93
    aput v3, p3, v9

    .line 94
    .line 95
    const/4 v3, 0x7

    .line 96
    aput v0, p3, v3

    .line 97
    .line 98
    invoke-virtual {p2, p3}, Landroid/graphics/Matrix;->mapPoints([F)V

    .line 99
    .line 100
    .line 101
    iget-object p0, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->path:Landroid/graphics/Path;

    .line 102
    .line 103
    invoke-virtual {p0}, Landroid/graphics/Path;->reset()V

    .line 104
    .line 105
    .line 106
    aget p2, p3, v2

    .line 107
    .line 108
    aget v0, p3, v4

    .line 109
    .line 110
    invoke-virtual {p0, p2, v0}, Landroid/graphics/Path;->moveTo(FF)V

    .line 111
    .line 112
    .line 113
    aget p2, p3, v6

    .line 114
    .line 115
    aget v0, p3, v7

    .line 116
    .line 117
    invoke-virtual {p0, p2, v0}, Landroid/graphics/Path;->lineTo(FF)V

    .line 118
    .line 119
    .line 120
    aget p2, p3, v8

    .line 121
    .line 122
    aget v0, p3, v5

    .line 123
    .line 124
    invoke-virtual {p0, p2, v0}, Landroid/graphics/Path;->lineTo(FF)V

    .line 125
    .line 126
    .line 127
    aget p2, p3, v9

    .line 128
    .line 129
    aget v0, p3, v3

    .line 130
    .line 131
    invoke-virtual {p0, p2, v0}, Landroid/graphics/Path;->lineTo(FF)V

    .line 132
    .line 133
    .line 134
    aget p2, p3, v2

    .line 135
    .line 136
    aget p3, p3, v4

    .line 137
    .line 138
    invoke-virtual {p0, p2, p3}, Landroid/graphics/Path;->lineTo(FF)V

    .line 139
    .line 140
    .line 141
    invoke-virtual {p0}, Landroid/graphics/Path;->close()V

    .line 142
    .line 143
    .line 144
    invoke-virtual {p1, p0, v1}, Landroid/graphics/Canvas;->drawPath(Landroid/graphics/Path;Landroid/graphics/Paint;)V

    .line 145
    .line 146
    .line 147
    :cond_3
    return-void
.end method

.method public final getBounds(Landroid/graphics/RectF;Landroid/graphics/Matrix;Z)V
    .locals 2

    .line 1
    invoke-super {p0, p1, p2, p3}, Lcom/airbnb/lottie/model/layer/BaseLayer;->getBounds(Landroid/graphics/RectF;Landroid/graphics/Matrix;Z)V

    .line 2
    .line 3
    .line 4
    iget-object p2, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->rect:Landroid/graphics/RectF;

    .line 5
    .line 6
    iget-object p3, p0, Lcom/airbnb/lottie/model/layer/SolidLayer;->layerModel:Lcom/airbnb/lottie/model/layer/Layer;

    .line 7
    .line 8
    iget v0, p3, Lcom/airbnb/lottie/model/layer/Layer;->solidWidth:I

    .line 9
    .line 10
    int-to-float v0, v0

    .line 11
    iget p3, p3, Lcom/airbnb/lottie/model/layer/Layer;->solidHeight:I

    .line 12
    .line 13
    int-to-float p3, p3

    .line 14
    const/4 v1, 0x0

    .line 15
    invoke-virtual {p2, v1, v1, v0, p3}, Landroid/graphics/RectF;->set(FFFF)V

    .line 16
    .line 17
    .line 18
    iget-object p0, p0, Lcom/airbnb/lottie/model/layer/BaseLayer;->boundsMatrix:Landroid/graphics/Matrix;

    .line 19
    .line 20
    invoke-virtual {p0, p2}, Landroid/graphics/Matrix;->mapRect(Landroid/graphics/RectF;)Z

    .line 21
    .line 22
    .line 23
    invoke-virtual {p1, p2}, Landroid/graphics/RectF;->set(Landroid/graphics/RectF;)V

    .line 24
    .line 25
    .line 26
    return-void
.end method

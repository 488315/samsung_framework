.class public final Lcom/airbnb/lottie/animation/keyframe/TextKeyframeAnimation;
.super Lcom/airbnb/lottie/animation/keyframe/KeyframeAnimation;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/value/Keyframe;",
            ">;)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1}, Lcom/airbnb/lottie/animation/keyframe/KeyframeAnimation;-><init>(Ljava/util/List;)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final getValue(Lcom/airbnb/lottie/value/Keyframe;F)Ljava/lang/Object;
    .locals 8

    .line 1
    iget-object v0, p0, Lcom/airbnb/lottie/animation/keyframe/BaseKeyframeAnimation;->valueCallback:Lcom/airbnb/lottie/value/LottieValueCallback;

    .line 2
    .line 3
    iget-object v1, p1, Lcom/airbnb/lottie/value/Keyframe;->startValue:Ljava/lang/Object;

    .line 4
    .line 5
    if-eqz v0, :cond_2

    .line 6
    .line 7
    iget v2, p1, Lcom/airbnb/lottie/value/Keyframe;->startFrame:F

    .line 8
    .line 9
    iget-object v3, p1, Lcom/airbnb/lottie/value/Keyframe;->endFrame:Ljava/lang/Float;

    .line 10
    .line 11
    if-nez v3, :cond_0

    .line 12
    .line 13
    const v3, 0x7f7fffff    # Float.MAX_VALUE

    .line 14
    .line 15
    .line 16
    goto :goto_0

    .line 17
    :cond_0
    invoke-virtual {v3}, Ljava/lang/Float;->floatValue()F

    .line 18
    .line 19
    .line 20
    move-result v3

    .line 21
    :goto_0
    move-object v4, v1

    .line 22
    check-cast v4, Lcom/airbnb/lottie/model/DocumentData;

    .line 23
    .line 24
    iget-object p1, p1, Lcom/airbnb/lottie/value/Keyframe;->endValue:Ljava/lang/Object;

    .line 25
    .line 26
    if-nez p1, :cond_1

    .line 27
    .line 28
    move-object p1, v4

    .line 29
    goto :goto_1

    .line 30
    :cond_1
    check-cast p1, Lcom/airbnb/lottie/model/DocumentData;

    .line 31
    .line 32
    :goto_1
    invoke-virtual {p0}, Lcom/airbnb/lottie/animation/keyframe/BaseKeyframeAnimation;->getInterpolatedCurrentKeyframeProgress()F

    .line 33
    .line 34
    .line 35
    move-result v6

    .line 36
    iget v7, p0, Lcom/airbnb/lottie/animation/keyframe/BaseKeyframeAnimation;->progress:F

    .line 37
    .line 38
    move v1, v2

    .line 39
    move v2, v3

    .line 40
    move-object v3, v4

    .line 41
    move-object v4, p1

    .line 42
    move v5, p2

    .line 43
    invoke-virtual/range {v0 .. v7}, Lcom/airbnb/lottie/value/LottieValueCallback;->getValueInternal(FFLjava/lang/Object;Ljava/lang/Object;FFF)Ljava/lang/Object;

    .line 44
    .line 45
    .line 46
    move-result-object p0

    .line 47
    check-cast p0, Lcom/airbnb/lottie/model/DocumentData;

    .line 48
    .line 49
    goto :goto_3

    .line 50
    :cond_2
    const/high16 p0, 0x3f800000    # 1.0f

    .line 51
    .line 52
    cmpl-float p0, p2, p0

    .line 53
    .line 54
    if-nez p0, :cond_4

    .line 55
    .line 56
    iget-object p0, p1, Lcom/airbnb/lottie/value/Keyframe;->endValue:Ljava/lang/Object;

    .line 57
    .line 58
    if-nez p0, :cond_3

    .line 59
    .line 60
    goto :goto_2

    .line 61
    :cond_3
    check-cast p0, Lcom/airbnb/lottie/model/DocumentData;

    .line 62
    .line 63
    goto :goto_3

    .line 64
    :cond_4
    :goto_2
    move-object p0, v1

    .line 65
    check-cast p0, Lcom/airbnb/lottie/model/DocumentData;

    .line 66
    .line 67
    :goto_3
    return-object p0
.end method

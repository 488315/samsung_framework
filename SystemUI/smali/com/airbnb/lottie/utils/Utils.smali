.class public final Lcom/airbnb/lottie/utils/Utils;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final INV_SQRT_2:F

.field public static final threadLocalPathMeasure:Lcom/airbnb/lottie/utils/Utils$1;

.field public static final threadLocalPoints:Lcom/airbnb/lottie/utils/Utils$4;

.field public static final threadLocalTempPath:Lcom/airbnb/lottie/utils/Utils$2;

.field public static final threadLocalTempPath2:Lcom/airbnb/lottie/utils/Utils$3;


# direct methods
.method public static constructor <clinit>()V
    .locals 4

    .line 1
    new-instance v0, Lcom/airbnb/lottie/utils/Utils$1;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/airbnb/lottie/utils/Utils$1;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalPathMeasure:Lcom/airbnb/lottie/utils/Utils$1;

    .line 7
    .line 8
    new-instance v0, Lcom/airbnb/lottie/utils/Utils$2;

    .line 9
    .line 10
    invoke-direct {v0}, Lcom/airbnb/lottie/utils/Utils$2;-><init>()V

    .line 11
    .line 12
    .line 13
    sput-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalTempPath:Lcom/airbnb/lottie/utils/Utils$2;

    .line 14
    .line 15
    new-instance v0, Lcom/airbnb/lottie/utils/Utils$3;

    .line 16
    .line 17
    invoke-direct {v0}, Lcom/airbnb/lottie/utils/Utils$3;-><init>()V

    .line 18
    .line 19
    .line 20
    sput-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalTempPath2:Lcom/airbnb/lottie/utils/Utils$3;

    .line 21
    .line 22
    new-instance v0, Lcom/airbnb/lottie/utils/Utils$4;

    .line 23
    .line 24
    invoke-direct {v0}, Lcom/airbnb/lottie/utils/Utils$4;-><init>()V

    .line 25
    .line 26
    .line 27
    sput-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalPoints:Lcom/airbnb/lottie/utils/Utils$4;

    .line 28
    .line 29
    const-wide/high16 v0, 0x4000000000000000L    # 2.0

    .line 30
    .line 31
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 32
    .line 33
    .line 34
    move-result-wide v2

    .line 35
    div-double/2addr v2, v0

    .line 36
    double-to-float v0, v2

    .line 37
    sput v0, Lcom/airbnb/lottie/utils/Utils;->INV_SQRT_2:F

    .line 38
    .line 39
    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static applyTrimPathIfNeeded(Landroid/graphics/Path;FFF)V
    .locals 9

    .line 1
    sget-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalPathMeasure:Lcom/airbnb/lottie/utils/Utils$1;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/ThreadLocal;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Landroid/graphics/PathMeasure;

    .line 8
    .line 9
    sget-object v1, Lcom/airbnb/lottie/utils/Utils;->threadLocalTempPath:Lcom/airbnb/lottie/utils/Utils$2;

    .line 10
    .line 11
    invoke-virtual {v1}, Ljava/lang/ThreadLocal;->get()Ljava/lang/Object;

    .line 12
    .line 13
    .line 14
    move-result-object v1

    .line 15
    check-cast v1, Landroid/graphics/Path;

    .line 16
    .line 17
    sget-object v2, Lcom/airbnb/lottie/utils/Utils;->threadLocalTempPath2:Lcom/airbnb/lottie/utils/Utils$3;

    .line 18
    .line 19
    invoke-virtual {v2}, Ljava/lang/ThreadLocal;->get()Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object v2

    .line 23
    check-cast v2, Landroid/graphics/Path;

    .line 24
    .line 25
    const/4 v3, 0x0

    .line 26
    invoke-virtual {v0, p0, v3}, Landroid/graphics/PathMeasure;->setPath(Landroid/graphics/Path;Z)V

    .line 27
    .line 28
    .line 29
    invoke-virtual {v0}, Landroid/graphics/PathMeasure;->getLength()F

    .line 30
    .line 31
    .line 32
    move-result v3

    .line 33
    const/high16 v4, 0x3f800000    # 1.0f

    .line 34
    .line 35
    cmpl-float v5, p1, v4

    .line 36
    .line 37
    const/4 v6, 0x0

    .line 38
    if-nez v5, :cond_0

    .line 39
    .line 40
    cmpl-float v5, p2, v6

    .line 41
    .line 42
    if-nez v5, :cond_0

    .line 43
    .line 44
    return-void

    .line 45
    :cond_0
    cmpg-float v5, v3, v4

    .line 46
    .line 47
    if-ltz v5, :cond_9

    .line 48
    .line 49
    sub-float v5, p2, p1

    .line 50
    .line 51
    sub-float/2addr v5, v4

    .line 52
    invoke-static {v5}, Ljava/lang/Math;->abs(F)F

    .line 53
    .line 54
    .line 55
    move-result v4

    .line 56
    float-to-double v4, v4

    .line 57
    const-wide v7, 0x3f847ae147ae147bL    # 0.01

    .line 58
    .line 59
    .line 60
    .line 61
    .line 62
    cmpg-double v4, v4, v7

    .line 63
    .line 64
    if-gez v4, :cond_1

    .line 65
    .line 66
    goto :goto_1

    .line 67
    :cond_1
    mul-float/2addr p1, v3

    .line 68
    mul-float/2addr p2, v3

    .line 69
    invoke-static {p1, p2}, Ljava/lang/Math;->min(FF)F

    .line 70
    .line 71
    .line 72
    move-result v4

    .line 73
    invoke-static {p1, p2}, Ljava/lang/Math;->max(FF)F

    .line 74
    .line 75
    .line 76
    move-result p1

    .line 77
    mul-float/2addr p3, v3

    .line 78
    add-float/2addr v4, p3

    .line 79
    add-float/2addr p1, p3

    .line 80
    cmpl-float p2, v4, v3

    .line 81
    .line 82
    if-ltz p2, :cond_2

    .line 83
    .line 84
    cmpl-float p2, p1, v3

    .line 85
    .line 86
    if-ltz p2, :cond_2

    .line 87
    .line 88
    invoke-static {v4, v3}, Lcom/airbnb/lottie/utils/MiscUtils;->floorMod(FF)I

    .line 89
    .line 90
    .line 91
    move-result p2

    .line 92
    int-to-float v4, p2

    .line 93
    invoke-static {p1, v3}, Lcom/airbnb/lottie/utils/MiscUtils;->floorMod(FF)I

    .line 94
    .line 95
    .line 96
    move-result p1

    .line 97
    int-to-float p1, p1

    .line 98
    :cond_2
    cmpg-float p2, v4, v6

    .line 99
    .line 100
    if-gez p2, :cond_3

    .line 101
    .line 102
    invoke-static {v4, v3}, Lcom/airbnb/lottie/utils/MiscUtils;->floorMod(FF)I

    .line 103
    .line 104
    .line 105
    move-result p2

    .line 106
    int-to-float v4, p2

    .line 107
    :cond_3
    cmpg-float p2, p1, v6

    .line 108
    .line 109
    if-gez p2, :cond_4

    .line 110
    .line 111
    invoke-static {p1, v3}, Lcom/airbnb/lottie/utils/MiscUtils;->floorMod(FF)I

    .line 112
    .line 113
    .line 114
    move-result p1

    .line 115
    int-to-float p1, p1

    .line 116
    :cond_4
    cmpl-float p2, v4, p1

    .line 117
    .line 118
    if-nez p2, :cond_5

    .line 119
    .line 120
    invoke-virtual {p0}, Landroid/graphics/Path;->reset()V

    .line 121
    .line 122
    .line 123
    return-void

    .line 124
    :cond_5
    if-ltz p2, :cond_6

    .line 125
    .line 126
    sub-float/2addr v4, v3

    .line 127
    :cond_6
    invoke-virtual {v1}, Landroid/graphics/Path;->reset()V

    .line 128
    .line 129
    .line 130
    const/4 p2, 0x1

    .line 131
    invoke-virtual {v0, v4, p1, v1, p2}, Landroid/graphics/PathMeasure;->getSegment(FFLandroid/graphics/Path;Z)Z

    .line 132
    .line 133
    .line 134
    cmpl-float p3, p1, v3

    .line 135
    .line 136
    if-lez p3, :cond_7

    .line 137
    .line 138
    invoke-virtual {v2}, Landroid/graphics/Path;->reset()V

    .line 139
    .line 140
    .line 141
    rem-float/2addr p1, v3

    .line 142
    invoke-virtual {v0, v6, p1, v2, p2}, Landroid/graphics/PathMeasure;->getSegment(FFLandroid/graphics/Path;Z)Z

    .line 143
    .line 144
    .line 145
    invoke-virtual {v1, v2}, Landroid/graphics/Path;->addPath(Landroid/graphics/Path;)V

    .line 146
    .line 147
    .line 148
    goto :goto_0

    .line 149
    :cond_7
    cmpg-float p1, v4, v6

    .line 150
    .line 151
    if-gez p1, :cond_8

    .line 152
    .line 153
    invoke-virtual {v2}, Landroid/graphics/Path;->reset()V

    .line 154
    .line 155
    .line 156
    add-float/2addr v4, v3

    .line 157
    invoke-virtual {v0, v4, v3, v2, p2}, Landroid/graphics/PathMeasure;->getSegment(FFLandroid/graphics/Path;Z)Z

    .line 158
    .line 159
    .line 160
    invoke-virtual {v1, v2}, Landroid/graphics/Path;->addPath(Landroid/graphics/Path;)V

    .line 161
    .line 162
    .line 163
    :cond_8
    :goto_0
    invoke-virtual {p0, v1}, Landroid/graphics/Path;->set(Landroid/graphics/Path;)V

    .line 164
    .line 165
    .line 166
    :cond_9
    :goto_1
    return-void
.end method

.method public static closeQuietly(Ljava/io/Closeable;)V
    .locals 0

    .line 1
    if-eqz p0, :cond_0

    .line 2
    .line 3
    :try_start_0
    invoke-interface {p0}, Ljava/io/Closeable;->close()V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :catch_0
    move-exception p0

    .line 8
    throw p0

    .line 9
    :catch_1
    :cond_0
    :goto_0
    return-void
.end method

.method public static dpScale()F
    .locals 1

    .line 1
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    iget v0, v0, Landroid/util/DisplayMetrics;->density:F

    .line 10
    .line 11
    return v0
.end method

.method public static getScale(Landroid/graphics/Matrix;)F
    .locals 6

    .line 1
    sget-object v0, Lcom/airbnb/lottie/utils/Utils;->threadLocalPoints:Lcom/airbnb/lottie/utils/Utils$4;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/ThreadLocal;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [F

    .line 8
    .line 9
    const/4 v1, 0x0

    .line 10
    const/4 v2, 0x0

    .line 11
    aput v2, v0, v1

    .line 12
    .line 13
    const/4 v3, 0x1

    .line 14
    aput v2, v0, v3

    .line 15
    .line 16
    const/4 v2, 0x2

    .line 17
    sget v4, Lcom/airbnb/lottie/utils/Utils;->INV_SQRT_2:F

    .line 18
    .line 19
    aput v4, v0, v2

    .line 20
    .line 21
    const/4 v5, 0x3

    .line 22
    aput v4, v0, v5

    .line 23
    .line 24
    invoke-virtual {p0, v0}, Landroid/graphics/Matrix;->mapPoints([F)V

    .line 25
    .line 26
    .line 27
    aget p0, v0, v2

    .line 28
    .line 29
    aget v1, v0, v1

    .line 30
    .line 31
    sub-float/2addr p0, v1

    .line 32
    aget v1, v0, v5

    .line 33
    .line 34
    aget v0, v0, v3

    .line 35
    .line 36
    sub-float/2addr v1, v0

    .line 37
    float-to-double v2, p0

    .line 38
    float-to-double v0, v1

    .line 39
    invoke-static {v2, v3, v0, v1}, Ljava/lang/Math;->hypot(DD)D

    .line 40
    .line 41
    .line 42
    move-result-wide v0

    .line 43
    double-to-float p0, v0

    .line 44
    return p0
.end method

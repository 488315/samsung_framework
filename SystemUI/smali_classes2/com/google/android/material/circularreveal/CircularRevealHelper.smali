.class public final Lcom/google/android/material/circularreveal/CircularRevealHelper;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final delegate:Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;

.field public overlayDrawable:Landroid/graphics/drawable/Drawable;

.field public revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

.field public final scrimPaint:Landroid/graphics/Paint;

.field public final view:Landroid/view/View;


# direct methods
.method public constructor <init>(Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;)V
    .locals 2

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->delegate:Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;

    .line 5
    .line 6
    check-cast p1, Landroid/view/View;

    .line 7
    .line 8
    iput-object p1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 9
    .line 10
    const/4 v0, 0x0

    .line 11
    invoke-virtual {p1, v0}, Landroid/view/View;->setWillNotDraw(Z)V

    .line 12
    .line 13
    .line 14
    new-instance p1, Landroid/graphics/Path;

    .line 15
    .line 16
    invoke-direct {p1}, Landroid/graphics/Path;-><init>()V

    .line 17
    .line 18
    .line 19
    new-instance p1, Landroid/graphics/Paint;

    .line 20
    .line 21
    const/4 v1, 0x7

    .line 22
    invoke-direct {p1, v1}, Landroid/graphics/Paint;-><init>(I)V

    .line 23
    .line 24
    .line 25
    new-instance p1, Landroid/graphics/Paint;

    .line 26
    .line 27
    const/4 v1, 0x1

    .line 28
    invoke-direct {p1, v1}, Landroid/graphics/Paint;-><init>(I)V

    .line 29
    .line 30
    .line 31
    iput-object p1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->scrimPaint:Landroid/graphics/Paint;

    .line 32
    .line 33
    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setColor(I)V

    .line 34
    .line 35
    .line 36
    return-void
.end method


# virtual methods
.method public final draw(Landroid/graphics/Canvas;)V
    .locals 9

    .line 1
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/4 v2, 0x1

    .line 5
    if-eqz v0, :cond_2

    .line 6
    .line 7
    iget v0, v0, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 8
    .line 9
    const v3, 0x7f7fffff    # Float.MAX_VALUE

    .line 10
    .line 11
    .line 12
    cmpl-float v0, v0, v3

    .line 13
    .line 14
    if-nez v0, :cond_0

    .line 15
    .line 16
    move v0, v2

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    move v0, v1

    .line 19
    :goto_0
    if-eqz v0, :cond_1

    .line 20
    .line 21
    goto :goto_1

    .line 22
    :cond_1
    move v0, v1

    .line 23
    goto :goto_2

    .line 24
    :cond_2
    :goto_1
    move v0, v2

    .line 25
    :goto_2
    xor-int/2addr v0, v2

    .line 26
    iget-object v8, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->scrimPaint:Landroid/graphics/Paint;

    .line 27
    .line 28
    iget-object v3, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->delegate:Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;

    .line 29
    .line 30
    iget-object v4, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 31
    .line 32
    if-eqz v0, :cond_4

    .line 33
    .line 34
    invoke-interface {v3, p1}, Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;->actualDraw(Landroid/graphics/Canvas;)V

    .line 35
    .line 36
    .line 37
    invoke-virtual {v8}, Landroid/graphics/Paint;->getColor()I

    .line 38
    .line 39
    .line 40
    move-result v0

    .line 41
    invoke-static {v0}, Landroid/graphics/Color;->alpha(I)I

    .line 42
    .line 43
    .line 44
    move-result v0

    .line 45
    if-eqz v0, :cond_3

    .line 46
    .line 47
    move v0, v2

    .line 48
    goto :goto_3

    .line 49
    :cond_3
    move v0, v1

    .line 50
    :goto_3
    if-eqz v0, :cond_6

    .line 51
    .line 52
    const/4 v0, 0x0

    .line 53
    const/4 v5, 0x0

    .line 54
    invoke-virtual {v4}, Landroid/view/View;->getWidth()I

    .line 55
    .line 56
    .line 57
    move-result v3

    .line 58
    int-to-float v6, v3

    .line 59
    invoke-virtual {v4}, Landroid/view/View;->getHeight()I

    .line 60
    .line 61
    .line 62
    move-result v3

    .line 63
    int-to-float v7, v3

    .line 64
    move-object v3, p1

    .line 65
    move v4, v0

    .line 66
    invoke-virtual/range {v3 .. v8}, Landroid/graphics/Canvas;->drawRect(FFFFLandroid/graphics/Paint;)V

    .line 67
    .line 68
    .line 69
    goto :goto_5

    .line 70
    :cond_4
    invoke-interface {v3, p1}, Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;->actualDraw(Landroid/graphics/Canvas;)V

    .line 71
    .line 72
    .line 73
    invoke-virtual {v8}, Landroid/graphics/Paint;->getColor()I

    .line 74
    .line 75
    .line 76
    move-result v0

    .line 77
    invoke-static {v0}, Landroid/graphics/Color;->alpha(I)I

    .line 78
    .line 79
    .line 80
    move-result v0

    .line 81
    if-eqz v0, :cond_5

    .line 82
    .line 83
    move v0, v2

    .line 84
    goto :goto_4

    .line 85
    :cond_5
    move v0, v1

    .line 86
    :goto_4
    if-eqz v0, :cond_6

    .line 87
    .line 88
    const/4 v0, 0x0

    .line 89
    const/4 v5, 0x0

    .line 90
    invoke-virtual {v4}, Landroid/view/View;->getWidth()I

    .line 91
    .line 92
    .line 93
    move-result v3

    .line 94
    int-to-float v6, v3

    .line 95
    invoke-virtual {v4}, Landroid/view/View;->getHeight()I

    .line 96
    .line 97
    .line 98
    move-result v3

    .line 99
    int-to-float v7, v3

    .line 100
    move-object v3, p1

    .line 101
    move v4, v0

    .line 102
    invoke-virtual/range {v3 .. v8}, Landroid/graphics/Canvas;->drawRect(FFFFLandroid/graphics/Paint;)V

    .line 103
    .line 104
    .line 105
    :cond_6
    :goto_5
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->overlayDrawable:Landroid/graphics/drawable/Drawable;

    .line 106
    .line 107
    if-eqz v0, :cond_7

    .line 108
    .line 109
    iget-object v3, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 110
    .line 111
    if-eqz v3, :cond_7

    .line 112
    .line 113
    move v1, v2

    .line 114
    :cond_7
    if-eqz v1, :cond_8

    .line 115
    .line 116
    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->getBounds()Landroid/graphics/Rect;

    .line 117
    .line 118
    .line 119
    move-result-object v0

    .line 120
    iget-object v1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 121
    .line 122
    iget v1, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerX:F

    .line 123
    .line 124
    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    .line 125
    .line 126
    .line 127
    move-result v2

    .line 128
    int-to-float v2, v2

    .line 129
    const/high16 v3, 0x40000000    # 2.0f

    .line 130
    .line 131
    div-float/2addr v2, v3

    .line 132
    sub-float/2addr v1, v2

    .line 133
    iget-object v2, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 134
    .line 135
    iget v2, v2, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerY:F

    .line 136
    .line 137
    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    .line 138
    .line 139
    .line 140
    move-result v0

    .line 141
    int-to-float v0, v0

    .line 142
    div-float/2addr v0, v3

    .line 143
    sub-float/2addr v2, v0

    .line 144
    invoke-virtual {p1, v1, v2}, Landroid/graphics/Canvas;->translate(FF)V

    .line 145
    .line 146
    .line 147
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->overlayDrawable:Landroid/graphics/drawable/Drawable;

    .line 148
    .line 149
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 150
    .line 151
    .line 152
    neg-float p0, v1

    .line 153
    neg-float v0, v2

    .line 154
    invoke-virtual {p1, p0, v0}, Landroid/graphics/Canvas;->translate(FF)V

    .line 155
    .line 156
    .line 157
    :cond_8
    return-void
.end method

.method public final getCircularRevealScrimColor()I
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->scrimPaint:Landroid/graphics/Paint;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/graphics/Paint;->getColor()I

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    return p0
.end method

.method public final getRevealInfo()Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    const/4 p0, 0x0

    .line 6
    return-object p0

    .line 7
    :cond_0
    new-instance v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 8
    .line 9
    invoke-direct {v1, v0}, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;-><init>(Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;)V

    .line 10
    .line 11
    .line 12
    iget v0, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 13
    .line 14
    const v2, 0x7f7fffff    # Float.MAX_VALUE

    .line 15
    .line 16
    .line 17
    cmpl-float v0, v0, v2

    .line 18
    .line 19
    if-nez v0, :cond_1

    .line 20
    .line 21
    const/4 v0, 0x1

    .line 22
    goto :goto_0

    .line 23
    :cond_1
    const/4 v0, 0x0

    .line 24
    :goto_0
    if-eqz v0, :cond_2

    .line 25
    .line 26
    iget v0, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerX:F

    .line 27
    .line 28
    iget v2, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerY:F

    .line 29
    .line 30
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 31
    .line 32
    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    .line 33
    .line 34
    .line 35
    move-result v3

    .line 36
    int-to-float v3, v3

    .line 37
    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    .line 38
    .line 39
    .line 40
    move-result p0

    .line 41
    int-to-float p0, p0

    .line 42
    invoke-static {v0, v2, v3, p0}, Lcom/google/android/material/math/MathUtils;->distanceToFurthestCorner(FFFF)F

    .line 43
    .line 44
    .line 45
    move-result p0

    .line 46
    iput p0, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 47
    .line 48
    :cond_2
    return-object v1
.end method

.method public final isOpaque()Z
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->delegate:Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;

    .line 2
    .line 3
    invoke-interface {v0}, Lcom/google/android/material/circularreveal/CircularRevealHelper$Delegate;->actualIsOpaque()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    const/4 v1, 0x0

    .line 8
    if-eqz v0, :cond_3

    .line 9
    .line 10
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 11
    .line 12
    const/4 v0, 0x1

    .line 13
    if-eqz p0, :cond_2

    .line 14
    .line 15
    iget p0, p0, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 16
    .line 17
    const v2, 0x7f7fffff    # Float.MAX_VALUE

    .line 18
    .line 19
    .line 20
    cmpl-float p0, p0, v2

    .line 21
    .line 22
    if-nez p0, :cond_0

    .line 23
    .line 24
    move p0, v0

    .line 25
    goto :goto_0

    .line 26
    :cond_0
    move p0, v1

    .line 27
    :goto_0
    if-eqz p0, :cond_1

    .line 28
    .line 29
    goto :goto_1

    .line 30
    :cond_1
    move p0, v1

    .line 31
    goto :goto_2

    .line 32
    :cond_2
    :goto_1
    move p0, v0

    .line 33
    :goto_2
    xor-int/2addr p0, v0

    .line 34
    if-nez p0, :cond_3

    .line 35
    .line 36
    move v1, v0

    .line 37
    :cond_3
    return v1
.end method

.method public final setCircularRevealOverlayDrawable(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->overlayDrawable:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 4
    .line 5
    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    .line 6
    .line 7
    .line 8
    return-void
.end method

.method public final setCircularRevealScrimColor(I)V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->scrimPaint:Landroid/graphics/Paint;

    .line 2
    .line 3
    invoke-virtual {v0, p1}, Landroid/graphics/Paint;->setColor(I)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 7
    .line 8
    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    .line 9
    .line 10
    .line 11
    return-void
.end method

.method public final setRevealInfo(Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;)V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->view:Landroid/view/View;

    .line 2
    .line 3
    if-nez p1, :cond_0

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    iput-object p1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 7
    .line 8
    goto :goto_2

    .line 9
    :cond_0
    iget-object v1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 10
    .line 11
    if-nez v1, :cond_1

    .line 12
    .line 13
    new-instance v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 14
    .line 15
    invoke-direct {v1, p1}, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;-><init>(Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;)V

    .line 16
    .line 17
    .line 18
    iput-object v1, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 19
    .line 20
    goto :goto_0

    .line 21
    :cond_1
    iget v2, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerX:F

    .line 22
    .line 23
    iget v3, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerY:F

    .line 24
    .line 25
    iget v4, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 26
    .line 27
    iput v2, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerX:F

    .line 28
    .line 29
    iput v3, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerY:F

    .line 30
    .line 31
    iput v4, v1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 32
    .line 33
    :goto_0
    iget v1, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 34
    .line 35
    iget v2, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerX:F

    .line 36
    .line 37
    iget p1, p1, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->centerY:F

    .line 38
    .line 39
    invoke-virtual {v0}, Landroid/view/View;->getWidth()I

    .line 40
    .line 41
    .line 42
    move-result v3

    .line 43
    int-to-float v3, v3

    .line 44
    invoke-virtual {v0}, Landroid/view/View;->getHeight()I

    .line 45
    .line 46
    .line 47
    move-result v4

    .line 48
    int-to-float v4, v4

    .line 49
    invoke-static {v2, p1, v3, v4}, Lcom/google/android/material/math/MathUtils;->distanceToFurthestCorner(FFFF)F

    .line 50
    .line 51
    .line 52
    move-result p1

    .line 53
    const v2, 0x38d1b717    # 1.0E-4f

    .line 54
    .line 55
    .line 56
    add-float/2addr v1, v2

    .line 57
    cmpl-float p1, v1, p1

    .line 58
    .line 59
    if-ltz p1, :cond_2

    .line 60
    .line 61
    const/4 p1, 0x1

    .line 62
    goto :goto_1

    .line 63
    :cond_2
    const/4 p1, 0x0

    .line 64
    :goto_1
    if-eqz p1, :cond_3

    .line 65
    .line 66
    iget-object p0, p0, Lcom/google/android/material/circularreveal/CircularRevealHelper;->revealInfo:Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;

    .line 67
    .line 68
    const p1, 0x7f7fffff    # Float.MAX_VALUE

    .line 69
    .line 70
    .line 71
    iput p1, p0, Lcom/google/android/material/circularreveal/CircularRevealWidget$RevealInfo;->radius:F

    .line 72
    .line 73
    :cond_3
    :goto_2
    invoke-virtual {v0}, Landroid/view/View;->invalidate()V

    .line 74
    .line 75
    .line 76
    return-void
.end method

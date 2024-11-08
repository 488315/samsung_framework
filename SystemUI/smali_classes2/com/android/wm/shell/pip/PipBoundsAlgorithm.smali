.class public Lcom/android/wm/shell/pip/PipBoundsAlgorithm;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mDefaultAspectRatio:F

.field public mDefaultStackGravity:I

.field public mMaxAspectRatio:F

.field public mMinAspectRatio:F

.field public final mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

.field public final mPipKeepClearAlgorithm:Lcom/android/wm/shell/pip/PipKeepClearAlgorithmInterface;

.field public final mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

.field public final mSnapAlgorithm:Lcom/android/wm/shell/pip/PipSnapAlgorithm;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/android/wm/shell/pip/PipBoundsState;Lcom/android/wm/shell/pip/PipSnapAlgorithm;Lcom/android/wm/shell/pip/PipKeepClearAlgorithmInterface;Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    .line 5
    .line 6
    iput-object p3, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mSnapAlgorithm:Lcom/android/wm/shell/pip/PipSnapAlgorithm;

    .line 7
    .line 8
    iput-object p4, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipKeepClearAlgorithm:Lcom/android/wm/shell/pip/PipKeepClearAlgorithmInterface;

    .line 9
    .line 10
    iput-object p5, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 11
    .line 12
    invoke-virtual {p0, p1}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->reloadResources(Landroid/content/Context;)V

    .line 13
    .line 14
    .line 15
    iget p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultAspectRatio:F

    .line 16
    .line 17
    iput p0, p2, Lcom/android/wm/shell/pip/PipBoundsState;->mAspectRatio:F

    .line 18
    .line 19
    return-void
.end method

.method public static getMovementBounds(Landroid/graphics/Rect;Landroid/graphics/Rect;Landroid/graphics/Rect;I)V
    .locals 3

    .line 6
    invoke-virtual {p2, p1}, Landroid/graphics/Rect;->set(Landroid/graphics/Rect;)V

    .line 7
    iget v0, p1, Landroid/graphics/Rect;->left:I

    iget v1, p1, Landroid/graphics/Rect;->right:I

    .line 8
    invoke-virtual {p0}, Landroid/graphics/Rect;->width()I

    move-result v2

    sub-int/2addr v1, v2

    .line 9
    invoke-static {v0, v1}, Ljava/lang/Math;->max(II)I

    move-result v0

    iput v0, p2, Landroid/graphics/Rect;->right:I

    .line 10
    iget v0, p1, Landroid/graphics/Rect;->top:I

    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    .line 11
    invoke-virtual {p0}, Landroid/graphics/Rect;->height()I

    move-result p0

    sub-int/2addr p1, p0

    .line 12
    invoke-static {v0, p1}, Ljava/lang/Math;->max(II)I

    move-result p0

    sub-int/2addr p0, p3

    .line 13
    iput p0, p2, Landroid/graphics/Rect;->bottom:I

    return-void
.end method

.method public static getValidSourceHintRect(Landroid/app/PictureInPictureParams;Landroid/graphics/Rect;)Landroid/graphics/Rect;
    .locals 2

    .line 1
    const/4 v0, 0x0

    .line 2
    if-eqz p0, :cond_0

    .line 3
    .line 4
    invoke-virtual {p0}, Landroid/app/PictureInPictureParams;->hasSourceBoundsHint()Z

    .line 5
    .line 6
    .line 7
    move-result v1

    .line 8
    if-eqz v1, :cond_0

    .line 9
    .line 10
    invoke-virtual {p0}, Landroid/app/PictureInPictureParams;->getSourceRectHint()Landroid/graphics/Rect;

    .line 11
    .line 12
    .line 13
    move-result-object p0

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move-object p0, v0

    .line 16
    :goto_0
    if-eqz p0, :cond_1

    .line 17
    .line 18
    invoke-virtual {p1, p0}, Landroid/graphics/Rect;->contains(Landroid/graphics/Rect;)Z

    .line 19
    .line 20
    .line 21
    move-result p1

    .line 22
    if-eqz p1, :cond_1

    .line 23
    .line 24
    return-object p0

    .line 25
    :cond_1
    return-object v0
.end method


# virtual methods
.method public final dump(Ljava/io/PrintWriter;Ljava/lang/String;)V
    .locals 2

    .line 1
    const-string v0, "  "

    .line 2
    .line 3
    invoke-static {p2, v0}, Landroidx/concurrent/futures/AbstractResolvableFuture$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    new-instance v1, Ljava/lang/StringBuilder;

    .line 8
    .line 9
    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 10
    .line 11
    .line 12
    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 13
    .line 14
    .line 15
    const-string p2, "PipBoundsAlgorithm"

    .line 16
    .line 17
    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 18
    .line 19
    .line 20
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 21
    .line 22
    .line 23
    move-result-object p2

    .line 24
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 25
    .line 26
    .line 27
    new-instance p2, Ljava/lang/StringBuilder;

    .line 28
    .line 29
    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    .line 30
    .line 31
    .line 32
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 33
    .line 34
    .line 35
    const-string v1, "mDefaultAspectRatio="

    .line 36
    .line 37
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 38
    .line 39
    .line 40
    iget v1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultAspectRatio:F

    .line 41
    .line 42
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 43
    .line 44
    .line 45
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 46
    .line 47
    .line 48
    move-result-object p2

    .line 49
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 50
    .line 51
    .line 52
    new-instance p2, Ljava/lang/StringBuilder;

    .line 53
    .line 54
    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    .line 55
    .line 56
    .line 57
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 58
    .line 59
    .line 60
    const-string v1, "mMinAspectRatio="

    .line 61
    .line 62
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 63
    .line 64
    .line 65
    iget v1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMinAspectRatio:F

    .line 66
    .line 67
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 68
    .line 69
    .line 70
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 71
    .line 72
    .line 73
    move-result-object p2

    .line 74
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 75
    .line 76
    .line 77
    new-instance p2, Ljava/lang/StringBuilder;

    .line 78
    .line 79
    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    .line 80
    .line 81
    .line 82
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 83
    .line 84
    .line 85
    const-string v1, "mMaxAspectRatio="

    .line 86
    .line 87
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 88
    .line 89
    .line 90
    iget v1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMaxAspectRatio:F

    .line 91
    .line 92
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 93
    .line 94
    .line 95
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 96
    .line 97
    .line 98
    move-result-object p2

    .line 99
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 100
    .line 101
    .line 102
    new-instance p2, Ljava/lang/StringBuilder;

    .line 103
    .line 104
    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    .line 105
    .line 106
    .line 107
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 108
    .line 109
    .line 110
    const-string v1, "mDefaultStackGravity="

    .line 111
    .line 112
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 113
    .line 114
    .line 115
    iget v1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultStackGravity:I

    .line 116
    .line 117
    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 118
    .line 119
    .line 120
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 121
    .line 122
    .line 123
    move-result-object p2

    .line 124
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 125
    .line 126
    .line 127
    new-instance p2, Ljava/lang/StringBuilder;

    .line 128
    .line 129
    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    .line 130
    .line 131
    .line 132
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 133
    .line 134
    .line 135
    const-string v0, "mSnapAlgorithm"

    .line 136
    .line 137
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 138
    .line 139
    .line 140
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mSnapAlgorithm:Lcom/android/wm/shell/pip/PipSnapAlgorithm;

    .line 141
    .line 142
    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 143
    .line 144
    .line 145
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 146
    .line 147
    .line 148
    move-result-object p0

    .line 149
    invoke-virtual {p1, p0}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 150
    .line 151
    .line 152
    return-void
.end method

.method public getAdjustedDestinationBounds(Landroid/graphics/Rect;F)Landroid/graphics/Rect;
    .locals 3

    .line 1
    new-instance v0, Landroid/graphics/Rect;

    .line 2
    .line 3
    invoke-direct {v0, p1}, Landroid/graphics/Rect;-><init>(Landroid/graphics/Rect;)V

    .line 4
    .line 5
    .line 6
    iget p1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMinAspectRatio:F

    .line 7
    .line 8
    invoke-static {p1, p2}, Ljava/lang/Float;->compare(FF)I

    .line 9
    .line 10
    .line 11
    move-result p1

    .line 12
    const/4 v1, 0x1

    .line 13
    const/4 v2, 0x0

    .line 14
    if-gtz p1, :cond_0

    .line 15
    .line 16
    iget p1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMaxAspectRatio:F

    .line 17
    .line 18
    invoke-static {p2, p1}, Ljava/lang/Float;->compare(FF)I

    .line 19
    .line 20
    .line 21
    move-result p1

    .line 22
    if-gtz p1, :cond_0

    .line 23
    .line 24
    move p1, v1

    .line 25
    goto :goto_0

    .line 26
    :cond_0
    move p1, v2

    .line 27
    :goto_0
    if-eqz p1, :cond_1

    .line 28
    .line 29
    invoke-virtual {p0, v0, p2, v1, v2}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->transformBoundsToAspectRatio(Landroid/graphics/Rect;FZZ)V

    .line 30
    .line 31
    .line 32
    :cond_1
    return-object v0
.end method

.method public final getDefaultBounds(Landroid/util/Size;F)Landroid/graphics/Rect;
    .locals 8

    .line 1
    new-instance v7, Landroid/graphics/Rect;

    .line 2
    .line 3
    invoke-direct {v7}, Landroid/graphics/Rect;-><init>()V

    .line 4
    .line 5
    .line 6
    const/high16 v0, -0x40800000    # -1.0f

    .line 7
    .line 8
    cmpl-float v0, p2, v0

    .line 9
    .line 10
    iget-object v1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mSnapAlgorithm:Lcom/android/wm/shell/pip/PipSnapAlgorithm;

    .line 11
    .line 12
    const/4 v2, 0x1

    .line 13
    const/4 v3, 0x0

    .line 14
    if-eqz v0, :cond_0

    .line 15
    .line 16
    if-eqz p1, :cond_0

    .line 17
    .line 18
    invoke-virtual {p1}, Landroid/util/Size;->getWidth()I

    .line 19
    .line 20
    .line 21
    move-result v0

    .line 22
    invoke-virtual {p1}, Landroid/util/Size;->getHeight()I

    .line 23
    .line 24
    .line 25
    move-result p1

    .line 26
    invoke-virtual {v7, v3, v3, v0, p1}, Landroid/graphics/Rect;->set(IIII)V

    .line 27
    .line 28
    .line 29
    invoke-virtual {p0, v7, v2}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getMovementBounds(Landroid/graphics/Rect;Z)Landroid/graphics/Rect;

    .line 30
    .line 31
    .line 32
    move-result-object p0

    .line 33
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 34
    .line 35
    .line 36
    invoke-static {p2, v7, p0}, Lcom/android/wm/shell/pip/PipSnapAlgorithm;->applySnapFraction(FLandroid/graphics/Rect;Landroid/graphics/Rect;)V

    .line 37
    .line 38
    .line 39
    return-object v7

    .line 40
    :cond_0
    new-instance p1, Landroid/graphics/Rect;

    .line 41
    .line 42
    invoke-direct {p1}, Landroid/graphics/Rect;-><init>()V

    .line 43
    .line 44
    .line 45
    invoke-virtual {p0, p1}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getInsetBounds(Landroid/graphics/Rect;)V

    .line 46
    .line 47
    .line 48
    iget v4, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultAspectRatio:F

    .line 49
    .line 50
    iget-object v5, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 51
    .line 52
    iget-object v5, v5, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->mSizeSpecSourceImpl:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;

    .line 53
    .line 54
    invoke-interface {v5, v4}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;->getDefaultSize(F)Landroid/util/Size;

    .line 55
    .line 56
    .line 57
    move-result-object v4

    .line 58
    if-eqz v0, :cond_1

    .line 59
    .line 60
    invoke-virtual {v4}, Landroid/util/Size;->getWidth()I

    .line 61
    .line 62
    .line 63
    move-result p1

    .line 64
    invoke-virtual {v4}, Landroid/util/Size;->getHeight()I

    .line 65
    .line 66
    .line 67
    move-result v0

    .line 68
    invoke-virtual {v7, v3, v3, p1, v0}, Landroid/graphics/Rect;->set(IIII)V

    .line 69
    .line 70
    .line 71
    invoke-virtual {p0, v7, v2}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getMovementBounds(Landroid/graphics/Rect;Z)Landroid/graphics/Rect;

    .line 72
    .line 73
    .line 74
    move-result-object p0

    .line 75
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 76
    .line 77
    .line 78
    invoke-static {p2, v7, p0}, Lcom/android/wm/shell/pip/PipSnapAlgorithm;->applySnapFraction(FLandroid/graphics/Rect;Landroid/graphics/Rect;)V

    .line 79
    .line 80
    .line 81
    goto :goto_1

    .line 82
    :cond_1
    iget v0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultStackGravity:I

    .line 83
    .line 84
    invoke-virtual {v4}, Landroid/util/Size;->getWidth()I

    .line 85
    .line 86
    .line 87
    move-result v1

    .line 88
    invoke-virtual {v4}, Landroid/util/Size;->getHeight()I

    .line 89
    .line 90
    .line 91
    move-result v2

    .line 92
    const/4 v4, 0x0

    .line 93
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    .line 94
    .line 95
    iget-boolean p2, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mIsImeShowing:Z

    .line 96
    .line 97
    if-eqz p2, :cond_2

    .line 98
    .line 99
    iget p2, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mImeHeight:I

    .line 100
    .line 101
    goto :goto_0

    .line 102
    :cond_2
    move p2, v3

    .line 103
    :goto_0
    iget-boolean v5, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mIsShelfShowing:Z

    .line 104
    .line 105
    if-eqz v5, :cond_3

    .line 106
    .line 107
    iget v3, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mShelfHeight:I

    .line 108
    .line 109
    :cond_3
    invoke-static {p2, v3}, Ljava/lang/Math;->max(II)I

    .line 110
    .line 111
    .line 112
    move-result v5

    .line 113
    move-object v3, p1

    .line 114
    move-object v6, v7

    .line 115
    invoke-static/range {v0 .. v6}, Landroid/view/Gravity;->apply(IIILandroid/graphics/Rect;IILandroid/graphics/Rect;)V

    .line 116
    .line 117
    .line 118
    :goto_1
    return-object v7
.end method

.method public getEntryDestinationBounds()Landroid/graphics/Rect;
    .locals 4

    .line 1
    invoke-virtual {p0}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getEntryDestinationBoundsIgnoringKeepClearAreas()Landroid/graphics/Rect;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    new-instance v1, Landroid/graphics/Rect;

    .line 6
    .line 7
    invoke-direct {v1}, Landroid/graphics/Rect;-><init>()V

    .line 8
    .line 9
    .line 10
    invoke-virtual {p0, v1}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getInsetBounds(Landroid/graphics/Rect;)V

    .line 11
    .line 12
    .line 13
    iget-object v2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    .line 14
    .line 15
    iget-object v3, v2, Lcom/android/wm/shell/pip/PipBoundsState;->mRestrictedKeepClearAreas:Ljava/util/Set;

    .line 16
    .line 17
    invoke-virtual {v2}, Lcom/android/wm/shell/pip/PipBoundsState;->getUnrestrictedKeepClearAreas()Ljava/util/Set;

    .line 18
    .line 19
    .line 20
    move-result-object v2

    .line 21
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipKeepClearAlgorithm:Lcom/android/wm/shell/pip/PipKeepClearAlgorithmInterface;

    .line 22
    .line 23
    invoke-interface {p0, v0, v3, v2, v1}, Lcom/android/wm/shell/pip/PipKeepClearAlgorithmInterface;->findUnoccludedPosition(Landroid/graphics/Rect;Ljava/util/Set;Ljava/util/Set;Landroid/graphics/Rect;)Landroid/graphics/Rect;

    .line 24
    .line 25
    .line 26
    move-result-object p0

    .line 27
    return-object p0
.end method

.method public final getEntryDestinationBoundsIgnoringKeepClearAreas()Landroid/graphics/Rect;
    .locals 6

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    .line 2
    .line 3
    iget-object v1, v0, Lcom/android/wm/shell/pip/PipBoundsState;->mPipReentryState:Lcom/android/wm/shell/pip/PipBoundsState$PipReentryState;

    .line 4
    .line 5
    if-eqz v1, :cond_0

    .line 6
    .line 7
    iget-object v2, v1, Lcom/android/wm/shell/pip/PipBoundsState$PipReentryState;->mSize:Landroid/util/Size;

    .line 8
    .line 9
    iget v3, v1, Lcom/android/wm/shell/pip/PipBoundsState$PipReentryState;->mSnapFraction:F

    .line 10
    .line 11
    invoke-virtual {p0, v2, v3}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getDefaultBounds(Landroid/util/Size;F)Landroid/graphics/Rect;

    .line 12
    .line 13
    .line 14
    move-result-object v2

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    const/high16 v2, -0x40800000    # -1.0f

    .line 17
    .line 18
    const/4 v3, 0x0

    .line 19
    invoke-virtual {p0, v3, v2}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getDefaultBounds(Landroid/util/Size;F)Landroid/graphics/Rect;

    .line 20
    .line 21
    .line 22
    move-result-object v2

    .line 23
    :goto_0
    const/4 v3, 0x1

    .line 24
    const/4 v4, 0x0

    .line 25
    if-eqz v1, :cond_1

    .line 26
    .line 27
    iget-object v1, v1, Lcom/android/wm/shell/pip/PipBoundsState$PipReentryState;->mSize:Landroid/util/Size;

    .line 28
    .line 29
    if-eqz v1, :cond_1

    .line 30
    .line 31
    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    .line 32
    .line 33
    .line 34
    move-result v1

    .line 35
    iget-object v5, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 36
    .line 37
    iget v5, v5, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->mDefaultMinWidth:I

    .line 38
    .line 39
    if-le v1, v5, :cond_1

    .line 40
    .line 41
    move v1, v3

    .line 42
    goto :goto_1

    .line 43
    :cond_1
    move v1, v4

    .line 44
    :goto_1
    iget v0, v0, Lcom/android/wm/shell/pip/PipBoundsState;->mAspectRatio:F

    .line 45
    .line 46
    new-instance v5, Landroid/graphics/Rect;

    .line 47
    .line 48
    invoke-direct {v5, v2}, Landroid/graphics/Rect;-><init>(Landroid/graphics/Rect;)V

    .line 49
    .line 50
    .line 51
    iget v2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMinAspectRatio:F

    .line 52
    .line 53
    invoke-static {v2, v0}, Ljava/lang/Float;->compare(FF)I

    .line 54
    .line 55
    .line 56
    move-result v2

    .line 57
    if-gtz v2, :cond_2

    .line 58
    .line 59
    iget v2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMaxAspectRatio:F

    .line 60
    .line 61
    invoke-static {v0, v2}, Ljava/lang/Float;->compare(FF)I

    .line 62
    .line 63
    .line 64
    move-result v2

    .line 65
    if-gtz v2, :cond_2

    .line 66
    .line 67
    goto :goto_2

    .line 68
    :cond_2
    move v3, v4

    .line 69
    :goto_2
    if-eqz v3, :cond_3

    .line 70
    .line 71
    invoke-virtual {p0, v5, v0, v4, v1}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->transformBoundsToAspectRatio(Landroid/graphics/Rect;FZZ)V

    .line 72
    .line 73
    .line 74
    :cond_3
    return-object v5
.end method

.method public final getInsetBounds(Landroid/graphics/Rect;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->getInsetBounds()Landroid/graphics/Rect;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    invoke-virtual {p1, p0}, Landroid/graphics/Rect;->set(Landroid/graphics/Rect;)V

    .line 8
    .line 9
    .line 10
    return-void
.end method

.method public final getMinimalSize(Landroid/content/pm/ActivityInfo;)Landroid/util/Size;
    .locals 3

    .line 1
    const/4 v0, 0x0

    .line 2
    if-eqz p1, :cond_1

    .line 3
    .line 4
    iget-object p1, p1, Landroid/content/pm/ActivityInfo;->windowLayout:Landroid/content/pm/ActivityInfo$WindowLayout;

    .line 5
    .line 6
    if-nez p1, :cond_0

    .line 7
    .line 8
    goto :goto_0

    .line 9
    :cond_0
    iget v1, p1, Landroid/content/pm/ActivityInfo$WindowLayout;->minWidth:I

    .line 10
    .line 11
    if-lez v1, :cond_1

    .line 12
    .line 13
    iget v1, p1, Landroid/content/pm/ActivityInfo$WindowLayout;->minHeight:I

    .line 14
    .line 15
    if-lez v1, :cond_1

    .line 16
    .line 17
    new-instance v0, Landroid/util/Size;

    .line 18
    .line 19
    iget v1, p1, Landroid/content/pm/ActivityInfo$WindowLayout;->minWidth:I

    .line 20
    .line 21
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 22
    .line 23
    invoke-virtual {p0}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->getOverrideMinEdgeSize()I

    .line 24
    .line 25
    .line 26
    move-result v2

    .line 27
    invoke-static {v1, v2}, Ljava/lang/Math;->max(II)I

    .line 28
    .line 29
    .line 30
    move-result v1

    .line 31
    iget p1, p1, Landroid/content/pm/ActivityInfo$WindowLayout;->minHeight:I

    .line 32
    .line 33
    invoke-virtual {p0}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->getOverrideMinEdgeSize()I

    .line 34
    .line 35
    .line 36
    move-result p0

    .line 37
    invoke-static {p1, p0}, Ljava/lang/Math;->max(II)I

    .line 38
    .line 39
    .line 40
    move-result p0

    .line 41
    invoke-direct {v0, v1, p0}, Landroid/util/Size;-><init>(II)V

    .line 42
    .line 43
    .line 44
    :cond_1
    :goto_0
    return-object v0
.end method

.method public final getMovementBounds(Landroid/graphics/Rect;Z)Landroid/graphics/Rect;
    .locals 1

    .line 1
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 2
    invoke-virtual {p0, v0}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getInsetBounds(Landroid/graphics/Rect;)V

    if-eqz p2, :cond_0

    .line 3
    iget-object p0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    iget-boolean p2, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mIsImeShowing:Z

    if-eqz p2, :cond_0

    .line 4
    iget p0, p0, Lcom/android/wm/shell/pip/PipBoundsState;->mImeHeight:I

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    .line 5
    :goto_0
    invoke-static {p1, v0, v0, p0}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getMovementBounds(Landroid/graphics/Rect;Landroid/graphics/Rect;Landroid/graphics/Rect;I)V

    return-object v0
.end method

.method public onConfigurationChanged(Landroid/content/Context;)V
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->reloadResources(Landroid/content/Context;)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public final reloadResources(Landroid/content/Context;)V
    .locals 2

    .line 1
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 2
    .line 3
    .line 4
    move-result-object p1

    .line 5
    const v0, 0x7f0701d7

    .line 6
    .line 7
    .line 8
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getFloat(I)F

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    iput v0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultAspectRatio:F

    .line 13
    .line 14
    const v0, 0x7f0b0019

    .line 15
    .line 16
    .line 17
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getInteger(I)I

    .line 18
    .line 19
    .line 20
    move-result v0

    .line 21
    iput v0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mDefaultStackGravity:I

    .line 22
    .line 23
    const v0, 0x7f130361

    .line 24
    .line 25
    .line 26
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 27
    .line 28
    .line 29
    move-result-object v0

    .line 30
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    .line 31
    .line 32
    .line 33
    move-result v1

    .line 34
    if-nez v1, :cond_0

    .line 35
    .line 36
    invoke-static {v0}, Landroid/util/Size;->parseSize(Ljava/lang/String;)Landroid/util/Size;

    .line 37
    .line 38
    .line 39
    :cond_0
    const v0, 0x10500d8

    .line 40
    .line 41
    .line 42
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getFloat(I)F

    .line 43
    .line 44
    .line 45
    move-result v0

    .line 46
    iput v0, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMinAspectRatio:F

    .line 47
    .line 48
    const v0, 0x10500d7

    .line 49
    .line 50
    .line 51
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getFloat(I)F

    .line 52
    .line 53
    .line 54
    move-result p1

    .line 55
    iput p1, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mMaxAspectRatio:F

    .line 56
    .line 57
    return-void
.end method

.method public final transformBoundsToAspectRatio(Landroid/graphics/Rect;FZZ)V
    .locals 4

    .line 1
    const/4 v0, 0x1

    .line 2
    invoke-virtual {p0, p1, v0}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getMovementBounds(Landroid/graphics/Rect;Z)Landroid/graphics/Rect;

    .line 3
    .line 4
    .line 5
    move-result-object v1

    .line 6
    iget-object v2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipBoundsState:Lcom/android/wm/shell/pip/PipBoundsState;

    .line 7
    .line 8
    iget v2, v2, Lcom/android/wm/shell/pip/PipBoundsState;->mStashedState:I

    .line 9
    .line 10
    iget-object v3, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mSnapAlgorithm:Lcom/android/wm/shell/pip/PipSnapAlgorithm;

    .line 11
    .line 12
    invoke-virtual {v3, v2, p1, v1}, Lcom/android/wm/shell/pip/PipSnapAlgorithm;->getSnapFraction(ILandroid/graphics/Rect;Landroid/graphics/Rect;)F

    .line 13
    .line 14
    .line 15
    move-result v1

    .line 16
    iget-object v2, p0, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->mPipSizeSpecHandler:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;

    .line 17
    .line 18
    if-nez p3, :cond_1

    .line 19
    .line 20
    if-eqz p4, :cond_0

    .line 21
    .line 22
    goto :goto_0

    .line 23
    :cond_0
    iget-object p3, v2, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->mSizeSpecSourceImpl:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;

    .line 24
    .line 25
    invoke-interface {p3, p2}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;->getDefaultSize(F)Landroid/util/Size;

    .line 26
    .line 27
    .line 28
    move-result-object p2

    .line 29
    goto :goto_1

    .line 30
    :cond_1
    :goto_0
    new-instance p3, Landroid/util/Size;

    .line 31
    .line 32
    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    .line 33
    .line 34
    .line 35
    move-result p4

    .line 36
    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    .line 37
    .line 38
    .line 39
    move-result v3

    .line 40
    invoke-direct {p3, p4, v3}, Landroid/util/Size;-><init>(II)V

    .line 41
    .line 42
    .line 43
    iget-object p4, v2, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->mOverrideMinSize:Landroid/util/Size;

    .line 44
    .line 45
    invoke-virtual {p3, p4}, Landroid/util/Size;->equals(Ljava/lang/Object;)Z

    .line 46
    .line 47
    .line 48
    move-result p4

    .line 49
    if-eqz p4, :cond_2

    .line 50
    .line 51
    invoke-virtual {v2, p2}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->adjustOverrideMinSizeToAspectRatio(F)Landroid/util/Size;

    .line 52
    .line 53
    .line 54
    move-result-object p2

    .line 55
    goto :goto_1

    .line 56
    :cond_2
    iget-object p4, v2, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler;->mSizeSpecSourceImpl:Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;

    .line 57
    .line 58
    invoke-interface {p4, p3, p2}, Lcom/android/wm/shell/pip/phone/PipSizeSpecHandler$SizeSpecSource;->getSizeForAspectRatio(Landroid/util/Size;F)Landroid/util/Size;

    .line 59
    .line 60
    .line 61
    move-result-object p2

    .line 62
    :goto_1
    invoke-virtual {p1}, Landroid/graphics/Rect;->centerX()I

    .line 63
    .line 64
    .line 65
    move-result p3

    .line 66
    int-to-float p3, p3

    .line 67
    invoke-virtual {p2}, Landroid/util/Size;->getWidth()I

    .line 68
    .line 69
    .line 70
    move-result p4

    .line 71
    int-to-float p4, p4

    .line 72
    const/high16 v2, 0x40000000    # 2.0f

    .line 73
    .line 74
    div-float/2addr p4, v2

    .line 75
    sub-float/2addr p3, p4

    .line 76
    float-to-int p3, p3

    .line 77
    invoke-virtual {p1}, Landroid/graphics/Rect;->centerY()I

    .line 78
    .line 79
    .line 80
    move-result p4

    .line 81
    int-to-float p4, p4

    .line 82
    invoke-virtual {p2}, Landroid/util/Size;->getHeight()I

    .line 83
    .line 84
    .line 85
    move-result v3

    .line 86
    int-to-float v3, v3

    .line 87
    div-float/2addr v3, v2

    .line 88
    sub-float/2addr p4, v3

    .line 89
    float-to-int p4, p4

    .line 90
    invoke-virtual {p2}, Landroid/util/Size;->getWidth()I

    .line 91
    .line 92
    .line 93
    move-result v2

    .line 94
    add-int/2addr v2, p3

    .line 95
    invoke-virtual {p2}, Landroid/util/Size;->getHeight()I

    .line 96
    .line 97
    .line 98
    move-result p2

    .line 99
    add-int/2addr p2, p4

    .line 100
    invoke-virtual {p1, p3, p4, v2, p2}, Landroid/graphics/Rect;->set(IIII)V

    .line 101
    .line 102
    .line 103
    invoke-virtual {p0, p1, v0}, Lcom/android/wm/shell/pip/PipBoundsAlgorithm;->getMovementBounds(Landroid/graphics/Rect;Z)Landroid/graphics/Rect;

    .line 104
    .line 105
    .line 106
    move-result-object p0

    .line 107
    invoke-static {v1, p1, p0}, Lcom/android/wm/shell/pip/PipSnapAlgorithm;->applySnapFraction(FLandroid/graphics/Rect;Landroid/graphics/Rect;)V

    .line 108
    .line 109
    .line 110
    return-void
.end method

.class public final Landroidx/cardview/widget/RoundRectDrawable;
.super Landroid/graphics/drawable/Drawable;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mBackground:Landroid/content/res/ColorStateList;

.field public final mBoundsF:Landroid/graphics/RectF;

.field public final mBoundsI:Landroid/graphics/Rect;

.field public mInsetForPadding:Z

.field public mInsetForRadius:Z

.field public mPadding:F

.field public final mPaint:Landroid/graphics/Paint;

.field public mRadius:F

.field public mTint:Landroid/content/res/ColorStateList;

.field public mTintFilter:Landroid/graphics/PorterDuffColorFilter;

.field public mTintMode:Landroid/graphics/PorterDuff$Mode;


# direct methods
.method public constructor <init>(Landroid/content/res/ColorStateList;F)V
    .locals 2

    .line 1
    invoke-direct {p0}, Landroid/graphics/drawable/Drawable;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, 0x0

    .line 5
    iput-boolean v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mInsetForPadding:Z

    .line 6
    .line 7
    const/4 v1, 0x1

    .line 8
    iput-boolean v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mInsetForRadius:Z

    .line 9
    .line 10
    sget-object v1, Landroid/graphics/PorterDuff$Mode;->SRC_IN:Landroid/graphics/PorterDuff$Mode;

    .line 11
    .line 12
    iput-object v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintMode:Landroid/graphics/PorterDuff$Mode;

    .line 13
    .line 14
    iput p2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mRadius:F

    .line 15
    .line 16
    new-instance p2, Landroid/graphics/Paint;

    .line 17
    .line 18
    const/4 v1, 0x5

    .line 19
    invoke-direct {p2, v1}, Landroid/graphics/Paint;-><init>(I)V

    .line 20
    .line 21
    .line 22
    iput-object p2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 23
    .line 24
    if-nez p1, :cond_0

    .line 25
    .line 26
    invoke-static {v0}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    .line 27
    .line 28
    .line 29
    move-result-object p1

    .line 30
    :cond_0
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBackground:Landroid/content/res/ColorStateList;

    .line 31
    .line 32
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getState()[I

    .line 33
    .line 34
    .line 35
    move-result-object v0

    .line 36
    iget-object v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBackground:Landroid/content/res/ColorStateList;

    .line 37
    .line 38
    invoke-virtual {v1}, Landroid/content/res/ColorStateList;->getDefaultColor()I

    .line 39
    .line 40
    .line 41
    move-result v1

    .line 42
    invoke-virtual {p1, v0, v1}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    .line 43
    .line 44
    .line 45
    move-result p1

    .line 46
    invoke-virtual {p2, p1}, Landroid/graphics/Paint;->setColor(I)V

    .line 47
    .line 48
    .line 49
    new-instance p1, Landroid/graphics/RectF;

    .line 50
    .line 51
    invoke-direct {p1}, Landroid/graphics/RectF;-><init>()V

    .line 52
    .line 53
    .line 54
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsF:Landroid/graphics/RectF;

    .line 55
    .line 56
    new-instance p1, Landroid/graphics/Rect;

    .line 57
    .line 58
    invoke-direct {p1}, Landroid/graphics/Rect;-><init>()V

    .line 59
    .line 60
    .line 61
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsI:Landroid/graphics/Rect;

    .line 62
    .line 63
    return-void
.end method


# virtual methods
.method public final createTintFilter(Landroid/content/res/ColorStateList;Landroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;
    .locals 1

    .line 1
    if-eqz p1, :cond_1

    .line 2
    .line 3
    if-nez p2, :cond_0

    .line 4
    .line 5
    goto :goto_0

    .line 6
    :cond_0
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getState()[I

    .line 7
    .line 8
    .line 9
    move-result-object p0

    .line 10
    const/4 v0, 0x0

    .line 11
    invoke-virtual {p1, p0, v0}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    .line 12
    .line 13
    .line 14
    move-result p0

    .line 15
    new-instance p1, Landroid/graphics/PorterDuffColorFilter;

    .line 16
    .line 17
    invoke-direct {p1, p0, p2}, Landroid/graphics/PorterDuffColorFilter;-><init>(ILandroid/graphics/PorterDuff$Mode;)V

    .line 18
    .line 19
    .line 20
    return-object p1

    .line 21
    :cond_1
    :goto_0
    const/4 p0, 0x0

    .line 22
    return-object p0
.end method

.method public final draw(Landroid/graphics/Canvas;)V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 2
    .line 3
    iget-object v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintFilter:Landroid/graphics/PorterDuffColorFilter;

    .line 4
    .line 5
    if-eqz v1, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/graphics/Paint;->getColorFilter()Landroid/graphics/ColorFilter;

    .line 8
    .line 9
    .line 10
    move-result-object v1

    .line 11
    if-nez v1, :cond_0

    .line 12
    .line 13
    iget-object v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintFilter:Landroid/graphics/PorterDuffColorFilter;

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColorFilter(Landroid/graphics/ColorFilter;)Landroid/graphics/ColorFilter;

    .line 16
    .line 17
    .line 18
    const/4 v1, 0x1

    .line 19
    goto :goto_0

    .line 20
    :cond_0
    const/4 v1, 0x0

    .line 21
    :goto_0
    iget-object v2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsF:Landroid/graphics/RectF;

    .line 22
    .line 23
    iget p0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mRadius:F

    .line 24
    .line 25
    invoke-virtual {p1, v2, p0, p0, v0}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 26
    .line 27
    .line 28
    if-eqz v1, :cond_1

    .line 29
    .line 30
    const/4 p0, 0x0

    .line 31
    invoke-virtual {v0, p0}, Landroid/graphics/Paint;->setColorFilter(Landroid/graphics/ColorFilter;)Landroid/graphics/ColorFilter;

    .line 32
    .line 33
    .line 34
    :cond_1
    return-void
.end method

.method public final getOpacity()I
    .locals 0

    .line 1
    const/4 p0, -0x3

    .line 2
    return p0
.end method

.method public final getOutline(Landroid/graphics/Outline;)V
    .locals 1

    .line 1
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsI:Landroid/graphics/Rect;

    .line 2
    .line 3
    iget p0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mRadius:F

    .line 4
    .line 5
    invoke-virtual {p1, v0, p0}, Landroid/graphics/Outline;->setRoundRect(Landroid/graphics/Rect;F)V

    .line 6
    .line 7
    .line 8
    return-void
.end method

.method public final isStateful()Z
    .locals 1

    .line 1
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTint:Landroid/content/res/ColorStateList;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    invoke-virtual {v0}, Landroid/content/res/ColorStateList;->isStateful()Z

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-nez v0, :cond_2

    .line 10
    .line 11
    :cond_0
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBackground:Landroid/content/res/ColorStateList;

    .line 12
    .line 13
    if-eqz v0, :cond_1

    .line 14
    .line 15
    invoke-virtual {v0}, Landroid/content/res/ColorStateList;->isStateful()Z

    .line 16
    .line 17
    .line 18
    move-result v0

    .line 19
    if-nez v0, :cond_2

    .line 20
    .line 21
    :cond_1
    invoke-super {p0}, Landroid/graphics/drawable/Drawable;->isStateful()Z

    .line 22
    .line 23
    .line 24
    move-result p0

    .line 25
    if-eqz p0, :cond_3

    .line 26
    .line 27
    :cond_2
    const/4 p0, 0x1

    .line 28
    goto :goto_0

    .line 29
    :cond_3
    const/4 p0, 0x0

    .line 30
    :goto_0
    return p0
.end method

.method public final onBoundsChange(Landroid/graphics/Rect;)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Landroid/graphics/drawable/Drawable;->onBoundsChange(Landroid/graphics/Rect;)V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0, p1}, Landroidx/cardview/widget/RoundRectDrawable;->updateBounds(Landroid/graphics/Rect;)V

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public final onStateChange([I)Z
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBackground:Landroid/content/res/ColorStateList;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroid/content/res/ColorStateList;->getDefaultColor()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    invoke-virtual {v0, p1, v1}, Landroid/content/res/ColorStateList;->getColorForState([II)I

    .line 8
    .line 9
    .line 10
    move-result p1

    .line 11
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 12
    .line 13
    invoke-virtual {v0}, Landroid/graphics/Paint;->getColor()I

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    const/4 v1, 0x1

    .line 18
    if-eq p1, v0, :cond_0

    .line 19
    .line 20
    move v0, v1

    .line 21
    goto :goto_0

    .line 22
    :cond_0
    const/4 v0, 0x0

    .line 23
    :goto_0
    if-eqz v0, :cond_1

    .line 24
    .line 25
    iget-object v2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 26
    .line 27
    invoke-virtual {v2, p1}, Landroid/graphics/Paint;->setColor(I)V

    .line 28
    .line 29
    .line 30
    :cond_1
    iget-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTint:Landroid/content/res/ColorStateList;

    .line 31
    .line 32
    if-eqz p1, :cond_2

    .line 33
    .line 34
    iget-object v2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintMode:Landroid/graphics/PorterDuff$Mode;

    .line 35
    .line 36
    if-eqz v2, :cond_2

    .line 37
    .line 38
    invoke-virtual {p0, p1, v2}, Landroidx/cardview/widget/RoundRectDrawable;->createTintFilter(Landroid/content/res/ColorStateList;Landroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;

    .line 39
    .line 40
    .line 41
    move-result-object p1

    .line 42
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintFilter:Landroid/graphics/PorterDuffColorFilter;

    .line 43
    .line 44
    return v1

    .line 45
    :cond_2
    return v0
.end method

.method public final setAlpha(I)V
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/graphics/Paint;->setAlpha(I)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final setColorFilter(Landroid/graphics/ColorFilter;)V
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPaint:Landroid/graphics/Paint;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/graphics/Paint;->setColorFilter(Landroid/graphics/ColorFilter;)Landroid/graphics/ColorFilter;

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final setTintList(Landroid/content/res/ColorStateList;)V
    .locals 1

    .line 1
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTint:Landroid/content/res/ColorStateList;

    .line 2
    .line 3
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintMode:Landroid/graphics/PorterDuff$Mode;

    .line 4
    .line 5
    invoke-virtual {p0, p1, v0}, Landroidx/cardview/widget/RoundRectDrawable;->createTintFilter(Landroid/content/res/ColorStateList;Landroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;

    .line 6
    .line 7
    .line 8
    move-result-object p1

    .line 9
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintFilter:Landroid/graphics/PorterDuffColorFilter;

    .line 10
    .line 11
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->invalidateSelf()V

    .line 12
    .line 13
    .line 14
    return-void
.end method

.method public final setTintMode(Landroid/graphics/PorterDuff$Mode;)V
    .locals 1

    .line 1
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintMode:Landroid/graphics/PorterDuff$Mode;

    .line 2
    .line 3
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTint:Landroid/content/res/ColorStateList;

    .line 4
    .line 5
    invoke-virtual {p0, v0, p1}, Landroidx/cardview/widget/RoundRectDrawable;->createTintFilter(Landroid/content/res/ColorStateList;Landroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;

    .line 6
    .line 7
    .line 8
    move-result-object p1

    .line 9
    iput-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mTintFilter:Landroid/graphics/PorterDuffColorFilter;

    .line 10
    .line 11
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->invalidateSelf()V

    .line 12
    .line 13
    .line 14
    return-void
.end method

.method public final updateBounds(Landroid/graphics/Rect;)V
    .locals 8

    .line 1
    if-nez p1, :cond_0

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getBounds()Landroid/graphics/Rect;

    .line 4
    .line 5
    .line 6
    move-result-object p1

    .line 7
    :cond_0
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsF:Landroid/graphics/RectF;

    .line 8
    .line 9
    iget v1, p1, Landroid/graphics/Rect;->left:I

    .line 10
    .line 11
    int-to-float v1, v1

    .line 12
    iget v2, p1, Landroid/graphics/Rect;->top:I

    .line 13
    .line 14
    int-to-float v2, v2

    .line 15
    iget v3, p1, Landroid/graphics/Rect;->right:I

    .line 16
    .line 17
    int-to-float v3, v3

    .line 18
    iget v4, p1, Landroid/graphics/Rect;->bottom:I

    .line 19
    .line 20
    int-to-float v4, v4

    .line 21
    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/graphics/RectF;->set(FFFF)V

    .line 22
    .line 23
    .line 24
    iget-object v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsI:Landroid/graphics/Rect;

    .line 25
    .line 26
    invoke-virtual {v0, p1}, Landroid/graphics/Rect;->set(Landroid/graphics/Rect;)V

    .line 27
    .line 28
    .line 29
    iget-boolean p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mInsetForPadding:Z

    .line 30
    .line 31
    if-eqz p1, :cond_2

    .line 32
    .line 33
    iget p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPadding:F

    .line 34
    .line 35
    iget v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mRadius:F

    .line 36
    .line 37
    iget-boolean v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mInsetForRadius:Z

    .line 38
    .line 39
    invoke-static {p1, v0, v1}, Landroidx/cardview/widget/RoundRectDrawableWithShadow;->calculateVerticalPadding(FFZ)F

    .line 40
    .line 41
    .line 42
    move-result p1

    .line 43
    iget v0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mPadding:F

    .line 44
    .line 45
    iget v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mRadius:F

    .line 46
    .line 47
    iget-boolean v2, p0, Landroidx/cardview/widget/RoundRectDrawable;->mInsetForRadius:Z

    .line 48
    .line 49
    if-eqz v2, :cond_1

    .line 50
    .line 51
    float-to-double v2, v0

    .line 52
    const-wide/high16 v4, 0x3ff0000000000000L    # 1.0

    .line 53
    .line 54
    sget-wide v6, Landroidx/cardview/widget/RoundRectDrawableWithShadow;->COS_45:D

    .line 55
    .line 56
    sub-double/2addr v4, v6

    .line 57
    float-to-double v0, v1

    .line 58
    mul-double/2addr v4, v0

    .line 59
    add-double/2addr v4, v2

    .line 60
    double-to-float v0, v4

    .line 61
    :cond_1
    iget-object v1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsI:Landroid/graphics/Rect;

    .line 62
    .line 63
    float-to-double v2, v0

    .line 64
    invoke-static {v2, v3}, Ljava/lang/Math;->ceil(D)D

    .line 65
    .line 66
    .line 67
    move-result-wide v2

    .line 68
    double-to-int v0, v2

    .line 69
    float-to-double v2, p1

    .line 70
    invoke-static {v2, v3}, Ljava/lang/Math;->ceil(D)D

    .line 71
    .line 72
    .line 73
    move-result-wide v2

    .line 74
    double-to-int p1, v2

    .line 75
    invoke-virtual {v1, v0, p1}, Landroid/graphics/Rect;->inset(II)V

    .line 76
    .line 77
    .line 78
    iget-object p1, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsF:Landroid/graphics/RectF;

    .line 79
    .line 80
    iget-object p0, p0, Landroidx/cardview/widget/RoundRectDrawable;->mBoundsI:Landroid/graphics/Rect;

    .line 81
    .line 82
    invoke-virtual {p1, p0}, Landroid/graphics/RectF;->set(Landroid/graphics/Rect;)V

    .line 83
    .line 84
    .line 85
    :cond_2
    return-void
.end method

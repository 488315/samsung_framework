.class public final Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;
.super Lcom/android/launcher3/icons/BitmapInfo;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final animInfo:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

.field public final boundsOffset:F

.field public final mFlattenedBackground:Landroid/graphics/Bitmap;

.field public final themeBackground:Landroid/graphics/Bitmap;

.field public final themeData:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;


# direct methods
.method public constructor <init>(Landroid/graphics/Bitmap;IFLcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;Landroid/graphics/Bitmap;Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;Landroid/graphics/Bitmap;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2}, Lcom/android/launcher3/icons/BitmapInfo;-><init>(Landroid/graphics/Bitmap;I)V

    .line 2
    .line 3
    .line 4
    const/high16 p1, 0x3f800000    # 1.0f

    .line 5
    .line 6
    sub-float/2addr p1, p3

    .line 7
    const/high16 p2, 0x40000000    # 2.0f

    .line 8
    .line 9
    div-float/2addr p1, p2

    .line 10
    const p2, 0x3d0f5c29    # 0.035f

    .line 11
    .line 12
    .line 13
    invoke-static {p2, p1}, Ljava/lang/Math;->max(FF)F

    .line 14
    .line 15
    .line 16
    move-result p1

    .line 17
    iput p1, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->boundsOffset:F

    .line 18
    .line 19
    iput-object p4, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->animInfo:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

    .line 20
    .line 21
    iput-object p5, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->mFlattenedBackground:Landroid/graphics/Bitmap;

    .line 22
    .line 23
    iput-object p6, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->themeData:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

    .line 24
    .line 25
    iput-object p7, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->themeBackground:Landroid/graphics/Bitmap;

    .line 26
    .line 27
    return-void
.end method


# virtual methods
.method public final clone()Lcom/android/launcher3/icons/BitmapInfo;
    .locals 9

    .line 2
    new-instance v8, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;

    iget-object v1, p0, Lcom/android/launcher3/icons/BitmapInfo;->icon:Landroid/graphics/Bitmap;

    iget v2, p0, Lcom/android/launcher3/icons/BitmapInfo;->color:I

    const/high16 v0, 0x40000000    # 2.0f

    iget v3, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->boundsOffset:F

    mul-float/2addr v3, v0

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float v3, v0, v3

    iget-object v4, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->animInfo:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

    iget-object v5, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->mFlattenedBackground:Landroid/graphics/Bitmap;

    iget-object v6, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->themeData:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

    iget-object v7, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->themeBackground:Landroid/graphics/Bitmap;

    move-object v0, v8

    invoke-direct/range {v0 .. v7}, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;-><init>(Landroid/graphics/Bitmap;IFLcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;Landroid/graphics/Bitmap;Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;Landroid/graphics/Bitmap;)V

    .line 3
    iget-object v0, p0, Lcom/android/launcher3/icons/BitmapInfo;->mMono:Landroid/graphics/Bitmap;

    iput-object v0, v8, Lcom/android/launcher3/icons/BitmapInfo;->mMono:Landroid/graphics/Bitmap;

    .line 4
    iget-object v0, p0, Lcom/android/launcher3/icons/BitmapInfo;->mWhiteShadowLayer:Landroid/graphics/Bitmap;

    iput-object v0, v8, Lcom/android/launcher3/icons/BitmapInfo;->mWhiteShadowLayer:Landroid/graphics/Bitmap;

    .line 5
    iget v0, p0, Lcom/android/launcher3/icons/BitmapInfo;->flags:I

    iput v0, v8, Lcom/android/launcher3/icons/BitmapInfo;->flags:I

    .line 6
    iget-object p0, p0, Lcom/android/launcher3/icons/BitmapInfo;->badgeInfo:Lcom/android/launcher3/icons/BitmapInfo;

    iput-object p0, v8, Lcom/android/launcher3/icons/BitmapInfo;->badgeInfo:Lcom/android/launcher3/icons/BitmapInfo;

    return-object v8
.end method

.method public final bridge synthetic clone()Ljava/lang/Object;
    .locals 0

    .line 1
    invoke-virtual {p0}, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->clone()Lcom/android/launcher3/icons/BitmapInfo;

    move-result-object p0

    return-object p0
.end method

.method public final newIcon(Landroid/content/Context;)Lcom/android/launcher3/icons/FastBitmapDrawable;
    .locals 9

    .line 1
    iget-object v6, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->mFlattenedBackground:Landroid/graphics/Bitmap;

    .line 2
    .line 3
    const/4 v3, -0x1

    .line 4
    const/4 v7, 0x0

    .line 5
    iget-object v5, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->animInfo:Lcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;

    .line 6
    .line 7
    if-nez v5, :cond_0

    .line 8
    .line 9
    invoke-super {p0, p1}, Lcom/android/launcher3/icons/BitmapInfo;->newIcon(Landroid/content/Context;)Lcom/android/launcher3/icons/FastBitmapDrawable;

    .line 10
    .line 11
    .line 12
    move-result-object p0

    .line 13
    return-object p0

    .line 14
    :cond_0
    new-instance v8, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockIconDrawable$ClockConstantState;

    .line 15
    .line 16
    iget-object v1, p0, Lcom/android/launcher3/icons/BitmapInfo;->icon:Landroid/graphics/Bitmap;

    .line 17
    .line 18
    iget v2, p0, Lcom/android/launcher3/icons/BitmapInfo;->color:I

    .line 19
    .line 20
    iget v4, p0, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockBitmapInfo;->boundsOffset:F

    .line 21
    .line 22
    move-object v0, v8

    .line 23
    invoke-direct/range {v0 .. v7}, Lcom/android/launcher3/icons/ClockDrawableWrapper$ClockIconDrawable$ClockConstantState;-><init>(Landroid/graphics/Bitmap;IIFLcom/android/launcher3/icons/ClockDrawableWrapper$AnimationInfo;Landroid/graphics/Bitmap;Landroid/graphics/ColorFilter;)V

    .line 24
    .line 25
    .line 26
    invoke-virtual {v8}, Lcom/android/launcher3/icons/FastBitmapDrawable$FastBitmapConstantState;->newDrawable()Lcom/android/launcher3/icons/FastBitmapDrawable;

    .line 27
    .line 28
    .line 29
    move-result-object v0

    .line 30
    invoke-virtual {p0, p1, v0}, Lcom/android/launcher3/icons/BitmapInfo;->applyFlags(Landroid/content/Context;Lcom/android/launcher3/icons/FastBitmapDrawable;)V

    .line 31
    .line 32
    .line 33
    return-object v0
.end method

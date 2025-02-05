.class public Lcom/google/android/material/divider/MaterialDivider;
.super Landroid/view/View;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public color:I

.field public final dividerDrawable:Lcom/google/android/material/shape/MaterialShapeDrawable;

.field public final insetEnd:I

.field public final insetStart:I

.field public final thickness:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    .line 1
    invoke-direct {p0, p1, v0}, Lcom/google/android/material/divider/MaterialDivider;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const v0, 0x7f0403dd

    .line 2
    invoke-direct {p0, p1, p2, v0}, Lcom/google/android/material/divider/MaterialDivider;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 8

    const v0, 0x7f1407c4

    .line 3
    invoke-static {p1, p2, p3, v0}, Lcom/google/android/material/theme/overlay/MaterialThemeOverlay;->wrap(Landroid/content/Context;Landroid/util/AttributeSet;II)Landroid/content/Context;

    move-result-object p1

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 4
    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p1

    .line 5
    new-instance v6, Lcom/google/android/material/shape/MaterialShapeDrawable;

    invoke-direct {v6}, Lcom/google/android/material/shape/MaterialShapeDrawable;-><init>()V

    iput-object v6, p0, Lcom/google/android/material/divider/MaterialDivider;->dividerDrawable:Lcom/google/android/material/shape/MaterialShapeDrawable;

    .line 6
    sget-object v2, Lcom/google/android/material/R$styleable;->MaterialDivider:[I

    const v4, 0x7f1407c4

    const/4 v7, 0x0

    new-array v5, v7, [I

    move-object v0, p1

    move-object v1, p2

    move v3, p3

    .line 7
    invoke-static/range {v0 .. v5}, Lcom/google/android/material/internal/ThemeEnforcement;->obtainStyledAttributes(Landroid/content/Context;Landroid/util/AttributeSet;[III[I)Landroid/content/res/TypedArray;

    move-result-object p2

    .line 8
    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object p3

    const v0, 0x7f0707f8

    invoke-virtual {p3, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p3

    const/4 v0, 0x3

    .line 9
    invoke-virtual {p2, v0, p3}, Landroid/content/res/TypedArray;->getDimensionPixelSize(II)I

    move-result p3

    iput p3, p0, Lcom/google/android/material/divider/MaterialDivider;->thickness:I

    const/4 p3, 0x2

    .line 10
    invoke-virtual {p2, p3, v7}, Landroid/content/res/TypedArray;->getDimensionPixelOffset(II)I

    move-result p3

    iput p3, p0, Lcom/google/android/material/divider/MaterialDivider;->insetStart:I

    const/4 p3, 0x1

    .line 11
    invoke-virtual {p2, p3, v7}, Landroid/content/res/TypedArray;->getDimensionPixelOffset(II)I

    move-result p3

    iput p3, p0, Lcom/google/android/material/divider/MaterialDivider;->insetEnd:I

    .line 12
    invoke-static {p1, p2, v7}, Lcom/google/android/material/resources/MaterialResources;->getColorStateList(Landroid/content/Context;Landroid/content/res/TypedArray;I)Landroid/content/res/ColorStateList;

    move-result-object p1

    .line 13
    invoke-virtual {p1}, Landroid/content/res/ColorStateList;->getDefaultColor()I

    move-result p1

    .line 14
    iget p3, p0, Lcom/google/android/material/divider/MaterialDivider;->color:I

    if-eq p3, p1, :cond_0

    .line 15
    iput p1, p0, Lcom/google/android/material/divider/MaterialDivider;->color:I

    .line 16
    invoke-static {p1}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    move-result-object p1

    invoke-virtual {v6, p1}, Lcom/google/android/material/shape/MaterialShapeDrawable;->setFillColor(Landroid/content/res/ColorStateList;)V

    .line 17
    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    .line 18
    :cond_0
    invoke-virtual {p2}, Landroid/content/res/TypedArray;->recycle()V

    return-void
.end method


# virtual methods
.method public final onDraw(Landroid/graphics/Canvas;)V
    .locals 6

    .line 1
    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    .line 2
    .line 3
    .line 4
    sget-object v0, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 5
    .line 6
    invoke-static {p0}, Landroidx/core/view/ViewCompat$Api17Impl;->getLayoutDirection(Landroid/view/View;)I

    .line 7
    .line 8
    .line 9
    move-result v0

    .line 10
    const/4 v1, 0x0

    .line 11
    const/4 v2, 0x1

    .line 12
    if-ne v0, v2, :cond_0

    .line 13
    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move v2, v1

    .line 16
    :goto_0
    if-eqz v2, :cond_1

    .line 17
    .line 18
    iget v0, p0, Lcom/google/android/material/divider/MaterialDivider;->insetEnd:I

    .line 19
    .line 20
    goto :goto_1

    .line 21
    :cond_1
    iget v0, p0, Lcom/google/android/material/divider/MaterialDivider;->insetStart:I

    .line 22
    .line 23
    :goto_1
    if-eqz v2, :cond_2

    .line 24
    .line 25
    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    .line 26
    .line 27
    .line 28
    move-result v2

    .line 29
    iget v3, p0, Lcom/google/android/material/divider/MaterialDivider;->insetStart:I

    .line 30
    .line 31
    goto :goto_2

    .line 32
    :cond_2
    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    .line 33
    .line 34
    .line 35
    move-result v2

    .line 36
    iget v3, p0, Lcom/google/android/material/divider/MaterialDivider;->insetEnd:I

    .line 37
    .line 38
    :goto_2
    sub-int/2addr v2, v3

    .line 39
    iget-object v3, p0, Lcom/google/android/material/divider/MaterialDivider;->dividerDrawable:Lcom/google/android/material/shape/MaterialShapeDrawable;

    .line 40
    .line 41
    invoke-virtual {p0}, Landroid/view/View;->getBottom()I

    .line 42
    .line 43
    .line 44
    move-result v4

    .line 45
    invoke-virtual {p0}, Landroid/view/View;->getTop()I

    .line 46
    .line 47
    .line 48
    move-result v5

    .line 49
    sub-int/2addr v4, v5

    .line 50
    invoke-virtual {v3, v0, v1, v2, v4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 51
    .line 52
    .line 53
    iget-object p0, p0, Lcom/google/android/material/divider/MaterialDivider;->dividerDrawable:Lcom/google/android/material/shape/MaterialShapeDrawable;

    .line 54
    .line 55
    invoke-virtual {p0, p1}, Lcom/google/android/material/shape/MaterialShapeDrawable;->draw(Landroid/graphics/Canvas;)V

    .line 56
    .line 57
    .line 58
    return-void
.end method

.method public final onMeasure(II)V
    .locals 1

    .line 1
    invoke-super {p0, p1, p2}, Landroid/view/View;->onMeasure(II)V

    .line 2
    .line 3
    .line 4
    invoke-static {p2}, Landroid/view/View$MeasureSpec;->getMode(I)I

    .line 5
    .line 6
    .line 7
    move-result p1

    .line 8
    invoke-virtual {p0}, Landroid/view/View;->getMeasuredHeight()I

    .line 9
    .line 10
    .line 11
    move-result p2

    .line 12
    const/high16 v0, -0x80000000

    .line 13
    .line 14
    if-eq p1, v0, :cond_0

    .line 15
    .line 16
    if-nez p1, :cond_2

    .line 17
    .line 18
    :cond_0
    iget p1, p0, Lcom/google/android/material/divider/MaterialDivider;->thickness:I

    .line 19
    .line 20
    if-lez p1, :cond_1

    .line 21
    .line 22
    if-eq p2, p1, :cond_1

    .line 23
    .line 24
    move p2, p1

    .line 25
    :cond_1
    invoke-virtual {p0}, Landroid/view/View;->getMeasuredWidth()I

    .line 26
    .line 27
    .line 28
    move-result p1

    .line 29
    invoke-virtual {p0, p1, p2}, Landroid/view/View;->setMeasuredDimension(II)V

    .line 30
    .line 31
    .line 32
    :cond_2
    return-void
.end method

.class public final Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;
.super Landroid/graphics/drawable/Drawable;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final baseIcon:Landroid/graphics/drawable/Drawable;

.field public final dp:F

.field public limit:J

.field public final paint:Landroid/graphics/Paint;

.field public rss:J


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 2

    .line 1
    invoke-direct {p0}, Landroid/graphics/drawable/Drawable;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Landroid/graphics/Paint;

    .line 5
    .line 6
    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->paint:Landroid/graphics/Paint;

    .line 10
    .line 11
    const v1, 0x7f08097c

    .line 12
    .line 13
    .line 14
    invoke-virtual {p1, v1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 15
    .line 16
    .line 17
    move-result-object v1

    .line 18
    invoke-virtual {v1}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    .line 19
    .line 20
    .line 21
    move-result-object v1

    .line 22
    iput-object v1, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 23
    .line 24
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 25
    .line 26
    .line 27
    move-result-object p1

    .line 28
    invoke-virtual {p1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 29
    .line 30
    .line 31
    move-result-object p1

    .line 32
    iget p1, p1, Landroid/util/DisplayMetrics;->density:F

    .line 33
    .line 34
    iput p1, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->dp:F

    .line 35
    .line 36
    const/4 p0, -0x1

    .line 37
    invoke-virtual {v0, p0}, Landroid/graphics/Paint;->setColor(I)V

    .line 38
    .line 39
    .line 40
    return-void
.end method


# virtual methods
.method public final draw(Landroid/graphics/Canvas;)V
    .locals 13

    .line 1
    iget-object v0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 4
    .line 5
    .line 6
    iget-wide v0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->limit:J

    .line 7
    .line 8
    const-wide/16 v2, 0x0

    .line 9
    .line 10
    cmp-long v4, v0, v2

    .line 11
    .line 12
    if-lez v4, :cond_0

    .line 13
    .line 14
    iget-wide v4, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->rss:J

    .line 15
    .line 16
    cmp-long v2, v4, v2

    .line 17
    .line 18
    if-lez v2, :cond_0

    .line 19
    .line 20
    long-to-float v2, v4

    .line 21
    long-to-float v0, v0

    .line 22
    div-float/2addr v2, v0

    .line 23
    const/high16 v0, 0x3f800000    # 1.0f

    .line 24
    .line 25
    invoke-static {v0, v2}, Ljava/lang/Math;->min(FF)F

    .line 26
    .line 27
    .line 28
    move-result v1

    .line 29
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getBounds()Landroid/graphics/Rect;

    .line 30
    .line 31
    .line 32
    move-result-object v2

    .line 33
    iget v3, v2, Landroid/graphics/Rect;->left:I

    .line 34
    .line 35
    int-to-float v3, v3

    .line 36
    iget v4, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->dp:F

    .line 37
    .line 38
    const/high16 v5, 0x41000000    # 8.0f

    .line 39
    .line 40
    mul-float v6, v4, v5

    .line 41
    .line 42
    add-float/2addr v6, v3

    .line 43
    iget v2, v2, Landroid/graphics/Rect;->top:I

    .line 44
    .line 45
    int-to-float v2, v2

    .line 46
    const/high16 v3, 0x40a00000    # 5.0f

    .line 47
    .line 48
    mul-float/2addr v4, v3

    .line 49
    add-float/2addr v4, v2

    .line 50
    invoke-virtual {p1, v6, v4}, Landroid/graphics/Canvas;->translate(FF)V

    .line 51
    .line 52
    .line 53
    const/4 v8, 0x0

    .line 54
    iget v2, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->dp:F

    .line 55
    .line 56
    const/high16 v3, 0x41600000    # 14.0f

    .line 57
    .line 58
    mul-float v4, v2, v3

    .line 59
    .line 60
    sub-float v1, v0, v1

    .line 61
    .line 62
    mul-float v9, v1, v4

    .line 63
    .line 64
    mul-float/2addr v5, v2

    .line 65
    add-float v10, v5, v0

    .line 66
    .line 67
    mul-float/2addr v2, v3

    .line 68
    add-float v11, v2, v0

    .line 69
    .line 70
    iget-object v12, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->paint:Landroid/graphics/Paint;

    .line 71
    .line 72
    move-object v7, p1

    .line 73
    invoke-virtual/range {v7 .. v12}, Landroid/graphics/Canvas;->drawRect(FFFFLandroid/graphics/Paint;)V

    .line 74
    .line 75
    .line 76
    :cond_0
    return-void
.end method

.method public final getIntrinsicHeight()I
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    return p0
.end method

.method public final getIntrinsicWidth()I
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    return p0
.end method

.method public final getOpacity()I
    .locals 0

    .line 1
    const/4 p0, -0x3

    .line 2
    return p0
.end method

.method public final setAlpha(I)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final setBounds(IIII)V
    .locals 0

    .line 1
    invoke-super {p0, p1, p2, p3, p4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 5
    .line 6
    invoke-virtual {p0, p1, p2, p3, p4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 7
    .line 8
    .line 9
    return-void
.end method

.method public final setColorFilter(Landroid/graphics/ColorFilter;)V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->setColorFilter(Landroid/graphics/ColorFilter;)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->paint:Landroid/graphics/Paint;

    .line 7
    .line 8
    invoke-virtual {p0, p1}, Landroid/graphics/Paint;->setColorFilter(Landroid/graphics/ColorFilter;)Landroid/graphics/ColorFilter;

    .line 9
    .line 10
    .line 11
    return-void
.end method

.method public final setTint(I)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Landroid/graphics/drawable/Drawable;->setTint(I)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 5
    .line 6
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->setTint(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method

.method public final setTintList(Landroid/content/res/ColorStateList;)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Landroid/graphics/drawable/Drawable;->setTintList(Landroid/content/res/ColorStateList;)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 5
    .line 6
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->setTintList(Landroid/content/res/ColorStateList;)V

    .line 7
    .line 8
    .line 9
    return-void
.end method

.method public final setTintMode(Landroid/graphics/PorterDuff$Mode;)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Landroid/graphics/drawable/Drawable;->setTintMode(Landroid/graphics/PorterDuff$Mode;)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$MemoryIconDrawable;->baseIcon:Landroid/graphics/drawable/Drawable;

    .line 5
    .line 6
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->setTintMode(Landroid/graphics/PorterDuff$Mode;)V

    .line 7
    .line 8
    .line 9
    return-void
.end method

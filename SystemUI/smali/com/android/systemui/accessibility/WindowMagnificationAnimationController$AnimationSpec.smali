.class public final Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mCenterX:F

.field public mCenterY:F

.field public mScale:F


# direct methods
.method private constructor <init>()V
    .locals 1

    .line 2
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/high16 v0, 0x7fc00000    # Float.NaN

    .line 3
    iput v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 4
    iput v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 5
    iput v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    return-void
.end method

.method public synthetic constructor <init>(I)V
    .locals 0

    .line 1
    invoke-direct {p0}, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;-><init>()V

    return-void
.end method


# virtual methods
.method public final equals(Ljava/lang/Object;)Z
    .locals 4

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p0, p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    const/4 v1, 0x0

    .line 6
    if-eqz p1, :cond_3

    .line 7
    .line 8
    const-class v2, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;

    .line 9
    .line 10
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 11
    .line 12
    .line 13
    move-result-object v3

    .line 14
    if-eq v2, v3, :cond_1

    .line 15
    .line 16
    goto :goto_1

    .line 17
    :cond_1
    check-cast p1, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;

    .line 18
    .line 19
    iget v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 20
    .line 21
    iget v3, p1, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 22
    .line 23
    cmpl-float v2, v2, v3

    .line 24
    .line 25
    if-nez v2, :cond_2

    .line 26
    .line 27
    iget v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 28
    .line 29
    iget v3, p1, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 30
    .line 31
    cmpl-float v2, v2, v3

    .line 32
    .line 33
    if-nez v2, :cond_2

    .line 34
    .line 35
    iget p0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    .line 36
    .line 37
    iget p1, p1, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    .line 38
    .line 39
    cmpl-float p0, p0, p1

    .line 40
    .line 41
    if-nez p0, :cond_2

    .line 42
    .line 43
    goto :goto_0

    .line 44
    :cond_2
    move v0, v1

    .line 45
    :goto_0
    return v0

    .line 46
    :cond_3
    :goto_1
    return v1
.end method

.method public final hashCode()I
    .locals 5

    .line 1
    iget v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    cmpl-float v2, v0, v1

    .line 5
    .line 6
    const/4 v3, 0x0

    .line 7
    if-eqz v2, :cond_0

    .line 8
    .line 9
    invoke-static {v0}, Ljava/lang/Float;->floatToIntBits(F)I

    .line 10
    .line 11
    .line 12
    move-result v0

    .line 13
    goto :goto_0

    .line 14
    :cond_0
    move v0, v3

    .line 15
    :goto_0
    mul-int/lit8 v0, v0, 0x1f

    .line 16
    .line 17
    iget v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 18
    .line 19
    cmpl-float v4, v2, v1

    .line 20
    .line 21
    if-eqz v4, :cond_1

    .line 22
    .line 23
    invoke-static {v2}, Ljava/lang/Float;->floatToIntBits(F)I

    .line 24
    .line 25
    .line 26
    move-result v2

    .line 27
    goto :goto_1

    .line 28
    :cond_1
    move v2, v3

    .line 29
    :goto_1
    add-int/2addr v0, v2

    .line 30
    mul-int/lit8 v0, v0, 0x1f

    .line 31
    .line 32
    iget p0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    .line 33
    .line 34
    cmpl-float v1, p0, v1

    .line 35
    .line 36
    if-eqz v1, :cond_2

    .line 37
    .line 38
    invoke-static {p0}, Ljava/lang/Float;->floatToIntBits(F)I

    .line 39
    .line 40
    .line 41
    move-result v3

    .line 42
    :cond_2
    add-int/2addr v0, v3

    .line 43
    return v0
.end method

.method public final set(FFF)V
    .locals 0

    .line 1
    iput p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 2
    .line 3
    iput p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 4
    .line 5
    iput p3, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    .line 6
    .line 7
    return-void
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "AnimationSpec{mScale="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mScale:F

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", mCenterX="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget v1, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterX:F

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", mCenterY="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget p0, p0, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec;->mCenterY:F

    .line 29
    .line 30
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const/16 p0, 0x7d

    .line 34
    .line 35
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 39
    .line 40
    .line 41
    move-result-object p0

    .line 42
    return-object p0
.end method

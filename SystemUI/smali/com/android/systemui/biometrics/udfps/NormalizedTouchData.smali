.class public final Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final gestureStart:J

.field public final major:F

.field public final minor:F

.field public final orientation:F

.field public final pointerId:I

.field public final time:J

.field public final x:F

.field public final y:F


# direct methods
.method public constructor <init>()V
    .locals 13

    .line 1
    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const-wide/16 v7, 0x0

    const-wide/16 v9, 0x0

    const/16 v11, 0xff

    const/4 v12, 0x0

    move-object v0, p0

    invoke-direct/range {v0 .. v12}, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;-><init>(IFFFFFJJILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-void
.end method

.method public constructor <init>(IFFFFFJJ)V
    .locals 0

    .line 2
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 3
    iput p1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->pointerId:I

    .line 4
    iput p2, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 5
    iput p3, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 6
    iput p4, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->minor:F

    .line 7
    iput p5, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->major:F

    .line 8
    iput p6, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->orientation:F

    .line 9
    iput-wide p7, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->time:J

    .line 10
    iput-wide p9, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->gestureStart:J

    return-void
.end method

.method public synthetic constructor <init>(IFFFFFJJILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 12

    move/from16 v0, p11

    and-int/lit8 v1, v0, 0x1

    if-eqz v1, :cond_0

    const/4 v1, -0x1

    goto :goto_0

    :cond_0
    move v1, p1

    :goto_0
    and-int/lit8 v2, v0, 0x2

    const/4 v3, 0x0

    if-eqz v2, :cond_1

    move v2, v3

    goto :goto_1

    :cond_1
    move v2, p2

    :goto_1
    and-int/lit8 v4, v0, 0x4

    if-eqz v4, :cond_2

    move v4, v3

    goto :goto_2

    :cond_2
    move v4, p3

    :goto_2
    and-int/lit8 v5, v0, 0x8

    if-eqz v5, :cond_3

    move v5, v3

    goto :goto_3

    :cond_3
    move/from16 v5, p4

    :goto_3
    and-int/lit8 v6, v0, 0x10

    if-eqz v6, :cond_4

    move v6, v3

    goto :goto_4

    :cond_4
    move/from16 v6, p5

    :goto_4
    and-int/lit8 v7, v0, 0x20

    if-eqz v7, :cond_5

    goto :goto_5

    :cond_5
    move/from16 v3, p6

    :goto_5
    and-int/lit8 v7, v0, 0x40

    const-wide/16 v8, 0x0

    if-eqz v7, :cond_6

    move-wide v10, v8

    goto :goto_6

    :cond_6
    move-wide/from16 v10, p7

    :goto_6
    and-int/lit16 v0, v0, 0x80

    if-eqz v0, :cond_7

    goto :goto_7

    :cond_7
    move-wide/from16 v8, p9

    :goto_7
    move p1, v1

    move p2, v2

    move p3, v4

    move/from16 p4, v5

    move/from16 p5, v6

    move/from16 p6, v3

    move-wide/from16 p7, v10

    move-wide/from16 p9, v8

    .line 11
    invoke-direct/range {p0 .. p10}, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;-><init>(IFFFFFJJ)V

    return-void
.end method


# virtual methods
.method public final equals(Ljava/lang/Object;)Z
    .locals 7

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p0, p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    instance-of v1, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    if-nez v1, :cond_1

    .line 9
    .line 10
    return v2

    .line 11
    :cond_1
    check-cast p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;

    .line 12
    .line 13
    iget v1, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->pointerId:I

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->pointerId:I

    .line 16
    .line 17
    if-eq v3, v1, :cond_2

    .line 18
    .line 19
    return v2

    .line 20
    :cond_2
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 21
    .line 22
    iget v3, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 23
    .line 24
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 25
    .line 26
    .line 27
    move-result v1

    .line 28
    if-eqz v1, :cond_3

    .line 29
    .line 30
    return v2

    .line 31
    :cond_3
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 32
    .line 33
    iget v3, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 34
    .line 35
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 36
    .line 37
    .line 38
    move-result v1

    .line 39
    if-eqz v1, :cond_4

    .line 40
    .line 41
    return v2

    .line 42
    :cond_4
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->minor:F

    .line 43
    .line 44
    iget v3, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->minor:F

    .line 45
    .line 46
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 47
    .line 48
    .line 49
    move-result v1

    .line 50
    if-eqz v1, :cond_5

    .line 51
    .line 52
    return v2

    .line 53
    :cond_5
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->major:F

    .line 54
    .line 55
    iget v3, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->major:F

    .line 56
    .line 57
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 58
    .line 59
    .line 60
    move-result v1

    .line 61
    if-eqz v1, :cond_6

    .line 62
    .line 63
    return v2

    .line 64
    :cond_6
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->orientation:F

    .line 65
    .line 66
    iget v3, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->orientation:F

    .line 67
    .line 68
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 69
    .line 70
    .line 71
    move-result v1

    .line 72
    if-eqz v1, :cond_7

    .line 73
    .line 74
    return v2

    .line 75
    :cond_7
    iget-wide v3, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->time:J

    .line 76
    .line 77
    iget-wide v5, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->time:J

    .line 78
    .line 79
    cmp-long v1, v3, v5

    .line 80
    .line 81
    if-eqz v1, :cond_8

    .line 82
    .line 83
    return v2

    .line 84
    :cond_8
    iget-wide v3, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->gestureStart:J

    .line 85
    .line 86
    iget-wide p0, p1, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->gestureStart:J

    .line 87
    .line 88
    cmp-long p0, v3, p0

    .line 89
    .line 90
    if-eqz p0, :cond_9

    .line 91
    .line 92
    return v2

    .line 93
    :cond_9
    return v0
.end method

.method public final hashCode()I
    .locals 4

    .line 1
    iget v0, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->pointerId:I

    .line 2
    .line 3
    invoke-static {v0}, Ljava/lang/Integer;->hashCode(I)I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    mul-int/lit8 v0, v0, 0x1f

    .line 8
    .line 9
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 10
    .line 11
    const/16 v2, 0x1f

    .line 12
    .line 13
    invoke-static {v1, v0, v2}, Lcom/android/settingslib/udfps/UdfpsOverlayParams$$ExternalSyntheticOutline0;->m(FII)I

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 18
    .line 19
    invoke-static {v1, v0, v2}, Lcom/android/settingslib/udfps/UdfpsOverlayParams$$ExternalSyntheticOutline0;->m(FII)I

    .line 20
    .line 21
    .line 22
    move-result v0

    .line 23
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->minor:F

    .line 24
    .line 25
    invoke-static {v1, v0, v2}, Lcom/android/settingslib/udfps/UdfpsOverlayParams$$ExternalSyntheticOutline0;->m(FII)I

    .line 26
    .line 27
    .line 28
    move-result v0

    .line 29
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->major:F

    .line 30
    .line 31
    invoke-static {v1, v0, v2}, Lcom/android/settingslib/udfps/UdfpsOverlayParams$$ExternalSyntheticOutline0;->m(FII)I

    .line 32
    .line 33
    .line 34
    move-result v0

    .line 35
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->orientation:F

    .line 36
    .line 37
    invoke-static {v1, v0, v2}, Lcom/android/settingslib/udfps/UdfpsOverlayParams$$ExternalSyntheticOutline0;->m(FII)I

    .line 38
    .line 39
    .line 40
    move-result v0

    .line 41
    iget-wide v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->time:J

    .line 42
    .line 43
    const/16 v3, 0x1f

    .line 44
    .line 45
    invoke-static {v1, v2, v0, v3}, Lcom/android/app/motiontool/TraceMetadata$$ExternalSyntheticOutline0;->m(JII)I

    .line 46
    .line 47
    .line 48
    move-result v0

    .line 49
    iget-wide v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->gestureStart:J

    .line 50
    .line 51
    invoke-static {v1, v2}, Ljava/lang/Long;->hashCode(J)I

    .line 52
    .line 53
    .line 54
    move-result p0

    .line 55
    add-int/2addr p0, v0

    .line 56
    return p0
.end method

.method public final isWithinBounds(Landroid/graphics/Rect;)Z
    .locals 2

    .line 1
    iget v0, p1, Landroid/graphics/Rect;->left:I

    .line 2
    .line 3
    int-to-float v0, v0

    .line 4
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 5
    .line 6
    cmpg-float v0, v0, v1

    .line 7
    .line 8
    if-gtz v0, :cond_0

    .line 9
    .line 10
    iget v0, p1, Landroid/graphics/Rect;->right:I

    .line 11
    .line 12
    int-to-float v0, v0

    .line 13
    cmpl-float v0, v0, v1

    .line 14
    .line 15
    if-ltz v0, :cond_0

    .line 16
    .line 17
    iget v0, p1, Landroid/graphics/Rect;->top:I

    .line 18
    .line 19
    int-to-float v0, v0

    .line 20
    iget p0, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 21
    .line 22
    cmpg-float v0, v0, p0

    .line 23
    .line 24
    if-gtz v0, :cond_0

    .line 25
    .line 26
    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    .line 27
    .line 28
    int-to-float p1, p1

    .line 29
    cmpl-float p0, p1, p0

    .line 30
    .line 31
    if-ltz p0, :cond_0

    .line 32
    .line 33
    const/4 p0, 0x1

    .line 34
    goto :goto_0

    .line 35
    :cond_0
    const/4 p0, 0x0

    .line 36
    :goto_0
    return p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 3

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "NormalizedTouchData(pointerId="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->pointerId:I

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", x="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->x:F

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", y="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->y:F

    .line 29
    .line 30
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string v1, ", minor="

    .line 34
    .line 35
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->minor:F

    .line 39
    .line 40
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    const-string v1, ", major="

    .line 44
    .line 45
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 46
    .line 47
    .line 48
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->major:F

    .line 49
    .line 50
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 51
    .line 52
    .line 53
    const-string v1, ", orientation="

    .line 54
    .line 55
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 56
    .line 57
    .line 58
    iget v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->orientation:F

    .line 59
    .line 60
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 61
    .line 62
    .line 63
    const-string v1, ", time="

    .line 64
    .line 65
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 66
    .line 67
    .line 68
    iget-wide v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->time:J

    .line 69
    .line 70
    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    .line 71
    .line 72
    .line 73
    const-string v1, ", gestureStart="

    .line 74
    .line 75
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 76
    .line 77
    .line 78
    iget-wide v1, p0, Lcom/android/systemui/biometrics/udfps/NormalizedTouchData;->gestureStart:J

    .line 79
    .line 80
    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    .line 81
    .line 82
    .line 83
    const-string p0, ")"

    .line 84
    .line 85
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 86
    .line 87
    .line 88
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 89
    .line 90
    .line 91
    move-result-object p0

    .line 92
    return-object p0
.end method

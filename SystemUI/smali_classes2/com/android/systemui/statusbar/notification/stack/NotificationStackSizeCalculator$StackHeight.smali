.class public final Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final notifsHeight:F

.field public final notifsHeightSavingSpace:F

.field public final shelfHeightWithSpaceBefore:F


# direct methods
.method public constructor <init>(FFF)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeight:F

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeightSavingSpace:F

    .line 7
    .line 8
    iput p3, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->shelfHeightWithSpaceBefore:F

    .line 9
    .line 10
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
    instance-of v1, p1, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;

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
    check-cast p1, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;

    .line 12
    .line 13
    iget v1, p1, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeight:F

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeight:F

    .line 16
    .line 17
    invoke-static {v3, v1}, Ljava/lang/Float;->compare(FF)I

    .line 18
    .line 19
    .line 20
    move-result v1

    .line 21
    if-eqz v1, :cond_2

    .line 22
    .line 23
    return v2

    .line 24
    :cond_2
    iget v1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeightSavingSpace:F

    .line 25
    .line 26
    iget v3, p1, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeightSavingSpace:F

    .line 27
    .line 28
    invoke-static {v1, v3}, Ljava/lang/Float;->compare(FF)I

    .line 29
    .line 30
    .line 31
    move-result v1

    .line 32
    if-eqz v1, :cond_3

    .line 33
    .line 34
    return v2

    .line 35
    :cond_3
    iget p0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->shelfHeightWithSpaceBefore:F

    .line 36
    .line 37
    iget p1, p1, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->shelfHeightWithSpaceBefore:F

    .line 38
    .line 39
    invoke-static {p0, p1}, Ljava/lang/Float;->compare(FF)I

    .line 40
    .line 41
    .line 42
    move-result p0

    .line 43
    if-eqz p0, :cond_4

    .line 44
    .line 45
    return v2

    .line 46
    :cond_4
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeight:F

    .line 2
    .line 3
    invoke-static {v0}, Ljava/lang/Float;->hashCode(F)I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    mul-int/lit8 v0, v0, 0x1f

    .line 8
    .line 9
    iget v1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeightSavingSpace:F

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
    iget p0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->shelfHeightWithSpaceBefore:F

    .line 18
    .line 19
    invoke-static {p0}, Ljava/lang/Float;->hashCode(F)I

    .line 20
    .line 21
    .line 22
    move-result p0

    .line 23
    add-int/2addr p0, v0

    .line 24
    return p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "StackHeight(notifsHeight="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeight:F

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", notifsHeightSavingSpace="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget v1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->notifsHeightSavingSpace:F

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", shelfHeightWithSpaceBefore="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget p0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackSizeCalculator$StackHeight;->shelfHeightWithSpaceBefore:F

    .line 29
    .line 30
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string p0, ")"

    .line 34
    .line 35
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

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
.class public final Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;
.super Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final distanceMoved:F

.field public final gestureDuration:J


# direct methods
.method public constructor <init>(FJ)V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-direct {p0, v0}, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 3
    .line 4
    .line 5
    iput p1, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->distanceMoved:F

    .line 6
    .line 7
    iput-wide p2, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->gestureDuration:J

    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final equals(Ljava/lang/Object;)Z
    .locals 5

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p0, p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    instance-of v1, p1, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;

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
    check-cast p1, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;

    .line 12
    .line 13
    iget v1, p1, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->distanceMoved:F

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->distanceMoved:F

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
    iget-wide v3, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->gestureDuration:J

    .line 25
    .line 26
    iget-wide p0, p1, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->gestureDuration:J

    .line 27
    .line 28
    cmp-long p0, v3, p0

    .line 29
    .line 30
    if-eqz p0, :cond_3

    .line 31
    .line 32
    return v2

    .line 33
    :cond_3
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->distanceMoved:F

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
    iget-wide v1, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->gestureDuration:J

    .line 10
    .line 11
    invoke-static {v1, v2}, Ljava/lang/Long;->hashCode(J)I

    .line 12
    .line 13
    .line 14
    move-result p0

    .line 15
    add-int/2addr p0, v0

    .line 16
    return p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 3

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "Up(distanceMoved="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->distanceMoved:F

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", gestureDuration="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-wide v1, p0, Lcom/android/systemui/common/ui/view/LongPressHandlingViewInteractionHandler$MotionEventModel$Up;->gestureDuration:J

    .line 19
    .line 20
    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string p0, ")"

    .line 24
    .line 25
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 29
    .line 30
    .line 31
    move-result-object p0

    .line 32
    return-object p0
.end method
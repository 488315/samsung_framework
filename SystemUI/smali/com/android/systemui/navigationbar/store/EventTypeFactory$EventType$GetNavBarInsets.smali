.class public final Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType;


# instance fields
.field public final insetHeight:I

.field public final insetWidth:I

.field public final rotation:I


# direct methods
.method public constructor <init>(III)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetHeight:I

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetWidth:I

    .line 7
    .line 8
    iput p3, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->rotation:I

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
    instance-of v1, p1, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;

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
    check-cast p1, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;

    .line 12
    .line 13
    iget v1, p1, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetHeight:I

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetHeight:I

    .line 16
    .line 17
    if-eq v3, v1, :cond_2

    .line 18
    .line 19
    return v2

    .line 20
    :cond_2
    iget v1, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetWidth:I

    .line 21
    .line 22
    iget v3, p1, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetWidth:I

    .line 23
    .line 24
    if-eq v1, v3, :cond_3

    .line 25
    .line 26
    return v2

    .line 27
    :cond_3
    iget p0, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->rotation:I

    .line 28
    .line 29
    iget p1, p1, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->rotation:I

    .line 30
    .line 31
    if-eq p0, p1, :cond_4

    .line 32
    .line 33
    return v2

    .line 34
    :cond_4
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetHeight:I

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
    iget v1, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetWidth:I

    .line 10
    .line 11
    const/16 v2, 0x1f

    .line 12
    .line 13
    invoke-static {v1, v0, v2}, Landroidx/picker/model/viewdata/AppInfoViewData$$ExternalSyntheticOutline0;->m(III)I

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    iget p0, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->rotation:I

    .line 18
    .line 19
    invoke-static {p0}, Ljava/lang/Integer;->hashCode(I)I

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
    const-string v1, "GetNavBarInsets(insetHeight="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetHeight:I

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", insetWidth="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget v1, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->insetWidth:I

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", rotation="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget p0, p0, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$GetNavBarInsets;->rotation:I

    .line 29
    .line 30
    const-string v1, ")"

    .line 31
    .line 32
    invoke-static {v0, p0, v1}, Landroidx/constraintlayout/core/widgets/ConstraintWidget$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;

    .line 33
    .line 34
    .line 35
    move-result-object p0

    .line 36
    return-object p0
.end method

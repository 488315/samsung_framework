.class public final Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;
.super Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final currentUserImage:Landroid/graphics/drawable/Drawable;

.field public final currentUserName:Ljava/lang/String;

.field public final isGuestUser:Z


# direct methods
.method public constructor <init>(Ljava/lang/String;Landroid/graphics/drawable/Drawable;Z)V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-direct {p0, v0}, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 3
    .line 4
    .line 5
    iput-object p1, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserName:Ljava/lang/String;

    .line 6
    .line 7
    iput-object p2, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserImage:Landroid/graphics/drawable/Drawable;

    .line 8
    .line 9
    iput-boolean p3, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->isGuestUser:Z

    .line 10
    .line 11
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
    instance-of v1, p1, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;

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
    check-cast p1, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;

    .line 12
    .line 13
    iget-object v1, p1, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserName:Ljava/lang/String;

    .line 14
    .line 15
    iget-object v3, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserName:Ljava/lang/String;

    .line 16
    .line 17
    invoke-static {v3, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 18
    .line 19
    .line 20
    move-result v1

    .line 21
    if-nez v1, :cond_2

    .line 22
    .line 23
    return v2

    .line 24
    :cond_2
    iget-object v1, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserImage:Landroid/graphics/drawable/Drawable;

    .line 25
    .line 26
    iget-object v3, p1, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserImage:Landroid/graphics/drawable/Drawable;

    .line 27
    .line 28
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 29
    .line 30
    .line 31
    move-result v1

    .line 32
    if-nez v1, :cond_3

    .line 33
    .line 34
    return v2

    .line 35
    :cond_3
    iget-boolean p0, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->isGuestUser:Z

    .line 36
    .line 37
    iget-boolean p1, p1, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->isGuestUser:Z

    .line 38
    .line 39
    if-eq p0, p1, :cond_4

    .line 40
    .line 41
    return v2

    .line 42
    :cond_4
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-object v1, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserName:Ljava/lang/String;

    .line 3
    .line 4
    if-nez v1, :cond_0

    .line 5
    .line 6
    move v1, v0

    .line 7
    goto :goto_0

    .line 8
    :cond_0
    invoke-virtual {v1}, Ljava/lang/String;->hashCode()I

    .line 9
    .line 10
    .line 11
    move-result v1

    .line 12
    :goto_0
    mul-int/lit8 v1, v1, 0x1f

    .line 13
    .line 14
    iget-object v2, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserImage:Landroid/graphics/drawable/Drawable;

    .line 15
    .line 16
    if-nez v2, :cond_1

    .line 17
    .line 18
    goto :goto_1

    .line 19
    :cond_1
    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->hashCode()I

    .line 20
    .line 21
    .line 22
    move-result v0

    .line 23
    :goto_1
    add-int/2addr v1, v0

    .line 24
    mul-int/lit8 v1, v1, 0x1f

    .line 25
    .line 26
    iget-boolean p0, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->isGuestUser:Z

    .line 27
    .line 28
    if-eqz p0, :cond_2

    .line 29
    .line 30
    const/4 p0, 0x1

    .line 31
    :cond_2
    add-int/2addr v1, p0

    .line 32
    return v1
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "Enabled(currentUserName="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserName:Ljava/lang/String;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", currentUserImage="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-object v1, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->currentUserImage:Landroid/graphics/drawable/Drawable;

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", isGuestUser="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget-boolean p0, p0, Lcom/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled;->isGuestUser:Z

    .line 29
    .line 30
    const-string v1, ")"

    .line 31
    .line 32
    invoke-static {v0, p0, v1}, Landroidx/appcompat/app/AppCompatDelegateImpl$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ZLjava/lang/String;)Ljava/lang/String;

    .line 33
    .line 34
    .line 35
    move-result-object p0

    .line 36
    return-object p0
.end method

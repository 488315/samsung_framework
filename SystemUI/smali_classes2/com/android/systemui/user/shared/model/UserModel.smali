.class public final Lcom/android/systemui/user/shared/model/UserModel;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final id:I

.field public final image:Landroid/graphics/drawable/Drawable;

.field public final isGuest:Z

.field public final isSelectable:Z

.field public final isSelected:Z

.field public final name:Lcom/android/systemui/common/shared/model/Text;


# direct methods
.method public constructor <init>(ILcom/android/systemui/common/shared/model/Text;Landroid/graphics/drawable/Drawable;ZZZ)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lcom/android/systemui/user/shared/model/UserModel;->id:I

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/user/shared/model/UserModel;->name:Lcom/android/systemui/common/shared/model/Text;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/user/shared/model/UserModel;->image:Landroid/graphics/drawable/Drawable;

    .line 9
    .line 10
    iput-boolean p4, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelected:Z

    .line 11
    .line 12
    iput-boolean p5, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelectable:Z

    .line 13
    .line 14
    iput-boolean p6, p0, Lcom/android/systemui/user/shared/model/UserModel;->isGuest:Z

    .line 15
    .line 16
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
    instance-of v1, p1, Lcom/android/systemui/user/shared/model/UserModel;

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
    check-cast p1, Lcom/android/systemui/user/shared/model/UserModel;

    .line 12
    .line 13
    iget v1, p1, Lcom/android/systemui/user/shared/model/UserModel;->id:I

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/user/shared/model/UserModel;->id:I

    .line 16
    .line 17
    if-eq v3, v1, :cond_2

    .line 18
    .line 19
    return v2

    .line 20
    :cond_2
    iget-object v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->name:Lcom/android/systemui/common/shared/model/Text;

    .line 21
    .line 22
    iget-object v3, p1, Lcom/android/systemui/user/shared/model/UserModel;->name:Lcom/android/systemui/common/shared/model/Text;

    .line 23
    .line 24
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 25
    .line 26
    .line 27
    move-result v1

    .line 28
    if-nez v1, :cond_3

    .line 29
    .line 30
    return v2

    .line 31
    :cond_3
    iget-object v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->image:Landroid/graphics/drawable/Drawable;

    .line 32
    .line 33
    iget-object v3, p1, Lcom/android/systemui/user/shared/model/UserModel;->image:Landroid/graphics/drawable/Drawable;

    .line 34
    .line 35
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 36
    .line 37
    .line 38
    move-result v1

    .line 39
    if-nez v1, :cond_4

    .line 40
    .line 41
    return v2

    .line 42
    :cond_4
    iget-boolean v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelected:Z

    .line 43
    .line 44
    iget-boolean v3, p1, Lcom/android/systemui/user/shared/model/UserModel;->isSelected:Z

    .line 45
    .line 46
    if-eq v1, v3, :cond_5

    .line 47
    .line 48
    return v2

    .line 49
    :cond_5
    iget-boolean v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelectable:Z

    .line 50
    .line 51
    iget-boolean v3, p1, Lcom/android/systemui/user/shared/model/UserModel;->isSelectable:Z

    .line 52
    .line 53
    if-eq v1, v3, :cond_6

    .line 54
    .line 55
    return v2

    .line 56
    :cond_6
    iget-boolean p0, p0, Lcom/android/systemui/user/shared/model/UserModel;->isGuest:Z

    .line 57
    .line 58
    iget-boolean p1, p1, Lcom/android/systemui/user/shared/model/UserModel;->isGuest:Z

    .line 59
    .line 60
    if-eq p0, p1, :cond_7

    .line 61
    .line 62
    return v2

    .line 63
    :cond_7
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/systemui/user/shared/model/UserModel;->id:I

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
    iget-object v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->name:Lcom/android/systemui/common/shared/model/Text;

    .line 10
    .line 11
    invoke-virtual {v1}, Ljava/lang/Object;->hashCode()I

    .line 12
    .line 13
    .line 14
    move-result v1

    .line 15
    add-int/2addr v1, v0

    .line 16
    mul-int/lit8 v1, v1, 0x1f

    .line 17
    .line 18
    iget-object v0, p0, Lcom/android/systemui/user/shared/model/UserModel;->image:Landroid/graphics/drawable/Drawable;

    .line 19
    .line 20
    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->hashCode()I

    .line 21
    .line 22
    .line 23
    move-result v0

    .line 24
    add-int/2addr v0, v1

    .line 25
    mul-int/lit8 v0, v0, 0x1f

    .line 26
    .line 27
    const/4 v1, 0x1

    .line 28
    iget-boolean v2, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelected:Z

    .line 29
    .line 30
    if-eqz v2, :cond_0

    .line 31
    .line 32
    move v2, v1

    .line 33
    :cond_0
    add-int/2addr v0, v2

    .line 34
    mul-int/lit8 v0, v0, 0x1f

    .line 35
    .line 36
    iget-boolean v2, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelectable:Z

    .line 37
    .line 38
    if-eqz v2, :cond_1

    .line 39
    .line 40
    move v2, v1

    .line 41
    :cond_1
    add-int/2addr v0, v2

    .line 42
    mul-int/lit8 v0, v0, 0x1f

    .line 43
    .line 44
    iget-boolean p0, p0, Lcom/android/systemui/user/shared/model/UserModel;->isGuest:Z

    .line 45
    .line 46
    if-eqz p0, :cond_2

    .line 47
    .line 48
    goto :goto_0

    .line 49
    :cond_2
    move v1, p0

    .line 50
    :goto_0
    add-int/2addr v0, v1

    .line 51
    return v0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "UserModel(id="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->id:I

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", name="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-object v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->name:Lcom/android/systemui/common/shared/model/Text;

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", image="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget-object v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->image:Landroid/graphics/drawable/Drawable;

    .line 29
    .line 30
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string v1, ", isSelected="

    .line 34
    .line 35
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    iget-boolean v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelected:Z

    .line 39
    .line 40
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    const-string v1, ", isSelectable="

    .line 44
    .line 45
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 46
    .line 47
    .line 48
    iget-boolean v1, p0, Lcom/android/systemui/user/shared/model/UserModel;->isSelectable:Z

    .line 49
    .line 50
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 51
    .line 52
    .line 53
    const-string v1, ", isGuest="

    .line 54
    .line 55
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 56
    .line 57
    .line 58
    iget-boolean p0, p0, Lcom/android/systemui/user/shared/model/UserModel;->isGuest:Z

    .line 59
    .line 60
    const-string v1, ")"

    .line 61
    .line 62
    invoke-static {v0, p0, v1}, Landroidx/appcompat/app/AppCompatDelegateImpl$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ZLjava/lang/String;)Ljava/lang/String;

    .line 63
    .line 64
    .line 65
    move-result-object p0

    .line 66
    return-object p0
.end method

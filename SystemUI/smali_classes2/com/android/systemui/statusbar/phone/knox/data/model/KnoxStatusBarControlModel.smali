.class public final Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final customTextSize:I

.field public final customTextStyle:I

.field public final customTextWidth:I

.field public final knoxStatusBarCustomText:Ljava/lang/String;

.field public final statusBarHidden:Z

.field public final statusBarIconsEnabled:Z


# direct methods
.method public constructor <init>(ZZLjava/lang/String;III)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 3
    iput-boolean p2, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 4
    iput-object p3, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 5
    iput p4, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 6
    iput p5, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 7
    iput p6, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    return-void
.end method

.method public synthetic constructor <init>(ZZLjava/lang/String;IIIILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 9

    and-int/lit8 v0, p7, 0x1

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    move v3, v1

    goto :goto_0

    :cond_0
    move v3, p1

    :goto_0
    and-int/lit8 v0, p7, 0x2

    if-eqz v0, :cond_1

    move v4, v1

    goto :goto_1

    :cond_1
    move v4, p2

    :goto_1
    move-object v2, p0

    move-object v5, p3

    move v6, p4

    move v7, p5

    move v8, p6

    .line 8
    invoke-direct/range {v2 .. v8}, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;-><init>(ZZLjava/lang/String;III)V

    return-void
.end method

.method public static copy$default(Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;ZZLjava/lang/String;IIII)Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;
    .locals 7

    .line 1
    and-int/lit8 v0, p7, 0x1

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    iget-boolean p1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 6
    .line 7
    :cond_0
    move v1, p1

    .line 8
    and-int/lit8 p1, p7, 0x2

    .line 9
    .line 10
    if-eqz p1, :cond_1

    .line 11
    .line 12
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 13
    .line 14
    :cond_1
    move v2, p2

    .line 15
    and-int/lit8 p1, p7, 0x4

    .line 16
    .line 17
    if-eqz p1, :cond_2

    .line 18
    .line 19
    iget-object p3, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 20
    .line 21
    :cond_2
    move-object v3, p3

    .line 22
    and-int/lit8 p1, p7, 0x8

    .line 23
    .line 24
    if-eqz p1, :cond_3

    .line 25
    .line 26
    iget p4, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 27
    .line 28
    :cond_3
    move v4, p4

    .line 29
    and-int/lit8 p1, p7, 0x10

    .line 30
    .line 31
    if-eqz p1, :cond_4

    .line 32
    .line 33
    iget p5, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 34
    .line 35
    :cond_4
    move v5, p5

    .line 36
    and-int/lit8 p1, p7, 0x20

    .line 37
    .line 38
    if-eqz p1, :cond_5

    .line 39
    .line 40
    iget p6, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    .line 41
    .line 42
    :cond_5
    move v6, p6

    .line 43
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 44
    .line 45
    .line 46
    new-instance p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;

    .line 47
    .line 48
    move-object v0, p0

    .line 49
    invoke-direct/range {v0 .. v6}, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;-><init>(ZZLjava/lang/String;III)V

    .line 50
    .line 51
    .line 52
    return-object p0
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
    instance-of v1, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;

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
    check-cast p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;

    .line 12
    .line 13
    iget-boolean v1, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 14
    .line 15
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 16
    .line 17
    if-eq v3, v1, :cond_2

    .line 18
    .line 19
    return v2

    .line 20
    :cond_2
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 21
    .line 22
    iget-boolean v3, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 23
    .line 24
    if-eq v1, v3, :cond_3

    .line 25
    .line 26
    return v2

    .line 27
    :cond_3
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 28
    .line 29
    iget-object v3, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 30
    .line 31
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 32
    .line 33
    .line 34
    move-result v1

    .line 35
    if-nez v1, :cond_4

    .line 36
    .line 37
    return v2

    .line 38
    :cond_4
    iget v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 39
    .line 40
    iget v3, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 41
    .line 42
    if-eq v1, v3, :cond_5

    .line 43
    .line 44
    return v2

    .line 45
    :cond_5
    iget v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 46
    .line 47
    iget v3, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 48
    .line 49
    if-eq v1, v3, :cond_6

    .line 50
    .line 51
    return v2

    .line 52
    :cond_6
    iget p0, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    .line 53
    .line 54
    iget p1, p1, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    .line 55
    .line 56
    if-eq p0, p1, :cond_7

    .line 57
    .line 58
    return v2

    .line 59
    :cond_7
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    const/4 v0, 0x1

    .line 2
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 3
    .line 4
    if-eqz v1, :cond_0

    .line 5
    .line 6
    move v1, v0

    .line 7
    :cond_0
    mul-int/lit8 v1, v1, 0x1f

    .line 8
    .line 9
    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 10
    .line 11
    if-eqz v2, :cond_1

    .line 12
    .line 13
    goto :goto_0

    .line 14
    :cond_1
    move v0, v2

    .line 15
    :goto_0
    add-int/2addr v1, v0

    .line 16
    mul-int/lit8 v1, v1, 0x1f

    .line 17
    .line 18
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 19
    .line 20
    if-nez v0, :cond_2

    .line 21
    .line 22
    const/4 v0, 0x0

    .line 23
    goto :goto_1

    .line 24
    :cond_2
    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    .line 25
    .line 26
    .line 27
    move-result v0

    .line 28
    :goto_1
    add-int/2addr v1, v0

    .line 29
    mul-int/lit8 v1, v1, 0x1f

    .line 30
    .line 31
    iget v0, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 32
    .line 33
    const/16 v2, 0x1f

    .line 34
    .line 35
    invoke-static {v0, v1, v2}, Landroidx/picker/model/viewdata/AppInfoViewData$$ExternalSyntheticOutline0;->m(III)I

    .line 36
    .line 37
    .line 38
    move-result v0

    .line 39
    iget v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 40
    .line 41
    invoke-static {v1, v0, v2}, Landroidx/picker/model/viewdata/AppInfoViewData$$ExternalSyntheticOutline0;->m(III)I

    .line 42
    .line 43
    .line 44
    move-result v0

    .line 45
    iget p0, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    .line 46
    .line 47
    invoke-static {p0}, Ljava/lang/Integer;->hashCode(I)I

    .line 48
    .line 49
    .line 50
    move-result p0

    .line 51
    add-int/2addr p0, v0

    .line 52
    return p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "KnoxStatusBarControlModel(statusBarHidden="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarHidden:Z

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", statusBarIconsEnabled="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->statusBarIconsEnabled:Z

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", knoxStatusBarCustomText="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->knoxStatusBarCustomText:Ljava/lang/String;

    .line 29
    .line 30
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string v1, ", customTextStyle="

    .line 34
    .line 35
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    iget v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextStyle:I

    .line 39
    .line 40
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    const-string v1, ", customTextSize="

    .line 44
    .line 45
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 46
    .line 47
    .line 48
    iget v1, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextSize:I

    .line 49
    .line 50
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 51
    .line 52
    .line 53
    const-string v1, ", customTextWidth="

    .line 54
    .line 55
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 56
    .line 57
    .line 58
    iget p0, p0, Lcom/android/systemui/statusbar/phone/knox/data/model/KnoxStatusBarControlModel;->customTextWidth:I

    .line 59
    .line 60
    const-string v1, ")"

    .line 61
    .line 62
    invoke-static {v0, p0, v1}, Landroidx/constraintlayout/core/widgets/ConstraintWidget$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;

    .line 63
    .line 64
    .line 65
    move-result-object p0

    .line 66
    return-object p0
.end method

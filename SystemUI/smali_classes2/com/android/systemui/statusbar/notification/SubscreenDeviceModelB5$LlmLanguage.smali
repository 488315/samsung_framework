.class public final Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public language:Ljava/lang/String;

.field public languageDisplayName:Ljava/lang/String;

.field public order:I

.field public supportCorrection:Z

.field public supportReply:Z

.field public supportToneConversion:Z


# direct methods
.method public constructor <init>()V
    .locals 9

    .line 1
    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/16 v7, 0x3f

    const/4 v8, 0x0

    move-object v0, p0

    invoke-direct/range {v0 .. v8}, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;-><init>(ILjava/lang/String;Ljava/lang/String;ZZZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-void
.end method

.method public constructor <init>(ILjava/lang/String;Ljava/lang/String;ZZZ)V
    .locals 0

    .line 2
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 3
    iput p1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->order:I

    .line 4
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->language:Ljava/lang/String;

    .line 5
    iput-object p3, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->languageDisplayName:Ljava/lang/String;

    .line 6
    iput-boolean p4, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportToneConversion:Z

    .line 7
    iput-boolean p5, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportCorrection:Z

    .line 8
    iput-boolean p6, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportReply:Z

    return-void
.end method

.method public synthetic constructor <init>(ILjava/lang/String;Ljava/lang/String;ZZZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 4

    and-int/lit8 p8, p7, 0x1

    if-eqz p8, :cond_0

    const/4 p1, -0x1

    :cond_0
    and-int/lit8 p8, p7, 0x2

    const-string v0, ""

    if-eqz p8, :cond_1

    move-object p8, v0

    goto :goto_0

    :cond_1
    move-object p8, p2

    :goto_0
    and-int/lit8 p2, p7, 0x4

    if-eqz p2, :cond_2

    goto :goto_1

    :cond_2
    move-object v0, p3

    :goto_1
    and-int/lit8 p2, p7, 0x8

    const/4 p3, 0x1

    if-eqz p2, :cond_3

    move v1, p3

    goto :goto_2

    :cond_3
    move v1, p4

    :goto_2
    and-int/lit8 p2, p7, 0x10

    if-eqz p2, :cond_4

    move v2, p3

    goto :goto_3

    :cond_4
    move v2, p5

    :goto_3
    and-int/lit8 p2, p7, 0x20

    if-eqz p2, :cond_5

    move v3, p3

    goto :goto_4

    :cond_5
    move v3, p6

    :goto_4
    move-object p2, p0

    move p3, p1

    move-object p4, p8

    move-object p5, v0

    move p6, v1

    move p7, v2

    move p8, v3

    .line 9
    invoke-direct/range {p2 .. p8}, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;-><init>(ILjava/lang/String;Ljava/lang/String;ZZZ)V

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
    instance-of v1, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;

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
    check-cast p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;

    .line 12
    .line 13
    iget v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->order:I

    .line 14
    .line 15
    iget v3, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->order:I

    .line 16
    .line 17
    if-eq v1, v3, :cond_2

    .line 18
    .line 19
    return v2

    .line 20
    :cond_2
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->language:Ljava/lang/String;

    .line 21
    .line 22
    iget-object v3, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->language:Ljava/lang/String;

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
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->languageDisplayName:Ljava/lang/String;

    .line 32
    .line 33
    iget-object v3, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->languageDisplayName:Ljava/lang/String;

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
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportToneConversion:Z

    .line 43
    .line 44
    iget-boolean v3, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportToneConversion:Z

    .line 45
    .line 46
    if-eq v1, v3, :cond_5

    .line 47
    .line 48
    return v2

    .line 49
    :cond_5
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportCorrection:Z

    .line 50
    .line 51
    iget-boolean v3, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportCorrection:Z

    .line 52
    .line 53
    if-eq v1, v3, :cond_6

    .line 54
    .line 55
    return v2

    .line 56
    :cond_6
    iget-boolean p0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportReply:Z

    .line 57
    .line 58
    iget-boolean p1, p1, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportReply:Z

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
    iget v0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->order:I

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
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->language:Ljava/lang/String;

    .line 10
    .line 11
    const/16 v2, 0x1f

    .line 12
    .line 13
    invoke-static {v1, v0, v2}, Landroidx/picker/model/AppInfo$$ExternalSyntheticOutline0;->m(Ljava/lang/String;II)I

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->languageDisplayName:Ljava/lang/String;

    .line 18
    .line 19
    invoke-static {v1, v0, v2}, Landroidx/picker/model/AppInfo$$ExternalSyntheticOutline0;->m(Ljava/lang/String;II)I

    .line 20
    .line 21
    .line 22
    move-result v0

    .line 23
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportToneConversion:Z

    .line 24
    .line 25
    const/4 v2, 0x1

    .line 26
    if-eqz v1, :cond_0

    .line 27
    .line 28
    move v1, v2

    .line 29
    :cond_0
    add-int/2addr v0, v1

    .line 30
    mul-int/lit8 v0, v0, 0x1f

    .line 31
    .line 32
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportCorrection:Z

    .line 33
    .line 34
    if-eqz v1, :cond_1

    .line 35
    .line 36
    move v1, v2

    .line 37
    :cond_1
    add-int/2addr v0, v1

    .line 38
    mul-int/lit8 v0, v0, 0x1f

    .line 39
    .line 40
    iget-boolean p0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportReply:Z

    .line 41
    .line 42
    if-eqz p0, :cond_2

    .line 43
    .line 44
    goto :goto_0

    .line 45
    :cond_2
    move v2, p0

    .line 46
    :goto_0
    add-int/2addr v0, v2

    .line 47
    return v0
.end method

.method public final toString()Ljava/lang/String;
    .locals 8

    .line 1
    iget v0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->order:I

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->language:Ljava/lang/String;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->languageDisplayName:Ljava/lang/String;

    .line 6
    .line 7
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportToneConversion:Z

    .line 8
    .line 9
    iget-boolean v4, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportCorrection:Z

    .line 10
    .line 11
    iget-boolean p0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelB5$LlmLanguage;->supportReply:Z

    .line 12
    .line 13
    const-string v5, "LlmLanguage(order="

    .line 14
    .line 15
    const-string v6, ", language="

    .line 16
    .line 17
    const-string v7, ", languageDisplayName="

    .line 18
    .line 19
    invoke-static {v5, v0, v6, v1, v7}, Lcom/android/keyguard/KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 24
    .line 25
    .line 26
    const-string v1, ", supportToneConversion="

    .line 27
    .line 28
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 29
    .line 30
    .line 31
    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 32
    .line 33
    .line 34
    const-string v1, ", supportCorrection="

    .line 35
    .line 36
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 37
    .line 38
    .line 39
    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 40
    .line 41
    .line 42
    const-string v1, ", supportReply="

    .line 43
    .line 44
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 45
    .line 46
    .line 47
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 48
    .line 49
    .line 50
    const-string p0, ")"

    .line 51
    .line 52
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 53
    .line 54
    .line 55
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 56
    .line 57
    .line 58
    move-result-object p0

    .line 59
    return-object p0
.end method

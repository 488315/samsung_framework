.class public final Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel;


# instance fields
.field public final activityIcons:[I

.field public final contentDescription:I

.field public final iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

.field public final iconId:I

.field public final name:Ljava/lang/String;


# direct methods
.method public constructor <init>(Lcom/android/settingslib/SignalIcon$MobileIconGroup;I)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 7
    .line 8
    iget p2, p1, Lcom/android/settingslib/SignalIcon$MobileIconGroup;->dataContentDescription:I

    .line 9
    .line 10
    iput p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->contentDescription:I

    .line 11
    .line 12
    iget-object p2, p1, Lcom/android/settingslib/SignalIcon$IconGroup;->name:Ljava/lang/String;

    .line 13
    .line 14
    iput-object p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->name:Ljava/lang/String;

    .line 15
    .line 16
    iget-object p1, p1, Lcom/android/settingslib/SignalIcon$IconGroup;->activityIcons:[I

    .line 17
    .line 18
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->activityIcons:[I

    .line 19
    .line 20
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
    instance-of v1, p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;

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
    check-cast p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;

    .line 12
    .line 13
    iget-object v1, p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

    .line 14
    .line 15
    iget-object v3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

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
    iget p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 25
    .line 26
    iget p1, p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 27
    .line 28
    if-eq p0, p1, :cond_3

    .line 29
    .line 30
    return v2

    .line 31
    :cond_3
    return v0
.end method

.method public final getActivityIcons()[I
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->activityIcons:[I

    .line 2
    .line 3
    return-object p0
.end method

.method public final getContentDescription()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->contentDescription:I

    .line 2
    .line 3
    return p0
.end method

.method public final getIconId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 2
    .line 3
    return p0
.end method

.method public final getName()Ljava/lang/String;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->name:Ljava/lang/String;

    .line 2
    .line 3
    return-object p0
.end method

.method public final hashCode()I
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->hashCode()I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    mul-int/lit8 v0, v0, 0x1f

    .line 8
    .line 9
    iget p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 10
    .line 11
    invoke-static {p0}, Ljava/lang/Integer;->hashCode(I)I

    .line 12
    .line 13
    .line 14
    move-result p0

    .line 15
    add-int/2addr p0, v0

    .line 16
    return p0
.end method

.method public final logDiffs(Lcom/android/systemui/log/table/Diffable;Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;)V
    .locals 3

    .line 1
    check-cast p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel;

    .line 2
    .line 3
    instance-of v0, p1, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;

    .line 4
    .line 5
    iget-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->name:Ljava/lang/String;

    .line 6
    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    invoke-interface {p1}, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel;->getName()Ljava/lang/String;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    if-eqz v0, :cond_0

    .line 18
    .line 19
    invoke-interface {p1}, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel;->getIconId()I

    .line 20
    .line 21
    .line 22
    move-result v0

    .line 23
    iget v2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 24
    .line 25
    if-ne v0, v2, :cond_0

    .line 26
    .line 27
    invoke-interface {p1}, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel;->getActivityIcons()[I

    .line 28
    .line 29
    .line 30
    move-result-object p1

    .line 31
    iget-object p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->activityIcons:[I

    .line 32
    .line 33
    invoke-static {p1, p0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 34
    .line 35
    .line 36
    move-result p0

    .line 37
    if-nez p0, :cond_1

    .line 38
    .line 39
    :cond_0
    new-instance p0, Ljava/lang/StringBuilder;

    .line 40
    .line 41
    const-string p1, "Ovrd("

    .line 42
    .line 43
    invoke-direct {p0, p1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 44
    .line 45
    .line 46
    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 47
    .line 48
    .line 49
    const-string p1, ")"

    .line 50
    .line 51
    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 52
    .line 53
    .line 54
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 55
    .line 56
    .line 57
    move-result-object p0

    .line 58
    const-string p1, "networkTypeIcon"

    .line 59
    .line 60
    invoke-virtual {p2, p1, p0}, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->logChange(Ljava/lang/String;Ljava/lang/String;)V

    .line 61
    .line 62
    .line 63
    :cond_1
    return-void
.end method

.method public final logFull(Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "OverriddenIcon(iconGroup="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconGroup:Lcom/android/settingslib/SignalIcon$MobileIconGroup;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", iconId="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/model/NetworkTypeIconModel$OverriddenIcon;->iconId:I

    .line 19
    .line 20
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

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

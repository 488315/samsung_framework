.class public final Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public batch:Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;

.field public final key:Ljava/lang/String;

.field public final position:I

.field public ranking:Landroid/service/notification/NotificationListenerService$Ranking;

.field public final sbn:Landroid/service/notification/StatusBarNotification;


# direct methods
.method public constructor <init>(Ljava/lang/String;ILandroid/service/notification/StatusBarNotification;Landroid/service/notification/NotificationListenerService$Ranking;Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->key:Ljava/lang/String;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->position:I

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->sbn:Landroid/service/notification/StatusBarNotification;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->ranking:Landroid/service/notification/NotificationListenerService$Ranking;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->batch:Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;

    .line 13
    .line 14
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
    instance-of v1, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;

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
    check-cast p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;

    .line 12
    .line 13
    iget-object v1, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->key:Ljava/lang/String;

    .line 14
    .line 15
    iget-object v3, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->key:Ljava/lang/String;

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
    iget v1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->position:I

    .line 25
    .line 26
    iget v3, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->position:I

    .line 27
    .line 28
    if-eq v1, v3, :cond_3

    .line 29
    .line 30
    return v2

    .line 31
    :cond_3
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->sbn:Landroid/service/notification/StatusBarNotification;

    .line 32
    .line 33
    iget-object v3, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->sbn:Landroid/service/notification/StatusBarNotification;

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
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->ranking:Landroid/service/notification/NotificationListenerService$Ranking;

    .line 43
    .line 44
    iget-object v3, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->ranking:Landroid/service/notification/NotificationListenerService$Ranking;

    .line 45
    .line 46
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 47
    .line 48
    .line 49
    move-result v1

    .line 50
    if-nez v1, :cond_5

    .line 51
    .line 52
    return v2

    .line 53
    :cond_5
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->batch:Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;

    .line 54
    .line 55
    iget-object p1, p1, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->batch:Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;

    .line 56
    .line 57
    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 58
    .line 59
    .line 60
    move-result p0

    .line 61
    if-nez p0, :cond_6

    .line 62
    .line 63
    return v2

    .line 64
    :cond_6
    return v0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->key:Ljava/lang/String;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    mul-int/lit8 v0, v0, 0x1f

    .line 8
    .line 9
    iget v1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->position:I

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
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->sbn:Landroid/service/notification/StatusBarNotification;

    .line 18
    .line 19
    invoke-virtual {v1}, Landroid/service/notification/StatusBarNotification;->hashCode()I

    .line 20
    .line 21
    .line 22
    move-result v1

    .line 23
    add-int/2addr v1, v0

    .line 24
    mul-int/lit8 v1, v1, 0x1f

    .line 25
    .line 26
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->ranking:Landroid/service/notification/NotificationListenerService$Ranking;

    .line 27
    .line 28
    invoke-virtual {v0}, Landroid/service/notification/NotificationListenerService$Ranking;->hashCode()I

    .line 29
    .line 30
    .line 31
    move-result v0

    .line 32
    add-int/2addr v0, v1

    .line 33
    mul-int/lit8 v0, v0, 0x1f

    .line 34
    .line 35
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->batch:Lcom/android/systemui/statusbar/notification/collection/coalescer/EventBatch;

    .line 36
    .line 37
    if-nez p0, :cond_0

    .line 38
    .line 39
    const/4 p0, 0x0

    .line 40
    goto :goto_0

    .line 41
    :cond_0
    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    .line 42
    .line 43
    .line 44
    move-result p0

    .line 45
    :goto_0
    add-int/2addr v0, p0

    .line 46
    return v0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "CoalescedEvent(key="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/collection/coalescer/CoalescedEvent;->key:Ljava/lang/String;

    .line 9
    .line 10
    const-string v1, ")"

    .line 11
    .line 12
    invoke-static {v0, p0, v1}, Landroidx/concurrent/futures/AbstractResolvableFuture$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 13
    .line 14
    .line 15
    move-result-object p0

    .line 16
    return-object p0
.end method

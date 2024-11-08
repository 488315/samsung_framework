.class public abstract Lcom/android/systemui/statusbar/AlertingNotificationManager;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mAlertEntries:Landroid/util/ArrayMap;

.field public mAutoDismissNotificationDecay:I

.field public final mClock:Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;

.field public mHandler:Landroid/os/Handler;

.field public final mLogger:Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;

.field public mMinimumDisplayTime:I

.field public mStickyDisplayTime:I


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;Landroid/os/Handler;)V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;

    .line 5
    .line 6
    invoke-direct {v0}, Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mClock:Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;

    .line 10
    .line 11
    new-instance v0, Landroid/util/ArrayMap;

    .line 12
    .line 13
    invoke-direct {v0}, Landroid/util/ArrayMap;-><init>()V

    .line 14
    .line 15
    .line 16
    iput-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 17
    .line 18
    iput-object p1, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mLogger:Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;

    .line 19
    .line 20
    iput-object p2, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mHandler:Landroid/os/Handler;

    .line 21
    .line 22
    return-void
.end method


# virtual methods
.method public canRemoveImmediately(Ljava/lang/String;)Z
    .locals 4

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;

    .line 8
    .line 9
    const/4 p1, 0x1

    .line 10
    if-eqz p0, :cond_3

    .line 11
    .line 12
    iget-wide v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mEarliestRemovaltime:J

    .line 13
    .line 14
    iget-object v2, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->this$0:Lcom/android/systemui/statusbar/AlertingNotificationManager;

    .line 15
    .line 16
    iget-object v2, v2, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mClock:Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;

    .line 17
    .line 18
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 19
    .line 20
    .line 21
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    .line 22
    .line 23
    .line 24
    move-result-wide v2

    .line 25
    cmp-long v0, v0, v2

    .line 26
    .line 27
    const/4 v1, 0x0

    .line 28
    if-gez v0, :cond_0

    .line 29
    .line 30
    move v0, p1

    .line 31
    goto :goto_0

    .line 32
    :cond_0
    move v0, v1

    .line 33
    :goto_0
    if-nez v0, :cond_3

    .line 34
    .line 35
    iget-object p0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mEntry:Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 36
    .line 37
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->row:Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 38
    .line 39
    if-eqz p0, :cond_1

    .line 40
    .line 41
    iget-boolean p0, p0, Lcom/android/systemui/statusbar/notification/row/ActivatableNotificationView;->mDismissed:Z

    .line 42
    .line 43
    if-eqz p0, :cond_1

    .line 44
    .line 45
    move p0, p1

    .line 46
    goto :goto_1

    .line 47
    :cond_1
    move p0, v1

    .line 48
    :goto_1
    if-eqz p0, :cond_2

    .line 49
    .line 50
    goto :goto_2

    .line 51
    :cond_2
    move p1, v1

    .line 52
    :cond_3
    :goto_2
    return p1
.end method

.method public final getAllEntries()Ljava/util/stream/Stream;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/util/ArrayMap;->values()Ljava/util/Collection;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    invoke-interface {p0}, Ljava/util/Collection;->stream()Ljava/util/stream/Stream;

    .line 8
    .line 9
    .line 10
    move-result-object p0

    .line 11
    new-instance v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$$ExternalSyntheticLambda0;

    .line 12
    .line 13
    invoke-direct {v0}, Lcom/android/systemui/statusbar/AlertingNotificationManager$$ExternalSyntheticLambda0;-><init>()V

    .line 14
    .line 15
    .line 16
    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->map(Ljava/util/function/Function;)Ljava/util/stream/Stream;

    .line 17
    .line 18
    .line 19
    move-result-object p0

    .line 20
    return-object p0
.end method

.method public final isAlerting(Ljava/lang/String;)Z
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/util/ArrayMap;->containsKey(Ljava/lang/Object;)Z

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    return p0
.end method

.method public abstract onAlertEntryRemoved(Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;)V
.end method

.method public final releaseAllImmediately()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mLogger:Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;

    .line 2
    .line 3
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;->logReleaseAllImmediately()V

    .line 4
    .line 5
    .line 6
    new-instance v0, Landroid/util/ArraySet;

    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 9
    .line 10
    invoke-virtual {v1}, Landroid/util/ArrayMap;->keySet()Ljava/util/Set;

    .line 11
    .line 12
    .line 13
    move-result-object v1

    .line 14
    invoke-direct {v0, v1}, Landroid/util/ArraySet;-><init>(Ljava/util/Collection;)V

    .line 15
    .line 16
    .line 17
    invoke-virtual {v0}, Landroid/util/ArraySet;->iterator()Ljava/util/Iterator;

    .line 18
    .line 19
    .line 20
    move-result-object v0

    .line 21
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 22
    .line 23
    .line 24
    move-result v1

    .line 25
    if-eqz v1, :cond_0

    .line 26
    .line 27
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 28
    .line 29
    .line 30
    move-result-object v1

    .line 31
    check-cast v1, Ljava/lang/String;

    .line 32
    .line 33
    invoke-virtual {p0, v1}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->removeAlertEntry(Ljava/lang/String;)V

    .line 34
    .line 35
    .line 36
    goto :goto_0

    .line 37
    :cond_0
    return-void
.end method

.method public final removeAlertEntry(Ljava/lang/String;)V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 2
    .line 3
    invoke-virtual {v0, p1}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v1

    .line 7
    check-cast v1, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;

    .line 8
    .line 9
    if-nez v1, :cond_0

    .line 10
    .line 11
    return-void

    .line 12
    :cond_0
    iget-object v2, v1, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mEntry:Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 13
    .line 14
    if-eqz v2, :cond_1

    .line 15
    .line 16
    iget-boolean v3, v2, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->mExpandAnimationRunning:Z

    .line 17
    .line 18
    if-eqz v3, :cond_1

    .line 19
    .line 20
    return-void

    .line 21
    :cond_1
    const/4 v3, 0x1

    .line 22
    iput-boolean v3, v2, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->mIsDemoted:Z

    .line 23
    .line 24
    invoke-virtual {v0, p1}, Landroid/util/ArrayMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 25
    .line 26
    .line 27
    invoke-virtual {p0, v1}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->onAlertEntryRemoved(Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;)V

    .line 28
    .line 29
    .line 30
    iget-object p0, v2, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->row:Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 31
    .line 32
    if-eqz p0, :cond_2

    .line 33
    .line 34
    const/16 p1, 0x800

    .line 35
    .line 36
    invoke-virtual {p0, p1}, Landroid/widget/FrameLayout;->sendAccessibilityEvent(I)V

    .line 37
    .line 38
    .line 39
    :cond_2
    invoke-virtual {v1}, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->reset()V

    .line 40
    .line 41
    .line 42
    return-void
.end method

.method public final removeNotification(Ljava/lang/String;Z)Z
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mLogger:Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;

    .line 2
    .line 3
    invoke-virtual {v0, p1, p2}, Lcom/android/systemui/statusbar/policy/HeadsUpManagerLogger;->logRemoveNotification(Ljava/lang/String;Z)V

    .line 4
    .line 5
    .line 6
    iget-object v0, p0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 7
    .line 8
    invoke-virtual {v0, p1}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 9
    .line 10
    .line 11
    move-result-object v0

    .line 12
    check-cast v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;

    .line 13
    .line 14
    const/4 v1, 0x1

    .line 15
    if-nez v0, :cond_0

    .line 16
    .line 17
    return v1

    .line 18
    :cond_0
    if-nez p2, :cond_3

    .line 19
    .line 20
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->canRemoveImmediately(Ljava/lang/String;)Z

    .line 21
    .line 22
    .line 23
    move-result p2

    .line 24
    if-eqz p2, :cond_1

    .line 25
    .line 26
    goto :goto_0

    .line 27
    :cond_1
    iget-object p0, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mRemoveAlertRunnable:Ljava/lang/Runnable;

    .line 28
    .line 29
    if-eqz p0, :cond_2

    .line 30
    .line 31
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->removeAutoRemovalCallbacks()V

    .line 32
    .line 33
    .line 34
    iget-wide p0, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mEarliestRemovaltime:J

    .line 35
    .line 36
    iget-object p2, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->this$0:Lcom/android/systemui/statusbar/AlertingNotificationManager;

    .line 37
    .line 38
    iget-object p2, p2, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mClock:Lcom/android/systemui/statusbar/AlertingNotificationManager$Clock;

    .line 39
    .line 40
    invoke-virtual {p2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 41
    .line 42
    .line 43
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    .line 44
    .line 45
    .line 46
    move-result-wide v1

    .line 47
    sub-long/2addr p0, v1

    .line 48
    iget-object p2, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->this$0:Lcom/android/systemui/statusbar/AlertingNotificationManager;

    .line 49
    .line 50
    iget-object p2, p2, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mHandler:Landroid/os/Handler;

    .line 51
    .line 52
    iget-object v0, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager$AlertEntry;->mRemoveAlertRunnable:Ljava/lang/Runnable;

    .line 53
    .line 54
    invoke-virtual {p2, v0, p0, p1}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 55
    .line 56
    .line 57
    :cond_2
    const/4 p0, 0x0

    .line 58
    return p0

    .line 59
    :cond_3
    :goto_0
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->removeAlertEntry(Ljava/lang/String;)V

    .line 60
    .line 61
    .line 62
    return v1
.end method

.class public final Lcom/android/systemui/power/notification/SafeModeNotification;
.super Lcom/android/systemui/power/notification/PowerUiNotification;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Lcom/android/systemui/power/notification/PowerUiNotification;-><init>(Landroid/content/Context;)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final dismissNotification()V
    .locals 3

    .line 1
    iget-object p0, p0, Lcom/android/systemui/power/notification/PowerUiNotification;->mNotificationManager:Landroid/app/NotificationManager;

    .line 2
    .line 3
    const v0, 0x7f0a0772

    .line 4
    .line 5
    .line 6
    sget-object v1, Landroid/os/UserHandle;->ALL:Landroid/os/UserHandle;

    .line 7
    .line 8
    const-string/jumbo v2, "safe_mode"

    .line 9
    .line 10
    .line 11
    invoke-virtual {p0, v2, v0, v1}, Landroid/app/NotificationManager;->cancelAsUser(Ljava/lang/String;ILandroid/os/UserHandle;)V

    .line 12
    .line 13
    .line 14
    return-void
.end method

.method public final getBuilder()Landroid/app/Notification$Builder;
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/power/notification/PowerUiNotification;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    const v1, 0x7f130e52

    .line 4
    .line 5
    .line 6
    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 7
    .line 8
    .line 9
    move-result-object v1

    .line 10
    const v2, 0x7f130e51

    .line 11
    .line 12
    .line 13
    invoke-virtual {v0, v2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 14
    .line 15
    .line 16
    move-result-object v2

    .line 17
    const-string v3, "ALR"

    .line 18
    .line 19
    invoke-virtual {p0, v1, v2, v3}, Lcom/android/systemui/power/notification/PowerUiNotification;->getCommonBuilder(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 20
    .line 21
    .line 22
    move-result-object p0

    .line 23
    const v1, 0x7f081117

    .line 24
    .line 25
    .line 26
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    .line 27
    .line 28
    .line 29
    move-result-object p0

    .line 30
    const/4 v1, 0x1

    .line 31
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setOnlyAlertOnce(Z)Landroid/app/Notification$Builder;

    .line 32
    .line 33
    .line 34
    move-result-object p0

    .line 35
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    .line 36
    .line 37
    .line 38
    move-result-object p0

    .line 39
    const-string v1, "com.samsung.systemui.power.action.ACTION_BATTERY_SAFE_MODE"

    .line 40
    .line 41
    invoke-static {v0, v1}, Lcom/android/systemui/power/PowerUtils;->pendingBroadcast(Landroid/content/Context;Ljava/lang/String;)Landroid/app/PendingIntent;

    .line 42
    .line 43
    .line 44
    move-result-object v1

    .line 45
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    .line 46
    .line 47
    .line 48
    move-result-object p0

    .line 49
    const/4 v1, 0x0

    .line 50
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setPriority(I)Landroid/app/Notification$Builder;

    .line 51
    .line 52
    .line 53
    move-result-object p0

    .line 54
    const-string/jumbo v2, "sys"

    .line 55
    .line 56
    .line 57
    invoke-virtual {p0, v2}, Landroid/app/Notification$Builder;->setCategory(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 58
    .line 59
    .line 60
    move-result-object p0

    .line 61
    invoke-static {v0, p0, v1}, Lcom/android/systemui/SystemUIApplication;->overrideNotificationAppName(Landroid/content/Context;Landroid/app/Notification$Builder;Z)V

    .line 62
    .line 63
    .line 64
    return-object p0
.end method

.method public final setInformation(Lcom/android/systemui/power/SecBatteryStatsSnapshot;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final showNotification()V
    .locals 4

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/power/notification/SafeModeNotification;->getBuilder()Landroid/app/Notification$Builder;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    invoke-virtual {v0}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    iget-object v1, v0, Landroid/app/Notification;->headsUpContentView:Landroid/widget/RemoteViews;

    .line 10
    .line 11
    if-eqz v1, :cond_0

    .line 12
    .line 13
    const v2, 0x10204fd

    .line 14
    .line 15
    .line 16
    const/16 v3, 0x8

    .line 17
    .line 18
    invoke-virtual {v1, v2, v3}, Landroid/widget/RemoteViews;->setViewVisibility(II)V

    .line 19
    .line 20
    .line 21
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/power/notification/PowerUiNotification;->mNotificationManager:Landroid/app/NotificationManager;

    .line 22
    .line 23
    const v1, 0x7f0a0772

    .line 24
    .line 25
    .line 26
    sget-object v2, Landroid/os/UserHandle;->ALL:Landroid/os/UserHandle;

    .line 27
    .line 28
    const-string/jumbo v3, "safe_mode"

    .line 29
    .line 30
    .line 31
    invoke-virtual {p0, v3, v1, v0, v2}, Landroid/app/NotificationManager;->notifyAsUser(Ljava/lang/String;ILandroid/app/Notification;Landroid/os/UserHandle;)V

    .line 32
    .line 33
    .line 34
    return-void
.end method

.class public final synthetic Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/notification/InstantAppNotifier;

.field public final synthetic f$1:Landroid/app/NotificationManager;

.field public final synthetic f$2:Landroid/content/pm/IPackageManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/notification/InstantAppNotifier;Landroid/app/NotificationManager;Landroid/content/pm/IPackageManager;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/notification/InstantAppNotifier;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$1:Landroid/app/NotificationManager;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$2:Landroid/content/pm/IPackageManager;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 6

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/notification/InstantAppNotifier;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$1:Landroid/app/NotificationManager;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda0;->f$2:Landroid/content/pm/IPackageManager;

    .line 6
    .line 7
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 8
    .line 9
    .line 10
    new-instance v2, Landroid/util/ArraySet;

    .line 11
    .line 12
    iget-object v3, v0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier;->mCurrentNotifs:Landroid/util/ArraySet;

    .line 13
    .line 14
    invoke-direct {v2, v3}, Landroid/util/ArraySet;-><init>(Landroid/util/ArraySet;)V

    .line 15
    .line 16
    .line 17
    :try_start_0
    invoke-static {}, Landroid/app/ActivityTaskManager;->getService()Landroid/app/IActivityTaskManager;

    .line 18
    .line 19
    .line 20
    move-result-object v3

    .line 21
    invoke-interface {v3}, Landroid/app/IActivityTaskManager;->getFocusedRootTaskInfo()Landroid/app/ActivityTaskManager$RootTaskInfo;

    .line 22
    .line 23
    .line 24
    move-result-object v3

    .line 25
    if-eqz v3, :cond_1

    .line 26
    .line 27
    iget-object v4, v3, Landroid/app/ActivityTaskManager$RootTaskInfo;->configuration:Landroid/content/res/Configuration;

    .line 28
    .line 29
    iget-object v4, v4, Landroid/content/res/Configuration;->windowConfiguration:Landroid/app/WindowConfiguration;

    .line 30
    .line 31
    invoke-virtual {v4}, Landroid/app/WindowConfiguration;->getWindowingMode()I

    .line 32
    .line 33
    .line 34
    move-result v4

    .line 35
    const/4 v5, 0x1

    .line 36
    if-eq v4, v5, :cond_0

    .line 37
    .line 38
    const/4 v5, 0x6

    .line 39
    if-eq v4, v5, :cond_0

    .line 40
    .line 41
    const/4 v5, 0x5

    .line 42
    if-ne v4, v5, :cond_1

    .line 43
    .line 44
    :cond_0
    invoke-virtual {v0, v3, v2, v1, p0}, Lcom/android/systemui/statusbar/notification/InstantAppNotifier;->checkAndPostForStack(Landroid/app/ActivityTaskManager$RootTaskInfo;Landroid/util/ArraySet;Landroid/app/NotificationManager;Landroid/content/pm/IPackageManager;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 45
    .line 46
    .line 47
    goto :goto_0

    .line 48
    :catch_0
    move-exception p0

    .line 49
    invoke-virtual {p0}, Landroid/os/RemoteException;->rethrowFromSystemServer()Ljava/lang/RuntimeException;

    .line 50
    .line 51
    .line 52
    :cond_1
    :goto_0
    new-instance p0, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda1;

    .line 53
    .line 54
    invoke-direct {p0, v0, v1}, Lcom/android/systemui/statusbar/notification/InstantAppNotifier$$ExternalSyntheticLambda1;-><init>(Lcom/android/systemui/statusbar/notification/InstantAppNotifier;Landroid/app/NotificationManager;)V

    .line 55
    .line 56
    .line 57
    invoke-virtual {v2, p0}, Landroid/util/ArraySet;->forEach(Ljava/util/function/Consumer;)V

    .line 58
    .line 59
    .line 60
    return-void
.end method

.class public final Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/NotificationRemoteInputManager$Callback;
.implements Lcom/android/systemui/statusbar/CommandQueue$Callbacks;
.implements Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;


# instance fields
.field public final mActionClickLogger:Lcom/android/systemui/statusbar/ActionClickLogger;

.field public final mActivityIntentHelper:Lcom/android/systemui/ActivityIntentHelper;

.field public final mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

.field public final mChallengeReceiver:Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$ChallengeReceiver;

.field public final mCommandQueue:Lcom/android/systemui/statusbar/CommandQueue;

.field public final mContext:Landroid/content/Context;

.field public mDisabled2:I

.field public final mExecutor:Ljava/util/concurrent/Executor;

.field public final mGroupExpansionManager:Lcom/android/systemui/statusbar/notification/collection/render/GroupExpansionManager;

.field public final mKeyguardManager:Landroid/app/KeyguardManager;

.field public final mKeyguardStateController:Lcom/android/systemui/statusbar/policy/KeyguardStateController;

.field public final mLockscreenUserManager:Lcom/android/systemui/statusbar/NotificationLockscreenUserManager;

.field public mPendingRemoteInputView:Landroid/view/View;

.field public mPendingWorkRemoteInputView:Landroid/view/View;

.field public final mShadeController:Lcom/android/systemui/shade/ShadeController;

.field public final mStatusBarKeyguardViewManager:Lcom/android/systemui/statusbar/phone/StatusBarKeyguardViewManager;

.field public final mStatusBarStateController:Lcom/android/systemui/statusbar/SysuiStatusBarStateController;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/android/systemui/statusbar/notification/collection/render/GroupExpansionManager;Lcom/android/systemui/statusbar/NotificationLockscreenUserManager;Lcom/android/systemui/statusbar/policy/KeyguardStateController;Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/statusbar/phone/StatusBarKeyguardViewManager;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/shade/ShadeController;Lcom/android/systemui/statusbar/CommandQueue;Lcom/android/systemui/statusbar/ActionClickLogger;Ljava/util/concurrent/Executor;)V
    .locals 9

    .line 1
    move-object v0, p0

    .line 2
    move-object v7, p1

    .line 3
    move-object/from16 v8, p9

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    new-instance v2, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$ChallengeReceiver;

    .line 9
    .line 10
    invoke-direct {v2, p0}, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$ChallengeReceiver;-><init>(Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;)V

    .line 11
    .line 12
    .line 13
    iput-object v2, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mChallengeReceiver:Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$ChallengeReceiver;

    .line 14
    .line 15
    iput-object v7, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mContext:Landroid/content/Context;

    .line 16
    .line 17
    move-object v1, p6

    .line 18
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mStatusBarKeyguardViewManager:Lcom/android/systemui/statusbar/phone/StatusBarKeyguardViewManager;

    .line 19
    .line 20
    move-object/from16 v1, p8

    .line 21
    .line 22
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mShadeController:Lcom/android/systemui/shade/ShadeController;

    .line 23
    .line 24
    move-object/from16 v1, p11

    .line 25
    .line 26
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mExecutor:Ljava/util/concurrent/Executor;

    .line 27
    .line 28
    sget-object v3, Landroid/os/UserHandle;->ALL:Landroid/os/UserHandle;

    .line 29
    .line 30
    new-instance v4, Landroid/content/IntentFilter;

    .line 31
    .line 32
    const-string v1, "android.intent.action.DEVICE_LOCKED_CHANGED"

    .line 33
    .line 34
    invoke-direct {v4, v1}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 35
    .line 36
    .line 37
    const/4 v5, 0x0

    .line 38
    const/4 v6, 0x0

    .line 39
    move-object v1, p1

    .line 40
    invoke-virtual/range {v1 .. v6}, Landroid/content/Context;->registerReceiverAsUser(Landroid/content/BroadcastReceiver;Landroid/os/UserHandle;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 41
    .line 42
    .line 43
    move-object v1, p3

    .line 44
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mLockscreenUserManager:Lcom/android/systemui/statusbar/NotificationLockscreenUserManager;

    .line 45
    .line 46
    move-object v1, p4

    .line 47
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mKeyguardStateController:Lcom/android/systemui/statusbar/policy/KeyguardStateController;

    .line 48
    .line 49
    move-object v1, p5

    .line 50
    check-cast v1, Lcom/android/systemui/statusbar/SysuiStatusBarStateController;

    .line 51
    .line 52
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mStatusBarStateController:Lcom/android/systemui/statusbar/SysuiStatusBarStateController;

    .line 53
    .line 54
    move-object/from16 v2, p7

    .line 55
    .line 56
    iput-object v2, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    .line 57
    .line 58
    check-cast v1, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;

    .line 59
    .line 60
    invoke-virtual {v1, p0}, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;->addCallback(Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;)V

    .line 61
    .line 62
    .line 63
    const-class v1, Landroid/app/KeyguardManager;

    .line 64
    .line 65
    invoke-virtual {p1, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/Class;)Ljava/lang/Object;

    .line 66
    .line 67
    .line 68
    move-result-object v1

    .line 69
    check-cast v1, Landroid/app/KeyguardManager;

    .line 70
    .line 71
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mKeyguardManager:Landroid/app/KeyguardManager;

    .line 72
    .line 73
    iput-object v8, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mCommandQueue:Lcom/android/systemui/statusbar/CommandQueue;

    .line 74
    .line 75
    invoke-virtual {v8, p0}, Lcom/android/systemui/statusbar/CommandQueue;->addCallback(Lcom/android/systemui/statusbar/CommandQueue$Callbacks;)V

    .line 76
    .line 77
    .line 78
    move-object/from16 v1, p10

    .line 79
    .line 80
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mActionClickLogger:Lcom/android/systemui/statusbar/ActionClickLogger;

    .line 81
    .line 82
    new-instance v1, Lcom/android/systemui/ActivityIntentHelper;

    .line 83
    .line 84
    invoke-direct {v1, p1}, Lcom/android/systemui/ActivityIntentHelper;-><init>(Landroid/content/Context;)V

    .line 85
    .line 86
    .line 87
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mActivityIntentHelper:Lcom/android/systemui/ActivityIntentHelper;

    .line 88
    .line 89
    move-object v1, p2

    .line 90
    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mGroupExpansionManager:Lcom/android/systemui/statusbar/notification/collection/render/GroupExpansionManager;

    .line 91
    .line 92
    return-void
.end method


# virtual methods
.method public final disable(IIIZ)V
    .locals 0

    .line 1
    iget-object p2, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    invoke-virtual {p2}, Landroid/content/Context;->getDisplayId()I

    .line 4
    .line 5
    .line 6
    move-result p2

    .line 7
    if-ne p1, p2, :cond_0

    .line 8
    .line 9
    iput p3, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mDisabled2:I

    .line 10
    .line 11
    :cond_0
    return-void
.end method

.method public final onStateChanged(I)V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mPendingRemoteInputView:Landroid/view/View;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-eqz v0, :cond_0

    .line 5
    .line 6
    const/4 v0, 0x1

    .line 7
    goto :goto_0

    .line 8
    :cond_0
    move v0, v1

    .line 9
    :goto_0
    if-nez p1, :cond_3

    .line 10
    .line 11
    iget-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mStatusBarStateController:Lcom/android/systemui/statusbar/SysuiStatusBarStateController;

    .line 12
    .line 13
    move-object v2, p1

    .line 14
    check-cast v2, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;

    .line 15
    .line 16
    iget-boolean v2, v2, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;->mLeaveOpenOnKeyguardHide:Z

    .line 17
    .line 18
    if-nez v2, :cond_1

    .line 19
    .line 20
    if-eqz v0, :cond_3

    .line 21
    .line 22
    :cond_1
    check-cast p1, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;

    .line 23
    .line 24
    iget-boolean p1, p1, Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;->mKeyguardRequested:Z

    .line 25
    .line 26
    if-nez p1, :cond_3

    .line 27
    .line 28
    iget-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mKeyguardStateController:Lcom/android/systemui/statusbar/policy/KeyguardStateController;

    .line 29
    .line 30
    invoke-interface {p1}, Lcom/android/systemui/statusbar/policy/KeyguardStateController;->isUnlocked()Z

    .line 31
    .line 32
    .line 33
    move-result p1

    .line 34
    if-eqz p1, :cond_3

    .line 35
    .line 36
    if-eqz v0, :cond_2

    .line 37
    .line 38
    iget-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mPendingRemoteInputView:Landroid/view/View;

    .line 39
    .line 40
    invoke-static {p1}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    .line 41
    .line 42
    .line 43
    new-instance v0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$$ExternalSyntheticLambda1;

    .line 44
    .line 45
    invoke-direct {v0, p1, v1}, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback$$ExternalSyntheticLambda1;-><init>(Ljava/lang/Object;I)V

    .line 46
    .line 47
    .line 48
    iget-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mExecutor:Ljava/util/concurrent/Executor;

    .line 49
    .line 50
    invoke-interface {p1, v0}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 51
    .line 52
    .line 53
    :cond_2
    const/4 p1, 0x0

    .line 54
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mPendingRemoteInputView:Landroid/view/View;

    .line 55
    .line 56
    :cond_3
    return-void
.end method

.method public final startWorkChallengeIfNecessary(ILandroid/content/IntentSender;Ljava/lang/String;)Z
    .locals 4

    .line 1
    const/4 v0, 0x0

    .line 2
    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mPendingWorkRemoteInputView:Landroid/view/View;

    .line 3
    .line 4
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mKeyguardManager:Landroid/app/KeyguardManager;

    .line 5
    .line 6
    invoke-virtual {v1, v0, v0, p1}, Landroid/app/KeyguardManager;->createConfirmDeviceCredentialIntent(Ljava/lang/CharSequence;Ljava/lang/CharSequence;I)Landroid/content/Intent;

    .line 7
    .line 8
    .line 9
    move-result-object p1

    .line 10
    const/4 v1, 0x0

    .line 11
    if-nez p1, :cond_0

    .line 12
    .line 13
    return v1

    .line 14
    :cond_0
    new-instance v2, Landroid/content/Intent;

    .line 15
    .line 16
    const-string v3, "com.android.systemui.statusbar.work_challenge_unlocked_notification_action"

    .line 17
    .line 18
    invoke-direct {v2, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 19
    .line 20
    .line 21
    const-string v3, "android.intent.extra.INTENT"

    .line 22
    .line 23
    invoke-virtual {v2, v3, p2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 24
    .line 25
    .line 26
    const-string p2, "android.intent.extra.INDEX"

    .line 27
    .line 28
    invoke-virtual {v2, p2, p3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 29
    .line 30
    .line 31
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/StatusBarRemoteInputCallback;->mContext:Landroid/content/Context;

    .line 32
    .line 33
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    .line 34
    .line 35
    .line 36
    move-result-object p2

    .line 37
    invoke-virtual {v2, p2}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 38
    .line 39
    .line 40
    const/high16 p2, 0x54000000

    .line 41
    .line 42
    invoke-static {p0, v1, v2, p2}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    .line 43
    .line 44
    .line 45
    move-result-object p0

    .line 46
    invoke-virtual {p0}, Landroid/app/PendingIntent;->getIntentSender()Landroid/content/IntentSender;

    .line 47
    .line 48
    .line 49
    move-result-object p0

    .line 50
    invoke-virtual {p1, v3, p0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 51
    .line 52
    .line 53
    :try_start_0
    invoke-static {}, Landroid/app/ActivityManager;->getService()Landroid/app/IActivityManager;

    .line 54
    .line 55
    .line 56
    move-result-object p0

    .line 57
    invoke-interface {p0, p1, v0}, Landroid/app/IActivityManager;->startConfirmDeviceCredentialIntent(Landroid/content/Intent;Landroid/os/Bundle;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 58
    .line 59
    .line 60
    :catch_0
    const/4 p0, 0x1

    .line 61
    return p0
.end method

.class public final Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# instance fields
.field public final synthetic $this_with:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

.field public final synthetic this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/user/UserSwitchDialogController;Lcom/android/systemui/statusbar/phone/SystemUIDialog;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->$this_with:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onClick(Landroid/content/DialogInterface;I)V
    .locals 1

    .line 1
    iget-object p1, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;

    .line 2
    .line 3
    iget-object p1, p1, Lcom/android/systemui/qs/user/UserSwitchDialogController;->falsingManager:Lcom/android/systemui/plugins/FalsingManager;

    .line 4
    .line 5
    const/4 p2, 0x1

    .line 6
    invoke-interface {p1, p2}, Lcom/android/systemui/plugins/FalsingManager;->isFalseTap(I)Z

    .line 7
    .line 8
    .line 9
    move-result p1

    .line 10
    if-nez p1, :cond_1

    .line 11
    .line 12
    iget-object p1, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;

    .line 13
    .line 14
    iget-object p1, p1, Lcom/android/systemui/qs/user/UserSwitchDialogController;->uiEventLogger:Lcom/android/internal/logging/UiEventLogger;

    .line 15
    .line 16
    sget-object p2, Lcom/android/systemui/qs/QSUserSwitcherEvent;->QS_USER_MORE_SETTINGS:Lcom/android/systemui/qs/QSUserSwitcherEvent;

    .line 17
    .line 18
    invoke-interface {p1, p2}, Lcom/android/internal/logging/UiEventLogger;->log(Lcom/android/internal/logging/UiEventLogger$UiEventEnum;)V

    .line 19
    .line 20
    .line 21
    iget-object p1, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;

    .line 22
    .line 23
    iget-object p1, p1, Lcom/android/systemui/qs/user/UserSwitchDialogController;->dialogLaunchAnimator:Lcom/android/systemui/animation/DialogLaunchAnimator;

    .line 24
    .line 25
    iget-object p2, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->$this_with:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    .line 26
    .line 27
    const/4 v0, -0x3

    .line 28
    invoke-virtual {p2, v0}, Landroid/app/AlertDialog;->getButton(I)Landroid/widget/Button;

    .line 29
    .line 30
    .line 31
    move-result-object p2

    .line 32
    invoke-static {p1, p2}, Lcom/android/systemui/animation/DialogLaunchAnimator;->createActivityLaunchController$default(Lcom/android/systemui/animation/DialogLaunchAnimator;Landroid/view/View;)Lcom/android/systemui/animation/DialogLaunchAnimator$createActivityLaunchController$1;

    .line 33
    .line 34
    .line 35
    move-result-object p1

    .line 36
    if-nez p1, :cond_0

    .line 37
    .line 38
    iget-object p2, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->$this_with:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    .line 39
    .line 40
    invoke-virtual {p2}, Landroid/app/AlertDialog;->dismiss()V

    .line 41
    .line 42
    .line 43
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController$showDialog$1$2;->this$0:Lcom/android/systemui/qs/user/UserSwitchDialogController;

    .line 44
    .line 45
    iget-object p0, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->activityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    .line 46
    .line 47
    sget-object p2, Lcom/android/systemui/qs/user/UserSwitchDialogController;->USER_SETTINGS_INTENT:Landroid/content/Intent;

    .line 48
    .line 49
    const/4 v0, 0x0

    .line 50
    invoke-interface {p0, p2, v0, p1}, Lcom/android/systemui/plugins/ActivityStarter;->postStartActivityDismissingKeyguard(Landroid/content/Intent;ILcom/android/systemui/animation/ActivityLaunchAnimator$Controller;)V

    .line 51
    .line 52
    .line 53
    :cond_1
    return-void
.end method

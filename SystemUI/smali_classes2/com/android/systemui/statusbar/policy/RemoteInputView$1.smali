.class public final Lcom/android/systemui/statusbar/policy/RemoteInputView$1;
.super Landroid/view/WindowInsetsAnimation$Callback;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/policy/RemoteInputView;I)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$1;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 2
    .line 3
    invoke-direct {p0, p2}, Landroid/view/WindowInsetsAnimation$Callback;-><init>(I)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onEnd(Landroid/view/WindowInsetsAnimation;)V
    .locals 4

    .line 1
    invoke-super {p0, p1}, Landroid/view/WindowInsetsAnimation$Callback;->onEnd(Landroid/view/WindowInsetsAnimation;)V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p1}, Landroid/view/WindowInsetsAnimation;->getTypeMask()I

    .line 5
    .line 6
    .line 7
    move-result p1

    .line 8
    invoke-static {}, Landroid/view/WindowInsets$Type;->ime()I

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    if-ne p1, v0, :cond_2

    .line 13
    .line 14
    iget-object p1, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$1;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 15
    .line 16
    iget-object v0, p1, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mEntry:Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 17
    .line 18
    const/4 v1, 0x0

    .line 19
    iput-boolean v1, v0, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->mRemoteEditImeAnimatingAway:Z

    .line 20
    .line 21
    iget-object p1, p1, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mEditText:Lcom/android/systemui/statusbar/policy/RemoteInputView$RemoteEditText;

    .line 22
    .line 23
    invoke-virtual {p1}, Landroid/widget/EditText;->getRootWindowInsets()Landroid/view/WindowInsets;

    .line 24
    .line 25
    .line 26
    move-result-object p1

    .line 27
    if-nez p1, :cond_0

    .line 28
    .line 29
    new-instance v0, Ljava/lang/Exception;

    .line 30
    .line 31
    invoke-direct {v0}, Ljava/lang/Exception;-><init>()V

    .line 32
    .line 33
    .line 34
    const-string v2, "RemoteInput"

    .line 35
    .line 36
    const-string v3, "onEnd called on detached view"

    .line 37
    .line 38
    invoke-static {v2, v3, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 39
    .line 40
    .line 41
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$1;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 42
    .line 43
    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mEntry:Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 44
    .line 45
    if-eqz p1, :cond_1

    .line 46
    .line 47
    invoke-static {}, Landroid/view/WindowInsets$Type;->ime()I

    .line 48
    .line 49
    .line 50
    move-result v2

    .line 51
    invoke-virtual {p1, v2}, Landroid/view/WindowInsets;->isVisible(I)Z

    .line 52
    .line 53
    .line 54
    move-result p1

    .line 55
    if-eqz p1, :cond_1

    .line 56
    .line 57
    const/4 v1, 0x1

    .line 58
    :cond_1
    iput-boolean v1, v0, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->mRemoteEditImeVisible:Z

    .line 59
    .line 60
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$1;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 61
    .line 62
    iget-object p1, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mEntry:Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 63
    .line 64
    iget-boolean v0, p1, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->mRemoteEditImeVisible:Z

    .line 65
    .line 66
    if-nez v0, :cond_2

    .line 67
    .line 68
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mEditText:Lcom/android/systemui/statusbar/policy/RemoteInputView$RemoteEditText;

    .line 69
    .line 70
    iget-boolean v0, v0, Lcom/android/systemui/statusbar/policy/RemoteInputView$RemoteEditText;->mShowImeOnInputConnection:Z

    .line 71
    .line 72
    if-nez v0, :cond_2

    .line 73
    .line 74
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mController:Lcom/android/systemui/statusbar/RemoteInputController;

    .line 75
    .line 76
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView;->mToken:Ljava/lang/Object;

    .line 77
    .line 78
    invoke-virtual {v0, p1, p0}, Lcom/android/systemui/statusbar/RemoteInputController;->removeRemoteInput(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Ljava/lang/Object;)V

    .line 79
    .line 80
    .line 81
    :cond_2
    return-void
.end method

.method public final onProgress(Landroid/view/WindowInsets;Ljava/util/List;)Landroid/view/WindowInsets;
    .locals 0

    .line 1
    return-object p1
.end method
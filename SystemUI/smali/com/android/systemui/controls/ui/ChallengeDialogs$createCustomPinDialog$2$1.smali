.class public final Lcom/android/systemui/controls/ui/ChallengeDialogs$createCustomPinDialog$2$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# instance fields
.field public final synthetic $cvh:Lcom/android/systemui/controls/ui/ControlViewHolder;

.field public final synthetic $lastAction:Landroid/service/controls/actions/ControlAction;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/ui/ControlViewHolder;Landroid/service/controls/actions/ControlAction;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/ui/ChallengeDialogs$createCustomPinDialog$2$1;->$cvh:Lcom/android/systemui/controls/ui/ControlViewHolder;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/controls/ui/ChallengeDialogs$createCustomPinDialog$2$1;->$lastAction:Landroid/service/controls/actions/ControlAction;

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
    .locals 2

    .line 1
    instance-of p2, p1, Landroid/app/Dialog;

    .line 2
    .line 3
    if-eqz p2, :cond_0

    .line 4
    .line 5
    move-object p2, p1

    .line 6
    check-cast p2, Landroid/app/Dialog;

    .line 7
    .line 8
    const v0, 0x7f0a02c0

    .line 9
    .line 10
    .line 11
    invoke-virtual {p2, v0}, Landroid/app/Dialog;->requireViewById(I)Landroid/view/View;

    .line 12
    .line 13
    .line 14
    move-result-object p2

    .line 15
    check-cast p2, Lcom/google/android/material/textfield/TextInputLayout;

    .line 16
    .line 17
    iget-object p2, p2, Lcom/google/android/material/textfield/TextInputLayout;->editText:Landroid/widget/EditText;

    .line 18
    .line 19
    invoke-static {p2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 20
    .line 21
    .line 22
    invoke-virtual {p2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    .line 23
    .line 24
    .line 25
    move-result-object p2

    .line 26
    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    .line 27
    .line 28
    .line 29
    move-result-object p2

    .line 30
    iget-object v0, p0, Lcom/android/systemui/controls/ui/ChallengeDialogs$createCustomPinDialog$2$1;->$cvh:Lcom/android/systemui/controls/ui/ControlViewHolder;

    .line 31
    .line 32
    sget-object v1, Lcom/android/systemui/controls/ui/ChallengeDialogs;->INSTANCE:Lcom/android/systemui/controls/ui/ChallengeDialogs;

    .line 33
    .line 34
    iget-object p0, p0, Lcom/android/systemui/controls/ui/ChallengeDialogs$createCustomPinDialog$2$1;->$lastAction:Landroid/service/controls/actions/ControlAction;

    .line 35
    .line 36
    invoke-static {v1, p0, p2}, Lcom/android/systemui/controls/ui/ChallengeDialogs;->access$addChallengeValue(Lcom/android/systemui/controls/ui/ChallengeDialogs;Landroid/service/controls/actions/ControlAction;Ljava/lang/String;)Landroid/service/controls/actions/ControlAction;

    .line 37
    .line 38
    .line 39
    move-result-object p0

    .line 40
    invoke-virtual {v0, p0}, Lcom/android/systemui/controls/ui/ControlViewHolder;->action(Landroid/service/controls/actions/ControlAction;)V

    .line 41
    .line 42
    .line 43
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 44
    .line 45
    .line 46
    :cond_0
    return-void
.end method
.class public final Lcom/android/systemui/popup/SamsungScreenPinningRequest$1;
.super Landroid/content/BroadcastReceiver;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/popup/SamsungScreenPinningRequest;


# direct methods
.method public constructor <init>(Lcom/android/systemui/popup/SamsungScreenPinningRequest;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/popup/SamsungScreenPinningRequest$1;->this$0:Lcom/android/systemui/popup/SamsungScreenPinningRequest;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 0

    .line 1
    iget-object p1, p0, Lcom/android/systemui/popup/SamsungScreenPinningRequest$1;->this$0:Lcom/android/systemui/popup/SamsungScreenPinningRequest;

    .line 2
    .line 3
    iget-object p1, p1, Lcom/android/systemui/popup/SamsungScreenPinningRequest;->mDialog:Landroid/app/AlertDialog;

    .line 4
    .line 5
    if-eqz p1, :cond_0

    .line 6
    .line 7
    invoke-virtual {p1}, Landroid/app/AlertDialog;->isShowing()Z

    .line 8
    .line 9
    .line 10
    move-result p1

    .line 11
    if-eqz p1, :cond_0

    .line 12
    .line 13
    iget-object p0, p0, Lcom/android/systemui/popup/SamsungScreenPinningRequest$1;->this$0:Lcom/android/systemui/popup/SamsungScreenPinningRequest;

    .line 14
    .line 15
    invoke-virtual {p0}, Lcom/android/systemui/popup/SamsungScreenPinningRequest;->clearPrompt()V

    .line 16
    .line 17
    .line 18
    :cond_0
    return-void
.end method
.class public final Lcom/android/wm/shell/common/MultiWindowOverheatUI$1;
.super Landroid/content/BroadcastReceiver;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/wm/shell/common/MultiWindowOverheatUI;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/common/MultiWindowOverheatUI;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/common/MultiWindowOverheatUI$1;->this$0:Lcom/android/wm/shell/common/MultiWindowOverheatUI;

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
    iget-object p0, p0, Lcom/android/wm/shell/common/MultiWindowOverheatUI$1;->this$0:Lcom/android/wm/shell/common/MultiWindowOverheatUI;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/app/AlertDialog;->dismiss()V

    .line 4
    .line 5
    .line 6
    return-void
.end method
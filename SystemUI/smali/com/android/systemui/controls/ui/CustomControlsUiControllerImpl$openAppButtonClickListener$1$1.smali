.class public final Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$openAppButtonClickListener$1$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/plugins/ActivityStarter$OnDismissAction;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$openAppButtonClickListener$1$1;->this$0:Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onDismiss()Z
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$openAppButtonClickListener$1$1;->this$0:Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl;->launchingPendingIntent:Landroid/app/PendingIntent;

    .line 4
    .line 5
    if-eqz p0, :cond_0

    .line 6
    .line 7
    invoke-virtual {p0}, Landroid/app/PendingIntent;->send()V

    .line 8
    .line 9
    .line 10
    :cond_0
    const/4 p0, 0x1

    .line 11
    return p0
.end method

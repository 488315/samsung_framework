.class final Lcom/android/systemui/uithreadmonitor/UiThreadMonitor$display$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;-><init>(Landroid/os/Handler;Landroid/os/Handler;Landroid/hardware/display/DisplayManager;Lcom/android/systemui/dump/DumpManager;Lcom/android/systemui/uithreadmonitor/LooperSlowLogController;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/uithreadmonitor/UiThreadMonitor$display$2;->this$0:Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/uithreadmonitor/UiThreadMonitor$display$2;->this$0:Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/uithreadmonitor/UiThreadMonitor;->displayManager:Landroid/hardware/display/DisplayManager;

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    invoke-virtual {p0, v0}, Landroid/hardware/display/DisplayManager;->getDisplay(I)Landroid/view/Display;

    .line 7
    .line 8
    .line 9
    move-result-object p0

    .line 10
    return-object p0
.end method

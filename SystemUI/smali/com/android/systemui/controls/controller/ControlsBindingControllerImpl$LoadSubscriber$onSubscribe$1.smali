.class final Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl;

.field final synthetic this$1:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl;Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1;->this$0:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1;->this$1:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber;

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1;->this$0:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl;->currentProvider:Lcom/android/systemui/controls/controller/ControlsProviderLifecycleManager;

    .line 4
    .line 5
    if-eqz v0, :cond_1

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1;->this$1:Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber;->subscription:Landroid/service/controls/IControlsSubscription;

    .line 10
    .line 11
    if-nez p0, :cond_0

    .line 12
    .line 13
    const/4 p0, 0x0

    .line 14
    :cond_0
    invoke-virtual {v0, p0}, Lcom/android/systemui/controls/controller/ControlsProviderLifecycleManager;->cancelSubscription(Landroid/service/controls/IControlsSubscription;)V

    .line 15
    .line 16
    .line 17
    :cond_1
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 18
    .line 19
    return-object p0
.end method

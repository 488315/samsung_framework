.class final Lcom/android/systemui/controls/controller/StatefulControlSubscriber$onSubscribe$1;
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
.field final synthetic $subs:Landroid/service/controls/IControlsSubscription;

.field final synthetic this$0:Lcom/android/systemui/controls/controller/StatefulControlSubscriber;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/controller/StatefulControlSubscriber;Landroid/service/controls/IControlsSubscription;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber$onSubscribe$1;->this$0:Lcom/android/systemui/controls/controller/StatefulControlSubscriber;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber$onSubscribe$1;->$subs:Landroid/service/controls/IControlsSubscription;

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
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber$onSubscribe$1;->this$0:Lcom/android/systemui/controls/controller/StatefulControlSubscriber;

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    iput-boolean v1, v0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber;->subscriptionOpen:Z

    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber$onSubscribe$1;->$subs:Landroid/service/controls/IControlsSubscription;

    .line 7
    .line 8
    iput-object p0, v0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber;->subscription:Landroid/service/controls/IControlsSubscription;

    .line 9
    .line 10
    iget-object v1, v0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber;->provider:Lcom/android/systemui/controls/controller/ControlsProviderLifecycleManager;

    .line 11
    .line 12
    iget-wide v2, v0, Lcom/android/systemui/controls/controller/StatefulControlSubscriber;->requestLimit:J

    .line 13
    .line 14
    invoke-virtual {v1, p0, v2, v3}, Lcom/android/systemui/controls/controller/ControlsProviderLifecycleManager;->startSubscription(Landroid/service/controls/IControlsSubscription;J)V

    .line 15
    .line 16
    .line 17
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 18
    .line 19
    return-object p0
.end method

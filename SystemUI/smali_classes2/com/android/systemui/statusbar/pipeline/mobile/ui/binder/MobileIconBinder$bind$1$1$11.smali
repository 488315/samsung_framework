.class final Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/coroutines/jvm/internal/SuspendLambda;",
        "Lkotlin/jvm/functions/Function2;"
    }
.end annotation

.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$11"
    f = "MobileIconBinder.kt"
    l = {
        0xef
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $activityIn:Landroid/widget/ImageView;

.field final synthetic $activityOut:Landroid/widget/ImageView;

.field final synthetic $dataActivity:Landroid/widget/ImageView;

.field final synthetic $dotView:Lcom/android/systemui/statusbar/StatusBarIconView;

.field final synthetic $iconTint:Lkotlinx/coroutines/flow/MutableStateFlow;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lkotlinx/coroutines/flow/MutableStateFlow;"
        }
    .end annotation
.end field

.field final synthetic $iconView:Landroid/widget/ImageView;

.field final synthetic $networkTypeView:Landroid/widget/ImageView;

.field final synthetic $roamingView:Landroid/widget/ImageView;

.field final synthetic $voiceNoService:Landroid/widget/ImageView;

.field label:I


# direct methods
.method public constructor <init>(Lkotlinx/coroutines/flow/MutableStateFlow;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Lcom/android/systemui/statusbar/StatusBarIconView;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlinx/coroutines/flow/MutableStateFlow;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Landroid/widget/ImageView;",
            "Lcom/android/systemui/statusbar/StatusBarIconView;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconTint:Lkotlinx/coroutines/flow/MutableStateFlow;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconView:Landroid/widget/ImageView;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$networkTypeView:Landroid/widget/ImageView;

    .line 6
    .line 7
    iput-object p4, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$roamingView:Landroid/widget/ImageView;

    .line 8
    .line 9
    iput-object p5, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityIn:Landroid/widget/ImageView;

    .line 10
    .line 11
    iput-object p6, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityOut:Landroid/widget/ImageView;

    .line 12
    .line 13
    iput-object p7, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dataActivity:Landroid/widget/ImageView;

    .line 14
    .line 15
    iput-object p8, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$voiceNoService:Landroid/widget/ImageView;

    .line 16
    .line 17
    iput-object p9, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dotView:Lcom/android/systemui/statusbar/StatusBarIconView;

    .line 18
    .line 19
    const/4 p1, 0x2

    .line 20
    invoke-direct {p0, p1, p10}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 21
    .line 22
    .line 23
    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .locals 11

    .line 1
    new-instance p1, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconTint:Lkotlinx/coroutines/flow/MutableStateFlow;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconView:Landroid/widget/ImageView;

    .line 6
    .line 7
    iget-object v3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$networkTypeView:Landroid/widget/ImageView;

    .line 8
    .line 9
    iget-object v4, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$roamingView:Landroid/widget/ImageView;

    .line 10
    .line 11
    iget-object v5, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityIn:Landroid/widget/ImageView;

    .line 12
    .line 13
    iget-object v6, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityOut:Landroid/widget/ImageView;

    .line 14
    .line 15
    iget-object v7, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dataActivity:Landroid/widget/ImageView;

    .line 16
    .line 17
    iget-object v8, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$voiceNoService:Landroid/widget/ImageView;

    .line 18
    .line 19
    iget-object v9, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dotView:Lcom/android/systemui/statusbar/StatusBarIconView;

    .line 20
    .line 21
    move-object v0, p1

    .line 22
    move-object v10, p2

    .line 23
    invoke-direct/range {v0 .. v10}, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;-><init>(Lkotlinx/coroutines/flow/MutableStateFlow;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Lcom/android/systemui/statusbar/StatusBarIconView;Lkotlin/coroutines/Continuation;)V

    .line 24
    .line 25
    .line 26
    return-object p1
.end method

.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    .line 2
    .line 3
    check-cast p2, Lkotlin/coroutines/Continuation;

    .line 4
    .line 5
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 12

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->label:I

    .line 4
    .line 5
    const/4 v2, 0x1

    .line 6
    if-eqz v1, :cond_1

    .line 7
    .line 8
    if-eq v1, v2, :cond_0

    .line 9
    .line 10
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 11
    .line 12
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 13
    .line 14
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 15
    .line 16
    .line 17
    throw p0

    .line 18
    :cond_0
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 19
    .line 20
    .line 21
    goto :goto_0

    .line 22
    :cond_1
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 23
    .line 24
    .line 25
    iget-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconTint:Lkotlinx/coroutines/flow/MutableStateFlow;

    .line 26
    .line 27
    new-instance v1, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11$1;

    .line 28
    .line 29
    iget-object v4, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$iconView:Landroid/widget/ImageView;

    .line 30
    .line 31
    iget-object v5, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$networkTypeView:Landroid/widget/ImageView;

    .line 32
    .line 33
    iget-object v6, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$roamingView:Landroid/widget/ImageView;

    .line 34
    .line 35
    iget-object v7, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityIn:Landroid/widget/ImageView;

    .line 36
    .line 37
    iget-object v8, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$activityOut:Landroid/widget/ImageView;

    .line 38
    .line 39
    iget-object v9, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dataActivity:Landroid/widget/ImageView;

    .line 40
    .line 41
    iget-object v10, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$voiceNoService:Landroid/widget/ImageView;

    .line 42
    .line 43
    iget-object v11, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->$dotView:Lcom/android/systemui/statusbar/StatusBarIconView;

    .line 44
    .line 45
    move-object v3, v1

    .line 46
    invoke-direct/range {v3 .. v11}, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11$1;-><init>(Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/ImageView;Lcom/android/systemui/statusbar/StatusBarIconView;)V

    .line 47
    .line 48
    .line 49
    iput v2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/ui/binder/MobileIconBinder$bind$1$1$11;->label:I

    .line 50
    .line 51
    check-cast p1, Lkotlinx/coroutines/flow/StateFlowImpl;

    .line 52
    .line 53
    invoke-virtual {p1, v1, p0}, Lkotlinx/coroutines/flow/StateFlowImpl;->collect(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 54
    .line 55
    .line 56
    move-result-object p0

    .line 57
    if-ne p0, v0, :cond_2

    .line 58
    .line 59
    return-object v0

    .line 60
    :cond_2
    :goto_0
    new-instance p0, Lkotlin/KotlinNothingValueException;

    .line 61
    .line 62
    invoke-direct {p0}, Lkotlin/KotlinNothingValueException;-><init>()V

    .line 63
    .line 64
    .line 65
    throw p0
.end method
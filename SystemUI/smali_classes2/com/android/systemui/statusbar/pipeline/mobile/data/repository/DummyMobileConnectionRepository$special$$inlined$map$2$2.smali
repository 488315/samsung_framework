.class public final Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlinx/coroutines/flow/FlowCollector;


# instance fields
.field public final synthetic $logger$inlined:Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;

.field public final synthetic $this_unsafeFlow:Lkotlinx/coroutines/flow/FlowCollector;

.field public final synthetic this$0:Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;


# direct methods
.method public constructor <init>(Lkotlinx/coroutines/flow/FlowCollector;Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->$this_unsafeFlow:Lkotlinx/coroutines/flow/FlowCollector;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->$logger$inlined:Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->this$0:Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;

    .line 6
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 8
    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .locals 4

    .line 1
    instance-of v0, p2, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    move-object v0, p2

    .line 6
    check-cast v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;

    .line 7
    .line 8
    iget v1, v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;->label:I

    .line 9
    .line 10
    const/high16 v2, -0x80000000

    .line 11
    .line 12
    and-int v3, v1, v2

    .line 13
    .line 14
    if-eqz v3, :cond_0

    .line 15
    .line 16
    sub-int/2addr v1, v2

    .line 17
    iput v1, v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;->label:I

    .line 18
    .line 19
    goto :goto_0

    .line 20
    :cond_0
    new-instance v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;

    .line 21
    .line 22
    invoke-direct {v0, p0, p2}, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;-><init>(Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;Lkotlin/coroutines/Continuation;)V

    .line 23
    .line 24
    .line 25
    :goto_0
    iget-object p2, v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;->result:Ljava/lang/Object;

    .line 26
    .line 27
    sget-object v1, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 28
    .line 29
    iget v2, v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;->label:I

    .line 30
    .line 31
    const/4 v3, 0x1

    .line 32
    if-eqz v2, :cond_2

    .line 33
    .line 34
    if-ne v2, v3, :cond_1

    .line 35
    .line 36
    invoke-static {p2}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 37
    .line 38
    .line 39
    goto :goto_1

    .line 40
    :cond_1
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 41
    .line 42
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 43
    .line 44
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 45
    .line 46
    .line 47
    throw p0

    .line 48
    :cond_2
    invoke-static {p2}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 49
    .line 50
    .line 51
    check-cast p1, Landroid/content/Intent;

    .line 52
    .line 53
    invoke-virtual {p1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    .line 54
    .line 55
    .line 56
    move-result-object p1

    .line 57
    invoke-static {p1}, Landroid/telephony/SignalStrength;->newFromBundle(Landroid/os/Bundle;)Landroid/telephony/SignalStrength;

    .line 58
    .line 59
    .line 60
    move-result-object p1

    .line 61
    iget-object p2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->this$0:Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;

    .line 62
    .line 63
    iget p2, p2, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;->subId:I

    .line 64
    .line 65
    iget-object v2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->$logger$inlined:Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;

    .line 66
    .line 67
    invoke-virtual {v2, p1, p2}, Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;->logOnSignalStrengthsChanged(Landroid/telephony/SignalStrength;I)V

    .line 68
    .line 69
    .line 70
    iput v3, v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2$1;->label:I

    .line 71
    .line 72
    iget-object p0, p0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$special$$inlined$map$2$2;->$this_unsafeFlow:Lkotlinx/coroutines/flow/FlowCollector;

    .line 73
    .line 74
    invoke-interface {p0, p1, v0}, Lkotlinx/coroutines/flow/FlowCollector;->emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 75
    .line 76
    .line 77
    move-result-object p0

    .line 78
    if-ne p0, v1, :cond_3

    .line 79
    .line 80
    return-object v1

    .line 81
    :cond_3
    :goto_1
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 82
    .line 83
    return-object p0
.end method
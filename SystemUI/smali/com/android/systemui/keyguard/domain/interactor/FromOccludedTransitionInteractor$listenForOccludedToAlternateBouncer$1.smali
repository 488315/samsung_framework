.class final Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;
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
    c = "com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1"
    f = "FromOccludedTransitionInteractor.kt"
    l = {
        0xb4
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field label:I

.field final synthetic this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

    .line 2
    .line 3
    const/4 p1, 0x2

    .line 4
    invoke-direct {p0, p1, p2}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .locals 0

    .line 1
    new-instance p1, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

    .line 4
    .line 5
    invoke-direct {p1, p0, p2}, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;-><init>(Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;Lkotlin/coroutines/Continuation;)V

    .line 6
    .line 7
    .line 8
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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 4

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->label:I

    .line 4
    .line 5
    const/4 v2, 0x1

    .line 6
    if-eqz v1, :cond_1

    .line 7
    .line 8
    if-ne v1, v2, :cond_0

    .line 9
    .line 10
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 11
    .line 12
    .line 13
    goto :goto_0

    .line 14
    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 15
    .line 16
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 17
    .line 18
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 19
    .line 20
    .line 21
    throw p0

    .line 22
    :cond_1
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 23
    .line 24
    .line 25
    iget-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

    .line 26
    .line 27
    iget-object v1, p1, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;->keyguardInteractor:Lcom/android/systemui/keyguard/domain/interactor/KeyguardInteractor;

    .line 28
    .line 29
    iget-object v1, v1, Lcom/android/systemui/keyguard/domain/interactor/KeyguardInteractor;->alternateBouncerShowing:Lkotlinx/coroutines/flow/StateFlow;

    .line 30
    .line 31
    iget-object p1, p1, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;->keyguardTransitionInteractor:Lcom/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor;

    .line 32
    .line 33
    iget-object p1, p1, Lcom/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor;->startedKeyguardTransitionStep:Lcom/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$4;

    .line 34
    .line 35
    sget-object v3, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1$2;->INSTANCE:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1$2;

    .line 36
    .line 37
    invoke-static {v1, p1, v3}, Lcom/android/systemui/util/kotlin/FlowKt;->sample(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/SafeFlow;

    .line 38
    .line 39
    .line 40
    move-result-object p1

    .line 41
    new-instance v1, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1$3;

    .line 42
    .line 43
    iget-object v3, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

    .line 44
    .line 45
    invoke-direct {v1, v3}, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1$3;-><init>(Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;)V

    .line 46
    .line 47
    .line 48
    iput v2, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1;->label:I

    .line 49
    .line 50
    invoke-virtual {p1, v1, p0}, Lkotlinx/coroutines/flow/AbstractFlow;->collect(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 51
    .line 52
    .line 53
    move-result-object p0

    .line 54
    if-ne p0, v0, :cond_2

    .line 55
    .line 56
    return-object v0

    .line 57
    :cond_2
    :goto_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 58
    .line 59
    return-object p0
.end method

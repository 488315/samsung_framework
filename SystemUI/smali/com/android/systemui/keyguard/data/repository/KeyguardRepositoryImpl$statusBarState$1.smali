.class final Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;-><init>(Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/keyguard/WakefulnessLifecycle;Lcom/android/systemui/statusbar/phone/BiometricUnlockController;Lcom/android/systemui/statusbar/policy/KeyguardStateController;Lcom/android/keyguard/KeyguardUpdateMonitor;Lcom/android/systemui/doze/DozeTransitionListener;Lcom/android/systemui/statusbar/phone/DozeParameters;Lcom/android/systemui/biometrics/AuthController;Lcom/android/systemui/dreams/DreamOverlayCallbackController;Lkotlinx/coroutines/CoroutineDispatcher;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/coroutines/jvm/internal/SuspendLambda;",
        "Lkotlin/jvm/functions/Function2;"
    }
.end annotation

.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1"
    f = "KeyguardRepository.kt"
    l = {
        0x1cc
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

.field private synthetic L$0:Ljava/lang/Object;

.field label:I

.field final synthetic this$0:Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;


# direct methods
.method public constructor <init>(Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/plugins/statusbar/StatusBarStateController;",
            "Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->$statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->this$0:Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;

    .line 4
    .line 5
    const/4 p1, 0x2

    .line 6
    invoke-direct {p0, p1, p3}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .locals 2

    .line 1
    new-instance v0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->$statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->this$0:Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;

    .line 6
    .line 7
    invoke-direct {v0, v1, p0, p2}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;-><init>(Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;Lkotlin/coroutines/Continuation;)V

    .line 8
    .line 9
    .line 10
    iput-object p1, v0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->L$0:Ljava/lang/Object;

    .line 11
    .line 12
    return-object v0
.end method

.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/channels/ProducerScope;

    .line 2
    .line 3
    check-cast p2, Lkotlin/coroutines/Continuation;

    .line 4
    .line 5
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 6

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->label:I

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
    iget-object p1, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->L$0:Ljava/lang/Object;

    .line 26
    .line 27
    check-cast p1, Lkotlinx/coroutines/channels/ProducerScope;

    .line 28
    .line 29
    new-instance v1, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1$callback$1;

    .line 30
    .line 31
    iget-object v3, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->this$0:Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;

    .line 32
    .line 33
    invoke-direct {v1, p1, v3}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1$callback$1;-><init>(Lkotlinx/coroutines/channels/ProducerScope;Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;)V

    .line 34
    .line 35
    .line 36
    iget-object v3, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->$statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 37
    .line 38
    invoke-interface {v3, v1}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->addCallback(Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;)V

    .line 39
    .line 40
    .line 41
    sget-object v3, Lcom/android/systemui/common/coroutine/ChannelExt;->INSTANCE:Lcom/android/systemui/common/coroutine/ChannelExt;

    .line 42
    .line 43
    iget-object v4, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->this$0:Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;

    .line 44
    .line 45
    iget-object v5, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->$statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 46
    .line 47
    invoke-interface {v5}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getState()I

    .line 48
    .line 49
    .line 50
    move-result v5

    .line 51
    invoke-static {v4, v5}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;->access$statusBarStateIntToObject(Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl;I)Lcom/android/systemui/keyguard/shared/model/StatusBarState;

    .line 52
    .line 53
    .line 54
    move-result-object v4

    .line 55
    invoke-virtual {v3}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 56
    .line 57
    .line 58
    const-string v3, "KeyguardRepositoryImpl"

    .line 59
    .line 60
    const-string v5, "initial state"

    .line 61
    .line 62
    invoke-static {p1, v4, v3, v5}, Lcom/android/systemui/common/coroutine/ChannelExt;->trySendWithFailureLogging(Lkotlinx/coroutines/channels/SendChannel;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V

    .line 63
    .line 64
    .line 65
    new-instance v3, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1$1;

    .line 66
    .line 67
    iget-object v4, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->$statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 68
    .line 69
    invoke-direct {v3, v4, v1}, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1$1;-><init>(Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1$callback$1;)V

    .line 70
    .line 71
    .line 72
    iput v2, p0, Lcom/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1;->label:I

    .line 73
    .line 74
    invoke-static {p1, v3, p0}, Lkotlinx/coroutines/channels/ProduceKt;->awaitClose(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 75
    .line 76
    .line 77
    move-result-object p0

    .line 78
    if-ne p0, v0, :cond_2

    .line 79
    .line 80
    return-object v0

    .line 81
    :cond_2
    :goto_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 82
    .line 83
    return-object p0
.end method

.class final Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function3;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl;-><init>(Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/MobileConnectionsRepository;Lcom/android/systemui/util/CarrierConfigTracker;Lcom/android/systemui/log/table/TableLogBuffer;Lcom/android/systemui/statusbar/pipeline/shared/data/repository/ConnectivityRepository;Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/UserSetupRepository;Lcom/android/systemui/statusbar/pipeline/wifi/data/repository/WifiRepository;Lkotlinx/coroutines/CoroutineScope;Landroid/content/Context;Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileDataIconResource;Lcom/android/systemui/statusbar/pipeline/mobile/ui/util/MobileSignalIconResource;Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileRoamingIconResource;Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileDisabledDataIconResource;Lcom/android/systemui/statusbar/pipeline/mobile/util/MobileMappingsProxy;Lcom/android/systemui/util/SettingsHelper;Landroid/os/Handler;Lcom/android/systemui/statusbar/pipeline/carrier/CarrierInfraMediator;Lcom/android/systemui/statusbar/pipeline/mobile/data/model/SubscriptionsOrder;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/coroutines/jvm/internal/SuspendLambda;",
        "Lkotlin/jvm/functions/Function3;"
    }
.end annotation

.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$forcingCellularValidation$2"
    f = "MobileIconsInteractor.kt"
    l = {
        0x10b,
        0x10c,
        0x10d
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field private synthetic L$0:Ljava/lang/Object;

.field label:I


# direct methods
.method public constructor <init>(Lkotlin/coroutines/Continuation;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;",
            ">;)V"
        }
    .end annotation

    .line 1
    const/4 v0, 0x3

    .line 2
    invoke-direct {p0, v0, p1}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/flow/FlowCollector;

    .line 2
    .line 3
    check-cast p2, Lkotlin/Unit;

    .line 4
    .line 5
    check-cast p3, Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    new-instance p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;

    .line 8
    .line 9
    invoke-direct {p0, p3}, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;-><init>(Lkotlin/coroutines/Continuation;)V

    .line 10
    .line 11
    .line 12
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 13
    .line 14
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 15
    .line 16
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 17
    .line 18
    .line 19
    move-result-object p0

    .line 20
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 5

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->label:I

    .line 4
    .line 5
    const/4 v2, 0x3

    .line 6
    const/4 v3, 0x2

    .line 7
    const/4 v4, 0x1

    .line 8
    if-eqz v1, :cond_3

    .line 9
    .line 10
    if-eq v1, v4, :cond_2

    .line 11
    .line 12
    if-eq v1, v3, :cond_1

    .line 13
    .line 14
    if-ne v1, v2, :cond_0

    .line 15
    .line 16
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 17
    .line 18
    .line 19
    goto :goto_2

    .line 20
    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 21
    .line 22
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 23
    .line 24
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 25
    .line 26
    .line 27
    throw p0

    .line 28
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 29
    .line 30
    check-cast v1, Lkotlinx/coroutines/flow/FlowCollector;

    .line 31
    .line 32
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 33
    .line 34
    .line 35
    goto :goto_1

    .line 36
    :cond_2
    iget-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 37
    .line 38
    check-cast v1, Lkotlinx/coroutines/flow/FlowCollector;

    .line 39
    .line 40
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 41
    .line 42
    .line 43
    goto :goto_0

    .line 44
    :cond_3
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 45
    .line 46
    .line 47
    iget-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 48
    .line 49
    check-cast p1, Lkotlinx/coroutines/flow/FlowCollector;

    .line 50
    .line 51
    sget-object v1, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    .line 52
    .line 53
    iput-object p1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 54
    .line 55
    iput v4, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->label:I

    .line 56
    .line 57
    invoke-interface {p1, v1, p0}, Lkotlinx/coroutines/flow/FlowCollector;->emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 58
    .line 59
    .line 60
    move-result-object v1

    .line 61
    if-ne v1, v0, :cond_4

    .line 62
    .line 63
    return-object v0

    .line 64
    :cond_4
    move-object v1, p1

    .line 65
    :goto_0
    iput-object v1, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 66
    .line 67
    iput v3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->label:I

    .line 68
    .line 69
    const-wide/16 v3, 0x7d0

    .line 70
    .line 71
    invoke-static {v3, v4, p0}, Lkotlinx/coroutines/DelayKt;->delay(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 72
    .line 73
    .line 74
    move-result-object p1

    .line 75
    if-ne p1, v0, :cond_5

    .line 76
    .line 77
    return-object v0

    .line 78
    :cond_5
    :goto_1
    sget-object p1, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;

    .line 79
    .line 80
    const/4 v3, 0x0

    .line 81
    iput-object v3, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->L$0:Ljava/lang/Object;

    .line 82
    .line 83
    iput v2, p0, Lcom/android/systemui/statusbar/pipeline/mobile/domain/interactor/MobileIconsInteractorImpl$forcingCellularValidation$2;->label:I

    .line 84
    .line 85
    invoke-interface {v1, p1, p0}, Lkotlinx/coroutines/flow/FlowCollector;->emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 86
    .line 87
    .line 88
    move-result-object p0

    .line 89
    if-ne p0, v0, :cond_6

    .line 90
    .line 91
    return-object v0

    .line 92
    :cond_6
    :goto_2
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 93
    .line 94
    return-object p0
.end method
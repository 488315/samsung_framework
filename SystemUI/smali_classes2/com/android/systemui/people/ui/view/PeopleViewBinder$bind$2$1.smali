.class final Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;
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
    c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1"
    f = "PeopleViewBinder.kt"
    l = {
        0x62
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $view:Landroid/view/ViewGroup;

.field final synthetic $viewModel:Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;

.field label:I


# direct methods
.method public constructor <init>(Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Landroid/view/ViewGroup;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;",
            "Landroid/view/ViewGroup;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$viewModel:Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$view:Landroid/view/ViewGroup;

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
    .locals 1

    .line 1
    new-instance p1, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;

    .line 2
    .line 3
    iget-object v0, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$viewModel:Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$view:Landroid/view/ViewGroup;

    .line 6
    .line 7
    invoke-direct {p1, v0, p0, p2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;-><init>(Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Landroid/view/ViewGroup;Lkotlin/coroutines/Continuation;)V

    .line 8
    .line 9
    .line 10
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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 5

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->label:I

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
    iget-object p1, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$viewModel:Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;

    .line 26
    .line 27
    iget-object v1, p1, Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;->priorityTiles:Lkotlinx/coroutines/flow/ReadonlyStateFlow;

    .line 28
    .line 29
    new-instance v3, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$1;

    .line 30
    .line 31
    const/4 v4, 0x0

    .line 32
    invoke-direct {v3, v4}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$1;-><init>(Lkotlin/coroutines/Continuation;)V

    .line 33
    .line 34
    .line 35
    new-instance v4, Lkotlinx/coroutines/flow/FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

    .line 36
    .line 37
    iget-object p1, p1, Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;->recentTiles:Lkotlinx/coroutines/flow/ReadonlyStateFlow;

    .line 38
    .line 39
    invoke-direct {v4, v1, p1, v3}, Lkotlinx/coroutines/flow/FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;-><init>(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)V

    .line 40
    .line 41
    .line 42
    new-instance p1, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$2;

    .line 43
    .line 44
    iget-object v1, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$view:Landroid/view/ViewGroup;

    .line 45
    .line 46
    iget-object v3, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->$viewModel:Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;

    .line 47
    .line 48
    invoke-direct {p1, v1, v3}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$2;-><init>(Landroid/view/ViewGroup;Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;)V

    .line 49
    .line 50
    .line 51
    iput v2, p0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1;->label:I

    .line 52
    .line 53
    invoke-virtual {v4, p1, p0}, Lkotlinx/coroutines/flow/FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;->collect(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

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
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 61
    .line 62
    return-object p0
.end method

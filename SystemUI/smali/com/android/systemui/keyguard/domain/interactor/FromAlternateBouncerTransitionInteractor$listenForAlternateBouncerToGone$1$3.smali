.class public final Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1$3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlinx/coroutines/flow/FlowCollector;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1$3;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .locals 3

    .line 1
    check-cast p1, Lkotlin/Pair;

    .line 2
    .line 3
    invoke-virtual {p1}, Lkotlin/Pair;->component1()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p2

    .line 7
    check-cast p2, Ljava/lang/Boolean;

    .line 8
    .line 9
    invoke-virtual {p2}, Ljava/lang/Boolean;->booleanValue()Z

    .line 10
    .line 11
    .line 12
    move-result p2

    .line 13
    invoke-virtual {p1}, Lkotlin/Pair;->component2()Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p1

    .line 17
    check-cast p1, Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 18
    .line 19
    if-eqz p2, :cond_0

    .line 20
    .line 21
    sget-object p2, Lcom/android/systemui/keyguard/shared/model/KeyguardState;->ALTERNATE_BOUNCER:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 22
    .line 23
    if-ne p1, p2, :cond_0

    .line 24
    .line 25
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1$3;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;

    .line 26
    .line 27
    iget-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;->keyguardTransitionRepository:Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository;

    .line 28
    .line 29
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/TransitionInfo;

    .line 30
    .line 31
    sget-object v1, Lcom/android/systemui/keyguard/shared/model/KeyguardState;->GONE:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 32
    .line 33
    invoke-static {p0}, Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;->access$getAnimator(Lcom/android/systemui/keyguard/domain/interactor/FromAlternateBouncerTransitionInteractor;)Landroid/animation/ValueAnimator;

    .line 34
    .line 35
    .line 36
    move-result-object v2

    .line 37
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/TransitionInteractor;->name:Ljava/lang/String;

    .line 38
    .line 39
    invoke-direct {v0, p0, p2, v1, v2}, Lcom/android/systemui/keyguard/shared/model/TransitionInfo;-><init>(Ljava/lang/String;Lcom/android/systemui/keyguard/shared/model/KeyguardState;Lcom/android/systemui/keyguard/shared/model/KeyguardState;Landroid/animation/ValueAnimator;)V

    .line 40
    .line 41
    .line 42
    invoke-static {p1, v0}, Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository$DefaultImpls;->startTransition$default(Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository;Lcom/android/systemui/keyguard/shared/model/TransitionInfo;)Ljava/util/UUID;

    .line 43
    .line 44
    .line 45
    :cond_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 46
    .line 47
    return-object p0
.end method
.class public final Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAodOrDozing$1$4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlinx/coroutines/flow/FlowCollector;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAodOrDozing$1$4;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

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
    .locals 5

    .line 1
    check-cast p1, Lkotlin/Triple;

    .line 2
    .line 3
    invoke-virtual {p1}, Lkotlin/Triple;->component1()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p2

    .line 7
    check-cast p2, Lcom/android/systemui/keyguard/shared/model/WakefulnessModel;

    .line 8
    .line 9
    invoke-virtual {p1}, Lkotlin/Triple;->component2()Ljava/lang/Object;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    check-cast v0, Lcom/android/systemui/keyguard/shared/model/TransitionStep;

    .line 14
    .line 15
    invoke-virtual {p1}, Lkotlin/Triple;->component3()Ljava/lang/Object;

    .line 16
    .line 17
    .line 18
    move-result-object p1

    .line 19
    check-cast p1, Ljava/lang/Boolean;

    .line 20
    .line 21
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    .line 22
    .line 23
    .line 24
    move-result p1

    .line 25
    iget-object v0, v0, Lcom/android/systemui/keyguard/shared/model/TransitionStep;->to:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 26
    .line 27
    sget-object v1, Lcom/android/systemui/keyguard/shared/model/KeyguardState;->OCCLUDED:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 28
    .line 29
    if-ne v0, v1, :cond_1

    .line 30
    .line 31
    iget-object p2, p2, Lcom/android/systemui/keyguard/shared/model/WakefulnessModel;->state:Lcom/android/systemui/keyguard/shared/model/WakefulnessState;

    .line 32
    .line 33
    sget-object v0, Lcom/android/systemui/keyguard/shared/model/WakefulnessState;->STARTING_TO_SLEEP:Lcom/android/systemui/keyguard/shared/model/WakefulnessState;

    .line 34
    .line 35
    if-ne p2, v0, :cond_1

    .line 36
    .line 37
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$listenForOccludedToAodOrDozing$1$4;->this$0:Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;

    .line 38
    .line 39
    iget-object p2, p0, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;->keyguardTransitionRepository:Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository;

    .line 40
    .line 41
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/TransitionInfo;

    .line 42
    .line 43
    if-eqz p1, :cond_0

    .line 44
    .line 45
    sget-object p1, Lcom/android/systemui/keyguard/shared/model/KeyguardState;->AOD:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 46
    .line 47
    goto :goto_0

    .line 48
    :cond_0
    sget-object p1, Lcom/android/systemui/keyguard/shared/model/KeyguardState;->DOZING:Lcom/android/systemui/keyguard/shared/model/KeyguardState;

    .line 49
    .line 50
    :goto_0
    new-instance v2, Landroid/animation/ValueAnimator;

    .line 51
    .line 52
    invoke-direct {v2}, Landroid/animation/ValueAnimator;-><init>()V

    .line 53
    .line 54
    .line 55
    sget-object v3, Lcom/android/app/animation/Interpolators;->LINEAR:Landroid/view/animation/Interpolator;

    .line 56
    .line 57
    invoke-virtual {v2, v3}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 58
    .line 59
    .line 60
    sget-wide v3, Lcom/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor;->DEFAULT_DURATION:J

    .line 61
    .line 62
    invoke-static {v3, v4}, Lkotlin/time/Duration;->getInWholeMilliseconds-impl(J)J

    .line 63
    .line 64
    .line 65
    move-result-wide v3

    .line 66
    invoke-virtual {v2, v3, v4}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 67
    .line 68
    .line 69
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/TransitionInteractor;->name:Ljava/lang/String;

    .line 70
    .line 71
    invoke-direct {v0, p0, v1, p1, v2}, Lcom/android/systemui/keyguard/shared/model/TransitionInfo;-><init>(Ljava/lang/String;Lcom/android/systemui/keyguard/shared/model/KeyguardState;Lcom/android/systemui/keyguard/shared/model/KeyguardState;Landroid/animation/ValueAnimator;)V

    .line 72
    .line 73
    .line 74
    invoke-static {p2, v0}, Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository$DefaultImpls;->startTransition$default(Lcom/android/systemui/keyguard/data/repository/KeyguardTransitionRepository;Lcom/android/systemui/keyguard/shared/model/TransitionInfo;)Ljava/util/UUID;

    .line 75
    .line 76
    .line 77
    :cond_1
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 78
    .line 79
    return-object p0
.end method

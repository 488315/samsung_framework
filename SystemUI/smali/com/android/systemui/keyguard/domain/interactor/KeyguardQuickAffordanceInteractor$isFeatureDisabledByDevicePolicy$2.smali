.class final Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;
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
    c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2"
    f = "KeyguardQuickAffordanceInteractor.kt"
    l = {}
    m = "invokeSuspend"
.end annotation


# instance fields
.field label:I

.field final synthetic this$0:Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->this$0:Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;

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
    new-instance p1, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->this$0:Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;

    .line 4
    .line 5
    invoke-direct {p1, p0, p2}, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;-><init>(Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;Lkotlin/coroutines/Continuation;)V

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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v0, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->label:I

    .line 4
    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 8
    .line 9
    .line 10
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2;->this$0:Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;

    .line 11
    .line 12
    iget-object p1, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;->devicePolicyManager:Landroid/app/admin/DevicePolicyManager;

    .line 13
    .line 14
    iget-object p0, p0, Lcom/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor;->userTracker:Lcom/android/systemui/settings/UserTracker;

    .line 15
    .line 16
    check-cast p0, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 17
    .line 18
    invoke-virtual {p0}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 19
    .line 20
    .line 21
    move-result p0

    .line 22
    invoke-static {p1, p0}, Lcom/android/systemui/devicepolicy/DevicePolicyManagerExtKt;->areKeyguardShortcutsDisabled$default(Landroid/app/admin/DevicePolicyManager;I)Z

    .line 23
    .line 24
    .line 25
    move-result p0

    .line 26
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 27
    .line 28
    .line 29
    move-result-object p0

    .line 30
    return-object p0

    .line 31
    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 32
    .line 33
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 34
    .line 35
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 36
    .line 37
    .line 38
    throw p0
.end method
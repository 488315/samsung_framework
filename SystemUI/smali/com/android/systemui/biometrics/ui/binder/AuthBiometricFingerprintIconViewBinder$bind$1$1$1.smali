.class final Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;
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
    c = "com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1$1"
    f = "AuthBiometricFingerprintIconViewBinder.kt"
    l = {
        0x2b
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $view:Lcom/airbnb/lottie/LottieAnimationView;

.field final synthetic $viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

.field label:I


# direct methods
.method public constructor <init>(Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;Lcom/airbnb/lottie/LottieAnimationView;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;",
            "Lcom/airbnb/lottie/LottieAnimationView;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

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
    new-instance p1, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;

    .line 2
    .line 3
    iget-object v0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 6
    .line 7
    invoke-direct {p1, v0, p0, p2}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;-><init>(Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;Lcom/airbnb/lottie/LottieAnimationView;Lkotlin/coroutines/Continuation;)V

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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

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
    iget v1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->label:I

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
    iget-object p1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 26
    .line 27
    iget-object p1, p1, Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;->iconAsset:Lkotlinx/coroutines/flow/FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

    .line 28
    .line 29
    new-instance v1, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1$1;

    .line 30
    .line 31
    iget-object v3, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 32
    .line 33
    invoke-direct {v1, v3}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1$1;-><init>(Lcom/airbnb/lottie/LottieAnimationView;)V

    .line 34
    .line 35
    .line 36
    iput v2, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;->label:I

    .line 37
    .line 38
    invoke-virtual {p1, v1, p0}, Lkotlinx/coroutines/flow/FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;->collect(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 39
    .line 40
    .line 41
    move-result-object p0

    .line 42
    if-ne p0, v0, :cond_2

    .line 43
    .line 44
    return-object v0

    .line 45
    :cond_2
    :goto_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 46
    .line 47
    return-object p0
.end method

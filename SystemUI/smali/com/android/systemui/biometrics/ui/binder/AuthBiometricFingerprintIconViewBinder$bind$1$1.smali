.class final Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;
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
    c = "com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1"
    f = "AuthBiometricFingerprintIconViewBinder.kt"
    l = {}
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $view:Lcom/airbnb/lottie/LottieAnimationView;

.field final synthetic $viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

.field private synthetic L$0:Ljava/lang/Object;

.field label:I


# direct methods
.method public constructor <init>(Lcom/airbnb/lottie/LottieAnimationView;Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/LottieAnimationView;",
            "Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

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
    new-instance v0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 6
    .line 7
    invoke-direct {v0, v1, p0, p2}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;-><init>(Lcom/airbnb/lottie/LottieAnimationView;Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;Lkotlin/coroutines/Continuation;)V

    .line 8
    .line 9
    .line 10
    iput-object p1, v0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->L$0:Ljava/lang/Object;

    .line 11
    .line 12
    return-object v0
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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 3

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->label:I

    .line 4
    .line 5
    if-nez v0, :cond_1

    .line 6
    .line 7
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 8
    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->L$0:Ljava/lang/Object;

    .line 11
    .line 12
    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    .line 13
    .line 14
    new-instance v0, Landroid/view/DisplayInfo;

    .line 15
    .line 16
    invoke-direct {v0}, Landroid/view/DisplayInfo;-><init>()V

    .line 17
    .line 18
    .line 19
    iget-object v1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 20
    .line 21
    invoke-virtual {v1}, Landroid/widget/ImageView;->getContext()Landroid/content/Context;

    .line 22
    .line 23
    .line 24
    move-result-object v1

    .line 25
    invoke-virtual {v1}, Landroid/content/Context;->getDisplay()Landroid/view/Display;

    .line 26
    .line 27
    .line 28
    move-result-object v1

    .line 29
    if-eqz v1, :cond_0

    .line 30
    .line 31
    invoke-virtual {v1, v0}, Landroid/view/Display;->getDisplayInfo(Landroid/view/DisplayInfo;)Z

    .line 32
    .line 33
    .line 34
    :cond_0
    iget-object v1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 35
    .line 36
    iget v0, v0, Landroid/view/DisplayInfo;->rotation:I

    .line 37
    .line 38
    iput v0, v1, Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;->rotation:I

    .line 39
    .line 40
    iget-object v0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 41
    .line 42
    invoke-virtual {v0}, Landroid/widget/ImageView;->getContext()Landroid/content/Context;

    .line 43
    .line 44
    .line 45
    move-result-object v0

    .line 46
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 47
    .line 48
    .line 49
    move-result-object v0

    .line 50
    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 51
    .line 52
    .line 53
    move-result-object v0

    .line 54
    iget-object v1, v1, Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;->interactor:Lcom/android/systemui/biometrics/domain/interactor/DisplayStateInteractor;

    .line 55
    .line 56
    check-cast v1, Lcom/android/systemui/biometrics/domain/interactor/DisplayStateInteractorImpl;

    .line 57
    .line 58
    iget-object v1, v1, Lcom/android/systemui/biometrics/domain/interactor/DisplayStateInteractorImpl;->screenSizeFoldProvider:Lcom/android/systemui/unfold/compat/ScreenSizeFoldProvider;

    .line 59
    .line 60
    invoke-virtual {v1, v0}, Lcom/android/systemui/unfold/compat/ScreenSizeFoldProvider;->onConfigurationChange(Landroid/content/res/Configuration;)V

    .line 61
    .line 62
    .line 63
    new-instance v0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;

    .line 64
    .line 65
    iget-object v1, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$viewModel:Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;

    .line 66
    .line 67
    iget-object p0, p0, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1;->$view:Lcom/airbnb/lottie/LottieAnimationView;

    .line 68
    .line 69
    const/4 v2, 0x0

    .line 70
    invoke-direct {v0, v1, p0, v2}, Lcom/android/systemui/biometrics/ui/binder/AuthBiometricFingerprintIconViewBinder$bind$1$1$1;-><init>(Lcom/android/systemui/biometrics/ui/viewmodel/AuthBiometricFingerprintViewModel;Lcom/airbnb/lottie/LottieAnimationView;Lkotlin/coroutines/Continuation;)V

    .line 71
    .line 72
    .line 73
    const/4 p0, 0x3

    .line 74
    invoke-static {p1, v2, v2, v0, p0}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;I)Lkotlinx/coroutines/StandaloneCoroutine;

    .line 75
    .line 76
    .line 77
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 78
    .line 79
    return-object p0

    .line 80
    :cond_1
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 81
    .line 82
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 83
    .line 84
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 85
    .line 86
    .line 87
    throw p0
.end method

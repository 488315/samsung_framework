.class final Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;
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
    c = "com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$finalStatus$1"
    f = "PromptCredentialInteractor.kt"
    l = {}
    m = "invokeSuspend"
.end annotation


# instance fields
.field synthetic L$0:Ljava/lang/Object;

.field label:I

.field final synthetic this$0:Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->this$0:Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;

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
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->this$0:Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;

    .line 4
    .line 5
    invoke-direct {v0, p0, p2}, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;-><init>(Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;Lkotlin/coroutines/Continuation;)V

    .line 6
    .line 7
    .line 8
    iput-object p1, v0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->L$0:Ljava/lang/Object;

    .line 9
    .line 10
    return-object v0
.end method

.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lcom/android/systemui/biometrics/domain/interactor/CredentialStatus;

    .line 2
    .line 3
    check-cast p2, Lkotlin/coroutines/Continuation;

    .line 4
    .line 5
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

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
    iget v0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->label:I

    .line 4
    .line 5
    if-nez v0, :cond_2

    .line 6
    .line 7
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 8
    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->L$0:Ljava/lang/Object;

    .line 11
    .line 12
    check-cast p1, Lcom/android/systemui/biometrics/domain/interactor/CredentialStatus;

    .line 13
    .line 14
    instance-of v0, p1, Lcom/android/systemui/biometrics/domain/interactor/CredentialStatus$Success$Verified;

    .line 15
    .line 16
    if-eqz v0, :cond_0

    .line 17
    .line 18
    iget-object p0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->this$0:Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;

    .line 19
    .line 20
    iget-object p0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;->_verificationError:Lkotlinx/coroutines/flow/StateFlowImpl;

    .line 21
    .line 22
    const/4 p1, 0x0

    .line 23
    invoke-virtual {p0, p1}, Lkotlinx/coroutines/flow/StateFlowImpl;->setValue(Ljava/lang/Object;)V

    .line 24
    .line 25
    .line 26
    goto :goto_0

    .line 27
    :cond_0
    instance-of v0, p1, Lcom/android/systemui/biometrics/domain/interactor/CredentialStatus$Fail;

    .line 28
    .line 29
    if-eqz v0, :cond_1

    .line 30
    .line 31
    iget-object p0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor$verifyCredential$finalStatus$1;->this$0:Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;

    .line 32
    .line 33
    iget-object p0, p0, Lcom/android/systemui/biometrics/domain/interactor/PromptCredentialInteractor;->_verificationError:Lkotlinx/coroutines/flow/StateFlowImpl;

    .line 34
    .line 35
    invoke-virtual {p0, p1}, Lkotlinx/coroutines/flow/StateFlowImpl;->setValue(Ljava/lang/Object;)V

    .line 36
    .line 37
    .line 38
    :cond_1
    :goto_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 39
    .line 40
    return-object p0

    .line 41
    :cond_2
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 42
    .line 43
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 44
    .line 45
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 46
    .line 47
    .line 48
    throw p0
.end method
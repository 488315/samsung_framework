.class final Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;
.super Lkotlin/coroutines/jvm/internal/ContinuationImpl;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel"
    f = "PromptViewModel.kt"
    l = {
        0x181,
        0x182,
        0x188
    }
    m = "needsExplicitConfirmation"
.end annotation


# instance fields
.field L$0:Ljava/lang/Object;

.field L$1:Ljava/lang/Object;

.field L$2:Ljava/lang/Object;

.field Z$0:Z

.field label:I

.field synthetic result:Ljava/lang/Object;

.field final synthetic this$0:Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;


# direct methods
.method public constructor <init>(Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;->this$0:Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;

    .line 2
    .line 3
    invoke-direct {p0, p2}, Lkotlin/coroutines/jvm/internal/ContinuationImpl;-><init>(Lkotlin/coroutines/Continuation;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;->result:Ljava/lang/Object;

    .line 2
    .line 3
    iget p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;->label:I

    .line 4
    .line 5
    const/high16 v0, -0x80000000

    .line 6
    .line 7
    or-int/2addr p1, v0

    .line 8
    iput p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;->label:I

    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel$needsExplicitConfirmation$1;->this$0:Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;

    .line 11
    .line 12
    sget v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;->$r8$clinit:I

    .line 13
    .line 14
    const/4 v0, 0x0

    .line 15
    invoke-virtual {p1, v0, p0}, Lcom/android/systemui/biometrics/ui/viewmodel/PromptViewModel;->needsExplicitConfirmation(Lcom/android/systemui/biometrics/domain/model/BiometricModality;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 16
    .line 17
    .line 18
    move-result-object p0

    .line 19
    return-object p0
.end method
.class final Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;
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
    c = "com.android.systemui.screenshot.ActionIntentExecutor$launchIntentAsync$1"
    f = "ActionIntentExecutor.kt"
    l = {
        0x41
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field final synthetic $bundle:Landroid/os/Bundle;

.field final synthetic $intent:Landroid/content/Intent;

.field final synthetic $overrideTransition:Z

.field final synthetic $userId:I

.field label:I

.field final synthetic this$0:Lcom/android/systemui/screenshot/ActionIntentExecutor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/screenshot/ActionIntentExecutor;Landroid/content/Intent;Landroid/os/Bundle;IZLkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/screenshot/ActionIntentExecutor;",
            "Landroid/content/Intent;",
            "Landroid/os/Bundle;",
            "IZ",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->this$0:Lcom/android/systemui/screenshot/ActionIntentExecutor;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$intent:Landroid/content/Intent;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$bundle:Landroid/os/Bundle;

    .line 6
    .line 7
    iput p4, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$userId:I

    .line 8
    .line 9
    iput-boolean p5, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$overrideTransition:Z

    .line 10
    .line 11
    const/4 p1, 0x2

    .line 12
    invoke-direct {p0, p1, p6}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 13
    .line 14
    .line 15
    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .locals 7

    .line 1
    new-instance p1, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->this$0:Lcom/android/systemui/screenshot/ActionIntentExecutor;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$intent:Landroid/content/Intent;

    .line 6
    .line 7
    iget-object v3, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$bundle:Landroid/os/Bundle;

    .line 8
    .line 9
    iget v4, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$userId:I

    .line 10
    .line 11
    iget-boolean v5, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$overrideTransition:Z

    .line 12
    .line 13
    move-object v0, p1

    .line 14
    move-object v6, p2

    .line 15
    invoke-direct/range {v0 .. v6}, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;-><init>(Lcom/android/systemui/screenshot/ActionIntentExecutor;Landroid/content/Intent;Landroid/os/Bundle;IZLkotlin/coroutines/Continuation;)V

    .line 16
    .line 17
    .line 18
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
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 7

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->label:I

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
    iget-object v1, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->this$0:Lcom/android/systemui/screenshot/ActionIntentExecutor;

    .line 26
    .line 27
    iget-object p1, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$intent:Landroid/content/Intent;

    .line 28
    .line 29
    iget-object v3, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$bundle:Landroid/os/Bundle;

    .line 30
    .line 31
    iget v4, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$userId:I

    .line 32
    .line 33
    iget-boolean v5, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->$overrideTransition:Z

    .line 34
    .line 35
    iput v2, p0, Lcom/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1;->label:I

    .line 36
    .line 37
    move-object v2, p1

    .line 38
    move-object v6, p0

    .line 39
    invoke-virtual/range {v1 .. v6}, Lcom/android/systemui/screenshot/ActionIntentExecutor;->launchIntent(Landroid/content/Intent;Landroid/os/Bundle;IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 40
    .line 41
    .line 42
    move-result-object p0

    .line 43
    if-ne p0, v0, :cond_2

    .line 44
    .line 45
    return-object v0

    .line 46
    :cond_2
    :goto_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 47
    .line 48
    return-object p0
.end method

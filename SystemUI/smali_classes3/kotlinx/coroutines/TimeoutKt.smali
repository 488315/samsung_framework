.class public abstract Lkotlinx/coroutines/TimeoutKt;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public static final setupTimeout(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;
    .locals 4

    .line 1
    iget-object v0, p0, Lkotlinx/coroutines/internal/ScopeCoroutine;->uCont:Lkotlin/coroutines/Continuation;

    .line 2
    .line 3
    invoke-interface {v0}, Lkotlin/coroutines/Continuation;->getContext()Lkotlin/coroutines/CoroutineContext;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    invoke-static {v0}, Lkotlinx/coroutines/DelayKt;->getDelay(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    iget-wide v1, p0, Lkotlinx/coroutines/TimeoutCoroutine;->time:J

    .line 12
    .line 13
    iget-object v3, p0, Lkotlinx/coroutines/AbstractCoroutine;->context:Lkotlin/coroutines/CoroutineContext;

    .line 14
    .line 15
    invoke-interface {v0, v1, v2, p0, v3}, Lkotlinx/coroutines/Delay;->invokeOnTimeout(JLjava/lang/Runnable;Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/DisposableHandle;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    new-instance v1, Lkotlinx/coroutines/DisposeOnCompletion;

    .line 20
    .line 21
    invoke-direct {v1, v0}, Lkotlinx/coroutines/DisposeOnCompletion;-><init>(Lkotlinx/coroutines/DisposableHandle;)V

    .line 22
    .line 23
    .line 24
    invoke-virtual {p0, v1}, Lkotlinx/coroutines/JobSupport;->invokeOnCompletion(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;

    .line 25
    .line 26
    .line 27
    const/4 v0, 0x0

    .line 28
    const/4 v1, 0x2

    .line 29
    :try_start_0
    invoke-static {v1, p1}, Lkotlin/jvm/internal/TypeIntrinsics;->beforeCheckcastToFunctionOfArity(ILjava/lang/Object;)V

    .line 30
    .line 31
    .line 32
    invoke-interface {p1, p0, p0}, Lkotlin/jvm/functions/Function2;->invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 33
    .line 34
    .line 35
    move-result-object p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 36
    goto :goto_0

    .line 37
    :catchall_0
    move-exception p1

    .line 38
    new-instance v2, Lkotlinx/coroutines/CompletedExceptionally;

    .line 39
    .line 40
    const/4 v3, 0x0

    .line 41
    invoke-direct {v2, p1, v0, v1, v3}, Lkotlinx/coroutines/CompletedExceptionally;-><init>(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 42
    .line 43
    .line 44
    move-object p1, v2

    .line 45
    :goto_0
    sget-object v1, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 46
    .line 47
    if-ne p1, v1, :cond_0

    .line 48
    .line 49
    goto :goto_2

    .line 50
    :cond_0
    invoke-virtual {p0, p1}, Lkotlinx/coroutines/JobSupport;->makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines(Ljava/lang/Object;)Ljava/lang/Object;

    .line 51
    .line 52
    .line 53
    move-result-object v2

    .line 54
    sget-object v3, Lkotlinx/coroutines/JobSupportKt;->COMPLETING_WAITING_CHILDREN:Lkotlinx/coroutines/internal/Symbol;

    .line 55
    .line 56
    if-ne v2, v3, :cond_1

    .line 57
    .line 58
    goto :goto_2

    .line 59
    :cond_1
    instance-of v1, v2, Lkotlinx/coroutines/CompletedExceptionally;

    .line 60
    .line 61
    if-eqz v1, :cond_6

    .line 62
    .line 63
    check-cast v2, Lkotlinx/coroutines/CompletedExceptionally;

    .line 64
    .line 65
    iget-object v1, v2, Lkotlinx/coroutines/CompletedExceptionally;->cause:Ljava/lang/Throwable;

    .line 66
    .line 67
    instance-of v2, v1, Lkotlinx/coroutines/TimeoutCancellationException;

    .line 68
    .line 69
    if-eqz v2, :cond_2

    .line 70
    .line 71
    move-object v2, v1

    .line 72
    check-cast v2, Lkotlinx/coroutines/TimeoutCancellationException;

    .line 73
    .line 74
    iget-object v2, v2, Lkotlinx/coroutines/TimeoutCancellationException;->coroutine:Lkotlinx/coroutines/Job;

    .line 75
    .line 76
    if-eq v2, p0, :cond_3

    .line 77
    .line 78
    :cond_2
    const/4 v0, 0x1

    .line 79
    :cond_3
    if-nez v0, :cond_5

    .line 80
    .line 81
    instance-of p0, p1, Lkotlinx/coroutines/CompletedExceptionally;

    .line 82
    .line 83
    if-nez p0, :cond_4

    .line 84
    .line 85
    goto :goto_1

    .line 86
    :cond_4
    check-cast p1, Lkotlinx/coroutines/CompletedExceptionally;

    .line 87
    .line 88
    iget-object p0, p1, Lkotlinx/coroutines/CompletedExceptionally;->cause:Ljava/lang/Throwable;

    .line 89
    .line 90
    throw p0

    .line 91
    :cond_5
    throw v1

    .line 92
    :cond_6
    invoke-static {v2}, Lkotlinx/coroutines/JobSupportKt;->unboxState(Ljava/lang/Object;)Ljava/lang/Object;

    .line 93
    .line 94
    .line 95
    move-result-object p1

    .line 96
    :goto_1
    move-object v1, p1

    .line 97
    :goto_2
    return-object v1
.end method

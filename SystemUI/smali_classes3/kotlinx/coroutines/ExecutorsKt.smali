.class public abstract Lkotlinx/coroutines/ExecutorsKt;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public static final asExecutor(Lkotlinx/coroutines/CoroutineDispatcher;)Ljava/util/concurrent/Executor;
    .locals 1

    .line 1
    instance-of v0, p0, Lkotlinx/coroutines/ExecutorCoroutineDispatcher;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    move-object v0, p0

    .line 6
    check-cast v0, Lkotlinx/coroutines/ExecutorCoroutineDispatcher;

    .line 7
    .line 8
    goto :goto_0

    .line 9
    :cond_0
    const/4 v0, 0x0

    .line 10
    :goto_0
    if-eqz v0, :cond_1

    .line 11
    .line 12
    invoke-virtual {v0}, Lkotlinx/coroutines/ExecutorCoroutineDispatcher;->getExecutor()Ljava/util/concurrent/Executor;

    .line 13
    .line 14
    .line 15
    move-result-object v0

    .line 16
    if-nez v0, :cond_2

    .line 17
    .line 18
    :cond_1
    new-instance v0, Lkotlinx/coroutines/DispatcherExecutor;

    .line 19
    .line 20
    invoke-direct {v0, p0}, Lkotlinx/coroutines/DispatcherExecutor;-><init>(Lkotlinx/coroutines/CoroutineDispatcher;)V

    .line 21
    .line 22
    .line 23
    :cond_2
    return-object v0
.end method

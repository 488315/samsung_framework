.class public abstract Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;
.implements Ljava/lang/Comparable;
.implements Lkotlinx/coroutines/DisposableHandle;


# instance fields
.field private volatile _heap:Ljava/lang/Object;

.field public index:I

.field public nanoTime:J


# direct methods
.method public constructor <init>(J)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-wide p1, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 5
    .line 6
    const/4 p1, -0x1

    .line 7
    iput p1, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->index:I

    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final compareTo(Ljava/lang/Object;)I
    .locals 2

    .line 1
    check-cast p1, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;

    .line 2
    .line 3
    iget-wide v0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 4
    .line 5
    iget-wide p0, p1, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 6
    .line 7
    sub-long/2addr v0, p0

    .line 8
    const-wide/16 p0, 0x0

    .line 9
    .line 10
    cmp-long p0, v0, p0

    .line 11
    .line 12
    if-lez p0, :cond_0

    .line 13
    .line 14
    const/4 p0, 0x1

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    if-gez p0, :cond_1

    .line 17
    .line 18
    const/4 p0, -0x1

    .line 19
    goto :goto_0

    .line 20
    :cond_1
    const/4 p0, 0x0

    .line 21
    :goto_0
    return p0
.end method

.method public final declared-synchronized dispose()V
    .locals 3

    .line 1
    monitor-enter p0

    .line 2
    :try_start_0
    iget-object v0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;

    .line 3
    .line 4
    sget-object v1, Lkotlinx/coroutines/EventLoop_commonKt;->DISPOSED_TASK:Lkotlinx/coroutines/internal/Symbol;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 5
    .line 6
    if-ne v0, v1, :cond_0

    .line 7
    .line 8
    monitor-exit p0

    .line 9
    return-void

    .line 10
    :cond_0
    :try_start_1
    instance-of v2, v0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;

    .line 11
    .line 12
    if-eqz v2, :cond_1

    .line 13
    .line 14
    check-cast v0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;

    .line 15
    .line 16
    goto :goto_0

    .line 17
    :cond_1
    const/4 v0, 0x0

    .line 18
    :goto_0
    if-eqz v0, :cond_3

    .line 19
    .line 20
    monitor-enter v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 21
    :try_start_2
    invoke-virtual {p0}, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->getHeap()Lkotlinx/coroutines/internal/ThreadSafeHeap;

    .line 22
    .line 23
    .line 24
    move-result-object v2

    .line 25
    if-nez v2, :cond_2

    .line 26
    .line 27
    goto :goto_1

    .line 28
    :cond_2
    iget v2, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->index:I

    .line 29
    .line 30
    invoke-virtual {v0, v2}, Lkotlinx/coroutines/internal/ThreadSafeHeap;->removeAtImpl(I)Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 31
    .line 32
    .line 33
    :goto_1
    :try_start_3
    monitor-exit v0

    .line 34
    goto :goto_2

    .line 35
    :catchall_0
    move-exception v1

    .line 36
    monitor-exit v0

    .line 37
    throw v1

    .line 38
    :cond_3
    :goto_2
    iput-object v1, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 39
    .line 40
    monitor-exit p0

    .line 41
    return-void

    .line 42
    :catchall_1
    move-exception v0

    .line 43
    monitor-exit p0

    .line 44
    throw v0
.end method

.method public final getHeap()Lkotlinx/coroutines/internal/ThreadSafeHeap;
    .locals 1

    .line 1
    iget-object p0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;

    .line 2
    .line 3
    instance-of v0, p0, Lkotlinx/coroutines/internal/ThreadSafeHeap;

    .line 4
    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    check-cast p0, Lkotlinx/coroutines/internal/ThreadSafeHeap;

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    const/4 p0, 0x0

    .line 11
    :goto_0
    return-object p0
.end method

.method public final declared-synchronized scheduleTask(JLkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;Lkotlinx/coroutines/EventLoopImplBase;)I
    .locals 8

    .line 1
    monitor-enter p0

    .line 2
    :try_start_0
    iget-object v0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;

    .line 3
    .line 4
    sget-object v1, Lkotlinx/coroutines/EventLoop_commonKt;->DISPOSED_TASK:Lkotlinx/coroutines/internal/Symbol;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 5
    .line 6
    if-ne v0, v1, :cond_0

    .line 7
    .line 8
    monitor-exit p0

    .line 9
    const/4 p0, 0x2

    .line 10
    return p0

    .line 11
    :cond_0
    :try_start_1
    monitor-enter p3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 12
    :try_start_2
    iget-object v0, p3, Lkotlinx/coroutines/internal/ThreadSafeHeap;->a:[Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;

    .line 13
    .line 14
    const/4 v1, 0x0

    .line 15
    if-eqz v0, :cond_1

    .line 16
    .line 17
    aget-object v0, v0, v1

    .line 18
    .line 19
    goto :goto_0

    .line 20
    :catchall_0
    move-exception p1

    .line 21
    goto :goto_4

    .line 22
    :cond_1
    const/4 v0, 0x0

    .line 23
    :goto_0
    iget-object p4, p4, Lkotlinx/coroutines/EventLoopImplBase;->_isCompleted:Lkotlinx/atomicfu/AtomicBoolean;

    .line 24
    .line 25
    iget p4, p4, Lkotlinx/atomicfu/AtomicBoolean;->_value:I
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 26
    .line 27
    const/4 v2, 0x1

    .line 28
    if-eqz p4, :cond_2

    .line 29
    .line 30
    move p4, v2

    .line 31
    goto :goto_1

    .line 32
    :cond_2
    move p4, v1

    .line 33
    :goto_1
    if-eqz p4, :cond_3

    .line 34
    .line 35
    :try_start_3
    monitor-exit p3
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 36
    monitor-exit p0

    .line 37
    return v2

    .line 38
    :cond_3
    const-wide/16 v2, 0x0

    .line 39
    .line 40
    if-nez v0, :cond_4

    .line 41
    .line 42
    :try_start_4
    iput-wide p1, p3, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;->timeNow:J

    .line 43
    .line 44
    goto :goto_3

    .line 45
    :cond_4
    iget-wide v4, v0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 46
    .line 47
    sub-long v6, v4, p1

    .line 48
    .line 49
    cmp-long p4, v6, v2

    .line 50
    .line 51
    if-ltz p4, :cond_5

    .line 52
    .line 53
    goto :goto_2

    .line 54
    :cond_5
    move-wide p1, v4

    .line 55
    :goto_2
    iget-wide v4, p3, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;->timeNow:J

    .line 56
    .line 57
    sub-long v4, p1, v4

    .line 58
    .line 59
    cmp-long p4, v4, v2

    .line 60
    .line 61
    if-lez p4, :cond_6

    .line 62
    .line 63
    iput-wide p1, p3, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;->timeNow:J

    .line 64
    .line 65
    :cond_6
    :goto_3
    iget-wide p1, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 66
    .line 67
    iget-wide v4, p3, Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;->timeNow:J

    .line 68
    .line 69
    sub-long/2addr p1, v4

    .line 70
    cmp-long p1, p1, v2

    .line 71
    .line 72
    if-gez p1, :cond_7

    .line 73
    .line 74
    iput-wide v4, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 75
    .line 76
    :cond_7
    invoke-virtual {p3, p0}, Lkotlinx/coroutines/internal/ThreadSafeHeap;->addImpl(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 77
    .line 78
    .line 79
    :try_start_5
    monitor-exit p3
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    .line 80
    monitor-exit p0

    .line 81
    return v1

    .line 82
    :goto_4
    :try_start_6
    monitor-exit p3

    .line 83
    throw p1
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_1

    .line 84
    :catchall_1
    move-exception p1

    .line 85
    monitor-exit p0

    .line 86
    throw p1
.end method

.method public final setHeap(Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;)V
    .locals 2

    .line 1
    iget-object v0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;

    .line 2
    .line 3
    sget-object v1, Lkotlinx/coroutines/EventLoop_commonKt;->DISPOSED_TASK:Lkotlinx/coroutines/internal/Symbol;

    .line 4
    .line 5
    if-eq v0, v1, :cond_0

    .line 6
    .line 7
    const/4 v0, 0x1

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
    iput-object p1, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->_heap:Ljava/lang/Object;

    .line 13
    .line 14
    return-void

    .line 15
    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    .line 16
    .line 17
    const-string p1, "Failed requirement."

    .line 18
    .line 19
    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    .line 20
    .line 21
    .line 22
    move-result-object p1

    .line 23
    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    .line 24
    .line 25
    .line 26
    throw p0
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 1
    iget-wide v0, p0, Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;->nanoTime:J

    .line 2
    .line 3
    new-instance p0, Ljava/lang/StringBuilder;

    .line 4
    .line 5
    const-string v2, "Delayed[nanos="

    .line 6
    .line 7
    invoke-direct {p0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 8
    .line 9
    .line 10
    invoke-virtual {p0, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v0, "]"

    .line 14
    .line 15
    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 19
    .line 20
    .line 21
    move-result-object p0

    .line 22
    return-object p0
.end method

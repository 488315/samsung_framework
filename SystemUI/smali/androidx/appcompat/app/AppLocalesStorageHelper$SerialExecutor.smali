.class public final Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/concurrent/Executor;


# instance fields
.field public mActive:Ljava/lang/Runnable;

.field public final mExecutor:Ljava/util/concurrent/Executor;

.field public final mLock:Ljava/lang/Object;

.field public final mTasks:Ljava/util/Queue;


# direct methods
.method public constructor <init>(Ljava/util/concurrent/Executor;)V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Ljava/lang/Object;

    .line 5
    .line 6
    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mLock:Ljava/lang/Object;

    .line 10
    .line 11
    new-instance v0, Ljava/util/ArrayDeque;

    .line 12
    .line 13
    invoke-direct {v0}, Ljava/util/ArrayDeque;-><init>()V

    .line 14
    .line 15
    .line 16
    iput-object v0, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mTasks:Ljava/util/Queue;

    .line 17
    .line 18
    iput-object p1, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mExecutor:Ljava/util/concurrent/Executor;

    .line 19
    .line 20
    return-void
.end method


# virtual methods
.method public final execute(Ljava/lang/Runnable;)V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mLock:Ljava/lang/Object;

    .line 2
    .line 3
    monitor-enter v0

    .line 4
    :try_start_0
    iget-object v1, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mTasks:Ljava/util/Queue;

    .line 5
    .line 6
    new-instance v2, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor$$ExternalSyntheticLambda0;

    .line 7
    .line 8
    invoke-direct {v2, p0, p1}, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor$$ExternalSyntheticLambda0;-><init>(Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;Ljava/lang/Runnable;)V

    .line 9
    .line 10
    .line 11
    check-cast v1, Ljava/util/ArrayDeque;

    .line 12
    .line 13
    invoke-virtual {v1, v2}, Ljava/util/ArrayDeque;->add(Ljava/lang/Object;)Z

    .line 14
    .line 15
    .line 16
    iget-object p1, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mActive:Ljava/lang/Runnable;

    .line 17
    .line 18
    if-nez p1, :cond_0

    .line 19
    .line 20
    invoke-virtual {p0}, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->scheduleNext()V

    .line 21
    .line 22
    .line 23
    :cond_0
    monitor-exit v0

    .line 24
    return-void

    .line 25
    :catchall_0
    move-exception p0

    .line 26
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 27
    throw p0
.end method

.method public final scheduleNext()V
    .locals 2

    .line 1
    iget-object v0, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mLock:Ljava/lang/Object;

    .line 2
    .line 3
    monitor-enter v0

    .line 4
    :try_start_0
    iget-object v1, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mTasks:Ljava/util/Queue;

    .line 5
    .line 6
    check-cast v1, Ljava/util/ArrayDeque;

    .line 7
    .line 8
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->poll()Ljava/lang/Object;

    .line 9
    .line 10
    .line 11
    move-result-object v1

    .line 12
    check-cast v1, Ljava/lang/Runnable;

    .line 13
    .line 14
    iput-object v1, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mActive:Ljava/lang/Runnable;

    .line 15
    .line 16
    if-eqz v1, :cond_0

    .line 17
    .line 18
    iget-object p0, p0, Landroidx/appcompat/app/AppLocalesStorageHelper$SerialExecutor;->mExecutor:Ljava/util/concurrent/Executor;

    .line 19
    .line 20
    invoke-interface {p0, v1}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 21
    .line 22
    .line 23
    :cond_0
    monitor-exit v0

    .line 24
    return-void

    .line 25
    :catchall_0
    move-exception p0

    .line 26
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 27
    throw p0
.end method

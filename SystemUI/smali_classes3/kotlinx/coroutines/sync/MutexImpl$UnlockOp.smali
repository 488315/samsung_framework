.class public final Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;
.super Lkotlinx/coroutines/internal/AtomicOp;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final queue:Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;


# direct methods
.method public constructor <init>(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Lkotlinx/coroutines/internal/AtomicOp;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;->queue:Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final complete(Ljava/lang/Object;Ljava/lang/Object;)V
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/sync/MutexImpl;

    .line 2
    .line 3
    if-nez p2, :cond_0

    .line 4
    .line 5
    sget-object p2, Lkotlinx/coroutines/sync/MutexKt;->EMPTY_UNLOCKED:Lkotlinx/coroutines/sync/Empty;

    .line 6
    .line 7
    goto :goto_0

    .line 8
    :cond_0
    iget-object p2, p0, Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;->queue:Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;

    .line 9
    .line 10
    :goto_0
    iget-object p1, p1, Lkotlinx/coroutines/sync/MutexImpl;->_state:Lkotlinx/atomicfu/AtomicRef;

    .line 11
    .line 12
    invoke-virtual {p1, p0, p2}, Lkotlinx/atomicfu/AtomicRef;->compareAndSet(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 13
    .line 14
    .line 15
    return-void
.end method

.method public final prepare(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/sync/MutexImpl;

    .line 2
    .line 3
    iget-object p0, p0, Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;->queue:Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;

    .line 4
    .line 5
    invoke-virtual {p0}, Lkotlinx/coroutines/internal/LockFreeLinkedListNode;->getNext()Ljava/lang/Object;

    .line 6
    .line 7
    .line 8
    move-result-object p1

    .line 9
    if-ne p1, p0, :cond_0

    .line 10
    .line 11
    const/4 p0, 0x1

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 p0, 0x0

    .line 14
    :goto_0
    if-eqz p0, :cond_1

    .line 15
    .line 16
    const/4 p0, 0x0

    .line 17
    goto :goto_1

    .line 18
    :cond_1
    sget-object p0, Lkotlinx/coroutines/sync/MutexKt;->UNLOCK_FAIL:Lkotlinx/coroutines/internal/Symbol;

    .line 19
    .line 20
    :goto_1
    return-object p0
.end method

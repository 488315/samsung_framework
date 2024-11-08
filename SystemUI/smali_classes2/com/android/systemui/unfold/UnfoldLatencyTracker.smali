.class public final Lcom/android/systemui/unfold/UnfoldLatencyTracker;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/keyguard/ScreenLifecycle$Observer;
.implements Lcom/android/systemui/unfold/UnfoldTransitionProgressProvider$TransitionProgressListener;


# instance fields
.field public final contentResolver:Landroid/content/ContentResolver;

.field public final context:Landroid/content/Context;

.field public final deviceStateManager:Landroid/hardware/devicestate/DeviceStateManager;

.field public final foldStateListener:Lcom/android/systemui/unfold/UnfoldLatencyTracker$FoldStateListener;

.field public folded:Ljava/lang/Boolean;

.field public isTransitionEnabled:Ljava/lang/Boolean;

.field public final latencyTracker:Lcom/android/internal/util/LatencyTracker;

.field public final screenLifecycle:Lcom/android/systemui/keyguard/ScreenLifecycle;

.field public final transitionProgressProvider:Ljava/util/Optional;

.field public final uiBgExecutor:Ljava/util/concurrent/Executor;


# direct methods
.method public constructor <init>(Lcom/android/internal/util/LatencyTracker;Landroid/hardware/devicestate/DeviceStateManager;Ljava/util/Optional;Ljava/util/concurrent/Executor;Landroid/content/Context;Landroid/content/ContentResolver;Lcom/android/systemui/keyguard/ScreenLifecycle;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/internal/util/LatencyTracker;",
            "Landroid/hardware/devicestate/DeviceStateManager;",
            "Ljava/util/Optional<",
            "Lcom/android/systemui/unfold/UnfoldTransitionProgressProvider;",
            ">;",
            "Ljava/util/concurrent/Executor;",
            "Landroid/content/Context;",
            "Landroid/content/ContentResolver;",
            "Lcom/android/systemui/keyguard/ScreenLifecycle;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->latencyTracker:Lcom/android/internal/util/LatencyTracker;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->deviceStateManager:Landroid/hardware/devicestate/DeviceStateManager;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->transitionProgressProvider:Ljava/util/Optional;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->uiBgExecutor:Ljava/util/concurrent/Executor;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->context:Landroid/content/Context;

    .line 13
    .line 14
    iput-object p6, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->contentResolver:Landroid/content/ContentResolver;

    .line 15
    .line 16
    iput-object p7, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->screenLifecycle:Lcom/android/systemui/keyguard/ScreenLifecycle;

    .line 17
    .line 18
    new-instance p1, Lcom/android/systemui/unfold/UnfoldLatencyTracker$FoldStateListener;

    .line 19
    .line 20
    invoke-direct {p1, p0, p5}, Lcom/android/systemui/unfold/UnfoldLatencyTracker$FoldStateListener;-><init>(Lcom/android/systemui/unfold/UnfoldLatencyTracker;Landroid/content/Context;)V

    .line 21
    .line 22
    .line 23
    iput-object p1, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->foldStateListener:Lcom/android/systemui/unfold/UnfoldLatencyTracker$FoldStateListener;

    .line 24
    .line 25
    return-void
.end method


# virtual methods
.method public final onScreenTurnedOn()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->folded:Ljava/lang/Boolean;

    .line 2
    .line 3
    sget-object v1, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;

    .line 4
    .line 5
    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-eqz v0, :cond_0

    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->isTransitionEnabled:Ljava/lang/Boolean;

    .line 12
    .line 13
    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    if-eqz v0, :cond_0

    .line 18
    .line 19
    iget-object p0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->latencyTracker:Lcom/android/internal/util/LatencyTracker;

    .line 20
    .line 21
    const/16 v0, 0xd

    .line 22
    .line 23
    invoke-virtual {p0, v0}, Lcom/android/internal/util/LatencyTracker;->onActionEnd(I)V

    .line 24
    .line 25
    .line 26
    :cond_0
    return-void
.end method

.method public final onTransitionStarted()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->folded:Ljava/lang/Boolean;

    .line 2
    .line 3
    sget-object v1, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;

    .line 4
    .line 5
    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-eqz v0, :cond_0

    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->isTransitionEnabled:Ljava/lang/Boolean;

    .line 12
    .line 13
    sget-object v1, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    .line 14
    .line 15
    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 16
    .line 17
    .line 18
    move-result v0

    .line 19
    if-eqz v0, :cond_0

    .line 20
    .line 21
    iget-object p0, p0, Lcom/android/systemui/unfold/UnfoldLatencyTracker;->latencyTracker:Lcom/android/internal/util/LatencyTracker;

    .line 22
    .line 23
    const/16 v0, 0xd

    .line 24
    .line 25
    invoke-virtual {p0, v0}, Lcom/android/internal/util/LatencyTracker;->onActionEnd(I)V

    .line 26
    .line 27
    .line 28
    :cond_0
    return-void
.end method

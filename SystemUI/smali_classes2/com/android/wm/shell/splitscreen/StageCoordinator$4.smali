.class public final Lcom/android/wm/shell/splitscreen/StageCoordinator$4;
.super Landroid/view/IRemoteAnimationRunner$Stub;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/wm/shell/splitscreen/StageCoordinator;

.field public final synthetic val$adapter:Landroid/view/RemoteAnimationAdapter;

.field public final synthetic val$evictWct:Landroid/window/WindowContainerTransaction;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/splitscreen/StageCoordinator;Landroid/window/WindowContainerTransaction;Landroid/view/RemoteAnimationAdapter;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->this$0:Lcom/android/wm/shell/splitscreen/StageCoordinator;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$evictWct:Landroid/window/WindowContainerTransaction;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$adapter:Landroid/view/RemoteAnimationAdapter;

    .line 6
    .line 7
    invoke-direct {p0}, Landroid/view/IRemoteAnimationRunner$Stub;-><init>()V

    .line 8
    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final onAnimationCancelled()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->this$0:Lcom/android/wm/shell/splitscreen/StageCoordinator;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$evictWct:Landroid/window/WindowContainerTransaction;

    .line 4
    .line 5
    invoke-static {v0, v1}, Lcom/android/wm/shell/splitscreen/StageCoordinator;->-$$Nest$monRemoteAnimationFinishedOrCancelled(Lcom/android/wm/shell/splitscreen/StageCoordinator;Landroid/window/WindowContainerTransaction;)V

    .line 6
    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->this$0:Lcom/android/wm/shell/splitscreen/StageCoordinator;

    .line 9
    .line 10
    const/4 v1, 0x1

    .line 11
    const/4 v2, 0x0

    .line 12
    invoke-virtual {v0, v1, v2}, Lcom/android/wm/shell/splitscreen/StageCoordinator;->setDividerVisibility(ZLandroid/view/SurfaceControl$Transaction;)V

    .line 13
    .line 14
    .line 15
    :try_start_0
    iget-object p0, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$adapter:Landroid/view/RemoteAnimationAdapter;

    .line 16
    .line 17
    invoke-virtual {p0}, Landroid/view/RemoteAnimationAdapter;->getRunner()Landroid/view/IRemoteAnimationRunner;

    .line 18
    .line 19
    .line 20
    move-result-object p0

    .line 21
    invoke-interface {p0}, Landroid/view/IRemoteAnimationRunner;->onAnimationCancelled()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 22
    .line 23
    .line 24
    goto :goto_0

    .line 25
    :catch_0
    move-exception p0

    .line 26
    const-string v0, "StageCoordinator"

    .line 27
    .line 28
    const-string v1, "Error starting remote animation"

    .line 29
    .line 30
    invoke-static {v0, v1, p0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 31
    .line 32
    .line 33
    :goto_0
    return-void
.end method

.method public final onAnimationStart(I[Landroid/view/RemoteAnimationTarget;[Landroid/view/RemoteAnimationTarget;[Landroid/view/RemoteAnimationTarget;Landroid/view/IRemoteAnimationFinishedCallback;)V
    .locals 6

    .line 1
    new-instance v5, Lcom/android/wm/shell/splitscreen/StageCoordinator$4$1;

    .line 2
    .line 3
    invoke-direct {v5, p0, p5}, Lcom/android/wm/shell/splitscreen/StageCoordinator$4$1;-><init>(Lcom/android/wm/shell/splitscreen/StageCoordinator$4;Landroid/view/IRemoteAnimationFinishedCallback;)V

    .line 4
    .line 5
    .line 6
    iget-object p5, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$adapter:Landroid/view/RemoteAnimationAdapter;

    .line 7
    .line 8
    invoke-virtual {p5}, Landroid/view/RemoteAnimationAdapter;->getCallingApplication()Landroid/app/IApplicationThread;

    .line 9
    .line 10
    .line 11
    move-result-object p5

    .line 12
    invoke-static {p5}, Lcom/android/wm/shell/transition/Transitions;->setRunningRemoteTransitionDelegate(Landroid/app/IApplicationThread;)V

    .line 13
    .line 14
    .line 15
    :try_start_0
    iget-object p5, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->val$adapter:Landroid/view/RemoteAnimationAdapter;

    .line 16
    .line 17
    invoke-virtual {p5}, Landroid/view/RemoteAnimationAdapter;->getRunner()Landroid/view/IRemoteAnimationRunner;

    .line 18
    .line 19
    .line 20
    move-result-object v0

    .line 21
    const-class p5, Landroid/view/RemoteAnimationTarget;

    .line 22
    .line 23
    iget-object p0, p0, Lcom/android/wm/shell/splitscreen/StageCoordinator$4;->this$0:Lcom/android/wm/shell/splitscreen/StageCoordinator;

    .line 24
    .line 25
    invoke-virtual {p0}, Lcom/android/wm/shell/splitscreen/StageCoordinator;->getDividerBarLegacyTarget()Landroid/view/RemoteAnimationTarget;

    .line 26
    .line 27
    .line 28
    move-result-object p0

    .line 29
    invoke-static {p5, p4, p0}, Lcom/android/internal/util/ArrayUtils;->appendElement(Ljava/lang/Class;[Ljava/lang/Object;Ljava/lang/Object;)[Ljava/lang/Object;

    .line 30
    .line 31
    .line 32
    move-result-object p0

    .line 33
    move-object v4, p0

    .line 34
    check-cast v4, [Landroid/view/RemoteAnimationTarget;

    .line 35
    .line 36
    move v1, p1

    .line 37
    move-object v2, p2

    .line 38
    move-object v3, p3

    .line 39
    invoke-interface/range {v0 .. v5}, Landroid/view/IRemoteAnimationRunner;->onAnimationStart(I[Landroid/view/RemoteAnimationTarget;[Landroid/view/RemoteAnimationTarget;[Landroid/view/RemoteAnimationTarget;Landroid/view/IRemoteAnimationFinishedCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 40
    .line 41
    .line 42
    goto :goto_0

    .line 43
    :catch_0
    move-exception p0

    .line 44
    const-string p1, "StageCoordinator"

    .line 45
    .line 46
    const-string p2, "Error starting remote animation"

    .line 47
    .line 48
    invoke-static {p1, p2, p0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 49
    .line 50
    .line 51
    :goto_0
    return-void
.end method

.class public final Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/dynamicanimation/animation/FrameCallbackScheduler;


# instance fields
.field public final mChoreographer:Landroid/view/Choreographer;

.field public final mLooper:Landroid/os/Looper;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    invoke-static {}, Landroid/view/Choreographer;->getInstance()Landroid/view/Choreographer;

    .line 5
    .line 6
    .line 7
    move-result-object v0

    .line 8
    iput-object v0, p0, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16;->mChoreographer:Landroid/view/Choreographer;

    .line 9
    .line 10
    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    .line 11
    .line 12
    .line 13
    move-result-object v0

    .line 14
    iput-object v0, p0, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16;->mLooper:Landroid/os/Looper;

    .line 15
    .line 16
    return-void
.end method


# virtual methods
.method public final isCurrentThread()Z
    .locals 1

    .line 1
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    iget-object p0, p0, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16;->mLooper:Landroid/os/Looper;

    .line 6
    .line 7
    invoke-virtual {p0}, Landroid/os/Looper;->getThread()Ljava/lang/Thread;

    .line 8
    .line 9
    .line 10
    move-result-object p0

    .line 11
    if-ne v0, p0, :cond_0

    .line 12
    .line 13
    const/4 p0, 0x1

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    const/4 p0, 0x0

    .line 16
    :goto_0
    return p0
.end method

.method public final postFrameCallback(Landroidx/dynamicanimation/animation/AnimationHandler$$ExternalSyntheticLambda0;)V
    .locals 1

    .line 1
    new-instance v0, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0;

    .line 2
    .line 3
    invoke-direct {v0, p1}, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0;-><init>(Landroidx/dynamicanimation/animation/AnimationHandler$$ExternalSyntheticLambda0;)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Landroidx/dynamicanimation/animation/AnimationHandler$FrameCallbackScheduler16;->mChoreographer:Landroid/view/Choreographer;

    .line 7
    .line 8
    invoke-virtual {p0, v0}, Landroid/view/Choreographer;->postFrameCallback(Landroid/view/Choreographer$FrameCallback;)V

    .line 9
    .line 10
    .line 11
    return-void
.end method

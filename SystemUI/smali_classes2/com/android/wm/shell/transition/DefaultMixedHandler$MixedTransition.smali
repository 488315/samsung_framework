.class public final Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mAnimType:I

.field public mFinishWCT:Landroid/window/WindowContainerTransaction;

.field public mInFlightSubAnimations:I

.field public mLeftoversHandler:Lcom/android/wm/shell/transition/Transitions$TransitionHandler;

.field public final mTransition:Landroid/os/IBinder;

.field public final mType:I


# direct methods
.method public constructor <init>(ILandroid/os/IBinder;)V
    .locals 2

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, 0x0

    .line 5
    iput v0, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mAnimType:I

    .line 6
    .line 7
    const/4 v1, 0x0

    .line 8
    iput-object v1, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mLeftoversHandler:Lcom/android/wm/shell/transition/Transitions$TransitionHandler;

    .line 9
    .line 10
    iput-object v1, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mFinishWCT:Landroid/window/WindowContainerTransaction;

    .line 11
    .line 12
    iput v0, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mInFlightSubAnimations:I

    .line 13
    .line 14
    iput p1, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mType:I

    .line 15
    .line 16
    iput-object p2, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mTransition:Landroid/os/IBinder;

    .line 17
    .line 18
    return-void
.end method


# virtual methods
.method public final joinFinishArgs(Landroid/window/WindowContainerTransaction;Landroid/window/WindowContainerTransactionCallback;)V
    .locals 0

    .line 1
    if-nez p2, :cond_2

    .line 2
    .line 3
    if-eqz p1, :cond_1

    .line 4
    .line 5
    iget-object p2, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mFinishWCT:Landroid/window/WindowContainerTransaction;

    .line 6
    .line 7
    if-nez p2, :cond_0

    .line 8
    .line 9
    iput-object p1, p0, Lcom/android/wm/shell/transition/DefaultMixedHandler$MixedTransition;->mFinishWCT:Landroid/window/WindowContainerTransaction;

    .line 10
    .line 11
    goto :goto_0

    .line 12
    :cond_0
    const/4 p0, 0x1

    .line 13
    invoke-virtual {p2, p1, p0}, Landroid/window/WindowContainerTransaction;->merge(Landroid/window/WindowContainerTransaction;Z)V

    .line 14
    .line 15
    .line 16
    :cond_1
    :goto_0
    return-void

    .line 17
    :cond_2
    new-instance p0, Ljava/lang/IllegalArgumentException;

    .line 18
    .line 19
    const-string p1, "Can\'t mix transitions that require finish sync callback"

    .line 20
    .line 21
    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    .line 22
    .line 23
    .line 24
    throw p0
.end method

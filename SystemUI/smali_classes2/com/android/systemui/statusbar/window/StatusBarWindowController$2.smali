.class public final Lcom/android/systemui/statusbar/window/StatusBarWindowController$2;
.super Lcom/android/systemui/animation/DelegateLaunchAnimatorController;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/window/StatusBarWindowController;Lcom/android/systemui/animation/ActivityLaunchAnimator$Controller;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController$2;->this$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;

    .line 2
    .line 3
    invoke-direct {p0, p2}, Lcom/android/systemui/animation/DelegateLaunchAnimatorController;-><init>(Lcom/android/systemui/animation/ActivityLaunchAnimator$Controller;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onLaunchAnimationEnd(Z)V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/animation/DelegateLaunchAnimatorController;->delegate:Lcom/android/systemui/animation/ActivityLaunchAnimator$Controller;

    .line 2
    .line 3
    invoke-interface {v0, p1}, Lcom/android/systemui/animation/LaunchAnimator$Controller;->onLaunchAnimationEnd(Z)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController$2;->this$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;

    .line 7
    .line 8
    iget-object p1, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController;->mCurrentState:Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;

    .line 9
    .line 10
    iget-boolean v0, p1, Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;->mIsLaunchAnimationRunning:Z

    .line 11
    .line 12
    if-nez v0, :cond_0

    .line 13
    .line 14
    goto :goto_0

    .line 15
    :cond_0
    const/4 v0, 0x0

    .line 16
    iput-boolean v0, p1, Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;->mIsLaunchAnimationRunning:Z

    .line 17
    .line 18
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/statusbar/window/StatusBarWindowController;->apply(Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;Z)V

    .line 19
    .line 20
    .line 21
    :goto_0
    return-void
.end method

.method public final onLaunchAnimationStart(Z)V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/animation/DelegateLaunchAnimatorController;->delegate:Lcom/android/systemui/animation/ActivityLaunchAnimator$Controller;

    .line 2
    .line 3
    invoke-interface {v0, p1}, Lcom/android/systemui/animation/LaunchAnimator$Controller;->onLaunchAnimationStart(Z)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController$2;->this$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;

    .line 7
    .line 8
    iget-object p1, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController;->mCurrentState:Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;

    .line 9
    .line 10
    iget-boolean v0, p1, Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;->mIsLaunchAnimationRunning:Z

    .line 11
    .line 12
    const/4 v1, 0x1

    .line 13
    if-ne v1, v0, :cond_0

    .line 14
    .line 15
    goto :goto_0

    .line 16
    :cond_0
    iput-boolean v1, p1, Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;->mIsLaunchAnimationRunning:Z

    .line 17
    .line 18
    const/4 v0, 0x0

    .line 19
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/statusbar/window/StatusBarWindowController;->apply(Lcom/android/systemui/statusbar/window/StatusBarWindowController$State;Z)V

    .line 20
    .line 21
    .line 22
    :goto_0
    return-void
.end method

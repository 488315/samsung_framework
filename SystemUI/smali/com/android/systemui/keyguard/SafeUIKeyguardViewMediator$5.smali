.class public final Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/animation/ActivityLaunchAnimator$Controller;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final createAnimatorState()Lcom/android/systemui/animation/LaunchAnimator$State;
    .locals 13

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->getLaunchContainer()Landroid/view/ViewGroup;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getWidth()I

    .line 6
    .line 7
    .line 8
    move-result v5

    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->getLaunchContainer()Landroid/view/ViewGroup;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getHeight()I

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 18
    .line 19
    iget-object v1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 20
    .line 21
    iget-boolean v1, v1, Lcom/android/keyguard/KeyguardUpdateMonitor;->mSecureCameraLaunched:Z

    .line 22
    .line 23
    const/high16 v2, 0x40000000    # 2.0f

    .line 24
    .line 25
    if-eqz v1, :cond_0

    .line 26
    .line 27
    int-to-float v0, v0

    .line 28
    const/high16 v1, 0x40400000    # 3.0f

    .line 29
    .line 30
    div-float/2addr v0, v1

    .line 31
    int-to-float v3, v5

    .line 32
    div-float v1, v3, v1

    .line 33
    .line 34
    new-instance v8, Lcom/android/systemui/animation/LaunchAnimator$State;

    .line 35
    .line 36
    iget v4, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mPowerButtonY:F

    .line 37
    .line 38
    div-float/2addr v0, v2

    .line 39
    sub-float v2, v4, v0

    .line 40
    .line 41
    float-to-int v2, v2

    .line 42
    add-float/2addr v4, v0

    .line 43
    float-to-int v0, v4

    .line 44
    sub-float/2addr v3, v1

    .line 45
    float-to-int v4, v3

    .line 46
    iget v7, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mWindowCornerRadius:F

    .line 47
    .line 48
    move-object v1, v8

    .line 49
    move v3, v0

    .line 50
    move v6, v7

    .line 51
    invoke-direct/range {v1 .. v7}, Lcom/android/systemui/animation/LaunchAnimator$State;-><init>(IIIIFF)V

    .line 52
    .line 53
    .line 54
    return-object v8

    .line 55
    :cond_0
    int-to-float v0, v0

    .line 56
    div-float v1, v0, v2

    .line 57
    .line 58
    int-to-float v3, v5

    .line 59
    div-float v4, v3, v2

    .line 60
    .line 61
    new-instance v12, Lcom/android/systemui/animation/LaunchAnimator$State;

    .line 62
    .line 63
    sub-float/2addr v0, v1

    .line 64
    float-to-int v5, v0

    .line 65
    div-int/lit8 v6, v5, 0x2

    .line 66
    .line 67
    div-float/2addr v0, v2

    .line 68
    add-float/2addr v0, v1

    .line 69
    float-to-int v7, v0

    .line 70
    sub-float/2addr v3, v4

    .line 71
    float-to-int v0, v3

    .line 72
    div-int/lit8 v8, v0, 0x2

    .line 73
    .line 74
    div-float/2addr v3, v2

    .line 75
    add-float/2addr v3, v4

    .line 76
    float-to-int v9, v3

    .line 77
    iget v11, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mWindowCornerRadius:F

    .line 78
    .line 79
    move-object v5, v12

    .line 80
    move v10, v11

    .line 81
    invoke-direct/range {v5 .. v11}, Lcom/android/systemui/animation/LaunchAnimator$State;-><init>(IIIIFF)V

    .line 82
    .line 83
    .line 84
    return-object v12
.end method

.method public final getLaunchContainer()Landroid/view/ViewGroup;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mKeyguardViewControllerLazy:Ldagger/Lazy;

    .line 4
    .line 5
    invoke-interface {p0}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/keyguard/KeyguardViewController;

    .line 10
    .line 11
    invoke-interface {p0}, Lcom/android/keyguard/KeyguardViewController;->getViewRootImpl()Landroid/view/ViewRootImpl;

    .line 12
    .line 13
    .line 14
    move-result-object p0

    .line 15
    invoke-virtual {p0}, Landroid/view/ViewRootImpl;->getView()Landroid/view/View;

    .line 16
    .line 17
    .line 18
    move-result-object p0

    .line 19
    check-cast p0, Landroid/view/ViewGroup;

    .line 20
    .line 21
    return-object p0
.end method

.method public final onLaunchAnimationCancelled(Ljava/lang/Boolean;)V
    .locals 2

    .line 1
    new-instance p1, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v0, "Occlude launch animation cancelled. Occluded state is now: "

    .line 4
    .line 5
    invoke-direct {p1, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 9
    .line 10
    iget-boolean v0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mOccluded:Z

    .line 11
    .line 12
    const-string v1, "SafeUIKeyguardViewMediator"

    .line 13
    .line 14
    invoke-static {p1, v0, v1}, Landroidx/appcompat/widget/ActionBarContextView$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ZLjava/lang/String;)V

    .line 15
    .line 16
    .line 17
    const/4 p1, 0x0

    .line 18
    iput-boolean p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mOccludeAnimationPlaying:Z

    .line 19
    .line 20
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 21
    .line 22
    .line 23
    const/4 p0, 0x0

    .line 24
    throw p0
.end method

.method public final onLaunchAnimationEnd(Z)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 2
    .line 3
    if-eqz p1, :cond_0

    .line 4
    .line 5
    iget-object p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mShadeController:Ldagger/Lazy;

    .line 6
    .line 7
    invoke-interface {p1}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 8
    .line 9
    .line 10
    move-result-object p1

    .line 11
    check-cast p1, Lcom/android/systemui/shade/ShadeController;

    .line 12
    .line 13
    check-cast p1, Lcom/android/systemui/shade/ShadeControllerImpl;

    .line 14
    .line 15
    invoke-virtual {p1}, Lcom/android/systemui/shade/ShadeControllerImpl;->instantCollapseShade()V

    .line 16
    .line 17
    .line 18
    :cond_0
    const/4 p1, 0x0

    .line 19
    iput-boolean p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mOccludeAnimationPlaying:Z

    .line 20
    .line 21
    const/4 p0, 0x0

    .line 22
    throw p0
.end method

.method public final onLaunchAnimationStart(Z)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$5;->this$0:Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;

    .line 2
    .line 3
    const/4 p1, 0x1

    .line 4
    iput-boolean p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mOccludeAnimationPlaying:Z

    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator;->mScrimControllerLazy:Ldagger/Lazy;

    .line 7
    .line 8
    invoke-interface {p0}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 9
    .line 10
    .line 11
    move-result-object p0

    .line 12
    check-cast p0, Lcom/android/systemui/statusbar/phone/ScrimController;

    .line 13
    .line 14
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/phone/ScrimController;->setOccludeAnimationPlaying(Z)V

    .line 15
    .line 16
    .line 17
    return-void
.end method

.method public final setLaunchContainer(Landroid/view/ViewGroup;)V
    .locals 0

    .line 1
    const-string p0, "SafeUIKeyguardViewMediator"

    .line 2
    .line 3
    const-string p1, "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen."

    .line 4
    .line 5
    invoke-static {p0, p1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    return-void
.end method
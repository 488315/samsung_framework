.class public final Lcom/android/wm/shell/back/CrossActivityAnimation$3;
.super Landroid/animation/AnimatorListenerAdapter;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/wm/shell/back/CrossActivityAnimation;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/back/CrossActivityAnimation;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/back/CrossActivityAnimation$3;->this$0:Lcom/android/wm/shell/back/CrossActivityAnimation;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationEnd(Landroid/animation/Animator;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/back/CrossActivityAnimation$3;->this$0:Lcom/android/wm/shell/back/CrossActivityAnimation;

    .line 2
    .line 3
    sget-object p1, Lcom/android/wm/shell/back/CrossActivityAnimation;->INTERPOLATOR:Landroid/view/animation/Interpolator;

    .line 4
    .line 5
    invoke-virtual {p0}, Lcom/android/wm/shell/back/CrossActivityAnimation;->finishAnimation()V

    .line 6
    .line 7
    .line 8
    return-void
.end method

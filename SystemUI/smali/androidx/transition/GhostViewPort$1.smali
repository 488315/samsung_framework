.class public final Landroidx/transition/GhostViewPort$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnPreDrawListener;


# instance fields
.field public final synthetic this$0:Landroidx/transition/GhostViewPort;


# direct methods
.method public constructor <init>(Landroidx/transition/GhostViewPort;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/transition/GhostViewPort$1;->this$0:Landroidx/transition/GhostViewPort;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onPreDraw()Z
    .locals 2

    .line 1
    iget-object v0, p0, Landroidx/transition/GhostViewPort$1;->this$0:Landroidx/transition/GhostViewPort;

    .line 2
    .line 3
    sget-object v1, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 4
    .line 5
    invoke-static {v0}, Landroidx/core/view/ViewCompat$Api16Impl;->postInvalidateOnAnimation(Landroid/view/View;)V

    .line 6
    .line 7
    .line 8
    iget-object v0, p0, Landroidx/transition/GhostViewPort$1;->this$0:Landroidx/transition/GhostViewPort;

    .line 9
    .line 10
    iget-object v1, v0, Landroidx/transition/GhostViewPort;->mStartParent:Landroid/view/ViewGroup;

    .line 11
    .line 12
    if-eqz v1, :cond_0

    .line 13
    .line 14
    iget-object v0, v0, Landroidx/transition/GhostViewPort;->mStartView:Landroid/view/View;

    .line 15
    .line 16
    if-eqz v0, :cond_0

    .line 17
    .line 18
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->endViewTransition(Landroid/view/View;)V

    .line 19
    .line 20
    .line 21
    iget-object v0, p0, Landroidx/transition/GhostViewPort$1;->this$0:Landroidx/transition/GhostViewPort;

    .line 22
    .line 23
    iget-object v0, v0, Landroidx/transition/GhostViewPort;->mStartParent:Landroid/view/ViewGroup;

    .line 24
    .line 25
    invoke-static {v0}, Landroidx/core/view/ViewCompat$Api16Impl;->postInvalidateOnAnimation(Landroid/view/View;)V

    .line 26
    .line 27
    .line 28
    iget-object p0, p0, Landroidx/transition/GhostViewPort$1;->this$0:Landroidx/transition/GhostViewPort;

    .line 29
    .line 30
    const/4 v0, 0x0

    .line 31
    iput-object v0, p0, Landroidx/transition/GhostViewPort;->mStartParent:Landroid/view/ViewGroup;

    .line 32
    .line 33
    iput-object v0, p0, Landroidx/transition/GhostViewPort;->mStartView:Landroid/view/View;

    .line 34
    .line 35
    :cond_0
    const/4 p0, 0x1

    .line 36
    return p0
.end method

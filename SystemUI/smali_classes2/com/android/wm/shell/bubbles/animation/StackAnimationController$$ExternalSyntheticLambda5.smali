.class public final synthetic Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/bubbles/animation/StackAnimationController;

.field public final synthetic f$1:Ljava/lang/Runnable;

.field public final synthetic f$2:Landroid/view/View;

.field public final synthetic f$3:Ljava/lang/Runnable;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/bubbles/animation/StackAnimationController;Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda4;Landroid/view/View;Lcom/android/wm/shell/bubbles/BubbleStackView$$ExternalSyntheticLambda1;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$0:Lcom/android/wm/shell/bubbles/animation/StackAnimationController;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$1:Ljava/lang/Runnable;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$2:Landroid/view/View;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$3:Ljava/lang/Runnable;

    .line 11
    .line 12
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$0:Lcom/android/wm/shell/bubbles/animation/StackAnimationController;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$1:Ljava/lang/Runnable;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$2:Landroid/view/View;

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/wm/shell/bubbles/animation/StackAnimationController$$ExternalSyntheticLambda5;->f$3:Ljava/lang/Runnable;

    .line 8
    .line 9
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 10
    .line 11
    .line 12
    invoke-interface {v1}, Ljava/lang/Runnable;->run()V

    .line 13
    .line 14
    .line 15
    const/4 v1, 0x0

    .line 16
    invoke-virtual {v0, v2, v1, p0}, Lcom/android/wm/shell/bubbles/animation/StackAnimationController;->moveToFinalIndex(Landroid/view/View;ILjava/lang/Runnable;)V

    .line 17
    .line 18
    .line 19
    return-void
.end method
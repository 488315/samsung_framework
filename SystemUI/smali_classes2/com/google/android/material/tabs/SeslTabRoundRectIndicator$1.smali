.class public final Lcom/google/android/material/tabs/SeslTabRoundRectIndicator$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/animation/Animation$AnimationListener;


# instance fields
.field public final synthetic this$0:Lcom/google/android/material/tabs/SeslTabRoundRectIndicator;


# direct methods
.method public constructor <init>(Lcom/google/android/material/tabs/SeslTabRoundRectIndicator;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/google/android/material/tabs/SeslTabRoundRectIndicator$1;->this$0:Lcom/google/android/material/tabs/SeslTabRoundRectIndicator;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationEnd(Landroid/view/animation/Animation;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/google/android/material/tabs/SeslTabRoundRectIndicator$1;->this$0:Lcom/google/android/material/tabs/SeslTabRoundRectIndicator;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    iput-object p1, p0, Lcom/google/android/material/tabs/SeslTabRoundRectIndicator;->mPressAnimationSet:Landroid/view/animation/AnimationSet;

    .line 5
    .line 6
    return-void
.end method

.method public final onAnimationRepeat(Landroid/view/animation/Animation;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onAnimationStart(Landroid/view/animation/Animation;)V
    .locals 0

    .line 1
    return-void
.end method

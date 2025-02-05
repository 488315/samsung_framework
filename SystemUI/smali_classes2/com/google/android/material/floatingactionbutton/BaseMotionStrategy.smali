.class public abstract Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/google/android/material/floatingactionbutton/MotionStrategy;


# instance fields
.field public final context:Landroid/content/Context;

.field public defaultMotionSpec:Lcom/google/android/material/animation/MotionSpec;

.field public final fab:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;

.field public final listeners:Ljava/util/ArrayList;

.field public motionSpec:Lcom/google/android/material/animation/MotionSpec;

.field public final tracker:Lcom/google/android/material/floatingactionbutton/AnimatorTracker;


# direct methods
.method public constructor <init>(Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Lcom/google/android/material/floatingactionbutton/AnimatorTracker;)V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Ljava/util/ArrayList;

    .line 5
    .line 6
    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->listeners:Ljava/util/ArrayList;

    .line 10
    .line 11
    iput-object p1, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->fab:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;

    .line 12
    .line 13
    invoke-virtual {p1}, Landroid/widget/Button;->getContext()Landroid/content/Context;

    .line 14
    .line 15
    .line 16
    move-result-object p1

    .line 17
    iput-object p1, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->context:Landroid/content/Context;

    .line 18
    .line 19
    iput-object p2, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->tracker:Lcom/google/android/material/floatingactionbutton/AnimatorTracker;

    .line 20
    .line 21
    return-void
.end method


# virtual methods
.method public createAnimator()Landroid/animation/AnimatorSet;
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->motionSpec:Lcom/google/android/material/animation/MotionSpec;

    if-eqz v0, :cond_0

    goto :goto_0

    .line 2
    :cond_0
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->defaultMotionSpec:Lcom/google/android/material/animation/MotionSpec;

    if-nez v0, :cond_1

    .line 3
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->context:Landroid/content/Context;

    invoke-interface {p0}, Lcom/google/android/material/floatingactionbutton/MotionStrategy;->getDefaultMotionSpecResource()I

    move-result v1

    invoke-static {v1, v0}, Lcom/google/android/material/animation/MotionSpec;->createFromResource(ILandroid/content/Context;)Lcom/google/android/material/animation/MotionSpec;

    move-result-object v0

    iput-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->defaultMotionSpec:Lcom/google/android/material/animation/MotionSpec;

    .line 4
    :cond_1
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->defaultMotionSpec:Lcom/google/android/material/animation/MotionSpec;

    .line 5
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    :goto_0
    invoke-virtual {p0, v0}, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->createAnimator(Lcom/google/android/material/animation/MotionSpec;)Landroid/animation/AnimatorSet;

    move-result-object p0

    return-object p0
.end method

.method public final createAnimator(Lcom/google/android/material/animation/MotionSpec;)Landroid/animation/AnimatorSet;
    .locals 6

    .line 7
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    const-string/jumbo v1, "opacity"

    .line 8
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    iget-object v3, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->fab:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;

    if-eqz v2, :cond_0

    .line 9
    sget-object v2, Landroid/view/View;->ALPHA:Landroid/util/Property;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_0
    const-string/jumbo v1, "scale"

    .line 10
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 11
    sget-object v2, Landroid/view/View;->SCALE_Y:Landroid/util/Property;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 12
    sget-object v2, Landroid/view/View;->SCALE_X:Landroid/util/Property;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_1
    const-string/jumbo v1, "width"

    .line 13
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 14
    sget-object v2, Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;->WIDTH:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton$4;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_2
    const-string v1, "height"

    .line 15
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 16
    sget-object v2, Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;->HEIGHT:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton$5;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_3
    const-string/jumbo v1, "paddingStart"

    .line 17
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 18
    sget-object v2, Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;->PADDING_START:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton$6;

    .line 19
    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    .line 20
    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_4
    const-string/jumbo v1, "paddingEnd"

    .line 21
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_5

    .line 22
    sget-object v2, Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;->PADDING_END:Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton$7;

    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_5
    const-string v1, "labelOpacity"

    .line 23
    invoke-virtual {p1, v1}, Lcom/google/android/material/animation/MotionSpec;->hasPropertyValues(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_6

    .line 24
    new-instance v2, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy$1;

    const-class v4, Ljava/lang/Float;

    const-string v5, "LABEL_OPACITY_PROPERTY"

    invoke-direct {v2, p0, v4, v5}, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy$1;-><init>(Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;Ljava/lang/Class;Ljava/lang/String;)V

    .line 25
    invoke-virtual {p1, v1, v3, v2}, Lcom/google/android/material/animation/MotionSpec;->getAnimator(Ljava/lang/String;Lcom/google/android/material/floatingactionbutton/ExtendedFloatingActionButton;Landroid/util/Property;)Landroid/animation/ObjectAnimator;

    move-result-object p0

    .line 26
    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 27
    :cond_6
    new-instance p0, Landroid/animation/AnimatorSet;

    invoke-direct {p0}, Landroid/animation/AnimatorSet;-><init>()V

    .line 28
    invoke-static {p0, v0}, Lcom/google/android/material/animation/AnimatorSetCompat;->playTogether(Landroid/animation/AnimatorSet;Ljava/util/List;)V

    return-object p0
.end method

.method public onAnimationCancel()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-object p0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->tracker:Lcom/google/android/material/floatingactionbutton/AnimatorTracker;

    .line 3
    .line 4
    iput-object v0, p0, Lcom/google/android/material/floatingactionbutton/AnimatorTracker;->currentAnimator:Landroid/animation/Animator;

    .line 5
    .line 6
    return-void
.end method

.method public onAnimationEnd()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-object p0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->tracker:Lcom/google/android/material/floatingactionbutton/AnimatorTracker;

    .line 3
    .line 4
    iput-object v0, p0, Lcom/google/android/material/floatingactionbutton/AnimatorTracker;->currentAnimator:Landroid/animation/Animator;

    .line 5
    .line 6
    return-void
.end method

.method public onAnimationStart(Landroid/animation/Animator;)V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/google/android/material/floatingactionbutton/BaseMotionStrategy;->tracker:Lcom/google/android/material/floatingactionbutton/AnimatorTracker;

    .line 2
    .line 3
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/AnimatorTracker;->currentAnimator:Landroid/animation/Animator;

    .line 4
    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/animation/Animator;->cancel()V

    .line 8
    .line 9
    .line 10
    :cond_0
    iput-object p1, p0, Lcom/google/android/material/floatingactionbutton/AnimatorTracker;->currentAnimator:Landroid/animation/Animator;

    .line 11
    .line 12
    return-void
.end method

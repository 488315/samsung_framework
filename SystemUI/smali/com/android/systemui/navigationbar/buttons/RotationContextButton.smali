.class public final Lcom/android/systemui/navigationbar/buttons/RotationContextButton;
.super Lcom/android/systemui/navigationbar/buttons/ContextualButton;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/shared/rotation/RotationButton;


# instance fields
.field public mRotationButtonController:Lcom/android/systemui/shared/rotation/RotationButtonController;


# direct methods
.method public constructor <init>(ILandroid/content/Context;I)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2, p3}, Lcom/android/systemui/navigationbar/buttons/ContextualButton;-><init>(ILandroid/content/Context;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final acceptRotationProposal()Z
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/ButtonDispatcher;->mCurrentView:Landroid/view/View;

    .line 2
    .line 3
    if-eqz p0, :cond_0

    .line 4
    .line 5
    invoke-virtual {p0}, Landroid/view/View;->isAttachedToWindow()Z

    .line 6
    .line 7
    .line 8
    move-result p0

    .line 9
    if-eqz p0, :cond_0

    .line 10
    .line 11
    const/4 p0, 0x1

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 p0, 0x0

    .line 14
    :goto_0
    return p0
.end method

.method public final getImageDrawable()Landroid/graphics/drawable/Drawable;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/ButtonDispatcher;->mImageDrawable:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 2
    .line 3
    return-object p0
.end method

.method public final getNewDrawable(II)Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/navigationbar/buttons/RotationContextButton;->mRotationButtonController:Lcom/android/systemui/shared/rotation/RotationButtonController;

    .line 2
    .line 3
    invoke-virtual {v0}, Lcom/android/systemui/shared/rotation/RotationButtonController;->getContext()Landroid/content/Context;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/RotationContextButton;->mRotationButtonController:Lcom/android/systemui/shared/rotation/RotationButtonController;

    .line 8
    .line 9
    iget p0, p0, Lcom/android/systemui/shared/rotation/RotationButtonController;->mIconResId:I

    .line 10
    .line 11
    const/4 v1, 0x0

    .line 12
    invoke-static {v0, p1, p2, p0, v1}, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;->create(Landroid/content/Context;IIIZ)Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 13
    .line 14
    .line 15
    move-result-object p0

    .line 16
    return-object p0
.end method

.method public final setRotationButtonController(Lcom/android/systemui/shared/rotation/RotationButtonController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/navigationbar/buttons/RotationContextButton;->mRotationButtonController:Lcom/android/systemui/shared/rotation/RotationButtonController;

    .line 2
    .line 3
    return-void
.end method

.method public final setUpdatesCallback(Lcom/android/systemui/navigationbar/NavigationBarView$2;)V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/navigationbar/buttons/RotationContextButton$$ExternalSyntheticLambda0;

    .line 2
    .line 3
    invoke-direct {v0, p1}, Lcom/android/systemui/navigationbar/buttons/RotationContextButton$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/navigationbar/NavigationBarView$2;)V

    .line 4
    .line 5
    .line 6
    iput-object v0, p0, Lcom/android/systemui/navigationbar/buttons/ContextualButton;->mListener:Lcom/android/systemui/navigationbar/buttons/RotationContextButton$$ExternalSyntheticLambda0;

    .line 7
    .line 8
    return-void
.end method

.method public final setVisibility(I)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Lcom/android/systemui/navigationbar/buttons/ContextualButton;->setVisibility(I)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/ButtonDispatcher;->mImageDrawable:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 5
    .line 6
    if-nez p1, :cond_1

    .line 7
    .line 8
    if-eqz p0, :cond_1

    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;->mAnimatedDrawable:Landroid/graphics/drawable/AnimatedVectorDrawable;

    .line 11
    .line 12
    if-eqz p1, :cond_0

    .line 13
    .line 14
    invoke-virtual {p1}, Landroid/graphics/drawable/AnimatedVectorDrawable;->reset()V

    .line 15
    .line 16
    .line 17
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;->mAnimatedDrawable:Landroid/graphics/drawable/AnimatedVectorDrawable;

    .line 18
    .line 19
    if-eqz p0, :cond_1

    .line 20
    .line 21
    invoke-virtual {p0}, Landroid/graphics/drawable/AnimatedVectorDrawable;->start()V

    .line 22
    .line 23
    .line 24
    :cond_1
    return-void
.end method

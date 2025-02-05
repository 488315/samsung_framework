.class public Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;
.super Landroidx/fragment/app/DialogFragment;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mDialog:Landroidx/appcompat/app/AppCompatDialog;

.field public mSelector:Landroidx/mediarouter/media/MediaRouteSelector;

.field public mUseDynamicGroup:Z


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Landroidx/fragment/app/DialogFragment;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, 0x0

    .line 5
    iput-boolean v0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mUseDynamicGroup:Z

    .line 6
    .line 7
    const/4 v0, 0x1

    .line 8
    iput-boolean v0, p0, Landroidx/fragment/app/DialogFragment;->mCancelable:Z

    .line 9
    .line 10
    iget-object p0, p0, Landroidx/fragment/app/DialogFragment;->mDialog:Landroid/app/Dialog;

    .line 11
    .line 12
    if-eqz p0, :cond_0

    .line 13
    .line 14
    invoke-virtual {p0, v0}, Landroid/app/Dialog;->setCancelable(Z)V

    .line 15
    .line 16
    .line 17
    :cond_0
    return-void
.end method


# virtual methods
.method public final onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 0

    .line 1
    const/4 p1, 0x1

    .line 2
    iput-boolean p1, p0, Landroidx/fragment/app/Fragment;->mCalled:Z

    .line 3
    .line 4
    iget-object p1, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mDialog:Landroidx/appcompat/app/AppCompatDialog;

    .line 5
    .line 6
    if-eqz p1, :cond_1

    .line 7
    .line 8
    iget-boolean p0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mUseDynamicGroup:Z

    .line 9
    .line 10
    if-eqz p0, :cond_0

    .line 11
    .line 12
    check-cast p1, Landroidx/mediarouter/app/MediaRouteDynamicControllerDialog;

    .line 13
    .line 14
    invoke-virtual {p1}, Landroidx/mediarouter/app/MediaRouteDynamicControllerDialog;->updateLayout()V

    .line 15
    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_0
    check-cast p1, Landroidx/mediarouter/app/MediaRouteControllerDialog;

    .line 19
    .line 20
    invoke-virtual {p1}, Landroidx/mediarouter/app/MediaRouteControllerDialog;->updateLayout()V

    .line 21
    .line 22
    .line 23
    :cond_1
    :goto_0
    return-void
.end method

.method public final onCreateDialog()Landroid/app/Dialog;
    .locals 2

    .line 1
    iget-boolean v0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mUseDynamicGroup:Z

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    new-instance v1, Landroidx/mediarouter/app/MediaRouteDynamicControllerDialog;

    .line 10
    .line 11
    invoke-direct {v1, v0}, Landroidx/mediarouter/app/MediaRouteDynamicControllerDialog;-><init>(Landroid/content/Context;)V

    .line 12
    .line 13
    .line 14
    iput-object v1, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mDialog:Landroidx/appcompat/app/AppCompatDialog;

    .line 15
    .line 16
    iget-object v0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mSelector:Landroidx/mediarouter/media/MediaRouteSelector;

    .line 17
    .line 18
    invoke-virtual {v1, v0}, Landroidx/mediarouter/app/MediaRouteDynamicControllerDialog;->setRouteSelector(Landroidx/mediarouter/media/MediaRouteSelector;)V

    .line 19
    .line 20
    .line 21
    goto :goto_0

    .line 22
    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    .line 23
    .line 24
    .line 25
    move-result-object v0

    .line 26
    new-instance v1, Landroidx/mediarouter/app/MediaRouteControllerDialog;

    .line 27
    .line 28
    invoke-direct {v1, v0}, Landroidx/mediarouter/app/MediaRouteControllerDialog;-><init>(Landroid/content/Context;)V

    .line 29
    .line 30
    .line 31
    iput-object v1, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mDialog:Landroidx/appcompat/app/AppCompatDialog;

    .line 32
    .line 33
    :goto_0
    iget-object p0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mDialog:Landroidx/appcompat/app/AppCompatDialog;

    .line 34
    .line 35
    return-object p0
.end method

.method public final onStop()V
    .locals 1

    .line 1
    invoke-super {p0}, Landroidx/fragment/app/DialogFragment;->onStop()V

    .line 2
    .line 3
    .line 4
    iget-object v0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mDialog:Landroidx/appcompat/app/AppCompatDialog;

    .line 5
    .line 6
    if-eqz v0, :cond_0

    .line 7
    .line 8
    iget-boolean p0, p0, Landroidx/mediarouter/app/MediaRouteControllerDialogFragment;->mUseDynamicGroup:Z

    .line 9
    .line 10
    if-nez p0, :cond_0

    .line 11
    .line 12
    check-cast v0, Landroidx/mediarouter/app/MediaRouteControllerDialog;

    .line 13
    .line 14
    const/4 p0, 0x0

    .line 15
    invoke-virtual {v0, p0}, Landroidx/mediarouter/app/MediaRouteControllerDialog;->clearGroupListAnimation(Z)V

    .line 16
    .line 17
    .line 18
    :cond_0
    return-void
.end method

.class public final synthetic Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/qs/tiles/CastTile;

.field public final synthetic f$1:Landroid/view/View;

.field public final synthetic f$2:Landroid/app/Dialog;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/qs/tiles/CastTile;Landroid/view/View;Landroid/app/Dialog;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/qs/tiles/CastTile;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$1:Landroid/view/View;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$2:Landroid/app/Dialog;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 7

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/qs/tiles/CastTile;

    .line 2
    .line 3
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$1:Landroid/view/View;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/systemui/qs/tiles/CastTile$$ExternalSyntheticLambda3;->f$2:Landroid/app/Dialog;

    .line 6
    .line 7
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 8
    .line 9
    .line 10
    if-eqz v3, :cond_0

    .line 11
    .line 12
    new-instance v4, Lcom/android/systemui/animation/DialogCuj;

    .line 13
    .line 14
    const/16 p0, 0x3a

    .line 15
    .line 16
    const-string v1, "cast"

    .line 17
    .line 18
    invoke-direct {v4, p0, v1}, Lcom/android/systemui/animation/DialogCuj;-><init>(ILjava/lang/String;)V

    .line 19
    .line 20
    .line 21
    iget-object v1, v0, Lcom/android/systemui/qs/tiles/CastTile;->mDialogLaunchAnimator:Lcom/android/systemui/animation/DialogLaunchAnimator;

    .line 22
    .line 23
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 24
    .line 25
    .line 26
    const/4 v5, 0x0

    .line 27
    const/16 v6, 0x8

    .line 28
    .line 29
    invoke-static/range {v1 .. v6}, Lcom/android/systemui/animation/DialogLaunchAnimator;->showFromView$default(Lcom/android/systemui/animation/DialogLaunchAnimator;Landroid/app/Dialog;Landroid/view/View;Lcom/android/systemui/animation/DialogCuj;ZI)V

    .line 30
    .line 31
    .line 32
    goto :goto_0

    .line 33
    :cond_0
    invoke-virtual {v2}, Landroid/app/Dialog;->show()V

    .line 34
    .line 35
    .line 36
    :goto_0
    return-void
.end method

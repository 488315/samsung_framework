.class public final Lcom/android/systemui/animation/ViewDialogLaunchAnimatorController;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/animation/DialogLaunchAnimator$Controller;


# instance fields
.field public final cuj:Lcom/android/systemui/animation/DialogCuj;

.field public final source:Landroid/view/View;

.field public final sourceIdentity:Ljava/lang/Object;


# direct methods
.method public constructor <init>(Landroid/view/View;Lcom/android/systemui/animation/DialogCuj;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/animation/ViewDialogLaunchAnimatorController;->source:Landroid/view/View;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/animation/ViewDialogLaunchAnimatorController;->cuj:Lcom/android/systemui/animation/DialogCuj;

    .line 7
    .line 8
    iput-object p1, p0, Lcom/android/systemui/animation/ViewDialogLaunchAnimatorController;->sourceIdentity:Ljava/lang/Object;

    .line 9
    .line 10
    return-void
.end method

.class public final Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field public final synthetic $positions:I

.field public final synthetic this$0:Lcom/android/systemui/dreams/DreamOverlayAnimationsController;


# direct methods
.method public constructor <init>(ILcom/android/systemui/dreams/DreamOverlayAnimationsController;)V
    .locals 0

    .line 1
    iput p1, p0, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1;->$positions:I

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1;->this$0:Lcom/android/systemui/dreams/DreamOverlayAnimationsController;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 2

    .line 1
    new-instance v0, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1$1;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1;->this$0:Lcom/android/systemui/dreams/DreamOverlayAnimationsController;

    .line 4
    .line 5
    invoke-direct {v0, v1, p1}, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1$1;-><init>(Lcom/android/systemui/dreams/DreamOverlayAnimationsController;Landroid/animation/ValueAnimator;)V

    .line 6
    .line 7
    .line 8
    iget p0, p0, Lcom/android/systemui/dreams/DreamOverlayAnimationsController$translationYAnimator$1$1;->$positions:I

    .line 9
    .line 10
    invoke-static {p0, v0}, Lcom/android/systemui/complication/ComplicationLayoutParams;->iteratePositions(ILjava/util/function/Consumer;)V

    .line 11
    .line 12
    .line 13
    return-void
.end method

.class public final Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView;


# direct methods
.method public constructor <init>(Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView$1;->this$0:Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 0

    .line 1
    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    .line 2
    .line 3
    .line 4
    move-result-object p1

    .line 5
    check-cast p1, Ljava/lang/Integer;

    .line 6
    .line 7
    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    .line 8
    .line 9
    .line 10
    move-result p1

    .line 11
    iget-object p0, p0, Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView$1;->this$0:Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/edgelighting/effect/view/EdgeLightAppEffectView;->setMainColor(I)V

    .line 14
    .line 15
    .line 16
    return-void
.end method
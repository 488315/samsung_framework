.class public final synthetic Lcom/android/systemui/FaceScanningOverlay$createRimAppearAnimator$1$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field public final synthetic $tmp0:Lcom/android/systemui/FaceScanningOverlay;


# direct methods
.method public constructor <init>(Lcom/android/systemui/FaceScanningOverlay;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/FaceScanningOverlay$createRimAppearAnimator$1$1;->$tmp0:Lcom/android/systemui/FaceScanningOverlay;

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
    iget-object p0, p0, Lcom/android/systemui/FaceScanningOverlay$createRimAppearAnimator$1$1;->$tmp0:Lcom/android/systemui/FaceScanningOverlay;

    .line 2
    .line 3
    invoke-static {p0, p1}, Lcom/android/systemui/FaceScanningOverlay;->access$updateRimProgress(Lcom/android/systemui/FaceScanningOverlay;Landroid/animation/ValueAnimator;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method
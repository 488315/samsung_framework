.class public final Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field public final synthetic this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

.field public final synthetic val$matrix:Landroid/graphics/Matrix;

.field public final synthetic val$startAlpha:F

.field public final synthetic val$startImageMatrixScale:F

.field public final synthetic val$startScaleX:F

.field public final synthetic val$startScaleY:F

.field public final synthetic val$targetIconScale:F

.field public final synthetic val$targetOpacity:F

.field public final synthetic val$targetScale:F


# direct methods
.method public constructor <init>(Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;FFFFFFFLandroid/graphics/Matrix;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 2
    .line 3
    iput p2, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startAlpha:F

    .line 4
    .line 5
    iput p3, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetOpacity:F

    .line 6
    .line 7
    iput p4, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startScaleX:F

    .line 8
    .line 9
    iput p5, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetScale:F

    .line 10
    .line 11
    iput p6, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startScaleY:F

    .line 12
    .line 13
    iput p7, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startImageMatrixScale:F

    .line 14
    .line 15
    iput p8, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetIconScale:F

    .line 16
    .line 17
    iput-object p9, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$matrix:Landroid/graphics/Matrix;

    .line 18
    .line 19
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 20
    .line 21
    .line 22
    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 5

    .line 1
    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    .line 2
    .line 3
    .line 4
    move-result-object p1

    .line 5
    check-cast p1, Ljava/lang/Float;

    .line 6
    .line 7
    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    .line 8
    .line 9
    .line 10
    move-result p1

    .line 11
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 12
    .line 13
    iget-object v0, v0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->view:Lcom/google/android/material/floatingactionbutton/FloatingActionButton;

    .line 14
    .line 15
    iget v1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startAlpha:F

    .line 16
    .line 17
    iget v2, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetOpacity:F

    .line 18
    .line 19
    const/4 v3, 0x0

    .line 20
    const v4, 0x3e4ccccd    # 0.2f

    .line 21
    .line 22
    .line 23
    invoke-static {v1, v2, v3, v4, p1}, Lcom/google/android/material/animation/AnimationUtils;->lerp(FFFFF)F

    .line 24
    .line 25
    .line 26
    move-result v1

    .line 27
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setAlpha(F)V

    .line 28
    .line 29
    .line 30
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 31
    .line 32
    iget-object v0, v0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->view:Lcom/google/android/material/floatingactionbutton/FloatingActionButton;

    .line 33
    .line 34
    iget v1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startScaleX:F

    .line 35
    .line 36
    iget v2, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetScale:F

    .line 37
    .line 38
    sub-float/2addr v2, v1

    .line 39
    mul-float/2addr v2, p1

    .line 40
    add-float/2addr v2, v1

    .line 41
    invoke-virtual {v0, v2}, Lcom/google/android/material/floatingactionbutton/FloatingActionButton;->setScaleX(F)V

    .line 42
    .line 43
    .line 44
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 45
    .line 46
    iget-object v0, v0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->view:Lcom/google/android/material/floatingactionbutton/FloatingActionButton;

    .line 47
    .line 48
    iget v1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startScaleY:F

    .line 49
    .line 50
    iget v2, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetScale:F

    .line 51
    .line 52
    sub-float/2addr v2, v1

    .line 53
    mul-float/2addr v2, p1

    .line 54
    add-float/2addr v2, v1

    .line 55
    invoke-virtual {v0, v2}, Lcom/google/android/material/floatingactionbutton/FloatingActionButton;->setScaleY(F)V

    .line 56
    .line 57
    .line 58
    iget-object v0, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 59
    .line 60
    iget v1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$startImageMatrixScale:F

    .line 61
    .line 62
    iget v2, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$targetIconScale:F

    .line 63
    .line 64
    invoke-static {v2, v1, p1, v1}, Landroidx/constraintlayout/core/widgets/analyzer/DependencyGraph$$ExternalSyntheticOutline0;->m(FFFF)F

    .line 65
    .line 66
    .line 67
    move-result v3

    .line 68
    iput v3, v0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->imageMatrixScale:F

    .line 69
    .line 70
    invoke-static {v2, v1, p1, v1}, Landroidx/constraintlayout/core/widgets/analyzer/DependencyGraph$$ExternalSyntheticOutline0;->m(FFFF)F

    .line 71
    .line 72
    .line 73
    move-result p1

    .line 74
    iget-object v1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$matrix:Landroid/graphics/Matrix;

    .line 75
    .line 76
    invoke-virtual {v0, p1, v1}, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->calculateImageMatrixFromScale(FLandroid/graphics/Matrix;)V

    .line 77
    .line 78
    .line 79
    iget-object p1, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->this$0:Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;

    .line 80
    .line 81
    iget-object p1, p1, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl;->view:Lcom/google/android/material/floatingactionbutton/FloatingActionButton;

    .line 82
    .line 83
    iget-object p0, p0, Lcom/google/android/material/floatingactionbutton/FloatingActionButtonImpl$4;->val$matrix:Landroid/graphics/Matrix;

    .line 84
    .line 85
    invoke-virtual {p1, p0}, Landroid/widget/ImageButton;->setImageMatrix(Landroid/graphics/Matrix;)V

    .line 86
    .line 87
    .line 88
    return-void
.end method

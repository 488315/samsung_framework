.class public final Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field public final synthetic this$1:Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;


# direct methods
.method public constructor <init>(Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable$2;->this$1:Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;

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
    .locals 1

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
    iget-object p0, p0, Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable$2;->this$1:Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;

    .line 12
    .line 13
    iget-object v0, p0, Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;->mPaint:Landroid/graphics/Paint;

    .line 14
    .line 15
    invoke-virtual {v0, p1}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 16
    .line 17
    .line 18
    const/high16 v0, 0x40000000    # 2.0f

    .line 19
    .line 20
    div-float/2addr p1, v0

    .line 21
    iput p1, p0, Landroidx/appcompat/widget/SeslAbsSeekBar$SliderDrawable;->mRadius:F

    .line 22
    .line 23
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->invalidateSelf()V

    .line 24
    .line 25
    .line 26
    return-void
.end method
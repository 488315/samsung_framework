.class public final Lcom/android/systemui/controls/TooltipManager$show$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $x:I

.field public final synthetic $y:I

.field public final synthetic this$0:Lcom/android/systemui/controls/TooltipManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/TooltipManager;II)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 2
    .line 3
    iput p2, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->$x:I

    .line 4
    .line 5
    iput p3, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->$y:I

    .line 6
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 8
    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 6

    .line 1
    const/4 v0, 0x2

    .line 2
    new-array v1, v0, [I

    .line 3
    .line 4
    iget-object v2, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 5
    .line 6
    iget-object v2, v2, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 7
    .line 8
    invoke-virtual {v2, v1}, Landroid/view/ViewGroup;->getLocationOnScreen([I)V

    .line 9
    .line 10
    .line 11
    iget-object v2, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 12
    .line 13
    iget-object v2, v2, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 14
    .line 15
    iget v3, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->$x:I

    .line 16
    .line 17
    const/4 v4, 0x0

    .line 18
    aget v5, v1, v4

    .line 19
    .line 20
    sub-int/2addr v3, v5

    .line 21
    invoke-virtual {v2}, Landroid/view/ViewGroup;->getWidth()I

    .line 22
    .line 23
    .line 24
    move-result v5

    .line 25
    div-int/2addr v5, v0

    .line 26
    sub-int/2addr v3, v5

    .line 27
    int-to-float v0, v3

    .line 28
    invoke-virtual {v2, v0}, Landroid/view/ViewGroup;->setTranslationX(F)V

    .line 29
    .line 30
    .line 31
    iget-object v0, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 32
    .line 33
    iget-object v2, v0, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 34
    .line 35
    iget v3, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->$y:I

    .line 36
    .line 37
    const/4 v5, 0x1

    .line 38
    aget v1, v1, v5

    .line 39
    .line 40
    sub-int/2addr v3, v1

    .line 41
    int-to-float v1, v3

    .line 42
    iget-boolean v0, v0, Lcom/android/systemui/controls/TooltipManager;->below:Z

    .line 43
    .line 44
    if-nez v0, :cond_0

    .line 45
    .line 46
    invoke-virtual {v2}, Landroid/view/ViewGroup;->getHeight()I

    .line 47
    .line 48
    .line 49
    move-result v0

    .line 50
    goto :goto_0

    .line 51
    :cond_0
    move v0, v4

    .line 52
    :goto_0
    int-to-float v0, v0

    .line 53
    sub-float/2addr v1, v0

    .line 54
    invoke-virtual {v2, v1}, Landroid/view/ViewGroup;->setTranslationY(F)V

    .line 55
    .line 56
    .line 57
    iget-object v0, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 58
    .line 59
    iget-object v0, v0, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 60
    .line 61
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getAlpha()F

    .line 62
    .line 63
    .line 64
    move-result v0

    .line 65
    const/4 v1, 0x0

    .line 66
    cmpg-float v0, v0, v1

    .line 67
    .line 68
    if-nez v0, :cond_1

    .line 69
    .line 70
    move v4, v5

    .line 71
    :cond_1
    if-eqz v4, :cond_2

    .line 72
    .line 73
    iget-object p0, p0, Lcom/android/systemui/controls/TooltipManager$show$1;->this$0:Lcom/android/systemui/controls/TooltipManager;

    .line 74
    .line 75
    iget-object p0, p0, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 76
    .line 77
    invoke-virtual {p0}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 78
    .line 79
    .line 80
    move-result-object p0

    .line 81
    const/high16 v0, 0x3f800000    # 1.0f

    .line 82
    .line 83
    invoke-virtual {p0, v0}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    .line 84
    .line 85
    .line 86
    move-result-object p0

    .line 87
    invoke-virtual {p0}, Landroid/view/ViewPropertyAnimator;->withLayer()Landroid/view/ViewPropertyAnimator;

    .line 88
    .line 89
    .line 90
    move-result-object p0

    .line 91
    const-wide/16 v0, 0x1f4

    .line 92
    .line 93
    invoke-virtual {p0, v0, v1}, Landroid/view/ViewPropertyAnimator;->setStartDelay(J)Landroid/view/ViewPropertyAnimator;

    .line 94
    .line 95
    .line 96
    move-result-object p0

    .line 97
    const-wide/16 v0, 0x12c

    .line 98
    .line 99
    invoke-virtual {p0, v0, v1}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    .line 100
    .line 101
    .line 102
    move-result-object p0

    .line 103
    new-instance v0, Landroid/view/animation/DecelerateInterpolator;

    .line 104
    .line 105
    invoke-direct {v0}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    .line 106
    .line 107
    .line 108
    invoke-virtual {p0, v0}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    .line 109
    .line 110
    .line 111
    move-result-object p0

    .line 112
    invoke-virtual {p0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 113
    .line 114
    .line 115
    :cond_2
    return-void
.end method

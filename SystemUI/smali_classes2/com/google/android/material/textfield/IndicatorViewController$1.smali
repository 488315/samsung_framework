.class public final Lcom/google/android/material/textfield/IndicatorViewController$1;
.super Landroid/animation/AnimatorListenerAdapter;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/google/android/material/textfield/IndicatorViewController;

.field public final synthetic val$captionToHide:I

.field public final synthetic val$captionToShow:I

.field public final synthetic val$captionViewToHide:Landroid/widget/TextView;

.field public final synthetic val$captionViewToShow:Landroid/widget/TextView;


# direct methods
.method public constructor <init>(Lcom/google/android/material/textfield/IndicatorViewController;ILandroid/widget/TextView;ILandroid/widget/TextView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->this$0:Lcom/google/android/material/textfield/IndicatorViewController;

    .line 2
    .line 3
    iput p2, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionToShow:I

    .line 4
    .line 5
    iput-object p3, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToHide:Landroid/widget/TextView;

    .line 6
    .line 7
    iput p4, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionToHide:I

    .line 8
    .line 9
    iput-object p5, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToShow:Landroid/widget/TextView;

    .line 10
    .line 11
    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    .line 12
    .line 13
    .line 14
    return-void
.end method


# virtual methods
.method public final onAnimationEnd(Landroid/animation/Animator;)V
    .locals 2

    .line 1
    iget-object p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->this$0:Lcom/google/android/material/textfield/IndicatorViewController;

    .line 2
    .line 3
    iget v0, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionToShow:I

    .line 4
    .line 5
    iput v0, p1, Lcom/google/android/material/textfield/IndicatorViewController;->captionDisplayed:I

    .line 6
    .line 7
    const/4 v0, 0x0

    .line 8
    iput-object v0, p1, Lcom/google/android/material/textfield/IndicatorViewController;->captionAnimator:Landroid/animation/Animator;

    .line 9
    .line 10
    iget-object p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToHide:Landroid/widget/TextView;

    .line 11
    .line 12
    if-eqz p1, :cond_0

    .line 13
    .line 14
    const/4 v1, 0x4

    .line 15
    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 16
    .line 17
    .line 18
    iget p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionToHide:I

    .line 19
    .line 20
    const/4 v1, 0x1

    .line 21
    if-ne p1, v1, :cond_0

    .line 22
    .line 23
    iget-object p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->this$0:Lcom/google/android/material/textfield/IndicatorViewController;

    .line 24
    .line 25
    iget-object p1, p1, Lcom/google/android/material/textfield/IndicatorViewController;->errorView:Landroidx/appcompat/widget/AppCompatTextView;

    .line 26
    .line 27
    if-eqz p1, :cond_0

    .line 28
    .line 29
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 30
    .line 31
    .line 32
    :cond_0
    iget-object p1, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToShow:Landroid/widget/TextView;

    .line 33
    .line 34
    if-eqz p1, :cond_1

    .line 35
    .line 36
    const/4 v0, 0x0

    .line 37
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTranslationY(F)V

    .line 38
    .line 39
    .line 40
    iget-object p0, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToShow:Landroid/widget/TextView;

    .line 41
    .line 42
    const/high16 p1, 0x3f800000    # 1.0f

    .line 43
    .line 44
    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setAlpha(F)V

    .line 45
    .line 46
    .line 47
    :cond_1
    return-void
.end method

.method public final onAnimationStart(Landroid/animation/Animator;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/google/android/material/textfield/IndicatorViewController$1;->val$captionViewToShow:Landroid/widget/TextView;

    .line 2
    .line 3
    if-eqz p0, :cond_0

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 7
    .line 8
    .line 9
    :cond_0
    return-void
.end method

.class public final Lcom/google/android/setupdesign/template/HeaderMixin$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnPreDrawListener;


# instance fields
.field public final synthetic this$0:Lcom/google/android/setupdesign/template/HeaderMixin;

.field public final synthetic val$titleView:Landroid/widget/TextView;


# direct methods
.method public constructor <init>(Lcom/google/android/setupdesign/template/HeaderMixin;Landroid/widget/TextView;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->this$0:Lcom/google/android/setupdesign/template/HeaderMixin;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onPreDraw()Z
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroid/widget/TextView;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    invoke-virtual {v0, p0}, Landroid/view/ViewTreeObserver;->removeOnPreDrawListener(Landroid/view/ViewTreeObserver$OnPreDrawListener;)V

    .line 8
    .line 9
    .line 10
    iget-object v0, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 11
    .line 12
    invoke-virtual {v0}, Landroid/widget/TextView;->getLineCount()I

    .line 13
    .line 14
    .line 15
    move-result v0

    .line 16
    iget-object v1, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->this$0:Lcom/google/android/setupdesign/template/HeaderMixin;

    .line 17
    .line 18
    iget v2, v1, Lcom/google/android/setupdesign/template/HeaderMixin;->headerAutoSizeMaxLineOfMaxSize:I

    .line 19
    .line 20
    if-le v0, v2, :cond_0

    .line 21
    .line 22
    iget-object v0, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 23
    .line 24
    iget v1, v1, Lcom/google/android/setupdesign/template/HeaderMixin;->headerAutoSizeMinTextSizeInPx:F

    .line 25
    .line 26
    const/4 v2, 0x0

    .line 27
    invoke-virtual {v0, v2, v1}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 28
    .line 29
    .line 30
    iget-object v0, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 31
    .line 32
    iget-object v1, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->this$0:Lcom/google/android/setupdesign/template/HeaderMixin;

    .line 33
    .line 34
    iget v3, v1, Lcom/google/android/setupdesign/template/HeaderMixin;->headerAutoSizeLineExtraSpacingInPx:F

    .line 35
    .line 36
    iget v1, v1, Lcom/google/android/setupdesign/template/HeaderMixin;->headerAutoSizeMinTextSizeInPx:F

    .line 37
    .line 38
    add-float/2addr v3, v1

    .line 39
    invoke-static {v3}, Ljava/lang/Math;->round(F)I

    .line 40
    .line 41
    .line 42
    move-result v1

    .line 43
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setLineHeight(I)V

    .line 44
    .line 45
    .line 46
    iget-object p0, p0, Lcom/google/android/setupdesign/template/HeaderMixin$1;->val$titleView:Landroid/widget/TextView;

    .line 47
    .line 48
    invoke-virtual {p0}, Landroid/widget/TextView;->invalidate()V

    .line 49
    .line 50
    .line 51
    return v2

    .line 52
    :cond_0
    const/4 p0, 0x1

    .line 53
    return p0
.end method
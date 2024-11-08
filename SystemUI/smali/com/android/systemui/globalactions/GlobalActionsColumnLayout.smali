.class public Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;
.super Lcom/android/systemui/globalactions/GlobalActionsLayout;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final synthetic $r8$clinit:I


# instance fields
.field public mLastSnap:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2}, Lcom/android/systemui/globalactions/GlobalActionsLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public centerAlongEdge()V
    .locals 3

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getCurrentRotation()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x1

    .line 6
    const/4 v2, 0x0

    .line 7
    if-eq v0, v1, :cond_1

    .line 8
    .line 9
    const/4 v1, 0x3

    .line 10
    if-eq v0, v1, :cond_0

    .line 11
    .line 12
    invoke-virtual {p0, v2, v2, v2, v2}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 13
    .line 14
    .line 15
    const/16 v0, 0x15

    .line 16
    .line 17
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 18
    .line 19
    .line 20
    goto :goto_0

    .line 21
    :cond_0
    invoke-virtual {p0, v2, v2, v2, v2}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 22
    .line 23
    .line 24
    const/16 v0, 0x51

    .line 25
    .line 26
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 27
    .line 28
    .line 29
    goto :goto_0

    .line 30
    :cond_1
    invoke-virtual {p0, v2, v2, v2, v2}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 31
    .line 32
    .line 33
    const/16 v0, 0x31

    .line 34
    .line 35
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 36
    .line 37
    .line 38
    :goto_0
    return-void
.end method

.method public getAnimationDistance()F
    .locals 1

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->getGridItemSize()F

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    const/high16 v0, 0x40000000    # 2.0f

    .line 6
    .line 7
    div-float/2addr p0, v0

    .line 8
    return p0
.end method

.method public getGridItemSize()F
    .locals 1

    .line 1
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getContext()Landroid/content/Context;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    const v0, 0x7f0703b7

    .line 10
    .line 11
    .line 12
    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getDimension(I)F

    .line 13
    .line 14
    .line 15
    move-result p0

    .line 16
    return p0
.end method

.method public getPowerButtonOffsetDistance()I
    .locals 1

    .line 1
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getContext()Landroid/content/Context;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    const v0, 0x7f0703c2

    .line 10
    .line 11
    .line 12
    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getDimension(I)F

    .line 13
    .line 14
    .line 15
    move-result p0

    .line 16
    invoke-static {p0}, Ljava/lang/Math;->round(F)I

    .line 17
    .line 18
    .line 19
    move-result p0

    .line 20
    return p0
.end method

.method public final onLayout(ZIIII)V
    .locals 0

    .line 1
    invoke-super/range {p0 .. p5}, Landroid/widget/LinearLayout;->onLayout(ZIIII)V

    .line 2
    .line 3
    .line 4
    new-instance p1, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout$$ExternalSyntheticLambda0;

    .line 5
    .line 6
    invoke-direct {p1, p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;)V

    .line 7
    .line 8
    .line 9
    invoke-virtual {p0, p1}, Landroid/widget/LinearLayout;->post(Ljava/lang/Runnable;)Z

    .line 10
    .line 11
    .line 12
    return-void
.end method

.method public final onMeasure(II)V
    .locals 0

    .line 1
    invoke-super {p0, p1, p2}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->onMeasure(II)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public final onUpdateList()V
    .locals 1

    .line 1
    invoke-super {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->onUpdateList()V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->shouldReverseListItems()Z

    .line 5
    .line 6
    .line 7
    move-result v0

    .line 8
    if-eqz v0, :cond_0

    .line 9
    .line 10
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getListView()Landroid/view/ViewGroup;

    .line 11
    .line 12
    .line 13
    move-result-object p0

    .line 14
    invoke-virtual {p0}, Landroid/view/ViewGroup;->bringToFront()V

    .line 15
    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getSeparatedView()Landroid/view/ViewGroup;

    .line 19
    .line 20
    .line 21
    move-result-object p0

    .line 22
    invoke-virtual {p0}, Landroid/view/ViewGroup;->bringToFront()V

    .line 23
    .line 24
    .line 25
    :goto_0
    return-void
.end method

.method public shouldReverseListItems()Z
    .locals 3

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getCurrentRotation()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    if-nez v0, :cond_0

    .line 7
    .line 8
    return v1

    .line 9
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getCurrentLayoutDirection()I

    .line 10
    .line 11
    .line 12
    move-result p0

    .line 13
    const/4 v2, 0x1

    .line 14
    if-ne p0, v2, :cond_2

    .line 15
    .line 16
    if-ne v0, v2, :cond_1

    .line 17
    .line 18
    move v1, v2

    .line 19
    :cond_1
    return v1

    .line 20
    :cond_2
    const/4 p0, 0x3

    .line 21
    if-ne v0, p0, :cond_3

    .line 22
    .line 23
    move v1, v2

    .line 24
    :cond_3
    return v1
.end method

.method public shouldSnapToPowerButton()Z
    .locals 4

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->getPowerButtonOffsetDistance()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    invoke-virtual {p0, v1}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    .line 7
    .line 8
    .line 9
    move-result-object v2

    .line 10
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getCurrentRotation()I

    .line 11
    .line 12
    .line 13
    move-result v3

    .line 14
    if-nez v3, :cond_0

    .line 15
    .line 16
    invoke-virtual {v2}, Landroid/view/View;->getMeasuredHeight()I

    .line 17
    .line 18
    .line 19
    move-result v2

    .line 20
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getMeasuredHeight()I

    .line 21
    .line 22
    .line 23
    move-result p0

    .line 24
    goto :goto_0

    .line 25
    :cond_0
    invoke-virtual {v2}, Landroid/view/View;->getMeasuredWidth()I

    .line 26
    .line 27
    .line 28
    move-result v2

    .line 29
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getMeasuredWidth()I

    .line 30
    .line 31
    .line 32
    move-result p0

    .line 33
    :goto_0
    add-int/2addr v2, v0

    .line 34
    if-ge v2, p0, :cond_1

    .line 35
    .line 36
    const/4 v1, 0x1

    .line 37
    :cond_1
    return v1
.end method

.method public snapToPowerButton()V
    .locals 4

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->getPowerButtonOffsetDistance()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsLayout;->getCurrentRotation()I

    .line 6
    .line 7
    .line 8
    move-result v1

    .line 9
    const/4 v2, 0x1

    .line 10
    const/4 v3, 0x0

    .line 11
    if-eq v1, v2, :cond_1

    .line 12
    .line 13
    const/4 v2, 0x3

    .line 14
    if-eq v1, v2, :cond_0

    .line 15
    .line 16
    invoke-virtual {p0, v3, v0, v3, v3}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 17
    .line 18
    .line 19
    const/16 v0, 0x35

    .line 20
    .line 21
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 22
    .line 23
    .line 24
    goto :goto_0

    .line 25
    :cond_0
    invoke-virtual {p0, v3, v3, v0, v3}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 26
    .line 27
    .line 28
    const/16 v0, 0x55

    .line 29
    .line 30
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 31
    .line 32
    .line 33
    goto :goto_0

    .line 34
    :cond_1
    invoke-virtual {p0, v0, v3, v3, v3}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 35
    .line 36
    .line 37
    const/16 v0, 0x33

    .line 38
    .line 39
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 40
    .line 41
    .line 42
    :goto_0
    return-void
.end method

.method public updateSnap()V
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->shouldSnapToPowerButton()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    iget-boolean v1, p0, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->mLastSnap:Z

    .line 6
    .line 7
    if-eq v0, v1, :cond_1

    .line 8
    .line 9
    if-eqz v0, :cond_0

    .line 10
    .line 11
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->snapToPowerButton()V

    .line 12
    .line 13
    .line 14
    goto :goto_0

    .line 15
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->centerAlongEdge()V

    .line 16
    .line 17
    .line 18
    :cond_1
    :goto_0
    iput-boolean v0, p0, Lcom/android/systemui/globalactions/GlobalActionsColumnLayout;->mLastSnap:Z

    .line 19
    .line 20
    return-void
.end method

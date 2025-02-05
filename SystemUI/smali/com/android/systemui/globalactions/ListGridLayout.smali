.class public Lcom/android/systemui/globalactions/ListGridLayout;
.super Landroid/widget/LinearLayout;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mConfigs:[[I

.field public mCurrentCount:I

.field public mExpectedCount:I

.field public mReverseItems:Z

.field public mReverseSublists:Z

.field public mSwapRowsAndColumns:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 10

    .line 1
    invoke-direct {p0, p1, p2}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 2
    .line 3
    .line 4
    const/4 p1, 0x0

    .line 5
    iput p1, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mCurrentCount:I

    .line 6
    .line 7
    filled-new-array {p1, p1}, [I

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    const/4 p1, 0x1

    .line 12
    filled-new-array {p1, p1}, [I

    .line 13
    .line 14
    .line 15
    move-result-object v1

    .line 16
    const/4 p2, 0x2

    .line 17
    filled-new-array {p1, p2}, [I

    .line 18
    .line 19
    .line 20
    move-result-object v2

    .line 21
    const/4 v3, 0x3

    .line 22
    filled-new-array {p1, v3}, [I

    .line 23
    .line 24
    .line 25
    move-result-object p1

    .line 26
    filled-new-array {p2, p2}, [I

    .line 27
    .line 28
    .line 29
    move-result-object v4

    .line 30
    filled-new-array {p2, v3}, [I

    .line 31
    .line 32
    .line 33
    move-result-object v5

    .line 34
    filled-new-array {p2, v3}, [I

    .line 35
    .line 36
    .line 37
    move-result-object v6

    .line 38
    filled-new-array {v3, v3}, [I

    .line 39
    .line 40
    .line 41
    move-result-object v7

    .line 42
    filled-new-array {v3, v3}, [I

    .line 43
    .line 44
    .line 45
    move-result-object v8

    .line 46
    filled-new-array {v3, v3}, [I

    .line 47
    .line 48
    .line 49
    move-result-object v9

    .line 50
    move-object v3, p1

    .line 51
    filled-new-array/range {v0 .. v9}, [[I

    .line 52
    .line 53
    .line 54
    move-result-object p1

    .line 55
    iput-object p1, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mConfigs:[[I

    .line 56
    .line 57
    return-void
.end method


# virtual methods
.method public getParentView(IZZ)Landroid/view/ViewGroup;
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/ListGridLayout;->getRowCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-eqz v0, :cond_3

    .line 6
    .line 7
    if-gez p1, :cond_0

    .line 8
    .line 9
    goto :goto_1

    .line 10
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mConfigs:[[I

    .line 11
    .line 12
    array-length v0, v0

    .line 13
    add-int/lit8 v0, v0, -0x1

    .line 14
    .line 15
    add-int/lit8 v0, v0, -0x1

    .line 16
    .line 17
    invoke-static {p1, v0}, Ljava/lang/Math;->min(II)I

    .line 18
    .line 19
    .line 20
    move-result p1

    .line 21
    invoke-virtual {p0}, Lcom/android/systemui/globalactions/ListGridLayout;->getRowCount()I

    .line 22
    .line 23
    .line 24
    move-result v0

    .line 25
    if-eqz p3, :cond_1

    .line 26
    .line 27
    div-int/2addr p1, v0

    .line 28
    int-to-double v0, p1

    .line 29
    invoke-static {v0, v1}, Ljava/lang/Math;->floor(D)D

    .line 30
    .line 31
    .line 32
    move-result-wide v0

    .line 33
    double-to-int p1, v0

    .line 34
    goto :goto_0

    .line 35
    :cond_1
    rem-int/2addr p1, v0

    .line 36
    :goto_0
    if-eqz p2, :cond_2

    .line 37
    .line 38
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getChildCount()I

    .line 39
    .line 40
    .line 41
    move-result p2

    .line 42
    add-int/lit8 p1, p1, 0x1

    .line 43
    .line 44
    sub-int p1, p2, p1

    .line 45
    .line 46
    :cond_2
    invoke-virtual {p0, p1}, Lcom/android/systemui/globalactions/ListGridLayout;->getSublist(I)Landroid/view/ViewGroup;

    .line 47
    .line 48
    .line 49
    move-result-object p0

    .line 50
    return-object p0

    .line 51
    :cond_3
    :goto_1
    const/4 p0, 0x0

    .line 52
    return-object p0
.end method

.method public final getRowCount()I
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mExpectedCount:I

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-gez v0, :cond_0

    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mConfigs:[[I

    .line 7
    .line 8
    aget-object p0, p0, v1

    .line 9
    .line 10
    goto :goto_0

    .line 11
    :cond_0
    iget-object v2, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mConfigs:[[I

    .line 12
    .line 13
    array-length v2, v2

    .line 14
    add-int/lit8 v2, v2, -0x1

    .line 15
    .line 16
    invoke-static {v2, v0}, Ljava/lang/Math;->min(II)I

    .line 17
    .line 18
    .line 19
    move-result v0

    .line 20
    iget-object p0, p0, Lcom/android/systemui/globalactions/ListGridLayout;->mConfigs:[[I

    .line 21
    .line 22
    aget-object p0, p0, v0

    .line 23
    .line 24
    :goto_0
    aget p0, p0, v1

    .line 25
    .line 26
    return p0
.end method

.method public getSublist(I)Landroid/view/ViewGroup;
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    check-cast p0, Landroid/view/ViewGroup;

    .line 6
    .line 7
    return-object p0
.end method

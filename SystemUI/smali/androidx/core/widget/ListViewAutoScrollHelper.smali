.class public final Landroidx/core/widget/ListViewAutoScrollHelper;
.super Landroidx/core/widget/AutoScrollHelper;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mTarget:Landroid/widget/ListView;


# direct methods
.method public constructor <init>(Landroid/widget/ListView;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Landroidx/core/widget/AutoScrollHelper;-><init>(Landroid/view/View;)V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Landroidx/core/widget/ListViewAutoScrollHelper;->mTarget:Landroid/widget/ListView;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final canTargetScrollHorizontally()V
    .locals 0

    .line 1
    return-void
.end method

.method public final canTargetScrollVertically(I)Z
    .locals 6

    .line 1
    iget-object p0, p0, Landroidx/core/widget/ListViewAutoScrollHelper;->mTarget:Landroid/widget/ListView;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/widget/ListView;->getCount()I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    const/4 v1, 0x0

    .line 8
    if-nez v0, :cond_0

    .line 9
    .line 10
    return v1

    .line 11
    :cond_0
    invoke-virtual {p0}, Landroid/widget/ListView;->getChildCount()I

    .line 12
    .line 13
    .line 14
    move-result v2

    .line 15
    invoke-virtual {p0}, Landroid/widget/ListView;->getFirstVisiblePosition()I

    .line 16
    .line 17
    .line 18
    move-result v3

    .line 19
    add-int v4, v3, v2

    .line 20
    .line 21
    const/4 v5, 0x1

    .line 22
    if-lez p1, :cond_1

    .line 23
    .line 24
    if-lt v4, v0, :cond_2

    .line 25
    .line 26
    sub-int/2addr v2, v5

    .line 27
    invoke-virtual {p0, v2}, Landroid/widget/ListView;->getChildAt(I)Landroid/view/View;

    .line 28
    .line 29
    .line 30
    move-result-object p1

    .line 31
    invoke-virtual {p1}, Landroid/view/View;->getBottom()I

    .line 32
    .line 33
    .line 34
    move-result p1

    .line 35
    invoke-virtual {p0}, Landroid/widget/ListView;->getHeight()I

    .line 36
    .line 37
    .line 38
    move-result p0

    .line 39
    if-gt p1, p0, :cond_2

    .line 40
    .line 41
    return v1

    .line 42
    :cond_1
    if-gez p1, :cond_3

    .line 43
    .line 44
    if-gtz v3, :cond_2

    .line 45
    .line 46
    invoke-virtual {p0, v1}, Landroid/widget/ListView;->getChildAt(I)Landroid/view/View;

    .line 47
    .line 48
    .line 49
    move-result-object p0

    .line 50
    invoke-virtual {p0}, Landroid/view/View;->getTop()I

    .line 51
    .line 52
    .line 53
    move-result p0

    .line 54
    if-ltz p0, :cond_2

    .line 55
    .line 56
    return v1

    .line 57
    :cond_2
    return v5

    .line 58
    :cond_3
    return v1
.end method

.method public final scrollTargetBy(I)V
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/core/widget/ListViewAutoScrollHelper;->mTarget:Landroid/widget/ListView;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/widget/ListView;->scrollListBy(I)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

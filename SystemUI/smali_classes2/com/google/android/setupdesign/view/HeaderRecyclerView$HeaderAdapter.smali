.class public final Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;
.super Landroidx/recyclerview/widget/RecyclerView$Adapter;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

.field public header:Landroid/view/View;

.field public final observer:Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter$1;


# direct methods
.method public constructor <init>(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroidx/recyclerview/widget/RecyclerView$Adapter;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter$1;

    .line 5
    .line 6
    invoke-direct {v0, p0}, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter$1;-><init>(Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;)V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->observer:Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter$1;

    .line 10
    .line 11
    iput-object p1, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 12
    .line 13
    invoke-virtual {p1, v0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->registerAdapterDataObserver(Landroidx/recyclerview/widget/RecyclerView$AdapterDataObserver;)V

    .line 14
    .line 15
    .line 16
    iget-boolean p1, p1, Landroidx/recyclerview/widget/RecyclerView$Adapter;->mHasStableIds:Z

    .line 17
    .line 18
    invoke-virtual {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->setHasStableIds(Z)V

    .line 19
    .line 20
    .line 21
    return-void
.end method


# virtual methods
.method public final getItemCount()I
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->getItemCount()I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 8
    .line 9
    if-eqz p0, :cond_0

    .line 10
    .line 11
    add-int/lit8 v0, v0, 0x1

    .line 12
    .line 13
    :cond_0
    return v0
.end method

.method public final getItemId(I)J
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    add-int/lit8 p1, p1, -0x1

    .line 6
    .line 7
    :cond_0
    if-gez p1, :cond_1

    .line 8
    .line 9
    const-wide p0, 0x7fffffffffffffffL

    .line 10
    .line 11
    .line 12
    .line 13
    .line 14
    return-wide p0

    .line 15
    :cond_1
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 16
    .line 17
    invoke-virtual {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->getItemId(I)J

    .line 18
    .line 19
    .line 20
    move-result-wide p0

    .line 21
    return-wide p0
.end method

.method public final getItemViewType(I)I
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    add-int/lit8 p1, p1, -0x1

    .line 6
    .line 7
    :cond_0
    if-gez p1, :cond_1

    .line 8
    .line 9
    const p0, 0x7fffffff

    .line 10
    .line 11
    .line 12
    return p0

    .line 13
    :cond_1
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 14
    .line 15
    invoke-virtual {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->getItemViewType(I)I

    .line 16
    .line 17
    .line 18
    move-result p0

    .line 19
    return p0
.end method

.method public final onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    add-int/lit8 p2, p2, -0x1

    .line 6
    .line 7
    :cond_0
    instance-of v1, p1, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderViewHolder;

    .line 8
    .line 9
    if-eqz v1, :cond_3

    .line 10
    .line 11
    if-eqz v0, :cond_2

    .line 12
    .line 13
    invoke-virtual {v0}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 14
    .line 15
    .line 16
    move-result-object p2

    .line 17
    if-eqz p2, :cond_1

    .line 18
    .line 19
    iget-object p2, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 20
    .line 21
    invoke-virtual {p2}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 22
    .line 23
    .line 24
    move-result-object p2

    .line 25
    check-cast p2, Landroid/view/ViewGroup;

    .line 26
    .line 27
    iget-object v0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 28
    .line 29
    invoke-virtual {p2, v0}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 30
    .line 31
    .line 32
    :cond_1
    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    .line 33
    .line 34
    check-cast p1, Landroid/widget/FrameLayout;

    .line 35
    .line 36
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->header:Landroid/view/View;

    .line 37
    .line 38
    invoke-virtual {p1, p0}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;)V

    .line 39
    .line 40
    .line 41
    goto :goto_0

    .line 42
    :cond_2
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 43
    .line 44
    const-string p1, "HeaderViewHolder cannot find mHeader"

    .line 45
    .line 46
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 47
    .line 48
    .line 49
    throw p0

    .line 50
    :cond_3
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 51
    .line 52
    invoke-virtual {p0, p1, p2}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V

    .line 53
    .line 54
    .line 55
    :goto_0
    return-void
.end method

.method public final onCreateViewHolder(Landroidx/recyclerview/widget/RecyclerView;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
    .locals 1

    .line 1
    const v0, 0x7fffffff

    .line 2
    .line 3
    .line 4
    if-ne p2, v0, :cond_0

    .line 5
    .line 6
    new-instance p0, Landroid/widget/FrameLayout;

    .line 7
    .line 8
    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    .line 9
    .line 10
    .line 11
    move-result-object p1

    .line 12
    invoke-direct {p0, p1}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    .line 13
    .line 14
    .line 15
    new-instance p1, Landroid/widget/FrameLayout$LayoutParams;

    .line 16
    .line 17
    const/4 p2, -0x1

    .line 18
    const/4 v0, -0x2

    .line 19
    invoke-direct {p1, p2, v0}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 20
    .line 21
    .line 22
    invoke-virtual {p0, p1}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 23
    .line 24
    .line 25
    new-instance p1, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderViewHolder;

    .line 26
    .line 27
    invoke-direct {p1, p0}, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderViewHolder;-><init>(Landroid/view/View;)V

    .line 28
    .line 29
    .line 30
    return-object p1

    .line 31
    :cond_0
    iget-object p0, p0, Lcom/google/android/setupdesign/view/HeaderRecyclerView$HeaderAdapter;->adapter:Landroidx/recyclerview/widget/RecyclerView$Adapter;

    .line 32
    .line 33
    invoke-virtual {p0, p1, p2}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->onCreateViewHolder(Landroidx/recyclerview/widget/RecyclerView;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 34
    .line 35
    .line 36
    move-result-object p0

    .line 37
    return-object p0
.end method

.class public final Landroidx/leanback/widget/GridLayoutManager;
.super Landroidx/recyclerview/widget/RecyclerView$LayoutManager;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Landroidx/leanback/widget/GridLayoutManager$LayoutParams;
    }
.end annotation


# static fields
.field public static final sTempRect:Landroid/graphics/Rect;

.field public static final sTwoInts:[I


# instance fields
.field public mBaseGridView:Landroidx/leanback/widget/BaseGridView;

.field public mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

.field public final mChildVisibility:I

.field public final mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

.field public mCurrentSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

.field public mDisappearingPositions:[I

.field public mExtraLayoutSpaceInPreLayout:I

.field public mFixedRowSizeSecondary:I

.field public mFlag:I

.field public mFocusPosition:I

.field public mFocusPositionOffset:I

.field public mGravity:I

.field public mGrid:Landroidx/leanback/widget/Grid;

.field public final mGridProvider:Landroidx/leanback/widget/GridLayoutManager$2;

.field public final mItemAlignment:Landroidx/leanback/widget/ItemAlignment;

.field public final mMaxPendingMoves:I

.field public mMaxSizeSecondary:I

.field public final mMeasuredDimension:[I

.field public mNumRows:I

.field public mNumRowsRequested:I

.field public mOrientation:I

.field public mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

.field public mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

.field public mPositionDeltaInPreLayout:I

.field public final mPositionToRowInPostLayout:Landroid/util/SparseIntArray;

.field public mPrimaryScrollExtra:I

.field public mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

.field public final mRequestLayoutRunnable:Landroidx/leanback/widget/GridLayoutManager$1;

.field public mRowSizeSecondary:[I

.field public mRowSizeSecondaryRequested:I

.field public mSaveContextLevel:I

.field public mScrollOffsetSecondary:I

.field public mSizePrimary:I

.field public final mSmoothScrollSpeedFactor:F

.field public mSpacingPrimary:I

.field public mSpacingSecondary:I

.field public mState:Landroidx/recyclerview/widget/RecyclerView$State;

.field public mSubFocusPosition:I

.field public mVerticalSpacing:I

.field public final mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Landroid/graphics/Rect;

    .line 2
    .line 3
    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Landroidx/leanback/widget/GridLayoutManager;->sTempRect:Landroid/graphics/Rect;

    .line 7
    .line 8
    const/4 v0, 0x2

    .line 9
    new-array v0, v0, [I

    .line 10
    .line 11
    sput-object v0, Landroidx/leanback/widget/GridLayoutManager;->sTwoInts:[I

    .line 12
    .line 13
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    const/4 v0, 0x0

    .line 1
    invoke-direct {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;-><init>(Landroidx/leanback/widget/BaseGridView;)V

    return-void
.end method

.method public constructor <init>(Landroidx/leanback/widget/BaseGridView;)V
    .locals 3

    .line 2
    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;-><init>()V

    const/high16 v0, 0x3f800000    # 1.0f

    .line 3
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSmoothScrollSpeedFactor:F

    const/16 v0, 0xa

    .line 4
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mMaxPendingMoves:I

    const/4 v0, 0x0

    .line 5
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 6
    new-instance v1, Landroidx/recyclerview/widget/OrientationHelper$1;

    invoke-direct {v1, p0}, Landroidx/recyclerview/widget/OrientationHelper$1;-><init>(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    .line 7
    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

    .line 8
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPositionToRowInPostLayout:Landroid/util/SparseIntArray;

    const v1, 0x36200

    .line 9
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    const/4 v1, 0x0

    .line 10
    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    const/4 v1, -0x1

    .line 11
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 12
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 13
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    const v2, 0x800033

    .line 14
    iput v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mGravity:I

    const/4 v2, 0x1

    .line 15
    iput v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRowsRequested:I

    .line 16
    new-instance v2, Landroidx/leanback/widget/WindowAlignment;

    invoke-direct {v2}, Landroidx/leanback/widget/WindowAlignment;-><init>()V

    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 17
    new-instance v2, Landroidx/leanback/widget/ItemAlignment;

    invoke-direct {v2}, Landroidx/leanback/widget/ItemAlignment;-><init>()V

    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mItemAlignment:Landroidx/leanback/widget/ItemAlignment;

    const/4 v2, 0x2

    new-array v2, v2, [I

    .line 18
    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mMeasuredDimension:[I

    .line 19
    new-instance v2, Landroidx/leanback/widget/ViewsStateBundle;

    invoke-direct {v2}, Landroidx/leanback/widget/ViewsStateBundle;-><init>()V

    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 20
    new-instance v2, Landroidx/leanback/widget/GridLayoutManager$1;

    invoke-direct {v2, p0}, Landroidx/leanback/widget/GridLayoutManager$1;-><init>(Landroidx/leanback/widget/GridLayoutManager;)V

    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mRequestLayoutRunnable:Landroidx/leanback/widget/GridLayoutManager$1;

    .line 21
    new-instance v2, Landroidx/leanback/widget/GridLayoutManager$2;

    invoke-direct {v2, p0}, Landroidx/leanback/widget/GridLayoutManager$2;-><init>(Landroidx/leanback/widget/GridLayoutManager;)V

    iput-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mGridProvider:Landroidx/leanback/widget/GridLayoutManager$2;

    .line 22
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 23
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildVisibility:I

    .line 24
    iget-boolean p1, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mItemPrefetchEnabled:Z

    if-eqz p1, :cond_0

    .line 25
    iput-boolean v0, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mItemPrefetchEnabled:Z

    .line 26
    iput v0, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mPrefetchMaxCountObserved:I

    .line 27
    iget-object p0, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    if-eqz p0, :cond_0

    .line 28
    iget-object p0, p0, Landroidx/recyclerview/widget/RecyclerView;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Recycler;->updateViewCacheSize()V

    :cond_0
    return-void
.end method

.method public static getAdapterPositionByView(Landroid/view/View;)I
    .locals 2

    .line 1
    const/4 v0, -0x1

    .line 2
    if-nez p0, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    invoke-virtual {p0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 10
    .line 11
    if-eqz p0, :cond_2

    .line 12
    .line 13
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->isItemRemoved()Z

    .line 14
    .line 15
    .line 16
    move-result v1

    .line 17
    if-eqz v1, :cond_1

    .line 18
    .line 19
    goto :goto_0

    .line 20
    :cond_1
    iget-object p0, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mViewHolder:Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 21
    .line 22
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getAbsoluteAdapterPosition()I

    .line 23
    .line 24
    .line 25
    move-result v0

    .line 26
    :cond_2
    :goto_0
    return v0
.end method

.method public static getDecoratedMeasuredHeightWithMargin(Landroid/view/View;)I
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 6
    .line 7
    invoke-static {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getDecoratedMeasuredHeight(Landroid/view/View;)I

    .line 8
    .line 9
    .line 10
    move-result p0

    .line 11
    iget v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    .line 12
    .line 13
    add-int/2addr p0, v1

    .line 14
    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 15
    .line 16
    add-int/2addr p0, v0

    .line 17
    return p0
.end method

.method public static getDecoratedMeasuredWidthWithMargin(Landroid/view/View;)I
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 6
    .line 7
    invoke-static {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getDecoratedMeasuredWidth(Landroid/view/View;)I

    .line 8
    .line 9
    .line 10
    move-result p0

    .line 11
    iget v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->leftMargin:I

    .line 12
    .line 13
    add-int/2addr p0, v1

    .line 14
    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->rightMargin:I

    .line 15
    .line 16
    add-int/2addr p0, v0

    .line 17
    return p0
.end method

.method public static getSubPositionByView(Landroid/view/View;Landroid/view/View;)I
    .locals 7

    .line 1
    if-eqz p0, :cond_4

    .line 2
    .line 3
    if-nez p1, :cond_0

    .line 4
    .line 5
    goto :goto_3

    .line 6
    :cond_0
    invoke-virtual {p0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 7
    .line 8
    .line 9
    move-result-object v0

    .line 10
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 11
    .line 12
    iget-object v0, v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignmentFacet:Landroidx/leanback/widget/ItemAlignmentFacet;

    .line 13
    .line 14
    if-eqz v0, :cond_4

    .line 15
    .line 16
    iget-object v0, v0, Landroidx/leanback/widget/ItemAlignmentFacet;->mAlignmentDefs:[Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;

    .line 17
    .line 18
    array-length v1, v0

    .line 19
    const/4 v2, 0x1

    .line 20
    if-le v1, v2, :cond_4

    .line 21
    .line 22
    :goto_0
    if-eq p1, p0, :cond_4

    .line 23
    .line 24
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    .line 25
    .line 26
    .line 27
    move-result v1

    .line 28
    const/4 v3, -0x1

    .line 29
    if-eq v1, v3, :cond_3

    .line 30
    .line 31
    move v4, v2

    .line 32
    :goto_1
    array-length v5, v0

    .line 33
    if-ge v4, v5, :cond_3

    .line 34
    .line 35
    aget-object v5, v0, v4

    .line 36
    .line 37
    iget v6, v5, Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;->mFocusViewId:I

    .line 38
    .line 39
    if-eq v6, v3, :cond_1

    .line 40
    .line 41
    goto :goto_2

    .line 42
    :cond_1
    iget v6, v5, Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;->mViewId:I

    .line 43
    .line 44
    :goto_2
    if-ne v6, v1, :cond_2

    .line 45
    .line 46
    return v4

    .line 47
    :cond_2
    add-int/lit8 v4, v4, 0x1

    .line 48
    .line 49
    goto :goto_1

    .line 50
    :cond_3
    invoke-virtual {p1}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 51
    .line 52
    .line 53
    move-result-object p1

    .line 54
    check-cast p1, Landroid/view/View;

    .line 55
    .line 56
    goto :goto_0

    .line 57
    :cond_4
    :goto_3
    const/4 p0, 0x0

    .line 58
    return p0
.end method


# virtual methods
.method public final appendVisibleItems()V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 2
    .line 3
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 4
    .line 5
    const/high16 v2, 0x40000

    .line 6
    .line 7
    and-int/2addr v1, v2

    .line 8
    const/4 v2, 0x0

    .line 9
    if-eqz v1, :cond_0

    .line 10
    .line 11
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 12
    .line 13
    rsub-int/lit8 p0, p0, 0x0

    .line 14
    .line 15
    goto :goto_0

    .line 16
    :cond_0
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 17
    .line 18
    add-int/2addr v1, v2

    .line 19
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 20
    .line 21
    add-int/2addr p0, v1

    .line 22
    :goto_0
    invoke-virtual {v0, p0, v2}, Landroidx/leanback/widget/Grid;->appendVisibleItems(IZ)Z

    .line 23
    .line 24
    .line 25
    return-void
.end method

.method public final canScrollHorizontally()Z
    .locals 2

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    if-eqz v0, :cond_1

    .line 5
    .line 6
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 7
    .line 8
    if-le p0, v1, :cond_0

    .line 9
    .line 10
    goto :goto_0

    .line 11
    :cond_0
    const/4 v1, 0x0

    .line 12
    :cond_1
    :goto_0
    return v1
.end method

.method public final canScrollVertically()Z
    .locals 2

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    if-eq v0, v1, :cond_1

    .line 5
    .line 6
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 7
    .line 8
    if-le p0, v1, :cond_0

    .line 9
    .line 10
    goto :goto_0

    .line 11
    :cond_0
    const/4 v1, 0x0

    .line 12
    :cond_1
    :goto_0
    return v1
.end method

.method public final checkLayoutParams(Landroidx/recyclerview/widget/RecyclerView$LayoutParams;)Z
    .locals 0

    .line 1
    instance-of p0, p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 2
    .line 3
    return p0
.end method

.method public final collectAdjacentPrefetchPositions(IILandroidx/recyclerview/widget/RecyclerView$State;Landroidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl;)V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    :try_start_0
    invoke-virtual {p0, v0, p3}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 3
    .line 4
    .line 5
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 6
    .line 7
    if-nez p3, :cond_0

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    move p1, p2

    .line 11
    :goto_0
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 12
    .line 13
    .line 14
    move-result p2

    .line 15
    if-eqz p2, :cond_3

    .line 16
    .line 17
    if-nez p1, :cond_1

    .line 18
    .line 19
    goto :goto_2

    .line 20
    :cond_1
    const/4 p2, 0x0

    .line 21
    if-gez p1, :cond_2

    .line 22
    .line 23
    goto :goto_1

    .line 24
    :cond_2
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 25
    .line 26
    add-int/2addr p2, p3

    .line 27
    :goto_1
    iget-object p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 28
    .line 29
    invoke-virtual {p3, p2, p1, p4}, Landroidx/leanback/widget/Grid;->collectAdjacentPrefetchPositions(IILandroidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 30
    .line 31
    .line 32
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 33
    .line 34
    .line 35
    return-void

    .line 36
    :cond_3
    :goto_2
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 37
    .line 38
    .line 39
    return-void

    .line 40
    :catchall_0
    move-exception p1

    .line 41
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 42
    .line 43
    .line 44
    throw p1
.end method

.method public final collectInitialPrefetchPositions(ILandroidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl;)V
    .locals 4

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 2
    .line 3
    iget v0, v0, Landroidx/leanback/widget/BaseGridView;->mInitialPrefetchItemCount:I

    .line 4
    .line 5
    if-eqz p1, :cond_0

    .line 6
    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 10
    .line 11
    add-int/lit8 v1, v0, -0x1

    .line 12
    .line 13
    div-int/lit8 v1, v1, 0x2

    .line 14
    .line 15
    sub-int/2addr p0, v1

    .line 16
    sub-int v1, p1, v0

    .line 17
    .line 18
    invoke-static {p0, v1}, Ljava/lang/Math;->min(II)I

    .line 19
    .line 20
    .line 21
    move-result p0

    .line 22
    const/4 v1, 0x0

    .line 23
    invoke-static {v1, p0}, Ljava/lang/Math;->max(II)I

    .line 24
    .line 25
    .line 26
    move-result p0

    .line 27
    move v2, p0

    .line 28
    :goto_0
    if-ge v2, p1, :cond_0

    .line 29
    .line 30
    add-int v3, p0, v0

    .line 31
    .line 32
    if-ge v2, v3, :cond_0

    .line 33
    .line 34
    invoke-virtual {p2, v2, v1}, Landroidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl;->addPosition(II)V

    .line 35
    .line 36
    .line 37
    add-int/lit8 v2, v2, 0x1

    .line 38
    .line 39
    goto :goto_0

    .line 40
    :cond_0
    return-void
.end method

.method public final dispatchChildSelected()V
    .locals 8

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/4 v2, 0x1

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 8
    .line 9
    .line 10
    move-result v0

    .line 11
    if-lez v0, :cond_0

    .line 12
    .line 13
    move v0, v2

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move v0, v1

    .line 16
    :goto_0
    if-nez v0, :cond_1

    .line 17
    .line 18
    return-void

    .line 19
    :cond_1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 20
    .line 21
    const/4 v3, -0x1

    .line 22
    const/4 v4, 0x0

    .line 23
    if-ne v0, v3, :cond_2

    .line 24
    .line 25
    move-object v0, v4

    .line 26
    goto :goto_1

    .line 27
    :cond_2
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 28
    .line 29
    .line 30
    move-result-object v0

    .line 31
    :goto_1
    if-eqz v0, :cond_4

    .line 32
    .line 33
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 34
    .line 35
    invoke-virtual {v4, v0}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 36
    .line 37
    .line 38
    move-result-object v0

    .line 39
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 40
    .line 41
    iget v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 42
    .line 43
    iget-object v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 44
    .line 45
    if-nez v6, :cond_3

    .line 46
    .line 47
    goto :goto_4

    .line 48
    :cond_3
    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    .line 49
    .line 50
    .line 51
    move-result v6

    .line 52
    :goto_2
    add-int/2addr v6, v3

    .line 53
    if-ltz v6, :cond_6

    .line 54
    .line 55
    iget-object v7, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 56
    .line 57
    invoke-virtual {v7, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 58
    .line 59
    .line 60
    move-result-object v7

    .line 61
    check-cast v7, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;

    .line 62
    .line 63
    invoke-virtual {v7, v4, v0, v5}, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;->onChildViewHolderSelected(Landroidx/leanback/widget/BaseGridView;Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V

    .line 64
    .line 65
    .line 66
    goto :goto_2

    .line 67
    :cond_4
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 68
    .line 69
    iget-object v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 70
    .line 71
    if-nez v5, :cond_5

    .line 72
    .line 73
    goto :goto_4

    .line 74
    :cond_5
    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    .line 75
    .line 76
    .line 77
    move-result v5

    .line 78
    :goto_3
    add-int/2addr v5, v3

    .line 79
    if-ltz v5, :cond_6

    .line 80
    .line 81
    iget-object v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 82
    .line 83
    invoke-virtual {v6, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 84
    .line 85
    .line 86
    move-result-object v6

    .line 87
    check-cast v6, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;

    .line 88
    .line 89
    invoke-virtual {v6, v0, v4, v3}, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;->onChildViewHolderSelected(Landroidx/leanback/widget/BaseGridView;Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V

    .line 90
    .line 91
    .line 92
    goto :goto_3

    .line 93
    :cond_6
    :goto_4
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 94
    .line 95
    and-int/lit8 v0, v0, 0x3

    .line 96
    .line 97
    if-eq v0, v2, :cond_8

    .line 98
    .line 99
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 100
    .line 101
    invoke-virtual {v0}, Landroid/view/ViewGroup;->isLayoutRequested()Z

    .line 102
    .line 103
    .line 104
    move-result v0

    .line 105
    if-nez v0, :cond_8

    .line 106
    .line 107
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 108
    .line 109
    .line 110
    move-result v0

    .line 111
    :goto_5
    if-ge v1, v0, :cond_8

    .line 112
    .line 113
    invoke-virtual {p0, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 114
    .line 115
    .line 116
    move-result-object v2

    .line 117
    invoke-virtual {v2}, Landroid/view/View;->isLayoutRequested()Z

    .line 118
    .line 119
    .line 120
    move-result v2

    .line 121
    if-eqz v2, :cond_7

    .line 122
    .line 123
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 124
    .line 125
    sget-object v1, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 126
    .line 127
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mRequestLayoutRunnable:Landroidx/leanback/widget/GridLayoutManager$1;

    .line 128
    .line 129
    invoke-static {v0, p0}, Landroidx/core/view/ViewCompat$Api16Impl;->postOnAnimation(Landroid/view/View;Ljava/lang/Runnable;)V

    .line 130
    .line 131
    .line 132
    goto :goto_6

    .line 133
    :cond_7
    add-int/lit8 v1, v1, 0x1

    .line 134
    .line 135
    goto :goto_5

    .line 136
    :cond_8
    :goto_6
    return-void
.end method

.method public final dispatchChildSelectedAndPositioned()V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-lez v0, :cond_0

    .line 10
    .line 11
    const/4 v0, 0x1

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 v0, 0x0

    .line 14
    :goto_0
    if-nez v0, :cond_1

    .line 15
    .line 16
    return-void

    .line 17
    :cond_1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 18
    .line 19
    const/4 v1, -0x1

    .line 20
    if-ne v0, v1, :cond_2

    .line 21
    .line 22
    const/4 v0, 0x0

    .line 23
    goto :goto_1

    .line 24
    :cond_2
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 25
    .line 26
    .line 27
    move-result-object v0

    .line 28
    :goto_1
    if-eqz v0, :cond_4

    .line 29
    .line 30
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 31
    .line 32
    invoke-virtual {v2, v0}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 33
    .line 34
    .line 35
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 36
    .line 37
    if-nez v0, :cond_3

    .line 38
    .line 39
    goto :goto_4

    .line 40
    :cond_3
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 41
    .line 42
    .line 43
    move-result v0

    .line 44
    :goto_2
    add-int/2addr v0, v1

    .line 45
    if-ltz v0, :cond_6

    .line 46
    .line 47
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 48
    .line 49
    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 50
    .line 51
    .line 52
    move-result-object v2

    .line 53
    check-cast v2, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;

    .line 54
    .line 55
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 56
    .line 57
    .line 58
    goto :goto_2

    .line 59
    :cond_4
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 60
    .line 61
    if-nez v0, :cond_5

    .line 62
    .line 63
    goto :goto_4

    .line 64
    :cond_5
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 65
    .line 66
    .line 67
    move-result v0

    .line 68
    :goto_3
    add-int/2addr v0, v1

    .line 69
    if-ltz v0, :cond_6

    .line 70
    .line 71
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildViewHolderSelectedListeners:Ljava/util/ArrayList;

    .line 72
    .line 73
    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 74
    .line 75
    .line 76
    move-result-object v2

    .line 77
    check-cast v2, Landroidx/leanback/widget/OnChildViewHolderSelectedListener;

    .line 78
    .line 79
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 80
    .line 81
    .line 82
    goto :goto_3

    .line 83
    :cond_6
    :goto_4
    return-void
.end method

.method public final generateDefaultLayoutParams()Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .locals 1

    .line 1
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 2
    .line 3
    const/4 v0, -0x2

    .line 4
    invoke-direct {p0, v0, v0}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(II)V

    .line 5
    .line 6
    .line 7
    return-object p0
.end method

.method public final generateLayoutParams(Landroid/content/Context;Landroid/util/AttributeSet;)Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .locals 0

    .line 1
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    invoke-direct {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-object p0
.end method

.method public final generateLayoutParams(Landroid/view/ViewGroup$LayoutParams;)Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .locals 0

    .line 2
    instance-of p0, p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    if-eqz p0, :cond_0

    .line 3
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    invoke-direct {p0, p1}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(Landroidx/leanback/widget/GridLayoutManager$LayoutParams;)V

    return-object p0

    .line 4
    :cond_0
    instance-of p0, p1, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    if-eqz p0, :cond_1

    .line 5
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    check-cast p1, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    invoke-direct {p0, p1}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(Landroidx/recyclerview/widget/RecyclerView$LayoutParams;)V

    return-object p0

    .line 6
    :cond_1
    instance-of p0, p1, Landroid/view/ViewGroup$MarginLayoutParams;

    if-eqz p0, :cond_2

    .line 7
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-direct {p0, p1}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(Landroid/view/ViewGroup$MarginLayoutParams;)V

    return-object p0

    .line 8
    :cond_2
    new-instance p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    invoke-direct {p0, p1}, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;-><init>(Landroid/view/ViewGroup$LayoutParams;)V

    return-object p0
.end method

.method public final getColumnCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I
    .locals 2

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    if-ne v0, v1, :cond_0

    .line 5
    .line 6
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 7
    .line 8
    if-eqz v0, :cond_0

    .line 9
    .line 10
    iget p0, v0, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 11
    .line 12
    return p0

    .line 13
    :cond_0
    invoke-super {p0, p1, p2}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getColumnCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I

    .line 14
    .line 15
    .line 16
    move-result p0

    .line 17
    return p0
.end method

.method public final getDecoratedBottom(Landroid/view/View;)I
    .locals 1

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->getBottom()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    check-cast v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 10
    .line 11
    iget-object v0, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mDecorInsets:Landroid/graphics/Rect;

    .line 12
    .line 13
    iget v0, v0, Landroid/graphics/Rect;->bottom:I

    .line 14
    .line 15
    add-int/2addr v0, p0

    .line 16
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 17
    .line 18
    .line 19
    move-result-object p0

    .line 20
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 21
    .line 22
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mBottomInset:I

    .line 23
    .line 24
    sub-int/2addr v0, p0

    .line 25
    return v0
.end method

.method public final getDecoratedBoundsWithMargins(Landroid/graphics/Rect;Landroid/view/View;)V
    .locals 1

    .line 1
    invoke-super {p0, p1, p2}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getDecoratedBoundsWithMargins(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p2}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 5
    .line 6
    .line 7
    move-result-object p0

    .line 8
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 9
    .line 10
    iget p2, p1, Landroid/graphics/Rect;->left:I

    .line 11
    .line 12
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 13
    .line 14
    add-int/2addr p2, v0

    .line 15
    iput p2, p1, Landroid/graphics/Rect;->left:I

    .line 16
    .line 17
    iget p2, p1, Landroid/graphics/Rect;->top:I

    .line 18
    .line 19
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 20
    .line 21
    add-int/2addr p2, v0

    .line 22
    iput p2, p1, Landroid/graphics/Rect;->top:I

    .line 23
    .line 24
    iget p2, p1, Landroid/graphics/Rect;->right:I

    .line 25
    .line 26
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mRightInset:I

    .line 27
    .line 28
    sub-int/2addr p2, v0

    .line 29
    iput p2, p1, Landroid/graphics/Rect;->right:I

    .line 30
    .line 31
    iget p2, p1, Landroid/graphics/Rect;->bottom:I

    .line 32
    .line 33
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mBottomInset:I

    .line 34
    .line 35
    sub-int/2addr p2, p0

    .line 36
    iput p2, p1, Landroid/graphics/Rect;->bottom:I

    .line 37
    .line 38
    return-void
.end method

.method public final getDecoratedLeft(Landroid/view/View;)I
    .locals 1

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->getLeft()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    check-cast v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 10
    .line 11
    iget-object v0, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mDecorInsets:Landroid/graphics/Rect;

    .line 12
    .line 13
    iget v0, v0, Landroid/graphics/Rect;->left:I

    .line 14
    .line 15
    sub-int/2addr p0, v0

    .line 16
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 17
    .line 18
    .line 19
    move-result-object p1

    .line 20
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 21
    .line 22
    iget p1, p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 23
    .line 24
    add-int/2addr p0, p1

    .line 25
    return p0
.end method

.method public final getDecoratedRight(Landroid/view/View;)I
    .locals 1

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->getRight()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    check-cast v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 10
    .line 11
    iget-object v0, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mDecorInsets:Landroid/graphics/Rect;

    .line 12
    .line 13
    iget v0, v0, Landroid/graphics/Rect;->right:I

    .line 14
    .line 15
    add-int/2addr v0, p0

    .line 16
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 17
    .line 18
    .line 19
    move-result-object p0

    .line 20
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 21
    .line 22
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mRightInset:I

    .line 23
    .line 24
    sub-int/2addr v0, p0

    .line 25
    return v0
.end method

.method public final getDecoratedTop(Landroid/view/View;)I
    .locals 1

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->getTop()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    check-cast v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 10
    .line 11
    iget-object v0, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mDecorInsets:Landroid/graphics/Rect;

    .line 12
    .line 13
    iget v0, v0, Landroid/graphics/Rect;->top:I

    .line 14
    .line 15
    sub-int/2addr p0, v0

    .line 16
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 17
    .line 18
    .line 19
    move-result-object p1

    .line 20
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 21
    .line 22
    iget p1, p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 23
    .line 24
    add-int/2addr p0, p1

    .line 25
    return p0
.end method

.method public final getMovement(I)I
    .locals 9

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    const/16 v1, 0x82

    .line 4
    .line 5
    const/16 v2, 0x42

    .line 6
    .line 7
    const/16 v3, 0x21

    .line 8
    .line 9
    const/4 v4, 0x0

    .line 10
    const/4 v5, 0x3

    .line 11
    const/4 v6, 0x2

    .line 12
    const/4 v7, 0x1

    .line 13
    const/16 v8, 0x11

    .line 14
    .line 15
    if-nez v0, :cond_4

    .line 16
    .line 17
    const/high16 v0, 0x40000

    .line 18
    .line 19
    if-eq p1, v8, :cond_3

    .line 20
    .line 21
    if-eq p1, v3, :cond_2

    .line 22
    .line 23
    if-eq p1, v2, :cond_1

    .line 24
    .line 25
    if-eq p1, v1, :cond_0

    .line 26
    .line 27
    goto :goto_3

    .line 28
    :cond_0
    :goto_0
    move v4, v5

    .line 29
    goto :goto_4

    .line 30
    :cond_1
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 31
    .line 32
    and-int/2addr p0, v0

    .line 33
    if-nez p0, :cond_9

    .line 34
    .line 35
    goto :goto_2

    .line 36
    :cond_2
    :goto_1
    move v4, v6

    .line 37
    goto :goto_4

    .line 38
    :cond_3
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 39
    .line 40
    and-int/2addr p0, v0

    .line 41
    if-nez p0, :cond_5

    .line 42
    .line 43
    goto :goto_4

    .line 44
    :cond_4
    if-ne v0, v7, :cond_8

    .line 45
    .line 46
    const/high16 v0, 0x80000

    .line 47
    .line 48
    if-eq p1, v8, :cond_7

    .line 49
    .line 50
    if-eq p1, v3, :cond_9

    .line 51
    .line 52
    if-eq p1, v2, :cond_6

    .line 53
    .line 54
    if-eq p1, v1, :cond_5

    .line 55
    .line 56
    goto :goto_3

    .line 57
    :cond_5
    :goto_2
    move v4, v7

    .line 58
    goto :goto_4

    .line 59
    :cond_6
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 60
    .line 61
    and-int/2addr p0, v0

    .line 62
    if-nez p0, :cond_2

    .line 63
    .line 64
    goto :goto_0

    .line 65
    :cond_7
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 66
    .line 67
    and-int/2addr p0, v0

    .line 68
    if-nez p0, :cond_0

    .line 69
    .line 70
    goto :goto_1

    .line 71
    :cond_8
    :goto_3
    move v4, v8

    .line 72
    :cond_9
    :goto_4
    return v4
.end method

.method public final getRowCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I
    .locals 1

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 6
    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    iget p0, v0, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 10
    .line 11
    return p0

    .line 12
    :cond_0
    invoke-super {p0, p1, p2}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getRowCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I

    .line 13
    .line 14
    .line 15
    move-result p0

    .line 16
    return p0
.end method

.method public final getRowSizeSecondary(I)I
    .locals 1

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    return v0

    .line 6
    :cond_0
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 7
    .line 8
    if-nez p0, :cond_1

    .line 9
    .line 10
    const/4 p0, 0x0

    .line 11
    return p0

    .line 12
    :cond_1
    aget p0, p0, p1

    .line 13
    .line 14
    return p0
.end method

.method public final getRowStartSecondary(I)I
    .locals 4

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const/high16 v1, 0x80000

    .line 4
    .line 5
    and-int/2addr v0, v1

    .line 6
    const/4 v1, 0x0

    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 10
    .line 11
    add-int/lit8 v0, v0, -0x1

    .line 12
    .line 13
    :goto_0
    if-le v0, p1, :cond_2

    .line 14
    .line 15
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->getRowSizeSecondary(I)I

    .line 16
    .line 17
    .line 18
    move-result v2

    .line 19
    iget v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 20
    .line 21
    add-int/2addr v2, v3

    .line 22
    add-int/2addr v1, v2

    .line 23
    add-int/lit8 v0, v0, -0x1

    .line 24
    .line 25
    goto :goto_0

    .line 26
    :cond_0
    move v0, v1

    .line 27
    :goto_1
    if-ge v1, p1, :cond_1

    .line 28
    .line 29
    invoke-virtual {p0, v1}, Landroidx/leanback/widget/GridLayoutManager;->getRowSizeSecondary(I)I

    .line 30
    .line 31
    .line 32
    move-result v2

    .line 33
    iget v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 34
    .line 35
    add-int/2addr v2, v3

    .line 36
    add-int/2addr v0, v2

    .line 37
    add-int/lit8 v1, v1, 0x1

    .line 38
    .line 39
    goto :goto_1

    .line 40
    :cond_1
    move v1, v0

    .line 41
    :cond_2
    return v1
.end method

.method public final getScrollPosition(Landroid/view/View;Landroid/view/View;[I)Z
    .locals 5

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 2
    .line 3
    iget-object v1, v0, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 4
    .line 5
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 6
    .line 7
    if-nez v2, :cond_0

    .line 8
    .line 9
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 10
    .line 11
    .line 12
    move-result-object v2

    .line 13
    check-cast v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 14
    .line 15
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 16
    .line 17
    .line 18
    invoke-virtual {p1}, Landroid/view/View;->getLeft()I

    .line 19
    .line 20
    .line 21
    move-result v3

    .line 22
    iget v4, v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 23
    .line 24
    add-int/2addr v3, v4

    .line 25
    iget v2, v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 26
    .line 27
    goto :goto_0

    .line 28
    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 29
    .line 30
    .line 31
    move-result-object v2

    .line 32
    check-cast v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 33
    .line 34
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 35
    .line 36
    .line 37
    invoke-virtual {p1}, Landroid/view/View;->getTop()I

    .line 38
    .line 39
    .line 40
    move-result v3

    .line 41
    iget v4, v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 42
    .line 43
    add-int/2addr v3, v4

    .line 44
    iget v2, v2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 45
    .line 46
    :goto_0
    add-int/2addr v3, v2

    .line 47
    invoke-virtual {v1, v3}, Landroidx/leanback/widget/WindowAlignment$Axis;->getScroll(I)I

    .line 48
    .line 49
    .line 50
    move-result v1

    .line 51
    const/4 v2, 0x0

    .line 52
    if-eqz p2, :cond_1

    .line 53
    .line 54
    invoke-static {p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->getSubPositionByView(Landroid/view/View;Landroid/view/View;)I

    .line 55
    .line 56
    .line 57
    move-result p2

    .line 58
    if-eqz p2, :cond_1

    .line 59
    .line 60
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 61
    .line 62
    .line 63
    move-result-object v3

    .line 64
    check-cast v3, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 65
    .line 66
    iget-object v3, v3, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 67
    .line 68
    aget p2, v3, p2

    .line 69
    .line 70
    aget v3, v3, v2

    .line 71
    .line 72
    sub-int/2addr p2, v3

    .line 73
    add-int/2addr v1, p2

    .line 74
    :cond_1
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 75
    .line 76
    if-nez p2, :cond_2

    .line 77
    .line 78
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 79
    .line 80
    .line 81
    move-result-object p2

    .line 82
    check-cast p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 83
    .line 84
    invoke-virtual {p2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 85
    .line 86
    .line 87
    invoke-virtual {p1}, Landroid/view/View;->getTop()I

    .line 88
    .line 89
    .line 90
    move-result p1

    .line 91
    iget v3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 92
    .line 93
    add-int/2addr p1, v3

    .line 94
    iget p2, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 95
    .line 96
    goto :goto_1

    .line 97
    :cond_2
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 98
    .line 99
    .line 100
    move-result-object p2

    .line 101
    check-cast p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 102
    .line 103
    invoke-virtual {p2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 104
    .line 105
    .line 106
    invoke-virtual {p1}, Landroid/view/View;->getLeft()I

    .line 107
    .line 108
    .line 109
    move-result p1

    .line 110
    iget v3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 111
    .line 112
    add-int/2addr p1, v3

    .line 113
    iget p2, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 114
    .line 115
    :goto_1
    add-int/2addr p1, p2

    .line 116
    iget-object p2, v0, Landroidx/leanback/widget/WindowAlignment;->mSecondAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 117
    .line 118
    invoke-virtual {p2, p1}, Landroidx/leanback/widget/WindowAlignment$Axis;->getScroll(I)I

    .line 119
    .line 120
    .line 121
    move-result p1

    .line 122
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mPrimaryScrollExtra:I

    .line 123
    .line 124
    add-int/2addr v1, p0

    .line 125
    const/4 p0, 0x1

    .line 126
    if-nez v1, :cond_4

    .line 127
    .line 128
    if-eqz p1, :cond_3

    .line 129
    .line 130
    goto :goto_2

    .line 131
    :cond_3
    aput v2, p3, v2

    .line 132
    .line 133
    aput v2, p3, p0

    .line 134
    .line 135
    goto :goto_3

    .line 136
    :cond_4
    :goto_2
    aput v1, p3, v2

    .line 137
    .line 138
    aput p1, p3, p0

    .line 139
    .line 140
    move v2, p0

    .line 141
    :goto_3
    return v2
.end method

.method public final getSizeSecondary()I
    .locals 2

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const/high16 v1, 0x80000

    .line 4
    .line 5
    and-int/2addr v0, v1

    .line 6
    if-eqz v0, :cond_0

    .line 7
    .line 8
    const/4 v0, 0x0

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 11
    .line 12
    add-int/lit8 v0, v0, -0x1

    .line 13
    .line 14
    :goto_0
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->getRowStartSecondary(I)I

    .line 15
    .line 16
    .line 17
    move-result v1

    .line 18
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->getRowSizeSecondary(I)I

    .line 19
    .line 20
    .line 21
    move-result p0

    .line 22
    add-int/2addr p0, v1

    .line 23
    return p0
.end method

.method public final hasCreatedFirstItem()Z
    .locals 1

    .line 1
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getItemCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 8
    .line 9
    const/4 v0, 0x0

    .line 10
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView;->findViewHolderForAdapterPosition(I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 11
    .line 12
    .line 13
    move-result-object p0

    .line 14
    if-eqz p0, :cond_1

    .line 15
    .line 16
    :cond_0
    const/4 v0, 0x1

    .line 17
    :cond_1
    return v0
.end method

.method public final hasCreatedLastItem()Z
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getItemCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x1

    .line 6
    if-eqz v0, :cond_1

    .line 7
    .line 8
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 9
    .line 10
    sub-int/2addr v0, v1

    .line 11
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView;->findViewHolderForAdapterPosition(I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 12
    .line 13
    .line 14
    move-result-object p0

    .line 15
    if-eqz p0, :cond_0

    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_0
    const/4 v1, 0x0

    .line 19
    :cond_1
    :goto_0
    return v1
.end method

.method public final isItemFullyVisible(I)Z
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 2
    .line 3
    invoke-virtual {v0, p1}, Landroidx/recyclerview/widget/RecyclerView;->findViewHolderForAdapterPosition(I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 4
    .line 5
    .line 6
    move-result-object p1

    .line 7
    const/4 v0, 0x0

    .line 8
    if-nez p1, :cond_0

    .line 9
    .line 10
    return v0

    .line 11
    :cond_0
    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    .line 12
    .line 13
    invoke-virtual {p1}, Landroid/view/View;->getLeft()I

    .line 14
    .line 15
    .line 16
    move-result v1

    .line 17
    if-ltz v1, :cond_1

    .line 18
    .line 19
    invoke-virtual {p1}, Landroid/view/View;->getRight()I

    .line 20
    .line 21
    .line 22
    move-result v1

    .line 23
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 24
    .line 25
    invoke-virtual {v2}, Landroid/view/ViewGroup;->getWidth()I

    .line 26
    .line 27
    .line 28
    move-result v2

    .line 29
    if-gt v1, v2, :cond_1

    .line 30
    .line 31
    invoke-virtual {p1}, Landroid/view/View;->getTop()I

    .line 32
    .line 33
    .line 34
    move-result v1

    .line 35
    if-ltz v1, :cond_1

    .line 36
    .line 37
    invoke-virtual {p1}, Landroid/view/View;->getBottom()I

    .line 38
    .line 39
    .line 40
    move-result p1

    .line 41
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 42
    .line 43
    invoke-virtual {p0}, Landroid/view/ViewGroup;->getHeight()I

    .line 44
    .line 45
    .line 46
    move-result p0

    .line 47
    if-gt p1, p0, :cond_1

    .line 48
    .line 49
    const/4 v0, 0x1

    .line 50
    :cond_1
    return v0
.end method

.method public final layoutChild(Landroid/view/View;IIII)V
    .locals 8

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    invoke-static {p1}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredHeightWithMargin(Landroid/view/View;)I

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    invoke-static {p1}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredWidthWithMargin(Landroid/view/View;)I

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    :goto_0
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 15
    .line 16
    if-lez v1, :cond_1

    .line 17
    .line 18
    invoke-static {v0, v1}, Ljava/lang/Math;->min(II)I

    .line 19
    .line 20
    .line 21
    move-result v0

    .line 22
    :cond_1
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGravity:I

    .line 23
    .line 24
    and-int/lit8 v2, v1, 0x70

    .line 25
    .line 26
    iget v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 27
    .line 28
    const/high16 v4, 0xc0000

    .line 29
    .line 30
    and-int/2addr v3, v4

    .line 31
    const/4 v4, 0x1

    .line 32
    if-eqz v3, :cond_2

    .line 33
    .line 34
    const v3, 0x800007

    .line 35
    .line 36
    .line 37
    and-int/2addr v1, v3

    .line 38
    invoke-static {v1, v4}, Landroid/view/Gravity;->getAbsoluteGravity(II)I

    .line 39
    .line 40
    .line 41
    move-result v1

    .line 42
    goto :goto_1

    .line 43
    :cond_2
    and-int/lit8 v1, v1, 0x7

    .line 44
    .line 45
    :goto_1
    iget v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 46
    .line 47
    if-nez v3, :cond_3

    .line 48
    .line 49
    const/16 v5, 0x30

    .line 50
    .line 51
    if-eq v2, v5, :cond_a

    .line 52
    .line 53
    :cond_3
    if-ne v3, v4, :cond_4

    .line 54
    .line 55
    const/4 v5, 0x3

    .line 56
    if-ne v1, v5, :cond_4

    .line 57
    .line 58
    goto :goto_3

    .line 59
    :cond_4
    if-nez v3, :cond_5

    .line 60
    .line 61
    const/16 v5, 0x50

    .line 62
    .line 63
    if-eq v2, v5, :cond_6

    .line 64
    .line 65
    :cond_5
    if-ne v3, v4, :cond_7

    .line 66
    .line 67
    const/4 v5, 0x5

    .line 68
    if-ne v1, v5, :cond_7

    .line 69
    .line 70
    :cond_6
    invoke-virtual {p0, p2}, Landroidx/leanback/widget/GridLayoutManager;->getRowSizeSecondary(I)I

    .line 71
    .line 72
    .line 73
    move-result p2

    .line 74
    sub-int/2addr p2, v0

    .line 75
    goto :goto_2

    .line 76
    :cond_7
    if-nez v3, :cond_8

    .line 77
    .line 78
    const/16 v5, 0x10

    .line 79
    .line 80
    if-eq v2, v5, :cond_9

    .line 81
    .line 82
    :cond_8
    if-ne v3, v4, :cond_a

    .line 83
    .line 84
    if-ne v1, v4, :cond_a

    .line 85
    .line 86
    :cond_9
    invoke-virtual {p0, p2}, Landroidx/leanback/widget/GridLayoutManager;->getRowSizeSecondary(I)I

    .line 87
    .line 88
    .line 89
    move-result p2

    .line 90
    sub-int/2addr p2, v0

    .line 91
    div-int/lit8 p2, p2, 0x2

    .line 92
    .line 93
    :goto_2
    add-int/2addr p5, p2

    .line 94
    :cond_a
    :goto_3
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 95
    .line 96
    add-int/2addr v0, p5

    .line 97
    if-nez p2, :cond_b

    .line 98
    .line 99
    goto :goto_4

    .line 100
    :cond_b
    move v6, p5

    .line 101
    move p5, p3

    .line 102
    move p3, v6

    .line 103
    move v7, v0

    .line 104
    move v0, p4

    .line 105
    move p4, v7

    .line 106
    :goto_4
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 107
    .line 108
    .line 109
    move-result-object p2

    .line 110
    check-cast p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 111
    .line 112
    invoke-static {p1, p3, p5, p4, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->layoutDecoratedWithMargins(Landroid/view/View;IIII)V

    .line 113
    .line 114
    .line 115
    sget-object v1, Landroidx/leanback/widget/GridLayoutManager;->sTempRect:Landroid/graphics/Rect;

    .line 116
    .line 117
    invoke-super {p0, v1, p1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getDecoratedBoundsWithMargins(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 118
    .line 119
    .line 120
    iget v2, v1, Landroid/graphics/Rect;->left:I

    .line 121
    .line 122
    sub-int/2addr p3, v2

    .line 123
    iget v2, v1, Landroid/graphics/Rect;->top:I

    .line 124
    .line 125
    sub-int/2addr p5, v2

    .line 126
    iget v2, v1, Landroid/graphics/Rect;->right:I

    .line 127
    .line 128
    sub-int/2addr v2, p4

    .line 129
    iget p4, v1, Landroid/graphics/Rect;->bottom:I

    .line 130
    .line 131
    sub-int/2addr p4, v0

    .line 132
    iput p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 133
    .line 134
    iput p5, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 135
    .line 136
    iput v2, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mRightInset:I

    .line 137
    .line 138
    iput p4, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mBottomInset:I

    .line 139
    .line 140
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 141
    .line 142
    .line 143
    move-result-object p2

    .line 144
    check-cast p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 145
    .line 146
    iget-object p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignmentFacet:Landroidx/leanback/widget/ItemAlignmentFacet;

    .line 147
    .line 148
    iget-object p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mItemAlignment:Landroidx/leanback/widget/ItemAlignment;

    .line 149
    .line 150
    if-nez p3, :cond_c

    .line 151
    .line 152
    iget-object p0, p4, Landroidx/leanback/widget/ItemAlignment;->horizontal:Landroidx/leanback/widget/ItemAlignment$Axis;

    .line 153
    .line 154
    iget p3, p0, Landroidx/leanback/widget/ItemAlignment$Axis;->mOrientation:I

    .line 155
    .line 156
    invoke-static {p1, p0, p3}, Landroidx/leanback/widget/ItemAlignmentFacetHelper;->getAlignmentPosition(Landroid/view/View;Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;I)I

    .line 157
    .line 158
    .line 159
    move-result p0

    .line 160
    iput p0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 161
    .line 162
    iget-object p0, p4, Landroidx/leanback/widget/ItemAlignment;->vertical:Landroidx/leanback/widget/ItemAlignment$Axis;

    .line 163
    .line 164
    iget p3, p0, Landroidx/leanback/widget/ItemAlignment$Axis;->mOrientation:I

    .line 165
    .line 166
    invoke-static {p1, p0, p3}, Landroidx/leanback/widget/ItemAlignmentFacetHelper;->getAlignmentPosition(Landroid/view/View;Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;I)I

    .line 167
    .line 168
    .line 169
    move-result p0

    .line 170
    iput p0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 171
    .line 172
    goto :goto_7

    .line 173
    :cond_c
    iget p5, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 174
    .line 175
    iget-object v0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 176
    .line 177
    iget-object p3, p3, Landroidx/leanback/widget/ItemAlignmentFacet;->mAlignmentDefs:[Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;

    .line 178
    .line 179
    if-eqz v0, :cond_d

    .line 180
    .line 181
    array-length v0, v0

    .line 182
    array-length v1, p3

    .line 183
    if-eq v0, v1, :cond_e

    .line 184
    .line 185
    :cond_d
    array-length v0, p3

    .line 186
    new-array v0, v0, [I

    .line 187
    .line 188
    iput-object v0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 189
    .line 190
    :cond_e
    const/4 v0, 0x0

    .line 191
    move v1, v0

    .line 192
    :goto_5
    array-length v2, p3

    .line 193
    if-ge v1, v2, :cond_f

    .line 194
    .line 195
    iget-object v2, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 196
    .line 197
    aget-object v3, p3, v1

    .line 198
    .line 199
    invoke-static {p1, v3, p5}, Landroidx/leanback/widget/ItemAlignmentFacetHelper;->getAlignmentPosition(Landroid/view/View;Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;I)I

    .line 200
    .line 201
    .line 202
    move-result v3

    .line 203
    aput v3, v2, v1

    .line 204
    .line 205
    add-int/lit8 v1, v1, 0x1

    .line 206
    .line 207
    goto :goto_5

    .line 208
    :cond_f
    if-nez p5, :cond_10

    .line 209
    .line 210
    iget-object p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 211
    .line 212
    aget p3, p3, v0

    .line 213
    .line 214
    iput p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 215
    .line 216
    goto :goto_6

    .line 217
    :cond_10
    iget-object p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 218
    .line 219
    aget p3, p3, v0

    .line 220
    .line 221
    iput p3, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 222
    .line 223
    :goto_6
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 224
    .line 225
    if-nez p0, :cond_11

    .line 226
    .line 227
    iget-object p0, p4, Landroidx/leanback/widget/ItemAlignment;->vertical:Landroidx/leanback/widget/ItemAlignment$Axis;

    .line 228
    .line 229
    iget p3, p0, Landroidx/leanback/widget/ItemAlignment$Axis;->mOrientation:I

    .line 230
    .line 231
    invoke-static {p1, p0, p3}, Landroidx/leanback/widget/ItemAlignmentFacetHelper;->getAlignmentPosition(Landroid/view/View;Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;I)I

    .line 232
    .line 233
    .line 234
    move-result p0

    .line 235
    iput p0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 236
    .line 237
    goto :goto_7

    .line 238
    :cond_11
    iget-object p0, p4, Landroidx/leanback/widget/ItemAlignment;->horizontal:Landroidx/leanback/widget/ItemAlignment$Axis;

    .line 239
    .line 240
    iget p3, p0, Landroidx/leanback/widget/ItemAlignment$Axis;->mOrientation:I

    .line 241
    .line 242
    invoke-static {p1, p0, p3}, Landroidx/leanback/widget/ItemAlignmentFacetHelper;->getAlignmentPosition(Landroid/view/View;Landroidx/leanback/widget/ItemAlignmentFacet$ItemAlignmentDef;I)I

    .line 243
    .line 244
    .line 245
    move-result p0

    .line 246
    iput p0, p2, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 247
    .line 248
    :goto_7
    return-void
.end method

.method public final leaveContext()V
    .locals 1

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSaveContextLevel:I

    .line 2
    .line 3
    add-int/lit8 v0, v0, -0x1

    .line 4
    .line 5
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSaveContextLevel:I

    .line 6
    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    const/4 v0, 0x0

    .line 10
    iput-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 11
    .line 12
    iput-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 13
    .line 14
    const/4 v0, 0x0

    .line 15
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 16
    .line 17
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 18
    .line 19
    :cond_0
    return-void
.end method

.method public final measureChild(Landroid/view/View;)V
    .locals 7

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 6
    .line 7
    sget-object v1, Landroidx/leanback/widget/GridLayoutManager;->sTempRect:Landroid/graphics/Rect;

    .line 8
    .line 9
    invoke-virtual {p0, v1, p1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->calculateItemDecorationsForChild(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 10
    .line 11
    .line 12
    iget v2, v0, Landroid/view/ViewGroup$MarginLayoutParams;->leftMargin:I

    .line 13
    .line 14
    iget v3, v0, Landroid/view/ViewGroup$MarginLayoutParams;->rightMargin:I

    .line 15
    .line 16
    add-int/2addr v2, v3

    .line 17
    iget v3, v1, Landroid/graphics/Rect;->left:I

    .line 18
    .line 19
    add-int/2addr v2, v3

    .line 20
    iget v3, v1, Landroid/graphics/Rect;->right:I

    .line 21
    .line 22
    add-int/2addr v2, v3

    .line 23
    iget v3, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    .line 24
    .line 25
    iget v4, v0, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 26
    .line 27
    add-int/2addr v3, v4

    .line 28
    iget v4, v1, Landroid/graphics/Rect;->top:I

    .line 29
    .line 30
    add-int/2addr v3, v4

    .line 31
    iget v1, v1, Landroid/graphics/Rect;->bottom:I

    .line 32
    .line 33
    add-int/2addr v3, v1

    .line 34
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondaryRequested:I

    .line 35
    .line 36
    const/4 v4, -0x2

    .line 37
    const/4 v5, 0x0

    .line 38
    if-ne v1, v4, :cond_0

    .line 39
    .line 40
    invoke-static {v5, v5}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 41
    .line 42
    .line 43
    move-result v1

    .line 44
    goto :goto_0

    .line 45
    :cond_0
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 46
    .line 47
    const/high16 v4, 0x40000000    # 2.0f

    .line 48
    .line 49
    invoke-static {v1, v4}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 50
    .line 51
    .line 52
    move-result v1

    .line 53
    :goto_0
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 54
    .line 55
    if-nez p0, :cond_1

    .line 56
    .line 57
    invoke-static {v5, v5}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 58
    .line 59
    .line 60
    move-result p0

    .line 61
    iget v4, v0, Landroid/view/ViewGroup$MarginLayoutParams;->width:I

    .line 62
    .line 63
    invoke-static {p0, v2, v4}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 64
    .line 65
    .line 66
    move-result p0

    .line 67
    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    .line 68
    .line 69
    invoke-static {v1, v3, v0}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 70
    .line 71
    .line 72
    move-result v0

    .line 73
    goto :goto_1

    .line 74
    :cond_1
    invoke-static {v5, v5}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 75
    .line 76
    .line 77
    move-result p0

    .line 78
    iget v4, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    .line 79
    .line 80
    invoke-static {p0, v3, v4}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 81
    .line 82
    .line 83
    move-result p0

    .line 84
    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->width:I

    .line 85
    .line 86
    invoke-static {v1, v2, v0}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 87
    .line 88
    .line 89
    move-result v0

    .line 90
    move v6, v0

    .line 91
    move v0, p0

    .line 92
    move p0, v6

    .line 93
    :goto_1
    invoke-virtual {p1, p0, v0}, Landroid/view/View;->measure(II)V

    .line 94
    .line 95
    .line 96
    return-void
.end method

.method public final onAdapterChanged(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V
    .locals 0

    .line 1
    if-eqz p1, :cond_0

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 5
    .line 6
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 7
    .line 8
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 9
    .line 10
    and-int/lit16 p1, p1, -0x401

    .line 11
    .line 12
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 13
    .line 14
    const/4 p1, -0x1

    .line 15
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 16
    .line 17
    const/4 p1, 0x0

    .line 18
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 19
    .line 20
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 21
    .line 22
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 23
    .line 24
    .line 25
    :cond_0
    return-void
.end method

.method public final onAddFocusables(Landroidx/recyclerview/widget/RecyclerView;Ljava/util/ArrayList;II)Z
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move-object/from16 v1, p2

    .line 4
    .line 5
    move/from16 v2, p3

    .line 6
    .line 7
    move/from16 v3, p4

    .line 8
    .line 9
    iget v4, v0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 10
    .line 11
    const v5, 0x8000

    .line 12
    .line 13
    .line 14
    and-int/2addr v4, v5

    .line 15
    const/4 v5, 0x1

    .line 16
    if-eqz v4, :cond_0

    .line 17
    .line 18
    return v5

    .line 19
    :cond_0
    invoke-virtual/range {p1 .. p1}, Landroid/view/ViewGroup;->hasFocus()Z

    .line 20
    .line 21
    .line 22
    move-result v4

    .line 23
    if-eqz v4, :cond_1d

    .line 24
    .line 25
    iget-object v4, v0, Landroidx/leanback/widget/GridLayoutManager;->mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 26
    .line 27
    if-eqz v4, :cond_1

    .line 28
    .line 29
    return v5

    .line 30
    :cond_1
    invoke-virtual {v0, v2}, Landroidx/leanback/widget/GridLayoutManager;->getMovement(I)I

    .line 31
    .line 32
    .line 33
    move-result v4

    .line 34
    invoke-virtual/range {p1 .. p1}, Landroid/view/ViewGroup;->findFocus()Landroid/view/View;

    .line 35
    .line 36
    .line 37
    move-result-object v6

    .line 38
    const/4 v7, -0x1

    .line 39
    if-eqz v6, :cond_3

    .line 40
    .line 41
    iget-object v9, v0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 42
    .line 43
    if-eqz v9, :cond_3

    .line 44
    .line 45
    if-eq v6, v9, :cond_3

    .line 46
    .line 47
    invoke-virtual {v0, v6}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findContainingItemView(Landroid/view/View;)Landroid/view/View;

    .line 48
    .line 49
    .line 50
    move-result-object v6

    .line 51
    if-eqz v6, :cond_3

    .line 52
    .line 53
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 54
    .line 55
    .line 56
    move-result v9

    .line 57
    const/4 v10, 0x0

    .line 58
    :goto_0
    if-ge v10, v9, :cond_3

    .line 59
    .line 60
    invoke-virtual {v0, v10}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 61
    .line 62
    .line 63
    move-result-object v11

    .line 64
    if-ne v11, v6, :cond_2

    .line 65
    .line 66
    goto :goto_1

    .line 67
    :cond_2
    add-int/lit8 v10, v10, 0x1

    .line 68
    .line 69
    goto :goto_0

    .line 70
    :cond_3
    move v10, v7

    .line 71
    :goto_1
    invoke-virtual {v0, v10}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 72
    .line 73
    .line 74
    move-result-object v6

    .line 75
    invoke-static {v6}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 76
    .line 77
    .line 78
    move-result v6

    .line 79
    if-ne v6, v7, :cond_4

    .line 80
    .line 81
    const/4 v9, 0x0

    .line 82
    goto :goto_2

    .line 83
    :cond_4
    invoke-virtual {v0, v6}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 84
    .line 85
    .line 86
    move-result-object v9

    .line 87
    :goto_2
    if-eqz v9, :cond_5

    .line 88
    .line 89
    invoke-virtual {v9, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 90
    .line 91
    .line 92
    :cond_5
    iget-object v11, v0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 93
    .line 94
    if-eqz v11, :cond_1c

    .line 95
    .line 96
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 97
    .line 98
    .line 99
    move-result v11

    .line 100
    if-nez v11, :cond_6

    .line 101
    .line 102
    goto/16 :goto_b

    .line 103
    .line 104
    :cond_6
    const/4 v11, 0x3

    .line 105
    const/4 v12, 0x2

    .line 106
    if-eq v4, v11, :cond_7

    .line 107
    .line 108
    if-ne v4, v12, :cond_8

    .line 109
    .line 110
    :cond_7
    iget-object v13, v0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 111
    .line 112
    iget v13, v13, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 113
    .line 114
    if-gt v13, v5, :cond_8

    .line 115
    .line 116
    return v5

    .line 117
    :cond_8
    iget-object v13, v0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 118
    .line 119
    if-eqz v13, :cond_9

    .line 120
    .line 121
    if-eqz v9, :cond_9

    .line 122
    .line 123
    invoke-virtual {v13, v6}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 124
    .line 125
    .line 126
    move-result-object v13

    .line 127
    iget v13, v13, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 128
    .line 129
    goto :goto_3

    .line 130
    :cond_9
    move v13, v7

    .line 131
    :goto_3
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 132
    .line 133
    .line 134
    move-result v14

    .line 135
    if-eq v4, v5, :cond_b

    .line 136
    .line 137
    if-ne v4, v11, :cond_a

    .line 138
    .line 139
    goto :goto_4

    .line 140
    :cond_a
    move v15, v7

    .line 141
    goto :goto_5

    .line 142
    :cond_b
    :goto_4
    move v15, v5

    .line 143
    :goto_5
    if-lez v15, :cond_c

    .line 144
    .line 145
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 146
    .line 147
    .line 148
    move-result v16

    .line 149
    add-int/lit8 v16, v16, -0x1

    .line 150
    .line 151
    move/from16 v8, v16

    .line 152
    .line 153
    goto :goto_6

    .line 154
    :cond_c
    const/4 v8, 0x0

    .line 155
    :goto_6
    if-ne v10, v7, :cond_e

    .line 156
    .line 157
    if-lez v15, :cond_d

    .line 158
    .line 159
    const/4 v7, 0x0

    .line 160
    goto :goto_7

    .line 161
    :cond_d
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 162
    .line 163
    .line 164
    move-result v7

    .line 165
    sub-int/2addr v7, v5

    .line 166
    goto :goto_7

    .line 167
    :cond_e
    add-int v7, v10, v15

    .line 168
    .line 169
    :goto_7
    if-lez v15, :cond_f

    .line 170
    .line 171
    if-gt v7, v8, :cond_20

    .line 172
    .line 173
    goto :goto_8

    .line 174
    :cond_f
    if-lt v7, v8, :cond_20

    .line 175
    .line 176
    :goto_8
    invoke-virtual {v0, v7}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 177
    .line 178
    .line 179
    move-result-object v10

    .line 180
    invoke-virtual {v10}, Landroid/view/View;->getVisibility()I

    .line 181
    .line 182
    .line 183
    move-result v16

    .line 184
    if-nez v16, :cond_1b

    .line 185
    .line 186
    invoke-virtual {v10}, Landroid/view/View;->hasFocusable()Z

    .line 187
    .line 188
    .line 189
    move-result v16

    .line 190
    if-nez v16, :cond_10

    .line 191
    .line 192
    goto/16 :goto_a

    .line 193
    .line 194
    :cond_10
    if-nez v9, :cond_11

    .line 195
    .line 196
    invoke-virtual {v10, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 197
    .line 198
    .line 199
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 200
    .line 201
    .line 202
    move-result v10

    .line 203
    if-le v10, v14, :cond_1b

    .line 204
    .line 205
    goto/16 :goto_c

    .line 206
    .line 207
    :cond_11
    invoke-virtual {v0, v7}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 208
    .line 209
    .line 210
    move-result-object v16

    .line 211
    invoke-static/range {v16 .. v16}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 212
    .line 213
    .line 214
    move-result v12

    .line 215
    iget-object v11, v0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 216
    .line 217
    invoke-virtual {v11, v12}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 218
    .line 219
    .line 220
    move-result-object v11

    .line 221
    if-nez v11, :cond_13

    .line 222
    .line 223
    :cond_12
    const/4 v12, 0x3

    .line 224
    goto :goto_9

    .line 225
    :cond_13
    iget v11, v11, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 226
    .line 227
    if-ne v4, v5, :cond_14

    .line 228
    .line 229
    if-ne v11, v13, :cond_12

    .line 230
    .line 231
    if-le v12, v6, :cond_12

    .line 232
    .line 233
    invoke-virtual {v10, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 234
    .line 235
    .line 236
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 237
    .line 238
    .line 239
    move-result v10

    .line 240
    if-le v10, v14, :cond_12

    .line 241
    .line 242
    goto :goto_c

    .line 243
    :cond_14
    if-nez v4, :cond_15

    .line 244
    .line 245
    if-ne v11, v13, :cond_12

    .line 246
    .line 247
    if-ge v12, v6, :cond_12

    .line 248
    .line 249
    invoke-virtual {v10, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 250
    .line 251
    .line 252
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 253
    .line 254
    .line 255
    move-result v10

    .line 256
    if-le v10, v14, :cond_12

    .line 257
    .line 258
    goto :goto_c

    .line 259
    :cond_15
    const/4 v12, 0x3

    .line 260
    if-ne v4, v12, :cond_18

    .line 261
    .line 262
    if-ne v11, v13, :cond_16

    .line 263
    .line 264
    goto :goto_9

    .line 265
    :cond_16
    if-ge v11, v13, :cond_17

    .line 266
    .line 267
    goto :goto_c

    .line 268
    :cond_17
    invoke-virtual {v10, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 269
    .line 270
    .line 271
    :goto_9
    const/4 v12, 0x2

    .line 272
    goto :goto_a

    .line 273
    :cond_18
    const/4 v12, 0x2

    .line 274
    if-ne v4, v12, :cond_1b

    .line 275
    .line 276
    if-ne v11, v13, :cond_19

    .line 277
    .line 278
    goto :goto_a

    .line 279
    :cond_19
    if-le v11, v13, :cond_1a

    .line 280
    .line 281
    goto :goto_c

    .line 282
    :cond_1a
    invoke-virtual {v10, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 283
    .line 284
    .line 285
    :cond_1b
    :goto_a
    add-int/2addr v7, v15

    .line 286
    const/4 v11, 0x3

    .line 287
    goto :goto_7

    .line 288
    :cond_1c
    :goto_b
    return v5

    .line 289
    :cond_1d
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 290
    .line 291
    .line 292
    move-result v4

    .line 293
    iget v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 294
    .line 295
    invoke-virtual {v0, v6}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 296
    .line 297
    .line 298
    move-result-object v0

    .line 299
    if-eqz v0, :cond_1e

    .line 300
    .line 301
    invoke-virtual {v0, v1, v2, v3}, Landroid/view/View;->addFocusables(Ljava/util/ArrayList;II)V

    .line 302
    .line 303
    .line 304
    :cond_1e
    invoke-virtual/range {p2 .. p2}, Ljava/util/ArrayList;->size()I

    .line 305
    .line 306
    .line 307
    move-result v0

    .line 308
    if-eq v0, v4, :cond_1f

    .line 309
    .line 310
    return v5

    .line 311
    :cond_1f
    invoke-virtual/range {p1 .. p1}, Landroid/view/ViewGroup;->isFocusable()Z

    .line 312
    .line 313
    .line 314
    move-result v0

    .line 315
    if-eqz v0, :cond_20

    .line 316
    .line 317
    move-object/from16 v0, p1

    .line 318
    .line 319
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 320
    .line 321
    .line 322
    :cond_20
    :goto_c
    return v5
.end method

.method public final onInitializeAccessibilityNodeInfo(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;)V
    .locals 5

    .line 1
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p2}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 5
    .line 6
    .line 7
    move-result v0

    .line 8
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 9
    .line 10
    const/high16 v2, 0x40000

    .line 11
    .line 12
    and-int/2addr v2, v1

    .line 13
    const/4 v3, 0x0

    .line 14
    const/4 v4, 0x1

    .line 15
    if-eqz v2, :cond_0

    .line 16
    .line 17
    move v2, v4

    .line 18
    goto :goto_0

    .line 19
    :cond_0
    move v2, v3

    .line 20
    :goto_0
    and-int/lit16 v1, v1, 0x800

    .line 21
    .line 22
    if-eqz v1, :cond_1

    .line 23
    .line 24
    if-le v0, v4, :cond_4

    .line 25
    .line 26
    invoke-virtual {p0, v3}, Landroidx/leanback/widget/GridLayoutManager;->isItemFullyVisible(I)Z

    .line 27
    .line 28
    .line 29
    move-result v1

    .line 30
    if-nez v1, :cond_4

    .line 31
    .line 32
    :cond_1
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 33
    .line 34
    if-nez v1, :cond_3

    .line 35
    .line 36
    if-eqz v2, :cond_2

    .line 37
    .line 38
    sget-object v1, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_RIGHT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 39
    .line 40
    goto :goto_1

    .line 41
    :cond_2
    sget-object v1, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_LEFT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 42
    .line 43
    :goto_1
    invoke-virtual {p3, v1}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->addAction(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;)V

    .line 44
    .line 45
    .line 46
    goto :goto_2

    .line 47
    :cond_3
    sget-object v1, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_UP:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 48
    .line 49
    invoke-virtual {p3, v1}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->addAction(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;)V

    .line 50
    .line 51
    .line 52
    :goto_2
    invoke-virtual {p3, v4}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->setScrollable(Z)V

    .line 53
    .line 54
    .line 55
    :cond_4
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 56
    .line 57
    and-int/lit16 v1, v1, 0x1000

    .line 58
    .line 59
    if-eqz v1, :cond_5

    .line 60
    .line 61
    if-le v0, v4, :cond_8

    .line 62
    .line 63
    sub-int/2addr v0, v4

    .line 64
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->isItemFullyVisible(I)Z

    .line 65
    .line 66
    .line 67
    move-result v0

    .line 68
    if-nez v0, :cond_8

    .line 69
    .line 70
    :cond_5
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 71
    .line 72
    if-nez v0, :cond_7

    .line 73
    .line 74
    if-eqz v2, :cond_6

    .line 75
    .line 76
    sget-object v0, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_LEFT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 77
    .line 78
    goto :goto_3

    .line 79
    :cond_6
    sget-object v0, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_RIGHT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 80
    .line 81
    :goto_3
    invoke-virtual {p3, v0}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->addAction(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;)V

    .line 82
    .line 83
    .line 84
    goto :goto_4

    .line 85
    :cond_7
    sget-object v0, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_DOWN:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 86
    .line 87
    invoke-virtual {p3, v0}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->addAction(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;)V

    .line 88
    .line 89
    .line 90
    :goto_4
    invoke-virtual {p3, v4}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->setScrollable(Z)V

    .line 91
    .line 92
    .line 93
    :cond_8
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->getRowCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I

    .line 94
    .line 95
    .line 96
    move-result v0

    .line 97
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->getColumnCountForAccessibility(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I

    .line 98
    .line 99
    .line 100
    move-result p1

    .line 101
    invoke-static {v0, p1, v3}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionInfoCompat;->obtain(III)Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionInfoCompat;

    .line 102
    .line 103
    .line 104
    move-result-object p1

    .line 105
    invoke-virtual {p3, p1}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->setCollectionInfo(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionInfoCompat;)V

    .line 106
    .line 107
    .line 108
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 109
    .line 110
    .line 111
    return-void
.end method

.method public final onInitializeAccessibilityNodeInfoForItem(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;Landroid/view/View;Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;)V
    .locals 6

    .line 1
    invoke-virtual {p3}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 2
    .line 3
    .line 4
    move-result-object p1

    .line 5
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 6
    .line 7
    if-eqz p2, :cond_5

    .line 8
    .line 9
    instance-of p2, p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 10
    .line 11
    if-nez p2, :cond_0

    .line 12
    .line 13
    goto :goto_1

    .line 14
    :cond_0
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 15
    .line 16
    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mViewHolder:Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 17
    .line 18
    invoke-virtual {p1}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getAbsoluteAdapterPosition()I

    .line 19
    .line 20
    .line 21
    move-result p1

    .line 22
    const/4 p2, -0x1

    .line 23
    if-ltz p1, :cond_2

    .line 24
    .line 25
    iget-object p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 26
    .line 27
    invoke-virtual {p3, p1}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 28
    .line 29
    .line 30
    move-result-object p3

    .line 31
    if-nez p3, :cond_1

    .line 32
    .line 33
    goto :goto_0

    .line 34
    :cond_1
    iget p2, p3, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 35
    .line 36
    :cond_2
    :goto_0
    move v3, p2

    .line 37
    if-gez v3, :cond_3

    .line 38
    .line 39
    return-void

    .line 40
    :cond_3
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 41
    .line 42
    iget p2, p2, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 43
    .line 44
    div-int/2addr p1, p2

    .line 45
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 46
    .line 47
    if-nez p0, :cond_4

    .line 48
    .line 49
    const/4 v2, 0x1

    .line 50
    const/4 v4, 0x1

    .line 51
    const/4 v0, 0x0

    .line 52
    const/4 v5, 0x0

    .line 53
    move v1, v3

    .line 54
    move v3, p1

    .line 55
    invoke-static/range {v0 .. v5}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;->obtain(ZIIIIZ)Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;

    .line 56
    .line 57
    .line 58
    move-result-object p0

    .line 59
    invoke-virtual {p4, p0}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->setCollectionItemInfo(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;)V

    .line 60
    .line 61
    .line 62
    goto :goto_1

    .line 63
    :cond_4
    const/4 v2, 0x1

    .line 64
    const/4 v4, 0x1

    .line 65
    const/4 v0, 0x0

    .line 66
    const/4 v5, 0x0

    .line 67
    move v1, p1

    .line 68
    invoke-static/range {v0 .. v5}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;->obtain(ZIIIIZ)Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;

    .line 69
    .line 70
    .line 71
    move-result-object p0

    .line 72
    invoke-virtual {p4, p0}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;->setCollectionItemInfo(Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$CollectionItemInfoCompat;)V

    .line 73
    .line 74
    .line 75
    :cond_5
    :goto_1
    return-void
.end method

.method public final onInterceptFocusSearch(Landroid/view/View;I)Landroid/view/View;
    .locals 7

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const v1, 0x8000

    .line 4
    .line 5
    .line 6
    and-int/2addr v0, v1

    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    return-object p1

    .line 10
    :cond_0
    invoke-static {}, Landroid/view/FocusFinder;->getInstance()Landroid/view/FocusFinder;

    .line 11
    .line 12
    .line 13
    move-result-object v0

    .line 14
    const/4 v1, 0x0

    .line 15
    const/4 v2, 0x2

    .line 16
    const/4 v3, 0x1

    .line 17
    if-eq p2, v2, :cond_2

    .line 18
    .line 19
    if-ne p2, v3, :cond_1

    .line 20
    .line 21
    goto :goto_0

    .line 22
    :cond_1
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 23
    .line 24
    invoke-virtual {v0, v4, p1, p2}, Landroid/view/FocusFinder;->findNextFocus(Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;

    .line 25
    .line 26
    .line 27
    move-result-object v0

    .line 28
    goto :goto_6

    .line 29
    :cond_2
    :goto_0
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->canScrollVertically()Z

    .line 30
    .line 31
    .line 32
    move-result v4

    .line 33
    if-eqz v4, :cond_4

    .line 34
    .line 35
    if-ne p2, v2, :cond_3

    .line 36
    .line 37
    const/16 v4, 0x82

    .line 38
    .line 39
    goto :goto_1

    .line 40
    :cond_3
    const/16 v4, 0x21

    .line 41
    .line 42
    :goto_1
    iget-object v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 43
    .line 44
    invoke-virtual {v0, v5, p1, v4}, Landroid/view/FocusFinder;->findNextFocus(Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;

    .line 45
    .line 46
    .line 47
    move-result-object v4

    .line 48
    goto :goto_2

    .line 49
    :cond_4
    const/4 v4, 0x0

    .line 50
    :goto_2
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->canScrollHorizontally()Z

    .line 51
    .line 52
    .line 53
    move-result v5

    .line 54
    if-eqz v5, :cond_8

    .line 55
    .line 56
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getLayoutDirection()I

    .line 57
    .line 58
    .line 59
    move-result v4

    .line 60
    if-ne v4, v3, :cond_5

    .line 61
    .line 62
    move v4, v3

    .line 63
    goto :goto_3

    .line 64
    :cond_5
    move v4, v1

    .line 65
    :goto_3
    if-ne p2, v2, :cond_6

    .line 66
    .line 67
    move v5, v3

    .line 68
    goto :goto_4

    .line 69
    :cond_6
    move v5, v1

    .line 70
    :goto_4
    xor-int/2addr v4, v5

    .line 71
    if-eqz v4, :cond_7

    .line 72
    .line 73
    const/16 v4, 0x42

    .line 74
    .line 75
    goto :goto_5

    .line 76
    :cond_7
    const/16 v4, 0x11

    .line 77
    .line 78
    :goto_5
    iget-object v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 79
    .line 80
    invoke-virtual {v0, v5, p1, v4}, Landroid/view/FocusFinder;->findNextFocus(Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;

    .line 81
    .line 82
    .line 83
    move-result-object v0

    .line 84
    goto :goto_6

    .line 85
    :cond_8
    move-object v0, v4

    .line 86
    :goto_6
    if-eqz v0, :cond_9

    .line 87
    .line 88
    return-object v0

    .line 89
    :cond_9
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 90
    .line 91
    invoke-virtual {v4}, Landroid/view/ViewGroup;->getDescendantFocusability()I

    .line 92
    .line 93
    .line 94
    move-result v4

    .line 95
    const/high16 v5, 0x60000

    .line 96
    .line 97
    if-ne v4, v5, :cond_a

    .line 98
    .line 99
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 100
    .line 101
    invoke-virtual {p0}, Landroid/view/ViewGroup;->getParent()Landroid/view/ViewParent;

    .line 102
    .line 103
    .line 104
    move-result-object p0

    .line 105
    invoke-interface {p0, p1, p2}, Landroid/view/ViewParent;->focusSearch(Landroid/view/View;I)Landroid/view/View;

    .line 106
    .line 107
    .line 108
    move-result-object p0

    .line 109
    return-object p0

    .line 110
    :cond_a
    invoke-virtual {p0, p2}, Landroidx/leanback/widget/GridLayoutManager;->getMovement(I)I

    .line 111
    .line 112
    .line 113
    move-result v4

    .line 114
    iget-object v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 115
    .line 116
    iget v5, v5, Landroidx/recyclerview/widget/RecyclerView;->mScrollState:I

    .line 117
    .line 118
    if-eqz v5, :cond_b

    .line 119
    .line 120
    move v5, v3

    .line 121
    goto :goto_7

    .line 122
    :cond_b
    move v5, v1

    .line 123
    :goto_7
    const/high16 v6, 0x20000

    .line 124
    .line 125
    if-ne v4, v3, :cond_e

    .line 126
    .line 127
    if-nez v5, :cond_c

    .line 128
    .line 129
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 130
    .line 131
    and-int/lit16 v1, v1, 0x1000

    .line 132
    .line 133
    if-nez v1, :cond_d

    .line 134
    .line 135
    :cond_c
    move-object v0, p1

    .line 136
    :cond_d
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 137
    .line 138
    and-int/2addr v1, v6

    .line 139
    if-eqz v1, :cond_14

    .line 140
    .line 141
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->hasCreatedLastItem()Z

    .line 142
    .line 143
    .line 144
    move-result v1

    .line 145
    if-nez v1, :cond_14

    .line 146
    .line 147
    invoke-virtual {p0, v3}, Landroidx/leanback/widget/GridLayoutManager;->processPendingMovement(Z)V

    .line 148
    .line 149
    .line 150
    goto :goto_8

    .line 151
    :cond_e
    if-nez v4, :cond_11

    .line 152
    .line 153
    if-nez v5, :cond_f

    .line 154
    .line 155
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 156
    .line 157
    and-int/lit16 v2, v2, 0x800

    .line 158
    .line 159
    if-nez v2, :cond_10

    .line 160
    .line 161
    :cond_f
    move-object v0, p1

    .line 162
    :cond_10
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 163
    .line 164
    and-int/2addr v2, v6

    .line 165
    if-eqz v2, :cond_14

    .line 166
    .line 167
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->hasCreatedFirstItem()Z

    .line 168
    .line 169
    .line 170
    move-result v2

    .line 171
    if-nez v2, :cond_14

    .line 172
    .line 173
    invoke-virtual {p0, v1}, Landroidx/leanback/widget/GridLayoutManager;->processPendingMovement(Z)V

    .line 174
    .line 175
    .line 176
    goto :goto_8

    .line 177
    :cond_11
    const/4 v1, 0x3

    .line 178
    if-ne v4, v1, :cond_12

    .line 179
    .line 180
    if-nez v5, :cond_13

    .line 181
    .line 182
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 183
    .line 184
    and-int/lit16 v1, v1, 0x4000

    .line 185
    .line 186
    if-nez v1, :cond_14

    .line 187
    .line 188
    goto :goto_8

    .line 189
    :cond_12
    if-ne v4, v2, :cond_14

    .line 190
    .line 191
    if-nez v5, :cond_13

    .line 192
    .line 193
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 194
    .line 195
    and-int/lit16 v1, v1, 0x2000

    .line 196
    .line 197
    if-nez v1, :cond_14

    .line 198
    .line 199
    :cond_13
    :goto_8
    move-object v0, p1

    .line 200
    :cond_14
    if-eqz v0, :cond_15

    .line 201
    .line 202
    return-object v0

    .line 203
    :cond_15
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 204
    .line 205
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getParent()Landroid/view/ViewParent;

    .line 206
    .line 207
    .line 208
    move-result-object v0

    .line 209
    invoke-interface {v0, p1, p2}, Landroid/view/ViewParent;->focusSearch(Landroid/view/View;I)Landroid/view/View;

    .line 210
    .line 211
    .line 212
    move-result-object p2

    .line 213
    if-eqz p2, :cond_16

    .line 214
    .line 215
    return-object p2

    .line 216
    :cond_16
    if-eqz p1, :cond_17

    .line 217
    .line 218
    goto :goto_9

    .line 219
    :cond_17
    iget-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 220
    .line 221
    :goto_9
    return-object p1
.end method

.method public final onItemsAdded(II)V
    .locals 3

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 2
    .line 3
    const/4 v1, -0x1

    .line 4
    if-eq v0, v1, :cond_0

    .line 5
    .line 6
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 7
    .line 8
    if-eqz v1, :cond_0

    .line 9
    .line 10
    iget v1, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 11
    .line 12
    if-ltz v1, :cond_0

    .line 13
    .line 14
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 15
    .line 16
    const/high16 v2, -0x80000000

    .line 17
    .line 18
    if-eq v1, v2, :cond_0

    .line 19
    .line 20
    add-int/2addr v0, v1

    .line 21
    if-gt p1, v0, :cond_0

    .line 22
    .line 23
    add-int/2addr v1, p2

    .line 24
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 25
    .line 26
    :cond_0
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 27
    .line 28
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 29
    .line 30
    .line 31
    return-void
.end method

.method public final onItemsChanged()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 3
    .line 4
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 5
    .line 6
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 7
    .line 8
    .line 9
    return-void
.end method

.method public final onItemsMoved(II)V
    .locals 3

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 2
    .line 3
    const/4 v1, -0x1

    .line 4
    if-eq v0, v1, :cond_2

    .line 5
    .line 6
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 7
    .line 8
    const/high16 v2, -0x80000000

    .line 9
    .line 10
    if-eq v1, v2, :cond_2

    .line 11
    .line 12
    add-int/2addr v0, v1

    .line 13
    if-gt p1, v0, :cond_0

    .line 14
    .line 15
    add-int/lit8 v2, p1, 0x1

    .line 16
    .line 17
    if-ge v0, v2, :cond_0

    .line 18
    .line 19
    sub-int/2addr p2, p1

    .line 20
    add-int/2addr p2, v1

    .line 21
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 22
    .line 23
    goto :goto_0

    .line 24
    :cond_0
    if-ge p1, v0, :cond_1

    .line 25
    .line 26
    add-int/lit8 v2, v0, -0x1

    .line 27
    .line 28
    if-le p2, v2, :cond_1

    .line 29
    .line 30
    add-int/lit8 v1, v1, -0x1

    .line 31
    .line 32
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 33
    .line 34
    goto :goto_0

    .line 35
    :cond_1
    if-le p1, v0, :cond_2

    .line 36
    .line 37
    if-ge p2, v0, :cond_2

    .line 38
    .line 39
    add-int/lit8 v1, v1, 0x1

    .line 40
    .line 41
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 42
    .line 43
    :cond_2
    :goto_0
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 44
    .line 45
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 46
    .line 47
    .line 48
    return-void
.end method

.method public final onItemsRemoved(II)V
    .locals 5

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 2
    .line 3
    const/4 v1, -0x1

    .line 4
    if-eq v0, v1, :cond_1

    .line 5
    .line 6
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 7
    .line 8
    if-eqz v1, :cond_1

    .line 9
    .line 10
    iget v1, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 11
    .line 12
    if-ltz v1, :cond_1

    .line 13
    .line 14
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 15
    .line 16
    const/high16 v2, -0x80000000

    .line 17
    .line 18
    if-eq v1, v2, :cond_1

    .line 19
    .line 20
    add-int v3, v0, v1

    .line 21
    .line 22
    if-gt p1, v3, :cond_1

    .line 23
    .line 24
    add-int v4, p1, p2

    .line 25
    .line 26
    if-le v4, v3, :cond_0

    .line 27
    .line 28
    sub-int/2addr p1, v3

    .line 29
    add-int/2addr p1, v1

    .line 30
    add-int/2addr p1, v0

    .line 31
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 32
    .line 33
    iput v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 34
    .line 35
    goto :goto_0

    .line 36
    :cond_0
    sub-int/2addr v1, p2

    .line 37
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 38
    .line 39
    :cond_1
    :goto_0
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 40
    .line 41
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 42
    .line 43
    .line 44
    return-void
.end method

.method public final onItemsUpdated(II)V
    .locals 1

    .line 1
    add-int/2addr p2, p1

    .line 2
    :goto_0
    if-ge p1, p2, :cond_0

    .line 3
    .line 4
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 5
    .line 6
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 7
    .line 8
    .line 9
    add-int/lit8 p1, p1, 0x1

    .line 10
    .line 11
    goto :goto_0

    .line 12
    :cond_0
    return-void
.end method

.method public final onLayoutChildren(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 27

    .line 1
    move-object/from16 v6, p0

    .line 2
    .line 3
    move-object/from16 v7, p2

    .line 4
    .line 5
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 6
    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    return-void

    .line 10
    :cond_0
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    if-gez v0, :cond_1

    .line 15
    .line 16
    return-void

    .line 17
    :cond_1
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 18
    .line 19
    and-int/lit8 v0, v0, 0x40

    .line 20
    .line 21
    if-eqz v0, :cond_2

    .line 22
    .line 23
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 24
    .line 25
    .line 26
    move-result v0

    .line 27
    if-lez v0, :cond_2

    .line 28
    .line 29
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 30
    .line 31
    or-int/lit16 v0, v0, 0x80

    .line 32
    .line 33
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 34
    .line 35
    return-void

    .line 36
    :cond_2
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 37
    .line 38
    and-int/lit16 v1, v0, 0x200

    .line 39
    .line 40
    const/4 v8, 0x0

    .line 41
    if-nez v1, :cond_3

    .line 42
    .line 43
    iput-object v8, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 44
    .line 45
    iput-object v8, v6, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 46
    .line 47
    and-int/lit16 v0, v0, -0x401

    .line 48
    .line 49
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 50
    .line 51
    invoke-virtual/range {p0 .. p1}, Landroidx/leanback/widget/GridLayoutManager;->removeAndRecycleAllViews(Landroidx/recyclerview/widget/RecyclerView$Recycler;)V

    .line 52
    .line 53
    .line 54
    return-void

    .line 55
    :cond_3
    and-int/lit8 v0, v0, -0x4

    .line 56
    .line 57
    const/4 v9, 0x1

    .line 58
    or-int/2addr v0, v9

    .line 59
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 60
    .line 61
    invoke-virtual/range {p0 .. p2}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 62
    .line 63
    .line 64
    iget-boolean v0, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mInPreLayout:Z

    .line 65
    .line 66
    const/high16 v11, -0x80000000

    .line 67
    .line 68
    const/4 v12, 0x0

    .line 69
    if-eqz v0, :cond_b

    .line 70
    .line 71
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updatePositionDeltaInPreLayout()V

    .line 72
    .line 73
    .line 74
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 75
    .line 76
    .line 77
    move-result v0

    .line 78
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 79
    .line 80
    if-eqz v1, :cond_a

    .line 81
    .line 82
    if-lez v0, :cond_a

    .line 83
    .line 84
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 85
    .line 86
    invoke-virtual {v6, v12}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 87
    .line 88
    .line 89
    move-result-object v2

    .line 90
    invoke-virtual {v1, v2}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 91
    .line 92
    .line 93
    move-result-object v1

    .line 94
    iget v1, v1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->mOldPosition:I

    .line 95
    .line 96
    iget-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 97
    .line 98
    add-int/lit8 v3, v0, -0x1

    .line 99
    .line 100
    invoke-virtual {v6, v3}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 101
    .line 102
    .line 103
    move-result-object v3

    .line 104
    invoke-virtual {v2, v3}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 105
    .line 106
    .line 107
    move-result-object v2

    .line 108
    iget v2, v2, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->mOldPosition:I

    .line 109
    .line 110
    const v10, 0x7fffffff

    .line 111
    .line 112
    .line 113
    :goto_0
    if-ge v12, v0, :cond_8

    .line 114
    .line 115
    invoke-virtual {v6, v12}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 116
    .line 117
    .line 118
    move-result-object v3

    .line 119
    invoke-virtual {v3}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 120
    .line 121
    .line 122
    move-result-object v4

    .line 123
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 124
    .line 125
    iget-object v5, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 126
    .line 127
    invoke-virtual {v5}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 128
    .line 129
    .line 130
    invoke-static {v3}, Landroidx/recyclerview/widget/RecyclerView;->getChildAdapterPosition(Landroid/view/View;)I

    .line 131
    .line 132
    .line 133
    move-result v5

    .line 134
    invoke-virtual {v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->isItemChanged()Z

    .line 135
    .line 136
    .line 137
    move-result v7

    .line 138
    if-nez v7, :cond_6

    .line 139
    .line 140
    invoke-virtual {v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->isItemRemoved()Z

    .line 141
    .line 142
    .line 143
    move-result v7

    .line 144
    if-nez v7, :cond_6

    .line 145
    .line 146
    invoke-virtual {v3}, Landroid/view/View;->isLayoutRequested()Z

    .line 147
    .line 148
    .line 149
    move-result v7

    .line 150
    if-nez v7, :cond_6

    .line 151
    .line 152
    invoke-virtual {v3}, Landroid/view/View;->hasFocus()Z

    .line 153
    .line 154
    .line 155
    move-result v7

    .line 156
    if-nez v7, :cond_4

    .line 157
    .line 158
    iget v7, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 159
    .line 160
    iget-object v8, v4, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mViewHolder:Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 161
    .line 162
    invoke-virtual {v8}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getAbsoluteAdapterPosition()I

    .line 163
    .line 164
    .line 165
    move-result v8

    .line 166
    if-eq v7, v8, :cond_6

    .line 167
    .line 168
    :cond_4
    invoke-virtual {v3}, Landroid/view/View;->hasFocus()Z

    .line 169
    .line 170
    .line 171
    move-result v7

    .line 172
    if-eqz v7, :cond_5

    .line 173
    .line 174
    iget v7, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 175
    .line 176
    iget-object v4, v4, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mViewHolder:Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 177
    .line 178
    invoke-virtual {v4}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getAbsoluteAdapterPosition()I

    .line 179
    .line 180
    .line 181
    move-result v4

    .line 182
    if-ne v7, v4, :cond_6

    .line 183
    .line 184
    :cond_5
    if-lt v5, v1, :cond_6

    .line 185
    .line 186
    if-le v5, v2, :cond_7

    .line 187
    .line 188
    :cond_6
    iget-object v4, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

    .line 189
    .line 190
    invoke-virtual {v4, v3}, Landroidx/recyclerview/widget/OrientationHelper;->getDecoratedStart(Landroid/view/View;)I

    .line 191
    .line 192
    .line 193
    move-result v4

    .line 194
    invoke-static {v10, v4}, Ljava/lang/Math;->min(II)I

    .line 195
    .line 196
    .line 197
    move-result v4

    .line 198
    iget-object v5, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

    .line 199
    .line 200
    invoke-virtual {v5, v3}, Landroidx/recyclerview/widget/OrientationHelper;->getDecoratedEnd(Landroid/view/View;)I

    .line 201
    .line 202
    .line 203
    move-result v3

    .line 204
    invoke-static {v11, v3}, Ljava/lang/Math;->max(II)I

    .line 205
    .line 206
    .line 207
    move-result v3

    .line 208
    move v11, v3

    .line 209
    move v10, v4

    .line 210
    :cond_7
    add-int/lit8 v12, v12, 0x1

    .line 211
    .line 212
    goto :goto_0

    .line 213
    :cond_8
    if-le v11, v10, :cond_9

    .line 214
    .line 215
    sub-int/2addr v11, v10

    .line 216
    iput v11, v6, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 217
    .line 218
    :cond_9
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->appendVisibleItems()V

    .line 219
    .line 220
    .line 221
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->prependVisibleItems()V

    .line 222
    .line 223
    .line 224
    :cond_a
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 225
    .line 226
    and-int/lit8 v0, v0, -0x4

    .line 227
    .line 228
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 229
    .line 230
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 231
    .line 232
    .line 233
    return-void

    .line 234
    :cond_b
    iget-boolean v0, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mRunPredictiveAnimations:Z

    .line 235
    .line 236
    iget-object v13, v6, Landroidx/leanback/widget/GridLayoutManager;->mPositionToRowInPostLayout:Landroid/util/SparseIntArray;

    .line 237
    .line 238
    if-eqz v0, :cond_d

    .line 239
    .line 240
    invoke-virtual {v13}, Landroid/util/SparseIntArray;->clear()V

    .line 241
    .line 242
    .line 243
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 244
    .line 245
    .line 246
    move-result v0

    .line 247
    move v1, v12

    .line 248
    :goto_1
    if-ge v1, v0, :cond_d

    .line 249
    .line 250
    iget-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 251
    .line 252
    invoke-virtual {v6, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 253
    .line 254
    .line 255
    move-result-object v3

    .line 256
    invoke-virtual {v2, v3}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 257
    .line 258
    .line 259
    move-result-object v2

    .line 260
    iget v2, v2, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->mOldPosition:I

    .line 261
    .line 262
    if-ltz v2, :cond_c

    .line 263
    .line 264
    iget-object v3, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 265
    .line 266
    invoke-virtual {v3, v2}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 267
    .line 268
    .line 269
    move-result-object v3

    .line 270
    if-eqz v3, :cond_c

    .line 271
    .line 272
    iget v3, v3, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 273
    .line 274
    invoke-virtual {v13, v2, v3}, Landroid/util/SparseIntArray;->put(II)V

    .line 275
    .line 276
    .line 277
    :cond_c
    add-int/lit8 v1, v1, 0x1

    .line 278
    .line 279
    goto :goto_1

    .line 280
    :cond_d
    iget-object v0, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mSmoothScroller:Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;

    .line 281
    .line 282
    if-eqz v0, :cond_e

    .line 283
    .line 284
    iget-boolean v0, v0, Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;->mRunning:Z

    .line 285
    .line 286
    if-eqz v0, :cond_e

    .line 287
    .line 288
    move v0, v9

    .line 289
    goto :goto_2

    .line 290
    :cond_e
    move v0, v12

    .line 291
    :goto_2
    xor-int/lit8 v14, v0, 0x1

    .line 292
    .line 293
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 294
    .line 295
    const/4 v1, -0x1

    .line 296
    if-eq v0, v1, :cond_f

    .line 297
    .line 298
    iget v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 299
    .line 300
    if-eq v2, v11, :cond_f

    .line 301
    .line 302
    add-int/2addr v0, v2

    .line 303
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 304
    .line 305
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 306
    .line 307
    :cond_f
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 308
    .line 309
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 310
    .line 311
    invoke-virtual {v6, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 312
    .line 313
    .line 314
    move-result-object v15

    .line 315
    iget v5, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 316
    .line 317
    iget v4, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 318
    .line 319
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 320
    .line 321
    invoke-virtual {v0}, Landroid/view/ViewGroup;->hasFocus()Z

    .line 322
    .line 323
    .line 324
    move-result v16

    .line 325
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 326
    .line 327
    if-eqz v0, :cond_10

    .line 328
    .line 329
    iget v2, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 330
    .line 331
    goto :goto_3

    .line 332
    :cond_10
    move v2, v1

    .line 333
    :goto_3
    if-eqz v0, :cond_11

    .line 334
    .line 335
    iget v0, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 336
    .line 337
    goto :goto_4

    .line 338
    :cond_11
    move v0, v1

    .line 339
    :goto_4
    iget v3, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 340
    .line 341
    if-nez v3, :cond_12

    .line 342
    .line 343
    iget v3, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mRemainingScrollHorizontal:I

    .line 344
    .line 345
    iget v8, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mRemainingScrollVertical:I

    .line 346
    .line 347
    goto :goto_5

    .line 348
    :cond_12
    iget v8, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mRemainingScrollHorizontal:I

    .line 349
    .line 350
    iget v3, v7, Landroidx/recyclerview/widget/RecyclerView$State;->mRemainingScrollVertical:I

    .line 351
    .line 352
    :goto_5
    move/from16 v26, v8

    .line 353
    .line 354
    move v8, v3

    .line 355
    move/from16 v3, v26

    .line 356
    .line 357
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 358
    .line 359
    invoke-virtual {v10}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 360
    .line 361
    .line 362
    move-result v10

    .line 363
    if-nez v10, :cond_13

    .line 364
    .line 365
    iput v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 366
    .line 367
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 368
    .line 369
    goto :goto_6

    .line 370
    :cond_13
    iget v11, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 371
    .line 372
    if-lt v11, v10, :cond_14

    .line 373
    .line 374
    sub-int/2addr v10, v9

    .line 375
    iput v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 376
    .line 377
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 378
    .line 379
    goto :goto_6

    .line 380
    :cond_14
    if-ne v11, v1, :cond_15

    .line 381
    .line 382
    if-lez v10, :cond_15

    .line 383
    .line 384
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 385
    .line 386
    iput v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 387
    .line 388
    :cond_15
    :goto_6
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 389
    .line 390
    iget-boolean v10, v10, Landroidx/recyclerview/widget/RecyclerView$State;->mStructureChanged:Z

    .line 391
    .line 392
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 393
    .line 394
    if-nez v10, :cond_16

    .line 395
    .line 396
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 397
    .line 398
    if-eqz v10, :cond_16

    .line 399
    .line 400
    iget v12, v10, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 401
    .line 402
    if-ltz v12, :cond_16

    .line 403
    .line 404
    iget v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 405
    .line 406
    and-int/lit16 v12, v12, 0x100

    .line 407
    .line 408
    if-nez v12, :cond_16

    .line 409
    .line 410
    iget v10, v10, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 411
    .line 412
    iget v12, v6, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 413
    .line 414
    if-ne v10, v12, :cond_16

    .line 415
    .line 416
    iget-object v10, v1, Landroidx/leanback/widget/WindowAlignment;->horizontal:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 417
    .line 418
    iget v12, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mWidth:I

    .line 419
    .line 420
    iput v12, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 421
    .line 422
    iget v12, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mHeight:I

    .line 423
    .line 424
    iget-object v9, v1, Landroidx/leanback/widget/WindowAlignment;->vertical:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 425
    .line 426
    iput v12, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 427
    .line 428
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingLeft()I

    .line 429
    .line 430
    .line 431
    move-result v12

    .line 432
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingRight()I

    .line 433
    .line 434
    .line 435
    move-result v11

    .line 436
    iput v12, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 437
    .line 438
    iput v11, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMax:I

    .line 439
    .line 440
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingTop()I

    .line 441
    .line 442
    .line 443
    move-result v10

    .line 444
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingBottom()I

    .line 445
    .line 446
    .line 447
    move-result v11

    .line 448
    iput v10, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 449
    .line 450
    iput v11, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMax:I

    .line 451
    .line 452
    iget-object v9, v1, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 453
    .line 454
    iget v9, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 455
    .line 456
    iput v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 457
    .line 458
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updateSecondaryScrollLimits()V

    .line 459
    .line 460
    .line 461
    iget-object v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 462
    .line 463
    iget v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mSpacingPrimary:I

    .line 464
    .line 465
    iput v10, v9, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 466
    .line 467
    const/4 v9, 0x1

    .line 468
    const/high16 v10, -0x80000000

    .line 469
    .line 470
    const v11, 0x7fffffff

    .line 471
    .line 472
    .line 473
    goto/16 :goto_a

    .line 474
    .line 475
    :cond_16
    iget v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 476
    .line 477
    and-int/lit16 v9, v9, -0x101

    .line 478
    .line 479
    iput v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 480
    .line 481
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 482
    .line 483
    if-eqz v10, :cond_18

    .line 484
    .line 485
    iget v11, v6, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 486
    .line 487
    iget v12, v10, Landroidx/leanback/widget/Grid;->mNumRows:I

    .line 488
    .line 489
    if-ne v11, v12, :cond_18

    .line 490
    .line 491
    const/high16 v11, 0x40000

    .line 492
    .line 493
    and-int/2addr v9, v11

    .line 494
    if-eqz v9, :cond_17

    .line 495
    .line 496
    const/4 v9, 0x1

    .line 497
    goto :goto_7

    .line 498
    :cond_17
    const/4 v9, 0x0

    .line 499
    :goto_7
    iget-boolean v10, v10, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 500
    .line 501
    if-eq v9, v10, :cond_1b

    .line 502
    .line 503
    :cond_18
    iget v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 504
    .line 505
    const/4 v10, 0x1

    .line 506
    if-ne v9, v10, :cond_19

    .line 507
    .line 508
    new-instance v9, Landroidx/leanback/widget/SingleRow;

    .line 509
    .line 510
    invoke-direct {v9}, Landroidx/leanback/widget/SingleRow;-><init>()V

    .line 511
    .line 512
    .line 513
    goto :goto_8

    .line 514
    :cond_19
    new-instance v10, Landroidx/leanback/widget/StaggeredGridDefault;

    .line 515
    .line 516
    invoke-direct {v10}, Landroidx/leanback/widget/StaggeredGridDefault;-><init>()V

    .line 517
    .line 518
    .line 519
    invoke-virtual {v10, v9}, Landroidx/leanback/widget/Grid;->setNumRows(I)V

    .line 520
    .line 521
    .line 522
    move-object v9, v10

    .line 523
    :goto_8
    iput-object v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 524
    .line 525
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mGridProvider:Landroidx/leanback/widget/GridLayoutManager$2;

    .line 526
    .line 527
    iput-object v10, v9, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 528
    .line 529
    iget v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 530
    .line 531
    const/high16 v11, 0x40000

    .line 532
    .line 533
    and-int/2addr v10, v11

    .line 534
    if-eqz v10, :cond_1a

    .line 535
    .line 536
    const/4 v10, 0x1

    .line 537
    goto :goto_9

    .line 538
    :cond_1a
    const/4 v10, 0x0

    .line 539
    :goto_9
    iput-boolean v10, v9, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 540
    .line 541
    :cond_1b
    iget-object v9, v1, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 542
    .line 543
    const/high16 v10, -0x80000000

    .line 544
    .line 545
    iput v10, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinEdge:I

    .line 546
    .line 547
    const v10, 0x7fffffff

    .line 548
    .line 549
    .line 550
    iput v10, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxEdge:I

    .line 551
    .line 552
    iget v9, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mWidth:I

    .line 553
    .line 554
    iget-object v10, v1, Landroidx/leanback/widget/WindowAlignment;->horizontal:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 555
    .line 556
    iput v9, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 557
    .line 558
    iget v9, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mHeight:I

    .line 559
    .line 560
    iget-object v11, v1, Landroidx/leanback/widget/WindowAlignment;->vertical:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 561
    .line 562
    iput v9, v11, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 563
    .line 564
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingLeft()I

    .line 565
    .line 566
    .line 567
    move-result v9

    .line 568
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingRight()I

    .line 569
    .line 570
    .line 571
    move-result v12

    .line 572
    iput v9, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 573
    .line 574
    iput v12, v10, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMax:I

    .line 575
    .line 576
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingTop()I

    .line 577
    .line 578
    .line 579
    move-result v9

    .line 580
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingBottom()I

    .line 581
    .line 582
    .line 583
    move-result v10

    .line 584
    iput v9, v11, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 585
    .line 586
    iput v10, v11, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMax:I

    .line 587
    .line 588
    iget-object v9, v1, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 589
    .line 590
    iget v9, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mSize:I

    .line 591
    .line 592
    iput v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 593
    .line 594
    const/4 v9, 0x0

    .line 595
    iput v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mScrollOffsetSecondary:I

    .line 596
    .line 597
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updateSecondaryScrollLimits()V

    .line 598
    .line 599
    .line 600
    iget-object v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 601
    .line 602
    iget v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mSpacingPrimary:I

    .line 603
    .line 604
    iput v10, v9, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 605
    .line 606
    iget-object v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 607
    .line 608
    invoke-virtual {v6, v9}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->detachAndScrapAttachedViews(Landroidx/recyclerview/widget/RecyclerView$Recycler;)V

    .line 609
    .line 610
    .line 611
    iget-object v9, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 612
    .line 613
    const/4 v10, -0x1

    .line 614
    iput v10, v9, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 615
    .line 616
    iput v10, v9, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 617
    .line 618
    iget-object v9, v1, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 619
    .line 620
    const/high16 v10, -0x80000000

    .line 621
    .line 622
    iput v10, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinEdge:I

    .line 623
    .line 624
    iput v10, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinScroll:I

    .line 625
    .line 626
    const v11, 0x7fffffff

    .line 627
    .line 628
    .line 629
    iput v11, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxEdge:I

    .line 630
    .line 631
    iput v11, v9, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxScroll:I

    .line 632
    .line 633
    const/4 v9, 0x0

    .line 634
    :goto_a
    if-eqz v9, :cond_29

    .line 635
    .line 636
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 637
    .line 638
    or-int/lit8 v0, v0, 0x4

    .line 639
    .line 640
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 641
    .line 642
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 643
    .line 644
    iget v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 645
    .line 646
    iput v2, v0, Landroidx/leanback/widget/Grid;->mStartIndex:I

    .line 647
    .line 648
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 649
    .line 650
    .line 651
    move-result v9

    .line 652
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 653
    .line 654
    iget v0, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 655
    .line 656
    iget v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 657
    .line 658
    and-int/lit8 v2, v2, -0x9

    .line 659
    .line 660
    iput v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 661
    .line 662
    move v2, v0

    .line 663
    const/4 v0, 0x0

    .line 664
    :goto_b
    if-ge v0, v9, :cond_23

    .line 665
    .line 666
    invoke-virtual {v6, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 667
    .line 668
    .line 669
    move-result-object v10

    .line 670
    invoke-static {v10}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 671
    .line 672
    .line 673
    move-result v11

    .line 674
    if-eq v2, v11, :cond_1c

    .line 675
    .line 676
    goto :goto_c

    .line 677
    :cond_1c
    iget-object v11, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 678
    .line 679
    invoke-virtual {v11, v2}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 680
    .line 681
    .line 682
    move-result-object v11

    .line 683
    if-nez v11, :cond_1d

    .line 684
    .line 685
    :goto_c
    move v11, v3

    .line 686
    move/from16 v24, v4

    .line 687
    .line 688
    move/from16 v25, v5

    .line 689
    .line 690
    move-object/from16 v23, v13

    .line 691
    .line 692
    move-object/from16 v22, v15

    .line 693
    .line 694
    move v15, v0

    .line 695
    move v13, v2

    .line 696
    goto/16 :goto_11

    .line 697
    .line 698
    :cond_1d
    iget v12, v11, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 699
    .line 700
    invoke-virtual {v6, v12}, Landroidx/leanback/widget/GridLayoutManager;->getRowStartSecondary(I)I

    .line 701
    .line 702
    .line 703
    move-result v12

    .line 704
    move/from16 v19, v3

    .line 705
    .line 706
    iget-object v3, v1, Landroidx/leanback/widget/WindowAlignment;->mSecondAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 707
    .line 708
    iget v3, v3, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 709
    .line 710
    add-int/2addr v12, v3

    .line 711
    iget v3, v6, Landroidx/leanback/widget/GridLayoutManager;->mScrollOffsetSecondary:I

    .line 712
    .line 713
    sub-int/2addr v12, v3

    .line 714
    iget-object v3, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

    .line 715
    .line 716
    invoke-virtual {v3, v10}, Landroidx/recyclerview/widget/OrientationHelper;->getDecoratedStart(Landroid/view/View;)I

    .line 717
    .line 718
    .line 719
    move-result v3

    .line 720
    move-object/from16 v20, v1

    .line 721
    .line 722
    sget-object v1, Landroidx/leanback/widget/GridLayoutManager;->sTempRect:Landroid/graphics/Rect;

    .line 723
    .line 724
    invoke-virtual {v6, v1, v10}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedBoundsWithMargins(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 725
    .line 726
    .line 727
    move/from16 v21, v4

    .line 728
    .line 729
    iget v4, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 730
    .line 731
    if-nez v4, :cond_1e

    .line 732
    .line 733
    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    .line 734
    .line 735
    .line 736
    move-result v1

    .line 737
    goto :goto_d

    .line 738
    :cond_1e
    invoke-virtual {v1}, Landroid/graphics/Rect;->height()I

    .line 739
    .line 740
    .line 741
    move-result v1

    .line 742
    :goto_d
    move v4, v1

    .line 743
    invoke-virtual {v10}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 744
    .line 745
    .line 746
    move-result-object v1

    .line 747
    check-cast v1, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 748
    .line 749
    iget-object v1, v1, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->mViewHolder:Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 750
    .line 751
    iget v1, v1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->mFlags:I

    .line 752
    .line 753
    and-int/lit8 v1, v1, 0x2

    .line 754
    .line 755
    if-eqz v1, :cond_1f

    .line 756
    .line 757
    const/4 v1, 0x1

    .line 758
    goto :goto_e

    .line 759
    :cond_1f
    const/4 v1, 0x0

    .line 760
    :goto_e
    if-eqz v1, :cond_20

    .line 761
    .line 762
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 763
    .line 764
    or-int/lit8 v1, v1, 0x8

    .line 765
    .line 766
    iput v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 767
    .line 768
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 769
    .line 770
    move/from16 v18, v4

    .line 771
    .line 772
    iget-object v4, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mChildHelper:Landroidx/recyclerview/widget/ChildHelper;

    .line 773
    .line 774
    invoke-virtual {v4, v10}, Landroidx/recyclerview/widget/ChildHelper;->indexOfChild(Landroid/view/View;)I

    .line 775
    .line 776
    .line 777
    move-result v4

    .line 778
    invoke-virtual {v6, v1, v4, v10}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->scrapOrRecycleView(Landroidx/recyclerview/widget/RecyclerView$Recycler;ILandroid/view/View;)V

    .line 779
    .line 780
    .line 781
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 782
    .line 783
    invoke-virtual {v1, v2}, Landroidx/recyclerview/widget/RecyclerView$Recycler;->getViewForPosition(I)Landroid/view/View;

    .line 784
    .line 785
    .line 786
    move-result-object v1

    .line 787
    invoke-virtual {v1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 788
    .line 789
    .line 790
    move-result-object v4

    .line 791
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 792
    .line 793
    iget-object v10, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 794
    .line 795
    invoke-virtual {v10, v1}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 796
    .line 797
    .line 798
    const/4 v10, 0x0

    .line 799
    iput-object v10, v4, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignmentFacet:Landroidx/leanback/widget/ItemAlignmentFacet;

    .line 800
    .line 801
    const/4 v4, 0x0

    .line 802
    invoke-virtual {v6, v1, v0, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->addViewInt(Landroid/view/View;IZ)V

    .line 803
    .line 804
    .line 805
    move-object/from16 v17, v10

    .line 806
    .line 807
    goto :goto_f

    .line 808
    :cond_20
    move/from16 v18, v4

    .line 809
    .line 810
    const/16 v17, 0x0

    .line 811
    .line 812
    move-object v1, v10

    .line 813
    :goto_f
    invoke-virtual {v6, v1}, Landroidx/leanback/widget/GridLayoutManager;->measureChild(Landroid/view/View;)V

    .line 814
    .line 815
    .line 816
    iget v4, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 817
    .line 818
    if-nez v4, :cond_21

    .line 819
    .line 820
    invoke-static {v1}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredWidthWithMargin(Landroid/view/View;)I

    .line 821
    .line 822
    .line 823
    move-result v4

    .line 824
    goto :goto_10

    .line 825
    :cond_21
    invoke-static {v1}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredHeightWithMargin(Landroid/view/View;)I

    .line 826
    .line 827
    .line 828
    move-result v4

    .line 829
    :goto_10
    move v10, v4

    .line 830
    add-int v4, v3, v10

    .line 831
    .line 832
    iget v11, v11, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 833
    .line 834
    move-object/from16 v22, v15

    .line 835
    .line 836
    move v15, v0

    .line 837
    move-object/from16 v0, p0

    .line 838
    .line 839
    move-object/from16 v23, v13

    .line 840
    .line 841
    move v13, v2

    .line 842
    move v2, v11

    .line 843
    move/from16 v11, v19

    .line 844
    .line 845
    move/from16 v7, v18

    .line 846
    .line 847
    move/from16 v24, v21

    .line 848
    .line 849
    move/from16 v25, v5

    .line 850
    .line 851
    move v5, v12

    .line 852
    invoke-virtual/range {v0 .. v5}, Landroidx/leanback/widget/GridLayoutManager;->layoutChild(Landroid/view/View;IIII)V

    .line 853
    .line 854
    .line 855
    if-eq v7, v10, :cond_22

    .line 856
    .line 857
    :goto_11
    const/4 v0, 0x1

    .line 858
    goto :goto_12

    .line 859
    :cond_22
    add-int/lit8 v0, v15, 0x1

    .line 860
    .line 861
    add-int/lit8 v2, v13, 0x1

    .line 862
    .line 863
    move-object/from16 v7, p2

    .line 864
    .line 865
    move v3, v11

    .line 866
    move-object/from16 v1, v20

    .line 867
    .line 868
    move-object/from16 v15, v22

    .line 869
    .line 870
    move-object/from16 v13, v23

    .line 871
    .line 872
    move/from16 v4, v24

    .line 873
    .line 874
    move/from16 v5, v25

    .line 875
    .line 876
    const/high16 v10, -0x80000000

    .line 877
    .line 878
    const v11, 0x7fffffff

    .line 879
    .line 880
    .line 881
    goto/16 :goto_b

    .line 882
    .line 883
    :cond_23
    move v11, v3

    .line 884
    move/from16 v24, v4

    .line 885
    .line 886
    move/from16 v25, v5

    .line 887
    .line 888
    move-object/from16 v23, v13

    .line 889
    .line 890
    move-object/from16 v22, v15

    .line 891
    .line 892
    move v15, v0

    .line 893
    move v13, v2

    .line 894
    const/4 v0, 0x0

    .line 895
    :goto_12
    if-eqz v0, :cond_28

    .line 896
    .line 897
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 898
    .line 899
    iget v0, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 900
    .line 901
    const/4 v1, 0x1

    .line 902
    sub-int/2addr v9, v1

    .line 903
    :goto_13
    if-lt v9, v15, :cond_24

    .line 904
    .line 905
    invoke-virtual {v6, v9}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 906
    .line 907
    .line 908
    move-result-object v1

    .line 909
    iget-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 910
    .line 911
    iget-object v3, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mChildHelper:Landroidx/recyclerview/widget/ChildHelper;

    .line 912
    .line 913
    invoke-virtual {v3, v1}, Landroidx/recyclerview/widget/ChildHelper;->indexOfChild(Landroid/view/View;)I

    .line 914
    .line 915
    .line 916
    move-result v3

    .line 917
    invoke-virtual {v6, v2, v3, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->scrapOrRecycleView(Landroidx/recyclerview/widget/RecyclerView$Recycler;ILandroid/view/View;)V

    .line 918
    .line 919
    .line 920
    add-int/lit8 v9, v9, -0x1

    .line 921
    .line 922
    goto :goto_13

    .line 923
    :cond_24
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 924
    .line 925
    invoke-virtual {v1, v13}, Landroidx/leanback/widget/Grid;->invalidateItemsAfter(I)V

    .line 926
    .line 927
    .line 928
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 929
    .line 930
    const/high16 v2, 0x10000

    .line 931
    .line 932
    and-int/2addr v1, v2

    .line 933
    if-eqz v1, :cond_26

    .line 934
    .line 935
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->appendVisibleItems()V

    .line 936
    .line 937
    .line 938
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 939
    .line 940
    if-ltz v1, :cond_28

    .line 941
    .line 942
    if-gt v1, v0, :cond_28

    .line 943
    .line 944
    :goto_14
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 945
    .line 946
    iget v1, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 947
    .line 948
    iget v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 949
    .line 950
    if-ge v1, v2, :cond_28

    .line 951
    .line 952
    iget-boolean v1, v0, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 953
    .line 954
    if-eqz v1, :cond_25

    .line 955
    .line 956
    const/4 v1, 0x1

    .line 957
    const v10, 0x7fffffff

    .line 958
    .line 959
    .line 960
    goto :goto_15

    .line 961
    :cond_25
    const/4 v1, 0x1

    .line 962
    const/high16 v10, -0x80000000

    .line 963
    .line 964
    :goto_15
    invoke-virtual {v0, v10, v1}, Landroidx/leanback/widget/Grid;->appendVisibleItems(IZ)Z

    .line 965
    .line 966
    .line 967
    goto :goto_14

    .line 968
    :cond_26
    :goto_16
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 969
    .line 970
    iget-boolean v2, v1, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 971
    .line 972
    if-eqz v2, :cond_27

    .line 973
    .line 974
    const/4 v2, 0x1

    .line 975
    const v10, 0x7fffffff

    .line 976
    .line 977
    .line 978
    goto :goto_17

    .line 979
    :cond_27
    const/4 v2, 0x1

    .line 980
    const/high16 v10, -0x80000000

    .line 981
    .line 982
    :goto_17
    invoke-virtual {v1, v10, v2}, Landroidx/leanback/widget/Grid;->appendVisibleItems(IZ)Z

    .line 983
    .line 984
    .line 985
    move-result v1

    .line 986
    if-eqz v1, :cond_28

    .line 987
    .line 988
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 989
    .line 990
    iget v1, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 991
    .line 992
    if-ge v1, v0, :cond_28

    .line 993
    .line 994
    goto :goto_16

    .line 995
    :cond_28
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updateScrollLimits()V

    .line 996
    .line 997
    .line 998
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updateSecondaryScrollLimits()V

    .line 999
    .line 1000
    .line 1001
    goto :goto_1b

    .line 1002
    :cond_29
    move v11, v3

    .line 1003
    move/from16 v24, v4

    .line 1004
    .line 1005
    move/from16 v25, v5

    .line 1006
    .line 1007
    move-object/from16 v23, v13

    .line 1008
    .line 1009
    move-object/from16 v22, v15

    .line 1010
    .line 1011
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1012
    .line 1013
    and-int/lit8 v1, v1, -0x5

    .line 1014
    .line 1015
    iput v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1016
    .line 1017
    and-int/lit8 v1, v1, -0x11

    .line 1018
    .line 1019
    if-eqz v14, :cond_2a

    .line 1020
    .line 1021
    const/16 v3, 0x10

    .line 1022
    .line 1023
    goto :goto_18

    .line 1024
    :cond_2a
    const/4 v3, 0x0

    .line 1025
    :goto_18
    or-int/2addr v1, v3

    .line 1026
    iput v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1027
    .line 1028
    if-eqz v14, :cond_2c

    .line 1029
    .line 1030
    if-ltz v2, :cond_2b

    .line 1031
    .line 1032
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 1033
    .line 1034
    if-gt v1, v0, :cond_2b

    .line 1035
    .line 1036
    if-ge v1, v2, :cond_2c

    .line 1037
    .line 1038
    :cond_2b
    iget v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 1039
    .line 1040
    move v0, v2

    .line 1041
    :cond_2c
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 1042
    .line 1043
    iput v2, v1, Landroidx/leanback/widget/Grid;->mStartIndex:I

    .line 1044
    .line 1045
    const/4 v1, -0x1

    .line 1046
    if-eq v0, v1, :cond_2e

    .line 1047
    .line 1048
    :goto_19
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 1049
    .line 1050
    iget-boolean v2, v1, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 1051
    .line 1052
    if-eqz v2, :cond_2d

    .line 1053
    .line 1054
    const/4 v2, 0x1

    .line 1055
    const v10, 0x7fffffff

    .line 1056
    .line 1057
    .line 1058
    goto :goto_1a

    .line 1059
    :cond_2d
    const/4 v2, 0x1

    .line 1060
    const/high16 v10, -0x80000000

    .line 1061
    .line 1062
    :goto_1a
    invoke-virtual {v1, v10, v2}, Landroidx/leanback/widget/Grid;->appendVisibleItems(IZ)Z

    .line 1063
    .line 1064
    .line 1065
    move-result v1

    .line 1066
    if-eqz v1, :cond_2e

    .line 1067
    .line 1068
    invoke-virtual {v6, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 1069
    .line 1070
    .line 1071
    move-result-object v1

    .line 1072
    if-nez v1, :cond_2e

    .line 1073
    .line 1074
    goto :goto_19

    .line 1075
    :cond_2e
    :goto_1b
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->updateScrollLimits()V

    .line 1076
    .line 1077
    .line 1078
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 1079
    .line 1080
    iget v7, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 1081
    .line 1082
    iget v9, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 1083
    .line 1084
    neg-int v10, v8

    .line 1085
    neg-int v12, v11

    .line 1086
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 1087
    .line 1088
    invoke-virtual {v6, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 1089
    .line 1090
    .line 1091
    move-result-object v13

    .line 1092
    if-eqz v13, :cond_2f

    .line 1093
    .line 1094
    if-eqz v14, :cond_2f

    .line 1095
    .line 1096
    const/4 v3, 0x0

    .line 1097
    invoke-virtual {v13}, Landroid/view/View;->findFocus()Landroid/view/View;

    .line 1098
    .line 1099
    .line 1100
    move-result-object v2

    .line 1101
    move-object/from16 v0, p0

    .line 1102
    .line 1103
    move-object v1, v13

    .line 1104
    move v4, v10

    .line 1105
    move v5, v12

    .line 1106
    invoke-virtual/range {v0 .. v5}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Landroid/view/View;ZII)V

    .line 1107
    .line 1108
    .line 1109
    :cond_2f
    if-eqz v13, :cond_30

    .line 1110
    .line 1111
    if-eqz v16, :cond_30

    .line 1112
    .line 1113
    invoke-virtual {v13}, Landroid/view/View;->hasFocus()Z

    .line 1114
    .line 1115
    .line 1116
    move-result v0

    .line 1117
    if-nez v0, :cond_30

    .line 1118
    .line 1119
    invoke-virtual {v13}, Landroid/view/View;->requestFocus()Z

    .line 1120
    .line 1121
    .line 1122
    goto :goto_1e

    .line 1123
    :cond_30
    if-nez v16, :cond_34

    .line 1124
    .line 1125
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 1126
    .line 1127
    invoke-virtual {v0}, Landroid/view/ViewGroup;->hasFocus()Z

    .line 1128
    .line 1129
    .line 1130
    move-result v0

    .line 1131
    if-nez v0, :cond_34

    .line 1132
    .line 1133
    if-eqz v13, :cond_31

    .line 1134
    .line 1135
    invoke-virtual {v13}, Landroid/view/View;->hasFocusable()Z

    .line 1136
    .line 1137
    .line 1138
    move-result v0

    .line 1139
    if-eqz v0, :cond_31

    .line 1140
    .line 1141
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 1142
    .line 1143
    invoke-virtual {v0, v13}, Landroid/view/ViewGroup;->focusableViewAvailable(Landroid/view/View;)V

    .line 1144
    .line 1145
    .line 1146
    goto :goto_1d

    .line 1147
    :cond_31
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 1148
    .line 1149
    .line 1150
    move-result v0

    .line 1151
    const/4 v1, 0x0

    .line 1152
    :goto_1c
    if-ge v1, v0, :cond_33

    .line 1153
    .line 1154
    invoke-virtual {v6, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 1155
    .line 1156
    .line 1157
    move-result-object v13

    .line 1158
    if-eqz v13, :cond_32

    .line 1159
    .line 1160
    invoke-virtual {v13}, Landroid/view/View;->hasFocusable()Z

    .line 1161
    .line 1162
    .line 1163
    move-result v2

    .line 1164
    if-eqz v2, :cond_32

    .line 1165
    .line 1166
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 1167
    .line 1168
    invoke-virtual {v0, v13}, Landroid/view/ViewGroup;->focusableViewAvailable(Landroid/view/View;)V

    .line 1169
    .line 1170
    .line 1171
    goto :goto_1d

    .line 1172
    :cond_32
    add-int/lit8 v1, v1, 0x1

    .line 1173
    .line 1174
    goto :goto_1c

    .line 1175
    :cond_33
    :goto_1d
    move-object v1, v13

    .line 1176
    if-eqz v14, :cond_34

    .line 1177
    .line 1178
    if-eqz v1, :cond_34

    .line 1179
    .line 1180
    invoke-virtual {v1}, Landroid/view/View;->hasFocus()Z

    .line 1181
    .line 1182
    .line 1183
    move-result v0

    .line 1184
    if-eqz v0, :cond_34

    .line 1185
    .line 1186
    const/4 v3, 0x0

    .line 1187
    invoke-virtual {v1}, Landroid/view/View;->findFocus()Landroid/view/View;

    .line 1188
    .line 1189
    .line 1190
    move-result-object v2

    .line 1191
    move-object/from16 v0, p0

    .line 1192
    .line 1193
    move v4, v10

    .line 1194
    move v5, v12

    .line 1195
    invoke-virtual/range {v0 .. v5}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Landroid/view/View;ZII)V

    .line 1196
    .line 1197
    .line 1198
    :cond_34
    :goto_1e
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->appendVisibleItems()V

    .line 1199
    .line 1200
    .line 1201
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->prependVisibleItems()V

    .line 1202
    .line 1203
    .line 1204
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 1205
    .line 1206
    iget v1, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 1207
    .line 1208
    if-ne v1, v7, :cond_52

    .line 1209
    .line 1210
    iget v0, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 1211
    .line 1212
    if-ne v0, v9, :cond_52

    .line 1213
    .line 1214
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->removeInvisibleViewsAtFront()V

    .line 1215
    .line 1216
    .line 1217
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->removeInvisibleViewsAtEnd()V

    .line 1218
    .line 1219
    .line 1220
    move-object/from16 v0, p2

    .line 1221
    .line 1222
    iget-boolean v0, v0, Landroidx/recyclerview/widget/RecyclerView$State;->mRunPredictiveAnimations:Z

    .line 1223
    .line 1224
    if-eqz v0, :cond_47

    .line 1225
    .line 1226
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 1227
    .line 1228
    iget-object v0, v0, Landroidx/recyclerview/widget/RecyclerView$Recycler;->mUnmodifiableAttachedScrap:Ljava/util/List;

    .line 1229
    .line 1230
    invoke-interface {v0}, Ljava/util/List;->size()I

    .line 1231
    .line 1232
    .line 1233
    move-result v1

    .line 1234
    if-nez v1, :cond_35

    .line 1235
    .line 1236
    goto/16 :goto_27

    .line 1237
    .line 1238
    :cond_35
    iget-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mDisappearingPositions:[I

    .line 1239
    .line 1240
    if-eqz v2, :cond_36

    .line 1241
    .line 1242
    array-length v3, v2

    .line 1243
    if-le v1, v3, :cond_39

    .line 1244
    .line 1245
    :cond_36
    if-nez v2, :cond_37

    .line 1246
    .line 1247
    const/16 v2, 0x10

    .line 1248
    .line 1249
    goto :goto_1f

    .line 1250
    :cond_37
    array-length v2, v2

    .line 1251
    :goto_1f
    if-ge v2, v1, :cond_38

    .line 1252
    .line 1253
    shl-int/lit8 v2, v2, 0x1

    .line 1254
    .line 1255
    goto :goto_1f

    .line 1256
    :cond_38
    new-array v2, v2, [I

    .line 1257
    .line 1258
    iput-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mDisappearingPositions:[I

    .line 1259
    .line 1260
    :cond_39
    const/4 v2, 0x0

    .line 1261
    const/4 v3, 0x0

    .line 1262
    :goto_20
    if-ge v2, v1, :cond_3b

    .line 1263
    .line 1264
    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 1265
    .line 1266
    .line 1267
    move-result-object v4

    .line 1268
    check-cast v4, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 1269
    .line 1270
    invoke-virtual {v4}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getAbsoluteAdapterPosition()I

    .line 1271
    .line 1272
    .line 1273
    move-result v4

    .line 1274
    if-ltz v4, :cond_3a

    .line 1275
    .line 1276
    iget-object v5, v6, Landroidx/leanback/widget/GridLayoutManager;->mDisappearingPositions:[I

    .line 1277
    .line 1278
    add-int/lit8 v7, v3, 0x1

    .line 1279
    .line 1280
    aput v4, v5, v3

    .line 1281
    .line 1282
    move v3, v7

    .line 1283
    :cond_3a
    add-int/lit8 v2, v2, 0x1

    .line 1284
    .line 1285
    goto :goto_20

    .line 1286
    :cond_3b
    if-lez v3, :cond_45

    .line 1287
    .line 1288
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mDisappearingPositions:[I

    .line 1289
    .line 1290
    const/4 v1, 0x0

    .line 1291
    invoke-static {v0, v1, v3}, Ljava/util/Arrays;->sort([III)V

    .line 1292
    .line 1293
    .line 1294
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 1295
    .line 1296
    iget-object v2, v6, Landroidx/leanback/widget/GridLayoutManager;->mDisappearingPositions:[I

    .line 1297
    .line 1298
    iget v4, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 1299
    .line 1300
    if-ltz v4, :cond_3c

    .line 1301
    .line 1302
    invoke-static {v2, v1, v3, v4}, Ljava/util/Arrays;->binarySearch([IIII)I

    .line 1303
    .line 1304
    .line 1305
    move-result v5

    .line 1306
    goto :goto_21

    .line 1307
    :cond_3c
    const/4 v5, 0x0

    .line 1308
    :goto_21
    iget-object v1, v0, Landroidx/leanback/widget/Grid;->mTmpItem:[Ljava/lang/Object;

    .line 1309
    .line 1310
    if-gez v5, :cond_40

    .line 1311
    .line 1312
    neg-int v5, v5

    .line 1313
    const/4 v7, 0x1

    .line 1314
    sub-int/2addr v5, v7

    .line 1315
    iget-boolean v7, v0, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 1316
    .line 1317
    if-eqz v7, :cond_3d

    .line 1318
    .line 1319
    iget-object v7, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1320
    .line 1321
    check-cast v7, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1322
    .line 1323
    invoke-virtual {v7, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 1324
    .line 1325
    .line 1326
    move-result v7

    .line 1327
    iget-object v8, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1328
    .line 1329
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1330
    .line 1331
    invoke-virtual {v8, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getSize(I)I

    .line 1332
    .line 1333
    .line 1334
    move-result v4

    .line 1335
    sub-int/2addr v7, v4

    .line 1336
    iget v4, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1337
    .line 1338
    sub-int/2addr v7, v4

    .line 1339
    goto :goto_22

    .line 1340
    :cond_3d
    iget-object v7, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1341
    .line 1342
    check-cast v7, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1343
    .line 1344
    invoke-virtual {v7, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 1345
    .line 1346
    .line 1347
    move-result v7

    .line 1348
    iget-object v8, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1349
    .line 1350
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1351
    .line 1352
    invoke-virtual {v8, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getSize(I)I

    .line 1353
    .line 1354
    .line 1355
    move-result v4

    .line 1356
    add-int/2addr v4, v7

    .line 1357
    iget v7, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1358
    .line 1359
    add-int/2addr v7, v4

    .line 1360
    :goto_22
    if-ge v5, v3, :cond_40

    .line 1361
    .line 1362
    aget v4, v2, v5

    .line 1363
    .line 1364
    move-object/from16 v9, v23

    .line 1365
    .line 1366
    invoke-virtual {v9, v4}, Landroid/util/SparseIntArray;->get(I)I

    .line 1367
    .line 1368
    .line 1369
    move-result v8

    .line 1370
    if-gez v8, :cond_3e

    .line 1371
    .line 1372
    const/4 v8, 0x0

    .line 1373
    :cond_3e
    iget-object v10, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1374
    .line 1375
    check-cast v10, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1376
    .line 1377
    const/4 v11, 0x1

    .line 1378
    invoke-virtual {v10, v4, v11, v1, v11}, Landroidx/leanback/widget/GridLayoutManager$2;->createItem(IZ[Ljava/lang/Object;Z)I

    .line 1379
    .line 1380
    .line 1381
    move-result v4

    .line 1382
    iget-object v10, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1383
    .line 1384
    const/4 v11, 0x0

    .line 1385
    aget-object v12, v1, v11

    .line 1386
    .line 1387
    check-cast v10, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1388
    .line 1389
    invoke-virtual {v10, v12, v4, v8, v7}, Landroidx/leanback/widget/GridLayoutManager$2;->addItem(Ljava/lang/Object;III)V

    .line 1390
    .line 1391
    .line 1392
    iget-boolean v8, v0, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 1393
    .line 1394
    if-eqz v8, :cond_3f

    .line 1395
    .line 1396
    sub-int/2addr v7, v4

    .line 1397
    iget v4, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1398
    .line 1399
    sub-int/2addr v7, v4

    .line 1400
    goto :goto_23

    .line 1401
    :cond_3f
    add-int/2addr v7, v4

    .line 1402
    iget v4, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1403
    .line 1404
    add-int/2addr v7, v4

    .line 1405
    :goto_23
    add-int/lit8 v5, v5, 0x1

    .line 1406
    .line 1407
    move-object/from16 v23, v9

    .line 1408
    .line 1409
    goto :goto_22

    .line 1410
    :cond_40
    move-object/from16 v9, v23

    .line 1411
    .line 1412
    iget v4, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 1413
    .line 1414
    if-ltz v4, :cond_41

    .line 1415
    .line 1416
    const/4 v5, 0x0

    .line 1417
    invoke-static {v2, v5, v3, v4}, Ljava/util/Arrays;->binarySearch([IIII)I

    .line 1418
    .line 1419
    .line 1420
    move-result v3

    .line 1421
    goto :goto_24

    .line 1422
    :cond_41
    const/4 v3, 0x0

    .line 1423
    :goto_24
    if-gez v3, :cond_46

    .line 1424
    .line 1425
    neg-int v3, v3

    .line 1426
    add-int/lit8 v3, v3, -0x2

    .line 1427
    .line 1428
    iget-boolean v5, v0, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 1429
    .line 1430
    if-eqz v5, :cond_42

    .line 1431
    .line 1432
    iget-object v5, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1433
    .line 1434
    check-cast v5, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1435
    .line 1436
    invoke-virtual {v5, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 1437
    .line 1438
    .line 1439
    move-result v4

    .line 1440
    goto :goto_25

    .line 1441
    :cond_42
    iget-object v5, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1442
    .line 1443
    check-cast v5, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1444
    .line 1445
    invoke-virtual {v5, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 1446
    .line 1447
    .line 1448
    move-result v4

    .line 1449
    :goto_25
    if-ltz v3, :cond_46

    .line 1450
    .line 1451
    aget v5, v2, v3

    .line 1452
    .line 1453
    invoke-virtual {v9, v5}, Landroid/util/SparseIntArray;->get(I)I

    .line 1454
    .line 1455
    .line 1456
    move-result v7

    .line 1457
    if-gez v7, :cond_43

    .line 1458
    .line 1459
    const/4 v7, 0x0

    .line 1460
    :cond_43
    iget-object v8, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1461
    .line 1462
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1463
    .line 1464
    const/4 v10, 0x1

    .line 1465
    const/4 v11, 0x0

    .line 1466
    invoke-virtual {v8, v5, v11, v1, v10}, Landroidx/leanback/widget/GridLayoutManager$2;->createItem(IZ[Ljava/lang/Object;Z)I

    .line 1467
    .line 1468
    .line 1469
    move-result v5

    .line 1470
    iget-boolean v8, v0, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 1471
    .line 1472
    if-eqz v8, :cond_44

    .line 1473
    .line 1474
    iget v8, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1475
    .line 1476
    add-int/2addr v4, v8

    .line 1477
    add-int/2addr v4, v5

    .line 1478
    goto :goto_26

    .line 1479
    :cond_44
    iget v8, v0, Landroidx/leanback/widget/Grid;->mSpacing:I

    .line 1480
    .line 1481
    sub-int/2addr v4, v8

    .line 1482
    sub-int/2addr v4, v5

    .line 1483
    :goto_26
    iget-object v8, v0, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 1484
    .line 1485
    const/4 v10, 0x0

    .line 1486
    aget-object v11, v1, v10

    .line 1487
    .line 1488
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 1489
    .line 1490
    invoke-virtual {v8, v11, v5, v7, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->addItem(Ljava/lang/Object;III)V

    .line 1491
    .line 1492
    .line 1493
    add-int/lit8 v3, v3, -0x1

    .line 1494
    .line 1495
    goto :goto_25

    .line 1496
    :cond_45
    move-object/from16 v9, v23

    .line 1497
    .line 1498
    :cond_46
    invoke-virtual {v9}, Landroid/util/SparseIntArray;->clear()V

    .line 1499
    .line 1500
    .line 1501
    :cond_47
    :goto_27
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1502
    .line 1503
    and-int/lit16 v1, v0, 0x400

    .line 1504
    .line 1505
    if-eqz v1, :cond_48

    .line 1506
    .line 1507
    and-int/lit16 v0, v0, -0x401

    .line 1508
    .line 1509
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1510
    .line 1511
    goto :goto_29

    .line 1512
    :cond_48
    and-int/lit16 v0, v0, -0x401

    .line 1513
    .line 1514
    const/4 v1, 0x0

    .line 1515
    invoke-virtual {v6, v1}, Landroidx/leanback/widget/GridLayoutManager;->processRowSizeSecondary(Z)Z

    .line 1516
    .line 1517
    .line 1518
    move-result v2

    .line 1519
    const/16 v1, 0x400

    .line 1520
    .line 1521
    if-eqz v2, :cond_49

    .line 1522
    .line 1523
    move v2, v1

    .line 1524
    goto :goto_28

    .line 1525
    :cond_49
    const/4 v2, 0x0

    .line 1526
    :goto_28
    or-int/2addr v0, v2

    .line 1527
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1528
    .line 1529
    and-int/2addr v0, v1

    .line 1530
    if-eqz v0, :cond_4a

    .line 1531
    .line 1532
    iget-object v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 1533
    .line 1534
    sget-object v1, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 1535
    .line 1536
    iget-object v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mRequestLayoutRunnable:Landroidx/leanback/widget/GridLayoutManager$1;

    .line 1537
    .line 1538
    invoke-static {v0, v1}, Landroidx/core/view/ViewCompat$Api16Impl;->postOnAnimation(Landroid/view/View;Ljava/lang/Runnable;)V

    .line 1539
    .line 1540
    .line 1541
    :cond_4a
    :goto_29
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1542
    .line 1543
    and-int/lit8 v0, v0, 0x4

    .line 1544
    .line 1545
    if-eqz v0, :cond_4c

    .line 1546
    .line 1547
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 1548
    .line 1549
    move/from16 v1, v25

    .line 1550
    .line 1551
    if-ne v0, v1, :cond_4b

    .line 1552
    .line 1553
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 1554
    .line 1555
    move/from16 v2, v24

    .line 1556
    .line 1557
    if-ne v1, v2, :cond_4b

    .line 1558
    .line 1559
    invoke-virtual {v6, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 1560
    .line 1561
    .line 1562
    move-result-object v0

    .line 1563
    move-object/from16 v3, v22

    .line 1564
    .line 1565
    if-ne v0, v3, :cond_4b

    .line 1566
    .line 1567
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1568
    .line 1569
    and-int/lit8 v0, v0, 0x8

    .line 1570
    .line 1571
    if-eqz v0, :cond_4c

    .line 1572
    .line 1573
    :cond_4b
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->dispatchChildSelected()V

    .line 1574
    .line 1575
    .line 1576
    goto :goto_2a

    .line 1577
    :cond_4c
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1578
    .line 1579
    and-int/lit8 v0, v0, 0x14

    .line 1580
    .line 1581
    const/16 v4, 0x10

    .line 1582
    .line 1583
    if-ne v0, v4, :cond_4d

    .line 1584
    .line 1585
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->dispatchChildSelected()V

    .line 1586
    .line 1587
    .line 1588
    :cond_4d
    :goto_2a
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->dispatchChildSelectedAndPositioned()V

    .line 1589
    .line 1590
    .line 1591
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1592
    .line 1593
    and-int/lit8 v1, v0, 0x40

    .line 1594
    .line 1595
    if-eqz v1, :cond_51

    .line 1596
    .line 1597
    iget v1, v6, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 1598
    .line 1599
    const/4 v5, 0x1

    .line 1600
    if-ne v1, v5, :cond_4e

    .line 1601
    .line 1602
    iget v0, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mHeight:I

    .line 1603
    .line 1604
    neg-int v0, v0

    .line 1605
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 1606
    .line 1607
    .line 1608
    move-result v1

    .line 1609
    if-lez v1, :cond_50

    .line 1610
    .line 1611
    const/4 v1, 0x0

    .line 1612
    invoke-virtual {v6, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 1613
    .line 1614
    .line 1615
    move-result-object v1

    .line 1616
    invoke-virtual {v1}, Landroid/view/View;->getTop()I

    .line 1617
    .line 1618
    .line 1619
    move-result v1

    .line 1620
    if-gez v1, :cond_50

    .line 1621
    .line 1622
    goto :goto_2b

    .line 1623
    :cond_4e
    const/high16 v7, 0x40000

    .line 1624
    .line 1625
    and-int/2addr v0, v7

    .line 1626
    if-eqz v0, :cond_4f

    .line 1627
    .line 1628
    iget v0, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mWidth:I

    .line 1629
    .line 1630
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 1631
    .line 1632
    .line 1633
    move-result v1

    .line 1634
    if-lez v1, :cond_50

    .line 1635
    .line 1636
    const/4 v10, 0x0

    .line 1637
    invoke-virtual {v6, v10}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 1638
    .line 1639
    .line 1640
    move-result-object v1

    .line 1641
    invoke-virtual {v1}, Landroid/view/View;->getRight()I

    .line 1642
    .line 1643
    .line 1644
    move-result v1

    .line 1645
    if-le v1, v0, :cond_50

    .line 1646
    .line 1647
    move v0, v1

    .line 1648
    goto :goto_2c

    .line 1649
    :cond_4f
    const/4 v10, 0x0

    .line 1650
    iget v0, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mWidth:I

    .line 1651
    .line 1652
    neg-int v0, v0

    .line 1653
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 1654
    .line 1655
    .line 1656
    move-result v1

    .line 1657
    if-lez v1, :cond_50

    .line 1658
    .line 1659
    invoke-virtual {v6, v10}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 1660
    .line 1661
    .line 1662
    move-result-object v1

    .line 1663
    invoke-virtual {v1}, Landroid/view/View;->getLeft()I

    .line 1664
    .line 1665
    .line 1666
    move-result v1

    .line 1667
    if-gez v1, :cond_50

    .line 1668
    .line 1669
    :goto_2b
    add-int/2addr v0, v1

    .line 1670
    :cond_50
    :goto_2c
    invoke-virtual {v6, v0}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionPrimary(I)I

    .line 1671
    .line 1672
    .line 1673
    :cond_51
    iget v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1674
    .line 1675
    and-int/lit8 v0, v0, -0x4

    .line 1676
    .line 1677
    iput v0, v6, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 1678
    .line 1679
    invoke-virtual/range {p0 .. p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 1680
    .line 1681
    .line 1682
    return-void

    .line 1683
    :cond_52
    move-object/from16 v0, p2

    .line 1684
    .line 1685
    move-object/from16 v3, v22

    .line 1686
    .line 1687
    move-object/from16 v9, v23

    .line 1688
    .line 1689
    move/from16 v2, v24

    .line 1690
    .line 1691
    move/from16 v1, v25

    .line 1692
    .line 1693
    const/16 v4, 0x10

    .line 1694
    .line 1695
    const/4 v5, 0x1

    .line 1696
    const/high16 v7, 0x40000

    .line 1697
    .line 1698
    const/4 v10, 0x0

    .line 1699
    move/from16 v25, v1

    .line 1700
    .line 1701
    move/from16 v24, v2

    .line 1702
    .line 1703
    move-object/from16 v22, v3

    .line 1704
    .line 1705
    move-object/from16 v23, v9

    .line 1706
    .line 1707
    goto/16 :goto_1b
.end method

.method public final onLayoutCompleted(Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onMeasure(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;II)V
    .locals 6

    .line 1
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 2
    .line 3
    .line 4
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 5
    .line 6
    if-nez p1, :cond_0

    .line 7
    .line 8
    invoke-static {p3}, Landroid/view/View$MeasureSpec;->getSize(I)I

    .line 9
    .line 10
    .line 11
    move-result p1

    .line 12
    invoke-static {p4}, Landroid/view/View$MeasureSpec;->getSize(I)I

    .line 13
    .line 14
    .line 15
    move-result p2

    .line 16
    invoke-static {p4}, Landroid/view/View$MeasureSpec;->getMode(I)I

    .line 17
    .line 18
    .line 19
    move-result p3

    .line 20
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingTop()I

    .line 21
    .line 22
    .line 23
    move-result p4

    .line 24
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingBottom()I

    .line 25
    .line 26
    .line 27
    move-result v0

    .line 28
    goto :goto_0

    .line 29
    :cond_0
    invoke-static {p3}, Landroid/view/View$MeasureSpec;->getSize(I)I

    .line 30
    .line 31
    .line 32
    move-result p2

    .line 33
    invoke-static {p4}, Landroid/view/View$MeasureSpec;->getSize(I)I

    .line 34
    .line 35
    .line 36
    move-result p1

    .line 37
    invoke-static {p3}, Landroid/view/View$MeasureSpec;->getMode(I)I

    .line 38
    .line 39
    .line 40
    move-result p3

    .line 41
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingLeft()I

    .line 42
    .line 43
    .line 44
    move-result p4

    .line 45
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingRight()I

    .line 46
    .line 47
    .line 48
    move-result v0

    .line 49
    :goto_0
    add-int/2addr v0, p4

    .line 50
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mMaxSizeSecondary:I

    .line 51
    .line 52
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondaryRequested:I

    .line 53
    .line 54
    const/4 v1, -0x2

    .line 55
    const-string/jumbo v2, "wrong spec"

    .line 56
    .line 57
    .line 58
    const/high16 v3, 0x40000000    # 2.0f

    .line 59
    .line 60
    const/high16 v4, -0x80000000

    .line 61
    .line 62
    const/4 v5, 0x1

    .line 63
    if-ne p4, v1, :cond_8

    .line 64
    .line 65
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRowsRequested:I

    .line 66
    .line 67
    if-nez p2, :cond_1

    .line 68
    .line 69
    move p2, v5

    .line 70
    :cond_1
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 71
    .line 72
    const/4 p4, 0x0

    .line 73
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 74
    .line 75
    iget-object p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 76
    .line 77
    if-eqz p4, :cond_2

    .line 78
    .line 79
    array-length p4, p4

    .line 80
    if-eq p4, p2, :cond_3

    .line 81
    .line 82
    :cond_2
    new-array p2, p2, [I

    .line 83
    .line 84
    iput-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 85
    .line 86
    :cond_3
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 87
    .line 88
    iget-boolean p2, p2, Landroidx/recyclerview/widget/RecyclerView$State;->mInPreLayout:Z

    .line 89
    .line 90
    if-eqz p2, :cond_4

    .line 91
    .line 92
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->updatePositionDeltaInPreLayout()V

    .line 93
    .line 94
    .line 95
    :cond_4
    invoke-virtual {p0, v5}, Landroidx/leanback/widget/GridLayoutManager;->processRowSizeSecondary(Z)Z

    .line 96
    .line 97
    .line 98
    if-eq p3, v4, :cond_7

    .line 99
    .line 100
    if-eqz p3, :cond_6

    .line 101
    .line 102
    if-ne p3, v3, :cond_5

    .line 103
    .line 104
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mMaxSizeSecondary:I

    .line 105
    .line 106
    goto/16 :goto_4

    .line 107
    .line 108
    :cond_5
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 109
    .line 110
    invoke-direct {p0, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 111
    .line 112
    .line 113
    throw p0

    .line 114
    :cond_6
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->getSizeSecondary()I

    .line 115
    .line 116
    .line 117
    move-result p2

    .line 118
    goto :goto_1

    .line 119
    :cond_7
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->getSizeSecondary()I

    .line 120
    .line 121
    .line 122
    move-result p2

    .line 123
    add-int/2addr p2, v0

    .line 124
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mMaxSizeSecondary:I

    .line 125
    .line 126
    invoke-static {p2, p3}, Ljava/lang/Math;->min(II)I

    .line 127
    .line 128
    .line 129
    move-result p2

    .line 130
    goto :goto_4

    .line 131
    :cond_8
    if-eq p3, v4, :cond_d

    .line 132
    .line 133
    if-eqz p3, :cond_a

    .line 134
    .line 135
    if-ne p3, v3, :cond_9

    .line 136
    .line 137
    goto :goto_2

    .line 138
    :cond_9
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 139
    .line 140
    invoke-direct {p0, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 141
    .line 142
    .line 143
    throw p0

    .line 144
    :cond_a
    if-nez p4, :cond_b

    .line 145
    .line 146
    sub-int p4, p2, v0

    .line 147
    .line 148
    :cond_b
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 149
    .line 150
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRowsRequested:I

    .line 151
    .line 152
    if-nez p2, :cond_c

    .line 153
    .line 154
    move p2, v5

    .line 155
    :cond_c
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 156
    .line 157
    mul-int/2addr p4, p2

    .line 158
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 159
    .line 160
    sub-int/2addr p2, v5

    .line 161
    mul-int/2addr p2, p3

    .line 162
    add-int/2addr p2, p4

    .line 163
    :goto_1
    add-int/2addr p2, v0

    .line 164
    goto :goto_4

    .line 165
    :cond_d
    :goto_2
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRowsRequested:I

    .line 166
    .line 167
    if-nez v1, :cond_e

    .line 168
    .line 169
    if-nez p4, :cond_e

    .line 170
    .line 171
    iput v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 172
    .line 173
    sub-int p4, p2, v0

    .line 174
    .line 175
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 176
    .line 177
    goto :goto_3

    .line 178
    :cond_e
    if-nez v1, :cond_f

    .line 179
    .line 180
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 181
    .line 182
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 183
    .line 184
    add-int v2, p2, v1

    .line 185
    .line 186
    add-int/2addr p4, v1

    .line 187
    div-int/2addr v2, p4

    .line 188
    iput v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 189
    .line 190
    goto :goto_3

    .line 191
    :cond_f
    if-nez p4, :cond_10

    .line 192
    .line 193
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 194
    .line 195
    sub-int p4, p2, v0

    .line 196
    .line 197
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 198
    .line 199
    add-int/lit8 v3, v1, -0x1

    .line 200
    .line 201
    mul-int/2addr v3, v2

    .line 202
    sub-int/2addr p4, v3

    .line 203
    div-int/2addr p4, v1

    .line 204
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 205
    .line 206
    goto :goto_3

    .line 207
    :cond_10
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 208
    .line 209
    iput p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 210
    .line 211
    :goto_3
    if-ne p3, v4, :cond_11

    .line 212
    .line 213
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 214
    .line 215
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 216
    .line 217
    mul-int/2addr p3, p4

    .line 218
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mSpacingSecondary:I

    .line 219
    .line 220
    sub-int/2addr p4, v5

    .line 221
    mul-int/2addr p4, v1

    .line 222
    add-int/2addr p4, p3

    .line 223
    add-int/2addr p4, v0

    .line 224
    if-ge p4, p2, :cond_11

    .line 225
    .line 226
    move p2, p4

    .line 227
    :cond_11
    :goto_4
    iget p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 228
    .line 229
    if-nez p3, :cond_12

    .line 230
    .line 231
    iget-object p3, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 232
    .line 233
    invoke-static {p3, p1, p2}, Landroidx/recyclerview/widget/RecyclerView;->access$6300(Landroidx/recyclerview/widget/RecyclerView;II)V

    .line 234
    .line 235
    .line 236
    goto :goto_5

    .line 237
    :cond_12
    iget-object p3, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 238
    .line 239
    invoke-static {p3, p2, p1}, Landroidx/recyclerview/widget/RecyclerView;->access$6300(Landroidx/recyclerview/widget/RecyclerView;II)V

    .line 240
    .line 241
    .line 242
    :goto_5
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 243
    .line 244
    .line 245
    return-void
.end method

.method public final onRequestChildFocus(Landroidx/recyclerview/widget/RecyclerView;Landroid/view/View;Landroid/view/View;)Z
    .locals 7

    .line 1
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const v0, 0x8000

    .line 4
    .line 5
    .line 6
    and-int/2addr p1, v0

    .line 7
    const/4 v0, 0x1

    .line 8
    if-eqz p1, :cond_0

    .line 9
    .line 10
    return v0

    .line 11
    :cond_0
    invoke-static {p2}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 12
    .line 13
    .line 14
    move-result p1

    .line 15
    const/4 v1, -0x1

    .line 16
    if-ne p1, v1, :cond_1

    .line 17
    .line 18
    return v0

    .line 19
    :cond_1
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 20
    .line 21
    and-int/lit8 p1, p1, 0x23

    .line 22
    .line 23
    if-nez p1, :cond_2

    .line 24
    .line 25
    const/4 v5, 0x0

    .line 26
    const/4 v6, 0x0

    .line 27
    const/4 v4, 0x1

    .line 28
    move-object v1, p0

    .line 29
    move-object v2, p2

    .line 30
    move-object v3, p3

    .line 31
    invoke-virtual/range {v1 .. v6}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Landroid/view/View;ZII)V

    .line 32
    .line 33
    .line 34
    :cond_2
    return v0
.end method

.method public final onRestoreInstanceState(Landroid/os/Parcelable;)V
    .locals 1

    .line 1
    instance-of v0, p1, Landroidx/leanback/widget/GridLayoutManager$SavedState;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$SavedState;

    .line 7
    .line 8
    iget p1, p1, Landroidx/leanback/widget/GridLayoutManager$SavedState;->index:I

    .line 9
    .line 10
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 11
    .line 12
    const/4 p1, 0x0

    .line 13
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 14
    .line 15
    iget-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 16
    .line 17
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 18
    .line 19
    .line 20
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 21
    .line 22
    or-int/lit16 p1, p1, 0x100

    .line 23
    .line 24
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 25
    .line 26
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->requestLayout()V

    .line 27
    .line 28
    .line 29
    return-void
.end method

.method public final onSaveInstanceState()Landroid/os/Parcelable;
    .locals 4

    .line 1
    new-instance v0, Landroidx/leanback/widget/GridLayoutManager$SavedState;

    .line 2
    .line 3
    invoke-direct {v0}, Landroidx/leanback/widget/GridLayoutManager$SavedState;-><init>()V

    .line 4
    .line 5
    .line 6
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 7
    .line 8
    iput v1, v0, Landroidx/leanback/widget/GridLayoutManager$SavedState;->index:I

    .line 9
    .line 10
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mChildrenStates:Landroidx/leanback/widget/ViewsStateBundle;

    .line 11
    .line 12
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 13
    .line 14
    .line 15
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 16
    .line 17
    .line 18
    move-result v1

    .line 19
    const/4 v2, 0x0

    .line 20
    :goto_0
    if-ge v2, v1, :cond_0

    .line 21
    .line 22
    invoke-virtual {p0, v2}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 23
    .line 24
    .line 25
    move-result-object v3

    .line 26
    invoke-static {v3}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 27
    .line 28
    .line 29
    add-int/lit8 v2, v2, 0x1

    .line 30
    .line 31
    goto :goto_0

    .line 32
    :cond_0
    const/4 p0, 0x0

    .line 33
    iput-object p0, v0, Landroidx/leanback/widget/GridLayoutManager$SavedState;->childStates:Landroid/os/Bundle;

    .line 34
    .line 35
    return-object v0
.end method

.method public final performAccessibilityAction(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;ILandroid/os/Bundle;)Z
    .locals 4

    .line 1
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const/high16 v0, 0x20000

    .line 4
    .line 5
    and-int/2addr p4, v0

    .line 6
    const/4 v0, 0x0

    .line 7
    const/4 v1, 0x1

    .line 8
    if-eqz p4, :cond_0

    .line 9
    .line 10
    move p4, v1

    .line 11
    goto :goto_0

    .line 12
    :cond_0
    move p4, v0

    .line 13
    :goto_0
    if-nez p4, :cond_1

    .line 14
    .line 15
    return v1

    .line 16
    :cond_1
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 17
    .line 18
    .line 19
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 20
    .line 21
    const/high16 p4, 0x40000

    .line 22
    .line 23
    and-int/2addr p1, p4

    .line 24
    if-eqz p1, :cond_2

    .line 25
    .line 26
    move p1, v1

    .line 27
    goto :goto_1

    .line 28
    :cond_2
    move p1, v0

    .line 29
    :goto_1
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 30
    .line 31
    const/16 v2, 0x2000

    .line 32
    .line 33
    const/16 v3, 0x1000

    .line 34
    .line 35
    if-nez p4, :cond_4

    .line 36
    .line 37
    sget-object p4, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_LEFT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 38
    .line 39
    invoke-virtual {p4}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->getId()I

    .line 40
    .line 41
    .line 42
    move-result p4

    .line 43
    if-ne p3, p4, :cond_3

    .line 44
    .line 45
    if-eqz p1, :cond_5

    .line 46
    .line 47
    goto :goto_3

    .line 48
    :cond_3
    sget-object p4, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_RIGHT:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 49
    .line 50
    invoke-virtual {p4}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->getId()I

    .line 51
    .line 52
    .line 53
    move-result p4

    .line 54
    if-ne p3, p4, :cond_8

    .line 55
    .line 56
    if-eqz p1, :cond_7

    .line 57
    .line 58
    goto :goto_2

    .line 59
    :cond_4
    sget-object p1, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_UP:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 60
    .line 61
    invoke-virtual {p1}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->getId()I

    .line 62
    .line 63
    .line 64
    move-result p1

    .line 65
    if-ne p3, p1, :cond_6

    .line 66
    .line 67
    :cond_5
    :goto_2
    move p3, v2

    .line 68
    goto :goto_4

    .line 69
    :cond_6
    sget-object p1, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_SCROLL_DOWN:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 70
    .line 71
    invoke-virtual {p1}, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->getId()I

    .line 72
    .line 73
    .line 74
    move-result p1

    .line 75
    if-ne p3, p1, :cond_8

    .line 76
    .line 77
    :cond_7
    :goto_3
    move p3, v3

    .line 78
    :cond_8
    :goto_4
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 79
    .line 80
    if-nez p1, :cond_9

    .line 81
    .line 82
    if-ne p3, v2, :cond_9

    .line 83
    .line 84
    move p4, v1

    .line 85
    goto :goto_5

    .line 86
    :cond_9
    move p4, v0

    .line 87
    :goto_5
    invoke-virtual {p2}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 88
    .line 89
    .line 90
    move-result p2

    .line 91
    sub-int/2addr p2, v1

    .line 92
    if-ne p1, p2, :cond_a

    .line 93
    .line 94
    if-ne p3, v3, :cond_a

    .line 95
    .line 96
    move p1, v1

    .line 97
    goto :goto_6

    .line 98
    :cond_a
    move p1, v0

    .line 99
    :goto_6
    if-nez p4, :cond_e

    .line 100
    .line 101
    if-eqz p1, :cond_b

    .line 102
    .line 103
    goto :goto_7

    .line 104
    :cond_b
    if-eq p3, v3, :cond_d

    .line 105
    .line 106
    if-eq p3, v2, :cond_c

    .line 107
    .line 108
    goto :goto_8

    .line 109
    :cond_c
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->processPendingMovement(Z)V

    .line 110
    .line 111
    .line 112
    const/4 p1, -0x1

    .line 113
    invoke-virtual {p0, p1, v0}, Landroidx/leanback/widget/GridLayoutManager;->processSelectionMoves(IZ)I

    .line 114
    .line 115
    .line 116
    goto :goto_8

    .line 117
    :cond_d
    invoke-virtual {p0, v1}, Landroidx/leanback/widget/GridLayoutManager;->processPendingMovement(Z)V

    .line 118
    .line 119
    .line 120
    invoke-virtual {p0, v1, v0}, Landroidx/leanback/widget/GridLayoutManager;->processSelectionMoves(IZ)I

    .line 121
    .line 122
    .line 123
    goto :goto_8

    .line 124
    :cond_e
    :goto_7
    invoke-static {v3}, Landroid/view/accessibility/AccessibilityEvent;->obtain(I)Landroid/view/accessibility/AccessibilityEvent;

    .line 125
    .line 126
    .line 127
    move-result-object p1

    .line 128
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 129
    .line 130
    invoke-virtual {p2, p1}, Landroid/view/ViewGroup;->onInitializeAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)V

    .line 131
    .line 132
    .line 133
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 134
    .line 135
    invoke-virtual {p2, p2, p1}, Landroid/view/ViewGroup;->requestSendAccessibilityEvent(Landroid/view/View;Landroid/view/accessibility/AccessibilityEvent;)Z

    .line 136
    .line 137
    .line 138
    :goto_8
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 139
    .line 140
    .line 141
    return v1
.end method

.method public final prependVisibleItems()V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 2
    .line 3
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 4
    .line 5
    const/high16 v2, 0x40000

    .line 6
    .line 7
    and-int/2addr v1, v2

    .line 8
    const/4 v2, 0x0

    .line 9
    if-eqz v1, :cond_0

    .line 10
    .line 11
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 12
    .line 13
    add-int/2addr v1, v2

    .line 14
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 15
    .line 16
    add-int/2addr v1, p0

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 19
    .line 20
    rsub-int/lit8 v1, p0, 0x0

    .line 21
    .line 22
    :goto_0
    invoke-virtual {v0, v1, v2}, Landroidx/leanback/widget/Grid;->prependVisibleItems(IZ)Z

    .line 23
    .line 24
    .line 25
    return-void
.end method

.method public final processPendingMovement(Z)V
    .locals 4

    .line 1
    if-eqz p1, :cond_0

    .line 2
    .line 3
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->hasCreatedLastItem()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-eqz v0, :cond_1

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->hasCreatedFirstItem()Z

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    if-eqz v0, :cond_1

    .line 15
    .line 16
    :goto_0
    return-void

    .line 17
    :cond_1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 18
    .line 19
    const/4 v1, -0x1

    .line 20
    const/4 v2, 0x1

    .line 21
    if-nez v0, :cond_4

    .line 22
    .line 23
    new-instance v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 24
    .line 25
    if-eqz p1, :cond_2

    .line 26
    .line 27
    move v1, v2

    .line 28
    :cond_2
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 29
    .line 30
    const/4 v3, 0x0

    .line 31
    if-le p1, v2, :cond_3

    .line 32
    .line 33
    goto :goto_1

    .line 34
    :cond_3
    move v2, v3

    .line 35
    :goto_1
    invoke-direct {v0, p0, v1, v2}, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;-><init>(Landroidx/leanback/widget/GridLayoutManager;IZ)V

    .line 36
    .line 37
    .line 38
    iput v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 39
    .line 40
    invoke-virtual {p0, v0}, Landroidx/leanback/widget/GridLayoutManager;->startSmoothScroll(Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;)V

    .line 41
    .line 42
    .line 43
    goto :goto_2

    .line 44
    :cond_4
    if-eqz p1, :cond_5

    .line 45
    .line 46
    iget p0, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->mPendingMoves:I

    .line 47
    .line 48
    iget-object p1, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->this$0:Landroidx/leanback/widget/GridLayoutManager;

    .line 49
    .line 50
    iget p1, p1, Landroidx/leanback/widget/GridLayoutManager;->mMaxPendingMoves:I

    .line 51
    .line 52
    if-ge p0, p1, :cond_6

    .line 53
    .line 54
    add-int/2addr p0, v2

    .line 55
    iput p0, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->mPendingMoves:I

    .line 56
    .line 57
    goto :goto_2

    .line 58
    :cond_5
    iget p0, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->mPendingMoves:I

    .line 59
    .line 60
    iget-object p1, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->this$0:Landroidx/leanback/widget/GridLayoutManager;

    .line 61
    .line 62
    iget p1, p1, Landroidx/leanback/widget/GridLayoutManager;->mMaxPendingMoves:I

    .line 63
    .line 64
    neg-int p1, p1

    .line 65
    if-le p0, p1, :cond_6

    .line 66
    .line 67
    add-int/2addr p0, v1

    .line 68
    iput p0, v0, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;->mPendingMoves:I

    .line 69
    .line 70
    :cond_6
    :goto_2
    return-void
.end method

.method public final processRowSizeSecondary(Z)Z
    .locals 16

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    iget v1, v0, Landroidx/leanback/widget/GridLayoutManager;->mFixedRowSizeSecondary:I

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    if-nez v1, :cond_19

    .line 7
    .line 8
    iget-object v1, v0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 9
    .line 10
    if-nez v1, :cond_0

    .line 11
    .line 12
    goto/16 :goto_c

    .line 13
    .line 14
    :cond_0
    iget-object v1, v0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 15
    .line 16
    if-nez v1, :cond_1

    .line 17
    .line 18
    const/4 v1, 0x0

    .line 19
    goto :goto_0

    .line 20
    :cond_1
    iget v4, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 21
    .line 22
    iget v5, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 23
    .line 24
    invoke-virtual {v1, v4, v5}, Landroidx/leanback/widget/Grid;->getItemPositionsInRows(II)[Landroidx/collection/CircularIntArray;

    .line 25
    .line 26
    .line 27
    move-result-object v1

    .line 28
    :goto_0
    move v5, v2

    .line 29
    move v6, v5

    .line 30
    const/4 v7, -0x1

    .line 31
    :goto_1
    iget v8, v0, Landroidx/leanback/widget/GridLayoutManager;->mNumRows:I

    .line 32
    .line 33
    if-ge v5, v8, :cond_18

    .line 34
    .line 35
    if-nez v1, :cond_2

    .line 36
    .line 37
    const/4 v8, 0x0

    .line 38
    goto :goto_2

    .line 39
    :cond_2
    aget-object v8, v1, v5

    .line 40
    .line 41
    :goto_2
    if-nez v8, :cond_3

    .line 42
    .line 43
    move v9, v2

    .line 44
    goto :goto_3

    .line 45
    :cond_3
    iget v9, v8, Landroidx/collection/CircularIntArray;->tail:I

    .line 46
    .line 47
    add-int/2addr v9, v2

    .line 48
    iget v10, v8, Landroidx/collection/CircularIntArray;->capacityBitmask:I

    .line 49
    .line 50
    and-int/2addr v9, v10

    .line 51
    :goto_3
    move v10, v2

    .line 52
    const/4 v11, -0x1

    .line 53
    :goto_4
    if-ge v10, v9, :cond_c

    .line 54
    .line 55
    if-ltz v10, :cond_a

    .line 56
    .line 57
    iget v12, v8, Landroidx/collection/CircularIntArray;->tail:I

    .line 58
    .line 59
    add-int/lit8 v13, v12, 0x0

    .line 60
    .line 61
    iget v14, v8, Landroidx/collection/CircularIntArray;->capacityBitmask:I

    .line 62
    .line 63
    and-int/2addr v13, v14

    .line 64
    if-ge v10, v13, :cond_b

    .line 65
    .line 66
    iget-object v13, v8, Landroidx/collection/CircularIntArray;->elements:[I

    .line 67
    .line 68
    add-int/lit8 v15, v10, 0x0

    .line 69
    .line 70
    and-int/2addr v15, v14

    .line 71
    aget v15, v13, v15

    .line 72
    .line 73
    add-int/lit8 v3, v10, 0x1

    .line 74
    .line 75
    if-ltz v3, :cond_9

    .line 76
    .line 77
    add-int/lit8 v12, v12, 0x0

    .line 78
    .line 79
    and-int/2addr v12, v14

    .line 80
    if-ge v3, v12, :cond_9

    .line 81
    .line 82
    add-int/lit8 v3, v3, 0x0

    .line 83
    .line 84
    and-int/2addr v3, v14

    .line 85
    aget v3, v13, v3

    .line 86
    .line 87
    :goto_5
    if-gt v15, v3, :cond_8

    .line 88
    .line 89
    iget v12, v0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 90
    .line 91
    sub-int v12, v15, v12

    .line 92
    .line 93
    invoke-virtual {v0, v12}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 94
    .line 95
    .line 96
    move-result-object v12

    .line 97
    if-nez v12, :cond_4

    .line 98
    .line 99
    goto :goto_7

    .line 100
    :cond_4
    if-eqz p1, :cond_5

    .line 101
    .line 102
    invoke-virtual {v0, v12}, Landroidx/leanback/widget/GridLayoutManager;->measureChild(Landroid/view/View;)V

    .line 103
    .line 104
    .line 105
    :cond_5
    iget v13, v0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 106
    .line 107
    if-nez v13, :cond_6

    .line 108
    .line 109
    invoke-static {v12}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredHeightWithMargin(Landroid/view/View;)I

    .line 110
    .line 111
    .line 112
    move-result v12

    .line 113
    goto :goto_6

    .line 114
    :cond_6
    invoke-static {v12}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredWidthWithMargin(Landroid/view/View;)I

    .line 115
    .line 116
    .line 117
    move-result v12

    .line 118
    :goto_6
    if-le v12, v11, :cond_7

    .line 119
    .line 120
    move v11, v12

    .line 121
    :cond_7
    :goto_7
    add-int/lit8 v15, v15, 0x1

    .line 122
    .line 123
    goto :goto_5

    .line 124
    :cond_8
    add-int/lit8 v10, v10, 0x2

    .line 125
    .line 126
    goto :goto_4

    .line 127
    :cond_9
    sget v0, Landroidx/collection/CollectionPlatformUtils;->$r8$clinit:I

    .line 128
    .line 129
    new-instance v0, Ljava/lang/ArrayIndexOutOfBoundsException;

    .line 130
    .line 131
    invoke-direct {v0}, Ljava/lang/ArrayIndexOutOfBoundsException;-><init>()V

    .line 132
    .line 133
    .line 134
    throw v0

    .line 135
    :cond_a
    invoke-virtual {v8}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 136
    .line 137
    .line 138
    :cond_b
    sget v0, Landroidx/collection/CollectionPlatformUtils;->$r8$clinit:I

    .line 139
    .line 140
    new-instance v0, Ljava/lang/ArrayIndexOutOfBoundsException;

    .line 141
    .line 142
    invoke-direct {v0}, Ljava/lang/ArrayIndexOutOfBoundsException;-><init>()V

    .line 143
    .line 144
    .line 145
    throw v0

    .line 146
    :cond_c
    iget-object v3, v0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 147
    .line 148
    invoke-virtual {v3}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 149
    .line 150
    .line 151
    move-result v3

    .line 152
    iget-object v8, v0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 153
    .line 154
    iget-boolean v8, v8, Landroidx/recyclerview/widget/RecyclerView;->mHasFixedSize:Z

    .line 155
    .line 156
    const/4 v9, 0x1

    .line 157
    if-nez v8, :cond_15

    .line 158
    .line 159
    if-eqz p1, :cond_15

    .line 160
    .line 161
    if-gez v11, :cond_15

    .line 162
    .line 163
    if-lez v3, :cond_15

    .line 164
    .line 165
    if-gez v7, :cond_14

    .line 166
    .line 167
    iget v8, v0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 168
    .line 169
    if-gez v8, :cond_d

    .line 170
    .line 171
    move v8, v2

    .line 172
    goto :goto_8

    .line 173
    :cond_d
    if-lt v8, v3, :cond_e

    .line 174
    .line 175
    add-int/lit8 v8, v3, -0x1

    .line 176
    .line 177
    :cond_e
    :goto_8
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 178
    .line 179
    .line 180
    move-result v10

    .line 181
    if-lez v10, :cond_11

    .line 182
    .line 183
    iget-object v10, v0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 184
    .line 185
    invoke-virtual {v0, v2}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 186
    .line 187
    .line 188
    move-result-object v12

    .line 189
    invoke-virtual {v10, v12}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 190
    .line 191
    .line 192
    move-result-object v10

    .line 193
    invoke-virtual {v10}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getLayoutPosition()I

    .line 194
    .line 195
    .line 196
    move-result v10

    .line 197
    iget-object v12, v0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 198
    .line 199
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 200
    .line 201
    .line 202
    move-result v13

    .line 203
    sub-int/2addr v13, v9

    .line 204
    invoke-virtual {v0, v13}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 205
    .line 206
    .line 207
    move-result-object v13

    .line 208
    invoke-virtual {v12, v13}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 209
    .line 210
    .line 211
    move-result-object v12

    .line 212
    invoke-virtual {v12}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getLayoutPosition()I

    .line 213
    .line 214
    .line 215
    move-result v12

    .line 216
    if-lt v8, v10, :cond_11

    .line 217
    .line 218
    if-gt v8, v12, :cond_11

    .line 219
    .line 220
    sub-int v13, v8, v10

    .line 221
    .line 222
    sub-int v8, v12, v8

    .line 223
    .line 224
    if-gt v13, v8, :cond_f

    .line 225
    .line 226
    add-int/lit8 v8, v10, -0x1

    .line 227
    .line 228
    goto :goto_9

    .line 229
    :cond_f
    add-int/lit8 v8, v12, 0x1

    .line 230
    .line 231
    :goto_9
    if-gez v8, :cond_10

    .line 232
    .line 233
    add-int/lit8 v13, v3, -0x1

    .line 234
    .line 235
    if-ge v12, v13, :cond_10

    .line 236
    .line 237
    add-int/lit8 v8, v12, 0x1

    .line 238
    .line 239
    goto :goto_a

    .line 240
    :cond_10
    if-lt v8, v3, :cond_11

    .line 241
    .line 242
    if-lez v10, :cond_11

    .line 243
    .line 244
    add-int/lit8 v8, v10, -0x1

    .line 245
    .line 246
    :cond_11
    :goto_a
    if-ltz v8, :cond_14

    .line 247
    .line 248
    if-ge v8, v3, :cond_14

    .line 249
    .line 250
    invoke-static {v2, v2}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 251
    .line 252
    .line 253
    move-result v3

    .line 254
    invoke-static {v2, v2}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    .line 255
    .line 256
    .line 257
    move-result v7

    .line 258
    iget-object v10, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 259
    .line 260
    invoke-virtual {v10, v8}, Landroidx/recyclerview/widget/RecyclerView$Recycler;->getViewForPosition(I)Landroid/view/View;

    .line 261
    .line 262
    .line 263
    move-result-object v8

    .line 264
    iget-object v10, v0, Landroidx/leanback/widget/GridLayoutManager;->mMeasuredDimension:[I

    .line 265
    .line 266
    if-eqz v8, :cond_12

    .line 267
    .line 268
    invoke-virtual {v8}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 269
    .line 270
    .line 271
    move-result-object v12

    .line 272
    check-cast v12, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 273
    .line 274
    sget-object v13, Landroidx/leanback/widget/GridLayoutManager;->sTempRect:Landroid/graphics/Rect;

    .line 275
    .line 276
    invoke-virtual {v0, v13, v8}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->calculateItemDecorationsForChild(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 277
    .line 278
    .line 279
    iget v14, v12, Landroid/view/ViewGroup$MarginLayoutParams;->leftMargin:I

    .line 280
    .line 281
    iget v15, v12, Landroid/view/ViewGroup$MarginLayoutParams;->rightMargin:I

    .line 282
    .line 283
    add-int/2addr v14, v15

    .line 284
    iget v15, v13, Landroid/graphics/Rect;->left:I

    .line 285
    .line 286
    add-int/2addr v14, v15

    .line 287
    iget v15, v13, Landroid/graphics/Rect;->right:I

    .line 288
    .line 289
    add-int/2addr v14, v15

    .line 290
    iget v15, v12, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    .line 291
    .line 292
    iget v4, v12, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 293
    .line 294
    add-int/2addr v15, v4

    .line 295
    iget v4, v13, Landroid/graphics/Rect;->top:I

    .line 296
    .line 297
    add-int/2addr v15, v4

    .line 298
    iget v4, v13, Landroid/graphics/Rect;->bottom:I

    .line 299
    .line 300
    add-int/2addr v15, v4

    .line 301
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingLeft()I

    .line 302
    .line 303
    .line 304
    move-result v4

    .line 305
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingRight()I

    .line 306
    .line 307
    .line 308
    move-result v13

    .line 309
    add-int/2addr v13, v4

    .line 310
    add-int/2addr v13, v14

    .line 311
    iget v4, v12, Landroid/view/ViewGroup$MarginLayoutParams;->width:I

    .line 312
    .line 313
    invoke-static {v3, v13, v4}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 314
    .line 315
    .line 316
    move-result v3

    .line 317
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingTop()I

    .line 318
    .line 319
    .line 320
    move-result v4

    .line 321
    invoke-virtual/range {p0 .. p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getPaddingBottom()I

    .line 322
    .line 323
    .line 324
    move-result v13

    .line 325
    add-int/2addr v13, v4

    .line 326
    add-int/2addr v13, v15

    .line 327
    iget v4, v12, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    .line 328
    .line 329
    invoke-static {v7, v13, v4}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    .line 330
    .line 331
    .line 332
    move-result v4

    .line 333
    invoke-virtual {v8, v3, v4}, Landroid/view/View;->measure(II)V

    .line 334
    .line 335
    .line 336
    invoke-static {v8}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredWidthWithMargin(Landroid/view/View;)I

    .line 337
    .line 338
    .line 339
    move-result v3

    .line 340
    aput v3, v10, v2

    .line 341
    .line 342
    invoke-static {v8}, Landroidx/leanback/widget/GridLayoutManager;->getDecoratedMeasuredHeightWithMargin(Landroid/view/View;)I

    .line 343
    .line 344
    .line 345
    move-result v3

    .line 346
    aput v3, v10, v9

    .line 347
    .line 348
    iget-object v3, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 349
    .line 350
    invoke-virtual {v3, v8}, Landroidx/recyclerview/widget/RecyclerView$Recycler;->recycleView(Landroid/view/View;)V

    .line 351
    .line 352
    .line 353
    :cond_12
    iget v3, v0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 354
    .line 355
    if-nez v3, :cond_13

    .line 356
    .line 357
    aget v3, v10, v9

    .line 358
    .line 359
    goto :goto_b

    .line 360
    :cond_13
    aget v3, v10, v2

    .line 361
    .line 362
    :goto_b
    move v7, v3

    .line 363
    :cond_14
    if-ltz v7, :cond_15

    .line 364
    .line 365
    move v11, v7

    .line 366
    :cond_15
    if-gez v11, :cond_16

    .line 367
    .line 368
    move v11, v2

    .line 369
    :cond_16
    iget-object v3, v0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondary:[I

    .line 370
    .line 371
    aget v4, v3, v5

    .line 372
    .line 373
    if-eq v4, v11, :cond_17

    .line 374
    .line 375
    aput v11, v3, v5

    .line 376
    .line 377
    move v6, v9

    .line 378
    :cond_17
    add-int/lit8 v5, v5, 0x1

    .line 379
    .line 380
    goto/16 :goto_1

    .line 381
    .line 382
    :cond_18
    return v6

    .line 383
    :cond_19
    :goto_c
    return v2
.end method

.method public final processSelectionMoves(IZ)I
    .locals 11

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return p1

    .line 6
    :cond_0
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 7
    .line 8
    const/4 v2, -0x1

    .line 9
    if-eq v1, v2, :cond_2

    .line 10
    .line 11
    invoke-virtual {v0, v1}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    if-nez v0, :cond_1

    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_1
    iget v0, v0, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 19
    .line 20
    goto :goto_1

    .line 21
    :cond_2
    :goto_0
    move v0, v2

    .line 22
    :goto_1
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 23
    .line 24
    .line 25
    move-result v3

    .line 26
    const/4 v4, 0x0

    .line 27
    const/4 v5, 0x0

    .line 28
    move v6, v4

    .line 29
    :goto_2
    const/4 v7, 0x1

    .line 30
    if-ge v6, v3, :cond_d

    .line 31
    .line 32
    if-eqz p1, :cond_d

    .line 33
    .line 34
    if-lez p1, :cond_3

    .line 35
    .line 36
    move v8, v6

    .line 37
    goto :goto_3

    .line 38
    :cond_3
    add-int/lit8 v8, v3, -0x1

    .line 39
    .line 40
    sub-int/2addr v8, v6

    .line 41
    :goto_3
    invoke-virtual {p0, v8}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 42
    .line 43
    .line 44
    move-result-object v9

    .line 45
    invoke-virtual {v9}, Landroid/view/View;->getVisibility()I

    .line 46
    .line 47
    .line 48
    move-result v10

    .line 49
    if-nez v10, :cond_4

    .line 50
    .line 51
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->hasFocus()Z

    .line 52
    .line 53
    .line 54
    move-result v10

    .line 55
    if-eqz v10, :cond_5

    .line 56
    .line 57
    invoke-virtual {v9}, Landroid/view/View;->hasFocusable()Z

    .line 58
    .line 59
    .line 60
    move-result v10

    .line 61
    if-eqz v10, :cond_4

    .line 62
    .line 63
    goto :goto_4

    .line 64
    :cond_4
    move v7, v4

    .line 65
    :cond_5
    :goto_4
    if-nez v7, :cond_6

    .line 66
    .line 67
    goto :goto_8

    .line 68
    :cond_6
    invoke-virtual {p0, v8}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 69
    .line 70
    .line 71
    move-result-object v7

    .line 72
    invoke-static {v7}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 73
    .line 74
    .line 75
    move-result v7

    .line 76
    iget-object v8, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 77
    .line 78
    invoke-virtual {v8, v7}, Landroidx/leanback/widget/Grid;->getLocation(I)Landroidx/leanback/widget/Grid$Location;

    .line 79
    .line 80
    .line 81
    move-result-object v8

    .line 82
    if-nez v8, :cond_7

    .line 83
    .line 84
    move v8, v2

    .line 85
    goto :goto_5

    .line 86
    :cond_7
    iget v8, v8, Landroidx/leanback/widget/Grid$Location;->row:I

    .line 87
    .line 88
    :goto_5
    if-ne v0, v2, :cond_8

    .line 89
    .line 90
    move v1, v7

    .line 91
    move v0, v8

    .line 92
    :goto_6
    move-object v5, v9

    .line 93
    goto :goto_8

    .line 94
    :cond_8
    if-ne v8, v0, :cond_c

    .line 95
    .line 96
    if-lez p1, :cond_9

    .line 97
    .line 98
    if-gt v7, v1, :cond_a

    .line 99
    .line 100
    :cond_9
    if-gez p1, :cond_c

    .line 101
    .line 102
    if-ge v7, v1, :cond_c

    .line 103
    .line 104
    :cond_a
    if-lez p1, :cond_b

    .line 105
    .line 106
    add-int/lit8 p1, p1, -0x1

    .line 107
    .line 108
    goto :goto_7

    .line 109
    :cond_b
    add-int/lit8 p1, p1, 0x1

    .line 110
    .line 111
    :goto_7
    move v1, v7

    .line 112
    goto :goto_6

    .line 113
    :cond_c
    :goto_8
    add-int/lit8 v6, v6, 0x1

    .line 114
    .line 115
    goto :goto_2

    .line 116
    :cond_d
    if-eqz v5, :cond_10

    .line 117
    .line 118
    if-eqz p2, :cond_f

    .line 119
    .line 120
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->hasFocus()Z

    .line 121
    .line 122
    .line 123
    move-result p2

    .line 124
    if-eqz p2, :cond_e

    .line 125
    .line 126
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 127
    .line 128
    or-int/lit8 p2, p2, 0x20

    .line 129
    .line 130
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 131
    .line 132
    invoke-virtual {v5}, Landroid/view/View;->requestFocus()Z

    .line 133
    .line 134
    .line 135
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 136
    .line 137
    and-int/lit8 p2, p2, -0x21

    .line 138
    .line 139
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 140
    .line 141
    :cond_e
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 142
    .line 143
    iput v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 144
    .line 145
    goto :goto_9

    .line 146
    :cond_f
    invoke-virtual {p0, v5, v7}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Z)V

    .line 147
    .line 148
    .line 149
    :cond_10
    :goto_9
    return p1
.end method

.method public final removeAndRecycleAllViews(Landroidx/recyclerview/widget/RecyclerView$Recycler;)V
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    :goto_0
    add-int/lit8 v0, v0, -0x1

    .line 6
    .line 7
    if-ltz v0, :cond_0

    .line 8
    .line 9
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 10
    .line 11
    .line 12
    move-result-object v1

    .line 13
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->removeViewAt(I)V

    .line 14
    .line 15
    .line 16
    invoke-virtual {p1, v1}, Landroidx/recyclerview/widget/RecyclerView$Recycler;->recycleView(Landroid/view/View;)V

    .line 17
    .line 18
    .line 19
    goto :goto_0

    .line 20
    :cond_0
    return-void
.end method

.method public final removeInvisibleViewsAtEnd()V
    .locals 8

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const v1, 0x10040

    .line 4
    .line 5
    .line 6
    and-int/2addr v1, v0

    .line 7
    const/high16 v2, 0x10000

    .line 8
    .line 9
    if-ne v1, v2, :cond_5

    .line 10
    .line 11
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 12
    .line 13
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 14
    .line 15
    const/high16 v3, 0x40000

    .line 16
    .line 17
    and-int/2addr v0, v3

    .line 18
    const/4 v3, 0x0

    .line 19
    if-eqz v0, :cond_0

    .line 20
    .line 21
    move p0, v3

    .line 22
    goto :goto_0

    .line 23
    :cond_0
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 24
    .line 25
    add-int/2addr p0, v3

    .line 26
    :goto_0
    iget v0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 27
    .line 28
    iget v4, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 29
    .line 30
    if-lt v0, v4, :cond_4

    .line 31
    .line 32
    if-le v0, v2, :cond_4

    .line 33
    .line 34
    iget-boolean v4, v1, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 35
    .line 36
    const/4 v5, 0x1

    .line 37
    if-nez v4, :cond_1

    .line 38
    .line 39
    iget-object v4, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 40
    .line 41
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 42
    .line 43
    invoke-virtual {v4, v0}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 44
    .line 45
    .line 46
    move-result v0

    .line 47
    if-lt v0, p0, :cond_2

    .line 48
    .line 49
    goto :goto_1

    .line 50
    :cond_1
    iget-object v4, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 51
    .line 52
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 53
    .line 54
    invoke-virtual {v4, v0}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 55
    .line 56
    .line 57
    move-result v0

    .line 58
    if-gt v0, p0, :cond_2

    .line 59
    .line 60
    :goto_1
    move v0, v5

    .line 61
    goto :goto_2

    .line 62
    :cond_2
    move v0, v3

    .line 63
    :goto_2
    if-eqz v0, :cond_4

    .line 64
    .line 65
    iget-object v0, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 66
    .line 67
    iget v4, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 68
    .line 69
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 70
    .line 71
    iget-object v0, v0, Landroidx/leanback/widget/GridLayoutManager$2;->this$0:Landroidx/leanback/widget/GridLayoutManager;

    .line 72
    .line 73
    iget v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 74
    .line 75
    sub-int/2addr v4, v6

    .line 76
    invoke-virtual {v0, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 77
    .line 78
    .line 79
    move-result-object v4

    .line 80
    iget v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 81
    .line 82
    and-int/lit8 v6, v6, 0x3

    .line 83
    .line 84
    if-ne v6, v5, :cond_3

    .line 85
    .line 86
    iget-object v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 87
    .line 88
    iget-object v7, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mChildHelper:Landroidx/recyclerview/widget/ChildHelper;

    .line 89
    .line 90
    invoke-virtual {v7, v4}, Landroidx/recyclerview/widget/ChildHelper;->indexOfChild(Landroid/view/View;)I

    .line 91
    .line 92
    .line 93
    move-result v7

    .line 94
    invoke-virtual {v0, v6, v7, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->scrapOrRecycleView(Landroidx/recyclerview/widget/RecyclerView$Recycler;ILandroid/view/View;)V

    .line 95
    .line 96
    .line 97
    goto :goto_3

    .line 98
    :cond_3
    iget-object v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 99
    .line 100
    invoke-virtual {v0, v4, v6}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->removeAndRecycleView(Landroid/view/View;Landroidx/recyclerview/widget/RecyclerView$Recycler;)V

    .line 101
    .line 102
    .line 103
    :goto_3
    iget v0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 104
    .line 105
    sub-int/2addr v0, v5

    .line 106
    iput v0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 107
    .line 108
    goto :goto_0

    .line 109
    :cond_4
    iget p0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 110
    .line 111
    iget v0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 112
    .line 113
    if-ge p0, v0, :cond_5

    .line 114
    .line 115
    const/4 p0, -0x1

    .line 116
    iput p0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 117
    .line 118
    iput p0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 119
    .line 120
    :cond_5
    return-void
.end method

.method public final removeInvisibleViewsAtFront()V
    .locals 8

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    const v1, 0x10040

    .line 4
    .line 5
    .line 6
    and-int/2addr v1, v0

    .line 7
    const/high16 v2, 0x10000

    .line 8
    .line 9
    if-ne v1, v2, :cond_5

    .line 10
    .line 11
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 12
    .line 13
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 14
    .line 15
    const/high16 v3, 0x40000

    .line 16
    .line 17
    and-int/2addr v0, v3

    .line 18
    const/4 v3, 0x0

    .line 19
    if-eqz v0, :cond_0

    .line 20
    .line 21
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSizePrimary:I

    .line 22
    .line 23
    add-int/2addr p0, v3

    .line 24
    goto :goto_0

    .line 25
    :cond_0
    move p0, v3

    .line 26
    :goto_0
    iget v0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 27
    .line 28
    iget v4, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 29
    .line 30
    if-lt v0, v4, :cond_4

    .line 31
    .line 32
    if-ge v4, v2, :cond_4

    .line 33
    .line 34
    iget-object v0, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 35
    .line 36
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 37
    .line 38
    invoke-virtual {v0, v4}, Landroidx/leanback/widget/GridLayoutManager$2;->getSize(I)I

    .line 39
    .line 40
    .line 41
    move-result v0

    .line 42
    iget-boolean v4, v1, Landroidx/leanback/widget/Grid;->mReversedFlow:Z

    .line 43
    .line 44
    const/4 v5, 0x1

    .line 45
    if-nez v4, :cond_1

    .line 46
    .line 47
    iget-object v4, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 48
    .line 49
    iget v6, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 50
    .line 51
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 52
    .line 53
    invoke-virtual {v4, v6}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 54
    .line 55
    .line 56
    move-result v4

    .line 57
    add-int/2addr v4, v0

    .line 58
    if-gt v4, p0, :cond_2

    .line 59
    .line 60
    goto :goto_1

    .line 61
    :cond_1
    iget-object v4, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 62
    .line 63
    iget v6, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 64
    .line 65
    check-cast v4, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 66
    .line 67
    invoke-virtual {v4, v6}, Landroidx/leanback/widget/GridLayoutManager$2;->getEdge(I)I

    .line 68
    .line 69
    .line 70
    move-result v4

    .line 71
    sub-int/2addr v4, v0

    .line 72
    if-lt v4, p0, :cond_2

    .line 73
    .line 74
    :goto_1
    move v0, v5

    .line 75
    goto :goto_2

    .line 76
    :cond_2
    move v0, v3

    .line 77
    :goto_2
    if-eqz v0, :cond_4

    .line 78
    .line 79
    iget-object v0, v1, Landroidx/leanback/widget/Grid;->mProvider:Landroidx/leanback/widget/Grid$Provider;

    .line 80
    .line 81
    iget v4, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 82
    .line 83
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$2;

    .line 84
    .line 85
    iget-object v0, v0, Landroidx/leanback/widget/GridLayoutManager$2;->this$0:Landroidx/leanback/widget/GridLayoutManager;

    .line 86
    .line 87
    iget v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 88
    .line 89
    sub-int/2addr v4, v6

    .line 90
    invoke-virtual {v0, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 91
    .line 92
    .line 93
    move-result-object v4

    .line 94
    iget v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 95
    .line 96
    and-int/lit8 v6, v6, 0x3

    .line 97
    .line 98
    if-ne v6, v5, :cond_3

    .line 99
    .line 100
    iget-object v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 101
    .line 102
    iget-object v7, v0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mChildHelper:Landroidx/recyclerview/widget/ChildHelper;

    .line 103
    .line 104
    invoke-virtual {v7, v4}, Landroidx/recyclerview/widget/ChildHelper;->indexOfChild(Landroid/view/View;)I

    .line 105
    .line 106
    .line 107
    move-result v7

    .line 108
    invoke-virtual {v0, v6, v7, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->scrapOrRecycleView(Landroidx/recyclerview/widget/RecyclerView$Recycler;ILandroid/view/View;)V

    .line 109
    .line 110
    .line 111
    goto :goto_3

    .line 112
    :cond_3
    iget-object v6, v0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 113
    .line 114
    invoke-virtual {v0, v4, v6}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->removeAndRecycleView(Landroid/view/View;Landroidx/recyclerview/widget/RecyclerView$Recycler;)V

    .line 115
    .line 116
    .line 117
    :goto_3
    iget v0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 118
    .line 119
    add-int/2addr v0, v5

    .line 120
    iput v0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 121
    .line 122
    goto :goto_0

    .line 123
    :cond_4
    iget p0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 124
    .line 125
    iget v0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 126
    .line 127
    if-ge p0, v0, :cond_5

    .line 128
    .line 129
    const/4 p0, -0x1

    .line 130
    iput p0, v1, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 131
    .line 132
    iput p0, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 133
    .line 134
    :cond_5
    return-void
.end method

.method public final requestChildRectangleOnScreen(Landroidx/recyclerview/widget/RecyclerView;Landroid/view/View;Landroid/graphics/Rect;Z)Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 1

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSaveContextLevel:I

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mRecycler:Landroidx/recyclerview/widget/RecyclerView$Recycler;

    .line 6
    .line 7
    iput-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 8
    .line 9
    const/4 p1, 0x0

    .line 10
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 11
    .line 12
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mExtraLayoutSpaceInPreLayout:I

    .line 13
    .line 14
    :cond_0
    add-int/lit8 v0, v0, 0x1

    .line 15
    .line 16
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSaveContextLevel:I

    .line 17
    .line 18
    return-void
.end method

.method public final scrollDirectionPrimary(I)I
    .locals 6

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    and-int/lit8 v1, v0, 0x40

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    const/4 v3, 0x1

    .line 7
    if-nez v1, :cond_3

    .line 8
    .line 9
    and-int/lit8 v0, v0, 0x3

    .line 10
    .line 11
    if-eq v0, v3, :cond_3

    .line 12
    .line 13
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 14
    .line 15
    if-lez p1, :cond_1

    .line 16
    .line 17
    iget-object v0, v0, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 18
    .line 19
    iget v1, v0, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxEdge:I

    .line 20
    .line 21
    const v4, 0x7fffffff

    .line 22
    .line 23
    .line 24
    if-ne v1, v4, :cond_0

    .line 25
    .line 26
    move v1, v3

    .line 27
    goto :goto_0

    .line 28
    :cond_0
    move v1, v2

    .line 29
    :goto_0
    if-nez v1, :cond_3

    .line 30
    .line 31
    iget v0, v0, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxScroll:I

    .line 32
    .line 33
    if-le p1, v0, :cond_3

    .line 34
    .line 35
    goto :goto_2

    .line 36
    :cond_1
    if-gez p1, :cond_3

    .line 37
    .line 38
    iget-object v0, v0, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 39
    .line 40
    iget v1, v0, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinEdge:I

    .line 41
    .line 42
    const/high16 v4, -0x80000000

    .line 43
    .line 44
    if-ne v1, v4, :cond_2

    .line 45
    .line 46
    move v1, v3

    .line 47
    goto :goto_1

    .line 48
    :cond_2
    move v1, v2

    .line 49
    :goto_1
    if-nez v1, :cond_3

    .line 50
    .line 51
    iget v0, v0, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinScroll:I

    .line 52
    .line 53
    if-ge p1, v0, :cond_3

    .line 54
    .line 55
    :goto_2
    move p1, v0

    .line 56
    :cond_3
    if-nez p1, :cond_4

    .line 57
    .line 58
    return v2

    .line 59
    :cond_4
    neg-int v0, p1

    .line 60
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 61
    .line 62
    .line 63
    move-result v1

    .line 64
    iget v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 65
    .line 66
    if-ne v4, v3, :cond_5

    .line 67
    .line 68
    move v4, v2

    .line 69
    :goto_3
    if-ge v4, v1, :cond_6

    .line 70
    .line 71
    invoke-virtual {p0, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 72
    .line 73
    .line 74
    move-result-object v5

    .line 75
    invoke-virtual {v5, v0}, Landroid/view/View;->offsetTopAndBottom(I)V

    .line 76
    .line 77
    .line 78
    add-int/lit8 v4, v4, 0x1

    .line 79
    .line 80
    goto :goto_3

    .line 81
    :cond_5
    move v4, v2

    .line 82
    :goto_4
    if-ge v4, v1, :cond_6

    .line 83
    .line 84
    invoke-virtual {p0, v4}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 85
    .line 86
    .line 87
    move-result-object v5

    .line 88
    invoke-virtual {v5, v0}, Landroid/view/View;->offsetLeftAndRight(I)V

    .line 89
    .line 90
    .line 91
    add-int/lit8 v4, v4, 0x1

    .line 92
    .line 93
    goto :goto_4

    .line 94
    :cond_6
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 95
    .line 96
    and-int/lit8 v0, v0, 0x3

    .line 97
    .line 98
    if-ne v0, v3, :cond_7

    .line 99
    .line 100
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->updateScrollLimits()V

    .line 101
    .line 102
    .line 103
    return p1

    .line 104
    :cond_7
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 105
    .line 106
    .line 107
    move-result v0

    .line 108
    iget v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 109
    .line 110
    const/high16 v4, 0x40000

    .line 111
    .line 112
    and-int/2addr v1, v4

    .line 113
    if-eqz v1, :cond_8

    .line 114
    .line 115
    if-lez p1, :cond_9

    .line 116
    .line 117
    goto :goto_5

    .line 118
    :cond_8
    if-gez p1, :cond_9

    .line 119
    .line 120
    :goto_5
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->prependVisibleItems()V

    .line 121
    .line 122
    .line 123
    goto :goto_6

    .line 124
    :cond_9
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->appendVisibleItems()V

    .line 125
    .line 126
    .line 127
    :goto_6
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 128
    .line 129
    .line 130
    move-result v1

    .line 131
    if-le v1, v0, :cond_a

    .line 132
    .line 133
    move v0, v3

    .line 134
    goto :goto_7

    .line 135
    :cond_a
    move v0, v2

    .line 136
    :goto_7
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 137
    .line 138
    .line 139
    move-result v1

    .line 140
    iget v5, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 141
    .line 142
    and-int/2addr v4, v5

    .line 143
    if-eqz v4, :cond_b

    .line 144
    .line 145
    if-lez p1, :cond_c

    .line 146
    .line 147
    goto :goto_8

    .line 148
    :cond_b
    if-gez p1, :cond_c

    .line 149
    .line 150
    :goto_8
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->removeInvisibleViewsAtEnd()V

    .line 151
    .line 152
    .line 153
    goto :goto_9

    .line 154
    :cond_c
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->removeInvisibleViewsAtFront()V

    .line 155
    .line 156
    .line 157
    :goto_9
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 158
    .line 159
    .line 160
    move-result v4

    .line 161
    if-ge v4, v1, :cond_d

    .line 162
    .line 163
    goto :goto_a

    .line 164
    :cond_d
    move v3, v2

    .line 165
    :goto_a
    or-int/2addr v0, v3

    .line 166
    if-eqz v0, :cond_f

    .line 167
    .line 168
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 169
    .line 170
    and-int/lit16 v0, v0, -0x401

    .line 171
    .line 172
    invoke-virtual {p0, v2}, Landroidx/leanback/widget/GridLayoutManager;->processRowSizeSecondary(Z)Z

    .line 173
    .line 174
    .line 175
    move-result v1

    .line 176
    const/16 v3, 0x400

    .line 177
    .line 178
    if-eqz v1, :cond_e

    .line 179
    .line 180
    move v2, v3

    .line 181
    :cond_e
    or-int/2addr v0, v2

    .line 182
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 183
    .line 184
    and-int/2addr v0, v3

    .line 185
    if-eqz v0, :cond_f

    .line 186
    .line 187
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 188
    .line 189
    sget-object v1, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 190
    .line 191
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mRequestLayoutRunnable:Landroidx/leanback/widget/GridLayoutManager$1;

    .line 192
    .line 193
    invoke-static {v0, v1}, Landroidx/core/view/ViewCompat$Api16Impl;->postOnAnimation(Landroid/view/View;Ljava/lang/Runnable;)V

    .line 194
    .line 195
    .line 196
    :cond_f
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 197
    .line 198
    invoke-virtual {v0}, Landroid/view/ViewGroup;->invalidate()V

    .line 199
    .line 200
    .line 201
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->updateScrollLimits()V

    .line 202
    .line 203
    .line 204
    return p1
.end method

.method public final scrollDirectionSecondary(I)I
    .locals 4

    .line 1
    const/4 v0, 0x0

    .line 2
    if-nez p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    neg-int v1, p1

    .line 6
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 7
    .line 8
    .line 9
    move-result v2

    .line 10
    iget v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 11
    .line 12
    if-nez v3, :cond_1

    .line 13
    .line 14
    :goto_0
    if-ge v0, v2, :cond_2

    .line 15
    .line 16
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 17
    .line 18
    .line 19
    move-result-object v3

    .line 20
    invoke-virtual {v3, v1}, Landroid/view/View;->offsetTopAndBottom(I)V

    .line 21
    .line 22
    .line 23
    add-int/lit8 v0, v0, 0x1

    .line 24
    .line 25
    goto :goto_0

    .line 26
    :cond_1
    :goto_1
    if-ge v0, v2, :cond_2

    .line 27
    .line 28
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 29
    .line 30
    .line 31
    move-result-object v3

    .line 32
    invoke-virtual {v3, v1}, Landroid/view/View;->offsetLeftAndRight(I)V

    .line 33
    .line 34
    .line 35
    add-int/lit8 v0, v0, 0x1

    .line 36
    .line 37
    goto :goto_1

    .line 38
    :cond_2
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mScrollOffsetSecondary:I

    .line 39
    .line 40
    add-int/2addr v0, p1

    .line 41
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mScrollOffsetSecondary:I

    .line 42
    .line 43
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->updateSecondaryScrollLimits()V

    .line 44
    .line 45
    .line 46
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 47
    .line 48
    invoke-virtual {p0}, Landroid/view/ViewGroup;->invalidate()V

    .line 49
    .line 50
    .line 51
    return p1
.end method

.method public final scrollHorizontallyBy(ILandroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I
    .locals 2

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    and-int/lit16 v0, v0, 0x200

    .line 4
    .line 5
    const/4 v1, 0x0

    .line 6
    if-eqz v0, :cond_3

    .line 7
    .line 8
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 9
    .line 10
    if-eqz v0, :cond_0

    .line 11
    .line 12
    const/4 v0, 0x1

    .line 13
    goto :goto_0

    .line 14
    :cond_0
    move v0, v1

    .line 15
    :goto_0
    if-nez v0, :cond_1

    .line 16
    .line 17
    goto :goto_2

    .line 18
    :cond_1
    invoke-virtual {p0, p2, p3}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 19
    .line 20
    .line 21
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 22
    .line 23
    and-int/lit8 p2, p2, -0x4

    .line 24
    .line 25
    or-int/lit8 p2, p2, 0x2

    .line 26
    .line 27
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 28
    .line 29
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 30
    .line 31
    if-nez p2, :cond_2

    .line 32
    .line 33
    invoke-virtual {p0, p1}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionPrimary(I)I

    .line 34
    .line 35
    .line 36
    move-result p1

    .line 37
    goto :goto_1

    .line 38
    :cond_2
    invoke-virtual {p0, p1}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionSecondary(I)I

    .line 39
    .line 40
    .line 41
    move-result p1

    .line 42
    :goto_1
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 43
    .line 44
    .line 45
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 46
    .line 47
    and-int/lit8 p2, p2, -0x4

    .line 48
    .line 49
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 50
    .line 51
    return p1

    .line 52
    :cond_3
    :goto_2
    return v1
.end method

.method public final scrollToPosition(I)V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-virtual {p0, p1, v0}, Landroidx/leanback/widget/GridLayoutManager;->setSelection$1(IZ)V

    .line 3
    .line 4
    .line 5
    return-void
.end method

.method public final scrollToSelection(IZ)V
    .locals 7

    .line 1
    const/4 v0, 0x0

    .line 2
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mPrimaryScrollExtra:I

    .line 3
    .line 4
    invoke-virtual {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 5
    .line 6
    .line 7
    move-result-object v1

    .line 8
    iget-object v2, p0, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->mSmoothScroller:Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;

    .line 9
    .line 10
    const/4 v3, 0x1

    .line 11
    if-eqz v2, :cond_0

    .line 12
    .line 13
    iget-boolean v2, v2, Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;->mRunning:Z

    .line 14
    .line 15
    if-eqz v2, :cond_0

    .line 16
    .line 17
    move v2, v3

    .line 18
    goto :goto_0

    .line 19
    :cond_0
    move v2, v0

    .line 20
    :goto_0
    xor-int/2addr v2, v3

    .line 21
    if-eqz v2, :cond_1

    .line 22
    .line 23
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 24
    .line 25
    invoke-virtual {v4}, Landroid/view/ViewGroup;->isLayoutRequested()Z

    .line 26
    .line 27
    .line 28
    move-result v4

    .line 29
    if-nez v4, :cond_1

    .line 30
    .line 31
    if-eqz v1, :cond_1

    .line 32
    .line 33
    invoke-static {v1}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 34
    .line 35
    .line 36
    move-result v4

    .line 37
    if-ne v4, p1, :cond_1

    .line 38
    .line 39
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 40
    .line 41
    or-int/lit8 p1, p1, 0x20

    .line 42
    .line 43
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 44
    .line 45
    invoke-virtual {p0, v1, p2}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Z)V

    .line 46
    .line 47
    .line 48
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 49
    .line 50
    and-int/lit8 p1, p1, -0x21

    .line 51
    .line 52
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 53
    .line 54
    goto/16 :goto_2

    .line 55
    .line 56
    :cond_1
    iget v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 57
    .line 58
    and-int/lit16 v5, v4, 0x200

    .line 59
    .line 60
    const/high16 v6, -0x80000000

    .line 61
    .line 62
    if-eqz v5, :cond_a

    .line 63
    .line 64
    and-int/lit8 v4, v4, 0x40

    .line 65
    .line 66
    if-eqz v4, :cond_2

    .line 67
    .line 68
    goto/16 :goto_3

    .line 69
    .line 70
    :cond_2
    if-eqz p2, :cond_5

    .line 71
    .line 72
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 73
    .line 74
    invoke-virtual {v4}, Landroid/view/ViewGroup;->isLayoutRequested()Z

    .line 75
    .line 76
    .line 77
    move-result v4

    .line 78
    if-nez v4, :cond_5

    .line 79
    .line 80
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 81
    .line 82
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 83
    .line 84
    iput v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 85
    .line 86
    iget-object p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 87
    .line 88
    if-eqz p2, :cond_3

    .line 89
    .line 90
    goto :goto_1

    .line 91
    :cond_3
    move v3, v0

    .line 92
    :goto_1
    if-nez v3, :cond_4

    .line 93
    .line 94
    new-instance p1, Ljava/lang/StringBuilder;

    .line 95
    .line 96
    const-string p2, "GridLayoutManager:"

    .line 97
    .line 98
    invoke-direct {p1, p2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 99
    .line 100
    .line 101
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 102
    .line 103
    invoke-virtual {p0}, Landroid/view/ViewGroup;->getId()I

    .line 104
    .line 105
    .line 106
    move-result p0

    .line 107
    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 108
    .line 109
    .line 110
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 111
    .line 112
    .line 113
    move-result-object p0

    .line 114
    const-string/jumbo p1, "setSelectionSmooth should not be called before first layout pass"

    .line 115
    .line 116
    .line 117
    invoke-static {p0, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 118
    .line 119
    .line 120
    return-void

    .line 121
    :cond_4
    new-instance p2, Landroidx/leanback/widget/GridLayoutManager$4;

    .line 122
    .line 123
    invoke-direct {p2, p0}, Landroidx/leanback/widget/GridLayoutManager$4;-><init>(Landroidx/leanback/widget/GridLayoutManager;)V

    .line 124
    .line 125
    .line 126
    iput p1, p2, Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;->mTargetPosition:I

    .line 127
    .line 128
    invoke-virtual {p0, p2}, Landroidx/leanback/widget/GridLayoutManager;->startSmoothScroll(Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;)V

    .line 129
    .line 130
    .line 131
    iget p1, p2, Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;->mTargetPosition:I

    .line 132
    .line 133
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 134
    .line 135
    if-eq p1, p2, :cond_9

    .line 136
    .line 137
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 138
    .line 139
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 140
    .line 141
    goto :goto_2

    .line 142
    :cond_5
    if-nez v2, :cond_7

    .line 143
    .line 144
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mCurrentSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 145
    .line 146
    if-eqz v2, :cond_6

    .line 147
    .line 148
    iput-boolean v3, v2, Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;->mSkipOnStopInternal:Z

    .line 149
    .line 150
    :cond_6
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 151
    .line 152
    invoke-virtual {v2}, Landroidx/recyclerview/widget/RecyclerView;->stopScroll()V

    .line 153
    .line 154
    .line 155
    :cond_7
    iget-object v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 156
    .line 157
    invoke-virtual {v2}, Landroid/view/ViewGroup;->isLayoutRequested()Z

    .line 158
    .line 159
    .line 160
    move-result v2

    .line 161
    if-nez v2, :cond_8

    .line 162
    .line 163
    if-eqz v1, :cond_8

    .line 164
    .line 165
    invoke-static {v1}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    .line 166
    .line 167
    .line 168
    move-result v2

    .line 169
    if-ne v2, p1, :cond_8

    .line 170
    .line 171
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 172
    .line 173
    or-int/lit8 p1, p1, 0x20

    .line 174
    .line 175
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 176
    .line 177
    invoke-virtual {p0, v1, p2}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Z)V

    .line 178
    .line 179
    .line 180
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 181
    .line 182
    and-int/lit8 p1, p1, -0x21

    .line 183
    .line 184
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 185
    .line 186
    goto :goto_2

    .line 187
    :cond_8
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 188
    .line 189
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 190
    .line 191
    iput v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 192
    .line 193
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 194
    .line 195
    or-int/lit16 p1, p1, 0x100

    .line 196
    .line 197
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 198
    .line 199
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->requestLayout()V

    .line 200
    .line 201
    .line 202
    :cond_9
    :goto_2
    return-void

    .line 203
    :cond_a
    :goto_3
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 204
    .line 205
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 206
    .line 207
    iput v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 208
    .line 209
    return-void
.end method

.method public final scrollToView(Landroid/view/View;Landroid/view/View;ZII)V
    .locals 6

    .line 3
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    and-int/lit8 v0, v0, 0x40

    if-eqz v0, :cond_0

    return-void

    .line 4
    :cond_0
    invoke-static {p1}, Landroidx/leanback/widget/GridLayoutManager;->getAdapterPositionByView(Landroid/view/View;)I

    move-result v0

    .line 5
    invoke-static {p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->getSubPositionByView(Landroid/view/View;Landroid/view/View;)I

    move-result v1

    .line 6
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    const/4 v3, 0x0

    const/4 v4, 0x1

    if-ne v0, v2, :cond_1

    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    if-eq v1, v2, :cond_3

    .line 7
    :cond_1
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 8
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 9
    iput v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPositionOffset:I

    .line 10
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    and-int/lit8 v0, v0, 0x3

    if-eq v0, v4, :cond_2

    .line 11
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->dispatchChildSelected()V

    .line 12
    :cond_2
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    invoke-virtual {v0}, Landroidx/leanback/widget/BaseGridView;->isChildrenDrawingOrderEnabledInternal()Z

    move-result v0

    if-eqz v0, :cond_3

    .line 13
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->invalidate()V

    :cond_3
    if-nez p1, :cond_4

    return-void

    .line 14
    :cond_4
    invoke-virtual {p1}, Landroid/view/View;->hasFocus()Z

    move-result v0

    if-nez v0, :cond_5

    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->hasFocus()Z

    move-result v0

    if-eqz v0, :cond_5

    .line 15
    invoke-virtual {p1}, Landroid/view/View;->requestFocus()Z

    .line 16
    :cond_5
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    const/high16 v1, 0x20000

    and-int/2addr v0, v1

    if-nez v0, :cond_6

    if-eqz p3, :cond_6

    return-void

    .line 17
    :cond_6
    sget-object v0, Landroidx/leanback/widget/GridLayoutManager;->sTwoInts:[I

    invoke-virtual {p0, p1, p2, v0}, Landroidx/leanback/widget/GridLayoutManager;->getScrollPosition(Landroid/view/View;Landroid/view/View;[I)Z

    move-result p1

    if-nez p1, :cond_7

    if-nez p4, :cond_7

    if-eqz p5, :cond_b

    .line 18
    :cond_7
    aget p1, v0, v3

    add-int/2addr p1, p4

    aget p2, v0, v4

    add-int/2addr p2, p5

    .line 19
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    and-int/lit8 p4, p4, 0x3

    if-ne p4, v4, :cond_8

    .line 20
    invoke-virtual {p0, p1}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionPrimary(I)I

    .line 21
    invoke-virtual {p0, p2}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionSecondary(I)I

    goto :goto_1

    .line 22
    :cond_8
    iget p4, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    if-nez p4, :cond_9

    goto :goto_0

    :cond_9
    move v5, p2

    move p2, p1

    move p1, v5

    :goto_0
    if-eqz p3, :cond_a

    .line 23
    iget-object p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    .line 24
    invoke-virtual {p0, p1, p2, v3}, Landroidx/recyclerview/widget/RecyclerView;->smoothScrollBy(IIZ)V

    goto :goto_1

    .line 25
    :cond_a
    iget-object p3, p0, Landroidx/leanback/widget/GridLayoutManager;->mBaseGridView:Landroidx/leanback/widget/BaseGridView;

    invoke-virtual {p3, p1, p2}, Landroidx/recyclerview/widget/RecyclerView;->scrollBy(II)V

    .line 26
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->dispatchChildSelectedAndPositioned()V

    :cond_b
    :goto_1
    return-void
.end method

.method public final scrollToView(Landroid/view/View;Z)V
    .locals 6

    .line 1
    invoke-virtual {p1}, Landroid/view/View;->findFocus()Landroid/view/View;

    move-result-object v2

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, p0

    move-object v1, p1

    move v3, p2

    .line 2
    invoke-virtual/range {v0 .. v5}, Landroidx/leanback/widget/GridLayoutManager;->scrollToView(Landroid/view/View;Landroid/view/View;ZII)V

    return-void
.end method

.method public final scrollVerticallyBy(ILandroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)I
    .locals 4

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 2
    .line 3
    and-int/lit16 v1, v0, 0x200

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    if-eqz v1, :cond_3

    .line 7
    .line 8
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 9
    .line 10
    const/4 v3, 0x1

    .line 11
    if-eqz v1, :cond_0

    .line 12
    .line 13
    move v1, v3

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move v1, v2

    .line 16
    :goto_0
    if-nez v1, :cond_1

    .line 17
    .line 18
    goto :goto_2

    .line 19
    :cond_1
    and-int/lit8 v0, v0, -0x4

    .line 20
    .line 21
    or-int/lit8 v0, v0, 0x2

    .line 22
    .line 23
    iput v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 24
    .line 25
    invoke-virtual {p0, p2, p3}, Landroidx/leanback/widget/GridLayoutManager;->saveContext(Landroidx/recyclerview/widget/RecyclerView$Recycler;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 26
    .line 27
    .line 28
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 29
    .line 30
    if-ne p2, v3, :cond_2

    .line 31
    .line 32
    invoke-virtual {p0, p1}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionPrimary(I)I

    .line 33
    .line 34
    .line 35
    move-result p1

    .line 36
    goto :goto_1

    .line 37
    :cond_2
    invoke-virtual {p0, p1}, Landroidx/leanback/widget/GridLayoutManager;->scrollDirectionSecondary(I)I

    .line 38
    .line 39
    .line 40
    move-result p1

    .line 41
    :goto_1
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->leaveContext()V

    .line 42
    .line 43
    .line 44
    iget p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 45
    .line 46
    and-int/lit8 p2, p2, -0x4

    .line 47
    .line 48
    iput p2, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 49
    .line 50
    return p1

    .line 51
    :cond_3
    :goto_2
    return v2
.end method

.method public final setOrientation(I)V
    .locals 3

    .line 1
    if-eqz p1, :cond_0

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    if-eq p1, v0, :cond_0

    .line 5
    .line 6
    return-void

    .line 7
    :cond_0
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 8
    .line 9
    invoke-static {p0, p1}, Landroidx/recyclerview/widget/OrientationHelper;->createOrientationHelper(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;I)Landroidx/recyclerview/widget/OrientationHelper;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    iput-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientationHelper:Landroidx/recyclerview/widget/OrientationHelper;

    .line 14
    .line 15
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 16
    .line 17
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 18
    .line 19
    .line 20
    iget-object v1, v0, Landroidx/leanback/widget/WindowAlignment;->horizontal:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 21
    .line 22
    iget-object v2, v0, Landroidx/leanback/widget/WindowAlignment;->vertical:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 23
    .line 24
    if-nez p1, :cond_1

    .line 25
    .line 26
    iput-object v1, v0, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 27
    .line 28
    iput-object v2, v0, Landroidx/leanback/widget/WindowAlignment;->mSecondAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 29
    .line 30
    goto :goto_0

    .line 31
    :cond_1
    iput-object v2, v0, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 32
    .line 33
    iput-object v1, v0, Landroidx/leanback/widget/WindowAlignment;->mSecondAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 34
    .line 35
    :goto_0
    iget-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mItemAlignment:Landroidx/leanback/widget/ItemAlignment;

    .line 36
    .line 37
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 38
    .line 39
    .line 40
    iget p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 41
    .line 42
    or-int/lit16 p1, p1, 0x100

    .line 43
    .line 44
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 45
    .line 46
    return-void
.end method

.method public final setRowHeight(I)V
    .locals 1

    .line 1
    if-gez p1, :cond_1

    .line 2
    .line 3
    const/4 v0, -0x2

    .line 4
    if-ne p1, v0, :cond_0

    .line 5
    .line 6
    goto :goto_0

    .line 7
    :cond_0
    new-instance p0, Ljava/lang/IllegalArgumentException;

    .line 8
    .line 9
    const-string v0, "Invalid row height: "

    .line 10
    .line 11
    invoke-static {v0, p1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;I)Ljava/lang/String;

    .line 12
    .line 13
    .line 14
    move-result-object p1

    .line 15
    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    .line 16
    .line 17
    .line 18
    throw p0

    .line 19
    :cond_1
    :goto_0
    iput p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mRowSizeSecondaryRequested:I

    .line 20
    .line 21
    return-void
.end method

.method public final setSelection$1(IZ)V
    .locals 1

    .line 1
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFocusPosition:I

    .line 2
    .line 3
    if-eq v0, p1, :cond_0

    .line 4
    .line 5
    const/4 v0, -0x1

    .line 6
    if-ne p1, v0, :cond_1

    .line 7
    .line 8
    :cond_0
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mSubFocusPosition:I

    .line 9
    .line 10
    if-nez v0, :cond_1

    .line 11
    .line 12
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mPrimaryScrollExtra:I

    .line 13
    .line 14
    if-eqz v0, :cond_2

    .line 15
    .line 16
    :cond_1
    invoke-virtual {p0, p1, p2}, Landroidx/leanback/widget/GridLayoutManager;->scrollToSelection(IZ)V

    .line 17
    .line 18
    .line 19
    :cond_2
    return-void
.end method

.method public final smoothScrollToPosition(Landroidx/recyclerview/widget/RecyclerView;I)V
    .locals 0

    .line 1
    const/4 p1, 0x1

    .line 2
    invoke-virtual {p0, p2, p1}, Landroidx/leanback/widget/GridLayoutManager;->setSelection$1(IZ)V

    .line 3
    .line 4
    .line 5
    return-void
.end method

.method public final startSmoothScroll(Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;)V
    .locals 2

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mCurrentSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    const/4 v1, 0x1

    .line 6
    iput-boolean v1, v0, Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;->mSkipOnStopInternal:Z

    .line 7
    .line 8
    :cond_0
    invoke-super {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->startSmoothScroll(Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;)V

    .line 9
    .line 10
    .line 11
    iget-boolean v0, p1, Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;->mRunning:Z

    .line 12
    .line 13
    const/4 v1, 0x0

    .line 14
    if-eqz v0, :cond_2

    .line 15
    .line 16
    instance-of v0, p1, Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 17
    .line 18
    if-eqz v0, :cond_2

    .line 19
    .line 20
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 21
    .line 22
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mCurrentSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 23
    .line 24
    instance-of v0, p1, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 25
    .line 26
    if-eqz v0, :cond_1

    .line 27
    .line 28
    check-cast p1, Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 29
    .line 30
    iput-object p1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 31
    .line 32
    goto :goto_0

    .line 33
    :cond_1
    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 34
    .line 35
    goto :goto_0

    .line 36
    :cond_2
    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mCurrentSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$GridLinearSmoothScroller;

    .line 37
    .line 38
    iput-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPendingMoveSmoothScroller:Landroidx/leanback/widget/GridLayoutManager$PendingMoveSmoothScroller;

    .line 39
    .line 40
    :goto_0
    return-void
.end method

.method public final updatePositionDeltaInPreLayout()V
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    if-lez v0, :cond_0

    .line 7
    .line 8
    invoke-virtual {p0, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->getChildAt(I)Landroid/view/View;

    .line 9
    .line 10
    .line 11
    move-result-object v0

    .line 12
    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 13
    .line 14
    .line 15
    move-result-object v0

    .line 16
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 17
    .line 18
    iget-object v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 19
    .line 20
    iget v1, v1, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 21
    .line 22
    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->getViewLayoutPosition()I

    .line 23
    .line 24
    .line 25
    move-result v0

    .line 26
    sub-int/2addr v1, v0

    .line 27
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 28
    .line 29
    goto :goto_0

    .line 30
    :cond_0
    iput v1, p0, Landroidx/leanback/widget/GridLayoutManager;->mPositionDeltaInPreLayout:I

    .line 31
    .line 32
    :goto_0
    return-void
.end method

.method public final updateScrollLimits()V
    .locals 12

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    return-void

    .line 10
    :cond_0
    iget v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mFlag:I

    .line 11
    .line 12
    const/high16 v1, 0x40000

    .line 13
    .line 14
    and-int/2addr v0, v1

    .line 15
    const/4 v1, 0x1

    .line 16
    const/4 v2, 0x0

    .line 17
    if-nez v0, :cond_1

    .line 18
    .line 19
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 20
    .line 21
    iget v0, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 22
    .line 23
    iget-object v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 24
    .line 25
    invoke-virtual {v3}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 26
    .line 27
    .line 28
    move-result v3

    .line 29
    sub-int/2addr v3, v1

    .line 30
    iget-object v4, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 31
    .line 32
    iget v4, v4, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 33
    .line 34
    move v5, v4

    .line 35
    move v4, v3

    .line 36
    move v3, v2

    .line 37
    goto :goto_0

    .line 38
    :cond_1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 39
    .line 40
    iget v3, v0, Landroidx/leanback/widget/Grid;->mFirstVisibleIndex:I

    .line 41
    .line 42
    iget v4, v0, Landroidx/leanback/widget/Grid;->mLastVisibleIndex:I

    .line 43
    .line 44
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mState:Landroidx/recyclerview/widget/RecyclerView$State;

    .line 45
    .line 46
    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    .line 47
    .line 48
    .line 49
    move-result v0

    .line 50
    sub-int/2addr v0, v1

    .line 51
    move v5, v4

    .line 52
    move v4, v2

    .line 53
    move v11, v3

    .line 54
    move v3, v0

    .line 55
    move v0, v11

    .line 56
    :goto_0
    if-ltz v0, :cond_d

    .line 57
    .line 58
    if-gez v5, :cond_2

    .line 59
    .line 60
    goto/16 :goto_9

    .line 61
    .line 62
    :cond_2
    if-ne v0, v4, :cond_3

    .line 63
    .line 64
    move v0, v1

    .line 65
    goto :goto_1

    .line 66
    :cond_3
    move v0, v2

    .line 67
    :goto_1
    if-ne v5, v3, :cond_4

    .line 68
    .line 69
    move v3, v1

    .line 70
    goto :goto_2

    .line 71
    :cond_4
    move v3, v2

    .line 72
    :goto_2
    const/high16 v4, -0x80000000

    .line 73
    .line 74
    const v5, 0x7fffffff

    .line 75
    .line 76
    .line 77
    iget-object v6, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 78
    .line 79
    if-nez v0, :cond_7

    .line 80
    .line 81
    iget-object v7, v6, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 82
    .line 83
    iget v8, v7, Landroidx/leanback/widget/WindowAlignment$Axis;->mMaxEdge:I

    .line 84
    .line 85
    if-ne v8, v5, :cond_5

    .line 86
    .line 87
    move v8, v1

    .line 88
    goto :goto_3

    .line 89
    :cond_5
    move v8, v2

    .line 90
    :goto_3
    if-eqz v8, :cond_7

    .line 91
    .line 92
    if-nez v3, :cond_7

    .line 93
    .line 94
    iget v7, v7, Landroidx/leanback/widget/WindowAlignment$Axis;->mMinEdge:I

    .line 95
    .line 96
    if-ne v7, v4, :cond_6

    .line 97
    .line 98
    move v7, v1

    .line 99
    goto :goto_4

    .line 100
    :cond_6
    move v7, v2

    .line 101
    :goto_4
    if-eqz v7, :cond_7

    .line 102
    .line 103
    return-void

    .line 104
    :cond_7
    sget-object v7, Landroidx/leanback/widget/GridLayoutManager;->sTwoInts:[I

    .line 105
    .line 106
    if-eqz v0, :cond_a

    .line 107
    .line 108
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 109
    .line 110
    invoke-virtual {v0, v1, v7}, Landroidx/leanback/widget/Grid;->findRowMax(Z[I)I

    .line 111
    .line 112
    .line 113
    move-result v5

    .line 114
    aget v0, v7, v1

    .line 115
    .line 116
    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 117
    .line 118
    .line 119
    move-result-object v0

    .line 120
    iget v8, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 121
    .line 122
    if-nez v8, :cond_8

    .line 123
    .line 124
    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 125
    .line 126
    .line 127
    move-result-object v8

    .line 128
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 129
    .line 130
    invoke-virtual {v8}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 131
    .line 132
    .line 133
    invoke-virtual {v0}, Landroid/view/View;->getLeft()I

    .line 134
    .line 135
    .line 136
    move-result v9

    .line 137
    iget v10, v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 138
    .line 139
    add-int/2addr v9, v10

    .line 140
    iget v8, v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 141
    .line 142
    goto :goto_5

    .line 143
    :cond_8
    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 144
    .line 145
    .line 146
    move-result-object v8

    .line 147
    check-cast v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 148
    .line 149
    invoke-virtual {v8}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 150
    .line 151
    .line 152
    invoke-virtual {v0}, Landroid/view/View;->getTop()I

    .line 153
    .line 154
    .line 155
    move-result v9

    .line 156
    iget v10, v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 157
    .line 158
    add-int/2addr v9, v10

    .line 159
    iget v8, v8, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 160
    .line 161
    :goto_5
    add-int/2addr v8, v9

    .line 162
    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 163
    .line 164
    .line 165
    move-result-object v0

    .line 166
    check-cast v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 167
    .line 168
    iget-object v0, v0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignMultiple:[I

    .line 169
    .line 170
    if-eqz v0, :cond_9

    .line 171
    .line 172
    array-length v9, v0

    .line 173
    if-lez v9, :cond_9

    .line 174
    .line 175
    array-length v9, v0

    .line 176
    sub-int/2addr v9, v1

    .line 177
    aget v9, v0, v9

    .line 178
    .line 179
    aget v0, v0, v2

    .line 180
    .line 181
    sub-int/2addr v9, v0

    .line 182
    add-int v0, v9, v8

    .line 183
    .line 184
    goto :goto_6

    .line 185
    :cond_9
    move v0, v8

    .line 186
    goto :goto_6

    .line 187
    :cond_a
    move v0, v5

    .line 188
    :goto_6
    if-eqz v3, :cond_c

    .line 189
    .line 190
    iget-object v3, p0, Landroidx/leanback/widget/GridLayoutManager;->mGrid:Landroidx/leanback/widget/Grid;

    .line 191
    .line 192
    invoke-virtual {v3, v2, v7}, Landroidx/leanback/widget/Grid;->findRowMin(Z[I)I

    .line 193
    .line 194
    .line 195
    move-result v4

    .line 196
    aget v1, v7, v1

    .line 197
    .line 198
    invoke-virtual {p0, v1}, Landroidx/recyclerview/widget/RecyclerView$LayoutManager;->findViewByPosition(I)Landroid/view/View;

    .line 199
    .line 200
    .line 201
    move-result-object v1

    .line 202
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager;->mOrientation:I

    .line 203
    .line 204
    if-nez p0, :cond_b

    .line 205
    .line 206
    invoke-virtual {v1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 207
    .line 208
    .line 209
    move-result-object p0

    .line 210
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 211
    .line 212
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 213
    .line 214
    .line 215
    invoke-virtual {v1}, Landroid/view/View;->getLeft()I

    .line 216
    .line 217
    .line 218
    move-result v1

    .line 219
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mLeftInset:I

    .line 220
    .line 221
    add-int/2addr v1, v2

    .line 222
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignX:I

    .line 223
    .line 224
    goto :goto_7

    .line 225
    :cond_b
    invoke-virtual {v1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 226
    .line 227
    .line 228
    move-result-object p0

    .line 229
    check-cast p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;

    .line 230
    .line 231
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 232
    .line 233
    .line 234
    invoke-virtual {v1}, Landroid/view/View;->getTop()I

    .line 235
    .line 236
    .line 237
    move-result v1

    .line 238
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mTopInset:I

    .line 239
    .line 240
    add-int/2addr v1, v2

    .line 241
    iget p0, p0, Landroidx/leanback/widget/GridLayoutManager$LayoutParams;->mAlignY:I

    .line 242
    .line 243
    :goto_7
    add-int/2addr p0, v1

    .line 244
    goto :goto_8

    .line 245
    :cond_c
    move p0, v4

    .line 246
    :goto_8
    iget-object v1, v6, Landroidx/leanback/widget/WindowAlignment;->mMainAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 247
    .line 248
    invoke-virtual {v1, v4, v5, p0, v0}, Landroidx/leanback/widget/WindowAlignment$Axis;->updateMinMax(IIII)V

    .line 249
    .line 250
    .line 251
    :cond_d
    :goto_9
    return-void
.end method

.method public final updateSecondaryScrollLimits()V
    .locals 3

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/GridLayoutManager;->mWindowAlignment:Landroidx/leanback/widget/WindowAlignment;

    .line 2
    .line 3
    iget-object v0, v0, Landroidx/leanback/widget/WindowAlignment;->mSecondAxis:Landroidx/leanback/widget/WindowAlignment$Axis;

    .line 4
    .line 5
    iget v1, v0, Landroidx/leanback/widget/WindowAlignment$Axis;->mPaddingMin:I

    .line 6
    .line 7
    iget v2, p0, Landroidx/leanback/widget/GridLayoutManager;->mScrollOffsetSecondary:I

    .line 8
    .line 9
    sub-int/2addr v1, v2

    .line 10
    invoke-virtual {p0}, Landroidx/leanback/widget/GridLayoutManager;->getSizeSecondary()I

    .line 11
    .line 12
    .line 13
    move-result p0

    .line 14
    add-int/2addr p0, v1

    .line 15
    invoke-virtual {v0, v1, p0, v1, p0}, Landroidx/leanback/widget/WindowAlignment$Axis;->updateMinMax(IIII)V

    .line 16
    .line 17
    .line 18
    return-void
.end method

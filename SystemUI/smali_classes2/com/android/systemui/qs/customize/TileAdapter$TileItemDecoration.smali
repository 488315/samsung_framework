.class public final Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;
.super Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mDrawable:Landroid/graphics/drawable/Drawable;

.field public final synthetic this$0:Lcom/android/systemui/qs/customize/TileAdapter;


# direct methods
.method private constructor <init>(Lcom/android/systemui/qs/customize/TileAdapter;Landroid/content/Context;)V
    .locals 0

    .line 2
    iput-object p1, p0, Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;->this$0:Lcom/android/systemui/qs/customize/TileAdapter;

    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;-><init>()V

    const p1, 0x7f080d71

    .line 3
    invoke-virtual {p2, p1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    iput-object p1, p0, Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;->mDrawable:Landroid/graphics/drawable/Drawable;

    return-void
.end method

.method public synthetic constructor <init>(Lcom/android/systemui/qs/customize/TileAdapter;Landroid/content/Context;I)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2}, Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;-><init>(Lcom/android/systemui/qs/customize/TileAdapter;Landroid/content/Context;)V

    return-void
.end method


# virtual methods
.method public final onDraw(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;)V
    .locals 9

    .line 1
    invoke-virtual {p2}, Landroid/view/ViewGroup;->getChildCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    invoke-virtual {p2}, Landroid/view/ViewGroup;->getWidth()I

    .line 6
    .line 7
    .line 8
    move-result v1

    .line 9
    invoke-virtual {p2}, Landroid/view/ViewGroup;->getBottom()I

    .line 10
    .line 11
    .line 12
    move-result v2

    .line 13
    const/4 v3, 0x0

    .line 14
    move v4, v3

    .line 15
    :goto_0
    if-ge v4, v0, :cond_3

    .line 16
    .line 17
    invoke-virtual {p2, v4}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    .line 18
    .line 19
    .line 20
    move-result-object v5

    .line 21
    invoke-virtual {p2, v5}, Landroidx/recyclerview/widget/RecyclerView;->getChildViewHolder(Landroid/view/View;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;

    .line 22
    .line 23
    .line 24
    move-result-object v6

    .line 25
    iget-object v7, p0, Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;->this$0:Lcom/android/systemui/qs/customize/TileAdapter;

    .line 26
    .line 27
    iget-object v8, v7, Lcom/android/systemui/qs/customize/TileAdapter;->mCurrentDrag:Lcom/android/systemui/qs/customize/TileAdapter$Holder;

    .line 28
    .line 29
    if-ne v6, v8, :cond_0

    .line 30
    .line 31
    goto :goto_1

    .line 32
    :cond_0
    invoke-virtual {v6}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getBindingAdapterPosition()I

    .line 33
    .line 34
    .line 35
    move-result v8

    .line 36
    if-eqz v8, :cond_2

    .line 37
    .line 38
    invoke-virtual {v6}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->getBindingAdapterPosition()I

    .line 39
    .line 40
    .line 41
    move-result v6

    .line 42
    iget v7, v7, Lcom/android/systemui/qs/customize/TileAdapter;->mEditIndex:I

    .line 43
    .line 44
    if-ge v6, v7, :cond_1

    .line 45
    .line 46
    instance-of v6, v5, Landroid/widget/TextView;

    .line 47
    .line 48
    if-nez v6, :cond_1

    .line 49
    .line 50
    goto :goto_1

    .line 51
    :cond_1
    invoke-virtual {v5}, Landroid/view/View;->getTop()I

    .line 52
    .line 53
    .line 54
    move-result p2

    .line 55
    sget-object v0, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    .line 56
    .line 57
    invoke-virtual {v5}, Landroid/view/View;->getTranslationY()F

    .line 58
    .line 59
    .line 60
    move-result v0

    .line 61
    invoke-static {v0}, Ljava/lang/Math;->round(F)I

    .line 62
    .line 63
    .line 64
    move-result v0

    .line 65
    add-int/2addr v0, p2

    .line 66
    iget-object p0, p0, Lcom/android/systemui/qs/customize/TileAdapter$TileItemDecoration;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 67
    .line 68
    invoke-virtual {p0, v3, v0, v1, v2}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 69
    .line 70
    .line 71
    invoke-virtual {p0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 72
    .line 73
    .line 74
    goto :goto_2

    .line 75
    :cond_2
    :goto_1
    add-int/lit8 v4, v4, 0x1

    .line 76
    .line 77
    goto :goto_0

    .line 78
    :cond_3
    :goto_2
    return-void
.end method
.class public final Landroidx/slice/widget/GridRowView$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnPreDrawListener;


# instance fields
.field public final synthetic this$0:Landroidx/slice/widget/GridRowView;


# direct methods
.method public constructor <init>(Landroidx/slice/widget/GridRowView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/slice/widget/GridRowView$2;->this$0:Landroidx/slice/widget/GridRowView;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onPreDraw()Z
    .locals 2

    .line 1
    iget-object v0, p0, Landroidx/slice/widget/GridRowView$2;->this$0:Landroidx/slice/widget/GridRowView;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroidx/slice/widget/GridRowView;->getMaxCells()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    iput v1, v0, Landroidx/slice/widget/GridRowView;->mMaxCells:I

    .line 8
    .line 9
    iget-object v0, p0, Landroidx/slice/widget/GridRowView$2;->this$0:Landroidx/slice/widget/GridRowView;

    .line 10
    .line 11
    invoke-virtual {v0}, Landroidx/slice/widget/GridRowView;->populateViews()V

    .line 12
    .line 13
    .line 14
    iget-object v0, p0, Landroidx/slice/widget/GridRowView$2;->this$0:Landroidx/slice/widget/GridRowView;

    .line 15
    .line 16
    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    .line 17
    .line 18
    .line 19
    move-result-object v0

    .line 20
    invoke-virtual {v0, p0}, Landroid/view/ViewTreeObserver;->removeOnPreDrawListener(Landroid/view/ViewTreeObserver$OnPreDrawListener;)V

    .line 21
    .line 22
    .line 23
    iget-object p0, p0, Landroidx/slice/widget/GridRowView$2;->this$0:Landroidx/slice/widget/GridRowView;

    .line 24
    .line 25
    const/4 v0, 0x0

    .line 26
    iput-boolean v0, p0, Landroidx/slice/widget/GridRowView;->mMaxCellUpdateScheduled:Z

    .line 27
    .line 28
    const/4 p0, 0x1

    .line 29
    return p0
.end method

.class public final Lcom/android/systemui/ExpandHelper$2;
.super Landroid/view/ScaleGestureDetector$SimpleOnScaleGestureListener;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/ExpandHelper;


# direct methods
.method public constructor <init>(Lcom/android/systemui/ExpandHelper;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/ExpandHelper$2;->this$0:Lcom/android/systemui/ExpandHelper;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/view/ScaleGestureDetector$SimpleOnScaleGestureListener;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onScale(Landroid/view/ScaleGestureDetector;)Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public final onScaleBegin(Landroid/view/ScaleGestureDetector;)Z
    .locals 2

    .line 1
    iget-object p1, p0, Lcom/android/systemui/ExpandHelper$2;->this$0:Lcom/android/systemui/ExpandHelper;

    .line 2
    .line 3
    iget-boolean v0, p1, Lcom/android/systemui/ExpandHelper;->mOnlyMovements:Z

    .line 4
    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    iget-object v0, p1, Lcom/android/systemui/ExpandHelper;->mResizedView:Lcom/android/systemui/statusbar/notification/row/ExpandableView;

    .line 8
    .line 9
    const/4 v1, 0x4

    .line 10
    invoke-virtual {p1, v0, v1}, Lcom/android/systemui/ExpandHelper;->startExpanding(Lcom/android/systemui/statusbar/notification/row/ExpandableView;I)Z

    .line 11
    .line 12
    .line 13
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/ExpandHelper$2;->this$0:Lcom/android/systemui/ExpandHelper;

    .line 14
    .line 15
    iget-boolean p0, p0, Lcom/android/systemui/ExpandHelper;->mExpanding:Z

    .line 16
    .line 17
    return p0
.end method

.method public final onScaleEnd(Landroid/view/ScaleGestureDetector;)V
    .locals 0

    .line 1
    return-void
.end method
.class public final Landroidx/core/widget/NestedScrollView$5;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic this$0:Landroidx/core/widget/NestedScrollView;


# direct methods
.method public constructor <init>(Landroidx/core/widget/NestedScrollView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/core/widget/NestedScrollView$5;->this$0:Landroidx/core/widget/NestedScrollView;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Landroidx/core/widget/NestedScrollView$5;->this$0:Landroidx/core/widget/NestedScrollView;

    .line 2
    .line 3
    sget v0, Landroidx/core/widget/NestedScrollView;->DECELERATION_RATE:F

    .line 4
    .line 5
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getPaddingLeft()I

    .line 6
    .line 7
    .line 8
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getPaddingRight()I

    .line 9
    .line 10
    .line 11
    return-void
.end method

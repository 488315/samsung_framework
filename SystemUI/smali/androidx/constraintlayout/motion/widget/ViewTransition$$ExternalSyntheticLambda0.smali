.class public final synthetic Landroidx/constraintlayout/motion/widget/ViewTransition$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Landroidx/constraintlayout/motion/widget/ViewTransition;

.field public final synthetic f$1:[Landroid/view/View;


# direct methods
.method public synthetic constructor <init>(Landroidx/constraintlayout/motion/widget/ViewTransition;[Landroid/view/View;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/ViewTransition$$ExternalSyntheticLambda0;->f$0:Landroidx/constraintlayout/motion/widget/ViewTransition;

    .line 5
    .line 6
    iput-object p2, p0, Landroidx/constraintlayout/motion/widget/ViewTransition$$ExternalSyntheticLambda0;->f$1:[Landroid/view/View;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 9

    .line 1
    iget-object v0, p0, Landroidx/constraintlayout/motion/widget/ViewTransition$$ExternalSyntheticLambda0;->f$0:Landroidx/constraintlayout/motion/widget/ViewTransition;

    .line 2
    .line 3
    iget-object p0, p0, Landroidx/constraintlayout/motion/widget/ViewTransition$$ExternalSyntheticLambda0;->f$1:[Landroid/view/View;

    .line 4
    .line 5
    iget v1, v0, Landroidx/constraintlayout/motion/widget/ViewTransition;->mSetsTag:I

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    const/4 v3, -0x1

    .line 9
    if-eq v1, v3, :cond_0

    .line 10
    .line 11
    array-length v1, p0

    .line 12
    move v4, v2

    .line 13
    :goto_0
    if-ge v4, v1, :cond_0

    .line 14
    .line 15
    aget-object v5, p0, v4

    .line 16
    .line 17
    iget v6, v0, Landroidx/constraintlayout/motion/widget/ViewTransition;->mSetsTag:I

    .line 18
    .line 19
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    .line 20
    .line 21
    .line 22
    move-result-wide v7

    .line 23
    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 24
    .line 25
    .line 26
    move-result-object v7

    .line 27
    invoke-virtual {v5, v6, v7}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    .line 28
    .line 29
    .line 30
    add-int/lit8 v4, v4, 0x1

    .line 31
    .line 32
    goto :goto_0

    .line 33
    :cond_0
    iget v1, v0, Landroidx/constraintlayout/motion/widget/ViewTransition;->mClearsTag:I

    .line 34
    .line 35
    if-eq v1, v3, :cond_1

    .line 36
    .line 37
    array-length v1, p0

    .line 38
    :goto_1
    if-ge v2, v1, :cond_1

    .line 39
    .line 40
    aget-object v3, p0, v2

    .line 41
    .line 42
    iget v4, v0, Landroidx/constraintlayout/motion/widget/ViewTransition;->mClearsTag:I

    .line 43
    .line 44
    const/4 v5, 0x0

    .line 45
    invoke-virtual {v3, v4, v5}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    .line 46
    .line 47
    .line 48
    add-int/lit8 v2, v2, 0x1

    .line 49
    .line 50
    goto :goto_1

    .line 51
    :cond_1
    return-void
.end method

.class public final synthetic Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/common/split/SplitLayout;

.field public final synthetic f$1:Z

.field public final synthetic f$2:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/common/split/SplitLayout;ZI)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/common/split/SplitLayout;

    .line 5
    .line 6
    iput-boolean p2, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$1:Z

    .line 7
    .line 8
    iput p3, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$2:I

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/common/split/SplitLayout;

    .line 2
    .line 3
    iget-boolean v1, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$1:Z

    .line 4
    .line 5
    iget p0, p0, Lcom/android/wm/shell/common/split/SplitLayout$$ExternalSyntheticLambda1;->f$2:I

    .line 6
    .line 7
    iget-object v0, v0, Lcom/android/wm/shell/common/split/SplitLayout;->mSplitLayoutHandler:Lcom/android/wm/shell/common/split/SplitLayout$SplitLayoutHandler;

    .line 8
    .line 9
    check-cast v0, Lcom/android/wm/shell/splitscreen/StageCoordinator;

    .line 10
    .line 11
    const/4 v2, 0x0

    .line 12
    invoke-virtual {v0, p0, v1, v2}, Lcom/android/wm/shell/splitscreen/StageCoordinator;->onSnappedToDismiss(IZZ)V

    .line 13
    .line 14
    .line 15
    return-void
.end method
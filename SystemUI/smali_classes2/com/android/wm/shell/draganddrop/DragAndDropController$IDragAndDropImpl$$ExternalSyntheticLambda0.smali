.class public final synthetic Lcom/android/wm/shell/draganddrop/DragAndDropController$IDragAndDropImpl$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic f$0:[Z


# direct methods
.method public synthetic constructor <init>([Z)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/draganddrop/DragAndDropController$IDragAndDropImpl$$ExternalSyntheticLambda0;->f$0:[Z

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 3

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/draganddrop/DragAndDropController$IDragAndDropImpl$$ExternalSyntheticLambda0;->f$0:[Z

    .line 2
    .line 3
    check-cast p1, Lcom/android/wm/shell/draganddrop/DragAndDropController;

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    move v1, v0

    .line 7
    :goto_0
    iget-object v2, p1, Lcom/android/wm/shell/draganddrop/DragAndDropController;->mDisplayDropTargets:Landroid/util/SparseArray;

    .line 8
    .line 9
    invoke-virtual {v2}, Landroid/util/SparseArray;->size()I

    .line 10
    .line 11
    .line 12
    move-result v2

    .line 13
    if-ge v1, v2, :cond_1

    .line 14
    .line 15
    iget-object v2, p1, Lcom/android/wm/shell/draganddrop/DragAndDropController;->mDisplayDropTargets:Landroid/util/SparseArray;

    .line 16
    .line 17
    invoke-virtual {v2, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    .line 18
    .line 19
    .line 20
    move-result-object v2

    .line 21
    check-cast v2, Lcom/android/wm/shell/draganddrop/DragAndDropController$PerDisplay;

    .line 22
    .line 23
    iget-boolean v2, v2, Lcom/android/wm/shell/draganddrop/DragAndDropController$PerDisplay;->mHasDrawn:Z

    .line 24
    .line 25
    if-eqz v2, :cond_0

    .line 26
    .line 27
    const/4 p1, 0x1

    .line 28
    goto :goto_1

    .line 29
    :cond_0
    add-int/lit8 v1, v1, 0x1

    .line 30
    .line 31
    goto :goto_0

    .line 32
    :cond_1
    move p1, v0

    .line 33
    :goto_1
    aput-boolean p1, p0, v0

    .line 34
    .line 35
    return-void
.end method
.class public final synthetic Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/wm/shell/freeform/FreeformContainerFolderView$1;->this$0:Lcom/android/wm/shell/freeform/FreeformContainerFolderView;

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    iput-boolean v0, p0, Lcom/android/wm/shell/freeform/FreeformContainerFolderView;->mItemAddedWhileAnimating:Z

    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/wm/shell/freeform/FreeformContainerFolderView;->mAdapter:Lcom/android/wm/shell/freeform/FreeformContainerFolderView$FolderViewAdapter;

    .line 9
    .line 10
    invoke-virtual {p0}, Lcom/android/internal/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    .line 11
    .line 12
    .line 13
    return-void
.end method

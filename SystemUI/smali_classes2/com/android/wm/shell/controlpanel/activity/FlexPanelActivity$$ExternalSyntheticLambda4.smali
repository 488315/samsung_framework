.class public final synthetic Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;

.field public final synthetic f$1:Landroid/view/View;

.field public final synthetic f$2:Landroid/view/DragEvent;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;Landroid/view/View;Landroid/view/DragEvent;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$0:Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$1:Landroid/view/View;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$2:Landroid/view/DragEvent;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$0:Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$1:Landroid/view/View;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity$$ExternalSyntheticLambda4;->f$2:Landroid/view/DragEvent;

    .line 6
    .line 7
    sget v2, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;->mEditPanelItemSize:I

    .line 8
    .line 9
    invoke-virtual {v0, v1, p0}, Lcom/android/wm/shell/controlpanel/activity/FlexPanelActivity;->onDrag(Landroid/view/View;Landroid/view/DragEvent;)Z

    .line 10
    .line 11
    .line 12
    return-void
.end method
.class public final synthetic Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl;

.field public final synthetic f$1:I

.field public final synthetic f$2:Landroid/content/res/Configuration;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl;ILandroid/content/res/Configuration;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$0:Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$1:I

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$2:Landroid/content/res/Configuration;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$0:Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$1:I

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3;->f$2:Landroid/content/res/Configuration;

    .line 6
    .line 7
    iget-object v0, v0, Lcom/android/wm/shell/common/DisplayController$DisplayWindowListenerImpl;->this$0:Lcom/android/wm/shell/common/DisplayController;

    .line 8
    .line 9
    invoke-virtual {v0, v1, p0}, Lcom/android/wm/shell/common/DisplayController;->onDisplayConfigurationChanged(ILandroid/content/res/Configuration;)V

    .line 10
    .line 11
    .line 12
    return-void
.end method
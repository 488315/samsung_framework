.class public final Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $cancelWrapper:Ljava/util/function/Consumer;

.field public final synthetic $componentName:Landroid/content/ComponentName;

.field public final synthetic $dataCallback:Ljava/util/function/Consumer;

.field public final synthetic this$0:Lcom/android/systemui/controls/controller/ControlsControllerImpl;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/controller/ControlsControllerImpl;Landroid/content/ComponentName;Ljava/util/function/Consumer;Ljava/util/function/Consumer;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/controls/controller/ControlsControllerImpl;",
            "Landroid/content/ComponentName;",
            "Ljava/util/function/Consumer<",
            "Lcom/android/systemui/controls/controller/ControlsController$LoadData;",
            ">;",
            "Ljava/util/function/Consumer<",
            "Ljava/lang/Runnable;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->this$0:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$componentName:Landroid/content/ComponentName;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$dataCallback:Ljava/util/function/Consumer;

    .line 6
    .line 7
    iput-object p4, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$cancelWrapper:Ljava/util/function/Consumer;

    .line 8
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 10
    .line 11
    .line 12
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->this$0:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$componentName:Landroid/content/ComponentName;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$dataCallback:Ljava/util/function/Consumer;

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/systemui/controls/controller/ControlsControllerImpl$loadForComponent$1;->$cancelWrapper:Ljava/util/function/Consumer;

    .line 8
    .line 9
    invoke-virtual {v0, v1, v2, p0}, Lcom/android/systemui/controls/controller/ControlsControllerImpl;->loadForComponent(Landroid/content/ComponentName;Ljava/util/function/Consumer;Ljava/util/function/Consumer;)V

    .line 10
    .line 11
    .line 12
    return-void
.end method
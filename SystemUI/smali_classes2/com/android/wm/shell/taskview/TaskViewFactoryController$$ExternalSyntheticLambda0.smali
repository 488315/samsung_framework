.class public final synthetic Lcom/android/wm/shell/taskview/TaskViewFactoryController$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Ljava/util/function/Consumer;

.field public final synthetic f$1:Lcom/android/wm/shell/taskview/TaskView;


# direct methods
.method public synthetic constructor <init>(Ljava/util/function/Consumer;Lcom/android/wm/shell/taskview/TaskView;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/taskview/TaskViewFactoryController$$ExternalSyntheticLambda0;->f$0:Ljava/util/function/Consumer;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/wm/shell/taskview/TaskViewFactoryController$$ExternalSyntheticLambda0;->f$1:Lcom/android/wm/shell/taskview/TaskView;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/taskview/TaskViewFactoryController$$ExternalSyntheticLambda0;->f$0:Ljava/util/function/Consumer;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/wm/shell/taskview/TaskViewFactoryController$$ExternalSyntheticLambda0;->f$1:Lcom/android/wm/shell/taskview/TaskView;

    .line 4
    .line 5
    invoke-interface {v0, p0}, Ljava/util/function/Consumer;->accept(Ljava/lang/Object;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method

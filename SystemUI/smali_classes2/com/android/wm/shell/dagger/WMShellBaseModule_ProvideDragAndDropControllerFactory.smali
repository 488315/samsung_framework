.class public final Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# instance fields
.field public final contextProvider:Ljavax/inject/Provider;

.field public final displayControllerProvider:Ljavax/inject/Provider;

.field public final iconProvider:Ljavax/inject/Provider;

.field public final mainExecutorProvider:Ljavax/inject/Provider;

.field public final shellCommandHandlerProvider:Ljavax/inject/Provider;

.field public final shellControllerProvider:Ljavax/inject/Provider;

.field public final shellInitProvider:Ljavax/inject/Provider;

.field public final uiEventLoggerProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->contextProvider:Ljavax/inject/Provider;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellInitProvider:Ljavax/inject/Provider;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellControllerProvider:Ljavax/inject/Provider;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellCommandHandlerProvider:Ljavax/inject/Provider;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->displayControllerProvider:Ljavax/inject/Provider;

    .line 13
    .line 14
    iput-object p6, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->uiEventLoggerProvider:Ljavax/inject/Provider;

    .line 15
    .line 16
    iput-object p7, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->iconProvider:Ljavax/inject/Provider;

    .line 17
    .line 18
    iput-object p8, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->mainExecutorProvider:Ljavax/inject/Provider;

    .line 19
    .line 20
    return-void
.end method

.method public static provideDragAndDropController(Landroid/content/Context;Lcom/android/wm/shell/sysui/ShellInit;Lcom/android/wm/shell/sysui/ShellController;Lcom/android/wm/shell/sysui/ShellCommandHandler;Lcom/android/wm/shell/common/DisplayController;Lcom/android/internal/logging/UiEventLogger;Lcom/android/launcher3/icons/IconProvider;Lcom/android/wm/shell/common/ShellExecutor;)Ljava/util/Optional;
    .locals 10

    .line 1
    sget v0, Lcom/android/wm/shell/draganddrop/DragAndDropController;->$r8$clinit:I

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    const v1, 0x7f050019

    .line 8
    .line 9
    .line 10
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    if-nez v0, :cond_0

    .line 15
    .line 16
    const/4 v0, 0x0

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    new-instance v0, Lcom/android/wm/shell/draganddrop/DragAndDropController;

    .line 19
    .line 20
    move-object v1, v0

    .line 21
    move-object v2, p0

    .line 22
    move-object v3, p1

    .line 23
    move-object v4, p2

    .line 24
    move-object v5, p3

    .line 25
    move-object v6, p4

    .line 26
    move-object v7, p5

    .line 27
    move-object/from16 v8, p6

    .line 28
    .line 29
    move-object/from16 v9, p7

    .line 30
    .line 31
    invoke-direct/range {v1 .. v9}, Lcom/android/wm/shell/draganddrop/DragAndDropController;-><init>(Landroid/content/Context;Lcom/android/wm/shell/sysui/ShellInit;Lcom/android/wm/shell/sysui/ShellController;Lcom/android/wm/shell/sysui/ShellCommandHandler;Lcom/android/wm/shell/common/DisplayController;Lcom/android/internal/logging/UiEventLogger;Lcom/android/launcher3/icons/IconProvider;Lcom/android/wm/shell/common/ShellExecutor;)V

    .line 32
    .line 33
    .line 34
    :goto_0
    invoke-static {v0}, Ljava/util/Optional;->ofNullable(Ljava/lang/Object;)Ljava/util/Optional;

    .line 35
    .line 36
    .line 37
    move-result-object v0

    .line 38
    invoke-static {v0}, Ldagger/internal/Preconditions;->checkNotNullFromProvides(Ljava/lang/Object;)V

    .line 39
    .line 40
    .line 41
    return-object v0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 9

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->contextProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    move-object v1, v0

    .line 8
    check-cast v1, Landroid/content/Context;

    .line 9
    .line 10
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellInitProvider:Ljavax/inject/Provider;

    .line 11
    .line 12
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 13
    .line 14
    .line 15
    move-result-object v0

    .line 16
    move-object v2, v0

    .line 17
    check-cast v2, Lcom/android/wm/shell/sysui/ShellInit;

    .line 18
    .line 19
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellControllerProvider:Ljavax/inject/Provider;

    .line 20
    .line 21
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 22
    .line 23
    .line 24
    move-result-object v0

    .line 25
    move-object v3, v0

    .line 26
    check-cast v3, Lcom/android/wm/shell/sysui/ShellController;

    .line 27
    .line 28
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->shellCommandHandlerProvider:Ljavax/inject/Provider;

    .line 29
    .line 30
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 31
    .line 32
    .line 33
    move-result-object v0

    .line 34
    move-object v4, v0

    .line 35
    check-cast v4, Lcom/android/wm/shell/sysui/ShellCommandHandler;

    .line 36
    .line 37
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->displayControllerProvider:Ljavax/inject/Provider;

    .line 38
    .line 39
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 40
    .line 41
    .line 42
    move-result-object v0

    .line 43
    move-object v5, v0

    .line 44
    check-cast v5, Lcom/android/wm/shell/common/DisplayController;

    .line 45
    .line 46
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->uiEventLoggerProvider:Ljavax/inject/Provider;

    .line 47
    .line 48
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 49
    .line 50
    .line 51
    move-result-object v0

    .line 52
    move-object v6, v0

    .line 53
    check-cast v6, Lcom/android/internal/logging/UiEventLogger;

    .line 54
    .line 55
    iget-object v0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->iconProvider:Ljavax/inject/Provider;

    .line 56
    .line 57
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 58
    .line 59
    .line 60
    move-result-object v0

    .line 61
    move-object v7, v0

    .line 62
    check-cast v7, Lcom/android/launcher3/icons/IconProvider;

    .line 63
    .line 64
    iget-object p0, p0, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->mainExecutorProvider:Ljavax/inject/Provider;

    .line 65
    .line 66
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 67
    .line 68
    .line 69
    move-result-object p0

    .line 70
    move-object v8, p0

    .line 71
    check-cast v8, Lcom/android/wm/shell/common/ShellExecutor;

    .line 72
    .line 73
    invoke-static/range {v1 .. v8}, Lcom/android/wm/shell/dagger/WMShellBaseModule_ProvideDragAndDropControllerFactory;->provideDragAndDropController(Landroid/content/Context;Lcom/android/wm/shell/sysui/ShellInit;Lcom/android/wm/shell/sysui/ShellController;Lcom/android/wm/shell/sysui/ShellCommandHandler;Lcom/android/wm/shell/common/DisplayController;Lcom/android/internal/logging/UiEventLogger;Lcom/android/launcher3/icons/IconProvider;Lcom/android/wm/shell/common/ShellExecutor;)Ljava/util/Optional;

    .line 74
    .line 75
    .line 76
    move-result-object p0

    .line 77
    return-object p0
.end method
.class public final synthetic Lcom/android/wm/shell/common/DisplayChangeController$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/common/DisplayChangeController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/common/DisplayChangeController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/common/DisplayChangeController$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/common/DisplayChangeController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/common/DisplayChangeController$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/common/DisplayChangeController;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    :try_start_0
    iget-object v0, p0, Lcom/android/wm/shell/common/DisplayChangeController;->mWmService:Landroid/view/IWindowManager;

    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/wm/shell/common/DisplayChangeController;->mControllerImpl:Lcom/android/wm/shell/common/DisplayChangeController$DisplayChangeWindowControllerImpl;

    .line 9
    .line 10
    invoke-interface {v0, p0}, Landroid/view/IWindowManager;->setDisplayChangeWindowController(Landroid/view/IDisplayChangeWindowController;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 11
    .line 12
    .line 13
    return-void

    .line 14
    :catch_0
    new-instance p0, Ljava/lang/RuntimeException;

    .line 15
    .line 16
    const-string v0, "Unable to register rotation controller"

    .line 17
    .line 18
    invoke-direct {p0, v0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    .line 19
    .line 20
    .line 21
    throw p0
.end method

.class public final Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# instance fields
.field public final clickNotifierProvider:Ljavax/inject/Provider;

.field public final dumpManagerProvider:Ljavax/inject/Provider;

.field public final statusBarServiceProvider:Ljavax/inject/Provider;

.field public final visibilityProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
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
    iput-object p1, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->dumpManagerProvider:Ljavax/inject/Provider;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->visibilityProvider:Ljavax/inject/Provider;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->statusBarServiceProvider:Ljavax/inject/Provider;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->clickNotifierProvider:Ljavax/inject/Provider;

    .line 11
    .line 12
    return-void
.end method

.method public static provideSmartReplyController(Lcom/android/systemui/dump/DumpManager;Lcom/android/systemui/statusbar/notification/collection/render/NotificationVisibilityProvider;Lcom/android/internal/statusbar/IStatusBarService;Lcom/android/systemui/statusbar/NotificationClickNotifier;)Lcom/android/systemui/statusbar/SmartReplyController;
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/statusbar/SmartReplyController;

    .line 2
    .line 3
    invoke-direct {v0, p0, p1, p2, p3}, Lcom/android/systemui/statusbar/SmartReplyController;-><init>(Lcom/android/systemui/dump/DumpManager;Lcom/android/systemui/statusbar/notification/collection/render/NotificationVisibilityProvider;Lcom/android/internal/statusbar/IStatusBarService;Lcom/android/systemui/statusbar/NotificationClickNotifier;)V

    .line 4
    .line 5
    .line 6
    return-object v0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->dumpManagerProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Lcom/android/systemui/dump/DumpManager;

    .line 8
    .line 9
    iget-object v1, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->visibilityProvider:Ljavax/inject/Provider;

    .line 10
    .line 11
    invoke-interface {v1}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 12
    .line 13
    .line 14
    move-result-object v1

    .line 15
    check-cast v1, Lcom/android/systemui/statusbar/notification/collection/render/NotificationVisibilityProvider;

    .line 16
    .line 17
    iget-object v2, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->statusBarServiceProvider:Ljavax/inject/Provider;

    .line 18
    .line 19
    invoke-interface {v2}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object v2

    .line 23
    check-cast v2, Lcom/android/internal/statusbar/IStatusBarService;

    .line 24
    .line 25
    iget-object p0, p0, Lcom/android/systemui/statusbar/dagger/CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;->clickNotifierProvider:Ljavax/inject/Provider;

    .line 26
    .line 27
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 28
    .line 29
    .line 30
    move-result-object p0

    .line 31
    check-cast p0, Lcom/android/systemui/statusbar/NotificationClickNotifier;

    .line 32
    .line 33
    new-instance v3, Lcom/android/systemui/statusbar/SmartReplyController;

    .line 34
    .line 35
    invoke-direct {v3, v0, v1, v2, p0}, Lcom/android/systemui/statusbar/SmartReplyController;-><init>(Lcom/android/systemui/dump/DumpManager;Lcom/android/systemui/statusbar/notification/collection/render/NotificationVisibilityProvider;Lcom/android/internal/statusbar/IStatusBarService;Lcom/android/systemui/statusbar/NotificationClickNotifier;)V

    .line 36
    .line 37
    .line 38
    return-object v3
.end method
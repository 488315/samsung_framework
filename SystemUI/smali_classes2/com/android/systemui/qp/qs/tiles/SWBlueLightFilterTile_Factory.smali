.class public final Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# instance fields
.field public final centralSurfacesProvider:Ljavax/inject/Provider;

.field public final contextProvider:Ljavax/inject/Provider;

.field public final settingsHelperProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Ljavax/inject/Provider;Ljavax/inject/Provider;Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
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
    iput-object p1, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->centralSurfacesProvider:Ljavax/inject/Provider;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->contextProvider:Ljavax/inject/Provider;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->settingsHelperProvider:Ljavax/inject/Provider;

    .line 9
    .line 10
    return-void
.end method

.method public static newInstance(Lcom/android/systemui/statusbar/phone/CentralSurfaces;Landroid/content/Context;Lcom/android/systemui/util/SettingsHelper;)Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile;
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile;

    .line 2
    .line 3
    invoke-direct {v0, p0, p1, p2}, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile;-><init>(Lcom/android/systemui/statusbar/phone/CentralSurfaces;Landroid/content/Context;Lcom/android/systemui/util/SettingsHelper;)V

    .line 4
    .line 5
    .line 6
    return-object v0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->centralSurfacesProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Lcom/android/systemui/statusbar/phone/CentralSurfaces;

    .line 8
    .line 9
    iget-object v1, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->contextProvider:Ljavax/inject/Provider;

    .line 10
    .line 11
    invoke-interface {v1}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 12
    .line 13
    .line 14
    move-result-object v1

    .line 15
    check-cast v1, Landroid/content/Context;

    .line 16
    .line 17
    iget-object p0, p0, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile_Factory;->settingsHelperProvider:Ljavax/inject/Provider;

    .line 18
    .line 19
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object p0

    .line 23
    check-cast p0, Lcom/android/systemui/util/SettingsHelper;

    .line 24
    .line 25
    new-instance v2, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile;

    .line 26
    .line 27
    invoke-direct {v2, v0, v1, p0}, Lcom/android/systemui/qp/qs/tiles/SWBlueLightFilterTile;-><init>(Lcom/android/systemui/statusbar/phone/CentralSurfaces;Landroid/content/Context;Lcom/android/systemui/util/SettingsHelper;)V

    .line 28
    .line 29
    .line 30
    return-object v2
.end method

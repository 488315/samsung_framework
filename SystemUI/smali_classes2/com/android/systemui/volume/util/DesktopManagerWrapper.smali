.class public final Lcom/android/systemui/volume/util/DesktopManagerWrapper;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final desktopManager:Lcom/android/systemui/util/DesktopManager;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    const-class v0, Lcom/android/systemui/util/DesktopManager;

    .line 5
    .line 6
    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 7
    .line 8
    .line 9
    move-result-object v0

    .line 10
    check-cast v0, Lcom/android/systemui/util/DesktopManager;

    .line 11
    .line 12
    iput-object v0, p0, Lcom/android/systemui/volume/util/DesktopManagerWrapper;->desktopManager:Lcom/android/systemui/util/DesktopManager;

    .line 13
    .line 14
    return-void
.end method
.class public final synthetic Lcom/android/wm/shell/desktopmode/DesktopModeController$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Supplier;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/desktopmode/DesktopModeController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/desktopmode/DesktopModeController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/desktopmode/DesktopModeController$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/desktopmode/DesktopModeController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/desktopmode/DesktopModeController$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/desktopmode/DesktopModeController;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    new-instance v0, Lcom/android/wm/shell/desktopmode/DesktopModeController$IDesktopModeImpl;

    .line 7
    .line 8
    invoke-direct {v0, p0}, Lcom/android/wm/shell/desktopmode/DesktopModeController$IDesktopModeImpl;-><init>(Lcom/android/wm/shell/desktopmode/DesktopModeController;)V

    .line 9
    .line 10
    .line 11
    return-object v0
.end method
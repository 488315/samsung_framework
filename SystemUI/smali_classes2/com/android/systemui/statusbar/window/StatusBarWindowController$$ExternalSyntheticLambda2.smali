.class public final synthetic Lcom/android/systemui/statusbar/window/StatusBarWindowController$$ExternalSyntheticLambda2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/phone/StatusBarContentInsetsChangedListener;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/window/StatusBarWindowController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onStatusBarContentInsetsChanged()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/window/StatusBarWindowController$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/statusbar/window/StatusBarWindowController;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/window/StatusBarWindowController;->calculateStatusBarLocationsForAllRotations()V

    .line 4
    .line 5
    .line 6
    return-void
.end method
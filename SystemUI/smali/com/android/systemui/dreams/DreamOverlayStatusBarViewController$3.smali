.class public final Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/ZenModeController$Callback;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$3;->this$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onZenChanged(I)V
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$3;->this$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;

    .line 2
    .line 3
    iget-object p1, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;->mZenModeController:Lcom/android/systemui/statusbar/policy/ZenModeController;

    .line 4
    .line 5
    check-cast p1, Lcom/android/systemui/statusbar/policy/ZenModeControllerImpl;

    .line 6
    .line 7
    iget p1, p1, Lcom/android/systemui/statusbar/policy/ZenModeControllerImpl;->mZenMode:I

    .line 8
    .line 9
    if-eqz p1, :cond_0

    .line 10
    .line 11
    const/4 p1, 0x1

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 p1, 0x0

    .line 14
    :goto_0
    const v0, 0x7f130cd4

    .line 15
    .line 16
    .line 17
    const/4 v1, 0x6

    .line 18
    invoke-virtual {p0, v1, v0, p1}, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;->showIcon(IIZ)V

    .line 19
    .line 20
    .line 21
    return-void
.end method

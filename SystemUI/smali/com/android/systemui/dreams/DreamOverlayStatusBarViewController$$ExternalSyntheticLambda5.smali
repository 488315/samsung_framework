.class public final synthetic Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/window/StatusBarWindowStateListener;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5;->f$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onStatusBarWindowStateChanged(I)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5;->f$0:Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;

    .line 2
    .line 3
    iget-boolean p1, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;->mIsAttached:Z

    .line 4
    .line 5
    if-eqz p1, :cond_1

    .line 6
    .line 7
    iget-boolean p1, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;->mEntryAnimationsFinished:Z

    .line 8
    .line 9
    if-nez p1, :cond_0

    .line 10
    .line 11
    goto :goto_0

    .line 12
    :cond_0
    new-instance p1, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$$ExternalSyntheticLambda7;

    .line 13
    .line 14
    invoke-direct {p1, p0}, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController$$ExternalSyntheticLambda7;-><init>(Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;)V

    .line 15
    .line 16
    .line 17
    iget-object p0, p0, Lcom/android/systemui/dreams/DreamOverlayStatusBarViewController;->mMainExecutor:Ljava/util/concurrent/Executor;

    .line 18
    .line 19
    invoke-interface {p0, p1}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 20
    .line 21
    .line 22
    :cond_1
    :goto_0
    return-void
.end method

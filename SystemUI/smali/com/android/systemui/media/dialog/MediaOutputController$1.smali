.class public final Lcom/android/systemui/media/dialog/MediaOutputController$1;
.super Landroid/media/session/MediaController$Callback;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/media/dialog/MediaOutputController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/media/dialog/MediaOutputController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/media/dialog/MediaOutputController$1;->this$0:Lcom/android/systemui/media/dialog/MediaOutputController;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/media/session/MediaController$Callback;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onMetadataChanged(Landroid/media/MediaMetadata;)V
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/android/systemui/media/dialog/MediaOutputController$1;->this$0:Lcom/android/systemui/media/dialog/MediaOutputController;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/media/dialog/MediaOutputController;->mCallback:Lcom/android/systemui/media/dialog/MediaOutputController$Callback;

    .line 4
    .line 5
    check-cast p0, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog;

    .line 6
    .line 7
    iget-object p1, p0, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog;->mMainThreadHandler:Landroid/os/Handler;

    .line 8
    .line 9
    new-instance v0, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog$$ExternalSyntheticLambda1;

    .line 10
    .line 11
    const/4 v1, 0x0

    .line 12
    invoke-direct {v0, p0, v1}, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog$$ExternalSyntheticLambda1;-><init>(Lcom/android/systemui/media/dialog/MediaOutputBaseDialog;I)V

    .line 13
    .line 14
    .line 15
    invoke-virtual {p1, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 16
    .line 17
    .line 18
    return-void
.end method

.method public final onPlaybackStateChanged(Landroid/media/session/PlaybackState;)V
    .locals 3

    .line 1
    const/4 v0, 0x1

    .line 2
    if-nez p1, :cond_0

    .line 3
    .line 4
    move p1, v0

    .line 5
    goto :goto_0

    .line 6
    :cond_0
    invoke-virtual {p1}, Landroid/media/session/PlaybackState;->getState()I

    .line 7
    .line 8
    .line 9
    move-result p1

    .line 10
    :goto_0
    iget-object v1, p0, Lcom/android/systemui/media/dialog/MediaOutputController$1;->this$0:Lcom/android/systemui/media/dialog/MediaOutputController;

    .line 11
    .line 12
    iget v2, v1, Lcom/android/systemui/media/dialog/MediaOutputController;->mCurrentState:I

    .line 13
    .line 14
    if-ne v2, p1, :cond_1

    .line 15
    .line 16
    return-void

    .line 17
    :cond_1
    if-ne p1, v0, :cond_2

    .line 18
    .line 19
    iget-object v0, v1, Lcom/android/systemui/media/dialog/MediaOutputController;->mCallback:Lcom/android/systemui/media/dialog/MediaOutputController$Callback;

    .line 20
    .line 21
    check-cast v0, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog;

    .line 22
    .line 23
    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    .line 24
    .line 25
    .line 26
    move-result v1

    .line 27
    if-eqz v1, :cond_2

    .line 28
    .line 29
    invoke-virtual {v0}, Lcom/android/systemui/media/dialog/MediaOutputBaseDialog;->dismiss()V

    .line 30
    .line 31
    .line 32
    :cond_2
    iget-object p0, p0, Lcom/android/systemui/media/dialog/MediaOutputController$1;->this$0:Lcom/android/systemui/media/dialog/MediaOutputController;

    .line 33
    .line 34
    iput p1, p0, Lcom/android/systemui/media/dialog/MediaOutputController;->mCurrentState:I

    .line 35
    .line 36
    return-void
.end method
.class public final synthetic Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/wm/shell/controlpanel/activity/TouchPad;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/controlpanel/activity/TouchPad;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/controlpanel/activity/TouchPad;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    .line 1
    iget v0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/controlpanel/activity/TouchPad;

    .line 8
    .line 9
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad;->mTouchPadLine:Landroid/view/View;

    .line 10
    .line 11
    const/4 v1, 0x1

    .line 12
    invoke-virtual {p0, v0, v1}, Lcom/android/wm/shell/controlpanel/activity/TouchPad;->startFadeInAnimation(Landroid/view/View;Z)V

    .line 13
    .line 14
    .line 15
    return-void

    .line 16
    :goto_0
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPad$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/controlpanel/activity/TouchPad;

    .line 17
    .line 18
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/FloatingUI;->mOverlayView:Landroid/view/View;

    .line 19
    .line 20
    if-eqz v0, :cond_0

    .line 21
    .line 22
    const/16 v1, 0x8

    .line 23
    .line 24
    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 25
    .line 26
    .line 27
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/FloatingUI;->mOverlayView:Landroid/view/View;

    .line 28
    .line 29
    invoke-virtual {v0}, Landroid/view/View;->isAttachedToWindow()Z

    .line 30
    .line 31
    .line 32
    move-result v0

    .line 33
    if-eqz v0, :cond_0

    .line 34
    .line 35
    invoke-virtual {p0}, Lcom/android/wm/shell/controlpanel/activity/FloatingUI;->removeView()V

    .line 36
    .line 37
    .line 38
    :cond_0
    return-void

    .line 39
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
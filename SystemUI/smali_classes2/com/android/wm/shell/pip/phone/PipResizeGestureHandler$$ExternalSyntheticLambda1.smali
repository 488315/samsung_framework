.class public final synthetic Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;

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
    .locals 3

    .line 1
    iget v0, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;->mPipTouchState:Lcom/android/wm/shell/pip/phone/PipTouchState;

    .line 10
    .line 11
    const/4 v0, 0x1

    .line 12
    iput-boolean v0, p0, Lcom/android/wm/shell/pip/phone/PipTouchState;->mAllowInputEvents:Z

    .line 13
    .line 14
    return-void

    .line 15
    :goto_0
    iget-object p0, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$$ExternalSyntheticLambda1;->f$0:Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;

    .line 16
    .line 17
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 18
    .line 19
    .line 20
    new-instance v0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$PipResizeInputEventReceiver;

    .line 21
    .line 22
    iget-object v1, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;->mInputMonitor:Landroid/view/InputMonitor;

    .line 23
    .line 24
    invoke-virtual {v1}, Landroid/view/InputMonitor;->getInputChannel()Landroid/view/InputChannel;

    .line 25
    .line 26
    .line 27
    move-result-object v1

    .line 28
    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    .line 29
    .line 30
    .line 31
    move-result-object v2

    .line 32
    invoke-direct {v0, p0, v1, v2}, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$PipResizeInputEventReceiver;-><init>(Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;Landroid/view/InputChannel;Landroid/os/Looper;)V

    .line 33
    .line 34
    .line 35
    iput-object v0, p0, Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler;->mInputEventReceiver:Lcom/android/wm/shell/pip/phone/PipResizeGestureHandler$PipResizeInputEventReceiver;

    .line 36
    .line 37
    return-void

    .line 38
    nop

    .line 39
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method

.class public final Lcom/android/systemui/screenshot/ScreenshotView$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnAttachStateChangeListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/screenshot/ScreenshotView;


# direct methods
.method public constructor <init>(Lcom/android/systemui/screenshot/ScreenshotView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotView$2;->this$0:Lcom/android/systemui/screenshot/ScreenshotView;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onViewAttachedToWindow(Landroid/view/View;)V
    .locals 4

    .line 1
    iget-object p0, p0, Lcom/android/systemui/screenshot/ScreenshotView$2;->this$0:Lcom/android/systemui/screenshot/ScreenshotView;

    .line 2
    .line 3
    sget p1, Lcom/android/systemui/screenshot/ScreenshotView;->$r8$clinit:I

    .line 4
    .line 5
    invoke-virtual {p0}, Lcom/android/systemui/screenshot/ScreenshotView;->stopInputListening()V

    .line 6
    .line 7
    .line 8
    new-instance p1, Lcom/android/systemui/shared/system/InputMonitorCompat;

    .line 9
    .line 10
    const-string v0, "Screenshot"

    .line 11
    .line 12
    iget v1, p0, Lcom/android/systemui/screenshot/ScreenshotView;->mDefaultDisplay:I

    .line 13
    .line 14
    invoke-direct {p1, v0, v1}, Lcom/android/systemui/shared/system/InputMonitorCompat;-><init>(Ljava/lang/String;I)V

    .line 15
    .line 16
    .line 17
    iput-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotView;->mInputMonitor:Lcom/android/systemui/shared/system/InputMonitorCompat;

    .line 18
    .line 19
    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    invoke-static {}, Landroid/view/Choreographer;->getInstance()Landroid/view/Choreographer;

    .line 24
    .line 25
    .line 26
    move-result-object v1

    .line 27
    new-instance v2, Lcom/android/systemui/screenshot/ScreenshotView$$ExternalSyntheticLambda0;

    .line 28
    .line 29
    invoke-direct {v2, p0}, Lcom/android/systemui/screenshot/ScreenshotView$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/screenshot/ScreenshotView;)V

    .line 30
    .line 31
    .line 32
    new-instance v3, Lcom/android/systemui/shared/system/InputChannelCompat$InputEventReceiver;

    .line 33
    .line 34
    iget-object p1, p1, Lcom/android/systemui/shared/system/InputMonitorCompat;->mInputMonitor:Landroid/view/InputMonitor;

    .line 35
    .line 36
    invoke-virtual {p1}, Landroid/view/InputMonitor;->getInputChannel()Landroid/view/InputChannel;

    .line 37
    .line 38
    .line 39
    move-result-object p1

    .line 40
    invoke-direct {v3, p1, v0, v1, v2}, Lcom/android/systemui/shared/system/InputChannelCompat$InputEventReceiver;-><init>(Landroid/view/InputChannel;Landroid/os/Looper;Landroid/view/Choreographer;Lcom/android/systemui/shared/system/InputChannelCompat$InputEventListener;)V

    .line 41
    .line 42
    .line 43
    iput-object v3, p0, Lcom/android/systemui/screenshot/ScreenshotView;->mInputEventReceiver:Lcom/android/systemui/shared/system/InputChannelCompat$InputEventReceiver;

    .line 44
    .line 45
    return-void
.end method

.method public final onViewDetachedFromWindow(Landroid/view/View;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/screenshot/ScreenshotView$2;->this$0:Lcom/android/systemui/screenshot/ScreenshotView;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/screenshot/ScreenshotView;->stopInputListening()V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.class Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/lifecycle/DefaultLifecycleObserver;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;


# direct methods
.method public constructor <init>(Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$1;->this$0:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onDestroy$1()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$1;->this$0:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    invoke-virtual {p0, v0}, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->stopMonitoring(Z)V

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public final onPause$1()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$1;->this$0:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    invoke-virtual {p0, v0}, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->stopMonitoring(Z)V

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public final onResume$1()V
    .locals 5

    .line 1
    iget-object p0, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$1;->this$0:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    invoke-virtual {p0, v0}, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->stopMonitoring(Z)V

    .line 5
    .line 6
    .line 7
    iget-object v1, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->mInputSessionFactory:Lcom/android/systemui/dreams/touch/dagger/InputSessionComponent$Factory;

    .line 8
    .line 9
    const-string v2, "dreamOverlay"

    .line 10
    .line 11
    iget-object v3, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->mInputEventListener:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$2;

    .line 12
    .line 13
    iget-object v4, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->mOnGestureListener:Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor$3;

    .line 14
    .line 15
    invoke-interface {v1, v2, v3, v4, v0}, Lcom/android/systemui/dreams/touch/dagger/InputSessionComponent$Factory;->create(Ljava/lang/String;Lcom/android/systemui/shared/system/InputChannelCompat$InputEventListener;Landroid/view/GestureDetector$OnGestureListener;Z)Lcom/android/systemui/dreams/touch/dagger/InputSessionComponent;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    invoke-interface {v0}, Lcom/android/systemui/dreams/touch/dagger/InputSessionComponent;->getInputSession()Lcom/android/systemui/dreams/touch/InputSession;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    iput-object v0, p0, Lcom/android/systemui/dreams/touch/DreamOverlayTouchMonitor;->mCurrentInputSession:Lcom/android/systemui/dreams/touch/InputSession;

    .line 24
    .line 25
    return-void
.end method

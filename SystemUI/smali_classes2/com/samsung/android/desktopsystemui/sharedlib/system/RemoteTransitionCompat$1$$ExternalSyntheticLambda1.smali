.class public final synthetic Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionRunner;

.field public final synthetic f$1:Landroid/os/IBinder;

.field public final synthetic f$2:Landroid/window/TransitionInfo;

.field public final synthetic f$3:Landroid/view/SurfaceControl$Transaction;

.field public final synthetic f$4:Ljava/lang/Runnable;


# direct methods
.method public synthetic constructor <init>(Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionRunner;Landroid/os/IBinder;Landroid/window/TransitionInfo;Landroid/view/SurfaceControl$Transaction;Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$0:Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionRunner;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$1:Landroid/os/IBinder;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$2:Landroid/window/TransitionInfo;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$3:Landroid/view/SurfaceControl$Transaction;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$4:Ljava/lang/Runnable;

    .line 13
    .line 14
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$0:Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionRunner;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$1:Landroid/os/IBinder;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$2:Landroid/window/TransitionInfo;

    .line 6
    .line 7
    iget-object v3, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$3:Landroid/view/SurfaceControl$Transaction;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda1;->f$4:Ljava/lang/Runnable;

    .line 10
    .line 11
    invoke-static {v0, v1, v2, v3, p0}, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1;->$r8$lambda$yHHFGBInfE68TtzJYO318eEbUck(Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionRunner;Landroid/os/IBinder;Landroid/window/TransitionInfo;Landroid/view/SurfaceControl$Transaction;Ljava/lang/Runnable;)V

    .line 12
    .line 13
    .line 14
    return-void
.end method
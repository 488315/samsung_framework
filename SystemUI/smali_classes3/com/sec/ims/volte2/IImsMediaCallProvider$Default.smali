.class public Lcom/sec/ims/volte2/IImsMediaCallProvider$Default;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/sec/ims/volte2/IImsMediaCallProvider;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/IImsMediaCallProvider;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Default"
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public asBinder()Landroid/os/IBinder;
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return-object p0
.end method

.method public changeCameraCapabilities(III)V
    .locals 0

    .line 1
    return-void
.end method

.method public deinitSurface(Z)V
    .locals 0

    .line 1
    return-void
.end method

.method public getCameraInfo(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public getDefaultCameraId()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public getMaxZoom()V
    .locals 0

    .line 1
    return-void
.end method

.method public getZoom()V
    .locals 0

    .line 1
    return-void
.end method

.method public registerForVideoServiceEvent(Lcom/sec/ims/volte2/IVideoServiceEventListener;)V
    .locals 0

    .line 1
    return-void
.end method

.method public requestCallDataUsage()V
    .locals 0

    .line 1
    return-void
.end method

.method public resetCameraId()V
    .locals 0

    .line 1
    return-void
.end method

.method public sendGeneralEvent(IIILjava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public sendLiveVideo(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public sendStillImage(ILjava/lang/String;ILjava/lang/String;I)V
    .locals 0

    .line 1
    return-void
.end method

.method public setCamera(Ljava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public setCameraEffect(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public setDeviceOrientation(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public setDisplaySurface(ILandroid/view/Surface;)V
    .locals 0

    .line 1
    return-void
.end method

.method public setPreviewSurface(ILandroid/view/Surface;)V
    .locals 0

    .line 1
    return-void
.end method

.method public setZoom(F)V
    .locals 0

    .line 1
    return-void
.end method

.method public startCamera(Landroid/view/Surface;)V
    .locals 0

    .line 1
    return-void
.end method

.method public startEmoji(Ljava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public startRecord(Ljava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public startRender(Z)V
    .locals 0

    .line 1
    return-void
.end method

.method public startVideoRenderer(Landroid/view/Surface;)V
    .locals 0

    .line 1
    return-void
.end method

.method public stopCamera()V
    .locals 0

    .line 1
    return-void
.end method

.method public stopEmoji(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public stopRecord()V
    .locals 0

    .line 1
    return-void
.end method

.method public stopVideoRenderer()V
    .locals 0

    .line 1
    return-void
.end method

.method public swipeVideoSurface()V
    .locals 0

    .line 1
    return-void
.end method

.method public switchCamera()V
    .locals 0

    .line 1
    return-void
.end method

.method public unregisterForVideoServiceEvent(Lcom/sec/ims/volte2/IVideoServiceEventListener;)V
    .locals 0

    .line 1
    return-void
.end method

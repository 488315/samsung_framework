.class public Lcom/sec/ims/volte2/IImsCallSessionEventListener$Default;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/sec/ims/volte2/IImsCallSessionEventListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/IImsCallSessionEventListener;
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

.method public notifyReadyToHandleImsCallbacks()V
    .locals 0

    .line 1
    return-void
.end method

.method public onCallQualityChanged()V
    .locals 0

    .line 1
    return-void
.end method

.method public onCalling()V
    .locals 0

    .line 1
    return-void
.end method

.method public onConfParticipantHeld(IZ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onConfParticipantResumed(IZ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onConferenceEstablished()V
    .locals 0

    .line 1
    return-void
.end method

.method public onEPdgUnavailable(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEarlyMediaStarted(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEnded(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgStateChanged()V
    .locals 0

    .line 1
    return-void
.end method

.method public onError(ILjava/lang/String;I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEstablished(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onFailure(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onForwarded()V
    .locals 0

    .line 1
    return-void
.end method

.method public onHeld(ZZ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onImsGeneralEvent(Ljava/lang/String;Landroid/os/Bundle;)V
    .locals 0

    .line 1
    return-void
.end method

.method public onParticipantAdded(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onParticipantRemoved(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onParticipantUpdated(I[Ljava/lang/String;[I[I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onProfileUpdated(Lcom/sec/ims/volte2/data/MediaProfile;Lcom/sec/ims/volte2/data/MediaProfile;)V
    .locals 0

    .line 1
    return-void
.end method

.method public onResumed(Z)V
    .locals 0

    .line 1
    return-void
.end method

.method public onRetryingVoLteOrCsCall(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onRingingBack()V
    .locals 0

    .line 1
    return-void
.end method

.method public onSessionChanged(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onSessionProgress(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onSessionUpdateRequested(I[B)V
    .locals 0

    .line 1
    return-void
.end method

.method public onStopAlertTone()V
    .locals 0

    .line 1
    return-void
.end method

.method public onSwitched(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onTrying()V
    .locals 0

    .line 1
    return-void
.end method

.method public onTtyTextRequest(I[B)V
    .locals 0

    .line 1
    return-void
.end method

.method public onUssdReceived(II[B)V
    .locals 0

    .line 1
    return-void
.end method

.method public onUssdResponse(I)V
    .locals 0

    .line 1
    return-void
.end method

.class public Lcom/sec/ims/IEpdgListener$Default;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/sec/ims/IEpdgListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/IEpdgListener;
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

.method public onEpdgAvailable(III)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgDeregister(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgHandoverEnableChanged(IZ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgHandoverResult(IIILjava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgIpsecConnection(ILjava/lang/String;II)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgIpsecDisconnection(ILjava/lang/String;)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgRegister(IZ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgReleaseCall(I)V
    .locals 0

    .line 1
    return-void
.end method

.method public onEpdgShowPopup(II)V
    .locals 0

    .line 1
    return-void
.end method

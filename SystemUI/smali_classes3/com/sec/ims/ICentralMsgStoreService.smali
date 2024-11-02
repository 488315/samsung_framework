.class public interface abstract Lcom/sec/ims/ICentralMsgStoreService;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/sec/ims/ICentralMsgStoreService$Stub;,
        Lcom/sec/ims/ICentralMsgStoreService$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.sec.ims.ICentralMsgStoreService"


# virtual methods
.method public abstract cancelMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract createParticipant(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract createSession(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract deleteMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract deleteOldLegacyMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract deleteParticipant(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract deleteSession(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract disableAutoSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract downloadMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract enableAutoSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract getAccount(I)V
.end method

.method public abstract getRestartScreenName(Ljava/lang/String;)I
.end method

.method public abstract getSd(IZLjava/lang/String;)V
.end method

.method public abstract manageSd(IILjava/lang/String;)V
.end method

.method public abstract manualSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract notifyCloudMessageUpdate(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract notifyUIScreen(Ljava/lang/String;ILjava/lang/String;I)V
.end method

.method public abstract onBufferDBReadResult(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZ)V
.end method

.method public abstract onBufferDBReadResultBatch(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract onDefaultSmsPackageChanged()V
.end method

.method public abstract onDeregistered(Lcom/sec/ims/ImsRegistration;)V
.end method

.method public abstract onFTUriResponse(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract onRCSDBReady(Ljava/lang/String;)V
.end method

.method public abstract onRegistered(Lcom/sec/ims/ImsRegistration;)V
.end method

.method public abstract onUIButtonProceed(Ljava/lang/String;ILjava/lang/String;)Z
.end method

.method public abstract onUserEnterApp(Ljava/lang/String;)V
.end method

.method public abstract onUserLeaveApp(Ljava/lang/String;)V
.end method

.method public abstract readMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract receivedMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract registerCallback(Ljava/lang/String;Lcom/sec/ims/ICentralMsgStoreService;)V
.end method

.method public abstract registerCmsProvisioningListenerByPhoneId(Lcom/sec/ims/ICentralMsgStoreServiceListener;I)V
.end method

.method public abstract requestMessageProcess(Ljava/lang/String;Ljava/lang/String;I)V
.end method

.method public abstract requestOperation(IILjava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract restartService(Ljava/lang/String;)V
.end method

.method public abstract resumeSync(Ljava/lang/String;)V
.end method

.method public abstract sendTryDeregisterCms(I)V
.end method

.method public abstract sendTryRegisterCms(ILjava/lang/String;)V
.end method

.method public abstract sentMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract startContactSyncActivity(IZ)V
.end method

.method public abstract startDeltaSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract startFullSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract stopSync(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract unReadMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract unregisterCmsProvisioningListenerByPhoneId(Lcom/sec/ims/ICentralMsgStoreServiceListener;I)V
.end method

.method public abstract updateAccountInfo(ILjava/lang/String;)V
.end method

.method public abstract uploadMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract wipeOutMessage(Ljava/lang/String;Ljava/lang/String;)V
.end method

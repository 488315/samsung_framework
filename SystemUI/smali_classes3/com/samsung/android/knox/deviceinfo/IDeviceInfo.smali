.class public interface abstract Lcom/samsung/android/knox/deviceinfo/IDeviceInfo;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/deviceinfo/IDeviceInfo$Stub;,
        Lcom/samsung/android/knox/deviceinfo/IDeviceInfo$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.deviceinfo.IDeviceInfo"


# virtual methods
.method public abstract clearCallingLog(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearMMSLog(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearSMSLog(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract dataUsageTimerActivation(Lcom/samsung/android/knox/ContextInfo;)V
.end method

.method public abstract enableCallingCapture(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract enableMMSCapture(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract enableSMSCapture(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract getAvailableCapacityExternal(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getAvailableCapacityInternal(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getAvailableRamMemory(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getBytesReceivedNetwork(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getBytesReceivedWiFi(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getBytesSentNetwork(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getBytesSentWiFi(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getCellTowerCID(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getCellTowerLAC(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getCellTowerPSC(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getCellTowerRSSI(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDataCallLog(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getDataCallLoggingEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getDataCallStatisticsEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getDataUsageTimer(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getDeviceMaker(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDeviceName(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDeviceOS(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDeviceOSVersion(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDevicePlatform(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDeviceProcessorSpeed(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDeviceProcessorType(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getDroppedCallsCount(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getInboundMMSCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getInboundSMSCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getIncomingCallingCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getKnoxServiceId(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getKnoxServicePackageList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getMissedCallsCount(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getModelName(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getModelNumber(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getModemFirmware(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getOutboundMMSCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getOutboundSMSCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getOutgoingCallingCaptured(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPlatformSDK(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getPlatformVersion(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getPlatformVersionName(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getSalesCode(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getSerialNumber(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getSuccessCallsCount(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getTotalCapacityExternal(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getTotalCapacityInternal(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getTotalRamMemory(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract getWifiStatisticEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isCallingCaptureEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isDeviceLocked(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isDeviceSecure(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isMMSCaptureEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSMSCaptureEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract resetCallsCount(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract resetDataCallLogging(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract resetDataUsage(Lcom/samsung/android/knox/ContextInfo;)V
.end method

.method public abstract setDataCallLoggingEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setDataCallStatisticsEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setDataUsageTimer(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setKnoxServiceId(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;Ljava/lang/String;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/lang/String;",
            ")Z"
        }
    .end annotation
.end method

.method public abstract setWifiStatisticEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract storeCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
.end method

.method public abstract storeMMS(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
.end method

.method public abstract storeSMS(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
.end method

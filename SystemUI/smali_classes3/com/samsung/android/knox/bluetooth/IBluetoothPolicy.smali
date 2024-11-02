.class public interface abstract Lcom/samsung/android/knox/bluetooth/IBluetoothPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/bluetooth/IBluetoothPolicy$Stub;,
        Lcom/samsung/android/knox/bluetooth/IBluetoothPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.bluetooth.IBluetoothPolicy"


# virtual methods
.method public abstract activateBluetoothDeviceRestriction(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract activateBluetoothUUIDRestriction(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract addBluetoothDevicesToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract addBluetoothDevicesToWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract addBluetoothUUIDsToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract addBluetoothUUIDsToWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract allowBLE(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowBluetooth(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowCallerIDDisplay(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowOutgoingCalls(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract bluetoothLog(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract clearBluetoothDevicesFromBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearBluetoothDevicesFromWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearBluetoothUUIDsFromBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearBluetoothUUIDsFromWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getAllBluetoothDevicesBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/bluetooth/BluetoothControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllBluetoothDevicesWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/bluetooth/BluetoothControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllBluetoothUUIDsBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/bluetooth/BluetoothControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllBluetoothUUIDsWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/bluetooth/BluetoothControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllowBluetoothDataTransfer(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract getBluetoothLog(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getEffectiveBluetoothDevicesBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getEffectiveBluetoothDevicesWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getEffectiveBluetoothUUIDsBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getEffectiveBluetoothUUIDsWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract isBLEAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBluetoothDeviceAllowed(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isBluetoothDeviceRestrictionActive(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBluetoothEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBluetoothEnabledWithMsg(Z)Z
.end method

.method public abstract isBluetoothLogEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBluetoothUUIDAllowed(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isBluetoothUUIDRestrictionActive(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isCallerIDDisplayAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isDesktopConnectivityEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isDiscoverableEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isLimitedDiscoverableEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isOutgoingCallsAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isPairingEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isProfileEnabled(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract isProfileEnabledInternal(IZ)Z
.end method

.method public abstract removeBluetoothDevicesFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract removeBluetoothDevicesFromWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract removeBluetoothUUIDsFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract removeBluetoothUUIDsFromWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract setAllowBluetoothDataTransfer(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setBluetooth(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setBluetoothLogEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setDesktopConnectivityState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setDiscoverableState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setLimitedDiscoverableState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setPairingState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setProfileState(Lcom/samsung/android/knox/ContextInfo;ZI)Z
.end method

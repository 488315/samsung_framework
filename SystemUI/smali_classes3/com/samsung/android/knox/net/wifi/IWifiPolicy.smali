.class public interface abstract Lcom/samsung/android/knox/net/wifi/IWifiPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/net/wifi/IWifiPolicy$Stub;,
        Lcom/samsung/android/knox/net/wifi/IWifiPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.net.wifi.IWifiPolicy"


# virtual methods
.method public abstract activateWifiSsidRestriction(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract addNetworkWithRandomizationState(Landroid/net/wifi/WifiConfiguration;Z)I
.end method

.method public abstract addWifiSsidToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addWifiSsidToWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract allowOpenWifiAp(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowWifiApSettingUserModification(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowWifiScanning(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract clearWifiSsidBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearWifiSsidWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getAllWifiSsidBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/net/wifi/WifiControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllWifiSsidWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/net/wifi/WifiControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllowUserPolicyChanges(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getAllowUserProfiles(Lcom/samsung/android/knox/ContextInfo;ZI)Z
.end method

.method public abstract getAutomaticConnectionToWifi(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getBlockedNetworks(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getMinimumRequiredSecurity(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getNetworkSSIDList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPasswordHidden(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getPromptCredentialsEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getWifiApSetting(Lcom/samsung/android/knox/ContextInfo;)Landroid/net/wifi/WifiConfiguration;
.end method

.method public abstract getWifiProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Lcom/samsung/android/knox/net/wifi/WifiAdminProfile;
.end method

.method public abstract isOpenWifiApAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isWifiAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isWifiApSettingUserModificationAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isWifiScanningAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isWifiSsidRestrictionActive(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isWifiStateChangeAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract removeBlockedNetwork(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeNetworkConfiguration(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeWifiSsidFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removeWifiSsidFromWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract resetAutomaticConnectionPolicy(I)V
.end method

.method public abstract setAllowUserPolicyChanges(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setAllowUserProfiles(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setAutomaticConnectionToWifi(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setMinimumRequiredSecurity(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setPasswordHidden(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setPromptCredentialsEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setWifi(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setWifiAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setWifiApSetting(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract setWifiProfile(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/net/wifi/WifiAdminProfile;)Z
.end method

.method public abstract setWifiStateChangeAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

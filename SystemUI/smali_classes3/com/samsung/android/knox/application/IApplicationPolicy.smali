.class public interface abstract Lcom/samsung/android/knox/application/IApplicationPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/application/IApplicationPolicy$Stub;,
        Lcom/samsung/android/knox/application/IApplicationPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.application.IApplicationPolicy"


# virtual methods
.method public abstract addAppNotificationBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addAppNotificationWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addAppPackageNameToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract addAppPackageNameToWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract addAppPermissionToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract addAppSignatureToBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract addAppSignatureToWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract addApplicationToCameraAllowList(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract addHomeShortcut(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract addPackageToBatteryOptimizationWhiteList(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract addPackageToBlackList(Lcom/samsung/android/knox/ContextInfo;ILcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract addPackageToWhiteList(Lcom/samsung/android/knox/ContextInfo;ILcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract addPackagesToClearCacheBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToClearCacheWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToClearDataBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToClearDataWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToDisableClipboardBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToDisableClipboardWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToDisableUpdateBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToDisableUpdateWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToFocusMonitoringList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToForceStopBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToForceStopWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToPreventStartBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract addPackagesToWidgetBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addPackagesToWidgetWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract addUsbDevicesForDefaultAccess(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/UsbDeviceConfig;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract applicationUsageAppLaunchCount(Ljava/lang/String;I)V
.end method

.method public abstract applicationUsageAppPauseTime(Ljava/lang/String;I)V
.end method

.method public abstract applyRuntimePermissions(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/AppIdentity;Ljava/util/List;I)I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Lcom/samsung/android/knox/AppIdentity;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;I)I"
        }
    .end annotation
.end method

.method public abstract changeApplicationIcon(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;[B)Z
.end method

.method public abstract changeApplicationName(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract clearDisableClipboardBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearDisableClipboardWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearDisableUpdateBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearDisableUpdateWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearFocusMonitoringList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearPackagesFromExternalStorageWhiteList(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract clearPreventStartBlackList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract clearUsbDevicesForDefaultAccess(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract createIntentFilter(Landroid/content/Intent;)Landroid/content/IntentFilter;
.end method

.method public abstract deleteHomeShortcut(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract deleteManagedAppInfo(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract doSelfUninstall(Lcom/samsung/android/knox/ContextInfo;)V
.end method

.method public abstract enableOcspCheck(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract enableRevocationCheck(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract getAddHomeShorcutRequested()Z
.end method

.method public abstract getAllAppLastUsage(Lcom/samsung/android/knox/ContextInfo;)[Lcom/samsung/android/knox/application/AppInfoLastUsage;
.end method

.method public abstract getAllDefaultApplications(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/DefaultAppConfiguration;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllDefaultApplicationsInternal(I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/DefaultAppConfiguration;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllPackagesFromBatteryOptimizationWhiteList()Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAllWidgets(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/Map;
.end method

.method public abstract getAppInstallToSdCard(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract getAppInstallationMode(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getAppNotificationBlackList(Lcom/samsung/android/knox/ContextInfo;Z)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Z)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppNotificationWhiteList(Lcom/samsung/android/knox/ContextInfo;Z)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Z)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppPackageNamesAllBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppPackageNamesAllWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppPermissionsAllBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppPermissionsBlackList(Lcom/samsung/android/knox/ContextInfo;)[Ljava/lang/String;
.end method

.method public abstract getAppSignatureBlackList(Lcom/samsung/android/knox/ContextInfo;)[Ljava/lang/String;
.end method

.method public abstract getAppSignaturesAllBlackLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppSignaturesAllWhiteLists(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppControlInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getAppSignaturesWhiteList(Lcom/samsung/android/knox/ContextInfo;)[Ljava/lang/String;
.end method

.method public abstract getApplicationCacheSize(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationCodeSize(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationComponentState(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)Z
.end method

.method public abstract getApplicationCpuUsage(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationDataSize(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationGrantedPermissions(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
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

.method public abstract getApplicationIconFromDb(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)[B
.end method

.method public abstract getApplicationInstallationEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract getApplicationMemoryUsage(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationName(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract getApplicationNameFromDb(Ljava/lang/String;I)Ljava/lang/String;
.end method

.method public abstract getApplicationNotificationMode(Lcom/samsung/android/knox/ContextInfo;Z)I
.end method

.method public abstract getApplicationNotificationModeAsUser(I)I
.end method

.method public abstract getApplicationPackagesFromCameraAllowList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getApplicationStateEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract getApplicationStateEnabledAsUser(Ljava/lang/String;ZI)Z
.end method

.method public abstract getApplicationStateList(Lcom/samsung/android/knox/ContextInfo;Z)[Ljava/lang/String;
.end method

.method public abstract getApplicationTotalSize(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract getApplicationUid(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)I
.end method

.method public abstract getApplicationUninstallationEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract getApplicationUninstallationEnabledAsUser(Ljava/lang/String;I)Z
.end method

.method public abstract getApplicationUninstallationMode(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getApplicationVersion(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract getApplicationVersionCode(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)I
.end method

.method public abstract getApplicationsList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)[Lcom/samsung/android/knox/application/ManagedAppInfo;
.end method

.method public abstract getAvgNoAppUsagePerMonth(Lcom/samsung/android/knox/ContextInfo;)[Lcom/samsung/android/knox/application/AppInfoLastUsage;
.end method

.method public abstract getConcentrationMode()Z
.end method

.method public abstract getDefaultApplication(Lcom/samsung/android/knox/ContextInfo;Landroid/content/Intent;)Landroid/content/ComponentName;
.end method

.method public abstract getDefaultApplicationInternal(Landroid/content/Intent;I)Landroid/content/ComponentName;
.end method

.method public abstract getDisabledPackages(I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getHomeShortcuts(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            "Z)",
            "Ljava/util/List<",
            "Landroid/content/ComponentName;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getInstalledApplicationsIDList(Lcom/samsung/android/knox/ContextInfo;)[Ljava/lang/String;
.end method

.method public abstract getInstalledManagedApplicationsList(Lcom/samsung/android/knox/ContextInfo;)[Ljava/lang/String;
.end method

.method public abstract getNetworkStats(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/NetworkStats;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPackageSignaturesFromExternalStorageWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)[Landroid/content/pm/Signature;
.end method

.method public abstract getPackagesFromBatteryOptimizationWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromBlackList(Lcom/samsung/android/knox/ContextInfo;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPackagesFromClearCacheBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromClearCacheWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromClearDataBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromClearDataWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromDisableClipboardBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromDisableClipboardBlackListAsUserInternal(Lcom/samsung/android/knox/ContextInfo;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPackagesFromDisableClipboardWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromDisableClipboardWhiteListAsUserInternal(Lcom/samsung/android/knox/ContextInfo;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPackagesFromDisableUpdateBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromDisableUpdateWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromFocusMonitoringList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromForceStopBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromForceStopWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromPreventStartBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromWhiteList(Lcom/samsung/android/knox/ContextInfo;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getPackagesFromWidgetBlackList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getPackagesFromWidgetWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getRuntimePermissions(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getRuntimePermissionsEnforced(ILjava/lang/String;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/lang/String;",
            "I)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getTopNCPUUsageApp(Lcom/samsung/android/knox/ContextInfo;IZ)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "IZ)",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getTopNDataUsageApp(Lcom/samsung/android/knox/ContextInfo;I)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "I)",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getTopNMemoryUsageApp(Lcom/samsung/android/knox/ContextInfo;IZ)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "IZ)",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/AppInfo;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getUsbDevicesForDefaultAccess(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/application/UsbDeviceConfig;",
            ">;"
        }
    .end annotation
.end method

.method public abstract handleStatusBarNotificationNotAllowedAsUser(Ljava/lang/String;ILandroid/app/Notification;)Z
.end method

.method public abstract installApplication(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;ZLandroid/os/ParcelFileDescriptor;)Z
.end method

.method public abstract installExistingApplication(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isAnyApplicationIconChangedAsUser(I)Z
.end method

.method public abstract isAnyApplicationNameChangedAsUser(I)Z
.end method

.method public abstract isApplicationClearCacheDisabled(Ljava/lang/String;IZ)Z
.end method

.method public abstract isApplicationClearDataDisabled(Ljava/lang/String;IZ)Z
.end method

.method public abstract isApplicationExternalStorageBlacklisted(ILjava/lang/String;)Z
.end method

.method public abstract isApplicationExternalStorageWhitelisted(ILjava/lang/String;)Z
.end method

.method public abstract isApplicationFocusMonitoredAsUser(Ljava/lang/String;I)Z
.end method

.method public abstract isApplicationForceStopDisabled(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Z
.end method

.method public abstract isApplicationInstallationEnabled(Ljava/lang/String;[Landroid/content/pm/Signature;Ljava/util/List;I)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "[",
            "Landroid/content/pm/Signature;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;I)Z"
        }
    .end annotation
.end method

.method public abstract isApplicationInstalled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isApplicationRunning(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isApplicationSetToDefault(Ljava/lang/String;I)Z
.end method

.method public abstract isApplicationStartDisabledAsUser(Ljava/lang/String;I)Z
.end method

.method public abstract isCameraAllowlistedApp(II)Z
.end method

.method public abstract isFromApprovedInstaller(II)Z
.end method

.method public abstract isIntentDisabled(Landroid/content/Intent;)Z
.end method

.method public abstract isOcspCheckEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isPackageClipboardAllowed(Ljava/lang/String;I)Z
.end method

.method public abstract isPackageInApprovedInstallerWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isPackageInBlacklistInternal(III)Z
.end method

.method public abstract isPackageInWhitelistInternal(III)Z
.end method

.method public abstract isPackageUpdateAllowed(Ljava/lang/String;Z)Z
.end method

.method public abstract isRevocationCheckEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isStatusBarNotificationAllowedAsUser(Ljava/lang/String;I)Z
.end method

.method public abstract isUsbDevicePermittedForPackage(ILandroid/hardware/usb/UsbDevice;Ljava/lang/String;)Z
.end method

.method public abstract isWidgetAllowed(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract reapplyRuntimePermissions(I)V
.end method

.method public abstract removeAppNotificationBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removeAppNotificationWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removeAppPackageNameFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeAppPackageNameFromWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeAppPermissionFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeAppSignatureFromBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeAppSignatureFromWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeApplicationFromCameraAllowList(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract removeDefaultApplication(Lcom/samsung/android/knox/ContextInfo;Landroid/content/Intent;Landroid/content/ComponentName;)Z
.end method

.method public abstract removeManagedApplications(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public abstract removePackageFromBatteryOptimizationWhiteList(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/AppIdentity;)I
.end method

.method public abstract removePackageFromBlackList(Lcom/samsung/android/knox/ContextInfo;ILjava/lang/String;)I
.end method

.method public abstract removePackageFromWhiteList(Lcom/samsung/android/knox/ContextInfo;ILjava/lang/String;)I
.end method

.method public abstract removePackagesFromClearCacheBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromClearCacheWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromClearDataBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromClearDataWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromDisableClipboardBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromDisableClipboardWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromDisableUpdateBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromDisableUpdateWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromFocusMonitoringList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromForceStopBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromForceStopWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromPreventStartBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromWidgetBlackList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract removePackagesFromWidgetWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract setAndroidMarketState(Lcom/samsung/android/knox/ContextInfo;Z)V
.end method

.method public abstract setAppInstallToSdCard(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setAppInstallationMode(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setApplicationComponentState(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;Z)Z
.end method

.method public abstract setApplicationInstallationDisabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)V
.end method

.method public abstract setApplicationNotificationMode(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setApplicationState(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract setApplicationStateList(Lcom/samsung/android/knox/ContextInfo;[Ljava/lang/String;Z)[Ljava/lang/String;
.end method

.method public abstract setApplicationUninstallationDisabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)V
.end method

.method public abstract setApplicationUninstallationMode(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setAsManagedApp(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract setConcentrationMode(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;Z)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;Z)Z"
        }
    .end annotation
.end method

.method public abstract setDefaultApplication(Lcom/samsung/android/knox/ContextInfo;Landroid/content/Intent;Landroid/content/ComponentName;)Z
.end method

.method public abstract startApp(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract stopApp(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract uninstallApplication(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract updateApplicationTable(III)Z
.end method

.method public abstract updateDataUsageDb()V
.end method

.method public abstract updateWidgetStatus(Landroid/content/ComponentName;I)V
.end method

.method public abstract verifyRuntimePermissionPackageSignature(Ljava/lang/String;)Z
.end method

.method public abstract wipeApplicationData(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

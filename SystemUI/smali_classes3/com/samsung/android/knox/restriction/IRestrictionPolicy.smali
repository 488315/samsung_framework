.class public interface abstract Lcom/samsung/android/knox/restriction/IRestrictionPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/restriction/IRestrictionPolicy$Stub;,
        Lcom/samsung/android/knox/restriction/IRestrictionPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.restriction.IRestrictionPolicy"


# virtual methods
.method public abstract addNewAdminActivationAppWhiteList(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract allowActivationLock(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowAirplaneMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowAudioRecord(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowBackgroundProcessLimit(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowClipboardShare(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowDataSaving(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowDeveloperMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowFaceRecognitionEvenCameraBlocked(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowFactoryReset(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowFastEncryption(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowFirmwareAutoUpdate(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowFirmwareRecovery(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowGoogleAccountsAutoSync(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowGoogleCrashReport(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowIntelligenceOnlineProcessing(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowKillingActivitiesOnLeave(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowLocalContactStorage(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowLockScreenView(Lcom/samsung/android/knox/ContextInfo;IZ)Z
.end method

.method public abstract allowOTAUpgrade(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowPowerOff(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowPowerSavingMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSDCardMove(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSDCardWrite(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSVoice(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSafeMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowScreenPinning(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSettingsChanges(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowShareList(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowSmartClipMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowStatusBarExpansion(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowStopSystemApp(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowUsbHostStorage(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowUserMobileDataLimit(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowVideoRecord(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowVpn(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowWallpaperChange(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract allowWifiDirect(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract checkAdminActivationEnabled(ILjava/lang/String;)Z
.end method

.method public abstract checkIfRestrictionWasSetByKC(Ljava/lang/String;)Z
.end method

.method public abstract checkPackageSource(ILjava/lang/String;)Z
.end method

.method public abstract clearNewAdminActivationAppWhiteList(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract disableConstrainedState(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract enableConstrainedState(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Z
.end method

.method public abstract enableODETrustedBootVerification(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract enableWearablePolicy(Lcom/samsung/android/knox/ContextInfo;IZ)Z
.end method

.method public abstract getAllowedFOTAInfo(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getAllowedFOTAVersion(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getCCModeState(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getConstrainedState()I
.end method

.method public abstract getKcActionDisabledText()Ljava/lang/String;
.end method

.method public abstract getNewAdminActivationAppWhiteList(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
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

.method public abstract getUsbExceptionList()I
.end method

.method public abstract isActivationLockAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isAirplaneModeAllowed(Z)Z
.end method

.method public abstract isAudioRecordAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isBackgroundDataEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBackgroundProcessLimitAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBackupAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isBluetoothTetheringEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isCCModeEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isCCModeSupported(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isCameraEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isCellularDataAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isClipboardAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isClipboardAllowedAsUser(ZI)Z
.end method

.method public abstract isClipboardShareAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isClipboardShareAllowedAsUser(I)Z
.end method

.method public abstract isDataSavingAllowed()Z
.end method

.method public abstract isDeveloperModeAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isFaceRecognitionAllowedEvenCameraBlocked(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isFactoryResetAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isFastEncryptionAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isFirmwareAutoUpdateAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isFirmwareRecoveryAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isGoogleAccountsAutoSyncAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isGoogleAccountsAutoSyncAllowedAsUser(I)Z
.end method

.method public abstract isGoogleCrashReportAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isGoogleCrashReportAllowedAsUser(I)Z
.end method

.method public abstract isHeadphoneEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isHomeKeyEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isIntelligenceOnlineProcessingAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isIrisCameraEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isKillingActivitiesOnLeaveAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isKnoxDelegationEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isLocalContactStorageAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isLockScreenEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isLockScreenViewAllowed(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract isMicrophoneEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isMicrophoneEnabledAsUser(ZI)Z
.end method

.method public abstract isMockLocationEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isNewAdminActivationEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isNewAdminInstallationEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isNewAdminInstallationEnabledAsUser(IZ)Z
.end method

.method public abstract isNonMarketAppAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isNonTrustedAppInstallBlocked(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isNonTrustedAppInstallBlockedAsUser(I)Z
.end method

.method public abstract isODETrustedBootVerificationEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isOTAUpgradeAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isPowerOffAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isPowerSavingModeAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSDCardMoveAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isSDCardWriteAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSVoiceAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isSVoiceAllowedAsUser(ZI)Z
.end method

.method public abstract isSafeModeAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isScreenCaptureEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isScreenCaptureEnabledEx(IZ)Z
.end method

.method public abstract isScreenCaptureEnabledInternal(Z)Z
.end method

.method public abstract isScreenPinningAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSdCardEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSettingsChangesAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isSettingsChangesAllowedAsUser(ZI)Z
.end method

.method public abstract isShareListAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isShareListAllowedAsUser(IZ)Z
.end method

.method public abstract isSmartClipModeAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isSmartClipModeAllowedInternal(Z)Z
.end method

.method public abstract isStatusBarExpansionAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isStatusBarExpansionAllowedAsUser(ZI)Z
.end method

.method public abstract isStopSystemAppAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isTetheringEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isUsbDebuggingEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isUsbHostStorageAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isUsbKiesAvailable(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isUsbMassStorageEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isUsbMediaPlayerAvailable(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isUsbTetheringEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isUseSecureKeypadEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isUserMobileDataLimitAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isVideoRecordAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isVpnAllowed(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isWallpaperChangeAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isWearablePolicyEnabled(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract isWifiDirectAllowed(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract isWifiTetheringEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract preventNewAdminActivation(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract preventNewAdminInstallation(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setAllowNonMarketApps(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setAllowedFOTAVersion(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Landroid/os/Bundle;Z)Z
.end method

.method public abstract setBackgroundData(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setBackup(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setBluetoothTethering(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setCCMode(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setCCModeOnlyForCallerSystem(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setCamera(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setCellularData(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setClipboardEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setHeadphoneState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setHomeKeyState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setIrisCameraState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setKnoxDelegationEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setLockScreenState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setMicrophoneState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setMockLocation(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setNonTrustedAppInstallBlock(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setScreenCapture(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setSdCardState(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setTethering(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUsbDebuggingEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUsbExceptionList(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setUsbKiesAvailability(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUsbMassStorage(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUsbMediaPlayerAvailability(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUsbTethering(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setUseSecureKeypad(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setWifiTethering(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract showRestrictionToast(Ljava/lang/String;)V
.end method

.method public abstract systemReady(I)V
.end method

.method public abstract updateUserRestrictionsByKC(Ljava/lang/String;Z)V
.end method

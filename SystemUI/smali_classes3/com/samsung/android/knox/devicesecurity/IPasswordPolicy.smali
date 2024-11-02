.class public interface abstract Lcom/samsung/android/knox/devicesecurity/IPasswordPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/devicesecurity/IPasswordPolicy$Stub;,
        Lcom/samsung/android/knox/devicesecurity/IPasswordPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.devicesecurity.IPasswordPolicy"


# virtual methods
.method public abstract addRequiredPasswordPattern(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract clearResetPasswordToken(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)Z
.end method

.method public abstract deleteAllRestrictions(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract enforcePwdChange(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract excludeExternalStorageForFailedPasswordsWipe(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract getCurrentFailedPasswordAttempts(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getCurrentFailedPasswordAttemptsInternal(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getForbiddenStrings(Lcom/samsung/android/knox/ContextInfo;Z)Ljava/util/List;
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

.method public abstract getKeyguardDisabledFeatures(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getKeyguardDisabledFeaturesInternal(Landroid/content/ComponentName;I)I
.end method

.method public abstract getMaximumCharacterOccurences(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getMaximumCharacterSequenceLength(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getMaximumFailedPasswordsForDisable(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getMaximumFailedPasswordsForWipe(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getMaximumNumericSequenceLength(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getMaximumTimeToLock(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)J
.end method

.method public abstract getMinimumCharacterChangeLength(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getPasswordChangeTimeout(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getPasswordExpiration(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)J
.end method

.method public abstract getPasswordExpirationTimeout(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)J
.end method

.method public abstract getPasswordHistoryLength(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordLockDelay(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract getPasswordMinimumLength(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumLetters(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumLowerCase(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumNonLetter(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumNumeric(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumSymbols(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordMinimumUpperCase(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getPasswordQuality(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)I
.end method

.method public abstract getRequiredPwdPatternRestrictions(Lcom/samsung/android/knox/ContextInfo;Z)Ljava/lang/String;
.end method

.method public abstract getSupportedBiometricAuthentications(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/Map;
.end method

.method public abstract hasForbiddenCharacterSequence(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract hasForbiddenData(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract hasForbiddenNumericSequence(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract hasForbiddenStringDistance(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract hasMaxRepeatedCharacters(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isActivePasswordSufficient(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isBiometricAuthenticationEnabled(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract isBiometricAuthenticationEnabledAsUser(II)Z
.end method

.method public abstract isChangeRequested(Lcom/samsung/android/knox/ContextInfo;)I
.end method

.method public abstract isChangeRequestedAsUser(I)I
.end method

.method public abstract isChangeRequestedForInner()I
.end method

.method public abstract isClearLockAllowed()Z
.end method

.method public abstract isExternalStorageForFailedPasswordsWipeExcluded(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isMDMDisabledFP(I)Z
.end method

.method public abstract isMultifactorAuthenticationEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isPasswordPatternMatched(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isPasswordTableExist(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isPasswordVisibilityEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract isPasswordVisibilityEnabledAsUser(I)Z
.end method

.method public abstract isResetPasswordTokenActive(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;)Z
.end method

.method public abstract isScreenLockPatternVisibilityEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract lock(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract reboot(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V
.end method

.method public abstract resetPassword(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;I)Z
.end method

.method public abstract resetPasswordWithToken(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;Ljava/lang/String;[BI)Z
.end method

.method public abstract setBiometricAuthenticationEnabled(Lcom/samsung/android/knox/ContextInfo;IZ)Z
.end method

.method public abstract setForbiddenStrings(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract setKeyguardDisabledFeatures(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setKeyguardDisabledFeaturesInternal(Landroid/content/ComponentName;II)V
.end method

.method public abstract setMaximumCharacterOccurrences(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setMaximumCharacterSequenceLength(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setMaximumFailedPasswordsForDisable(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setMaximumFailedPasswordsForWipe(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setMaximumNumericSequenceLength(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setMaximumTimeToLock(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;J)V
.end method

.method public abstract setMinimumCharacterChangeLength(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setMultifactorAuthenticationEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setPasswordChangeTimeout(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setPasswordExpirationTimeout(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;J)V
.end method

.method public abstract setPasswordHistoryLength(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordLockDelay(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setPasswordMinimumLength(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumLetters(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumLowerCase(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumNonLetter(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumNumeric(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumSymbols(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordMinimumUpperCase(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordQuality(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;I)V
.end method

.method public abstract setPasswordVisibilityEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setPwdChangeRequested(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract setPwdChangeRequestedForInner(I)Z
.end method

.method public abstract setRequiredPasswordPattern(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract setResetPasswordToken(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;[B)Z
.end method

.method public abstract setScreenLockPatternVisibilityEnabled(Lcom/samsung/android/knox/ContextInfo;Z)Z
.end method

.method public abstract setTrustAgentConfiguration(Lcom/samsung/android/knox/ContextInfo;Landroid/content/ComponentName;Landroid/content/ComponentName;Landroid/os/PersistableBundle;)V
.end method

.method public abstract unlock(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

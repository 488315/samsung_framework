.class public interface abstract Lcom/samsung/android/knox/accounts/IExchangeAccountPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/accounts/IExchangeAccountPolicy$Stub;,
        Lcom/samsung/android/knox/accounts/IExchangeAccountPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.accounts.IExchangeAccountPolicy"


# virtual methods
.method public abstract addNewAccount(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;ZZZLjava/lang/String;Ljava/lang/String;)J
.end method

.method public abstract addNewAccount_ex(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;ZZZLjava/lang/String;Ljava/lang/String;IIIIIIIZII[BLjava/lang/String;)J
.end method

.method public abstract addNewAccount_new(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/accounts/ExchangeAccount;)J
.end method

.method public abstract allowEmailSettingsChange(Lcom/samsung/android/knox/ContextInfo;JZ)Z
.end method

.method public abstract allowInComingAttachments(Lcom/samsung/android/knox/ContextInfo;ZJ)Z
.end method

.method public abstract createAccount(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
.end method

.method public abstract deleteAccount(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getAccountCertificatePassword(Lcom/samsung/android/knox/ContextInfo;J)Ljava/lang/String;
.end method

.method public abstract getAccountDetails(Lcom/samsung/android/knox/ContextInfo;J)Lcom/samsung/android/knox/accounts/Account;
.end method

.method public abstract getAccountEmailPassword(Lcom/samsung/android/knox/ContextInfo;J)Ljava/lang/String;
.end method

.method public abstract getAccountId(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
.end method

.method public abstract getAllEASAccounts(Lcom/samsung/android/knox/ContextInfo;)[Lcom/samsung/android/knox/accounts/Account;
.end method

.method public abstract getDeviceId(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;
.end method

.method public abstract getForceSMIMECertificate(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getForceSMIMECertificateForEncryption(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getForceSMIMECertificateForSigning(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getIncomingAttachmentsSize(Lcom/samsung/android/knox/ContextInfo;J)I
.end method

.method public abstract getMaxCalendarAgeFilter(Lcom/samsung/android/knox/ContextInfo;J)I
.end method

.method public abstract getMaxEmailAgeFilter(Lcom/samsung/android/knox/ContextInfo;J)I
.end method

.method public abstract getMaxEmailBodyTruncationSize(Lcom/samsung/android/knox/ContextInfo;J)I
.end method

.method public abstract getMaxEmailHTMLBodyTruncationSize(Lcom/samsung/android/knox/ContextInfo;J)I
.end method

.method public abstract getRequireEncryptedSMIMEMessages(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getRequireSignedSMIMEMessages(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract getSMIMECertificateAlias(Lcom/samsung/android/knox/ContextInfo;JI)Ljava/lang/String;
.end method

.method public abstract isEmailNotificationsEnabled(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract isEmailSettingsChangeAllowed(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract isIncomingAttachmentsAllowed(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract removePendingAccount(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract sendAccountsChangedBroadcast(Lcom/samsung/android/knox/ContextInfo;)V
.end method

.method public abstract setAcceptAllCertificates(Lcom/samsung/android/knox/ContextInfo;ZJ)Z
.end method

.method public abstract setAccountBaseParameters(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)J
.end method

.method public abstract setAccountCertificatePassword(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract setAccountEmailPassword(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)J
.end method

.method public abstract setAccountName(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;J)Z
.end method

.method public abstract setAlwaysVibrateOnEmailNotification(Lcom/samsung/android/knox/ContextInfo;ZJ)Z
.end method

.method public abstract setAsDefaultAccount(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract setClientAuthCert(Lcom/samsung/android/knox/ContextInfo;[BLjava/lang/String;J)V
.end method

.method public abstract setDataSyncs(Lcom/samsung/android/knox/ContextInfo;ZZZZJ)Z
.end method

.method public abstract setEmailNotificationsState(Lcom/samsung/android/knox/ContextInfo;JZ)Z
.end method

.method public abstract setForceSMIMECertificate(Lcom/samsung/android/knox/ContextInfo;JLjava/lang/String;Ljava/lang/String;)I
.end method

.method public abstract setForceSMIMECertificateAlias(Lcom/samsung/android/knox/ContextInfo;JLjava/lang/String;Ljava/lang/String;I)Z
.end method

.method public abstract setForceSMIMECertificateForEncryption(Lcom/samsung/android/knox/ContextInfo;JLjava/lang/String;Ljava/lang/String;)I
.end method

.method public abstract setForceSMIMECertificateForSigning(Lcom/samsung/android/knox/ContextInfo;JLjava/lang/String;Ljava/lang/String;)I
.end method

.method public abstract setIncomingAttachmentsSize(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setMaxCalendarAgeFilter(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setMaxEmailAgeFilter(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setMaxEmailBodyTruncationSize(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setMaxEmailHTMLBodyTruncationSize(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setPassword(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;J)Z
.end method

.method public abstract setPastDaysToSync(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setProtocolVersion(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;J)Z
.end method

.method public abstract setReleaseSMIMECertificate(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract setReleaseSMIMECertificateForEncryption(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract setReleaseSMIMECertificateForSigning(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract setRequireEncryptedSMIMEMessages(Lcom/samsung/android/knox/ContextInfo;JZ)Z
.end method

.method public abstract setRequireSignedSMIMEMessages(Lcom/samsung/android/knox/ContextInfo;JZ)Z
.end method

.method public abstract setSSL(Lcom/samsung/android/knox/ContextInfo;ZJ)Z
.end method

.method public abstract setSenderName(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;J)Z
.end method

.method public abstract setSignature(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;J)Z
.end method

.method public abstract setSilentVibrateOnEmailNotification(Lcom/samsung/android/knox/ContextInfo;ZJ)Z
.end method

.method public abstract setSyncInterval(Lcom/samsung/android/knox/ContextInfo;IJ)Z
.end method

.method public abstract setSyncPeakTimings(Lcom/samsung/android/knox/ContextInfo;IIIJ)Z
.end method

.method public abstract setSyncSchedules(Lcom/samsung/android/knox/ContextInfo;IIIJ)Z
.end method

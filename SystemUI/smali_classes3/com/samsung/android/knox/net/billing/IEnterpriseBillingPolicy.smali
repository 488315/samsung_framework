.class public interface abstract Lcom/samsung/android/knox/net/billing/IEnterpriseBillingPolicy;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/net/billing/IEnterpriseBillingPolicy$Stub;,
        Lcom/samsung/android/knox/net/billing/IEnterpriseBillingPolicy$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy"


# virtual methods
.method public abstract activateProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract addProfile(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;)Z
.end method

.method public abstract addProfileForCurrentContainer(Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;)Z
.end method

.method public abstract addVpnToBillingProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract addVpnToBillingProfileForCurrentContainer(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract allowRoaming(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)Z
.end method

.method public abstract allowWifiFallback(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Z)V
.end method

.method public abstract disableProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract disableProfileForApps(Lcom/samsung/android/knox/ContextInfo;Ljava/util/List;)Z
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

.method public abstract disableProfileForContainer(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract disableProfileForCurrentContainer()Z
.end method

.method public abstract enableProfileForApps(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/util/List;)Z
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            "Ljava/lang/String;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)Z"
        }
    .end annotation
.end method

.method public abstract enableProfileForContainer(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract enableProfileForCurrentContainer(Ljava/lang/String;)Z
.end method

.method public abstract getApplicationsUsingProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
.end method

.method public abstract getAvailableProfiles(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
.end method

.method public abstract getAvailableProfilesForCaller()Ljava/util/List;
.end method

.method public abstract getContainersUsingProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
.end method

.method public abstract getProfileDetails(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;
.end method

.method public abstract getProfileForApplication(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;
.end method

.method public abstract getProfileForContainer(Lcom/samsung/android/knox/ContextInfo;)Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;
.end method

.method public abstract getVpnsBoundToProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Ljava/util/List;
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

.method public abstract isProfileActive(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isProfileActiveByCaller(Ljava/lang/String;)Z
.end method

.method public abstract isProfileEnabled(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isProfileTurnedOn(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isRoamingAllowed(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract isWifiFallbackAllowed(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract removeProfileForCurrentContainer(Ljava/lang/String;)Z
.end method

.method public abstract removeVpnFromBillingProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;Ljava/lang/String;)Z
.end method

.method public abstract removeVpnFromBillingProfileForCurrentContainer(Ljava/lang/String;)Z
.end method

.method public abstract turnOffProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract turnOnProfile(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z
.end method

.method public abstract updateProfile(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/net/billing/EnterpriseBillingProfile;)Z
.end method

.class public abstract Lcom/samsung/android/knox/localservice/ApplicationPolicyInternal;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public abstract getApplicationNameFromDb(Ljava/lang/String;I)Ljava/lang/String;
.end method

.method public abstract getApplicationStateEnabledAsUser(Ljava/lang/String;ZI)Z
.end method

.method public abstract isApplicationStartDisabledAsUser(Ljava/lang/String;I)Z
.end method

.method public abstract isApplicationStopDisabledAsUser(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Z
.end method

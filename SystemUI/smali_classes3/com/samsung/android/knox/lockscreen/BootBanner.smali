.class public final Lcom/samsung/android/knox/lockscreen/BootBanner;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static TAG:Ljava/lang/String; = "BootBanner"


# instance fields
.field public mContextInfo:Lcom/samsung/android/knox/ContextInfo;

.field public mService:Lcom/samsung/android/knox/ISecurityPolicy;


# direct methods
.method public constructor <init>(Lcom/samsung/android/knox/ContextInfo;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final enableRebootBanner(Z)Z
    .locals 2

    .line 5
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    const-string v1, "BootBanner.enableRebootBanner(boolean)"

    invoke-static {v0, v1}, Lcom/samsung/android/knox/license/EnterpriseLicenseManager;->log(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V

    .line 6
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    invoke-interface {v0, p0, p1}, Lcom/samsung/android/knox/ISecurityPolicy;->enableRebootBanner(Lcom/samsung/android/knox/ContextInfo;Z)Z

    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    :catch_0
    move-exception p0

    .line 8
    sget-object p1, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    const-string v0, "Failed talking with security policy"

    invoke-static {p1, v0, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public final enableRebootBanner(ZLjava/lang/String;)Z
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    const-string v1, "BootBanner.enableRebootBanner(boolean, String)"

    invoke-static {v0, v1}, Lcom/samsung/android/knox/license/EnterpriseLicenseManager;->log(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V

    .line 2
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 3
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    invoke-interface {v0, p0, p1, p2}, Lcom/samsung/android/knox/ISecurityPolicy;->enableRebootBannerWithText(Lcom/samsung/android/knox/ContextInfo;ZLjava/lang/String;)Z

    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    :catch_0
    move-exception p0

    .line 4
    sget-object p1, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    const-string p2, "Failed talking with security policy"

    invoke-static {p1, p2, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public final getDeviceLastAccessDate()Ljava/lang/String;
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0}, Lcom/samsung/android/knox/ISecurityPolicy;->getDeviceLastAccessDate(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;

    .line 12
    .line 13
    .line 14
    move-result-object p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return-object p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object v0, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v1, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return-object p0
.end method

.method public final getRebootBannerText()Ljava/lang/String;
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0}, Lcom/samsung/android/knox/ISecurityPolicy;->getRebootBannerText(Lcom/samsung/android/knox/ContextInfo;)Ljava/lang/String;

    .line 12
    .line 13
    .line 14
    move-result-object p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return-object p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object v0, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v1, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return-object p0
.end method

.method public final getService()Lcom/samsung/android/knox/ISecurityPolicy;
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    const-string v0, "security_policy"

    .line 6
    .line 7
    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    invoke-static {v0}, Lcom/samsung/android/knox/ISecurityPolicy$Stub;->asInterface(Landroid/os/IBinder;)Lcom/samsung/android/knox/ISecurityPolicy;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    iput-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 16
    .line 17
    :cond_0
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 18
    .line 19
    return-object p0
.end method

.method public final isDodBannerVisible()Z
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0}, Lcom/samsung/android/knox/ISecurityPolicy;->isDodBannerVisible(Lcom/samsung/android/knox/ContextInfo;)Z

    .line 12
    .line 13
    .line 14
    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object v0, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v1, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return p0
.end method

.method public final isRebootBannerEnabled()Z
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0}, Lcom/samsung/android/knox/ISecurityPolicy;->isRebootBannerEnabled(Lcom/samsung/android/knox/ContextInfo;)Z

    .line 12
    .line 13
    .line 14
    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object v0, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v1, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return p0
.end method

.method public final onKeyguardLaunched()V
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    invoke-interface {p0}, Lcom/samsung/android/knox/ISecurityPolicy;->onKeyguardLaunched()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 10
    .line 11
    .line 12
    goto :goto_0

    .line 13
    :catch_0
    move-exception p0

    .line 14
    sget-object v0, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 15
    .line 16
    const-string v1, "Failed talking with security policy"

    .line 17
    .line 18
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 19
    .line 20
    .line 21
    :cond_0
    :goto_0
    return-void
.end method

.method public final setDeviceLastAccessDate(Ljava/lang/String;)Z
    .locals 1

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0, p1}, Lcom/samsung/android/knox/ISecurityPolicy;->setDeviceLastAccessDate(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)Z

    .line 12
    .line 13
    .line 14
    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object p1, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v0, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {p1, v0, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return p0
.end method

.method public final setDodBannerVisibleStatus(Z)Z
    .locals 1

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/knox/lockscreen/BootBanner;->getService()Lcom/samsung/android/knox/ISecurityPolicy;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    :try_start_0
    iget-object v0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mService:Lcom/samsung/android/knox/ISecurityPolicy;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/samsung/android/knox/lockscreen/BootBanner;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 10
    .line 11
    invoke-interface {v0, p0, p1}, Lcom/samsung/android/knox/ISecurityPolicy;->setDodBannerVisibleStatus(Lcom/samsung/android/knox/ContextInfo;Z)Z

    .line 12
    .line 13
    .line 14
    move-result p0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 15
    return p0

    .line 16
    :catch_0
    move-exception p0

    .line 17
    sget-object p1, Lcom/samsung/android/knox/lockscreen/BootBanner;->TAG:Ljava/lang/String;

    .line 18
    .line 19
    const-string v0, "Failed talking with security policy"

    .line 20
    .line 21
    invoke-static {p1, v0, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 22
    .line 23
    .line 24
    :cond_0
    const/4 p0, 0x0

    .line 25
    return p0
.end method

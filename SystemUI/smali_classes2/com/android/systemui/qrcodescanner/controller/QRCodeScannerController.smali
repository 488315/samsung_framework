.class public final Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/CallbackController;


# instance fields
.field public final mCallbacks:Ljava/util/ArrayList;

.field public final mConfigEnableLockScreenButton:Z

.field public final mContext:Landroid/content/Context;

.field public final mDefaultQRCodeScannerChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

.field public final mDeviceConfigProxy:Lcom/android/systemui/util/DeviceConfigProxy;

.field public final mExecutor:Ljava/util/concurrent/Executor;

.field public mIntent:Landroid/content/Intent;

.field public mIsCameraAvailable:Ljava/lang/Boolean;

.field public mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

.field public mQRCodeScannerActivity:Ljava/lang/String;

.field public mQRCodeScannerEnabled:Z

.field public final mQRCodeScannerPreferenceChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

.field public mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

.field public final mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

.field public mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

.field public final mUserTracker:Lcom/android/systemui/settings/UserTracker;


# direct methods
.method public constructor <init>(Landroid/content/Context;Ljava/util/concurrent/Executor;Lcom/android/systemui/util/settings/SecureSettings;Lcom/android/systemui/util/DeviceConfigProxy;Lcom/android/systemui/settings/UserTracker;)V
    .locals 3

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance v0, Ljava/util/ArrayList;

    .line 5
    .line 6
    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 10
    .line 11
    new-instance v0, Ljava/util/HashMap;

    .line 12
    .line 13
    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 14
    .line 15
    .line 16
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 17
    .line 18
    const/4 v0, 0x0

    .line 19
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 20
    .line 21
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 22
    .line 23
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIntent:Landroid/content/Intent;

    .line 24
    .line 25
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 26
    .line 27
    new-instance v1, Ljava/util/concurrent/atomic/AtomicInteger;

    .line 28
    .line 29
    const/4 v2, 0x0

    .line 30
    invoke-direct {v1, v2}, Ljava/util/concurrent/atomic/AtomicInteger;-><init>(I)V

    .line 31
    .line 32
    .line 33
    iput-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 34
    .line 35
    new-instance v1, Ljava/util/concurrent/atomic/AtomicInteger;

    .line 36
    .line 37
    invoke-direct {v1, v2}, Ljava/util/concurrent/atomic/AtomicInteger;-><init>(I)V

    .line 38
    .line 39
    .line 40
    iput-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDefaultQRCodeScannerChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 41
    .line 42
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIsCameraAvailable:Ljava/lang/Boolean;

    .line 43
    .line 44
    iput-object p1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mContext:Landroid/content/Context;

    .line 45
    .line 46
    iput-object p2, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mExecutor:Ljava/util/concurrent/Executor;

    .line 47
    .line 48
    iput-object p3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

    .line 49
    .line 50
    iput-object p4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDeviceConfigProxy:Lcom/android/systemui/util/DeviceConfigProxy;

    .line 51
    .line 52
    iput-object p5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 53
    .line 54
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 55
    .line 56
    .line 57
    move-result-object p1

    .line 58
    const p2, 0x1110008

    .line 59
    .line 60
    .line 61
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getBoolean(I)Z

    .line 62
    .line 63
    .line 64
    move-result p1

    .line 65
    iput-boolean p1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mConfigEnableLockScreenButton:Z

    .line 66
    .line 67
    return-void
.end method


# virtual methods
.method public final addCallback(Ljava/lang/Object;)V
    .locals 1

    .line 1
    check-cast p1, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$Callback;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->isCameraAvailable()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 11
    .line 12
    monitor-enter v0

    .line 13
    :try_start_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 14
    .line 15
    invoke-virtual {p0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 16
    .line 17
    .line 18
    monitor-exit v0

    .line 19
    :goto_0
    return-void

    .line 20
    :catchall_0
    move-exception p0

    .line 21
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 22
    throw p0
.end method

.method public final isAbleToOpenCameraApp()Z
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIntent:Landroid/content/Intent;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-eqz v0, :cond_1

    .line 5
    .line 6
    invoke-virtual {v0}, Landroid/content/Intent;->getComponent()Landroid/content/ComponentName;

    .line 7
    .line 8
    .line 9
    move-result-object v2

    .line 10
    const/4 v3, 0x1

    .line 11
    if-nez v2, :cond_0

    .line 12
    .line 13
    move p0, v1

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mContext:Landroid/content/Context;

    .line 16
    .line 17
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    .line 18
    .line 19
    .line 20
    move-result-object p0

    .line 21
    const v2, 0xc8000

    .line 22
    .line 23
    .line 24
    invoke-virtual {p0, v0, v2}, Landroid/content/pm/PackageManager;->queryIntentActivities(Landroid/content/Intent;I)Ljava/util/List;

    .line 25
    .line 26
    .line 27
    move-result-object p0

    .line 28
    invoke-interface {p0}, Ljava/util/List;->isEmpty()Z

    .line 29
    .line 30
    .line 31
    move-result p0

    .line 32
    xor-int/2addr p0, v3

    .line 33
    :goto_0
    if-eqz p0, :cond_1

    .line 34
    .line 35
    move v1, v3

    .line 36
    :cond_1
    return v1
.end method

.method public final isCameraAvailable()Z
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIsCameraAvailable:Ljava/lang/Boolean;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mContext:Landroid/content/Context;

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    const-string v1, "android.hardware.camera"

    .line 12
    .line 13
    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 18
    .line 19
    .line 20
    move-result-object v0

    .line 21
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIsCameraAvailable:Ljava/lang/Boolean;

    .line 22
    .line 23
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIsCameraAvailable:Ljava/lang/Boolean;

    .line 24
    .line 25
    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    .line 26
    .line 27
    .line 28
    move-result p0

    .line 29
    return p0
.end method

.method public final registerQRCodePreferenceObserver()V
    .locals 5

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mConfigEnableLockScreenButton:Z

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 7
    .line 8
    check-cast v0, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 9
    .line 10
    invoke-virtual {v0}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    iget-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 15
    .line 16
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 17
    .line 18
    .line 19
    move-result-object v2

    .line 20
    const/4 v3, 0x0

    .line 21
    invoke-virtual {v1, v2, v3}, Ljava/util/HashMap;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 22
    .line 23
    .line 24
    move-result-object v1

    .line 25
    if-eqz v1, :cond_1

    .line 26
    .line 27
    return-void

    .line 28
    :cond_1
    new-instance v1, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda1;

    .line 29
    .line 30
    const/4 v2, 0x1

    .line 31
    invoke-direct {v1, p0, v2}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda1;-><init>(Ljava/lang/Object;I)V

    .line 32
    .line 33
    .line 34
    iget-object v2, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mExecutor:Ljava/util/concurrent/Executor;

    .line 35
    .line 36
    invoke-interface {v2, v1}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 37
    .line 38
    .line 39
    iget-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 40
    .line 41
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 42
    .line 43
    .line 44
    move-result-object v2

    .line 45
    new-instance v4, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$1;

    .line 46
    .line 47
    invoke-direct {v4, p0, v3}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$1;-><init>(Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;Landroid/os/Handler;)V

    .line 48
    .line 49
    .line 50
    invoke-virtual {v1, v2, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 51
    .line 52
    .line 53
    iget-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

    .line 54
    .line 55
    move-object v2, v1

    .line 56
    check-cast v2, Lcom/android/systemui/util/settings/SecureSettingsImpl;

    .line 57
    .line 58
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 59
    .line 60
    .line 61
    const-string v2, "lock_screen_show_qr_code_scanner"

    .line 62
    .line 63
    invoke-static {v2}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    .line 64
    .line 65
    .line 66
    move-result-object v2

    .line 67
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 68
    .line 69
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 70
    .line 71
    .line 72
    move-result-object v3

    .line 73
    invoke-virtual {p0, v3}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 74
    .line 75
    .line 76
    move-result-object p0

    .line 77
    check-cast p0, Landroid/database/ContentObserver;

    .line 78
    .line 79
    const/4 v3, 0x0

    .line 80
    invoke-interface {v1, v2, v3, p0, v0}, Lcom/android/systemui/util/settings/SettingsProxy;->registerContentObserverForUser(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    .line 81
    .line 82
    .line 83
    return-void
.end method

.method public final varargs registerQRCodeScannerChangeObservers([I)V
    .locals 6

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->isCameraAvailable()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    return-void

    .line 8
    :cond_0
    array-length v0, p1

    .line 9
    const/4 v1, 0x0

    .line 10
    move v2, v1

    .line 11
    :goto_0
    if-ge v2, v0, :cond_5

    .line 12
    .line 13
    aget v3, p1, v2

    .line 14
    .line 15
    iget-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mExecutor:Ljava/util/concurrent/Executor;

    .line 16
    .line 17
    if-eqz v3, :cond_3

    .line 18
    .line 19
    const/4 v5, 0x1

    .line 20
    if-eq v3, v5, :cond_1

    .line 21
    .line 22
    const-string v4, "Unrecognised event: "

    .line 23
    .line 24
    const-string v5, "QRCodeScannerController"

    .line 25
    .line 26
    invoke-static {v4, v3, v5}, Landroidx/core/widget/NestedScrollView$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ILjava/lang/String;)V

    .line 27
    .line 28
    .line 29
    goto :goto_1

    .line 30
    :cond_1
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 31
    .line 32
    invoke-virtual {v3}, Ljava/util/concurrent/atomic/AtomicInteger;->incrementAndGet()I

    .line 33
    .line 34
    .line 35
    invoke-virtual {p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->registerQRCodePreferenceObserver()V

    .line 36
    .line 37
    .line 38
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 39
    .line 40
    if-eqz v3, :cond_2

    .line 41
    .line 42
    goto :goto_1

    .line 43
    :cond_2
    new-instance v3, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$2;

    .line 44
    .line 45
    invoke-direct {v3, p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$2;-><init>(Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;)V

    .line 46
    .line 47
    .line 48
    iput-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 49
    .line 50
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 51
    .line 52
    check-cast v5, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 53
    .line 54
    invoke-virtual {v5, v3, v4}, Lcom/android/systemui/settings/UserTrackerImpl;->addCallback(Lcom/android/systemui/settings/UserTracker$Callback;Ljava/util/concurrent/Executor;)V

    .line 55
    .line 56
    .line 57
    goto :goto_1

    .line 58
    :cond_3
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDefaultQRCodeScannerChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 59
    .line 60
    invoke-virtual {v3}, Ljava/util/concurrent/atomic/AtomicInteger;->incrementAndGet()I

    .line 61
    .line 62
    .line 63
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 64
    .line 65
    if-eqz v3, :cond_4

    .line 66
    .line 67
    goto :goto_1

    .line 68
    :cond_4
    new-instance v3, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda1;

    .line 69
    .line 70
    invoke-direct {v3, p0, v1}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda1;-><init>(Ljava/lang/Object;I)V

    .line 71
    .line 72
    .line 73
    invoke-interface {v4, v3}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 74
    .line 75
    .line 76
    new-instance v3, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 77
    .line 78
    invoke-direct {v3, p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;)V

    .line 79
    .line 80
    .line 81
    iput-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 82
    .line 83
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDeviceConfigProxy:Lcom/android/systemui/util/DeviceConfigProxy;

    .line 84
    .line 85
    invoke-virtual {v5}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 86
    .line 87
    .line 88
    const-string/jumbo v5, "systemui"

    .line 89
    .line 90
    .line 91
    invoke-static {v5, v4, v3}, Landroid/provider/DeviceConfig;->addOnPropertiesChangedListener(Ljava/lang/String;Ljava/util/concurrent/Executor;Landroid/provider/DeviceConfig$OnPropertiesChangedListener;)V

    .line 92
    .line 93
    .line 94
    :goto_1
    add-int/lit8 v2, v2, 0x1

    .line 95
    .line 96
    goto :goto_0

    .line 97
    :cond_5
    return-void
.end method

.method public final removeCallback(Ljava/lang/Object;)V
    .locals 1

    .line 1
    check-cast p1, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$Callback;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->isCameraAvailable()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 11
    .line 12
    monitor-enter v0

    .line 13
    :try_start_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 14
    .line 15
    invoke-virtual {p0, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 16
    .line 17
    .line 18
    monitor-exit v0

    .line 19
    :goto_0
    return-void

    .line 20
    :catchall_0
    move-exception p0

    .line 21
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 22
    throw p0
.end method

.method public final varargs unregisterQRCodeScannerChangeObservers([I)V
    .locals 8

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->isCameraAvailable()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    return-void

    .line 8
    :cond_0
    array-length v0, p1

    .line 9
    const/4 v1, 0x0

    .line 10
    move v2, v1

    .line 11
    :goto_0
    if-ge v2, v0, :cond_7

    .line 12
    .line 13
    aget v3, p1, v2

    .line 14
    .line 15
    const/4 v4, 0x0

    .line 16
    if-eqz v3, :cond_4

    .line 17
    .line 18
    const/4 v5, 0x1

    .line 19
    if-eq v3, v5, :cond_1

    .line 20
    .line 21
    const-string v4, "Unrecognised event: "

    .line 22
    .line 23
    const-string v5, "QRCodeScannerController"

    .line 24
    .line 25
    invoke-static {v4, v3, v5}, Landroidx/core/widget/NestedScrollView$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ILjava/lang/String;)V

    .line 26
    .line 27
    .line 28
    goto :goto_2

    .line 29
    :cond_1
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 30
    .line 31
    if-nez v3, :cond_2

    .line 32
    .line 33
    goto :goto_2

    .line 34
    :cond_2
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 35
    .line 36
    invoke-virtual {v5}, Ljava/util/concurrent/atomic/AtomicInteger;->decrementAndGet()I

    .line 37
    .line 38
    .line 39
    move-result v5

    .line 40
    if-nez v5, :cond_6

    .line 41
    .line 42
    iget-boolean v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mConfigEnableLockScreenButton:Z

    .line 43
    .line 44
    if-nez v5, :cond_3

    .line 45
    .line 46
    goto :goto_1

    .line 47
    :cond_3
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 48
    .line 49
    new-instance v6, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda0;

    .line 50
    .line 51
    invoke-direct {v6, p0}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;)V

    .line 52
    .line 53
    .line 54
    invoke-virtual {v5, v6}, Ljava/util/HashMap;->forEach(Ljava/util/function/BiConsumer;)V

    .line 55
    .line 56
    .line 57
    new-instance v5, Ljava/util/HashMap;

    .line 58
    .line 59
    invoke-direct {v5}, Ljava/util/HashMap;-><init>()V

    .line 60
    .line 61
    .line 62
    iput-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerPreferenceObserver:Ljava/util/HashMap;

    .line 63
    .line 64
    move-object v5, v3

    .line 65
    check-cast v5, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 66
    .line 67
    invoke-virtual {v5}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 68
    .line 69
    .line 70
    move-result v5

    .line 71
    iget-object v6, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

    .line 72
    .line 73
    check-cast v6, Lcom/android/systemui/util/settings/SecureSettingsImpl;

    .line 74
    .line 75
    const-string/jumbo v7, "show_qr_code_scanner_setting"

    .line 76
    .line 77
    .line 78
    invoke-virtual {v6, v5, v7, v4}, Lcom/android/systemui/util/settings/SecureSettingsImpl;->putStringForUser(ILjava/lang/String;Ljava/lang/String;)Z

    .line 79
    .line 80
    .line 81
    :goto_1
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 82
    .line 83
    check-cast v3, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 84
    .line 85
    invoke-virtual {v3, v5}, Lcom/android/systemui/settings/UserTrackerImpl;->removeCallback(Lcom/android/systemui/settings/UserTracker$Callback;)V

    .line 86
    .line 87
    .line 88
    iput-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserChangedListener:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 89
    .line 90
    iput-boolean v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerEnabled:Z

    .line 91
    .line 92
    goto :goto_2

    .line 93
    :cond_4
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 94
    .line 95
    if-nez v3, :cond_5

    .line 96
    .line 97
    goto :goto_2

    .line 98
    :cond_5
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDefaultQRCodeScannerChangeEvents:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 99
    .line 100
    invoke-virtual {v3}, Ljava/util/concurrent/atomic/AtomicInteger;->decrementAndGet()I

    .line 101
    .line 102
    .line 103
    move-result v3

    .line 104
    if-nez v3, :cond_6

    .line 105
    .line 106
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 107
    .line 108
    iget-object v5, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDeviceConfigProxy:Lcom/android/systemui/util/DeviceConfigProxy;

    .line 109
    .line 110
    invoke-virtual {v5}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 111
    .line 112
    .line 113
    invoke-static {v3}, Landroid/provider/DeviceConfig;->removeOnPropertiesChangedListener(Landroid/provider/DeviceConfig$OnPropertiesChangedListener;)V

    .line 114
    .line 115
    .line 116
    iput-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mOnDefaultQRCodeScannerChangedListener:Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda2;

    .line 117
    .line 118
    iput-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 119
    .line 120
    iput-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIntent:Landroid/content/Intent;

    .line 121
    .line 122
    :cond_6
    :goto_2
    add-int/lit8 v2, v2, 0x1

    .line 123
    .line 124
    goto :goto_0

    .line 125
    :cond_7
    return-void
.end method

.method public final updateQRCodeScannerActivityDetails()V
    .locals 6

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mDeviceConfigProxy:Lcom/android/systemui/util/DeviceConfigProxy;

    .line 2
    .line 3
    const-string/jumbo v1, "systemui"

    .line 4
    .line 5
    .line 6
    const-string v2, "default_qr_code_scanner"

    .line 7
    .line 8
    const-string v3, ""

    .line 9
    .line 10
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 11
    .line 12
    .line 13
    invoke-static {v1, v2, v3}, Landroid/provider/DeviceConfig;->getString(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    const-string v1, ""

    .line 18
    .line 19
    invoke-static {v0, v1}, Ljava/util/Objects;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 20
    .line 21
    .line 22
    move-result v1

    .line 23
    if-eqz v1, :cond_0

    .line 24
    .line 25
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mContext:Landroid/content/Context;

    .line 26
    .line 27
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 28
    .line 29
    .line 30
    move-result-object v0

    .line 31
    const v1, 0x1040318

    .line 32
    .line 33
    .line 34
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 35
    .line 36
    .line 37
    move-result-object v0

    .line 38
    :cond_0
    iget-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 39
    .line 40
    new-instance v2, Landroid/content/Intent;

    .line 41
    .line 42
    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    .line 43
    .line 44
    .line 45
    if-eqz v0, :cond_1

    .line 46
    .line 47
    invoke-static {v0}, Landroid/content/ComponentName;->unflattenFromString(Ljava/lang/String;)Landroid/content/ComponentName;

    .line 48
    .line 49
    .line 50
    move-result-object v3

    .line 51
    invoke-virtual {v2, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 52
    .line 53
    .line 54
    const/high16 v3, 0x14000000

    .line 55
    .line 56
    invoke-virtual {v2, v3}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 57
    .line 58
    .line 59
    :cond_1
    invoke-virtual {v2}, Landroid/content/Intent;->getComponent()Landroid/content/ComponentName;

    .line 60
    .line 61
    .line 62
    move-result-object v3

    .line 63
    const/4 v4, 0x0

    .line 64
    if-nez v3, :cond_2

    .line 65
    .line 66
    move v3, v4

    .line 67
    goto :goto_0

    .line 68
    :cond_2
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mContext:Landroid/content/Context;

    .line 69
    .line 70
    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    .line 71
    .line 72
    .line 73
    move-result-object v3

    .line 74
    const v5, 0x200ca200

    .line 75
    .line 76
    .line 77
    invoke-virtual {v3, v2, v5}, Landroid/content/pm/PackageManager;->queryIntentActivities(Landroid/content/Intent;I)Ljava/util/List;

    .line 78
    .line 79
    .line 80
    move-result-object v3

    .line 81
    invoke-interface {v3}, Ljava/util/List;->isEmpty()Z

    .line 82
    .line 83
    .line 84
    move-result v3

    .line 85
    xor-int/lit8 v3, v3, 0x1

    .line 86
    .line 87
    :goto_0
    if-eqz v3, :cond_3

    .line 88
    .line 89
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 90
    .line 91
    iput-object v2, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIntent:Landroid/content/Intent;

    .line 92
    .line 93
    goto :goto_1

    .line 94
    :cond_3
    const/4 v0, 0x0

    .line 95
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 96
    .line 97
    iput-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mIntent:Landroid/content/Intent;

    .line 98
    .line 99
    :goto_1
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 100
    .line 101
    invoke-static {v0, v1}, Ljava/util/Objects;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 102
    .line 103
    .line 104
    move-result v0

    .line 105
    if-nez v0, :cond_4

    .line 106
    .line 107
    iget-object v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 108
    .line 109
    monitor-enter v0

    .line 110
    :try_start_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 111
    .line 112
    invoke-virtual {p0}, Ljava/util/ArrayList;->clone()Ljava/lang/Object;

    .line 113
    .line 114
    .line 115
    move-result-object p0

    .line 116
    check-cast p0, Ljava/util/ArrayList;

    .line 117
    .line 118
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 119
    new-instance v0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda3;

    .line 120
    .line 121
    invoke-direct {v0, v4}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda3;-><init>(I)V

    .line 122
    .line 123
    .line 124
    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->forEach(Ljava/util/function/Consumer;)V

    .line 125
    .line 126
    .line 127
    goto :goto_2

    .line 128
    :catchall_0
    move-exception p0

    .line 129
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 130
    throw p0

    .line 131
    :cond_4
    :goto_2
    return-void
.end method

.method public final updateQRCodeScannerPreferenceDetails(Z)V
    .locals 5

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mConfigEnableLockScreenButton:Z

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerEnabled:Z

    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

    .line 9
    .line 10
    const-string v2, "lock_screen_show_qr_code_scanner"

    .line 11
    .line 12
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 13
    .line 14
    check-cast v3, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 15
    .line 16
    invoke-virtual {v3}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 17
    .line 18
    .line 19
    move-result v3

    .line 20
    const/4 v4, 0x0

    .line 21
    invoke-interface {v1, v4, v3, v2}, Lcom/android/systemui/util/settings/SettingsProxy;->getIntForUser(IILjava/lang/String;)I

    .line 22
    .line 23
    .line 24
    move-result v1

    .line 25
    const/4 v2, 0x1

    .line 26
    if-eqz v1, :cond_1

    .line 27
    .line 28
    move v4, v2

    .line 29
    :cond_1
    iput-boolean v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerEnabled:Z

    .line 30
    .line 31
    if-eqz p1, :cond_2

    .line 32
    .line 33
    iget-object p1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mSecureSettings:Lcom/android/systemui/util/settings/SecureSettings;

    .line 34
    .line 35
    const-string/jumbo v1, "show_qr_code_scanner_setting"

    .line 36
    .line 37
    .line 38
    iget-object v3, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerActivity:Ljava/lang/String;

    .line 39
    .line 40
    iget-object v4, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 41
    .line 42
    check-cast v4, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 43
    .line 44
    invoke-virtual {v4}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 45
    .line 46
    .line 47
    move-result v4

    .line 48
    check-cast p1, Lcom/android/systemui/util/settings/SecureSettingsImpl;

    .line 49
    .line 50
    invoke-virtual {p1, v4, v1, v3}, Lcom/android/systemui/util/settings/SecureSettingsImpl;->putStringForUser(ILjava/lang/String;Ljava/lang/String;)Z

    .line 51
    .line 52
    .line 53
    :cond_2
    iget-boolean p1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mQRCodeScannerEnabled:Z

    .line 54
    .line 55
    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 56
    .line 57
    .line 58
    move-result-object p1

    .line 59
    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 60
    .line 61
    .line 62
    move-result-object v0

    .line 63
    invoke-static {p1, v0}, Ljava/util/Objects;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 64
    .line 65
    .line 66
    move-result p1

    .line 67
    if-nez p1, :cond_3

    .line 68
    .line 69
    iget-object p1, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 70
    .line 71
    monitor-enter p1

    .line 72
    :try_start_0
    iget-object p0, p0, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController;->mCallbacks:Ljava/util/ArrayList;

    .line 73
    .line 74
    invoke-virtual {p0}, Ljava/util/ArrayList;->clone()Ljava/lang/Object;

    .line 75
    .line 76
    .line 77
    move-result-object p0

    .line 78
    check-cast p0, Ljava/util/ArrayList;

    .line 79
    .line 80
    monitor-exit p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 81
    new-instance p1, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda3;

    .line 82
    .line 83
    invoke-direct {p1, v2}, Lcom/android/systemui/qrcodescanner/controller/QRCodeScannerController$$ExternalSyntheticLambda3;-><init>(I)V

    .line 84
    .line 85
    .line 86
    invoke-virtual {p0, p1}, Ljava/util/ArrayList;->forEach(Ljava/util/function/Consumer;)V

    .line 87
    .line 88
    .line 89
    goto :goto_0

    .line 90
    :catchall_0
    move-exception p0

    .line 91
    :try_start_1
    monitor-exit p1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 92
    throw p0

    .line 93
    :cond_3
    :goto_0
    return-void
.end method

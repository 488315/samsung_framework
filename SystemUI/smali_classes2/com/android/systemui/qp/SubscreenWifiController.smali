.class public final Lcom/android/systemui/qp/SubscreenWifiController;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/qp/SubscreenQSControllerContract$Presenter;


# static fields
.field public static mContext:Landroid/content/Context;

.field public static sInstance:Lcom/android/systemui/qp/SubscreenWifiController;


# instance fields
.field public final mNetworkController:Lcom/android/systemui/statusbar/connectivity/NetworkController;

.field public final mSignalCallback:Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;

.field public final mWifiAdapter:Landroid/net/wifi/WifiManager;

.field public mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

.field public mWifiState:Z

.field public mWifiView:Lcom/android/systemui/qp/SubscreenQSControllerContract$View;

.field public mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    sget-object v0, Lcom/android/systemui/qp/SubscreenWifiController;->mContext:Landroid/content/Context;

    .line 5
    .line 6
    const-string/jumbo v1, "wifi"

    .line 7
    .line 8
    .line 9
    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    check-cast v0, Landroid/net/wifi/WifiManager;

    .line 14
    .line 15
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiAdapter:Landroid/net/wifi/WifiManager;

    .line 16
    .line 17
    const-class v0, Lcom/android/systemui/statusbar/connectivity/NetworkController;

    .line 18
    .line 19
    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    check-cast v0, Lcom/android/systemui/statusbar/connectivity/NetworkController;

    .line 24
    .line 25
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mNetworkController:Lcom/android/systemui/statusbar/connectivity/NetworkController;

    .line 26
    .line 27
    new-instance v0, Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;

    .line 28
    .line 29
    const/4 v1, 0x0

    .line 30
    invoke-direct {v0, p0, v1}, Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;-><init>(Lcom/android/systemui/qp/SubscreenWifiController;I)V

    .line 31
    .line 32
    .line 33
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mSignalCallback:Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;

    .line 34
    .line 35
    return-void
.end method

.method public static getInstance(Landroid/content/Context;)Lcom/android/systemui/qp/SubscreenWifiController;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/qp/SubscreenWifiController;->sInstance:Lcom/android/systemui/qp/SubscreenWifiController;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    sput-object p0, Lcom/android/systemui/qp/SubscreenWifiController;->mContext:Landroid/content/Context;

    .line 6
    .line 7
    new-instance p0, Lcom/android/systemui/qp/SubscreenWifiController;

    .line 8
    .line 9
    invoke-direct {p0}, Lcom/android/systemui/qp/SubscreenWifiController;-><init>()V

    .line 10
    .line 11
    .line 12
    sput-object p0, Lcom/android/systemui/qp/SubscreenWifiController;->sInstance:Lcom/android/systemui/qp/SubscreenWifiController;

    .line 13
    .line 14
    :cond_0
    sget-object p0, Lcom/android/systemui/qp/SubscreenWifiController;->sInstance:Lcom/android/systemui/qp/SubscreenWifiController;

    .line 15
    .line 16
    return-object p0
.end method


# virtual methods
.method public final registerReceiver(Z)V
    .locals 8

    .line 1
    const-string v0, "WIFI_STATE_CHANGE"

    .line 2
    .line 3
    invoke-static {v0}, Landroidx/appcompat/app/AppCompatDelegateImpl$$ExternalSyntheticOutline0;->m(Ljava/lang/String;)Landroid/content/IntentFilter;

    .line 4
    .line 5
    .line 6
    move-result-object v3

    .line 7
    if-eqz p1, :cond_0

    .line 8
    .line 9
    new-instance v0, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 10
    .line 11
    invoke-direct {v0, p0}, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;-><init>(Lcom/android/systemui/qp/SubscreenWifiController;)V

    .line 12
    .line 13
    .line 14
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 15
    .line 16
    goto :goto_0

    .line 17
    :cond_0
    new-instance v0, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 18
    .line 19
    invoke-direct {v0, p0}, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;-><init>(Lcom/android/systemui/qp/SubscreenWifiController;)V

    .line 20
    .line 21
    .line 22
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 23
    .line 24
    :goto_0
    new-instance v0, Ljava/lang/StringBuilder;

    .line 25
    .line 26
    const-string v1, "SWC registerWifiReceiver mWifiViewReceiver: "

    .line 27
    .line 28
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 29
    .line 30
    .line 31
    iget-object v1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 32
    .line 33
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 34
    .line 35
    .line 36
    const-string v1, "mWifiReceiver: "

    .line 37
    .line 38
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 39
    .line 40
    .line 41
    iget-object v1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 42
    .line 43
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 44
    .line 45
    .line 46
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 47
    .line 48
    .line 49
    move-result-object v0

    .line 50
    const-string v1, "SubscreenWifiController"

    .line 51
    .line 52
    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 53
    .line 54
    .line 55
    const-class v0, Lcom/android/systemui/broadcast/BroadcastDispatcher;

    .line 56
    .line 57
    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 58
    .line 59
    .line 60
    move-result-object v0

    .line 61
    move-object v1, v0

    .line 62
    check-cast v1, Lcom/android/systemui/broadcast/BroadcastDispatcher;

    .line 63
    .line 64
    if-eqz p1, :cond_1

    .line 65
    .line 66
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 67
    .line 68
    goto :goto_1

    .line 69
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 70
    .line 71
    :goto_1
    move-object v2, p1

    .line 72
    const/4 v4, 0x0

    .line 73
    sget-object v5, Landroid/os/UserHandle;->ALL:Landroid/os/UserHandle;

    .line 74
    .line 75
    const/4 v6, 0x2

    .line 76
    const-string v7, "com.samsung.systemui.permission.WIFI_STATE_CHANGE"

    .line 77
    .line 78
    invoke-virtual/range {v1 .. v7}, Lcom/android/systemui/broadcast/BroadcastDispatcher;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/util/concurrent/Executor;Landroid/os/UserHandle;ILjava/lang/String;)V

    .line 79
    .line 80
    .line 81
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mSignalCallback:Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;

    .line 82
    .line 83
    iget-object p0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mNetworkController:Lcom/android/systemui/statusbar/connectivity/NetworkController;

    .line 84
    .line 85
    check-cast p0, Lcom/android/systemui/statusbar/connectivity/NetworkControllerImpl;

    .line 86
    .line 87
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/connectivity/NetworkControllerImpl;->addCallback(Ljava/lang/Object;)V

    .line 88
    .line 89
    .line 90
    return-void
.end method

.method public final setListener(Lcom/android/systemui/qp/SubscreenQSControllerContract$View;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiView:Lcom/android/systemui/qp/SubscreenQSControllerContract$View;

    .line 2
    .line 3
    new-instance p1, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 4
    .line 5
    invoke-direct {p1, p0}, Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;-><init>(Lcom/android/systemui/qp/SubscreenWifiController;)V

    .line 6
    .line 7
    .line 8
    iput-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 9
    .line 10
    return-void
.end method

.method public final unRegisterReceiver(Z)V
    .locals 4

    .line 1
    const/4 v0, 0x0

    .line 2
    const-class v1, Lcom/android/systemui/broadcast/BroadcastDispatcher;

    .line 3
    .line 4
    const-string v2, "SubscreenWifiController"

    .line 5
    .line 6
    if-eqz p1, :cond_0

    .line 7
    .line 8
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 9
    .line 10
    if-eqz p1, :cond_0

    .line 11
    .line 12
    new-instance p1, Ljava/lang/StringBuilder;

    .line 13
    .line 14
    const-string v3, "SWC unRegisterReceiver mWifiViewReceiver: "

    .line 15
    .line 16
    invoke-direct {p1, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 17
    .line 18
    .line 19
    iget-object v3, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 20
    .line 21
    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 22
    .line 23
    .line 24
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 25
    .line 26
    .line 27
    move-result-object p1

    .line 28
    invoke-static {v2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 29
    .line 30
    .line 31
    invoke-static {v1}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 32
    .line 33
    .line 34
    move-result-object p1

    .line 35
    check-cast p1, Lcom/android/systemui/broadcast/BroadcastDispatcher;

    .line 36
    .line 37
    iget-object v1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 38
    .line 39
    invoke-virtual {p1, v1}, Lcom/android/systemui/broadcast/BroadcastDispatcher;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 40
    .line 41
    .line 42
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiViewReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 43
    .line 44
    goto :goto_0

    .line 45
    :cond_0
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 46
    .line 47
    if-eqz p1, :cond_1

    .line 48
    .line 49
    new-instance p1, Ljava/lang/StringBuilder;

    .line 50
    .line 51
    const-string v3, "SWC unRegisterReceiver mWifiReceiver: "

    .line 52
    .line 53
    invoke-direct {p1, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 54
    .line 55
    .line 56
    iget-object v3, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 57
    .line 58
    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 59
    .line 60
    .line 61
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 62
    .line 63
    .line 64
    move-result-object p1

    .line 65
    invoke-static {v2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 66
    .line 67
    .line 68
    invoke-static {v1}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 69
    .line 70
    .line 71
    move-result-object p1

    .line 72
    check-cast p1, Lcom/android/systemui/broadcast/BroadcastDispatcher;

    .line 73
    .line 74
    iget-object v1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 75
    .line 76
    invoke-virtual {p1, v1}, Lcom/android/systemui/broadcast/BroadcastDispatcher;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 77
    .line 78
    .line 79
    iput-object v0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mWifiReceiver:Lcom/android/systemui/qp/SubscreenWifiController$TileReceiver;

    .line 80
    .line 81
    :cond_1
    :goto_0
    iget-object p1, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mSignalCallback:Lcom/android/systemui/qp/SubscreenWifiController$WifiSignalCallback;

    .line 82
    .line 83
    if-eqz p1, :cond_2

    .line 84
    .line 85
    iget-object p0, p0, Lcom/android/systemui/qp/SubscreenWifiController;->mNetworkController:Lcom/android/systemui/statusbar/connectivity/NetworkController;

    .line 86
    .line 87
    check-cast p0, Lcom/android/systemui/statusbar/connectivity/NetworkControllerImpl;

    .line 88
    .line 89
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/connectivity/NetworkControllerImpl;->removeCallback(Ljava/lang/Object;)V

    .line 90
    .line 91
    .line 92
    :cond_2
    return-void
.end method
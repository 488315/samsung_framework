.class public final Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/bluetooth/BluetoothProfile$ServiceListener;


# instance fields
.field public final synthetic this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;


# direct methods
.method private constructor <init>(Lcom/android/settingslib/bluetooth/MapClientProfile;)V
    .locals 0

    .line 2
    iput-object p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public synthetic constructor <init>(Lcom/android/settingslib/bluetooth/MapClientProfile;I)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;-><init>(Lcom/android/settingslib/bluetooth/MapClientProfile;)V

    return-void
.end method


# virtual methods
.method public final onServiceConnected(ILandroid/bluetooth/BluetoothProfile;)V
    .locals 2

    .line 1
    iget-object p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 2
    .line 3
    check-cast p2, Landroid/bluetooth/BluetoothMapClient;

    .line 4
    .line 5
    iput-object p2, p1, Lcom/android/settingslib/bluetooth/MapClientProfile;->mService:Landroid/bluetooth/BluetoothMapClient;

    .line 6
    .line 7
    invoke-virtual {p2}, Landroid/bluetooth/BluetoothMapClient;->getConnectedDevices()Ljava/util/List;

    .line 8
    .line 9
    .line 10
    move-result-object p1

    .line 11
    :goto_0
    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    .line 12
    .line 13
    .line 14
    move-result p2

    .line 15
    if-nez p2, :cond_1

    .line 16
    .line 17
    const/4 p2, 0x0

    .line 18
    invoke-interface {p1, p2}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    .line 19
    .line 20
    .line 21
    move-result-object p2

    .line 22
    check-cast p2, Landroid/bluetooth/BluetoothDevice;

    .line 23
    .line 24
    iget-object v0, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 25
    .line 26
    iget-object v0, v0, Lcom/android/settingslib/bluetooth/MapClientProfile;->mDeviceManager:Lcom/android/settingslib/bluetooth/CachedBluetoothDeviceManager;

    .line 27
    .line 28
    invoke-virtual {v0, p2}, Lcom/android/settingslib/bluetooth/CachedBluetoothDeviceManager;->findDevice(Landroid/bluetooth/BluetoothDevice;)Lcom/android/settingslib/bluetooth/CachedBluetoothDevice;

    .line 29
    .line 30
    .line 31
    move-result-object v0

    .line 32
    if-nez v0, :cond_0

    .line 33
    .line 34
    new-instance v0, Ljava/lang/StringBuilder;

    .line 35
    .line 36
    const-string v1, "MapProfile found new device: "

    .line 37
    .line 38
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 39
    .line 40
    .line 41
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 42
    .line 43
    .line 44
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 45
    .line 46
    .line 47
    move-result-object v0

    .line 48
    const-string v1, "MapClientProfile"

    .line 49
    .line 50
    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    .line 52
    .line 53
    iget-object v0, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 54
    .line 55
    iget-object v1, v0, Lcom/android/settingslib/bluetooth/MapClientProfile;->mDeviceManager:Lcom/android/settingslib/bluetooth/CachedBluetoothDeviceManager;

    .line 56
    .line 57
    iget-object v0, v0, Lcom/android/settingslib/bluetooth/MapClientProfile;->mProfileManager:Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;

    .line 58
    .line 59
    invoke-virtual {v1, v0, p2}, Lcom/android/settingslib/bluetooth/CachedBluetoothDeviceManager;->addDevice(Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;Landroid/bluetooth/BluetoothDevice;)Lcom/android/settingslib/bluetooth/CachedBluetoothDevice;

    .line 60
    .line 61
    .line 62
    move-result-object v0

    .line 63
    :cond_0
    iget-object p2, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 64
    .line 65
    const/4 v1, 0x2

    .line 66
    invoke-virtual {v0, p2, v1}, Lcom/android/settingslib/bluetooth/CachedBluetoothDevice;->onProfileStateChanged(Lcom/android/settingslib/bluetooth/LocalBluetoothProfile;I)V

    .line 67
    .line 68
    .line 69
    invoke-virtual {v0}, Lcom/android/settingslib/bluetooth/CachedBluetoothDevice;->refresh()V

    .line 70
    .line 71
    .line 72
    goto :goto_0

    .line 73
    :cond_1
    iget-object p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 74
    .line 75
    iget-object p1, p1, Lcom/android/settingslib/bluetooth/MapClientProfile;->mProfileManager:Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;

    .line 76
    .line 77
    invoke-virtual {p1}, Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;->callServiceConnectedListeners()V

    .line 78
    .line 79
    .line 80
    iget-object p0, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 81
    .line 82
    const/4 p1, 0x1

    .line 83
    iput-boolean p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile;->mIsProfileReady:Z

    .line 84
    .line 85
    return-void
.end method

.method public final onServiceDisconnected(I)V
    .locals 0

    .line 1
    iget-object p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 2
    .line 3
    iget-object p1, p1, Lcom/android/settingslib/bluetooth/MapClientProfile;->mProfileManager:Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;

    .line 4
    .line 5
    invoke-virtual {p1}, Lcom/android/settingslib/bluetooth/LocalBluetoothProfileManager;->callServiceDisconnectedListeners()V

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/settingslib/bluetooth/MapClientProfile$MapClientServiceListener;->this$0:Lcom/android/settingslib/bluetooth/MapClientProfile;

    .line 9
    .line 10
    const/4 p1, 0x0

    .line 11
    iput-boolean p1, p0, Lcom/android/settingslib/bluetooth/MapClientProfile;->mIsProfileReady:Z

    .line 12
    .line 13
    return-void
.end method
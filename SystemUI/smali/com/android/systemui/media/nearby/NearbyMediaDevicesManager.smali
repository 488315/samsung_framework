.class public final Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final activeCallbacks:Ljava/util/List;

.field public final deathRecipient:Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$deathRecipient$1;

.field public final logger:Lcom/android/systemui/media/nearby/NearbyMediaDevicesLogger;

.field public final providers:Ljava/util/List;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/CommandQueue;Lcom/android/systemui/media/nearby/NearbyMediaDevicesLogger;)V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p2, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->logger:Lcom/android/systemui/media/nearby/NearbyMediaDevicesLogger;

    .line 5
    .line 6
    new-instance p2, Ljava/util/ArrayList;

    .line 7
    .line 8
    invoke-direct {p2}, Ljava/util/ArrayList;-><init>()V

    .line 9
    .line 10
    .line 11
    iput-object p2, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->providers:Ljava/util/List;

    .line 12
    .line 13
    new-instance p2, Ljava/util/ArrayList;

    .line 14
    .line 15
    invoke-direct {p2}, Ljava/util/ArrayList;-><init>()V

    .line 16
    .line 17
    .line 18
    iput-object p2, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->activeCallbacks:Ljava/util/List;

    .line 19
    .line 20
    new-instance p2, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$commandQueueCallbacks$1;

    .line 21
    .line 22
    invoke-direct {p2, p0}, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$commandQueueCallbacks$1;-><init>(Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;)V

    .line 23
    .line 24
    .line 25
    new-instance v0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$deathRecipient$1;

    .line 26
    .line 27
    invoke-direct {v0, p0}, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$deathRecipient$1;-><init>(Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;)V

    .line 28
    .line 29
    .line 30
    iput-object v0, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->deathRecipient:Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager$deathRecipient$1;

    .line 31
    .line 32
    invoke-virtual {p1, p2}, Lcom/android/systemui/statusbar/CommandQueue;->addCallback(Lcom/android/systemui/statusbar/CommandQueue$Callbacks;)V

    .line 33
    .line 34
    .line 35
    return-void
.end method


# virtual methods
.method public final unregisterNearbyDevicesCallback(Landroid/media/INearbyMediaDevicesUpdateCallback;)V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->activeCallbacks:Ljava/util/List;

    .line 2
    .line 3
    check-cast v0, Ljava/util/ArrayList;

    .line 4
    .line 5
    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/media/nearby/NearbyMediaDevicesManager;->providers:Ljava/util/List;

    .line 9
    .line 10
    invoke-interface {p0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    .line 11
    .line 12
    .line 13
    move-result-object p0

    .line 14
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    .line 15
    .line 16
    .line 17
    move-result v0

    .line 18
    if-eqz v0, :cond_0

    .line 19
    .line 20
    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 21
    .line 22
    .line 23
    move-result-object v0

    .line 24
    check-cast v0, Landroid/media/INearbyMediaDevicesProvider;

    .line 25
    .line 26
    invoke-interface {v0, p1}, Landroid/media/INearbyMediaDevicesProvider;->unregisterNearbyDevicesCallback(Landroid/media/INearbyMediaDevicesUpdateCallback;)V

    .line 27
    .line 28
    .line 29
    goto :goto_0

    .line 30
    :cond_0
    return-void
.end method

.class public final Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/systemui/splugins/volume/VolumeState;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Builder"
.end annotation


# instance fields
.field private volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState;

    invoke-direct {v0}, Lcom/samsung/systemui/splugins/volume/VolumeState;-><init>()V

    iput-object v0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    return-void
.end method

.method public constructor <init>(Lcom/samsung/systemui/splugins/volume/VolumeState;)V
    .locals 2

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState;

    invoke-direct {v0}, Lcom/samsung/systemui/splugins/volume/VolumeState;-><init>()V

    .line 5
    invoke-virtual {p1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->getStreamStates()Ljava/util/List;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$setStreamStates$p(Lcom/samsung/systemui/splugins/volume/VolumeState;Ljava/util/List;)V

    .line 6
    invoke-static {p1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$getBoolMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;)Ljava/util/HashMap;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$setBoolMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;Ljava/util/HashMap;)V

    .line 7
    invoke-static {p1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$getIntMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;)Ljava/util/HashMap;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$setIntMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;Ljava/util/HashMap;)V

    .line 8
    iput-object v0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    return-void
.end method


# virtual methods
.method public final activeStream(I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ACTIVE_STREAM:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setIntegerValue(Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final broadcastMode(I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->BROADCAST_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setIntegerValue(Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final build()Lcom/samsung/systemui/splugins/volume/VolumeState;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    .line 2
    .line 3
    return-object p0
.end method

.method public final disallowMedia(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->DISALLOW_MEDIA:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final disallowRinger(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->DISALLOW_RINGER:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final disallowSystem(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->DISALLOW_SYSTEM:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final fixedScoVolume(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->FIXED_SCO_VOLUME:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final isAodVolumePanel(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->IS_AOD_VOLUME_PANEL:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final isDualAudio(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->IS_DUAL_AUDIO:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final isFromKey(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->IS_FROM_KEY:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final isLeBroadcasting(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->IS_LE_BROADCASTING:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final remoteMic(Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;->REMOTE_MIC:Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final ringerModeInternal(I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->RINGER_MODE_INTERNAL:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setIntegerValue(Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

.method public final setEnabled(Lcom/samsung/systemui/splugins/volume/VolumeState$BooleanStateKey;Z)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 2
    .line 3
    .line 4
    move-result-object p2

    .line 5
    iget-object v0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    .line 6
    .line 7
    invoke-static {v0}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$getBoolMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;)Ljava/util/HashMap;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    invoke-interface {v0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 12
    .line 13
    .line 14
    return-object p0
.end method

.method public final setIntegerValue(Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 2
    .line 3
    .line 4
    move-result-object p2

    .line 5
    iget-object v0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    .line 6
    .line 7
    invoke-static {v0}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$getIntMap$p(Lcom/samsung/systemui/splugins/volume/VolumeState;)Ljava/util/HashMap;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    invoke-interface {v0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 12
    .line 13
    .line 14
    return-object p0
.end method

.method public final setStreamStates(Ljava/util/List;)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/samsung/systemui/splugins/volume/VolumeStreamState;",
            ">;)",
            "Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;"
        }
    .end annotation

    .line 1
    iget-object v0, p0, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->volumeState:Lcom/samsung/systemui/splugins/volume/VolumeState;

    .line 2
    .line 3
    invoke-static {v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState;->access$setStreamStates$p(Lcom/samsung/systemui/splugins/volume/VolumeState;Ljava/util/List;)V

    .line 4
    .line 5
    .line 6
    return-object p0
.end method

.method public final zenMode(I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ZEN_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-virtual {p0, v0, p1}, Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;->setIntegerValue(Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;I)Lcom/samsung/systemui/splugins/volume/VolumeState$Builder;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method

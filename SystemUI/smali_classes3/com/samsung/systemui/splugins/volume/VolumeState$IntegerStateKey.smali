.class public final enum Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/systemui/splugins/volume/VolumeState;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "IntegerStateKey"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

.field public static final enum ACTIVE_STREAM:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

.field public static final enum BROADCAST_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

.field public static final enum RINGER_MODE_INTERNAL:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

.field public static final enum ZEN_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;


# direct methods
.method private static final synthetic $values()[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;
    .locals 4

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ACTIVE_STREAM:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->RINGER_MODE_INTERNAL:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ZEN_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 6
    .line 7
    sget-object v3, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->BROADCAST_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 8
    .line 9
    filled-new-array {v0, v1, v2, v3}, [Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    const-string v1, "ACTIVE_STREAM"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ACTIVE_STREAM:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 12
    .line 13
    const-string v1, "RINGER_MODE_INTERNAL"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->RINGER_MODE_INTERNAL:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 22
    .line 23
    const-string v1, "ZEN_MODE"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->ZEN_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 30
    .line 31
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 32
    .line 33
    const-string v1, "BROADCAST_MODE"

    .line 34
    .line 35
    const/4 v2, 0x3

    .line 36
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->BROADCAST_MODE:Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 40
    .line 41
    invoke-static {}, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->$values()[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 42
    .line 43
    .line 44
    move-result-object v0

    .line 45
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->$VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 46
    .line 47
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;->$VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/systemui/splugins/volume/VolumeState$IntegerStateKey;

    .line 8
    .line 9
    return-object v0
.end method

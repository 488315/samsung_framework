.class public final enum Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/systemui/splugins/volume/VolumeStreamState;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "BooleanStateKey"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum DISABLED_FIXED_SESSION:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum DYNAMIC:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum MUTED:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum MUTE_SUPPORT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_APP_MIRRORING:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_BT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_BUDS:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_BUDS3:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_HEADSET:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_HEARING_AID:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_HOME_MINI:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

.field public static final enum ROUTED_TO_REMOTE_SPEAKER:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;


# direct methods
.method private static final synthetic $values()[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;
    .locals 12

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->DYNAMIC:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->MUTED:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->MUTE_SUPPORT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 6
    .line 7
    sget-object v3, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 8
    .line 9
    sget-object v4, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BUDS:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 10
    .line 11
    sget-object v5, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BUDS3:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 12
    .line 13
    sget-object v6, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_APP_MIRRORING:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 14
    .line 15
    sget-object v7, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_REMOTE_SPEAKER:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 16
    .line 17
    sget-object v8, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HEADSET:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 18
    .line 19
    sget-object v9, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HOME_MINI:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 20
    .line 21
    sget-object v10, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HEARING_AID:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 22
    .line 23
    sget-object v11, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->DISABLED_FIXED_SESSION:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 24
    .line 25
    filled-new-array/range {v0 .. v11}, [Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 26
    .line 27
    .line 28
    move-result-object v0

    .line 29
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 2
    .line 3
    const-string v1, "DYNAMIC"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->DYNAMIC:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 12
    .line 13
    const-string v1, "MUTED"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->MUTED:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 22
    .line 23
    const-string v1, "MUTE_SUPPORT"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->MUTE_SUPPORT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 30
    .line 31
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 32
    .line 33
    const-string v1, "ROUTED_TO_BT"

    .line 34
    .line 35
    const/4 v2, 0x3

    .line 36
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BT:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 40
    .line 41
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 42
    .line 43
    const-string v1, "ROUTED_TO_BUDS"

    .line 44
    .line 45
    const/4 v2, 0x4

    .line 46
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BUDS:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 50
    .line 51
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 52
    .line 53
    const-string v1, "ROUTED_TO_BUDS3"

    .line 54
    .line 55
    const/4 v2, 0x5

    .line 56
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 57
    .line 58
    .line 59
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_BUDS3:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 60
    .line 61
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 62
    .line 63
    const-string v1, "ROUTED_TO_APP_MIRRORING"

    .line 64
    .line 65
    const/4 v2, 0x6

    .line 66
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 67
    .line 68
    .line 69
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_APP_MIRRORING:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 70
    .line 71
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 72
    .line 73
    const-string v1, "ROUTED_TO_REMOTE_SPEAKER"

    .line 74
    .line 75
    const/4 v2, 0x7

    .line 76
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 77
    .line 78
    .line 79
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_REMOTE_SPEAKER:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 80
    .line 81
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 82
    .line 83
    const-string v1, "ROUTED_TO_HEADSET"

    .line 84
    .line 85
    const/16 v2, 0x8

    .line 86
    .line 87
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 88
    .line 89
    .line 90
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HEADSET:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 91
    .line 92
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 93
    .line 94
    const-string v1, "ROUTED_TO_HOME_MINI"

    .line 95
    .line 96
    const/16 v2, 0x9

    .line 97
    .line 98
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 99
    .line 100
    .line 101
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HOME_MINI:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 102
    .line 103
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 104
    .line 105
    const-string v1, "ROUTED_TO_HEARING_AID"

    .line 106
    .line 107
    const/16 v2, 0xa

    .line 108
    .line 109
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 110
    .line 111
    .line 112
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->ROUTED_TO_HEARING_AID:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 113
    .line 114
    new-instance v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 115
    .line 116
    const-string v1, "DISABLED_FIXED_SESSION"

    .line 117
    .line 118
    const/16 v2, 0xb

    .line 119
    .line 120
    invoke-direct {v0, v1, v2}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;-><init>(Ljava/lang/String;I)V

    .line 121
    .line 122
    .line 123
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->DISABLED_FIXED_SESSION:Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 124
    .line 125
    invoke-static {}, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->$values()[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 126
    .line 127
    .line 128
    move-result-object v0

    .line 129
    sput-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->$VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 130
    .line 131
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

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;->$VALUES:[Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/systemui/splugins/volume/VolumeStreamState$BooleanStateKey;

    .line 8
    .line 9
    return-object v0
.end method
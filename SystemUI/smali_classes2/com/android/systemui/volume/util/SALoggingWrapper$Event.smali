.class public final enum Lcom/android/systemui/volume/util/SALoggingWrapper$Event;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/volume/util/SALoggingWrapper$Event;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum EXPAND:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_ACCESSIBILITY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_BIXBY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_MEDIA:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_NOTIFICATION:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_RINGTONE:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum FINE_CONTROL_SYSTEM:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum MEDIA_DEFAULT_OFF:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum MEDIA_DEFAULT_ON:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum SAFETY_CANCEL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum SAFETY_OK:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum SHRINK:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum SUB_VOLUME_PANEL_FINE_CONTROL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum SUB_VOLUME_PANEL_SHOW:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum VOLUME_KEY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum VOLUME_LIMITER_CANCEL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

.field public static final enum VOLUME_LIMITER_SETTING:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;


# direct methods
.method public static constructor <clinit>()V
    .locals 19

    .line 1
    new-instance v0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 2
    .line 3
    const-string v1, "EXPAND"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->EXPAND:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 12
    .line 13
    const-string v2, "SHRINK"

    .line 14
    .line 15
    const/4 v3, 0x1

    .line 16
    invoke-direct {v1, v2, v3}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->SHRINK:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 20
    .line 21
    new-instance v2, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 22
    .line 23
    const-string v3, "MEDIA_DEFAULT_ON"

    .line 24
    .line 25
    const/4 v4, 0x2

    .line 26
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v2, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->MEDIA_DEFAULT_ON:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 30
    .line 31
    new-instance v3, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 32
    .line 33
    const-string v4, "MEDIA_DEFAULT_OFF"

    .line 34
    .line 35
    const/4 v5, 0x3

    .line 36
    invoke-direct {v3, v4, v5}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v3, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->MEDIA_DEFAULT_OFF:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 40
    .line 41
    new-instance v4, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 42
    .line 43
    const-string v5, "SAFETY_CANCEL"

    .line 44
    .line 45
    const/4 v6, 0x4

    .line 46
    invoke-direct {v4, v5, v6}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v4, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->SAFETY_CANCEL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 50
    .line 51
    new-instance v5, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 52
    .line 53
    const-string v6, "SAFETY_OK"

    .line 54
    .line 55
    const/4 v7, 0x5

    .line 56
    invoke-direct {v5, v6, v7}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 57
    .line 58
    .line 59
    sput-object v5, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->SAFETY_OK:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 60
    .line 61
    new-instance v6, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 62
    .line 63
    const-string v7, "VOLUME_LIMITER_SETTING"

    .line 64
    .line 65
    const/4 v8, 0x6

    .line 66
    invoke-direct {v6, v7, v8}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 67
    .line 68
    .line 69
    sput-object v6, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->VOLUME_LIMITER_SETTING:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 70
    .line 71
    new-instance v7, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 72
    .line 73
    const-string v8, "VOLUME_LIMITER_CANCEL"

    .line 74
    .line 75
    const/4 v9, 0x7

    .line 76
    invoke-direct {v7, v8, v9}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 77
    .line 78
    .line 79
    sput-object v7, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->VOLUME_LIMITER_CANCEL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 80
    .line 81
    new-instance v8, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 82
    .line 83
    const-string v9, "VOLUME_KEY"

    .line 84
    .line 85
    const/16 v10, 0x8

    .line 86
    .line 87
    invoke-direct {v8, v9, v10}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 88
    .line 89
    .line 90
    sput-object v8, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->VOLUME_KEY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 91
    .line 92
    new-instance v9, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 93
    .line 94
    const-string v10, "FINE_CONTROL_RINGTONE"

    .line 95
    .line 96
    const/16 v11, 0x9

    .line 97
    .line 98
    invoke-direct {v9, v10, v11}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 99
    .line 100
    .line 101
    sput-object v9, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_RINGTONE:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 102
    .line 103
    new-instance v10, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 104
    .line 105
    const-string v11, "FINE_CONTROL_NOTIFICATION"

    .line 106
    .line 107
    const/16 v12, 0xa

    .line 108
    .line 109
    invoke-direct {v10, v11, v12}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 110
    .line 111
    .line 112
    sput-object v10, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_NOTIFICATION:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 113
    .line 114
    new-instance v11, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 115
    .line 116
    const-string v12, "FINE_CONTROL_MEDIA"

    .line 117
    .line 118
    const/16 v13, 0xb

    .line 119
    .line 120
    invoke-direct {v11, v12, v13}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 121
    .line 122
    .line 123
    sput-object v11, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_MEDIA:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 124
    .line 125
    new-instance v12, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 126
    .line 127
    const-string v13, "FINE_CONTROL_SYSTEM"

    .line 128
    .line 129
    const/16 v14, 0xc

    .line 130
    .line 131
    invoke-direct {v12, v13, v14}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 132
    .line 133
    .line 134
    sput-object v12, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_SYSTEM:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 135
    .line 136
    new-instance v13, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 137
    .line 138
    const-string v14, "FINE_CONTROL_BIXBY"

    .line 139
    .line 140
    const/16 v15, 0xd

    .line 141
    .line 142
    invoke-direct {v13, v14, v15}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 143
    .line 144
    .line 145
    sput-object v13, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_BIXBY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 146
    .line 147
    new-instance v14, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 148
    .line 149
    const-string v15, "FINE_CONTROL_ACCESSIBILITY"

    .line 150
    .line 151
    move-object/from16 v16, v13

    .line 152
    .line 153
    const/16 v13, 0xe

    .line 154
    .line 155
    invoke-direct {v14, v15, v13}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 156
    .line 157
    .line 158
    sput-object v14, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->FINE_CONTROL_ACCESSIBILITY:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 159
    .line 160
    new-instance v15, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 161
    .line 162
    const-string v13, "SUB_VOLUME_PANEL_SHOW"

    .line 163
    .line 164
    move-object/from16 v17, v14

    .line 165
    .line 166
    const/16 v14, 0xf

    .line 167
    .line 168
    invoke-direct {v15, v13, v14}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 169
    .line 170
    .line 171
    sput-object v15, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->SUB_VOLUME_PANEL_SHOW:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 172
    .line 173
    new-instance v14, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 174
    .line 175
    const-string v13, "SUB_VOLUME_PANEL_FINE_CONTROL"

    .line 176
    .line 177
    move-object/from16 v18, v15

    .line 178
    .line 179
    const/16 v15, 0x10

    .line 180
    .line 181
    invoke-direct {v14, v13, v15}, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;-><init>(Ljava/lang/String;I)V

    .line 182
    .line 183
    .line 184
    sput-object v14, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->SUB_VOLUME_PANEL_FINE_CONTROL:Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 185
    .line 186
    move-object/from16 v13, v16

    .line 187
    .line 188
    move-object/from16 v16, v14

    .line 189
    .line 190
    move-object/from16 v14, v17

    .line 191
    .line 192
    move-object/from16 v15, v18

    .line 193
    .line 194
    filled-new-array/range {v0 .. v16}, [Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 195
    .line 196
    .line 197
    move-result-object v0

    .line 198
    sput-object v0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->$VALUES:[Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 199
    .line 200
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

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/volume/util/SALoggingWrapper$Event;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/volume/util/SALoggingWrapper$Event;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/volume/util/SALoggingWrapper$Event;->$VALUES:[Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/volume/util/SALoggingWrapper$Event;

    .line 8
    .line 9
    return-object v0
.end method
.class public final enum Lcom/android/systemui/keyguard/shared/model/DozeStateModel;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/keyguard/shared/model/DozeStateModel;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final Companion:Lcom/android/systemui/keyguard/shared/model/DozeStateModel$Companion;

.field public static final enum DOZE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_AOD:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_AOD_DOCKED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_AOD_PAUSED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_AOD_PAUSING:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_DISPLAY_STATE_ON:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_MOD:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_PULSE_DONE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_PULSING:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_PULSING_BRIGHT:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_REQUEST_PULSE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_SUSPEND_TRIGGERS:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum DOZE_TRANSITION_ENDED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum FINISH:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum INITIALIZED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum SCRIM_AOD_ENDED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

.field public static final enum UNINITIALIZED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;


# direct methods
.method public static constructor <clinit>()V
    .locals 19

    .line 1
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 2
    .line 3
    const-string v1, "UNINITIALIZED"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->UNINITIALIZED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 12
    .line 13
    const-string v2, "INITIALIZED"

    .line 14
    .line 15
    const/4 v3, 0x1

    .line 16
    invoke-direct {v1, v2, v3}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->INITIALIZED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 20
    .line 21
    new-instance v2, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 22
    .line 23
    const-string v3, "DOZE"

    .line 24
    .line 25
    const/4 v4, 0x2

    .line 26
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v2, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 30
    .line 31
    new-instance v3, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 32
    .line 33
    const-string v4, "DOZE_SUSPEND_TRIGGERS"

    .line 34
    .line 35
    const/4 v5, 0x3

    .line 36
    invoke-direct {v3, v4, v5}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v3, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_SUSPEND_TRIGGERS:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 40
    .line 41
    new-instance v4, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 42
    .line 43
    const-string v5, "DOZE_AOD"

    .line 44
    .line 45
    const/4 v6, 0x4

    .line 46
    invoke-direct {v4, v5, v6}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v4, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_AOD:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 50
    .line 51
    new-instance v5, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 52
    .line 53
    const-string v6, "DOZE_REQUEST_PULSE"

    .line 54
    .line 55
    const/4 v7, 0x5

    .line 56
    invoke-direct {v5, v6, v7}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 57
    .line 58
    .line 59
    sput-object v5, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_REQUEST_PULSE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 60
    .line 61
    new-instance v6, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 62
    .line 63
    const-string v7, "DOZE_PULSING"

    .line 64
    .line 65
    const/4 v8, 0x6

    .line 66
    invoke-direct {v6, v7, v8}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 67
    .line 68
    .line 69
    sput-object v6, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_PULSING:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 70
    .line 71
    new-instance v7, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 72
    .line 73
    const-string v8, "DOZE_PULSING_BRIGHT"

    .line 74
    .line 75
    const/4 v9, 0x7

    .line 76
    invoke-direct {v7, v8, v9}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 77
    .line 78
    .line 79
    sput-object v7, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_PULSING_BRIGHT:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 80
    .line 81
    new-instance v8, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 82
    .line 83
    const-string v9, "DOZE_PULSE_DONE"

    .line 84
    .line 85
    const/16 v10, 0x8

    .line 86
    .line 87
    invoke-direct {v8, v9, v10}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 88
    .line 89
    .line 90
    sput-object v8, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_PULSE_DONE:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 91
    .line 92
    new-instance v9, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 93
    .line 94
    const-string v10, "FINISH"

    .line 95
    .line 96
    const/16 v11, 0x9

    .line 97
    .line 98
    invoke-direct {v9, v10, v11}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 99
    .line 100
    .line 101
    sput-object v9, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->FINISH:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 102
    .line 103
    new-instance v10, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 104
    .line 105
    const-string v11, "DOZE_AOD_PAUSED"

    .line 106
    .line 107
    const/16 v12, 0xa

    .line 108
    .line 109
    invoke-direct {v10, v11, v12}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 110
    .line 111
    .line 112
    sput-object v10, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_AOD_PAUSED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 113
    .line 114
    new-instance v11, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 115
    .line 116
    const-string v12, "DOZE_AOD_PAUSING"

    .line 117
    .line 118
    const/16 v13, 0xb

    .line 119
    .line 120
    invoke-direct {v11, v12, v13}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 121
    .line 122
    .line 123
    sput-object v11, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_AOD_PAUSING:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 124
    .line 125
    new-instance v12, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 126
    .line 127
    const-string v13, "DOZE_AOD_DOCKED"

    .line 128
    .line 129
    const/16 v14, 0xc

    .line 130
    .line 131
    invoke-direct {v12, v13, v14}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 132
    .line 133
    .line 134
    sput-object v12, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_AOD_DOCKED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 135
    .line 136
    new-instance v13, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 137
    .line 138
    const-string v14, "DOZE_MOD"

    .line 139
    .line 140
    const/16 v15, 0xd

    .line 141
    .line 142
    invoke-direct {v13, v14, v15}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 143
    .line 144
    .line 145
    sput-object v13, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_MOD:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 146
    .line 147
    new-instance v14, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 148
    .line 149
    const-string v15, "SCRIM_AOD_ENDED"

    .line 150
    .line 151
    move-object/from16 v16, v13

    .line 152
    .line 153
    const/16 v13, 0xe

    .line 154
    .line 155
    invoke-direct {v14, v15, v13}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 156
    .line 157
    .line 158
    sput-object v14, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->SCRIM_AOD_ENDED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 159
    .line 160
    new-instance v15, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 161
    .line 162
    const-string v13, "DOZE_TRANSITION_ENDED"

    .line 163
    .line 164
    move-object/from16 v17, v14

    .line 165
    .line 166
    const/16 v14, 0xf

    .line 167
    .line 168
    invoke-direct {v15, v13, v14}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 169
    .line 170
    .line 171
    sput-object v15, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_TRANSITION_ENDED:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 172
    .line 173
    new-instance v14, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 174
    .line 175
    const-string v13, "DOZE_DISPLAY_STATE_ON"

    .line 176
    .line 177
    move-object/from16 v18, v15

    .line 178
    .line 179
    const/16 v15, 0x10

    .line 180
    .line 181
    invoke-direct {v14, v13, v15}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;-><init>(Ljava/lang/String;I)V

    .line 182
    .line 183
    .line 184
    sput-object v14, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->DOZE_DISPLAY_STATE_ON:Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

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
    filled-new-array/range {v0 .. v16}, [Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 195
    .line 196
    .line 197
    move-result-object v0

    .line 198
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->$VALUES:[Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 199
    .line 200
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel$Companion;

    .line 201
    .line 202
    const/4 v1, 0x0

    .line 203
    invoke-direct {v0, v1}, Lcom/android/systemui/keyguard/shared/model/DozeStateModel$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 204
    .line 205
    .line 206
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->Companion:Lcom/android/systemui/keyguard/shared/model/DozeStateModel$Companion;

    .line 207
    .line 208
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

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/keyguard/shared/model/DozeStateModel;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/keyguard/shared/model/DozeStateModel;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/keyguard/shared/model/DozeStateModel;->$VALUES:[Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/keyguard/shared/model/DozeStateModel;

    .line 8
    .line 9
    return-object v0
.end method

.class public final Lcom/android/systemui/monet/hct/Cam16;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final CAM16RGB_TO_XYZ:[[D

.field public static final XYZ_TO_CAM16RGB:[[D


# instance fields
.field public final chroma:D

.field public final hue:D

.field public final j:D


# direct methods
.method public static constructor <clinit>()V
    .locals 4

    .line 1
    const/4 v0, 0x3

    .line 2
    new-array v1, v0, [D

    .line 3
    .line 4
    fill-array-data v1, :array_0

    .line 5
    .line 6
    .line 7
    new-array v2, v0, [D

    .line 8
    .line 9
    fill-array-data v2, :array_1

    .line 10
    .line 11
    .line 12
    new-array v3, v0, [D

    .line 13
    .line 14
    fill-array-data v3, :array_2

    .line 15
    .line 16
    .line 17
    filled-new-array {v1, v2, v3}, [[D

    .line 18
    .line 19
    .line 20
    move-result-object v1

    .line 21
    sput-object v1, Lcom/android/systemui/monet/hct/Cam16;->XYZ_TO_CAM16RGB:[[D

    .line 22
    .line 23
    new-array v1, v0, [D

    .line 24
    .line 25
    fill-array-data v1, :array_3

    .line 26
    .line 27
    .line 28
    new-array v2, v0, [D

    .line 29
    .line 30
    fill-array-data v2, :array_4

    .line 31
    .line 32
    .line 33
    new-array v0, v0, [D

    .line 34
    .line 35
    fill-array-data v0, :array_5

    .line 36
    .line 37
    .line 38
    filled-new-array {v1, v2, v0}, [[D

    .line 39
    .line 40
    .line 41
    move-result-object v0

    .line 42
    sput-object v0, Lcom/android/systemui/monet/hct/Cam16;->CAM16RGB_TO_XYZ:[[D

    .line 43
    .line 44
    return-void

    .line 45
    :array_0
    .array-data 8
        0x3fd9aeb3dd11be6eL    # 0.401288
        0x3fe4ce379b77c02bL    # 0.650173
        -0x4055a6e75ff609ddL    # -0.051461
    .end array-data

    .line 46
    .line 47
    .line 48
    .line 49
    .line 50
    .line 51
    .line 52
    .line 53
    .line 54
    .line 55
    .line 56
    .line 57
    .line 58
    .line 59
    .line 60
    .line 61
    :array_1
    .array-data 8
        -0x402ffb9bed30f063L    # -0.250268
        0x3ff345479d4d8341L    # 1.204414
        0x3fa77a2cecc814d7L    # 0.045854
    .end array-data

    .line 62
    .line 63
    .line 64
    .line 65
    .line 66
    .line 67
    .line 68
    .line 69
    .line 70
    .line 71
    .line 72
    .line 73
    .line 74
    .line 75
    .line 76
    .line 77
    :array_2
    .array-data 8
        -0x409ef8055fbb517aL    # -0.002079
        0x3fa9103c8e25c811L    # 0.048952
        0x3fee800431bde82dL    # 0.953127
    .end array-data

    .line 78
    .line 79
    .line 80
    .line 81
    .line 82
    .line 83
    .line 84
    .line 85
    .line 86
    .line 87
    .line 88
    .line 89
    :array_3
    .array-data 8
        0x3ffdcb079afef467L    # 1.8620678
        -0x400fd1e697792de9L    # -1.0112547
        0x3fc3188d6a8c3ae1L    # 0.14918678
    .end array-data

    :array_4
    .array-data 8
        0x3fd8cd3c1de87346L    # 0.38752654
        0x3fe3e2e5bddf7419L    # 0.62144744
        -0x407d9f0ccb1490dcL    # -0.00897398
    .end array-data

    :array_5
    .array-data 8
        -0x406fc73eee525892L    # -0.0158415
        -0x405e8770215031c7L    # -0.03412294
        0x3ff0cca7787f6d9eL    # 1.0499644
    .end array-data
.end method

.method private constructor <init>(DDDDDDDDD)V
    .locals 3

    .line 1
    move-object v0, p0

    .line 2
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 3
    .line 4
    .line 5
    move-wide v1, p1

    .line 6
    iput-wide v1, v0, Lcom/android/systemui/monet/hct/Cam16;->hue:D

    .line 7
    .line 8
    move-wide v1, p3

    .line 9
    iput-wide v1, v0, Lcom/android/systemui/monet/hct/Cam16;->chroma:D

    .line 10
    .line 11
    move-wide v1, p5

    .line 12
    iput-wide v1, v0, Lcom/android/systemui/monet/hct/Cam16;->j:D

    .line 13
    .line 14
    return-void
.end method

.method public static fromInt(I)Lcom/android/systemui/monet/hct/Cam16;
    .locals 13

    .line 1
    sget-object v6, Lcom/android/systemui/monet/hct/ViewingConditions;->DEFAULT:Lcom/android/systemui/monet/hct/ViewingConditions;

    .line 2
    .line 3
    const/high16 v0, 0xff0000

    .line 4
    .line 5
    and-int/2addr v0, p0

    .line 6
    shr-int/lit8 v0, v0, 0x10

    .line 7
    .line 8
    const v1, 0xff00

    .line 9
    .line 10
    .line 11
    and-int/2addr v1, p0

    .line 12
    shr-int/lit8 v1, v1, 0x8

    .line 13
    .line 14
    and-int/lit16 p0, p0, 0xff

    .line 15
    .line 16
    invoke-static {v0}, Lcom/android/systemui/monet/utils/ColorUtils;->linearized(I)D

    .line 17
    .line 18
    .line 19
    move-result-wide v2

    .line 20
    invoke-static {v1}, Lcom/android/systemui/monet/utils/ColorUtils;->linearized(I)D

    .line 21
    .line 22
    .line 23
    move-result-wide v0

    .line 24
    invoke-static {p0}, Lcom/android/systemui/monet/utils/ColorUtils;->linearized(I)D

    .line 25
    .line 26
    .line 27
    move-result-wide v4

    .line 28
    const-wide v7, 0x3fda63c2e8477c96L    # 0.41233895

    .line 29
    .line 30
    .line 31
    .line 32
    .line 33
    mul-double/2addr v7, v2

    .line 34
    const-wide v9, 0x3fd6e341ae4b2c79L    # 0.35762064

    .line 35
    .line 36
    .line 37
    .line 38
    .line 39
    mul-double/2addr v9, v0

    .line 40
    add-double/2addr v9, v7

    .line 41
    const-wide v7, 0x3fc71af7273e5d5eL    # 0.18051042

    .line 42
    .line 43
    .line 44
    .line 45
    .line 46
    mul-double/2addr v7, v4

    .line 47
    add-double/2addr v7, v9

    .line 48
    const-wide v9, 0x3fcb367a0f9096bcL    # 0.2126

    .line 49
    .line 50
    .line 51
    .line 52
    .line 53
    mul-double/2addr v9, v2

    .line 54
    const-wide v11, 0x3fe6e2eb1c432ca5L    # 0.7152

    .line 55
    .line 56
    .line 57
    .line 58
    .line 59
    mul-double/2addr v11, v0

    .line 60
    add-double/2addr v11, v9

    .line 61
    const-wide v9, 0x3fb27bb2fec56d5dL    # 0.0722

    .line 62
    .line 63
    .line 64
    .line 65
    .line 66
    mul-double/2addr v9, v4

    .line 67
    add-double/2addr v9, v11

    .line 68
    const-wide v11, 0x3f93c8fde0401c25L    # 0.01932141

    .line 69
    .line 70
    .line 71
    .line 72
    .line 73
    mul-double/2addr v2, v11

    .line 74
    const-wide v11, 0x3fbe818525c434ceL    # 0.11916382

    .line 75
    .line 76
    .line 77
    .line 78
    .line 79
    mul-double/2addr v0, v11

    .line 80
    add-double/2addr v0, v2

    .line 81
    const-wide v2, 0x3fee693974c0c730L    # 0.95034478

    .line 82
    .line 83
    .line 84
    .line 85
    .line 86
    mul-double/2addr v4, v2

    .line 87
    add-double/2addr v4, v0

    .line 88
    move-wide v0, v7

    .line 89
    move-wide v2, v9

    .line 90
    invoke-static/range {v0 .. v6}, Lcom/android/systemui/monet/hct/Cam16;->fromXyzInViewingConditions(DDDLcom/android/systemui/monet/hct/ViewingConditions;)Lcom/android/systemui/monet/hct/Cam16;

    .line 91
    .line 92
    .line 93
    move-result-object p0

    .line 94
    return-object p0
.end method

.method public static fromXyzInViewingConditions(DDDLcom/android/systemui/monet/hct/ViewingConditions;)Lcom/android/systemui/monet/hct/Cam16;
    .locals 37

    .line 1
    move-object/from16 v0, p6

    .line 2
    .line 3
    sget-object v1, Lcom/android/systemui/monet/hct/Cam16;->XYZ_TO_CAM16RGB:[[D

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    aget-object v3, v1, v2

    .line 7
    .line 8
    aget-wide v4, v3, v2

    .line 9
    .line 10
    mul-double v4, v4, p0

    .line 11
    .line 12
    const/4 v6, 0x1

    .line 13
    aget-wide v7, v3, v6

    .line 14
    .line 15
    mul-double v7, v7, p2

    .line 16
    .line 17
    add-double/2addr v7, v4

    .line 18
    const/4 v4, 0x2

    .line 19
    aget-wide v9, v3, v4

    .line 20
    .line 21
    mul-double v9, v9, p4

    .line 22
    .line 23
    add-double/2addr v9, v7

    .line 24
    aget-object v3, v1, v6

    .line 25
    .line 26
    aget-wide v7, v3, v2

    .line 27
    .line 28
    mul-double v7, v7, p0

    .line 29
    .line 30
    aget-wide v11, v3, v6

    .line 31
    .line 32
    mul-double v11, v11, p2

    .line 33
    .line 34
    add-double/2addr v11, v7

    .line 35
    aget-wide v7, v3, v4

    .line 36
    .line 37
    mul-double v7, v7, p4

    .line 38
    .line 39
    add-double/2addr v7, v11

    .line 40
    aget-object v1, v1, v4

    .line 41
    .line 42
    aget-wide v11, v1, v2

    .line 43
    .line 44
    mul-double v11, v11, p0

    .line 45
    .line 46
    aget-wide v13, v1, v6

    .line 47
    .line 48
    mul-double v13, v13, p2

    .line 49
    .line 50
    add-double/2addr v13, v11

    .line 51
    aget-wide v11, v1, v4

    .line 52
    .line 53
    mul-double v11, v11, p4

    .line 54
    .line 55
    add-double/2addr v11, v13

    .line 56
    iget-object v1, v0, Lcom/android/systemui/monet/hct/ViewingConditions;->rgbD:[D

    .line 57
    .line 58
    aget-wide v2, v1, v2

    .line 59
    .line 60
    mul-double/2addr v2, v9

    .line 61
    aget-wide v5, v1, v6

    .line 62
    .line 63
    mul-double/2addr v5, v7

    .line 64
    aget-wide v7, v1, v4

    .line 65
    .line 66
    mul-double/2addr v7, v11

    .line 67
    invoke-static {v2, v3}, Ljava/lang/Math;->abs(D)D

    .line 68
    .line 69
    .line 70
    move-result-wide v9

    .line 71
    iget-wide v11, v0, Lcom/android/systemui/monet/hct/ViewingConditions;->fl:D

    .line 72
    .line 73
    mul-double/2addr v9, v11

    .line 74
    const-wide/high16 v13, 0x4059000000000000L    # 100.0

    .line 75
    .line 76
    div-double/2addr v9, v13

    .line 77
    const-wide v13, 0x3fdae147ae147ae1L    # 0.42

    .line 78
    .line 79
    .line 80
    .line 81
    .line 82
    invoke-static {v9, v10, v13, v14}, Ljava/lang/Math;->pow(DD)D

    .line 83
    .line 84
    .line 85
    move-result-wide v9

    .line 86
    invoke-static {v5, v6}, Ljava/lang/Math;->abs(D)D

    .line 87
    .line 88
    .line 89
    move-result-wide v15

    .line 90
    mul-double/2addr v15, v11

    .line 91
    const-wide/high16 v17, 0x4059000000000000L    # 100.0

    .line 92
    .line 93
    div-double v0, v15, v17

    .line 94
    .line 95
    invoke-static {v0, v1, v13, v14}, Ljava/lang/Math;->pow(DD)D

    .line 96
    .line 97
    .line 98
    move-result-wide v0

    .line 99
    invoke-static {v7, v8}, Ljava/lang/Math;->abs(D)D

    .line 100
    .line 101
    .line 102
    move-result-wide v15

    .line 103
    mul-double/2addr v15, v11

    .line 104
    div-double v11, v15, v17

    .line 105
    .line 106
    invoke-static {v11, v12, v13, v14}, Ljava/lang/Math;->pow(DD)D

    .line 107
    .line 108
    .line 109
    move-result-wide v11

    .line 110
    invoke-static {v2, v3}, Ljava/lang/Math;->signum(D)D

    .line 111
    .line 112
    .line 113
    move-result-wide v2

    .line 114
    const-wide/high16 v13, 0x4079000000000000L    # 400.0

    .line 115
    .line 116
    mul-double/2addr v2, v13

    .line 117
    mul-double/2addr v2, v9

    .line 118
    const-wide v15, 0x403b2147ae147ae1L    # 27.13

    .line 119
    .line 120
    .line 121
    .line 122
    .line 123
    add-double/2addr v9, v15

    .line 124
    div-double/2addr v2, v9

    .line 125
    invoke-static {v5, v6}, Ljava/lang/Math;->signum(D)D

    .line 126
    .line 127
    .line 128
    move-result-wide v4

    .line 129
    mul-double/2addr v4, v13

    .line 130
    mul-double/2addr v4, v0

    .line 131
    add-double/2addr v0, v15

    .line 132
    div-double/2addr v4, v0

    .line 133
    invoke-static {v7, v8}, Ljava/lang/Math;->signum(D)D

    .line 134
    .line 135
    .line 136
    move-result-wide v0

    .line 137
    mul-double/2addr v0, v13

    .line 138
    mul-double/2addr v0, v11

    .line 139
    add-double/2addr v11, v15

    .line 140
    div-double/2addr v0, v11

    .line 141
    const-wide/high16 v6, 0x4026000000000000L    # 11.0

    .line 142
    .line 143
    mul-double v8, v2, v6

    .line 144
    .line 145
    const-wide/high16 v10, -0x3fd8000000000000L    # -12.0

    .line 146
    .line 147
    mul-double/2addr v10, v4

    .line 148
    add-double/2addr v10, v8

    .line 149
    add-double/2addr v10, v0

    .line 150
    div-double/2addr v10, v6

    .line 151
    add-double v6, v2, v4

    .line 152
    .line 153
    const-wide/high16 v8, 0x4000000000000000L    # 2.0

    .line 154
    .line 155
    mul-double v12, v0, v8

    .line 156
    .line 157
    sub-double/2addr v6, v12

    .line 158
    const-wide/high16 v12, 0x4022000000000000L    # 9.0

    .line 159
    .line 160
    div-double/2addr v6, v12

    .line 161
    const-wide/high16 v12, 0x4034000000000000L    # 20.0

    .line 162
    .line 163
    mul-double v14, v2, v12

    .line 164
    .line 165
    mul-double/2addr v4, v12

    .line 166
    add-double/2addr v14, v4

    .line 167
    const-wide/high16 v16, 0x4035000000000000L    # 21.0

    .line 168
    .line 169
    mul-double v16, v16, v0

    .line 170
    .line 171
    add-double v16, v16, v14

    .line 172
    .line 173
    div-double v16, v16, v12

    .line 174
    .line 175
    const-wide/high16 v14, 0x4044000000000000L    # 40.0

    .line 176
    .line 177
    mul-double/2addr v2, v14

    .line 178
    add-double/2addr v2, v4

    .line 179
    add-double/2addr v2, v0

    .line 180
    div-double/2addr v2, v12

    .line 181
    invoke-static {v6, v7, v10, v11}, Ljava/lang/Math;->atan2(DD)D

    .line 182
    .line 183
    .line 184
    move-result-wide v0

    .line 185
    invoke-static {v0, v1}, Ljava/lang/Math;->toDegrees(D)D

    .line 186
    .line 187
    .line 188
    move-result-wide v0

    .line 189
    const-wide/16 v4, 0x0

    .line 190
    .line 191
    cmpg-double v4, v0, v4

    .line 192
    .line 193
    const-wide v12, 0x4076800000000000L    # 360.0

    .line 194
    .line 195
    .line 196
    .line 197
    .line 198
    if-gez v4, :cond_0

    .line 199
    .line 200
    add-double/2addr v0, v12

    .line 201
    goto :goto_0

    .line 202
    :cond_0
    cmpl-double v4, v0, v12

    .line 203
    .line 204
    if-ltz v4, :cond_1

    .line 205
    .line 206
    sub-double/2addr v0, v12

    .line 207
    :cond_1
    :goto_0
    move-wide/from16 v19, v0

    .line 208
    .line 209
    invoke-static/range {v19 .. v20}, Ljava/lang/Math;->toRadians(D)D

    .line 210
    .line 211
    .line 212
    move-result-wide v0

    .line 213
    move-object/from16 v4, p6

    .line 214
    .line 215
    iget-wide v14, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->nbb:D

    .line 216
    .line 217
    mul-double/2addr v2, v14

    .line 218
    iget-wide v14, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->aw:D

    .line 219
    .line 220
    div-double/2addr v2, v14

    .line 221
    iget-wide v8, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->z:D

    .line 222
    .line 223
    iget-wide v12, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->c:D

    .line 224
    .line 225
    mul-double/2addr v8, v12

    .line 226
    invoke-static {v2, v3, v8, v9}, Ljava/lang/Math;->pow(DD)D

    .line 227
    .line 228
    .line 229
    move-result-wide v2

    .line 230
    const-wide/high16 v8, 0x4059000000000000L    # 100.0

    .line 231
    .line 232
    mul-double v23, v2, v8

    .line 233
    .line 234
    const-wide/high16 v2, 0x4010000000000000L    # 4.0

    .line 235
    .line 236
    div-double v21, v2, v12

    .line 237
    .line 238
    div-double v8, v23, v8

    .line 239
    .line 240
    invoke-static {v8, v9}, Ljava/lang/Math;->sqrt(D)D

    .line 241
    .line 242
    .line 243
    move-result-wide v25

    .line 244
    mul-double v25, v25, v21

    .line 245
    .line 246
    add-double/2addr v14, v2

    .line 247
    mul-double v25, v25, v14

    .line 248
    .line 249
    iget-wide v2, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->flRoot:D

    .line 250
    .line 251
    mul-double v25, v25, v2

    .line 252
    .line 253
    const-wide v21, 0x403423d70a3d70a4L    # 20.14

    .line 254
    .line 255
    .line 256
    .line 257
    .line 258
    cmpg-double v5, v19, v21

    .line 259
    .line 260
    if-gez v5, :cond_2

    .line 261
    .line 262
    const-wide v21, 0x4076800000000000L    # 360.0

    .line 263
    .line 264
    .line 265
    .line 266
    .line 267
    add-double v21, v19, v21

    .line 268
    .line 269
    goto :goto_1

    .line 270
    :cond_2
    move-wide/from16 v21, v19

    .line 271
    .line 272
    :goto_1
    invoke-static/range {v21 .. v22}, Ljava/lang/Math;->toRadians(D)D

    .line 273
    .line 274
    .line 275
    move-result-wide v21

    .line 276
    const-wide/high16 v27, 0x4000000000000000L    # 2.0

    .line 277
    .line 278
    add-double v21, v21, v27

    .line 279
    .line 280
    invoke-static/range {v21 .. v22}, Ljava/lang/Math;->cos(D)D

    .line 281
    .line 282
    .line 283
    move-result-wide v21

    .line 284
    const-wide v27, 0x400e666666666666L    # 3.8

    .line 285
    .line 286
    .line 287
    .line 288
    .line 289
    add-double v21, v21, v27

    .line 290
    .line 291
    const-wide/high16 v27, 0x3fd0000000000000L    # 0.25

    .line 292
    .line 293
    mul-double v21, v21, v27

    .line 294
    .line 295
    const-wide v27, 0x40ae0c4ec4ec4ec5L    # 3846.153846153846

    .line 296
    .line 297
    .line 298
    .line 299
    .line 300
    mul-double v21, v21, v27

    .line 301
    .line 302
    move-wide/from16 p0, v0

    .line 303
    .line 304
    iget-wide v0, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->nc:D

    .line 305
    .line 306
    mul-double v21, v21, v0

    .line 307
    .line 308
    iget-wide v0, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->ncb:D

    .line 309
    .line 310
    mul-double v21, v21, v0

    .line 311
    .line 312
    invoke-static {v10, v11, v6, v7}, Ljava/lang/Math;->hypot(DD)D

    .line 313
    .line 314
    .line 315
    move-result-wide v0

    .line 316
    mul-double v0, v0, v21

    .line 317
    .line 318
    const-wide v5, 0x3fd3851eb851eb85L    # 0.305

    .line 319
    .line 320
    .line 321
    .line 322
    .line 323
    add-double v16, v16, v5

    .line 324
    .line 325
    div-double v0, v0, v16

    .line 326
    .line 327
    const-wide v5, 0x3fd28f5c28f5c28fL    # 0.29

    .line 328
    .line 329
    .line 330
    .line 331
    .line 332
    iget-wide v10, v4, Lcom/android/systemui/monet/hct/ViewingConditions;->n:D

    .line 333
    .line 334
    invoke-static {v5, v6, v10, v11}, Ljava/lang/Math;->pow(DD)D

    .line 335
    .line 336
    .line 337
    move-result-wide v4

    .line 338
    const-wide v6, 0x3ffa3d70a3d70a3dL    # 1.64

    .line 339
    .line 340
    .line 341
    .line 342
    .line 343
    sub-double/2addr v6, v4

    .line 344
    const-wide v4, 0x3fe75c28f5c28f5cL    # 0.73

    .line 345
    .line 346
    .line 347
    .line 348
    .line 349
    invoke-static {v6, v7, v4, v5}, Ljava/lang/Math;->pow(DD)D

    .line 350
    .line 351
    .line 352
    move-result-wide v4

    .line 353
    const-wide v6, 0x3feccccccccccccdL    # 0.9

    .line 354
    .line 355
    .line 356
    .line 357
    .line 358
    invoke-static {v0, v1, v6, v7}, Ljava/lang/Math;->pow(DD)D

    .line 359
    .line 360
    .line 361
    move-result-wide v0

    .line 362
    mul-double/2addr v0, v4

    .line 363
    invoke-static {v8, v9}, Ljava/lang/Math;->sqrt(D)D

    .line 364
    .line 365
    .line 366
    move-result-wide v4

    .line 367
    mul-double/2addr v4, v0

    .line 368
    move-wide/from16 v21, v4

    .line 369
    .line 370
    mul-double/2addr v4, v2

    .line 371
    move-wide/from16 v27, v4

    .line 372
    .line 373
    mul-double/2addr v0, v12

    .line 374
    div-double/2addr v0, v14

    .line 375
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 376
    .line 377
    .line 378
    move-result-wide v0

    .line 379
    const-wide/high16 v2, 0x4049000000000000L    # 50.0

    .line 380
    .line 381
    mul-double v29, v0, v2

    .line 382
    .line 383
    const-wide v0, 0x3ffb333333333334L    # 1.7000000000000002

    .line 384
    .line 385
    .line 386
    .line 387
    .line 388
    mul-double v0, v0, v23

    .line 389
    .line 390
    const-wide v2, 0x3f7cac083126e979L    # 0.007

    .line 391
    .line 392
    .line 393
    .line 394
    .line 395
    mul-double v2, v2, v23

    .line 396
    .line 397
    const-wide/high16 v6, 0x3ff0000000000000L    # 1.0

    .line 398
    .line 399
    add-double/2addr v2, v6

    .line 400
    div-double v31, v0, v2

    .line 401
    .line 402
    const-wide v0, 0x3f9758e219652bd4L    # 0.0228

    .line 403
    .line 404
    .line 405
    .line 406
    .line 407
    mul-double/2addr v4, v0

    .line 408
    invoke-static {v4, v5}, Ljava/lang/Math;->log1p(D)D

    .line 409
    .line 410
    .line 411
    move-result-wide v0

    .line 412
    const-wide v2, 0x4045ee08fb823ee0L    # 43.859649122807014

    .line 413
    .line 414
    .line 415
    .line 416
    .line 417
    mul-double/2addr v0, v2

    .line 418
    invoke-static/range {p0 .. p1}, Ljava/lang/Math;->cos(D)D

    .line 419
    .line 420
    .line 421
    move-result-wide v2

    .line 422
    mul-double v33, v2, v0

    .line 423
    .line 424
    invoke-static/range {p0 .. p1}, Ljava/lang/Math;->sin(D)D

    .line 425
    .line 426
    .line 427
    move-result-wide v2

    .line 428
    mul-double v35, v2, v0

    .line 429
    .line 430
    new-instance v0, Lcom/android/systemui/monet/hct/Cam16;

    .line 431
    .line 432
    move-object/from16 v18, v0

    .line 433
    .line 434
    invoke-direct/range {v18 .. v36}, Lcom/android/systemui/monet/hct/Cam16;-><init>(DDDDDDDDD)V

    .line 435
    .line 436
    .line 437
    return-object v0
.end method

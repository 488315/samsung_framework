.class public final Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/SmartReplyStateInflater;


# instance fields
.field public final activityManagerWrapper:Lcom/android/systemui/shared/system/ActivityManagerWrapper;

.field public final constants:Lcom/android/systemui/statusbar/policy/SmartReplyConstants;

.field public final devicePolicyManagerWrapper:Lcom/android/systemui/shared/system/DevicePolicyManagerWrapper;

.field public final packageManagerWrapper:Lcom/android/systemui/shared/system/PackageManagerWrapper;

.field public final smartActionsInflater:Lcom/android/systemui/statusbar/policy/SmartActionInflater;

.field public final smartRepliesInflater:Lcom/android/systemui/statusbar/policy/SmartReplyInflater;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/policy/SmartReplyConstants;Lcom/android/systemui/shared/system/ActivityManagerWrapper;Lcom/android/systemui/shared/system/PackageManagerWrapper;Lcom/android/systemui/shared/system/DevicePolicyManagerWrapper;Lcom/android/systemui/statusbar/policy/SmartReplyInflater;Lcom/android/systemui/statusbar/policy/SmartActionInflater;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->constants:Lcom/android/systemui/statusbar/policy/SmartReplyConstants;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->activityManagerWrapper:Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->packageManagerWrapper:Lcom/android/systemui/shared/system/PackageManagerWrapper;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->devicePolicyManagerWrapper:Lcom/android/systemui/shared/system/DevicePolicyManagerWrapper;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->smartRepliesInflater:Lcom/android/systemui/statusbar/policy/SmartReplyInflater;

    .line 13
    .line 14
    iput-object p6, p0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->smartActionsInflater:Lcom/android/systemui/statusbar/policy/SmartActionInflater;

    .line 15
    .line 16
    return-void
.end method


# virtual methods
.method public final inflateSmartReplyViewHolder(Landroid/content/Context;Landroid/content/Context;Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;)Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;
    .locals 16

    .line 1
    move-object/from16 v0, p4

    .line 2
    .line 3
    move-object/from16 v6, p3

    .line 4
    .line 5
    move-object/from16 v7, p5

    .line 6
    .line 7
    invoke-static {v6, v7}, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterKt;->shouldShowSmartReplyView(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;)Z

    .line 8
    .line 9
    .line 10
    move-result v1

    .line 11
    const/4 v2, 0x0

    .line 12
    if-nez v1, :cond_0

    .line 13
    .line 14
    new-instance v0, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;

    .line 15
    .line 16
    invoke-direct {v0, v2, v2}, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;-><init>(Lcom/android/systemui/statusbar/policy/SmartReplyView;Ljava/util/List;)V

    .line 17
    .line 18
    .line 19
    return-object v0

    .line 20
    :cond_0
    const/4 v1, 0x1

    .line 21
    const/4 v3, 0x0

    .line 22
    if-ne v0, v7, :cond_1

    .line 23
    .line 24
    goto/16 :goto_12

    .line 25
    .line 26
    :cond_1
    if-eqz v0, :cond_2a

    .line 27
    .line 28
    iget-boolean v4, v0, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->hasPhishingAction:Z

    .line 29
    .line 30
    iget-boolean v5, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->hasPhishingAction:Z

    .line 31
    .line 32
    if-eq v4, v5, :cond_2

    .line 33
    .line 34
    goto/16 :goto_13

    .line 35
    .line 36
    :cond_2
    iget-object v4, v0, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartReplies:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;

    .line 37
    .line 38
    if-eqz v4, :cond_3

    .line 39
    .line 40
    iget-object v4, v4, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;->choices:Ljava/util/List;

    .line 41
    .line 42
    goto :goto_0

    .line 43
    :cond_3
    move-object v4, v2

    .line 44
    :goto_0
    if-nez v4, :cond_4

    .line 45
    .line 46
    sget-object v4, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 47
    .line 48
    :cond_4
    iget-object v5, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartReplies:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;

    .line 49
    .line 50
    if-eqz v5, :cond_5

    .line 51
    .line 52
    iget-object v5, v5, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;->choices:Ljava/util/List;

    .line 53
    .line 54
    goto :goto_1

    .line 55
    :cond_5
    move-object v5, v2

    .line 56
    :goto_1
    if-nez v5, :cond_6

    .line 57
    .line 58
    sget-object v5, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 59
    .line 60
    :cond_6
    invoke-static {v4, v5}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 61
    .line 62
    .line 63
    move-result v4

    .line 64
    if-nez v4, :cond_7

    .line 65
    .line 66
    goto/16 :goto_13

    .line 67
    .line 68
    :cond_7
    iget-object v4, v0, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->suppressedActions:Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState$SuppressedActions;

    .line 69
    .line 70
    if-eqz v4, :cond_8

    .line 71
    .line 72
    iget-object v4, v4, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState$SuppressedActions;->suppressedActionIndices:Ljava/util/List;

    .line 73
    .line 74
    if-nez v4, :cond_9

    .line 75
    .line 76
    :cond_8
    sget-object v4, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 77
    .line 78
    :cond_9
    iget-object v5, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->suppressedActions:Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState$SuppressedActions;

    .line 79
    .line 80
    if-eqz v5, :cond_a

    .line 81
    .line 82
    iget-object v5, v5, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState$SuppressedActions;->suppressedActionIndices:Ljava/util/List;

    .line 83
    .line 84
    if-nez v5, :cond_b

    .line 85
    .line 86
    :cond_a
    sget-object v5, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 87
    .line 88
    :cond_b
    invoke-static {v4, v5}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 89
    .line 90
    .line 91
    move-result v4

    .line 92
    if-nez v4, :cond_c

    .line 93
    .line 94
    goto/16 :goto_13

    .line 95
    .line 96
    :cond_c
    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartActions:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;

    .line 97
    .line 98
    if-eqz v0, :cond_d

    .line 99
    .line 100
    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;->actions:Ljava/util/List;

    .line 101
    .line 102
    goto :goto_2

    .line 103
    :cond_d
    move-object v0, v2

    .line 104
    :goto_2
    if-nez v0, :cond_e

    .line 105
    .line 106
    sget-object v0, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 107
    .line 108
    :cond_e
    iget-object v4, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartActions:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;

    .line 109
    .line 110
    if-eqz v4, :cond_f

    .line 111
    .line 112
    iget-object v4, v4, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;->actions:Ljava/util/List;

    .line 113
    .line 114
    goto :goto_3

    .line 115
    :cond_f
    move-object v4, v2

    .line 116
    :goto_3
    if-nez v4, :cond_10

    .line 117
    .line 118
    sget-object v4, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 119
    .line 120
    :cond_10
    if-ne v0, v4, :cond_11

    .line 121
    .line 122
    goto/16 :goto_f

    .line 123
    .line 124
    :cond_11
    if-eqz v0, :cond_29

    .line 125
    .line 126
    if-nez v4, :cond_12

    .line 127
    .line 128
    goto/16 :goto_10

    .line 129
    .line 130
    :cond_12
    invoke-interface {v0}, Ljava/util/List;->size()I

    .line 131
    .line 132
    .line 133
    move-result v5

    .line 134
    invoke-interface {v4}, Ljava/util/List;->size()I

    .line 135
    .line 136
    .line 137
    move-result v8

    .line 138
    if-eq v5, v8, :cond_13

    .line 139
    .line 140
    goto/16 :goto_10

    .line 141
    .line 142
    :cond_13
    move v5, v3

    .line 143
    :goto_4
    invoke-interface {v0}, Ljava/util/List;->size()I

    .line 144
    .line 145
    .line 146
    move-result v8

    .line 147
    if-ge v5, v8, :cond_28

    .line 148
    .line 149
    invoke-interface {v0, v5}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 150
    .line 151
    .line 152
    move-result-object v8

    .line 153
    check-cast v8, Landroid/app/Notification$Action;

    .line 154
    .line 155
    invoke-interface {v4, v5}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 156
    .line 157
    .line 158
    move-result-object v9

    .line 159
    check-cast v9, Landroid/app/Notification$Action;

    .line 160
    .line 161
    iget-object v10, v8, Landroid/app/Notification$Action;->title:Ljava/lang/CharSequence;

    .line 162
    .line 163
    iget-object v11, v9, Landroid/app/Notification$Action;->title:Ljava/lang/CharSequence;

    .line 164
    .line 165
    invoke-static {v10, v11}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    .line 166
    .line 167
    .line 168
    move-result v10

    .line 169
    if-nez v10, :cond_14

    .line 170
    .line 171
    goto/16 :goto_10

    .line 172
    .line 173
    :cond_14
    invoke-virtual {v8}, Landroid/app/Notification$Action;->getIcon()Landroid/graphics/drawable/Icon;

    .line 174
    .line 175
    .line 176
    move-result-object v10

    .line 177
    invoke-virtual {v9}, Landroid/app/Notification$Action;->getIcon()Landroid/graphics/drawable/Icon;

    .line 178
    .line 179
    .line 180
    move-result-object v11

    .line 181
    if-ne v10, v11, :cond_15

    .line 182
    .line 183
    move v10, v3

    .line 184
    goto :goto_6

    .line 185
    :cond_15
    if-eqz v10, :cond_17

    .line 186
    .line 187
    if-nez v11, :cond_16

    .line 188
    .line 189
    goto :goto_5

    .line 190
    :cond_16
    invoke-virtual {v10, v11}, Landroid/graphics/drawable/Icon;->sameAs(Landroid/graphics/drawable/Icon;)Z

    .line 191
    .line 192
    .line 193
    move-result v10

    .line 194
    xor-int/2addr v10, v1

    .line 195
    goto :goto_6

    .line 196
    :cond_17
    :goto_5
    move v10, v1

    .line 197
    :goto_6
    if-eqz v10, :cond_18

    .line 198
    .line 199
    goto/16 :goto_10

    .line 200
    .line 201
    :cond_18
    iget-object v10, v8, Landroid/app/Notification$Action;->actionIntent:Landroid/app/PendingIntent;

    .line 202
    .line 203
    iget-object v11, v9, Landroid/app/Notification$Action;->actionIntent:Landroid/app/PendingIntent;

    .line 204
    .line 205
    invoke-static {v10, v11}, Ljava/util/Objects;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 206
    .line 207
    .line 208
    move-result v10

    .line 209
    if-nez v10, :cond_19

    .line 210
    .line 211
    goto/16 :goto_10

    .line 212
    .line 213
    :cond_19
    invoke-virtual {v8}, Landroid/app/Notification$Action;->getRemoteInputs()[Landroid/app/RemoteInput;

    .line 214
    .line 215
    .line 216
    move-result-object v8

    .line 217
    invoke-virtual {v9}, Landroid/app/Notification$Action;->getRemoteInputs()[Landroid/app/RemoteInput;

    .line 218
    .line 219
    .line 220
    move-result-object v9

    .line 221
    if-ne v8, v9, :cond_1a

    .line 222
    .line 223
    goto :goto_c

    .line 224
    :cond_1a
    if-eqz v8, :cond_26

    .line 225
    .line 226
    if-nez v9, :cond_1b

    .line 227
    .line 228
    goto :goto_d

    .line 229
    :cond_1b
    array-length v10, v8

    .line 230
    array-length v11, v9

    .line 231
    if-eq v10, v11, :cond_1c

    .line 232
    .line 233
    goto :goto_d

    .line 234
    :cond_1c
    move v10, v3

    .line 235
    :goto_7
    array-length v11, v8

    .line 236
    if-ge v10, v11, :cond_25

    .line 237
    .line 238
    aget-object v11, v8, v10

    .line 239
    .line 240
    aget-object v12, v9, v10

    .line 241
    .line 242
    invoke-virtual {v11}, Landroid/app/RemoteInput;->getLabel()Ljava/lang/CharSequence;

    .line 243
    .line 244
    .line 245
    move-result-object v13

    .line 246
    invoke-virtual {v12}, Landroid/app/RemoteInput;->getLabel()Ljava/lang/CharSequence;

    .line 247
    .line 248
    .line 249
    move-result-object v14

    .line 250
    invoke-static {v13, v14}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    .line 251
    .line 252
    .line 253
    move-result v13

    .line 254
    if-nez v13, :cond_1d

    .line 255
    .line 256
    goto :goto_d

    .line 257
    :cond_1d
    invoke-virtual {v11}, Landroid/app/RemoteInput;->getChoices()[Ljava/lang/CharSequence;

    .line 258
    .line 259
    .line 260
    move-result-object v11

    .line 261
    invoke-virtual {v12}, Landroid/app/RemoteInput;->getChoices()[Ljava/lang/CharSequence;

    .line 262
    .line 263
    .line 264
    move-result-object v12

    .line 265
    if-ne v11, v12, :cond_1e

    .line 266
    .line 267
    goto :goto_9

    .line 268
    :cond_1e
    if-eqz v11, :cond_23

    .line 269
    .line 270
    if-nez v12, :cond_1f

    .line 271
    .line 272
    goto :goto_a

    .line 273
    :cond_1f
    array-length v13, v11

    .line 274
    array-length v14, v12

    .line 275
    if-eq v13, v14, :cond_20

    .line 276
    .line 277
    goto :goto_a

    .line 278
    :cond_20
    move v13, v3

    .line 279
    :goto_8
    array-length v14, v11

    .line 280
    if-ge v13, v14, :cond_22

    .line 281
    .line 282
    aget-object v14, v11, v13

    .line 283
    .line 284
    aget-object v15, v12, v13

    .line 285
    .line 286
    invoke-static {v14, v15}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    .line 287
    .line 288
    .line 289
    move-result v14

    .line 290
    if-nez v14, :cond_21

    .line 291
    .line 292
    goto :goto_a

    .line 293
    :cond_21
    add-int/lit8 v13, v13, 0x1

    .line 294
    .line 295
    goto :goto_8

    .line 296
    :cond_22
    :goto_9
    move v11, v3

    .line 297
    goto :goto_b

    .line 298
    :cond_23
    :goto_a
    move v11, v1

    .line 299
    :goto_b
    if-eqz v11, :cond_24

    .line 300
    .line 301
    goto :goto_d

    .line 302
    :cond_24
    add-int/lit8 v10, v10, 0x1

    .line 303
    .line 304
    goto :goto_7

    .line 305
    :cond_25
    :goto_c
    move v8, v3

    .line 306
    goto :goto_e

    .line 307
    :cond_26
    :goto_d
    move v8, v1

    .line 308
    :goto_e
    if-eqz v8, :cond_27

    .line 309
    .line 310
    goto :goto_10

    .line 311
    :cond_27
    add-int/lit8 v5, v5, 0x1

    .line 312
    .line 313
    goto/16 :goto_4

    .line 314
    .line 315
    :cond_28
    :goto_f
    move v0, v3

    .line 316
    goto :goto_11

    .line 317
    :cond_29
    :goto_10
    move v0, v1

    .line 318
    :goto_11
    if-nez v0, :cond_2a

    .line 319
    .line 320
    :goto_12
    move v0, v1

    .line 321
    goto :goto_14

    .line 322
    :cond_2a
    :goto_13
    move v0, v3

    .line 323
    :goto_14
    xor-int/lit8 v8, v0, 0x1

    .line 324
    .line 325
    move-object/from16 v9, p0

    .line 326
    .line 327
    iget-object v0, v9, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;->constants:Lcom/android/systemui/statusbar/policy/SmartReplyConstants;

    .line 328
    .line 329
    sget v1, Lcom/android/systemui/statusbar/policy/SmartReplyView;->MEASURE_SPEC_ANY_LENGTH:I

    .line 330
    .line 331
    invoke-static/range {p1 .. p1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    .line 332
    .line 333
    .line 334
    move-result-object v1

    .line 335
    const v4, 0x7f0d0408

    .line 336
    .line 337
    .line 338
    invoke-virtual {v1, v4, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    .line 339
    .line 340
    .line 341
    move-result-object v1

    .line 342
    move-object v10, v1

    .line 343
    check-cast v10, Lcom/android/systemui/statusbar/policy/SmartReplyView;

    .line 344
    .line 345
    iget v1, v0, Lcom/android/systemui/statusbar/policy/SmartReplyConstants;->mMaxNumActions:I

    .line 346
    .line 347
    iput v1, v10, Lcom/android/systemui/statusbar/policy/SmartReplyView;->mMaxNumActions:I

    .line 348
    .line 349
    iget v1, v0, Lcom/android/systemui/statusbar/policy/SmartReplyConstants;->mMaxSqueezeRemeasureAttempts:I

    .line 350
    .line 351
    iput v1, v10, Lcom/android/systemui/statusbar/policy/SmartReplyView;->mMaxSqueezeRemeasureAttempts:I

    .line 352
    .line 353
    iget v0, v0, Lcom/android/systemui/statusbar/policy/SmartReplyConstants;->mMinNumSystemGeneratedReplies:I

    .line 354
    .line 355
    iput v0, v10, Lcom/android/systemui/statusbar/policy/SmartReplyView;->mMinNumSystemGeneratedReplies:I

    .line 356
    .line 357
    iget-object v4, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartReplies:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;

    .line 358
    .line 359
    if-eqz v4, :cond_2b

    .line 360
    .line 361
    iget-boolean v3, v4, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;->fromAssistant:Z

    .line 362
    .line 363
    :cond_2b
    iput-boolean v3, v10, Lcom/android/systemui/statusbar/policy/SmartReplyView;->mSmartRepliesGeneratedByAssistant:Z

    .line 364
    .line 365
    if-eqz v4, :cond_2c

    .line 366
    .line 367
    iget-object v0, v4, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;->choices:Ljava/util/List;

    .line 368
    .line 369
    new-instance v11, Lkotlin/collections/CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;

    .line 370
    .line 371
    invoke-direct {v11, v0}, Lkotlin/collections/CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;-><init>(Ljava/lang/Iterable;)V

    .line 372
    .line 373
    .line 374
    new-instance v12, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1;

    .line 375
    .line 376
    move-object v0, v12

    .line 377
    move-object/from16 v1, p0

    .line 378
    .line 379
    move-object v2, v10

    .line 380
    move-object/from16 v3, p3

    .line 381
    .line 382
    move v5, v8

    .line 383
    invoke-direct/range {v0 .. v5}, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1;-><init>(Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;Lcom/android/systemui/statusbar/policy/SmartReplyView;Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartReplies;Z)V

    .line 384
    .line 385
    .line 386
    new-instance v0, Lkotlin/sequences/TransformingIndexedSequence;

    .line 387
    .line 388
    invoke-direct {v0, v11, v12}, Lkotlin/sequences/TransformingIndexedSequence;-><init>(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V

    .line 389
    .line 390
    .line 391
    goto :goto_15

    .line 392
    :cond_2c
    sget-object v0, Lkotlin/sequences/EmptySequence;->INSTANCE:Lkotlin/sequences/EmptySequence;

    .line 393
    .line 394
    :goto_15
    move-object v11, v0

    .line 395
    iget-object v4, v7, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyState;->smartActions:Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;

    .line 396
    .line 397
    if-eqz v4, :cond_2d

    .line 398
    .line 399
    new-instance v7, Landroid/view/ContextThemeWrapper;

    .line 400
    .line 401
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 402
    .line 403
    .line 404
    move-result-object v0

    .line 405
    move-object/from16 v1, p2

    .line 406
    .line 407
    invoke-direct {v7, v1, v0}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;Landroid/content/res/Resources$Theme;)V

    .line 408
    .line 409
    .line 410
    iget-object v0, v4, Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;->actions:Ljava/util/List;

    .line 411
    .line 412
    new-instance v1, Lkotlin/collections/CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;

    .line 413
    .line 414
    invoke-direct {v1, v0}, Lkotlin/collections/CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;-><init>(Ljava/lang/Iterable;)V

    .line 415
    .line 416
    .line 417
    sget-object v0, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1;->INSTANCE:Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1;

    .line 418
    .line 419
    invoke-static {v1, v0}, Lkotlin/sequences/SequencesKt___SequencesKt;->filter(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/FilteringSequence;

    .line 420
    .line 421
    .line 422
    move-result-object v12

    .line 423
    new-instance v13, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2;

    .line 424
    .line 425
    move-object v0, v13

    .line 426
    move-object/from16 v1, p0

    .line 427
    .line 428
    move-object v2, v10

    .line 429
    move-object/from16 v3, p3

    .line 430
    .line 431
    move v5, v8

    .line 432
    move-object v6, v7

    .line 433
    invoke-direct/range {v0 .. v6}, Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2;-><init>(Lcom/android/systemui/statusbar/policy/SmartReplyStateInflaterImpl;Lcom/android/systemui/statusbar/policy/SmartReplyView;Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Lcom/android/systemui/statusbar/policy/SmartReplyView$SmartActions;ZLandroid/view/ContextThemeWrapper;)V

    .line 434
    .line 435
    .line 436
    new-instance v0, Lkotlin/sequences/TransformingIndexedSequence;

    .line 437
    .line 438
    invoke-direct {v0, v12, v13}, Lkotlin/sequences/TransformingIndexedSequence;-><init>(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V

    .line 439
    .line 440
    .line 441
    goto :goto_16

    .line 442
    :cond_2d
    sget-object v0, Lkotlin/sequences/EmptySequence;->INSTANCE:Lkotlin/sequences/EmptySequence;

    .line 443
    .line 444
    :goto_16
    new-instance v1, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;

    .line 445
    .line 446
    invoke-static {v11, v0}, Lkotlin/sequences/SequencesKt___SequencesKt;->plus(Lkotlin/sequences/Sequence;Lkotlin/sequences/Sequence;)Lkotlin/sequences/FlatteningSequence;

    .line 447
    .line 448
    .line 449
    move-result-object v0

    .line 450
    invoke-static {v0}, Lkotlin/sequences/SequencesKt___SequencesKt;->toList(Lkotlin/sequences/Sequence;)Ljava/util/List;

    .line 451
    .line 452
    .line 453
    move-result-object v0

    .line 454
    invoke-direct {v1, v10, v0}, Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;-><init>(Lcom/android/systemui/statusbar/policy/SmartReplyView;Ljava/util/List;)V

    .line 455
    .line 456
    .line 457
    return-object v1
.end method

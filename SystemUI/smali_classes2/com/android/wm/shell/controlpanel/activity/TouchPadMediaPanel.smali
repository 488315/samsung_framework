.class public final Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public displayX:I

.field public displayY:I

.field public final mActionViewIdMapFlip:Landroid/util/SparseArray;

.field public final mActionViewIdMapFold:Landroid/util/SparseArray;

.field public final mContext:Landroid/content/Context;

.field public final mFloatingPanelView:Landroid/widget/LinearLayout;

.field public mMediaArtistText:Landroid/widget/TextView;

.field public mMediaController:Landroid/media/session/MediaController;

.field public mMediaImageView:Landroid/widget/ImageView;

.field public mMediaNextButton:Landroid/widget/LinearLayout;

.field public mMediaPauseButton:Landroid/widget/LinearLayout;

.field public mMediaPreviousButton:Landroid/widget/LinearLayout;

.field public mMediaResumeButton:Landroid/widget/LinearLayout;

.field public mMediaTitleText:Landroid/widget/TextView;

.field public mPlaybackState:Landroid/media/session/PlaybackState;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/widget/LinearLayout;Landroid/media/session/MediaController;)V
    .locals 15

    .line 1
    move-object v0, p0

    .line 2
    move-object/from16 v1, p1

    .line 3
    .line 4
    move-object/from16 v2, p2

    .line 5
    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 7
    .line 8
    .line 9
    const/4 v3, 0x0

    .line 10
    iput-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 11
    .line 12
    new-instance v3, Landroid/util/SparseArray;

    .line 13
    .line 14
    invoke-direct {v3}, Landroid/util/SparseArray;-><init>()V

    .line 15
    .line 16
    .line 17
    iput-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mActionViewIdMapFold:Landroid/util/SparseArray;

    .line 18
    .line 19
    const-wide/16 v4, 0x10

    .line 20
    .line 21
    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 22
    .line 23
    .line 24
    move-result-object v4

    .line 25
    const v5, 0x7f0a008d

    .line 26
    .line 27
    .line 28
    invoke-virtual {v3, v5, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 29
    .line 30
    .line 31
    const-wide/16 v6, 0x20

    .line 32
    .line 33
    invoke-static {v6, v7}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 34
    .line 35
    .line 36
    move-result-object v4

    .line 37
    const v6, 0x7f0a008c

    .line 38
    .line 39
    .line 40
    invoke-virtual {v3, v6, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 41
    .line 42
    .line 43
    const-wide/16 v7, 0x4

    .line 44
    .line 45
    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 46
    .line 47
    .line 48
    move-result-object v4

    .line 49
    const v7, 0x7f0a0088

    .line 50
    .line 51
    .line 52
    invoke-virtual {v3, v7, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 53
    .line 54
    .line 55
    const-wide/16 v8, 0x2

    .line 56
    .line 57
    invoke-static {v8, v9}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 58
    .line 59
    .line 60
    move-result-object v8

    .line 61
    const v9, 0x7f0a0083

    .line 62
    .line 63
    .line 64
    invoke-virtual {v3, v9, v8}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 65
    .line 66
    .line 67
    new-instance v3, Landroid/util/SparseArray;

    .line 68
    .line 69
    invoke-direct {v3}, Landroid/util/SparseArray;-><init>()V

    .line 70
    .line 71
    .line 72
    iput-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mActionViewIdMapFlip:Landroid/util/SparseArray;

    .line 73
    .line 74
    invoke-virtual {v3, v7, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 75
    .line 76
    .line 77
    invoke-virtual {v3, v9, v8}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 78
    .line 79
    .line 80
    iput-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mContext:Landroid/content/Context;

    .line 81
    .line 82
    iput-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mFloatingPanelView:Landroid/widget/LinearLayout;

    .line 83
    .line 84
    move-object/from16 v3, p3

    .line 85
    .line 86
    iput-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 87
    .line 88
    const v3, 0x7f0a0be3

    .line 89
    .line 90
    .line 91
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 92
    .line 93
    .line 94
    move-result-object v4

    .line 95
    check-cast v4, Landroid/widget/TextView;

    .line 96
    .line 97
    iput-object v4, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 98
    .line 99
    const v4, 0x7f0a00f8

    .line 100
    .line 101
    .line 102
    invoke-virtual {v2, v4}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 103
    .line 104
    .line 105
    move-result-object v8

    .line 106
    check-cast v8, Landroid/widget/TextView;

    .line 107
    .line 108
    iput-object v8, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaArtistText:Landroid/widget/TextView;

    .line 109
    .line 110
    const v8, 0x7f0a0651

    .line 111
    .line 112
    .line 113
    invoke-virtual {v2, v8}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 114
    .line 115
    .line 116
    move-result-object v10

    .line 117
    check-cast v10, Landroid/widget/ImageView;

    .line 118
    .line 119
    iput-object v10, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaImageView:Landroid/widget/ImageView;

    .line 120
    .line 121
    invoke-virtual {v2, v7}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 122
    .line 123
    .line 124
    move-result-object v7

    .line 125
    check-cast v7, Landroid/widget/LinearLayout;

    .line 126
    .line 127
    iput-object v7, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 128
    .line 129
    invoke-virtual {v2, v9}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 130
    .line 131
    .line 132
    move-result-object v7

    .line 133
    check-cast v7, Landroid/widget/LinearLayout;

    .line 134
    .line 135
    iput-object v7, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 136
    .line 137
    invoke-static {}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->isTypeFold()Z

    .line 138
    .line 139
    .line 140
    move-result v7

    .line 141
    if-eqz v7, :cond_0

    .line 142
    .line 143
    invoke-virtual {v2, v5}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 144
    .line 145
    .line 146
    move-result-object v5

    .line 147
    check-cast v5, Landroid/widget/LinearLayout;

    .line 148
    .line 149
    iput-object v5, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPreviousButton:Landroid/widget/LinearLayout;

    .line 150
    .line 151
    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 152
    .line 153
    .line 154
    move-result-object v5

    .line 155
    check-cast v5, Landroid/widget/LinearLayout;

    .line 156
    .line 157
    iput-object v5, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaNextButton:Landroid/widget/LinearLayout;

    .line 158
    .line 159
    :cond_0
    const-string/jumbo v5, "window"

    .line 160
    .line 161
    .line 162
    invoke-virtual {v1, v5}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    .line 163
    .line 164
    .line 165
    move-result-object v5

    .line 166
    check-cast v5, Landroid/view/WindowManager;

    .line 167
    .line 168
    new-instance v6, Landroid/graphics/Point;

    .line 169
    .line 170
    invoke-direct {v6}, Landroid/graphics/Point;-><init>()V

    .line 171
    .line 172
    .line 173
    invoke-interface {v5}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    .line 174
    .line 175
    .line 176
    move-result-object v5

    .line 177
    invoke-virtual {v5, v6}, Landroid/view/Display;->getRealSize(Landroid/graphics/Point;)V

    .line 178
    .line 179
    .line 180
    iget v5, v6, Landroid/graphics/Point;->x:I

    .line 181
    .line 182
    iput v5, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayX:I

    .line 183
    .line 184
    iget v5, v6, Landroid/graphics/Point;->y:I

    .line 185
    .line 186
    iput v5, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayY:I

    .line 187
    .line 188
    invoke-static {}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->isTypeFold()Z

    .line 189
    .line 190
    .line 191
    move-result v5

    .line 192
    const v6, 0x7f0a08c4

    .line 193
    .line 194
    .line 195
    const v7, 0x7f0a07da

    .line 196
    .line 197
    .line 198
    const v9, 0x7f0a0671

    .line 199
    .line 200
    .line 201
    const v10, 0x7f0a066e

    .line 202
    .line 203
    .line 204
    const-wide/16 v11, 0x0

    .line 205
    .line 206
    if-eqz v5, :cond_1

    .line 207
    .line 208
    iget v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayY:I

    .line 209
    .line 210
    int-to-double v3, v3

    .line 211
    const-wide v13, 0x403fe66666666666L    # 31.9

    .line 212
    .line 213
    .line 214
    .line 215
    .line 216
    mul-double/2addr v3, v13

    .line 217
    const-wide/high16 v13, 0x4059000000000000L    # 100.0

    .line 218
    .line 219
    div-double/2addr v3, v13

    .line 220
    double-to-float v3, v3

    .line 221
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 222
    .line 223
    .line 224
    move-result-object v1

    .line 225
    const v4, 0x7f0700a8

    .line 226
    .line 227
    .line 228
    invoke-virtual {v1, v4}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 229
    .line 230
    .line 231
    move-result v1

    .line 232
    int-to-float v1, v1

    .line 233
    add-float/2addr v3, v1

    .line 234
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setY(F)V

    .line 235
    .line 236
    .line 237
    invoke-virtual {v2, v10}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 238
    .line 239
    .line 240
    move-result-object v1

    .line 241
    const-wide v3, 0x3ffe666666666666L    # 1.9

    .line 242
    .line 243
    .line 244
    .line 245
    .line 246
    invoke-virtual {p0, v3, v4, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 247
    .line 248
    .line 249
    move-result-object v5

    .line 250
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 251
    .line 252
    .line 253
    const v1, 0x7f0a0670

    .line 254
    .line 255
    .line 256
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 257
    .line 258
    .line 259
    move-result-object v1

    .line 260
    invoke-virtual {p0, v3, v4, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 261
    .line 262
    .line 263
    move-result-object v5

    .line 264
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 265
    .line 266
    .line 267
    invoke-virtual {v2, v9}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 268
    .line 269
    .line 270
    move-result-object v1

    .line 271
    const-wide v9, 0x4007333333333333L    # 2.9

    .line 272
    .line 273
    .line 274
    .line 275
    .line 276
    invoke-virtual {p0, v9, v10, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 277
    .line 278
    .line 279
    move-result-object v5

    .line 280
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 281
    .line 282
    .line 283
    const v1, 0x7f0a063e

    .line 284
    .line 285
    .line 286
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 287
    .line 288
    .line 289
    move-result-object v1

    .line 290
    invoke-virtual {p0, v3, v4, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 291
    .line 292
    .line 293
    move-result-object v5

    .line 294
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 295
    .line 296
    .line 297
    const v1, 0x7f0a063f

    .line 298
    .line 299
    .line 300
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 301
    .line 302
    .line 303
    move-result-object v1

    .line 304
    invoke-virtual {p0, v3, v4, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 305
    .line 306
    .line 307
    move-result-object v3

    .line 308
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 309
    .line 310
    .line 311
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 312
    .line 313
    const-wide v3, 0x4046e66666666666L    # 45.8

    .line 314
    .line 315
    .line 316
    .line 317
    .line 318
    invoke-virtual {p0, v3, v4}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParamsWidth(D)Landroid/widget/LinearLayout$LayoutParams;

    .line 319
    .line 320
    .line 321
    move-result-object v3

    .line 322
    invoke-virtual {v1, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 323
    .line 324
    .line 325
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaArtistText:Landroid/widget/TextView;

    .line 326
    .line 327
    const-wide v3, 0x4021666666666666L    # 8.7

    .line 328
    .line 329
    .line 330
    .line 331
    .line 332
    invoke-virtual {p0, v3, v4}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParamsWidth(D)Landroid/widget/LinearLayout$LayoutParams;

    .line 333
    .line 334
    .line 335
    move-result-object v3

    .line 336
    invoke-virtual {v1, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 337
    .line 338
    .line 339
    invoke-virtual {v2, v8}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 340
    .line 341
    .line 342
    move-result-object v1

    .line 343
    const-wide v3, 0x40115c28f5c28f5cL    # 4.34

    .line 344
    .line 345
    .line 346
    .line 347
    .line 348
    const-wide v8, 0x4014d70a3d70a3d7L    # 5.21

    .line 349
    .line 350
    .line 351
    .line 352
    .line 353
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 354
    .line 355
    .line 356
    move-result-object v3

    .line 357
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 358
    .line 359
    .line 360
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 361
    .line 362
    const-wide v3, 0x400ee147ae147ae1L    # 3.86

    .line 363
    .line 364
    .line 365
    .line 366
    .line 367
    const-wide v8, 0x4012851eb851eb85L    # 4.63

    .line 368
    .line 369
    .line 370
    .line 371
    .line 372
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 373
    .line 374
    .line 375
    move-result-object v5

    .line 376
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 377
    .line 378
    .line 379
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 380
    .line 381
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 382
    .line 383
    .line 384
    move-result-object v5

    .line 385
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 386
    .line 387
    .line 388
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPreviousButton:Landroid/widget/LinearLayout;

    .line 389
    .line 390
    const-wide v10, 0x40071eb851eb851fL    # 2.89

    .line 391
    .line 392
    .line 393
    .line 394
    .line 395
    const-wide v12, 0x400bc28f5c28f5c3L    # 3.47

    .line 396
    .line 397
    .line 398
    .line 399
    .line 400
    invoke-virtual {p0, v10, v11, v12, v13}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 401
    .line 402
    .line 403
    move-result-object v5

    .line 404
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 405
    .line 406
    .line 407
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaNextButton:Landroid/widget/LinearLayout;

    .line 408
    .line 409
    invoke-virtual {p0, v10, v11, v12, v13}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 410
    .line 411
    .line 412
    move-result-object v5

    .line 413
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 414
    .line 415
    .line 416
    invoke-virtual {v2, v7}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 417
    .line 418
    .line 419
    move-result-object v1

    .line 420
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 421
    .line 422
    .line 423
    move-result-object v5

    .line 424
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 425
    .line 426
    .line 427
    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 428
    .line 429
    .line 430
    move-result-object v1

    .line 431
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 432
    .line 433
    .line 434
    move-result-object v3

    .line 435
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 436
    .line 437
    .line 438
    const v1, 0x7f0a0a46

    .line 439
    .line 440
    .line 441
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 442
    .line 443
    .line 444
    move-result-object v1

    .line 445
    invoke-virtual {p0, v10, v11, v12, v13}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 446
    .line 447
    .line 448
    move-result-object v3

    .line 449
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 450
    .line 451
    .line 452
    const v1, 0x7f0a080c

    .line 453
    .line 454
    .line 455
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 456
    .line 457
    .line 458
    move-result-object v1

    .line 459
    invoke-virtual {p0, v10, v11, v12, v13}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 460
    .line 461
    .line 462
    move-result-object v2

    .line 463
    invoke-virtual {v1, v2}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 464
    .line 465
    .line 466
    goto/16 :goto_0

    .line 467
    .line 468
    :cond_1
    invoke-virtual {v2, v10}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 469
    .line 470
    .line 471
    move-result-object v1

    .line 472
    const-wide v13, 0x400199999999999aL    # 2.2

    .line 473
    .line 474
    .line 475
    .line 476
    .line 477
    invoke-virtual {p0, v13, v14, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 478
    .line 479
    .line 480
    move-result-object v5

    .line 481
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 482
    .line 483
    .line 484
    invoke-virtual {v2, v9}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 485
    .line 486
    .line 487
    move-result-object v1

    .line 488
    invoke-virtual {p0, v13, v14, v11, v12}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 489
    .line 490
    .line 491
    move-result-object v5

    .line 492
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 493
    .line 494
    .line 495
    const v1, 0x7f0a0c15

    .line 496
    .line 497
    .line 498
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 499
    .line 500
    .line 501
    move-result-object v1

    .line 502
    const-wide/high16 v9, 0x4000000000000000L    # 2.0

    .line 503
    .line 504
    invoke-virtual {p0, v11, v12, v9, v10}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 505
    .line 506
    .line 507
    move-result-object v5

    .line 508
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 509
    .line 510
    .line 511
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 512
    .line 513
    .line 514
    move-result-object v1

    .line 515
    const-wide/high16 v9, 0x404e000000000000L    # 60.0

    .line 516
    .line 517
    invoke-virtual {p0, v9, v10}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParamsWidth(D)Landroid/widget/LinearLayout$LayoutParams;

    .line 518
    .line 519
    .line 520
    move-result-object v3

    .line 521
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 522
    .line 523
    .line 524
    invoke-virtual {v2, v4}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 525
    .line 526
    .line 527
    move-result-object v1

    .line 528
    invoke-virtual {p0, v9, v10}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParamsWidth(D)Landroid/widget/LinearLayout$LayoutParams;

    .line 529
    .line 530
    .line 531
    move-result-object v3

    .line 532
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 533
    .line 534
    .line 535
    invoke-virtual {v2, v8}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 536
    .line 537
    .line 538
    move-result-object v1

    .line 539
    const-wide/high16 v3, 0x4024000000000000L    # 10.0

    .line 540
    .line 541
    const-wide v8, 0x40105c28f5c28f5cL    # 4.09

    .line 542
    .line 543
    .line 544
    .line 545
    .line 546
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 547
    .line 548
    .line 549
    move-result-object v3

    .line 550
    invoke-virtual {v1, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 551
    .line 552
    .line 553
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 554
    .line 555
    const-wide v3, 0x4021c28f5c28f5c3L    # 8.88

    .line 556
    .line 557
    .line 558
    .line 559
    .line 560
    const-wide v8, 0x400d1eb851eb851fL    # 3.64

    .line 561
    .line 562
    .line 563
    .line 564
    .line 565
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 566
    .line 567
    .line 568
    move-result-object v5

    .line 569
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 570
    .line 571
    .line 572
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 573
    .line 574
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 575
    .line 576
    .line 577
    move-result-object v5

    .line 578
    invoke-virtual {v1, v5}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 579
    .line 580
    .line 581
    invoke-virtual {v2, v7}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 582
    .line 583
    .line 584
    move-result-object v1

    .line 585
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 586
    .line 587
    .line 588
    move-result-object v5

    .line 589
    invoke-virtual {v1, v5}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 590
    .line 591
    .line 592
    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 593
    .line 594
    .line 595
    move-result-object v1

    .line 596
    invoke-virtual {p0, v3, v4, v8, v9}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;

    .line 597
    .line 598
    .line 599
    move-result-object v2

    .line 600
    invoke-virtual {v1, v2}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 601
    .line 602
    .line 603
    :goto_0
    const-string v1, "TouchPadMediaPanel"

    .line 604
    .line 605
    const-string v2, "MediaPanel setupButtons"

    .line 606
    .line 607
    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 608
    .line 609
    .line 610
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 611
    .line 612
    new-instance v2, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;

    .line 613
    .line 614
    const/4 v3, 0x0

    .line 615
    invoke-direct {v2, p0, v3}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;-><init>(Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;I)V

    .line 616
    .line 617
    .line 618
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 619
    .line 620
    .line 621
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 622
    .line 623
    new-instance v2, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;

    .line 624
    .line 625
    const/4 v3, 0x1

    .line 626
    invoke-direct {v2, p0, v3}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;-><init>(Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;I)V

    .line 627
    .line 628
    .line 629
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 630
    .line 631
    .line 632
    invoke-static {}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->isTypeFold()Z

    .line 633
    .line 634
    .line 635
    move-result v1

    .line 636
    if-eqz v1, :cond_2

    .line 637
    .line 638
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPreviousButton:Landroid/widget/LinearLayout;

    .line 639
    .line 640
    new-instance v2, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;

    .line 641
    .line 642
    const/4 v3, 0x2

    .line 643
    invoke-direct {v2, p0, v3}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;-><init>(Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;I)V

    .line 644
    .line 645
    .line 646
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 647
    .line 648
    .line 649
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaNextButton:Landroid/widget/LinearLayout;

    .line 650
    .line 651
    new-instance v2, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;

    .line 652
    .line 653
    const/4 v3, 0x3

    .line 654
    invoke-direct {v2, p0, v3}, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel$$ExternalSyntheticLambda0;-><init>(Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;I)V

    .line 655
    .line 656
    .line 657
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 658
    .line 659
    .line 660
    :cond_2
    return-void
.end method


# virtual methods
.method public final getRatioLayoutParams(DD)Landroid/widget/LinearLayout$LayoutParams;
    .locals 4

    .line 1
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayX:I

    .line 4
    .line 5
    int-to-double v1, v1

    .line 6
    mul-double/2addr v1, p1

    .line 7
    const-wide/high16 p1, 0x4059000000000000L    # 100.0

    .line 8
    .line 9
    div-double/2addr v1, p1

    .line 10
    double-to-int v1, v1

    .line 11
    iget p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayY:I

    .line 12
    .line 13
    int-to-double v2, p0

    .line 14
    mul-double/2addr v2, p3

    .line 15
    div-double/2addr v2, p1

    .line 16
    double-to-int p0, v2

    .line 17
    invoke-direct {v0, v1, p0}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 18
    .line 19
    .line 20
    return-object v0
.end method

.method public final getRatioLayoutParamsWidth(D)Landroid/widget/LinearLayout$LayoutParams;
    .locals 3

    .line 1
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 2
    .line 3
    iget p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->displayX:I

    .line 4
    .line 5
    int-to-double v1, p0

    .line 6
    mul-double/2addr v1, p1

    .line 7
    const-wide/high16 p0, 0x4059000000000000L    # 100.0

    .line 8
    .line 9
    div-double/2addr v1, p0

    .line 10
    double-to-int p0, v1

    .line 11
    const/4 p1, -0x2

    .line 12
    invoke-direct {v0, p0, p1}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 13
    .line 14
    .line 15
    return-object v0
.end method

.method public final onClickButton(I)V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 2
    .line 3
    const-string v1, "TouchPadMediaPanel"

    .line 4
    .line 5
    if-eqz v0, :cond_5

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    if-nez v0, :cond_0

    .line 12
    .line 13
    goto :goto_1

    .line 14
    :cond_0
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 15
    .line 16
    invoke-virtual {v0}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    .line 17
    .line 18
    .line 19
    move-result-object v0

    .line 20
    iput-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 21
    .line 22
    new-instance v0, Ljava/lang/StringBuilder;

    .line 23
    .line 24
    const-string v2, "MediaPanel onClick mMediaPauseButton mMediaController : "

    .line 25
    .line 26
    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 27
    .line 28
    .line 29
    iget-object v2, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 30
    .line 31
    invoke-virtual {v2}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    .line 32
    .line 33
    .line 34
    move-result-object v2

    .line 35
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    const-string v2, ", logging : , mPlaybackState : "

    .line 39
    .line 40
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    iget-object v2, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 44
    .line 45
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 46
    .line 47
    .line 48
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 49
    .line 50
    .line 51
    move-result-object v0

    .line 52
    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 53
    .line 54
    .line 55
    const v0, 0x7f0a008d

    .line 56
    .line 57
    .line 58
    if-ne p1, v0, :cond_1

    .line 59
    .line 60
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 61
    .line 62
    invoke-virtual {p0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 63
    .line 64
    .line 65
    move-result-object p0

    .line 66
    invoke-virtual {p0}, Landroid/media/session/MediaController$TransportControls;->skipToPrevious()V

    .line 67
    .line 68
    .line 69
    goto :goto_0

    .line 70
    :cond_1
    const v0, 0x7f0a008c

    .line 71
    .line 72
    .line 73
    if-ne p1, v0, :cond_2

    .line 74
    .line 75
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 76
    .line 77
    invoke-virtual {p0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 78
    .line 79
    .line 80
    move-result-object p0

    .line 81
    invoke-virtual {p0}, Landroid/media/session/MediaController$TransportControls;->skipToNext()V

    .line 82
    .line 83
    .line 84
    goto :goto_0

    .line 85
    :cond_2
    const v0, 0x7f0a0088

    .line 86
    .line 87
    .line 88
    if-ne p1, v0, :cond_3

    .line 89
    .line 90
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 91
    .line 92
    invoke-virtual {p0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 93
    .line 94
    .line 95
    move-result-object p0

    .line 96
    invoke-virtual {p0}, Landroid/media/session/MediaController$TransportControls;->play()V

    .line 97
    .line 98
    .line 99
    goto :goto_0

    .line 100
    :cond_3
    const v0, 0x7f0a0083

    .line 101
    .line 102
    .line 103
    if-ne p1, v0, :cond_4

    .line 104
    .line 105
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 106
    .line 107
    invoke-virtual {p0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 108
    .line 109
    .line 110
    move-result-object p0

    .line 111
    invoke-virtual {p0}, Landroid/media/session/MediaController$TransportControls;->pause()V

    .line 112
    .line 113
    .line 114
    :cond_4
    :goto_0
    return-void

    .line 115
    :cond_5
    :goto_1
    const-string p0, "MediaPanel onClickButton mMediaController or getPlaybackState == null"

    .line 116
    .line 117
    invoke-static {v1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 118
    .line 119
    .line 120
    return-void
.end method

.method public final updateTouchPadMediaPanel()V
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 4
    .line 5
    const-string v2, "TouchPadMediaPanel"

    .line 6
    .line 7
    if-nez v1, :cond_0

    .line 8
    .line 9
    const-string v0, "TouchPadMediaPanel updateTouchPadMediaPanel mMediaController == null"

    .line 10
    .line 11
    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 12
    .line 13
    .line 14
    return-void

    .line 15
    :cond_0
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    .line 16
    .line 17
    .line 18
    move-result-object v1

    .line 19
    iput-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 20
    .line 21
    if-nez v1, :cond_1

    .line 22
    .line 23
    const-string v0, "TouchPadMediaPanel updateTouchPadMediaPanel getPlaybackState == null"

    .line 24
    .line 25
    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    .line 27
    .line 28
    return-void

    .line 29
    :cond_1
    invoke-virtual {v1}, Landroid/media/session/PlaybackState;->getActions()J

    .line 30
    .line 31
    .line 32
    move-result-wide v3

    .line 33
    const/4 v1, 0x0

    .line 34
    const/4 v5, 0x1

    .line 35
    const v6, 0x3ecccccd    # 0.4f

    .line 36
    .line 37
    .line 38
    iget-object v7, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mFloatingPanelView:Landroid/widget/LinearLayout;

    .line 39
    .line 40
    if-nez v7, :cond_2

    .line 41
    .line 42
    goto/16 :goto_4

    .line 43
    .line 44
    :cond_2
    iget-object v8, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mActionViewIdMapFlip:Landroid/util/SparseArray;

    .line 45
    .line 46
    invoke-static {}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->isTypeFold()Z

    .line 47
    .line 48
    .line 49
    move-result v9

    .line 50
    if-eqz v9, :cond_3

    .line 51
    .line 52
    iget-object v8, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mActionViewIdMapFold:Landroid/util/SparseArray;

    .line 53
    .line 54
    :cond_3
    invoke-virtual {v8}, Landroid/util/SparseArray;->size()I

    .line 55
    .line 56
    .line 57
    move-result v9

    .line 58
    new-instance v10, Ljava/util/ArrayList;

    .line 59
    .line 60
    invoke-direct {v10}, Ljava/util/ArrayList;-><init>()V

    .line 61
    .line 62
    .line 63
    move v11, v1

    .line 64
    :goto_0
    if-ge v11, v9, :cond_7

    .line 65
    .line 66
    invoke-virtual {v8, v11}, Landroid/util/SparseArray;->keyAt(I)I

    .line 67
    .line 68
    .line 69
    move-result v12

    .line 70
    invoke-virtual {v8, v11}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    .line 71
    .line 72
    .line 73
    move-result-object v13

    .line 74
    check-cast v13, Ljava/lang/Long;

    .line 75
    .line 76
    invoke-virtual {v13}, Ljava/lang/Long;->longValue()J

    .line 77
    .line 78
    .line 79
    move-result-wide v13

    .line 80
    invoke-virtual {v7, v12}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 81
    .line 82
    .line 83
    move-result-object v12

    .line 84
    check-cast v12, Landroid/widget/LinearLayout;

    .line 85
    .line 86
    and-long/2addr v13, v3

    .line 87
    const-wide/16 v15, 0x0

    .line 88
    .line 89
    cmp-long v13, v13, v15

    .line 90
    .line 91
    if-eqz v13, :cond_4

    .line 92
    .line 93
    move v14, v5

    .line 94
    goto :goto_1

    .line 95
    :cond_4
    move v14, v1

    .line 96
    :goto_1
    invoke-virtual {v12, v14}, Landroid/widget/LinearLayout;->setEnabled(Z)V

    .line 97
    .line 98
    .line 99
    if-eqz v13, :cond_5

    .line 100
    .line 101
    move v13, v5

    .line 102
    goto :goto_2

    .line 103
    :cond_5
    move v13, v1

    .line 104
    :goto_2
    if-eqz v13, :cond_6

    .line 105
    .line 106
    const/high16 v13, 0x3f800000    # 1.0f

    .line 107
    .line 108
    invoke-virtual {v12, v13}, Landroid/widget/LinearLayout;->setAlpha(F)V

    .line 109
    .line 110
    .line 111
    invoke-virtual {v12}, Landroid/widget/LinearLayout;->getContentDescription()Ljava/lang/CharSequence;

    .line 112
    .line 113
    .line 114
    move-result-object v12

    .line 115
    invoke-virtual {v10, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 116
    .line 117
    .line 118
    goto :goto_3

    .line 119
    :cond_6
    invoke-virtual {v12, v6}, Landroid/widget/LinearLayout;->setAlpha(F)V

    .line 120
    .line 121
    .line 122
    :goto_3
    add-int/lit8 v11, v11, 0x1

    .line 123
    .line 124
    goto :goto_0

    .line 125
    :cond_7
    new-instance v3, Ljava/lang/StringBuilder;

    .line 126
    .line 127
    const-string v4, "action count : "

    .line 128
    .line 129
    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 130
    .line 131
    .line 132
    invoke-virtual {v3, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 133
    .line 134
    .line 135
    const-string v4, " enable buttons : "

    .line 136
    .line 137
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 138
    .line 139
    .line 140
    invoke-virtual {v3, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 141
    .line 142
    .line 143
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 144
    .line 145
    .line 146
    move-result-object v3

    .line 147
    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 148
    .line 149
    .line 150
    :goto_4
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 151
    .line 152
    invoke-virtual {v2}, Landroid/media/session/PlaybackState;->getState()I

    .line 153
    .line 154
    .line 155
    move-result v2

    .line 156
    const/16 v3, 0x8

    .line 157
    .line 158
    if-nez v2, :cond_8

    .line 159
    .line 160
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 161
    .line 162
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 163
    .line 164
    .line 165
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 166
    .line 167
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 168
    .line 169
    .line 170
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 171
    .line 172
    invoke-virtual {v1, v6}, Landroid/widget/LinearLayout;->setAlpha(F)V

    .line 173
    .line 174
    .line 175
    goto :goto_6

    .line 176
    :cond_8
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 177
    .line 178
    invoke-virtual {v2}, Landroid/media/session/PlaybackState;->getState()I

    .line 179
    .line 180
    .line 181
    move-result v2

    .line 182
    const/4 v4, 0x3

    .line 183
    if-eq v2, v4, :cond_a

    .line 184
    .line 185
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mPlaybackState:Landroid/media/session/PlaybackState;

    .line 186
    .line 187
    invoke-virtual {v2}, Landroid/media/session/PlaybackState;->getState()I

    .line 188
    .line 189
    .line 190
    move-result v2

    .line 191
    const/4 v4, 0x6

    .line 192
    if-ne v2, v4, :cond_9

    .line 193
    .line 194
    goto :goto_5

    .line 195
    :cond_9
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 196
    .line 197
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 198
    .line 199
    .line 200
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 201
    .line 202
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 203
    .line 204
    .line 205
    goto :goto_6

    .line 206
    :cond_a
    :goto_5
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaResumeButton:Landroid/widget/LinearLayout;

    .line 207
    .line 208
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 209
    .line 210
    .line 211
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaPauseButton:Landroid/widget/LinearLayout;

    .line 212
    .line 213
    invoke-virtual {v2, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 214
    .line 215
    .line 216
    :goto_6
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 217
    .line 218
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getMetadata()Landroid/media/MediaMetadata;

    .line 219
    .line 220
    .line 221
    move-result-object v1

    .line 222
    if-nez v1, :cond_b

    .line 223
    .line 224
    goto :goto_8

    .line 225
    :cond_b
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaArtistText:Landroid/widget/TextView;

    .line 226
    .line 227
    const-string v3, "android.media.metadata.ARTIST"

    .line 228
    .line 229
    invoke-virtual {v1, v3}, Landroid/media/MediaMetadata;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 230
    .line 231
    .line 232
    move-result-object v3

    .line 233
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 234
    .line 235
    .line 236
    const-string v2, "android.media.metadata.ALBUM_ART"

    .line 237
    .line 238
    invoke-virtual {v1, v2}, Landroid/media/MediaMetadata;->getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;

    .line 239
    .line 240
    .line 241
    move-result-object v2

    .line 242
    if-eqz v2, :cond_c

    .line 243
    .line 244
    iget-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaImageView:Landroid/widget/ImageView;

    .line 245
    .line 246
    invoke-virtual {v3, v2}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 247
    .line 248
    .line 249
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaImageView:Landroid/widget/ImageView;

    .line 250
    .line 251
    invoke-virtual {v2, v5}, Landroid/widget/ImageView;->setClipToOutline(Z)V

    .line 252
    .line 253
    .line 254
    goto :goto_7

    .line 255
    :cond_c
    iget-object v2, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaImageView:Landroid/widget/ImageView;

    .line 256
    .line 257
    iget-object v3, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mContext:Landroid/content/Context;

    .line 258
    .line 259
    const v4, 0x7f080730

    .line 260
    .line 261
    .line 262
    invoke-virtual {v3, v4}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 263
    .line 264
    .line 265
    move-result-object v3

    .line 266
    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 267
    .line 268
    .line 269
    :goto_7
    const-string v2, "android.media.metadata.TITLE"

    .line 270
    .line 271
    invoke-virtual {v1, v2}, Landroid/media/MediaMetadata;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 272
    .line 273
    .line 274
    move-result-object v2

    .line 275
    if-nez v2, :cond_d

    .line 276
    .line 277
    const-string v2, "android.media.metadata.DISPLAY_TITLE"

    .line 278
    .line 279
    invoke-virtual {v1, v2}, Landroid/media/MediaMetadata;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 280
    .line 281
    .line 282
    move-result-object v2

    .line 283
    :cond_d
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 284
    .line 285
    invoke-virtual {v1}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    .line 286
    .line 287
    .line 288
    move-result-object v1

    .line 289
    invoke-virtual {v1, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    .line 290
    .line 291
    .line 292
    move-result v1

    .line 293
    if-nez v1, :cond_e

    .line 294
    .line 295
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 296
    .line 297
    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 298
    .line 299
    .line 300
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 301
    .line 302
    invoke-virtual {v1, v5}, Landroid/widget/TextView;->setSingleLine(Z)V

    .line 303
    .line 304
    .line 305
    iget-object v1, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 306
    .line 307
    sget-object v2, Landroid/text/TextUtils$TruncateAt;->MARQUEE:Landroid/text/TextUtils$TruncateAt;

    .line 308
    .line 309
    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 310
    .line 311
    .line 312
    iget-object v0, v0, Lcom/android/wm/shell/controlpanel/activity/TouchPadMediaPanel;->mMediaTitleText:Landroid/widget/TextView;

    .line 313
    .line 314
    invoke-virtual {v0, v5}, Landroid/widget/TextView;->setSelected(Z)V

    .line 315
    .line 316
    .line 317
    :cond_e
    :goto_8
    return-void
.end method
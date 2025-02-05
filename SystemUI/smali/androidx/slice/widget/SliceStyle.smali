.class public final Landroidx/slice/widget/SliceStyle;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mContext:Landroid/content/Context;

.field public final mDefaultRowStyleRes:I

.field public final mExpandToAvailableHeight:Z

.field public final mGridAllImagesHeight:I

.field public final mGridBigPicMaxHeight:I

.field public final mGridBigPicMinHeight:I

.field public final mGridBottomPadding:I

.field public final mGridImageTextHeight:I

.field public final mGridMaxHeight:I

.field public final mGridMinHeight:I

.field public final mGridRawImageTextHeight:I

.field public final mGridSubtitleSize:I

.field public final mGridTitleSize:I

.field public final mGridTopPadding:I

.field public final mHeaderSubtitleSize:I

.field public final mHeaderTitleSize:I

.field public final mHideHeaderRow:Z

.field public final mImageCornerRadius:F

.field public final mListLargeHeight:I

.field public final mListMinScrollHeight:I

.field public final mResourceToRowStyle:Landroid/util/SparseArray;

.field public final mRowInlineRangeHeight:I

.field public final mRowMaxHeight:I

.field public final mRowMinHeight:I

.field public final mRowRangeHeight:I

.field public final mRowSelectionHeight:I

.field public final mRowSingleTextWithRangeHeight:I

.field public final mRowSingleTextWithSelectionHeight:I

.field public final mRowTextWithRangeHeight:I

.field public final mRowTextWithSelectionHeight:I

.field public final mSubtitleColor:I

.field public final mSubtitleSize:I

.field public final mTintColor:I

.field public final mTitleColor:I

.field public final mTitleSize:I

.field public final mVerticalGridTextPadding:I

.field public final mVerticalHeaderTextPadding:I

.field public final mVerticalTextPadding:I


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 3

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, -0x1

    .line 5
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mTintColor:I

    .line 6
    .line 7
    new-instance v1, Landroid/util/SparseArray;

    .line 8
    .line 9
    invoke-direct {v1}, Landroid/util/SparseArray;-><init>()V

    .line 10
    .line 11
    .line 12
    iput-object v1, p0, Landroidx/slice/widget/SliceStyle;->mResourceToRowStyle:Landroid/util/SparseArray;

    .line 13
    .line 14
    invoke-virtual {p1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 15
    .line 16
    .line 17
    move-result-object v1

    .line 18
    sget-object v2, Landroidx/slice/view/R$styleable;->SliceView:[I

    .line 19
    .line 20
    invoke-virtual {v1, p2, v2, p3, p4}, Landroid/content/res/Resources$Theme;->obtainStyledAttributes(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;

    .line 21
    .line 22
    .line 23
    move-result-object p2

    .line 24
    const/16 p3, 0x14

    .line 25
    .line 26
    :try_start_0
    invoke-virtual {p2, p3, v0}, Landroid/content/res/TypedArray;->getColor(II)I

    .line 27
    .line 28
    .line 29
    move-result p3

    .line 30
    if-eq p3, v0, :cond_0

    .line 31
    .line 32
    move v0, p3

    .line 33
    :cond_0
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mTintColor:I

    .line 34
    .line 35
    const/4 p3, 0x0

    .line 36
    const/16 p4, 0x15

    .line 37
    .line 38
    invoke-virtual {p2, p4, p3}, Landroid/content/res/TypedArray;->getColor(II)I

    .line 39
    .line 40
    .line 41
    move-result p4

    .line 42
    iput p4, p0, Landroidx/slice/widget/SliceStyle;->mTitleColor:I

    .line 43
    .line 44
    const/16 p4, 0x11

    .line 45
    .line 46
    invoke-virtual {p2, p4, p3}, Landroid/content/res/TypedArray;->getColor(II)I

    .line 47
    .line 48
    .line 49
    move-result p4

    .line 50
    iput p4, p0, Landroidx/slice/widget/SliceStyle;->mSubtitleColor:I

    .line 51
    .line 52
    const/4 p4, 0x0

    .line 53
    const/16 v0, 0x8

    .line 54
    .line 55
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 56
    .line 57
    .line 58
    move-result v0

    .line 59
    float-to-int v0, v0

    .line 60
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mHeaderTitleSize:I

    .line 61
    .line 62
    const/4 v0, 0x6

    .line 63
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 64
    .line 65
    .line 66
    move-result v0

    .line 67
    float-to-int v0, v0

    .line 68
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mHeaderSubtitleSize:I

    .line 69
    .line 70
    const/4 v0, 0x7

    .line 71
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 72
    .line 73
    .line 74
    move-result v0

    .line 75
    float-to-int v0, v0

    .line 76
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mVerticalHeaderTextPadding:I

    .line 77
    .line 78
    const/16 v0, 0x16

    .line 79
    .line 80
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 81
    .line 82
    .line 83
    move-result v0

    .line 84
    float-to-int v0, v0

    .line 85
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mTitleSize:I

    .line 86
    .line 87
    const/16 v0, 0x12

    .line 88
    .line 89
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 90
    .line 91
    .line 92
    move-result v0

    .line 93
    float-to-int v0, v0

    .line 94
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mSubtitleSize:I

    .line 95
    .line 96
    const/16 v0, 0x13

    .line 97
    .line 98
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 99
    .line 100
    .line 101
    move-result v0

    .line 102
    float-to-int v0, v0

    .line 103
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mVerticalTextPadding:I

    .line 104
    .line 105
    const/4 v0, 0x4

    .line 106
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 107
    .line 108
    .line 109
    move-result v0

    .line 110
    float-to-int v0, v0

    .line 111
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mGridTitleSize:I

    .line 112
    .line 113
    const/4 v0, 0x2

    .line 114
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 115
    .line 116
    .line 117
    move-result v0

    .line 118
    float-to-int v0, v0

    .line 119
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mGridSubtitleSize:I

    .line 120
    .line 121
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 122
    .line 123
    .line 124
    move-result-object v0

    .line 125
    const v1, 0x7f070017

    .line 126
    .line 127
    .line 128
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 129
    .line 130
    .line 131
    move-result v0

    .line 132
    int-to-float v0, v0

    .line 133
    const/4 v1, 0x3

    .line 134
    invoke-virtual {p2, v1, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 135
    .line 136
    .line 137
    move-result v0

    .line 138
    float-to-int v0, v0

    .line 139
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mVerticalGridTextPadding:I

    .line 140
    .line 141
    const/4 v0, 0x5

    .line 142
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 143
    .line 144
    .line 145
    move-result v0

    .line 146
    float-to-int v0, v0

    .line 147
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mGridTopPadding:I

    .line 148
    .line 149
    const/4 v0, 0x1

    .line 150
    invoke-virtual {p2, v0, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 151
    .line 152
    .line 153
    move-result v0

    .line 154
    float-to-int v0, v0

    .line 155
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mGridBottomPadding:I

    .line 156
    .line 157
    const/16 v0, 0x10

    .line 158
    .line 159
    invoke-virtual {p2, v0, p3}, Landroid/content/res/TypedArray;->getResourceId(II)I

    .line 160
    .line 161
    .line 162
    move-result v0

    .line 163
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mDefaultRowStyleRes:I

    .line 164
    .line 165
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 166
    .line 167
    .line 168
    move-result-object v0

    .line 169
    const v1, 0x7f07001e

    .line 170
    .line 171
    .line 172
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 173
    .line 174
    .line 175
    move-result v0

    .line 176
    int-to-float v0, v0

    .line 177
    const/16 v2, 0xd

    .line 178
    .line 179
    invoke-virtual {p2, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 180
    .line 181
    .line 182
    move-result v0

    .line 183
    float-to-int v0, v0

    .line 184
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mRowMinHeight:I

    .line 185
    .line 186
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 187
    .line 188
    .line 189
    move-result-object v0

    .line 190
    const v2, 0x7f07001d

    .line 191
    .line 192
    .line 193
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 194
    .line 195
    .line 196
    move-result v0

    .line 197
    int-to-float v0, v0

    .line 198
    const/16 v2, 0xc

    .line 199
    .line 200
    invoke-virtual {p2, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 201
    .line 202
    .line 203
    move-result v0

    .line 204
    float-to-int v0, v0

    .line 205
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mRowMaxHeight:I

    .line 206
    .line 207
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 208
    .line 209
    .line 210
    move-result-object v0

    .line 211
    const v2, 0x7f07001f

    .line 212
    .line 213
    .line 214
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 215
    .line 216
    .line 217
    move-result v0

    .line 218
    int-to-float v0, v0

    .line 219
    const/16 v2, 0xe

    .line 220
    .line 221
    invoke-virtual {p2, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 222
    .line 223
    .line 224
    move-result v0

    .line 225
    float-to-int v0, v0

    .line 226
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mRowRangeHeight:I

    .line 227
    .line 228
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 229
    .line 230
    .line 231
    move-result-object v0

    .line 232
    const v2, 0x7f070022

    .line 233
    .line 234
    .line 235
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 236
    .line 237
    .line 238
    move-result v0

    .line 239
    int-to-float v0, v0

    .line 240
    const/16 v2, 0xf

    .line 241
    .line 242
    invoke-virtual {p2, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 243
    .line 244
    .line 245
    move-result v0

    .line 246
    float-to-int v0, v0

    .line 247
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mRowSingleTextWithRangeHeight:I

    .line 248
    .line 249
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 250
    .line 251
    .line 252
    move-result-object v0

    .line 253
    const v2, 0x7f070020

    .line 254
    .line 255
    .line 256
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 257
    .line 258
    .line 259
    move-result v0

    .line 260
    int-to-float v0, v0

    .line 261
    const/16 v2, 0xb

    .line 262
    .line 263
    invoke-virtual {p2, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 264
    .line 265
    .line 266
    move-result v0

    .line 267
    float-to-int v0, v0

    .line 268
    iput v0, p0, Landroidx/slice/widget/SliceStyle;->mRowInlineRangeHeight:I

    .line 269
    .line 270
    invoke-virtual {p2, p3, p3}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    .line 271
    .line 272
    .line 273
    move-result v0

    .line 274
    iput-boolean v0, p0, Landroidx/slice/widget/SliceStyle;->mExpandToAvailableHeight:Z

    .line 275
    .line 276
    const/16 v0, 0x9

    .line 277
    .line 278
    invoke-virtual {p2, v0, p3}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    .line 279
    .line 280
    .line 281
    move-result p3

    .line 282
    iput-boolean p3, p0, Landroidx/slice/widget/SliceStyle;->mHideHeaderRow:Z

    .line 283
    .line 284
    iput-object p1, p0, Landroidx/slice/widget/SliceStyle;->mContext:Landroid/content/Context;

    .line 285
    .line 286
    const/16 p3, 0xa

    .line 287
    .line 288
    invoke-virtual {p2, p3, p4}, Landroid/content/res/TypedArray;->getDimension(IF)F

    .line 289
    .line 290
    .line 291
    move-result p3

    .line 292
    iput p3, p0, Landroidx/slice/widget/SliceStyle;->mImageCornerRadius:F
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 293
    .line 294
    invoke-virtual {p2}, Landroid/content/res/TypedArray;->recycle()V

    .line 295
    .line 296
    .line 297
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 298
    .line 299
    .line 300
    move-result-object p1

    .line 301
    const p2, 0x7f070021

    .line 302
    .line 303
    .line 304
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 305
    .line 306
    .line 307
    move-result p2

    .line 308
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mRowTextWithRangeHeight:I

    .line 309
    .line 310
    const p2, 0x7f070023

    .line 311
    .line 312
    .line 313
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 314
    .line 315
    .line 316
    move-result p2

    .line 317
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mRowSelectionHeight:I

    .line 318
    .line 319
    const p2, 0x7f070024

    .line 320
    .line 321
    .line 322
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 323
    .line 324
    .line 325
    move-result p2

    .line 326
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mRowTextWithSelectionHeight:I

    .line 327
    .line 328
    const p2, 0x7f070025

    .line 329
    .line 330
    .line 331
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 332
    .line 333
    .line 334
    move-result p2

    .line 335
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mRowSingleTextWithSelectionHeight:I

    .line 336
    .line 337
    const p2, 0x7f07000e

    .line 338
    .line 339
    .line 340
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 341
    .line 342
    .line 343
    move-result p2

    .line 344
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridBigPicMinHeight:I

    .line 345
    .line 346
    const p2, 0x7f07000d

    .line 347
    .line 348
    .line 349
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 350
    .line 351
    .line 352
    move-result p2

    .line 353
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridBigPicMaxHeight:I

    .line 354
    .line 355
    const p2, 0x7f070011

    .line 356
    .line 357
    .line 358
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 359
    .line 360
    .line 361
    move-result p2

    .line 362
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridAllImagesHeight:I

    .line 363
    .line 364
    const p2, 0x7f070012

    .line 365
    .line 366
    .line 367
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 368
    .line 369
    .line 370
    move-result p2

    .line 371
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridImageTextHeight:I

    .line 372
    .line 373
    const p2, 0x7f070015

    .line 374
    .line 375
    .line 376
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 377
    .line 378
    .line 379
    move-result p2

    .line 380
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridRawImageTextHeight:I

    .line 381
    .line 382
    const p2, 0x7f070014

    .line 383
    .line 384
    .line 385
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 386
    .line 387
    .line 388
    move-result p2

    .line 389
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridMinHeight:I

    .line 390
    .line 391
    const p2, 0x7f070013

    .line 392
    .line 393
    .line 394
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 395
    .line 396
    .line 397
    move-result p2

    .line 398
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mGridMaxHeight:I

    .line 399
    .line 400
    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 401
    .line 402
    .line 403
    move-result p2

    .line 404
    iput p2, p0, Landroidx/slice/widget/SliceStyle;->mListMinScrollHeight:I

    .line 405
    .line 406
    const p2, 0x7f07001a

    .line 407
    .line 408
    .line 409
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 410
    .line 411
    .line 412
    move-result p1

    .line 413
    iput p1, p0, Landroidx/slice/widget/SliceStyle;->mListLargeHeight:I

    .line 414
    .line 415
    return-void

    .line 416
    :catchall_0
    move-exception p0

    .line 417
    invoke-virtual {p2}, Landroid/content/res/TypedArray;->recycle()V

    .line 418
    .line 419
    .line 420
    throw p0
.end method


# virtual methods
.method public final getListItemsForNonScrollingList(Landroidx/slice/widget/ListContent;ILandroidx/slice/widget/SliceViewPolicy;)Landroidx/slice/widget/DisplayedListItems;
    .locals 10

    .line 1
    new-instance v0, Ljava/util/ArrayList;

    .line 2
    .line 3
    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 4
    .line 5
    .line 6
    iget-object v1, p1, Landroidx/slice/widget/ListContent;->mRowItems:Ljava/util/ArrayList;

    .line 7
    .line 8
    const/4 v2, 0x0

    .line 9
    if-eqz v1, :cond_8

    .line 10
    .line 11
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    .line 12
    .line 13
    .line 14
    move-result v1

    .line 15
    if-nez v1, :cond_0

    .line 16
    .line 17
    goto/16 :goto_5

    .line 18
    .line 19
    :cond_0
    iget-object v1, p1, Landroidx/slice/widget/ListContent;->mRowItems:Ljava/util/ArrayList;

    .line 20
    .line 21
    invoke-virtual {p0, v1}, Landroidx/slice/widget/SliceStyle;->shouldSkipFirstListItem(Ljava/util/List;)Z

    .line 22
    .line 23
    .line 24
    move-result v3

    .line 25
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    .line 26
    .line 27
    .line 28
    move-result v4

    .line 29
    move v5, v2

    .line 30
    move v6, v5

    .line 31
    :goto_0
    if-ge v5, v4, :cond_3

    .line 32
    .line 33
    invoke-virtual {v1, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 34
    .line 35
    .line 36
    move-result-object v7

    .line 37
    check-cast v7, Landroidx/slice/widget/SliceContent;

    .line 38
    .line 39
    if-nez v5, :cond_1

    .line 40
    .line 41
    if-eqz v3, :cond_1

    .line 42
    .line 43
    goto :goto_1

    .line 44
    :cond_1
    invoke-virtual {v7, p0, p3}, Landroidx/slice/widget/SliceContent;->getHeight(Landroidx/slice/widget/SliceStyle;Landroidx/slice/widget/SliceViewPolicy;)I

    .line 45
    .line 46
    .line 47
    move-result v8

    .line 48
    if-lez p2, :cond_2

    .line 49
    .line 50
    add-int v9, v6, v8

    .line 51
    .line 52
    if-le v9, p2, :cond_2

    .line 53
    .line 54
    sub-int/2addr v4, v5

    .line 55
    goto :goto_2

    .line 56
    :cond_2
    add-int/2addr v6, v8

    .line 57
    invoke-virtual {v0, v7}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 58
    .line 59
    .line 60
    :goto_1
    add-int/lit8 v5, v5, 0x1

    .line 61
    .line 62
    goto :goto_0

    .line 63
    :cond_3
    move v4, v2

    .line 64
    :goto_2
    const/4 v5, 0x1

    .line 65
    if-eqz v3, :cond_4

    .line 66
    .line 67
    move v3, v5

    .line 68
    goto :goto_3

    .line 69
    :cond_4
    const/4 v3, 0x2

    .line 70
    :goto_3
    iget-object v7, p1, Landroidx/slice/widget/ListContent;->mSeeMoreContent:Landroidx/slice/widget/RowContent;

    .line 71
    .line 72
    if-eqz v7, :cond_6

    .line 73
    .line 74
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 75
    .line 76
    .line 77
    move-result v7

    .line 78
    if-lt v7, v3, :cond_6

    .line 79
    .line 80
    if-lez v4, :cond_6

    .line 81
    .line 82
    iget-object v7, p1, Landroidx/slice/widget/ListContent;->mSeeMoreContent:Landroidx/slice/widget/RowContent;

    .line 83
    .line 84
    invoke-virtual {v7, p0, p3}, Landroidx/slice/widget/RowContent;->getHeight(Landroidx/slice/widget/SliceStyle;Landroidx/slice/widget/SliceViewPolicy;)I

    .line 85
    .line 86
    .line 87
    move-result v7

    .line 88
    add-int/2addr v7, v6

    .line 89
    :goto_4
    if-le v7, p2, :cond_5

    .line 90
    .line 91
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 92
    .line 93
    .line 94
    move-result v6

    .line 95
    if-lt v6, v3, :cond_5

    .line 96
    .line 97
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 98
    .line 99
    .line 100
    move-result v6

    .line 101
    sub-int/2addr v6, v5

    .line 102
    invoke-virtual {v0, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 103
    .line 104
    .line 105
    move-result-object v8

    .line 106
    check-cast v8, Landroidx/slice/widget/SliceContent;

    .line 107
    .line 108
    invoke-virtual {v8, p0, p3}, Landroidx/slice/widget/SliceContent;->getHeight(Landroidx/slice/widget/SliceStyle;Landroidx/slice/widget/SliceViewPolicy;)I

    .line 109
    .line 110
    .line 111
    move-result v8

    .line 112
    sub-int/2addr v7, v8

    .line 113
    invoke-virtual {v0, v6}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 114
    .line 115
    .line 116
    add-int/lit8 v4, v4, 0x1

    .line 117
    .line 118
    goto :goto_4

    .line 119
    :cond_5
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 120
    .line 121
    .line 122
    move-result p0

    .line 123
    if-lt p0, v3, :cond_6

    .line 124
    .line 125
    iget-object p0, p1, Landroidx/slice/widget/ListContent;->mSeeMoreContent:Landroidx/slice/widget/RowContent;

    .line 126
    .line 127
    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 128
    .line 129
    .line 130
    :cond_6
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 131
    .line 132
    .line 133
    move-result p0

    .line 134
    if-nez p0, :cond_7

    .line 135
    .line 136
    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 137
    .line 138
    .line 139
    move-result-object p0

    .line 140
    check-cast p0, Landroidx/slice/widget/SliceContent;

    .line 141
    .line 142
    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 143
    .line 144
    .line 145
    :cond_7
    new-instance p0, Landroidx/slice/widget/DisplayedListItems;

    .line 146
    .line 147
    invoke-direct {p0, v0, v4}, Landroidx/slice/widget/DisplayedListItems;-><init>(Ljava/util/List;I)V

    .line 148
    .line 149
    .line 150
    return-object p0

    .line 151
    :cond_8
    :goto_5
    new-instance p0, Landroidx/slice/widget/DisplayedListItems;

    .line 152
    .line 153
    invoke-direct {p0, v0, v2}, Landroidx/slice/widget/DisplayedListItems;-><init>(Ljava/util/List;I)V

    .line 154
    .line 155
    .line 156
    return-object p0
.end method

.method public final getListItemsHeight(Ljava/util/List;Landroidx/slice/widget/SliceViewPolicy;)I
    .locals 4

    .line 1
    const/4 v0, 0x0

    .line 2
    if-nez p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    move v1, v0

    .line 6
    :goto_0
    invoke-interface {p1}, Ljava/util/List;->size()I

    .line 7
    .line 8
    .line 9
    move-result v2

    .line 10
    if-ge v0, v2, :cond_2

    .line 11
    .line 12
    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 13
    .line 14
    .line 15
    move-result-object v2

    .line 16
    check-cast v2, Landroidx/slice/widget/SliceContent;

    .line 17
    .line 18
    if-nez v0, :cond_1

    .line 19
    .line 20
    invoke-virtual {p0, p1}, Landroidx/slice/widget/SliceStyle;->shouldSkipFirstListItem(Ljava/util/List;)Z

    .line 21
    .line 22
    .line 23
    move-result v3

    .line 24
    if-eqz v3, :cond_1

    .line 25
    .line 26
    goto :goto_1

    .line 27
    :cond_1
    invoke-virtual {v2, p0, p2}, Landroidx/slice/widget/SliceContent;->getHeight(Landroidx/slice/widget/SliceStyle;Landroidx/slice/widget/SliceViewPolicy;)I

    .line 28
    .line 29
    .line 30
    move-result v2

    .line 31
    add-int/2addr v2, v1

    .line 32
    move v1, v2

    .line 33
    :goto_1
    add-int/lit8 v0, v0, 0x1

    .line 34
    .line 35
    goto :goto_0

    .line 36
    :cond_2
    return v1
.end method

.method public final getRowStyle(Landroidx/slice/SliceItem;)Landroidx/slice/widget/RowStyle;
    .locals 3

    .line 1
    iget-object p1, p0, Landroidx/slice/widget/SliceStyle;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    iget v0, p0, Landroidx/slice/widget/SliceStyle;->mDefaultRowStyleRes:I

    .line 4
    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    new-instance v0, Landroidx/slice/widget/RowStyle;

    .line 8
    .line 9
    invoke-direct {v0, p1, p0}, Landroidx/slice/widget/RowStyle;-><init>(Landroid/content/Context;Landroidx/slice/widget/SliceStyle;)V

    .line 10
    .line 11
    .line 12
    return-object v0

    .line 13
    :cond_0
    iget-object v1, p0, Landroidx/slice/widget/SliceStyle;->mResourceToRowStyle:Landroid/util/SparseArray;

    .line 14
    .line 15
    invoke-virtual {v1, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    .line 16
    .line 17
    .line 18
    move-result-object v2

    .line 19
    check-cast v2, Landroidx/slice/widget/RowStyle;

    .line 20
    .line 21
    if-nez v2, :cond_1

    .line 22
    .line 23
    new-instance v2, Landroidx/slice/widget/RowStyle;

    .line 24
    .line 25
    invoke-direct {v2, p1, v0, p0}, Landroidx/slice/widget/RowStyle;-><init>(Landroid/content/Context;ILandroidx/slice/widget/SliceStyle;)V

    .line 26
    .line 27
    .line 28
    invoke-virtual {v1, v0, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 29
    .line 30
    .line 31
    :cond_1
    return-object v2
.end method

.method public final shouldSkipFirstListItem(Ljava/util/List;)Z
    .locals 2

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-boolean p0, p0, Landroidx/slice/widget/SliceStyle;->mHideHeaderRow:Z

    .line 3
    .line 4
    if-eqz p0, :cond_0

    .line 5
    .line 6
    invoke-interface {p1}, Ljava/util/List;->size()I

    .line 7
    .line 8
    .line 9
    move-result p0

    .line 10
    const/4 v1, 0x1

    .line 11
    if-le p0, v1, :cond_0

    .line 12
    .line 13
    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    instance-of p0, p0, Landroidx/slice/widget/RowContent;

    .line 18
    .line 19
    if-eqz p0, :cond_0

    .line 20
    .line 21
    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    .line 22
    .line 23
    .line 24
    move-result-object p0

    .line 25
    check-cast p0, Landroidx/slice/widget/RowContent;

    .line 26
    .line 27
    iget-boolean p0, p0, Landroidx/slice/widget/RowContent;->mIsHeader:Z

    .line 28
    .line 29
    if-eqz p0, :cond_0

    .line 30
    .line 31
    move v0, v1

    .line 32
    :cond_0
    return v0
.end method

.class public final Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mBounds:Landroid/graphics/Rect;

.field public final mFillPaint:Landroid/graphics/Paint;

.field public mKeyFrameCount:I

.field public final mKeyFramePoints:[F

.field public final mPaint:Landroid/graphics/Paint;

.field public final mPaintGraph:Landroid/graphics/Paint;

.field public final mPaintKeyframes:Landroid/graphics/Paint;

.field public mPath:Landroid/graphics/Path;

.field public final mPathMode:[I

.field public mPoints:[F

.field public final mRectangle:[F

.field public final mShadowTranslate:I

.field public final mTextPaint:Landroid/graphics/Paint;

.field public final synthetic this$0:Landroidx/constraintlayout/motion/widget/MotionLayout;


# direct methods
.method public constructor <init>(Landroidx/constraintlayout/motion/widget/MotionLayout;)V
    .locals 4

    .line 1
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->this$0:Landroidx/constraintlayout/motion/widget/MotionLayout;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    new-instance v0, Landroid/graphics/Rect;

    .line 7
    .line 8
    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 9
    .line 10
    .line 11
    iput-object v0, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mBounds:Landroid/graphics/Rect;

    .line 12
    .line 13
    const/4 v0, 0x1

    .line 14
    iput v0, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mShadowTranslate:I

    .line 15
    .line 16
    new-instance v1, Landroid/graphics/Paint;

    .line 17
    .line 18
    invoke-direct {v1}, Landroid/graphics/Paint;-><init>()V

    .line 19
    .line 20
    .line 21
    iput-object v1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaint:Landroid/graphics/Paint;

    .line 22
    .line 23
    invoke-virtual {v1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 24
    .line 25
    .line 26
    const/16 v2, -0x55cd

    .line 27
    .line 28
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setColor(I)V

    .line 29
    .line 30
    .line 31
    const/high16 v2, 0x40000000    # 2.0f

    .line 32
    .line 33
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 34
    .line 35
    .line 36
    sget-object v3, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    .line 37
    .line 38
    invoke-virtual {v1, v3}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 39
    .line 40
    .line 41
    new-instance v1, Landroid/graphics/Paint;

    .line 42
    .line 43
    invoke-direct {v1}, Landroid/graphics/Paint;-><init>()V

    .line 44
    .line 45
    .line 46
    iput-object v1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintKeyframes:Landroid/graphics/Paint;

    .line 47
    .line 48
    invoke-virtual {v1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 49
    .line 50
    .line 51
    const v3, -0x1f8a66

    .line 52
    .line 53
    .line 54
    invoke-virtual {v1, v3}, Landroid/graphics/Paint;->setColor(I)V

    .line 55
    .line 56
    .line 57
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 58
    .line 59
    .line 60
    sget-object v3, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    .line 61
    .line 62
    invoke-virtual {v1, v3}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 63
    .line 64
    .line 65
    new-instance v1, Landroid/graphics/Paint;

    .line 66
    .line 67
    invoke-direct {v1}, Landroid/graphics/Paint;-><init>()V

    .line 68
    .line 69
    .line 70
    iput-object v1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 71
    .line 72
    invoke-virtual {v1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 73
    .line 74
    .line 75
    const v3, -0xcc5600

    .line 76
    .line 77
    .line 78
    invoke-virtual {v1, v3}, Landroid/graphics/Paint;->setColor(I)V

    .line 79
    .line 80
    .line 81
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 82
    .line 83
    .line 84
    sget-object v2, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    .line 85
    .line 86
    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 87
    .line 88
    .line 89
    new-instance v2, Landroid/graphics/Paint;

    .line 90
    .line 91
    invoke-direct {v2}, Landroid/graphics/Paint;-><init>()V

    .line 92
    .line 93
    .line 94
    iput-object v2, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mTextPaint:Landroid/graphics/Paint;

    .line 95
    .line 96
    invoke-virtual {v2, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 97
    .line 98
    .line 99
    invoke-virtual {v2, v3}, Landroid/graphics/Paint;->setColor(I)V

    .line 100
    .line 101
    .line 102
    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    .line 103
    .line 104
    .line 105
    move-result-object p1

    .line 106
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 107
    .line 108
    .line 109
    move-result-object p1

    .line 110
    invoke-virtual {p1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 111
    .line 112
    .line 113
    move-result-object p1

    .line 114
    iget p1, p1, Landroid/util/DisplayMetrics;->density:F

    .line 115
    .line 116
    const/high16 v3, 0x41400000    # 12.0f

    .line 117
    .line 118
    mul-float/2addr p1, v3

    .line 119
    invoke-virtual {v2, p1}, Landroid/graphics/Paint;->setTextSize(F)V

    .line 120
    .line 121
    .line 122
    const/16 p1, 0x8

    .line 123
    .line 124
    new-array p1, p1, [F

    .line 125
    .line 126
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mRectangle:[F

    .line 127
    .line 128
    new-instance p1, Landroid/graphics/Paint;

    .line 129
    .line 130
    invoke-direct {p1}, Landroid/graphics/Paint;-><init>()V

    .line 131
    .line 132
    .line 133
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mFillPaint:Landroid/graphics/Paint;

    .line 134
    .line 135
    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 136
    .line 137
    .line 138
    new-instance p1, Landroid/graphics/DashPathEffect;

    .line 139
    .line 140
    const/4 v0, 0x2

    .line 141
    new-array v0, v0, [F

    .line 142
    .line 143
    fill-array-data v0, :array_0

    .line 144
    .line 145
    .line 146
    const/4 v2, 0x0

    .line 147
    invoke-direct {p1, v0, v2}, Landroid/graphics/DashPathEffect;-><init>([FF)V

    .line 148
    .line 149
    .line 150
    invoke-virtual {v1, p1}, Landroid/graphics/Paint;->setPathEffect(Landroid/graphics/PathEffect;)Landroid/graphics/PathEffect;

    .line 151
    .line 152
    .line 153
    const/16 p1, 0x64

    .line 154
    .line 155
    new-array p1, p1, [F

    .line 156
    .line 157
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mKeyFramePoints:[F

    .line 158
    .line 159
    const/16 p1, 0x32

    .line 160
    .line 161
    new-array p1, p1, [I

    .line 162
    .line 163
    iput-object p1, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPathMode:[I

    .line 164
    .line 165
    return-void

    .line 166
    nop

    .line 167
    :array_0
    .array-data 4
        0x40800000    # 4.0f
        0x41000000    # 8.0f
    .end array-data
.end method


# virtual methods
.method public final drawAll(Landroid/graphics/Canvas;IILandroidx/constraintlayout/motion/widget/MotionController;)V
    .locals 22

    .line 1
    move-object/from16 v6, p0

    .line 2
    .line 3
    move-object/from16 v7, p1

    .line 4
    .line 5
    move/from16 v8, p2

    .line 6
    .line 7
    move-object/from16 v9, p4

    .line 8
    .line 9
    iget-object v10, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 10
    .line 11
    iget-object v11, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPathMode:[I

    .line 12
    .line 13
    const/4 v12, 0x1

    .line 14
    const/4 v13, 0x0

    .line 15
    const/4 v14, 0x4

    .line 16
    if-ne v8, v14, :cond_4

    .line 17
    .line 18
    move v0, v13

    .line 19
    move v1, v0

    .line 20
    move v15, v1

    .line 21
    :goto_0
    iget v2, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mKeyFrameCount:I

    .line 22
    .line 23
    if-ge v0, v2, :cond_2

    .line 24
    .line 25
    aget v2, v11, v0

    .line 26
    .line 27
    if-ne v2, v12, :cond_0

    .line 28
    .line 29
    move v1, v12

    .line 30
    :cond_0
    if-nez v2, :cond_1

    .line 31
    .line 32
    move v15, v12

    .line 33
    :cond_1
    add-int/lit8 v0, v0, 0x1

    .line 34
    .line 35
    goto :goto_0

    .line 36
    :cond_2
    if-eqz v1, :cond_3

    .line 37
    .line 38
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 39
    .line 40
    aget v1, v0, v13

    .line 41
    .line 42
    aget v2, v0, v12

    .line 43
    .line 44
    array-length v3, v0

    .line 45
    add-int/lit8 v3, v3, -0x2

    .line 46
    .line 47
    aget v3, v0, v3

    .line 48
    .line 49
    array-length v4, v0

    .line 50
    sub-int/2addr v4, v12

    .line 51
    aget v4, v0, v4

    .line 52
    .line 53
    move-object/from16 v0, p1

    .line 54
    .line 55
    move-object v5, v10

    .line 56
    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 57
    .line 58
    .line 59
    :cond_3
    if-eqz v15, :cond_4

    .line 60
    .line 61
    invoke-virtual/range {p0 .. p1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathCartesian(Landroid/graphics/Canvas;)V

    .line 62
    .line 63
    .line 64
    :cond_4
    const/4 v15, 0x2

    .line 65
    if-ne v8, v15, :cond_5

    .line 66
    .line 67
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 68
    .line 69
    aget v1, v0, v13

    .line 70
    .line 71
    aget v2, v0, v12

    .line 72
    .line 73
    array-length v3, v0

    .line 74
    add-int/lit8 v3, v3, -0x2

    .line 75
    .line 76
    aget v3, v0, v3

    .line 77
    .line 78
    array-length v4, v0

    .line 79
    sub-int/2addr v4, v12

    .line 80
    aget v4, v0, v4

    .line 81
    .line 82
    move-object/from16 v0, p1

    .line 83
    .line 84
    move-object v5, v10

    .line 85
    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 86
    .line 87
    .line 88
    :cond_5
    const/4 v10, 0x3

    .line 89
    if-ne v8, v10, :cond_6

    .line 90
    .line 91
    invoke-virtual/range {p0 .. p1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathCartesian(Landroid/graphics/Canvas;)V

    .line 92
    .line 93
    .line 94
    :cond_6
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 95
    .line 96
    iget-object v1, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaint:Landroid/graphics/Paint;

    .line 97
    .line 98
    invoke-virtual {v7, v0, v1}, Landroid/graphics/Canvas;->drawLines([FLandroid/graphics/Paint;)V

    .line 99
    .line 100
    .line 101
    iget-object v0, v9, Landroidx/constraintlayout/motion/widget/MotionController;->mView:Landroid/view/View;

    .line 102
    .line 103
    if-eqz v0, :cond_7

    .line 104
    .line 105
    invoke-virtual {v0}, Landroid/view/View;->getWidth()I

    .line 106
    .line 107
    .line 108
    move-result v0

    .line 109
    iget-object v1, v9, Landroidx/constraintlayout/motion/widget/MotionController;->mView:Landroid/view/View;

    .line 110
    .line 111
    invoke-virtual {v1}, Landroid/view/View;->getHeight()I

    .line 112
    .line 113
    .line 114
    move-result v1

    .line 115
    move/from16 v16, v0

    .line 116
    .line 117
    move/from16 v17, v1

    .line 118
    .line 119
    goto :goto_1

    .line 120
    :cond_7
    move/from16 v16, v13

    .line 121
    .line 122
    move/from16 v17, v16

    .line 123
    .line 124
    :goto_1
    move v5, v12

    .line 125
    :goto_2
    add-int/lit8 v0, p3, -0x1

    .line 126
    .line 127
    if-ge v5, v0, :cond_10

    .line 128
    .line 129
    if-ne v8, v14, :cond_8

    .line 130
    .line 131
    add-int/lit8 v0, v5, -0x1

    .line 132
    .line 133
    aget v0, v11, v0

    .line 134
    .line 135
    if-nez v0, :cond_8

    .line 136
    .line 137
    move/from16 v21, v5

    .line 138
    .line 139
    goto/16 :goto_6

    .line 140
    .line 141
    :cond_8
    mul-int/lit8 v0, v5, 0x2

    .line 142
    .line 143
    iget-object v1, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mKeyFramePoints:[F

    .line 144
    .line 145
    aget v4, v1, v0

    .line 146
    .line 147
    add-int/2addr v0, v12

    .line 148
    aget v3, v1, v0

    .line 149
    .line 150
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 151
    .line 152
    invoke-virtual {v0}, Landroid/graphics/Path;->reset()V

    .line 153
    .line 154
    .line 155
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 156
    .line 157
    const/high16 v1, 0x41200000    # 10.0f

    .line 158
    .line 159
    add-float v2, v3, v1

    .line 160
    .line 161
    invoke-virtual {v0, v4, v2}, Landroid/graphics/Path;->moveTo(FF)V

    .line 162
    .line 163
    .line 164
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 165
    .line 166
    add-float v2, v4, v1

    .line 167
    .line 168
    invoke-virtual {v0, v2, v3}, Landroid/graphics/Path;->lineTo(FF)V

    .line 169
    .line 170
    .line 171
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 172
    .line 173
    sub-float v2, v3, v1

    .line 174
    .line 175
    invoke-virtual {v0, v4, v2}, Landroid/graphics/Path;->lineTo(FF)V

    .line 176
    .line 177
    .line 178
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 179
    .line 180
    sub-float v1, v4, v1

    .line 181
    .line 182
    invoke-virtual {v0, v1, v3}, Landroid/graphics/Path;->lineTo(FF)V

    .line 183
    .line 184
    .line 185
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 186
    .line 187
    invoke-virtual {v0}, Landroid/graphics/Path;->close()V

    .line 188
    .line 189
    .line 190
    add-int/lit8 v0, v5, -0x1

    .line 191
    .line 192
    iget-object v1, v9, Landroidx/constraintlayout/motion/widget/MotionController;->mMotionPaths:Ljava/util/ArrayList;

    .line 193
    .line 194
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 195
    .line 196
    .line 197
    move-result-object v1

    .line 198
    check-cast v1, Landroidx/constraintlayout/motion/widget/MotionPaths;

    .line 199
    .line 200
    iget-object v2, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mFillPaint:Landroid/graphics/Paint;

    .line 201
    .line 202
    const/16 v18, 0x0

    .line 203
    .line 204
    if-ne v8, v14, :cond_c

    .line 205
    .line 206
    aget v0, v11, v0

    .line 207
    .line 208
    if-ne v0, v12, :cond_a

    .line 209
    .line 210
    sub-float v0, v4, v18

    .line 211
    .line 212
    sub-float v1, v3, v18

    .line 213
    .line 214
    invoke-virtual {v6, v7, v0, v1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathRelativeTicks(Landroid/graphics/Canvas;FF)V

    .line 215
    .line 216
    .line 217
    :cond_9
    :goto_3
    move-object v14, v2

    .line 218
    move/from16 v19, v3

    .line 219
    .line 220
    move/from16 v20, v4

    .line 221
    .line 222
    move/from16 v21, v5

    .line 223
    .line 224
    goto :goto_4

    .line 225
    :cond_a
    if-nez v0, :cond_b

    .line 226
    .line 227
    sub-float v0, v4, v18

    .line 228
    .line 229
    sub-float v1, v3, v18

    .line 230
    .line 231
    invoke-virtual {v6, v7, v0, v1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathCartesianTicks(Landroid/graphics/Canvas;FF)V

    .line 232
    .line 233
    .line 234
    goto :goto_3

    .line 235
    :cond_b
    if-ne v0, v15, :cond_9

    .line 236
    .line 237
    sub-float v19, v4, v18

    .line 238
    .line 239
    sub-float v20, v3, v18

    .line 240
    .line 241
    move-object/from16 v0, p0

    .line 242
    .line 243
    move-object/from16 v1, p1

    .line 244
    .line 245
    move-object v14, v2

    .line 246
    move/from16 v2, v19

    .line 247
    .line 248
    move/from16 v19, v3

    .line 249
    .line 250
    move/from16 v3, v20

    .line 251
    .line 252
    move/from16 v20, v4

    .line 253
    .line 254
    move/from16 v4, v16

    .line 255
    .line 256
    move/from16 v21, v5

    .line 257
    .line 258
    move/from16 v5, v17

    .line 259
    .line 260
    invoke-virtual/range {v0 .. v5}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathScreenTicks(Landroid/graphics/Canvas;FFII)V

    .line 261
    .line 262
    .line 263
    :goto_4
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 264
    .line 265
    invoke-virtual {v7, v0, v14}, Landroid/graphics/Canvas;->drawPath(Landroid/graphics/Path;Landroid/graphics/Paint;)V

    .line 266
    .line 267
    .line 268
    goto :goto_5

    .line 269
    :cond_c
    move-object v14, v2

    .line 270
    move/from16 v19, v3

    .line 271
    .line 272
    move/from16 v20, v4

    .line 273
    .line 274
    move/from16 v21, v5

    .line 275
    .line 276
    :goto_5
    if-ne v8, v15, :cond_d

    .line 277
    .line 278
    sub-float v4, v20, v18

    .line 279
    .line 280
    sub-float v3, v19, v18

    .line 281
    .line 282
    invoke-virtual {v6, v7, v4, v3}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathRelativeTicks(Landroid/graphics/Canvas;FF)V

    .line 283
    .line 284
    .line 285
    :cond_d
    if-ne v8, v10, :cond_e

    .line 286
    .line 287
    sub-float v4, v20, v18

    .line 288
    .line 289
    sub-float v3, v19, v18

    .line 290
    .line 291
    invoke-virtual {v6, v7, v4, v3}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathCartesianTicks(Landroid/graphics/Canvas;FF)V

    .line 292
    .line 293
    .line 294
    :cond_e
    const/4 v0, 0x6

    .line 295
    if-ne v8, v0, :cond_f

    .line 296
    .line 297
    sub-float v2, v20, v18

    .line 298
    .line 299
    sub-float v3, v19, v18

    .line 300
    .line 301
    move-object/from16 v0, p0

    .line 302
    .line 303
    move-object/from16 v1, p1

    .line 304
    .line 305
    move/from16 v4, v16

    .line 306
    .line 307
    move/from16 v5, v17

    .line 308
    .line 309
    invoke-virtual/range {v0 .. v5}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->drawPathScreenTicks(Landroid/graphics/Canvas;FFII)V

    .line 310
    .line 311
    .line 312
    :cond_f
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPath:Landroid/graphics/Path;

    .line 313
    .line 314
    invoke-virtual {v7, v0, v14}, Landroid/graphics/Canvas;->drawPath(Landroid/graphics/Path;Landroid/graphics/Paint;)V

    .line 315
    .line 316
    .line 317
    :goto_6
    add-int/lit8 v5, v21, 0x1

    .line 318
    .line 319
    const/4 v14, 0x4

    .line 320
    goto/16 :goto_2

    .line 321
    .line 322
    :cond_10
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 323
    .line 324
    array-length v1, v0

    .line 325
    if-le v1, v12, :cond_11

    .line 326
    .line 327
    aget v1, v0, v13

    .line 328
    .line 329
    aget v0, v0, v12

    .line 330
    .line 331
    iget-object v2, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintKeyframes:Landroid/graphics/Paint;

    .line 332
    .line 333
    const/high16 v3, 0x41000000    # 8.0f

    .line 334
    .line 335
    invoke-virtual {v7, v1, v0, v3, v2}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 336
    .line 337
    .line 338
    iget-object v0, v6, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 339
    .line 340
    array-length v1, v0

    .line 341
    sub-int/2addr v1, v15

    .line 342
    aget v1, v0, v1

    .line 343
    .line 344
    array-length v4, v0

    .line 345
    sub-int/2addr v4, v12

    .line 346
    aget v0, v0, v4

    .line 347
    .line 348
    invoke-virtual {v7, v1, v0, v3, v2}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 349
    .line 350
    .line 351
    :cond_11
    return-void
.end method

.method public final drawPathCartesian(Landroid/graphics/Canvas;)V
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    iget-object v1, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    aget v2, v1, v2

    .line 7
    .line 8
    const/4 v3, 0x1

    .line 9
    aget v4, v1, v3

    .line 10
    .line 11
    array-length v5, v1

    .line 12
    add-int/lit8 v5, v5, -0x2

    .line 13
    .line 14
    aget v5, v1, v5

    .line 15
    .line 16
    array-length v6, v1

    .line 17
    sub-int/2addr v6, v3

    .line 18
    aget v1, v1, v6

    .line 19
    .line 20
    invoke-static {v2, v5}, Ljava/lang/Math;->min(FF)F

    .line 21
    .line 22
    .line 23
    move-result v7

    .line 24
    invoke-static {v4, v1}, Ljava/lang/Math;->max(FF)F

    .line 25
    .line 26
    .line 27
    move-result v8

    .line 28
    invoke-static {v2, v5}, Ljava/lang/Math;->max(FF)F

    .line 29
    .line 30
    .line 31
    move-result v9

    .line 32
    invoke-static {v4, v1}, Ljava/lang/Math;->max(FF)F

    .line 33
    .line 34
    .line 35
    move-result v10

    .line 36
    iget-object v0, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 37
    .line 38
    move-object/from16 v6, p1

    .line 39
    .line 40
    move-object v11, v0

    .line 41
    invoke-virtual/range {v6 .. v11}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 42
    .line 43
    .line 44
    invoke-static {v2, v5}, Ljava/lang/Math;->min(FF)F

    .line 45
    .line 46
    .line 47
    move-result v12

    .line 48
    invoke-static {v4, v1}, Ljava/lang/Math;->min(FF)F

    .line 49
    .line 50
    .line 51
    move-result v13

    .line 52
    invoke-static {v2, v5}, Ljava/lang/Math;->min(FF)F

    .line 53
    .line 54
    .line 55
    move-result v14

    .line 56
    invoke-static {v4, v1}, Ljava/lang/Math;->max(FF)F

    .line 57
    .line 58
    .line 59
    move-result v15

    .line 60
    move-object/from16 v11, p1

    .line 61
    .line 62
    move-object/from16 v16, v0

    .line 63
    .line 64
    invoke-virtual/range {v11 .. v16}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 65
    .line 66
    .line 67
    return-void
.end method

.method public final drawPathCartesianTicks(Landroid/graphics/Canvas;FF)V
    .locals 19

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move-object/from16 v7, p1

    .line 4
    .line 5
    iget-object v1, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    aget v2, v1, v2

    .line 9
    .line 10
    const/4 v3, 0x1

    .line 11
    aget v8, v1, v3

    .line 12
    .line 13
    array-length v4, v1

    .line 14
    add-int/lit8 v4, v4, -0x2

    .line 15
    .line 16
    aget v4, v1, v4

    .line 17
    .line 18
    array-length v5, v1

    .line 19
    sub-int/2addr v5, v3

    .line 20
    aget v9, v1, v5

    .line 21
    .line 22
    invoke-static {v2, v4}, Ljava/lang/Math;->min(FF)F

    .line 23
    .line 24
    .line 25
    move-result v1

    .line 26
    invoke-static {v8, v9}, Ljava/lang/Math;->max(FF)F

    .line 27
    .line 28
    .line 29
    move-result v10

    .line 30
    invoke-static {v2, v4}, Ljava/lang/Math;->min(FF)F

    .line 31
    .line 32
    .line 33
    move-result v3

    .line 34
    sub-float v3, p2, v3

    .line 35
    .line 36
    invoke-static {v8, v9}, Ljava/lang/Math;->max(FF)F

    .line 37
    .line 38
    .line 39
    move-result v5

    .line 40
    sub-float v11, v5, p3

    .line 41
    .line 42
    new-instance v5, Ljava/lang/StringBuilder;

    .line 43
    .line 44
    const-string v12, ""

    .line 45
    .line 46
    invoke-direct {v5, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 47
    .line 48
    .line 49
    const/high16 v13, 0x42c80000    # 100.0f

    .line 50
    .line 51
    mul-float v6, v3, v13

    .line 52
    .line 53
    sub-float v14, v4, v2

    .line 54
    .line 55
    invoke-static {v14}, Ljava/lang/Math;->abs(F)F

    .line 56
    .line 57
    .line 58
    move-result v14

    .line 59
    div-float/2addr v6, v14

    .line 60
    float-to-double v14, v6

    .line 61
    const-wide/high16 v16, 0x3fe0000000000000L    # 0.5

    .line 62
    .line 63
    add-double v14, v14, v16

    .line 64
    .line 65
    double-to-int v6, v14

    .line 66
    int-to-float v6, v6

    .line 67
    div-float/2addr v6, v13

    .line 68
    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 69
    .line 70
    .line 71
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 72
    .line 73
    .line 74
    move-result-object v5

    .line 75
    iget-object v14, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mTextPaint:Landroid/graphics/Paint;

    .line 76
    .line 77
    invoke-virtual {v0, v14, v5}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V

    .line 78
    .line 79
    .line 80
    const/high16 v15, 0x40000000    # 2.0f

    .line 81
    .line 82
    div-float/2addr v3, v15

    .line 83
    iget-object v6, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mBounds:Landroid/graphics/Rect;

    .line 84
    .line 85
    invoke-virtual {v6}, Landroid/graphics/Rect;->width()I

    .line 86
    .line 87
    .line 88
    move-result v18

    .line 89
    div-int/lit8 v15, v18, 0x2

    .line 90
    .line 91
    int-to-float v15, v15

    .line 92
    sub-float/2addr v3, v15

    .line 93
    add-float/2addr v3, v1

    .line 94
    const/high16 v1, 0x41a00000    # 20.0f

    .line 95
    .line 96
    sub-float v1, p3, v1

    .line 97
    .line 98
    invoke-virtual {v7, v5, v3, v1, v14}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 99
    .line 100
    .line 101
    invoke-static {v2, v4}, Ljava/lang/Math;->min(FF)F

    .line 102
    .line 103
    .line 104
    move-result v4

    .line 105
    iget-object v15, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 106
    .line 107
    move-object/from16 v1, p1

    .line 108
    .line 109
    move/from16 v2, p2

    .line 110
    .line 111
    move/from16 v3, p3

    .line 112
    .line 113
    move/from16 v5, p3

    .line 114
    .line 115
    move-object/from16 v18, v6

    .line 116
    .line 117
    move-object v6, v15

    .line 118
    invoke-virtual/range {v1 .. v6}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 119
    .line 120
    .line 121
    new-instance v1, Ljava/lang/StringBuilder;

    .line 122
    .line 123
    invoke-direct {v1, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 124
    .line 125
    .line 126
    mul-float v2, v11, v13

    .line 127
    .line 128
    sub-float v3, v9, v8

    .line 129
    .line 130
    invoke-static {v3}, Ljava/lang/Math;->abs(F)F

    .line 131
    .line 132
    .line 133
    move-result v3

    .line 134
    div-float/2addr v2, v3

    .line 135
    float-to-double v2, v2

    .line 136
    add-double v2, v2, v16

    .line 137
    .line 138
    double-to-int v2, v2

    .line 139
    int-to-float v2, v2

    .line 140
    div-float/2addr v2, v13

    .line 141
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 142
    .line 143
    .line 144
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 145
    .line 146
    .line 147
    move-result-object v1

    .line 148
    invoke-virtual {v0, v14, v1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V

    .line 149
    .line 150
    .line 151
    const/high16 v0, 0x40000000    # 2.0f

    .line 152
    .line 153
    div-float/2addr v11, v0

    .line 154
    invoke-virtual/range {v18 .. v18}, Landroid/graphics/Rect;->height()I

    .line 155
    .line 156
    .line 157
    move-result v0

    .line 158
    div-int/lit8 v0, v0, 0x2

    .line 159
    .line 160
    int-to-float v0, v0

    .line 161
    sub-float/2addr v11, v0

    .line 162
    const/high16 v0, 0x40a00000    # 5.0f

    .line 163
    .line 164
    add-float v0, p2, v0

    .line 165
    .line 166
    sub-float/2addr v10, v11

    .line 167
    invoke-virtual {v7, v1, v0, v10, v14}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 168
    .line 169
    .line 170
    invoke-static {v8, v9}, Ljava/lang/Math;->max(FF)F

    .line 171
    .line 172
    .line 173
    move-result v4

    .line 174
    move-object/from16 v0, p1

    .line 175
    .line 176
    move/from16 v1, p2

    .line 177
    .line 178
    move/from16 v2, p3

    .line 179
    .line 180
    move/from16 v3, p2

    .line 181
    .line 182
    move-object v5, v15

    .line 183
    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 184
    .line 185
    .line 186
    return-void
.end method

.method public final drawPathRelativeTicks(Landroid/graphics/Canvas;FF)V
    .locals 13

    .line 1
    move-object v0, p0

    .line 2
    move v1, p2

    .line 3
    move/from16 v2, p3

    .line 4
    .line 5
    iget-object v3, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPoints:[F

    .line 6
    .line 7
    const/4 v4, 0x0

    .line 8
    aget v4, v3, v4

    .line 9
    .line 10
    const/4 v5, 0x1

    .line 11
    aget v6, v3, v5

    .line 12
    .line 13
    array-length v7, v3

    .line 14
    add-int/lit8 v7, v7, -0x2

    .line 15
    .line 16
    aget v7, v3, v7

    .line 17
    .line 18
    array-length v8, v3

    .line 19
    sub-int/2addr v8, v5

    .line 20
    aget v3, v3, v8

    .line 21
    .line 22
    sub-float v5, v4, v7

    .line 23
    .line 24
    float-to-double v8, v5

    .line 25
    sub-float v5, v6, v3

    .line 26
    .line 27
    float-to-double v10, v5

    .line 28
    invoke-static {v8, v9, v10, v11}, Ljava/lang/Math;->hypot(DD)D

    .line 29
    .line 30
    .line 31
    move-result-wide v8

    .line 32
    double-to-float v5, v8

    .line 33
    sub-float v8, v1, v4

    .line 34
    .line 35
    sub-float/2addr v7, v4

    .line 36
    mul-float/2addr v8, v7

    .line 37
    sub-float v9, v2, v6

    .line 38
    .line 39
    sub-float/2addr v3, v6

    .line 40
    mul-float/2addr v9, v3

    .line 41
    add-float/2addr v9, v8

    .line 42
    mul-float v8, v5, v5

    .line 43
    .line 44
    div-float/2addr v9, v8

    .line 45
    mul-float/2addr v7, v9

    .line 46
    add-float/2addr v4, v7

    .line 47
    mul-float/2addr v9, v3

    .line 48
    add-float/2addr v6, v9

    .line 49
    new-instance v9, Landroid/graphics/Path;

    .line 50
    .line 51
    invoke-direct {v9}, Landroid/graphics/Path;-><init>()V

    .line 52
    .line 53
    .line 54
    invoke-virtual {v9, p2, v2}, Landroid/graphics/Path;->moveTo(FF)V

    .line 55
    .line 56
    .line 57
    invoke-virtual {v9, v4, v6}, Landroid/graphics/Path;->lineTo(FF)V

    .line 58
    .line 59
    .line 60
    sub-float v3, v4, v1

    .line 61
    .line 62
    float-to-double v7, v3

    .line 63
    sub-float v3, v6, v2

    .line 64
    .line 65
    float-to-double v10, v3

    .line 66
    invoke-static {v7, v8, v10, v11}, Ljava/lang/Math;->hypot(DD)D

    .line 67
    .line 68
    .line 69
    move-result-wide v7

    .line 70
    double-to-float v3, v7

    .line 71
    new-instance v7, Ljava/lang/StringBuilder;

    .line 72
    .line 73
    const-string v8, ""

    .line 74
    .line 75
    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 76
    .line 77
    .line 78
    const/high16 v8, 0x42c80000    # 100.0f

    .line 79
    .line 80
    mul-float v10, v3, v8

    .line 81
    .line 82
    div-float/2addr v10, v5

    .line 83
    float-to-int v5, v10

    .line 84
    int-to-float v5, v5

    .line 85
    div-float/2addr v5, v8

    .line 86
    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 87
    .line 88
    .line 89
    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 90
    .line 91
    .line 92
    move-result-object v8

    .line 93
    iget-object v12, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mTextPaint:Landroid/graphics/Paint;

    .line 94
    .line 95
    invoke-virtual {p0, v12, v8}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V

    .line 96
    .line 97
    .line 98
    const/high16 v5, 0x40000000    # 2.0f

    .line 99
    .line 100
    div-float/2addr v3, v5

    .line 101
    iget-object v5, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mBounds:Landroid/graphics/Rect;

    .line 102
    .line 103
    invoke-virtual {v5}, Landroid/graphics/Rect;->width()I

    .line 104
    .line 105
    .line 106
    move-result v5

    .line 107
    div-int/lit8 v5, v5, 0x2

    .line 108
    .line 109
    int-to-float v5, v5

    .line 110
    sub-float v10, v3, v5

    .line 111
    .line 112
    const/high16 v11, -0x3e600000    # -20.0f

    .line 113
    .line 114
    move-object v7, p1

    .line 115
    invoke-virtual/range {v7 .. v12}, Landroid/graphics/Canvas;->drawTextOnPath(Ljava/lang/String;Landroid/graphics/Path;FFLandroid/graphics/Paint;)V

    .line 116
    .line 117
    .line 118
    iget-object v5, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 119
    .line 120
    move-object v0, p1

    .line 121
    move v3, v4

    .line 122
    move v4, v6

    .line 123
    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 124
    .line 125
    .line 126
    return-void
.end method

.method public final drawPathScreenTicks(Landroid/graphics/Canvas;FFII)V
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move-object/from16 v7, p1

    .line 4
    .line 5
    new-instance v1, Ljava/lang/StringBuilder;

    .line 6
    .line 7
    const-string v8, ""

    .line 8
    .line 9
    invoke-direct {v1, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 10
    .line 11
    .line 12
    div-int/lit8 v2, p4, 0x2

    .line 13
    .line 14
    int-to-float v2, v2

    .line 15
    sub-float v2, p2, v2

    .line 16
    .line 17
    const/high16 v9, 0x42c80000    # 100.0f

    .line 18
    .line 19
    mul-float/2addr v2, v9

    .line 20
    iget-object v10, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->this$0:Landroidx/constraintlayout/motion/widget/MotionLayout;

    .line 21
    .line 22
    invoke-virtual {v10}, Landroid/view/ViewGroup;->getWidth()I

    .line 23
    .line 24
    .line 25
    move-result v3

    .line 26
    sub-int v3, v3, p4

    .line 27
    .line 28
    int-to-float v3, v3

    .line 29
    div-float/2addr v2, v3

    .line 30
    float-to-double v2, v2

    .line 31
    const-wide/high16 v11, 0x3fe0000000000000L    # 0.5

    .line 32
    .line 33
    add-double/2addr v2, v11

    .line 34
    double-to-int v2, v2

    .line 35
    int-to-float v2, v2

    .line 36
    div-float/2addr v2, v9

    .line 37
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 38
    .line 39
    .line 40
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 41
    .line 42
    .line 43
    move-result-object v1

    .line 44
    iget-object v13, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mTextPaint:Landroid/graphics/Paint;

    .line 45
    .line 46
    invoke-virtual {v0, v13, v1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V

    .line 47
    .line 48
    .line 49
    const/high16 v14, 0x40000000    # 2.0f

    .line 50
    .line 51
    div-float v2, p2, v14

    .line 52
    .line 53
    iget-object v15, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mBounds:Landroid/graphics/Rect;

    .line 54
    .line 55
    invoke-virtual {v15}, Landroid/graphics/Rect;->width()I

    .line 56
    .line 57
    .line 58
    move-result v3

    .line 59
    div-int/lit8 v3, v3, 0x2

    .line 60
    .line 61
    int-to-float v3, v3

    .line 62
    sub-float/2addr v2, v3

    .line 63
    const/4 v6, 0x0

    .line 64
    add-float/2addr v2, v6

    .line 65
    const/high16 v3, 0x41a00000    # 20.0f

    .line 66
    .line 67
    sub-float v3, p3, v3

    .line 68
    .line 69
    invoke-virtual {v7, v1, v2, v3, v13}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 70
    .line 71
    .line 72
    const/high16 v5, 0x3f800000    # 1.0f

    .line 73
    .line 74
    invoke-static {v6, v5}, Ljava/lang/Math;->min(FF)F

    .line 75
    .line 76
    .line 77
    move-result v4

    .line 78
    iget-object v3, v0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mPaintGraph:Landroid/graphics/Paint;

    .line 79
    .line 80
    move-object/from16 v1, p1

    .line 81
    .line 82
    move/from16 v2, p2

    .line 83
    .line 84
    move-object/from16 v16, v3

    .line 85
    .line 86
    move/from16 v3, p3

    .line 87
    .line 88
    move/from16 v5, p3

    .line 89
    .line 90
    move-object/from16 v6, v16

    .line 91
    .line 92
    invoke-virtual/range {v1 .. v6}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 93
    .line 94
    .line 95
    new-instance v1, Ljava/lang/StringBuilder;

    .line 96
    .line 97
    invoke-direct {v1, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 98
    .line 99
    .line 100
    div-int/lit8 v2, p5, 0x2

    .line 101
    .line 102
    int-to-float v2, v2

    .line 103
    sub-float v2, p3, v2

    .line 104
    .line 105
    mul-float/2addr v2, v9

    .line 106
    invoke-virtual {v10}, Landroid/view/ViewGroup;->getHeight()I

    .line 107
    .line 108
    .line 109
    move-result v3

    .line 110
    sub-int v3, v3, p5

    .line 111
    .line 112
    int-to-float v3, v3

    .line 113
    div-float/2addr v2, v3

    .line 114
    float-to-double v2, v2

    .line 115
    add-double/2addr v2, v11

    .line 116
    double-to-int v2, v2

    .line 117
    int-to-float v2, v2

    .line 118
    div-float/2addr v2, v9

    .line 119
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 120
    .line 121
    .line 122
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 123
    .line 124
    .line 125
    move-result-object v1

    .line 126
    invoke-virtual {v0, v13, v1}, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V

    .line 127
    .line 128
    .line 129
    div-float v0, p3, v14

    .line 130
    .line 131
    invoke-virtual {v15}, Landroid/graphics/Rect;->height()I

    .line 132
    .line 133
    .line 134
    move-result v2

    .line 135
    div-int/lit8 v2, v2, 0x2

    .line 136
    .line 137
    int-to-float v2, v2

    .line 138
    sub-float/2addr v0, v2

    .line 139
    const/high16 v2, 0x40a00000    # 5.0f

    .line 140
    .line 141
    add-float v2, p2, v2

    .line 142
    .line 143
    const/4 v3, 0x0

    .line 144
    sub-float v6, v3, v0

    .line 145
    .line 146
    invoke-virtual {v7, v1, v2, v6, v13}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 147
    .line 148
    .line 149
    const/high16 v0, 0x3f800000    # 1.0f

    .line 150
    .line 151
    invoke-static {v3, v0}, Ljava/lang/Math;->max(FF)F

    .line 152
    .line 153
    .line 154
    move-result v4

    .line 155
    move-object/from16 v0, p1

    .line 156
    .line 157
    move/from16 v1, p2

    .line 158
    .line 159
    move/from16 v2, p3

    .line 160
    .line 161
    move/from16 v3, p2

    .line 162
    .line 163
    move-object/from16 v5, v16

    .line 164
    .line 165
    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 166
    .line 167
    .line 168
    return-void
.end method

.method public final getTextBounds(Landroid/graphics/Paint;Ljava/lang/String;)V
    .locals 2

    .line 1
    invoke-virtual {p2}, Ljava/lang/String;->length()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    iget-object p0, p0, Landroidx/constraintlayout/motion/widget/MotionLayout$DevModeDraw;->mBounds:Landroid/graphics/Rect;

    .line 6
    .line 7
    const/4 v1, 0x0

    .line 8
    invoke-virtual {p1, p2, v1, v0, p0}, Landroid/graphics/Paint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    .line 9
    .line 10
    .line 11
    return-void
.end method
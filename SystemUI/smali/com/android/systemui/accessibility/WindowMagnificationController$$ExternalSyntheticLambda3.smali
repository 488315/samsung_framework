.class public final synthetic Lcom/android/systemui/accessibility/WindowMagnificationController$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/Choreographer$FrameCallback;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/accessibility/WindowMagnificationController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/accessibility/WindowMagnificationController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/accessibility/WindowMagnificationController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final doFrame(J)V
    .locals 6

    .line 1
    iget-object p0, p0, Lcom/android/systemui/accessibility/WindowMagnificationController$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/accessibility/WindowMagnificationController;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/accessibility/WindowMagnificationController;->isActivated()Z

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    if-eqz p1, :cond_4

    .line 8
    .line 9
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMirrorSurface:Landroid/view/SurfaceControl;

    .line 10
    .line 11
    if-eqz p1, :cond_4

    .line 12
    .line 13
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMagnificationFrame:Landroid/graphics/Rect;

    .line 14
    .line 15
    iget p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mScale:F

    .line 16
    .line 17
    iget-object v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mTmpRect:Landroid/graphics/Rect;

    .line 18
    .line 19
    iget-object v1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 20
    .line 21
    invoke-virtual {v0, v1}, Landroid/graphics/Rect;->set(Landroid/graphics/Rect;)V

    .line 22
    .line 23
    .line 24
    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    .line 25
    .line 26
    .line 27
    move-result v1

    .line 28
    div-int/lit8 v1, v1, 0x2

    .line 29
    .line 30
    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    .line 31
    .line 32
    .line 33
    move-result v2

    .line 34
    div-int/lit8 v2, v2, 0x2

    .line 35
    .line 36
    iget v3, p1, Landroid/graphics/Rect;->left:I

    .line 37
    .line 38
    int-to-float v4, v1

    .line 39
    div-float/2addr v4, p2

    .line 40
    float-to-int v4, v4

    .line 41
    sub-int/2addr v1, v4

    .line 42
    add-int/2addr v3, v1

    .line 43
    iget v4, p1, Landroid/graphics/Rect;->right:I

    .line 44
    .line 45
    sub-int/2addr v4, v1

    .line 46
    iget v1, p1, Landroid/graphics/Rect;->top:I

    .line 47
    .line 48
    int-to-float v5, v2

    .line 49
    div-float/2addr v5, p2

    .line 50
    float-to-int p2, v5

    .line 51
    sub-int/2addr v2, p2

    .line 52
    add-int/2addr v1, v2

    .line 53
    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    .line 54
    .line 55
    sub-int/2addr p1, v2

    .line 56
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 57
    .line 58
    invoke-virtual {p2, v3, v1, v4, p1}, Landroid/graphics/Rect;->set(IIII)V

    .line 59
    .line 60
    .line 61
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 62
    .line 63
    iget p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMagnificationFrameOffsetX:I

    .line 64
    .line 65
    neg-int p2, p2

    .line 66
    iget v1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMagnificationFrameOffsetY:I

    .line 67
    .line 68
    neg-int v1, v1

    .line 69
    invoke-virtual {p1, p2, v1}, Landroid/graphics/Rect;->offset(II)V

    .line 70
    .line 71
    .line 72
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 73
    .line 74
    iget p2, p1, Landroid/graphics/Rect;->left:I

    .line 75
    .line 76
    const/4 v1, 0x0

    .line 77
    if-gez p2, :cond_0

    .line 78
    .line 79
    iget p2, p1, Landroid/graphics/Rect;->top:I

    .line 80
    .line 81
    invoke-virtual {p1, v1, p2}, Landroid/graphics/Rect;->offsetTo(II)V

    .line 82
    .line 83
    .line 84
    goto :goto_0

    .line 85
    :cond_0
    iget p1, p1, Landroid/graphics/Rect;->right:I

    .line 86
    .line 87
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mWindowBounds:Landroid/graphics/Rect;

    .line 88
    .line 89
    invoke-virtual {p2}, Landroid/graphics/Rect;->width()I

    .line 90
    .line 91
    .line 92
    move-result p2

    .line 93
    if-le p1, p2, :cond_1

    .line 94
    .line 95
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 96
    .line 97
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mWindowBounds:Landroid/graphics/Rect;

    .line 98
    .line 99
    invoke-virtual {p2}, Landroid/graphics/Rect;->width()I

    .line 100
    .line 101
    .line 102
    move-result p2

    .line 103
    iget-object v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 104
    .line 105
    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    .line 106
    .line 107
    .line 108
    move-result v2

    .line 109
    sub-int/2addr p2, v2

    .line 110
    iget-object v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 111
    .line 112
    iget v2, v2, Landroid/graphics/Rect;->top:I

    .line 113
    .line 114
    invoke-virtual {p1, p2, v2}, Landroid/graphics/Rect;->offsetTo(II)V

    .line 115
    .line 116
    .line 117
    :cond_1
    :goto_0
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 118
    .line 119
    iget p2, p1, Landroid/graphics/Rect;->top:I

    .line 120
    .line 121
    if-gez p2, :cond_2

    .line 122
    .line 123
    iget p2, p1, Landroid/graphics/Rect;->left:I

    .line 124
    .line 125
    invoke-virtual {p1, p2, v1}, Landroid/graphics/Rect;->offsetTo(II)V

    .line 126
    .line 127
    .line 128
    goto :goto_1

    .line 129
    :cond_2
    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    .line 130
    .line 131
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mWindowBounds:Landroid/graphics/Rect;

    .line 132
    .line 133
    invoke-virtual {p2}, Landroid/graphics/Rect;->height()I

    .line 134
    .line 135
    .line 136
    move-result p2

    .line 137
    if-le p1, p2, :cond_3

    .line 138
    .line 139
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 140
    .line 141
    iget p2, p1, Landroid/graphics/Rect;->left:I

    .line 142
    .line 143
    iget-object v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mWindowBounds:Landroid/graphics/Rect;

    .line 144
    .line 145
    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    .line 146
    .line 147
    .line 148
    move-result v2

    .line 149
    iget-object v3, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 150
    .line 151
    invoke-virtual {v3}, Landroid/graphics/Rect;->height()I

    .line 152
    .line 153
    .line 154
    move-result v3

    .line 155
    sub-int/2addr v2, v3

    .line 156
    invoke-virtual {p1, p2, v2}, Landroid/graphics/Rect;->offsetTo(II)V

    .line 157
    .line 158
    .line 159
    :cond_3
    :goto_1
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 160
    .line 161
    invoke-virtual {p1, v0}, Landroid/graphics/Rect;->equals(Ljava/lang/Object;)Z

    .line 162
    .line 163
    .line 164
    move-result p1

    .line 165
    xor-int/lit8 p1, p1, 0x1

    .line 166
    .line 167
    if-eqz p1, :cond_4

    .line 168
    .line 169
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mTmpRect:Landroid/graphics/Rect;

    .line 170
    .line 171
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMagnificationFrame:Landroid/graphics/Rect;

    .line 172
    .line 173
    invoke-virtual {p2}, Landroid/graphics/Rect;->width()I

    .line 174
    .line 175
    .line 176
    move-result p2

    .line 177
    iget-object v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMagnificationFrame:Landroid/graphics/Rect;

    .line 178
    .line 179
    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    .line 180
    .line 181
    .line 182
    move-result v0

    .line 183
    invoke-virtual {p1, v1, v1, p2, v0}, Landroid/graphics/Rect;->set(IIII)V

    .line 184
    .line 185
    .line 186
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mTransaction:Landroid/view/SurfaceControl$Transaction;

    .line 187
    .line 188
    iget-object p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mMirrorSurface:Landroid/view/SurfaceControl;

    .line 189
    .line 190
    iget-object v0, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 191
    .line 192
    iget-object v2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mTmpRect:Landroid/graphics/Rect;

    .line 193
    .line 194
    invoke-virtual {p1, p2, v0, v2, v1}, Landroid/view/SurfaceControl$Transaction;->setGeometry(Landroid/view/SurfaceControl;Landroid/graphics/Rect;Landroid/graphics/Rect;I)Landroid/view/SurfaceControl$Transaction;

    .line 195
    .line 196
    .line 197
    move-result-object p1

    .line 198
    invoke-virtual {p1}, Landroid/view/SurfaceControl$Transaction;->apply()V

    .line 199
    .line 200
    .line 201
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mAnimationController:Lcom/android/systemui/accessibility/WindowMagnificationAnimationController;

    .line 202
    .line 203
    iget-object p1, p1, Lcom/android/systemui/accessibility/WindowMagnificationAnimationController;->mValueAnimator:Landroid/animation/ValueAnimator;

    .line 204
    .line 205
    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->isRunning()Z

    .line 206
    .line 207
    .line 208
    move-result p1

    .line 209
    if-nez p1, :cond_4

    .line 210
    .line 211
    iget-object p1, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mWindowMagnifierCallback:Lcom/android/systemui/accessibility/WindowMagnifierCallback;

    .line 212
    .line 213
    iget p2, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mDisplayId:I

    .line 214
    .line 215
    iget-object p0, p0, Lcom/android/systemui/accessibility/WindowMagnificationController;->mSourceBounds:Landroid/graphics/Rect;

    .line 216
    .line 217
    check-cast p1, Lcom/android/systemui/accessibility/WindowMagnification$2;

    .line 218
    .line 219
    iget-object p1, p1, Lcom/android/systemui/accessibility/WindowMagnification$2;->this$0:Lcom/android/systemui/accessibility/WindowMagnification;

    .line 220
    .line 221
    iget-object p1, p1, Lcom/android/systemui/accessibility/WindowMagnification;->mWindowMagnificationConnectionImpl:Lcom/android/systemui/accessibility/WindowMagnificationConnectionImpl;

    .line 222
    .line 223
    if-eqz p1, :cond_4

    .line 224
    .line 225
    iget-object p1, p1, Lcom/android/systemui/accessibility/WindowMagnificationConnectionImpl;->mConnectionCallback:Landroid/view/accessibility/IWindowMagnificationConnectionCallback;

    .line 226
    .line 227
    if-eqz p1, :cond_4

    .line 228
    .line 229
    :try_start_0
    invoke-interface {p1, p2, p0}, Landroid/view/accessibility/IWindowMagnificationConnectionCallback;->onSourceBoundsChanged(ILandroid/graphics/Rect;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 230
    .line 231
    .line 232
    goto :goto_2

    .line 233
    :catch_0
    move-exception p0

    .line 234
    const-string p1, "WindowMagnificationConnectionImpl"

    .line 235
    .line 236
    const-string p2, "Failed to inform source bounds changed"

    .line 237
    .line 238
    invoke-static {p1, p2, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 239
    .line 240
    .line 241
    :cond_4
    :goto_2
    return-void
.end method

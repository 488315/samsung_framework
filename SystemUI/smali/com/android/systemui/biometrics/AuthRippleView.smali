.class public final Lcom/android/systemui/biometrics/AuthRippleView;
.super Landroid/view/View;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public drawDwell:Z

.field public drawRipple:Z

.field public final dwellExpandDuration:J

.field public dwellOrigin:Landroid/graphics/Point;

.field public final dwellPaint:Landroid/graphics/Paint;

.field public final dwellPulseDuration:J

.field public dwellPulseOutAnimator:Landroid/animation/Animator;

.field public dwellRadius:F

.field public final dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

.field public final fadeDuration:J

.field public fadeDwellAnimator:Landroid/animation/Animator;

.field public lockScreenColorVal:I

.field public origin:Landroid/graphics/Point;

.field public final retractDuration:J

.field public retractDwellAnimator:Landroid/animation/Animator;

.field public final retractInterpolator:Landroid/view/animation/PathInterpolator;

.field public final ripplePaint:Landroid/graphics/Paint;

.field public final rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

.field public unlockedRippleAnimator:Landroid/animation/Animator;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 7

    .line 1
    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 2
    .line 3
    .line 4
    new-instance p1, Landroid/view/animation/PathInterpolator;

    .line 5
    .line 6
    const p2, 0x3d4ccccd    # 0.05f

    .line 7
    .line 8
    .line 9
    const v0, 0x3f6e147b    # 0.93f

    .line 10
    .line 11
    .line 12
    const v1, 0x3dcccccd    # 0.1f

    .line 13
    .line 14
    .line 15
    const/high16 v2, 0x3f800000    # 1.0f

    .line 16
    .line 17
    invoke-direct {p1, p2, v0, v1, v2}, Landroid/view/animation/PathInterpolator;-><init>(FFFF)V

    .line 18
    .line 19
    .line 20
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractInterpolator:Landroid/view/animation/PathInterpolator;

    .line 21
    .line 22
    const-wide/16 p1, 0x64

    .line 23
    .line 24
    iput-wide p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellPulseDuration:J

    .line 25
    .line 26
    const-wide/16 p1, 0x76c

    .line 27
    .line 28
    iput-wide p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellExpandDuration:J

    .line 29
    .line 30
    const/4 p1, -0x1

    .line 31
    iput p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->lockScreenColorVal:I

    .line 32
    .line 33
    const-wide/16 v0, 0x53

    .line 34
    .line 35
    iput-wide v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->fadeDuration:J

    .line 36
    .line 37
    const-wide/16 v0, 0x190

    .line 38
    .line 39
    iput-wide v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDuration:J

    .line 40
    .line 41
    new-instance p2, Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 42
    .line 43
    invoke-direct {p2}, Lcom/android/systemui/biometrics/DwellRippleShader;-><init>()V

    .line 44
    .line 45
    .line 46
    iput-object p2, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 47
    .line 48
    new-instance v0, Landroid/graphics/Paint;

    .line 49
    .line 50
    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    .line 51
    .line 52
    .line 53
    iput-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellPaint:Landroid/graphics/Paint;

    .line 54
    .line 55
    new-instance v1, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 56
    .line 57
    const/4 v3, 0x0

    .line 58
    const/4 v4, 0x1

    .line 59
    invoke-direct {v1, v3, v4, v3}, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;-><init>(Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleShape;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 60
    .line 61
    .line 62
    iput-object v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 63
    .line 64
    new-instance v3, Landroid/graphics/Paint;

    .line 65
    .line 66
    invoke-direct {v3}, Landroid/graphics/Paint;-><init>()V

    .line 67
    .line 68
    .line 69
    iput-object v3, p0, Lcom/android/systemui/biometrics/AuthRippleView;->ripplePaint:Landroid/graphics/Paint;

    .line 70
    .line 71
    new-instance v4, Landroid/graphics/Point;

    .line 72
    .line 73
    invoke-direct {v4}, Landroid/graphics/Point;-><init>()V

    .line 74
    .line 75
    .line 76
    iput-object v4, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellOrigin:Landroid/graphics/Point;

    .line 77
    .line 78
    new-instance v4, Landroid/graphics/Point;

    .line 79
    .line 80
    invoke-direct {v4}, Landroid/graphics/Point;-><init>()V

    .line 81
    .line 82
    .line 83
    iput-object v4, p0, Lcom/android/systemui/biometrics/AuthRippleView;->origin:Landroid/graphics/Point;

    .line 84
    .line 85
    const/4 v4, 0x0

    .line 86
    invoke-virtual {v1, v4}, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->setRawProgress(F)V

    .line 87
    .line 88
    .line 89
    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    .line 90
    .line 91
    .line 92
    move-result-object v5

    .line 93
    invoke-virtual {v5}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 94
    .line 95
    .line 96
    move-result-object v5

    .line 97
    iget v5, v5, Landroid/util/DisplayMetrics;->density:F

    .line 98
    .line 99
    invoke-virtual {v1, v5}, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->setPixelDensity(F)V

    .line 100
    .line 101
    .line 102
    const-string v5, "in_sparkle_strength"

    .line 103
    .line 104
    const v6, 0x3e99999a    # 0.3f

    .line 105
    .line 106
    .line 107
    invoke-virtual {v1, v5, v6}, Landroid/graphics/RuntimeShader;->setFloatUniform(Ljava/lang/String;F)V

    .line 108
    .line 109
    .line 110
    iget-object v5, v1, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->baseRingFadeParams:Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;

    .line 111
    .line 112
    iput v4, v5, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeInStart:F

    .line 113
    .line 114
    const v6, 0x3e4ccccd    # 0.2f

    .line 115
    .line 116
    .line 117
    iput v6, v5, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeInEnd:F

    .line 118
    .line 119
    iput v6, v5, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeOutStart:F

    .line 120
    .line 121
    iput v2, v5, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeOutEnd:F

    .line 122
    .line 123
    iget-object v2, v1, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->centerFillFadeParams:Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;

    .line 124
    .line 125
    iput v4, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeInStart:F

    .line 126
    .line 127
    const v5, 0x3e19999a    # 0.15f

    .line 128
    .line 129
    .line 130
    iput v5, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeInEnd:F

    .line 131
    .line 132
    iput v5, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeOutStart:F

    .line 133
    .line 134
    const v5, 0x3f0f5c29    # 0.56f

    .line 135
    .line 136
    .line 137
    iput v5, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$FadeParams;->fadeOutEnd:F

    .line 138
    .line 139
    invoke-virtual {v3, v1}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    .line 140
    .line 141
    .line 142
    iput p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->lockScreenColorVal:I

    .line 143
    .line 144
    const/16 v2, 0x3e

    .line 145
    .line 146
    invoke-static {p1, v2}, Lcom/android/internal/graphics/ColorUtils;->setAlphaComponent(II)I

    .line 147
    .line 148
    .line 149
    move-result v2

    .line 150
    const-string v3, "in_color"

    .line 151
    .line 152
    invoke-virtual {v1, v3, v2}, Landroid/graphics/RuntimeShader;->setColorUniform(Ljava/lang/String;I)V

    .line 153
    .line 154
    .line 155
    invoke-virtual {p2, p1}, Lcom/android/systemui/biometrics/DwellRippleShader;->setColor(I)V

    .line 156
    .line 157
    .line 158
    invoke-virtual {p2, v4}, Lcom/android/systemui/biometrics/DwellRippleShader;->setProgress(F)V

    .line 159
    .line 160
    .line 161
    const-string p1, "in_distortion_strength"

    .line 162
    .line 163
    const v1, 0x3ecccccd    # 0.4f

    .line 164
    .line 165
    .line 166
    invoke-virtual {p2, p1, v1}, Landroid/graphics/RuntimeShader;->setFloatUniform(Ljava/lang/String;F)V

    .line 167
    .line 168
    .line 169
    invoke-virtual {v0, p2}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    .line 170
    .line 171
    .line 172
    const/16 p1, 0x8

    .line 173
    .line 174
    invoke-virtual {p0, p1}, Landroid/view/View;->setVisibility(I)V

    .line 175
    .line 176
    .line 177
    return-void
.end method


# virtual methods
.method public final fadeDwellRipple()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->fadeDwellAnimator:Landroid/animation/Animator;

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    const/4 v2, 0x0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 8
    .line 9
    .line 10
    move-result v0

    .line 11
    if-ne v0, v1, :cond_0

    .line 12
    .line 13
    move v0, v1

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move v0, v2

    .line 16
    :goto_0
    if-eqz v0, :cond_1

    .line 17
    .line 18
    return-void

    .line 19
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellPulseOutAnimator:Landroid/animation/Animator;

    .line 20
    .line 21
    if-eqz v0, :cond_2

    .line 22
    .line 23
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 24
    .line 25
    .line 26
    move-result v0

    .line 27
    if-ne v0, v1, :cond_2

    .line 28
    .line 29
    move v0, v1

    .line 30
    goto :goto_1

    .line 31
    :cond_2
    move v0, v2

    .line 32
    :goto_1
    if-nez v0, :cond_4

    .line 33
    .line 34
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDwellAnimator:Landroid/animation/Animator;

    .line 35
    .line 36
    if-eqz v0, :cond_3

    .line 37
    .line 38
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 39
    .line 40
    .line 41
    move-result v0

    .line 42
    if-ne v0, v1, :cond_3

    .line 43
    .line 44
    goto :goto_2

    .line 45
    :cond_3
    move v1, v2

    .line 46
    :goto_2
    if-eqz v1, :cond_5

    .line 47
    .line 48
    :cond_4
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 49
    .line 50
    iget v0, v0, Lcom/android/systemui/biometrics/DwellRippleShader;->color:I

    .line 51
    .line 52
    invoke-static {v0}, Landroid/graphics/Color;->alpha(I)I

    .line 53
    .line 54
    .line 55
    move-result v0

    .line 56
    filled-new-array {v0, v2}, [I

    .line 57
    .line 58
    .line 59
    move-result-object v0

    .line 60
    invoke-static {v0}, Landroid/animation/ValueAnimator;->ofInt([I)Landroid/animation/ValueAnimator;

    .line 61
    .line 62
    .line 63
    move-result-object v0

    .line 64
    sget-object v1, Lcom/android/app/animation/Interpolators;->LINEAR:Landroid/view/animation/Interpolator;

    .line 65
    .line 66
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 67
    .line 68
    .line 69
    iget-wide v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->fadeDuration:J

    .line 70
    .line 71
    invoke-virtual {v0, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 72
    .line 73
    .line 74
    new-instance v1, Lcom/android/systemui/biometrics/AuthRippleView$fadeDwellRipple$1$1;

    .line 75
    .line 76
    invoke-direct {v1, p0}, Lcom/android/systemui/biometrics/AuthRippleView$fadeDwellRipple$1$1;-><init>(Lcom/android/systemui/biometrics/AuthRippleView;)V

    .line 77
    .line 78
    .line 79
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 80
    .line 81
    .line 82
    new-instance v1, Lcom/android/systemui/biometrics/AuthRippleView$fadeDwellRipple$1$2;

    .line 83
    .line 84
    invoke-direct {v1, p0}, Lcom/android/systemui/biometrics/AuthRippleView$fadeDwellRipple$1$2;-><init>(Lcom/android/systemui/biometrics/AuthRippleView;)V

    .line 85
    .line 86
    .line 87
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 88
    .line 89
    .line 90
    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->start()V

    .line 91
    .line 92
    .line 93
    iput-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->fadeDwellAnimator:Landroid/animation/Animator;

    .line 94
    .line 95
    :cond_5
    return-void
.end method

.method public final onDraw(Landroid/graphics/Canvas;)V
    .locals 4

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->drawDwell:Z

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    const/4 v0, 0x1

    .line 6
    int-to-float v0, v0

    .line 7
    iget-object v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 8
    .line 9
    iget v1, v1, Lcom/android/systemui/biometrics/DwellRippleShader;->progress:F

    .line 10
    .line 11
    sub-float v2, v0, v1

    .line 12
    .line 13
    sub-float v3, v0, v1

    .line 14
    .line 15
    mul-float/2addr v3, v2

    .line 16
    sub-float v1, v0, v1

    .line 17
    .line 18
    mul-float/2addr v1, v3

    .line 19
    sub-float/2addr v0, v1

    .line 20
    iget v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellRadius:F

    .line 21
    .line 22
    mul-float/2addr v0, v1

    .line 23
    const/high16 v1, 0x40000000    # 2.0f

    .line 24
    .line 25
    mul-float/2addr v0, v1

    .line 26
    if-eqz p1, :cond_0

    .line 27
    .line 28
    iget-object v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellOrigin:Landroid/graphics/Point;

    .line 29
    .line 30
    iget v2, v1, Landroid/graphics/Point;->x:I

    .line 31
    .line 32
    int-to-float v2, v2

    .line 33
    iget v1, v1, Landroid/graphics/Point;->y:I

    .line 34
    .line 35
    int-to-float v1, v1

    .line 36
    iget-object v3, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellPaint:Landroid/graphics/Paint;

    .line 37
    .line 38
    invoke-virtual {p1, v2, v1, v0, v3}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 39
    .line 40
    .line 41
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->drawRipple:Z

    .line 42
    .line 43
    if-eqz v0, :cond_1

    .line 44
    .line 45
    if-eqz p1, :cond_1

    .line 46
    .line 47
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->origin:Landroid/graphics/Point;

    .line 48
    .line 49
    iget v1, v0, Landroid/graphics/Point;->x:I

    .line 50
    .line 51
    int-to-float v1, v1

    .line 52
    iget v0, v0, Landroid/graphics/Point;->y:I

    .line 53
    .line 54
    int-to-float v0, v0

    .line 55
    iget-object v2, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 56
    .line 57
    iget-object v2, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->rippleSize:Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;

    .line 58
    .line 59
    iget v2, v2, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;->currentWidth:F

    .line 60
    .line 61
    iget-object p0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->ripplePaint:Landroid/graphics/Paint;

    .line 62
    .line 63
    invoke-virtual {p1, v1, v0, v2, p0}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 64
    .line 65
    .line 66
    :cond_1
    return-void
.end method

.method public final retractDwellRipple()V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDwellAnimator:Landroid/animation/Animator;

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    const/4 v2, 0x0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 8
    .line 9
    .line 10
    move-result v0

    .line 11
    if-ne v0, v1, :cond_0

    .line 12
    .line 13
    move v0, v1

    .line 14
    goto :goto_0

    .line 15
    :cond_0
    move v0, v2

    .line 16
    :goto_0
    if-nez v0, :cond_4

    .line 17
    .line 18
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->fadeDwellAnimator:Landroid/animation/Animator;

    .line 19
    .line 20
    if-eqz v0, :cond_1

    .line 21
    .line 22
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 23
    .line 24
    .line 25
    move-result v0

    .line 26
    if-ne v0, v1, :cond_1

    .line 27
    .line 28
    move v0, v1

    .line 29
    goto :goto_1

    .line 30
    :cond_1
    move v0, v2

    .line 31
    :goto_1
    if-eqz v0, :cond_2

    .line 32
    .line 33
    goto :goto_3

    .line 34
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellPulseOutAnimator:Landroid/animation/Animator;

    .line 35
    .line 36
    if-eqz v0, :cond_3

    .line 37
    .line 38
    invoke-virtual {v0}, Landroid/animation/Animator;->isRunning()Z

    .line 39
    .line 40
    .line 41
    move-result v0

    .line 42
    if-ne v0, v1, :cond_3

    .line 43
    .line 44
    move v0, v1

    .line 45
    goto :goto_2

    .line 46
    :cond_3
    move v0, v2

    .line 47
    :goto_2
    if-eqz v0, :cond_4

    .line 48
    .line 49
    const/4 v0, 0x2

    .line 50
    new-array v0, v0, [F

    .line 51
    .line 52
    iget-object v3, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 53
    .line 54
    iget v3, v3, Lcom/android/systemui/biometrics/DwellRippleShader;->progress:F

    .line 55
    .line 56
    aput v3, v0, v2

    .line 57
    .line 58
    const/4 v3, 0x0

    .line 59
    aput v3, v0, v1

    .line 60
    .line 61
    invoke-static {v0}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    .line 62
    .line 63
    .line 64
    move-result-object v0

    .line 65
    iget-object v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractInterpolator:Landroid/view/animation/PathInterpolator;

    .line 66
    .line 67
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 68
    .line 69
    .line 70
    iget-wide v3, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDuration:J

    .line 71
    .line 72
    invoke-virtual {v0, v3, v4}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 73
    .line 74
    .line 75
    new-instance v1, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$retractDwellRippleAnimator$1$1;

    .line 76
    .line 77
    invoke-direct {v1, p0}, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$retractDwellRippleAnimator$1$1;-><init>(Lcom/android/systemui/biometrics/AuthRippleView;)V

    .line 78
    .line 79
    .line 80
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 81
    .line 82
    .line 83
    const/16 v1, 0xff

    .line 84
    .line 85
    filled-new-array {v1, v2}, [I

    .line 86
    .line 87
    .line 88
    move-result-object v1

    .line 89
    invoke-static {v1}, Landroid/animation/ValueAnimator;->ofInt([I)Landroid/animation/ValueAnimator;

    .line 90
    .line 91
    .line 92
    move-result-object v1

    .line 93
    sget-object v2, Lcom/android/app/animation/Interpolators;->LINEAR:Landroid/view/animation/Interpolator;

    .line 94
    .line 95
    invoke-virtual {v1, v2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 96
    .line 97
    .line 98
    iget-wide v2, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDuration:J

    .line 99
    .line 100
    invoke-virtual {v1, v2, v3}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 101
    .line 102
    .line 103
    new-instance v2, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$retractAlphaAnimator$1$1;

    .line 104
    .line 105
    invoke-direct {v2, p0}, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$retractAlphaAnimator$1$1;-><init>(Lcom/android/systemui/biometrics/AuthRippleView;)V

    .line 106
    .line 107
    .line 108
    invoke-virtual {v1, v2}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 109
    .line 110
    .line 111
    new-instance v2, Landroid/animation/AnimatorSet;

    .line 112
    .line 113
    invoke-direct {v2}, Landroid/animation/AnimatorSet;-><init>()V

    .line 114
    .line 115
    .line 116
    filled-new-array {v0, v1}, [Landroid/animation/Animator;

    .line 117
    .line 118
    .line 119
    move-result-object v0

    .line 120
    invoke-virtual {v2, v0}, Landroid/animation/AnimatorSet;->playTogether([Landroid/animation/Animator;)V

    .line 121
    .line 122
    .line 123
    new-instance v0, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$1$1;

    .line 124
    .line 125
    invoke-direct {v0, p0}, Lcom/android/systemui/biometrics/AuthRippleView$retractDwellRipple$1$1;-><init>(Lcom/android/systemui/biometrics/AuthRippleView;)V

    .line 126
    .line 127
    .line 128
    invoke-virtual {v2, v0}, Landroid/animation/AnimatorSet;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 129
    .line 130
    .line 131
    invoke-virtual {v2}, Landroid/animation/AnimatorSet;->start()V

    .line 132
    .line 133
    .line 134
    iput-object v2, p0, Lcom/android/systemui/biometrics/AuthRippleView;->retractDwellAnimator:Landroid/animation/Animator;

    .line 135
    .line 136
    :cond_4
    :goto_3
    return-void
.end method

.method public final setFingerprintSensorLocation(Landroid/graphics/Point;F)V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 2
    .line 3
    iget v1, p1, Landroid/graphics/Point;->x:I

    .line 4
    .line 5
    int-to-float v1, v1

    .line 6
    iget v2, p1, Landroid/graphics/Point;->y:I

    .line 7
    .line 8
    int-to-float v2, v2

    .line 9
    const-string v3, "in_center"

    .line 10
    .line 11
    invoke-virtual {v0, v3, v1, v2}, Landroid/graphics/RuntimeShader;->setFloatUniform(Ljava/lang/String;FF)V

    .line 12
    .line 13
    .line 14
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->origin:Landroid/graphics/Point;

    .line 15
    .line 16
    iget v0, p1, Landroid/graphics/Point;->x:I

    .line 17
    .line 18
    iget v1, p1, Landroid/graphics/Point;->y:I

    .line 19
    .line 20
    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    .line 21
    .line 22
    .line 23
    move-result v2

    .line 24
    iget v3, p1, Landroid/graphics/Point;->x:I

    .line 25
    .line 26
    sub-int/2addr v2, v3

    .line 27
    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    .line 28
    .line 29
    .line 30
    move-result v3

    .line 31
    iget v4, p1, Landroid/graphics/Point;->y:I

    .line 32
    .line 33
    sub-int/2addr v3, v4

    .line 34
    filled-new-array {v1, v2, v3}, [I

    .line 35
    .line 36
    .line 37
    move-result-object v1

    .line 38
    const/4 v2, 0x0

    .line 39
    :goto_0
    const/4 v3, 0x3

    .line 40
    if-ge v2, v3, :cond_0

    .line 41
    .line 42
    aget v3, v1, v2

    .line 43
    .line 44
    invoke-static {v0, v3}, Ljava/lang/Math;->max(II)I

    .line 45
    .line 46
    .line 47
    move-result v0

    .line 48
    add-int/lit8 v2, v2, 0x1

    .line 49
    .line 50
    goto :goto_0

    .line 51
    :cond_0
    int-to-float v0, v0

    .line 52
    const v1, 0x3f666666    # 0.9f

    .line 53
    .line 54
    .line 55
    mul-float/2addr v0, v1

    .line 56
    iget-object v1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 57
    .line 58
    iget-object v1, v1, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->rippleSize:Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;

    .line 59
    .line 60
    const/high16 v2, 0x40000000    # 2.0f

    .line 61
    .line 62
    mul-float/2addr v0, v2

    .line 63
    invoke-virtual {v1, v0, v0}, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;->setMaxSize(FF)V

    .line 64
    .line 65
    .line 66
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 67
    .line 68
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 69
    .line 70
    .line 71
    iget v1, p1, Landroid/graphics/Point;->x:I

    .line 72
    .line 73
    int-to-float v1, v1

    .line 74
    iget v2, p1, Landroid/graphics/Point;->y:I

    .line 75
    .line 76
    int-to-float v2, v2

    .line 77
    const-string v3, "in_origin"

    .line 78
    .line 79
    invoke-virtual {v0, v3, v1, v2}, Landroid/graphics/RuntimeShader;->setFloatUniform(Ljava/lang/String;FF)V

    .line 80
    .line 81
    .line 82
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellOrigin:Landroid/graphics/Point;

    .line 83
    .line 84
    const/high16 p1, 0x3fc00000    # 1.5f

    .line 85
    .line 86
    mul-float/2addr p2, p1

    .line 87
    iget-object p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellShader:Lcom/android/systemui/biometrics/DwellRippleShader;

    .line 88
    .line 89
    iput p2, p1, Lcom/android/systemui/biometrics/DwellRippleShader;->maxRadius:F

    .line 90
    .line 91
    iput p2, p0, Lcom/android/systemui/biometrics/AuthRippleView;->dwellRadius:F

    .line 92
    .line 93
    return-void
.end method

.method public final setSensorLocation(Landroid/graphics/Point;)V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 2
    .line 3
    iget v1, p1, Landroid/graphics/Point;->x:I

    .line 4
    .line 5
    int-to-float v1, v1

    .line 6
    iget v2, p1, Landroid/graphics/Point;->y:I

    .line 7
    .line 8
    int-to-float v2, v2

    .line 9
    const-string v3, "in_center"

    .line 10
    .line 11
    invoke-virtual {v0, v3, v1, v2}, Landroid/graphics/RuntimeShader;->setFloatUniform(Ljava/lang/String;FF)V

    .line 12
    .line 13
    .line 14
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthRippleView;->origin:Landroid/graphics/Point;

    .line 15
    .line 16
    iget v0, p1, Landroid/graphics/Point;->x:I

    .line 17
    .line 18
    iget v1, p1, Landroid/graphics/Point;->y:I

    .line 19
    .line 20
    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    .line 21
    .line 22
    .line 23
    move-result v2

    .line 24
    iget v3, p1, Landroid/graphics/Point;->x:I

    .line 25
    .line 26
    sub-int/2addr v2, v3

    .line 27
    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    .line 28
    .line 29
    .line 30
    move-result v3

    .line 31
    iget p1, p1, Landroid/graphics/Point;->y:I

    .line 32
    .line 33
    sub-int/2addr v3, p1

    .line 34
    filled-new-array {v1, v2, v3}, [I

    .line 35
    .line 36
    .line 37
    move-result-object p1

    .line 38
    const/4 v1, 0x0

    .line 39
    :goto_0
    const/4 v2, 0x3

    .line 40
    if-ge v1, v2, :cond_0

    .line 41
    .line 42
    aget v2, p1, v1

    .line 43
    .line 44
    invoke-static {v0, v2}, Ljava/lang/Math;->max(II)I

    .line 45
    .line 46
    .line 47
    move-result v0

    .line 48
    add-int/lit8 v1, v1, 0x1

    .line 49
    .line 50
    goto :goto_0

    .line 51
    :cond_0
    int-to-float p1, v0

    .line 52
    const v0, 0x3f666666    # 0.9f

    .line 53
    .line 54
    .line 55
    mul-float/2addr p1, v0

    .line 56
    iget-object p0, p0, Lcom/android/systemui/biometrics/AuthRippleView;->rippleShader:Lcom/android/systemui/surfaceeffects/ripple/RippleShader;

    .line 57
    .line 58
    iget-object p0, p0, Lcom/android/systemui/surfaceeffects/ripple/RippleShader;->rippleSize:Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;

    .line 59
    .line 60
    const/high16 v0, 0x40000000    # 2.0f

    .line 61
    .line 62
    mul-float/2addr p1, v0

    .line 63
    invoke-virtual {p0, p1, p1}, Lcom/android/systemui/surfaceeffects/ripple/RippleShader$RippleSize;->setMaxSize(FF)V

    .line 64
    .line 65
    .line 66
    return-void
.end method

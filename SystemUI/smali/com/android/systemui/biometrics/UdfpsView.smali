.class public final Lcom/android/systemui/biometrics/UdfpsView;
.super Landroid/widget/FrameLayout;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/doze/DozeReceiver;


# instance fields
.field public animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

.field public debugMessage:Ljava/lang/String;

.field public final debugTextPaint:Landroid/graphics/Paint;

.field public isDisplayConfigured:Z

.field public mUdfpsDisplayMode:Lcom/android/systemui/biometrics/UdfpsDisplayModeProvider;

.field public overlayParams:Lcom/android/settingslib/udfps/UdfpsOverlayParams;

.field public sensorRect:Landroid/graphics/Rect;

.field public final sensorTouchAreaCoefficient:F

.field public useExpandedOverlay:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 10

    .line 1
    invoke-direct {p0, p1, p2}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 2
    .line 3
    .line 4
    new-instance v0, Landroid/graphics/Rect;

    .line 5
    .line 6
    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 7
    .line 8
    .line 9
    iput-object v0, p0, Lcom/android/systemui/biometrics/UdfpsView;->sensorRect:Landroid/graphics/Rect;

    .line 10
    .line 11
    new-instance v0, Landroid/graphics/Paint;

    .line 12
    .line 13
    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    .line 14
    .line 15
    .line 16
    const/4 v1, 0x1

    .line 17
    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 18
    .line 19
    .line 20
    const v2, -0xffff01

    .line 21
    .line 22
    .line 23
    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setColor(I)V

    .line 24
    .line 25
    .line 26
    const/high16 v2, 0x42000000    # 32.0f

    .line 27
    .line 28
    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setTextSize(F)V

    .line 29
    .line 30
    .line 31
    iput-object v0, p0, Lcom/android/systemui/biometrics/UdfpsView;->debugTextPaint:Landroid/graphics/Paint;

    .line 32
    .line 33
    invoke-virtual {p1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 34
    .line 35
    .line 36
    move-result-object p1

    .line 37
    sget-object v0, Lcom/android/systemui/R$styleable;->UdfpsView:[I

    .line 38
    .line 39
    const/4 v2, 0x0

    .line 40
    invoke-virtual {p1, p2, v0, v2, v2}, Landroid/content/res/Resources$Theme;->obtainStyledAttributes(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;

    .line 41
    .line 42
    .line 43
    move-result-object p1

    .line 44
    :try_start_0
    invoke-virtual {p1, v1}, Landroid/content/res/TypedArray;->hasValue(I)Z

    .line 45
    .line 46
    .line 47
    move-result p2

    .line 48
    if-eqz p2, :cond_0

    .line 49
    .line 50
    const/4 p2, 0x0

    .line 51
    invoke-virtual {p1, v1, p2}, Landroid/content/res/TypedArray;->getFloat(IF)F

    .line 52
    .line 53
    .line 54
    move-result p2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 55
    const/4 v0, 0x0

    .line 56
    invoke-static {p1, v0}, Lkotlin/jdk7/AutoCloseableKt;->closeFinally(Ljava/lang/AutoCloseable;Ljava/lang/Throwable;)V

    .line 57
    .line 58
    .line 59
    iput p2, p0, Lcom/android/systemui/biometrics/UdfpsView;->sensorTouchAreaCoefficient:F

    .line 60
    .line 61
    new-instance p1, Lcom/android/settingslib/udfps/UdfpsOverlayParams;

    .line 62
    .line 63
    const/4 v2, 0x0

    .line 64
    const/4 v3, 0x0

    .line 65
    const/4 v4, 0x0

    .line 66
    const/4 v5, 0x0

    .line 67
    const/4 v6, 0x0

    .line 68
    const/4 v7, 0x0

    .line 69
    const/16 v8, 0x3f

    .line 70
    .line 71
    const/4 v9, 0x0

    .line 72
    move-object v1, p1

    .line 73
    invoke-direct/range {v1 .. v9}, Lcom/android/settingslib/udfps/UdfpsOverlayParams;-><init>(Landroid/graphics/Rect;Landroid/graphics/Rect;IIFIILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 74
    .line 75
    .line 76
    iput-object p1, p0, Lcom/android/systemui/biometrics/UdfpsView;->overlayParams:Lcom/android/settingslib/udfps/UdfpsOverlayParams;

    .line 77
    .line 78
    return-void

    .line 79
    :cond_0
    :try_start_1
    const-string p0, "UdfpsView must contain sensorTouchAreaCoefficient"

    .line 80
    .line 81
    new-instance p2, Ljava/lang/IllegalArgumentException;

    .line 82
    .line 83
    invoke-virtual {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    .line 84
    .line 85
    .line 86
    move-result-object p0

    .line 87
    invoke-direct {p2, p0}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    .line 88
    .line 89
    .line 90
    throw p2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 91
    :catchall_0
    move-exception p0

    .line 92
    :try_start_2
    throw p0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 93
    :catchall_1
    move-exception p2

    .line 94
    invoke-static {p1, p0}, Lkotlin/jdk7/AutoCloseableKt;->closeFinally(Ljava/lang/AutoCloseable;Ljava/lang/Throwable;)V

    .line 95
    .line 96
    .line 97
    throw p2
.end method


# virtual methods
.method public final dozeTimeTick()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/biometrics/UdfpsView;->animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

    .line 2
    .line 3
    if-eqz p0, :cond_0

    .line 4
    .line 5
    invoke-virtual {p0}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    invoke-virtual {v0}, Lcom/android/systemui/biometrics/UdfpsAnimationView;->dozeTimeTick()Z

    .line 10
    .line 11
    .line 12
    move-result v0

    .line 13
    if-eqz v0, :cond_0

    .line 14
    .line 15
    invoke-virtual {p0}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 16
    .line 17
    .line 18
    move-result-object p0

    .line 19
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->postInvalidate()V

    .line 20
    .line 21
    .line 22
    :cond_0
    return-void
.end method

.method public final onAttachedToWindow()V
    .locals 0

    .line 1
    invoke-super {p0}, Landroid/widget/FrameLayout;->onAttachedToWindow()V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public final onDetachedFromWindow()V
    .locals 0

    .line 1
    invoke-super {p0}, Landroid/widget/FrameLayout;->onDetachedFromWindow()V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public final onDraw(Landroid/graphics/Canvas;)V
    .locals 3

    .line 1
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->onDraw(Landroid/graphics/Canvas;)V

    .line 2
    .line 3
    .line 4
    iget-boolean v0, p0, Lcom/android/systemui/biometrics/UdfpsView;->isDisplayConfigured:Z

    .line 5
    .line 6
    if-nez v0, :cond_2

    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/systemui/biometrics/UdfpsView;->debugMessage:Ljava/lang/String;

    .line 9
    .line 10
    if-eqz v0, :cond_1

    .line 11
    .line 12
    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    .line 13
    .line 14
    .line 15
    move-result v0

    .line 16
    if-nez v0, :cond_0

    .line 17
    .line 18
    goto :goto_0

    .line 19
    :cond_0
    const/4 v0, 0x0

    .line 20
    goto :goto_1

    .line 21
    :cond_1
    :goto_0
    const/4 v0, 0x1

    .line 22
    :goto_1
    if-nez v0, :cond_2

    .line 23
    .line 24
    iget-object v0, p0, Lcom/android/systemui/biometrics/UdfpsView;->debugMessage:Ljava/lang/String;

    .line 25
    .line 26
    invoke-static {v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 27
    .line 28
    .line 29
    iget-object p0, p0, Lcom/android/systemui/biometrics/UdfpsView;->debugTextPaint:Landroid/graphics/Paint;

    .line 30
    .line 31
    const/4 v1, 0x0

    .line 32
    const/high16 v2, 0x43200000    # 160.0f

    .line 33
    .line 34
    invoke-virtual {p1, v0, v1, v2, p0}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 35
    .line 36
    .line 37
    :cond_2
    return-void
.end method

.method public final onInterceptTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/biometrics/UdfpsView;->animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

    .line 2
    .line 3
    if-eqz p0, :cond_1

    .line 4
    .line 5
    invoke-static {p0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 6
    .line 7
    .line 8
    invoke-virtual {p0}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->shouldPauseAuth()Z

    .line 9
    .line 10
    .line 11
    move-result p0

    .line 12
    if-nez p0, :cond_0

    .line 13
    .line 14
    goto :goto_0

    .line 15
    :cond_0
    const/4 p0, 0x0

    .line 16
    goto :goto_1

    .line 17
    :cond_1
    :goto_0
    const/4 p0, 0x1

    .line 18
    :goto_1
    return p0
.end method

.method public final onLayout(ZIIII)V
    .locals 0

    .line 1
    invoke-super/range {p0 .. p5}, Landroid/widget/FrameLayout;->onLayout(ZIIII)V

    .line 2
    .line 3
    .line 4
    iget-object p1, p0, Lcom/android/systemui/biometrics/UdfpsView;->animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

    .line 5
    .line 6
    iget-boolean p2, p0, Lcom/android/systemui/biometrics/UdfpsView;->useExpandedOverlay:Z

    .line 7
    .line 8
    if-eqz p2, :cond_0

    .line 9
    .line 10
    if-eqz p1, :cond_1

    .line 11
    .line 12
    new-instance p2, Landroid/graphics/RectF;

    .line 13
    .line 14
    iget-object p0, p0, Lcom/android/systemui/biometrics/UdfpsView;->sensorRect:Landroid/graphics/Rect;

    .line 15
    .line 16
    invoke-direct {p2, p0}, Landroid/graphics/RectF;-><init>(Landroid/graphics/Rect;)V

    .line 17
    .line 18
    .line 19
    invoke-virtual {p1}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 20
    .line 21
    .line 22
    move-result-object p0

    .line 23
    invoke-virtual {p0, p2}, Lcom/android/systemui/biometrics/UdfpsAnimationView;->onSensorRectUpdated(Landroid/graphics/RectF;)V

    .line 24
    .line 25
    .line 26
    goto :goto_0

    .line 27
    :cond_0
    iget-object p1, p0, Lcom/android/systemui/biometrics/UdfpsView;->sensorRect:Landroid/graphics/Rect;

    .line 28
    .line 29
    iget-object p2, p0, Lcom/android/systemui/biometrics/UdfpsView;->overlayParams:Lcom/android/settingslib/udfps/UdfpsOverlayParams;

    .line 30
    .line 31
    iget-object p2, p2, Lcom/android/settingslib/udfps/UdfpsOverlayParams;->sensorBounds:Landroid/graphics/Rect;

    .line 32
    .line 33
    invoke-virtual {p2}, Landroid/graphics/Rect;->width()I

    .line 34
    .line 35
    .line 36
    move-result p2

    .line 37
    const/4 p3, 0x0

    .line 38
    add-int/2addr p2, p3

    .line 39
    iget-object p4, p0, Lcom/android/systemui/biometrics/UdfpsView;->overlayParams:Lcom/android/settingslib/udfps/UdfpsOverlayParams;

    .line 40
    .line 41
    iget-object p4, p4, Lcom/android/settingslib/udfps/UdfpsOverlayParams;->sensorBounds:Landroid/graphics/Rect;

    .line 42
    .line 43
    invoke-virtual {p4}, Landroid/graphics/Rect;->height()I

    .line 44
    .line 45
    .line 46
    move-result p4

    .line 47
    add-int/2addr p4, p3

    .line 48
    invoke-virtual {p1, p3, p3, p2, p4}, Landroid/graphics/Rect;->set(IIII)V

    .line 49
    .line 50
    .line 51
    iget-object p1, p0, Lcom/android/systemui/biometrics/UdfpsView;->animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

    .line 52
    .line 53
    if-eqz p1, :cond_1

    .line 54
    .line 55
    new-instance p2, Landroid/graphics/RectF;

    .line 56
    .line 57
    iget-object p0, p0, Lcom/android/systemui/biometrics/UdfpsView;->sensorRect:Landroid/graphics/Rect;

    .line 58
    .line 59
    invoke-direct {p2, p0}, Landroid/graphics/RectF;-><init>(Landroid/graphics/Rect;)V

    .line 60
    .line 61
    .line 62
    invoke-virtual {p1}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 63
    .line 64
    .line 65
    move-result-object p0

    .line 66
    invoke-virtual {p0, p2}, Lcom/android/systemui/biometrics/UdfpsAnimationView;->onSensorRectUpdated(Landroid/graphics/RectF;)V

    .line 67
    .line 68
    .line 69
    :cond_1
    :goto_0
    return-void
.end method

.method public final unconfigureDisplay$1()V
    .locals 18

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    iput-boolean v1, v0, Lcom/android/systemui/biometrics/UdfpsView;->isDisplayConfigured:Z

    .line 5
    .line 6
    iget-object v1, v0, Lcom/android/systemui/biometrics/UdfpsView;->animationViewController:Lcom/android/systemui/biometrics/UdfpsAnimationViewController;

    .line 7
    .line 8
    if-eqz v1, :cond_0

    .line 9
    .line 10
    invoke-virtual {v1}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 11
    .line 12
    .line 13
    move-result-object v2

    .line 14
    invoke-virtual {v2}, Lcom/android/systemui/biometrics/UdfpsAnimationView;->onDisplayUnconfigured()V

    .line 15
    .line 16
    .line 17
    invoke-virtual {v1}, Lcom/android/systemui/biometrics/UdfpsAnimationViewController;->getView()Lcom/android/systemui/biometrics/UdfpsAnimationView;

    .line 18
    .line 19
    .line 20
    move-result-object v1

    .line 21
    invoke-virtual {v1}, Landroid/widget/FrameLayout;->postInvalidate()V

    .line 22
    .line 23
    .line 24
    :cond_0
    iget-object v0, v0, Lcom/android/systemui/biometrics/UdfpsView;->mUdfpsDisplayMode:Lcom/android/systemui/biometrics/UdfpsDisplayModeProvider;

    .line 25
    .line 26
    if-eqz v0, :cond_2

    .line 27
    .line 28
    move-object v1, v0

    .line 29
    check-cast v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;

    .line 30
    .line 31
    iget-object v0, v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;->execution:Lcom/android/systemui/util/concurrency/Execution;

    .line 32
    .line 33
    check-cast v0, Lcom/android/systemui/util/concurrency/ExecutionImpl;

    .line 34
    .line 35
    iget-object v0, v0, Lcom/android/systemui/util/concurrency/ExecutionImpl;->mainLooper:Landroid/os/Looper;

    .line 36
    .line 37
    invoke-virtual {v0}, Landroid/os/Looper;->isCurrentThread()Z

    .line 38
    .line 39
    .line 40
    const-string v9, "disable"

    .line 41
    .line 42
    iget-object v10, v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;->logger:Lcom/android/systemui/biometrics/UdfpsLogger;

    .line 43
    .line 44
    invoke-virtual {v10}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 45
    .line 46
    .line 47
    sget-object v0, Lcom/android/systemui/log/LogLevel;->VERBOSE:Lcom/android/systemui/log/LogLevel;

    .line 48
    .line 49
    const-string v3, "UdfpsDisplayMode"

    .line 50
    .line 51
    iget-object v2, v10, Lcom/android/systemui/biometrics/UdfpsLogger;->logBuffer:Lcom/android/systemui/log/LogBuffer;

    .line 52
    .line 53
    const/4 v6, 0x0

    .line 54
    const/16 v7, 0x8

    .line 55
    .line 56
    const/4 v8, 0x0

    .line 57
    move-object v4, v0

    .line 58
    move-object v5, v9

    .line 59
    invoke-static/range {v2 .. v8}, Lcom/android/systemui/log/LogBuffer;->log$default(Lcom/android/systemui/log/LogBuffer;Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Ljava/lang/String;Ljava/lang/Throwable;ILjava/lang/Object;)V

    .line 60
    .line 61
    .line 62
    iget-object v2, v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;->currentRequest:Lcom/android/systemui/biometrics/Request;

    .line 63
    .line 64
    if-nez v2, :cond_1

    .line 65
    .line 66
    const-string v14, "disable | already disabled"

    .line 67
    .line 68
    sget-object v13, Lcom/android/systemui/log/LogLevel;->WARNING:Lcom/android/systemui/log/LogLevel;

    .line 69
    .line 70
    const-string v12, "UdfpsDisplayMode"

    .line 71
    .line 72
    iget-object v11, v10, Lcom/android/systemui/biometrics/UdfpsLogger;->logBuffer:Lcom/android/systemui/log/LogBuffer;

    .line 73
    .line 74
    const/4 v15, 0x0

    .line 75
    const/16 v16, 0x8

    .line 76
    .line 77
    const/16 v17, 0x0

    .line 78
    .line 79
    invoke-static/range {v11 .. v17}, Lcom/android/systemui/log/LogBuffer;->log$default(Lcom/android/systemui/log/LogBuffer;Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Ljava/lang/String;Ljava/lang/Throwable;ILjava/lang/Object;)V

    .line 80
    .line 81
    .line 82
    goto :goto_1

    .line 83
    :cond_1
    const-string v3, "UdfpsDisplayMode.disable"

    .line 84
    .line 85
    invoke-static {v3}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    .line 86
    .line 87
    .line 88
    :try_start_0
    iget-object v3, v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;->authController:Lcom/android/systemui/biometrics/AuthController;

    .line 89
    .line 90
    iget-object v3, v3, Lcom/android/systemui/biometrics/AuthController;->mUdfpsRefreshRateRequestCallback:Landroid/hardware/fingerprint/IUdfpsRefreshRateRequestCallback;

    .line 91
    .line 92
    invoke-static {v3}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 93
    .line 94
    .line 95
    iget v2, v2, Lcom/android/systemui/biometrics/Request;->displayId:I

    .line 96
    .line 97
    invoke-interface {v3, v2}, Landroid/hardware/fingerprint/IUdfpsRefreshRateRequestCallback;->onRequestDisabled(I)V

    .line 98
    .line 99
    .line 100
    const-string v5, "disable | removed the UDFPS refresh rate request"

    .line 101
    .line 102
    const-string v3, "UdfpsDisplayMode"

    .line 103
    .line 104
    iget-object v2, v10, Lcom/android/systemui/biometrics/UdfpsLogger;->logBuffer:Lcom/android/systemui/log/LogBuffer;

    .line 105
    .line 106
    const/4 v6, 0x0

    .line 107
    const/16 v7, 0x8

    .line 108
    .line 109
    const/4 v8, 0x0

    .line 110
    move-object v4, v0

    .line 111
    invoke-static/range {v2 .. v8}, Lcom/android/systemui/log/LogBuffer;->log$default(Lcom/android/systemui/log/LogBuffer;Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Ljava/lang/String;Ljava/lang/Throwable;ILjava/lang/Object;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 112
    .line 113
    .line 114
    goto :goto_0

    .line 115
    :catch_0
    move-exception v0

    .line 116
    sget-object v2, Lcom/android/systemui/log/LogLevel;->ERROR:Lcom/android/systemui/log/LogLevel;

    .line 117
    .line 118
    new-instance v3, Lcom/android/systemui/biometrics/UdfpsLogger$e$2;

    .line 119
    .line 120
    invoke-direct {v3, v9}, Lcom/android/systemui/biometrics/UdfpsLogger$e$2;-><init>(Ljava/lang/String;)V

    .line 121
    .line 122
    .line 123
    const-string v4, "UdfpsDisplayMode"

    .line 124
    .line 125
    iget-object v5, v10, Lcom/android/systemui/biometrics/UdfpsLogger;->logBuffer:Lcom/android/systemui/log/LogBuffer;

    .line 126
    .line 127
    invoke-virtual {v5, v4, v2, v3, v0}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 128
    .line 129
    .line 130
    move-result-object v0

    .line 131
    invoke-virtual {v5, v0}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 132
    .line 133
    .line 134
    :goto_0
    const/4 v0, 0x0

    .line 135
    iput-object v0, v1, Lcom/android/systemui/biometrics/UdfpsDisplayMode;->currentRequest:Lcom/android/systemui/biometrics/Request;

    .line 136
    .line 137
    const-string v5, "disable | onDisabled is null"

    .line 138
    .line 139
    sget-object v4, Lcom/android/systemui/log/LogLevel;->WARNING:Lcom/android/systemui/log/LogLevel;

    .line 140
    .line 141
    const-string v3, "UdfpsDisplayMode"

    .line 142
    .line 143
    iget-object v2, v10, Lcom/android/systemui/biometrics/UdfpsLogger;->logBuffer:Lcom/android/systemui/log/LogBuffer;

    .line 144
    .line 145
    const/4 v6, 0x0

    .line 146
    const/16 v7, 0x8

    .line 147
    .line 148
    const/4 v8, 0x0

    .line 149
    invoke-static/range {v2 .. v8}, Lcom/android/systemui/log/LogBuffer;->log$default(Lcom/android/systemui/log/LogBuffer;Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Ljava/lang/String;Ljava/lang/Throwable;ILjava/lang/Object;)V

    .line 150
    .line 151
    .line 152
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 153
    .line 154
    .line 155
    :cond_2
    :goto_1
    return-void
.end method

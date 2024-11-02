.class public abstract Lcom/samsung/android/nexus/base/layer/BaseLayer;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field private static final TAG:Ljava/lang/String; = "BaseLayer"


# instance fields
.field private mContext:Lcom/samsung/android/nexus/base/context/NexusContext;

.field private mLayerParams:Lcom/samsung/android/nexus/base/layer/NexusLayerParams;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public draw()V
    .locals 0

    .line 1
    invoke-virtual {p0}, Lcom/samsung/android/nexus/base/layer/BaseLayer;->onDraw()V

    return-void
.end method

.method public draw(Landroid/graphics/Canvas;)V
    .locals 0

    .line 2
    invoke-virtual {p0, p1}, Lcom/samsung/android/nexus/base/layer/BaseLayer;->onDraw(Landroid/graphics/Canvas;)V

    return-void
.end method

.method public getAppContext()Landroid/content/Context;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->mContext:Lcom/samsung/android/nexus/base/context/NexusContext;

    .line 2
    .line 3
    if-nez p0, :cond_0

    .line 4
    .line 5
    sget-object p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->TAG:Ljava/lang/String;

    .line 6
    .line 7
    const-string v0, "Context is null."

    .line 8
    .line 9
    invoke-static {p0, v0}, Lcom/samsung/android/nexus/base/utils/Log;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 10
    .line 11
    .line 12
    const/4 p0, 0x0

    .line 13
    return-object p0

    .line 14
    :cond_0
    iget-object p0, p0, Lcom/samsung/android/nexus/base/context/NexusContext;->mContext:Landroid/content/Context;

    .line 15
    .line 16
    return-object p0
.end method

.method public getLayerParams()Lcom/samsung/android/nexus/base/layer/NexusLayerParams;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->mLayerParams:Lcom/samsung/android/nexus/base/layer/NexusLayerParams;

    .line 2
    .line 3
    return-object p0
.end method

.method public getNexusContext()Lcom/samsung/android/nexus/base/context/NexusContext;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->mContext:Lcom/samsung/android/nexus/base/context/NexusContext;

    .line 2
    .line 3
    return-object p0
.end method

.method public abstract onCreate()V
.end method

.method public onDestroy()V
    .locals 0

    .line 1
    return-void
.end method

.method public abstract onDraw()V
.end method

.method public onDraw(Landroid/graphics/Canvas;)V
    .locals 0

    .line 1
    return-void
.end method

.method public abstract onLayerParamsChanged(Lcom/samsung/android/nexus/base/layer/NexusLayerParams;)V
.end method

.method public onSizeChanged(II)V
    .locals 0

    .line 1
    return-void
.end method

.method public onTapEvent(IIJ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onTouchCancelEvent(IIJ)V
    .locals 0

    .line 1
    return-void
.end method

.method public onTouchEvent(IIJ)V
    .locals 0

    .line 1
    return-void
.end method

.method public abstract onVisibilityChanged(Ljava/lang/Boolean;)V
.end method

.method public setLayerParams(Lcom/samsung/android/nexus/base/layer/NexusLayerParams;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->mLayerParams:Lcom/samsung/android/nexus/base/layer/NexusLayerParams;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Lcom/samsung/android/nexus/base/layer/BaseLayer;->onLayerParamsChanged(Lcom/samsung/android/nexus/base/layer/NexusLayerParams;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public setNexusContext(Lcom/samsung/android/nexus/base/context/NexusContext;)V
    .locals 3

    .line 1
    sget-object v0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->TAG:Ljava/lang/String;

    .line 2
    .line 3
    new-instance v1, Ljava/lang/StringBuilder;

    .line 4
    .line 5
    const-string v2, "setNexusContext(): "

    .line 6
    .line 7
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 8
    .line 9
    .line 10
    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 14
    .line 15
    .line 16
    move-result-object v1

    .line 17
    invoke-static {v0, v1}, Lcom/samsung/android/nexus/base/utils/Log;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 18
    .line 19
    .line 20
    iput-object p1, p0, Lcom/samsung/android/nexus/base/layer/BaseLayer;->mContext:Lcom/samsung/android/nexus/base/context/NexusContext;

    .line 21
    .line 22
    return-void
.end method

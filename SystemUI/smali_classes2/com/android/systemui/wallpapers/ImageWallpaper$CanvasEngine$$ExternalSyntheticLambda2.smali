.class public final synthetic Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;

.field public final synthetic f$1:I

.field public final synthetic f$2:Landroid/graphics/Rect;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;ILandroid/graphics/Rect;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$1:I

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$2:Landroid/graphics/Rect;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$1:I

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2;->f$2:Landroid/graphics/Rect;

    .line 6
    .line 7
    iget-object v2, v0, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;->mLock:Ljava/lang/Object;

    .line 8
    .line 9
    monitor-enter v2

    .line 10
    :try_start_0
    invoke-virtual {v0, v1, p0}, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;->drawFullQualityFrame(ILandroid/graphics/Rect;)V

    .line 11
    .line 12
    .line 13
    invoke-virtual {v0}, Lcom/android/systemui/wallpapers/ImageWallpaper$CanvasEngine;->finishRendering()V

    .line 14
    .line 15
    .line 16
    monitor-exit v2

    .line 17
    return-void

    .line 18
    :catchall_0
    move-exception p0

    .line 19
    monitor-exit v2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 20
    throw p0
.end method

.class public final synthetic Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/widget/SystemUIWidgetCallback;

.field public final synthetic f$1:J

.field public final synthetic f$2:Landroid/app/SemWallpaperColors;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/widget/SystemUIWidgetCallback;JLandroid/app/SemWallpaperColors;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/widget/SystemUIWidgetCallback;

    .line 5
    .line 6
    iput-wide p2, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$1:J

    .line 7
    .line 8
    iput-object p4, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$2:Landroid/app/SemWallpaperColors;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/widget/SystemUIWidgetCallback;

    .line 2
    .line 3
    iget-wide v1, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$1:J

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/wallpaper/WallpaperEventNotifier$$ExternalSyntheticLambda1;->f$2:Landroid/app/SemWallpaperColors;

    .line 6
    .line 7
    invoke-interface {v0, v1, v2, p0}, Lcom/android/systemui/widget/SystemUIWidgetCallback;->updateStyle(JLandroid/app/SemWallpaperColors;)V

    .line 8
    .line 9
    .line 10
    return-void
.end method

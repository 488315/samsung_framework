.class public final Lcom/android/systemui/screenshot/ScreenshotDetectionController;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final packageManager:Landroid/content/pm/PackageManager;

.field public final windowManager:Landroid/view/IWindowManager;


# direct methods
.method public constructor <init>(Landroid/view/IWindowManager;Landroid/content/pm/PackageManager;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotDetectionController;->windowManager:Landroid/view/IWindowManager;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/screenshot/ScreenshotDetectionController;->packageManager:Landroid/content/pm/PackageManager;

    .line 7
    .line 8
    return-void
.end method

.class public final Lcom/android/systemui/settings/brightness/BrightnessController$Factory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mBackgroundHandler:Landroid/os/Handler;

.field public final mContext:Landroid/content/Context;

.field public final mDisplayTracker:Lcom/android/systemui/settings/DisplayTracker;

.field public final mMainExecutor:Ljava/util/concurrent/Executor;

.field public final mUserTracker:Lcom/android/systemui/settings/UserTracker;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/android/systemui/settings/UserTracker;Lcom/android/systemui/settings/DisplayTracker;Ljava/util/concurrent/Executor;Landroid/os/Handler;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/settings/brightness/BrightnessController$Factory;->mContext:Landroid/content/Context;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/settings/brightness/BrightnessController$Factory;->mUserTracker:Lcom/android/systemui/settings/UserTracker;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/settings/brightness/BrightnessController$Factory;->mDisplayTracker:Lcom/android/systemui/settings/DisplayTracker;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/settings/brightness/BrightnessController$Factory;->mMainExecutor:Ljava/util/concurrent/Executor;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/settings/brightness/BrightnessController$Factory;->mBackgroundHandler:Landroid/os/Handler;

    .line 13
    .line 14
    return-void
.end method

.class public final Lcom/android/systemui/statusbar/notification/LaunchAnimationParameters;
.super Lcom/android/systemui/animation/LaunchAnimator$State;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public linearProgress:F

.field public notificationParentTop:I

.field public parentStartClipTopAmount:I

.field public parentStartRoundedTopClipping:I

.field public progress:F

.field public startClipTopAmount:I

.field public startNotificationTop:I

.field public startRoundedTopClipping:I

.field public startTranslationZ:F


# direct methods
.method public constructor <init>()V
    .locals 7

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    move-object v0, p0

    .line 3
    invoke-direct/range {v0 .. v6}, Lcom/android/systemui/statusbar/notification/LaunchAnimationParameters;-><init>(IIIIFF)V

    return-void
.end method

.method public constructor <init>(IIIIFF)V
    .locals 0

    .line 2
    invoke-direct/range {p0 .. p6}, Lcom/android/systemui/animation/LaunchAnimator$State;-><init>(IIIIFF)V

    return-void
.end method

.method public synthetic constructor <init>(IIIIFFILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 9

    and-int/lit8 v0, p7, 0x10

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    move v7, v1

    goto :goto_0

    :cond_0
    move v7, p5

    :goto_0
    and-int/lit8 v0, p7, 0x20

    if-eqz v0, :cond_1

    move v8, v1

    goto :goto_1

    :cond_1
    move v8, p6

    :goto_1
    move-object v2, p0

    move v3, p1

    move v4, p2

    move v5, p3

    move v6, p4

    .line 1
    invoke-direct/range {v2 .. v8}, Lcom/android/systemui/statusbar/notification/LaunchAnimationParameters;-><init>(IIIIFF)V

    return-void
.end method

.class public final Lcom/android/systemui/power/sound/UsbDamageCautionSound;
.super Lcom/android/systemui/power/sound/PowerUiSound;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Landroid/content/Context;I)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2}, Lcom/android/systemui/power/sound/PowerUiSound;-><init>(Landroid/content/Context;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final checkCondition()Z
    .locals 2

    .line 1
    iget v0, p0, Lcom/android/systemui/power/sound/PowerUiSound;->mRingerMode:I

    .line 2
    .line 3
    const/4 v1, 0x2

    .line 4
    if-eq v0, v1, :cond_0

    .line 5
    .line 6
    iput v1, p0, Lcom/android/systemui/power/sound/PowerUiSound;->mRingerMode:I

    .line 7
    .line 8
    :cond_0
    const/4 p0, 0x1

    .line 9
    return p0
.end method

.method public final playSoundAndVibration()V
    .locals 3

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/power/sound/PowerUiSound;->checkCommonCondition()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    const/4 v0, 0x7

    .line 8
    invoke-virtual {p0, v0, v0}, Lcom/android/systemui/power/sound/PowerUiSound;->playSound(II)V

    .line 9
    .line 10
    .line 11
    const/16 v0, 0x1770

    .line 12
    .line 13
    sget-object v1, Landroid/os/VibrationEffect$SemMagnitudeType;->TYPE_NOTIFICATION:Landroid/os/VibrationEffect$SemMagnitudeType;

    .line 14
    .line 15
    const/16 v2, 0x9

    .line 16
    .line 17
    invoke-virtual {p0, v2, v0, v1}, Lcom/android/systemui/power/sound/PowerUiSound;->playVibration(IILandroid/os/VibrationEffect$SemMagnitudeType;)V

    .line 18
    .line 19
    .line 20
    :cond_0
    return-void
.end method
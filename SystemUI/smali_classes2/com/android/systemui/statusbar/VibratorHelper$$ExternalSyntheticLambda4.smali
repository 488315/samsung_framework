.class public final synthetic Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/VibratorHelper;

.field public final synthetic f$1:I

.field public final synthetic f$2:Ljava/lang/String;

.field public final synthetic f$3:Landroid/os/VibrationEffect;

.field public final synthetic f$4:Ljava/lang/String;

.field public final synthetic f$5:Landroid/os/VibrationAttributes;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/VibratorHelper;ILjava/lang/String;Landroid/os/VibrationEffect;Ljava/lang/String;Landroid/os/VibrationAttributes;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/statusbar/VibratorHelper;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$1:I

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$2:Ljava/lang/String;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$3:Landroid/os/VibrationEffect;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$4:Ljava/lang/String;

    .line 13
    .line 14
    iput-object p6, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$5:Landroid/os/VibrationAttributes;

    .line 15
    .line 16
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 7

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/statusbar/VibratorHelper;

    .line 2
    .line 3
    iget v2, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$1:I

    .line 4
    .line 5
    iget-object v3, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$2:Ljava/lang/String;

    .line 6
    .line 7
    iget-object v4, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$3:Landroid/os/VibrationEffect;

    .line 8
    .line 9
    iget-object v5, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$4:Ljava/lang/String;

    .line 10
    .line 11
    iget-object v6, p0, Lcom/android/systemui/statusbar/VibratorHelper$$ExternalSyntheticLambda4;->f$5:Landroid/os/VibrationAttributes;

    .line 12
    .line 13
    iget-object v1, v0, Lcom/android/systemui/statusbar/VibratorHelper;->mVibrator:Landroid/os/Vibrator;

    .line 14
    .line 15
    invoke-virtual/range {v1 .. v6}, Landroid/os/Vibrator;->vibrate(ILjava/lang/String;Landroid/os/VibrationEffect;Ljava/lang/String;Landroid/os/VibrationAttributes;)V

    .line 16
    .line 17
    .line 18
    return-void
.end method
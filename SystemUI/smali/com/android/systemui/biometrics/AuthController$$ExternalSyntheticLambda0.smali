.class public final synthetic Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/biometrics/AuthController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/biometrics/AuthController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/biometrics/AuthController;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/biometrics/AuthController;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/biometrics/AuthController;->updateSensorLocations()V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/biometrics/AuthController;->mCurrentDialog:Lcom/android/systemui/biometrics/AuthDialog;

    .line 7
    .line 8
    if-eqz p0, :cond_0

    .line 9
    .line 10
    check-cast p0, Lcom/android/systemui/biometrics/AuthContainerView;

    .line 11
    .line 12
    const/4 v0, 0x1

    .line 13
    invoke-virtual {p0, v0}, Lcom/android/systemui/biometrics/AuthContainerView;->maybeUpdatePositionForUdfps(Z)V

    .line 14
    .line 15
    .line 16
    iget-object p0, p0, Lcom/android/systemui/biometrics/AuthContainerView;->mBiometricView:Lcom/android/systemui/biometrics/AuthBiometricViewAdapter;

    .line 17
    .line 18
    if-eqz p0, :cond_0

    .line 19
    .line 20
    invoke-interface {p0}, Lcom/android/systemui/biometrics/AuthBiometricViewAdapter;->onOrientationChanged()V

    .line 21
    .line 22
    .line 23
    :cond_0
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 24
    .line 25
    return-object p0
.end method

.class public final synthetic Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/biometrics/AuthController;

.field public final synthetic f$1:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/biometrics/AuthController;I)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/biometrics/AuthController;

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda3;->f$1:I

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/biometrics/AuthController;

    .line 2
    .line 3
    iget p0, p0, Lcom/android/systemui/biometrics/AuthController$$ExternalSyntheticLambda3;->f$1:I

    .line 4
    .line 5
    iget-object v1, v0, Lcom/android/systemui/biometrics/AuthController;->mCurrentDialog:Lcom/android/systemui/biometrics/AuthDialog;

    .line 6
    .line 7
    iget-object v0, v0, Lcom/android/systemui/biometrics/AuthController;->mContext:Landroid/content/Context;

    .line 8
    .line 9
    const v2, 0x104051c

    .line 10
    .line 11
    .line 12
    invoke-virtual {v0, v2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 13
    .line 14
    .line 15
    move-result-object v0

    .line 16
    check-cast v1, Lcom/android/systemui/biometrics/AuthContainerView;

    .line 17
    .line 18
    invoke-virtual {v1, p0, v0}, Lcom/android/systemui/biometrics/AuthContainerView;->onAuthenticationFailed(ILjava/lang/String;)V

    .line 19
    .line 20
    .line 21
    return-void
.end method

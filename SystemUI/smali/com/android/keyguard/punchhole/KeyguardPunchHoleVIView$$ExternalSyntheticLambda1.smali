.class public final synthetic Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/airbnb/lottie/LottieListener;


# instance fields
.field public final synthetic f$0:Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;

.field public final synthetic f$1:Ljava/lang/String;


# direct methods
.method public synthetic constructor <init>(Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;Ljava/lang/String;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView$$ExternalSyntheticLambda1;->f$0:Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView$$ExternalSyntheticLambda1;->f$1:Ljava/lang/String;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onResult(Ljava/lang/Object;)V
    .locals 3

    .line 1
    check-cast p1, Ljava/lang/Throwable;

    .line 2
    .line 3
    iget-object p1, p0, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView$$ExternalSyntheticLambda1;->f$0:Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;

    .line 4
    .line 5
    iget-object v0, p1, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;->TAG:Ljava/lang/String;

    .line 6
    .line 7
    new-instance v1, Ljava/lang/StringBuilder;

    .line 8
    .line 9
    const-string v2, "Unable to parse json composition : "

    .line 10
    .line 11
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 12
    .line 13
    .line 14
    iget-object p0, p0, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView$$ExternalSyntheticLambda1;->f$1:Ljava/lang/String;

    .line 15
    .line 16
    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 17
    .line 18
    .line 19
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 20
    .line 21
    .line 22
    move-result-object p0

    .line 23
    invoke-static {v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 24
    .line 25
    .line 26
    const/4 p0, 0x0

    .line 27
    invoke-virtual {p1, p0}, Lcom/android/keyguard/punchhole/KeyguardPunchHoleVIView;->setPrepareState(I)V

    .line 28
    .line 29
    .line 30
    return-void
.end method

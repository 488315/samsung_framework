.class public final Lcom/android/keyguard/KeyguardUpdateMonitor$16;
.super Landroid/hardware/fingerprint/IFingerprintAuthenticatorsRegisteredCallback$Stub;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method public constructor <init>(Lcom/android/keyguard/KeyguardUpdateMonitor;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$16;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/hardware/fingerprint/IFingerprintAuthenticatorsRegisteredCallback$Stub;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAllAuthenticatorsRegistered(Ljava/util/List;)V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$16;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 2
    .line 3
    iput-object p1, v0, Lcom/android/keyguard/KeyguardUpdateMonitor;->mFingerprintSensorProperties:Ljava/util/List;

    .line 4
    .line 5
    const/4 p1, 0x2

    .line 6
    invoke-virtual {v0, p1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->updateFingerprintListeningState(I)V

    .line 7
    .line 8
    .line 9
    iget-object p0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$16;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor;->mLogger:Lcom/android/keyguard/logging/KeyguardUpdateMonitorLogger;

    .line 12
    .line 13
    const-string p1, "FingerprintManager onAllAuthenticatorsRegistered"

    .line 14
    .line 15
    invoke-virtual {p0, p1}, Lcom/android/keyguard/logging/KeyguardUpdateMonitorLogger;->d(Ljava/lang/String;)V

    .line 16
    .line 17
    .line 18
    return-void
.end method
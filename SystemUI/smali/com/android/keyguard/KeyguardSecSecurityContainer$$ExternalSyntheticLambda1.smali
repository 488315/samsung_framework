.class public final synthetic Lcom/android/keyguard/KeyguardSecSecurityContainer$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/content/DialogInterface$OnDismissListener;


# instance fields
.field public final synthetic f$0:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method public synthetic constructor <init>(Lcom/android/keyguard/KeyguardUpdateMonitor;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/keyguard/KeyguardSecSecurityContainer$$ExternalSyntheticLambda1;->f$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onDismiss(Landroid/content/DialogInterface;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSecSecurityContainer$$ExternalSyntheticLambda1;->f$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 2
    .line 3
    sget p1, Lcom/android/keyguard/KeyguardSecSecurityContainer;->$r8$clinit:I

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-interface {p0, p1}, Lcom/android/keyguard/KeyguardSecUpdateMonitor;->setDisableBiometricBySecurityDialog(Z)V

    .line 7
    .line 8
    .line 9
    return-void
.end method

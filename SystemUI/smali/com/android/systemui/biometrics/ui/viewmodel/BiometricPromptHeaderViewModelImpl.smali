.class public final Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/biometrics/ui/viewmodel/CredentialHeaderViewModel;


# instance fields
.field public final description:Ljava/lang/String;

.field public final icon:Landroid/graphics/drawable/Drawable;

.field public final request:Lcom/android/systemui/biometrics/domain/model/BiometricPromptRequest$Credential;

.field public final subtitle:Ljava/lang/String;

.field public final title:Ljava/lang/String;

.field public final user:Landroid/os/UserHandle;


# direct methods
.method public constructor <init>(Lcom/android/systemui/biometrics/domain/model/BiometricPromptRequest$Credential;Landroid/os/UserHandle;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/drawable/Drawable;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->request:Lcom/android/systemui/biometrics/domain/model/BiometricPromptRequest$Credential;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->user:Landroid/os/UserHandle;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->title:Ljava/lang/String;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->subtitle:Ljava/lang/String;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->description:Ljava/lang/String;

    .line 13
    .line 14
    iput-object p6, p0, Lcom/android/systemui/biometrics/ui/viewmodel/BiometricPromptHeaderViewModelImpl;->icon:Landroid/graphics/drawable/Drawable;

    .line 15
    .line 16
    return-void
.end method

.class public Lcom/android/keyguard/KeyguardRMMView;
.super Lcom/android/keyguard/KeyguardSecPinBasedInputView;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    .line 1
    invoke-direct {p0, p1, v0}, Lcom/android/keyguard/KeyguardRMMView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    .line 2
    invoke-direct {p0, p1, p2}, Lcom/android/keyguard/KeyguardSecPinBasedInputView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method


# virtual methods
.method public final getPasswordTextViewId()I
    .locals 0

    .line 1
    const p0, 0x7f0a08e0

    .line 2
    .line 3
    .line 4
    return p0
.end method

.method public final getWrongPasswordStringId()I
    .locals 0

    .line 1
    const p0, 0x7f130873

    .line 2
    .line 3
    .line 4
    return p0
.end method
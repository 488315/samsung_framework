.class public final Lcom/android/keyguard/KeyguardSecSimPukViewController;
.super Lcom/android/keyguard/KeyguardSimPukViewController;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mCarrierDialog:Landroid/app/AlertDialog;

.field public final mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

.field public final mConfigurationListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$1;

.field public final mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

.field public final mInputMethodManager:Landroid/view/inputmethod/InputMethodManager;

.field public mLocale:Ljava/util/Locale;

.field public mOrientation:I

.field public final mProgressBar:Landroid/widget/ProgressBar;

.field public final mSettingsHelper:Lcom/android/systemui/util/SettingsHelper;

.field public final mSettingsListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;

.field public final mSettingsValueList:[Landroid/net/Uri;

.field public final mSimCardName:Lcom/android/systemui/widget/SystemUITextView;

.field public final mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;


# direct methods
.method public constructor <init>(Lcom/android/keyguard/KeyguardSecSimPukView;Lcom/android/keyguard/KeyguardUpdateMonitor;Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;Lcom/android/internal/widget/LockPatternUtils;Lcom/android/keyguard/KeyguardSecurityCallback;Lcom/android/keyguard/KeyguardMessageAreaController$Factory;Lcom/android/internal/util/LatencyTracker;Lcom/android/keyguard/LiftToActivateListener;Landroid/telephony/TelephonyManager;Lcom/android/systemui/classifier/FalsingCollector;Lcom/android/keyguard/EmergencyButtonController;Lcom/android/systemui/flags/FeatureFlags;Lcom/android/keyguard/SecRotationWatcher;Lcom/android/systemui/vibrate/VibrationUtil;Landroid/view/accessibility/AccessibilityManager;Lcom/android/systemui/statusbar/policy/ConfigurationController;Landroid/view/inputmethod/InputMethodManager;)V
    .locals 5

    .line 1
    move-object v0, p0

    .line 2
    invoke-direct/range {p0 .. p16}, Lcom/android/keyguard/KeyguardSimPukViewController;-><init>(Lcom/android/keyguard/KeyguardSimPukView;Lcom/android/keyguard/KeyguardUpdateMonitor;Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;Lcom/android/internal/widget/LockPatternUtils;Lcom/android/keyguard/KeyguardSecurityCallback;Lcom/android/keyguard/KeyguardMessageAreaController$Factory;Lcom/android/internal/util/LatencyTracker;Lcom/android/keyguard/LiftToActivateListener;Landroid/telephony/TelephonyManager;Lcom/android/systemui/classifier/FalsingCollector;Lcom/android/keyguard/EmergencyButtonController;Lcom/android/systemui/flags/FeatureFlags;Lcom/android/keyguard/SecRotationWatcher;Lcom/android/systemui/vibrate/VibrationUtil;Landroid/view/accessibility/AccessibilityManager;Lcom/android/systemui/statusbar/policy/ConfigurationController;)V

    .line 3
    .line 4
    .line 5
    const/4 v1, 0x1

    .line 6
    iput v1, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mOrientation:I

    .line 7
    .line 8
    new-instance v2, Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;

    .line 9
    .line 10
    invoke-direct {v2, p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;)V

    .line 11
    .line 12
    .line 13
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;

    .line 14
    .line 15
    new-instance v2, Lcom/android/keyguard/KeyguardSecSimPukViewController$1;

    .line 16
    .line 17
    invoke-direct {v2, p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController$1;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;)V

    .line 18
    .line 19
    .line 20
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$1;

    .line 21
    .line 22
    const-string v2, "emergency_mode"

    .line 23
    .line 24
    invoke-static {v2}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    .line 25
    .line 26
    .line 27
    move-result-object v2

    .line 28
    const-string/jumbo v3, "select_name_1"

    .line 29
    .line 30
    .line 31
    invoke-static {v3}, Landroid/provider/Settings$Global;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    .line 32
    .line 33
    .line 34
    move-result-object v3

    .line 35
    const-string/jumbo v4, "select_name_2"

    .line 36
    .line 37
    .line 38
    invoke-static {v4}, Landroid/provider/Settings$Global;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    .line 39
    .line 40
    .line 41
    move-result-object v4

    .line 42
    filled-new-array {v2, v3, v4}, [Landroid/net/Uri;

    .line 43
    .line 44
    .line 45
    move-result-object v2

    .line 46
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsValueList:[Landroid/net/Uri;

    .line 47
    .line 48
    new-instance v2, Lcom/android/keyguard/KeyguardSecSimPukViewController$2;

    .line 49
    .line 50
    invoke-direct {v2, p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController$2;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;)V

    .line 51
    .line 52
    .line 53
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 54
    .line 55
    move-object/from16 v2, p16

    .line 56
    .line 57
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 58
    .line 59
    move-object/from16 v2, p17

    .line 60
    .line 61
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mInputMethodManager:Landroid/view/inputmethod/InputMethodManager;

    .line 62
    .line 63
    new-instance v2, Lcom/android/keyguard/KeyguardSecSimPukViewController$SecStateMachine;

    .line 64
    .line 65
    const/4 v3, 0x0

    .line 66
    invoke-direct {v2, p0, v3}, Lcom/android/keyguard/KeyguardSecSimPukViewController$SecStateMachine;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;I)V

    .line 67
    .line 68
    .line 69
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSimPukViewController;->mStateMachine:Lcom/android/keyguard/KeyguardSimPukViewController$StateMachine;

    .line 70
    .line 71
    const-class v2, Lcom/android/systemui/util/SettingsHelper;

    .line 72
    .line 73
    invoke-static {v2}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 74
    .line 75
    .line 76
    move-result-object v2

    .line 77
    check-cast v2, Lcom/android/systemui/util/SettingsHelper;

    .line 78
    .line 79
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsHelper:Lcom/android/systemui/util/SettingsHelper;

    .line 80
    .line 81
    iget-object v2, v0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 82
    .line 83
    check-cast v2, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 84
    .line 85
    const v3, 0x7f0a0548

    .line 86
    .line 87
    .line 88
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 89
    .line 90
    .line 91
    move-result-object v2

    .line 92
    check-cast v2, Lcom/android/keyguard/KeyguardSecESimArea;

    .line 93
    .line 94
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 95
    .line 96
    if-eqz v2, :cond_0

    .line 97
    .line 98
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardInputViewController;->getKeyguardSecurityCallback()Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 99
    .line 100
    .line 101
    move-result-object v3

    .line 102
    iput-object v3, v2, Lcom/android/keyguard/KeyguardSecESimArea;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 103
    .line 104
    iget-object v3, v0, Lcom/android/keyguard/KeyguardInputViewController;->mSecurityMode:Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    .line 105
    .line 106
    iput-object v3, v2, Lcom/android/keyguard/KeyguardSecESimArea;->mSecurityMode:Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    .line 107
    .line 108
    :cond_0
    iget-object v2, v0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 109
    .line 110
    check-cast v2, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 111
    .line 112
    const v3, 0x7f0a0829

    .line 113
    .line 114
    .line 115
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 116
    .line 117
    .line 118
    move-result-object v2

    .line 119
    check-cast v2, Landroid/widget/ProgressBar;

    .line 120
    .line 121
    iput-object v2, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mProgressBar:Landroid/widget/ProgressBar;

    .line 122
    .line 123
    invoke-virtual {v2, v1}, Landroid/widget/ProgressBar;->setIndeterminate(Z)V

    .line 124
    .line 125
    .line 126
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController;->updateProgressBarDrawable()V

    .line 127
    .line 128
    .line 129
    iget-object v1, v0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 130
    .line 131
    check-cast v1, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 132
    .line 133
    const v2, 0x7f0a0550

    .line 134
    .line 135
    .line 136
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 137
    .line 138
    .line 139
    move-result-object v1

    .line 140
    check-cast v1, Landroid/widget/ImageView;

    .line 141
    .line 142
    iput-object v1, v0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSimImageView:Landroid/widget/ImageView;

    .line 143
    .line 144
    iget-object v1, v0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 145
    .line 146
    check-cast v1, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 147
    .line 148
    const v2, 0x7f0a0551

    .line 149
    .line 150
    .line 151
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 152
    .line 153
    .line 154
    move-result-object v1

    .line 155
    check-cast v1, Lcom/android/systemui/widget/SystemUITextView;

    .line 156
    .line 157
    iput-object v1, v0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSimCardName:Lcom/android/systemui/widget/SystemUITextView;

    .line 158
    .line 159
    iget-object v0, v0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 160
    .line 161
    check-cast v0, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 162
    .line 163
    const v1, 0x7f0a0549

    .line 164
    .line 165
    .line 166
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    .line 167
    .line 168
    .line 169
    move-result-object v0

    .line 170
    if-eqz v0, :cond_1

    .line 171
    .line 172
    const/4 v1, 0x4

    .line 173
    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 174
    .line 175
    .line 176
    :cond_1
    return-void
.end method


# virtual methods
.method public final checkPin()Z
    .locals 4

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-object v1, p0, Lcom/android/keyguard/KeyguardPinBasedInputViewController;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    .line 3
    .line 4
    if-eqz v1, :cond_1

    .line 5
    .line 6
    instance-of v2, v1, Lcom/android/keyguard/SecPasswordTextView;

    .line 7
    .line 8
    if-nez v2, :cond_0

    .line 9
    .line 10
    goto :goto_0

    .line 11
    :cond_0
    move-object v2, v1

    .line 12
    check-cast v2, Lcom/android/keyguard/SecPasswordTextView;

    .line 13
    .line 14
    iget-object v2, v2, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 15
    .line 16
    invoke-virtual {v2}, Ljava/lang/String;->length()I

    .line 17
    .line 18
    .line 19
    move-result v2

    .line 20
    const/4 v3, 0x4

    .line 21
    if-lt v2, v3, :cond_1

    .line 22
    .line 23
    const/16 v3, 0x8

    .line 24
    .line 25
    if-gt v2, v3, :cond_1

    .line 26
    .line 27
    check-cast v1, Lcom/android/keyguard/SecPasswordTextView;

    .line 28
    .line 29
    iget-object v0, v1, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 30
    .line 31
    iput-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPinText:Ljava/lang/String;

    .line 32
    .line 33
    const/4 p0, 0x1

    .line 34
    return p0

    .line 35
    :cond_1
    :goto_0
    return v0
.end method

.method public final checkPuk()Z
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/keyguard/KeyguardPinBasedInputViewController;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    instance-of v1, v0, Lcom/android/keyguard/SecPasswordTextView;

    .line 6
    .line 7
    if-eqz v1, :cond_0

    .line 8
    .line 9
    move-object v1, v0

    .line 10
    check-cast v1, Lcom/android/keyguard/SecPasswordTextView;

    .line 11
    .line 12
    iget-object v1, v1, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 13
    .line 14
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    .line 15
    .line 16
    .line 17
    move-result v1

    .line 18
    const/16 v2, 0x8

    .line 19
    .line 20
    if-ne v1, v2, :cond_0

    .line 21
    .line 22
    check-cast v0, Lcom/android/keyguard/SecPasswordTextView;

    .line 23
    .line 24
    iget-object v0, v0, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 25
    .line 26
    iput-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPukText:Ljava/lang/String;

    .line 27
    .line 28
    const/4 p0, 0x1

    .line 29
    return p0

    .line 30
    :cond_0
    const/4 p0, 0x0

    .line 31
    return p0
.end method

.method public final confirmPin()Z
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/keyguard/KeyguardPinBasedInputViewController;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    const/4 p0, 0x0

    .line 6
    return p0

    .line 7
    :cond_0
    instance-of v1, v0, Lcom/android/keyguard/SecPasswordTextView;

    .line 8
    .line 9
    if-eqz v1, :cond_1

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPinText:Ljava/lang/String;

    .line 12
    .line 13
    check-cast v0, Lcom/android/keyguard/SecPasswordTextView;

    .line 14
    .line 15
    iget-object v0, v0, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 16
    .line 17
    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    .line 18
    .line 19
    .line 20
    move-result p0

    .line 21
    return p0

    .line 22
    :cond_1
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPinText:Ljava/lang/String;

    .line 23
    .line 24
    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    .line 25
    .line 26
    .line 27
    move-result-object v0

    .line 28
    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    .line 29
    .line 30
    .line 31
    move-result-object v0

    .line 32
    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    .line 33
    .line 34
    .line 35
    move-result p0

    .line 36
    return p0
.end method

.method public final getSecurityViewId()I
    .locals 0

    .line 1
    const p0, 0x7f0a0553

    .line 2
    .line 3
    .line 4
    return p0
.end method

.method public final onViewAttached()V
    .locals 2

    .line 1
    invoke-super {p0}, Lcom/android/keyguard/KeyguardSimPukViewController;->onViewAttached()V

    .line 2
    .line 3
    .line 4
    iget-object v0, p0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 5
    .line 6
    check-cast v0, Lcom/android/keyguard/KeyguardSecSimPukView;

    .line 7
    .line 8
    new-instance v1, Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda0;

    .line 9
    .line 10
    invoke-direct {v1, p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda0;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;)V

    .line 11
    .line 12
    .line 13
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->post(Ljava/lang/Runnable;)Z

    .line 14
    .line 15
    .line 16
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 17
    .line 18
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->registerCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 21
    .line 22
    .line 23
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 24
    .line 25
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$1;

    .line 26
    .line 27
    check-cast v0, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;

    .line 28
    .line 29
    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;->addCallback(Ljava/lang/Object;)V

    .line 30
    .line 31
    .line 32
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsHelper:Lcom/android/systemui/util/SettingsHelper;

    .line 33
    .line 34
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;

    .line 35
    .line 36
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsValueList:[Landroid/net/Uri;

    .line 37
    .line 38
    invoke-virtual {v0, v1, p0}, Lcom/android/systemui/util/SettingsHelper;->registerCallback(Lcom/android/systemui/util/SettingsHelper$OnChangedCallback;[Landroid/net/Uri;)V

    .line 39
    .line 40
    .line 41
    return-void
.end method

.method public final onViewDetached()V
    .locals 2

    .line 1
    invoke-super {p0}, Lcom/android/keyguard/KeyguardSimPukViewController;->onViewDetached()V

    .line 2
    .line 3
    .line 4
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 5
    .line 6
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 7
    .line 8
    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->removeCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 9
    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 12
    .line 13
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mConfigurationListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$1;

    .line 14
    .line 15
    check-cast v0, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;

    .line 16
    .line 17
    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;->removeCallback(Ljava/lang/Object;)V

    .line 18
    .line 19
    .line 20
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsHelper:Lcom/android/systemui/util/SettingsHelper;

    .line 21
    .line 22
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSettingsListener:Lcom/android/keyguard/KeyguardSecSimPukViewController$$ExternalSyntheticLambda1;

    .line 23
    .line 24
    invoke-virtual {v0, p0}, Lcom/android/systemui/util/SettingsHelper;->unregisterCallback(Lcom/android/systemui/util/SettingsHelper$OnChangedCallback;)V

    .line 25
    .line 26
    .line 27
    return-void
.end method

.method public final resetState()V
    .locals 1

    .line 1
    invoke-super {p0}, Lcom/android/keyguard/KeyguardSimPukViewController;->resetState()V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController;->showDefaultMessage()V

    .line 5
    .line 6
    .line 7
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSimImageView:Landroid/widget/ImageView;

    .line 8
    .line 9
    if-eqz v0, :cond_0

    .line 10
    .line 11
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController;->updateSimIconImage()V

    .line 12
    .line 13
    .line 14
    :cond_0
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController;->updateESimLayout()V

    .line 15
    .line 16
    .line 17
    return-void
.end method

.method public final showDefaultMessage()V
    .locals 8

    .line 1
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_SUB_DISPLAY_COVER:Z

    .line 2
    .line 3
    const-string v1, "KeyguardSecSimPukViewController"

    .line 4
    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    const-class v0, Lcom/android/systemui/keyguard/DisplayLifecycle;

    .line 8
    .line 9
    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 10
    .line 11
    .line 12
    move-result-object v0

    .line 13
    check-cast v0, Lcom/android/systemui/keyguard/DisplayLifecycle;

    .line 14
    .line 15
    iget-boolean v0, v0, Lcom/android/systemui/keyguard/DisplayLifecycle;->mIsFolderOpened:Z

    .line 16
    .line 17
    if-nez v0, :cond_0

    .line 18
    .line 19
    const-string p0, "Skip updating showDefaultMessage when folder closed"

    .line 20
    .line 21
    invoke-static {v1, p0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 22
    .line 23
    .line 24
    return-void

    .line 25
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    .line 26
    .line 27
    const-string/jumbo v2, "showDefaultMessage subId="

    .line 28
    .line 29
    .line 30
    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 31
    .line 32
    .line 33
    iget v2, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 34
    .line 35
    invoke-static {v0, v2, v1}, Landroidx/appcompat/widget/TooltipPopup$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)V

    .line 36
    .line 37
    .line 38
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 39
    .line 40
    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->isValidSubscriptionId(I)Z

    .line 41
    .line 42
    .line 43
    move-result v0

    .line 44
    iget-object v2, p0, Lcom/android/keyguard/KeyguardPinBasedInputViewController;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    .line 45
    .line 46
    if-eqz v0, :cond_10

    .line 47
    .line 48
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 49
    .line 50
    invoke-static {v0}, Lcom/android/keyguard/SecurityUtils;->getSimSlotNum(I)I

    .line 51
    .line 52
    .line 53
    move-result v0

    .line 54
    const/4 v3, -0x1

    .line 55
    if-ne v0, v3, :cond_1

    .line 56
    .line 57
    const-string/jumbo p0, "showDefaultMessage - skip update"

    .line 58
    .line 59
    .line 60
    invoke-static {v1, p0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 61
    .line 62
    .line 63
    return-void

    .line 64
    :cond_1
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 65
    .line 66
    const/4 v4, 0x0

    .line 67
    :try_start_0
    const-string v5, "isemtelephony"

    .line 68
    .line 69
    invoke-static {v5}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    .line 70
    .line 71
    .line 72
    move-result-object v5

    .line 73
    invoke-static {v5}, Lcom/android/internal/telephony/ISemTelephony$Stub;->asInterface(Landroid/os/IBinder;)Lcom/android/internal/telephony/ISemTelephony;

    .line 74
    .line 75
    .line 76
    move-result-object v5

    .line 77
    if-eqz v5, :cond_2

    .line 78
    .line 79
    invoke-interface {v5, v0}, Lcom/android/internal/telephony/ISemTelephony;->getSimPukRetryForSubscriber(I)I

    .line 80
    .line 81
    .line 82
    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 83
    goto :goto_0

    .line 84
    :catch_0
    move-exception v0

    .line 85
    const-string v5, "Exception: "

    .line 86
    .line 87
    invoke-static {v5, v0, v1}, Landroidx/picker/adapter/AbsAdapter$1$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/Exception;Ljava/lang/String;)V

    .line 88
    .line 89
    .line 90
    :cond_2
    move v0, v4

    .line 91
    :goto_0
    new-instance v5, Ljava/lang/StringBuilder;

    .line 92
    .line 93
    const-string v6, "getSimPukLockInfoResult(): num_of_retry is "

    .line 94
    .line 95
    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 96
    .line 97
    .line 98
    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 99
    .line 100
    .line 101
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 102
    .line 103
    .line 104
    move-result-object v5

    .line 105
    invoke-static {v1, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 106
    .line 107
    .line 108
    invoke-virtual {p0}, Lcom/android/systemui/util/ViewController;->getResources()Landroid/content/res/Resources;

    .line 109
    .line 110
    .line 111
    move-result-object v1

    .line 112
    const/4 v5, 0x1

    .line 113
    const v6, 0x7f130891

    .line 114
    .line 115
    .line 116
    if-eq v0, v3, :cond_8

    .line 117
    .line 118
    const-string v3, " "

    .line 119
    .line 120
    if-eq v0, v5, :cond_5

    .line 121
    .line 122
    const/16 v7, 0xa

    .line 123
    .line 124
    if-eq v0, v7, :cond_8

    .line 125
    .line 126
    sget-boolean v5, Lcom/android/systemui/LsRune;->SECURITY_KOR_USIM_TEXT:Z

    .line 127
    .line 128
    if-eqz v5, :cond_3

    .line 129
    .line 130
    new-instance v5, Ljava/lang/StringBuilder;

    .line 131
    .line 132
    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    .line 133
    .line 134
    .line 135
    invoke-virtual {v1, v6}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 136
    .line 137
    .line 138
    move-result-object v6

    .line 139
    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 140
    .line 141
    .line 142
    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 143
    .line 144
    .line 145
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 146
    .line 147
    .line 148
    move-result-object v0

    .line 149
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 150
    .line 151
    .line 152
    move-result-object v0

    .line 153
    const v3, 0x7f13097c

    .line 154
    .line 155
    .line 156
    invoke-virtual {v1, v3, v0}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 157
    .line 158
    .line 159
    move-result-object v0

    .line 160
    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 161
    .line 162
    .line 163
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 164
    .line 165
    .line 166
    move-result-object v0

    .line 167
    goto/16 :goto_2

    .line 168
    .line 169
    :cond_3
    sget-boolean v3, Lcom/android/systemui/LsRune;->SECURITY_USE_CDMA_CARD_TEXT:Z

    .line 170
    .line 171
    if-eqz v3, :cond_4

    .line 172
    .line 173
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 174
    .line 175
    .line 176
    move-result-object v0

    .line 177
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 178
    .line 179
    .line 180
    move-result-object v0

    .line 181
    const v3, 0x7f1307fb

    .line 182
    .line 183
    .line 184
    invoke-virtual {v1, v3, v0}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 185
    .line 186
    .line 187
    move-result-object v0

    .line 188
    goto/16 :goto_2

    .line 189
    .line 190
    :cond_4
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 191
    .line 192
    .line 193
    move-result-object v0

    .line 194
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 195
    .line 196
    .line 197
    move-result-object v0

    .line 198
    const v3, 0x7f13097f

    .line 199
    .line 200
    .line 201
    invoke-virtual {v1, v3, v0}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 202
    .line 203
    .line 204
    move-result-object v0

    .line 205
    goto/16 :goto_2

    .line 206
    .line 207
    :cond_5
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_KOR_USIM_TEXT:Z

    .line 208
    .line 209
    if-eqz v0, :cond_6

    .line 210
    .line 211
    new-instance v0, Ljava/lang/StringBuilder;

    .line 212
    .line 213
    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 214
    .line 215
    .line 216
    invoke-virtual {v1, v6}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 217
    .line 218
    .line 219
    move-result-object v5

    .line 220
    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 221
    .line 222
    .line 223
    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 224
    .line 225
    .line 226
    const v3, 0x7f13097b

    .line 227
    .line 228
    .line 229
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 230
    .line 231
    .line 232
    move-result-object v1

    .line 233
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 234
    .line 235
    .line 236
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 237
    .line 238
    .line 239
    move-result-object v0

    .line 240
    goto/16 :goto_2

    .line 241
    .line 242
    :cond_6
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_USE_CDMA_CARD_TEXT:Z

    .line 243
    .line 244
    if-eqz v0, :cond_7

    .line 245
    .line 246
    const v0, 0x7f1307fa

    .line 247
    .line 248
    .line 249
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 250
    .line 251
    .line 252
    move-result-object v0

    .line 253
    goto/16 :goto_2

    .line 254
    .line 255
    :cond_7
    const v0, 0x7f13097e

    .line 256
    .line 257
    .line 258
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 259
    .line 260
    .line 261
    move-result-object v0

    .line 262
    goto :goto_2

    .line 263
    :cond_8
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_DCM_USIM_TEXT:Z

    .line 264
    .line 265
    if-eqz v0, :cond_9

    .line 266
    .line 267
    const v0, 0x7f1307fe

    .line 268
    .line 269
    .line 270
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 271
    .line 272
    .line 273
    move-result-object v0

    .line 274
    goto :goto_2

    .line 275
    :cond_9
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_KTT_USIM_TEXT:Z

    .line 276
    .line 277
    if-eqz v0, :cond_a

    .line 278
    .line 279
    const v0, 0x7f130896

    .line 280
    .line 281
    .line 282
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 283
    .line 284
    .line 285
    move-result-object v0

    .line 286
    goto :goto_2

    .line 287
    :cond_a
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_KOR_USIM_TEXT:Z

    .line 288
    .line 289
    if-eqz v0, :cond_b

    .line 290
    .line 291
    invoke-virtual {v1, v6}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 292
    .line 293
    .line 294
    move-result-object v0

    .line 295
    goto :goto_2

    .line 296
    :cond_b
    sget-boolean v0, Lcom/android/systemui/LsRune;->SECURITY_USE_CDMA_CARD_TEXT:Z

    .line 297
    .line 298
    if-eqz v0, :cond_c

    .line 299
    .line 300
    const v0, 0x7f1307f9

    .line 301
    .line 302
    .line 303
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 304
    .line 305
    .line 306
    move-result-object v0

    .line 307
    goto :goto_2

    .line 308
    :cond_c
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 309
    .line 310
    const-class v3, Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 311
    .line 312
    invoke-static {v3}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    .line 313
    .line 314
    .line 315
    move-result-object v3

    .line 316
    check-cast v3, Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 317
    .line 318
    invoke-virtual {v3, v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSubscriptionInfoForSubId(I)Landroid/telephony/SubscriptionInfo;

    .line 319
    .line 320
    .line 321
    move-result-object v0

    .line 322
    if-eqz v0, :cond_d

    .line 323
    .line 324
    invoke-virtual {v0}, Landroid/telephony/SubscriptionInfo;->getIccId()Ljava/lang/String;

    .line 325
    .line 326
    .line 327
    move-result-object v0

    .line 328
    if-eqz v0, :cond_d

    .line 329
    .line 330
    const-string v3, "894101"

    .line 331
    .line 332
    invoke-virtual {v0, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    .line 333
    .line 334
    .line 335
    move-result v0

    .line 336
    if-eqz v0, :cond_d

    .line 337
    .line 338
    goto :goto_1

    .line 339
    :cond_d
    move v5, v4

    .line 340
    :goto_1
    if-eqz v5, :cond_e

    .line 341
    .line 342
    const v0, 0x7f13098d

    .line 343
    .line 344
    .line 345
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 346
    .line 347
    .line 348
    move-result-object v0

    .line 349
    goto :goto_2

    .line 350
    :cond_e
    const v0, 0x7f13097d

    .line 351
    .line 352
    .line 353
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 354
    .line 355
    .line 356
    move-result-object v0

    .line 357
    :goto_2
    iget-object p0, p0, Lcom/android/keyguard/KeyguardInputViewController;->mMessageAreaController:Lcom/android/keyguard/KeyguardSecMessageAreaController;

    .line 358
    .line 359
    if-eqz p0, :cond_11

    .line 360
    .line 361
    if-eqz v2, :cond_f

    .line 362
    .line 363
    move-object v1, v2

    .line 364
    check-cast v1, Lcom/android/keyguard/SecPasswordTextView;

    .line 365
    .line 366
    iget-object v1, v1, Lcom/android/keyguard/PasswordTextView;->mText:Ljava/lang/String;

    .line 367
    .line 368
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    .line 369
    .line 370
    .line 371
    move-result v1

    .line 372
    if-lez v1, :cond_f

    .line 373
    .line 374
    return-void

    .line 375
    :cond_f
    invoke-virtual {p0, v0, v4}, Lcom/android/keyguard/KeyguardMessageAreaController;->setMessage(Ljava/lang/CharSequence;Z)V

    .line 376
    .line 377
    .line 378
    goto :goto_3

    .line 379
    :cond_10
    new-instance v0, Ljava/lang/StringBuilder;

    .line 380
    .line 381
    const-string/jumbo v3, "showDefaultMessage isValidSubscriptionId failed !!!  subid:"

    .line 382
    .line 383
    .line 384
    invoke-direct {v0, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 385
    .line 386
    .line 387
    iget p0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 388
    .line 389
    invoke-static {v0, p0, v1}, Landroidx/appcompat/widget/TooltipPopup$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)V

    .line 390
    .line 391
    .line 392
    :cond_11
    :goto_3
    if-eqz v2, :cond_12

    .line 393
    .line 394
    invoke-virtual {v2}, Landroid/widget/EditText;->requestFocus()Z

    .line 395
    .line 396
    .line 397
    :cond_12
    return-void
.end method

.method public final updateESimLayout()V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 2
    .line 3
    if-eqz v0, :cond_2

    .line 4
    .line 5
    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getVisibility()I

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    const/16 v1, 0x8

    .line 10
    .line 11
    if-ne v0, v1, :cond_0

    .line 12
    .line 13
    goto :goto_1

    .line 14
    :cond_0
    iget-object v0, p0, Lcom/android/keyguard/KeyguardInputViewController;->mMessageAreaController:Lcom/android/keyguard/KeyguardSecMessageAreaController;

    .line 15
    .line 16
    if-eqz v0, :cond_2

    .line 17
    .line 18
    invoke-virtual {v0}, Lcom/android/keyguard/KeyguardSecMessageAreaController;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 19
    .line 20
    .line 21
    move-result-object v1

    .line 22
    check-cast v1, Landroid/view/ViewGroup$MarginLayoutParams;

    .line 23
    .line 24
    iget v2, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mOrientation:I

    .line 25
    .line 26
    const/4 v3, 0x1

    .line 27
    if-ne v2, v3, :cond_1

    .line 28
    .line 29
    iget-object v2, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 30
    .line 31
    invoke-virtual {v2}, Landroid/widget/FrameLayout;->getHeight()I

    .line 32
    .line 33
    .line 34
    move-result v2

    .line 35
    invoke-virtual {p0}, Lcom/android/systemui/util/ViewController;->getResources()Landroid/content/res/Resources;

    .line 36
    .line 37
    .line 38
    move-result-object p0

    .line 39
    const v3, 0x7f07041c

    .line 40
    .line 41
    .line 42
    invoke-virtual {p0, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 43
    .line 44
    .line 45
    move-result p0

    .line 46
    mul-int/lit8 p0, p0, 0x2

    .line 47
    .line 48
    add-int/2addr p0, v2

    .line 49
    iput p0, v1, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 50
    .line 51
    goto :goto_0

    .line 52
    :cond_1
    iget-object p0, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 53
    .line 54
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getHeight()I

    .line 55
    .line 56
    .line 57
    move-result p0

    .line 58
    iput p0, v1, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 59
    .line 60
    :goto_0
    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardSecMessageAreaController;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 61
    .line 62
    .line 63
    :cond_2
    :goto_1
    return-void
.end method

.method public final updateProgressBarDrawable()V
    .locals 2

    .line 1
    const-string v0, "background"

    .line 2
    .line 3
    invoke-static {v0}, Lcom/android/systemui/wallpaper/WallpaperUtils;->isWhiteKeyguardWallpaper(Ljava/lang/String;)Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mProgressBar:Landroid/widget/ProgressBar;

    .line 8
    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/util/ViewController;->getContext()Landroid/content/Context;

    .line 10
    .line 11
    .line 12
    move-result-object p0

    .line 13
    if-eqz v0, :cond_0

    .line 14
    .line 15
    const v0, 0x7f080b44

    .line 16
    .line 17
    .line 18
    goto :goto_0

    .line 19
    :cond_0
    const v0, 0x7f080b43

    .line 20
    .line 21
    .line 22
    :goto_0
    invoke-virtual {p0, v0}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 23
    .line 24
    .line 25
    move-result-object p0

    .line 26
    invoke-virtual {v1, p0}, Landroid/widget/ProgressBar;->setIndeterminateDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 27
    .line 28
    .line 29
    return-void
.end method

.method public final updateSim()V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mCheckSimPukThread:Lcom/android/keyguard/KeyguardSimPukViewController$CheckSimPuk;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    new-instance v0, Lcom/android/keyguard/KeyguardSecSimPukViewController$3;

    .line 6
    .line 7
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPukText:Ljava/lang/String;

    .line 8
    .line 9
    iget-object v2, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mPinText:Ljava/lang/String;

    .line 10
    .line 11
    iget v3, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 12
    .line 13
    invoke-direct {v0, p0, v1, v2, v3}, Lcom/android/keyguard/KeyguardSecSimPukViewController$3;-><init>(Lcom/android/keyguard/KeyguardSecSimPukViewController;Ljava/lang/String;Ljava/lang/String;I)V

    .line 14
    .line 15
    .line 16
    iput-object v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mCheckSimPukThread:Lcom/android/keyguard/KeyguardSimPukViewController$CheckSimPuk;

    .line 17
    .line 18
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 19
    .line 20
    .line 21
    :cond_0
    return-void
.end method

.method public final updateSimIconImage()V
    .locals 7

    .line 1
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSimCount()I

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    const/4 v1, 0x1

    .line 10
    if-le v0, v1, :cond_b

    .line 11
    .line 12
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 13
    .line 14
    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->isValidSubscriptionId(I)Z

    .line 15
    .line 16
    .line 17
    move-result v0

    .line 18
    if-eqz v0, :cond_b

    .line 19
    .line 20
    iget v0, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSubId:I

    .line 21
    .line 22
    invoke-static {v0}, Lcom/android/keyguard/SecurityUtils;->getSimSlotNum(I)I

    .line 23
    .line 24
    .line 25
    move-result v0

    .line 26
    const/4 v2, -0x1

    .line 27
    const-string v3, "KeyguardSecSimPukViewController"

    .line 28
    .line 29
    if-ne v0, v2, :cond_0

    .line 30
    .line 31
    const-string/jumbo p0, "updateSimIconImage - skip update"

    .line 32
    .line 33
    .line 34
    invoke-static {v3, p0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 35
    .line 36
    .line 37
    return-void

    .line 38
    :cond_0
    iget-object v2, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mSimImageView:Landroid/widget/ImageView;

    .line 39
    .line 40
    instance-of v4, v2, Lcom/android/systemui/widget/SystemUIImageView;

    .line 41
    .line 42
    const/4 v5, 0x0

    .line 43
    const/16 v6, 0x8

    .line 44
    .line 45
    if-eqz v4, :cond_7

    .line 46
    .line 47
    check-cast v2, Lcom/android/systemui/widget/SystemUIImageView;

    .line 48
    .line 49
    sget-boolean v4, Lcom/android/systemui/LsRune;->SECURITY_ESIM:Z

    .line 50
    .line 51
    if-eqz v4, :cond_3

    .line 52
    .line 53
    invoke-virtual {p0}, Lcom/android/systemui/util/ViewController;->getContext()Landroid/content/Context;

    .line 54
    .line 55
    .line 56
    move-result-object v4

    .line 57
    invoke-static {v0, v4}, Lcom/android/systemui/util/DeviceState;->isESIM(ILandroid/content/Context;)Z

    .line 58
    .line 59
    .line 60
    move-result v4

    .line 61
    if-eqz v4, :cond_3

    .line 62
    .line 63
    iget-object v4, p0, Lcom/android/keyguard/KeyguardSimPukViewController;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 64
    .line 65
    invoke-interface {v4}, Lcom/android/keyguard/KeyguardSecUpdateMonitor;->isESimEmbedded()Z

    .line 66
    .line 67
    .line 68
    move-result v4

    .line 69
    if-eqz v4, :cond_3

    .line 70
    .line 71
    const-string/jumbo v4, "this is e-SIM"

    .line 72
    .line 73
    .line 74
    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 75
    .line 76
    .line 77
    iget-object v3, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 78
    .line 79
    if-eqz v3, :cond_1

    .line 80
    .line 81
    invoke-virtual {v3, v5}, Lcom/android/keyguard/KeyguardSecESimArea;->setVisibility(I)V

    .line 82
    .line 83
    .line 84
    :cond_1
    if-nez v0, :cond_2

    .line 85
    .line 86
    const-string v1, "lock_ic_pin_attempt_esim_01"

    .line 87
    .line 88
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setOriginImage(Ljava/lang/String;)V

    .line 89
    .line 90
    .line 91
    const-string v1, "lock_ic_pin_attempt_esim_01_whitebg"

    .line 92
    .line 93
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setWhiteBgImage(Ljava/lang/String;)V

    .line 94
    .line 95
    .line 96
    goto :goto_0

    .line 97
    :cond_2
    if-ne v0, v1, :cond_6

    .line 98
    .line 99
    const-string v1, "lock_ic_pin_attempt_esim_02"

    .line 100
    .line 101
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setOriginImage(Ljava/lang/String;)V

    .line 102
    .line 103
    .line 104
    const-string v1, "lock_ic_pin_attempt_esim_02_whitebg"

    .line 105
    .line 106
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setWhiteBgImage(Ljava/lang/String;)V

    .line 107
    .line 108
    .line 109
    goto :goto_0

    .line 110
    :cond_3
    iget-object v3, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mESimSkipArea:Lcom/android/keyguard/KeyguardSecESimArea;

    .line 111
    .line 112
    if-eqz v3, :cond_4

    .line 113
    .line 114
    invoke-virtual {v3, v6}, Lcom/android/keyguard/KeyguardSecESimArea;->setVisibility(I)V

    .line 115
    .line 116
    .line 117
    :cond_4
    if-nez v0, :cond_5

    .line 118
    .line 119
    const-string v1, "lock_ic_pin_attempt_sim_01"

    .line 120
    .line 121
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setOriginImage(Ljava/lang/String;)V

    .line 122
    .line 123
    .line 124
    const-string v1, "lock_ic_pin_attempt_sim_01_whitebg"

    .line 125
    .line 126
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setWhiteBgImage(Ljava/lang/String;)V

    .line 127
    .line 128
    .line 129
    goto :goto_0

    .line 130
    :cond_5
    if-ne v0, v1, :cond_6

    .line 131
    .line 132
    const-string v1, "lock_ic_pin_attempt_sim_02"

    .line 133
    .line 134
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setOriginImage(Ljava/lang/String;)V

    .line 135
    .line 136
    .line 137
    const-string v1, "lock_ic_pin_attempt_sim_02_whitebg"

    .line 138
    .line 139
    invoke-virtual {v2, v1}, Lcom/android/systemui/widget/SystemUIImageView;->setWhiteBgImage(Ljava/lang/String;)V

    .line 140
    .line 141
    .line 142
    :cond_6
    :goto_0
    invoke-virtual {v2}, Lcom/android/systemui/widget/SystemUIImageView;->updateImage()V

    .line 143
    .line 144
    .line 145
    :cond_7
    iget-object v1, p0, Lcom/android/keyguard/KeyguardSecSimPukViewController;->mSimCardName:Lcom/android/systemui/widget/SystemUITextView;

    .line 146
    .line 147
    instance-of v2, v1, Lcom/android/systemui/widget/SystemUITextView;

    .line 148
    .line 149
    if-eqz v2, :cond_b

    .line 150
    .line 151
    invoke-virtual {p0}, Lcom/android/systemui/util/ViewController;->getContext()Landroid/content/Context;

    .line 152
    .line 153
    .line 154
    move-result-object p0

    .line 155
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    .line 156
    .line 157
    .line 158
    move-result-object p0

    .line 159
    if-nez v0, :cond_8

    .line 160
    .line 161
    const-string/jumbo v0, "select_name_1"

    .line 162
    .line 163
    .line 164
    goto :goto_1

    .line 165
    :cond_8
    const-string/jumbo v0, "select_name_2"

    .line 166
    .line 167
    .line 168
    :goto_1
    invoke-static {p0, v0}, Landroid/provider/Settings$Global;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    .line 169
    .line 170
    .line 171
    move-result-object p0

    .line 172
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    .line 173
    .line 174
    .line 175
    move-result v0

    .line 176
    if-nez v0, :cond_9

    .line 177
    .line 178
    invoke-virtual {v1, p0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 179
    .line 180
    .line 181
    :cond_9
    if-eqz v0, :cond_a

    .line 182
    .line 183
    move v5, v6

    .line 184
    :cond_a
    invoke-virtual {v1, v5}, Lcom/android/systemui/widget/SystemUITextView;->setVisibility(I)V

    .line 185
    .line 186
    .line 187
    :cond_b
    return-void
.end method

.method public final updateStyle(JLandroid/app/SemWallpaperColors;)V
    .locals 0

    .line 1
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecPinBasedInputViewController;->initializeBottomContainerView()V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecSimPukViewController;->updateProgressBarDrawable()V

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public final verifyPasswordAndUnlock()V
    .locals 2

    .line 1
    const-string v0, "KeyguardSecSimPukViewController"

    .line 2
    .line 3
    const-string/jumbo v1, "verifyPasswordAndUnlock next"

    .line 4
    .line 5
    .line 6
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 7
    .line 8
    .line 9
    invoke-super {p0}, Lcom/android/keyguard/KeyguardSimPukViewController;->verifyPasswordAndUnlock()V

    .line 10
    .line 11
    .line 12
    return-void
.end method

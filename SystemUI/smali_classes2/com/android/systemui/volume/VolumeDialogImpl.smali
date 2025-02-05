.class public final Lcom/android/systemui/volume/VolumeDialogImpl;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/plugins/VolumeDialog;
.implements Lcom/android/systemui/Dumpable;
.implements Lcom/android/systemui/statusbar/policy/ConfigurationController$ConfigurationListener;
.implements Landroid/view/ViewTreeObserver$OnComputeInternalInsetsListener;


# static fields
.field public static final TAG:Ljava/lang/String;


# instance fields
.field public final mAccessibility:Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;

.field public final mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

.field public mActiveStream:I

.field public final mActivityManager:Landroid/app/ActivityManager;

.field public final mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

.field public final mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

.field public mAutomute:Z

.field public final mChangeVolumeRowTintWhenInactive:Z

.field public mConfigChanged:Z

.field public mConfigurableTexts:Lcom/android/systemui/volume/ConfigurableTexts;

.field public final mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

.field public final mContext:Landroid/content/Context;

.field public final mController:Lcom/android/systemui/plugins/VolumeDialogController;

.field public final mControllerCallbackH:Lcom/android/systemui/volume/VolumeDialogImpl$7;

.field public final mCrossWindowBlurEnabledListener:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda0;

.field public mCsdDialog:Lcom/android/systemui/volume/CsdWarningDialog;

.field public final mCsdWarningDialogFactory:Lcom/android/systemui/volume/CsdWarningDialog$Factory;

.field public mDevicePosture:I

.field public final mDevicePostureController:Lcom/android/systemui/statusbar/policy/DevicePostureController;

.field public final mDevicePostureControllerCallback:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

.field public final mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

.field public mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

.field public mDialogCornerRadius:I

.field public final mDialogHideAnimationDurationMs:I

.field public mDialogRowsView:Landroid/view/ViewGroup;

.field public mDialogRowsViewBackground:Lcom/android/internal/graphics/drawable/BackgroundBlurDrawable;

.field public mDialogRowsViewContainer:Landroid/view/ViewGroup;

.field public final mDialogShowAnimationDurationMs:I

.field public mDialogView:Landroid/view/ViewGroup;

.field public mDialogWidth:I

.field public final mDynamic:Landroid/util/SparseBooleanArray;

.field public final mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

.field public mHasSeenODICaptionsTooltip:Z

.field public mHovering:Z

.field public final mInteractionJankMonitor:Lcom/android/internal/jank/InteractionJankMonitor;

.field public mIsAnimatingDismiss:Z

.field public mIsRingerDrawerOpen:Z

.field public final mKeyguard:Landroid/app/KeyguardManager;

.field public final mMediaOutputDialogFactory:Lcom/android/systemui/media/dialog/MediaOutputDialogFactory;

.field public mODICaptionsIcon:Lcom/android/systemui/volume/CaptionsToggleImageButton;

.field public mODICaptionsTooltipView:Landroid/view/View;

.field public mODICaptionsTooltipViewStub:Landroid/view/ViewStub;

.field public mODICaptionsView:Landroid/view/ViewGroup;

.field public mOrientation:I

.field public mOriginalGravity:I

.field public mPrevActiveStream:I

.field public mRinger:Landroid/view/ViewGroup;

.field public mRingerAndDrawerContainer:Landroid/view/View;

.field public mRingerAndDrawerContainerBackground:Landroid/graphics/drawable/Drawable;

.field public mRingerCount:I

.field public mRingerDrawerClosedAmount:F

.field public mRingerDrawerContainer:Landroid/view/ViewGroup;

.field public mRingerDrawerIconAnimatingDeselected:Landroid/widget/ImageView;

.field public mRingerDrawerIconAnimatingSelected:Landroid/widget/ImageView;

.field public final mRingerDrawerIconColorAnimator:Landroid/animation/ValueAnimator;

.field public mRingerDrawerItemSize:I

.field public mRingerDrawerMute:Landroid/view/ViewGroup;

.field public mRingerDrawerMuteIcon:Landroid/widget/ImageView;

.field public mRingerDrawerNewSelectionBg:Landroid/view/ViewGroup;

.field public mRingerDrawerNormal:Landroid/view/ViewGroup;

.field public mRingerDrawerNormalIcon:Landroid/widget/ImageView;

.field public mRingerDrawerVibrate:Landroid/view/ViewGroup;

.field public mRingerDrawerVibrateIcon:Landroid/widget/ImageView;

.field public mRingerIcon:Landroid/widget/ImageButton;

.field public mRingerRowsPadding:I

.field public final mRows:Ljava/util/List;

.field public mSafetyWarning:Lcom/android/systemui/volume/SafetyWarningDialog;

.field public final mSafetyWarningLock:Ljava/lang/Object;

.field public mSelectedRingerContainer:Landroid/view/ViewGroup;

.field public mSelectedRingerIcon:Landroid/widget/ImageView;

.field public mSettingsIcon:Landroid/widget/ImageButton;

.field public mSettingsView:Landroid/view/View;

.field public mShowA11yStream:Z

.field public final mShowActiveStreamOnly:Z

.field public final mShowLowMediaVolumeIcon:Z

.field public mShowVibrate:Z

.field public mShowing:Z

.field public mSilentMode:Z

.field public mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

.field public mTopContainer:Landroid/view/View;

.field public final mTouchableRegion:Landroid/graphics/Region;

.field public final mUseBackgroundBlur:Z

.field public final mVolumePanelFactory:Lcom/android/systemui/volume/VolumePanelFactory;

.field final mVolumeRingerIconDrawableId:I

.field final mVolumeRingerMuteIconDrawableId:I

.field public mWindow:Landroid/view/Window;

.field public mWindowGravity:I

.field public mZenIcon:Landroid/widget/FrameLayout;


# direct methods
.method public static -$$Nest$mshowH(Lcom/android/systemui/volume/VolumeDialogImpl;IZI)V
    .locals 5

    .line 1
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 2
    .line 3
    .line 4
    const-string v0, "VolumeDialogImpl#showH"

    .line 5
    .line 6
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    .line 7
    .line 8
    .line 9
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 10
    .line 11
    new-instance v1, Ljava/lang/StringBuilder;

    .line 12
    .line 13
    const-string/jumbo v2, "showH r="

    .line 14
    .line 15
    .line 16
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 17
    .line 18
    .line 19
    sget-object v2, Lcom/android/systemui/volume/Events;->SHOW_REASONS:[Ljava/lang/String;

    .line 20
    .line 21
    aget-object v2, v2, p1

    .line 22
    .line 23
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 24
    .line 25
    .line 26
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 27
    .line 28
    .line 29
    move-result-object v1

    .line 30
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 31
    .line 32
    .line 33
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 34
    .line 35
    const/4 v1, 0x1

    .line 36
    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    .line 37
    .line 38
    .line 39
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 40
    .line 41
    const/4 v2, 0x2

    .line 42
    invoke-virtual {v0, v2}, Landroid/os/Handler;->removeMessages(I)V

    .line 43
    .line 44
    .line 45
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->rescheduleTimeoutH()V

    .line 46
    .line 47
    .line 48
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigChanged:Z

    .line 49
    .line 50
    const/4 v2, 0x0

    .line 51
    if-eqz v0, :cond_1

    .line 52
    .line 53
    invoke-virtual {p0, p3}, Lcom/android/systemui/volume/VolumeDialogImpl;->initDialog(I)V

    .line 54
    .line 55
    .line 56
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurableTexts:Lcom/android/systemui/volume/ConfigurableTexts;

    .line 57
    .line 58
    iget-object v3, v0, Lcom/android/systemui/volume/ConfigurableTexts;->mTexts:Landroid/util/ArrayMap;

    .line 59
    .line 60
    invoke-virtual {v3}, Landroid/util/ArrayMap;->isEmpty()Z

    .line 61
    .line 62
    .line 63
    move-result v4

    .line 64
    if-eqz v4, :cond_0

    .line 65
    .line 66
    goto :goto_0

    .line 67
    :cond_0
    invoke-virtual {v3, v2}, Landroid/util/ArrayMap;->keyAt(I)Ljava/lang/Object;

    .line 68
    .line 69
    .line 70
    move-result-object v3

    .line 71
    check-cast v3, Landroid/widget/TextView;

    .line 72
    .line 73
    iget-object v0, v0, Lcom/android/systemui/volume/ConfigurableTexts;->mUpdateAll:Lcom/android/systemui/volume/ConfigurableTexts$2;

    .line 74
    .line 75
    invoke-virtual {v3, v0}, Landroid/widget/TextView;->post(Ljava/lang/Runnable;)Z

    .line 76
    .line 77
    .line 78
    :goto_0
    iput-boolean v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigChanged:Z

    .line 79
    .line 80
    :cond_1
    invoke-virtual {p0, p3}, Lcom/android/systemui/volume/VolumeDialogImpl;->initSettingsH(I)V

    .line 81
    .line 82
    .line 83
    iput-boolean v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 84
    .line 85
    iput-boolean v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsAnimatingDismiss:Z

    .line 86
    .line 87
    iget-object p3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 88
    .line 89
    invoke-virtual {p3}, Landroid/app/Dialog;->show()V

    .line 90
    .line 91
    .line 92
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 93
    .line 94
    .line 95
    move-result-object p1

    .line 96
    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 97
    .line 98
    .line 99
    move-result-object p2

    .line 100
    filled-new-array {p1, p2}, [Ljava/lang/Object;

    .line 101
    .line 102
    .line 103
    move-result-object p1

    .line 104
    invoke-static {v2, p1}, Lcom/android/systemui/volume/Events;->writeEvent(I[Ljava/lang/Object;)V

    .line 105
    .line 106
    .line 107
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 108
    .line 109
    invoke-interface {p1, v1}, Lcom/android/systemui/plugins/VolumeDialogController;->notifyVisible(Z)V

    .line 110
    .line 111
    .line 112
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 113
    .line 114
    invoke-interface {p1, v2}, Lcom/android/systemui/plugins/VolumeDialogController;->getCaptionsComponentState(Z)V

    .line 115
    .line 116
    .line 117
    invoke-virtual {p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->checkODICaptionsTooltip(Z)V

    .line 118
    .line 119
    .line 120
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateBackgroundForDrawerClosedAmount()V

    .line 121
    .line 122
    .line 123
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 124
    .line 125
    .line 126
    return-void
.end method

.method public static constructor <clinit>()V
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/volume/VolumeDialogImpl;

    .line 2
    .line 3
    invoke-static {v0}, Lcom/android/systemui/volume/Util;->logTag(Ljava/lang/Class;)Ljava/lang/String;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    sput-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 8
    .line 9
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/android/systemui/plugins/VolumeDialogController;Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;Lcom/android/systemui/statusbar/policy/ConfigurationController;Lcom/android/systemui/media/dialog/MediaOutputDialogFactory;Lcom/android/systemui/volume/VolumePanelFactory;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/internal/jank/InteractionJankMonitor;Lcom/android/systemui/volume/CsdWarningDialog$Factory;Lcom/android/systemui/statusbar/policy/DevicePostureController;Landroid/os/Looper;Lcom/android/systemui/dump/DumpManager;)V
    .locals 9

    .line 1
    move-object v0, p0

    .line 2
    move-object v1, p1

    .line 3
    move-object/from16 v2, p11

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    new-instance v3, Landroid/graphics/Region;

    .line 9
    .line 10
    invoke-direct {v3}, Landroid/graphics/Region;-><init>()V

    .line 11
    .line 12
    .line 13
    iput-object v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTouchableRegion:Landroid/graphics/Region;

    .line 14
    .line 15
    const/4 v3, 0x2

    .line 16
    new-array v4, v3, [F

    .line 17
    .line 18
    fill-array-data v4, :array_0

    .line 19
    .line 20
    .line 21
    invoke-static {v4}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    .line 22
    .line 23
    .line 24
    move-result-object v4

    .line 25
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerIconColorAnimator:Landroid/animation/ValueAnimator;

    .line 26
    .line 27
    new-array v3, v3, [F

    .line 28
    .line 29
    fill-array-data v3, :array_1

    .line 30
    .line 31
    .line 32
    invoke-static {v3}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    .line 33
    .line 34
    .line 35
    move-result-object v3

    .line 36
    iput-object v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

    .line 37
    .line 38
    const/4 v3, 0x0

    .line 39
    iput-boolean v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsRingerDrawerOpen:Z

    .line 40
    .line 41
    const/high16 v4, 0x3f800000    # 1.0f

    .line 42
    .line 43
    iput v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerClosedAmount:F

    .line 44
    .line 45
    new-instance v4, Ljava/util/ArrayList;

    .line 46
    .line 47
    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    .line 48
    .line 49
    .line 50
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 51
    .line 52
    new-instance v4, Landroid/util/SparseBooleanArray;

    .line 53
    .line 54
    invoke-direct {v4}, Landroid/util/SparseBooleanArray;-><init>()V

    .line 55
    .line 56
    .line 57
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 58
    .line 59
    new-instance v4, Ljava/lang/Object;

    .line 60
    .line 61
    invoke-direct {v4}, Ljava/lang/Object;-><init>()V

    .line 62
    .line 63
    .line 64
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarningLock:Ljava/lang/Object;

    .line 65
    .line 66
    new-instance v4, Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;

    .line 67
    .line 68
    invoke-direct {v4, p0, v3}, Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 69
    .line 70
    .line 71
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibility:Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;

    .line 72
    .line 73
    const/4 v4, 0x1

    .line 74
    iput-boolean v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAutomute:Z

    .line 75
    .line 76
    iput-boolean v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSilentMode:Z

    .line 77
    .line 78
    iput-boolean v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHovering:Z

    .line 79
    .line 80
    iput-boolean v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigChanged:Z

    .line 81
    .line 82
    iput-boolean v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsAnimatingDismiss:Z

    .line 83
    .line 84
    const/4 v5, 0x0

    .line 85
    iput-object v5, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 86
    .line 87
    const v6, 0x7f080ac6

    .line 88
    .line 89
    .line 90
    iput v6, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerIconDrawableId:I

    .line 91
    .line 92
    const v6, 0x7f080ac5

    .line 93
    .line 94
    .line 95
    iput v6, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 96
    .line 97
    new-instance v6, Lcom/android/systemui/volume/VolumeDialogImpl$7;

    .line 98
    .line 99
    invoke-direct {v6, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$7;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 100
    .line 101
    .line 102
    iput-object v6, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mControllerCallbackH:Lcom/android/systemui/volume/VolumeDialogImpl$7;

    .line 103
    .line 104
    new-instance v6, Landroid/view/ContextThemeWrapper;

    .line 105
    .line 106
    const v7, 0x7f14082d

    .line 107
    .line 108
    .line 109
    invoke-direct {v6, p1, v7}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 110
    .line 111
    .line 112
    iput-object v6, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 113
    .line 114
    new-instance v7, Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 115
    .line 116
    move-object/from16 v8, p12

    .line 117
    .line 118
    invoke-direct {v7, p0, v8}, Lcom/android/systemui/volume/VolumeDialogImpl$H;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Landroid/os/Looper;)V

    .line 119
    .line 120
    .line 121
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 122
    .line 123
    move-object v7, p2

    .line 124
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 125
    .line 126
    const-string v7, "keyguard"

    .line 127
    .line 128
    invoke-virtual {v6, v7}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    .line 129
    .line 130
    .line 131
    move-result-object v7

    .line 132
    check-cast v7, Landroid/app/KeyguardManager;

    .line 133
    .line 134
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mKeyguard:Landroid/app/KeyguardManager;

    .line 135
    .line 136
    const-string v7, "activity"

    .line 137
    .line 138
    invoke-virtual {v6, v7}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    .line 139
    .line 140
    .line 141
    move-result-object v7

    .line 142
    check-cast v7, Landroid/app/ActivityManager;

    .line 143
    .line 144
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActivityManager:Landroid/app/ActivityManager;

    .line 145
    .line 146
    move-object v7, p3

    .line 147
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

    .line 148
    .line 149
    move-object v7, p4

    .line 150
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    .line 151
    .line 152
    move-object v7, p5

    .line 153
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 154
    .line 155
    move-object v7, p6

    .line 156
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mMediaOutputDialogFactory:Lcom/android/systemui/media/dialog/MediaOutputDialogFactory;

    .line 157
    .line 158
    move-object/from16 v7, p7

    .line 159
    .line 160
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumePanelFactory:Lcom/android/systemui/volume/VolumePanelFactory;

    .line 161
    .line 162
    move-object/from16 v7, p10

    .line 163
    .line 164
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mCsdWarningDialogFactory:Lcom/android/systemui/volume/CsdWarningDialog$Factory;

    .line 165
    .line 166
    move-object/from16 v7, p8

    .line 167
    .line 168
    iput-object v7, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    .line 169
    .line 170
    invoke-virtual {v6}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    .line 171
    .line 172
    .line 173
    move-result-object v7

    .line 174
    const-string v8, "android.software.leanback"

    .line 175
    .line 176
    invoke-virtual {v7, v8}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    .line 177
    .line 178
    .line 179
    move-result v7

    .line 180
    if-nez v7, :cond_1

    .line 181
    .line 182
    invoke-virtual {v6}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    .line 183
    .line 184
    .line 185
    move-result-object v7

    .line 186
    const-string v8, "android.hardware.type.television"

    .line 187
    .line 188
    invoke-virtual {v7, v8}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    .line 189
    .line 190
    .line 191
    move-result v7

    .line 192
    if-eqz v7, :cond_0

    .line 193
    .line 194
    goto :goto_0

    .line 195
    :cond_0
    move v4, v3

    .line 196
    :cond_1
    :goto_0
    iput-boolean v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowActiveStreamOnly:Z

    .line 197
    .line 198
    const-string v4, "HasSeenODICaptionsTooltip"

    .line 199
    .line 200
    invoke-static {p1, v4, v3}, Lcom/android/systemui/Prefs;->getBoolean(Landroid/content/Context;Ljava/lang/String;Z)Z

    .line 201
    .line 202
    .line 203
    move-result v1

    .line 204
    iput-boolean v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHasSeenODICaptionsTooltip:Z

    .line 205
    .line 206
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 207
    .line 208
    .line 209
    move-result-object v1

    .line 210
    const v3, 0x7f050035

    .line 211
    .line 212
    .line 213
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getBoolean(I)Z

    .line 214
    .line 215
    .line 216
    move-result v1

    .line 217
    iput-boolean v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowLowMediaVolumeIcon:Z

    .line 218
    .line 219
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 220
    .line 221
    .line 222
    move-result-object v1

    .line 223
    const v3, 0x7f050004

    .line 224
    .line 225
    .line 226
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getBoolean(I)Z

    .line 227
    .line 228
    .line 229
    move-result v1

    .line 230
    iput-boolean v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mChangeVolumeRowTintWhenInactive:Z

    .line 231
    .line 232
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 233
    .line 234
    .line 235
    move-result-object v1

    .line 236
    const v3, 0x7f0b001b

    .line 237
    .line 238
    .line 239
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getInteger(I)I

    .line 240
    .line 241
    .line 242
    move-result v1

    .line 243
    iput v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogShowAnimationDurationMs:I

    .line 244
    .line 245
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 246
    .line 247
    .line 248
    move-result-object v1

    .line 249
    const v3, 0x7f0b001a

    .line 250
    .line 251
    .line 252
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getInteger(I)I

    .line 253
    .line 254
    .line 255
    move-result v1

    .line 256
    iput v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogHideAnimationDurationMs:I

    .line 257
    .line 258
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 259
    .line 260
    .line 261
    move-result-object v1

    .line 262
    const v3, 0x7f050048

    .line 263
    .line 264
    .line 265
    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getBoolean(I)Z

    .line 266
    .line 267
    .line 268
    move-result v1

    .line 269
    iput-boolean v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mUseBackgroundBlur:Z

    .line 270
    .line 271
    move-object/from16 v3, p9

    .line 272
    .line 273
    iput-object v3, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mInteractionJankMonitor:Lcom/android/internal/jank/InteractionJankMonitor;

    .line 274
    .line 275
    invoke-virtual/range {p13 .. p13}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 276
    .line 277
    .line 278
    const-string v3, "VolumeDialogImpl"

    .line 279
    .line 280
    move-object/from16 v4, p13

    .line 281
    .line 282
    invoke-static {v4, v3, p0}, Lcom/android/systemui/dump/DumpManager;->registerDumpable$default(Lcom/android/systemui/dump/DumpManager;Ljava/lang/String;Lcom/android/systemui/Dumpable;)V

    .line 283
    .line 284
    .line 285
    if-eqz v1, :cond_2

    .line 286
    .line 287
    const v1, 0x7f060973

    .line 288
    .line 289
    .line 290
    invoke-virtual {v6, v1}, Landroid/content/Context;->getColor(I)I

    .line 291
    .line 292
    .line 293
    move-result v1

    .line 294
    const v3, 0x7f060972

    .line 295
    .line 296
    .line 297
    invoke-virtual {v6, v3}, Landroid/content/Context;->getColor(I)I

    .line 298
    .line 299
    .line 300
    move-result v3

    .line 301
    new-instance v4, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda0;

    .line 302
    .line 303
    invoke-direct {v4, p0, v1, v3}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;II)V

    .line 304
    .line 305
    .line 306
    iput-object v4, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mCrossWindowBlurEnabledListener:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda0;

    .line 307
    .line 308
    :cond_2
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->initDimens()V

    .line 309
    .line 310
    .line 311
    invoke-virtual {v6}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 312
    .line 313
    .line 314
    move-result-object v1

    .line 315
    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 316
    .line 317
    .line 318
    move-result-object v1

    .line 319
    iget v1, v1, Landroid/content/res/Configuration;->orientation:I

    .line 320
    .line 321
    iput v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mOrientation:I

    .line 322
    .line 323
    iput-object v2, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureController:Lcom/android/systemui/statusbar/policy/DevicePostureController;

    .line 324
    .line 325
    if-eqz v2, :cond_3

    .line 326
    .line 327
    move-object v1, v2

    .line 328
    check-cast v1, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;

    .line 329
    .line 330
    iget v1, v1, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;->mCurrentDevicePosture:I

    .line 331
    .line 332
    iput v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePosture:I

    .line 333
    .line 334
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

    .line 335
    .line 336
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 337
    .line 338
    .line 339
    iput-object v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureControllerCallback:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

    .line 340
    .line 341
    goto :goto_1

    .line 342
    :cond_3
    iput-object v5, v0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureControllerCallback:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

    .line 343
    .line 344
    :goto_1
    return-void

    :array_0
    .array-data 4
        0x0
        0x3f800000    # 1.0f
    .end array-data

    :array_1
    .array-data 4
        0x3f800000    # 1.0f
        0x0
    .end array-data
.end method

.method public static getImpliedLevel(Landroid/widget/SeekBar;I)I
    .locals 2

    .line 1
    invoke-virtual {p0}, Landroid/widget/SeekBar;->getMax()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    div-int/lit8 v0, p0, 0x64

    .line 6
    .line 7
    add-int/lit8 v1, v0, -0x1

    .line 8
    .line 9
    if-nez p1, :cond_0

    .line 10
    .line 11
    const/4 v0, 0x0

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    if-ne p1, p0, :cond_1

    .line 14
    .line 15
    goto :goto_0

    .line 16
    :cond_1
    int-to-float p1, p1

    .line 17
    int-to-float p0, p0

    .line 18
    div-float/2addr p1, p0

    .line 19
    int-to-float p0, v1

    .line 20
    mul-float/2addr p1, p0

    .line 21
    float-to-int p0, p1

    .line 22
    add-int/lit8 v0, p0, 0x1

    .line 23
    .line 24
    :goto_0
    return v0
.end method


# virtual methods
.method public final addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    if-eqz p2, :cond_1

    .line 4
    .line 5
    const/4 v1, 0x1

    .line 6
    if-eq p2, v1, :cond_0

    .line 7
    .line 8
    const p2, 0x7f131221

    .line 9
    .line 10
    .line 11
    goto :goto_0

    .line 12
    :cond_0
    const p2, 0x7f131223

    .line 13
    .line 14
    .line 15
    goto :goto_0

    .line 16
    :cond_1
    const p2, 0x7f131222

    .line 17
    .line 18
    .line 19
    :goto_0
    invoke-virtual {v0, p2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 20
    .line 21
    .line 22
    move-result-object p2

    .line 23
    invoke-virtual {p1, p2}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 24
    .line 25
    .line 26
    new-instance p2, Lcom/android/systemui/volume/VolumeDialogImpl$4;

    .line 27
    .line 28
    invoke-direct {p2, p0, p3}, Lcom/android/systemui/volume/VolumeDialogImpl$4;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Ljava/lang/String;)V

    .line 29
    .line 30
    .line 31
    invoke-virtual {p1, p2}, Landroid/view/View;->setAccessibilityDelegate(Landroid/view/View$AccessibilityDelegate;)V

    .line 32
    .line 33
    .line 34
    return-void
.end method

.method public final addRow(IIIZZ)V
    .locals 9

    .line 2
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Adding row for stream "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 3
    :cond_0
    new-instance v0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;-><init>(I)V

    move-object v2, p0

    move-object v3, v0

    move v4, p1

    move v5, p2

    move v6, p3

    move v7, p4

    move v8, p5

    .line 4
    invoke-virtual/range {v2 .. v8}, Lcom/android/systemui/volume/VolumeDialogImpl;->initRow(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;IIIZZ)V

    .line 5
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    iget-object p2, v0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    invoke-virtual {p1, p2}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 6
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    check-cast p0, Ljava/util/ArrayList;

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method public final addRow(IIZZI)V
    .locals 6

    move-object v0, p0

    move v1, p1

    move v2, p2

    move v3, p5

    move v4, p3

    move v5, p4

    .line 1
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIIZZ)V

    return-void
.end method

.method public final checkODICaptionsTooltip(Z)V
    .locals 2

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHasSeenODICaptionsTooltip:Z

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    if-nez p1, :cond_0

    .line 6
    .line 7
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipViewStub:Landroid/view/ViewStub;

    .line 8
    .line 9
    if-eqz v1, :cond_0

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 12
    .line 13
    const/4 p1, 0x1

    .line 14
    invoke-interface {p0, p1}, Lcom/android/systemui/plugins/VolumeDialogController;->getCaptionsComponentState(Z)V

    .line 15
    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_0
    if-eqz v0, :cond_1

    .line 19
    .line 20
    if-eqz p1, :cond_1

    .line 21
    .line 22
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 23
    .line 24
    if-eqz p1, :cond_1

    .line 25
    .line 26
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->hideCaptionsTooltip()V

    .line 27
    .line 28
    .line 29
    :cond_1
    :goto_0
    return-void
.end method

.method public clearInternalHandlerAfterTest()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 2
    .line 3
    if-eqz p0, :cond_0

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    invoke-virtual {p0, v0}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 7
    .line 8
    .line 9
    :cond_0
    return-void
.end method

.method public final destroy()V
    .locals 2

    .line 1
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 2
    .line 3
    const-string v1, "destroy() called"

    .line 4
    .line 5
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 9
    .line 10
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mControllerCallbackH:Lcom/android/systemui/volume/VolumeDialogImpl$7;

    .line 11
    .line 12
    invoke-interface {v0, v1}, Lcom/android/systemui/plugins/VolumeDialogController;->removeCallback(Lcom/android/systemui/plugins/VolumeDialogController$Callbacks;)V

    .line 13
    .line 14
    .line 15
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 16
    .line 17
    const/4 v1, 0x0

    .line 18
    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 19
    .line 20
    .line 21
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 22
    .line 23
    check-cast v0, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;

    .line 24
    .line 25
    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;->removeCallback(Ljava/lang/Object;)V

    .line 26
    .line 27
    .line 28
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureController:Lcom/android/systemui/statusbar/policy/DevicePostureController;

    .line 29
    .line 30
    if-eqz v0, :cond_0

    .line 31
    .line 32
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureControllerCallback:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

    .line 33
    .line 34
    check-cast v0, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;

    .line 35
    .line 36
    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;->removeCallback(Ljava/lang/Object;)V

    .line 37
    .line 38
    .line 39
    :cond_0
    return-void
.end method

.method public final dismissH(I)V
    .locals 9

    .line 1
    const-string v0, "VolumeDialogImpl#dismissH"

    .line 2
    .line 3
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    .line 4
    .line 5
    .line 6
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 7
    .line 8
    new-instance v1, Ljava/lang/StringBuilder;

    .line 9
    .line 10
    const-string v2, "mDialog.dismiss() reason: "

    .line 11
    .line 12
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 13
    .line 14
    .line 15
    sget-object v2, Lcom/android/systemui/volume/Events;->DISMISS_REASONS:[Ljava/lang/String;

    .line 16
    .line 17
    aget-object v2, v2, p1

    .line 18
    .line 19
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 20
    .line 21
    .line 22
    const-string v2, " from: "

    .line 23
    .line 24
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 25
    .line 26
    .line 27
    invoke-static {}, Landroid/os/Debug;->getCaller()Ljava/lang/String;

    .line 28
    .line 29
    .line 30
    move-result-object v2

    .line 31
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 32
    .line 33
    .line 34
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 35
    .line 36
    .line 37
    move-result-object v1

    .line 38
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    .line 40
    .line 41
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 42
    .line 43
    const/4 v2, 0x2

    .line 44
    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeMessages(I)V

    .line 45
    .line 46
    .line 47
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 48
    .line 49
    const/4 v2, 0x1

    .line 50
    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeMessages(I)V

    .line 51
    .line 52
    .line 53
    iget-boolean v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 54
    .line 55
    const/4 v3, 0x0

    .line 56
    if-nez v1, :cond_0

    .line 57
    .line 58
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 59
    .line 60
    if-eqz v1, :cond_0

    .line 61
    .line 62
    invoke-virtual {v1}, Landroid/app/Dialog;->isShowing()Z

    .line 63
    .line 64
    .line 65
    move-result v1

    .line 66
    if-eqz v1, :cond_0

    .line 67
    .line 68
    move v1, v2

    .line 69
    goto :goto_0

    .line 70
    :cond_0
    move v1, v3

    .line 71
    :goto_0
    if-eqz v1, :cond_2

    .line 72
    .line 73
    new-instance v4, Ljava/lang/StringBuilder;

    .line 74
    .line 75
    const-string v5, "dismissH: volume dialog possible in inconsistent state:mShowing="

    .line 76
    .line 77
    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 78
    .line 79
    .line 80
    iget-boolean v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 81
    .line 82
    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 83
    .line 84
    .line 85
    const-string v5, ", mDialog==null?"

    .line 86
    .line 87
    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 88
    .line 89
    .line 90
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 91
    .line 92
    if-nez v5, :cond_1

    .line 93
    .line 94
    move v5, v2

    .line 95
    goto :goto_1

    .line 96
    :cond_1
    move v5, v3

    .line 97
    :goto_1
    invoke-static {v4, v5, v0}, Landroidx/appcompat/widget/ActionBarContextView$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ZLjava/lang/String;)V

    .line 98
    .line 99
    .line 100
    :cond_2
    iget-boolean v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsAnimatingDismiss:Z

    .line 101
    .line 102
    if-eqz v4, :cond_3

    .line 103
    .line 104
    if-nez v1, :cond_3

    .line 105
    .line 106
    const-string p0, "dismissH: skipping dismiss because isAnimatingDismiss is true and showingStateInconsistent is false"

    .line 107
    .line 108
    invoke-static {v0, p0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 109
    .line 110
    .line 111
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 112
    .line 113
    .line 114
    return-void

    .line 115
    :cond_3
    iput-boolean v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsAnimatingDismiss:Z

    .line 116
    .line 117
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 118
    .line 119
    invoke-virtual {v1}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 120
    .line 121
    .line 122
    move-result-object v1

    .line 123
    invoke-virtual {v1}, Landroid/view/ViewPropertyAnimator;->cancel()V

    .line 124
    .line 125
    .line 126
    iget-boolean v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 127
    .line 128
    if-eqz v1, :cond_4

    .line 129
    .line 130
    iput-boolean v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 131
    .line 132
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 133
    .line 134
    .line 135
    move-result-object p1

    .line 136
    filled-new-array {p1}, [Ljava/lang/Object;

    .line 137
    .line 138
    .line 139
    move-result-object p1

    .line 140
    invoke-static {v2, p1}, Lcom/android/systemui/volume/Events;->writeEvent(I[Ljava/lang/Object;)V

    .line 141
    .line 142
    .line 143
    :cond_4
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 144
    .line 145
    const/4 v1, 0x0

    .line 146
    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setTranslationX(F)V

    .line 147
    .line 148
    .line 149
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 150
    .line 151
    const/high16 v4, 0x3f800000    # 1.0f

    .line 152
    .line 153
    invoke-virtual {p1, v4}, Landroid/view/ViewGroup;->setAlpha(F)V

    .line 154
    .line 155
    .line 156
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 157
    .line 158
    invoke-virtual {p1}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 159
    .line 160
    .line 161
    move-result-object p1

    .line 162
    invoke-virtual {p1, v1}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    .line 163
    .line 164
    .line 165
    move-result-object p1

    .line 166
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogHideAnimationDurationMs:I

    .line 167
    .line 168
    int-to-long v4, v1

    .line 169
    invoke-virtual {p1, v4, v5}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    .line 170
    .line 171
    .line 172
    move-result-object p1

    .line 173
    new-instance v1, Lcom/android/systemui/volume/SystemUIInterpolators$LogAccelerateInterpolator;

    .line 174
    .line 175
    invoke-direct {v1}, Lcom/android/systemui/volume/SystemUIInterpolators$LogAccelerateInterpolator;-><init>()V

    .line 176
    .line 177
    .line 178
    invoke-virtual {p1, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    .line 179
    .line 180
    .line 181
    move-result-object p1

    .line 182
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;

    .line 183
    .line 184
    const/4 v4, 0x4

    .line 185
    invoke-direct {v1, p0, v4}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 186
    .line 187
    .line 188
    invoke-virtual {p1, v1}, Landroid/view/ViewPropertyAnimator;->withEndAction(Ljava/lang/Runnable;)Landroid/view/ViewPropertyAnimator;

    .line 189
    .line 190
    .line 191
    move-result-object p1

    .line 192
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 193
    .line 194
    invoke-virtual {v1}, Landroid/content/Context;->getDisplay()Landroid/view/Display;

    .line 195
    .line 196
    .line 197
    move-result-object v1

    .line 198
    invoke-virtual {v1}, Landroid/view/Display;->getRotation()I

    .line 199
    .line 200
    .line 201
    move-result v1

    .line 202
    if-eqz v1, :cond_5

    .line 203
    .line 204
    move v1, v2

    .line 205
    goto :goto_2

    .line 206
    :cond_5
    move v1, v3

    .line 207
    :goto_2
    if-nez v1, :cond_8

    .line 208
    .line 209
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindowGravity:I

    .line 210
    .line 211
    const/4 v4, 0x3

    .line 212
    and-int/2addr v1, v4

    .line 213
    if-ne v1, v4, :cond_6

    .line 214
    .line 215
    move v3, v2

    .line 216
    :cond_6
    if-eqz v3, :cond_7

    .line 217
    .line 218
    const/4 v1, -0x1

    .line 219
    goto :goto_3

    .line 220
    :cond_7
    move v1, v2

    .line 221
    :goto_3
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 222
    .line 223
    invoke-virtual {v3}, Landroid/view/ViewGroup;->getWidth()I

    .line 224
    .line 225
    .line 226
    move-result v3

    .line 227
    mul-int/2addr v3, v1

    .line 228
    int-to-float v1, v3

    .line 229
    const/high16 v3, 0x40000000    # 2.0f

    .line 230
    .line 231
    div-float/2addr v1, v3

    .line 232
    invoke-virtual {p1, v1}, Landroid/view/ViewPropertyAnimator;->translationX(F)Landroid/view/ViewPropertyAnimator;

    .line 233
    .line 234
    .line 235
    :cond_8
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 236
    .line 237
    const-string v6, "dismiss"

    .line 238
    .line 239
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogHideAnimationDurationMs:I

    .line 240
    .line 241
    int-to-long v7, v1

    .line 242
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$3;

    .line 243
    .line 244
    move-object v3, v1

    .line 245
    move-object v4, p0

    .line 246
    invoke-direct/range {v3 .. v8}, Lcom/android/systemui/volume/VolumeDialogImpl$3;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Landroid/view/View;Ljava/lang/String;J)V

    .line 247
    .line 248
    .line 249
    invoke-virtual {p1, v1}, Landroid/view/ViewPropertyAnimator;->setListener(Landroid/animation/Animator$AnimatorListener;)Landroid/view/ViewPropertyAnimator;

    .line 250
    .line 251
    .line 252
    move-result-object p1

    .line 253
    invoke-virtual {p1}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 254
    .line 255
    .line 256
    invoke-virtual {p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->checkODICaptionsTooltip(Z)V

    .line 257
    .line 258
    .line 259
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarningLock:Ljava/lang/Object;

    .line 260
    .line 261
    monitor-enter p1

    .line 262
    :try_start_0
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarning:Lcom/android/systemui/volume/SafetyWarningDialog;

    .line 263
    .line 264
    if-eqz v1, :cond_a

    .line 265
    .line 266
    sget-boolean v1, Lcom/android/systemui/volume/D;->BUG:Z

    .line 267
    .line 268
    if-eqz v1, :cond_9

    .line 269
    .line 270
    const-string v1, "SafetyWarning dismissed"

    .line 271
    .line 272
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 273
    .line 274
    .line 275
    :cond_9
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarning:Lcom/android/systemui/volume/SafetyWarningDialog;

    .line 276
    .line 277
    invoke-virtual {p0}, Landroid/app/AlertDialog;->dismiss()V

    .line 278
    .line 279
    .line 280
    :cond_a
    monitor-exit p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 281
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 282
    .line 283
    .line 284
    return-void

    .line 285
    :catchall_0
    move-exception p0

    .line 286
    :try_start_1
    monitor-exit p1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 287
    throw p0
.end method

.method public final dump(Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 0

    .line 1
    const-class p2, Lcom/android/systemui/volume/VolumeDialogImpl;

    .line 2
    .line 3
    const-string p2, "VolumeDialogImpl state:"

    .line 4
    .line 5
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    const-string p2, "  mShowing: "

    .line 9
    .line 10
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 11
    .line 12
    .line 13
    iget-boolean p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 14
    .line 15
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Z)V

    .line 16
    .line 17
    .line 18
    const-string p2, "  mIsAnimatingDismiss: "

    .line 19
    .line 20
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 21
    .line 22
    .line 23
    iget-boolean p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsAnimatingDismiss:Z

    .line 24
    .line 25
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Z)V

    .line 26
    .line 27
    .line 28
    const-string p2, "  mActiveStream: "

    .line 29
    .line 30
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 31
    .line 32
    .line 33
    iget p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActiveStream:I

    .line 34
    .line 35
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(I)V

    .line 36
    .line 37
    .line 38
    const-string p2, "  mDynamic: "

    .line 39
    .line 40
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 41
    .line 42
    .line 43
    iget-object p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 44
    .line 45
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 46
    .line 47
    .line 48
    const-string p2, "  mAutomute: "

    .line 49
    .line 50
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 51
    .line 52
    .line 53
    iget-boolean p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAutomute:Z

    .line 54
    .line 55
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Z)V

    .line 56
    .line 57
    .line 58
    const-string p2, "  mSilentMode: "

    .line 59
    .line 60
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 61
    .line 62
    .line 63
    iget-boolean p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSilentMode:Z

    .line 64
    .line 65
    invoke-virtual {p1, p0}, Ljava/io/PrintWriter;->println(Z)V

    .line 66
    .line 67
    .line 68
    return-void
.end method

.method public final getActiveRow()Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 2
    .line 3
    check-cast v0, Ljava/util/ArrayList;

    .line 4
    .line 5
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    :cond_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 10
    .line 11
    .line 12
    move-result v1

    .line 13
    if-eqz v1, :cond_1

    .line 14
    .line 15
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 16
    .line 17
    .line 18
    move-result-object v1

    .line 19
    check-cast v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 20
    .line 21
    iget v2, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 22
    .line 23
    iget v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActiveStream:I

    .line 24
    .line 25
    if-ne v2, v3, :cond_0

    .line 26
    .line 27
    return-object v1

    .line 28
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 29
    .line 30
    check-cast v0, Ljava/util/ArrayList;

    .line 31
    .line 32
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 33
    .line 34
    .line 35
    move-result-object v0

    .line 36
    :cond_2
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 37
    .line 38
    .line 39
    move-result v1

    .line 40
    if-eqz v1, :cond_3

    .line 41
    .line 42
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 43
    .line 44
    .line 45
    move-result-object v1

    .line 46
    check-cast v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 47
    .line 48
    iget v2, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 49
    .line 50
    const/4 v3, 0x3

    .line 51
    if-ne v2, v3, :cond_2

    .line 52
    .line 53
    return-object v1

    .line 54
    :cond_3
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 55
    .line 56
    const/4 v0, 0x0

    .line 57
    check-cast p0, Ljava/util/ArrayList;

    .line 58
    .line 59
    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 60
    .line 61
    .line 62
    move-result-object p0

    .line 63
    check-cast p0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 64
    .line 65
    return-object p0
.end method

.method public final getDrawerIconViewForMode(I)Landroid/widget/ImageView;
    .locals 1

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p1, v0, :cond_0

    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerVibrateIcon:Landroid/widget/ImageView;

    .line 5
    .line 6
    return-object p0

    .line 7
    :cond_0
    if-nez p1, :cond_1

    .line 8
    .line 9
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerMuteIcon:Landroid/widget/ImageView;

    .line 10
    .line 11
    return-object p0

    .line 12
    :cond_1
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNormalIcon:Landroid/widget/ImageView;

    .line 13
    .line 14
    return-object p0
.end method

.method public final getRingerDrawerOpenExtraSize()I
    .locals 1

    .line 1
    iget v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerCount:I

    .line 2
    .line 3
    add-int/lit8 v0, v0, -0x1

    .line 4
    .line 5
    iget p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 6
    .line 7
    mul-int/2addr v0, p0

    .line 8
    return v0
.end method

.method public final getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;
    .locals 3

    .line 1
    const-string v0, ""

    .line 2
    .line 3
    if-nez p1, :cond_0

    .line 4
    .line 5
    return-object v0

    .line 6
    :cond_0
    iget-object v1, p1, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->remoteLabel:Ljava/lang/String;

    .line 7
    .line 8
    if-eqz v1, :cond_1

    .line 9
    .line 10
    return-object v1

    .line 11
    :cond_1
    :try_start_0
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 12
    .line 13
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    iget v1, p1, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->name:I

    .line 18
    .line 19
    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 20
    .line 21
    .line 22
    move-result-object p0
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 23
    return-object p0

    .line 24
    :catch_0
    sget-object p0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 25
    .line 26
    new-instance v1, Ljava/lang/StringBuilder;

    .line 27
    .line 28
    const-string v2, "Can\'t find translation for stream "

    .line 29
    .line 30
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 31
    .line 32
    .line 33
    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 34
    .line 35
    .line 36
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 37
    .line 38
    .line 39
    move-result-object p1

    .line 40
    invoke-static {p0, p1}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 41
    .line 42
    .line 43
    return-object v0
.end method

.method public final getTranslationInDrawerForRingerMode(I)F
    .locals 1

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p1, v0, :cond_0

    .line 3
    .line 4
    iget p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 5
    .line 6
    neg-int p0, p0

    .line 7
    mul-int/lit8 p0, p0, 0x2

    .line 8
    .line 9
    :goto_0
    int-to-float p0, p0

    .line 10
    goto :goto_1

    .line 11
    :cond_0
    if-nez p1, :cond_1

    .line 12
    .line 13
    iget p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 14
    .line 15
    neg-int p0, p0

    .line 16
    goto :goto_0

    .line 17
    :cond_1
    const/4 p0, 0x0

    .line 18
    :goto_1
    return p0
.end method

.method public final getVisibleRowsExtraSize()I
    .locals 4

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getActiveRow()Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 6
    .line 7
    check-cast v1, Ljava/util/ArrayList;

    .line 8
    .line 9
    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 10
    .line 11
    .line 12
    move-result-object v1

    .line 13
    const/4 v2, 0x0

    .line 14
    :cond_0
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    .line 15
    .line 16
    .line 17
    move-result v3

    .line 18
    if-eqz v3, :cond_1

    .line 19
    .line 20
    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 21
    .line 22
    .line 23
    move-result-object v3

    .line 24
    check-cast v3, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 25
    .line 26
    invoke-virtual {p0, v3, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->shouldBeVisibleH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)Z

    .line 27
    .line 28
    .line 29
    move-result v3

    .line 30
    if-eqz v3, :cond_0

    .line 31
    .line 32
    add-int/lit8 v2, v2, 0x1

    .line 33
    .line 34
    goto :goto_0

    .line 35
    :cond_1
    add-int/lit8 v2, v2, -0x1

    .line 36
    .line 37
    iget v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogWidth:I

    .line 38
    .line 39
    iget p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerRowsPadding:I

    .line 40
    .line 41
    add-int/2addr v0, p0

    .line 42
    mul-int/2addr v0, v2

    .line 43
    return v0
.end method

.method public getWindowGravity()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindowGravity:I

    .line 2
    .line 3
    return p0
.end method

.method public final hideCaptionsTooltip()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-nez v0, :cond_0

    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 12
    .line 13
    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->cancel()V

    .line 18
    .line 19
    .line 20
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 21
    .line 22
    const/high16 v1, 0x3f800000    # 1.0f

    .line 23
    .line 24
    invoke-virtual {v0, v1}, Landroid/view/View;->setAlpha(F)V

    .line 25
    .line 26
    .line 27
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 28
    .line 29
    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    .line 30
    .line 31
    .line 32
    move-result-object v0

    .line 33
    const/4 v1, 0x0

    .line 34
    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    .line 35
    .line 36
    .line 37
    move-result-object v0

    .line 38
    const-wide/16 v1, 0x0

    .line 39
    .line 40
    invoke-virtual {v0, v1, v2}, Landroid/view/ViewPropertyAnimator;->setStartDelay(J)Landroid/view/ViewPropertyAnimator;

    .line 41
    .line 42
    .line 43
    move-result-object v0

    .line 44
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogHideAnimationDurationMs:I

    .line 45
    .line 46
    int-to-long v1, v1

    .line 47
    invoke-virtual {v0, v1, v2}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    .line 48
    .line 49
    .line 50
    move-result-object v0

    .line 51
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;

    .line 52
    .line 53
    const/4 v2, 0x2

    .line 54
    invoke-direct {v1, p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 55
    .line 56
    .line 57
    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->withEndAction(Ljava/lang/Runnable;)Landroid/view/ViewPropertyAnimator;

    .line 58
    .line 59
    .line 60
    move-result-object p0

    .line 61
    invoke-virtual {p0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 62
    .line 63
    .line 64
    :cond_0
    return-void
.end method

.method public final hideRingerDrawer()V
    .locals 6

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsRingerDrawerOpen:Z

    .line 7
    .line 8
    if-nez v0, :cond_1

    .line 9
    .line 10
    return-void

    .line 11
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 12
    .line 13
    iget v0, v0, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 14
    .line 15
    invoke-virtual {p0, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getDrawerIconViewForMode(I)Landroid/widget/ImageView;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    const/4 v1, 0x4

    .line 20
    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 21
    .line 22
    .line 23
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 24
    .line 25
    invoke-virtual {v0}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 26
    .line 27
    .line 28
    move-result-object v0

    .line 29
    const/4 v1, 0x0

    .line 30
    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    .line 31
    .line 32
    .line 33
    move-result-object v0

    .line 34
    const-wide/16 v2, 0xfa

    .line 35
    .line 36
    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    .line 37
    .line 38
    .line 39
    move-result-object v0

    .line 40
    const-wide/16 v4, 0x0

    .line 41
    .line 42
    invoke-virtual {v0, v4, v5}, Landroid/view/ViewPropertyAnimator;->setStartDelay(J)Landroid/view/ViewPropertyAnimator;

    .line 43
    .line 44
    .line 45
    move-result-object v0

    .line 46
    new-instance v4, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;

    .line 47
    .line 48
    const/4 v5, 0x7

    .line 49
    invoke-direct {v4, p0, v5}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 50
    .line 51
    .line 52
    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->withEndAction(Ljava/lang/Runnable;)Landroid/view/ViewPropertyAnimator;

    .line 53
    .line 54
    .line 55
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 56
    .line 57
    .line 58
    move-result v0

    .line 59
    if-nez v0, :cond_2

    .line 60
    .line 61
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 62
    .line 63
    invoke-virtual {v0}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 64
    .line 65
    .line 66
    move-result-object v0

    .line 67
    iget v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 68
    .line 69
    mul-int/lit8 v4, v4, 0x2

    .line 70
    .line 71
    int-to-float v4, v4

    .line 72
    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->translationY(F)Landroid/view/ViewPropertyAnimator;

    .line 73
    .line 74
    .line 75
    move-result-object v0

    .line 76
    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 77
    .line 78
    .line 79
    goto :goto_0

    .line 80
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 81
    .line 82
    invoke-virtual {v0}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 83
    .line 84
    .line 85
    move-result-object v0

    .line 86
    iget v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 87
    .line 88
    mul-int/lit8 v4, v4, 0x2

    .line 89
    .line 90
    int-to-float v4, v4

    .line 91
    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->translationX(F)Landroid/view/ViewPropertyAnimator;

    .line 92
    .line 93
    .line 94
    move-result-object v0

    .line 95
    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 96
    .line 97
    .line 98
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

    .line 99
    .line 100
    invoke-virtual {v0, v2, v3}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 101
    .line 102
    .line 103
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

    .line 104
    .line 105
    sget-object v2, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN_REVERSE:Landroid/view/animation/Interpolator;

    .line 106
    .line 107
    invoke-virtual {v0, v2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 108
    .line 109
    .line 110
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

    .line 111
    .line 112
    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->reverse()V

    .line 113
    .line 114
    .line 115
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerContainer:Landroid/view/ViewGroup;

    .line 116
    .line 117
    invoke-virtual {v0}, Landroid/view/ViewGroup;->animate()Landroid/view/ViewPropertyAnimator;

    .line 118
    .line 119
    .line 120
    move-result-object v0

    .line 121
    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->translationX(F)Landroid/view/ViewPropertyAnimator;

    .line 122
    .line 123
    .line 124
    move-result-object v0

    .line 125
    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->translationY(F)Landroid/view/ViewPropertyAnimator;

    .line 126
    .line 127
    .line 128
    move-result-object v0

    .line 129
    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 130
    .line 131
    .line 132
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerContainer:Landroid/view/ViewGroup;

    .line 133
    .line 134
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 135
    .line 136
    const v2, 0x7f13121d

    .line 137
    .line 138
    .line 139
    invoke-virtual {v1, v2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 140
    .line 141
    .line 142
    move-result-object v1

    .line 143
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 144
    .line 145
    .line 146
    const/4 v0, 0x0

    .line 147
    iput-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsRingerDrawerOpen:Z

    .line 148
    .line 149
    return-void
.end method

.method public final init(ILcom/android/systemui/plugins/VolumeDialog$Callback;)V
    .locals 1

    .line 1
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActivityManager:Landroid/app/ActivityManager;

    .line 2
    .line 3
    invoke-virtual {p1}, Landroid/app/ActivityManager;->getLockTaskModeState()I

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    invoke-virtual {p0, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->initDialog(I)V

    .line 8
    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 11
    .line 12
    iget-object p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mControllerCallbackH:Lcom/android/systemui/volume/VolumeDialogImpl$7;

    .line 13
    .line 14
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 15
    .line 16
    invoke-interface {p1, p2, v0}, Lcom/android/systemui/plugins/VolumeDialogController;->addCallback(Lcom/android/systemui/plugins/VolumeDialogController$Callbacks;Landroid/os/Handler;)V

    .line 17
    .line 18
    .line 19
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 20
    .line 21
    invoke-interface {p1}, Lcom/android/systemui/plugins/VolumeDialogController;->getState()V

    .line 22
    .line 23
    .line 24
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurationController:Lcom/android/systemui/statusbar/policy/ConfigurationController;

    .line 25
    .line 26
    check-cast p1, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;

    .line 27
    .line 28
    invoke-virtual {p1, p0}, Lcom/android/systemui/statusbar/phone/ConfigurationControllerImpl;->addCallback(Ljava/lang/Object;)V

    .line 29
    .line 30
    .line 31
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureController:Lcom/android/systemui/statusbar/policy/DevicePostureController;

    .line 32
    .line 33
    if-eqz p1, :cond_0

    .line 34
    .line 35
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePostureControllerCallback:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda1;

    .line 36
    .line 37
    check-cast p1, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;

    .line 38
    .line 39
    invoke-virtual {p1, p0}, Lcom/android/systemui/statusbar/policy/DevicePostureControllerImpl;->addCallback(Ljava/lang/Object;)V

    .line 40
    .line 41
    .line 42
    :cond_0
    return-void
.end method

.method public final initDialog(I)V
    .locals 12

    .line 1
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 2
    .line 3
    const-string v1, "initDialog: called!"

    .line 4
    .line 5
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    new-instance v0, Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 9
    .line 10
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 11
    .line 12
    invoke-direct {v0, p0, v1}, Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Landroid/content/Context;)V

    .line 13
    .line 14
    .line 15
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 16
    .line 17
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->initDimens()V

    .line 18
    .line 19
    .line 20
    new-instance v0, Lcom/android/systemui/volume/ConfigurableTexts;

    .line 21
    .line 22
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 23
    .line 24
    invoke-direct {v0, v1}, Lcom/android/systemui/volume/ConfigurableTexts;-><init>(Landroid/content/Context;)V

    .line 25
    .line 26
    .line 27
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurableTexts:Lcom/android/systemui/volume/ConfigurableTexts;

    .line 28
    .line 29
    const/4 v7, 0x0

    .line 30
    iput-boolean v7, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHovering:Z

    .line 31
    .line 32
    iput-boolean v7, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 33
    .line 34
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 35
    .line 36
    invoke-virtual {v0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    .line 37
    .line 38
    .line 39
    move-result-object v0

    .line 40
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 41
    .line 42
    const/4 v8, 0x1

    .line 43
    invoke-virtual {v0, v8}, Landroid/view/Window;->requestFeature(I)Z

    .line 44
    .line 45
    .line 46
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 47
    .line 48
    new-instance v1, Landroid/graphics/drawable/ColorDrawable;

    .line 49
    .line 50
    invoke-direct {v1, v7}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 51
    .line 52
    .line 53
    invoke-virtual {v0, v1}, Landroid/view/Window;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 54
    .line 55
    .line 56
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 57
    .line 58
    const v1, 0x10002

    .line 59
    .line 60
    .line 61
    invoke-virtual {v0, v1}, Landroid/view/Window;->clearFlags(I)V

    .line 62
    .line 63
    .line 64
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 65
    .line 66
    const v1, 0x10c0028

    .line 67
    .line 68
    .line 69
    invoke-virtual {v0, v1}, Landroid/view/Window;->addFlags(I)V

    .line 70
    .line 71
    .line 72
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 73
    .line 74
    const/high16 v1, 0x20000000

    .line 75
    .line 76
    invoke-virtual {v0, v1}, Landroid/view/Window;->addPrivateFlags(I)V

    .line 77
    .line 78
    .line 79
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 80
    .line 81
    const/16 v1, 0x7e4

    .line 82
    .line 83
    invoke-virtual {v0, v1}, Landroid/view/Window;->setType(I)V

    .line 84
    .line 85
    .line 86
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 87
    .line 88
    const v1, 0x1030004

    .line 89
    .line 90
    .line 91
    invoke-virtual {v0, v1}, Landroid/view/Window;->setWindowAnimations(I)V

    .line 92
    .line 93
    .line 94
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 95
    .line 96
    invoke-virtual {v0}, Landroid/view/Window;->getAttributes()Landroid/view/WindowManager$LayoutParams;

    .line 97
    .line 98
    .line 99
    move-result-object v0

    .line 100
    const/4 v1, -0x3

    .line 101
    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->format:I

    .line 102
    .line 103
    const-class v1, Lcom/android/systemui/volume/VolumeDialogImpl;

    .line 104
    .line 105
    const-string v1, "VolumeDialogImpl"

    .line 106
    .line 107
    invoke-virtual {v0, v1}, Landroid/view/WindowManager$LayoutParams;->setTitle(Ljava/lang/CharSequence;)V

    .line 108
    .line 109
    .line 110
    const/4 v1, -0x1

    .line 111
    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 112
    .line 113
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 114
    .line 115
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 116
    .line 117
    .line 118
    move-result-object v1

    .line 119
    const v2, 0x7f0b0113

    .line 120
    .line 121
    .line 122
    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getInteger(I)I

    .line 123
    .line 124
    .line 125
    move-result v1

    .line 126
    iput v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mOriginalGravity:I

    .line 127
    .line 128
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 129
    .line 130
    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 131
    .line 132
    .line 133
    move-result-object v2

    .line 134
    invoke-virtual {v2}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 135
    .line 136
    .line 137
    move-result-object v2

    .line 138
    invoke-virtual {v2}, Landroid/content/res/Configuration;->getLayoutDirection()I

    .line 139
    .line 140
    .line 141
    move-result v2

    .line 142
    invoke-static {v1, v2}, Landroid/view/Gravity;->getAbsoluteGravity(II)I

    .line 143
    .line 144
    .line 145
    move-result v1

    .line 146
    iput v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindowGravity:I

    .line 147
    .line 148
    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->gravity:I

    .line 149
    .line 150
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 151
    .line 152
    invoke-virtual {v1, v0}, Landroid/view/Window;->setAttributes(Landroid/view/WindowManager$LayoutParams;)V

    .line 153
    .line 154
    .line 155
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 156
    .line 157
    const/4 v1, -0x2

    .line 158
    invoke-virtual {v0, v1, v1}, Landroid/view/Window;->setLayout(II)V

    .line 159
    .line 160
    .line 161
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 162
    .line 163
    const v1, 0x7f0d0506

    .line 164
    .line 165
    .line 166
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->setContentView(I)V

    .line 167
    .line 168
    .line 169
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 170
    .line 171
    const v1, 0x7f0a0cd2

    .line 172
    .line 173
    .line 174
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 175
    .line 176
    .line 177
    move-result-object v0

    .line 178
    check-cast v0, Landroid/view/ViewGroup;

    .line 179
    .line 180
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 181
    .line 182
    const/4 v1, 0x0

    .line 183
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setAlpha(F)V

    .line 184
    .line 185
    .line 186
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 187
    .line 188
    invoke-virtual {v0, v8}, Landroid/app/Dialog;->setCanceledOnTouchOutside(Z)V

    .line 189
    .line 190
    .line 191
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 192
    .line 193
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda3;

    .line 194
    .line 195
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda3;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 196
    .line 197
    .line 198
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->setOnShowListener(Landroid/content/DialogInterface$OnShowListener;)V

    .line 199
    .line 200
    .line 201
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 202
    .line 203
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda4;

    .line 204
    .line 205
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda4;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 206
    .line 207
    .line 208
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 209
    .line 210
    .line 211
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 212
    .line 213
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda5;

    .line 214
    .line 215
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda5;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 216
    .line 217
    .line 218
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setOnHoverListener(Landroid/view/View$OnHoverListener;)V

    .line 219
    .line 220
    .line 221
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 222
    .line 223
    const v1, 0x7f0a0cd4

    .line 224
    .line 225
    .line 226
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 227
    .line 228
    .line 229
    move-result-object v0

    .line 230
    check-cast v0, Landroid/view/ViewGroup;

    .line 231
    .line 232
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 233
    .line 234
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mUseBackgroundBlur:Z

    .line 235
    .line 236
    if-eqz v0, :cond_0

    .line 237
    .line 238
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 239
    .line 240
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$1;

    .line 241
    .line 242
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$1;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 243
    .line 244
    .line 245
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->addOnAttachStateChangeListener(Landroid/view/View$OnAttachStateChangeListener;)V

    .line 246
    .line 247
    .line 248
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 249
    .line 250
    const v1, 0x7f0a0cd5

    .line 251
    .line 252
    .line 253
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 254
    .line 255
    .line 256
    move-result-object v0

    .line 257
    check-cast v0, Landroid/view/ViewGroup;

    .line 258
    .line 259
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsViewContainer:Landroid/view/ViewGroup;

    .line 260
    .line 261
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 262
    .line 263
    const v1, 0x7f0a0cd6

    .line 264
    .line 265
    .line 266
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 267
    .line 268
    .line 269
    move-result-object v0

    .line 270
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTopContainer:Landroid/view/View;

    .line 271
    .line 272
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 273
    .line 274
    const v1, 0x7f0a0d08

    .line 275
    .line 276
    .line 277
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 278
    .line 279
    .line 280
    move-result-object v0

    .line 281
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 282
    .line 283
    if-eqz v0, :cond_2

    .line 284
    .line 285
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 286
    .line 287
    .line 288
    move-result v0

    .line 289
    if-eqz v0, :cond_1

    .line 290
    .line 291
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 292
    .line 293
    invoke-virtual {v0}, Landroid/view/View;->getPaddingLeft()I

    .line 294
    .line 295
    .line 296
    move-result v1

    .line 297
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 298
    .line 299
    invoke-virtual {v2}, Landroid/view/View;->getPaddingTop()I

    .line 300
    .line 301
    .line 302
    move-result v2

    .line 303
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 304
    .line 305
    invoke-virtual {v3}, Landroid/view/View;->getPaddingRight()I

    .line 306
    .line 307
    .line 308
    move-result v3

    .line 309
    iget v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerRowsPadding:I

    .line 310
    .line 311
    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/view/View;->setPadding(IIII)V

    .line 312
    .line 313
    .line 314
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 315
    .line 316
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 317
    .line 318
    const v2, 0x7f08131b

    .line 319
    .line 320
    .line 321
    invoke-virtual {v1, v2}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 322
    .line 323
    .line 324
    move-result-object v1

    .line 325
    invoke-virtual {v0, v1}, Landroid/view/View;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 326
    .line 327
    .line 328
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainer:Landroid/view/View;

    .line 329
    .line 330
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;

    .line 331
    .line 332
    invoke-direct {v1, p0, v8}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 333
    .line 334
    .line 335
    invoke-virtual {v0, v1}, Landroid/view/View;->post(Ljava/lang/Runnable;)Z

    .line 336
    .line 337
    .line 338
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 339
    .line 340
    const v1, 0x7f0a08dd

    .line 341
    .line 342
    .line 343
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 344
    .line 345
    .line 346
    move-result-object v0

    .line 347
    check-cast v0, Landroid/view/ViewGroup;

    .line 348
    .line 349
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRinger:Landroid/view/ViewGroup;

    .line 350
    .line 351
    if-eqz v0, :cond_3

    .line 352
    .line 353
    const v1, 0x7f0a08de

    .line 354
    .line 355
    .line 356
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 357
    .line 358
    .line 359
    move-result-object v0

    .line 360
    check-cast v0, Landroid/widget/ImageButton;

    .line 361
    .line 362
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 363
    .line 364
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRinger:Landroid/view/ViewGroup;

    .line 365
    .line 366
    const v1, 0x7f0a0356

    .line 367
    .line 368
    .line 369
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 370
    .line 371
    .line 372
    move-result-object v0

    .line 373
    check-cast v0, Landroid/widget/FrameLayout;

    .line 374
    .line 375
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mZenIcon:Landroid/widget/FrameLayout;

    .line 376
    .line 377
    :cond_3
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 378
    .line 379
    const v1, 0x7f0a0cec

    .line 380
    .line 381
    .line 382
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 383
    .line 384
    .line 385
    move-result-object v0

    .line 386
    check-cast v0, Landroid/widget/ImageView;

    .line 387
    .line 388
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerIcon:Landroid/widget/ImageView;

    .line 389
    .line 390
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 391
    .line 392
    const v1, 0x7f0a0ced

    .line 393
    .line 394
    .line 395
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 396
    .line 397
    .line 398
    move-result-object v0

    .line 399
    check-cast v0, Landroid/view/ViewGroup;

    .line 400
    .line 401
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerContainer:Landroid/view/ViewGroup;

    .line 402
    .line 403
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 404
    .line 405
    const v1, 0x7f0a0cd8

    .line 406
    .line 407
    .line 408
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 409
    .line 410
    .line 411
    move-result-object v0

    .line 412
    check-cast v0, Landroid/view/ViewGroup;

    .line 413
    .line 414
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerMute:Landroid/view/ViewGroup;

    .line 415
    .line 416
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 417
    .line 418
    const v1, 0x7f0a0cda

    .line 419
    .line 420
    .line 421
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 422
    .line 423
    .line 424
    move-result-object v0

    .line 425
    check-cast v0, Landroid/view/ViewGroup;

    .line 426
    .line 427
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNormal:Landroid/view/ViewGroup;

    .line 428
    .line 429
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 430
    .line 431
    const v1, 0x7f0a0cde

    .line 432
    .line 433
    .line 434
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 435
    .line 436
    .line 437
    move-result-object v0

    .line 438
    check-cast v0, Landroid/view/ViewGroup;

    .line 439
    .line 440
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerVibrate:Landroid/view/ViewGroup;

    .line 441
    .line 442
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 443
    .line 444
    const v1, 0x7f0a0cd9

    .line 445
    .line 446
    .line 447
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 448
    .line 449
    .line 450
    move-result-object v0

    .line 451
    check-cast v0, Landroid/widget/ImageView;

    .line 452
    .line 453
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerMuteIcon:Landroid/widget/ImageView;

    .line 454
    .line 455
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 456
    .line 457
    const v1, 0x7f0a0cdf

    .line 458
    .line 459
    .line 460
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 461
    .line 462
    .line 463
    move-result-object v0

    .line 464
    check-cast v0, Landroid/widget/ImageView;

    .line 465
    .line 466
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerVibrateIcon:Landroid/widget/ImageView;

    .line 467
    .line 468
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 469
    .line 470
    const v1, 0x7f0a0cdb

    .line 471
    .line 472
    .line 473
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 474
    .line 475
    .line 476
    move-result-object v0

    .line 477
    check-cast v0, Landroid/widget/ImageView;

    .line 478
    .line 479
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNormalIcon:Landroid/widget/ImageView;

    .line 480
    .line 481
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 482
    .line 483
    const v1, 0x7f0a0cdd

    .line 484
    .line 485
    .line 486
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 487
    .line 488
    .line 489
    move-result-object v0

    .line 490
    check-cast v0, Landroid/view/ViewGroup;

    .line 491
    .line 492
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNewSelectionBg:Landroid/view/ViewGroup;

    .line 493
    .line 494
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerMuteIcon:Landroid/widget/ImageView;

    .line 495
    .line 496
    if-eqz v0, :cond_4

    .line 497
    .line 498
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 499
    .line 500
    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 501
    .line 502
    .line 503
    :cond_4
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNormalIcon:Landroid/widget/ImageView;

    .line 504
    .line 505
    if-eqz v0, :cond_5

    .line 506
    .line 507
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerIconDrawableId:I

    .line 508
    .line 509
    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 510
    .line 511
    .line 512
    :cond_5
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 513
    .line 514
    const v1, 0x7f0a0cd7

    .line 515
    .line 516
    .line 517
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 518
    .line 519
    .line 520
    move-result-object v0

    .line 521
    check-cast v0, Landroid/view/ViewGroup;

    .line 522
    .line 523
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 524
    .line 525
    if-nez v0, :cond_6

    .line 526
    .line 527
    goto/16 :goto_1

    .line 528
    .line 529
    :cond_6
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowVibrate:Z

    .line 530
    .line 531
    if-nez v0, :cond_7

    .line 532
    .line 533
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerVibrate:Landroid/view/ViewGroup;

    .line 534
    .line 535
    const/16 v1, 0x8

    .line 536
    .line 537
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 538
    .line 539
    .line 540
    :cond_7
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 541
    .line 542
    .line 543
    move-result v0

    .line 544
    if-nez v0, :cond_8

    .line 545
    .line 546
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 547
    .line 548
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getPaddingLeft()I

    .line 549
    .line 550
    .line 551
    move-result v1

    .line 552
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 553
    .line 554
    invoke-virtual {v2}, Landroid/view/ViewGroup;->getPaddingTop()I

    .line 555
    .line 556
    .line 557
    move-result v2

    .line 558
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 559
    .line 560
    invoke-virtual {v3}, Landroid/view/ViewGroup;->getPaddingRight()I

    .line 561
    .line 562
    .line 563
    move-result v3

    .line 564
    iget-object v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 565
    .line 566
    invoke-virtual {v4}, Landroid/view/ViewGroup;->getPaddingBottom()I

    .line 567
    .line 568
    .line 569
    move-result v4

    .line 570
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 571
    .line 572
    .line 573
    move-result v5

    .line 574
    add-int/2addr v5, v4

    .line 575
    invoke-virtual {v0, v1, v2, v3, v5}, Landroid/view/ViewGroup;->setPadding(IIII)V

    .line 576
    .line 577
    .line 578
    goto :goto_0

    .line 579
    :cond_8
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 580
    .line 581
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getPaddingLeft()I

    .line 582
    .line 583
    .line 584
    move-result v1

    .line 585
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 586
    .line 587
    .line 588
    move-result v2

    .line 589
    add-int/2addr v2, v1

    .line 590
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 591
    .line 592
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getPaddingTop()I

    .line 593
    .line 594
    .line 595
    move-result v1

    .line 596
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 597
    .line 598
    invoke-virtual {v3}, Landroid/view/ViewGroup;->getPaddingRight()I

    .line 599
    .line 600
    .line 601
    move-result v3

    .line 602
    iget-object v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 603
    .line 604
    invoke-virtual {v4}, Landroid/view/ViewGroup;->getPaddingBottom()I

    .line 605
    .line 606
    .line 607
    move-result v4

    .line 608
    invoke-virtual {v0, v2, v1, v3, v4}, Landroid/view/ViewGroup;->setPadding(IIII)V

    .line 609
    .line 610
    .line 611
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerContainer:Landroid/view/ViewGroup;

    .line 612
    .line 613
    const v1, 0x7f0a0cdc

    .line 614
    .line 615
    .line 616
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 617
    .line 618
    .line 619
    move-result-object v0

    .line 620
    check-cast v0, Landroid/widget/LinearLayout;

    .line 621
    .line 622
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 623
    .line 624
    .line 625
    move-result v1

    .line 626
    xor-int/2addr v1, v8

    .line 627
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 628
    .line 629
    .line 630
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerContainer:Landroid/view/ViewGroup;

    .line 631
    .line 632
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;

    .line 633
    .line 634
    const/4 v2, 0x2

    .line 635
    invoke-direct {v1, p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 636
    .line 637
    .line 638
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 639
    .line 640
    .line 641
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerVibrate:Landroid/view/ViewGroup;

    .line 642
    .line 643
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;

    .line 644
    .line 645
    invoke-direct {v1, p0, v8}, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 646
    .line 647
    .line 648
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 649
    .line 650
    .line 651
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerMute:Landroid/view/ViewGroup;

    .line 652
    .line 653
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;

    .line 654
    .line 655
    invoke-direct {v1, p0, v7}, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 656
    .line 657
    .line 658
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 659
    .line 660
    .line 661
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerNormal:Landroid/view/ViewGroup;

    .line 662
    .line 663
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;

    .line 664
    .line 665
    invoke-direct {v1, p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl$RingerDrawerItemClickListener;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 666
    .line 667
    .line 668
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 669
    .line 670
    .line 671
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 672
    .line 673
    const v1, 0x1010435

    .line 674
    .line 675
    .line 676
    invoke-static {v1, v0, v7}, Lcom/android/settingslib/Utils;->getColorAttrDefaultColor(ILandroid/content/Context;I)I

    .line 677
    .line 678
    .line 679
    move-result v0

    .line 680
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 681
    .line 682
    const v2, 0x10104e2

    .line 683
    .line 684
    .line 685
    invoke-static {v2, v1, v7}, Lcom/android/settingslib/Utils;->getColorAttrDefaultColor(ILandroid/content/Context;I)I

    .line 686
    .line 687
    .line 688
    move-result v1

    .line 689
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerIconColorAnimator:Landroid/animation/ValueAnimator;

    .line 690
    .line 691
    new-instance v3, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda7;

    .line 692
    .line 693
    invoke-direct {v3, p0, v1, v0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda7;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;II)V

    .line 694
    .line 695
    .line 696
    invoke-virtual {v2, v3}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 697
    .line 698
    .line 699
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerIconColorAnimator:Landroid/animation/ValueAnimator;

    .line 700
    .line 701
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$2;

    .line 702
    .line 703
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 704
    .line 705
    .line 706
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 707
    .line 708
    .line 709
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerIconColorAnimator:Landroid/animation/ValueAnimator;

    .line 710
    .line 711
    const-wide/16 v1, 0xaf

    .line 712
    .line 713
    invoke-virtual {v0, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 714
    .line 715
    .line 716
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAnimateUpBackgroundToMatchDrawer:Landroid/animation/ValueAnimator;

    .line 717
    .line 718
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda8;

    .line 719
    .line 720
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda8;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 721
    .line 722
    .line 723
    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 724
    .line 725
    .line 726
    :goto_1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 727
    .line 728
    const v1, 0x7f0a077e

    .line 729
    .line 730
    .line 731
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 732
    .line 733
    .line 734
    move-result-object v0

    .line 735
    check-cast v0, Landroid/view/ViewGroup;

    .line 736
    .line 737
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsView:Landroid/view/ViewGroup;

    .line 738
    .line 739
    if-eqz v0, :cond_9

    .line 740
    .line 741
    const v1, 0x7f0a077f

    .line 742
    .line 743
    .line 744
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 745
    .line 746
    .line 747
    move-result-object v0

    .line 748
    check-cast v0, Lcom/android/systemui/volume/CaptionsToggleImageButton;

    .line 749
    .line 750
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsIcon:Lcom/android/systemui/volume/CaptionsToggleImageButton;

    .line 751
    .line 752
    :cond_9
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 753
    .line 754
    const v1, 0x7f0a0780

    .line 755
    .line 756
    .line 757
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 758
    .line 759
    .line 760
    move-result-object v0

    .line 761
    check-cast v0, Landroid/view/ViewStub;

    .line 762
    .line 763
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipViewStub:Landroid/view/ViewStub;

    .line 764
    .line 765
    iget-boolean v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHasSeenODICaptionsTooltip:Z

    .line 766
    .line 767
    if-eqz v1, :cond_a

    .line 768
    .line 769
    if-eqz v0, :cond_a

    .line 770
    .line 771
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 772
    .line 773
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 774
    .line 775
    .line 776
    const/4 v0, 0x0

    .line 777
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipViewStub:Landroid/view/ViewStub;

    .line 778
    .line 779
    :cond_a
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 780
    .line 781
    const v1, 0x7f0a0a13

    .line 782
    .line 783
    .line 784
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 785
    .line 786
    .line 787
    move-result-object v0

    .line 788
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSettingsView:Landroid/view/View;

    .line 789
    .line 790
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 791
    .line 792
    const v1, 0x7f0a0a0e

    .line 793
    .line 794
    .line 795
    invoke-virtual {v0, v1}, Landroid/app/Dialog;->findViewById(I)Landroid/view/View;

    .line 796
    .line 797
    .line 798
    move-result-object v0

    .line 799
    check-cast v0, Landroid/widget/ImageButton;

    .line 800
    .line 801
    iput-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSettingsIcon:Landroid/widget/ImageButton;

    .line 802
    .line 803
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 804
    .line 805
    check-cast v0, Ljava/util/ArrayList;

    .line 806
    .line 807
    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    .line 808
    .line 809
    .line 810
    move-result v0

    .line 811
    if-eqz v0, :cond_c

    .line 812
    .line 813
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 814
    .line 815
    invoke-static {v0}, Landroid/media/AudioSystem;->isSingleVolume(Landroid/content/Context;)Z

    .line 816
    .line 817
    .line 818
    move-result v0

    .line 819
    if-nez v0, :cond_b

    .line 820
    .line 821
    const/16 v1, 0xa

    .line 822
    .line 823
    const v2, 0x7f080b01

    .line 824
    .line 825
    .line 826
    const v5, 0x7f080b01

    .line 827
    .line 828
    .line 829
    const/4 v3, 0x1

    .line 830
    const/4 v4, 0x0

    .line 831
    move-object v0, p0

    .line 832
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 833
    .line 834
    .line 835
    :cond_b
    const/4 v1, 0x3

    .line 836
    const v2, 0x7f080b08

    .line 837
    .line 838
    .line 839
    const v5, 0x7f080b0c

    .line 840
    .line 841
    .line 842
    const/4 v3, 0x1

    .line 843
    const/4 v4, 0x1

    .line 844
    move-object v0, p0

    .line 845
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 846
    .line 847
    .line 848
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 849
    .line 850
    invoke-static {v0}, Landroid/media/AudioSystem;->isSingleVolume(Landroid/content/Context;)Z

    .line 851
    .line 852
    .line 853
    move-result v0

    .line 854
    if-nez v0, :cond_d

    .line 855
    .line 856
    const/4 v1, 0x2

    .line 857
    const v2, 0x7f080a6c

    .line 858
    .line 859
    .line 860
    const v5, 0x7f080a6d

    .line 861
    .line 862
    .line 863
    const/4 v6, 0x1

    .line 864
    const/4 v9, 0x0

    .line 865
    const/4 v3, 0x1

    .line 866
    const/4 v4, 0x0

    .line 867
    move-object v0, p0

    .line 868
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 869
    .line 870
    .line 871
    const/4 v1, 0x4

    .line 872
    const v2, 0x7f0807eb

    .line 873
    .line 874
    .line 875
    const v5, 0x7f080b03

    .line 876
    .line 877
    .line 878
    move v3, v6

    .line 879
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 880
    .line 881
    .line 882
    const/4 v1, 0x0

    .line 883
    const v2, 0x108055e

    .line 884
    .line 885
    .line 886
    const v5, 0x108055e

    .line 887
    .line 888
    .line 889
    const/4 v6, 0x0

    .line 890
    const/4 v3, 0x0

    .line 891
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 892
    .line 893
    .line 894
    const/4 v1, 0x6

    .line 895
    const v2, 0x7f080b04

    .line 896
    .line 897
    .line 898
    const v5, 0x7f080b04

    .line 899
    .line 900
    .line 901
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 902
    .line 903
    .line 904
    const/4 v1, 0x1

    .line 905
    const v2, 0x7f080b19

    .line 906
    .line 907
    .line 908
    const v5, 0x7f080b1a

    .line 909
    .line 910
    .line 911
    move v3, v6

    .line 912
    move v4, v9

    .line 913
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIZZI)V

    .line 914
    .line 915
    .line 916
    goto :goto_3

    .line 917
    :cond_c
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 918
    .line 919
    check-cast v0, Ljava/util/ArrayList;

    .line 920
    .line 921
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 922
    .line 923
    .line 924
    move-result v9

    .line 925
    move v10, v7

    .line 926
    :goto_2
    if-ge v10, v9, :cond_d

    .line 927
    .line 928
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 929
    .line 930
    check-cast v0, Ljava/util/ArrayList;

    .line 931
    .line 932
    invoke-virtual {v0, v10}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 933
    .line 934
    .line 935
    move-result-object v0

    .line 936
    move-object v11, v0

    .line 937
    check-cast v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 938
    .line 939
    iget v2, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 940
    .line 941
    iget v3, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconRes:I

    .line 942
    .line 943
    iget v4, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconMuteRes:I

    .line 944
    .line 945
    iget-boolean v5, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->important:Z

    .line 946
    .line 947
    iget-boolean v6, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->defaultStream:Z

    .line 948
    .line 949
    move-object v0, p0

    .line 950
    move-object v1, v11

    .line 951
    invoke-virtual/range {v0 .. v6}, Lcom/android/systemui/volume/VolumeDialogImpl;->initRow(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;IIIZZ)V

    .line 952
    .line 953
    .line 954
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 955
    .line 956
    iget-object v1, v11, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 957
    .line 958
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 959
    .line 960
    .line 961
    invoke-virtual {p0, v11}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 962
    .line 963
    .line 964
    add-int/lit8 v10, v10, 0x1

    .line 965
    .line 966
    goto :goto_2

    .line 967
    :cond_d
    :goto_3
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getActiveRow()Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 968
    .line 969
    .line 970
    move-result-object v0

    .line 971
    invoke-virtual {p0, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateRowsH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 972
    .line 973
    .line 974
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 975
    .line 976
    if-eqz v0, :cond_e

    .line 977
    .line 978
    invoke-virtual {v0, v8}, Landroid/widget/ImageButton;->setAccessibilityLiveRegion(I)V

    .line 979
    .line 980
    .line 981
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 982
    .line 983
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;

    .line 984
    .line 985
    invoke-direct {v1, p0, v7}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 986
    .line 987
    .line 988
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 989
    .line 990
    .line 991
    :cond_e
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateRingerH()V

    .line 992
    .line 993
    .line 994
    invoke-virtual {p0, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->initSettingsH(I)V

    .line 995
    .line 996
    .line 997
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsIcon:Lcom/android/systemui/volume/CaptionsToggleImageButton;

    .line 998
    .line 999
    if-eqz v0, :cond_f

    .line 1000
    .line 1001
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda9;

    .line 1002
    .line 1003
    invoke-direct {v1, p0}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda9;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;)V

    .line 1004
    .line 1005
    .line 1006
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 1007
    .line 1008
    iput-object v1, v0, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mConfirmedTapListener:Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda9;

    .line 1009
    .line 1010
    iget-object v1, v0, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mGestureDetector:Landroid/view/GestureDetector;

    .line 1011
    .line 1012
    if-nez v1, :cond_f

    .line 1013
    .line 1014
    new-instance v1, Landroid/view/GestureDetector;

    .line 1015
    .line 1016
    invoke-virtual {v0}, Landroid/widget/ImageButton;->getContext()Landroid/content/Context;

    .line 1017
    .line 1018
    .line 1019
    move-result-object v3

    .line 1020
    iget-object v4, v0, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mGestureListener:Lcom/android/systemui/volume/CaptionsToggleImageButton$1;

    .line 1021
    .line 1022
    invoke-direct {v1, v3, v4, v2}, Landroid/view/GestureDetector;-><init>(Landroid/content/Context;Landroid/view/GestureDetector$OnGestureListener;Landroid/os/Handler;)V

    .line 1023
    .line 1024
    .line 1025
    iput-object v1, v0, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mGestureDetector:Landroid/view/GestureDetector;

    .line 1026
    .line 1027
    :cond_f
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 1028
    .line 1029
    invoke-interface {v0, v7}, Lcom/android/systemui/plugins/VolumeDialogController;->getCaptionsComponentState(Z)V

    .line 1030
    .line 1031
    .line 1032
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibility:Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;

    .line 1033
    .line 1034
    iget-object v1, v0, Lcom/android/systemui/volume/VolumeDialogImpl$Accessibility;->this$0:Lcom/android/systemui/volume/VolumeDialogImpl;

    .line 1035
    .line 1036
    iget-object v1, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 1037
    .line 1038
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->setAccessibilityDelegate(Landroid/view/View$AccessibilityDelegate;)V

    .line 1039
    .line 1040
    .line 1041
    return-void
.end method

.method public final initDimens()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    const v1, 0x7f07152e

    .line 8
    .line 9
    .line 10
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 11
    .line 12
    .line 13
    move-result v0

    .line 14
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogWidth:I

    .line 15
    .line 16
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 17
    .line 18
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 19
    .line 20
    .line 21
    move-result-object v0

    .line 22
    const v1, 0x7f07152f

    .line 23
    .line 24
    .line 25
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 26
    .line 27
    .line 28
    move-result v0

    .line 29
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogCornerRadius:I

    .line 30
    .line 31
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 32
    .line 33
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 34
    .line 35
    .line 36
    move-result-object v0

    .line 37
    const v1, 0x7f071572

    .line 38
    .line 39
    .line 40
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 41
    .line 42
    .line 43
    move-result v0

    .line 44
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerItemSize:I

    .line 45
    .line 46
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 47
    .line 48
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 49
    .line 50
    .line 51
    move-result-object v0

    .line 52
    const v1, 0x7f071531

    .line 53
    .line 54
    .line 55
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 56
    .line 57
    .line 58
    move-result v0

    .line 59
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerRowsPadding:I

    .line 60
    .line 61
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 62
    .line 63
    invoke-interface {v0}, Lcom/android/systemui/plugins/VolumeDialogController;->hasVibrator()Z

    .line 64
    .line 65
    .line 66
    move-result v0

    .line 67
    iput-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowVibrate:Z

    .line 68
    .line 69
    if-eqz v0, :cond_0

    .line 70
    .line 71
    const/4 v0, 0x3

    .line 72
    goto :goto_0

    .line 73
    :cond_0
    const/4 v0, 0x2

    .line 74
    :goto_0
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerCount:I

    .line 75
    .line 76
    return-void
.end method

.method public final initRow(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;IIIZZ)V
    .locals 2

    .line 1
    iput p2, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 2
    .line 3
    iput p3, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconRes:I

    .line 4
    .line 5
    iput p4, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconMuteRes:I

    .line 6
    .line 7
    iput-boolean p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->important:Z

    .line 8
    .line 9
    iput-boolean p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->defaultStream:Z

    .line 10
    .line 11
    iget-object p4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialog:Lcom/android/systemui/volume/VolumeDialogImpl$CustomDialog;

    .line 12
    .line 13
    invoke-virtual {p4}, Landroid/app/Dialog;->getLayoutInflater()Landroid/view/LayoutInflater;

    .line 14
    .line 15
    .line 16
    move-result-object p4

    .line 17
    const p5, 0x7f0d0507

    .line 18
    .line 19
    .line 20
    const/4 p6, 0x0

    .line 21
    invoke-virtual {p4, p5, p6}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    .line 22
    .line 23
    .line 24
    move-result-object p4

    .line 25
    iput-object p4, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 26
    .line 27
    iget p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 28
    .line 29
    invoke-virtual {p4, p5}, Landroid/view/View;->setId(I)V

    .line 30
    .line 31
    .line 32
    iget-object p4, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 33
    .line 34
    invoke-virtual {p4, p1}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    .line 35
    .line 36
    .line 37
    iget-object p4, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 38
    .line 39
    const p5, 0x7f0a0d0b

    .line 40
    .line 41
    .line 42
    invoke-virtual {p4, p5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 43
    .line 44
    .line 45
    move-result-object p4

    .line 46
    check-cast p4, Landroid/widget/TextView;

    .line 47
    .line 48
    iput-object p4, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 49
    .line 50
    iget p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 51
    .line 52
    mul-int/lit8 p5, p5, 0x14

    .line 53
    .line 54
    invoke-virtual {p4, p5}, Landroid/widget/TextView;->setId(I)V

    .line 55
    .line 56
    .line 57
    const/16 p4, 0xa

    .line 58
    .line 59
    if-ne p2, p4, :cond_0

    .line 60
    .line 61
    iget-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 62
    .line 63
    new-instance v0, Landroid/text/InputFilter$LengthFilter;

    .line 64
    .line 65
    const/16 v1, 0xd

    .line 66
    .line 67
    invoke-direct {v0, v1}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    .line 68
    .line 69
    .line 70
    filled-new-array {v0}, [Landroid/text/InputFilter;

    .line 71
    .line 72
    .line 73
    move-result-object v0

    .line 74
    invoke-virtual {p5, v0}, Landroid/widget/TextView;->setFilters([Landroid/text/InputFilter;)V

    .line 75
    .line 76
    .line 77
    :cond_0
    iget-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 78
    .line 79
    const v0, 0x7f0a0356

    .line 80
    .line 81
    .line 82
    invoke-virtual {p5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 83
    .line 84
    .line 85
    move-result-object p5

    .line 86
    check-cast p5, Landroid/widget/FrameLayout;

    .line 87
    .line 88
    iput-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->dndIcon:Landroid/widget/FrameLayout;

    .line 89
    .line 90
    iget-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 91
    .line 92
    const v0, 0x7f0a0d0e

    .line 93
    .line 94
    .line 95
    invoke-virtual {p5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 96
    .line 97
    .line 98
    move-result-object p5

    .line 99
    check-cast p5, Landroid/widget/SeekBar;

    .line 100
    .line 101
    iput-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 102
    .line 103
    new-instance v0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeSeekBarChangeListener;

    .line 104
    .line 105
    const/4 v1, 0x0

    .line 106
    invoke-direct {v0, p0, p1, v1}, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeSeekBarChangeListener;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;I)V

    .line 107
    .line 108
    .line 109
    invoke-virtual {p5, v0}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 110
    .line 111
    .line 112
    iget-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 113
    .line 114
    const v0, 0x7f0a0cef

    .line 115
    .line 116
    .line 117
    invoke-virtual {p5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 118
    .line 119
    .line 120
    move-result-object p5

    .line 121
    check-cast p5, Landroid/widget/TextView;

    .line 122
    .line 123
    iput-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->number:Landroid/widget/TextView;

    .line 124
    .line 125
    iput-object p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 126
    .line 127
    iget-object p5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 128
    .line 129
    const v0, 0x7f081324

    .line 130
    .line 131
    .line 132
    invoke-virtual {p5, v0}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 133
    .line 134
    .line 135
    move-result-object p5

    .line 136
    check-cast p5, Landroid/graphics/drawable/LayerDrawable;

    .line 137
    .line 138
    const v0, 0x102000d

    .line 139
    .line 140
    .line 141
    invoke-virtual {p5, v0}, Landroid/graphics/drawable/LayerDrawable;->findDrawableByLayerId(I)Landroid/graphics/drawable/Drawable;

    .line 142
    .line 143
    .line 144
    move-result-object v0

    .line 145
    check-cast v0, Lcom/android/systemui/util/RoundedCornerProgressDrawable;

    .line 146
    .line 147
    invoke-virtual {v0}, Landroid/graphics/drawable/InsetDrawable;->getDrawable()Landroid/graphics/drawable/Drawable;

    .line 148
    .line 149
    .line 150
    move-result-object v0

    .line 151
    check-cast v0, Landroid/graphics/drawable/LayerDrawable;

    .line 152
    .line 153
    const v1, 0x7f0a0d17

    .line 154
    .line 155
    .line 156
    invoke-virtual {v0, v1}, Landroid/graphics/drawable/LayerDrawable;->findDrawableByLayerId(I)Landroid/graphics/drawable/Drawable;

    .line 157
    .line 158
    .line 159
    move-result-object v1

    .line 160
    iput-object v1, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressSolid:Landroid/graphics/drawable/Drawable;

    .line 161
    .line 162
    const v1, 0x7f0a0d16

    .line 163
    .line 164
    .line 165
    invoke-virtual {v0, v1}, Landroid/graphics/drawable/LayerDrawable;->findDrawableByLayerId(I)Landroid/graphics/drawable/Drawable;

    .line 166
    .line 167
    .line 168
    move-result-object v0

    .line 169
    if-eqz v0, :cond_1

    .line 170
    .line 171
    check-cast v0, Landroid/graphics/drawable/RotateDrawable;

    .line 172
    .line 173
    invoke-virtual {v0}, Landroid/graphics/drawable/RotateDrawable;->getDrawable()Landroid/graphics/drawable/Drawable;

    .line 174
    .line 175
    .line 176
    move-result-object p6

    .line 177
    check-cast p6, Lcom/android/systemui/util/AlphaTintDrawableWrapper;

    .line 178
    .line 179
    :cond_1
    iput-object p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressIcon:Lcom/android/systemui/util/AlphaTintDrawableWrapper;

    .line 180
    .line 181
    iget-object p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 182
    .line 183
    invoke-virtual {p6, p5}, Landroid/widget/SeekBar;->setProgressDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 184
    .line 185
    .line 186
    iget-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 187
    .line 188
    const p6, 0x7f0a0d0c

    .line 189
    .line 190
    .line 191
    invoke-virtual {p5, p6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 192
    .line 193
    .line 194
    move-result-object p5

    .line 195
    check-cast p5, Landroid/widget/ImageButton;

    .line 196
    .line 197
    iput-object p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 198
    .line 199
    iget-object p5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 200
    .line 201
    invoke-virtual {p5}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 202
    .line 203
    .line 204
    move-result-object p5

    .line 205
    iget-object p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 206
    .line 207
    if-eqz p6, :cond_2

    .line 208
    .line 209
    invoke-virtual {p6, p3}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 210
    .line 211
    .line 212
    :cond_2
    iget-object p6, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressIcon:Lcom/android/systemui/util/AlphaTintDrawableWrapper;

    .line 213
    .line 214
    if-eqz p6, :cond_3

    .line 215
    .line 216
    iget-object v0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 217
    .line 218
    invoke-virtual {v0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    .line 219
    .line 220
    .line 221
    move-result-object v0

    .line 222
    invoke-virtual {v0, p3, p5}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    .line 223
    .line 224
    .line 225
    move-result-object p3

    .line 226
    invoke-virtual {p6, p3}, Landroid/graphics/drawable/InsetDrawable;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 227
    .line 228
    .line 229
    :cond_3
    iget-object p3, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 230
    .line 231
    if-eqz p3, :cond_5

    .line 232
    .line 233
    iget p5, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 234
    .line 235
    if-eq p5, p4, :cond_4

    .line 236
    .line 237
    new-instance p4, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda10;

    .line 238
    .line 239
    invoke-direct {p4, p0, p1, p2}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda10;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;I)V

    .line 240
    .line 241
    .line 242
    invoke-virtual {p3, p4}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 243
    .line 244
    .line 245
    goto :goto_0

    .line 246
    :cond_4
    const/4 p0, 0x2

    .line 247
    invoke-virtual {p3, p0}, Landroid/widget/ImageButton;->setImportantForAccessibility(I)V

    .line 248
    .line 249
    .line 250
    :cond_5
    :goto_0
    return-void
.end method

.method public final initSettingsH(I)V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSettingsView:Landroid/view/View;

    .line 2
    .line 3
    if-eqz v0, :cond_1

    .line 4
    .line 5
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    .line 6
    .line 7
    check-cast v1, Lcom/android/systemui/statusbar/policy/DeviceProvisionedControllerImpl;

    .line 8
    .line 9
    invoke-virtual {v1}, Lcom/android/systemui/statusbar/policy/DeviceProvisionedControllerImpl;->isCurrentUserSetup()Z

    .line 10
    .line 11
    .line 12
    move-result v1

    .line 13
    if-eqz v1, :cond_0

    .line 14
    .line 15
    if-nez p1, :cond_0

    .line 16
    .line 17
    const/4 p1, 0x0

    .line 18
    goto :goto_0

    .line 19
    :cond_0
    const/16 p1, 0x8

    .line 20
    .line 21
    :goto_0
    invoke-virtual {v0, p1}, Landroid/view/View;->setVisibility(I)V

    .line 22
    .line 23
    .line 24
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSettingsIcon:Landroid/widget/ImageButton;

    .line 25
    .line 26
    if-eqz p1, :cond_2

    .line 27
    .line 28
    new-instance v0, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;

    .line 29
    .line 30
    const/4 v1, 0x1

    .line 31
    invoke-direct {v0, p0, v1}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda6;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 32
    .line 33
    .line 34
    invoke-virtual {p1, v0}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 35
    .line 36
    .line 37
    :cond_2
    return-void
.end method

.method public final isLandscape()Z
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    invoke-virtual {p0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 8
    .line 9
    .line 10
    move-result-object p0

    .line 11
    iget p0, p0, Landroid/content/res/Configuration;->orientation:I

    .line 12
    .line 13
    const/4 v0, 0x2

    .line 14
    if-ne p0, v0, :cond_0

    .line 15
    .line 16
    const/4 p0, 0x1

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    const/4 p0, 0x0

    .line 19
    :goto_0
    return p0
.end method

.method public final isRtl()Z
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    invoke-static {p0}, Landroidx/appcompat/widget/MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;->m(Landroid/content/Context;)I

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    const/4 v0, 0x1

    .line 8
    if-ne p0, v0, :cond_0

    .line 9
    .line 10
    goto :goto_0

    .line 11
    :cond_0
    const/4 v0, 0x0

    .line 12
    :goto_0
    return v0
.end method

.method public final onComputeInternalInsets(Landroid/view/ViewTreeObserver$InternalInsetsInfo;)V
    .locals 2

    .line 1
    const/4 v0, 0x3

    .line 2
    invoke-virtual {p1, v0}, Landroid/view/ViewTreeObserver$InternalInsetsInfo;->setTouchableInsets(I)V

    .line 3
    .line 4
    .line 5
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTouchableRegion:Landroid/graphics/Region;

    .line 6
    .line 7
    invoke-virtual {v0}, Landroid/graphics/Region;->setEmpty()V

    .line 8
    .line 9
    .line 10
    const/4 v0, 0x0

    .line 11
    :goto_0
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 12
    .line 13
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getChildCount()I

    .line 14
    .line 15
    .line 16
    move-result v1

    .line 17
    if-ge v0, v1, :cond_0

    .line 18
    .line 19
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogView:Landroid/view/ViewGroup;

    .line 20
    .line 21
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    .line 22
    .line 23
    .line 24
    move-result-object v1

    .line 25
    invoke-virtual {p0, v1}, Lcom/android/systemui/volume/VolumeDialogImpl;->unionViewBoundstoTouchableRegion(Landroid/view/View;)V

    .line 26
    .line 27
    .line 28
    add-int/lit8 v0, v0, 0x1

    .line 29
    .line 30
    goto :goto_0

    .line 31
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 32
    .line 33
    if-eqz v0, :cond_1

    .line 34
    .line 35
    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    .line 36
    .line 37
    .line 38
    move-result v0

    .line 39
    if-nez v0, :cond_1

    .line 40
    .line 41
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 42
    .line 43
    invoke-virtual {p0, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->unionViewBoundstoTouchableRegion(Landroid/view/View;)V

    .line 44
    .line 45
    .line 46
    :cond_1
    iget-object p1, p1, Landroid/view/ViewTreeObserver$InternalInsetsInfo;->touchableRegion:Landroid/graphics/Region;

    .line 47
    .line 48
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTouchableRegion:Landroid/graphics/Region;

    .line 49
    .line 50
    invoke-virtual {p1, p0}, Landroid/graphics/Region;->set(Landroid/graphics/Region;)Z

    .line 51
    .line 52
    .line 53
    return-void
.end method

.method public final onConfigChanged(Landroid/content/res/Configuration;)V
    .locals 0

    .line 1
    iget p1, p1, Landroid/content/res/Configuration;->orientation:I

    .line 2
    .line 3
    iput p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mOrientation:I

    .line 4
    .line 5
    return-void
.end method

.method public onPostureChanged(I)V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 2
    .line 3
    const/4 v1, 0x2

    .line 4
    const/4 v2, 0x0

    .line 5
    const/16 v3, 0xb

    .line 6
    .line 7
    invoke-virtual {v0, v1, v3, v2}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 12
    .line 13
    .line 14
    iput p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDevicePosture:I

    .line 15
    .line 16
    return-void
.end method

.method public final onStateChangedH(Lcom/android/systemui/plugins/VolumeDialogController$State;)V
    .locals 9

    .line 1
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 6
    .line 7
    new-instance v1, Ljava/lang/StringBuilder;

    .line 8
    .line 9
    const-string v2, "onStateChangedH() state: "

    .line 10
    .line 11
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 12
    .line 13
    .line 14
    invoke-virtual {p1}, Lcom/android/systemui/plugins/VolumeDialogController$State;->toString()Ljava/lang/String;

    .line 15
    .line 16
    .line 17
    move-result-object v2

    .line 18
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 19
    .line 20
    .line 21
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 22
    .line 23
    .line 24
    move-result-object v1

    .line 25
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    .line 27
    .line 28
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 29
    .line 30
    const/4 v1, 0x1

    .line 31
    if-eqz v0, :cond_1

    .line 32
    .line 33
    if-eqz p1, :cond_1

    .line 34
    .line 35
    iget v0, v0, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 36
    .line 37
    const/4 v2, -0x1

    .line 38
    if-eq v0, v2, :cond_1

    .line 39
    .line 40
    iget v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 41
    .line 42
    if-eq v0, v2, :cond_1

    .line 43
    .line 44
    if-ne v2, v1, :cond_1

    .line 45
    .line 46
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 47
    .line 48
    invoke-static {v1}, Landroid/os/VibrationEffect;->get(I)Landroid/os/VibrationEffect;

    .line 49
    .line 50
    .line 51
    move-result-object v2

    .line 52
    invoke-interface {v0, v2}, Lcom/android/systemui/plugins/VolumeDialogController;->vibrate(Landroid/os/VibrationEffect;)V

    .line 53
    .line 54
    .line 55
    :cond_1
    iput-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 56
    .line 57
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 58
    .line 59
    invoke-virtual {v0}, Landroid/util/SparseBooleanArray;->clear()V

    .line 60
    .line 61
    .line 62
    const/4 v0, 0x0

    .line 63
    :goto_0
    iget-object v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 64
    .line 65
    invoke-virtual {v2}, Landroid/util/SparseArray;->size()I

    .line 66
    .line 67
    .line 68
    move-result v2

    .line 69
    if-ge v0, v2, :cond_6

    .line 70
    .line 71
    iget-object v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 72
    .line 73
    invoke-virtual {v2, v0}, Landroid/util/SparseArray;->keyAt(I)I

    .line 74
    .line 75
    .line 76
    move-result v4

    .line 77
    iget-object v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 78
    .line 79
    invoke-virtual {v2, v0}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    .line 80
    .line 81
    .line 82
    move-result-object v2

    .line 83
    check-cast v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 84
    .line 85
    iget-boolean v2, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->dynamic:Z

    .line 86
    .line 87
    if-nez v2, :cond_2

    .line 88
    .line 89
    goto :goto_2

    .line 90
    :cond_2
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 91
    .line 92
    invoke-virtual {v2, v4, v1}, Landroid/util/SparseBooleanArray;->put(IZ)V

    .line 93
    .line 94
    .line 95
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 96
    .line 97
    check-cast v2, Ljava/util/ArrayList;

    .line 98
    .line 99
    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 100
    .line 101
    .line 102
    move-result-object v2

    .line 103
    :cond_3
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    .line 104
    .line 105
    .line 106
    move-result v3

    .line 107
    if-eqz v3, :cond_4

    .line 108
    .line 109
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 110
    .line 111
    .line 112
    move-result-object v3

    .line 113
    check-cast v3, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 114
    .line 115
    iget v5, v3, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 116
    .line 117
    if-ne v5, v4, :cond_3

    .line 118
    .line 119
    goto :goto_1

    .line 120
    :cond_4
    const/4 v3, 0x0

    .line 121
    :goto_1
    if-nez v3, :cond_5

    .line 122
    .line 123
    const v5, 0x7f080b13

    .line 124
    .line 125
    .line 126
    const v6, 0x7f080b14

    .line 127
    .line 128
    .line 129
    const/4 v7, 0x1

    .line 130
    const/4 v8, 0x0

    .line 131
    move-object v3, p0

    .line 132
    invoke-virtual/range {v3 .. v8}, Lcom/android/systemui/volume/VolumeDialogImpl;->addRow(IIIZZ)V

    .line 133
    .line 134
    .line 135
    :cond_5
    :goto_2
    add-int/lit8 v0, v0, 0x1

    .line 136
    .line 137
    goto :goto_0

    .line 138
    :cond_6
    iget v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActiveStream:I

    .line 139
    .line 140
    iget p1, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->activeStream:I

    .line 141
    .line 142
    if-eq v0, p1, :cond_7

    .line 143
    .line 144
    iput v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mPrevActiveStream:I

    .line 145
    .line 146
    iput p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mActiveStream:I

    .line 147
    .line 148
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getActiveRow()Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 149
    .line 150
    .line 151
    move-result-object p1

    .line 152
    invoke-virtual {p0, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateRowsH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 153
    .line 154
    .line 155
    iget-boolean p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 156
    .line 157
    if-eqz p1, :cond_7

    .line 158
    .line 159
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->rescheduleTimeoutH()V

    .line 160
    .line 161
    .line 162
    :cond_7
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 163
    .line 164
    check-cast p1, Ljava/util/ArrayList;

    .line 165
    .line 166
    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 167
    .line 168
    .line 169
    move-result-object p1

    .line 170
    :goto_3
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    .line 171
    .line 172
    .line 173
    move-result v0

    .line 174
    if-eqz v0, :cond_8

    .line 175
    .line 176
    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 177
    .line 178
    .line 179
    move-result-object v0

    .line 180
    check-cast v0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 181
    .line 182
    invoke-virtual {p0, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 183
    .line 184
    .line 185
    goto :goto_3

    .line 186
    :cond_8
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateRingerH()V

    .line 187
    .line 188
    .line 189
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mWindow:Landroid/view/Window;

    .line 190
    .line 191
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 192
    .line 193
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getActiveRow()Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 194
    .line 195
    .line 196
    move-result-object v1

    .line 197
    iget-object v1, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->ss:Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 198
    .line 199
    invoke-virtual {p0, v1}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 200
    .line 201
    .line 202
    move-result-object p0

    .line 203
    filled-new-array {p0}, [Ljava/lang/Object;

    .line 204
    .line 205
    .line 206
    move-result-object p0

    .line 207
    const v1, 0x7f131204

    .line 208
    .line 209
    .line 210
    invoke-virtual {v0, v1, p0}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 211
    .line 212
    .line 213
    move-result-object p0

    .line 214
    invoke-virtual {p1, p0}, Landroid/view/Window;->setTitle(Ljava/lang/CharSequence;)V

    .line 215
    .line 216
    .line 217
    return-void
.end method

.method public final onUiModeChanged()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 2
    .line 3
    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 8
    .line 9
    invoke-virtual {p0}, Landroid/content/Context;->getThemeResId()I

    .line 10
    .line 11
    .line 12
    move-result p0

    .line 13
    const/4 v1, 0x1

    .line 14
    invoke-virtual {v0, p0, v1}, Landroid/content/res/Resources$Theme;->applyStyle(IZ)V

    .line 15
    .line 16
    .line 17
    return-void
.end method

.method public final recheckH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V
    .locals 3

    .line 1
    if-nez p1, :cond_1

    .line 2
    .line 3
    sget-boolean p1, Lcom/android/systemui/volume/D;->BUG:Z

    .line 4
    .line 5
    if-eqz p1, :cond_0

    .line 6
    .line 7
    sget-object p1, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 8
    .line 9
    const-string/jumbo v0, "recheckH ALL"

    .line 10
    .line 11
    .line 12
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 13
    .line 14
    .line 15
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->trimObsoleteH()V

    .line 16
    .line 17
    .line 18
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 19
    .line 20
    check-cast p1, Ljava/util/ArrayList;

    .line 21
    .line 22
    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 23
    .line 24
    .line 25
    move-result-object p1

    .line 26
    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    .line 27
    .line 28
    .line 29
    move-result v0

    .line 30
    if-eqz v0, :cond_3

    .line 31
    .line 32
    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 33
    .line 34
    .line 35
    move-result-object v0

    .line 36
    check-cast v0, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 37
    .line 38
    invoke-virtual {p0, v0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 39
    .line 40
    .line 41
    goto :goto_0

    .line 42
    :cond_1
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    .line 43
    .line 44
    if-eqz v0, :cond_2

    .line 45
    .line 46
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 47
    .line 48
    new-instance v1, Ljava/lang/StringBuilder;

    .line 49
    .line 50
    const-string/jumbo v2, "recheckH "

    .line 51
    .line 52
    .line 53
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 54
    .line 55
    .line 56
    iget v2, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 57
    .line 58
    invoke-static {v1, v2, v0}, Landroidx/recyclerview/widget/RecyclerView$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)V

    .line 59
    .line 60
    .line 61
    :cond_2
    invoke-virtual {p0, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 62
    .line 63
    .line 64
    :cond_3
    return-void
.end method

.method public final rescheduleTimeoutH()V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 2
    .line 3
    const/4 v1, 0x2

    .line 4
    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    .line 5
    .line 6
    .line 7
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHovering:Z

    .line 8
    .line 9
    const/4 v2, 0x4

    .line 10
    if-eqz v0, :cond_0

    .line 11
    .line 12
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

    .line 13
    .line 14
    const/16 v3, 0x3e80

    .line 15
    .line 16
    invoke-virtual {v0, v3, v2}, Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;->getRecommendedTimeoutMillis(II)I

    .line 17
    .line 18
    .line 19
    move-result v0

    .line 20
    goto :goto_0

    .line 21
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarning:Lcom/android/systemui/volume/SafetyWarningDialog;

    .line 22
    .line 23
    const/4 v3, 0x6

    .line 24
    const/16 v4, 0x1388

    .line 25
    .line 26
    if-eqz v0, :cond_1

    .line 27
    .line 28
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

    .line 29
    .line 30
    invoke-virtual {v0, v4, v3}, Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;->getRecommendedTimeoutMillis(II)I

    .line 31
    .line 32
    .line 33
    move-result v0

    .line 34
    goto :goto_0

    .line 35
    :cond_1
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHasSeenODICaptionsTooltip:Z

    .line 36
    .line 37
    if-nez v0, :cond_2

    .line 38
    .line 39
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsTooltipView:Landroid/view/View;

    .line 40
    .line 41
    if-eqz v0, :cond_2

    .line 42
    .line 43
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

    .line 44
    .line 45
    invoke-virtual {v0, v4, v3}, Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;->getRecommendedTimeoutMillis(II)I

    .line 46
    .line 47
    .line 48
    move-result v0

    .line 49
    goto :goto_0

    .line 50
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAccessibilityMgr:Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;

    .line 51
    .line 52
    const/16 v3, 0xbb8

    .line 53
    .line 54
    invoke-virtual {v0, v3, v2}, Lcom/android/systemui/statusbar/policy/AccessibilityManagerWrapper;->getRecommendedTimeoutMillis(II)I

    .line 55
    .line 56
    .line 57
    move-result v0

    .line 58
    :goto_0
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 59
    .line 60
    const/4 v3, 0x3

    .line 61
    const/4 v4, 0x0

    .line 62
    invoke-virtual {v2, v1, v3, v4}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    .line 63
    .line 64
    .line 65
    move-result-object v1

    .line 66
    int-to-long v3, v0

    .line 67
    invoke-virtual {v2, v1, v3, v4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 68
    .line 69
    .line 70
    sget-object v1, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 71
    .line 72
    const-string/jumbo v2, "rescheduleTimeout "

    .line 73
    .line 74
    .line 75
    const-string v3, " "

    .line 76
    .line 77
    invoke-static {v2, v0, v3}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/StringBuilder;

    .line 78
    .line 79
    .line 80
    move-result-object v0

    .line 81
    invoke-static {}, Landroid/os/Debug;->getCaller()Ljava/lang/String;

    .line 82
    .line 83
    .line 84
    move-result-object v2

    .line 85
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 86
    .line 87
    .line 88
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 89
    .line 90
    .line 91
    move-result-object v0

    .line 92
    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 93
    .line 94
    .line 95
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 96
    .line 97
    invoke-interface {p0}, Lcom/android/systemui/plugins/VolumeDialogController;->userActivity()V

    .line 98
    .line 99
    .line 100
    return-void
.end method

.method public final setRingerMode(I)V
    .locals 10

    .line 1
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    const/16 v1, 0x12

    .line 10
    .line 11
    invoke-static {v1, v0}, Lcom/android/systemui/volume/Events;->writeEvent(I[Ljava/lang/Object;)V

    .line 12
    .line 13
    .line 14
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 15
    .line 16
    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    .line 17
    .line 18
    .line 19
    move-result-object v0

    .line 20
    const-string v1, "manual_ringer_toggle_count"

    .line 21
    .line 22
    const/4 v2, 0x0

    .line 23
    invoke-static {v0, v1, v2}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    .line 24
    .line 25
    .line 26
    move-result v3

    .line 27
    const/4 v4, 0x1

    .line 28
    add-int/2addr v3, v4

    .line 29
    invoke-static {v0, v1, v3}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 30
    .line 31
    .line 32
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateRingerH()V

    .line 33
    .line 34
    .line 35
    const/4 v0, 0x2

    .line 36
    const/4 v1, 0x0

    .line 37
    if-eqz p1, :cond_2

    .line 38
    .line 39
    if-eq p1, v4, :cond_1

    .line 40
    .line 41
    if-eq p1, v0, :cond_0

    .line 42
    .line 43
    invoke-static {v4}, Landroid/os/VibrationEffect;->get(I)Landroid/os/VibrationEffect;

    .line 44
    .line 45
    .line 46
    move-result-object v3

    .line 47
    goto :goto_0

    .line 48
    :cond_0
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 49
    .line 50
    invoke-interface {v3}, Lcom/android/systemui/plugins/VolumeDialogController;->scheduleTouchFeedback()V

    .line 51
    .line 52
    .line 53
    :cond_1
    move-object v3, v1

    .line 54
    goto :goto_0

    .line 55
    :cond_2
    invoke-static {v2}, Landroid/os/VibrationEffect;->get(I)Landroid/os/VibrationEffect;

    .line 56
    .line 57
    .line 58
    move-result-object v3

    .line 59
    :goto_0
    if-eqz v3, :cond_3

    .line 60
    .line 61
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 62
    .line 63
    invoke-interface {v5, v3}, Lcom/android/systemui/plugins/VolumeDialogController;->vibrate(Landroid/os/VibrationEffect;)V

    .line 64
    .line 65
    .line 66
    :cond_3
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 67
    .line 68
    invoke-interface {v3, p1, v2}, Lcom/android/systemui/plugins/VolumeDialogController;->setRingerMode(IZ)V

    .line 69
    .line 70
    .line 71
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 72
    .line 73
    const-string v5, "RingerGuidanceCount"

    .line 74
    .line 75
    invoke-static {v3, v5, v2}, Lcom/android/systemui/Prefs;->getInt(Landroid/content/Context;Ljava/lang/String;I)I

    .line 76
    .line 77
    .line 78
    move-result v3

    .line 79
    const/16 v6, 0xc

    .line 80
    .line 81
    if-le v3, v6, :cond_4

    .line 82
    .line 83
    goto :goto_2

    .line 84
    :cond_4
    if-eqz p1, :cond_6

    .line 85
    .line 86
    if-eq p1, v0, :cond_5

    .line 87
    .line 88
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 89
    .line 90
    const v0, 0x1040f19

    .line 91
    .line 92
    .line 93
    invoke-virtual {p1, v0}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 94
    .line 95
    .line 96
    move-result-object v1

    .line 97
    goto :goto_1

    .line 98
    :cond_5
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 99
    .line 100
    iget-object p1, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 101
    .line 102
    invoke-virtual {p1, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    .line 103
    .line 104
    .line 105
    move-result-object p1

    .line 106
    check-cast p1, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 107
    .line 108
    if-eqz p1, :cond_7

    .line 109
    .line 110
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 111
    .line 112
    iget v1, p1, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 113
    .line 114
    int-to-long v6, v1

    .line 115
    iget p1, p1, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->levelMax:I

    .line 116
    .line 117
    int-to-long v8, p1

    .line 118
    long-to-double v6, v6

    .line 119
    long-to-double v8, v8

    .line 120
    div-double/2addr v6, v8

    .line 121
    invoke-static {}, Ljava/text/NumberFormat;->getPercentInstance()Ljava/text/NumberFormat;

    .line 122
    .line 123
    .line 124
    move-result-object p1

    .line 125
    invoke-virtual {p1, v6, v7}, Ljava/text/NumberFormat;->format(D)Ljava/lang/String;

    .line 126
    .line 127
    .line 128
    move-result-object p1

    .line 129
    filled-new-array {p1}, [Ljava/lang/Object;

    .line 130
    .line 131
    .line 132
    move-result-object p1

    .line 133
    const v1, 0x7f131203

    .line 134
    .line 135
    .line 136
    invoke-virtual {v0, v1, p1}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 137
    .line 138
    .line 139
    move-result-object v1

    .line 140
    goto :goto_1

    .line 141
    :cond_6
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 142
    .line 143
    const v0, 0x1040f18

    .line 144
    .line 145
    .line 146
    invoke-virtual {p1, v0}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 147
    .line 148
    .line 149
    move-result-object v1

    .line 150
    :cond_7
    :goto_1
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 151
    .line 152
    invoke-static {p1, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    .line 153
    .line 154
    .line 155
    move-result-object p1

    .line 156
    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    .line 157
    .line 158
    .line 159
    add-int/2addr v3, v4

    .line 160
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 161
    .line 162
    invoke-static {p0, v5, v3}, Lcom/android/systemui/Prefs;->putInt(Landroid/content/Context;Ljava/lang/String;I)V

    .line 163
    .line 164
    .line 165
    :goto_2
    return-void
.end method

.method public final shouldBeVisibleH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)Z
    .locals 5

    .line 1
    iget v0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 2
    .line 3
    iget p2, p2, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 4
    .line 5
    const/4 v1, 0x0

    .line 6
    const/4 v2, 0x1

    .line 7
    if-ne v0, p2, :cond_0

    .line 8
    .line 9
    move v3, v2

    .line 10
    goto :goto_0

    .line 11
    :cond_0
    move v3, v1

    .line 12
    :goto_0
    if-eqz v3, :cond_1

    .line 13
    .line 14
    return v2

    .line 15
    :cond_1
    iget-boolean v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowActiveStreamOnly:Z

    .line 16
    .line 17
    if-nez v3, :cond_5

    .line 18
    .line 19
    const/16 v3, 0xa

    .line 20
    .line 21
    if-ne v0, v3, :cond_2

    .line 22
    .line 23
    iget-boolean p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowA11yStream:Z

    .line 24
    .line 25
    return p0

    .line 26
    :cond_2
    if-ne p2, v3, :cond_3

    .line 27
    .line 28
    iget v4, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mPrevActiveStream:I

    .line 29
    .line 30
    if-ne v0, v4, :cond_3

    .line 31
    .line 32
    return v2

    .line 33
    :cond_3
    iget-boolean p1, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->defaultStream:Z

    .line 34
    .line 35
    if-eqz p1, :cond_5

    .line 36
    .line 37
    const/4 p1, 0x2

    .line 38
    if-eq p2, p1, :cond_4

    .line 39
    .line 40
    const/4 p1, 0x4

    .line 41
    if-eq p2, p1, :cond_4

    .line 42
    .line 43
    if-eqz p2, :cond_4

    .line 44
    .line 45
    if-eq p2, v3, :cond_4

    .line 46
    .line 47
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 48
    .line 49
    invoke-virtual {p0, p2}, Landroid/util/SparseBooleanArray;->get(I)Z

    .line 50
    .line 51
    .line 52
    move-result p0

    .line 53
    if-eqz p0, :cond_5

    .line 54
    .line 55
    :cond_4
    move v1, v2

    .line 56
    :cond_5
    return v1
.end method

.method public showCsdWarningH(II)V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSafetyWarningLock:Ljava/lang/Object;

    .line 2
    .line 3
    monitor-enter v0

    .line 4
    :try_start_0
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mCsdDialog:Lcom/android/systemui/volume/CsdWarningDialog;

    .line 5
    .line 6
    if-eqz v1, :cond_0

    .line 7
    .line 8
    monitor-exit v0

    .line 9
    return-void

    .line 10
    :cond_0
    new-instance v1, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;

    .line 11
    .line 12
    const/4 v2, 0x0

    .line 13
    invoke-direct {v1, p0, v2}, Lcom/android/systemui/volume/VolumeDialogImpl$$ExternalSyntheticLambda2;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;I)V

    .line 14
    .line 15
    .line 16
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mCsdWarningDialogFactory:Lcom/android/systemui/volume/CsdWarningDialog$Factory;

    .line 17
    .line 18
    invoke-interface {v3, p1, v1}, Lcom/android/systemui/volume/CsdWarningDialog$Factory;->create(ILjava/lang/Runnable;)Lcom/android/systemui/volume/CsdWarningDialog;

    .line 19
    .line 20
    .line 21
    move-result-object p1

    .line 22
    iput-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mCsdDialog:Lcom/android/systemui/volume/CsdWarningDialog;

    .line 23
    .line 24
    invoke-virtual {p1}, Lcom/android/systemui/volume/CsdWarningDialog;->show()V

    .line 25
    .line 26
    .line 27
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 28
    const/4 p1, 0x0

    .line 29
    invoke-virtual {p0, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->recheckH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V

    .line 30
    .line 31
    .line 32
    if-lez p2, :cond_1

    .line 33
    .line 34
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 35
    .line 36
    const/16 v0, 0x8

    .line 37
    .line 38
    invoke-virtual {p1, v0}, Landroid/os/Handler;->removeMessages(I)V

    .line 39
    .line 40
    .line 41
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 42
    .line 43
    const/16 v1, 0xa

    .line 44
    .line 45
    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    .line 46
    .line 47
    .line 48
    move-result-object v0

    .line 49
    int-to-long v1, p2

    .line 50
    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 51
    .line 52
    .line 53
    sget-object p1, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 54
    .line 55
    const-string/jumbo v0, "scheduleCsdTimeoutH "

    .line 56
    .line 57
    .line 58
    const-string v1, "ms "

    .line 59
    .line 60
    invoke-static {v0, p2, v1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/StringBuilder;

    .line 61
    .line 62
    .line 63
    move-result-object p2

    .line 64
    invoke-static {}, Landroid/os/Debug;->getCaller()Ljava/lang/String;

    .line 65
    .line 66
    .line 67
    move-result-object v0

    .line 68
    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 69
    .line 70
    .line 71
    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 72
    .line 73
    .line 74
    move-result-object p2

    .line 75
    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 76
    .line 77
    .line 78
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 79
    .line 80
    invoke-interface {p1}, Lcom/android/systemui/plugins/VolumeDialogController;->userActivity()V

    .line 81
    .line 82
    .line 83
    :cond_1
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->rescheduleTimeoutH()V

    .line 84
    .line 85
    .line 86
    return-void

    .line 87
    :catchall_0
    move-exception p0

    .line 88
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 89
    throw p0
.end method

.method public final trimObsoleteH()V
    .locals 4

    .line 1
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 6
    .line 7
    const-string/jumbo v1, "trimObsoleteH"

    .line 8
    .line 9
    .line 10
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 11
    .line 12
    .line 13
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 14
    .line 15
    check-cast v0, Ljava/util/ArrayList;

    .line 16
    .line 17
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    .line 18
    .line 19
    .line 20
    move-result v0

    .line 21
    add-int/lit8 v0, v0, -0x1

    .line 22
    .line 23
    :goto_0
    if-ltz v0, :cond_3

    .line 24
    .line 25
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 26
    .line 27
    check-cast v1, Ljava/util/ArrayList;

    .line 28
    .line 29
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 30
    .line 31
    .line 32
    move-result-object v1

    .line 33
    check-cast v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 34
    .line 35
    iget-object v2, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->ss:Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 36
    .line 37
    if-eqz v2, :cond_2

    .line 38
    .line 39
    iget-boolean v2, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->dynamic:Z

    .line 40
    .line 41
    if-nez v2, :cond_1

    .line 42
    .line 43
    goto :goto_1

    .line 44
    :cond_1
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDynamic:Landroid/util/SparseBooleanArray;

    .line 45
    .line 46
    iget v3, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 47
    .line 48
    invoke-virtual {v2, v3}, Landroid/util/SparseBooleanArray;->get(I)Z

    .line 49
    .line 50
    .line 51
    move-result v2

    .line 52
    if-nez v2, :cond_2

    .line 53
    .line 54
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 55
    .line 56
    check-cast v2, Ljava/util/ArrayList;

    .line 57
    .line 58
    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 59
    .line 60
    .line 61
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 62
    .line 63
    iget-object v3, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 64
    .line 65
    invoke-virtual {v2, v3}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 66
    .line 67
    .line 68
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurableTexts:Lcom/android/systemui/volume/ConfigurableTexts;

    .line 69
    .line 70
    iget-object v1, v1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 71
    .line 72
    iget-object v3, v2, Lcom/android/systemui/volume/ConfigurableTexts;->mTexts:Landroid/util/ArrayMap;

    .line 73
    .line 74
    invoke-virtual {v3, v1}, Landroid/util/ArrayMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 75
    .line 76
    .line 77
    iget-object v2, v2, Lcom/android/systemui/volume/ConfigurableTexts;->mTextLabels:Landroid/util/ArrayMap;

    .line 78
    .line 79
    invoke-virtual {v2, v1}, Landroid/util/ArrayMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 80
    .line 81
    .line 82
    :cond_2
    :goto_1
    add-int/lit8 v0, v0, -0x1

    .line 83
    .line 84
    goto :goto_0

    .line 85
    :cond_3
    return-void
.end method

.method public final unionViewBoundstoTouchableRegion(Landroid/view/View;)V
    .locals 11

    .line 1
    const/4 v0, 0x2

    .line 2
    new-array v0, v0, [I

    .line 3
    .line 4
    invoke-virtual {p1, v0}, Landroid/view/View;->getLocationInWindow([I)V

    .line 5
    .line 6
    .line 7
    const/4 v1, 0x0

    .line 8
    aget v2, v0, v1

    .line 9
    .line 10
    int-to-float v2, v2

    .line 11
    const/4 v3, 0x1

    .line 12
    aget v4, v0, v3

    .line 13
    .line 14
    int-to-float v4, v4

    .line 15
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTopContainer:Landroid/view/View;

    .line 16
    .line 17
    if-ne p1, v5, :cond_1

    .line 18
    .line 19
    iget-boolean v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mIsRingerDrawerOpen:Z

    .line 20
    .line 21
    if-nez v5, :cond_1

    .line 22
    .line 23
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 24
    .line 25
    .line 26
    move-result v5

    .line 27
    if-nez v5, :cond_0

    .line 28
    .line 29
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 30
    .line 31
    .line 32
    move-result v5

    .line 33
    int-to-float v5, v5

    .line 34
    add-float/2addr v4, v5

    .line 35
    goto :goto_0

    .line 36
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 37
    .line 38
    .line 39
    move-result v5

    .line 40
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getVisibleRowsExtraSize()I

    .line 41
    .line 42
    .line 43
    move-result v6

    .line 44
    if-le v5, v6, :cond_1

    .line 45
    .line 46
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 47
    .line 48
    .line 49
    move-result v5

    .line 50
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getVisibleRowsExtraSize()I

    .line 51
    .line 52
    .line 53
    move-result v6

    .line 54
    sub-int/2addr v5, v6

    .line 55
    int-to-float v5, v5

    .line 56
    add-float/2addr v2, v5

    .line 57
    :cond_1
    :goto_0
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mTouchableRegion:Landroid/graphics/Region;

    .line 58
    .line 59
    float-to-int v6, v2

    .line 60
    float-to-int v7, v4

    .line 61
    aget p0, v0, v1

    .line 62
    .line 63
    invoke-virtual {p1}, Landroid/view/View;->getWidth()I

    .line 64
    .line 65
    .line 66
    move-result v1

    .line 67
    add-int v8, v1, p0

    .line 68
    .line 69
    aget p0, v0, v3

    .line 70
    .line 71
    invoke-virtual {p1}, Landroid/view/View;->getHeight()I

    .line 72
    .line 73
    .line 74
    move-result p1

    .line 75
    add-int v9, p1, p0

    .line 76
    .line 77
    sget-object v10, Landroid/graphics/Region$Op;->UNION:Landroid/graphics/Region$Op;

    .line 78
    .line 79
    invoke-virtual/range {v5 .. v10}, Landroid/graphics/Region;->op(IIIILandroid/graphics/Region$Op;)Z

    .line 80
    .line 81
    .line 82
    return-void
.end method

.method public final updateBackgroundForDrawerClosedAmount()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainerBackground:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->copyBounds()Landroid/graphics/Rect;

    .line 7
    .line 8
    .line 9
    move-result-object v0

    .line 10
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isLandscape()Z

    .line 11
    .line 12
    .line 13
    move-result v1

    .line 14
    if-nez v1, :cond_1

    .line 15
    .line 16
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerClosedAmount:F

    .line 17
    .line 18
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 19
    .line 20
    .line 21
    move-result v2

    .line 22
    int-to-float v2, v2

    .line 23
    mul-float/2addr v1, v2

    .line 24
    float-to-int v1, v1

    .line 25
    iput v1, v0, Landroid/graphics/Rect;->top:I

    .line 26
    .line 27
    goto :goto_0

    .line 28
    :cond_1
    iget v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerDrawerClosedAmount:F

    .line 29
    .line 30
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->getRingerDrawerOpenExtraSize()I

    .line 31
    .line 32
    .line 33
    move-result v2

    .line 34
    int-to-float v2, v2

    .line 35
    mul-float/2addr v1, v2

    .line 36
    float-to-int v1, v1

    .line 37
    iput v1, v0, Landroid/graphics/Rect;->left:I

    .line 38
    .line 39
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainerBackground:Landroid/graphics/drawable/Drawable;

    .line 40
    .line 41
    invoke-virtual {p0, v0}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    .line 42
    .line 43
    .line 44
    return-void
.end method

.method public final updateCaptionsIcon()V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 2
    .line 3
    invoke-interface {v0}, Lcom/android/systemui/plugins/VolumeDialogController;->areCaptionsEnabled()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mODICaptionsIcon:Lcom/android/systemui/volume/CaptionsToggleImageButton;

    .line 8
    .line 9
    iget-boolean v2, v1, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mCaptionsEnabled:Z

    .line 10
    .line 11
    if-eq v2, v0, :cond_2

    .line 12
    .line 13
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 14
    .line 15
    iput-boolean v0, v1, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mCaptionsEnabled:Z

    .line 16
    .line 17
    sget-object v2, Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;->ACTION_CLICK:Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;

    .line 18
    .line 19
    if-eqz v0, :cond_0

    .line 20
    .line 21
    invoke-virtual {v1}, Landroid/widget/ImageButton;->getContext()Landroid/content/Context;

    .line 22
    .line 23
    .line 24
    move-result-object v0

    .line 25
    const v3, 0x7f131216

    .line 26
    .line 27
    .line 28
    invoke-virtual {v0, v3}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 29
    .line 30
    .line 31
    move-result-object v0

    .line 32
    goto :goto_0

    .line 33
    :cond_0
    invoke-virtual {v1}, Landroid/widget/ImageButton;->getContext()Landroid/content/Context;

    .line 34
    .line 35
    .line 36
    move-result-object v0

    .line 37
    const v3, 0x7f131217

    .line 38
    .line 39
    .line 40
    invoke-virtual {v0, v3}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 41
    .line 42
    .line 43
    move-result-object v0

    .line 44
    :goto_0
    new-instance v3, Lcom/android/systemui/volume/CaptionsToggleImageButton$$ExternalSyntheticLambda0;

    .line 45
    .line 46
    invoke-direct {v3, v1}, Lcom/android/systemui/volume/CaptionsToggleImageButton$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/volume/CaptionsToggleImageButton;)V

    .line 47
    .line 48
    .line 49
    invoke-static {v1, v2, v0, v3}, Landroidx/core/view/ViewCompat;->replaceAccessibilityAction(Landroid/view/View;Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$AccessibilityActionCompat;Ljava/lang/CharSequence;Landroidx/core/view/accessibility/AccessibilityViewCommand;)V

    .line 50
    .line 51
    .line 52
    iget-boolean v0, v1, Lcom/android/systemui/volume/CaptionsToggleImageButton;->mCaptionsEnabled:Z

    .line 53
    .line 54
    if-eqz v0, :cond_1

    .line 55
    .line 56
    const v0, 0x7f080b0e

    .line 57
    .line 58
    .line 59
    goto :goto_1

    .line 60
    :cond_1
    const v0, 0x7f080b0f

    .line 61
    .line 62
    .line 63
    :goto_1
    invoke-virtual {v1, v0}, Landroid/widget/ImageButton;->setImageResourceAsync(I)Ljava/lang/Runnable;

    .line 64
    .line 65
    .line 66
    move-result-object v0

    .line 67
    invoke-virtual {p0, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 68
    .line 69
    .line 70
    :cond_2
    return-void
.end method

.method public final updateRingerH()V
    .locals 9

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRinger:Landroid/view/ViewGroup;

    .line 2
    .line 3
    if-eqz v0, :cond_d

    .line 4
    .line 5
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 6
    .line 7
    if-eqz v0, :cond_d

    .line 8
    .line 9
    iget-object v0, v0, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 10
    .line 11
    const/4 v1, 0x2

    .line 12
    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    .line 13
    .line 14
    .line 15
    move-result-object v0

    .line 16
    check-cast v0, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 17
    .line 18
    if-nez v0, :cond_0

    .line 19
    .line 20
    return-void

    .line 21
    :cond_0
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 22
    .line 23
    iget v3, v2, Lcom/android/systemui/plugins/VolumeDialogController$State;->zenMode:I

    .line 24
    .line 25
    const/4 v4, 0x3

    .line 26
    const/4 v5, 0x0

    .line 27
    const/4 v6, 0x1

    .line 28
    if-eq v3, v4, :cond_2

    .line 29
    .line 30
    if-eq v3, v1, :cond_2

    .line 31
    .line 32
    if-ne v3, v6, :cond_1

    .line 33
    .line 34
    iget-boolean v2, v2, Lcom/android/systemui/plugins/VolumeDialogController$State;->disallowRinger:Z

    .line 35
    .line 36
    if-eqz v2, :cond_1

    .line 37
    .line 38
    goto :goto_0

    .line 39
    :cond_1
    move v2, v5

    .line 40
    goto :goto_1

    .line 41
    :cond_2
    :goto_0
    move v2, v6

    .line 42
    :goto_1
    xor-int/lit8 v3, v2, 0x1

    .line 43
    .line 44
    iget-object v7, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 45
    .line 46
    if-eqz v7, :cond_3

    .line 47
    .line 48
    invoke-virtual {v7, v3}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 49
    .line 50
    .line 51
    :cond_3
    iget-object v7, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mZenIcon:Landroid/widget/FrameLayout;

    .line 52
    .line 53
    if-eqz v7, :cond_5

    .line 54
    .line 55
    if-eqz v3, :cond_4

    .line 56
    .line 57
    const/16 v3, 0x8

    .line 58
    .line 59
    goto :goto_2

    .line 60
    :cond_4
    move v3, v5

    .line 61
    :goto_2
    invoke-virtual {v7, v3}, Landroid/widget/FrameLayout;->setVisibility(I)V

    .line 62
    .line 63
    .line 64
    :cond_5
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 65
    .line 66
    iget v3, v3, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 67
    .line 68
    const v7, 0x7f13121f

    .line 69
    .line 70
    .line 71
    if-eqz v3, :cond_c

    .line 72
    .line 73
    const v8, 0x7f13121e

    .line 74
    .line 75
    .line 76
    if-eq v3, v6, :cond_b

    .line 77
    .line 78
    iget-boolean v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mAutomute:Z

    .line 79
    .line 80
    if-eqz v3, :cond_6

    .line 81
    .line 82
    iget v3, v0, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 83
    .line 84
    if-eqz v3, :cond_7

    .line 85
    .line 86
    :cond_6
    iget-boolean v0, v0, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 87
    .line 88
    if-eqz v0, :cond_8

    .line 89
    .line 90
    :cond_7
    move v5, v6

    .line 91
    :cond_8
    if-nez v2, :cond_9

    .line 92
    .line 93
    if-eqz v5, :cond_9

    .line 94
    .line 95
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 96
    .line 97
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 98
    .line 99
    invoke-virtual {v0, v2}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 100
    .line 101
    .line 102
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerIcon:Landroid/widget/ImageView;

    .line 103
    .line 104
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 105
    .line 106
    invoke-virtual {v0, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 107
    .line 108
    .line 109
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 110
    .line 111
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 112
    .line 113
    invoke-virtual {v2, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 114
    .line 115
    .line 116
    move-result-object v2

    .line 117
    invoke-virtual {p0, v0, v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V

    .line 118
    .line 119
    .line 120
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 121
    .line 122
    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 123
    .line 124
    .line 125
    move-result-object v0

    .line 126
    invoke-virtual {p0, v0}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 127
    .line 128
    .line 129
    goto/16 :goto_4

    .line 130
    .line 131
    :cond_9
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 132
    .line 133
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerIconDrawableId:I

    .line 134
    .line 135
    invoke-virtual {v0, v2}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 136
    .line 137
    .line 138
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerIcon:Landroid/widget/ImageView;

    .line 139
    .line 140
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerIconDrawableId:I

    .line 141
    .line 142
    invoke-virtual {v0, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 143
    .line 144
    .line 145
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 146
    .line 147
    invoke-interface {v0}, Lcom/android/systemui/plugins/VolumeDialogController;->hasVibrator()Z

    .line 148
    .line 149
    .line 150
    move-result v0

    .line 151
    if-eqz v0, :cond_a

    .line 152
    .line 153
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 154
    .line 155
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 156
    .line 157
    const v3, 0x7f131220

    .line 158
    .line 159
    .line 160
    invoke-virtual {v2, v3}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 161
    .line 162
    .line 163
    move-result-object v2

    .line 164
    invoke-virtual {p0, v0, v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V

    .line 165
    .line 166
    .line 167
    goto :goto_3

    .line 168
    :cond_a
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 169
    .line 170
    iget-object v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 171
    .line 172
    invoke-virtual {v2, v8}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 173
    .line 174
    .line 175
    move-result-object v2

    .line 176
    invoke-virtual {p0, v0, v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V

    .line 177
    .line 178
    .line 179
    :goto_3
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 180
    .line 181
    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 182
    .line 183
    .line 184
    move-result-object v0

    .line 185
    invoke-virtual {p0, v0}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 186
    .line 187
    .line 188
    goto :goto_4

    .line 189
    :cond_b
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 190
    .line 191
    const v1, 0x7f080b17

    .line 192
    .line 193
    .line 194
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 195
    .line 196
    .line 197
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerIcon:Landroid/widget/ImageView;

    .line 198
    .line 199
    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 200
    .line 201
    .line 202
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 203
    .line 204
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 205
    .line 206
    invoke-virtual {v1, v8}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 207
    .line 208
    .line 209
    move-result-object v1

    .line 210
    invoke-virtual {p0, v0, v6, v1}, Lcom/android/systemui/volume/VolumeDialogImpl;->addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V

    .line 211
    .line 212
    .line 213
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 214
    .line 215
    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 216
    .line 217
    .line 218
    move-result-object v0

    .line 219
    invoke-virtual {p0, v0}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 220
    .line 221
    .line 222
    goto :goto_4

    .line 223
    :cond_c
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 224
    .line 225
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 226
    .line 227
    invoke-virtual {v0, v2}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 228
    .line 229
    .line 230
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mSelectedRingerIcon:Landroid/widget/ImageView;

    .line 231
    .line 232
    iget v2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mVolumeRingerMuteIconDrawableId:I

    .line 233
    .line 234
    invoke-virtual {v0, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 235
    .line 236
    .line 237
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 238
    .line 239
    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 240
    .line 241
    .line 242
    move-result-object v1

    .line 243
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 244
    .line 245
    .line 246
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerIcon:Landroid/widget/ImageButton;

    .line 247
    .line 248
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 249
    .line 250
    invoke-virtual {v1, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 251
    .line 252
    .line 253
    move-result-object v1

    .line 254
    invoke-virtual {p0, v0, v5, v1}, Lcom/android/systemui/volume/VolumeDialogImpl;->addAccessibilityDescription(Landroid/view/View;ILjava/lang/String;)V

    .line 255
    .line 256
    .line 257
    :cond_d
    :goto_4
    return-void
.end method

.method public final updateRowsH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V
    .locals 11

    .line 1
    const-string v0, "VolumeDialogImpl#updateRowsH"

    .line 2
    .line 3
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    .line 4
    .line 5
    .line 6
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    .line 7
    .line 8
    if-eqz v0, :cond_0

    .line 9
    .line 10
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 11
    .line 12
    const-string/jumbo v1, "updateRowsH"

    .line 13
    .line 14
    .line 15
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 16
    .line 17
    .line 18
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 19
    .line 20
    if-nez v0, :cond_1

    .line 21
    .line 22
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->trimObsoleteH()V

    .line 23
    .line 24
    .line 25
    :cond_1
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isRtl()Z

    .line 26
    .line 27
    .line 28
    move-result v0

    .line 29
    const/4 v1, -0x1

    .line 30
    const/16 v2, 0x7fff

    .line 31
    .line 32
    if-nez v0, :cond_2

    .line 33
    .line 34
    move v0, v1

    .line 35
    goto :goto_0

    .line 36
    :cond_2
    move v0, v2

    .line 37
    :goto_0
    iget-object v3, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRows:Ljava/util/List;

    .line 38
    .line 39
    check-cast v3, Ljava/util/ArrayList;

    .line 40
    .line 41
    invoke-virtual {v3}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 42
    .line 43
    .line 44
    move-result-object v3

    .line 45
    :cond_3
    :goto_1
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    .line 46
    .line 47
    .line 48
    move-result v4

    .line 49
    const/4 v5, 0x0

    .line 50
    if-eqz v4, :cond_d

    .line 51
    .line 52
    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 53
    .line 54
    .line 55
    move-result-object v4

    .line 56
    check-cast v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;

    .line 57
    .line 58
    const/4 v6, 0x1

    .line 59
    if-ne v4, p1, :cond_4

    .line 60
    .line 61
    move v7, v6

    .line 62
    goto :goto_2

    .line 63
    :cond_4
    move v7, v5

    .line 64
    :goto_2
    invoke-virtual {p0, v4, p1}, Lcom/android/systemui/volume/VolumeDialogImpl;->shouldBeVisibleH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)Z

    .line 65
    .line 66
    .line 67
    move-result v8

    .line 68
    iget-object v9, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 69
    .line 70
    if-eqz v9, :cond_8

    .line 71
    .line 72
    invoke-virtual {v9}, Landroid/view/View;->getVisibility()I

    .line 73
    .line 74
    .line 75
    move-result v10

    .line 76
    if-nez v10, :cond_5

    .line 77
    .line 78
    goto :goto_3

    .line 79
    :cond_5
    move v6, v5

    .line 80
    :goto_3
    if-ne v6, v8, :cond_6

    .line 81
    .line 82
    goto :goto_5

    .line 83
    :cond_6
    if-eqz v8, :cond_7

    .line 84
    .line 85
    goto :goto_4

    .line 86
    :cond_7
    const/16 v5, 0x8

    .line 87
    .line 88
    :goto_4
    invoke-virtual {v9, v5}, Landroid/view/View;->setVisibility(I)V

    .line 89
    .line 90
    .line 91
    :cond_8
    :goto_5
    if-eqz v8, :cond_c

    .line 92
    .line 93
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerAndDrawerContainerBackground:Landroid/graphics/drawable/Drawable;

    .line 94
    .line 95
    if-eqz v5, :cond_c

    .line 96
    .line 97
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isRtl()Z

    .line 98
    .line 99
    .line 100
    move-result v5

    .line 101
    if-nez v5, :cond_9

    .line 102
    .line 103
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 104
    .line 105
    iget-object v6, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 106
    .line 107
    invoke-virtual {v5, v6}, Landroid/view/ViewGroup;->indexOfChild(Landroid/view/View;)I

    .line 108
    .line 109
    .line 110
    move-result v5

    .line 111
    invoke-static {v0, v5}, Ljava/lang/Math;->max(II)I

    .line 112
    .line 113
    .line 114
    move-result v0

    .line 115
    goto :goto_6

    .line 116
    :cond_9
    iget-object v5, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 117
    .line 118
    iget-object v6, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 119
    .line 120
    invoke-virtual {v5, v6}, Landroid/view/ViewGroup;->indexOfChild(Landroid/view/View;)I

    .line 121
    .line 122
    .line 123
    move-result v5

    .line 124
    invoke-static {v0, v5}, Ljava/lang/Math;->min(II)I

    .line 125
    .line 126
    .line 127
    move-result v0

    .line 128
    :goto_6
    iget-object v5, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 129
    .line 130
    invoke-virtual {v5}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 131
    .line 132
    .line 133
    move-result-object v5

    .line 134
    instance-of v6, v5, Landroid/widget/LinearLayout$LayoutParams;

    .line 135
    .line 136
    if-eqz v6, :cond_b

    .line 137
    .line 138
    check-cast v5, Landroid/widget/LinearLayout$LayoutParams;

    .line 139
    .line 140
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->isRtl()Z

    .line 141
    .line 142
    .line 143
    move-result v6

    .line 144
    if-nez v6, :cond_a

    .line 145
    .line 146
    iget v6, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerRowsPadding:I

    .line 147
    .line 148
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout$LayoutParams;->setMarginEnd(I)V

    .line 149
    .line 150
    .line 151
    goto :goto_7

    .line 152
    :cond_a
    iget v6, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mRingerRowsPadding:I

    .line 153
    .line 154
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout$LayoutParams;->setMarginStart(I)V

    .line 155
    .line 156
    .line 157
    :cond_b
    :goto_7
    iget-object v5, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 158
    .line 159
    iget-object v6, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 160
    .line 161
    const v8, 0x7f081323

    .line 162
    .line 163
    .line 164
    invoke-virtual {v6, v8}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    .line 165
    .line 166
    .line 167
    move-result-object v6

    .line 168
    invoke-virtual {v5, v6}, Landroid/view/View;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 169
    .line 170
    .line 171
    :cond_c
    iget-object v5, v4, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 172
    .line 173
    invoke-virtual {v5}, Landroid/view/View;->isShown()Z

    .line 174
    .line 175
    .line 176
    move-result v5

    .line 177
    if-eqz v5, :cond_3

    .line 178
    .line 179
    invoke-virtual {p0, v4, v7}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowTintH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Z)V

    .line 180
    .line 181
    .line 182
    goto/16 :goto_1

    .line 183
    .line 184
    :cond_d
    if-le v0, v1, :cond_e

    .line 185
    .line 186
    if-ge v0, v2, :cond_e

    .line 187
    .line 188
    iget-object p1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mDialogRowsView:Landroid/view/ViewGroup;

    .line 189
    .line 190
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    .line 191
    .line 192
    .line 193
    move-result-object p1

    .line 194
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 195
    .line 196
    .line 197
    move-result-object v0

    .line 198
    instance-of v1, v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 199
    .line 200
    if-eqz v1, :cond_e

    .line 201
    .line 202
    check-cast v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 203
    .line 204
    invoke-virtual {v0, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMarginStart(I)V

    .line 205
    .line 206
    .line 207
    invoke-virtual {v0, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMarginEnd(I)V

    .line 208
    .line 209
    .line 210
    invoke-virtual {p1, v5}, Landroid/view/View;->setBackgroundColor(I)V

    .line 211
    .line 212
    .line 213
    :cond_e
    invoke-virtual {p0}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateBackgroundForDrawerClosedAmount()V

    .line 214
    .line 215
    .line 216
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 217
    .line 218
    .line 219
    return-void
.end method

.method public final updateVolumeRowH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;)V
    .locals 17

    .line 1
    move-object/from16 v1, p0

    .line 2
    .line 3
    move-object/from16 v6, p1

    .line 4
    .line 5
    sget-boolean v0, Lcom/android/systemui/volume/D;->BUG:Z

    .line 6
    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    sget-object v2, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 10
    .line 11
    new-instance v3, Ljava/lang/StringBuilder;

    .line 12
    .line 13
    const-string/jumbo v4, "updateVolumeRowH s="

    .line 14
    .line 15
    .line 16
    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 17
    .line 18
    .line 19
    iget v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 20
    .line 21
    invoke-static {v3, v4, v2}, Landroidx/appcompat/widget/TooltipPopup$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ILjava/lang/String;)V

    .line 22
    .line 23
    .line 24
    :cond_0
    iget-object v2, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 25
    .line 26
    if-nez v2, :cond_1

    .line 27
    .line 28
    return-void

    .line 29
    :cond_1
    iget-object v2, v2, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    .line 30
    .line 31
    iget v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 32
    .line 33
    invoke-virtual {v2, v3}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    .line 34
    .line 35
    .line 36
    move-result-object v2

    .line 37
    check-cast v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 38
    .line 39
    if-nez v2, :cond_2

    .line 40
    .line 41
    return-void

    .line 42
    :cond_2
    iput-object v2, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->ss:Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 43
    .line 44
    iget v3, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 45
    .line 46
    if-lez v3, :cond_3

    .line 47
    .line 48
    iput v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->lastAudibleLevel:I

    .line 49
    .line 50
    :cond_3
    iget v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->requestedLevel:I

    .line 51
    .line 52
    if-ne v3, v4, :cond_4

    .line 53
    .line 54
    const/4 v3, -0x1

    .line 55
    iput v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->requestedLevel:I

    .line 56
    .line 57
    :cond_4
    iget v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 58
    .line 59
    const/4 v4, 0x1

    .line 60
    if-nez v3, :cond_5

    .line 61
    .line 62
    move v5, v4

    .line 63
    goto :goto_0

    .line 64
    :cond_5
    const/4 v5, 0x0

    .line 65
    :goto_0
    const/16 v7, 0xa

    .line 66
    .line 67
    if-ne v3, v7, :cond_6

    .line 68
    .line 69
    move v7, v4

    .line 70
    goto :goto_1

    .line 71
    :cond_6
    const/4 v7, 0x0

    .line 72
    :goto_1
    const/4 v8, 0x2

    .line 73
    if-ne v3, v8, :cond_7

    .line 74
    .line 75
    move v8, v4

    .line 76
    goto :goto_2

    .line 77
    :cond_7
    const/4 v8, 0x0

    .line 78
    :goto_2
    if-ne v3, v4, :cond_8

    .line 79
    .line 80
    move v9, v4

    .line 81
    goto :goto_3

    .line 82
    :cond_8
    const/4 v9, 0x0

    .line 83
    :goto_3
    const/4 v10, 0x4

    .line 84
    if-ne v3, v10, :cond_9

    .line 85
    .line 86
    move v10, v4

    .line 87
    goto :goto_4

    .line 88
    :cond_9
    const/4 v10, 0x0

    .line 89
    :goto_4
    const/4 v11, 0x3

    .line 90
    if-ne v3, v11, :cond_a

    .line 91
    .line 92
    move v3, v4

    .line 93
    goto :goto_5

    .line 94
    :cond_a
    const/4 v3, 0x0

    .line 95
    :goto_5
    if-eqz v8, :cond_b

    .line 96
    .line 97
    iget-object v12, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 98
    .line 99
    iget v12, v12, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 100
    .line 101
    if-ne v12, v4, :cond_b

    .line 102
    .line 103
    move v12, v4

    .line 104
    goto :goto_6

    .line 105
    :cond_b
    const/4 v12, 0x0

    .line 106
    :goto_6
    if-eqz v8, :cond_c

    .line 107
    .line 108
    iget-object v13, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 109
    .line 110
    iget v13, v13, Lcom/android/systemui/plugins/VolumeDialogController$State;->ringerModeInternal:I

    .line 111
    .line 112
    if-nez v13, :cond_c

    .line 113
    .line 114
    move v13, v4

    .line 115
    goto :goto_7

    .line 116
    :cond_c
    const/4 v13, 0x0

    .line 117
    :goto_7
    iget-object v14, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mState:Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 118
    .line 119
    iget v15, v14, Lcom/android/systemui/plugins/VolumeDialogController$State;->zenMode:I

    .line 120
    .line 121
    if-ne v15, v4, :cond_d

    .line 122
    .line 123
    move/from16 v16, v4

    .line 124
    .line 125
    goto :goto_8

    .line 126
    :cond_d
    const/16 v16, 0x0

    .line 127
    .line 128
    :goto_8
    if-ne v15, v11, :cond_e

    .line 129
    .line 130
    const/4 v11, 0x2

    .line 131
    goto :goto_9

    .line 132
    :cond_e
    const/4 v11, 0x2

    .line 133
    const/4 v4, 0x0

    .line 134
    :goto_9
    if-ne v15, v11, :cond_f

    .line 135
    .line 136
    const/4 v11, 0x1

    .line 137
    goto :goto_a

    .line 138
    :cond_f
    const/4 v11, 0x0

    .line 139
    :goto_a
    if-eqz v4, :cond_10

    .line 140
    .line 141
    if-nez v8, :cond_15

    .line 142
    .line 143
    if-eqz v9, :cond_16

    .line 144
    .line 145
    goto :goto_b

    .line 146
    :cond_10
    if-eqz v11, :cond_11

    .line 147
    .line 148
    if-nez v8, :cond_15

    .line 149
    .line 150
    if-nez v9, :cond_15

    .line 151
    .line 152
    if-nez v10, :cond_15

    .line 153
    .line 154
    if-eqz v3, :cond_16

    .line 155
    .line 156
    goto :goto_b

    .line 157
    :cond_11
    if-eqz v16, :cond_16

    .line 158
    .line 159
    if-eqz v10, :cond_12

    .line 160
    .line 161
    iget-boolean v4, v14, Lcom/android/systemui/plugins/VolumeDialogController$State;->disallowAlarms:Z

    .line 162
    .line 163
    if-nez v4, :cond_15

    .line 164
    .line 165
    :cond_12
    if-eqz v3, :cond_13

    .line 166
    .line 167
    iget-boolean v3, v14, Lcom/android/systemui/plugins/VolumeDialogController$State;->disallowMedia:Z

    .line 168
    .line 169
    if-nez v3, :cond_15

    .line 170
    .line 171
    :cond_13
    if-eqz v8, :cond_14

    .line 172
    .line 173
    iget-boolean v3, v14, Lcom/android/systemui/plugins/VolumeDialogController$State;->disallowRinger:Z

    .line 174
    .line 175
    if-nez v3, :cond_15

    .line 176
    .line 177
    :cond_14
    if-eqz v9, :cond_16

    .line 178
    .line 179
    iget-boolean v3, v14, Lcom/android/systemui/plugins/VolumeDialogController$State;->disallowSystem:Z

    .line 180
    .line 181
    if-eqz v3, :cond_16

    .line 182
    .line 183
    :cond_15
    :goto_b
    const/4 v3, 0x1

    .line 184
    goto :goto_c

    .line 185
    :cond_16
    const/4 v3, 0x0

    .line 186
    :goto_c
    iget v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->levelMax:I

    .line 187
    .line 188
    mul-int/lit8 v4, v4, 0x64

    .line 189
    .line 190
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 191
    .line 192
    invoke-virtual {v9}, Landroid/widget/SeekBar;->getMax()I

    .line 193
    .line 194
    .line 195
    move-result v9

    .line 196
    if-eq v4, v9, :cond_17

    .line 197
    .line 198
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 199
    .line 200
    invoke-virtual {v9, v4}, Landroid/widget/SeekBar;->setMax(I)V

    .line 201
    .line 202
    .line 203
    :cond_17
    iget v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->levelMin:I

    .line 204
    .line 205
    mul-int/lit8 v4, v4, 0x64

    .line 206
    .line 207
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 208
    .line 209
    invoke-virtual {v9}, Landroid/widget/SeekBar;->getMin()I

    .line 210
    .line 211
    .line 212
    move-result v9

    .line 213
    if-eq v4, v9, :cond_18

    .line 214
    .line 215
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 216
    .line 217
    invoke-virtual {v9, v4}, Landroid/widget/SeekBar;->setMin(I)V

    .line 218
    .line 219
    .line 220
    :cond_18
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 221
    .line 222
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 223
    .line 224
    .line 225
    move-result-object v9

    .line 226
    invoke-virtual {v4}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    .line 227
    .line 228
    .line 229
    move-result-object v10

    .line 230
    const/4 v11, 0x0

    .line 231
    if-eqz v10, :cond_19

    .line 232
    .line 233
    invoke-interface {v10}, Ljava/lang/CharSequence;->length()I

    .line 234
    .line 235
    .line 236
    move-result v14

    .line 237
    if-nez v14, :cond_1a

    .line 238
    .line 239
    :cond_19
    move-object v10, v11

    .line 240
    :cond_1a
    if-eqz v9, :cond_1c

    .line 241
    .line 242
    invoke-virtual {v9}, Ljava/lang/String;->length()I

    .line 243
    .line 244
    .line 245
    move-result v14

    .line 246
    if-nez v14, :cond_1b

    .line 247
    .line 248
    goto :goto_d

    .line 249
    :cond_1b
    move-object v11, v9

    .line 250
    :cond_1c
    :goto_d
    invoke-static {v10, v11}, Ljava/util/Objects;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 251
    .line 252
    .line 253
    move-result v10

    .line 254
    if-eqz v10, :cond_1d

    .line 255
    .line 256
    goto :goto_e

    .line 257
    :cond_1d
    invoke-virtual {v4, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 258
    .line 259
    .line 260
    :goto_e
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 261
    .line 262
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 263
    .line 264
    invoke-virtual {v9}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    .line 265
    .line 266
    .line 267
    move-result-object v9

    .line 268
    invoke-virtual {v4, v9}, Landroid/widget/SeekBar;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 269
    .line 270
    .line 271
    iget-object v4, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mConfigurableTexts:Lcom/android/systemui/volume/ConfigurableTexts;

    .line 272
    .line 273
    iget-object v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->header:Landroid/widget/TextView;

    .line 274
    .line 275
    iget v10, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->name:I

    .line 276
    .line 277
    if-nez v9, :cond_1e

    .line 278
    .line 279
    invoke-virtual {v4}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 280
    .line 281
    .line 282
    goto :goto_f

    .line 283
    :cond_1e
    iget-object v11, v4, Lcom/android/systemui/volume/ConfigurableTexts;->mTexts:Landroid/util/ArrayMap;

    .line 284
    .line 285
    invoke-virtual {v11, v9}, Landroid/util/ArrayMap;->containsKey(Ljava/lang/Object;)Z

    .line 286
    .line 287
    .line 288
    move-result v14

    .line 289
    if-eqz v14, :cond_1f

    .line 290
    .line 291
    invoke-virtual {v11, v9}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 292
    .line 293
    .line 294
    move-result-object v4

    .line 295
    check-cast v4, Ljava/lang/Integer;

    .line 296
    .line 297
    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    .line 298
    .line 299
    .line 300
    goto :goto_f

    .line 301
    :cond_1f
    iget-object v14, v4, Lcom/android/systemui/volume/ConfigurableTexts;->mContext:Landroid/content/Context;

    .line 302
    .line 303
    invoke-virtual {v14}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 304
    .line 305
    .line 306
    move-result-object v14

    .line 307
    invoke-virtual {v14}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 308
    .line 309
    .line 310
    move-result-object v15

    .line 311
    iget v15, v15, Landroid/content/res/Configuration;->fontScale:F

    .line 312
    .line 313
    invoke-virtual {v14}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 314
    .line 315
    .line 316
    move-result-object v14

    .line 317
    iget v14, v14, Landroid/util/DisplayMetrics;->density:F

    .line 318
    .line 319
    invoke-virtual {v9}, Landroid/widget/TextView;->getTextSize()F

    .line 320
    .line 321
    .line 322
    move-result v16

    .line 323
    div-float v16, v16, v15

    .line 324
    .line 325
    div-float v14, v16, v14

    .line 326
    .line 327
    float-to-int v14, v14

    .line 328
    invoke-static {v14}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 329
    .line 330
    .line 331
    move-result-object v15

    .line 332
    invoke-virtual {v11, v9, v15}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 333
    .line 334
    .line 335
    new-instance v11, Lcom/android/systemui/volume/ConfigurableTexts$1;

    .line 336
    .line 337
    invoke-direct {v11, v4, v9, v14}, Lcom/android/systemui/volume/ConfigurableTexts$1;-><init>(Lcom/android/systemui/volume/ConfigurableTexts;Landroid/widget/TextView;I)V

    .line 338
    .line 339
    .line 340
    invoke-virtual {v9, v11}, Landroid/widget/TextView;->addOnAttachStateChangeListener(Landroid/view/View$OnAttachStateChangeListener;)V

    .line 341
    .line 342
    .line 343
    iget-object v4, v4, Lcom/android/systemui/volume/ConfigurableTexts;->mTextLabels:Landroid/util/ArrayMap;

    .line 344
    .line 345
    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 346
    .line 347
    .line 348
    move-result-object v10

    .line 349
    invoke-virtual {v4, v9, v10}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 350
    .line 351
    .line 352
    :goto_f
    iget-boolean v4, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mAutomute:Z

    .line 353
    .line 354
    if-nez v4, :cond_20

    .line 355
    .line 356
    iget-boolean v9, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muteSupported:Z

    .line 357
    .line 358
    if-eqz v9, :cond_21

    .line 359
    .line 360
    :cond_20
    if-nez v3, :cond_21

    .line 361
    .line 362
    const/4 v9, 0x1

    .line 363
    goto :goto_10

    .line 364
    :cond_21
    const/4 v9, 0x0

    .line 365
    :goto_10
    const v10, 0x7f080b09

    .line 366
    .line 367
    .line 368
    const v11, 0x7f080b0a

    .line 369
    .line 370
    .line 371
    const v14, 0x7f080b17

    .line 372
    .line 373
    .line 374
    if-eqz v12, :cond_22

    .line 375
    .line 376
    move v4, v14

    .line 377
    goto :goto_14

    .line 378
    :cond_22
    if-nez v13, :cond_30

    .line 379
    .line 380
    if-eqz v3, :cond_23

    .line 381
    .line 382
    goto :goto_13

    .line 383
    :cond_23
    iget-boolean v13, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->routedToBluetooth:Z

    .line 384
    .line 385
    if-eqz v13, :cond_29

    .line 386
    .line 387
    if-eqz v5, :cond_24

    .line 388
    .line 389
    const v4, 0x7f080b04

    .line 390
    .line 391
    .line 392
    goto :goto_14

    .line 393
    :cond_24
    if-eqz v4, :cond_25

    .line 394
    .line 395
    iget v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 396
    .line 397
    if-eqz v4, :cond_26

    .line 398
    .line 399
    :cond_25
    iget-boolean v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 400
    .line 401
    if-eqz v4, :cond_27

    .line 402
    .line 403
    :cond_26
    const/4 v4, 0x1

    .line 404
    goto :goto_11

    .line 405
    :cond_27
    const/4 v4, 0x0

    .line 406
    :goto_11
    if-eqz v4, :cond_28

    .line 407
    .line 408
    move v4, v11

    .line 409
    goto :goto_14

    .line 410
    :cond_28
    move v4, v10

    .line 411
    goto :goto_14

    .line 412
    :cond_29
    if-eqz v4, :cond_2a

    .line 413
    .line 414
    iget v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 415
    .line 416
    if-eqz v4, :cond_2b

    .line 417
    .line 418
    :cond_2a
    iget-boolean v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 419
    .line 420
    if-eqz v4, :cond_2c

    .line 421
    .line 422
    :cond_2b
    const/4 v4, 0x1

    .line 423
    goto :goto_12

    .line 424
    :cond_2c
    const/4 v4, 0x0

    .line 425
    :goto_12
    if-eqz v4, :cond_2e

    .line 426
    .line 427
    iget-boolean v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 428
    .line 429
    if-eqz v4, :cond_2d

    .line 430
    .line 431
    const v4, 0x7f080b0d

    .line 432
    .line 433
    .line 434
    goto :goto_14

    .line 435
    :cond_2d
    iget v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconMuteRes:I

    .line 436
    .line 437
    goto :goto_14

    .line 438
    :cond_2e
    iget-boolean v4, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowLowMediaVolumeIcon:Z

    .line 439
    .line 440
    if-eqz v4, :cond_2f

    .line 441
    .line 442
    iget v4, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 443
    .line 444
    mul-int/lit8 v4, v4, 0x2

    .line 445
    .line 446
    iget v5, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->levelMax:I

    .line 447
    .line 448
    iget v13, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->levelMin:I

    .line 449
    .line 450
    add-int/2addr v5, v13

    .line 451
    if-ge v4, v5, :cond_2f

    .line 452
    .line 453
    const v4, 0x7f080b0b

    .line 454
    .line 455
    .line 456
    goto :goto_14

    .line 457
    :cond_2f
    iget v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconRes:I

    .line 458
    .line 459
    goto :goto_14

    .line 460
    :cond_30
    :goto_13
    iget v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconMuteRes:I

    .line 461
    .line 462
    :goto_14
    iget-object v5, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 463
    .line 464
    invoke-virtual {v5}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    .line 465
    .line 466
    .line 467
    move-result-object v5

    .line 468
    iget-object v13, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 469
    .line 470
    if-eqz v13, :cond_31

    .line 471
    .line 472
    invoke-virtual {v13, v4}, Landroid/widget/ImageButton;->setImageResource(I)V

    .line 473
    .line 474
    .line 475
    :cond_31
    iget-object v13, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressIcon:Lcom/android/systemui/util/AlphaTintDrawableWrapper;

    .line 476
    .line 477
    if-eqz v13, :cond_32

    .line 478
    .line 479
    iget-object v15, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 480
    .line 481
    invoke-virtual {v15}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    .line 482
    .line 483
    .line 484
    move-result-object v15

    .line 485
    invoke-virtual {v15, v4, v5}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    .line 486
    .line 487
    .line 488
    move-result-object v5

    .line 489
    invoke-virtual {v13, v5}, Landroid/graphics/drawable/InsetDrawable;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 490
    .line 491
    .line 492
    :cond_32
    if-ne v4, v14, :cond_33

    .line 493
    .line 494
    const/4 v4, 0x3

    .line 495
    goto :goto_17

    .line 496
    :cond_33
    if-eq v4, v11, :cond_37

    .line 497
    .line 498
    iget v5, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconMuteRes:I

    .line 499
    .line 500
    if-ne v4, v5, :cond_34

    .line 501
    .line 502
    goto :goto_16

    .line 503
    :cond_34
    if-eq v4, v10, :cond_36

    .line 504
    .line 505
    iget v5, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconRes:I

    .line 506
    .line 507
    if-eq v4, v5, :cond_36

    .line 508
    .line 509
    const v5, 0x7f080b0b

    .line 510
    .line 511
    .line 512
    if-ne v4, v5, :cond_35

    .line 513
    .line 514
    goto :goto_15

    .line 515
    :cond_35
    const/4 v4, 0x0

    .line 516
    goto :goto_17

    .line 517
    :cond_36
    :goto_15
    const/4 v4, 0x1

    .line 518
    goto :goto_17

    .line 519
    :cond_37
    :goto_16
    const/4 v4, 0x2

    .line 520
    :goto_17
    iput v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->iconState:I

    .line 521
    .line 522
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 523
    .line 524
    if-eqz v4, :cond_42

    .line 525
    .line 526
    if-eqz v9, :cond_41

    .line 527
    .line 528
    const v5, 0x7f131227

    .line 529
    .line 530
    .line 531
    const v9, 0x7f131226

    .line 532
    .line 533
    .line 534
    const v10, 0x7f131228

    .line 535
    .line 536
    .line 537
    if-eqz v8, :cond_3c

    .line 538
    .line 539
    if-eqz v12, :cond_38

    .line 540
    .line 541
    iget-object v5, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 542
    .line 543
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 544
    .line 545
    .line 546
    move-result-object v2

    .line 547
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 548
    .line 549
    .line 550
    move-result-object v2

    .line 551
    invoke-virtual {v5, v10, v2}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 552
    .line 553
    .line 554
    move-result-object v2

    .line 555
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 556
    .line 557
    .line 558
    goto/16 :goto_1c

    .line 559
    .line 560
    :cond_38
    iget-object v4, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mController:Lcom/android/systemui/plugins/VolumeDialogController;

    .line 561
    .line 562
    invoke-interface {v4}, Lcom/android/systemui/plugins/VolumeDialogController;->hasVibrator()Z

    .line 563
    .line 564
    .line 565
    move-result v4

    .line 566
    if-eqz v4, :cond_3a

    .line 567
    .line 568
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 569
    .line 570
    iget-object v5, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 571
    .line 572
    iget-boolean v7, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowA11yStream:Z

    .line 573
    .line 574
    if-eqz v7, :cond_39

    .line 575
    .line 576
    const v7, 0x7f13122a

    .line 577
    .line 578
    .line 579
    goto :goto_18

    .line 580
    :cond_39
    const v7, 0x7f131229

    .line 581
    .line 582
    .line 583
    :goto_18
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 584
    .line 585
    .line 586
    move-result-object v2

    .line 587
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 588
    .line 589
    .line 590
    move-result-object v2

    .line 591
    invoke-virtual {v5, v7, v2}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 592
    .line 593
    .line 594
    move-result-object v2

    .line 595
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 596
    .line 597
    .line 598
    goto :goto_1c

    .line 599
    :cond_3a
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 600
    .line 601
    iget-object v7, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 602
    .line 603
    iget-boolean v10, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowA11yStream:Z

    .line 604
    .line 605
    if-eqz v10, :cond_3b

    .line 606
    .line 607
    goto :goto_19

    .line 608
    :cond_3b
    move v5, v9

    .line 609
    :goto_19
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 610
    .line 611
    .line 612
    move-result-object v2

    .line 613
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 614
    .line 615
    .line 616
    move-result-object v2

    .line 617
    invoke-virtual {v7, v5, v2}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 618
    .line 619
    .line 620
    move-result-object v2

    .line 621
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 622
    .line 623
    .line 624
    goto :goto_1c

    .line 625
    :cond_3c
    if-eqz v7, :cond_3d

    .line 626
    .line 627
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 628
    .line 629
    .line 630
    move-result-object v2

    .line 631
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 632
    .line 633
    .line 634
    goto :goto_1c

    .line 635
    :cond_3d
    iget-boolean v7, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 636
    .line 637
    if-nez v7, :cond_40

    .line 638
    .line 639
    iget-boolean v7, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mAutomute:Z

    .line 640
    .line 641
    if-eqz v7, :cond_3e

    .line 642
    .line 643
    iget v7, v2, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 644
    .line 645
    if-nez v7, :cond_3e

    .line 646
    .line 647
    goto :goto_1b

    .line 648
    :cond_3e
    iget-object v7, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 649
    .line 650
    iget-boolean v10, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowA11yStream:Z

    .line 651
    .line 652
    if-eqz v10, :cond_3f

    .line 653
    .line 654
    goto :goto_1a

    .line 655
    :cond_3f
    move v5, v9

    .line 656
    :goto_1a
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 657
    .line 658
    .line 659
    move-result-object v2

    .line 660
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 661
    .line 662
    .line 663
    move-result-object v2

    .line 664
    invoke-virtual {v7, v5, v2}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 665
    .line 666
    .line 667
    move-result-object v2

    .line 668
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 669
    .line 670
    .line 671
    goto :goto_1c

    .line 672
    :cond_40
    :goto_1b
    iget-object v5, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 673
    .line 674
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 675
    .line 676
    .line 677
    move-result-object v2

    .line 678
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 679
    .line 680
    .line 681
    move-result-object v2

    .line 682
    invoke-virtual {v5, v10, v2}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    .line 683
    .line 684
    .line 685
    move-result-object v2

    .line 686
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 687
    .line 688
    .line 689
    goto :goto_1c

    .line 690
    :cond_41
    invoke-virtual {v1, v2}, Lcom/android/systemui/volume/VolumeDialogImpl;->getStreamLabelH(Lcom/android/systemui/plugins/VolumeDialogController$StreamState;)Ljava/lang/String;

    .line 691
    .line 692
    .line 693
    move-result-object v2

    .line 694
    invoke-virtual {v4, v2}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 695
    .line 696
    .line 697
    :cond_42
    :goto_1c
    const/4 v2, 0x0

    .line 698
    if-eqz v3, :cond_43

    .line 699
    .line 700
    iput-boolean v2, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->tracking:Z

    .line 701
    .line 702
    :cond_43
    xor-int/lit8 v4, v3, 0x1

    .line 703
    .line 704
    xor-int/lit8 v5, v4, 0x1

    .line 705
    .line 706
    iget-object v7, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->dndIcon:Landroid/widget/FrameLayout;

    .line 707
    .line 708
    if-eqz v5, :cond_44

    .line 709
    .line 710
    move v5, v2

    .line 711
    goto :goto_1d

    .line 712
    :cond_44
    const/16 v5, 0x8

    .line 713
    .line 714
    :goto_1d
    invoke-virtual {v7, v5}, Landroid/widget/FrameLayout;->setVisibility(I)V

    .line 715
    .line 716
    .line 717
    iget-object v5, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->ss:Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 718
    .line 719
    iget-boolean v7, v5, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    .line 720
    .line 721
    if-eqz v7, :cond_45

    .line 722
    .line 723
    if-nez v8, :cond_45

    .line 724
    .line 725
    if-nez v3, :cond_45

    .line 726
    .line 727
    move v7, v2

    .line 728
    goto :goto_1e

    .line 729
    :cond_45
    iget v3, v5, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    .line 730
    .line 731
    move v7, v3

    .line 732
    :goto_1e
    const-string v3, "VolumeDialogImpl#updateVolumeRowSliderH"

    .line 733
    .line 734
    invoke-static {v3}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    .line 735
    .line 736
    .line 737
    iget-object v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 738
    .line 739
    invoke-virtual {v3, v4}, Landroid/widget/SeekBar;->setEnabled(Z)V

    .line 740
    .line 741
    .line 742
    iget v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->stream:I

    .line 743
    .line 744
    iget v4, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mActiveStream:I

    .line 745
    .line 746
    if-ne v3, v4, :cond_46

    .line 747
    .line 748
    const/4 v3, 0x1

    .line 749
    goto :goto_1f

    .line 750
    :cond_46
    move v3, v2

    .line 751
    :goto_1f
    invoke-virtual {v1, v6, v3}, Lcom/android/systemui/volume/VolumeDialogImpl;->updateVolumeRowTintH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Z)V

    .line 752
    .line 753
    .line 754
    iget-boolean v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->tracking:Z

    .line 755
    .line 756
    if-eqz v3, :cond_47

    .line 757
    .line 758
    goto/16 :goto_22

    .line 759
    .line 760
    :cond_47
    iget-object v3, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 761
    .line 762
    invoke-virtual {v3}, Landroid/widget/SeekBar;->getProgress()I

    .line 763
    .line 764
    .line 765
    move-result v3

    .line 766
    iget-object v4, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 767
    .line 768
    invoke-static {v4, v3}, Lcom/android/systemui/volume/VolumeDialogImpl;->getImpliedLevel(Landroid/widget/SeekBar;I)I

    .line 769
    .line 770
    .line 771
    move-result v4

    .line 772
    iget-object v5, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 773
    .line 774
    invoke-virtual {v5}, Landroid/view/View;->getVisibility()I

    .line 775
    .line 776
    .line 777
    move-result v5

    .line 778
    if-nez v5, :cond_48

    .line 779
    .line 780
    const/4 v5, 0x1

    .line 781
    goto :goto_20

    .line 782
    :cond_48
    move v5, v2

    .line 783
    :goto_20
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    .line 784
    .line 785
    .line 786
    move-result-wide v8

    .line 787
    iget-wide v10, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->userAttempt:J

    .line 788
    .line 789
    sub-long/2addr v8, v10

    .line 790
    const-wide/16 v10, 0x3e8

    .line 791
    .line 792
    cmp-long v8, v8, v10

    .line 793
    .line 794
    if-gez v8, :cond_49

    .line 795
    .line 796
    const/4 v2, 0x1

    .line 797
    :cond_49
    iget-object v8, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 798
    .line 799
    const/4 v9, 0x3

    .line 800
    invoke-virtual {v8, v9, v6}, Landroid/os/Handler;->removeMessages(ILjava/lang/Object;)V

    .line 801
    .line 802
    .line 803
    iget-boolean v8, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mShowing:Z

    .line 804
    .line 805
    if-eqz v8, :cond_4b

    .line 806
    .line 807
    if-eqz v5, :cond_4b

    .line 808
    .line 809
    if-eqz v2, :cond_4b

    .line 810
    .line 811
    if-eqz v0, :cond_4a

    .line 812
    .line 813
    sget-object v0, Lcom/android/systemui/volume/VolumeDialogImpl;->TAG:Ljava/lang/String;

    .line 814
    .line 815
    const-string v2, "inGracePeriod"

    .line 816
    .line 817
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 818
    .line 819
    .line 820
    :cond_4a
    iget-object v0, v1, Lcom/android/systemui/volume/VolumeDialogImpl;->mHandler:Lcom/android/systemui/volume/VolumeDialogImpl$H;

    .line 821
    .line 822
    const/4 v1, 0x3

    .line 823
    invoke-virtual {v0, v1, v6}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    .line 824
    .line 825
    .line 826
    move-result-object v1

    .line 827
    iget-wide v2, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->userAttempt:J

    .line 828
    .line 829
    add-long/2addr v2, v10

    .line 830
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageAtTime(Landroid/os/Message;J)Z

    .line 831
    .line 832
    .line 833
    goto/16 :goto_22

    .line 834
    .line 835
    :cond_4b
    if-ne v7, v4, :cond_4c

    .line 836
    .line 837
    if-eqz v8, :cond_4c

    .line 838
    .line 839
    if-eqz v5, :cond_4c

    .line 840
    .line 841
    goto/16 :goto_22

    .line 842
    .line 843
    :cond_4c
    mul-int/lit8 v9, v7, 0x64

    .line 844
    .line 845
    if-eq v3, v9, :cond_51

    .line 846
    .line 847
    if-eqz v8, :cond_4f

    .line 848
    .line 849
    if-eqz v5, :cond_4f

    .line 850
    .line 851
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 852
    .line 853
    if-eqz v0, :cond_4d

    .line 854
    .line 855
    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->isRunning()Z

    .line 856
    .line 857
    .line 858
    move-result v0

    .line 859
    if-eqz v0, :cond_4d

    .line 860
    .line 861
    iget v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->animTargetProgress:I

    .line 862
    .line 863
    if-ne v0, v9, :cond_4d

    .line 864
    .line 865
    goto :goto_22

    .line 866
    :cond_4d
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 867
    .line 868
    if-nez v0, :cond_4e

    .line 869
    .line 870
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 871
    .line 872
    const-string/jumbo v2, "progress"

    .line 873
    .line 874
    .line 875
    filled-new-array {v3, v9}, [I

    .line 876
    .line 877
    .line 878
    move-result-object v3

    .line 879
    invoke-static {v0, v2, v3}, Landroid/animation/ObjectAnimator;->ofInt(Ljava/lang/Object;Ljava/lang/String;[I)Landroid/animation/ObjectAnimator;

    .line 880
    .line 881
    .line 882
    move-result-object v0

    .line 883
    iput-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 884
    .line 885
    new-instance v2, Landroid/view/animation/DecelerateInterpolator;

    .line 886
    .line 887
    invoke-direct {v2}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    .line 888
    .line 889
    .line 890
    invoke-virtual {v0, v2}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 891
    .line 892
    .line 893
    iget-object v8, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 894
    .line 895
    iget-object v2, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->view:Landroid/view/View;

    .line 896
    .line 897
    const-string/jumbo v3, "update"

    .line 898
    .line 899
    .line 900
    new-instance v10, Lcom/android/systemui/volume/VolumeDialogImpl$3;

    .line 901
    .line 902
    const-wide/16 v4, 0x50

    .line 903
    .line 904
    move-object v0, v10

    .line 905
    move-object/from16 v1, p0

    .line 906
    .line 907
    invoke-direct/range {v0 .. v5}, Lcom/android/systemui/volume/VolumeDialogImpl$3;-><init>(Lcom/android/systemui/volume/VolumeDialogImpl;Landroid/view/View;Ljava/lang/String;J)V

    .line 908
    .line 909
    .line 910
    invoke-virtual {v8, v10}, Landroid/animation/ObjectAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 911
    .line 912
    .line 913
    goto :goto_21

    .line 914
    :cond_4e
    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->cancel()V

    .line 915
    .line 916
    .line 917
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 918
    .line 919
    filled-new-array {v3, v9}, [I

    .line 920
    .line 921
    .line 922
    move-result-object v1

    .line 923
    invoke-virtual {v0, v1}, Landroid/animation/ObjectAnimator;->setIntValues([I)V

    .line 924
    .line 925
    .line 926
    :goto_21
    iput v9, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->animTargetProgress:I

    .line 927
    .line 928
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 929
    .line 930
    const-wide/16 v1, 0x50

    .line 931
    .line 932
    invoke-virtual {v0, v1, v2}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    .line 933
    .line 934
    .line 935
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 936
    .line 937
    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->start()V

    .line 938
    .line 939
    .line 940
    goto :goto_22

    .line 941
    :cond_4f
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->anim:Landroid/animation/ObjectAnimator;

    .line 942
    .line 943
    if-eqz v0, :cond_50

    .line 944
    .line 945
    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->cancel()V

    .line 946
    .line 947
    .line 948
    :cond_50
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 949
    .line 950
    const/4 v1, 0x1

    .line 951
    invoke-virtual {v0, v9, v1}, Landroid/widget/SeekBar;->setProgress(IZ)V

    .line 952
    .line 953
    .line 954
    :cond_51
    :goto_22
    invoke-static {}, Landroid/os/Trace;->endSection()V

    .line 955
    .line 956
    .line 957
    iget-object v0, v6, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->number:Landroid/widget/TextView;

    .line 958
    .line 959
    if-eqz v0, :cond_52

    .line 960
    .line 961
    invoke-static {v7}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .line 962
    .line 963
    .line 964
    move-result-object v1

    .line 965
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 966
    .line 967
    .line 968
    :cond_52
    return-void
.end method

.method public final updateVolumeRowTintH(Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;Z)V
    .locals 3

    .line 1
    if-eqz p2, :cond_0

    .line 2
    .line 3
    iget-object v0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 4
    .line 5
    invoke-virtual {v0}, Landroid/widget/SeekBar;->requestFocus()Z

    .line 6
    .line 7
    .line 8
    :cond_0
    const/4 v0, 0x0

    .line 9
    if-eqz p2, :cond_1

    .line 10
    .line 11
    iget-object p2, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->slider:Landroid/widget/SeekBar;

    .line 12
    .line 13
    invoke-virtual {p2}, Landroid/widget/SeekBar;->isEnabled()Z

    .line 14
    .line 15
    .line 16
    move-result p2

    .line 17
    if-eqz p2, :cond_1

    .line 18
    .line 19
    const/4 p2, 0x1

    .line 20
    goto :goto_0

    .line 21
    :cond_1
    move p2, v0

    .line 22
    :goto_0
    if-nez p2, :cond_2

    .line 23
    .line 24
    iget-boolean v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mChangeVolumeRowTintWhenInactive:Z

    .line 25
    .line 26
    if-nez v1, :cond_2

    .line 27
    .line 28
    return-void

    .line 29
    :cond_2
    if-eqz p2, :cond_3

    .line 30
    .line 31
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 32
    .line 33
    const v2, 0x1010435

    .line 34
    .line 35
    .line 36
    invoke-static {v2, v1}, Lcom/android/settingslib/Utils;->getColorAttr(ILandroid/content/Context;)Landroid/content/res/ColorStateList;

    .line 37
    .line 38
    .line 39
    move-result-object v1

    .line 40
    goto :goto_1

    .line 41
    :cond_3
    iget-object v1, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 42
    .line 43
    const v2, 0x1120028

    .line 44
    .line 45
    .line 46
    invoke-static {v2, v1}, Lcom/android/settingslib/Utils;->getColorAttr(ILandroid/content/Context;)Landroid/content/res/ColorStateList;

    .line 47
    .line 48
    .line 49
    move-result-object v1

    .line 50
    :goto_1
    if-eqz p2, :cond_4

    .line 51
    .line 52
    invoke-virtual {v1}, Landroid/content/res/ColorStateList;->getDefaultColor()I

    .line 53
    .line 54
    .line 55
    move-result p2

    .line 56
    invoke-static {p2}, Landroid/graphics/Color;->alpha(I)I

    .line 57
    .line 58
    .line 59
    move-result p2

    .line 60
    goto :goto_2

    .line 61
    :cond_4
    iget-object p2, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 62
    .line 63
    const v2, 0x1010553

    .line 64
    .line 65
    .line 66
    filled-new-array {v2}, [I

    .line 67
    .line 68
    .line 69
    move-result-object v2

    .line 70
    invoke-virtual {p2, v2}, Landroid/content/Context;->obtainStyledAttributes([I)Landroid/content/res/TypedArray;

    .line 71
    .line 72
    .line 73
    move-result-object p2

    .line 74
    const/4 v2, 0x0

    .line 75
    invoke-virtual {p2, v0, v2}, Landroid/content/res/TypedArray;->getFloat(IF)F

    .line 76
    .line 77
    .line 78
    move-result v0

    .line 79
    invoke-virtual {p2}, Landroid/content/res/TypedArray;->recycle()V

    .line 80
    .line 81
    .line 82
    const/high16 p2, 0x437f0000    # 255.0f

    .line 83
    .line 84
    mul-float/2addr v0, p2

    .line 85
    float-to-int p2, v0

    .line 86
    :goto_2
    iget-object v0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 87
    .line 88
    const v2, 0x10104e2

    .line 89
    .line 90
    .line 91
    invoke-static {v2, v0}, Lcom/android/settingslib/Utils;->getColorAttr(ILandroid/content/Context;)Landroid/content/res/ColorStateList;

    .line 92
    .line 93
    .line 94
    move-result-object v0

    .line 95
    iget-object p0, p0, Lcom/android/systemui/volume/VolumeDialogImpl;->mContext:Landroid/content/Context;

    .line 96
    .line 97
    const v2, 0x112016e

    .line 98
    .line 99
    .line 100
    invoke-static {v2, p0}, Lcom/android/settingslib/Utils;->getColorAttr(ILandroid/content/Context;)Landroid/content/res/ColorStateList;

    .line 101
    .line 102
    .line 103
    move-result-object p0

    .line 104
    iget-object v2, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressSolid:Landroid/graphics/drawable/Drawable;

    .line 105
    .line 106
    invoke-virtual {v2, v1}, Landroid/graphics/drawable/Drawable;->setTintList(Landroid/content/res/ColorStateList;)V

    .line 107
    .line 108
    .line 109
    iget-object v2, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->sliderProgressIcon:Lcom/android/systemui/util/AlphaTintDrawableWrapper;

    .line 110
    .line 111
    if-eqz v2, :cond_5

    .line 112
    .line 113
    invoke-virtual {v2, v0}, Lcom/android/systemui/util/AlphaTintDrawableWrapper;->setTintList(Landroid/content/res/ColorStateList;)V

    .line 114
    .line 115
    .line 116
    :cond_5
    iget-object v0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 117
    .line 118
    if-eqz v0, :cond_6

    .line 119
    .line 120
    invoke-virtual {v0, p0}, Landroid/widget/ImageButton;->setImageTintList(Landroid/content/res/ColorStateList;)V

    .line 121
    .line 122
    .line 123
    iget-object p0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->icon:Landroid/widget/ImageButton;

    .line 124
    .line 125
    invoke-virtual {p0, p2}, Landroid/widget/ImageButton;->setImageAlpha(I)V

    .line 126
    .line 127
    .line 128
    :cond_6
    iget-object p0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->number:Landroid/widget/TextView;

    .line 129
    .line 130
    if-eqz p0, :cond_7

    .line 131
    .line 132
    invoke-virtual {p0, v1}, Landroid/widget/TextView;->setTextColor(Landroid/content/res/ColorStateList;)V

    .line 133
    .line 134
    .line 135
    iget-object p0, p1, Lcom/android/systemui/volume/VolumeDialogImpl$VolumeRow;->number:Landroid/widget/TextView;

    .line 136
    .line 137
    int-to-float p1, p2

    .line 138
    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setAlpha(F)V

    .line 139
    .line 140
    .line 141
    :cond_7
    return-void
.end method

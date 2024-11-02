package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.RippleDrawable;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.PasswordTextView;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetUtil;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class KeyguardSecPinBasedInputViewController extends KeyguardPinBasedInputViewController implements TextView.OnEditorActionListener, TextWatcher {
    public final AnonymousClass1 mAccessibilityDelegate;
    public final View[] mButtons;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public View mDeleteButton;
    public RippleDrawable mDeleteButtonRipple;
    public final KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda3 mInitializeBottomContainerViewRunnable;
    public boolean mInitialized;
    public boolean mIsImagePinLock;
    public boolean mIsNightModeOn;
    public View mOkButton;
    public RippleDrawable mOkButtonRipple;
    public final KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda2 mOnKeyListener;
    public int mOriginPinEntryId;
    public boolean mUpdateSkipped;
    public Rect mWindowRect;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$2] */
    public KeyguardSecPinBasedInputViewController(KeyguardSecPinBasedInputView keyguardSecPinBasedInputView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardSecPinBasedInputView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager);
        this.mWindowRect = new Rect(0, 0, 0, 0);
        this.mButtons = r1;
        this.mAccessibilityDelegate = new View.AccessibilityDelegate(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setTextEntryKey(true);
            }
        };
        this.mInitialized = false;
        this.mUpdateSkipped = false;
        this.mIsNightModeOn = false;
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                View view2;
                KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = KeyguardSecPinBasedInputViewController.this;
                keyguardSecPinBasedInputViewController.getClass();
                String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
                if (i == 66 && (view2 = keyguardSecPinBasedInputViewController.mOkButton) != null && view2.getAlpha() < 1.0f) {
                    return true;
                }
                if (keyEvent.getAction() == 0) {
                    return ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).onKeyDown(i, keyEvent);
                }
                if (keyEvent.getAction() == 1) {
                    return ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).onKeyUp(i, keyEvent);
                }
                return false;
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z;
                if ((configuration.uiMode & 32) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = KeyguardSecPinBasedInputViewController.this;
                if (keyguardSecPinBasedInputViewController.mIsNightModeOn != z) {
                    KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("night mode changed : "), keyguardSecPinBasedInputViewController.mIsNightModeOn, " -> ", z, "KeyguardSecPinBasedInputViewController");
                    keyguardSecPinBasedInputViewController.mIsNightModeOn = z;
                    if (LsRune.SECURITY_OPEN_THEME) {
                        keyguardSecPinBasedInputViewController.updateStyle(WallpaperEventNotifier.getInstance().mCurStatusFlag, WallpaperEventNotifier.getInstance().getSemWallpaperColors(false));
                    } else {
                        Log.d("KeyguardSecPinBasedInputViewController", "Can't apply night mode! NOT supported OPEN THEME feature");
                    }
                }
                Rect rect = new Rect();
                rect.set(configuration.windowConfiguration.getBounds());
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && keyguardSecPinBasedInputViewController.mWindowRect != rect) {
                    Log.d("KeyguardSecPinBasedInputViewController", "onConfigurationChanged()");
                    keyguardSecPinBasedInputViewController.mWindowRect = rect;
                    if (keyguardSecPinBasedInputViewController.skipUpdateWhenCloseFolder()) {
                        keyguardSecPinBasedInputViewController.mUpdateSkipped = true;
                    } else {
                        keyguardSecPinBasedInputViewController.initializeBottomContainerView();
                    }
                }
            }
        };
        this.mInitializeBottomContainerViewRunnable = new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda3(this, 0);
        this.mConfigurationController = configurationController;
        View[] viewArr = {((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key0), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key1), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key2), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key3), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key4), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key5), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key6), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key7), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key8), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key9)};
    }

    public static boolean isFolderClosed() {
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            return true;
        }
        return false;
    }

    public static void updateNumPadKeySideMargin(View view, int i) {
        if (view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            onUserInput();
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        getKeyguardSecurityCallback().userActivity();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    public final byte[] getPasswordText() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView instanceof SecPasswordTextView) {
            return KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(((SecPasswordTextView) passwordTextView).mText);
        }
        return KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(passwordTextView.getText());
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void initializeBottomContainerView() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            Log.d("KeyguardSecPinBasedInputViewController", "First initializeBottomContainerView");
            initializeBottomContainerView$1();
        } else {
            Handler handler = this.mHandler;
            if (handler.hasCallbacks(this.mInitializeBottomContainerViewRunnable)) {
                handler.removeCallbacks(this.mInitializeBottomContainerViewRunnable);
            }
            handler.post(this.mInitializeBottomContainerViewRunnable);
        }
    }

    public final void initializeBottomContainerView$1() {
        boolean z;
        int min;
        int i;
        int width;
        int i2;
        int max;
        int i3;
        ImageView.ScaleType scaleType;
        Resources resources = getResources();
        final int i4 = 1;
        final int i5 = 0;
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isOpenThemeLook() && resources.getBoolean(R.bool.theme_use_image_pinlock)) {
            z = true;
        } else {
            z = false;
        }
        this.mIsImagePinLock = z;
        View view = this.mOkButton;
        if (view != null) {
            view.setVisibility(8);
        }
        if (this.mIsImagePinLock) {
            this.mOkButton = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key_enter);
        } else {
            this.mOkButton = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key_enter_text);
        }
        View view2 = this.mOkButton;
        KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 keyguardPinBasedInputViewController$$ExternalSyntheticLambda1 = this.mActionButtonTouchListener;
        if (view2 != null) {
            view2.setVisibility(0);
            this.mOkButton.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
            this.mOkButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda4
                public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    switch (i5) {
                        case 0:
                            KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled() && keyguardSecPinBasedInputViewController.mOkButton.getAlpha() > 0.4f) {
                                keyguardSecPinBasedInputViewController.verifyPasswordAndUnlock();
                                return;
                            }
                            return;
                        default:
                            KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController2 = this.f$0;
                            PasswordTextView passwordTextView = keyguardSecPinBasedInputViewController2.mPasswordEntry;
                            if (passwordTextView.isEnabled()) {
                                passwordTextView.deleteLastChar();
                                if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
                                    keyguardSecPinBasedInputViewController2.setOkButtonEnabled(false);
                                    return;
                                }
                                return;
                            }
                            return;
                    }
                }
            });
            this.mOkButton.setAccessibilityDelegate(this.mAccessibilityDelegate);
            this.mOkButton.setOnHoverListener(null);
            if (this.mIsImagePinLock) {
                ((ImageButton) this.mOkButton).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
        View findViewById = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.delete_button);
        this.mDeleteButton = findViewById;
        if (findViewById != null) {
            findViewById.setVisibility(0);
            this.mDeleteButton.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
            this.mDeleteButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda4
                public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    switch (i4) {
                        case 0:
                            KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled() && keyguardSecPinBasedInputViewController.mOkButton.getAlpha() > 0.4f) {
                                keyguardSecPinBasedInputViewController.verifyPasswordAndUnlock();
                                return;
                            }
                            return;
                        default:
                            KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController2 = this.f$0;
                            PasswordTextView passwordTextView = keyguardSecPinBasedInputViewController2.mPasswordEntry;
                            if (passwordTextView.isEnabled()) {
                                passwordTextView.deleteLastChar();
                                if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
                                    keyguardSecPinBasedInputViewController2.setOkButtonEnabled(false);
                                    return;
                                }
                                return;
                            }
                            return;
                    }
                }
            });
            this.mDeleteButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda5
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view3) {
                    KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = KeyguardSecPinBasedInputViewController.this;
                    if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                        ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).resetPasswordText(true, true);
                        keyguardSecPinBasedInputViewController.setOkButtonEnabled(false);
                    }
                    ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).doHapticKeyClick();
                    return true;
                }
            });
            this.mDeleteButton.setAccessibilityDelegate(this.mAccessibilityDelegate);
            String string = resources.getString(R.string.kg_keycode_delete);
            View view3 = this.mDeleteButton;
            if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", ");
                m.append(String.format(resources.getString(R.string.kg_keycode_delete_help_text_to), resources.getString(R.string.kg_keycode_delete_help_text_double_tap), resources.getString(R.string.kg_keycode_delete)));
                m.append(", ");
                m.append(String.format(resources.getString(R.string.kg_keycode_delete_help_text_and_hold_to), resources.getString(R.string.kg_keycode_delete_help_text_double_tap), resources.getString(R.string.kg_keycode_delete_all)));
                string = m.toString();
            }
            view3.setContentDescription(string);
            this.mDeleteButton.setOnHoverListener(null);
            ImageButton imageButton = (ImageButton) this.mDeleteButton;
            if (this.mIsImagePinLock) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else {
                scaleType = ImageView.ScaleType.CENTER;
            }
            imageButton.setScaleType(scaleType);
        }
        if (SystemUIWidgetUtil.needsBlackComponent(getContext(), SystemUIWidgetUtil.convertFlag("background"), true)) {
            this.mOkButtonRipple = (RippleDrawable) getContext().getDrawable(R.drawable.ripple_drawable_pin_whitebg);
            this.mDeleteButtonRipple = (RippleDrawable) getContext().getDrawable(R.drawable.ripple_drawable_pin_whitebg);
        } else {
            this.mOkButtonRipple = (RippleDrawable) getContext().getDrawable(R.drawable.origin_ripple_drawable);
            this.mDeleteButtonRipple = (RippleDrawable) getContext().getDrawable(R.drawable.origin_ripple_drawable);
        }
        View view4 = this.mOkButton;
        if (view4 != null) {
            view4.setBackground(this.mOkButtonRipple);
        }
        View view5 = this.mDeleteButton;
        if (view5 != null) {
            view5.setBackground(this.mDeleteButtonRipple);
        }
        for (int i6 = 0; i6 < 10; i6++) {
            View view6 = this.mButtons[i6];
            if (!(view6 instanceof SecNumPadKey)) {
                break;
            }
            ((SecNumPadKey) view6).updateViewStyle();
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        boolean isDualDisplayPolicyAllowed = keyguardUpdateMonitor.isDualDisplayPolicyAllowed();
        Resources resources2 = getResources();
        if (isDualDisplayPolicyAllowed) {
            min = this.mWindowRect.width();
        } else {
            min = Math.min(this.mWindowRect.width(), this.mWindowRect.height());
        }
        int height = this.mWindowRect.height();
        if (DeviceType.isTablet()) {
            i = R.dimen.tablet_num_pad_key_size_ratio;
        } else if (isDualDisplayPolicyAllowed) {
            i = R.dimen.fold_num_pad_key_size_ratio;
        } else if (isFolderClosed()) {
            i = R.dimen.fold_sub_num_pad_key_size_ratio;
        } else {
            i = R.dimen.num_pad_key_size_ratio;
        }
        float f = resources2.getFloat(i);
        int i7 = (int) ((isDualDisplayPolicyAllowed ? height : min) * f);
        Resources resources3 = getResources();
        if (!DeviceType.isTablet() && !isDualDisplayPolicyAllowed) {
            width = Math.min(this.mWindowRect.width(), this.mWindowRect.height());
        } else {
            width = this.mWindowRect.width();
        }
        if (DeviceType.isTablet()) {
            i2 = R.dimen.tablet_num_pad_key_side_margin_ratio;
        } else if (isDualDisplayPolicyAllowed) {
            i2 = R.dimen.fold_num_pad_key_side_margin_ratio;
        } else if (isFolderClosed()) {
            i2 = R.dimen.fold_sub_num_pad_key_side_margin_ratio;
        } else {
            i2 = R.dimen.num_pad_key_side_margin_ratio;
        }
        int i8 = (int) (resources3.getFloat(i2) * width);
        for (int i9 = 0; i9 < 10; i9++) {
            View view7 = this.mButtons[i9];
            if (view7 != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view7.getLayoutParams();
                layoutParams.width = i7;
                layoutParams.height = i7;
                view7.setLayoutParams(layoutParams);
            }
            View view8 = this.mButtons[i9];
            if (view8 instanceof SecNumPadKeyTablet) {
                ((SecNumPadKeyTablet) view8).updateDigitTextSize();
                ((SecNumPadKeyTablet) this.mButtons[i9]).updateKlondikeTextSize();
            } else {
                ((SecNumPadKey) view8).updateDigitTextSize();
                ((SecNumPadKey) this.mButtons[i9]).updateKlondikeTextSize();
            }
        }
        View view9 = this.mOkButton;
        if (view9 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view9.getLayoutParams();
            layoutParams2.width = i7;
            layoutParams2.height = i7;
            view9.setLayoutParams(layoutParams2);
        }
        View view10 = this.mDeleteButton;
        if (view10 != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view10.getLayoutParams();
            layoutParams3.width = i7;
            layoutParams3.height = i7;
            view10.setLayoutParams(layoutParams3);
        }
        updateNumPadKeySideMargin(this.mButtons[2], i8);
        updateNumPadKeySideMargin(this.mButtons[5], i8);
        updateNumPadKeySideMargin(this.mButtons[8], i8);
        updateNumPadKeySideMargin(this.mButtons[0], i8);
        if (!this.mIsImagePinLock) {
            View view11 = this.mOkButton;
            if (view11 instanceof SystemUITextView) {
                ((SystemUITextView) view11).setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_ok_button_font_size));
            }
        }
        ViewGroup viewGroup = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row1);
        ViewGroup viewGroup2 = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row2);
        ViewGroup viewGroup3 = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row3);
        Resources resources4 = getResources();
        if (isDualDisplayPolicyAllowed) {
            max = this.mWindowRect.height();
        } else {
            max = Math.max(this.mWindowRect.width(), this.mWindowRect.height());
        }
        if (DeviceType.isTablet()) {
            i3 = R.dimen.tablet_num_pad_key_bottom_margin_ratio;
        } else if (isDualDisplayPolicyAllowed) {
            i3 = R.dimen.fold_num_pad_key_bottom_margin_ratio;
        } else if (isFolderClosed()) {
            i3 = R.dimen.fold_sub_num_pad_key_bottom_margin_ratio;
        } else {
            i3 = R.dimen.num_pad_key_bottom_margin_ratio;
        }
        int i10 = (int) (resources4.getFloat(i3) * max);
        if (viewGroup != null) {
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams4.bottomMargin = i10;
            viewGroup.setLayoutParams(layoutParams4);
            if (viewGroup2 != null) {
                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) viewGroup2.getLayoutParams();
                layoutParams5.bottomMargin = i10;
                viewGroup2.setLayoutParams(layoutParams5);
            }
            if (viewGroup3 != null) {
                LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) viewGroup3.getLayoutParams();
                layoutParams6.bottomMargin = i10;
                viewGroup3.setLayoutParams(layoutParams6);
            }
        }
        boolean isForgotPasswordView = keyguardUpdateMonitor.isForgotPasswordView();
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (isForgotPasswordView) {
            this.mOriginPinEntryId = passwordTextView.getId();
            int generateViewId = View.generateViewId();
            passwordTextView.setId(generateViewId);
            while (i5 < 10) {
                View view12 = this.mButtons[i5];
                if (view12 instanceof SecNumPadKey) {
                    ((SecNumPadKey) view12).mTextViewResId = generateViewId;
                    i5++;
                } else {
                    return;
                }
            }
            return;
        }
        int id = passwordTextView.getId();
        if (this.mOriginPinEntryId != id) {
            this.mOriginPinEntryId = id;
            passwordTextView.setId(id);
            int i11 = this.mOriginPinEntryId;
            while (i5 < 10) {
                View view13 = this.mButtons[i5];
                if (view13 instanceof SecNumPadKey) {
                    ((SecNumPadKey) view13).mTextViewResId = i11;
                    i5++;
                } else {
                    return;
                }
            }
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 0 && i != 6 && i != 5) {
            return false;
        }
        verifyPasswordAndUnlock();
        return true;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK && this.mUpdateSkipped) {
            this.mUpdateSkipped = false;
            if (isFolderClosed()) {
                initializeBottomContainerView$1();
            }
        }
        this.mHandler.postDelayed(new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda3(this, 1), 100L);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mWindowRect = getResources().getConfiguration().windowConfiguration.getBounds();
        boolean z = true;
        if (Settings.System.getInt(getContext().getContentResolver(), "show_password", 1) != 1) {
            z = false;
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        passwordTextView.mShowPassword = z;
        passwordTextView.setOnTouchListener(new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda0());
        passwordTextView.setOnKeyListener(this.mOnKeyListener);
        passwordTextView.mUserActivityListener = new PasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda1
            @Override // com.android.keyguard.PasswordTextView.UserActivityListener
            public final void onUserActivity() {
                KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = KeyguardSecPinBasedInputViewController.this;
                keyguardSecPinBasedInputViewController.onUserInput();
                keyguardSecPinBasedInputViewController.setOkButtonEnabled(true);
            }
        };
        passwordTextView.setLongClickable(false);
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            passwordTextView.setSelected(false);
        } else {
            passwordTextView.requestFocus();
        }
        initializeBottomContainerView();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        super.resetState();
        ((KeyguardSecPinBasedInputView) this.mView).setPasswordEntryEnabled(true);
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
            setOkButtonEnabled(false);
        }
    }

    public final void setEnabledKeypad(boolean z) {
        float f;
        float f2;
        View view = this.mDeleteButton;
        if (view != null) {
            view.setFocusable(z);
            this.mDeleteButton.setClickable(z);
            View view2 = this.mDeleteButton;
            if (z) {
                f2 = 1.0f;
            } else {
                f2 = 0.4f;
            }
            view2.setAlpha(f2);
        }
        for (int i = 0; i < 10; i++) {
            this.mButtons[i].setFocusable(z);
            this.mButtons[i].setClickable(z);
            View view3 = this.mButtons[i];
            if (z) {
                f = 1.0f;
            } else {
                f = 0.4f;
            }
            view3.setAlpha(f);
        }
    }

    public final void setOkButtonContentDescription(boolean z, boolean z2) {
        if (this.mOkButton != null) {
            String string = getResources().getString(R.string.kg_keycode_ok);
            if (z2) {
                this.mOkButton.setContentDescription(string);
                return;
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", ");
            m.append(getResources().getString(R.string.accessibility_button));
            String sb = m.toString();
            View view = this.mOkButton;
            if (!z) {
                StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, ", ");
                m2.append(getResources().getString(R.string.kg_keycode_ok_disabled));
                sb = m2.toString();
            }
            view.setContentDescription(sb);
        }
    }

    public void setOkButtonEnabled(boolean z) {
        float f;
        View view = this.mOkButton;
        if (view != null) {
            view.setFocusable(z);
            this.mOkButton.setClickable(z);
            View view2 = this.mOkButton;
            if (z) {
                f = 1.0f;
            } else {
                f = 0.4f;
            }
            view2.setAlpha(f);
            setOkButtonContentDescription(z, false);
        }
    }

    public void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (skipUpdateWhenCloseFolder()) {
            return;
        }
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(this.mInitializeBottomContainerViewRunnable)) {
            handler.removeCallbacks(this.mInitializeBottomContainerViewRunnable);
        }
        handler.post(this.mInitializeBottomContainerViewRunnable);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void verifyPasswordAndUnlock() {
        super.verifyPasswordAndUnlock();
        setOkButtonEnabled(false);
        setOkButtonContentDescription(false, true);
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}

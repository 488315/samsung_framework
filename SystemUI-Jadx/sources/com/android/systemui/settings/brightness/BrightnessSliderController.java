package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BrightnessSliderController extends ViewController implements ToggleSlider {
    public static final Uri VOICE_ASSISTANT_ENABLED_URI = Settings.Secure.getUriFor("enabled_accessibility_services");
    public static boolean mUsingHighBrightnessDialogEnabled;
    public String BRIGHTNESS_DIALOG_TAG;
    public final BrightnessAnimationIcon mBrightnessIcon;
    public final BrightnessSliderView mBrightnessSliderView;
    public final FalsingManager mFalsingManager;
    public ToggleSlider.Listener mListener;
    public ToggleSlider mMirror;
    public BrightnessMirrorController mMirrorController;
    public final AnonymousClass1 mOnInterceptListener;
    public final PowerManager mPowerManager;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final AnonymousClass2 mSeekListener;
    public final BrightnessSliderController$$ExternalSyntheticLambda0 mSettingsListener;
    public boolean mSliderEnabled;
    public boolean mSliderNeedToDisabled;
    public boolean mTracking;
    public SystemUIDialog mUsingHighBrightnessDialog;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Factory {
        public final FalsingManager mFalsingManager;

        public Factory(FalsingManager falsingManager) {
            this.mFalsingManager = falsingManager;
        }

        public final BrightnessSliderController create(Context context, ViewGroup viewGroup) {
            return new BrightnessSliderController((BrightnessSliderView) LayoutInflater.from(context).inflate(R.layout.sec_quick_settings_brightness_dialog, viewGroup, false), this.mFalsingManager);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.settings.brightness.BrightnessSliderController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.settings.brightness.BrightnessSliderController$2] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.settings.brightness.BrightnessSliderController$$ExternalSyntheticLambda0] */
    public BrightnessSliderController(BrightnessSliderView brightnessSliderView, FalsingManager falsingManager) {
        super(brightnessSliderView);
        this.mSliderEnabled = true;
        this.mSliderNeedToDisabled = false;
        this.BRIGHTNESS_DIALOG_TAG = null;
        this.mOnInterceptListener = new Gefingerpoken() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.1
            @Override // com.android.systemui.Gefingerpoken
            public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 1 || actionMasked == 3) {
                    BrightnessSliderController.this.mFalsingManager.isFalseTouch(10);
                    return false;
                }
                return false;
            }

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                return false;
            }
        };
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                boolean z2;
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                ToggleSlider.Listener listener = brightnessSliderController.mListener;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(i, brightnessSliderController.mTracking, false);
                }
                BrightnessSliderController.this.setSeekBarContentDescription(i);
                if (Settings.System.getIntForUser(BrightnessSliderController.this.getContext().getContentResolver(), "screen_brightness_mode", 0, -2) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2 && BrightnessSliderController.mUsingHighBrightnessDialogEnabled) {
                    BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                    BrightnessSliderView brightnessSliderView2 = (BrightnessSliderView) brightnessSliderController2.mView;
                    if (brightnessSliderView2.mIsTouchSlider && z) {
                        if (brightnessSliderView2.mDualSeekBarThreshold <= i) {
                            if (brightnessSliderController2.mMirrorController != null) {
                                Log.d("ToggleSlider", "hideMirror : USING_HIGH_BRIGHTNESS_DIALOG");
                                BrightnessSliderController.this.mMirrorController.hideMirror();
                            }
                            final BrightnessSliderController brightnessSliderController3 = BrightnessSliderController.this;
                            if (brightnessSliderController3.mMirror != null) {
                                if (brightnessSliderController3.getContext().getResources().getInteger(android.R.integer.leanback_setup_alpha_forward_out_content_duration) > brightnessSliderController3.mPowerManager.getMaximumScreenBrightnessSetting()) {
                                    if (brightnessSliderController3.mUsingHighBrightnessDialog == null) {
                                        brightnessSliderController3.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessSliderController3.getContext(), 2132018528);
                                        String string = brightnessSliderController3.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_message_support_hbm);
                                        brightnessSliderController3.mUsingHighBrightnessDialog.setTitle(brightnessSliderController3.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_title));
                                        brightnessSliderController3.mUsingHighBrightnessDialog.setMessage(string);
                                        brightnessSliderController3.mUsingHighBrightnessDialog.setPositiveButton(R.string.sec_brightness_using_high_brightness_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController$$ExternalSyntheticLambda2
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                                Settings.System.semPutIntForUser(BrightnessSliderController.this.getContext().getContentResolver(), "screen_brightness_mode", 1, -2);
                                            }
                                        });
                                        brightnessSliderController3.mUsingHighBrightnessDialog.setNegativeButton(R.string.sec_brightness_using_high_brightness_dialog_negative_button, new BrightnessSliderController$$ExternalSyntheticLambda3());
                                        brightnessSliderController3.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.3
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(DialogInterface dialogInterface) {
                                                BrightnessSliderController brightnessSliderController4 = BrightnessSliderController.this;
                                                brightnessSliderController4.mUsingHighBrightnessDialog = null;
                                                boolean z3 = false;
                                                BrightnessSliderController.mUsingHighBrightnessDialogEnabled = false;
                                                if (Settings.System.getIntForUser(brightnessSliderController4.getContext().getContentResolver(), "screen_brightness_mode", 0, -2) != 0) {
                                                    z3 = true;
                                                }
                                                if (!z3) {
                                                    BrightnessSliderController brightnessSliderController5 = BrightnessSliderController.this;
                                                    brightnessSliderController5.setValue(((BrightnessSliderView) brightnessSliderController5.mView).mDualSeekBarThreshold + 1);
                                                }
                                                Settings.System.semPutIntForUser(BrightnessSliderController.this.getContext().getContentResolver(), "shown_max_brightness_dialog", 1, -2);
                                            }
                                        });
                                        brightnessSliderController3.mUsingHighBrightnessDialog.show();
                                        return;
                                    }
                                    return;
                                }
                                if (brightnessSliderController3.mUsingHighBrightnessDialog == null) {
                                    brightnessSliderController3.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessSliderController3.getContext(), 2132018528);
                                    brightnessSliderController3.mUsingHighBrightnessDialog.setMessage(brightnessSliderController3.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_message));
                                    brightnessSliderController3.mUsingHighBrightnessDialog.setPositiveButton(R.string.sec_brightness_using_high_brightness_dialog_ok_button, null);
                                    brightnessSliderController3.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.4
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(DialogInterface dialogInterface) {
                                            BrightnessSliderController brightnessSliderController4 = BrightnessSliderController.this;
                                            brightnessSliderController4.mUsingHighBrightnessDialog = null;
                                            BrightnessSliderController.mUsingHighBrightnessDialogEnabled = false;
                                            brightnessSliderController4.setValue(((BrightnessSliderView) brightnessSliderController4.mView).mDualSeekBarThreshold + 1);
                                            Settings.System.semPutIntForUser(BrightnessSliderController.this.getContext().getContentResolver(), "shown_max_brightness_dialog", 1, -2);
                                        }
                                    });
                                    brightnessSliderController3.mUsingHighBrightnessDialog.show();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        SystemUIDialog systemUIDialog = brightnessSliderController2.mUsingHighBrightnessDialog;
                        if (systemUIDialog != null && systemUIDialog.isShowing()) {
                            BrightnessSliderController.this.mUsingHighBrightnessDialog.dismiss();
                            BrightnessMirrorController brightnessMirrorController = BrightnessSliderController.this.mMirrorController;
                            if (brightnessMirrorController != null) {
                                brightnessMirrorController.showMirror();
                                BrightnessSliderController brightnessSliderController4 = BrightnessSliderController.this;
                                brightnessSliderController4.mMirrorController.setLocationAndSize(brightnessSliderController4.mView);
                            }
                        }
                    }
                }
                BrightnessSliderView brightnessSliderView3 = (BrightnessSliderView) BrightnessSliderController.this.mView;
                if (brightnessSliderView3.mDualSeekBarThreshold <= i) {
                    brightnessSliderView3.setDualSeekBarResources(true);
                } else {
                    brightnessSliderView3.setDualSeekBarResources(false);
                }
                BrightnessMirrorController brightnessMirrorController2 = BrightnessSliderController.this.mMirrorController;
                if (brightnessMirrorController2 != null) {
                    BrightnessSliderController brightnessSliderController5 = brightnessMirrorController2.mToggleSliderController;
                    if (brightnessSliderController5 != null) {
                        brightnessSliderController5.setValue(i);
                    }
                    BrightnessSliderController brightnessSliderController6 = BrightnessSliderController.this;
                    BrightnessMirrorController brightnessMirrorController3 = brightnessSliderController6.mMirrorController;
                    int max = ((BrightnessSliderView) brightnessSliderController6.mView).mSlider.getMax();
                    LottieAnimationView lottieAnimationView = brightnessMirrorController3.mBrightnessIcon;
                    if (lottieAnimationView != null) {
                        float f = i / max;
                        if (brightnessMirrorController3.mIconAnimationValue != f) {
                            brightnessMirrorController3.mIconAnimationValue = f;
                            lottieAnimationView.setProgressInternal(f, true);
                        }
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                brightnessSliderController.mTracking = true;
                BrightnessMirrorController brightnessMirrorController = brightnessSliderController.mMirrorController;
                if (brightnessMirrorController != null) {
                    brightnessMirrorController.showMirror();
                    BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                    brightnessSliderController2.mMirrorController.setLocationAndSize(brightnessSliderController2.mView);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                brightnessSliderController.mTracking = false;
                ToggleSlider.Listener listener = brightnessSliderController.mListener;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(brightnessSliderController.getValue(), false, true);
                }
                ((BrightnessSliderView) BrightnessSliderController.this.mView).setDualSeekBarResources(false);
                if (BrightnessSliderController.this.mSliderNeedToDisabled) {
                    Log.d("ToggleSlider", "slider disabled by onStopTrackingTouch()");
                    BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                    brightnessSliderController2.mSliderNeedToDisabled = false;
                    brightnessSliderController2.updateSystemBrightnessEnabled(false);
                }
                if (BrightnessSliderController.this.mMirror != null) {
                    SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1009", GlsIntent.Extras.EXTRA_LOCATION, "quick panel", "QUICK_PANEL_LAYOUT");
                }
                BrightnessMirrorController brightnessMirrorController = BrightnessSliderController.this.mMirrorController;
                if (brightnessMirrorController != null) {
                    brightnessMirrorController.hideMirror();
                }
            }
        };
        this.mFalsingManager = falsingManager;
        this.mBrightnessSliderView = brightnessSliderView;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                brightnessSliderController.getClass();
                if (uri.equals(Settings.Secure.getUriFor("enabled_accessibility_services"))) {
                    brightnessSliderController.setSeekBarContentDescription(brightnessSliderController.mBrightnessSliderView.mSlider.getProgress());
                }
            }
        };
        this.mBrightnessIcon = new BrightnessAnimationIcon((LottieAnimationView) ((BrightnessSliderView) this.mView).findViewById(R.id.brightness_icon));
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mPowerManager = (PowerManager) getContext().getSystemService(PowerManager.class);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final String getTag() {
        return this.BRIGHTNESS_DIALOG_TAG;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getValue() {
        return ((BrightnessSliderView) this.mView).mSlider.getProgress();
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void initBrightnessIconResources() {
        this.mBrightnessIcon.initBrightnessIconResources(getContext());
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final boolean mirrorTouchEvent(MotionEvent motionEvent) {
        if (this.mMirror != null) {
            MotionEvent copy = motionEvent.copy();
            boolean mirrorTouchEvent = this.mMirror.mirrorTouchEvent(copy);
            copy.recycle();
            return mirrorTouchEvent;
        }
        return ((BrightnessSliderView) this.mView).dispatchTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        boolean z;
        boolean z2;
        ((BrightnessSliderView) this.mView).mSlider.setOnSeekBarChangeListener(this.mSeekListener);
        ((BrightnessSliderView) this.mView).mOnInterceptListener = this.mOnInterceptListener;
        boolean z3 = false;
        if (Settings.System.getIntForUser(getContext().getContentResolver(), "shown_max_brightness_dialog", 0, -2) == 0) {
            z = true;
        } else {
            z = false;
        }
        mUsingHighBrightnessDialogEnabled = z;
        if (QpRune.QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL) {
            if (Settings.System.getIntForUser(getContext().getContentResolver(), "high_brightness_mode_pms_enter", 0, -2) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            updateHighBrightnessModeEnter(z2);
        }
        if (Settings.System.getIntForUser(getContext().getContentResolver(), "display_outdoor_mode", 0, -2) != 0) {
            z3 = true;
        }
        updateOutdoorMode(z3);
        updateSliderHeight();
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.mSettingsListener, VOICE_ASSISTANT_ENABLED_URI);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((BrightnessSliderView) this.mView).mSlider.setOnSeekBarChangeListener(null);
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        brightnessSliderView.mListener = null;
        brightnessSliderView.mOnInterceptListener = null;
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z;
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        ToggleSeekBar toggleSeekBar = brightnessSliderView.mSlider;
        if (enforcedAdmin == null) {
            z = true;
        } else {
            z = false;
        }
        toggleSeekBar.setEnabled(z);
        brightnessSliderView.mSlider.mEnforcedAdmin = enforcedAdmin;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setMax(int i) {
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        brightnessSliderView.mSlider.setMax(i);
        brightnessSliderView.updateSliderResources();
        ToggleSlider toggleSlider = this.mMirror;
        if (toggleSlider != null) {
            toggleSlider.setMax(i);
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setOnChangedListener(BrightnessController brightnessController) {
        this.mListener = brightnessController;
    }

    public final void setSeekBarContentDescription(int i) {
        boolean isVoiceAssistantEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isVoiceAssistantEnabled();
        BrightnessSliderView brightnessSliderView = this.mBrightnessSliderView;
        if (!isVoiceAssistantEnabled) {
            brightnessSliderView.setContentDescription(Integer.toString(i));
        } else {
            brightnessSliderView.setContentDescription(null);
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setValue(int i) {
        ((BrightnessSliderView) this.mView).mSlider.setProgress(i);
        setSeekBarContentDescription(i);
        ToggleSlider toggleSlider = this.mMirror;
        if (toggleSlider != null) {
            toggleSlider.setValue(i);
        }
        this.mBrightnessIcon.playBrightnessIconAnimation(i, ((BrightnessSliderView) this.mView).mSlider.getMax());
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateDualSeekBar() {
        ((BrightnessSliderView) this.mView).setDualSeekBarResources(false);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateHighBrightnessModeEnter(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", z, ", slider is ");
        m.append(((BrightnessSliderView) this.mView).mSlider);
        Log.d("ToggleSlider", m.toString());
        View view = this.mView;
        if (((BrightnessSliderView) view).mSlider != null) {
            ((BrightnessSliderView) view).mSlider.mHighBrightnessModeEnter = z;
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateOutdoorMode(boolean z) {
        boolean z2;
        AnonymousClass2 anonymousClass2;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateOutdoorMode() = ", z, "ToggleSlider");
        boolean z3 = true;
        boolean z4 = !z;
        ToggleSeekBar toggleSeekBar = ((BrightnessSliderView) this.mView).mSlider;
        if (toggleSeekBar != null) {
            z2 = toggleSeekBar.mSystemBrightnessEnabled;
        } else {
            z2 = true;
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateSliderEnabled() = ", z4, ", mSystemBrightnessEnabled = ", z2, "ToggleSlider");
        if (!z4 || !z2) {
            z3 = false;
        }
        if (this.mSliderEnabled != z3) {
            this.mSliderEnabled = z3;
            BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
            if (z3) {
                anonymousClass2 = this.mSeekListener;
            } else {
                anonymousClass2 = null;
            }
            brightnessSliderView.mSlider.setOnSeekBarChangeListener(anonymousClass2);
        }
        ((BrightnessSliderView) this.mView).mSlider.setEnabled(z4);
    }

    public final void updateSliderHeight() {
        ToggleSeekBar toggleSeekBar = ((BrightnessSliderView) this.mView).mSlider;
        Context context = getContext();
        this.mResourcePicker.getClass();
        toggleSeekBar.setMaxHeight(SecQSPanelResourcePicker.getBrightnessBarHeight(context));
        ((BrightnessSliderView) this.mView).mSlider.setPaddingRelative(0, 0, 0, 0);
        ((BrightnessSliderView) this.mView).getClass();
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateSystemBrightnessEnabled(boolean z) {
        AnonymousClass2 anonymousClass2;
        if (this.mTracking) {
            Log.d("ToggleSlider", "Can't using updateSystemBrightnessEnabled() now.");
            this.mSliderNeedToDisabled = !z;
            if (!z) {
                return;
            }
        }
        View view = this.mView;
        ToggleSeekBar toggleSeekBar = ((BrightnessSliderView) view).mSlider;
        if (toggleSeekBar != null) {
            toggleSeekBar.mSystemBrightnessEnabled = z;
            if (this.mSliderEnabled != z) {
                this.mSliderEnabled = z;
                BrightnessSliderView brightnessSliderView = (BrightnessSliderView) view;
                if (z) {
                    anonymousClass2 = this.mSeekListener;
                } else {
                    anonymousClass2 = null;
                }
                brightnessSliderView.mSlider.setOnSeekBarChangeListener(anonymousClass2);
                return;
            }
            return;
        }
        Log.d("ToggleSlider", "Can't using updateSystemBrightnessEnabled() : slider is null.");
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateUsingHighBrightnessDialog(boolean z) {
        mUsingHighBrightnessDialogEnabled = z;
    }
}

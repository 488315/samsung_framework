package com.android.keyguard;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.sec.ims.configuration.DATA;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EmergencyButtonController extends ViewController {
    public final ActivityTaskManager mActivityTaskManager;
    public final Executor mBackgroundExecutor;
    public boolean mBouncerShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public int mCurrentSimState;
    public EmergencyButtonCallback mEmergencyButtonCallback;
    public final InputMethodManager mImm;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public boolean mKeyguardShowing;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Executor mMainExecutor;
    public final MetricsLogger mMetricsLogger;
    public View mPasswordEntry;
    public final PowerManager mPowerManager;
    public final EmergencyButtonController$$ExternalSyntheticLambda4 mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final ShadeController mShadeController;
    public final TelecomManager mTelecomManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Factory {
        public final ActivityTaskManager mActivityTaskManager;
        public final Executor mBackgroundExecutor;
        public final ConfigurationController mConfigurationController;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LockPatternUtils mLockPatternUtils;
        public final Executor mMainExecutor;
        public final MetricsLogger mMetricsLogger;
        public final PowerManager mPowerManager;
        public final ShadeController mShadeController;
        public final TelecomManager mTelecomManager;
        public final TelephonyManager mTelephonyManager;

        public Factory(ConfigurationController configurationController, InputMethodManager inputMethodManager, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2) {
            this.mConfigurationController = configurationController;
            this.mInputMethodManager = inputMethodManager;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mTelephonyManager = telephonyManager;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
            this.mLockPatternUtils = lockPatternUtils;
            this.mMainExecutor = executor;
            this.mBackgroundExecutor = executor2;
        }

        public final EmergencyButtonController create(EmergencyButton emergencyButton) {
            return new EmergencyButtonController(emergencyButton, this.mConfigurationController, this.mInputMethodManager, this.mKeyguardUpdateMonitor, this.mTelephonyManager, this.mPowerManager, this.mActivityTaskManager, this.mShadeController, this.mTelecomManager, this.mMetricsLogger, this.mLockPatternUtils, this.mMainExecutor, this.mBackgroundExecutor);
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.EmergencyButtonController$2] */
    public EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, InputMethodManager inputMethodManager, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2) {
        super(emergencyButton);
        this.mKeyguardShowing = true;
        this.mBouncerShowing = false;
        this.mCurrentSimState = 1;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor("airplane_mode_on")};
        this.mPasswordEntry = null;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.EmergencyButtonController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mBouncerShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mKeyguardShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onOfflineStateChanged() {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.getClass();
                emergencyButtonController.mCurrentSimState = i3;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.EmergencyButtonController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationController = configurationController;
        this.mImm = inputMethodManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        DejankUtils.whitelistIpcs(new EmergencyButtonController$$ExternalSyntheticLambda1(this, 0));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        setEmergencyView(this.mView);
        if (LsRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.mSettingsListener, this.mSettingsValueList);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        if (LsRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        }
    }

    public final void setEmergencyView(View view) {
        EmergencyButton emergencyButton = (EmergencyButton) view;
        this.mView = emergencyButton;
        emergencyButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mMetricsLogger.action(200);
                PowerManager powerManager = emergencyButtonController.mPowerManager;
                if (powerManager != null) {
                    powerManager.userActivity(SystemClock.uptimeMillis(), true);
                }
                emergencyButtonController.mActivityTaskManager.stopSystemLockTaskMode();
                if (!SafeUIState.isSysUiSafeModeEnabled()) {
                    ((ShadeControllerImpl) emergencyButtonController.mShadeController).collapseShade(false);
                }
                emergencyButtonController.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda1(emergencyButtonController, 1));
                SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1031");
            }
        });
    }

    public void updateEmergencyCallButton() {
        boolean z;
        if (this.mView != null) {
            int i = 0;
            TelecomManager telecomManager = this.mTelecomManager;
            if (telecomManager != null && telecomManager.isInCall()) {
                z = true;
            } else {
                z = false;
            }
            boolean isSecure = this.mKeyguardUpdateMonitor.isSecure();
            if (!this.mKeyguardShowing && !this.mBouncerShowing) {
                return;
            }
            this.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda2(this, z, isSecure, i));
        }
    }
}

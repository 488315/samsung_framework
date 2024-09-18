package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings;
import android.sec.enterprise.content.SecContentProviderURI;
import com.samsung.android.audio.AudioConstants;

/* loaded from: classes5.dex */
public class SettingsWrapper {
    private static final String TAG = "SettingsWrapper";
    private final Context mContext;
    private final LogWrapper mLogWrapper;
    private final ContentResolver mResolver;

    /* loaded from: classes5.dex */
    private static class State {
        static int OFF = 0;
        static int ON = 1;

        private State() {
        }
    }

    public SettingsWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mResolver = context.getContentResolver();
    }

    public boolean isBugReportMode() {
        int value = Settings.Global.getInt(this.mResolver, "bugreport_in_power_menu", State.OFF);
        this.mLogWrapper.i(TAG, "(BUGREPORT_IN_POWER_MENU)current value : " + value);
        return value == State.ON;
    }

    public boolean isTaskBarEnabled() {
        boolean value = Settings.Global.getInt(this.mResolver, Settings.Global.SEM_TASK_BAR, 0) == 1;
        this.mLogWrapper.i(TAG, "(SEM_TASK_BAR)current value : " + value);
        return value;
    }

    public boolean isNavBarGestureType() {
        boolean value = Settings.Global.getInt(this.mResolver, Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) > 0;
        this.mLogWrapper.i(TAG, "(NAVIGATION_BAR_GESTURE_WHILE_HIDDEN)current value : " + value);
        return value;
    }

    public boolean isMissingPhoneLock() {
        String value = Settings.System.getString(this.mResolver, "missing_phone_lock");
        this.mLogWrapper.i(TAG, "(missing_phone_lock)current value : " + value);
        return value != null && value.equals("lock");
    }

    public boolean isUltraPowerSavingMode() {
        int value = Settings.System.getInt(this.mResolver, Settings.System.SEM_ULTRA_POWERSAVING_MODE, State.OFF);
        this.mLogWrapper.i(TAG, "(SEM_ULTRA_POWERSAVING_MODE)current value : " + value);
        return value == State.ON;
    }

    public boolean isShopDemo() {
        int value = Settings.Secure.getInt(this.mContext.getContentResolver(), "shopdemo", State.OFF);
        this.mLogWrapper.i(TAG, "(isShopDemo)current value : " + value);
        return value == State.ON;
    }

    public boolean isAirplaneMode() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public boolean isLockNetworkAndSecurityOn() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.LOCK_NOTI_AND_SECURITY, 0) != 0;
    }

    public boolean isLockDownInPowerMenu() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.LOCKDOWN_IN_POWER_MENU, 0, ActivityManager.getCurrentUser()) != 0;
    }

    public boolean isMedicalInfoAccess() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "lock_screen_medical_info_access", 1) == 1;
    }

    public boolean isWifiOnlyDevice() {
        return SystemProperties.get("ro.carrier", "unknown").equals("wifi-only");
    }

    public boolean isEmergencyModeUserAgreement() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE_USER_AGREEMENT, 0, ActivityManager.getCurrentUser()) != 0;
    }

    public boolean isPasswordChangeEnforced() {
        Cursor cr = this.mResolver.query(Uri.parse("content://com.sec.knox.provider/PasswordPolicy2"), null, SecContentProviderURI.PASSWORDPOLICY_CHANGEREQUESTED_METHOD, null, null);
        try {
            cr.moveToFirst();
            int value = cr.getInt(cr.getColumnIndex(SecContentProviderURI.PASSWORDPOLICY_CHANGEREQUESTED_METHOD));
            cr.close();
            return value >= 1;
        } catch (Exception e) {
            cr.close();
            return false;
        }
    }

    public boolean isDeviceOwner() {
        return ActivityManager.getCurrentUser() == 0;
    }

    public boolean isPowerOffUnlockNotRequired() {
        return Settings.System.getInt(this.mResolver, "power_off_lock_option", 1) == 0;
    }

    public boolean isPowerOffUnlockAlwaysRequired() {
        return Settings.System.getInt(this.mResolver, "power_off_lock_option", 1) == 1;
    }

    public boolean isPowerOffUnlockOnlyLockscreenRequired() {
        return Settings.System.getInt(this.mResolver, "power_off_lock_option", 1) == 2;
    }

    public boolean isMinimalBatteryUse() {
        return Settings.System.getInt(this.mResolver, Settings.System.SEM_MINIMAL_BATTERY_USE, 0) == 1;
    }

    public boolean isBikeMode() {
        int value = Settings.Secure.getInt(this.mResolver, AudioConstants.SETTING_BIKE_MODE, State.OFF);
        this.mLogWrapper.i(TAG, "(isBikeMode)current value : " + value);
        return value == State.ON;
    }

    public boolean isRepairMode() {
        int currentUser = ActivityManager.getCurrentUser();
        this.mLogWrapper.i(TAG, "(isRepairMode)current User : " + currentUser);
        return currentUser == 77;
    }
}

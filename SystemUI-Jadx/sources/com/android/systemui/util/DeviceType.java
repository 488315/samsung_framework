package com.android.systemui.util;

import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Debug;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.sec.ims.configuration.DATA;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DeviceType {
    public static int debugLevel = -1;
    public static boolean mIsSupport5G = false;
    public static boolean mIsSupport5GChecked = false;
    public static int sCachedEngOrUTBinary = -1;
    public static int sRpCount = -2;
    public static int supportCover = -1;
    public static int supportDeadzone = -1;
    public static int supportESim = -1;
    public static int supportFBE = -1;
    public static int supportInDisplayFingerprint = -1;
    public static int supportLightReveal = -1;
    public static int supportLightSensor = -1;
    public static int supportMultiSIM = -1;
    public static int supportOpticalFingerprint = -1;
    public static Boolean supportPenDetachmentOption = null;
    public static int supportSEPLite = -1;
    public static int supportTablet = -1;
    public static int supportVibrator = -1;
    public static int supportWeaver = -1;
    public static int supportWifiOnly = -1;

    static {
        SemSystemProperties.getBoolean("mdc.sys.enable_smff", false);
        SemSystemProperties.getBoolean("mdc.singlesku", false);
        SemSystemProperties.getBoolean("mdc.unified", false);
    }

    public static int getDebugLevel() {
        if (debugLevel == -1) {
            String str = SystemProperties.get("ro.boot.debug_level", "");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.debug_level", "");
            }
            if (!TextUtils.isEmpty(str) && !"0x4f4c".equalsIgnoreCase(str)) {
                if ("0x494d".equalsIgnoreCase(str)) {
                    debugLevel = 1;
                } else if ("0x4948".equalsIgnoreCase(str)) {
                    debugLevel = 2;
                }
            } else {
                debugLevel = 0;
            }
        }
        return debugLevel;
    }

    public static boolean isCoverSupported() {
        Application currentApplication;
        if (supportCover == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
            supportCover = currentApplication.getPackageManager().hasSystemFeature("com.sec.feature.cover") ? 1 : 0;
        }
        if (supportCover == 1) {
            return true;
        }
        return false;
    }

    public static boolean isEngOrUTBinary() {
        int i;
        if (sCachedEngOrUTBinary < 0) {
            if (!Debug.semIsProductDev() && !"1".equals(SystemProperties.get("persist.log.seclevel", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN))) {
                i = 0;
            } else {
                i = 1;
            }
            sCachedEngOrUTBinary = i;
        }
        if (sCachedEngOrUTBinary > 0) {
            return true;
        }
        return false;
    }

    public static boolean isFbeSupported() {
        if (supportFBE == -1) {
            supportFBE = StorageManager.isFileEncryptedNativeOnly() ? 1 : 0;
        }
        if (supportFBE == 1) {
            return true;
        }
        return false;
    }

    public static boolean isLDUSKU() {
        String str = SystemProperties.get("ril.product_code", "");
        if (str.length() < 11) {
            return false;
        }
        if (str.charAt(10) != '8' && str.charAt(10) != '9') {
            return false;
        }
        return true;
    }

    public static boolean isLightSensorSupported(Context context) {
        if (supportLightSensor == -1) {
            List<Sensor> sensorList = ((SensorManager) context.getSystemService("sensor")).getSensorList(-1);
            int size = sensorList.size();
            for (int i = 0; i < size; i++) {
                int type = sensorList.get(i).getType();
                if (type == 5 || type == 65604) {
                    supportLightSensor = 1;
                    return true;
                }
            }
            supportLightSensor = 0;
        }
        if (supportLightSensor != 1) {
            return false;
        }
        return true;
    }

    public static boolean isMultiSimSupported() {
        int i;
        if (supportMultiSIM == -1) {
            if (TelephonyManager.getDefault().getPhoneCount() > 1) {
                i = 1;
            } else {
                i = 0;
            }
            supportMultiSIM = i;
        }
        if (supportMultiSIM != 1) {
            return false;
        }
        return true;
    }

    public static boolean isShipBuild() {
        return "true".equals(SystemProperties.get("ro.product_ship", "false"));
    }

    public static boolean isSupportESim() {
        Application currentApplication;
        if (supportESim == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
            supportESim = currentApplication.getPackageManager().hasSystemFeature("android.hardware.telephony.euicc") ? 1 : 0;
        }
        if (supportESim == 1) {
            return true;
        }
        return false;
    }

    public static boolean isSupportPenDetachmentOption(Context context) {
        if (supportPenDetachmentOption == null) {
            supportPenDetachmentOption = Boolean.valueOf(context.getPackageManager().hasSystemFeature("com.sec.feature.spen_usp"));
        }
        return supportPenDetachmentOption.booleanValue();
    }

    public static boolean isTablet() {
        if (supportTablet == -1) {
            supportTablet = SystemProperties.get("ro.build.characteristics", "phone").contains("tablet") ? 1 : 0;
        }
        if (supportTablet == 1) {
            return true;
        }
        return false;
    }

    public static boolean isVibratorSupported(Context context) {
        int i;
        if (supportVibrator == -1) {
            Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
            if (vibrator != null && vibrator.hasVibrator()) {
                i = 1;
            } else {
                i = 0;
            }
            supportVibrator = i;
        }
        if (supportVibrator != 1) {
            return false;
        }
        return true;
    }

    public static boolean isWeaverDevice() {
        if (supportWeaver == -1) {
            supportWeaver = SystemProperties.getBoolean("security.securehw.available", false) ? 1 : 0;
            RecyclerView$$ExternalSyntheticOutline0.m(new StringBuilder("isWeaverDevice : "), supportWeaver, "DeviceType");
        }
        if (supportWeaver != 1) {
            return false;
        }
        return true;
    }

    public static boolean isWiFiOnlyDevice() {
        if (supportWifiOnly == -1) {
            supportWifiOnly = "wifi-only".equals(SystemProperties.get("ro.carrier", "unknown")) ? 1 : 0;
        }
        if (supportWifiOnly == 1) {
            return true;
        }
        return false;
    }
}

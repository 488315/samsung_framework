package com.android.systemui;

import android.os.Build;
import android.os.SystemProperties;
import com.android.systemui.util.DeviceType;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class QpRune extends Rune {
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_SECOND;
    public static final boolean PANEL_DATA_USAGE_LABEL;
    public static final boolean QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL;
    public static final boolean QUICK_BAR_EXTRA_BRIGHTNESS;
    public static final boolean QUICK_BAR_MULTISIM;
    public static final boolean QUICK_BLUETOOTH_MUSIC_SHARE;
    public static final boolean QUICK_HIDE_TILE_FROM_BAR;
    public static final boolean QUICK_MANAGE_TWO_PHONE;
    public static final boolean QUICK_ONE_UI_6_1;
    public static final boolean QUICK_PANEL_BLUR;
    public static final boolean QUICK_PANEL_BLUR_DEFAULT;
    public static final boolean QUICK_PANEL_BLUR_MASSIVE;
    public static final boolean QUICK_PANEL_SUBSCREEN;
    public static final boolean QUICK_PANEL_SUBSCREEN_QUICK_PANEL_WINDOW;
    public static final boolean QUICK_SETTINGS_SUBSCREEN;
    public static final boolean QUICK_STYLE_ALTERNATE_CALENDAR;
    public static final boolean QUICK_STYLE_ALTERNATE_CALENDAR_HIJRI;
    public static final boolean QUICK_STYLE_ALTERNATE_CALENDAR_LUNAR_IN_VIETNAM;
    public static final boolean QUICK_STYLE_ALTERNATE_CALENDAR_PERSIAN;
    public static final boolean QUICK_TABLET;
    public static final boolean QUICK_TABLET_BG;
    public static final boolean QUICK_TABLET_HORIZONTAL_PANEL_POSITION;
    public static final boolean QUICK_TABLET_TOP_MARGIN;
    public static final boolean QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE;
    public static final boolean QUICK_TILE_FLASHLIGHT_INTENSITY;
    public static final boolean QUICK_TILE_NIGHT_DIM;
    public static final boolean QUICK_TILE_ROTATION_MANUAL;

    static {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        String string;
        boolean z6;
        boolean z7 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        QUICK_PANEL_BLUR_DEFAULT = z7;
        if (!"1".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents"))) {
            "true".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents"));
        }
        boolean z8 = true;
        if (!z7 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR")) {
            z = true;
        } else {
            z = false;
        }
        QUICK_PANEL_BLUR_MASSIVE = z;
        if (!z7 && !z) {
            z2 = false;
        } else {
            z2 = true;
        }
        QUICK_PANEL_BLUR = z2;
        boolean isTablet = DeviceType.isTablet();
        QUICK_TABLET = isTablet;
        QUICK_TABLET_BG = isTablet;
        QUICK_TABLET_TOP_MARGIN = isTablet;
        QUICK_TABLET_HORIZONTAL_PANEL_POSITION = isTablet;
        boolean z9 = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportPersianCalendar", false);
        QUICK_STYLE_ALTERNATE_CALENDAR_PERSIAN = z9;
        boolean z10 = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportHijriLunarCalendar", false);
        QUICK_STYLE_ALTERNATE_CALENDAR_HIJRI = z10;
        boolean equals = "VI".equals(SemCscFeature.getInstance().getString("CscFeature_Calendar_EnableLocalHolidayDisplay", (String) null));
        QUICK_STYLE_ALTERNATE_CALENDAR_LUNAR_IN_VIETNAM = equals;
        if (!z9 && !z10 && !equals) {
            z3 = false;
        } else {
            z3 = true;
        }
        QUICK_STYLE_ALTERNATE_CALENDAR = z3;
        QUICK_MANAGE_TWO_PHONE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");
        QUICK_BAR_MULTISIM = Rune.SYSUI_MULTI_SIM;
        QUICK_HIDE_TILE_FROM_BAR = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false);
        QUICK_BAR_EXTRA_BRIGHTNESS = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS");
        QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL = true;
        SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("aodversion");
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE = z4;
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM", 0) == 1) {
            z5 = true;
        } else {
            z5 = false;
        }
        QUICK_TILE_NIGHT_DIM = z5;
        if (!"user".equals(Build.TYPE) && (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) != 0) {
            string = "";
        } else {
            string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        }
        boolean contains = string.contains("WATCHFACE");
        NOTI_SUBSCREEN_NOTIFICATION_SECOND = contains;
        QUICK_SETTINGS_SUBSCREEN = contains;
        if (contains && string.contains("LARGESCREEN")) {
            z6 = true;
        } else {
            z6 = false;
        }
        QUICK_PANEL_SUBSCREEN = z6;
        QUICK_PANEL_SUBSCREEN_QUICK_PANEL_WINDOW = z6;
        QUICK_TILE_FLASHLIGHT_INTENSITY = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_TORCH_BRIGHTNESS_LEVEL");
        QUICK_BLUETOOTH_MUSIC_SHARE = SemBluetoothCastAdapter.isBluetoothCastSupported();
        QUICK_TILE_ROTATION_MANUAL = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "").isEmpty();
        if (Build.VERSION.SEM_PLATFORM_INT < 150100) {
            z8 = false;
        }
        QUICK_ONE_UI_6_1 = z8;
        PANEL_DATA_USAGE_LABEL = Rune.SYSUI_CHINA_FEATURE;
    }
}

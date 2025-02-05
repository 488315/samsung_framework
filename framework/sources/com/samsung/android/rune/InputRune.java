package com.samsung.android.rune;

import android.os.Debug;
import android.os.FactoryTest;
import android.os.SystemProperties;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes6.dex */
public class InputRune {
    public static final boolean IFW = true;
    public static final boolean IFW_120HZ_FORCE_INTERPOLATION = true;
    public static final boolean IFW_ADDITIONAL_KEYCODE = true;
    public static final boolean IFW_ADD_WAKEKEY = true;
    public static final boolean IFW_API_MONITOR_INPUT = true;
    public static final boolean IFW_API_TOAST_DIALOG = true;
    public static final boolean IFW_API_WAKE_UP = true;
    public static final boolean IFW_BLOCK_DEVICE_EVENT = true;
    public static final boolean IFW_BLOCK_KEYS_WHEN_SHUTDOWN = true;
    public static final boolean IFW_BUG_FIX = true;
    public static final boolean IFW_CCIC_DOCK = true;
    public static final boolean IFW_CHANGE_MOUSE_ACTION = true;
    public static final boolean IFW_CHANGE_WACOM_MODE = true;
    public static final boolean IFW_CHECK_INPUT_FEATURE = true;
    public static final boolean IFW_COMMON = true;
    public static final boolean IFW_CONTROL_SEPN_STATE = true;
    public static final boolean IFW_DEX_CURSOR_TIMEOUT = true;
    public static final boolean IFW_DEX_MOUSE_DUAL_DISPLAY = true;
    public static final boolean IFW_DISABLE_DOCK_SOUND = true;
    public static final boolean IFW_DOCK_PID_VID = true;
    public static final boolean IFW_ENHANCED_GAME_PERFORMANCE = true;
    public static final boolean IFW_ENHANCED_GESTURE = true;
    public static final boolean IFW_ENHANCED_KEYBOARD_LAYOUT = true;
    public static final boolean IFW_EXTERNAL_API = true;
    public static final boolean IFW_FOLDABLE_DEVICE = true;
    public static final boolean IFW_FORCE_FADE_ICON = true;
    public static final boolean IFW_GAMEPAD_REMAP_PROFILE = true;
    public static final boolean IFW_GET_GLOBAL_METASTATE = true;
    public static final boolean IFW_GET_IQ_LENGTH = true;
    public static final boolean IFW_GET_MOTION_IDLE_TIME = true;
    public static final boolean IFW_GET_SCANCODE_STATE = true;
    public static final boolean IFW_IS_UID_TOUCHED = true;
    public static final boolean IFW_LID_STATE_LISTENER = true;
    public static final boolean IFW_LOG = true;
    public static final boolean IFW_NOTIFY_POINTER_OUTSIDE_FOCUS = true;
    public static final boolean IFW_OBSCURED_WARNING_TOAST = true;
    public static final boolean IFW_PERSISTENT_META_STATE = true;
    public static final boolean IFW_QUICK_ACCESS = true;
    public static final boolean IFW_REVERSE_VOLUME_KEY_WHEN_FOLDED = false;
    public static final boolean IFW_SEPARATE_INTERACTIVE_STATE = true;
    public static final boolean IFW_SET_TSP_ENABLED = true;
    public static final boolean IFW_SET_WAKEKEY_DYNAMICALLY = true;
    public static final boolean IFW_SHOWING_TOUCH_SENSITIVITY_NOTIFICATION = true;
    public static final boolean IFW_SHOW_ALL_TOUCHES = true;
    public static final boolean IFW_SPAY_INIT_CMD = true;
    public static final boolean IFW_STICKY_KEYS = true;
    public static final boolean IFW_SUPPORT_COVER = true;
    public static final boolean IFW_SUPPORT_DEX = true;
    public static final boolean IFW_SUPPORT_FLEXIBLE_DISPLAY = false;
    public static final boolean IFW_SUPPORT_KIDSMODE = true;
    public static final boolean IFW_SUPPORT_MULTI_CONTROL = true;
    public static final boolean IFW_SUPPORT_MULTI_FOLD = true;
    public static final boolean IFW_SUPPORT_POGO = true;
    public static final boolean IFW_SUPPORT_SPEN = true;
    public static final boolean IFW_SUPPORT_SWITCH_EVENT_LISTENER = true;
    public static final boolean IFW_SUPPORT_USERKEY_AS_WAKEKEY = false;
    public static final boolean IFW_SUPPORT_WFD = true;
    public static final boolean IFW_VIRTUAL_TOUCHPAD = true;
    public static final boolean IFW_WIRELESS_KEYBOARD_MOUSE_SHARE = true;
    public static final boolean KNOX_CAPTURE_XCOVER_OR_TOP_KEY;
    public static final boolean PWM = true;
    public static final boolean PWM_ACCESSIBILITY_SHORTCUT_VOLUP_POWER = true;
    public static final boolean PWM_ACCESSIBILITY_SHORTCUT_VOLUP_VOLDOWN = true;
    public static final boolean PWM_ACTIVE_KEY;
    public static final boolean PWM_ACTIVE_OR_XCOVER_KEY;
    public static final boolean PWM_B2B_DEDICATED_APP;
    public static final String PWM_BIXBY_ONBOARDING_SERVICE_COMPONENT = "com.samsung.android.bixby.agent/com.samsung.android.bixby.BixbyKeyLService";
    public static final String PWM_BIXBY_SERVICE_COMPONENT = "com.samsung.android.bixby.agent/com.samsung.android.bixby.WinkService";
    public static final boolean PWM_BRIGHTNESS_KEY = true;
    public static final boolean PWM_COMMON = true;
    public static final boolean PWM_DOUBLE_TAP_PREMIUM_WATCH;
    public static final boolean PWM_DOUBLE_TAP_TO_WAKE_UP_ON_SCREEN = true;
    public static final boolean PWM_EXTERNAL_KEYBOARD_SHORTCUT = true;
    public static final boolean PWM_FAKE_FOCUSED_WINDOW = true;
    public static final boolean PWM_FINGERPRINT_BIXBY_UNLOCK = false;
    public static final boolean PWM_FINGERPRINT_ON_DISPLAY = true;
    public static final boolean PWM_FINGERPRINT_SIDE_TOUCH;
    public static final boolean PWM_FN_LOCK_UNLOCK_TOAST = true;
    public static final boolean PWM_GAME_CONTROLLER_POLICY = true;
    public static final boolean PWM_GAME_TOOLS_WINDOW_POLICY = true;
    public static final boolean PWM_HANDLE_ORIENTATION_BY_PROXIMITY_SENSOR = true;
    public static final boolean PWM_HAPTIC_FEEDBACK_ON_DC_MOTOR;
    public static final boolean PWM_HOME_ANSWER_CALL = true;
    public static final boolean PWM_HOME_KEY = true;
    public static final boolean PWM_HOME_KEY_LONG_PRESS = true;
    public static final boolean PWM_HOME_KEY_LONG_PRESS_SEARCLE;
    public static final boolean PWM_KEYGUARD_PENDING_INTENT = true;
    public static final boolean PWM_KEY_BUG_FIX = true;
    public static final boolean PWM_KEY_COMBINATION = true;
    public static final boolean PWM_KEY_COMBINATION_DISABLE_VOLUP_POWER = true;
    public static final boolean PWM_KEY_COMBINATION_GLOBAL_ACTION;
    public static final boolean PWM_KEY_COMBINATION_INTERACTION_CONTROL = true;
    public static final boolean PWM_KEY_COMBINATION_SCREENSHOT = true;
    public static final boolean PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
    public static final boolean PWM_KEY_CUSTOMIZATION = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_BACKUP_N_RESTORE = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_FOR_KNOX = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_HOT_KEY = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_MIGRATION = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_MOUSE_BUTTON = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_MULTI_USER = true;
    public static final boolean PWM_KEY_CUSTOMIZATION_SHORTCUT = true;
    public static final boolean PWM_KEY_FACTORY_TEST_POLICY = true;
    public static final boolean PWM_KEY_FLIP_POLICY = false;
    public static final boolean PWM_KEY_LARGE_FLIP_POLICY = false;
    public static final boolean PWM_KEY_POLICY = true;
    public static final boolean PWM_KEY_POLICY_TESTS = true;
    public static final boolean PWM_KEY_WM_TESTS = true;
    public static final boolean PWM_KODIAK_DEDICATED_PTT_APP;
    public static final boolean PWM_LOCK_TASK_MODE_PINNED_RECENT_BACK = true;
    public static final boolean PWM_META_KEY_SEND_TO_APP = true;
    public static final boolean PWM_MULTI_FINGER_GESTURE = true;
    public static final boolean PWM_NOTIFY_HOME_KEY_PRESSED = true;
    public static final boolean PWM_NOTIFY_SYSTEM_KEY_EVENT_CHANGED = true;
    public static final boolean PWM_OBSERVING_DIFFERENT_HDMI_PATH_ACCORDING_TO_CHIPSET = true;
    public static final boolean PWM_OMC_SPEN_SOUND;
    public static final boolean PWM_OMC_SPEN_VIBRATION;
    public static final boolean PWM_PHONE_FALLBACK_EVENT = true;
    public static final boolean PWM_POGO_KEYBOARD_FOR_TABLET = true;
    public static final boolean PWM_POWER_KEY_END_CALL = true;
    public static final boolean PWM_POWER_KEY_MULTI_PRESS = true;
    public static final boolean PWM_POWER_KEY_WAKE_LOCK = true;
    public static final boolean PWM_POWER_OFF_NO_CONFIRM = true;
    public static final boolean PWM_PROXIMITY_SENSOR = true;
    public static final boolean PWM_QUICK_ACCESS_AOD = true;
    public static final boolean PWM_QUICK_ACCESS_FINGERPRINT = true;
    public static final boolean PWM_QUICK_ACCESS_SAMSUNG_PAY = true;
    public static final boolean PWM_QUICK_LAUNCH_CAMERA;
    public static final boolean PWM_RECENT_APPS_KEY = true;
    public static final boolean PWM_SAFETY_SYSTEM_SERVICE = true;
    public static final boolean PWM_SCREEN_OFF_BY_PALM_TOUCH_DOWN = false;
    public static final boolean PWM_SEC_HAPTIC_FEEDBACK = true;
    public static final boolean PWM_SEC_KEY_SOUND_CONCEPT = true;
    public static final boolean PWM_SHOWING_ESC_DIALOG = true;
    public static final boolean PWM_SIDE_KEY_B2B_DELTA = true;
    public static final boolean PWM_SIDE_KEY_DIGITAL_ASSISTANT;
    public static final boolean PWM_SIDE_KEY_DOUBLE_PRESS_SECURE_FOLDER;
    public static final boolean PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS;
    public static final boolean PWM_SIDE_KEY_TORCH;
    public static final boolean PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL;
    public static final boolean PWM_SIDE_KEY_WAKE_UP_BIXBY;
    public static final boolean PWM_SIDE_SUPPORT_AI_AGENT;
    public static final boolean PWM_SIM_LOCK_POLICY = true;
    public static final boolean PWM_SINGLE_KEY_GESTURE_DETECTOR = true;
    public static final boolean PWM_SKIP_TOO_FAST_DOUBLE_PRESS;
    public static final boolean PWM_SKT_PHONE_RELAX_MODE;
    public static final boolean PWM_SPEN;
    public static final boolean PWM_SPEN_HOVER;
    public static final boolean PWM_SPEN_SCREEN_OFF_MEMO;
    public static final boolean PWM_SPEN_SMART_CLIP;
    public static final int PWM_SPEN_USP_LEVEL;
    public static final boolean PWM_SUPPORT_BIXBY_SERVICE = true;
    public static final boolean PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD;
    public static final boolean PWM_SUPPORT_DOWNLOADABLE_RESERVE_BATTERY_MODE = false;
    public static final boolean PWM_SUPPORT_RESERVE_BATTERY_MODE = false;
    public static final boolean PWM_SUPPORT_TOOLBAR_SHORTCUT;
    public static final boolean PWM_SYSTEM_KEY_SEND_TO_APP = true;
    public static final boolean PWM_TOUCHPAD_ON_OFF_TOAST = true;
    public static final boolean PWM_TOUCHSLOP = true;
    public static final boolean PWM_VIEW_CONCEPT = true;
    public static final boolean PWM_VOLUME_UP_ANSWER_CALL = true;
    public static final boolean PWM_XCOVER_AND_TOP_KEY;
    private static final boolean PWM_XCOVER_KEY;
    public static final boolean PWM_XCOVER_TOP_KEY_B2B_DELTA;
    public static final boolean SEP = true;
    public static final boolean IFW_WIRELESS_KEYBOARD_SA_LOGGING = CoreRune.FW_SA_LOGGING;
    public static final boolean IFW_KEY_COUNTER = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public static final boolean IS_FACTORY_BINARY = FactoryTest.isFactoryMode();
    public static final boolean PWM_KEY_FACTORY_MODE_POLICY = FactoryTest.isFactoryMode();
    public static boolean SAFE_DEBUG = Debug.semIsProductDev();
    public static final boolean PWM_KEY_SA_LOGGING = CoreRune.FW_SA_LOGGING;
    public static final boolean PWM_SUPPORT_BIXBY_ONBOARDING_SERVICE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BIXBY_SUPPORT_LONG_KEY_SERVICE");
    public static final boolean PWM_SUPPORT_BIXBY_TOUCH_CHN = "true".equals(SystemProperties.get("ro.bbt.support.circle2search"));
    public static final boolean PWM_SUPPORT_SEARCLE = "bsxasm1".equals(SystemProperties.get("ro.com.google.cdb.spa1"));
    public static final boolean PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", "").contains("TvMode");
    public static final boolean PWM_SIDE_KEY = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU");
    public static final boolean PWM_SIDE_KEY_DOUBLE_PRESS = PWM_SIDE_KEY;
    public static final boolean PWM_SIDE_KEY_LONG_PRESS = PWM_SIDE_KEY;

    static {
        PWM_SIDE_KEY_WAKE_UP_BIXBY = PWM_SIDE_KEY_LONG_PRESS && !SemCscFeature.getInstance().getBoolean("CscFeature_Common_DisableBixby", false);
        PWM_SIDE_SUPPORT_AI_AGENT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT");
        PWM_SIDE_KEY_DIGITAL_ASSISTANT = PWM_SIDE_KEY_LONG_PRESS && PWM_SIDE_SUPPORT_AI_AGENT;
        PWM_SIDE_KEY_TORCH = PWM_SIDE_KEY;
        PWM_SIDE_KEY_DOUBLE_PRESS_SECURE_FOLDER = PWM_SIDE_KEY_DOUBLE_PRESS && SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportPrivateMode");
        PWM_KEY_COMBINATION_GLOBAL_ACTION = PWM_SIDE_KEY;
        PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY = PWM_SIDE_KEY;
        PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD = PWM_SIDE_KEY_WAKE_UP_BIXBY || PWM_XCOVER_AND_TOP_KEY;
        PWM_HOME_KEY_LONG_PRESS_SEARCLE = PWM_SUPPORT_SEARCLE || PWM_SUPPORT_BIXBY_TOUCH_CHN;
        PWM_SKT_PHONE_RELAX_MODE = "tphone".equalsIgnoreCase(SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigOperatorCallService"));
        PWM_FINGERPRINT_SIDE_TOUCH = "google_touch_display_ultrasonic".contains("touch_side");
        PWM_SUPPORT_TOOLBAR_SHORTCUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_EMOJI_SHORTCUT");
        PWM_SPEN_USP_LEVEL = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1);
        PWM_SPEN = PWM_SPEN_USP_LEVEL > 0;
        PWM_SPEN_SCREEN_OFF_MEMO = PWM_SPEN;
        PWM_SPEN_HOVER = PWM_SPEN;
        PWM_SPEN_SMART_CLIP = PWM_SPEN;
        PWM_OMC_SPEN_SOUND = PWM_SPEN;
        PWM_OMC_SPEN_VIBRATION = PWM_SPEN && SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportSpenEditionVibration");
        PWM_HAPTIC_FEEDBACK_ON_DC_MOTOR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK");
        PWM_DOUBLE_TAP_PREMIUM_WATCH = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
        PWM_ACTIVE_KEY = "".contains("active");
        PWM_XCOVER_KEY = "".contains("xcover");
        PWM_ACTIVE_OR_XCOVER_KEY = PWM_ACTIVE_KEY || PWM_XCOVER_KEY;
        PWM_XCOVER_AND_TOP_KEY = PWM_ACTIVE_OR_XCOVER_KEY && "".contains("xcoverpro");
        PWM_XCOVER_TOP_KEY_B2B_DELTA = PWM_XCOVER_AND_TOP_KEY || PWM_ACTIVE_KEY;
        PWM_B2B_DEDICATED_APP = true;
        PWM_KODIAK_DEDICATED_PTT_APP = PWM_XCOVER_TOP_KEY_B2B_DELTA;
        PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL = "PanicMode".equalsIgnoreCase(SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigActionForMultiPowerPress"));
        PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS = !PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL;
        PWM_QUICK_LAUNCH_CAMERA = !PWM_SIDE_KEY;
        PWM_SKIP_TOO_FAST_DOUBLE_PRESS = PWM_FINGERPRINT_SIDE_TOUCH;
        KNOX_CAPTURE_XCOVER_OR_TOP_KEY = PWM_ACTIVE_OR_XCOVER_KEY || PWM_XCOVER_AND_TOP_KEY;
    }
}

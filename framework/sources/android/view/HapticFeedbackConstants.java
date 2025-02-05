package android.view;

import com.samsung.android.vibrator.SemHapticFeedbackConstants;

/* loaded from: classes4.dex */
public class HapticFeedbackConstants {
    public static final int ASSISTANT_BUTTON = 10002;
    public static final int BIOMETRIC_CONFIRM = 10004;
    public static final int BIOMETRIC_REJECT = 10005;
    public static final int CALENDAR_DATE = 5;
    public static final int CLOCK_TICK = 4;
    public static final int CONFIRM = 16;
    public static final int CONTEXT_CLICK = 6;
    public static final int DRAG_CROSSING = 11;
    public static final int DRAG_START = 25;
    public static final int EDGE_RELEASE = 15;
    public static final int EDGE_SQUEEZE = 14;

    @Deprecated
    public static final int FLAG_IGNORE_GLOBAL_SETTING = 2;
    public static final int FLAG_IGNORE_VIEW_SETTING = 1;
    public static final int GESTURE_END = 13;
    public static final int GESTURE_START = 12;
    public static final int GESTURE_THRESHOLD_ACTIVATE = 23;
    public static final int GESTURE_THRESHOLD_DEACTIVATE = 24;
    public static final int GRAB = 50132;
    public static final int KEYBOARD_PRESS = 3;
    public static final int KEYBOARD_RELEASE = 7;
    public static final int KEYBOARD_TAP = 3;
    public static final int LONG_PRESS = 0;
    public static final int LONG_PRESS_POWER_BUTTON = 10003;
    public static final int NO_HAPTICS = -1;
    public static final int REJECT = 17;
    public static final int SAFE_MODE_ENABLED = 10001;
    public static final int SCROLL_ITEM_FOCUS = 19;
    public static final int SCROLL_LIMIT = 20;
    public static final int SCROLL_TICK = 18;
    public static final int SEGMENT_FREQUENT_TICK = 27;
    public static final int SEGMENT_TICK = 26;
    public static final int TEXT_HANDLE_MOVE = 9;
    public static final int TOGGLE_OFF = 22;
    public static final int TOGGLE_ON = 21;
    public static final int VIRTUAL_KEY = 1;
    public static final int VIRTUAL_KEY_RELEASE = 8;

    private HapticFeedbackConstants() {
    }

    public static int semGetVibrationIndex(int auiHapticPatternIndex) {
        return SemHapticFeedbackConstants.semGetVibrationIndex(auiHapticPatternIndex);
    }

    private static int hidden_semGetVibrationIndex(int index) {
        return semGetVibrationIndex(index);
    }
}

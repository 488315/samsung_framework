package android.view;

import android.app.AppGlobals;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.input.InputManagerGlobal;
import android.os.RemoteException;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.flags.Flags;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ViewConfiguration {
    private static final int A11Y_SHORTCUT_KEY_TIMEOUT = 3000;
    private static final int A11Y_SHORTCUT_KEY_TIMEOUT_AFTER_CONFIRMATION = 1000;
    private static final long ACTION_MODE_HIDE_DURATION_DEFAULT = 2000;
    private static final float AMBIGUOUS_GESTURE_MULTIPLIER = 2.0f;
    private static final int BIT_COUNT_DENSITY = 10;
    private static final int BIT_COUNT_WIDTH_HEIGHT = 11;
    private static final int DEFAULT_KEY_REPEAT_DELAY_MS = 50;
    private static final int DEFAULT_KEY_REPEAT_TIMEOUT_MS = 400;
    public static final int DEFAULT_LONG_PRESS_TIMEOUT = 400;
    private static final int DEFAULT_MULTI_PRESS_TIMEOUT = 300;
    private static final int DOUBLE_TAP_MIN_TIME = 40;
    private static final int DOUBLE_TAP_SLOP = 100;
    private static final int DOUBLE_TAP_TIMEOUT = 300;
    private static final int DOUBLE_TAP_TOUCH_SLOP = 8;
    private static final int EDGE_SLOP = 12;
    private static final int FADING_EDGE_LENGTH = 12;
    private static final int GLOBAL_ACTIONS_KEY_TIMEOUT = 500;
    private static final int HANDWRITING_GESTURE_LINE_MARGIN = 16;
    private static final int HANDWRITING_SLOP = 2;
    private static final int HAS_PERMANENT_MENU_KEY_AUTODETECT = 0;
    private static final int HAS_PERMANENT_MENU_KEY_FALSE = 2;
    private static final int HAS_PERMANENT_MENU_KEY_TRUE = 1;
    private static final float HORIZONTAL_SCROLL_FACTOR = 64.0f;
    private static final int HOVER_TAP_SLOP = 20;
    private static final int HOVER_TAP_TIMEOUT = 150;
    private static final int HOVER_TOOLTIP_HIDE_SHORT_TIMEOUT = 3000;
    private static final int HOVER_TOOLTIP_HIDE_TIMEOUT = 15000;
    private static final int HOVER_TOOLTIP_SHOW_TIMEOUT = 500;
    private static final int JUMP_TAP_TIMEOUT = 500;
    private static final int LONG_PRESS_TOOLTIP_HIDE_TIMEOUT = 1500;

    @Deprecated
    private static final int MAXIMUM_DRAWING_CACHE_SIZE = 1536000;
    private static final int MAXIMUM_FLING_VELOCITY = 8000;
    private static final int MINIMUM_FLING_VELOCITY = 50;
    private static final int MIN_SCROLLBAR_TOUCH_TARGET = 48;
    private static final int NO_FLING_MAX_VELOCITY = Integer.MIN_VALUE;
    private static final int NO_FLING_MIN_VELOCITY = Integer.MAX_VALUE;
    public static final int NO_HAPTIC_SCROLL_TICK_INTERVAL = Integer.MAX_VALUE;
    private static final int OVERFLING_DISTANCE = 6;
    private static final int OVERSCROLL_DISTANCE = 0;
    private static final int PAGING_TOUCH_SLOP = 16;
    private static final int PRESSED_STATE_DURATION = 64;
    private static final int SCREENSHOT_CHORD_KEY_TIMEOUT = 0;
    private static final int SCROLL_BAR_DEFAULT_DELAY = 300;
    private static final int SCROLL_BAR_FADE_DURATION = 250;
    private static final int SCROLL_BAR_SIZE = 4;
    private static final float SCROLL_FRICTION = 0.015f;
    private static final long SEND_RECURRING_ACCESSIBILITY_EVENTS_INTERVAL_MILLIS = 100;
    private static final int SMART_SELECTION_INITIALIZED_TIMEOUT_IN_MILLISECOND = 200;
    private static final int SMART_SELECTION_INITIALIZING_TIMEOUT_IN_MILLISECOND = 500;
    private static final String TAG = "ViewConfiguration";
    private static final int TAP_TIMEOUT = 100;
    private static final int TOUCH_SLOP = 8;
    private static final float VERTICAL_SCROLL_FACTOR = 64.0f;
    private static final int WINDOW_TOUCH_SLOP = 16;
    private static final int ZOOM_CONTROLS_TIMEOUT = 3000;
    static final SparseArray<ViewConfiguration> sConfigurations = new SparseArray<>(2);
    private final float mAmbiguousGestureMultiplier;
    private final boolean mConstructedWithContext;
    private final int mDoubleTapSlop;
    private final int mDoubleTapTouchSlop;
    private final int mEdgeSlop;
    private final int mFadingEdgeLength;
    private final boolean mFadingMarqueeEnabled;
    private final long mGlobalActionsKeyTimeout;
    private final int mHandwritingGestureLineMargin;
    private final int mHandwritingSlop;
    private final float mHorizontalScrollFactor;
    private final int mHoverSlop;
    private final int mMaximumDrawingCacheSize;
    private final int mMaximumFlingVelocity;
    private final int mMaximumRotaryEncoderFlingVelocity;
    private final int mMinScalingSpan;
    private final int mMinScrollbarTouchTarget;
    private final int mMinimumFlingVelocity;
    private final int mMinimumRotaryEncoderFlingVelocity;
    private final int mOverflingDistance;
    private final int mOverscrollDistance;
    private final int mPagingTouchSlop;
    private final boolean mPreferKeepClearForFocusEnabled;
    private final boolean mRotaryEncoderHapticScrollFeedbackEnabled;
    private final int mRotaryEncoderHapticScrollFeedbackTickIntervalPixels;
    private final long mScreenshotChordKeyTimeout;
    private final int mScrollbarSize;
    private final boolean mShowMenuShortcutsWhenKeyboardPresent;
    private final int mSmartSelectionInitializedTimeout;
    private final int mSmartSelectionInitializingTimeout;
    private final int mTouchSlop;
    private final float mVerticalScrollFactor;
    private final boolean mViewBasedRotaryEncoderScrollHapticsEnabledConfig;
    private final int mWindowTouchSlop;
    private boolean sHasPermanentMenuKey;
    private boolean sHasPermanentMenuKeySet;

    @Deprecated
    public ViewConfiguration() {
        this.mConstructedWithContext = false;
        this.mEdgeSlop = 12;
        this.mFadingEdgeLength = 12;
        this.mMinimumFlingVelocity = 50;
        this.mMaximumFlingVelocity = 8000;
        this.mMinimumRotaryEncoderFlingVelocity = 50;
        this.mMaximumRotaryEncoderFlingVelocity = 8000;
        this.mRotaryEncoderHapticScrollFeedbackEnabled = false;
        this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels = Integer.MAX_VALUE;
        this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig = false;
        this.mScrollbarSize = 4;
        this.mTouchSlop = 8;
        this.mHandwritingSlop = 2;
        this.mHoverSlop = 4;
        this.mMinScrollbarTouchTarget = 48;
        this.mDoubleTapTouchSlop = 8;
        this.mPagingTouchSlop = 16;
        this.mDoubleTapSlop = 100;
        this.mWindowTouchSlop = 16;
        this.mHandwritingGestureLineMargin = 16;
        this.mAmbiguousGestureMultiplier = 2.0f;
        this.mMaximumDrawingCacheSize = MAXIMUM_DRAWING_CACHE_SIZE;
        this.mOverscrollDistance = 0;
        this.mOverflingDistance = 6;
        this.mFadingMarqueeEnabled = true;
        this.mGlobalActionsKeyTimeout = 500L;
        this.mHorizontalScrollFactor = 64.0f;
        this.mVerticalScrollFactor = 64.0f;
        this.mShowMenuShortcutsWhenKeyboardPresent = false;
        this.mScreenshotChordKeyTimeout = 0L;
        this.mMinScalingSpan = 0;
        this.mSmartSelectionInitializedTimeout = 200;
        this.mSmartSelectionInitializingTimeout = 500;
        this.mPreferKeepClearForFocusEnabled = false;
    }

    private ViewConfiguration(Context context) {
        float sizeAndDensity;
        boolean z;
        this.mConstructedWithContext = true;
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        float density = metrics.density;
        if (config.isLayoutSizeAtLeast(4)) {
            sizeAndDensity = 1.5f * density;
        } else {
            sizeAndDensity = density;
        }
        this.mEdgeSlop = (int) ((sizeAndDensity * 12.0f) + 0.5f);
        this.mFadingEdgeLength = (int) ((12.0f * sizeAndDensity) + 0.5f);
        this.mScrollbarSize = res.getDimensionPixelSize(R.dimen.config_scrollbarSize);
        this.mDoubleTapSlop = (int) ((100.0f * sizeAndDensity) + 0.5f);
        this.mWindowTouchSlop = (int) ((16.0f * sizeAndDensity) + 0.5f);
        TypedValue multiplierValue = new TypedValue();
        res.getValue(R.dimen.config_ambiguousGestureMultiplier, multiplierValue, true);
        this.mAmbiguousGestureMultiplier = Math.max(1.0f, multiplierValue.getFloat());
        Rect maxBounds = config.windowConfiguration.getMaxBounds();
        this.mMaximumDrawingCacheSize = maxBounds.width() * 4 * maxBounds.height();
        this.mOverscrollDistance = (int) ((0.0f * sizeAndDensity) + 0.5f);
        this.mOverflingDistance = (int) ((6.0f * sizeAndDensity) + 0.5f);
        if (!this.sHasPermanentMenuKeySet) {
            int configVal = res.getInteger(R.integer.config_overrideHasPermanentMenuKey);
            switch (configVal) {
                case 1:
                    this.sHasPermanentMenuKey = true;
                    this.sHasPermanentMenuKeySet = true;
                    break;
                case 2:
                    this.sHasPermanentMenuKey = false;
                    this.sHasPermanentMenuKeySet = true;
                    break;
                default:
                    IWindowManager wm = WindowManagerGlobal.getWindowManagerService();
                    try {
                        if (!wm.hasNavigationBar(context.getDisplayId())) {
                            z = true;
                        } else {
                            z = false;
                        }
                        this.sHasPermanentMenuKey = z;
                        this.sHasPermanentMenuKeySet = true;
                        break;
                    } catch (RemoteException e) {
                        this.sHasPermanentMenuKey = false;
                        break;
                    }
            }
        }
        this.mFadingMarqueeEnabled = res.getBoolean(R.bool.config_ui_enableFadingMarquee);
        this.mTouchSlop = res.getDimensionPixelSize(R.dimen.config_viewConfigurationTouchSlop);
        this.mHandwritingSlop = res.getDimensionPixelSize(R.dimen.config_viewConfigurationHandwritingSlop);
        this.mHoverSlop = res.getDimensionPixelSize(R.dimen.config_viewConfigurationHoverSlop);
        this.mMinScrollbarTouchTarget = res.getDimensionPixelSize(R.dimen.config_minScrollbarTouchTarget);
        this.mPagingTouchSlop = this.mTouchSlop * 2;
        this.mDoubleTapTouchSlop = this.mTouchSlop;
        this.mHandwritingGestureLineMargin = res.getDimensionPixelSize(17104906);
        this.mMinimumFlingVelocity = res.getDimensionPixelSize(R.dimen.config_viewMinFlingVelocity);
        this.mMaximumFlingVelocity = res.getDimensionPixelSize(R.dimen.config_viewMaxFlingVelocity);
        int configMinRotaryEncoderFlingVelocity = res.getDimensionPixelSize(R.dimen.config_viewMinRotaryEncoderFlingVelocity);
        int configMaxRotaryEncoderFlingVelocity = res.getDimensionPixelSize(R.dimen.config_viewMaxRotaryEncoderFlingVelocity);
        if (configMinRotaryEncoderFlingVelocity < 0 || configMaxRotaryEncoderFlingVelocity < 0) {
            this.mMinimumRotaryEncoderFlingVelocity = Integer.MAX_VALUE;
            this.mMaximumRotaryEncoderFlingVelocity = Integer.MIN_VALUE;
        } else {
            this.mMinimumRotaryEncoderFlingVelocity = configMinRotaryEncoderFlingVelocity;
            this.mMaximumRotaryEncoderFlingVelocity = configMaxRotaryEncoderFlingVelocity;
        }
        int configRotaryEncoderHapticScrollFeedbackTickIntervalPixels = res.getDimensionPixelSize(R.dimen.config_rotaryEncoderAxisScrollTickInterval);
        this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels = configRotaryEncoderHapticScrollFeedbackTickIntervalPixels > 0 ? configRotaryEncoderHapticScrollFeedbackTickIntervalPixels : Integer.MAX_VALUE;
        this.mRotaryEncoderHapticScrollFeedbackEnabled = res.getBoolean(R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled);
        this.mGlobalActionsKeyTimeout = res.getInteger(R.integer.config_globalActionsKeyTimeout);
        this.mHorizontalScrollFactor = res.getDimensionPixelSize(R.dimen.config_horizontalScrollFactor);
        this.mVerticalScrollFactor = res.getDimensionPixelSize(R.dimen.config_verticalScrollFactor);
        this.mShowMenuShortcutsWhenKeyboardPresent = res.getBoolean(R.bool.config_showMenuShortcutsWhenKeyboardPresent);
        this.mMinScalingSpan = res.getDimensionPixelSize(R.dimen.config_minScalingSpan);
        this.mScreenshotChordKeyTimeout = res.getInteger(R.integer.config_screenshotChordKeyTimeout);
        this.mSmartSelectionInitializedTimeout = res.getInteger(R.integer.config_smartSelectionInitializedTimeoutMillis);
        this.mSmartSelectionInitializingTimeout = res.getInteger(R.integer.config_smartSelectionInitializingTimeoutMillis);
        this.mPreferKeepClearForFocusEnabled = res.getBoolean(R.bool.config_preferKeepClearForFocus);
        this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig = res.getBoolean(R.bool.config_viewBasedRotaryEncoderHapticsEnabled);
    }

    public static ViewConfiguration get(Context context) {
        StrictMode.assertConfigurationContext(context, TAG);
        int density = getDisplayDensity(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenFactor = ((metrics.widthPixels / 10) << 21) | ((metrics.heightPixels / 10) << 10) | density;
        ViewConfiguration configuration = sConfigurations.get(screenFactor);
        if (configuration == null) {
            ViewConfiguration configuration2 = new ViewConfiguration(context);
            sConfigurations.put(screenFactor, configuration2);
            return configuration2;
        }
        return configuration;
    }

    public static void resetCacheForTesting() {
        sConfigurations.clear();
    }

    public static void setInstanceForTesting(Context context, ViewConfiguration instance) {
        sConfigurations.put(getDisplayDensity(context), instance);
    }

    @Deprecated
    public static int getScrollBarSize() {
        return 4;
    }

    public int getScaledScrollBarSize() {
        return this.mScrollbarSize;
    }

    public int getScaledMinScrollbarTouchTarget() {
        return this.mMinScrollbarTouchTarget;
    }

    public static int getScrollBarFadeDuration() {
        return 250;
    }

    public static int getScrollDefaultDelay() {
        return 300;
    }

    @Deprecated
    public static int getFadingEdgeLength() {
        return 12;
    }

    public int getScaledFadingEdgeLength() {
        return this.mFadingEdgeLength;
    }

    public static int getPressedStateDuration() {
        return 64;
    }

    public static int getLongPressTimeout() {
        return AppGlobals.getIntCoreSetting("long_press_timeout", 400);
    }

    public static int getMultiPressTimeout() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.MULTI_PRESS_TIMEOUT, 300);
    }

    public static int getKeyRepeatTimeout() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.KEY_REPEAT_TIMEOUT_MS, 400);
    }

    public static int getKeyRepeatDelay() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.KEY_REPEAT_DELAY_MS, 50);
    }

    public static int getTapTimeout() {
        return 100;
    }

    public static int getJumpTapTimeout() {
        return 500;
    }

    public static int getDoubleTapTimeout() {
        return 300;
    }

    public static int getDoubleTapMinTime() {
        return 40;
    }

    public static int getHoverTapTimeout() {
        return 150;
    }

    public static int getHoverTapSlop() {
        return 20;
    }

    @Deprecated
    public static int getEdgeSlop() {
        return 12;
    }

    public int getScaledEdgeSlop() {
        return this.mEdgeSlop;
    }

    @Deprecated
    public static int getTouchSlop() {
        return 8;
    }

    public int getScaledTouchSlop() {
        return this.mTouchSlop;
    }

    public int getScaledHandwritingSlop() {
        return this.mHandwritingSlop;
    }

    public int getScaledHoverSlop() {
        return this.mHoverSlop;
    }

    public int getScaledDoubleTapTouchSlop() {
        return this.mDoubleTapTouchSlop;
    }

    public int getScaledPagingTouchSlop() {
        return this.mPagingTouchSlop;
    }

    @Deprecated
    public static int getDoubleTapSlop() {
        return 100;
    }

    public int getScaledDoubleTapSlop() {
        return this.mDoubleTapSlop;
    }

    public int getScaledHandwritingGestureLineMargin() {
        return this.mHandwritingGestureLineMargin;
    }

    public static long getSendRecurringAccessibilityEventsInterval() {
        return SEND_RECURRING_ACCESSIBILITY_EVENTS_INTERVAL_MILLIS;
    }

    @Deprecated
    public static int getWindowTouchSlop() {
        return 16;
    }

    public int getScaledWindowTouchSlop() {
        return this.mWindowTouchSlop;
    }

    @Deprecated
    public static int getMinimumFlingVelocity() {
        return 50;
    }

    public int getScaledMinimumFlingVelocity() {
        return this.mMinimumFlingVelocity;
    }

    @Deprecated
    public static int getMaximumFlingVelocity() {
        return 8000;
    }

    public int getScaledMaximumFlingVelocity() {
        return this.mMaximumFlingVelocity;
    }

    public int getScaledScrollFactor() {
        return (int) this.mVerticalScrollFactor;
    }

    public float getScaledHorizontalScrollFactor() {
        return this.mHorizontalScrollFactor;
    }

    public float getScaledVerticalScrollFactor() {
        return this.mVerticalScrollFactor;
    }

    @Deprecated
    public static int getMaximumDrawingCacheSize() {
        return MAXIMUM_DRAWING_CACHE_SIZE;
    }

    public int getScaledMaximumDrawingCacheSize() {
        return this.mMaximumDrawingCacheSize;
    }

    public int getScaledOverscrollDistance() {
        return this.mOverscrollDistance;
    }

    public int getScaledOverflingDistance() {
        return this.mOverflingDistance;
    }

    public static long getZoomControlsTimeout() {
        return 3000L;
    }

    @Deprecated
    public static long getGlobalActionKeyTimeout() {
        return 500L;
    }

    public long getDeviceGlobalActionKeyTimeout() {
        return this.mGlobalActionsKeyTimeout;
    }

    public long getScreenshotChordKeyTimeout() {
        return this.mScreenshotChordKeyTimeout;
    }

    public long getAccessibilityShortcutKeyTimeout() {
        return 3000L;
    }

    public long getAccessibilityShortcutKeyTimeoutAfterConfirmation() {
        return 1000L;
    }

    public static float getScrollFriction() {
        return SCROLL_FRICTION;
    }

    public static long getDefaultActionModeHideDuration() {
        return ACTION_MODE_HIDE_DURATION_DEFAULT;
    }

    @Deprecated
    public static float getAmbiguousGestureMultiplier() {
        return 2.0f;
    }

    public float getScaledAmbiguousGestureMultiplier() {
        return this.mAmbiguousGestureMultiplier;
    }

    public boolean hasPermanentMenuKey() {
        return this.sHasPermanentMenuKey;
    }

    public int getScaledMinimumFlingVelocity(int inputDeviceId, int axis, int source) {
        if (isInputDeviceInfoValid(inputDeviceId, axis, source)) {
            return source == 4194304 ? this.mMinimumRotaryEncoderFlingVelocity : this.mMinimumFlingVelocity;
        }
        return Integer.MAX_VALUE;
    }

    public int getScaledMaximumFlingVelocity(int inputDeviceId, int axis, int source) {
        if (isInputDeviceInfoValid(inputDeviceId, axis, source)) {
            return source == 4194304 ? this.mMaximumRotaryEncoderFlingVelocity : this.mMaximumFlingVelocity;
        }
        return Integer.MIN_VALUE;
    }

    public boolean isHapticScrollFeedbackEnabled(int inputDeviceId, int axis, int source) {
        if (isInputDeviceInfoValid(inputDeviceId, axis, source) && source == 4194304 && axis == 26) {
            return this.mRotaryEncoderHapticScrollFeedbackEnabled;
        }
        return false;
    }

    public int getHapticScrollFeedbackTickInterval(int inputDeviceId, int axis, int source) {
        if (this.mRotaryEncoderHapticScrollFeedbackEnabled && isInputDeviceInfoValid(inputDeviceId, axis, source) && source == 4194304 && axis == 26) {
            return this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels;
        }
        return Integer.MAX_VALUE;
    }

    public boolean isViewBasedRotaryEncoderHapticScrollFeedbackEnabled() {
        return this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig && Flags.useViewBasedRotaryEncoderScrollHaptics();
    }

    private static boolean isInputDeviceInfoValid(int id, int axis, int source) {
        InputDevice device = InputManagerGlobal.getInstance().getInputDevice(id);
        return (device == null || device.getMotionRange(axis, source) == null) ? false : true;
    }

    public boolean shouldShowMenuShortcutsWhenKeyboardPresent() {
        return this.mShowMenuShortcutsWhenKeyboardPresent;
    }

    public int getScaledMinimumScalingSpan() {
        if (!this.mConstructedWithContext) {
            throw new IllegalStateException("Min scaling span cannot be determined when this method is called on a ViewConfiguration that was instantiated using a constructor with no Context parameter");
        }
        return this.mMinScalingSpan;
    }

    public boolean isFadingMarqueeEnabled() {
        return this.mFadingMarqueeEnabled;
    }

    public int getSmartSelectionInitializedTimeout() {
        return this.mSmartSelectionInitializedTimeout;
    }

    public int getSmartSelectionInitializingTimeout() {
        return this.mSmartSelectionInitializingTimeout;
    }

    public boolean isPreferKeepClearForFocusEnabled() {
        return this.mPreferKeepClearForFocusEnabled;
    }

    public static int getLongPressTooltipHideTimeout() {
        return 1500;
    }

    public static int getHoverTooltipShowTimeout() {
        return 500;
    }

    public static int getHoverTooltipHideTimeout() {
        return 15000;
    }

    public static int getHoverTooltipHideShortTimeout() {
        return 3000;
    }

    private static final int getDisplayDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * 100.0f);
    }
}

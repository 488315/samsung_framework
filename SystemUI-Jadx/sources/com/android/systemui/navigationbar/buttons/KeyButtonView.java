package com.android.systemui.navigationbar.buttons;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.input.InputManagerGlobal;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.DeviceType;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class KeyButtonView extends ImageView implements ButtonInterface {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioManager mAudioManager;
    public final Handler mBgHandler;
    public final AnonymousClass1 mCheckLongPress;
    public int mCode;
    public final int mContentDescriptionRes;
    public float mDarkIntensity;
    public long mDownTime;
    public boolean mGestureAborted;
    public boolean mGestureAbortedByA11yGesture;
    public boolean mHasOvalBg;
    public final InputManagerGlobal mInputManagerGlobal;
    boolean mLongClicked;
    public final MetricsLogger mMetricsLogger;
    public View.OnClickListener mOnClickListener;
    public final Paint mOvalBgPaint;
    public final OverviewProxyService mOverviewProxyService;
    public final boolean mPlaySounds;
    public final KeyButtonRipple mRipple;
    public KeyButtonDrawable mSamsungKeyButtonDrawable;
    public int mTouchDownX;
    public int mTouchDownY;
    public final UiEventLogger mUiEventLogger;
    public final VibratorHelper mVibratorHelper;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public enum NavBarButtonEvent implements UiEventLogger.UiEventEnum {
        NAVBAR_HOME_BUTTON_TAP(533),
        NAVBAR_BACK_BUTTON_TAP(534),
        NAVBAR_OVERVIEW_BUTTON_TAP(535),
        NAVBAR_IME_SWITCHER_BUTTON_TAP(923),
        NAVBAR_HOME_BUTTON_LONGPRESS(536),
        NAVBAR_BACK_BUTTON_LONGPRESS(537),
        NAVBAR_OVERVIEW_BUTTON_LONGPRESS(538),
        NONE(0);

        private final int mId;

        NavBarButtonEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public KeyButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void abortCurrentGesture() {
        Log.d("b/63783866", "KeyButtonView.abortCurrentGesture");
        if (this.mCode != 0) {
            sendEvent(1, 32);
        }
        setPressed(false);
        this.mRipple.mHandler.removeCallbacksAndMessages(null);
        this.mGestureAborted = true;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void abortCurrentGestureByA11yGesture(boolean z) {
        this.mGestureAbortedByA11yGesture = z;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        if (this.mHasOvalBg) {
            float min = Math.min(getWidth(), getHeight());
            canvas.drawOval(0.0f, 0.0f, min, min, this.mOvalBgPaint);
        }
        super.draw(canvas);
    }

    @Override // android.widget.ImageView
    public final Drawable getDrawable() {
        KeyButtonDrawable keyButtonDrawable;
        if (BasicRune.NAVBAR_ENABLED && (keyButtonDrawable = this.mSamsungKeyButtonDrawable) != null) {
            return keyButtonDrawable;
        }
        return super.getDrawable();
    }

    @Override // android.view.View
    public final boolean isClickable() {
        if (this.mCode == 0 && !super.isClickable()) {
            return false;
        }
        return true;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        if (!BasicRune.NAVBAR_ENABLED && (i = this.mContentDescriptionRes) != 0) {
            setContentDescription(((ImageView) this).mContext.getString(i));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCode != 0) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, null));
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, null));
            }
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        View.OnClickListener onClickListener;
        boolean shouldShowSwipeUpUI = this.mOverviewProxyService.shouldShowSwipeUpUI();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        }
        if (action != 0) {
            if (action != 1) {
                if (action != 2) {
                    if (action == 3) {
                        setPressed(false);
                        if (this.mCode != 0) {
                            sendEvent(1, 32);
                        }
                        removeCallbacks(this.mCheckLongPress);
                    }
                } else {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (!BasicRune.NAVBAR_ENABLED) {
                        Context context = getContext();
                        boolean z3 = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
                        float scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
                        if (Math.abs(x - this.mTouchDownX) > scaledTouchSlop || Math.abs(y - this.mTouchDownY) > scaledTouchSlop) {
                            setPressed(false);
                            removeCallbacks(this.mCheckLongPress);
                        }
                    }
                }
            } else {
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mGestureAbortedByA11yGesture) {
                    this.mGestureAbortedByA11yGesture = false;
                    if (getDisplay().getDisplayId() == 1) {
                        Log.d("KeyButtonView", "abortCurrentGesture by A11yGesture");
                        setPressed(false);
                        return false;
                    }
                }
                if (isPressed() && !this.mLongClicked) {
                    z = true;
                } else {
                    z = false;
                }
                setPressed(false);
                if (SystemClock.uptimeMillis() - this.mDownTime > 150) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (shouldShowSwipeUpUI) {
                    if (z) {
                        if (BasicRune.NAVBAR_ENABLED) {
                            if (this.mVibratorHelper.isSupportDCMotorHapticFeedback()) {
                                this.mVibratorHelper.vibrateButton();
                            } else {
                                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                            }
                        } else {
                            performHapticFeedback(1);
                        }
                        playSoundEffect(0);
                    }
                } else if (z2 && !this.mLongClicked && !BasicRune.NAVBAR_ENABLED) {
                    performHapticFeedback(8);
                }
                if (this.mCode != 0) {
                    if (z) {
                        sendEvent(1, 0);
                        sendAccessibilityEvent(1);
                    } else {
                        sendEvent(1, 32);
                    }
                } else if (z && (onClickListener = this.mOnClickListener) != null) {
                    onClickListener.onClick(this);
                    sendAccessibilityEvent(1);
                }
                removeCallbacks(this.mCheckLongPress);
            }
        } else {
            this.mDownTime = SystemClock.uptimeMillis();
            this.mLongClicked = false;
            setPressed(true);
            this.mTouchDownX = (int) motionEvent.getX();
            this.mTouchDownY = (int) motionEvent.getY();
            if (this.mCode != 0) {
                sendEvent(0, 0, this.mDownTime);
                if (BasicRune.NAVBAR_ENABLED && !shouldShowSwipeUpUI && this.mVibratorHelper.isSupportDCMotorHapticFeedback()) {
                    this.mVibratorHelper.vibrateButton();
                }
            } else if (BasicRune.NAVBAR_ENABLED) {
                if (!shouldShowSwipeUpUI) {
                    if (this.mVibratorHelper.isSupportDCMotorHapticFeedback()) {
                        this.mVibratorHelper.vibrateButton();
                    } else {
                        performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                    }
                }
            } else {
                performHapticFeedback(1);
            }
            if (!shouldShowSwipeUpUI) {
                playSoundEffect(0);
            }
            removeCallbacks(this.mCheckLongPress);
            postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
        }
        return true;
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (i == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, 0);
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (i == 32 && this.mCode != 0) {
            sendEvent(0, 128);
            sendEvent(1, 0);
            sendAccessibilityEvent(2);
            return true;
        }
        return super.performAccessibilityActionInternal(i, bundle);
    }

    @Override // android.view.View
    public final void playSoundEffect(int i) {
        if (!this.mPlaySounds) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED && i == 0) {
            i = 102;
        }
        this.mAudioManager.playSoundEffect(i, ActivityManager.getCurrentUser());
    }

    public final void sendEvent(int i, int i2) {
        sendEvent(i, i2, SystemClock.uptimeMillis());
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
        this.mRipple.setDarkIntensity(f);
    }

    @Override // android.widget.ImageView, com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setImageDrawable(Drawable drawable) {
        boolean z;
        KeyButtonRipple.Type type;
        LayerDrawable layerDrawable;
        if (BasicRune.NAVBAR_ENABLED) {
            KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) drawable;
            this.mSamsungKeyButtonDrawable = keyButtonDrawable;
            if (keyButtonDrawable != null && (layerDrawable = keyButtonDrawable.mLayerDrawable) != null) {
                super.setImageDrawable(layerDrawable);
            } else {
                super.setImageDrawable(drawable);
            }
        } else {
            super.setImageDrawable(drawable);
        }
        if (drawable == null) {
            return;
        }
        KeyButtonDrawable keyButtonDrawable2 = (KeyButtonDrawable) drawable;
        keyButtonDrawable2.setDarkIntensity(this.mDarkIntensity);
        Color color = keyButtonDrawable2.mState.mOvalBackgroundColor;
        boolean z2 = true;
        if (color != null) {
            z = true;
        } else {
            z = false;
        }
        this.mHasOvalBg = z;
        if (z) {
            this.mOvalBgPaint.setColor(color.toArgb());
        }
        KeyButtonRipple keyButtonRipple = this.mRipple;
        if (keyButtonDrawable2.mState.mOvalBackgroundColor == null) {
            z2 = false;
        }
        if (z2) {
            type = KeyButtonRipple.Type.OVAL;
        } else {
            type = KeyButtonRipple.Type.ROUNDED_RECT;
        }
        keyButtonRipple.mType = type;
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    public KeyButtonView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, InputManagerGlobal.getInstance(), new UiEventLoggerImpl());
        if (BasicRune.NAVBAR_ENABLED) {
            this.mVibratorHelper = (VibratorHelper) Dependency.get(VibratorHelper.class);
        }
        if (BasicRune.NAVBAR_PERFORMANCE_TUNING) {
            this.mBgHandler = (Handler) Dependency.get(Dependency.NAVBAR_BG_HANDLER);
        }
    }

    public final void sendEvent(int i, int i2, long j) {
        NavBarButtonEvent navBarButtonEvent;
        Handler handler;
        this.mMetricsLogger.write(new LogMaker(931).setType(4).setSubtype(this.mCode).addTaggedData(933, Integer.valueOf(i)).addTaggedData(932, Integer.valueOf(i2)));
        int i3 = i2 & 128;
        boolean z = i3 != 0;
        NavBarButtonEvent navBarButtonEvent2 = NavBarButtonEvent.NONE;
        if ((i != 1 || !this.mLongClicked) && ((i != 0 || z) && (i2 & 32) == 0 && (i2 & 256) == 0)) {
            int i4 = this.mCode;
            if (i4 != 3) {
                if (i4 != 4) {
                    if (i4 != 187) {
                        navBarButtonEvent = navBarButtonEvent2;
                    } else if (z) {
                        navBarButtonEvent = NavBarButtonEvent.NAVBAR_OVERVIEW_BUTTON_LONGPRESS;
                    } else {
                        navBarButtonEvent = NavBarButtonEvent.NAVBAR_OVERVIEW_BUTTON_TAP;
                    }
                } else if (z) {
                    navBarButtonEvent = NavBarButtonEvent.NAVBAR_BACK_BUTTON_LONGPRESS;
                } else {
                    navBarButtonEvent = NavBarButtonEvent.NAVBAR_BACK_BUTTON_TAP;
                }
            } else if (z) {
                navBarButtonEvent = NavBarButtonEvent.NAVBAR_HOME_BUTTON_LONGPRESS;
            } else {
                navBarButtonEvent = NavBarButtonEvent.NAVBAR_HOME_BUTTON_TAP;
            }
            if (navBarButtonEvent != navBarButtonEvent2) {
                this.mUiEventLogger.log(navBarButtonEvent);
            }
        }
        if (this.mCode == 4 && i2 != 128) {
            Log.i("KeyButtonView", "Back button event: " + KeyEvent.actionToString(i));
        }
        final KeyEvent keyEvent = new KeyEvent(this.mDownTime, j, i, this.mCode, i3 != 0 ? 1 : 0, 0, -1, 0, i2 | 8 | 64, 257);
        int displayId = getDisplay() != null ? getDisplay().getDisplayId() : -1;
        if (displayId != -1) {
            keyEvent.setDisplayId(displayId);
        }
        if (BasicRune.NAVBAR_PERFORMANCE_TUNING && (handler = this.mBgHandler) != null) {
            handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyButtonView keyButtonView = KeyButtonView.this;
                    KeyEvent keyEvent2 = keyEvent;
                    int i5 = KeyButtonView.$r8$clinit;
                    keyButtonView.getClass();
                    Log.i("KeyButtonView", "injectInputEvent - " + keyEvent2.getKeyCode());
                    keyButtonView.mInputManagerGlobal.injectInputEvent(keyEvent2, 0);
                }
            });
        } else {
            this.mInputManagerGlobal.injectInputEvent(keyEvent, 0);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.navigationbar.buttons.KeyButtonView$1] */
    public KeyButtonView(Context context, AttributeSet attributeSet, int i, InputManagerGlobal inputManagerGlobal, UiEventLogger uiEventLogger) {
        super(context, attributeSet);
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mOvalBgPaint = new Paint(3);
        this.mHasOvalBg = false;
        this.mCheckLongPress = new Runnable() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView.1
            @Override // java.lang.Runnable
            public final void run() {
                if (KeyButtonView.this.isPressed()) {
                    if (KeyButtonView.this.isLongClickable()) {
                        KeyButtonView.this.performLongClick();
                        KeyButtonView.this.mLongClicked = true;
                        return;
                    }
                    KeyButtonView keyButtonView = KeyButtonView.this;
                    if (keyButtonView.mCode != 0) {
                        keyButtonView.sendEvent(0, 128);
                        KeyButtonView.this.sendAccessibilityEvent(2);
                    }
                    KeyButtonView.this.mLongClicked = true;
                }
            }
        };
        this.mUiEventLogger = uiEventLogger;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyButtonView, i, 0);
        this.mCode = obtainStyledAttributes.getInteger(1, 0);
        this.mPlaySounds = obtainStyledAttributes.getBoolean(2, true);
        TypedValue typedValue = new TypedValue();
        if (obtainStyledAttributes.getValue(0, typedValue)) {
            this.mContentDescriptionRes = typedValue.resourceId;
        }
        obtainStyledAttributes.recycle();
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (BasicRune.NAVBAR_STABLE_LAYOUT) {
            this.mRipple = new KeyButtonRipple(context, this, R.dimen.key_button_ripple_max_width, (DeviceType.isTablet() || context.getDisplayId() == 1 || !context.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider)) ? 1.0f : 1.35f);
        } else {
            this.mRipple = new KeyButtonRipple(context, this, R.dimen.key_button_ripple_max_width);
        }
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mInputManagerGlobal = inputManagerGlobal;
        setBackground(this.mRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setVertical() {
    }
}

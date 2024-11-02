package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationGestureDetector;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MagnificationModeSwitch implements MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    static final int DEFAULT_FADE_OUT_ANIMATION_DELAY_MS = 5000;
    static final long FADING_ANIMATION_DURATION_MS = 300;
    public final AccessibilityManager mAccessibilityManager;
    public final ClickListener mClickListener;
    public final Configuration mConfiguration;
    public final Context mContext;
    final Rect mDraggableWindowBounds;
    public final MagnificationModeSwitch$$ExternalSyntheticLambda1 mFadeInAnimationTask;
    public final MagnificationModeSwitch$$ExternalSyntheticLambda1 mFadeOutAnimationTask;
    public final MagnificationGestureDetector mGestureDetector;
    public final MagnificationModeSwitch$$ExternalSyntheticLambda1 mHideTootipRunnable;
    public final ImageView mImageView;
    public final Rect mImeInsetsRect;
    boolean mIsFadeOutAnimating;
    public boolean mIsVisible;
    public int mMagnificationMode;
    public final WindowManager.LayoutParams mParams;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public boolean mSingleTapDetected;
    public boolean mToLeftScreenEdge;
    public final TooltipPopup mTooltipPopup;
    public int mUiTimeout;
    public final MagnificationModeSwitch$$ExternalSyntheticLambda1 mWindowInsetChangeRunnable;
    public final WindowManager mWindowManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface ClickListener {
        void onClick(int i);
    }

    public MagnificationModeSwitch(Context context, ImageView imageView, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, ClickListener clickListener) {
        this.mIsFadeOutAnimating = false;
        this.mMagnificationMode = 0;
        this.mDraggableWindowBounds = new Rect();
        this.mIsVisible = false;
        this.mSingleTapDetected = false;
        this.mToLeftScreenEdge = false;
        this.mImeInsetsRect = new Rect();
        this.mHideTootipRunnable = new MagnificationModeSwitch$$ExternalSyntheticLambda1(this, 0);
        this.mContext = context;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mClickListener = clickListener;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, dimensionPixelSize, 2039, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.accessibilityTitle = context.getString(android.R.string.capital_on);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(WindowInsets.Type.ime());
        layoutParams.receiveInsetsIgnoringZOrder = true;
        this.mParams = layoutParams;
        context.setTheme(android.R.style.Theme.DeviceDefault.DayNight);
        this.mImageView = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                MagnificationModeSwitch magnificationModeSwitch = MagnificationModeSwitch.this;
                if (!magnificationModeSwitch.mIsVisible) {
                    return false;
                }
                return magnificationModeSwitch.mGestureDetector.onTouch(view, motionEvent);
            }
        });
        imageView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                int i;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                MagnificationModeSwitch magnificationModeSwitch = MagnificationModeSwitch.this;
                if (magnificationModeSwitch.mMagnificationMode == 2) {
                    i = R.string.magnification_mode_switch_state_window;
                } else {
                    i = R.string.accessibility_magnification_window_magnification_opened;
                }
                accessibilityNodeInfo.setStateDescription(magnificationModeSwitch.mContext.getResources().getString(i));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), MagnificationModeSwitch.this.mContext.getResources().getString(R.string.magnification_open_settings_click_label)));
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_up, MagnificationModeSwitch.this.mContext.getString(R.string.accessibility_control_move_up)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_down, MagnificationModeSwitch.this.mContext.getString(R.string.accessibility_control_move_down)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_left, MagnificationModeSwitch.this.mContext.getString(R.string.accessibility_control_move_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_right, MagnificationModeSwitch.this.mContext.getString(R.string.accessibility_control_move_right)));
            }

            /* JADX WARN: Removed duplicated region for block: B:6:0x0062 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0063  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean performAccessibilityAction(android.view.View r5, int r6, android.os.Bundle r7) {
                /*
                    r4 = this;
                    com.android.systemui.accessibility.MagnificationModeSwitch r0 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    android.view.WindowManager r0 = r0.mWindowManager
                    android.view.WindowMetrics r0 = r0.getCurrentWindowMetrics()
                    android.graphics.Rect r0 = r0.getBounds()
                    android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r1 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK
                    int r1 = r1.getId()
                    r2 = 1
                    if (r6 != r1) goto L1b
                    com.android.systemui.accessibility.MagnificationModeSwitch r0 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    r0.handleSingleTap()
                    goto L5d
                L1b:
                    r1 = 2131361833(0x7f0a0029, float:1.834343E38)
                    r3 = 0
                    if (r6 != r1) goto L2d
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.height()
                    int r0 = -r0
                    float r0 = (float) r0
                    r1.moveButton(r3, r0)
                    goto L5d
                L2d:
                    r1 = 2131361830(0x7f0a0026, float:1.8343423E38)
                    if (r6 != r1) goto L3d
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.height()
                    float r0 = (float) r0
                    r1.moveButton(r3, r0)
                    goto L5d
                L3d:
                    r1 = 2131361831(0x7f0a0027, float:1.8343425E38)
                    if (r6 != r1) goto L4e
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.width()
                    int r0 = -r0
                    float r0 = (float) r0
                    r1.moveButton(r0, r3)
                    goto L5d
                L4e:
                    r1 = 2131361832(0x7f0a0028, float:1.8343427E38)
                    if (r6 != r1) goto L5f
                    com.android.systemui.accessibility.MagnificationModeSwitch r1 = com.android.systemui.accessibility.MagnificationModeSwitch.this
                    int r0 = r0.width()
                    float r0 = (float) r0
                    r1.moveButton(r0, r3)
                L5d:
                    r0 = r2
                    goto L60
                L5f:
                    r0 = 0
                L60:
                    if (r0 == 0) goto L63
                    return r2
                L63:
                    boolean r4 = super.performAccessibilityAction(r5, r6, r7)
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.MagnificationModeSwitch.AnonymousClass1.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
            }
        });
        this.mWindowInsetChangeRunnable = new MagnificationModeSwitch$$ExternalSyntheticLambda1(this, 1);
        imageView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda3
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                MagnificationModeSwitch magnificationModeSwitch = MagnificationModeSwitch.this;
                if (!magnificationModeSwitch.mImageView.getHandler().hasCallbacks(magnificationModeSwitch.mWindowInsetChangeRunnable)) {
                    magnificationModeSwitch.mImageView.getHandler().post(magnificationModeSwitch.mWindowInsetChangeRunnable);
                }
                return view.onApplyWindowInsets(windowInsets);
            }
        });
        this.mFadeInAnimationTask = new MagnificationModeSwitch$$ExternalSyntheticLambda1(this, 2);
        this.mFadeOutAnimationTask = new MagnificationModeSwitch$$ExternalSyntheticLambda1(this, 3);
        this.mGestureDetector = new MagnificationGestureDetector(context, context.getMainThreadHandler(), this);
        this.mTooltipPopup = new TooltipPopup(context);
    }

    public static int getIconResId(int i) {
        return R.drawable.ic_magnification_button_window;
    }

    public final void applyResourcesValues(int i) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(this.mContext.getColor(R.color.accessibility_full_magnifier_button_icon_color), PorterDuff.Mode.SRC_ATOP);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_mode_switch_padding);
        this.mMagnificationMode = i;
        this.mImageView.setImageResource(getIconResId(i));
        this.mImageView.setColorFilter(porterDuffColorFilter);
        this.mImageView.setBackgroundColor(this.mContext.getColor(R.color.accessibility_full_magnifier_button_background_color));
        this.mImageView.setBackgroundResource(R.drawable.accessibility_magnifier_btn_bg);
        this.mImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }

    public final Rect getDraggableWindowBounds() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_switch_button_margin);
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        Rect rect = new Rect(currentWindowMetrics.getBounds());
        rect.offsetTo(0, 0);
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        rect.inset(this.mImeInsetsRect);
        if (inputMethodManager != null && inputMethodManager.semIsInputMethodShown()) {
            rect.inset(0, 0, this.mParams.width, 0);
        } else {
            WindowManager.LayoutParams layoutParams = this.mParams;
            rect.inset(0, 0, layoutParams.width, layoutParams.height);
        }
        rect.inset(insetsIgnoringVisibility);
        rect.inset(dimensionPixelSize, dimensionPixelSize);
        return rect;
    }

    public final void handleSingleTap() {
        removeButton();
        this.mClickListener.onClick(this.mContext.getDisplayId());
        this.mImageView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
        A11yLogger.insertLog(this.mContext, "A11Y3188");
    }

    public final void moveButton(final float f, final float f2) {
        this.mSfVsyncFrameProvider.postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                MagnificationModeSwitch magnificationModeSwitch = MagnificationModeSwitch.this;
                float f3 = f;
                float f4 = f2;
                WindowManager.LayoutParams layoutParams = magnificationModeSwitch.mParams;
                layoutParams.x = (int) (layoutParams.x + f3);
                layoutParams.y = (int) (layoutParams.y + f4);
                magnificationModeSwitch.updateButtonViewLayoutIfNeeded();
            }
        });
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if (diff != 0) {
            if ((diff & 512) != 0) {
                applyResourcesValues(this.mMagnificationMode);
            }
            if ((diff & 1152) != 0) {
                removeButton();
                return;
            }
            if ((diff & 4096) != 0) {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_switch_button_size);
                WindowManager.LayoutParams layoutParams = this.mParams;
                layoutParams.height = dimensionPixelSize;
                layoutParams.width = dimensionPixelSize;
                if (this.mIsVisible) {
                    stickToScreenEdge(this.mToLeftScreenEdge);
                    removeButton();
                    showButton(this.mMagnificationMode, false);
                    return;
                }
                return;
            }
            if ((diff & 4) != 0) {
                this.mParams.accessibilityTitle = this.mContext.getString(android.R.string.capital_on);
                if (this.mIsVisible) {
                    this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
                }
            }
        }
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onDrag(View view, float f, float f2) {
        moveButton(f, f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onFinish() {
        boolean z;
        if (this.mIsVisible) {
            if (this.mParams.x < this.mWindowManager.getCurrentWindowMetrics().getBounds().width() / 2) {
                z = true;
            } else {
                z = false;
            }
            this.mToLeftScreenEdge = z;
            stickToScreenEdge(z);
        }
        if (!this.mSingleTapDetected) {
            showButton(this.mMagnificationMode, true);
        }
        this.mSingleTapDetected = false;
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onLongPressed(View view) {
        TooltipPopup tooltipPopup = this.mTooltipPopup;
        if (tooltipPopup != null) {
            tooltipPopup.show(view, this.mImageView.getTooltipPositionX(), this.mImageView.getTooltipPositionY(), false, this.mContext.getString(R.string.accessibility_magnification_window_magnification_opened));
            this.mImageView.removeCallbacks(this.mHideTootipRunnable);
            this.mImageView.postDelayed(this.mHideTootipRunnable, 1500L);
        }
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onSingleTap(View view) {
        this.mSingleTapDetected = true;
        handleSingleTap();
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onStart() {
        stopFadeOutAnimation();
    }

    public final void removeButton() {
        if (!this.mIsVisible) {
            return;
        }
        this.mImageView.removeCallbacks(this.mFadeInAnimationTask);
        this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
        this.mImageView.animate().cancel();
        this.mIsFadeOutAnimating = false;
        this.mImageView.setAlpha(0.0f);
        this.mWindowManager.removeView(this.mImageView);
        this.mContext.unregisterComponentCallbacks(this);
        this.mIsVisible = false;
    }

    public final void showButton(int i, boolean z) {
        this.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_mode_switch_padding);
        if (i != 1) {
            return;
        }
        if (this.mMagnificationMode != i) {
            applyResourcesValues(i);
        }
        if (!this.mIsVisible) {
            onConfigurationChanged(this.mContext.getResources().getConfiguration());
            this.mContext.registerComponentCallbacks(this);
            if (z) {
                this.mDraggableWindowBounds.set(getDraggableWindowBounds());
                WindowManager.LayoutParams layoutParams = this.mParams;
                Rect rect = this.mDraggableWindowBounds;
                layoutParams.x = rect.right;
                layoutParams.y = rect.bottom;
                this.mToLeftScreenEdge = false;
            }
            this.mWindowManager.addView(this.mImageView, this.mParams);
            this.mImageView.post(new MagnificationModeSwitch$$ExternalSyntheticLambda1(this, 4));
            this.mIsVisible = true;
            this.mImageView.postOnAnimation(this.mFadeInAnimationTask);
            this.mUiTimeout = this.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 5);
        }
        stopFadeOutAnimation();
        this.mImageView.postOnAnimationDelayed(this.mFadeOutAnimationTask, this.mUiTimeout);
    }

    public final void stickToScreenEdge(boolean z) {
        int i;
        WindowManager.LayoutParams layoutParams = this.mParams;
        if (z) {
            i = this.mDraggableWindowBounds.left;
        } else {
            i = this.mDraggableWindowBounds.right;
        }
        layoutParams.x = i;
        updateButtonViewLayoutIfNeeded();
    }

    public final void stopFadeOutAnimation() {
        this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
        if (this.mIsFadeOutAnimating) {
            this.mImageView.animate().cancel();
            this.mImageView.setAlpha(1.0f);
            this.mIsFadeOutAnimating = false;
        }
    }

    public final void updateButtonViewLayoutIfNeeded() {
        if (this.mIsVisible) {
            WindowManager.LayoutParams layoutParams = this.mParams;
            int i = layoutParams.x;
            Rect rect = this.mDraggableWindowBounds;
            layoutParams.x = MathUtils.constrain(i, rect.left, rect.right);
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            int i2 = layoutParams2.y;
            Rect rect2 = this.mDraggableWindowBounds;
            layoutParams2.y = MathUtils.constrain(i2, rect2.top, rect2.bottom);
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MagnificationModeSwitch(android.content.Context r3, com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener r4) {
        /*
            r2 = this;
            android.widget.ImageView r0 = new android.widget.ImageView
            r0.<init>(r3)
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER
            r0.setScaleType(r1)
            r1 = 1
            r0.setClickable(r1)
            r0.setFocusable(r1)
            r1 = 0
            r0.setAlpha(r1)
            com.android.internal.graphics.SfVsyncFrameCallbackProvider r1 = new com.android.internal.graphics.SfVsyncFrameCallbackProvider
            r1.<init>()
            r2.<init>(r3, r0, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.MagnificationModeSwitch.<init>(android.content.Context, com.android.systemui.accessibility.MagnificationModeSwitch$ClickListener):void");
    }
}

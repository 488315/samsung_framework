package com.android.wm.shell.onehanded;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.onehanded.OneHandedAnimationController;
import com.android.wm.shell.onehanded.OneHandedState;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class OneHandedTutorialHandler implements OneHandedTransitionCallback, OneHandedState.OnStateChangedListener, OneHandedAnimationCallback {
    public final int mAlphaAnimationDurationMs;
    public ValueAnimator mAlphaAnimator;
    public float mAlphaTransitionStart;
    public final BackgroundWindowManager mBackgroundWindowManager;
    public final Context mContext;
    public int mCurrentState;
    public Rect mDisplayBounds;
    public ViewGroup mTargetViewContainer;
    public int mTutorialAreaHeight;
    public final float mTutorialHeightRatio;
    public View mTutorialView;
    public final WindowManager mWindowManager;

    public OneHandedTutorialHandler(Context context, OneHandedSettingsUtil oneHandedSettingsUtil, WindowManager windowManager, BackgroundWindowManager backgroundWindowManager) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mBackgroundWindowManager = backgroundWindowManager;
        oneHandedSettingsUtil.getClass();
        this.mTutorialHeightRatio = context.getResources().getFraction(R.fraction.config_one_handed_offset, 1, 1);
        this.mAlphaAnimationDurationMs = context.getResources().getInteger(R.integer.config_one_handed_translate_animation_duration);
    }

    public final void checkTransitionEnd() {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null) {
            if (valueAnimator.isRunning() || this.mAlphaAnimator.isStarted()) {
                this.mAlphaAnimator.end();
                this.mAlphaAnimator.removeAllUpdateListeners();
                this.mAlphaAnimator = null;
            }
        }
    }

    public void createViewAndAttachToWindow(Context context) {
        float f;
        if (isAttached()) {
            return;
        }
        this.mTutorialView = LayoutInflater.from(context).inflate(R.layout.one_handed_tutorial, (ViewGroup) null);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mTargetViewContainer = frameLayout;
        frameLayout.setClipChildren(false);
        ViewGroup viewGroup = this.mTargetViewContainer;
        if (this.mCurrentState == 2) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        viewGroup.setAlpha(f);
        this.mTargetViewContainer.addView(this.mTutorialView);
        this.mTargetViewContainer.setLayerType(2, null);
        WindowManager windowManager = this.mWindowManager;
        try {
            windowManager.addView(this.mTargetViewContainer, getTutorialTargetLayoutParams());
            this.mBackgroundWindowManager.showBackgroundLayer();
        } catch (IllegalStateException unused) {
            windowManager.updateViewLayout(this.mTargetViewContainer, getTutorialTargetLayoutParams());
        }
    }

    public final WindowManager.LayoutParams getTutorialTargetLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(this.mDisplayBounds.width(), this.mTutorialAreaHeight, 0, 0, 2024, 264, -3);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("one-handed-tutorial-overlay");
        return layoutParams;
    }

    public boolean isAttached() {
        ViewGroup viewGroup = this.mTargetViewContainer;
        if (viewGroup != null && viewGroup.isAttachedToWindow()) {
            return true;
        }
        return false;
    }

    @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
    public final void onAnimationUpdate(float f) {
        if (!isAttached()) {
            return;
        }
        if (f < this.mAlphaTransitionStart) {
            checkTransitionEnd();
            return;
        }
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null && !valueAnimator.isStarted() && !this.mAlphaAnimator.isRunning()) {
            this.mAlphaAnimator.start();
        }
    }

    @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
    public final void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStartFinished(Rect rect) {
        BackgroundWindowManager backgroundWindowManager;
        ViewGroup viewGroup = this.mTargetViewContainer;
        if (viewGroup != null && (backgroundWindowManager = this.mBackgroundWindowManager) != null) {
            viewGroup.setBackgroundColor(backgroundWindowManager.getThemeColorForBackground());
        }
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStopFinished(Rect rect) {
        removeBackgroundSurface();
    }

    public void removeBackgroundSurface() {
        BackgroundWindowManager backgroundWindowManager = this.mBackgroundWindowManager;
        if (backgroundWindowManager.mBackgroundView != null) {
            backgroundWindowManager.mBackgroundView = null;
        }
        SurfaceControlViewHost surfaceControlViewHost = backgroundWindowManager.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            backgroundWindowManager.mViewHost = null;
        }
        if (backgroundWindowManager.mLeash != null) {
            backgroundWindowManager.mTransactionFactory.getClass();
            new SurfaceControl.Transaction().remove(backgroundWindowManager.mLeash).apply();
            backgroundWindowManager.mLeash = null;
        }
    }

    public void removeTutorialFromWindowManager() {
        if (!isAttached()) {
            return;
        }
        this.mTargetViewContainer.setLayerType(0, null);
        this.mWindowManager.removeViewImmediate(this.mTargetViewContainer);
        this.mTargetViewContainer = null;
    }

    public final void setupAlphaTransition(boolean z) {
        float f;
        float f2 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (z) {
            f2 = 1.0f;
        }
        int i = this.mAlphaAnimationDurationMs;
        if (!z) {
            i = Math.round((1.0f - this.mTutorialHeightRatio) * i);
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        this.mAlphaAnimator = ofFloat;
        ofFloat.setInterpolator(new LinearInterpolator());
        this.mAlphaAnimator.setDuration(i);
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.onehanded.OneHandedTutorialHandler$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                OneHandedTutorialHandler.this.mTargetViewContainer.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
    }

    public final void updateThemeColor() {
        if (this.mTutorialView == null) {
            return;
        }
        TypedArray obtainStyledAttributes = new ContextThemeWrapper(this.mTutorialView.getContext(), android.R.style.Theme.DeviceDefault.DayNight).obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary, android.R.attr.textColorSecondary});
        int color = obtainStyledAttributes.getColor(0, 0);
        int color2 = obtainStyledAttributes.getColor(1, 0);
        obtainStyledAttributes.recycle();
        ((ImageView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_image)).setImageTintList(ColorStateList.valueOf(color));
        TextView textView = (TextView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_title);
        TextView textView2 = (TextView) this.mTutorialView.findViewById(R.id.one_handed_tutorial_description);
        textView.setTextColor(color);
        textView2.setTextColor(color2);
    }
}

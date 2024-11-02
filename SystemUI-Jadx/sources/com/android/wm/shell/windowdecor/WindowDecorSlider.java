package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.SeekBar;
import com.android.systemui.R;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.android.util.SemViewUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class WindowDecorSlider extends SeekBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimatable;
    public View mButtonContainer;
    public boolean mControllable;
    public final WindowDecorSlider$$ExternalSyntheticLambda0 mSetControlDisabledRunnable;
    public View mSliderContainer;
    public final VisibilityAnimListener mVisAnimListener;
    public AnimatorSet mVisibilityAnim;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class VisibilityAnimListener implements Animator.AnimatorListener {
        public boolean mCanceled;
        public boolean mIsTalkbackEnabled;

        public /* synthetic */ VisibilityAnimListener(WindowDecorSlider windowDecorSlider, int i) {
            this();
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            WindowDecorSlider windowDecorSlider = WindowDecorSlider.this;
            if (windowDecorSlider.mControllable) {
                windowDecorSlider.mButtonContainer.setVisibility(8);
                WindowDecorSlider.this.mSliderContainer.setVisibility(0);
                if (!this.mIsTalkbackEnabled) {
                    WindowDecorSlider windowDecorSlider2 = WindowDecorSlider.this;
                    windowDecorSlider2.mSliderContainer.postDelayed(windowDecorSlider2.mSetControlDisabledRunnable, 3000L);
                    return;
                }
                return;
            }
            windowDecorSlider.mButtonContainer.setVisibility(0);
            WindowDecorSlider.this.mSliderContainer.setVisibility(8);
            WindowDecorSlider windowDecorSlider3 = WindowDecorSlider.this;
            windowDecorSlider3.mSliderContainer.removeCallbacks(windowDecorSlider3.mSetControlDisabledRunnable);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            WindowDecorSlider windowDecorSlider = WindowDecorSlider.this;
            if (windowDecorSlider.mControllable) {
                windowDecorSlider.mSliderContainer.setVisibility(0);
                this.mIsTalkbackEnabled = SemViewUtils.isTalkbackEnabled(WindowDecorSlider.this.mSliderContainer.getContext());
            } else {
                windowDecorSlider.mButtonContainer.setVisibility(0);
            }
            this.mCanceled = false;
        }

        private VisibilityAnimListener() {
            this.mCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.wm.shell.windowdecor.WindowDecorSlider$$ExternalSyntheticLambda0] */
    public WindowDecorSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 2132019203);
        int i;
        int i2;
        this.mVisAnimListener = new VisibilityAnimListener(this, 0);
        this.mSetControlDisabledRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowDecorSlider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WindowDecorSlider windowDecorSlider = WindowDecorSlider.this;
                int i3 = WindowDecorSlider.$r8$clinit;
                windowDecorSlider.setControllable(false);
            }
        };
        this.mAnimatable = false;
        setMax(60);
        setBackground(context.getDrawable(R.drawable.decor_seekbar_background));
        semSetMode(5);
        Resources resources = context.getResources();
        int i3 = Settings.System.getInt(((SeekBar) this).mContext.getContentResolver(), "wallpapertheme_state", 0) == 1 ? 1 : 0;
        if (i3 != 0) {
            i = 17171340;
        } else {
            i = 17171339;
        }
        ColorStateList colorStateList = resources.getColorStateList(i, null);
        setThumbTintList(colorStateList);
        setProgressTintList(colorStateList);
        if (i3 != 0) {
            i2 = 17171338;
        } else {
            i2 = 17171337;
        }
        setProgressBackgroundTintList(resources.getColorStateList(i2, null));
        setContentDescription(context.getString(R.string.sec_decor_button_text_opacity));
    }

    public final void onStartTrackingTouch() {
        this.mSliderContainer.removeCallbacks(this.mSetControlDisabledRunnable);
    }

    public final void onStopTrackingTouch() {
        this.mSliderContainer.postDelayed(this.mSetControlDisabledRunnable, 3000L);
    }

    public final void setControllable(boolean z) {
        float f;
        if (this.mAnimatable && this.mControllable != z) {
            this.mControllable = z;
            AnimatorSet animatorSet = this.mVisibilityAnim;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.mVisibilityAnim = animatorSet2;
            VisibilityAnimListener visibilityAnimListener = this.mVisAnimListener;
            visibilityAnimListener.getClass();
            animatorSet2.addListener(visibilityAnimListener);
            View view = this.mSliderContainer;
            Property property = View.ALPHA;
            float[] fArr = new float[1];
            float f2 = 1.0f;
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            fArr[0] = f;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr);
            ofFloat.setDuration(150L);
            ofFloat.setInterpolator(InterpolatorUtils.SINE_IN_OUT_80);
            View view2 = this.mButtonContainer;
            Property property2 = View.ALPHA;
            float[] fArr2 = new float[1];
            if (z) {
                f2 = 0.0f;
            }
            fArr2[0] = f2;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) property2, fArr2);
            ofFloat2.setDuration(150L);
            if (z) {
                this.mSliderContainer.setAlpha(0.0f);
            } else {
                this.mButtonContainer.setAlpha(0.0f);
            }
            this.mVisibilityAnim.play(ofFloat).with(ofFloat2);
            this.mVisibilityAnim.start();
        }
    }
}

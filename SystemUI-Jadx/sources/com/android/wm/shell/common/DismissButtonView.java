package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.draganddrop.DragAndDropUtil;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class DismissButtonView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAccessibilityTextResId;
    public float mCurrentFontScale;
    public final Rect mDismissArea;
    public int mDismissType;
    public float mElevation;
    public Animation mEnterAnimation;
    public boolean mFocusChangeHapticDisabled;
    public Runnable mHideAnimationEnd;
    public ImageView mIconView;
    public Animation mInsideHideAnimation;
    public boolean mIsEnterDismissButton;
    public boolean mIsNightModeOn;
    public Animation mOutsideHideAnimation;
    public Interpolator mSineOut60;
    public TextView mTextView;
    public boolean mVisible;

    public DismissButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDismissType = 0;
        this.mDismissArea = new Rect();
        this.mAccessibilityTextResId = VideoPlayer.MEDIA_ERROR_SYSTEM;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        if ((configuration.uiMode & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (this.mIsNightModeOn != z) {
            this.mIsNightModeOn = z;
            updateNightModeUI();
        }
        float f = this.mCurrentFontScale;
        float f2 = configuration.fontScale;
        if (f != f2) {
            this.mCurrentFontScale = f2;
            this.mTextView.setTextSize(0, DragAndDropUtil.calculateFontSizeWithScale(getResources().getDimension(R.dimen.dismiss_button_text_size), this.mCurrentFontScale));
        }
        setDismissType(this.mDismissType);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        boolean z;
        super.onFinishInflate();
        this.mSineOut60 = AnimationUtils.loadInterpolator(getContext(), R.interpolator.sine_in_out_60);
        this.mTextView = (TextView) findViewById(R.id.dismiss_button_text);
        this.mIconView = (ImageView) findViewById(R.id.dismiss_button_icon);
        this.mElevation = getResources().getDimension(R.dimen.dismiss_elevation);
        this.mCurrentFontScale = this.mTextView.getResources().getConfiguration().fontScale;
        if ((getResources().getConfiguration().uiMode & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsNightModeOn = z;
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_button_show);
        this.mEnterAnimation = loadAnimation;
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DismissButtonView.1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                int i = DismissButtonView.$r8$clinit;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onAnimationEnd, mVisible="), DismissButtonView.this.mVisible, "DismissButtonView");
                DismissButtonView dismissButtonView = DismissButtonView.this;
                if (!dismissButtonView.mVisible) {
                    return;
                }
                dismissButtonView.getGlobalVisibleRect(dismissButtonView.mDismissArea);
                DismissButtonView.this.setVisibility(0);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        this.mInsideHideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.entered_dismiss_button_hide);
        this.mOutsideHideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_button_hide);
        this.mTextView.setTextSize(0, DragAndDropUtil.calculateFontSizeWithScale(getResources().getDimension(R.dimen.dismiss_button_text_size), this.mCurrentFontScale));
        updateNightModeUI();
    }

    public final void setDismissType(int i) {
        this.mDismissType = i;
        if (i != 1 && i != 4) {
            if (i == 2 || i == 3) {
                this.mTextView.setText(getResources().getString(R.string.dnd_remove));
                return;
            }
            return;
        }
        this.mTextView.setText(getResources().getString(R.string.dnd_cancel));
        this.mAccessibilityTextResId = R.string.accessibility_drop_now_to_cancel;
    }

    public final void updateDismissButtonState(boolean z) {
        if (this.mIsEnterDismissButton != z) {
            this.mIsEnterDismissButton = z;
            if (CoreRune.SAFE_DEBUG) {
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(3, RowView$$ExternalSyntheticOutline0.m("updateEnterDismissButtonState: enter=", z, ", Callers="), "DismissButtonView");
            }
        }
    }

    public final void updateNightModeUI() {
        setBackgroundResource(0);
        setBackgroundResource(R.drawable.dismiss_button_bg);
        this.mTextView.setTextColor(getResources().getColor(R.color.dismiss_button_text_color));
        this.mIconView.setBackgroundTintList(getResources().getColorStateList(R.color.dismiss_button_icon_color));
    }

    public final void updateResources(boolean z) {
        int i;
        int i2;
        int i3;
        float f = this.mElevation;
        if (z) {
            f *= 1.15f;
        }
        setElevation(f);
        if (z) {
            i = R.drawable.dismiss_button_bg_over;
        } else {
            i = R.drawable.dismiss_button_bg;
        }
        setBackgroundResource(i);
        TextView textView = this.mTextView;
        Resources resources = getResources();
        if (z) {
            i2 = R.color.dismiss_button_text_color_focused;
        } else {
            i2 = R.color.dismiss_button_text_color;
        }
        textView.setTextColor(resources.getColor(i2));
        ImageView imageView = this.mIconView;
        Resources resources2 = getResources();
        if (z) {
            i3 = R.color.dismiss_button_icon_color_focused;
        } else {
            i3 = R.color.dismiss_button_icon_color;
        }
        imageView.setBackgroundTintList(resources2.getColorStateList(i3));
    }

    public final void updateView(Rect rect) {
        Rect rect2 = this.mDismissArea;
        if (rect2 == null || rect2.isEmpty()) {
            getGlobalVisibleRect(this.mDismissArea);
        }
        updateView(isShown() && Rect.intersects(this.mDismissArea, rect), true);
    }

    public final void updateView(final boolean z, boolean z2) {
        if (this.mIsEnterDismissButton == z) {
            return;
        }
        if (z2) {
            animate().cancel();
            animate().scaleX(z ? 1.15f : 1.0f).scaleY(z ? 1.15f : 1.0f).alpha(z ? 0.8f : 1.0f).setInterpolator(this.mSineOut60).setDuration(250L).withStartAction(new Runnable() { // from class: com.android.wm.shell.common.DismissButtonView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DismissButtonView dismissButtonView = DismissButtonView.this;
                    boolean z3 = z;
                    if (z3) {
                        if (dismissButtonView.mAccessibilityTextResId > 0) {
                            dismissButtonView.announceForAccessibility(dismissButtonView.getResources().getText(dismissButtonView.mAccessibilityTextResId));
                        }
                    } else {
                        int i = DismissButtonView.$r8$clinit;
                    }
                    dismissButtonView.updateResources(z3);
                }
            }).start();
            if (!this.mFocusChangeHapticDisabled) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
        } else {
            updateResources(z);
            setScaleX(z ? 1.15f : 1.0f);
            setScaleY(z ? 1.15f : 1.0f);
            setAlpha(z ? 0.8f : 1.0f);
        }
        boolean z3 = true;
        if (this.mDismissType == 1) {
            try {
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                if (z) {
                    z3 = false;
                }
                windowManagerService.setDragSurfaceToOverlay(z3);
            } catch (RemoteException e) {
                Log.w("Failed to setDragSurfaceToOverlay.", e.getMessage());
            }
        }
        updateDismissButtonState(z);
    }
}

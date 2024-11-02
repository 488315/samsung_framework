package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TouchPad extends FloatingUI {
    public View mCenterText;
    public final boolean mIsMediaPanel;
    public View mTouchPadBg;
    public View mTouchPadLine;

    public TouchPad(Context context, boolean z) {
        super(context);
        this.mIsMediaPanel = z;
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void connectUIObject() {
        View inflate = View.inflate(this.mContext, R.layout.cursorcontrol_panel, null);
        this.mOverlayView = inflate;
        this.mTouchPadBg = inflate.findViewById(R.id.touch_pad_bg);
        this.mCenterText = this.mOverlayView.findViewById(R.id.center_text);
        View findViewById = this.mOverlayView.findViewById(R.id.touch_pad_line);
        this.mTouchPadLine = findViewById;
        findViewById.setVisibility(4);
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void fadeInAnimation() {
        startFadeInAnimation(this.mTouchPadBg, false);
        startFadeInAnimation(this.mCenterText, false);
        new Handler(Looper.getMainLooper()).postDelayed(new TouchPad$$ExternalSyntheticLambda0(this, 0), 100L);
    }

    public final int getPixel(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void setAppendLayoutParams() {
        int pixel;
        this.mLayoutParam.setFitInsetsTypes(0);
        this.mLayoutParam.semAddExtensionFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        this.mLayoutParam.layoutInDisplayCutoutMode = 3;
        Context context = this.mContext;
        int displayX = ControlPanelUtils.getDisplayX(context);
        int displayY = ControlPanelUtils.getDisplayY(context);
        int i = Settings.Global.getInt(context.getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
        boolean isTypeFold = ControlPanelUtils.isTypeFold();
        int i2 = 83;
        boolean z = this.mIsMediaPanel;
        if (isTypeFold) {
            if (z) {
                WindowManager.LayoutParams layoutParams = this.mLayoutParam;
                layoutParams.height = (int) ((displayY * 30.4d) / 100.0d);
                layoutParams.y = getPixel(R.dimen.basic_panel_top_margin_land);
                this.mLayoutParam.gravity = 51;
            } else {
                WindowManager.LayoutParams layoutParams2 = this.mLayoutParam;
                double d = displayY;
                layoutParams2.height = (int) ((37.1d * d) / 100.0d);
                int pixel2 = ((displayY / 2) - getPixel(R.dimen.basic_panel_top_margin_land)) - ((int) ((d * 18.55d) / 100.0d));
                WindowManager.LayoutParams layoutParams3 = this.mLayoutParam;
                layoutParams2.y = pixel2 - (layoutParams3.height / 2);
                layoutParams3.gravity = 83;
            }
            if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL && !FlexPanelActivity.sTalkbackEnabled && ControlPanelUtils.isWheelActive(context)) {
                if (i == 2) {
                    this.mLayoutParam.x = getPixel(R.dimen.touchpad_left_margin_to_panel_land) + getPixel(R.dimen.basic_panel_left_margin) + ((int) ((displayX * 6.76d) / 100.0d));
                    WindowManager.LayoutParams layoutParams4 = this.mLayoutParam;
                    layoutParams4.width = (displayX - layoutParams4.x) - getPixel(R.dimen.right_scroll_wheel_margin_to_touchpad_land);
                } else {
                    this.mLayoutParam.x = getPixel(R.dimen.touchpad_left_margin_to_panel_with_left_wheel_land) + getPixel(R.dimen.basic_panel_left_margin) + ((int) ((displayX * 6.76d) / 100.0d));
                    WindowManager.LayoutParams layoutParams5 = this.mLayoutParam;
                    layoutParams5.width = (displayX - layoutParams5.x) - getPixel(R.dimen.touchpad_right_margin_land);
                }
            } else {
                this.mLayoutParam.x = getPixel(R.dimen.touchpad_left_margin_land);
                WindowManager.LayoutParams layoutParams6 = this.mLayoutParam;
                layoutParams6.width = (displayX - layoutParams6.x) - getPixel(R.dimen.touchpad_right_margin_land);
            }
            if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1) {
                WindowManager.LayoutParams layoutParams7 = this.mLayoutParam;
                layoutParams7.x = (displayX - layoutParams7.width) - getPixel(R.dimen.touchpad_left_margin_land);
            }
        } else {
            WindowManager.LayoutParams layoutParams8 = this.mLayoutParam;
            if (z) {
                pixel = getPixel(R.dimen.touchpad_with_media_bottom_margin);
            } else {
                pixel = getPixel(R.dimen.touchpad_bottom_margin);
            }
            layoutParams8.y = pixel;
            if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL && !FlexPanelActivity.sTalkbackEnabled && ControlPanelUtils.isWheelActive(context)) {
                WindowManager.LayoutParams layoutParams9 = this.mLayoutParam;
                if (i == 2) {
                    i2 = 85;
                }
                layoutParams9.gravity = i2;
                layoutParams9.x = getPixel(R.dimen.touchpad_wheel_scroll_margin);
                WindowManager.LayoutParams layoutParams10 = this.mLayoutParam;
                layoutParams10.width = (displayX - layoutParams10.x) - ((int) ((displayX * 5.55d) / 100.0d));
            } else {
                WindowManager.LayoutParams layoutParams11 = this.mLayoutParam;
                layoutParams11.width = (int) ((displayX * 88.9d) / 100.0d);
                layoutParams11.gravity = 81;
            }
            WindowManager.LayoutParams layoutParams12 = this.mLayoutParam;
            layoutParams12.height = ((displayY / 2) - layoutParams12.y) - getPixel(R.dimen.touchpad_top_margin);
        }
        this.mLayoutParam.setTitle("FlexPanelTouchPad");
    }

    public final void startFadeInAnimation(View view, boolean z) {
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.fadein);
        if (z) {
            view.setVisibility(0);
        }
        view.startAnimation(loadAnimation);
    }
}

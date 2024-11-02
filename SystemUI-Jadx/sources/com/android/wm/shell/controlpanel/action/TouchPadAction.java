package com.android.wm.shell.controlpanel.action;

import android.os.Handler;
import android.widget.GridLayout;
import com.android.wm.shell.controlpanel.GridUIManager;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda2;
import com.android.wm.shell.controlpanel.activity.TouchPad;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.wm.shell.controlpanel.widget.WheelScrollView;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TouchPadAction extends MenuActionType {
    private TouchPadAction() {
    }

    public static TouchPadAction createAction() {
        return new TouchPadAction();
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, GridUIManager gridUIManager) {
        ControlPanelAction.Action action = ControlPanelAction.Action.TouchPad;
        int value = action.getValue();
        FlexPanelActivity flexPanelActivity = (FlexPanelActivity) gridUIManager;
        flexPanelActivity.getClass();
        if (action.getValue() == value) {
            if (flexPanelActivity.mTouchPad == null) {
                if (flexPanelActivity.mIsMediaPanel) {
                    flexPanelActivity.mForceTouchPadRemoved = false;
                    flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeOut);
                    new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(flexPanelActivity, 4), 100L);
                    return;
                }
                if (flexPanelActivity.mIsEditPanel) {
                    if (flexPanelActivity.getPreferences$1("MEDIA_PANEL")) {
                        if (flexPanelActivity.getPreferences$1("MEDIA_TOUCH_PAD_ENABLED")) {
                            flexPanelActivity.mForceTouchPadRemoved = true;
                            flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
                        } else {
                            flexPanelActivity.mForceTouchPadRemoved = false;
                            flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", true);
                        }
                    } else if (flexPanelActivity.getPreferences$1("TOUCH_PAD_ENABLED")) {
                        flexPanelActivity.mForceTouchPadRemoved = true;
                        flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", false);
                    } else {
                        flexPanelActivity.mForceTouchPadRemoved = false;
                        flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", true);
                    }
                    GridLayout gridLayout = flexPanelActivity.mGridLayout;
                    if (gridLayout != null) {
                        gridLayout.removeAllViews();
                        Iterator it = flexPanelActivity.mEditActions.iterator();
                        while (it.hasNext()) {
                            flexPanelActivity.mGridLayout.addView(flexPanelActivity.getEditButton((ControlPanelAction.Action) it.next()));
                        }
                    }
                    if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                        flexPanelActivity.updateStatusPreferences(false);
                        return;
                    }
                    return;
                }
                flexPanelActivity.mForceTouchPadRemoved = false;
                flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", true);
                TouchPad touchPad = new TouchPad(flexPanelActivity, flexPanelActivity.mIsMediaPanel);
                flexPanelActivity.mTouchPad = touchPad;
                touchPad.showView();
                if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL && ControlPanelUtils.isWheelActive(flexPanelActivity) && !FlexPanelActivity.sTalkbackEnabled) {
                    WheelScrollView wheelScrollView = new WheelScrollView(flexPanelActivity, flexPanelActivity.mIsMediaPanel);
                    flexPanelActivity.mScrollWheel = wheelScrollView;
                    wheelScrollView.showView();
                    return;
                }
                return;
            }
            flexPanelActivity.mForceTouchPadRemoved = true;
            if (flexPanelActivity.getPreferences$1("MEDIA_PANEL")) {
                flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
                flexPanelActivity.removeTouchPad();
            } else {
                flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", false);
                flexPanelActivity.removeTouchPad();
            }
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                flexPanelActivity.updateStatusPreferences(false);
            }
            if (flexPanelActivity.mIsMediaPanel) {
                flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeOut);
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(flexPanelActivity, 5), 100L);
            }
        }
    }
}

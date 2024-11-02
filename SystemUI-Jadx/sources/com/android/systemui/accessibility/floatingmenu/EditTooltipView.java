package com.android.systemui.accessibility.floatingmenu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.view.MotionEvent;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.R;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EditTooltipView extends BaseTooltipView {
    public final Context mContext;

    public EditTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        this.mContext = context;
        setAccessibilityPaneTitle(context.getText(R.string.accessibility_floating_button));
        this.mTextView.setText(context.getText(R.string.accessibility_floating_button_edit_tooltip));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.BaseTooltipView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (!AccessibilityUtils.makeToastForCoverScreen(this.mContext, (String) null)) {
                Intent intent = new Intent();
                intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$AccessibilityButtonPreferenceActivity");
                intent.setFlags(268468224);
                try {
                    this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                } catch (ActivityNotFoundException unused) {
                }
            }
            hide();
        }
        return super.onTouchEvent(motionEvent);
    }
}

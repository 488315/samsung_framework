package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class QuickQSPanel extends QSPanel {
    public QuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getResources().getInteger(R.integer.quick_qs_panel_max_tiles);
    }

    @Override // com.android.systemui.qs.QSPanel
    public final void drawTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord, QSTile.State state) {
        if (state instanceof QSTile.SignalState) {
            QSTile.SignalState signalState = new QSTile.SignalState();
            state.copyTo(signalState);
            signalState.activityIn = false;
            signalState.activityOut = false;
            state = signalState;
        }
        qSPanelControllerBase$TileRecord.tileView.onStateChanged(state);
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // com.android.systemui.qs.QSPanel, com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("qs_show_brightness".equals(str)) {
            super.onTuningChanged(str, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
    }
}

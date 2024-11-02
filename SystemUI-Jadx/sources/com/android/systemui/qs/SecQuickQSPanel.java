package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.function.DoubleSupplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SecQuickQSPanel extends SecQSPanel {
    public boolean mDisabledByPolicy;
    public DoubleSupplier mMeasuredHeightSupplier;

    public SecQuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final QSEvent closePanelEvent() {
        return QSEvent.QQS_PANEL_COLLAPSED;
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final String getDumpableTag() {
        return "SecQuickQSPanel";
    }

    @Override // com.android.systemui.qs.SecQSPanel, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mMeasuredHeightSupplier != null) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize((int) this.mMeasuredHeightSupplier.getAsDouble()));
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final QSEvent openPanelEvent() {
        return QSEvent.QQS_PANEL_EXPANDED;
    }

    @Override // com.android.systemui.qs.SecQSPanel, android.view.View
    public final void setVisibility(int i) {
        if (this.mDisabledByPolicy) {
            if (getVisibility() == 8) {
                return;
            } else {
                i = 8;
            }
        }
        super.setVisibility(i);
    }
}

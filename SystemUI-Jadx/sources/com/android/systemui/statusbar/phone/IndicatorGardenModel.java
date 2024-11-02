package com.android.systemui.statusbar.phone;

import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class IndicatorGardenModel {
    public int cameraTopMargin;
    public boolean hasCameraTopMargin;
    public int paddingLeft;
    public int paddingRight;
    public int totalHeight;
    public int maxWidthLeftContainer = -1;
    public int maxWidthCenterContainer = -1;
    public int maxWidthRightContainer = -1;

    public final String toString() {
        int i = this.totalHeight;
        boolean z = this.hasCameraTopMargin;
        int i2 = this.cameraTopMargin;
        int i3 = this.paddingLeft;
        int i4 = this.paddingRight;
        int i5 = this.maxWidthLeftContainer;
        int i6 = this.maxWidthCenterContainer;
        int i7 = this.maxWidthRightContainer;
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("height=", i, " hasCameraTopMargin=", z, " cameraTopMargin=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i2, "paddingLeft=", i3, " paddingRight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i4, " maxWidthLeftContainer=", i5, " maxWidthCenterContainer=");
        m.append(i6);
        m.append(" maxWidthRightContainer=");
        m.append(i7);
        return m.toString();
    }
}

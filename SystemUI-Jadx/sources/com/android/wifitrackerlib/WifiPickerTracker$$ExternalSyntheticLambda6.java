package com.android.wifitrackerlib;

import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.wifitrackerlib.WifiPickerTracker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker.WifiPickerTrackerCallback f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda6(WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTrackerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                return;
            case 1:
                ((AccessPointControllerImpl) this.f$0).scanForAccessPoints();
                return;
            default:
                this.f$0.getClass();
                return;
        }
    }
}

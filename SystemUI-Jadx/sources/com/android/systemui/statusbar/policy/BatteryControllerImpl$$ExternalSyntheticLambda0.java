package com.android.systemui.statusbar.policy;

import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.PowerUtil;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryControllerImpl f$0;

    public /* synthetic */ BatteryControllerImpl$$ExternalSyntheticLambda0(BatteryControllerImpl batteryControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str = null;
        switch (this.$r8$classId) {
            case 0:
                BatteryControllerImpl batteryControllerImpl = this.f$0;
                synchronized (batteryControllerImpl.mEstimateLock) {
                    batteryControllerImpl.mEstimate = null;
                    batteryControllerImpl.mEstimates.getClass();
                }
                batteryControllerImpl.mFetchingEstimate = false;
                batteryControllerImpl.mMainHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, 1));
                return;
            default:
                BatteryControllerImpl batteryControllerImpl2 = this.f$0;
                synchronized (batteryControllerImpl2.mFetchCallbacks) {
                    synchronized (batteryControllerImpl2.mEstimateLock) {
                        Estimate estimate = batteryControllerImpl2.mEstimate;
                        if (estimate != null) {
                            str = PowerUtil.getBatteryRemainingShortStringFormatted(batteryControllerImpl2.mContext, estimate.estimateMillis);
                        }
                    }
                    Iterator it = batteryControllerImpl2.mFetchCallbacks.iterator();
                    while (it.hasNext()) {
                        BatteryMeterView batteryMeterView = ((BatteryMeterView$$ExternalSyntheticLambda0) it.next()).f$0;
                        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = batteryMeterView.mBatteryPercentView;
                        if (switchableDoubleShadowTextView != null) {
                            if (str != null && batteryMeterView.mShowPercentMode == 3) {
                                batteryMeterView.mEstimateText = str;
                                switchableDoubleShadowTextView.setText(str);
                                batteryMeterView.updateContentDescription();
                            } else {
                                batteryMeterView.setPercentTextAtCurrentLevel();
                            }
                        }
                    }
                    batteryControllerImpl2.mFetchCallbacks.clear();
                }
                return;
        }
    }
}

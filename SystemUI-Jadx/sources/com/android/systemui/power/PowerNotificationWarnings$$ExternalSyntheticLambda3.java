package com.android.systemui.power;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda3 implements ActivityStarter.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.Callback
    public final void onActivityStarted(int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((PowerNotificationWarnings) obj).mUsbHighTempDialog = null;
                return;
            case 1:
                ((PowerNotificationWarnings.AnonymousClass1) obj).this$0.mHighTempDialog = null;
                return;
            default:
                ((PowerNotificationWarnings.AnonymousClass2) obj).this$0.mThermalShutdownDialog = null;
                return;
        }
    }
}

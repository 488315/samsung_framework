package com.android.server.biometrics.sensors.face.hidl;

import com.android.server.biometrics.sensors.face.aidl.AidlSession;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HidlToAidlSensorAdapter$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HidlToAidlSensorAdapter f$0;

    public /* synthetic */ HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(HidlToAidlSensorAdapter hidlToAidlSensorAdapter, int i) {
        this.$r8$classId = i;
        this.f$0 = hidlToAidlSensorAdapter;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        AidlSession aidlSession;
        int i = this.$r8$classId;
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter = this.f$0;
        switch (i) {
            case 0:
                return Integer.valueOf(hidlToAidlSensorAdapter.mCurrentUserId);
            case 1:
                if (hidlToAidlSensorAdapter.mDaemon != null && (aidlSession = hidlToAidlSensorAdapter.mSession) != null) {
                    return aidlSession;
                }
                AidlSession aidlSession2 = new AidlSession(hidlToAidlSensorAdapter.mContext, new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(hidlToAidlSensorAdapter, 2), hidlToAidlSensorAdapter.mCurrentUserId, hidlToAidlSensorAdapter.getAidlResponseHandler());
                hidlToAidlSensorAdapter.mSession = aidlSession2;
                return aidlSession2;
            default:
                return hidlToAidlSensorAdapter.getIBiometricsFace();
        }
    }
}

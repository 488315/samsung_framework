package com.android.server.biometrics.sensors.face.aidl;

import com.android.server.biometrics.sensors.StopUserClient;
import com.android.server.biometrics.sensors.face.aidl.SemFaceAidlLockoutHalImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Sensor$$ExternalSyntheticLambda1 implements SemFaceAidlLockoutHalImpl.LockoutResetCallback, StopUserClient.UserStoppedCallback {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Sensor$$ExternalSyntheticLambda1(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
    public void onUserStopped() {
        Sensor.this.mCurrentSession = null;
    }
}

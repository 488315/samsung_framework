package com.android.server.biometrics.sensors.face.aidl;

import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda5(FaceProvider faceProvider, int i, IBinder iBinder, long j, int i2) {
        this.$r8$classId = i2;
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = iBinder;
        this.f$3 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FaceProvider faceProvider = this.f$0;
                int i = this.f$1;
                ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mScheduler.cancelAuthenticationOrDetection(this.f$2, this.f$3);
                break;
            case 1:
                FaceProvider faceProvider2 = this.f$0;
                int i2 = this.f$1;
                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i2)).mScheduler.cancelEnrollment(this.f$2, this.f$3);
                break;
            default:
                FaceProvider faceProvider3 = this.f$0;
                int i3 = this.f$1;
                ((Sensor) faceProvider3.mFaceSensors.mSensors.get(i3)).mScheduler.cancelAuthenticationOrDetection(this.f$2, this.f$3);
                break;
        }
    }
}

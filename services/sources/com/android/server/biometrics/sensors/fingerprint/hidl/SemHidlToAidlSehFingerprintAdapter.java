package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.os.IBinder;
import android.util.Slog;
import java.util.ArrayList;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;
import vendor.samsung.hardware.biometrics.fingerprint.SehResult;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemHidlToAidlSehFingerprintAdapter implements ISehFingerprint {
    public final Supplier mSession;

    public SemHidlToAidlSehFingerprintAdapter(HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 hidlToAidlSensorAdapter$$ExternalSyntheticLambda3) {
        this.mSession = hidlToAidlSensorAdapter$$ExternalSyntheticLambda3;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        Slog.e("SemHidlToAidlSehFingerprintAdapter", "asBinder unsupported in HIDL");
        return null;
    }

    @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
    public final SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
        IBiometricsFingerprint iBiometricsFingerprint = (IBiometricsFingerprint) this.mSession.get();
        final SehResult sehResult = new SehResult();
        if (iBiometricsFingerprint instanceof ISehBiometricsFingerprint) {
            ArrayList arrayList = new ArrayList();
            if (bArr != null && bArr.length > 0) {
                for (byte b : bArr) {
                    arrayList.add(Byte.valueOf(b));
                }
            }
            ((ISehBiometricsFingerprint) iBiometricsFingerprint).sehRequest(i2, i3, arrayList, new ISehBiometricsFingerprint.sehRequestCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.SemHidlToAidlSehFingerprintAdapter$$ExternalSyntheticLambda0
                @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint.sehRequestCallback
                public final void onValues(ArrayList arrayList2, int i4) {
                    SemHidlToAidlSehFingerprintAdapter.this.getClass();
                    SehResult sehResult2 = sehResult;
                    sehResult2.retValue = i4;
                    if (i4 == 0) {
                        byte[] bArr2 = new byte[arrayList2.size()];
                        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                            bArr2[i5] = ((Byte) arrayList2.get(i5)).byteValue();
                        }
                        sehResult2.data = bArr2;
                    }
                }
            });
        } else {
            sehResult.retValue = -1;
        }
        return sehResult;
    }
}

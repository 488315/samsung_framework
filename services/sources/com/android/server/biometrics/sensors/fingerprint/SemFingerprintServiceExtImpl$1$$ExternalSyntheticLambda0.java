package com.android.server.biometrics.sensors.fingerprint;

import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFingerprintServiceExtImpl$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFingerprintServiceExtImpl.AnonymousClass1 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SemFingerprintServiceExtImpl$1$$ExternalSyntheticLambda0(SemFingerprintServiceExtImpl.AnonymousClass1 anonymousClass1, int i, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemFingerprintServiceExtImpl.AnonymousClass1 anonymousClass1 = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback = anonymousClass1.this$0.mSysUiDisplayStateCallback;
                if (iSemBiometricSysUiDisplayStateCallback != null) {
                    try {
                        iSemBiometricSysUiDisplayStateCallback.onStart(i, i2, i3);
                        break;
                    } catch (RemoteException unused) {
                        Slog.w("FingerprintService.Ext", "onStartDisplayState: failed to invoke onStartCallback");
                        return;
                    }
                }
                break;
            default:
                SemFingerprintServiceExtImpl.AnonymousClass1 anonymousClass12 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback2 = anonymousClass12.this$0.mSysUiDisplayStateCallback;
                if (iSemBiometricSysUiDisplayStateCallback2 != null) {
                    try {
                        iSemBiometricSysUiDisplayStateCallback2.onFinish(i4, i5, i6);
                        break;
                    } catch (RemoteException unused2) {
                        Slog.w("FingerprintService.Ext", "onFinishDisplayState: failed to invoke onFinishCallback");
                    }
                }
                break;
        }
    }
}

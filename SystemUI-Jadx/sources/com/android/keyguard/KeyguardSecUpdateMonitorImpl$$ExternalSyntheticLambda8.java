package com.android.keyguard;

import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecUpdateMonitorImpl f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda8(KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecUpdateMonitorImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FaceManager faceManager;
        FingerprintManager fingerprintManager;
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                keyguardSecUpdateMonitorImpl.mFastUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda8(keyguardSecUpdateMonitorImpl, 11), "PowerManager#userActivity"));
                return;
            case 1:
                this.f$0.updateFingerprintListeningState(2);
                return;
            case 2:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl2 = this.f$0;
                keyguardSecUpdateMonitorImpl2.getClass();
                keyguardSecUpdateMonitorImpl2.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED);
                return;
            case 3:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl3 = this.f$0;
                keyguardSecUpdateMonitorImpl3.getClass();
                keyguardSecUpdateMonitorImpl3.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET);
                return;
            case 4:
                ((LooperSlowLogControllerImpl) this.f$0.mLooperSlowLogController).enable(1, 10L, 20L, 0L, false, null);
                return;
            case 5:
                ((LooperSlowLogControllerImpl) this.f$0.mLooperSlowLogController).enable(1, 10L, 20L, 0L, false, null);
                return;
            case 6:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl4 = this.f$0;
                keyguardSecUpdateMonitorImpl4.getClass();
                Log.i("KeyguardFingerprint", "Waiting window focus change");
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    keyguardSecUpdateMonitorImpl4.updateFingerprintListeningState(2);
                    return;
                }
                return;
            case 7:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl5 = this.f$0;
                boolean z = keyguardSecUpdateMonitorImpl5.mDeviceInteractive;
                if (!keyguardSecUpdateMonitorImpl5.isEnabledWof() && !z && keyguardSecUpdateMonitorImpl5.isFingerprintOptionEnabled() && (fingerprintManager = keyguardSecUpdateMonitorImpl5.mFpm) != null) {
                    fingerprintManager.requestSessionOpen();
                }
                if (keyguardSecUpdateMonitorImpl5.isUnlockCompleted() && !z && keyguardSecUpdateMonitorImpl5.isFaceOptionEnabled() && (faceManager = keyguardSecUpdateMonitorImpl5.mFaceManager) != null) {
                    faceManager.semSessionOpen();
                    return;
                }
                return;
            case 8:
                this.f$0.mFastUnlockController.logLapTime("onFaceAuthenticationSucceeded end", new Object[0]);
                return;
            case 9:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl6 = this.f$0;
                if (keyguardSecUpdateMonitorImpl6.isFaceOptionEnabled()) {
                    if (keyguardSecUpdateMonitorImpl6.isFaceDetectionRunning()) {
                        keyguardSecUpdateMonitorImpl6.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_SESSION_CLOSE);
                    }
                    keyguardSecUpdateMonitorImpl6.setFaceAuthenticated(false);
                    if (keyguardSecUpdateMonitorImpl6.mFaceManager != null) {
                        Log.d("KeyguardFace", "requestSessionClose()");
                        keyguardSecUpdateMonitorImpl6.mFaceManager.semSessionClose();
                        return;
                    }
                    return;
                }
                return;
            case 10:
                this.f$0.mFastUnlockController.logLapTime("onFpAuthenticationSucceeded end", new Object[0]);
                return;
            default:
                this.f$0.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                return;
        }
    }
}

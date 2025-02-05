package android.hardware.camera2.impl;

import android.hardware.camera2.impl.CameraDeviceImpl;

/* loaded from: classes2.dex */
public interface CameraCaptureSessionCore {
    void closeWithoutDraining();

    CameraDeviceImpl.StateCallbackKK getDeviceStateCallback();

    boolean isAborting();

    void replaceSessionClose();
}

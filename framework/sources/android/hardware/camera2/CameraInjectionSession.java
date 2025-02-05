package android.hardware.camera2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class CameraInjectionSession implements AutoCloseable {

    public static abstract class InjectionStatusCallback {
        public static final int ERROR_INJECTION_SERVICE = 1;
        public static final int ERROR_INJECTION_SESSION = 0;
        public static final int ERROR_INJECTION_UNSUPPORTED = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ErrorCode {
        }

        public abstract void onInjectionError(int i);

        public abstract void onInjectionSucceeded(CameraInjectionSession cameraInjectionSession);
    }

    @Override // java.lang.AutoCloseable
    public abstract void close();
}

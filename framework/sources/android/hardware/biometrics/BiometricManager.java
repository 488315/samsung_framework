package android.hardware.biometrics;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.biometrics.BiometricTestSession;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BiometricManager {
    public static final int BIOMETRIC_ERROR_HW_UNAVAILABLE = 1;
    public static final int BIOMETRIC_ERROR_LOCKOUT = 7;
    public static final int BIOMETRIC_ERROR_MANDATORY_NOT_ACTIVE = 20;
    public static final int BIOMETRIC_ERROR_NONE_ENROLLED = 11;
    public static final int BIOMETRIC_ERROR_NOT_ENABLED_FOR_APPS = 21;
    public static final int BIOMETRIC_ERROR_NO_HARDWARE = 12;
    public static final int BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = 15;
    public static final long BIOMETRIC_NO_AUTHENTICATION = -1;
    public static final int BIOMETRIC_SUCCESS = 0;
    public static final String EXTRA_ENROLL_REASON = "enroll_reason";
    private static final int GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS = 32783;
    private static final String TAG = "BiometricManager";
    private final Context mContext;
    private final IAuthService mService;

    public interface Authenticators {

        @SystemApi
        public static final int BIOMETRIC_CONVENIENCE = 4095;
        public static final int BIOMETRIC_MAX_STRENGTH = 1;
        public static final int BIOMETRIC_MIN_STRENGTH = 32767;
        public static final int BIOMETRIC_STRONG = 15;
        public static final int BIOMETRIC_WEAK = 255;
        public static final int DEVICE_CREDENTIAL = 32768;

        @SystemApi
        public static final int EMPTY_SET = 0;
        public static final int MANDATORY_BIOMETRICS = 65536;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Types {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BiometricError {
    }

    public static String authenticatorToStr(int authenticatorType) {
        switch (authenticatorType) {
            case 15:
                return "BIOMETRIC_STRONG";
            case 255:
                return "BIOMETRIC_WEAK";
            case 4095:
                return "BIOMETRIC_CONVENIENCE";
            case 32768:
                return "DEVICE_CREDENTIAL";
            default:
                return "Unknown authenticator type: " + authenticatorType;
        }
    }

    public static class Strings {
        int mAuthenticators;
        private final Context mContext;
        private final IAuthService mService;

        private Strings(Context context, IAuthService service, int authenticators) {
            this.mContext = context;
            this.mService = service;
            this.mAuthenticators = authenticators;
        }

        public CharSequence getButtonLabel() {
            int userId = this.mContext.getUserId();
            String opPackageName = this.mContext.getOpPackageName();
            try {
                return this.mService.getButtonLabel(userId, opPackageName, this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public CharSequence getPromptMessage() {
            int userId = this.mContext.getUserId();
            String opPackageName = this.mContext.getOpPackageName();
            try {
                return this.mService.getPromptMessage(userId, opPackageName, this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public CharSequence getSettingName() {
            int userId = this.mContext.getUserId();
            String opPackageName = this.mContext.getOpPackageName();
            try {
                return this.mService.getSettingName(userId, opPackageName, this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public BiometricManager(Context context, IAuthService service) {
        this.mContext = context;
        this.mService = service;
    }

    public List<SensorProperties> getSensorProperties() {
        try {
            List<SensorPropertiesInternal> internalProperties = this.mService.getSensorProperties(this.mContext.getOpPackageName());
            List<SensorProperties> properties = new ArrayList<>();
            for (SensorPropertiesInternal internalProp : internalProperties) {
                properties.add(SensorProperties.from(internalProp));
            }
            return properties;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BiometricTestSession createTestSession(int sensorId) {
        try {
            return new BiometricTestSession(this.mContext, sensorId, new BiometricTestSession.TestSessionProvider() { // from class: android.hardware.biometrics.BiometricManager$$ExternalSyntheticLambda0
                @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                public final ITestSession createTestSession(Context context, int i, ITestSessionCallback iTestSessionCallback) {
                    ITestSession lambda$createTestSession$0;
                    lambda$createTestSession$0 = BiometricManager.this.lambda$createTestSession$0(context, i, iTestSessionCallback);
                    return lambda$createTestSession$0;
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ITestSession lambda$createTestSession$0(Context context, int sensorId1, ITestSessionCallback callback) throws RemoteException {
        return this.mService.createTestSession(sensorId1, callback, context.getOpPackageName());
    }

    public String getUiPackage() {
        try {
            return this.mService.getUiPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int canAuthenticate() {
        int result = canAuthenticate(this.mContext.getUserId(), 255);
        FrameworkStatsLog.write(354, false, 0, result);
        FrameworkStatsLog.write(356, 4, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        return result;
    }

    public int canAuthenticate(int authenticators) {
        int result = canAuthenticate(this.mContext.getUserId(), authenticators);
        FrameworkStatsLog.write(354, true, authenticators, result);
        return result;
    }

    public int canAuthenticate(int userId, int authenticators) {
        if (this.mService != null) {
            try {
                String opPackageName = this.mContext.getOpPackageName();
                return this.mService.canAuthenticate(opPackageName, userId, authenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "canAuthenticate(): Service not connected");
        return 1;
    }

    public Strings getStrings(int authenticators) {
        return new Strings(this.mContext, this.mService, authenticators);
    }

    public boolean hasEnrolledBiometrics(int userId) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasEnrolledBiometrics(userId, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception in hasEnrolledBiometrics(): " + e);
            return false;
        }
    }

    public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback callback) {
        if (this.mService != null) {
            try {
                this.mService.registerEnabledOnKeyguardCallback(callback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "registerEnabledOnKeyguardCallback(): Service not connected");
    }

    public void registerAuthenticationStateListener(AuthenticationStateListener listener) {
        if (this.mService != null) {
            try {
                this.mService.registerAuthenticationStateListener(listener);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "registerAuthenticationStateListener(): Service not connected");
    }

    public void unregisterAuthenticationStateListener(AuthenticationStateListener listener) {
        if (this.mService != null) {
            try {
                this.mService.unregisterAuthenticationStateListener(listener);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "unregisterAuthenticationStateListener(): Service not connected");
    }

    public void invalidateAuthenticatorIds(int userId, int fromSensorId, IInvalidationCallback callback) {
        if (this.mService != null) {
            try {
                this.mService.invalidateAuthenticatorIds(userId, fromSensorId, callback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long[] getAuthenticatorIds() {
        return getAuthenticatorIds(UserHandle.myUserId());
    }

    public long[] getAuthenticatorIds(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.getAuthenticatorIds(userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "getAuthenticatorIds(): Service not connected");
        return new long[0];
    }

    public void resetLockoutTimeBound(IBinder token, String opPackageName, int fromSensorId, int userId, byte[] hardwareAuthToken) {
        if (this.mService != null) {
            try {
                this.mService.resetLockoutTimeBound(token, opPackageName, fromSensorId, userId, hardwareAuthToken);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int userId, byte[] hardwareAuthToken) {
        if (this.mService != null) {
            try {
                this.mService.resetLockout(userId, hardwareAuthToken);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getLastAuthenticationTime(int authenticators) {
        if (authenticators == 0 || (GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS & authenticators) != authenticators) {
            throw new IllegalArgumentException("Only BIOMETRIC_STRONG and DEVICE_CREDENTIAL authenticators may be used.");
        }
        if (this.mService != null) {
            try {
                return this.mService.getLastAuthenticationTime(UserHandle.myUserId(), authenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1L;
    }
}

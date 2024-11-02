package com.samsung.android.bio.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes5.dex */
public class SemFingerprintManager {
    public static final String EXTRA_KEY_ALLOW_AUTH_EVEN_IF_ENCRYPTED_OR_LOCKDOWN = "EXTRA_KEY_ALLOW_EVEN_IF_ENCRYPTED_OR_LOCKDOWN";
    public static final String EXTRA_KEY_AUTH_FLAG = "EXTRA_KEY_AUTH_FLAG";
    public static final String EXTRA_KEY_DISPLAY_ID = "EXTRA_KEY_DISPLAY_ID";
    public static final String EXTRA_KEY_ICON_COLOR = "EXTRA_KEY_ICON_COLOR";
    public static final String EXTRA_KEY_ICON_CONTAINER_COLOR = "EXTRA_KEY_ICON_CONTAINER_COLOR";
    public static final String EXTRA_KEY_PRIVILEGED_FLAG = "sem_privileged_attr";
    public static final String EXTRA_KEY_TASK_ID = "EXTRA_KEY_TASK_ID";
    public static final int FEATURE_GESTURE = 1;
    public static final int FINGERPRINT_ACQUIRED_GOOD = 0;
    public static final int FINGERPRINT_ACQUIRED_IMAGER_DIRTY = 3;
    public static final int FINGERPRINT_ACQUIRED_INSUFFICIENT = 2;
    public static final int FINGERPRINT_ACQUIRED_PARTIAL = 1;
    public static final int FINGERPRINT_ACQUIRED_TOO_FAST = 5;
    public static final int FINGERPRINT_ACQUIRED_TOO_SLOW = 4;
    public static final int FINGERPRINT_ERROR_CANCELED = 5;
    public static final int FINGERPRINT_ERROR_HW_UNAVAILABLE = 1;
    public static final int FINGERPRINT_ERROR_LOCKOUT = 7;
    public static final int FINGERPRINT_ERROR_LOCKOUT_PERMANENT = 9;
    public static final int FINGERPRINT_ERROR_TEMPLATE_CORRUPTED = 1001;
    public static final int FINGERPRINT_ERROR_TIMEOUT = 3;
    public static final int FINGERPRINT_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FINGERPRINT_ERROR_USER_CANCELED = 10;
    private static final int MSG_ACQUIRED = 100;
    private static final int MSG_AUTHENTICATION_FAILED = 102;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 101;
    private static final int MSG_ERROR = 103;
    public static final int PRIVILEGED_FLAG_ALLOW_BACKGROUND = 4;
    public static final int PRIVILEGED_FLAG_AVOID_LOCKOUT = 1;
    public static final int PRIVILEGED_FLAG_HIDE_AUTHENTICATION_GUIDE_LAYER = 16;
    public static final int PRIVILEGED_FLAG_NO_VIBRATION_EFFECT = 8;
    public static final int PRIVILEGED_FLAG_RECEIVE_VENDOR_EVENT = 2;
    public static final int PRIVILEGED_FLAG_USE_KEYGUARD_ICON = 32;
    private static final String TAG = "SemFingerprintManager";
    private AuthenticationCallback mAuthenticationCallback;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private final FingerprintManager mFingerprintManager;
    private Handler mHandler;
    private final IFingerprintService mService;
    private final IFingerprintServiceReceiver mServiceReceiver;
    private final IBinder mToken = new Binder();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes5.dex */
    public @interface ExtraKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes5.dex */
    public @interface FingerprintAcquired {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes5.dex */
    public @interface FingerprintError {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes5.dex */
    public @interface PrivilegedFlag {
    }

    /* loaded from: classes5.dex */
    public class MyHandler extends Handler {
        /* synthetic */ MyHandler(SemFingerprintManager semFingerprintManager, Context context, MyHandlerIA myHandlerIA) {
            this(context);
        }

        /* synthetic */ MyHandler(SemFingerprintManager semFingerprintManager, Looper looper, MyHandlerIA myHandlerIA) {
            this(looper);
        }

        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Slog.i(SemFingerprintManager.TAG, "handleMessage = " + msg.what + ", " + msg.arg1 + ", " + msg.arg2);
            switch (msg.what) {
                case 100:
                    SemFingerprintManager.this.sendAcquiredResult(msg.arg1, (String) msg.obj);
                    return;
                case 101:
                    SemFingerprintManager.this.sendAuthenticatedSucceeded((Fingerprint) msg.obj, msg.arg1, null);
                    return;
                case 102:
                    SemFingerprintManager.this.sendAuthenticatedFailed();
                    return;
                case 103:
                    SemFingerprintManager.this.sendErrorResult(msg.arg1, (String) msg.obj);
                    return;
                default:
                    Slog.w(SemFingerprintManager.TAG, "handleMessage : Unknown msg");
                    return;
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class CryptoObject {
        private android.hardware.biometrics.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData = null;

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(signature);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(cipher);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(mac);
            this.mFidoRequestData = fidoRequestData;
        }

        public Signature getSignature() {
            return this.mBioCryptoObject.getSignature();
        }

        public Cipher getCipher() {
            return this.mBioCryptoObject.getCipher();
        }

        public Mac getMac() {
            return this.mBioCryptoObject.getMac();
        }

        public long getOpId() {
            return this.mBioCryptoObject.getOpId();
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        public void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    /* loaded from: classes5.dex */
    public static class AuthenticationResult {
        private final CryptoObject mCryptoObject;
        private final Fingerprint mFingerprint;

        public AuthenticationResult(CryptoObject crypto, Fingerprint fp) {
            this.mCryptoObject = crypto;
            this.mFingerprint = fp;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Fingerprint getFingerprint() {
            return this.mFingerprint;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationFailed() {
        }
    }

    /* loaded from: classes5.dex */
    public static class Characteristics {
        public static final int SENSOR_POSITION_HOME_KEY = 1;
        public static final int SENSOR_POSITION_IN_DISPLAY = 2;
        public static final int SENSOR_POSITION_POWER_KEY = 4;
        public static final int SENSOR_POSITION_REAR = 3;
        public static final int SENSOR_TYPE_CAPACITANCE = 1;
        public static final int SENSOR_TYPE_OPTICAL = 2;
        public static final int SENSOR_TYPE_ULTRASONIC = 3;
        private static final String mConfig = "google_touch_display_optical,settings=3,screen_off";
        private final FingerprintManager mFingerprintManager;

        /* synthetic */ Characteristics(FingerprintManager fingerprintManager, CharacteristicsIA characteristicsIA) {
            this(fingerprintManager);
        }

        private Characteristics(FingerprintManager fm) {
            this.mFingerprintManager = fm;
        }

        public int getSensorType() {
            if (mConfig.contains("ultrasonic")) {
                return 3;
            }
            if (mConfig.contains("optical")) {
                return 2;
            }
            return 1;
        }

        public int getSensorPosition() {
            return FingerprintManager.semGetSensorPosition();
        }

        public int getMaxFingerprintCount() {
            return this.mFingerprintManager.semGetMaxEnrollmentNumber();
        }

        public Rect getSensorAreaInDisplay() {
            return this.mFingerprintManager.semGetFingerIconRectInDisplay();
        }
    }

    public static SemFingerprintManager createInstance(Context context) {
        return new SemFingerprintManager(context);
    }

    private SemFingerprintManager(Context context) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        this.mFingerprintManager = fingerprintManager;
        this.mService = fingerprintManager.semGetService();
        this.mServiceReceiver = new IFingerprintServiceReceiver.Stub() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager.1
            AnonymousClass1() {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAcquired(int acquireInfo, int vendorCode) {
                SemFingerprintManager.this.mHandler.obtainMessage(100, SemFingerprintManager.this.convertAcquiredCode(acquireInfo), 0, FingerprintManager.getAcquiredString(SemFingerprintManager.this.mContext, acquireInfo, vendorCode)).sendToTarget();
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationSucceeded(Fingerprint fp, int userId, boolean isStrongBiometric) {
                SemFingerprintManager.this.mHandler.obtainMessage(101, userId, 0, fp).sendToTarget();
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationFailed() {
                SemFingerprintManager.this.mHandler.obtainMessage(102).sendToTarget();
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onError(int fpErrorCode, int vendor2) {
                int errorCode = fpErrorCode;
                switch (fpErrorCode) {
                    case 7:
                        errorCode = 7;
                        break;
                    case 9:
                        errorCode = 9;
                        break;
                    case 10:
                        errorCode = 10;
                        break;
                }
                SemFingerprintManager.this.mHandler.obtainMessage(103, errorCode, 0, FingerprintManager.getErrorString(SemFingerprintManager.this.mContext, fpErrorCode, vendor2)).sendToTarget();
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onEnrollResult(Fingerprint fp, int remaining) {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onRemoved(Fingerprint fp, int remaining) {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onChallengeGenerated(int sensorId, int userId, long challenge) {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerDown(int sensorId) {
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerUp(int sensorId) {
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.bio.fingerprint.SemFingerprintManager$1 */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 extends IFingerprintServiceReceiver.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int acquireInfo, int vendorCode) {
            SemFingerprintManager.this.mHandler.obtainMessage(100, SemFingerprintManager.this.convertAcquiredCode(acquireInfo), 0, FingerprintManager.getAcquiredString(SemFingerprintManager.this.mContext, acquireInfo, vendorCode)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(Fingerprint fp, int userId, boolean isStrongBiometric) {
            SemFingerprintManager.this.mHandler.obtainMessage(101, userId, 0, fp).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            SemFingerprintManager.this.mHandler.obtainMessage(102).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int fpErrorCode, int vendor2) {
            int errorCode = fpErrorCode;
            switch (fpErrorCode) {
                case 7:
                    errorCode = 7;
                    break;
                case 9:
                    errorCode = 9;
                    break;
                case 10:
                    errorCode = 10;
                    break;
            }
            SemFingerprintManager.this.mHandler.obtainMessage(103, errorCode, 0, FingerprintManager.getErrorString(SemFingerprintManager.this.mContext, fpErrorCode, vendor2)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fp, int remaining) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(Fingerprint fp, int remaining) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int sensorId, int userId, long challenge) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int sensorId) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int sensorId) {
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId, Bundle attr) {
        Bundle attr2;
        final long authId;
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel.isCanceled()) {
            Slog.w(TAG, "authentication : already canceled");
            handleDefaultError(callback);
            return;
        }
        if (this.mService != null) {
            try {
                useHandler(handler);
                this.mAuthenticationCallback = callback;
                this.mCryptoObject = crypto;
                long operationId = crypto != null ? crypto.getOpId() : 0L;
                if (attr != null) {
                    attr2 = attr;
                } else {
                    attr2 = new Bundle();
                }
                try {
                    setExtraInfo(this.mContext, attr2);
                    try {
                        FingerprintAuthenticateOptions options = new FingerprintAuthenticateOptions.Builder().setSensorId(-1).setUserId(userId).setOpPackageName(this.mContext.getOpPackageName()).setAttributionTag(this.mContext.getAttributionTag()).build();
                        authId = this.mService.semAuthenticate(this.mToken, operationId, this.mServiceReceiver, options, attr2);
                        if (authId < 0) {
                            this.mHandler.obtainMessage(103, 5, 0, FingerprintManager.getErrorString(this.mContext, 5, 0)).sendToTarget();
                        }
                    } catch (RemoteException e) {
                        e = e;
                    }
                    try {
                        cancel.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda1
                            @Override // android.os.CancellationSignal.OnCancelListener
                            public final void onCancel() {
                                SemFingerprintManager.this.lambda$authenticate$0(authId);
                            }
                        });
                    } catch (RemoteException e2) {
                        e = e2;
                        Slog.w(TAG, "Remote exception while authenticating: ", e);
                        handleDefaultError(callback);
                    }
                } catch (RemoteException e3) {
                    e = e3;
                }
            } catch (RemoteException e4) {
                e = e4;
            }
        } else {
            Slog.w(TAG, "authentication : Service is NULL");
            handleDefaultError(callback);
        }
    }

    public Characteristics getCharacteristics() {
        return new Characteristics(this.mFingerprintManager);
    }

    public boolean hasDisabledFingerprints() {
        return false;
    }

    public List<String> getEnrolledFingerprintNames() {
        checkPermission(Manifest.permission.BIOMETRICS_PRIVILEGED);
        List<Fingerprint> fpList = this.mFingerprintManager.getEnrolledFingerprints();
        if (fpList == null) {
            return new ArrayList();
        }
        ArrayList<String> nameList = new ArrayList<>(fpList.size());
        for (Fingerprint f : fpList) {
            nameList.add(f.getName().toString());
        }
        return nameList;
    }

    public boolean hasEnrolledFingerprints() {
        return this.mFingerprintManager.hasEnrolledFingerprints();
    }

    public boolean hasEnrolledFingerprints(int userId) {
        return this.mFingerprintManager.hasEnrolledFingerprints(userId);
    }

    public boolean hasFeature(int feature) {
        return this.mFingerprintManager.semHasFeature(feature);
    }

    public static void setExtraInfo(Context context, Bundle b) {
        int displayId;
        if (b == null) {
            return;
        }
        try {
            displayId = context.getDisplayId();
        } catch (Exception e) {
            Slog.w(TAG, "setExtraInfo: " + e.getMessage());
            displayId = 0;
        }
        b.putInt(EXTRA_KEY_DISPLAY_ID, displayId);
        if (context instanceof Activity) {
            b.putInt(EXTRA_KEY_TASK_ID, ((Activity) context).getTaskId());
        }
    }

    public static String getProductFeatureValue(Context context) {
        if (context.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
            throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
        }
        return "google_touch_display_optical,settings=3,screen_off";
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    private void checkPermission(String per) {
        if (this.mContext.checkSelfPermission(per) == -1) {
            throw new SecurityException("Must have " + per + " permission.");
        }
    }

    /* renamed from: cancelAuthentication */
    public void lambda$authenticate$0(long requestId) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), requestId);
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception while canceling authentication : " + e.getMessage());
            }
        }
    }

    private void handleDefaultError(final AuthenticationCallback callback) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFingerprintManager.this.lambda$handleDefaultError$1(callback);
            }
        });
    }

    public /* synthetic */ void lambda$handleDefaultError$1(AuthenticationCallback callback) {
        callback.onAuthenticationError(2, FingerprintManager.getErrorString(this.mContext, 2, 0));
    }

    public int convertAcquiredCode(int code) {
        switch (code) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return code;
        }
    }

    public void sendErrorResult(int errMsgId, String errMsg) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            if (errMsg == null) {
                errMsg = "";
            }
            authenticationCallback.onAuthenticationError(errMsgId, errMsg);
        }
    }

    public void sendAuthenticatedFailed() {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    public void sendAcquiredResult(int acquireInfo, String helpMsg) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null && helpMsg != null) {
            authenticationCallback.onAuthenticationHelp(acquireInfo, helpMsg);
        }
    }

    public void sendAuthenticatedSucceeded(Fingerprint fp, int userId, Bundle data) {
        if (this.mAuthenticationCallback != null) {
            CryptoObject cryptoObject = this.mCryptoObject;
            if (cryptoObject != null && data != null) {
                cryptoObject.setFidoResultData(data.getByteArray("fidoResult"));
            }
            AuthenticationResult result = new AuthenticationResult(this.mCryptoObject, fp);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }
}

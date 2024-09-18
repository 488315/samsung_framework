package com.samsung.android.camera.iris;

import android.app.ActivityManagerNative;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.security.keystore.AndroidKeyStoreProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.camera.iris.IIrisService;
import com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback;
import com.samsung.android.camera.iris.IIrisServiceReceiver;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes5.dex */
public class SemIrisManager {
    public static final String CLIENTSPEC_KEY_ALLOW_INDEXES = "request_template_index_list";
    public static final String CLIENTSPEC_KEY_USE_MANUAL_TIMEOUT = "useManualTimeout";
    public static final String CLIENT_KEY_PRIVILEGED_ATTR = "privileged_attr";
    public static final int ENABLE_IMAGE_CALLBACK = 50000;
    public static final int FRONT_SENSOR_ORIENTATION = 50002;
    public static final int IRIS_ACQUIRED_CHANGE_YOUR_POSITION = 12;
    public static final int IRIS_ACQUIRED_DUPLICATED_SCANNED_IMAGE = 1002;
    public static final int IRIS_ACQUIRED_EYE_NOT_PRESENT = 10;
    public static final int IRIS_ACQUIRED_GOOD = 0;
    public static final int IRIS_ACQUIRED_INSUFFICIENT = 2;
    public static final int IRIS_ACQUIRED_MOVE_CLOSER = 3;
    public static final int IRIS_ACQUIRED_MOVE_DOWN = 8;
    public static final int IRIS_ACQUIRED_MOVE_FARTHER = 4;
    public static final int IRIS_ACQUIRED_MOVE_LEFT = 5;
    public static final int IRIS_ACQUIRED_MOVE_RIGHT = 6;
    public static final int IRIS_ACQUIRED_MOVE_SOMEWHERE_DARKER = 11;
    public static final int IRIS_ACQUIRED_MOVE_UP = 7;
    public static final int IRIS_ACQUIRED_OPEN_EYES_WIDER = 9;
    public static final int IRIS_ACQUIRED_PARTIAL = 1;
    public static final int IRIS_AUTH_TYPE_NONE = 0;
    public static final int IRIS_AUTH_TYPE_PREVIEW_CALLBACK = 1;
    public static final int IRIS_AUTH_TYPE_UI_NO_PREVIEW = 3;
    public static final int IRIS_AUTH_TYPE_UI_WITH_PREVIEW = 2;
    public static final int IRIS_DISABLE_PREVIEW_CALLBACK = 7;
    public static final int IRIS_ENABLE_PREVIEW_CALLBACK = 6;
    public static final int IRIS_ERROR_AUTH_VIEW_SIZE = 10;
    public static final int IRIS_ERROR_AUTH_WINDOW_TOKEN = 11;
    public static final int IRIS_ERROR_CANCELED = 4;
    public static final int IRIS_ERROR_EVICTED = 13;
    public static final int IRIS_ERROR_EVICTED_CAMERA_IN_USE = 19;
    public static final int IRIS_ERROR_EVICTED_DUE_TO_VIDEO_CALL = 14;
    public static final int IRIS_ERROR_EYE_SAFETY_TIMEOUT = 9;
    public static final int IRIS_ERROR_FLIP_OFF = 17;
    public static final int IRIS_ERROR_HW_UNAVAILABLE = 0;
    public static final int IRIS_ERROR_LOCKOUT = 6;
    public static final int IRIS_ERROR_LOCKOUT_PERMANENT = 16;
    public static final int IRIS_ERROR_NEED_SET_LOCK_TYPE = 18;
    public static final int IRIS_ERROR_NEED_TO_RETRY = 5000;
    public static final int IRIS_ERROR_NO_EYE_DETECTED = 15;
    public static final int IRIS_ERROR_NO_SPACE = 3;
    public static final int IRIS_ERROR_OPEN_IR_CAMERA_FAIL = 8;
    public static final int IRIS_ERROR_PROXIMITY_ALERT = 123;
    public static final int IRIS_ERROR_PROXIMITY_TIMEOUT = 12;
    public static final int IRIS_ERROR_START_IR_CAMERA_PREVIEW_FAIL = 7;
    public static final int IRIS_ERROR_TIMEOUT = 2;
    public static final int IRIS_ERROR_UNABLE_TO_PROCESS = 1;
    public static final int IRIS_ERROR_UNABLE_TO_REMOVE = 5;
    public static final int IRIS_ERROR_UNSUPPORTED_ORIENTATION = 20;
    public static final int IRIS_ERROR_USER_CANCELED = 21;
    public static final int IRIS_INVISIBLE_PREVIEW = 4;
    public static final int IRIS_ONE_EYE = 40000;
    public static final int IRIS_REQUEST_DVFS_FREQUENCY = 1004;
    public static final int IRIS_REQUEST_ENROLL_SESSION = 1002;
    public static final int IRIS_REQUEST_ENUMERATE = 11;
    public static final int IRIS_REQUEST_FACTORY_TEST_ALWAYS_LED_ON = 2001;
    public static final int IRIS_REQUEST_FACTORY_TEST_CAMERA_VERSION = 2004;
    public static final int IRIS_REQUEST_FACTORY_TEST_CAPTURE = 2002;
    public static final int IRIS_REQUEST_FACTORY_TEST_FULL_PREVIEW = 2000;
    public static final int IRIS_REQUEST_FACTORY_TEST_PREVIEW_MODE = 2003;
    public static final int IRIS_REQUEST_GET_IR_IDS = 1003;
    public static final int IRIS_REQUEST_GET_UNIQUE_ID = 7;
    public static final int IRIS_REQUEST_GET_VERSION = 4;
    public static final int IRIS_REQUEST_IR_PREVIEW_ENABLE = 2005;
    public static final int IRIS_REQUEST_LOCKOUT = 1001;
    public static final int IRIS_REQUEST_PROCESS_FIDO = 9;
    public static final int IRIS_REQUEST_REMOVE_IRIS = 1000;
    public static final int IRIS_REQUEST_SESSION_OPEN = 2;
    public static final int IRIS_REQUEST_UPDATE_SID = 10;
    public static final int IRIS_TWO_EYES = 40001;
    public static final int IRIS_VISIBLE_PREVIEW = 5;
    public static final int IR_SENSOR_ORIENTATION = 50001;
    private static final String MANAGE_IRIS = "com.samsung.android.camera.iris.permission.MANAGE_IRIS";
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_AUTHENTICATION_SUCCEEDED_FIDO_RESULT_DATA = 107;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_IR_IMAGE = 106;
    private static final int MSG_REMOVED = 105;
    public static final int PRIVILEGED_ATTR_EXCLUSIVE_IDENTIFY = 4;
    public static final int PRIVILEGED_ATTR_EXTRA_EVENT = 16;
    public static final int PRIVILEGED_ATTR_IRIS_DETECTION = 8;
    public static final int PRIVILEGED_ATTR_NO_LOCKOUT = 2;
    public static final int PRIVILEGED_ATTR_NO_VIBRATION = 1;
    public static final int PRIVILEGED_TYPE_KEYGUARD = Integer.MIN_VALUE;
    public static final int SENSOR_STATUS_ERROR = 100042;
    public static final int SENSOR_STATUS_LED_OFF = 30001;
    public static final int SENSOR_STATUS_LED_ON = 30000;
    public static final int SENSOR_STATUS_OK = 100040;
    public static final int SENSOR_STATUS_SECURE_DISABLE = 20001;
    public static final int SENSOR_STATUS_SECURE_ENALBE = 20000;
    public static final int SENSOR_STATUS_WORKING = 100041;
    private static final String SYSTEM_FEATURE_IRIS = "com.samsung.android.camera.iris";
    private static final String TAG = "SemIrisManager";
    private static final String USE_IRIS = "com.samsung.android.camera.iris.permission.USE_IRIS";
    private static SemIrisManager mSemIrisManager = null;
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private GetterHandler mGetterHandler;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Iris mRemovalIris;
    private RequestCallback mRequestCallback;
    private IIrisService mService;
    private IBinder mToken = new Binder();
    private long mAuthBegin = 0;
    private IIrisServiceReceiver mServiceReceiver = new IIrisServiceReceiver.Stub() { // from class: com.samsung.android.camera.iris.SemIrisManager.4
        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining) {
            SemIrisManager.this.mHandler.obtainMessage(100, remaining, 0, new Iris(null, groupId, irisId, deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAcquired(long deviceId, int acquireInfo) {
            SemIrisManager.this.mHandler.obtainMessage(101, acquireInfo, 0, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationSucceeded(long deviceId, Iris ir, byte[] fidoResultData) {
            SemIrisManager.this.mHandler.obtainMessage(107, fidoResultData).sendToTarget();
            SemIrisManager.this.mHandler.obtainMessage(102, ir).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationFailed(long deviceId) {
            SemIrisManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onError(long deviceId, int error) {
            SemIrisManager.this.mHandler.obtainMessage(104, error, 0, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onRemoved(long deviceId, int irisId, int groupId) {
            SemIrisManager.this.mHandler.obtainMessage(105, irisId, groupId, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onIRImage(long deviceId, byte[] irisImage, int width, int height) {
            SemIrisManager.this.mHandler.obtainMessage(106, width, height, irisImage).sendToTarget();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private OnEnrollCancelListener() {
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            SemIrisManager.this.cancelEnrollment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private CryptoObject mCrypto;

        public OnAuthenticationCancelListener(CryptoObject crypto) {
            this.mCrypto = crypto;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            SemIrisManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    /* loaded from: classes5.dex */
    public static final class CryptoObject {
        private final Object mCrypto;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData = null;

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mCrypto = signature;
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mCrypto = cipher;
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mCrypto = mac;
            this.mFidoRequestData = fidoRequestData;
        }

        public Signature getSignature() {
            Object obj = this.mCrypto;
            if (obj instanceof Signature) {
                return (Signature) obj;
            }
            return null;
        }

        public Cipher getCipher() {
            Object obj = this.mCrypto;
            if (obj instanceof Cipher) {
                return (Cipher) obj;
            }
            return null;
        }

        public Mac getMac() {
            Object obj = this.mCrypto;
            if (obj instanceof Mac) {
                return (Mac) obj;
            }
            return null;
        }

        public long getOpId() {
            Object obj = this.mCrypto;
            if (obj != null) {
                return AndroidKeyStoreProvider.getKeyStoreOperationHandle(obj);
            }
            return 0L;
        }

        public byte[] getFidoRequestData() {
            return this.mFidoRequestData;
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    /* loaded from: classes5.dex */
    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Iris mIris;

        public AuthenticationResult(CryptoObject crypto, Iris iris) {
            this.mCryptoObject = crypto;
            this.mIris = iris;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Iris getIris() {
            return this.mIris;
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

        public void onAuthenticationAcquired(int acquireInfo) {
        }

        public void onIRImage(byte[] irisImage, int width, int height) {
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {
        }

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onEnrollmentProgress(int remaining) {
        }

        public void onIRImage(byte[] irisImage, int width, int height) {
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class RemovalCallback {
        public void onRemovalError(Iris ir, int errMsgId, CharSequence errString) {
        }

        public void onRemovalSucceeded(Iris iris) {
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class RequestCallback {
        public void onRequested(int msgId) {
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, View irisView) {
        authenticate(crypto, cancel, flags, callback, handler, irisView, UserHandle.myUserId());
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, View irisView, int userId) {
        authenticate(crypto, cancel, flags, callback, handler, userId, null, irisView);
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, int userId, Bundle attr, View irisView) {
        String str;
        AuthenticationCallback authenticationCallback;
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel != null) {
            if (!cancel.isCanceled()) {
                cancel.setOnCancelListener(new OnAuthenticationCancelListener(crypto));
            } else {
                Log.w(TAG, "authentication already canceled");
                return;
            }
        }
        if (ensureServiceConnected() && this.mService != null) {
            try {
                useHandler(handler);
                byte[] bArr = null;
                this.mEnrollmentCallback = null;
                this.mAuthenticationCallback = callback;
                this.mCryptoObject = crypto;
                long sessionId = crypto != null ? crypto.getOpId() : 0L;
                if (crypto != null) {
                    bArr = this.mCryptoObject.getFidoRequestData();
                }
                byte[] fidoRequestData = bArr;
                if (irisView == null) {
                    this.mService.authenticate(this.mToken, null, 0, 0, 0, 0, sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
                    return;
                }
                this.mAuthBegin = System.currentTimeMillis();
                str = TAG;
                authenticationCallback = callback;
                try {
                    checkAuthViewWindowToken(crypto, cancel, flags, callback, handler, userId, attr, irisView, sessionId, fidoRequestData);
                } catch (RemoteException e) {
                    Log.w(str, "Remote exception while authenticating");
                    authenticationCallback.onAuthenticationError(1, getErrorString(1));
                }
            } catch (RemoteException e2) {
                str = TAG;
                authenticationCallback = callback;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAuthViewWindowToken(final CryptoObject crypto, final CancellationSignal cancel, final int flags, final AuthenticationCallback callback, final Handler handler, final int userId, final Bundle attr, final View irisView, final long sessionId, final byte[] fidoRequestData) {
        AuthenticationCallback authenticationCallback;
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (irisView.getWindowToken() == null) {
            Log.e(TAG, "checking AuthViewWindowToken");
            if (System.currentTimeMillis() - this.mAuthBegin >= JobInfo.MIN_BACKOFF_MILLIS) {
                Log.e(TAG, "checkAuthViewWindowToken is null");
                this.mGetterHandler.removeAllGetterCallbacks();
                if (callback != null) {
                    callback.onAuthenticationError(1, getErrorString(1));
                    return;
                }
                return;
            }
            this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.1
                @Override // java.lang.Runnable
                public void run() {
                    SemIrisManager.this.checkAuthViewWindowToken(crypto, cancel, flags, callback, handler, userId, attr, irisView, sessionId, fidoRequestData);
                }
            });
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder mWindowToken = irisView.getWindowToken();
            int[] position = new int[2];
            try {
                irisView.getLocationInWindow(position);
                if (this.mToken == null) {
                    Log.e(TAG, "mToken null");
                }
                Size mAuthViewSize = getMinimumIrisViewSize();
                if (irisView.getWidth() >= mAuthViewSize.getWidth()) {
                    if (irisView.getHeight() >= mAuthViewSize.getHeight()) {
                        authenticationCallback = callback;
                        this.mService.authenticate(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
                    }
                }
                authenticationCallback = callback;
                if (authenticationCallback != null) {
                    try {
                        Log.e(TAG, "Invalid irisView size. IrisView's proper size:" + mAuthViewSize.getWidth() + "x" + mAuthViewSize.getHeight() + ", but app's size:" + irisView.getWidth() + "x" + irisView.getHeight());
                    } catch (RemoteException e) {
                        Log.w(TAG, "Remote exception while authenticating");
                        if (authenticationCallback != null) {
                            authenticationCallback.onAuthenticationError(1, getErrorString(1));
                            return;
                        }
                        return;
                    }
                }
                this.mService.authenticate(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr, fidoRequestData);
            } catch (RemoteException e2) {
                authenticationCallback = callback;
            }
        } catch (RemoteException e3) {
            authenticationCallback = callback;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class GetterHandler extends Handler {
        private static final int IMAGE_GETTER_CALLBACK = 1;

        public GetterHandler(Context context) {
            super(context.getMainLooper());
        }

        private GetterHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ((Runnable) message.obj).run();
                    return;
                default:
                    return;
            }
        }

        public void postGetterCallback(Runnable callback) {
            postDelayedGetterCallback(callback, 10L);
        }

        public void postDelayedGetterCallback(Runnable callback, long delay) {
            if (callback == null) {
                throw new NullPointerException();
            }
            Message message = Message.obtain();
            message.what = 1;
            message.obj = callback;
            sendMessageDelayed(message, delay);
        }

        public void removeAllGetterCallbacks() {
            removeMessages(1);
        }
    }

    public void enroll(byte[] token, CancellationSignal cancel, int flags, EnrollmentCallback callback, View irisView) {
        enroll(token, cancel, flags, getCurrentUserId(), callback, null, irisView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, int i2, EnrollmentCallback enrollmentCallback, Bundle bundle, View view) {
        int i3;
        String str;
        if (i2 != -2) {
            i3 = i2;
        } else {
            i3 = getCurrentUserId();
        }
        if (enrollmentCallback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        ?? r8 = TAG;
        if (cancellationSignal != null) {
            if (!cancellationSignal.isCanceled()) {
                cancellationSignal.setOnCancelListener(new OnEnrollCancelListener());
            } else {
                Log.w(TAG, "enrollment already canceled");
                return;
            }
        }
        if (ensureServiceConnected() && this.mService != null) {
            try {
                this.mAuthenticationCallback = null;
                this.mEnrollmentCallback = enrollmentCallback;
                if (view == null) {
                    Log.v(TAG, "irisView null");
                    this.mService.enroll(this.mToken, null, 0, 0, 0, 0, bArr, i3, this.mServiceReceiver, i, this.mContext.getOpPackageName(), bundle);
                    r8 = r8;
                } else {
                    Log.v(TAG, "irisView not null");
                    int i4 = i3;
                    str = TAG;
                    View view2 = view;
                    try {
                        checkEnrollViewWindowToken(bArr, cancellationSignal, i, i4, enrollmentCallback, bundle, view2);
                        r8 = view2;
                    } catch (RemoteException e) {
                        Log.w(str, "Remote exception in enroll");
                        enrollmentCallback.onEnrollmentError(1, getErrorString(1));
                    }
                }
            } catch (RemoteException e2) {
                str = r8;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEnrollViewWindowToken(final byte[] token, final CancellationSignal cancel, final int flags, final int userId, final EnrollmentCallback callback, final Bundle attr, final View irisView) {
        Log.v(TAG, "checkEnrollViewWindowToken");
        if (this.mGetterHandler == null) {
            this.mGetterHandler = new GetterHandler(Looper.getMainLooper());
        }
        if (irisView.getWindowToken() == null) {
            Log.v(TAG, "check, irisView.getWindowToken() is null");
            this.mGetterHandler.postGetterCallback(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.2
                @Override // java.lang.Runnable
                public void run() {
                    SemIrisManager.this.checkEnrollViewWindowToken(token, cancel, flags, userId, callback, attr, irisView);
                }
            });
            return;
        }
        this.mGetterHandler.removeAllGetterCallbacks();
        try {
            IBinder mWindowToken = irisView.getWindowToken();
            int[] position = new int[2];
            try {
                irisView.getLocationInWindow(position);
                if (this.mToken == null) {
                    Log.v(TAG, "mToken null");
                }
                Log.v(TAG, "check, irisView.Width=" + irisView.getWidth() + "irisView.Height=" + irisView.getHeight());
                this.mService.enroll(this.mToken, mWindowToken, position[0], position[1], irisView.getWidth(), irisView.getHeight(), token, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName(), attr);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in enroll");
                if (callback != null) {
                    callback.onEnrollmentError(1, getErrorString(1));
                }
            }
        } catch (RemoteException e2) {
        }
    }

    public long preEnroll() {
        IIrisService iIrisService;
        if (!ensureServiceConnected() || (iIrisService = this.mService) == null) {
            return 0L;
        }
        try {
            long result = iIrisService.preEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            Log.w(TAG, "Remote exception in enroll");
            return 0L;
        }
    }

    public int postEnroll() {
        IIrisService iIrisService;
        if (!ensureServiceConnected() || (iIrisService = this.mService) == null) {
            return 0;
        }
        try {
            int result = iIrisService.postEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            Log.w(TAG, "Remote exception in post enroll");
            return 0;
        }
    }

    public void setActiveUser(int userId) {
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.setActiveUser(userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in setActiveUser");
            }
        }
    }

    public void remove(Iris ir, int userId, RemovalCallback callback) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalIris = ir;
                iIrisService.remove(this.mToken, ir.getIrisId(), ir.getGroupId(), userId, this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in remove");
                if (callback != null) {
                    callback.onRemovalError(ir, 1, getErrorString(1));
                }
            }
        }
    }

    public void remove(Iris ir, RemovalCallback callback) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalIris = ir;
                iIrisService.remove(this.mToken, ir.getIrisId(), ir.getGroupId(), getCurrentUserId(), this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception in remove");
                if (callback != null) {
                    callback.onRemovalError(ir, 1, getErrorString(1));
                }
            }
        }
    }

    public void rename(int irId, int userId, String newName) {
        if (!ensureServiceConnected()) {
            return;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.rename(irId, userId, newName);
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in rename()");
                return;
            }
        }
        Log.w(TAG, "rename(): Service not connected!");
    }

    public List<Iris> getEnrolledIrises(int userId) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.getEnrolledIrises(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return null;
    }

    public List<Iris> getEnrolledIrises() {
        return getEnrolledIrises(UserHandle.myUserId());
    }

    public boolean hasEnrolledIrises() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        return false;
    }

    public boolean hasDisabledIris() {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasDisabledIris(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledFaces");
            }
        }
        return false;
    }

    public boolean hasEnrolledIrises(int userId) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                return iIrisService.hasEnrolledIrises(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises, userId : " + userId);
            }
        }
        return false;
    }

    public boolean isHardwareDetected() {
        Log.w(TAG, "isIrisHardwareDetected()");
        Context context = this.mContext;
        if (context != null) {
            return context.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS);
        }
        return false;
    }

    public Size getMinimumIrisViewSize() {
        int width;
        int height;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        int roundDensity = Math.round(displaymetrics.density);
        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            width = displaymetrics.widthPixels / roundDensity;
            height = (int) (width / 1.7777778f);
        } else {
            width = displaymetrics.heightPixels / roundDensity;
            height = (int) (width / 1.7777778f);
        }
        return new Size(width * roundDensity, height * roundDensity);
    }

    public void setIrisViewType(int irisViewType) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.setIrisViewType(UserHandle.myUserId(), this.mContext.getOpPackageName(), irisViewType);
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in setIrisViewType");
            }
        }
    }

    public void enableIRImageCallback(boolean enabled) {
        IIrisService iIrisService;
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                if (enabled) {
                    iIrisService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 6);
                } else {
                    iIrisService.enableIRImageCallback(UserHandle.myUserId(), this.mContext.getOpPackageName(), 7);
                }
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in enableIRImageCallback");
            }
        }
    }

    public SparseArray getEnrolledIrisUniqueID() {
        if (!ensureServiceConnected()) {
            return null;
        }
        SparseArray localSparseArray = new SparseArray();
        List<Iris> irisList = null;
        int index = 1;
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                irisList = iIrisService.getEnrolledIrises(UserHandle.myUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getEnrolledIrises");
            }
        }
        if (irisList == null || irisList.size() <= 0 || this.mContext == null) {
            return null;
        }
        for (Iris ir : irisList) {
            localSparseArray.put(index, byteArrayToHex(requestGetUniqueID(ir.getIrisId(), this.mContext.getOpPackageName())));
            index++;
        }
        return localSparseArray;
    }

    public int request(int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, RequestCallback callback) {
        if (!ensureServiceConnected()) {
            return 0;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            if (inputBuf == null) {
                try {
                    inputBuf = new byte[0];
                } catch (RemoteException e) {
                    Log.v(TAG, "Remote exception in request()");
                    return -2;
                }
            }
            if (outputBuf == null) {
                outputBuf = new byte[0];
            }
            this.mRequestCallback = callback;
            return iIrisService.request(this.mToken, cmd, inputBuf, outputBuf, inParam, getCurrentUserId(), this.mServiceReceiver);
        }
        Log.w(TAG, "request(): Service not connected!");
        return -2;
    }

    public boolean isEnrollSession() {
        int ret = request(1002, null, null, 0, null);
        return ret > 0;
    }

    public boolean requestSessionOpen() {
        if (request(2, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public byte[] requestGetVersion() {
        byte[] outBuf = new byte[256];
        int size = request(4, null, outBuf, 0, null);
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    private byte[] requestGetUniqueID(int irisId, String packageName) {
        if (!ensureServiceConnected()) {
            return null;
        }
        byte[] outBuf = new byte[256];
        int size = 0;
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                size = iIrisService.request(this.mToken, 7, packageName.getBytes(), outBuf, irisId, UserHandle.myUserId(), this.mServiceReceiver);
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in request()");
            }
        }
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    public byte[] requestProcessFIDO(byte[] inBuf) {
        byte[] outBuf = new byte[10240];
        int size = request(9, inBuf, outBuf, 0, null);
        if (size <= 0) {
            return null;
        }
        return Arrays.copyOf(outBuf, size);
    }

    public boolean requestUpdateSID(byte[] sId) {
        if (request(10, sId, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestLedOn() {
        if (request(2001, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestFullPreview() {
        if (request(2000, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestPreviewMode() {
        if (request(2003, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestCapture() {
        if (request(2002, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public boolean requestCameraVersion() {
        if (request(2004, null, null, 0, null) < 0) {
            return false;
        }
        return true;
    }

    public long getAuthenticatorId() {
        if (!ensureServiceConnected()) {
            return 0L;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                return iIrisService.getAuthenticatorId(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in getAuthenticatorId()");
            }
        } else {
            Log.w(TAG, "getAuthenticatorId(): Service not connected!");
        }
        return 0L;
    }

    public void resetTimeout(byte[] token) {
        if (!ensureServiceConnected()) {
            return;
        }
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.resetTimeout(token);
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in resetTimeout()");
                return;
            }
        }
        Log.w(TAG, "resetTimeout(): Service not connected!");
    }

    public void addLockoutResetCallback(final LockoutResetCallback callback) {
        if (!ensureServiceConnected()) {
            return;
        }
        if (this.mService != null) {
            try {
                final PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mService.addLockoutResetCallback(new IIrisServiceLockoutResetCallback.Stub() { // from class: com.samsung.android.camera.iris.SemIrisManager.3
                    @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
                    public void onLockoutReset(long deviceId) throws RemoteException {
                        final PowerManager.WakeLock wakeLock = powerManager.newWakeLock(1, "lockoutResetCallback");
                        wakeLock.acquire();
                        SemIrisManager.this.mHandler.post(new Runnable() { // from class: com.samsung.android.camera.iris.SemIrisManager.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    callback.onLockoutReset();
                                } finally {
                                    wakeLock.release();
                                }
                            }
                        });
                    }
                });
                return;
            } catch (RemoteException e) {
                Log.v(TAG, "Remote exception in addLockoutResetCallback()");
                return;
            }
        }
        Log.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    sendEnrollResult((Iris) msg.obj, msg.arg1);
                    return;
                case 101:
                    sendAcquiredResult(((Long) msg.obj).longValue(), msg.arg1);
                    return;
                case 102:
                    sendAuthenticatedSucceeded((Iris) msg.obj);
                    return;
                case 103:
                    sendAuthenticatedFailed();
                    return;
                case 104:
                    sendErrorResult(((Long) msg.obj).longValue(), msg.arg1);
                    return;
                case 105:
                    sendRemovedResult(((Long) msg.obj).longValue(), msg.arg1, msg.arg2);
                    return;
                case 106:
                    sendIRImage((byte[]) msg.obj, msg.arg1, msg.arg2);
                    return;
                case 107:
                    sendAuthenticatedSucceededFidoResultData((byte[]) msg.obj);
                    return;
                default:
                    return;
            }
        }

        private void sendIRImage(byte[] irisImage, int width, int height) {
            Log.w(SemIrisManager.TAG, "sendIRImage, width : " + width + " height : " + height);
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onIRImage(irisImage, width, height);
            }
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onIRImage(irisImage, width, height);
            }
        }

        private void sendRemovedResult(long deviceId, int irisId, int groupId) {
            if (SemIrisManager.this.mRemovalCallback != null) {
                int reqIrisId = SemIrisManager.this.mRemovalIris.getIrisId();
                int reqGroupId = SemIrisManager.this.mRemovalIris.getGroupId();
                if (irisId != reqIrisId) {
                    Log.w(SemIrisManager.TAG, "Iris id didn't match: " + irisId + " != " + reqIrisId);
                }
                if (groupId != reqGroupId) {
                    Log.w(SemIrisManager.TAG, "Group id didn't match: " + groupId + " != " + reqGroupId);
                }
                SemIrisManager.this.mRemovalCallback.onRemovalSucceeded(SemIrisManager.this.mRemovalIris);
            }
        }

        private void sendErrorResult(long deviceId, int errMsgId) {
            Log.w(SemIrisManager.TAG, "sendErrorResult, errMsgId : " + errMsgId);
            if (errMsgId == 4) {
                return;
            }
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentError(errMsgId, SemIrisManager.this.getErrorString(errMsgId));
            } else if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationError(errMsgId, SemIrisManager.this.getErrorString(errMsgId));
            } else if (SemIrisManager.this.mRemovalCallback != null) {
                SemIrisManager.this.mRemovalCallback.onRemovalError(SemIrisManager.this.mRemovalIris, errMsgId, SemIrisManager.this.getErrorString(errMsgId));
            }
        }

        private void sendEnrollResult(Iris ir, int remaining) {
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentProgress(remaining);
            }
        }

        private void sendAuthenticatedSucceededFidoResultData(byte[] fidoResultData) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceededFidoResultData, fidoResultData : " + Arrays.toString(fidoResultData));
            if (SemIrisManager.this.mCryptoObject != null) {
                SemIrisManager.this.mCryptoObject.setFidoResultData(fidoResultData);
            }
        }

        private void sendAuthenticatedSucceeded(Iris ir) {
            Log.w(SemIrisManager.TAG, "sendAuthenticatedSucceeded, ir : " + ir);
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                AuthenticationResult result = new AuthenticationResult(SemIrisManager.this.mCryptoObject, ir);
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationSucceeded(result);
            }
        }

        private void sendAuthenticatedFailed() {
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationFailed();
            }
        }

        private void sendAcquiredResult(long deviceId, int acquireInfo) {
            if (SemIrisManager.this.mAuthenticationCallback != null) {
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationAcquired(acquireInfo);
            }
            String msg = SemIrisManager.this.getAcquiredString(acquireInfo);
            if (msg == null) {
                return;
            }
            if (SemIrisManager.this.mEnrollmentCallback != null) {
                SemIrisManager.this.mEnrollmentCallback.onEnrollmentHelp(acquireInfo, msg);
            } else {
                if (SemIrisManager.this.mAuthenticationCallback == null || msg == null) {
                    return;
                }
                SemIrisManager.this.mAuthenticationCallback.onAuthenticationHelp(acquireInfo, msg);
            }
        }
    }

    public SemIrisManager(Context context, IIrisService service) {
        this.mContext = context;
        this.mService = service;
        if (service == null) {
            Log.v(TAG, "SemIrisManagerService was null");
        }
        this.mHandler = new MyHandler(context);
        this.mGetterHandler = new GetterHandler(context);
    }

    private int getCurrentUserId() {
        try {
            return ActivityManagerNative.getDefault().getCurrentUser().id;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get current user id\n");
            return -10000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment() {
        IIrisService iIrisService;
        Log.e(TAG, "cancelEnrollment");
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.cancelEnrollment(this.mToken);
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception while canceling enrollment");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(CryptoObject cryptoObject) {
        IIrisService iIrisService;
        Log.e(TAG, "cancelAuthentication");
        if (ensureServiceConnected() && (iIrisService = this.mService) != null) {
            try {
                iIrisService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.w(TAG, "Remote exception while canceling authentication");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getErrorString(int errMsg) {
        Resources mRes;
        PackageManager mPm = this.mContext.getPackageManager();
        try {
            mRes = mPm.getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getErrorString, Exception = " + e);
            mRes = null;
        }
        if (mRes == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        try {
            switch (errMsg) {
                case 0:
                    return mRes.getString(mRes.getIdentifier("iris_error_sensor_no_response", "string", "com.samsung.android.server.iris"));
                case 1:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
                case 2:
                    return mRes.getString(mRes.getIdentifier("iris_error_timeout", "string", "com.samsung.android.server.iris"));
                case 3:
                    return mRes.getString(mRes.getIdentifier("iris_error_no_space", "string", "com.samsung.android.server.iris"));
                case 4:
                    return mRes.getString(mRes.getIdentifier("iris_error_canceled", "string", "com.samsung.android.server.iris"));
                case 5:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_remove", "string", "com.samsung.android.server.iris"));
                case 6:
                    return mRes.getString(mRes.getIdentifier("iris_error_lockout", "string", "com.samsung.android.server.iris"));
                case 7:
                    return "";
                case 8:
                    return "";
                case 9:
                    return mRes.getString(mRes.getIdentifier("iris_error_eye_safety_timeout", "string", "com.samsung.android.server.iris"));
                case 10:
                    return mRes.getString(mRes.getIdentifier("iris_error_auth_view_size", "string", "com.samsung.android.server.iris"));
                case 12:
                    return mRes.getString(mRes.getIdentifier("iris_error_proximity_timeout", "string", "com.samsung.android.server.iris"));
                case 13:
                    return mRes.getString(mRes.getIdentifier("iris_error_evicted", "string", "com.samsung.android.server.iris"));
                case 14:
                    return mRes.getString(mRes.getIdentifier("iris_error_video_call_interrupt", "string", "com.samsung.android.server.iris"));
                case 15:
                    return mRes.getString(mRes.getIdentifier("iris_error_no_eye_detected", "string", "com.samsung.android.server.iris"));
                case 17:
                    return mRes.getString(mRes.getIdentifier("iris_error_flip_off", "string", "com.samsung.android.server.iris"));
                case 18:
                    return mRes.getString(mRes.getIdentifier("iris_error_need_set_lock_type", "string", "com.samsung.android.server.iris"));
                case 19:
                    return mRes.getString(mRes.getIdentifier("iris_error_while_camera_in_use", "string", "com.samsung.android.server.iris"));
                case 20:
                    return mRes.getString(mRes.getIdentifier("iris_error_unsupported_orientation", "string", "com.samsung.android.server.iris"));
                case 25:
                    return null;
                case 123:
                    return mRes.getString(mRes.getIdentifier("iris_error_proximity_alert", "string", "com.samsung.android.server.iris"));
                default:
                    return mRes.getString(mRes.getIdentifier("iris_error_unable_to_process", "string", "com.samsung.android.server.iris"));
            }
        } catch (Resources.NotFoundException e2) {
            Log.d(TAG, "getErrorString, NotFoundException = " + e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAcquiredString(int acquireInfo) {
        Resources mRes;
        PackageManager mPm = this.mContext.getPackageManager();
        try {
            mRes = mPm.getResourcesForApplication("com.samsung.android.server.iris");
        } catch (Exception e) {
            Log.e(TAG, "getAcquiredString, Exception = " + e);
            mRes = null;
        }
        if (mRes == null) {
            Log.e(TAG, "mRes is null");
            return null;
        }
        try {
            switch (acquireInfo) {
                case 3:
                    return mRes.getString(mRes.getIdentifier("iris_acquired_move_closer", "string", "com.samsung.android.server.iris"));
                case 4:
                    return mRes.getString(mRes.getIdentifier("iris_acquired_move_farther", "string", "com.samsung.android.server.iris"));
                case 9:
                    return mRes.getString(mRes.getIdentifier("iris_acquired_open_wider", "string", "com.samsung.android.server.iris"));
                case 11:
                    return mRes.getString(mRes.getIdentifier("iris_acquired_move_somewhere_darker", "string", "com.samsung.android.server.iris"));
                case 12:
                    return mRes.getString(mRes.getIdentifier("iris_acquired_change_your_position", "string", "com.samsung.android.server.iris"));
                default:
                    return null;
            }
        } catch (Resources.NotFoundException e2) {
            Log.d(TAG, "getAcquiredString, NotFoundException = " + e2);
            return null;
        }
    }

    public static synchronized SemIrisManager getSemIrisManager(Context context) {
        synchronized (SemIrisManager.class) {
            if (!context.getPackageManager().hasSystemFeature(SYSTEM_FEATURE_IRIS)) {
                return null;
            }
            if (mSemIrisManager == null) {
                mSemIrisManager = new SemIrisManager(context);
            }
            return mSemIrisManager;
        }
    }

    public SemIrisManager(Context context) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        this.mGetterHandler = new GetterHandler(context);
    }

    public IIrisService getService() {
        ensureServiceConnected();
        return this.mService;
    }

    private synchronized boolean ensureServiceConnected() {
        IIrisService iIrisService = this.mService;
        if (iIrisService != null) {
            try {
                iIrisService.isHardwareDetected(0L, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    this.mService = null;
                }
            }
        }
        if (this.mService == null) {
            startIrisService();
            waitForService();
        }
        return this.mService != null;
    }

    private void startIrisService() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.samsung.android.server.iris", "com.samsung.android.server.iris.IrisService"));
            this.mContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (Exception e) {
            Log.e(TAG, "Starting startIrisService failed: " + e);
        }
    }

    private void waitForService() {
        for (int count = 1; count <= 20; count++) {
            IIrisService asInterface = IIrisService.Stub.asInterface(ServiceManager.getService("samsung.iris"));
            this.mService = asInterface;
            if (asInterface != null) {
                Log.v(TAG, "Service connected!");
                return;
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
            }
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (byte b : byteArray) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    private static String bytesToString(byte[] a, int len) {
        if (len > a.length || len < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%c", Integer.valueOf(a[i] & 255)));
        }
        return sb.toString();
    }
}

package android.hardware.biometrics;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.IAuthService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.security.identity.IdentityCredential;
import android.security.identity.PresentationSession;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;

/* loaded from: classes2.dex */
public class BiometricPrompt implements BiometricAuthenticator, BiometricConstants {
    public static final int AUTHENTICATION_RESULT_TYPE_BIOMETRIC = 2;
    public static final int AUTHENTICATION_RESULT_TYPE_DEVICE_CREDENTIAL = 1;
    private static final boolean DEBUG = Debug.semIsProductDev();
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRMED = 1;
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRM_NOT_REQUIRED = 4;
    public static final int DISMISSED_REASON_CONTENT_VIEW_MORE_OPTIONS = 8;
    public static final int DISMISSED_REASON_CREDENTIAL_CONFIRMED = 7;
    public static final int DISMISSED_REASON_ERROR = 5;
    public static final int DISMISSED_REASON_NEGATIVE = 2;
    public static final int DISMISSED_REASON_SERVER_REQUESTED = 6;
    public static final int DISMISSED_REASON_USER_CANCEL = 3;
    public static final int HIDE_DIALOG_DELAY = 2000;
    static final int MAX_LOGO_DESCRIPTION_CHARACTER_NUMBER = 30;
    public static final int SEM_FLAG_BIOMETRIC_IDENITIFIER_ID = 2;
    public static final int SEM_FLAG_CHECK_ENROLLED_BIOMETRIC = 1;
    public static final int SEM_PRIVILEGED_FLAG_AVOID_LOCKOUT = 4;
    public static final int SEM_PRIVILEGED_FLAG_EXCLUSIVE_AUTHENTICATION = 8;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_INTERNAL = 32;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_ONLY_CONFIRM_BIOMETRIC = 128;
    public static final int SEM_PRIVILEGED_FLAG_KNOX_TWO_FACTOR = 64;
    public static final int SEM_PRIVILEGED_FLAG_USING_FIDO = 16;
    public static final int SEM_TYPE_DEVICE_CUSTOM_SCAN = 8;
    public static final int SEM_TYPE_FACE = 2;
    public static final int SEM_TYPE_FINGERPRINT = 1;
    public static final int SEM_TYPE_IRIS = 4;
    private static final String TAG = "BiometricPrompt";
    private AuthenticationCallback mAuthenticationCallback;
    private final IBiometricServiceReceiver mBiometricServiceReceiver;
    private final ButtonInfo mContentViewMoreOptionsButtonInfo;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private Executor mExecutor;
    private boolean mIsPromptShowing;
    private final ButtonInfo mNegativeButtonInfo;
    private final PromptInfo mPromptInfo;
    private final IAuthService mService;
    private final IBinder mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationResultType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DismissedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemBiometricType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemPrivilegedFlag {
    }

    static class ButtonInfo {
        Executor executor;
        DialogInterface.OnClickListener listener;

        ButtonInfo(Executor ex, DialogInterface.OnClickListener l) {
            this.executor = ex;
            this.listener = l;
        }
    }

    public static class Builder {
        private ButtonInfo mContentViewMoreOptionsButtonInfo;
        private Context mContext;
        private ButtonInfo mNegativeButtonInfo;
        private PromptInfo mPromptInfo = new PromptInfo();
        private IAuthService mService;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setLogoRes(int logoRes) {
            if (this.mPromptInfo.getLogoBitmap() != null) {
                throw new IllegalStateException("Exclusively one of logo resource or logo bitmap can be set");
            }
            if (logoRes != 0) {
                this.mPromptInfo.setLogo(logoRes, BiometricPrompt.convertDrawableToBitmap(this.mContext.getDrawable(logoRes)));
            }
            return this;
        }

        public Builder setLogoBitmap(Bitmap logoBitmap) {
            if (this.mPromptInfo.getLogoRes() != 0) {
                throw new IllegalStateException("Exclusively one of logo resource or logo bitmap can be set");
            }
            this.mPromptInfo.setLogo(0, logoBitmap);
            return this;
        }

        public Builder setLogoDescription(String logoDescription) {
            if (logoDescription == null || logoDescription.isEmpty()) {
                throw new IllegalArgumentException("Logo description passed in can not be null");
            }
            if (logoDescription.length() > 30) {
                Log.w(BiometricPrompt.TAG, "Logo description passed in exceeds30 character number and may be truncated.");
            }
            this.mPromptInfo.setLogoDescription(logoDescription);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.mPromptInfo.setTitle(title);
            return this;
        }

        public Builder setUseDefaultTitle() {
            this.mPromptInfo.setUseDefaultTitle(true);
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle) {
            this.mPromptInfo.setSubtitle(subtitle);
            return this;
        }

        public Builder setUseDefaultSubtitle() {
            this.mPromptInfo.setUseDefaultSubtitle(true);
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mPromptInfo.setDescription(description);
            return this;
        }

        public Builder setContentView(PromptContentView view) {
            this.mPromptInfo.setContentView(view);
            if (this.mPromptInfo.isContentViewMoreOptionsButtonUsed()) {
                this.mContentViewMoreOptionsButtonInfo = ((PromptContentViewWithMoreOptionsButton) view).getButtonInfo();
            }
            return this;
        }

        public Builder setService(IAuthService service) {
            this.mService = service;
            return this;
        }

        public Builder setTextForDeviceCredential(CharSequence title, CharSequence subtitle, CharSequence description) {
            if (title != null) {
                this.mPromptInfo.setDeviceCredentialTitle(title);
            }
            if (subtitle != null) {
                this.mPromptInfo.setDeviceCredentialSubtitle(subtitle);
            }
            if (description != null) {
                this.mPromptInfo.setDeviceCredentialDescription(description);
            }
            return this;
        }

        public Builder setNegativeButton(CharSequence text, Executor executor, DialogInterface.OnClickListener listener) {
            if (TextUtils.isEmpty(text)) {
                throw new IllegalArgumentException("Text must be set and non-empty");
            }
            if (executor == null) {
                throw new IllegalArgumentException("Executor must not be null");
            }
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null");
            }
            this.mPromptInfo.setNegativeButtonText(text);
            this.mNegativeButtonInfo = new ButtonInfo(executor, listener);
            return this;
        }

        public Builder setConfirmationRequired(boolean requireConfirmation) {
            this.mPromptInfo.setConfirmationRequested(requireConfirmation);
            return this;
        }

        @Deprecated
        public Builder setDeviceCredentialAllowed(boolean allowed) {
            this.mPromptInfo.setDeviceCredentialAllowed(allowed);
            return this;
        }

        public Builder setAllowedAuthenticators(int authenticators) {
            this.mPromptInfo.setAuthenticators(authenticators);
            return this;
        }

        public Builder setAllowedSensorIds(List<Integer> sensorIds) {
            this.mPromptInfo.setAllowedSensorIds(sensorIds);
            return this;
        }

        public Builder setAllowBackgroundAuthentication(boolean allow) {
            this.mPromptInfo.setAllowBackgroundAuthentication(allow);
            return this;
        }

        public Builder setAllowBackgroundAuthentication(boolean allow, boolean useParentProfileForDeviceCredential) {
            this.mPromptInfo.setAllowBackgroundAuthentication(allow);
            this.mPromptInfo.setUseParentProfileForDeviceCredential(useParentProfileForDeviceCredential);
            return this;
        }

        public Builder setDisallowBiometricsIfPolicyExists(boolean checkDevicePolicyManager) {
            this.mPromptInfo.setDisallowBiometricsIfPolicyExists(checkDevicePolicyManager);
            return this;
        }

        public Builder setReceiveSystemEvents(boolean set) {
            this.mPromptInfo.setReceiveSystemEvents(set);
            return this;
        }

        public Builder setIgnoreEnrollmentState(boolean ignoreEnrollmentState) {
            this.mPromptInfo.setIgnoreEnrollmentState(ignoreEnrollmentState);
            return this;
        }

        public Builder setIsForLegacyFingerprintManager(int sensorId) {
            this.mPromptInfo.setIsForLegacyFingerprintManager(sensorId);
            return this;
        }

        public Builder setShowEmergencyCallButton(boolean showEmergencyCallButton) {
            this.mPromptInfo.setShowEmergencyCallButton(showEmergencyCallButton);
            return this;
        }

        public Builder setComponentNameForConfirmDeviceCredentialActivity(ComponentName componentNameForConfirmDeviceCredentialActivity) {
            this.mPromptInfo.setComponentNameForConfirmDeviceCredentialActivity(componentNameForConfirmDeviceCredentialActivity);
            return this;
        }

        public Builder semSetBiometricType(int type) {
            if (this.mContext.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
                throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
            }
            this.mPromptInfo.semSetBiometricType(type);
            return this;
        }

        public Builder semSetPrivilegedFlag(int flag) {
            if (this.mContext.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
                throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
            }
            this.mPromptInfo.semSetPrivilegedFlag(flag);
            return this;
        }

        public BiometricPrompt build() {
            IAuthService iAuthService;
            CharSequence title = this.mPromptInfo.getTitle();
            CharSequence negative = this.mPromptInfo.getNegativeButtonText();
            boolean useDefaultTitle = this.mPromptInfo.isUseDefaultTitle();
            boolean deviceCredentialAllowed = this.mPromptInfo.isDeviceCredentialAllowed();
            int authenticators = this.mPromptInfo.getAuthenticators();
            boolean willShowDeviceCredentialButton = deviceCredentialAllowed || BiometricPrompt.isCredentialAllowed(authenticators);
            if (TextUtils.isEmpty(title) && !useDefaultTitle) {
                throw new IllegalArgumentException("Title must be set and non-empty");
            }
            if (TextUtils.isEmpty(negative) && !willShowDeviceCredentialButton) {
                throw new IllegalArgumentException("Negative text must be set and non-empty");
            }
            if (!TextUtils.isEmpty(negative) && willShowDeviceCredentialButton) {
                throw new IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
            }
            if (this.mService == null) {
                iAuthService = IAuthService.Stub.asInterface(ServiceManager.getService(Context.AUTH_SERVICE));
            } else {
                iAuthService = this.mService;
            }
            this.mService = iAuthService;
            return new BiometricPrompt(this.mContext, this.mPromptInfo, this.mNegativeButtonInfo, this.mContentViewMoreOptionsButtonInfo, this.mService);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            if (!BiometricPrompt.this.mIsPromptShowing) {
                Log.w(BiometricPrompt.TAG, "BP is not showing");
            } else {
                Log.d(BiometricPrompt.TAG, "Cancel BP authentication requested for: " + this.mAuthRequestId);
                BiometricPrompt.this.cancelAuthentication(this.mAuthRequestId);
            }
        }
    }

    /* renamed from: android.hardware.biometrics.BiometricPrompt$1, reason: invalid class name */
    class AnonymousClass1 extends IBiometricServiceReceiver.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationSucceeded(final int authenticationType) {
            Slog.i(BiometricPrompt.TAG, "onAuthenticationSucceeded: " + authenticationType);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationSucceeded$0(authenticationType);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$0(int authenticationType) {
            AuthenticationResult result = new AuthenticationResult(BiometricPrompt.this.mCryptoObject, authenticationType);
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(result);
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationFailed() {
            Slog.i(BiometricPrompt.TAG, "onAuthenticationFailed");
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationFailed$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFailed$1() {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationFailed();
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onError(int modality, final int error, int vendorCode) {
            Slog.i(BiometricPrompt.TAG, "onError: " + error + ", " + vendorCode);
            String errorMessage = null;
            switch (modality) {
                case 2:
                    errorMessage = FingerprintManager.getErrorString(BiometricPrompt.this.mContext, error, vendorCode);
                    break;
                case 8:
                    errorMessage = FaceManager.getErrorString(BiometricPrompt.this.mContext, error, vendorCode);
                    break;
            }
            if (errorMessage == null) {
                switch (error) {
                    case 5:
                        errorMessage = BiometricPrompt.this.mContext.getString(R.string.biometric_error_canceled);
                        break;
                    case 10:
                        errorMessage = BiometricPrompt.this.mContext.getString(R.string.biometric_error_user_canceled);
                        break;
                    case 12:
                        errorMessage = BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable);
                        break;
                    case 14:
                        errorMessage = BiometricPrompt.this.mContext.getString(R.string.biometric_error_device_not_secured);
                        break;
                    default:
                        Log.e(BiometricPrompt.TAG, "Unknown error, modality: " + modality + " error: " + error + " vendorCode: " + vendorCode);
                        errorMessage = BiometricPrompt.this.mContext.getString(R.string.biometric_error_generic);
                        break;
                }
            }
            final String stringToSend = errorMessage;
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onError$2(error, stringToSend);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(int error, String stringToSend) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(error, stringToSend);
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAcquired(final int acquireInfo, final String message) {
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onAcquired$3(acquireInfo, message);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$3(int acquireInfo, String message) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationHelp(acquireInfo, message);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onDialogDismissed(int reason) {
            Slog.i(BiometricPrompt.TAG, "onDialogDismissed: " + reason);
            if (reason == 2) {
                if (BiometricPrompt.this.mNegativeButtonInfo != null) {
                    BiometricPrompt.this.mNegativeButtonInfo.executor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricPrompt.AnonymousClass1.this.lambda$onDialogDismissed$4();
                        }
                    });
                    return;
                } else {
                    BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(10, null);
                    return;
                }
            }
            if (reason == 8) {
                if (BiometricPrompt.this.mContentViewMoreOptionsButtonInfo != null) {
                    BiometricPrompt.this.mContentViewMoreOptionsButtonInfo.executor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricPrompt.AnonymousClass1.this.lambda$onDialogDismissed$5();
                        }
                    });
                }
            } else {
                BiometricPrompt.this.mIsPromptShowing = false;
                Log.e(BiometricPrompt.TAG, "Unknown reason: " + reason);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$4() {
            BiometricPrompt.this.mNegativeButtonInfo.listener.onClick(null, -2);
            BiometricPrompt.this.mIsPromptShowing = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$5() {
            BiometricPrompt.this.mContentViewMoreOptionsButtonInfo.listener.onClick(null, -2);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSystemEvent(final int event) {
            Slog.i(BiometricPrompt.TAG, "onSystemEvent: " + event);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onSystemEvent$6(event);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemEvent$6(int event) {
            BiometricPrompt.this.mAuthenticationCallback.onSystemEvent(event);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSemAuthenticationSucceeded(final int authenticationType, final int id, final byte[] challengeToken) {
            Slog.i(BiometricPrompt.TAG, "onSemAuthenticationSucceeded: " + authenticationType);
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPrompt.AnonymousClass1.this.lambda$onSemAuthenticationSucceeded$7(authenticationType, challengeToken, id);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceeded$7(int authenticationType, byte[] challengeToken, int id) {
            AuthenticationResult result = new AuthenticationResult(BiometricPrompt.this.mCryptoObject, authenticationType);
            if (challengeToken != null) {
                result.setToken(challengeToken);
            }
            result.mBiometricId = id;
            if (BiometricPrompt.DEBUG) {
                Slog.d(BiometricPrompt.TAG, "AuthenticationSucceeded: " + id + ", " + Arrays.toString(challengeToken));
            }
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    private BiometricPrompt(Context context, PromptInfo promptInfo, ButtonInfo negativeButtonInfo, ButtonInfo contentViewMoreOptionsButtonInfo, IAuthService service) {
        this.mToken = new Binder();
        this.mBiometricServiceReceiver = new AnonymousClass1();
        this.mContext = context;
        this.mPromptInfo = promptInfo;
        this.mNegativeButtonInfo = negativeButtonInfo;
        this.mContentViewMoreOptionsButtonInfo = contentViewMoreOptionsButtonInfo;
        this.mService = service;
        this.mIsPromptShowing = false;
    }

    public int getLogoRes() {
        return this.mPromptInfo.getLogoRes();
    }

    public Bitmap getLogoBitmap() {
        return this.mPromptInfo.getLogoBitmap();
    }

    public String getLogoDescription() {
        return this.mPromptInfo.getLogoDescription();
    }

    public CharSequence getTitle() {
        return this.mPromptInfo.getTitle();
    }

    public boolean shouldUseDefaultTitle() {
        return this.mPromptInfo.isUseDefaultTitle();
    }

    public CharSequence getSubtitle() {
        return this.mPromptInfo.getSubtitle();
    }

    public boolean shouldUseDefaultSubtitle() {
        return this.mPromptInfo.isUseDefaultSubtitle();
    }

    public CharSequence getDescription() {
        return this.mPromptInfo.getDescription();
    }

    public PromptContentView getContentView() {
        return this.mPromptInfo.getContentView();
    }

    public CharSequence getNegativeButtonText() {
        return this.mPromptInfo.getNegativeButtonText();
    }

    public boolean isConfirmationRequired() {
        return this.mPromptInfo.isConfirmationRequested();
    }

    public int getAllowedAuthenticators() {
        return this.mPromptInfo.getAuthenticators();
    }

    public List<Integer> getAllowedSensorIds() {
        return this.mPromptInfo.getAllowedSensorIds();
    }

    public boolean isAllowBackgroundAuthentication() {
        return this.mPromptInfo.isAllowBackgroundAuthentication();
    }

    public static final class CryptoObject extends android.hardware.biometrics.CryptoObject {
        public CryptoObject(Signature signature) {
            super(signature);
        }

        public CryptoObject(Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(Mac mac) {
            super(mac);
        }

        @Deprecated
        public CryptoObject(IdentityCredential credential) {
            super(credential);
        }

        public CryptoObject(PresentationSession session) {
            super(session);
        }

        public CryptoObject(KeyAgreement keyAgreement) {
            super(keyAgreement);
        }

        public CryptoObject(long operationHandle) {
            super(operationHandle);
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Signature getSignature() {
            return super.getSignature();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Cipher getCipher() {
            return super.getCipher();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Mac getMac() {
            return super.getMac();
        }

        @Override // android.hardware.biometrics.CryptoObject
        @Deprecated
        public IdentityCredential getIdentityCredential() {
            return super.getIdentityCredential();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public PresentationSession getPresentationSession() {
            return super.getPresentationSession();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public KeyAgreement getKeyAgreement() {
            return super.getKeyAgreement();
        }

        public long getOperationHandle() {
            return super.getOpId();
        }
    }

    public static class AuthenticationResult extends BiometricAuthenticator.AuthenticationResult {
        private int mBiometricId;
        private byte[] mToken;

        public AuthenticationResult(CryptoObject crypto, int authenticationType) {
            super(crypto, authenticationType, null, 0);
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public CryptoObject getCryptoObject() {
            return (CryptoObject) super.getCryptoObject();
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public int getAuthenticationType() {
            return super.getAuthenticationType();
        }

        public int getBiometricId() {
            return this.mBiometricId;
        }

        public void setToken(byte[] token) {
            this.mToken = token;
        }

        public byte[] semGetToken() {
            if (BiometricPrompt.DEBUG) {
                Slog.d(BiometricPrompt.TAG, "semGetToken: token = " + (this.mToken == null ? "NULL" : Integer.valueOf(this.mToken.length)));
            }
            return this.mToken;
        }
    }

    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int acquireInfo) {
        }

        public void onSystemEvent(int event) {
        }
    }

    public void authenticateUser(CancellationSignal cancel, Executor executor, AuthenticationCallback callback, int userId) {
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal(0L, cancel, executor, callback, userId);
    }

    public long authenticateForOperation(CancellationSignal cancel, Executor executor, AuthenticationCallback callback, long operationId) {
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        return authenticateInternal(operationId, cancel, executor, callback, this.mContext.getUserId());
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, Executor executor, AuthenticationCallback callback) {
        FrameworkStatsLog.write(353, true, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (crypto == null) {
            throw new IllegalArgumentException("Must supply a crypto object");
        }
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        int authenticators = this.mPromptInfo.getAuthenticators();
        if (authenticators == 0) {
            authenticators = 15;
        }
        int biometricStrength = authenticators & 255;
        if ((biometricStrength & (-16)) != 0) {
            throw new IllegalArgumentException("Only Strong biometrics supported with crypto");
        }
        authenticateInternal(crypto, cancel, executor, callback, this.mContext.getUserId());
    }

    public void authenticate(CancellationSignal cancel, Executor executor, AuthenticationCallback callback) {
        FrameworkStatsLog.write(353, false, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal((CryptoObject) null, cancel, executor, callback, this.mContext.getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long requestId) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getPackageName(), requestId);
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to cancel authentication", e);
            }
        }
    }

    private void authenticateInternal(CryptoObject crypto, CancellationSignal cancel, Executor executor, AuthenticationCallback callback, int userId) {
        this.mCryptoObject = crypto;
        long operationId = crypto != null ? crypto.getOpId() : 0L;
        authenticateInternal(operationId, cancel, executor, callback, userId);
    }

    private long authenticateInternal(long operationId, CancellationSignal cancel, Executor executor, final AuthenticationCallback callback, int userId) {
        PromptInfo promptInfo;
        if (this.mCryptoObject != null && this.mCryptoObject.getOpId() != operationId) {
            Log.w(TAG, "CryptoObject operation ID does not match argument; setting field to null");
            this.mCryptoObject = null;
        }
        try {
            if (!cancel.isCanceled()) {
                try {
                    this.mExecutor = executor;
                    this.mAuthenticationCallback = callback;
                    if (this.mIsPromptShowing) {
                        final String stringToSend = this.mContext.getString(R.string.biometric_error_canceled);
                        this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BiometricPrompt.this.lambda$authenticateInternal$0(stringToSend);
                            }
                        });
                        return -1L;
                    }
                    if (operationId != 0) {
                        Parcel parcel = Parcel.obtain();
                        this.mPromptInfo.writeToParcel(parcel, 0);
                        parcel.setDataPosition(0);
                        PromptInfo promptInfo2 = new PromptInfo(parcel);
                        if (promptInfo2.getAuthenticators() == 0) {
                            promptInfo2.setAuthenticators(15);
                        }
                        promptInfo = promptInfo2;
                    } else {
                        promptInfo = this.mPromptInfo;
                    }
                    semSetExtraInfo(promptInfo);
                    long authId = this.mService.authenticate(this.mToken, operationId, userId, this.mBiometricServiceReceiver, this.mContext.getPackageName(), promptInfo);
                    try {
                        cancel.setOnCancelListener(new OnAuthenticationCancelListener(authId));
                        this.mIsPromptShowing = true;
                        return authId;
                    } catch (RemoteException e) {
                        e = e;
                        Log.e(TAG, "Remote exception while authenticating", e);
                        this.mExecutor.execute(new Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BiometricPrompt.this.lambda$authenticateInternal$1(callback);
                            }
                        });
                        return -1L;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                }
            } else {
                Log.w(TAG, "Authentication already canceled");
                return -1L;
            }
        } catch (RemoteException e3) {
            e = e3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$0(String stringToSend) {
        this.mAuthenticationCallback.onAuthenticationError(5, stringToSend);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$1(AuthenticationCallback callback) {
        callback.onAuthenticationError(1, this.mContext.getString(R.string.biometric_error_hw_unavailable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCredentialAllowed(int allowedAuthenticators) {
        return (32768 & allowedAuthenticators) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void semAuthenticate(CryptoObject crypto, CancellationSignal cancel, Executor executor, AuthenticationCallback callback, byte[] requestData) {
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        }
        if (requestData != null) {
            this.mPromptInfo.semSetChallengeData(requestData);
        }
        authenticateInternal(crypto, cancel, executor, callback, this.mContext.getUserId());
    }

    private void semSetExtraInfo(PromptInfo promptInfo) {
        int displayId;
        try {
            displayId = this.mContext.getDisplayId();
        } catch (Exception e) {
            Slog.w(TAG, "semSetExtraInfo: " + e.getMessage());
            displayId = 0;
        }
        promptInfo.semSetDisplayId(displayId);
        try {
            if (this.mContext instanceof Activity) {
                promptInfo.semSetTaskId(((Activity) this.mContext).getTaskId());
            }
        } catch (Exception e2) {
            Slog.w(TAG, "semSetExtraInfo: " + e2.getMessage());
        }
    }
}

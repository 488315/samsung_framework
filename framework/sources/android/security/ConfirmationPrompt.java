package android.security;

import android.content.ContentResolver;
import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.security.apc.IConfirmationCallback;
import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class ConfirmationPrompt {
    private static final String TAG = "ConfirmationPrompt";
    private ConfirmationCallback mCallback;
    private final IConfirmationCallback mConfirmationCallback;
    private Context mContext;
    private Executor mExecutor;
    private byte[] mExtraData;
    private final KeyStore mKeyStore;
    private CharSequence mPromptText;
    private AndroidProtectedConfirmation mProtectedConfirmation;

    /* synthetic */ ConfirmationPrompt(Context context, CharSequence charSequence, byte[] bArr, ConfirmationPromptIA confirmationPromptIA) {
        this(context, charSequence, bArr);
    }

    private AndroidProtectedConfirmation getService() {
        if (this.mProtectedConfirmation == null) {
            this.mProtectedConfirmation = new AndroidProtectedConfirmation();
        }
        return this.mProtectedConfirmation;
    }

    public void doCallback(int responseCode, byte[] dataThatWasConfirmed, ConfirmationCallback callback) {
        switch (responseCode) {
            case 0:
                callback.onConfirmed(dataThatWasConfirmed);
                return;
            case 1:
                callback.onDismissed();
                return;
            case 2:
                callback.onCanceled();
                return;
            case 3:
            case 4:
            default:
                callback.onError(new Exception("Unexpected responseCode=" + responseCode + " from onConfirmtionPromptCompleted() callback."));
                return;
            case 5:
                callback.onError(new Exception("System error returned by ConfirmationUI."));
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.security.ConfirmationPrompt$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends IConfirmationCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.security.apc.IConfirmationCallback
        public void onCompleted(int result, byte[] dataThatWasConfirmed) throws RemoteException {
            if (ConfirmationPrompt.this.mCallback != null) {
                ConfirmationCallback callback = ConfirmationPrompt.this.mCallback;
                Executor executor = ConfirmationPrompt.this.mExecutor;
                ConfirmationPrompt.this.mCallback = null;
                ConfirmationPrompt.this.mExecutor = null;
                if (executor == null) {
                    ConfirmationPrompt.this.doCallback(result, dataThatWasConfirmed, callback);
                } else {
                    executor.execute(new Runnable() { // from class: android.security.ConfirmationPrompt.1.1
                        final /* synthetic */ ConfirmationCallback val$callback;
                        final /* synthetic */ byte[] val$dataThatWasConfirmed;
                        final /* synthetic */ int val$result;

                        RunnableC00061(int result2, byte[] dataThatWasConfirmed2, ConfirmationCallback callback2) {
                            result = result2;
                            dataThatWasConfirmed = dataThatWasConfirmed2;
                            callback = callback2;
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            ConfirmationPrompt.this.doCallback(result, dataThatWasConfirmed, callback);
                        }
                    });
                }
            }
        }

        /* renamed from: android.security.ConfirmationPrompt$1$1 */
        /* loaded from: classes3.dex */
        class RunnableC00061 implements Runnable {
            final /* synthetic */ ConfirmationCallback val$callback;
            final /* synthetic */ byte[] val$dataThatWasConfirmed;
            final /* synthetic */ int val$result;

            RunnableC00061(int result2, byte[] dataThatWasConfirmed2, ConfirmationCallback callback2) {
                result = result2;
                dataThatWasConfirmed = dataThatWasConfirmed2;
                callback = callback2;
            }

            @Override // java.lang.Runnable
            public void run() {
                ConfirmationPrompt.this.doCallback(result, dataThatWasConfirmed, callback);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private Context mContext;
        private byte[] mExtraData;
        private CharSequence mPromptText;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setPromptText(CharSequence promptText) {
            this.mPromptText = promptText;
            return this;
        }

        public Builder setExtraData(byte[] extraData) {
            this.mExtraData = extraData;
            return this;
        }

        public ConfirmationPrompt build() {
            if (TextUtils.isEmpty(this.mPromptText)) {
                throw new IllegalArgumentException("prompt text must be set and non-empty");
            }
            byte[] bArr = this.mExtraData;
            if (bArr == null) {
                throw new IllegalArgumentException("extraData must be set");
            }
            return new ConfirmationPrompt(this.mContext, this.mPromptText, bArr);
        }
    }

    private ConfirmationPrompt(Context context, CharSequence promptText, byte[] extraData) {
        this.mKeyStore = KeyStore.getInstance();
        this.mConfirmationCallback = new IConfirmationCallback.Stub() { // from class: android.security.ConfirmationPrompt.1
            AnonymousClass1() {
            }

            @Override // android.security.apc.IConfirmationCallback
            public void onCompleted(int result2, byte[] dataThatWasConfirmed2) throws RemoteException {
                if (ConfirmationPrompt.this.mCallback != null) {
                    ConfirmationCallback callback2 = ConfirmationPrompt.this.mCallback;
                    Executor executor = ConfirmationPrompt.this.mExecutor;
                    ConfirmationPrompt.this.mCallback = null;
                    ConfirmationPrompt.this.mExecutor = null;
                    if (executor == null) {
                        ConfirmationPrompt.this.doCallback(result2, dataThatWasConfirmed2, callback2);
                    } else {
                        executor.execute(new Runnable() { // from class: android.security.ConfirmationPrompt.1.1
                            final /* synthetic */ ConfirmationCallback val$callback;
                            final /* synthetic */ byte[] val$dataThatWasConfirmed;
                            final /* synthetic */ int val$result;

                            RunnableC00061(int result22, byte[] dataThatWasConfirmed22, ConfirmationCallback callback22) {
                                result = result22;
                                dataThatWasConfirmed = dataThatWasConfirmed22;
                                callback = callback22;
                            }

                            @Override // java.lang.Runnable
                            public void run() {
                                ConfirmationPrompt.this.doCallback(result, dataThatWasConfirmed, callback);
                            }
                        });
                    }
                }
            }

            /* renamed from: android.security.ConfirmationPrompt$1$1 */
            /* loaded from: classes3.dex */
            class RunnableC00061 implements Runnable {
                final /* synthetic */ ConfirmationCallback val$callback;
                final /* synthetic */ byte[] val$dataThatWasConfirmed;
                final /* synthetic */ int val$result;

                RunnableC00061(int result22, byte[] dataThatWasConfirmed22, ConfirmationCallback callback22) {
                    result = result22;
                    dataThatWasConfirmed = dataThatWasConfirmed22;
                    callback = callback22;
                }

                @Override // java.lang.Runnable
                public void run() {
                    ConfirmationPrompt.this.doCallback(result, dataThatWasConfirmed, callback);
                }
            }
        };
        this.mContext = context;
        this.mPromptText = promptText;
        this.mExtraData = extraData;
    }

    private int getUiOptionsAsFlags() {
        int uiOptionsAsFlags = 0;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int inversionEnabled = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, 0);
        if (inversionEnabled == 1) {
            uiOptionsAsFlags = 0 | 1;
        }
        float fontScale = Settings.System.getFloat(contentResolver, Settings.System.FONT_SCALE, 1.0f);
        if (fontScale > 1.0d) {
            return uiOptionsAsFlags | 2;
        }
        return uiOptionsAsFlags;
    }

    private static boolean isAccessibilityServiceRunning(Context context) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            int a11yEnabled = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED);
            if (a11yEnabled != 1) {
                return false;
            }
            return true;
        } catch (Settings.SettingNotFoundException e) {
            Log.w(TAG, "Unexpected SettingNotFoundException");
            e.printStackTrace();
            return false;
        }
    }

    public void presentPrompt(Executor executor, ConfirmationCallback callback) throws ConfirmationAlreadyPresentingException, ConfirmationNotAvailableException {
        if (this.mCallback != null) {
            throw new ConfirmationAlreadyPresentingException();
        }
        if (isAccessibilityServiceRunning(this.mContext)) {
            throw new ConfirmationNotAvailableException();
        }
        this.mCallback = callback;
        this.mExecutor = executor;
        String locale = Locale.getDefault().toLanguageTag();
        int uiOptionsAsFlags = getUiOptionsAsFlags();
        int responseCode = getService().presentConfirmationPrompt(this.mConfirmationCallback, this.mPromptText.toString(), this.mExtraData, locale, uiOptionsAsFlags);
        switch (responseCode) {
            case 0:
                return;
            case 3:
                throw new ConfirmationAlreadyPresentingException();
            case 6:
                throw new ConfirmationNotAvailableException();
            default:
                Log.w(TAG, "Unexpected responseCode=" + responseCode + " from presentConfirmationPrompt() call.");
                throw new IllegalArgumentException();
        }
    }

    public void cancelPrompt() {
        int responseCode = getService().cancelConfirmationPrompt(this.mConfirmationCallback);
        if (responseCode == 0) {
            return;
        }
        if (responseCode == 3) {
            throw new IllegalStateException();
        }
        Log.w(TAG, "Unexpected responseCode=" + responseCode + " from cancelConfirmationPrompt() call.");
        throw new IllegalStateException();
    }

    public static boolean isSupported(Context context) {
        if (isAccessibilityServiceRunning(context)) {
            return false;
        }
        return new AndroidProtectedConfirmation().isConfirmationPromptSupported();
    }
}

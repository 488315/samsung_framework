package android.telephony;

import android.annotation.SystemApi;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public class NetworkServiceCallback {
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_FAILED = 5;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String mTag = NetworkServiceCallback.class.getSimpleName();
    private final INetworkServiceCallback mCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    public NetworkServiceCallback(INetworkServiceCallback callback) {
        this.mCallback = callback;
    }

    public void onRequestNetworkRegistrationInfoComplete(int result, NetworkRegistrationInfo state) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onRequestNetworkRegistrationInfoComplete(result, state);
                return;
            } catch (RemoteException e) {
                com.android.telephony.Rlog.e(mTag, "Failed to onRequestNetworkRegistrationInfoComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(mTag, "onRequestNetworkRegistrationInfoComplete callback is null.");
    }
}

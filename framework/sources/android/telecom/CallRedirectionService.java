package android.telecom;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.ICallRedirectionAdapter;
import com.android.internal.telecom.ICallRedirectionService;

/* loaded from: classes3.dex */
public abstract class CallRedirectionService extends Service {
    private static final int MSG_PLACE_CALL = 1;
    private static final int MSG_TIMEOUT = 2;
    public static final String SERVICE_INTERFACE = "android.telecom.CallRedirectionService";
    private ICallRedirectionAdapter mCallRedirectionAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.telecom.CallRedirectionService.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        CallRedirectionService.this.mCallRedirectionAdapter = (ICallRedirectionAdapter) args.arg1;
                        CallRedirectionService.this.onPlaceCall((Uri) args.arg2, (PhoneAccountHandle) args.arg3, ((Boolean) args.arg4).booleanValue());
                        return;
                    } finally {
                        args.recycle();
                    }
                case 2:
                    CallRedirectionService.this.onRedirectionTimeout();
                    return;
                default:
                    return;
            }
        }
    };

    public abstract void onPlaceCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z);

    public void onRedirectionTimeout() {
    }

    public final void placeCallUnmodified() {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.placeCallUnmodified();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void redirectCall(Uri gatewayUri, PhoneAccountHandle targetPhoneAccount, boolean confirmFirst) {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.redirectCall(gatewayUri, targetPhoneAccount, confirmFirst);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void cancelCall() {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.cancelCall();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    private final class CallRedirectionBinder extends ICallRedirectionService.Stub {
        private CallRedirectionBinder() {
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void placeCall(ICallRedirectionAdapter adapter, Uri handle, PhoneAccountHandle initialPhoneAccount, boolean allowInteractiveResponse) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = adapter;
            args.arg2 = handle;
            args.arg3 = initialPhoneAccount;
            args.arg4 = Boolean.valueOf(allowInteractiveResponse);
            CallRedirectionService.this.mHandler.obtainMessage(1, args).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void notifyTimeout() {
            CallRedirectionService.this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new CallRedirectionBinder();
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return false;
    }
}

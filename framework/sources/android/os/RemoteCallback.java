package android.os;

import android.annotation.SystemApi;
import android.os.IRemoteCallback;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class RemoteCallback implements Parcelable {
    public static final Parcelable.Creator<RemoteCallback> CREATOR = new Parcelable.Creator<RemoteCallback>() { // from class: android.os.RemoteCallback.3
        AnonymousClass3() {
        }

        @Override // android.os.Parcelable.Creator
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public RemoteCallback[] newArray(int size) {
            return new RemoteCallback[size];
        }
    };
    private final IRemoteCallback mCallback;
    private final Handler mHandler;
    private final OnResultListener mListener;

    /* loaded from: classes3.dex */
    public interface OnResultListener {
        void onResult(Bundle bundle);
    }

    public RemoteCallback(OnResultListener listener) {
        this(listener, null);
    }

    public RemoteCallback(OnResultListener listener, Handler handler) {
        if (listener == null) {
            throw new NullPointerException("listener cannot be null");
        }
        this.mListener = listener;
        this.mHandler = handler;
        this.mCallback = new IRemoteCallback.Stub() { // from class: android.os.RemoteCallback.1
            AnonymousClass1() {
            }

            @Override // android.os.IRemoteCallback
            public void sendResult(Bundle data) {
                RemoteCallback.this.sendResult(data);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.os.RemoteCallback$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends IRemoteCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(Bundle data) {
            RemoteCallback.this.sendResult(data);
        }
    }

    RemoteCallback(Parcel parcel) {
        this.mListener = null;
        this.mHandler = null;
        this.mCallback = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
    }

    public void sendResult(Bundle result) {
        OnResultListener onResultListener = this.mListener;
        if (onResultListener != null) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.os.RemoteCallback.2
                    final /* synthetic */ Bundle val$result;

                    AnonymousClass2(Bundle result2) {
                        result = result2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        RemoteCallback.this.mListener.onResult(result);
                    }
                });
                return;
            } else {
                onResultListener.onResult(result2);
                return;
            }
        }
        try {
            this.mCallback.sendResult(result2);
        } catch (RemoteException e) {
        }
    }

    /* renamed from: android.os.RemoteCallback$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 implements Runnable {
        final /* synthetic */ Bundle val$result;

        AnonymousClass2(Bundle result2) {
            result = result2;
        }

        @Override // java.lang.Runnable
        public void run() {
            RemoteCallback.this.mListener.onResult(result);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStrongBinder(this.mCallback.asBinder());
    }

    /* renamed from: android.os.RemoteCallback$3 */
    /* loaded from: classes3.dex */
    class AnonymousClass3 implements Parcelable.Creator<RemoteCallback> {
        AnonymousClass3() {
        }

        @Override // android.os.Parcelable.Creator
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public RemoteCallback[] newArray(int size) {
            return new RemoteCallback[size];
        }
    }
}

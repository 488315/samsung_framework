package android.credentials;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IGetCredentialCallback";

    void onError(String str, String str2) throws RemoteException;

    void onPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void onResponse(GetCredentialResponse getCredentialResponse) throws RemoteException;

    public static class Default implements IGetCredentialCallback {
        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(GetCredentialResponse response) throws RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(String errorType, String message) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGetCredentialCallback {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onPendingIntent = 1;
        static final int TRANSACTION_onResponse = 2;

        public Stub() {
            attachInterface(this, IGetCredentialCallback.DESCRIPTOR);
        }

        public static IGetCredentialCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetCredentialCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetCredentialCallback)) {
                return (IGetCredentialCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onPendingIntent";
                case 2:
                    return "onResponse";
                case 3:
                    return "onError";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IGetCredentialCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PendingIntent _arg0 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    onPendingIntent(_arg0);
                    return true;
                case 2:
                    GetCredentialResponse _arg02 = (GetCredentialResponse) data.readTypedObject(GetCredentialResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onResponse(_arg02);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg03, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onResponse(GetCredentialResponse response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onError(String errorType, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    _data.writeString(errorType);
                    _data.writeString(message);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}

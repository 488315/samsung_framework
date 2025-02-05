package android.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISetEnabledProvidersCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.ISetEnabledProvidersCallback";

    void onError(String str, String str2) throws RemoteException;

    void onResponse() throws RemoteException;

    public static class Default implements ISetEnabledProvidersCallback {
        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onResponse() throws RemoteException {
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onError(String errorType, String message) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISetEnabledProvidersCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResponse = 1;

        public Stub() {
            attachInterface(this, ISetEnabledProvidersCallback.DESCRIPTOR);
        }

        public static ISetEnabledProvidersCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISetEnabledProvidersCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISetEnabledProvidersCallback)) {
                return (ISetEnabledProvidersCallback) iin;
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
                    return "onResponse";
                case 2:
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
                data.enforceInterface(ISetEnabledProvidersCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISetEnabledProvidersCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onResponse();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISetEnabledProvidersCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISetEnabledProvidersCallback.DESCRIPTOR;
            }

            @Override // android.credentials.ISetEnabledProvidersCallback
            public void onResponse() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISetEnabledProvidersCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.ISetEnabledProvidersCallback
            public void onError(String errorType, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISetEnabledProvidersCallback.DESCRIPTOR);
                    _data.writeString(errorType);
                    _data.writeString(message);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}

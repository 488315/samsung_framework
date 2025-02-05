package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISystemDataTransferCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.ISystemDataTransferCallback";

    void onError(String str) throws RemoteException;

    void onResult() throws RemoteException;

    public static class Default implements ISystemDataTransferCallback {
        @Override // android.companion.ISystemDataTransferCallback
        public void onResult() throws RemoteException {
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onError(String error) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISystemDataTransferCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, ISystemDataTransferCallback.DESCRIPTOR);
        }

        public static ISystemDataTransferCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemDataTransferCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemDataTransferCallback)) {
                return (ISystemDataTransferCallback) iin;
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
                    return "onResult";
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
                data.enforceInterface(ISystemDataTransferCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISystemDataTransferCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onResult();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISystemDataTransferCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemDataTransferCallback.DESCRIPTOR;
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onResult() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemDataTransferCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onError(String error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemDataTransferCallback.DESCRIPTOR);
                    _data.writeString(error);
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

package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IStoreUpgradedKeyCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IStoreUpgradedKeyCallback";

    void onError(String str) throws RemoteException;

    void onSuccess() throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IStoreUpgradedKeyCallback {
        @Override // android.security.rkp.IStoreUpgradedKeyCallback
        public void onSuccess() throws RemoteException {
        }

        @Override // android.security.rkp.IStoreUpgradedKeyCallback
        public void onError(String error) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IStoreUpgradedKeyCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IStoreUpgradedKeyCallback.DESCRIPTOR);
        }

        public static IStoreUpgradedKeyCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStoreUpgradedKeyCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IStoreUpgradedKeyCallback)) {
                return (IStoreUpgradedKeyCallback) iin;
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
                    return "onSuccess";
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
                data.enforceInterface(IStoreUpgradedKeyCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IStoreUpgradedKeyCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onSuccess();
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
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class Proxy implements IStoreUpgradedKeyCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStoreUpgradedKeyCallback.DESCRIPTOR;
            }

            @Override // android.security.rkp.IStoreUpgradedKeyCallback
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStoreUpgradedKeyCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.security.rkp.IStoreUpgradedKeyCallback
            public void onError(String error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStoreUpgradedKeyCallback.DESCRIPTOR);
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

package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.identity.ICredentialStore;

/* loaded from: classes3.dex */
public interface ICredentialStoreFactory extends IInterface {
    public static final int CREDENTIAL_STORE_TYPE_DEFAULT = 0;
    public static final int CREDENTIAL_STORE_TYPE_DIRECT_ACCESS = 1;
    public static final String DESCRIPTOR = "android.security.identity.ICredentialStoreFactory";

    ICredentialStore getCredentialStore(int i) throws RemoteException;

    public static class Default implements ICredentialStoreFactory {
        @Override // android.security.identity.ICredentialStoreFactory
        public ICredentialStore getCredentialStore(int credentialStoreType) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICredentialStoreFactory {
        static final int TRANSACTION_getCredentialStore = 1;

        public Stub() {
            attachInterface(this, ICredentialStoreFactory.DESCRIPTOR);
        }

        public static ICredentialStoreFactory asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICredentialStoreFactory.DESCRIPTOR);
            if (iin != null && (iin instanceof ICredentialStoreFactory)) {
                return (ICredentialStoreFactory) iin;
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
                    return "getCredentialStore";
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
                data.enforceInterface(ICredentialStoreFactory.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICredentialStoreFactory.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    ICredentialStore _result = getCredentialStore(_arg0);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICredentialStoreFactory {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialStoreFactory.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredentialStoreFactory
            public ICredentialStore getCredentialStore(int credentialStoreType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialStoreFactory.DESCRIPTOR);
                    _data.writeInt(credentialStoreType);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ICredentialStore _result = ICredentialStore.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}

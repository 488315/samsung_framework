package android.security.legacykeystore;

import android.app.slice.Slice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILegacyKeystore extends IInterface {
    public static final String DESCRIPTOR = "android.security.legacykeystore.ILegacyKeystore";
    public static final int ERROR_ENTRY_NOT_FOUND = 7;
    public static final int ERROR_PERMISSION_DENIED = 6;
    public static final int ERROR_SYSTEM_ERROR = 4;
    public static final int UID_SELF = -1;

    byte[] get(String str, int i) throws RemoteException;

    String[] list(String str, int i) throws RemoteException;

    void put(String str, int i, byte[] bArr) throws RemoteException;

    void remove(String str, int i) throws RemoteException;

    public static class Default implements ILegacyKeystore {
        @Override // android.security.legacykeystore.ILegacyKeystore
        public byte[] get(String alias, int uid) throws RemoteException {
            return null;
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void put(String alias, int uid, byte[] blob) throws RemoteException {
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void remove(String alias, int uid) throws RemoteException {
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public String[] list(String prefix, int uid) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILegacyKeystore {
        static final int TRANSACTION_get = 1;
        static final int TRANSACTION_list = 4;
        static final int TRANSACTION_put = 2;
        static final int TRANSACTION_remove = 3;

        public Stub() {
            attachInterface(this, ILegacyKeystore.DESCRIPTOR);
        }

        public static ILegacyKeystore asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILegacyKeystore.DESCRIPTOR);
            if (iin != null && (iin instanceof ILegacyKeystore)) {
                return (ILegacyKeystore) iin;
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
                    return "get";
                case 2:
                    return "put";
                case 3:
                    return "remove";
                case 4:
                    return Slice.HINT_LIST;
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
                data.enforceInterface(ILegacyKeystore.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILegacyKeystore.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result = get(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    byte[] _arg2 = data.createByteArray();
                    data.enforceNoDataAvail();
                    put(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    remove(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result2 = list(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeStringArray(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILegacyKeystore {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILegacyKeystore.DESCRIPTOR;
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public byte[] get(String alias, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void put(String alias, int uid, byte[] blob) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    _data.writeByteArray(blob);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void remove(String alias, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public String[] list(String prefix, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    _data.writeString(prefix);
                    _data.writeInt(uid);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}

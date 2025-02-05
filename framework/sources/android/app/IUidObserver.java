package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUidObserver extends IInterface {
    void onUidActive(int i) throws RemoteException;

    void onUidCachedChanged(int i, boolean z) throws RemoteException;

    void onUidGone(int i, boolean z) throws RemoteException;

    void onUidIdle(int i, boolean z) throws RemoteException;

    void onUidProcAdjChanged(int i, int i2) throws RemoteException;

    void onUidStateChanged(int i, int i2, long j, int i3) throws RemoteException;

    public static class Default implements IUidObserver {
        @Override // android.app.IUidObserver
        public void onUidGone(int uid, boolean disabled) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidActive(int uid) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidIdle(int uid, boolean disabled) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidStateChanged(int uid, int procState, long procStateSeq, int capability) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidProcAdjChanged(int uid, int adj) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidCachedChanged(int uid, boolean cached) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUidObserver {
        public static final String DESCRIPTOR = "android.app.IUidObserver";
        static final int TRANSACTION_onUidActive = 2;
        static final int TRANSACTION_onUidCachedChanged = 6;
        static final int TRANSACTION_onUidGone = 1;
        static final int TRANSACTION_onUidIdle = 3;
        static final int TRANSACTION_onUidProcAdjChanged = 5;
        static final int TRANSACTION_onUidStateChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUidObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUidObserver)) {
                return (IUidObserver) iin;
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
                    return "onUidGone";
                case 2:
                    return "onUidActive";
                case 3:
                    return "onUidIdle";
                case 4:
                    return "onUidStateChanged";
                case 5:
                    return "onUidProcAdjChanged";
                case 6:
                    return "onUidCachedChanged";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onUidGone(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onUidActive(_arg02);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onUidIdle(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    int _arg13 = data.readInt();
                    long _arg2 = data.readLong();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onUidStateChanged(_arg04, _arg13, _arg2, _arg3);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onUidProcAdjChanged(_arg05, _arg14);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onUidCachedChanged(_arg06, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUidObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IUidObserver
            public void onUidGone(int uid, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidActive(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidIdle(int uid, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidStateChanged(int uid, int procState, long procStateSeq, int capability) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(procState);
                    _data.writeLong(procStateSeq);
                    _data.writeInt(capability);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidProcAdjChanged(int uid, int adj) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(adj);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidCachedChanged(int uid, boolean cached) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(cached);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}

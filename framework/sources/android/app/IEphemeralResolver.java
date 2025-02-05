package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IEphemeralResolver extends IInterface {
    void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String str, int i) throws RemoteException;

    void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] iArr, int i) throws RemoteException;

    public static class Default implements IEphemeralResolver {
        @Override // android.app.IEphemeralResolver
        public void getEphemeralResolveInfoList(IRemoteCallback callback, int[] digestPrefix, int sequence) throws RemoteException {
        }

        @Override // android.app.IEphemeralResolver
        public void getEphemeralIntentFilterList(IRemoteCallback callback, String hostName, int sequence) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEphemeralResolver {
        public static final String DESCRIPTOR = "android.app.IEphemeralResolver";
        static final int TRANSACTION_getEphemeralIntentFilterList = 2;
        static final int TRANSACTION_getEphemeralResolveInfoList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEphemeralResolver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IEphemeralResolver)) {
                return (IEphemeralResolver) iin;
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
                    return "getEphemeralResolveInfoList";
                case 2:
                    return "getEphemeralIntentFilterList";
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
                    IRemoteCallback _arg0 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    int[] _arg1 = data.createIntArray();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    getEphemeralResolveInfoList(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    IRemoteCallback _arg02 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    getEphemeralIntentFilterList(_arg02, _arg12, _arg22);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IEphemeralResolver {
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

            @Override // android.app.IEphemeralResolver
            public void getEphemeralResolveInfoList(IRemoteCallback callback, int[] digestPrefix, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeIntArray(digestPrefix);
                    _data.writeInt(sequence);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IEphemeralResolver
            public void getEphemeralIntentFilterList(IRemoteCallback callback, String hostName, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeString(hostName);
                    _data.writeInt(sequence);
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

package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerCallback";

    void dispatchSpatializerAvailableChanged(boolean z) throws RemoteException;

    void dispatchSpatializerEnabledChanged(boolean z) throws RemoteException;

    public static class Default implements ISpatializerCallback {
        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerEnabledChanged(boolean enabled) throws RemoteException {
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerAvailableChanged(boolean available) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpatializerCallback {
        static final int TRANSACTION_dispatchSpatializerAvailableChanged = 2;
        static final int TRANSACTION_dispatchSpatializerEnabledChanged = 1;

        public Stub() {
            attachInterface(this, ISpatializerCallback.DESCRIPTOR);
        }

        public static ISpatializerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpatializerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializerCallback)) {
                return (ISpatializerCallback) iin;
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
                    return "dispatchSpatializerEnabledChanged";
                case 2:
                    return "dispatchSpatializerAvailableChanged";
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
                data.enforceInterface(ISpatializerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpatializerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchSpatializerEnabledChanged(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchSpatializerAvailableChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpatializerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerCallback
            public void dispatchSpatializerEnabledChanged(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerCallback.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializerCallback
            public void dispatchSpatializerAvailableChanged(boolean available) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerCallback.DESCRIPTOR);
                    _data.writeBoolean(available);
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

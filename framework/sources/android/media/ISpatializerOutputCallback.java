package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerOutputCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerOutputCallback";

    void dispatchSpatializerOutputChanged(int i) throws RemoteException;

    public static class Default implements ISpatializerOutputCallback {
        @Override // android.media.ISpatializerOutputCallback
        public void dispatchSpatializerOutputChanged(int output) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpatializerOutputCallback {
        static final int TRANSACTION_dispatchSpatializerOutputChanged = 1;

        public Stub() {
            attachInterface(this, ISpatializerOutputCallback.DESCRIPTOR);
        }

        public static ISpatializerOutputCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpatializerOutputCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializerOutputCallback)) {
                return (ISpatializerOutputCallback) iin;
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
                    return "dispatchSpatializerOutputChanged";
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
                data.enforceInterface(ISpatializerOutputCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpatializerOutputCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchSpatializerOutputChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpatializerOutputCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerOutputCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerOutputCallback
            public void dispatchSpatializerOutputChanged(int output) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerOutputCallback.DESCRIPTOR);
                    _data.writeInt(output);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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

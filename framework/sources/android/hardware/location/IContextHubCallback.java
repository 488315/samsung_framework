package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubCallback extends IInterface {
    void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException;

    public static class Default implements IContextHubCallback {
        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(int hubId, int nanoAppId, ContextHubMessage msg) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContextHubCallback {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubCallback";
        static final int TRANSACTION_onMessageReceipt = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContextHubCallback)) {
                return (IContextHubCallback) iin;
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
                    return "onMessageReceipt";
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
                    int _arg1 = data.readInt();
                    ContextHubMessage _arg2 = (ContextHubMessage) data.readTypedObject(ContextHubMessage.CREATOR);
                    data.enforceNoDataAvail();
                    onMessageReceipt(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContextHubCallback {
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

            @Override // android.hardware.location.IContextHubCallback
            public void onMessageReceipt(int hubId, int nanoAppId, ContextHubMessage msg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hubId);
                    _data.writeInt(nanoAppId);
                    _data.writeTypedObject(msg, 0);
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

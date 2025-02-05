package android.media.tv.interactive;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppServiceCallback";

    void onStateChanged(int i, int i2, int i3) throws RemoteException;

    public static class Default implements ITvInteractiveAppServiceCallback {
        @Override // android.media.tv.interactive.ITvInteractiveAppServiceCallback
        public void onStateChanged(int type, int state, int error) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInteractiveAppServiceCallback {
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, ITvInteractiveAppServiceCallback.DESCRIPTOR);
        }

        public static ITvInteractiveAppServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvInteractiveAppServiceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInteractiveAppServiceCallback)) {
                return (ITvInteractiveAppServiceCallback) iin;
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
                    return "onStateChanged";
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
                data.enforceInterface(ITvInteractiveAppServiceCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvInteractiveAppServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onStateChanged(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvInteractiveAppServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppServiceCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppServiceCallback
            public void onStateChanged(int type, int state, int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppServiceCallback.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    _data.writeInt(error);
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

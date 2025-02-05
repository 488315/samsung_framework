package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IPinnedTaskListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IPinnedTaskListener";

    void onImeVisibilityChanged(boolean z, int i) throws RemoteException;

    void onMovementBoundsChanged(boolean z) throws RemoteException;

    public static class Default implements IPinnedTaskListener {
        @Override // android.view.IPinnedTaskListener
        public void onMovementBoundsChanged(boolean fromImeAdjustment) throws RemoteException {
        }

        @Override // android.view.IPinnedTaskListener
        public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPinnedTaskListener {
        static final int TRANSACTION_onImeVisibilityChanged = 2;
        static final int TRANSACTION_onMovementBoundsChanged = 1;

        public Stub() {
            attachInterface(this, IPinnedTaskListener.DESCRIPTOR);
        }

        public static IPinnedTaskListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPinnedTaskListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IPinnedTaskListener)) {
                return (IPinnedTaskListener) iin;
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
                    return "onMovementBoundsChanged";
                case 2:
                    return "onImeVisibilityChanged";
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
                data.enforceInterface(IPinnedTaskListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPinnedTaskListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onMovementBoundsChanged(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onImeVisibilityChanged(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPinnedTaskListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPinnedTaskListener.DESCRIPTOR;
            }

            @Override // android.view.IPinnedTaskListener
            public void onMovementBoundsChanged(boolean fromImeAdjustment) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPinnedTaskListener.DESCRIPTOR);
                    _data.writeBoolean(fromImeAdjustment);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IPinnedTaskListener
            public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPinnedTaskListener.DESCRIPTOR);
                    _data.writeBoolean(imeVisible);
                    _data.writeInt(imeHeight);
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

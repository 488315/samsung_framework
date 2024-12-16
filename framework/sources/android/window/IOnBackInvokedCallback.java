package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IOnBackInvokedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IOnBackInvokedCallback";

    void onBackCancelled() throws RemoteException;

    void onBackInvoked() throws RemoteException;

    void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException;

    void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException;

    void setTriggerBack(boolean z) throws RemoteException;

    public static class Default implements IOnBackInvokedCallback {
        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean triggerBack) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOnBackInvokedCallback {
        static final int TRANSACTION_onBackCancelled = 3;
        static final int TRANSACTION_onBackInvoked = 4;
        static final int TRANSACTION_onBackProgressed = 2;
        static final int TRANSACTION_onBackStarted = 1;
        static final int TRANSACTION_setTriggerBack = 5;

        public Stub() {
            attachInterface(this, IOnBackInvokedCallback.DESCRIPTOR);
        }

        public static IOnBackInvokedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnBackInvokedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnBackInvokedCallback)) {
                return (IOnBackInvokedCallback) iin;
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
                    return "onBackStarted";
                case 2:
                    return "onBackProgressed";
                case 3:
                    return "onBackCancelled";
                case 4:
                    return "onBackInvoked";
                case 5:
                    return "setTriggerBack";
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
                data.enforceInterface(IOnBackInvokedCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnBackInvokedCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    BackMotionEvent _arg0 = (BackMotionEvent) data.readTypedObject(BackMotionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onBackStarted(_arg0);
                    return true;
                case 2:
                    BackMotionEvent _arg02 = (BackMotionEvent) data.readTypedObject(BackMotionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onBackProgressed(_arg02);
                    return true;
                case 3:
                    onBackCancelled();
                    return true;
                case 4:
                    onBackInvoked();
                    return true;
                case 5:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTriggerBack(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnBackInvokedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnBackInvokedCallback.DESCRIPTOR;
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    _data.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    _data.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackCancelled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackInvoked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void setTriggerBack(boolean triggerBack) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    _data.writeBoolean(triggerBack);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}

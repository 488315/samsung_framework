package android.service.games;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGameSession extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameSession";

    void onDestroyed() throws RemoteException;

    void onTaskFocusChanged(boolean z) throws RemoteException;

    void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws RemoteException;

    public static class Default implements IGameSession {
        @Override // android.service.games.IGameSession
        public void onDestroyed() throws RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean visibleDueToGesture) throws RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean focused) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameSession {
        static final int TRANSACTION_onDestroyed = 1;
        static final int TRANSACTION_onTaskFocusChanged = 3;
        static final int TRANSACTION_onTransientSystemBarVisibilityFromRevealGestureChanged = 2;

        public Stub() {
            attachInterface(this, IGameSession.DESCRIPTOR);
        }

        public static IGameSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameSession.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameSession)) {
                return (IGameSession) iin;
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
                    return "onDestroyed";
                case 2:
                    return "onTransientSystemBarVisibilityFromRevealGestureChanged";
                case 3:
                    return "onTaskFocusChanged";
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
                data.enforceInterface(IGameSession.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameSession.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onDestroyed();
                    return true;
                case 2:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTransientSystemBarVisibilityFromRevealGestureChanged(_arg0);
                    return true;
                case 3:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTaskFocusChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameSession {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameSession.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSession
            public void onDestroyed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean visibleDueToGesture) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    _data.writeBoolean(visibleDueToGesture);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTaskFocusChanged(boolean focused) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    _data.writeBoolean(focused);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}

package android.service.games;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.games.IGameServiceController;

/* loaded from: classes3.dex */
public interface IGameService extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameService";

    void connected(IGameServiceController iGameServiceController) throws RemoteException;

    void disconnected() throws RemoteException;

    void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException;

    public static class Default implements IGameService {
        @Override // android.service.games.IGameService
        public void connected(IGameServiceController gameServiceController) throws RemoteException {
        }

        @Override // android.service.games.IGameService
        public void disconnected() throws RemoteException {
        }

        @Override // android.service.games.IGameService
        public void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameService {
        static final int TRANSACTION_connected = 1;
        static final int TRANSACTION_disconnected = 2;
        static final int TRANSACTION_gameStarted = 3;

        public Stub() {
            attachInterface(this, IGameService.DESCRIPTOR);
        }

        public static IGameService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameService.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameService)) {
                return (IGameService) iin;
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
                    return "connected";
                case 2:
                    return "disconnected";
                case 3:
                    return "gameStarted";
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
                data.enforceInterface(IGameService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IGameServiceController _arg0 = IGameServiceController.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    connected(_arg0);
                    return true;
                case 2:
                    disconnected();
                    return true;
                case 3:
                    GameStartedEvent _arg02 = (GameStartedEvent) data.readTypedObject(GameStartedEvent.CREATOR);
                    data.enforceNoDataAvail();
                    gameStarted(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameService.DESCRIPTOR;
            }

            @Override // android.service.games.IGameService
            public void connected(IGameServiceController gameServiceController) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameService.DESCRIPTOR);
                    _data.writeStrongInterface(gameServiceController);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void disconnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameService.DESCRIPTOR);
                    _data.writeTypedObject(gameStartedEvent, 0);
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

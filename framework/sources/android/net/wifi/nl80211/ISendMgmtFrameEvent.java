package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISendMgmtFrameEvent extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.ISendMgmtFrameEvent";
    public static final int SEND_MGMT_FRAME_ERROR_ALREADY_STARTED = 5;
    public static final int SEND_MGMT_FRAME_ERROR_MCS_UNSUPPORTED = 2;
    public static final int SEND_MGMT_FRAME_ERROR_NO_ACK = 3;
    public static final int SEND_MGMT_FRAME_ERROR_TIMEOUT = 4;
    public static final int SEND_MGMT_FRAME_ERROR_UNKNOWN = 1;

    void OnAck(int i) throws RemoteException;

    void OnFailure(int i) throws RemoteException;

    public static class Default implements ISendMgmtFrameEvent {
        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnAck(int elapsedTimeMs) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnFailure(int reason) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISendMgmtFrameEvent {
        static final int TRANSACTION_OnAck = 1;
        static final int TRANSACTION_OnFailure = 2;

        public Stub() {
            attachInterface(this, ISendMgmtFrameEvent.DESCRIPTOR);
        }

        public static ISendMgmtFrameEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISendMgmtFrameEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof ISendMgmtFrameEvent)) {
                return (ISendMgmtFrameEvent) iin;
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
                    return "OnAck";
                case 2:
                    return "OnFailure";
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
                data.enforceInterface(ISendMgmtFrameEvent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISendMgmtFrameEvent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    OnAck(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    OnFailure(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISendMgmtFrameEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISendMgmtFrameEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
            public void OnAck(int elapsedTimeMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISendMgmtFrameEvent.DESCRIPTOR);
                    _data.writeInt(elapsedTimeMs);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
            public void OnFailure(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISendMgmtFrameEvent.DESCRIPTOR);
                    _data.writeInt(reason);
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

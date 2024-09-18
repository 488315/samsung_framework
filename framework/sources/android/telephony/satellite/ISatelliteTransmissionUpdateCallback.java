package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISatelliteTransmissionUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteTransmissionUpdateCallback";

    void onReceiveDatagramStateChanged(int i, int i2, int i3) throws RemoteException;

    void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException;

    void onSendDatagramStateChanged(int i, int i2, int i3) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISatelliteTransmissionUpdateCallback {
        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(int state, int sendPendingCount, int errorCode) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(int state, int receivePendingCount, int errorCode) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISatelliteTransmissionUpdateCallback {
        static final int TRANSACTION_onReceiveDatagramStateChanged = 2;
        static final int TRANSACTION_onSatellitePositionChanged = 3;
        static final int TRANSACTION_onSendDatagramStateChanged = 1;

        public Stub() {
            attachInterface(this, ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
        }

        public static ISatelliteTransmissionUpdateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteTransmissionUpdateCallback)) {
                return (ISatelliteTransmissionUpdateCallback) iin;
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
                    return "onSendDatagramStateChanged";
                case 2:
                    return "onReceiveDatagramStateChanged";
                case 3:
                    return "onSatellitePositionChanged";
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
                data.enforceInterface(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            onSendDatagramStateChanged(_arg0, _arg1, _arg2);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            onReceiveDatagramStateChanged(_arg02, _arg12, _arg22);
                            return true;
                        case 3:
                            PointingInfo _arg03 = (PointingInfo) data.readTypedObject(PointingInfo.CREATOR);
                            data.enforceNoDataAvail();
                            onSatellitePositionChanged(_arg03);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class Proxy implements ISatelliteTransmissionUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteTransmissionUpdateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSendDatagramStateChanged(int state, int sendPendingCount, int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(sendPendingCount);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onReceiveDatagramStateChanged(int state, int receivePendingCount, int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(receivePendingCount);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    _data.writeTypedObject(pointingInfo, 0);
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

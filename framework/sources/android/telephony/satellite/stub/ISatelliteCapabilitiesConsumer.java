package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteCapabilitiesConsumer extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer";

    void accept(SatelliteCapabilities satelliteCapabilities) throws RemoteException;

    public static class Default implements ISatelliteCapabilitiesConsumer {
        @Override // android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer
        public void accept(SatelliteCapabilities result) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteCapabilitiesConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, ISatelliteCapabilitiesConsumer.DESCRIPTOR);
        }

        public static ISatelliteCapabilitiesConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteCapabilitiesConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteCapabilitiesConsumer)) {
                return (ISatelliteCapabilitiesConsumer) iin;
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
                    return "accept";
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
                data.enforceInterface(ISatelliteCapabilitiesConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteCapabilitiesConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SatelliteCapabilities _arg0 = (SatelliteCapabilities) data.readTypedObject(SatelliteCapabilities.CREATOR);
                    data.enforceNoDataAvail();
                    accept(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteCapabilitiesConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteCapabilitiesConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer
            public void accept(SatelliteCapabilities result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteCapabilitiesConsumer.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
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

package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface INearbyMediaDevicesUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.INearbyMediaDevicesUpdateCallback";

    void onDevicesUpdated(List<NearbyDevice> list) throws RemoteException;

    public static class Default implements INearbyMediaDevicesUpdateCallback {
        @Override // android.media.INearbyMediaDevicesUpdateCallback
        public void onDevicesUpdated(List<NearbyDevice> nearbyDevices) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INearbyMediaDevicesUpdateCallback {
        static final int TRANSACTION_onDevicesUpdated = 1;

        public Stub() {
            attachInterface(this, INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
        }

        public static INearbyMediaDevicesUpdateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof INearbyMediaDevicesUpdateCallback)) {
                return (INearbyMediaDevicesUpdateCallback) iin;
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
                    return "onDevicesUpdated";
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
                data.enforceInterface(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<NearbyDevice> _arg0 = data.createTypedArrayList(NearbyDevice.CREATOR);
                    data.enforceNoDataAvail();
                    onDevicesUpdated(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INearbyMediaDevicesUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INearbyMediaDevicesUpdateCallback.DESCRIPTOR;
            }

            @Override // android.media.INearbyMediaDevicesUpdateCallback
            public void onDevicesUpdated(List<NearbyDevice> nearbyDevices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                    _data.writeTypedList(nearbyDevices, 0);
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

package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiApClientUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApClientUpdateCallback";

    void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails) throws RemoteException;

    /* loaded from: classes6.dex */
    public static class Default implements ISemWifiApClientUpdateCallback {
        @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
        public void onClientUpdated(SemWifiApClientDetails clientDetails) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes6.dex */
    public static abstract class Stub extends Binder implements ISemWifiApClientUpdateCallback {
        static final int TRANSACTION_onClientUpdated = 1;

        public Stub() {
            attachInterface(this, ISemWifiApClientUpdateCallback.DESCRIPTOR);
        }

        public static ISemWifiApClientUpdateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiApClientUpdateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiApClientUpdateCallback)) {
                return (ISemWifiApClientUpdateCallback) iin;
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
                    return "onClientUpdated";
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
                data.enforceInterface(ISemWifiApClientUpdateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemWifiApClientUpdateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            SemWifiApClientDetails _arg0 = (SemWifiApClientDetails) data.readTypedObject(SemWifiApClientDetails.CREATOR);
                            data.enforceNoDataAvail();
                            onClientUpdated(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* loaded from: classes6.dex */
        private static class Proxy implements ISemWifiApClientUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApClientUpdateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
            public void onClientUpdated(SemWifiApClientDetails clientDetails) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiApClientUpdateCallback.DESCRIPTOR);
                    _data.writeTypedObject(clientDetails, 0);
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

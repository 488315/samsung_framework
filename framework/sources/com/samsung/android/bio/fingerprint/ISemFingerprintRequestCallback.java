package com.samsung.android.bio.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemFingerprintRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback";

    void onResult(int i) throws RemoteException;

    public static class Default implements ISemFingerprintRequestCallback {
        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(int code) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemFingerprintRequestCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, ISemFingerprintRequestCallback.DESCRIPTOR);
        }

        public static ISemFingerprintRequestCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemFingerprintRequestCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemFingerprintRequestCallback)) {
                return (ISemFingerprintRequestCallback) iin;
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
                    return "onResult";
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
                data.enforceInterface(ISemFingerprintRequestCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemFingerprintRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemFingerprintRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemFingerprintRequestCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
            public void onResult(int code) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintRequestCallback.DESCRIPTOR);
                    _data.writeInt(code);
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

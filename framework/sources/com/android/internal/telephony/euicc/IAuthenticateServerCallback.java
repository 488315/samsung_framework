package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAuthenticateServerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IAuthenticateServerCallback";

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static class Default implements IAuthenticateServerCallback {
        @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
        public void onComplete(int resultCode, byte[] response) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAuthenticateServerCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IAuthenticateServerCallback.DESCRIPTOR);
        }

        public static IAuthenticateServerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAuthenticateServerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAuthenticateServerCallback)) {
                return (IAuthenticateServerCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IAuthenticateServerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAuthenticateServerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAuthenticateServerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthenticateServerCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
            public void onComplete(int resultCode, byte[] response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAuthenticateServerCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeByteArray(response);
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

package com.samsung.android.knox.dar.sdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISdpListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.sdp.ISdpListener";

    void onEngineRemoved() throws RemoteException;

    void onStateChange(int i) throws RemoteException;

    public static class Default implements ISdpListener {
        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onStateChange(int state) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onEngineRemoved() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISdpListener {
        static final int TRANSACTION_onEngineRemoved = 2;
        static final int TRANSACTION_onStateChange = 1;

        public Stub() {
            attachInterface(this, ISdpListener.DESCRIPTOR);
        }

        public static ISdpListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISdpListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISdpListener)) {
                return (ISdpListener) iin;
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
                    return "onStateChange";
                case 2:
                    return "onEngineRemoved";
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
                data.enforceInterface(ISdpListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISdpListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onStateChange(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    onEngineRemoved();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISdpListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISdpListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.sdp.ISdpListener
            public void onStateChange(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISdpListener.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.sdp.ISdpListener
            public void onEngineRemoved() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISdpListener.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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

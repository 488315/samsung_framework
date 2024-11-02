package com.samsung.android.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemCmcRecordingListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.cmc.ISemCmcRecordingListener";

    void onError(int i, int i2) throws RemoteException;

    void onInfo(int i, int i2) throws RemoteException;

    /* loaded from: classes5.dex */
    public static class Default implements ISemCmcRecordingListener {
        @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
        public void onInfo(int what, int extra) throws RemoteException {
        }

        @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
        public void onError(int what, int extra) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class Stub extends Binder implements ISemCmcRecordingListener {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onInfo = 1;

        public Stub() {
            attachInterface(this, ISemCmcRecordingListener.DESCRIPTOR);
        }

        public static ISemCmcRecordingListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemCmcRecordingListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemCmcRecordingListener)) {
                return (ISemCmcRecordingListener) iin;
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
                    return "onInfo";
                case 2:
                    return "onError";
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
                data.enforceInterface(ISemCmcRecordingListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemCmcRecordingListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onInfo(_arg0, _arg1);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onError(_arg02, _arg12);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes5.dex */
        public static class Proxy implements ISemCmcRecordingListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemCmcRecordingListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onInfo(int what, int extra) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemCmcRecordingListener.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeInt(extra);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onError(int what, int extra) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemCmcRecordingListener.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeInt(extra);
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

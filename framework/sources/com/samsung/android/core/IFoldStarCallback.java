package com.samsung.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IFoldStarCallback extends IInterface {
    public static final int BOUNDS_COMPAT_NONE = 0;
    public static final String DESCRIPTOR = "com.samsung.android.core.IFoldStarCallback";
    public static final int DISPLAY_COMPAT_MODE = 1;

    void onBoundsCompatPackageAppeared(int i, String str) throws RemoteException;

    public static class Default implements IFoldStarCallback {
        @Override // com.samsung.android.core.IFoldStarCallback
        public void onBoundsCompatPackageAppeared(int mode, String packageName) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFoldStarCallback {
        static final int TRANSACTION_onBoundsCompatPackageAppeared = 1;

        public Stub() {
            attachInterface(this, IFoldStarCallback.DESCRIPTOR);
        }

        public static IFoldStarCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFoldStarCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFoldStarCallback)) {
                return (IFoldStarCallback) iin;
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
                    return "onBoundsCompatPackageAppeared";
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
                data.enforceInterface(IFoldStarCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFoldStarCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onBoundsCompatPackageAppeared(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFoldStarCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFoldStarCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.IFoldStarCallback
            public void onBoundsCompatPackageAppeared(int mode, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFoldStarCallback.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(packageName);
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

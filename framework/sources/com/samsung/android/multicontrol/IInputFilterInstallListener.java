package com.samsung.android.multicontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IInputFilterInstallListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multicontrol.IInputFilterInstallListener";

    void onInstalled() throws RemoteException;

    void onUninstalled() throws RemoteException;

    /* loaded from: classes5.dex */
    public static class Default implements IInputFilterInstallListener {
        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onInstalled() throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onUninstalled() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class Stub extends Binder implements IInputFilterInstallListener {
        static final int TRANSACTION_onInstalled = 1;
        static final int TRANSACTION_onUninstalled = 2;

        public Stub() {
            attachInterface(this, IInputFilterInstallListener.DESCRIPTOR);
        }

        public static IInputFilterInstallListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInputFilterInstallListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IInputFilterInstallListener)) {
                return (IInputFilterInstallListener) iin;
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
                    return "onInstalled";
                case 2:
                    return "onUninstalled";
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
                data.enforceInterface(IInputFilterInstallListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IInputFilterInstallListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onInstalled();
                            return true;
                        case 2:
                            onUninstalled();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes5.dex */
        public static class Proxy implements IInputFilterInstallListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputFilterInstallListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
            public void onInstalled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
            public void onUninstalled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
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

package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDesktopModeBlocker extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeBlocker";

    String onBlocked() throws RemoteException;

    public static class Default implements IDesktopModeBlocker {
        @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
        public String onBlocked() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDesktopModeBlocker {
        static final int TRANSACTION_onBlocked = 1;

        public Stub() {
            attachInterface(this, IDesktopModeBlocker.DESCRIPTOR);
        }

        public static IDesktopModeBlocker asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDesktopModeBlocker.DESCRIPTOR);
            if (iin != null && (iin instanceof IDesktopModeBlocker)) {
                return (IDesktopModeBlocker) iin;
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
                    return "onBlocked";
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
                data.enforceInterface(IDesktopModeBlocker.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDesktopModeBlocker.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _result = onBlocked();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDesktopModeBlocker {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeBlocker.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
            public String onBlocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDesktopModeBlocker.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
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

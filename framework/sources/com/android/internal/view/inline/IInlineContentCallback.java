package com.android.internal.view.inline;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControlViewHost;

/* loaded from: classes5.dex */
public interface IInlineContentCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentCallback";

    void onClick() throws RemoteException;

    void onContent(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException;

    void onLongClick() throws RemoteException;

    public static class Default implements IInlineContentCallback {
        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onContent(SurfaceControlViewHost.SurfacePackage content, int width, int height) throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onClick() throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onLongClick() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInlineContentCallback {
        static final int TRANSACTION_onClick = 2;
        static final int TRANSACTION_onContent = 1;
        static final int TRANSACTION_onLongClick = 3;

        public Stub() {
            attachInterface(this, IInlineContentCallback.DESCRIPTOR);
        }

        public static IInlineContentCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInlineContentCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInlineContentCallback)) {
                return (IInlineContentCallback) iin;
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
                    return "onContent";
                case 2:
                    return "onClick";
                case 3:
                    return "onLongClick";
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
                data.enforceInterface(IInlineContentCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInlineContentCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SurfaceControlViewHost.SurfacePackage _arg0 = (SurfaceControlViewHost.SurfacePackage) data.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onContent(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    onClick();
                    return true;
                case 3:
                    onLongClick();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInlineContentCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineContentCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onContent(SurfaceControlViewHost.SurfacePackage content, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    _data.writeTypedObject(content, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onClick() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onLongClick() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}

package android.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IWallpaperManagerCallback extends IInterface {
    void onSemBackupStatusChanged(int i, int i2, int i3) throws RemoteException;

    void onSemMultipackApplied(int i) throws RemoteException;

    void onSemWallpaperChanged(int i, int i2, Bundle bundle) throws RemoteException;

    void onSemWallpaperColorsAnalysisRequested(int i, int i2) throws RemoteException;

    void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) throws RemoteException;

    void onWallpaperChanged() throws RemoteException;

    void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IWallpaperManagerCallback {
        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(SemWallpaperColors colors, int which, int userId) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int which, int userId) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemBackupStatusChanged(int which, int status, int key) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int type, int which, Bundle extras) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemMultipackApplied(int which) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IWallpaperManagerCallback {
        public static final String DESCRIPTOR = "android.app.IWallpaperManagerCallback";
        static final int TRANSACTION_onSemBackupStatusChanged = 5;
        static final int TRANSACTION_onSemMultipackApplied = 7;
        static final int TRANSACTION_onSemWallpaperChanged = 6;
        static final int TRANSACTION_onSemWallpaperColorsAnalysisRequested = 4;
        static final int TRANSACTION_onSemWallpaperColorsChanged = 3;
        static final int TRANSACTION_onWallpaperChanged = 1;
        static final int TRANSACTION_onWallpaperColorsChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManagerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperManagerCallback)) {
                return (IWallpaperManagerCallback) iin;
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
                    return "onWallpaperChanged";
                case 2:
                    return "onWallpaperColorsChanged";
                case 3:
                    return "onSemWallpaperColorsChanged";
                case 4:
                    return "onSemWallpaperColorsAnalysisRequested";
                case 5:
                    return "onSemBackupStatusChanged";
                case 6:
                    return "onSemWallpaperChanged";
                case 7:
                    return "onSemMultipackApplied";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onWallpaperChanged();
                            return true;
                        case 2:
                            WallpaperColors _arg0 = (WallpaperColors) data.readTypedObject(WallpaperColors.CREATOR);
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            onWallpaperColorsChanged(_arg0, _arg1, _arg2);
                            return true;
                        case 3:
                            SemWallpaperColors _arg02 = (SemWallpaperColors) data.readTypedObject(SemWallpaperColors.CREATOR);
                            int _arg12 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            onSemWallpaperColorsChanged(_arg02, _arg12, _arg22);
                            return true;
                        case 4:
                            int _arg03 = data.readInt();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            onSemWallpaperColorsAnalysisRequested(_arg03, _arg13);
                            return true;
                        case 5:
                            int _arg04 = data.readInt();
                            int _arg14 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            onSemBackupStatusChanged(_arg04, _arg14, _arg23);
                            return true;
                        case 6:
                            int _arg05 = data.readInt();
                            int _arg15 = data.readInt();
                            Bundle _arg24 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            onSemWallpaperChanged(_arg05, _arg15, _arg24);
                            return true;
                        case 7:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            onSemMultipackApplied(_arg06);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IWallpaperManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onWallpaperChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(colors, 0);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperColorsChanged(SemWallpaperColors colors, int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(colors, 0);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperColorsAnalysisRequested(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemBackupStatusChanged(int which, int status, int key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(status);
                    _data.writeInt(key);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemWallpaperChanged(int type, int which, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(which);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManagerCallback
            public void onSemMultipackApplied(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}

package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IFullBackupRestoreObserver extends IInterface {
    void onBackupPackage(String str) throws RemoteException;

    void onEndBackup() throws RemoteException;

    void onEndRestore() throws RemoteException;

    void onRestorePackage(String str) throws RemoteException;

    void onStartBackup() throws RemoteException;

    void onStartRestore() throws RemoteException;

    void onTimeout() throws RemoteException;

    public static class Default implements IFullBackupRestoreObserver {
        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartBackup() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onBackupPackage(String name) throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndBackup() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartRestore() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onRestorePackage(String name) throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndRestore() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onTimeout() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFullBackupRestoreObserver {
        public static final String DESCRIPTOR = "android.app.backup.IFullBackupRestoreObserver";
        static final int TRANSACTION_onBackupPackage = 2;
        static final int TRANSACTION_onEndBackup = 3;
        static final int TRANSACTION_onEndRestore = 6;
        static final int TRANSACTION_onRestorePackage = 5;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onStartRestore = 4;
        static final int TRANSACTION_onTimeout = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFullBackupRestoreObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IFullBackupRestoreObserver)) {
                return (IFullBackupRestoreObserver) iin;
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
                    return "onStartBackup";
                case 2:
                    return "onBackupPackage";
                case 3:
                    return "onEndBackup";
                case 4:
                    return "onStartRestore";
                case 5:
                    return "onRestorePackage";
                case 6:
                    return "onEndRestore";
                case 7:
                    return "onTimeout";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onStartBackup();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onBackupPackage(_arg0);
                    return true;
                case 3:
                    onEndBackup();
                    return true;
                case 4:
                    onStartRestore();
                    return true;
                case 5:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onRestorePackage(_arg02);
                    return true;
                case 6:
                    onEndRestore();
                    return true;
                case 7:
                    onTimeout();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFullBackupRestoreObserver {
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

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartBackup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onBackupPackage(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndBackup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartRestore() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onRestorePackage(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndRestore() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onTimeout() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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

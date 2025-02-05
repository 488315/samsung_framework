package android.app.backup;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRestoreSession extends IInterface {
    void endRestoreSession() throws RemoteException;

    int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restoreAll(long j, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restorePackage(String str, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restorePackages(long j, IRestoreObserver iRestoreObserver, String[] strArr, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    public static class Default implements IRestoreSession {
        @Override // android.app.backup.IRestoreSession
        public int getAvailableRestoreSets(IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restoreAll(long token, IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackages(long token, IRestoreObserver observer, String[] packages, IBackupManagerMonitor monitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackage(String packageName, IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public void endRestoreSession() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRestoreSession {
        public static final String DESCRIPTOR = "android.app.backup.IRestoreSession";
        static final int TRANSACTION_endRestoreSession = 5;
        static final int TRANSACTION_getAvailableRestoreSets = 1;
        static final int TRANSACTION_restoreAll = 2;
        static final int TRANSACTION_restorePackage = 4;
        static final int TRANSACTION_restorePackages = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRestoreSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRestoreSession)) {
                return (IRestoreSession) iin;
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
                    return "getAvailableRestoreSets";
                case 2:
                    return "restoreAll";
                case 3:
                    return "restorePackages";
                case 4:
                    return "restorePackage";
                case 5:
                    return "endRestoreSession";
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
                    IRestoreObserver _arg0 = IRestoreObserver.Stub.asInterface(data.readStrongBinder());
                    IBackupManagerMonitor _arg1 = IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = getAvailableRestoreSets(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    IRestoreObserver _arg12 = IRestoreObserver.Stub.asInterface(data.readStrongBinder());
                    IBackupManagerMonitor _arg2 = IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result2 = restoreAll(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    long _arg03 = data.readLong();
                    IRestoreObserver _arg13 = IRestoreObserver.Stub.asInterface(data.readStrongBinder());
                    String[] _arg22 = data.createStringArray();
                    IBackupManagerMonitor _arg3 = IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = restorePackages(_arg03, _arg13, _arg22, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    IRestoreObserver _arg14 = IRestoreObserver.Stub.asInterface(data.readStrongBinder());
                    IBackupManagerMonitor _arg23 = IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result4 = restorePackage(_arg04, _arg14, _arg23);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    endRestoreSession();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRestoreSession {
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

            @Override // android.app.backup.IRestoreSession
            public int getAvailableRestoreSets(IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    _data.writeStrongInterface(monitor);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restoreAll(long token, IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(token);
                    _data.writeStrongInterface(observer);
                    _data.writeStrongInterface(monitor);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackages(long token, IRestoreObserver observer, String[] packages, IBackupManagerMonitor monitor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(token);
                    _data.writeStrongInterface(observer);
                    _data.writeStringArray(packages);
                    _data.writeStrongInterface(monitor);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackage(String packageName, IRestoreObserver observer, IBackupManagerMonitor monitor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(observer);
                    _data.writeStrongInterface(monitor);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public void endRestoreSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}

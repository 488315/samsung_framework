package android.os;

import android.os.IDumpstateListener;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public interface IDumpstate extends IInterface {
    public static final int BUGREPORT_FLAG_DEFER_CONSENT = 2;
    public static final int BUGREPORT_FLAG_KEEP_BUGREPORT_ON_RETRIEVAL = 4;
    public static final int BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA = 1;
    public static final int BUGREPORT_MODE_APP_ANR = 16;
    public static final int BUGREPORT_MODE_APP_ERROR = 14;
    public static final int BUGREPORT_MODE_APP_NATIVE = 15;
    public static final int BUGREPORT_MODE_BOOT_DELAY = 8;
    public static final int BUGREPORT_MODE_BOOT_ENOSPC = 9;
    public static final int BUGREPORT_MODE_BY_KEY = 21;
    public static final int BUGREPORT_MODE_DEFAULT = 6;
    public static final int BUGREPORT_MODE_ENOSPC = 18;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_LIGHT = 17;
    public static final int BUGREPORT_MODE_ONBOARDING = 7;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_SHUTDOWN_BROADCAST = 19;
    public static final int BUGREPORT_MODE_SHUTDOWN_DELAY = 20;
    public static final int BUGREPORT_MODE_SYS_ERROR = 11;
    public static final int BUGREPORT_MODE_SYS_NATIVE = 12;
    public static final int BUGREPORT_MODE_SYS_RESCUE = 10;
    public static final int BUGREPORT_MODE_SYS_WATCHDOG = 13;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;
    public static final String DESCRIPTOR = "android.os.IDumpstate";

    void cancelBugreport(int i, String str) throws RemoteException;

    void preDumpUiData(String str) throws RemoteException;

    void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException;

    void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException;

    public static class Default implements IDumpstate {
        @Override // android.os.IDumpstate
        public void preDumpUiData(String callingPackage) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void startBugreport(int callingUid, String callingPackage, FileDescriptor bugreportFd, FileDescriptor screenshotFd, int bugreportMode, int bugreportFlags, IDumpstateListener listener, boolean isScreenshotRequested, boolean skipUserConsent) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void cancelBugreport(int callingUid, String callingPackage) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void retrieveBugreport(int callingUid, String callingPackage, int userId, FileDescriptor bugreportFd, String bugreportFile, boolean keepBugreportOnRetrieval, boolean skipUserConsent, IDumpstateListener listener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDumpstate {
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_preDumpUiData = 1;
        static final int TRANSACTION_retrieveBugreport = 4;
        static final int TRANSACTION_startBugreport = 2;

        public Stub() {
            attachInterface(this, IDumpstate.DESCRIPTOR);
        }

        public static IDumpstate asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDumpstate.DESCRIPTOR);
            if (iin != null && (iin instanceof IDumpstate)) {
                return (IDumpstate) iin;
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
                    return "preDumpUiData";
                case 2:
                    return "startBugreport";
                case 3:
                    return "cancelBugreport";
                case 4:
                    return "retrieveBugreport";
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
                data.enforceInterface(IDumpstate.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDumpstate.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    preDumpUiData(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    FileDescriptor _arg2 = data.readRawFileDescriptor();
                    FileDescriptor _arg3 = data.readRawFileDescriptor();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    IDumpstateListener _arg6 = IDumpstateListener.Stub.asInterface(data.readStrongBinder());
                    boolean _arg7 = data.readBoolean();
                    boolean _arg8 = data.readBoolean();
                    data.enforceNoDataAvail();
                    startBugreport(_arg02, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    cancelBugreport(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    String _arg13 = data.readString();
                    int _arg22 = data.readInt();
                    FileDescriptor _arg32 = data.readRawFileDescriptor();
                    String _arg42 = data.readString();
                    boolean _arg52 = data.readBoolean();
                    boolean _arg62 = data.readBoolean();
                    IDumpstateListener _arg72 = IDumpstateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    retrieveBugreport(_arg04, _arg13, _arg22, _arg32, _arg42, _arg52, _arg62, _arg72);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDumpstate {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDumpstate.DESCRIPTOR;
            }

            @Override // android.os.IDumpstate
            public void preDumpUiData(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void startBugreport(int callingUid, String callingPackage, FileDescriptor bugreportFd, FileDescriptor screenshotFd, int bugreportMode, int bugreportFlags, IDumpstateListener listener, boolean isScreenshotRequested, boolean skipUserConsent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    _data.writeInt(callingUid);
                    _data.writeString(callingPackage);
                    _data.writeRawFileDescriptor(bugreportFd);
                    _data.writeRawFileDescriptor(screenshotFd);
                    _data.writeInt(bugreportMode);
                    _data.writeInt(bugreportFlags);
                    _data.writeStrongInterface(listener);
                    _data.writeBoolean(isScreenshotRequested);
                    _data.writeBoolean(skipUserConsent);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void cancelBugreport(int callingUid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    _data.writeInt(callingUid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void retrieveBugreport(int callingUid, String callingPackage, int userId, FileDescriptor bugreportFd, String bugreportFile, boolean keepBugreportOnRetrieval, boolean skipUserConsent, IDumpstateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    _data.writeInt(callingUid);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeRawFileDescriptor(bugreportFd);
                    _data.writeString(bugreportFile);
                    _data.writeBoolean(keepBugreportOnRetrieval);
                    _data.writeBoolean(skipUserConsent);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}

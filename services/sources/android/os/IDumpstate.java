package android.os;

import android.os.IDumpstateListener;
import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IDumpstate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IDumpstate
        public void cancelBugreport(int i, String str) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void preDumpUiData(String str) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IDumpstate {
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_preDumpUiData = 1;
        static final int TRANSACTION_retrieveBugreport = 4;
        static final int TRANSACTION_startBugreport = 2;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IDumpstate {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.os.IDumpstate
            public final void cancelBugreport(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public final void preDumpUiData(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public final void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iDumpstateListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public final void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeRawFileDescriptor(fileDescriptor2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iDumpstateListener);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDumpstate.DESCRIPTOR);
        }

        public static IDumpstate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDumpstate.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDumpstate)) {
                return (IDumpstate) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDumpstate.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDumpstate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                preDumpUiData(readString);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                FileDescriptor readRawFileDescriptor2 = parcel.readRawFileDescriptor();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                IDumpstateListener asInterface = IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                startBugreport(readInt, readString2, readRawFileDescriptor, readRawFileDescriptor2, readInt2, readInt3, asInterface, readBoolean, readBoolean2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                cancelBugreport(readInt4, readString3);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt5 = parcel.readInt();
                String readString4 = parcel.readString();
                int readInt6 = parcel.readInt();
                FileDescriptor readRawFileDescriptor3 = parcel.readRawFileDescriptor();
                String readString5 = parcel.readString();
                boolean readBoolean3 = parcel.readBoolean();
                boolean readBoolean4 = parcel.readBoolean();
                IDumpstateListener asInterface2 = IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                retrieveBugreport(readInt5, readString4, readInt6, readRawFileDescriptor3, readString5, readBoolean3, readBoolean4, asInterface2);
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void cancelBugreport(int i, String str) throws RemoteException;

    void preDumpUiData(String str) throws RemoteException;

    void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException;

    void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException;
}

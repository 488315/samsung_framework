package android.os;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IDumpstateListener extends IInterface {
    public static final int BUGREPORT_ERROR_ANOTHER_REPORT_IN_PROGRESS = 5;
    public static final int BUGREPORT_ERROR_INVALID_INPUT = 1;
    public static final int BUGREPORT_ERROR_NO_BUGREPORT_TO_RETRIEVE = 6;
    public static final int BUGREPORT_ERROR_RUNTIME_ERROR = 2;
    public static final int BUGREPORT_ERROR_USER_CONSENT_TIMED_OUT = 4;
    public static final int BUGREPORT_ERROR_USER_DENIED_CONSENT = 3;
    public static final String DESCRIPTOR = "android.os.IDumpstateListener";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IDumpstateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IDumpstateListener
        public void onError(int i) throws RemoteException {
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(String str) throws RemoteException {
        }

        @Override // android.os.IDumpstateListener
        public void onProgress(int i) throws RemoteException {
        }

        @Override // android.os.IDumpstateListener
        public void onScreenshotTaken(boolean z) throws RemoteException {
        }

        @Override // android.os.IDumpstateListener
        public void onUiIntensiveBugreportDumpsFinished() throws RemoteException {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IDumpstateListener {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onFinished = 3;
        static final int TRANSACTION_onProgress = 1;
        static final int TRANSACTION_onScreenshotTaken = 4;
        static final int TRANSACTION_onUiIntensiveBugreportDumpsFinished = 5;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IDumpstateListener {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.os.IDumpstateListener
            public final void onError(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IDumpstateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstateListener
            public final void onFinished(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IDumpstateListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstateListener
            public final void onProgress(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IDumpstateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstateListener
            public final void onScreenshotTaken(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IDumpstateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstateListener
            public final void onUiIntensiveBugreportDumpsFinished() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IDumpstateListener.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDumpstateListener.DESCRIPTOR);
        }

        public static IDumpstateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDumpstateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDumpstateListener)) {
                return (IDumpstateListener) queryLocalInterface;
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
                parcel.enforceInterface(IDumpstateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDumpstateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onProgress(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt2);
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onFinished(readString);
            } else if (i == 4) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onScreenshotTaken(readBoolean);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onUiIntensiveBugreportDumpsFinished();
            }
            return true;
        }
    }

    void onError(int i) throws RemoteException;

    void onFinished(String str) throws RemoteException;

    void onProgress(int i) throws RemoteException;

    void onScreenshotTaken(boolean z) throws RemoteException;

    void onUiIntensiveBugreportDumpsFinished() throws RemoteException;
}

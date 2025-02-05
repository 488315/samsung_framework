package android.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IModelDownloadListener extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IModelDownloadListener";

    void onError(int i) throws RemoteException;

    void onProgress(int i) throws RemoteException;

    void onScheduled() throws RemoteException;

    void onSuccess() throws RemoteException;

    public static class Default implements IModelDownloadListener {
        @Override // android.speech.IModelDownloadListener
        public void onProgress(int completedPercent) throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onSuccess() throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onScheduled() throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onError(int error) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IModelDownloadListener {
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onProgress = 1;
        static final int TRANSACTION_onScheduled = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IModelDownloadListener.DESCRIPTOR);
        }

        public static IModelDownloadListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IModelDownloadListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IModelDownloadListener)) {
                return (IModelDownloadListener) iin;
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
                    return "onProgress";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onScheduled";
                case 4:
                    return "onError";
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
                data.enforceInterface(IModelDownloadListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IModelDownloadListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onProgress(_arg0);
                    return true;
                case 2:
                    onSuccess();
                    return true;
                case 3:
                    onScheduled();
                    return true;
                case 4:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IModelDownloadListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IModelDownloadListener.DESCRIPTOR;
            }

            @Override // android.speech.IModelDownloadListener
            public void onProgress(int completedPercent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    _data.writeInt(completedPercent);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onScheduled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    _data.writeInt(error);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
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

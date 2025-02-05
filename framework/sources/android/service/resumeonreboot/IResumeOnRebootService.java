package android.service.resumeonreboot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IResumeOnRebootService extends IInterface {
    public static final String DESCRIPTOR = "android.service.resumeonreboot.IResumeOnRebootService";

    void unwrap(byte[] bArr, RemoteCallback remoteCallback) throws RemoteException;

    void wrapSecret(byte[] bArr, long j, RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IResumeOnRebootService {
        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void wrapSecret(byte[] unwrappedBlob, long lifeTimeInMillis, RemoteCallback resultCallback) throws RemoteException {
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void unwrap(byte[] wrappedBlob, RemoteCallback resultCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IResumeOnRebootService {
        static final int TRANSACTION_unwrap = 2;
        static final int TRANSACTION_wrapSecret = 1;

        public Stub() {
            attachInterface(this, IResumeOnRebootService.DESCRIPTOR);
        }

        public static IResumeOnRebootService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResumeOnRebootService.DESCRIPTOR);
            if (iin != null && (iin instanceof IResumeOnRebootService)) {
                return (IResumeOnRebootService) iin;
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
                    return "wrapSecret";
                case 2:
                    return "unwrap";
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
                data.enforceInterface(IResumeOnRebootService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IResumeOnRebootService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    byte[] _arg0 = data.createByteArray();
                    long _arg1 = data.readLong();
                    RemoteCallback _arg2 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    wrapSecret(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    byte[] _arg02 = data.createByteArray();
                    RemoteCallback _arg12 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    unwrap(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IResumeOnRebootService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResumeOnRebootService.DESCRIPTOR;
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void wrapSecret(byte[] unwrappedBlob, long lifeTimeInMillis, RemoteCallback resultCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResumeOnRebootService.DESCRIPTOR);
                    _data.writeByteArray(unwrappedBlob);
                    _data.writeLong(lifeTimeInMillis);
                    _data.writeTypedObject(resultCallback, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.resumeonreboot.IResumeOnRebootService
            public void unwrap(byte[] wrappedBlob, RemoteCallback resultCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResumeOnRebootService.DESCRIPTOR);
                    _data.writeByteArray(wrappedBlob);
                    _data.writeTypedObject(resultCallback, 0);
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

package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IApplicationStartInfoCompleteListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IApplicationStartInfoCompleteListener";

    void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IApplicationStartInfoCompleteListener {
        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IApplicationStartInfoCompleteListener {
        static final int TRANSACTION_onApplicationStartInfoComplete = 1;

        public Stub() {
            attachInterface(this, IApplicationStartInfoCompleteListener.DESCRIPTOR);
        }

        public static IApplicationStartInfoCompleteListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IApplicationStartInfoCompleteListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IApplicationStartInfoCompleteListener)) {
                return (IApplicationStartInfoCompleteListener) iin;
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
                    return "onApplicationStartInfoComplete";
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
                data.enforceInterface(IApplicationStartInfoCompleteListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IApplicationStartInfoCompleteListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ApplicationStartInfo _arg0 = (ApplicationStartInfo) data.readTypedObject(ApplicationStartInfo.CREATOR);
                            data.enforceNoDataAvail();
                            onApplicationStartInfoComplete(_arg0);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IApplicationStartInfoCompleteListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApplicationStartInfoCompleteListener.DESCRIPTOR;
            }

            @Override // android.app.IApplicationStartInfoCompleteListener
            public void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IApplicationStartInfoCompleteListener.DESCRIPTOR);
                    _data.writeTypedObject(applicationStartInfo, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
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

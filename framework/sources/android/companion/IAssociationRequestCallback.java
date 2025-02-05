package android.companion;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes.dex */
public interface IAssociationRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IAssociationRequestCallback";

    void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException;

    void onAssociationPending(PendingIntent pendingIntent) throws RemoteException;

    void onFailure(CharSequence charSequence) throws RemoteException;

    public static class Default implements IAssociationRequestCallback {
        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationPending(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onFailure(CharSequence error) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAssociationRequestCallback {
        static final int TRANSACTION_onAssociationCreated = 2;
        static final int TRANSACTION_onAssociationPending = 1;
        static final int TRANSACTION_onFailure = 3;

        public Stub() {
            attachInterface(this, IAssociationRequestCallback.DESCRIPTOR);
        }

        public static IAssociationRequestCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAssociationRequestCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAssociationRequestCallback)) {
                return (IAssociationRequestCallback) iin;
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
                    return "onAssociationPending";
                case 2:
                    return "onAssociationCreated";
                case 3:
                    return "onFailure";
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
                data.enforceInterface(IAssociationRequestCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAssociationRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PendingIntent _arg0 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    onAssociationPending(_arg0);
                    return true;
                case 2:
                    AssociationInfo _arg02 = (AssociationInfo) data.readTypedObject(AssociationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAssociationCreated(_arg02);
                    return true;
                case 3:
                    CharSequence _arg03 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAssociationRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAssociationRequestCallback.DESCRIPTOR;
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationPending(PendingIntent pendingIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    _data.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onFailure(CharSequence error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    if (error != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(error, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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

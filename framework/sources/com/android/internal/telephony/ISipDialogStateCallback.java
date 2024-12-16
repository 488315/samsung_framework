package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SipDialogState;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISipDialogStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISipDialogStateCallback";

    void onActiveSipDialogsChanged(List<SipDialogState> list) throws RemoteException;

    public static class Default implements ISipDialogStateCallback {
        @Override // com.android.internal.telephony.ISipDialogStateCallback
        public void onActiveSipDialogsChanged(List<SipDialogState> dialogs) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISipDialogStateCallback {
        static final int TRANSACTION_onActiveSipDialogsChanged = 1;

        public Stub() {
            attachInterface(this, ISipDialogStateCallback.DESCRIPTOR);
        }

        public static ISipDialogStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISipDialogStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISipDialogStateCallback)) {
                return (ISipDialogStateCallback) iin;
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
                    return "onActiveSipDialogsChanged";
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
                data.enforceInterface(ISipDialogStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISipDialogStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SipDialogState> _arg0 = data.createTypedArrayList(SipDialogState.CREATOR);
                    data.enforceNoDataAvail();
                    onActiveSipDialogsChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISipDialogStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDialogStateCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISipDialogStateCallback
            public void onActiveSipDialogsChanged(List<SipDialogState> dialogs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDialogStateCallback.DESCRIPTOR);
                    _data.writeTypedList(dialogs, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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

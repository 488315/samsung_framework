package android.service.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.EuiccInfo;

/* loaded from: classes3.dex */
public interface IGetEuiccInfoCallback extends IInterface {
    void onSuccess(EuiccInfo euiccInfo) throws RemoteException;

    public static class Default implements IGetEuiccInfoCallback {
        @Override // android.service.euicc.IGetEuiccInfoCallback
        public void onSuccess(EuiccInfo euiccInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGetEuiccInfoCallback {
        public static final String DESCRIPTOR = "android.service.euicc.IGetEuiccInfoCallback";
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGetEuiccInfoCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IGetEuiccInfoCallback)) {
                return (IGetEuiccInfoCallback) iin;
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
                    return "onSuccess";
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
                    EuiccInfo _arg0 = (EuiccInfo) data.readTypedObject(EuiccInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetEuiccInfoCallback {
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

            @Override // android.service.euicc.IGetEuiccInfoCallback
            public void onSuccess(EuiccInfo euiccInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(euiccInfo, 0);
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

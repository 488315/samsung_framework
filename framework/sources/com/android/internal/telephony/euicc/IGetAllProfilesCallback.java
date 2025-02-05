package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

/* loaded from: classes5.dex */
public interface IGetAllProfilesCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetAllProfilesCallback";

    void onComplete(int i, EuiccProfileInfo[] euiccProfileInfoArr) throws RemoteException;

    public static class Default implements IGetAllProfilesCallback {
        @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
        public void onComplete(int resultCode, EuiccProfileInfo[] profiles) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGetAllProfilesCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IGetAllProfilesCallback.DESCRIPTOR);
        }

        public static IGetAllProfilesCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetAllProfilesCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetAllProfilesCallback)) {
                return (IGetAllProfilesCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IGetAllProfilesCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetAllProfilesCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    EuiccProfileInfo[] _arg1 = (EuiccProfileInfo[]) data.createTypedArray(EuiccProfileInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetAllProfilesCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetAllProfilesCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
            public void onComplete(int resultCode, EuiccProfileInfo[] profiles) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetAllProfilesCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeTypedArray(profiles, 0);
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

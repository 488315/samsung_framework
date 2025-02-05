package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IWwanSelectorResultCallback;

/* loaded from: classes5.dex */
public interface IWwanSelectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IWwanSelectorCallback";

    void onCancel() throws RemoteException;

    void onDomainSelected(int i, boolean z) throws RemoteException;

    void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, IWwanSelectorResultCallback iWwanSelectorResultCallback) throws RemoteException;

    public static class Default implements IWwanSelectorCallback {
        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onRequestEmergencyNetworkScan(int[] preferredNetworks, int scanType, boolean resetScan, IWwanSelectorResultCallback cb) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onDomainSelected(int domain, boolean useEmergencyPdn) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onCancel() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWwanSelectorCallback {
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onDomainSelected = 2;
        static final int TRANSACTION_onRequestEmergencyNetworkScan = 1;

        public Stub() {
            attachInterface(this, IWwanSelectorCallback.DESCRIPTOR);
        }

        public static IWwanSelectorCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWwanSelectorCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWwanSelectorCallback)) {
                return (IWwanSelectorCallback) iin;
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
                    return "onRequestEmergencyNetworkScan";
                case 2:
                    return "onDomainSelected";
                case 3:
                    return "onCancel";
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
                data.enforceInterface(IWwanSelectorCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWwanSelectorCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    int _arg1 = data.readInt();
                    boolean _arg2 = data.readBoolean();
                    IWwanSelectorResultCallback _arg3 = IWwanSelectorResultCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onRequestEmergencyNetworkScan(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onDomainSelected(_arg02, _arg12);
                    return true;
                case 3:
                    onCancel();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWwanSelectorCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWwanSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onRequestEmergencyNetworkScan(int[] preferredNetworks, int scanType, boolean resetScan, IWwanSelectorResultCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    _data.writeIntArray(preferredNetworks);
                    _data.writeInt(scanType);
                    _data.writeBoolean(resetScan);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onDomainSelected(int domain, boolean useEmergencyPdn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
                    _data.writeInt(domain);
                    _data.writeBoolean(useEmergencyPdn);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onCancel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWwanSelectorCallback.DESCRIPTOR);
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

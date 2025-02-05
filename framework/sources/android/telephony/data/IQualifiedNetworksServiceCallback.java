package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IIntegerConsumer;

/* loaded from: classes4.dex */
public interface IQualifiedNetworksServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksServiceCallback";

    void onHandoverEnabledChanged(int i) throws RemoteException;

    void onNetworkValidationRequested(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws RemoteException;

    void onReconnectQualifiedNetworkType(int i, int i2) throws RemoteException;

    public static class Default implements IQualifiedNetworksServiceCallback {
        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onQualifiedNetworkTypesChanged(int apnTypes, int[] qualifiedNetworkTypes) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onNetworkValidationRequested(int networkCapability, IIntegerConsumer callback) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onReconnectQualifiedNetworkType(int apnTypes, int qualifiedNetworkType) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onHandoverEnabledChanged(int supportedApnTypes) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IQualifiedNetworksServiceCallback {
        static final int TRANSACTION_onHandoverEnabledChanged = 4;
        static final int TRANSACTION_onNetworkValidationRequested = 2;
        static final int TRANSACTION_onQualifiedNetworkTypesChanged = 1;
        static final int TRANSACTION_onReconnectQualifiedNetworkType = 3;

        public Stub() {
            attachInterface(this, IQualifiedNetworksServiceCallback.DESCRIPTOR);
        }

        public static IQualifiedNetworksServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IQualifiedNetworksServiceCallback)) {
                return (IQualifiedNetworksServiceCallback) iin;
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
                    return "onQualifiedNetworkTypesChanged";
                case 2:
                    return "onNetworkValidationRequested";
                case 3:
                    return "onReconnectQualifiedNetworkType";
                case 4:
                    return "onHandoverEnabledChanged";
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
                data.enforceInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    onQualifiedNetworkTypesChanged(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    IIntegerConsumer _arg12 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onNetworkValidationRequested(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onReconnectQualifiedNetworkType(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onHandoverEnabledChanged(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IQualifiedNetworksServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IQualifiedNetworksServiceCallback.DESCRIPTOR;
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onQualifiedNetworkTypesChanged(int apnTypes, int[] qualifiedNetworkTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    _data.writeInt(apnTypes);
                    _data.writeIntArray(qualifiedNetworkTypes);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onNetworkValidationRequested(int networkCapability, IIntegerConsumer callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    _data.writeInt(networkCapability);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onReconnectQualifiedNetworkType(int apnTypes, int qualifiedNetworkType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    _data.writeInt(apnTypes);
                    _data.writeInt(qualifiedNetworkType);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onHandoverEnabledChanged(int supportedApnTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    _data.writeInt(supportedApnTypes);
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

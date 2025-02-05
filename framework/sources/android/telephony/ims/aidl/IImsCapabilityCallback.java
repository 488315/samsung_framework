package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsCapabilityCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsCapabilityCallback";

    void onCapabilitiesStatusChanged(int i) throws RemoteException;

    void onChangeCapabilityConfigurationError(int i, int i2, int i3) throws RemoteException;

    void onQueryCapabilityConfiguration(int i, int i2, boolean z) throws RemoteException;

    public static class Default implements IImsCapabilityCallback {
        @Override // android.telephony.ims.aidl.IImsCapabilityCallback
        public void onQueryCapabilityConfiguration(int capability, int radioTech, boolean enabled) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCapabilityCallback
        public void onChangeCapabilityConfigurationError(int capability, int radioTech, int reason) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCapabilityCallback
        public void onCapabilitiesStatusChanged(int config) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsCapabilityCallback {
        static final int TRANSACTION_onCapabilitiesStatusChanged = 3;
        static final int TRANSACTION_onChangeCapabilityConfigurationError = 2;
        static final int TRANSACTION_onQueryCapabilityConfiguration = 1;

        public Stub() {
            attachInterface(this, IImsCapabilityCallback.DESCRIPTOR);
        }

        public static IImsCapabilityCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsCapabilityCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsCapabilityCallback)) {
                return (IImsCapabilityCallback) iin;
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
                    return "onQueryCapabilityConfiguration";
                case 2:
                    return "onChangeCapabilityConfigurationError";
                case 3:
                    return "onCapabilitiesStatusChanged";
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
                data.enforceInterface(IImsCapabilityCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImsCapabilityCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onQueryCapabilityConfiguration(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    onChangeCapabilityConfigurationError(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onCapabilitiesStatusChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsCapabilityCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsCapabilityCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onQueryCapabilityConfiguration(int capability, int radioTech, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsCapabilityCallback.DESCRIPTOR);
                    _data.writeInt(capability);
                    _data.writeInt(radioTech);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onChangeCapabilityConfigurationError(int capability, int radioTech, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsCapabilityCallback.DESCRIPTOR);
                    _data.writeInt(capability);
                    _data.writeInt(radioTech);
                    _data.writeInt(reason);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onCapabilitiesStatusChanged(int config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsCapabilityCallback.DESCRIPTOR);
                    _data.writeInt(config);
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

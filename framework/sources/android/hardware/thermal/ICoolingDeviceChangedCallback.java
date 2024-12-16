package android.hardware.thermal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICoolingDeviceChangedCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$thermal$ICoolingDeviceChangedCallback".replace('$', '.');
    public static final String HASH = "2f49c78011338b42b43d5d0e250d9b520850cc1f";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void notifyCoolingDeviceChanged(CoolingDevice coolingDevice) throws RemoteException;

    public static class Default implements ICoolingDeviceChangedCallback {
        @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
        public void notifyCoolingDeviceChanged(CoolingDevice coolingDevice) throws RemoteException {
        }

        @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICoolingDeviceChangedCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyCoolingDeviceChanged = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ICoolingDeviceChangedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICoolingDeviceChangedCallback)) {
                return (ICoolingDeviceChangedCallback) iin;
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
                    return "notifyCoolingDeviceChanged";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    CoolingDevice _arg0 = (CoolingDevice) data.readTypedObject(CoolingDevice.CREATOR);
                    data.enforceNoDataAvail();
                    notifyCoolingDeviceChanged(_arg0);
                    break;
            }
            return true;
        }

        private static class Proxy implements ICoolingDeviceChangedCallback {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
            public void notifyCoolingDeviceChanged(CoolingDevice coolingDevice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(coolingDevice, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyCoolingDeviceChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.thermal.ICoolingDeviceChangedCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}

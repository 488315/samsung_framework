package com.samsung.android.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiAwareManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.aware.ISemWifiAwareManager";

    int getMaxNdpCountForAwareP2p() throws RemoteException;

    int getMaxNdpCountForAwareSoftAp() throws RemoteException;

    long getStdPlusFeature() throws RemoteException;

    boolean isAwareP2pConcurrencySupported() throws RemoteException;

    boolean isAwareSoftApConcurrencySupported() throws RemoteException;

    int isPreEnabled() throws RemoteException;

    boolean isVendorNanServiceAvailable() throws RemoteException;

    void setClusterMergingEnabled(boolean z) throws RemoteException;

    void setNanCommand(int i, byte[] bArr) throws RemoteException;

    public static class Default implements ISemWifiAwareManager {
        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public void setClusterMergingEnabled(boolean enable) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int isPreEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public void setNanCommand(int cmdId, byte[] cmd) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isVendorNanServiceAvailable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public long getStdPlusFeature() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isAwareSoftApConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isAwareP2pConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int getMaxNdpCountForAwareP2p() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int getMaxNdpCountForAwareSoftAp() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemWifiAwareManager {
        static final int TRANSACTION_getMaxNdpCountForAwareP2p = 8;
        static final int TRANSACTION_getMaxNdpCountForAwareSoftAp = 9;
        static final int TRANSACTION_getStdPlusFeature = 5;
        static final int TRANSACTION_isAwareP2pConcurrencySupported = 7;
        static final int TRANSACTION_isAwareSoftApConcurrencySupported = 6;
        static final int TRANSACTION_isPreEnabled = 2;
        static final int TRANSACTION_isVendorNanServiceAvailable = 4;
        static final int TRANSACTION_setClusterMergingEnabled = 1;
        static final int TRANSACTION_setNanCommand = 3;

        public Stub() {
            attachInterface(this, ISemWifiAwareManager.DESCRIPTOR);
        }

        public static ISemWifiAwareManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiAwareManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiAwareManager)) {
                return (ISemWifiAwareManager) iin;
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
                    return "setClusterMergingEnabled";
                case 2:
                    return "isPreEnabled";
                case 3:
                    return "setNanCommand";
                case 4:
                    return "isVendorNanServiceAvailable";
                case 5:
                    return "getStdPlusFeature";
                case 6:
                    return "isAwareSoftApConcurrencySupported";
                case 7:
                    return "isAwareP2pConcurrencySupported";
                case 8:
                    return "getMaxNdpCountForAwareP2p";
                case 9:
                    return "getMaxNdpCountForAwareSoftAp";
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
                data.enforceInterface(ISemWifiAwareManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiAwareManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setClusterMergingEnabled(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _result = isPreEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setNanCommand(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean _result2 = isVendorNanServiceAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 5:
                    long _result3 = getStdPlusFeature();
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 6:
                    boolean _result4 = isAwareSoftApConcurrencySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 7:
                    boolean _result5 = isAwareP2pConcurrencySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 8:
                    int _result6 = getMaxNdpCountForAwareP2p();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 9:
                    int _result7 = getMaxNdpCountForAwareSoftAp();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemWifiAwareManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiAwareManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public void setClusterMergingEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int isPreEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public void setNanCommand(int cmdId, byte[] cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    _data.writeInt(cmdId);
                    _data.writeByteArray(cmd);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isVendorNanServiceAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public long getStdPlusFeature() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isAwareSoftApConcurrencySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isAwareP2pConcurrencySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int getMaxNdpCountForAwareP2p() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int getMaxNdpCountForAwareSoftAp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}

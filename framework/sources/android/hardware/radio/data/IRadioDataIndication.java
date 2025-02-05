package android.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioDataIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$data$IRadioDataIndication".replace('$', '.');
    public static final String HASH = "cd8913a3f9d39f1cc0a5fcf9e90257be94ec38df";
    public static final int VERSION = 3;

    void dataCallListChanged(int i, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void keepaliveStatus(int i, KeepaliveStatus keepaliveStatus) throws RemoteException;

    void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException;

    void slicingConfigChanged(int i, SlicingConfig slicingConfig) throws RemoteException;

    void unthrottleApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException;

    public static class Default implements IRadioDataIndication {
        @Override // android.hardware.radio.data.IRadioDataIndication
        public void dataCallListChanged(int type, SetupDataCallResult[] dcList) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void keepaliveStatus(int type, KeepaliveStatus status) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void pcoData(int type, PcoDataInfo pco) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void unthrottleApn(int type, DataProfileInfo dataProfileInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void slicingConfigChanged(int type, SlicingConfig slicingConfig) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRadioDataIndication {
        static final int TRANSACTION_dataCallListChanged = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_keepaliveStatus = 2;
        static final int TRANSACTION_pcoData = 3;
        static final int TRANSACTION_slicingConfigChanged = 5;
        static final int TRANSACTION_unthrottleApn = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioDataIndication asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRadioDataIndication)) {
                return (IRadioDataIndication) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
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
                    int _arg0 = data.readInt();
                    SetupDataCallResult[] _arg1 = (SetupDataCallResult[]) data.createTypedArray(SetupDataCallResult.CREATOR);
                    data.enforceNoDataAvail();
                    dataCallListChanged(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    KeepaliveStatus _arg12 = (KeepaliveStatus) data.readTypedObject(KeepaliveStatus.CREATOR);
                    data.enforceNoDataAvail();
                    keepaliveStatus(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    PcoDataInfo _arg13 = (PcoDataInfo) data.readTypedObject(PcoDataInfo.CREATOR);
                    data.enforceNoDataAvail();
                    pcoData(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    DataProfileInfo _arg14 = (DataProfileInfo) data.readTypedObject(DataProfileInfo.CREATOR);
                    data.enforceNoDataAvail();
                    unthrottleApn(_arg04, _arg14);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    SlicingConfig _arg15 = (SlicingConfig) data.readTypedObject(SlicingConfig.CREATOR);
                    data.enforceNoDataAvail();
                    slicingConfigChanged(_arg05, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRadioDataIndication {
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

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void dataCallListChanged(int type, SetupDataCallResult[] dcList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedArray(dcList, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method dataCallListChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void keepaliveStatus(int type, KeepaliveStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(status, 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method keepaliveStatus is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void pcoData(int type, PcoDataInfo pco) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(pco, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method pcoData is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void unthrottleApn(int type, DataProfileInfo dataProfileInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(dataProfileInfo, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method unthrottleApn is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void slicingConfigChanged(int type, SlicingConfig slicingConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(slicingConfig, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method slicingConfigChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
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

            @Override // android.hardware.radio.data.IRadioDataIndication
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
    }
}

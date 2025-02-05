package android.hardware.tv.tuner;

import android.hardware.tv.tuner.IDvr;
import android.hardware.tv.tuner.IDvrCallback;
import android.hardware.tv.tuner.IFilter;
import android.hardware.tv.tuner.IFilterCallback;
import android.hardware.tv.tuner.ITimeFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDemux extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IDemux".replace('$', '.');
    public static final String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void close() throws RemoteException;

    void connectCiCam(int i) throws RemoteException;

    void disconnectCiCam() throws RemoteException;

    int getAvSyncHwId(IFilter iFilter) throws RemoteException;

    long getAvSyncTime(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    IDvr openDvr(byte b, int i, IDvrCallback iDvrCallback) throws RemoteException;

    IFilter openFilter(DemuxFilterType demuxFilterType, int i, IFilterCallback iFilterCallback) throws RemoteException;

    ITimeFilter openTimeFilter() throws RemoteException;

    void setFrontendDataSource(int i) throws RemoteException;

    public static class Default implements IDemux {
        @Override // android.hardware.tv.tuner.IDemux
        public void setFrontendDataSource(int frontendId) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public IFilter openFilter(DemuxFilterType type, int bufferSize, IFilterCallback cb) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public ITimeFilter openTimeFilter() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getAvSyncHwId(IFilter filter) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public long getAvSyncTime(int avSyncHwId) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public IDvr openDvr(byte type, int bufferSize, IDvrCallback cb) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void connectCiCam(int ciCamId) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void disconnectCiCam() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDemux {
        static final int TRANSACTION_close = 6;
        static final int TRANSACTION_connectCiCam = 8;
        static final int TRANSACTION_disconnectCiCam = 9;
        static final int TRANSACTION_getAvSyncHwId = 4;
        static final int TRANSACTION_getAvSyncTime = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openDvr = 7;
        static final int TRANSACTION_openFilter = 2;
        static final int TRANSACTION_openTimeFilter = 3;
        static final int TRANSACTION_setFrontendDataSource = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IDemux asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDemux)) {
                return (IDemux) iin;
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
                    data.enforceNoDataAvail();
                    setFrontendDataSource(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    DemuxFilterType _arg02 = (DemuxFilterType) data.readTypedObject(DemuxFilterType.CREATOR);
                    int _arg1 = data.readInt();
                    IFilterCallback _arg2 = IFilterCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    IFilter _result = openFilter(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 3:
                    ITimeFilter _result2 = openTimeFilter();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 4:
                    IFilter _arg03 = IFilter.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = getAvSyncHwId(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 5:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result4 = getAvSyncTime(_arg04);
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    return true;
                case 6:
                    close();
                    reply.writeNoException();
                    return true;
                case 7:
                    byte _arg05 = data.readByte();
                    int _arg12 = data.readInt();
                    IDvrCallback _arg22 = IDvrCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    IDvr _result5 = openDvr(_arg05, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    connectCiCam(_arg06);
                    reply.writeNoException();
                    return true;
                case 9:
                    disconnectCiCam();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDemux {
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

            @Override // android.hardware.tv.tuner.IDemux
            public void setFrontendDataSource(int frontendId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(frontendId);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setFrontendDataSource is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public IFilter openFilter(DemuxFilterType type, int bufferSize, IFilterCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(type, 0);
                    _data.writeInt(bufferSize);
                    _data.writeStrongInterface(cb);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method openFilter is unimplemented.");
                    }
                    _reply.readException();
                    IFilter _result = IFilter.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public ITimeFilter openTimeFilter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method openTimeFilter is unimplemented.");
                    }
                    _reply.readException();
                    ITimeFilter _result = ITimeFilter.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public int getAvSyncHwId(IFilter filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(filter);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getAvSyncHwId is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public long getAvSyncTime(int avSyncHwId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(avSyncHwId);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getAvSyncTime is unimplemented.");
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public IDvr openDvr(byte type, int bufferSize, IDvrCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(type);
                    _data.writeInt(bufferSize);
                    _data.writeStrongInterface(cb);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method openDvr is unimplemented.");
                    }
                    _reply.readException();
                    IDvr _result = IDvr.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void connectCiCam(int ciCamId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(ciCamId);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method connectCiCam is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void disconnectCiCam() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method disconnectCiCam is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
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

            @Override // android.hardware.tv.tuner.IDemux
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

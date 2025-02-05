package android.hardware.tv.tuner;

import android.hardware.common.NativeHandle;
import android.hardware.common.fmq.MQDescriptor;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFilter extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IFilter".replace('$', '.');
    public static final String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void close() throws RemoteException;

    void configure(DemuxFilterSettings demuxFilterSettings) throws RemoteException;

    void configureAvStreamType(AvStreamType avStreamType) throws RemoteException;

    void configureIpCid(int i) throws RemoteException;

    void configureMonitorEvent(int i) throws RemoteException;

    void flush() throws RemoteException;

    long getAvSharedHandle(NativeHandle nativeHandle) throws RemoteException;

    int getId() throws RemoteException;

    long getId64Bit() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException;

    void releaseAvHandle(NativeHandle nativeHandle, long j) throws RemoteException;

    void setDataSource(IFilter iFilter) throws RemoteException;

    void setDelayHint(FilterDelayHint filterDelayHint) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    public static class Default implements IFilter {
        @Override // android.hardware.tv.tuner.IFilter
        public void getQueueDesc(MQDescriptor<Byte, Byte> queue) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configure(DemuxFilterSettings settings) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureAvStreamType(AvStreamType avStreamType) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureIpCid(int ipCid) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureMonitorEvent(int monitorEventTypes) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void start() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void stop() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void flush() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getAvSharedHandle(NativeHandle avMemory) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getId64Bit() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void releaseAvHandle(NativeHandle avMemory, long avDataId) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDataSource(IFilter filter) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDelayHint(FilterDelayHint hint) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFilter {
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_configure = 3;
        static final int TRANSACTION_configureAvStreamType = 4;
        static final int TRANSACTION_configureIpCid = 5;
        static final int TRANSACTION_configureMonitorEvent = 6;
        static final int TRANSACTION_flush = 9;
        static final int TRANSACTION_getAvSharedHandle = 10;
        static final int TRANSACTION_getId = 11;
        static final int TRANSACTION_getId64Bit = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getQueueDesc = 1;
        static final int TRANSACTION_releaseAvHandle = 13;
        static final int TRANSACTION_setDataSource = 14;
        static final int TRANSACTION_setDelayHint = 15;
        static final int TRANSACTION_start = 7;
        static final int TRANSACTION_stop = 8;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFilter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IFilter)) {
                return (IFilter) iin;
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
                    MQDescriptor<Byte, Byte> _arg0 = new MQDescriptor<>();
                    data.enforceNoDataAvail();
                    getQueueDesc(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg0, 1);
                    return true;
                case 2:
                    close();
                    reply.writeNoException();
                    return true;
                case 3:
                    DemuxFilterSettings _arg02 = (DemuxFilterSettings) data.readTypedObject(DemuxFilterSettings.CREATOR);
                    data.enforceNoDataAvail();
                    configure(_arg02);
                    reply.writeNoException();
                    return true;
                case 4:
                    AvStreamType _arg03 = (AvStreamType) data.readTypedObject(AvStreamType.CREATOR);
                    data.enforceNoDataAvail();
                    configureAvStreamType(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    configureIpCid(_arg04);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    configureMonitorEvent(_arg05);
                    reply.writeNoException();
                    return true;
                case 7:
                    start();
                    reply.writeNoException();
                    return true;
                case 8:
                    stop();
                    reply.writeNoException();
                    return true;
                case 9:
                    flush();
                    reply.writeNoException();
                    return true;
                case 10:
                    NativeHandle _arg06 = new NativeHandle();
                    data.enforceNoDataAvail();
                    long _result = getAvSharedHandle(_arg06);
                    reply.writeNoException();
                    reply.writeLong(_result);
                    reply.writeTypedObject(_arg06, 1);
                    return true;
                case 11:
                    int _result2 = getId();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 12:
                    long _result3 = getId64Bit();
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 13:
                    NativeHandle _arg07 = (NativeHandle) data.readTypedObject(NativeHandle.CREATOR);
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    releaseAvHandle(_arg07, _arg1);
                    reply.writeNoException();
                    return true;
                case 14:
                    IFilter _arg08 = asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setDataSource(_arg08);
                    reply.writeNoException();
                    return true;
                case 15:
                    FilterDelayHint _arg09 = (FilterDelayHint) data.readTypedObject(FilterDelayHint.CREATOR);
                    data.enforceNoDataAvail();
                    setDelayHint(_arg09);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFilter {
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

            @Override // android.hardware.tv.tuner.IFilter
            public void getQueueDesc(MQDescriptor<Byte, Byte> queue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getQueueDesc is unimplemented.");
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        queue.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configure(DemuxFilterSettings settings) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(settings, 0);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method configure is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureAvStreamType(AvStreamType avStreamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(avStreamType, 0);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method configureAvStreamType is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureIpCid(int ipCid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(ipCid);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method configureIpCid is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureMonitorEvent(int monitorEventTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(monitorEventTypes);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method configureMonitorEvent is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void start() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void stop() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void flush() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method flush is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getAvSharedHandle(NativeHandle avMemory) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getAvSharedHandle is unimplemented.");
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    if (_reply.readInt() != 0) {
                        avMemory.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public int getId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getId is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getId64Bit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getId64Bit is unimplemented.");
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void releaseAvHandle(NativeHandle avMemory, long avDataId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(avMemory, 0);
                    _data.writeLong(avDataId);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method releaseAvHandle is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDataSource(IFilter filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(filter);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setDataSource is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDelayHint(FilterDelayHint hint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hint, 0);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setDelayHint is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
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

            @Override // android.hardware.tv.tuner.IFilter
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

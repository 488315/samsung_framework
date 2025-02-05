package android.hardware.biometrics.fingerprint;

import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFingerprint extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$fingerprint$IFingerprint".replace('$', '.');
    public static final String HASH = "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    public static final int VERSION = 4;

    ISession createSession(int i, int i2, ISessionCallback iSessionCallback) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    SensorProps[] getSensorProps() throws RemoteException;

    public static class Default implements IFingerprint {
        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public SensorProps[] getSensorProps() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public ISession createSession(int sensorId, int userId, ISessionCallback cb) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFingerprint {
        static final int TRANSACTION_createSession = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSensorProps = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprint asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IFingerprint)) {
                return (IFingerprint) iin;
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
                    return "getSensorProps";
                case 2:
                    return "createSession";
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
                    SensorProps[] _result = getSensorProps();
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    ISessionCallback _arg2 = ISessionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    ISession _result2 = createSession(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFingerprint {
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

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
            public SensorProps[] getSensorProps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getSensorProps is unimplemented.");
                    }
                    _reply.readException();
                    SensorProps[] _result = (SensorProps[]) _reply.createTypedArray(SensorProps.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
            public ISession createSession(int sensorId, int userId, ISessionCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(cb);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method createSession is unimplemented.");
                    }
                    _reply.readException();
                    ISession _result = ISession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
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

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
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

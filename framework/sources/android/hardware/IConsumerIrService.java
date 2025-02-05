package android.hardware;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IConsumerIrService extends IInterface {
    int[] getCarrierFrequencies() throws RemoteException;

    boolean hasIrEmitter() throws RemoteException;

    void transmit(String str, int i, int[] iArr) throws RemoteException;

    public static class Default implements IConsumerIrService {
        @Override // android.hardware.IConsumerIrService
        public boolean hasIrEmitter() throws RemoteException {
            return false;
        }

        @Override // android.hardware.IConsumerIrService
        public void transmit(String packageName, int carrierFrequency, int[] pattern) throws RemoteException {
        }

        @Override // android.hardware.IConsumerIrService
        public int[] getCarrierFrequencies() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IConsumerIrService {
        public static final String DESCRIPTOR = "android.hardware.IConsumerIrService";
        static final int TRANSACTION_getCarrierFrequencies = 3;
        static final int TRANSACTION_hasIrEmitter = 1;
        static final int TRANSACTION_transmit = 2;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IConsumerIrService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IConsumerIrService)) {
                return (IConsumerIrService) iin;
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
                    return "hasIrEmitter";
                case 2:
                    return "transmit";
                case 3:
                    return "getCarrierFrequencies";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = hasIrEmitter();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int[] _arg2 = data.createIntArray();
                    data.enforceNoDataAvail();
                    transmit(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    int[] _result2 = getCarrierFrequencies();
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IConsumerIrService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.IConsumerIrService
            public boolean hasIrEmitter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public void transmit(String packageName, int carrierFrequency, int[] pattern) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(carrierFrequency);
                    _data.writeIntArray(pattern);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public int[] getCarrierFrequencies() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void transmit_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }

        protected void getCarrierFrequencies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}

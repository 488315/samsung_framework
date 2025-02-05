package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAccessibilityEmbeddedConnection extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityEmbeddedConnection";

    IBinder associateEmbeddedHierarchy(IBinder iBinder, int i) throws RemoteException;

    void disassociateEmbeddedHierarchy() throws RemoteException;

    void setWindowMatrix(float[] fArr) throws RemoteException;

    public static class Default implements IAccessibilityEmbeddedConnection {
        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public IBinder associateEmbeddedHierarchy(IBinder hostToken, int sourceId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void disassociateEmbeddedHierarchy() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void setWindowMatrix(float[] matrixValues) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityEmbeddedConnection {
        static final int TRANSACTION_associateEmbeddedHierarchy = 1;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 2;
        static final int TRANSACTION_setWindowMatrix = 3;

        public Stub() {
            attachInterface(this, IAccessibilityEmbeddedConnection.DESCRIPTOR);
        }

        public static IAccessibilityEmbeddedConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAccessibilityEmbeddedConnection.DESCRIPTOR);
            if (iin != null && (iin instanceof IAccessibilityEmbeddedConnection)) {
                return (IAccessibilityEmbeddedConnection) iin;
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
                    return "associateEmbeddedHierarchy";
                case 2:
                    return "disassociateEmbeddedHierarchy";
                case 3:
                    return "setWindowMatrix";
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
                data.enforceInterface(IAccessibilityEmbeddedConnection.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    IBinder _result = associateEmbeddedHierarchy(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result);
                    return true;
                case 2:
                    disassociateEmbeddedHierarchy();
                    reply.writeNoException();
                    return true;
                case 3:
                    float[] _arg02 = data.createFloatArray();
                    data.enforceNoDataAvail();
                    setWindowMatrix(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAccessibilityEmbeddedConnection {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccessibilityEmbeddedConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public IBinder associateEmbeddedHierarchy(IBinder hostToken, int sourceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    _data.writeStrongBinder(hostToken);
                    _data.writeInt(sourceId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void disassociateEmbeddedHierarchy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void setWindowMatrix(float[] matrixValues) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    _data.writeFloatArray(matrixValues);
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

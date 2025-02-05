package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMultiFingerGestureListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IMultiFingerGestureListener";

    void onMultiFingerGesture(int i, int i2) throws RemoteException;

    public static class Default implements IMultiFingerGestureListener {
        @Override // android.hardware.input.IMultiFingerGestureListener
        public void onMultiFingerGesture(int behavior, int reserved) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMultiFingerGestureListener {
        static final int TRANSACTION_onMultiFingerGesture = 1;

        public Stub() {
            attachInterface(this, IMultiFingerGestureListener.DESCRIPTOR);
        }

        public static IMultiFingerGestureListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMultiFingerGestureListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IMultiFingerGestureListener)) {
                return (IMultiFingerGestureListener) iin;
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
                    return "onMultiFingerGesture";
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
                data.enforceInterface(IMultiFingerGestureListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMultiFingerGestureListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onMultiFingerGesture(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMultiFingerGestureListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMultiFingerGestureListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IMultiFingerGestureListener
            public void onMultiFingerGesture(int behavior, int reserved) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMultiFingerGestureListener.DESCRIPTOR);
                    _data.writeInt(behavior);
                    _data.writeInt(reserved);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}

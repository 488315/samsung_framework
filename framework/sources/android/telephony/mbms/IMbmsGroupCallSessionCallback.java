package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsGroupCallSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.mbms.IMbmsGroupCallSessionCallback";

    void onAvailableSaisUpdated(List list, List list2) throws RemoteException;

    void onError(int i, String str) throws RemoteException;

    void onMiddlewareReady() throws RemoteException;

    void onServiceInterfaceAvailable(String str, int i) throws RemoteException;

    public static class Default implements IMbmsGroupCallSessionCallback {
        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onError(int errorCode, String message) throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onAvailableSaisUpdated(List currentSai, List availableSais) throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onServiceInterfaceAvailable(String interfaceName, int index) throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onMiddlewareReady() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMbmsGroupCallSessionCallback {
        static final int TRANSACTION_onAvailableSaisUpdated = 2;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onMiddlewareReady = 4;
        static final int TRANSACTION_onServiceInterfaceAvailable = 3;

        public Stub() {
            attachInterface(this, IMbmsGroupCallSessionCallback.DESCRIPTOR);
        }

        public static IMbmsGroupCallSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMbmsGroupCallSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IMbmsGroupCallSessionCallback)) {
                return (IMbmsGroupCallSessionCallback) iin;
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
                    return "onError";
                case 2:
                    return "onAvailableSaisUpdated";
                case 3:
                    return "onServiceInterfaceAvailable";
                case 4:
                    return "onMiddlewareReady";
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
                data.enforceInterface(IMbmsGroupCallSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg0, _arg1);
                    return true;
                case 2:
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg02 = data.readArrayList(cl);
                    List _arg12 = data.readArrayList(cl);
                    data.enforceNoDataAvail();
                    onAvailableSaisUpdated(_arg02, _arg12);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onServiceInterfaceAvailable(_arg03, _arg13);
                    return true;
                case 4:
                    onMiddlewareReady();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMbmsGroupCallSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMbmsGroupCallSessionCallback.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onError(int errorCode, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(message);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onAvailableSaisUpdated(List currentSai, List availableSais) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    _data.writeList(currentSai);
                    _data.writeList(availableSais);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onServiceInterfaceAvailable(String interfaceName, int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    _data.writeString(interfaceName);
                    _data.writeInt(index);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onMiddlewareReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}

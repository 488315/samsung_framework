package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes4.dex */
public interface IOptionsResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IOptionsResponseCallback";

    void onCommandError(int i) throws RemoteException;

    void onNetworkResponse(int i, String str, List<String> list) throws RemoteException;

    public static class Default implements IOptionsResponseCallback {
        @Override // android.telephony.ims.aidl.IOptionsResponseCallback
        public void onCommandError(int code) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IOptionsResponseCallback
        public void onNetworkResponse(int code, String reason, List<String> theirCaps) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOptionsResponseCallback {
        static final int TRANSACTION_onCommandError = 1;
        static final int TRANSACTION_onNetworkResponse = 2;

        public Stub() {
            attachInterface(this, IOptionsResponseCallback.DESCRIPTOR);
        }

        public static IOptionsResponseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOptionsResponseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IOptionsResponseCallback)) {
                return (IOptionsResponseCallback) iin;
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
                    return "onCommandError";
                case 2:
                    return "onNetworkResponse";
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
                data.enforceInterface(IOptionsResponseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOptionsResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onCommandError(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    List<String> _arg2 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    onNetworkResponse(_arg02, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOptionsResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOptionsResponseCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IOptionsResponseCallback
            public void onCommandError(int code) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOptionsResponseCallback.DESCRIPTOR);
                    _data.writeInt(code);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IOptionsResponseCallback
            public void onNetworkResponse(int code, String reason, List<String> theirCaps) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOptionsResponseCallback.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeString(reason);
                    _data.writeStringList(theirCaps);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}

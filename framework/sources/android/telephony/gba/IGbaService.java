package android.telephony.gba;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IGbaService extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.gba.IGbaService";

    void authenticationRequest(GbaAuthRequest gbaAuthRequest) throws RemoteException;

    public static class Default implements IGbaService {
        @Override // android.telephony.gba.IGbaService
        public void authenticationRequest(GbaAuthRequest request) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGbaService {
        static final int TRANSACTION_authenticationRequest = 1;

        public Stub() {
            attachInterface(this, IGbaService.DESCRIPTOR);
        }

        public static IGbaService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGbaService.DESCRIPTOR);
            if (iin != null && (iin instanceof IGbaService)) {
                return (IGbaService) iin;
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
                    return "authenticationRequest";
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
                data.enforceInterface(IGbaService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGbaService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    GbaAuthRequest _arg0 = (GbaAuthRequest) data.readTypedObject(GbaAuthRequest.CREATOR);
                    data.enforceNoDataAvail();
                    authenticationRequest(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGbaService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGbaService.DESCRIPTOR;
            }

            @Override // android.telephony.gba.IGbaService
            public void authenticationRequest(GbaAuthRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGbaService.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
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

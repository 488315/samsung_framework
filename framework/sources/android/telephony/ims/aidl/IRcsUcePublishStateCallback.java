package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.PublishAttributes;

/* loaded from: classes4.dex */
public interface IRcsUcePublishStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcsUcePublishStateCallback";

    void onPublishUpdated(PublishAttributes publishAttributes) throws RemoteException;

    public static class Default implements IRcsUcePublishStateCallback {
        @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
        public void onPublishUpdated(PublishAttributes attributes) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRcsUcePublishStateCallback {
        static final int TRANSACTION_onPublishUpdated = 1;

        public Stub() {
            attachInterface(this, IRcsUcePublishStateCallback.DESCRIPTOR);
        }

        public static IRcsUcePublishStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRcsUcePublishStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRcsUcePublishStateCallback)) {
                return (IRcsUcePublishStateCallback) iin;
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
                    return "onPublishUpdated";
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
                data.enforceInterface(IRcsUcePublishStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRcsUcePublishStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PublishAttributes _arg0 = (PublishAttributes) data.readTypedObject(PublishAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    onPublishUpdated(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRcsUcePublishStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRcsUcePublishStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
            public void onPublishUpdated(PublishAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRcsUcePublishStateCallback.DESCRIPTOR);
                    _data.writeTypedObject(attributes, 0);
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

package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.DelegateRegistrationState;
import android.telephony.ims.FeatureTagState;
import android.telephony.ims.SipDelegateConfiguration;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.telephony.ims.aidl.ISipDelegate;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISipDelegateStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegateStateCallback";

    void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException;

    void onCreated(ISipDelegate iSipDelegate, List<FeatureTagState> list) throws RemoteException;

    void onDestroyed(int i) throws RemoteException;

    void onFeatureTagRegistrationChanged(DelegateRegistrationState delegateRegistrationState) throws RemoteException;

    void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException;

    public static class Default implements ISipDelegateStateCallback {
        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onCreated(ISipDelegate c, List<FeatureTagState> deniedFeatureTags) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onFeatureTagRegistrationChanged(DelegateRegistrationState registrationState) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onImsConfigurationChanged(SipDelegateImsConfiguration registeredSipConfig) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onConfigurationChanged(SipDelegateConfiguration registeredSipConfig) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onDestroyed(int reason) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISipDelegateStateCallback {
        static final int TRANSACTION_onConfigurationChanged = 4;
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onDestroyed = 5;
        static final int TRANSACTION_onFeatureTagRegistrationChanged = 2;
        static final int TRANSACTION_onImsConfigurationChanged = 3;

        public Stub() {
            attachInterface(this, ISipDelegateStateCallback.DESCRIPTOR);
        }

        public static ISipDelegateStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISipDelegateStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISipDelegateStateCallback)) {
                return (ISipDelegateStateCallback) iin;
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
                    return "onCreated";
                case 2:
                    return "onFeatureTagRegistrationChanged";
                case 3:
                    return "onImsConfigurationChanged";
                case 4:
                    return "onConfigurationChanged";
                case 5:
                    return "onDestroyed";
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
                data.enforceInterface(ISipDelegateStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISipDelegateStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ISipDelegate _arg0 = ISipDelegate.Stub.asInterface(data.readStrongBinder());
                    List<FeatureTagState> _arg1 = data.createTypedArrayList(FeatureTagState.CREATOR);
                    data.enforceNoDataAvail();
                    onCreated(_arg0, _arg1);
                    return true;
                case 2:
                    DelegateRegistrationState _arg02 = (DelegateRegistrationState) data.readTypedObject(DelegateRegistrationState.CREATOR);
                    data.enforceNoDataAvail();
                    onFeatureTagRegistrationChanged(_arg02);
                    return true;
                case 3:
                    SipDelegateImsConfiguration _arg03 = (SipDelegateImsConfiguration) data.readTypedObject(SipDelegateImsConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    onImsConfigurationChanged(_arg03);
                    return true;
                case 4:
                    SipDelegateConfiguration _arg04 = (SipDelegateConfiguration) data.readTypedObject(SipDelegateConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    onConfigurationChanged(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    onDestroyed(_arg05);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISipDelegateStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegateStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onCreated(ISipDelegate c, List<FeatureTagState> deniedFeatureTags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    _data.writeTypedList(deniedFeatureTags, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onFeatureTagRegistrationChanged(DelegateRegistrationState registrationState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    _data.writeTypedObject(registrationState, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onImsConfigurationChanged(SipDelegateImsConfiguration registeredSipConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    _data.writeTypedObject(registeredSipConfig, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onConfigurationChanged(SipDelegateConfiguration registeredSipConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    _data.writeTypedObject(registeredSipConfig, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onDestroyed(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}

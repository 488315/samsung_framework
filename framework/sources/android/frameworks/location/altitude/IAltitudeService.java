package android.frameworks.location.altitude;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAltitudeService extends IInterface {
    public static final String DESCRIPTOR = "android$frameworks$location$altitude$IAltitudeService".replace('$', '.');
    public static final String HASH = "e47d23f579ff7a897fb03e7e7f1c3006cfc6036b";
    public static final int VERSION = 2;

    AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws RemoteException;

    GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest getGeoidHeightRequest) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    public static class Default implements IAltitudeService {
        @Override // android.frameworks.location.altitude.IAltitudeService
        public AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest request) throws RemoteException {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest request) throws RemoteException {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAltitudeService {
        static final int TRANSACTION_addMslAltitudeToLocation = 1;
        static final int TRANSACTION_getGeoidHeight = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IAltitudeService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAltitudeService)) {
                return (IAltitudeService) iin;
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
                    return "addMslAltitudeToLocation";
                case 2:
                    return "getGeoidHeight";
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
                    AddMslAltitudeToLocationRequest _arg0 = (AddMslAltitudeToLocationRequest) data.readTypedObject(AddMslAltitudeToLocationRequest.CREATOR);
                    data.enforceNoDataAvail();
                    AddMslAltitudeToLocationResponse _result = addMslAltitudeToLocation(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    GetGeoidHeightRequest _arg02 = (GetGeoidHeightRequest) data.readTypedObject(GetGeoidHeightRequest.CREATOR);
                    data.enforceNoDataAvail();
                    GetGeoidHeightResponse _result2 = getGeoidHeight(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAltitudeService {
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

            @Override // android.frameworks.location.altitude.IAltitudeService
            public AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method addMslAltitudeToLocation is unimplemented.");
                    }
                    _reply.readException();
                    AddMslAltitudeToLocationResponse _result = (AddMslAltitudeToLocationResponse) _reply.readTypedObject(AddMslAltitudeToLocationResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getGeoidHeight is unimplemented.");
                    }
                    _reply.readException();
                    GetGeoidHeightResponse _result = (GetGeoidHeightResponse) _reply.readTypedObject(GetGeoidHeightResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
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

            @Override // android.frameworks.location.altitude.IAltitudeService
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

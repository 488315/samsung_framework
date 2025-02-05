package android.service.timezone;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.timezone.ITimeZoneProviderManager;

/* loaded from: classes3.dex */
public interface ITimeZoneProvider extends IInterface {
    public static final String DESCRIPTOR = "android.service.timezone.ITimeZoneProvider";

    void startUpdates(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws RemoteException;

    void stopUpdates() throws RemoteException;

    public static class Default implements ITimeZoneProvider {
        @Override // android.service.timezone.ITimeZoneProvider
        public void startUpdates(ITimeZoneProviderManager manager, long initializationTimeoutMillis, long eventFilteringAgeThresholdMillis) throws RemoteException {
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void stopUpdates() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITimeZoneProvider {
        static final int TRANSACTION_startUpdates = 1;
        static final int TRANSACTION_stopUpdates = 2;

        public Stub() {
            attachInterface(this, ITimeZoneProvider.DESCRIPTOR);
        }

        public static ITimeZoneProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITimeZoneProvider.DESCRIPTOR);
            if (iin != null && (iin instanceof ITimeZoneProvider)) {
                return (ITimeZoneProvider) iin;
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
                    return "startUpdates";
                case 2:
                    return "stopUpdates";
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
                data.enforceInterface(ITimeZoneProvider.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITimeZoneProvider.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ITimeZoneProviderManager _arg0 = ITimeZoneProviderManager.Stub.asInterface(data.readStrongBinder());
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    startUpdates(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    stopUpdates();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITimeZoneProvider {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneProvider.DESCRIPTOR;
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void startUpdates(ITimeZoneProviderManager manager, long initializationTimeoutMillis, long eventFilteringAgeThresholdMillis) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITimeZoneProvider.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeLong(initializationTimeoutMillis);
                    _data.writeLong(eventFilteringAgeThresholdMillis);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void stopUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITimeZoneProvider.DESCRIPTOR);
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

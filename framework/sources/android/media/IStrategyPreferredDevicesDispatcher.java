package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IStrategyPreferredDevicesDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IStrategyPreferredDevicesDispatcher";

    void dispatchPrefDevicesChanged(int i, List<AudioDeviceAttributes> list) throws RemoteException;

    public static class Default implements IStrategyPreferredDevicesDispatcher {
        @Override // android.media.IStrategyPreferredDevicesDispatcher
        public void dispatchPrefDevicesChanged(int strategyId, List<AudioDeviceAttributes> devices) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStrategyPreferredDevicesDispatcher {
        static final int TRANSACTION_dispatchPrefDevicesChanged = 1;

        public Stub() {
            attachInterface(this, IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
        }

        public static IStrategyPreferredDevicesDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof IStrategyPreferredDevicesDispatcher)) {
                return (IStrategyPreferredDevicesDispatcher) iin;
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
                    return "dispatchPrefDevicesChanged";
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
                data.enforceInterface(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    List<AudioDeviceAttributes> _arg1 = data.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchPrefDevicesChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStrategyPreferredDevicesDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStrategyPreferredDevicesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStrategyPreferredDevicesDispatcher
            public void dispatchPrefDevicesChanged(int strategyId, List<AudioDeviceAttributes> devices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
                    _data.writeInt(strategyId);
                    _data.writeTypedList(devices, 0);
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

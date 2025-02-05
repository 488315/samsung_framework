package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceVolumeBehaviorDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IDeviceVolumeBehaviorDispatcher";

    void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException;

    public static class Default implements IDeviceVolumeBehaviorDispatcher {
        @Override // android.media.IDeviceVolumeBehaviorDispatcher
        public void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes device, int deviceVolumeBehavior) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDeviceVolumeBehaviorDispatcher {
        static final int TRANSACTION_dispatchDeviceVolumeBehaviorChanged = 1;

        public Stub() {
            attachInterface(this, IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
        }

        public static IDeviceVolumeBehaviorDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof IDeviceVolumeBehaviorDispatcher)) {
                return (IDeviceVolumeBehaviorDispatcher) iin;
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
                    return "dispatchDeviceVolumeBehaviorChanged";
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
                data.enforceInterface(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AudioDeviceAttributes _arg0 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchDeviceVolumeBehaviorChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDeviceVolumeBehaviorDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceVolumeBehaviorDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IDeviceVolumeBehaviorDispatcher
            public void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes device, int deviceVolumeBehavior) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(deviceVolumeBehavior);
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

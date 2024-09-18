package android.hardware;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRemoteDeviceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IRemoteDeviceCallback";
    public static final int ERROR_REMOTE_BUFFER = 1;
    public static final int ERROR_REMOTE_DEVICE = 0;
    public static final int ERROR_REMOTE_UNKNOWN = 2;

    void onCaptureResult(CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onOrientationChanged(int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IRemoteDeviceCallback {
        @Override // android.hardware.IRemoteDeviceCallback
        public void onCaptureResult(CameraMetadataNative result) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDeviceCallback
        public void onError(int errorCode) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDeviceCallback
        public void onOrientationChanged(int orientation) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IRemoteDeviceCallback {
        static final int TRANSACTION_onCaptureResult = 1;
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onOrientationChanged = 3;

        public Stub() {
            attachInterface(this, IRemoteDeviceCallback.DESCRIPTOR);
        }

        public static IRemoteDeviceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteDeviceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteDeviceCallback)) {
                return (IRemoteDeviceCallback) iin;
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
                    return "onCaptureResult";
                case 2:
                    return "onError";
                case 3:
                    return "onOrientationChanged";
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
                data.enforceInterface(IRemoteDeviceCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IRemoteDeviceCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            CameraMetadataNative _arg0 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                            data.enforceNoDataAvail();
                            onCaptureResult(_arg0);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            onError(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            onOrientationChanged(_arg03);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IRemoteDeviceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteDeviceCallback.DESCRIPTOR;
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onCaptureResult(CameraMetadataNative result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onError(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onOrientationChanged(int orientation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    _data.writeInt(orientation);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}

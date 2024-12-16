package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IProcessResultImpl;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import java.util.List;

/* loaded from: classes2.dex */
public interface ICaptureProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.ICaptureProcessorImpl";

    void onImageFormatUpdate(int i) throws RemoteException;

    void onOutputSurface(Surface surface, int i) throws RemoteException;

    void onPostviewOutputSurface(Surface surface) throws RemoteException;

    void onResolutionUpdate(Size size, Size size2) throws RemoteException;

    void process(List<CaptureBundle> list, IProcessResultImpl iProcessResultImpl, boolean z) throws RemoteException;

    public static class Default implements ICaptureProcessorImpl {
        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onOutputSurface(Surface surface, int imageFormat) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onPostviewOutputSurface(Surface surface) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onResolutionUpdate(Size size, Size postviewSize) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onImageFormatUpdate(int imageFormat) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void process(List<CaptureBundle> capturelist, IProcessResultImpl resultCallback, boolean isPostviewRequested) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICaptureProcessorImpl {
        static final int TRANSACTION_onImageFormatUpdate = 4;
        static final int TRANSACTION_onOutputSurface = 1;
        static final int TRANSACTION_onPostviewOutputSurface = 2;
        static final int TRANSACTION_onResolutionUpdate = 3;
        static final int TRANSACTION_process = 5;

        public Stub() {
            attachInterface(this, ICaptureProcessorImpl.DESCRIPTOR);
        }

        public static ICaptureProcessorImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICaptureProcessorImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof ICaptureProcessorImpl)) {
                return (ICaptureProcessorImpl) iin;
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
                    return "onOutputSurface";
                case 2:
                    return "onPostviewOutputSurface";
                case 3:
                    return "onResolutionUpdate";
                case 4:
                    return "onImageFormatUpdate";
                case 5:
                    return "process";
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
                data.enforceInterface(ICaptureProcessorImpl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICaptureProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Surface _arg0 = (Surface) data.readTypedObject(Surface.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onOutputSurface(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    Surface _arg02 = (Surface) data.readTypedObject(Surface.CREATOR);
                    data.enforceNoDataAvail();
                    onPostviewOutputSurface(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    Size _arg03 = (Size) data.readTypedObject(Size.CREATOR);
                    Size _arg12 = (Size) data.readTypedObject(Size.CREATOR);
                    data.enforceNoDataAvail();
                    onResolutionUpdate(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onImageFormatUpdate(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    List<CaptureBundle> _arg05 = data.createTypedArrayList(CaptureBundle.CREATOR);
                    IProcessResultImpl _arg13 = IProcessResultImpl.Stub.asInterface(data.readStrongBinder());
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    process(_arg05, _arg13, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICaptureProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICaptureProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onOutputSurface(Surface surface, int imageFormat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(surface, 0);
                    _data.writeInt(imageFormat);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onPostviewOutputSurface(Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(surface, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onResolutionUpdate(Size size, Size postviewSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(size, 0);
                    _data.writeTypedObject(postviewSize, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onImageFormatUpdate(int imageFormat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    _data.writeInt(imageFormat);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void process(List<CaptureBundle> capturelist, IProcessResultImpl resultCallback, boolean isPostviewRequested) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    _data.writeTypedList(capturelist, 0);
                    _data.writeStrongInterface(resultCallback);
                    _data.writeBoolean(isPostviewRequested);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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

package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface IAdvancedExtenderImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IAdvancedExtenderImpl";

    CameraMetadataNative getAvailableCaptureRequestKeys(String str) throws RemoteException;

    CameraMetadataNative getAvailableCaptureResultKeys(String str) throws RemoteException;

    CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) throws RemoteException;

    LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) throws RemoteException;

    ISessionProcessorImpl getSessionProcessor() throws RemoteException;

    List<SizeList> getSupportedCaptureOutputResolutions(String str) throws RemoteException;

    List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException;

    List<SizeList> getSupportedPreviewOutputResolutions(String str) throws RemoteException;

    void init(String str, Map<String, CameraMetadataNative> map) throws RemoteException;

    boolean isCaptureProcessProgressAvailable() throws RemoteException;

    boolean isExtensionAvailable(String str, Map<String, CameraMetadataNative> map) throws RemoteException;

    boolean isPostviewAvailable() throws RemoteException;

    public static class Default implements IAdvancedExtenderImpl {
        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(String cameraId, Map<String, CameraMetadataNative> charsMap) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(String cameraId, Map<String, CameraMetadataNative> charsMap) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(String cameraId, Size outputSize, int format) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPreviewOutputResolutions(String cameraId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedCaptureOutputResolutions(String cameraId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size captureSize) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public ISessionProcessorImpl getSessionProcessor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureRequestKeys(String cameraId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureResultKeys(String cameraId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isCaptureProcessProgressAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isPostviewAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCharacteristicsKeyValues(String cameraId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAdvancedExtenderImpl {
        static final int TRANSACTION_getAvailableCaptureRequestKeys = 8;
        static final int TRANSACTION_getAvailableCaptureResultKeys = 9;
        static final int TRANSACTION_getAvailableCharacteristicsKeyValues = 12;
        static final int TRANSACTION_getEstimatedCaptureLatencyRange = 3;
        static final int TRANSACTION_getSessionProcessor = 7;
        static final int TRANSACTION_getSupportedCaptureOutputResolutions = 5;
        static final int TRANSACTION_getSupportedPostviewResolutions = 6;
        static final int TRANSACTION_getSupportedPreviewOutputResolutions = 4;
        static final int TRANSACTION_init = 2;
        static final int TRANSACTION_isCaptureProcessProgressAvailable = 10;
        static final int TRANSACTION_isExtensionAvailable = 1;
        static final int TRANSACTION_isPostviewAvailable = 11;

        public Stub() {
            attachInterface(this, IAdvancedExtenderImpl.DESCRIPTOR);
        }

        public static IAdvancedExtenderImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof IAdvancedExtenderImpl)) {
                return (IAdvancedExtenderImpl) iin;
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
                    return "isExtensionAvailable";
                case 2:
                    return "init";
                case 3:
                    return "getEstimatedCaptureLatencyRange";
                case 4:
                    return "getSupportedPreviewOutputResolutions";
                case 5:
                    return "getSupportedCaptureOutputResolutions";
                case 6:
                    return "getSupportedPostviewResolutions";
                case 7:
                    return "getSessionProcessor";
                case 8:
                    return "getAvailableCaptureRequestKeys";
                case 9:
                    return "getAvailableCaptureResultKeys";
                case 10:
                    return "isCaptureProcessProgressAvailable";
                case 11:
                    return "isPostviewAvailable";
                case 12:
                    return "getAvailableCharacteristicsKeyValues";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, final Parcel data, Parcel reply, int flags) throws RemoteException {
            final Map<String, CameraMetadataNative> _arg1;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAdvancedExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int N = data.readInt();
                    _arg1 = N >= 0 ? new HashMap<>() : null;
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IAdvancedExtenderImpl.Stub.lambda$onTransact$0(Parcel.this, _arg1, i);
                        }
                    });
                    data.enforceNoDataAvail();
                    boolean _result = isExtensionAvailable(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int N2 = data.readInt();
                    _arg1 = N2 >= 0 ? new HashMap<>() : null;
                    IntStream.range(0, N2).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IAdvancedExtenderImpl.Stub.lambda$onTransact$1(Parcel.this, _arg1, i);
                        }
                    });
                    data.enforceNoDataAvail();
                    init(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    Size _arg12 = (Size) data.readTypedObject(Size.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    LatencyRange _result2 = getEstimatedCaptureLatencyRange(_arg03, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    List<SizeList> _result3 = getSupportedPreviewOutputResolutions(_arg04);
                    reply.writeNoException();
                    reply.writeTypedList(_result3, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    List<SizeList> _result4 = getSupportedCaptureOutputResolutions(_arg05);
                    reply.writeNoException();
                    reply.writeTypedList(_result4, 1);
                    return true;
                case 6:
                    Size _arg06 = (Size) data.readTypedObject(Size.CREATOR);
                    data.enforceNoDataAvail();
                    List<SizeList> _result5 = getSupportedPostviewResolutions(_arg06);
                    reply.writeNoException();
                    reply.writeTypedList(_result5, 1);
                    return true;
                case 7:
                    ISessionProcessorImpl _result6 = getSessionProcessor();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result6);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result7 = getAvailableCaptureRequestKeys(_arg07);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result8 = getAvailableCaptureResultKeys(_arg08);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 10:
                    boolean _result9 = isCaptureProcessProgressAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 11:
                    boolean _result10 = isPostviewAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 12:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result11 = getAvailableCharacteristicsKeyValues(_arg09);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel data, Map _arg1, int i) {
            String k = data.readString();
            CameraMetadataNative v = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
            _arg1.put(k, v);
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel data, Map _arg1, int i) {
            String k = data.readString();
            CameraMetadataNative v = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
            _arg1.put(k, v);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IAdvancedExtenderImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdvancedExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isExtensionAvailable(String cameraId, Map<String, CameraMetadataNative> charsMap) throws RemoteException {
                final Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    if (charsMap == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(charsMap.size());
                        charsMap.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$isExtensionAvailable$0(Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$isExtensionAvailable$0(Parcel _data, String k, CameraMetadataNative v) {
                _data.writeString(k);
                _data.writeTypedObject(v, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public void init(String cameraId, Map<String, CameraMetadataNative> charsMap) throws RemoteException {
                final Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    if (charsMap == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(charsMap.size());
                        charsMap.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$init$1(Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$init$1(Parcel _data, String k, CameraMetadataNative v) {
                _data.writeString(k);
                _data.writeTypedObject(v, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public LatencyRange getEstimatedCaptureLatencyRange(String cameraId, Size outputSize, int format) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeTypedObject(outputSize, 0);
                    _data.writeInt(format);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    LatencyRange _result = (LatencyRange) _reply.readTypedObject(LatencyRange.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPreviewOutputResolutions(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<SizeList> _result = _reply.createTypedArrayList(SizeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedCaptureOutputResolutions(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    List<SizeList> _result = _reply.createTypedArrayList(SizeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPostviewResolutions(Size captureSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeTypedObject(captureSize, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    List<SizeList> _result = _reply.createTypedArrayList(SizeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public ISessionProcessorImpl getSessionProcessor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    ISessionProcessorImpl _result = ISessionProcessorImpl.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureRequestKeys(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureResultKeys(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isPostviewAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCharacteristicsKeyValues(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}

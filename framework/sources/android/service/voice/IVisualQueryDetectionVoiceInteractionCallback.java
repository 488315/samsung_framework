package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IVisualQueryDetectionVoiceInteractionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IVisualQueryDetectionVoiceInteractionCallback";

    void onQueryDetected(String str) throws RemoteException;

    void onQueryFinished() throws RemoteException;

    void onQueryRejected() throws RemoteException;

    void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IVisualQueryDetectionVoiceInteractionCallback {
        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(String partialQuery) throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IVisualQueryDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onQueryDetected = 1;
        static final int TRANSACTION_onQueryFinished = 2;
        static final int TRANSACTION_onQueryRejected = 3;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 4;

        public Stub() {
            attachInterface(this, IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static IVisualQueryDetectionVoiceInteractionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IVisualQueryDetectionVoiceInteractionCallback)) {
                return (IVisualQueryDetectionVoiceInteractionCallback) iin;
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
                    return "onQueryDetected";
                case 2:
                    return "onQueryFinished";
                case 3:
                    return "onQueryRejected";
                case 4:
                    return "onVisualQueryDetectionServiceFailure";
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
                data.enforceInterface(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onQueryDetected(_arg0);
                            return true;
                        case 2:
                            onQueryFinished();
                            return true;
                        case 3:
                            onQueryRejected();
                            return true;
                        case 4:
                            VisualQueryDetectionServiceFailure _arg02 = (VisualQueryDetectionServiceFailure) data.readTypedObject(VisualQueryDetectionServiceFailure.CREATOR);
                            data.enforceNoDataAvail();
                            onVisualQueryDetectionServiceFailure(_arg02);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class Proxy implements IVisualQueryDetectionVoiceInteractionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryDetected(String partialQuery) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    _data.writeString(partialQuery);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryFinished() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryRejected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    _data.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}

package android.service.appprediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPredictionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.appprediction.IPredictionService";

    void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException;

    void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException;

    void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException;

    void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    public static class Default implements IPredictionService {
        @Override // android.service.appprediction.IPredictionService
        public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestPredictionUpdate(AppPredictionSessionId sessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onDestroyPredictionSession(AppPredictionSessionId sessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestServiceFeatures(AppPredictionSessionId sessionId, IRemoteCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPredictionService {
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onCreatePredictionSession = 1;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_requestServiceFeatures = 9;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        public Stub() {
            attachInterface(this, IPredictionService.DESCRIPTOR);
        }

        public static IPredictionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPredictionService.DESCRIPTOR);
            if (iin != null && (iin instanceof IPredictionService)) {
                return (IPredictionService) iin;
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
                    return "onCreatePredictionSession";
                case 2:
                    return "notifyAppTargetEvent";
                case 3:
                    return "notifyLaunchLocationShown";
                case 4:
                    return "sortAppTargets";
                case 5:
                    return "registerPredictionUpdates";
                case 6:
                    return "unregisterPredictionUpdates";
                case 7:
                    return "requestPredictionUpdate";
                case 8:
                    return "onDestroyPredictionSession";
                case 9:
                    return "requestServiceFeatures";
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
                data.enforceInterface(IPredictionService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPredictionService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AppPredictionContext _arg0 = (AppPredictionContext) data.readTypedObject(AppPredictionContext.CREATOR);
                    AppPredictionSessionId _arg1 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    data.enforceNoDataAvail();
                    onCreatePredictionSession(_arg0, _arg1);
                    return true;
                case 2:
                    AppPredictionSessionId _arg02 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    AppTargetEvent _arg12 = (AppTargetEvent) data.readTypedObject(AppTargetEvent.CREATOR);
                    data.enforceNoDataAvail();
                    notifyAppTargetEvent(_arg02, _arg12);
                    return true;
                case 3:
                    AppPredictionSessionId _arg03 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    String _arg13 = data.readString();
                    ParceledListSlice _arg2 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    data.enforceNoDataAvail();
                    notifyLaunchLocationShown(_arg03, _arg13, _arg2);
                    return true;
                case 4:
                    AppPredictionSessionId _arg04 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    ParceledListSlice _arg14 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    IPredictionCallback _arg22 = IPredictionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    sortAppTargets(_arg04, _arg14, _arg22);
                    return true;
                case 5:
                    AppPredictionSessionId _arg05 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback _arg15 = IPredictionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPredictionUpdates(_arg05, _arg15);
                    return true;
                case 6:
                    AppPredictionSessionId _arg06 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback _arg16 = IPredictionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPredictionUpdates(_arg06, _arg16);
                    return true;
                case 7:
                    AppPredictionSessionId _arg07 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    data.enforceNoDataAvail();
                    requestPredictionUpdate(_arg07);
                    return true;
                case 8:
                    AppPredictionSessionId _arg08 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    data.enforceNoDataAvail();
                    onDestroyPredictionSession(_arg08);
                    return true;
                case 9:
                    AppPredictionSessionId _arg09 = (AppPredictionSessionId) data.readTypedObject(AppPredictionSessionId.CREATOR);
                    IRemoteCallback _arg17 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestServiceFeatures(_arg09, _arg17);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPredictionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPredictionService.DESCRIPTOR;
            }

            @Override // android.service.appprediction.IPredictionService
            public void onCreatePredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(context, 0);
                    _data.writeTypedObject(sessionId, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeString(launchLocation);
                    _data.writeTypedObject(targetIds, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeTypedObject(targets, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestPredictionUpdate(AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void onDestroyPredictionSession(AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestServiceFeatures(AppPredictionSessionId sessionId, IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    _data.writeTypedObject(sessionId, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}

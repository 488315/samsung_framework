package android.app.ambientcontext;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.app.ambientcontext.IAmbientContextObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAmbientContextManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.ambientcontext.IAmbientContextManager";

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException;

    void registerObserver(AmbientContextEventRequest ambientContextEventRequest, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    void registerObserverWithCallback(AmbientContextEventRequest ambientContextEventRequest, String str, IAmbientContextObserver iAmbientContextObserver) throws RemoteException;

    void startConsentActivity(int[] iArr, String str) throws RemoteException;

    void unregisterObserver(String str) throws RemoteException;

    public static class Default implements IAmbientContextManager {
        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserver(AmbientContextEventRequest request, PendingIntent resultPendingIntent, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserverWithCallback(AmbientContextEventRequest request, String packageName, IAmbientContextObserver observer) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void unregisterObserver(String callingPackage) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void queryServiceStatus(int[] eventTypes, String callingPackage, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void startConsentActivity(int[] eventTypes, String callingPackage) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAmbientContextManager {
        static final int TRANSACTION_queryServiceStatus = 4;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_registerObserverWithCallback = 2;
        static final int TRANSACTION_startConsentActivity = 5;
        static final int TRANSACTION_unregisterObserver = 3;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IAmbientContextManager.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAmbientContextManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAmbientContextManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IAmbientContextManager)) {
                return (IAmbientContextManager) iin;
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
                    return "registerObserver";
                case 2:
                    return "registerObserverWithCallback";
                case 3:
                    return "unregisterObserver";
                case 4:
                    return "queryServiceStatus";
                case 5:
                    return "startConsentActivity";
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
                data.enforceInterface(IAmbientContextManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAmbientContextManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AmbientContextEventRequest _arg0 = (AmbientContextEventRequest) data.readTypedObject(AmbientContextEventRequest.CREATOR);
                    PendingIntent _arg1 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    RemoteCallback _arg2 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    registerObserver(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    AmbientContextEventRequest _arg02 = (AmbientContextEventRequest) data.readTypedObject(AmbientContextEventRequest.CREATOR);
                    String _arg12 = data.readString();
                    IAmbientContextObserver _arg22 = IAmbientContextObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerObserverWithCallback(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterObserver(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    int[] _arg04 = data.createIntArray();
                    String _arg13 = data.readString();
                    RemoteCallback _arg23 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    queryServiceStatus(_arg04, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 5:
                    int[] _arg05 = data.createIntArray();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    startConsentActivity(_arg05, _arg14);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAmbientContextManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAmbientContextManager.DESCRIPTOR;
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserver(AmbientContextEventRequest request, PendingIntent resultPendingIntent, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeTypedObject(resultPendingIntent, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserverWithCallback(AmbientContextEventRequest request, String packageName, IAmbientContextObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void unregisterObserver(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void queryServiceStatus(int[] eventTypes, String callingPackage, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    _data.writeIntArray(eventTypes);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void startConsentActivity(int[] eventTypes, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    _data.writeIntArray(eventTypes);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void unregisterObserver_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_AMBIENT_CONTEXT_EVENT, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}

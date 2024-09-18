package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IKnoxAnalyticsProxy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy";

    void log(KnoxAnalyticsData knoxAnalyticsData) throws RemoteException;

    /* loaded from: classes5.dex */
    public static class Default implements IKnoxAnalyticsProxy {
        @Override // com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy
        public void log(KnoxAnalyticsData data) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class Stub extends Binder implements IKnoxAnalyticsProxy {
        static final int TRANSACTION_log = 1;

        public Stub() {
            attachInterface(this, IKnoxAnalyticsProxy.DESCRIPTOR);
        }

        public static IKnoxAnalyticsProxy asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKnoxAnalyticsProxy.DESCRIPTOR);
            if (iin != null && (iin instanceof IKnoxAnalyticsProxy)) {
                return (IKnoxAnalyticsProxy) iin;
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
                    return "log";
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
                data.enforceInterface(IKnoxAnalyticsProxy.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IKnoxAnalyticsProxy.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            KnoxAnalyticsData _arg0 = (KnoxAnalyticsData) data.readTypedObject(KnoxAnalyticsData.CREATOR);
                            data.enforceNoDataAvail();
                            log(_arg0);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* loaded from: classes5.dex */
        private static class Proxy implements IKnoxAnalyticsProxy {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxAnalyticsProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy
            public void log(KnoxAnalyticsData data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxAnalyticsProxy.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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

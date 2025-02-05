package com.android.internal.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes5.dex */
public interface IAppWidgetHost extends IInterface {
    void appWidgetRemoved(int i) throws RemoteException;

    void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException;

    void providersChanged() throws RemoteException;

    void updateAppWidget(int i, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetDeferred(int i) throws RemoteException;

    void viewDataChanged(int i, int i2) throws RemoteException;

    public static class Default implements IAppWidgetHost {
        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidgetDeferred(int appWidgetId) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int appWidgetId, RemoteViews views) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int appWidgetId, AppWidgetProviderInfo info) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void appWidgetRemoved(int appWidgetId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppWidgetHost {
        public static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetHost";
        static final int TRANSACTION_appWidgetRemoved = 6;
        static final int TRANSACTION_providerChanged = 3;
        static final int TRANSACTION_providersChanged = 4;
        static final int TRANSACTION_updateAppWidget = 2;
        static final int TRANSACTION_updateAppWidgetDeferred = 1;
        static final int TRANSACTION_viewDataChanged = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetHost asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAppWidgetHost)) {
                return (IAppWidgetHost) iin;
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
                    return "updateAppWidgetDeferred";
                case 2:
                    return "updateAppWidget";
                case 3:
                    return "providerChanged";
                case 4:
                    return "providersChanged";
                case 5:
                    return "viewDataChanged";
                case 6:
                    return "appWidgetRemoved";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    updateAppWidgetDeferred(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    RemoteViews _arg1 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    updateAppWidget(_arg02, _arg1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    AppWidgetProviderInfo _arg12 = (AppWidgetProviderInfo) data.readTypedObject(AppWidgetProviderInfo.CREATOR);
                    data.enforceNoDataAvail();
                    providerChanged(_arg03, _arg12);
                    return true;
                case 4:
                    providersChanged();
                    return true;
                case 5:
                    int _arg04 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    viewDataChanged(_arg04, _arg13);
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    appWidgetRemoved(_arg05);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppWidgetHost {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidgetDeferred(int appWidgetId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidget(int appWidgetId, RemoteViews views) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    _data.writeTypedObject(views, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providerChanged(int appWidgetId, AppWidgetProviderInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providersChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void viewDataChanged(int appWidgetId, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(viewId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void appWidgetRemoved(int appWidgetId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}

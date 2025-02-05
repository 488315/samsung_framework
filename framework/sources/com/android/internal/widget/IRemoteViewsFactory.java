package com.android.internal.widget;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes5.dex */
public interface IRemoteViewsFactory extends IInterface {
    int getCount() throws RemoteException;

    long getItemId(int i) throws RemoteException;

    RemoteViews getLoadingView() throws RemoteException;

    RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i) throws RemoteException;

    RemoteViews getViewAt(int i) throws RemoteException;

    int getViewTypeCount() throws RemoteException;

    boolean hasStableIds() throws RemoteException;

    boolean isCreated() throws RemoteException;

    void onDataSetChanged() throws RemoteException;

    void onDataSetChangedAsync() throws RemoteException;

    void onDestroy(Intent intent) throws RemoteException;

    public static class Default implements IRemoteViewsFactory {
        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDataSetChanged() throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDataSetChangedAsync() throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDestroy(Intent intent) throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public int getCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews getViewAt(int position) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews getLoadingView() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public int getViewTypeCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public long getItemId(int position) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public boolean hasStableIds() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public boolean isCreated() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int capSize) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRemoteViewsFactory {
        public static final String DESCRIPTOR = "com.android.internal.widget.IRemoteViewsFactory";
        static final int TRANSACTION_getCount = 4;
        static final int TRANSACTION_getItemId = 8;
        static final int TRANSACTION_getLoadingView = 6;
        static final int TRANSACTION_getRemoteCollectionItems = 11;
        static final int TRANSACTION_getViewAt = 5;
        static final int TRANSACTION_getViewTypeCount = 7;
        static final int TRANSACTION_hasStableIds = 9;
        static final int TRANSACTION_isCreated = 10;
        static final int TRANSACTION_onDataSetChanged = 1;
        static final int TRANSACTION_onDataSetChangedAsync = 2;
        static final int TRANSACTION_onDestroy = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteViewsFactory asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteViewsFactory)) {
                return (IRemoteViewsFactory) iin;
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
                    return "onDataSetChanged";
                case 2:
                    return "onDataSetChangedAsync";
                case 3:
                    return "onDestroy";
                case 4:
                    return "getCount";
                case 5:
                    return "getViewAt";
                case 6:
                    return "getLoadingView";
                case 7:
                    return "getViewTypeCount";
                case 8:
                    return "getItemId";
                case 9:
                    return "hasStableIds";
                case 10:
                    return "isCreated";
                case 11:
                    return "getRemoteCollectionItems";
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
                    onDataSetChanged();
                    reply.writeNoException();
                    return true;
                case 2:
                    onDataSetChangedAsync();
                    return true;
                case 3:
                    Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    onDestroy(_arg0);
                    return true;
                case 4:
                    int _result = getCount();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 5:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    RemoteViews _result2 = getViewAt(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 6:
                    RemoteViews _result3 = getLoadingView();
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 7:
                    int _result4 = getViewTypeCount();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 8:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result5 = getItemId(_arg03);
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 9:
                    boolean _result6 = hasStableIds();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 10:
                    boolean _result7 = isCreated();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 11:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    RemoteViews.RemoteCollectionItems _result8 = getRemoteCollectionItems(_arg04);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteViewsFactory {
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

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDataSetChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDataSetChangedAsync() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDestroy(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public int getCount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews getViewAt(int position) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(position);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    RemoteViews _result = (RemoteViews) _reply.readTypedObject(RemoteViews.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews getLoadingView() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    RemoteViews _result = (RemoteViews) _reply.readTypedObject(RemoteViews.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public int getViewTypeCount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public long getItemId(int position) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(position);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public boolean hasStableIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public boolean isCreated() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int capSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capSize);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    RemoteViews.RemoteCollectionItems _result = (RemoteViews.RemoteCollectionItems) _reply.readTypedObject(RemoteViews.RemoteCollectionItems.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}

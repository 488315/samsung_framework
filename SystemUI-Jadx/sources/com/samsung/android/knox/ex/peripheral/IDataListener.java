package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IDataListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IDataListener";

    long getHashCode();

    void onFail(int i, String str);

    void onReceive(int i, Bundle bundle);

    void onSuccess();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IDataListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IDataListener
        public final long getHashCode() {
            return 0L;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IDataListener
        public final void onSuccess() {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IDataListener
        public final void onFail(int i, String str) {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IDataListener
        public final void onReceive(int i, Bundle bundle) {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDataListener {
        public static final int TRANSACTION_getHashCode = 1;
        public static final int TRANSACTION_onFail = 3;
        public static final int TRANSACTION_onReceive = 4;
        public static final int TRANSACTION_onSuccess = 2;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IDataListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IDataListener
            public final long getHashCode() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDataListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IDataListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IDataListener
            public final void onFail(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDataListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IDataListener
            public final void onReceive(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDataListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IDataListener
            public final void onSuccess() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDataListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDataListener.DESCRIPTOR);
        }

        public static IDataListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDataListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataListener)) {
                return (IDataListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataListener.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                return super.onTransact(i, parcel, parcel2, i2);
                            }
                            int readInt = parcel.readInt();
                            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                            parcel.enforceNoDataAvail();
                            onReceive(readInt, bundle);
                            parcel2.writeNoException();
                        } else {
                            int readInt2 = parcel.readInt();
                            String readString = parcel.readString();
                            parcel.enforceNoDataAvail();
                            onFail(readInt2, readString);
                            parcel2.writeNoException();
                        }
                    } else {
                        onSuccess();
                        parcel2.writeNoException();
                    }
                } else {
                    long hashCode = getHashCode();
                    parcel2.writeNoException();
                    parcel2.writeLong(hashCode);
                }
                return true;
            }
            parcel2.writeString(IDataListener.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
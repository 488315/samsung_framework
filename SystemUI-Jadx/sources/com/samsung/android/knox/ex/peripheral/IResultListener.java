package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IResultListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IResultListener";

    void onFail(int i, String str);

    void onSuccess(Bundle bundle);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IResultListener {
        public static final int TRANSACTION_onFail = 2;
        public static final int TRANSACTION_onSuccess = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IResultListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IResultListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IResultListener
            public final void onFail(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResultListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IResultListener
            public final void onSuccess(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResultListener.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IResultListener.DESCRIPTOR);
        }

        public static IResultListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResultListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResultListener)) {
                return (IResultListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResultListener.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    if (i != 2) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onFail(readInt, readString);
                    parcel2.writeNoException();
                } else {
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(bundle);
                    parcel2.writeNoException();
                }
                return true;
            }
            parcel2.writeString(IResultListener.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IResultListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IResultListener
        public final void onSuccess(Bundle bundle) {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IResultListener
        public final void onFail(int i, String str) {
        }
    }
}

package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IEventListener";

    void onEvent(int i, Bundle bundle);

    void onFail(String str);

    void onSuccess();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IEventListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IEventListener
        public final void onSuccess() {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IEventListener
        public final void onFail(String str) {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IEventListener
        public final void onEvent(int i, Bundle bundle) {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IEventListener {
        public static final int TRANSACTION_onEvent = 3;
        public static final int TRANSACTION_onFail = 2;
        public static final int TRANSACTION_onSuccess = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IEventListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IEventListener
            public final void onEvent(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IEventListener
            public final void onFail(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IEventListener
            public final void onSuccess() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEventListener.DESCRIPTOR);
        }

        public static IEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEventListener)) {
                return (IEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEventListener.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return super.onTransact(i, parcel, parcel2, i2);
                        }
                        int readInt = parcel.readInt();
                        Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        parcel.enforceNoDataAvail();
                        onEvent(readInt, bundle);
                        parcel2.writeNoException();
                    } else {
                        String readString = parcel.readString();
                        parcel.enforceNoDataAvail();
                        onFail(readString);
                        parcel2.writeNoException();
                    }
                } else {
                    onSuccess();
                    parcel2.writeNoException();
                }
                return true;
            }
            parcel2.writeString(IEventListener.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

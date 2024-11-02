package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IUCMUnlockTaskCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback";

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IUCMUnlockTaskCallback {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
        public final int postAuthentication() {
            return 0;
        }
    }

    int postAuthentication();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IUCMUnlockTaskCallback {
        public static final int TRANSACTION_postAuthentication = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IUCMUnlockTaskCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IUCMUnlockTaskCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
            public final int postAuthentication() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUCMUnlockTaskCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUCMUnlockTaskCallback.DESCRIPTOR);
        }

        public static IUCMUnlockTaskCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUCMUnlockTaskCallback)) {
                return (IUCMUnlockTaskCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int postAuthentication = postAuthentication();
                parcel2.writeNoException();
                parcel2.writeInt(postAuthentication);
                return true;
            }
            parcel2.writeString(IUCMUnlockTaskCallback.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

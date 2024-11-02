package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface ICredentialManagerServiceSystemUICallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback";

    void setUCMKeyguardVendor(String str);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ICredentialManagerServiceSystemUICallback {
        public static final int TRANSACTION_setUCMKeyguardVendor = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements ICredentialManagerServiceSystemUICallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return ICredentialManagerServiceSystemUICallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
            public final void setUCMKeyguardVendor(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
        }

        public static ICredentialManagerServiceSystemUICallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICredentialManagerServiceSystemUICallback)) {
                return (ICredentialManagerServiceSystemUICallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                setUCMKeyguardVendor(readString);
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(ICredentialManagerServiceSystemUICallback.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements ICredentialManagerServiceSystemUICallback {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
        public final void setUCMKeyguardVendor(String str) {
        }
    }
}

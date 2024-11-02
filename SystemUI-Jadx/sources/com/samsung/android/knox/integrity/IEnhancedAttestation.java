package com.samsung.android.knox.integrity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IEnhancedAttestation extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.integrity.IEnhancedAttestation";

    void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IEnhancedAttestation {
        public static final int TRANSACTION_enhancedAttestation = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IEnhancedAttestation {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.integrity.IEnhancedAttestation
            public final void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnhancedAttestation.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iEnhancedAttestationPolicyCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IEnhancedAttestation.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestation.DESCRIPTOR);
        }

        public static IEnhancedAttestation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnhancedAttestation.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEnhancedAttestation)) {
                return (IEnhancedAttestation) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "enhancedAttestation";
        }

        public final int getMaxTransactionId() {
            return 0;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnhancedAttestation.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                IEnhancedAttestationPolicyCallback asInterface = IEnhancedAttestationPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                enhancedAttestation(readString, readString2, asInterface, readBoolean);
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(IEnhancedAttestation.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IEnhancedAttestation {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestation
        public final void enhancedAttestation(String str, String str2, IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback, boolean z) {
        }
    }
}

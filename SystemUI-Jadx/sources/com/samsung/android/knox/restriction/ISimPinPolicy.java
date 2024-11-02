package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface ISimPinPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.ISimPinPolicy";

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements ISimPinPolicy {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.ISimPinPolicy
        public final int changeSimPinCode(int i, String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.ISimPinPolicy
        public final boolean isSimLocked(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.ISimPinPolicy
        public final int setSubIdLockEnabled(int i, boolean z, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.ISimPinPolicy
        public final boolean supplyPinReportResultForSubscriber(String str, int i) {
            return false;
        }
    }

    int changeSimPinCode(int i, String str, String str2);

    boolean isSimLocked(int i);

    int setSubIdLockEnabled(int i, boolean z, String str);

    boolean supplyPinReportResultForSubscriber(String str, int i);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISimPinPolicy {
        public static final int TRANSACTION_changeSimPinCode = 2;
        public static final int TRANSACTION_isSimLocked = 3;
        public static final int TRANSACTION_setSubIdLockEnabled = 1;
        public static final int TRANSACTION_supplyPinReportResultForSubscriber = 4;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements ISimPinPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.restriction.ISimPinPolicy
            public final int changeSimPinCode(int i, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISimPinPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return ISimPinPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.ISimPinPolicy
            public final boolean isSimLocked(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISimPinPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.ISimPinPolicy
            public final int setSubIdLockEnabled(int i, boolean z, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISimPinPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.ISimPinPolicy
            public final boolean supplyPinReportResultForSubscriber(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISimPinPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISimPinPolicy.DESCRIPTOR);
        }

        public static ISimPinPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISimPinPolicy.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISimPinPolicy)) {
                return (ISimPinPolicy) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISimPinPolicy.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                return super.onTransact(i, parcel, parcel2, i2);
                            }
                            String readString = parcel.readString();
                            int readInt = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            boolean supplyPinReportResultForSubscriber = supplyPinReportResultForSubscriber(readString, readInt);
                            parcel2.writeNoException();
                            parcel2.writeBoolean(supplyPinReportResultForSubscriber);
                        } else {
                            int readInt2 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            boolean isSimLocked = isSimLocked(readInt2);
                            parcel2.writeNoException();
                            parcel2.writeBoolean(isSimLocked);
                        }
                    } else {
                        int readInt3 = parcel.readInt();
                        String readString2 = parcel.readString();
                        String readString3 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int changeSimPinCode = changeSimPinCode(readInt3, readString2, readString3);
                        parcel2.writeNoException();
                        parcel2.writeInt(changeSimPinCode);
                    }
                } else {
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int subIdLockEnabled = setSubIdLockEnabled(readInt4, readBoolean, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(subIdLockEnabled);
                }
                return true;
            }
            parcel2.writeString(ISimPinPolicy.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

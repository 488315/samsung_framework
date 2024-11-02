package com.samsung.android.knox.downloadablekeystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface IDownloadableKeystoreService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService";

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class Default implements IDownloadableKeystoreService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService
        public final int startTimaKeystoreServices(int i) {
            return 0;
        }
    }

    int startTimaKeystoreServices(int i);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDownloadableKeystoreService {
        public static final int TRANSACTION_startTimaKeystoreServices = 1;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes3.dex */
        public static class Proxy implements IDownloadableKeystoreService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IDownloadableKeystoreService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.downloadablekeystore.IDownloadableKeystoreService
            public final int startTimaKeystoreServices(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDownloadableKeystoreService.DESCRIPTOR);
                    obtain.writeInt(i);
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
            attachInterface(this, IDownloadableKeystoreService.DESCRIPTOR);
        }

        public static IDownloadableKeystoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDownloadableKeystoreService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDownloadableKeystoreService)) {
                return (IDownloadableKeystoreService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "startTimaKeystoreServices";
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
                parcel.enforceInterface(IDownloadableKeystoreService.DESCRIPTOR);
            }
            if (i != 1598968902) {
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int startTimaKeystoreServices = startTimaKeystoreServices(readInt);
                parcel2.writeNoException();
                parcel2.writeInt(startTimaKeystoreServices);
                return true;
            }
            parcel2.writeString(IDownloadableKeystoreService.DESCRIPTOR);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

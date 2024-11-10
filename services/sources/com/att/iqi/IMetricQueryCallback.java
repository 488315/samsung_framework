package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.att.iqi.lib.Metric;

/* loaded from: classes3.dex */
public interface IMetricQueryCallback extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IMetricQueryCallback";

    /* loaded from: classes3.dex */
    public class Default implements IMetricQueryCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.att.iqi.IMetricQueryCallback
        public void onMetricQueried(Metric.ID id, byte[] bArr) {
        }
    }

    void onMetricQueried(Metric.ID id, byte[] bArr);

    /* loaded from: classes3.dex */
    public abstract class Stub extends Binder implements IMetricQueryCallback {
        static final int TRANSACTION_onMetricQueried = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMetricQueryCallback.DESCRIPTOR);
        }

        public static IMetricQueryCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMetricQueryCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMetricQueryCallback)) {
                return (IMetricQueryCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMetricQueryCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMetricQueryCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onMetricQueried((Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR), parcel.createByteArray());
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        /* loaded from: classes3.dex */
        class Proxy implements IMetricQueryCallback {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return IMetricQueryCallback.DESCRIPTOR;
            }

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.att.iqi.IMetricQueryCallback
            public void onMetricQueried(Metric.ID id, byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMetricQueryCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable != null) {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}

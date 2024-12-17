package android.hardware.security.secretkeeper;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ISecretkeeper$Stub$Proxy implements ISecretkeeper {
    public IBinder mRemote;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void deleteAll() {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(ISecretkeeper.DESCRIPTOR);
            if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                throw new RemoteException("Method deleteAll is unimplemented.");
            }
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}

package com.android.wm.shell.recents;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class IRecentsTransitionStateListener$Stub$Proxy implements IRecentsTransitionStateListener {
    public final IBinder mRemote;

    public IRecentsTransitionStateListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onAnimationStateChanged(boolean z) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsTransitionStateListener");
            obtain.writeBoolean(z);
            this.mRemote.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
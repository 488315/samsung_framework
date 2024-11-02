package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.recents.RecentTasksController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface IRecentTasksListener extends IInterface {
    void onRecentTasksChanged();

    void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract class Stub extends Binder implements IRecentTasksListener {

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public final class Proxy implements IRecentTasksListener {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRecentTasksChanged() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.recents.IRecentTasksListener");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.recents.IRecentTasksListener");
            }
            if (i != 1598968902) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return super.onTransact(i, parcel, parcel2, i2);
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                        parcel.enforceNoDataAvail();
                        ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRunningTaskVanished(runningTaskInfo);
                    } else {
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                        parcel.enforceNoDataAvail();
                        ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRunningTaskAppeared(runningTaskInfo2);
                    }
                } else {
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRecentTasksChanged();
                }
                return true;
            }
            parcel2.writeString("com.android.wm.shell.recents.IRecentTasksListener");
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

package com.android.wm.shell.onehanded;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.common.ExecutorUtils;
import com.android.wm.shell.onehanded.OneHandedController;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class IOneHanded$Stub extends Binder implements IInterface {
    public IOneHanded$Stub() {
        attachInterface(this, "com.android.wm.shell.onehanded.IOneHanded");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        final int i3 = 1;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.onehanded.IOneHanded");
        }
        if (i != 1598968902) {
            final int i4 = 0;
            if (i != 2) {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ExecutorUtils.executeRemoteCallWithTaskPermission(((OneHandedController.IOneHandedImpl) this).mController, "stopOneHanded", new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedController$IOneHandedImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i3) {
                            case 0:
                                ((OneHandedController) obj).startOneHanded();
                                return;
                            default:
                                ((OneHandedController) obj).stopOneHanded();
                                return;
                        }
                    }
                }, false);
            } else {
                ExecutorUtils.executeRemoteCallWithTaskPermission(((OneHandedController.IOneHandedImpl) this).mController, "startOneHanded", new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedController$IOneHandedImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i4) {
                            case 0:
                                ((OneHandedController) obj).startOneHanded();
                                return;
                            default:
                                ((OneHandedController) obj).stopOneHanded();
                                return;
                        }
                    }
                }, false);
            }
            return true;
        }
        parcel2.writeString("com.android.wm.shell.onehanded.IOneHanded");
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}

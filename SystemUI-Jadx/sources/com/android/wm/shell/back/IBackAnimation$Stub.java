package com.android.wm.shell.back;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.IRemoteAnimationRunner;
import android.window.IOnBackInvokedCallback;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda5;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda7;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.common.ExecutorUtils;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class IBackAnimation$Stub extends Binder implements IInterface {
    public IBackAnimation$Stub() {
        attachInterface(this, "com.android.wm.shell.back.IBackAnimation");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.back.IBackAnimation");
        }
        if (i != 1598968902) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    final AppearanceRegion appearanceRegion = (AppearanceRegion) parcel.readTypedObject(AppearanceRegion.CREATOR);
                    parcel.enforceNoDataAvail();
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((BackAnimationController.IBackAnimationImpl) this).mController, "useLauncherSysBarFlags", new Consumer() { // from class: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            AppearanceRegion appearanceRegion2 = appearanceRegion;
                            EdgeBackGestureHandler$$ExternalSyntheticLambda5 edgeBackGestureHandler$$ExternalSyntheticLambda5 = ((BackAnimationController) obj).mCustomizer;
                            if (edgeBackGestureHandler$$ExternalSyntheticLambda5 != null) {
                                EdgeBackGestureHandler edgeBackGestureHandler = edgeBackGestureHandler$$ExternalSyntheticLambda5.f$0;
                                edgeBackGestureHandler.getClass();
                                edgeBackGestureHandler.mMainExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda7(edgeBackGestureHandler, appearanceRegion2));
                            }
                        }
                    }, false);
                    parcel2.writeNoException();
                } else {
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((BackAnimationController.IBackAnimationImpl) this).mController, "clearBackToLauncherCallback", new BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda2(), false);
                    parcel2.writeNoException();
                }
            } else {
                final IOnBackInvokedCallback asInterface = IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
                final IRemoteAnimationRunner asInterface2 = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ExecutorUtils.executeRemoteCallWithTaskPermission(((BackAnimationController.IBackAnimationImpl) this).mController, "setBackToLauncherCallback", new Consumer() { // from class: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((BackAnimationController) obj).mAnimationDefinition.set(1, new BackAnimationRunner(asInterface, asInterface2));
                    }
                }, false);
                parcel2.writeNoException();
            }
            return true;
        }
        parcel2.writeString("com.android.wm.shell.back.IBackAnimation");
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}

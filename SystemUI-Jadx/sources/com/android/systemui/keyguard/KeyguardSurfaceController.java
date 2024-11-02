package com.android.systemui.keyguard;

import android.os.Looper;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewRootImpl;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public interface KeyguardSurfaceController {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class DefaultImpls {
        public static void setKeyguardSurfaceAppearAmount$default(KeyguardSurfaceController keyguardSurfaceController) {
            final KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = (KeyguardSurfaceControllerImpl) keyguardSurfaceController;
            final SurfaceControl surfaceControl = ((ViewRootImpl) keyguardSurfaceControllerImpl.viewRootImpl$delegate.getValue()).getSurfaceControl();
            final float f = 0.01f;
            if (KeyguardSurfaceControllerImpl.isValid(surfaceControl, 0.01f)) {
                Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount amount=0.01 hasTransaction=false");
                final SurfaceControl.Transaction transaction = null;
                Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        boolean z;
                        KeyguardSurfaceControllerImpl.this.getClass();
                        KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl2 = KeyguardSurfaceControllerImpl.this;
                        SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withAlpha(f).build();
                        SurfaceControl.Transaction transaction2 = transaction;
                        KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl3 = KeyguardSurfaceControllerImpl.this;
                        if (transaction2 == null) {
                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl3.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                        }
                        keyguardSurfaceControllerImpl2.lastKeyguardSurfaceParams = build;
                        if (f == 1.0f) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!z) {
                            KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl4 = KeyguardSurfaceControllerImpl.this;
                            KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardSurfaceControllerImpl4.keyguardVisibilityMonitor;
                            Function1 function1 = (Function1) keyguardSurfaceControllerImpl4.isExpandedChangedListener;
                            ArrayList arrayList = (ArrayList) keyguardVisibilityMonitor.isExpandedChangedListeners;
                            if (!arrayList.contains(function1)) {
                                arrayList.add(function1);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                if (Looper.getMainLooper().isCurrentThread()) {
                    function0.invoke();
                } else {
                    keyguardSurfaceControllerImpl.mainExecutor.execute(new KeyguadSurfaceControllerImplKt$sam$java_lang_Runnable$0(function0));
                }
            }
        }
    }
}

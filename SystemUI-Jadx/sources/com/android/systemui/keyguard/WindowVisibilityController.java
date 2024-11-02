package com.android.systemui.keyguard;

import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class WindowVisibilityController implements VisibilityController {
    public final Choreographer choreographer;
    public final Lazy shadeWindowControllerHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.WindowVisibilityController$shadeWindowControllerHelper$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) WindowVisibilityController.this.shadeWindowControllerLazy.get())).mHelper;
        }
    });
    public final dagger.Lazy shadeWindowControllerLazy;

    public WindowVisibilityController(Choreographer choreographer, dagger.Lazy lazy) {
        this.choreographer = choreographer;
        this.shadeWindowControllerLazy = lazy;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void invalidate() {
        ViewRootImpl viewRootImpl;
        ViewGroup viewGroup = ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.shadeWindowControllerLazy.get())).mNotificationShadeView;
        if (viewGroup != null && (viewRootImpl = viewGroup.getViewRootImpl()) != null) {
            viewRootImpl.setReportNextDraw(false, "BioUnlock");
        }
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean needToBeInvisibleWindow() {
        return true;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void registerFrameUpdateCallback(final Function0 function0) {
        this.choreographer.postCallbackDelayed(3, new Runnable() { // from class: com.android.systemui.keyguard.WindowVisibilityController$registerFrameUpdateCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                Function0.this.invoke();
            }
        }, null, 0L);
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void resetForceInvisible(boolean z) {
        ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) this.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(z);
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean setForceInvisible(SurfaceControl.Transaction transaction, boolean z) {
        Lazy lazy = this.shadeWindowControllerHelper$delegate;
        if (((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) lazy.getValue())).getCurrentState().forceInvisible) {
            return false;
        }
        ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) lazy.getValue())).setForceInvisible(z);
        return true;
    }
}

package com.android.wm.shell.freeform;

import android.util.Log;
import android.view.View;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerDismissButtonView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FreeformContainerDismissButtonView f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(FreeformContainerDismissButtonView freeformContainerDismissButtonView, FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1) {
        this.f$0 = freeformContainerDismissButtonView;
        this.f$1 = freeformContainerViewController$$ExternalSyntheticLambda1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.f$0;
        Runnable runnable = this.f$1;
        freeformContainerDismissButtonView.getClass();
        Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] clear()");
        View view = freeformContainerDismissButtonView.mDismissingIconView;
        if (view != null) {
            view.clearAnimation();
            freeformContainerDismissButtonView.mDismissingIconView = null;
        }
        freeformContainerDismissButtonView.setVisibility(8);
        runnable.run();
    }
}

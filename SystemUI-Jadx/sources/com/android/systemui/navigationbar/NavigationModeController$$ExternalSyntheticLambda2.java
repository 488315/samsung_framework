package com.android.systemui.navigationbar;

import android.content.om.IOverlayManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class NavigationModeController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ NavigationModeController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ NavigationModeController$$ExternalSyntheticLambda2(NavigationModeController navigationModeController, String str, int i) {
        this.f$0 = navigationModeController;
        this.f$1 = str;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NavigationModeController navigationModeController = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        navigationModeController.getClass();
        try {
            boolean z = BasicRune.NAVBAR_ENABLED_HARD_KEY;
            IOverlayManager iOverlayManager = navigationModeController.mOverlayManager;
            if (z && QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY.equals(str)) {
                iOverlayManager.setEnabled(NavigationModeUtil.getGestureOverlayPackageName(navigationModeController.mContext), false, i);
            } else {
                iOverlayManager.setEnabledExclusiveInCategory(str, i);
            }
            Log.d("NavigationModeController", "setModeOverlay: overlayPackage=" + str + " userId=" + i);
        } catch (RemoteException unused) {
            Log.e("NavigationModeController", "Failed to enable overlay " + str + " for user " + i);
        }
    }
}

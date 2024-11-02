package com.android.systemui.statusbar.connectivity;

import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class CallbackHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CallbackHandler f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CallbackHandler$$ExternalSyntheticLambda0(CallbackHandler callbackHandler, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = callbackHandler;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CallbackHandler callbackHandler = this.f$0;
                WifiIndicators wifiIndicators = (WifiIndicators) this.f$1;
                Iterator it = callbackHandler.mSignalCallbacks.iterator();
                while (it.hasNext()) {
                    ((SignalCallback) it.next()).setWifiIndicators(wifiIndicators);
                }
                return;
            default:
                CallbackHandler callbackHandler2 = this.f$0;
                MobileDataIndicators mobileDataIndicators = (MobileDataIndicators) this.f$1;
                Iterator it2 = callbackHandler2.mSignalCallbacks.iterator();
                while (it2.hasNext()) {
                    ((SignalCallback) it2.next()).setMobileDataIndicators(mobileDataIndicators);
                }
                return;
        }
    }
}

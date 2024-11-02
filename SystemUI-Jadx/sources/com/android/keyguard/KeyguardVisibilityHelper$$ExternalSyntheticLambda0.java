package com.android.keyguard;

import com.android.systemui.LsRune;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardVisibilityHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardVisibilityHelper f$0;

    public /* synthetic */ KeyguardVisibilityHelper$$ExternalSyntheticLambda0(KeyguardVisibilityHelper keyguardVisibilityHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardVisibilityHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DcmMascotViewContainer dcmMascotViewContainer;
        DcmMascotViewContainer dcmMascotViewContainer2;
        switch (this.$r8$classId) {
            case 0:
                KeyguardVisibilityHelper keyguardVisibilityHelper = this.f$0;
                keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
                keyguardVisibilityHelper.mView.setVisibility(4);
                if (LsRune.KEYGUARD_DCM_LIVE_UX && (dcmMascotViewContainer = keyguardVisibilityHelper.mMascotViewContainer) != null) {
                    dcmMascotViewContainer.setMascotViewVisible(8);
                }
                keyguardVisibilityHelper.log("Callback Set Visibility to INVISIBLE");
                return;
            case 1:
                KeyguardVisibilityHelper keyguardVisibilityHelper2 = this.f$0;
                keyguardVisibilityHelper2.mKeyguardViewVisibilityAnimating = false;
                keyguardVisibilityHelper2.mView.setVisibility(8);
                if (LsRune.KEYGUARD_DCM_LIVE_UX && (dcmMascotViewContainer2 = keyguardVisibilityHelper2.mMascotViewContainer) != null) {
                    dcmMascotViewContainer2.setMascotViewVisible(8);
                }
                keyguardVisibilityHelper2.log("CallbackSet Visibility to GONE");
                return;
            case 2:
                KeyguardVisibilityHelper keyguardVisibilityHelper3 = this.f$0;
                keyguardVisibilityHelper3.mKeyguardViewVisibilityAnimating = false;
                keyguardVisibilityHelper3.mView.setVisibility(0);
                keyguardVisibilityHelper3.log("Callback Set Visibility to VISIBLE");
                return;
            default:
                this.f$0.mKeyguardViewVisibilityAnimating = false;
                return;
        }
    }
}

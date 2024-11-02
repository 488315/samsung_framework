package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardRMMViewController$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardRMMViewController f$0;

    public /* synthetic */ KeyguardRMMViewController$$ExternalSyntheticLambda0(KeyguardRMMViewController keyguardRMMViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardRMMViewController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardRMMViewController keyguardRMMViewController = this.f$0;
                ((KeyguardRMMView) keyguardRMMViewController.mView).doHapticKeyClick();
                if (keyguardRMMViewController.mPasswordEntry.isEnabled()) {
                    keyguardRMMViewController.verifyPasswordAndUnlock();
                    return;
                }
                return;
            default:
                KeyguardRMMViewController keyguardRMMViewController2 = this.f$0;
                keyguardRMMViewController2.getClass();
                Intent intent = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", keyguardRMMViewController2.mPhoneNumber, null));
                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                try {
                    Log.d("KeyguardRMMView", "click call button");
                    keyguardRMMViewController2.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
                    return;
                } catch (ActivityNotFoundException e) {
                    Log.w("KeyguardRMMView", "Can't find the component " + e);
                    return;
                }
        }
    }
}

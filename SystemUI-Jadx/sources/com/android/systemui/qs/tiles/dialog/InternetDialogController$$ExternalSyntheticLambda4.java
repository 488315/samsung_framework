package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                if (((Set) this.f$0).contains(c1DisplayInfo.uniqueName)) {
                    c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                }
                return c1DisplayInfo;
            default:
                InternetDialogController internetDialogController = (InternetDialogController) this.f$0;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                internetDialogController.getClass();
                return new InternetDialogController.C1DisplayInfo(internetDialogController, subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim());
        }
    }
}

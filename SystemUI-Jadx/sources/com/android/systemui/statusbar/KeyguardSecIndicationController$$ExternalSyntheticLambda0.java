package com.android.systemui.statusbar;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationController$$ExternalSyntheticLambda0 implements Consumer, Action {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecIndicationController f$0;

    public /* synthetic */ KeyguardSecIndicationController$$ExternalSyntheticLambda0(KeyguardSecIndicationController keyguardSecIndicationController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecIndicationController;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.f$0.removeIndication((IndicationEventType) obj);
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        int i = this.$r8$classId;
        KeyguardSecIndicationController keyguardSecIndicationController = this.f$0;
        switch (i) {
            case 1:
                keyguardSecIndicationController.getClass();
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
                return;
            default:
                keyguardSecIndicationController.setVisible(true);
                return;
        }
    }
}

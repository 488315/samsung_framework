package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(OneHandedController.OneHandedImpl oneHandedImpl, int i) {
        this.f$0 = oneHandedImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.stopOneHanded(this.f$1);
    }
}

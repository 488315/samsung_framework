package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2 = false;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(OneHandedController.OneHandedImpl oneHandedImpl, boolean z) {
        this.f$0 = oneHandedImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.setLockedDisabled(this.f$1, this.f$2);
    }
}

package com.android.server.am;

import com.android.server.am.MARsPolicyManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserController$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ UserController$$ExternalSyntheticLambda16(int i) {
        this.$r8$classId = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                throw new RuntimeException("Keyguard is not shown in 20000 ms.");
            default:
                boolean z = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                return;
        }
    }
}

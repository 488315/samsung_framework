package com.android.server.policy.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardServiceDelegate$2$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            ActivityTaskManager.getService().setLockScreenShown(true, false);
        } catch (RemoteException unused) {
        }
    }
}

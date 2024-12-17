package com.android.server.pm.verify.domain;

import android.app.ActivityManager;
import android.os.Binder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationShell {
    public final DomainVerificationService mCallback;

    public DomainVerificationShell(DomainVerificationService domainVerificationService) {
        this.mCallback = domainVerificationService;
    }

    public static int translateUserId(int i, String str) {
        return ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, str, "pm command");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0331, code lost:
    
        if (r10.equals("STATE_NO_RESPONSE") == false) goto L183;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0398 A[LOOP:9: B:197:0x0392->B:199:0x0398, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x039c A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r12v36, types: [com.android.server.pm.verify.domain.DomainVerificationService] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v78 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean runCommand(com.android.modules.utils.BasicShellCommandHandler r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 1704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.verify.domain.DomainVerificationShell.runCommand(com.android.modules.utils.BasicShellCommandHandler, java.lang.String):java.lang.Boolean");
    }
}

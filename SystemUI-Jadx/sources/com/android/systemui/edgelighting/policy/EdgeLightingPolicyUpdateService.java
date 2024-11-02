package com.android.systemui.edgelighting.policy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class EdgeLightingPolicyUpdateService extends IntentService {
    public EdgeLightingPolicyUpdateService() {
        super("EdgeLightingPolicyUpdateService");
    }

    public static void startActionUpdate(Context context) {
        Slog.d("ELPolicyUpdateService", "startActionUpdate");
        Intent intent = new Intent(context, (Class<?>) EdgeLightingPolicyUpdateService.class);
        intent.setAction("com.android.systemui.edgelighting.action.UPDATE_POLICY");
        context.startService(intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0134 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0130 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHandleIntent(android.content.Intent r13) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService.onHandleIntent(android.content.Intent):void");
    }
}

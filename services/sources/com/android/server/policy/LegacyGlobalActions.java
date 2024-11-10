package com.android.server.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

/* loaded from: classes3.dex */
public abstract class LegacyGlobalActions implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
    /* renamed from: -$$Nest$fgetmHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ Handler m9913$$Nest$fgetmHandler(LegacyGlobalActions legacyGlobalActions) {
        throw null;
    }

    /* renamed from: -$$Nest$fgetmIsWaitingForEcmExit, reason: not valid java name */
    public static /* bridge */ /* synthetic */ boolean m9914$$Nest$fgetmIsWaitingForEcmExit(LegacyGlobalActions legacyGlobalActions) {
        throw null;
    }

    /* renamed from: -$$Nest$fputmIsWaitingForEcmExit, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m9915$$Nest$fputmIsWaitingForEcmExit(LegacyGlobalActions legacyGlobalActions, boolean z) {
        throw null;
    }

    /* renamed from: -$$Nest$mchangeAirplaneModeSystemSetting, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m9916$$Nest$mchangeAirplaneModeSystemSetting(LegacyGlobalActions legacyGlobalActions, boolean z) {
        throw null;
    }

    /* renamed from: com.android.server.policy.LegacyGlobalActions$9, reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass9 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                if ("globalactions".equals(intent.getStringExtra("reason"))) {
                    return;
                }
                LegacyGlobalActions.m9913$$Nest$fgetmHandler(null).sendEmptyMessage(0);
            } else if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false) && LegacyGlobalActions.m9914$$Nest$fgetmIsWaitingForEcmExit(null)) {
                LegacyGlobalActions.m9915$$Nest$fputmIsWaitingForEcmExit(null, false);
                LegacyGlobalActions.m9916$$Nest$mchangeAirplaneModeSystemSetting(null, true);
            }
        }
    }

    /* renamed from: com.android.server.policy.LegacyGlobalActions$11, reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass11 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.RINGER_MODE_CHANGED")) {
                LegacyGlobalActions.m9913$$Nest$fgetmHandler(null).sendEmptyMessage(1);
            }
        }
    }
}

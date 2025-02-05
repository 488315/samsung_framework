package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

/* loaded from: classes.dex */
public class FirebaseInstanceIdService extends zzb {
    @Override // com.google.firebase.iid.zzb
    public final void handleIntent(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            onTokenRefresh();
            return;
        }
        String stringExtra = intent.getStringExtra("CMD");
        if (stringExtra != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder sb = new StringBuilder(stringExtra.length() + 21 + valueOf.length());
                sb.append("Received command: ");
                sb.append(stringExtra);
                sb.append(" - ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            if ("RST".equals(stringExtra) || "RST_FULL".equals(stringExtra)) {
                FirebaseInstanceId.getInstance().zzclg();
            } else if ("SYNC".equals(stringExtra)) {
                FirebaseInstanceId.getInstance().zzclh();
            }
        }
    }

    public void onTokenRefresh() {
    }

    @Override // com.google.firebase.iid.zzb
    protected final Intent zzp(Intent intent) {
        return zzz.zzclq().zzolm.poll();
    }
}

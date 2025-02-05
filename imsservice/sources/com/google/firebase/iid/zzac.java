package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
final class zzac implements Runnable {
    private final zzw zzokq;
    private final long zzolp;
    private final PowerManager.WakeLock zzolq;
    private final FirebaseInstanceId zzolr;

    zzac(FirebaseInstanceId firebaseInstanceId, zzw zzwVar, long j) {
        this.zzolr = firebaseInstanceId;
        this.zzokq = zzwVar;
        this.zzolp = j;
        PowerManager.WakeLock newWakeLock = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
        this.zzolq = newWakeLock;
        newWakeLock.setReferenceCounted(false);
    }

    private final boolean zzclt() {
        zzab zzclc = this.zzolr.zzclc();
        if (zzclc != null && !zzclc.zzru(this.zzokq.zzclm())) {
            return true;
        }
        try {
            String zzcld = this.zzolr.zzcld();
            if (zzcld == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzclc == null || !zzcld.equals(zzclc.zzlnm)) {
                Context context = getContext();
                Intent intent = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
                Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
                intent2.setClass(context, FirebaseInstanceIdReceiver.class);
                intent2.putExtra("wrapped_intent", intent);
                context.sendBroadcast(intent2);
            }
            return true;
        } catch (IOException | SecurityException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", valueOf.length() != 0 ? "Token retrieval failed: ".concat(valueOf) : new String("Token retrieval failed: "));
            return false;
        }
    }

    private final boolean zzclu() {
        while (true) {
            synchronized (this.zzolr) {
                String zzcls = FirebaseInstanceId.zzcle().zzcls();
                if (zzcls == null) {
                    Log.d("FirebaseInstanceId", "topic sync succeeded");
                    return true;
                }
                if (!zzrv(zzcls)) {
                    return false;
                }
                FirebaseInstanceId.zzcle().zzro(zzcls);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004b A[Catch: IOException -> 0x005a, TryCatch #0 {IOException -> 0x005a, blocks: (B:5:0x0012, B:14:0x0039, B:17:0x0047, B:19:0x004b, B:22:0x001f, B:25:0x0029), top: B:4:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzrv(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r1 = "!"
            java.lang.String[] r7 = r7.split(r1)
            int r1 = r7.length
            r2 = 2
            r3 = 1
            if (r1 != r2) goto L79
            r1 = 0
            r2 = r7[r1]
            r7 = r7[r3]
            int r4 = r2.hashCode()     // Catch: java.io.IOException -> L5a
            r5 = 83
            if (r4 == r5) goto L29
            r5 = 85
            if (r4 == r5) goto L1f
            goto L33
        L1f:
            java.lang.String r4 = "U"
            boolean r2 = r2.equals(r4)     // Catch: java.io.IOException -> L5a
            if (r2 == 0) goto L33
            r2 = r3
            goto L34
        L29:
            java.lang.String r4 = "S"
            boolean r2 = r2.equals(r4)     // Catch: java.io.IOException -> L5a
            if (r2 == 0) goto L33
            r2 = r1
            goto L34
        L33:
            r2 = -1
        L34:
            if (r2 == 0) goto L4b
            if (r2 == r3) goto L39
            goto L79
        L39:
            com.google.firebase.iid.FirebaseInstanceId r6 = r6.zzolr     // Catch: java.io.IOException -> L5a
            r6.zzrn(r7)     // Catch: java.io.IOException -> L5a
            boolean r6 = com.google.firebase.iid.FirebaseInstanceId.zzclf()     // Catch: java.io.IOException -> L5a
            if (r6 == 0) goto L79
            java.lang.String r6 = "unsubscribe operation succeeded"
        L47:
            android.util.Log.d(r0, r6)     // Catch: java.io.IOException -> L5a
            goto L79
        L4b:
            com.google.firebase.iid.FirebaseInstanceId r6 = r6.zzolr     // Catch: java.io.IOException -> L5a
            r6.zzrm(r7)     // Catch: java.io.IOException -> L5a
            boolean r6 = com.google.firebase.iid.FirebaseInstanceId.zzclf()     // Catch: java.io.IOException -> L5a
            if (r6 == 0) goto L79
            java.lang.String r6 = "subscribe operation succeeded"
            goto L47
        L5a:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()
            java.lang.String r6 = java.lang.String.valueOf(r6)
            int r7 = r6.length()
            java.lang.String r2 = "Topic sync failed: "
            if (r7 == 0) goto L70
            java.lang.String r6 = r2.concat(r6)
            goto L75
        L70:
            java.lang.String r6 = new java.lang.String
            r6.<init>(r2)
        L75:
            android.util.Log.e(r0, r6)
            return r1
        L79:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzac.zzrv(java.lang.String):boolean");
    }

    final Context getContext() {
        return this.zzolr.getApp().getApplicationContext();
    }

    @Override // java.lang.Runnable
    public final void run() {
        FirebaseInstanceId firebaseInstanceId;
        this.zzolq.acquire();
        try {
            boolean z = true;
            this.zzolr.zzcy(true);
            if (this.zzokq.zzcll() == 0) {
                z = false;
            }
            if (z) {
                if (!zzclv()) {
                    new zzad(this).zzclw();
                } else if (zzclt() && zzclu()) {
                    firebaseInstanceId = this.zzolr;
                } else {
                    this.zzolr.zzcd(this.zzolp);
                }
            }
            firebaseInstanceId = this.zzolr;
            firebaseInstanceId.zzcy(false);
        } finally {
            this.zzolq.release();
        }
    }

    final boolean zzclv() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.samsung.android.knox.integrity;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AttestationPolicy {
    public static final String ACTION_KNOX_ATTESTATION_RESULT = "com.samsung.android.knox.intent.action.KNOX_ATTESTATION_RESULT";
    public static final int ERROR_DEVICE_NOT_SUPPORTED = -3;
    public static final int ERROR_INVALID_NONCE = -5;
    public static final int ERROR_MDM_PERMISSION = -1;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_TIMA_INTERNAL = -2;
    public static final int ERROR_UNKNOWN = -4;
    public static final String EXTRA_ATTESTATION_DATA = "com.samsung.android.knox.intent.extra.ATTESTATION_DATA";
    public static final String EXTRA_ERROR_MSG = "com.samsung.android.knox.intent.extra.ERROR_MSG";
    public static final String EXTRA_RESULT = "com.samsung.android.knox.intent.extra.RESULT";
    public static final String KNOX_ATTESTATION_PERMISSION = "com.samsung.android.knox.permission.KNOX_REMOTE_ATTESTATION";
    public static final String KNOX_ATTESTATION_PERMISSION_ERROR = "Need com.samsung.android.knox.permission.KNOX_REMOTE_ATTESTATION permission";
    public static final String TAG = "AttestationPolicy";
    public static final int TAL_KNOX_KEY_ERROR = 90;
    public static final int TIMA_ATTESTATION_SUCCESS = 0;
    public static final int TIMA_ERROR_DEVICE_NOT_SUPPORTED = 4;
    public static final int TIMA_INVALID_NONCE = 91;
    public Context mContext;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class AttestationRunnable implements Runnable {
        public int callingUid;
        public String nonce;

        public AttestationRunnable(String str, int i) {
            this.nonce = str;
            this.callingUid = i;
        }
    }

    public AttestationPolicy(Context context) {
        this.mContext = context;
    }

    public final Intent makeAttestationIntent(String str, int i) {
        Log.e(TAG, "V2 Attestation is not supported from S OS. Please use V3 Attestation.");
        return makeReturnIntent(-3, null);
    }

    public final Intent makeReturnIntent(int i, String str) {
        Intent intent = new Intent();
        if (i != -5) {
            if (i != -3) {
                if (i != -2) {
                    if (i != -1) {
                        intent.putExtra("com.samsung.android.knox.intent.extra.RESULT", -4);
                        if (str != null) {
                            intent.putExtra(EXTRA_ERROR_MSG, str);
                        }
                    } else {
                        intent.putExtra("com.samsung.android.knox.intent.extra.RESULT", -1);
                    }
                } else {
                    intent.putExtra("com.samsung.android.knox.intent.extra.RESULT", -2);
                    if (str != null) {
                        intent.putExtra(EXTRA_ERROR_MSG, str);
                    }
                }
            } else {
                intent.putExtra("com.samsung.android.knox.intent.extra.RESULT", -3);
            }
        } else {
            intent.putExtra("com.samsung.android.knox.intent.extra.RESULT", -5);
        }
        return intent;
    }

    public final void startAttestation(String str) {
        new Thread(new AttestationRunnable(str, Binder.getCallingUid()) { // from class: com.samsung.android.knox.integrity.AttestationPolicy.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Intent makeAttestationIntent = AttestationPolicy.this.makeAttestationIntent(this.nonce, this.callingUid);
                    if (makeAttestationIntent != null) {
                        makeAttestationIntent.setAction(AttestationPolicy.ACTION_KNOX_ATTESTATION_RESULT);
                        String[] packagesForUid = AttestationPolicy.this.mContext.getPackageManager().getPackagesForUid(this.callingUid);
                        int userId = UserHandle.getUserId(this.callingUid);
                        for (String str2 : packagesForUid) {
                            makeAttestationIntent.setPackage(str2);
                            AttestationPolicy.this.mContext.sendBroadcastAsUser(makeAttestationIntent, new UserHandle(userId));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

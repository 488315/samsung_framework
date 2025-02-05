package com.sec.internal.ims.entitlement.util;

import android.util.Log;
import java.util.Date;

/* loaded from: classes.dex */
public class E911AidValidator {
    private static final long E911_AID_CHECK_EXPIRATION_TIME = 172800000;
    private static final String LOG_TAG = DateUtil.class.getSimpleName();

    public static boolean validate(String str) {
        Date parseIso8601Date = DateUtil.parseIso8601Date(str);
        if (parseIso8601Date != null && parseIso8601Date.getTime() - new Date().getTime() >= E911_AID_CHECK_EXPIRATION_TIME) {
            return true;
        }
        Log.i(LOG_TAG, "validate: e911 AID is expired");
        return false;
    }
}

package com.sec.internal.ims.entitlement.nsds.app.flow.xaawfcentitlement;

import android.util.Log;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class XaaWfcErrorCodeTranslator {
    private static final String LOG_TAG = "XaaWfcErrorCodeTranslator";
    private static final Map<Integer, Integer> sMapE911FilteredFailureCodes;
    private static final Map<Integer, Integer> sMapE911FilteredSuccessCodes;

    static {
        HashMap hashMap = new HashMap();
        sMapE911FilteredSuccessCodes = hashMap;
        hashMap.put(7, Integer.valueOf(NSDSNamespaces.NSDSDefinedResponseCode.SVC_PROVISION_COMPLETED_SUCCESS_CODE));
        hashMap.put(12, Integer.valueOf(NSDSNamespaces.NSDSDefinedResponseCode.LOCATIONANDTC_UPDATE_SUCCESS_CODE));
        hashMap.put(2, Integer.valueOf(NSDSNamespaces.NSDSDefinedResponseCode.LOCATIONANDTC_UPDATE_NOT_REQUIRED));
        Integer valueOf = Integer.valueOf(NSDSNamespaces.NSDSDefinedResponseCode.FORCE_TOGGLE_OFF_ERROR_CODE);
        hashMap.put(3, valueOf);
        HashMap hashMap2 = new HashMap();
        sMapE911FilteredFailureCodes = hashMap2;
        hashMap2.put(7, Integer.valueOf(NSDSNamespaces.NSDSDefinedResponseCode.SVC_NOT_PROVISIONED_ERROR_CODE));
        hashMap2.put(12, valueOf);
        hashMap2.put(2, valueOf);
    }

    public static int translateErrorCode(int i, boolean z, int i2) {
        Log.i(LOG_TAG, "translateErrorCode: deviceEventType " + i + " success " + z + "nsdsErrorCode " + i2);
        if (i2 == 1000) {
            return translateSuccessCode(i);
        }
        if (i2 != 1046) {
            return (i2 == 2500 || i2 == 2300 || i2 == 2301) ? i2 : translateErrorCodeByEventType(i, z);
        }
        if (i == 2) {
            return NSDSNamespaces.NSDSDefinedResponseCode.SVC_NOT_PROVISIONED_ERROR_CODE;
        }
        return -1;
    }

    private static int translateErrorCodeByEventType(int i, boolean z) {
        if (z) {
            Log.e(LOG_TAG, "translateErrorCodeByEventType: result cannot be success");
            return -1;
        }
        if (i != 1) {
            if (i == 2) {
                return NSDSNamespaces.NSDSDefinedResponseCode.SVC_NOT_PROVISIONED_ERROR_CODE;
            }
            if (i != 4) {
                return -1;
            }
        }
        return NSDSNamespaces.NSDSDefinedResponseCode.FORCE_TOGGLE_OFF_ERROR_CODE;
    }

    private static int translateSuccessCode(int i) {
        Integer num = sMapE911FilteredSuccessCodes.get(Integer.valueOf(i));
        if (num == null) {
            num = 1000;
        }
        return num.intValue();
    }
}

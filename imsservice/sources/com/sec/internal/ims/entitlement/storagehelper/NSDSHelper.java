package com.sec.internal.ims.entitlement.storagehelper;

import android.content.Context;
import android.telephony.CellInfo;
import android.text.TextUtils;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.helper.os.CellIdentityWrapper;
import com.sec.internal.helper.os.TelephonyManagerWrapper;
import com.sec.internal.ims.core.PdnController$$ExternalSyntheticLambda0;
import com.sec.internal.ims.core.PdnController$$ExternalSyntheticLambda1;
import com.sec.internal.log.IMSLog;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class NSDSHelper {
    private static final String LOG_TAG = "NSDSHelper";

    /* JADX WARN: Removed duplicated region for block: B:8:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getRealm(java.lang.String r6) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            r2 = 3
            r3 = 310(0x136, float:4.34E-43)
            java.lang.String r1 = r6.substring(r1, r2)     // Catch: java.lang.NumberFormatException -> L1c
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.NumberFormatException -> L1c
            java.lang.String r6 = r6.substring(r2)     // Catch: java.lang.NumberFormatException -> L1a
            int r3 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.NumberFormatException -> L1a
            goto L38
        L1a:
            r6 = move-exception
            goto L1e
        L1c:
            r6 = move-exception
            r1 = r3
        L1e:
            java.lang.String r2 = com.sec.internal.ims.entitlement.storagehelper.NSDSHelper.LOG_TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "exception "
            r4.append(r5)
            java.lang.String r6 = r6.getMessage()
            r4.append(r6)
            java.lang.String r6 = r4.toString()
            com.sec.internal.log.IMSLog.s(r2, r6)
        L38:
            java.lang.String r6 = "nai.epc.mnc"
            r0.append(r6)
            r6 = 100
            if (r3 >= r6) goto L46
            java.lang.String r6 = "0"
            r0.append(r6)
        L46:
            r0.append(r3)
            java.lang.String r6 = ".mcc"
            r0.append(r6)
            r0.append(r1)
            java.lang.String r6 = ".3gppnetwork.org"
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.String r0 = com.sec.internal.ims.entitlement.storagehelper.NSDSHelper.LOG_TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "getRealm: "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.sec.internal.log.IMSLog.s(r0, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.entitlement.storagehelper.NSDSHelper.getRealm(java.lang.String):java.lang.String");
    }

    public static String getImsiEap(Context context, int i, String str, String str2) {
        String str3 = LOG_TAG;
        IMSLog.s(str3, "getImsiEap: imsi " + str + " mccmnc " + str2);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            IMSLog.e(str3, "getImsiEap: mccmnc null");
            return null;
        }
        String str4 = "0" + str + "@" + getRealm(str2);
        String prefForSlot = NSDSSharedPrefHelper.getPrefForSlot(context, i, NSDSNamespaces.NSDSSharedPref.PREF_IMSI_EAP);
        if (prefForSlot == null || !prefForSlot.equals(str4)) {
            IMSLog.i(str3, "getImsiEap: imsi eap updated for slotId " + i);
            NSDSSharedPrefHelper.savePrefForSlot(context, i, NSDSNamespaces.NSDSSharedPref.PREF_IMSI_EAP, str4);
        }
        IMSLog.s(str3, "getImsiEap: " + str4);
        return str4;
    }

    public static String getVIMSIforSIMDevice(Context context, String str) {
        String isimDomain = TelephonyManagerWrapper.getInstance(context).getIsimDomain();
        IMSLog.s(LOG_TAG, "getVIMSIforSIMDevice: IsimDomain " + isimDomain);
        if (TextUtils.isEmpty(isimDomain)) {
            return null;
        }
        return str + "@" + isimDomain;
    }

    public static String getMSISDNFromSIM(Context context, int i) {
        String msisdn = TelephonyManagerWrapper.getInstance(context).getMsisdn(i);
        if (TextUtils.isEmpty(msisdn)) {
            return null;
        }
        return msisdn;
    }

    public static int getTACfromCellInfo(Context context) {
        return ((Integer) TelephonyManagerWrapper.getInstance(context).getAllCellInfo().stream().filter(new Predicate() { // from class: com.sec.internal.ims.entitlement.storagehelper.NSDSHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((CellInfo) obj).isRegistered();
            }
        }).map(new PdnController$$ExternalSyntheticLambda0()).map(new PdnController$$ExternalSyntheticLambda1()).map(new Function() { // from class: com.sec.internal.ims.entitlement.storagehelper.NSDSHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((CellIdentityWrapper) obj).getAreaCode());
            }
        }).findFirst().orElse(Integer.MAX_VALUE)).intValue();
    }
}

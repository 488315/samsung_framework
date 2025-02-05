package com.sec.internal.ims.util;

import android.util.Base64;
import android.util.Log;
import com.sec.internal.helper.StrUtil;
import com.sec.internal.ims.config.util.AkaResponse;
import com.sec.internal.ims.config.util.TelephonySupport;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.log.IMSLog;
import java.util.Locale;

/* loaded from: classes.dex */
public class AkaAuth {
    public static final String LOG_TAG = "AkaAuth";

    public static class AkaAuthenticationResponse {
        String mAuthKey;
        String mEncrKey;
        String mRes;

        public AkaAuthenticationResponse(String str, String str2, String str3) {
            this.mRes = str;
            this.mEncrKey = str2;
            this.mAuthKey = str3;
        }

        public String getRes() {
            return this.mRes;
        }

        public String getEncrKey() {
            return this.mEncrKey;
        }

        public String getAuthKey() {
            return this.mAuthKey;
        }
    }

    public static AkaAuthenticationResponse getAkaResponse(int i, String str) {
        try {
            String upperCase = StrUtil.bytesToHexString(Base64.decode(str.getBytes(), 2)).toUpperCase(Locale.US);
            ISimManager simManagerFromSimSlot = SimManagerFactory.getSimManagerFromSimSlot(i);
            String isimAuthentication = simManagerFromSimSlot == null ? null : simManagerFromSimSlot.getIsimAuthentication(upperCase);
            if (isimAuthentication == null) {
                Log.d(LOG_TAG, "getAkaResponse(): getIsimResponse is null.");
                return null;
            }
            try {
                AkaResponse buildAkaResponse = TelephonySupport.buildAkaResponse(isimAuthentication);
                if (buildAkaResponse != null) {
                    if (buildAkaResponse.getRes() != null && buildAkaResponse.getCk() != null && buildAkaResponse.getIk() != null) {
                        return new AkaAuthenticationResponse(StrUtil.bytesToHexString(buildAkaResponse.getRes()), StrUtil.bytesToHexString(buildAkaResponse.getCk()), StrUtil.bytesToHexString(buildAkaResponse.getIk()));
                    }
                } else {
                    Log.d(LOG_TAG, "getAkaResponse(): response wrongly encoded.");
                }
            } catch (IllegalArgumentException unused) {
                Log.d(LOG_TAG, "Parsing failed for response");
            }
            return null;
        } catch (IllegalArgumentException e) {
            IMSLog.e(LOG_TAG, "Error decoding challenge: " + e.getMessage());
            return null;
        }
    }
}

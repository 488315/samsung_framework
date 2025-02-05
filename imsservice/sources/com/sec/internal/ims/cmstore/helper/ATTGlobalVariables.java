package com.sec.internal.ims.cmstore.helper;

import android.os.Build;
import android.util.Log;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.entitilement.SoftphoneNamespaces;
import com.sec.internal.helper.httpclient.HttpController;
import com.sec.internal.ims.core.cmc.CmcConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/* loaded from: classes.dex */
public class ATTGlobalVariables {
    public static String ACMS_HOST_NAME = null;
    public static String ACMS_TARGET_URL = null;
    public static String APPLICATION_ID = null;
    public static String APP_ID = null;
    public static final String ATT_DATA_MESSAGE_PORT = "5586";
    public static String BUILD_INFO = null;
    public static final String CONNECT_HTTPS = "https://";
    public static String CPS_HOST_NAME = null;
    public static String DEFAULT_NMS_HOST = null;
    public static String DEFAULT_PRODUCT_NC_HOST = null;
    private static String HTTP_CLIENT_ID = null;
    public static final int INIT_SYNC_TERMINAL_FLAG = -1;
    public static final int INTERVAL_ZCODE_API = 900000;
    public static String MSG_PROXY_HOST_NAME = null;
    public static final String MSG_STORE_SERVICE_NAME = "msgstoreoemtbs";
    public static final HashSet<String> PHASE_AMBS_SERVICE;
    public static final int RESERVED_TIME = 900;
    private static final String TAG = "ATTGlobalVariables";
    public static String URL_PARAM_STYLE = null;
    public static final int USER_CTN_LENTH = 10;
    public static String VERSION_NAME = null;
    public static int ambsPhaseVersion = 3;
    public static boolean isSignedBinarySupport = true;
    private static ArrayList<String> mAddList;

    public static class ProvisionApiHeaderAttribute {
        public static final String mContentType = "application/x-www-form-urlencoded";
        public static final boolean mGzip = false;
        public static final boolean mKeepAlive = false;
    }

    static {
        HashSet<String> hashSet = new HashSet<>();
        PHASE_AMBS_SERVICE = hashSet;
        hashSet.add(DiagnosisConstants.RCSM_ORST_REGI);
        hashSet.add(DiagnosisConstants.RCSM_ORST_HTTP);
        APPLICATION_ID = "SGH_SMC105A";
        String str = "SGH_" + Build.MODEL.split(CmcConstants.E_NUM_SLOT_SPLIT)[r0.length - 1];
        APPLICATION_ID = str;
        HTTP_CLIENT_ID = str;
        mAddList = new ArrayList<>(Arrays.asList("C105A", "G800A", "G850A", "N915A", "G750A", "N910A", "G920A", "G925A", "G890A", "N920A", "G928A", "Zenzero", "J120A", "J320A", "G930A", "G935A", "G891A", "N930A", "J327A", "J727A", "G950U", "G955U", "G892A", "N950U", "G960U", "G965U", "J337A", "J737A", "N960U", "A600A", "G970U", "G973U", "G975U", "J260A", "F900U", "G977U", "A205U", "A505U", "A705U"));
        int i = 0;
        while (true) {
            if (i >= mAddList.size()) {
                break;
            }
            if (Build.MODEL.contains(mAddList.get(i))) {
                HTTP_CLIENT_ID = APPLICATION_ID + "_p4";
                break;
            }
            i++;
        }
        ACMS_HOST_NAME = SoftphoneNamespaces.SoftphoneSettings.MSIP_PROD_TOKEN_HOST;
        MSG_PROXY_HOST_NAME = "messagessms.att.net";
        URL_PARAM_STYLE = "messagessms.att.net";
        CPS_HOST_NAME = "messagessms.att.net";
        DEFAULT_PRODUCT_NC_HOST = "cns.att.net";
        DEFAULT_NMS_HOST = "vcnms-c2s.enc.att.net";
        ACMS_TARGET_URL = "https://" + MSG_PROXY_HOST_NAME + "/GetHUIMSToken?applicationID=" + APPLICATION_ID;
        BUILD_INFO = "mdl=" + Build.MODEL + ",os=Android " + Build.VERSION.RELEASE + ",fw=" + Build.DISPLAY;
    }

    private static void initVersion() {
        if (isAmbsPhaseIV()) {
            VERSION_NAME = "5.4.201";
        } else {
            VERSION_NAME = "5.3.81";
        }
    }

    public static void initVersionName() {
        Log.d(TAG, "init version name: " + VERSION_NAME);
    }

    private static void initAppID() {
        if (isAmbsPhaseIV()) {
            APP_ID = "ismvAI24FaBhGRJFewoEn2CtsRE=";
        } else {
            APP_ID = "/8ptGdh1Zj1qutLxu+oBJP1fa4Y=";
        }
    }

    public static void initDefault() {
        initAppID();
        ACMS_HOST_NAME = SoftphoneNamespaces.SoftphoneSettings.MSIP_PROD_TOKEN_HOST;
        MSG_PROXY_HOST_NAME = "messagessms.att.net";
        URL_PARAM_STYLE = "messagessms.att.net";
        CPS_HOST_NAME = "messagessms.att.net";
        DEFAULT_PRODUCT_NC_HOST = "cns.att.net";
    }

    public static void setValue(String str, String str2, String str3, String str4) {
        APP_ID = str;
        ACMS_HOST_NAME = str2;
        MSG_PROXY_HOST_NAME = str3;
        URL_PARAM_STYLE = str3;
        CPS_HOST_NAME = str3;
        ACMS_TARGET_URL = "https://" + MSG_PROXY_HOST_NAME + "/GetHUIMSToken?applicationID=" + APPLICATION_ID;
        DEFAULT_PRODUCT_NC_HOST = str4;
    }

    public static void setDebugHttps(boolean z) {
        HttpController.getInstance().setDebugHttps(z);
    }

    public static void setAmbsPhaseVersion(int i) {
        ambsPhaseVersion = i;
        initVersion();
        initAppID();
        Log.i(TAG, "ambsPhaseVersion: " + ambsPhaseVersion);
    }

    public static boolean isGcmReplacePolling() {
        return ambsPhaseVersion >= 4;
    }

    public static boolean isAmbsPhaseIV() {
        return ambsPhaseVersion >= 4;
    }

    public static boolean supportSignedBinary() {
        return isSignedBinarySupport;
    }

    public static String getHttpClientID() {
        if (isAmbsPhaseIV()) {
            return HTTP_CLIENT_ID;
        }
        return APPLICATION_ID;
    }
}

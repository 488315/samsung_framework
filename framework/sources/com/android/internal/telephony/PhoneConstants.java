package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class PhoneConstants {
    public static final int ACTIVE = 1;
    public static final int APN_ALREADY_ACTIVE = 0;
    public static final int APN_ALREADY_INACTIVE = 4;
    public static final int APN_REQUEST_FAILED = 3;
    public static final int APN_REQUEST_STARTED = 1;
    public static final String APN_TYPE_ALL = "*";
    public static final String APN_TYPE_BIP = "bip";
    public static final String APN_TYPE_CAS = "cas";
    public static final String APN_TYPE_CBS = "cbs";
    public static final String APN_TYPE_CMDM = "cmdm";
    public static final String APN_TYPE_CMMAIL = "cmmail";
    public static final String APN_TYPE_DEFAULT = "default";
    public static final String APN_TYPE_DM = "dm";
    public static final String APN_TYPE_DUN = "dun";
    public static final String APN_TYPE_EMERGENCY = "emergency";
    public static final String APN_TYPE_ENT1 = "ent1";
    public static final String APN_TYPE_ENT2 = "ent2";
    public static final String APN_TYPE_FOTA = "fota";
    public static final String APN_TYPE_HIPRI = "hipri";
    public static final String APN_TYPE_IA = "ia";
    public static final String APN_TYPE_IMS = "ims";
    public static final String APN_TYPE_MCX = "mcx";
    public static final String APN_TYPE_MMS = "mms";
    public static final String APN_TYPE_MMS2 = "mms2";
    public static final int APN_TYPE_NOT_AVAILABLE = 2;
    public static final String APN_TYPE_RCS = "rcs";
    public static final String APN_TYPE_SUPL = "supl";
    public static final String APN_TYPE_WAP = "wap";
    public static final String APN_TYPE_XCAP = "xcap";
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_UNKNOWN = 0;
    public static final int APPTYPE_USIM = 2;
    public static final int AUDIO_OUTPUT_DEFAULT = 0;
    public static final int AUDIO_OUTPUT_DISABLE_SPEAKER = 1;
    public static final int AUDIO_OUTPUT_ENABLE_SPEAKER = 0;
    public static final int AUTHTYPE_GBA_NAF_KEY_EXTERNAL = 133;
    public static final int AUTH_CONTEXT_EAP_AKA = 129;
    public static final int AUTH_CONTEXT_EAP_SIM = 128;
    public static final int AUTH_CONTEXT_GBA_BOOTSTRAP = 132;
    public static final int AUTH_CONTEXT_UNDEFINED = -1;
    public static final String BIP_PREF_APN = "bip.pref.apn";
    public static final String BIP_PREF_AUTH_TYPE = "bip.pref.auth_type";
    public static final String BIP_PREF_ENABLE = "bip.pref.enable";
    public static final String BIP_PREF_PASSWD = "bip.pref.passwd";
    public static final String BIP_PREF_PROTOCOL = "bip.pref.protocol";
    public static final String BIP_PREF_PROXY = "bip.pref.proxy";
    public static final String BIP_PREF_ROAMING_PROTOCOL = "bip.pref.roaming_protocol";
    public static final String BIP_PREF_USER = "bip.pref.user";
    public static final int CAPABILITY_3G = 1;
    public static final int CELL_OFF_DUE_TO_AIRPLANE_MODE_FLAG = 2;
    public static final int CELL_OFF_FLAG = 0;
    public static final int CELL_ON_FLAG = 1;
    public static final String DATA_APN_KEY = "apn";
    public static final String DATA_APN_TYPE_KEY = "apnType";
    public static final String DATA_NETWORK_TYPE_KEY = "networkType";
    public static final int DEFAULT_SLOT_INDEX = 0;
    public static final int DOMAIN_NON_3GPP_PS = 4;
    public static final String EXTRA_DIAL_DOMAIN = "dial_domain";
    public static final String EXTRA_EMERGENCY_SERVICE_CATEGORY = "emergency_service_category";
    public static final String EXTRA_EMERGENCY_URNS = "emergency_urns";
    public static final String EXTRA_USE_EMERGENCY_ROUTING = "use_emergency_routing";
    public static final String FEATURE_ENABLE_BIP = "enableBIP";
    public static final String FEATURE_ENABLE_CBS = "enableCBS";
    public static final String FEATURE_ENABLE_DUN_ALWAYS = "enableDUNAlways";
    public static final String FEATURE_ENABLE_FOTA = "enableFOTA";
    public static final String FEATURE_ENABLE_HIPRI = "enableHIPRI";
    public static final String FEATURE_ENABLE_IMS = "enableIMS";
    public static final String FEATURE_ENABLE_XCAP = "enableXCAP";
    public static final int INACTIVE = 0;
    public static final int LTE_ON_CDMA_FALSE = 0;
    public static final int LTE_ON_CDMA_TRUE = 1;
    public static final int LTE_ON_CDMA_UNKNOWN = -1;
    public static final int MAX_PHONE_COUNT_DUAL_SIM = 2;
    public static final int MAX_PHONE_COUNT_SINGLE_SIM = 1;
    public static final int MAX_PHONE_COUNT_TRI_SIM = 3;
    public static final int PHONE_ID1 = 0;
    public static final int PHONE_ID2 = 1;
    public static final String PHONE_KEY = "phone";
    public static final String PHONE_NAME_KEY = "phoneName";
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_CDMA_LTE = 6;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_IMS = 5;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int PHONE_TYPE_THIRD_PARTY = 4;
    public static final int PIN_GENERAL_FAILURE = 2;
    public static final int PIN_OPERATION_ABORTED = 3;
    public static final int PIN_PASSWORD_INCORRECT = 1;
    public static final int PIN_RESULT_SUCCESS = 0;
    public static final String PORT_KEY = "port";
    public static final int PRESENTATION_ALLOWED = 1;
    public static final int PRESENTATION_PAYPHONE = 4;
    public static final int PRESENTATION_RESTRICTED = 2;
    public static final int PRESENTATION_UNAVAILABLE = 5;
    public static final int PRESENTATION_UNKNOWN = 3;
    public static final int RIL_CARD_MAX_APPS = 8;
    public static final int SIM_ID_1 = 0;
    public static final int SIM_ID_2 = 1;
    public static final int SIM_ID_3 = 2;
    public static final int SIM_ID_4 = 3;
    public static final int SIM_OFF = 1;
    public static final int SIM_ON = 0;
    public static final String SLOT_KEY = "slot";
    public static final String STATE_KEY = "state";
    public static final int SUB1 = 0;
    public static final int SUB2 = 1;
    public static final int SUB3 = 2;
    public static final String SUBSCRIPTION_KEY = "subscription";
    public static final String SUB_SETTING = "subSettings";
    public static final int UNSET_MTU = 0;

    public enum CardUnavailableReason {
        REASON_CARD_REMOVED,
        REASON_RADIO_UNAVAILABLE,
        REASON_SIM_REFRESH_RESET
    }

    public enum DataState {
        CONNECTED,
        CONNECTING,
        DISCONNECTED,
        SUSPENDED,
        DISCONNECTING
    }

    public enum State {
        IDLE,
        RINGING,
        OFFHOOK
    }
}

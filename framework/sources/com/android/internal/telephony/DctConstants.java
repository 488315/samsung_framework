package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class DctConstants {
    public static final String APN_TYPE_KEY = "apnType";
    public static final String BANDWIDTH_SOURCE_BANDWIDTH_ESTIMATOR_KEY = "bandwidth_estimator";
    public static final String BANDWIDTH_SOURCE_CARRIER_CONFIG_KEY = "carrier_config";
    public static final String BANDWIDTH_SOURCE_MODEM_KEY = "modem";
    public static final int BASE = 270336;
    public static final int CMD_CLEAR_PROVISIONING_SPINNER = 270378;
    public static final int CMD_ENABLE_MOBILE_PROVISIONING = 270373;
    public static final int CMD_IS_PROVISIONING_APN = 270374;
    public static final int CMD_NET_STAT_POLL = 270376;
    public static final int CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA = 270372;
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;
    public static final int EPS_QOS_OFFSET = 22;
    public static final int EVENT_AIRPLANE_MODE_CANCELLED = 270462;
    public static final int EVENT_ALWAYS_ON_PDN_DONE = 270455;
    public static final int EVENT_APN_CHANGED = 270355;
    public static final int EVENT_APN_UNTHROTTLED = 270392;
    public static final int EVENT_CARRIER_CONFIG_CHANGED = 270390;
    public static final int EVENT_CLEAN_UP_ALL_CONNECTIONS = 270365;
    public static final int EVENT_CLEAN_UP_CONNECTION = 270360;
    public static final int EVENT_CREATE_ALL_APN_RETRY_TIMEOUT = 270445;
    public static final int EVENT_DATA_CONNECTION_ATTACHED = 270352;
    public static final int EVENT_DATA_CONNECTION_DETACHED = 270345;
    public static final int EVENT_DATA_ENABLED_CHANGED = 270382;
    public static final int EVENT_DATA_ENABLED_OVERRIDE_RULES_CHANGED = 270387;
    public static final int EVENT_DATA_POPUP_CHECK_BOOT_COMPLETED = 270465;
    public static final int EVENT_DATA_POPUP_REQUEST_SENDIBLE = 270461;
    public static final int EVENT_DATA_RAT_CHANGED = 270377;
    public static final int EVENT_DATA_RECONNECT = 270383;
    public static final int EVENT_DATA_SERVICE_BINDING_CHANGED = 270385;
    public static final int EVENT_DATA_SETUP_COMPLETE = 270336;
    public static final int EVENT_DATA_SETUP_COMPLETE_ERROR = 270371;
    public static final int EVENT_DATA_STALL_ALARM = 270353;
    public static final int EVENT_DATA_SWITCH_TIME_OUT = 270443;
    public static final int EVENT_DEFER_DATA_ALLOW_POPUP_REQUEST = 270460;
    public static final int EVENT_DEVICE_PROVISIONED_CHANGE = 270386;
    public static final int EVENT_DISABLE_APN = 270350;
    public static final int EVENT_DISCONNECT_DONE = 270351;
    public static final int EVENT_DO_RECOVERY = 270354;
    public static final int EVENT_ENABLE_APN = 270349;
    public static final int EVENT_ENABLE_DATA_SWITCH_READY = 270436;
    public static final int EVENT_ENABLE_DATA_SWITCH_READY_FOR_MMS = 270442;
    public static final int EVENT_ENABLE_T3346 = 270446;
    public static final int EVENT_HONGBAO_RECEIVED = 270447;
    public static final int EVENT_IMS_PREFERENCE_CHANGED = 270451;
    public static final int EVENT_JPN_DATA_SETTING_CHANGED = 270464;
    public static final int EVENT_LCE_DATA_RECEIVED = 270454;
    public static final int EVENT_LTE_MODE_DB_CHANGED = 270407;
    public static final int EVENT_LTE_ROAMING_DB_CHANGED = 270406;
    public static final int EVENT_MOBILE_DATA_CHANGE = 270449;
    public static final int EVENT_NETWORK_STATUS_CHANGED = 270380;
    public static final int EVENT_NON_DDS_MMS_ENDED = 270453;
    public static final int EVENT_NON_DDS_MMS_STARTED = 270452;
    public static final int EVENT_NR_BEARER_ALLOCATION_CHANGED = 270458;
    public static final int EVENT_NR_TIMER_WATCHDOG = 270389;
    public static final int EVENT_PCO_DATA_RECEIVED = 270381;
    public static final int EVENT_PLMN_CHANGED = 270444;
    public static final int EVENT_PROVISIONING_APN_ALARM = 270375;
    public static final int EVENT_PS_RESTRICT_DISABLED = 270359;
    public static final int EVENT_PS_RESTRICT_ENABLED = 270358;
    public static final int EVENT_PS_VOICE_CALL_ENDED = 270457;
    public static final int EVENT_PS_VOICE_CALL_STARTED = 270456;
    public static final int EVENT_QOS_STATE_CHANGED = 270463;
    public static final int EVENT_RADIO_AVAILABLE = 270337;
    public static final int EVENT_RADIO_OFF_OR_NOT_AVAILABLE = 270342;
    public static final int EVENT_RESTART_RADIO = 270362;
    public static final int EVENT_RESTORE_ALL_PDN = 270459;
    public static final int EVENT_ROAMING_OFF = 270348;
    public static final int EVENT_ROAMING_ON = 270347;
    public static final int EVENT_ROAMING_SETTING_CHANGE = 270384;
    public static final int EVENT_ROUTING_AREA_CHANGED = 270439;
    public static final int EVENT_SETUP_DATA_RECORDS_LOADED = 270448;
    public static final int EVENT_SET_FORCE_INTERNAL_DATA_ENABLE = 270441;
    public static final int EVENT_SIM_STATE_UPDATED = 270391;
    public static final int EVENT_SPR_ROAMING_ON = 270408;
    public static final int EVENT_TELEPHONY_DISPLAY_INFO_CHANGED = 270388;
    public static final int EVENT_TRAFFIC_DESCRIPTORS_UPDATED = 270393;
    public static final int EVENT_TRY_SETUP_DATA = 270339;
    public static final int EVENT_VOICE_CALL_ENDED = 270344;
    public static final int EVENT_VOICE_CALL_STARTED = 270343;
    public static final int EVENT_WIFI_INVALIDATED_DURING_SUP = 270466;
    public static final int INVALID = -1;
    public static final int NR_QOS_OFFSET = 25;
    public static final String PROVISIONING_URL_KEY = "provisioningUrl";
    public static final int QOS_TYPE_EPS = 1;
    public static final int QOS_TYPE_NR = 2;
    public static final String RAT_NAME_LTE = "LTE";
    public static final String RAT_NAME_NR_NSA = "NR_NSA";
    public static final String RAT_NAME_NR_NSA_MMWAVE = "NR_NSA_MMWAVE";
    public static final String RAT_NAME_NR_SA_MMWAVE = "NR_MMWAVE";

    public enum Activity {
        NONE,
        DATAIN,
        DATAOUT,
        DATAINANDOUT,
        DORMANT
    }

    public enum State {
        IDLE,
        CONNECTING,
        RETRYING,
        CONNECTED,
        DISCONNECTING,
        FAILED
    }
}

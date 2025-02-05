package android.telephony;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public final class PreciseDisconnectCause {
    public static final int ACCESS_CLASS_BLOCKED = 260;
    public static final int ACCESS_INFORMATION_DISCARDED = 43;
    public static final int ACM_LIMIT_EXCEEDED = 68;
    public static final int BEARER_CAPABILITY_NOT_AUTHORIZED = 57;
    public static final int BEARER_NOT_AVAIL = 58;
    public static final int BEARER_SERVICE_NOT_IMPLEMENTED = 65;
    public static final int BUSY = 17;
    public static final int CALL_BARRED = 240;
    public static final int CALL_REJECTED = 21;
    public static final int CDMA_ACCESS_BLOCKED = 1009;
    public static final int CDMA_ACCESS_FAILURE = 1006;
    public static final int CDMA_DROP = 1001;
    public static final int CDMA_INTERCEPT = 1002;
    public static final int CDMA_LOCKED_UNTIL_POWER_CYCLE = 1000;
    public static final int CDMA_NOT_EMERGENCY = 1008;
    public static final int CDMA_PREEMPTED = 1007;
    public static final int CDMA_REORDER = 1003;
    public static final int CDMA_RETRY_ORDER = 1005;
    public static final int CDMA_SO_REJECT = 1004;
    public static final int CHANNEL_NOT_AVAIL = 44;
    public static final int CHANNEL_UNACCEPTABLE = 6;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int DESTINATION_OUT_OF_ORDER = 27;
    public static final int EMERGENCY_PERM_FAILURE = 326;
    public static final int EMERGENCY_TEMP_FAILURE = 325;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int FACILITY_REJECTED = 29;
    public static final int FDN_BLOCKED = 241;
    public static final int IMEI_NOT_ACCEPTED = 243;
    public static final int IMSI_UNKNOWN_IN_VLR = 242;
    public static final int INCOMING_CALLS_BARRED_WITHIN_CUG = 55;
    public static final int INCOMPATIBLE_DESTINATION = 88;
    public static final int INFORMATION_ELEMENT_NON_EXISTENT = 99;
    public static final int INTERWORKING_UNSPECIFIED = 127;
    public static final int INVALID_MANDATORY_INFORMATION = 96;
    public static final int INVALID_NUMBER_FORMAT = 28;
    public static final int INVALID_TRANSACTION_IDENTIFIER = 81;
    public static final int MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE = 101;
    public static final int MESSAGE_TYPE_NON_IMPLEMENTED = 97;
    public static final int MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE = 98;
    public static final int NETWORK_DETACH = 261;
    public static final int NETWORK_OUT_OF_ORDER = 38;
    public static final int NETWORK_REJECT = 252;
    public static final int NETWORK_RESP_TIMEOUT = 251;
    public static final int NORMAL = 16;
    public static final int NORMAL_UNSPECIFIED = 31;
    public static final int NOT_VALID = -1;
    public static final int NO_ANSWER_FROM_USER = 19;
    public static final int NO_CIRCUIT_AVAIL = 34;
    public static final int NO_DISCONNECT_CAUSE_AVAILABLE = 0;
    public static final int NO_ROUTE_TO_DESTINATION = 3;
    public static final int NO_USER_RESPONDING = 18;
    public static final int NO_VALID_SIM = 249;
    public static final int NUMBER_CHANGED = 22;
    public static final int OEM_CAUSE_1 = 61441;
    public static final int OEM_CAUSE_10 = 61450;
    public static final int OEM_CAUSE_11 = 61451;
    public static final int OEM_CAUSE_12 = 61452;
    public static final int OEM_CAUSE_13 = 61453;
    public static final int OEM_CAUSE_14 = 61454;
    public static final int OEM_CAUSE_15 = 61455;
    public static final int OEM_CAUSE_2 = 61442;
    public static final int OEM_CAUSE_3 = 61443;
    public static final int OEM_CAUSE_4 = 61444;
    public static final int OEM_CAUSE_5 = 61445;
    public static final int OEM_CAUSE_6 = 61446;
    public static final int OEM_CAUSE_7 = 61447;
    public static final int OEM_CAUSE_8 = 61448;
    public static final int OEM_CAUSE_9 = 61449;
    public static final int ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE = 70;
    public static final int OPERATOR_DETERMINED_BARRING = 8;
    public static final int OUT_OF_SRV = 248;
    public static final int PREEMPTION = 25;
    public static final int PROTOCOL_ERROR_UNSPECIFIED = 111;
    public static final int QOS_NOT_AVAIL = 49;
    public static final int RADIO_ACCESS_FAILURE = 253;
    public static final int RADIO_INTERNAL_ERROR = 250;
    public static final int RADIO_LINK_FAILURE = 254;
    public static final int RADIO_LINK_LOST = 255;
    public static final int RADIO_OFF = 247;
    public static final int RADIO_RELEASE_ABNORMAL = 259;
    public static final int RADIO_RELEASE_NORMAL = 258;
    public static final int RADIO_SETUP_FAILURE = 257;
    public static final int RADIO_UPLINK_FAILURE = 256;
    public static final int RECOVERY_ON_TIMER_EXPIRED = 102;
    public static final int REQUESTED_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int REQUESTED_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int RESOURCES_UNAVAILABLE_OR_UNSPECIFIED = 47;
    public static final int SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SERVICE_OPTION_NOT_AVAILABLE = 63;
    public static final int SERVICE_OR_OPTION_NOT_IMPLEMENTED = 79;
    public static final int STATUS_ENQUIRY = 30;
    public static final int SWITCHING_CONGESTION = 42;
    public static final int TEMPORARY_FAILURE = 41;
    public static final int UNOBTAINABLE_NUMBER = 1;
    public static final int USER_NOT_MEMBER_OF_CUG = 87;

    private PreciseDisconnectCause() {
    }
}

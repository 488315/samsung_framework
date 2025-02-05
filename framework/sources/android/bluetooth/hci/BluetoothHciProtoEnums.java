package android.bluetooth.hci;

/* loaded from: classes.dex */
public final class BluetoothHciProtoEnums {
    public static final int BLE_EVT_ADVERTISING_SET_TERMINATED_EVT = 18;
    public static final int BLE_EVT_ADV_PKT_RPT_EVT = 2;
    public static final int BLE_EVT_CHNL_SELECTION_ALGORITHM = 20;
    public static final int BLE_EVT_CONN_COMPLETE_EVT = 1;
    public static final int BLE_EVT_DATA_LENGTH_CHANGE_EVT = 7;
    public static final int BLE_EVT_DIRECT_ADV_EVT = 11;
    public static final int BLE_EVT_ENHANCED_CONN_COMPLETE_EVT = 10;
    public static final int BLE_EVT_EXTENDED_ADVERTISING_REPORT_EVT = 13;
    public static final int BLE_EVT_GEN_DHKEY_CMPL = 9;
    public static final int BLE_EVT_LL_CONN_PARAM_UPD_EVT = 3;
    public static final int BLE_EVT_LTK_REQ_EVT = 5;
    public static final int BLE_EVT_PERIODIC_ADV_REPORT_EVT = 15;
    public static final int BLE_EVT_PERIODIC_ADV_SYNC_EST_EVT = 14;
    public static final int BLE_EVT_PERIODIC_ADV_SYNC_LOST_EVT = 16;
    public static final int BLE_EVT_PHY_UPDATE_COMPLETE_EVT = 12;
    public static final int BLE_EVT_RC_PARAM_REQ_EVT = 6;
    public static final int BLE_EVT_READ_LOCAL_P256_PUB_KEY = 8;
    public static final int BLE_EVT_READ_REMOTE_FEAT_CMPL_EVT = 4;
    public static final int BLE_EVT_SCAN_REQ_RX_EVT = 19;
    public static final int BLE_EVT_SCAN_TIMEOUT_EVT = 17;
    public static final int BLE_EVT_UNKNOWN = 4095;
    public static final int BQR_ID_A2DP_AUDIO_CHOPPY = 3;
    public static final int BQR_ID_APPROACH_LSTO = 2;
    public static final int BQR_ID_MONITOR_MODE = 1;
    public static final int BQR_ID_SCO_VOICE_CHOPPY = 4;
    public static final int BQR_ID_UNKNOWN = 0;
    public static final int BQR_PACKET_TYPE_2DH1 = 23;
    public static final int BQR_PACKET_TYPE_2DH3 = 24;
    public static final int BQR_PACKET_TYPE_2DH5 = 25;
    public static final int BQR_PACKET_TYPE_2EV3 = 12;
    public static final int BQR_PACKET_TYPE_2EV5 = 13;
    public static final int BQR_PACKET_TYPE_3DH1 = 26;
    public static final int BQR_PACKET_TYPE_3DH3 = 27;
    public static final int BQR_PACKET_TYPE_3DH5 = 28;
    public static final int BQR_PACKET_TYPE_3EV3 = 14;
    public static final int BQR_PACKET_TYPE_3EV5 = 15;
    public static final int BQR_PACKET_TYPE_AUX1 = 22;
    public static final int BQR_PACKET_TYPE_DH1 = 17;
    public static final int BQR_PACKET_TYPE_DH3 = 19;
    public static final int BQR_PACKET_TYPE_DH5 = 21;
    public static final int BQR_PACKET_TYPE_DM1 = 16;
    public static final int BQR_PACKET_TYPE_DM3 = 18;
    public static final int BQR_PACKET_TYPE_DM5 = 20;
    public static final int BQR_PACKET_TYPE_DV = 8;
    public static final int BQR_PACKET_TYPE_EV3 = 9;
    public static final int BQR_PACKET_TYPE_EV4 = 10;
    public static final int BQR_PACKET_TYPE_EV5 = 11;
    public static final int BQR_PACKET_TYPE_FHS = 4;
    public static final int BQR_PACKET_TYPE_HV1 = 5;
    public static final int BQR_PACKET_TYPE_HV2 = 6;
    public static final int BQR_PACKET_TYPE_HV3 = 7;
    public static final int BQR_PACKET_TYPE_ID = 1;
    public static final int BQR_PACKET_TYPE_NULL = 2;
    public static final int BQR_PACKET_TYPE_POLL = 3;
    public static final int BQR_PACKET_TYPE_UNKNOWN = 0;
    public static final int CMD_ACCEPT_CONNECTION_REQUEST = 1033;
    public static final int CMD_ACCEPT_ESCO_CONNECTION = 1065;
    public static final int CMD_ACCEPT_LOGICAL_LINK = 1081;
    public static final int CMD_ACCEPT_PHYSICAL_LINK = 1078;
    public static final int CMD_ADD_SCO_CONNECTION = 1031;
    public static final int CMD_AMP_TEST = 6153;
    public static final int CMD_AMP_TEST_END = 6152;
    public static final int CMD_AUTHENTICATION_REQUESTED = 1041;
    public static final int CMD_BLE_ADD_DEVICE_TO_PERIODIC_ADVERTISING_LIST = 8263;
    public static final int CMD_BLE_ADD_DEV_RESOLVING_LIST = 8231;
    public static final int CMD_BLE_ADD_WHITE_LIST = 8209;
    public static final int CMD_BLE_ADV_FILTER = 64855;
    public static final int CMD_BLE_BATCH_SCAN = 64854;
    public static final int CMD_BLE_CLEAR_ADVERTISING_SETS = 8253;
    public static final int CMD_BLE_CLEAR_PERIODIC_ADVERTISING_LIST = 8265;
    public static final int CMD_BLE_CLEAR_RESOLVING_LIST = 8233;
    public static final int CMD_BLE_CLEAR_WHITE_LIST = 8208;
    public static final int CMD_BLE_CREATE_CONN_CANCEL = 8206;
    public static final int CMD_BLE_CREATE_LL_CONN = 8205;
    public static final int CMD_BLE_ENCRYPT = 8215;
    public static final int CMD_BLE_ENERGY_INFO = 64857;
    public static final int CMD_BLE_ENH_RECEIVER_TEST = 8243;
    public static final int CMD_BLE_ENH_TRANSMITTER_TEST = 8244;
    public static final int CMD_BLE_EXTENDED_CREATE_CONNECTION = 8259;
    public static final int CMD_BLE_EXTENDED_SCAN_PARAMS = 64858;
    public static final int CMD_BLE_GENERATE_DHKEY = 8230;
    public static final int CMD_BLE_LTK_REQ_NEG_REPLY = 8219;
    public static final int CMD_BLE_LTK_REQ_REPLY = 8218;
    public static final int CMD_BLE_MULTI_ADV = 64852;
    public static final int CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC = 8260;
    public static final int CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC_CANCEL = 8261;
    public static final int CMD_BLE_PERIODIC_ADVERTISING_TERMINATE_SYNC = 8262;
    public static final int CMD_BLE_RAND = 8216;
    public static final int CMD_BLE_RC_PARAM_REQ_NEG_REPLY = 8225;
    public static final int CMD_BLE_RC_PARAM_REQ_REPLY = 8224;
    public static final int CMD_BLE_READ_ADV_CHNL_TX_POWER = 8199;
    public static final int CMD_BLE_READ_BUFFER_SIZE = 8194;
    public static final int CMD_BLE_READ_CHNL_MAP = 8213;
    public static final int CMD_BLE_READ_DEFAULT_DATA_LENGTH = 8227;
    public static final int CMD_BLE_READ_LOCAL_SPT_FEAT = 8195;
    public static final int CMD_BLE_READ_MAXIMUM_ADVERTISING_DATA_LENGTH = 8250;
    public static final int CMD_BLE_READ_MAXIMUM_DATA_LENGTH = 8239;
    public static final int CMD_BLE_READ_NUMBER_OF_SUPPORTED_ADVERTISING_SETS = 8251;
    public static final int CMD_BLE_READ_PERIODIC_ADVERTISING_LIST_SIZE = 8266;
    public static final int CMD_BLE_READ_PHY = 8240;
    public static final int CMD_BLE_READ_REMOTE_FEAT = 8214;
    public static final int CMD_BLE_READ_RESOLVABLE_ADDR_LOCAL = 8236;
    public static final int CMD_BLE_READ_RESOLVABLE_ADDR_PEER = 8235;
    public static final int CMD_BLE_READ_RESOLVING_LIST_SIZE = 8234;
    public static final int CMD_BLE_READ_RF_COMPENS_POWER = 8268;
    public static final int CMD_BLE_READ_SUPPORTED_STATES = 8220;
    public static final int CMD_BLE_READ_TRANSMIT_POWER = 8267;
    public static final int CMD_BLE_READ_WHITE_LIST_SIZE = 8207;
    public static final int CMD_BLE_RECEIVER_TEST = 8221;
    public static final int CMD_BLE_REMOVE_ADVERTISING_SET = 8252;
    public static final int CMD_BLE_REMOVE_WHITE_LIST = 8210;
    public static final int CMD_BLE_RM_DEVICE_FROM_PERIODIC_ADVERTISING_LIST = 8264;
    public static final int CMD_BLE_RM_DEV_RESOLVING_LIST = 8232;
    public static final int CMD_BLE_SET_ADDR_RESOLUTION_ENABLE = 8237;
    public static final int CMD_BLE_SET_DATA_LENGTH = 8226;
    public static final int CMD_BLE_SET_DEFAULT_PHY = 8241;
    public static final int CMD_BLE_SET_EVENT_MASK = 8193;
    public static final int CMD_BLE_SET_EXTENDED_SCAN_ENABLE = 8258;
    public static final int CMD_BLE_SET_EXTENDED_SCAN_PARAMETERS = 8257;
    public static final int CMD_BLE_SET_EXT_ADVERTISING_DATA = 8247;
    public static final int CMD_BLE_SET_EXT_ADVERTISING_ENABLE = 8249;
    public static final int CMD_BLE_SET_EXT_ADVERTISING_PARAM = 8246;
    public static final int CMD_BLE_SET_EXT_ADVERTISING_RANDOM_ADDRESS = 8245;
    public static final int CMD_BLE_SET_EXT_ADVERTISING_SCAN_RESP = 8248;
    public static final int CMD_BLE_SET_HOST_CHNL_CLASS = 8212;
    public static final int CMD_BLE_SET_PERIODIC_ADVERTISING_DATA = 8255;
    public static final int CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE = 8256;
    public static final int CMD_BLE_SET_PERIODIC_ADVERTISING_PARAM = 8254;
    public static final int CMD_BLE_SET_PHY = 8242;
    public static final int CMD_BLE_SET_PRIVACY_MODE = 8270;
    public static final int CMD_BLE_SET_RAND_PRIV_ADDR_TIMOUT = 8238;
    public static final int CMD_BLE_START_ENC = 8217;
    public static final int CMD_BLE_TEST_END = 8223;
    public static final int CMD_BLE_TRACK_ADV = 64856;
    public static final int CMD_BLE_TRANSMITTER_TEST = 8222;
    public static final int CMD_BLE_UPD_LL_CONN_PARAMS = 8211;
    public static final int CMD_BLE_VENDOR_CAP = 64851;
    public static final int CMD_BLE_WRITE_ADV_DATA = 8200;
    public static final int CMD_BLE_WRITE_ADV_ENABLE = 8202;
    public static final int CMD_BLE_WRITE_ADV_PARAMS = 8198;
    public static final int CMD_BLE_WRITE_DEFAULT_DATA_LENGTH = 8228;
    public static final int CMD_BLE_WRITE_LOCAL_SPT_FEAT = 8196;
    public static final int CMD_BLE_WRITE_RANDOM_ADDR = 8197;
    public static final int CMD_BLE_WRITE_RF_COMPENS_POWER = 8269;
    public static final int CMD_BLE_WRITE_SCAN_ENABLE = 8204;
    public static final int CMD_BLE_WRITE_SCAN_PARAMS = 8203;
    public static final int CMD_BLE_WRITE_SCAN_RSP_DATA = 8201;
    public static final int CMD_BRCM_SET_ACL_PRIORITY = 64599;
    public static final int CMD_CHANGE_CONN_LINK_KEY = 1045;
    public static final int CMD_CHANGE_CONN_PACKET_TYPE = 1039;
    public static final int CMD_CHANGE_LOCAL_NAME = 3091;
    public static final int CMD_CONTROLLER_A2DP_OPCODE = 64861;
    public static final int CMD_CONTROLLER_DEBUG_INFO = 64859;
    public static final int CMD_CREATE_CONNECTION = 1029;
    public static final int CMD_CREATE_CONNECTION_CANCEL = 1032;
    public static final int CMD_CREATE_LOGICAL_LINK = 1080;
    public static final int CMD_CREATE_NEW_UNIT_KEY = 3083;
    public static final int CMD_CREATE_PHYSICAL_LINK = 1077;
    public static final int CMD_DELETE_RESERVED_LT_ADDR = 3189;
    public static final int CMD_DELETE_STORED_LINK_KEY = 3090;
    public static final int CMD_DISCONNECT = 1030;
    public static final int CMD_DISCONNECT_LOGICAL_LINK = 1082;
    public static final int CMD_DISCONNECT_PHYSICAL_LINK = 1079;
    public static final int CMD_ENABLE_AMP_RCVR_REPORTS = 6151;
    public static final int CMD_ENABLE_DEV_UNDER_TEST_MODE = 6147;
    public static final int CMD_ENHANCED_FLUSH = 3167;
    public static final int CMD_ENH_ACCEPT_ESCO_CONNECTION = 1086;
    public static final int CMD_ENH_SETUP_ESCO_CONNECTION = 1085;
    public static final int CMD_EXIT_PARK_MODE = 2054;
    public static final int CMD_EXIT_PERIODIC_INQUIRY_MODE = 1028;
    public static final int CMD_EXIT_SNIFF_MODE = 2052;
    public static final int CMD_FLOW_SPECIFICATION = 2064;
    public static final int CMD_FLOW_SPEC_MODIFY = 1084;
    public static final int CMD_FLUSH = 3080;
    public static final int CMD_GET_LINK_QUALITY = 5123;
    public static final int CMD_GET_MWS_TRANSPORT_CFG = 5132;
    public static final int CMD_GET_MWS_TRANS_LAYER_CFG = 3084;
    public static final int CMD_HOLD_MODE = 2049;
    public static final int CMD_HOST_BUFFER_SIZE = 3123;
    public static final int CMD_HOST_NUM_PACKETS_DONE = 3125;
    public static final int CMD_INQUIRY = 1025;
    public static final int CMD_INQUIRY_CANCEL = 1026;
    public static final int CMD_IO_CAPABILITY_REQUEST_REPLY = 1067;
    public static final int CMD_IO_CAP_REQ_NEG_REPLY = 1076;
    public static final int CMD_LINK_KEY_REQUEST_NEG_REPLY = 1036;
    public static final int CMD_LINK_KEY_REQUEST_REPLY = 1035;
    public static final int CMD_LOGICAL_LINK_CANCEL = 1083;
    public static final int CMD_MASTER_LINK_KEY = 1047;
    public static final int CMD_PARK_MODE = 2053;
    public static final int CMD_PERIODIC_INQUIRY_MODE = 1027;
    public static final int CMD_PIN_CODE_REQUEST_NEG_REPLY = 1038;
    public static final int CMD_PIN_CODE_REQUEST_REPLY = 1037;
    public static final int CMD_QOS_SETUP = 2055;
    public static final int CMD_READ_AFH_ASSESSMENT_MODE = 3144;
    public static final int CMD_READ_AFH_CH_MAP = 5126;
    public static final int CMD_READ_AUTHED_PAYLOAD_TIMEOUT = 3195;
    public static final int CMD_READ_AUTHENTICATION_ENABLE = 3103;
    public static final int CMD_READ_AUTOMATIC_FLUSH_TIMEOUT = 3111;
    public static final int CMD_READ_BD_ADDR = 4105;
    public static final int CMD_READ_BE_FLUSH_TOUT = 3177;
    public static final int CMD_READ_BLE_HOST_SUPPORT = 3180;
    public static final int CMD_READ_BUFFER_SIZE = 4101;
    public static final int CMD_READ_CLASS_OF_DEVICE = 3107;
    public static final int CMD_READ_CLOCK = 5127;
    public static final int CMD_READ_CONN_ACCEPT_TOUT = 3093;
    public static final int CMD_READ_COUNTRY_CODE = 4103;
    public static final int CMD_READ_CURRENT_IAC_LAP = 3129;
    public static final int CMD_READ_DATA_BLOCK_SIZE = 4106;
    public static final int CMD_READ_DEF_POLICY_SETTINGS = 2062;
    public static final int CMD_READ_ENCRYPTION_MODE = 3105;
    public static final int CMD_READ_ENCR_KEY_SIZE = 5128;
    public static final int CMD_READ_ENHANCED_TX_PWR_LEVEL = 3176;
    public static final int CMD_READ_ERRONEOUS_DATA_RPT = 3162;
    public static final int CMD_READ_EXTENDED_INQUIRY_LENGTH = 3200;
    public static final int CMD_READ_EXTENDED_PAGE_TIMEOUT = 3198;
    public static final int CMD_READ_EXT_INQ_RESPONSE = 3153;
    public static final int CMD_READ_FAILED_CONTACT_COUNTER = 5121;
    public static final int CMD_READ_FLOW_CONTROL_MODE = 3174;
    public static final int CMD_READ_HOLD_MODE_ACTIVITY = 3115;
    public static final int CMD_READ_INQSCAN_TYPE = 3138;
    public static final int CMD_READ_INQUIRYSCAN_CFG = 3101;
    public static final int CMD_READ_INQUIRY_MODE = 3140;
    public static final int CMD_READ_INQ_TX_POWER_LEVEL = 3160;
    public static final int CMD_READ_LINK_SUPER_TOUT = 3126;
    public static final int CMD_READ_LMP_HANDLE = 1056;
    public static final int CMD_READ_LOCAL_AMP_ASSOC = 5130;
    public static final int CMD_READ_LOCAL_AMP_INFO = 5129;
    public static final int CMD_READ_LOCAL_EXT_FEATURES = 4100;
    public static final int CMD_READ_LOCAL_FEATURES = 4099;
    public static final int CMD_READ_LOCAL_NAME = 3092;
    public static final int CMD_READ_LOCAL_OOB_DATA = 3159;
    public static final int CMD_READ_LOCAL_OOB_EXTENDED_DATA = 3197;
    public static final int CMD_READ_LOCAL_SUPPORTED_CMDS = 4098;
    public static final int CMD_READ_LOCAL_SUPPORTED_CODECS = 4107;
    public static final int CMD_READ_LOCAL_VERSION_INFO = 4097;
    public static final int CMD_READ_LOCATION_DATA = 3172;
    public static final int CMD_READ_LOGICAL_LINK_ACCEPT_TIMEOUT = 3169;
    public static final int CMD_READ_LOOPBACK_MODE = 6145;
    public static final int CMD_READ_NUM_BCAST_REXMITS = 3113;
    public static final int CMD_READ_NUM_SUPPORTED_IAC = 3128;
    public static final int CMD_READ_PAGESCAN_CFG = 3099;
    public static final int CMD_READ_PAGESCAN_MODE = 3133;
    public static final int CMD_READ_PAGESCAN_PERIOD_MODE = 3131;
    public static final int CMD_READ_PAGESCAN_TYPE = 3142;
    public static final int CMD_READ_PAGE_TOUT = 3095;
    public static final int CMD_READ_PIN_TYPE = 3081;
    public static final int CMD_READ_POLICY_SETTINGS = 2060;
    public static final int CMD_READ_RMT_CLOCK_OFFSET = 1055;
    public static final int CMD_READ_RMT_EXT_FEATURES = 1052;
    public static final int CMD_READ_RMT_FEATURES = 1051;
    public static final int CMD_READ_RMT_VERSION_INFO = 1053;
    public static final int CMD_READ_RSSI = 5125;
    public static final int CMD_READ_SCAN_ENABLE = 3097;
    public static final int CMD_READ_SCO_FLOW_CTRL_ENABLE = 3118;
    public static final int CMD_READ_SECURE_CONNS_SUPPORT = 3193;
    public static final int CMD_READ_SIMPLE_PAIRING_MODE = 3157;
    public static final int CMD_READ_STORED_LINK_KEY = 3085;
    public static final int CMD_READ_SYNC_TRAIN_PARAM = 3191;
    public static final int CMD_READ_TRANSMIT_POWER_LEVEL = 3117;
    public static final int CMD_READ_VOICE_SETTINGS = 3109;
    public static final int CMD_RECEIVE_CLB = 1090;
    public static final int CMD_RECEIVE_SYNC_TRAIN = 1092;
    public static final int CMD_REFRESH_ENCRYPTION_KEY = 3155;
    public static final int CMD_REJECT_CONNECTION_REQUEST = 1034;
    public static final int CMD_REJECT_ESCO_CONNECTION = 1066;
    public static final int CMD_REM_OOB_DATA_REQ_NEG_REPLY = 1075;
    public static final int CMD_REM_OOB_DATA_REQ_REPLY = 1072;
    public static final int CMD_REM_OOB_EXTENDED_DATA_REQ_REPLY = 1093;
    public static final int CMD_RESET = 3075;
    public static final int CMD_RESET_FAILED_CONTACT_COUNTER = 5122;
    public static final int CMD_RMT_NAME_REQUEST = 1049;
    public static final int CMD_RMT_NAME_REQUEST_CANCEL = 1050;
    public static final int CMD_ROLE_DISCOVERY = 2057;
    public static final int CMD_SEND_KEYPRESS_NOTIF = 3168;
    public static final int CMD_SETUP_ESCO_CONNECTION = 1064;
    public static final int CMD_SET_AFH_CHANNELS = 3135;
    public static final int CMD_SET_CLB = 1089;
    public static final int CMD_SET_CONN_ENCRYPTION = 1043;
    public static final int CMD_SET_EVENT_FILTER = 3077;
    public static final int CMD_SET_EVENT_MASK = 3073;
    public static final int CMD_SET_EVENT_MASK_PAGE_2 = 3171;
    public static final int CMD_SET_EXTERNAL_FRAME_CONFIGURATION = 3183;
    public static final int CMD_SET_HC_TO_HOST_FLOW_CTRL = 3121;
    public static final int CMD_SET_MWS_CHANNEL_PARAMETERS = 3182;
    public static final int CMD_SET_MWS_PATTERN_CONFIGURATION = 3187;
    public static final int CMD_SET_MWS_SCAN_FREQUENCY_TABLE = 3186;
    public static final int CMD_SET_MWS_SIGNALING = 3184;
    public static final int CMD_SET_MWS_TRANSPORT_LAYER = 3185;
    public static final int CMD_SET_RESERVED_LT_ADDR = 3188;
    public static final int CMD_SET_TRIGGERED_CLK_CAPTURE = 5133;
    public static final int CMD_SHORT_RANGE_MODE = 3179;
    public static final int CMD_SNIFF_MODE = 2051;
    public static final int CMD_SNIFF_SUB_RATE = 2065;
    public static final int CMD_START_SYNC_TRAIN = 1091;
    public static final int CMD_SWITCH_ROLE = 2059;
    public static final int CMD_TRUNCATED_PAGE = 1087;
    public static final int CMD_TRUNCATED_PAGE_CANCEL = 1088;
    public static final int CMD_UNKNOWN = 1048575;
    public static final int CMD_USER_CONF_REQUEST_REPLY = 1068;
    public static final int CMD_USER_CONF_VALUE_NEG_REPLY = 1069;
    public static final int CMD_USER_PASSKEY_REQ_NEG_REPLY = 1071;
    public static final int CMD_USER_PASSKEY_REQ_REPLY = 1070;
    public static final int CMD_WRITE_AFH_ASSESSMENT_MODE = 3145;
    public static final int CMD_WRITE_AUTHED_PAYLOAD_TIMEOUT = 3196;
    public static final int CMD_WRITE_AUTHENTICATION_ENABLE = 3104;
    public static final int CMD_WRITE_AUTOMATIC_FLUSH_TIMEOUT = 3112;
    public static final int CMD_WRITE_BE_FLUSH_TOUT = 3178;
    public static final int CMD_WRITE_BLE_HOST_SUPPORT = 3181;
    public static final int CMD_WRITE_CLASS_OF_DEVICE = 3108;
    public static final int CMD_WRITE_CLB_DATA = 3190;
    public static final int CMD_WRITE_CONN_ACCEPT_TOUT = 3094;
    public static final int CMD_WRITE_CURRENT_IAC_LAP = 3130;
    public static final int CMD_WRITE_DEF_POLICY_SETTINGS = 2063;
    public static final int CMD_WRITE_ENCRYPTION_MODE = 3106;
    public static final int CMD_WRITE_ERRONEOUS_DATA_RPT = 3163;
    public static final int CMD_WRITE_EXTENDED_INQUIRY_LENGTH = 3201;
    public static final int CMD_WRITE_EXTENDED_PAGE_TIMEOUT = 3199;
    public static final int CMD_WRITE_EXT_INQ_RESPONSE = 3154;
    public static final int CMD_WRITE_FLOW_CONTROL_MODE = 3175;
    public static final int CMD_WRITE_HOLD_MODE_ACTIVITY = 3116;
    public static final int CMD_WRITE_INQSCAN_TYPE = 3139;
    public static final int CMD_WRITE_INQUIRYSCAN_CFG = 3102;
    public static final int CMD_WRITE_INQUIRY_MODE = 3141;
    public static final int CMD_WRITE_INQ_TX_POWER_LEVEL = 3161;
    public static final int CMD_WRITE_LINK_SUPER_TOUT = 3127;
    public static final int CMD_WRITE_LOCATION_DATA = 3173;
    public static final int CMD_WRITE_LOGICAL_LINK_ACCEPT_TIMEOUT = 3170;
    public static final int CMD_WRITE_LOOPBACK_MODE = 6146;
    public static final int CMD_WRITE_NUM_BCAST_REXMITS = 3114;
    public static final int CMD_WRITE_PAGESCAN_CFG = 3100;
    public static final int CMD_WRITE_PAGESCAN_MODE = 3134;
    public static final int CMD_WRITE_PAGESCAN_PERIOD_MODE = 3132;
    public static final int CMD_WRITE_PAGESCAN_TYPE = 3143;
    public static final int CMD_WRITE_PAGE_TOUT = 3096;
    public static final int CMD_WRITE_PIN_TYPE = 3082;
    public static final int CMD_WRITE_POLICY_SETTINGS = 2061;
    public static final int CMD_WRITE_REMOTE_AMP_ASSOC = 5131;
    public static final int CMD_WRITE_SCAN_ENABLE = 3098;
    public static final int CMD_WRITE_SCO_FLOW_CTRL_ENABLE = 3119;
    public static final int CMD_WRITE_SECURE_CONNS_SUPPORT = 3194;
    public static final int CMD_WRITE_SECURE_CONN_TEST_MODE = 6154;
    public static final int CMD_WRITE_SIMPLE_PAIRING_MODE = 3158;
    public static final int CMD_WRITE_SIMP_PAIR_DEBUG_MODE = 6148;
    public static final int CMD_WRITE_STORED_LINK_KEY = 3089;
    public static final int CMD_WRITE_SYNC_TRAIN_PARAM = 3192;
    public static final int CMD_WRITE_VOICE_SETTINGS = 3110;
    public static final int EVT_AMP_RECEIVER_RPT = 75;
    public static final int EVT_AMP_STATUS_CHANGE = 77;
    public static final int EVT_AMP_TEST_END = 74;
    public static final int EVT_AMP_TEST_START = 73;
    public static final int EVT_AUTHED_PAYLOAD_TIMEOUT = 87;
    public static final int EVT_AUTHENTICATION_COMP = 6;
    public static final int EVT_BLE_META = 62;
    public static final int EVT_CHANGE_CONN_LINK_KEY = 9;
    public static final int EVT_CHANNEL_SELECTED = 65;
    public static final int EVT_COMMAND_COMPLETE = 14;
    public static final int EVT_COMMAND_STATUS = 15;
    public static final int EVT_CONNECTION_COMP = 3;
    public static final int EVT_CONNECTION_REQUEST = 4;
    public static final int EVT_CONNLESS_SLAVE_BROADCAST_CHNL_MAP_CHANGE = 85;
    public static final int EVT_CONNLESS_SLAVE_BROADCAST_RCVD = 81;
    public static final int EVT_CONNLESS_SLAVE_BROADCAST_TIMEOUT = 82;
    public static final int EVT_CONN_PKT_TYPE_CHANGE = 29;
    public static final int EVT_DATA_BUF_OVERFLOW = 26;
    public static final int EVT_DISCONNECTION_COMP = 5;
    public static final int EVT_DISC_LOGICAL_LINK_COMP = 70;
    public static final int EVT_DISC_PHYSICAL_LINK_COMP = 66;
    public static final int EVT_ENCRYPTION_CHANGE = 8;
    public static final int EVT_ENCRYPTION_KEY_REFRESH_COMP = 48;
    public static final int EVT_ENHANCED_FLUSH_COMPLETE = 57;
    public static final int EVT_ESCO_CONNECTION_CHANGED = 45;
    public static final int EVT_ESCO_CONNECTION_COMP = 44;
    public static final int EVT_EXTENDED_INQUIRY_RESULT = 47;
    public static final int EVT_FLOW_SPECIFICATION_COMP = 33;
    public static final int EVT_FLOW_SPEC_MODIFY_COMP = 71;
    public static final int EVT_FLUSH_OCCURRED = 17;
    public static final int EVT_HARDWARE_ERROR = 16;
    public static final int EVT_INQUIRY_COMP = 1;
    public static final int EVT_INQUIRY_RESULT = 2;
    public static final int EVT_INQUIRY_RES_NOTIFICATION = 86;
    public static final int EVT_INQUIRY_RSSI_RESULT = 34;
    public static final int EVT_IO_CAPABILITY_REQUEST = 49;
    public static final int EVT_IO_CAPABILITY_RESPONSE = 50;
    public static final int EVT_KEYPRESS_NOTIFY = 60;
    public static final int EVT_LINK_KEY_NOTIFICATION = 24;
    public static final int EVT_LINK_KEY_REQUEST = 23;
    public static final int EVT_LINK_SUPER_TOUT_CHANGED = 56;
    public static final int EVT_LOGICAL_LINK_COMP = 69;
    public static final int EVT_LOOPBACK_COMMAND = 25;
    public static final int EVT_MASTER_LINK_KEY_COMP = 10;
    public static final int EVT_MAX_SLOTS_CHANGED = 27;
    public static final int EVT_MODE_CHANGE = 20;
    public static final int EVT_NUM_COMPL_DATA_BLOCKS = 72;
    public static final int EVT_NUM_COMPL_DATA_PKTS = 19;
    public static final int EVT_PAGE_SCAN_MODE_CHANGE = 31;
    public static final int EVT_PAGE_SCAN_REP_MODE_CHNG = 32;
    public static final int EVT_PHYSICAL_LINK_COMP = 64;
    public static final int EVT_PHY_LINK_LOSS_EARLY_WARNING = 67;
    public static final int EVT_PHY_LINK_RECOVERY = 68;
    public static final int EVT_PIN_CODE_REQUEST = 22;
    public static final int EVT_QOS_SETUP_COMP = 13;
    public static final int EVT_QOS_VIOLATION = 30;
    public static final int EVT_READ_CLOCK_OFF_COMP = 28;
    public static final int EVT_READ_RMT_EXT_FEATURES_COMP = 35;
    public static final int EVT_READ_RMT_FEATURES_COMP = 11;
    public static final int EVT_READ_RMT_VERSION_COMP = 12;
    public static final int EVT_REMOTE_OOB_DATA_REQUEST = 53;
    public static final int EVT_RETURN_LINK_KEYS = 21;
    public static final int EVT_RMT_HOST_SUP_FEAT_NOTIFY = 61;
    public static final int EVT_RMT_NAME_REQUEST_COMP = 7;
    public static final int EVT_ROLE_CHANGE = 18;
    public static final int EVT_SAM_STATUS_CHANGE = 88;
    public static final int EVT_SET_TRIGGERED_CLOCK_CAPTURE = 78;
    public static final int EVT_SHORT_RANGE_MODE_COMPLETE = 76;
    public static final int EVT_SIMPLE_PAIRING_COMPLETE = 54;
    public static final int EVT_SLAVE_PAGE_RES_TIMEOUT = 84;
    public static final int EVT_SNIFF_SUB_RATE = 46;
    public static final int EVT_SYNC_TRAIN_CMPL = 79;
    public static final int EVT_SYNC_TRAIN_RCVD = 80;
    public static final int EVT_TRUNCATED_PAGE_CMPL = 83;
    public static final int EVT_UNKNOWN = 4095;
    public static final int EVT_USER_CONFIRMATION_REQUEST = 51;
    public static final int EVT_USER_PASSKEY_NOTIFY = 59;
    public static final int EVT_USER_PASSKEY_REQUEST = 52;
    public static final int STATUS_ADVERTISING_TIMEOUT = 60;
    public static final int STATUS_AUTH_FAILURE = 5;
    public static final int STATUS_CHAN_CLASSIF_NOT_SUPPORTED = 46;
    public static final int STATUS_CLB_DATA_TOO_BIG = 67;
    public static final int STATUS_CLB_NOT_ENABLED = 66;
    public static final int STATUS_COMMAND_DISALLOWED = 12;
    public static final int STATUS_CONNECTION_EXISTS = 11;
    public static final int STATUS_CONNECTION_TOUT = 8;
    public static final int STATUS_CONN_CAUSE_LOCAL_HOST = 22;
    public static final int STATUS_CONN_FAILED_ESTABLISHMENT = 62;
    public static final int STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE = 61;
    public static final int STATUS_CONTROLLER_BUSY = 58;
    public static final int STATUS_DIFF_TRANSACTION_COLLISION = 42;
    public static final int STATUS_ENCRY_MODE_NOT_ACCEPTABLE = 37;
    public static final int STATUS_HOST_BUSY_PAIRING = 56;
    public static final int STATUS_HOST_REJECT_DEVICE = 15;
    public static final int STATUS_HOST_REJECT_RESOURCES = 13;
    public static final int STATUS_HOST_REJECT_SECURITY = 14;
    public static final int STATUS_HOST_TIMEOUT = 16;
    public static final int STATUS_HW_FAILURE = 3;
    public static final int STATUS_ILLEGAL_COMMAND = 1;
    public static final int STATUS_ILLEGAL_PARAMETER_FMT = 18;
    public static final int STATUS_INQ_RSP_DATA_TOO_LARGE = 54;
    public static final int STATUS_INSTANT_PASSED = 40;
    public static final int STATUS_INSUFFCIENT_SECURITY = 47;
    public static final int STATUS_INVALID_LMP_PARAM = 30;
    public static final int STATUS_KEY_MISSING = 6;
    public static final int STATUS_LMP_PDU_NOT_ALLOWED = 36;
    public static final int STATUS_LMP_RESPONSE_TIMEOUT = 34;
    public static final int STATUS_LMP_STATUS_TRANS_COLLISION = 35;
    public static final int STATUS_LT_ADDR_ALREADY_IN_USE = 64;
    public static final int STATUS_LT_ADDR_NOT_ALLOCATED = 65;
    public static final int STATUS_MAC_CONNECTION_FAILED = 63;
    public static final int STATUS_MAX_NUM_OF_CONNECTIONS = 9;
    public static final int STATUS_MAX_NUM_OF_SCOS = 10;
    public static final int STATUS_MEMORY_FULL = 7;
    public static final int STATUS_NO_CONNECTION = 2;
    public static final int STATUS_OPERATION_CANCELED_BY_HOST = 68;
    public static final int STATUS_PAGE_TIMEOUT = 4;
    public static final int STATUS_PAIRING_NOT_ALLOWED = 24;
    public static final int STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED = 41;
    public static final int STATUS_PARAM_OUT_OF_RANGE = 48;
    public static final int STATUS_PEER_LOW_RESOURCES = 20;
    public static final int STATUS_PEER_POWER_OFF = 21;
    public static final int STATUS_PEER_USER = 19;
    public static final int STATUS_QOS_NOT_SUPPORTED = 39;
    public static final int STATUS_QOS_REJECTED = 45;
    public static final int STATUS_QOS_UNACCEPTABLE_PARAM = 44;
    public static final int STATUS_REJ_NO_SUITABLE_CHANNEL = 57;
    public static final int STATUS_REPEATED_ATTEMPTS = 23;
    public static final int STATUS_RESERVED_SLOT_VIOLATION = 52;
    public static final int STATUS_ROLE_CHANGE_NOT_ALLOWED = 33;
    public static final int STATUS_ROLE_SWITCH_FAILED = 53;
    public static final int STATUS_ROLE_SWITCH_PENDING = 50;
    public static final int STATUS_SCO_AIR_MODE = 29;
    public static final int STATUS_SCO_INTERVAL_REJECTED = 28;
    public static final int STATUS_SCO_OFFSET_REJECTED = 27;
    public static final int STATUS_SIMPLE_PAIRING_NOT_SUPPORTED = 55;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_UNACCEPT_CONN_INTERVAL = 59;
    public static final int STATUS_UNDEFINED_0X2_B = 43;
    public static final int STATUS_UNDEFINED_0X31 = 49;
    public static final int STATUS_UNDEFINED_0X33 = 51;
    public static final int STATUS_UNIT_KEY_USED = 38;
    public static final int STATUS_UNKNOWN = 4095;
    public static final int STATUS_UNKNOWN_LMP_PDU = 25;
    public static final int STATUS_UNSPECIFIED = 31;
    public static final int STATUS_UNSUPPORTED_LMP_FEATURE = 32;
    public static final int STATUS_UNSUPPORTED_REM_FEATURE = 26;
    public static final int STATUS_UNSUPPORTED_VALUE = 17;
}

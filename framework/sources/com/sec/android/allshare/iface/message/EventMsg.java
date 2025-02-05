package com.sec.android.allshare.iface.message;

/* loaded from: classes6.dex */
public class EventMsg {
    public static final int AIRCONDITIONER = 4;
    public static final int ALLCONTROL_CHANGED_PROFILE = 1;
    public static final int ALLCONTROL_DATA_DOWNLOAD = 4;
    public static final int ALLCONTROL_DATA_DOWNLOAD_COMPLETE = 5;
    public static final int ALLCONTROL_FINISH_ACTIVITY = 0;
    public static final int ALLCONTROL_LAYOUT_CHANGE = 3;
    public static final int ALLCONTROL_PROFILE_DOWNLOAD = 2;
    public static final int CINTERNAL_UI_CONTROLLER_CONFIGURE = 601;
    public static final int CONTROLLER_EVENT_BUTTON_CLICK = 3;
    public static final int CONTROLLER_EVENT_DEVICE_AUTHENTICATION = 52;
    public static final int CONTROLLER_EVENT_DEVICE_CONTROLLER_EXIT = 51;
    public static final int CONTROLLER_EVENT_DEVICE_CONTROLLER_START = 50;
    public static final int CONTROLLER_EVENT_DEVICE_LISTVIEW_DATA = 75;
    public static final int CONTROLLER_EVENT_DEVICE_LISTVIEW_DATA_END = 80;
    public static final int CONTROLLER_EVENT_DEVICE_REQUEST_LISTVIEW = 70;
    public static final int CONTROLLER_EVENT_FIRST_ENTER_TOP_PROCESS = 21;
    public static final int CONTROLLER_EVENT_GESTURE = 5;
    public static final int CONTROLLER_EVENT_GESTURE_MODE_RAW = 11;
    public static final int CONTROLLER_EVENT_GESTURE_MODE_TV = 12;
    public static final int CONTROLLER_EVENT_GET_FILE_INFO = 71;
    public static final int CONTROLLER_EVENT_KEYBOARD_INPUT = 1;
    public static final int CONTROLLER_EVENT_KEYBOARD_INPUT_CONTINUE = 13;
    public static final int CONTROLLER_EVENT_KEYBOARD_INPUT_END = 8;
    public static final int CONTROLLER_EVENT_MOUSE = 2;
    public static final int CONTROLLER_EVENT_MOUSE_CREATE = 15;
    public static final int CONTROLLER_EVENT_MOUSE_DESTROY = 16;
    public static final int CONTROLLER_EVENT_MOUSE_RESET = 4;
    public static final int CONTROLLER_EVENT_MOUSE_SCROLL = 10;
    public static final int CONTROLLER_EVENT_NONE = 0;
    public static final int CONTROLLER_EVENT_PROGRAM_CHANGE = 7;
    public static final int CONTROLLER_EVENT_REMOTECONTROL_KEY = 14;
    public static final int CONTROLLER_EVENT_SEMANTIC = 6;
    public static final int CONTROLLER_EVENT_SET_GESTURE_MODE = 9;
    public static final int CONTROLLER_EVENT_TOP_PROCESS_CHANGE = 20;
    public static final int DINTERACT_DTV_APP_LAYOUT_CHANGE = 2;
    public static final int DINTERACT_DTV_APP_START = 0;
    public static final int DINTERACT_DTV_APP_STOP = 1;
    public static final int DINTERNAL_CHANGED_PROFILE = 306;
    public static final int DINTERNAL_CHANGE_PROCESS_NOTIFY = 302;
    public static final int DINTERNAL_DEVICE_APP_FINISH = 303;
    public static final int DINTERNAL_DEVICE_LAYOUT_CHANGE = 304;
    public static final int DINTERNAL_DEVICE_LIST_CLEAN = 309;
    public static final int DINTERNAL_GET_SEARCH_DEVICES = 301;
    public static final int DINTERNAL_GET_UPNP_DEVICE_LIST = 308;
    public static final int DINTERNAL_OCUPIED_BY_OTHER_ONE = 305;
    public static final int DINTERNAL_STATUS_DELIVERY_FROM_IAPP = 307;
    public static final int DISENABLE_PROFILE = -1;
    public static final int DOWNLOAD_START = 6;
    public static final int END_CONNECT = 5;
    public static final int FILE_NOT_EXIST = -7;
    public static final int FLAG_DEVICE_CHANGED_CURRENT_PROGRAM = 2;
    public static final int FLAG_DEVICE_FIRST_ENTER_CURRENT_PROGRAM = 1;
    public static final int FLAG_DEVICE_TOP_PROGRAM_NONE = 0;
    public static final int HAVE_LOCAL = -4;
    public static final int IAPP_ACCELEROMETER = 6;
    public static final int IAPP_AES128_KEY = 121;
    public static final int IAPP_AUTHENTICATION = 100;
    public static final int IAPP_AUTHENTICATION_FAILED = 0;
    public static final int IAPP_AUTHENTICATION_SUCCESS = 1;
    public static final int IAPP_AUTHENTICATION_TIMEOUT = 101;
    public static final int IAPP_BLUETOOTH_ADDRESS = 110;
    public static final int IAPP_BROWSER_START = 401;
    public static final int IAPP_BROWSER_STOP = 402;
    public static final int IAPP_CONNECTION_OFF = 2;
    public static final int IAPP_DELETED_FROM_TV = 0;
    public static final int IAPP_DENIED_FROM_TV = 1;
    public static final int IAPP_EXIT = 300;
    public static final int IAPP_FULL_CONNECTION = 2;
    public static final int IAPP_KEYBOARD_CHAR = 2;
    public static final int IAPP_KEYBOARD_STRING = 1;
    public static final int IAPP_KEYBOARD_SYNC = 3;
    public static final int IAPP_KEY_INPUT_END = 4;
    public static final int IAPP_MOUSE_BUTTON_LBUTTON = 1;
    public static final int IAPP_MOUSE_BUTTON_MBUTTON = 2;
    public static final int IAPP_MOUSE_BUTTON_NONE = 0;
    public static final int IAPP_MOUSE_BUTTON_RBUTTON = 3;
    public static final int IAPP_MOUSE_BUTTON_SLBUTTON = 4;
    public static final int IAPP_MOUSE_BUTTON_SRBUTTON = 5;
    public static final int IAPP_MOUSE_BUTTON_WHEEL_BACKWARD = 7;
    public static final int IAPP_MOUSE_BUTTON_WHEEL_FORWARD = 6;
    public static final int IAPP_MOUSE_CREATE = 15;
    public static final int IAPP_MOUSE_DESTROY = 16;
    public static final int IAPP_MOUSE_EVENT_MOVE = 13;
    public static final int IAPP_MOUSE_EVENT_PRESS = 11;
    public static final int IAPP_MOUSE_EVENT_RELEASE = 12;
    public static final int IAPP_MOUSE_EVENT_WHEEL = 27;
    public static final int IAPP_MOUSE_PROCESS = 17;
    public static final int IAPP_MOUSE_SET_DOUBLECLICK = 19;
    public static final int IAPP_MOUSE_SET_POINT_MODE = 20;
    public static final int IAPP_MOUSE_SET_SENSITIVITY = 18;
    public static final int IAPP_PUBLIC_KEY = 120;
    public static final int IAPP_REMOCON = 0;
    public static final int IAPP_REMOTE_INPUT_TYPE = 10;
    public static final int IAPP_STATUS = 200;
    public static final int IAPP_TOUCH_GESTURE = 5;
    public static final int IAPP_TOUCH_GESTURE_2012 = 7;
    public static final int IAPP_TOUCH_GESTURE_SEMANTIC = 8;
    public static final int IAPP_TOUCH_GESTURE_TOUCHMODE = 11;
    public static final int IAPP_TVCONNECTION_FAILED = 9999;
    public static final int IAPP_TVSTATUS_APP = 1;
    public static final int IAPP_TVSTATUS_DTV = 0;
    public static final int MENU_DELETE_PROFILE = 0;
    public static final int NOT_INSTALL = -6;
    public static final int OTHERS = 50;
    public static final int PINTERACT_DEVICE_INFO_ANS = 1;
    public static final int PINTERACT_DEVICE_INFO_REQ = 0;
    public static final int PINTERACT_GET_CONNECTION_OK = 8;
    public static final int PINTERACT_OCUPIED_BY_OTHER_ONE = 7;
    public static final int PINTERACT_SERVICE_INFO_ANS = 3;
    public static final int PINTERACT_SERVICE_INFO_REQ = 2;
    public static final int PINTERACT_START_PROCESS_MONITORING = 5;
    public static final int PINTERACT_STOP_PROCESS_MONITORING = 6;
    public static final int PINTERACT_TOP_PROCESS_NOTIFY = 4;
    public static final int PINTERNAL_CALL_USER_NOTI = 411;
    public static final int PINTERNAL_DELETE_COMPLETE = 412;
    public static final int PINTERNAL_DOWNLOAD_START = 404;
    public static final int PINTERNAL_END_CONNECT = 405;
    public static final int PINTERNAL_FILE_NOT_EXIST = 407;
    public static final int PINTERNAL_HAVE_LOCAL = 409;
    public static final int PINTERNAL_NOT_INSTALL = 406;
    public static final int PINTERNAL_PROFILE_NOT_EXIST = 408;
    public static final int PINTERNAL_SEND_INFO_OK = 401;
    public static final int PINTERNAL_SEND_PROFILE_OK = 402;
    public static final int PINTERNAL_SOCKET_AVAILABLE = 413;
    public static final int PINTERNAL_SOCKET_CLOSED = 410;
    public static final int PINTERNAL_UNZIP_PROFILE = 403;
    public static final int PRINTER = 2;
    public static final int PROFILE_NOT_EXIST = -2;
    public static final int PROTOCOL_NONE = 0;
    public static final int PROTOCOL_TCP = 1;
    public static final int PROTOCOL_UDP = 2;
    public static final int RECEIVER_MSG_CHANGE_LAYOUT = 504;
    public static final int RECEIVER_MSG_CHANGE_PROFILE = 501;
    public static final int RECEIVER_MSG_DATA_DOWNLOAD = 505;
    public static final int RECEIVER_MSG_DATA_DOWNLOAD_COMPLETE = 506;
    public static final int RECEIVER_MSG_FINISH_ACTIVITY = 503;
    public static final int RECEIVER_MSG_PROFILE_DOWNLOAD = 502;
    public static final int REMOTECONTROLRECEIVER = 1;
    public static final int REMOTEPC = 3;
    public static final int REMOTE_4DIRECTION_TYPE = 2;
    public static final int REMOTE_COLOR_TYPE = 24;
    public static final int REMOTE_DATASERVICE_TYPE = 10;
    public static final int REMOTE_DEFAULT_TYPE = 1;
    public static final int REMOTE_FULLBROWSER2_TYPE = 14;
    public static final int REMOTE_FULLBROWSER3_TYPE = 15;
    public static final int REMOTE_FULLBROWSER4_TYPE = 16;
    public static final int REMOTE_FULLBROWSER5_TYPE = 17;
    public static final int REMOTE_FULLBROWSER6_TYPE = 18;
    public static final int REMOTE_FULLBROWSER7_TYPE = 19;
    public static final int REMOTE_FULLBROWSER8_TYPE = 20;
    public static final int REMOTE_FULLBROWSER_TYPE = 8;
    public static final int REMOTE_HDMICEC_TYPE = 21;
    public static final int REMOTE_INTERNET_4DIRECTION_TYPE = 23;
    public static final int REMOTE_INTERNET_TYPE = 9;
    public static final int REMOTE_IR_TYPE = 25;
    public static final int REMOTE_MHEG_TYPE = 26;
    public static final int REMOTE_MHP_TYPE = 13;
    public static final int REMOTE_NONE_TYPE = 0;
    public static final int REMOTE_NUMERIC_NONE_ENTER_TYPE = 27;
    public static final int REMOTE_NUMERIC_TYPE = 3;
    public static final int REMOTE_PLAYBACK_TYPE = 4;
    public static final int REMOTE_PSIZE_TYPE = 7;
    public static final int REMOTE_ROOMEQ_TYPE = 11;
    public static final int REMOTE_TTX_TYPE = 12;
    public static final int REMOTE_USBDLNA_TYPE = 22;
    public static final int REMOTE_YAHOO_EU_TYPE = 6;
    public static final int REMOTE_YAHOO_TYPE = 5;
    public static final int REQUEST_PROFILE = 0;
    public static final int REQUEST_PROFILE_OK = 50;
    public static final int SAVE_EXTERNAL = 1;
    public static final int SAVE_INTERNAL = 0;
    public static final int SEND_END = 4;
    public static final int SEND_INFO = 1;
    public static final int SEND_INFO_OK = 51;
    public static final int SEND_PROFILE = 2;
    public static final int SEND_PROFILE_OK = 52;
    public static final int SEND_STOP = -3;
    public static final int SEND_SUCCESS = 3;
    public static final int SOCKET_CLOSED = -5;
    public static final int UEVENT_ALLSHARE_DEVICE_ADDDEVICE = 7320;
    public static final int UEVENT_ALLSHARE_DEVICE_REFREASH = 7321;
    public static final int UEVENT_BINDING_UNISEF_RECEIVED = 7201;
    public static final int UEVENT_BIND_CIM = 7303;
    public static final int UEVENT_CALL_USER_NOTI = 7401;
    public static final int UEVENT_CHANGED_PROFILE = 7202;
    public static final int UEVENT_CONTROLMANAGER_BINDING_DONE = 7307;
    public static final int UEVENT_DATA_DOWNLOAD_COMPLETE = 7209;
    public static final int UEVENT_DATA_DOWNLOAD_START = 7208;
    public static final int UEVENT_DELETE_COMPLETE = 7001;
    public static final int UEVENT_DEVCIE_CONNTECTION_STATE = 7207;
    public static final int UEVENT_DEVICELINK_ADDDEVICE = 7301;
    public static final int UEVENT_DEVICELINK_BINDING_DONE = 7306;
    public static final int UEVENT_DEVICELINK_DESTROY = 7302;
    public static final int UEVENT_DEVICE_APP_FINISH = 7203;
    public static final int UEVENT_DEVICE_CONNECT = 7017;
    public static final int UEVENT_DEVICE_LAYOUT_CHANGE = 7204;
    public static final int UEVENT_DISENABLE_PROFILE = 7013;
    public static final int UEVENT_DISMISS_USER_NOTI = 7402;
    public static final int UEVENT_DOWNLOADED_CHANGED_PROFILE = 7011;
    public static final int UEVENT_END_CONNECT = 7006;
    public static final int UEVENT_FILE_NOT_EXIST = 7008;
    public static final int UEVENT_FIRST_RUNNING_PROFILE = 7206;
    public static final int UEVENT_HAVE_CHANGED_PROFILE = 7012;
    public static final int UEVENT_HAVE_LOCAL = 7010;
    public static final int UEVENT_INIT_END = 7103;
    public static final int UEVENT_LIST_CLEAN = 7305;
    public static final int UEVENT_NET_RES_ALL_ACTIVITY_KILL = 7601;
    public static final int UEVENT_NET_RES_NETWORK_CHANGED = 7604;
    public static final int UEVENT_NET_RES_START_ALLCNTMANAGER = 7603;
    public static final int UEVENT_NET_RES_START_WARNING = 7602;
    public static final int UEVENT_NET_STATE_CHANGED = 7503;
    public static final int UEVENT_NET_STATE_CONNECTED = 7502;
    public static final int UEVENT_NET_STATE_CONNECTED_INIT_TIME = 7504;
    public static final int UEVENT_NET_STATE_DISCONNECTED = 7501;
    public static final int UEVENT_NET_STATE_DISCONNECTED_INIT_TIME = 7505;
    public static final int UEVENT_NET_STATE_INIT = 7506;
    public static final int UEVENT_NOT_INSTALL = 7007;
    public static final int UEVENT_OCUPIED_BY_ANOTHER_ONE = 7205;
    public static final int UEVENT_PROFILE_DOWNLOAD_START = 7005;
    public static final int UEVENT_PROFILE_NOT_EXIST = 7009;
    public static final int UEVENT_REFRESH_LIST = 7100;
    public static final int UEVENT_SEARCH_DEVICE = 7102;
    public static final int UEVENT_SEND_INFO_OK = 7003;
    public static final int UEVENT_SEND_PROFILE_OK = 7004;
    public static final int UEVENT_SOCKET_AVAILABLE = 7002;
    public static final int UEVENT_SOCKET_CLOSED = 7014;
    public static final int UEVENT_START_CIPLAYER = 7015;
    public static final int UEVENT_START_CONTROLACTIVITY = 7016;
    public static final int UEVENT_TOAST_MESSAGE = 7104;
    public static final int UEVENT_UNBIND_CIM = 7304;
    public static final int UEVENT_UNZIP_PROFILE = 7000;
    public static final int UEVENT_WIFI_CONNECTED = 7101;
    public static final int UEVNET_ALLSHARE_DEVICE_SEARCH_DONE = 7322;
}

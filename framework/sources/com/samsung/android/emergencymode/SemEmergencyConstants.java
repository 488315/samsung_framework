package com.samsung.android.emergencymode;

import android.net.Uri;

/* loaded from: classes6.dex */
public class SemEmergencyConstants {
    public static final String ACTION = "action";
    public static final int ALARM_ALLOW = 4;
    public static final String ALLOWFLAG = "allowflag";
    public static final int ALL_ALLOW = 7;
    public static final String AUTHORITY = "com.samsung.android.max.power.saving";

    @Deprecated
    public static final int BATTERY_CONSERVING_MODE = 2048;
    public static final int BATTERY_CONSERVING_MODE_LAUNCHER = 2;
    public static final int BLIND = 2;
    public static final String CMD_UPDATE_TABLE = "updatetable";
    public static final int DATABASE_VERSION = 1;
    public static final int DEAF = 4;

    @Deprecated
    public static final int DEFAULT_ALLOW = 1;
    public static final int DETECT = 128;
    public static final int DISASTER = 256;

    @Deprecated
    public static final String EMERGENCY_CALL = "emcall";
    public static final String EMERGENCY_CHECK_ABNORMAL_STATE = "com.samsung.intent.action.EMERGENCY_CHECK_ABNORMAL_STATE";
    public static final String EMERGENCY_FINISHED_SENDING_PACKAGE_CHANGED = "com.samsung.intent.action.EMERGENCY_FINISHED_SENDING_PACKAGE_CHANGED";
    public static final String EMERGENCY_LAUNCHER = "com.sec.android.emergencylauncher";
    public static final String EMERGENCY_LAUNCHER_CLASS = "com.sec.android.emergencylauncher.LauncherActivity";
    public static final String EMERGENCY_MANAGER_SERVICE = "com.sec.android.emergency_manager_service";

    @Deprecated
    public static final int EMERGENCY_MODE = 16;
    public static final int EMERGENCY_MODE_LAUNCHER = 0;
    public static final String EMERGENCY_SERVICE_PACKAGE = "com.sec.android.emergencymode.service";
    public static final String EMERGENCY_SERVICE_STARTER = "com.sec.android.emergencymode.service.EmergencyServiceStarter";
    public static final String EMERGENCY_START_EMERGENCY_CHOICE_POPUP = "com.samsung.intent.action.EMERGENCY_START_CHOICE_POPUP";

    @Deprecated
    public static final String EMERGENCY_START_SERVICE_BY_ORDER = "com.samsung.intent.action.EMERGENCY_START_SERVICE_BY_ORDER";
    public static final String EMERGENCY_START_SERVICE_BY_ORDER_OLD = "android.intent.action.EMERGENCY_START_SERVICE_BY_ORDER";

    @Deprecated
    public static final String EMERGENCY_STATE_CHANGED = "com.samsung.intent.action.EMERGENCY_STATE_CHANGED";

    @Deprecated
    public static final int ERROR_USER_CANCELED = -8;
    public static final int ERR_CONFIGURING = -10;
    public static final int ERR_GENERAL_FAILURE = -1;
    public static final int ERR_INVALID_STATE = -4;
    public static final int ERR_INVALID_TYPE = -3;
    public static final int ERR_MODIFYING = -2;
    public static final int ERR_NOT_FOUND_SERVICE = -5;
    public static final int ERR_NULL_CONTEXT = -7;
    public static final int ERR_ON_VTCALL = -11;
    public static final int ERR_PERMISSION_DENIED = -6;
    public static final int ERR_UNKNOWN_FAIL = -9;
    public static final String EXTRA_CLEAR_BOOT_TIME = "clearBootTime";
    public static final String EXTRA_EMERGENCY_START_SERVICE_ENABLE = "enabled";
    public static final String EXTRA_EMERGENCY_START_SERVICE_FLAG = "flag";
    public static final String EXTRA_EMERGENCY_START_SERVICE_SKIPDIALOG = "skipdialog";
    public static final String EXTRA_INIT_FOR_EM_STATE = "initForEMState";

    @Deprecated
    public static final int FIND_MY_MOBILE = 64;
    public static final int FIND_MY_MOBILE_ALL = 1088;
    public static final String GPS_ACCURACY = "GPS_ACCURACY";

    @Deprecated
    public static final String GPS_LATITUDE = "GPS_LATITUDE";

    @Deprecated
    public static final String GPS_LONGITUDE = "GPS_LONGITUDE";
    public static final String HEATSENSING = "heatsensing";
    public static final String MCC = "mcc";
    public static final int MINIMAL_BATTERY_USE_LAUNCHER = 3;

    @Deprecated
    public static final int MODE_DISABLED = 5;

    @Deprecated
    public static final int MODE_DISABLING = 4;

    @Deprecated
    public static final int MODE_ENABLED = 3;

    @Deprecated
    public static final int MODE_ENABLING = 2;
    public static final int NORMAL = 1;
    public static final String PKG = "pkg";
    public static final String PREF = "pref";
    public static final String PROVIDER = "com.sec.android.provider.emergencymode";
    public static final int RECOVERY = 15;
    public static final String SERVICE_NAME = "emergency_service";
    public static final int SUCCESS = 1;
    public static final int SVOICE = 32;
    public static final String TABLE_ALARM = "alarm";
    public static final String TABLE_DISABLED_PKG = "blocklist";
    public static final String TABLE_ECCLIST = "ecclist";
    public static final String TABLE_LAUNCHER_ADD = "addlist";
    public static final String TABLE_LAUNCHER_DEFAULT = "defaultallow";
    public static final String TABLE_PREF_SETTINGS = "prefsettings";
    public static final String TABLE_WHITE_LIST = "userallow";

    @Deprecated
    public static final int ULTRA_POWER_SAVING_MODE = 512;
    public static final int ULTRA_POWER_SAVING_MODE_ALL = 1536;

    @Deprecated
    public static final int ULTRA_POWER_SAVING_MODE_FIND_MY_MOBILE = 1024;
    public static final int ULTRA_POWER_SAVING_MODE_LAUNCHER = 1;
    public static final int USER_ALLOW = 2;
    public static final String VALUE = "value";
    public static final int[] RGBCMYArray = {19635, 19635, 19635, 38505, 38505, 38505, 7650, 7650, 7650};
    public static final Uri URI_WHITELIST = Uri.parse("content://com.samsung.android.max.power.saving/userallow");
    public static final Uri URI_PREFSETTINGS = Uri.parse("content://com.samsung.android.max.power.saving/prefsettings");
    public static final Uri URI_DISABLEDPKG = Uri.parse("content://com.samsung.android.max.power.saving/blocklist");
    public static final Uri URI_ALARM = Uri.parse("content://com.samsung.android.max.power.saving/alarm");
    public static final Uri URI_ECCLIST = Uri.parse("content://com.samsung.android.max.power.saving/ecclist");
    public static final Uri URI_UPDATE_TABLE = Uri.parse("content://com.samsung.android.max.power.saving/updatetable");

    private SemEmergencyConstants() {
    }
}

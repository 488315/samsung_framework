package android.app.backup;

import android.annotation.SystemApi;
import android.os.Bundle;

@SystemApi
/* loaded from: classes.dex */
public class BackupManagerMonitor {
    public static final String EXTRA_LOG_AGENT_LOGGING_RESULTS = "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS";
    public static final String EXTRA_LOG_CANCEL_ALL = "android.app.backup.extra.LOG_CANCEL_ALL";
    public static final String EXTRA_LOG_EVENT_CATEGORY = "android.app.backup.extra.LOG_EVENT_CATEGORY";
    public static final String EXTRA_LOG_EVENT_ID = "android.app.backup.extra.LOG_EVENT_ID";
    public static final String EXTRA_LOG_EVENT_PACKAGE_LONG_VERSION = "android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION";
    public static final String EXTRA_LOG_EVENT_PACKAGE_NAME = "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME";

    @Deprecated
    public static final String EXTRA_LOG_EVENT_PACKAGE_VERSION = "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION";
    public static final String EXTRA_LOG_EXCEPTION_FULL_BACKUP = "android.app.backup.extra.LOG_EXCEPTION_FULL_BACKUP";
    public static final String EXTRA_LOG_ILLEGAL_KEY = "android.app.backup.extra.LOG_ILLEGAL_KEY";
    public static final String EXTRA_LOG_MANIFEST_PACKAGE_NAME = "android.app.backup.extra.LOG_MANIFEST_PACKAGE_NAME";
    public static final String EXTRA_LOG_OLD_VERSION = "android.app.backup.extra.LOG_OLD_VERSION";
    public static final String EXTRA_LOG_OPERATION_TYPE = "android.app.backup.extra.OPERATION_TYPE";
    public static final String EXTRA_LOG_POLICY_ALLOW_APKS = "android.app.backup.extra.LOG_POLICY_ALLOW_APKS";
    public static final String EXTRA_LOG_PREFLIGHT_ERROR = "android.app.backup.extra.LOG_PREFLIGHT_ERROR";
    public static final String EXTRA_LOG_RESTORE_ANYWAY = "android.app.backup.extra.LOG_RESTORE_ANYWAY";
    public static final String EXTRA_LOG_RESTORE_VERSION = "android.app.backup.extra.LOG_RESTORE_VERSION";
    public static final String EXTRA_LOG_V_TO_U_ALLOWLIST = "android.app.backup.extra.V_TO_U_ALLOWLIST";
    public static final String EXTRA_LOG_V_TO_U_DENYLIST = "android.app.backup.extra.V_TO_U_DENYLIST";
    public static final String EXTRA_LOG_WIDGET_PACKAGE_NAME = "android.app.backup.extra.LOG_WIDGET_PACKAGE_NAME";
    public static final int LOG_EVENT_CATEGORY_AGENT = 2;
    public static final int LOG_EVENT_CATEGORY_BACKUP_MANAGER_POLICY = 3;
    public static final int LOG_EVENT_CATEGORY_TRANSPORT = 1;
    public static final int LOG_EVENT_ID_AGENT_CRASHED_BEFORE_RESTORE_DATA_IS_SENT = 78;
    public static final int LOG_EVENT_ID_AGENT_FAILURE = 69;
    public static final int LOG_EVENT_ID_AGENT_FAILURE_DURING_RESTORE = 80;
    public static final int LOG_EVENT_ID_AGENT_LOGGING_RESULTS = 52;
    public static final int LOG_EVENT_ID_APK_NOT_INSTALLED = 40;
    public static final int LOG_EVENT_ID_APP_HAS_NO_AGENT = 28;
    public static final int LOG_EVENT_ID_BACKUP_DISABLED = 13;
    public static final int LOG_EVENT_ID_CANNOT_GET_NEXT_PKG_NAME = 56;
    public static final int LOG_EVENT_ID_CANNOT_RESTORE_WITHOUT_APK = 41;
    public static final int LOG_EVENT_ID_CANT_FIND_AGENT = 30;
    public static final int LOG_EVENT_ID_CORRUPT_MANIFEST = 46;
    public static final int LOG_EVENT_ID_DEVICE_NOT_PROVISIONED = 14;
    public static final int LOG_EVENT_ID_ERROR_PREFLIGHT = 16;
    public static final int LOG_EVENT_ID_EXCEPTION_FULL_BACKUP = 19;
    public static final int LOG_EVENT_ID_EXPECTED_DIFFERENT_PACKAGE = 43;
    public static final int LOG_EVENT_ID_FAILED_TO_READ_DATA_FROM_TRANSPORT = 81;
    public static final int LOG_EVENT_ID_FAILED_TO_SEND_DATA_TO_AGENT_DURING_RESTORE = 79;
    public static final int LOG_EVENT_ID_FULL_AGENT_ERROR = 65;
    public static final int LOG_EVENT_ID_FULL_BACKUP_CANCEL = 4;
    public static final int LOG_EVENT_ID_FULL_RESTORE = 59;
    public static final int LOG_EVENT_ID_FULL_RESTORE_ALLOW_BACKUP_FALSE = 39;
    public static final int LOG_EVENT_ID_FULL_RESTORE_SIGNATURE_MISMATCH = 37;
    public static final int LOG_EVENT_ID_FULL_RESTORE_TIMEOUT = 45;
    public static final int LOG_EVENT_ID_ILLEGAL_KEY = 5;
    public static final int LOG_EVENT_ID_KEY_VALUE_BACKUP_CANCEL = 21;
    public static final int LOG_EVENT_ID_KEY_VALUE_RESTORE_TIMEOUT = 31;
    public static final int LOG_EVENT_ID_KV_AGENT_ERROR = 61;
    public static final int LOG_EVENT_ID_KV_RESTORE = 58;
    public static final int LOG_EVENT_ID_LOST_TRANSPORT = 25;
    public static final int LOG_EVENT_ID_MISSING_SIGNATURE = 42;
    public static final int LOG_EVENT_ID_NO_DATA_TO_SEND = 7;
    public static final int LOG_EVENT_ID_NO_FEEDER_THREAD = 64;
    public static final int LOG_EVENT_ID_NO_NEXT_RESTORE_TARGET = 60;
    public static final int LOG_EVENT_ID_NO_PACKAGES = 49;
    public static final int LOG_EVENT_ID_NO_PM_METADATA_RECEIVED = 23;
    public static final int LOG_EVENT_ID_NO_RESTORE_METADATA_AVAILABLE = 22;
    public static final int LOG_EVENT_ID_PACKAGE_ACCEPTED_FOR_RESTORE = 75;
    public static final int LOG_EVENT_ID_PACKAGE_INELIGIBLE = 9;
    public static final int LOG_EVENT_ID_PACKAGE_KEY_VALUE_PARTICIPANT = 10;
    public static final int LOG_EVENT_ID_PACKAGE_NOT_FOUND = 12;
    public static final int LOG_EVENT_ID_PACKAGE_NOT_PRESENT = 26;
    public static final int LOG_EVENT_ID_PACKAGE_RESTORE_FINISHED = 62;
    public static final int LOG_EVENT_ID_PACKAGE_STOPPED = 11;
    public static final int LOG_EVENT_ID_PACKAGE_TRANSPORT_NOT_PRESENT = 15;
    public static final int LOG_EVENT_ID_PM_AGENT_HAS_NO_METADATA = 24;
    public static final int LOG_EVENT_ID_QUOTA_HIT_PREFLIGHT = 18;
    public static final int LOG_EVENT_ID_RESTORE_ANY_VERSION = 34;
    public static final int LOG_EVENT_ID_RESTORE_AT_INSTALL_INVOKED = 73;
    public static final int LOG_EVENT_ID_RESTORE_COMPLETE = 68;
    public static final int LOG_EVENT_ID_RESTORE_DATA_DOES_NOT_BELONG_TO_PACKAGE = 76;
    public static final int LOG_EVENT_ID_RESTORE_VERSION_HIGHER = 27;
    public static final int LOG_EVENT_ID_SIGNATURE_MISMATCH = 29;
    public static final int LOG_EVENT_ID_SKIP_RESTORE_AT_INSTALL = 74;
    public static final int LOG_EVENT_ID_START_PACKAGE_RESTORE = 67;
    public static final int LOG_EVENT_ID_START_RESTORE_AT_INSTALL = 54;
    public static final int LOG_EVENT_ID_START_SYSTEM_RESTORE = 53;
    public static final int LOG_EVENT_ID_SYSTEM_APP_NO_AGENT = 38;
    public static final int LOG_EVENT_ID_TRANSPORT_ERROR_DURING_START_RESTORE = 55;
    public static final int LOG_EVENT_ID_TRANSPORT_ERROR_FULL_RESTORE = 66;
    public static final int LOG_EVENT_ID_TRANSPORT_ERROR_KV_RESTORE = 63;
    public static final int LOG_EVENT_ID_TRANSPORT_IS_NULL = 50;
    public static final int LOG_EVENT_ID_TRANSPORT_NON_INCREMENTAL_BACKUP_REQUIRED = 51;
    public static final int LOG_EVENT_ID_UNABLE_TO_CREATE_AGENT_FOR_RESTORE = 77;
    public static final int LOG_EVENT_ID_UNKNOWN_RESTORE_TYPE = 57;
    public static final int LOG_EVENT_ID_UNKNOWN_VERSION = 44;
    public static final int LOG_EVENT_ID_VERSIONS_MATCH = 35;
    public static final int LOG_EVENT_ID_VERSION_OF_BACKUP_OLDER = 36;
    public static final int LOG_EVENT_ID_V_TO_U_RESTORE_PKG_ELIGIBLE = 70;
    public static final int LOG_EVENT_ID_V_TO_U_RESTORE_PKG_NOT_ELIGIBLE = 71;
    public static final int LOG_EVENT_ID_V_TO_U_RESTORE_SET_LIST = 72;
    public static final int LOG_EVENT_ID_WIDGET_METADATA_MISMATCH = 47;
    public static final int LOG_EVENT_ID_WIDGET_UNKNOWN_VERSION = 48;

    public void onEvent(Bundle event) {
    }
}

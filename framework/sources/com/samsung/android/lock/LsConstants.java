package com.samsung.android.lock;

import android.os.Build;

/* loaded from: classes6.dex */
public interface LsConstants {
    public static final int ATTEMPTS_BEFORE_AUTO_WIPE = 20;
    public static final String BACKUP_LSKF_BASED_PROTECTOR_ID_KEY = "backup-protector-id";
    public static final String BACKUP_LSKF_EXPIRATION_TIME_KEY = "backup-expiration-ts";
    public static final String BACKUP_LSKF_LAST_CHANGED_TIME_KEY = "backup-protector-ts";
    public static final String BIOMETRIC_ATTEMPT_DEADLINE = "lockscreen.lockout_biometric_attempt_deadline";
    public static final String BIOMETRIC_ATTEMPT_TIMEOUT_MS = "lockscreen.lockout_biometric_attempt_timeoutms";
    public static final String BIOMETRIC_LOCKSCREEN_KEY = "lockscreen.samsung_biometric";
    public static final int BIOMETRIC_STATE_OFF = 0;
    public static final int BIOMETRIC_STATE_ON = 1;
    public static final int BIOMETRIC_TYPE_ALL = 257;
    public static final int BIOMETRIC_TYPE_FACE = 256;
    public static final int BIOMETRIC_TYPE_FINGERPRINT = 1;
    public static final int BIOMETRIC_TYPE_NONE = 0;
    public static final String FMM_FAIELD_ATTEMPT_KEY = "lockscreen.failed_fmm_attempts";
    public static final int FMM_LOCK = 0;
    public static final int KNOX_GUARD = 3;
    public static final String LOCKOUT_ATTEMPT_DEADLINE = "lockscreen.lockoutattemptdeadline";
    public static final String LOCKOUT_ATTEMPT_TIMEOUT_MS = "lockscreen.lockoutattempttimeoutmss";
    public static final String LOCKSCREEN_FOLDER_INSTANTLY_LOCKS = "lockscreen.folder_instantly_locks";
    public static final String LOCKSETTINGS_DB_BACKUP = "locksettings_db_backup";
    public static final String LOCKSETTINGS_DB_RESTORE = "locksettings_db_restore";
    public static final String LOCKSETTINGS_SPBLOB_BACKUP = "locksettings_spblob_backup";
    public static final String LOCKSETTINGS_SPBLOB_RESTORE = "locksettings_spblob_restore";
    public static final String LOCK_SETTINGS_LOG_VER = "locksettings.log.ver";
    public static final byte LOCK_SETTINGS_LOG_VERSION_LATEST = 1;
    public static final byte LOCK_SETTINGS_LOG_VERSION_V1 = 1;
    public static final int MAX_PREV_CREDENTIAL_ATTEMPTS = 3;
    public static final String NON_STRONG_BIO_IDLE_TIMEOUT = "lockscreen.non_strong_bio_idle_timeout";
    public static final String NON_STRONG_BIO_TIMEOUT = "lockscreen.non_strong_bio_timeout";
    public static final String PASSWORD_HINT_KEY = "lockscreen.password_hint";
    public static final String PREV_ATTEMPTS_COUNT = "prev.attempts.count";
    public static final long PREV_CREDENTIAL_TIMEOUT_MS = 259200000;
    public static final int PROPERTY_ALL = 3;
    public static final int PROPERTY_DUMP = 1;
    public static final int PROPERTY_F_ACCUMULATE = 256;
    public static final int PROPERTY_F_BYTE = 512;
    public static final int PROPERTY_F_REFRESH = 512;
    public static final int PROPERTY_F_STRING = 256;
    public static final int PROPERTY_NONE = 0;
    public static final int PROPERTY_UPLOAD = 2;
    public static final int REMOTELOCK_SIZE = 4;
    public static final int REMOTELOCK_SYSTEMUI = 4;
    public static final int REMOTELOCK_SYSTEMUI_DESKTOP = 5;
    public static final int RESPONSE_FAILED = 1;
    public static final int RESPONSE_INCORRECT_KEY = 2;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_THROTTLE = 3;
    public static final int RMM_LOCK = 2;
    public static final int SECURE_STATE_BIO = 16;
    public static final int SECURE_STATE_BIO_LOCKOUT = 64;
    public static final int SECURE_STATE_CARRIER = 8;
    public static final int SECURE_STATE_CLEAR_LOCK = 1;
    public static final int SECURE_STATE_CREDENTIAL_TYPE = 2;
    public static final int SECURE_STATE_DEVICE_OWNERINFO = 256;
    public static final int SECURE_STATE_FMM = 4;
    public static final int SECURE_STATE_LOCKOUT = 32;
    public static final int SECURE_STATE_LOCK_DISABLED = 512;
    public static final int SECURE_STATE_OWNERINFO = 128;
    public static final int SECURE_STATE_UPDATE_ALL = 4094;
    public static final int SECURITY_DEBUG_DEV = 1;
    public static final int SECURITY_DEBUG_LOW = 0;
    public static final int SECURITY_DEBUG_MID = 1;
    public static final int SECURITY_DEBUG_ON_COUNT;
    public static final String SECURITY_LOG_PWDAT_VER = "lockscreen.pwdata.ver";
    public static final int SKT_CARRIER_LOCK = 1;
    public static final String SKT_CARRIER_LOCK_MODE_FILE = "/efs/sms/sktcarrierlockmode";
    public static final String SKT_LOCKOUT_ATTEMPT_DEADLINE = "sktlockscreen.lockoutdeadline";
    public static final long SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT = 600000;
    public static final String STRONG_BIO_TIMEOUT = "lockscreen.strong_bio_timeout";
    public static final int USER_PREV = -9899;
    public static final boolean DEBUG = LsUtil.isDevBuild();
    public static final String SECURITY_LOG_VERSION = Build.VERSION.INCREMENTAL;

    static {
        SECURITY_DEBUG_ON_COUNT = DEBUG ? 10 : 17;
    }
}

package android.os;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class BugreportParams {
    public static final int BUGREPORT_FLAG_DEFER_CONSENT = 2;
    public static final int BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA = 1;
    public static final int BUGREPORT_MODE_APP_ANR = 15;
    public static final int BUGREPORT_MODE_APP_ERROR = 13;
    public static final int BUGREPORT_MODE_APP_NATIVE = 14;
    public static final int BUGREPORT_MODE_BOOT_DELAY = 7;
    public static final int BUGREPORT_MODE_BOOT_ENOSPC = 8;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_SHUTDOWN_BROADCAST = 16;
    public static final int BUGREPORT_MODE_SHUTDOWN_DELAY = 17;
    public static final int BUGREPORT_MODE_SVCAGENT = 20;
    public static final int BUGREPORT_MODE_SYS_ERROR = 10;
    public static final int BUGREPORT_MODE_SYS_NATIVE = 11;
    public static final int BUGREPORT_MODE_SYS_RESCUE = 9;
    public static final int BUGREPORT_MODE_SYS_WATCHDOG = 12;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;
    private final int mFlags;
    private final int mMode;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface BugreportFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface BugreportMode {
    }

    public BugreportParams(int mode) {
        this.mMode = mode;
        this.mFlags = 0;
    }

    public BugreportParams(int mode, int flags) {
        this.mMode = mode;
        this.mFlags = flags;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getFlags() {
        return this.mFlags;
    }
}

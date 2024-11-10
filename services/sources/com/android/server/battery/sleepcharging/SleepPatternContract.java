package com.android.server.battery.sleepcharging;

import android.net.Uri;
import android.provider.BaseColumns;

/* loaded from: classes.dex */
public abstract class SleepPatternContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.rubin.persona.sleeppattern");

    /* loaded from: classes.dex */
    public abstract class SleepPatternInfo implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(SleepPatternContract.AUTHORITY_URI, "sleep_pattern_info");
    }
}

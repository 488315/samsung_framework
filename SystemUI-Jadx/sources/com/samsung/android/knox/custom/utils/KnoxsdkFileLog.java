package com.samsung.android.knox.custom.utils;

import android.util.Log;
import com.samsung.android.util.SemLog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class KnoxsdkFileLog {
    public static final int LOG_FILE_MAX_COUNT = 2;
    public static final int LOG_FILE_SIZE_LIMIT = 500000;
    public static String TAG = "knoxsdk/filelog";
    public static Logger sLogger;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class KnoxsdkFileLogHolder {
        public static final KnoxsdkFileLog INSTANCE = new KnoxsdkFileLog(0);

        private KnoxsdkFileLogHolder() {
        }
    }

    public /* synthetic */ KnoxsdkFileLog(int i) {
        this();
    }

    public static void d(String str, String str2) {
        fileLog(str, str2);
        SemLog.d(str, str2);
    }

    public static void e(String str, String str2) {
        fileLog(str, str2);
        SemLog.e(str, str2);
    }

    public static void fileLog(String str, String str2) {
        KnoxsdkFileLog knoxsdkFileLog = KnoxsdkFileLogHolder.INSTANCE;
        if (sLogger != null) {
            sLogger.log(Level.INFO, String.format(" %s : %s\n", str, str2));
        }
    }

    public static KnoxsdkFileLog getInstance() {
        return KnoxsdkFileLogHolder.INSTANCE;
    }

    public static void i(String str, String str2) {
        fileLog(str, str2);
        SemLog.i(str, str2);
    }

    public static void init() {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
            FileHandler fileHandler = new FileHandler("/data/log/knoxsdk.log", LOG_FILE_SIZE_LIMIT, 2, true);
            fileHandler.setFormatter(new Formatter() { // from class: com.samsung.android.knox.custom.utils.KnoxsdkFileLog.1
                @Override // java.util.logging.Formatter
                public final String format(LogRecord logRecord) {
                    Date date = new Date();
                    date.setTime(System.currentTimeMillis());
                    StringBuilder sb = new StringBuilder(80);
                    sb.append(simpleDateFormat.format(date));
                    sb.append(logRecord.getMessage());
                    return sb.toString();
                }
            });
            Logger logger = Logger.getLogger(KnoxsdkFileLog.class.getName());
            sLogger = logger;
            logger.addHandler(fileHandler);
            sLogger.setLevel(Level.ALL);
            sLogger.setUseParentHandlers(false);
            Log.d(TAG, "init success");
        } catch (IOException e) {
            Log.d(TAG, "init failure : " + e.getMessage());
        }
    }

    public static void v(String str, String str2) {
        fileLog(str, str2);
        SemLog.v(str, str2);
    }

    public static void w(String str, String str2) {
        fileLog(str, str2);
        SemLog.w(str, str2);
    }

    private KnoxsdkFileLog() {
        init();
    }

    public static void d(String str, String str2, Throwable th) {
        fileLog(str, str2, th);
        SemLog.d(str, str2, th);
    }

    public static void fileLog(String str, String str2, Throwable th) {
        KnoxsdkFileLog knoxsdkFileLog = KnoxsdkFileLogHolder.INSTANCE;
        if (sLogger != null) {
            sLogger.log(Level.INFO, String.format(" %s : %s\n", str, str2), th);
        }
    }
}

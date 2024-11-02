package com.android.systemui.pluginlock.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DumpUtils {
    public static final String SEPARATOR = System.getProperty("line.separator");
    public final Context mContext;
    public final HandlerExecutor mHandlerExecutor = new HandlerExecutor();
    public final DumpUtils$$ExternalSyntheticLambda0 mWriteRunnable = new Runnable() { // from class: com.android.systemui.pluginlock.utils.DumpUtils$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            boolean z;
            DumpUtils dumpUtils = DumpUtils.this;
            dumpUtils.getClass();
            Log.d("DumpUtils", "writeDump");
            File file = new File("/data/user_de/0/com.android.systemui/files/pluginlock/");
            if (!file.exists()) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("writeDump, created: ", file.mkdirs(), "DumpUtils");
            }
            String str = dumpUtils.mDumpString;
            dumpUtils.mDumpString = "";
            File file2 = new File("/data/user_de/0/com.android.systemui/files/pluginlock/dump.txt");
            if (file2.length() < 102400) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                str = DumpUtils.getDump() + str;
            }
            try {
                FileWriter fileWriter = new FileWriter(file2, z);
                try {
                    fileWriter.append((CharSequence) str);
                    fileWriter.flush();
                    fileWriter.close();
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public String mDumpString = "";

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class HandlerExecutor {
        public final Handler mHandler;

        public HandlerExecutor() {
            HandlerThread handlerThread = new HandlerThread("DumpUtilsThread");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.pluginlock.utils.DumpUtils$$ExternalSyntheticLambda0] */
    public DumpUtils(Context context) {
        this.mContext = context;
    }

    public static String buildDumpString(String str) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date) + " | " + str.replace("\n", "");
    }

    public static String getDump() {
        int i;
        int length;
        String str = SEPARATOR;
        StringBuilder sb = new StringBuilder();
        try {
            String dumpFromFile = getDumpFromFile();
            int i2 = 0;
            if (str != null && dumpFromFile.split(str).length - 1 > 200) {
                i = length - 200;
            } else {
                i = 0;
            }
            Scanner scanner = new Scanner(dumpFromFile);
            int i3 = 0;
            while (scanner.hasNextLine() && i2 < 200) {
                String nextLine = scanner.nextLine();
                int i4 = i3 + 1;
                if (i3 >= i) {
                    sb.append(nextLine);
                    sb.append(str);
                    i2++;
                }
                i3 = i4;
            }
            scanner.close();
            return sb.toString();
        } catch (Throwable th) {
            sb.append(str);
            sb.append(th.toString());
            return sb.toString();
        }
    }

    public static String getDumpFromFile() {
        try {
            File file = new File("/data/user_de/0/com.android.systemui/files/pluginlock/dump.txt");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine);
                        sb.append(SEPARATOR);
                    } else {
                        bufferedReader.close();
                        fileInputStream.close();
                        return sb.toString();
                    }
                }
            } else {
                return "";
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public final void addEvent(String str) {
        HandlerExecutor handlerExecutor = this.mHandlerExecutor;
        if (str != null && !str.isEmpty()) {
            DumpUtils$$ExternalSyntheticLambda0 dumpUtils$$ExternalSyntheticLambda0 = this.mWriteRunnable;
            try {
                handlerExecutor.mHandler.removeCallbacks(dumpUtils$$ExternalSyntheticLambda0);
                this.mDumpString += buildDumpString(str) + SEPARATOR;
                if (!handlerExecutor.mHandler.postDelayed(dumpUtils$$ExternalSyntheticLambda0, 2000L)) {
                    Log.w("DumpUtils", "execute failed");
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}

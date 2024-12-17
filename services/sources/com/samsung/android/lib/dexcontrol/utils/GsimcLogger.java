package com.samsung.android.lib.dexcontrol.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class GsimcLogger {
    public static final boolean SURVEY_LOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public static boolean sIsDmaSupported;
    public static boolean sIsDmaVersionChecked;

    public static void insertLog(Context context, String str) {
        if (SURVEY_LOG) {
            if (!sIsDmaVersionChecked) {
                sIsDmaSupported = isDmaSupported(context);
            }
            if (sIsDmaSupported) {
                sendDmaLog(context, str, null);
            } else {
                sendGSIMLog(context, str, null);
            }
        }
    }

    public static void insertLog(Context context, String str, String str2) {
        if (SURVEY_LOG) {
            if (!sIsDmaVersionChecked) {
                sIsDmaSupported = isDmaSupported(context);
            }
            if (sIsDmaSupported) {
                sendDmaLog(context, str, str2);
            } else {
                sendGSIMLog(context, str, str2);
            }
        }
    }

    public static boolean isDmaSupported(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
            sIsDmaVersionChecked = true;
            if (packageInfo != null) {
                return packageInfo.versionCode >= 540000000;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void sendDmaLog(Context context, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", "4F9-399-4810210");
        bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
        bundle.putString("pkg_name", "com.samsung.android.lib.dexcontrol");
        if (str2 != null) {
            bundle.putString("extra", str2);
        }
        Intent intent = new Intent().setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY").putExtras(bundle).setPackage("com.sec.android.diagmonagent");
        context.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(-2));
        SLog.d("GsimcLogger", "sendDmaLog intent : " + intent.getExtras());
    }

    public static void sendGSIMLog(Context context, String str, String str2) {
        int i;
        if (context == null) {
            return;
        }
        try {
            i = context.getPackageManager().getPackageInfo("com.samsung.android.providers.context", 128).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            SLog.e("GsimcLogger", "Could not find ContextProvider");
            i = -1;
        }
        if (i < 2) {
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", "com.samsung.android.app.dexcontrol");
            contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
            if (str2 != null) {
                contentValues.put("extra", str2);
            }
            Intent intent = new Intent("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
            intent.setPackage("com.samsung.android.providers.context");
            Bundle bundle = new Bundle();
            bundle.putString("pkg_name", "com.samsung.android.lib.dexcontrol");
            bundle.putParcelable("data", contentValues);
            intent.putExtras(bundle);
            context.sendBroadcast(intent);
            SLog.d("GsimcLogger", "Logger intent : " + intent.getExtras());
        } catch (Exception e) {
            SLog.e("GsimcLogger", "Error while using insertLog");
            SLog.e("GsimcLogger", e.toString());
        }
    }
}

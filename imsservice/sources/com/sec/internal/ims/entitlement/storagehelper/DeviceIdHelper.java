package com.sec.internal.ims.entitlement.storagehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.helper.os.TelephonyManagerWrapper;
import com.sec.internal.log.IMSLog;
import java.util.UUID;

/* loaded from: classes.dex */
public class DeviceIdHelper {
    private static final int INTERVAL_BETWEEN_RETRY = 500;
    private static final String LOG_TAG = "DeviceIdHelper";
    private static final int RETRY_COUNT = 5;

    public static String getDeviceIdIfExists(Context context, int i) {
        SharedPreferences sharedPref = NSDSSharedPrefHelper.getSharedPref(context, NSDSNamespaces.NSDSSharedPref.NAME_SHARED_PREF, 0);
        if (sharedPref == null) {
            return null;
        }
        return sharedPref.getString(i + ":device_id", null);
    }

    public static void makeDeviceId(Context context, int i) {
        String generateDeviceId = generateDeviceId(context, i);
        IMSLog.s(LOG_TAG, i, "makeDeviceId: " + generateDeviceId);
        saveDeviceId(context, i, generateDeviceId);
    }

    public static String getDeviceId(Context context, int i) {
        SharedPreferences sharedPref = NSDSSharedPrefHelper.getSharedPref(context, NSDSNamespaces.NSDSSharedPref.NAME_SHARED_PREF, 0);
        String str = null;
        if (sharedPref != null) {
            str = sharedPref.getString(i + ":device_id", null);
        }
        if (str == null) {
            IMSLog.e(LOG_TAG, i, "getDeviceId is null");
            str = generateDeviceId(context, i);
            saveDeviceId(context, i, str);
        }
        if (str != null) {
            IMSLog.s(LOG_TAG, i, "getDeviceId: " + str);
        }
        return str;
    }

    public static String getEncodedDeviceId(String str) {
        return Base64.encodeToString(str.getBytes(), 2);
    }

    private static void saveDeviceId(Context context, int i, String str) {
        SharedPreferences sharedPref = NSDSSharedPrefHelper.getSharedPref(context, NSDSNamespaces.NSDSSharedPref.NAME_SHARED_PREF, 0);
        if (sharedPref == null) {
            IMSLog.e(LOG_TAG, i, "saveDeviceId: save is failed");
            return;
        }
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(i + ":device_id", str);
        edit.commit();
    }

    private static String generateDeviceId(Context context, int i) {
        String queryDeviceIdFromTelephonyManager = queryDeviceIdFromTelephonyManager(context, i);
        for (int i2 = 0; i2 < 5; i2++) {
            queryDeviceIdFromTelephonyManager = queryDeviceIdFromTelephonyManager(context, i);
            if (!TextUtils.isEmpty(queryDeviceIdFromTelephonyManager)) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                IMSLog.s(LOG_TAG, "generateDeviceId wait interrrupted:" + e.getMessage());
            }
        }
        return TextUtils.isEmpty(queryDeviceIdFromTelephonyManager) ? String.format("urn:uuid:%s", UUID.randomUUID().toString()) : queryDeviceIdFromTelephonyManager;
    }

    private static String queryDeviceIdFromTelephonyManager(Context context, int i) {
        String format;
        ITelephonyManager telephonyManagerWrapper = TelephonyManagerWrapper.getInstance(context);
        try {
            String imei = telephonyManagerWrapper.getImei(i);
            String meid = telephonyManagerWrapper.getMeid(i);
            if (!TextUtils.isEmpty(imei)) {
                format = String.format("urn:gsma:imei:%s-%s-%s", imei.substring(0, 8), imei.substring(8, 14), "0");
            } else {
                if (TextUtils.isEmpty(meid)) {
                    return "";
                }
                format = String.format("urn:device-id:meid:%s-%s-%s", meid.substring(0, 8), meid.substring(8, 14), "0");
            }
            return format;
        } catch (Exception e) {
            IMSLog.i(LOG_TAG, "getting deviceId failed:" + e.getMessage());
            return "";
        }
    }
}

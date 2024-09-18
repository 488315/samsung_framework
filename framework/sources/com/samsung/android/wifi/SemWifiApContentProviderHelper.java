package com.samsung.android.wifi;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemWifiApContentProviderHelper {
    public static final String KEY_AUTO_HOTSPOT_CONNECTED_USER = "auto_hotspot_connected_user";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    private static String TAG = "SemWifiApContentProviderHelper";
    static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
    static final Uri CONTENT_URI = Uri.parse(URL);

    public static void insert(Context mContext, String key, String val) {
        boolean hasPermission = mContext.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) == 0;
        if (!hasPermission) {
            return;
        }
        long ident = Binder.clearCallingIdentity();
        ContentValues values = new ContentValues();
        if (val == null) {
            val = "";
        }
        values.put("name", key);
        values.put("value", val);
        try {
            try {
            } catch (SQLiteException e) {
                Log.e(TAG, "SemWifiApContentProviderHelper insert:" + e);
            }
            if (isKeypresent(mContext, key)) {
                String[] selectionArgs = {key};
                mContext.getContentResolver().update(CONTENT_URI, values, "name = ?", selectionArgs);
            } else {
                mContext.getContentResolver().insert(CONTENT_URI, values);
                Log.i(TAG, "Inserting Key:" + key);
            }
        } finally {
            Binder.restoreCallingIdentity(ident);
        }
    }

    public static String get(Context mContext, String key) {
        boolean hasPermission = mContext.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) == 0;
        if (!hasPermission) {
            return "";
        }
        String returnValue = "";
        String[] selectionArgs = {key};
        long ident = Binder.clearCallingIdentity();
        try {
            try {
                Cursor c = mContext.getContentResolver().query(CONTENT_URI, null, "name = ?", selectionArgs, null);
                if (c != null) {
                    try {
                        if (c.moveToFirst()) {
                            returnValue = c.getString(c.getColumnIndex("value"));
                        }
                        c.close();
                    } catch (Throwable th) {
                        c.close();
                        throw th;
                    }
                }
            } catch (SQLiteException e) {
                Log.e(TAG, "SemWifiApContentProviderHelper get:" + e);
            }
            return returnValue;
        } finally {
            Binder.restoreCallingIdentity(ident);
        }
    }

    private static boolean isKeypresent(Context mContext, String key) {
        String[] selectionArgs = {key};
        Cursor c = mContext.getContentResolver().query(CONTENT_URI, null, "name = ?", selectionArgs, null);
        if (c == null) {
            return false;
        }
        try {
            boolean ret = c.moveToFirst();
            return ret;
        } finally {
            c.close();
        }
    }
}

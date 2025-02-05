package com.sec.internal.ims.cmstore.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.interfaces.ims.cmstore.ITelephonyDBColumns;
import com.sec.internal.log.IMSLog;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class TelephonyDbHelper {
    public static final String MSGAPP_CHATCONTENT_URI = "content://im/chat";
    public static final String MSGAPP_FTCONTENT_URI = "content://im/ft/";
    public static final String MSGAPP_IM_THREADS_CONTENT_URI = "content://mms-sms/im-threads/";
    public static final String TAG = "TelephonyDbHelper";
    private ContentResolver mResolver;

    public TelephonyDbHelper(Context context) {
        this.mResolver = null;
        this.mResolver = context.getContentResolver();
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Log.i(TAG, "query uri=" + IMSLog.checker(uri.toString()) + " whereClaus: " + str + " sortOrder: " + str2);
        try {
            if (ITelephonyDBColumns.CONTENT_SMS.equals(uri)) {
                return this.mResolver.query(uri, strArr, str, strArr2, str2);
            }
            if (ITelephonyDBColumns.SPAM_SMS_CONTENT_URI.equals(uri)) {
                return this.mResolver.query(ITelephonyDBColumns.SPAM_MMSSMS_CONTENT_URI, null, makeWhereForSpam(str, "sms"), strArr2, str2);
            }
            if (ITelephonyDBColumns.CONTENT_MMS.equals(uri)) {
                return this.mResolver.query(uri, strArr, str, strArr2, null);
            }
            if (ITelephonyDBColumns.SPAM_MMS_CONTENT_URI.equals(uri)) {
                return this.mResolver.query(ITelephonyDBColumns.SPAM_MMSSMS_CONTENT_URI, strArr, makeWhereForSpam(str, "mms"), strArr2, str2);
            }
            return this.mResolver.query(uri, strArr, str, strArr2, str2);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when query: ", e);
            return null;
        }
    }

    private String makeWhereForSpam(String str, String str2) {
        if (str == null || str.length() == 0) {
            return "transport_type='" + str2 + "'";
        }
        return str + " and (" + ITelephonyDBColumns.TYPE_DISCRIMINATOR_COLUMN + "= '" + str2 + "')";
    }

    public InputStream getInputStream(Uri uri) throws FileNotFoundException {
        try {
            return this.mResolver.openInputStream(uri);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when getinput stream: ", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long getFtRowFromTelephony(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "content://im/ft/"
            android.net.Uri r2 = android.net.Uri.parse(r0)
            java.lang.String r0 = "_id"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            java.lang.String r4 = "imdn_message_id = ?"
            java.lang.String[] r5 = new java.lang.String[]{r8}
            r6 = 0
            r1 = r7
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)
            if (r7 == 0) goto L33
            boolean r8 = r7.moveToFirst()     // Catch: java.lang.Throwable -> L29
            if (r8 == 0) goto L33
            int r8 = r7.getColumnIndexOrThrow(r0)     // Catch: java.lang.Throwable -> L29
            long r0 = r7.getLong(r8)     // Catch: java.lang.Throwable -> L29
            goto L35
        L29:
            r8 = move-exception
            r7.close()     // Catch: java.lang.Throwable -> L2e
            goto L32
        L2e:
            r7 = move-exception
            r8.addSuppressed(r7)
        L32:
            throw r8
        L33:
            r0 = -1
        L35:
            if (r7 == 0) goto L3a
            r7.close()
        L3a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.cmstore.helper.TelephonyDbHelper.getFtRowFromTelephony(java.lang.String):long");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getFtFileDataFromTelephony(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = com.sec.internal.ims.cmstore.helper.TelephonyDbHelper.TAG
            java.lang.String r1 = "getFtFileDataFromTelephony"
            com.sec.internal.log.IMSLog.i(r0, r1)
            java.lang.String r1 = "content://im/ft/"
            android.net.Uri r3 = android.net.Uri.parse(r1)
            java.lang.String[] r4 = new java.lang.String[]{r10}
            java.lang.String r5 = "imdn_message_id = ?"
            java.lang.String[] r6 = new java.lang.String[]{r9}
            r7 = 0
            r2 = r8
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)
            if (r8 == 0) goto L38
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Throwable -> L2e
            if (r9 == 0) goto L38
            int r9 = r8.getColumnIndexOrThrow(r10)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Throwable -> L2e
            goto L3a
        L2e:
            r9 = move-exception
            r8.close()     // Catch: java.lang.Throwable -> L33
            goto L37
        L33:
            r8 = move-exception
            r9.addSuppressed(r8)
        L37:
            throw r9
        L38:
            java.lang.String r9 = ""
        L3a:
            if (r8 == 0) goto L3f
            r8.close()
        L3f:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "getFtFileDataFromTelephony field:"
            r8.append(r1)
            r8.append(r10)
            java.lang.String r10 = " path:"
            r8.append(r10)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.sec.internal.log.IMSLog.i(r0, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.cmstore.helper.TelephonyDbHelper.getFtFileDataFromTelephony(java.lang.String, java.lang.String):java.lang.String");
    }

    public Cursor queryAllFtRCSFromTelephony(String str, String str2) {
        Uri parse = Uri.parse(MSGAPP_FTCONTENT_URI);
        Log.i(TAG, "queryAllFtRCSFromTelephony");
        return query(parse, null, "chat_session_id = ? AND sim_imsi=?", new String[]{str, str2}, null);
    }

    public Cursor queryAllSessionsFromTelephony(String str) {
        Uri parse = Uri.parse(MSGAPP_IM_THREADS_CONTENT_URI);
        Log.i(TAG, "queryAllSessionsFromTelephony");
        return query(parse, null, "(sim_imsi = " + str + " OR " + CloudMessageProviderContract.BufferDBSMS.SIM_IMSI2 + " = " + str + ")", null, null);
    }

    public Cursor queryParticipantsUsingChatIdFromTP(String str) {
        Uri parse = Uri.parse(MSGAPP_IM_THREADS_CONTENT_URI);
        Log.i(TAG, "queryParticipantsUsingChatIdFromTP");
        return query(parse, new String[]{CloudMessageProviderContract.BufferDBSMS.RECIPIENT_ID}, "(session_id = '" + str + "' OR " + CloudMessageProviderContract.BufferDBSMS.SESSION_ID2 + " = '" + str + "')", null, null);
    }

    public Cursor queryParticipantsInfoFromTP(String str) {
        Uri parse = Uri.parse("content://mms-sms/canonical-addresses/");
        Log.i(TAG, "queryParticipantsInfoFromTP");
        return query(parse, new String[]{"address"}, "_id = ?", new String[]{str}, null);
    }

    public Cursor queryAllRCSChatFromTP(String str, String str2) {
        Uri parse = Uri.parse(MSGAPP_CHATCONTENT_URI);
        Log.i(TAG, "queryAllRCSChatFromTP");
        return query(parse, null, "session_id=? AND sim_imsi=?", new String[]{str, str2}, null);
    }
}

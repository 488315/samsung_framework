package com.sec.internal.ims.servicemodules.euc.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.sec.internal.constants.tapi.UserConsentProviderContract;
import com.sec.internal.helper.Preconditions;
import com.sec.internal.helper.header.AuthenticationHeaders;
import com.sec.internal.ims.servicemodules.euc.data.EucState;
import com.sec.internal.ims.servicemodules.euc.data.EucType;
import com.sec.internal.ims.servicemodules.euc.locale.DeviceLocale;
import com.sec.internal.log.IMSLog;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class UserConsentPersistence implements IUserConsentPersistence {
    private static final String LOG_TAG = "UserConsentPersistence";
    private final EucSQLiteHelper mEucSQLiteHelper;

    public UserConsentPersistence(Context context) {
        this.mEucSQLiteHelper = EucSQLiteHelper.getInstance((Context) Preconditions.checkNotNull(context));
    }

    @Override // com.sec.internal.ims.servicemodules.euc.persistence.IUserConsentPersistence
    public Cursor getEucList(String str, List<EucType> list, String str2, String str3) throws IllegalArgumentException {
        Log.d(LOG_TAG, "getEucList, thread=" + Thread.currentThread().getName() + ", lang=" + str);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("types list is empty");
        }
        try {
            SQLiteDatabase writableDatabase = this.mEucSQLiteHelper.getWritableDatabase();
            StringBuilder sb = new StringBuilder("SELECT EUCRDATA.ROWID, EUCRDATA.ID, EUCRDATA.TIMESTAMP, EUCRDATA.STATE, EUCRDATA.TYPE, DIALOG.SUBJECT, DIALOG.TEXT, DIALOG.ACCEPT_BUTTON, DIALOG.REJECT_BUTTON, EUCRDATA.REMOTE_URI, EUCRDATA.SUBSCRIBER_IDENTITY FROM EUCRDATA, DIALOG WHERE ((EXISTS (SELECT * FROM DIALOG d WHERE EUCRDATA.ID = d.ID AND d.LANGUAGE = ?) AND LANGUAGE = ?)OR (not EXISTS (SELECT * FROM DIALOG d WHERE EUCRDATA.ID = d.ID AND d.LANGUAGE = ?) AND LANGUAGE = '" + DeviceLocale.DEFAULT_LANG_VALUE + "')) AND EUCRDATA." + UserConsentProviderContract.UserConsentList.ID + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + "DIALOG." + UserConsentProviderContract.UserConsentList.ID + " AND EUCRDATA.TYPE" + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + "DIALOG.TYPE AND EUCRDATA." + UserConsentProviderContract.UserConsentList.SUBSCRIBER_IDENTITY + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + "DIALOG." + UserConsentProviderContract.UserConsentList.SUBSCRIBER_IDENTITY + " ");
            Iterator<EucType> it = list.iterator();
            if (it.hasNext()) {
                sb.append("AND (");
                sb.append("EUCRDATA");
                sb.append(".");
                sb.append("TYPE");
                sb.append(AuthenticationHeaders.HEADER_PRARAM_SPERATOR);
                sb.append(it.next().getId());
                while (it.hasNext()) {
                    sb.append(" OR ");
                    sb.append("EUCRDATA");
                    sb.append(".");
                    sb.append("TYPE");
                    sb.append(AuthenticationHeaders.HEADER_PRARAM_SPERATOR);
                    sb.append(it.next().getId());
                }
                sb.append(") ");
            }
            sb.append("AND (");
            sb.append("EUCRDATA");
            sb.append(".");
            sb.append(UserConsentProviderContract.UserConsentList.STATE);
            sb.append(AuthenticationHeaders.HEADER_PRARAM_SPERATOR);
            sb.append(EucState.ACCEPTED.getId());
            sb.append(" OR ");
            sb.append("EUCRDATA");
            sb.append(".");
            sb.append(UserConsentProviderContract.UserConsentList.STATE);
            sb.append(AuthenticationHeaders.HEADER_PRARAM_SPERATOR);
            sb.append(EucState.REJECTED.getId());
            sb.append(") AND ");
            sb.append("EUCRDATA");
            sb.append(".");
            sb.append(UserConsentProviderContract.UserConsentList.SUBSCRIBER_IDENTITY);
            sb.append("= '");
            sb.append(str3);
            sb.append("' AND (");
            sb.append("DIALOG");
            sb.append(".");
            sb.append("SUBJECT");
            sb.append(" != '' OR ");
            sb.append("DIALOG");
            sb.append(".");
            sb.append("TEXT");
            sb.append(" != '') ");
            sb.append("ORDER BY ");
            sb.append(str2);
            String[] strArr = {str, str, str};
            String sb2 = sb.toString();
            IMSLog.s(LOG_TAG, "query: " + sb2);
            try {
                return writableDatabase.rawQueryWithFactory(this.mEucSQLiteHelper.getCursorFactory(), sb2, strArr, null);
            } catch (SQLException e) {
                logSqlException(e);
                return null;
            }
        } catch (SQLiteException e2) {
            logSqlException(e2);
            return null;
        }
    }

    @Override // com.sec.internal.ims.servicemodules.euc.persistence.IUserConsentPersistence
    public int removeEuc(String str, String[] strArr) throws IllegalArgumentException {
        String str2 = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("removeEuc, whereClause: ");
        sb.append(str);
        sb.append(", number of whereArgs: ");
        int i = 0;
        sb.append(strArr == null ? 0 : strArr.length);
        Log.d(str2, sb.toString());
        IMSLog.s(str2, "removeEuc, whereClause: " + str + " whereArgs: " + Arrays.toString(strArr));
        try {
            SQLiteDatabase writableDatabase = this.mEucSQLiteHelper.getWritableDatabase();
            try {
                try {
                    i = writableDatabase.delete("EUCRDATA", str, strArr);
                } catch (SQLException e) {
                    logSqlException(e);
                }
                return i;
            } finally {
                this.mEucSQLiteHelper.close();
                writableDatabase.close();
            }
        } catch (SQLiteException e2) {
            logSqlException(e2);
            return 0;
        }
    }

    private void logSqlException(Exception exc) {
        Log.e(LOG_TAG, "SQL Exception " + exc);
    }
}

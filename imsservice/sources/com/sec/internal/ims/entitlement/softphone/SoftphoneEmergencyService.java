package com.sec.internal.ims.entitlement.softphone;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.sec.internal.constants.ims.entitilement.SoftphoneContract;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SoftphoneEmergencyService {
    private static final int E911AID_REVERSE_INDEX = 2;
    private static final String LOG_TAG = "SoftphoneEmergencyService";
    private final Context mContext;

    public SoftphoneEmergencyService(Context context) {
        this.mContext = context;
    }

    private static ContentValues getContentValues(String[] strArr, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_id", str);
        contentValues.put("name", strArr[0]);
        contentValues.put(SoftphoneContract.AddressColumns.HOUSE_NUMBER, strArr[1]);
        contentValues.put(SoftphoneContract.AddressColumns.HOUSE_NUMBER_EXTENSION, strArr[2]);
        contentValues.put(SoftphoneContract.AddressColumns.STREET_DIRECTION_PREFIX, strArr[3]);
        contentValues.put(SoftphoneContract.AddressColumns.STREET_NAME, strArr[4]);
        contentValues.put(SoftphoneContract.AddressColumns.STREET_NAME_SUFFIX, strArr[5]);
        contentValues.put(SoftphoneContract.AddressColumns.STREET_DIRECTION_SUFFIX, strArr[6]);
        contentValues.put(SoftphoneContract.AddressColumns.CITY, strArr[7]);
        contentValues.put("state", strArr[8]);
        contentValues.put(SoftphoneContract.AddressColumns.ZIP, strArr[9]);
        contentValues.put(SoftphoneContract.AddressColumns.ADDITIONAL_ADDRESS_INFO, strArr[10]);
        contentValues.put(SoftphoneContract.AddressColumns.E911AID, strArr[11]);
        contentValues.put(SoftphoneContract.AddressColumns.EXPIRE_DATE, strArr[12]);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 11; i++) {
            String str2 = strArr[i];
            sb.append((str2 == null || str2.isEmpty() || strArr[i].equalsIgnoreCase("null")) ? "" : strArr[i]);
            sb.append(";");
        }
        contentValues.put(SoftphoneContract.AddressColumns.FORMATTED_ADDRESS, sb.toString());
        return contentValues;
    }

    public void compareAndSaveE911Address(List<String> list, String str) {
        Log.i(LOG_TAG, "networkLocations size: " + list.size());
        Uri buildAddressUri = SoftphoneContract.SoftphoneAddress.buildAddressUri(str);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String[] split = it.next().split(";");
            if (split.length >= 13) {
                String str2 = split[split.length - 2];
                Log.i(LOG_TAG, "networkLocation: " + split[0] + " " + str2);
                ContentValues contentValues = getContentValues(split, str);
                if (this.mContext.getContentResolver().update(buildAddressUri, contentValues, "E911AID=?", new String[]{str2}) == 0) {
                    this.mContext.getContentResolver().insert(buildAddressUri, contentValues);
                }
            }
        }
    }
}

package com.sec.internal.ims.servicemodules.tapi.service.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import com.gsma.services.rcs.CommonServiceConfiguration;
import com.sec.internal.constants.ims.cmstore.McsConstants;
import com.sec.internal.ims.servicemodules.tapi.service.api.TapiServiceManager;
import com.sec.internal.ims.util.RcsSettingsUtils;

/* loaded from: classes.dex */
public class RcsServiceControlReceiver extends BroadcastReceiver {
    private static final String[] PROJECTION = {"value"};
    private static final String WHERE_CLAUSE = McsConstants.BundleData.KEY + "=?";
    private final String LOG_TAG = RcsServiceControlReceiver.class.getSimpleName();
    private String mServiceActivated = null;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle resultExtras;
        Log.d(this.LOG_TAG, "RcsServiceControlReceiver.onReceive() intent: " + intent);
        if ("com.gsma.services.rcs.action.GET_ACTIVATION_MODE_CHANGEABLE".equals(intent.getAction())) {
            Bundle resultExtras2 = getResultExtras(true);
            if (resultExtras2 == null) {
                return;
            }
            resultExtras2.putBoolean("get_activation_mode_changeable", Boolean.parseBoolean(getStringValueSetting(context, "ModeChangeable")));
            setResultExtras(resultExtras2);
            return;
        }
        if ("com.gsma.services.rcs.action.GET_ACTIVATION_MODE".equals(intent.getAction())) {
            Bundle resultExtras3 = getResultExtras(true);
            if (resultExtras3 == null) {
                return;
            }
            if (RcsSettingsUtils.getInstance() != null) {
                RcsSettingsUtils.getInstance().updateTapiSettings();
            }
            if (this.mServiceActivated == null) {
                Log.d(this.LOG_TAG, "mServiceActivated is null");
                this.mServiceActivated = getStringValueSetting(context, "ServiceActivated");
            }
            boolean parseBoolean = Boolean.parseBoolean(this.mServiceActivated);
            Log.d(this.LOG_TAG, "ACTION_GET_ACTIVATION_MODE result value " + parseBoolean);
            resultExtras3.putBoolean("get_activation_mode", parseBoolean);
            setResultExtras(resultExtras3);
            return;
        }
        if ("com.gsma.services.rcs.action.SET_ACTIVATION_MODE".equals(intent.getAction())) {
            if (Boolean.parseBoolean(getStringValueSetting(context, "ModeChangeable"))) {
                boolean booleanExtra = intent.getBooleanExtra("set_activation_mode", true);
                ContentResolver contentResolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put("value", Boolean.toString(booleanExtra));
                contentResolver.update(CommonServiceConfiguration.Settings.CONTENT_URI, contentValues, "ServiceActivated", new String[]{"ServiceActivated"});
                return;
            }
            return;
        }
        if (!"com.gsma.services.rcs.action.GET_SERVICE_STARTING_STATE".equals(intent.getAction()) || (resultExtras = getResultExtras(true)) == null) {
            return;
        }
        boolean isSupportTapi = TapiServiceManager.isSupportTapi();
        resultExtras.putBoolean("get_service_starting_state", isSupportTapi);
        Log.d(this.LOG_TAG, "EXTRA_GET_SERVICE_STARTING_STATE" + isSupportTapi);
        setResultExtras(resultExtras);
    }

    private String getStringValueSetting(Context context, String str) {
        Cursor query = context.getContentResolver().query(CommonServiceConfiguration.Settings.CONTENT_URI, PROJECTION, WHERE_CLAUSE, new String[]{str}, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    query.close();
                    return string;
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query == null) {
            return null;
        }
        query.close();
        return null;
    }
}

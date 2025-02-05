package com.sec.internal.ims.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.sec.internal.helper.ImsSharedPrefHelper;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SettingsProviderUtility {
    private static ArrayList<String> mOldBackupFileList;

    enum DB_CREAT_STATE {
        DB_NEVER_CREATED,
        DB_CREATING_FAIL,
        DB_CREATED
    }

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        mOldBackupFileList = arrayList;
        arrayList.add("/efs/ims_setting/ims_setting_bak.dat");
        mOldBackupFileList.add("/efs/ims_setting/ims_setting.dat");
    }

    public static DB_CREAT_STATE getDbCreatState(Context context) {
        SharedPreferences sharedPref = ImsSharedPrefHelper.getSharedPref(-1, context, ImsSharedPrefHelper.IMS_CONFIG, 0, false);
        if (sharedPref == null || !sharedPref.contains(ImsProfileLoaderInternal.SETTING_DB_CREATED)) {
            return DB_CREAT_STATE.DB_NEVER_CREATED;
        }
        return sharedPref.getBoolean(ImsProfileLoaderInternal.SETTING_DB_CREATED, false) ? DB_CREAT_STATE.DB_CREATED : DB_CREAT_STATE.DB_CREATING_FAIL;
    }

    public static void setDbCreated(Context context, boolean z) {
        ImsSharedPrefHelper.save(-1, context, ImsSharedPrefHelper.IMS_CONFIG, ImsProfileLoaderInternal.SETTING_DB_CREATED, z);
    }
}

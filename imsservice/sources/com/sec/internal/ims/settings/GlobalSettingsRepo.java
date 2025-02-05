package com.sec.internal.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.SemSystemProperties;
import com.sec.internal.constants.Mno;
import com.sec.internal.helper.ImsSharedPrefHelper;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.interfaces.ims.core.ISimManager;
import java.util.Observable;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class GlobalSettingsRepo extends Observable {
    protected static final String SP_KEY_BUILDINFO = "buildinfo";
    protected static final String SP_KEY_CSC_IMSSETTING_TYPE = "cscimssettingtype";
    protected static final String SP_KEY_GCFMODE = "gcfmode";
    protected static final String SP_KEY_GLOBAL_GC_ENABLED = "globalgcenabled";
    protected static final String SP_KEY_HAS_SIM = "hassim";
    protected static final String SP_KEY_IMSI = "imsi";
    protected static final String SP_KEY_LOADED = "loaded";
    protected static final String SP_KEY_MNONAME = "mnoname";
    protected static final String SP_KEY_MVNONAME = "mvnoname";
    protected static final String SP_KEY_NWCODE = "nwcode";
    protected static final String SP_KEY_NWNAME = "NetworkName";
    protected Context mContext;
    GlobalSettingsRepoBase mInstance;
    protected final Object mLock;
    protected boolean mMnoNameUpdated;
    protected ContentValues mMnoinfo;
    protected int mPhoneId;
    protected boolean mVersionUpdated;

    protected int preUpdateSystemSettings(Mno mno, int i, boolean z, boolean z2) {
        return i;
    }

    protected GlobalSettingsRepo() {
        this.mPhoneId = 0;
        this.mLock = new Object();
        this.mMnoinfo = null;
        this.mInstance = null;
        this.mVersionUpdated = false;
        this.mMnoNameUpdated = false;
    }

    public GlobalSettingsRepo(Context context, int i) {
        this.mLock = new Object();
        this.mMnoinfo = null;
        this.mInstance = null;
        this.mVersionUpdated = false;
        this.mMnoNameUpdated = false;
        this.mContext = context;
        this.mPhoneId = i;
        makeInstance(SimUtil.getMno(i), i);
    }

    void makeInstance(Mno mno, int i) {
        if (mno.isKor() || mno.isChn() || mno.isHkMo() || mno.isTw()) {
            if (this.mInstance instanceof GlobalSettingsRepoKorChnx) {
                return;
            }
            initNewInstance(new GlobalSettingsRepoKorChnx(this.mContext, i));
        } else if (mno.isUSA()) {
            if (this.mInstance instanceof GlobalSettingsRepoUsa) {
                return;
            }
            initNewInstance(new GlobalSettingsRepoUsa(this.mContext, i));
        } else if (mno.isEmeasewaoce()) {
            if (this.mInstance instanceof GlobalSettingsRepoEur) {
                return;
            }
            initNewInstance(new GlobalSettingsRepoEur(this.mContext, i));
        } else {
            if (this.mInstance != null) {
                return;
            }
            initNewInstance(new GlobalSettingsRepoBase(this.mContext, i));
        }
    }

    private void initNewInstance(GlobalSettingsRepoBase globalSettingsRepoBase) {
        Optional.ofNullable(this.mInstance).ifPresent(new Consumer() { // from class: com.sec.internal.ims.settings.GlobalSettingsRepo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((GlobalSettingsRepoBase) obj).cleanUp();
            }
        });
        this.mInstance = globalSettingsRepoBase;
    }

    public boolean updateMno(ContentValues contentValues) {
        Mno fromName = Mno.fromName(contentValues.getAsString("mnoname"));
        if (((Integer) Optional.ofNullable(contentValues.getAsInteger(ISimManager.KEY_IMSSWITCH_TYPE)).orElse(0)).intValue() == 5 && !fromName.isChn()) {
            fromName = Mno.DEFAULT;
        }
        contentValues.put("mnoname", fromName.getName());
        makeInstance(fromName, this.mPhoneId);
        boolean updateMno = this.mInstance.updateMno(contentValues);
        boolean booleanValue = ((Boolean) Optional.ofNullable(contentValues.getAsBoolean("globalgcenabled")).orElse(Boolean.FALSE)).booleanValue();
        if (getGlobalGcEnabled() != booleanValue) {
            this.mInstance.loadGlobalGcSettings(booleanValue);
            updateMno = true;
        }
        SharedPreferences.Editor edit = ImsSharedPrefHelper.getSharedPref(this.mPhoneId, this.mContext, ImsSharedPrefHelper.GLOBAL_SETTINGS, 0, false).edit();
        edit.putString("mnoname", (String) Optional.ofNullable(contentValues.getAsString("mnoname")).orElse(""));
        edit.putString("mvnoname", (String) Optional.ofNullable(contentValues.getAsString("mvnoname")).orElse(""));
        edit.putString("NetworkName", (String) Optional.ofNullable(contentValues.getAsString("NetworkName")).orElse(""));
        edit.putBoolean("globalgcenabled", booleanValue);
        edit.apply();
        return updateMno;
    }

    protected boolean needResetCallSettingBySim(int i) {
        String str = SemSystemProperties.get("ro.simbased.changetype", "");
        return str.contains("OMC") || str.contains("SED");
    }

    public Cursor query(String[] strArr, String str, String[] strArr2) {
        return this.mInstance.query(strArr, str, strArr2);
    }

    public String getPreviousMno() {
        return this.mInstance.getPreviousMno();
    }

    public boolean getGlobalGcEnabled() {
        return this.mInstance.getGlobalGcEnabled();
    }

    public void update(ContentValues contentValues) {
        this.mInstance.update(contentValues);
    }

    public void reset() {
        this.mInstance.reset();
    }

    public void load() {
        this.mInstance.load();
    }

    public void loadByDynamicConfig() {
        this.mInstance.loadByDynamicConfig();
    }

    public void resetUserSettingAsDefault(boolean z, boolean z2, boolean z3) {
        this.mInstance.resetUserSettingAsDefault(z, z2, z3);
    }

    public void dump() {
        this.mInstance.dump();
    }
}

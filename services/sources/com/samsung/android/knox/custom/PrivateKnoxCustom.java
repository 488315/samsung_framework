package com.samsung.android.knox.custom;

import android.content.Context;
import android.os.Binder;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PrivateKnoxCustom {
    public static final int KNOX_CUSTOM_AUTO_STARTUP_FLAG = 1;
    public static final int KNOX_CUSTOM_AUTO_STARTUP_OFFSET = 8;
    public static final int KNOX_CUSTOM_CHECKSUM_OFFSET = 31;
    public static final int KNOX_CUSTOM_CHECKSUM_VALUE = 6;
    public static final int KNOX_CUSTOM_FLAG_OFFSET = 30;
    public static final int KNOX_CUSTOM_RESERVED_FLAG = 2;
    public static final int KNOX_CUSTOM_RESERVED_OFFSET = 9;
    public static final String KNOX_CUSTOM_SYSTEM_PERMISSION_ONESDK = "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM";
    public static final int KNOX_CUSTOM_TRUE = 8;
    public static final int PARAM_BUF_SIZE = 32;
    public static final String TAG = "PrivateKnoxCustom:";
    public static PrivateKnoxCustom mInstance;
    public Context mContext;
    public final boolean DEBUG = false;
    public EnterpriseDeviceManager mEDM = null;

    public PrivateKnoxCustom(Context context) {
        this.mContext = context;
    }

    public static synchronized PrivateKnoxCustom getInstance(Context context) {
        PrivateKnoxCustom privateKnoxCustom;
        synchronized (PrivateKnoxCustom.class) {
            try {
                if (mInstance == null) {
                    mInstance = new PrivateKnoxCustom(context);
                }
                privateKnoxCustom = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return privateKnoxCustom;
    }

    private native byte[] readParamData();

    private native boolean writeParamData(byte[] bArr);

    public final int enforceSystemPermission() throws SecurityException {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM")));
        return 0;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final boolean isKnoxCustomAutoStartUpEnabled() {
        return readBooleanDataValue(8);
    }

    public final boolean readBooleanDataValue(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        byte[] readParamData = readParamData();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        boolean z = false;
        if (readParamData == null || readParamData[31] != 6) {
            return false;
        }
        if (i == 8 && readParamData[8] == 8 && (readParamData[30] & 1) != 0) {
            z = true;
        }
        if (i == 9 && readParamData[9] == 8 && (readParamData[30] & 2) != 0) {
            return true;
        }
        return z;
    }

    public final boolean setKnoxCustomAutoStartUp(boolean z) {
        enforceSystemPermission();
        boolean readBooleanDataValue = readBooleanDataValue(8);
        if (z) {
            if (readBooleanDataValue) {
                return false;
            }
            return writeBooleanDataValue(8, true);
        }
        if (readBooleanDataValue) {
            return writeBooleanDataValue(8, false);
        }
        return false;
    }

    public final boolean writeBooleanDataValue(int i, boolean z) {
        byte[] bArr = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        secureRandom.nextBytes(bArr);
        byte[] readParamData = readParamData();
        if (readParamData[31] == 6) {
            bArr[8] = readParamData[8];
            bArr[9] = readParamData[9];
            bArr[30] = readParamData[30];
        } else {
            bArr[8] = 23;
            bArr[9] = 0;
            bArr[30] = 0;
        }
        if (z) {
            bArr[i] = 8;
        } else {
            bArr[i] = 7;
        }
        bArr[31] = 6;
        if (i == 8) {
            bArr[30] = (byte) (bArr[30] | 1);
        }
        if (i == 9) {
            bArr[30] = (byte) (bArr[30] | 2);
        }
        boolean writeParamData = writeParamData(bArr);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return writeParamData;
    }
}

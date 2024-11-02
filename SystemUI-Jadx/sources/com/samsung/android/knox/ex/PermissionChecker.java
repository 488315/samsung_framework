package com.samsung.android.knox.ex;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.knox.IEnterpriseDeviceManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class PermissionChecker {
    public static final String TAG = "PermissionChecker";
    public static IEnterpriseDeviceManager mService;

    public static boolean checkPermission(Context context, int i, int i2, String str) {
        if (str == null || context.checkPermission(str, i, i2) != 0) {
            return false;
        }
        return true;
    }

    public static void enforceKnoxAccessPermission(String str, String str2) {
        if (getService() != null) {
            try {
                mService.enforceKnoxV2Permission(str, str2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public static DevicePolicyManager getDevicePolicyManager(Context context) {
        return (DevicePolicyManager) context.getSystemService("device_policy");
    }

    public static IEnterpriseDeviceManager getService() {
        if (mService == null) {
            mService = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }
        return mService;
    }

    public static UserManager getUserManager(Context context) {
        return (UserManager) context.getSystemService("user");
    }

    public static boolean hasDelegatedPermission(String str, int i, String str2) {
        if (getService() != null) {
            try {
                return mService.hasDelegatedPermission(str, i, str2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
                return false;
            }
        }
        return false;
    }

    public static boolean hasDeviceOwner(Context context) {
        if (getDevicePolicyManager(context).getDeviceOwner() != null) {
            return true;
        }
        return false;
    }

    public static boolean isManagedProfile(Context context) {
        return getUserManager(context).isManagedProfile();
    }

    public static boolean isOrganizationOwnedDeviceWithManagedProfile(Context context) {
        return getDevicePolicyManager(context).isOrganizationOwnedDeviceWithManagedProfile();
    }

    public static void enforceKnoxAccessPermission(Context context, int i, int i2, String str, String str2) {
        if (getService() != null) {
            try {
                boolean enforceKnoxV2VerifyCaller = mService.enforceKnoxV2VerifyCaller(i2);
                if (!checkPermission(context, i, i2, str)) {
                    if (UserHandle.getUserId(i2) != 0) {
                        if (!isOrganizationOwnedDeviceWithManagedProfile(context)) {
                            Log.e(TAG, "This API is works only with managedProfile(WPC)");
                            throw new SecurityException("This API is works only with managedProfile(WPC)");
                        }
                    } else if (!hasDeviceOwner(context)) {
                        Log.e(TAG, "This API is works only with managed device(DO)");
                        throw new SecurityException("This API is works only with managed device(DO)");
                    }
                }
                if (enforceKnoxV2VerifyCaller || checkPermission(context, i, i2, str2)) {
                    return;
                }
                String str3 = "Application doesn't have this permission:" + str2;
                Log.e(TAG, str3);
                throw new SecurityException(str3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
                throw e;
            }
        }
    }
}

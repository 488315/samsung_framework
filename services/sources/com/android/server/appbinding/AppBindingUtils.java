package com.android.server.appbinding;

import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AppBindingUtils {
    public static ServiceInfo findService(String str, int i, String str2, String str3, Class cls, IPackageManager iPackageManager, StringBuilder sb) {
        String simpleName = cls.getSimpleName();
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        sb.setLength(0);
        try {
            ParceledListSlice queryIntentServices = iPackageManager.queryIntentServices(intent, (String) null, 0L, i);
            if (queryIntentServices != null && queryIntentServices.getList().size() != 0) {
                List list = queryIntentServices.getList();
                if (list.size() > 1) {
                    sb.append("More than one " + simpleName + "'s found in package " + str + ".  They'll all be ignored.");
                    Log.e("AppBindingUtils", sb.toString());
                    return null;
                }
                ServiceInfo serviceInfo = ((ResolveInfo) list.get(0)).serviceInfo;
                if (str3.equals(serviceInfo.permission)) {
                    return serviceInfo;
                }
                sb.append(simpleName + " " + serviceInfo.getComponentName().flattenToShortString() + " must be protected with " + str3 + ".");
                Log.e("AppBindingUtils", sb.toString());
                return null;
            }
            sb.append("Service with " + str2 + " not found.");
        } catch (RemoteException unused) {
        }
        return null;
    }
}

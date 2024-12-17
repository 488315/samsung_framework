package com.android.server.knox.zt.devicetrust.data;

import android.content.pm.IPackageManager;
import android.os.SELinux;
import android.os.ServiceManager;
import com.samsung.android.server.pm.PmServerUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Utils {
    public static final String EMPTY_STRING = "";
    public IPackageManager mPm = getPackageManager();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InstanceHolder {
        public static final Utils INSTANCE = new Utils();
    }

    public static Utils getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static long millisToNanos(long j) {
        return j * 1000000;
    }

    public static String nullSafe(String str) {
        return str != null ? str : "";
    }

    public final IPackageManager getPackageManager() {
        return IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x000d A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPackageNameForUid(int r1) {
        /*
            r0 = this;
            android.content.pm.IPackageManager r0 = r0.mPm
            if (r0 == 0) goto L9
            java.lang.String r0 = r0.getNameForUid(r1)     // Catch: android.os.RemoteException -> L9
            goto La
        L9:
            r0 = 0
        La:
            if (r0 == 0) goto Ld
            goto Lf
        Ld:
            java.lang.String r0 = ""
        Lf:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.zt.devicetrust.data.Utils.getPackageNameForUid(int):java.lang.String");
    }

    public final String getProcessNameForPid(int i) {
        String str;
        try {
            str = PmServerUtils.getProcessNameForPid(i);
        } catch (Throwable unused) {
            str = null;
        }
        return str != null ? str : "";
    }

    public final String getSecurityContextForPid(int i) {
        String str;
        try {
            str = SELinux.getPidContext(i);
        } catch (Throwable unused) {
            str = null;
        }
        return str != null ? str : "";
    }
}

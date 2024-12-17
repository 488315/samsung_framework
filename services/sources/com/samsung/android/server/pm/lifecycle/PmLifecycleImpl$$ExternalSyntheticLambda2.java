package com.samsung.android.server.pm.lifecycle;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.PackageManagerServiceUtils;
import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PmLifecycleImpl$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PackageInfo packageInfo = (PackageInfo) obj;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null) {
            PackageManagerServiceUtils.logCriticalInfo(6, "missing applicationInfo : " + packageInfo.packageName);
        } else if (applicationInfo.getCodePath().startsWith("/data/app")) {
            File file = new File(packageInfo.applicationInfo.getCodePath());
            if (!file.exists()) {
                PackageManagerServiceUtils.logCriticalInfo(6, "missing codepath : " + packageInfo.applicationInfo.getCodePath());
            } else if (ArrayUtils.isEmpty(file.listFiles())) {
                PackageManagerServiceUtils.logCriticalInfo(6, "no packages found in codepath : " + packageInfo.applicationInfo.getCodePath());
            }
        }
    }
}

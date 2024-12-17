package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.SystemClock;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupServiceFilter implements IFilter {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class BackupServiceFilterHolder {
        public static final BackupServiceFilter INSTANCE = new BackupServiceFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        synchronized (mARsPolicyManager.mBackupExpirationUptimeMap) {
            try {
                if (mARsPolicyManager.mBackupExpirationUptimeMap.containsKey(Integer.valueOf(i2))) {
                    if (SystemClock.uptimeMillis() < ((Long) mARsPolicyManager.mBackupExpirationUptimeMap.get(Integer.valueOf(i2))).longValue()) {
                        return 26;
                    }
                    mARsPolicyManager.mBackupExpirationUptimeMap.remove(Integer.valueOf(i2));
                }
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
    }
}

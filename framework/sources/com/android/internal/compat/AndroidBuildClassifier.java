package com.android.internal.compat;

import android.os.Build;

/* loaded from: classes5.dex */
public class AndroidBuildClassifier {
    public boolean isDebuggableBuild() {
        return Build.IS_DEBUGGABLE;
    }

    public boolean isFinalBuild() {
        return "REL".equals(Build.VERSION.CODENAME);
    }

    public int platformTargetSdk() {
        if (isFinalBuild()) {
            return Build.VERSION.SDK_INT;
        }
        return 10000;
    }
}

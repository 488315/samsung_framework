package com.samsung.android.jdsms;

import android.os.Process;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
final class UidAllowList {
    private static final Set<String> mAllowList;

    static {
        HashSet<String> modifiableSet = new HashSet<>();
        modifiableSet.add("OEM_UID:" + Integer.toString(Process.DSMS_UID));
        modifiableSet.add("android.uid.dsms:" + Integer.toString(Process.DSMS_UID));
        modifiableSet.add("android.uid.system:" + Integer.toString(1000));
        mAllowList = Collections.unmodifiableSet(modifiableSet);
    }

    public boolean containsUid(String uidName) {
        return mAllowList.contains(uidName);
    }
}

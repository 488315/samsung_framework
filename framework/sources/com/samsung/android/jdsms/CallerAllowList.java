package com.samsung.android.jdsms;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
final class CallerAllowList {
    private static final Set<String> mAllowList;

    static {
        HashSet<String> modifiableSet = new HashSet<>();
        modifiableSet.add("com.android.server.ReactiveService$1.onReceive");
        modifiableSet.add("com.android.server.StorageManagerService.prepareUserStorageInternal");
        modifiableSet.add("com.android.server.devicepolicy.DevicePolicyManagerService.SendlogDSMS");
        modifiableSet.add("com.samsung.android.jdsms.DsmsService.sendMessage");
        mAllowList = Collections.unmodifiableSet(modifiableSet);
    }

    public boolean contains(String caller) {
        return mAllowList.contains(caller);
    }
}

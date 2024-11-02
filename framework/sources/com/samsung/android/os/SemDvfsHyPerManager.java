package com.samsung.android.os;

import android.content.Context;

/* loaded from: classes5.dex */
public class SemDvfsHyPerManager extends SemDvfsManager {
    public SemDvfsHyPerManager(Context context, String packageName, int type) {
        super(context, packageName, type);
        this.LOG_TAG = SemDvfsHyPerManager.class.getSimpleName();
        this.mName = "HyPer";
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void setDvfsValue(int value) {
        if (this.mType == -999) {
            return;
        }
        this.acquireHash.put(Integer.valueOf(this.mType), Integer.valueOf(value));
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void setDvfsValue(String actionName) {
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void clearDvfsValue() {
        this.acquireHash.clear();
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void acquire() {
        acquire(-999);
    }
}

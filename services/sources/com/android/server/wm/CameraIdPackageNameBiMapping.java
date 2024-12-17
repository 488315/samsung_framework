package com.android.server.wm;

import android.util.ArrayMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CameraIdPackageNameBiMapping {
    public final Map mPackageToCameraIdMap = new ArrayMap();
    public final Map mCameraIdToPackageMap = new ArrayMap();

    public final void put(String str, String str2) {
        String str3 = (String) ((ArrayMap) this.mPackageToCameraIdMap).get(str);
        if (str3 != null) {
            this.mPackageToCameraIdMap.remove(str, str3);
            this.mCameraIdToPackageMap.remove(str3, str);
        }
        String str4 = (String) ((ArrayMap) this.mCameraIdToPackageMap).get(str2);
        if (str4 != null) {
            this.mPackageToCameraIdMap.remove(str4, str2);
            this.mCameraIdToPackageMap.remove(str2, str4);
        }
        ((ArrayMap) this.mPackageToCameraIdMap).put(str, str2);
        ((ArrayMap) this.mCameraIdToPackageMap).put(str2, str);
    }
}

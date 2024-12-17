package com.android.server.wm;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.wm.CameraStateMonitor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class CameraStateMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ CameraStateMonitor f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ CameraStateMonitor$$ExternalSyntheticLambda1(CameraStateMonitor cameraStateMonitor, String str) {
        this.f$0 = cameraStateMonitor;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CameraStateMonitor cameraStateMonitor = this.f$0;
        String str = this.f$1;
        WindowManagerGlobalLock windowManagerGlobalLock = cameraStateMonitor.mWmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!((ArraySet) cameraStateMonitor.mScheduledToBeRemovedCameraIdSet).remove(str)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                boolean z = true;
                for (int i = 0; i < cameraStateMonitor.mCameraStateListeners.size(); i++) {
                    z &= ((CameraStateMonitor.CameraCompatStateListener) cameraStateMonitor.mCameraStateListeners.get(i)).onCameraClosed(str);
                }
                if (z) {
                    CameraIdPackageNameBiMapping cameraIdPackageNameBiMapping = cameraStateMonitor.mCameraIdPackageBiMapping;
                    String str2 = (String) ((ArrayMap) cameraIdPackageNameBiMapping.mCameraIdToPackageMap).get(str);
                    if (str2 != null) {
                        cameraIdPackageNameBiMapping.mPackageToCameraIdMap.remove(str2, str);
                        cameraIdPackageNameBiMapping.mCameraIdToPackageMap.remove(str, str2);
                    }
                } else {
                    ((ArraySet) cameraStateMonitor.mScheduledToBeRemovedCameraIdSet).add(str);
                    cameraStateMonitor.mHandler.postDelayed(new CameraStateMonitor$$ExternalSyntheticLambda1(cameraStateMonitor, str), 2000L);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }
}

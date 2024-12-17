package com.android.server.accessibility;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.function.pooled.PooledLambda;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda27 {
    public final /* synthetic */ AccessibilityManagerService f$0;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda27(AccessibilityManagerService accessibilityManagerService) {
        this.f$0 = accessibilityManagerService;
    }

    public final void onResult(int i, boolean z) {
        AccessibilityManagerService accessibilityManagerService = this.f$0;
        AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
        int magnificationModeLocked = userStateLocked.getMagnificationModeLocked(i) ^ 3;
        if (z || magnificationModeLocked == 0) {
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(13), accessibilityManagerService, Integer.valueOf(i)));
            return;
        }
        userStateLocked.mMagnificationModes.put(i, magnificationModeLocked);
        if (i == accessibilityManagerService.getDisplayId()) {
            BackgroundThread.getHandler().post(new AccessibilityManagerService$$ExternalSyntheticLambda6(magnificationModeLocked, accessibilityManagerService));
        }
    }
}

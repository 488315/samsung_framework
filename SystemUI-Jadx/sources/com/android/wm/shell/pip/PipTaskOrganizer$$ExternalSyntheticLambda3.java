package com.android.wm.shell.pip;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ Parcelable f$1;
    public final /* synthetic */ SurfaceControl f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda3(PipTaskOrganizer pipTaskOrganizer, Rect rect, SurfaceControl surfaceControl) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = rect;
        this.f$2 = surfaceControl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipTaskOrganizer pipTaskOrganizer = this.f$0;
                Rect rect = (Rect) this.f$1;
                SurfaceControl surfaceControl = this.f$2;
                if (pipTaskOrganizer.mPipTransitionState.mState == 5) {
                    Log.w("PipTaskOrganizer", "onEndOfSwipePipToHomeTransition: failed to enter, reason=exiting_pip");
                } else {
                    pipTaskOrganizer.finishResizeForMenu(rect);
                    pipTaskOrganizer.sendOnPipTransitionFinished(2);
                }
                if (surfaceControl != null) {
                    pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, null, false, -1);
                    return;
                }
                return;
            default:
                PipTaskOrganizer pipTaskOrganizer2 = this.f$0;
                SurfaceControl surfaceControl2 = this.f$2;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$1;
                pipTaskOrganizer2.getClass();
                Log.w("PipTaskOrganizer", "onTaskVanished: Remove, leash=" + surfaceControl2 + ", info=" + runningTaskInfo);
                SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer2.mSurfaceControlTransactionFactory).getTransaction();
                transaction.addDebugName("PIP_Vanished");
                transaction.remove(surfaceControl2);
                transaction.apply();
                return;
        }
    }

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda3(PipTaskOrganizer pipTaskOrganizer, SurfaceControl surfaceControl, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.f$0 = pipTaskOrganizer;
        this.f$2 = surfaceControl;
        this.f$1 = runningTaskInfo;
    }
}

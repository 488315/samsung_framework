package com.android.wm.shell.naturalswitching;

import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.SurfaceControl;
import android.view.View;
import android.window.TaskAppearedInfo;
import com.android.wm.shell.common.DismissButtonManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DragTargetView dragTargetView;
        int i = 2;
        int i2 = 0;
        switch (this.$r8$classId) {
            case 0:
                NaturalSwitchingLayout naturalSwitchingLayout = (NaturalSwitchingLayout) this.f$0;
                if (naturalSwitchingLayout.mHideRequested) {
                    Log.w("NaturalSwitchingLayout", "onPreDraw: failed, reason=hide_requested");
                    return;
                }
                naturalSwitchingLayout.mReadyToStart = true;
                NonDragTargetView nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                nonDragTargetView.getClass();
                Log.d("NonDragTargetView", "showBackground");
                nonDragTargetView.mMainView.setVisibility(0);
                View view = nonDragTargetView.mDimView;
                if (nonDragTargetView.mNaturalSwitchingMode != 1) {
                    i2 = 8;
                }
                view.setVisibility(i2);
                if (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout.mIsPipNaturalSwitching) {
                    DragTargetView dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
                    dragTargetView2.getClass();
                    dragTargetView2.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                    AudioManager audioManager = (AudioManager) dragTargetView2.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                    if (audioManager == null) {
                        Log.w("DragTargetView", "performSoundEffect: Couldn't get audio manager");
                    } else {
                        audioManager.playSoundEffect(106);
                    }
                    dragTargetView2.mNonDragTargetView.startTransition(true);
                } else {
                    naturalSwitchingLayout.mDragTargetView.startSpringAnimation(true);
                }
                naturalSwitchingLayout.mHandler.post(new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, i));
                return;
            case 1:
                ((NaturalSwitchingLayout) this.f$0).hide(false);
                return;
            case 2:
                final NaturalSwitchingLayout naturalSwitchingLayout2 = (NaturalSwitchingLayout) this.f$0;
                if (naturalSwitchingLayout2.mNaturalSwitchingStartReported) {
                    Log.w("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: failed, already started!");
                    return;
                }
                if (naturalSwitchingLayout2.mHideRequested) {
                    Log.w("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: failed, reason=hide_requested");
                    return;
                }
                int i3 = naturalSwitchingLayout2.mNaturalSwitchingMode;
                if (i3 != 0) {
                    naturalSwitchingLayout2.mNaturalSwitchingStartReported = true;
                    if (i3 == 1 && !naturalSwitchingLayout2.mTaskVisibility.isTaskVisible(1)) {
                        naturalSwitchingLayout2.mSplitScreenController.setDividerVisibilityFromNS(false);
                    }
                    IBinder iBinder = null;
                    if (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout2.mIsPipNaturalSwitching && (dragTargetView = naturalSwitchingLayout2.mDragTargetView) != null) {
                        iBinder = dragTargetView.getWindowToken();
                    }
                    Log.d("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: " + naturalSwitchingLayout2);
                    MultiWindowManager.getInstance().startNaturalSwitching(naturalSwitchingLayout2.mBinder, iBinder);
                    if (!CoreRune.MW_NATURAL_SWITCHING_PIP || !naturalSwitchingLayout2.mIsPipNaturalSwitching || naturalSwitchingLayout2.mNaturalSwitchingMode != 2) {
                        ArrayList arrayList = naturalSwitchingLayout2.mHideTasks;
                        arrayList.clear();
                        List visibleTaskAppearedInfos = naturalSwitchingLayout2.mShellTaskOrganizer.getVisibleTaskAppearedInfos();
                        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        ((ArrayList) visibleTaskAppearedInfos).forEach(new Consumer() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                boolean z;
                                NaturalSwitchingLayout naturalSwitchingLayout3 = NaturalSwitchingLayout.this;
                                SurfaceControl.Transaction transaction2 = transaction;
                                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                                naturalSwitchingLayout3.getClass();
                                if (taskAppearedInfo.getTaskInfo().taskId == naturalSwitchingLayout3.mTaskInfo.taskId) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (naturalSwitchingLayout3.mNaturalSwitchingMode != 2 || z) {
                                    if ((!CoreRune.MW_NATURAL_SWITCHING_PIP || !naturalSwitchingLayout3.mIsPipNaturalSwitching || !z) && taskAppearedInfo.getTaskInfo().displayId == 0) {
                                        transaction2.setAlpha(taskAppearedInfo.getLeash(), 0.0f);
                                        naturalSwitchingLayout3.mHideTasks.add(taskAppearedInfo);
                                    }
                                }
                            }
                        });
                        if (!arrayList.isEmpty()) {
                            transaction.apply();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            default:
                ((DismissButtonManager) this.f$0).cleanUpDismissTarget();
                return;
        }
    }
}

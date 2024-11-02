package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TaskOperations {
    public final Context mContext;
    public final Optional mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public final FreeformTaskTransitionStarter mTransitionStarter;

    public TaskOperations(FreeformTaskTransitionStarter freeformTaskTransitionStarter, Context context, SyncTransactionQueue syncTransactionQueue, Optional<SplitScreenController> optional) {
        this.mTransitionStarter = freeformTaskTransitionStarter;
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
        this.mSplitScreenController = optional;
    }

    public final void closeTask(WindowContainerToken windowContainerToken) {
        if (CoreRune.MW_CAPTION_SHELL) {
            Optional optional = this.mSplitScreenController;
            if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(windowContainerToken)) {
                ((SplitScreenController) optional.get()).dismissSplitTask(windowContainerToken);
                return;
            }
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.removeTask(windowContainerToken);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            FreeformTaskTransitionHandler freeformTaskTransitionHandler = (FreeformTaskTransitionHandler) this.mTransitionStarter;
            ((ArrayList) freeformTaskTransitionHandler.mPendingTransitionTokens).add(freeformTaskTransitionHandler.mTransitions.startTransition(2, windowContainerTransaction, freeformTaskTransitionHandler));
            return;
        }
        this.mSyncQueue.queue(windowContainerTransaction);
    }

    public final void maximizeTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i;
        int i2;
        if (CoreRune.MW_CAPTION_SHELL) {
            Optional optional = this.mSplitScreenController;
            if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(runningTaskInfo.token)) {
                ((SplitScreenController) optional.get()).maximizeSplitTask(runningTaskInfo.token);
                return;
            }
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (runningTaskInfo.getWindowingMode() != 1) {
            i = 1;
        } else {
            i = 5;
        }
        int displayWindowingMode = runningTaskInfo.configuration.windowConfiguration.getDisplayWindowingMode();
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        if (i == displayWindowingMode) {
            i2 = 0;
        } else {
            i2 = i;
        }
        windowContainerTransaction.setWindowingMode(windowContainerToken, i2);
        if (i == 1) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            ((FreeformTaskTransitionHandler) this.mTransitionStarter).startWindowingModeTransition(windowContainerTransaction, i);
        } else {
            this.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    public final void minimizeTask(WindowContainerToken windowContainerToken) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.reorder(windowContainerToken, false);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            FreeformTaskTransitionHandler freeformTaskTransitionHandler = (FreeformTaskTransitionHandler) this.mTransitionStarter;
            ((ArrayList) freeformTaskTransitionHandler.mPendingTransitionTokens).add(freeformTaskTransitionHandler.mTransitions.startTransition(4, windowContainerTransaction, freeformTaskTransitionHandler));
            return;
        }
        this.mSyncQueue.queue(windowContainerTransaction);
    }

    public final void moveToFreeform(WindowContainerToken windowContainerToken) {
        Optional optional = this.mSplitScreenController;
        if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(windowContainerToken)) {
            ((SplitScreenController) optional.get()).moveSplitToFreeform(windowContainerToken, null, false);
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setWindowingMode(windowContainerToken, 5);
        ((FreeformTaskTransitionHandler) this.mTransitionStarter).startWindowingModeTransition(windowContainerTransaction, 5);
    }

    public final void sendBackEvent(int i, int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
        if (CoreRune.MW_CAPTION_SHELL_DEX) {
            keyEvent.setDisplayId(i2);
        }
        if (!((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0)) {
            Log.e("TaskOperations", "Inject input event fail");
        }
    }
}

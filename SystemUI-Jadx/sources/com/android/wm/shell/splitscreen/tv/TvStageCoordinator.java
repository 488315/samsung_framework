package com.android.wm.shell.splitscreen.tv;

import android.content.Context;
import android.os.Handler;
import android.view.SurfaceControlViewHost;
import android.view.WindowManager;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.tv.TvSplitMenuController;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TvStageCoordinator extends StageCoordinator implements TvSplitMenuController.StageController {
    public final TvSplitMenuController mTvSplitMenuController;

    public TvStageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, ShellExecutor shellExecutor, Handler handler, Optional<RecentTasksController> optional, SystemWindows systemWindows) {
        super(context, i, syncTransactionQueue, shellTaskOrganizer, displayController, displayImeController, displayInsetsController, transitions, transactionPool, iconProvider, shellExecutor, optional);
        this.mTvSplitMenuController = new TvSplitMenuController(context, this, systemWindows, handler);
    }

    @Override // com.android.wm.shell.splitscreen.StageCoordinator
    public final void onSplitScreenEnter() {
        TvSplitMenuController tvSplitMenuController = this.mTvSplitMenuController;
        tvSplitMenuController.getClass();
        Context context = tvSplitMenuController.mContext;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, 2034, 16, -3);
        layoutParams.privateFlags |= 536870976;
        TvSplitMenuView tvSplitMenuView = tvSplitMenuController.mSplitMenuView;
        tvSplitMenuView.setAlpha(0.0f);
        tvSplitMenuController.mSystemWindows.addView(tvSplitMenuView, layoutParams, 0);
        TvSplitMenuController.ActionBroadcastReceiver actionBroadcastReceiver = tvSplitMenuController.mActionBroadcastReceiver;
        if (!actionBroadcastReceiver.mRegistered) {
            TvSplitMenuController tvSplitMenuController2 = TvSplitMenuController.this;
            tvSplitMenuController2.mContext.registerReceiverForAllUsers(actionBroadcastReceiver, actionBroadcastReceiver.mIntentFilter, "com.android.systemui.permission.SELF", tvSplitMenuController2.mMainHandler);
            actionBroadcastReceiver.mRegistered = true;
        }
    }

    @Override // com.android.wm.shell.splitscreen.StageCoordinator
    public final void onSplitScreenExit() {
        TvSplitMenuController tvSplitMenuController = this.mTvSplitMenuController;
        TvSplitMenuController.ActionBroadcastReceiver actionBroadcastReceiver = tvSplitMenuController.mActionBroadcastReceiver;
        if (actionBroadcastReceiver.mRegistered) {
            TvSplitMenuController.this.mContext.unregisterReceiver(actionBroadcastReceiver);
            actionBroadcastReceiver.mRegistered = false;
        }
        ((SurfaceControlViewHost) tvSplitMenuController.mSystemWindows.mViewRoots.remove(tvSplitMenuController.mSplitMenuView)).release();
    }
}

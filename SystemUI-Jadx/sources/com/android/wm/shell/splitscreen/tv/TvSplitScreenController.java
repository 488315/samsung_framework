package com.android.wm.shell.splitscreen.tv;

import android.content.Context;
import android.os.Handler;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TvSplitScreenController extends SplitScreenController {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final IconProvider mIconProvider;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final Optional mRecentTasksOptional;
    public final SyncTransactionQueue mSyncQueue;
    public final SystemWindows mSystemWindows;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;

    public TvSplitScreenController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Optional<DragAndDropController> optional, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, Optional<RecentTasksController> optional2, ShellExecutor shellExecutor, Handler handler, SystemWindows systemWindows) {
        super(context, shellInit, shellCommandHandler, shellController, shellTaskOrganizer, syncTransactionQueue, rootTaskDisplayAreaOrganizer, displayController, displayImeController, displayInsetsController, optional, transitions, transactionPool, iconProvider, optional2, shellExecutor);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransitions = transitions;
        this.mTransactionPool = transactionPool;
        this.mIconProvider = iconProvider;
        this.mRecentTasksOptional = optional2;
        this.mMainHandler = handler;
        this.mSystemWindows = systemWindows;
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreenController
    public final StageCoordinator createStageCoordinator() {
        return new TvStageCoordinator(this.mContext, 0, this.mSyncQueue, this.mTaskOrganizer, this.mDisplayController, this.mDisplayImeController, this.mDisplayInsetsController, this.mTransitions, this.mTransactionPool, this.mIconProvider, this.mMainExecutor, this.mMainHandler, this.mRecentTasksOptional, this.mSystemWindows);
    }
}

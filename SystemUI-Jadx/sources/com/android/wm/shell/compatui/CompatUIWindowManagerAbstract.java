package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class CompatUIWindowManagerAbstract extends WindowlessWindowManager {
    public Context mContext;
    public final int mDisplayId;
    public DisplayLayout mDisplayLayout;
    public SurfaceControl mLeash;
    public final Rect mStableBounds;
    public final SyncTransactionQueue mSyncQueue;
    public Configuration mTaskConfig;
    public final int mTaskId;
    public TaskInfo mTaskInfo;
    public ShellTaskOrganizer.TaskListener mTaskListener;
    public SurfaceControlViewHost mViewHost;

    public CompatUIWindowManagerAbstract(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout) {
        super(taskInfo.configuration, (SurfaceControl) null, (IBinder) null);
        this.mTaskInfo = taskInfo;
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskConfig = taskInfo.configuration;
        this.mDisplayId = context.getDisplayId();
        this.mTaskId = taskInfo.taskId;
        this.mTaskListener = taskListener;
        this.mDisplayLayout = displayLayout;
        Rect rect = new Rect();
        this.mStableBounds = rect;
        this.mDisplayLayout.getStableBounds(rect, false);
    }

    public void attachToParentSurface(SurfaceControl.Builder builder) {
        this.mTaskListener.attachChildSurfaceToTask(this.mTaskId, builder);
    }

    public abstract View createLayout();

    public boolean createLayout(boolean z) {
        if (!eligibleToShowLayout()) {
            return false;
        }
        if (!z || getLayout() != null) {
            return true;
        }
        if (this.mViewHost == null) {
            SurfaceControlViewHost createSurfaceViewHost = createSurfaceViewHost();
            this.mViewHost = createSurfaceViewHost;
            createSurfaceViewHost.setView(createLayout(), getWindowLayoutParams());
            updateSurfacePosition();
            return true;
        }
        throw new IllegalStateException("A UI has already been created with this window manager.");
    }

    public SurfaceControlViewHost createSurfaceViewHost() {
        Context context = this.mContext;
        return new SurfaceControlViewHost(context, context.getDisplay(), this, getClass().getSimpleName());
    }

    public abstract boolean eligibleToShowLayout();

    public abstract View getLayout();

    public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
        String simpleName = getClass().getSimpleName();
        SurfaceControl.Builder callsite = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName(simpleName.concat("Leash")).setHidden(false).setCallsite(simpleName.concat("#attachToParentSurface"));
        attachToParentSurface(callsite);
        final SurfaceControl build = callsite.build();
        this.mLeash = build;
        final int zOrder = getZOrder();
        this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = CompatUIWindowManagerAbstract.this;
                SurfaceControl surfaceControl = build;
                if (surfaceControl != null) {
                    compatUIWindowManagerAbstract.getClass();
                    if (surfaceControl.isValid()) {
                        transaction.setLayer(surfaceControl, zOrder);
                        return;
                    }
                }
                Log.w(compatUIWindowManagerAbstract.getClass().getSimpleName(), "The leash has been released.");
            }
        });
        return this.mLeash;
    }

    public final Rect getTaskBounds() {
        return this.mTaskConfig.windowConfiguration.getBounds();
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        View layout = getLayout();
        if (layout == null) {
            return new WindowManager.LayoutParams();
        }
        layout.measure(0, 0);
        return getWindowLayoutParams(layout.getMeasuredWidth(), layout.getMeasuredHeight());
    }

    public int getWindowManagerLayoutParamsFlags() {
        return 40;
    }

    public abstract int getZOrder();

    public boolean needsToBeRecreated(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        boolean z;
        if (this.mTaskInfo.configuration.uiMode != taskInfo.configuration.uiMode) {
            z = true;
        } else {
            z = false;
        }
        if (!z && !(!this.mTaskListener.equals(taskListener))) {
            return false;
        }
        return true;
    }

    public void onParentBoundsChanged() {
        updateSurfacePosition();
    }

    public void release() {
        View layout = getLayout();
        if (layout != null) {
            layout.setVisibility(8);
        }
        removeLayout();
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            this.mViewHost = null;
        }
        final SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda2
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                    transaction.remove(surfaceControl);
                }
            });
            this.mLeash = null;
        }
    }

    public abstract void removeLayout();

    public final void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
        this.mContext = this.mContext.createConfigurationContext(configuration);
    }

    public final void setTouchRegion(Region region) {
        IBinder iBinder;
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null && surfaceControlViewHost.getView() != null) {
            iBinder = this.mViewHost.getView().getWindowToken();
        } else {
            iBinder = null;
        }
        if (iBinder == null) {
            return;
        }
        super.setTouchRegion(iBinder, region);
    }

    public boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        this.mTaskInfo = taskInfo;
        Configuration configuration = this.mTaskConfig;
        ShellTaskOrganizer.TaskListener taskListener2 = this.mTaskListener;
        Configuration configuration2 = taskInfo.configuration;
        this.mTaskConfig = configuration2;
        this.mTaskListener = taskListener;
        setConfiguration(configuration2);
        boolean z2 = false;
        if (!eligibleToShowLayout()) {
            release();
            return false;
        }
        View layout = getLayout();
        if (layout != null && taskListener2 == taskListener) {
            Configuration configuration3 = this.mTaskConfig;
            if (configuration3.uiMode == configuration.uiMode) {
                boolean z3 = !configuration3.windowConfiguration.getBounds().equals(configuration.windowConfiguration.getBounds());
                if (this.mTaskConfig.getLayoutDirection() != configuration.getLayoutDirection()) {
                    z2 = true;
                }
                if (z3 || z2) {
                    onParentBoundsChanged();
                }
                if (z2) {
                    layout.setLayoutDirection(this.mTaskConfig.getLayoutDirection());
                }
                return true;
            }
        }
        release();
        return createLayout(z);
    }

    public void updateDisplayLayout(DisplayLayout displayLayout) {
        Rect rect = this.mStableBounds;
        Rect rect2 = new Rect();
        displayLayout.getStableBounds(rect2, false);
        this.mDisplayLayout = displayLayout;
        if (!rect.equals(rect2)) {
            this.mStableBounds.set(rect2);
            onParentBoundsChanged();
        }
    }

    public abstract void updateSurfacePosition();

    public void updateVisibility(boolean z) {
        int i;
        View layout = getLayout();
        if (layout == null) {
            createLayout(z);
            return;
        }
        if (z && eligibleToShowLayout()) {
            i = 0;
        } else {
            i = 8;
        }
        if (layout.getVisibility() != i) {
            layout.setVisibility(i);
        }
    }

    public final WindowManager.LayoutParams getWindowLayoutParams(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2038, getWindowManagerLayoutParamsFlags(), -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(getClass().getSimpleName() + this.mTaskId);
        layoutParams.privateFlags = layoutParams.privateFlags | 536870976;
        return layoutParams;
    }
}
package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Debug;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.IRecentsAnimationRunner;
import android.window.TaskSnapshot;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.wm.ActivityMetricsLogger;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.RecentsAnimationController;
import com.android.server.wm.TaskDisplayArea;
import com.samsung.android.rune.CoreRune;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class RecentsAnimation implements RecentsAnimationController.RecentsAnimationCallbacks, TaskDisplayArea.OnRootTaskOrderChangedListener {
    public static final String TAG = "RecentsAnimation";
    public final ActivityStartController mActivityStartController;
    public final WindowProcessController mCaller;
    public final TaskDisplayArea mDefaultTaskDisplayArea;
    public ActivityRecord mLaunchedTargetActivity;
    public final ComponentName mRecentsComponent;
    public final String mRecentsFeatureId;
    public final int mRecentsUid;
    public Task mRestoreTargetBehindRootTask;
    public final ActivityTaskManagerService mService;
    public final int mTargetActivityType;
    public final Intent mTargetIntent;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final int mUserId;
    public final WindowManagerService mWindowManager;

    public static /* synthetic */ boolean lambda$onRootTaskOrderChanged$1(Task task, Task task2) {
        return task2 == task;
    }

    public RecentsAnimation(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor, ActivityStartController activityStartController, WindowManagerService windowManagerService, Intent intent, ComponentName componentName, String str, int i, WindowProcessController windowProcessController) {
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mDefaultTaskDisplayArea = activityTaskManagerService.mRootWindowContainer.getDefaultTaskDisplayArea();
        this.mActivityStartController = activityStartController;
        this.mWindowManager = windowManagerService;
        this.mTargetIntent = intent;
        this.mRecentsComponent = componentName;
        this.mRecentsFeatureId = str;
        this.mRecentsUid = i;
        this.mCaller = windowProcessController;
        this.mUserId = activityTaskManagerService.getCurrentUserId();
        this.mTargetActivityType = (intent.getComponent() == null || !componentName.equals(intent.getComponent())) ? 2 : 3;
    }

    public void preloadRecentsActivity() {
        if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -106400104, 0, "Preload recents with %s", new Object[]{String.valueOf(this.mTargetIntent)});
        }
        ActivityRecord targetActivity = getTargetActivity(this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
        if (targetActivity != null) {
            if (targetActivity.isVisibleRequested() || targetActivity.isTopRunningActivity()) {
                return;
            }
            if (targetActivity.attachedToProcess()) {
                targetActivity.ensureActivityConfiguration(0, false, true);
                if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1156118957, 0, "Updated config=%s", new Object[]{String.valueOf(targetActivity.getConfiguration())});
                }
            }
        } else {
            if (this.mDefaultTaskDisplayArea.getActivity(new Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((ActivityRecord) obj).occludesParent();
                }
            }, false) == null) {
                return;
            }
            startRecentsActivityInBackground("preloadRecents");
            targetActivity = getTargetActivity(this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
            if (targetActivity == null) {
                Slog.w(TAG, "Cannot start " + this.mTargetIntent);
                return;
            }
        }
        if (!targetActivity.attachedToProcess()) {
            if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 644675193, 0, "Real start recents", (Object[]) null);
            }
            this.mTaskSupervisor.startSpecificActivity(targetActivity, false, false);
            if (targetActivity.getDisplayContent() != null) {
                targetActivity.getDisplayContent().mUnknownAppVisibilityController.appRemovedOrHidden(targetActivity);
            }
        }
        if (targetActivity.isState(ActivityRecord.State.STOPPING, ActivityRecord.State.STOPPED)) {
            return;
        }
        targetActivity.addToStopping(true, true, "preloadRecents");
    }

    public void startRecentsActivity(IRecentsAnimationRunner iRecentsAnimationRunner, long j) {
        ActivityOptions activityOptions;
        Task task;
        boolean z;
        if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1413901262, 0, "startRecentsActivity(): intent=%s", new Object[]{String.valueOf(this.mTargetIntent)});
        }
        Trace.traceBegin(32L, "RecentsAnimation#startRecentsActivity");
        if (this.mWindowManager.getRecentsAnimationController() != null) {
            this.mWindowManager.getRecentsAnimationController().forceCancelAnimation(2, "startRecentsActivity");
        }
        Task rootTask = this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType);
        ActivityRecord targetActivity = getTargetActivity(rootTask);
        boolean z2 = targetActivity != null;
        if (z2) {
            Task rootTaskAbove = TaskDisplayArea.getRootTaskAbove(rootTask);
            this.mRestoreTargetBehindRootTask = rootTaskAbove;
            if (rootTaskAbove == null && rootTask.getTopMostTask() == targetActivity.getTask()) {
                notifyAnimationCancelBeforeStart(iRecentsAnimationRunner);
                if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -8483143, 0, "No root task above target root task=%s", new Object[]{String.valueOf(rootTask)});
                    return;
                }
                return;
            }
            if (this.mService.mController != null) {
                try {
                    z = !this.mService.mController.activityStarting(this.mTargetIntent.cloneFilter(), targetActivity.packageName);
                } catch (RemoteException unused) {
                    this.mService.mController = null;
                    z = false;
                }
                if (z) {
                    notifyAnimationCancelBeforeStart(iRecentsAnimationRunner);
                    Slog.d(TAG, "Abort cause controller : " + this.mService.mControllerDescription);
                    return;
                }
            }
        }
        if (targetActivity == null || !targetActivity.isVisibleRequested()) {
            this.mService.mRootWindowContainer.startPowerModeLaunchIfNeeded(true, targetActivity);
        }
        ActivityMetricsLogger.LaunchingState notifyActivityLaunching = this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityLaunching(this.mTargetIntent);
        setProcessAnimating(true);
        this.mService.deferWindowLayout();
        try {
            try {
                if (z2) {
                    this.mDefaultTaskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask);
                    if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 1191587912, 0, "Moved rootTask=%s behind rootTask=%s", new Object[]{String.valueOf(rootTask), String.valueOf(TaskDisplayArea.getRootTaskAbove(rootTask))});
                    }
                    Task task2 = targetActivity.getTask();
                    if (rootTask.getTopMostTask() != task2) {
                        rootTask.positionChildAtTop(task2);
                    }
                } else {
                    startRecentsActivityInBackground("startRecentsActivity_noTargetActivity");
                    Task rootTask2 = this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType);
                    targetActivity = getTargetActivity(rootTask2);
                    this.mDefaultTaskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask2);
                    if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 1191587912, 0, "Moved rootTask=%s behind rootTask=%s", new Object[]{String.valueOf(rootTask2), String.valueOf(TaskDisplayArea.getRootTaskAbove(rootTask2))});
                    }
                    this.mWindowManager.prepareAppTransitionNone();
                    this.mWindowManager.executeAppTransition();
                    if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 646155519, 0, "Started intent=%s", new Object[]{String.valueOf(this.mTargetIntent)});
                    }
                }
                ActivityRecord activityRecord = targetActivity;
                activityRecord.mLaunchTaskBehind = true;
                this.mLaunchedTargetActivity = activityRecord;
                activityRecord.intent.replaceExtras(this.mTargetIntent);
                this.mWindowManager.initializeRecentsAnimation(this.mTargetActivityType, iRecentsAnimationRunner, this, this.mDefaultTaskDisplayArea.getDisplayId(), this.mTaskSupervisor.mRecentTasks.getRecentTaskIds(), activityRecord);
                this.mService.mRootWindowContainer.ensureActivitiesVisible(null, 0, true);
                if (j > 0) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setSourceInfo(4, j);
                    activityOptions = makeBasic;
                } else {
                    activityOptions = null;
                }
                this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityLaunched(notifyActivityLaunching, 2, !z2, activityRecord, activityOptions);
                this.mDefaultTaskDisplayArea.registerRootTaskOrderChangedListener(this);
                if (CoreRune.FW_BOUNDS_COMPAT_UI && (task = this.mRestoreTargetBehindRootTask) != null) {
                    task.dispatchTaskInfoChangedByBoundsCompat();
                }
            } catch (Exception e) {
                Slog.e(TAG, "Failed to start recents activity", e);
                throw e;
            }
        } finally {
            this.mService.continueWindowLayout();
            Trace.traceEnd(32L);
        }
    }

    public final void finishAnimation(final int i, final boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Slog.d(TAG, "finishAnimation(), caller=" + Debug.getCallers(7));
                if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 765395228, 4, "onAnimationFinished(): controller=%s reorderMode=%d", new Object[]{String.valueOf(this.mWindowManager.getRecentsAnimationController()), Long.valueOf(i)});
                }
                this.mDefaultTaskDisplayArea.unregisterRootTaskOrderChangedListener(this);
                final RecentsAnimationController recentsAnimationController = this.mWindowManager.getRecentsAnimationController();
                if (recentsAnimationController == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (i != 0) {
                    this.mService.endLaunchPowerMode(1);
                }
                if (i == 1) {
                    this.mService.stopAppSwitches();
                }
                this.mWindowManager.inSurfaceTransaction(new Runnable() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        RecentsAnimation.this.lambda$finishAnimation$0(i, z, recentsAnimationController);
                    }
                });
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0068, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0174, code lost:
    
        if (r12.mWindowManager.mRoot.isLayoutNeeded() != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$finishAnimation$0(int r13, boolean r14, com.android.server.wm.RecentsAnimationController r15) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RecentsAnimation.lambda$finishAnimation$0(int, boolean, com.android.server.wm.RecentsAnimationController):void");
    }

    public final void setProcessAnimating(boolean z) {
        WindowProcessController windowProcessController = this.mCaller;
        if (windowProcessController == null) {
            return;
        }
        windowProcessController.setRunningRecentsAnimation(z);
        int i = this.mService.mDemoteTopAppReasons;
        this.mService.mDemoteTopAppReasons = z ? i | 2 : i & (-3);
        if (!z || this.mService.mTopApp == null) {
            return;
        }
        this.mService.mTopApp.scheduleUpdateOomAdj();
    }

    @Override // com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks
    public void onAnimationFinished(int i, boolean z) {
        finishAnimation(i, z);
    }

    @Override // com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener
    public void onRootTaskOrderChanged(final Task task) {
        RecentsAnimationController recentsAnimationController;
        if (ProtoLogCache.WM_DEBUG_RECENTS_ANIMATIONS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1069336896, 0, "onRootTaskOrderChanged(): rootTask=%s", new Object[]{String.valueOf(task)});
        }
        if (this.mDefaultTaskDisplayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onRootTaskOrderChanged$1;
                lambda$onRootTaskOrderChanged$1 = RecentsAnimation.lambda$onRootTaskOrderChanged$1(Task.this, (Task) obj);
                return lambda$onRootTaskOrderChanged$1;
            }
        }) == null || !task.shouldBeVisible(null) || (recentsAnimationController = this.mWindowManager.getRecentsAnimationController()) == null) {
            return;
        }
        if ((!recentsAnimationController.isAnimatingTask(task.getTopMostTask()) || recentsAnimationController.isTargetApp(task.getTopNonFinishingActivity())) && recentsAnimationController.shouldDeferCancelUntilNextTransition()) {
            this.mWindowManager.prepareAppTransitionNone();
            recentsAnimationController.setCancelOnNextTransitionStart();
        }
    }

    public final void startRecentsActivityInBackground(String str) {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchActivityType(this.mTargetActivityType);
        makeBasic.setAvoidMoveToFront();
        this.mTargetIntent.addFlags(268500992);
        this.mActivityStartController.obtainStarter(this.mTargetIntent, str).setCallingUid(this.mRecentsUid).setCallingPackage(this.mRecentsComponent.getPackageName()).setCallingFeatureId(this.mRecentsFeatureId).setActivityOptions(new SafeActivityOptions(makeBasic)).setUserId(this.mUserId).execute();
    }

    public static void notifyAnimationCancelBeforeStart(IRecentsAnimationRunner iRecentsAnimationRunner) {
        try {
            iRecentsAnimationRunner.onAnimationCanceled((int[]) null, (TaskSnapshot[]) null);
        } catch (RemoteException e) {
            Slog.e(TAG, "Failed to cancel recents animation before start", e);
        }
    }

    public final Task getTopNonAlwaysOnTopRootTask() {
        return this.mDefaultTaskDisplayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopNonAlwaysOnTopRootTask$2;
                lambda$getTopNonAlwaysOnTopRootTask$2 = RecentsAnimation.lambda$getTopNonAlwaysOnTopRootTask$2((Task) obj);
                return lambda$getTopNonAlwaysOnTopRootTask$2;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getTopNonAlwaysOnTopRootTask$2(Task task) {
        return !task.getWindowConfiguration().isAlwaysOnTop();
    }

    public final ActivityRecord getTargetActivity(Task task) {
        if (task == null) {
            return null;
        }
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new BiPredicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                boolean matchesTarget;
                matchesTarget = ((RecentsAnimation) obj).matchesTarget((Task) obj2);
                return matchesTarget;
            }
        }, this, PooledLambda.__(Task.class));
        Task task2 = task.getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            return task2.getTopNonFinishingActivity();
        }
        return null;
    }

    public final boolean matchesTarget(Task task) {
        return task.getNonFinishingActivityCount() > 0 && task.mUserId == this.mUserId && task.getBaseIntent().getComponent().equals(this.mTargetIntent.getComponent());
    }
}

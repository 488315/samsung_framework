package com.android.server.wm;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.RemoteAnimationAdapter;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.android.internal.R;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class AppTransition implements DumpUtils.Dump {
    public static final ArrayList sFlagToString;
    public IRemoteCallback mAnimationFinishedCallback;
    public final Context mContext;
    public AppTransitionAnimationSpec mDefaultNextAppTransitionAnimationSpec;
    public final int mDefaultWindowAnimationStyleResId;
    public final DisplayContent mDisplayContent;
    public final Handler mHandler;
    public String mLastChangingApp;
    public String mLastClosingApp;
    public String mLastOpeningApp;
    public IAppTransitionAnimationSpecsFuture mNextAppTransitionAnimationsSpecsFuture;
    public boolean mNextAppTransitionAnimationsSpecsPending;
    public int mNextAppTransitionBackgroundColor;
    public IRemoteCallback mNextAppTransitionCallback;
    public int mNextAppTransitionEnter;
    public int mNextAppTransitionExit;
    public IRemoteCallback mNextAppTransitionFutureCallback;
    public int mNextAppTransitionInPlace;
    public boolean mNextAppTransitionIsSync;
    public boolean mNextAppTransitionOverrideRequested;
    public String mNextAppTransitionPackage;
    public boolean mNextAppTransitionScaleUp;
    public boolean mOverrideTaskTransition;
    public RemoteAnimationController mRemoteAnimationController;
    public final WindowManagerService mService;
    final TransitionAnimation mTransitionAnimation;
    public int mNextAppTransitionFlags = 0;
    public final ArrayList mNextAppTransitionRequests = new ArrayList();
    public int mLastUsedAppTransition = -1;
    public int mNextAppTransitionType = 0;
    public final SparseArray mNextAppTransitionAnimationsSpecs = new SparseArray();
    public final Rect mTmpRect = new Rect();
    public int mAppTransitionState = 0;
    public final ArrayList mListeners = new ArrayList();
    public final ExecutorService mDefaultExecutor = Executors.newSingleThreadExecutor();
    public final Runnable mHandleAppTransitionTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            AppTransition.this.lambda$new$0();
        }
    };
    public final boolean mGridLayoutRecentsEnabled = SystemProperties.getBoolean("ro.recents.grid", false);

    public static boolean isActivityTransitOld(int i) {
        return i == 6 || i == 7 || i == 18;
    }

    public static boolean isChangeTransitOld(int i) {
        return i == 27 || i == 30;
    }

    public static boolean isClosingTransitOld(int i) {
        return i == 7 || i == 9 || i == 12 || i == 15 || i == 25 || i == 26;
    }

    public static boolean isKeyguardGoingAwayTransitOld(int i) {
        return i == 20 || i == 21;
    }

    public static boolean isKeyguardOccludeTransitOld(int i) {
        return i == 22 || i == 33 || i == 23;
    }

    public static boolean isKeyguardTransit(int i) {
        return i == 7 || i == 8 || i == 9;
    }

    public static boolean isNormalTransit(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    public static boolean isTaskCloseTransitOld(int i) {
        return i == 9 || i == 11;
    }

    public static boolean isTaskFragmentTransitOld(int i) {
        return i == 28 || i == 29 || i == 30;
    }

    public static boolean isTaskOpenTransitOld(int i) {
        return i == 8 || i == 16 || i == 10;
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0076, code lost:
    
        if (r10 != false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x007f, code lost:
    
        r3 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0079, code lost:
    
        if (r10 != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0085, code lost:
    
        r0 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0087, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x007c, code lost:
    
        if (r10 != false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0082, code lost:
    
        if (r10 != false) goto L132;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x001d. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int mapOpenCloseTransitTypes(int r9, boolean r10) {
        /*
            r0 = 4
            r1 = 5
            r2 = 24
            if (r9 == r2) goto L82
            r3 = 6
            r4 = 7
            r5 = 25
            if (r9 == r5) goto L7c
            r6 = 28
            if (r9 == r6) goto L79
            r7 = 29
            if (r9 == r7) goto L76
            r8 = 31
            if (r9 == r8) goto L70
            r6 = 32
            r7 = 0
            if (r9 == r6) goto L6a
            switch(r9) {
                case 6: goto L82;
                case 7: goto L7c;
                case 8: goto L62;
                case 9: goto L5a;
                case 10: goto L52;
                case 11: goto L4a;
                case 12: goto L42;
                case 13: goto L3a;
                case 14: goto L32;
                case 15: goto L28;
                case 16: goto L22;
                default: goto L20;
            }
        L20:
            goto L87
        L22:
            if (r10 == 0) goto L25
            r2 = r5
        L25:
            r7 = r2
            goto L87
        L28:
            if (r10 == 0) goto L2d
            r9 = 22
            goto L2f
        L2d:
            r9 = 23
        L2f:
            r7 = r9
            goto L87
        L32:
            if (r10 == 0) goto L37
            r9 = 20
            goto L2f
        L37:
            r9 = 21
            goto L2f
        L3a:
            if (r10 == 0) goto L3f
            r9 = 16
            goto L2f
        L3f:
            r9 = 17
            goto L2f
        L42:
            if (r10 == 0) goto L47
            r9 = 18
            goto L2f
        L47:
            r9 = 19
            goto L2f
        L4a:
            if (r10 == 0) goto L4f
            r9 = 14
            goto L2f
        L4f:
            r9 = 15
            goto L2f
        L52:
            if (r10 == 0) goto L57
            r9 = 12
            goto L2f
        L57:
            r9 = 13
            goto L2f
        L5a:
            if (r10 == 0) goto L5f
            r9 = 10
            goto L2f
        L5f:
            r9 = 11
            goto L2f
        L62:
            if (r10 == 0) goto L67
            r9 = 8
            goto L2f
        L67:
            r9 = 9
            goto L2f
        L6a:
            if (r10 == 0) goto L6d
            goto L87
        L6d:
            r9 = 27
            goto L2f
        L70:
            if (r10 == 0) goto L73
            goto L74
        L73:
            r6 = r7
        L74:
            r7 = r6
            goto L87
        L76:
            if (r10 == 0) goto L7f
            goto L80
        L79:
            if (r10 == 0) goto L85
            goto L86
        L7c:
            if (r10 == 0) goto L7f
            goto L80
        L7f:
            r3 = r4
        L80:
            r7 = r3
            goto L87
        L82:
            if (r10 == 0) goto L85
            goto L86
        L85:
            r0 = r1
        L86:
            r7 = r0
        L87:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.AppTransition.mapOpenCloseTransitTypes(int, boolean):int");
    }

    public void registerKeygaurdExitAnimationStartListener(WindowManagerInternal.KeyguardExitAnimationStartListener keyguardExitAnimationStartListener) {
    }

    public AppTransition(Context context, WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mContext = context;
        this.mService = windowManagerService;
        this.mHandler = new Handler(windowManagerService.mH.getLooper());
        this.mDisplayContent = displayContent;
        this.mTransitionAnimation = new TransitionAnimation(context, ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM), StartingSurfaceController.TAG);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
    }

    public boolean isTransitionSet() {
        return !this.mNextAppTransitionRequests.isEmpty();
    }

    public boolean isUnoccluding() {
        return this.mNextAppTransitionRequests.contains(9);
    }

    public boolean transferFrom(AppTransition appTransition) {
        this.mNextAppTransitionRequests.addAll(appTransition.mNextAppTransitionRequests);
        return prepare();
    }

    public void setLastAppTransition(int i, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityRecord activityRecord3) {
        this.mLastUsedAppTransition = i;
        this.mLastOpeningApp = "" + activityRecord;
        this.mLastClosingApp = "" + activityRecord2;
        this.mLastChangingApp = "" + activityRecord3;
    }

    public boolean isReady() {
        int i = this.mAppTransitionState;
        return i == 1 || i == 3;
    }

    public void setReady() {
        setAppTransitionState(1);
        fetchAppTransitionSpecsFromFuture();
    }

    public boolean isRunning() {
        return this.mAppTransitionState == 2;
    }

    public void setIdle() {
        setAppTransitionState(0);
    }

    public boolean isIdle() {
        return this.mAppTransitionState == 0;
    }

    public boolean isTimeout() {
        return this.mAppTransitionState == 3;
    }

    public void setTimeout() {
        setAppTransitionState(3);
    }

    public Animation getNextAppRequestedAnimation(boolean z) {
        Animation loadAppTransitionAnimation = this.mTransitionAnimation.loadAppTransitionAnimation(this.mNextAppTransitionPackage, z ? this.mNextAppTransitionEnter : this.mNextAppTransitionExit);
        int i = this.mNextAppTransitionBackgroundColor;
        if (i != 0 && loadAppTransitionAnimation != null) {
            loadAppTransitionAnimation.setBackdropColor(i);
        }
        return loadAppTransitionAnimation;
    }

    public int getNextAppTransitionBackgroundColor() {
        return this.mNextAppTransitionBackgroundColor;
    }

    public boolean isNextAppTransitionOverrideRequested() {
        return this.mNextAppTransitionOverrideRequested;
    }

    public HardwareBuffer getAppTransitionThumbnailHeader(WindowContainer windowContainer) {
        AppTransitionAnimationSpec appTransitionAnimationSpec = (AppTransitionAnimationSpec) this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
        if (appTransitionAnimationSpec == null) {
            appTransitionAnimationSpec = this.mDefaultNextAppTransitionAnimationSpec;
        }
        if (appTransitionAnimationSpec != null) {
            return appTransitionAnimationSpec.buffer;
        }
        return null;
    }

    public boolean isNextAppTransitionThumbnailUp() {
        int i = this.mNextAppTransitionType;
        return i == 3 || i == 5;
    }

    public boolean isNextAppTransitionThumbnailDown() {
        int i = this.mNextAppTransitionType;
        return i == 4 || i == 6;
    }

    public boolean isNextAppTransitionOpenCrossProfileApps() {
        return this.mNextAppTransitionType == 9;
    }

    public boolean isFetchingAppTransitionsSpecs() {
        return this.mNextAppTransitionAnimationsSpecsPending;
    }

    public final boolean prepare() {
        if (isRunning()) {
            return false;
        }
        setAppTransitionState(0);
        notifyAppTransitionPendingLocked();
        return true;
    }

    public int goodToGo(int i, ActivityRecord activityRecord) {
        long uptimeMillis;
        boolean z = false;
        this.mNextAppTransitionFlags = 0;
        this.mNextAppTransitionRequests.clear();
        setAppTransitionState(2);
        WindowContainer animatingContainer = activityRecord != null ? activityRecord.getAnimatingContainer() : null;
        AnimationAdapter animation = animatingContainer != null ? animatingContainer.getAnimation() : null;
        if (animation != null) {
            uptimeMillis = animation.getStatusBarTransitionsStartTime();
        } else {
            uptimeMillis = SystemClock.uptimeMillis();
        }
        int notifyAppTransitionStartingLocked = notifyAppTransitionStartingLocked(uptimeMillis, 120L);
        if (animatingContainer != null && animatingContainer.isDexMode()) {
            z = true;
        }
        RemoteAnimationController remoteAnimationController = this.mRemoteAnimationController;
        if (remoteAnimationController != null) {
            remoteAnimationController.goodToGo(i);
        } else if ((isTaskOpenTransitOld(i) || i == 12) && animation != null && !z && this.mDisplayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() && this.mService.getRecentsAnimationController() == null) {
            new NavBarFadeAnimationController(this.mDisplayContent).fadeOutAndInSequentially(animation.getDurationHint(), null, activityRecord.getSurfaceControl());
        }
        return notifyAppTransitionStartingLocked;
    }

    public void clear() {
        clear(true);
    }

    public final void clear(boolean z) {
        this.mNextAppTransitionType = 0;
        this.mNextAppTransitionOverrideRequested = false;
        this.mNextAppTransitionAnimationsSpecs.clear();
        this.mRemoteAnimationController = null;
        this.mNextAppTransitionAnimationsSpecsFuture = null;
        this.mDefaultNextAppTransitionAnimationSpec = null;
        this.mAnimationFinishedCallback = null;
        this.mOverrideTaskTransition = false;
        this.mNextAppTransitionIsSync = false;
        if (z) {
            this.mNextAppTransitionPackage = null;
            this.mNextAppTransitionEnter = 0;
            this.mNextAppTransitionExit = 0;
            this.mNextAppTransitionBackgroundColor = 0;
        }
    }

    public void freeze() {
        boolean contains = this.mNextAppTransitionRequests.contains(7);
        RemoteAnimationController remoteAnimationController = this.mRemoteAnimationController;
        if (remoteAnimationController != null) {
            remoteAnimationController.cancelAnimation("freeze");
        }
        this.mNextAppTransitionRequests.clear();
        clear();
        setReady();
        notifyAppTransitionCancelledLocked(contains);
    }

    public final void setAppTransitionState(int i) {
        this.mAppTransitionState = i;
        updateBooster();
    }

    public void updateBooster() {
        WindowManagerService.sThreadPriorityBooster.setAppTransitionRunning(needsBoosting());
    }

    public final boolean needsBoosting() {
        int i;
        return !this.mNextAppTransitionRequests.isEmpty() || (i = this.mAppTransitionState) == 1 || i == 2 || (this.mService.getRecentsAnimationController() != null);
    }

    public void registerListenerLocked(WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mListeners.add(appTransitionListener);
    }

    public void unregisterListener(WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mListeners.remove(appTransitionListener);
    }

    public void notifyAppTransitionFinishedLocked(IBinder iBinder) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionFinishedLocked(iBinder);
        }
    }

    public final void notifyAppTransitionPendingLocked() {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionPendingLocked();
        }
    }

    public final void notifyAppTransitionCancelledLocked(boolean z) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionCancelledLocked(z);
        }
    }

    public final void notifyAppTransitionTimeoutLocked() {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionTimeoutLocked();
        }
    }

    public final int notifyAppTransitionStartingLocked(long j, long j2) {
        int i = 0;
        for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
            i |= ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i2)).onAppTransitionStartingLocked(j, j2);
        }
        return i;
    }

    public int getDefaultWindowAnimationStyleResId() {
        return this.mDefaultWindowAnimationStyleResId;
    }

    public int getAnimationStyleResId(WindowManager.LayoutParams layoutParams) {
        return this.mTransitionAnimation.getAnimationStyleResId(layoutParams);
    }

    public Animation loadAnimationSafely(Context context, int i) {
        return TransitionAnimation.loadAnimationSafely(context, i, StartingSurfaceController.TAG);
    }

    public Animation loadAnimationAttr(WindowManager.LayoutParams layoutParams, int i, int i2) {
        return this.mTransitionAnimation.loadAnimationAttr(layoutParams, i, i2);
    }

    public final void getDefaultNextAppTransitionStartRect(Rect rect) {
        Rect rect2;
        AppTransitionAnimationSpec appTransitionAnimationSpec = this.mDefaultNextAppTransitionAnimationSpec;
        if (appTransitionAnimationSpec == null || (rect2 = appTransitionAnimationSpec.rect) == null) {
            Slog.e(StartingSurfaceController.TAG, "Starting rect for app requested, but none available", new Throwable());
            rect.setEmpty();
        } else {
            rect.set(rect2);
        }
    }

    public final void putDefaultNextAppTransitionCoordinates(int i, int i2, int i3, int i4, HardwareBuffer hardwareBuffer) {
        this.mDefaultNextAppTransitionAnimationSpec = new AppTransitionAnimationSpec(-1, hardwareBuffer, new Rect(i, i2, i3 + i, i4 + i2));
    }

    public HardwareBuffer createCrossProfileAppsThumbnail(Drawable drawable, Rect rect) {
        return this.mTransitionAnimation.createCrossProfileAppsThumbnail(drawable, rect);
    }

    public Animation createCrossProfileAppsThumbnailAnimationLocked(Rect rect) {
        return this.mTransitionAnimation.createCrossProfileAppsThumbnailAnimationLocked(rect);
    }

    public Animation createThumbnailAspectScaleAnimationLocked(Rect rect, Rect rect2, HardwareBuffer hardwareBuffer, WindowContainer windowContainer, int i) {
        AppTransitionAnimationSpec appTransitionAnimationSpec = (AppTransitionAnimationSpec) this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
        TransitionAnimation transitionAnimation = this.mTransitionAnimation;
        Rect rect3 = appTransitionAnimationSpec != null ? appTransitionAnimationSpec.rect : null;
        AppTransitionAnimationSpec appTransitionAnimationSpec2 = this.mDefaultNextAppTransitionAnimationSpec;
        return transitionAnimation.createThumbnailAspectScaleAnimationLocked(rect, rect2, hardwareBuffer, i, rect3, appTransitionAnimationSpec2 != null ? appTransitionAnimationSpec2.rect : null, this.mNextAppTransitionScaleUp);
    }

    public boolean canSkipFirstFrame() {
        int i = this.mNextAppTransitionType;
        return (i == 1 || this.mNextAppTransitionOverrideRequested || i == 7 || i == 8 || this.mNextAppTransitionRequests.contains(7)) ? false : true;
    }

    public RemoteAnimationController getRemoteAnimationController() {
        return this.mRemoteAnimationController;
    }

    public Animation loadAnimation(WindowManager.LayoutParams layoutParams, int i, boolean z, int i2, int i3, Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, boolean z2, boolean z3, WindowContainer windowContainer) {
        int i4;
        int i5;
        int i6;
        Animation createAspectScaledThumbnailEnterExitAnimationLocked;
        Animation animation;
        Rect rect6;
        Rect rect7;
        Animation animation2 = null;
        if (this.mService.mExt.mExtraDisplayPolicy.shouldSkipAppTransition(this.mDisplayContent.getDisplayId())) {
            return null;
        }
        if (CoreRune.SYSFW_APP_SPEG && (windowContainer.getDisplayContent().getDisplayInfo().flags & 32768) != 0) {
            Slog.d("SPEG", "skip animation-leash of app_transition");
            return null;
        }
        boolean canCustomizeAppTransition = windowContainer.canCustomizeAppTransition();
        if (this.mNextAppTransitionOverrideRequested) {
            if (canCustomizeAppTransition || this.mOverrideTaskTransition) {
                this.mNextAppTransitionType = 1;
            } else if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 2079410261, 0, (String) null, (Object[]) null);
            }
        }
        if (isKeyguardGoingAwayTransitOld(i) && z) {
            animation2 = this.mTransitionAnimation.loadKeyguardExitAnimation(this.mNextAppTransitionFlags, i == 21);
        } else if (i != 22 && i != 33) {
            if (i == 23 && !z) {
                animation2 = this.mTransitionAnimation.loadKeyguardUnoccludeAnimation();
            } else if (i != 26) {
                if (z2 && (i == 6 || i == 8 || i == 10)) {
                    createAspectScaledThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadVoiceActivityOpenAnimation(z);
                    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 508887531, 48, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(3))});
                    }
                } else if (z2 && (i == 7 || i == 9 || i == 11)) {
                    createAspectScaledThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadVoiceActivityExitAnimation(z);
                    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 508887531, 48, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(3))});
                    }
                } else if (i == 18) {
                    TransitionAnimation transitionAnimation = this.mTransitionAnimation;
                    AppTransitionAnimationSpec appTransitionAnimationSpec = this.mDefaultNextAppTransitionAnimationSpec;
                    if (appTransitionAnimationSpec != null) {
                        rect7 = appTransitionAnimationSpec.rect;
                        rect6 = rect3;
                    } else {
                        rect6 = rect3;
                        rect7 = null;
                    }
                    createAspectScaledThumbnailEnterExitAnimationLocked = transitionAnimation.createRelaunchAnimation(rect, rect6, rect7);
                    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -1800899273, 0, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), String.valueOf(Debug.getCallers(3))});
                    }
                } else {
                    int i7 = this.mNextAppTransitionType;
                    if (i7 == 1) {
                        createAspectScaledThumbnailEnterExitAnimationLocked = getNextAppRequestedAnimation(z);
                        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -519504830, 48, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(3))});
                        }
                    } else if (i7 == 7) {
                        createAspectScaledThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadAppTransitionAnimation(this.mNextAppTransitionPackage, this.mNextAppTransitionInPlace);
                        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 1457990604, 0, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), String.valueOf(Debug.getCallers(3))});
                        }
                    } else if (i7 == 8) {
                        TransitionAnimation transitionAnimation2 = this.mTransitionAnimation;
                        AppTransitionAnimationSpec appTransitionAnimationSpec2 = this.mDefaultNextAppTransitionAnimationSpec;
                        createAspectScaledThumbnailEnterExitAnimationLocked = transitionAnimation2.createClipRevealAnimationLockedCompat(i, z, rect, rect2, appTransitionAnimationSpec2 != null ? appTransitionAnimationSpec2.rect : null);
                        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 274773837, 0, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), String.valueOf(Debug.getCallers(3))});
                        }
                    } else if (i7 == 2) {
                        TransitionAnimation transitionAnimation3 = this.mTransitionAnimation;
                        AppTransitionAnimationSpec appTransitionAnimationSpec3 = this.mDefaultNextAppTransitionAnimationSpec;
                        createAspectScaledThumbnailEnterExitAnimationLocked = transitionAnimation3.createScaleUpAnimationLockedCompat(i, z, rect, appTransitionAnimationSpec3 != null ? appTransitionAnimationSpec3.rect : null);
                        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 2028163120, 0, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), String.valueOf(z), String.valueOf(Debug.getCallers(3))});
                        }
                    } else {
                        if (i7 == 3) {
                            i4 = 192;
                            i5 = -1872288685;
                            i6 = 3;
                        } else if (i7 == 4) {
                            i4 = 192;
                            i6 = 3;
                            i5 = -1872288685;
                        } else if (i7 == 5 || i7 == 6) {
                            this.mNextAppTransitionScaleUp = i7 == 5;
                            AppTransitionAnimationSpec appTransitionAnimationSpec4 = (AppTransitionAnimationSpec) this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
                            TransitionAnimation transitionAnimation4 = this.mTransitionAnimation;
                            boolean z4 = this.mNextAppTransitionScaleUp;
                            Rect rect8 = appTransitionAnimationSpec4 != null ? appTransitionAnimationSpec4.rect : null;
                            AppTransitionAnimationSpec appTransitionAnimationSpec5 = this.mDefaultNextAppTransitionAnimationSpec;
                            createAspectScaledThumbnailEnterExitAnimationLocked = transitionAnimation4.createAspectScaledThumbnailEnterExitAnimationLocked(z, z4, i3, i, rect, rect3, rect4, rect5, z3, rect8, appTransitionAnimationSpec5 != null ? appTransitionAnimationSpec5.rect : null);
                            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -1872288685, 192, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), this.mNextAppTransitionScaleUp ? "ANIM_THUMBNAIL_ASPECT_SCALE_UP" : "ANIM_THUMBNAIL_ASPECT_SCALE_DOWN", String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(3))});
                            }
                        } else if (i7 == 9 && z) {
                            createAspectScaledThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadCrossProfileAppEnterAnimation();
                            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 1589610525, 0, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), String.valueOf(Debug.getCallers(3))});
                            }
                        } else if (isChangeTransitOld(i)) {
                            createAspectScaledThumbnailEnterExitAnimationLocked = new AlphaAnimation(1.0f, 1.0f);
                            createAspectScaledThumbnailEnterExitAnimationLocked.setDuration(336L);
                            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -1862269827, 48, (String) null, new Object[]{String.valueOf(createAspectScaledThumbnailEnterExitAnimationLocked), String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(3))});
                            }
                        } else {
                            int mapOpenCloseTransitTypes = mapOpenCloseTransitTypes(i, z);
                            if (mapOpenCloseTransitTypes != 0) {
                                ActivityRecord.CustomAppTransition customAppTransition = getCustomAppTransition(mapOpenCloseTransitTypes, windowContainer);
                                if (customAppTransition != null) {
                                    animation = loadCustomActivityAnimation(customAppTransition, z, windowContainer);
                                } else if (canCustomizeAppTransition) {
                                    animation = loadAnimationAttr(layoutParams, mapOpenCloseTransitTypes, i);
                                } else {
                                    animation = this.mTransitionAnimation.loadDefaultAnimationAttr(mapOpenCloseTransitTypes, i);
                                }
                            } else {
                                animation = null;
                            }
                            if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM && animation != null) {
                                windowContainer.mDimAnimator.createDimAnimationLayerIfNeeded((i == 6 || i == 8 || i == 10) ? 1 : 0, z, layoutParams);
                            }
                            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -57572004, 964, (String) null, new Object[]{String.valueOf(animation), Long.valueOf(mapOpenCloseTransitTypes), String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), Boolean.valueOf(canCustomizeAppTransition), String.valueOf(Debug.getCallers(3))});
                            }
                            animation2 = animation;
                        }
                        this.mNextAppTransitionScaleUp = i7 == i6;
                        HardwareBuffer appTransitionThumbnailHeader = getAppTransitionThumbnailHeader(windowContainer);
                        TransitionAnimation transitionAnimation5 = this.mTransitionAnimation;
                        boolean z5 = this.mNextAppTransitionScaleUp;
                        AppTransitionAnimationSpec appTransitionAnimationSpec6 = this.mDefaultNextAppTransitionAnimationSpec;
                        Animation createThumbnailEnterExitAnimationLockedCompat = transitionAnimation5.createThumbnailEnterExitAnimationLockedCompat(z, z5, rect, i, appTransitionThumbnailHeader, appTransitionAnimationSpec6 != null ? appTransitionAnimationSpec6.rect : null);
                        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, i5, i4, (String) null, new Object[]{String.valueOf(createThumbnailEnterExitAnimationLockedCompat), this.mNextAppTransitionScaleUp ? "ANIM_THUMBNAIL_SCALE_UP" : "ANIM_THUMBNAIL_SCALE_DOWN", String.valueOf(appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(Debug.getCallers(i6))});
                        }
                        animation2 = createThumbnailEnterExitAnimationLockedCompat;
                    }
                }
                animation2 = createAspectScaledThumbnailEnterExitAnimationLocked;
            }
        }
        setAppTransitionFinishedCallbackIfNeeded(animation2);
        return animation2;
    }

    public ActivityRecord.CustomAppTransition getCustomAppTransition(int i, WindowContainer windowContainer) {
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord == null) {
            return null;
        }
        if ((i == 5 || i == 6) && (asActivityRecord = asActivityRecord.getTask().getActivityAbove(asActivityRecord)) == null) {
            return null;
        }
        if (i == 4 || i == 5) {
            return asActivityRecord.getCustomAnimation(true);
        }
        if (i == 6 || i == 7) {
            return asActivityRecord.getCustomAnimation(false);
        }
        return null;
    }

    public final Animation loadCustomActivityAnimation(ActivityRecord.CustomAppTransition customAppTransition, boolean z, WindowContainer windowContainer) {
        int i;
        Animation loadAppTransitionAnimation = this.mTransitionAnimation.loadAppTransitionAnimation(windowContainer.asActivityRecord().packageName, z ? customAppTransition.mEnterAnim : customAppTransition.mExitAnim);
        if (loadAppTransitionAnimation != null && (i = customAppTransition.mBackgroundColor) != 0) {
            loadAppTransitionAnimation.setBackdropColor(i);
            loadAppTransitionAnimation.setShowBackdrop(true);
        }
        return loadAppTransitionAnimation;
    }

    public int getAppRootTaskClipMode() {
        return (this.mNextAppTransitionRequests.contains(5) || this.mNextAppTransitionRequests.contains(7) || this.mNextAppTransitionType == 8) ? 1 : 0;
    }

    public int getTransitFlags() {
        return this.mNextAppTransitionFlags;
    }

    public void postAnimationCallback() {
        if (this.mNextAppTransitionCallback != null) {
            this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppTransition.doAnimationCallback((IRemoteCallback) obj);
                }
            }, this.mNextAppTransitionCallback));
            this.mNextAppTransitionCallback = null;
        }
    }

    public void overridePendingAppTransition(String str, int i, int i2, int i3, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionOverrideRequested = true;
            this.mNextAppTransitionPackage = str;
            this.mNextAppTransitionEnter = i;
            this.mNextAppTransitionExit = i2;
            this.mNextAppTransitionBackgroundColor = i3;
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
            this.mOverrideTaskTransition = z;
        }
    }

    public void overridePendingAppTransitionScaleUp(int i, int i2, int i3, int i4) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 2;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, null);
            postAnimationCallback();
        }
    }

    public void overridePendingAppTransitionClipReveal(int i, int i2, int i3, int i4) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 8;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, null);
            postAnimationCallback();
        }
    }

    public void overridePendingAppTransitionThumb(HardwareBuffer hardwareBuffer, int i, int i2, IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 3 : 4;
            this.mNextAppTransitionScaleUp = z;
            putDefaultNextAppTransitionCoordinates(i, i2, 0, 0, hardwareBuffer);
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
        }
    }

    public void overridePendingAppTransitionAspectScaledThumb(HardwareBuffer hardwareBuffer, int i, int i2, int i3, int i4, IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionScaleUp = z;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, hardwareBuffer);
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
        }
    }

    public void overridePendingAppTransitionMultiThumb(AppTransitionAnimationSpec[] appTransitionAnimationSpecArr, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionScaleUp = z;
            if (appTransitionAnimationSpecArr != null) {
                for (int i = 0; i < appTransitionAnimationSpecArr.length; i++) {
                    AppTransitionAnimationSpec appTransitionAnimationSpec = appTransitionAnimationSpecArr[i];
                    if (appTransitionAnimationSpec != null) {
                        Predicate obtainPredicate = PooledLambda.obtainPredicate(new AppTransition$$ExternalSyntheticLambda2(), PooledLambda.__(Task.class), Integer.valueOf(appTransitionAnimationSpec.taskId));
                        Task task = this.mDisplayContent.getTask(obtainPredicate);
                        obtainPredicate.recycle();
                        if (task != null) {
                            this.mNextAppTransitionAnimationsSpecs.put(task.hashCode(), appTransitionAnimationSpec);
                            if (i == 0) {
                                Rect rect = appTransitionAnimationSpec.rect;
                                putDefaultNextAppTransitionCoordinates(rect.left, rect.top, rect.width(), rect.height(), appTransitionAnimationSpec.buffer);
                            }
                        }
                    }
                }
            }
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
        }
    }

    public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionAnimationsSpecsFuture = iAppTransitionAnimationSpecsFuture;
            this.mNextAppTransitionScaleUp = z;
            this.mNextAppTransitionFutureCallback = iRemoteCallback;
            if (isReady()) {
                fetchAppTransitionSpecsFromFuture();
            }
        }
    }

    public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter) {
        overridePendingAppTransitionRemote(remoteAnimationAdapter, false, false);
    }

    public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, boolean z, boolean z2) {
        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 1448683958, 3, (String) null, new Object[]{Boolean.valueOf(isTransitionSet()), String.valueOf(remoteAnimationAdapter)});
        }
        if (!isTransitionSet() || this.mNextAppTransitionIsSync) {
            return;
        }
        clear(!z2);
        this.mNextAppTransitionType = 10;
        this.mRemoteAnimationController = new RemoteAnimationController(this.mService, this.mDisplayContent, remoteAnimationAdapter, this.mHandler, z2);
        this.mNextAppTransitionIsSync = z;
    }

    public void overridePendingAppTransitionStartCrossProfileApps() {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 9;
            postAnimationCallback();
        }
    }

    public final boolean canOverridePendingAppTransition() {
        return isTransitionSet() && this.mNextAppTransitionType != 10;
    }

    public final void fetchAppTransitionSpecsFromFuture() {
        final IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = this.mNextAppTransitionAnimationsSpecsFuture;
        if (iAppTransitionAnimationSpecsFuture != null) {
            this.mNextAppTransitionAnimationsSpecsPending = true;
            this.mNextAppTransitionAnimationsSpecsFuture = null;
            this.mDefaultExecutor.execute(new Runnable() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AppTransition.this.lambda$fetchAppTransitionSpecsFromFuture$1(iAppTransitionAnimationSpecsFuture);
                }
            });
        }
    }

    public /* synthetic */ void lambda$fetchAppTransitionSpecsFromFuture$1(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture) {
        AppTransitionAnimationSpec[] appTransitionAnimationSpecArr;
        try {
            Binder.allowBlocking(iAppTransitionAnimationSpecsFuture.asBinder());
            appTransitionAnimationSpecArr = iAppTransitionAnimationSpecsFuture.get();
        } catch (RemoteException e) {
            Slog.w(StartingSurfaceController.TAG, "Failed to fetch app transition specs: " + e);
            appTransitionAnimationSpecArr = null;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mNextAppTransitionAnimationsSpecsPending = false;
                overridePendingAppTransitionMultiThumb(appTransitionAnimationSpecArr, this.mNextAppTransitionFutureCallback, null, this.mNextAppTransitionScaleUp);
                this.mNextAppTransitionFutureCallback = null;
                this.mService.requestTraversal();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mNextAppTransitionRequests=[");
        Iterator it = this.mNextAppTransitionRequests.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (z) {
                sb.append(", ");
            }
            sb.append(appTransitionToString(num.intValue()));
            z = true;
        }
        sb.append("]");
        sb.append(", mNextAppTransitionFlags=" + appTransitionFlagsToString(this.mNextAppTransitionFlags));
        return sb.toString();
    }

    public static String appTransitionOldToString(int i) {
        switch (i) {
            case -1:
                return "TRANSIT_OLD_UNSET";
            case 0:
                return "TRANSIT_OLD_NONE";
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 17:
            case 19:
            case 27:
            default:
                return "<UNKNOWN: " + i + ">";
            case 6:
                return "TRANSIT_OLD_ACTIVITY_OPEN";
            case 7:
                return "TRANSIT_OLD_ACTIVITY_CLOSE";
            case 8:
                return "TRANSIT_OLD_TASK_OPEN";
            case 9:
                return "TRANSIT_OLD_TASK_CLOSE";
            case 10:
                return "TRANSIT_OLD_TASK_TO_FRONT";
            case 11:
                return "TRANSIT_OLD_TASK_TO_BACK";
            case 12:
                return "TRANSIT_OLD_WALLPAPER_CLOSE";
            case 13:
                return "TRANSIT_OLD_WALLPAPER_OPEN";
            case 14:
                return "TRANSIT_OLD_WALLPAPER_INTRA_OPEN";
            case 15:
                return "TRANSIT_OLD_WALLPAPER_INTRA_CLOSE";
            case 16:
                return "TRANSIT_OLD_TASK_OPEN_BEHIND";
            case 18:
                return "TRANSIT_OLD_ACTIVITY_RELAUNCH";
            case 20:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY";
            case 21:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY_ON_WALLPAPER";
            case 22:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE";
            case 23:
                return "TRANSIT_OLD_KEYGUARD_UNOCCLUDE";
            case 24:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_OPEN";
            case 25:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_CLOSE";
            case 26:
                return "TRANSIT_OLD_CRASHING_ACTIVITY_CLOSE";
            case 28:
                return "TRANSIT_OLD_TASK_FRAGMENT_OPEN";
            case 29:
                return "TRANSIT_OLD_TASK_FRAGMENT_CLOSE";
            case 30:
                return "TRANSIT_OLD_TASK_FRAGMENT_CHANGE";
            case 31:
                return "TRANSIT_OLD_DREAM_ACTIVITY_OPEN";
            case 32:
                return "TRANSIT_OLD_DREAM_ACTIVITY_CLOSE";
            case 33:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE_BY_DREAM";
        }
    }

    public static String appTransitionToString(int i) {
        switch (i) {
            case 0:
                return "TRANSIT_NONE";
            case 1:
                return "TRANSIT_OPEN";
            case 2:
                return "TRANSIT_CLOSE";
            case 3:
                return "TRANSIT_TO_FRONT";
            case 4:
                return "TRANSIT_TO_BACK";
            case 5:
                return "TRANSIT_RELAUNCH";
            case 6:
                return "TRANSIT_CHANGE";
            case 7:
                return "TRANSIT_KEYGUARD_GOING_AWAY";
            case 8:
                return "TRANSIT_KEYGUARD_OCCLUDE";
            case 9:
                return "TRANSIT_KEYGUARD_UNOCCLUDE";
            default:
                return "<UNKNOWN: " + i + ">";
        }
    }

    public final String appStateToString() {
        int i = this.mAppTransitionState;
        if (i == 0) {
            return "APP_STATE_IDLE";
        }
        if (i == 1) {
            return "APP_STATE_READY";
        }
        if (i == 2) {
            return "APP_STATE_RUNNING";
        }
        if (i == 3) {
            return "APP_STATE_TIMEOUT";
        }
        return "unknown state=" + this.mAppTransitionState;
    }

    public final String transitTypeToString() {
        switch (this.mNextAppTransitionType) {
            case 0:
                return "NEXT_TRANSIT_TYPE_NONE";
            case 1:
                return "NEXT_TRANSIT_TYPE_CUSTOM";
            case 2:
                return "NEXT_TRANSIT_TYPE_SCALE_UP";
            case 3:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_UP";
            case 4:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_DOWN";
            case 5:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_UP";
            case 6:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_DOWN";
            case 7:
                return "NEXT_TRANSIT_TYPE_CUSTOM_IN_PLACE";
            case 8:
            default:
                return "unknown type=" + this.mNextAppTransitionType;
            case 9:
                return "NEXT_TRANSIT_TYPE_OPEN_CROSS_PROFILE_APPS";
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        sFlagToString = arrayList;
        arrayList.add(new Pair(1, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE"));
        arrayList.add(new Pair(2, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION"));
        arrayList.add(new Pair(4, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER"));
        arrayList.add(new Pair(8, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_SUBTLE_ANIMATION"));
        arrayList.add(new Pair(512, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_LAUNCHER_WITH_IN_WINDOW_ANIMATIONS"));
        arrayList.add(new Pair(16, "TRANSIT_FLAG_APP_CRASHED"));
        arrayList.add(new Pair(32, "TRANSIT_FLAG_OPEN_BEHIND"));
    }

    public static String appTransitionFlagsToString(int i) {
        StringBuilder sb = new StringBuilder();
        Iterator it = sFlagToString.iterator();
        String str = "";
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if ((((Integer) pair.first).intValue() & i) != 0) {
                sb.append(str);
                sb.append((String) pair.second);
                str = " | ";
            }
        }
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mAppTransitionState);
        protoOutputStream.write(1159641169922L, this.mLastUsedAppTransition);
        protoOutputStream.end(start);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println(this);
        printWriter.print(str);
        printWriter.print("mAppTransitionState=");
        printWriter.println(appStateToString());
        if (this.mNextAppTransitionType != 0) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionType=");
            printWriter.println(transitTypeToString());
        }
        if (this.mNextAppTransitionOverrideRequested || this.mNextAppTransitionType == 1) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionPackage=");
            printWriter.println(this.mNextAppTransitionPackage);
            printWriter.print(str);
            printWriter.print("mNextAppTransitionEnter=0x");
            printWriter.print(Integer.toHexString(this.mNextAppTransitionEnter));
            printWriter.print(" mNextAppTransitionExit=0x");
            printWriter.println(Integer.toHexString(this.mNextAppTransitionExit));
            printWriter.print(" mNextAppTransitionBackgroundColor=0x");
            printWriter.println(Integer.toHexString(this.mNextAppTransitionBackgroundColor));
        }
        switch (this.mNextAppTransitionType) {
            case 2:
                getDefaultNextAppTransitionStartRect(this.mTmpRect);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionStartX=");
                printWriter.print(this.mTmpRect.left);
                printWriter.print(" mNextAppTransitionStartY=");
                printWriter.println(this.mTmpRect.top);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionStartWidth=");
                printWriter.print(this.mTmpRect.width());
                printWriter.print(" mNextAppTransitionStartHeight=");
                printWriter.println(this.mTmpRect.height());
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                printWriter.print(str);
                printWriter.print("mDefaultNextAppTransitionAnimationSpec=");
                printWriter.println(this.mDefaultNextAppTransitionAnimationSpec);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionAnimationsSpecs=");
                printWriter.println(this.mNextAppTransitionAnimationsSpecs);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionScaleUp=");
                printWriter.println(this.mNextAppTransitionScaleUp);
                break;
            case 7:
                printWriter.print(str);
                printWriter.print("mNextAppTransitionPackage=");
                printWriter.println(this.mNextAppTransitionPackage);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionInPlace=0x");
                printWriter.print(Integer.toHexString(this.mNextAppTransitionInPlace));
                break;
        }
        if (this.mNextAppTransitionCallback != null) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionCallback=");
            printWriter.println(this.mNextAppTransitionCallback);
        }
        if (this.mLastUsedAppTransition != 0) {
            printWriter.print(str);
            printWriter.print("mLastUsedAppTransition=");
            printWriter.println(appTransitionOldToString(this.mLastUsedAppTransition));
            printWriter.print(str);
            printWriter.print("mLastOpeningApp=");
            printWriter.println(this.mLastOpeningApp);
            printWriter.print(str);
            printWriter.print("mLastClosingApp=");
            printWriter.println(this.mLastClosingApp);
            printWriter.print(str);
            printWriter.print("mLastChangingApp=");
            printWriter.println(this.mLastChangingApp);
        }
    }

    public boolean prepareAppTransition(int i, int i2) {
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        this.mNextAppTransitionRequests.add(Integer.valueOf(i));
        this.mNextAppTransitionFlags |= i2;
        updateBooster();
        removeAppTransitionTimeoutCallbacks();
        this.mHandler.postDelayed(this.mHandleAppTransitionTimeoutRunnable, 5000L);
        if (ProtoLogCache.WM_FORCE_DEBUG_APP_TRANSITIONS_enabled) {
            ProtoLogImpl.w(ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS, 114209416, 4, "Prepare app transition: %s, displayId: %d Callers=%s", new Object[]{String.valueOf(this), Long.valueOf(this.mDisplayContent.getDisplayId()), String.valueOf(Debug.getCallers(5))});
        }
        return prepare();
    }

    public static boolean isTaskTransitOld(int i) {
        return isTaskOpenTransitOld(i) || isTaskCloseTransitOld(i);
    }

    public int getKeyguardTransition() {
        if (this.mNextAppTransitionRequests.indexOf(7) != -1) {
            return 7;
        }
        int indexOf = this.mNextAppTransitionRequests.indexOf(9);
        int indexOf2 = this.mNextAppTransitionRequests.indexOf(8);
        if (indexOf == -1 && indexOf2 == -1) {
            return 0;
        }
        if (indexOf == -1 || indexOf >= indexOf2) {
            return indexOf != -1 ? 9 : 8;
        }
        return 0;
    }

    public int getFirstAppTransition() {
        for (int i = 0; i < this.mNextAppTransitionRequests.size(); i++) {
            int intValue = ((Integer) this.mNextAppTransitionRequests.get(i)).intValue();
            if (intValue != 0 && !isKeyguardTransit(intValue)) {
                return intValue;
            }
        }
        return 0;
    }

    public boolean containsTransitRequest(int i) {
        return this.mNextAppTransitionRequests.contains(Integer.valueOf(i));
    }

    /* renamed from: handleAppTransitionTimeout */
    public final void lambda$new$0() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mDisplayContent;
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                notifyAppTransitionTimeoutLocked();
                if (isTransitionSet() || !displayContent.mOpeningApps.isEmpty() || !displayContent.mClosingApps.isEmpty() || !displayContent.mChangingContainers.isEmpty()) {
                    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 344795667, 349, (String) null, new Object[]{Long.valueOf(displayContent.getDisplayId()), Boolean.valueOf(displayContent.mAppTransition.isTransitionSet()), Long.valueOf(displayContent.mOpeningApps.size()), Long.valueOf(displayContent.mClosingApps.size()), Long.valueOf(displayContent.mChangingContainers.size())});
                    }
                    Slog.v(StartingSurfaceController.TAG, "*** APP TRANSITION TIMEOUT. displayId=" + displayContent.getDisplayId());
                    if (isTransitionSet()) {
                        Slog.v(StartingSurfaceController.TAG, "* transit=" + this + " state=" + appStateToString());
                    }
                    if (!displayContent.mOpeningApps.isEmpty()) {
                        Iterator it = displayContent.mOpeningApps.iterator();
                        while (it.hasNext()) {
                            final ActivityRecord activityRecord = (ActivityRecord) it.next();
                            if (!activityRecord.allDrawn) {
                                Slog.v(StartingSurfaceController.TAG, "* " + activityRecord + ", allDrawn=false");
                                activityRecord.forAllWindows(new Consumer() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        AppTransition.lambda$handleAppTransitionTimeout$2(ActivityRecord.this, (WindowState) obj);
                                    }
                                }, true);
                            }
                        }
                    }
                    if (!displayContent.mUnknownAppVisibilityController.allResolved()) {
                        Slog.v(StartingSurfaceController.TAG, "* Unknown apps: " + displayContent.mUnknownAppVisibilityController.getDebugMessage());
                    }
                    setTimeout();
                    this.mService.mWindowPlacerLocked.performSurfacePlacement();
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public static /* synthetic */ void lambda$handleAppTransitionTimeout$2(ActivityRecord activityRecord, WindowState windowState) {
        if (activityRecord.equals(windowState.mToken)) {
            StringWriter stringWriter = new StringWriter();
            windowState.dump(new PrintWriter((Writer) stringWriter, true), "\t* ", true);
            Slog.v(StartingSurfaceController.TAG, stringWriter.toString());
        }
    }

    public static void doAnimationCallback(IRemoteCallback iRemoteCallback) {
        try {
            iRemoteCallback.sendResult((Bundle) null);
        } catch (RemoteException unused) {
        }
    }

    public final void setAppTransitionFinishedCallbackIfNeeded(Animation animation) {
        IRemoteCallback iRemoteCallback = this.mAnimationFinishedCallback;
        if (iRemoteCallback == null || animation == null) {
            return;
        }
        animation.setAnimationListener(new AnonymousClass1(iRemoteCallback));
    }

    /* renamed from: com.android.server.wm.AppTransition$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements Animation.AnimationListener {
        public final /* synthetic */ IRemoteCallback val$callback;

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass1(IRemoteCallback iRemoteCallback) {
            this.val$callback = iRemoteCallback;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            AppTransition.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.wm.AppTransition$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppTransition.doAnimationCallback((IRemoteCallback) obj);
                }
            }, this.val$callback));
        }
    }

    public void removeAppTransitionTimeoutCallbacks() {
        this.mHandler.removeCallbacks(this.mHandleAppTransitionTimeoutRunnable);
    }
}

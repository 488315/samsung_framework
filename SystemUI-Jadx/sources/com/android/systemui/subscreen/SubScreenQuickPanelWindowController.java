package com.android.systemui.subscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsCoverAnimator;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.subscreen.dagger.SubScreenQuickPanelComponent;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubScreenQuickPanelWindowController implements PanelScreenShotLogger.LogProvider, CommandQueue.Callbacks {
    public static int COVER_DISPLAY = 1;
    public final QsCoverAnimator mAnimator;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public float mExpandedFraction;
    public float mExpandedHeight;
    public FlingAnimationUtils mFlingAnimationUtils;
    public WindowManager.LayoutParams mLp;
    public float mMaxExpandedHeight;
    public boolean mPanelExpanded;
    public boolean mPanelFullyExpanded;
    public ValueAnimator mPanelHeightAnimator;
    public final SecPanelLogger mPanelLogger;
    public final SecQSPanelResourcePicker mPanelResourcePicker;
    public View mQSPanel;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public final SubScreenQuickPanelComponent.Factory mSubScreenComponent;
    public final SubScreenQSEventHandler mSubScreenQSEventHandler;
    public SubScreenQuickPanelWindowView mSubScreenQsWindowView;
    public SubRoom.StateChangeListener mSubScreenStateChangedListener;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public final SysUiState mSysUiState;
    public WindowManager mWindowManager;
    public int mDisabled1 = 0;
    public int mDisabled2 = 0;
    public boolean mPanelDisabled = false;
    public boolean mFolderOpened = false;
    public boolean mIsAnnounced = false;
    public final AnonymousClass1 mFoldStateChangedListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = SubScreenQuickPanelWindowController.this;
            ((SecPanelLoggerImpl) subScreenQuickPanelWindowController.mPanelLogger).addCoverPanelStateLog(KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0.m("onFolderStateChanged: ", z));
            if (z && subScreenQuickPanelWindowController.mPanelExpanded) {
                subScreenQuickPanelWindowController.collapsePanel();
            }
            subScreenQuickPanelWindowController.mFolderOpened = z;
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class PanelExpandedFractionProvider implements SubRoom {
        public /* synthetic */ PanelExpandedFractionProvider(SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final View getView(Context context) {
            return null;
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final Bundle request(String str, Bundle bundle) {
            return null;
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
            SubScreenQuickPanelWindowController.this.mSubScreenStateChangedListener = stateChangeListener;
        }

        private PanelExpandedFractionProvider() {
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final void removeListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.subscreen.SubScreenQuickPanelWindowController$1] */
    public SubScreenQuickPanelWindowController(SubscreenQsPanelController subscreenQsPanelController, SecPanelLogger secPanelLogger, CommandQueue commandQueue, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, DisplayLifecycle displayLifecycle, DisplayManager displayManager, SysUiState sysUiState, ScreenRecordingStateProvider screenRecordingStateProvider, DelayableExecutor delayableExecutor, Context context, FlingAnimationUtils.Builder builder, DeviceStateManager deviceStateManager, NavigationModeController navigationModeController, SubScreenQuickPanelComponent.Factory factory, SecQSPanelResourcePicker secQSPanelResourcePicker, SubscreenUtil subscreenUtil, WakefulnessLifecycle wakefulnessLifecycle) {
        Context context2;
        final int i = 0;
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            return;
        }
        this.mSubscreenQsPanelController = subscreenQsPanelController;
        this.mPanelLogger = secPanelLogger;
        this.mCommandQueue = commandQueue;
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        subscreenQsPanelController.init();
        this.mDisplayLifecycle = displayLifecycle;
        this.mSysUiState = sysUiState;
        this.mSubScreenComponent = factory;
        this.mPanelResourcePicker = secQSPanelResourcePicker;
        Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        final int i2 = 1;
        if (displays.length > 1) {
            context2 = context.createDisplayContext(displays[1]);
        } else {
            ((SecPanelLoggerImpl) secPanelLogger).addCoverPanelStateLog("initContext: fail to get sub-display");
            context2 = context;
        }
        this.mContext = context2;
        int displayId = context2.getDisplayId();
        ((SecPanelLoggerImpl) secPanelLogger).addCoverPanelStateLog(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("initCoverDisplay: ", displayId));
        COVER_DISPLAY = displayId;
        builder.reset();
        builder.mMaxLengthSeconds = 0.5f;
        builder.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtils = builder.build();
        this.mAnimator = new QsCoverAnimator(context2, subscreenQsPanelController);
        subscreenUtil.mSubScreenQuickPanelWindowController = this;
        Runnable runnable = new Runnable(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        ValueAnimator valueAnimator = this.f$0.mPanelHeightAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                            return;
                        }
                        return;
                    case 1:
                        this.f$0.collapsePanel();
                        return;
                    default:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.checkNotInitialized()) {
                            ((SecPanelLoggerImpl) subScreenQuickPanelWindowController.mPanelLogger).addCoverPanelStateLog("instantCollapse");
                            subScreenQuickPanelWindowController.updatePanelExpansion(0.0f, false);
                            return;
                        }
                        return;
                }
            }
        };
        Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        ValueAnimator valueAnimator = this.f$0.mPanelHeightAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                            return;
                        }
                        return;
                    case 1:
                        this.f$0.collapsePanel();
                        return;
                    default:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.checkNotInitialized()) {
                            ((SecPanelLoggerImpl) subScreenQuickPanelWindowController.mPanelLogger).addCoverPanelStateLog("instantCollapse");
                            subScreenQuickPanelWindowController.updatePanelExpansion(0.0f, false);
                            return;
                        }
                        return;
                }
            }
        };
        final int i3 = 2;
        Runnable runnable3 = new Runnable(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        ValueAnimator valueAnimator = this.f$0.mPanelHeightAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                            return;
                        }
                        return;
                    case 1:
                        this.f$0.collapsePanel();
                        return;
                    default:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.checkNotInitialized()) {
                            ((SecPanelLoggerImpl) subScreenQuickPanelWindowController.mPanelLogger).addCoverPanelStateLog("instantCollapse");
                            subScreenQuickPanelWindowController.updatePanelExpansion(0.0f, false);
                            return;
                        }
                        return;
                }
            }
        };
        Supplier supplier = new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda3
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i) {
                    case 0:
                        return this.f$0.mContext;
                    case 1:
                        return this.f$0.mSubScreenQsWindowView;
                    case 2:
                        return this.f$0.mWindowManager;
                    default:
                        return this.f$0.mLp;
                }
            }
        };
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SubScreenQuickPanelWindowController.this.createPanelHeightAnimatorAndRun(((Float) obj).floatValue(), ((Boolean) obj2).booleanValue());
            }
        };
        BooleanSupplier booleanSupplier = new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda5
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i) {
                    case 0:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.mPanelDisabled && !subScreenQuickPanelWindowController.mFolderOpened) {
                            return false;
                        }
                        return true;
                    case 1:
                        return this.f$0.mPanelFullyExpanded;
                    default:
                        return this.f$0.mPanelExpanded;
                }
            }
        };
        DoubleSupplier doubleSupplier = new DoubleSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda6
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                float f;
                switch (i) {
                    case 0:
                        f = this.f$0.mExpandedHeight;
                        break;
                    default:
                        f = this.f$0.mMaxExpandedHeight;
                        break;
                }
                return f;
            }
        };
        final int i4 = 3;
        Supplier supplier2 = new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda3
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i4) {
                    case 0:
                        return this.f$0.mContext;
                    case 1:
                        return this.f$0.mSubScreenQsWindowView;
                    case 2:
                        return this.f$0.mWindowManager;
                    default:
                        return this.f$0.mLp;
                }
            }
        };
        final int i5 = 1;
        DoubleSupplier doubleSupplier2 = new DoubleSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda6
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                float f;
                switch (i5) {
                    case 0:
                        f = this.f$0.mExpandedHeight;
                        break;
                    default:
                        f = this.f$0.mMaxExpandedHeight;
                        break;
                }
                return f;
            }
        };
        final int i6 = 2;
        BooleanSupplier booleanSupplier2 = new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda5
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i6) {
                    case 0:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.mPanelDisabled && !subScreenQuickPanelWindowController.mFolderOpened) {
                            return false;
                        }
                        return true;
                    case 1:
                        return this.f$0.mPanelFullyExpanded;
                    default:
                        return this.f$0.mPanelExpanded;
                }
            }
        };
        final int i7 = 1;
        final int i8 = 2;
        this.mSubScreenQSEventHandler = new SubScreenQSEventHandler(runnable, runnable2, runnable3, supplier, biConsumer, deviceStateManager, booleanSupplier, displayManager, doubleSupplier, supplier2, delayableExecutor, doubleSupplier2, navigationModeController, booleanSupplier2, new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda5
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i7) {
                    case 0:
                        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                        if (!subScreenQuickPanelWindowController.mPanelDisabled && !subScreenQuickPanelWindowController.mFolderOpened) {
                            return false;
                        }
                        return true;
                    case 1:
                        return this.f$0.mPanelFullyExpanded;
                    default:
                        return this.f$0.mPanelExpanded;
                }
            }
        }, secPanelLogger, secQSPanelResourcePicker, screenRecordingStateProvider, new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda3
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i7) {
                    case 0:
                        return this.f$0.mContext;
                    case 1:
                        return this.f$0.mSubScreenQsWindowView;
                    case 2:
                        return this.f$0.mWindowManager;
                    default:
                        return this.f$0.mLp;
                }
            }
        }, sysUiState, new DoubleConsumer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                SubScreenQuickPanelWindowController.this.updatePanelExpansion((float) d, false);
            }
        }, wakefulnessLifecycle, new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda3
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i8) {
                    case 0:
                        return this.f$0.mContext;
                    case 1:
                        return this.f$0.mSubScreenQsWindowView;
                    case 2:
                        return this.f$0.mWindowManager;
                    default:
                        return this.f$0.mLp;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateCollapsePanels(int i, boolean z) {
        if (checkNotInitialized()) {
            return;
        }
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("animateCollapsePanels");
        if (this.mPanelExpanded) {
            collapsePanel();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandSettingsPanel(String str) {
        if (checkNotInitialized()) {
            return;
        }
        SecPanelLogger secPanelLogger = this.mPanelLogger;
        ((SecPanelLoggerImpl) secPanelLogger).addCoverPanelStateLog("animateExpandSettingsPanel");
        if (!this.mPanelExpanded && !checkNotInitialized()) {
            ((SecPanelLoggerImpl) secPanelLogger).addCoverPanelStateLog("expandPanel");
            createPanelHeightAnimatorAndRun(0.0f, true);
        }
    }

    public final boolean checkNotInitialized() {
        if (this.mSubScreenQsWindowView == null) {
            return true;
        }
        return false;
    }

    public final void collapsePanel() {
        if (checkNotInitialized()) {
            return;
        }
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("collapsePanel");
        createPanelHeightAnimatorAndRun(0.0f, false);
    }

    public final void createPanelHeightAnimatorAndRun(float f, final boolean z) {
        float f2;
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("createPanelHeightAnimatorAndRun vel: " + f + " expand: " + z);
        float f3 = 0.0f;
        if (z) {
            f2 = this.mMaxExpandedHeight;
        } else {
            f2 = 0.0f;
        }
        final boolean z2 = this.mPanelFullyExpanded;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mExpandedHeight, f2);
        this.mPanelHeightAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                boolean z3;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = SubScreenQuickPanelWindowController.this;
                boolean z4 = z;
                boolean z5 = z2;
                subScreenQuickPanelWindowController.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (!z4 && z5) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                subScreenQuickPanelWindowController.updatePanelExpansion(floatValue, z3);
            }
        });
        this.mPanelHeightAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubScreenQuickPanelWindowController.this.mPanelHeightAnimator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
        if (flingAnimationUtils != null) {
            ValueAnimator valueAnimator = this.mPanelHeightAnimator;
            float f4 = this.mExpandedHeight;
            if (z) {
                f3 = this.mMaxExpandedHeight;
            }
            float f5 = f3;
            flingAnimationUtils.apply(valueAnimator, f4, f5, f, Math.abs(f5 - f4));
        }
        this.mPanelHeightAnimator.start();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (checkNotInitialized()) {
            return;
        }
        boolean z2 = true;
        if (i == 1) {
            return;
        }
        this.mRemoteInputQuickSettingsDisabler.getClass();
        int i4 = this.mDisabled1 ^ i2;
        this.mDisabled1 = i2;
        int i5 = this.mDisabled2 ^ i3;
        this.mDisabled2 = i3;
        StringBuilder sb = new StringBuilder();
        if ((i4 & 65536) != 0 && (i2 & 65536) != 0) {
            sb.append("DISABLE_EXPAND ");
            collapsePanel();
        }
        if ((i5 & 1) != 0) {
            sb.append("DISABLE2_QUICK_SETTINGS ");
            collapsePanel();
        }
        if ((i5 & 4) != 0 && (i3 & 4) != 0) {
            sb.append("DISABLE2_NOTIFICATION_SHADE");
            collapsePanel();
        }
        if ((i2 & 65536) == 0 && (i3 & 1) == 0 && (i3 & 4) == 0) {
            z2 = false;
        }
        if (this.mPanelDisabled != z2) {
            this.mPanelDisabled = z2;
            StringBuilder sb2 = new StringBuilder("Disable ");
            int length = sb.length();
            CharSequence charSequence = sb;
            if (length == 0) {
                charSequence = "released";
            }
            sb2.append((Object) charSequence);
            ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog(sb2.toString());
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SubScreenQuickPanelWindowController ============================================= ");
        arrayList.add("    mMaxExpandedHeight = " + this.mMaxExpandedHeight);
        arrayList.add("    mExpandedHeight = " + this.mExpandedHeight);
        arrayList.add("    mPanelExpanded = " + this.mPanelExpanded);
        arrayList.add("    mPanelFullyExpanded = " + this.mPanelFullyExpanded);
        arrayList.add("    mPanelDisabled = " + this.mPanelDisabled);
        arrayList.add("    mFolderOpened = " + this.mFolderOpened);
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        if (checkNotInitialized()) {
            return;
        }
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("onCameraLaunchGestureDetected");
        if (this.mPanelExpanded) {
            collapsePanel();
        }
    }

    public final void updatePanelExpansion(float f, boolean z) {
        boolean z2;
        boolean z3;
        String str;
        int i;
        if (f < 0.0f || f > this.mMaxExpandedHeight) {
            f = Math.min(this.mMaxExpandedHeight, Math.max(0.0f, f));
        }
        this.mExpandedHeight = f;
        boolean z4 = true;
        if (f > 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 != this.mPanelExpanded) {
            this.mPanelExpanded = z2;
            if (z2) {
                str = "com.android.systemui.subscreen.EXPANDED";
            } else {
                str = "com.android.systemui.subscreen.COLLAPSED";
            }
            final Intent intent = new Intent(str);
            ((UiOffloadThread) Dependency.get(UiOffloadThread.class)).execute(new Runnable() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = SubScreenQuickPanelWindowController.this;
                    subScreenQuickPanelWindowController.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
            });
            if (!checkNotInitialized()) {
                SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = this.mSubScreenQsWindowView;
                if (z2) {
                    i = 0;
                } else {
                    i = 8;
                }
                subScreenQuickPanelWindowView.setVisibility(i);
            }
        }
        float f2 = this.mExpandedHeight / this.mMaxExpandedHeight;
        this.mExpandedFraction = f2;
        QsCoverAnimator qsCoverAnimator = this.mAnimator;
        if (qsCoverAnimator != null) {
            TouchAnimator touchAnimator = qsCoverAnimator.mPanelViewTranslationAnimator;
            if (touchAnimator != null) {
                touchAnimator.setPosition(f2);
            }
            if (z) {
                qsCoverAnimator.mPanelViewAlphaAnimator.setPosition(f2);
            } else {
                qsCoverAnimator.mQSPanel.setAlpha(1.0f);
            }
        }
        String str2 = "mExpandedFraction: " + this.mExpandedFraction;
        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) this.mPanelLogger;
        secPanelLoggerImpl.addCoverPanelStateLog(str2);
        if (this.mExpandedFraction == 0.0f) {
            secPanelLoggerImpl.addCoverPanelStateLog("onSubQSPanelCollapsed");
            this.mIsAnnounced = false;
        }
        if (this.mExpandedFraction == 1.0f && !checkNotInitialized()) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2000");
            secPanelLoggerImpl.addCoverPanelStateLog("onSubQSPanelFullyExpanded");
            if (!this.mIsAnnounced) {
                this.mSubScreenQsWindowView.announceForAccessibility(this.mContext.getString(R.string.subscreen_accessibility_quick_settings));
            }
            this.mIsAnnounced = true;
        }
        if (this.mExpandedFraction == 1.0f) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mPanelFullyExpanded = z3;
        if (this.mSubScreenStateChangedListener != null) {
            Bundle bundle = new Bundle();
            bundle.putFloat(SubRoom.EXTRA_KEY_QUICK_PANEL_SWIPE_FRACTION, this.mExpandedFraction);
            this.mSubScreenStateChangedListener.onStateChanged(bundle);
        }
        SysUiState sysUiState = this.mSysUiState;
        if (sysUiState != null) {
            if (this.mExpandedFraction <= 0.0f) {
                z4 = false;
            }
            sysUiState.setFlag(2048L, z4);
            sysUiState.commitUpdate(COVER_DISPLAY);
        }
    }
}

package com.android.systemui.statusbar.events;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.DeviceConfig;
import android.util.Log;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SystemStatusAnimationSchedulerImpl implements SystemStatusAnimationScheduler, DesktopManager.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SystemEventChipAnimationController chipAnimationController;
    public final SystemEventCoordinator coordinator;
    public final CoroutineScope coroutineScope;
    public StatusEvent currentlyDisplayedEvent;
    public StandaloneCoroutine currentlyRunningAnimationJob;
    public final DesktopManager desktopManager;
    public StandaloneCoroutine eventCancellationJob;
    public boolean hasPersistentDot;
    public boolean isKeyguardVisible;
    public final KeyguardStateController keyguardStateController;
    public boolean statusBarHidden;
    public final StatusBarWindowController statusBarWindowController;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final SystemClock systemClock;
    public final StateFlowImpl scheduledEvent = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl animationState = StateFlowKt.MutableStateFlow(0);
    public final Set listeners = new LinkedHashSet();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1", f = "SystemStatusAnimationSchedulerImpl.kt", l = {139}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        @DebugMetadata(c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1$1", f = "SystemStatusAnimationSchedulerImpl.kt", l = {}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1$1, reason: invalid class name and collision with other inner class name */
        /* loaded from: classes2.dex */
        public final class C00771 extends SuspendLambda implements Function3 {
            /* synthetic */ int I$0;
            /* synthetic */ Object L$0;
            int label;

            public C00771(Continuation<? super C00771> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int intValue = ((Number) obj).intValue();
                C00771 c00771 = new C00771((Continuation) obj3);
                c00771.I$0 = intValue;
                c00771.L$0 = (StatusEvent) obj2;
                return c00771.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    int i = this.I$0;
                    return new Pair(new Integer(i), (StatusEvent) this.L$0);
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } else {
                ResultKt.throwOnFailure(obj);
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                Flow debounce = FlowKt.debounce(SystemStatusAnimationSchedulerKt.DEBOUNCE_DELAY, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(systemStatusAnimationSchedulerImpl.animationState, systemStatusAnimationSchedulerImpl.scheduledEvent, new C00771(null)));
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        int intValue = ((Number) pair.component1()).intValue();
                        StatusEvent statusEvent = (StatusEvent) pair.component2();
                        if (intValue == 1 && statusEvent != null) {
                            int i2 = SystemStatusAnimationSchedulerImpl.$r8$clinit;
                            SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl3 = SystemStatusAnimationSchedulerImpl.this;
                            systemStatusAnimationSchedulerImpl3.getClass();
                            Assert.isMainThread();
                            systemStatusAnimationSchedulerImpl3.hasPersistentDot = statusEvent.getForceVisible();
                            if (!statusEvent.getShowAnimation() && statusEvent.getForceVisible()) {
                                systemStatusAnimationSchedulerImpl3.animationState.setValue(5);
                                systemStatusAnimationSchedulerImpl3.notifyTransitionToPersistentDot();
                            } else {
                                systemStatusAnimationSchedulerImpl3.currentlyDisplayedEvent = statusEvent;
                                systemStatusAnimationSchedulerImpl3.chipAnimationController.prepareChipAnimation(statusEvent.getViewCreator());
                                systemStatusAnimationSchedulerImpl3.currentlyRunningAnimationJob = BuildersKt.launch$default(systemStatusAnimationSchedulerImpl3.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(systemStatusAnimationSchedulerImpl3, null), 3);
                            }
                            systemStatusAnimationSchedulerImpl3.scheduledEvent.setValue(null);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (debounce.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SystemStatusAnimationSchedulerImpl(SystemEventCoordinator systemEventCoordinator, SystemEventChipAnimationController systemEventChipAnimationController, StatusBarWindowController statusBarWindowController, DumpManager dumpManager, SystemClock systemClock, CoroutineScope coroutineScope, DesktopManager desktopManager, KeyguardStateController keyguardStateController, StatusBarWindowStateController statusBarWindowStateController) {
        this.coordinator = systemEventCoordinator;
        this.chipAnimationController = systemEventChipAnimationController;
        this.statusBarWindowController = statusBarWindowController;
        this.systemClock = systemClock;
        this.coroutineScope = coroutineScope;
        this.desktopManager = desktopManager;
        this.keyguardStateController = keyguardStateController;
        this.statusBarWindowStateController = statusBarWindowStateController;
        systemEventCoordinator.scheduler = this;
        dumpManager.registerCriticalDumpable("SystemStatusAnimationSchedulerImpl", this);
        ((DesktopManagerImpl) desktopManager).registerCallback(this);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    public static final void access$runChipDisappearAnimation(final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl) {
        AnimatorSet notifyTransitionToPersistentDot;
        systemStatusAnimationSchedulerImpl.getClass();
        Assert.isMainThread();
        boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
        ArrayList arrayList = new ArrayList();
        if (!systemStatusAnimationSchedulerImpl.isKeyguardVisible) {
            Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
            while (it.hasNext()) {
                Animator onSystemEventAnimationFinish = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z);
                if (onSystemEventAnimationFinish != null) {
                    arrayList.add(onSystemEventAnimationFinish);
                }
            }
            arrayList.add(systemStatusAnimationSchedulerImpl.chipAnimationController.onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z));
        }
        if (systemStatusAnimationSchedulerImpl.hasPersistentDot && (notifyTransitionToPersistentDot = systemStatusAnimationSchedulerImpl.notifyTransitionToPersistentDot()) != null) {
            arrayList.add(notifyTransitionToPersistentDot);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        systemStatusAnimationSchedulerImpl.animationState.setValue(4);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipDisappearAnimation$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd$1(Animator animator) {
                int i;
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl2.animationState;
                if (systemStatusAnimationSchedulerImpl2.hasPersistentDot) {
                    i = 5;
                } else if (systemStatusAnimationSchedulerImpl2.scheduledEvent.getValue() != null) {
                    i = 1;
                } else {
                    i = 0;
                }
                stateFlowImpl.setValue(Integer.valueOf(i));
                systemStatusAnimationSchedulerImpl2.statusBarWindowController.setForceStatusBarVisible(false);
            }
        });
        animatorSet.start();
        systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent = null;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        SystemStatusAnimationCallback systemStatusAnimationCallback = (SystemStatusAnimationCallback) obj;
        Assert.isMainThread();
        Set set = this.listeners;
        if (set.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            systemEventCoordinator.privacyController.addCallback(systemEventCoordinator.privacyStateListener);
        }
        set.add(systemStatusAnimationCallback);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Scheduled event: " + this.scheduledEvent.getValue());
        printWriter.println("Currently displayed event: " + this.currentlyDisplayedEvent);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("Has persistent privacy dot: ", this.hasPersistentDot, printWriter);
        printWriter.println("Animation state: " + this.animationState.getValue());
        printWriter.println("Listeners:");
        Set set = this.listeners;
        if (set.isEmpty()) {
            printWriter.println("(none)");
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            printWriter.println("  " + ((SystemStatusAnimationCallback) it.next()));
        }
    }

    public final AnimatorSet notifyHidePersistentDot(boolean z) {
        Assert.isMainThread();
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((SystemStatusAnimationCallback) it.next()).onHidePersistentDot(z);
        }
        if (!arrayList.isEmpty()) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            return animatorSet;
        }
        return null;
    }

    public final AnimatorSet notifyTransitionToPersistentDot() {
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (true) {
            String str = null;
            if (!it.hasNext()) {
                break;
            }
            SystemStatusAnimationCallback systemStatusAnimationCallback = (SystemStatusAnimationCallback) it.next();
            StatusEvent statusEvent = this.currentlyDisplayedEvent;
            if (statusEvent != null) {
                str = statusEvent.getContentDescription();
            }
            systemStatusAnimationCallback.onSystemStatusAnimationTransitionToPersistentDot(str);
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    @Override // com.android.systemui.util.DesktopManager.Callback
    public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        if (semDesktopModeState == null) {
            return;
        }
        int state = semDesktopModeState.getState();
        int enabled = semDesktopModeState.getEnabled();
        int displayType = semDesktopModeState.getDisplayType();
        if (state == 50 && displayType == 101) {
            if (enabled == 4) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$onDesktopModeStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                        int i = SystemStatusAnimationSchedulerImpl.$r8$clinit;
                        systemStatusAnimationSchedulerImpl.notifyHidePersistentDot(true);
                    }
                });
            } else if (this.hasPersistentDot) {
                notifyTransitionToPersistentDot();
            }
        }
    }

    public final void onStatusEvent(StatusEvent statusEvent) {
        boolean z;
        int i;
        boolean z2;
        StatusEvent statusEvent2;
        Assert.isMainThread();
        ((SystemClockImpl) this.systemClock).getClass();
        boolean z3 = false;
        if (android.os.SystemClock.uptimeMillis() - Process.getStartUptimeMillis() < 5000) {
            z = true;
        } else {
            z = false;
        }
        if ((z && !statusEvent.getForceVisible()) || !DeviceConfig.getBoolean("privacy", "enable_immersive_indicator", true)) {
            return;
        }
        if (((DesktopManagerImpl) this.desktopManager).isStandalone()) {
            if (statusEvent.getForceVisible()) {
                this.hasPersistentDot = true;
                return;
            }
            return;
        }
        int priority = statusEvent.getPriority();
        StateFlowImpl stateFlowImpl = this.scheduledEvent;
        StatusEvent statusEvent3 = (StatusEvent) stateFlowImpl.getValue();
        int i2 = -1;
        if (statusEvent3 != null) {
            i = statusEvent3.getPriority();
        } else {
            i = -1;
        }
        StateFlowImpl stateFlowImpl2 = this.animationState;
        if (priority > i) {
            int priority2 = statusEvent.getPriority();
            StatusEvent statusEvent4 = this.currentlyDisplayedEvent;
            if (statusEvent4 != null) {
                i2 = statusEvent4.getPriority();
            }
            if (priority2 > i2 && (statusEvent.getShowAnimation() || !this.hasPersistentDot)) {
                if (statusEvent.getShowAnimation() && ((Number) stateFlowImpl2.getValue()).intValue() == 5) {
                    removePersistentDot(false);
                }
                stateFlowImpl.setValue(statusEvent);
                if (this.currentlyDisplayedEvent != null) {
                    StandaloneCoroutine standaloneCoroutine = this.eventCancellationJob;
                    if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
                        z3 = true;
                    }
                    if (!z3) {
                        this.eventCancellationJob = BuildersKt.launch$default(this.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1(this, null), 3);
                        return;
                    }
                }
                if (((Number) stateFlowImpl2.getValue()).intValue() == 0) {
                    stateFlowImpl2.setValue(1);
                    return;
                }
                return;
            }
        }
        StatusEvent statusEvent5 = this.currentlyDisplayedEvent;
        if (statusEvent5 != null && statusEvent5.shouldUpdateFromEvent(statusEvent)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            if (((Number) stateFlowImpl2.getValue()).intValue() == 2) {
                Log.d("SystemStatusAnimationSchedulerImpl", "skip updating since animation is already started");
                return;
            }
            StatusEvent statusEvent6 = this.currentlyDisplayedEvent;
            if (statusEvent6 != null) {
                statusEvent6.updateFromEvent(statusEvent);
            }
            if (statusEvent.getForceVisible()) {
                this.hasPersistentDot = true;
            }
            if (((Number) stateFlowImpl2.getValue()).intValue() == 1) {
                Log.d("SystemStatusAnimationSchedulerImpl", "Reset chip animation since privacy items are updated");
                Intrinsics.checkNotNull(stateFlowImpl);
                Object value = stateFlowImpl.getValue();
                Intrinsics.checkNotNull(value);
                this.chipAnimationController.prepareChipAnimation(((StatusEvent) value).getViewCreator());
                return;
            }
            return;
        }
        StatusEvent statusEvent7 = (StatusEvent) stateFlowImpl.getValue();
        if (statusEvent7 != null && statusEvent7.shouldUpdateFromEvent(statusEvent)) {
            z3 = true;
        }
        if (z3 && (statusEvent2 = (StatusEvent) stateFlowImpl.getValue()) != null) {
            statusEvent2.updateFromEvent(statusEvent);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Assert.isMainThread();
        Set set = this.listeners;
        set.remove((SystemStatusAnimationCallback) obj);
        if (set.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            systemEventCoordinator.privacyController.removeCallback(systemEventCoordinator.privacyStateListener);
        }
    }

    public final void removePersistentDot(boolean z) {
        Assert.isMainThread();
        StateFlowImpl stateFlowImpl = this.scheduledEvent;
        StatusEvent statusEvent = (StatusEvent) stateFlowImpl.getValue();
        if (statusEvent != null) {
            statusEvent.setForceVisible();
        }
        if (!this.hasPersistentDot) {
            return;
        }
        this.hasPersistentDot = false;
        StateFlowImpl stateFlowImpl2 = this.animationState;
        if (((Number) stateFlowImpl2.getValue()).intValue() == 5) {
            notifyHidePersistentDot(z);
            if (stateFlowImpl.getValue() != null) {
                stateFlowImpl2.setValue(1);
                return;
            } else {
                stateFlowImpl2.setValue(0);
                return;
            }
        }
        if (((Number) stateFlowImpl2.getValue()).intValue() == 4) {
            notifyHidePersistentDot(z);
        }
    }
}

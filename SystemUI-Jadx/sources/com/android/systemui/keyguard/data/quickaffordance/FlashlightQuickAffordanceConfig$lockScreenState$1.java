package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.Dependency;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1", f = "FlashlightQuickAffordanceConfig.kt", l = {174}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class FlashlightQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FlashlightQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlashlightQuickAffordanceConfig$lockScreenState$1(FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig, Continuation<? super FlashlightQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = flashlightQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlashlightQuickAffordanceConfig$lockScreenState$1 flashlightQuickAffordanceConfig$lockScreenState$1 = new FlashlightQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        flashlightQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return flashlightQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlashlightQuickAffordanceConfig$lockScreenState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1$flashlightCallback$1, java.lang.Object] */
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
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig = this.this$0;
            final ?? r1 = new FlashlightController.FlashlightListener() { // from class: com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1$flashlightCallback$1
                @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                public final void onFlashlightAvailabilityChanged(boolean z) {
                    KeyguardQuickAffordanceConfig.LockScreenState lockScreenState;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    if (!z) {
                        FlashlightQuickAffordanceConfig.FlashlightState.Unavailable.INSTANCE.getClass();
                        lockScreenState = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
                    } else if (((FlashlightControllerImpl) flashlightQuickAffordanceConfig.flashlightController).isEnabled()) {
                        lockScreenState = FlashlightQuickAffordanceConfig.FlashlightState.On.INSTANCE.toLockScreenState();
                    } else {
                        lockScreenState = FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState();
                    }
                    ChannelExt.trySendWithFailureLogging$default(channelExt, ProducerScope.this, lockScreenState, "FlashlightQuickAffordanceConfig");
                    ((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).updateShortcutIcons();
                }

                @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                public final void onFlashlightChanged(boolean z) {
                    KeyguardQuickAffordanceConfig.LockScreenState lockScreenState;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    if (z) {
                        lockScreenState = FlashlightQuickAffordanceConfig.FlashlightState.On.INSTANCE.toLockScreenState();
                    } else {
                        lockScreenState = FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState();
                    }
                    ChannelExt.trySendWithFailureLogging$default(channelExt, ProducerScope.this, lockScreenState, "FlashlightQuickAffordanceConfig");
                    ((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).updateShortcutIcons();
                }

                @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                public final void onFlashlightError() {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState(), "FlashlightQuickAffordanceConfig");
                    ((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).updateShortcutIcons();
                }
            };
            ((FlashlightControllerImpl) this.this$0.flashlightController).addCallback(r1);
            final FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((FlashlightControllerImpl) FlashlightQuickAffordanceConfig.this.flashlightController).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}

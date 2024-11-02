package com.android.systemui.statusbar.pipeline.shared.data.repository;

import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.tuner.TunerService;
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
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1", f = "ConnectivityRepository.kt", l = {130}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ConnectivityRepositoryImpl$forceHiddenSlots$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    final /* synthetic */ TunerService $tunerService;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectivityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$forceHiddenSlots$1(TunerService tunerService, ConnectivityInputLogger connectivityInputLogger, ConnectivityRepositoryImpl connectivityRepositoryImpl, Continuation<? super ConnectivityRepositoryImpl$forceHiddenSlots$1> continuation) {
        super(2, continuation);
        this.$tunerService = tunerService;
        this.$logger = connectivityInputLogger;
        this.this$0 = connectivityRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConnectivityRepositoryImpl$forceHiddenSlots$1 connectivityRepositoryImpl$forceHiddenSlots$1 = new ConnectivityRepositoryImpl$forceHiddenSlots$1(this.$tunerService, this.$logger, this.this$0, continuation);
        connectivityRepositoryImpl$forceHiddenSlots$1.L$0 = obj;
        return connectivityRepositoryImpl$forceHiddenSlots$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConnectivityRepositoryImpl$forceHiddenSlots$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1, com.android.systemui.tuner.TunerService$Tunable] */
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
            final ConnectivityInputLogger connectivityInputLogger = this.$logger;
            final ConnectivityRepositoryImpl connectivityRepositoryImpl = this.this$0;
            final ?? r1 = new TunerService.Tunable() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1
                /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
                
                    if (r5 == null) goto L9;
                 */
                @Override // com.android.systemui.tuner.TunerService.Tunable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onTuningChanged(java.lang.String r4, java.lang.String r5) {
                    /*
                        r3 = this;
                        java.lang.String r0 = "icon_blacklist"
                        boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
                        if (r4 != 0) goto L9
                        return
                    L9:
                        com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger r4 = com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger.this
                        r4.logTuningChanged(r5)
                        com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl r4 = r2
                        if (r5 == 0) goto L28
                        java.lang.String r0 = ","
                        java.lang.String[] r0 = new java.lang.String[]{r0}
                        r1 = 0
                        r2 = 6
                        java.util.List r5 = kotlin.text.StringsKt__StringsKt.split$default(r5, r0, r1, r2)
                        com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$Companion r0 = com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl.Companion
                        com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlots r1 = r4.connectivitySlots
                        java.util.Set r5 = com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl.Companion.access$toSlotSet(r0, r5, r1)
                        if (r5 != 0) goto L2a
                    L28:
                        java.util.Set r5 = r4.defaultHiddenIcons
                    L2a:
                        kotlinx.coroutines.channels.ProducerScope r3 = r3
                        kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
                        r3.mo2584trySendJP2dKIU(r5)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1.onTuningChanged(java.lang.String, java.lang.String):void");
                }
            };
            this.$tunerService.addTunable(r1, "icon_blacklist");
            final TunerService tunerService = this.$tunerService;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TunerService.this.removeTunable(r1);
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

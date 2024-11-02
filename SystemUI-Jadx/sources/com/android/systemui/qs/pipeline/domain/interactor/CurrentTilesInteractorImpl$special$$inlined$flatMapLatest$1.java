package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1;
import com.android.systemui.retail.data.repository.RetailModeSettingsRepository;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1", f = "CurrentTilesInteractor.kt", l = {190}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1(Continuation continuation, CurrentTilesInteractorImpl currentTilesInteractorImpl) {
        super(3, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1 currentTilesInteractorImpl$special$$inlined$flatMapLatest$1 = new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return currentTilesInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
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
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final int intValue = ((Number) this.L$1).intValue();
            TileSpecSettingsRepository tileSpecSettingsRepository = (TileSpecSettingsRepository) this.this$0.tileSpecRepository;
            final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(((RetailModeSettingsRepository) tileSpecSettingsRepository.retailModeRepository).retailMode, new TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(null, tileSpecSettingsRepository, intValue));
            Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1

                /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2, reason: invalid class name */
                /* loaded from: classes2.dex */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ int $userId$inlined;

                    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                    @DebugMetadata(c = "com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2", f = "CurrentTilesInteractor.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
                    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
                    /* loaded from: classes2.dex */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, int i) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$userId$inlined = i;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L46
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.util.List r5 = (java.util.List) r5
                            com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$UserAndTiles r6 = new com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$UserAndTiles
                            int r2 = r4.$userId$inlined
                            r6.<init>(r2, r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L46
                            return r1
                        L46:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$userAndTiles$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, intValue), continuation);
                    if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
                        return collect;
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}

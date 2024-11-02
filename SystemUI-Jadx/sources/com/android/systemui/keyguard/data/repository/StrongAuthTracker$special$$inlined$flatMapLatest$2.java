package com.android.systemui.keyguard.data.repository;

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
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$flatMapLatest$2", f = "BiometricSettingsRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class StrongAuthTracker$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ StrongAuthTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StrongAuthTracker$special$$inlined$flatMapLatest$2(Continuation continuation, StrongAuthTracker strongAuthTracker) {
        super(3, continuation);
        this.this$0 = strongAuthTracker;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StrongAuthTracker$special$$inlined$flatMapLatest$2 strongAuthTracker$special$$inlined$flatMapLatest$2 = new StrongAuthTracker$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        strongAuthTracker$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        strongAuthTracker$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return strongAuthTracker$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
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
            final StateFlowImpl stateFlowImpl = this.this$0._nonStrongBiometricAllowed;
            final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1

                /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2, reason: invalid class name */
                /* loaded from: classes.dex */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ int $userId$inlined;

                    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2", f = "BiometricSettingsRepository.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2$1, reason: invalid class name */
                    /* loaded from: classes.dex */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L53
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            r6 = r5
                            kotlin.Pair r6 = (kotlin.Pair) r6
                            java.lang.Object r6 = r6.getFirst()
                            java.lang.Number r6 = (java.lang.Number) r6
                            int r6 = r6.intValue()
                            int r2 = r4.$userId$inlined
                            if (r6 != r2) goto L45
                            r6 = r3
                            goto L46
                        L45:
                            r6 = 0
                        L46:
                            if (r6 == 0) goto L53
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L53
                            return r1
                        L53:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new StrongAuthTracker$isNonStrongBiometricAllowed$2$4(this.this$0, intValue, null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1

                /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2, reason: invalid class name */
                /* loaded from: classes.dex */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2", f = "BiometricSettingsRepository.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2$1, reason: invalid class name */
                    /* loaded from: classes.dex */
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L43
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            kotlin.Pair r5 = (kotlin.Pair) r5
                            java.lang.Object r5 = r5.getSecond()
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L43
                            return r1
                        L43:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$lambda$7$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
                        return collect;
                    }
                    return Unit.INSTANCE;
                }
            }, new StrongAuthTracker$isNonStrongBiometricAllowed$2$3(null)));
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}

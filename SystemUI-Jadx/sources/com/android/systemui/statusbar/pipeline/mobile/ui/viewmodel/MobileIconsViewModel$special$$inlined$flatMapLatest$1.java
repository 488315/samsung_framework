package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$flatMapLatest$1", f = "MobileIconsViewModel.kt", l = {190}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconsViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconsViewModel$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconsViewModel$special$$inlined$flatMapLatest$1 mobileIconsViewModel$special$$inlined$flatMapLatest$1 = new MobileIconsViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3);
        mobileIconsViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mobileIconsViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return mobileIconsViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final Flow networkTypeIcon;
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
            MobileIconViewModelCommon mobileIconViewModelCommon = (MobileIconViewModelCommon) this.L$1;
            if (mobileIconViewModelCommon != null && (networkTypeIcon = mobileIconViewModelCommon.getNetworkTypeIcon()) != null) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1

                    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    /* loaded from: classes2.dex */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                        @DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2", f = "MobileIconsViewModel.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
                        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L48
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.common.shared.model.Icon$Resource r5 = (com.android.systemui.common.shared.model.Icon.Resource) r5
                                if (r5 == 0) goto L38
                                r5 = r3
                                goto L39
                            L38:
                                r5 = 0
                            L39:
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L48
                                return r1
                            L48:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$firstMobileSubShowingNetworkTypeIcon$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}

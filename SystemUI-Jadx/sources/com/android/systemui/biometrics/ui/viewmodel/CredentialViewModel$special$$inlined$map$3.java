package com.android.systemui.biometrics.ui.viewmodel;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CredentialViewModel$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        @DebugMetadata(c = "com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2", f = "CredentialViewModel.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
        /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r6
                com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3$2$1
                r0.<init>(r6)
            L18:
                java.lang.Object r6 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r6)
                goto L4d
            L27:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2f:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential) r5
                boolean r6 = r5 instanceof com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.Pattern
                if (r6 == 0) goto L3d
                com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential$Pattern r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.Pattern) r5
                boolean r5 = r5.stealthMode
                goto L3e
            L3d:
                r5 = 0
            L3e:
                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                java.lang.Object r4 = r4.emit(r5, r0)
                if (r4 != r1) goto L4d
                return r1
            L4d:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public CredentialViewModel$special$$inlined$map$3(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}

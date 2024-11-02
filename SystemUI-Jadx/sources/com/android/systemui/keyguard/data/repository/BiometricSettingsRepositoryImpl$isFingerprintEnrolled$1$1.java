package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
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
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1", f = "BiometricSettingsRepository.kt", l = {204}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AuthController $authController;
    final /* synthetic */ int $currentUserId;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1(AuthController authController, int i, Continuation<? super BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1> continuation) {
        super(2, continuation);
        this.$authController = authController;
        this.$currentUserId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1 biometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1 = new BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1(this.$authController, this.$currentUserId, continuation);
        biometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1.L$0 = obj;
        return biometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1$callback$1, com.android.systemui.biometrics.AuthController$Callback] */
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
            final int i2 = this.$currentUserId;
            final ?? r1 = new AuthController.Callback() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1$callback$1
                @Override // com.android.systemui.biometrics.AuthController.Callback
                public final void onEnrollmentsChanged(BiometricType biometricType, int i3, boolean z) {
                    if (biometricType.isFingerprint() && i3 == i2) {
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        Boolean valueOf = Boolean.valueOf(z);
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "BiometricsRepositoryImpl", "update fpEnrollment");
                    }
                }
            };
            this.$authController.addCallback(r1);
            final AuthController authController = this.$authController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AuthController.this.removeCallback(r1);
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

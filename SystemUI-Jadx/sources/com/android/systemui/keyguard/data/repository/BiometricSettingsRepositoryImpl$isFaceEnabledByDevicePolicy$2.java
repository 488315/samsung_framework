package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2", f = "BiometricSettingsRepository.kt", l = {261}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(DevicePolicyManager devicePolicyManager, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, Continuation<? super BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2> continuation) {
        super(2, continuation);
        this.$devicePolicyManager = devicePolicyManager;
        this.this$0 = biometricSettingsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 = new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(this.$devicePolicyManager, this.this$0, continuation);
        biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2.L$0 = obj;
        return biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
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
            if ((this.$devicePolicyManager.getKeyguardDisabledFeatures(null, ((UserRepositoryImpl) this.this$0.userRepository).getSelectedUserInfo().id) & 128) == 0) {
                z = true;
            } else {
                z = false;
            }
            Boolean valueOf = Boolean.valueOf(z);
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}

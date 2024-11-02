package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.util.settings.SecureSettings;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$set$2", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardQuickAffordanceLegacySettingSyncer$set$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isSet;
    final /* synthetic */ String $settingsKey;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$set$2(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, String str, boolean z, Continuation<? super KeyguardQuickAffordanceLegacySettingSyncer$set$2> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$settingsKey = str;
        this.$isSet = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardQuickAffordanceLegacySettingSyncer$set$2(this.this$0, this.$settingsKey, this.$isSet, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardQuickAffordanceLegacySettingSyncer$set$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            SecureSettings secureSettings = this.this$0.secureSettings;
            String str = this.$settingsKey;
            boolean z = this.$isSet;
            return Boolean.valueOf(secureSettings.putIntForUser(z ? 1 : 0, secureSettings.getUserId(), str));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

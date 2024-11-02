package com.android.systemui.keyboard.data.repository;

import android.hardware.input.InputManager;
import android.hardware.input.KeyboardBacklightState;
import java.util.concurrent.Executor;
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
@DebugMetadata(c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1", f = "KeyboardRepository.kt", l = {141}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyboardRepositoryImpl$backlightStateListener$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyboardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardRepositoryImpl$backlightStateListener$1(KeyboardRepositoryImpl keyboardRepositoryImpl, Continuation<? super KeyboardRepositoryImpl$backlightStateListener$1> continuation) {
        super(2, continuation);
        this.this$0 = keyboardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardRepositoryImpl$backlightStateListener$1 keyboardRepositoryImpl$backlightStateListener$1 = new KeyboardRepositoryImpl$backlightStateListener$1(this.this$0, continuation);
        keyboardRepositoryImpl$backlightStateListener$1.L$0 = obj;
        return keyboardRepositoryImpl$backlightStateListener$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardRepositoryImpl$backlightStateListener$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyboardRepositoryImpl keyboardRepositoryImpl = this.this$0;
            final InputManager.KeyboardBacklightListener keyboardBacklightListener = new InputManager.KeyboardBacklightListener() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1$listener$1
                public final void onKeyboardBacklightChanged(int i2, KeyboardBacklightState keyboardBacklightState, boolean z) {
                    if (z) {
                        KeyboardRepositoryImpl.access$sendWithLogging(KeyboardRepositoryImpl.this, producerScope, keyboardBacklightState);
                    }
                }
            };
            this.this$0.inputManager.registerKeyboardBacklightListener(new Executor() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            }, keyboardBacklightListener);
            final KeyboardRepositoryImpl keyboardRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyboardRepositoryImpl.this.inputManager.unregisterKeyboardBacklightListener(keyboardBacklightListener);
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

package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", f = "SharingStarted.kt", l = {178, 180, 182, 183, 185}, m = "invokeSuspend")
/* loaded from: classes3.dex */
final class StartedWhileSubscribed$command$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StartedWhileSubscribed this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StartedWhileSubscribed$command$1(StartedWhileSubscribed startedWhileSubscribed, Continuation<? super StartedWhileSubscribed$command$1> continuation) {
        super(3, continuation);
        this.this$0 = startedWhileSubscribed;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj2).intValue();
        StartedWhileSubscribed$command$1 startedWhileSubscribed$command$1 = new StartedWhileSubscribed$command$1(this.this$0, (Continuation) obj3);
        startedWhileSubscribed$command$1.L$0 = (FlowCollector) obj;
        startedWhileSubscribed$command$1.I$0 = intValue;
        return startedWhileSubscribed$command$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L3a
            if (r1 == r6) goto L36
            if (r1 == r5) goto L2e
            if (r1 == r4) goto L26
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            goto L36
        L16:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1e:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L86
        L26:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L77
        L2e:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L60
        L36:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L94
        L3a:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            int r10 = r9.I$0
            if (r10 <= 0) goto L51
            kotlinx.coroutines.flow.SharingCommand r10 = kotlinx.coroutines.flow.SharingCommand.START
            r9.label = r6
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L94
            return r0
        L51:
            kotlinx.coroutines.flow.StartedWhileSubscribed r10 = r9.this$0
            long r6 = r10.stopTimeout
            r9.L$0 = r1
            r9.label = r5
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.delay(r6, r9)
            if (r10 != r0) goto L60
            return r0
        L60:
            kotlinx.coroutines.flow.StartedWhileSubscribed r10 = r9.this$0
            long r5 = r10.replayExpiration
            r7 = 0
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L86
            kotlinx.coroutines.flow.SharingCommand r10 = kotlinx.coroutines.flow.SharingCommand.STOP
            r9.L$0 = r1
            r9.label = r4
            java.lang.Object r10 = r1.emit(r10, r9)
            if (r10 != r0) goto L77
            return r0
        L77:
            kotlinx.coroutines.flow.StartedWhileSubscribed r10 = r9.this$0
            long r4 = r10.replayExpiration
            r9.L$0 = r1
            r9.label = r3
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.delay(r4, r9)
            if (r10 != r0) goto L86
            return r0
        L86:
            kotlinx.coroutines.flow.SharingCommand r10 = kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE
            r3 = 0
            r9.L$0 = r3
            r9.label = r2
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L94
            return r0
        L94:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedWhileSubscribed$command$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

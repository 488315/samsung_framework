package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class ReadonlyStateFlow implements StateFlow, Flow, FusibleFlow {
    public final /* synthetic */ StateFlow $$delegate_0;

    public ReadonlyStateFlow(StateFlow stateFlow, Job job) {
        this.$$delegate_0 = stateFlow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        return this.$$delegate_0.collect(flowCollector, continuation);
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        boolean z;
        Symbol symbol = StateFlowKt.NONE;
        if (i >= 0 && i < 2) {
            z = true;
        } else {
            z = false;
        }
        if ((!z && i != -2) || bufferOverflow != BufferOverflow.DROP_OLDEST) {
            Symbol symbol2 = SharedFlowKt.NO_VALUE;
            if ((i != 0 && i != -3) || bufferOverflow != BufferOverflow.SUSPEND) {
                return new ChannelFlowOperatorImpl(this, coroutineContext, i, bufferOverflow);
            }
            return this;
        }
        return this;
    }

    @Override // kotlinx.coroutines.flow.StateFlow
    public final Object getValue() {
        return this.$$delegate_0.getValue();
    }
}
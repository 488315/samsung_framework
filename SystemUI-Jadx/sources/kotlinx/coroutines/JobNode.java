package kotlinx.coroutines;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class JobNode extends CompletionHandlerBase implements DisposableHandle, Incomplete {
    public JobSupport job;

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode, kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines;
        JobSupport job = getJob();
        do {
            state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = job.getState$external__kotlinx_coroutines__android_common__kotlinx_coroutines();
            if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof JobNode) {
                if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines == this) {
                } else {
                    return;
                }
            } else {
                if ((state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Incomplete) && ((Incomplete) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).getList() != null) {
                    remove();
                    return;
                }
                return;
            }
        } while (!job._state.compareAndSet(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines, JobSupportKt.EMPTY_ACTIVE));
    }

    public final JobSupport getJob() {
        JobSupport jobSupport = this.job;
        if (jobSupport != null) {
            return jobSupport;
        }
        return null;
    }

    @Override // kotlinx.coroutines.Incomplete
    public final NodeList getList() {
        return null;
    }

    public Job getParent() {
        return getJob();
    }

    @Override // kotlinx.coroutines.Incomplete
    public final boolean isActive() {
        return true;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + "@" + DebugStringsKt.getHexAddress(this) + "[job@" + DebugStringsKt.getHexAddress(getJob()) + "]";
    }
}

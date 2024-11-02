package kotlinx.coroutines.scheduling;

import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class TasksKt {
    public static final TaskContextImpl BlockingContext;
    public static final int CORE_POOL_SIZE;
    public static final long IDLE_WORKER_KEEP_ALIVE_NS;
    public static final int MAX_POOL_SIZE;
    public static final TaskContextImpl NonBlockingContext;
    public static final long WORK_STEALING_TIME_RESOLUTION_NS = SystemPropsKt.systemProp("kotlinx.coroutines.scheduler.resolution.ns", 100000, 1, Long.MAX_VALUE);
    public static final NanoTimeSource schedulerTimeSource;

    static {
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        if (i < 2) {
            i = 2;
        }
        CORE_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.core.pool.size", i, 1, 0, 8);
        MAX_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.max.pool.size", 2097150, 0, 2097150, 4);
        IDLE_WORKER_KEEP_ALIVE_NS = TimeUnit.SECONDS.toNanos(SystemPropsKt.systemProp("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 1L, Long.MAX_VALUE));
        schedulerTimeSource = NanoTimeSource.INSTANCE;
        NonBlockingContext = new TaskContextImpl(0);
        BlockingContext = new TaskContextImpl(1);
    }
}

package com.android.server;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FgThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static FgThread sInstance;

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            FgThread fgThread = new FgThread(0, "android.fg", true);
            sInstance = fgThread;
            fgThread.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(100L, 200L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
            sHandlerExecutor = new HandlerExecutor(sHandler);
        }
    }

    public static FgThread get() {
        FgThread fgThread;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            fgThread = sInstance;
        }
        return fgThread;
    }

    public static Executor getExecutor() {
        HandlerExecutor handlerExecutor;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}

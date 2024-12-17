package com.android.server;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppSchedulingModuleThread extends HandlerThread {
    public static Handler sHandler;
    public static Executor sHandlerExecutor;
    public static AppSchedulingModuleThread sInstance;

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            AppSchedulingModuleThread appSchedulingModuleThread = new AppSchedulingModuleThread("appscheduling.default", 0);
            sInstance = appSchedulingModuleThread;
            appSchedulingModuleThread.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(10000L, 30000L);
            sHandler = new Handler(sInstance.getLooper());
            sHandlerExecutor = new HandlerExecutor(sHandler);
        }
    }

    public static AppSchedulingModuleThread get() {
        AppSchedulingModuleThread appSchedulingModuleThread;
        synchronized (AppSchedulingModuleThread.class) {
            ensureThreadLocked();
            appSchedulingModuleThread = sInstance;
        }
        return appSchedulingModuleThread;
    }

    public static Executor getExecutor() {
        Executor executor;
        synchronized (AppSchedulingModuleThread.class) {
            ensureThreadLocked();
            executor = sHandlerExecutor;
        }
        return executor;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (AppSchedulingModuleThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}

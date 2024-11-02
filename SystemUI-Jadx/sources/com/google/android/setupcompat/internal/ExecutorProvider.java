package com.google.android.setupcompat.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ExecutorProvider {
    public static final ExecutorProvider setupCompatServiceInvoker = new ExecutorProvider(createSizeBoundedExecutor("SetupCompatServiceInvoker", 50));
    public final Executor executor;
    public Executor injectedExecutor;

    private ExecutorProvider(Executor executor) {
        this.executor = executor;
    }

    public static ExecutorService createSizeBoundedExecutor(final String str, int i) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue(i), new ThreadFactory() { // from class: com.google.android.setupcompat.internal.ExecutorProvider$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, str);
            }
        });
    }

    public static void resetExecutors() {
        setupCompatServiceInvoker.injectedExecutor = null;
    }

    public void injectExecutor(Executor executor) {
        this.injectedExecutor = executor;
    }
}

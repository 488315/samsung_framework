package android.view;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes4.dex */
public class InsetsAnimationThread extends HandlerThread {
    private static Handler sHandler;
    private static InsetsAnimationThread sInstance;

    private InsetsAnimationThread() {
        super("InsetsAnimations");
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new InsetsAnimationThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(8L);
            sHandler = new Handler(sInstance.getLooper());
        }
    }

    public static void release() {
        synchronized (InsetsAnimationThread.class) {
            if (sInstance == null) {
                return;
            }
            sInstance.getLooper().quitSafely();
            sInstance = null;
            sHandler = null;
        }
    }

    public static InsetsAnimationThread get() {
        InsetsAnimationThread insetsAnimationThread;
        synchronized (InsetsAnimationThread.class) {
            ensureThreadLocked();
            insetsAnimationThread = sInstance;
        }
        return insetsAnimationThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (InsetsAnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}

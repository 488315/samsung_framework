package com.android.internal.os;

/* loaded from: classes5.dex */
public final class SomeArgs {
    private static final int MAX_POOL_SIZE = 10;
    static final int WAIT_FINISHED = 2;
    static final int WAIT_NONE = 0;
    static final int WAIT_WAITING = 1;
    private static SomeArgs sPool;
    private static Object sPoolLock = new Object();
    private static int sPoolSize;
    public Object arg1;
    public Object arg2;
    public Object arg3;
    public Object arg4;
    public Object arg5;
    public Object arg6;
    public Object arg7;
    public int argi1;
    public int argi2;
    public int argi3;
    public int argi4;
    public int argi5;
    public int argi6;
    public long argl1;
    public long argl2;
    private boolean mInPool;
    private SomeArgs mNext;
    int mWaitState = 0;

    private SomeArgs() {
    }

    public static SomeArgs obtain() {
        synchronized (sPoolLock) {
            if (sPoolSize > 0) {
                SomeArgs args = sPool;
                sPool = sPool.mNext;
                args.mNext = null;
                args.mInPool = false;
                sPoolSize--;
                return args;
            }
            return new SomeArgs();
        }
    }

    public void complete() {
        synchronized (this) {
            if (this.mWaitState != 1) {
                throw new IllegalStateException("Not waiting");
            }
            this.mWaitState = 2;
            notifyAll();
        }
    }

    public void recycle() {
        if (this.mInPool) {
            throw new IllegalStateException("Already recycled.");
        }
        if (this.mWaitState != 0) {
            return;
        }
        synchronized (sPoolLock) {
            clear();
            if (sPoolSize < 10) {
                this.mNext = sPool;
                this.mInPool = true;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    private void clear() {
        this.arg1 = null;
        this.arg2 = null;
        this.arg3 = null;
        this.arg4 = null;
        this.arg5 = null;
        this.arg6 = null;
        this.arg7 = null;
        this.argi1 = 0;
        this.argi2 = 0;
        this.argi3 = 0;
        this.argi4 = 0;
        this.argi5 = 0;
        this.argi6 = 0;
        this.argl1 = 0L;
        this.argl2 = 0L;
    }
}

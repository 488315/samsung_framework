package com.android.server.timezonedetector.location;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ThreadingDomain$SingleRunnableQueue {
    public long mDelayMillis;
    public boolean mIsQueued;
    public final /* synthetic */ HandlerThreadingDomain this$0;

    public ThreadingDomain$SingleRunnableQueue(HandlerThreadingDomain handlerThreadingDomain) {
        this.this$0 = handlerThreadingDomain;
    }

    public final void cancel() {
        HandlerThreadingDomain handlerThreadingDomain = this.this$0;
        handlerThreadingDomain.assertCurrentThread();
        if (this.mIsQueued) {
            handlerThreadingDomain.mHandler.removeCallbacksAndMessages(this);
        }
        this.mIsQueued = false;
        this.mDelayMillis = -1L;
    }
}

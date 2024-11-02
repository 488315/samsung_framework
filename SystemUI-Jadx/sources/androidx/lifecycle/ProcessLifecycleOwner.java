package androidx.lifecycle;

import android.os.Handler;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ProcessLifecycleOwner implements LifecycleOwner {
    public static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    public Handler mHandler;
    public int mStartedCounter = 0;
    public int mResumedCounter = 0;
    public boolean mPauseSent = true;
    public boolean mStopSent = true;
    public final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    public final AnonymousClass1 mDelayedPauseRunnable = new Runnable() { // from class: androidx.lifecycle.ProcessLifecycleOwner.1
        @Override // java.lang.Runnable
        public final void run() {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            if (processLifecycleOwner.mResumedCounter == 0) {
                processLifecycleOwner.mPauseSent = true;
                processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            }
            ProcessLifecycleOwner processLifecycleOwner2 = ProcessLifecycleOwner.this;
            if (processLifecycleOwner2.mStartedCounter == 0 && processLifecycleOwner2.mPauseSent) {
                processLifecycleOwner2.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                processLifecycleOwner2.mStopSent = true;
            }
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: androidx.lifecycle.ProcessLifecycleOwner$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.lifecycle.ProcessLifecycleOwner$1] */
    private ProcessLifecycleOwner() {
        new AnonymousClass2();
    }

    public final void activityResumed() {
        int i = this.mResumedCounter + 1;
        this.mResumedCounter = i;
        if (i == 1) {
            if (this.mPauseSent) {
                this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                this.mPauseSent = false;
            } else {
                this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
            }
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }
}

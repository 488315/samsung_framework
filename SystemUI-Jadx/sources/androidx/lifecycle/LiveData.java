package androidx.lifecycle;

import android.os.Handler;
import android.os.Looper;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.util.Map;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class LiveData {
    public static final Object NOT_SET = new Object();
    public int mActiveCount;
    public boolean mChangingActiveState;
    public volatile Object mData;
    public final Object mDataLock;
    public boolean mDispatchInvalidated;
    public boolean mDispatchingValue;
    public final SafeIterableMap mObservers;
    public volatile Object mPendingData;
    public final AnonymousClass1 mPostValueRunnable;
    public int mVersion;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AlwaysActiveObserver extends ObserverWrapper {
        public AlwaysActiveObserver(LiveData liveData, Observer observer) {
            super(observer);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean shouldBeActive() {
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    class LifecycleBoundObserver extends ObserverWrapper implements LifecycleEventObserver {
        public final LifecycleOwner mOwner;

        public LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer observer) {
            super(observer);
            this.mOwner = lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            if (this.mOwner == lifecycleOwner) {
                return true;
            }
            return false;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            LifecycleOwner lifecycleOwner2 = this.mOwner;
            Lifecycle.State state = lifecycleOwner2.getLifecycle().mState;
            if (state == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
                return;
            }
            Lifecycle.State state2 = null;
            while (state2 != state) {
                activeStateChanged(shouldBeActive());
                state2 = state;
                state = lifecycleOwner2.getLifecycle().mState;
            }
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean shouldBeActive() {
            return this.mOwner.getLifecycle().mState.isAtLeast(Lifecycle.State.STARTED);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.lifecycle.LiveData$1] */
    public LiveData(Object obj) {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap();
        this.mActiveCount = 0;
        this.mPendingData = NOT_SET;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2;
                synchronized (LiveData.this.mDataLock) {
                    obj2 = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(obj2);
            }
        };
        this.mData = obj;
        this.mVersion = 0;
    }

    public static void assertMainThread(String str) {
        boolean z;
        ArchTaskExecutor.getInstance().mDelegate.getClass();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
        } else {
            throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m("Cannot invoke ", str, " on a background thread"));
        }
    }

    public final void considerNotify(ObserverWrapper observerWrapper) {
        if (!observerWrapper.mActive) {
            return;
        }
        if (!observerWrapper.shouldBeActive()) {
            observerWrapper.activeStateChanged(false);
            return;
        }
        int i = observerWrapper.mLastVersion;
        int i2 = this.mVersion;
        if (i >= i2) {
            return;
        }
        observerWrapper.mLastVersion = i2;
        observerWrapper.mObserver.onChanged(this.mData);
    }

    public final void dispatchingValue(ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (observerWrapper != null) {
                considerNotify(observerWrapper);
                observerWrapper = null;
            } else {
                SafeIterableMap safeIterableMap = this.mObservers;
                safeIterableMap.getClass();
                SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = new SafeIterableMap.IteratorWithAdditions();
                safeIterableMap.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
                while (iteratorWithAdditions.hasNext()) {
                    considerNotify((ObserverWrapper) ((Map.Entry) iteratorWithAdditions.next()).getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    public Object getValue() {
        Object obj = this.mData;
        if (obj != NOT_SET) {
            return obj;
        }
        return null;
    }

    public final void observe(LifecycleOwner lifecycleOwner, Observer observer) {
        assertMainThread("observe");
        if (lifecycleOwner.getLifecycle().mState == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, lifecycleBoundObserver);
        if (observerWrapper != null && !observerWrapper.isAttachedTo(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapper != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    public final void observeForever(Observer observer) {
        assertMainThread("observeForever");
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(this, observer);
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, alwaysActiveObserver);
        if (!(observerWrapper instanceof LifecycleBoundObserver)) {
            if (observerWrapper != null) {
                return;
            }
            alwaysActiveObserver.activeStateChanged(true);
            return;
        }
        throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
    }

    public void postValue(Object obj) {
        boolean z;
        synchronized (this.mDataLock) {
            if (this.mPendingData == NOT_SET) {
                z = true;
            } else {
                z = false;
            }
            this.mPendingData = obj;
        }
        if (!z) {
            return;
        }
        ArchTaskExecutor archTaskExecutor = ArchTaskExecutor.getInstance();
        AnonymousClass1 anonymousClass1 = this.mPostValueRunnable;
        DefaultTaskExecutor defaultTaskExecutor = archTaskExecutor.mDelegate;
        if (defaultTaskExecutor.mMainHandler == null) {
            synchronized (defaultTaskExecutor.mLock) {
                if (defaultTaskExecutor.mMainHandler == null) {
                    defaultTaskExecutor.mMainHandler = Handler.createAsync(Looper.getMainLooper());
                }
            }
        }
        defaultTaskExecutor.mMainHandler.post(anonymousClass1);
    }

    public void removeObserver(Observer observer) {
        assertMainThread("removeObserver");
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.remove(observer);
        if (observerWrapper == null) {
            return;
        }
        observerWrapper.detachObserver();
        observerWrapper.activeStateChanged(false);
    }

    public void setValue(Object obj) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = obj;
        dispatchingValue(null);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [androidx.lifecycle.LiveData$1] */
    public LiveData() {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap();
        this.mActiveCount = 0;
        Object obj = NOT_SET;
        this.mPendingData = obj;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2;
                synchronized (LiveData.this.mDataLock) {
                    obj2 = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(obj2);
            }
        };
        this.mData = obj;
        this.mVersion = -1;
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class ObserverWrapper {
        public boolean mActive;
        public int mLastVersion = -1;
        public final Observer mObserver;

        public ObserverWrapper(Observer observer) {
            this.mObserver = observer;
        }

        public final void activeStateChanged(boolean z) {
            int i;
            boolean z2;
            boolean z3;
            if (z == this.mActive) {
                return;
            }
            this.mActive = z;
            if (z) {
                i = 1;
            } else {
                i = -1;
            }
            LiveData liveData = LiveData.this;
            int i2 = liveData.mActiveCount;
            liveData.mActiveCount = i + i2;
            if (!liveData.mChangingActiveState) {
                liveData.mChangingActiveState = true;
                while (true) {
                    try {
                        int i3 = liveData.mActiveCount;
                        if (i2 == i3) {
                            break;
                        }
                        if (i2 == 0 && i3 > 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (i2 > 0 && i3 == 0) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z2) {
                            liveData.onActive();
                        } else if (z3) {
                            liveData.onInactive();
                        }
                        i2 = i3;
                    } finally {
                        liveData.mChangingActiveState = false;
                    }
                }
            }
            if (this.mActive) {
                liveData.dispatchingValue(this);
            }
        }

        public boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return false;
        }

        public abstract boolean shouldBeActive();

        public void detachObserver() {
        }
    }

    public void onActive() {
    }

    public void onInactive() {
    }
}

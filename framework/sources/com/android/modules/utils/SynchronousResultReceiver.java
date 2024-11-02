package com.android.modules.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.android.modules.utils.ISynchronousResultReceiver;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes5.dex */
public final class SynchronousResultReceiver<T> implements Parcelable {
    private static final int QUEUE_THRESHOLD = 4;
    private static final String TAG = "SynchronousResultReceiver";
    private CompletableFuture<Result<T>> mFuture;
    private boolean mIsCompleted;
    private final boolean mLocal;
    ISynchronousResultReceiver mReceiver;
    private static final Object sLock = new Object();
    private static final ConcurrentLinkedQueue<SynchronousResultReceiver> sAvailableReceivers = new ConcurrentLinkedQueue<>();
    public static final Parcelable.Creator<SynchronousResultReceiver<?>> CREATOR = new Parcelable.Creator<SynchronousResultReceiver<?>>() { // from class: com.android.modules.utils.SynchronousResultReceiver.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SynchronousResultReceiver<?> createFromParcel(Parcel in) {
            return new SynchronousResultReceiver<>(in);
        }

        @Override // android.os.Parcelable.Creator
        public SynchronousResultReceiver<?>[] newArray(int size) {
            return new SynchronousResultReceiver[size];
        }
    };

    /* synthetic */ SynchronousResultReceiver(Parcel parcel, SynchronousResultReceiverIA synchronousResultReceiverIA) {
        this(parcel);
    }

    public static <T> SynchronousResultReceiver<T> get() {
        synchronized (sLock) {
            ConcurrentLinkedQueue<SynchronousResultReceiver> concurrentLinkedQueue = sAvailableReceivers;
            if (concurrentLinkedQueue.isEmpty()) {
                return new SynchronousResultReceiver<>();
            }
            SynchronousResultReceiver receiver = concurrentLinkedQueue.poll();
            receiver.resetLocked();
            return receiver;
        }
    }

    private SynchronousResultReceiver() {
        this.mFuture = new CompletableFuture<>();
        this.mReceiver = null;
        this.mLocal = true;
        this.mIsCompleted = false;
    }

    private void releaseLocked() {
        this.mFuture = null;
        ConcurrentLinkedQueue<SynchronousResultReceiver> concurrentLinkedQueue = sAvailableReceivers;
        if (concurrentLinkedQueue.size() < 4) {
            concurrentLinkedQueue.add(this);
        }
    }

    private void resetLocked() {
        this.mFuture = new CompletableFuture<>();
        this.mIsCompleted = false;
    }

    public CompletableFuture<Result<T>> getFuture() {
        CompletableFuture<Result<T>> completableFuture;
        synchronized (sLock) {
            completableFuture = this.mFuture;
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static class Result<T> implements Parcelable {
        public static final Parcelable.Creator<Result<?>> CREATOR = new Parcelable.Creator<Result<?>>() { // from class: com.android.modules.utils.SynchronousResultReceiver.Result.1
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Result<?> createFromParcel(Parcel in) {
                return new Result<>(in);
            }

            @Override // android.os.Parcelable.Creator
            public Result<?>[] newArray(int size) {
                return new Result[size];
            }
        };
        private final RuntimeException mException;
        private final T mObject;

        /* synthetic */ Result(Parcel parcel, ResultIA resultIA) {
            this(parcel);
        }

        public Result(RuntimeException exception) {
            this.mObject = null;
            this.mException = exception;
        }

        public Result(T object) {
            this.mObject = object;
            this.mException = null;
        }

        public T getValue(T defaultValue) {
            RuntimeException runtimeException = this.mException;
            if (runtimeException != null) {
                throw runtimeException;
            }
            T t = this.mObject;
            if (t == null) {
                return defaultValue;
            }
            return t;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeValue(this.mObject);
            out.writeValue(this.mException);
        }

        private Result(Parcel parcel) {
            this.mObject = (T) parcel.readValue(null);
            this.mException = (RuntimeException) parcel.readValue(null);
        }

        /* renamed from: com.android.modules.utils.SynchronousResultReceiver$Result$1 */
        /* loaded from: classes5.dex */
        class AnonymousClass1 implements Parcelable.Creator<Result<?>> {
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Result<?> createFromParcel(Parcel in) {
                return new Result<>(in);
            }

            @Override // android.os.Parcelable.Creator
            public Result<?>[] newArray(int size) {
                return new Result[size];
            }
        }
    }

    private void complete(Result<T> result) {
        ISynchronousResultReceiver rr;
        if (this.mIsCompleted) {
            throw new IllegalStateException("Receiver has already been completed");
        }
        this.mIsCompleted = true;
        if (this.mLocal) {
            getFuture().complete(result);
            return;
        }
        synchronized (this) {
            rr = this.mReceiver;
        }
        if (rr != null) {
            try {
                rr.send(result);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to complete future");
            }
        }
    }

    public void send(T resultData) {
        complete(new Result<>(resultData));
    }

    public void propagateException(RuntimeException e) {
        Objects.requireNonNull(e, "RuntimeException cannot be null");
        complete(new Result<>(e));
    }

    public Result<T> awaitResultNoInterrupt(Duration timeout) throws TimeoutException {
        Objects.requireNonNull(timeout, "Null timeout is not allowed");
        long startWaitNanoTime = SystemClock.elapsedRealtimeNanos();
        Duration remainingTime = timeout;
        while (!remainingTime.isNegative()) {
            try {
                Result<T> result = getFuture().get(remainingTime.toMillis(), TimeUnit.MILLISECONDS);
                synchronized (sLock) {
                    releaseLocked();
                }
                return result;
            } catch (InterruptedException e) {
                remainingTime = timeout.minus(Duration.ofNanos(SystemClock.elapsedRealtimeNanos() - startWaitNanoTime));
            } catch (ExecutionException e2) {
                throw new AssertionError("Error receiving response", e2);
            }
        }
        synchronized (sLock) {
            releaseLocked();
        }
        throw new TimeoutException();
    }

    /* loaded from: classes5.dex */
    private final class MyResultReceiver extends ISynchronousResultReceiver.Stub {
        /* synthetic */ MyResultReceiver(SynchronousResultReceiver synchronousResultReceiver, MyResultReceiverIA myResultReceiverIA) {
            this();
        }

        private MyResultReceiver() {
        }

        @Override // com.android.modules.utils.ISynchronousResultReceiver
        public void send(Result result) {
            CompletableFuture<Result<T>> future = SynchronousResultReceiver.this.getFuture();
            if (future != null) {
                future.complete(result);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new MyResultReceiver();
            }
            out.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    private SynchronousResultReceiver(Parcel in) {
        this.mFuture = new CompletableFuture<>();
        this.mReceiver = null;
        this.mLocal = false;
        this.mIsCompleted = false;
        this.mReceiver = ISynchronousResultReceiver.Stub.asInterface(in.readStrongBinder());
    }

    /* renamed from: com.android.modules.utils.SynchronousResultReceiver$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SynchronousResultReceiver<?>> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SynchronousResultReceiver<?> createFromParcel(Parcel in) {
            return new SynchronousResultReceiver<>(in);
        }

        @Override // android.os.Parcelable.Creator
        public SynchronousResultReceiver<?>[] newArray(int size) {
            return new SynchronousResultReceiver[size];
        }
    }
}

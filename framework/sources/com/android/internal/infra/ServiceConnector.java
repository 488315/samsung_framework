package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes5.dex */
public interface ServiceConnector<I extends IInterface> {

    @FunctionalInterface
    public interface Job<II, R> {
        R run(II ii) throws Exception;
    }

    AndroidFuture<I> connect();

    AndroidFuture<Void> post(VoidJob<I> voidJob);

    <R> AndroidFuture<R> postAsync(Job<I, CompletableFuture<R>> job);

    <R> AndroidFuture<R> postForResult(Job<I, R> job);

    boolean run(VoidJob<I> voidJob);

    void setServiceLifecycleCallbacks(ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks);

    void unbind();

    @FunctionalInterface
    public interface VoidJob<II> extends Job<II, Void> {
        void runNoResult(II ii) throws Exception;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.internal.infra.ServiceConnector.Job
        /* bridge */ /* synthetic */ default Void run(Object obj) throws Exception {
            return run2((VoidJob<II>) obj);
        }

        @Override // com.android.internal.infra.ServiceConnector.Job
        /* renamed from: run, reason: avoid collision after fix types in other method */
        default Void run2(II service) throws Exception {
            runNoResult(service);
            return null;
        }
    }

    public interface ServiceLifecycleCallbacks<II extends IInterface> {
        default void onConnected(II service) {
        }

        default void onDisconnected(II service) {
        }

        default void onBinderDied() {
        }
    }

    public static class Impl<I extends IInterface> extends ArrayDeque<Job<I, ?>> implements ServiceConnector<I>, ServiceConnection, IBinder.DeathRecipient, Runnable {
        static final boolean DEBUG = false;
        private static final long DEFAULT_DISCONNECT_TIMEOUT_MS = 15000;
        private static final long DEFAULT_REQUEST_TIMEOUT_MS = 30000;
        static final String LOG_TAG = "ServiceConnector.Impl";
        private final Function<IBinder, I> mBinderAsInterface;
        private final int mBindingFlags;
        protected final Context mContext;
        private final Intent mIntent;
        private final Queue<Job<I, ?>> mQueue = this;
        private final List<Impl<I>.CompletionAwareJob<I, ?>> mUnfinishedJobs = new ArrayList();
        private final Handler mMainHandler = new Handler(Looper.getMainLooper());
        private final ServiceConnection mServiceConnection = this;
        private final Runnable mTimeoutDisconnect = this;
        private volatile ServiceLifecycleCallbacks<I> mServiceLifecycleCallbacks = null;
        private volatile I mService = null;
        private boolean mBinding = false;
        private boolean mUnbinding = false;
        private Impl<I>.CompletionAwareJob<I, I> mServiceConnectionFutureCache = null;
        private final Handler mHandler = getJobHandler();
        protected final Executor mExecutor = new HandlerExecutor(this.mHandler);

        public Impl(Context context, Intent intent, int bindingFlags, int userId, Function<IBinder, I> binderAsInterface) {
            this.mContext = context.createContextAsUser(UserHandle.of(userId), 0);
            this.mIntent = intent;
            this.mBindingFlags = bindingFlags;
            this.mBinderAsInterface = binderAsInterface;
        }

        protected Handler getJobHandler() {
            return this.mMainHandler;
        }

        protected long getAutoDisconnectTimeoutMs() {
            return DEFAULT_DISCONNECT_TIMEOUT_MS;
        }

        protected long getRequestTimeoutMs() {
            return 30000L;
        }

        protected boolean bindService(ServiceConnection serviceConnection) {
            return this.mContext.bindService(this.mIntent, this.mBindingFlags | 1, this.mExecutor, serviceConnection);
        }

        protected I binderAsInterface(IBinder service) {
            return this.mBinderAsInterface.apply(service);
        }

        protected void onServiceUnbound() {
        }

        private void dispatchOnServiceConnectionStatusChanged(I service, boolean isConnected) {
            ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks = this.mServiceLifecycleCallbacks;
            if (serviceLifecycleCallbacks != null) {
                if (isConnected) {
                    serviceLifecycleCallbacks.onConnected(service);
                } else {
                    serviceLifecycleCallbacks.onDisconnected(service);
                }
            }
            onServiceConnectionStatusChanged(service, isConnected);
        }

        protected void onServiceConnectionStatusChanged(I service, boolean isConnected) {
        }

        @Override // com.android.internal.infra.ServiceConnector
        public boolean run(VoidJob<I> job) {
            return enqueue(job);
        }

        @Override // com.android.internal.infra.ServiceConnector
        public AndroidFuture<Void> post(VoidJob<I> job) {
            return postForResult((Job) job);
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> Impl<I>.CompletionAwareJob<I, R> postForResult(Job<I, R> job) {
            Impl<I>.CompletionAwareJob<I, R> task = new CompletionAwareJob<>();
            task.mDelegate = (Job) Objects.requireNonNull(job);
            enqueue((CompletionAwareJob) task);
            return task;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> AndroidFuture<R> postAsync(Job<I, CompletableFuture<R>> job) {
            Impl<I>.CompletionAwareJob<I, ?> completionAwareJob = new CompletionAwareJob<>();
            completionAwareJob.mDelegate = (Job) Objects.requireNonNull(job);
            completionAwareJob.mAsync = true;
            enqueue((CompletionAwareJob) completionAwareJob);
            return completionAwareJob;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public synchronized AndroidFuture<I> connect() {
            if (this.mServiceConnectionFutureCache == null) {
                this.mServiceConnectionFutureCache = new CompletionAwareJob<>();
                this.mServiceConnectionFutureCache.mDelegate = new Job() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda1
                    @Override // com.android.internal.infra.ServiceConnector.Job
                    public final Object run(Object obj) {
                        return ServiceConnector.Impl.lambda$connect$0((IInterface) obj);
                    }
                };
                I service = this.mService;
                if (service != null) {
                    this.mServiceConnectionFutureCache.complete(service);
                } else {
                    enqueue((CompletionAwareJob) this.mServiceConnectionFutureCache);
                }
            }
            return this.mServiceConnectionFutureCache;
        }

        static /* synthetic */ IInterface lambda$connect$0(IInterface s) throws Exception {
            return s;
        }

        private void enqueue(Impl<I>.CompletionAwareJob<I, ?> task) {
            if (!enqueue((Job) task)) {
                task.completeExceptionally(new IllegalStateException("Failed to post a job to handler. Likely " + this.mHandler.getLooper() + " is exiting"));
            }
        }

        private boolean enqueue(final Job<I, ?> job) {
            cancelTimeout();
            return this.mHandler.post(new Runnable() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceConnector.Impl.this.lambda$enqueue$1(job);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: enqueueJobThread, reason: merged with bridge method [inline-methods] */
        public void lambda$enqueue$1(Job<I, ?> job) {
            cancelTimeout();
            if (this.mUnbinding) {
                completeExceptionally(job, new IllegalStateException("Service is unbinding. Ignoring " + job));
                return;
            }
            if (!this.mQueue.offer(job)) {
                completeExceptionally(job, new IllegalStateException("Failed to add to queue: " + job));
                return;
            }
            if (isBound()) {
                processQueue();
            } else if (!this.mBinding) {
                if (bindService(this.mServiceConnection)) {
                    this.mBinding = true;
                } else {
                    completeExceptionally(job, new IllegalStateException("Failed to bind to service " + this.mIntent));
                }
            }
        }

        private void cancelTimeout() {
            this.mMainHandler.removeCallbacks(this.mTimeoutDisconnect);
        }

        void completeExceptionally(Job<?, ?> job, Throwable ex) {
            CompletionAwareJob task = (CompletionAwareJob) castOrNull(job, CompletionAwareJob.class);
            if (task != null) {
                task.completeExceptionally(ex);
            }
            if (task == null) {
                Log.e(LOG_TAG, "Job failed: " + job, ex);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static <BASE, T extends BASE> T castOrNull(BASE base, Class<T> cls) {
            if (cls.isInstance(base)) {
                return base;
            }
            return null;
        }

        private void processQueue() {
            I i;
            while (true) {
                Job<I, ?> poll = this.mQueue.poll();
                if (poll != null) {
                    Impl<I>.CompletionAwareJob<I, ?> completionAwareJob = (CompletionAwareJob) castOrNull(poll, CompletionAwareJob.class);
                    try {
                        i = this.mService;
                    } catch (Throwable th) {
                        completeExceptionally(poll, th);
                    }
                    if (i == null) {
                        return;
                    }
                    Object run = poll.run(i);
                    if (completionAwareJob != null) {
                        if (completionAwareJob.mAsync) {
                            this.mUnfinishedJobs.add(completionAwareJob);
                            ((CompletionStage) run).whenComplete(completionAwareJob);
                        } else {
                            completionAwareJob.complete(run);
                        }
                    }
                } else {
                    maybeScheduleUnbindTimeout();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void maybeScheduleUnbindTimeout() {
            if (this.mUnfinishedJobs.isEmpty() && this.mQueue.isEmpty()) {
                scheduleUnbindTimeout();
            }
        }

        private void scheduleUnbindTimeout() {
            long timeout = getAutoDisconnectTimeoutMs();
            if (timeout > 0) {
                this.mMainHandler.postDelayed(this.mTimeoutDisconnect, timeout);
            }
        }

        private boolean isBound() {
            return this.mService != null;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void unbind() {
            this.mUnbinding = true;
            this.mHandler.post(new Runnable() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceConnector.Impl.this.unbindJobThread();
                }
            });
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void setServiceLifecycleCallbacks(ServiceLifecycleCallbacks<I> callbacks) {
            this.mServiceLifecycleCallbacks = callbacks;
        }

        void unbindJobThread() {
            cancelTimeout();
            I service = this.mService;
            boolean wasBound = service != null;
            if (wasBound || this.mBinding) {
                try {
                    this.mContext.unbindService(this.mServiceConnection);
                } catch (IllegalArgumentException e) {
                    Slog.e(LOG_TAG, "Failed to unbind: " + e);
                }
            }
            if (wasBound) {
                dispatchOnServiceConnectionStatusChanged(service, false);
                service.asBinder().unlinkToDeath(this, 0);
                this.mService = null;
            }
            this.mBinding = false;
            this.mUnbinding = false;
            synchronized (this) {
                if (this.mServiceConnectionFutureCache != null) {
                    this.mServiceConnectionFutureCache.cancel(true);
                    this.mServiceConnectionFutureCache = null;
                }
            }
            cancelPendingJobs();
            if (wasBound) {
                onServiceUnbound();
            }
        }

        protected void cancelPendingJobs() {
            while (true) {
                Job<I, ?> job = this.mQueue.poll();
                if (job != null) {
                    CompletionAwareJob task = (CompletionAwareJob) castOrNull(job, CompletionAwareJob.class);
                    if (task != null) {
                        task.cancel(false);
                    }
                } else {
                    return;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            if (this.mUnbinding) {
                Log.i(LOG_TAG, "Ignoring onServiceConnected due to ongoing unbinding: " + this);
                return;
            }
            I service = binderAsInterface(binder);
            this.mService = service;
            this.mBinding = false;
            try {
                binder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "onServiceConnected " + name + ": ", e);
            }
            dispatchOnServiceConnectionStatusChanged(service, true);
            processQueue();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            this.mBinding = true;
            I service = this.mService;
            if (service != null) {
                dispatchOnServiceConnectionStatusChanged(service, false);
                this.mService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName name) {
            binderDied();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mService = null;
            unbind();
            dispatchOnBinderDied();
        }

        private void dispatchOnBinderDied() {
            ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks = this.mServiceLifecycleCallbacks;
            if (serviceLifecycleCallbacks != null) {
                serviceLifecycleCallbacks.onBinderDied();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            onTimeout();
        }

        private void onTimeout() {
            unbind();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder("ServiceConnector@").append(System.identityHashCode(this) % 1000).append(NavigationBarInflaterView.KEY_CODE_START).append(this.mIntent).append(", user: ").append(this.mContext.getUser().getIdentifier()).append(")[").append(stateToString());
            if (!this.mQueue.isEmpty()) {
                sb.append(", ").append(this.mQueue.size()).append(" pending job(s)");
            }
            if (!this.mUnfinishedJobs.isEmpty()) {
                sb.append(", ").append(this.mUnfinishedJobs.size()).append(" unfinished async job(s)");
            }
            return sb.append(NavigationBarInflaterView.SIZE_MOD_END).toString();
        }

        public void dump(String prefix, PrintWriter pw) {
            pw.append((CharSequence) prefix).append("ServiceConnector:").println();
            pw.append((CharSequence) prefix).append("  ").append((CharSequence) String.valueOf(this.mIntent)).println();
            pw.append((CharSequence) prefix).append("  ").append("userId: ").append((CharSequence) String.valueOf(this.mContext.getUser().getIdentifier())).println();
            pw.append((CharSequence) prefix).append("  ").append("State: ").append((CharSequence) stateToString()).println();
            pw.append((CharSequence) prefix).append("  ").append("Pending jobs: ").append((CharSequence) String.valueOf(this.mQueue.size())).println();
            pw.append((CharSequence) prefix).append("  ").append("Unfinished async jobs: ").append((CharSequence) String.valueOf(this.mUnfinishedJobs.size())).println();
        }

        private String stateToString() {
            if (this.mBinding) {
                return "Binding...";
            }
            if (this.mUnbinding) {
                return "Unbinding...";
            }
            if (isBound()) {
                return "Bound";
            }
            return "Unbound";
        }

        private void logTrace() {
            Log.i(LOG_TAG, "See stacktrace", new Throwable());
        }

        class CompletionAwareJob<II, R> extends AndroidFuture<R> implements Job<II, R>, BiConsumer<R, Throwable> {
            boolean mAsync = false;
            private String mDebugName;
            Job<II, R> mDelegate;

            CompletionAwareJob() {
                setTimeoutHandler(Impl.this.getJobHandler());
                long requestTimeout = Impl.this.getRequestTimeoutMs();
                if (requestTimeout > 0) {
                    orTimeout(requestTimeout, TimeUnit.MILLISECONDS);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.function.BiConsumer
            public /* bridge */ /* synthetic */ void accept(Object obj, Throwable th) {
                accept2((CompletionAwareJob<II, R>) obj, th);
            }

            private static /* synthetic */ boolean lambda$new$0(StackTraceElement st) {
                return !st.getClassName().contains(ServiceConnector.class.getName());
            }

            @Override // com.android.internal.infra.ServiceConnector.Job
            public R run(II service) throws Exception {
                return this.mDelegate.run(service);
            }

            @Override // com.android.internal.infra.AndroidFuture, java.util.concurrent.CompletableFuture, java.util.concurrent.Future
            public boolean cancel(boolean mayInterruptIfRunning) {
                if (mayInterruptIfRunning) {
                    Log.w(Impl.LOG_TAG, "mayInterruptIfRunning not supported - ignoring");
                }
                boolean wasRemoved = Impl.this.mQueue.remove(this);
                return super.cancel(mayInterruptIfRunning) || wasRemoved;
            }

            @Override // java.util.concurrent.CompletableFuture
            public String toString() {
                return this.mDelegate + " wrapped into " + super.toString();
            }

            /* renamed from: accept, reason: avoid collision after fix types in other method */
            public void accept2(R res, Throwable err) {
                if (err != null) {
                    completeExceptionally(err);
                } else {
                    complete(res);
                }
            }

            @Override // com.android.internal.infra.AndroidFuture
            protected void onCompleted(R res, Throwable err) {
                super.onCompleted(res, err);
                if (Impl.this.mUnfinishedJobs.remove(this)) {
                    Impl.this.maybeScheduleUnbindTimeout();
                }
            }
        }
    }

    public static class NoOp<T extends IInterface> extends AndroidFuture<Object> implements ServiceConnector<T> {
        public NoOp() {
            completeExceptionally(new IllegalStateException("ServiceConnector is a no-op"));
        }

        @Override // com.android.internal.infra.ServiceConnector
        public boolean run(VoidJob<T> job) {
            return false;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public AndroidFuture<Void> post(VoidJob<T> job) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> AndroidFuture<R> postForResult(Job<T, R> job) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> AndroidFuture<R> postAsync(Job<T, CompletableFuture<R>> job) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public AndroidFuture<T> connect() {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void unbind() {
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void setServiceLifecycleCallbacks(ServiceLifecycleCallbacks<T> callbacks) {
        }
    }
}

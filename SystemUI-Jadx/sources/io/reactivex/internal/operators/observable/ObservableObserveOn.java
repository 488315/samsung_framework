package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class ObservableObserveOn extends AbstractObservableWithUpstream {
    public final int bufferSize;
    public final boolean delayError;
    public final Scheduler scheduler;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    final class ObserveOnObserver<T> extends BasicIntQueueDisposable<T> implements Observer, Runnable {
        private static final long serialVersionUID = 6576896619930983584L;
        final int bufferSize;
        final boolean delayError;
        volatile boolean disposed;
        volatile boolean done;
        final Observer downstream;
        Throwable error;
        boolean outputFused;
        SimpleQueue queue;
        int sourceMode;
        Disposable upstream;
        final Scheduler.Worker worker;

        public ObserveOnObserver(Observer observer, Scheduler.Worker worker, boolean z, int i) {
            this.downstream = observer;
            this.worker = worker;
            this.delayError = z;
            this.bufferSize = i;
        }

        public final boolean checkTerminated(boolean z, boolean z2, Observer observer) {
            if (this.disposed) {
                this.queue.clear();
                return true;
            }
            if (z) {
                Throwable th = this.error;
                if (this.delayError) {
                    if (z2) {
                        this.disposed = true;
                        if (th != null) {
                            observer.onError(th);
                        } else {
                            observer.onComplete();
                        }
                        this.worker.dispose();
                        return true;
                    }
                    return false;
                }
                if (th != null) {
                    this.disposed = true;
                    this.queue.clear();
                    observer.onError(th);
                    this.worker.dispose();
                    return true;
                }
                if (z2) {
                    this.disposed = true;
                    observer.onComplete();
                    this.worker.dispose();
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.upstream.dispose();
                this.worker.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.Observer
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 2) {
                this.queue.offer(obj);
            }
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion();
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        if (getAndIncrement() == 0) {
                            this.worker.schedule(this);
                            return;
                        }
                        return;
                    }
                    if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final Object poll() {
            return this.queue.poll();
        }

        @Override // io.reactivex.internal.fuseable.QueueDisposable
        public final int requestFusion() {
            this.outputFused = true;
            return 2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x0075, code lost:
        
            r3 = addAndGet(-r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x007a, code lost:
        
            if (r3 != 0) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
        
            return;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r7 = this;
                boolean r0 = r7.outputFused
                r1 = 1
                if (r0 == 0) goto L4f
                r0 = r1
            L6:
                boolean r2 = r7.disposed
                if (r2 == 0) goto Lc
                goto L97
            Lc:
                boolean r2 = r7.done
                java.lang.Throwable r3 = r7.error
                boolean r4 = r7.delayError
                if (r4 != 0) goto L28
                if (r2 == 0) goto L28
                if (r3 == 0) goto L28
                r7.disposed = r1
                io.reactivex.Observer r0 = r7.downstream
                java.lang.Throwable r1 = r7.error
                r0.onError(r1)
                io.reactivex.Scheduler$Worker r7 = r7.worker
                r7.dispose()
                goto L97
            L28:
                io.reactivex.Observer r3 = r7.downstream
                r4 = 0
                r3.onNext(r4)
                if (r2 == 0) goto L47
                r7.disposed = r1
                java.lang.Throwable r0 = r7.error
                if (r0 == 0) goto L3c
                io.reactivex.Observer r1 = r7.downstream
                r1.onError(r0)
                goto L41
            L3c:
                io.reactivex.Observer r0 = r7.downstream
                r0.onComplete()
            L41:
                io.reactivex.Scheduler$Worker r7 = r7.worker
                r7.dispose()
                goto L97
            L47:
                int r0 = -r0
                int r0 = r7.addAndGet(r0)
                if (r0 != 0) goto L6
                goto L97
            L4f:
                io.reactivex.internal.fuseable.SimpleQueue r0 = r7.queue
                io.reactivex.Observer r2 = r7.downstream
                r3 = r1
            L54:
                boolean r4 = r7.done
                boolean r5 = r0.isEmpty()
                boolean r4 = r7.checkTerminated(r4, r5, r2)
                if (r4 == 0) goto L61
                goto L97
            L61:
                boolean r4 = r7.done
                java.lang.Object r5 = r0.poll()     // Catch: java.lang.Throwable -> L81
                if (r5 != 0) goto L6b
                r6 = r1
                goto L6c
            L6b:
                r6 = 0
            L6c:
                boolean r4 = r7.checkTerminated(r4, r6, r2)
                if (r4 == 0) goto L73
                goto L97
            L73:
                if (r6 == 0) goto L7d
                int r3 = -r3
                int r3 = r7.addAndGet(r3)
                if (r3 != 0) goto L54
                goto L97
            L7d:
                r2.onNext(r5)
                goto L61
            L81:
                r3 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r3)
                r7.disposed = r1
                io.reactivex.disposables.Disposable r1 = r7.upstream
                r1.dispose()
                r0.clear()
                r2.onError(r3)
                io.reactivex.Scheduler$Worker r7 = r7.worker
                r7.dispose()
            L97:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableObserveOn.ObserveOnObserver.run():void");
        }
    }

    public ObservableObserveOn(ObservableSource observableSource, Scheduler scheduler, boolean z, int i) {
        super(observableSource);
        this.scheduler = scheduler;
        this.delayError = z;
        this.bufferSize = i;
    }

    @Override // io.reactivex.Observable
    public final void subscribeActual(Observer observer) {
        Scheduler scheduler = this.scheduler;
        boolean z = scheduler instanceof TrampolineScheduler;
        ObservableSource observableSource = this.source;
        if (z) {
            ((Observable) observableSource).subscribe(observer);
        } else {
            ((Observable) observableSource).subscribe(new ObserveOnObserver(observer, scheduler.createWorker(), this.delayError, this.bufferSize));
        }
    }
}
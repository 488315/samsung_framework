package android.media;

import android.media.CallbackUtil;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
class CallbackUtil {
    private static final String TAG = "CallbackUtil";

    interface CallbackMethod<T> {
        void callbackMethod(T t);
    }

    interface DispatcherStub {
        void register(boolean z);
    }

    CallbackUtil() {
    }

    static class ListenerInfo<T> {
        final Executor mExecutor;
        final T mListener;

        ListenerInfo(T listener, Executor exe) {
            this.mListener = listener;
            this.mExecutor = exe;
        }
    }

    static <T> ListenerInfo<T> getListenerInfo(T listener, ArrayList<ListenerInfo<T>> listeners) {
        if (listeners == null) {
            return null;
        }
        Iterator<ListenerInfo<T>> it = listeners.iterator();
        while (it.hasNext()) {
            ListenerInfo<T> info = it.next();
            if (info.mListener == listener) {
                return info;
            }
        }
        return null;
    }

    static <T> boolean hasListener(T listener, ArrayList<ListenerInfo<T>> listeners) {
        return getListenerInfo(listener, listeners) != null;
    }

    static <T> boolean removeListener(T listener, ArrayList<ListenerInfo<T>> listeners) {
        ListenerInfo<T> infoToRemove = getListenerInfo(listener, listeners);
        if (infoToRemove != null) {
            listeners.remove(infoToRemove);
            return true;
        }
        return false;
    }

    static <T, S> Pair<ArrayList<ListenerInfo<T>>, S> addListener(String methodName, Executor executor, T listener, ArrayList<ListenerInfo<T>> listeners, S dispatchStub, Supplier<S> newStub, Consumer<S> registerStub) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(listener);
        if (hasListener(listener, listeners)) {
            throw new IllegalArgumentException("attempt to call " + methodName + "on a previously registered listener");
        }
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        if (listeners.size() == 0) {
            if (dispatchStub == null) {
                try {
                    dispatchStub = newStub.get();
                } catch (Exception e) {
                    Log.e(TAG, "Exception while creating stub in " + methodName, e);
                    return new Pair<>(null, null);
                }
            }
            registerStub.accept(dispatchStub);
        }
        listeners.add(new ListenerInfo<>(listener, executor));
        return new Pair<>(listeners, dispatchStub);
    }

    static <T, S> Pair<ArrayList<ListenerInfo<T>>, S> removeListener(String methodName, T listener, ArrayList<ListenerInfo<T>> listeners, S dispatchStub, Consumer<S> unregisterStub) {
        Objects.requireNonNull(listener);
        if (!removeListener(listener, listeners)) {
            throw new IllegalArgumentException("attempt to call " + methodName + " on an unregistered listener");
        }
        if (listeners.size() == 0) {
            unregisterStub.accept(dispatchStub);
            return new Pair<>(null, null);
        }
        return new Pair<>(listeners, dispatchStub);
    }

    static <T> void callListeners(ArrayList<ListenerInfo<T>> listeners, Object listenerLock, final CallbackMethod<T> callback) {
        Objects.requireNonNull(listenerLock);
        synchronized (listenerLock) {
            if (listeners != null) {
                if (listeners.size() != 0) {
                    ArrayList<ListenerInfo<T>> listenersShallowCopy = (ArrayList) listeners.clone();
                    SafeCloseable ignored = ClearCallingIdentityContext.create();
                    try {
                        Iterator<ListenerInfo<T>> it = listenersShallowCopy.iterator();
                        while (it.hasNext()) {
                            final ListenerInfo<T> info = it.next();
                            info.mExecutor.execute(new Runnable() { // from class: android.media.CallbackUtil$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CallbackUtil.CallbackMethod.this.callbackMethod(info.mListener);
                                }
                            });
                        }
                        if (ignored != null) {
                            ignored.close();
                        }
                    } catch (Throwable th) {
                        if (ignored != null) {
                            try {
                                ignored.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
            }
        }
    }

    static class LazyListenerManager<T> {
        private DispatcherStub mDispatcherStub;
        private final Object mListenerLock = new Object();
        private ArrayList<ListenerInfo<T>> mListeners;

        LazyListenerManager() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        void addListener(Executor executor, T t, String str, Supplier<DispatcherStub> supplier) {
            synchronized (this.mListenerLock) {
                Pair addListener = CallbackUtil.addListener(str, executor, t, this.mListeners, this.mDispatcherStub, supplier, new Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((CallbackUtil.DispatcherStub) obj).register(true);
                    }
                });
                this.mListeners = (ArrayList<ListenerInfo<T>>) addListener.first;
                this.mDispatcherStub = (DispatcherStub) addListener.second;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeListener(T t, String str) {
            synchronized (this.mListenerLock) {
                Pair removeListener = CallbackUtil.removeListener(str, t, this.mListeners, this.mDispatcherStub, new Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((CallbackUtil.DispatcherStub) obj).register(false);
                    }
                });
                this.mListeners = (ArrayList<ListenerInfo<T>>) removeListener.first;
                this.mDispatcherStub = (DispatcherStub) removeListener.second;
            }
        }

        void callListeners(CallbackMethod<T> callback) {
            CallbackUtil.callListeners(this.mListeners, this.mListenerLock, callback);
        }
    }
}

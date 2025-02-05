package android.app.search;

import android.annotation.SystemApi;
import android.app.search.ISearchCallback;
import android.app.search.ISearchUiManager;
import android.app.search.SearchSession;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public final class SearchSession implements AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = SearchSession.class.getSimpleName();
    private final ISearchUiManager mInterface;
    private final SearchSessionId mSessionId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final IBinder mToken = new Binder();
    private final ArrayMap<Callback, CallbackWrapper> mRegisteredCallbacks = new ArrayMap<>();

    public interface Callback {
        void onTargetsAvailable(List<SearchTarget> list);
    }

    SearchSession(Context context, SearchContext searchContext) {
        IBinder b = ServiceManager.getService(Context.SEARCH_UI_SERVICE);
        this.mInterface = ISearchUiManager.Stub.asInterface(b);
        this.mSessionId = new SearchSessionId(context.getPackageName() + ":" + UUID.randomUUID().toString(), context.getUserId());
        searchContext.setPackageName(context.getPackageName());
        try {
            this.mInterface.createSearchSession(searchContext, this.mSessionId, this.mToken);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to search session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SearchSession.close");
    }

    public void notifyEvent(Query query, SearchTargetEvent event) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifyEvent(this.mSessionId, query, event);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify event", e);
            e.rethrowFromSystemServer();
        }
    }

    public void query(Query input, Executor callbackExecutor, Consumer<List<SearchTarget>> callback) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.query(this.mSessionId, input, new CallbackWrapper(callbackExecutor, callback));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sort targets", e);
            e.rethrowFromSystemServer();
        }
    }

    public void registerEmptyQueryResultUpdateCallback(Executor callbackExecutor, final Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                return;
            }
            try {
                Objects.requireNonNull(callback);
                CallbackWrapper callbackWrapper = new CallbackWrapper(callbackExecutor, new Consumer() { // from class: android.app.search.SearchSession$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SearchSession.Callback.this.onTargetsAvailable((List) obj);
                    }
                });
                this.mInterface.registerEmptyQueryResultUpdateCallback(this.mSessionId, callbackWrapper);
                this.mRegisteredCallbacks.put(callback, callbackWrapper);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to register for empty query result updates", e);
                e.rethrowAsRuntimeException();
            }
        }
    }

    public void unregisterEmptyQueryResultUpdateCallback(Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                try {
                    CallbackWrapper callbackWrapper = this.mRegisteredCallbacks.remove(callback);
                    this.mInterface.unregisterEmptyQueryResultUpdateCallback(this.mSessionId, callbackWrapper);
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to unregister for empty query result updates", e);
                    e.rethrowAsRuntimeException();
                }
            }
        }
    }

    @Deprecated
    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mInterface.destroySearchSession(this.mSessionId);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify search target event", e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    protected void finalize() {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
            try {
                super.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                super.finalize();
            } catch (Throwable throwable2) {
                throwable2.printStackTrace();
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    static class CallbackWrapper extends ISearchCallback.Stub {
        private final Consumer<List<SearchTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor callbackExecutor, Consumer<List<SearchTarget>> callback) {
            this.mCallback = callback;
            this.mExecutor = callbackExecutor;
        }

        @Override // android.app.search.ISearchCallback
        public void onResult(ParceledListSlice result) {
            Bundle bundle;
            long identity = Binder.clearCallingIdentity();
            try {
                final List<SearchTarget> list = result.getList();
                if (list.size() > 0 && (bundle = list.get(0).getExtras()) != null) {
                    bundle.putLong("key_ipc_start", SystemClock.elapsedRealtime());
                }
                this.mExecutor.execute(new Runnable() { // from class: android.app.search.SearchSession$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchSession.CallbackWrapper.this.lambda$onResult$0(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(List list) {
            this.mCallback.accept(list);
        }
    }
}

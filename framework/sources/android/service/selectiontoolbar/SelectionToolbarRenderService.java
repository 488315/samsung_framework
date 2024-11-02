package android.service.selectiontoolbar;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.selectiontoolbar.ISelectionToolbarRenderService;
import android.service.selectiontoolbar.ISelectionToolbarRenderServiceCallback;
import android.service.selectiontoolbar.SelectionToolbarRenderService;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.selectiontoolbar.ISelectionToolbarCallback;
import android.view.selectiontoolbar.ShowInfo;
import android.view.selectiontoolbar.ToolbarMenuItem;
import android.view.selectiontoolbar.WidgetInfo;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public abstract class SelectionToolbarRenderService extends Service {
    private static final int CACHE_CLEAN_AFTER_SHOW_TIMEOUT_IN_MS = 600000;
    public static final String SERVICE_INTERFACE = "android.service.selectiontoolbar.SelectionToolbarRenderService";
    private static final String TAG = "SelectionToolbarRenderService";
    private Handler mHandler;
    private ISelectionToolbarRenderServiceCallback mServiceCallback;
    private final SparseArray<Pair<RemoteCallbackWrapper, CleanCacheRunnable>> mCache = new SparseArray<>();
    private final ISelectionToolbarRenderService mInterface = new AnonymousClass1();

    /* loaded from: classes3.dex */
    public interface TransferTouchListener {
        void onTransferTouch(IBinder iBinder, IBinder iBinder2);
    }

    public abstract void onDismiss(long j);

    public abstract void onHide(long j);

    public abstract void onShow(int i, ShowInfo showInfo, RemoteCallbackWrapper remoteCallbackWrapper);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.service.selectiontoolbar.SelectionToolbarRenderService$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends ISelectionToolbarRenderService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
        public void onShow(int callingUid, ShowInfo showInfo, ISelectionToolbarCallback callback) {
            if (SelectionToolbarRenderService.this.mCache.indexOfKey(callingUid) < 0) {
                SelectionToolbarRenderService.this.mCache.put(callingUid, new Pair(new RemoteCallbackWrapper(callback), new CleanCacheRunnable(callingUid)));
            }
            Pair<RemoteCallbackWrapper, CleanCacheRunnable> toolbarPair = (Pair) SelectionToolbarRenderService.this.mCache.get(callingUid);
            CleanCacheRunnable cleanRunnable = toolbarPair.second;
            SelectionToolbarRenderService.this.mHandler.removeCallbacks(cleanRunnable);
            SelectionToolbarRenderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.selectiontoolbar.SelectionToolbarRenderService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((SelectionToolbarRenderService) obj).onShow(((Integer) obj2).intValue(), (ShowInfo) obj3, (SelectionToolbarRenderService.RemoteCallbackWrapper) obj4);
                }
            }, SelectionToolbarRenderService.this, Integer.valueOf(callingUid), showInfo, toolbarPair.first));
            SelectionToolbarRenderService.this.mHandler.postDelayed(cleanRunnable, 600000L);
        }

        @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
        public void onHide(long widgetToken) {
            SelectionToolbarRenderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.selectiontoolbar.SelectionToolbarRenderService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SelectionToolbarRenderService) obj).onHide(((Long) obj2).longValue());
                }
            }, SelectionToolbarRenderService.this, Long.valueOf(widgetToken)));
        }

        @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
        public void onDismiss(int callingUid, long widgetToken) {
            SelectionToolbarRenderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.selectiontoolbar.SelectionToolbarRenderService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SelectionToolbarRenderService) obj).onDismiss(((Long) obj2).longValue());
                }
            }, SelectionToolbarRenderService.this, Long.valueOf(widgetToken)));
            Pair<RemoteCallbackWrapper, CleanCacheRunnable> toolbarPair = (Pair) SelectionToolbarRenderService.this.mCache.get(callingUid);
            if (toolbarPair != null) {
                SelectionToolbarRenderService.this.mHandler.removeCallbacks(toolbarPair.second);
                SelectionToolbarRenderService.this.mCache.remove(callingUid);
            }
        }

        @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
        public void onConnected(IBinder callback) {
            SelectionToolbarRenderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.selectiontoolbar.SelectionToolbarRenderService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SelectionToolbarRenderService) obj).handleOnConnected((IBinder) obj2);
                }
            }, SelectionToolbarRenderService.this, callback));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.selectiontoolbar.SelectionToolbarRenderService: " + intent);
        return null;
    }

    public void handleOnConnected(IBinder callback) {
        this.mServiceCallback = ISelectionToolbarRenderServiceCallback.Stub.asInterface(callback);
    }

    public void transferTouch(IBinder source, IBinder target) {
        ISelectionToolbarRenderServiceCallback callback = this.mServiceCallback;
        if (callback == null) {
            Log.e(TAG, "transferTouch(): no server callback");
        } else {
            try {
                callback.transferTouch(source, target);
            } catch (RemoteException e) {
            }
        }
    }

    public void onToolbarShowTimeout(int callingUid) {
    }

    /* loaded from: classes3.dex */
    public static final class RemoteCallbackWrapper implements SelectionToolbarRenderCallback {
        private final ISelectionToolbarCallback mRemoteCallback;

        RemoteCallbackWrapper(ISelectionToolbarCallback remoteCallback) {
            this.mRemoteCallback = remoteCallback;
        }

        @Override // android.service.selectiontoolbar.SelectionToolbarRenderCallback
        public void onShown(WidgetInfo widgetInfo) {
            try {
                this.mRemoteCallback.onShown(widgetInfo);
            } catch (RemoteException e) {
            }
        }

        @Override // android.service.selectiontoolbar.SelectionToolbarRenderCallback
        public void onToolbarShowTimeout() {
            try {
                this.mRemoteCallback.onToolbarShowTimeout();
            } catch (RemoteException e) {
            }
        }

        @Override // android.service.selectiontoolbar.SelectionToolbarRenderCallback
        public void onWidgetUpdated(WidgetInfo widgetInfo) {
            try {
                this.mRemoteCallback.onWidgetUpdated(widgetInfo);
            } catch (RemoteException e) {
            }
        }

        @Override // android.service.selectiontoolbar.SelectionToolbarRenderCallback
        public void onMenuItemClicked(ToolbarMenuItem item) {
            try {
                this.mRemoteCallback.onMenuItemClicked(item);
            } catch (RemoteException e) {
            }
        }

        @Override // android.service.selectiontoolbar.SelectionToolbarRenderCallback
        public void onError(int errorCode) {
            try {
                this.mRemoteCallback.onError(errorCode);
            } catch (RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class CleanCacheRunnable implements Runnable {
        int mCleanUid;

        CleanCacheRunnable(int cleanUid) {
            this.mCleanUid = cleanUid;
        }

        @Override // java.lang.Runnable
        public void run() {
            Pair<RemoteCallbackWrapper, CleanCacheRunnable> toolbarPair = (Pair) SelectionToolbarRenderService.this.mCache.get(this.mCleanUid);
            if (toolbarPair != null) {
                Log.w(SelectionToolbarRenderService.TAG, "CleanCacheRunnable: remove " + this.mCleanUid + " from cache.");
                SelectionToolbarRenderService.this.mCache.remove(this.mCleanUid);
                SelectionToolbarRenderService.this.onToolbarShowTimeout(this.mCleanUid);
            }
        }
    }
}

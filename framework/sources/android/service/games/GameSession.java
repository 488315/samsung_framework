package android.service.games;

import android.annotation.SystemApi;
import android.app.ActivityTaskManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.games.GameSession;
import android.service.games.IGameSession;
import android.util.Slog;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class GameSession {
    private static final boolean DEBUG = false;
    private static final String TAG = "GameSession";
    private Context mContext;
    private IGameSessionController mGameSessionController;
    private GameSessionRootView mGameSessionRootView;
    private SurfaceControlViewHost mSurfaceControlViewHost;
    private int mTaskId;
    final IGameSession mInterface = new AnonymousClass1();
    private LifecycleState mLifecycleState = LifecycleState.INITIALIZED;
    private boolean mAreTransientInsetsVisibleDueToGesture = false;

    /* loaded from: classes3.dex */
    public enum LifecycleState {
        INITIALIZED,
        CREATED,
        TASK_FOCUSED,
        TASK_UNFOCUSED,
        DESTROYED
    }

    /* loaded from: classes3.dex */
    public interface ScreenshotCallback {
        public static final int ERROR_TAKE_SCREENSHOT_INTERNAL_ERROR = 0;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes3.dex */
        public @interface ScreenshotFailureStatus {
        }

        void onFailure(int i);

        void onSuccess();
    }

    /* renamed from: android.service.games.GameSession$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends IGameSession.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameSession
        public void onDestroyed() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((GameSession) obj).doDestroy();
                }
            }, GameSession.this));
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean visibleDueToGesture) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((GameSession) obj).dispatchTransientSystemBarVisibilityFromRevealGestureChanged(((Boolean) obj2).booleanValue());
                }
            }, GameSession.this, Boolean.valueOf(visibleDueToGesture)));
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean focused) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((GameSession) obj).moveToState((GameSession.LifecycleState) obj2);
                }
            }, GameSession.this, focused ? LifecycleState.TASK_FOCUSED : LifecycleState.TASK_UNFOCUSED));
        }
    }

    public void attach(IGameSessionController gameSessionController, int taskId, Context context, SurfaceControlViewHost surfaceControlViewHost, int widthPx, int heightPx) {
        this.mGameSessionController = gameSessionController;
        this.mTaskId = taskId;
        this.mContext = context;
        this.mSurfaceControlViewHost = surfaceControlViewHost;
        GameSessionRootView gameSessionRootView = new GameSessionRootView(context, this.mSurfaceControlViewHost);
        this.mGameSessionRootView = gameSessionRootView;
        surfaceControlViewHost.setView(gameSessionRootView, widthPx, heightPx);
    }

    public void doCreate() {
        moveToState(LifecycleState.CREATED);
    }

    public void doDestroy() {
        this.mSurfaceControlViewHost.release();
        moveToState(LifecycleState.DESTROYED);
    }

    public void dispatchTransientSystemBarVisibilityFromRevealGestureChanged(boolean visibleDueToGesture) {
        boolean didValueChange = this.mAreTransientInsetsVisibleDueToGesture != visibleDueToGesture;
        this.mAreTransientInsetsVisibleDueToGesture = visibleDueToGesture;
        if (didValueChange) {
            onTransientSystemBarVisibilityFromRevealGestureChanged(visibleDueToGesture);
        }
    }

    public void moveToState(LifecycleState newLifecycleState) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("moveToState should be used only from the main thread");
        }
        if (this.mLifecycleState == newLifecycleState) {
            return;
        }
        switch (AnonymousClass2.$SwitchMap$android$service$games$GameSession$LifecycleState[this.mLifecycleState.ordinal()]) {
            case 1:
                if (newLifecycleState == LifecycleState.CREATED) {
                    onCreate();
                    break;
                } else if (newLifecycleState == LifecycleState.DESTROYED) {
                    onCreate();
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case 2:
                if (newLifecycleState == LifecycleState.TASK_FOCUSED) {
                    onGameTaskFocusChanged(true);
                    break;
                } else if (newLifecycleState == LifecycleState.DESTROYED) {
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case 3:
                if (newLifecycleState == LifecycleState.TASK_UNFOCUSED) {
                    onGameTaskFocusChanged(false);
                    break;
                } else if (newLifecycleState == LifecycleState.DESTROYED) {
                    onGameTaskFocusChanged(false);
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case 4:
                if (newLifecycleState == LifecycleState.TASK_FOCUSED) {
                    onGameTaskFocusChanged(true);
                    break;
                } else if (newLifecycleState == LifecycleState.DESTROYED) {
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case 5:
                return;
        }
        this.mLifecycleState = newLifecycleState;
    }

    /* renamed from: android.service.games.GameSession$2 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$service$games$GameSession$LifecycleState;

        static {
            int[] iArr = new int[LifecycleState.values().length];
            $SwitchMap$android$service$games$GameSession$LifecycleState = iArr;
            try {
                iArr[LifecycleState.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$service$games$GameSession$LifecycleState[LifecycleState.CREATED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$service$games$GameSession$LifecycleState[LifecycleState.TASK_FOCUSED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$service$games$GameSession$LifecycleState[LifecycleState.TASK_UNFOCUSED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$service$games$GameSession$LifecycleState[LifecycleState.DESTROYED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public void onGameTaskFocusChanged(boolean focused) {
    }

    public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean visibleDueToGesture) {
    }

    public void setTaskOverlayView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mGameSessionRootView.removeAllViews();
        this.mGameSessionRootView.addView(view, layoutParams);
    }

    public final boolean restartGame() {
        try {
            this.mGameSessionController.restartGame(this.mTaskId);
            return true;
        } catch (RemoteException e) {
            Slog.w(TAG, "Failed to restart game", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class GameSessionRootView extends FrameLayout {
        private final SurfaceControlViewHost mSurfaceControlViewHost;

        GameSessionRootView(Context context, SurfaceControlViewHost surfaceControlViewHost) {
            super(context);
            this.mSurfaceControlViewHost = surfaceControlViewHost;
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            Rect bounds = newConfig.windowConfiguration.getBounds();
            this.mSurfaceControlViewHost.relayout(bounds.width(), bounds.height());
        }
    }

    public void takeScreenshot(Executor executor, final ScreenshotCallback callback) {
        if (this.mGameSessionController == null) {
            throw new IllegalStateException("Can not call before onCreate()");
        }
        AndroidFuture<GameScreenshotResult> takeScreenshotResult = new AndroidFuture().whenCompleteAsync(new BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GameSession.this.lambda$takeScreenshot$0(callback, (GameScreenshotResult) obj, (Throwable) obj2);
            }
        }, executor);
        try {
            this.mGameSessionController.takeScreenshot(this.mTaskId, takeScreenshotResult);
        } catch (RemoteException ex) {
            takeScreenshotResult.completeExceptionally(ex);
        }
    }

    /* renamed from: handleScreenshotResult */
    public void lambda$takeScreenshot$0(ScreenshotCallback callback, GameScreenshotResult result, Throwable error) {
        if (error != null) {
            Slog.w(TAG, error.getMessage(), error.getCause());
            callback.onFailure(0);
            return;
        }
        int status = result.getStatus();
        switch (status) {
            case 0:
                callback.onSuccess();
                return;
            case 1:
                Slog.w(TAG, "Error taking screenshot");
                callback.onFailure(0);
                return;
            default:
                return;
        }
    }

    public final void startActivityFromGameSessionForResult(Intent intent, Bundle options, Executor executor, final GameSessionActivityCallback callback) {
        Objects.requireNonNull(intent);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        AndroidFuture<GameSessionActivityResult> future = new AndroidFuture().whenCompleteAsync(new BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GameSession.lambda$startActivityFromGameSessionForResult$1(GameSessionActivityCallback.this, (GameSessionActivityResult) obj, (Throwable) obj2);
            }
        }, executor);
        Intent trampolineIntent = GameSessionTrampolineActivity.createIntent(intent, options, future);
        try {
            int result = ActivityTaskManager.getService().startActivityFromGameSession(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), TAG, Binder.getCallingPid(), Binder.getCallingUid(), trampolineIntent, this.mTaskId, UserHandle.myUserId());
            Instrumentation.checkStartActivityResult(result, trampolineIntent);
        } catch (Throwable t) {
            executor.execute(new Runnable() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GameSessionActivityCallback.this.onActivityStartFailed(t);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$startActivityFromGameSessionForResult$1(GameSessionActivityCallback callback, GameSessionActivityResult result, Throwable ex) {
        if (ex != null) {
            callback.onActivityStartFailed(ex);
        } else {
            callback.onActivityResult(result.getResultCode(), result.getData());
        }
    }
}

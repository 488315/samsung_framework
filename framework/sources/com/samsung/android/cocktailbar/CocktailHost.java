package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.cocktailbar.ICocktailHost;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class CocktailHost {
    static final int HANDLE_COCKTAIL_CLOSE = 5;
    static final int HANDLE_COCKTAIL_PARTIALLY_UPDATE = 2;
    static final int HANDLE_COCKTAIL_PARTIALLY_UPDATE_HELPVIEW = 14;
    static final int HANDLE_COCKTAIL_REMOVE = 3;
    static final int HANDLE_COCKTAIL_SEND_EXTRA_DATA = 12;
    static final int HANDLE_COCKTAIL_SET_PULL_TO_REFRESH = 13;
    static final int HANDLE_COCKTAIL_SHOW = 4;
    static final int HANDLE_COCKTAIL_SWITCH_DEFAULT = 10;
    static final int HANDLE_COCKTAIL_TICKER_DISABLE = 9;
    static final int HANDLE_COCKTAIL_UPDATE = 1;
    static final int HANDLE_COCKTAIL_UPDATE_EXTRA = 8;
    static final int HANDLE_COCKTAIL_UPDATE_TOOL_LAUNCHER = 7;
    static final int HANDLE_COCKTAIL_VIEW_DATA_CHANGED = 6;
    static final int HANDLE_NOTE_PAUSE_COMPONENT = 104;
    static final int HANDLE_NOTE_RESUME_COMPONENT = 103;
    static final int HANDLE_NOTIFY_CHANGE_VISIBLE_EDGE_SERVICE = 102;
    static final int HANDLE_NOTIFY_KEYGUARD_STATE = 100;
    static final int HANDLE_NOTIFY_WAKEUP_STATE = 101;
    static final int HANDLE_PACKAGE_SUSPEND_CHANGED = 105;
    static ICocktailBarService sService;
    ICallbackListener mCallbackListener;
    private final Callbacks mCallbacks;
    private String mContextOpPackageName;
    private final Handler mHandler;
    private int mListeningCategory;
    private static final String TAG = CocktailHost.class.getSimpleName();
    static final Object sServiceLock = new Object();

    /* loaded from: classes5.dex */
    public interface ICallbackListener {
        void onChangeVisibleEdgeService(boolean z, int i);

        void onCloseCocktail(int i, int i2, int i3);

        void onNotePauseComponent(ComponentName componentName);

        void onNoteResumeComponent(ComponentName componentName);

        void onNotifyKeyguardState(boolean z, int i);

        void onNotifyWakeUpModeState(boolean z, int i, int i2);

        void onPackageSuspendChanged(Cocktail cocktail);

        void onPartiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2);

        void onPartiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2);

        void onRemoveCocktail(int i, int i2);

        void onSendExtraDataToCocktailBar(Bundle bundle, int i);

        void onSetDisableTickerView(int i, int i2);

        void onSetPullToRefresh(int i, int i2, PendingIntent pendingIntent);

        void onShowCocktail(int i, int i2);

        void onSwitchDefaultCocktail(int i);

        void onUpdateCocktail(int i, Cocktail cocktail, int i2);

        void onUpdateToolLauncher(int i);

        void onViewDataChanged(int i, int i2, int i3);
    }

    /* loaded from: classes5.dex */
    public static class Callbacks extends ICocktailHost.Stub {
        private final WeakReference<Handler> mWeakHandler;

        public Callbacks(Handler handler) {
            this.mWeakHandler = new WeakReference<>(handler);
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateCocktail(int cocktailId, Cocktail cocktail, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(1, cocktailId, userId, cocktail);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateCocktail(int cocktailId, RemoteViews contentView, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(2, cocktailId, userId, contentView);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateHelpView(int cocktailId, RemoteViews helpView, int userId) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(14, cocktailId, userId, helpView);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void removeCocktail(int cocktailId, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(3, cocktailId, userId);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void showCocktail(int cocktailId, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(4, cocktailId, userId);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void closeCocktail(int cocktailId, int category, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(5, cocktailId, category, Integer.valueOf(userId));
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void viewDataChanged(int cocktailId, int viewId, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(6, cocktailId, viewId, Integer.valueOf(userId));
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateToolLauncher(int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(7, userId, 0);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyKeyguardState(boolean enable, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(100, !enable ? 0 : 1, userId);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyWakeUpState(boolean z, int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(101, z ? 1 : 0, i, Integer.valueOf(i2)).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void switchDefaultCocktail(int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(10, userId, 0);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void sendExtraData(int userId, Bundle extraData) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(12, userId, 0, extraData);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setDisableTickerView(int state, int userId) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(9, state, userId);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void changeVisibleEdgeService(boolean z, int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(102, z ? 1 : 0, i).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setPullToRefresh(int cocktailId, int viewId, PendingIntent pendingIntent, int userId) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(13, cocktailId, viewId, pendingIntent);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void noteResumeComponent(ComponentName resumeComponentName) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(103, resumeComponentName);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notePauseComponent(ComponentName pauseComponentName) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(104, pauseComponentName);
            msg.sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            Message msg = handler.obtainMessage(105, cocktail);
            msg.sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class UpdateHandler extends Handler {
        public UpdateHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CocktailHost.this.updateCocktail(msg.arg1, (Cocktail) msg.obj, msg.arg2);
                    return;
                case 2:
                    CocktailHost.this.partiallyUpdateCocktail(msg.arg1, (RemoteViews) msg.obj, msg.arg2);
                    return;
                case 3:
                    CocktailHost.this.removeCocktail(msg.arg1, msg.arg2);
                    return;
                case 4:
                    CocktailHost.this.showCocktail(msg.arg1, msg.arg2);
                    return;
                case 5:
                    CocktailHost.this.closeCocktail(msg.arg1, msg.arg2, ((Integer) msg.obj).intValue());
                    return;
                case 6:
                    CocktailHost.this.viewDataChanged(msg.arg1, msg.arg2, ((Integer) msg.obj).intValue());
                    return;
                case 7:
                    CocktailHost.this.updateToolLauncher(msg.arg1);
                    return;
                case 9:
                    CocktailHost.this.setDisableTickerView(msg.arg1, msg.arg2);
                    return;
                case 10:
                    CocktailHost.this.switchDefaultCocktail(msg.arg1);
                    return;
                case 12:
                    CocktailHost.this.sendExtraDataToCocktailBar(msg.arg1, (Bundle) msg.obj);
                    return;
                case 13:
                    CocktailHost.this.setPullToRefresh(msg.arg1, msg.arg2, (PendingIntent) msg.obj);
                    return;
                case 14:
                    CocktailHost.this.partiallyUpdateHelpView(msg.arg1, (RemoteViews) msg.obj, msg.arg2);
                    return;
                case 100:
                    CocktailHost.this.notifyKeyguardState(msg.arg1, msg.arg2);
                    return;
                case 101:
                    CocktailHost.this.notifyWakeUpState(msg.arg1, msg.arg2, ((Integer) msg.obj).intValue());
                    return;
                case 102:
                    CocktailHost.this.changeVisibleEdgeService(msg.arg1, msg.arg2);
                    return;
                case 103:
                    CocktailHost.this.noteResumeComponent((ComponentName) msg.obj);
                    return;
                case 104:
                    CocktailHost.this.notePauseComponent((ComponentName) msg.obj);
                    return;
                case 105:
                    CocktailHost.this.packageSuspendChanged((Cocktail) msg.obj);
                    return;
                default:
                    return;
            }
        }
    }

    public CocktailHost(Context context, ICallbackListener callbackListener) {
        this(context, callbackListener, context.getMainLooper());
    }

    public CocktailHost(Context context, int category, ICallbackListener callbackListener) {
        this(context, category, callbackListener, context.getMainLooper());
    }

    public CocktailHost(Context context, ICallbackListener callbackListener, Looper looper) {
        this.mListeningCategory = 0;
        this.mContextOpPackageName = context.getOpPackageName();
        this.mCallbackListener = callbackListener;
        UpdateHandler updateHandler = new UpdateHandler(looper);
        this.mHandler = updateHandler;
        this.mCallbacks = new Callbacks(updateHandler);
        bindService(0);
    }

    public CocktailHost(Context context, int category, ICallbackListener callbackListener, Looper looper) {
        this.mListeningCategory = 0;
        this.mContextOpPackageName = context.getOpPackageName();
        this.mCallbackListener = callbackListener;
        UpdateHandler updateHandler = new UpdateHandler(looper);
        this.mHandler = updateHandler;
        this.mCallbacks = new Callbacks(updateHandler);
        this.mListeningCategory = category;
        bindService(category);
    }

    private void bindService(int category) {
        synchronized (sServiceLock) {
            if (sService == null) {
                IBinder b = ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE);
                sService = ICocktailBarService.Stub.asInterface(b);
            }
            try {
                ICocktailBarService iCocktailBarService = sService;
                if (iCocktailBarService != null) {
                    iCocktailBarService.setCocktailHostCallbacks(this.mCallbacks, this.mContextOpPackageName, category);
                } else {
                    Slog.d(TAG, "bindService: can not get ICocktailBarService");
                }
            } catch (RemoteException e) {
            }
        }
    }

    public void startListening() {
        try {
            sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mListeningCategory);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void startListening(int category) {
        try {
            this.mListeningCategory = category;
            sService.startListening(this.mCallbacks, this.mContextOpPackageName, category);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void stopListening() {
        try {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mCallbackListener = null;
            sService.stopListening(this.mContextOpPackageName);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void updateCocktail(int cocktailId, Cocktail cocktail, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onUpdateCocktail(cocktailId, cocktail, userId);
        }
    }

    public void partiallyUpdateCocktail(int cocktailId, RemoteViews contentView, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onPartiallyUpdateCocktail(cocktailId, contentView, userId);
        }
    }

    public void partiallyUpdateHelpView(int cocktailId, RemoteViews helpView, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onPartiallyUpdateHelpView(cocktailId, helpView, userId);
        }
    }

    public void removeCocktail(int cocktailId, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onRemoveCocktail(cocktailId, userId);
        }
    }

    public void showCocktail(int cocktailId, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onShowCocktail(cocktailId, userId);
        }
    }

    public void closeCocktail(int cocktailId, int category, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onCloseCocktail(cocktailId, category, userId);
        }
    }

    public void viewDataChanged(int cocktailId, int viewId, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onViewDataChanged(cocktailId, viewId, userId);
        }
    }

    public void updateToolLauncher(int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onUpdateToolLauncher(userId);
        }
    }

    public void notifyKeyguardState(int enable, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onNotifyKeyguardState(enable == 1, userId);
        }
    }

    public void notifyWakeUpState(int bEnable, int keyCode, int reason) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onNotifyWakeUpModeState(bEnable == 1, keyCode, reason);
        }
    }

    public void switchDefaultCocktail(int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSwitchDefaultCocktail(userId);
        }
    }

    public void sendExtraDataToCocktailBar(int userId, Bundle extraData) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSendExtraDataToCocktailBar(extraData, userId);
        }
    }

    public void setDisableTickerView(int state, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSetDisableTickerView(state, userId);
        }
    }

    public void changeVisibleEdgeService(int visible, int userId) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onChangeVisibleEdgeService(visible == 1, userId);
        }
    }

    public void setPullToRefresh(int cocktailId, int viewId, PendingIntent pendingIntent) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSetPullToRefresh(cocktailId, viewId, pendingIntent);
        }
    }

    public void noteResumeComponent(ComponentName componentName) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onNoteResumeComponent(componentName);
            }
        } catch (AbstractMethodError e) {
            Slog.d(TAG, "noteResumeComponent: AbstractMethodError happens");
        }
    }

    public void notePauseComponent(ComponentName componentName) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onNotePauseComponent(componentName);
            }
        } catch (AbstractMethodError e) {
            Slog.d(TAG, "notePauseComponent: AbstractMethodError happens");
        }
    }

    public void packageSuspendChanged(Cocktail cocktail) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onPackageSuspendChanged(cocktail);
            }
        } catch (AbstractMethodError e) {
            Slog.d(TAG, "packageSuspended: AbstractMethodError happens");
        }
    }
}

package com.android.internal.statusbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audio.Enums;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.statusbar.IAppClipsService;
import java.util.concurrent.CompletableFuture;

/* loaded from: classes5.dex */
public class AppClipsServiceConnector {
    private static final String TAG = AppClipsServiceConnector.class.getSimpleName();
    private final Context mContext;
    private final Handler mHandler;

    public AppClipsServiceConnector(Context context) {
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
    }

    public boolean canLaunchCaptureContentActivityForNote(int taskId) {
        try {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            connectToServiceAndProcessRequest(taskId, future);
            return future.get().booleanValue();
        } catch (Exception e) {
            Log.d(TAG, "Exception from service\n" + e);
            return false;
        }
    }

    private void connectToServiceAndProcessRequest(final int taskId, final CompletableFuture<Boolean> future) {
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.internal.statusbar.AppClipsServiceConnector.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    future.complete(Boolean.valueOf(IAppClipsService.Stub.asInterface(service).canLaunchCaptureContentActivityForNote(taskId)));
                } catch (Exception e) {
                    Log.d(AppClipsServiceConnector.TAG, "Exception from service\n" + e);
                }
                future.complete(false);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                if (!future.isDone()) {
                    future.complete(false);
                }
            }
        };
        ComponentName serviceComponent = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotAppClipsServiceComponent));
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(serviceComponent);
        boolean bindService = this.mContext.bindServiceAsUser(serviceIntent, serviceConnection, Enums.AUDIO_FORMAT_AAC_MAIN, this.mHandler, this.mContext.getUser());
        if (!bindService) {
            future.complete(false);
        }
    }
}

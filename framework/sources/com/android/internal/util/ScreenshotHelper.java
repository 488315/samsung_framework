package com.android.internal.util;

import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.audio.Enums;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.util.ScreenshotRequest;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class ScreenshotHelper {
    public static final int SCREENSHOT_MSG_PROCESS_COMPLETE = 2;
    public static final int SCREENSHOT_MSG_URI = 1;
    private static final String TAG = "ScreenshotHelper";
    private final Context mContext;
    private final int SCREENSHOT_TIMEOUT_MS = 10000;
    private final Object mScreenshotLock = new Object();
    private IBinder mScreenshotService = null;
    private ServiceConnection mScreenshotConnection = null;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.internal.util.ScreenshotHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (ScreenshotHelper.this.mScreenshotLock) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    ScreenshotHelper.this.resetConnection();
                }
            }
        }
    };

    public ScreenshotHelper(Context context) {
        this.mContext = context;
    }

    public void takeScreenshot(int source, Handler handler, Consumer<Uri> completionConsumer) {
        ScreenshotRequest request = new ScreenshotRequest.Builder(1, source).build();
        takeScreenshot(request, handler, completionConsumer);
    }

    public void takeScreenshot(ScreenshotRequest request, Handler handler, Consumer<Uri> completionConsumer) {
        takeScreenshotInternal(request, handler, completionConsumer, JobInfo.MIN_BACKOFF_MILLIS);
    }

    public void takeScreenshotInternal(ScreenshotRequest request, final Handler handler, final Consumer<Uri> completionConsumer, long timeoutMs) {
        synchronized (this.mScreenshotLock) {
            try {
                try {
                    this.mContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"), 2);
                    final Runnable mScreenshotTimeout = new Runnable() { // from class: com.android.internal.util.ScreenshotHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScreenshotHelper.this.lambda$takeScreenshotInternal$0(completionConsumer);
                        }
                    };
                    final Message msg = Message.obtain(null, 0, request);
                    Handler h = new Handler(handler.getLooper()) { // from class: com.android.internal.util.ScreenshotHelper.2
                        @Override // android.os.Handler
                        public void handleMessage(Message msg2) {
                            switch (msg2.what) {
                                case 1:
                                    if (completionConsumer != null) {
                                        completionConsumer.accept((Uri) msg2.obj);
                                    }
                                    handler.removeCallbacks(mScreenshotTimeout);
                                    return;
                                case 2:
                                    synchronized (ScreenshotHelper.this.mScreenshotLock) {
                                        ScreenshotHelper.this.resetConnection();
                                    }
                                    return;
                                default:
                                    return;
                            }
                        }
                    };
                    msg.replyTo = new Messenger(h);
                    if (this.mScreenshotConnection != null && this.mScreenshotService != null) {
                        Messenger messenger = new Messenger(this.mScreenshotService);
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                            Log.e(TAG, "Couldn't take screenshot: " + e);
                            if (completionConsumer != null) {
                                completionConsumer.accept(null);
                            }
                        }
                        handler.postDelayed(mScreenshotTimeout, timeoutMs);
                    }
                    if (this.mScreenshotConnection != null) {
                        resetConnection();
                    }
                    ComponentName serviceComponent = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotServiceComponent));
                    Intent serviceIntent = new Intent();
                    serviceIntent.setComponent(serviceComponent);
                    ServiceConnection conn = new ServiceConnection() { // from class: com.android.internal.util.ScreenshotHelper.3
                        @Override // android.content.ServiceConnection
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            synchronized (ScreenshotHelper.this.mScreenshotLock) {
                                if (ScreenshotHelper.this.mScreenshotConnection != this) {
                                    return;
                                }
                                ScreenshotHelper.this.mScreenshotService = service;
                                Messenger messenger2 = new Messenger(ScreenshotHelper.this.mScreenshotService);
                                try {
                                    messenger2.send(msg);
                                } catch (RemoteException e2) {
                                    Log.e(ScreenshotHelper.TAG, "Couldn't take screenshot: " + e2);
                                    if (completionConsumer != null) {
                                        completionConsumer.accept(null);
                                    }
                                }
                            }
                        }

                        @Override // android.content.ServiceConnection
                        public void onServiceDisconnected(ComponentName name) {
                            synchronized (ScreenshotHelper.this.mScreenshotLock) {
                                if (ScreenshotHelper.this.mScreenshotConnection != null) {
                                    ScreenshotHelper.this.resetConnection();
                                    if (handler.hasCallbacks(mScreenshotTimeout)) {
                                        Log.e(ScreenshotHelper.TAG, "Screenshot service disconnected");
                                        handler.removeCallbacks(mScreenshotTimeout);
                                        ScreenshotHelper.this.notifyScreenshotError();
                                    }
                                }
                            }
                        }
                    };
                    if (this.mContext.bindServiceAsUser(serviceIntent, conn, Enums.AUDIO_FORMAT_AAC_MAIN, UserHandle.CURRENT)) {
                        this.mScreenshotConnection = conn;
                        handler.postDelayed(mScreenshotTimeout, timeoutMs);
                    } else {
                        this.mContext.unbindService(conn);
                    }
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotInternal$0(Consumer completionConsumer) {
        synchronized (this.mScreenshotLock) {
            if (this.mScreenshotConnection != null) {
                Log.e(TAG, "Timed out before getting screenshot capture response");
                resetConnection();
                notifyScreenshotError();
            }
        }
        if (completionConsumer != null) {
            completionConsumer.accept(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConnection() {
        if (this.mScreenshotConnection != null) {
            this.mContext.unbindService(this.mScreenshotConnection);
            this.mScreenshotConnection = null;
            this.mScreenshotService = null;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Attempted to remove broadcast receiver twice");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenshotError() {
        ComponentName errorComponent = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotErrorReceiverComponent));
        Intent errorIntent = new Intent(Intent.ACTION_USER_PRESENT);
        errorIntent.setComponent(errorComponent);
        errorIntent.addFlags(335544320);
        this.mContext.sendBroadcastAsUser(errorIntent, UserHandle.CURRENT);
    }
}

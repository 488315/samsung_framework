package com.samsung.android.photoremasterservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class ProgressUpdateClient {
    private static final long BIND_TIME_OUT_SECOND = 25;
    private static final String SERVICE_CLASS = "com.samsung.android.photoremasterservice.PhotoRemasterService";
    private static final String SERVICE_PACKAGE = "com.samsung.android.photoremasterservice";
    private static final String TAG = "ProgressUpdateClient";
    private Context mContext;
    private HandlerThread mHandlerThread;
    private Messenger mIncomingMessenger;
    private IDirector.ProgressUpdateListener mProgressUpdateListener;
    private Messenger mServiceMessenger;
    private boolean mIsBound = false;
    private CompletableFuture<Bundle> mServiceReturnValue = new CompletableFuture<>();
    private final List<ProgressObserver> mProgressObservers = new ArrayList();
    private final ServiceConnection mConnection = new ServiceConnection() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            ProgressUpdateClient.this.mServiceMessenger = new Messenger(service);
            ProgressUpdateClient.this.mServiceReturnValue.complete(null);
            LogUtil.i(ProgressUpdateClient.TAG, "Service is connected(attached).");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            LogUtil.i(ProgressUpdateClient.TAG, "Service is disconnected.");
            ProgressUpdateClient.this.mServiceMessenger = null;
        }
    };

    public void registerObserver(ProgressObserver progressObserver) {
        this.mProgressObservers.add(progressObserver);
    }

    public synchronized void init() {
        HandlerThread handlerThread = new HandlerThread("PhotoRemasterService Looper");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mIncomingMessenger = new Messenger(new Handler(this.mHandlerThread.getLooper()) { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (ProgressUpdateClient.this.mProgressUpdateListener == null) {
                    LogUtil.d(ProgressUpdateClient.TAG, "progressUpdate is received. But listener is unregistered. So received data is ignored.");
                    return;
                }
                Bundle bundle = msg.getData();
                switch (msg.what) {
                    case -1:
                        ProgressUpdateClient.this.mServiceReturnValue.complete(msg.getData());
                        LogUtil.e(ProgressUpdateClient.TAG, "Received exception : " + msg.what);
                        return;
                    case 0:
                        ProgressUpdateClient.this.mServiceReturnValue.complete(msg.getData());
                        LogUtil.d(ProgressUpdateClient.TAG, "Received MSG_RET");
                        return;
                    case 15:
                        String metadata = bundle.getString("String");
                        ProgressUpdateClient.this.mProgressUpdateListener.onUpdateMetadata(metadata);
                        LogUtil.d(ProgressUpdateClient.TAG, "Received OnUpdateMetaData");
                        return;
                    case 16:
                        double percent = bundle.getDouble(ServiceReturnKey.DOUBLE);
                        int currentImageIndex = bundle.getIntArray(ServiceReturnKey.INT_ARRAY)[0];
                        int totalImageCount = bundle.getIntArray(ServiceReturnKey.INT_ARRAY)[1];
                        ProgressUpdateClient.this.mProgressUpdateListener.onUpdateProgress(percent, currentImageIndex, totalImageCount);
                        ProgressUpdateClient.this.mProgressObservers.forEach(new Consumer() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((ProgressObserver) obj).update();
                            }
                        });
                        LogUtil.d(ProgressUpdateClient.TAG, "Received OnUpdateProgress: " + percent + ", " + currentImageIndex + ", " + totalImageCount);
                        return;
                    default:
                        LogUtil.e(ProgressUpdateClient.TAG, "Wrong message is received.: " + msg.what);
                        super.handleMessage(msg);
                        return;
                }
            }
        });
    }

    public synchronized void deinit() {
        this.mHandlerThread.quit();
    }

    public synchronized void setContext(Context context) {
        LogUtil.d(TAG, "Context is set.");
        this.mContext = context;
    }

    private synchronized void bind() {
        LogUtil.d(TAG, "Try binding...");
        if (this.mContext == null) {
            LogUtil.e(TAG, "Context is null.");
            return;
        }
        if (this.mIsBound) {
            LogUtil.i(TAG, "Already bound.");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(SERVICE_PACKAGE, SERVICE_CLASS);
        this.mContext.bindService(intent, 1, Executors.newSingleThreadExecutor(), this.mConnection);
        getServiceReturnValue();
        LogUtil.i(TAG, "Service binding is finished.");
        this.mIsBound = true;
    }

    private static boolean isExceptionContained(Bundle bundle) {
        return (bundle == null || bundle.getSerializable("exception", Exception.class) == null) ? false : true;
    }

    private Bundle getServiceReturnValue() {
        try {
            Bundle retValue = this.mServiceReturnValue.get(BIND_TIME_OUT_SECOND, TimeUnit.SECONDS);
            this.mServiceReturnValue = new CompletableFuture<>();
            if (isExceptionContained(retValue)) {
                LogUtil.e(TAG, "Exception is received from service.");
                Exception exception = (Exception) retValue.getSerializable("exception", Exception.class);
                if (exception == null) {
                    throw new RuntimeException("Unknown exception form service-server.");
                }
                RuntimeException rtException = new RuntimeException(exception + " in PhotoRemaster Service");
                rtException.setStackTrace(exception.getStackTrace());
                rtException.printStackTrace();
                throw rtException;
            }
            return retValue;
        } catch (InterruptedException | ExecutionException e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            return null;
        } catch (TimeoutException e2) {
            LogUtil.e(TAG, e2 + " is occurred at getServiceReturnValue()");
            e2.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            throw new RuntimeException(e2.toString());
        }
    }

    public synchronized void unbindService() {
        LogUtil.d(TAG, "Try unbinding...");
        Context context = this.mContext;
        if (context == null) {
            LogUtil.e(TAG, "Context is null.");
            return;
        }
        if (!this.mIsBound) {
            LogUtil.d(TAG, "Already unbound!!!");
            return;
        }
        context.unbindService(this.mConnection);
        this.mIsBound = false;
        this.mProgressUpdateListener = null;
        LogUtil.i(TAG, "Service is unbound.");
    }

    private void throwRuntimeException(String msg) {
        LogUtil.e(TAG, msg);
        throw new RuntimeException(msg);
    }

    public void setProgressUpdateListener(IDirector.ProgressUpdateListener listener) {
        this.mProgressUpdateListener = listener;
        if (listener == null) {
            return;
        }
        if (!this.mIsBound) {
            bind();
            if (!this.mIsBound) {
                throwRuntimeException("Service bind fail.");
            }
        }
        if (this.mServiceMessenger == null) {
            throwRuntimeException("Unexpected disconnection, ServiceMessenger is null!");
        }
        LogUtil.d(TAG, "ServiceCallRunnable started!");
        new Thread(new Runnable() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.1ServiceCallRunnable
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LogUtil.d(ProgressUpdateClient.TAG, "Send message to service...");
                    Message msg = Message.obtain((Handler) null, 14);
                    msg.setData(null);
                    msg.replyTo = ProgressUpdateClient.this.mIncomingMessenger;
                    ProgressUpdateClient.this.mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    LogUtil.e(ProgressUpdateClient.TAG, "Exception at sending message. - " + e);
                }
            }
        }).start();
        LogUtil.d(TAG, "Waiting the ack.");
        Bundle retBundle = getServiceReturnValue();
        if (retBundle == null) {
            LogUtil.e(TAG, "Service return bundle is null!");
            throw new RuntimeException("Service return bundle is null!");
        }
        LogUtil.d(TAG, "Service ack is received.");
    }
}

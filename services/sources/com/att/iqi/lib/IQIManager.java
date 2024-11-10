package com.att.iqi.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import com.att.iqi.IIQIBroker;
import com.att.iqi.IMetricQueryCallback;
import com.att.iqi.IMetricSourcingCallback;
import com.att.iqi.IProfileChangedCallback;
import com.att.iqi.IServiceStateChangeCallback;
import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.Metric;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class IQIManager {
    private static final String ACTION_SERVICE_FORCE_STOPPED = "com.att.iqi.action.SERVICE_FORCE_STOPPED";
    private static final long FORCE_STOP_WAIT_MS = 3500;
    private static final String IQIMANAGER_TAG = "IQIManager";
    private static final String PERMISSION_SERVICE_FORCE_STOP = "com.att.iqi.permission.RECEIVE_FORCE_STOP_NOTIFICATION";
    private static IQIManager sInstance;
    private static final Object sLock = new Object();
    private final HandlerThread mHandlerThread;
    private IIQIBroker mIQIService;
    private final Handler mMessageDispatcher;
    private final SparseArray mMetricQueryCallbackMap = new SparseArray();
    private final List mProfileChangeListenerList = new ArrayList();
    private final SparseArray mMetricSourcingListenerMap = new SparseArray();
    private final List mServiceStateChangeListenerList = new ArrayList();
    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private final IMetricQueryCallback mQueryCallback = new IMetricQueryCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.2
        @Override // com.att.iqi.IMetricQueryCallback
        public void onMetricQueried(Metric.ID id, byte[] bArr) {
            if (id == null) {
                return;
            }
            IQIManager.this.mMessageDispatcher.obtainMessage(1, id.asInt(), 0, bArr).sendToTarget();
        }
    };
    private final IMetricSourcingCallback mMetricSourcingCallback = new IMetricSourcingCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.3
        @Override // com.att.iqi.IMetricSourcingCallback
        public void onMetricSourced(Metric.ID id, byte[] bArr) {
            if (id == null) {
                return;
            }
            IQIManager.this.mMessageDispatcher.obtainMessage(2, id.asInt(), 0, bArr).sendToTarget();
        }
    };
    private final IProfileChangedCallback mProfileChangedCallback = new IProfileChangedCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.4
        @Override // com.att.iqi.IProfileChangedCallback
        public void onProfileChanged() {
            IQIManager.this.mMessageDispatcher.obtainMessage(3).sendToTarget();
        }
    };
    private final IServiceStateChangeCallback mServiceStateChangedCallback = new IServiceStateChangeCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.5
        @Override // com.att.iqi.IServiceStateChangeCallback
        public void onServiceChange(boolean z) {
            IQIManager.this.mMessageDispatcher.obtainMessage(4, z ? 1 : 0, 0).sendToTarget();
        }
    };

    /* loaded from: classes3.dex */
    public interface MetricQueryCallback {
        void onMetricQueried(Metric.ID id, ByteBuffer byteBuffer);
    }

    /* loaded from: classes3.dex */
    public interface MetricSourcingListener {
        void onMetricSourcing(Metric.ID id, ByteBuffer byteBuffer);
    }

    /* loaded from: classes3.dex */
    public interface ProfileChangeListener {
        void onProfileChanged();
    }

    /* loaded from: classes3.dex */
    public interface ServiceStateChangeListener {
        void onServiceChange(boolean z);
    }

    public static IQIManager getInstance() {
        IQIManager iQIManager;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new IQIManager();
            }
            iQIManager = sInstance;
        }
        return iQIManager;
    }

    private IQIManager() {
        getService();
        HandlerThread handlerThread = new HandlerThread("msg-handler-iqi");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        this.mMessageDispatcher = new Handler(looper == null ? Looper.getMainLooper() : looper, new MessageDispatcherCallback(this));
    }

    /* loaded from: classes3.dex */
    class MessageDispatcherCallback implements Handler.Callback {
        public static final int MSG_ON_METRIC_QUERIED = 1;
        public static final int MSG_ON_METRIC_SOURCED = 2;
        public static final int MSG_ON_PROFILE_CHANGED = 3;
        public static final int MSG_ON_SERVICE_CHANGED = 4;
        public static final int MSG_TIMED_OUT_WAITING_PACKAGE_FORCE_STOPPED = 5;
        final WeakReference reference;

        public MessageDispatcherCallback(IQIManager iQIManager) {
            this.reference = new WeakReference(iQIManager);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            IQIManager iQIManager = (IQIManager) this.reference.get();
            if (iQIManager == null) {
                return true;
            }
            int i = message.what;
            if (i == 1) {
                final int i2 = message.arg1;
                final MetricQueryCallback metricQueryCallback = (MetricQueryCallback) iQIManager.mMetricQueryCallbackMap.get(i2);
                if (metricQueryCallback != null) {
                    Object obj = message.obj;
                    final ByteBuffer wrap = ByteBuffer.wrap(obj != null ? (byte[]) obj : new byte[0]);
                    iQIManager.mExecutorService.execute(new Runnable() { // from class: com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            IQIManager.MessageDispatcherCallback.lambda$handleMessage$0(IQIManager.MetricQueryCallback.this, i2, wrap);
                        }
                    });
                }
            } else if (i == 2) {
                final int i3 = message.arg1;
                final MetricSourcingListener metricSourcingListener = (MetricSourcingListener) iQIManager.mMetricSourcingListenerMap.get(i3);
                if (metricSourcingListener != null) {
                    Object obj2 = message.obj;
                    final ByteBuffer wrap2 = ByteBuffer.wrap(obj2 != null ? (byte[]) obj2 : new byte[0]);
                    iQIManager.mExecutorService.execute(new Runnable() { // from class: com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            IQIManager.MessageDispatcherCallback.lambda$handleMessage$1(IQIManager.MetricSourcingListener.this, i3, wrap2);
                        }
                    });
                }
            } else if (i == 3) {
                synchronized (iQIManager.mProfileChangeListenerList) {
                    for (final ProfileChangeListener profileChangeListener : iQIManager.mProfileChangeListenerList) {
                        ExecutorService executorService = iQIManager.mExecutorService;
                        Objects.requireNonNull(profileChangeListener);
                        executorService.execute(new Runnable() { // from class: com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                IQIManager.ProfileChangeListener.this.onProfileChanged();
                            }
                        });
                    }
                }
            } else if (i == 4) {
                final boolean z = message.arg1 == 1;
                synchronized (iQIManager.mServiceStateChangeListenerList) {
                    for (final ServiceStateChangeListener serviceStateChangeListener : iQIManager.mServiceStateChangeListenerList) {
                        iQIManager.mExecutorService.execute(new Runnable() { // from class: com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                IQIManager.ServiceStateChangeListener.this.onServiceChange(z);
                            }
                        });
                    }
                }
            } else if (i == 5) {
                Log.d(IQIManager.IQIMANAGER_TAG, "Timed out waiting for package to be force stopped");
                Object obj3 = message.obj;
                if (obj3 instanceof Runnable) {
                    iQIManager.mExecutorService.execute((Runnable) obj3);
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$handleMessage$0(MetricQueryCallback metricQueryCallback, int i, ByteBuffer byteBuffer) {
            metricQueryCallback.onMetricQueried(new Metric.ID(i), byteBuffer);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$handleMessage$1(MetricSourcingListener metricSourcingListener, int i, ByteBuffer byteBuffer) {
            metricSourcingListener.onMetricSourcing(new Metric.ID(i), byteBuffer);
        }
    }

    public boolean shouldSubmitMetric(Metric.ID id) {
        if (id == null) {
            return false;
        }
        try {
            getService();
            return this.mIQIService.shouldSubmitMetric(id);
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in shouldSubmitMetric", e);
            return false;
        }
    }

    public void submitMetric(Metric metric) {
        if (metric == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.submitMetric(metric);
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in submitMetric", e);
        }
    }

    public void registerQueryCallback(Metric.ID id, MetricQueryCallback metricQueryCallback) {
        if (id == null || metricQueryCallback == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.registerMetricQueryCallback(id, this.mQueryCallback);
            synchronized (this.mMetricQueryCallbackMap) {
                this.mMetricQueryCallbackMap.append(id.asInt(), metricQueryCallback);
            }
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException("Callback already registered for metric ID " + id.asString());
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in registerQueryCallback", e);
        }
    }

    public void unregisterQueryCallback(Metric.ID id, MetricQueryCallback metricQueryCallback) {
        if (id == null || metricQueryCallback == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.unregisterMetricQueryCallback(id, this.mQueryCallback);
            synchronized (this.mMetricQueryCallbackMap) {
                this.mMetricQueryCallbackMap.remove(id.asInt());
            }
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in registerQueryCallback", e);
        }
    }

    public void registerProfileChangeListener(ProfileChangeListener profileChangeListener) {
        boolean isEmpty;
        if (profileChangeListener == null) {
            return;
        }
        synchronized (this.mProfileChangeListenerList) {
            isEmpty = this.mProfileChangeListenerList.isEmpty();
            this.mProfileChangeListenerList.add(profileChangeListener);
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.registerProfileChangedCallback(this.mProfileChangedCallback);
            } catch (Exception e) {
                Log.d(IQIMANAGER_TAG, "Remote exception in registerProfileChangeListener", e);
            }
        }
    }

    public void unregisterProfileChangeListener(ProfileChangeListener profileChangeListener) {
        boolean isEmpty;
        if (profileChangeListener == null) {
            return;
        }
        synchronized (this.mProfileChangeListenerList) {
            this.mProfileChangeListenerList.remove(profileChangeListener);
            isEmpty = this.mProfileChangeListenerList.isEmpty();
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.unregisterProfileChangedCallback(this.mProfileChangedCallback);
            } catch (Exception e) {
                Log.d(IQIMANAGER_TAG, "Remote exception in unregisterProfileChangeListener", e);
            }
        }
    }

    public void registerMetricSourcingListener(Metric.ID id, MetricSourcingListener metricSourcingListener) {
        if (id == null || metricSourcingListener == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.registerMetricSourcingCallback(id, this.mMetricSourcingCallback);
            synchronized (this.mMetricSourcingListenerMap) {
                this.mMetricSourcingListenerMap.append(id.asInt(), metricSourcingListener);
            }
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException("Callback already registered for metric ID " + id.asString());
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in registerMetricSourcingListener", e);
        }
    }

    public void unregisterMetricSourcingListener(Metric.ID id, MetricSourcingListener metricSourcingListener) {
        if (id == null || metricSourcingListener == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.unregisterMetricSourcingCallback(id, this.mMetricSourcingCallback);
            synchronized (this.mMetricSourcingListenerMap) {
                this.mMetricSourcingListenerMap.remove(id.asInt());
            }
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in unregisterMetricSourcingListener", e);
        }
    }

    public void registerServiceStateChangeListener(ServiceStateChangeListener serviceStateChangeListener) {
        boolean isEmpty;
        if (serviceStateChangeListener == null) {
            return;
        }
        synchronized (this.mServiceStateChangeListenerList) {
            isEmpty = this.mServiceStateChangeListenerList.isEmpty();
            this.mServiceStateChangeListenerList.add(serviceStateChangeListener);
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.registerServiceChangedCallback(this.mServiceStateChangedCallback);
            } catch (Exception e) {
                Log.d(IQIMANAGER_TAG, "Remote exception in registerServiceStateChangeListener", e);
            }
        }
    }

    public void unregisterServiceStateChangeListener(ServiceStateChangeListener serviceStateChangeListener) {
        boolean isEmpty;
        if (serviceStateChangeListener == null) {
            return;
        }
        synchronized (this.mServiceStateChangeListenerList) {
            this.mServiceStateChangeListenerList.remove(serviceStateChangeListener);
            isEmpty = this.mServiceStateChangeListenerList.isEmpty();
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.unregisterServiceChangedCallback(this.mServiceStateChangedCallback);
            } catch (Exception e) {
                Log.d(IQIMANAGER_TAG, "Remote exception in unregisterServiceStateChangeListener", e);
            }
        }
    }

    public void forceStopService(Context context, final Runnable runnable) {
        if (runnable != null) {
            final Message obtainMessage = this.mMessageDispatcher.obtainMessage(5, runnable);
            context.registerReceiver(new BroadcastReceiver() { // from class: com.att.iqi.lib.IQIManager.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    IQIManager.this.mMessageDispatcher.removeMessages(obtainMessage.what, runnable);
                    IQIManager.this.mExecutorService.execute(runnable);
                    context2.unregisterReceiver(this);
                }
            }, new IntentFilter(ACTION_SERVICE_FORCE_STOPPED), PERMISSION_SERVICE_FORCE_STOP, this.mMessageDispatcher, 2);
            this.mMessageDispatcher.sendMessageDelayed(obtainMessage, FORCE_STOP_WAIT_MS);
        }
        try {
            getService();
            this.mIQIService.forceStopService();
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in forceStopService", e);
        }
    }

    public void disableService() {
        try {
            getService();
            this.mIQIService.disableService();
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in disableService", e);
        }
    }

    public boolean setUnlockCode(long j) {
        try {
            getService();
            return this.mIQIService.setUnlockCode(j);
        } catch (Exception e) {
            Log.d(IQIMANAGER_TAG, "Remote exception in setUnlockCode", e);
            return false;
        }
    }

    private void getService() {
        if (this.mIQIService != null) {
            return;
        }
        try {
            try {
                try {
                    IBinder iBinder = (IBinder) Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class).invoke(null, "iqi");
                    if (iBinder != null) {
                        IIQIBroker asInterface = IIQIBroker.Stub.asInterface(iBinder);
                        this.mIQIService = asInterface;
                        if (asInterface != null) {
                            Log.d(IQIMANAGER_TAG, "Service reached!");
                        } else {
                            Log.d(IQIMANAGER_TAG, "getService returned null :(");
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.d(IQIMANAGER_TAG, "Access exception!", e);
                } catch (InvocationTargetException e2) {
                    Log.d(IQIMANAGER_TAG, "Invocation exception!", e2);
                }
            } catch (NoSuchMethodException e3) {
                Log.d(IQIMANAGER_TAG, "Can't find getService method!", e3);
            }
        } catch (ClassNotFoundException e4) {
            Log.d(IQIMANAGER_TAG, "ServiceManager not found!", e4);
        }
    }
}

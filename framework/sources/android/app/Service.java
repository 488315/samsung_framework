package android.app;

import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class Service extends ContextWrapper implements ComponentCallbacks2, ContentCaptureManager.ContentCaptureClient {
    public static final int START_CONTINUATION_MASK = 15;
    public static final int START_FLAG_REDELIVERY = 1;
    public static final int START_FLAG_RETRY = 2;
    public static final int START_NOT_STICKY = 2;
    public static final int START_REDELIVER_INTENT = 3;
    public static final int START_STICKY = 1;
    public static final int START_STICKY_COMPATIBILITY = 0;
    public static final int START_TASK_REMOVED_COMPLETE = 1000;
    public static final int STOP_FOREGROUND_DETACH = 2;

    @Deprecated
    public static final int STOP_FOREGROUND_LEGACY = 0;
    public static final int STOP_FOREGROUND_REMOVE = 1;
    private static final String TAG = "Service";
    private static final String TRACE_TRACK_NAME_FOREGROUND_SERVICE = "FGS";
    private static final ArrayMap<String, StackTrace> sStartForegroundServiceStackTraces = new ArrayMap<>();
    private IActivityManager mActivityManager;
    private Application mApplication;
    private String mClassName;
    private String mForegroundServiceTraceTitle;
    private final Object mForegroundServiceTraceTitleLock;
    private boolean mStartCompatibility;
    private ActivityThread mThread;
    private IBinder mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartArgFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StopForegroundSelector {
    }

    public abstract IBinder onBind(Intent intent);

    public Service() {
        super(null);
        this.mThread = null;
        this.mClassName = null;
        this.mToken = null;
        this.mApplication = null;
        this.mActivityManager = null;
        this.mStartCompatibility = false;
        this.mForegroundServiceTraceTitle = null;
        this.mForegroundServiceTraceTitleLock = new Object();
    }

    public final Application getApplication() {
        return this.mApplication;
    }

    public void onCreate() {
    }

    @Deprecated
    public void onStart(Intent intent, int startId) {
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        onStart(intent, i2);
        return !this.mStartCompatibility ? 1 : 0;
    }

    public void onDestroy() {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void onRebind(Intent intent) {
    }

    public void onTaskRemoved(Intent rootIntent) {
    }

    public final void stopSelf() {
        stopSelf(-1);
    }

    public final void stopSelf(int startId) {
        if (this.mActivityManager == null) {
            return;
        }
        try {
            this.mActivityManager.stopServiceToken(new ComponentName(this, this.mClassName), this.mToken, startId);
        } catch (RemoteException e) {
        }
    }

    public final boolean stopSelfResult(int startId) {
        if (this.mActivityManager == null) {
            return false;
        }
        try {
            return this.mActivityManager.stopServiceToken(new ComponentName(this, this.mClassName), this.mToken, startId);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Deprecated
    public final void setForeground(boolean isForeground) {
        Log.w(TAG, "setForeground: ignoring old API call on " + getClass().getName());
    }

    public final void startForeground(int id, Notification notification) {
        try {
            ComponentName comp = new ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(comp, this.mToken, id, notification, 0, -1);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(comp, -1);
        } catch (RemoteException e) {
        }
    }

    public final void startForeground(int id, Notification notification, int foregroundServiceType) {
        try {
            ComponentName comp = new ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(comp, this.mToken, id, notification, 0, foregroundServiceType);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(comp, foregroundServiceType);
        } catch (RemoteException e) {
        }
    }

    @Deprecated
    public final void stopForeground(boolean z) {
        stopForeground(z ? 1 : 0);
    }

    public final void stopForeground(int notificationBehavior) {
        try {
            this.mActivityManager.setServiceForeground(new ComponentName(this, this.mClassName), this.mToken, 0, null, notificationBehavior, 0);
            logForegroundServiceStopIfNecessary();
        } catch (RemoteException e) {
        }
    }

    public final int getForegroundServiceType() {
        try {
            int ret = this.mActivityManager.getForegroundServiceType(new ComponentName(this, this.mClassName), this.mToken);
            return ret;
        } catch (RemoteException e) {
            return 0;
        }
    }

    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.println("nothing to dump");
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        if (newBase != null) {
            newBase.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

    public final void attach(Context context, ActivityThread thread, String className, IBinder token, Application application, Object activityManager) {
        attachBaseContext(context);
        this.mThread = thread;
        this.mClassName = className;
        this.mToken = token;
        this.mApplication = application;
        this.mActivityManager = (IActivityManager) activityManager;
        this.mStartCompatibility = getApplicationInfo().targetSdkVersion < 5;
        setContentCaptureOptions(application.getContentCaptureOptions());
    }

    public Context createServiceBaseContext(ActivityThread mainThread, LoadedApk packageInfo) {
        return ContextImpl.createAppContext(mainThread, packageInfo);
    }

    public final void detachAndCleanUp() {
        this.mToken = null;
        logForegroundServiceStopIfNecessary();
    }

    final String getClassName() {
        return this.mClassName;
    }

    @Override // android.content.Context
    public final ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final ComponentName contentCaptureClientGetComponentName() {
        return new ComponentName(this, this.mClassName);
    }

    private void logForegroundServiceStart(ComponentName comp, int foregroundServiceType) {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            if (this.mForegroundServiceTraceTitle == null) {
                this.mForegroundServiceTraceTitle = TextUtils.formatSimple("comp=%s type=%s", comp.toShortString(), Integer.toHexString(foregroundServiceType));
                Trace.asyncTraceForTrackBegin(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, this.mForegroundServiceTraceTitle, System.identityHashCode(this));
            } else {
                Trace.instantForTrack(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, this.mForegroundServiceTraceTitle);
            }
        }
    }

    private void logForegroundServiceStopIfNecessary() {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            if (this.mForegroundServiceTraceTitle != null) {
                Trace.asyncTraceForTrackEnd(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, System.identityHashCode(this));
                this.mForegroundServiceTraceTitle = null;
            }
        }
    }

    public static void setStartForegroundServiceStackTrace(String className, StackTrace stacktrace) {
        synchronized (sStartForegroundServiceStackTraces) {
            sStartForegroundServiceStackTraces.put(className, stacktrace);
        }
    }

    private void clearStartForegroundServiceStackTrace() {
        synchronized (sStartForegroundServiceStackTraces) {
            sStartForegroundServiceStackTraces.remove(getClassName());
        }
    }

    public static StackTrace getStartForegroundServiceStackTrace(String className) {
        StackTrace stackTrace;
        synchronized (sStartForegroundServiceStackTraces) {
            stackTrace = sStartForegroundServiceStackTraces.get(className);
        }
        return stackTrace;
    }

    public final void callOnTimeout(int startId) {
        if (this.mToken == null) {
            Log.w(TAG, "Service already destroyed, skipping onTimeout()");
            return;
        }
        try {
            if (!this.mActivityManager.shouldServiceTimeOut(new ComponentName(this, this.mClassName), this.mToken)) {
                Log.w(TAG, "Service no longer relevant, skipping onTimeout()");
                return;
            }
        } catch (RemoteException e) {
        }
        onTimeout(startId);
        if (Flags.introduceNewServiceOntimeoutCallback()) {
            onTimeout(startId, 2048);
        }
    }

    public void onTimeout(int startId) {
    }

    public final void callOnTimeLimitExceeded(int startId, int fgsType) {
        if (this.mToken == null) {
            Log.w(TAG, "Service already destroyed, skipping onTimeLimitExceeded()");
            return;
        }
        try {
            if (!this.mActivityManager.hasServiceTimeLimitExceeded(new ComponentName(this, this.mClassName), this.mToken)) {
                Log.w(TAG, "Service no longer relevant, skipping onTimeLimitExceeded()");
                return;
            }
        } catch (RemoteException e) {
        }
        if (Flags.introduceNewServiceOntimeoutCallback()) {
            onTimeout(startId, fgsType);
        }
    }

    public void onTimeout(int startId, int fgsType) {
    }
}

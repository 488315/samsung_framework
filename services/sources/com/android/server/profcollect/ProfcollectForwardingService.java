package com.android.server.profcollect;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.profcollect.IProfCollectd;
import com.android.server.profcollect.IProviderStatusCallback;
import com.android.server.profcollect.ProfcollectForwardingService;
import com.android.server.wm.ActivityMetricsLaunchObserver;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class ProfcollectForwardingService extends SystemService {
    public static ProfcollectForwardingService sSelfService;
    public final AppLaunchObserver mAppLaunchObserver;
    public final BroadcastReceiver mBroadcastReceiver;
    public final Handler mHandler;
    public IProfCollectd mIProfcollect;
    public IProviderStatusCallback mProviderStatusCallback;
    public static final boolean DEBUG = Log.isLoggable("ProfcollectForwardingService", 3);
    public static final long BG_PROCESS_PERIOD = TimeUnit.HOURS.toMillis(4);

    public ProfcollectForwardingService(Context context) {
        super(context);
        this.mHandler = new ProfcollectdHandler(IoThread.getHandler().getLooper());
        this.mProviderStatusCallback = new IProviderStatusCallback.Stub() { // from class: com.android.server.profcollect.ProfcollectForwardingService.1
            @Override // com.android.server.profcollect.IProviderStatusCallback
            public void onProviderReady() {
                ProfcollectForwardingService.this.mHandler.sendEmptyMessage(1);
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.profcollect.ProfcollectForwardingService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.android.server.profcollect.UPLOAD_PROFILES".equals(intent.getAction())) {
                    Log.d("ProfcollectForwardingService", "Received broadcast to pack and upload reports");
                    ProfcollectForwardingService.this.packAndUploadReport();
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        this.mAppLaunchObserver = new AppLaunchObserver();
        if (sSelfService != null) {
            throw new AssertionError("only one service instance allowed");
        }
        sSelfService = this;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.profcollect.UPLOAD_PROFILES");
        context.registerReceiver(broadcastReceiver, intentFilter, 4);
    }

    public static boolean enabled() {
        return DeviceConfig.getBoolean("profcollect_native_boot", "enabled", false) || SystemProperties.getBoolean("persist.profcollectd.enabled_override", false);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (DEBUG) {
            Log.d("ProfcollectForwardingService", "Profcollect forwarding service start");
        }
        connectNativeService();
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i != 1000 || this.mIProfcollect == null) {
            return;
        }
        BackgroundThread.get().getThreadHandler().post(new Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProfcollectForwardingService.this.lambda$onBootPhase$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0() {
        if (serviceHasSupportedTraceProvider()) {
            registerProviderStatusCallback();
        }
    }

    public final void registerProviderStatusCallback() {
        IProfCollectd iProfCollectd = this.mIProfcollect;
        if (iProfCollectd == null) {
            return;
        }
        try {
            iProfCollectd.registerProviderStatusCallback(this.mProviderStatusCallback);
        } catch (RemoteException e) {
            Log.e("ProfcollectForwardingService", "Failed to register provider status callback: " + e.getMessage());
        }
    }

    public final boolean serviceHasSupportedTraceProvider() {
        if (this.mIProfcollect == null) {
            return false;
        }
        try {
            return !r3.get_supported_provider().isEmpty();
        } catch (RemoteException e) {
            Log.e("ProfcollectForwardingService", "Failed to get supported provider: " + e.getMessage());
            return false;
        }
    }

    public final boolean tryConnectNativeService() {
        if (connectNativeService()) {
            return true;
        }
        this.mHandler.sendEmptyMessageDelayed(0, 5000L);
        return false;
    }

    public final boolean connectNativeService() {
        try {
            IProfCollectd asInterface = IProfCollectd.Stub.asInterface(ServiceManager.getServiceOrThrow("profcollectd"));
            asInterface.asBinder().linkToDeath(new ProfcollectdDeathRecipient(), 0);
            this.mIProfcollect = asInterface;
            return true;
        } catch (ServiceManager.ServiceNotFoundException | RemoteException unused) {
            Log.w("ProfcollectForwardingService", "Failed to connect profcollectd binder service.");
            return false;
        }
    }

    /* loaded from: classes3.dex */
    public class ProfcollectdHandler extends Handler {
        public ProfcollectdHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                ProfcollectForwardingService.this.connectNativeService();
                return;
            }
            if (i == 1) {
                ProfcollectForwardingService.this.registerObservers();
                ProfcollectBGJobService.schedule(ProfcollectForwardingService.this.getContext());
            } else {
                throw new AssertionError("Unknown message: " + message);
            }
        }
    }

    /* loaded from: classes3.dex */
    public class ProfcollectdDeathRecipient implements IBinder.DeathRecipient {
        public ProfcollectdDeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.w("ProfcollectForwardingService", "profcollectd has died");
            ProfcollectForwardingService.this.mIProfcollect = null;
            ProfcollectForwardingService.this.tryConnectNativeService();
        }
    }

    /* loaded from: classes3.dex */
    public class ProfcollectBGJobService extends JobService {
        public static final ComponentName JOB_SERVICE_NAME = new ComponentName("android", ProfcollectBGJobService.class.getName());

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }

        public static void schedule(Context context) {
            ((JobScheduler) context.getSystemService(JobScheduler.class)).schedule(new JobInfo.Builder(260817, JOB_SERVICE_NAME).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(ProfcollectForwardingService.BG_PROCESS_PERIOD).setPriority(100).build());
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            if (ProfcollectForwardingService.DEBUG) {
                Log.d("ProfcollectForwardingService", "Starting background process job");
            }
            BackgroundThread.get().getThreadHandler().post(new Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$ProfcollectBGJobService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ProfcollectForwardingService.ProfcollectBGJobService.lambda$onStartJob$0();
                }
            });
            return true;
        }

        public static /* synthetic */ void lambda$onStartJob$0() {
            try {
                if (ProfcollectForwardingService.sSelfService.mIProfcollect == null) {
                    return;
                }
                ProfcollectForwardingService.sSelfService.mIProfcollect.process();
            } catch (RemoteException e) {
                Log.e("ProfcollectForwardingService", "Failed to process profiles in background: " + e.getMessage());
            }
        }
    }

    public final void registerObservers() {
        BackgroundThread.get().getThreadHandler().post(new Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ProfcollectForwardingService.this.lambda$registerObservers$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerObservers$1() {
        registerAppLaunchObserver();
        registerOTAObserver();
    }

    public final void registerAppLaunchObserver() {
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getLaunchObserverRegistry().registerLaunchObserver(this.mAppLaunchObserver);
    }

    public final void traceOnAppStart(String str) {
        if (this.mIProfcollect == null) {
            return;
        }
        if (ThreadLocalRandom.current().nextInt(100) < DeviceConfig.getInt("profcollect_native_boot", "applaunch_trace_freq", 2)) {
            if (DEBUG) {
                Log.d("ProfcollectForwardingService", "Tracing on app launch event: " + str);
            }
            BackgroundThread.get().getThreadHandler().post(new Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ProfcollectForwardingService.this.lambda$traceOnAppStart$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$traceOnAppStart$2() {
        try {
            this.mIProfcollect.trace_once("applaunch");
        } catch (RemoteException e) {
            Log.e("ProfcollectForwardingService", "Failed to initiate trace: " + e.getMessage());
        }
    }

    /* loaded from: classes3.dex */
    public class AppLaunchObserver extends ActivityMetricsLaunchObserver {
        public AppLaunchObserver() {
        }

        @Override // com.android.server.wm.ActivityMetricsLaunchObserver
        public void onIntentStarted(Intent intent, long j) {
            ProfcollectForwardingService.this.traceOnAppStart(intent.getPackage());
        }
    }

    public final void registerOTAObserver() {
        new UpdateEngine().bind(new UpdateEngineCallback() { // from class: com.android.server.profcollect.ProfcollectForwardingService.3
            public void onPayloadApplicationComplete(int i) {
            }

            public void onStatusUpdate(int i, float f) {
                if (ProfcollectForwardingService.DEBUG) {
                    Log.d("ProfcollectForwardingService", "Received OTA status update, status: " + i + ", percent: " + f);
                }
                if (i == 6) {
                    ProfcollectForwardingService.this.packAndUploadReport();
                }
            }
        });
    }

    public final void packAndUploadReport() {
        if (this.mIProfcollect == null) {
            return;
        }
        final Context context = getContext();
        BackgroundThread.get().getThreadHandler().post(new Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ProfcollectForwardingService.this.lambda$packAndUploadReport$3(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$packAndUploadReport$3(Context context) {
        int i;
        try {
            try {
                i = Settings.Global.getInt(context.getContentResolver(), "multi_cb");
            } catch (Settings.SettingNotFoundException e) {
                Log.i("ProfcollectForwardingService", "Usage setting not found: " + e.getMessage());
                i = -1;
            }
            String str = this.mIProfcollect.report(i) + ".zip";
            if (!context.getResources().getBoolean(17891801)) {
                Log.i("ProfcollectForwardingService", "Upload is not enabled.");
            } else {
                context.sendBroadcast(new Intent().setPackage("com.android.shell").setAction("com.android.shell.action.PROFCOLLECT_UPLOAD").putExtra("filename", str));
            }
        } catch (RemoteException e2) {
            Log.e("ProfcollectForwardingService", "Failed to upload report: " + e2.getMessage());
        }
    }
}

package com.sec.android.iaft;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.nano.MetricsProto;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import com.samsung.android.media.AudioParameter;
import com.sec.android.iaft.IIAFTManagerService;
import com.sec.android.iaft.callback.IIAFTCallback;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes6.dex */
public class IAFTManagerServiceImpl extends IIAFTManagerService.Stub {
    private static final int MSG_START_ATRACE = 2;
    private static final int MSG_START_ATRACE_ANALYZE = 3;
    private static final int MSG_START_PERFETTO = 1;
    private static final int MSG_STOP_TRACE = 4;
    private static final String TAG = "IAFTManager";
    private Context mContext;
    private ServiceHandler mHandler;
    private Looper mLooper;
    private boolean mSystemReady = false;
    private static int mForegroundPid = 0;
    private static int mPolicy = -1;
    private static String mPackageName = "";
    private static IIAFTCallback mIAFTCallback = null;
    private static CountDownTimer mTraceTimer = null;
    private static int mTraceMaxTime = MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_ACCESS_FINE_LOCATION;

    public IAFTManagerServiceImpl(Context context) {
        this.mContext = context;
        init();
    }

    /* loaded from: classes6.dex */
    public final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(IAFTManagerServiceImpl.TAG, "Start perfetto in Handler thread");
                    return;
                case 2:
                    Log.d(IAFTManagerServiceImpl.TAG, "Start atrace in Handler thread");
                    Intent intent = new Intent();
                    intent.setAction("com.android.internal.intent.action.START_TRACE");
                    intent.setPackage("com.android.traceur");
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent);
                    IAFTManagerServiceImpl.mTraceTimer = new CountDownTimer(IAFTManagerServiceImpl.mTraceMaxTime * 1000, IAFTManagerServiceImpl.mTraceMaxTime * 1000) { // from class: com.sec.android.iaft.IAFTManagerServiceImpl.ServiceHandler.1
                        AnonymousClass1(long millisInFuture, long countDownInterval) {
                            super(millisInFuture, countDownInterval);
                        }

                        @Override // android.os.CountDownTimer
                        public void onTick(long duration) {
                        }

                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            Log.d(IAFTManagerServiceImpl.TAG, "traceTimer onfinish");
                            if (IAFTManagerServiceImpl.this.mSystemReady) {
                                IAFTManagerServiceImpl.this.mHandler.obtainMessage(4).sendToTarget();
                            }
                        }
                    }.start();
                    return;
                case 3:
                    Log.d(IAFTManagerServiceImpl.TAG, "Start atrace and analyze in Handler thread");
                    Intent intent2 = new Intent();
                    intent2.setAction("com.android.internal.intent.action.START_TRACE_ANALYZE");
                    intent2.setPackage("com.android.traceur");
                    intent2.putExtra(AudioParameter.SUBKEY_HIDDEN_SOUND_PID, IAFTManagerServiceImpl.mForegroundPid);
                    intent2.putExtra("package_name", IAFTManagerServiceImpl.mPackageName);
                    intent2.putExtra(RuntimeManifestUtils.TAG_POLICY, IAFTManagerServiceImpl.mPolicy);
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent2);
                    return;
                case 4:
                    Log.d(IAFTManagerServiceImpl.TAG, "Stop trace in Handler thread");
                    Intent intent3 = new Intent();
                    intent3.setAction("com.android.internal.intent.action.STOP_TRACE");
                    intent3.setPackage("com.android.traceur");
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent3);
                    return;
                default:
                    return;
            }
        }

        /* renamed from: com.sec.android.iaft.IAFTManagerServiceImpl$ServiceHandler$1 */
        /* loaded from: classes6.dex */
        class AnonymousClass1 extends CountDownTimer {
            AnonymousClass1(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override // android.os.CountDownTimer
            public void onTick(long duration) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                Log.d(IAFTManagerServiceImpl.TAG, "traceTimer onfinish");
                if (IAFTManagerServiceImpl.this.mSystemReady) {
                    IAFTManagerServiceImpl.this.mHandler.obtainMessage(4).sendToTarget();
                }
            }
        }
    }

    void init() {
        HandlerThread thread = new HandlerThread("MessageIAFTThread", 10);
        thread.start();
        this.mLooper = thread.getLooper();
        this.mHandler = new ServiceHandler(this.mLooper);
        if (!this.mSystemReady) {
            this.mSystemReady = true;
        }
    }

    public static void sendResult(int tid, int code, int freq) {
        Log.d(TAG, "sendResult back.");
        IIAFTCallback iIAFTCallback = mIAFTCallback;
        if (iIAFTCallback != null) {
            try {
                iIAFTCallback.traceResult(mPackageName, tid, code, freq, mPolicy);
            } catch (RemoteException e) {
                Log.d(TAG, "mIAFTCallback.traceResult exception!");
            }
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void registerCallback(IIAFTCallback callback) throws RemoteException {
        Log.d(TAG, "Register callback.");
        mIAFTCallback = callback;
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtraceAndAnalyze(int pid, String packageName, int policy) throws RemoteException {
        mForegroundPid = pid;
        mPackageName = packageName;
        mPolicy = policy;
        Log.d(TAG, "Send msg: MSG_START_ATRACE_ANALYZE. pid is " + pid);
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(3).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtrace() throws RemoteException {
        Log.d(TAG, "Send msg: MSG_START_ATRACE.");
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void stopTrace() throws RemoteException {
        Log.d(TAG, "Remote call stopTrace.");
        CountDownTimer countDownTimer = mTraceTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(4).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public boolean traceLogSupported() {
        return false;
    }
}

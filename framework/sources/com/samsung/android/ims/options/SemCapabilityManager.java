package com.samsung.android.ims.options;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.ims.extensions.SemContextExt;
import com.samsung.android.ims.options.SemImsCapabilityService;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
public class SemCapabilityManager {
    private static final String INTENT_ACTION_IMSSERVICE_RESTART = "com.sec.ims.imsmanager.RESTART";
    private Context mContext;
    private ConnectionListener mListener;
    private int mPhoneId;
    private final String LOG_TAG_BASE = "semCapabilityManager";
    private String LOG_TAG = "semCapabilityManager";
    private SemImsCapabilityService mImsCapabilityService = null;
    private Set<SemCapabilityListener> mQueuedCapabilityListener = new HashSet();
    private BroadcastReceiver mRestartReceiver = null;
    private ServiceConnection mConnection = null;

    /* loaded from: classes5.dex */
    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    public SemCapabilityManager(Context context, ConnectionListener listener, int phoneId) {
        this.mPhoneId = 0;
        this.mListener = null;
        this.mContext = context;
        this.mListener = listener;
        this.mPhoneId = phoneId;
        init();
    }

    private void init() {
        this.LOG_TAG = "semCapabilityManager[" + this.mPhoneId + "] this: " + this;
        this.mRestartReceiver = new BroadcastReceiver() { // from class: com.samsung.android.ims.options.SemCapabilityManager.1
            AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "onReceive " + intent.getAction());
                if (TextUtils.equals(intent.getAction(), SemCapabilityManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                    Log.i(SemCapabilityManager.this.LOG_TAG, "IMS service restarted.");
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(INTENT_ACTION_IMSSERVICE_RESTART);
        this.mContext.registerReceiver(this.mRestartReceiver, filter);
        connect();
    }

    /* renamed from: com.samsung.android.ims.options.SemCapabilityManager$1 */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(SemCapabilityManager.this.LOG_TAG, "onReceive " + intent.getAction());
            if (TextUtils.equals(intent.getAction(), SemCapabilityManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "IMS service restarted.");
            }
        }
    }

    public void connect() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(this.LOG_TAG, "Not recommended in main thread.");
        }
        if (this.mImsCapabilityService != null) {
            Log.i(this.LOG_TAG, "Already connected.");
            return;
        }
        Log.i(this.LOG_TAG, "Connecting to SemCapabilityDiscoveryService...");
        this.mConnection = new ServiceConnection() { // from class: com.samsung.android.ims.options.SemCapabilityManager.2
            AnonymousClass2() {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "Connected to SemCapabilityDiscoveryService.");
                SemCapabilityManager.this.mImsCapabilityService = SemImsCapabilityService.Stub.asInterface(service);
                if (SemCapabilityManager.this.mListener != null) {
                    SemCapabilityManager.this.mListener.onConnected();
                }
                if (!SemCapabilityManager.this.mQueuedCapabilityListener.isEmpty()) {
                    try {
                        for (SemCapabilityListener listener : SemCapabilityManager.this.mQueuedCapabilityListener) {
                            SemCapabilityManager.this.registerListener(listener);
                        }
                        SemCapabilityManager.this.mQueuedCapabilityListener.clear();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e(SemCapabilityManager.this.LOG_TAG, "registerListener failed. RemoteException: " + e);
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "Disconnected to SemCapabilityDiscoveryService.");
                if (SemCapabilityManager.this.mListener != null) {
                    SemCapabilityManager.this.mListener.onDisconnected();
                }
                SemCapabilityManager.this.mImsCapabilityService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClassName("com.sec.imsservice", "com.sec.internal.ims.imsservice.SemCapabilityService");
        SemContextExt.bindServiceAsUser(this.mContext, intent, this.mConnection, 1, SemContextExt.CURRENT_OR_SELF);
    }

    /* renamed from: com.samsung.android.ims.options.SemCapabilityManager$2 */
    /* loaded from: classes5.dex */
    public class AnonymousClass2 implements ServiceConnection {
        AnonymousClass2() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(SemCapabilityManager.this.LOG_TAG, "Connected to SemCapabilityDiscoveryService.");
            SemCapabilityManager.this.mImsCapabilityService = SemImsCapabilityService.Stub.asInterface(service);
            if (SemCapabilityManager.this.mListener != null) {
                SemCapabilityManager.this.mListener.onConnected();
            }
            if (!SemCapabilityManager.this.mQueuedCapabilityListener.isEmpty()) {
                try {
                    for (SemCapabilityListener listener : SemCapabilityManager.this.mQueuedCapabilityListener) {
                        SemCapabilityManager.this.registerListener(listener);
                    }
                    SemCapabilityManager.this.mQueuedCapabilityListener.clear();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e(SemCapabilityManager.this.LOG_TAG, "registerListener failed. RemoteException: " + e);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Log.i(SemCapabilityManager.this.LOG_TAG, "Disconnected to SemCapabilityDiscoveryService.");
            if (SemCapabilityManager.this.mListener != null) {
                SemCapabilityManager.this.mListener.onDisconnected();
            }
            SemCapabilityManager.this.mImsCapabilityService = null;
        }
    }

    public void disconnect() {
        try {
            BroadcastReceiver broadcastReceiver = this.mRestartReceiver;
            if (broadcastReceiver != null) {
                this.mContext.unregisterReceiver(broadcastReceiver);
                this.mRestartReceiver = null;
            }
            ServiceConnection serviceConnection = this.mConnection;
            if (serviceConnection != null) {
                this.mContext.unbindService(serviceConnection);
            }
            ConnectionListener connectionListener = this.mListener;
            if (connectionListener != null) {
                connectionListener.onDisconnected();
            }
            this.mImsCapabilityService = null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(this.LOG_TAG, "disconnect: IllegalArgumentException: " + e);
        }
    }

    public boolean isConnected() {
        return this.mImsCapabilityService != null;
    }

    public SemCapabilities getOwnCapabilities() throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getOwnCapabilities(this.mPhoneId);
        }
        return null;
    }

    public SemCapabilities getCapabilities(Uri uri, int refreshType) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService;
        if (uri == null || (semImsCapabilityService = this.mImsCapabilityService) == null) {
            return null;
        }
        return semImsCapabilityService.getCapabilities(uri.toString(), refreshType, this.mPhoneId);
    }

    public SemCapabilities getCapabilitiesByNumber(String number, int refreshType, boolean delay) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getCapabilitiesByNumber(number, refreshType, delay, this.mPhoneId);
        }
        return null;
    }

    public SemCapabilities[] getCapabilitiesByContactId(String contactId, int refreshType) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getCapabilitiesByContactId(contactId, refreshType, this.mPhoneId);
        }
        return null;
    }

    public void registerListener(SemCapabilityListener listener) throws RemoteException {
        Log.i(this.LOG_TAG, "registerListener: listener = " + listener + ", Caller = " + Binder.getCallingPid());
        if (listener == null || !TextUtils.isEmpty(listener.getToken())) {
            Log.i(this.LOG_TAG, "registerListener: token is notEmpty ");
            return;
        }
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService == null) {
            Log.e(this.LOG_TAG, "registerListener: not connected.");
            this.mQueuedCapabilityListener.add(listener);
        } else {
            String token = semImsCapabilityService.registerListener(listener.callback, this.mPhoneId);
            if (token != null) {
                listener.setToken(token);
            }
        }
    }

    public void unregisterListener(SemCapabilityListener listener) throws RemoteException {
        if (listener == null) {
            return;
        }
        Log.i(this.LOG_TAG, "unregisterListener: listener = " + listener);
        if (this.mImsCapabilityService == null) {
            Log.e(this.LOG_TAG, "unregisterListener: not connected.");
            this.mQueuedCapabilityListener.remove(listener);
        } else {
            String token = listener.getToken();
            this.mImsCapabilityService.unregisterListener(token, this.mPhoneId);
            listener.setToken("");
        }
    }
}

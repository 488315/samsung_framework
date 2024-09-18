package com.samsung.android.telecom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;

/* loaded from: classes5.dex */
public class SamsungTelecomServiceConnection {
    private static final String SERVICE_ACTION = "com.samsung.android.telecom.ISamsungTelecomService";
    private static final ComponentName SERVICE_COMPONENT = new ComponentName("com.android.server.telecom", "com.samsung.server.telecom.SamsungTelecomService");
    private static final String TAG = "SamsungTelecomServiceConnection";
    private final Context mContext;
    private final Object mLock;
    private TelecomServiceConnection mTelecomServiceConnection;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class TelecomServiceConnection implements ServiceConnection {
        private TelecomServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.telecom.SamsungTelecomServiceConnection.TelecomServiceConnection.1
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        SamsungTelecomServiceConnection.this.connectToSamsungTelecom();
                    }
                }, 0);
                Slog.i(SamsungTelecomServiceConnection.TAG, "connectToSamsungTelecom - ServiceManager.addService : " + service);
                ServiceManager.addService(Context.SEM_TELECOM_SERVICE, service);
            } catch (RemoteException e) {
                Slog.i(SamsungTelecomServiceConnection.TAG, "Failed linking to death.");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            SamsungTelecomServiceConnection.this.connectToSamsungTelecom();
        }
    }

    public SamsungTelecomServiceConnection(Context context, Object lock) {
        this.mContext = context;
        this.mLock = lock;
    }

    public void connectToSamsungTelecom() {
        if (hasSamsungTelecomSystemFeature()) {
            synchronized (this.mLock) {
                TelecomServiceConnection telecomServiceConnection = this.mTelecomServiceConnection;
                if (telecomServiceConnection != null) {
                    this.mContext.unbindService(telecomServiceConnection);
                    this.mTelecomServiceConnection = null;
                }
                TelecomServiceConnection telecomServiceConnection2 = new TelecomServiceConnection();
                Intent intent = new Intent(SERVICE_ACTION);
                ComponentName componentName = SERVICE_COMPONENT;
                intent.setComponent(componentName);
                Slog.i(TAG, "connectToSamsungTelecom - Attempting to bind to : " + componentName);
                if (this.mContext.bindServiceAsUser(intent, telecomServiceConnection2, 67108929, UserHandle.SYSTEM)) {
                    Slog.i(TAG, "connectToSamsungTelecom - Succeeded to connect");
                    this.mTelecomServiceConnection = telecomServiceConnection2;
                } else {
                    Slog.i(TAG, "connectToSamsungTelecom - Failed to connect");
                }
            }
        }
    }

    private boolean hasSamsungTelecomSystemFeature() {
        return SemTelecomManager.hasSamsungTelecomSystemFeature();
    }
}

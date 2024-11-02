package com.samsung.android.knox.dar.ddar.proxy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class KnoxProxyAgent extends Service {
    private static final String TAG = "KnoxProxyAgent";
    Object mPolicyServiceLock = new Object();
    HashMap<String, IProxyAgentService> mPolicyServiceMap = new HashMap<>();

    @Override // android.app.Service
    public void onCreate() {
        Log.i(TAG, "onCreate!");
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ServiceImpl();
    }

    /* loaded from: classes5.dex */
    private final class ServiceImpl extends IProxyAgent.Stub {
        /* synthetic */ ServiceImpl(KnoxProxyAgent knoxProxyAgent, ServiceImplIA serviceImplIA) {
            this();
        }

        private ServiceImpl() {
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public Bundle onMessage(int callingUid, String svcName, String action, Bundle args) throws RemoteException {
            return KnoxProxyAgent.this.relay(callingUid, svcName, action, args);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public String initializeSecureSession(int callingUid, String svcName, String secureClientId, String secureClientPubKey) {
            return KnoxProxyAgent.this.establishSecureSession(callingUid, svcName, secureClientId, secureClientPubKey);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public boolean terminateSecureSession(int callingUid, String svcName, String secureClientId) {
            return KnoxProxyAgent.this.teardownSecureSession(callingUid, svcName, secureClientId);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public void onAgentReconnected() {
            KnoxProxyAgent.this.onAgentReconnected();
        }
    }

    public Bundle relay(int callingUid, String svcName, String action, Bundle args) {
        IProxyAgentService svc = getService(svcName);
        if (svc == null) {
            Log.e(TAG, "Service not found");
            return null;
        }
        return svc.onMessage(callingUid, action, args);
    }

    protected String establishSecureSession(int callingUid, String svcName, String secureClientId, String secureClientPubKey) {
        IProxyAgentService svc = getService(svcName);
        if (svc == null) {
            Log.e(TAG, "Service not found");
            return null;
        }
        return svc.initializeSecureSession(callingUid, svcName, secureClientId, secureClientPubKey);
    }

    protected boolean teardownSecureSession(int callingUid, String svcName, String secureClientId) {
        IProxyAgentService svc = getService(svcName);
        if (svc == null) {
            Log.e(TAG, "Service not found");
            return false;
        }
        return svc.terminateSecureSession(callingUid, svcName, secureClientId);
    }

    protected void onAgentReconnected() {
    }

    public boolean register(String name, IProxyAgentService service) {
        synchronized (this.mPolicyServiceLock) {
            if (this.mPolicyServiceMap.containsKey(name)) {
                Log.i(TAG, "Proxy Agent Service " + name + " already exists");
                return false;
            }
            this.mPolicyServiceMap.put(name, service);
            Log.i(TAG, "Proxy Agent Service " + name + " registered");
            return true;
        }
    }

    public IProxyAgentService getService(String name) {
        synchronized (this.mPolicyServiceLock) {
            if (!this.mPolicyServiceMap.containsKey(name)) {
                Log.i(TAG, "Proxy Agent Service " + name + " not found");
                return null;
            }
            return this.mPolicyServiceMap.get(name);
        }
    }
}

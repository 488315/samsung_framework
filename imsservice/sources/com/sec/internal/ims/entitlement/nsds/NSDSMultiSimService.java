package com.sec.internal.ims.entitlement.nsds;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.sec.ims.extensions.Extensions;
import com.sec.internal.constants.ims.entitilement.NSDSContractExt;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.ims.entitlement.nsds.app.fcm.ericssonnsds.NsdsFcmListenerService;
import com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase;
import com.sec.internal.ims.entitlement.storagehelper.MigrationHelper;
import com.sec.internal.ims.entitlement.util.NSDSConfigHelper;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.log.IMSLog;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class NSDSMultiSimService extends Service {
    private static final String LOG_TAG = NSDSMultiSimService.class.getSimpleName();
    public static boolean[] mSimEvtRegistered = new boolean[SimUtil.getPhoneCount()];
    private Messenger mMessenger;
    protected ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private Context mContext = null;
    protected Map<Integer, NSDSModuleBase> mModuleMap = new ConcurrentHashMap();
    private ConnectivityManager.NetworkCallback mDefaultNetworkCallback = null;
    private NsdsFcmListenerService mNsdsFcmListenerService = null;

    protected final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            IMSLog.i(NSDSMultiSimService.LOG_TAG, "handleMessage:" + message.what);
            int i = message.what;
            if (i == 3) {
                NSDSMultiSimService.this.activateSimDevice(message.getData());
                return;
            }
            if (i == 4) {
                NSDSMultiSimService.this.deactivateSimDevice(message.getData());
                return;
            }
            if (i == 5) {
                NSDSMultiSimService.this.bindNSDSMultiSimService();
                return;
            }
            if (i == 19) {
                NSDSMultiSimService.this.updateE911Address(message.getData());
                return;
            }
            if (i == 49) {
                NSDSMultiSimService.this.retrieveAkaToken(message.getData());
                return;
            }
            if (i == 100) {
                NSDSMultiSimService.this.onEventSimReady((Bundle) message.obj);
                return;
            }
            if (i == 212) {
                NSDSMultiSimService.this.updateEntitlementUrl(message.getData());
                return;
            }
            if (i == 220) {
                NSDSMultiSimService.this.handleVoWifToggleOnEvent(message.getData());
                return;
            }
            if (i == 221) {
                NSDSMultiSimService.this.handleVoWifToggleOffEvent(message.getData());
            } else if (i == 223) {
                NSDSMultiSimService.this.registerNsdsEventMessenger(message.replyTo);
            } else {
                if (i != 224) {
                    return;
                }
                NSDSMultiSimService.this.unregisterNsdsEventMessenger(message.replyTo);
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            if (Extensions.UserHandle.myUserId() != 0) {
                IMSLog.i(LOG_TAG, "Do not initialize on non-system user");
                return;
            }
        } catch (IllegalStateException e) {
            IMSLog.s(LOG_TAG, "IllegalStateException occurred" + e.getMessage());
        }
        IMSLog.i(LOG_TAG, "onCreate");
        this.mContext = getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("NSDSMultiSimService", 10);
        handlerThread.start();
        this.mServiceLooper = handlerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
        this.mMessenger = new Messenger(this.mServiceHandler);
        this.mNsdsFcmListenerService = NsdsFcmListenerService.getInstance(this.mContext);
        NSDSSimEventManager.createInstance(this.mServiceLooper, this);
        NSDSModuleFactory.createInstance(this.mServiceLooper, this);
        ImsRegistry.getFcmHandler().registerFcmEventListener(this.mNsdsFcmListenerService);
        try {
            Message message = new Message();
            message.what = 5;
            this.mMessenger.send(message);
        } catch (RemoteException unused) {
            IMSLog.i(LOG_TAG, "initialize failed");
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (Extensions.UserHandle.myUserId() != 0) {
            IMSLog.i(LOG_TAG, "Do not allow bind on non-system user");
            return null;
        }
        IMSLog.i(LOG_TAG, "onBind");
        return this.mMessenger.getBinder();
    }

    @Override // android.app.Service
    public void onDestroy() {
        IMSLog.i(LOG_TAG, "onDestroy");
        if (this.mMessenger != null) {
            NSDSSimEventManager.getInstance().unregisterSimEventMessenger(this.mMessenger);
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        String str = LOG_TAG;
        IMSLog.i(str, "onStartCommand");
        if (intent != null) {
            int intExtra = intent.getIntExtra(NSDSNamespaces.NSDSExtras.SIM_SLOT_IDX, 0);
            if (!mSimEvtRegistered[intExtra]) {
                registerForSimEvents(intExtra);
            }
            IMSLog.i(str, "Received <" + intExtra + "> startId:" + i2 + " intent:" + intent);
            handleIntent(intent, i, i2);
            return 1;
        }
        IMSLog.i(str, "handleIntent() - Intent is null. return....");
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindNSDSMultiSimService() {
        IMSLog.i(LOG_TAG, "bindNSDSMultiSimService");
        this.mContext.getContentResolver().update(Uri.withAppendedPath(NSDSContractExt.AUTHORITY_URI, "binding_service"), new ContentValues(), null, null);
    }

    private void registerForSimEvents(int i) {
        NSDSSimEventManager.getInstance().registerSimEventMessenger(this.mMessenger, i);
        mSimEvtRegistered[i] = true;
    }

    public void handleIntent(Intent intent, int i, int i2) {
        String str = LOG_TAG;
        IMSLog.i(str, "Received startId:" + i2 + " flags:" + i + " intent:" + intent);
        String action = intent.getAction();
        StringBuilder sb = new StringBuilder();
        sb.append("onStartCommand: ");
        sb.append(action);
        IMSLog.i(str, sb.toString());
        String stringExtra = intent.getStringExtra("imsi");
        if (NSDSNamespaces.NSDSActions.ACTION_REFRESH_DEVICE_CONFIG.equals(action)) {
            refreshDeviceConfig(stringExtra);
        } else if (NSDSNamespaces.NSDSActions.ACTION_SIM_DEVICE_ACTIVATION.equals(action)) {
            activateSimDevice(11, 0);
        } else if (NSDSNamespaces.NSDSActions.ACTION_REFRESH_GCM_TOKEN.equals(action)) {
            getGcmRegistrationToken(stringExtra);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEntitlementUrl(Bundle bundle) {
        String string = bundle.getString("IMSI");
        String string2 = bundle.getString("URL");
        NSDSModuleBase nsdsModuleForImsi = getNsdsModuleForImsi(string);
        if (nsdsModuleForImsi != null) {
            nsdsModuleForImsi.updateEntitlementUrl(string2);
        }
    }

    public void refreshDeviceConfig(String str) {
        if (!TextUtils.isEmpty(str)) {
            NSDSModuleBase nsdsModuleForImsi = getNsdsModuleForImsi(str);
            if (nsdsModuleForImsi != null) {
                nsdsModuleForImsi.queueRefreshDeviceConfig(0);
                return;
            }
            return;
        }
        IMSLog.s(LOG_TAG, "Refresh device config for all modules");
        Iterator<NSDSModuleBase> it = this.mModuleMap.values().iterator();
        while (it.hasNext()) {
            it.next().queueRefreshDeviceConfig(0);
        }
    }

    private void getGcmRegistrationToken(String str) {
        ISimManager simManager;
        if (TextUtils.isEmpty(str) && (simManager = getSimManager(0)) != null) {
            str = simManager.getImsi();
        }
        NSDSModuleBase nsdsModuleForImsi = getNsdsModuleForImsi(str);
        if (nsdsModuleForImsi != null) {
            nsdsModuleForImsi.queueGcmTokenRetrieval();
        }
    }

    private NSDSModuleBase getNsdsModuleForImsi(String str) {
        int activeDataPhoneId = SimUtil.getActiveDataPhoneId();
        if (TextUtils.isEmpty(str)) {
            return getNsdsModuleForSimSlot(activeDataPhoneId);
        }
        ISimManager simManager = getSimManager(str);
        if (simManager != null && simManager.getSimSlotIndex() != -1) {
            return getNsdsModuleForSimSlot(simManager.getSimSlotIndex());
        }
        IMSLog.s(LOG_TAG, "Could not find any NSDSModule for imsi:" + str + ", returning for ADS slot");
        return getNsdsModuleForSimSlot(activeDataPhoneId);
    }

    private NSDSModuleBase getNsdsModuleForSimSlot(int i) {
        NSDSModuleBase nSDSModuleBase = this.mModuleMap.get(Integer.valueOf(i));
        if (nSDSModuleBase == null && (nSDSModuleBase = NSDSModuleFactory.getInstance().getNsdsModule(getSimManager(i))) != null) {
            this.mModuleMap.put(Integer.valueOf(i), nSDSModuleBase);
            IMSLog.i(LOG_TAG, "creating NSDSModule for simSlot:" + i);
        }
        return nSDSModuleBase;
    }

    private ISimManager getSimManager(int i) {
        return NSDSSimEventManager.getInstance().getSimManagerFromSimSlot(i);
    }

    private ISimManager getSimManager(String str) {
        return NSDSSimEventManager.getInstance().getSimManager(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerNsdsEventMessenger(Messenger messenger) {
        IMSLog.i(LOG_TAG, "registerNsdsEventMessenger");
        NSDSAppFlowBase.registerEventMessenger(messenger);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterNsdsEventMessenger(Messenger messenger) {
        NSDSAppFlowBase.unregisterEventMessenger(messenger);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retrieveAkaToken(Bundle bundle) {
        String string = bundle.getString("IMSI");
        int i = bundle.getInt("EVENT_TYPE", 19);
        int i2 = bundle.getInt("RETRYCOUNT", 0);
        NSDSModuleBase nsdsModuleForImsi = getNsdsModuleForImsi(string);
        if (nsdsModuleForImsi != null) {
            nsdsModuleForImsi.retrieveAkaToken(i, i2);
        }
    }

    private NSDSModuleBase getVSimModuleForSimSlot2(int i) {
        return this.mModuleMap.get(Integer.valueOf(i));
    }

    private void activateSimDevice(int i, int i2) {
        IMSLog.i(LOG_TAG, "activateSimDevice: deviceEventType " + i);
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(0);
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.activateSimDevice(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activateSimDevice(Bundle bundle) {
        int i = bundle.getInt("SLOT_ID", 0);
        int i2 = bundle.getInt("EVENT_TYPE", 11);
        int i3 = bundle.getInt("RETRYCOUNT", 0);
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(i);
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.activateSimDevice(i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateE911Address(Bundle bundle) {
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(bundle.getInt("SLOT_ID", 0));
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.updateE911Address();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleVoWifToggleOnEvent(Bundle bundle) {
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(bundle.getInt("SLOT_ID", 0));
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.handleVoWifToggleOnEvent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleVoWifToggleOffEvent(Bundle bundle) {
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(bundle.getInt("SLOT_ID", 0));
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.handleVoWifToggleOffEvent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deactivateSimDevice(Bundle bundle) {
        NSDSModuleBase nsdsModuleForImsi = getNsdsModuleForImsi(bundle.getString("IMSI"));
        if (nsdsModuleForImsi != null) {
            nsdsModuleForImsi.deactivateSimDevice(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEventSimReady(Bundle bundle) {
        int i = bundle.getInt(NSDSNamespaces.NSDSExtras.SIM_SLOT_IDX, 0);
        boolean z = bundle.getBoolean(NSDSNamespaces.NSDSExtras.SIM_ABSENT, false);
        boolean z2 = bundle.getBoolean(NSDSNamespaces.NSDSExtras.SIM_SWAPPED, false);
        String str = LOG_TAG;
        IMSLog.i(str, "onEventSimReady: isSimAbsent " + z);
        if (z) {
            onSimStateNotAvailable(i);
            return;
        }
        IMSLog.i(str, i, " isSimSwapped:" + z2);
        NSDSModuleBase nsdsModuleForSimSlot = getNsdsModuleForSimSlot(i);
        if (nsdsModuleForSimSlot != null) {
            nsdsModuleForSimSlot.onSimReady(z2);
            if (isDeviceReady()) {
                nsdsModuleForSimSlot.onDeviceReady();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceReady() {
        if (!NSDSConfigHelper.isUserUnlocked(this.mContext)) {
            IMSLog.i(LOG_TAG, "isDeviceReady() User lock ");
            return false;
        }
        if (!MigrationHelper.checkMigrateDB(this.mContext)) {
            MigrationHelper.migrateDBToCe(this.mContext);
        }
        if (!NetworkUtil.isConnected(this.mContext)) {
            registerDefaultNetworkCallback();
            return false;
        }
        unregisterNetworkCallback();
        return true;
    }

    private ConnectivityManager.NetworkCallback getDefaultNetworkCallback() {
        return new ConnectivityManager.NetworkCallback() { // from class: com.sec.internal.ims.entitlement.nsds.NSDSMultiSimService.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                IMSLog.i(NSDSMultiSimService.LOG_TAG, "onAvailable");
                if (network == null || !NSDSMultiSimService.this.isDeviceReady()) {
                    return;
                }
                NSDSMultiSimService.this.onDeviceReady();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                IMSLog.i(NSDSMultiSimService.LOG_TAG, "onLost");
            }
        };
    }

    private void registerDefaultNetworkCallback() {
        ConnectivityManager connectivityManager;
        if (this.mDefaultNetworkCallback != null || (connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity")) == null) {
            return;
        }
        IMSLog.i(LOG_TAG, "registerDefaultNetworkCallback");
        ConnectivityManager.NetworkCallback defaultNetworkCallback = getDefaultNetworkCallback();
        this.mDefaultNetworkCallback = defaultNetworkCallback;
        connectivityManager.registerDefaultNetworkCallback(defaultNetworkCallback);
    }

    private void unregisterNetworkCallback() {
        ConnectivityManager connectivityManager;
        if (this.mDefaultNetworkCallback == null || (connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity")) == null) {
            return;
        }
        IMSLog.i(LOG_TAG, "unregisterNetworkCallback");
        connectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        this.mDefaultNetworkCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceReady() {
        IMSLog.i(LOG_TAG, "onDeviceReady()");
        for (NSDSModuleBase nSDSModuleBase : this.mModuleMap.values()) {
            nSDSModuleBase.initForDeviceReady();
            nSDSModuleBase.onDeviceReady();
        }
    }

    private void onSimStateNotAvailable(int i) {
        String str = LOG_TAG;
        IMSLog.i(str, "onSimStateNotAvailable()");
        NSDSModuleBase vSimModuleForSimSlot2 = getVSimModuleForSimSlot2(i);
        if (vSimModuleForSimSlot2 != null) {
            vSimModuleForSimSlot2.onSimNotAvailable();
        } else {
            IMSLog.i(str, "onSimStateNotAvailable() - nsdsModule is null");
        }
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IMSLog.prepareDump(this.mContext, printWriter);
        Iterator<NSDSModuleBase> it = this.mModuleMap.values().iterator();
        while (it.hasNext()) {
            it.next().dump();
        }
        IMSLog.postDump(printWriter);
    }

    public static void startNsdsMultiSimService(Context context, int i) {
        IMSLog.i(LOG_TAG, "startNsdsMultiSimService()");
        Intent intent = new Intent(context, (Class<?>) NSDSMultiSimService.class);
        intent.putExtra(NSDSNamespaces.NSDSExtras.SIM_SLOT_IDX, i);
        context.startService(intent);
    }
}

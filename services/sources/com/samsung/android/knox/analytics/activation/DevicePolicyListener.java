package com.samsung.android.knox.analytics.activation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.samsung.android.knox.analytics.util.Log;

/* loaded from: classes2.dex */
public class DevicePolicyListener extends BroadcastReceiver {
    public static final String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
    public static final String ACTION_PROFILE_OWNER_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final String ACTION_PROFILE_OWNER_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final String EXTRA_DO_CHANGED_STATUS = "com.samsung.android.knox.intent.extra.EXTRA_DO_CHANGED_STATUS";
    public static final String EXTRA_DO_PO_PACKAGE_NAME = "com.samsung.android.knox.intent.extra.EXTRA_DO_PO_PACKAGE_NAME";
    public static final String TAG = "[KnoxAnalytics] " + DevicePolicyListener.class.getSimpleName();
    public final ActivationMonitor mActivationMonitor;
    public final Context mContext;

    public DevicePolicyListener(ActivationMonitor activationMonitor, Context context) {
        this.mActivationMonitor = activationMonitor;
        this.mContext = context;
    }

    public void register() {
        Log.d(TAG, "start()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DEVICE_OWNER_CHANGED);
        intentFilter.addAction(ACTION_PROFILE_OWNER_ADDED);
        intentFilter.addAction(ACTION_PROFILE_OWNER_REMOVED);
        this.mContext.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive(" + intent.getAction() + ")");
        String action = intent.getAction();
        action.hashCode();
        char c = 65535;
        switch (action.hashCode()) {
            case -385593787:
                if (action.equals(ACTION_PROFILE_OWNER_ADDED)) {
                    c = 0;
                    break;
                }
                break;
            case 370775467:
                if (action.equals(ACTION_DEVICE_OWNER_CHANGED)) {
                    c = 1;
                    break;
                }
                break;
            case 1051477093:
                if (action.equals(ACTION_PROFILE_OWNER_REMOVED)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
                if (userHandle != null) {
                    this.mActivationMonitor.onPoAdded(userHandle.getIdentifier());
                    return;
                }
                return;
            case 1:
                this.mActivationMonitor.checkDO(intent.getStringExtra(EXTRA_DO_PO_PACKAGE_NAME), intent.getBooleanExtra(EXTRA_DO_CHANGED_STATUS, false));
                return;
            case 2:
                this.mActivationMonitor.onPoRemoved(intent.getStringExtra(EXTRA_DO_PO_PACKAGE_NAME));
                return;
            default:
                return;
        }
    }
}

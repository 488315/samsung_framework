package com.android.systemui.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.debug.IAdbManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.ServiceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.systemui.R;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class WifiDebuggingActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CheckBox mAlwaysAllow;
    public String mBssid;
    public boolean mClicked = false;
    public WifiChangeReceiver mWifiChangeReceiver;
    public WifiManager mWifiManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class WifiChangeReceiver extends BroadcastReceiver {
        public final Activity mActivity;

        public WifiChangeReceiver(Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                if (intent.getIntExtra("wifi_state", 1) == 1) {
                    this.mActivity.finish();
                    return;
                }
                return;
            }
            if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                if (networkInfo.getType() == 1) {
                    if (!networkInfo.isConnected()) {
                        this.mActivity.finish();
                        return;
                    }
                    WifiInfo connectionInfo = WifiDebuggingActivity.this.mWifiManager.getConnectionInfo();
                    if (connectionInfo != null && connectionInfo.getNetworkId() != -1) {
                        String bssid = connectionInfo.getBSSID();
                        if (bssid != null && !bssid.isEmpty()) {
                            if (!bssid.equals(WifiDebuggingActivity.this.mBssid)) {
                                this.mActivity.finish();
                                return;
                            }
                            return;
                        }
                        this.mActivity.finish();
                        return;
                    }
                    this.mActivity.finish();
                }
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        boolean z;
        boolean z2 = true;
        this.mClicked = true;
        if (i == -1) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !this.mAlwaysAllow.isChecked()) {
            z2 = false;
        }
        try {
            IAdbManager asInterface = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
            if (z) {
                asInterface.allowWirelessDebugging(z2, this.mBssid);
            } else {
                asInterface.denyWirelessDebugging();
            }
        } catch (Exception e) {
            Log.e("WifiDebuggingActivity", "Unable to notify Adb service", e);
        }
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        Window window = getWindow();
        window.addSystemFlags(524288);
        window.setType(2008);
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiChangeReceiver = new WifiChangeReceiver(this);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("ssid");
        String stringExtra2 = intent.getStringExtra("bssid");
        this.mBssid = stringExtra2;
        if (stringExtra != null && stringExtra2 != null) {
            AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
            alertParams.mTitle = getString(R.string.wifi_debugging_title);
            alertParams.mMessage = getString(R.string.wifi_debugging_message, new Object[]{stringExtra, this.mBssid});
            alertParams.mPositiveButtonText = getString(R.string.wifi_debugging_allow);
            alertParams.mNegativeButtonText = getString(android.R.string.cancel);
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonListener = this;
            View inflate = LayoutInflater.from(alertParams.mContext).inflate(android.R.layout.app_perms_summary, (ViewGroup) null);
            CheckBox checkBox = (CheckBox) inflate.findViewById(android.R.id.autofill_save_yes);
            this.mAlwaysAllow = checkBox;
            checkBox.setText(getString(R.string.wifi_debugging_always));
            alertParams.mView = inflate;
            window.setCloseOnTouchOutside(false);
            setupAlert();
            ((AlertActivity) this).mAlert.getButton(-1).setOnTouchListener(new WifiDebuggingActivity$$ExternalSyntheticLambda0());
            return;
        }
        finish();
    }

    public final void onDestroy() {
        super.onDestroy();
        if (!this.mClicked) {
            try {
                IAdbManager.Stub.asInterface(ServiceManager.getService("adb")).denyWirelessDebugging();
            } catch (Exception e) {
                Log.e("WifiDebuggingActivity", "Unable to notify Adb service", e);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(this.mWifiChangeReceiver, intentFilter);
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public final void onStop() {
        WifiChangeReceiver wifiChangeReceiver = this.mWifiChangeReceiver;
        if (wifiChangeReceiver != null) {
            unregisterReceiver(wifiChangeReceiver);
        }
        super.onStop();
    }

    public final void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        super.onWindowAttributesChanged(layoutParams);
    }
}

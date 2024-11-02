package com.android.systemui.net;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.INetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class NetworkOverLimitActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SystemUIDialog mDialog;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        final NetworkTemplate parcelableExtra = getIntent().getParcelableExtra("android.net.NETWORK_TEMPLATE");
        SystemUIDialog systemUIDialog = new SystemUIDialog(this, 2132018540);
        this.mDialog = systemUIDialog;
        if (parcelableExtra == null) {
            Log.d("NetworkOverLimitActivity", "invalid template");
            return;
        }
        if (parcelableExtra.getMatchRule() != 10) {
            i = R.string.data_usage_disabled_dialog_title;
        } else {
            i = R.string.data_connection_data_limit_data_usage_disabled_title;
        }
        systemUIDialog.setTitle(i);
        this.mDialog.setMessage(R.string.data_connection_data_limit_data_usage_disabled_body);
        this.mDialog.setPositiveButton(R.string.data_connection_data_limit_resume_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.net.NetworkOverLimitActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                NetworkOverLimitActivity networkOverLimitActivity = NetworkOverLimitActivity.this;
                NetworkTemplate networkTemplate = parcelableExtra;
                int i3 = NetworkOverLimitActivity.$r8$clinit;
                networkOverLimitActivity.getClass();
                try {
                    INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy")).snoozeLimit(networkTemplate);
                } catch (RemoteException e) {
                    Log.w("NetworkOverLimitActivity", "problem snoozing network policy", e);
                }
            }
        });
        this.mDialog.setNegativeButton(R.string.cancel, null);
        this.mDialog.getWindow().setType(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.net.NetworkOverLimitActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                NetworkOverLimitActivity networkOverLimitActivity = NetworkOverLimitActivity.this;
                int i2 = NetworkOverLimitActivity.$r8$clinit;
                networkOverLimitActivity.finish();
            }
        });
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= 2;
        attributes.dimAmount = 0.3f;
        attributes.gravity = 80;
        this.mDialog.getWindow().setAttributes(attributes);
        this.mDialog.show();
    }

    @Override // android.app.Activity
    public final void onPause() {
        if (this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        super.onPause();
    }
}

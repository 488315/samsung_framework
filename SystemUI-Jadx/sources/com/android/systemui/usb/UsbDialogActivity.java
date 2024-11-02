package com.android.systemui.usb;

import android.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class UsbDialogActivity extends AlertActivity implements DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public CheckBox mAlwaysUse;
    public TextView mCheckBoxText;
    public TextView mClearDefaultHint;
    public UsbDialogHelper mDialogHelper;

    public final void addAlwaysUseCheckbox() {
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        View inflate = ((LayoutInflater) getSystemService(LayoutInflater.class)).inflate(R.layout.typing_filter, (ViewGroup) null);
        alertParams.mView = inflate;
        this.mAlwaysUse = (CheckBox) inflate.findViewById(R.id.userIdentification);
        TextView textView = (TextView) alertParams.mView.findViewById(R.id.userLandscape);
        this.mCheckBoxText = textView;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (!usbDialogHelper.mIsUsbDevice) {
            textView.setText(getString(com.android.systemui.R.string.always_use_accessory, new Object[]{usbDialogHelper.mAppName, usbDialogHelper.getDeviceDescription()}));
        } else {
            textView.setText(getString(com.android.systemui.R.string.always_use_device, new Object[]{usbDialogHelper.mAppName, usbDialogHelper.getDeviceDescription()}));
        }
        this.mCheckBoxText.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.usb.UsbDialogActivity.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UsbDialogActivity.this.mAlwaysUse.toggle();
            }
        });
        this.mAlwaysUse.setOnCheckedChangeListener(this);
        TextView textView2 = (TextView) alertParams.mView.findViewById(R.id.userPortrait);
        this.mClearDefaultHint = textView2;
        textView2.setVisibility(8);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onCheckedChanged: isChecked=", z, "UsbDialogActivity");
        TextView textView = this.mClearDefaultHint;
        if (textView == null) {
            return;
        }
        if (z) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            onConfirm();
        } else {
            finish();
        }
    }

    public abstract void onConfirm();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        try {
            this.mDialogHelper = new UsbDialogHelper(getApplicationContext(), getIntent());
        } catch (IllegalStateException e) {
            Log.e("UsbDialogActivity", "unable to initialize", e);
            finish();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onPause() {
        UsbDisconnectedReceiver usbDisconnectedReceiver;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (usbDialogHelper != null && (usbDisconnectedReceiver = usbDialogHelper.mDisconnectedReceiver) != null) {
            try {
                unregisterReceiver(usbDisconnectedReceiver);
            } catch (Exception unused) {
            }
            usbDialogHelper.mDisconnectedReceiver = null;
        }
        super.onPause();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onResume() {
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (usbDialogHelper.mIsUsbDevice) {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mDevice);
        } else {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mAccessory);
        }
    }

    public final void setAlertParams(String str, String str2) {
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = str;
        alertParams.mMessage = str2;
        alertParams.mPositiveButtonText = getString(R.string.ok);
        alertParams.mNegativeButtonText = getString(R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
        getWindow().setGravity(80);
    }
}

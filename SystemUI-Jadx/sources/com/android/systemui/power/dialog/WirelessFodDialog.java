package com.android.systemui.power.dialog;

import android.content.Context;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WirelessFodDialog extends PowerUiDialog {
    public WirelessFodDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        Context context = this.mContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = false;
        alertParams.mTitle = context.getString(R.string.battery_wireless_charger_fod_title);
        alertParams.mMessage = context.getString(R.string.battery_wireless_charger_fod_text);
        alertParams.mNegativeButtonText = context.getString(R.string.dialog_button_text_ok);
        alertParams.mNegativeButtonListener = null;
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}

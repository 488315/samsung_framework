package com.android.systemui.volume.view.warnings;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class VolumeCSD100WarningDialog extends VolumeWarningDialog implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener, VolumeObserver {
    public final StoreInteractor storeInteractor;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VolumeCSD100WarningDialog(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        initDialog();
        String string = getContext().getString(R.string.tw_volume_csd_100_warning);
        AlertController alertController = this.mAlert;
        alertController.mMessage = string;
        TextView textView = alertController.mMessageView;
        if (textView != null) {
            textView.setText(string);
        }
        setButton(-1, getContext().getString(android.R.string.yes), this);
    }

    @Override // com.android.systemui.volume.view.warnings.VolumeWarningDialog, android.app.Dialog
    public final Window getWindow() {
        return super.getWindow();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                onDismiss(this);
                dismiss();
                return;
            }
            return;
        }
        if (volumePanelState.getCoverType() != 8) {
            dismiss();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED), true, this.storeInteractor, false);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG).build(), true);
        this.storeInteractor.dispose();
    }
}

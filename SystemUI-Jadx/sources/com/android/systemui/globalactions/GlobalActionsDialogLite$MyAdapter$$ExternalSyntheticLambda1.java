package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(BaseAdapter baseAdapter, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = baseAdapter;
        this.f$1 = i;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((GlobalActionsDialogLite.MyAdapter) this.f$0).this$0.getClass();
                throw null;
            default:
                GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter = (GlobalActionsDialogLite.MyPowerOptionsAdapter) this.f$0;
                int i = this.f$1;
                int i2 = GlobalActionsDialogLite.MyPowerOptionsAdapter.$r8$clinit;
                GlobalActionsDialogLite.Action action = myPowerOptionsAdapter.this$0.mPowerItems.get(i);
                if (action instanceof GlobalActionsDialogLite.LongPressAction) {
                    GlobalActionsDialogLite globalActionsDialogLite = myPowerOptionsAdapter.this$0;
                    if (globalActionsDialogLite.mDialog != null) {
                        globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                        myPowerOptionsAdapter.this$0.mDialog.dismiss();
                    } else {
                        Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                    }
                    return ((GlobalActionsDialogLite.LongPressAction) action).onLongPress();
                }
                return false;
        }
    }
}

package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.wm.shell.windowdecor.DexCompatRestartActivity;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DexCompatRestartDialogUtils {
    public final Context mContext;
    public boolean mIsDisableDialog;
    public final Handler mMainHandler;

    public DexCompatRestartDialogUtils(Context context, Handler handler) {
        this.mContext = context;
        this.mMainHandler = handler;
    }

    public static boolean isDexCompatEnabled(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.getConfiguration().dexCompatEnabled == 2) {
            return true;
        }
        return false;
    }

    public final void toggleFreeformForDexCompatApp(final int i) {
        if (!this.mIsDisableDialog && Settings.System.getInt(this.mContext.getContentResolver(), "disable_dexcompat_restart_dialog", 0) == 1) {
            this.mIsDisableDialog = true;
        }
        boolean z = this.mIsDisableDialog;
        Slog.d("DexCompatRestartDialogUtils", "toggleFreeformForDexCompatApp: t#" + i + ", isRestartDialogDisabled=" + z);
        if (z) {
            int i2 = DexCompatRestartActivity.$r8$clinit;
            MultiWindowManager.getInstance().toggleFreeformForDexCompatApp(i);
        } else {
            this.mMainHandler.post(new Runnable() { // from class: com.android.wm.shell.shortcut.DexCompatRestartDialogUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DexCompatRestartDialogUtils dexCompatRestartDialogUtils = DexCompatRestartDialogUtils.this;
                    int i3 = i;
                    dexCompatRestartDialogUtils.getClass();
                    int i4 = DexCompatRestartActivity.$r8$clinit;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.android.systemui", DexCompatRestartActivity.class.getName()));
                    intent.putExtra("compat_task_id", i3);
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchTaskId(i3);
                    dexCompatRestartDialogUtils.mContext.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.CURRENT);
                }
            });
        }
    }
}

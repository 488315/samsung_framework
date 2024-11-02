package com.android.wm.shell.freeform;

import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class MinimizeContainerItem extends FreeformContainerItem {
    public final int mTaskId;

    public MinimizeContainerItem(Context context, String str, ComponentName componentName, int i, int i2, boolean z) {
        super(context, str, componentName, i2);
        this.mTaskId = i;
        this.mAnimationCompleted = z;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final int getTaskId() {
        return this.mTaskId;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void launch() {
        final int i = this.mTaskId;
        final Context context = this.mContext;
        FreeformContainerSystemProxy.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTaskManager.getService().moveTaskToFront(ActivityThread.currentActivityThread().getApplicationThread(), context.getPackageName(), i, 0, (Bundle) null);
                } catch (RemoteException e) {
                    Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to moveTaskToFront: " + e);
                    throw e.rethrowFromSystemServer();
                }
            }
        });
        if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
            boolean z = true;
            if (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY) {
                z = true ^ MultiWindowUtils.isInSubDisplay(context);
            }
            if (z) {
                CoreSaLogger.logForAdvanced("2201");
            }
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void loadShowingIcon(FreeformContainerIconLoader freeformContainerIconLoader) {
        Drawable drawable;
        Context context = this.mContext;
        String str = this.mPackageName;
        int i = this.mUserId;
        freeformContainerIconLoader.getClass();
        try {
            drawable = context.getPackageManager().semGetApplicationIconForIconTray(PackageManagerUtil.getApplicationInfoAsUser(context, i, str), 48);
        } catch (Exception e) {
            e.printStackTrace();
            drawable = null;
        }
        Drawable showingIcon = freeformContainerIconLoader.getShowingIcon(drawable, null);
        if (SemPersonaManager.isKnoxId(i)) {
            Log.i("FreeformContainer", "loadShowingIcon: knox icon");
            showingIcon = freeformContainerIconLoader.mPackageManager.getUserBadgedIcon(showingIcon, new UserHandle(i));
        }
        this.mShowingIcon = showingIcon;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void removeDuplicatedItemsIfExist(FreeformContainerItemController freeformContainerItemController) {
        int i = this.mTaskId;
        FreeformContainerItem itemById = freeformContainerItemController.getItemById(i);
        while (itemById != null) {
            freeformContainerItemController.removeItem(itemById);
            itemById = freeformContainerItemController.getItemById(i);
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void throwAway(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.removeItem(this);
        FreeformContainerSystemProxy.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTaskManager.getService().removeTaskWithFlags(MinimizeContainerItem.this.mTaskId, 16);
                } catch (RemoteException e) {
                    Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to removeTask: " + e);
                    e.printStackTrace();
                }
            }
        });
    }

    public final String toString() {
        return "MinimizeContainerItem {mPackageName=" + this.mPackageName + ", mTaskId=" + this.mTaskId + ", mUserId=" + this.mUserId;
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerItem
    public final void handleMaxItem() {
    }
}

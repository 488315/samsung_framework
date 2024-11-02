package com.android.wm.shell.freeform;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class FreeformContainerItem {
    public final Context mContext;
    public String mDescription;
    public ImageView mIconView;
    public final String mPackageName;
    public Drawable mShowingIcon;
    public final int mUserId;
    public boolean mAnimationCompleted = false;
    public boolean mIconLoadCompleted = false;
    public boolean mPublishCompleted = false;

    public FreeformContainerItem(Context context, String str, ComponentName componentName, int i) {
        this.mContext = context;
        this.mPackageName = str;
        if (str.equals("com.samsung.android.messaging")) {
            context.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging").getComponent();
        }
        this.mUserId = i;
    }

    public abstract int getTaskId();

    public abstract void launch();

    public abstract void loadShowingIcon(FreeformContainerIconLoader freeformContainerIconLoader);

    public boolean needLoading(FreeformContainerItemController freeformContainerItemController) {
        return true;
    }

    public abstract void removeDuplicatedItemsIfExist(FreeformContainerItemController freeformContainerItemController);

    public void throwAway(FreeformContainerItemController freeformContainerItemController) {
        freeformContainerItemController.removeItem(this);
    }

    public void handleMaxItem() {
    }
}

package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import com.android.wm.shell.common.DisplayLayout;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TaskVisibility {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public final Context mContext;
    public DisplayLayout mDisplayLayout;
    public final SparseArray mRunningTaskInfo = new SparseArray();
    public boolean mSupportOnlyTwoUpMode;

    public TaskVisibility(Context context) {
        this.mContext = context;
    }

    public final Rect getTaskBounds(int i) {
        Rect rect = new Rect();
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mRunningTaskInfo.get(i);
        if (runningTaskInfo != null) {
            rect.set(runningTaskInfo.configuration.windowConfiguration.getBounds());
        }
        return rect;
    }

    public final boolean isMultiSplit() {
        if (CoreRune.MW_MULTI_SPLIT) {
            return isTaskVisible(12);
        }
        return false;
    }

    public final boolean isTaskVisible(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mRunningTaskInfo.get(i);
        if (runningTaskInfo != null && runningTaskInfo.isVisible) {
            return true;
        }
        return false;
    }

    public final boolean isTwoUp() {
        if (CoreRune.MW_MULTI_SPLIT) {
            if (isTaskVisible(4) && !isMultiSplit()) {
                return true;
            }
            return false;
        }
        return isTaskVisible(4);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TaskVisibility{");
        SparseArray sparseArray = this.mRunningTaskInfo;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) sparseArray.valueAt(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt + "=[TaskId=" + runningTaskInfo.taskId);
            if (DEBUG) {
                sb.append(", top=" + runningTaskInfo.topActivity);
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}

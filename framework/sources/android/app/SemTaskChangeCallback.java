package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;

/* loaded from: classes.dex */
public interface SemTaskChangeCallback {
    void onActivityRequestedOrientationChanged(int i, int i2);

    void onTaskCreated(int i, ComponentName componentName);

    void onTaskDisplayChanged(int i, int i2);

    void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onTaskRemoved(int i);

    default void onTaskRequestedOrientationChanged(int taskId, int requestedOrientation) {
    }
}

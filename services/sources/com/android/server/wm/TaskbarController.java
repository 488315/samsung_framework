package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskbarController {
    public final DisplayPolicy mDisplayPolicy;
    public WindowState mTaskbarWin;

    public TaskbarController(DisplayPolicyExt displayPolicyExt) {
        WindowManagerService windowManagerService = displayPolicyExt.mService;
        this.mDisplayPolicy = displayPolicyExt.mDisplayPolicy;
    }
}

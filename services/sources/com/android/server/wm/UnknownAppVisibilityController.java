package com.android.server.wm;

import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.wm.ActivityRecord;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UnknownAppVisibilityController {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final ArrayMap mUnknownApps = new ArrayMap();

    public UnknownAppVisibilityController(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
    }

    public final void appRemovedOrHidden(ActivityRecord activityRecord) {
        if (this.mUnknownApps.isEmpty()) {
            return;
        }
        this.mUnknownApps.remove(activityRecord);
    }

    public final String getDebugMessage() {
        StringBuilder sb = new StringBuilder();
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            sb.append("app=");
            sb.append(this.mUnknownApps.keyAt(size));
            sb.append(" state=");
            sb.append(this.mUnknownApps.valueAt(size));
            if (size != 0) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public final void notifyRelayouted(ActivityRecord activityRecord) {
        Integer num;
        if (this.mUnknownApps.isEmpty() || (num = (Integer) this.mUnknownApps.get(activityRecord)) == null) {
            return;
        }
        if (num.intValue() == 2 || activityRecord.mStartingWindow != null) {
            this.mUnknownApps.put(activityRecord, 3);
            this.mDisplayContent.notifyKeyguardFlagsChanged();
            notifyVisibilitiesUpdated();
        } else {
            if (num.intValue() != 1 || activityRecord.isState(ActivityRecord.State.RESUMED)) {
                return;
            }
            Slog.d("WindowManager", "UAVC: skip waiting for non-resumed relayouted " + activityRecord);
            this.mUnknownApps.remove(activityRecord);
        }
    }

    public final void notifyVisibilitiesUpdated() {
        boolean z = false;
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            if (((Integer) this.mUnknownApps.valueAt(size)).intValue() == 3) {
                this.mUnknownApps.removeAt(size);
                z = true;
            }
        }
        if (z) {
            this.mService.mWindowPlacerLocked.performSurfacePlacement(false);
        }
    }
}

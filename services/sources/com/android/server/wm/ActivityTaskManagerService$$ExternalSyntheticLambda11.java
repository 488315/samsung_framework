package com.android.server.wm;

import android.app.ActivityManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda11(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (i) {
            case 0:
                ActivityManager.TaskDescription taskDescription = activityRecord.taskDescription;
                if (taskDescription == null || taskDescription.getIconFilename() == null) {
                    return false;
                }
                return activityRecord.taskDescription.getIconFilename().equals(str);
            default:
                return str.equals(activityRecord.packageName) && !activityRecord.finishing;
        }
    }
}

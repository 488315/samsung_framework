package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskSupervisor$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityTaskSupervisor f$0;

    public /* synthetic */ ActivityTaskSupervisor$$ExternalSyntheticLambda1(ActivityTaskSupervisor activityTaskSupervisor, int i) {
        this.$r8$classId = i;
        this.f$0 = activityTaskSupervisor;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ActivityTaskSupervisor activityTaskSupervisor = this.f$0;
        switch (i) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                activityTaskSupervisor.getClass();
                if (activityRecord.attachedToProcess()) {
                    activityTaskSupervisor.mMultiWindowModeChangedActivities.add(activityRecord);
                    break;
                }
                break;
            case 1:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                activityTaskSupervisor.getClass();
                if (activityRecord2.attachedToProcess()) {
                    activityTaskSupervisor.mPipModeChangedActivities.add(activityRecord2);
                    activityTaskSupervisor.mMultiWindowModeChangedActivities.remove(activityRecord2);
                    break;
                }
                break;
            default:
                activityTaskSupervisor.removeTask((Task) obj, true, true, "remove-root-task");
                break;
        }
    }
}

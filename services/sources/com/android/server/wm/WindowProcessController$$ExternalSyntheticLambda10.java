package com.android.server.wm;

import com.android.internal.util.function.QuintConsumer;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ProcessRecord;
import com.android.server.am.ProcessStateRecord;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda10 implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ProcessRecord processRecord = (ProcessRecord) obj;
        int intValue = ((Integer) obj2).intValue();
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        String str = (String) obj4;
        long longValue = ((Long) obj5).longValue();
        ActivityManagerService activityManagerService = processRecord.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                processRecord.mWaitingToKill = null;
                processRecord.mClearedWaitingToKill = false;
                if (booleanValue) {
                    synchronized (processRecord.mService.mAppProfiler.mProfilerLock) {
                        processRecord.mService.mAppProfiler.mProfileData.setProfileProc(processRecord);
                    }
                }
                if (str != null) {
                    processRecord.addPackage(str, longValue, processRecord.mService.mProcessStats);
                }
                processRecord.updateProcessInfo(false, true, true);
                processRecord.setPendingUiClean(true);
                ProcessStateRecord processStateRecord = processRecord.mState;
                processStateRecord.mHasShownUi = true;
                processStateRecord.forceProcessStateUpTo(intValue);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }
}

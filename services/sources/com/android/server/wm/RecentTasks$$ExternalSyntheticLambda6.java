package com.android.server.wm;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentTasks$$ExternalSyntheticLambda6 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Task task = (Task) obj;
        WindowProcessController windowProcessController = task.mRootProcess;
        if (windowProcessController != null) {
            task.mHostProcessName = windowProcessController.mName;
        }
        return task.mHostProcessName;
    }
}

package com.android.wm.shell.recents;

import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentTasksController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentTasksController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) this.f$0;
                recentTasksController.getClass();
                ((DesktopModeTaskRepository) obj).activeTasksListeners.add(recentTasksController);
                return;
            case 1:
                ((RecentTasksController) obj).registerRecentTasksListener(((RecentTasksController.IRecentTasksImpl) this.f$0).mRecentTasksListener);
                return;
            default:
                ((RecentTasksController.IRecentTasksImpl) this.f$0).mListener.unregister();
                return;
        }
    }
}
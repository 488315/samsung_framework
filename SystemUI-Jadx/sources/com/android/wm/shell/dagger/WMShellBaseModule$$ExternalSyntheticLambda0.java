package com.android.wm.shell.dagger;

import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DesktopModeController;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import dagger.Lazy;
import java.util.function.Function;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return (DesktopTasksController) ((Lazy) obj).get();
            case 1:
                return (DesktopModeController) ((Lazy) obj).get();
            case 2:
                return ((RecentTasksController) obj).mImpl;
            case 3:
                return ((SplitScreenController) obj).mImpl;
            case 4:
                return (DesktopModeTaskRepository) ((Lazy) obj).get();
            case 5:
                return ((BubbleController) obj).asBubbles();
            case 6:
                return ((DesktopTasksController) obj).desktopMode;
            case 7:
                return ((DesktopModeController) obj).mDesktopModeImpl;
            case 8:
                return ((BackAnimationController) obj).mBackAnimation;
            default:
                return ((OneHandedController) obj).mImpl;
        }
    }
}

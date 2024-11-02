package com.android.systemui.statusbar.phone;

import android.os.Build;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEventLogger;
import com.android.systemui.notetask.NoteTaskInfo;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.wm.shell.bubbles.Bubble;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda24 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda24(CentralSurfacesImpl centralSurfacesImpl, boolean z, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
        this.f$1 = z;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NoteTaskInfo noteTaskInfo;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).setRequestTopUi(this.f$2, this.f$1);
                return;
            default:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                boolean z = this.f$1;
                String str = this.f$2;
                centralSurfacesImpl2.updateScrimController();
                NoteTaskController noteTaskController = (NoteTaskController) centralSurfacesImpl2.mNoteTaskControllerLazy.get();
                if (noteTaskController.isEnabled && (noteTaskInfo = (NoteTaskInfo) noteTaskController.infoReference.getAndSet(null)) != null && Intrinsics.areEqual(str, Bubble.getAppBubbleKeyForApp(noteTaskInfo.packageName, noteTaskInfo.user))) {
                    if (Intrinsics.areEqual(noteTaskInfo.launchMode, NoteTaskLaunchMode.AppBubble.INSTANCE)) {
                        NoteTaskEventLogger noteTaskEventLogger = noteTaskController.eventLogger;
                        if (z) {
                            int i = DebugLogger.$r8$clinit;
                            boolean z2 = Build.IS_DEBUGGABLE;
                            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                            noteTaskEventLogger.logNoteTaskOpened(noteTaskInfo);
                            return;
                        }
                        int i2 = DebugLogger.$r8$clinit;
                        boolean z3 = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                        noteTaskEventLogger.logNoteTaskClosed(noteTaskInfo);
                        return;
                    }
                    return;
                }
                return;
        }
    }
}

package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DreamOverlayTouchMonitor$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                for (DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) obj; touchSessionImpl != null; touchSessionImpl = touchSessionImpl.mPredecessor) {
                    DreamOverlayTouchMonitor.TouchSessionImpl.m1240$$Nest$monRemoved(touchSessionImpl);
                }
                return;
            default:
                DreamOverlayTouchMonitor.TouchSessionImpl.m1240$$Nest$monRemoved((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                return;
        }
    }
}

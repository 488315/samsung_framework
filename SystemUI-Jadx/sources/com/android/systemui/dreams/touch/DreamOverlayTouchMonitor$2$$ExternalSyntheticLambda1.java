package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((DreamOverlayTouchMonitor.TouchSessionImpl) obj).mEventListeners;
            case 1:
                return ((Collection) obj).stream();
            case 2:
                return ((DreamOverlayTouchMonitor.TouchSessionImpl) obj).mGestureListeners;
            default:
                return ((Collection) obj).stream();
        }
    }
}

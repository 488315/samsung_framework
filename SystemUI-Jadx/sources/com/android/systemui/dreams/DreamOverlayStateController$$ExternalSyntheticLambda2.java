package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda2(DreamOverlayStateController dreamOverlayStateController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStateController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStateController dreamOverlayStateController = this.f$0;
                DreamOverlayStateController.Callback callback = (DreamOverlayStateController.Callback) this.f$1;
                dreamOverlayStateController.getClass();
                Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
                dreamOverlayStateController.mCallbacks.remove(callback);
                return;
            case 1:
                DreamOverlayStateController dreamOverlayStateController2 = this.f$0;
                DreamOverlayStateController.Callback callback2 = (DreamOverlayStateController.Callback) this.f$1;
                dreamOverlayStateController2.getClass();
                Objects.requireNonNull(callback2, "Callback must not be null. b/128895449");
                ArrayList arrayList = dreamOverlayStateController2.mCallbacks;
                if (!arrayList.contains(callback2)) {
                    arrayList.add(callback2);
                    if (!((HashSet) dreamOverlayStateController2.mComplications).isEmpty()) {
                        callback2.onComplicationsChanged();
                        return;
                    }
                    return;
                }
                return;
            default:
                DreamOverlayStateController dreamOverlayStateController3 = this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                Iterator it = dreamOverlayStateController3.mCallbacks.iterator();
                while (it.hasNext()) {
                    consumer.accept((DreamOverlayStateController.Callback) it.next());
                }
                return;
        }
    }
}

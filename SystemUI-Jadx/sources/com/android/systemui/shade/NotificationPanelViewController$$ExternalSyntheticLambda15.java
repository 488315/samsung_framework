package com.android.systemui.shade;

import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda15 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda15(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return Float.valueOf(this.f$0.getFaceWidgetAlpha());
            case 1:
                return this.f$0.mView;
            default:
                boolean z = true;
                if (this.f$0.mPluginLockViewMode != 1) {
                    z = false;
                }
                return Boolean.valueOf(z);
        }
    }
}

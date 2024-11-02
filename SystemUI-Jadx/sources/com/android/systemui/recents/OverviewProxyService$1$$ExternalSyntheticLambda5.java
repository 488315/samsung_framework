package com.android.systemui.recents;

import android.os.Binder;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Binder f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda5(Binder binder, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = binder;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService.AnonymousClass1 anonymousClass1 = (OverviewProxyService.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.mHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda5(anonymousClass1, this.f$1, 3));
                return;
            case 1:
                OverviewProxyService.AnonymousClass1 anonymousClass12 = (OverviewProxyService.AnonymousClass1) this.f$0;
                boolean z = this.f$1;
                ArrayList arrayList = (ArrayList) anonymousClass12.this$0.mConnectionCallbacks;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onTaskbarAutohideSuspend(z);
                    } else {
                        return;
                    }
                }
            case 2:
                OverviewProxyService.AnonymousClass1 anonymousClass13 = (OverviewProxyService.AnonymousClass1) this.f$0;
                int size2 = ((ArrayList) anonymousClass13.this$0.mConnectionCallbacks).size();
                while (true) {
                    size2--;
                    if (size2 >= 0) {
                        ((OverviewProxyService.OverviewProxyListener) ((ArrayList) anonymousClass13.this$0.mConnectionCallbacks).get(size2)).onOverviewShown();
                    } else {
                        return;
                    }
                }
            case 3:
                OverviewProxyService.AnonymousClass1 anonymousClass14 = (OverviewProxyService.AnonymousClass1) this.f$0;
                boolean z2 = this.f$1;
                ArrayList arrayList2 = (ArrayList) anonymousClass14.this$0.mConnectionCallbacks;
                int size3 = arrayList2.size();
                while (true) {
                    size3--;
                    if (size3 >= 0) {
                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size3)).onHomeRotationEnabled(z2);
                    } else {
                        return;
                    }
                }
            default:
                OverviewProxyService.AnonymousClass4 anonymousClass4 = (OverviewProxyService.AnonymousClass4) this.f$0;
                boolean z3 = this.f$1;
                OverviewProxyService overviewProxyService = anonymousClass4.this$0;
                SysUiState sysUiState = overviewProxyService.mSysUiState;
                sysUiState.setFlag(33554432L, z3);
                sysUiState.commitUpdate(overviewProxyService.mContext.getDisplayId());
                return;
        }
    }
}

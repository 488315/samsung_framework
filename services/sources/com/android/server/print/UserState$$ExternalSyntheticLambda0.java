package com.android.server.print;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.print.UserState;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserState$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                UserState userState = (UserState) obj;
                synchronized (userState.mLock) {
                    try {
                        if (userState.mPrintServicesChangeListenerRecords == null) {
                            return;
                        }
                        ArrayList arrayList = new ArrayList(userState.mPrintServicesChangeListenerRecords);
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            try {
                                ((UserState.AnonymousClass3) arrayList.get(i)).listener.onPrintServicesChanged();
                            } catch (RemoteException e) {
                                Log.e("UserState", "Error notifying for print services change", e);
                            }
                        }
                        return;
                    } finally {
                    }
                }
            case 1:
                UserState userState2 = (UserState) obj;
                synchronized (userState2.mLock) {
                    userState2.onConfigurationChangedLocked();
                }
                return;
            default:
                RemotePrintService remotePrintService = (RemotePrintService) obj;
                remotePrintService.getClass();
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda0(5), remotePrintService));
                return;
        }
    }
}

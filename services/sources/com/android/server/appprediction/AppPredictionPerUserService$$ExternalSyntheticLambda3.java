package com.android.server.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.os.IInterface;
import android.service.appprediction.IPredictionService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppPredictionPerUserService$$ExternalSyntheticLambda3 implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppPredictionSessionId f$0;

    public /* synthetic */ AppPredictionPerUserService$$ExternalSyntheticLambda3(AppPredictionSessionId appPredictionSessionId, int i) {
        this.$r8$classId = i;
        this.f$0 = appPredictionSessionId;
    }

    public final void run(IInterface iInterface) {
        int i = this.$r8$classId;
        AppPredictionSessionId appPredictionSessionId = this.f$0;
        IPredictionService iPredictionService = (IPredictionService) iInterface;
        switch (i) {
            case 0:
                iPredictionService.onDestroyPredictionSession(appPredictionSessionId);
                break;
            default:
                iPredictionService.requestPredictionUpdate(appPredictionSessionId);
                break;
        }
    }
}

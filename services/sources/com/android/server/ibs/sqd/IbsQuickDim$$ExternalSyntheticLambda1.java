package com.android.server.ibs.sqd;

import android.net.Uri;
import android.util.Slog;
import com.android.server.ibs.sqd.IbsQuickDim;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IbsQuickDim$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ IbsQuickDim$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((PrintWriter) obj2).println((Integer) obj);
                break;
            default:
                IbsQuickDim.IntentReceiver intentReceiver = (IbsQuickDim.IntentReceiver) obj2;
                intentReceiver.getClass();
                if ("com.samsung.android.statsd".equals(((Uri) obj).getSchemeSpecificPart())) {
                    IbsQuickDim ibsQuickDim = intentReceiver.this$0;
                    ibsQuickDim.getClass();
                    Slog.d("IbsQuickDim", "handlePkgRemoved");
                    ibsQuickDim.mQkDimHandler.post(new IbsQuickDim$$ExternalSyntheticLambda0(ibsQuickDim, 2));
                    break;
                }
                break;
        }
    }
}

package com.android.server.broadcastradio.hal1;

import android.hardware.radio.ProgramList;
import com.android.server.broadcastradio.hal1.TunerCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TunerCallback$$ExternalSyntheticLambda0 implements TunerCallback.RunnableThrowingRemoteException {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TunerCallback f$0;
    public final /* synthetic */ ProgramList.Chunk f$1;

    public /* synthetic */ TunerCallback$$ExternalSyntheticLambda0(TunerCallback tunerCallback, ProgramList.Chunk chunk, int i) {
        this.$r8$classId = i;
        this.f$0 = tunerCallback;
        this.f$1 = chunk;
    }

    @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$sendProgramListUpdate$9(this.f$1);
                break;
            default:
                this.f$0.lambda$onProgramListUpdated$10(this.f$1);
                break;
        }
    }
}

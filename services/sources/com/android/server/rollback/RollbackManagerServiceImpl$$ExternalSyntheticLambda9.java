package com.android.server.rollback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RollbackManagerServiceImpl$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RollbackManagerServiceImpl f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ RollbackManagerServiceImpl$$ExternalSyntheticLambda9(RollbackManagerServiceImpl rollbackManagerServiceImpl, long j, int i) {
        this.$r8$classId = i;
        this.f$0 = rollbackManagerServiceImpl;
        this.f$1 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RollbackManagerServiceImpl rollbackManagerServiceImpl = this.f$0;
                long j = this.f$1;
                rollbackManagerServiceImpl.assertInWorkerThread();
                rollbackManagerServiceImpl.mSleepDuration.addLast(j);
                return;
            default:
                RollbackManagerServiceImpl rollbackManagerServiceImpl2 = this.f$0;
                long j2 = this.f$1;
                rollbackManagerServiceImpl2.assertInWorkerThread();
                try {
                    Thread.sleep(j2);
                    return;
                } catch (InterruptedException unused) {
                    throw new IllegalStateException("RollbackManagerHandlerThread interrupted");
                }
        }
    }
}

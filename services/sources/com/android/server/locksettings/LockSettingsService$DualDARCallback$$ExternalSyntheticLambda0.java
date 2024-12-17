package com.android.server.locksettings;

import android.os.IBinder;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.locksettings.LockSettingsService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LockSettingsService$DualDARCallback$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LockSettingsService.DualDARCallback f$0;

    public /* synthetic */ LockSettingsService$DualDARCallback$$ExternalSyntheticLambda0(LockSettingsService.DualDARCallback dualDARCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = dualDARCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        LockSettingsService.DualDARCallback dualDARCallback = this.f$0;
        IBinder iBinder = (IBinder) obj;
        dualDARCallback.getClass();
        switch (i) {
            case 0:
                try {
                    iBinder.linkToDeath(dualDARCallback, 0);
                    break;
                } catch (Exception e) {
                    SDPLog.e(e, "LockSettingsService.DDAR", "Failed to link death listener...");
                    return;
                }
            default:
                try {
                    iBinder.unlinkToDeath(dualDARCallback, 0);
                } catch (Exception e2) {
                    SDPLog.e(e2, "LockSettingsService.DDAR", "Failed to unlink death listener");
                    e2.printStackTrace();
                }
                dualDARCallback.mCallback = null;
                break;
        }
    }
}

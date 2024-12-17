package com.android.server.biometrics.sensors.fingerprint;

import android.os.Handler;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpAodController {
    ISemFingerprintAodController mAodController;
    final Handler mH;
    public boolean mIsDozeMode;
    public boolean mIsHlpmMode;
    final ArrayList mPendingRequestBeforeListener = new ArrayList();

    public SemFpAodController(Handler handler) {
        this.mH = handler;
    }
}

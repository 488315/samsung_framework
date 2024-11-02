package com.android.systemui.util;

import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda1(MemoryMonitor memoryMonitor, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MemoryMonitor memoryMonitor = this.f$0;
                boolean z = this.f$1;
                memoryMonitor.getClass();
                new Thread(new MemoryMonitor$$ExternalSyntheticLambda1(memoryMonitor, z, 1), "MemoryMonitor").start();
                return;
            default:
                MemoryMonitor memoryMonitor2 = this.f$0;
                memoryMonitor2.printMemoryInfo(this.f$1);
                memoryMonitor2.mIsInCalcMemInfo = false;
                memoryMonitor2.mLastMemoryInfoLogTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
                memoryMonitor2.mLastMemoryInfoCalcTime = Long.valueOf(SystemClock.elapsedRealtime());
                return;
        }
    }
}

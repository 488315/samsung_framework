package com.android.systemui.uithreadmonitor;

import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.log.SamsungServiceLogger;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BinderCallMonitorImpl implements BinderCallMonitor {
    public static int sSkipCallCount = -1;
    public SamsungServiceLogger mLogger;
    public final SparseArray mMonitorInfo = new SparseArray();
    public long mDuration = 1;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Item {
        public final long compareDuration;
        public String stackTrace;
        public final long startTime;

        public /* synthetic */ Item(long j, int i) {
            this(j);
        }

        private Item(long j) {
            this.compareDuration = j;
            this.startTime = System.nanoTime();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class MonitorInfo {
        public long duration;
        public boolean enabled;
        public boolean infinite;
        public long timeOut;

        public /* synthetic */ MonitorInfo(int i) {
            this();
        }

        private MonitorInfo() {
            this.enabled = false;
            this.infinite = false;
        }
    }

    public final boolean startMonitoring(int i, long j, long j2) {
        int i2 = 0;
        if (i >= 0 && i < 6 && j >= 1 && j2 >= 3000 && j2 <= 8000) {
            synchronized (this.mMonitorInfo) {
                MonitorInfo monitorInfo = (MonitorInfo) this.mMonitorInfo.get(i);
                if (monitorInfo == null) {
                    monitorInfo = new MonitorInfo(i2);
                }
                monitorInfo.duration = j * 1000000;
                if (i == 0) {
                    monitorInfo.infinite = true;
                } else {
                    monitorInfo.timeOut = System.currentTimeMillis() + j2;
                }
                monitorInfo.enabled = true;
                this.mMonitorInfo.put(i, monitorInfo);
            }
            return true;
        }
        Log.w("BinderCallMonitor", "not monitoring started");
        return false;
    }

    public final boolean startMonitoring$1(int i) {
        return startMonitoring(i, BinderCallMonitorConstants.MAX_DURATION / 1000000, 8000L);
    }
}

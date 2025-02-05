package com.android.internal.os;

import android.util.Slog;
import com.android.modules.expresslog.Counter;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class KernelSingleProcessCpuThreadReader {
    private static final boolean DEBUG = false;
    private static final String TAG = "KernelSingleProcCpuThreadRdr";
    private final CpuTimeInStateReader mCpuTimeInStateReader;
    private int mFrequencyCount;
    private boolean mIsTracking;
    private final int mPid;
    private int[] mSelectedThreadNativeTids = new int[0];

    public interface CpuTimeInStateReader {
        String[] getAggregatedTaskCpuFreqTimes(int i);

        int getCpuFrequencyCount();

        boolean startAggregatingTaskCpuTimes(int i, int i2);

        boolean startTrackingProcessCpuTimes(int i);
    }

    private native int getCpuFrequencyCount(CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean readProcessCpuUsage(int i, long[] jArr, long[] jArr2, CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startAggregatingThreadCpuTimes(int[] iArr, CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startTrackingProcessCpuTimes(int i, CpuTimeInStateReader cpuTimeInStateReader);

    public KernelSingleProcessCpuThreadReader(int pid, CpuTimeInStateReader cpuTimeInStateReader) throws IOException {
        this.mPid = pid;
        this.mCpuTimeInStateReader = cpuTimeInStateReader;
    }

    public static KernelSingleProcessCpuThreadReader create(int pid) {
        try {
            return new KernelSingleProcessCpuThreadReader(pid, null);
        } catch (IOException e) {
            Slog.e(TAG, "Failed to initialize KernelSingleProcessCpuThreadReader", e);
            return null;
        }
    }

    public void startTrackingThreadCpuTimes() {
        if (!this.mIsTracking) {
            if (!startTrackingProcessCpuTimes(this.mPid, this.mCpuTimeInStateReader)) {
                Slog.wtf(TAG, "Failed to start tracking process CPU times for " + this.mPid);
                Counter.logIncrement("cpu.value_process_tracking_start_failure_count");
            }
            if (this.mSelectedThreadNativeTids.length > 0 && !startAggregatingThreadCpuTimes(this.mSelectedThreadNativeTids, this.mCpuTimeInStateReader)) {
                Slog.wtf(TAG, "Failed to start tracking aggregated thread CPU times for " + Arrays.toString(this.mSelectedThreadNativeTids));
                Counter.logIncrement("cpu.value_aggregated_thread_tracking_start_failure_count");
            }
            this.mIsTracking = true;
        }
    }

    public void setSelectedThreadIds(int[] nativeTids) {
        this.mSelectedThreadNativeTids = (int[]) nativeTids.clone();
        if (this.mIsTracking) {
            startAggregatingThreadCpuTimes(this.mSelectedThreadNativeTids, this.mCpuTimeInStateReader);
        }
    }

    public int getCpuFrequencyCount() {
        if (this.mFrequencyCount == 0) {
            this.mFrequencyCount = getCpuFrequencyCount(this.mCpuTimeInStateReader);
        }
        return this.mFrequencyCount;
    }

    public ProcessCpuUsage getProcessCpuUsage() {
        ProcessCpuUsage processCpuUsage = new ProcessCpuUsage(getCpuFrequencyCount());
        boolean result = readProcessCpuUsage(this.mPid, processCpuUsage.threadCpuTimesMillis, processCpuUsage.selectedThreadCpuTimesMillis, this.mCpuTimeInStateReader);
        if (!result) {
            return null;
        }
        return processCpuUsage;
    }

    public static class ProcessCpuUsage {
        public long[] selectedThreadCpuTimesMillis;
        public long[] threadCpuTimesMillis;

        public ProcessCpuUsage(int cpuFrequencyCount) {
            this.threadCpuTimesMillis = new long[cpuFrequencyCount];
            this.selectedThreadCpuTimesMillis = new long[cpuFrequencyCount];
        }
    }
}

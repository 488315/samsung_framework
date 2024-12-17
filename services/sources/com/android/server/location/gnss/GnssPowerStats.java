package com.android.server.location.gnss;

import android.location.GnssCapabilities;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssPowerStats {
    public final int mElapsedRealtimeFlags;
    public final long mElapsedRealtimeNanos;
    public final double mElapsedRealtimeUncertaintyNanos;
    public final double mMultibandAcquisitionModeEnergyMilliJoule;
    public final double mMultibandTrackingModeEnergyMilliJoule;
    public final double[] mOtherModesEnergyMilliJoule;
    public final double mSinglebandAcquisitionModeEnergyMilliJoule;
    public final double mSinglebandTrackingModeEnergyMilliJoule;
    public final double mTotalEnergyMilliJoule;

    public GnssPowerStats(int i, long j, double d, double d2, double d3, double d4, double d5, double d6, double[] dArr) {
        this.mElapsedRealtimeFlags = i;
        this.mElapsedRealtimeNanos = j;
        this.mElapsedRealtimeUncertaintyNanos = d;
        this.mTotalEnergyMilliJoule = d2;
        this.mSinglebandTrackingModeEnergyMilliJoule = d3;
        this.mMultibandTrackingModeEnergyMilliJoule = d4;
        this.mSinglebandAcquisitionModeEnergyMilliJoule = d5;
        this.mMultibandAcquisitionModeEnergyMilliJoule = d6;
        this.mOtherModesEnergyMilliJoule = dArr;
    }

    public final void dump(FileDescriptor fileDescriptor, IndentingPrintWriter indentingPrintWriter, String[] strArr, GnssCapabilities gnssCapabilities) {
        if (hasElapsedRealtimeNanos()) {
            indentingPrintWriter.print("time: ");
            TimeUnit timeUnit = TimeUnit.NANOSECONDS;
            indentingPrintWriter.print(TimeUtils.formatRealtime(timeUnit.toMillis(this.mElapsedRealtimeNanos)));
            if (hasElapsedRealtimeUncertaintyNanos() && this.mElapsedRealtimeUncertaintyNanos != 0.0d) {
                indentingPrintWriter.print(" +/- ");
                indentingPrintWriter.print(timeUnit.toMillis((long) this.mElapsedRealtimeUncertaintyNanos));
            }
        }
        if (gnssCapabilities.hasPowerTotal()) {
            indentingPrintWriter.print("total power: ");
            indentingPrintWriter.print(this.mTotalEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerSinglebandTracking()) {
            indentingPrintWriter.print("single-band tracking power: ");
            indentingPrintWriter.print(this.mSinglebandTrackingModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerMultibandTracking()) {
            indentingPrintWriter.print("multi-band tracking power: ");
            indentingPrintWriter.print(this.mMultibandTrackingModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerSinglebandAcquisition()) {
            indentingPrintWriter.print("single-band acquisition power: ");
            indentingPrintWriter.print(this.mSinglebandAcquisitionModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerMultibandAcquisition()) {
            indentingPrintWriter.print("multi-band acquisition power: ");
            indentingPrintWriter.print(this.mMultibandAcquisitionModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerOtherModes()) {
            for (int i = 0; i < this.mOtherModesEnergyMilliJoule.length; i++) {
                indentingPrintWriter.print("other mode [" + i + "] power: ");
                indentingPrintWriter.print(this.mOtherModesEnergyMilliJoule[i]);
                indentingPrintWriter.println("mJ");
            }
        }
    }

    public final long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public final double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNanos;
    }

    public final double getMultibandAcquisitionModeEnergyMilliJoule() {
        return this.mMultibandAcquisitionModeEnergyMilliJoule;
    }

    public final double getMultibandTrackingModeEnergyMilliJoule() {
        return this.mMultibandTrackingModeEnergyMilliJoule;
    }

    public final double[] getOtherModesEnergyMilliJoule() {
        return this.mOtherModesEnergyMilliJoule;
    }

    public final double getSinglebandAcquisitionModeEnergyMilliJoule() {
        return this.mSinglebandAcquisitionModeEnergyMilliJoule;
    }

    public final double getSinglebandTrackingModeEnergyMilliJoule() {
        return this.mSinglebandTrackingModeEnergyMilliJoule;
    }

    public final double getTotalEnergyMilliJoule() {
        return this.mTotalEnergyMilliJoule;
    }

    public final boolean hasElapsedRealtimeNanos() {
        return (this.mElapsedRealtimeFlags & 1) != 0;
    }

    public final boolean hasElapsedRealtimeUncertaintyNanos() {
        return (this.mElapsedRealtimeFlags & 2) != 0;
    }

    public final void validate() {
        Preconditions.checkArgument(hasElapsedRealtimeNanos());
    }
}

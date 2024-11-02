package com.android.systemui.power;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BatteryStateSnapshot {
    public final long averageTimeToDischargeMillis;
    public final int batteryLevel;
    public final int batteryStatus;
    public final int bucket;
    public final boolean isBasedOnUsage;
    public final boolean isHybrid;
    public final boolean isLowWarningEnabled;
    public final boolean isPowerSaver;
    public final int lowLevelThreshold;
    public final long lowThresholdMillis;
    public final boolean plugged;
    public final int severeLevelThreshold;
    public final long severeThresholdMillis;
    public final long timeRemainingMillis;

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, long j, long j2, long j3, long j4, boolean z3, boolean z4) {
        this.batteryLevel = i;
        this.isPowerSaver = z;
        this.plugged = z2;
        this.bucket = i2;
        this.batteryStatus = i3;
        this.severeLevelThreshold = i4;
        this.lowLevelThreshold = i5;
        this.timeRemainingMillis = j;
        this.averageTimeToDischargeMillis = j2;
        this.severeThresholdMillis = j3;
        this.lowThresholdMillis = j4;
        this.isBasedOnUsage = z3;
        this.isLowWarningEnabled = z4;
        this.isHybrid = true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryStateSnapshot)) {
            return false;
        }
        BatteryStateSnapshot batteryStateSnapshot = (BatteryStateSnapshot) obj;
        if (this.batteryLevel == batteryStateSnapshot.batteryLevel && this.isPowerSaver == batteryStateSnapshot.isPowerSaver && this.plugged == batteryStateSnapshot.plugged && this.bucket == batteryStateSnapshot.bucket && this.batteryStatus == batteryStateSnapshot.batteryStatus && this.severeLevelThreshold == batteryStateSnapshot.severeLevelThreshold && this.lowLevelThreshold == batteryStateSnapshot.lowLevelThreshold && this.timeRemainingMillis == batteryStateSnapshot.timeRemainingMillis && this.averageTimeToDischargeMillis == batteryStateSnapshot.averageTimeToDischargeMillis && this.severeThresholdMillis == batteryStateSnapshot.severeThresholdMillis && this.lowThresholdMillis == batteryStateSnapshot.lowThresholdMillis && this.isBasedOnUsage == batteryStateSnapshot.isBasedOnUsage && this.isLowWarningEnabled == batteryStateSnapshot.isLowWarningEnabled) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Integer.hashCode(this.batteryLevel) * 31;
        int i = 1;
        boolean z = this.isPowerSaver;
        int i2 = z;
        if (z != 0) {
            i2 = 1;
        }
        int i3 = (hashCode + i2) * 31;
        boolean z2 = this.plugged;
        int i4 = z2;
        if (z2 != 0) {
            i4 = 1;
        }
        int m = TraceMetadata$$ExternalSyntheticOutline0.m(this.lowThresholdMillis, TraceMetadata$$ExternalSyntheticOutline0.m(this.severeThresholdMillis, TraceMetadata$$ExternalSyntheticOutline0.m(this.averageTimeToDischargeMillis, TraceMetadata$$ExternalSyntheticOutline0.m(this.timeRemainingMillis, AppInfoViewData$$ExternalSyntheticOutline0.m(this.lowLevelThreshold, AppInfoViewData$$ExternalSyntheticOutline0.m(this.severeLevelThreshold, AppInfoViewData$$ExternalSyntheticOutline0.m(this.batteryStatus, AppInfoViewData$$ExternalSyntheticOutline0.m(this.bucket, (i3 + i4) * 31, 31), 31), 31), 31), 31), 31), 31), 31);
        boolean z3 = this.isBasedOnUsage;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (m + i5) * 31;
        boolean z4 = this.isLowWarningEnabled;
        if (!z4) {
            i = z4 ? 1 : 0;
        }
        return i6 + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryStateSnapshot(batteryLevel=");
        sb.append(this.batteryLevel);
        sb.append(", isPowerSaver=");
        sb.append(this.isPowerSaver);
        sb.append(", plugged=");
        sb.append(this.plugged);
        sb.append(", bucket=");
        sb.append(this.bucket);
        sb.append(", batteryStatus=");
        sb.append(this.batteryStatus);
        sb.append(", severeLevelThreshold=");
        sb.append(this.severeLevelThreshold);
        sb.append(", lowLevelThreshold=");
        sb.append(this.lowLevelThreshold);
        sb.append(", timeRemainingMillis=");
        sb.append(this.timeRemainingMillis);
        sb.append(", averageTimeToDischargeMillis=");
        sb.append(this.averageTimeToDischargeMillis);
        sb.append(", severeThresholdMillis=");
        sb.append(this.severeThresholdMillis);
        sb.append(", lowThresholdMillis=");
        sb.append(this.lowThresholdMillis);
        sb.append(", isBasedOnUsage=");
        sb.append(this.isBasedOnUsage);
        sb.append(", isLowWarningEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isLowWarningEnabled, ")");
    }

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5) {
        this(i, z, z2, i2, i3, i4, i5, -1L, -1L, -1L, -1L, false, true);
        this.isHybrid = false;
    }
}

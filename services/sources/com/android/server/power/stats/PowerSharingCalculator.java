package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerSharingCalculator extends PowerCalculator {
    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(BatteryUsageStats.Builder builder, BatteryStats batteryStats, long j, long j2, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long txPowerSharingTime = batteryStats.getTxPowerSharingTime(j, 0) / 1000;
        double txSharingDischargeAmount = batteryStats.getTxSharingDischargeAmount();
        if (txSharingDischargeAmount != 0.0d) {
            builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(18, txSharingDischargeAmount, 1).setUsageDurationMillis(18, txPowerSharingTime);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 18;
    }
}

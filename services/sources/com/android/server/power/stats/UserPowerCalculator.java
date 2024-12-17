package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.os.UserHandle;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserPowerCalculator extends PowerCalculator {
    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(BatteryUsageStats.Builder builder, BatteryStats batteryStats, long j, long j2, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        int[] userIds = batteryUsageStatsQuery.getUserIds();
        if (ArrayUtils.contains(userIds, -1)) {
            return;
        }
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder2 = (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (!builder2.isVirtualUid()) {
                int uid = builder2.getUid();
                if (UserHandle.getAppId(uid) >= 10000) {
                    int userId = UserHandle.getUserId(uid);
                    if (!ArrayUtils.contains(userIds, userId)) {
                        builder2.excludeFromBatteryUsageStats();
                        builder.getOrCreateUserBatteryConsumerBuilder(userId).addUidBatteryConsumer(builder2);
                    }
                }
            }
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return true;
    }
}

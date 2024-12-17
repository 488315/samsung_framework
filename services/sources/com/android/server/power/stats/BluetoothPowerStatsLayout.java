package com.android.server.power.stats;

import android.os.PersistableBundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BluetoothPowerStatsLayout extends PowerStatsLayout {
    public int mDeviceIdleTimePosition;
    public int mDeviceRxTimePosition;
    public int mDeviceScanTimePosition;
    public int mDeviceTxTimePosition;
    public int mUidRxBytesPosition;
    public int mUidScanTimePosition;
    public int mUidTxBytesPosition;

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void fromExtras(PersistableBundle persistableBundle) {
        super.fromExtras(persistableBundle);
        this.mDeviceRxTimePosition = persistableBundle.getInt("dt-rx");
        this.mDeviceTxTimePosition = persistableBundle.getInt("dt-tx");
        this.mDeviceIdleTimePosition = persistableBundle.getInt("dt-idle");
        this.mDeviceScanTimePosition = persistableBundle.getInt("dt-scan");
        this.mUidRxBytesPosition = persistableBundle.getInt("ub-rx");
        this.mUidTxBytesPosition = persistableBundle.getInt("ub-tx");
        this.mUidScanTimePosition = persistableBundle.getInt("ut-scan");
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void toExtras(PersistableBundle persistableBundle) {
        super.toExtras(persistableBundle);
        persistableBundle.putInt("dt-rx", this.mDeviceRxTimePosition);
        persistableBundle.putInt("dt-tx", this.mDeviceTxTimePosition);
        persistableBundle.putInt("dt-idle", this.mDeviceIdleTimePosition);
        persistableBundle.putInt("dt-scan", this.mDeviceScanTimePosition);
        persistableBundle.putInt("ub-rx", this.mUidRxBytesPosition);
        persistableBundle.putInt("ub-tx", this.mUidTxBytesPosition);
        persistableBundle.putInt("ut-scan", this.mUidScanTimePosition);
    }
}

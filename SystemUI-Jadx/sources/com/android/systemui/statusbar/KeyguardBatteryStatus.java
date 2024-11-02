package com.android.systemui.statusbar;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.settingslib.fuelgauge.BatteryStatus;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class KeyguardBatteryStatus extends BatteryStatus {
    public final boolean highVoltage;
    public final int mSuperFastCharger;
    public final int online;
    public final boolean protectedFully;
    public long remaining;
    public final int swellingMode;

    public KeyguardBatteryStatus(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        this(i, i2, i3, i4, i5, i6, z, 0, 0, z2);
    }

    public final int getChargingType() {
        int i = this.mSuperFastCharger;
        if (i == 3) {
            return 14;
        }
        if (i == 4) {
            return 15;
        }
        if (this.highVoltage) {
            return 11;
        }
        if (this.plugged == 4) {
            if (this.online == 100) {
                return 13;
            }
            return 12;
        }
        return 10;
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final boolean isPluggedIn() {
        int i = this.plugged;
        if (i == 1 || i == 2 || i == 8 || i == 4) {
            return true;
        }
        return false;
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryStatus{status=");
        sb.append(this.status);
        sb.append(",level=");
        sb.append(this.level);
        sb.append(",plugged=");
        sb.append(this.plugged);
        sb.append(",chargingStatus=");
        sb.append(this.chargingStatus);
        sb.append(",maxChargingWattage=");
        sb.append(this.maxChargingWattage);
        sb.append(",remaining=");
        sb.append(this.remaining);
        sb.append("ultraFastCharger=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.mSuperFastCharger, "}");
    }

    public KeyguardBatteryStatus(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, boolean z2) {
        super(i, i2, i3, i4, i5, false);
        this.remaining = -1L;
        this.online = i6;
        this.highVoltage = z;
        this.swellingMode = i7;
        this.mSuperFastCharger = i8;
        this.protectedFully = z2;
    }
}

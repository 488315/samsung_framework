package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.hardware.Sensor;
import com.android.systemui.R;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SensorModule_ProvideSecondaryProximitySensorFactory implements Provider {
    public final Provider thresholdSensorBuilderProvider;

    public SensorModule_ProvideSecondaryProximitySensorFactory(Provider provider) {
        this.thresholdSensorBuilderProvider = provider;
    }

    public static ThresholdSensorImpl provideSecondaryProximitySensor(ThresholdSensorImpl.Builder builder) {
        try {
            Sensor findSensorByType = builder.findSensorByType(builder.mResources.getString(R.string.proximity_sensor_secondary_type), true);
            if (findSensorByType != null) {
                builder.mSensor = findSensorByType;
                builder.mSensorSet = true;
            }
            Resources resources = builder.mResources;
            try {
                float f = resources.getFloat(R.dimen.proximity_sensor_secondary_threshold);
                builder.mThresholdValue = f;
                builder.mThresholdSet = true;
                if (!builder.mThresholdLatchValueSet) {
                    builder.mThresholdLatchValue = f;
                }
            } catch (Resources.NotFoundException unused) {
            }
            try {
                builder.mThresholdLatchValue = resources.getFloat(R.dimen.proximity_sensor_secondary_threshold_latch);
                builder.mThresholdLatchValueSet = true;
            } catch (Resources.NotFoundException unused2) {
            }
            return builder.build();
        } catch (IllegalStateException unused3) {
            builder.mSensor = null;
            builder.mSensorSet = true;
            builder.mThresholdValue = 0.0f;
            builder.mThresholdSet = true;
            if (!builder.mThresholdLatchValueSet) {
                builder.mThresholdLatchValue = 0.0f;
            }
            return builder.build();
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSecondaryProximitySensor((ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}

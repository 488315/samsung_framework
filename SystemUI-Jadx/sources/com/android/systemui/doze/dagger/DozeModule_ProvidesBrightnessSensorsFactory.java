package com.android.systemui.doze.dagger;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class DozeModule_ProvidesBrightnessSensorsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider dozeParametersProvider;
    public final Provider sensorManagerProvider;

    public DozeModule_ProvidesBrightnessSensorsFactory(Provider provider, Provider provider2, Provider provider3) {
        this.sensorManagerProvider = provider;
        this.contextProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static Optional[] providesBrightnessSensors(AsyncSensorManager asyncSensorManager, Context context, DozeParameters dozeParameters) {
        String[] stringArray = dozeParameters.mResources.getStringArray(R.array.doze_brightness_sensor_name_posture_mapping);
        if (stringArray.length != 0) {
            Optional[] optionalArr = new Optional[5];
            Arrays.fill(optionalArr, Optional.empty());
            HashMap hashMap = new HashMap();
            for (int i = 0; i < stringArray.length; i++) {
                String str = stringArray[i];
                if (!hashMap.containsKey(str)) {
                    hashMap.put(str, Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), stringArray[i])));
                }
                optionalArr[i] = (Optional) hashMap.get(str);
            }
            return optionalArr;
        }
        return new Optional[]{Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), null))};
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesBrightnessSensors((AsyncSensorManager) this.sensorManagerProvider.get(), (Context) this.contextProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}

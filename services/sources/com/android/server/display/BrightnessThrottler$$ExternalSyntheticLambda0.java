package com.android.server.display;

import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessThrottler$$ExternalSyntheticLambda0 implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        String str = (String) obj2;
        try {
            return new DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(DeviceConfigParsingUtils.parseBrightness(str), DeviceConfigParsingUtils.parseThermalStatus((String) obj));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}

package com.android.settingslib.media;

import com.android.systemui.R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class DeviceIconUtil {
    public final Map mAudioDeviceTypeToIconMap = new HashMap();
    public final Map mMediaRouteTypeToIconMap = new HashMap();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Device {
        public final int mAudioDeviceType;
        public final int mIconDrawableRes;
        public final int mMediaRouteType;

        public Device(int i, int i2, int i3) {
            this.mAudioDeviceType = i;
            this.mMediaRouteType = i2;
            this.mIconDrawableRes = i3;
        }
    }

    public DeviceIconUtil() {
        List asList = Arrays.asList(new Device(11, 11, R.drawable.ic_headphone), new Device(22, 22, R.drawable.ic_headphone), new Device(12, 12, R.drawable.ic_headphone), new Device(13, 13, R.drawable.ic_dock_device), new Device(9, 9, R.drawable.ic_headphone), new Device(3, 3, R.drawable.ic_headphone), new Device(4, 4, R.drawable.ic_headphone), new Device(2, 2, R.drawable.ic_smartphone));
        for (int i = 0; i < asList.size(); i++) {
            Device device = (Device) asList.get(i);
            this.mAudioDeviceTypeToIconMap.put(Integer.valueOf(device.mAudioDeviceType), device);
            this.mMediaRouteTypeToIconMap.put(Integer.valueOf(device.mMediaRouteType), device);
        }
    }
}

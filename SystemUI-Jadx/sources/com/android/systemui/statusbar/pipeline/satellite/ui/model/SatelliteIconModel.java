package com.android.systemui.statusbar.pipeline.satellite.ui.model;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SatelliteIconModel {
    public static final SatelliteIconModel INSTANCE = new SatelliteIconModel();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SatelliteConnectionState.values().length];
            try {
                iArr[SatelliteConnectionState.Unknown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SatelliteConnectionState.Off.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SatelliteConnectionState.On.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SatelliteConnectionState.Connected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private SatelliteIconModel() {
    }

    public static Icon.Resource fromSignalStrength(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 5) {
                                return null;
                            }
                            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_3, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
                        }
                        return new Icon.Resource(R.drawable.stat_sys_sos_satellite_3, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
                    }
                    return new Icon.Resource(R.drawable.stat_sys_sos_satellite_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
                }
                return new Icon.Resource(R.drawable.stat_sys_sos_satellite_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
            }
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_1, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
        }
        return new Icon.Resource(R.drawable.stat_sys_sos_satellite_0, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
    }
}

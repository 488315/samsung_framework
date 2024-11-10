package com.android.server.input;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.att.iqi.lib.metrics.mm.MM05;

/* loaded from: classes2.dex */
public abstract class HidKeycodes {
    public static final byte[] ScancodeToHidKeycode = {0, 41, 30, 31, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 33, 34, 35, 36, 37, 38, 39, 45, 46, 42, 43, 20, 26, 8, 21, 23, 28, 24, 12, 18, 19, 47, 48, 40, -32, 4, 22, 7, 9, 10, MM05.IQ_SIP_CALL_STATE_DISCONNECTING, 13, 14, 15, 51, 52, 53, -31, 49, 29, 27, 6, 25, 5, 17, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 54, 55, 56, -27, 85, -30, 44, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 83, 71, 95, 96, 97, 86, 92, 93, 94, 87, 89, 90, 91, 98, 99, 0, -108, 100, 68, 69, -121, 0, 0, -118, -120, -117, -123, 88, -28, 84, 70, -26, 0, 74, 82, 75, 80, 79, 77, 81, 78, 73, 76, 0, -12, -10, -11, 102, 103, 0, 72, 0, -123, -112, 0, -119, -29, -25, 118, -14, 0, 0, 0, 0, 124, 0, 125, 0, 123, 0, 118, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 102, 0, 0, 0, 0, 0, 0, 0, -23, -20, -20, -15, -13, -16, -14, 0, 0, 0, 0, 0, 107, 0, 0, 0, 0, 75, 78, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 110, 111, 0};

    public static final byte convertScancodeToHidKeycode(int i) {
        if (i >= 0 && i <= 226) {
            return ScancodeToHidKeycode[i];
        }
        if (i == 254) {
            return (byte) 104;
        }
        if (i == 701) {
            return (byte) 109;
        }
        if (i == 705) {
            return (byte) 105;
        }
        if (i == 706) {
            return (byte) 106;
        }
        if (i == 709) {
            return (byte) 112;
        }
        if (i == 710) {
            return (byte) 108;
        }
        switch (i) {
            case 757:
                return (byte) 113;
            case 758:
                return (byte) 114;
            case 759:
                return (byte) 115;
            default:
                return (byte) 0;
        }
    }
}

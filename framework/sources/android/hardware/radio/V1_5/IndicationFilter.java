package android.hardware.radio.V1_5;

import android.security.keystore.KeyProperties;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class IndicationFilter {
    public static final int ALL = -1;
    public static final int BARRING_INFO = 64;
    public static final int DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int FULL_NETWORK_STATE = 2;
    public static final int LINK_CAPACITY_ESTIMATE = 8;
    public static final int NONE = 0;
    public static final int PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int REGISTRATION_FAILURE = 32;
    public static final int SIGNAL_STRENGTH = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (o == -1) {
            return SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL;
        }
        if (o == 1) {
            return "SIGNAL_STRENGTH";
        }
        if (o == 2) {
            return "FULL_NETWORK_STATE";
        }
        if (o == 4) {
            return "DATA_CALL_DORMANCY_CHANGED";
        }
        if (o == 8) {
            return "LINK_CAPACITY_ESTIMATE";
        }
        if (o == 16) {
            return "PHYSICAL_CHANNEL_CONFIG";
        }
        if (o == 32) {
            return "REGISTRATION_FAILURE";
        }
        if (o == 64) {
            return "BARRING_INFO";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add(KeyProperties.DIGEST_NONE);
        if ((o & (-1)) == -1) {
            list.add(SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL);
            flipped = 0 | (-1);
        }
        if ((o & 1) == 1) {
            list.add("SIGNAL_STRENGTH");
            flipped |= 1;
        }
        if ((o & 2) == 2) {
            list.add("FULL_NETWORK_STATE");
            flipped |= 2;
        }
        if ((o & 4) == 4) {
            list.add("DATA_CALL_DORMANCY_CHANGED");
            flipped |= 4;
        }
        if ((o & 8) == 8) {
            list.add("LINK_CAPACITY_ESTIMATE");
            flipped |= 8;
        }
        if ((o & 16) == 16) {
            list.add("PHYSICAL_CHANNEL_CONFIG");
            flipped |= 16;
        }
        if ((o & 32) == 32) {
            list.add("REGISTRATION_FAILURE");
            flipped |= 32;
        }
        if ((o & 64) == 64) {
            list.add("BARRING_INFO");
            flipped |= 64;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}

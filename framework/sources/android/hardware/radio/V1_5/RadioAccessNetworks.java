package android.hardware.radio.V1_5;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioAccessNetworks {
    public static final int CDMA2000 = 5;
    public static final int EUTRAN = 3;
    public static final int GERAN = 1;
    public static final int NGRAN = 4;
    public static final int UNKNOWN = 0;
    public static final int UTRAN = 2;

    public static final String toString(int o) {
        if (o == 1) {
            return "GERAN";
        }
        if (o == 2) {
            return "UTRAN";
        }
        if (o == 3) {
            return "EUTRAN";
        }
        if (o == 0) {
            return "UNKNOWN";
        }
        if (o == 4) {
            return "NGRAN";
        }
        if (o == 5) {
            return "CDMA2000";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("GERAN");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("UTRAN");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("EUTRAN");
            flipped |= 3;
        }
        list.add("UNKNOWN");
        if ((o & 4) == 4) {
            list.add("NGRAN");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("CDMA2000");
            flipped |= 5;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}

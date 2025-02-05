package android.sysprop;

import android.os.SystemProperties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class HdmiProperties {
    private HdmiProperties() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static Boolean tryParseBoolean(String str) {
        char c;
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        switch (lowerCase.hashCode()) {
            case 48:
                if (lowerCase.equals("0")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (lowerCase.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer tryParseInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer tryParseUInt(String str) {
        try {
            return Integer.valueOf(Integer.parseUnsignedInt(str));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long tryParseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long tryParseULong(String str) {
        try {
            return Long.valueOf(Long.parseUnsignedLong(str));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String tryParseString(String str) {
        if ("".equals(str)) {
            return null;
        }
        return str;
    }

    private static <T extends Enum<T>> T tryParseEnum(Class<T> cls, String str) {
        try {
            return (T) Enum.valueOf(cls, str.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> elementParser, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        List<T> ret = new ArrayList<>();
        int p = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            while (p < str.length() && str.charAt(p) != ',') {
                if (str.charAt(p) == '\\') {
                    p++;
                }
                if (p == str.length()) {
                    break;
                }
                sb.append(str.charAt(p));
                p++;
            }
            ret.add(elementParser.apply(sb.toString()));
            if (p == str.length()) {
                return ret;
            }
            p++;
        }
    }

    private static <T extends Enum<T>> List<T> tryParseEnumList(Class<T> enumType, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (String element : str.split(",")) {
            arrayList.add(tryParseEnum(enumType, element));
        }
        return arrayList;
    }

    private static String escape(String str) {
        return str.replaceAll("([\\\\,])", "\\\\$1");
    }

    private static <T> String formatList(List<T> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : escape(element.toString()));
        }
        return joiner.toString();
    }

    private static String formatUIntList(List<Integer> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer element = it.next();
            joiner.add(element == null ? "" : escape(Integer.toUnsignedString(element.intValue())));
        }
        return joiner.toString();
    }

    private static String formatULongList(List<Long> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            Long element = it.next();
            joiner.add(element == null ? "" : escape(Long.toUnsignedString(element.longValue())));
        }
        return joiner.toString();
    }

    private static <T extends Enum<T>> String formatEnumList(List<T> list, Function<T, String> elementFormatter) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : elementFormatter.apply(element));
        }
        return joiner.toString();
    }

    @Deprecated
    public static List<Integer> device_type() {
        String value = SystemProperties.get("ro.hdmi.device_type");
        return tryParseList(new Function() { // from class: android.sysprop.HdmiProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseInteger;
                tryParseInteger = HdmiProperties.tryParseInteger((String) obj);
                return tryParseInteger;
            }
        }, value);
    }

    public enum cec_device_types_values {
        TV("tv"),
        RECORDING_DEVICE("recording_device"),
        RESERVED("reserved"),
        TUNER("tuner"),
        PLAYBACK_DEVICE("playback_device"),
        AUDIO_SYSTEM("audio_system"),
        PURE_CEC_SWITCH("pure_cec_switch"),
        VIDEO_PROCESSOR("video_processor");

        private final String propValue;

        cec_device_types_values(String propValue) {
            this.propValue = propValue;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static List<cec_device_types_values> cec_device_types() {
        String value = SystemProperties.get("ro.hdmi.cec_device_types");
        return tryParseEnumList(cec_device_types_values.class, value);
    }

    public static Optional<String> arc_port() {
        String value = SystemProperties.get("ro.hdmi.property_sytem_audio_device_arc_port");
        return Optional.ofNullable(tryParseString(value));
    }

    public static Optional<Boolean> forward_volume_keys_when_system_audio_mode_off() {
        String value = SystemProperties.get("ro.hdmi.cec_audio_device_forward_volume_keys_system_audio_mode_off");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static Optional<Boolean> is_switch() {
        String value = SystemProperties.get("ro.hdmi.property_is_device_hdmi_cec_switch");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public enum playback_device_action_on_routing_control_values {
        NONE("none"),
        WAKE_UP_ONLY("wake_up_only"),
        WAKE_UP_AND_SEND_ACTIVE_SOURCE("wake_up_and_send_active_source");

        private final String propValue;

        playback_device_action_on_routing_control_values(String propValue) {
            this.propValue = propValue;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<playback_device_action_on_routing_control_values> playback_device_action_on_routing_control() {
        String value = SystemProperties.get("ro.hdmi.cec.source.playback_device_action_on_routing_control");
        return Optional.ofNullable((playback_device_action_on_routing_control_values) tryParseEnum(playback_device_action_on_routing_control_values.class, value));
    }
}

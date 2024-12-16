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
public final class CrashRecoveryProperties {
    private CrashRecoveryProperties() {
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

    private static Integer tryParseInteger(String str) {
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

    public static Optional<Long> lastFactoryResetTimeMs() {
        String value = SystemProperties.get("persist.crashrecovery.last_factory_reset");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void lastFactoryResetTimeMs(Long value) {
        SystemProperties.set("persist.crashrecovery.last_factory_reset", value == null ? "" : value.toString());
    }

    public static Optional<Long> rescueBootStart() {
        String value = SystemProperties.get("crashrecovery.rescue_boot_start");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void rescueBootStart(Long value) {
        SystemProperties.set("crashrecovery.rescue_boot_start", value == null ? "" : value.toString());
    }

    public static Optional<Integer> rescueBootCount() {
        String value = SystemProperties.get("crashrecovery.rescue_boot_count");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void rescueBootCount(Integer value) {
        SystemProperties.set("crashrecovery.rescue_boot_count", value == null ? "" : value.toString());
    }

    public static Optional<Long> bootMitigationStart() {
        String value = SystemProperties.get("crashrecovery.boot_mitigation_start");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void bootMitigationStart(Long value) {
        SystemProperties.set("crashrecovery.boot_mitigation_start", value == null ? "" : value.toString());
    }

    public static Optional<Integer> bootMitigationCount() {
        String value = SystemProperties.get("crashrecovery.boot_mitigation_count");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void bootMitigationCount(Integer value) {
        SystemProperties.set("crashrecovery.boot_mitigation_count", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> attemptingReboot() {
        String value = SystemProperties.get("crashrecovery.attempting_reboot");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void attemptingReboot(Boolean value) {
        SystemProperties.set("crashrecovery.attempting_reboot", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> attemptingFactoryReset() {
        String value = SystemProperties.get("crashrecovery.attempting_factory_reset");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void attemptingFactoryReset(Boolean value) {
        SystemProperties.set("crashrecovery.attempting_factory_reset", value == null ? "" : value.toString());
    }

    public static Optional<Integer> maxRescueLevelAttempted() {
        String value = SystemProperties.get("crashrecovery.max_rescue_level_attempted");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void maxRescueLevelAttempted(Integer value) {
        SystemProperties.set("crashrecovery.max_rescue_level_attempted", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> enableRescueParty() {
        String value = SystemProperties.get("persist.crashrecovery.enable_rescue");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void enableRescueParty(Boolean value) {
        SystemProperties.set("persist.crashrecovery.enable_rescue", value == null ? "" : value.toString());
    }
}

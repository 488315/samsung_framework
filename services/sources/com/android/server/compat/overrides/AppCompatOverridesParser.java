package com.android.server.compat.overrides;

import android.app.compat.PackageOverride;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.KeyValueListParser;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class AppCompatOverridesParser {
    public static final Pattern BOOLEAN_PATTERN = Pattern.compile("true|false", 2);
    public final PackageManager mPackageManager;

    public AppCompatOverridesParser(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    public Map parseRemoveOverrides(String str, Set set) {
        if (str.isEmpty()) {
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap();
        if (str.equals("*")) {
            if (set.isEmpty()) {
                Slog.w("AppCompatOverridesParser", "Wildcard can't be used in 'remove_overrides' flag with an empty owned_change_ids' flag");
                return Collections.emptyMap();
            }
            Iterator<ApplicationInfo> it = this.mPackageManager.getInstalledApplications(4194304).iterator();
            while (it.hasNext()) {
                arrayMap.put(it.next().packageName, set);
            }
            return arrayMap;
        }
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            for (int i = 0; i < keyValueListParser.size(); i++) {
                String keyAt = keyValueListParser.keyAt(i);
                String string = keyValueListParser.getString(keyAt, "");
                if (string.equals("*")) {
                    if (set.isEmpty()) {
                        Slog.w("AppCompatOverridesParser", "Wildcard can't be used in 'remove_overrides' flag with an empty owned_change_ids' flag");
                    } else {
                        arrayMap.put(keyAt, set);
                    }
                } else {
                    for (String str2 : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                        try {
                            ((Set) arrayMap.computeIfAbsent(keyAt, new Function() { // from class: com.android.server.compat.overrides.AppCompatOverridesParser$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    Set lambda$parseRemoveOverrides$0;
                                    lambda$parseRemoveOverrides$0 = AppCompatOverridesParser.lambda$parseRemoveOverrides$0((String) obj);
                                    return lambda$parseRemoveOverrides$0;
                                }
                            })).add(Long.valueOf(Long.parseLong(str2)));
                        } catch (NumberFormatException e) {
                            Slog.w("AppCompatOverridesParser", "Invalid change ID in 'remove_overrides' flag: " + str2, e);
                        }
                    }
                }
            }
            return arrayMap;
        } catch (IllegalArgumentException e2) {
            Slog.w("AppCompatOverridesParser", "Invalid format in 'remove_overrides' flag: " + str, e2);
            return Collections.emptyMap();
        }
    }

    public static /* synthetic */ Set lambda$parseRemoveOverrides$0(String str) {
        return new ArraySet();
    }

    public static Set parseOwnedChangeIds(String str) {
        if (str.isEmpty()) {
            return Collections.emptySet();
        }
        ArraySet arraySet = new ArraySet();
        for (String str2 : str.split(",")) {
            try {
                arraySet.add(Long.valueOf(Long.parseLong(str2)));
            } catch (NumberFormatException e) {
                Slog.w("AppCompatOverridesParser", "Invalid change ID in 'owned_change_ids' flag: " + str2, e);
            }
        }
        return arraySet;
    }

    public Map parsePackageOverrides(String str, String str2, long j, Set set) {
        if (str.isEmpty()) {
            return Collections.emptyMap();
        }
        PackageOverrideComparator packageOverrideComparator = new PackageOverrideComparator(j);
        ArrayMap arrayMap = new ArrayMap();
        Pair extractSignatureFromConfig = extractSignatureFromConfig(str);
        if (extractSignatureFromConfig == null) {
            return Collections.emptyMap();
        }
        String str3 = (String) extractSignatureFromConfig.first;
        String str4 = (String) extractSignatureFromConfig.second;
        if (!verifySignature(str2, str3)) {
            return Collections.emptyMap();
        }
        for (String str5 : str4.split(",")) {
            List asList = Arrays.asList(str5.split(XmlUtils.STRING_ARRAY_SEPARATOR, 4));
            if (asList.size() != 4) {
                Slog.w("AppCompatOverridesParser", "Invalid change override entry: " + str5);
            } else {
                try {
                    long parseLong = Long.parseLong((String) asList.get(0));
                    if (!set.contains(Long.valueOf(parseLong))) {
                        String str6 = (String) asList.get(1);
                        String str7 = (String) asList.get(2);
                        String str8 = (String) asList.get(3);
                        if (BOOLEAN_PATTERN.matcher(str8).matches()) {
                            PackageOverride.Builder enabled = new PackageOverride.Builder().setEnabled(Boolean.parseBoolean(str8));
                            try {
                                if (!str6.isEmpty()) {
                                    enabled.setMinVersionCode(Long.parseLong(str6));
                                }
                                if (!str7.isEmpty()) {
                                    enabled.setMaxVersionCode(Long.parseLong(str7));
                                }
                                try {
                                    PackageOverride build = enabled.build();
                                    if (!arrayMap.containsKey(Long.valueOf(parseLong)) || packageOverrideComparator.compare(build, (PackageOverride) arrayMap.get(Long.valueOf(parseLong))) < 0) {
                                        arrayMap.put(Long.valueOf(parseLong), build);
                                    }
                                } catch (IllegalArgumentException e) {
                                    Slog.w("AppCompatOverridesParser", "Failed to build PackageOverride", e);
                                }
                            } catch (NumberFormatException e2) {
                                Slog.w("AppCompatOverridesParser", "Invalid min/max version code in override entry: " + str5, e2);
                            }
                        } else {
                            Slog.w("AppCompatOverridesParser", "Invalid enabled string in override entry: " + str5);
                        }
                    }
                } catch (NumberFormatException e3) {
                    Slog.w("AppCompatOverridesParser", "Invalid change ID in override entry: " + str5, e3);
                }
            }
        }
        return arrayMap;
    }

    public static Pair extractSignatureFromConfig(String str) {
        List asList = Arrays.asList(str.split("~"));
        if (asList.size() == 1) {
            return Pair.create("", str);
        }
        if (asList.size() > 2) {
            Slog.w("AppCompatOverridesParser", "Only one signature per config is supported. Config: " + str);
            return null;
        }
        return Pair.create((String) asList.get(0), (String) asList.get(1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001a, code lost:
    
        android.util.Slog.w("AppCompatOverridesParser", r5 + " did not have expected signature: " + r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean verifySignature(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = "AppCompatOverridesParser"
            r1 = 0
            boolean r2 = r6.isEmpty()     // Catch: java.lang.IllegalArgumentException -> L32
            r3 = 1
            if (r2 != 0) goto L18
            android.content.pm.PackageManager r4 = r4.mPackageManager     // Catch: java.lang.IllegalArgumentException -> L32
            byte[] r2 = libcore.util.HexEncoding.decode(r6)     // Catch: java.lang.IllegalArgumentException -> L32
            boolean r4 = r4.hasSigningCertificate(r5, r2, r3)     // Catch: java.lang.IllegalArgumentException -> L32
            if (r4 == 0) goto L17
            goto L18
        L17:
            r3 = r1
        L18:
            if (r3 != 0) goto L31
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.IllegalArgumentException -> L32
            r4.<init>()     // Catch: java.lang.IllegalArgumentException -> L32
            r4.append(r5)     // Catch: java.lang.IllegalArgumentException -> L32
            java.lang.String r2 = " did not have expected signature: "
            r4.append(r2)     // Catch: java.lang.IllegalArgumentException -> L32
            r4.append(r6)     // Catch: java.lang.IllegalArgumentException -> L32
            java.lang.String r4 = r4.toString()     // Catch: java.lang.IllegalArgumentException -> L32
            android.util.Slog.w(r0, r4)     // Catch: java.lang.IllegalArgumentException -> L32
        L31:
            return r3
        L32:
            r4 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unable to verify signature "
            r2.append(r3)
            r2.append(r6)
            java.lang.String r6 = " for "
            r2.append(r6)
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            android.util.Slog.w(r0, r5, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.compat.overrides.AppCompatOverridesParser.verifySignature(java.lang.String, java.lang.String):boolean");
    }

    /* loaded from: classes.dex */
    public final class PackageOverrideComparator implements Comparator {
        public final long mVersionCode;

        public PackageOverrideComparator(long j) {
            this.mVersionCode = j;
        }

        @Override // java.util.Comparator
        public int compare(PackageOverride packageOverride, PackageOverride packageOverride2) {
            boolean isVersionInRange = isVersionInRange(packageOverride, this.mVersionCode);
            if (isVersionInRange != isVersionInRange(packageOverride2, this.mVersionCode)) {
                return isVersionInRange ? -1 : 1;
            }
            boolean isVersionAfterRange = isVersionAfterRange(packageOverride, this.mVersionCode);
            if (isVersionAfterRange != isVersionAfterRange(packageOverride2, this.mVersionCode)) {
                return isVersionAfterRange ? -1 : 1;
            }
            return Long.compare(getVersionProximity(packageOverride, this.mVersionCode), getVersionProximity(packageOverride2, this.mVersionCode));
        }

        public static boolean isVersionInRange(PackageOverride packageOverride, long j) {
            return packageOverride.getMinVersionCode() <= j && j <= packageOverride.getMaxVersionCode();
        }

        public static boolean isVersionAfterRange(PackageOverride packageOverride, long j) {
            return packageOverride.getMaxVersionCode() < j;
        }

        public static boolean isVersionBeforeRange(PackageOverride packageOverride, long j) {
            return packageOverride.getMinVersionCode() > j;
        }

        public static long getVersionProximity(PackageOverride packageOverride, long j) {
            if (isVersionAfterRange(packageOverride, j)) {
                return j - packageOverride.getMaxVersionCode();
            }
            if (isVersionBeforeRange(packageOverride, j)) {
                return packageOverride.getMinVersionCode() - j;
            }
            return 0L;
        }
    }
}

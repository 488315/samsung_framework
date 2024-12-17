package com.android.server.enterprise.firewall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class FirewallDefinitions {
    public static final List EXEMPT_PACKAGE_LIST;
    public static final Pattern PATTERN_NUMERAL_ENDING_SUFIX = Pattern.compile("[0-9]*$");

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Table {
        public static final /* synthetic */ Table[] $VALUES;
        public static final Table FILTER;
        public static final Table NAT;

        static {
            Table table = new Table("FILTER", 0);
            FILTER = table;
            Table table2 = new Table("NAT", 1);
            NAT = table2;
            $VALUES = new Table[]{table, table2};
        }

        public static Table valueOf(String str) {
            return (Table) Enum.valueOf(Table.class, str);
        }

        public static Table[] values() {
            return (Table[]) $VALUES.clone();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.kgclient");
        arrayList.add("com.samsung.android.knox.sandbox");
        arrayList.add("com.samsung.android.knox.dai");
        arrayList.add("com.samsung.android.knox.knnr");
        arrayList.add("com.samsung.android.knox.er");
        arrayList.add("com.samsung.android.knox.kfbp");
        EXEMPT_PACKAGE_LIST = Collections.unmodifiableList(arrayList);
    }
}

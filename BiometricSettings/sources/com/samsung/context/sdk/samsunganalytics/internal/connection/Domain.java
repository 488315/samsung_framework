package com.samsung.context.sdk.samsunganalytics.internal.connection;

import android.os.Build;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'REGISTRATION' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public final class Domain {
    private static final /* synthetic */ Domain[] $VALUES;
    public static final Domain DLS;
    public static final Domain POLICY;
    public static final Domain REGISTRATION;
    String domain;

    static {
        String str = Build.TYPE;
        Domain domain = new Domain(0, "REGISTRATION", str.equals("eng") ? "https://stg-api.di.atlas.samsung.com" : "https://regi.di.atlas.samsung.com");
        REGISTRATION = domain;
        Domain domain2 = new Domain(1, "POLICY", str.equals("eng") ? "https://stg-api.di.atlas.samsung.com" : "https://dc.di.atlas.samsung.com");
        POLICY = domain2;
        Domain domain3 = new Domain(2, "DLS", "");
        DLS = domain3;
        $VALUES = new Domain[]{domain, domain2, domain3};
    }

    private Domain(int i, String str, String str2) {
        this.domain = str2;
    }

    public static Domain valueOf(String str) {
        return (Domain) Enum.valueOf(Domain.class, str);
    }

    public static Domain[] values() {
        return (Domain[]) $VALUES.clone();
    }

    public final void setDomain(String str) {
        this.domain = str;
    }
}

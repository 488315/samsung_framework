package com.samsung.android.server.hwrs.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KsmbdConfigUtil {
    public final Map sections = new HashMap();

    public KsmbdConfigUtil() {
        int indexOf;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/data/misc/hwrs/ksmbd/ksmbd.conf"));
            String str = null;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return;
                    }
                    String trim = readLine.trim();
                    if (trim.startsWith("[")) {
                        str = trim.substring(1, trim.length() - 1).trim();
                        ((HashMap) this.sections).put(str, new HashMap());
                    } else if (!trim.isEmpty() && !trim.startsWith(";") && (indexOf = trim.indexOf(61)) != -1) {
                        ((Map) ((HashMap) this.sections).get(str)).put(trim.substring(0, indexOf).trim(), trim.substring(indexOf + 1).trim());
                    }
                } finally {
                }
            }
        } catch (IOException unused) {
            throw new StorageServiceException("loadConfFile failed!!!");
        }
    }
}

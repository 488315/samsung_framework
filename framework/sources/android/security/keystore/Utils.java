package android.security.keystore;

import java.util.Date;

/* loaded from: classes3.dex */
abstract class Utils {
    private Utils() {
    }

    static Date cloneIfNotNull(Date value) {
        if (value != null) {
            return (Date) value.clone();
        }
        return null;
    }

    static byte[] cloneIfNotNull(byte[] value) {
        if (value != null) {
            return (byte[]) value.clone();
        }
        return null;
    }

    static int[] cloneIfNotNull(int[] value) {
        if (value != null) {
            return (int[]) value.clone();
        }
        return null;
    }
}

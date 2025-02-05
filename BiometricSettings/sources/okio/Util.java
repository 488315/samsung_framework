package okio;

import java.nio.charset.Charset;

/* loaded from: classes.dex */
final class Util {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3)));
        }
    }
}

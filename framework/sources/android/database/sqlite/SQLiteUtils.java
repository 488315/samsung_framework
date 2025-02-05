package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteUtils {
    private static final String TAG = "SQLiteUtils";
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String getHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[(j * 2) + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }
}

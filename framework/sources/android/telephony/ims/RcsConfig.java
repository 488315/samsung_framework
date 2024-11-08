package android.telephony.ims;

import android.content.ContentValues;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.telephony.Rlog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes3.dex */
public final class RcsConfig {
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final boolean DBG = Build.IS_ENG;
    private static final String LOG_TAG = "RcsConfig";
    private static final String PARM_SINGLE_REGISTRATION = "rcsVolteSingleRegistration";
    private static final String TAG_CHARACTERISTIC = "characteristic";
    private static final String TAG_PARM = "parm";
    private Characteristic mCurrent;
    private final byte[] mData;
    private final Characteristic mRoot;

    /* loaded from: classes3.dex */
    public static class Characteristic {
        private final Characteristic mParent;
        private final Map<String, String> mParms;
        private final Set<Characteristic> mSubs;
        private String mType;

        /* synthetic */ Characteristic(String str, Characteristic characteristic, CharacteristicIA characteristicIA) {
            this(str, characteristic);
        }

        private Characteristic(String type, Characteristic parent) {
            this.mParms = new ArrayMap();
            this.mSubs = new ArraySet();
            this.mType = type;
            this.mParent = parent;
        }

        private String getType() {
            return this.mType;
        }

        public Map<String, String> getParms() {
            return this.mParms;
        }

        public Set<Characteristic> getSubs() {
            return this.mSubs;
        }

        public Characteristic getParent() {
            return this.mParent;
        }

        public Characteristic getSubByType(String type) {
            if (TextUtils.equals(this.mType, type)) {
                return this;
            }
            Characteristic result = null;
            for (Characteristic sub : this.mSubs) {
                result = sub.getSubByType(type);
                if (result != null) {
                    break;
                }
            }
            return result;
        }

        private boolean hasSubByType(String type) {
            return getSubByType(type) != null;
        }

        public String getParmValue(String name) {
            String value = this.mParms.get(name);
            if (value == null) {
                for (Characteristic sub : this.mSubs) {
                    value = sub.getParmValue(name);
                    if (value != null) {
                        break;
                    }
                }
            }
            return value;
        }

        boolean hasParm(String name) {
            if (this.mParms.containsKey(name)) {
                return true;
            }
            for (Characteristic sub : this.mSubs) {
                if (sub.hasParm(name)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + this.mType + "]: ");
            if (RcsConfig.DBG) {
                sb.append(this.mParms);
            }
            for (Characteristic sub : this.mSubs) {
                sb.append("\n");
                sb.append(sub.toString().replace("\n", "\n\t"));
            }
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Characteristic)) {
                return false;
            }
            Characteristic o = (Characteristic) obj;
            return TextUtils.equals(this.mType, o.mType) && this.mParms.equals(o.mParms) && this.mSubs.equals(o.mSubs);
        }

        public int hashCode() {
            return Objects.hash(this.mType, this.mParms, this.mSubs);
        }
    }

    public RcsConfig(byte[] data) throws IllegalArgumentException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        CharacteristicIA characteristicIA = null;
        Characteristic characteristic = new Characteristic(null, null);
        this.mRoot = characteristic;
        this.mCurrent = characteristic;
        this.mData = data;
        Characteristic current = this.mRoot;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, null);
                int eventType = xpp.getEventType();
                for (int i = 1; eventType != i && current != null; i = 1) {
                    if (eventType == 2) {
                        String tag = xpp.getName().trim().toLowerCase(Locale.ROOT);
                        if (TAG_CHARACTERISTIC.equals(tag)) {
                            int count = xpp.getAttributeCount();
                            String type = null;
                            if (count > 0) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= count) {
                                        break;
                                    }
                                    String name = xpp.getAttributeName(i2).trim().toLowerCase(Locale.ROOT);
                                    if (!"type".equals(name)) {
                                        i2++;
                                    } else {
                                        type = xpp.getAttributeValue(xpp.getAttributeNamespace(i2), name).trim().toLowerCase(Locale.ROOT);
                                        break;
                                    }
                                }
                            }
                            Characteristic next = new Characteristic(type, current);
                            current.getSubs().add(next);
                            current = next;
                        } else if (TAG_PARM.equals(tag)) {
                            int count2 = xpp.getAttributeCount();
                            String key = null;
                            String value = null;
                            if (count2 > 1) {
                                for (int i3 = 0; i3 < count2; i3++) {
                                    String name2 = xpp.getAttributeName(i3).trim().toLowerCase(Locale.ROOT);
                                    if ("name".equals(name2)) {
                                        key = xpp.getAttributeValue(xpp.getAttributeNamespace(i3), name2).trim().toLowerCase(Locale.ROOT);
                                    } else if ("value".equals(name2)) {
                                        value = xpp.getAttributeValue(xpp.getAttributeNamespace(i3), name2).trim();
                                    }
                                }
                            }
                            if (key != null && value != null) {
                                current.getParms().put(key, value);
                            }
                        }
                    } else if (eventType == 3) {
                        current = TAG_CHARACTERISTIC.equals(xpp.getName().trim().toLowerCase(Locale.ROOT)) ? current.getParent() : current;
                    }
                    eventType = xpp.next();
                    characteristicIA = null;
                }
                try {
                    inputStream.close();
                } catch (IOException e) {
                    loge("error to close input stream, skip.");
                }
            } catch (IOException | XmlPullParserException e2) {
                throw new IllegalArgumentException(e2);
            }
        } finally {
        }
    }

    public String getString(String tag, String defaultVal) {
        String value = this.mCurrent.getParmValue(tag.trim().toLowerCase(Locale.ROOT));
        return value != null ? value : defaultVal;
    }

    public int getInteger(String tag, int defaultVal) {
        try {
            return Integer.parseInt(getString(tag, null));
        } catch (NumberFormatException e) {
            logd("error to getInteger for " + tag + " due to " + e);
            return defaultVal;
        }
    }

    public boolean getBoolean(String tag, boolean defaultVal) {
        String value = getString(tag, null);
        return value != null ? Boolean.parseBoolean(value) : defaultVal;
    }

    public boolean hasConfig(String tag) {
        return this.mCurrent.hasParm(tag.trim().toLowerCase(Locale.ROOT));
    }

    public Characteristic getCharacteristic(String type) {
        return this.mCurrent.getSubByType(type.trim().toLowerCase(Locale.ROOT));
    }

    public boolean hasCharacteristic(String type) {
        return this.mCurrent.getSubByType(type.trim().toLowerCase(Locale.ROOT)) != null;
    }

    public void setCurrentCharacteristic(Characteristic current) {
        if (current != null) {
            this.mCurrent = current;
        }
    }

    public boolean moveToParent() {
        if (this.mCurrent.getParent() == null) {
            return false;
        }
        this.mCurrent = this.mCurrent.getParent();
        return true;
    }

    public void moveToRoot() {
        this.mCurrent = this.mRoot;
    }

    public Characteristic getRoot() {
        return this.mRoot;
    }

    public Characteristic getCurrentCharacteristic() {
        return this.mCurrent;
    }

    public boolean isRcsVolteSingleRegistrationSupported(boolean isRoaming) {
        int val = getInteger(PARM_SINGLE_REGISTRATION, 1);
        if (isRoaming) {
            if (val == 1) {
                return true;
            }
        } else if (val > 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[RCS Config]");
        if (DBG) {
            sb.append("=== Root ===\n");
            sb.append(this.mRoot);
            sb.append("=== Current ===\n");
            sb.append(this.mCurrent);
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RcsConfig)) {
            return false;
        }
        RcsConfig other = (RcsConfig) obj;
        return this.mRoot.equals(other.mRoot) && this.mCurrent.equals(other.mCurrent);
    }

    public int hashCode() {
        return Objects.hash(this.mRoot, this.mCurrent);
    }

    public static byte[] compressGzip(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }
        byte[] out = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            GZIPOutputStream gzipCompressingStream = new GZIPOutputStream(outputStream);
            gzipCompressingStream.write(data);
            gzipCompressingStream.close();
            out = outputStream.toByteArray();
            outputStream.close();
            return out;
        } catch (IOException e) {
            loge("Error to compressGzip due to " + e);
            return out;
        }
    }

    public static byte[] decompressGzip(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }
        byte[] out = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GZIPInputStream gzipDecompressingStream = new GZIPInputStream(inputStream);
            byte[] buf = new byte[1024];
            for (int size = gzipDecompressingStream.read(buf); size >= 0; size = gzipDecompressingStream.read(buf)) {
                outputStream.write(buf, 0, size);
            }
            gzipDecompressingStream.close();
            inputStream.close();
            out = outputStream.toByteArray();
            outputStream.close();
            return out;
        } catch (IOException e) {
            loge("Error to decompressGzip due to " + e);
            return out;
        }
    }

    public static void updateConfigForSub(Context cxt, int subId, byte[] config, boolean isCompressed) {
        byte[] data = isCompressed ? config : compressGzip(config);
        ContentValues values = new ContentValues();
        values.put(Telephony.SimInfo.COLUMN_RCS_CONFIG, data);
        cxt.getContentResolver().update(Telephony.SimInfo.CONTENT_URI, values, "_id=" + subId, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0070, code lost:
    
        return decompressGzip(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x005b, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0059, code lost:
    
        if (r1 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0065, code lost:
    
        if (r1 != null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0068, code lost:
    
        if (r9 == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] loadRcsConfigForSub(android.content.Context r7, int r8, boolean r9) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r7.getContentResolver()
            android.net.Uri r2 = android.provider.Telephony.SimInfo.CONTENT_URI
            r3 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "_id="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r4 = r4.toString()
            r5 = 0
            r6 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L65
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            if (r2 == 0) goto L65
            java.lang.String r2 = "rcs_config"
            int r2 = r1.getColumnIndexOrThrow(r2)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            byte[] r2 = r1.getBlob(r2)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L38
            r0 = r2
            goto L65
        L36:
            r2 = move-exception
            goto L5f
        L38:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L36
            r3.<init>()     // Catch: java.lang.Throwable -> L36
            java.lang.String r4 = "error to load rcs config for sub:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L36
            java.lang.StringBuilder r3 = r3.append(r8)     // Catch: java.lang.Throwable -> L36
            java.lang.String r4 = " due to "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L36
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch: java.lang.Throwable -> L36
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L36
            loge(r3)     // Catch: java.lang.Throwable -> L36
            if (r1 == 0) goto L68
        L5b:
            r1.close()
            goto L68
        L5f:
            if (r1 == 0) goto L64
            r1.close()
        L64:
            throw r2
        L65:
            if (r1 == 0) goto L68
            goto L5b
        L68:
            if (r9 == 0) goto L6c
            r2 = r0
            goto L70
        L6c:
            byte[] r2 = decompressGzip(r0)
        L70:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.RcsConfig.loadRcsConfigForSub(android.content.Context, int, boolean):byte[]");
    }

    private static void logd(String msg) {
        Rlog.d(LOG_TAG, msg);
    }

    private static void loge(String msg) {
        Rlog.e(LOG_TAG, msg);
    }
}

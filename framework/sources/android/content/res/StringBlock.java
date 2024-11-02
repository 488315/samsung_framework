package android.content.res;

import android.app.ActivityThread;
import android.app.Application;
import android.app.blob.XmlTags;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.TtmlUtils;
import android.provider.Telephony;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineHeightSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import com.samsung.android.util.CustomizedTextParser;
import java.io.Closeable;

/* loaded from: classes.dex */
public final class StringBlock implements Closeable {
    private static final String TAG = "AssetManager";
    private static final boolean localLOGV = false;
    private long mNative;
    private SparseArray<CharSequence> mSparseStrings;
    private CharSequence[] mStrings;
    private final boolean mUseSparse;
    private boolean mOpen = true;
    StyleIDs mStyleIDs = null;
    private final boolean mOwnsNative = false;

    private static native long nativeCreate(byte[] bArr, int i, int i2);

    private static native void nativeDestroy(long j);

    private static native int nativeGetSize(long j);

    private static native String nativeGetString(long j, int i);

    private static native int[] nativeGetStyle(long j, int i);

    public StringBlock(byte[] data, boolean useSparse) {
        this.mNative = nativeCreate(data, 0, data.length);
        this.mUseSparse = useSparse;
    }

    public StringBlock(byte[] data, int offset, int size, boolean useSparse) {
        this.mNative = nativeCreate(data, offset, size);
        this.mUseSparse = useSparse;
    }

    @Deprecated
    public CharSequence get(int idx) {
        CharSequence seq = getSequence(idx);
        return seq == null ? "" : seq;
    }

    public CharSequence getSequence(int idx) {
        synchronized (this) {
            CharSequence[] charSequenceArr = this.mStrings;
            if (charSequenceArr != null) {
                CharSequence res = charSequenceArr[idx];
                if (res != null) {
                    return res;
                }
            } else {
                SparseArray<CharSequence> sparseArray = this.mSparseStrings;
                if (sparseArray != null) {
                    CharSequence res2 = sparseArray.get(idx);
                    if (res2 != null) {
                        return res2;
                    }
                } else {
                    int num = nativeGetSize(this.mNative);
                    if (this.mUseSparse && num > 250) {
                        this.mSparseStrings = new SparseArray<>();
                    } else {
                        this.mStrings = new CharSequence[num];
                    }
                }
            }
            String str = nativeGetString(this.mNative, idx);
            if (str == null) {
                return null;
            }
            CharSequence res3 = str;
            int[] style = nativeGetStyle(this.mNative, idx);
            if (style != null) {
                if (this.mStyleIDs == null) {
                    this.mStyleIDs = new StyleIDs();
                }
                boolean hasCustomizedString = false;
                for (int styleIndex = 0; styleIndex < style.length; styleIndex += 3) {
                    int styleId = style[styleIndex];
                    if (styleId != this.mStyleIDs.boldId && styleId != this.mStyleIDs.italicId && styleId != this.mStyleIDs.underlineId && styleId != this.mStyleIDs.ttId && styleId != this.mStyleIDs.bigId && styleId != this.mStyleIDs.smallId && styleId != this.mStyleIDs.subId && styleId != this.mStyleIDs.supId && styleId != this.mStyleIDs.strikeId && styleId != this.mStyleIDs.listItemId && styleId != this.mStyleIDs.marqueeId) {
                        if (styleId == this.mStyleIDs.uniqueTextId) {
                            hasCustomizedString = true;
                        } else {
                            String styleTag = nativeGetString(this.mNative, styleId);
                            if (styleTag == null) {
                                return null;
                            }
                            if (styleTag.equals("b")) {
                                this.mStyleIDs.boldId = styleId;
                            } else if (styleTag.equals("i")) {
                                this.mStyleIDs.italicId = styleId;
                            } else if (styleTag.equals(XmlTags.ATTR_UID)) {
                                this.mStyleIDs.underlineId = styleId;
                            } else if (styleTag.equals(TtmlUtils.TAG_TT)) {
                                this.mStyleIDs.ttId = styleId;
                            } else if (styleTag.equals("big")) {
                                this.mStyleIDs.bigId = styleId;
                            } else if (styleTag.equals("small")) {
                                this.mStyleIDs.smallId = styleId;
                            } else if (styleTag.equals("sup")) {
                                this.mStyleIDs.supId = styleId;
                            } else if (styleTag.equals(Telephony.BaseMmsColumns.SUBJECT)) {
                                this.mStyleIDs.subId = styleId;
                            } else if (styleTag.equals("strike")) {
                                this.mStyleIDs.strikeId = styleId;
                            } else if (styleTag.equals("li")) {
                                this.mStyleIDs.listItemId = styleId;
                            } else if (styleTag.equals("marquee")) {
                                this.mStyleIDs.marqueeId = styleId;
                            } else if (styleTag.equals(CustomizedTextParser.REPLACE_TAG)) {
                                hasCustomizedString = true;
                                this.mStyleIDs.uniqueTextId = styleId;
                            }
                        }
                    }
                }
                res3 = applyStyles(str, style, this.mStyleIDs, hasCustomizedString);
            }
            if (res3 != null) {
                CharSequence[] charSequenceArr2 = this.mStrings;
                if (charSequenceArr2 != null) {
                    charSequenceArr2[idx] = res3;
                } else {
                    this.mSparseStrings.put(idx, res3);
                }
            }
            return res3;
        }
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            close();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                if (this.mOwnsNative) {
                    nativeDestroy(this.mNative);
                }
                this.mNative = 0L;
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class StyleIDs {
        private int boldId = -1;
        private int italicId = -1;
        private int underlineId = -1;
        private int ttId = -1;
        private int bigId = -1;
        private int smallId = -1;
        private int subId = -1;
        private int supId = -1;
        private int strikeId = -1;
        private int listItemId = -1;
        private int marqueeId = -1;
        private int uniqueTextId = -1;

        StyleIDs() {
        }
    }

    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r3v5 */
    private CharSequence applyStyles(String str, int[] style, StyleIDs ids, boolean hasCustomizedString) {
        String str2 = str;
        if (style.length == 0) {
            return str2;
        }
        ?? r3 = 1;
        if (hasCustomizedString) {
            StringBuffer sb = new StringBuffer(str2);
            for (int i = 0; i < style.length; i += 3) {
                if (style[i] == ids.uniqueTextId) {
                    String originalString = sb.substring(style[i + 1], style[i + 2] + 1);
                    String customizedString = AssetManager.getCustomizedString(originalString);
                    if (customizedString == null) {
                        customizedString = originalString;
                    }
                    sb.replace(style[i + 1], style[i + 2] + 1, customizedString);
                    int distance = customizedString.length() - originalString.length();
                    for (int j = 0; j < style.length; j += 3) {
                        if (i != j) {
                            if (style[j + 1] >= style[i + 2]) {
                                int i2 = j + 1;
                                style[i2] = style[i2] + distance;
                            }
                            if (style[j + 2] >= style[i + 2]) {
                                int i3 = j + 2;
                                style[i3] = style[i3] + distance;
                            }
                        }
                    }
                }
            }
            str2 = sb.toString();
        }
        SpannableString buffer = new SpannableString(str2);
        int i4 = 0;
        while (i4 < style.length) {
            int type = style[i4];
            if (type == ids.boldId) {
                Application application = ActivityThread.currentApplication();
                int fontWeightAdjustment = application.getResources().getConfiguration().fontWeightAdjustment;
                buffer.setSpan(new StyleSpan(r3, fontWeightAdjustment), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.italicId) {
                buffer.setSpan(new StyleSpan(2), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.underlineId) {
                buffer.setSpan(new UnderlineSpan(), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.ttId) {
                buffer.setSpan(new TypefaceSpan("monospace"), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.bigId) {
                buffer.setSpan(new RelativeSizeSpan(1.25f), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.smallId) {
                buffer.setSpan(new RelativeSizeSpan(0.8f), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.subId) {
                buffer.setSpan(new SubscriptSpan(), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.supId) {
                buffer.setSpan(new SuperscriptSpan(), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.strikeId) {
                buffer.setSpan(new StrikethroughSpan(), style[i4 + 1], style[i4 + 2] + r3, 33);
            } else if (type == ids.listItemId) {
                addParagraphSpan(buffer, new BulletSpan(10), style[i4 + 1], style[i4 + 2] + r3);
            } else if (type == ids.marqueeId) {
                buffer.setSpan(TextUtils.TruncateAt.MARQUEE, style[i4 + 1], style[i4 + 2] + r3, 18);
            } else {
                String tag = nativeGetString(this.mNative, type);
                if (tag == null) {
                    return null;
                }
                if (tag.startsWith("font;")) {
                    String sub = subtag(tag, ";height=");
                    if (sub != null) {
                        int size = Integer.parseInt(sub);
                        addParagraphSpan(buffer, new Height(size), style[i4 + 1], style[i4 + 2] + r3);
                    }
                    String sub2 = subtag(tag, ";size=");
                    if (sub2 != null) {
                        int size2 = Integer.parseInt(sub2);
                        buffer.setSpan(new AbsoluteSizeSpan(size2, r3), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                    String sub3 = subtag(tag, ";fgcolor=");
                    if (sub3 != null) {
                        buffer.setSpan(getColor(sub3, r3), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                    String sub4 = subtag(tag, ";color=");
                    if (sub4 != null) {
                        buffer.setSpan(getColor(sub4, r3), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                    String sub5 = subtag(tag, ";bgcolor=");
                    if (sub5 != null) {
                        buffer.setSpan(getColor(sub5, false), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                    String sub6 = subtag(tag, ";face=");
                    if (sub6 != null) {
                        buffer.setSpan(new TypefaceSpan(sub6), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                } else if (tag.startsWith("a;")) {
                    String sub7 = subtag(tag, ";href=");
                    if (sub7 != null) {
                        buffer.setSpan(new URLSpan(sub7), style[i4 + 1], style[i4 + 2] + r3, 33);
                    }
                } else if (tag.startsWith("annotation;")) {
                    int len = tag.length();
                    int i5 = 59;
                    int t = tag.indexOf(59);
                    while (t < len) {
                        int eq = tag.indexOf(61, t);
                        if (eq < 0) {
                            break;
                        }
                        int next = tag.indexOf(i5, eq);
                        if (next < 0) {
                            next = len;
                        }
                        String key = tag.substring(t + 1, eq);
                        String value = tag.substring(eq + 1, next);
                        buffer.setSpan(new Annotation(key, value), style[i4 + 1], style[i4 + 2] + 1, 33);
                        t = next;
                        str2 = str2;
                        i5 = 59;
                    }
                }
            }
            i4 += 3;
            str2 = str2;
            r3 = 1;
        }
        return new SpannedString(buffer);
    }

    private static CharacterStyle getColor(String color, boolean foreground) {
        int c = -16777216;
        if (!TextUtils.isEmpty(color)) {
            if (color.startsWith("@")) {
                Resources res = Resources.getSystem();
                String name = color.substring(1);
                int colorRes = res.getIdentifier(name, "color", "android");
                if (colorRes != 0) {
                    ColorStateList colors = res.getColorStateList(colorRes, null);
                    if (foreground) {
                        return new TextAppearanceSpan(null, 0, 0, colors, null);
                    }
                    c = colors.getDefaultColor();
                }
            } else {
                try {
                    c = Color.parseColor(color);
                } catch (IllegalArgumentException e) {
                    c = -16777216;
                }
            }
        }
        if (foreground) {
            return new ForegroundColorSpan(c);
        }
        return new BackgroundColorSpan(c);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0029, code lost:
    
        if (r3.charAt(r6 - 1) != '\n') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002b, code lost:
    
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002d, code lost:
    
        if (r6 >= r0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0035, code lost:
    
        if (r3.charAt(r6 - 1) != '\n') goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0010, code lost:
    
        if (r3.charAt(r5 - 1) != '\n') goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        r5 = r5 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0014, code lost:
    
        if (r5 <= 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x001c, code lost:
    
        if (r3.charAt(r5 - 1) != '\n') goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void addParagraphSpan(android.text.Spannable r3, java.lang.Object r4, int r5, int r6) {
        /*
            int r0 = r3.length()
            r1 = 10
            if (r5 == 0) goto L1f
            if (r5 == r0) goto L1f
            int r2 = r5 + (-1)
            char r2 = r3.charAt(r2)
            if (r2 == r1) goto L1f
        L12:
            int r5 = r5 + (-1)
            if (r5 <= 0) goto L1f
            int r2 = r5 + (-1)
            char r2 = r3.charAt(r2)
            if (r2 != r1) goto L12
        L1f:
            if (r6 == 0) goto L38
            if (r6 == r0) goto L38
            int r2 = r6 + (-1)
            char r2 = r3.charAt(r2)
            if (r2 == r1) goto L38
        L2b:
            int r6 = r6 + 1
            if (r6 >= r0) goto L38
            int r2 = r6 + (-1)
            char r2 = r3.charAt(r2)
            if (r2 != r1) goto L2b
        L38:
            r1 = 51
            r3.setSpan(r4, r5, r6, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.StringBlock.addParagraphSpan(android.text.Spannable, java.lang.Object, int, int):void");
    }

    private static String subtag(String full, String attribute) {
        int start = full.indexOf(attribute);
        if (start < 0) {
            return null;
        }
        int start2 = start + attribute.length();
        int end = full.indexOf(59, start2);
        if (end < 0) {
            return full.substring(start2);
        }
        return full.substring(start2, end);
    }

    /* loaded from: classes.dex */
    public static class Height implements LineHeightSpan.WithDensity {
        private static float sProportion = 0.0f;
        private int mSize;

        public Height(int size) {
            this.mSize = size;
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
            chooseHeight(text, start, end, spanstartv, v, fm, null);
        }

        @Override // android.text.style.LineHeightSpan.WithDensity
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm, TextPaint paint) {
            int size = this.mSize;
            if (paint != null) {
                size = (int) (size * paint.density);
            }
            if (fm.bottom - fm.top < size) {
                fm.top = fm.bottom - size;
                fm.ascent -= size;
                return;
            }
            if (sProportion == 0.0f) {
                Paint p = new Paint();
                p.setTextSize(100.0f);
                Rect r = new Rect();
                p.getTextBounds("ABCDEFG", 0, 7, r);
                sProportion = r.top / p.ascent();
            }
            int need = (int) Math.ceil((-fm.top) * sProportion);
            if (size - fm.descent >= need) {
                fm.top = fm.bottom - size;
                fm.ascent = fm.descent - size;
                return;
            }
            if (size >= need) {
                int i = -need;
                fm.ascent = i;
                fm.top = i;
                int i2 = fm.top + size;
                fm.descent = i2;
                fm.bottom = i2;
                return;
            }
            int i3 = -size;
            fm.ascent = i3;
            fm.top = i3;
            fm.descent = 0;
            fm.bottom = 0;
        }
    }

    public StringBlock(long obj, boolean useSparse) {
        this.mNative = obj;
        this.mUseSparse = useSparse;
    }
}

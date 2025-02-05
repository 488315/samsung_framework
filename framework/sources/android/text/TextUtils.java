package android.text;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.icu.lang.UCharacter;
import android.icu.text.CaseMap;
import android.icu.text.Edits;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import android.sysprop.DisplayProperties;
import android.telecom.Logging.Session;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityReplacementSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.LineBreakConfigSpan;
import android.text.style.LineHeightSpan;
import android.text.style.LocaleSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.EmptyArray;
import android.util.Log;
import android.util.Printer;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.media.SemExtendedFormat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TextUtils {
    public static final int ABSOLUTE_SIZE_SPAN = 16;
    public static final int ACCESSIBILITY_CLICKABLE_SPAN = 25;
    public static final int ACCESSIBILITY_REPLACEMENT_SPAN = 29;
    public static final int ACCESSIBILITY_URL_SPAN = 26;
    public static final int ALIGNMENT_SPAN = 1;
    public static final int ANNOTATION = 18;
    public static final int BACKGROUND_COLOR_SPAN = 12;
    public static final int BULLET_SPAN = 8;
    public static final int CAP_MODE_CHARACTERS = 4096;
    public static final int CAP_MODE_SENTENCES = 16384;
    public static final int CAP_MODE_WORDS = 8192;
    public static final int EASY_EDIT_SPAN = 22;
    static final char ELLIPSIS_FILLER = 65279;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String ELLIPSIS_TWO_DOTS = "‥";
    public static final int FIRST_SPAN = 1;
    public static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 30;
    public static final int LEADING_MARGIN_SPAN = 10;
    public static final int LINE_BACKGROUND_SPAN = 27;
    public static final int LINE_BREAK_CONFIG_SPAN = 30;
    public static final int LINE_FEED_CODE_POINT = 10;
    public static final int LINE_HEIGHT_SPAN = 28;
    public static final int LOCALE_SPAN = 23;
    private static final int NBSP_CODE_POINT = 160;
    private static final int PARCEL_SAFE_TEXT_LENGTH = 100000;
    public static final int QUOTE_SPAN = 9;
    public static final int RELATIVE_SIZE_SPAN = 3;
    public static final int SAFE_STRING_FLAG_FIRST_LINE = 4;
    public static final int SAFE_STRING_FLAG_SINGLE_LINE = 2;
    public static final int SAFE_STRING_FLAG_TRIM = 1;
    public static final int SCALE_X_SPAN = 4;
    public static final int SPELL_CHECK_SPAN = 20;
    public static final int STRIKETHROUGH_SPAN = 5;
    public static final int STYLE_SPAN = 7;
    public static final int SUBSCRIPT_SPAN = 15;
    public static final int SUGGESTION_RANGE_SPAN = 21;
    public static final int SUGGESTION_SPAN = 19;
    public static final int SUPERSCRIPT_SPAN = 14;
    private static final String TAG = "TextUtils";
    public static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    public static final int TYPEFACE_SPAN = 13;
    public static final int UNDERLINE_SPAN = 6;
    public static final int URL_SPAN = 11;
    public static final Parcelable.Creator<CharSequence> CHAR_SEQUENCE_CREATOR = new Parcelable.Creator<CharSequence>() { // from class: android.text.TextUtils.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequence createFromParcel(Parcel p) {
            Object span;
            int kind = p.readInt();
            String string = p.readString8();
            if (string == null) {
                return null;
            }
            if (kind == 1) {
                return string;
            }
            SpannableString sp = new SpannableString(string);
            while (true) {
                int kind2 = p.readInt();
                if (kind2 != 0) {
                    switch (kind2) {
                        case 1:
                            span = new AlignmentSpan.Standard(p);
                            break;
                        case 2:
                            span = new ForegroundColorSpan(p);
                            break;
                        case 3:
                            span = new RelativeSizeSpan(p);
                            break;
                        case 4:
                            span = new ScaleXSpan(p);
                            break;
                        case 5:
                            span = new StrikethroughSpan(p);
                            break;
                        case 6:
                            span = new UnderlineSpan(p);
                            break;
                        case 7:
                            span = new StyleSpan(p);
                            break;
                        case 8:
                            span = new BulletSpan(p);
                            break;
                        case 9:
                            span = new QuoteSpan(p);
                            break;
                        case 10:
                            span = new LeadingMarginSpan.Standard(p);
                            break;
                        case 11:
                            span = new URLSpan(p);
                            break;
                        case 12:
                            span = new BackgroundColorSpan(p);
                            break;
                        case 13:
                            span = new TypefaceSpan(p);
                            break;
                        case 14:
                            span = new SuperscriptSpan(p);
                            break;
                        case 15:
                            span = new SubscriptSpan(p);
                            break;
                        case 16:
                            span = new AbsoluteSizeSpan(p);
                            break;
                        case 17:
                            span = new TextAppearanceSpan(p);
                            break;
                        case 18:
                            span = new Annotation(p);
                            break;
                        case 19:
                            span = new SuggestionSpan(p);
                            break;
                        case 20:
                            span = new SpellCheckSpan(p);
                            break;
                        case 21:
                            span = new SuggestionRangeSpan(p);
                            break;
                        case 22:
                            span = new EasyEditSpan(p);
                            break;
                        case 23:
                            span = new LocaleSpan(p);
                            break;
                        case 24:
                            span = new TtsSpan(p);
                            break;
                        case 25:
                            span = new AccessibilityClickableSpan(p);
                            break;
                        case 26:
                            span = new AccessibilityURLSpan(p);
                            break;
                        case 27:
                            span = new LineBackgroundSpan.Standard(p);
                            break;
                        case 28:
                            span = new LineHeightSpan.Standard(p);
                            break;
                        case 29:
                            span = new AccessibilityReplacementSpan(p);
                            break;
                        case 30:
                            span = LineBreakConfigSpan.CREATOR.createFromParcel(p);
                            break;
                        default:
                            throw new RuntimeException("bogus span encoding " + kind2);
                    }
                    TextUtils.readSpan(p, sp, span);
                } else {
                    return sp;
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequence[] newArray(int size) {
            return new CharSequence[size];
        }
    };
    private static Object sLock = new Object();
    private static char[] sTemp = null;

    public interface EllipsizeCallback {
        void ellipsized(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SafeStringFlags {
    }

    public interface StringSplitter extends Iterable<String> {
        void setString(String str);
    }

    public enum TruncateAt {
        START,
        MIDDLE,
        END,
        MARQUEE,
        END_SMALL,
        SEM_KEYWORD
    }

    public static String getEllipsisString(TruncateAt method) {
        return method == TruncateAt.END_SMALL ? ELLIPSIS_TWO_DOTS : ELLIPSIS_NORMAL;
    }

    private TextUtils() {
    }

    public static void getChars(CharSequence s, int start, int end, char[] dest, int destoff) {
        Class<?> cls = s.getClass();
        if (cls == String.class) {
            ((String) s).getChars(start, end, dest, destoff);
            return;
        }
        if (cls == StringBuffer.class) {
            ((StringBuffer) s).getChars(start, end, dest, destoff);
            return;
        }
        if (cls == StringBuilder.class) {
            ((StringBuilder) s).getChars(start, end, dest, destoff);
            return;
        }
        if (s instanceof GetChars) {
            ((GetChars) s).getChars(start, end, dest, destoff);
            return;
        }
        int i = start;
        while (i < end) {
            dest[destoff] = s.charAt(i);
            i++;
            destoff++;
        }
    }

    public static int indexOf(CharSequence s, char ch) {
        return indexOf(s, ch, 0);
    }

    public static int indexOf(CharSequence s, char ch, int start) {
        if (s.getClass() == String.class) {
            return ((String) s).indexOf(ch, start);
        }
        return indexOf(s, ch, start, s.length());
    }

    public static int indexOf(CharSequence s, char ch, int start, int end) {
        Class<?> cls = s.getClass();
        if ((s instanceof GetChars) || cls == StringBuffer.class || cls == StringBuilder.class || cls == String.class) {
            char[] temp = obtain(500);
            while (start < end) {
                int segend = start + 500;
                if (segend > end) {
                    segend = end;
                }
                getChars(s, start, segend, temp, 0);
                int count = segend - start;
                for (int i = 0; i < count; i++) {
                    if (temp[i] == ch) {
                        recycle(temp);
                        return i + start;
                    }
                }
                start = segend;
            }
            recycle(temp);
            return -1;
        }
        for (int i2 = start; i2 < end; i2++) {
            if (s.charAt(i2) == ch) {
                return i2;
            }
        }
        return -1;
    }

    public static int lastIndexOf(CharSequence s, char ch) {
        return lastIndexOf(s, ch, s.length() - 1);
    }

    public static int lastIndexOf(CharSequence s, char ch, int last) {
        if (s.getClass() == String.class) {
            return ((String) s).lastIndexOf(ch, last);
        }
        return lastIndexOf(s, ch, 0, last);
    }

    public static int lastIndexOf(CharSequence s, char ch, int start, int last) {
        if (last < 0) {
            return -1;
        }
        if (last >= s.length()) {
            last = s.length() - 1;
        }
        int end = last + 1;
        Class<?> cls = s.getClass();
        if ((s instanceof GetChars) || cls == StringBuffer.class || cls == StringBuilder.class || cls == String.class) {
            char[] temp = obtain(500);
            while (start < end) {
                int segstart = end - 500;
                if (segstart < start) {
                    segstart = start;
                }
                getChars(s, segstart, end, temp, 0);
                int count = end - segstart;
                for (int i = count - 1; i >= 0; i--) {
                    if (temp[i] == ch) {
                        recycle(temp);
                        return i + segstart;
                    }
                }
                end = segstart;
            }
            recycle(temp);
            return -1;
        }
        for (int i2 = end - 1; i2 >= start; i2--) {
            if (s.charAt(i2) == ch) {
                return i2;
            }
        }
        return -1;
    }

    public static int indexOf(CharSequence s, CharSequence needle) {
        return indexOf(s, needle, 0, s.length());
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start) {
        return indexOf(s, needle, start, s.length());
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start, int end) {
        int nlen = needle.length();
        if (nlen == 0) {
            return start;
        }
        char c = needle.charAt(0);
        while (true) {
            int start2 = indexOf(s, c, start);
            if (start2 > end - nlen || start2 < 0) {
                return -1;
            }
            if (regionMatches(s, start2, needle, 0, nlen)) {
                return start2;
            }
            start = start2 + 1;
        }
    }

    public static boolean regionMatches(CharSequence one, int toffset, CharSequence two, int ooffset, int len) {
        int tempLen = len * 2;
        if (tempLen < len) {
            throw new IndexOutOfBoundsException();
        }
        char[] temp = obtain(tempLen);
        getChars(one, toffset, toffset + len, temp, 0);
        getChars(two, ooffset, ooffset + len, temp, len);
        boolean match = true;
        int i = 0;
        while (true) {
            if (i >= len) {
                break;
            }
            if (temp[i] == temp[i + len]) {
                i++;
            } else {
                match = false;
                break;
            }
        }
        recycle(temp);
        return match;
    }

    public static String substring(CharSequence source, int start, int end) {
        if (source instanceof String) {
            return ((String) source).substring(start, end);
        }
        if (source instanceof StringBuilder) {
            return ((StringBuilder) source).substring(start, end);
        }
        if (source instanceof StringBuffer) {
            return ((StringBuffer) source).substring(start, end);
        }
        char[] temp = obtain(end - start);
        getChars(source, start, end, temp, 0);
        String ret = new String(temp, 0, end - start);
        recycle(temp);
        return ret;
    }

    public static String truncateStringForUtf8Storage(String str, int maxbytes) {
        if (maxbytes < 0) {
            throw new IndexOutOfBoundsException();
        }
        int bytes = 0;
        int i = 0;
        int len = str.length();
        while (i < len) {
            char c = str.charAt(i);
            if (c < 128) {
                bytes++;
            } else if (c < 2048) {
                bytes += 2;
            } else if (c < 55296 || c > 57343 || str.codePointAt(i) < 65536) {
                bytes += 3;
            } else {
                bytes += 4;
                i += bytes > maxbytes ? 0 : 1;
            }
            if (bytes <= maxbytes) {
                i++;
            } else {
                return str.substring(0, i);
            }
        }
        return str;
    }

    public static String join(CharSequence delimiter, Object[] tokens) {
        int length = tokens.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(tokens[0]);
        for (int i = 1; i < length; i++) {
            sb.append(delimiter);
            sb.append(tokens[i]);
        }
        return sb.toString();
    }

    public static String join(CharSequence delimiter, Iterable tokens) {
        Iterator<?> it = tokens.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String[] split(String text, String expression) {
        if (text.length() == 0) {
            return EmptyArray.STRING;
        }
        return text.split(expression, -1);
    }

    public static String[] split(String text, Pattern pattern) {
        if (text.length() == 0) {
            return EmptyArray.STRING;
        }
        return pattern.split(text, -1);
    }

    public static class SimpleStringSplitter implements StringSplitter, Iterator<String> {
        private char mDelimiter;
        private int mLength;
        private int mPosition;
        private String mString;

        public SimpleStringSplitter(char delimiter) {
            this.mDelimiter = delimiter;
        }

        @Override // android.text.TextUtils.StringSplitter
        public void setString(String string) {
            this.mString = string;
            this.mPosition = 0;
            this.mLength = this.mString.length();
        }

        @Override // java.lang.Iterable
        public Iterator<String> iterator() {
            return this;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mPosition < this.mLength;
        }

        @Override // java.util.Iterator
        public String next() {
            int end = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (end == -1) {
                end = this.mLength;
            }
            String nextString = this.mString.substring(this.mPosition, end);
            this.mPosition = end + 1;
            return nextString;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static CharSequence stringOrSpannedString(CharSequence source) {
        if (source == null) {
            return null;
        }
        if (source instanceof SpannedString) {
            return source;
        }
        if (source instanceof Spanned) {
            return new SpannedString(source);
        }
        return source.toString();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static String nullIfEmpty(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static String firstNotEmpty(String a, String b) {
        return !isEmpty(a) ? a : (String) Preconditions.checkStringNotEmpty(b);
    }

    public static int length(String s) {
        if (s != null) {
            return s.length();
        }
        return 0;
    }

    public static String safeIntern(String s) {
        if (s != null) {
            return s.intern();
        }
        return null;
    }

    public static int getTrimmedLength(CharSequence s) {
        int len = s.length();
        int start = 0;
        while (start < len && s.charAt(start) <= ' ') {
            start++;
        }
        int end = len;
        while (end > start && s.charAt(end - 1) <= ' ') {
            end--;
        }
        return end - start;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        int length;
        if (a == b) {
            return true;
        }
        if (a == null || b == null || (length = a.length()) != b.length()) {
            return false;
        }
        if ((a instanceof String) && (b instanceof String)) {
            return a.equals(b);
        }
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public static CharSequence getReverse(CharSequence source, int start, int end) {
        return new Reverser(source, start, end);
    }

    private static class Reverser implements CharSequence, GetChars {
        private int mEnd;
        private CharSequence mSource;
        private int mStart;

        public Reverser(CharSequence source, int start, int end) {
            this.mSource = source;
            this.mStart = start;
            this.mEnd = end;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mEnd - this.mStart;
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int start, int end) {
            char[] buf = new char[end - start];
            getChars(start, end, buf, 0);
            return new String(buf);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return subSequence(0, length()).toString();
        }

        @Override // java.lang.CharSequence
        public char charAt(int off) {
            return (char) UCharacter.getMirror(this.mSource.charAt((this.mEnd - 1) - off));
        }

        @Override // android.text.GetChars
        public void getChars(int start, int end, char[] dest, int destoff) {
            TextUtils.getChars(this.mSource, this.mStart + start, this.mStart + end, dest, destoff);
            AndroidCharacter.mirror(dest, 0, end - start);
            int len = end - start;
            int n = (end - start) / 2;
            for (int i = 0; i < n; i++) {
                char tmp = dest[destoff + i];
                dest[destoff + i] = dest[((destoff + len) - i) - 1];
                dest[((destoff + len) - i) - 1] = tmp;
            }
        }
    }

    public static void writeToParcel(CharSequence cs, Parcel p, int parcelableFlags) {
        if (cs instanceof Spanned) {
            p.writeInt(0);
            p.writeString8(cs.toString());
            Spanned sp = (Spanned) cs;
            Object[] os = sp.getSpans(0, cs.length(), Object.class);
            for (int i = 0; i < os.length; i++) {
                Object o = os[i];
                Object prop = os[i];
                if (prop instanceof CharacterStyle) {
                    prop = ((CharacterStyle) prop).getUnderlying();
                }
                if (prop instanceof ParcelableSpan) {
                    ParcelableSpan ps = (ParcelableSpan) prop;
                    int spanTypeId = ps.getSpanTypeIdInternal();
                    if (spanTypeId < 1 || spanTypeId > 30) {
                        Log.e(TAG, "External class \"" + ps.getClass().getSimpleName() + "\" is attempting to use the frameworks-only ParcelableSpan interface");
                    } else {
                        p.writeInt(spanTypeId);
                        ps.writeToParcelInternal(p, parcelableFlags);
                        writeWhere(p, sp, o);
                    }
                }
            }
            p.writeInt(0);
            return;
        }
        p.writeInt(1);
        if (cs != null) {
            p.writeString8(cs.toString());
        } else {
            p.writeString8(null);
        }
    }

    private static void writeWhere(Parcel p, Spanned sp, Object o) {
        p.writeInt(sp.getSpanStart(o));
        p.writeInt(sp.getSpanEnd(o));
        p.writeInt(sp.getSpanFlags(o));
    }

    public static void dumpSpans(CharSequence cs, Printer printer, String prefix) {
        if (cs instanceof Spanned) {
            Spanned sp = (Spanned) cs;
            Object[] os = sp.getSpans(0, cs.length(), Object.class);
            for (Object o : os) {
                printer.println(prefix + ((Object) cs.subSequence(sp.getSpanStart(o), sp.getSpanEnd(o))) + ": " + Integer.toHexString(System.identityHashCode(o)) + " " + o.getClass().getCanonicalName() + " (" + sp.getSpanStart(o) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + sp.getSpanEnd(o) + ") fl=#" + sp.getSpanFlags(o));
            }
            return;
        }
        printer.println(prefix + ((Object) cs) + ": (no spans)");
    }

    public static CharSequence replace(CharSequence template, String[] sources, CharSequence[] destinations) {
        SpannableStringBuilder tb = new SpannableStringBuilder(template);
        for (int i = 0; i < sources.length; i++) {
            int where = indexOf(tb, sources[i]);
            if (where >= 0) {
                tb.setSpan(sources[i], where, sources[i].length() + where, 33);
            }
        }
        for (int i2 = 0; i2 < sources.length; i2++) {
            int start = tb.getSpanStart(sources[i2]);
            int end = tb.getSpanEnd(sources[i2]);
            if (start >= 0) {
                tb.replace(start, end, destinations[i2]);
            }
        }
        return tb;
    }

    public static CharSequence expandTemplate(CharSequence template, CharSequence... values) {
        if (values.length > 9) {
            throw new IllegalArgumentException("max of 9 values are supported");
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(template);
        int i = 0;
        while (i < ssb.length()) {
            try {
                if (ssb.charAt(i) == '^') {
                    char next = ssb.charAt(i + 1);
                    if (next == '^') {
                        ssb.delete(i + 1, i + 2);
                        i++;
                    } else if (Character.isDigit(next)) {
                        int which = Character.getNumericValue(next) - 1;
                        if (which < 0) {
                            throw new IllegalArgumentException("template requests value ^" + (which + 1));
                        }
                        if (which >= values.length) {
                            throw new IllegalArgumentException("template requests value ^" + (which + 1) + "; only " + values.length + " provided");
                        }
                        ssb.replace(i, i + 2, values[which]);
                        i += values[which].length();
                    }
                }
                i++;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        return ssb;
    }

    public static int getOffsetBefore(CharSequence text, int offset) {
        int offset2;
        if (offset == 0 || offset == 1) {
            return 0;
        }
        char c = text.charAt(offset - 1);
        if (c >= 56320 && c <= 57343) {
            char c1 = text.charAt(offset - 2);
            offset2 = (c1 >= 55296 && c1 <= 56319) ? offset - 2 : offset - 1;
        } else {
            offset2 = offset - 1;
        }
        if (text instanceof Spanned) {
            ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset2, offset2, ReplacementSpan.class);
            for (int i = 0; i < spans.length; i++) {
                int start = ((Spanned) text).getSpanStart(spans[i]);
                int end = ((Spanned) text).getSpanEnd(spans[i]);
                if (start < offset2 && end > offset2) {
                    offset2 = start;
                }
            }
        }
        return offset2;
    }

    public static int getOffsetAfter(CharSequence text, int offset) {
        int offset2;
        int len = text.length();
        if (offset == len) {
            return len;
        }
        if (offset == len - 1) {
            return len;
        }
        char c = text.charAt(offset);
        if (c >= 55296 && c <= 56319) {
            char c1 = text.charAt(offset + 1);
            if (c1 >= 56320 && c1 <= 57343) {
                offset2 = offset + 2;
            } else {
                offset2 = offset + 1;
            }
        } else {
            offset2 = offset + 1;
        }
        if (text instanceof Spanned) {
            ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset2, offset2, ReplacementSpan.class);
            for (int i = 0; i < spans.length; i++) {
                int start = ((Spanned) text).getSpanStart(spans[i]);
                int end = ((Spanned) text).getSpanEnd(spans[i]);
                if (start < offset2 && end > offset2) {
                    offset2 = end;
                }
            }
        }
        return offset2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readSpan(Parcel p, Spannable sp, Object o) {
        sp.setSpan(o, p.readInt(), p.readInt(), p.readInt());
    }

    public static void copySpansFrom(Spanned source, int start, int end, Class kind, Spannable dest, int destoff) {
        if (kind == null) {
            kind = Object.class;
        }
        Object[] spans = source.getSpans(start, end, kind);
        for (int i = 0; i < spans.length; i++) {
            int st = source.getSpanStart(spans[i]);
            int en = source.getSpanEnd(spans[i]);
            int fl = source.getSpanFlags(spans[i]);
            if (st < start) {
                st = start;
            }
            if (en > end) {
                en = end;
            }
            dest.setSpan(spans[i], (st - start) + destoff, (en - start) + destoff, fl);
        }
    }

    public static CharSequence toUpperCase(Locale locale, CharSequence source, boolean copySpans) {
        Edits edits = new Edits();
        if (!copySpans) {
            return edits.hasChanges() ? (StringBuilder) CaseMap.toUpper().apply(locale, source, new StringBuilder(), edits) : source;
        }
        SpannableStringBuilder result = (SpannableStringBuilder) CaseMap.toUpper().apply(locale, source, new SpannableStringBuilder(), edits);
        if (!edits.hasChanges()) {
            return source;
        }
        Edits.Iterator iterator = edits.getFineIterator();
        int sourceLength = source.length();
        Spanned spanned = (Spanned) source;
        Object[] spans = spanned.getSpans(0, sourceLength, Object.class);
        for (Object span : spans) {
            int sourceStart = spanned.getSpanStart(span);
            int sourceEnd = spanned.getSpanEnd(span);
            int flags = spanned.getSpanFlags(span);
            int destStart = sourceStart == sourceLength ? result.length() : toUpperMapToDest(iterator, sourceStart);
            int destEnd = sourceEnd == sourceLength ? result.length() : toUpperMapToDest(iterator, sourceEnd);
            result.setSpan(span, destStart, destEnd, flags);
        }
        return result;
    }

    private static int toUpperMapToDest(Edits.Iterator iterator, int sourceIndex) {
        iterator.findSourceIndex(sourceIndex);
        if (sourceIndex == iterator.sourceIndex()) {
            return iterator.destinationIndex();
        }
        if (iterator.hasChange()) {
            return iterator.destinationIndex() + iterator.newLength();
        }
        return iterator.destinationIndex() + (sourceIndex - iterator.sourceIndex());
    }

    public static CharSequence ellipsize(CharSequence text, TextPaint p, float avail, TruncateAt where) {
        return ellipsize(text, p, avail, where, false, null);
    }

    public static CharSequence ellipsize(CharSequence text, TextPaint paint, float avail, TruncateAt where, boolean preserveLength, EllipsizeCallback callback) {
        return ellipsize(text, paint, avail, where, preserveLength, callback, TextDirectionHeuristics.FIRSTSTRONG_LTR, getEllipsisString(where));
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x014c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence ellipsize(java.lang.CharSequence r25, android.text.TextPaint r26, float r27, android.text.TextUtils.TruncateAt r28, boolean r29, android.text.TextUtils.EllipsizeCallback r30, android.text.TextDirectionHeuristic r31, java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.ellipsize(java.lang.CharSequence, android.text.TextPaint, float, android.text.TextUtils$TruncateAt, boolean, android.text.TextUtils$EllipsizeCallback, android.text.TextDirectionHeuristic, java.lang.String):java.lang.CharSequence");
    }

    public static CharSequence listEllipsize(Context context, List<CharSequence> elements, String separator, TextPaint paint, float avail, int moreId) {
        int totalLen;
        Resources res;
        BidiFormatter bidiFormatter;
        CharSequence morePiece;
        if (elements == null || (totalLen = elements.size()) == 0) {
            return "";
        }
        if (context == null) {
            res = null;
            bidiFormatter = BidiFormatter.getInstance();
        } else {
            res = context.getResources();
            bidiFormatter = BidiFormatter.getInstance(res.getConfiguration().getLocales().get(0));
        }
        SpannableStringBuilder output = new SpannableStringBuilder();
        int[] endIndexes = new int[totalLen];
        for (int i = 0; i < totalLen; i++) {
            output.append(bidiFormatter.unicodeWrap(elements.get(i)));
            if (i != totalLen - 1) {
                output.append((CharSequence) separator);
            }
            endIndexes[i] = output.length();
        }
        for (int i2 = totalLen - 1; i2 >= 0; i2--) {
            output.delete(endIndexes[i2], output.length());
            int remainingElements = (totalLen - i2) - 1;
            if (remainingElements > 0) {
                if (res == null) {
                    morePiece = ELLIPSIS_NORMAL;
                } else {
                    morePiece = res.getQuantityString(moreId, remainingElements, Integer.valueOf(remainingElements));
                }
                output.append(bidiFormatter.unicodeWrap(morePiece));
            }
            float width = paint.measureText(output, 0, output.length());
            if (width <= avail) {
                return output;
            }
        }
        return "";
    }

    @Deprecated
    public static CharSequence commaEllipsize(CharSequence text, TextPaint p, float avail, String oneMore, String more) {
        return commaEllipsize(text, p, avail, oneMore, more, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fb  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence commaEllipsize(java.lang.CharSequence r22, android.text.TextPaint r23, float r24, java.lang.String r25, java.lang.String r26, android.text.TextDirectionHeuristic r27) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.commaEllipsize(java.lang.CharSequence, android.text.TextPaint, float, java.lang.String, java.lang.String, android.text.TextDirectionHeuristic):java.lang.CharSequence");
    }

    static boolean couldAffectRtl(char c) {
        return (1424 <= c && c <= 2303) || c == 8206 || c == 8207 || (8234 <= c && c <= 8238) || ((8294 <= c && c <= 8297) || ((55296 <= c && c <= 57343) || ((64285 <= c && c <= 65023) || (65136 <= c && c <= 65278))));
    }

    static boolean doesNotNeedBidi(char[] text, int start, int len) {
        int end = start + len;
        for (int i = start; i < end; i++) {
            if (couldAffectRtl(text[i])) {
                return false;
            }
        }
        return true;
    }

    static char[] obtain(int len) {
        char[] buf;
        synchronized (sLock) {
            buf = sTemp;
            sTemp = null;
        }
        if (buf == null || buf.length < len) {
            return ArrayUtils.newUnpaddedCharArray(len);
        }
        return buf;
    }

    static char[] obtain$ravenwood(int len) {
        return new char[len];
    }

    static void recycle(char[] temp) {
        if (temp.length > 1000) {
            return;
        }
        synchronized (sLock) {
            sTemp = temp;
        }
    }

    static void recycle$ravenwood(char[] temp) {
    }

    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    public static CharSequence concat(CharSequence... text) {
        if (text.length == 0) {
            return "";
        }
        int i = 0;
        if (text.length == 1) {
            return text[0];
        }
        boolean spanned = false;
        int length = text.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (!(text[i2] instanceof Spanned)) {
                i2++;
            } else {
                spanned = true;
                break;
            }
        }
        if (spanned) {
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            int length2 = text.length;
            while (i < length2) {
                CharSequence piece = text[i];
                ssb.append(piece == null ? "null" : piece);
                i++;
            }
            return new SpannedString(ssb);
        }
        StringBuilder sb = new StringBuilder();
        int length3 = text.length;
        while (i < length3) {
            sb.append(text[i]);
            i++;
        }
        return sb.toString();
    }

    public static boolean isGraphic(CharSequence str) {
        int len = str.length();
        int i = 0;
        while (i < len) {
            int cp = Character.codePointAt(str, i);
            int gc = Character.getType(cp);
            if (gc == 15 || gc == 16 || gc == 19 || gc == 0 || gc == 13 || gc == 14 || gc == 12) {
                i += Character.charCount(cp);
            } else {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isGraphic(char c) {
        int gc = Character.getType(c);
        return (gc == 15 || gc == 16 || gc == 19 || gc == 0 || gc == 13 || gc == 14 || gc == 12) ? false : true;
    }

    public static boolean isDigitsOnly(CharSequence str) {
        int len = str.length();
        int i = 0;
        while (i < len) {
            int cp = Character.codePointAt(str, i);
            if (Character.isDigit(cp)) {
                i += Character.charCount(cp);
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrintableAscii(char c) {
        return (' ' <= c && c <= '~') || c == '\r' || c == '\n';
    }

    public static boolean isPrintableAsciiOnly(CharSequence str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!isPrintableAscii(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getCapsMode(CharSequence cs, int off, int reqModes) {
        char c;
        char c2;
        if (off < 0) {
            return 0;
        }
        int mode = 0;
        if ((reqModes & 4096) != 0) {
            mode = 0 | 4096;
        }
        if ((reqModes & 24576) == 0) {
            return mode;
        }
        int i = off;
        while (i > 0 && ((c2 = cs.charAt(i - 1)) == '\"' || c2 == '\'' || Character.getType(c2) == 21)) {
            i--;
        }
        int j = i;
        while (j > 0) {
            char c3 = cs.charAt(j - 1);
            if (c3 != ' ' && c3 != '\t') {
                break;
            }
            j--;
        }
        if (j == 0 || cs.charAt(j - 1) == '\n') {
            return mode | 8192;
        }
        if ((reqModes & 16384) == 0) {
            return i != j ? mode | 8192 : mode;
        }
        if (i == j) {
            return mode;
        }
        while (j > 0) {
            char c4 = cs.charAt(j - 1);
            if (c4 != '\"' && c4 != '\'' && Character.getType(c4) != 22) {
                break;
            }
            j--;
        }
        if (j > 0 && ((c = cs.charAt(j - 1)) == '.' || c == '?' || c == '!')) {
            if (c == '.') {
                for (int k = j - 2; k >= 0; k--) {
                    char c5 = cs.charAt(k);
                    if (c5 == '.') {
                        return mode;
                    }
                    if (!Character.isLetter(c5)) {
                        break;
                    }
                }
            }
            return mode | 16384;
        }
        return mode;
    }

    public static boolean delimitedStringContains(String delimitedString, char delimiter, String item) {
        if (isEmpty(delimitedString) || isEmpty(item)) {
            return false;
        }
        int pos = -1;
        int length = delimitedString.length();
        while (true) {
            int indexOf = delimitedString.indexOf(item, pos + 1);
            pos = indexOf;
            if (indexOf == -1) {
                return false;
            }
            if (pos <= 0 || delimitedString.charAt(pos - 1) == delimiter) {
                int expectedDelimiterPos = item.length() + pos;
                if (expectedDelimiterPos == length || delimitedString.charAt(expectedDelimiterPos) == delimiter) {
                    return true;
                }
            }
        }
    }

    public static <T> T[] removeEmptySpans(T[] tArr, Spanned spanned, Class<T> cls) {
        Object[] objArr = null;
        int i = 0;
        for (int i2 = 0; i2 < tArr.length; i2++) {
            T t = tArr[i2];
            if (spanned.getSpanStart(t) == spanned.getSpanEnd(t)) {
                if (objArr == null) {
                    objArr = (Object[]) Array.newInstance((Class<?>) cls, tArr.length - 1);
                    System.arraycopy(tArr, 0, objArr, 0, i2);
                    i = i2;
                }
            } else if (objArr != null) {
                objArr[i] = t;
                i++;
            }
        }
        if (objArr != null) {
            T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
            System.arraycopy(objArr, 0, tArr2, 0, i);
            return tArr2;
        }
        return tArr;
    }

    public static long packRangeInLong(int start, int end) {
        return (start << 32) | end;
    }

    public static int unpackRangeStartFromLong(long range) {
        return (int) (range >>> 32);
    }

    public static int unpackRangeEndFromLong(long range) {
        return (int) (4294967295L & range);
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        return ((locale == null || locale.equals(Locale.ROOT) || !ULocale.forLocale(locale).isRightToLeft()) && !DisplayProperties.debug_force_rtl().orElse(false).booleanValue()) ? 0 : 1;
    }

    public static String formatSimple(String format, Object... args) {
        String repl;
        StringBuilder sb = new StringBuilder(format);
        int j = 0;
        int i = 0;
        while (i < sb.length()) {
            if (sb.charAt(i) == '%') {
                char code = sb.charAt(i + 1);
                char prefixChar = 0;
                int prefixLen = 0;
                int consume = 2;
                while (true) {
                    if ('0' <= code && code <= '9') {
                        if (prefixChar == 0) {
                            prefixChar = code != '0' ? ' ' : '0';
                        }
                        prefixLen = (prefixLen * 10) + Character.digit(code, 10);
                        consume++;
                        code = sb.charAt((i + consume) - 1);
                    }
                }
                switch (code) {
                    case '%':
                        repl = "%";
                        break;
                    case 'b':
                        if (j == args.length) {
                            throw new IllegalArgumentException("Too few arguments");
                        }
                        int j2 = j + 1;
                        Object arg = args[j];
                        if (arg instanceof Boolean) {
                            repl = Boolean.toString(((Boolean) arg).booleanValue());
                            j = j2;
                            break;
                        } else {
                            repl = Boolean.toString(arg != null);
                            j = j2;
                            break;
                        }
                    case 'c':
                    case 'd':
                    case 'f':
                    case 's':
                        if (j == args.length) {
                            throw new IllegalArgumentException("Too few arguments");
                        }
                        repl = String.valueOf(args[j]);
                        j++;
                        break;
                    case 'x':
                        if (j == args.length) {
                            throw new IllegalArgumentException("Too few arguments");
                        }
                        int j3 = j + 1;
                        Object arg2 = args[j];
                        if (arg2 instanceof Integer) {
                            repl = Integer.toHexString(((Integer) arg2).intValue());
                            j = j3;
                            break;
                        } else if (arg2 instanceof Long) {
                            repl = Long.toHexString(((Long) arg2).longValue());
                            j = j3;
                            break;
                        } else {
                            throw new IllegalArgumentException("Unsupported hex type " + arg2.getClass());
                        }
                    default:
                        throw new IllegalArgumentException("Unsupported format code " + code);
                }
                sb.replace(i, i + consume, repl);
                int prefixInsert = (prefixChar == '0' && repl.charAt(0) == '-') ? 1 : 0;
                for (int k = repl.length(); k < prefixLen; k++) {
                    sb.insert(i + prefixInsert, prefixChar);
                }
                int k2 = repl.length();
                i += Math.max(k2, prefixLen);
            } else {
                i++;
            }
        }
        int i2 = args.length;
        if (j != i2) {
            throw new IllegalArgumentException("Too many arguments");
        }
        return sb.toString();
    }

    public static boolean hasStyleSpan(Spanned spanned) {
        Preconditions.checkArgument(spanned != null);
        Class<?>[] styleClasses = {CharacterStyle.class, ParagraphStyle.class, UpdateAppearance.class};
        for (Class<?> clazz : styleClasses) {
            if (spanned.nextSpanTransition(-1, spanned.length(), clazz) < spanned.length()) {
                return true;
            }
        }
        return false;
    }

    public static CharSequence trimNoCopySpans(CharSequence charSequence) {
        if (charSequence != null && (charSequence instanceof Spanned)) {
            return new SpannableStringBuilder(charSequence);
        }
        return charSequence;
    }

    public static void wrap(StringBuilder builder, String start, String end) {
        builder.insert(0, start);
        builder.append(end);
    }

    public static <T extends CharSequence> T trimToParcelableSize(T t) {
        return (T) trimToSize(t, 100000);
    }

    public static <T extends CharSequence> T trimToSize(T t, int i) {
        Preconditions.checkArgument(i > 0);
        if (isEmpty(t) || t.length() <= i) {
            return t;
        }
        if (Character.isHighSurrogate(t.charAt(i - 1)) && Character.isLowSurrogate(t.charAt(i))) {
            i--;
        }
        return (T) t.subSequence(0, i);
    }

    public static <T extends CharSequence> T trimToLengthWithEllipsis(T t, int i) {
        T t2 = (T) trimToSize(t, i);
        if (t != null && t2.length() < t.length()) {
            return t2.toString() + Session.TRUNCATE_STRING;
        }
        return t2;
    }

    public static boolean isNewline(int codePoint) {
        int type = Character.getType(codePoint);
        return type == 14 || type == 13 || codePoint == 10;
    }

    public static boolean isWhitespace(int codePoint) {
        return Character.isWhitespace(codePoint) || codePoint == 160;
    }

    public static boolean isWhitespaceExceptNewline(int codePoint) {
        return isWhitespace(codePoint) && !isNewline(codePoint);
    }

    public static boolean isPunctuation(int codePoint) {
        int type = Character.getType(codePoint);
        return type == 23 || type == 20 || type == 22 || type == 30 || type == 29 || type == 24 || type == 21;
    }

    public static String withoutPrefix(String prefix, String str) {
        if (prefix == null || str == null) {
            return str;
        }
        return str.startsWith(prefix) ? str.substring(prefix.length()) : str;
    }

    public static CharSequence makeSafeForPresentation(String unclean, int maxCharactersToConsider, float ellipsizeDip, int flags) {
        boolean z = true;
        boolean onlyKeepFirstLine = (flags & 4) != 0;
        boolean forceSingleLine = (flags & 2) != 0;
        boolean trim = (flags & 1) != 0;
        Preconditions.checkNotNull(unclean);
        Preconditions.checkArgumentNonnegative(maxCharactersToConsider);
        Preconditions.checkArgumentNonNegative(ellipsizeDip, "ellipsizeDip");
        Preconditions.checkFlagsArgument(flags, 7);
        if (onlyKeepFirstLine && forceSingleLine) {
            z = false;
        }
        Preconditions.checkArgument(z, "Cannot set SAFE_STRING_FLAG_SINGLE_LINE and SAFE_STRING_FLAG_FIRST_LINE at thesame time");
        String shortString = maxCharactersToConsider > 0 ? unclean.substring(0, Math.min(unclean.length(), maxCharactersToConsider)) : unclean;
        StringWithRemovedChars gettingCleaned = new StringWithRemovedChars(Html.fromHtml(shortString).toString());
        int firstNonWhiteSpace = -1;
        int firstTrailingWhiteSpace = -1;
        int uncleanLength = gettingCleaned.length();
        int offset = 0;
        while (true) {
            if (offset >= uncleanLength) {
                break;
            }
            int codePoint = gettingCleaned.codePointAt(offset);
            int type = Character.getType(codePoint);
            int codePointLen = Character.charCount(codePoint);
            boolean isNewline = isNewline(codePoint);
            if (onlyKeepFirstLine && isNewline) {
                gettingCleaned.removeAllCharAfter(offset);
                break;
            }
            if (forceSingleLine && isNewline) {
                gettingCleaned.removeRange(offset, offset + codePointLen);
            } else if (type == 15 && !isNewline) {
                gettingCleaned.removeRange(offset, offset + codePointLen);
            } else if (trim && !isWhitespace(codePoint)) {
                if (firstNonWhiteSpace == -1) {
                    firstNonWhiteSpace = offset;
                }
                firstTrailingWhiteSpace = offset + codePointLen;
            }
            offset += codePointLen;
        }
        if (trim) {
            if (firstNonWhiteSpace == -1) {
                gettingCleaned.removeAllCharAfter(0);
            } else {
                if (firstNonWhiteSpace > 0) {
                    gettingCleaned.removeAllCharBefore(firstNonWhiteSpace);
                }
                if (firstTrailingWhiteSpace < uncleanLength) {
                    gettingCleaned.removeAllCharAfter(firstTrailingWhiteSpace);
                }
            }
        }
        if (ellipsizeDip == 0.0f) {
            return gettingCleaned.toString();
        }
        if (Typeface.getSystemFontMap().isEmpty()) {
            int charCount = (int) ((0.5f + ellipsizeDip) / 23.94f);
            String text = gettingCleaned.toString();
            if (!isEmpty(text) && text.length() > charCount) {
                return ((String) trimToSize(text, charCount)) + getEllipsisString(TruncateAt.END);
            }
            return text;
        }
        TextPaint paint = new TextPaint();
        paint.setTextSize(42.0f);
        return ellipsize(gettingCleaned.toString(), paint, ellipsizeDip, TruncateAt.END);
    }

    private static class StringWithRemovedChars {
        private final String mOriginal;
        private BitSet mRemovedChars;

        StringWithRemovedChars(String original) {
            this.mOriginal = original;
        }

        void removeRange(int firstRemoved, int firstNonRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(firstRemoved, firstNonRemoved);
        }

        void removeAllCharBefore(int firstNonRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(0, firstNonRemoved);
        }

        void removeAllCharAfter(int firstRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(firstRemoved, this.mOriginal.length());
        }

        public String toString() {
            if (this.mRemovedChars == null) {
                return this.mOriginal;
            }
            StringBuilder sb = new StringBuilder(this.mOriginal.length());
            for (int i = 0; i < this.mOriginal.length(); i++) {
                if (!this.mRemovedChars.get(i)) {
                    sb.append(this.mOriginal.charAt(i));
                }
            }
            return sb.toString();
        }

        int length() {
            return this.mOriginal.length();
        }

        int codePointAt(int offset) {
            return this.mOriginal.codePointAt(offset);
        }
    }

    public static boolean isArabicChar(char c) {
        return (c >= 1536 && c <= 1791) || (c >= 64256 && c <= 65023) || (c >= 65136 && c <= 65278);
    }

    public static boolean isIndianChar(char c) {
        return c >= 2304 && c < 3583;
    }

    public static boolean isHalant(char c) {
        return c == 2381 || c == 2509 || c == 2637 || c == 2765 || c == 3021 || c == 3149 || c == 3277 || c == 3405 || c == 3551 || c == 2893;
    }

    public static boolean isThaiChar(char c) {
        return c >= 3585 && c < 3675;
    }

    public static boolean isThaiVowel(char c) {
        return c == 3633 || (c >= 3635 && c <= 3642) || (c >= 3655 && c <= 3662);
    }

    public static boolean isKhmerChar(char c) {
        return c >= 6016 && c <= 6137;
    }

    public static boolean isKhmerVowel(char c) {
        return c >= 6070 && c <= 6099;
    }

    public static boolean isKhmerCoengSign(char c) {
        return c == 6098;
    }

    public static boolean isLaoChar(char c) {
        return (c >= 3713 && c <= 3805) || (c >= 57345 && c <= 57368);
    }

    public static boolean isLaoVowel(char c) {
        return c == 3761 || (c >= 3764 && c <= 3772) || (c >= 3784 && c <= 3789);
    }

    public static boolean isMyanmarChar(char c) {
        return c >= 4096 && c <= 4247;
    }

    public static boolean isCombinedCode(char c) {
        return Character.isSurrogate(c) || isIndianChar(c) || isThaiChar(c) || isKhmerChar(c) || isMyanmarChar(c) || isLaoChar(c);
    }

    public static boolean semNeedMoreWidth(char c) {
        switch (c) {
            case 2305:
            case 2369:
            case 2370:
            case 2497:
            case 2498:
            case 2754:
            case SemExtendedFormat.DataType.SUPER_SLOW_MOTION_BGM /* 2817 */:
            case 2847:
            case 2893:
            case 2914:
            case 2915:
                return true;
            default:
                return false;
        }
    }

    public static int indexOfWordPrefix(CharSequence text, char[] prefix) {
        int textLength = text.length();
        int prefixLength = prefix.length;
        if (prefixLength == 0 || textLength < prefixLength) {
            return -1;
        }
        for (int i = 0; i < textLength && i + prefixLength <= textLength; i++) {
            int j = 0;
            while (j < prefixLength && Character.toUpperCase(text.charAt(i + j)) == Character.toUpperCase(prefix[j])) {
                j++;
            }
            if (j == prefixLength) {
                return i;
            }
        }
        return -1;
    }

    public static char[] semGetPrefixCharForSpan(TextPaint paint, CharSequence text, char[] prefix) {
        int pos;
        int len = text.length();
        if (len == 0 || prefix == null) {
            return null;
        }
        float[] widths = new float[len];
        char[] chars = new char[len];
        int i = 0;
        while (i < prefix.length && !isCombinedCode(prefix[i])) {
            i++;
        }
        if (i == prefix.length || (pos = indexOfWordPrefix(text, prefix)) < 0 || pos >= len) {
            return null;
        }
        getChars(text, 0, len, chars, 0);
        paint.getTextRunAdvances(chars, 0, len, 0, len, false, widths, 0);
        int pre_pos_halant = pos;
        if (isIndianChar(prefix[i])) {
            while (pre_pos_halant > 0 && isHalant(chars[pre_pos_halant - 1])) {
                pre_pos_halant -= 2;
            }
            if (pre_pos_halant < 0) {
                return null;
            }
        } else {
            while (pos > 0 && widths[pos] == 0.0f) {
                pos--;
            }
            pre_pos_halant = pos;
        }
        int i2 = prefix.length + pos;
        while (i2 < len && (widths[i2] == 0.0f || isHalant(chars[i2 - 1]))) {
            i2++;
        }
        int destLength = i2 - pre_pos_halant;
        char[] dest = new char[destLength];
        for (int j = 0; j < destLength; j++) {
            dest[j] = chars[pre_pos_halant + j];
        }
        return dest;
    }

    private static char[] hidden_semGetPrefixCharForSpan(TextPaint paint, CharSequence text, char[] prefix) {
        return semGetPrefixCharForSpan(paint, text, prefix);
    }
}

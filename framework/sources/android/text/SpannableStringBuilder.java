package android.text;

import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class SpannableStringBuilder implements CharSequence, GetChars, Spannable, Editable, Appendable, GraphicsOperations {
    private static final int END_MASK = 15;
    private static final int MARK = 1;
    private static final int PARAGRAPH = 3;
    private static final int POINT = 2;
    private static final int SPAN_ADDED = 2048;
    private static final int SPAN_END_AT_END = 32768;
    private static final int SPAN_END_AT_START = 16384;
    private static final int SPAN_START_AT_END = 8192;
    private static final int SPAN_START_AT_START = 4096;
    private static final int SPAN_START_END_MASK = 61440;
    private static final int START_MASK = 240;
    private static final int START_SHIFT = 4;
    private static final String TAG = "SpannableStringBuilder";
    private InputFilter[] mFilters;
    private int mGapLength;
    private int mGapStart;
    private IdentityHashMap<Object, Integer> mIndexOfSpan;
    private int mLowWaterMark;
    private int mSpanCount;
    private int[] mSpanEnds;
    private int[] mSpanFlags;
    private int mSpanInsertCount;
    private int[] mSpanMax;
    private int[] mSpanOrder;
    private int[] mSpanStarts;
    private Object[] mSpans;
    private char[] mText;
    private int mTextWatcherDepth;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int[][] sCachedIntBuffer = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 0);

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(CharSequence text) {
        this(text, 0, text.length());
    }

    public SpannableStringBuilder(CharSequence text, int start, int end) {
        int st;
        int en;
        this.mFilters = NO_FILTERS;
        int srclen = end - start;
        if (srclen < 0) {
            throw new StringIndexOutOfBoundsException();
        }
        char[] newUnpaddedCharArray = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(srclen));
        this.mText = newUnpaddedCharArray;
        this.mGapStart = srclen;
        this.mGapLength = newUnpaddedCharArray.length - srclen;
        TextUtils.getChars(text, start, end, newUnpaddedCharArray, 0);
        this.mSpanCount = 0;
        this.mSpanInsertCount = 0;
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanStarts = EmptyArray.INT;
        this.mSpanEnds = EmptyArray.INT;
        this.mSpanFlags = EmptyArray.INT;
        this.mSpanMax = EmptyArray.INT;
        this.mSpanOrder = EmptyArray.INT;
        if (text instanceof Spanned) {
            Spanned sp = (Spanned) text;
            Object[] spans = sp.getSpans(start, end, Object.class);
            for (int i = 0; i < spans.length; i++) {
                if (!(spans[i] instanceof NoCopySpan)) {
                    int st2 = sp.getSpanStart(spans[i]) - start;
                    int en2 = sp.getSpanEnd(spans[i]) - start;
                    int fl = sp.getSpanFlags(spans[i]);
                    st2 = st2 < 0 ? 0 : st2;
                    if (st2 <= end - start) {
                        st = st2;
                    } else {
                        st = end - start;
                    }
                    en2 = en2 < 0 ? 0 : en2;
                    if (en2 <= end - start) {
                        en = en2;
                    } else {
                        en = end - start;
                    }
                    setSpan(false, spans[i], st, en, fl, false);
                }
            }
            restoreInvariants();
        }
    }

    public static SpannableStringBuilder valueOf(CharSequence source) {
        if (source instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder) source;
        }
        return new SpannableStringBuilder(source);
    }

    @Override // java.lang.CharSequence
    public char charAt(int where) {
        int len = length();
        if (where < 0) {
            throw new IndexOutOfBoundsException("charAt: " + where + " < 0");
        }
        if (where >= len) {
            throw new IndexOutOfBoundsException("charAt: " + where + " >= length " + len);
        }
        if (where >= this.mGapStart) {
            return this.mText[this.mGapLength + where];
        }
        return this.mText[where];
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length - this.mGapLength;
    }

    private void resizeFor(int size) {
        int oldLength = this.mText.length;
        if (size + 1 <= oldLength) {
            return;
        }
        char[] newText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(size));
        System.arraycopy(this.mText, 0, newText, 0, this.mGapStart);
        int newLength = newText.length;
        int delta = newLength - oldLength;
        int after = oldLength - (this.mGapStart + this.mGapLength);
        System.arraycopy(this.mText, oldLength - after, newText, newLength - after, after);
        this.mText = newText;
        int i = this.mGapLength + delta;
        this.mGapLength = i;
        if (i < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        if (this.mSpanCount != 0) {
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                int[] iArr = this.mSpanStarts;
                int i3 = iArr[i2];
                int i4 = this.mGapStart;
                if (i3 > i4) {
                    iArr[i2] = i3 + delta;
                }
                int[] iArr2 = this.mSpanEnds;
                int i5 = iArr2[i2];
                if (i5 > i4) {
                    iArr2[i2] = i5 + delta;
                }
            }
            int i6 = treeRoot();
            calcMax(i6);
        }
    }

    private void moveGapTo(int where) {
        int flag;
        int flag2;
        if (where == this.mGapStart) {
            return;
        }
        boolean atEnd = where == length();
        int i = this.mGapStart;
        if (where < i) {
            int overlap = i - where;
            char[] cArr = this.mText;
            System.arraycopy(cArr, where, cArr, (i + this.mGapLength) - overlap, overlap);
        } else {
            int overlap2 = where - i;
            char[] cArr2 = this.mText;
            System.arraycopy(cArr2, (this.mGapLength + where) - overlap2, cArr2, i, overlap2);
        }
        if (this.mSpanCount != 0) {
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                int[] iArr = this.mSpanStarts;
                int start = iArr[i2];
                int[] iArr2 = this.mSpanEnds;
                int end = iArr2[i2];
                int i3 = this.mGapStart;
                if (start > i3) {
                    start -= this.mGapLength;
                }
                if (start > where) {
                    start += this.mGapLength;
                } else if (start == where && ((flag = (this.mSpanFlags[i2] & 240) >> 4) == 2 || (atEnd && flag == 3))) {
                    start += this.mGapLength;
                }
                if (end > i3) {
                    end -= this.mGapLength;
                }
                if (end > where) {
                    end += this.mGapLength;
                } else if (end == where && ((flag2 = this.mSpanFlags[i2] & 15) == 2 || (atEnd && flag2 == 3))) {
                    end += this.mGapLength;
                }
                iArr[i2] = start;
                iArr2[i2] = end;
            }
            int i4 = treeRoot();
            calcMax(i4);
        }
        this.mGapStart = where;
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int where, CharSequence tb, int start, int end) {
        return replace(where, where, tb, start, end);
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int where, CharSequence tb) {
        return replace(where, where, tb, 0, tb.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder delete(int start, int end) {
        SpannableStringBuilder ret = replace(start, end, "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return ret;
    }

    @Override // android.text.Editable
    public void clear() {
        replace(0, length(), "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable
    public void clearSpans() {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            Object[] objArr = this.mSpans;
            Object what = objArr[i];
            int ostart = this.mSpanStarts[i];
            int oend = this.mSpanEnds[i];
            int i2 = this.mGapStart;
            if (ostart > i2) {
                ostart -= this.mGapLength;
            }
            if (oend > i2) {
                oend -= this.mGapLength;
            }
            this.mSpanCount = i;
            objArr[i] = null;
            sendSpanRemoved(what, ostart, oend);
        }
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap != null) {
            identityHashMap.clear();
        }
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence text) {
        int length = length();
        return replace(length, length, text, 0, text.length());
    }

    public SpannableStringBuilder append(CharSequence text, Object what, int flags) {
        int start = length();
        append(text);
        setSpan(what, start, length(), flags);
        return this;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence text, int start, int end) {
        int length = length();
        return replace(length, length, text, start, end);
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(char text) {
        return append((CharSequence) String.valueOf(text));
    }

    private boolean removeSpansForChange(int start, int end, boolean textIsRemoved, int i) {
        int i2;
        int i3;
        if ((i & 1) != 0 && resolveGap(this.mSpanMax[i]) >= start && removeSpansForChange(start, end, textIsRemoved, leftChild(i))) {
            return true;
        }
        if (i >= this.mSpanCount) {
            return false;
        }
        if ((this.mSpanFlags[i] & 33) == 33 && (i2 = this.mSpanStarts[i]) >= start) {
            int i4 = this.mGapStart;
            int i5 = this.mGapLength;
            if (i2 < i4 + i5 && (i3 = this.mSpanEnds[i]) >= start && i3 < i5 + i4 && (textIsRemoved || i2 > start || i3 < i4)) {
                this.mIndexOfSpan.remove(this.mSpans[i]);
                removeSpan(i, 0);
                return true;
            }
        }
        return resolveGap(this.mSpanStarts[i]) <= end && (i & 1) != 0 && removeSpansForChange(start, end, textIsRemoved, rightChild(i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01a1, code lost:
    
        r11 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01a8, code lost:
    
        if (getSpanStart(r8[r9]) >= 0) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01aa, code lost:
    
        r12 = (r10 - r2) + r26;
        r19 = (r11 - r2) + r26;
        r6 = r7.getSpanFlags(r8[r9]) | 2048;
        setSpan(false, r8[r9], r12, r19, r6, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01cd, code lost:
    
        r9 = r9 + 1;
        r2 = r29;
        r4 = r30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01d4, code lost:
    
        restoreInvariants();
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01d7, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0119, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0173, code lost:
    
        r4 = r10;
        r2 = r11;
        r15 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00de, code lost:
    
        if (r16 > 0) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00e2, code lost:
    
        if (r25.mSpanCount <= 0) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ec, code lost:
    
        if (removeSpansForChange(r26, r27, r8, treeRoot()) == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ef, code lost:
    
        r25.mGapStart += r9;
        r1 = r25.mGapLength - r9;
        r25.mGapLength = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f9, code lost:
    
        if (r1 >= 1) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00fb, code lost:
    
        new java.lang.Exception("mGapLength < 1").printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0106, code lost:
    
        android.text.TextUtils.getChars(r12, r11, r10, r25.mText, r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x010b, code lost:
    
        if (r16 <= 0) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0115, code lost:
    
        if ((r25.mGapStart + r25.mGapLength) != r25.mText.length) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0117, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x011a, code lost:
    
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x011e, code lost:
    
        if (r7 >= r25.mSpanCount) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0120, code lost:
    
        r19 = (r25.mSpanFlags[r7] & 240) >> 4;
        r6 = r25.mSpanStarts;
        r6[r7] = updatedIntervalBound(r6[r7], r26, r9, r19, r5, r8);
        r0 = r25.mSpanFlags[r7] & 15;
        r1 = r25.mSpanEnds;
        r3 = r7;
        r20 = r8;
        r1[r3] = updatedIntervalBound(r1[r7], r26, r9, r0, r5, r20);
        r7 = r3 + 1;
        r11 = r11;
        r10 = r10;
        r12 = r12;
        r8 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0167, code lost:
    
        r4 = r10;
        r2 = r11;
        r15 = r12;
        restoreInvariants();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x017c, code lost:
    
        if ((r15 instanceof android.text.Spanned) == false) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x017e, code lost:
    
        r7 = (android.text.Spanned) r15;
        r8 = r7.getSpans(r2, r4, java.lang.Object.class);
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x018a, code lost:
    
        if (r9 >= r8.length) goto L232;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x018c, code lost:
    
        r0 = r7.getSpanStart(r8[r9]);
        r1 = r7.getSpanEnd(r8[r9]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0198, code lost:
    
        if (r0 >= r2) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x019a, code lost:
    
        r0 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x019c, code lost:
    
        r10 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x019d, code lost:
    
        if (r1 <= r4) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x019f, code lost:
    
        r1 = r30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void change(int r26, int r27, java.lang.CharSequence r28, int r29, int r30) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.change(int, int, java.lang.CharSequence, int, int):void");
    }

    private int updatedIntervalBound(int offset, int start, int nbNewChars, int flag, boolean atEnd, boolean textIsRemoved) {
        if (offset >= start) {
            int i = this.mGapStart;
            int i2 = this.mGapLength;
            if (offset < i + i2) {
                if (flag == 2) {
                    if (textIsRemoved || offset > start) {
                        return i + i2;
                    }
                } else if (flag == 3) {
                    if (atEnd) {
                        return i + i2;
                    }
                } else {
                    if (textIsRemoved || offset < i - nbNewChars) {
                        return start;
                    }
                    return i;
                }
            }
        }
        return offset;
    }

    private void removeSpan(int i, int flags) {
        Object[] objArr = this.mSpans;
        Object object = objArr[i];
        int start = this.mSpanStarts[i];
        int end = this.mSpanEnds[i];
        int i2 = this.mGapStart;
        if (start > i2) {
            start -= this.mGapLength;
        }
        if (end > i2) {
            end -= this.mGapLength;
        }
        int count = this.mSpanCount - (i + 1);
        System.arraycopy(objArr, i + 1, objArr, i, count);
        int[] iArr = this.mSpanStarts;
        System.arraycopy(iArr, i + 1, iArr, i, count);
        int[] iArr2 = this.mSpanEnds;
        System.arraycopy(iArr2, i + 1, iArr2, i, count);
        int[] iArr3 = this.mSpanFlags;
        System.arraycopy(iArr3, i + 1, iArr3, i, count);
        int[] iArr4 = this.mSpanOrder;
        System.arraycopy(iArr4, i + 1, iArr4, i, count);
        this.mSpanCount--;
        invalidateIndex(i);
        this.mSpans[this.mSpanCount] = null;
        restoreInvariants();
        if ((flags & 512) == 0) {
            sendSpanRemoved(object, start, end);
        }
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(int start, int end, CharSequence tb) {
        return replace(start, end, tb, 0, tb.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(int start, int end, CharSequence tb, int tbstart, int tbend) {
        int selectionStart;
        int selectionEnd;
        TextWatcher[] textWatchers;
        int selectionStart2;
        boolean changed;
        checkRange("replace", start, end);
        int filtercount = this.mFilters.length;
        CharSequence tb2 = tb;
        int tbstart2 = tbstart;
        int tbend2 = tbend;
        for (int i = 0; i < filtercount; i++) {
            CharSequence repl = this.mFilters[i].filter(tb2, tbstart2, tbend2, this, start, end);
            if (repl != null) {
                tb2 = repl;
                tbstart2 = 0;
                tbend2 = repl.length();
            }
        }
        int i2 = end - start;
        int newLen = tbend2 - tbstart2;
        if (i2 != 0 || newLen != 0 || hasNonExclusiveExclusiveSpanAt(tb2, tbstart2)) {
            TextWatcher[] textWatchers2 = (TextWatcher[]) getSpans(start, start + i2, TextWatcher.class);
            sendBeforeTextChanged(textWatchers2, start, i2, newLen);
            boolean adjustSelection = (i2 == 0 || newLen == 0) ? false : true;
            if (!adjustSelection) {
                selectionStart = 0;
                selectionEnd = 0;
            } else {
                int selectionStart3 = Selection.getSelectionStart(this);
                int selectionEnd2 = Selection.getSelectionEnd(this);
                selectionStart = selectionStart3;
                selectionEnd = selectionEnd2;
            }
            int selectionEnd3 = selectionEnd;
            int selectionEnd4 = tbstart2;
            int selectionStart4 = selectionStart;
            int selectionStart5 = tbend2;
            change(start, end, tb2, selectionEnd4, selectionStart5);
            if (adjustSelection) {
                boolean changed2 = false;
                if (selectionStart4 > start && selectionStart4 < end) {
                    long diff = selectionStart4 - start;
                    int offset = Math.toIntExact((newLen * diff) / i2);
                    selectionStart2 = start + offset;
                    textWatchers = textWatchers2;
                    setSpan(false, Selection.SELECTION_START, selectionStart2, selectionStart2, 34, true);
                    changed2 = true;
                } else {
                    textWatchers = textWatchers2;
                    selectionStart2 = selectionStart4;
                }
                if (selectionEnd3 > start && selectionEnd3 < end) {
                    long diff2 = selectionEnd3 - start;
                    int offset2 = Math.toIntExact((newLen * diff2) / i2);
                    int selectionEnd5 = start + offset2;
                    setSpan(false, Selection.SELECTION_END, selectionEnd5, selectionEnd5, 34, true);
                    changed = true;
                } else {
                    changed = changed2;
                }
                if (changed) {
                    restoreInvariants();
                }
            } else {
                textWatchers = textWatchers2;
            }
            sendTextChanged(textWatchers, start, i2, newLen);
            sendAfterTextChanged(textWatchers);
            sendToSpanWatchers(start, end, newLen - i2);
            return this;
        }
        return this;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(CharSequence text, int offset) {
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            Object[] spans = spanned.getSpans(offset, offset, Object.class);
            for (Object span : spans) {
                int flags = spanned.getSpanFlags(span);
                if (flags != 33) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendToSpanWatchers(int r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 192
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.sendToSpanWatchers(int, int, int):void");
    }

    @Override // android.text.Spannable
    public void setSpan(Object what, int start, int end, int flags) {
        setSpan(true, what, start, end, flags, true);
    }

    private void setSpan(boolean send, Object what, int start, int end, int flags, boolean enforceParagraph) {
        int start2;
        int end2;
        Integer index;
        int ostart;
        int oend;
        checkRange("setSpan", start, end);
        int flagsStart = (flags & 240) >> 4;
        if (isInvalidParagraph(start, flagsStart)) {
            if (!enforceParagraph) {
                return;
            } else {
                throw new RuntimeException("PARAGRAPH span must start at paragraph boundary (" + start + " follows " + charAt(start - 1) + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        int flagsEnd = flags & 15;
        if (isInvalidParagraph(end, flagsEnd)) {
            if (!enforceParagraph) {
                return;
            } else {
                throw new RuntimeException("PARAGRAPH span must end at paragraph boundary (" + end + " follows " + charAt(end - 1) + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        if (flagsStart == 2 && flagsEnd == 1 && start == end) {
            if (send) {
                Log.e(TAG, "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length");
                return;
            }
            return;
        }
        int i = this.mGapStart;
        if (start > i) {
            start2 = start + this.mGapLength;
        } else if (start == i && (flagsStart == 2 || (flagsStart == 3 && start == length()))) {
            start2 = start + this.mGapLength;
        } else {
            start2 = start;
        }
        int end3 = this.mGapStart;
        if (end > end3) {
            end2 = this.mGapLength + end;
        } else if (end == end3 && (flagsEnd == 2 || (flagsEnd == 3 && end == length()))) {
            end2 = this.mGapLength + end;
        } else {
            end2 = end;
        }
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (index = identityHashMap.get(what)) == null) {
            this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, what);
            this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, start2);
            this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, end2);
            this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, flags);
            this.mSpanOrder = GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
            invalidateIndex(this.mSpanCount);
            this.mSpanCount++;
            this.mSpanInsertCount++;
            int sizeOfMax = (treeRoot() * 2) + 1;
            if (this.mSpanMax.length < sizeOfMax) {
                this.mSpanMax = new int[sizeOfMax];
            }
            if (send) {
                restoreInvariants();
                sendSpanAdded(what, start, end);
                return;
            }
            return;
        }
        int i2 = index.intValue();
        int[] iArr = this.mSpanStarts;
        int ostart2 = iArr[i2];
        int[] iArr2 = this.mSpanEnds;
        int oend2 = iArr2[i2];
        int i3 = this.mGapStart;
        if (ostart2 <= i3) {
            ostart = ostart2;
        } else {
            ostart = ostart2 - this.mGapLength;
        }
        if (oend2 <= i3) {
            oend = oend2;
        } else {
            oend = oend2 - this.mGapLength;
        }
        iArr[i2] = start2;
        iArr2[i2] = end2;
        this.mSpanFlags[i2] = flags;
        if (send) {
            restoreInvariants();
            sendSpanChanged(what, ostart, oend, start, end);
        }
    }

    private boolean isInvalidParagraph(int index, int flag) {
        return (flag != 3 || index == 0 || index == length() || charAt(index + (-1)) == '\n') ? false : true;
    }

    @Override // android.text.Spannable
    public void removeSpan(Object what) {
        removeSpan(what, 0);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object what, int flags) {
        Integer i;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap != null && (i = identityHashMap.remove(what)) != null) {
            removeSpan(i.intValue(), flags);
        }
    }

    private int resolveGap(int i) {
        return i > this.mGapStart ? i - this.mGapLength : i;
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object what) {
        Integer i;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (i = identityHashMap.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanStarts[i.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object what) {
        Integer i;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (i = identityHashMap.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanEnds[i.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object what) {
        Integer i;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (i = identityHashMap.get(what)) == null) {
            return 0;
        }
        return this.mSpanFlags[i.intValue()];
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        return (T[]) getSpans(i, i2, cls, true);
    }

    public <T> T[] getSpans(int i, int i2, Class<T> cls, boolean z) {
        if (cls == null) {
            return (T[]) ArrayUtils.emptyArray(Object.class);
        }
        if (this.mSpanCount == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        int countSpans = countSpans(i, i2, cls, treeRoot());
        if (countSpans == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, countSpans));
        int[] obtain = z ? obtain(countSpans) : EmptyArray.INT;
        int[] obtain2 = z ? obtain(countSpans) : EmptyArray.INT;
        getSpansRec(i, i2, cls, treeRoot(), tArr, obtain, obtain2, 0, z);
        if (z) {
            sort(tArr, obtain, obtain2);
            recycle(obtain);
            recycle(obtain2);
        }
        return tArr;
    }

    private int countSpans(int queryStart, int queryEnd, Class kind, int i) {
        int count = 0;
        if ((i & 1) != 0) {
            int left = leftChild(i);
            int spanMax = this.mSpanMax[left];
            if (spanMax > this.mGapStart) {
                spanMax -= this.mGapLength;
            }
            if (spanMax >= queryStart) {
                count = countSpans(queryStart, queryEnd, kind, left);
            }
        }
        if (i < this.mSpanCount) {
            int spanStart = this.mSpanStarts[i];
            int i2 = this.mGapStart;
            if (spanStart > i2) {
                spanStart -= this.mGapLength;
            }
            if (spanStart <= queryEnd) {
                int spanEnd = this.mSpanEnds[i];
                if (spanEnd > i2) {
                    spanEnd -= this.mGapLength;
                }
                if (spanEnd >= queryStart && ((spanStart == spanEnd || queryStart == queryEnd || (spanStart != queryEnd && spanEnd != queryStart)) && (Object.class == kind || kind.isInstance(this.mSpans[i])))) {
                    count++;
                }
                if ((i & 1) != 0) {
                    return count + countSpans(queryStart, queryEnd, kind, rightChild(i));
                }
                return count;
            }
            return count;
        }
        return count;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> int getSpansRec(int r20, int r21, java.lang.Class<T> r22, int r23, T[] r24, int[] r25, int[] r26, int r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.getSpansRec(int, int, java.lang.Class, int, java.lang.Object[], int[], int[], int, boolean):int");
    }

    private static int[] obtain(int elementCount) {
        int[] result = null;
        int[][] iArr = sCachedIntBuffer;
        synchronized (iArr) {
            int candidateIndex = -1;
            int i = iArr.length - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                int[] iArr2 = sCachedIntBuffer[i];
                if (iArr2 != null) {
                    if (iArr2.length >= elementCount) {
                        candidateIndex = i;
                        break;
                    }
                    if (candidateIndex == -1) {
                        candidateIndex = i;
                    }
                }
                i--;
            }
            if (candidateIndex != -1) {
                int[][] iArr3 = sCachedIntBuffer;
                result = iArr3[candidateIndex];
                iArr3[candidateIndex] = null;
            }
        }
        return checkSortBuffer(result, elementCount);
    }

    private static void recycle(int[] buffer) {
        int[][] iArr;
        synchronized (sCachedIntBuffer) {
            int i = 0;
            while (true) {
                iArr = sCachedIntBuffer;
                if (i < iArr.length) {
                    int[] iArr2 = iArr[i];
                    if (iArr2 == null || buffer.length > iArr2.length) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    break;
                }
            }
            iArr[i] = buffer;
        }
    }

    private static int[] checkSortBuffer(int[] buffer, int size) {
        if (buffer == null || size > buffer.length) {
            return ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(size));
        }
        return buffer;
    }

    private final <T> void sort(T[] array, int[] priority, int[] insertionOrder) {
        int size = array.length;
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i, array, size, priority, insertionOrder);
        }
        for (int i2 = size - 1; i2 > 0; i2--) {
            T tmpSpan = array[0];
            array[0] = array[i2];
            array[i2] = tmpSpan;
            int tmpPriority = priority[0];
            priority[0] = priority[i2];
            priority[i2] = tmpPriority;
            int tmpOrder = insertionOrder[0];
            insertionOrder[0] = insertionOrder[i2];
            insertionOrder[i2] = tmpOrder;
            siftDown(0, array, i2, priority, insertionOrder);
        }
    }

    private final <T> void siftDown(int index, T[] array, int size, int[] priority, int[] insertionOrder) {
        int left = (index * 2) + 1;
        while (left < size) {
            if (left < size - 1 && compareSpans(left, left + 1, priority, insertionOrder) < 0) {
                left++;
            }
            if (compareSpans(index, left, priority, insertionOrder) < 0) {
                T tmpSpan = array[index];
                array[index] = array[left];
                array[left] = tmpSpan;
                int tmpPriority = priority[index];
                priority[index] = priority[left];
                priority[left] = tmpPriority;
                int tmpOrder = insertionOrder[index];
                insertionOrder[index] = insertionOrder[left];
                insertionOrder[left] = tmpOrder;
                index = left;
                left = (index * 2) + 1;
            } else {
                return;
            }
        }
    }

    private final int compareSpans(int left, int right, int[] priority, int[] insertionOrder) {
        int priority1 = priority[left];
        int priority2 = priority[right];
        if (priority1 == priority2) {
            return Integer.compare(insertionOrder[left], insertionOrder[right]);
        }
        return Integer.compare(priority2, priority1);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int start, int limit, Class kind) {
        if (this.mSpanCount == 0) {
            return limit;
        }
        if (kind == null) {
            kind = Object.class;
        }
        return nextSpanTransitionRec(start, limit, kind, treeRoot());
    }

    private int nextSpanTransitionRec(int start, int limit, Class kind, int i) {
        if ((i & 1) != 0) {
            int left = leftChild(i);
            if (resolveGap(this.mSpanMax[left]) > start) {
                limit = nextSpanTransitionRec(start, limit, kind, left);
            }
        }
        if (i < this.mSpanCount) {
            int st = resolveGap(this.mSpanStarts[i]);
            int en = resolveGap(this.mSpanEnds[i]);
            if (st > start && st < limit && kind.isInstance(this.mSpans[i])) {
                limit = st;
            }
            if (en > start && en < limit && kind.isInstance(this.mSpans[i])) {
                limit = en;
            }
            if (st < limit && (i & 1) != 0) {
                return nextSpanTransitionRec(start, limit, kind, rightChild(i));
            }
            return limit;
        }
        return limit;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return new SpannableStringBuilder(this, start, end);
    }

    @Override // android.text.GetChars
    public void getChars(int start, int end, char[] dest, int destoff) {
        checkRange("getChars", start, end);
        int i = this.mGapStart;
        if (end <= i) {
            System.arraycopy(this.mText, start, dest, destoff, end - start);
            return;
        }
        if (start >= i) {
            System.arraycopy(this.mText, this.mGapLength + start, dest, destoff, end - start);
            return;
        }
        System.arraycopy(this.mText, start, dest, destoff, i - start);
        char[] cArr = this.mText;
        int i2 = this.mGapStart;
        System.arraycopy(cArr, this.mGapLength + i2, dest, (i2 - start) + destoff, end - i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        int len = length();
        char[] buf = new char[len];
        getChars(0, len, buf, 0);
        return new String(buf);
    }

    public String substring(int start, int end) {
        char[] buf = new char[end - start];
        getChars(start, end, buf, 0);
        return new String(buf);
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.beforeTextChanged(this, start, before, after);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.onTextChanged(this, start, before, after);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendAfterTextChanged(TextWatcher[] watchers) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.afterTextChanged(this);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendSpanAdded(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanAdded(this, what, start, end);
        }
    }

    private void sendSpanRemoved(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanRemoved(this, what, start, end);
        }
    }

    private void sendSpanChanged(Object what, int oldStart, int oldEnd, int start, int end) {
        SpanWatcher[] spanWatchers = (SpanWatcher[]) getSpans(Math.min(oldStart, start), Math.min(Math.max(oldEnd, end), length()), SpanWatcher.class);
        for (SpanWatcher spanWatcher : spanWatchers) {
            spanWatcher.onSpanChanged(this, what, oldStart, oldEnd, start, end);
        }
    }

    private static String region(int start, int end) {
        return NavigationBarInflaterView.KEY_CODE_START + start + " ... " + end + NavigationBarInflaterView.KEY_CODE_END;
    }

    private void checkRange(String operation, int start, int end) {
        if (end < start) {
            throw new IndexOutOfBoundsException(operation + " " + region(start, end) + " has end before start");
        }
        int len = length();
        if (start > len || end > len) {
            throw new IndexOutOfBoundsException(operation + " " + region(start, end) + " ends beyond length " + len);
        }
        if (start < 0 || end < 0) {
            throw new IndexOutOfBoundsException(operation + " " + region(start, end) + " starts before 0");
        }
    }

    @Override // android.text.GraphicsOperations
    public void drawText(BaseCanvas c, int start, int end, float x, float y, Paint p) {
        checkRange("drawText", start, end);
        int i = this.mGapStart;
        if (end <= i) {
            c.drawText(this.mText, start, end - start, x, y, p);
            return;
        }
        if (start >= i) {
            c.drawText(this.mText, start + this.mGapLength, end - start, x, y, p);
            return;
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        c.drawText(buf, 0, end - start, x, y, p);
        TextUtils.recycle(buf);
    }

    @Override // android.text.GraphicsOperations
    public void drawTextRun(BaseCanvas c, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint p) {
        checkRange("drawTextRun", start, end);
        int contextLen = contextEnd - contextStart;
        int len = end - start;
        int i = this.mGapStart;
        if (contextEnd <= i) {
            c.drawTextRun(this.mText, start, len, contextStart, contextLen, x, y, isRtl, p);
            return;
        }
        if (contextStart >= i) {
            char[] cArr = this.mText;
            int i2 = this.mGapLength;
            c.drawTextRun(cArr, start + i2, len, contextStart + i2, contextLen, x, y, isRtl, p);
        } else {
            char[] buf = TextUtils.obtain(contextLen);
            getChars(contextStart, contextEnd, buf, 0);
            c.drawTextRun(buf, start - contextStart, len, 0, contextLen, x, y, isRtl, p);
            TextUtils.recycle(buf);
        }
    }

    @Override // android.text.GraphicsOperations
    public float measureText(int start, int end, Paint p) {
        checkRange("measureText", start, end);
        int i = this.mGapStart;
        if (end <= i) {
            float ret = p.measureText(this.mText, start, end - start);
            return ret;
        }
        if (start >= i) {
            float ret2 = p.measureText(this.mText, this.mGapLength + start, end - start);
            return ret2;
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        float ret3 = p.measureText(buf, 0, end - start);
        TextUtils.recycle(buf);
        return ret3;
    }

    @Override // android.text.GraphicsOperations
    public int getTextWidths(int start, int end, float[] widths, Paint p) {
        checkRange("getTextWidths", start, end);
        int ret = this.mGapStart;
        if (end <= ret) {
            return p.getTextWidths(this.mText, start, end - start, widths);
        }
        if (start >= ret) {
            return p.getTextWidths(this.mText, this.mGapLength + start, end - start, widths);
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        int ret2 = p.getTextWidths(buf, 0, end - start, widths);
        TextUtils.recycle(buf);
        return ret2;
    }

    @Override // android.text.GraphicsOperations
    public float getTextRunAdvances(int start, int end, int contextStart, int contextEnd, boolean isRtl, float[] advances, int advancesPos, Paint p) {
        int contextLen = contextEnd - contextStart;
        int len = end - start;
        int i = this.mGapStart;
        if (end <= i) {
            float ret = p.getTextRunAdvances(this.mText, start, len, contextStart, contextLen, isRtl, advances, advancesPos);
            return ret;
        }
        if (start >= i) {
            char[] cArr = this.mText;
            int i2 = this.mGapLength;
            float ret2 = p.getTextRunAdvances(cArr, start + i2, len, contextStart + i2, contextLen, isRtl, advances, advancesPos);
            return ret2;
        }
        char[] buf = TextUtils.obtain(contextLen);
        getChars(contextStart, contextEnd, buf, 0);
        float ret3 = p.getTextRunAdvances(buf, start - contextStart, len, 0, contextLen, isRtl, advances, advancesPos);
        TextUtils.recycle(buf);
        return ret3;
    }

    @Deprecated
    public int getTextRunCursor(int contextStart, int contextEnd, int dir, int offset, int cursorOpt, Paint p) {
        return getTextRunCursor(contextStart, contextEnd, dir == 1, offset, cursorOpt, p);
    }

    @Override // android.text.GraphicsOperations
    public int getTextRunCursor(int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt, Paint p) {
        int contextLen = contextEnd - contextStart;
        int ret = this.mGapStart;
        if (contextEnd <= ret) {
            return p.getTextRunCursor(this.mText, contextStart, contextLen, isRtl, offset, cursorOpt);
        }
        if (contextStart >= ret) {
            char[] cArr = this.mText;
            int i = this.mGapLength;
            return p.getTextRunCursor(cArr, contextStart + i, contextLen, isRtl, offset + i, cursorOpt) - this.mGapLength;
        }
        char[] buf = TextUtils.obtain(contextLen);
        getChars(contextStart, contextEnd, buf, 0);
        int ret2 = p.getTextRunCursor(buf, 0, contextLen, isRtl, offset - contextStart, cursorOpt) + contextStart;
        TextUtils.recycle(buf);
        return ret2;
    }

    @Override // android.text.Editable
    public void setFilters(InputFilter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException();
        }
        this.mFilters = filters;
    }

    @Override // android.text.Editable
    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    public boolean equals(Object o) {
        if ((o instanceof Spanned) && toString().equals(o.toString())) {
            Spanned other = (Spanned) o;
            Object[] otherSpans = other.getSpans(0, other.length(), Object.class);
            Object[] thisSpans = getSpans(0, length(), Object.class);
            if (this.mSpanCount == otherSpans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    Object thisSpan = thisSpans[i];
                    Object otherSpan = otherSpans[i];
                    if (thisSpan == this) {
                        if (other != otherSpan || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                            return false;
                        }
                    } else if (!thisSpan.equals(otherSpan) || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = toString().hashCode();
        int hash2 = (hash * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object span = this.mSpans[i];
            if (span != this) {
                hash2 = (hash2 * 31) + span.hashCode();
            }
            hash2 = (((((hash2 * 31) + getSpanStart(span)) * 31) + getSpanEnd(span)) * 31) + getSpanFlags(span);
        }
        return hash2;
    }

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private static int leftChild(int i) {
        return i - (((i + 1) & (~i)) >> 1);
    }

    private static int rightChild(int i) {
        return (((i + 1) & (~i)) >> 1) + i;
    }

    private int calcMax(int i) {
        int max = 0;
        if ((i & 1) != 0) {
            max = calcMax(leftChild(i));
        }
        if (i < this.mSpanCount) {
            max = Math.max(max, this.mSpanEnds[i]);
            if ((i & 1) != 0) {
                max = Math.max(max, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = max;
        return max;
    }

    private void restoreInvariants() {
        Object[] objArr;
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        if (this.mSpanCount == 0) {
            return;
        }
        for (int i = 1; i < this.mSpanCount; i++) {
            int[] iArr5 = this.mSpanStarts;
            if (iArr5[i] < iArr5[i - 1]) {
                Object span = this.mSpans[i];
                int start = iArr5[i];
                int end = this.mSpanEnds[i];
                int flags = this.mSpanFlags[i];
                int insertionOrder = this.mSpanOrder[i];
                int j = i;
                do {
                    objArr = this.mSpans;
                    objArr[j] = objArr[j - 1];
                    iArr = this.mSpanStarts;
                    iArr[j] = iArr[j - 1];
                    iArr2 = this.mSpanEnds;
                    iArr2[j] = iArr2[j - 1];
                    iArr3 = this.mSpanFlags;
                    iArr3[j] = iArr3[j - 1];
                    iArr4 = this.mSpanOrder;
                    iArr4[j] = iArr4[j - 1];
                    j--;
                    if (j <= 0) {
                        break;
                    }
                } while (start < iArr[j - 1]);
                objArr[j] = span;
                iArr[j] = start;
                iArr2[j] = end;
                iArr3[j] = flags;
                iArr4[j] = insertionOrder;
                invalidateIndex(j);
            }
        }
        int i2 = treeRoot();
        calcMax(i2);
        if (this.mIndexOfSpan == null) {
            this.mIndexOfSpan = new IdentityHashMap<>();
        }
        for (int i3 = this.mLowWaterMark; i3 < this.mSpanCount; i3++) {
            Integer existing = this.mIndexOfSpan.get(this.mSpans[i3]);
            if (existing == null || existing.intValue() != i3) {
                this.mIndexOfSpan.put(this.mSpans[i3], Integer.valueOf(i3));
            }
        }
        this.mLowWaterMark = Integer.MAX_VALUE;
    }

    private void invalidateIndex(int i) {
        this.mLowWaterMark = Math.min(i, this.mLowWaterMark);
    }
}

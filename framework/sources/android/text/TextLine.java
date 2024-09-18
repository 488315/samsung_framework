package android.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.text.PositionedGlyphs;
import android.graphics.text.TextRunShaper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Layout;
import android.text.TextShaper;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    private static final TextLine[] sCached = new TextLine[3];
    private float mAddedWidthForJustify;
    private char[] mChars;
    private boolean mCharsValid;
    private PrecomputedText mComputed;
    private int mDir;
    private Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    private TextPaint mPaint;
    private Spanned mSpanned;
    private int mStart;
    private Layout.TabStops mTabs;
    private CharSequence mText;
    private boolean mUseFallbackExtent = false;
    private final TextPaint mWorkPaint = new TextPaint();
    private final TextPaint mActivePaint = new TextPaint();
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet = new SpanSet<>(MetricAffectingSpan.class);
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<>(CharacterStyle.class);
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet = new SpanSet<>(ReplacementSpan.class);
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList<>();

    public static TextLine obtain() {
        TextLine[] textLineArr;
        TextLine tl;
        TextLine[] textLineArr2 = sCached;
        synchronized (textLineArr2) {
            int i = textLineArr2.length;
            do {
                i--;
                if (i >= 0) {
                    textLineArr = sCached;
                    tl = textLineArr[i];
                } else {
                    return new TextLine();
                }
            } while (tl == null);
            textLineArr[i] = null;
            return tl;
        }
    }

    public static TextLine recycle(TextLine tl) {
        tl.mText = null;
        tl.mPaint = null;
        tl.mDirections = null;
        tl.mSpanned = null;
        tl.mTabs = null;
        tl.mChars = null;
        tl.mComputed = null;
        tl.mUseFallbackExtent = false;
        tl.mMetricAffectingSpanSpanSet.recycle();
        tl.mCharacterStyleSpanSet.recycle();
        tl.mReplacementSpanSpanSet.recycle();
        synchronized (sCached) {
            int i = 0;
            while (true) {
                TextLine[] textLineArr = sCached;
                if (i >= textLineArr.length) {
                    break;
                }
                if (textLineArr[i] != null) {
                    i++;
                } else {
                    textLineArr[i] = tl;
                    break;
                }
            }
        }
        return null;
    }

    public void set(TextPaint paint, CharSequence text, int start, int limit, int dir, Layout.Directions directions, boolean hasTabs, Layout.TabStops tabStops, int ellipsisStart, int ellipsisEnd, boolean useFallbackLineSpacing) {
        this.mPaint = paint;
        this.mText = text;
        this.mStart = start;
        this.mLen = limit - start;
        this.mDir = dir;
        this.mDirections = directions;
        this.mUseFallbackExtent = useFallbackLineSpacing;
        if (directions == null) {
            throw new IllegalArgumentException("Directions cannot be null");
        }
        this.mHasTabs = hasTabs;
        this.mSpanned = null;
        boolean hasReplacement = false;
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            this.mSpanned = spanned;
            this.mReplacementSpanSpanSet.init(spanned, start, limit);
            hasReplacement = this.mReplacementSpanSpanSet.numberOfSpans > 0;
        }
        this.mComputed = null;
        if (text instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText) text;
            this.mComputed = precomputedText;
            if (!precomputedText.getParams().getTextPaint().equalsForTextMeasurement(paint)) {
                this.mComputed = null;
            }
        }
        this.mCharsValid = hasReplacement;
        if (hasReplacement) {
            char[] cArr = this.mChars;
            if (cArr == null || cArr.length < this.mLen) {
                this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
            }
            TextUtils.getChars(text, start, limit, this.mChars, 0);
            if (hasReplacement) {
                char[] chars = this.mChars;
                int i = start;
                while (i < limit) {
                    int inext = this.mReplacementSpanSpanSet.getNextTransition(i, limit);
                    if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i, inext) && (i - start >= ellipsisEnd || inext - start <= ellipsisStart)) {
                        chars[i - start] = 65532;
                        int e = inext - start;
                        for (int j = (i - start) + 1; j < e; j++) {
                            chars[j] = 65279;
                        }
                    }
                    i = inext;
                }
            }
        }
        this.mTabs = tabStops;
        this.mAddedWidthForJustify = 0.0f;
        this.mIsJustifying = false;
        this.mEllipsisStart = ellipsisStart != ellipsisEnd ? ellipsisStart : 0;
        this.mEllipsisEnd = ellipsisStart != ellipsisEnd ? ellipsisEnd : 0;
    }

    private char charAt(int i) {
        return this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i);
    }

    public void justify(float justifyWidth) {
        int end = this.mLen;
        while (end > 0 && isLineEndSpace(this.mText.charAt((this.mStart + end) - 1))) {
            end--;
        }
        int spaces = countStretchableSpaces(0, end);
        if (spaces != 0) {
            float width = Math.abs(measure(end, false, null));
            this.mAddedWidthForJustify = (justifyWidth - width) / spaces;
            this.mIsJustifying = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void draw(Canvas c, float x, int top, int y, int bottom) {
        int runCount;
        int runCount2;
        float h = 0.0f;
        int j = this.mDirections.getRunCount();
        int runIndex = 0;
        while (runIndex < j) {
            int runStart = this.mDirections.getRunStart(runIndex);
            if (runStart <= this.mLen) {
                int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
                boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
                float h2 = h;
                int segStart = runStart;
                int j2 = this.mHasTabs ? runStart : runLimit;
                while (j2 <= runLimit) {
                    if (j2 == runLimit || charAt(j2) == '\t') {
                        float f = x + h2;
                        boolean z = (runIndex == j + (-1) && j2 == this.mLen) ? false : true;
                        runCount = j;
                        runCount2 = j2;
                        h2 += drawRun(c, segStart, j2, runIsRtl, f, top, y, bottom, z);
                        if (runCount2 != runLimit) {
                            int i = this.mDir;
                            h2 = i * nextTab(i * h2);
                        }
                        segStart = runCount2 + 1;
                    } else {
                        runCount = j;
                        runCount2 = j2;
                    }
                    j2 = runCount2 + 1;
                    j = runCount;
                }
                int runCount3 = j;
                runIndex++;
                h = h2;
                j = runCount3;
            } else {
                return;
            }
        }
    }

    public float metrics(Paint.FontMetricsInt fmi) {
        return measure(this.mLen, false, fmi);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shape(TextShaper.GlyphsConsumer consumer) {
        float x;
        int j;
        float horizontal = 0.0f;
        float j2 = 0.0f;
        int runCount = this.mDirections.getRunCount();
        int runIndex = 0;
        while (runIndex < runCount) {
            int runStart = this.mDirections.getRunStart(runIndex);
            if (runStart <= this.mLen) {
                int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
                boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
                float horizontal2 = horizontal;
                int segStart = runStart;
                int j3 = this.mHasTabs ? runStart : runLimit;
                while (j3 <= runLimit) {
                    if (j3 == runLimit || charAt(j3) == '\t') {
                        float f = j2 + horizontal2;
                        x = j2;
                        j = j3;
                        horizontal2 += shapeRun(consumer, segStart, j3, runIsRtl, f, (runIndex == runCount + (-1) && j3 == this.mLen) ? false : true);
                        if (j != runLimit) {
                            int i = this.mDir;
                            horizontal2 = i * nextTab(i * horizontal2);
                        }
                        segStart = j + 1;
                    } else {
                        x = j2;
                        j = j3;
                    }
                    j3 = j + 1;
                    j2 = x;
                }
                float x2 = j2;
                runIndex++;
                horizontal = horizontal2;
                j2 = x2;
            } else {
                return;
            }
        }
    }

    public float measure(int offset, boolean trailing, Paint.FontMetricsInt fmi) {
        int runStart;
        int j;
        if (offset > this.mLen) {
            throw new IndexOutOfBoundsException("offset(" + offset + ") should be less than line limit(" + this.mLen + NavigationBarInflaterView.KEY_CODE_END);
        }
        int target = trailing ? offset - 1 : offset;
        if (target < 0) {
            return 0.0f;
        }
        float h = 0.0f;
        int runIndex = 0;
        while (runIndex < this.mDirections.getRunCount() && (runStart = this.mDirections.getRunStart(runIndex)) <= this.mLen) {
            int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
            boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
            float h2 = h;
            int segStart = runStart;
            int j2 = this.mHasTabs ? runStart : runLimit;
            while (j2 <= runLimit) {
                if (j2 == runLimit || charAt(j2) == '\t') {
                    boolean targetIsInThisSegment = target >= segStart && target < j2;
                    boolean sameDirection = (this.mDir == -1) == runIsRtl;
                    if (!targetIsInThisSegment || !sameDirection) {
                        int j3 = j2;
                        int segStart2 = segStart;
                        float segmentWidth = measureRun(segStart2, j3, j3, runIsRtl, fmi, null, 0);
                        h2 += sameDirection ? segmentWidth : -segmentWidth;
                        if (targetIsInThisSegment) {
                            return measureRun(segStart2, offset, j3, runIsRtl, null, null, 0) + h2;
                        }
                        j = j3;
                        if (j != runLimit) {
                            if (offset == j) {
                                return h2;
                            }
                            int i = this.mDir;
                            float h3 = i * nextTab(i * h2);
                            if (target != j) {
                                h2 = h3;
                            } else {
                                return h3;
                            }
                        }
                        segStart = j + 1;
                    } else {
                        return measureRun(segStart, offset, j2, runIsRtl, fmi, null, 0) + h2;
                    }
                } else {
                    j = j2;
                }
                j2 = j + 1;
            }
            runIndex++;
            h = h2;
        }
        return h;
    }

    public void measureAllBounds(float[] bounds, float[] advances) {
        float[] advances2;
        int j;
        int runStart;
        int runStart2;
        float leftX;
        float h;
        float rightX;
        if (bounds == null) {
            throw new IllegalArgumentException("bounds can't be null");
        }
        int length = bounds.length;
        int i = this.mLen;
        if (length < i * 2) {
            throw new IndexOutOfBoundsException("bounds doesn't have enough space to receive the result, needed: " + (this.mLen * 2) + " had: " + bounds.length);
        }
        if (advances != null) {
            advances2 = advances;
        } else {
            advances2 = new float[i];
        }
        if (advances2.length < i) {
            throw new IndexOutOfBoundsException("advance doesn't have enough space to receive the result, needed: " + this.mLen + " had: " + advances2.length);
        }
        float h2 = 0.0f;
        int runIndex = 0;
        while (runIndex < this.mDirections.getRunCount() && (j = this.mDirections.getRunStart(runIndex)) <= this.mLen) {
            int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + j, this.mLen);
            boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
            float h3 = h2;
            int segStart = j;
            int j2 = this.mHasTabs ? j : runLimit;
            while (j2 <= runLimit) {
                if (j2 == runLimit || charAt(j2) == '\t') {
                    boolean sameDirection = (this.mDir == -1) == runIsRtl;
                    runStart = j;
                    runStart2 = j2;
                    float segmentWidth = measureRun(segStart, j2, j2, runIsRtl, null, advances2, segStart);
                    float oldh = h3;
                    h3 += sameDirection ? segmentWidth : -segmentWidth;
                    float currh = sameDirection ? oldh : h3;
                    for (int offset = segStart; offset < runStart2 && offset < this.mLen; offset++) {
                        if (runIsRtl) {
                            bounds[(offset * 2) + 1] = currh;
                            currh -= advances2[offset];
                            bounds[offset * 2] = currh;
                        } else {
                            bounds[offset * 2] = currh;
                            currh += advances2[offset];
                            bounds[(offset * 2) + 1] = currh;
                        }
                    }
                    if (runStart2 != runLimit) {
                        if (runIsRtl) {
                            rightX = h3;
                            int i2 = this.mDir;
                            h = i2 * nextTab(i2 * h3);
                            leftX = h;
                        } else {
                            leftX = h3;
                            int i3 = this.mDir;
                            h = i3 * nextTab(i3 * h3);
                            rightX = h;
                        }
                        bounds[runStart2 * 2] = leftX;
                        bounds[(runStart2 * 2) + 1] = rightX;
                        advances2[runStart2] = rightX - leftX;
                        h3 = h;
                    }
                    segStart = runStart2 + 1;
                } else {
                    runStart = j;
                    runStart2 = j2;
                }
                j2 = runStart2 + 1;
                j = runStart;
            }
            runIndex++;
            h2 = h3;
        }
    }

    public float[] measureAllOffsets(boolean[] trailing, Paint.FontMetricsInt fmi) {
        int runStart;
        int j;
        boolean z = true;
        float[] measurement = new float[this.mLen + 1];
        if (trailing[0]) {
            measurement[0] = 0.0f;
        }
        float horizontal = 0.0f;
        int runIndex = 0;
        while (runIndex < this.mDirections.getRunCount() && (runStart = this.mDirections.getRunStart(runIndex)) <= this.mLen) {
            int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
            boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
            float horizontal2 = horizontal;
            int segStart = runStart;
            int j2 = this.mHasTabs ? runStart : runLimit;
            while (j2 <= runLimit) {
                if (j2 == runLimit || charAt(j2) == '\t') {
                    float oldHorizontal = horizontal2;
                    boolean sameDirection = (this.mDir == -1 ? z : false) == runIsRtl ? z : false;
                    float previousSegEndHorizontal = measurement[segStart];
                    j = j2;
                    int segStart2 = segStart;
                    float width = measureRun(segStart, j2, j2, runIsRtl, fmi, measurement, segStart);
                    horizontal2 += sameDirection ? width : -width;
                    float currHorizontal = sameDirection ? oldHorizontal : horizontal2;
                    int segLimit = Math.min(j, this.mLen);
                    int offset = segStart2;
                    while (offset <= segLimit) {
                        float advance = 0.0f;
                        if (offset < segLimit) {
                            advance = runIsRtl ? -measurement[offset] : measurement[offset];
                        }
                        int segStart3 = segStart2;
                        if (offset == segStart3 && trailing[offset]) {
                            measurement[offset] = previousSegEndHorizontal;
                        } else if (offset != segLimit || trailing[offset]) {
                            measurement[offset] = currHorizontal;
                        }
                        currHorizontal += advance;
                        offset++;
                        segStart2 = segStart3;
                    }
                    if (j != runLimit) {
                        if (!trailing[j]) {
                            measurement[j] = horizontal2;
                        }
                        int i = this.mDir;
                        float horizontal3 = i * nextTab(i * horizontal2);
                        if (trailing[j + 1]) {
                            measurement[j + 1] = horizontal3;
                        }
                        horizontal2 = horizontal3;
                    }
                    segStart = j + 1;
                } else {
                    j = j2;
                }
                j2 = j + 1;
                z = true;
            }
            runIndex++;
            horizontal = horizontal2;
            z = true;
        }
        int i2 = this.mLen;
        if (!trailing[i2]) {
            measurement[i2] = horizontal;
        }
        return measurement;
    }

    private float drawRun(Canvas c, int start, int limit, boolean runIsRtl, float x, int top, int y, int bottom, boolean needWidth) {
        if ((this.mDir == 1) == runIsRtl) {
            float w = -measureRun(start, limit, limit, runIsRtl, null, null, 0);
            handleRun(start, limit, limit, runIsRtl, c, null, x + w, top, y, bottom, null, false, null, 0);
            return w;
        }
        return handleRun(start, limit, limit, runIsRtl, c, null, x, top, y, bottom, null, needWidth, null, 0);
    }

    private float measureRun(int start, int offset, int limit, boolean runIsRtl, Paint.FontMetricsInt fmi, float[] advances, int advancesIndex) {
        return handleRun(start, offset, limit, runIsRtl, null, null, 0.0f, 0, 0, 0, fmi, true, advances, advancesIndex);
    }

    private float shapeRun(TextShaper.GlyphsConsumer consumer, int start, int limit, boolean runIsRtl, float x, boolean needWidth) {
        if ((this.mDir == 1) == runIsRtl) {
            float w = -measureRun(start, limit, limit, runIsRtl, null, null, 0);
            handleRun(start, limit, limit, runIsRtl, null, consumer, x + w, 0, 0, 0, null, false, null, 0);
            return w;
        }
        return handleRun(start, limit, limit, runIsRtl, null, consumer, x, 0, 0, 0, null, needWidth, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0186, code lost:            r8 = r6;        r1 = -1;     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x018c, code lost:            if (r8 != (-1)) goto L115;     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x018e, code lost:            if (r0 == false) goto L114;     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0190, code lost:            r1 = r27.mLen + 1;     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0194, code lost:            r6 = r1;     */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:            return r6;     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0196, code lost:            if (r8 > r11) goto L120;     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0198, code lost:            if (r0 == false) goto L118;     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x019a, code lost:            r1 = r11;     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x019d, code lost:            r6 = r1;     */
    /* JADX WARN: Code restructure failed: missing block: B:63:?, code lost:            return r6;     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019c, code lost:            r1 = 0;     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getOffsetToLeftRightOf(int r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.getOffsetToLeftRightOf(int, boolean):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getOffsetBeforeAfter(int r20, int r21, int r22, boolean r23, int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.getOffsetBeforeAfter(int, int, int, boolean, int, boolean):int");
    }

    private static void expandMetricsFromPaint(Paint.FontMetricsInt fmi, TextPaint wp) {
        int previousTop = fmi.top;
        int previousAscent = fmi.ascent;
        int previousDescent = fmi.descent;
        int previousBottom = fmi.bottom;
        int previousLeading = fmi.leading;
        wp.getFontMetricsInt(fmi);
        updateMetrics(fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
    }

    private void expandMetricsFromPaint(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, Paint.FontMetricsInt fmi) {
        int previousTop = fmi.top;
        int previousAscent = fmi.ascent;
        int previousDescent = fmi.descent;
        int previousBottom = fmi.bottom;
        int previousLeading = fmi.leading;
        int count = end - start;
        int contextCount = contextEnd - contextStart;
        if (this.mCharsValid) {
            wp.getFontMetricsInt(this.mChars, start, count, contextStart, contextCount, runIsRtl, fmi);
        } else {
            PrecomputedText precomputedText = this.mComputed;
            if (precomputedText == null) {
                CharSequence charSequence = this.mText;
                int i = this.mStart;
                wp.getFontMetricsInt(charSequence, i + start, count, i + contextStart, contextCount, runIsRtl, fmi);
            } else {
                int i2 = this.mStart;
                precomputedText.getFontMetricsInt(i2 + start, i2 + end, fmi);
            }
        }
        updateMetrics(fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
    }

    static void updateMetrics(Paint.FontMetricsInt fmi, int previousTop, int previousAscent, int previousDescent, int previousBottom, int previousLeading) {
        fmi.top = Math.min(fmi.top, previousTop);
        fmi.ascent = Math.min(fmi.ascent, previousAscent);
        fmi.descent = Math.max(fmi.descent, previousDescent);
        fmi.bottom = Math.max(fmi.bottom, previousBottom);
        fmi.leading = Math.max(fmi.leading, previousLeading);
    }

    private static void drawStroke(TextPaint wp, Canvas c, int color, float position, float thickness, float xleft, float xright, float baseline) {
        float strokeTop = baseline + wp.baselineShift + position;
        int previousColor = wp.getColor();
        Paint.Style previousStyle = wp.getStyle();
        boolean previousAntiAlias = wp.isAntiAlias();
        wp.setStyle(Paint.Style.FILL);
        wp.setAntiAlias(true);
        wp.setColor(color);
        c.drawRect(xleft, strokeTop, xright, strokeTop + thickness, wp);
        wp.setStyle(previousStyle);
        wp.setColor(previousColor);
        wp.setAntiAlias(previousAntiAlias);
    }

    private float getRunAdvance(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, int offset, float[] advances, int advancesIndex) {
        if (this.mCharsValid) {
            return wp.getRunCharacterAdvance(this.mChars, start, end, contextStart, contextEnd, runIsRtl, offset, advances, advancesIndex);
        }
        int delta = this.mStart;
        PrecomputedText precomputedText = this.mComputed;
        if (precomputedText == null || advances != null) {
            return wp.getRunCharacterAdvance(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, runIsRtl, delta + offset, advances, advancesIndex);
        }
        return precomputedText.getWidth(start + delta, end + delta);
    }

    private float handleText(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, Canvas c, TextShaper.GlyphsConsumer consumer, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth, int offset, ArrayList<DecorationInfo> decorations, float[] advances, int advancesIndex) {
        int numDecorations;
        float totalWidth;
        float leftX;
        float rightX;
        float totalWidth2;
        float decorationXLeft;
        float decorationXRight;
        DecorationInfo info;
        float f;
        if (this.mIsJustifying) {
            wp.setWordSpacing(this.mAddedWidthForJustify);
        }
        if (fmi != null) {
            expandMetricsFromPaint(fmi, wp);
        }
        if (end == start) {
            return 0.0f;
        }
        float totalWidth3 = 0.0f;
        int numDecorations2 = decorations == null ? 0 : decorations.size();
        if (needWidth || ((c != null || consumer != null) && (wp.bgColor != 0 || numDecorations2 != 0 || runIsRtl))) {
            numDecorations = numDecorations2;
            totalWidth3 = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, offset, advances, advancesIndex);
        } else {
            numDecorations = numDecorations2;
        }
        int lastIndex = (this.mStart + end) - 1;
        if (lastIndex >= 0 && TextUtils.semNeedMoreWidth(this.mText.charAt(lastIndex))) {
            totalWidth = totalWidth3 + wp.measureText(" ");
        } else {
            totalWidth = totalWidth3;
        }
        if (runIsRtl) {
            float leftX2 = x - totalWidth;
            leftX = leftX2;
            rightX = x;
        } else {
            leftX = x;
            rightX = x + totalWidth;
        }
        if (consumer == null) {
            totalWidth2 = totalWidth;
        } else {
            totalWidth2 = totalWidth;
            float totalWidth4 = leftX;
            shapeTextRun(consumer, wp, start, end, contextStart, contextEnd, runIsRtl, totalWidth4);
        }
        if (this.mUseFallbackExtent && fmi != null) {
            expandMetricsFromPaint(wp, start, end, contextStart, contextEnd, runIsRtl, fmi);
        }
        if (c != null) {
            if (wp.bgColor != 0) {
                int previousColor = wp.getColor();
                Paint.Style previousStyle = wp.getStyle();
                wp.setColor(wp.bgColor);
                wp.setStyle(Paint.Style.FILL);
                c.drawRect(leftX, top, rightX, bottom, wp);
                wp.setStyle(previousStyle);
                wp.setColor(previousColor);
            }
            drawTextRun(c, wp, start, end, contextStart, contextEnd, runIsRtl, leftX, y + wp.baselineShift);
            if (numDecorations != 0) {
                int i = 0;
                while (i < numDecorations) {
                    DecorationInfo info2 = decorations.get(i);
                    int numDecorations3 = numDecorations;
                    int decorationStart = Math.max(info2.start, start);
                    int decorationEnd = Math.min(info2.end, offset);
                    int i2 = i;
                    float decorationStartAdvance = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, decorationStart, null, 0);
                    float decorationEndAdvance = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, decorationEnd, null, 0);
                    if (runIsRtl) {
                        float decorationXLeft2 = rightX - decorationEndAdvance;
                        decorationXLeft = decorationXLeft2;
                        decorationXRight = rightX - decorationStartAdvance;
                    } else {
                        float decorationXLeft3 = leftX + decorationStartAdvance;
                        decorationXLeft = decorationXLeft3;
                        decorationXRight = leftX + decorationEndAdvance;
                    }
                    if (info2.underlineColor != 0) {
                        info = info2;
                        drawStroke(wp, c, info2.underlineColor, wp.getUnderlinePosition(), info2.underlineThickness, decorationXLeft, decorationXRight, y);
                    } else {
                        info = info2;
                    }
                    if (!info.isUnderlineText) {
                        f = 1.0f;
                    } else {
                        float thickness = Math.max(wp.getUnderlineThickness(), 1.0f);
                        f = 1.0f;
                        drawStroke(wp, c, wp.getColor(), wp.getUnderlinePosition(), thickness, decorationXLeft, decorationXRight, y);
                    }
                    if (info.isStrikeThruText) {
                        float thickness2 = Math.max(wp.getStrikeThruThickness(), f);
                        drawStroke(wp, c, wp.getColor(), wp.getStrikeThruPosition(), thickness2, decorationXLeft, decorationXRight, y);
                    }
                    i = i2 + 1;
                    numDecorations = numDecorations3;
                }
            }
        }
        return runIsRtl ? -totalWidth2 : totalWidth2;
    }

    private float handleReplacement(ReplacementSpan replacement, TextPaint wp, int start, int limit, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth) {
        int previousTop;
        int previousAscent;
        int previousDescent;
        int previousBottom;
        int previousLeading;
        float x2;
        float ret = 0.0f;
        int i = this.mStart;
        int textStart = i + start;
        int textLimit = i + limit;
        if (needWidth || (c != null && runIsRtl)) {
            boolean needUpdateMetrics = fmi != null;
            if (!needUpdateMetrics) {
                previousTop = 0;
                previousAscent = 0;
                previousDescent = 0;
                previousBottom = 0;
                previousLeading = 0;
            } else {
                int previousTop2 = fmi.top;
                int previousAscent2 = fmi.ascent;
                int previousDescent2 = fmi.descent;
                int previousBottom2 = fmi.bottom;
                int previousLeading2 = fmi.leading;
                previousTop = previousTop2;
                previousAscent = previousAscent2;
                previousDescent = previousDescent2;
                previousBottom = previousBottom2;
                previousLeading = previousLeading2;
            }
            ret = replacement.getSize(wp, this.mText, textStart, textLimit, fmi);
            if (needUpdateMetrics) {
                updateMetrics(fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
            }
        }
        float ret2 = ret;
        if (c != null) {
            if (!runIsRtl) {
                x2 = x;
            } else {
                x2 = x - ret2;
            }
            replacement.draw(c, this.mText, textStart, textLimit, x2, top, y, bottom, wp);
        }
        return runIsRtl ? -ret2 : ret2;
    }

    private int adjustStartHyphenEdit(int start, int startHyphenEdit) {
        if (start > 0) {
            return 0;
        }
        return startHyphenEdit;
    }

    private int adjustEndHyphenEdit(int limit, int endHyphenEdit) {
        if (limit < this.mLen) {
            return 0;
        }
        return endHyphenEdit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class DecorationInfo {
        public int end;
        public boolean isStrikeThruText;
        public boolean isUnderlineText;
        public int start;
        public int underlineColor;
        public float underlineThickness;

        private DecorationInfo() {
            this.start = -1;
            this.end = -1;
        }

        public boolean hasDecoration() {
            return this.isStrikeThruText || this.isUnderlineText || this.underlineColor != 0;
        }

        public DecorationInfo copyInfo() {
            DecorationInfo copy = new DecorationInfo();
            copy.isStrikeThruText = this.isStrikeThruText;
            copy.isUnderlineText = this.isUnderlineText;
            copy.underlineColor = this.underlineColor;
            copy.underlineThickness = this.underlineThickness;
            return copy;
        }
    }

    private void extractDecorationInfo(TextPaint paint, DecorationInfo info) {
        info.isStrikeThruText = paint.isStrikeThruText();
        if (info.isStrikeThruText) {
            paint.setStrikeThruText(false);
        }
        info.isUnderlineText = paint.isUnderlineText();
        if (info.isUnderlineText) {
            paint.setUnderlineText(false);
        }
        info.underlineColor = paint.underlineColor;
        info.underlineThickness = paint.underlineThickness;
        paint.setUnderlineText(0, 0.0f);
    }

    private float handleRun(int start, int measureLimit, int limit, boolean runIsRtl, Canvas c, TextShaper.GlyphsConsumer consumer, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth, float[] advances, int advancesIndex) {
        boolean needsSpanMeasurement;
        int inext;
        boolean z;
        int jnext;
        int j;
        DecorationInfo decorationInfo;
        TextLine textLine;
        int activeStart;
        int i;
        int mlimit;
        TextPaint wp;
        TextPaint activePaint;
        int i2;
        int mlimit2;
        boolean z2;
        int jnext2;
        if (measureLimit < start || measureLimit > limit) {
            throw new IndexOutOfBoundsException("measureLimit (" + measureLimit + ") is out of start (" + start + ") and limit (" + limit + ") bounds");
        }
        if (advances != null && advances.length - advancesIndex < measureLimit - start) {
            throw new IndexOutOfBoundsException("advances doesn't have enough space to receive the result");
        }
        if (start == measureLimit) {
            TextPaint wp2 = this.mWorkPaint;
            wp2.set(this.mPaint);
            if (fmi != null) {
                expandMetricsFromPaint(fmi, wp2);
            }
            return 0.0f;
        }
        Spanned spanned = this.mSpanned;
        if (spanned == null) {
            needsSpanMeasurement = false;
        } else {
            SpanSet<MetricAffectingSpan> spanSet = this.mMetricAffectingSpanSpanSet;
            int i3 = this.mStart;
            spanSet.init(spanned, i3 + start, i3 + limit);
            SpanSet<CharacterStyle> spanSet2 = this.mCharacterStyleSpanSet;
            Spanned spanned2 = this.mSpanned;
            int i4 = this.mStart;
            spanSet2.init(spanned2, i4 + start, i4 + limit);
            needsSpanMeasurement = (this.mMetricAffectingSpanSpanSet.numberOfSpans == 0 && this.mCharacterStyleSpanSet.numberOfSpans == 0) ? false : true;
        }
        if (!needsSpanMeasurement) {
            TextPaint wp3 = this.mWorkPaint;
            wp3.set(this.mPaint);
            wp3.setStartHyphenEdit(adjustStartHyphenEdit(start, wp3.getStartHyphenEdit()));
            wp3.setEndHyphenEdit(adjustEndHyphenEdit(limit, wp3.getEndHyphenEdit()));
            return handleText(wp3, start, limit, start, limit, runIsRtl, c, consumer, x, top, y, bottom, fmi, needWidth, measureLimit, null, advances, advancesIndex);
        }
        float x2 = x;
        int i5 = start;
        while (i5 < measureLimit) {
            TextPaint wp4 = this.mWorkPaint;
            wp4.set(this.mPaint);
            SpanSet<MetricAffectingSpan> spanSet3 = this.mMetricAffectingSpanSpanSet;
            int i6 = this.mStart;
            int inext2 = spanSet3.getNextTransition(i6 + i5, i6 + limit) - this.mStart;
            int mlimit3 = Math.min(inext2, measureLimit);
            ReplacementSpan replacement = null;
            for (int j2 = 0; j2 < this.mMetricAffectingSpanSpanSet.numberOfSpans; j2++) {
                if (this.mMetricAffectingSpanSpanSet.spanStarts[j2] < this.mStart + mlimit3) {
                    int i7 = this.mMetricAffectingSpanSpanSet.spanEnds[j2];
                    int i8 = this.mStart;
                    if (i7 > i8 + i5) {
                        boolean insideEllipsis = i8 + this.mEllipsisStart <= this.mMetricAffectingSpanSpanSet.spanStarts[j2] && this.mMetricAffectingSpanSpanSet.spanEnds[j2] <= this.mStart + this.mEllipsisEnd;
                        MetricAffectingSpan span = this.mMetricAffectingSpanSpanSet.spans[j2];
                        if (span instanceof ReplacementSpan) {
                            replacement = !insideEllipsis ? (ReplacementSpan) span : null;
                        } else {
                            span.updateDrawState(wp4);
                        }
                    }
                }
            }
            if (replacement != null) {
                inext = inext2;
                float width = handleReplacement(replacement, wp4, i5, mlimit3, runIsRtl, c, x2, top, y, bottom, fmi, needWidth || mlimit3 < measureLimit);
                x2 += width;
                if (advances != null) {
                    advances[(advancesIndex + i5) - start] = runIsRtl ? -width : width;
                    for (int j3 = i5 + 1; j3 < mlimit3; j3++) {
                        advances[(advancesIndex + j3) - start] = 0.0f;
                    }
                }
            } else {
                int i9 = start;
                int mlimit4 = mlimit3;
                inext = inext2;
                TextPaint wp5 = wp4;
                TextLine textLine2 = this;
                TextPaint activePaint2 = textLine2.mActivePaint;
                activePaint2.set(textLine2.mPaint);
                int activeStart2 = i5;
                DecorationInfo decorationInfo2 = textLine2.mDecorationInfo;
                textLine2.mDecorations.clear();
                int activeEnd = mlimit4;
                int j4 = i5;
                float x3 = x2;
                int activeStart3 = activeStart2;
                while (j4 < mlimit4) {
                    SpanSet<CharacterStyle> spanSet4 = textLine2.mCharacterStyleSpanSet;
                    int i10 = textLine2.mStart;
                    int jnext3 = spanSet4.getNextTransition(i10 + j4, i10 + inext) - textLine2.mStart;
                    int offset = Math.min(jnext3, mlimit4);
                    TextPaint wp6 = wp5;
                    wp6.set(textLine2.mPaint);
                    for (int k = 0; k < textLine2.mCharacterStyleSpanSet.numberOfSpans; k++) {
                        if (textLine2.mCharacterStyleSpanSet.spanStarts[k] < textLine2.mStart + offset && textLine2.mCharacterStyleSpanSet.spanEnds[k] > textLine2.mStart + j4) {
                            textLine2.mCharacterStyleSpanSet.spans[k].updateDrawState(wp6);
                        }
                    }
                    textLine2.extractDecorationInfo(wp6, decorationInfo2);
                    if (j4 == i5) {
                        activePaint2.set(wp6);
                        jnext = jnext3;
                        j = j4;
                        decorationInfo = decorationInfo2;
                        textLine = textLine2;
                        activeStart = activeStart3;
                        i = i5;
                        mlimit = mlimit4;
                        wp = wp6;
                        activePaint = activePaint2;
                    } else if (equalAttributes(wp6, activePaint2)) {
                        jnext = jnext3;
                        j = j4;
                        decorationInfo = decorationInfo2;
                        textLine = textLine2;
                        activeStart = activeStart3;
                        i = i5;
                        mlimit = mlimit4;
                        wp = wp6;
                        activePaint = activePaint2;
                    } else {
                        activePaint2.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(activeStart3, textLine2.mPaint.getStartHyphenEdit()));
                        activePaint2.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(activeEnd, textLine2.mPaint.getEndHyphenEdit()));
                        if (needWidth) {
                            i2 = measureLimit;
                            mlimit2 = mlimit4;
                        } else {
                            i2 = measureLimit;
                            mlimit2 = mlimit4;
                            if (activeEnd >= i2) {
                                z2 = false;
                                mlimit = mlimit2;
                                jnext = jnext3;
                                j = j4;
                                decorationInfo = decorationInfo2;
                                i = i5;
                                x3 += handleText(activePaint2, activeStart3, activeEnd, i5, inext, runIsRtl, c, consumer, x3, top, y, bottom, fmi, z2, Math.min(activeEnd, mlimit2), textLine2.mDecorations, advances, (advancesIndex + activeStart3) - i9);
                                wp = wp6;
                                activePaint = activePaint2;
                                activePaint.set(wp);
                                textLine = this;
                                textLine.mDecorations.clear();
                                activeStart = j;
                            }
                        }
                        z2 = true;
                        mlimit = mlimit2;
                        jnext = jnext3;
                        j = j4;
                        decorationInfo = decorationInfo2;
                        i = i5;
                        x3 += handleText(activePaint2, activeStart3, activeEnd, i5, inext, runIsRtl, c, consumer, x3, top, y, bottom, fmi, z2, Math.min(activeEnd, mlimit2), textLine2.mDecorations, advances, (advancesIndex + activeStart3) - i9);
                        wp = wp6;
                        activePaint = activePaint2;
                        activePaint.set(wp);
                        textLine = this;
                        textLine.mDecorations.clear();
                        activeStart = j;
                    }
                    activeEnd = jnext;
                    if (!decorationInfo.hasDecoration()) {
                        jnext2 = jnext;
                    } else {
                        DecorationInfo copy = decorationInfo.copyInfo();
                        copy.start = j;
                        jnext2 = jnext;
                        copy.end = jnext2;
                        textLine.mDecorations.add(copy);
                    }
                    j4 = jnext2;
                    textLine2 = textLine;
                    activePaint2 = activePaint;
                    wp5 = wp;
                    mlimit4 = mlimit;
                    decorationInfo2 = decorationInfo;
                    activeStart3 = activeStart;
                    i5 = i;
                    i9 = start;
                }
                int activeEnd2 = activeEnd;
                TextLine textLine3 = textLine2;
                int activeStart4 = activeStart3;
                int i11 = i5;
                int mlimit5 = mlimit4;
                TextPaint activePaint3 = activePaint2;
                activePaint3.setStartHyphenEdit(textLine3.adjustStartHyphenEdit(activeStart4, textLine3.mPaint.getStartHyphenEdit()));
                activePaint3.setEndHyphenEdit(textLine3.adjustEndHyphenEdit(activeEnd2, textLine3.mPaint.getEndHyphenEdit()));
                if (!needWidth && activeEnd2 >= measureLimit) {
                    z = false;
                    x2 = x3 + handleText(activePaint3, activeStart4, activeEnd2, i11, inext, runIsRtl, c, consumer, x3, top, y, bottom, fmi, z, Math.min(activeEnd2, mlimit5), textLine3.mDecorations, advances, (advancesIndex + activeStart4) - start);
                }
                z = true;
                x2 = x3 + handleText(activePaint3, activeStart4, activeEnd2, i11, inext, runIsRtl, c, consumer, x3, top, y, bottom, fmi, z, Math.min(activeEnd2, mlimit5), textLine3.mDecorations, advances, (advancesIndex + activeStart4) - start);
            }
            i5 = inext;
        }
        return x2 - x;
    }

    private void drawTextRun(Canvas c, TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, float x, int y) {
        if (this.mCharsValid) {
            int count = end - start;
            int contextCount = contextEnd - contextStart;
            c.drawTextRun(this.mChars, start, count, contextStart, contextCount, x, y, runIsRtl, wp);
        } else {
            int delta = this.mStart;
            c.drawTextRun(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, x, y, runIsRtl, wp);
        }
    }

    private void shapeTextRun(TextShaper.GlyphsConsumer consumer, TextPaint paint, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, float x) {
        PositionedGlyphs glyphs;
        int count = end - start;
        int contextCount = contextEnd - contextStart;
        if (this.mCharsValid) {
            glyphs = TextRunShaper.shapeTextRun(this.mChars, start, count, contextStart, contextCount, x, 0.0f, runIsRtl, paint);
        } else {
            CharSequence charSequence = this.mText;
            int i = this.mStart;
            glyphs = TextRunShaper.shapeTextRun(charSequence, i + start, count, i + contextStart, contextCount, x, 0.0f, runIsRtl, paint);
        }
        consumer.accept(start, count, glyphs, paint);
    }

    float nextTab(float h) {
        Layout.TabStops tabStops = this.mTabs;
        if (tabStops != null) {
            return tabStops.nextTab(h);
        }
        return Layout.TabStops.nextDefaultStop(h, 20.0f);
    }

    private boolean isStretchableWhitespace(int ch) {
        return ch == 32;
    }

    private int countStretchableSpaces(int start, int end) {
        int count = 0;
        for (int i = start; i < end; i++) {
            char c = this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i);
            if (isStretchableWhitespace(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isLineEndSpace(char ch) {
        return ch == ' ' || ch == '\t' || ch == 5760 || (8192 <= ch && ch <= 8202 && ch != 8199) || ch == 8287 || ch == 12288;
    }

    private static boolean equalAttributes(TextPaint lp, TextPaint rp) {
        return lp.getColorFilter() == rp.getColorFilter() && lp.getMaskFilter() == rp.getMaskFilter() && lp.getShader() == rp.getShader() && lp.getTypeface() == rp.getTypeface() && lp.getXfermode() == rp.getXfermode() && lp.getTextLocales().equals(rp.getTextLocales()) && TextUtils.equals(lp.getFontFeatureSettings(), rp.getFontFeatureSettings()) && TextUtils.equals(lp.getFontVariationSettings(), rp.getFontVariationSettings()) && lp.getShadowLayerRadius() == rp.getShadowLayerRadius() && lp.getShadowLayerDx() == rp.getShadowLayerDx() && lp.getShadowLayerDy() == rp.getShadowLayerDy() && lp.getShadowLayerColor() == rp.getShadowLayerColor() && lp.getFlags() == rp.getFlags() && lp.getHinting() == rp.getHinting() && lp.getStyle() == rp.getStyle() && lp.getColor() == rp.getColor() && lp.getStrokeWidth() == rp.getStrokeWidth() && lp.getStrokeMiter() == rp.getStrokeMiter() && lp.getStrokeCap() == rp.getStrokeCap() && lp.getStrokeJoin() == rp.getStrokeJoin() && lp.getTextAlign() == rp.getTextAlign() && lp.isElegantTextHeight() == rp.isElegantTextHeight() && lp.getTextSize() == rp.getTextSize() && lp.getTextScaleX() == rp.getTextScaleX() && lp.getTextSkewX() == rp.getTextSkewX() && lp.getLetterSpacing() == rp.getLetterSpacing() && lp.getWordSpacing() == rp.getWordSpacing() && lp.getStartHyphenEdit() == rp.getStartHyphenEdit() && lp.getEndHyphenEdit() == rp.getEndHyphenEdit() && lp.bgColor == rp.bgColor && lp.baselineShift == rp.baselineShift && lp.linkColor == rp.linkColor && lp.drawableState == rp.drawableState && lp.density == rp.density && lp.underlineColor == rp.underlineColor && lp.underlineThickness == rp.underlineThickness;
    }
}

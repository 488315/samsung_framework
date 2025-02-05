package android.graphics;

import android.app.compat.CompatChanges;
import android.graphics.fonts.FontVariationAxis;
import android.os.LocaleList;
import android.text.GraphicsOperations;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import com.android.text.flags.Flags;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Paint {
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final int AUTO_HINTING_TEXT_FLAG = 2048;
    public static final int BIDI_DEFAULT_LTR = 2;
    public static final int BIDI_DEFAULT_RTL = 3;
    private static final int BIDI_FLAG_MASK = 7;
    public static final int BIDI_FORCE_LTR = 4;
    public static final int BIDI_FORCE_RTL = 5;
    public static final int BIDI_LTR = 0;
    private static final int BIDI_MAX_FLAG_VALUE = 5;
    public static final int BIDI_RTL = 1;
    public static final int CURSOR_AFTER = 0;
    public static final int CURSOR_AT = 4;
    public static final int CURSOR_AT_OR_AFTER = 1;
    public static final int CURSOR_AT_OR_BEFORE = 3;
    public static final int CURSOR_BEFORE = 2;
    private static final int CURSOR_OPT_MAX_VALUE = 4;
    public static final long DEPRECATE_UI_FONT = 279646685;
    public static final int DEV_KERN_TEXT_FLAG = 256;
    public static final int DIRECTION_LTR = 0;
    public static final int DIRECTION_RTL = 1;
    public static final int DITHER_FLAG = 4;
    private static final int ELEGANT_TEXT_HEIGHT_DISABLED = 1;
    private static final int ELEGANT_TEXT_HEIGHT_ENABLED = 0;
    private static final int ELEGANT_TEXT_HEIGHT_UNSET = -1;
    public static final int EMBEDDED_BITMAP_TEXT_FLAG = 1024;
    public static final int END_HYPHEN_EDIT_INSERT_ARMENIAN_HYPHEN = 3;
    public static final int END_HYPHEN_EDIT_INSERT_HYPHEN = 2;
    public static final int END_HYPHEN_EDIT_INSERT_MAQAF = 4;
    public static final int END_HYPHEN_EDIT_INSERT_UCAS_HYPHEN = 5;
    public static final int END_HYPHEN_EDIT_INSERT_ZWJ_AND_HYPHEN = 6;
    public static final int END_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int END_HYPHEN_EDIT_REPLACE_WITH_HYPHEN = 1;
    public static final int FAKE_BOLD_TEXT_FLAG = 32;
    public static final int FILTER_BITMAP_FLAG = 2;
    static final int HIDDEN_DEFAULT_PAINT_FLAGS = 1282;
    public static final int HINTING_OFF = 0;
    public static final int HINTING_ON = 1;
    public static final int LCD_RENDER_TEXT_FLAG = 512;
    public static final int LINEAR_TEXT_FLAG = 64;
    public static final int START_HYPHEN_EDIT_INSERT_HYPHEN = 1;
    public static final int START_HYPHEN_EDIT_INSERT_ZWJ = 2;
    public static final int START_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int STRIKE_THRU_TEXT_FLAG = 16;
    public static final int SUBPIXEL_TEXT_FLAG = 128;
    public static final int TEXT_RUN_FLAG_LEFT_EDGE = 8192;
    public static final int TEXT_RUN_FLAG_RIGHT_EDGE = 16384;
    public static final int UNDERLINE_TEXT_FLAG = 8;
    public static final int VERTICAL_TEXT_FLAG = 4096;
    public int mBidiFlags;
    private long mColor;
    private ColorFilter mColorFilter;
    private float mCompatScaling;
    private String mFontFeatureSettings;
    private String mFontVariationSettings;
    private boolean mHasCompatScaling;
    private float mInvCompatScaling;
    private LocaleList mLocales;
    private MaskFilter mMaskFilter;
    private MyanmarEncoding mMyanmarEncoding;
    private long mNativeColorFilter;
    private long mNativePaint;
    private long mNativeShader;
    private PathEffect mPathEffect;
    private Shader mShader;
    private long mShadowLayerColor;
    private float mShadowLayerDx;
    private float mShadowLayerDy;
    private float mShadowLayerRadius;
    private Typeface mTypeface;
    private boolean mUseCustomMyanmarEncoding;
    private Xfermode mXfermode;
    private static final Object sCacheLock = new Object();
    private static final HashMap<String, Integer> sMinikinLocaleListIdCache = new HashMap<>();
    static final Style[] sStyleArray = {Style.FILL, Style.STROKE, Style.FILL_AND_STROKE};
    static final Cap[] sCapArray = {Cap.BUTT, Cap.ROUND, Cap.SQUARE};
    static final Join[] sJoinArray = {Join.MITER, Join.ROUND, Join.BEVEL};
    static final Align[] sAlignArray = {Align.LEFT, Align.CENTER, Align.RIGHT};

    @Retention(RetentionPolicy.SOURCE)
    public @interface CursorOption {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndHyphenEdit {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PaintFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartHyphenEdit {
    }

    @CriticalNative
    private static native float nAscent(long j);

    private static native int nBreakText(long j, String str, boolean z, float f, int i, float[] fArr);

    private static native int nBreakText(long j, char[] cArr, int i, int i2, float f, int i3, float[] fArr);

    @CriticalNative
    private static native float nDescent(long j);

    @CriticalNative
    private static native boolean nEqualsForTextMeasurement(long j, long j2);

    private static native void nGetCharArrayBounds(long j, char[] cArr, int i, int i2, int i3, Rect rect);

    @CriticalNative
    private static native int nGetElegantTextHeight(long j);

    @CriticalNative
    private static native int nGetEndHyphenEdit(long j);

    @CriticalNative
    private static native boolean nGetFillPath(long j, long j2, long j3);

    @CriticalNative
    private static native int nGetFlags(long j);

    @FastNative
    private static native float nGetFontMetrics(long j, FontMetrics fontMetrics, boolean z);

    @FastNative
    private static native int nGetFontMetricsInt(long j, FontMetricsInt fontMetricsInt, boolean z);

    private static native void nGetFontMetricsIntForText(long j, String str, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt);

    private static native void nGetFontMetricsIntForText(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt);

    @CriticalNative
    private static native int nGetHinting(long j);

    @CriticalNative
    private static native float nGetLetterSpacing(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    private static native int nGetOffsetForAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, float f);

    private static native float nGetRunAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF, RunInfo runInfo);

    @CriticalNative
    private static native int nGetStartHyphenEdit(long j);

    @CriticalNative
    private static native float nGetStrikeThruPosition(long j);

    @CriticalNative
    private static native float nGetStrikeThruThickness(long j);

    private static native void nGetStringBounds(long j, String str, int i, int i2, int i3, Rect rect);

    @CriticalNative
    private static native int nGetStrokeCap(long j);

    @CriticalNative
    private static native int nGetStrokeJoin(long j);

    @CriticalNative
    private static native float nGetStrokeMiter(long j);

    @CriticalNative
    private static native float nGetStrokeWidth(long j);

    @CriticalNative
    private static native int nGetStyle(long j);

    private static native float nGetTextAdvances(long j, String str, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    private static native float nGetTextAdvances(long j, char[] cArr, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    @CriticalNative
    private static native int nGetTextAlign(long j);

    private static native void nGetTextPath(long j, int i, String str, int i2, int i3, float f, float f2, long j2);

    private static native void nGetTextPath(long j, int i, char[] cArr, int i2, int i3, float f, float f2, long j2);

    private native int nGetTextRunCursor(long j, String str, int i, int i2, int i3, int i4, int i5);

    private native int nGetTextRunCursor(long j, char[] cArr, int i, int i2, int i3, int i4, int i5);

    @CriticalNative
    private static native float nGetTextScaleX(long j);

    @CriticalNative
    private static native float nGetTextSize(long j);

    @CriticalNative
    private static native float nGetTextSkewX(long j);

    @CriticalNative
    private static native float nGetUnderlinePosition(long j);

    @CriticalNative
    private static native float nGetUnderlineThickness(long j);

    @CriticalNative
    private static native float nGetWordSpacing(long j);

    private static native boolean nHasGlyph(long j, int i, String str);

    @CriticalNative
    private static native boolean nHasShadowLayer(long j);

    private static native long nInit();

    private static native long nInitWithPaint(long j);

    @CriticalNative
    private static native void nReset(long j);

    @CriticalNative
    private static native void nSet(long j, long j2);

    @CriticalNative
    private static native void nSetAlpha(long j, int i);

    @CriticalNative
    private static native void nSetAntiAlias(long j, boolean z);

    @CriticalNative
    private static native void nSetColor(long j, int i);

    @CriticalNative
    private static native void nSetColor(long j, long j2, long j3);

    @CriticalNative
    private static native long nSetColorFilter(long j, long j2);

    @CriticalNative
    private static native void nSetDither(long j, boolean z);

    @CriticalNative
    private static native void nSetElegantTextHeight(long j, int i);

    @CriticalNative
    private static native void nSetEndHyphenEdit(long j, int i);

    @CriticalNative
    private static native void nSetFakeBoldText(long j, boolean z);

    @CriticalNative
    private static native void nSetFilterBitmap(long j, boolean z);

    @CriticalNative
    private static native void nSetFlags(long j, int i);

    @FastNative
    private static native void nSetFontFeatureSettings(long j, String str);

    @CriticalNative
    private static native void nSetHinting(long j, int i);

    @CriticalNative
    private static native void nSetLetterSpacing(long j, float f);

    @CriticalNative
    private static native void nSetLinearText(long j, boolean z);

    @CriticalNative
    private static native long nSetMaskFilter(long j, long j2);

    private static native void nSetMyanmarEncoding(long j, int i);

    @CriticalNative
    private static native long nSetPathEffect(long j, long j2);

    @CriticalNative
    private static native long nSetShader(long j, long j2);

    @CriticalNative
    private static native void nSetShadowLayer(long j, float f, float f2, float f3, long j2, long j3);

    @CriticalNative
    private static native void nSetStartHyphenEdit(long j, int i);

    @CriticalNative
    private static native void nSetStrikeThruText(long j, boolean z);

    @CriticalNative
    private static native void nSetStrokeCap(long j, int i);

    @CriticalNative
    private static native void nSetStrokeJoin(long j, int i);

    @CriticalNative
    private static native void nSetStrokeMiter(long j, float f);

    @CriticalNative
    private static native void nSetStrokeWidth(long j, float f);

    @CriticalNative
    private static native void nSetStyle(long j, int i);

    @CriticalNative
    private static native void nSetSubpixelText(long j, boolean z);

    @CriticalNative
    private static native void nSetTextAlign(long j, int i);

    @FastNative
    private static native int nSetTextLocales(long j, String str);

    @CriticalNative
    private static native void nSetTextLocalesByMinikinLocaleListId(long j, int i);

    @CriticalNative
    private static native void nSetTextScaleX(long j, float f);

    @CriticalNative
    private static native void nSetTextSize(long j, float f);

    @CriticalNative
    private static native void nSetTextSkewX(long j, float f);

    @CriticalNative
    private static native void nSetTypeface(long j, long j2);

    @CriticalNative
    private static native void nSetUnderlineText(long j, boolean z);

    @CriticalNative
    private static native void nSetWordSpacing(long j, float f);

    @CriticalNative
    private static native void nSetXfermode(long j, int i);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Paint.class.getClassLoader(), Paint.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public enum Style {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2);

        final int nativeInt;

        Style(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public enum Cap {
        BUTT(0),
        ROUND(1),
        SQUARE(2);

        final int nativeInt;

        Cap(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public enum Join {
        MITER(0),
        ROUND(1),
        BEVEL(2);

        final int nativeInt;

        Join(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public enum Align {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        final int nativeInt;

        Align(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public Paint() {
        this(1);
    }

    public Paint(int flags) {
        this.mUseCustomMyanmarEncoding = false;
        this.mBidiFlags = 2;
        this.mNativePaint = nInit();
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setFlags(flags | 1282);
        this.mInvCompatScaling = 1.0f;
        this.mCompatScaling = 1.0f;
        setTextLocales(LocaleList.getAdjustedDefault());
        this.mColor = Color.pack(-16777216);
        resetElegantTextHeight();
    }

    public Paint(Paint paint) {
        this.mUseCustomMyanmarEncoding = false;
        this.mBidiFlags = 2;
        this.mNativePaint = nInitWithPaint(paint.getNativeInstance());
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setClassVariablesFrom(paint);
    }

    public void reset() {
        nReset(this.mNativePaint);
        setFlags(1283);
        this.mColor = Color.pack(-16777216);
        this.mColorFilter = null;
        this.mMaskFilter = null;
        this.mPathEffect = null;
        this.mShader = null;
        this.mNativeShader = 0L;
        this.mTypeface = null;
        this.mXfermode = null;
        this.mHasCompatScaling = false;
        this.mCompatScaling = 1.0f;
        this.mInvCompatScaling = 1.0f;
        this.mBidiFlags = 2;
        setTextLocales(LocaleList.getAdjustedDefault());
        resetElegantTextHeight();
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
        this.mShadowLayerRadius = 0.0f;
        this.mShadowLayerDx = 0.0f;
        this.mShadowLayerDy = 0.0f;
        this.mShadowLayerColor = Color.pack(0);
        this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
    }

    public void set(Paint src) {
        if (this != src) {
            nSet(this.mNativePaint, src.mNativePaint);
            setClassVariablesFrom(src);
        }
        nSetTypeface(this.mNativePaint, getTypefaceNativeInstance(this.mTypeface));
    }

    private void setClassVariablesFrom(Paint paint) {
        this.mColor = paint.mColor;
        this.mColorFilter = paint.mColorFilter;
        this.mMaskFilter = paint.mMaskFilter;
        this.mPathEffect = paint.mPathEffect;
        this.mShader = paint.mShader;
        this.mNativeShader = paint.mNativeShader;
        this.mTypeface = paint.mTypeface;
        this.mXfermode = paint.mXfermode;
        this.mHasCompatScaling = paint.mHasCompatScaling;
        this.mCompatScaling = paint.mCompatScaling;
        this.mInvCompatScaling = paint.mInvCompatScaling;
        this.mBidiFlags = paint.mBidiFlags;
        this.mLocales = paint.mLocales;
        this.mFontFeatureSettings = paint.mFontFeatureSettings;
        this.mFontVariationSettings = paint.mFontVariationSettings;
        this.mShadowLayerRadius = paint.mShadowLayerRadius;
        this.mShadowLayerDx = paint.mShadowLayerDx;
        this.mShadowLayerDy = paint.mShadowLayerDy;
        this.mShadowLayerColor = paint.mShadowLayerColor;
        this.mMyanmarEncoding = paint.mMyanmarEncoding;
        this.mUseCustomMyanmarEncoding = paint.mUseCustomMyanmarEncoding;
    }

    public void setCompatibilityScaling(float factor) {
        if (factor == 1.0d) {
            this.mHasCompatScaling = false;
            this.mInvCompatScaling = 1.0f;
            this.mCompatScaling = 1.0f;
        } else {
            this.mHasCompatScaling = true;
            this.mCompatScaling = factor;
            this.mInvCompatScaling = 1.0f / factor;
        }
    }

    public synchronized long getNativeInstance() {
        boolean filter = isFilterBitmap();
        long newNativeShader = this.mShader == null ? 0L : this.mShader.getNativeInstance(filter);
        if (newNativeShader != this.mNativeShader) {
            this.mNativeShader = newNativeShader;
            nSetShader(this.mNativePaint, this.mNativeShader);
        }
        long newNativeColorFilter = this.mColorFilter != null ? this.mColorFilter.getNativeInstance() : 0L;
        if (newNativeColorFilter != this.mNativeColorFilter) {
            this.mNativeColorFilter = newNativeColorFilter;
            nSetColorFilter(this.mNativePaint, this.mNativeColorFilter);
        }
        return this.mNativePaint;
    }

    public int getBidiFlags() {
        return this.mBidiFlags;
    }

    public void setBidiFlags(int flags) {
        int flags2 = flags & 7;
        if (flags2 > 5) {
            throw new IllegalArgumentException("unknown bidi flag: " + flags2);
        }
        this.mBidiFlags = flags2;
    }

    public int getFlags() {
        return nGetFlags(this.mNativePaint);
    }

    public void setFlags(int flags) {
        nSetFlags(this.mNativePaint, flags);
    }

    public int getHinting() {
        return nGetHinting(this.mNativePaint);
    }

    public void setHinting(int mode) {
        nSetHinting(this.mNativePaint, mode);
    }

    public final boolean isAntiAlias() {
        return (getFlags() & 1) != 0;
    }

    public void setAntiAlias(boolean aa) {
        nSetAntiAlias(this.mNativePaint, aa);
    }

    public final boolean isDither() {
        return (getFlags() & 4) != 0;
    }

    public void setDither(boolean dither) {
        nSetDither(this.mNativePaint, dither);
    }

    public final boolean isLinearText() {
        return (getFlags() & 64) != 0;
    }

    public void setLinearText(boolean linearText) {
        nSetLinearText(this.mNativePaint, linearText);
    }

    public final boolean isSubpixelText() {
        return (getFlags() & 128) != 0;
    }

    public void setSubpixelText(boolean subpixelText) {
        nSetSubpixelText(this.mNativePaint, subpixelText);
    }

    public final boolean isUnderlineText() {
        return (getFlags() & 8) != 0;
    }

    public float getUnderlinePosition() {
        return nGetUnderlinePosition(this.mNativePaint);
    }

    public float getUnderlineThickness() {
        return nGetUnderlineThickness(this.mNativePaint);
    }

    public void setUnderlineText(boolean underlineText) {
        nSetUnderlineText(this.mNativePaint, underlineText);
    }

    public final boolean isStrikeThruText() {
        return (getFlags() & 16) != 0;
    }

    public float getStrikeThruPosition() {
        return nGetStrikeThruPosition(this.mNativePaint);
    }

    public float getStrikeThruThickness() {
        return nGetStrikeThruThickness(this.mNativePaint);
    }

    public void setStrikeThruText(boolean strikeThruText) {
        nSetStrikeThruText(this.mNativePaint, strikeThruText);
    }

    public final boolean isFakeBoldText() {
        return (getFlags() & 32) != 0;
    }

    public void setFakeBoldText(boolean fakeBoldText) {
        nSetFakeBoldText(this.mNativePaint, fakeBoldText);
    }

    public final boolean isFilterBitmap() {
        return (getFlags() & 2) != 0;
    }

    public void setFilterBitmap(boolean filter) {
        nSetFilterBitmap(this.mNativePaint, filter);
    }

    public Style getStyle() {
        return sStyleArray[nGetStyle(this.mNativePaint)];
    }

    public void setStyle(Style style) {
        nSetStyle(this.mNativePaint, style.nativeInt);
    }

    public int getColor() {
        return Color.toArgb(this.mColor);
    }

    public long getColorLong() {
        return this.mColor;
    }

    public void setColor(int color) {
        nSetColor(this.mNativePaint, color);
        this.mColor = Color.pack(color);
    }

    public void setColor(long color) {
        ColorSpace cs = Color.colorSpace(color);
        nSetColor(this.mNativePaint, cs.getNativeInstance(), color);
        this.mColor = color;
    }

    public int getAlpha() {
        return Math.round(Color.alpha(this.mColor) * 255.0f);
    }

    public void setAlpha(int a) {
        ColorSpace cs = Color.colorSpace(this.mColor);
        float r = Color.red(this.mColor);
        float g = Color.green(this.mColor);
        float b = Color.blue(this.mColor);
        this.mColor = Color.pack(r, g, b, a * 0.003921569f, cs);
        nSetAlpha(this.mNativePaint, a);
    }

    public void setARGB(int a, int r, int g, int b) {
        setColor((a << 24) | (r << 16) | (g << 8) | b);
    }

    public float getStrokeWidth() {
        return nGetStrokeWidth(this.mNativePaint);
    }

    public void setStrokeWidth(float width) {
        nSetStrokeWidth(this.mNativePaint, width);
    }

    public float getStrokeMiter() {
        return nGetStrokeMiter(this.mNativePaint);
    }

    public void setStrokeMiter(float miter) {
        nSetStrokeMiter(this.mNativePaint, miter);
    }

    public Cap getStrokeCap() {
        return sCapArray[nGetStrokeCap(this.mNativePaint)];
    }

    public void setStrokeCap(Cap cap) {
        nSetStrokeCap(this.mNativePaint, cap.nativeInt);
    }

    public Join getStrokeJoin() {
        return sJoinArray[nGetStrokeJoin(this.mNativePaint)];
    }

    public void setStrokeJoin(Join join) {
        nSetStrokeJoin(this.mNativePaint, join.nativeInt);
    }

    public boolean getFillPath(Path src, Path dst) {
        return nGetFillPath(this.mNativePaint, src.readOnlyNI(), dst.mutateNI());
    }

    public Shader getShader() {
        return this.mShader;
    }

    public Shader setShader(Shader shader) {
        if (this.mShader != shader) {
            this.mNativeShader = -1L;
            nSetShader(this.mNativePaint, 0L);
        }
        this.mShader = shader;
        return shader;
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public ColorFilter setColorFilter(ColorFilter filter) {
        if (this.mColorFilter != filter) {
            this.mNativeColorFilter = -1L;
        }
        this.mColorFilter = filter;
        return filter;
    }

    public Xfermode getXfermode() {
        return this.mXfermode;
    }

    public BlendMode getBlendMode() {
        if (this.mXfermode == null) {
            return null;
        }
        return BlendMode.fromValue(this.mXfermode.porterDuffMode);
    }

    public Xfermode setXfermode(Xfermode xfermode) {
        return installXfermode(xfermode);
    }

    private Xfermode installXfermode(Xfermode xfermode) {
        int newMode = xfermode != null ? xfermode.porterDuffMode : Xfermode.DEFAULT;
        int curMode = this.mXfermode != null ? this.mXfermode.porterDuffMode : Xfermode.DEFAULT;
        if (newMode != curMode) {
            nSetXfermode(this.mNativePaint, newMode);
        }
        this.mXfermode = xfermode;
        return xfermode;
    }

    public void setBlendMode(BlendMode blendmode) {
        installXfermode(blendmode != null ? blendmode.getXfermode() : null);
    }

    public PathEffect getPathEffect() {
        return this.mPathEffect;
    }

    public PathEffect setPathEffect(PathEffect effect) {
        long effectNative = 0;
        if (effect != null) {
            effectNative = effect.native_instance;
        }
        nSetPathEffect(this.mNativePaint, effectNative);
        this.mPathEffect = effect;
        return effect;
    }

    public MaskFilter getMaskFilter() {
        return this.mMaskFilter;
    }

    public MaskFilter setMaskFilter(MaskFilter maskfilter) {
        long maskfilterNative = 0;
        if (maskfilter != null) {
            maskfilterNative = maskfilter.native_instance;
        }
        nSetMaskFilter(this.mNativePaint, maskfilterNative);
        this.mMaskFilter = maskfilter;
        return maskfilter;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public Typeface setTypeface(Typeface typeface) {
        if (typeface != null) {
            long j = typeface.native_instance;
        }
        long typefaceNative = getTypefaceNativeInstance(typeface);
        nSetTypeface(this.mNativePaint, typefaceNative);
        this.mTypeface = typeface;
        return typeface;
    }

    private long getTypefaceNativeInstance(Typeface typeface) {
        long typefaceNative = 0;
        if (typeface != null) {
            if (Typeface.isFlipFontUsed && typeface.isLikeDefault) {
                typefaceNative = Typeface.DEFAULT.native_instance;
            } else {
                typefaceNative = typeface.native_instance;
            }
        }
        if (typefaceNative == 0 && Typeface.isFlipFontUsed) {
            long typefaceNative2 = Typeface.DEFAULT.native_instance;
            return typefaceNative2;
        }
        return typefaceNative;
    }

    @Deprecated
    public Rasterizer getRasterizer() {
        return null;
    }

    @Deprecated
    public Rasterizer setRasterizer(Rasterizer rasterizer) {
        return rasterizer;
    }

    public void setShadowLayer(float radius, float dx, float dy, int shadowColor) {
        setShadowLayer(radius, dx, dy, Color.pack(shadowColor));
    }

    public void setShadowLayer(float radius, float dx, float dy, long shadowColor) {
        ColorSpace cs = Color.colorSpace(shadowColor);
        nSetShadowLayer(this.mNativePaint, radius, dx, dy, cs.getNativeInstance(), shadowColor);
        this.mShadowLayerRadius = radius;
        this.mShadowLayerDx = dx;
        this.mShadowLayerDy = dy;
        this.mShadowLayerColor = shadowColor;
    }

    public void clearShadowLayer() {
        setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }

    public boolean hasShadowLayer() {
        return nHasShadowLayer(this.mNativePaint);
    }

    public float getShadowLayerRadius() {
        return this.mShadowLayerRadius;
    }

    public float getShadowLayerDx() {
        return this.mShadowLayerDx;
    }

    public float getShadowLayerDy() {
        return this.mShadowLayerDy;
    }

    public int getShadowLayerColor() {
        return Color.toArgb(this.mShadowLayerColor);
    }

    public long getShadowLayerColorLong() {
        return this.mShadowLayerColor;
    }

    public Align getTextAlign() {
        return sAlignArray[nGetTextAlign(this.mNativePaint)];
    }

    public void setTextAlign(Align align) {
        nSetTextAlign(this.mNativePaint, align.nativeInt);
    }

    public Locale getTextLocale() {
        return this.mLocales.get(0);
    }

    public LocaleList getTextLocales() {
        return this.mLocales;
    }

    public void setTextLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale cannot be null");
        }
        if (!this.mUseCustomMyanmarEncoding) {
            String country = locale.getCountry();
            if ("ZG".equals(country)) {
                if (this.mMyanmarEncoding != MyanmarEncoding.ME_ZAWGYI) {
                    this.mMyanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
                    nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_ZAWGYI.nativeInt);
                }
            } else if (this.mMyanmarEncoding != MyanmarEncoding.ME_UNICODE) {
                this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
                nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_UNICODE.nativeInt);
            }
        }
        if (this.mLocales != null && this.mLocales.size() == 1 && locale.equals(this.mLocales.get(0))) {
            return;
        }
        this.mLocales = new LocaleList(locale);
        syncTextLocalesWithMinikin();
    }

    public void setTextLocales(LocaleList locales) {
        if (locales == null || locales.isEmpty()) {
            throw new IllegalArgumentException("locales cannot be null or empty");
        }
        if (locales.equals(this.mLocales)) {
            return;
        }
        if (!this.mUseCustomMyanmarEncoding) {
            String country = locales.get(0).getCountry();
            if (locales.size() > 1 && (country == null || "".equals(country))) {
                country = locales.get(1).getCountry();
            }
            if ("ZG".equals(country)) {
                if (this.mMyanmarEncoding != MyanmarEncoding.ME_ZAWGYI) {
                    this.mMyanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
                    nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_ZAWGYI.nativeInt);
                }
            } else if (this.mMyanmarEncoding != MyanmarEncoding.ME_UNICODE) {
                this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
                nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_UNICODE.nativeInt);
            }
        }
        this.mLocales = locales;
        syncTextLocalesWithMinikin();
    }

    private void syncTextLocalesWithMinikin() {
        String languageTags = this.mLocales.toLanguageTags();
        synchronized (sCacheLock) {
            Integer minikinLocaleListId = sMinikinLocaleListIdCache.get(languageTags);
            if (minikinLocaleListId == null) {
                int newID = nSetTextLocales(this.mNativePaint, languageTags);
                sMinikinLocaleListIdCache.put(languageTags, Integer.valueOf(newID));
            } else {
                nSetTextLocalesByMinikinLocaleListId(this.mNativePaint, minikinLocaleListId.intValue());
            }
        }
    }

    public boolean isElegantTextHeight() {
        int rawValue = nGetElegantTextHeight(this.mNativePaint);
        switch (rawValue) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return Flags.deprecateUiFonts();
        }
    }

    public void setElegantTextHeight(boolean z) {
        nSetElegantTextHeight(this.mNativePaint, !z ? 1 : 0);
    }

    private void resetElegantTextHeight() {
        if (CompatChanges.isChangeEnabled(DEPRECATE_UI_FONT)) {
            nSetElegantTextHeight(this.mNativePaint, -1);
        } else {
            nSetElegantTextHeight(this.mNativePaint, 1);
        }
    }

    public float getTextSize() {
        return nGetTextSize(this.mNativePaint);
    }

    public void setTextSize(float textSize) {
        nSetTextSize(this.mNativePaint, textSize);
    }

    public float getTextScaleX() {
        return nGetTextScaleX(this.mNativePaint);
    }

    public void setTextScaleX(float scaleX) {
        nSetTextScaleX(this.mNativePaint, scaleX);
    }

    public float getTextSkewX() {
        return nGetTextSkewX(this.mNativePaint);
    }

    public void setTextSkewX(float skewX) {
        nSetTextSkewX(this.mNativePaint, skewX);
    }

    public float getLetterSpacing() {
        return nGetLetterSpacing(this.mNativePaint);
    }

    public void setLetterSpacing(float letterSpacing) {
        nSetLetterSpacing(this.mNativePaint, letterSpacing);
    }

    public float getWordSpacing() {
        return nGetWordSpacing(this.mNativePaint);
    }

    public void setWordSpacing(float wordSpacing) {
        nSetWordSpacing(this.mNativePaint, wordSpacing);
    }

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public void setFontFeatureSettings(String settings) {
        if (settings != null && settings.equals("")) {
            settings = null;
        }
        if (settings != null || this.mFontFeatureSettings != null) {
            if (settings != null && settings.equals(this.mFontFeatureSettings)) {
                return;
            }
            this.mFontFeatureSettings = settings;
            nSetFontFeatureSettings(this.mNativePaint, settings);
        }
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public boolean setFontVariationSettings(String fontVariationSettings) {
        String settings = TextUtils.nullIfEmpty(fontVariationSettings);
        if (settings == this.mFontVariationSettings || (settings != null && settings.equals(this.mFontVariationSettings))) {
            return true;
        }
        if (settings == null || settings.length() == 0) {
            this.mFontVariationSettings = null;
            setTypeface(Typeface.createFromTypefaceWithVariation(this.mTypeface, Collections.emptyList()));
            return true;
        }
        Typeface targetTypeface = this.mTypeface == null ? Typeface.DEFAULT : this.mTypeface;
        FontVariationAxis[] axes = FontVariationAxis.fromFontVariationSettings(settings);
        ArrayList<FontVariationAxis> filteredAxes = new ArrayList<>();
        for (FontVariationAxis axis : axes) {
            if (targetTypeface.isSupportedAxes(axis.getOpenTypeTagValue())) {
                filteredAxes.add(axis);
            }
        }
        if (filteredAxes.isEmpty()) {
            return false;
        }
        this.mFontVariationSettings = settings;
        setTypeface(Typeface.createFromTypefaceWithVariation(targetTypeface, filteredAxes));
        return true;
    }

    public int getStartHyphenEdit() {
        return nGetStartHyphenEdit(this.mNativePaint);
    }

    public int getEndHyphenEdit() {
        return nGetEndHyphenEdit(this.mNativePaint);
    }

    public void setStartHyphenEdit(int startHyphen) {
        nSetStartHyphenEdit(this.mNativePaint, startHyphen);
    }

    public void setEndHyphenEdit(int endHyphen) {
        nSetEndHyphenEdit(this.mNativePaint, endHyphen);
    }

    public float ascent() {
        return nAscent(this.mNativePaint);
    }

    public float descent() {
        return nDescent(this.mNativePaint);
    }

    public static class FontMetrics {
        public float ascent;
        public float bottom;
        public float descent;
        public float leading;
        public float top;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof FontMetrics)) {
                return false;
            }
            FontMetrics that = (FontMetrics) o;
            if (that.top == this.top && that.ascent == this.ascent && that.descent == this.descent && that.bottom == this.bottom && that.leading == this.leading) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.top), Float.valueOf(this.ascent), Float.valueOf(this.descent), Float.valueOf(this.bottom), Float.valueOf(this.leading));
        }

        public String toString() {
            return "FontMetrics{top=" + this.top + ", ascent=" + this.ascent + ", descent=" + this.descent + ", bottom=" + this.bottom + ", leading=" + this.leading + '}';
        }
    }

    public float getFontMetrics(FontMetrics metrics) {
        return nGetFontMetrics(this.mNativePaint, metrics, false);
    }

    public FontMetrics getFontMetrics() {
        FontMetrics fm = new FontMetrics();
        getFontMetrics(fm);
        return fm;
    }

    public void getFontMetricsForLocale(FontMetrics metrics) {
        nGetFontMetrics(this.mNativePaint, metrics, true);
    }

    public void getFontMetricsInt(CharSequence text, int start, int count, int contextStart, int contextCount, boolean isRtl, FontMetricsInt outMetrics) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (start < 0 || start >= text.length()) {
            throw new IllegalArgumentException("start argument is out of bounds.");
        }
        if (count < 0 || start + count > text.length()) {
            throw new IllegalArgumentException("count argument is out of bounds.");
        }
        if (contextStart < 0 || contextStart >= text.length()) {
            throw new IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (contextCount < 0 || contextStart + contextCount > text.length()) {
            throw new IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (outMetrics == null) {
            throw new IllegalArgumentException("outMetrics must not be null.");
        }
        if (count == 0) {
            getFontMetricsInt(outMetrics);
            return;
        }
        if (text instanceof String) {
            nGetFontMetricsIntForText(this.mNativePaint, (String) text, start, count, contextStart, contextCount, isRtl, outMetrics);
            return;
        }
        char[] buf = TemporaryBuffer.obtain(contextCount);
        try {
            TextUtils.getChars(text, contextStart, contextStart + contextCount, buf, 0);
            nGetFontMetricsIntForText(this.mNativePaint, buf, start - contextStart, count, 0, contextCount, isRtl, outMetrics);
        } finally {
            TemporaryBuffer.recycle(buf);
        }
    }

    public void getFontMetricsInt(char[] text, int start, int count, int contextStart, int contextCount, boolean isRtl, FontMetricsInt outMetrics) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (start < 0 || start >= text.length) {
            throw new IllegalArgumentException("start argument is out of bounds.");
        }
        if (count < 0 || start + count > text.length) {
            throw new IllegalArgumentException("count argument is out of bounds.");
        }
        if (contextStart < 0 || contextStart >= text.length) {
            throw new IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (contextCount < 0 || contextStart + contextCount > text.length) {
            throw new IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (outMetrics == null) {
            throw new IllegalArgumentException("outMetrics must not be null.");
        }
        if (count == 0) {
            getFontMetricsInt(outMetrics);
        } else {
            nGetFontMetricsIntForText(this.mNativePaint, text, start, count, contextStart, contextCount, isRtl, outMetrics);
        }
    }

    public static class FontMetricsInt {
        public int ascent;
        public int bottom;
        public int descent;
        public int leading;
        public int top;

        public void set(FontMetricsInt fontMetricsInt) {
            this.top = fontMetricsInt.top;
            this.ascent = fontMetricsInt.ascent;
            this.descent = fontMetricsInt.descent;
            this.bottom = fontMetricsInt.bottom;
            this.leading = fontMetricsInt.leading;
        }

        public void set(FontMetrics fontMetrics) {
            this.top = (int) Math.floor(fontMetrics.top);
            this.ascent = Math.round(fontMetrics.ascent);
            this.descent = Math.round(fontMetrics.descent);
            this.bottom = (int) Math.ceil(fontMetrics.bottom);
            this.leading = Math.round(fontMetrics.leading);
        }

        public String toString() {
            return "FontMetricsInt: top=" + this.top + " ascent=" + this.ascent + " descent=" + this.descent + " bottom=" + this.bottom + " leading=" + this.leading;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FontMetricsInt)) {
                return false;
            }
            FontMetricsInt that = (FontMetricsInt) o;
            return this.top == that.top && this.ascent == that.ascent && this.descent == that.descent && this.bottom == that.bottom && this.leading == that.leading;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.top), Integer.valueOf(this.ascent), Integer.valueOf(this.descent), Integer.valueOf(this.bottom), Integer.valueOf(this.leading));
        }
    }

    public int getFontMetricsInt(FontMetricsInt fmi) {
        return nGetFontMetricsInt(this.mNativePaint, fmi, false);
    }

    public FontMetricsInt getFontMetricsInt() {
        FontMetricsInt fm = new FontMetricsInt();
        getFontMetricsInt(fm);
        return fm;
    }

    public void getFontMetricsIntForLocale(FontMetricsInt metrics) {
        nGetFontMetricsInt(this.mNativePaint, metrics, true);
    }

    public static final class RunInfo {
        private int mClusterCount = 0;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int clusterCount) {
            this.mClusterCount = clusterCount;
        }
    }

    public float getFontSpacing() {
        return getFontMetrics(null);
    }

    public float measureText(char[] text, int index, int count) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((index | count) < 0 || index + count > text.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (text.length == 0 || count == 0) {
            return 0.0f;
        }
        int oldFlag = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (this.mHasCompatScaling) {
                float oldSize = getTextSize();
                setTextSize(this.mCompatScaling * oldSize);
                float w = nGetTextAdvances(this.mNativePaint, text, index, count, index, count, this.mBidiFlags, (float[]) null, 0);
                setTextSize(oldSize);
                return (float) Math.ceil(this.mInvCompatScaling * w);
            }
            return (float) Math.ceil(nGetTextAdvances(this.mNativePaint, text, index, count, index, count, this.mBidiFlags, (float[]) null, 0));
        } finally {
            setFlags(oldFlag);
        }
    }

    public float measureText(String text, int start, int end) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (text.length() == 0 || start == end) {
            return 0.0f;
        }
        int oldFlag = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (this.mHasCompatScaling) {
                float oldSize = getTextSize();
                setTextSize(this.mCompatScaling * oldSize);
                float w = nGetTextAdvances(this.mNativePaint, text, start, end, start, end, this.mBidiFlags, (float[]) null, 0);
                setTextSize(oldSize);
                return (float) Math.ceil(this.mInvCompatScaling * w);
            }
            return (float) Math.ceil(nGetTextAdvances(this.mNativePaint, text, start, end, start, end, this.mBidiFlags, (float[]) null, 0));
        } finally {
            setFlags(oldFlag);
        }
    }

    public float measureText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        return measureText(text, 0, text.length());
    }

    public float measureText(CharSequence text, int start, int end) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (text.length() == 0 || start == end) {
            return 0.0f;
        }
        if (text instanceof String) {
            return measureText((String) text, start, end);
        }
        if ((text instanceof SpannedString) || (text instanceof SpannableString)) {
            return measureText(text.toString(), start, end);
        }
        if (text instanceof GraphicsOperations) {
            return ((GraphicsOperations) text).measureText(start, end, this);
        }
        char[] buf = TemporaryBuffer.obtain(end - start);
        TextUtils.getChars(text, start, end, buf, 0);
        float result = measureText(buf, 0, end - start);
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public int breakText(char[] text, int index, int count, float maxWidth, float[] measuredWidth) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (index < 0 || text.length - index < Math.abs(count)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (text.length == 0 || count == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, text, index, count, maxWidth, this.mBidiFlags, measuredWidth);
        }
        float oldSize = getTextSize();
        setTextSize(this.mCompatScaling * oldSize);
        int res = nBreakText(this.mNativePaint, text, index, count, maxWidth * this.mCompatScaling, this.mBidiFlags, measuredWidth);
        setTextSize(oldSize);
        if (measuredWidth != null) {
            measuredWidth[0] = measuredWidth[0] * this.mInvCompatScaling;
        }
        return res;
    }

    public int breakText(CharSequence text, int start, int end, boolean measureForwards, float maxWidth, float[] measuredWidth) {
        int result;
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (text.length() == 0 || start == end) {
            return 0;
        }
        if (start == 0 && (text instanceof String) && end == text.length()) {
            return breakText((String) text, measureForwards, maxWidth, measuredWidth);
        }
        char[] buf = TemporaryBuffer.obtain(end - start);
        TextUtils.getChars(text, start, end, buf, 0);
        if (measureForwards) {
            result = breakText(buf, 0, end - start, maxWidth, measuredWidth);
        } else {
            int result2 = end - start;
            result = breakText(buf, 0, -result2, maxWidth, measuredWidth);
        }
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public int breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (text.length() == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, text, measureForwards, maxWidth, this.mBidiFlags, measuredWidth);
        }
        float oldSize = getTextSize();
        setTextSize(this.mCompatScaling * oldSize);
        int res = nBreakText(this.mNativePaint, text, measureForwards, maxWidth * this.mCompatScaling, this.mBidiFlags, measuredWidth);
        setTextSize(oldSize);
        if (measuredWidth != null) {
            measuredWidth[0] = measuredWidth[0] * this.mInvCompatScaling;
        }
        return res;
    }

    public int getTextWidths(char[] text, int index, int count, float[] widths) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((index | count) < 0 || index + count > text.length || count > widths.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (text.length == 0 || count == 0) {
            return 0;
        }
        int oldFlag = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, text, index, count, index, count, this.mBidiFlags, widths, 0);
                return count;
            }
            float oldSize = getTextSize();
            setTextSize(this.mCompatScaling * oldSize);
            nGetTextAdvances(this.mNativePaint, text, index, count, index, count, this.mBidiFlags, widths, 0);
            setTextSize(oldSize);
            for (int i = 0; i < count; i++) {
                widths[i] = widths[i] * this.mInvCompatScaling;
            }
            return count;
        } finally {
            setFlags(oldFlag);
        }
    }

    public int getTextWidths(CharSequence text, int start, int end, float[] widths) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (end - start > widths.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (text.length() == 0 || start == end) {
            return 0;
        }
        if (text instanceof String) {
            return getTextWidths((String) text, start, end, widths);
        }
        if ((text instanceof SpannedString) || (text instanceof SpannableString)) {
            return getTextWidths(text.toString(), start, end, widths);
        }
        if (text instanceof GraphicsOperations) {
            return ((GraphicsOperations) text).getTextWidths(start, end, widths, this);
        }
        char[] buf = TemporaryBuffer.obtain(end - start);
        TextUtils.getChars(text, start, end, buf, 0);
        int result = getTextWidths(buf, 0, end - start, widths);
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public int getTextWidths(String text, int start, int end, float[] widths) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (end - start > widths.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (text.length() == 0 || start == end) {
            return 0;
        }
        int oldFlag = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, text, start, end, start, end, this.mBidiFlags, widths, 0);
                return end - start;
            }
            float oldSize = getTextSize();
            setTextSize(this.mCompatScaling * oldSize);
            nGetTextAdvances(this.mNativePaint, text, start, end, start, end, this.mBidiFlags, widths, 0);
            setTextSize(oldSize);
            for (int i = 0; i < end - start; i++) {
                widths[i] = widths[i] * this.mInvCompatScaling;
            }
            int i2 = end - start;
            return i2;
        } finally {
            setFlags(oldFlag);
        }
    }

    public int getTextWidths(String text, float[] widths) {
        return getTextWidths(text, 0, text.length(), widths);
    }

    public float getTextRunAdvances(char[] chars, int index, int count, int contextIndex, int contextCount, boolean isRtl, float[] advances, int advancesIndex) {
        if (chars == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((index | count | contextIndex | contextCount | advancesIndex | (index - contextIndex) | (contextCount - count) | ((contextIndex + contextCount) - (index + count)) | (chars.length - (contextIndex + contextCount)) | (advances == null ? 0 : advances.length - (advancesIndex + count))) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (chars.length == 0 || count == 0) {
            return 0.0f;
        }
        if (!this.mHasCompatScaling) {
            return nGetTextAdvances(this.mNativePaint, chars, index, count, contextIndex, contextCount, isRtl ? 5 : 4, advances, advancesIndex);
        }
        float oldSize = getTextSize();
        setTextSize(this.mCompatScaling * oldSize);
        float res = nGetTextAdvances(this.mNativePaint, chars, index, count, contextIndex, contextCount, isRtl ? 5 : 4, advances, advancesIndex);
        setTextSize(oldSize);
        if (advances != null) {
            int i = advancesIndex;
            int e = i + count;
            while (i < e) {
                advances[i] = advances[i] * this.mInvCompatScaling;
                i++;
            }
        }
        return this.mInvCompatScaling * res;
    }

    public int getTextRunCursor(char[] cArr, int i, int i2, boolean z, int i3, int i4) {
        int i5 = i + i2;
        if ((i | i5 | i3 | (i5 - i) | (i3 - i) | (i5 - i3) | (cArr.length - i5) | i4) < 0 || i4 > 4) {
            throw new IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, cArr, i, i2, z ? 1 : 0, i3, i4);
    }

    public int getTextRunCursor(CharSequence text, int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt) {
        if ((text instanceof String) || (text instanceof SpannedString) || (text instanceof SpannableString)) {
            return getTextRunCursor(text.toString(), contextStart, contextEnd, isRtl, offset, cursorOpt);
        }
        if (text instanceof GraphicsOperations) {
            return ((GraphicsOperations) text).getTextRunCursor(contextStart, contextEnd, isRtl, offset, cursorOpt, this);
        }
        int contextLen = contextEnd - contextStart;
        char[] buf = TemporaryBuffer.obtain(contextLen);
        TextUtils.getChars(text, contextStart, contextEnd, buf, 0);
        int relPos = getTextRunCursor(buf, 0, contextLen, isRtl, offset - contextStart, cursorOpt);
        TemporaryBuffer.recycle(buf);
        if (relPos == -1) {
            return -1;
        }
        return relPos + contextStart;
    }

    public int getTextRunCursor(String str, int i, int i2, boolean z, int i3, int i4) {
        if ((i | i2 | i3 | (i2 - i) | (i3 - i) | (i2 - i3) | (str.length() - i2) | i4) < 0 || i4 > 4) {
            throw new IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, str, i, i2, z ? 1 : 0, i3, i4);
    }

    public void getTextPath(char[] text, int index, int count, float x, float y, Path path) {
        if ((index | count) >= 0 && index + count <= text.length) {
            nGetTextPath(this.mNativePaint, this.mBidiFlags, text, index, count, x, y, path.mutateNI());
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void getTextPath(String text, int start, int end, float x, float y, Path path) {
        if ((start | end | (end - start) | (text.length() - end)) >= 0) {
            nGetTextPath(this.mNativePaint, this.mBidiFlags, text, start, end, x, y, path.mutateNI());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void getTextBounds(String text, int start, int end, Rect bounds) {
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (bounds == null) {
            throw new NullPointerException("need bounds Rect");
        }
        nGetStringBounds(this.mNativePaint, text, start, end, this.mBidiFlags, bounds);
    }

    public void getTextBounds(CharSequence text, int start, int end, Rect bounds) {
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (bounds == null) {
            throw new NullPointerException("need bounds Rect");
        }
        char[] buf = TemporaryBuffer.obtain(end - start);
        TextUtils.getChars(text, start, end, buf, 0);
        getTextBounds(buf, 0, end - start, bounds);
        TemporaryBuffer.recycle(buf);
    }

    public void getTextBounds(char[] text, int index, int count, Rect bounds) {
        if ((index | count) < 0 || index + count > text.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (bounds == null) {
            throw new NullPointerException("need bounds Rect");
        }
        nGetCharArrayBounds(this.mNativePaint, text, index, count, this.mBidiFlags, bounds);
    }

    public boolean hasGlyph(String string) {
        return nHasGlyph(this.mNativePaint, this.mBidiFlags, string);
    }

    public float getRunAdvance(char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((contextStart | start | offset | end | contextEnd | (start - contextStart) | (offset - start) | (end - offset) | (contextEnd - end) | (text.length - contextEnd)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (end == start) {
            return 0.0f;
        }
        return nGetRunAdvance(this.mNativePaint, text, start, end, contextStart, contextEnd, isRtl, offset);
    }

    public float getRunAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((contextStart | start | offset | end | contextEnd | (start - contextStart) | (offset - start) | (end - offset) | (contextEnd - end) | (text.length() - contextEnd)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (end == start) {
            return 0.0f;
        }
        char[] buf = TemporaryBuffer.obtain(contextEnd - contextStart);
        TextUtils.getChars(text, contextStart, contextEnd, buf, 0);
        float result = getRunAdvance(buf, start - contextStart, end - contextStart, 0, contextEnd - contextStart, isRtl, offset - contextStart);
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public float getRunCharacterAdvance(char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset, float[] advances, int advancesIndex) {
        return getRunCharacterAdvance(text, start, end, contextStart, contextEnd, isRtl, offset, advances, advancesIndex, (RectF) null, (RunInfo) null);
    }

    public float getRunCharacterAdvance(char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset, float[] advances, int advancesIndex, RectF drawBounds, RunInfo runInfo) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (contextStart < 0 || contextEnd > text.length) {
            throw new IndexOutOfBoundsException("Invalid Context Range: " + contextStart + ", " + contextEnd + " must be in 0, " + text.length);
        }
        if (start < contextStart || contextEnd < end) {
            throw new IndexOutOfBoundsException("Invalid start/end range: " + start + ", " + end + " must be in " + contextStart + ", " + contextEnd);
        }
        if (offset < start || end < offset) {
            throw new IndexOutOfBoundsException("Invalid offset position: " + offset + " must be in " + start + ", " + end);
        }
        if (advances != null && advances.length < (advancesIndex - start) + end) {
            throw new IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + advances.length + " advanceIndex: " + advancesIndex + " needed space: " + (offset - start));
        }
        if (end == start) {
            if (runInfo != null) {
                runInfo.setClusterCount(0);
                return 0.0f;
            }
            return 0.0f;
        }
        return nGetRunCharacterAdvance(this.mNativePaint, text, start, end, contextStart, contextEnd, isRtl, offset, advances, advancesIndex, drawBounds, runInfo);
    }

    public float getRunCharacterAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset, float[] advances, int advancesIndex) {
        return getRunCharacterAdvance(text, start, end, contextStart, contextEnd, isRtl, offset, advances, advancesIndex, (RectF) null, (RunInfo) null);
    }

    public float getRunCharacterAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset, float[] advances, int advancesIndex, RectF drawBounds, RunInfo runInfo) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (contextStart < 0 || contextEnd > text.length()) {
            throw new IndexOutOfBoundsException("Invalid Context Range: " + contextStart + ", " + contextEnd + " must be in 0, " + text.length());
        }
        if (start < contextStart || contextEnd < end) {
            throw new IndexOutOfBoundsException("Invalid start/end range: " + start + ", " + end + " must be in " + contextStart + ", " + contextEnd);
        }
        if (offset < start || end < offset) {
            throw new IndexOutOfBoundsException("Invalid offset position: " + offset + " must be in " + start + ", " + end);
        }
        if (advances != null && advances.length < (advancesIndex - start) + end) {
            throw new IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + advances.length + " advanceIndex: " + advancesIndex + " needed space: " + (offset - start));
        }
        if (end == start) {
            return 0.0f;
        }
        char[] buf = TemporaryBuffer.obtain(contextEnd - contextStart);
        TextUtils.getChars(text, contextStart, contextEnd, buf, 0);
        float result = getRunCharacterAdvance(buf, start - contextStart, end - contextStart, 0, contextEnd - contextStart, isRtl, offset - contextStart, advances, advancesIndex, drawBounds, runInfo);
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public int getOffsetForAdvance(char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((contextStart | start | end | contextEnd | (start - contextStart) | (end - start) | (contextEnd - end) | (text.length - contextEnd)) >= 0) {
            return nGetOffsetForAdvance(this.mNativePaint, text, start, end, contextStart, contextEnd, isRtl, advance);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getOffsetForAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((contextStart | start | end | contextEnd | (start - contextStart) | (end - start) | (contextEnd - end) | (text.length() - contextEnd)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        char[] buf = TemporaryBuffer.obtain(contextEnd - contextStart);
        TextUtils.getChars(text, contextStart, contextEnd, buf, 0);
        int result = getOffsetForAdvance(buf, start - contextStart, end - contextStart, 0, contextEnd - contextStart, isRtl, advance) + contextStart;
        TemporaryBuffer.recycle(buf);
        return result;
    }

    public boolean equalsForTextMeasurement(Paint other) {
        return nEqualsForTextMeasurement(this.mNativePaint, other.mNativePaint);
    }

    public enum MyanmarEncoding {
        ME_UNICODE(0),
        ME_ZAWGYI(1),
        ME_AUTO(2);

        final int nativeInt;

        MyanmarEncoding(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public MyanmarEncoding getMyanmarEncoding() {
        return this.mMyanmarEncoding;
    }

    public void setMyanmarEncoding(MyanmarEncoding myanmarEncoding) {
        this.mUseCustomMyanmarEncoding = true;
        if (this.mMyanmarEncoding != myanmarEncoding) {
            this.mMyanmarEncoding = myanmarEncoding;
            nSetMyanmarEncoding(this.mNativePaint, myanmarEncoding.nativeInt);
        }
    }

    public void setMyanmarEncoding(Locale locale) {
        Locale myLocale;
        if (!this.mUseCustomMyanmarEncoding && locale != null) {
            MyanmarEncoding myanmarEncoding = MyanmarEncoding.ME_UNICODE;
            String country = locale.getCountry();
            if (LocaleList.getDefault().size() > 1 && ((country == null || "".equals(country)) && (myLocale = LocaleList.getDefault().get(1)) != null)) {
                country = myLocale.getCountry();
            }
            if ("ZG".equals(country)) {
                myanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
            }
            if (this.mMyanmarEncoding != myanmarEncoding) {
                this.mMyanmarEncoding = myanmarEncoding;
                nSetMyanmarEncoding(this.mNativePaint, myanmarEncoding.nativeInt);
            }
        }
    }

    public float getHCTStrokeWidth() {
        return (getTextSize() <= 15.0f ? 1 : 2) + (getTextSize() * 0.04f);
    }
}

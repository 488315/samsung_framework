package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class Drawable {
    private int mLayoutDirection;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    static final BlendMode DEFAULT_BLEND_MODE = BlendMode.SRC_IN;
    private static Class<?> SprClass = null;
    private static Method SprCreateFromStream = null;
    private int[] mStateSet = StateSet.WILD_CARD;
    private int mLevel = 0;
    private int mChangingConfigurations = 0;
    private Rect mBounds = ZERO_BOUNDS_RECT;
    private WeakReference<Callback> mCallback = null;
    private boolean mVisible = true;
    protected int mSrcDensityOverride = 0;
    private boolean mSetBlendModeInvoked = false;
    private boolean mSetTintModeInvoked = false;

    public interface Callback {
        void invalidateDrawable(Drawable drawable);

        void scheduleDrawable(Drawable drawable, Runnable runnable, long j);

        void unscheduleDrawable(Drawable drawable, Runnable runnable);
    }

    public abstract void draw(Canvas canvas);

    @Deprecated
    public abstract int getOpacity();

    public abstract void setAlpha(int i);

    public abstract void setColorFilter(ColorFilter colorFilter);

    public void setBounds(int left, int top, int right, int bottom) {
        Rect oldBounds = this.mBounds;
        if (oldBounds == ZERO_BOUNDS_RECT) {
            Rect rect = new Rect();
            this.mBounds = rect;
            oldBounds = rect;
        }
        if (oldBounds.left != left || oldBounds.top != top || oldBounds.right != right || oldBounds.bottom != bottom) {
            if (!oldBounds.isEmpty()) {
                invalidateSelf();
            }
            this.mBounds.set(left, top, right, bottom);
            onBoundsChange(this.mBounds);
        }
    }

    public void setBounds(Rect bounds) {
        setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public final void copyBounds(Rect bounds) {
        bounds.set(this.mBounds);
    }

    public final Rect copyBounds() {
        return new Rect(this.mBounds);
    }

    public final Rect getBounds() {
        if (this.mBounds == ZERO_BOUNDS_RECT) {
            this.mBounds = new Rect();
        }
        return this.mBounds;
    }

    public Rect getDirtyBounds() {
        return getBounds();
    }

    public void setChangingConfigurations(int configs) {
        this.mChangingConfigurations = configs;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    @Deprecated
    public void setDither(boolean dither) {
    }

    public void setFilterBitmap(boolean filter) {
    }

    public boolean isFilterBitmap() {
        return false;
    }

    public final void setCallback(Callback cb) {
        this.mCallback = cb != null ? new WeakReference<>(cb) : null;
    }

    public Callback getCallback() {
        if (this.mCallback != null) {
            return this.mCallback.get();
        }
        return null;
    }

    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleSelf(Runnable what, long when) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleSelf(Runnable what) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    public int getLayoutDirection() {
        return this.mLayoutDirection;
    }

    public final boolean setLayoutDirection(int layoutDirection) {
        if (this.mLayoutDirection != layoutDirection) {
            this.mLayoutDirection = layoutDirection;
            return onLayoutDirectionChanged(layoutDirection);
        }
        return false;
    }

    public boolean onLayoutDirectionChanged(int layoutDirection) {
        return false;
    }

    public int getAlpha() {
        return 255;
    }

    public void setXfermode(Xfermode mode) {
    }

    @Deprecated
    public void setColorFilter(int color, PorterDuff.Mode mode) {
        if (getColorFilter() instanceof PorterDuffColorFilter) {
            PorterDuffColorFilter existing = (PorterDuffColorFilter) getColorFilter();
            if (existing.getColor() == color && existing.getMode() == mode) {
                return;
            }
        }
        setColorFilter(new PorterDuffColorFilter(color, mode));
    }

    public void setTint(int tintColor) {
        setTintList(ColorStateList.valueOf(tintColor));
    }

    public void setTintList(ColorStateList tint) {
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        if (!this.mSetTintModeInvoked) {
            this.mSetTintModeInvoked = true;
            BlendMode mode = tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null;
            setTintBlendMode(mode != null ? mode : DEFAULT_BLEND_MODE);
            this.mSetTintModeInvoked = false;
        }
    }

    public void setTintBlendMode(BlendMode blendMode) {
        if (!this.mSetBlendModeInvoked) {
            this.mSetBlendModeInvoked = true;
            PorterDuff.Mode mode = BlendMode.blendModeToPorterDuffMode(blendMode);
            setTintMode(mode != null ? mode : DEFAULT_TINT_MODE);
            this.mSetBlendModeInvoked = false;
        }
    }

    public ColorFilter getColorFilter() {
        return null;
    }

    public void clearColorFilter() {
        setColorFilter(null);
    }

    public void setHotspot(float x, float y) {
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
    }

    public void getHotspotBounds(Rect outRect) {
        outRect.set(getBounds());
    }

    public boolean isProjected() {
        return false;
    }

    public boolean isStateful() {
        return false;
    }

    public boolean hasFocusStateSpecified() {
        return false;
    }

    public boolean setState(int[] stateSet) {
        if (!Arrays.equals(this.mStateSet, stateSet)) {
            this.mStateSet = stateSet;
            return onStateChange(stateSet);
        }
        return false;
    }

    public int[] getState() {
        return this.mStateSet;
    }

    public void jumpToCurrentState() {
    }

    public Drawable getCurrent() {
        return this;
    }

    public final boolean setLevel(int level) {
        if (this.mLevel != level) {
            this.mLevel = level;
            return onLevelChange(level);
        }
        return false;
    }

    public final int getLevel() {
        return this.mLevel;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = this.mVisible != visible;
        if (changed) {
            this.mVisible = visible;
            invalidateSelf();
        }
        return changed;
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public void setAutoMirrored(boolean mirrored) {
    }

    public boolean isAutoMirrored() {
        return false;
    }

    public void applyTheme(Resources.Theme t) {
    }

    public boolean canApplyTheme() {
        return false;
    }

    public static int resolveOpacity(int op1, int op2) {
        if (op1 == op2) {
            return op1;
        }
        if (op1 == 0 || op2 == 0) {
            return 0;
        }
        if (op1 == -3 || op2 == -3) {
            return -3;
        }
        if (op1 == -2 || op2 == -2) {
            return -2;
        }
        return -1;
    }

    public Region getTransparentRegion() {
        return null;
    }

    protected boolean onStateChange(int[] state) {
        return false;
    }

    protected boolean onLevelChange(int level) {
        return false;
    }

    protected void onBoundsChange(Rect bounds) {
    }

    public int getIntrinsicWidth() {
        return -1;
    }

    public int getIntrinsicHeight() {
        return -1;
    }

    public int getMinimumWidth() {
        int intrinsicWidth = getIntrinsicWidth();
        if (intrinsicWidth > 0) {
            return intrinsicWidth;
        }
        return 0;
    }

    public int getMinimumHeight() {
        int intrinsicHeight = getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            return intrinsicHeight;
        }
        return 0;
    }

    public boolean getPadding(Rect padding) {
        padding.set(0, 0, 0, 0);
        return false;
    }

    public Insets getOpticalInsets() {
        return Insets.NONE;
    }

    public void getOutline(Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(0.0f);
    }

    public Drawable mutate() {
        return this;
    }

    public void clearMutated() {
    }

    public static Drawable createFromStream(InputStream is, String srcName) {
        Trace.traceBegin(8192L, srcName != null ? srcName : "Unknown drawable");
        try {
            return createFromResourceStream(null, null, is, srcName);
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable createFromResourceStream(Resources res, TypedValue value, InputStream is, String srcName) {
        Trace.traceBegin(8192L, srcName != null ? srcName : "Unknown drawable");
        try {
            return createFromResourceStream(res, value, is, srcName, null);
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x001b, code lost:
    
        if (r13.endsWith(".spr") == false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable createFromResourceStream(android.content.res.Resources r10, android.util.TypedValue r11, java.io.InputStream r12, java.lang.String r13, android.graphics.BitmapFactory.Options r14) {
        /*
            r0 = 0
            if (r12 != 0) goto L4
            return r0
        L4:
            r1 = 0
            if (r13 == 0) goto L1d
            boolean r2 = r13.isEmpty()     // Catch: java.lang.Exception -> L78
            if (r2 != 0) goto L1d
            java.lang.String r2 = ".bmp"
            boolean r2 = r13.endsWith(r2)     // Catch: java.lang.Exception -> L78
            if (r2 != 0) goto L1d
            java.lang.String r2 = ".spr"
            boolean r2 = r13.endsWith(r2)     // Catch: java.lang.Exception -> L78
            if (r2 == 0) goto L77
        L1d:
            boolean r2 = r12.markSupported()     // Catch: java.lang.Exception -> L78
            if (r2 == 0) goto L77
            r2 = 3
            byte[] r3 = new byte[r2]     // Catch: java.lang.Exception -> L78
            r12.read(r3, r1, r2)     // Catch: java.lang.Exception -> L78
            r12.reset()     // Catch: java.lang.Exception -> L78
            r4 = r3[r1]     // Catch: java.lang.Exception -> L78
            r5 = 83
            if (r4 != r5) goto L77
            r4 = 1
            r5 = r3[r4]     // Catch: java.lang.Exception -> L78
            r6 = 80
            if (r5 != r6) goto L77
            r5 = 2
            r6 = r3[r5]     // Catch: java.lang.Exception -> L78
            r7 = 82
            if (r6 != r7) goto L77
            java.lang.Class<?> r6 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L78
            if (r6 != 0) goto L4c
            java.lang.String r6 = "com.samsung.android.graphics.spr.SemPathRenderingDrawable"
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: java.lang.Exception -> L78
            android.graphics.drawable.Drawable.SprClass = r6     // Catch: java.lang.Exception -> L78
        L4c:
            java.lang.reflect.Method r6 = android.graphics.drawable.Drawable.SprCreateFromStream     // Catch: java.lang.Exception -> L78
            if (r6 != 0) goto L68
            java.lang.Class<?> r6 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L78
            java.lang.String r7 = "createFromStream"
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch: java.lang.Exception -> L78
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r2[r1] = r8     // Catch: java.lang.Exception -> L78
            java.lang.Class<java.io.InputStream> r8 = java.io.InputStream.class
            r2[r4] = r8     // Catch: java.lang.Exception -> L78
            java.lang.Class<android.content.res.Resources> r4 = android.content.res.Resources.class
            r2[r5] = r4     // Catch: java.lang.Exception -> L78
            java.lang.reflect.Method r2 = r6.getMethod(r7, r2)     // Catch: java.lang.Exception -> L78
            android.graphics.drawable.Drawable.SprCreateFromStream = r2     // Catch: java.lang.Exception -> L78
        L68:
            java.lang.reflect.Method r2 = android.graphics.drawable.Drawable.SprCreateFromStream     // Catch: java.lang.Exception -> L78
            java.lang.Class<?> r4 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L78
            java.lang.Object[] r5 = new java.lang.Object[]{r13, r12, r10}     // Catch: java.lang.Exception -> L78
            java.lang.Object r2 = r2.invoke(r4, r5)     // Catch: java.lang.Exception -> L78
            android.graphics.drawable.Drawable r2 = (android.graphics.drawable.Drawable) r2     // Catch: java.lang.Exception -> L78
            return r2
        L77:
            goto L7c
        L78:
            r2 = move-exception
            r2.printStackTrace()
        L7c:
            if (r14 != 0) goto L83
            android.graphics.drawable.Drawable r0 = getBitmapDrawable(r10, r11, r12)
            return r0
        L83:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            int r1 = resolveDensity(r10, r1)
            r14.inScreenDensity = r1
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeResourceStream(r10, r11, r12, r2, r14)
            if (r1 == 0) goto Lb6
            byte[] r0 = r1.getNinePatchChunk()
            if (r0 == 0) goto La0
            boolean r3 = android.graphics.NinePatch.isNinePatchChunk(r0)
            if (r3 != 0) goto La2
        La0:
            r0 = 0
            r2 = 0
        La2:
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>()
            r9 = r3
            r1.getOpticalInsets(r9)
            r3 = r10
            r4 = r1
            r5 = r0
            r6 = r2
            r7 = r9
            r8 = r13
            android.graphics.drawable.Drawable r3 = drawableFromBitmap(r3, r4, r5, r6, r7, r8)
            return r3
        Lb6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.Drawable.createFromResourceStream(android.content.res.Resources, android.util.TypedValue, java.io.InputStream, java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.drawable.Drawable");
    }

    private static Drawable getBitmapDrawable(Resources res, TypedValue value, InputStream is) {
        ImageDecoder.Source source;
        try {
            if (value != null) {
                int density = 0;
                if (value.density == 0) {
                    density = 160;
                } else if (value.density != 65535) {
                    density = value.density;
                }
                source = ImageDecoder.createSource(res, is, density);
            } else {
                source = ImageDecoder.createSource(res, is);
            }
            return ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                    Drawable.lambda$getBitmapDrawable$1(imageDecoder, imageInfo, source2);
                }
            });
        } catch (IOException e) {
            Log.e("Drawable", "Unable to decode stream: " + e);
            return null;
        }
    }

    static /* synthetic */ void lambda$getBitmapDrawable$1(ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source src) {
        decoder.setAllocator(1);
        decoder.setOnPartialImageListener(new ImageDecoder.OnPartialImageListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnPartialImageListener
            public final boolean onPartialImage(ImageDecoder.DecodeException decodeException) {
                return Drawable.lambda$getBitmapDrawable$0(decodeException);
            }
        });
    }

    static /* synthetic */ boolean lambda$getBitmapDrawable$0(ImageDecoder.DecodeException e) {
        return e.getError() == 2;
    }

    public static Drawable createFromXml(Resources r, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createFromXml(r, parser, null);
    }

    public static Drawable createFromXml(Resources r, XmlPullParser parser, Resources.Theme theme) throws XmlPullParserException, IOException {
        return createFromXmlForDensity(r, parser, 0, theme);
    }

    public static Drawable createFromXmlForDensity(Resources r, XmlPullParser parser, int density, Resources.Theme theme) throws XmlPullParserException, IOException {
        int type;
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        Drawable drawable = createFromXmlInnerForDensity(r, parser, attrs, density, theme);
        if (drawable == null) {
            throw new RuntimeException("Unknown initial tag: " + parser.getName());
        }
        return drawable;
    }

    public static Drawable createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        return createFromXmlInner(r, parser, attrs, null);
    }

    public static Drawable createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        return createFromXmlInnerForDensity(r, parser, attrs, 0, theme);
    }

    static Drawable createFromXmlInnerForDensity(Resources r, XmlPullParser parser, AttributeSet attrs, int density, Resources.Theme theme) throws XmlPullParserException, IOException {
        return r.getDrawableInflater().inflateFromXmlForDensity(parser.getName(), parser, attrs, density, theme);
    }

    public static Drawable createFromPath(String pathName) {
        if (pathName == null) {
            return null;
        }
        Trace.traceBegin(8192L, pathName);
        try {
            FileInputStream stream = new FileInputStream(pathName);
            try {
                Drawable bitmapDrawable = getBitmapDrawable(null, null, stream);
                stream.close();
                return bitmapDrawable;
            } catch (Throwable th) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            return null;
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        inflate(r, parser, attrs, null);
    }

    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a = obtainAttributes(r, theme, attrs, R.styleable.Drawable);
        this.mVisible = a.getBoolean(0, this.mVisible);
        a.recycle();
    }

    void inflateWithAttributes(Resources r, XmlPullParser parser, TypedArray attrs, int visibleAttr) throws XmlPullParserException, IOException {
        this.mVisible = attrs.getBoolean(visibleAttr, this.mVisible);
    }

    final void setSrcDensityOverride(int density) {
        this.mSrcDensityOverride = density;
    }

    public static abstract class ConstantState {
        public abstract int getChangingConfigurations();

        public abstract Drawable newDrawable();

        public Drawable newDrawable(Resources res) {
            return newDrawable();
        }

        public Drawable newDrawable(Resources res, Resources.Theme theme) {
            return newDrawable(res);
        }

        public boolean canApplyTheme() {
            return false;
        }
    }

    public ConstantState getConstantState() {
        return null;
    }

    private static Drawable drawableFromBitmap(Resources res, Bitmap bm, byte[] np, Rect pad, Rect layoutBounds, String srcName) {
        if (np != null) {
            return new NinePatchDrawable(res, bm, np, pad, layoutBounds, srcName);
        }
        return new BitmapDrawable(res, bm);
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter tintFilter, ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        int color = tint.getColorForState(getState(), 0);
        if (tintFilter == null || tintFilter.getColor() != color || tintFilter.getMode() != tintMode) {
            return new PorterDuffColorFilter(color, tintMode);
        }
        return tintFilter;
    }

    BlendModeColorFilter updateBlendModeFilter(BlendModeColorFilter blendFilter, ColorStateList tint, BlendMode blendMode) {
        if (tint == null || blendMode == null) {
            return null;
        }
        int color = tint.getColorForState(getState(), 0);
        if (blendFilter == null || blendFilter.getColor() != color || blendFilter.getMode() != blendMode) {
            return new BlendModeColorFilter(color, blendMode);
        }
        return blendFilter;
    }

    protected static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    static float scaleFromDensity(float pixels, int sourceDensity, int targetDensity) {
        return (targetDensity * pixels) / sourceDensity;
    }

    static int scaleFromDensity(int pixels, int sourceDensity, int targetDensity, boolean isSize) {
        if (pixels == 0 || sourceDensity == targetDensity) {
            return pixels;
        }
        float result = (pixels * targetDensity) / sourceDensity;
        if (!isSize) {
            return (int) result;
        }
        int rounded = Math.round(result);
        if (rounded != 0) {
            return rounded;
        }
        if (pixels > 0) {
            return 1;
        }
        return -1;
    }

    static int resolveDensity(Resources r, int parentDensity) {
        int densityDpi = r == null ? parentDensity : r.getDisplayMetrics().densityDpi;
        if (densityDpi == 0) {
            return 160;
        }
        return densityDpi;
    }

    static void rethrowAsRuntimeException(Exception cause) throws RuntimeException {
        RuntimeException e = new RuntimeException(cause);
        e.setStackTrace(new StackTraceElement[0]);
        throw e;
    }

    public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }

    public static BlendMode parseBlendMode(int value, BlendMode defaultMode) {
        switch (value) {
            case 3:
                return BlendMode.SRC_OVER;
            case 5:
                return BlendMode.SRC_IN;
            case 9:
                return BlendMode.SRC_ATOP;
            case 14:
                return BlendMode.MODULATE;
            case 15:
                return BlendMode.SCREEN;
            case 16:
                return BlendMode.PLUS;
            default:
                return defaultMode;
        }
    }
}

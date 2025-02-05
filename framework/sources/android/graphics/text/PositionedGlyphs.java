package android.graphics.text;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import java.util.ArrayList;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class PositionedGlyphs {
    public static final float NO_OVERRIDE = Float.MIN_VALUE;
    private final ArrayList<Font> mFonts;
    private final long mLayoutPtr;
    private final float mXOffset;
    private final float mYOffset;

    @CriticalNative
    private static native float nGetAscent(long j);

    @CriticalNative
    private static native float nGetDescent(long j);

    @CriticalNative
    private static native boolean nGetFakeBold(long j, int i);

    @CriticalNative
    private static native boolean nGetFakeItalic(long j, int i);

    @CriticalNative
    private static native long nGetFont(long j, int i);

    @CriticalNative
    private static native int nGetGlyphCount(long j);

    @CriticalNative
    private static native int nGetGlyphId(long j, int i);

    @CriticalNative
    private static native float nGetItalicOverride(long j, int i);

    @CriticalNative
    private static native float nGetTotalAdvance(long j);

    @CriticalNative
    private static native float nGetWeightOverride(long j, int i);

    @CriticalNative
    private static native float nGetX(long j, int i);

    @CriticalNative
    private static native float nGetY(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nReleaseFunc();

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry REGISTRY = NativeAllocationRegistry.createMalloced(Typeface.class.getClassLoader(), PositionedGlyphs.nReleaseFunc());

        private NoImagePreloadHolder() {
        }
    }

    public float getAdvance() {
        return nGetTotalAdvance(this.mLayoutPtr);
    }

    public float getAscent() {
        return nGetAscent(this.mLayoutPtr);
    }

    public float getDescent() {
        return nGetDescent(this.mLayoutPtr);
    }

    public float getOffsetX() {
        return this.mXOffset;
    }

    public float getOffsetY() {
        return this.mYOffset;
    }

    public int glyphCount() {
        return nGetGlyphCount(this.mLayoutPtr);
    }

    public Font getFont(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return this.mFonts.get(index);
    }

    public int getGlyphId(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return nGetGlyphId(this.mLayoutPtr, index);
    }

    public float getGlyphX(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return nGetX(this.mLayoutPtr, index) + this.mXOffset;
    }

    public float getGlyphY(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return nGetY(this.mLayoutPtr, index) + this.mYOffset;
    }

    public boolean getFakeBold(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return nGetFakeBold(this.mLayoutPtr, index);
    }

    public boolean getFakeItalic(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        return nGetFakeItalic(this.mLayoutPtr, index);
    }

    public float getWeightOverride(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        float value = nGetWeightOverride(this.mLayoutPtr, index);
        if (value == -1.0f) {
            return Float.MIN_VALUE;
        }
        return value;
    }

    public float getItalicOverride(int index) {
        Preconditions.checkArgumentInRange(index, 0, glyphCount() - 1, "index");
        float value = nGetItalicOverride(this.mLayoutPtr, index);
        if (value == -1.0f) {
            return Float.MIN_VALUE;
        }
        return value;
    }

    public PositionedGlyphs(long layoutPtr, float xOffset, float yOffset) {
        this.mLayoutPtr = layoutPtr;
        int glyphCount = nGetGlyphCount(layoutPtr);
        this.mFonts = new ArrayList<>(glyphCount);
        this.mXOffset = xOffset;
        this.mYOffset = yOffset;
        long prevPtr = 0;
        Font prevFont = null;
        for (int i = 0; i < glyphCount; i++) {
            long ptr = nGetFont(layoutPtr, i);
            if (prevPtr != ptr) {
                prevPtr = ptr;
                prevFont = new Font(ptr);
            }
            this.mFonts.add(prevFont);
        }
        NoImagePreloadHolder.REGISTRY.registerNativeAllocation(this, layoutPtr);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PositionedGlyphs)) {
            return false;
        }
        PositionedGlyphs that = (PositionedGlyphs) o;
        if (this.mXOffset != that.mXOffset || this.mYOffset != that.mYOffset || glyphCount() != that.glyphCount()) {
            return false;
        }
        for (int i = 0; i < glyphCount(); i++) {
            if (getGlyphId(i) != that.getGlyphId(i) || getGlyphX(i) != that.getGlyphX(i) || getGlyphY(i) != that.getGlyphY(i) || !getFont(i).equals(that.getFont(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = Objects.hash(Float.valueOf(this.mXOffset), Float.valueOf(this.mYOffset));
        for (int i = 0; i < glyphCount(); i++) {
            hashCode = Objects.hash(Integer.valueOf(hashCode), Integer.valueOf(getGlyphId(i)), Float.valueOf(getGlyphX(i)), Float.valueOf(getGlyphY(i)), getFont(i));
        }
        return hashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        for (int i = 0; i < glyphCount(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("[ ID = " + getGlyphId(i) + ", pos = (" + getGlyphX(i) + "," + getGlyphY(i) + ") font = " + getFont(i) + " ]");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return "PositionedGlyphs{glyphs = " + sb.toString() + ", mXOffset=" + this.mXOffset + ", mYOffset=" + this.mYOffset + '}';
    }
}

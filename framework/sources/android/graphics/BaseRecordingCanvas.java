package android.graphics;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.fonts.Font;
import android.graphics.text.MeasuredText;
import android.text.GraphicsOperations;
import android.text.MeasuredParagraph;
import android.text.PrecomputedText;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.FastNative;
import java.util.Objects;

/* loaded from: classes.dex */
public class BaseRecordingCanvas extends Canvas {
    @FastNative
    private static native void nDrawArc(long j, float f, float f2, float f3, float f4, float f5, float f6, boolean z, long j2);

    @FastNative
    private static native void nDrawBitmap(long j, long j2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, long j3, int i, int i2);

    @FastNative
    private static native void nDrawBitmap(long j, long j2, float f, float f2, long j3, int i, int i2, int i3);

    @FastNative
    private static native void nDrawBitmap(long j, int[] iArr, int i, int i2, float f, float f2, int i3, int i4, boolean z, long j2);

    @FastNative
    private static native void nDrawBitmapMatrix(long j, long j2, long j3, long j4);

    @FastNative
    private static native void nDrawBitmapMesh(long j, long j2, int i, int i2, float[] fArr, int i3, int[] iArr, int i4, long j3);

    @FastNative
    private static native void nDrawCircle(long j, float f, float f2, float f3, long j2);

    @FastNative
    private static native void nDrawColor(long j, int i, int i2);

    @FastNative
    private static native void nDrawColor(long j, long j2, long j3, int i);

    @FastNative
    private static native void nDrawDoubleRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, long j2);

    @FastNative
    private static native void nDrawDoubleRoundRect(long j, float f, float f2, float f3, float f4, float[] fArr, float f5, float f6, float f7, float f8, float[] fArr2, long j2);

    @FastNative
    private static native void nDrawGlyphs(long j, int[] iArr, float[] fArr, int i, int i2, int i3, long j2, long j3);

    @FastNative
    private static native void nDrawLine(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawLines(long j, float[] fArr, int i, int i2, long j2);

    @FastNative
    private static native void nDrawMesh(long j, long j2, int i, long j3);

    @FastNative
    private static native void nDrawNinePatch(long j, long j2, long j3, float f, float f2, float f3, float f4, long j4, int i, int i2);

    @FastNative
    private static native void nDrawOval(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawPaint(long j, long j2);

    @FastNative
    private static native void nDrawPath(long j, long j2, long j3);

    @FastNative
    private static native void nDrawPoint(long j, float f, float f2, long j2);

    @FastNative
    private static native void nDrawPoints(long j, float[] fArr, int i, int i2, long j2);

    @FastNative
    private static native void nDrawRect(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawRegion(long j, long j2, long j3);

    @FastNative
    private static native void nDrawRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, long j2);

    @FastNative
    private static native void nDrawText(long j, String str, int i, int i2, float f, float f2, int i3, long j2);

    @FastNative
    private static native void nDrawText(long j, char[] cArr, int i, int i2, float f, float f2, int i3, long j2);

    @FastNative
    private static native void nDrawTextOnPath(long j, String str, long j2, float f, float f2, int i, long j3);

    @FastNative
    private static native void nDrawTextOnPath(long j, char[] cArr, int i, int i2, long j2, float f, float f2, int i3, long j3);

    @FastNative
    private static native void nDrawTextRun(long j, String str, int i, int i2, int i3, int i4, float f, float f2, boolean z, long j2);

    @FastNative
    private static native void nDrawTextRun(long j, char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, long j2, long j3);

    @FastNative
    private static native void nDrawVertices(long j, int i, int i2, float[] fArr, int i3, float[] fArr2, int i4, int[] iArr, int i5, short[] sArr, int i6, int i7, long j2);

    @FastNative
    private static native void nPunchHole(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    public BaseRecordingCanvas(long nativeCanvas) {
        super(nativeCanvas);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
        nDrawArc(this.mNativeCanvasWrapper, left, top, right, bottom, startAngle, sweepAngle, useCenter, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
        drawArc(oval.left, oval.top, oval.right, oval.bottom, startAngle, sweepAngle, useCenter, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawARGB(int a, int r, int g, int b) {
        drawColor(Color.argb(a, r, g, b));
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {
        throwIfCannotDraw(bitmap);
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), left, top, paint != null ? paint.getNativeInstance() : 0L, this.mDensity, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        nDrawBitmapMatrix(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), matrix.ni(), paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
        int left;
        int top;
        int right;
        int bottom;
        if (dst == null) {
            throw new NullPointerException();
        }
        throwIfCannotDraw(bitmap);
        long nativePaint = paint == null ? 0L : paint.getNativeInstance();
        if (src == null) {
            left = 0;
            top = 0;
            int right2 = bitmap.getWidth();
            right = right2;
            bottom = bitmap.getHeight();
        } else {
            left = src.left;
            int right3 = src.right;
            top = src.top;
            right = right3;
            bottom = src.bottom;
        }
        float f = dst.left;
        float f2 = dst.top;
        int left2 = dst.right;
        int top2 = dst.bottom;
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), left, top, right, bottom, f, f2, left2, top2, nativePaint, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        float left;
        float top;
        float right;
        float bottom;
        if (dst == null) {
            throw new NullPointerException();
        }
        throwIfCannotDraw(bitmap);
        long nativePaint = paint == null ? 0L : paint.getNativeInstance();
        if (src == null) {
            left = 0.0f;
            top = 0.0f;
            float right2 = bitmap.getWidth();
            right = right2;
            bottom = bitmap.getHeight();
        } else {
            left = src.left;
            float right3 = src.right;
            top = src.top;
            right = right3;
            bottom = src.bottom;
        }
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), left, top, right, bottom, dst.left, dst.top, dst.right, dst.bottom, nativePaint, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawBitmap(int[] colors, int offset, int stride, float x, float y, int width, int height, boolean hasAlpha, Paint paint) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be >= 0");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must be >= 0");
        }
        if (Math.abs(stride) < width) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int lastScanline = offset + ((height - 1) * stride);
        int length = colors.length;
        if (offset < 0 || offset + width > length || lastScanline < 0 || lastScanline + width > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (width == 0 || height == 0) {
            return;
        }
        nDrawBitmap(this.mNativeCanvasWrapper, colors, offset, stride, x, y, width, height, hasAlpha, paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawBitmap(int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, Paint paint) {
        drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmapMesh(Bitmap bitmap, int meshWidth, int meshHeight, float[] verts, int vertOffset, int[] colors, int colorOffset, Paint paint) {
        if ((meshWidth | meshHeight | vertOffset | colorOffset) < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (meshWidth == 0 || meshHeight == 0) {
            return;
        }
        int count = (meshWidth + 1) * (meshHeight + 1);
        checkRange(verts.length, vertOffset, count * 2);
        if (colors != null) {
            checkRange(colors.length, colorOffset, count);
        }
        nDrawBitmapMesh(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawCircle(float cx, float cy, float radius, Paint paint) {
        nDrawCircle(this.mNativeCanvasWrapper, cx, cy, radius, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int color) {
        nDrawColor(this.mNativeCanvasWrapper, color, BlendMode.SRC_OVER.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int color, PorterDuff.Mode mode) {
        nDrawColor(this.mNativeCanvasWrapper, color, mode.nativeInt);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int color, BlendMode mode) {
        nDrawColor(this.mNativeCanvasWrapper, color, mode.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(long color, BlendMode mode) {
        ColorSpace cs = Color.colorSpace(color);
        nDrawColor(this.mNativeCanvasWrapper, cs.getNativeInstance(), color, mode.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        nDrawLine(this.mNativeCanvasWrapper, startX, startY, stopX, stopY, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLines(float[] pts, int offset, int count, Paint paint) {
        nDrawLines(this.mNativeCanvasWrapper, pts, offset, count, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLines(float[] pts, Paint paint) {
        drawLines(pts, 0, pts.length, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawOval(float left, float top, float right, float bottom, Paint paint) {
        nDrawOval(this.mNativeCanvasWrapper, left, top, right, bottom, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawOval(RectF oval, Paint paint) {
        if (oval == null) {
            throw new NullPointerException();
        }
        drawOval(oval.left, oval.top, oval.right, oval.bottom, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPaint(Paint paint) {
        nDrawPaint(this.mNativeCanvasWrapper, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPatch(NinePatch patch, Rect dst, Paint paint) {
        Bitmap bitmap = patch.getBitmap();
        throwIfCannotDraw(bitmap);
        long nativePaint = paint == null ? 0L : paint.getNativeInstance();
        nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), patch.mNativeChunk, dst.left, dst.top, dst.right, dst.bottom, nativePaint, this.mDensity, patch.getDensity());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPatch(NinePatch patch, RectF dst, Paint paint) {
        Bitmap bitmap = patch.getBitmap();
        throwIfCannotDraw(bitmap);
        long nativePaint = paint == null ? 0L : paint.getNativeInstance();
        nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), patch.mNativeChunk, dst.left, dst.top, dst.right, dst.bottom, nativePaint, this.mDensity, patch.getDensity());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPath(Path path, Paint paint) {
        nDrawPath(this.mNativeCanvasWrapper, path.readOnlyNI(), paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawRegion(Region region, Paint paint) {
        nDrawRegion(this.mNativeCanvasWrapper, region.mNativeRegion, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture) {
        picture.endRecording();
        int restoreCount = save();
        picture.draw(this);
        restoreToCount(restoreCount);
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture, Rect dst) {
        save();
        translate(dst.left, dst.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(dst.width() / picture.getWidth(), dst.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture, RectF dst) {
        save();
        translate(dst.left, dst.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(dst.width() / picture.getWidth(), dst.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoint(float x, float y, Paint paint) {
        nDrawPoint(this.mNativeCanvasWrapper, x, y, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoints(float[] pts, int offset, int count, Paint paint) {
        nDrawPoints(this.mNativeCanvasWrapper, pts, offset, count, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoints(float[] pts, Paint paint) {
        drawPoints(pts, 0, pts.length, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawPosText(char[] text, int index, int count, float[] pos, Paint paint) {
        if (index < 0 || index + count > text.length || count * 2 > pos.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < count; i++) {
            drawText(text, index + i, 1, pos[i * 2], pos[(i * 2) + 1], paint);
        }
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawPosText(String text, float[] pos, Paint paint) {
        drawPosText(text.toCharArray(), 0, text.length(), pos, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(float left, float top, float right, float bottom, Paint paint) {
        nDrawRect(this.mNativeCanvasWrapper, left, top, right, bottom, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(Rect r, Paint paint) {
        drawRect(r.left, r.top, r.right, r.bottom, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(RectF rect, Paint paint) {
        nDrawRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRGB(int r, int g, int b) {
        drawColor(Color.rgb(r, g, b));
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) {
        nDrawRoundRect(this.mNativeCanvasWrapper, left, top, right, bottom, rx, ry, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {
        drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, rx, ry, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawDoubleRoundRect(RectF outer, float outerRx, float outerRy, RectF inner, float innerRx, float innerRy, Paint paint) {
        nDrawDoubleRoundRect(this.mNativeCanvasWrapper, outer.left, outer.top, outer.right, outer.bottom, outerRx, outerRy, inner.left, inner.top, inner.right, inner.bottom, innerRx, innerRy, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawDoubleRoundRect(RectF outer, float[] outerRadii, RectF inner, float[] innerRadii, Paint paint) {
        nDrawDoubleRoundRect(this.mNativeCanvasWrapper, outer.left, outer.top, outer.right, outer.bottom, outerRadii, inner.left, inner.top, inner.right, inner.bottom, innerRadii, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawGlyphs(int[] glyphIds, int glyphIdOffset, float[] positions, int positionOffset, int glyphCount, Font font, Paint paint) {
        Objects.requireNonNull(glyphIds, "glyphIds must not be null.");
        Objects.requireNonNull(positions, "positions must not be null.");
        Objects.requireNonNull(font, "font must not be null.");
        Objects.requireNonNull(paint, "paint must not be null.");
        Preconditions.checkArgumentNonnegative(glyphCount);
        if (glyphIdOffset < 0 || glyphIdOffset + glyphCount > glyphIds.length) {
            throw new IndexOutOfBoundsException("glyphIds must have at least " + (glyphIdOffset + glyphCount) + " of elements");
        }
        if (positionOffset < 0 || positionOffset + (glyphCount * 2) > positions.length) {
            throw new IndexOutOfBoundsException("positions must have at least " + (positionOffset + (glyphCount * 2)) + " of elements");
        }
        nDrawGlyphs(this.mNativeCanvasWrapper, glyphIds, positions, glyphIdOffset, positionOffset, glyphCount, font.getNativePtr(), paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(char[] text, int index, int count, float x, float y, Paint paint) {
        if ((index | count | (index + count) | ((text.length - index) - count)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, text, index, count, x, y, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        if ((text instanceof String) || (text instanceof SpannedString) || (text instanceof SpannableString)) {
            nDrawText(this.mNativeCanvasWrapper, text.toString(), start, end, x, y, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        if (text instanceof GraphicsOperations) {
            ((GraphicsOperations) text).drawText(this, start, end, x, y, paint);
            return;
        }
        char[] buf = TemporaryBuffer.obtain(end - start);
        TextUtils.getChars(text, start, end, buf, 0);
        nDrawText(this.mNativeCanvasWrapper, buf, 0, end - start, x, y, paint.mBidiFlags, paint.getNativeInstance());
        TemporaryBuffer.recycle(buf);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(String text, float x, float y, Paint paint) {
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, text, 0, text.length(), x, y, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(String text, int start, int end, float x, float y, Paint paint) {
        if ((start | end | (end - start) | (text.length() - end)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, text, start, end, x, y, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextOnPath(char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint) {
        if (index >= 0 && index + count <= text.length) {
            paint.set(paint);
            nDrawTextOnPath(this.mNativeCanvasWrapper, text, index, count, path.readOnlyNI(), hOffset, vOffset, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) {
        if (text.length() > 0) {
            paint.set(paint);
            nDrawTextOnPath(this.mNativeCanvasWrapper, text, path.readOnlyNI(), hOffset, vOffset, paint.mBidiFlags, paint.getNativeInstance());
        }
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextRun(char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, Paint paint) {
        if (text == null) {
            throw new NullPointerException("text is null");
        }
        if (paint == null) {
            throw new NullPointerException("paint is null");
        }
        paint.set(paint);
        if ((index | count | contextIndex | contextCount | (index - contextIndex) | ((contextIndex + contextCount) - (index + count)) | (text.length - (contextIndex + contextCount))) < 0) {
            throw new IndexOutOfBoundsException();
        }
        nDrawTextRun(this.mNativeCanvasWrapper, text, index, count, contextIndex, contextCount, x, y, isRtl, paint.getNativeInstance(), 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint) {
        if (text == null) {
            throw new NullPointerException("text is null");
        }
        if (paint == null) {
            throw new NullPointerException("paint is null");
        }
        if ((start | end | contextStart | contextEnd | (start - contextStart) | (end - start) | (contextEnd - end) | (text.length() - contextEnd)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        if ((text instanceof String) || (text instanceof SpannedString) || (text instanceof SpannableString)) {
            nDrawTextRun(this.mNativeCanvasWrapper, text.toString(), start, end, contextStart, contextEnd, x, y, isRtl, paint.getNativeInstance());
            return;
        }
        if (text instanceof GraphicsOperations) {
            ((GraphicsOperations) text).drawTextRun(this, start, end, contextStart, contextEnd, x, y, isRtl, paint);
            return;
        }
        if (text instanceof PrecomputedText) {
            PrecomputedText pt = (PrecomputedText) text;
            int paraIndex = pt.findParaIndex(start);
            if (end <= pt.getParagraphEnd(paraIndex)) {
                int paraStart = pt.getParagraphStart(paraIndex);
                MeasuredParagraph mp = pt.getMeasuredParagraph(paraIndex);
                drawTextRun(mp.getMeasuredText(), start - paraStart, end - paraStart, contextStart - paraStart, contextEnd - paraStart, x, y, isRtl, paint);
                return;
            }
        }
        int contextLen = contextEnd - contextStart;
        int len = end - start;
        char[] buf = TemporaryBuffer.obtain(contextLen);
        TextUtils.getChars(text, contextStart, contextEnd, buf, 0);
        nDrawTextRun(this.mNativeCanvasWrapper, buf, start - contextStart, len, 0, contextLen, x, y, isRtl, paint.getNativeInstance(), 0L);
        TemporaryBuffer.recycle(buf);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawTextRun(MeasuredText measuredText, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint) {
        nDrawTextRun(this.mNativeCanvasWrapper, measuredText.getChars(), start, end - start, contextStart, contextEnd - contextStart, x, y, isRtl, paint.getNativeInstance(), measuredText.getNativePtr());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawVertices(Canvas.VertexMode mode, int vertexCount, float[] verts, int vertOffset, float[] texs, int texOffset, int[] colors, int colorOffset, short[] indices, int indexOffset, int indexCount, Paint paint) {
        checkRange(verts.length, vertOffset, vertexCount);
        if (texs != null) {
            checkRange(texs.length, texOffset, vertexCount);
        }
        if (colors != null) {
            checkRange(colors.length, colorOffset, vertexCount / 2);
        }
        if (indices != null) {
            checkRange(indices.length, indexOffset, indexCount);
        }
        nDrawVertices(this.mNativeCanvasWrapper, mode.nativeInt, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint.getNativeInstance());
    }

    @Override // android.graphics.BaseCanvas
    public final void drawMesh(Mesh mesh, BlendMode blendMode, Paint paint) {
        if (blendMode == null) {
            blendMode = BlendMode.MODULATE;
        }
        nDrawMesh(this.mNativeCanvasWrapper, mesh.getNativeWrapperInstance(), blendMode.getXfermode().porterDuffMode, paint.getNativeInstance());
    }

    @Override // android.graphics.BaseCanvas
    public void punchHole(float left, float top, float right, float bottom, float rx, float ry, float alpha) {
        nPunchHole(this.mNativeCanvasWrapper, left, top, right, bottom, rx, ry, alpha);
    }
}

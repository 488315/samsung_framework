package com.android.systemui.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.graphics.text.PositionedGlyphs;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextShaper;
import android.util.MathUtils;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.ScreenDecorations$CoverRestartingPreDrawListener$$ExternalSyntheticOutline0;
import com.android.systemui.animation.FontInterpolator;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class TextInterpolator {
    public final TextPaint basePaint;
    public final FontInterpolator fontInterpolator;
    public Function2 glyphFilter;
    public Layout layout;
    public List lines;
    public float progress;
    public final TextPaint targetPaint;
    public final Lazy tmpGlyph$delegate;
    public final TextPaint tmpPaint;
    public final Lazy tmpPaintForGlyph$delegate;
    public float[] tmpPositionArray;
    public final TypefaceVariantCache typefaceCache;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class FontRun {
        public Font baseFont;
        public final int end;
        public final int start;
        public Font targetFont;

        public FontRun(int i, int i2, Font font, Font font2) {
            this.start = i;
            this.end = i2;
            this.baseFont = font;
            this.targetFont = font2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FontRun)) {
                return false;
            }
            FontRun fontRun = (FontRun) obj;
            if (this.start == fontRun.start && this.end == fontRun.end && Intrinsics.areEqual(this.baseFont, fontRun.baseFont) && Intrinsics.areEqual(this.targetFont, fontRun.targetFont)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.targetFont.hashCode() + ((this.baseFont.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m(this.end, Integer.hashCode(this.start) * 31, 31)) * 31);
        }

        public final String toString() {
            return "FontRun(start=" + this.start + ", end=" + this.end + ", baseFont=" + this.baseFont + ", targetFont=" + this.targetFont + ")";
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Line {
        public final List runs;

        public Line(List<Run> list) {
            this.runs = list;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MutablePositionedGlyph extends TextAnimator.PositionedGlyph {
        public int glyphIndex;

        public MutablePositionedGlyph() {
            super(null);
        }

        @Override // com.android.systemui.animation.TextAnimator.PositionedGlyph
        public final int getGlyphIndex() {
            return this.glyphIndex;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Run {
        public final float[] baseX;
        public final float[] baseY;
        public final List fontRuns;
        public final int[] glyphIds;
        public final float[] targetX;
        public final float[] targetY;

        public Run(int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, List<FontRun> list) {
            this.glyphIds = iArr;
            this.baseX = fArr;
            this.baseY = fArr2;
            this.targetX = fArr3;
            this.targetY = fArr4;
            this.fontRuns = list;
        }
    }

    public TextInterpolator(Layout layout, TypefaceVariantCache typefaceVariantCache, Integer num) {
        this.typefaceCache = typefaceVariantCache;
        this.basePaint = new TextPaint(layout.getPaint());
        this.targetPaint = new TextPaint(layout.getPaint());
        this.lines = EmptyList.INSTANCE;
        this.fontInterpolator = new FontInterpolator(num);
        this.tmpPaint = new TextPaint();
        this.tmpPaintForGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.TextInterpolator$tmpPaintForGlyph$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new TextPaint();
            }
        });
        this.tmpGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.TextInterpolator$tmpGlyph$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new TextInterpolator.MutablePositionedGlyph();
            }
        });
        this.tmpPositionArray = new float[20];
        this.layout = layout;
        shapeText(layout);
    }

    public static void lerp(Paint paint, Paint paint2, float f, Paint paint3) {
        paint3.set(paint);
        paint3.setTextSize(MathUtils.lerp(paint.getTextSize(), paint2.getTextSize(), f));
        paint3.setColor(ColorUtils.blendARGB(paint.getColor(), paint2.getColor(), f));
        paint3.setStrokeWidth(MathUtils.lerp(paint.getStrokeWidth(), paint2.getStrokeWidth(), f));
    }

    public final void drawFontRun(Canvas canvas, Run run, FontRun fontRun, int i, Paint paint) {
        boolean z;
        int i2;
        int i3;
        float[] fArr;
        int i4;
        Font lerp = this.fontInterpolator.lerp(fontRun.baseFont, fontRun.targetFont, this.progress);
        Function2 function2 = this.glyphFilter;
        float[] fArr2 = run.targetY;
        float[] fArr3 = run.baseY;
        float[] fArr4 = run.targetX;
        float[] fArr5 = run.baseX;
        int i5 = 0;
        int i6 = fontRun.start;
        int i7 = fontRun.end;
        if (function2 == null) {
            while (i6 < i7) {
                int i8 = i5 + 1;
                this.tmpPositionArray[i5] = MathUtils.lerp(fArr5[i6], fArr4[i6], this.progress);
                i5 = i8 + 1;
                this.tmpPositionArray[i8] = MathUtils.lerp(fArr3[i6], fArr2[i6], this.progress);
                i6++;
            }
            int[] iArr = run.glyphIds;
            int i9 = fontRun.start;
            canvas.drawGlyphs(iArr, i9, this.tmpPositionArray, 0, i7 - i9, lerp, paint);
            return;
        }
        getTmpGlyph().getClass();
        getTmpGlyph().getClass();
        getTmpGlyph().getClass();
        getTmpGlyph().lineNo = i;
        Lazy lazy = this.tmpPaintForGlyph$delegate;
        ((TextPaint) lazy.getValue()).set(paint);
        int i10 = i6;
        int i11 = i10;
        int i12 = 0;
        while (i11 < i7) {
            getTmpGlyph().glyphIndex = i11;
            MutablePositionedGlyph tmpGlyph = getTmpGlyph();
            int i13 = run.glyphIds[i11];
            tmpGlyph.getClass();
            int i14 = i12;
            getTmpGlyph().x = MathUtils.lerp(fArr5[i11], fArr4[i11], this.progress);
            getTmpGlyph().y = MathUtils.lerp(fArr3[i11], fArr2[i11], this.progress);
            getTmpGlyph().textSize = paint.getTextSize();
            getTmpGlyph().color = paint.getColor();
            function2.invoke(getTmpGlyph(), Float.valueOf(this.progress));
            if (getTmpGlyph().textSize == paint.getTextSize()) {
                z = true;
            } else {
                z = false;
            }
            if (z && getTmpGlyph().color == paint.getColor()) {
                i4 = i14;
                i2 = i11;
                i3 = i7;
                fArr = fArr5;
            } else {
                ((TextPaint) lazy.getValue()).setTextSize(getTmpGlyph().textSize);
                ((TextPaint) lazy.getValue()).setColor(getTmpGlyph().color);
                i2 = i11;
                i3 = i7;
                fArr = fArr5;
                canvas.drawGlyphs(run.glyphIds, i10, this.tmpPositionArray, 0, i11 - i10, lerp, (TextPaint) lazy.getValue());
                i4 = 0;
                i10 = i2;
            }
            int i15 = i4 + 1;
            this.tmpPositionArray[i4] = getTmpGlyph().x;
            this.tmpPositionArray[i15] = getTmpGlyph().y;
            i11 = i2 + 1;
            i12 = i15 + 1;
            i7 = i3;
            fArr5 = fArr;
        }
        canvas.drawGlyphs(run.glyphIds, i10, this.tmpPositionArray, 0, i7 - i10, lerp, (TextPaint) lazy.getValue());
    }

    public final MutablePositionedGlyph getTmpGlyph() {
        return (MutablePositionedGlyph) this.tmpGlyph$delegate.getValue();
    }

    public final void rebase() {
        boolean z;
        float f = this.progress;
        boolean z2 = true;
        if (f == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        if (f != 1.0f) {
            z2 = false;
        }
        TextPaint textPaint = this.targetPaint;
        TextPaint textPaint2 = this.basePaint;
        if (z2) {
            textPaint2.set(textPaint);
        } else {
            TextPaint textPaint3 = this.tmpPaint;
            lerp(textPaint2, textPaint, f, textPaint3);
            textPaint2.set(textPaint3);
        }
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            for (Run run : ((Line) it.next()).runs) {
                int length = run.baseX.length;
                for (int i = 0; i < length; i++) {
                    float[] fArr = run.baseX;
                    fArr[i] = MathUtils.lerp(fArr[i], run.targetX[i], this.progress);
                    float[] fArr2 = run.baseY;
                    fArr2[i] = MathUtils.lerp(fArr2[i], run.targetY[i], this.progress);
                }
                for (FontRun fontRun : run.fontRuns) {
                    Font lerp = this.fontInterpolator.lerp(fontRun.baseFont, fontRun.targetFont, this.progress);
                    fontRun.baseFont = lerp;
                    textPaint2.setTypeface(((TypefaceVariantCacheImpl) this.typefaceCache).getTypefaceForVariant(FontVariationAxis.toFontVariationSettings(lerp.getAxes())));
                }
            }
        }
        this.progress = 0.0f;
    }

    public final void shapeText(Layout layout) {
        Iterator it;
        Iterator it2;
        ArrayList arrayList;
        Iterator it3;
        Iterator it4;
        ArrayList arrayList2;
        float[] fArr;
        PositionedGlyphs positionedGlyphs;
        List shapeText = shapeText(layout, this.basePaint);
        List shapeText2 = shapeText(layout, this.targetPaint);
        ArrayList arrayList3 = (ArrayList) shapeText;
        ArrayList arrayList4 = (ArrayList) shapeText2;
        if (arrayList3.size() == arrayList4.size()) {
            Iterator it5 = arrayList3.iterator();
            Iterator it6 = arrayList4.iterator();
            int i = 10;
            ArrayList arrayList5 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(shapeText, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(shapeText2, 10)));
            int i2 = 0;
            TextInterpolator textInterpolator = this;
            while (it5.hasNext() && it6.hasNext()) {
                Object next = it5.next();
                List list = (List) it6.next();
                List list2 = (List) next;
                Iterator it7 = list2.iterator();
                Iterator it8 = list.iterator();
                ArrayList arrayList6 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, i), CollectionsKt__IterablesKt.collectionSizeOrDefault(list, i)));
                while (it7.hasNext() && it8.hasNext()) {
                    Object next2 = it7.next();
                    PositionedGlyphs positionedGlyphs2 = (PositionedGlyphs) it8.next();
                    PositionedGlyphs positionedGlyphs3 = (PositionedGlyphs) next2;
                    if (positionedGlyphs3.glyphCount() == positionedGlyphs2.glyphCount()) {
                        int glyphCount = positionedGlyphs3.glyphCount();
                        int[] iArr = new int[glyphCount];
                        for (int i3 = 0; i3 < glyphCount; i3++) {
                            int glyphId = positionedGlyphs3.getGlyphId(i3);
                            if (glyphId == positionedGlyphs2.getGlyphId(i3)) {
                                Unit unit = Unit.INSTANCE;
                                iArr[i3] = glyphId;
                            } else {
                                throw new IllegalArgumentException(ScreenDecorations$CoverRestartingPreDrawListener$$ExternalSyntheticOutline0.m("Inconsistent glyph ID at ", i3, " in line ", textInterpolator.lines.size()).toString());
                            }
                        }
                        float[] fArr2 = new float[glyphCount];
                        for (int i4 = 0; i4 < glyphCount; i4++) {
                            fArr2[i4] = positionedGlyphs3.getGlyphX(i4);
                        }
                        float[] fArr3 = new float[glyphCount];
                        for (int i5 = 0; i5 < glyphCount; i5++) {
                            fArr3[i5] = positionedGlyphs3.getGlyphY(i5);
                        }
                        float[] fArr4 = new float[glyphCount];
                        for (int i6 = 0; i6 < glyphCount; i6++) {
                            fArr4[i6] = positionedGlyphs2.getGlyphX(i6);
                        }
                        float[] fArr5 = new float[glyphCount];
                        for (int i7 = 0; i7 < glyphCount; i7++) {
                            fArr5[i7] = positionedGlyphs2.getGlyphY(i7);
                        }
                        ArrayList arrayList7 = new ArrayList();
                        if (glyphCount != 0) {
                            int i8 = i2;
                            it = it5;
                            Font font = positionedGlyphs3.getFont(0);
                            Font font2 = positionedGlyphs2.getFont(0);
                            FontInterpolator.Companion.getClass();
                            it2 = it6;
                            it3 = it7;
                            if (FontInterpolator.Companion.canInterpolate(font, font2)) {
                                arrayList = arrayList5;
                                it4 = it8;
                                int i9 = 1;
                                Font font3 = font;
                                int i10 = i8;
                                fArr = fArr5;
                                int i11 = 0;
                                while (i9 < glyphCount) {
                                    ArrayList arrayList8 = arrayList6;
                                    Font font4 = positionedGlyphs3.getFont(i9);
                                    PositionedGlyphs positionedGlyphs4 = positionedGlyphs3;
                                    Font font5 = positionedGlyphs2.getFont(i9);
                                    if (font3 == font4) {
                                        positionedGlyphs = positionedGlyphs2;
                                        if (!(font2 == font5)) {
                                            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m("Base font is unchanged at ", i9, " but target font has changed.").toString());
                                        }
                                    } else if (font2 != font5) {
                                        positionedGlyphs = positionedGlyphs2;
                                        arrayList7.add(new FontRun(i11, i9, font3, font2));
                                        int max = Math.max(i10, i9 - i11);
                                        FontInterpolator.Companion.getClass();
                                        if (!FontInterpolator.Companion.canInterpolate(font4, font5)) {
                                            throw new IllegalArgumentException(("Cannot interpolate font at " + i9 + " (" + font4 + " vs " + font5 + ")").toString());
                                        }
                                        i10 = max;
                                        i11 = i9;
                                        font2 = font5;
                                        font3 = font4;
                                    } else {
                                        throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m("Base font has changed at ", i9, " but target font is unchanged.").toString());
                                    }
                                    i9++;
                                    positionedGlyphs3 = positionedGlyphs4;
                                    positionedGlyphs2 = positionedGlyphs;
                                    arrayList6 = arrayList8;
                                }
                                arrayList2 = arrayList6;
                                arrayList7.add(new FontRun(i11, glyphCount, font3, font2));
                                i2 = Math.max(i10, glyphCount - i11);
                            } else {
                                throw new IllegalArgumentException(("Cannot interpolate font at 0 (" + font + " vs " + font2 + ")").toString());
                            }
                        } else {
                            it = it5;
                            it2 = it6;
                            arrayList = arrayList5;
                            it3 = it7;
                            it4 = it8;
                            arrayList2 = arrayList6;
                            fArr = fArr5;
                        }
                        Run run = new Run(iArr, fArr2, fArr3, fArr4, fArr, arrayList7);
                        ArrayList arrayList9 = arrayList2;
                        arrayList9.add(run);
                        textInterpolator = this;
                        arrayList6 = arrayList9;
                        it5 = it;
                        it6 = it2;
                        it7 = it3;
                        it8 = it4;
                        arrayList5 = arrayList;
                    } else {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Inconsistent glyph count at line ", textInterpolator.lines.size()).toString());
                    }
                }
                ArrayList arrayList10 = arrayList5;
                arrayList10.add(new Line(arrayList6));
                i = 10;
                i2 = i2;
                arrayList5 = arrayList10;
                it5 = it5;
                it6 = it6;
            }
            textInterpolator.lines = arrayList5;
            int i12 = i2 * 2;
            if (textInterpolator.tmpPositionArray.length < i12) {
                textInterpolator.tmpPositionArray = new float[i12];
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The new layout result has different line count.".toString());
    }

    public /* synthetic */ TextInterpolator(Layout layout, TypefaceVariantCache typefaceVariantCache, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(layout, typefaceVariantCache, (i & 4) != 0 ? null : num);
    }

    public static List shapeText(Layout layout, TextPaint textPaint) {
        ArrayList arrayList = new ArrayList();
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            int i2 = lineEnd - lineStart;
            int i3 = (lineStart + i2) - 1;
            if (i3 > lineStart && i3 < layout.getText().length() && layout.getText().charAt(i3) == '\n') {
                i2--;
            }
            final ArrayList arrayList2 = new ArrayList();
            TextShaper.shapeText(layout.getText(), lineStart, i2, layout.getTextDirectionHeuristic(), textPaint, new TextShaper.GlyphsConsumer() { // from class: com.android.systemui.animation.TextInterpolator$shapeText$3
                @Override // android.text.TextShaper.GlyphsConsumer
                public final void accept(int i4, int i5, PositionedGlyphs positionedGlyphs, TextPaint textPaint2) {
                    arrayList2.add(positionedGlyphs);
                }
            });
            arrayList.add(arrayList2);
            layout.getText().subSequence(lineStart, lineEnd).toString();
        }
        return arrayList;
    }
}

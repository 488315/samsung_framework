package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.scontext.SContextConstants;
import android.text.Spanned;
import android.util.Log;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class ColorPaletteExtractor extends ColorExtractor {
    public static final float DEFAULT_GRAYSCALE_THRESHOLD_BRIGHTNESS = 0.18f;
    public static final float DEFAULT_GRAYSCALE_THRESHOLD_SATURATION = 0.12f;
    static final float LAB_BRIGHTNESS_SCALE = 10.0f;
    static final String TAG = "ColorPaletteExtractor";
    static float sSaturationThresholdForGrayscale = 0.12f;
    static float sBrightnessThresholdForGrayscale = 0.18f;
    static float sHsvSpaceHueRadiusValue = 1.0f;
    static float sLabSpaceLightnessScale = 50.0f;
    static float sMaxRGB = (float) Math.sqrt(Math.pow(255.0d, 2.0d) * 3.0d);
    static float sMaxHSV = sHsvSpaceHueRadiusValue * 2.0f;
    static float sMaxLab = (int) Math.sqrt((Math.pow(sLabSpaceLightnessScale * 100.0f, 2.0d) + Math.pow(255.0d, 2.0d)) + Math.pow(255.0d, 2.0d));

    /* loaded from: classes5.dex */
    public enum ColorMergeType {
        MIX,
        A,
        B
    }

    /* loaded from: classes5.dex */
    public static final class ColorSpace extends Enum<ColorSpace> {
        static ColorSpace[] ColorSpaceIndex = {new ColorSpace(), new ColorSpace(), new ColorSpace()};
        public static final ColorSpace HSV;
        public static final ColorSpace LAB;
        public static final ColorSpace RGB;
        public static final ColorSpace HUE = new ColorSpace();
        private static final /* synthetic */ ColorSpace[] $VALUES = $values();

        private static /* synthetic */ ColorSpace[] $values() {
            return new ColorSpace[]{RGB, HSV, LAB, HUE};
        }

        private ColorSpace(String str, int i) {
            super(str, i);
        }

        public static ColorSpace valueOf(String name) {
            return (ColorSpace) Enum.valueOf(ColorSpace.class, name);
        }

        public static ColorSpace[] values() {
            return (ColorSpace[]) $VALUES.clone();
        }

        static {
            ColorSpace colorSpace = new ColorSpace();
            RGB = colorSpace;
            ColorSpace colorSpace2 = new ColorSpace();
            HSV = colorSpace2;
            ColorSpace colorSpace3 = new ColorSpace();
            LAB = colorSpace3;
            HUE = new ColorSpace();
            $VALUES = $values();
            ColorSpaceIndex = new ColorSpace[]{colorSpace, colorSpace2, colorSpace3};
        }
    }

    public static void setSaturationThresholdForGrayscale(float saturationThreshold) {
        sSaturationThresholdForGrayscale = saturationThreshold;
    }

    public static void setBrightnessThresholdForGrayscale(float brightnessThreshold) {
        sBrightnessThresholdForGrayscale = brightnessThreshold;
    }

    public static float getSaturationThresholdForGrayscale() {
        return sSaturationThresholdForGrayscale;
    }

    public static float getBrightnessThresholdForGrayscale() {
        return sBrightnessThresholdForGrayscale;
    }

    public static void setHsvSpaceHueRadiusValue(float hsvSpaceHueRadiusValue) {
        sHsvSpaceHueRadiusValue = hsvSpaceHueRadiusValue;
        sMaxHSV = 2.0f * hsvSpaceHueRadiusValue;
    }

    public static float getHsvSpaceHueRadiusValue() {
        return sHsvSpaceHueRadiusValue;
    }

    public static void setLabSpaceLightnessValue(float labSpaceLightnessScale) {
        sLabSpaceLightnessScale = labSpaceLightnessScale;
    }

    public static float getHsvSpaceClusteringRadiusValue() {
        return sLabSpaceLightnessScale;
    }

    public static int[] makeClusterGroupColorBandBasedFromHueInterval(float hueInterval) {
        return makeClusterGroupColorBandBasedFromHueInterval(hueInterval, new float[]{0.5f, 0.2f});
    }

    public static int[] makeClusterGroupColorBandBasedFromHueInterval(float hueInterval, float[] svValues) {
        ArrayList<Integer> clusterGroupArrayList = new ArrayList<>();
        clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.0f})));
        clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.3333f})));
        clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.6666f})));
        clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 1.0f})));
        float hue = 0.0f;
        while (hue < 360.0f) {
            clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{hue, 1.0f, 1.0f})));
            hue += hueInterval;
        }
        for (int i = 0; i < svValues.length; i++) {
            float hue2 = 0.0f;
            while (hue2 < 360.0f) {
                clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{hue2, svValues[i], 1.0f})));
                hue2 += hueInterval;
            }
            float hue3 = 0.0f;
            while (hue3 < 360.0f) {
                clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{hue3, 1.0f, svValues[i]})));
                hue3 += hueInterval;
            }
            float hue4 = 0.0f;
            while (hue4 < 360.0f) {
                clusterGroupArrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{hue4, svValues[i], svValues[i]})));
                hue4 += hueInterval;
            }
        }
        int[] clusterGroupArray = new int[clusterGroupArrayList.size()];
        int i2 = 0;
        Iterator<Integer> it = clusterGroupArrayList.iterator();
        while (it.hasNext()) {
            int indexList = it.next().intValue();
            clusterGroupArray[i2] = indexList;
            i2++;
        }
        return clusterGroupArray;
    }

    /* loaded from: classes5.dex */
    public static class ColorResultData {
        double dist;
        int index;
        int indexTarget;

        ColorResultData(int index, int indexTarget, double dist) {
            this.index = index;
            this.indexTarget = indexTarget;
            this.dist = dist;
        }
    }

    public static int discardSameColorFromDominantColorsForColorPalette(ColorExtractor.DominantColorResult[] dominantColorArray, double normalizedMergeMinDistance, ColorSpace colorSpace, boolean isBrightnessOrientedMerge) {
        int mergedColorNum;
        double[] labA;
        ColorResultData[] colorResultDataArray;
        int loopCount;
        float luminanceB;
        float hueA;
        int size;
        double squaredDistance;
        double[] labB;
        int i;
        ColorResultData[] colorResultDataArray2;
        double[] labB2;
        float hueA2;
        double[] labA2;
        double minDistance;
        int a;
        int size2;
        int minIndex;
        float hueA3;
        int a2;
        int minIndex2;
        float hueA4;
        double[] labA3;
        double d;
        ColorExtractor.DominantColorResult[] dominantColorResultArr = dominantColorArray;
        ColorSpace colorSpace2 = colorSpace;
        float[] hsvA = new float[3];
        float[] hsvB = new float[3];
        double[] labA4 = new double[3];
        double[] labB3 = new double[3];
        int loopCount2 = 0;
        float luminanceA = 0.0f;
        float luminanceB2 = 0.0f;
        float hueA5 = 0.0f;
        int size3 = dominantColorResultArr.length;
        double squaredDistance2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double distanceThreshold = normalizedMergeMinDistance;
        if (colorSpace2 == ColorSpace.HUE) {
            distanceThreshold = normalizedMergeMinDistance * 360.0d;
            mergedColorNum = 0;
        } else if (colorSpace2 == ColorSpace.RGB) {
            distanceThreshold = normalizedMergeMinDistance * sMaxRGB;
            mergedColorNum = 0;
            loopCount2 = 0;
        } else if (colorSpace2 == ColorSpace.HSV) {
            distanceThreshold = normalizedMergeMinDistance * sMaxHSV;
            mergedColorNum = 0;
            loopCount2 = 0;
        } else if (colorSpace2 != ColorSpace.LAB) {
            mergedColorNum = 0;
            loopCount2 = 0;
        } else {
            distanceThreshold = normalizedMergeMinDistance * sMaxLab;
            mergedColorNum = 0;
            loopCount2 = 0;
        }
        while (true) {
            int mergedColorNum2 = 0;
            int mergedColorNum3 = mergedColorNum;
            ColorResultData[] colorResultDataArray3 = new ColorResultData[size3];
            float luminanceA2 = luminanceA;
            int colorB = 0;
            while (true) {
                if (colorB < size3) {
                    luminanceB = luminanceB2;
                    ColorExtractor.DominantColorResult dominantColorResultA = dominantColorResultArr[colorB];
                    hueA = hueA5;
                    float hueA6 = dominantColorResultA.percentage;
                    if (hueA6 == 0.0f) {
                        labA = labA4;
                        colorResultDataArray = colorResultDataArray3;
                        loopCount = loopCount2;
                        size = size3;
                        squaredDistance = squaredDistance2;
                    } else {
                        int colorA = dominantColorResultA.color;
                        double squaredDistance3 = squaredDistance2;
                        if (colorSpace2 == ColorSpace.HSV || colorSpace2 == ColorSpace.HUE) {
                            dominantColorResultA.copyHSV(hsvA);
                            float luminanceA3 = IUXColorUtils.calculateLuminance(colorA);
                            hueA2 = hsvA[0];
                            luminanceA2 = luminanceA3;
                        } else {
                            if (colorSpace2 == ColorSpace.LAB) {
                                ColorUtils.colorToLAB(colorA, labA4);
                            }
                            hueA2 = hueA;
                        }
                        int minIndex3 = -1;
                        ColorResultData[] colorResultDataArray4 = colorResultDataArray3;
                        int loopCount3 = loopCount2;
                        int colorA2 = colorB + 1;
                        double minDistance2 = Double.MAX_VALUE;
                        while (true) {
                            if (colorA2 < size3) {
                                size2 = size3;
                                ColorExtractor.DominantColorResult dominantColorResultB = dominantColorResultArr[colorA2];
                                if (dominantColorResultB.percentage == 0.0f) {
                                    labA2 = labA4;
                                    minDistance = minDistance2;
                                    a = colorB;
                                    minIndex = minIndex3;
                                    hueA3 = hueA2;
                                } else {
                                    int colorB2 = dominantColorResultB.color;
                                    int a3 = colorB;
                                    if (colorSpace2 == ColorSpace.HSV || colorSpace2 == ColorSpace.HUE) {
                                        dominantColorResultB.copyHSV(hsvB);
                                    } else if (colorSpace2 == ColorSpace.LAB) {
                                        ColorUtils.colorToLAB(colorB2, labB3);
                                    }
                                    double minDistance3 = minDistance2;
                                    switch (AnonymousClass2.$SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace[colorSpace.ordinal()]) {
                                        case 1:
                                            minIndex2 = minIndex3;
                                            float hueA7 = hueA2;
                                            if (dominantColorResultA.isGrayScale == dominantColorResultB.isGrayScale) {
                                                if (dominantColorResultA.isGrayScale) {
                                                    labA3 = labA4;
                                                    squaredDistance3 = Math.pow((luminanceA2 - IUXColorUtils.calculateLuminance(colorB2)) * distanceThreshold * 1.5d, 2.0d);
                                                    d = 2.0d;
                                                    hueA4 = hueA7;
                                                    break;
                                                } else {
                                                    hueA4 = hueA7;
                                                    squaredDistance3 = Math.pow(IUXColorUtils.colorDistanceHueFast(hueA4, hsvB[0]), 2.0d);
                                                    labA3 = labA4;
                                                    d = 2.0d;
                                                    break;
                                                }
                                            } else {
                                                hueA4 = hueA7;
                                                double sqrt = Math.sqrt(IUXColorUtils.getHsvDistanceSquare2FromCornSpaceDoubleFast(hsvA, hsvB, sHsvSpaceHueRadiusValue));
                                                double sameSVdistance = sMaxHSV;
                                                double normalizedHSVdistance = sqrt / sameSVdistance;
                                                labA3 = labA4;
                                                d = 2.0d;
                                                double squaredDistance4 = Math.pow(Math.max(((normalizedHSVdistance - 0.019999999552965164d) * 360.0d) + distanceThreshold, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN), 2.0d);
                                                squaredDistance3 = squaredDistance4;
                                                break;
                                            }
                                        case 2:
                                            minIndex2 = minIndex3;
                                            double squaredDistance5 = IUXColorUtils.colorDistance_rgb_sqaure2(dominantColorResultA.color, dominantColorResultB.color);
                                            labA3 = labA4;
                                            squaredDistance3 = squaredDistance5;
                                            hueA4 = hueA2;
                                            d = 2.0d;
                                            break;
                                        case 3:
                                            minIndex2 = minIndex3;
                                            double squaredDistance6 = IUXColorUtils.getHsvDistanceSquare2FromCornSpaceDoubleFast(hsvA, hsvB, sHsvSpaceHueRadiusValue);
                                            labA3 = labA4;
                                            squaredDistance3 = squaredDistance6;
                                            hueA4 = hueA2;
                                            d = 2.0d;
                                            break;
                                        case 4:
                                            double d2 = labA4[0];
                                            float f = sLabSpaceLightnessScale;
                                            minIndex2 = minIndex3;
                                            labA3 = labA4;
                                            squaredDistance3 = IUXMathUtils.distanceSqrt2((d2 * f) - (labB3[0] * f), labA4[1] - labB3[1], labA4[2] - labB3[2]);
                                            hueA4 = hueA2;
                                            d = 2.0d;
                                            break;
                                        default:
                                            labA3 = labA4;
                                            minIndex2 = minIndex3;
                                            hueA4 = hueA2;
                                            d = 2.0d;
                                            break;
                                    }
                                    double squaredDistance7 = Math.pow(distanceThreshold, d);
                                    if (squaredDistance3 >= squaredDistance7 || minDistance3 <= squaredDistance3) {
                                        minIndex3 = minIndex2;
                                    } else {
                                        int minIndex4 = colorA2;
                                        minIndex3 = minIndex4;
                                        minDistance3 = squaredDistance3;
                                    }
                                    colorA2++;
                                    dominantColorResultArr = dominantColorArray;
                                    colorSpace2 = colorSpace;
                                    hueA2 = hueA4;
                                    size3 = size2;
                                    colorB = a3;
                                    minDistance2 = minDistance3;
                                    labA4 = labA3;
                                }
                            } else {
                                labA2 = labA4;
                                minDistance = minDistance2;
                                a = colorB;
                                size2 = size3;
                                minIndex = minIndex3;
                                hueA3 = hueA2;
                            }
                        }
                        int minIndex5 = minIndex;
                        if (minIndex5 == -1) {
                            a2 = a;
                        } else {
                            a2 = a;
                            colorResultDataArray4[a2] = new ColorResultData(a2, minIndex5, minDistance);
                            mergedColorNum2 = 1;
                        }
                        colorB = a2 + 1;
                        dominantColorResultArr = dominantColorArray;
                        colorSpace2 = colorSpace;
                        hueA5 = hueA3;
                        luminanceB2 = luminanceB;
                        squaredDistance2 = squaredDistance3;
                        loopCount2 = loopCount3;
                        colorResultDataArray3 = colorResultDataArray4;
                        size3 = size2;
                        labA4 = labA2;
                    }
                } else {
                    labA = labA4;
                    colorResultDataArray = colorResultDataArray3;
                    loopCount = loopCount2;
                    luminanceB = luminanceB2;
                    hueA = hueA5;
                    size = size3;
                    squaredDistance = squaredDistance2;
                }
            }
            if (mergedColorNum2 == 0) {
                labB = labB3;
                mergedColorNum = mergedColorNum3;
            } else {
                ColorResultData[] colorResultDataArray5 = colorResultDataArray;
                Arrays.sort(colorResultDataArray5, new Comparator<ColorResultData>() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor.1
                    AnonymousClass1() {
                    }

                    @Override // java.util.Comparator
                    public int compare(ColorResultData lhs, ColorResultData rhs) {
                        if (lhs == null && rhs == null) {
                            return 0;
                        }
                        if (lhs == null) {
                            return 1;
                        }
                        if (rhs == null) {
                            return -1;
                        }
                        return Double.compare(lhs.dist, rhs.dist);
                    }
                });
                int length = colorResultDataArray5.length;
                mergedColorNum = mergedColorNum3;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        labB = labB3;
                    } else {
                        ColorResultData colorResultData = colorResultDataArray5[i2];
                        if (colorResultData == null) {
                            labB = labB3;
                        } else {
                            int indexA = colorResultData.index;
                            int indexB = colorResultData.indexTarget;
                            ColorExtractor.DominantColorResult dominantColorResultA2 = dominantColorArray[indexA];
                            ColorExtractor.DominantColorResult dominantColorResultB2 = dominantColorArray[indexB];
                            if (dominantColorResultA2.percentage <= 0.0f || dominantColorResultB2.percentage <= 0.0f) {
                                i = length;
                                colorResultDataArray2 = colorResultDataArray5;
                                labB2 = labB3;
                            } else {
                                if (!isBrightnessOrientedMerge) {
                                    mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.A);
                                    i = length;
                                    colorResultDataArray2 = colorResultDataArray5;
                                    labB2 = labB3;
                                } else {
                                    int colorA3 = dominantColorResultA2.color;
                                    dominantColorResultA2.copyHSV(hsvA);
                                    int colorB3 = dominantColorResultB2.color;
                                    dominantColorResultB2.copyHSV(hsvB);
                                    i = length;
                                    boolean isGrayscaleA = dominantColorResultA2.isGrayScale;
                                    colorResultDataArray2 = colorResultDataArray5;
                                    float percentageA = dominantColorResultA2.percentage;
                                    labB2 = labB3;
                                    float percentageB = dominantColorResultB2.percentage;
                                    float brightnessA = IUXColorUtils.calculateLuminance(colorA3);
                                    float brightnessB = IUXColorUtils.calculateLuminance(colorB3);
                                    float percentageGap = percentageB / percentageA;
                                    if (dominantColorResultA2.isGrayScale == dominantColorResultB2.isGrayScale) {
                                        if (dominantColorResultA2.isGrayScale) {
                                            if (brightnessA < brightnessB * percentageGap) {
                                                mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.B);
                                            } else {
                                                mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.A);
                                            }
                                        } else if (hsvA[1] * hsvA[2] < hsvB[1] * hsvB[2] * percentageGap) {
                                            mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.B);
                                        } else {
                                            mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.A);
                                        }
                                    } else if (!isGrayscaleA) {
                                        mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.A);
                                    } else if (hsvA[2] < hsvB[2] * percentageGap * 1.2f) {
                                        mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.B);
                                    } else {
                                        mergeDominantColorUnit(dominantColorResultA2, dominantColorResultB2, ColorMergeType.A);
                                    }
                                }
                                mergedColorNum++;
                            }
                            i2++;
                            length = i;
                            labB3 = labB2;
                            colorResultDataArray5 = colorResultDataArray2;
                        }
                    }
                }
                sortColorResult(dominantColorArray);
            }
            if (mergedColorNum2 != 0) {
                loopCount2 = loopCount + 1;
                if (loopCount < 1000) {
                    dominantColorResultArr = dominantColorArray;
                    colorSpace2 = colorSpace;
                    luminanceA = luminanceA2;
                    luminanceB2 = luminanceB;
                    hueA5 = hueA;
                    squaredDistance2 = squaredDistance;
                    labB3 = labB;
                    size3 = size;
                    labA4 = labA;
                }
            }
        }
        return mergedColorNum;
    }

    /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$2 */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace;

        static {
            int[] iArr = new int[ColorSpace.values().length];
            $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace = iArr;
            try {
                iArr[ColorSpace.HUE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace[ColorSpace.RGB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace[ColorSpace.HSV.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$ColorPaletteExtractor$ColorSpace[ColorSpace.LAB.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$1 */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 implements Comparator<ColorResultData> {
        AnonymousClass1() {
        }

        @Override // java.util.Comparator
        public int compare(ColorResultData lhs, ColorResultData rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            }
            if (lhs == null) {
                return 1;
            }
            if (rhs == null) {
                return -1;
            }
            return Double.compare(lhs.dist, rhs.dist);
        }
    }

    public static int[] getOnlyColorsFromDominantColor(ColorExtractor.DominantColorResult[] dominantColorArray, double minHueDistance) {
        ArrayList<ColorExtractor.DominantColorResult> dominantColorArrayUnitList = new ArrayList<>();
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        int a = dominantColorArray.length;
        while (true) {
            a--;
            if (a <= 0) {
                break;
            }
            ColorExtractor.DominantColorResult dominantColorResultA = dominantColorArray[a];
            if (dominantColorResultA.percentage > 0.009f && !dominantColorResultA.isGrayScale) {
                float[] hsvA = dominantColorResultA.hsv;
                boolean isSameHueColor = false;
                int b = a - 1;
                while (true) {
                    if (b < 0) {
                        break;
                    }
                    ColorExtractor.DominantColorResult dominantColorResultB = dominantColorArray[b];
                    if (!dominantColorResultB.isGrayScale) {
                        float[] hsvB = dominantColorResultB.hsv;
                        float hueDistance = (float) IUXColorUtils.colorDistanceHueFast(hsvA[0], hsvB[0]);
                        if (hueDistance < minHueDistance) {
                            isSameHueColor = true;
                            break;
                        }
                    }
                    b--;
                }
                if (!isSameHueColor) {
                    dominantColorArrayUnitList.add(dominantColorResultA);
                }
            }
        }
        if (!dominantColorArray[0].isGrayScale) {
            dominantColorArrayUnitList.add(dominantColorArray[0]);
        }
        int size = dominantColorArrayUnitList.size();
        if (size > 0) {
            int[] colorResult = new int[size];
            for (int i = 0; i < size; i++) {
                colorResult[i] = dominantColorArrayUnitList.get((size - 1) - i).color;
            }
            return colorResult;
        }
        return null;
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] clusterGroups) {
        return kMeansHsv(bitmap, clusterGroups, false);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] clusterGroups, boolean isGettingExtraData) {
        if (bitmap == null) {
            return null;
        }
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.i(TAG, "kMeansHsv input bitmap size = " + bitmapWidth + " x " + bitmapHeight + " | ClusterGroups Num = " + clusterGroups.length);
        int[] pixels = new int[bitmapWidth * bitmapHeight];
        bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
        return kMeansHsv(pixels, clusterGroups, isGettingExtraData, bitmapWidth, bitmapHeight);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(int[] pixels, int[] clusterGroups) {
        return kMeansHsv(pixels, clusterGroups, false, 0, 0);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(int[] pixels, int[] clusterGroups, boolean isGettingExtraData, int bitmapWidth, int bitmapHeight) {
        int[] aN;
        long[][] aColorAvg;
        int avgR;
        int avgG;
        int avgR2;
        float[][] clusterGroupsCornSpace;
        float[] tempHsv;
        float hsvSpaceClusteringRadiusValue;
        float saturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale;
        int[] iArr = clusterGroups;
        int clusterNum = iArr.length;
        ColorExtractor.DominantColorResult[] dominantColorResults = new ColorExtractor.DominantColorResult[clusterNum];
        int[] clusterGroupsAvgColor = new int[clusterNum];
        float[][] clusterGroupsCornSpace2 = new float[clusterNum];
        float[][] clusterGroupsAvgHSV = new float[clusterNum];
        int[] aN2 = new int[clusterNum];
        long[][] aColorAvg2 = new long[clusterNum];
        int i = 3;
        float[] tempHsv2 = new float[3];
        float hsvSpaceClusteringRadiusValue2 = sHsvSpaceHueRadiusValue;
        float saturationThresholdForGrayscale2 = sSaturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale2 = sBrightnessThresholdForGrayscale;
        int i2 = 0;
        while (i2 < clusterNum) {
            ColorExtractor.DominantColorResult[] dominantColorResults2 = dominantColorResults;
            clusterGroupsAvgHSV[i2] = new float[i];
            float[] tempClusterGroupsHsv = new float[i];
            IUXColorUtils.colorToHSV(iArr[i2], tempClusterGroupsHsv);
            IUXColorUtils.convertHsv2CornSpace(tempClusterGroupsHsv, hsvSpaceClusteringRadiusValue2, tempClusterGroupsHsv);
            clusterGroupsCornSpace2[i2] = tempClusterGroupsHsv;
            aN2[i2] = 0;
            i = 3;
            aColorAvg2[i2] = new long[]{0, 0, 0};
            i2++;
            iArr = clusterGroups;
            dominantColorResults = dominantColorResults2;
        }
        ColorExtractor.DominantColorResult[] dominantColorResults3 = dominantColorResults;
        int R_MASK = Spanned.SPAN_PRIORITY;
        int numberOfVisiblePixels = 65280;
        int B_MASK = 255;
        Runtime runtime = Runtime.getRuntime();
        int A_MASK = 0;
        while (true) {
            if (A_MASK >= 1) {
                break;
            }
            long prevMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
            int R_MASK2 = R_MASK;
            int R_MASK3 = pixels.length;
            int G_MASK = numberOfVisiblePixels;
            int G_MASK2 = 0;
            int numberOfVisiblePixels2 = B_MASK;
            int B_MASK2 = 0;
            while (B_MASK2 < R_MASK3) {
                int i3 = R_MASK3;
                int pixel = pixels[B_MASK2];
                int alpha = pixel >>> 24;
                if (alpha <= 0) {
                    clusterGroupsCornSpace = clusterGroupsCornSpace2;
                    tempHsv = tempHsv2;
                    hsvSpaceClusteringRadiusValue = hsvSpaceClusteringRadiusValue2;
                    saturationThresholdForGrayscale = saturationThresholdForGrayscale2;
                    brightnessThresholdForGrayscale = brightnessThresholdForGrayscale2;
                } else {
                    int numberOfVisiblePixels3 = G_MASK2 + 1;
                    float minDist = Float.MAX_VALUE;
                    int minDistID = 0;
                    IUXColorUtils.colorToHSV(pixel, tempHsv2);
                    IUXColorUtils.convertHsv2CornSpace(tempHsv2, hsvSpaceClusteringRadiusValue2, tempHsv2);
                    float pixelCornSpaceH = tempHsv2[0];
                    float pixelCornSpaceS = tempHsv2[1];
                    float pixelCornSpaceV = tempHsv2[2];
                    for (int numberOfVisiblePixels4 = 0; numberOfVisiblePixels4 < clusterNum; numberOfVisiblePixels4++) {
                        float[] tempClusterGroupsHsv2 = clusterGroupsCornSpace2[numberOfVisiblePixels4];
                        float dH = pixelCornSpaceH - tempClusterGroupsHsv2[0];
                        float dS = pixelCornSpaceS - tempClusterGroupsHsv2[1];
                        float dV = pixelCornSpaceV - tempClusterGroupsHsv2[2];
                        float dist = (dH * dH) + (dS * dS) + (dV * dV);
                        if (dist < minDist) {
                            minDist = dist;
                            minDistID = numberOfVisiblePixels4;
                        }
                    }
                    long[] colorAvg = aColorAvg2[minDistID];
                    aN2[minDistID] = aN2[minDistID] + 1;
                    clusterGroupsCornSpace = clusterGroupsCornSpace2;
                    tempHsv = tempHsv2;
                    hsvSpaceClusteringRadiusValue = hsvSpaceClusteringRadiusValue2;
                    colorAvg[0] = colorAvg[0] + (pixel & Spanned.SPAN_PRIORITY);
                    saturationThresholdForGrayscale = saturationThresholdForGrayscale2;
                    brightnessThresholdForGrayscale = brightnessThresholdForGrayscale2;
                    colorAvg[1] = colorAvg[1] + (pixel & 65280);
                    colorAvg[2] = colorAvg[2] + (pixel & 255);
                    G_MASK2 = numberOfVisiblePixels3;
                }
                B_MASK2++;
                R_MASK3 = i3;
                saturationThresholdForGrayscale2 = saturationThresholdForGrayscale;
                brightnessThresholdForGrayscale2 = brightnessThresholdForGrayscale;
                clusterGroupsCornSpace2 = clusterGroupsCornSpace;
                tempHsv2 = tempHsv;
                hsvSpaceClusteringRadiusValue2 = hsvSpaceClusteringRadiusValue;
            }
            float[][] clusterGroupsCornSpace3 = clusterGroupsCornSpace2;
            float[] tempHsv3 = tempHsv2;
            float hsvSpaceClusteringRadiusValue3 = hsvSpaceClusteringRadiusValue2;
            float saturationThresholdForGrayscale3 = saturationThresholdForGrayscale2;
            float brightnessThresholdForGrayscale3 = brightnessThresholdForGrayscale2;
            int i4 = 0;
            while (i4 < clusterNum) {
                long[] avgColor = aColorAvg2[i4];
                int number = aN2[i4];
                if (number <= 0) {
                    avgR = 0;
                    avgG = 0;
                    avgR2 = 0;
                    aN = aN2;
                    aColorAvg = aColorAvg2;
                } else {
                    int avgR3 = ((int) (avgColor[0] / number)) & Spanned.SPAN_PRIORITY;
                    aN = aN2;
                    aColorAvg = aColorAvg2;
                    int avgG2 = ((int) (avgColor[1] / number)) & 65280;
                    avgR = avgR3;
                    avgG = avgG2;
                    avgR2 = ((int) (avgColor[2] / number)) & 255;
                }
                clusterGroupsAvgColor[i4] = (-16777216) | avgR | avgG | avgR2;
                IUXColorUtils.colorToHSV(clusterGroupsAvgColor[i4], clusterGroupsAvgHSV[i4]);
                i4++;
                aN2 = aN;
                aColorAvg2 = aColorAvg;
            }
            int[] aN3 = aN2;
            long[][] aColorAvg3 = aColorAvg2;
            int i5 = 1 - 1;
            if (A_MASK == i5) {
                float invPixelLength = G_MASK2 > 0 ? 1.0f / G_MASK2 : 0.0f;
                for (int i6 = 0; i6 < clusterNum; i6++) {
                    boolean isGrayScale = checkGayScaleWithSV(clusterGroupsAvgHSV[i6], saturationThresholdForGrayscale3, brightnessThresholdForGrayscale3);
                    float percentage = aN3[i6] * invPixelLength;
                    dominantColorResults3[i6] = new ColorExtractor.DominantColorResult(clusterGroupsAvgColor[i6], percentage, isGrayScale);
                }
                long currentMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
                Log.i(TAG, "ColorExtractor Memory Usage " + (currentMemoryUsage - prevMemoryUsage) + " length: " + pixels.length);
            } else {
                A_MASK++;
                saturationThresholdForGrayscale2 = saturationThresholdForGrayscale3;
                aN2 = aN3;
                B_MASK = numberOfVisiblePixels2;
                R_MASK = R_MASK2;
                numberOfVisiblePixels = G_MASK;
                aColorAvg2 = aColorAvg3;
                clusterGroupsCornSpace2 = clusterGroupsCornSpace3;
                hsvSpaceClusteringRadiusValue2 = hsvSpaceClusteringRadiusValue3;
                brightnessThresholdForGrayscale2 = brightnessThresholdForGrayscale3;
                tempHsv2 = tempHsv3;
            }
        }
        sortColorResult(dominantColorResults3);
        return dominantColorResults3;
    }
}

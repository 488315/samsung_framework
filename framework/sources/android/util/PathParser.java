package android.util;

import android.graphics.Path;
import dalvik.annotation.optimization.FastNative;

/* loaded from: classes4.dex */
public class PathParser {
    static final String LOGTAG = PathParser.class.getSimpleName();

    /* renamed from: -$$Nest$smnCreateEmptyPathData */
    static /* bridge */ /* synthetic */ long m4944$$Nest$smnCreateEmptyPathData() {
        return nCreateEmptyPathData();
    }

    @FastNative
    private static native boolean nCanMorph(long j, long j2);

    @FastNative
    private static native long nCreateEmptyPathData();

    @FastNative
    public static native long nCreatePathData(long j);

    public static native long nCreatePathDataFromString(String str, int i);

    @FastNative
    private static native void nCreatePathFromPathData(long j, long j2);

    @FastNative
    public static native void nFinalize(long j);

    @FastNative
    private static native boolean nInterpolatePathData(long j, long j2, long j3, float f);

    private static native void nParseStringForPath(long j, String str, int i);

    @FastNative
    public static native void nSetPathData(long j, long j2);

    public static Path createPathFromPathData(String pathString) {
        if (pathString == null) {
            throw new IllegalArgumentException("Path string can not be null.");
        }
        Path path = new Path();
        nParseStringForPath(path.mNativePath, pathString, pathString.length());
        return path;
    }

    public static void createPathFromPathData(Path outPath, PathData data) {
        nCreatePathFromPathData(outPath.mNativePath, data.mNativePathData);
    }

    public static boolean canMorph(PathData pathDataFrom, PathData pathDataTo) {
        return nCanMorph(pathDataFrom.mNativePathData, pathDataTo.mNativePathData);
    }

    /* loaded from: classes4.dex */
    public static class PathData {
        long mNativePathData;

        public PathData() {
            this.mNativePathData = 0L;
            this.mNativePathData = PathParser.m4944$$Nest$smnCreateEmptyPathData();
        }

        public PathData(PathData data) {
            this.mNativePathData = 0L;
            this.mNativePathData = PathParser.nCreatePathData(data.mNativePathData);
        }

        public PathData(String pathString) {
            this.mNativePathData = 0L;
            long nCreatePathDataFromString = PathParser.nCreatePathDataFromString(pathString, pathString.length());
            this.mNativePathData = nCreatePathDataFromString;
            if (nCreatePathDataFromString == 0) {
                throw new IllegalArgumentException("Invalid pathData: " + pathString);
            }
        }

        public long getNativePtr() {
            return this.mNativePathData;
        }

        public void setPathData(PathData source) {
            PathParser.nSetPathData(this.mNativePathData, source.mNativePathData);
        }

        protected void finalize() throws Throwable {
            long j = this.mNativePathData;
            if (j != 0) {
                PathParser.nFinalize(j);
                this.mNativePathData = 0L;
            }
            super.finalize();
        }
    }

    public static boolean interpolatePathData(PathData outData, PathData fromData, PathData toData, float fraction) {
        return nInterpolatePathData(outData.mNativePathData, fromData.mNativePathData, toData.mNativePathData, fraction);
    }
}

package com.google.zxing.common;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BitMatrix {
    public final int[] bits;
    public final int height;
    public final int rowSize;
    public final int width;

    public BitMatrix(int i) {
        this(i, i);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        if (this.width == bitMatrix.width && this.height == bitMatrix.height && this.rowSize == bitMatrix.rowSize) {
            int[] iArr = this.bits;
            int length = iArr.length;
            int[] iArr2 = bitMatrix.bits;
            if (length == iArr2.length) {
                for (int i = 0; i < iArr.length; i++) {
                    if (iArr[i] != iArr2[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final boolean get(int i, int i2) {
        if (((this.bits[(i2 * this.rowSize) + (i >> 5)] >>> (i & 31)) & 1) != 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = this.width;
        int i2 = (((((i * 31) + i) * 31) + this.height) * 31) + this.rowSize;
        for (int i3 : this.bits) {
            i2 = (i2 * 31) + i3;
        }
        return i2;
    }

    public final void set(int i, int i2) {
        int i3 = (i2 * this.rowSize) + (i >> 5);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) | iArr[i3];
    }

    public final void setRegion(int i, int i2, int i3, int i4) {
        if (i2 >= 0 && i >= 0) {
            if (i4 >= 1 && i3 >= 1) {
                int i5 = i3 + i;
                int i6 = i4 + i2;
                if (i6 <= this.height && i5 <= this.width) {
                    while (i2 < i6) {
                        int i7 = this.rowSize * i2;
                        for (int i8 = i; i8 < i5; i8++) {
                            int i9 = (i8 >> 5) + i7;
                            int[] iArr = this.bits;
                            iArr[i9] = iArr[i9] | (1 << (i8 & 31));
                        }
                        i2++;
                    }
                    return;
                }
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        throw new IllegalArgumentException("Left and top must be nonnegative");
    }

    public final String toString() {
        String str;
        int i = this.width;
        int i2 = this.height;
        StringBuilder sb = new StringBuilder((i + 1) * i2);
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i; i4++) {
                if (get(i4, i3)) {
                    str = "X ";
                } else {
                    str = "  ";
                }
                sb.append(str);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public BitMatrix(int i, int i2) {
        if (i >= 1 && i2 >= 1) {
            this.width = i;
            this.height = i2;
            int i3 = (i + 31) >> 5;
            this.rowSize = i3;
            this.bits = new int[i3 * i2];
            return;
        }
        throw new IllegalArgumentException("Both dimensions must be greater than 0");
    }
}
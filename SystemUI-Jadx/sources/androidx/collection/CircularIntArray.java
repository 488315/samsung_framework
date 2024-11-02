package androidx.collection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CircularIntArray {
    public int capacityBitmask;
    public int[] elements;
    public int tail;

    public CircularIntArray() {
        this(0, 1, null);
    }

    public final void addLast(int i) {
        int[] iArr = this.elements;
        int i2 = this.tail;
        iArr[i2] = i;
        int i3 = this.capacityBitmask & (i2 + 1);
        this.tail = i3;
        if (i3 == 0) {
            int length = iArr.length;
            int i4 = length + 0;
            int i5 = length << 1;
            if (i5 >= 0) {
                int[] iArr2 = new int[i5];
                System.arraycopy(iArr, 0, iArr2, 0, i4);
                System.arraycopy(this.elements, 0, iArr2, i4, 0);
                this.elements = iArr2;
                this.tail = length;
                this.capacityBitmask = i5 - 1;
                return;
            }
            throw new RuntimeException("Max array capacity exceeded");
        }
    }

    public CircularIntArray(int i) {
        if (!(i >= 1)) {
            throw new IllegalArgumentException("capacity must be >= 1".toString());
        }
        if (i <= 1073741824) {
            i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
            this.capacityBitmask = i - 1;
            this.elements = new int[i];
            return;
        }
        throw new IllegalArgumentException("capacity must be <= 2^30".toString());
    }

    public /* synthetic */ CircularIntArray(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }
}
